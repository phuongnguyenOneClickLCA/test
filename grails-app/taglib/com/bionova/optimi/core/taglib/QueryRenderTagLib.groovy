package com.bionova.optimi.core.taglib

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as Const
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryApplyFilterOnCriteria
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QueryLastUpdateInfo
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionTableFormat
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.QuestionService
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.util.DateUtil
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.converters.JSON
import org.bson.Document
import java.text.DecimalFormat
import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Pasi-Markus Mäkelä
 */
class QueryRenderTagLib {
    static defaultEncodeAs = "raw"
    static namespace = "queryRender"
    def queryService
    def userService
    def datasetService
    def unitConversionUtil
    def resourceFilterCriteriaUtil
    def configurationService
    def constructionService
    def maskingService
    def stringUtilsService
    def xmlService
    def optimiResourceService
    def valueReferenceService
    def additionalQuestionService
    QuestionService questionService
    def importMapperService
    ResourceService resourceService

    def renderIncompleteQueries = { attrs ->
        String toRender
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Indicator indicator = attrs.indicator

        if (entity && indicator && entity.modifiable) {
            Boolean isDesign = com.bionova.optimi.core.Constants.EntityClass.DESIGN.toString().equals(entity.entityClass) ? true : false
            List<Query> incompleteProjectLevelQueries = []
            List<Query> incompleteQueries = []
            List<Query> queries = attrs.queries// ?: indicator.getQueries(entity, Boolean.TRUE)
            List<String> optionalQueryIds = indicator?.indicatorQueries?.findAll({ it.optional })?.collect({ it.queryId })
            Map<String, Boolean> queriesReady = entity.queryReadyPerIndicator?.get(indicator.indicatorId)

            queries?.each { Query query ->
                if (indicator.indicatorQueries?.find({ query.queryId.equals(it.queryId) && it.projectLevel })) {
                    if (!parentEntity?.queryReady?.get(query.queryId)) {
                        incompleteProjectLevelQueries.add(query)
                    }
                } else {
                    if (!optionalQueryIds?.contains(query.queryId)) {
                        if (queriesReady?.get(query.queryId) != null) {
                            if (!queriesReady?.get(query.queryId)) {
                                incompleteQueries.add(query)
                            }
                        } else {
                            if (queriesReady?.get(query.queryId) == null) {
                                entity.resolveQueryReady(indicator, query)
                            }
                            if (!entity.queryReady?.get(query.queryId)) {
                                incompleteQueries.add(query)
                            }
                        }
                    }
                }
            }

            if (incompleteQueries) {
                toRender = "<div class=\"alert alert-error\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                        "<strong>${message(code: 'results.incomplete_queries')}</strong><ul>"
                incompleteProjectLevelQueries.each { Query query ->
                    toRender = "${toRender}<li>${opt.link(controller: 'query', action: 'form', class: 'red', params: [entityId: parentEntity?.id, indicatorId: indicator.indicatorId, queryId: query.queryId, projectLevel: true, designLevelParamQuery: isDesign, periodLevelParamQuery: !isDesign]) { query.localizedName }}</li>"
                }
                incompleteQueries.each { Query query ->
                    toRender = "${toRender}<li>${opt.link(controller: 'query', action: 'form', class: 'red', params: [childEntityId: entity.id, indicatorId: indicator.indicatorId, queryId: query.queryId]) { query.localizedName + " - <span class=\"warningRed\">" + g.message(code:'entity.mandatory_answers_missing') + "</span>"}} </li>"
                }
                toRender = "${toRender}</ul></div>"
            }
        }
        out << toRender
    }

    def missmatchingDataWarning = { attrs ->
        String toRender
        List<Dataset> datasets = attrs.datasets

        if (datasets) {
            List<Dataset> incompatibleUnitDatasets = []

            if (datasets) {
                ResourceCache resourceCache = ResourceCache.init(datasets)
                for (Dataset d: datasets) {
                    if (!d.noQueryRender) {
                        Resource resource = resourceCache.getResource(d)

                        if (resource) {
                            List<String> compatibleUnits = unitConversionUtil.getCompatibleUnitsForResource(resource)

                            if (d.userGivenUnit && compatibleUnits && !compatibleUnits*.toLowerCase()?.contains(d.userGivenUnit.toLowerCase()) && !d.uniqueConstructionIdentifier) {
                                incompatibleUnitDatasets.add(d)
                            }

                        }
                    }
                }
            }

            if (incompatibleUnitDatasets) {
                toRender = "<div class=\"container\"><div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<i class=\"fas fa-exclamation pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                        "<strong>${message(code: 'incompatible_warning_heading', args: [incompatibleUnitDatasets.size()])}</strong><ul>"

                ResourceCache resourceCache = ResourceCache.init(incompatibleUnitDatasets)
                for (Dataset d: incompatibleUnitDatasets) {
                    toRender = "${toRender}<li>${message(code: 'data_incompatible_unit', args: [resourceCache.getResource(d)?.staticFullName, d.userGivenUnit])} </li>"
                }
                toRender = "${toRender}</ul></div></div>"
            }
        }
        out << toRender
    }

    def implausibleThicknessesWarning = { attrs ->
        String toRender

        List <Dataset> datasets = attrs.datasets

        if (datasets) {
            List<Dataset> implausibleThicknesses = []

            List<Dataset> tooHighThickness = datasets.findAll({datasetService.getResource(it) && datasetService.getThickness(it.additionalQuestionAnswers) && datasetService.getResource(it).getResourceTypeObject()?.maxThickness_mm != null && (it.userGivenUnit && it?.userGivenUnit?.equalsIgnoreCase("m2") || it?.userGivenUnit?.equalsIgnoreCase("sq ft"))  && (datasetService.getThickness(it.additionalQuestionAnswers) > datasetService.getResource(it).getResourceTypeObject().maxThickness_mm)})?.unique({ it.manualId })
            List<Dataset> tooLowThickness = datasets.findAll({ datasetService.getResource(it) && datasetService.getThickness(it.additionalQuestionAnswers) && datasetService.getResource(it).getResourceTypeObject()?.minThickness_mm != null && (it.userGivenUnit && it?.userGivenUnit?.equalsIgnoreCase("m2") || it?.userGivenUnit?.equalsIgnoreCase("sq ft"))  && (datasetService.getThickness(it.additionalQuestionAnswers) > 0.0 && datasetService.getThickness(it.additionalQuestionAnswers) < datasetService.getResource(it).getResourceTypeObject().minThickness_mm)})?.unique({ it.manualId })
            List<Dataset> zeroThickness = datasets.findAll({datasetService.getThickness(it.additionalQuestionAnswers) && (it.userGivenUnit && it?.userGivenUnit?.equalsIgnoreCase("m2") || it?.userGivenUnit?.equalsIgnoreCase("sq ft")) && datasetService.getThickness(it.additionalQuestionAnswers)?.toDouble() == 0.0})?.unique({ it.manualId })

            if (tooHighThickness) {
                implausibleThicknesses.addAll(tooHighThickness)
            }
            if (tooLowThickness) {
                implausibleThicknesses.addAll(tooLowThickness)
            }
            if (zeroThickness) {
                implausibleThicknesses.addAll(zeroThickness)
            }

            if (implausibleThicknesses) {
                toRender = "<div class=\"container\"><div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<i class=\"fas fa-exclamation pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                        "<strong>${message(code: 'implausible_thicknesses_warning', args: [implausibleThicknesses.size()])}</strong><ul>"

                ResourceCache resourceCache = ResourceCache.init(tooHighThickness)
                for (Dataset d: tooHighThickness) {
                    toRender = "${toRender}<li>${message(code: 'data_high_thickness', args: [resourceCache.getResource(d)?.staticFullName, datasetService.getThickness(d.additionalQuestionAnswers)])} </li>"
                }

                resourceCache = ResourceCache.init(tooLowThickness)
                for (Dataset d: tooLowThickness) {
                    toRender = "${toRender}<li>${message(code: 'data_low_thickness', args: [resourceCache.getResource(d)?.staticFullName, datasetService.getThickness(d.additionalQuestionAnswers)])} </li>"
                }

                resourceCache = ResourceCache.init(zeroThickness)
                for (Dataset d: zeroThickness) {
                    toRender = "${toRender}<li>${message(code: 'data_zero_thickness', args: [resourceCache.getResource(d)?.staticFullName, datasetService.getThickness(d.additionalQuestionAnswers)])} </li>"
                }

                toRender = "${toRender}</ul></div></div>"
            }
        }
        out << toRender
    }

    def importMapperBimCheckerErrors = { attrs ->
        Dataset d = attrs.dataset
        String toRender = ""
        String errors = ""

        if (d) {
            Boolean error = false

            if (importMapperService.isResourceDataset(d)) {
                Resource r = datasetService.getResource(d)
                ResourceType rt = resourceService.getSubType(r)

                if (!d.quantity || !d.userGivenUnit) {
                    // WARN
                    errors = "${errors ? errors + "<br/>" : ""}No quantity or unrecognized unit"
                }
                if (!d.allowMapping && d.quantity && d.userGivenUnit) {
                    // WARN
                    errors = "${errors ? errors + "<br/>" : ""}${message(code: "importMapper.resolver.generic_datapoint_warning")}"
                }
                if (d.importWarning) {
                    // WARN
                    errors = "${errors ? errors + "<br/>" : ""}${d.importWarning}"
                }
                if (d.importError) {
                    // ERROR
                    errors = "${errors ? errors + "<br/>" : ""}${d.importError}"
                    error = true
                }
                if (d.importMapperCompositeMaterial) {
                    // WARN
                    errors = "${errors ? errors + "<br/>" : ""}${message(code: "importMapper.composite.info")}"
                }
                Double thickness = datasetService.getThickness(d.additionalQuestionAnswers)
                if (rt && thickness && (thickness > rt.maxThickness_mm || thickness < rt.minThickness_mm || thickness == 0.0)) {
                    // ERROR

                    if (thickness == 0.0) {
                        errors = "${errors ? errors + "<br/>" : ""}Material has no thickness even though required by material type"
                    } else if (thickness > rt.maxThickness_mm) {
                        errors = "${errors ? errors + "<br/>" : ""}Implausibly thick object for this material type"
                    } else if (thickness < rt.minThickness_mm) {
                        errors = "${errors ? errors + "<br/>" : ""}Implausibly thin object for this material type"
                    }
                    error = true
                }
            } else {
                if (d.importWarning) {
                    // WARN
                    errors = "${errors ? errors + "<br/>" : ""}${d.importWarning}"
                }
                if (d.importError) {
                    // ERROR
                    errors = "${errors ? errors + "<br/>" : ""}${d.importError}"
                    error = true
                }
            }

            if (errors) {
                toRender = "&nbsp;<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${errors}\">"

                if (error) {
                    toRender = "${toRender}<i class=\"icon-alert\"></i>"
                } else {
                    toRender = "${toRender}${asset.image(src:"img/icon-warning.png", style:"max-width:16px")}"
                }
                toRender = "${toRender}</a>"
            }
        }
        out << toRender
    }



    def implausibleThicknessRowLevel = { attrs ->
        Dataset dataset = attrs.dataset
        String templateAsString = ""
        String toRender = ""

        if (dataset && !dataset.ignoreWarning) {
            Resource datasetResource = attrs.resource ?: datasetService.getResource(dataset)
            ResourceType resourceType = datasetResource.resourceTypeObject
            Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)
            Double thickness_in

            String value = dataset.additionalQuestionAnswers?.get("thickness_mm")
            boolean usingInches = false

            if (!value) {
                value = dataset.additionalQuestionAnswers?.get("thickness_in")

                if (value) {
                    usingInches = true
                }
            }

            if (thickness != null && resourceType && datasetResource && datasetResource.allowVariableThickness) {
                if (usingInches) {
                    if (thickness) {
                        thickness_in = (thickness / 25.4).round(2)
                    } else {
                        thickness_in = 0.0
                    }
                }

                if (dataset.userGivenUnit && (dataset.userGivenUnit.equalsIgnoreCase("m2") || dataset.userGivenUnit.equalsIgnoreCase("sq ft"))) {

                    if (usingInches) {
                        String warning = "${message(code: 'userGivenThickness', args: [thickness_in])} in"
                        if (thickness_in == 0.0) {
                            toRender = "${datasetResource.staticFullName}<br />${message(code: 'userGivenThickness_zero')}"
                        } else if (resourceType.maxThickness_mm && resourceType.maxThickness_mm < thickness) {
                            toRender = "${message(code: 'userGivenThickness_implausibly_thick', args: [datasetResource.staticFullName])}<br />${warning}, ${message(code: 'userGivenThickness_too_high', args: [(resourceType.maxThickness_mm / 25.4).round(2)])} in."
                        } else if (resourceType.minThickness_mm && resourceType.minThickness_mm > thickness) {
                            toRender = "${message(code: 'userGivenThickness_implausibly_thin', args: [datasetResource.staticFullName])}<br />${warning}, ${message(code: 'userGivenThickness_too_low', args: [(resourceType.minThickness_mm / 25.4).round(2)])} in."

                        }
                    } else {
                        String warning = "${message(code: 'userGivenThickness', args: [thickness])} mm"

                        if (thickness == 0.0) {
                            toRender = "${datasetResource.staticFullName}<br />${message(code: 'userGivenThickness_zero')}"
                        } else if (resourceType.maxThickness_mm != null && resourceType.maxThickness_mm < thickness) {
                            toRender = "${message(code: 'userGivenThickness_implausibly_thick', args: [datasetResource.staticFullName])}<br />${warning}, ${message(code: 'userGivenThickness_too_high', args: [resourceType.maxThickness_mm])} mm."
                        } else if (resourceType.minThickness_mm != null && resourceType.minThickness_mm > thickness) {
                            toRender = "${message(code: 'userGivenThickness_implausibly_thin', args: [datasetResource.staticFullName])}<br />${warning}, ${message(code: 'userGivenThickness_too_low', args: [resourceType.minThickness_mm])} mm."
                        }
                    }
                }
            }

            String rowId = attrs.rowId
            Boolean isQueryPage = new Boolean(attrs.isQueryPage)

            if(toRender){
                toRender = "<div>${toRender}</div>"
                if (isQueryPage) {
                    toRender = "${toRender}<a id='hide${dataset.manualId}' data-warningThicknessId='warningThickness${dataset.manualId}' class='hideWarning hideWarningQuery'>${message(code: 'ignoreWarningThickness')}</a>"
                } else {
                    toRender = "${toRender}<a id='hide${dataset.manualId}' data-warningThicknessId='warningThickness${dataset.manualId}' data-manualId='${dataset.manualId}' class='hideWarning hideWarningImportMapper'>${message(code: 'ignoreWarningThickness')}</a>"
                }
                try {
                    templateAsString = g.render(template:"/query/alert", model: [dataset: dataset, content: toRender, rowId: rowId, isQueryPage: isQueryPage]).toString()
                } catch (Exception e) {
                    log.info("TEMPLATE EXCEPTION: ${e}")
                }
            }
        }

        out << templateAsString
    }

    def queryNotReadyAlert = { attrs ->
        String toRender
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Indicator indicator = attrs.indicator
        String queryId = params.queryId
        Boolean projectLevel = params.projectLevel
        Query query = attrs.query
        if ((entity||parentEntity) && indicator) {
            Boolean readyIndicatorSpecific
            //REL-265 - Hidden mandatory sections causes Incomplete query
            if(projectLevel){
                readyIndicatorSpecific = parentEntity?.queryReady?.get(queryId)
            }else if(entity.queryReadyPerIndicator?.get(indicator.indicatorId) == null){
                entity.resolveQueryReady(indicator, query)
                readyIndicatorSpecific = entity.queryReadyPerIndicator?.get(indicator.indicatorId)
            }else{
                readyIndicatorSpecific = entity.queryReadyPerIndicator?.get(indicator.indicatorId)
            }
            if (entity?.locked) {
                toRender = "<div class=\"container\" id=\"messageContent\"><div class=\"alert\" style=\"margin-bottom: 0px;\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<i class=\"fa fa-lock pull-left\" style=\"font-size: medium; margin-right: 8px;\"></i>" +
                        "<strong>${message(code: 'query.locked.entity')}</strong></div></div>"
            }

            if (!readyIndicatorSpecific && !entity?.locked && !indicator?.isQueryOptional(queryId)) {
                toRender = "<div class=\"container\" id=\"messageContent\"><div class=\"alert alert-error\" style=\"margin-bottom: 0px;\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                        "<strong>${message(code: 'query.missing_data')}</strong></div></div>"
            }

        }
        out << toRender
    }

    def mandatoryResource = { attrs ->
        String toRender
        Entity entity = attrs.entity
        Query query = attrs.query
        Indicator indicator = attrs.indicator
        String queryId = attrs.queryId
        String sectionId = attrs.sectionId
        String questionId = attrs.questionId
        String tableId = attrs.tableId
        String fieldName = attrs.fieldName


        if (entity && !entity.locked && indicator) {
            List<Resource> missingRequiredResources = entity.getMissingRequiredResources(indicator, queryId, query)

            if (missingRequiredResources && !indicator.isQueryOptional(queryId)) {
                String missingResources = ""
                String resourceAddJs

                missingRequiredResources.each { Resource resource ->
                    if (missingResources.isEmpty()) {
                        missingResources = missingResources + "${optimiResourceService.getLocalizedName(resource)}"
                    } else {
                        missingResources = missingResources + ", ${optimiResourceService.getLocalizedName(resource)}"
                    }

                    if (resourceAddJs) {
                        resourceAddJs = "${resourceAddJs},${resource.resourceId}.${resource.profileId}"
                    } else {
                        resourceAddJs = "${resource.resourceId}.${resource.profileId}"
                    }
                }
                toRender = "<div class=\"alert alert-error\" style=\"margin-bottom: 0px;\"><i class=\"fas fa-times pull-left\" style=\"font-size: large; margin-right: 8px;\"></i><strong><a style=\"color: #b94a48;\" href=\"javascript:\" onclick=\"\$('.alert').hide(); addMultipleResources('${resourceAddJs}', '${tableId}', '${entity.id.toString()}', '${indicator.indicatorId}', '${queryId}', '${sectionId}', '${questionId}', '${fieldName}')\">${message(code: 'query.missing_required_resource')} ${missingResources}</a></strong></div>"

            }
        }
        out << toRender
    }

    def displayWithSubAndSuperScript = { attrs ->
        String unit = attrs.unit

        if (unit) {
            unit = stringUtilsService.convertUnitToHTML(unit)
        }
        out << unit
    }

    def queryPreventChanges = { attrs ->
        String toRender
        Boolean preventChanges = attrs.preventChanges

        if (preventChanges) {
            toRender = "<div class=\"container\" id=\"messageContent\">" +
                        "<div class=\"alert alert-warnsing\">" +
                            "<i class=\"fas fa-exclamation pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                            "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                            "<strong>${message(code: 'query.preventChanges_true')}</strong>" +
                        "</div></div>"
        }
        out << toRender
    }

    def renderProfilePopup = { attrs ->
        String resourceUUID = attrs.resourceUUID

        if (resourceUUID) {
            out << "<a href=\"javascript:;\" class=\"fiveMarginLeft\" onclick=\"window.open('${createLink(controller: "import", action: "showData", params: [resourceUUID: resourceUUID])}', '_blank', 'width=1024, height=768, scrollbars=1');\"><i class=\"fas fa-search-plus\"></i></a>"
        }
    }

    def filesForQuestion = { attrs ->
        String queryId = attrs.queryId
        String questionId = attrs.questionId
        Entity entity = attrs.entity
        String returnable = ""

        if (entity && queryId && questionId) {
            List<EntityFile> entityFiles = entity.getFiles()?.findAll({
                queryId.equals(it.queryId) && questionId.equals(it.questionId)
            })

            if (entityFiles && !entityFiles.isEmpty()) {
                for (EntityFile file in entityFiles) {
                    returnable = "${returnable}<div id=\"${file.id.toString()}\">${link(controller: 'util', action: 'downloadEntityFile', id: file.id) { file.name }} <a href=\"javascript:;\" class=\"warningRed\" onclick=\"removeEntityFile('${file.id.toString()}');\">${message(code: 'delete')}</a></div>"
                }
            }
        }
        out << returnable
    }

    def renderLastUpdater = { attrs ->
        String queryId = attrs.queryId
        Entity entity = attrs.entity

        if (queryId && entity) {
            Map<String, QueryLastUpdateInfo> queryLastUpdates = entity.queryLastUpdateInfos
            QueryLastUpdateInfo queryLastUpdateInfo = queryLastUpdates?.get(queryId)

            if (queryLastUpdateInfo) {
                String outStream = ""
                LocalDateTime firstTime
                LocalDateTime secondTime = LocalDateTime.now()
                long days
                Locale locale = userService.getUserLocale(userService.getCurrentUser()?.localeString)
                int index = 0


                queryLastUpdateInfo.updates.each { Date updated ->
                    firstTime = DateUtil.dateToLocalDatetime(updated)
                    days = Duration.between(firstTime, secondTime).toDays()
                    String lastUpdaterInfo = "<span style=\"padding: 3px 15px; display: block; clear: both; white-space: nowrap;\"><strong>${index == 0 ? message(code: 'entity.lastUpdated'): ''}</strong><br />${maskingService.maskSurname(queryLastUpdateInfo?.usernames?.get(index)?.toString()) ?: message(code: 'task_user.update')}<br />${formatDate(date: updated, format: 'dd.MM.yyyy HH:mm', locale: locale)}<br />${days} ${message(code: 'days_ago')}</span>"
                    outStream = outStream + lastUpdaterInfo
                    index++
                }
                out << outStream
            }
        }
    }

    def renderQuestion = { attrs ->
        Question question = attrs.question
        Query query = attrs.query
        List<Question> questions = attrs.questions
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        QuerySection section = attrs.section
        String queryId = attrs.queryId
        Integer questionIndex = attrs.questionIndex
        Indicator indicator = attrs.indicator
        Boolean construction = attrs.construction
        Map<String, List<String>> projectLevelHideableQuestions = attrs.projectLevelHideableQuestions
        Boolean noDefaults = attrs.noDefaults
        Map<String, Boolean> additionalQuestionLicensed = attrs.additionalQuestionLicensed
        Boolean preventChanges = attrs.preventChanges
        Boolean sendMeData = attrs.sendMeData // Sendmedata render
        List<Question> linkedQuestions = attrs.linkedQuestions // Sendmedata render
        Boolean checkQuestionType = attrs.checkQuestionType
        Boolean groupMaterialsAllowed = attrs.groupMaterialsAllowed
        EolProcessCache eolProcessCache = attrs.eolProcessCache
        Boolean isQnLicensed = question.isQuestionLicensed(parentEntity ?: entity)
        Map additionalCalcParamsForCurrentQuery = attrs.additionalCalcParamsForCurrentQuery
        Boolean isQuestionUsedForAdditionalJSCalculation = Boolean.FALSE

        if ((question && section && construction) ||
                (question && section &&
                        (isQnLicensed ?: (question.licenseKey && question.noLicenseShowAsDisabled)))) {
            if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL == queryId && xmlService.skipRenderingRsetQuestion(question, parentEntity)) {
                return
            }

            if (!question.queryId) {
                question.queryId = queryId
            }

            if (!question.sectionId) {
                question.sectionId = section.sectionId
            }

            if (additionalCalcParamsForCurrentQuery){
                isQuestionUsedForAdditionalJSCalculation = questionService.isQuestionUsedForAdditionalJSCalculation(query, section.sectionId, question.questionId, additionalCalcParamsForCurrentQuery)
            }

            QuestionTableFormat tableFormat = section?.useTableFormatting
            Boolean useTableFormat = tableFormat && tableFormat.questionIds?.contains(question.questionId) ? Boolean.TRUE : Boolean.FALSE
            List<String> columnWidths = []
            Boolean firstQuestion = Boolean.FALSE
            Boolean lastQuestion = Boolean.FALSE

            if (tableFormat) {
                tableFormat.questionIds = tableFormat.questionIds.intersect(questions?.questionId) //exclude hidden questions from tableFormat

                tableFormat.columnWidths?.each {
                    columnWidths.add(it)
                }

                if (tableFormat.questionIds?.size() > 0 && question.questionId.equals(tableFormat.questionIds?.first())) {
                    firstQuestion = Boolean.TRUE
                }

                if (tableFormat.questionIds?.size() > 0 && question.questionId.equals(tableFormat.questionIds?.last())) {
                    lastQuestion = Boolean.TRUE
                }
            }
                //sendmedata enhancements change
                String typeToCheck = indicator?.sendPrivateDataType ?: attrs.dataTypeSelected
                if ((!checkQuestionType||!question.compatiblePrivateDataTypes||(checkQuestionType && typeToCheck && question.compatiblePrivateDataTypes?.contains(typeToCheck))) &&
                    !indicator?.hideQuestions?.get(section.sectionId)?.contains(question.questionId) &&
                    !projectLevelHideableQuestions?.get(section.sectionId)?.contains(question.questionId)) {
                User user = userService.getCurrentUser(true)

                Boolean superUser = userService.getSuperUser(user)
                String unit = question.defineUnit(section)
                Boolean quantity = question.defineQuantity(section)
                Boolean optional = question.defineOptional(section)
                Boolean multipleChoicesAllowed = question.defineMultipleChoicesAllowed(section)
                String fieldName = "${section.sectionId}.${question.questionId}"
                String checkValueJs
                boolean expandable = question.expandable ? true : false
                if (question.onlyNumeric) {
                    checkValueJs = " onblur=\"checkValue(this,'${question.localizedQuestion}','${question.minAllowed}','${question.maxAllowed}','${question.inputWidth}')\""
                }
                String divId = attrs.divId ? attrs.divId : "${section.sectionId}.${question.questionId}"
                String inputType = question.defineInputType(section)

                List<Question> additionalQuestions = inputType == "select" && question.resourceGroups ? [] : question.defineAdditionalQuestions(indicator, section, null, parentEntity, Boolean.TRUE)?.sort()

                if (additionalQuestionLicensed && additionalQuestions) {
                    additionalQuestions = additionalQuestions.findAll({ (!it.licenseKey && !it.disableForLicenseKeys && !it.requiredLicenseKeys) || additionalQuestionLicensed.get(it.questionId) })
                }
                Boolean questionModifiable

                if (attrs.modifiable != null) {
                    questionModifiable = attrs.modifiable
                }
                boolean disabled = resolveIfFieldDisabled(entity, indicator, questionModifiable)

                if (entity) {
                    disabled = resolveIfFieldDisabled(entity, indicator, questionModifiable)
                } else if (parentEntity) {
                    disabled = !parentEntity.modifiable||questionModifiable == Boolean.FALSE
                }
                if(question.licenseKey && !question.isQuestionLicensed(entity)){
                    disabled = true
                }else if(!question.licenseKey && question.noLicenseShowAsDisabled){
                    disabled = true
                }
                String disabledAttribute

                if (disabled) {
                    disabledAttribute = "disabled=\"disabled\""
                }
                boolean datasetsFound = false

                if (("checkbox".equals(inputType) || "radio".equals(inputType)) && queryId && section?.sectionId && question?.questionId) {
                    datasetsFound = entity?.datasets?.find({ queryId.equals(it.queryId) }) ? true : false
                }
                boolean readXmlButton = question.getReadXmlButton(indicator)
                boolean injectXmlButton = false
                boolean fecEpdInjectButton = false
                boolean disableInjectXmlButton = true
                if (FrenchConstants.RSET_QUESTIONS.contains(question.questionId)) {
                    injectXmlButton = question?.injectXmlButton
                    disableInjectXmlButton = questionService.getDisableReadXmlButton(indicator, entity)
                } else if ("fecEpd".equals(question.questionId)) {
                    fecEpdInjectButton = question?.injectXmlButton
                    disableInjectXmlButton = questionService.getDisableReadXmlButton(indicator, entity)
                }
                Map<String, Integer> additionalQuestionInputWidth = indicator?.indicatorQueries?.find({ it.queryId == queryId })?.additionalQuestionInputWidth


                Boolean lcaParametersQuestion = question?.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID
                Boolean hideQuestionByLCAModel = false

                if (lcaParametersQuestion) {
                    if (question?.compatibleLcaModels) {
                        hideQuestionByLCAModel = !question.compatibleLcaModels.contains(parentEntity?.datasets?.find({ it.questionId == "lcaModel" })?.answerIds?.first()?.toString())
                    } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_TARGET_QUESTIONID.equals(question.questionId)) {
                        hideQuestionByLCAModel = "00".equals(parentEntity?.datasets?.find({ it.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_METHOD_VERSION_QUESTIONID })?.answerIds?.first()?.toString())
                    }
                }
                    Map model = [question              : question, section: section, mainSectionId: section.sectionId, groupMaterialsAllowed: groupMaterialsAllowed,
                                 parentEntity          : parentEntity, entity: entity, unit: unit,
                                 additionalQuestions   : additionalQuestions, preventChanges: preventChanges,
                                 quantity              : quantity, optional: optional,
                                 multipleChoicesAllowed: multipleChoicesAllowed, additionalQuestionInputWidth: additionalQuestionInputWidth,
                                 fieldName             : fieldName, checkValue: checkValueJs,
                                 divId                 : divId, inputType: inputType, unitSystem: user?.unitSystem,
                                 disabledAttribute     : disabledAttribute, datasetsFound: datasetsFound,
                                 modifiable            : !disabled, useTableFormat: useTableFormat, columnWidths: columnWidths,
                                 questionIndex         : questionIndex, firstQuestion: firstQuestion,
                                 lastQuestion          : lastQuestion, expandable: expandable, readXmlButton: readXmlButton,
                                 injectXmlButton       : injectXmlButton, disableInjectXmlButton: disableInjectXmlButton,
                                 fecEpdInjectButton    : fecEpdInjectButton, superUser: superUser, noDefaults: noDefaults, hideQuestionByLCAModel: hideQuestionByLCAModel,
                                 sendMeData            : sendMeData, linkedQuestions: linkedQuestions, eolProcessCache: eolProcessCache,
                                 isQuestionUsedForAdditionalJSCalculation: isQuestionUsedForAdditionalJSCalculation]

                if (sendMeData) {
                    model << [showMandatory: true]
                }

                if (useTableFormat) {
                    out << render(template: "/query/questiontable", model: model)
                } else {
                    out << render(template: "/query/question", model: model)
                }
            } else if (useTableFormat && lastQuestion) {
                out << "</table></div>"
            }
        }
    }

    def renderResourceQuestion = { attrs ->
        long now = System.currentTimeMillis()
        Question question = attrs.question
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Query query = attrs.query
        QuerySection section = attrs.section
        String mainSectionId = attrs.mainSectionId
        Boolean noDefaults = attrs.noDefaults
        Boolean isPlanetary = attrs.isPlanetary
        Boolean groupMaterialsAllowed = attrs.groupMaterialsAllowed
        String maxRowLimitPerDesignReached = attrs.maxRowLimitPerDesignReached
        Map<String, Boolean> additionalQuestionLicensed = attrs.additionalQuestionLicensed
        Map<String, Integer> additionalQuestionInputWidth = attrs.additionalQuestionInputWidth
        List questionAnswers = attrs.questionAnswers
        EolProcessCache eolProcessCache = attrs.eolProcessCache
        Map additionalCalcParamsForCurrentQuery = attrs.additionalCalcParamsForCurrentQuery
        Boolean isQuestionUsedForAdditionalJSCalculation = Boolean.FALSE

        if (question && section && query && mainSectionId) {
            Indicator indicator = attrs.indicator
            List<Question> additionalQuestions = question.defineAdditionalQuestions(indicator, section, Boolean.TRUE, parentEntity, Boolean.TRUE)?.sort()

            if (additionalQuestionLicensed && additionalQuestions) {
                additionalQuestions = additionalQuestions.findAll({ (!it.licenseKey && !it.disableForLicenseKeys && !it.requiredLicenseKeys) || additionalQuestionLicensed.get(it.questionId) })
            }

            if (additionalCalcParamsForCurrentQuery){
                isQuestionUsedForAdditionalJSCalculation = questionService.isQuestionUsedForAdditionalJSCalculation(query, section.sectionId, question.questionId, additionalCalcParamsForCurrentQuery)
            }

            QueryApplyFilterOnCriteria applyFilterOnCriteria = indicator?.indicatorQueries?.find({
                query.queryId.equals(it.queryId) && it.applyFilterOnCriteria
            })?.applyFilterOnCriteria
            String filterOnCriteria

            if (applyFilterOnCriteria) {
                filterOnCriteria = session?.getAttribute("filterOnCriteria")
            }
            QuerySection mainSection = query.getAllSections()?.find({ mainSectionId.equals(it.sectionId) })
            Boolean quantity = question.defineQuantity(section)
            Boolean optional = question.defineOptional(section)
            Boolean showMandatory = !query.disableHighlight
            Boolean multipleChoicesAllowed = question.defineMultipleChoicesAllowed(section)
            Boolean showTypeahead = question.defineShowTypeahead(section)
            String preventDoubleEntries = question.preventDoubleEntries ? 'true' : 'false'
            String divId = attrs.divId
            String resourceTableId
            String addButtonId

            if (divId) {
                resourceTableId = "${divId}Resources".replace('.', '').trim()
                addButtonId = "${divId}ButtonAdd".replace('.', '').trim()
            }
            String fieldName = "${mainSectionId}.${question.questionId}"
            String inputWidth
            String inputWidthForBorder
            if (question.inputWidth) {
                inputWidth = " style=\"width: ${question.inputWidth}px;\""
                inputWidthForBorder = question.inputWidth
            }
            String inputHeight

            if (question.inputHeight) {
                inputHeight = " style=\"width: ${question.inputHeight}px;\""
            }
            String selectId = "${resourceTableId}Select"
            String trId = "${resourceTableId}Permanent"
            String textId = "${resourceTableId}Text"
            String additionalQuestionNames = ""
            List<Dataset> resourceDatasets = getDatasetsForResourceQuestion(entity ?: parentEntity, query, section, question)
            Boolean questionModifiable

            if (attrs.modifiable != null) {
                questionModifiable = attrs.modifiable
            }
            boolean disabled = resolveIfFieldDisabled(entity, indicator, questionModifiable)
            if (entity) {
                disabled = resolveIfFieldDisabled(entity, indicator, questionModifiable)
            } else if (parentEntity) {
                disabled = !parentEntity.modifiable||questionModifiable == Boolean.FALSE
            }
            String disabledAttribute

            if (disabled) {
                disabledAttribute = "disabled=\"disabled\""
            }
            Boolean showGWP = parentEntity?.showGWPLicensed ? Boolean.TRUE : Boolean.FALSE
            Boolean monthlyInputEnabled = entity?.monthlyInputEnabledByQuery?.get(query.queryId) && attrs.allowMonthlyData ? Boolean.TRUE : Boolean.FALSE
            Boolean quarterlyInputEnabled = entity?.quarterlyInputEnabledByQuery?.get(query.queryId) && attrs.allowQuarterlyData ? Boolean.TRUE : Boolean.FALSE
            List<QueryFilter> supportedFilters

            if (multipleChoicesAllowed) {
                supportedFilters = query.supportedFilters
            }
            Boolean preventChanges = attrs.preventChanges
            Boolean basicQuery = configurationService.getConfigurationValue(Constants.APPLICATION_ID, Constants.ConfigName.BASIC_QUERY_ID.toString())?.equals(query.queryId)
            Map model = [additionalQuestions   : additionalQuestions, groupMaterialsAllowed: groupMaterialsAllowed,
                         quantity              : quantity, optional: optional,
                         multipleChoicesAllowed: multipleChoicesAllowed, showTypeahead: showTypeahead,
                         preventDoubleEntries  : preventDoubleEntries, divId: divId,
                         resourceTableId       : resourceTableId, textId: textId, addButtonId: addButtonId, fieldName: fieldName,
                         inputWidth            : inputWidth, inputHeight: inputHeight, selectId: selectId, trId: trId,
                         inputWidthForBorder   : inputWidthForBorder,
                         resourceDatasets      : resourceDatasets, resourceCache: ResourceCache.init(resourceDatasets), disabledAttribute: disabledAttribute,
                         showMandatory         : showMandatory, section: section, question: question, query: query,
                         modifiable            : !disabled, showGWP: showGWP, monthlyInputEnabled: monthlyInputEnabled,
                         quarterlyInputEnabled : quarterlyInputEnabled, parentEntity: parentEntity,
                         mainSection           : mainSection, supportedFilters: supportedFilters, noDefaults: noDefaults, maxRowLimitPerDesignReached: maxRowLimitPerDesignReached,
                         preventChanges        : preventChanges, entity: entity, basicQuery: basicQuery, additionalQuestionInputWidth: additionalQuestionInputWidth,
                         eolProcessCache       : eolProcessCache, isQuestionUsedForAdditionalJSCalculation: isQuestionUsedForAdditionalJSCalculation]

            if (query.dataLoadingFeature && section.sectionId?.equals(query.dataLoadingFeature.filterParameterDefinition?.get("sectionId")) && question.questionId?.equals(query.dataLoadingFeature.filterParameterDefinition?.get("questionId"))) {
                model.put("renderDataLoadingFeature", query.dataLoadingFeature)
            }

            if (questionAnswers) {
                model.put("questionAnswers", questionAnswers)
            }

            if (multipleChoicesAllowed) {
                pageScope.resourceQuestion = render(template: "/query/multipleresource", model: model)
            } else {
                List<Resource> resources = questionService.getFilteredResources(parentEntity ? parentEntity : entity, indicator, query.queryId, filterOnCriteria, Boolean.TRUE, null, null, null, Boolean.TRUE, question)?.findAll({
                    it != null
                })
                Dataset singleResourceDataset
                Map<String, Map<String, String>> statesForCountryResource = [:]

                if (resources) {
                    if (resourceDatasets && !resourceDatasets.isEmpty()) {
                        singleResourceDataset = resourceDatasets.first()
                    }

                    if (singleResourceDataset) {
                        if (singleResourceDataset.resourceId && singleResourceDataset.profileId) {
                            Resource resource = resources.find({it.resourceId?.equals(singleResourceDataset.resourceId) && it.profileId?.equals(singleResourceDataset.profileId)})

                            if (resource && !resource.active) {
                                List<Resource> actives = resources.findAll({ it.active })

                                if (actives) {
                                    actives.add(resource)
                                } else {
                                    actives = [resource]
                                }
                                resources = actives
                            } else {
                                resources = resources.findAll({ it.active })
                            }
                        } else {
                            resources = resources.findAll({ it.active })
                        }
                    } else {
                        resources = resources.findAll({ it.active })
                    }
                }
                model.put("resources", resources?.sort({ optimiResourceService.getLocalizedName(it) }))
                model.put("countriesWithState", resources?.findAll({it.showState})?.collectEntries({[it.resourceId, it.showState]}) as JSON)
                List<String> countriesWithPlanetary = []
                if(isPlanetary){
                    BasicDBObject filter = new BasicDBObject("planetary", true)
                    countriesWithPlanetary = License.collection.distinct("conditionalCountries",filter,String.class)?.toList()
                }
                model.put("countriesWithPlanetary",countriesWithPlanetary)
                if (additionalQuestions) {
                    additionalQuestions.collect({ it.questionId }).each { String additionalQuestionId ->
                        if (!additionalQuestionNames) {
                            additionalQuestionNames = "${section.sectionId}.${question.questionId}_additional_${additionalQuestionId}"
                        } else {
                            additionalQuestionNames = "${additionalQuestionNames},${section.sectionId}.${question.questionId}_additional_${additionalQuestionId}"
                        }
                    }
                    model.put("additionalQuestionNames", additionalQuestionNames)

                    String formattedAdditionalQuestionNames = ""

                    resources?.collect({ it?.resourceId })?.each { String resourceId ->
                        if (resourceId.equals(singleResourceDataset?.resourceId)) {
                            additionalQuestions?.collect({ it.questionId })?.each { String additionalQuestionId ->
                                if (formattedAdditionalQuestionNames) {
                                    formattedAdditionalQuestionNames = "${formattedAdditionalQuestionNames},${mainSectionId}.${question.questionId}_additional_${additionalQuestionId}.${resourceId}"
                                } else {
                                    formattedAdditionalQuestionNames = "${mainSectionId}.${question.questionId}_additional_${additionalQuestionId}.${resourceId}"
                                }
                            }
                        } else if (resourceId.equals(question.defaultValue)) {
                            additionalQuestions?.collect({ it.questionId })?.each { String additionalQuestionId ->
                                if (formattedAdditionalQuestionNames) {
                                    formattedAdditionalQuestionNames = "${formattedAdditionalQuestionNames},${mainSectionId}.${question.questionId}_additional_${additionalQuestionId}.${resourceId}"
                                } else {
                                    formattedAdditionalQuestionNames = "${mainSectionId}.${question.questionId}_additional_${additionalQuestionId}.${resourceId}"
                                }
                            }
                        }
                    }
                    if (!formattedAdditionalQuestionNames) {
                        formattedAdditionalQuestionNames = null
                    }
                    model.put("formattedAdditionalQuestionNames", formattedAdditionalQuestionNames)
                }
                pageScope.resourceQuestion = render(template: "/query/singleresource", model: model)
            }
        }
    }

    def userGivenUnitForSingleResourceQuestion = { attrs ->
        Boolean disabled = attrs.disabled
        Resource resource = attrs.resource
        Dataset dataset = attrs.dataset
        String mainSectionId = attrs.mainSectionId
        String questionId = attrs.questionId
        Map<Boolean, List<String>> useUserGivenUnit = unitConversionUtil.useUserGivenUnit(resource)
        String userGivenUnit = dataset?.userGivenUnit
        out << render(template: "/query/usergivenunit", model: [useUserGivenUnit: useUserGivenUnit, singleResource: true,
                                                                userGivenUnit   : userGivenUnit, resource: resource,
                                                                mainSectionId   : mainSectionId, questionId: questionId,
                                                                disabled        : disabled])?.toString()
    }

    def renderDefaultValues = { attrs ->
        String queryId = attrs.queryId
        String indicatorId = attrs.indicatorId
        Entity entity = attrs.entity

        /*
        if (queryId && indicatorId && entity) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            IndicatorQuery indicatorQuery = indicator?.indicatorQueries?.find({queryId.equals(it.queryId) && it.defaultValues})

            if (indicatorQuery) {
                def useDefaults = entity.useDefaultValues
                String defaultsToRender = "<div class=\"btn-group\" style=\"display: inline-block;\">" +
                        "<a href=\"#\" data-toggle=\"dropdown\" class=\"btn btn-primary dropdown-toggle\">${message(code: 'query.distance.defaults')}" +
                        "<span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
                defaultsToRender = "${defaultsToRender}<li><a id=\"useDefaults${entity.id}\">${message(code: 'query.form.using_defaults')} ${useDefaults ? message(code:'on') : message(code:'off')}</a></li>"
                defaultsToRender = "${defaultsToRender}<li><a href=\"\">${message(code: 'query.form.modify_defaults')}</a></li>"
                defaultsToRender = "${defaultsToRender}<li><a href=\"javascript:;\" onclick=\"useDefaults('${entity.id}');\">${useDefaults ? message(code: 'query.form.disable_defaults') : message(code: 'query.form.enable_defaults')}</a></li>"
                defaultsToRender = "${defaultsToRender}</ul>"
                out << defaultsToRender
            }
        }  */
    }

    def renderResourceRow = { attrs ->
        long now = System.currentTimeMillis()
        Dataset dataset = attrs.dataset
        Resource resource = attrs.resource
        Indicator indicator = attrs.indicator
        Boolean multipleChoicesAllowed = attrs.multipleChoicesAllowed
        Boolean groupMaterialsAllowed = attrs.groupMaterialsAllowed
        Boolean createConstructionsAllowed = attrs.createConstructionsAllowed
        Boolean publicGroupAllowed = attrs.publicGroupAllowed
        Boolean constructionPage = attrs.constructionPage
        Boolean productDataListPage = attrs.productDataListPage
        Boolean splitViewAllowed = attrs.splitViewAllowed
        Boolean splitPage = attrs.splitPage
        Boolean changeView = attrs.changeView
        Boolean hideAllAnsweredQuestions = attrs.hideAllAnsweredQuestions
        Boolean basicQuery = attrs.basicQuery
        String splitRowId = attrs.splitRowId
        Map<String, Integer> additionalQuestionInputWidth = attrs.additionalQuestionInputWidth
        String defaultLocalCompCountry = attrs.defaultLocalCompCountry
        Map additionalQuestionResources = attrs.additionalQuestionResources
        Map<String, Boolean> additionalQuestionLicensed = attrs.additionalQuestionLicensed
        Map<String, Boolean> additionalQuestionShowAsDisabled = attrs.additionalQuestionShowAsDisabled
        EolProcessCache eolProcessCache = attrs.eolProcessCache
        ResourceCache resourceCache = attrs.resourceCache
        Boolean automaticCostLicensed = attrs.automaticCostLicensed

        if (dataset && dataset.resourceId && (indicator || constructionPage || basicQuery)) {
            if(resource == null) {
                resource = resourceCache ? resourceCache.getResource(dataset) : datasetService.getResource(dataset)
            }

            if (resource) {
                User user = userService.getCurrentUser(true)
                String queryId = dataset.queryId
                String sectionId = dataset.sectionId
                String questionId = dataset.questionId
                String eolProcessingType = resource.eolProcessingType ?: resource.getResourceTypeObject()?.eolProcessingType
                Integer seqNo = dataset.seqNr
                String fieldName = attrs.fieldName ?: "${sectionId}.${questionId}"
                Entity parentEntity = attrs.parentEntity
                Entity entity = attrs.entity
                String datasetId = dataset.manualId
                Boolean showGWP = attrs.showGWP ? new Boolean(attrs.showGWP) : (parentEntity?.showGWPLicensed ? Boolean.TRUE : Boolean.FALSE)

                Query query = attrs.query ?: queryService.getQueryByQueryId(queryId, true)
                Question question = attrs.question ?: query.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
                QuerySection section = attrs.mainSection ?: query?.sections?.find({ it.sectionId.equals(sectionId) })
                String resourceTableId = attrs.resourceTableId ?: "${section.sectionId}${question.questionId}Resources"
                String trId = resourceTableId + UUID.randomUUID().toString().replaceAll('-', '')

                Boolean quantity = question?.defineQuantity(section)
                Boolean optional = question?.defineOptional(section)
                String quantityAnswer = ""

                if (dataset.originalAnswer) {
                    quantityAnswer = dataset.originalAnswer
                } else if (dataset.answerIds && !dataset.answerIds.isEmpty()) {
                    quantityAnswer = dataset.answerIds[0]?.toString()
                }
                Boolean preventChanges = attrs.preventChanges
                Boolean splitOrChangeLicensed = attrs.splitOrChangeLicensed

                if (quantityAnswer && !quantityAnswer.replace(",", ".").isNumber()) {
                    quantityAnswer = ""
                }


                List<Question> additionalQuestions = attrs.additionalQuestions ?: question?.defineAdditionalQuestions(indicator, section, Boolean.TRUE, parentEntity)?.sort()
                Map additionalQuestionAnswers = dataset.additionalQuestionAnswers

                if (dataset.profileId && !additionalQuestionAnswers?.get("profileId")) {
                    if (additionalQuestionAnswers) {
                        additionalQuestionAnswers.put("profileId", dataset.profileId)
                    } else {
                        additionalQuestionAnswers = [profileId: dataset.profileId]
                    }
                }
                DecimalFormat decimalFormat = userService.getDefaultDecimalFormat()
                String quantityMissingStyle = ""

                if (!question?.optional && question?.quantity && dataset.quantity == null) {
                    quantityMissingStyle = "border: 1px solid red;"
                }

                if (resource.allowVariableThickness) {
                    Boolean useInches = indicator?.useThicknessInInches
                    Question thicknessQuestion

                    if (useInches) {
                        thicknessQuestion = additionalQuestions?.find({ "thickness_in".equals(it.questionId) })
                    } else {
                        thicknessQuestion = additionalQuestions?.find({ "thickness_mm".equals(it.questionId) })
                    }

                    if (thicknessQuestion) {
                        Double thickness = additionalQuestionAnswers?.get(thicknessQuestion.questionId) &&
                                ((String) additionalQuestionAnswers?.get(thicknessQuestion.questionId))?.replaceAll(',', ".")?.isNumber() ?
                                ((String) additionalQuestionAnswers?.get(thicknessQuestion.questionId))?.replaceAll(',', ".")?.toDouble() :
                                (useInches ? resource.defaultThickness_in : resource.defaultThickness_mm)

                        if (thickness != null) {
                            String formattedThickness = thickness < 1 ? "${thickness}" : decimalFormat.format(thickness)

                            if (additionalQuestionAnswers) {
                                additionalQuestionAnswers.put(thicknessQuestion.questionId, formattedThickness)
                            } else {
                                additionalQuestionAnswers = [(thicknessQuestion.questionId): formattedThickness]
                            }
                        }
                    }
                }
                def modifiable = attrs.modifiable
                Map monthlyAnswers = dataset.monthlyAnswers
                Map quarterlyAnswers = dataset.quarterlyAnswers
                Boolean monthlyInputEnabled = attrs.monthlyInputEnabled
                Boolean quarterlyInputEnabled = attrs.quarterlyInputEnabled
                def queryTask = attrs.queryTask

                def disabled = false

                if (!queryTask) {
                    if (!parentEntity) {
                        if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                            disabled = true
                        }
                    } else {
                        if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                            disabled = true
                        }
                    }
                }
                
                if (additionalQuestionAnswers && additionalQuestions && !constructionPage) {
                    List<String> hiddenAdditionalQuestionIds = new ArrayList<String>(additionalQuestionAnswers?.keySet()?.toList())

                    if (hideAllAnsweredQuestions && hiddenAdditionalQuestionIds) {
                        additionalQuestions.removeAll({ hiddenAdditionalQuestionIds.contains(it.questionId) && !it.isThicknessQuestion })
                    } else {
                        hiddenAdditionalQuestionIds.removeAll(additionalQuestions.collect({ it.questionId }))
                        List<String> grouped = additionalQuestions.collect({ it.groupedQuestionIds })?.flatten()?.unique()

                        if (grouped) {
                            hiddenAdditionalQuestionIds.removeAll(grouped)
                        }
                    }

                    if (hiddenAdditionalQuestionIds && !hiddenAdditionalQuestionIds.isEmpty()) {
                        hiddenAdditionalQuestionIds.each {
                            additionalQuestions.add(new Question(questionId: it, inputType: "hidden"))
                        }
                    }
                }

                Boolean inactive = resource.active ? Boolean.FALSE : Boolean.TRUE
                Boolean notInFilter = Boolean.FALSE
                Boolean notInOrgFilter = Boolean.FALSE
                Boolean inWarningFilter = Boolean.FALSE
                Map<String, List<Object>> organisationResourceFilterCriteria = question?.getOrganisationResourceFilterCriteria(indicator, parentEntity)
                Map<String, List<Object>> warningResourceFilterCriteria = question?.getWarningResourceFilterCriteria(indicator, parentEntity)
                Map<String, List<Object>> blockResourceFilterCriteria = question?.getBlockResourceFilterCriteria(indicator, parentEntity)

                // For mantis task https://80.69.162.100/mantisbt/view.php?id=2815
                if (!inactive && !indicator?.supressDataFilterWarnings && !constructionPage && !basicQuery &&
                        // Do not validate constituents and dummy resources/constructions; validate constituents of the dummy (locally made) constructions.
                        ((!dataset.parentConstructionId && !resource.isDummy) || (dataset.isConstituent() && (dataset.parentConstructionId == Const.DUMMY_RESOURCE_ID)))) {
                    notInFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, queryId, indicator, null, entity) ? Boolean.FALSE : Boolean.TRUE
                }

                if (organisationResourceFilterCriteria && notInFilter){
                    notInOrgFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, organisationResourceFilterCriteria) ? Boolean.FALSE : Boolean.TRUE
                    if(!notInOrgFilter) {
                        notInFilter = false
                    }
                }

                if (warningResourceFilterCriteria) {
                    inWarningFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, warningResourceFilterCriteria, true) ? Boolean.TRUE : Boolean.FALSE
                    if(inWarningFilter) {
                        notInFilter = false
                    }
                }

                if (blockResourceFilterCriteria) {
                    inWarningFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, blockResourceFilterCriteria, true) ? Boolean.TRUE : Boolean.FALSE
                    if(inWarningFilter) {
                        notInFilter = false
                    }
                }

                Boolean disableThickness = attrs.disableThickness ?: Boolean.FALSE
                Map<Boolean, List<String>> useUserGivenUnit = unitConversionUtil.useUserGivenUnit(resource, dataset)
                String userGivenUnit = dataset.userGivenUnit
                Boolean dataIncompatible = Boolean.FALSE
                List<String> compatibleUnits = unitConversionUtil.getCompatibleUnitsForResource(resource)

                if (dataset.userGivenUnit && compatibleUnits && !compatibleUnits*.toLowerCase()?.contains(dataset.userGivenUnit.toLowerCase()) && !dataset.uniqueConstructionIdentifier) {
                    dataIncompatible = Boolean.TRUE
                }

                if (useUserGivenUnit && dataset.userGivenUnit) {
                    // Add incompatible unit as first to show but allow changing to actually compatible units
                    useUserGivenUnit.each {
                        if (!it.value?.contains(dataset.userGivenUnit)) {
                            it.value.add(0, dataset.userGivenUnit)
                        }
                    }
                }

                if (!disableThickness && !"m2".equals(userGivenUnit) && !"sq ft".equals(userGivenUnit)) {
                    disableThickness = Boolean.TRUE
                }
                Question additionalQuestion = additionalQuestions?.find({ it.isThicknessQuestion })
                String profileId = additionalQuestionAnswers?.get('profileId')
                Boolean userSetCost = dataset?.userSetCost
                Boolean userSetTotalCost = dataset?.userSetTotalCost
                Boolean hideCopyRows = query?.hideCopyRows
                Question useResourceUnitCost = additionalQuestions?.find({ "costPerUnit".equals(it.questionId) && it.defaultValueFromResource })
                Double defaultResourceUnitCostFromResource
                Double resourceTypeCostMultiplier = resource.resourceTypeCostMultiplier
                String costCalculationMethod = parentEntity?.costCalculationMethod

                if (useResourceUnitCost) {
                    try {
                        defaultResourceUnitCostFromResource = (Double) DomainObjectUtil.callGetterByAttributeName(useResourceUnitCost.defaultValueFromResource, resource)
                        Double currencyMultiplier = valueReferenceService.getDoubleValueForEntity(indicator?.currencyExchangeValueReference, entity)

                        if (currencyMultiplier && defaultResourceUnitCostFromResource) {
                            defaultResourceUnitCostFromResource = defaultResourceUnitCostFromResource * currencyMultiplier
                        }

                        if (resourceTypeCostMultiplier && defaultResourceUnitCostFromResource) {
                            defaultResourceUnitCostFromResource = defaultResourceUnitCostFromResource * resourceTypeCostMultiplier
                        }
                    } catch (Exception e) {
                        log.error("Could not use ${useResourceUnitCost.defaultValueFromResource} defaultValueFromResource ${resource?.resourceId} / ${resource?.profileId}: ${e}")
                    }
                }
                Boolean constructionResource = Boolean.FALSE
                String uniqueConstructionIdentifier = dataset.uniqueConstructionIdentifier
                String constructionValue = dataset.constructionValue
                String parentConstructionId = dataset.parentConstructionId
                String constructionUnit
                String constructionTypeImage = constructionService.renderConstructionTypeImage(resource)
                Boolean unbundable = Boolean.FALSE
                Boolean preventExpand = Boolean.FALSE
                Map scalingParameters

                if (resource.construction) {
                    constructionResource = Boolean.TRUE
                    String constructionType = resource.constructionType
                    def construction = constructionService.getConstructionAsDocument(resource.constructionId)

                    if (construction) {
                        preventExpand = construction.preventExpand
                        unbundable = construction.unbundable
                        constructionUnit = userGivenUnit

                        scalingParameters = constructionService.getScalingParametersFromDocument(construction)

                        if (additionalQuestionAnswers) {
                            construction.additionalQuestionAnswers?.each {
                                if (!additionalQuestionAnswers.get(it.key)) {
                                    additionalQuestionAnswers.put(it.key, it.value)
                                }
                            }
                        } else {
                            additionalQuestionAnswers = construction.additionalQuestionAnswers ?: [:]
                        }
                    } else {
                        // if the construction object does not exist anymore, isDummy allows the user to unbundle or create a variant, but not copy the construction
                        resource.isDummy = true
                        constructionUnit = dataset?.userGivenUnit ?: resource.unitForData
                    }
                } else {
                    scalingParameters = constructionService.getNMD3ScalingParameters(resource)
                }

                Boolean inheritConstructionServiceLife = Boolean.FALSE

                if (parentConstructionId) {
                    constructionUnit = dataset.userGivenUnit
                    inheritConstructionServiceLife = constructionService.getConstructionInheritServiceLife(parentConstructionId)
                }
                String persistedClass = dataset.persistedOriginalClass
                Boolean showImportFields = dataset.datasetImportFieldsId ? Boolean.TRUE : Boolean.FALSE
                Double originalQuantity = dataset.originalAnswer?.replaceAll(",", ".")?.trim()?.isNumber() ? dataset.originalAnswer.replaceAll(",", ".").trim().toDouble() : dataset.quantity

                Boolean hideThickness = Boolean.FALSE

                if (!resource.allowVariableThickness) {
                    hideThickness = Boolean.TRUE
                } else {
                    if (dataset.userGivenUnit) {
                        if (!["m2","sq ft"].contains(dataset.userGivenUnit)) {
                            hideThickness = Boolean.TRUE
                        }
                    } else if (resource.unitForData && !["m2","sq ft"].contains(resource.unitForData)) {
                        hideThickness = Boolean.TRUE
                    } else if (constructionUnit && !["m2","sq ft"].contains(constructionUnit)) {
                        hideThickness = Boolean.TRUE
                    }
                }
                String firstSectionId = query?.sections?.get(0)?.sectionId
                String sourceListingDirection

                if (firstSectionId && section?.sectionId?.equalsIgnoreCase(firstSectionId)) {
                    sourceListingDirection = "right"
                } else {
                    sourceListingDirection = "top"
                }
                Boolean isManager = parentEntity?.managerIds?.contains(user?.id?.toString()) || userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)
                Resource projectCountryResource = parentEntity?.countryResource
                String nmdProfileSetString = dataset.nmdProfileSetString
                Boolean showImperial = constructionPage && UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(user?.unitSystem)
                String unitToShow = datasetService.getUnitToShow(dataset, useUserGivenUnit, constructionUnit)

                //SW-1525: check for enabling or disabling nmdReuseMaterial checkbox
                //disabledFlag is only supported by checkbox based on current implementation.
                Boolean disabledFlag
                Question nmdAddQuestion = additionalQuestions?.find { it.questionId == com.bionova.optimi.core.Constants.NMD_REUSED_MATERIAL_QUESTIONID }
                if (nmdAddQuestion) {
                    disabledFlag = additionalQuestionService.enableDisableFieldByEnablingCondition(nmdAddQuestion, resource, resourceCache)
                }

                //SW-1521
                if (dataset.constituent) {
                    if (!dataset.parentConstructionDataset) {
                        datasetService.setParentConstructionDataset(dataset, entity.datasets)
                    }
                    Resource constructResource = resourceCache ? resourceCache.getResource(dataset.parentConstructionDataset) : datasetService.getResource(dataset.parentConstructionDataset)
                    resource.hideConstituentImpacts = constructResource?.hideConstituentImpacts
                }

                //SW-1882
                //verifiedProductFlag is added to check verified building product resources.
                Boolean verifiedProductFlag = false
                if (parentEntity.entityClass == com.bionova.optimi.core.Constants.EntityClass.PRODUCT.getType()) {
                    verifiedProductFlag = true
                }

                Map params = [indicator                          : indicator,
                              parentEntity                       : parentEntity,
                              quarterlyInputEnabled              : quarterlyInputEnabled,
                              monthlyInputEnabled                : monthlyInputEnabled,
                              quarterlyAnswers                   : quarterlyAnswers,
                              monthlyAnswers                     : monthlyAnswers,
                              resource                           : resource,
                              resourceTableId                    : resourceTableId,
                              trId                               : trId,
                              quantity                           : quantity,
                              fieldName                          : fieldName,
                              additionalQuestions                : additionalQuestions,
                              additionalQuestionAnswers          : additionalQuestionAnswers,
                              mainSection                        : section,
                              query                              : query,
                              question                           : question,
                              showGWP                            : showGWP,
                              ignoreWarning                      : dataset.ignoreWarning,
                              unitToShow                         : unitToShow,
                              groupMaterialsAllowed              : groupMaterialsAllowed,
                              createConstructionsAllowed         : createConstructionsAllowed,
                              publicGroupAllowed                 : publicGroupAllowed,
                              quantityAnswer                     : quantityAnswer,
                              quantityMissing                    : quantityMissingStyle,
                              disabled                           : disabled,
                              multipleChoicesAllowed             : multipleChoicesAllowed,
                              optional                           : optional,
                              inactive                           : inactive,
                              notInFilter                        : notInFilter,
                              notInOrgFilter                     : notInOrgFilter,
                              inWarningFilter                    : inWarningFilter,
                              useUserGivenUnit                   : useUserGivenUnit,
                              userGivenUnit                      : userGivenUnit,
                              preventChanges                     : preventChanges,
                              disableThickness                   : disableThickness,
                              additionalQuestionId               : additionalQuestion?.questionId,
                              useResourceUnitCost                : useResourceUnitCost,
                              thicknessQuestion                  : additionalQuestion,
                              showImperial                       : showImperial,
                              productDataListPage                : productDataListPage,
                              profileId                          : profileId,
                              datasetId                          : datasetId,
                              seqNo                              : seqNo,
                              userSetCost                        : userSetCost,
                              userSetTotalCost                   : userSetTotalCost,
                              hideCopyRows                       : hideCopyRows,
                              defaultResourceUnitCostFromResource: defaultResourceUnitCostFromResource,
                              resourceTypeCostMultiplier         : resourceTypeCostMultiplier,
                              constructionResource               : constructionResource,
                              uniqueConstructionIdentifier       : uniqueConstructionIdentifier,
                              constructionValue                  : constructionValue,
                              parentConstructionId               : parentConstructionId,
                              constructionUnit                   : constructionUnit,
                              user                               : user,
                              constructionTypeImage              : constructionTypeImage,
                              showImportFields                   : showImportFields,
                              dataIncompatible                   : dataIncompatible,
                              unbundable                         : unbundable,
                              preventExpand                      : preventExpand,
                              scalingParameters                  : scalingParameters,
                              eolProcessingType                  : eolProcessingType,
                              entity                             : entity,
                              constructionPage                   : constructionPage,
                              persistedClass                     : persistedClass,
                              splitOrChangeLicensed              : splitOrChangeLicensed,
                              splitViewAllowed                   : splitViewAllowed,
                              splitPage                          : splitPage,
                              changeView                         : changeView,
                              splitRowId                         : splitRowId,
                              originalQuantity                   : originalQuantity,
                              datasetImportFieldsId              : dataset.datasetImportFieldsId,
                              hideThickness                      : hideThickness,
                              sectionId                          : sectionId,
                              sourceListingDirection             : sourceListingDirection,
                              dataset                            : dataset,
                              isManager                          : isManager,
                              additionalQuestionInputWidth       : additionalQuestionInputWidth,
                              projectCountryResource             : projectCountryResource,
                              inheritConstructionServiceLife     : inheritConstructionServiceLife,
                              nmdProfileSetString                : nmdProfileSetString,
                              customDatasetName                  : dataset.groupingDatasetName,
                              eolProcessCache                    : eolProcessCache,
                              costCalculationMethod              : costCalculationMethod,
                              disabledFlag                       : disabledFlag,
                              verifiedProductFlag                : verifiedProductFlag]
                /**
                 * This tagLib using for QueryController.form() and getResourceRowsRepresentation().
                 *
                 * For QueryController.form() params which placed below was initialized on top levels and shouldn't defined here
                 * because it can lead to incorrect overriding or additional system load, which no need in this method
                 *
                 * All this params was added for getResourceRowsRepresentation() method.
                 */
                if (additionalQuestionResources){
                    params.put("additionalQuestionResources", additionalQuestionResources)
                }

                if (defaultLocalCompCountry) {
                    params.put("defaultLocalCompCountry", defaultLocalCompCountry)
                }

                if (additionalQuestionLicensed) {
                    params.put("additionalQuestionLicensed", additionalQuestionLicensed)
                }

                if (additionalQuestionShowAsDisabled) {
                    params.put("additionalQuestionShowAsDisabled", additionalQuestionShowAsDisabled)
                }

                if (automaticCostLicensed != null) {
                    params.put("automaticCostLicensed", automaticCostLicensed)
                }

                out << render(template: "/query/resourcerow", model: params)
            }
        }
    }

    private List<Dataset> getDatasetsForResourceQuestion(Entity entity, Query query, QuerySection section, Question question) {
        List<Dataset> datasets

        if (entity) {
            String queryId = query?.queryId
            String sectionId = section?.sectionId
            String questionId = question?.questionId
            datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId, sectionId, questionId)
        }
        datasets = datasets?.findAll({it.resourceId})
        return datasets
    }

    private boolean resolveIfFieldDisabled(Entity entity, Indicator indicator, Boolean modifiable) {
        boolean disabled = false

        if (entity && indicator) {
            if (entity.locked || entity.disabledIndicators?.contains(indicator.indicatorId) || (modifiable != null && Boolean.FALSE == modifiable)) {
                disabled = true
            }
        }
        return disabled
    }
}
