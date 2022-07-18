package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntitySummaryData
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExportContent
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.service.ExportService
import com.bionova.optimi.util.UrlConnectionUtil
import gnu.trove.map.hash.THashMap
import grails.converters.JSON
import org.apache.tools.ant.util.StringUtils
import java.util.zip.ZipOutputStream

/**
 * @author Pasi-Markus Mäkelä
 */
class ExportController extends AbstractDomainObjectController {

    def entityService
    def indicatorService
    def configurationService
    def queryService
    def datasetService
    def flashService
    def valueReferenceService
    ExportService exportService

    def exportJsOn() {
        Entity entity
        Indicator indicator

        if (params.indicatorId && params.entityId && params.childEntityId) {
            entity = entityService.getEntityById(params.childEntityId, session)
            indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
            IndicatorExportContent exportContent = indicator?.exportContent

            if (exportContent) {
            } else {
                flash.fadeErrorAlert = message(code: "export.indicator_exportContent.missing")
            }
        }

        if ("design".equals(entity?.entityClass)) {
            redirect controller: "design", action: "results", params: [entityId: params.entityId, childEntityId: params.childEntityId, indicatorId: indicator.indicatorId]
        } else {
            redirect controller: "operatingPeriod", action: "results", params: [entityId: params.entityId, childEntityId: params.childEntityId, indicatorId: indicator.indicatorId]
        }
    }

    private THashMap getResultsByQueries(IndicatorExportContent exportContent, Entity entity) {
        Map queriesAndSections = exportContent?.queries
        Map<String, Map> answers = new THashMap<String, Map>()

        if (queriesAndSections && entity) {
            queriesAndSections.each { String queryId, List sectionIds ->
                Query query = queryService.getQueryByQueryId(queryId, true)
                Map answersBySections = new THashMap()

                sectionIds.each { String sectionId ->
                    Map answersByQuestions = new THashMap()
                    QuerySection section = query.sections?.find({ it.sectionId.equals(sectionId) })
                    def datasets = datasetService.getDatasetsByEntityAndQueryId(entity, queryId)?.findAll({
                        it.sectionId.equals(sectionId) && it.answerIds
                    })

                    if (datasets && query && section) {
                        def questionIds = datasets.collect({ it.questionId })

                        List<Question> questions = query.getQuestionsBySection(sectionId)?.findAll({ Question question -> questionIds.contains(question.questionId) })

                        if (questions) {
                            questions.each { Question question ->
                                answersByQuestions.put(question.localizedQuestion, datasets.find({
                                    it.questionId.equals(question.questionId)
                                })?.answerIds[0])
                            }
                            answers.put(query.localizedName, datasets.collect({ it.answerIds[0] }))
                        }
                    }

                    if (answersByQuestions) {
                        answersBySections.put(section.localizedName, answersByQuestions)
                    }
                }

                if (answersBySections) {
                    answers.put(query?.localizedName, answersBySections)
                }
            }
        }
        return answers
    }

    private Map getParentEntityData(Entity parentEntity) {
        Query basicQuery = queryService.getBasicQuery()
        Map parentEntityData = new THashMap()

        if (parentEntity && basicQuery) {
            EntitySummaryData summaryData = basicQuery.summaryData?.find({ EntitySummaryData sd ->
                sd.entityClass.equals(parentEntity.entityClass)
            })

            if (summaryData) {
                summaryData.displayName?.each { String attributeName ->
                    def value = parentEntity.getSummaryDataValue(attributeName)

                    if (value) {
                        def messageCode = "entity." + attributeName

                        if (value instanceof Boolean) {
                            value = formatBoolean(boolean: value, true: message(code: 'yes'), false: message(code: 'no'))
                        }
                        parentEntityData.put(message(code: messageCode), value)
                    }
                }
            }
        }
        return parentEntityData
    }

    @Override
    def list() {
        return null
    }

    @Override
    def saveInternal() {
        return null
    }

