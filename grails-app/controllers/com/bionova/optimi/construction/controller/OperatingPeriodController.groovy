/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.IndicatorUse
import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.ReportGenerationRule
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.IndicatorReportService
import org.grails.datastore.mapping.core.OptimisticLockingException

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class OperatingPeriodController extends ExceptionHandlerController {

    def entityService
    def indicatorService
    def queryService
    def datasetService
    def userService
    def newCalculationServiceProxy
    def helpConfigurationService
    def licenseService
    def optimiResourceService
    def questionService
    def verificationPointService
    def resultCategoryService
    def querySectionService
    def calculationProcessService
    IndicatorReportService indicatorReportService

    def newOperatingPeriod() {
        String entityId = params.entityId
        def entity = entityService.getEntityById(entityId, session)
        Boolean LCCSequenceProject = Boolean.FALSE
        def startGuide

        try {
            def indicatorsAdded = false

            if (!params.indicatorIds) {
                entity?.validOperatingLicenses?.each { License license ->
                    license.licensedIndicators?.each { Indicator indicator ->
                        if (IndicatorUse.OPERATING.toString() == indicator.indicatorUse &&
                                !entity?.indicatorIds?.contains(indicator.indicatorId)) {
                            entity?.indicatorIds?.add(indicator.indicatorId)
                            indicatorsAdded = true
                        }
                    }
                }
            }
            
            if (indicatorsAdded || params.indicatorIds) {
                entity = entityService.updateEntity(entity)
            }
            def operatingPeriod = new Entity()
            def year = Calendar.getInstance().get(Calendar.YEAR) - 1
            operatingPeriod.operatingPeriod = year
            operatingPeriod.entityClass = EntityClass.OPERATING_PERIOD.getType()
            operatingPeriod.parentEntityId = entity?.id
            operatingPeriod.parentName = entity?.name

            if (!entityService.operatingPeriodAlreadyFound(operatingPeriod)) {
                entityService.createEntity(operatingPeriod)
                startGuide = message(code: "entity.show.data_missing.guide")
            }
        } catch (OptimisticLockingException e) {
            flash.fadeErrorAlert = message(code: "error.optimisticLocking")
            loggerUtil.error(log, "OptimisticLockingException in createNew: " + e.getMessage())
        }
        List<String> indicatorIds = []
        List<Indicator> indicatorsChosen = []

        if (params.indicatorIds) {
            indicatorIds = params.list("indicatorIds")
            if (indicatorIds && entity?.indicators) {
                entity.indicators.each { Indicator indicator ->
                    if (indicatorIds.contains(indicator.indicatorId)) {
                        indicatorsChosen.add(indicator)
                    }

                }
            }
        }
        if (indicatorsChosen && indicatorsChosen.find({ it.indicatorQueries?.find({ it.projectLevel }) })) {
            LCCSequenceProject = Boolean.TRUE
        }
        if (LCCSequenceProject && entityId) {
            /* SW-1645 keep the commented code until after release 0.6.0
            Map<String, List<String>> queryAndLinkedIndicators = [:]
            List<String> projectLevelQueries = []

            indicatorsChosen.each { Indicator indicator ->
                indicator.getIndicatorQueries()?.each {
                    if (it.projectLevel) {
                        Query q = queryService.getQueryByQueryId(it.queryId)

                        if (q) {
                            def existing = queryAndLinkedIndicators.get(q.queryId)

                            if (existing) {
                                existing.add(indicator.indicatorId)
                            } else {
                                existing = [indicator.indicatorId]
                            }
                            queryAndLinkedIndicators.put(q.queryId, existing)

                            if (!projectLevelQueries.contains(q.queryId)) {
                                projectLevelQueries.add(q.queryId)
                            }
                        }
                    }
                }
            }
            entity = entityService.updateProjectLevelEntity(projectLevelQueries, null, queryAndLinkedIndicators, entity?.id?.toString())
            */

            String firstQueryId = entityService.determineFirstQueryId(entity, indicatorsChosen)

            /* SW-1645 keep the commented code until after release 0.6.0
            String firstQueryId

            if (!projectLevelQueries.isEmpty()) {
                firstQueryId = projectLevelQueries.first()
            }
            */

            if (firstQueryId) {
                redirect controller: "query", action: "form", params: [entityId: entityId, queryId: firstQueryId, projectLevel: true, newProject: true, periodLevelParamQuery:true]
            } else {
                chain(controller: "entity", action: "show", id: entity?.id, model: [designStartGuide: message(code: "entity.show.data_missing.guide")])
            }

        } else {
            chain(controller: "entity", action: "show", id: entity?.id, model: [operatingStartGuide: startGuide])
        }

    }

    def form() {
        Entity operatingPeriod
        def choosableYears = []
        def calendar = Calendar.instance
        def nextYear = calendar[Calendar.YEAR] + 4
        def entity = entityService.getEntityById(params.entityId, session)

        if (params.id) {
            operatingPeriod = entityService.getEntityById(params.id, session)
        } else {
            operatingPeriod = session?.getAttribute(SessionAttribute.OPERATING_PERIOD.getAttribute())
        }

        for (int i = 2005; i <= nextYear; i++) {
            choosableYears.add("${i}")
        }
        def originalOperatingPeriodId = params.originalOperatingPeriodId
        def originalOperatingPeriod = entityService.getEntityById(originalOperatingPeriodId, session)
        [entity: entity, operatingPeriod: operatingPeriod, choosableYears: choosableYears, originalOperatingPeriod: originalOperatingPeriod]
    }

    def save() {
        def entityId = params.parentEntityId
        def operatingPeriod

        if (params.id) {
            operatingPeriod = entityService.getEntityById(params.id, session)
            operatingPeriod.properties = params
        } else {
            operatingPeriod = new Entity(params)
            operatingPeriod.entityClass = EntityClass.OPERATING_PERIOD.getType()
        }
        def originalOperatingPeriodId = params.originalOperatingPeriodId

        if (operatingPeriod.validate()) {
            if (!entityService.operatingPeriodAlreadyFound(operatingPeriod)) {
                if (operatingPeriod.id) {
                    entityService.updateEntity(operatingPeriod)
                    flash.fadeSuccessAlert = g.message(code: "operatingPeriod.updated")
                } else {
                    operatingPeriod = entityService.createEntity(operatingPeriod)

                    if (originalOperatingPeriodId) {
                        def originalOperatingPeriod = entityService.getEntityById(originalOperatingPeriodId, session)
                        operatingPeriod = datasetService.copyDatasetsAndFiles(originalOperatingPeriod, operatingPeriod)
                        newCalculationServiceProxy.copyCalculationResults(originalOperatingPeriod.id, operatingPeriod.id)
                        flash.fadeSuccessAlert = message(code: "operatingPeriod.copied")
                    } else {
                        flash.fadeSuccessAlert = message(code: "operatingPeriod.created")
                    }
                }
                session?.removeAttribute(SessionAttribute.OPERATING_PERIOD.getAttribute())
                redirect(controller: "entity", action: "show", id: entityId)
            } else {
                flash.fadeErrorAlert = g.message(code: "operatingPeriod.duplicate")
                session?.setAttribute(SessionAttribute.OPERATING_PERIOD.getAttribute(), operatingPeriod)
                redirect(action: "form", params: [originalOperatingPeriodId: originalOperatingPeriodId, entityId: entityId])
            }
        } else {
            session?.setAttribute(SessionAttribute.OPERATING_PERIOD.getAttribute(), operatingPeriod)
            flash.fadeErrorAlert = renderErrors(bean: operatingPeriod)
            redirect(action: "form", params: [originalOperatingPeriodId: originalOperatingPeriodId, entityId: entityId])
        }
    }

    def cancel() {
        redirect(controller: "entity", action: "show", id: params.entityId)
    }

    def results() {
        Entity entity

        if (params.id) {
            entity = entityService.getEntityByIdReadOnly(params.id)
        } else {
            entity = entityService.getEntityByIdReadOnly(params.entityId)
        }
        Entity operating = entityService.getEntityByIdReadOnly(params.childEntityId)
        String indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Query basicQuery = queryService.getQueryByQueryId("basicQuery", true)
        List<Dataset> basicQueryDatasets = []
        String basicQueryTable = ''

        if (entity && operating && indicator) {
            Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(operating.id.toString(), indicatorId)

            if(isCalculationRunning){
                flash.permWarningAlert = g.message(code: "entity.calculation.running.result")
                return render(view: "/entity/results", model: [
                        isCalculationRunning: isCalculationRunning,
                        childEntity         : operating,
                        indicator           : indicator
                ])
            }

            def queries = queryService.getQueriesByIndicatorAndEntity(indicator, entity)
            def additionalInfos = getAdditionalInfos(operating, indicator, queries)
            List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
            List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
            def isPortfolio

            if (params.isPortfolio) {
                isPortfolio = true
            }
            def formattingFeatures
            formattingFeatures = ['useScientificNumbers': indicator.useScientificNumbers != null, 'showDecimals': indicator.showDecimals]
            def chosenFormatting = session?.getAttribute(SessionAttribute.RESULT_FORMATTING.toString())

            if (chosenFormatting && ((Map) chosenFormatting).keySet().contains(indicatorId)) {
                def formatting = chosenFormatting.get(indicatorId)

                if ("showDecimals".equals(formatting)) {
                    formattingFeatures.put(formatting, Boolean.TRUE)
                } else if ("useScientificNumbers".equals(formatting)) {
                    formattingFeatures.put(formatting, Boolean.TRUE)
                }
            }

            def licenses = entity?.getLicensesForIndicator(indicator)

            if (!licenses) {
                licenses = entity?.getExpiredLicensesForIndicator(indicator)
            }
            def nonProdLicenses

            if (licenses) {
                if (!licenses.find({ Constants.LicenseType.PRODUCTION.toString().equals(it.type) })) {
                    nonProdLicenses = licenses
                }
            }

            if(basicQuery){
                basicQueryDatasets= entity.datasets?.toList()?.findAll({ basicQuery.queryId.equals(it.queryId) })
                List<Question> allQuestionsBasicQuery = basicQuery.allQuestions
                basicQueryDatasets?.each {Dataset d ->
                    String questionName = allQuestionsBasicQuery.find({it.questionId?.equalsIgnoreCase(d.questionId)})?.localizedQuestion
                    String userAnswer =''
                    String name = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(d.answerIds[0], null, null))
                    if(!name){
                        userAnswer = d.answerIds[0].toString()
                    }else {
                        userAnswer = name

                    }
                    basicQueryTable += '<tr><th>'+ questionName +'</th><td>' + userAnswer + '</td></tr>'
                }
            }
            def calculationError = session?.getAttribute(SessionAttribute.CALCULATION_ERROR.toString())

            if (calculationError) {
                flash.errorAlert = calculationError
                session?.removeAttribute(SessionAttribute.CALCULATION_ERROR.toString())
            }
            List<CalculationResult> calculationResultList = operating?.getCalculationResultObjects(indicatorId, null, null)
            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(entity)
            List<CalculationRule> resolvedRules = []
            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(entity)
            List<ResultCategory> resolvedCategories = []
            IndicatorReportItem reportItem = indicatorReportService.getReportItemsAsReportItemObjects(entity, indicator.report?.reportItems)?.
                    find({ IndicatorReportItem reportItem -> "table".equals(reportItem?.type) })

            if (reportItem?.rules && calculationRules) {
                reportItem.rules.each { String ruleId ->
                    CalculationRule calculationRule = calculationRules.find({
                        ruleId.equals(it.calculationRuleId)
                    })

                    if (!calculationRule) {
                        calculationRule = calculationRules?.find({
                            ruleId.equals(it.calculationRule)
                        })
                    }

                    if (calculationRule) {
                        resolvedRules.add(calculationRule)
                    }
                }
            }

            if (reportItem?.categories && resultCategories) {
                reportItem.categories.each { String categoryId ->
                    ResultCategory resultCategory = resultCategories.find({ categoryId.equals(it.resultCategoryId) })

                    if (!resultCategory) {
                        resultCategory = resultCategories.find({ categoryId.equals(it.resultCategory) })
                    }

                    if (resultCategory) {
                        resolvedCategories.add(resultCategory)
                    }
                }
            }

            String graphLabels
            String graphDatasets
            List<CalculationRule> graphCalculationRulesForPieChart
            Integer scaleStartValue = 0
            Integer scaleSteps = 10
            Integer scaleStepWidth = 10
            def scaleOverride = false
            Boolean emptyDataset = Boolean.FALSE

            List<ResultCategory> graphCategories = (resolvedCategories ? resolvedCategories : resultCategories)?.
                    findAll({ !it.hideInGraphs })
            List<CalculationRule> graphRules = (resolvedRules ? resolvedRules : calculationRules)?.
                    findAll({ !it.hideInGraphs })
            graphDatasets = generateNewCalcGraphDatasets(graphCategories, graphRules)
            graphLabels = generateNewCalcGraphLabels(graphCategories, graphRules, calculationResultList)
            emptyDataset = graphDatasets != null && "[]".equals(graphDatasets) ? emptyDataset.TRUE : emptyDataset.FALSE
            graphCalculationRulesForPieChart = resolveCalculationRuleForPieChart(graphCategories, graphRules)
            int colSpan = 2
            if (calculationRules.size() <= 2) {
                colSpan = 1
            }

            List<Entity> childEntitiesForReportTabs  = entity?.operatingPeriods?.findAll({!operating.id.equals(it.id)})
            User user = userService?.getCurrentUser(true)
            def modifiable = entity?.modifiable && entity.getLicenseForIndicator(indicator)

            List<Query> allowedQueries = indicator?.getQueries(entity, Boolean.TRUE)
            allowedQueries = indicatorService.filterQueriesByProjectLevelIndicatorQueries(indicator, allowedQueries, false)

            List<CalculationRule> graphCalculationRules = indicator?.getGraphCalculationRuleObjects(entity)


            String arrayOfRulesWordGeneration
            Map<String,String> templatesAndCanvasIdsForReportGenerationMap

            if (graphCalculationRules && indicator?.wordReportGeneration && indicator?.reportGenerationRuleFromApplication) {
                graphCalculationRules.collect({ it.calculationRuleId })?.each { String rule ->
                    arrayOfRulesWordGeneration = arrayOfRulesWordGeneration ? arrayOfRulesWordGeneration + "," + rule : rule

                }
                indicator.getReportGenerationRuleFromApplication()?.each {ReportGenerationRule reportGenerationRule ->
                    String canvasIdsForReportGenerationTxt
                    reportGenerationRule.getAllChartUiIdentifications(indicator.getContentInjectionSetsFromApplication())?.each {
                        canvasIdsForReportGenerationTxt = canvasIdsForReportGenerationTxt ? canvasIdsForReportGenerationTxt + "," + it : it
                    }
                    if(templatesAndCanvasIdsForReportGenerationMap){
                        templatesAndCanvasIdsForReportGenerationMap.put(reportGenerationRule.reportGenerationRuleId,canvasIdsForReportGenerationTxt)
                    }else {
                        templatesAndCanvasIdsForReportGenerationMap = [(reportGenerationRule.reportGenerationRuleId):canvasIdsForReportGenerationTxt]
                    }
                }

            }
            List<HelpConfiguration> helpConfigurations = helpConfigurationService.getHelpConfigurationByTargetPageAndIndicatorId("Results", indicator?.indicatorId, Boolean.TRUE) ?:
                    helpConfigurationService.getConfigurationsForTargetPage("Results") ?: []

            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.BETA_FEATURES, Feature.CHAT_SUPPORT, Feature.ANNUAL_CHART, Feature.WORD_REPORT_GENERATION, Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS])
            Boolean annualChartLicensed = featuresAllowed.get(Feature.ANNUAL_CHART)
            Boolean sankeyAndOtherLicensed = entity.getAllowedFeatureByProjectLicense([Feature.SANKEY_AND_TREEMAP])
            Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
            Boolean chatSupportLicensed = featuresAllowed.get(Feature.CHAT_SUPPORT)
            Boolean wordReportGenerationLicensed = featuresAllowed.get(Feature.WORD_REPORT_GENERATION)
            Boolean showAndJumpToVPointLicensed = featuresAllowed.get(Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS)

            List<String> reportVisualItems = indicator.reportVisualisation
            Boolean bubbleChartRender = reportVisualItems?.contains("bubbleChart")
            Boolean overviewChartRender = reportVisualItems?.contains("overviewChartsSet")
            Boolean sankeyAndOtherChartRender = reportVisualItems?.contains("sankeyDiagram") && sankeyAndOtherLicensed
            Boolean annualChartRender = reportVisualItems?.contains("lineChartAnnual") && annualChartLicensed
            Boolean lifeCycleChartBreakdownRender = reportVisualItems?.contains("stackedBarChart")
            Boolean circularChartRender = reportVisualItems?.contains("circularEconomyGraph")
            Boolean treemapChartRender = reportVisualItems?.contains("treeMap") && sankeyAndOtherLicensed
            Boolean lifeCycleStageChartRender = reportVisualItems?.contains("barChartByStage")
            Boolean barChartByImpactCategoryStackedByStage = reportVisualItems?.contains("barChartByImpactCategoryStackedByStage") && sankeyAndOtherLicensed
            Boolean barChartByImpactCategoryStackedByMaterial = reportVisualItems?.contains("barChartByImpactCategoryStackedByMaterial") && sankeyAndOtherLicensed
            Boolean radialByElementByDesign = reportVisualItems?.contains("radialByElementByDesign") && sankeyAndOtherLicensed
            Boolean isComponentEntity = entityService.isComponentEntity(entity, featuresAvailableForEntity)
            Map<String,Boolean> chartsToRender = ["bubble":bubbleChartRender,"overview":overviewChartRender,"sankey":sankeyAndOtherChartRender,"annual":annualChartRender,"radial":radialByElementByDesign,"lifeCycle":lifeCycleStageChartRender,
                                                  "stackedStage":barChartByImpactCategoryStackedByStage,"stackedMat":barChartByImpactCategoryStackedByMaterial,"treemap":treemapChartRender,"circular":circularChartRender,"breakdown":lifeCycleChartBreakdownRender]

            List<String> classificationListForIndicator = indicator.classificationsMap
            Query additionalQuestionQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            Map<String,Question> additionalQuestions = classificationListForIndicator?.collectEntries {[(it):questionService.getQuestion(additionalQuestionQuery,it)]}
            String defaultClassQuesId = classificationListForIndicator?.size() > 1 ? classificationListForIndicator?.get(0) : ""
            Question groupingQuestion = additionalQuestions?.get(defaultClassQuesId)
            String defaultFallbackClassification = classificationListForIndicator?.size() == 1 && classificationListForIndicator?.contains(Constants.ORGANIZATION_CLASSIFICATION_ID) ? message(code: "default_class") : null
            // for showing verification points feature
            boolean isIndicatorHavingVerificationPoints = showAndJumpToVPointLicensed ? verificationPointService.isIndicatorHavingVerificationPoints(indicator, entity) : false
            List<Query> designQueries = indicator.sortQueriesByIndicatorQuery(queries)

            render view: "/entity/results", model: [entity                                     : entity,
                                                    modifiable                                 : modifiable,
                                                    childEntity                                : operating,
                                                    indicator                                  : indicator,
                                                    queries                                    : queries,
                                                    designQueries                              : designQueries,
                                                    chartsToRender                             : chartsToRender,
                                                    additionalInfos                            : additionalInfos,
                                                    isPortfolio                                : isPortfolio,
                                                    formattingFeatures                         : formattingFeatures,
                                                    wordReportGenerationLicensed               : wordReportGenerationLicensed,
                                                    type                                       : "operating",
                                                    nonProdLicenses                            : nonProdLicenses,
                                                    showPoweredBy                              : true,
                                                    annualChartLicensed                        : annualChartLicensed,
                                                    showAndJumpToVPointLicensed                : showAndJumpToVPointLicensed,
                                                    calculationRules                           : calculationRules,
                                                    betaFeaturesLicensed                       : betaFeaturesLicensed,
                                                    chatSupportLicensed                        : chatSupportLicensed,
                                                    isIndicatorHavingVerificationPoints        : isIndicatorHavingVerificationPoints,
                                                    graphLabels                                : graphLabels,
                                                    graphDatasets                              : graphDatasets,
                                                    emptyDataset                               : emptyDataset,
                                                    resultCategories                           : resultCategories,
                                                    groupingQuestion                           : groupingQuestion,
                                                    colSpan                                    : colSpan,
                                                    scaleStartValue                            : scaleStartValue,
                                                    scaleSteps                                 : scaleSteps,
                                                    scaleStepWidth                             : scaleStepWidth,
                                                    scaleOverride                              : scaleOverride,
                                                    defaultFallbackClassification              : defaultFallbackClassification,
                                                    childEntitiesForReportTabs                 : childEntitiesForReportTabs,
                                                    user                                       : user,
                                                    allowedQueries                             : allowedQueries,
                                                    graphCalculationRules                      : graphCalculationRules,
                                                    graphCalculationRulesForPieChart           : graphCalculationRulesForPieChart,
                                                    arrayOfRulesWordGeneration                 : arrayOfRulesWordGeneration,
                                                    helpConfigurations                         : helpConfigurations,
                                                    basicQueryTable                            : basicQueryTable,
                                                    templatesAndCanvasIdsForReportGenerationMap: templatesAndCanvasIdsForReportGenerationMap,
                                                    isComponentEntity                          : isComponentEntity,
                                                    isCalculationRunning                       : isCalculationRunning
            ]
        } else {
            redirect controller: "main"
        }
    }

    private String generateNewCalcGraphLabels(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules, List<CalculationResult> calculationResultList) {
        String labels = "["
        int index = 0
        if (resultCategories && calculationRules && calculationResultList) {
            resultCategories?.each { ResultCategory category ->
                    labels = "${labels}${index > 0 ? ', ' : ''}\"${abbr(value: resultCategoryService.getLocalizedShortName(category, 15))}\""
                    index++
            }
        }
        labels = "${labels}]"
        return labels
    }

    private String generateNewCalcGraphDatasets(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules) {
        String indicatorId = params.indicatorId
        List<String> strokeAndPointColors = ['rgba(127,196,204,1)', 'rgba(151,187,205,1)', 'gray', 'rgba(255,98,89,1)', 'rgba(0,128,0,1)', 'rgba(125,197,150,1)', 'rgba(115,49,70,0.8)', 'rgba(243,153,131,1)', 'rgba(0,26,101,0.8)']
        String datasets = "["
        Entity period = entityService.getEntityByIdReadOnly(params.childEntityId)

        if (resultCategories && calculationRules && period) {

            List<CalculationResult> resultsByIndicatorId = period?.getCalculationResultObjects(indicatorId, null, null)
            int colorIndex = 0

            calculationRules?.each { CalculationRule calculationRule ->
                if (colorIndex + 1 > strokeAndPointColors.size()) {
                    colorIndex = 0
                }

                String escapedForFrench = calculationRule.localizedName.replaceAll("\'", "\\’")


                Map<String, Double> scores = new LinkedHashMap<String, Double>()
                Double totalScore = (Double) period.getTotalResult(indicatorId, calculationRule.calculationRuleId, Boolean.FALSE)



                resultCategories?.each { ResultCategory resultCategory ->
                    Double score = period.getResult(indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsByIndicatorId)

                    if (score && score != null && totalScore != null && totalScore != 0) {
                        scores.put(resultCategory.resultCategory, (score / totalScore * 100))
                    } else {
                        if(totalScore !=null && totalScore > 0){
                            score = 0
                            scores.put(resultCategory.resultCategory, (score / totalScore * 100))

                        }
                    }
                }

                if (scores && !scores.isEmpty()) {
                    datasets = "${datasets}{label: \"${escapedForFrench}\","
                    datasets = "${datasets}  backgroundColor: \"${strokeAndPointColors.get(colorIndex)}\",\n" +
                            "                borderColor: \"black\",\n"
                    datasets = "${datasets}data: " + resultCategories.collect { scores?.get(it.resultCategory)?.round(0) ?: 0 }

                    datasets = "${datasets}},"
                    colorIndex++
                }
            }
        }
        datasets = "${datasets}]"

        return datasets
    }

    private List<String> getAdditionalInfos(entity, indicator, queries) {
        def additionalInfos = []

        queries?.each { query ->
            query.sections?.each { section ->
                section.questions?.each { question ->
                    def additionalInfo = getAdditionalInfo(entity, indicator, query, section, question)

                    if (additionalInfo) {
                        additionalInfos.add(additionalInfo)
                    }
                }

                querySectionService.getIncludedSections(section.includeSectionIds)?.each { includedSection ->
                    includedSection.questions?.each { question ->
                        def additionalInfo = getAdditionalInfo(entity, indicator, query, includedSection, question)

                        if (additionalInfo) {
                            additionalInfos.add(additionalInfo)
                        }
                    }
                }
            }
        }
        return additionalInfos
    }

    private String getAdditionalInfo(entity, indicator, query, section, question) {
        String additionalInfo = ""

        if (question.showAnswerInResults) {
            additionalInfo = resultQuestionAnswer(entity: entity, indicatorId: indicator.indicatorId, queryId: query.queryId, sectionId: section.sectionId, questionId: question.questionId)?.toString()?.trim()
        }
        return additionalInfo
    }

    def remove() {
        try {
            entityService.delete(params.id)
            flash.fadeSuccessAlert = g.message(code: "operatingPeriod.deleted")
        } catch (Exception e) {
            flash.fadeErrorAlert = "Error in deleting operating period"
        }
        redirect(controller: "entity", action: "show", id: params.entityId)
    }

    def graphs() {
        Entity entity = entityService.readEntity(params.entityId)
        List<Entity> operatingPeriods = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())
        def indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.OPERATING.toString())
        def scores = [:]
        def normalizedScores = [:]
        def validScores = [:]
        def showSummary = false
        def validScoresByIndicator = [:]
        def nonProdLicenses = []

        indicators?.each { indicator ->
            CalculationRule rule = indicator?.getResolveCalculationRules(entity)?.find({ it.calculationRule })
            ResultCategory category = indicator?.getResolveResultCategories(entity)?.find({ it.resultCategory })
            def operatingPeriodScores = [:]

            operatingPeriods?.each { operatingPeriod ->

                def operatingScore = operatingPeriod.getResult(indicator?.indicatorId, rule?.calculationRuleId, category?.resultCategoryId)

                if (operatingScore) {
                    def score = operatingScore.toString()
                    operatingPeriodScores.put((operatingPeriod.id), score && score.isNumber() ? score : "0")

                    if (score?.isNumber()) {
                        validScoresByIndicator.put(indicator, true)

                        if (validScores.get(operatingPeriod) == null) {
                            validScores.put(operatingPeriod, true)
                        }
                    }
                } else {
                    validScores.put(operatingPeriod, false)
                    operatingPeriodScores.put((operatingPeriod.id), message(code: "graphs.indicator.incomplete"))
                }
            }
            scores.put(indicator.indicatorId, operatingPeriodScores)
            def maxScore = 0
            def maxScoreKey

            operatingPeriods?.each { operatingPeriod ->
                def score = scores.get(indicator.indicatorId)?.get(operatingPeriod.id)

                if (score && score.isNumber() && score.toDouble() >= maxScore) {
                    maxScore = score.toDouble()
                    maxScoreKey = operatingPeriod.id
                }
            }
            operatingPeriodScores = [:]

            operatingPeriods?.each { operatingPeriod ->
                if (maxScoreKey == operatingPeriod.id) {
                    operatingPeriodScores.put((operatingPeriod.id), "100")
                } else {
                    def score = scores.get(indicator.indicatorId)?.get(operatingPeriod.id)

                    if (score && score.isNumber()) {
                        score = score.toDouble()
                        score = score / maxScore * 100
                        operatingPeriodScores.put((operatingPeriod.id), "" + score.toInteger())
                    }
                }
            }
            normalizedScores.put(indicator.indicatorId, operatingPeriodScores)
            def licenses = entity?.getLicensesForIndicator(indicator)

            if (licenses) {
                if (!licenses.find({ Constants.LicenseType.PRODUCTION.toString().equals(it.type) })) {
                    licenses.each { License license ->
                        if (!nonProdLicenses.contains(license)) {
                            nonProdLicenses.add(license)
                        }
                    }
                }
            }
        }

        if (validScores?.values()?.contains(true)) {
            showSummary = true
        }
        render view: "/entity/graphs", model: [entities              : operatingPeriods, indicators: indicators, scores: scores,
                                               normalizedScores      : normalizedScores, entity: entity, type: "periods", showSummary: showSummary,
                                               validScoresByIndicator: validScoresByIndicator, showPoweredBy: true, nonProdLicenses: nonProdLicenses]
    }

    def lock() {
        def entityId = params.entityId
        def periodId = params.periodId
        Boolean superUserLock = Boolean.FALSE

        if (params.superUser && userService.getSuperUser(userService.getCurrentUser())) {
            superUserLock = Boolean.TRUE
        }
        entityService.lockEntity(periodId, superUserLock)
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    def unlock() {
        def entityId = params.entityId
        def periodId = params.periodId
        Boolean superUserUnlock = Boolean.FALSE

        if (params.superUser && userService.getSuperUser(userService.getCurrentUser())) {
            superUserUnlock = Boolean.TRUE
        }
        entityService.unlockEntity(periodId, superUserUnlock)
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    private List<CalculationRule> resolveCalculationRuleForPieChart(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules) {
        String indicatorId = params.indicatorId
        Entity period = entityService.getEntityByIdReadOnly(params.childEntityId)
        List<CalculationRule> calculationRulesForPieChart = []
        if (resultCategories && calculationRules && period) {

            List<CalculationResult> resultsByIndicatorId = period?.getCalculationResultObjects(indicatorId, null, null)
            calculationRules?.each { CalculationRule calculationRule ->

                Double totalScore = (Double) period.getTotalResult(indicatorId, calculationRule.calculationRuleId, Boolean.FALSE)
                int counter = 0

                resultCategories?.each { ResultCategory resultCategory ->
                    Double score = period.getResult(indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsByIndicatorId)

                    if (score && score != null && totalScore != null && totalScore != 0) {
                       counter ++
                    }
                }

                if (counter > 1){
                    calculationRulesForPieChart.add(calculationRule)
                }
            }
        }

        return calculationRulesForPieChart
    }
}
