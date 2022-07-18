package com.bionova.optimi.core.service

import com.bionova.optimi.comparator.ResultListDatasetConstructionComparator
import com.bionova.optimi.construction.Constants as Const
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.ReportConditionalRemoval
import com.bionova.optimi.core.domain.mongo.ReportConditionalRemovalQueryValue
import com.bionova.optimi.core.domain.mongo.ReportGenerationRule
import com.bionova.optimi.core.domain.mongo.ReportGenerationRuleContent
import com.bionova.optimi.core.domain.mongo.ReportGenerationRuleTemplate
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.WordTextReplacer
import grails.plugin.springsecurity.web.SecurityRequestHolder
import groovy.time.TimeCategory
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.io.FileUtils
import org.apache.poi.util.Units
import org.apache.poi.xwpf.usermodel.IBodyElement
import org.apache.poi.xwpf.usermodel.PositionInParagraph
import org.apache.poi.xwpf.usermodel.TextSegment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFFooter
import org.apache.poi.xwpf.usermodel.XWPFHeader
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.XWPFTable
import org.apache.poi.xwpf.usermodel.XWPFTableCell
import org.apache.poi.xwpf.usermodel.XWPFTableRow
import org.apache.xmlbeans.XmlCursor
import org.bson.Document
import org.grails.web.util.WebUtils
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder

import java.awt.*
import java.math.MathContext
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.List

class WordDocumentService {

    def userService
    def messageSource
    def resultFormattingResolver
    def entityService
    def accountImagesService
    def optimiResourceService
    def loggerUtil
    def flashService
    def imageUtil
    def datasetService
    def queryService
    def calculationRuleService
    def denominatorUtil
    def valueReferenceService
    def calculationResultService
    ScopeFactoryService scopeFactoryService
    def indicatorService
    def denominatorService
    QuestionService questionService

    // Hung: why these numbers? I have no idea :)
    private static final int DEFAULT_HEIGHT = 300
    private static final int DEFAULT_WIDTH = 500
    private static final String CONSTRUCTION_RESOURCE_HIGHLIGHT_BACKGROUND_COLOR = "CCCCCC" // dark grey
    private static final String CONSTRUCTION_CONSTITUENT_HIGHLIGHT_BACKGROUND_COLOR = "EEEEEE" // light grey
    private static final int SCOPE_TARGET_MARKET_STAGE_START_INDEX = 14

    // End of validity for Betie tool (SW-1751)
    private static final int END_OF_VALIDITY_DATE_YEARS = 5

