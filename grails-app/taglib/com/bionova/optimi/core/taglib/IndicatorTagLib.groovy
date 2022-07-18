/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.taglib

import com.bionova.optimi.comparator.ResultListDatasetConstructionComparator
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.Constants.ConfigName
import com.bionova.optimi.core.domain.mongo.AppendQuestionIdToDisplayResult
import com.bionova.optimi.core.domain.mongo.ApplicationResourceExtraInformationSet
import com.bionova.optimi.core.domain.mongo.Benchmark
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.ReportTableCell
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.service.QuestionService
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import org.apache.commons.codec.binary.Base64
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.bson.Document
import org.bson.types.ObjectId

import java.text.DecimalFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class IndicatorTagLib {
    def configurationService
    def indicatorService
    def datasetService
    def loggerUtil
    def queryService
    def questionService
    def optimiResourceService
    def entityService
    def denominatorUtil
    def userService
    def channelFeatureService
    def resultFormattingResolver
    def portfolioService
    def errorMessageUtil
    def optimiStringUtils
    def resourceTypeService
    def licenseService
    def groovyPageRenderer
    def valueReferenceService
    def calculationResultService
    ResourceService resourceService

    private static final def months = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
    private static final def quarters = ["Q1", "Q2", "Q3", "Q4"]

    /*private String getDefaultTableContent(Indicator indicator, Entity entity, IndicatorReportItem reportItem, boolean hideBreakdown, Entity parentEntity = null) {
        if ("complex".equals(indicator?.assessmentMethod)) {
            List<CalculationRule> calculationRules = []
            List<ResultCategory> resultCategories = []

            if (reportItem.rules) {
                reportItem.rules.each { String rule ->
                    CalculationRule calculationRule = indicator.resolveCalculationRules?.find({
                        rule.equals(it.calculationRuleId)
                    })

                    if (!calculationRule) {
                        calculationRule = indicator.resolveCalculationRules?.find({
                            rule.equals(it.calculationRule)
                        })
                    }

                    if (calculationRule) {
                        calculationRules.add(calculationRule)
                    }
                }
            } else {
                calculationRules = indicator.resolveCalculationRules
            }

            if (!calculationRules && indicator.applicationRules) {
                flash.errorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
            }

            if (reportItem.categories && !reportItem.categories.isEmpty()) {
                reportItem.categories.each { String category ->
                    ResultCategory resultCategory = indicator.resolveResultCategories?.find({
                        category.equals(it.resultCategoryId)
                    })

                    if (!resultCategory) {
                        resultCategory = indicator.resolveResultCategories?.find({
                            category.equals(it.resultCategory)
                        })
                    }

                    if (resultCategory) {
                        resultCategories.add(resultCategory)
                    }
                }
            } else {
                resultCategories = indicator.resolveResultCategories
            }
            Boolean hideTotals = reportItem?.tableSettings?.get("showTotals") != null ? !reportItem.tableSettings.get("showTotals") : Boolean.FALSE
            String idForGraphs = StringUtils.remove(UUID.randomUUID().toString(), "-")

            if (calculationRules && !calculationRules.isEmpty() && resultCategories && !resultCategories.isEmpty()) {
                return render(template: '/entity/complextemplate', model: [entity          : entity, parentEntity: parentEntity, indicator: indicator,
                                                                           calculationRules: calculationRules, resultCategories: resultCategories,
                                                                           hideBreakdown   : hideBreakdown, hideTotals: hideTotals,
                                                                           idForGraphs     : idForGraphs, collapsed: reportItem?.collapsed,
                                                                           useShortNames   : reportItem?.useRuleShortNames ? true : false])
            } else {
                return ""
            }
        } else {
            def queries = queryService.getQueriesByIndicatorAndEntity(indicator, entity)
            return render(template: '/entity/basicresulttable', model: [entity: entity, indicator: indicator, queries: queries])
        }
    }*/

    private String getParentEntityImage(Entity entity, IndicatorReportItem reportItem) {
        String entityImage

        if (entity) {
            Map imageParams = reportItem.imageParams
            Entity parent

            if (entity.parentEntityId) {
                parent = entity.getParentById()
            }
            def images
            def defaultImage

            if (parent) {
                defaultImage = opt.entityIcon(entity: parent)
                images = entityService.getEntityImages(parent.id, null)
            } else {
                defaultImage = opt.entityIcon(entity: parent)
                images = entityService.getEntityImages(entity.id, null)
            }
            def maxWidth = imageParams?.get("maxWidth")
            def maxHeight = imageParams?.get("maxHeight")

            if (images) {
                EntityFile file = images[0]

                try {
                    Base64 encoder = new Base64()
                    def data = encoder.encodeBase64String(file.data)
                    entityImage = "<img src=\"data:" + file.contentType + ";base64," + data + "\" style=\"${maxWidth ? 'max-width: ' + maxWidth + 'px;' : ''}${maxHeight ? ' max-height: ' + maxHeight + 'px;' : ''}\" class=\"entityImg\" />"
                } catch (Exception e) {
                }
            } else if (imageParams?.get("useEntityDefaultImage")) {
                entityImage = "<i class=\"entitytype${entity.smallImage ? '-smaller' : ''} ${defaultImage}${entity.smallImage ? '-smaller' : ''}\"></i>"
            }
        }
        return entityImage ? entityImage : ""
    }

    private String getRowColorCoding(Map<String, String> rowColorCoding, Map<String, Object> additionalQuestionAnswers) {
        String backgroundColor = ""

        if (rowColorCoding && additionalQuestionAnswers) {

            String questionId = rowColorCoding.get("colorQuestion")
            String answer = (String) additionalQuestionAnswers.get(questionId)

            if (answer) {
                String color = rowColorCoding.get(answer)

                if (color) {
                    if (!color.trim().startsWith("#")) {
                        color = "#${color.trim()}"
                    }
                    backgroundColor = "background-color: ${color};"
                }
            }
        }
        return backgroundColor
    }

    private String getReportTableCell(Entity entity, ReportTableCell reportTableCell) {
        String value

        if (reportTableCell.text) {
            value = reportTableCell.localizedText
        } else if (reportTableCell.valueReference) {
            ValueReference valueReference = reportTableCell.valueReference

            if ("basicQuery".equals(valueReference?.queryId) && entity.parentEntityId) {
                Entity parent = entity.getParentById()
                value = valueReferenceService.getValueForEntity(valueReference, parent, Boolean.TRUE)
            } else {
                value = valueReferenceService.getValueForEntity(valueReference, entity, Boolean.TRUE)
            }

            if (value && valueReference.queryId && valueReference.questionId) {
                Question question = queryService.getQueryByQueryId(valueReference.queryId, true)?.getAllQuestions()?.find({ Question q -> q.questionId.equals(valueReference.questionId) })

                if (("select".equals(question?.inputType) || "checkbox".equals(question?.inputType)) && !question.isResourceQuestion) {
                    value = question.choices?.find({ QuestionAnswerChoice qac -> value.equals(qac.answerId) })?.localizedAnswer
                }
            }
        } else if (reportTableCell.valueReferences) {
            reportTableCell.valueReferenceObjects?.each { ValueReference valueReference ->
                def partValue

                if ("basicQuery".equals(valueReference?.queryId) && entity.parentEntityId) {
                    Entity parent = entity.getParentById()
                    partValue = valueReferenceService.getValueForEntity(valueReference, parent, Boolean.TRUE)
                } else {
                    partValue = valueReferenceService.getValueForEntity(valueReference, entity, Boolean.TRUE)
                }

                if (partValue) {
                    value = value ? value + " " + partValue : partValue
                }
            }
        }

        if (value) {
            Integer colspan = reportTableCell.colspan
            String formatting = reportTableCell.textFormatting
            String color = reportTableCell.textColor

            if (color) {
                value = "<span style=\"color: #${color};\">${value}</span>"
            }

            if (formatting) {
                if ("bold".equals(formatting)) {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}><strong>${value}</strong></td>"
                } else if ("italic".equals(formatting)) {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}><i>${value}</i></td>"
                } else if ("columnHeading".equals(formatting)) {
                    value = "<th${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value}</th>"
                } else {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value}</td>"
                }
            } else {
                value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value}</td>"
            }
        }
        return value ? value : "<td>&nbsp;</td>"
    }

    def showSourceListingButton = { attrs ->
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        Portfolio portfolio = attrs.portfolio
        List<String> queryIds = attrs.queryIds

        if ((portfolio && indicator?.portfolioSourceListing) ||
                (entity && indicator && !indicator.hideSourceListing && entityService.hasSourceListing(entity, indicator, queryIds))) {
            out << "${true}"
        }
    }

    def renderSourceListing = { attrs ->
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Indicator indicator = attrs.indicator
        def resources = entity?.getSourceListing(indicator)
        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.BETA_FEATURES, Feature.EPD_DOWNLOAD])
        Boolean downloadLinkLicense = featuresAllowed.get(Feature.EPD_DOWNLOAD)
        Boolean betaFeatures = featuresAllowed.get(Feature.BETA_FEATURES)
        Boolean showDiv = attrs.showDiv
        Map headers = new LinkedHashMap()
        List values = []
        List<Resource> resourceToDipslay = []
        //Many nullable points so put inside try catch to prevent page crashing - 15220
        try {
            if (resources && indicator && entity) {
                def userLanguage = userService.getCurrentUser()?.language
                userLanguage = userLanguage ? userLanguage : "EN"
                ApplicationResourceExtraInformationSet resourceExtraInformationSet = indicator?.resolveApplicationResourceExtraInformationSet
                Map sourceListing
                List<String> additionalQuestionsToShow
                List<String> showResultCategories
                List<Question> questionsToShow
                Set<Dataset> datasets = []
                List<CalculationResult> indicatorResults
                Map indicatorResultsPerResultCategory
                List<ResultCategory> resultCategories

                if (resourceExtraInformationSet) {
                    sourceListing = resourceExtraInformationSet.resourceExtraInformation
                    additionalQuestionsToShow = resourceExtraInformationSet.addToDataSources
                    showResultCategories = resourceExtraInformationSet.showResultCategory
                }
                if (additionalQuestionsToShow) {
                    Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                    questionsToShow = additionalQuestionsQuery?.getAllQuestions()?.findAll({ Question question -> additionalQuestionsToShow.contains(question.questionId) })
                }
                if (indicator.isResourceAggregationDisallowed || showResultCategories) {
                    indicatorResults = entity.getCalculationResultObjects(indicator.indicatorId, null, null)
                    if (showResultCategories) {
                        indicatorResultsPerResultCategory = indicatorResults.groupBy { it.resultCategoryId }
                        resultCategories = indicator.getResolveResultCategories(entity)
                    }
                }
                List<String> sourceListingAttributes = sourceListing?.keySet()?.toList()
                if (indicator.isResourceAggregationDisallowed) {
                    datasets = calculationResultService.getCalculationResultDatasets(entity, indicator, indicatorResults)

                    ResourceCache resourceCache = ResourceCache.init(datasets.toList())
                    for (Dataset dataset: datasets) {
                        Resource resource = resourceCache.getResource(dataset)
                        if (resource && dataset) {
                            resource.isConstruction = dataset.isConstruction()
                            resource.isConstituent = dataset.isConstituent()
                            datasetService.setLinkedDatasetManualIds(dataset, resource)
                            resourceToDipslay.add(resource)
                        }
                    }
                } else {
                    resourceToDipslay = resources
                }

                Map<String, Map> resourceExtraInformationResultPage = indicator.resolveResourceExtraInformationResultPage

                resourceToDipslay.each { Resource r ->
                    List<String> rowValues = new LinkedList<>()
                    if (sourceListingAttributes) {
                        rowValues.add(optimiResourceService.getLocalizedName(r))

                        sourceListingAttributes.each { String resourceAttribute ->
                            def value = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, r)
                            Map localizedHeading = resourceExtraInformationResultPage.get(resourceAttribute)
                            def heading = localizedHeading.get(userLanguage) ? localizedHeading.get(userLanguage) : localizedHeading.get("EN")
                            headers.put(heading, "")

                            if (resourceAttribute == 'verificationStatus') {
                                // need to clean the verificationStatus value since before November 2020, value of this field can be anything, while we decided that this field must be fixed to one of the values in Constants.ALLOWED_RESOURCE_VERIFICATION_STATUS
                                String verificationStatusCleaned = value?.toString()?.replaceAll('\\s', '')?.toLowerCase() ?: ''
                                if (verificationStatusCleaned && com.bionova.optimi.core.Constants?.ALLOWED_RESOURCE_VERIFICATION_STATUS?.keySet()?.contains(verificationStatusCleaned)) {
                                    value = message(code: "resource.verificationStatus.${verificationStatusCleaned}") ?: ''
                                } else {
                                    value = ''
                                }
                            }

                            rowValues.add(value ? value?.toString()?.replace("[", "")?.replace("]", "") : '')
                        }
                    } else {
                        headers.put(message(code: 'resource.profile'), "")
                        headers.put(message(code: 'resource.environmentDataSource'), "")
                        headers.put(message(code: 'resource.environmentDataPeriod'), "")
                        rowValues.add(optimiResourceService.getLocalizedName(r))
                        rowValues.add(r?.profileId ?: "")
                        rowValues.add(r?.environmentDataSource ?: "")
                        rowValues.add(r?.environmentDataPeriod ?: "")
                    }

                    if (questionsToShow) {
                        questionsToShow.each {
                            headers.put(it.localizedQuestion, "")
                            List<Document> additionalQuestionResources = it.getAdditionalQuestionResources(indicator, null)

                            if (it.questionId == "quantity") {
                                assignUnitQuantities(r, datasets, rowValues)
                            } else {
                                rowValues.add(r?.datasetAdditionalQuestionAnswers?.collect({ Map map -> map?.get(it.questionId) ? questionService.getAnswerForUI(map.get(it.questionId), additionalQuestionResources, it) : "" })?.unique()?.join("; "))
                            }

                        }
                    }

                    if (showResultCategories) {
                        headers.put(message(code: "resultCategory.name"), "")
                        List<String> collectedResultCategories = []

                        resultCategories.each { ResultCategory resultCategoryObject ->

                            List<CalculationResult> resultsForResultCategory = indicatorResultsPerResultCategory?.getAt(resultCategoryObject.resultCategoryId)
                            String resultCategory = resultCategoryObject?.resultCategory

                            r?.linkedDatasetManualIds?.each { String manualId ->
                                List<CalculationResult> datasetResults = entity.getResultObjectsForDataset(manualId, null, null, resultsForResultCategory)

                                datasetResults?.each { CalculationResult calcResults ->
                                    if (!collectedResultCategories.contains(resultCategory) && showResultCategories.contains(calcResults?.resultCategoryId)) {
                                        collectedResultCategories.add(resultCategory)
                                    }
                                }
                            }
                        }

                        rowValues.add(collectedResultCategories.join('<br/>'))
                    }

                    if (r?.benchmark && "OK".equals(r?.benchmark.status) && betaFeatures) {
                        String heading = message(code: 'resource.performance_ranking')
                        headers.put(heading.replace(":", ""), "<i class='icon-question-sign' style='font-weight:normal !important;' rel='tooltip_datasources'  data-content='${message(code: 'resource.ranking_help_1')}. ${message(code: 'resource.ranking_help_2')}'></i>")
                        Benchmark benchmark = r?.benchmark
                        ResourceType subType = resourceService.getSubType(r)

                        if (subType && subType.isSubType && subType.standardUnit && subType.benchmarkResult) {
                            Map<String, Integer> validValues = subType.benchmarkResult.validValues

                            if (validValues && indicator.connectedBenchmarks) {
                                String valueString = ''

                                indicator.connectedBenchmarks?.each { String benchmarkToShow ->
                                    Integer ranking = benchmark.ranking.get(benchmarkToShow)
                                    String validValuePerDataProperty = validValues.get(benchmarkToShow)
                                    String performanceRanking = "${ranking} / ${validValuePerDataProperty}"
                                    Integer quintile = benchmark.quintiles?.get(benchmarkToShow)
                                    String performanceCloud
                                    if (quintile > 0) {
                                        performanceCloud = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" :
                                                quintile == 2 ? "orangeEmissions" :
                                                        quintile == 1 ? "redEmissions" : ""
                                    }
                                    if (performanceRanking && !performanceRanking.isEmpty() && benchmarkToShow && r?.benchmark?.values?.get(benchmarkToShow) && !r?.benchmark?.values?.get(benchmarkToShow)?.equals("NOT_CALCULABLE")) {
                                        valueString += "${com.bionova.optimi.core.Constants.BENCHMARKS.get(benchmarkToShow)}: ${performanceRanking ? performanceRanking : ''} ${quintile && quintile > 0 && quintile <= 5 ? "<div class=\"co2CloudContainer\"><i class=\"fa fa-cloud ${performanceCloud}\"></i><p class=\"co2Text\">co<sub>2</sub></p></div>" : ""}"
                                        String link = "<i class=\"fas fa-chart-line benchmarkGraph\" onclick=\"fullScreenPopup('${createLink(controller: "resourceType", action: "benchmarkGraph", params: [entityId: entity?.id, resourceSubTypeId: r?.resourceTypeObject?.id, resourceId: r?.resourceId, profileId: r?.profileId, benchmarkToShow: benchmarkToShow])}');\"></i>"
                                        valueString += "<br />${message(code: 'resource.see_full_ranking')}: ${link}<br />"
                                    } else {
                                        valueString += "&nbsp;"
                                    }
                                }

                                rowValues.add(valueString)
                            } else {
                                rowValues.add("&nbsp;")
                            }
                        } else {
                            rowValues.add("&nbsp;")
                        }
                    } else {
                        headers.put("&nbsp;", "")
                        rowValues.add("&nbsp;")
                    }

                    if (resources.find({ it?.downloadLink })) {
                        headers.put(message(code: 'resource.pdfFile_download'), "")

                        if (!"-".equals(r?.downloadLink?.trim())) {
                            if (downloadLinkLicense) {
                                if (r?.downloadLink?.startsWith("http")) {
                                    rowValues.add("<a href='${r?.downloadLink}' onclick='stopBubblePropagation(event)' target='_blank' >${message(code: 'resource.externalLink')}</a>")
                                } else {
                                    String link = createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: r?.resourceId, profileId: r?.profileId])
                                    rowValues.add("<a href='${link}' target='_blank'>${message(code: 'resource.pdfFile_download')}</a>")
                                }
                            } else {
                                String businessLevel = "${message(code: 'business')} - Download EPD"
                                rowValues.add("<span class='enterpriseCheck'>${message(code: 'enterprise_feature_warning', args: [businessLevel])}</span>")
                            }
                        } else {
                            rowValues.add("&nbsp;")
                        }
                    }

                    values.add(rowValues)
                }
            }
            out << groovyPageRenderer.render(template: "/entity/modal/dataSourcesTable", model: [headers: headers, showDiv: showDiv, values: values, isResourceAggregationDisallowed: indicator.isResourceAggregationDisallowed, resourcesToIterate: resourceToDipslay])
        } catch (e) {
            log.error("Error when rendering renderSourceListing : ${e} ", e)
        }
    }


    def renderPortfolioSourceListing = { attrs ->
        Portfolio portfolio = attrs.portfolio
        Indicator indicator = attrs.indicator
        String operatingPeriod = attrs.operatingPeriod
        String output = ""

        if (portfolio && indicator?.portfolioSourceListing) {
            List<Entity> entities

            if (operatingPeriod) {
                entities = portfolioService.getAllChildEntities(portfolio)?.findAll({
                    operatingPeriod.equals(it.operatingPeriod) && !it.name
                })
            } else {
                entities = portfolioService.getAllChildEntities(portfolio)
            }

            if (entities) {
                if (portfolio.showOnlySpecificallyAllowed) {
                    entities = entities.findAll({ it.allowAsBenchmark })
                }

                if (portfolio.showOnlyLatestStatus) {
                    entities = entities.findAll({ it.chosenDesign })
                }

                if (portfolio.showOnlyCarbonHeroes) {
                    entities = entities.findAll({ it.carbonHero })
                }
            }

            if (entities) {
                List<Resource> resources = []

                entities.each { Entity entity ->
                    List<Resource> temp = entity.getSourceListing(indicator)

                    if (temp && !temp?.isEmpty()) {
                        temp.each { Resource resource ->
                            if (resources.isEmpty() || !resources.find({ Resource r -> r?.resourceId.equals(resource?.resourceId) && r?.profileId?.equals(resource?.profileId) })) {
                                resources.add(resource)
                            }
                        }
                    }
                }
                output = "<div class=\"sourceListingTable portFolioListing listing${indicator?.indicatorId}\" id=\"${operatingPeriod}sourceListingTable${indicator?.id}\" style=\"display: none;\">\n"
                output = output + "<h2><span>${message(code: 'results.sources')}</span></h2>"
                output = output + "<table class=\"table table-striped table-condensed table-cellvaligntop\" style=\"table-layout:fixed; height:100%; width:100%;\">\n"
                output = output + "<tr><th>${message(code: 'resource.name')}</th>"
                def userLanguage = userService.getCurrentUser()?.language
                userLanguage = userLanguage ? userLanguage : "EN"
                def sourceListingAttributes = indicator.portfolioSourceListing?.keySet()?.toList()

                sourceListingAttributes?.each { String resourceAttribute ->
                    Map localizedHeading = indicator.portfolioSourceListing.get(resourceAttribute)
                    def heading = localizedHeading.get(userLanguage) ? localizedHeading.get(userLanguage) : localizedHeading.get("EN")
                    output = output + "<th>${heading}</th>"
                }
                output = output + "</tr>"

                if (resources && !resources.isEmpty()) {
                    resources.sort({ optimiResourceService.getLocalizedName(it) }).each { Resource r ->
                        String resourceColumns = ""
                        boolean valueFound = false
                        for (String resourceAttribute in sourceListingAttributes) {
                            def value = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, r)

                            if (value) {
                                valueFound = true
                                if (value?.toString()?.isNumber() && value?.toString()?.toDouble() % 1 != 0) {
                                    value = value?.toString()?.toDouble()?.round(3)?.toString()
                                }
                                resourceColumns = "${resourceColumns}<td>${value}</td>"
                            } else {
                                resourceColumns = "${resourceColumns}<td>&nbsp;</td>"
                            }
                        }

                        if (valueFound) {
                            output = "${output}<tr><td>${optimiResourceService.getLocalizedName(r)}</td>${resourceColumns}</tr>\n"
                        }
                    }
                }
                output = output + "</table></div>"
            }
        }
        out << output
    }

    private String getAppendValueToIndicatorScore(Indicator indicator, Entity entity) {
        String appendValue
        AppendQuestionIdToDisplayResult appendQuestionIdToDisplayResult = indicator?.appendQuestionIdToDisplayResult


        if (appendQuestionIdToDisplayResult && entity?.datasets) {
            Dataset dataset = entity.datasets.find({ Dataset d ->
                d.queryId == appendQuestionIdToDisplayResult.queryId &&
                        d.sectionId == appendQuestionIdToDisplayResult.sectionId &&
                        d.questionId == appendQuestionIdToDisplayResult.questionId
            })

            if (dataset) {
                if (dataset.resourceId) {
                    appendValue = optimiResourceService.getLocalizedName(datasetService.getResource(dataset))
                } else {
                    appendValue = dataset.answerIds[0]
                }
            }
        }
        return appendValue
    }

    def discountRateLabels = { attrs ->
        Indicator indicator = attrs.indicator

        if ("LCC" == indicator.assessmentType) {
            def answers = message(code: 'results.discountRate') + "<br />"
            answers = answers + message(code: 'results.energyInflation') + "<br />"
            answers = answers + message(code: 'results.generalInflation') + "<br />"
            out << answers
        }
    }

    def discountRates = { attrs ->
        Entity entity = attrs.entity
        def datasets = entity?.datasets
        Indicator indicator = attrs.indicator
        CalculationRule calcRule = attrs.calculationRule
        DecimalFormat decimalFormat = userService.getDefaultDecimalFormat()
        decimalFormat.applyPattern('###,###.##')

        if ("LCC" == indicator.assessmentType && datasets && calcRule) {
            String answers
            ValueReference discountRateValueRef = calcRule.discountRateValueRef
            def discountRateQuestionId = calcRule.discountRate?.replace('$', '')

            if (discountRateValueRef) {
                Dataset discountRateDataset = valueReferenceService.getDatasetsForEntity(discountRateValueRef, entity)[0]
                answers = (discountRateDataset?.quantity ? decimalFormat.format(discountRateDataset?.quantity) : "0") + "%<br />"
            } else if (discountRateQuestionId) {
                Dataset discountRateDataset = datasets.find({ it.questionId == discountRateQuestionId })
                answers = (discountRateDataset?.quantity ? decimalFormat.format(discountRateDataset?.quantity) : "0") + "%<br />"
            } else {
                answers = "<br />"
            }
            ValueReference energyInflationValueRef = calcRule.energyInflationValueRef
            ValueReference waterInflationValueRef = calcRule.waterInflationValueRef
            def energyInflationQuestionId = calcRule.energyInflation?.replace('$', '')

            if (energyInflationValueRef) {
                Dataset energyInflationDataset = valueReferenceService.getDatasetsForEntity(energyInflationValueRef, entity)[0]
                answers = answers + (energyInflationDataset?.quantity ? decimalFormat.format(energyInflationDataset.quantity) : "0") + "%<br />"
            } else if (waterInflationValueRef) {
                Dataset waterInflationDataset = valueReferenceService.getDatasetsForEntity(waterInflationValueRef, entity)[0]
                answers = answers + (waterInflationDataset?.quantity ? decimalFormat.format(waterInflationDataset.quantity) : "0") + "%<br />"
            } else if (energyInflationQuestionId) {
                Dataset energyInflationDataset = datasets.find({ it.questionId == energyInflationQuestionId })
                answers = answers + (energyInflationDataset?.quantity ? decimalFormat.format(energyInflationDataset.quantity) : "0") + "%<br />"
            } else {
                answers = "<br />"
            }
            ValueReference generalInflationValueRef = calcRule.generalInflationValueRef
            def generalInflationQuestionId = calcRule.generalInflation?.replace('$', '')

            if (generalInflationValueRef) {
                Dataset generalInflationDataset = valueReferenceService.getDatasetsForEntity(generalInflationValueRef, entity)[0]
                answers = answers + (generalInflationDataset?.quantity ? decimalFormat.format(generalInflationDataset.quantity) : "0") + "%<br />"
            } else if (generalInflationQuestionId) {
                Dataset generalInflationDataset = datasets.find({ it.questionId == generalInflationQuestionId })
                answers = answers + (generalInflationDataset?.quantity ? decimalFormat.format(generalInflationDataset.quantity) : "0") + "%<br />"
            } else {
                answers = "<br />"
            }
            out << answers
        }
    }

    private Map getAveragesByDynamicDenominators(Map dynamicDenominatorScoresAsDouble) {
        Map averages = [:]
        Map scoreAmountByDenominator = [:]
        Map sumScoresByDenominator = [:]

        if (dynamicDenominatorScoresAsDouble) {
            dynamicDenominatorScoresAsDouble.each { Resource resource, Map<ObjectId, Double> scoresByDenominator ->
                scoresByDenominator?.values()?.each { Double score ->
                    if (score) {
                        Double existingSum = sumScoresByDenominator.get(resource)

                        if (existingSum) {
                            existingSum = existingSum + score
                            scoreAmountByDenominator.put(resource, (scoreAmountByDenominator.get(resource) + 1))
                        } else {
                            scoreAmountByDenominator.put(resource, 1)
                            existingSum = score
                        }
                        sumScoresByDenominator.put(resource, existingSum)
                    }
                }
            }
        }

        sumScoresByDenominator?.each { Resource resource, Double sum ->
            def divider = scoreAmountByDenominator?.get(resource)

            if (divider) {
                def average = sum / divider
                averages.put(resource, average)
            }
        }
        return averages
    }

    private String formatByFormattingDenominator(Indicator indicator, Denominator formattingDenominator, Double number, Entity entity = null) {
        DecimalFormat df = userService.getDefaultDecimalFormat()
        // df.applyPattern('###,###.00')
        String formattedScore

        if (number != null) {
            if (formattingDenominator && indicator) {
                Double multiplier = denominatorUtil.getMultiplierByDenominator(entity, formattingDenominator)

                if (multiplier) {
                    number = number * multiplier
                }
                formattedScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, formattingDenominator, number, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
            } else {
                formattedScore = df.format(number)
            }
        }
        return formattedScore
    }

    private Map getDynamicDenominatorsDifferencesToStandardDeviation(Map dynamicDenominatorScoresAsDouble) {
        Map differencesToStandardDeviation = [:]

        if (dynamicDenominatorScoresAsDouble) {
            List<Resource> denominators = dynamicDenominatorScoresAsDouble.keySet().toList()

            denominators?.each { Resource denominator ->
                Map<ObjectId, Double> scoresByEntityIds = dynamicDenominatorScoresAsDouble.get(denominator)

                scoresByEntityIds?.each { ObjectId entityId, Double score ->
                    if (score) {
                        List<Double> otherEntityScores = []
                        List<ObjectId> otherEntityIds = new ArrayList<ObjectId>(scoresByEntityIds.keySet().toList())
                        otherEntityIds.remove(entityId)

                        scoresByEntityIds.subMap(otherEntityIds)?.values()?.each { Double value ->
                            if (value) {
                                otherEntityScores.add(value)
                            }
                        }

                        if (otherEntityScores?.size() > 0) {
                            double[] scoresArray = otherEntityScores.toArray()
                            DescriptiveStatistics statistics = new DescriptiveStatistics(scoresArray)
                            // double maxValue = statistics.max
                            Double mean = statistics.mean
                            Double difference = mean - score
                            Boolean greaterThanMean = Boolean.FALSE
                            Double dOneStandardDeviation
                            Double differenceInPercents

                            if (difference < 0) {
                                difference = -difference
                                greaterThanMean = Boolean.TRUE
                            }
                            def oneStandardDeviation = configurationService.getConfigurationValue(Constants.APPLICATION_ID,
                                    ConfigName.ONE_STANDARD_DEVIATION.toString())

                            if (oneStandardDeviation && oneStandardDeviation.isNumber()) {
                                dOneStandardDeviation = oneStandardDeviation.toDouble()
                            }

                            if (greaterThanMean) {
                                differenceInPercents = difference / mean * 100
                            } else {
                                differenceInPercents = -(difference / mean * 100)
                            }
                            String differenceAsStr

                            if (differenceInPercents) {
                                differenceInPercents = Math.round(differenceInPercents)

                                if (dOneStandardDeviation) {
                                    if (differenceInPercents > dOneStandardDeviation) {
                                        differenceAsStr = " <span class='deviation' style='color: ${greaterThanMean ? 'red' : 'green'};'>(${greaterThanMean ? '+' : ''}${differenceInPercents.toInteger()} %)</span>"
                                    } else {
                                        differenceAsStr = "&nbsp;"
                                    }
                                } else {
                                    differenceAsStr = " <span class='deviation' style='color: ${greaterThanMean ? 'red' : 'green'};'>(${greaterThanMean ? '+' : ''}${differenceInPercents.toInteger()} %)</span>"
                                }
                            } else {
                                differenceAsStr = "&nbsp;"
                            }
                            Map existing = differencesToStandardDeviation.get(denominator)

                            if (existing) {
                                existing.put(entityId, differenceAsStr)
                            } else {
                                existing = [(entityId): differenceAsStr]
                            }
                            differencesToStandardDeviation.put(denominator, existing)
                        }
                    }
                }
            }
        }
        return differencesToStandardDeviation
    }

    private Map getDenominatorsDifferencesToStandardDeviation(Map denominatorScoresAsDouble) {
        Map differencesToStandardDeviation = [:]

        if (denominatorScoresAsDouble) {
            List<Denominator> denominators = denominatorScoresAsDouble.keySet().toList()

            denominators?.each { Denominator denominator ->
                Map<ObjectId, Double> scoresByEntityIds = denominatorScoresAsDouble.get(denominator)

                scoresByEntityIds?.each { ObjectId entityId, Double score ->
                    if (score) {
                        List<Double> otherEntityScores = []
                        List<ObjectId> otherEntityIds = new ArrayList<ObjectId>(scoresByEntityIds.keySet().toList())
                        otherEntityIds.remove(entityId)

                        scoresByEntityIds.subMap(otherEntityIds)?.values()?.each { Double value ->
                            if (value) {
                                otherEntityScores.add(value)
                            }
                        }

                        if (otherEntityScores?.size() > 0) {
                            double[] scoresArray = otherEntityScores.toArray()
                            DescriptiveStatistics statistics = new DescriptiveStatistics(scoresArray)
                            // double maxValue = statistics.max
                            Double mean = statistics.mean
                            Double difference = mean - score
                            Boolean greaterThanMean = Boolean.FALSE
                            Double dOneStandardDeviation
                            Double differenceInPercents

                            if (difference < 0) {
                                difference = -difference
                                greaterThanMean = Boolean.TRUE
                            }
                            def oneStandardDeviation = configurationService.getConfigurationValue(Constants.APPLICATION_ID,
                                    ConfigName.ONE_STANDARD_DEVIATION.toString())

                            if (oneStandardDeviation && oneStandardDeviation.isNumber()) {
                                dOneStandardDeviation = oneStandardDeviation.toDouble()
                            }

                            if (greaterThanMean) {
                                differenceInPercents = difference / mean * 100
                            } else {
                                differenceInPercents = -(difference / mean * 100)
                            }
                            String differenceAsStr

                            if (differenceInPercents) {
                                differenceInPercents = Math.round(differenceInPercents)

                                if (dOneStandardDeviation) {
                                    if (differenceInPercents > dOneStandardDeviation) {
                                        differenceAsStr = " <span class='deviation' style='color: ${greaterThanMean ? 'red' : 'green'};'>(${greaterThanMean ? '+' : ''}${differenceInPercents.toInteger()} %)</span>"
                                    } else {
                                        differenceAsStr = "&nbsp;"
                                    }
                                } else {
                                    differenceAsStr = " <span class='deviation' style='color: ${greaterThanMean ? 'red' : 'green'};'>(${greaterThanMean ? '+' : ''}${differenceInPercents.toInteger()} %)</span>"
                                }
                            } else {
                                differenceAsStr = "&nbsp;"
                            }
                            Map existing = differencesToStandardDeviation.get(denominator)

                            if (existing) {
                                existing.put(entityId, differenceAsStr)
                            } else {
                                existing = [(entityId): differenceAsStr]
                            }
                            differencesToStandardDeviation.put(denominator, existing)
                        }
                    }
                }

            }
        }
        return differencesToStandardDeviation
    }

    private Double internalScoreByDenominator(resourceOrQuestionIds, resourceId = null, Entity entity, Indicator indicator) {
        Double score

        if (resourceOrQuestionIds && entity && indicator) {
            List<Denominator> denominatorList = indicator.resolveDenominators

            if (denominatorList) {
                Denominator denominator
                def divider

                denominatorList?.each {
                    denominator = (Denominator) it
                    Map datasets = denominatorUtil.getDatasetsForDenominator(denominator, entity)

                    if (datasets) {
                        def indicatorScore
                        def datasetsAsList = []

                        datasets.each { String queryId, List<Dataset> dsets ->
                            if (dsets) {
                                datasetsAsList.addAll(dsets)
                            }
                        }

                        if (datasetsAsList) {
                            if (indicatorScore?.isNumber()) {
                                indicatorScore = indicatorScore.toDouble()

                                for (resourceOrQuestionId in resourceOrQuestionIds) {
                                    def denominDatasets

                                    if (resourceId) {
                                        denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.questionId == resourceOrQuestionId && resourceId.equals(d.resourceId) })
                                    } else {
                                        denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.questionId == resourceOrQuestionId })
                                    }

                                    if (!denominDatasets) {
                                        denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.resourceId == resourceOrQuestionId })
                                    }

                                    if (denominDatasets) {
                                        for (Dataset denominDataset : denominDatasets) {
                                            divider = divider ? divider * denominDataset.quantity : denominDataset.quantity
                                        }
                                    }
                                }

                                if (divider) {
                                    score = indicatorScore / divider
                                }
                            }
                        }
                    }
                }
            }
        }
        return score
    }

    private Map<String, Double> scoreByDenominator(List<String> resourceOrQuestionIds, String resourceId, Entity entity, Indicator indicator, Denominator denominator = null) {
        Double number = internalScoreByDenominator(resourceOrQuestionIds, resourceId, entity, indicator)
        String formattedNumber

        if (number) {
            formattedNumber = formatByFormattingDenominator(indicator, denominator, number, entity)
            return [(formattedNumber): number]
        } else {
            return [(message(code: "portfolio.denominator_missing")): null]
        }
    }

    def denominatorUnits = { attrs ->
        List<String> units = []
        Indicator indicator = attrs.indicator
        List<Entity> entities = attrs.entities
        String unitsAsOptions = ""

        if (indicator) {
            String localizedUnitIndicator = indicatorService.getDisplayUnit(indicator)
            if (indicator.resolveDenominators) {
                List<Denominator> denominatorList = indicator.getNonDynamicDenominatorList()
                List<Denominator> dynamicDenominatorList = indicator.getDynamicDenominatorList()
                Denominator displayDenominator = indicator.getDisplayDenominator()

                if (!displayDenominator) {
                    unitsAsOptions = "<option value=\"\">${localizedUnitIndicator}</option>"
                } else {
                    String label
                    String denomLabel = denominatorUtil.getLocalizedUnit(displayDenominator)
                    if (denomLabel) {
                        label = denomLabel
                    } else {
                        Map<String, List<Dataset>> datasets = denominatorUtil.getDatasetsForDenominator(displayDenominator, entity)

                        if (datasets) {
                            try {
                                datasets.each { String queryId, List<Dataset> dsets ->
                                    List<Dataset> datasetsWithResourceIds = dsets?.findAll({ it.resourceId })

                                    ResourceCache resourceCache = ResourceCache.init(datasetsWithResourceIds)
                                    for (Dataset dataset: datasetsWithResourceIds) {
                                        Resource resource = resourceCache.getResource(dataset)

                                        if (resource) {
                                            label = "${localizedUnitIndicator} / ${optimiResourceService.getLocalizedName(resource)} ${resource.unitForData ? resource.unitForData : ''}"

                                            if (label) {
                                                units.add(label)
                                                throw new Exception("Unit found")
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                    if (label) {
                        unitsAsOptions = "<option value=\"\">${label}</option>"
                    } else {
                        unitsAsOptions = "<option value=\"\">${localizedUnitIndicator}</option>"
                    }
                }

                if (entities) {
                    for (Entity entity in entities) {
                        if (denominatorList) {
                            if (displayDenominator) {
                                denominatorList = denominatorList.findAll({ !it.equals(displayDenominator) })
                            }

                            for (Denominator denominator in denominatorList) {
                                String label = "${denominatorUtil.getLocalizedUnit(displayDenominator) ?: localizedUnitIndicator}"

                                if (label && !units.contains(label)) {
                                    units.add(label)
                                }
                            }
                        }

                        if (dynamicDenominatorList) {
                            if (displayDenominator) {
                                dynamicDenominatorList = dynamicDenominatorList.findAll({
                                    !it.equals(displayDenominator)
                                })
                            }

                            for (Denominator denominator in dynamicDenominatorList) {
                                Map<String, List<Dataset>> datasets = denominatorUtil.getDatasetsForDenominator(denominator, entity)

                                if (datasets) {
                                    datasets.each { String queryId, List<Dataset> dsets ->
                                        List<Dataset> datasetsWithResourceIds = dsets?.findAll({ it.resourceId })

                                        ResourceCache resourceCache = ResourceCache.init(datasetsWithResourceIds)
                                        for (Dataset dataset: datasetsWithResourceIds) {
                                            Resource resource = resourceCache.getResource(dataset)

                                            if (resource) {
                                                String label = "${localizedUnitIndicator} / ${optimiResourceService.getLocalizedName(resource)} ${resource.unitForData ? resource.unitForData : ''}"

                                                if (label && !units.contains(label)) {
                                                    units.add(label)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (localizedUnitIndicator) {
                unitsAsOptions = "<option value=\"\">${localizedUnitIndicator}</option>"
            }
        }

        if (!units.isEmpty()) {
            units.each {
                unitsAsOptions = "${unitsAsOptions}<option value=\"${it}\">${it}</option>"
            }
        }
        out << unitsAsOptions
    }


    /**
     * Method to correctly display material Quantities for EPDs results page "Data Source" section
     * @param r : currently iterated resource
     * @param datasets : current EPD project's datasets
     * @param rowValues : object to be added to values list after completing all rows
     */
    private static void assignUnitQuantities(Resource r, Set<Dataset> datasets, List<String> rowValues) {
        Double quantity = datasets.find { dataset -> dataset?.manualId == r?.linkedDatasetManualIds?.get(0) }?.quantity
        String quantityUnit = ""

        if (quantity) {
            quantityUnit = "$quantity ${r?.unitForData ?: ''}"
        }

        rowValues.add(quantityUnit)
    }

}