    def exportWidget() {
        def entityId = params.childEntityId
        Entity entity = entityService.getEntityByIdReadOnly(entityId)
        def parentEntityId = params.entityId
        def indicatorId = params.indicatorId
        def content = ""
        def fileName
        def url

        if (entity) {
            String widgetUrl

            if (indicatorId) {
               Indicator widgetIndicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                widgetUrl = valueReferenceService.getValueForEntity(widgetIndicator.widgetTemplateUrl, entity, Boolean.TRUE)
            }

            if (widgetUrl) {
                Set<Dataset> datasets = datasetService.getDatasetsByEntity(entity)
                content = UrlConnectionUtil.getContentFromSslUrl(widgetUrl)
                Map<String, ValueReference> stringsAndValueReferences = getValueReferences(content, entity)
                stringsAndValueReferences?.each { String originalValue, ValueReference valueReference ->
                    List<String> value = valueReferenceService.getWidgetValueFromEntity(valueReference, entity)

                    if (value) {
                        if (value.size() > 1 || "listItem".equals(valueReference.exportType)) {
                            def list = "<ul>"
                            value.each {
                                list = "${list}<li>${it}</li>"
                            }
                            list = "${list}</ul>"
                            content = content.replace(originalValue, list)
                        } else {
                            content = content.replace(originalValue, value[0])
                        }
                    } else {
                        content = content.replace(originalValue, "")
                    }
                }

                try {
                    String filePrefix = configurationService.getConfigurationValue(Constants.APPLICATION_ID, Constants.ConfigName.WIDGET_EXPORT_PREFIX.toString())
                    Entity parent = entityService.getEntityByIdReadOnly(parentEntityId)
                    fileName = filePrefix + URLEncoder.encode("${parentEntityId}_${entity.operatingPeriodAndName}.html", "UTF-8")
                    File file = new File(fileName)
                    url = StringUtils.removePrefix(fileName, "/var/www/")
                    url = "${request.scheme}://$request.serverName/${url}"
                    file.setText(content, "UTF-8")
                    String width = datasets?.find({ "widgetWidth".equals(it.questionId) })?.answerIds?.get(0)
                    String height = datasets?.find({ "widgetHeight".equals(it.questionId) })?.answerIds?.get(0)
                    String okMessage = "Widget for ${parent?.name} generated to: <a href=\"${url}\" target=\"_blank\">${url}</a>."

                    if (width != null || height != null) {
                        okMessage = "${okMessage} Widget width is ${width != null ? width : '0'} pixels and height is ${height != null ? height : '0'} pixels"
                    }
                    flash.fadeSuccessAlert = okMessage
                } catch (Exception e) {
                    loggerUtil.error(log, "Error in generating thw widget", e)
                    flash.errorMEssage = "Error in generating the widget"
                }
            } else {
                flash.errorAlert = "Could not generate widget, because template url is missing."
            }
        }

        if ("design".equals(entity?.entityClass)) {
            redirect controller: "design", action: "results", params: [entityId: parentEntityId, childEntityId: entityId, indicatorId: indicatorId]
        } else {
            redirect controller: "operatingPeriod", action: "results", params: [entityId: parentEntityId, childEntityId: entityId, indicatorId: indicatorId]
        }
    }

    private Map<String, ValueReference> getValueReferences(String htmlContent, Entity entity) {
        Map<String, ValueReference> stringAndEquivalentValueReferenceValue = new HashMap<String, ValueReference>()
        int startIndex = 0
        int endIndex = 0

        if (htmlContent) {
            while (startIndex != -1) {
                if (startIndex > 0 && endIndex > 0) {
                    startIndex = htmlContent.indexOf("<!--", endIndex + 1)
                    endIndex = htmlContent.indexOf("-->", endIndex + 1)
                } else {
                    startIndex = htmlContent.indexOf("<!--")
                    endIndex = htmlContent.indexOf("-->")
                }
                if (startIndex != -1 && endIndex != -1) {
                    String originalString = htmlContent.substring(startIndex, endIndex + 3)
                    String valueReferenceAsJsOn = htmlContent.substring(startIndex + 4, endIndex)?.trim()

                    if (originalString && valueReferenceAsJsOn) {
                        try {
                            stringAndEquivalentValueReferenceValue.put(originalString,
                                    new ValueReference(JSON.parse(valueReferenceAsJsOn)))
                        } catch (Exception e) {
                            log.error("Invalid json: ${valueReferenceAsJsOn}")
                            flashService.setFadeInfoAlert("Invalid json: ${valueReferenceAsJsOn}", true)
                        }
                    }
                }
            }
        }
        return stringAndEquivalentValueReferenceValue
    }
    /**
     * download digital epdhub json
     * @return
     */
    def downloadEPDJson() {

        Entity entity
        File jsonFile
        final String EPD_JSON_FILENAME = "digital_epd.json"
        Entity childEntity = entityService.getEntityById(params.childEntityId)
        if (childEntity) {
            entity = childEntity.getParentById()
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
            jsonFile = exportService.exportEPDJson(childEntity, indicator, entity)
        }
        render file: jsonFile, fileName: EPD_JSON_FILENAME, contentType: "application/json"

    }
    /**
     * creates a downloadable zip file with json , ilcd epd xml , images etc
     * @return
     */
    def downloadDigitalEPDContent() {

        File tempFile
        ZipOutputStream zip
        Entity childEntity = entityService.getEntityById(params.childEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        Entity entity
        if (childEntity) {
            entity = childEntity.getParentById()
            final Map<String, String> fileNames = ["brandImage"               : "brand_image.png", "productImage": "product_image.jpg",
                                                   "manufacturingDiagramImage": "manufacturer_diagram_image.png"]
            final String EPD_JSON_FILENAME = "digital_epd.json"
            final String ILCD_EPD_XML_FILENAME = "ilcd_xml_export.xml"
            final String EPD_WORD_DRAFT_FILENAME = "epd_draft.docx"

            try {
                response.setContentType('APPLICATION/OCTET-STREAM')
                response.setHeader('Content-Disposition', "Attachment;Filename=${entity.name}_${childEntity.name}.zip")
                zip = new ZipOutputStream(response.outputStream)

                exportService.generateEPDJson(childEntity, indicator, entity, tempFile, zip, EPD_JSON_FILENAME)
                exportService.generateImageFiles(childEntity, indicator, tempFile, fileNames, zip)
                Object otherExports = indicator.epdHubExportContent.get("fileExport")
                if (otherExports) {
                    exportService.generateILCDEPDXml(childEntity, indicator, entity, tempFile, zip, ILCD_EPD_XML_FILENAME)
                    String wordConfig = otherExports["epdDraft"]?.find({ it })
                    exportService.generateEPDWordExport(childEntity, indicator, tempFile, zip, EPD_WORD_DRAFT_FILENAME, wordConfig)
                }
                zip.flush()
                zip.close()
            } catch (IOException io) {
                loggerUtil.error(log, "IOException ! : ${io}")
                flashService.setErrorAlert("IOException occured while epd zip folder generation : ${io.message}", true)
            }
        }
    }

}