    def File createDocument(Entity childEntity, Indicator indicator, String templateId, String reportGenerationRuleId) {
        List<ReportGenerationRule> reportGenerationRules = indicator.getReportGenerationRuleFromApplication()
        ReportGenerationRule reportGenerationRule = reportGenerationRules.find({ it.reportGenerationRuleId.equals(reportGenerationRuleId) })
        ReportGenerationRuleTemplate reportGenerationRuleTemplate = reportGenerationRule?.allowedTemplates?.find({ it.templateId?.equals(templateId) })
        File wordFile

        if (reportGenerationRule && reportGenerationRuleTemplate) {
            Boolean httpTemplate = Boolean.FALSE
            File wordDoc

            // Fetch report template
            if (reportGenerationRuleTemplate.template?.startsWith("http")) {
                httpTemplate = Boolean.TRUE
                try {
                    wordDoc = File.createTempFile("tempDocxFile", ".docx")
                    URL download = new URL(reportGenerationRuleTemplate.template)
                    InputStream inputStream = download.openStream()
                    FileUtils.copyInputStreamToFile(inputStream, wordDoc)
                    inputStream.close()
                } catch (Exception e) {
                    loggerUtil.error(log, "WORD DOCUMENT CREATION: Error when getting HTTP template", e)
                    flashService.setErrorAlert("WORD DOCUMENT CREATION: Error when getting HTTP template ${e.message}", true)
                }
            } else {
                wordDoc = new File(reportGenerationRuleTemplate.template)
            }

            if (wordDoc && wordDoc.exists() && wordDoc.isFile()) {
                Entity parentEntity = childEntity?.getParentById()
                XWPFDocument document

                try {
                    // If http template is misconfigured or file in templates is not valid for XWPFDocument
                    document = new XWPFDocument(new FileInputStream(wordDoc))
                } catch (Exception e) {
                    loggerUtil.error(log, "WORD DOCUMENT CREATION: Error when reading template ${reportGenerationRuleTemplate.template}", e)
                    flashService.setErrorAlert("WORD DOCUMENT CREATION: Error when reading template ${reportGenerationRuleTemplate.template}: ${e.message}", true)
                }

                if (document) {
                    List<CalculationResult> resultsByIndicator = childEntity?.getCalculationResultObjects(indicator?.indicatorId, null, null)
                    DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(userService.getCurrentUser()?.localeString))
                    decimalFormat.applyPattern("0.#####E0")
                    Map<String, String> fieldsForReport = [:]

                    // Fetch the generation rules
                    List<ReportGenerationRuleContent> contentGenerationRulesForTemplate = reportGenerationRule.getContentGenerationRulesForTemplate(indicator?.getContentInjectionSetsFromApplication())

                    // resources linked to the design and tool
                    List<Resource> resources = childEntity?.getSourceListing(indicator)

                    // Generate a list of included scopes for EPD reports
                    generateScopesList(parentEntity, childEntity)

                    // Fetch the data for injection
                    contentGenerationRulesForTemplate?.each { ReportGenerationRuleContent ruleContent ->
                        if ("text".equals(ruleContent.type)) {
                            String value = getTextValueForReportGenerationRuleContent(parentEntity, childEntity, ruleContent, indicator, reportGenerationRuleId)
                            value = value ? value : ""
                            fieldsForReport.put(ruleContent.targetMarker, value)
                        } else if ("table".equals(ruleContent.type)) {
                            addTableByReportGenerationRuleContent(childEntity, indicator, document, ruleContent, resultsByIndicator, resources)
                        } else if ("image".equals(ruleContent.type)) {
                            if ("REPLACE-PRODUCT-IMAGE".equals(ruleContent.targetMarker) || "REPLACE-MANUFACTURING-DIAGRAM".equals(ruleContent.targetMarker) || "REPLACE-BRANDING-IMAGE".equals(ruleContent.targetMarker)) {
                                Dataset dataset
                                String id
                                if ("child".equals(ruleContent.entity)) {
                                    dataset = childEntity?.datasets?.find({
                                        ruleContent.queryId.equals(it.queryId) &&
                                                ruleContent.sectionId.equals(it.sectionId) && ruleContent.questionId.equals(it.questionId)
                                    })
                                }

                                if (dataset) {
                                    if (dataset.answerIds) {
                                        id = dataset.answerIds.get(0)
                                    }
                                }

                                AccountImages productImage = accountImagesService.getAccountImage(id)
                                if (productImage) {
                                    File generatedImage = new File("${parentEntity?.name}_${childEntity?.operatingPeriodAndName}.jpeg")
                                    FileUtils.writeByteArrayToFile(generatedImage, productImage.image)
                                    replaceTextWithImage(generatedImage, document, ruleContent.targetMarker, ruleContent.imageWidth, ruleContent.imageHeight, "jpeg")
                                    generatedImage.delete()
                                }
                            }
                            if ("REPLACE-PICTURE".equals(ruleContent.targetMarker)) {
                                EntityFile image

                                if ("parent".equals(ruleContent.getEntity())) {
                                    image = entityService.getEntityFile(parentEntity?.id?.toString(), ruleContent.queryId, ruleContent.sectionId, ruleContent.questionId)
                                } else {
                                    image = entityService.getEntityFile(childEntity?.id?.toString(), ruleContent.queryId, ruleContent.sectionId, ruleContent.questionId)
                                }

                                if (image && image.isImage) {
                                    File entityImage = new File("${parentEntity?.name}_${childEntity?.operatingPeriodAndName}.${image.type}")
                                    FileUtils.writeByteArrayToFile(entityImage, image.data)
                                    replaceTextWithImage(entityImage, document, ruleContent.targetMarker, ruleContent.imageWidth, ruleContent.imageHeight, image.type)
                                    entityImage.delete()
                                }
                            } else {
                                replaceTextWithImage(getChartImage(ruleContent), document, ruleContent.targetMarker, ruleContent.imageWidth, ruleContent.imageHeight, null)
                            }
                        } else if ("result".equals(ruleContent.type)) {
                            Double result = getResultValueForReportGenerationRuleContent(ruleContent, resultsByIndicator)
                            String value

                            if (result != null) {
                                BigDecimal bd = new BigDecimal(result)
                                bd = bd.round(new MathContext(3))
                                result = bd.doubleValue()
                                value = decimalFormat.format(result)
                            } else {
                                String replacementText = reportGenerationRule.getTextReplacement(ruleContent)

                                if (replacementText) {
                                    value = replacementText
                                } else {
                                    value = decimalFormat.format(0D)
                                }
                            }
                            fieldsForReport.put(ruleContent.targetMarker, value)
                        } else if ("imageFile".equals(ruleContent.type)) {
                            String answer = getTextValueForReportGenerationRuleContent(parentEntity, childEntity, ruleContent, indicator, true, reportGenerationRuleId)
                            String imgPath = answer ? ruleContent.answerToImageFile?.get(answer) : ''
                            File imgFile = null
                            try {
                                imgFile = imgPath ? new File(imgPath) : null
                            } catch (ignored) {
                            }
                            replaceTextWithImage(imgFile, document, ruleContent.targetMarker, ruleContent.imageWidth, ruleContent.imageHeight, "jpeg")
                        }
                    }

                    List<String> resultMarkers = contentGenerationRulesForTemplate?.findAll({ "result".equals(it.type) })?.collect({ it.targetMarker })

                    // fetch removal sections for sections to be removed if removal conditions are met
                    List<ReportConditionalRemoval> removalRulesForTemplate = reportGenerationRule?.getRemovalRulesForTemplate(indicator?.getReportConditionalRemovalRulesFromApplication())

                    // start injecting content
                    if (!fieldsForReport.isEmpty()) {
                        List<Integer> removalTargets = getRemovalTargetPositions(document, removalRulesForTemplate, parentEntity, childEntity)
                        if (removalTargets) {
                            int indexAdjustment = 0
                            removalTargets.each { Integer position ->
                                try {
                                    document.removeBodyElement(position - indexAdjustment)
                                    indexAdjustment++
                                } catch (Exception e) {
                                    loggerUtil.warn(log, "WordDocumentService: createDocument: Error trying to remove element position ${position} on templateId ${reportGenerationRuleTemplate.templateId}", e)
                                    flashService.setErrorAlert("WordDocumentService: createDocument: Error trying to remove element position ${position} on templateId ${reportGenerationRuleTemplate.templateId}: ${e.message}", true)
                                }
                            }
                        }

                        WordTextReplacer.iterateThroughParagraphs(document, fieldsForReport)
                        WordTextReplacer.iterateThroughFooters(document, fieldsForReport)
                        WordTextReplacer.iterateThroughHeaders(document, fieldsForReport)
                        WordTextReplacer.iterateThroughTables(document, fieldsForReport, resultMarkers, reportGenerationRuleId)
                    }
                    String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())
                    // Cleanup all special characters and extra spaces
                    String wordFileName = "One Click LCA ${parentEntity.name?.replaceAll("\\W", " ")?.replaceAll("\\s{2,}", " ")?.trim()} ${reportGenerationRuleTemplate.localizedName} ${date}"
                    wordFile = File.createTempFile(wordFileName, ".docx")

                    try {
                        OutputStream outputStream = new FileOutputStream(wordFile)
                        document.write(outputStream)
                        outputStream.close()
                        document.close()
                    } catch (Exception e) {
                        wordFile = null
                        loggerUtil.error(log, "Error in wordreportGeneration", e)
                        flashService.setErrorAlert("Error in wordreportGeneration: ${e.message}", true)
                    }

                    if (httpTemplate) {
                        wordDoc.delete() // Delete template created from http inputstream
                    }
                }
            } else {
                loggerUtil.error(log, "Word Generation: No valid template found from: ${templateId} - ${reportGenerationRuleTemplate.template}")
                flashService.setErrorAlert("Word Generation: No valid template found from: ${templateId} - ${reportGenerationRuleTemplate.template}", true)
            }
        }
        deleteChartImages()
        return wordFile
    }

    private List<Integer> getRemovalTargetPositions(XWPFDocument doc, List<ReportConditionalRemoval> removalRulesForTemplate, Entity parentEntity, Entity childEntity) {
        List<Integer> removalTargetPositions = []
        try {
            if (doc && removalRulesForTemplate) {
                List<String> parentFeatures = parentEntity.features?.collect({ it.featureId })
                removalRulesForTemplate.each { ReportConditionalRemoval conditionalRemovalRule ->
                    if (conditionalRemovalRule && conditionalRemovalRule.conditionTrigger && conditionalRemovalRule.removalMarkerStart && conditionalRemovalRule.removalMarkerEnd) {
                        boolean triggerRemoval = false

                        // evaluate the triggerRemoval condition
                        if (conditionalRemovalRule.conditionTrigger == Constants.ConditionTriggerReportRemoval.LICENSEKEY.toString() && conditionalRemovalRule.licenseKey && parentEntity) {

                            // trigger the removal if project is not licensed
                            boolean isLicensed = parentFeatures?.contains(conditionalRemovalRule.licenseKey.toString())
                            triggerRemoval = !isLicensed

                        } else if (conditionalRemovalRule.conditionTrigger == Constants.ConditionTriggerReportRemoval.QUERYVALUE.toString() && conditionalRemovalRule.queryValue && conditionalRemovalRule.queryValue.condition) {

                            // trigger the removal based on a query answer's value
                            ReportConditionalRemovalQueryValue conditionForQueryValue = conditionalRemovalRule.queryValue
                            Dataset dataset

                            // find the dataset
                            if (conditionForQueryValue.queryId && conditionForQueryValue.sectionId && conditionForQueryValue.questionId) {
                                if (conditionForQueryValue.entity == Constants.EntityType.PARENT.toString()) {
                                    dataset = parentEntity?.datasets?.find { conditionForQueryValue.queryId == it.queryId && conditionForQueryValue.sectionId == it.sectionId && conditionForQueryValue.questionId == it.questionId }
                                } else if (conditionForQueryValue.entity == Constants.EntityType.CHILD.toString()) {
                                    dataset = childEntity?.datasets?.find { conditionForQueryValue.queryId == it.queryId && conditionForQueryValue.sectionId == it.sectionId && conditionForQueryValue.questionId == it.questionId }
                                }
                            }

                            // find the answer
                            String answer = ''
                            if (dataset) {
                                if (conditionForQueryValue.additionalQuestionId) {
                                    answer = dataset.additionalQuestionAnswers?.get(conditionForQueryValue.additionalQuestionId)
                                } else if (dataset.resourceId) {
                                    answer = optimiResourceService.getLocalizedName(datasetService.getResource(dataset))
                                } else if (dataset.answerIds) {
                                    answer = dataset.answerIds?.get(0)
                                    Question question = datasetService.getQuestion(dataset)
                                    if (question && (Constants.InputType.SELECT.toString().equalsIgnoreCase(question.inputType) || Constants.InputType.CHECKBOX.toString().equalsIgnoreCase(question.inputType) || Constants.InputType.RADIO.toString().equalsIgnoreCase(question.inputType))) {
                                        answer = question.getChoices()?.find { answer == it.answerId }?.localizedAnswer
                                    }
                                }
                            }

                            answer = answer.toString()

                            // start evaluating the answer base on the condition to trigger removal
                            if (!answer && conditionForQueryValue.condition == Constants.ValueEvaluation.EMPTY.toString()) {
                                triggerRemoval = true
                            } else if (answer && conditionForQueryValue.condition == Constants.ValueEvaluation.NOTEMPTY.toString()) {
                                triggerRemoval = true
                            } else if (answer && conditionForQueryValue.condition == Constants.ValueEvaluation.POSITIVE.toString()) {
                                try {
                                    Double answerToDouble = answer.toDouble()
                                    triggerRemoval = answerToDouble > 0
                                } catch (Exception e) {
                                    loggerUtil.warn(log, "ERROR: getRemovalTargetPositions in Word report generation: Answer of queryId ${conditionForQueryValue.queryId}, sectionId ${conditionForQueryValue.sectionId}, questionId ${conditionForQueryValue.questionId}, additionalQuestionId ${conditionForQueryValue.additionalQuestionId ?: '<undefined>'} is not a number. It is trying to evaluate if the value is positive", e)
                                    flashService.setWarningAlert("ERROR: getRemovalTargetPositions in Word report generation: Answer of queryId ${conditionForQueryValue.queryId}, sectionId ${conditionForQueryValue.sectionId}, questionId ${conditionForQueryValue.questionId}, additionalQuestionId ${conditionForQueryValue.additionalQuestionId ?: '<undefined>'} is not a number. It is trying to evaluate if the value is positive: ${e.message}", true)
                                }
                            } else if (answer && conditionForQueryValue.condition == Constants.ValueEvaluation.NEGATIVE.toString()) {
                                try {
                                    Double answerToDouble = answer.toDouble()
                                    triggerRemoval = answerToDouble < 0
                                } catch (Exception e) {
                                    loggerUtil.warn(log, "ERROR: getRemovalTargetPositions in Word report generation: Answer of queryId ${conditionForQueryValue.queryId}, sectionId ${conditionForQueryValue.sectionId}, questionId ${conditionForQueryValue.questionId}, additionalQuestionId ${conditionForQueryValue.additionalQuestionId ?: '<undefined>'} is not a number. It is trying to evaluate if the value is negative.", e)
                                    flashService.setWarningAlert("ERROR: getRemovalTargetPositions in Word report generation: Answer of queryId ${conditionForQueryValue.queryId}, sectionId ${conditionForQueryValue.sectionId}, questionId ${conditionForQueryValue.questionId}, additionalQuestionId ${conditionForQueryValue.additionalQuestionId ?: '<undefined>'} is not a number. It is trying to evaluate if the value is negative: ${e.message}", true)
                                }
                            } else if (conditionForQueryValue.condition == Constants.ValueEvaluation.EQUAL.toString() && conditionForQueryValue.refValue) {
                                triggerRemoval = answer == conditionForQueryValue.refValue.toString()
                            } else if (conditionForQueryValue.condition == Constants.ValueEvaluation.NOTEQUAL.toString() && conditionForQueryValue.refValue) {
                                triggerRemoval = answer != conditionForQueryValue.refValue.toString()
                            }
                        }

                        int startRemovalPos = -1

                        // start going through document to look for the markers, the sections will be removed based on the position
                        for (XWPFParagraph paragraph : doc.getParagraphs()) {
                            String paragraphText = paragraph.getText() ?: ''
                            if (paragraphText) {
                                int pos = doc.getPosOfParagraph(paragraph)
                                if (paragraphText.contains(conditionalRemovalRule.removalMarkerStart.toString())) {
                                    // Add the markerStart position to remove the marker
                                    startRemovalPos = pos
                                    removalTargetPositions.add(pos)
                                }
                                if (paragraphText.contains(conditionalRemovalRule.removalMarkerEnd.toString())) {
                                    if (triggerRemoval && startRemovalPos != -1) {
                                        // Add all the elements positions between marker start to marker end if they need to be removed.
                                        removalTargetPositions += startRemovalPos + 1..pos
                                    } else {
                                        // Add only the marker position to remove the marker. Nothing else to be removed
                                        removalTargetPositions.add(pos)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "ERROR: Word Generation: Error in getRemovalTargetPositions:", e)
            flashService.setErrorAlert("ERROR: Word Generation: Error in getRemovalTargetPositions: ${e.message}", true)
        }

        return removalTargetPositions.toSet().sort().toList()
    }

    private File getChartImage(ReportGenerationRuleContent ruleContent) {
        File image
        Map<String, File> images = WebUtils.retrieveGrailsWebRequest()?.session?.getAttribute(Const.SessionAttribute.WORD_DOC_GRAPHS.attribute)

        if (images && "image".equals(ruleContent.type) && ruleContent.uiIdentification) {
            for (String identification in ruleContent.uiIdentification) {
                image = images.find({ it.key.contains(identification) })?.value

                if (image) {
                    break
                }
            }
        }
        return image

    }

    private String getTextValueForReportGenerationRuleContent(Entity parentEntity, Entity childEntity,
                                                              ReportGenerationRuleContent reportGenerationRuleContent,
                                                              Indicator indicator, Boolean keepOriginalAnswer = Boolean.FALSE,
                                                              String reportGenerationRuleId) {
        String returnable = ""
        final String DATE_FORMAT_ONE = "dd.MM.yyy"

        if ("REPLACE-DATE-CREATION".equals(reportGenerationRuleContent.targetMarker)) {
            returnable = getFormattedDate(DATE_FORMAT_ONE, childEntity.dateCreated)
        } else if ("REPLACE-DATE-MODIFICATION".equals(reportGenerationRuleContent.targetMarker)) {
            returnable = getFormattedDate(DATE_FORMAT_ONE, new Date())
        } else if ("REPLACE-DATE-FIN-VALIDITE".equals(reportGenerationRuleContent.targetMarker)) {
            Date endOfValidityDate = new Date()
            use (TimeCategory) {
                endOfValidityDate = endOfValidityDate + END_OF_VALIDITY_DATE_YEARS.years
            }
            returnable = getFormattedDate(DATE_FORMAT_ONE, endOfValidityDate)
        } else if ("REPLACE-IDENTIFIANT-UNIQUE-CALCUL".equals(reportGenerationRuleContent.targetMarker)) {
            returnable = generateUniqueIDForEPD(childEntity.id.toString(), indicatorService.getLocalizedName(indicator))
        } else if ("REPLACE-NAME".equals(reportGenerationRuleContent.targetMarker)) {
            if ("parent".equals(reportGenerationRuleContent.entity)) {
                returnable = parentEntity.name
            } else if ("child".equals(reportGenerationRuleContent.entity)) {
                returnable = childEntity.name
            }
        } else if ("REPLACE-USERNAME".equals(reportGenerationRuleContent.targetMarker) || "REPLACE-USER NAME".equals(reportGenerationRuleContent.targetMarker)) {
            returnable = userService.getCurrentUser().getName()
        } else if ("REPLACE-DATE".equals(reportGenerationRuleContent.targetMarker)) {
            returnable = getFormattedDate(DATE_FORMAT_ONE, new Date())
        } else if ("REPLACE-SCOPE".equals(reportGenerationRuleContent.targetMarker)) {

            //the below new condition --&& ("epdHubToolReport".equals(reportGenerationRuleId))-- has been added because
            // the scope feature is different between regular EPD report and EPD Hub tool report
            if (Constants.EPD_HUB_REPORT_RULEID.equalsIgnoreCase(reportGenerationRuleId)) {
                String scopeId = childEntity?.scope?.scopeId ?: Constants.DEFAULT_EPD_SCOPE
                returnable = scopeFactoryService.getServiceImpl(parentEntity?.entityClass).getScopeIdMessage(scopeId)
            } else {
                String scopeKey = "EPD.${reportGenerationRuleId}.${childEntity?.scope?.scopeId ?: Constants.DEFAULT_EPD_SCOPE}"
                try {
                    returnable = messageSource.getMessage(scopeKey, null, LocaleContextHolder.getLocale())
                } catch (NoSuchMessageException e) {
                    loggerUtil.warn(log, "No text found for message: ${scopeKey} during EPD Word export for target marker 'REPLACE-SCOPE'")
                }
            }

        } else if (reportGenerationRuleContent.targetMarker.startsWith("REPLACE-SCOPE-")) {
            String stageToReplace = reportGenerationRuleContent.targetMarker.substring(SCOPE_TARGET_MARKET_STAGE_START_INDEX)
            returnable = scopeFactoryService.getServiceImpl(parentEntity?.entityClass).scopeContainsStage(stageToReplace)
        } else if ("indicator".equals(reportGenerationRuleContent.source)) {
            try {
                if (reportGenerationRuleContent.calculationRuleId) {
                    CalculationRule calculationRule = indicator.getResolveCalculationRules(parentEntity)?.find({
                        reportGenerationRuleContent.calculationRuleId.equals(it.calculationRuleId)
                    })
                    returnable = calculationRule[reportGenerationRuleContent.attribute]?.toString()
                } else if (reportGenerationRuleContent.resultCategoryId) {
                    ResultCategory resultCategory = indicator.getResolveResultCategories(parentEntity)?.find({
                        reportGenerationRuleContent.resultCategoryId.equals(it.resultCategoryId)
                    })
                    returnable = resultCategory[reportGenerationRuleContent.attribute]?.toString()
                } else {
                    returnable = indicator[reportGenerationRuleContent.attribute]?.toString()
                }
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting value from indicator ${indicator.indicatorId} with specification: " +
                        "calculationRuleId: ${reportGenerationRuleContent.calculationRuleId}, " +
                        "resultCategoryId: ${reportGenerationRuleContent.resultCategoryId}, " +
                        "attribute: ${reportGenerationRuleContent.attribute}", e)
                flashService.setErrorAlert("Error in getting value from indicator ${indicator.indicatorId} with specification: " +
                        "calculationRuleId: ${reportGenerationRuleContent.calculationRuleId}, " +
                        "resultCategoryId: ${reportGenerationRuleContent.resultCategoryId}, " +
                        "attribute: ${reportGenerationRuleContent.attribute}: ${e.message}", true)
            }
        } else {
            Dataset dataset

            if ("parent".equals(reportGenerationRuleContent.entity)) {
                dataset = parentEntity.datasets?.find({
                    reportGenerationRuleContent.queryId.equals(it.queryId) &&
                            reportGenerationRuleContent.sectionId.equals(it.sectionId) && reportGenerationRuleContent.questionId.equals(it.questionId)
                })
            } else if ("child".equals(reportGenerationRuleContent.entity)) {
                dataset = childEntity.datasets?.find({
                    reportGenerationRuleContent.queryId.equals(it.queryId) &&
                            reportGenerationRuleContent.sectionId.equals(it.sectionId) && reportGenerationRuleContent.questionId.equals(it.questionId)
                })
            }

            if (dataset) {
                if (reportGenerationRuleContent.additionalQuestionId) {
                    returnable = dataset.additionalQuestionAnswers?.get(reportGenerationRuleContent.additionalQuestionId)
                } else if (dataset.resourceId) {
                    returnable = optimiResourceService.getLocalizedName(datasetService.getResource(dataset))
                } else if (dataset.answerIds) {
                    returnable = dataset.answerIds.get(0)
                    Question question = datasetService.getQuestion(dataset)

                    if (!keepOriginalAnswer && question && ("select".equalsIgnoreCase(question.inputType) ||
                            "checkbox".equalsIgnoreCase(question.getInputType()) || "radio".equalsIgnoreCase(question.getInputType()))) {
                        returnable = question.getChoices()?.find({ returnable.equals(it.answerId) })?.localizedAnswer
                    }
                }
            }
        }
        return returnable
    }

    /**
     * Returns formatted date based on the pattern given
     * @param pattern
     * @param date
     * @return
     */

    String getFormattedDate(String pattern, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(pattern)
        return dateFormat.format(date ?: new Date())
    }

    /**
     * Genarating a unique id for the epd
     * @param childEntityId
     * @param indicatorName
     * @return
     */
    String generateUniqueIDForEPD(String childEntityId, String indicatorName) {
        return childEntityId + "_" + indicatorName + "_" + new Date().getTime()
    }

    private Double getResultValueForReportGenerationRuleContent(ReportGenerationRuleContent reportGenerationRuleContent, List<CalculationResult> resultsByIndicator) {
        Double result

        if (reportGenerationRuleContent.calculationRuleId && reportGenerationRuleContent.resultCategoryId) {
            result = resultsByIndicator?.find({
                reportGenerationRuleContent.calculationRuleId.equals(it.calculationRuleId) && reportGenerationRuleContent.resultCategoryId.equals(it.resultCategoryId)
            })?.result
        }
        return result
    }

    private void addTableByReportGenerationRuleContent(Entity childEntity, Indicator indicator,
                                                       XWPFDocument document, ReportGenerationRuleContent reportGenerationRuleContent,
                                                       List<CalculationResult> resultsByIndicator, List<Resource> resources) {
        if (reportGenerationRuleContent.source == Constants.BETIE_DATASOURCES_TABLE) {
            sourceListingAsWordTable(childEntity, indicator, reportGenerationRuleContent.targetMarker, document, resultsByIndicator, reportGenerationRuleContent.filterByResultCategory, resources, reportGenerationRuleContent.exportTransportResourceInfo, reportGenerationRuleContent.localizedHeaders)
        } else if ("resultTable".equals(reportGenerationRuleContent.source)) {
            resultsAsWordTable(childEntity, indicator, reportGenerationRuleContent.targetMarker, document)
        }
    }

    private void resultsAsWordTable(Entity childEntity, Indicator indicator, String targetMarker, XWPFDocument document) {
        if (childEntity && indicator && targetMarker && document) {
            Entity parentEntity = childEntity.parentById
            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parentEntity)
            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(parentEntity)
            List<CalculationResult> resultsByIndicatorId = childEntity.getCalculationResultObjects(indicator?.indicatorId, null, null)

            if (calculationRules && resultCategories && resultsByIndicatorId) {
                XWPFTable table = replaceTextWithTable(targetMarker, document)

                if (table) {
                    CTTbl cttbl = table.getCTTbl();
                    CTTblPr pr = cttbl.getTblPr();
                    CTTblWidth tblW = pr.getTblW();
                    tblW.setW(BigInteger.valueOf(5000));
                    tblW.setType(STTblWidth.PCT);
                    pr.setTblW(tblW);
                    cttbl.setTblPr(pr);
                    List<String> headers = ["", localizedHeaderForWordReport("resultCategory.name")]
                    boolean hasClarification = false

                    for (ResultCategory resultCategory in resultCategories) {
                        if (getResultCategoryClarificationText(resultCategory, childEntity)) {
                            hasClarification = true
                            break
                        }
                    }

                    if (hasClarification) {
                        headers.add(localizedHeaderForWordReport("entity.show.task.description"))
                    }

                    calculationRules?.each { CalculationRule rule ->
                        headers.add(rule?.localizedName + "\n${calculationRuleService.getLocalizedUnit(rule, indicator)} ${rule?.unitAsValueReference && valueReferenceService.getValueForEntity(rule.unitAsValueReference, childEntity, Boolean.FALSE, Boolean.TRUE) ? valueReferenceService.getValueForEntity(rule.unitAsValueReference, childEntity, Boolean.FALSE, Boolean.TRUE) : ''}")
                    }
                    XWPFTableCell headerCell
                    XWPFTableRow headerRow = table.getRow(0)

                    for (int i = 0; i < headers.size(); i++) {
                        String header = headers.get(i)

                        if (i == 0) {
                            headerCell = headerRow.getCell(0)
                            headerCell.setText("")
                        } else {
                            headerCell = headerRow.addNewTableCell()
                        }
                        setWordCellHeaderText(headerCell, header)
                    }
                    headerRow.setRepeatHeader(true)
                    int contentSize

                    resultCategories.each { ResultCategory category ->
                        boolean hasResult = false
                        boolean hasApplyConditionData = false

                        if (calculationRules && resultsByIndicatorId) {
                            for (CalculationRule calcRule : calculationRules) {
                                if (resultsByIndicatorId?.find({
                                    category.resultCategoryId.equals(it.resultCategoryId) && calcRule.calculationRuleId.equals(it.calculationRuleId)
                                })?.result) {
                                    hasResult = true
                                    break
                                }
                            }
                        }

                        if (category.applyConditions?.dataPresent && hasResult) {
                            hasApplyConditionData = true
                        }

                        if (!category.applyConditions || hasApplyConditionData) {
                            XWPFTableRow contentRow = table.createRow()
                            contentRow.setCantSplitRow(true)
                            List<String> content = []

                            if (!indicator?.hideResultCategoryLabels) {
                                content.add(category.resultCategory)
                                content.add(category.localizedName)
                            } else {
                                content.add("")
                                content.add(category.localizedName)
                            }

                            calculationRules.each { CalculationRule rule ->
                                Double score
                                boolean hideResult = category.hideResultInReport != null && (category.hideResultInReport.size() == 0 ||
                                        category.hideResultInReport.contains(rule.calculationRuleId))

                                if (resultsByIndicatorId && !rule.skipRuleForCategories?.contains(category.resultCategoryId) &&
                                        !hideResult) {
                                    if (hasClarification) {
                                        String clarificationText = getResultCategoryClarificationText(category, childEntity)
                                        content.add(clarificationText)
                                    }
                                    score = resultsByIndicatorId?.find({
                                        category.resultCategoryId.equals(it.resultCategoryId) && rule.calculationRuleId.equals(it.calculationRuleId)
                                    })?.result

                                    if (score != null) {
                                        hasResult = true
                                    }
                                    String formattedScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, score, SecurityRequestHolder.getRequest().getSession(false), Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "", null, null, null, null, false, true)
                                    content.add(formattedScore)
                                } else {
                                    content.add("")
                                }
                            }

                            for (int i = 0; i < content.size(); i++) {
                                String cellContent = content.get(i)
                                XWPFTableCell contentCell = contentRow.getCell(i)
                                setWordCellSmallText(contentCell, cellContent)
                            }
                            contentSize = content.size()
                        }
                    }

                    if (contentSize > 0) {
                        table.createRow()
                        XWPFTableRow totalsRow = table.createRow()
                        totalsRow.setCantSplitRow(true)

                        totalsRow.getTableCells().each {
                            it.setColor("92D050")
                        }
                        XWPFTableCell totalLabelCell = totalsRow.getCell(1)
                        setWordCellHeaderText(totalLabelCell, localizedHeaderForWordReport("totalScore"))

                        for (int i = 0; i < calculationRules.size(); i++) {
                            XWPFTableCell totalByRuleCell = totalsRow.getCell(2 + i)
                            setWordCellHeaderText(totalByRuleCell, resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(),
                                    indicator, null, childEntity.getTotalResult(indicator.indicatorId,
                                    calculationRules.get(i).calculationRuleId), SecurityRequestHolder.getRequest().getSession(false),
                                    Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "", null, null, null, null, false, true))
                        }
                        getResultsPerDenominators(childEntity, indicator, calculationRules, table)
                    }
                }
            }
        }
    }

    private void getResultsPerDenominators(Entity childEnity, Indicator indicator, List<CalculationRule> calculationRules,
                                           XWPFTable table) {
        if (childEnity && indicator && calculationRules) {
            List<Denominator> denominators = indicator.denominatorListAsDenominatorObjects

            if (denominators) {
                NumberFormat numberFormat = new DecimalFormat("#.##")
                table.createRow()
                XWPFTableRow headerRow = table.createRow()

                headerRow.getTableCells()?.each {
                    it.setColor("92D050")
                }
                XWPFTableCell headerCell = headerRow.getCell(1)
                setWordCellHeaderText(headerCell, localizedHeaderForWordReport("results.denominators"))

                for (Denominator denominator : denominators) {
                    if (!denominator) {
                        continue
                    }
                    if ("dynamicDenominator".equals(denominator.denominatorType)) {
                        List<Dataset> datasets = denominatorService.getDynamicDenominatorDatasets(childEnity, denominator)

                        ResourceCache resourceCache = ResourceCache.init(datasets)
                        for (Dataset dataset: datasets) {
                            Resource resource = resourceCache.getResource(dataset)
                            XWPFTableRow denominatorRow = table.createRow()
                            denominatorRow.setCantSplitRow(true)
                            XWPFTableCell contentCell = denominatorRow.getCell(1)
                            setWordCellSmallText(contentCell, "${optimiResourceService.getLocalizedName(resource)} ${dataset?.quantity} ${dataset?.userGivenUnit ? dataset?.userGivenUnit : resource?.unitForData ? resource?.unitForData : ''}")
                            int cellIndex = 2

                            calculationRules.each { CalculationRule calculationRule ->
                                Double value = childEnity.getResultByDynamicDenominator(indicator.indicatorId,
                                        calculationRule.calculationRuleId, dataset.manualId, Boolean.FALSE, denominator)

                                if (value != null) {
                                    String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(),
                                            indicator, denominator, value, SecurityRequestHolder.getRequest().getSession(false), Boolean.FALSE,
                                            Boolean.FALSE, Boolean.TRUE, "", null, null, null, null, false, true)
                                    XWPFTableCell scoreCell = denominatorRow.getCell(cellIndex)
                                    setWordCellSmallText(scoreCell, scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(value))
                                }
                                cellIndex++
                            }
                        }
                    } else {
                        String unit = denominatorUtil.getLocalizedUnit(denominator)
                        XWPFTableRow denominatorRow = table.createRow()
                        denominatorRow.setCantSplitRow(true)
                        XWPFTableCell denominatorNameCell = denominatorRow.getCell(1)
                        setWordCellSmallText(denominatorNameCell, denominator.localizedName)
                        String scorePerDenominator
                        int cellIndex = 2

                        calculationRules.each { CalculationRule calculationRule ->
                            scorePerDenominator = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(),
                                    indicator, null, childEnity.getTotalResultByNonDynamicDenominator(indicator?.indicatorId, calculationRule.calculationRuleId,
                                    denominator.denominatorId), SecurityRequestHolder.getRequest().getSession(false), Boolean.FALSE,
                                    Boolean.FALSE, Boolean.TRUE, unit, null, null, null, null, false, true)
                            XWPFTableCell scoreCell = denominatorRow.getCell(cellIndex)
                            setWordCellSmallText(scoreCell, scorePerDenominator ? scorePerDenominator + " " + (unit ? unit : '') : "")
                            cellIndex++
                        }
                    }
                }
            }
        }
    }

    private String getResultCategoryClarificationText(ResultCategory resultCategory, Entity entity) {
        String clarification = ""

        if (resultCategory && entity) {
            List<ValueReference> clarificationAsValueReferences = resultCategory.clarificationAsValueReferences

            if (clarificationAsValueReferences) {
                clarificationAsValueReferences.each { ValueReference valueReference ->
                    String answer = valueReferenceService.getValueForEntity(valueReference, entity, null, null)

                    if (answer) {
                        if (clarification) {
                            clarification = clarification + ", " + answer
                        } else {
                            clarification = answer
                        }
                    }
                }
            }
        }
        return clarification
    }

    private void sourceListingAsWordTable(Entity entity, Indicator indicator, String textToReplace, XWPFDocument document,
                                          List<CalculationResult> resultsByIndicator, List<String> filterByResultCategory, List<Resource> resources, Boolean exportTransportResourceInfo, Boolean localizedHeaders) {
        if (resources && indicator && entity) {
            //XmlCursor cursor = paragraph.getCTP().newCursor()
            XWPFTable table = replaceTextWithTable(textToReplace, document)
            List<String> additionalQuestionsToShow = indicator?.resolveApplicationResourceExtraInformationSet?.addToDataSources
            List<Question> questionsToShow

            if (additionalQuestionsToShow) {
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                questionsToShow = additionalQuestionsQuery?.getAllQuestions()?.findAll({ Question question -> additionalQuestionsToShow.contains(question.questionId) })
            }

            if (table) {
                CTTbl cttbl = table.getCTTbl();
                CTTblPr pr = cttbl.getTblPr();
                CTTblWidth tblW = pr.getTblW();
                tblW.setW(BigInteger.valueOf(5000));
                tblW.setType(STTblWidth.PCT);
                pr.setTblW(tblW);
                cttbl.setTblPr(pr);
                // table.getCTTbl().getTblPr().getTblW().setW(BigInteger.valueOf(5000))
                List<String> headers = []
                List<String> newHeadings = []
                //[message(code: 'resource.name'),]
                String userLanguage = userService.getCurrentUser()?.language ?: "EN"
                Map sourceListing = filterByResultCategory ? indicator?.resolveResourceExtraInformationBgReport : indicator?.resolveResourceExtraInformationResultPage
                def sourceListingAttributes = sourceListing?.keySet()?.toList()

                if (sourceListingAttributes) {
                    if (exportTransportResourceInfo) {
                        headers = addHeadersForExportingTransportResourceInfo(headers, localizedHeaders)
                    } else if (filterByResultCategory) {
                        headers.add(localizedHeaderForWordReport("stage", localizedHeaders))
                        headers.add(localizedHeaderForWordReport("resource.name", localizedHeaders))

                        if (questionsToShow) {
                            // inject quantity, unit, comment from dataset to table
                            newHeadings = questionsToShow.collect { it.question?.get("EN") }

                            if (newHeadings) {
                                newHeadings.forEach({ String heading ->
                                    heading.capitalize()
                                    headers.add(heading.capitalize())
                                })
                            }
                        }
                        sourceListingAttributes.each { String resourceAttribute ->
                            Map localizedHeading = sourceListing.get(resourceAttribute)
                            String heading = localizedHeading.get("EN")
                            headers.add(heading)
                        }
                    } else {
                        headers.add(localizedHeaderForWordReport("resource.name", localizedHeaders))
                        sourceListingAttributes.each { String resourceAttribute ->
                            Map localizedHeading = sourceListing.get(resourceAttribute)
                            String heading = localizedHeading.get("EN")
                            headers.add(heading)
                        }
                    }

                } else {
                    headers.addAll([localizedHeaderForWordReport("resource.name", localizedHeaders),
                                    localizedHeaderForWordReport("resource.environmentDataSourceType", localizedHeaders),
                                    localizedHeaderForWordReport("resource.verifiersName", localizedHeaders),
                                    localizedHeaderForWordReport("resource.verificationNumber", localizedHeaders),
                                    localizedHeaderForWordReport("resource.verificationDate", localizedHeaders)])
                }
                XWPFTableCell headerCell
                XWPFTableRow headerRow = table.getRow(0)

                for (int i = 0; i < headers.size(); i++) {
                    String header = headers.get(i)

                    if (i == 0) {
                        headerCell = headerRow.getCell(0)
                    } else {
                        headerCell = headerRow.addNewTableCell()
                    }
                    setWordCellHeaderText(headerCell, header)
                }
                headerRow.setRepeatHeader(true)

                if (filterByResultCategory) {
                    List<ResultCategory> resultCategories = indicator.getResolveResultCategories(entity)
                    filterByResultCategory.each { String resultCategoryId ->
                        List<CalculationResult> calculationResults = resultsByIndicator.findAll({ it.resultCategoryId.equals(resultCategoryId) && it.calculationRuleId.equals(indicator.displayResult) })

                        if (calculationResults) {
                            calculationResults.each { calculationResult ->
                                Set<Dataset> calculationResultDatasets = calculationResultService.getDatasets(calculationResult, entity)
                                List<String> allowedManualIds = calculationResultDatasets?.collect({ it.manualId })
                                Set<Dataset> datasets = []
                                if (indicator.isResourceAggregationDisallowed) {
                                    // finding constituents and linking them to their constructions
                                    datasets = calculationResultDatasets?.findAll { it?.resourceId }
                                            ?.each { datasetService.setParentConstructionDataset(it, calculationResultDatasets) }
                                    // explicitly adding constructions to the dataset list in case they are not there
                                    calculationResultDatasets?.findAll { it?.parentConstructionDataset }
                                            ?.collect(datasets) { it?.parentConstructionDataset }
                                    // sorting the dataset list
                                    datasets = datasets?.toSorted(new ResultListDatasetConstructionComparator())
                                } else {
                                    datasets = calculationResultDatasets
                                }
                                String resultCategory = resultCategories.find({ it.resultCategoryId.equals(resultCategoryId) }).resultCategory

                                if (exportTransportResourceInfo && allowedManualIds) {
                                    injectTransportResourceInfoTable(resources, allowedManualIds, datasets, table, indicator.isResourceAggregationDisallowed)
                                } else if (allowedManualIds) {
                                    if (indicator.isResourceAggregationDisallowed) {
                                        ResourceCache resourceCache = ResourceCache.init(datasets.toList())
                                        for (Dataset ds : datasets) {
                                            Resource resource = resourceCache.getResource(ds)
                                            if (resource && ds) {
                                                datasetService.setLinkedDatasetManualIds(ds, resource)
                                                List<String> content = createWordContentForResource(resource, [ds] as Set, sourceListingAttributes, resultCategory, !newHeadings.isEmpty(), indicator, questionsToShow)
                                                putContentIntoWordRow(content, table, ds.construction, ds.constituent)
                                            }
                                        }
                                    } else {
                                        resources.each { Resource resource ->
                                            if (CollectionUtils.containsAny(allowedManualIds, resource.linkedDatasetManualIds)) {
                                                List<String> content = createWordContentForResource(resource, datasets, sourceListingAttributes, resultCategory, !newHeadings.isEmpty(), indicator, questionsToShow)
                                                putContentIntoWordRow(content, table)
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            table.removeRow(0)
                        }
                    }
                } else {
                    resources.each { Resource r ->
                        XWPFTableRow contentRow = table.createRow()
                        contentRow.setCantSplitRow(true)
                        List<String> content = []

                        if (sourceListingAttributes) {
                            content.add(optimiResourceService.getLocalizedName(r))
                            sourceListingAttributes.each { String resourceAttribute ->
                                def value = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, r)
                                content.add(value ?: "")
                            }
                        } else {
                            content.add(optimiResourceService.getLocalizedName(r))
                            content.add(r?.environmentDataSourceType ?: "")
                            content.add(r?.verifiersName ?: "")
                            content.add(r?.verificationNumber ?: "")
                            content.add(r?.verificationDate ?: "")
                        }

                        for (int i = 0; i < content.size(); i++) {
                            String cellContent = content.get(i)
                            XWPFTableCell contentCell = contentRow.getCell(i)
                            setWordCellSmallText(contentCell, cellContent)
                        }
                    }
                }
            }
        }
    }

    /**
     * Check ticket 15602 to understand this. Note, we use this for other table as well, not just A2.
     * Used for exporting the legs of transportation datasets in background report. It doesn't follow the logic pattern of others hence hardcode
     * @param headers
     * @return
     */
    private List<String> addHeadersForExportingTransportResourceInfo(List<String> headers, Boolean localizedHeaders = false) {
        headers?.addAll([
                localizedHeaderForWordReport("resource.name", localizedHeaders),
                localizedHeaderForWordReport("query.quantity", localizedHeaders),
                localizedHeaderForWordReport("bgReport.A2.distance", localizedHeaders),
                localizedHeaderForWordReport("bgReport.A2.leg", localizedHeaders),
                localizedHeaderForWordReport("transport", localizedHeaders),
                localizedHeaderForWordReport("bgReport.A2.dataSource", localizedHeaders),
                localizedHeaderForWordReport("resource.date", localizedHeaders),
                localizedHeaderForWordReport("attachment.comment", localizedHeaders),
                localizedHeaderForWordReport("bgReport.A2.dataQuality", localizedHeaders)
        ])
        return headers
    }

    /**
     * Check ticket 15602 to understand this. Note, we use this for other table as well, not just A2.
     * Used for exporting the legs of transportation datasets in background report. It doesn't follow the logic pattern of others hence hardcode
     * @param resources
     * @param allowedManualIds list of dataset manualIds that are linked with the calculation result
     * @param datasets
     * @param table
     */
    private void injectTransportResourceInfoTable(List<Resource> resources, List<String> allowedManualIds, Set<Dataset> datasets,
                                                  XWPFTable table, Boolean isResourceAggregationDisallowed) {
        try {
            if (table) {
                resources?.each { Resource r ->
                    if (CollectionUtils.containsAny(allowedManualIds, r.linkedDatasetManualIds)) {
                        // When we have multiple resources in A2, the datasets of resources with same profileId and resourceId will be combined into the linkedDatasetManualIds
                        // In this A2 table, each of it is one resourceDataset
                        datasets?.findAll({ r.linkedDatasetManualIds.contains(it.manualId) })?.each { Dataset resourceDataset ->
                            // Each resource dataset would have 2 rows, one for leg 1 and the other for leg 2. Only display the legs that have valid value
                            String distanceLeg1 = resourceDataset?.additionalQuestionAnswers?.get(Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMECO36.toString())
                            String distanceLeg2 = resourceDataset?.additionalQuestionAnswers?.get(Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMECO36LEG2.toString())
                            boolean validLeg1 = distanceLeg1 && distanceLeg1.replace(',', '.').isNumber() && distanceLeg1.replace(',', '.').toDouble() != 0
                            boolean validLeg2 = distanceLeg2 && distanceLeg2.replace(',', '.').isNumber() && distanceLeg2.replace(',', '.').toDouble() != 0

                            String quantityAndUnit = "${resourceDataset?.calculatedMass != null ? resourceDataset?.calculatedMass : resourceDataset?.calculatedQuantity != null ? resourceDataset?.calculatedQuantity : ''} ${resourceDataset?.calculatedUnit ?: ''}"

                            String resourceName = optimiResourceService.getLocalizedName(r) ?: ''

                            if (validLeg1) {
                                String transportResourceIdLeg1 = resourceDataset?.additionalQuestionAnswers?.get(Constants.TransportDistanceQuestionId.TRANSPORT_KMECO36_RESOURCEID.toString())
                                Resource transportResourceLeg1 = optimiResourceService.getResourceByResourceAndProfileId(transportResourceIdLeg1, null)
                                List<String> firstContents = [
                                        resourceName,
                                        quantityAndUnit,
                                        distanceLeg1,
                                        '1',
                                        optimiResourceService.getLocalizedName(transportResourceLeg1) ?: '',
                                        transportResourceLeg1?.environmentDataSource ?: '',
                                        transportResourceLeg1?.environmentDataPeriod?.toString() ?: '',
                                        resourceDataset?.additionalQuestionAnswers?.get('comment')?.toString() ?: '',
                                        ''
                                ]
                                putContentIntoWordRow(firstContents, table,
                                        isResourceAggregationDisallowed && resourceDataset.construction,
                                        isResourceAggregationDisallowed && resourceDataset.constituent)

                                // for constituent the resource name should also contain the construction name, e.g. "Steel GD 29122021 / Steel production, converter, low-alloyed"
                                if (isResourceAggregationDisallowed && resourceDataset.constituent) {
                                    String constructionResourceName = optimiResourceService.getParentConstructionResourceName(resourceDataset)
                                    // overwriting the resource name (last row, first cell in the row)
                                    setTransportationResourceName(table.getRow(table.getNumberOfRows() - 1).getCell(0), constructionResourceName, resourceName)
                                }
                            }

                            if (validLeg2) {
                                String transportResourceIdLeg2 = resourceDataset?.additionalQuestionAnswers?.get(Constants.TransportDistanceQuestionId.TRANSPORT_KMECO36LEG2_RESOURCEID.toString())
                                Resource transportResourceLeg2 = optimiResourceService.getResourceByResourceAndProfileId(transportResourceIdLeg2, null)
                                List<String> secondContents = [
                                        validLeg1 ? '' : resourceName ?: '',
                                        validLeg1 ? '' : quantityAndUnit,
                                        distanceLeg2,
                                        '2',
                                        optimiResourceService.getLocalizedName(transportResourceLeg2) ?: '',
                                        transportResourceLeg2?.environmentDataSource ?: '',
                                        transportResourceLeg2?.environmentDataPeriod?.toString() ?: '',
                                        validLeg1 ? '' : resourceDataset?.additionalQuestionAnswers?.get('comment')?.toString() ?: '',
                                        ''
                                ]
                                putContentIntoWordRow(secondContents, table,
                                        isResourceAggregationDisallowed && resourceDataset.construction,
                                        isResourceAggregationDisallowed && resourceDataset.constituent)

                                if (!validLeg2 && isResourceAggregationDisallowed && resourceDataset.constituent) {
                                    String constructionResourceName = optimiResourceService.getParentConstructionResourceName(resourceDataset)
                                    setTransportationResourceName(table.getRow(table.getNumberOfRows() - 1).getCell(0), constructionResourceName, resourceName)
                                }
                            }
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, 'Error in exporting transport resource info for report', e)
            flashService.setErrorAlert("Error in exporting transport resource info for report: ${e.getMessage()}", Boolean.TRUE)
        }
    }

    private XWPFTable replaceTextWithTable(String text, XWPFDocument doc) {
        long count = 0
        XWPFTable table

        if (text) {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns()
                TextSegment found = paragraph.searchText(text, new PositionInParagraph())

                if (found != null) {
                    count++

                    if (found.getBeginRun() == found.getEndRun()) {
                        // whole search string is in one Run
                        XmlCursor cursor = paragraph.getCTP().newCursor()
                        table = doc.insertNewTbl(cursor)
                        XWPFRun run = runs.get(found.getBeginRun())
                        String runText = run.getText(run.getTextPosition());
                        String replaced = runText.replace(text, "")
                        run.setText(replaced, 0)
                    } else {
                        // The search string spans over more than one Run
                        StringBuilder b = new StringBuilder()

                        for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                            XWPFRun run = runs.get(runPos);
                            b.append(run.getText(run.getTextPosition()))
                        }
                        String connectedRuns = b.toString()
                        XmlCursor cursor = paragraph.getCTP().newCursor()
                        table = doc.insertNewTbl(cursor)
                        String replaced = connectedRuns.replace(text, "") // Clear search text
                        // The first Run receives the replaced String of all connected Runs
                        XWPFRun partOne = runs.get(found.getBeginRun())
                        partOne.setText(replaced, 0)
                        // Removing the text in the other Runs.

                        for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                            XWPFRun partNext = runs.get(runPos)
                            partNext.setText("", 0)
                        }
                    }
                }
            }
        }
        return table
    }

    private void replaceTextWithImage(File image, XWPFDocument doc, String targetMarker, Integer imageWidth, Integer imageHeight, String imageType) {
        long count = 0

        // first get all the main paragraphs
        List<XWPFParagraph> allPossibleParagraphsToLoopThrough = doc.getParagraphs() ?: []

        try {
            // get paragraphs from headers, and check also in bodyElements just in case
            for (XWPFHeader header : doc.getHeaderList()) {
                if (header.getParagraphs()) {
                    allPossibleParagraphsToLoopThrough += header.getParagraphs()
                } else {
                    for (IBodyElement paragraph : header.getBodyElements()) {
                        allPossibleParagraphsToLoopThrough.add(paragraph as XWPFParagraph)
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in replaceTextWithImage while trying to get headers for targetMarker ${targetMarker}:", e)
            flashService.setErrorAlert("Error occurred in replaceTextWithImage while trying to get headers for targetMarker ${targetMarker}: ${e.message}", true)
        }

        try {
            // hackish way to get paragraphs from footer, for some reason sometimes footer doesn't have paragraphs
            // therefore need to try looking into the bodyElement which is weird that it doesn't have the content in IBodyElement interface
            // not 100% sure if this will always work.
            // will try to have a more solid fix after the release
            for (XWPFFooter footer : doc.getFooterList()) {
                if (footer.getParagraphs()) {
                    allPossibleParagraphsToLoopThrough += footer.getParagraphs()
                } else {
                    for (IBodyElement bodyElement : footer.getBodyElements()) {
                        for (XWPFParagraph paragraph : bodyElement.content.bodyElements) {
                            allPossibleParagraphsToLoopThrough.add(paragraph)
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in replaceTextWithImage while trying to get footers for targetMarker ${targetMarker}:", e)
            flashService.setErrorAlert("Error occurred in replaceTextWithImage while trying to get footers for targetMarker ${targetMarker}: ${e.message}", true)
        }

        for (XWPFParagraph paragraph : allPossibleParagraphsToLoopThrough) {

            List<XWPFRun> runs = paragraph.getRuns()
            TextSegment found = paragraph.searchText(targetMarker, new PositionInParagraph())

            if (found != null) {
                int theImageType

                if (imageType) {
                    if ("jpg".equalsIgnoreCase(imageType) || "jpeg".equalsIgnoreCase(imageType)) {
                        theImageType = XWPFDocument.PICTURE_TYPE_JPEG
                    } else if ("png".equalsIgnoreCase(imageType)) {
                        theImageType = XWPFDocument.PICTURE_TYPE_PNG
                    } else if ("gif".equalsIgnoreCase(imageType)) {
                        theImageType = XWPFDocument.PICTURE_TYPE_GIF
                    } else if ("tif".equalsIgnoreCase(imageType) || "tiff".equalsIgnoreCase(imageType)) {
                        theImageType = XWPFDocument.PICTURE_TYPE_TIFF
                    } else {
                        theImageType = XWPFDocument.PICTURE_TYPE_JPEG
                    }
                } else {
                    theImageType = XWPFDocument.PICTURE_TYPE_JPEG
                }
                Dimension scaledDimensions = null
                if (image) {
                    scaledDimensions = imageUtil.calculateScaledDimensionToMaintainAspectRatio(image,
                            imageWidth ?: DEFAULT_WIDTH, imageHeight ?: DEFAULT_HEIGHT)
                }
                imageHeight = (Integer) (scaledDimensions?.height ?: DEFAULT_HEIGHT)
                imageWidth = (Integer) (scaledDimensions?.width ?: DEFAULT_WIDTH)
                InputStream inputStream

                if (image) {
                    try {
                        inputStream = new FileInputStream(image)
                    } catch (ignored) {
                    }
                }
                count++

                if (found.getBeginRun() == found.getEndRun()) {
                    XWPFRun run = runs.get(found.getBeginRun())
                    run.setText("", 0)

                    if (inputStream) {
                        run.addPicture(inputStream, theImageType, image.getName(), Units.toEMU(imageWidth.intValue()), Units.toEMU(imageHeight.intValue()))
                        inputStream.close()
                    }
                } else {
                    StringBuilder b = new StringBuilder()

                    for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                        XWPFRun run = runs.get(runPos);
                        b.append(run.getText(run.getTextPosition()))
                    }
                    XWPFRun partOne = runs.get(found.getBeginRun())
                    partOne.setText("", 0)

                    if (inputStream) {
                        partOne.addPicture(inputStream, theImageType, image.getName(), Units.toEMU(imageWidth.intValue()), Units.toEMU(imageHeight.intValue()))
                        inputStream.close()
                    }

                    for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                        XWPFRun partNext = runs.get(runPos)
                        partNext.setText("", 0)
                    }
                }
            }
        }
    }

    private void deleteChartImages() {
        WebUtils.retrieveGrailsWebRequest()?.session?.removeAttribute(Const.SessionAttribute.WORD_DOC_GRAPHS.attribute)
    }

    private void setWordCellHeaderText(XWPFTableCell cell, String text) {
        cell.removeParagraph(0)
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER)
        XWPFRun run = cell.addParagraph().createRun()
        run.setBold(true)
        run.setFontSize(9)
        run.setColor(Constants.FONT_COLOR_GREEN_HEX)
        text = text?.replaceAll(" ", "\n")
        run.setText(text ?: "")
    }

    private void setWordCellBoldText(XWPFTableCell cell, String text, Boolean appendText = false) {
        if (!appendText) {
            cell.removeParagraph(0)
        }
        XWPFRun run = appendText ? cell.getParagraphArray(0).createRun() : cell.addParagraph().createRun()
        run.setBold(true)
        run.setFontSize(9)
        if (!appendText) {
            run.addBreak()
        }
        text = text?.replaceAll(" ", "\n")
        run.setText(text ?: "")
    }

    private void setWordCellSmallText(XWPFTableCell cell, String text, Boolean appendText = false) {
        if (!appendText) {
            cell.removeParagraph(0)
        }
        XWPFRun run = appendText ? cell.getParagraphArray(0).createRun() : cell.addParagraph().createRun()
        run.setFontSize(9)
        if (!appendText) {
            run.addBreak()
        }
        text = text?.replaceAll(" ", "\n")
        run.setText(text ?: "")

    }

    private void setWordCellBackgroundColor(XWPFTableCell cell, String colorCode) {
        cell.getCTTc().addNewTcPr().addNewShd().setFill(colorCode)
    }

    private String localizedHeaderForWordReport(String headerKey, Boolean localizedHeaders = false) {
        String localizedHeader = ""
        try {
            localizedHeader = localizedHeaders ? messageSource.getMessage(headerKey, null, LocaleContextHolder.getLocale()) : messageSource.getMessage(headerKey, null, null) ?: headerKey
        } catch (Exception e) {
            localizedHeader = headerKey
        }
        return localizedHeader
    }

    private List<String> createWordContentForResource(Resource resource, Set<Dataset> datasets,
                                                      List<String> sourceListingAttributes, String resultCategory,
                                                      Boolean addNewHeadings, Indicator indicator, List<Question> questionsToShow) {
        List<String> content = []

        Set<Dataset> resourceDatasets = datasets.findAll({ resource?.linkedDatasetManualIds?.contains(it.manualId) })

        if (sourceListingAttributes) {
            content.add(resultCategory)
            content.add(resource?.isDummy ? datasets[0]?.groupingDatasetName : optimiResourceService.getLocalizedName(resource))

            questionsToShow.each {
                String answerContent = ""
                if (it.questionId == "quantity") {
                    for (Dataset dataset : resourceDatasets) {
                        String unit = dataset?.userGivenUnit ?: (resource?.unitForData ?: '')
                        answerContent <<= "${dataset?.quantity} $unit\n"
                    }
                } else {
                    List<Document> additionalQuestionResources = it.getAdditionalQuestionResources(indicator, null)
                    answerContent = resourceDatasets.collect { Dataset ds ->
                        ds.additionalQuestionAnswers?.get(it.questionId)
                                ? questionService.getAnswerForUI(
                                ds.additionalQuestionAnswers.get(it.questionId),
                                additionalQuestionResources, it)
                                : ""
                    }?.unique()?.join("; ")
                }
                content.add(answerContent ?: "")
            }

            sourceListingAttributes.each { String resourceAttribute ->
                def value = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource)
                content.add(value ? value.toString().replace("[", "").replace("]", "") : "")
            }
        } else {
            content.add(optimiResourceService.getLocalizedName(resource))
            content.add(resource?.environmentDataSourceType ?: "")
            content.add(resource?.verifiersName ?: "")
            content.add(resource?.verificationNumber ?: "")
            content.add(resource?.verificationDate ?: "")
        }

        return content
    }

    private void putContentIntoWordRow(List<String> content, XWPFTable table, Boolean isConstruction = false, Boolean isConstituent = false) {
        XWPFTableRow contentRow = table.createRow()
        contentRow.setCantSplitRow(true)

        for (int i = 0; i < content.size(); i++) {
            String cellContent = content.get(i)
            XWPFTableCell contentCell = contentRow.getCell(i)
            if (isConstruction) {
                setWordCellBoldText(contentCell, cellContent)
            } else {
                setWordCellSmallText(contentCell, cellContent)
            }

            if (isConstruction) {
                setWordCellBackgroundColor(contentCell, CONSTRUCTION_RESOURCE_HIGHLIGHT_BACKGROUND_COLOR)
            } else if (isConstituent) {
                setWordCellBackgroundColor(contentCell, CONSTRUCTION_CONSTITUENT_HIGHLIGHT_BACKGROUND_COLOR)
            }
        }
    }

    /**
     * Sets the resource name for a constituent. The name consists of the parent construction resource name
     * and the name of the constituent itself, e.g. "Steel GD 29122021 / Steel production, converter, low-alloyed".
     * The construction name is highlighted with a bold font.
     */
    private void setTransportationResourceName(XWPFTableCell cell, String constructionResourceName, String resourceName) {
        setWordCellBoldText(cell, constructionResourceName)
        setWordCellSmallText(cell, " / ${resourceName}", true)
    }

    /**
     * Generates a new list of included scopes for EPD reports,
     * saves the list in the related scopeService class
     * Can be extended to LCA scopes in the future
     * @param parentEntity
     * @param childEntity
     */
    private void generateScopesList(Entity parentEntity, Entity childEntity) {
        if (parentEntity?.entityClass?.equalsIgnoreCase(Constants.EntityClass.PRODUCT.toString())) {
            String scopeId = childEntity?.scope?.scopeId ?: Constants.DEFAULT_EPD_SCOPE
            List<String> predefinedScopeList = scopeFactoryService.getServiceImpl(parentEntity?.entityClass).findByScopeId(scopeId)?.epdScopeList
            scopeFactoryService.getServiceImpl(parentEntity?.entityClass).collectIncludedStages(predefinedScopeList)
        }
    }
}
