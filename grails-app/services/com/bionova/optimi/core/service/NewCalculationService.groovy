package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.AppliedNmdScaling
import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.construction.Constants as constructionConstants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.util.DenominatorUtil
import com.bionova.optimi.core.service.CarbonDesigner3DService
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import grails.util.Holders
import groovy.transform.CompileStatic
import org.apache.commons.collections.CollectionUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.OptimisticLockingException

import java.lang.reflect.Method

/**
 * @author Pasi-Markus Mäkelä
 */

class NewCalculationService {
    private static final List<String> HIERARCHY_PARAMETERS = [
            'impactGWPdirectFailoverGetter',
            'impactGWPlifeCycleFailoverGetter',
            'impactGWPA1FailoverGetter',
            'impactGWPA2TotalFailoverGetter',
            'impactGWPA2FossilFailoverGetter'
    ]
    private static final String D_STAGE = "D"

    def entityService
    def indicatorService
    def loggerUtil
    def unitConversionUtil
    def optimiResourceService
    def messageSource
    def queryService
    def constructionService
    def licenseService
    def lcaCheckerService
    def eolProcessService
    def userService
    def nmdApiService
    def domainClassService
    def datasetService
    def applicationService
    def resourceTypeService
    def nmdCalculationService
    def resultCategoryService
    def numberUtil
    def configurationService
    def valueReferenceService
    def resourceService
    def calculationResultService
    def calculationProcessService
    def denominatorService
    def resultManipulatorService
    def carbonDesigner3DService
    FlashService flashService
    QuestionService questionService
    CalculationMessageService calculationMessageService

    static transactional = "mongo"
    //static scope = "session"

    /**
     * Triggers calculation for other tools the user is currently not saving but share the same query
     *
     * @param entityId the database id of the design being saved
     * @param skippedIndicatorId indicatorId of the tool thats already been calculated
     * @param savedQueryId the id of the query user has saved
     * @param parentEntity the project or parent entity where the desings are
     */

    Entity calculateOtherIndicatorsIfNeeded(String entityId, String skippedIndicatorId, String savedQueryId,
                                                Entity parentEntity) {
        Entity entity = entityService.getEntityById(entityId)
        List<Indicator> indicators = parentEntity?.indicators

        if (indicators) {
            Indicator skippedIndicator = indicators.find({ Indicator indicator -> indicator.indicatorId == skippedIndicatorId })

            if (skippedIndicator) {
                indicators.remove(skippedIndicator)
            }
            def indicatorsWithSameQuery = []

            if (indicators) {
                for (Indicator indicator in indicators) {
                    if ("complex" == indicator.assessmentMethod) {
                        Set queryIds = []

                        indicator.getQueries(entity)?.each { Query query ->
                            queryIds.add(query.queryId)
                        }

                        if (savedQueryId && queryIds?.contains(savedQueryId)) {
                            indicatorsWithSameQuery.add(indicator)
                        }
                    }
                }
            }

            for (Indicator indicator in indicatorsWithSameQuery){
                calculationProcessService.createCalculationProcessAndSave(entityId?.toString(), parentEntity?.id?.toString(),  indicator.indicatorId)
            }

            indicatorsWithSameQuery?.each { Indicator indicator ->
                log.info("Calculating for ${indicator.indicatorId} indicator.")
                entity = calculate(entityId, indicator.indicatorId, parentEntity)
            }
        }
        return entity
    }

    Entity calculateOtherIndicatorsIfNeeded(List<Entity> entities, String skippedIndicatorId, String savedQueryId,
                                            Entity parentEntity) {
        List<Indicator> indicators = parentEntity?.indicators

        if (indicators) {
            Indicator skippedIndicator = indicators.find({ Indicator indicator -> indicator.indicatorId == skippedIndicatorId })
            Set<Indicator> indicatorsWithSameQuery = []

            if (skippedIndicator) {
                indicators.remove(skippedIndicator)
            }

            if (indicators) {
                for (Indicator indicator in indicators) {
                    if ("complex" == indicator.assessmentMethod) {
                        for (Entity entity in entities){
                            Set queryIds = []

                            indicator.getQueries(entity)?.each { Query query ->
                                queryIds.add(query.queryId)
                            }

                            if (savedQueryId && queryIds?.contains(savedQueryId)) {
                                calculationProcessService.createCalculationProcessAndSave(entity?.id?.toString(), parentEntity?.id?.toString(),  indicator.indicatorId)
                                indicatorsWithSameQuery.add(indicator)
                            }
                        }
                    }
                }
            }

            for (Entity entity in entities) {
                indicatorsWithSameQuery?.each { Indicator indicator ->
                    log.info("Calculating for ${indicator.indicatorId} indicator.")
                    if(!entity.entityClass.equals(Constants.EntityClass.CARBON_DESIGN.toString()) ){
                        calculate(entity.id.toString(), indicator.indicatorId, parentEntity)
                    }else{
                        Set<Dataset> carbonDesignResourceDatasets = entity.datasets?.findAll({ dataset -> !dataset.projectLevelDataset })?.collect({ it as Dataset })
                        CarbonDesign carbonDesign = carbonDesigner3DService.calculateDatasetsWithTempEntity(entity as CarbonDesign, carbonDesignResourceDatasets, indicator, parentEntity)
                        carbonDesign.datasets = carbonDesign.datasets.sort { a, b -> a.seqNr <=> b.seqNr }
                        carbonDesign.buildingElements.forEach({ CD3DBuildingElementsPayload element ->
                            carbonDesigner3DService.calculateElementCo2eImpact(carbonDesign, element)
                        })
                        carbonDesigner3DService.calculateDesignCo2eImpact(carbonDesign)
                        carbonDesign.save(flush: true)
                        calculationProcessService.deleteCalculationProcess(entity.id.toString(), parentEntity.id.toString(), indicator.indicatorId)
                    }
                }
            }
        }
    }

    /**
     * Admin feature to compare different material impacts
     *
     * @param indicator admin chooses the tool
     * @param calculationRules admin chooses the rules to calculate
     * @param comparisonCategories admin chooses the categories to compare
     * @param collectedDatasets list of the materials Resource objects
     */
    @Secured(["ROLE_AUTHENTICATED"])
    List<CalculationResult> resourceComparison(Indicator indicator, List<CalculationRule> calculationRules,
                                               List<ResultCategory> comparisonCategories, List<Dataset> collectedDatasets) {
        Entity temp = new Entity()

        if (indicator && comparisonCategories && collectedDatasets) {
            PreparedDenominator preparedDenominator = new PreparedDenominator(overallDenominator: 1)
            ResourceCache resourceCache = ResourceCache.init(collectedDatasets, true)
            EolProcessCache eolProcessCache = EolProcessCache.init()
            dataConversion(collectedDatasets, false, false, false, resourceCache)
            calculateMass(collectedDatasets, resourceCache)
            List<CalculationResult> resultsByScenarios = calculateResultsForRules(null, indicator, calculationRules,
                    comparisonCategories, collectedDatasets, null, null, null,
                    preparedDenominator, false, null, resourceCache, eolProcessCache)
            resultsByScenarios.removeAll({ !it.calculationRuleId })
            setEntityResults(temp, resultsByScenarios, indicator, comparisonCategories, calculationRules,
                    null, true, preparedDenominator, collectedDatasets, resourceCache)
        }
        return temp.tempCalculationResults
    }

    /**
     * Feature to show the calculation formula for the user, triggers calculation for that specific dataset in
     * users design. The formula is not saved to the database because it would take too much resources to store,
     * so dynamically calculate it again when user wants to see it.
     *
     * @param entity the current design
     * @param parentEntity the current project where the design belongs to
     * @param indicator the tool
     * @param calculationRules usually user can click to show only one rule / category, but if its a virtual rule, it requires the other rules linked to the virtual rule
     * @param resultCategories usually user can click to show only one rule / category, but if its a virtual category, it requires the other categories linked to the virtual category
     * @param dataset the material row user wants to see the calculation for
     * @param resultCategoryId the actual resultcategory user wanted to see
     * @param calculationRuleId the actual rule user wanted to see
     */
    CalculationResult dynamicCalculationFormulaResult(Entity entity, Entity parentEntity, Indicator indicator,
                                                      List<CalculationRule> calculationRules, List<ResultCategory> resultCategories,
                                                      Dataset dataset, String resultCategoryId, String calculationRuleId) {
        Entity temp = new Entity()

        if (indicator && calculationRules && resultCategories && dataset && entity && parentEntity) {
            // usually from collect data for calculation
            dataset.resultCategoryIds = resultCategories.findAll({ !it.virtual }).collect({ it.resultCategoryId })
            List<Dataset> collectedDatasets = [dataset]

            if (indicator.assessmentPeriodValueReference) {
                List<Dataset> assessmentDataset = valueReferenceService.getDatasetsForEntity(indicator.assessmentPeriodValueReference, entity)

                if (assessmentDataset) {
                    temp.datasets = assessmentDataset
                }
            }
            ResourceCache resourceCache = ResourceCache.init(collectedDatasets, true)
            EolProcessCache eolProcessCache = EolProcessCache.init()
            PreparedDenominator preparedDenominator = prepareDenominators(entity, parentEntity, indicator, resourceCache)
            nmdCalculationService.setAppliedScalingToPreResultFormula(collectedDatasets)
            dataConversion(collectedDatasets, false, false, false, resourceCache)
            calculateMass(collectedDatasets, resourceCache)
            List<CalculationResult> resultsByScenarios = calculateResultsForRules(entity, indicator, calculationRules,
                    resultCategories, collectedDatasets, null, null, parentEntity,
                    preparedDenominator, false, null, resourceCache, eolProcessCache)
            resultsByScenarios?.removeAll({ !it.calculationRuleId })
            setEntityResults(temp, resultsByScenarios, indicator, resultCategories, calculationRules, null, true,
                    preparedDenominator, collectedDatasets, resourceCache)
        }
        return temp.tempCalculationResults?.find({resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)})
    }

    /**
     * The main calculation function. Handles calculating all designs results
     *
     * @param entityId the design to be calculated
     * @param indicatorId the tool to be calculated
     * @param parentEntity the project object
     * @param entity if entityId not given you can give the entity/design object
     * @param temporary temporary calculation is not persisted in the database
     * @param calculationRuleIdsToCalculate if you want to calculate only specific rules, usually just given for performance reasons, if only some specific rule needs to be shown
     * @param byPassDenominator bypasses denominator
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    Entity calculate(def entityId, String indicatorId, Entity parentEntity, Entity entity = null, Boolean temporary = Boolean.FALSE,
                     List<String> calculationRuleIdsToCalculate = null, Boolean byPassDenominator = Boolean.FALSE) {
        long now = System.currentTimeMillis()
        loggerUtil.info(log, "NEW CALCULATION: Started calculating results for Entity: ${parentEntity?.name} and Indicator: ${indicatorId}")

        // check licenses, to be used later
        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.LCA_CHECKER, Feature.MONTHLY_DATA, Feature.QUARTERLY_DATA], null, true)
        Boolean monthlyDataLicensed = featuresAllowed.get(Feature.MONTHLY_DATA)
        Boolean quarterlyDataLicensed = featuresAllowed.get(Feature.QUARTERLY_DATA)
        // check if trial license (valid or expired) has been applied to project
        Boolean trialCalculation = parentEntity?.isFreeTrial
        //check if process was already created in calculateOtherIndicatorsIfNeeded
        CalculationProcess calculationProcess = calculationProcessService.findCalculationProcessByEntityIdAndIndicatorIdAndStatus(
                entityId?.toString(), indicatorId, CalculationProcess.CalculationStatus.IN_PROGRESS.name
        )

        try {
            if ((entityId || entity) && indicatorId) {
                if (!entity) {
                    entity = entityService.getEntityById(entityId)
                }
                Indicator indicator = indicatorService.getIndicatorWithoutCache(indicatorId)

                if (indicator && entity && !entity.disabledIndicators?.contains(indicator.indicatorId)) {
                    /**
                     * Get from indicator (configuration):
                     *  resultCategories - which resources to include in the calculation / from which question.
                     *  calculationRules - which resource parameter to include. As name, these basically tell how to calculate.
                     *
                     *  There are virtual resultCategories / calculationRules which are intermediaries used to combine other resultCategories / calculationRules.
                     */
                    if(!calculationProcess){
                        calculationProcess = calculationProcessService.createCalculationProcessAndSave(entity.id?.toString(), entity?.parentEntityId?.toString(), indicatorId)
                        if (calculationProcess) {
                            loggerUtil.debug(log, "NEW CALCULATION: calculationProcess creation time: ${calculationProcess.startTime}")
                        }
                    }

                    List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parentEntity)
                    List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parentEntity)

                    // if project is trial (has trial license), remove the calculationRules that should be hidden
                    if (trialCalculation && calculationRules) {
                        calculationRules = calculationRules.findAll({ !it.hideInTrial })
                    }

                    // if project is trial (has trial license), remove the resultCategories that should be hidden
                    if (trialCalculation && resultCategories) {
                        resultCategories = resultCategories.findAll({ !it.hideInTrial })
                    }

                    if (resultCategories && calculationRules) {
                        List<String> queryIds = indicator.getQueries(entity)?.collect({ Query query -> query.queryId })

                        /**
                         * Get list of resultCategories that is used for comparison.
                         * This list is handled separately later in handleComparisonCategories(). Ticket 5415
                         */
                        List<ResultCategory> comparisonCategories = resultCategories?.findAll({
                            it.comparison
                        })

                        // remove the resultCategories used for comparison.
                        if (resultCategories && comparisonCategories) {
                            resultCategories.removeAll(comparisonCategories)
                        }

                        /**
                         * Use only calculationRules defined in calculationRuleIdsToCalculate (if any)
                         * also get all the nested virtual rules
                         */
                        if (calculationRuleIdsToCalculate && calculationRules) {
                            List<CalculationRule> filteredRules = calculationRules.findAll({ calculationRuleIdsToCalculate.contains(it.calculationRuleId) })
                            List<CalculationRule> virtualRules = []

                            if (filteredRules && filteredRules.find({ it.virtual })) {
                                List<String> neededVirtualRules = filteredRules.findAll({ it.virtual })?.collect({ it.virtual })?.flatten()?.unique()
                                List<CalculationRule> filteredVirtualRules = calculationRules.findAll({ neededVirtualRules.contains(it.calculationRuleId) })

                                if (filteredVirtualRules) {
                                    if (filteredVirtualRules.find({ it.virtual })) {
                                        List<String> neededNestedVirtualRules = filteredVirtualRules.findAll({ it.virtual })?.collect({ it.virtual })?.flatten()?.unique()
                                        List<CalculationRule> filterednestedVirtualRules = calculationRules.findAll({ neededNestedVirtualRules.contains(it.calculationRuleId) })
                                        if (filterednestedVirtualRules) {
                                            virtualRules.addAll(filterednestedVirtualRules)
                                        }
                                        virtualRules.addAll(filteredVirtualRules)
                                    } else {
                                        virtualRules.addAll(filteredVirtualRules)
                                    }
                                }
                                calculationRules = virtualRules
                                calculationRules.addAll(filteredRules)
                                calculationRules = calculationRules.unique({ it.calculationRuleId })
                            } else {
                                calculationRules = filteredRules
                            }
                        }

                        /**
                         * Finish fetching the needed configurations at this point.
                         * Start fetching datasets (user inputs, materials, etc).
                         * Old results are removed and replaced by new results.
                         */
                        if (queryIds && resultCategories && calculationRules) {
                            loggerUtil.info(log, "NEW CALCULATION: Finished handling parameters: ${System.currentTimeMillis() - now} ms")
                            long now2 = System.currentTimeMillis()

                            List<Dataset> datasets = entity.datasets?.findAll({ queryIds.contains(it.queryId) })?.toList()
                            if (!datasets) {
                                loggerUtil.warn(log, "Datasets of design (EntityId: ${entity.id}) is empty after filtering out datasets which don't belong to queries of indicator (IndicatorId: ${indicator.indicatorId}). It is unexpected!")
                            }
                            ResourceCache resourceCache = ResourceCache.init(datasets, true)
                            resourceCache.initForCalculation()
                            EolProcessCache eolProcessCache = EolProcessCache.init()
                            loggerUtil.info(log, "NEW CALCULATION: Preload reources: ${System.currentTimeMillis() - now2} ms")

                            removeExistingResultsByIndicator(entity, indicator.indicatorId, indicator.displayResult)
                            loggerUtil.info(log, "NEW CALCULATION: removed existing results from entity: ${System.currentTimeMillis() - now2} ms")
                            now2 = System.currentTimeMillis()

                            /**
                             * Prepare the datasets for calculations >>> collectedDatasets
                             *  - convert the calculatedQuantity to Kg (if it's not in Kg), details saved in dataset.preResultFormula
                             *  - convert imperial units to european unit
                             *  - calculate mass (in kg)
                             *  - collect the datasets used for this tool
                             *
                             * Get denominators for calculations (dividers for results)
                             */
                            if (datasets) {
                                applyNmdScalingMultiplier(datasets, indicator, resourceCache)
                                dataConversion(datasets, monthlyDataLicensed, quarterlyDataLicensed, indicator.evaluateNonResourceAnswers, resourceCache)
                                calculateMass(datasets, resourceCache)
                                PreparedDenominator preparedDenominator = prepareDenominators(entity, parentEntity, indicator, resourceCache)
                                loggerUtil.info(log, "NEW CALCULATION: PREPAREDDENOMINATOR: ${indicator.indicatorId}, overallDenomn: ${preparedDenominator.overallDenominator}, Dynamic denoms: ${preparedDenominator.denominators}, assessment: ${preparedDenominator.assessmentPeriod}")
                                Set<String> indicatorAdditionalQuestionIds = questionService.getAdditionalQuestionIdsForIndicator(indicator, parentEntity)
                                loggerUtil.info(log, "NEW CALCULATION: Finished getting additional questions for calculation: ${System.currentTimeMillis() - now2} ms")
                                List<Dataset> collectedDatasets = collectDataForCalculation(entity, parentEntity, datasets,
                                        indicator, resultCategories, indicatorAdditionalQuestionIds, resourceCache)
                                loggerUtil.info(log, "NEW CALCULATION: Finished collecting datasets for calculation: ${System.currentTimeMillis() - now2} ms")
                                now2 = System.currentTimeMillis()

                                /**
                                 * Start calculations.
                                 * LCA and LCC are calculated by two different methods.
                                 */
                                if (collectedDatasets) {
                                    List<CalculationResult> resultsByScenarios

                                    if ("LCC".equalsIgnoreCase(indicator.assessmentType)) {
                                        resultsByScenarios = calculateCostForLCC(entity, resultCategories, indicator, calculationRules,
                                                collectedDatasets, parentEntity, preparedDenominator, resourceCache, eolProcessCache)
                                        loggerUtil.info(log, "NEW CALCULATION: Finished calculating cost for LCC: ${System.currentTimeMillis() - now2} ms")
                                        now2 = System.currentTimeMillis()
                                    } else {
                                        resultsByScenarios = calculateResultsForRules(entity, indicator, calculationRules, resultCategories,
                                                collectedDatasets, monthlyDataLicensed, quarterlyDataLicensed, parentEntity,
                                                preparedDenominator, byPassDenominator, indicatorAdditionalQuestionIds, resourceCache, eolProcessCache)
                                        loggerUtil.info(log, "NEW CALCULATION: Finished calculating results for rules: ${System.currentTimeMillis() - now2} ms")
                                        now2 = System.currentTimeMillis()
                                    }

                                    /**
                                     * Finish calculations.
                                     * Remove those results that do not have associated calculationRuleId TODO: Hung ?????
                                     * Set the results to design
                                     */
                                    if (resultsByScenarios) {
                                        resultsByScenarios.removeAll({ !it.calculationRuleId })

                                        if (resultsByScenarios && !resultsByScenarios.isEmpty()) {
                                            setEntityResults(entity, resultsByScenarios, indicator, resultCategories, calculationRules,
                                                    comparisonCategories, temporary, preparedDenominator,
                                                    collectedDatasets, resourceCache, indicatorAdditionalQuestionIds)
                                            loggerUtil.info(log, "NEW CALCULATION: Post handled virtual datasets && saved datasets for Entity: ${System.currentTimeMillis() - now2} ms")
                                        }
                                    }
                                }
                            }
                            loggerUtil.info(log, "NEW CALCULATION TIME FOR ENTITY ${entity.parentName} / ${entity.operatingPeriodAndName}, INDICATOR ${indicatorService.getLocalizedName(indicator)}: ${System.currentTimeMillis() - now} MILLISECONDS.")
                            if (System.currentTimeMillis() - now > 15000 && Environment.current == Environment.PRODUCTION) {
                                loggerUtil.error(log, "Calculation took more than 15 seconds!  ${entity.parentName} / ${entity.operatingPeriodAndName}, INDICATOR ${indicatorService.getLocalizedName(indicator)} It took:${(System.currentTimeMillis() - now) / 1000} seconds ")
                                flashService.setFadeWarningAlert("Calculation took more than 15 seconds!  ${entity.parentName} / ${entity.operatingPeriodAndName}, INDICATOR ${indicatorService.getLocalizedName(indicator)} It took:${(System.currentTimeMillis() - now) / 1000} seconds", true)
                            }
                            resolveQueryReadiness(indicator, entity, queryIds)

                            if (indicatorId == "xBenchmarkEmbodied" && parentEntity.lcaCheckerAllowed && indicator.enableLcaChecker) {
                                List<String> queries = indicator.getIndicatorQueries().findAll({ !it.projectLevel }).collect({ it.queryId })
                                Boolean lcaCheckerLicensed = featuresAllowed.get(Feature.LCA_CHECKER)
                                List<Dataset> lcaCheckerDatasets = entity.datasets?.toList()?.findAll({ queries?.contains(it.queryId) })
                                List<LcaChecker> lcaCheckers = lcaCheckerService.getAllLcaCheckers(lcaCheckerLicensed)

                                try {
                                    entity.lcaCheckerResult = getLcaCheckerResultForEntity(entity, indicator, lcaCheckers,
                                            lcaCheckerDatasets, entity.suppressedLcaCheckers, Boolean.TRUE, resourceCache)
                                } catch (Exception e) {
                                    loggerUtil.error(log, "LCA CHECKER ERROR: Unable to calculate LCA checker result due to exception", e)
                                    flashService.setErrorAlert("LCA CHECKER ERROR: Unable to calculate LCA checker result due to exception ${e.getMessage()}", true)
                                }
                            }

                            if (Boolean.TRUE.equals(entity.resultsOutOfDate)) {
                                entity.resultsOutOfDate = Boolean.FALSE
                            }
                            if (entityId) {
                                try {
                                    entity = entity.save(flush: true)
                                } catch (OptimisticLockingException ole) {
                                    loggerUtil.warn(log, "Handled error logging: Optimistic locking. User or users have attempted concurrent update-type access to data", ole)
                                    flashService.setFadeWarningAlert("Handled error logging: Optimistic locking. User or users have attempted concurrent update-type access to data", true)
                                } catch (IllegalArgumentException iaex) {
                                    loggerUtil.error(log, "Error while saving an entity: ", iaex)
                                }
                            }
                        } else {
                            loggerUtil.error(log, "Could not do calculation for indicator ${indicatorId}, because could not find resultCategories, calculationRules or queries")
                            flashService.setErrorAlert("Could not do calculation for indicator ${indicatorId}, because could not find resultCategories, calculationRules or queries", true)
                        }
                    }
                }
            }

            calculationProcessService.deleteCalculationProcess(calculationProcess)
        } catch (Exception ex) {
            calculationProcessService.setErrorStatus(calculationProcess)
            throw ex
        }

        return entity
    }

    /**
     * Converts materials to be by resources standard unit, if users impacts are in KG but user inputs material quantity
     * in M3 -> the calculatedQuantity needs to be per KG as the materials impacts will be in KG
     *
     * Converts imperial units to european unit
     *
     * @param collectedDatasets datasets for calculation, hold reference to a Resource object
     * @param monthlyDataLicensed if tool uses monthly results, calculate for monthly
     * @param quarterlyDataLicensed if tool uses quartetly results, calculate for quarterly
     * @param alsoNonResources allows calculating quantity for non resource rows
     */
    private void dataConversion(List<Dataset> collectedDatasets, Boolean monthlyDataLicensed,
                                Boolean quarterlyDataLicensed, Boolean alsoNonResources, ResourceCache resourceCache) {
        if (alsoNonResources) {
            collectedDatasets?.each { Dataset dataset ->
                if (dataset.resourceId) {
                    Resource resource = resourceCache.getResource(dataset)

                    if (resource) {
                        Double thickness_mm = datasetService.getThickness(dataset.additionalQuestionAnswers)
                        String userGivenUnit = dataset.userGivenUnit
                        dataset.calculatedUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(resource.unitForData)
                        dataset.calculatedQuantity = unitConversionUtil.doConversion(dataset.quantity, thickness_mm, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)
                        if (dataset.calculatedQuantity != null) {
                            dataset.preResultFormula = "${dataset.preResultFormula ?: ''}<b>Quantity: ${roundingNumber(dataset.calculatedQuantity)} ${resource.unitForData ? resource.unitForData : ''}.</b> Calculated from: ${roundingNumber(dataset.quantity)} ${userGivenUnit ? userGivenUnit : ''} (Thickness: ${thickness_mm} mm - Density: ${resource.density} kg / m3)"
                        }
                        if (monthlyDataLicensed) {
                            if (dataset.monthlyAnswers) {
                                Map<String, Double> calculatedMonthlyQuantity = dataset.getCalculatedMonthlyQuantity()
                                dataset.monthlyAnswers.each { String month, Object answer ->
                                    if (answer && DomainObjectUtil.isNumericValue(answer.toString())) {
                                        Double answerDouble = DomainObjectUtil.convertStringToDouble(answer.toString())

                                        //Thickness
                                        Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                                        if (calculatedMonthlyQuantity) {
                                            calculatedMonthlyQuantity.put(month, unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true))
                                        } else {
                                            calculatedMonthlyQuantity = [(month): unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)]
                                            dataset.setCalculatedMonthlyQuantity(calculatedMonthlyQuantity)
                                        }
                                    }
                                }
                            }
                        }

                        if (quarterlyDataLicensed) {
                            if (dataset.quarterlyAnswers) {
                                Map<String, Double> calculatedQuarterlyQuantity = dataset.getCalculatedQuarterlyQuantity()
                                dataset.quarterlyAnswers.each { String quarter, Object answer ->
                                    if (answer && DomainObjectUtil.isNumericValue(answer.toString())) {
                                        Double answerDouble = DomainObjectUtil.convertStringToDouble(answer.toString())

                                        //Thickness
                                        Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                                        if (calculatedQuarterlyQuantity) {
                                            calculatedQuarterlyQuantity.put(quarter, unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true))
                                        } else {
                                            calculatedQuarterlyQuantity = [(quarter): unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)]
                                            dataset.setCalculatedQuarterlyQuantity(calculatedQuarterlyQuantity)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    dataset.calculatedQuantity = dataset.quantity
                }
            }
        } else {
            collectedDatasets?.findAll({ it.resourceId })?.each { Dataset dataset ->
                Resource resource = resourceCache.getResource(dataset)

                if (resource) {
                    Double thickness_mm = datasetService.getThickness(dataset.additionalQuestionAnswers)
                    String userGivenUnit = dataset.userGivenUnit
                    dataset.calculatedUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(resource.unitForData)
                    if ("m2".equals(dataset.calculatedUnit) && resource.allowVariableThickness && "m2".equals(unitConversionUtil.transformImperialUnitToEuropeanUnit(userGivenUnit)) && thickness_mm == 0) {
                        dataset.calculatedQuantity = 0
                    } else {
                        dataset.calculatedQuantity = unitConversionUtil.doConversion(dataset.quantity, thickness_mm, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)
                    }
                    if (dataset.calculatedQuantity != null) {
                        dataset.preResultFormula = "${dataset.preResultFormula ?: ''}<b>Quantity: ${roundingNumber(dataset.calculatedQuantity)} ${resource.unitForData ? resource.unitForData : ''}.</b> Calculated from: ${roundingNumber(dataset.quantity)} ${userGivenUnit ? userGivenUnit : ''} (Thickness: ${thickness_mm ? thickness_mm : '(no value found)'} mm - Density: ${resource.density ? resource.density : '(no value found)'} kg / m3)"
                    }
                    if (monthlyDataLicensed) {
                        if (dataset.monthlyAnswers) {
                            Map<String, Double> calculatedMonthlyQuantity = dataset.getCalculatedMonthlyQuantity()
                            dataset.monthlyAnswers.each { String month, Object answer ->
                                if (answer && DomainObjectUtil.isNumericValue(answer.toString())) {
                                    Double answerDouble = DomainObjectUtil.convertStringToDouble(answer.toString())

                                    //Thickness
                                    Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                                    if (calculatedMonthlyQuantity) {
                                        calculatedMonthlyQuantity.put(month, unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true))
                                    } else {
                                        calculatedMonthlyQuantity = [(month): unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)]
                                        dataset.setCalculatedMonthlyQuantity(calculatedMonthlyQuantity)
                                    }
                                }
                            }
                        }
                    }

                    if (quarterlyDataLicensed) {
                        if (dataset.quarterlyAnswers) {
                            Map<String, Double> calculatedQuarterlyQuantity = dataset.getCalculatedQuarterlyQuantity()
                            dataset.quarterlyAnswers.each { String quarter, Object answer ->
                                if (answer && DomainObjectUtil.isNumericValue(answer.toString())) {
                                    Double answerDouble = DomainObjectUtil.convertStringToDouble(answer.toString())

                                    //Thickness
                                    Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                                    if (calculatedQuarterlyQuantity) {
                                        calculatedQuarterlyQuantity.put(quarter, unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true))
                                    } else {
                                        calculatedQuarterlyQuantity = [(quarter): unitConversionUtil.doConversion(answerDouble, thickness, userGivenUnit, resource, null, null, null, null, null, null, null, null, null, null, null, true)]
                                        dataset.setCalculatedQuarterlyQuantity(calculatedQuarterlyQuantity)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private DenominatorUtil getDenominatorUtil() {
        return Holders.getApplicationContext().getBean("denominatorUtil")
    }

    @CompileStatic
    private static Map<String, Double> mergeResultMaps(Map<String, Double> a, Map<String, Double> b) {
        Map<String, Double> y = new HashMap<>()
        a.forEach{key, value ->
            if(b.get(key)) {
                y.put(key, value + b.get(key))
            } else {
                y.put(key, value)
            }
        }
        Map yy = b.findAll { e -> !a.keySet().contains(e.key) }
        return y + yy
    }

    /**
     * Handles totalAndSocialCost additionalQuestion answer
     *
     * @param entity the design
     * @param indicator the tool
     * @param results calculated results
     */

    private void handleCarbonCostValuesForDatasets(Entity entity, Indicator indicator, List<CalculationResult> results) {
        if (entity && results && entity.datasets && indicator) {
            entity.datasets.each { Dataset d ->
                if (indicator.indicatorQueries?.find({it.queryId == d.queryId})?.additionalQuestionIds?.contains("totalAndSocialCost")) {
                    Double totalCost = d.additionalQuestionAnswers?.get("totalCost") &&
                            DomainObjectUtil.isNumericValue(d.additionalQuestionAnswers?.get("totalCost")?.toString()) ?
                            DomainObjectUtil.convertStringToDouble(d.additionalQuestionAnswers.get("totalCost").toString()) : null
                    Double result = entity.getResultForDataset(d.manualId, indicator.indicatorId, null,"socialCost_failover", results, false)

                    if (result && totalCost) {
                        d.additionalQuestionAnswers.put("totalAndSocialCost", (totalCost + result))
                    }
                }
            }
        }
    }

    /**
     * Sets the calculated results into the design
     *
     * @param entity the design
     * @param results the calculated results
     * @param indicator the tool being calculated
     * @param resultCategories the tools resultCategories
     * @param calculationRules the tools calculationRules
     * @param comparisonCategories if categories are being compared
     * @param temporary if temporary calculation the results wont be persisted in the database
     * @param preparedDenominator denominator for manipulating the end results
     * @param collectedDatasets collected datasets for the calculation, mostly used for virtual categories data filtering
     * @param indicatorAdditionalQuestionIds collected indicator additional questionId for virtual categories data filtering
     */
    @Secured(["ROLE_AUTHENTICATED"])
    private void setEntityResults(Entity entity, List<CalculationResult> results, Indicator indicator,
                                  List<ResultCategory> resultCategories, List<CalculationRule> calculationRules,
                                  List<ResultCategory> comparisonCategories, Boolean temporary, PreparedDenominator preparedDenominator,
                                  List<Dataset> collectedDatasets, ResourceCache resourceCache, Set<String> indicatorAdditionalQuestionIds = null) {
        Date start = new Date()
        if(entity == null) {
            log.error("Entity is missing.")
            return
        }
        Entity parent = entity.parentById

        List<CalculationResult> entityResults = new ArrayList<>()
        Map<String, Double> totalByRule = new HashMap<String, Double>()
        Map<String, Map<String, Double>> totalMonthly = new HashMap<String, Double>()
        Map<String, Map<String, Double>> totalQuarterly = new HashMap<String, Double>()
        Map<String, Map<String, Object>> dataValidity = new HashMap<String, Double>()

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parent, [Feature.ANNUAL_CHART])
        Boolean annualChartLicensed = featuresAllowed.get("annualChart")

        if (results) {
            log.info("Result categories size: ${resultCategories?.size()}")

            Map<String, Map<String, List<CalculationResult>>> resultsByCategoriesAndRules = new HashMap<>()

            List<ResultCategory> virtualCategories = resultCategories.findAll{ it.virtual }

            for (CalculationResult result : results) {
                if(resultsByCategoriesAndRules.get(result.resultCategoryId) == null) {
                    resultsByCategoriesAndRules.put(result.resultCategoryId, new HashMap<>())
                }
                if(resultsByCategoriesAndRules.get(result.resultCategoryId).get(result.calculationRuleId) == null) {
                    resultsByCategoriesAndRules.get(result.resultCategoryId)
                            .put(result.calculationRuleId, new ArrayList<>())
                }
                resultsByCategoriesAndRules.get(result.resultCategoryId).get(result.calculationRuleId).add(result)
            }

            for (ResultCategory resultCategory : resultCategories) {
                if(resultCategory == null) {
                    log.warn("Result category is missing for ${entity.name} ")
                    continue
                }
                if(resultCategory.resultCategoryId == null) {
                    log.warn("Result category id for (${resultCategory.name}) is missing for ${entity.name} ")
                    continue
                }
                mutateEntityResults(
                        entity, indicator, resultCategory, virtualCategories,
                        calculationRules, resultsByCategoriesAndRules,
                        annualChartLicensed, temporary, preparedDenominator,
                        totalMonthly, totalQuarterly, totalByRule, entityResults, resourceCache
                )

                if (resultCategory.data) {
                    dataValidity.put(resultCategory.resultCategoryId, new LinkedHashMap<String, Object>(resultCategory.data))
                } else {
                    dataValidity.put(resultCategory.resultCategoryId, null)
                }
            }
        }
        
        if (!entityResults.isEmpty()) {
            if (indicator?.handleVirtualLast) {
                handleComparisonCategories(indicator, entity, entityResults, comparisonCategories, totalByRule)
                handleVirtualCategories(entity, entityResults, resultCategories, calculationRules, indicator, totalByRule,
                        preparedDenominator, collectedDatasets, resourceCache, indicatorAdditionalQuestionIds, parent)
                handleVirtualRules(entity, entityResults, resultCategories, calculationRules, indicator, totalByRule, resourceCache)
            } else {
                handleVirtualCategories(entity, entityResults, resultCategories, calculationRules, indicator, totalByRule,
                        preparedDenominator, collectedDatasets, resourceCache, indicatorAdditionalQuestionIds,parent)
                handleVirtualRules(entity, entityResults, resultCategories, calculationRules, indicator, totalByRule, resourceCache)
                handleComparisonCategories(indicator, entity, entityResults, comparisonCategories, totalByRule)
            }

            selectBiggestResultCategory(entity, entityResults, resultCategories, calculationRules, indicator)
            selectSmallestResultCategory(entity, entityResults, resultCategories, calculationRules, indicator)
            selectBiggestCalculationRule(entity, entityResults, resultCategories, calculationRules, indicator)
            selectSmallestCalculationRule(entity, entityResults, resultCategories, calculationRules, indicator)

            if (indicator?.isCostCalculationAllowed) {
                handleCarbonCostValuesForDatasets(entity, indicator, entityResults)
            }

            if (preparedDenominator.denominators) {
                preparedDenominator.denominators.each { String datasetOrDenominatorId, Double denominatorValue ->
                    if (datasetOrDenominatorId) {
                        entityResults.each { CalculationResult calculationResult ->
                            if (!calculationResult.resultByDenominator) {
                                calculationResult.resultByDenominator = new LinkedHashMap<String, Double>()
                            }
                            calculationResult.resultByDenominator.put(datasetOrDenominatorId, (calculationResult.result / denominatorValue))
                        }
                    }
                }
            } else if (preparedDenominator.monthlyDenominators) {
                preparedDenominator.monthlyDenominators.each { String datasetOrDenominatorId, Map<String, Double> valueMonthly ->
                    valueMonthly.each { String month, Double value ->
                        if (value) {
                            entityResults.each { CalculationResult calculationResult ->
                                if (!calculationResult.monthlyResultByDenominator) {
                                    calculationResult.monthlyResultByDenominator = new LinkedHashMap<String, Map<String, Double>>()
                                }

                                if (calculationResult.monthlyResults?.get(month)) {
                                    def existing = calculationResult.monthlyResultByDenominator.get(datasetOrDenominatorId)

                                    if (existing) {
                                        existing.put(month, calculationResult.monthlyResults.get(month) / value)
                                    } else {
                                        existing = [(month): calculationResult.monthlyResults.get(month) / value]
                                    }
                                    calculationResult.monthlyResultByDenominator.put(datasetOrDenominatorId, existing)
                                }
                            }
                        }
                    }
                }
            }

            if (temporary) {
                CalculationTotalResult calculationTotalResult = new CalculationTotalResult()
                calculationTotalResult.indicatorId = indicator.indicatorId
                calculationTotalResult.totalByCalculationRule = totalByRule
                entity.calculationTotalResults = [calculationTotalResult]
                entity.tempCalculationResults = entityResults
            } else {
                if (entity.calculationResults) {
                    entity.calculationResults.addAll(entityResults)
                } else {
                    entity.calculationResults = entityResults
                }
                CalculationTotalResult calculationTotalResult = new CalculationTotalResult()
                calculationTotalResult.indicatorId = indicator.indicatorId
                calculationTotalResult.totalByCalculationRule = totalByRule

                if (preparedDenominator.denominators) {
                    calculationTotalResult.denominatorValues = new LinkedHashMap<String, Double>(preparedDenominator.denominators)
                }

                if (preparedDenominator.monthlyDenominators) {
                    calculationTotalResult.monthlyDenominatorValues = new LinkedHashMap<String, Map<String, Double>>(preparedDenominator.monthlyDenominators)
                }

                if (!totalMonthly.isEmpty()) {
                    calculationTotalResult.monthlyTotalsPerCalculationRule = totalMonthly
                }

                if (!totalQuarterly.isEmpty()) {
                    calculationTotalResult.quarterlyTotalsPerCalculationRule = totalQuarterly
                }

                if (!dataValidity.isEmpty()) {
                    calculationTotalResult.dataValidity = dataValidity
                }

                if (entity.calculationTotalResults) {
                    entity.calculationTotalResults.add(calculationTotalResult)
                } else {
                    entity.calculationTotalResults = [calculationTotalResult]
                }
                calculationMessageService.generateEntitySaveMessage(entity, totalByRule?.get(indicator.displayResult),
                        calculationRules.find({ indicator.displayResult.equals(it.calculationRuleId) }),
                        parent, resourceCache, indicator)
            }
        }
        //log.info("Time for setting entityResults entity ${entity.name}, indicator ${indicator.indicatorId}: ${end.getTime() - start.getTime()} ms")
    }

    @CompileStatic
    private void mutateEntityResults(final Entity entity, final Indicator indicator, final ResultCategory resultCategory,
                                     final List<ResultCategory> virtualCategories,
                                     final List<CalculationRule> calculationRules, final Map<String, Map<String, List<CalculationResult>>> results,
                                     final Boolean annualChartLicensed, final Boolean temporary, final PreparedDenominator preparedDenominator,
                                     Map<String, Map<String, Double>> totalMonthly,  Map<String, Map<String, Double>> totalQuarterly,
                                     Map<String, Double> totalByRule, List<CalculationResult> entityResults, ResourceCache resourceCache) {
        if (resultCategory == null || entity == null || indicator == null || !results) {
            log.warn("Cannot change entity results due missing arguments.")
            return
        }

        for(CalculationRule calculationRule : calculationRules) {
            if (!calculationRule?.calculationRuleId) {
                log.warn("Missing calculation rule id for ${entity.name}")
                continue
            }
            if(log.isTraceEnabled()) {
                log.trace("Processing ${calculationRule.calculationRuleId} rule for ${resultCategory.resultCategoryId}")
            }
            String calculationRuleId = calculationRule.getCalculationRuleId()
            String resultCategoryId = resultCategory.getResultCategoryId()
            List<CalculationResult> resultsByCategoryAndRule = new ArrayList<>()
            if(results.get(resultCategoryId) != null && results.get(resultCategoryId).get(calculationRuleId) != null) {
                resultsByCategoryAndRule = results.get(resultCategoryId).get(calculationRuleId)
            }

            if (resultsByCategoryAndRule) {
                Map<String, Double> resultByOccurencePeriod = null
                Map<String, Map<String, Object>> calculationResultDatasetMap = new HashMap<>()
                Double totalResult = 0D
                List<Map> monthlyResults = new ArrayList<>()
                List<Map> quarterlyResults = new ArrayList<>()

                for(CalculationResult result : resultsByCategoryAndRule) {
                    if(result.result != null) {
                        totalResult = totalResult + result.result
                    }
                    if(result.monthlyResults) {
                        monthlyResults.add(result.monthlyResults)
                    }
                    if(result.quarterlyResults) {
                        quarterlyResults.add(result.quarterlyResults)
                    }
                    result.datasetIds?.each { String datasetId ->
                        Map<String, Object> existing = calculationResultDatasetMap.get(datasetId)

                        if (!existing) {
                            existing = [:]
                            existing.resourceId = result.calculationResourceId
                            existing.profileId = result.calculationProfileId
                            existing.result = result.result
                            if (temporary) {
                                existing.calculationFormula = result.calculationFormula
                            }
                            calculationResultDatasetMap.put(datasetId, existing)
                        }
                    }

                    if (result.resultPerOccurencePeriod != null) {
                        if (resultByOccurencePeriod) {
                            resultByOccurencePeriod = mergeResultMaps(resultByOccurencePeriod, result.resultPerOccurencePeriod)
                        } else {
                            resultByOccurencePeriod = new HashMap<String, Double>(result.resultPerOccurencePeriod)
                        }
                    }
                }

                String unitToDisplay = getUnitToDisplay(indicator, calculationRule)
                CalculationResult calculationResult = new CalculationResult()
                calculationResult.entityId = entity.id?.toString()
                calculationResult.indicatorId = indicator.indicatorId
                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                calculationResult.calculationRuleId = calculationRule.calculationRuleId
                calculationResult.result = totalResult

                if (monthlyResults) {
                    Map<String, Double> sumMonthlyResults = null

                    monthlyResults.each { Map monthlyResult ->
                        if (sumMonthlyResults) {
                            sumMonthlyResults = mergeResultMaps(sumMonthlyResults, monthlyResult)
                        } else {
                            sumMonthlyResults = new HashMap<String, Double>(monthlyResult)
                        }
                    }
                    calculationResult.monthlyResults = sumMonthlyResults

                    if (resultCategory.ignoreFromTotals == null ||
                            (!resultCategory.ignoreFromTotals.contains(calculationRule.calculationRuleId) &&
                                    !resultCategory.ignoreFromTotals.isEmpty())) {
                        Map<String, Double> totalPerRuleMonthly = totalMonthly.get(calculationRule.calculationRuleId)

                        if (totalPerRuleMonthly) {
                            totalPerRuleMonthly = mergeResultMaps(totalPerRuleMonthly, sumMonthlyResults)
                            totalMonthly.put(calculationRule.calculationRuleId, totalPerRuleMonthly)
                        } else {
                            totalMonthly.put(calculationRule.calculationRuleId, sumMonthlyResults)
                        }
                    }
                }

                if (quarterlyResults) {
                    Map<String, Double> sumQuarterlyResults = null

                    quarterlyResults.each { Map quarterlyResult ->
                        if (sumQuarterlyResults) {
                            sumQuarterlyResults = mergeResultMaps(sumQuarterlyResults, quarterlyResult)
                        } else {
                            sumQuarterlyResults = new HashMap<String, Double>(quarterlyResult)
                        }
                    }
                    calculationResult.quarterlyResults = sumQuarterlyResults


                    if (resultCategory.ignoreFromTotals == null ||
                            (!resultCategory.ignoreFromTotals.contains(calculationRule.calculationRuleId) &&
                                    !resultCategory.ignoreFromTotals.isEmpty())) {
                        Map<String, Double> totalPerRuleQuarterly = totalQuarterly.get(calculationRule.calculationRuleId)

                        if (totalPerRuleQuarterly) {
                            totalPerRuleQuarterly = mergeResultMaps(totalPerRuleQuarterly, sumQuarterlyResults)
                            totalQuarterly.put(calculationRule.calculationRuleId, totalPerRuleQuarterly)
                        } else {
                            totalQuarterly.put(calculationRule.calculationRuleId, sumQuarterlyResults)
                        }
                    }
                }

                if (!calculationResultDatasetMap.isEmpty()) {
                    calculationResult.calculationResultDatasets = calculationResultDatasetMap
                }

                if (resultByOccurencePeriod) {
                    calculationResult.resultPerOccurencePeriod = resultByOccurencePeriod
                }

                if (calculationRule.resultManipulators) {
                    for (ResultManipulator resultManipulator in calculationRule.resultManipulators) {
                        useResultManipulatorForResult(resultManipulator, entity, [], calculationResult,
                                indicator, unitToDisplay,
                                entityResults, resourceCache, Boolean.TRUE, Boolean.FALSE)
                    }
                }

                if (calculationRule.resultManipulator) {
                    useResultManipulatorForResult(calculationRule.resultManipulator, entity, [], calculationResult,
                            indicator, unitToDisplay,
                            entityResults, resourceCache, Boolean.TRUE, Boolean.FALSE)
                }

                if (resultCategory.resultManipulators && !calculationRule.preventResultCategoryResultManipulator) {
                    for (ResultManipulator resultManipulator in resultCategory.resultManipulators) {
                        useResultManipulatorForResult(resultManipulator, entity, virtualCategories, calculationResult,
                                indicator, unitToDisplay,
                                entityResults, resourceCache, Boolean.FALSE, Boolean.TRUE,
                                resultCategory.calculateForUniqueAnswersOnly, resultCategory.allowUnassignedZone,
                                resultCategory.setMaximumValue, resultCategory.setMaximumValue)
                    }
                }

                if (resultCategory.ignoreFromTotals == null ||
                        (!resultCategory.ignoreFromTotals.contains(calculationRule.calculationRuleId) &&
                                !resultCategory.ignoreFromTotals.isEmpty())) {
                    Double total = totalByRule.get(calculationRule.calculationRuleId)

                    if (total != null) {
                        total = total + calculationResult.result
                        totalByRule.put(calculationRule.calculationRuleId, total)
                    } else {
                        totalByRule.put(calculationRule.calculationRuleId, calculationResult.result)
                    }
                }

                if (resultCategory.impactTiming && !resultCategory.virtual && annualChartLicensed) {
                    impactTimingProcessing(resultCategory.impactTiming, calculationResult, entity, indicator, preparedDenominator, resourceCache)
                }
                entityResults.add(calculationResult)
            }
        }
    }

    /**
     * Gets the denominator for calculation, just a divider for the results
     *
     * @param entity
     * @param parentEntity
     * @param indicator
     * @return
     */
    private PreparedDenominator prepareDenominators(Entity entity, Entity parentEntity, Indicator indicator, ResourceCache resourceCache) {
        PreparedDenominator preparedDenominator = new PreparedDenominator()

        if (entity && indicator) {
            preparedDenominator.assessmentPeriod = getAssessmentPeriod(entity, indicator, resourceCache)
            preparedDenominator.overallDenominator = getDenominatorUtil().getOverallDenominator(indicator, entity, resourceCache)
            List<Denominator> denominators = indicator.resolveDenominators?.findAll({ Denominator denominator ->
                !"overallDenominator".equals(denominator.denominatorType)
            })

            if (denominators) {
                for (Denominator denominator : denominators) {
                    if (!denominator) {
                        continue
                    }
                    if ("dynamicDenominator".equals(denominator.denominatorType)) {
                        List<Dataset> denominatorDatasets = denominatorService.getDynamicDenominatorDatasets(entity, denominator)

                        if (denominatorDatasets) {
                            if (denominator.requireMonthly) {
                                denominatorDatasets.each { Dataset dataset ->
                                    if (preparedDenominator.monthlyDenominators) {
                                        preparedDenominator.monthlyDenominators.put(dataset.manualId, datasetService.getMonthlyQuantities(dataset.monthlyAnswers))
                                    } else {
                                        preparedDenominator.monthlyDenominators = [(dataset.manualId): datasetService.getMonthlyQuantities(dataset.monthlyAnswers)]
                                    }
                                }
                            } else {
                                denominatorDatasets.each { Dataset dataset ->
                                    def valueForDenominator = dataset.quantity

                                    if (valueForDenominator && denominator.resultManipulator) {
                                        def multiplier = denominatorUtil.getMultiplierByDenominator(entity, denominator, resourceCache)

                                        if (multiplier != null) {
                                            valueForDenominator = valueForDenominator * multiplier
                                        }
                                        def divider = denominatorUtil.getDividerByDenominator(entity, denominator, resourceCache)

                                        if (divider) {
                                            valueForDenominator = valueForDenominator / divider
                                        }
                                    }
                                    if (preparedDenominator.denominators) {
                                        preparedDenominator.denominators.put(dataset.manualId, valueForDenominator)
                                    } else {
                                        preparedDenominator.denominators = [(dataset.manualId): valueForDenominator]
                                    }
                                }
                            }
                        }
                    } else if ("monthlyValueReference".equals(denominator.denominatorType)) {
                        List<Dataset> denominatorDatasets = denominatorService.getMonthlyValuereferenceDatasets(entity, denominator)

                        if (denominatorDatasets) {
                            denominatorDatasets.each { Dataset dataset ->
                                Double valueForDenominator = dataset.quantity
                                Map<String, Double> monthlyQuantities = [:]

                                Constants.MONTHS.each {
                                    monthlyQuantities.put(it, valueForDenominator)
                                }
                                if (preparedDenominator.monthlyDenominators) {
                                    preparedDenominator.monthlyDenominators.put(dataset.manualId, monthlyQuantities)
                                } else {
                                    preparedDenominator.monthlyDenominators = [(dataset.manualId): monthlyQuantities]
                                }
                            }
                        }
                    } else {
                        if (denominator.requireMonthly) {
                            Map<String, Double> monthlyDenominatorValues = getDenominatorUtil().
                                    getFinalValueForMonthlyDenominator(entity, denominator)

                            if (!monthlyDenominatorValues) {
                                monthlyDenominatorValues = getDenominatorUtil().
                                        getFinalValueForMonthlyDenominator(parentEntity, denominator)
                            }

                            if (monthlyDenominatorValues) {
                                if (preparedDenominator.monthlyDenominators) {
                                    preparedDenominator.monthlyDenominators.put(denominator.denominatorId, monthlyDenominatorValues)
                                } else {
                                    preparedDenominator.monthlyDenominators = [(denominator.denominatorId): monthlyDenominatorValues]
                                }
                            }
                        } else {
                            Double denominatorValue = getDenominatorUtil().getFinalValueForDenominator(entity, denominator, resourceCache)

                            if (!denominatorValue) {
                                denominatorValue = getDenominatorUtil().getFinalValueForDenominator(parentEntity, denominator, resourceCache)
                            }

                            if (denominatorValue) {
                                if (preparedDenominator.denominators) {
                                    preparedDenominator.denominators.put(denominator.denominatorId, denominatorValue)
                                } else {
                                    preparedDenominator.denominators = [(denominator.denominatorId): denominatorValue]
                                }
                            }
                        }
                    }
                }
            }
        }
        return preparedDenominator
    }

    /**
     * Calculates the total value for a result category, based on the largest (max) value between the
     * provided categories per each dataset.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     */

    private void selectBiggestResultCategory(entity, List<CalculationResult> calculationResults, List<ResultCategory> resultCategories,
                                             List<CalculationRule> calculationRules, Indicator indicator) {

        List<ResultCategory> categoriesWithBiggestResult = resultCategories.findAll({ it.selectBiggestResultCategory })

        if (categoriesWithBiggestResult) {
            for (ResultCategory resultCategory in categoriesWithBiggestResult) {
                List<CalculationResult> resultsForComparison = calculationResults.findAll({ CalculationResult result ->
                    resultCategory.selectBiggestResultCategory.contains(result.resultCategoryId)
                })

                if (resultsForComparison) {
                    calculationRules.each { CalculationRule calculationRule ->
                        List<CalculationResult> resultsForRule = resultsForComparison.findAll({
                            calculationRule.calculationRuleId.equals(it.calculationRuleId) &&
                                    (!calculationRule.skipRuleForCategories || (calculationRule.skipRuleForCategories
                                            && !calculationRule.skipRuleForCategories.contains(resultCategory.resultCategoryId)))
                        })

                        if (resultsForRule) {
                            compareResultDatasetsByMinOrMax(resultsForRule, entity, indicator?.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, calculationResults, Constants.MAX)
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for categories ${resultCategory.selectBiggestResultCategory}" +
                            " to calculate largest result category ${resultCategory.resultCategoryId}")
                    flashService.setWarningAlert("Could not find calculationResults for categories ${resultCategory.selectBiggestResultCategory}" +
                            " to calculate largest result category ${resultCategory.resultCategoryId}", true)
                }
            }
        }
    }

    /**
     * Calculates the total value for a result category, based on the smallest (min) value between the
     * provided categories per each dataset.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     */
    private void selectSmallestResultCategory(entity, List<CalculationResult> calculationResults, List<ResultCategory> resultCategories,
                                              List<CalculationRule> calculationRules, Indicator indicator) {

        List<ResultCategory> categoriesWithSmallestResult = resultCategories.findAll({ it.selectSmallestResultCategory })

        if (categoriesWithSmallestResult) {
            for (ResultCategory resultCategory in categoriesWithSmallestResult) {
                List<CalculationResult> resultsForComparison = calculationResults.findAll({ CalculationResult result ->
                    resultCategory.selectSmallestResultCategory.contains(result.resultCategoryId)
                })

                if (resultsForComparison) {
                    calculationRules.each { CalculationRule calculationRule ->
                        List<CalculationResult> resultsForRule = resultsForComparison.findAll({
                            calculationRule.calculationRuleId.equals(it.calculationRuleId) &&
                                    (!calculationRule.skipRuleForCategories || (calculationRule.skipRuleForCategories
                                            && !calculationRule.skipRuleForCategories.contains(resultCategory.resultCategoryId)))
                        })

                        if (resultsForRule) {
                            compareResultDatasetsByMinOrMax(resultsForRule, entity, indicator?.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, calculationResults, Constants.MIN)
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for categories ${resultCategory.selectSmallestResultCategory}" +
                            " to calculate smallest result category ${resultCategory.resultCategoryId}")
                    flashService.setWarningAlert("Could not find calculationResults for categories ${resultCategory.selectSmallestResultCategory}" +
                            " to calculate smallest result category ${resultCategory.resultCategoryId}", true)
                }
            }
        }
    }

    /**
     * Compares datasets provided by the list of CalculationResults and compares the values between each, can swap between
     * min and max (smallest/biggest value)
     */
    private void compareResultDatasetsByMinOrMax(List<CalculationResult> filteredResults, Entity entity, String indicatorId, String calculationRuleId, String resultCategoryId, List<CalculationResult> entityCalculationResults, String minOrMax) {

        Map<String, Map<String, Object>> calcResultDatasets = [:]
        Double total

        filteredResults.findAll({ it.calculationResultDatasets })?.
                collect({ it.calculationResultDatasets })?.each { Map resultPerDataset ->

            resultPerDataset.each { String datasetId, Map value ->
                Double result = value.get("result") as Double

                if (result != null) {
                    Map<String, Object> existing = calcResultDatasets.get(datasetId)

                    if (existing) {
                        Double existingResult = existing.get("result")

                        if (existingResult != null || existingResult == 0) {
                            Double comparedResult
                            if (minOrMax == Constants.MIN) {
                                comparedResult = Math.min(existingResult, result)
                            } else if (minOrMax == Constants.MAX) {
                                comparedResult = Math.max(existingResult, result)
                            }
                            if (total) {
                                total += comparedResult
                            } else {
                                total = comparedResult
                            }
                            existing.put("result", comparedResult)
                        }
                    } else {
                        existing = [result   : result, resourceId: value.resourceId,
                                    profileId: value.profileId]
                        calcResultDatasets.put(datasetId, existing)
                    }
                }
            }
        }

        if (!calcResultDatasets.isEmpty() && (total || total == 0)) {
            CalculationResult calculationResult = new CalculationResult()
            calculationResult.entityId = entity.id.toString()
            calculationResult.indicatorId = indicatorId
            calculationResult.calculationRuleId = calculationRuleId
            calculationResult.resultCategoryId = resultCategoryId
            calculationResult.calculationResultDatasets = calcResultDatasets
            calculationResult.result = total
            entityCalculationResults.add(calculationResult)
        }

    }

    /**
     * Calculates the total value for a calculation rule, based on the largest (max) value between the
     * provided rules per each dataset.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     */
    private void selectBiggestCalculationRule(entity, List<CalculationResult> calculationResults, List<ResultCategory> resultCategories,
                                               List<CalculationRule> calculationRules, Indicator indicator) {

        List<CalculationRule> rulesWithBiggestRule = calculationRules.findAll({ it.selectBiggestCalculationRule })

        if (rulesWithBiggestRule) {
            for (CalculationRule calculationRule in rulesWithBiggestRule) {
                List<CalculationResult> resultsForComparison = calculationResults.findAll({ CalculationResult result ->
                    calculationRule.selectBiggestCalculationRule.contains(result.calculationRuleId)
                })

                if (resultsForComparison) {
                    resultCategories.each { ResultCategory resultCategory ->
                        List<CalculationResult> resultsForCategory = resultsForComparison.findAll({
                            resultCategory.resultCategoryId.equals(it.resultCategoryId)
                        })

                        if (resultsForCategory) {
                            compareResultDatasetsByMinOrMax(resultsForCategory, entity, indicator?.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, calculationResults, Constants.MAX)
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for calculationRules ${calculationRule.selectBiggestCalculationRule}" +
                            " to calculate biggest calculationRule ${calculationRule.calculationRuleId}")
                    flashService.setWarningAlert("Could not find calculationResults for calculationRules ${calculationRule.selectBiggestCalculationRule}" +
                            " to calculate biggest calculationRule ${calculationRule.calculationRuleId}", true)
                }
            }
        }
    }

    /**
     * Calculates the total value for a calculation rule, based on the smallest (min) value between the
     * provided rules per each dataset.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     */
    private void selectSmallestCalculationRule(entity, List<CalculationResult> calculationResults, List<ResultCategory> resultCategories,
                                              List<CalculationRule> calculationRules, Indicator indicator) {

        List<CalculationRule> rulesWithSmallestRule = calculationRules.findAll({ it.selectSmallestCalculationRule })

        if (rulesWithSmallestRule) {
            for (CalculationRule calculationRule in rulesWithSmallestRule) {
                List<CalculationResult> resultsForComparison = calculationResults.findAll({ CalculationResult result ->
                    calculationRule.selectSmallestCalculationRule.contains(result.calculationRuleId)
                })

                if (resultsForComparison) {
                    resultCategories.each { ResultCategory resultCategory ->
                        List<CalculationResult> resultsForCategory = resultsForComparison.findAll({
                            resultCategory.resultCategoryId.equals(it.resultCategoryId)
                        })

                        if (resultsForCategory) {
                            compareResultDatasetsByMinOrMax(resultsForCategory, entity, indicator?.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, calculationResults, Constants.MIN)
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for calculationRules ${calculationRule.selectSmallestCalculationRule}" +
                            " to calculate smallest calculationRule ${calculationRule.calculationRuleId}")
                    flashService.setWarningAlert("Could not find calculationResults for calculationRules ${calculationRule.selectSmallestCalculationRule}" +
                            " to calculate smallest calculationRule ${calculationRule.calculationRuleId}", true)
                }
            }
        }
    }


    /**
     * Calculates the result for virtual categories, virtual category is just basically a sum of resultCategories that
     * are linked into it in config.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     * @param totalByRule
     * @param preparedDenominator
     * @param collectedDatasets
     * @param indicatorAdditionalQuestionIds
     * @param parentEntity
     */

    private void handleVirtualCategories(Entity entity, List<CalculationResult> calculationResults,
                                         List<ResultCategory> resultCategories, List<CalculationRule> calculationRules, Indicator indicator,
                                         Map<String, Double> totalByRule, PreparedDenominator preparedDenominator,
                                         List<Dataset> collectedDatasets, ResourceCache resourceCache, Set<String> indicatorAdditionalQuestionIds = null,
                                         Entity parentEntity = null) {
        List<ResultCategory> virtualCategories = resultCategories.findAll{ it.virtual }
        String indicatorId = indicator?.indicatorId
        indicatorAdditionalQuestionIds = indicatorAdditionalQuestionIds ?: questionService.getAdditionalQuestionIdsForIndicator(indicator,parentEntity)

        if (virtualCategories) {
            for (ResultCategory resultCategory in virtualCategories) {
                List<CalculationResult> resultsForVirtual = calculationResults.findAll({ CalculationResult result ->
                    resultCategory.virtual.contains(result.resultCategoryId)
                })

                if (resultsForVirtual) {
                    List<String> filteredDatasetIds
                    if (resultCategory.resourceFilterCriteriaList) {
                        filteredDatasetIds = collectedDatasets?.findAll{
                            optimiResourceService.filterResourceByResourceFilterCriteriaObjects(resourceCache.getResource(it),
                                    resultCategory.resourceFilterCriteriaList, it ,indicatorAdditionalQuestionIds) }?.collect{ it.manualId }
                    }
                    if (resultCategory.resourceFilterParameter && resultCategory.acceptResourceValues) {
                        filteredDatasetIds = collectedDatasets?.findAll{
                            optimiResourceService.fitlterResourceAndDatasetByResourceFilterParameter(resourceCache.getResource(it),
                                    it,[(resultCategory.resourceFilterParameter):resultCategory.acceptResourceValues]) }?.collect{ it.manualId }
                    }
                    calculationRules.each { CalculationRule calculationRule ->
                        List<CalculationResult> resultsForRule = resultsForVirtual.findAll({
                            calculationRule.calculationRuleId.equals(it.calculationRuleId) &&
                                    (!calculationRule.skipRuleForCategories || (calculationRule.skipRuleForCategories
                                            && !calculationRule.skipRuleForCategories.contains(resultCategory.resultCategoryId)))
                        })

                        if (resultsForRule) {
                            String unitToDisplay = getUnitToDisplay(indicator, calculationRule)
                            Double sum = resultsForRule.collect({ it.result ?: 0 })?.sum()

                            if (sum != null) {
                                CalculationResult calculationResult = new CalculationResult()
                                calculationResult.entityId = entity.id.toString()
                                calculationResult.indicatorId = indicatorId
                                calculationResult.calculationRuleId = calculationRule.calculationRuleId
                                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                                calculationResult.result = sum

                                //calculationResult.datasetIds = datasetIds.unique()

                                Map<String, Map<String, Object>> calcResultDatasets = [:]

                                resultsForRule.findAll({ it.calculationResultDatasets })?.
                                        collect({ it.calculationResultDatasets })?.each { Map resultPerDataset ->

                                    resultPerDataset.each { String datasetId, Map value ->
                                        if ((filteredDatasetIds && filteredDatasetIds.contains(datasetId)) || (!filteredDatasetIds && !resultCategory.resourceFilterCriteriaList && !resultCategory.resourceFilterParameter)) {
                                            Double result = value.get("result")

                                            if (result != null) {
                                                Map<String, Object> existing = calcResultDatasets.get(datasetId)

                                                if (existing) {
                                                    Double existingResult = existing.get("result")
                                                    String existingFormula = existing.get("calculationFormula")

                                                    if (existingResult != null) {
                                                        existing.put("result", existingResult + result)
                                                    }

                                                    if (existingFormula && value.calculationFormula) {
                                                        String formula = value.calculationFormula

                                                        if (formula.lastIndexOf("<b>") > -1) {
                                                            formula = formula.substring(formula.lastIndexOf("<b>"), formula.length())
                                                            existing.put("calculationFormula", existingFormula + "," + formula)
                                                        }
                                                    }
                                                } else {
                                                    existing = [result   : result, resourceId: value.resourceId,
                                                                profileId: value.profileId]
                                                    String formula = value.calculationFormula
                                                    if (formula && formula.lastIndexOf("<b>") > -1) {
                                                        formula = formula.substring(formula.lastIndexOf("<b>"), formula.length())
                                                        existing.put("calculationFormula", formula)
                                                    }
                                                    calcResultDatasets.put(datasetId, existing)
                                                }
                                            }
                                        }

                                    }
                                }

                                if (!calcResultDatasets.isEmpty()) {
                                    calculationResult.calculationResultDatasets = calcResultDatasets
                                    calculationResult.result = calcResultDatasets.collect {it?.value?.result}.sum()
                                } else {
                                    calculationResult.result = 0D
                                }

                                Map sumResultByDenominators = resultsForRule.findAll({ it.resultByDenominator })?.
                                        collect({ it.resultByDenominator }).collectMany({ it.entrySet() }).groupBy({
                                    it.key
                                }).
                                        collectEntries({ [it.key, it.value.sum({ it.value })] })

                                if (sumResultByDenominators) {
                                    calculationResult.resultByDenominator = sumResultByDenominators
                                }
                                boolean calculationOk = true
                                if (resultCategory.resultManipulators && !calculationRule.preventResultCategoryResultManipulator) {
                                    for (ResultManipulator resultManipulator in resultCategory.resultManipulators) {
                                        useResultManipulatorForResult(resultManipulator, entity, [],
                                                calculationResult, indicator, unitToDisplay,
                                                calculationResults, resourceCache, Boolean.FALSE, Boolean.TRUE,
                                                resultCategory.calculateForUniqueAnswersOnly, resultCategory.allowUnassignedZone,
                                                resultCategory.setMinimumValue, resultCategory.setMaximumValue)

                                        if (resultManipulator.totalsBasedOnWeightedAverage) {
                                            Set<Dataset> entityDatasets = entity.datasets
                                            Set<Dataset> onlyDatasetWithValidZone
                                            List validZones = datasetService.getValidFECZones(entityDatasets)
                                            ValueReference totalsBasedOnWeightedAverage = resultManipulator.totalsBasedOnWeightedAverage
                                            String areaQuestionId = totalsBasedOnWeightedAverage.questionId
                                            String areaSectionId = totalsBasedOnWeightedAverage.sectionId
                                            String areaQueryId = totalsBasedOnWeightedAverage.queryId
                                            Double totalArea = 0.0
                                            Double totalValue = 0.0
                                            Double weightedAverage = 0.0

                                            validZones?.each {zoneId ->
                                                Set<Dataset> datasetsIdsForThisZone = entityDatasets?.findAll({it.questionId == areaQuestionId && it.sectionId == areaSectionId && it.queryId == areaQueryId && it.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID) == zoneId})
                                                if(onlyDatasetWithValidZone){
                                                    onlyDatasetWithValidZone.addAll(datasetsIdsForThisZone)
                                                } else {
                                                    onlyDatasetWithValidZone = datasetsIdsForThisZone
                                                }
                                                List manualIdsList = datasetsIdsForThisZone?.collect({it.manualId})?.toList()
                                                Double areaForZone = datasetService.getFECAreaByZoneAndQuestionId(datasetsIdsForThisZone,areaQueryId,areaSectionId,areaQuestionId,zoneId)
                                                Double valueForZone = calculationResult.calculationResultDatasets?.findAll({manualIdsList.contains(it.key)})?.collect({ it.value.result ?: 0 })?.sum()
                                                if (valueForZone != null && areaForZone != null) {
                                                    totalValue += (areaForZone * valueForZone)
                                                }
                                            }
                                            if(onlyDatasetWithValidZone){
                                                totalArea = datasetService.getFECAreaByZoneAndQuestionId(onlyDatasetWithValidZone, areaQueryId,areaSectionId,areaQuestionId,null)
                                            }
                                            if( totalValue != null && totalArea != null) {
                                                weightedAverage = totalValue / totalArea
                                            } else {
                                                calculationOk = false
                                                // Not sure about this type of logging but want to somehow keep the log style consistent in here
                                                log.info("Aborting calculation in resultManipulator of calculationResult ${calculationResult.id} - EntityId ${entity.id}. Invalid value for totalArea ${totalArea} OR totalValue ${totalValue}")
                                                flashService.setWarningAlert("Aborting calculation in resultManipulator of calculationResult ${calculationResult.id} - EntityId ${entity.id}. Invalid value for totalArea ${totalArea} OR totalValue ${totalValue}", true)
                                                break
                                            }

                                            if (calculationOk) {
                                                calculationResult.result = weightedAverage
                                            }
                                        }
                                    }
                                }

                                if (resultCategory.impactTiming) {
                                    impactTimingProcessing(resultCategory.impactTiming, calculationResult, entity, indicator, preparedDenominator, resourceCache)
                                }

                                if (calculationResult.result < 0 && (resultCategory.setToZeroIfValueIsNegative||calculationRule.setToZeroIfValueIsNegative)) {
                                    applyMultiplier(calculationResult, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_NEGATIVE)
                                }

                                if (calculationResult.result > 0 && (resultCategory.setToZeroIfValueIsPositive||calculationRule.setToZeroIfValueIsPositive)) {
                                    applyMultiplier(calculationResult, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_POSITIVE)
                                }

                                if(resultCategory.setMinimumValue != null && calculationResult.result < resultCategory.setMinimumValue && !resultCategory.calculateForUniqueAnswersOnly){
                                    applyMultiplier(calculationResult, resultCategory.setMinimumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MINIMUM_VALUE, null, Boolean.TRUE)
                                }
                                if(resultCategory.setMaximumValue != null && calculationResult.result > resultCategory.setMaximumValue && !resultCategory.calculateForUniqueAnswersOnly){
                                    applyMultiplier(calculationResult, resultCategory.setMaximumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MAXIMUM_VALUE, null, Boolean.TRUE)
                                }

                                if (calculationResult.result && (resultCategory.ignoreFromTotals == null ||
                                        (!resultCategory.ignoreFromTotals.contains(calculationRule.calculationRuleId) && !resultCategory.ignoreFromTotals.isEmpty()))) {
                                    Double total = totalByRule.get(calculationRule.calculationRuleId)

                                    if (total != null) {
                                        total = total + calculationResult.result
                                        totalByRule.put(calculationRule.calculationRuleId, total)
                                    } else {
                                        totalByRule.put(calculationRule.calculationRuleId, calculationResult.result)
                                    }
                                }
                                calculationResults.add(calculationResult)
                            }
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for categories ${resultCategory.virtual}" +
                            " to calculate virtual category ${resultCategory.resultCategoryId}")
                    flashService.setWarningAlert("Could not find calculationResults for categories ${resultCategory.virtual}" +
                            " to calculate virtual category ${resultCategory.resultCategoryId}", true)
                }
            }
        }
    }

    /**
     * Calculates the result for virtual rules, virtual rule is just basically a sum of calculationRules that
     * are linked into it in config.
     *
     * @param entity
     * @param calculationResults
     * @param resultCategories
     * @param calculationRules
     * @param indicator
     * @param totalByRule
     */

    private void handleVirtualRules(Entity entity, List<CalculationResult> calculationResults, List<ResultCategory> resultCategories,
                                    List<CalculationRule> calculationRules, Indicator indicator,
                                    Map<String, Double> totalByRule, ResourceCache resourceCache) {
        List<CalculationRule> virtualRules = calculationRules.findAll({ it.virtual })
        String indicatorId = indicator?.indicatorId

        if (virtualRules) {
            for (CalculationRule calculationRule in virtualRules) {
                List<CalculationResult> resultsForVirtual = calculationResults.findAll({ CalculationResult result ->
                    calculationRule.virtual.contains(result.calculationRuleId)
                })

                if (resultsForVirtual) {
                    String unitToDisplay = getUnitToDisplay(indicator, calculationRule)
                    resultCategories.each { ResultCategory resultCategory ->
                        List<CalculationResult> resultsForCategory = resultsForVirtual.findAll({
                            resultCategory.resultCategoryId.equals(it.resultCategoryId)
                        })
                        if (resultsForCategory) {
                            Double sum = resultsForCategory.collect({ it.result ?: 0 })?.sum()

                            if (sum != null) {
                                CalculationResult calculationResult = new CalculationResult()
                                calculationResult.entityId = entity.id.toString()
                                calculationResult.indicatorId = indicatorId
                                calculationResult.calculationRuleId = calculationRule.calculationRuleId
                                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                                calculationResult.result = sum
                                //calculationResult.datasetIds = datasetIds.unique()

                                Map<String, Map<String, Object>> calcResultDatasets = [:]

                                resultsForCategory.findAll({ it.calculationResultDatasets })?.
                                        collect({ it.calculationResultDatasets }).each { Map resultPerDataset ->
                                    resultPerDataset.each { String datasetId, Map value ->
                                        Double result = value.get("result")

                                        if (result != null) {
                                            Map<String, Object> existing = calcResultDatasets.get(datasetId)

                                            if (existing) {
                                                Double existingResult = existing.get("result")
                                                String existingFormula = existing.get("calculationFormula")

                                                if (existingResult != null) {
                                                    existing.put("result", existingResult + result)
                                                }

                                                if (existingFormula && value.calculationFormula) {
                                                    String formula = value.calculationFormula

                                                    if (formula.lastIndexOf("<b>") > -1) {
                                                        formula = formula.substring(formula.lastIndexOf("<b>"), formula.length())
                                                        existing.put("calculationFormula", existingFormula + "," + formula)
                                                    }
                                                }
                                            } else {
                                                existing = [result   : result, resourceId: value.resourceId,
                                                            profileId: value.profileId]
                                                String formula = value.calculationFormula
                                                if (formula && formula.lastIndexOf("<b>") > -1) {
                                                    formula = formula.substring(formula.lastIndexOf("<b>"), formula.length())
                                                    existing.put("calculationFormula", formula)
                                                }
                                                calcResultDatasets.put(datasetId, existing)
                                            }
                                        }
                                    }
                                }

                                if (!calcResultDatasets.isEmpty()) {
                                    calculationResult.calculationResultDatasets = calcResultDatasets
                                }

                                Map sumResultByDenominators = resultsForCategory.findAll({ it.resultByDenominator })?.
                                        collect({ it.resultByDenominator }).collectMany({ it.entrySet() }).groupBy({
                                    it.key
                                }).
                                        collectEntries({ [it.key, it.value.sum({ it.value })] })

                                if (sumResultByDenominators) {
                                    calculationResult.resultByDenominator = sumResultByDenominators
                                }

                                if (resultCategory.resultManipulators && !calculationRule.preventResultCategoryResultManipulator) {
                                    for (ResultManipulator resultManipulator in resultCategory.resultManipulators) {
                                        useResultManipulatorForResult(resultManipulator, entity, [],
                                                calculationResult, indicator, unitToDisplay,
                                                calculationResults, resourceCache, Boolean.FALSE, Boolean.TRUE,
                                                resultCategory.calculateForUniqueAnswersOnly, resultCategory.allowUnassignedZone,
                                                resultCategory.setMinimumValue, resultCategory.setMaximumValue)
                                    }
                                }

                                if (calculationRule.resultManipulators) {
                                    for (ResultManipulator resultManipulator in calculationRule.resultManipulators) {
                                        useResultManipulatorForResult(resultManipulator, entity, [],
                                                calculationResult, indicator,
                                                unitToDisplay, calculationResults, resourceCache, Boolean.TRUE, Boolean.FALSE)
                                    }
                                }

                                if (calculationResult.result < 0 && (resultCategory.setToZeroIfValueIsNegative||calculationRule.setToZeroIfValueIsNegative)) {
                                    applyMultiplier(calculationResult, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_NEGATIVE)
                                }
                                if (calculationResult.result > 0 && (resultCategory.setToZeroIfValueIsPositive||calculationRule.setToZeroIfValueIsPositive)) {
                                    applyMultiplier(calculationResult, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_POSITIVE)
                                }
                                if (resultCategory.setMinimumValue != null && calculationResult.result < resultCategory.setMinimumValue && !resultCategory.calculateForUniqueAnswersOnly) {
                                    applyMultiplier(calculationResult, resultCategory.setMinimumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MINIMUM_VALUE, null, Boolean.TRUE)
                                }
                                if (resultCategory.setMaximumValue != null && calculationResult.result > resultCategory.setMaximumValue && !resultCategory.calculateForUniqueAnswersOnly) {
                                    applyMultiplier(calculationResult, resultCategory.setMaximumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MAXIMUM_VALUE, null, Boolean.TRUE)
                                }

                                if (calculationResult.result && (resultCategory.ignoreFromTotals == null ||
                                        (!resultCategory.ignoreFromTotals.contains(calculationRule.calculationRuleId) &&
                                                !resultCategory.ignoreFromTotals.isEmpty()))) {
                                    Double total = totalByRule.get(calculationRule.calculationRuleId)

                                    if (total != null) {
                                        total = total + calculationResult.result
                                        totalByRule.put(calculationRule.calculationRuleId, total)
                                    } else {
                                        totalByRule.put(calculationRule.calculationRuleId, calculationResult.result)
                                    }
                                }
                                calculationResults.add(calculationResult)
                            }
                        }
                    }
                } else {
                    loggerUtil.warn(log, "Could not find calculationResults for rules ${calculationRule.virtual}" +
                            " to calculate virtual rule ${calculationRule.calculationRuleId}")
                    flashService.setWarningAlert("Could not find calculationResults for rules ${calculationRule.virtual}" +
                            " to calculate virtual rule ${calculationRule.calculationRuleId}", true)
                }
            }
        }
    }

    /**
     * Handles calculating the result for comparison categories if any
     *
     * @param indicator
     * @param entity
     * @param resultsForEntity
     * @param comparisonCategories
     * @param totalsByRule
     */

    private void handleComparisonCategories(Indicator indicator, Entity entity, List<CalculationResult> resultsForEntity, List<ResultCategory> comparisonCategories, Map<String, Double> totalsByRule) {
        if (indicator && entity && comparisonCategories && resultsForEntity) {

            comparisonCategories.each { ResultCategory comparisonCategory ->
                String actualResultCategory = comparisonCategory.actualResultCategory
                String targetResultCategory = comparisonCategory.targetResultCategory
                String comparisonMethod = comparisonCategory.comparison

                if (comparisonMethod && actualResultCategory && targetResultCategory) {
                    comparisonCategory.compareRules?.each { CompareRule compareRule ->
                        String calculationRuleId = compareRule.calculationRuleId
                        Double fixedMultiplier = compareRule.fixedMultiplier

                        if (calculationRuleId) {
                            List<CalculationResult> targetResults = resultsForEntity.findAll({
                                calculationRuleId.equals(it?.calculationRuleId) && targetResultCategory.equals(it?.resultCategoryId)
                            })
                            List<CalculationResult> actualResults = resultsForEntity.findAll({
                                calculationRuleId.equals(it?.calculationRuleId) && actualResultCategory.equals(it?.resultCategoryId)
                            })
                            Double target
                            Double actual

                            if (targetResults && !targetResults.isEmpty()) {
                                target = targetResults.last().result
                            }

                            if (actualResults && !actualResults.isEmpty()) {
                                actual = actualResults.last().result
                            }

                            if (target != null && actual != null) {
                                CalculationResult calculationResult = new CalculationResult()
                                calculationResult.entityId = entity.id.toString()
                                calculationResult.indicatorId = indicator.indicatorId
                                calculationResult.resultCategoryId = comparisonCategory.resultCategoryId
                                calculationResult.calculationRuleId = calculationRuleId

                                if (Constants.ResultCategoryComparisonMethod.EQUAL_OR_SMALLER_THAN.toString().equals(comparisonMethod)) {
                                    List<String> pointValues = compareRule.pointValue?.keySet()?.toList()
                                    Double division = actual / target

                                    if (division && pointValues && !pointValues.isEmpty()) {
                                        Double resultForCompare

                                        for (String pointValue in pointValues) {
                                            Double value = DomainObjectUtil.convertStringToDouble(pointValue)

                                            if (division < value) {
                                                resultForCompare = compareRule.pointValue.get(pointValue)
                                                break
                                            }
                                        }

                                        if (resultForCompare) {
                                            if (fixedMultiplier != null) {
                                                resultForCompare = resultForCompare * fixedMultiplier
                                            }
                                            calculationResult.result = resultForCompare

                                            if (comparisonCategory.ignoreFromTotals == null || (!comparisonCategory.ignoreFromTotals.contains(calculationRuleId) && !comparisonCategory.ignoreFromTotals.isEmpty())) {
                                                if (totalsByRule) {
                                                    Double existingResult = totalsByRule.get(calculationRuleId)

                                                    if (existingResult != null) {
                                                        totalsByRule.put(calculationRuleId, existingResult + resultForCompare)
                                                    } else {
                                                        totalsByRule.put(calculationRuleId, resultForCompare)
                                                    }
                                                } else {
                                                    totalsByRule = [(calculationRuleId): resultForCompare]
                                                }
                                            }
                                        } else {
                                            calculationResult.result = 0D
                                        }
                                        resultsForEntity.add(calculationResult)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes virtual categories and rules from the actual calculation, virtual results can only be calculated
     * after all other categories and rules have their results
     *
     * @param entity
     * @param indicator
     * @param calculationRules
     * @param resultCategories
     * @param datasets
     * @param monthlyLicensed
     * @param quarterlyLicensed
     * @param parentEntity
     * @param preparedDenominator
     * @param byPassDenominator
     * @param indicatorAdditionalQuestionIds
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    private List<CalculationResult> calculateResultsForRules(Entity entity, Indicator indicator, List<CalculationRule> calculationRules,
                                                             List<ResultCategory> resultCategories, List<Dataset> datasets,
                                                             Boolean monthlyLicensed, Boolean quarterlyLicensed,
                                                             Entity parentEntity, PreparedDenominator preparedDenominator,
                                                             Boolean byPassDenominator = Boolean.FALSE,
                                                             Set<String> indicatorAdditionalQuestionIds = null,
                                                             ResourceCache resourceCache = null, EolProcessCache eolProcessCache = null) {
        List<ResultCategory> nonVirtualResultCategories = resultCategories.findAll{ !it.virtual }
        List<CalculationRule> nonVirtualCalculationRules = calculationRules.findAll{ !it.virtual }

        return calculateCalculationRuleImpact(entity, indicator, nonVirtualCalculationRules, nonVirtualResultCategories,
                datasets, monthlyLicensed, quarterlyLicensed, parentEntity, preparedDenominator, byPassDenominator,
                indicatorAdditionalQuestionIds, resourceCache, eolProcessCache)
    }

    /**
     * Handles energy benefit sharing
     *
     * @param entity
     * @param applyEnergyBenefitSharing
     * @param resultCategoryId
     * @param resultsByScenarios
     */

    private void energyBenefitSharing(Entity entity, ApplyEnergyBenefitSharing applyEnergyBenefitSharing,
                                      String resultCategoryId, List<CalculationResult> resultsByScenarios, ResourceCache resourceCache) {
        resultsByScenarios.findAll({
            resultCategoryId.equals(it.resultCategoryId) && it.result
        })?.each { CalculationResult calculationResult ->
            if (applyEnergyBenefitSharing) {
                Double electricityFromCHP = valueReferenceService.getDoubleValueForEntity(applyEnergyBenefitSharing.electricityFromCHP, entity, null, null, resourceCache)
                Double separateElectricityProdEff = valueReferenceService.getDoubleValueForEntity(applyEnergyBenefitSharing.separateElectricityProdEff, entity, null, null, resourceCache)
                Double heatFromCHP = valueReferenceService.getDoubleValueForEntity(applyEnergyBenefitSharing.heatFromCHP, entity, null, null, resourceCache)
                Double separateHeatProdEff = valueReferenceService.getDoubleValueForEntity(applyEnergyBenefitSharing.separateHeatProdEff, entity, null, null, resourceCache)

                if (electricityFromCHP && separateElectricityProdEff && heatFromCHP && separateHeatProdEff) {
                    Double altPowerGenImpact = electricityFromCHP / separateElectricityProdEff
                    Double altHeatGenImpact = heatFromCHP / separateHeatProdEff
                    Double chpPowerGenImpactShare = altPowerGenImpact / (altPowerGenImpact + altHeatGenImpact)
                    Double chpHeatGenImpactShare = altHeatGenImpact / (altPowerGenImpact + altHeatGenImpact)
                    loggerUtil.info(log, "EnergyBenefitSharing factors are: altPowerGenImpact: ${altPowerGenImpact}, altHeatGenImpact: ${altHeatGenImpact}, chpPowerGenImpactShare: ${chpPowerGenImpactShare}, chpHeatGenImpactShare: ${chpHeatGenImpactShare}")
                    flashService.setFadeInfoAlert("EnergyBenefitSharing factors are: altPowerGenImpact: ${altPowerGenImpact}, altHeatGenImpact: ${altHeatGenImpact}, chpPowerGenImpactShare: ${chpPowerGenImpactShare}, chpHeatGenImpactShare: ${chpHeatGenImpactShare}", true)

                    if ("heat".equals(applyEnergyBenefitSharing.applyImpactsFor)) {
                        Double benefitResult = calculationResult.result * chpHeatGenImpactShare
                        calculationResult.calculationFormula = "${calculationResult.calculationFormula},Energy benefit sharing: ${benefitResult} = ${calculationResult.result} * ${chpHeatGenImpactShare} (CHP heat share) (${resultCategoryId})"
                        calculationResult.result = benefitResult
                    } else {
                        Double benefitResult = calculationResult.result * chpPowerGenImpactShare
                        calculationResult.calculationFormula = "${calculationResult.calculationFormula},Energy benefit sharing: ${benefitResult} = ${calculationResult.result} * ${chpPowerGenImpactShare} (CHP heat share) (${resultCategoryId})"
                        calculationResult.result = benefitResult
                    }
                }
            }
        }
    }

    /**
     * Calculates mass for each dataset in calculation, mass is just the user given material amount in KG unit.
     * The calculatedMass is used mostly in the local compensation method to calculate compensation per country.
     *
     * @param datasets
     */
    private void calculateMass(List<Dataset> datasets, ResourceCache resourceCache = null) {
        if (datasets) {
            if(!resourceCache) {
                resourceCache = ResourceCache.init(datasets, true)
            }
            Resource resource
            datasets.each { Dataset dataset ->
                String userGivenUnit = dataset.userGivenUnit
                resource = resourceCache.getResource(dataset)

                if (resource) {
                    if (dataset.calculatedQuantity) {
                        dataset.calculatedMass = unitConversionUtil.doConversion(dataset.calculatedQuantity, datasetService.getThickness(dataset.additionalQuestionAnswers),
                                resource.unitForData, resource, null, null, null, null, null, "kg", null, null, true, null, null, true)

                        if (!dataset.calculatedMass) {
                            // Dataset converts to unitForData but fails conversion to kg
                            if (dataset.quantity && resource.massConversionFactor) {
                                if (["kg","lbs","ton"].contains(dataset.userGivenUnit)) {
                                    dataset.calculatedMass = dataset.calculatedQuantity / resource.massConversionFactor
                                    dataset.preResultFormula = "${dataset.preResultFormula},<b>Mass: ${roundingNumber(dataset.calculatedMass)} kg.</b> Calculated from ${roundingNumber(dataset.calculatedQuantity)} / ${roundingNumber(resource.massConversionFactor)} (mass conversion factor)"
                                } else {
                                    dataset.calculatedMass = dataset.calculatedQuantity * resource.massConversionFactor
                                    dataset.preResultFormula = "${dataset.preResultFormula},<b>Mass: ${roundingNumber(dataset.calculatedMass)} kg.</b> Calculated from ${roundingNumber(dataset.calculatedQuantity)} * ${roundingNumber(resource.massConversionFactor)} (mass conversion factor)"
                                }
                            }
                        } else {
                            dataset.preResultFormula = "${dataset.preResultFormula},<b>Mass: ${roundingNumber(dataset.calculatedMass)} kg.</b> Calculated from ${roundingNumber(dataset.calculatedQuantity)} ${resource.unitForData? resource.unitForData:''}"
                        }
                    } else {
                        // Dataset failed conversion to resource.unitForData in dataConversion
                        if (dataset.quantity && resource.massConversionFactor && (!datasetService.getThickness(dataset.additionalQuestionAnswers) || datasetService.getThickness(dataset.additionalQuestionAnswers) > 0)) {
                            if (["kg","lbs","ton"].contains(userGivenUnit)) {
                                Double europeanQuantity

                                if ("lbs".equals(userGivenUnit)||"ton".equals(userGivenUnit)) {
                                    europeanQuantity = unitConversionUtil.doConversion(dataset.quantity, datasetService.getThickness(dataset.additionalQuestionAnswers), userGivenUnit, resource, null, null, null, null, null, "kg", null, null, null, null, null, true)
                                } else {
                                    europeanQuantity = dataset.quantity
                                }
                                dataset.calculatedQuantity = europeanQuantity / resource.massConversionFactor
                                dataset.calculatedMass = europeanQuantity
                                dataset.preResultFormula = "${dataset.preResultFormula},<b>Quantity: ${roundingNumber(dataset.calculatedQuantity)}</b> Calculated from ${roundingNumber(europeanQuantity)} / ${roundingNumber(resource.massConversionFactor)} (mass conversion factor)"
                                dataset.preResultFormula = "${dataset.preResultFormula},<b>Mass: ${roundingNumber(dataset.calculatedMass)} kg</b> Calculated from ${roundingNumber(dataset.quantity)} ${userGivenUnit? userGivenUnit:''}"
                            } else if (resource.unitForData == "kg")  {
                                Double europeanQuantity

                                //Thickness
                                Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                                if ("sq ft".equals(userGivenUnit)) {
                                    europeanQuantity = unitConversionUtil.doConversion(dataset.quantity, thickness, userGivenUnit, resource, null, null, null, null, null, "m2", null, null, null, null, null, true)
                                } else if ("ft".equals(userGivenUnit)) {
                                    europeanQuantity = unitConversionUtil.doConversion(dataset.quantity, thickness, userGivenUnit, resource, null, null, null, null, null, "m", null, null, null, null, null, true)
                                } else if (["cu ft","cu yd"].contains(dataset.userGivenUnit)) {
                                    europeanQuantity = unitConversionUtil.doConversion(dataset.quantity, thickness, userGivenUnit, resource, null, null, null, null, null, "m3", null, null, null, null, null, true)
                                } else {
                                    europeanQuantity = dataset.quantity
                                }
                                dataset.calculatedQuantity = europeanQuantity * resource.massConversionFactor
                                dataset.calculatedMass = dataset.calculatedQuantity
                                dataset.preResultFormula = "${dataset.preResultFormula},<b>Quantity: ${roundingNumber(dataset.calculatedQuantity)}.</b> Calculated from ${roundingNumber(europeanQuantity)} * ${roundingNumber(resource.massConversionFactor)} (mass conversion factor)"
                                dataset.preResultFormula = "${dataset.preResultFormula},<b>Mass: ${roundingNumber(dataset.calculatedMass)} kg</b> Calculated from ${roundingNumber(europeanQuantity)} * ${roundingNumber(resource.massConversionFactor)} (mass conversion factor)"
                            }
                        }
                    }
                    //annie REL2108-61 - if the dataset is having calculatedmass & the addQ massperUnit_kg is also present , then set mass to massPerUnit_kg * quantity
                    if (dataset?.quantity && dataset?.additionalQuestionAnswers?.get(Constants.MASS_PER_UNIT_KG_QUESTIONID)) {
                        Double massPerUnitValue = DomainObjectUtil.convertStringToDouble(dataset?.additionalQuestionAnswers?.get(Constants.MASS_PER_UNIT_KG_QUESTIONID))
                        dataset?.calculatedMass = dataset?.quantity * massPerUnitValue
                    }
                }
            }
        }
    }

    /**
     * Apply the scaling multiplier to constituents for NMD tools
     * The scaling multiplier will be applied directly to the quantity and should be applied once (unless user changes the quantity or dimensions)
     * @param datasets
     * @param indicator
     */
    private void applyNmdScalingMultiplier(List<Dataset> datasets, Indicator indicator, ResourceCache resourceCache) {
        if (!indicator?.applyNmdScaling || !datasets) {
            return
        }

        for (Dataset dataset in datasets) {
            // skip if no quantity
            if (dataset?.quantity == null) {
                continue
            }

            Resource resource = resourceCache.getResource(dataset)
            if (resource?.nmdSchalingsFormuleID == null) {
                continue
            }

            if (resource?.construction) {
                // NMD 2.3 scaling applied at construction level. Logic for this is still in frontend
            } else {
                // NMD 3.0 scaling applied at constituent level
                String userGivenQuantity = dataset.answerIds?.getAt(0)

                if (!userGivenQuantity) {
                    continue
                }

                Double userGivenDimension1_NMD = null
                Double userGivenDimension2_NMD = null

                try {
                    Double dimention1Value = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers?.get(Constants.DIMENSION1_NMD)?.toString())
                    userGivenDimension1_NMD = dimention1Value
                } catch (ignored) {
                }

                try {
                    Double dimention2Value = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers?.get(Constants.DIMENSION2_NMD)?.toString())
                    userGivenDimension2_NMD = dimention2Value
                } catch (ignored) {
                }

                boolean okToApplyScaling = true
                if (dataset.appliedNmdScaling) {
                    AppliedNmdScaling appliedNmdScaling = dataset.appliedNmdScaling
                    if (userGivenDimension1_NMD == appliedNmdScaling.dimension1_mm && userGivenDimension2_NMD == appliedNmdScaling.dimension2_mm && userGivenQuantity == appliedNmdScaling.quantity) {
                        // the scaling had been applied and no parameters have changed >>> no need to apply scaling again
                        okToApplyScaling = false
                    }
                }

                if (!okToApplyScaling) {
                    continue
                }

                Double scalingMultiplier = calculateNmd30ScalingMultiplier(resource.nmdSchalingsFormuleID, userGivenDimension1_NMD,
                        userGivenDimension2_NMD, resource.defaultThickness_mm, resource.defaultThickness_mm2, resource.nmdSchalingsFormuleA1,
                        resource.nmdSchalingsFormuleA2, resource.nmdSchalingsFormuleB1, resource.nmdSchalingsFormuleB2, resource.nmdSchalingsFormuleC, resource)

                if (!scalingMultiplier) {
                    continue
                }

                // apply the scaling
                dataset.quantity *= scalingMultiplier

                // save the parameters when the scaling is applied.
                AppliedNmdScaling newAppliedNmdScaling = new AppliedNmdScaling()
                newAppliedNmdScaling.quantity = userGivenQuantity
                newAppliedNmdScaling.dimension1_mm = userGivenDimension1_NMD
                newAppliedNmdScaling.dimension2_mm = userGivenDimension2_NMD
                newAppliedNmdScaling.multiplier = scalingMultiplier
                newAppliedNmdScaling.calculatedQuantity = dataset.quantity
                dataset.appliedNmdScaling = newAppliedNmdScaling
            }
        }
    }

    /**
     * Scaling multiplier calculator for NMD 3.0
     * Y = calculated multiplier
     * X = thickness_mm, user editable
     * A = SchalingsFormuleA
     * B = SchalingsFormuleB
     * C = SchalingsFormuleC
     *
     * @param schalingsFormuleID
     * @param X1 dimension1, user input
     * @param X2 dimension2, user input
     * @param X1Default default dimension1, from resource
     * @param X2Default default dimension2, from resource
     * @param A1 nmdSchalingsFormuleA1
     * @param A2 nmdSchalingsFormuleA2
     * @param B1 nmdSchalingsFormuleB1
     * @param B2 nmdSchalingsFormuleB2
     * @param C nmdSchalingsFormuleC
     * @param resource
     * @return Y calculated multiplier
     */
    private Double calculateNmd30ScalingMultiplier(Integer schalingsFormuleID, Double X1, Double X2, Double X1Default, Double X2Default, Double A1, Double A2, Double B1, Double B2, Double C, Resource resource) {
        Double Y = 0D;
        String warning = ''

        if (schalingsFormuleID) {
            switch (schalingsFormuleID) {
                case 1:
                    // Linear
                    //Lineair: f(x) = A*X+ C
                    if (X2Default) {
                        if (A1 != null && X1 != null && X2 != null && X1Default != null && C != null) {
                            // Y = A1 * X1 + A2 * X2 + C;- old formula
                            // for the 2-dimensional case(when x2Default is available) X = (X1.X2) / (X1Default.X2Default)
                            Y = A1 * ((X1 * X2) / (X1Default * X2Default)) + C
                        }
                    } else {
                        if (A1 != null && X1 != null && X1Default != null && C != null) {
                            // Y = A1 * X1 + C;- old formula
                            //for the 1-dimensional case X = X1 / X1Default
                            Y = A1 * (X1 / X1Default) + C
                        }
                    }
                    break;
                case 2:
                    // Power of a point
                    //Macht: f(x) = A*X^B+ C
                    if (X2Default) {
                        if (A1 != null && X1 != null && X2 != null && X1Default != null && B1 != null && C != null) {
                            //Y = A1 * X1 ^ B1 + A2 * X2 ^ B2 + C;- old formula
                            Y = A1 * Math.pow(((X1 * X2) / (X1Default * X2Default)), B1) + C
                        }
                    } else {
                        if (A1 != null && X1 != null && X1Default != null && B1 != null && C != null) {
                            //Y = A1 * X1 ^ B1 + C;- old formula
                            Y = A1 * Math.pow((X1 / X1Default), B1) + C;
                        }
                    }
                    break;
                case 3:
                    // Logarithmic
                    //f(x) = A*ln(X)+ C
                    if (X2Default) {
                        if (A1 != null && X1 != null && X2 != null && X1Default != null && C != null) {
                            //Y = A1 * Math.log(X1) + A2 * Math.log(X2) + C;- old formula
                            double temp = (X1 * X2) / (X1Default * X2Default)
                            Y = A1 * Math.log(temp) + C;
                        }
                    } else {
                        if (A1 != null && X1 != null && X1Default != null && C != null) {
                            //Y = A1 * Math.log(X1) + C;- old formula
                            double temp = X1 / X1Default
                            Y = A1 * Math.log(temp) + C;
                        }
                    }
                    break;
                case 4:
                    // Exponential
                    //Exponentieel: f(x) = A*e^(B*X)+ C
                    if (X2Default) {
                        if (A1 != null && X1 != null && X2 != null && X1Default != null && B1 != null && C != null) {
                            // Y = A1 * Math.exp(B1 * X1) + A2 * Math.exp(B2 * X2) + C;- old formula
                            Y = A1 * Math.exp(B1 * (X1 * X2) / (X1Default * X2Default)) + C;
                        }
                    } else {
                        if (A1 != null && X1 != null && X1Default != null && B1 != null && C != null) {
                            //Y = A1 * Math.exp(B1 * X1) + C;- old formula
                            Y = A1 * Math.exp(B1 * X1 / X1Default) + C;
                        }
                    }
                    break;
                default:
                    // schalingsFormuleID out of scope
                    Y = 0D;
                    break;
            }
        }

        if (Y.isNaN() || Y.isInfinite()) {
            Y = 0D;
        }

        return Y
    }

    /**
     * ApplyProcess result, some custom handling for some tool that requires some special results. Not sure if currently
     * completely unused.
     *
     * @param applyProcess
     * @param originalDataset
     * @param resultCategory
     * @param indicatorId
     * @return
     */
    private List<CalculationResult> applyProcessingResults(ApplyProcess applyProcess, Dataset originalDataset,
                                                           ResultCategory resultCategory, String indicatorId, Resource resource) {
        List<CalculationResult> resultsByScenarios = []
        String definitionSource = applyProcess.definitionSources
        String resourceIdSource = applyProcess.resourceIdSource
        String transportResourceSource = applyProcess.transportResourceSource
        String transportDistanceSource = applyProcess.transportDistanceSource
        List<String> quantityMultiplierSources = applyProcess.quantityMultiplierSource
        String processingType = applyProcess.processingType

        if (definitionSource) {
            if ("component".equals(definitionSource)) {
                if (originalDataset && originalDataset.parentConstructionId) {
                    Construction parent = constructionService.getConstruction(originalDataset.parentConstructionId)
                    String manualId = originalDataset.additionalQuestionAnswers?.get("datasetIdFromConstruction")
                    Dataset component

                    if (manualId) {
                        component = parent?.datasets?.find({ it.manualId?.equals(manualId) })
                    }

                    if (!component) {
                        component = parent?.datasets?.find({
                            it.resourceId?.equals(originalDataset.resourceId) && it.profileId?.equals(originalDataset.profileId)
                        })
                    }

                    if (component) {
                        Resource replacementResource

                        if (resourceIdSource && component.additionalQuestionAnswers?.get(resourceIdSource)) {
                            replacementResource = Resource.findByResourceIdAndActive(component.additionalQuestionAnswers.get(resourceIdSource).toString(), true)
                        }

                        Resource transportResource
                        if (transportResourceSource && component.additionalQuestionAnswers?.get(transportResourceSource)) {
                            transportResource = Resource.findByResourceIdAndActive(component.additionalQuestionAnswers.get(transportResourceSource).toString(), true)
                        }

                        Double transportDistance
                        if (transportDistanceSource && DomainObjectUtil.isNumericValue(component.additionalQuestionAnswers?.get(transportDistanceSource)?.toString())) {
                            Double transportDistanceValue = DomainObjectUtil.convertStringToDouble(component.additionalQuestionAnswers.get(transportDistanceSource).toString())
                            transportDistance = transportDistanceValue
                        }

                        List<Double> multipliers = []

                        if (quantityMultiplierSources) {
                            quantityMultiplierSources.each { String quantityMultiplierSource ->
                                String stringMultiplier = originalDataset.additionalQuestionAnswers?.get(quantityMultiplierSource) ?: component.additionalQuestionAnswers?.get(quantityMultiplierSource)

                                if (stringMultiplier && DomainObjectUtil.isNumericValue(stringMultiplier)) {
                                    multipliers.add(DomainObjectUtil.convertStringToDouble(stringMultiplier))
                                }
                            }
                        }
                        Boolean allowMultiplication = Boolean.FALSE
                        Boolean m2km = Boolean.FALSE

                        if (Constants.QueryCalculationProcess.TRANSPORT.toString().equals(processingType)) {
                            String resourceAttr

                            if (transportResource) {
                                resourceAttr = DomainObjectUtil.callGetterByAttributeName("unit", transportResource)
                            } else if (replacementResource) {
                                resourceAttr = DomainObjectUtil.callGetterByAttributeName("unit", replacementResource)
                            }

                            if (resourceAttr && "tkm".equalsIgnoreCase(resourceAttr)) {
                                allowMultiplication = Boolean.TRUE
                            } else if (resourceAttr && "m2km".equalsIgnoreCase(resourceAttr)) {
                                m2km = Boolean.TRUE
                            }
                        } else {
                            allowMultiplication = Boolean.TRUE
                        }

                        if (resource) {
                            CalculationResult calculationResult = new CalculationResult()
                            calculationResult.indicatorId = indicatorId

                            if (Constants.QueryCalculationProcess.TRANSPORT.toString().equals(processingType)) {
                                calculationResult.calculationResourceId = transportResource ? transportResource.resourceId : resource.resourceId
                                calculationResult.calculationProfileId = transportResource ? transportResource.profileId : resource.profileId
                            } else {
                                calculationResult.calculationResourceId = replacementResource ? replacementResource.resourceId : resource.resourceId
                                calculationResult.calculationProfileId = replacementResource ? replacementResource.profileId : resource.profileId
                            }
                            calculationResult.originalResourceId = resource.resourceId
                            calculationResult.originalProfileId = resource.profileId
                            calculationResult.resultCategoryId = resultCategory.resultCategoryId
                            calculationResult.datasetIds = [originalDataset.manualId]
                            calculationResult.unit = originalDataset.userGivenUnit
                            calculationResult.calculatedUnit = originalDataset.calculatedUnit
                            calculationResult.datasetAdditionalQuestionAnswers = originalDataset.additionalQuestionAnswers
                            applyLocalCompensation(originalDataset, calculationResult)

                            Double result
                            Double mass = originalDataset.calculatedMass
                            Double quantity = originalDataset.calculatedQuantity

                            if (Constants.QueryCalculationProcess.TRANSPORT.toString().equals(processingType)) {
                                if (transportDistance != null && mass != null) {
                                    if (allowMultiplication) {
                                        result = (mass * transportDistance) / 1000
                                        calculationResult.calculationFormula = "${originalDataset.preResultFormula},<b>Process ${processingType}:</b> ${result.round(2)} = ${mass.round(2)} * ${transportDistance} / 1000 (transport distance) (${resultCategory.resultCategoryId})"
                                    } else if (!m2km) {
                                        if (transportDistance != 0) {
                                            transportDistance = 1
                                        }
                                        result = (mass * transportDistance)
                                        calculationResult.calculationFormula = "${originalDataset.preResultFormula},<b>Process ${processingType}:</b> ${result.round(2)} = ${mass.round(2)} * ${transportDistance} (transport distance) (${resultCategory.resultCategoryId})"
                                    } else {
                                        result = (mass * transportDistance)
                                        calculationResult.calculationFormula = "${originalDataset.preResultFormula},<b>Process ${processingType}:</b> ${result.round(2)} = ${mass.round(2)} * ${transportDistance} (transport distance) (${resultCategory.resultCategoryId})"
                                    }
                                }

                                if (result != null && multipliers) {
                                    multipliers.each { Double multiplier ->
                                        result = result * multiplier
                                    }
                                    calculationResult.calculationFormula = "${calculationResult.calculationFormula},<b>Process ${processingType}:</b> ${result.round(2)} = after multiplied with: ${quantityMultiplierSources} (${resultCategory.resultCategoryId})"
                                }
                            } else {
                                result = quantity
                                calculationResult.calculationFormula = "${originalDataset.preResultFormula},<b>Process${processingType ? " " + processingType : ""}:</b> ${result.round(2)}"

                                if (allowMultiplication && result != null && multipliers) {
                                    multipliers.each { Double multiplier ->
                                        result = result * multiplier
                                    }
                                    calculationResult.calculationFormula = "${calculationResult.calculationFormula},<b>Process${processingType ? " " + processingType : ""}:</b> ${result.round(2)} = after multiplied with: ${quantityMultiplierSources} (${resultCategory.resultCategoryId})"
                                }
                            }
                            calculationResult.result = result != null ? result : 0D
                            resultsByScenarios.add(calculationResult)
                        }
                    }
                }
            }
        } else {
            loggerUtil.error(log, "ApplyProcess error: ApplyProcess is missing definitionSources in indicator: ${indicatorId}, resultCategory: ${resultCategory.resultCategoryId}")
            flashService.setErrorAlert("ApplyProcess error: ApplyProcess is missing definitionSources in indicator: ${indicatorId}, resultCategory: ${resultCategory.resultCategoryId}", true)
        }
        return resultsByScenarios
    }

    /**
     * Replaces the calculation resource with a completely different one to allow it to use impact info in later steps
     *
     * @param originalDataset
     * @param collectedDatasets
     * @param resultCategory
     * @param indicatorId
     * @param processParameters
     * @return
     */
    private CalculationResult replaceResourceProcessingResult(Dataset originalDataset, List<Dataset> collectedDatasets,
                                                              ResultCategory resultCategory,
                                                              String indicatorId, processParameters, ResourceCache resourceCache) {
        CalculationResult calculationResult
        ValueReference replaceResource = processParameters as ValueReference
        Dataset dataset = collectedDatasets.find({
            replaceResource?.queryId?.equals(it.queryId) &&
                    replaceResource?.sectionId?.equals(it.sectionId) &&
                    replaceResource?.questionId?.equals(it.questionId)
        })

        Resource resource = resourceCache.getResource(dataset)

        if (resource && resource.resourceId) {
            calculationResult = new CalculationResult()
            calculationResult.indicatorId = indicatorId
            calculationResult.calculationResourceId = resource.resourceId
            calculationResult.calculationProfileId = resource.profileId
            calculationResult.originalResourceId = resource.resourceId
            calculationResult.originalProfileId = resource.profileId
            calculationResult.resultCategoryId = resultCategory.resultCategoryId
            calculationResult.datasetIds = [originalDataset.defaultDatasetOriginalManualId ?: originalDataset.manualId]
            calculationResult.unit = originalDataset.userGivenUnit
            calculationResult.calculatedUnit = originalDataset.calculatedUnit
            calculationResult.datasetAdditionalQuestionAnswers = originalDataset.additionalQuestionAnswers
            calculationResult.result = originalDataset.calculatedQuantity
            applyLocalCompensation(originalDataset, calculationResult)
            calculationResult.calculationFormula = "<b>Replace the resource:</b> ${calculationResult.result.round(2)} (${resultCategory.resultCategoryId})"

            loggerUtil.info(log, "ReplaceResource processing replaced dataset resource: ${originalDataset?.resourceId} / ${originalDataset?.profileId} with valueReference: ${resource.resourceId} / ${resource.profileId}")
            flashService.setFadeInfoAlert("ReplaceResource processing replaced dataset resource: ${originalDataset?.resourceId} / ${originalDataset?.profileId} with valueReference: ${resource.resourceId} / ${resource.profileId}", true)
        } else {
            loggerUtil.warn(log, "ReplaceResource processing could not find dataset from collectedDatasets for valuereference: queryId: ${replaceResource?.queryId}, sectionId: ${replaceResource?.sectionId}, questionId: ${replaceResource?.questionId} ${!dataset ? '' : !dataset.calculatedQuantity ? ', or dataset has no quantity!' : ''}")
            flashService.setErrorAlert("ReplaceResource processing could not find dataset from collectedDatasets for valuereference: queryId: ${replaceResource?.queryId}, sectionId: ${replaceResource?.sectionId}, questionId: ${replaceResource?.questionId} ${!dataset ? '' : !dataset.calculatedQuantity ? ', or dataset has no quantity!' : ''}", true)
        }
        return calculationResult
    }

    /**
     * Calculates benefit result category (D) result
     *
     * @param dataset
     * @param resultCategory
     * @param indicatorId
     * @param entity
     * @param projectCountry
     * @param resource
     * @param incinerationEnergyMixResource
     * @param lcaModel
     * @return
     */
    private CalculationResult benefitProcessingResult(Dataset dataset, ResultCategory resultCategory, String indicatorId,
                                                      Entity entity, Resource projectCountry, Resource resource,
                                                      Resource incinerationEnergyMixResource, String lcaModel,
                                                      Indicator indicator, Double fixedDiscountFactor = null,
                                                      Map<String, Double> discountFactors = null, PreparedDenominator preparedDenominator = null,
                                                      ResourceType resourceSubType, EolProcessCache eolProcessCache) {
        CalculationResult calculationResult
        if (resource) {
            boolean useStage = false

            if (resultCategory.applyStages) {
                List<String> stages = new ArrayList<String>(resultCategory.applyStages)
                stages.remove("A1-A3")

                if (stages && resource && resource.impacts && CollectionUtils.containsAny(resource.impacts.keySet().toList(), stages)) {
                    useStage = true
                }
            }

            if (useStage) {
                calculationResult = getDatasetStageResult(dataset, indicatorId, resultCategory.resultCategoryId, resource)
            } else {
                EolProcess eolProcess = eolProcessService.getEolProcessForDataset(dataset, resource, resourceSubType, projectCountry, Boolean.TRUE, lcaModel, eolProcessCache)

                Double netFlows = 1.0D

                // TODO netFlowsDisabled?
                if (resource.shareOfRecycledMaterial) {
                    netFlows = (100 - resource.shareOfRecycledMaterial) / 100
                }

                String environmentalBenefitsResourceId
                String environmentalBenefitsProfileId = null

                if (eolProcess && resourceSubType) {
                    if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcess.epdConversionType) {
                        calculationResult = getEPDEOLCalculationResult(resource, indicatorId, resultCategory, dataset)
                        applyLocalCompensation(dataset, calculationResult)
                        //annie - 15336
                        if(resultCategory.applyDiscountScenario){
                            calculateDiscount(resource, resourceSubType, dataset, entity, indicator, preparedDenominator, resultCategory, calculationResult, fixedDiscountFactor, discountFactors)
                        }
                    } else {
                        if (eolProcess.dConversionType == "energyConversionFactor") {
                            environmentalBenefitsResourceId = incinerationEnergyMixResource?.resourceId
                            environmentalBenefitsProfileId = incinerationEnergyMixResource?.profileId
                        } else {
                            environmentalBenefitsResourceId = eolProcess.dResourceId
                        }

                        if (environmentalBenefitsResourceId && eolProcess.dConversionType && eolProcess.dResourceMultiplier && dataset.calculatedMass != null) {
                            calculationResult = new CalculationResult()
                            calculationResult.indicatorId = indicatorId
                            calculationResult.calculationResourceId = environmentalBenefitsResourceId
                            calculationResult.calculationProfileId = environmentalBenefitsProfileId
                            calculationResult.originalResourceId = resource.resourceId
                            calculationResult.originalProfileId = resource.profileId
                            calculationResult.resultCategoryId = resultCategory.resultCategoryId
                            calculationResult.datasetIds = [dataset.manualId]
                            calculationResult.unit = dataset.userGivenUnit
                            calculationResult.calculatedUnit = dataset.calculatedUnit
                            calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                            calculationResult.eolProcessingType = resource.eolProcessingType ?: resourceSubType.eolProcessingType

                            Double resultFactor

                            if (eolProcess.dConversionType == Constants.EOLProcess.REUSE_AS_MATERIAL.toString()) {
                                if (dataset.calculatedQuantity) {
                                    resultFactor = dataset.calculatedQuantity * (eolProcess.dResourceMultiplier ?: 1)
                                } else {
                                    resultFactor = 0 * (eolProcess.dResourceMultiplier ?: 1)
                                }
                            } else if (eolProcess.dConversionType == "energy") {
                                resultFactor = dataset.calculatedMass * (eolProcess.dResourceMultiplier ?: 1)
                            } else if (eolProcess.dConversionType == "mass") {
                                resultFactor = dataset.calculatedMass * (eolProcess.dResourceMultiplier ?: 1) * netFlows
                            } else {
                                Double energyConversionFactor = resourceSubType.energyConversionFactorkWhPerKg

                                if (energyConversionFactor) {
                                    resultFactor = dataset.calculatedMass * energyConversionFactor * (eolProcess.dResourceMultiplier ?: 0.9D) * netFlows
                                } else {
                                    resultFactor = 0
                                }
                            }

                            if (resultFactor != null) {
                                calculationResult.result = resultFactor
                                calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Beyond the system boundary:</b> ${calculationResult.result.round(2)} for EOL Process conversion type: ${eolProcess.dConversionType} (${resultCategory.resultCategoryId})"
                            }

                            applyLocalCompensation(dataset, calculationResult)
                            //annie - 15336
                            if (resultCategory.applyDiscountScenario) {
                                calculateDiscount(resource, resourceSubType, dataset, entity, indicator, preparedDenominator, resultCategory, calculationResult, fixedDiscountFactor, discountFactors)
                            }
                        } else if (!dataset.isConstruction() && dataset.calculatedMass) {
                            if (environmentalBenefitsResourceId) {
                                if (!eolProcess.dResourceMultiplier||!eolProcess.dConversionType) {
                                    loggerUtil.error(log, "BENEFIT PROCESSING: EOL Process ${eolProcess.eolProcessId} is missing dResourceMultiplier: ${eolProcess.dResourceMultiplier} or dConversionType: ${eolProcess.dConversionType}")
                                    flashService.setErrorAlert("BENEFIT PROCESSING: EOL Process ${eolProcess.eolProcessId} is missing dResourceMultiplier: ${eolProcess.dResourceMultiplier} or dConversionType: ${eolProcess.dConversionType}", true)
                                }
                            }
                        }
                    }
                }
            }
        }
        return calculationResult
    }
    /**
     * method for calculating discount
     *
     * @param dataset
     * @param resultCategory
     * @param indicatorId
     * @param indicator
     * @param entity
     * @param projectCountry
     * @param resource
     * @param preparedDenominator
     */

    private void calculateDiscount(Resource resource, ResourceType resourceSubType, Dataset dataset, Entity entity,
                                   Indicator indicator, PreparedDenominator preparedDenominator,
                                   ResultCategory resultCategory, CalculationResult calculationResult,
                                   Double fixedDiscountFactor, Map<String, Double> discountFactors) {
        try {
            if ((Constants.IMPACTTIMING_FINAL.equals(resultCategory.impactTiming) ||
                    Constants.IMPACTTIMING_EXTERNAL.equals(resultCategory.impactTiming) ||
                    Constants.IMPACTTIMING_ANNUAL.equals(resultCategory.impactTiming)) && fixedDiscountFactor) {
                calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor

            } else if (discountFactors) {
                //annie - 15336
                Double serviceLife = getServiceLife(dataset, resource, resourceSubType, entity, indicator)
                loggerUtil.info(log, "serviceLife is ${serviceLife}")
                if (serviceLife != null && serviceLife > 0D) {
                    serviceLife = Math.ceil(serviceLife.doubleValue())
                    int recurrences = 1
                    Map<String, Double> resultWithDiscountPerOccurencePeriod = new TreeMap<String, Double>()
                    List<Integer> recurrenceMultipliers = []
                    Map<Integer, Integer> serviceLifePerOccurence = [:]
                    Map<Integer, Double> discountedRecurrences = [:]
                    for (Double i = serviceLife; i < preparedDenominator.assessmentPeriod; i = i + serviceLife) {
                        recurrenceMultipliers.add(new Integer(recurrences.intValue()))
                        serviceLifePerOccurence.put(new Integer(recurrences.intValue()), i.toInteger())
                        recurrences++
                    }

                    if (discountFactors) {
                        discountedRecurrences = calculateDiscountedRecurrences(preparedDenominator.assessmentPeriod, discountFactors, serviceLifePerOccurence)
                        if (discountedRecurrences) {
                            if (!calculationResult.resultWithoutOccurrencePeriods) {
                                calculationResult.resultWithoutOccurrencePeriods = (calculationResult.result && serviceLifePerOccurence.size()) ? calculationResult.result / serviceLifePerOccurence.size() : 0
                                //calculationResult.resultWithoutOccurrencePeriods = calculationResult.result
                            }

                            //calculationResult.result = calculationResult.result ? calculationResult.result * serviceLifePerOccurence.size() : 0
                            //annie - 15336
                            recurrenceMultipliers.each { Integer recurrency ->
                                if (discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                    resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(),
                                            calculationResult.resultWithoutOccurrencePeriods ? calculationResult.resultWithoutOccurrencePeriods * discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) : 0)
                                }
                            }
                            //annie
                            calculationResult.resultWithDiscountPerOccurencePeriod = resultWithDiscountPerOccurencePeriod

                            //annie
                            if (resultWithDiscountPerOccurencePeriod) {
                                calculationResult.calculatedDiscountFactor = discountedRecurrences?.values().sum()
                                calculationResult.discountedResult = resultWithDiscountPerOccurencePeriod?.values().sum()
                            }
                        }
                    }
                }
            }
        }catch(e){
            loggerUtil.warn(log, "Exception while calculating discount : ${e.getMessage()}")
            flashService.setErrorAlert("Exception while calculating discount : ${e.getMessage()}", true)
        }
    }


    /**
     * Old method for calculating benefit, needed for compatibility reasons for old tools
     *
     * @param dataset
     * @param resultCategory
     * @param indicatorId
     * @param entity
     * @param projectCountry
     * @param resource
     * @return
     */
    private CalculationResult benefitProcessingResultLegacy(Dataset dataset, ResultCategory resultCategory, String indicatorId,
                                                            Entity entity, Resource projectCountry, Resource resource,
                                                            Indicator indicator, Double fixedDiscountFactor = null,
                                                            Map<String, Double> discountFactors = null, PreparedDenominator preparedDenominator = null,
                                                            ResourceType resourceSubType, ResourceCache resourceCache) {
        CalculationResult calculationResult
        if (resource) {
            boolean useStage = false

            if (resultCategory.applyStages) {
                List<String> stages = new ArrayList<String>(resultCategory.applyStages)
                stages.remove("A1-A3")

                if (stages && resource && resource.impacts && CollectionUtils.containsAny(resource.impacts.keySet().toList(), stages)) {
                    useStage = true
                }
            }

            if (useStage) {
                calculationResult = getDatasetStageResult(dataset, indicatorId, resultCategory.resultCategoryId, resource)
            } else {
                String eolProcessingType

                if (dataset.additionalQuestionAnswers?.get("eolProcessingType")) {
                    eolProcessingType = dataset.additionalQuestionAnswers.get("eolProcessingType")
                } else {
                    eolProcessingType = resource.eolProcessingType ?: resourceSubType?.eolProcessingType
                }

                if ("recycling".equals(eolProcessingType) || "incineration".equals(eolProcessingType)) {
                    String environmentalBenefitsResourceId
                    Double environmentalBenefitsMultiplier

                    if (resource.environmentalBenefitsResourceId != null && resource.environmentalBenefitsMultiplier != null) {
                        environmentalBenefitsResourceId = resource.environmentalBenefitsResourceId
                        environmentalBenefitsMultiplier = resource.environmentalBenefitsMultiplier
                    } else {
                        environmentalBenefitsResourceId = resourceSubType?.environmentalBenefitsResourceId
                        environmentalBenefitsMultiplier = resourceSubType?.environmentalBenefitsMultiplier
                    }

                    if (environmentalBenefitsResourceId && (dataset.calculatedQuantity != null ||
                            dataset.calculatedMass != null) && environmentalBenefitsMultiplier != null &&
                            checkIfResourceIdValid(resource, environmentalBenefitsResourceId, resourceCache)) {
                        calculationResult = new CalculationResult()
                        calculationResult.indicatorId = indicatorId
                        calculationResult.calculationResourceId = environmentalBenefitsResourceId
                        calculationResult.calculationProfileId = null
                        calculationResult.originalResourceId = resource.resourceId
                        calculationResult.originalProfileId = resource.profileId
                        calculationResult.resultCategoryId = resultCategory.resultCategoryId
                        calculationResult.datasetIds = [dataset.manualId]
                        calculationResult.unit = dataset.userGivenUnit
                        calculationResult.calculatedUnit = dataset.calculatedUnit
                        calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                        Double resultFactor

                        if ("incineration".equals(eolProcessingType)) {
                            resultFactor = dataset.calculatedQuantity
                        } else {
                            resultFactor = dataset.calculatedMass != null ? dataset.calculatedMass : dataset.calculatedQuantity
                        }

                        if ("incineration".equalsIgnoreCase(eolProcessingType) && resource.energyConversionFactor != null) {
                            calculationResult.result = resultFactor * resource.energyConversionFactor * environmentalBenefitsMultiplier
                        } else {
                            calculationResult.result = resultFactor * environmentalBenefitsMultiplier
                        }
                        applyLocalCompensation(dataset, calculationResult)
                        //annie - 15336
                        if (resultCategory.applyDiscountScenario) {
                            calculateDiscount(resource, resourceSubType, dataset, entity, indicator, preparedDenominator, resultCategory, calculationResult, fixedDiscountFactor, discountFactors)
                        }
                    } else if (!environmentalBenefitsResourceId) {
                        if ("recycling".equals(eolProcessingType)) {
                            loggerUtil.error(log, "BenefitProcessing: Resource ${resource.resourceId} or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} is missing environmentalBenefitsResourceId")
                            flashService.setErrorAlert("BenefitProcessing: Resource ${resource.resourceId} or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} is missing environmentalBenefitsResourceId", true)
                        }
                    }
                }
            }
        }
        return calculationResult
    }

    /**
     * If result uses stage, doesnt need processing at this point
     *
     * @param dataset
     * @param indicatorId
     * @param resultCategoryId
     * @param resource
     * @return
     */

    private CalculationResult getDatasetStageResult(Dataset dataset, String indicatorId, String resultCategoryId, Resource resource) {
        CalculationResult calculationResult = new CalculationResult()
        calculationResult.indicatorId = indicatorId
        calculationResult.resultCategoryId = resultCategoryId
        calculationResult.calculationResourceId = resource?.resourceId
        calculationResult.calculationProfileId = resource?.profileId
        calculationResult.originalResourceId = dataset?.resourceId
        calculationResult.originalProfileId = dataset?.profileId
        calculationResult.result = dataset.calculatedQuantity
        calculationResult.datasetIds = [dataset.manualId]
        calculationResult.unit = dataset.userGivenUnit
        calculationResult.calculatedUnit = dataset.calculatedUnit
        calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
        calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Use data from resource life-cycle stages:</b> ${calculationResult.result} (${resultCategoryId})"
        return calculationResult
    }

    /**
     * Calculates waste result category (C3) result
     *
     * @param dataset
     * @param resultCategory
     * @param acceptedEOLTypes
     * @param indicatorId
     * @param countryResource
     * @param resource
     * @param lcaModel
     * @return
     */

    private CalculationResult wasteProcessingResult(Dataset dataset, ResultCategory resultCategory, List<String> acceptedEOLTypes,
                                                    String indicatorId, Resource countryResource, Resource resource,
                                                    String lcaModel, Double fixedDiscountFactor = null, Indicator indicator,
                                                    ResourceType resourceSubType, EolProcessCache eolProcessCache) {
        // This method transforms items into their corresponding waste types
        CalculationResult calculationResult
        //Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        if (resource) {
            boolean useStage = false

            if (resultCategory.applyStages) {
                List<String> stages = new ArrayList<String>(resultCategory.applyStages)
                stages.remove("A1-A3")

                if (stages && resource.impacts && CollectionUtils.containsAny(resource.impacts.keySet().toList(), stages)) {
                    useStage = true
                    loggerUtil.debug(log, "WasteProcessing / ResultCategory ${resultCategory.resultCategory}: USING STAGE for resource ${resource?.resourceId} / ${resource?.profileId}, quantity: ${dataset.quantity}, points: ${dataset.points}")
                    loggerUtil.debug(log, "Resource properties needed in wasteProcessing: massConversionFactor: ${resource?.massConversionFactor}, quantity: ${dataset.quantity}")
                }
            }

            if (useStage) {
                calculationResult = getDatasetStageResult(dataset, indicatorId, resultCategory.resultCategoryId, resource)
            } else if (resource.resourceSubType) {
                boolean resourceInAcceptedEOLTypes = true

                if (acceptedEOLTypes) {
                    String eolTypeForResource = resource.eolProcessingType ?: resourceSubType?.eolProcessingType

                    if (eolTypeForResource && !acceptedEOLTypes.contains(eolTypeForResource)) {
                        resourceInAcceptedEOLTypes = false
                    }
                }

                if (resourceInAcceptedEOLTypes) {
                    String wasteResourceId

                    EolProcess eolProcess = eolProcessService.getEolProcessForDataset(dataset, resource, resourceSubType, countryResource, null, lcaModel, eolProcessCache)

                    if (eolProcess && resourceSubType) {
                        if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcess.epdConversionType) {
                            calculationResult = getEPDEOLCalculationResult(resource, indicatorId, resultCategory, dataset)
                            applyLocalCompensation(dataset, calculationResult)
                            //annie - 15336
                            if (fixedDiscountFactor) {
                                calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                                calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                            }
                        } else {
                            wasteResourceId = eolProcess.c3ResourceId

                            if (wasteResourceId && eolProcess.c3ConversionType && eolProcess.c3ResourceMultiplier) {
                                calculationResult = new CalculationResult()
                                calculationResult.indicatorId = indicatorId
                                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                                calculationResult.calculationResourceId = wasteResourceId
                                calculationResult.originalResourceId = resource.resourceId
                                calculationResult.originalProfileId = resource.profileId
                                calculationResult.datasetIds = [dataset.manualId]
                                calculationResult.unit = dataset.userGivenUnit
                                calculationResult.calculatedUnit = dataset.calculatedUnit
                                calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers

                                if (dataset.additionalQuestionAnswers?.get("eolProcessingType")) {
                                    calculationResult.calculationProfileId = dataset.additionalQuestionAnswers.get("eolProcessingType")
                                } else {
                                    calculationResult.calculationProfileId = resource.eolProcessingType ?: resourceSubType?.eolProcessingType
                                }

                                if (dataset.calculatedMass != null) {
                                    if (eolProcess.c3ConversionType == "mass") {
                                        calculationResult.result = dataset.calculatedMass * (eolProcess.c3ResourceMultiplier ?: 1)
                                    } else {
                                        Double energyConversionFactor = resourceSubType.energyConversionFactorkWhPerKg

                                        if (energyConversionFactor) {
                                            calculationResult.result = dataset.calculatedMass * energyConversionFactor * (eolProcess.c3ResourceMultiplier ?: 0.9D)
                                            // PP add to the above equation the finalDiscountFactor if one was passed along as variable (if it's not null or zero)
                                        } else {
                                            calculationResult.result = 0
                                        }
                                    }
                                    calculationResult.calculationFormula = "${dataset.preResultFormula},<b>End of life quantity: ${roundingNumber(calculationResult.result)}.</b> Calculated from ${roundingNumber(calculationResult.result)} (${resultCategory.resultCategoryId})"
                                    applyLocalCompensation(dataset, calculationResult)
                                    //annie-15336
                                    if (fixedDiscountFactor) {
                                        calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                                        calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                                    }
                                }
                            } else if (!dataset.isConstruction()) {
                                if (wasteResourceId) {
                                    if (!eolProcess.c3ConversionType||!eolProcess.c3ResourceMultiplier) {
                                        loggerUtil.error(log, "WASTE PROCESSING: EOL Process ${eolProcess.eolProcessId} is missing c3ConversionType: ${eolProcess.c3ConversionType} or c3ResourceMultiplier: ${eolProcess.c3ResourceMultiplier}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return calculationResult
    }

    /**
     * Old method for calculating waste, needed for compatibility reasons for old tools
     *
     * @param dataset
     * @param resultCategory
     * @param acceptedEOLTypes
     * @param indicatorId
     * @param resource
     * @return
     */

    private CalculationResult wasteProcessingResultLegacy(Dataset dataset, ResultCategory resultCategory, List<String> acceptedEOLTypes,
                                                          String indicatorId, Resource resource, Double fixedDiscountFactor = null,
                                                          Indicator indicator, ResourceType resourceSubType, ResourceCache resourceCache) {
        // This method transforms items into their corresponding waste types
        CalculationResult calculationResult
        //Indicator indicator = indicatorService.getIndicatorById(indicatorId)
        if (resource) {
            boolean useStage = false

            if (resultCategory.applyStages) {
                List<String> stages = new ArrayList<String>(resultCategory.applyStages)
                stages.remove("A1-A3")

                if (stages && resource.impacts && CollectionUtils.containsAny(resource.impacts.keySet().toList(), stages)) {
                    useStage = true
                    loggerUtil.debug(log, "WasteProcessing / ResultCategory ${resultCategory.resultCategory}: USING STAGE for resource ${resource?.resourceId} / ${resource?.profileId}, quantity: ${dataset.quantity}, points: ${dataset.points}")
                    loggerUtil.debug(log, "Resource properties needed in wasteProcessing: massConversionFactor: ${resource?.massConversionFactor}, quantity: ${dataset.quantity}")
                }
            }

            if (useStage) {
                calculationResult = getDatasetStageResult(dataset, indicatorId, resultCategory.resultCategoryId, resource)
            } else if (resource.wasteResourceId || resource.resourceSubType) {
                boolean resourceInAcceptedEOLTypes = true

                if (acceptedEOLTypes) {
                    // eolProcessingType
                    String eolTypeForResource = getAnyParameterFromResource("eolProcessingType", resource)

                    if (eolTypeForResource && !acceptedEOLTypes.contains(eolTypeForResource)) {
                        resourceInAcceptedEOLTypes = false
                    }
                }

                if (resourceInAcceptedEOLTypes) {
                    // Check here, if resource has wasteResourceId, use that. If not take right attribute from resourceType based on entity countryRegulatedWasteHandling
                    String wasteResourceId = resource.wasteResourceId

                    if (!wasteResourceId) {
                        wasteResourceId = resourceSubType?.wasteResourceId
                    }

                    if (!wasteResourceId && !dataset.isConstruction()) {
                        loggerUtil.error(log, "WASTE PROCESSING: Could not get wasteResourceId from resource " +
                                "${resource.resourceId} or resourceSubType ${resource.resourceSubType}")
                        flashService.setErrorAlert("WASTE PROCESSING: Could not get wasteResourceId from resource " +
                                "${resource.resourceId} or resourceSubType ${resource.resourceSubType}", true)
                        // log the error here
                    }
                    if (wasteResourceId && checkIfResourceIdValid(resource, wasteResourceId, resourceCache)) {
                        //loggerUtil.debug(log, "WasteProcessing / ResultCategory ${resultCategory.resultCategory}: NOT USING STAGE for resource ${resource?.resourceId} / ${resource?.profileId}, quantity: ${dataset.quantity}")
                        calculationResult = new CalculationResult()
                        calculationResult.indicatorId = indicatorId
                        calculationResult.resultCategoryId = resultCategory.resultCategoryId
                        calculationResult.calculationResourceId = wasteResourceId
                        calculationResult.originalResourceId = resource.resourceId
                        calculationResult.originalProfileId = resource.profileId
                        calculationResult.datasetIds = [dataset.manualId]
                        calculationResult.unit = dataset.userGivenUnit
                        calculationResult.calculatedUnit = dataset.calculatedUnit
                        calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers

                        if (dataset.additionalQuestionAnswers?.get("eolProcessingType")) {
                            calculationResult.calculationProfileId = dataset.additionalQuestionAnswers.get("eolProcessingType")
                        } else {
                            calculationResult.calculationProfileId = resource.eolProcessingType ?: resourceSubType?.eolProcessingType
                        }

                        if (dataset.calculatedMass != null) {
                            if (dataset.calculatedMass != 0D) {
                                calculationResult.result = dataset.calculatedMass
                            } else {
                                calculationResult.result = 0
                            }
                            calculationResult.calculationFormula = "${dataset.preResultFormula},<b>End of life quantity: ${roundingNumber(calculationResult.result)}.</b> Calculated from ${roundingNumber(dataset.calculatedMass)} (mass of the resource) (${resultCategory.resultCategoryId})"
                            applyLocalCompensation(dataset, calculationResult)
                            //annie - 15336
                            if (fixedDiscountFactor) {
                                calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                                calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                            }
                        }
                    }
                }
            }
        }
        return calculationResult
    }

    /**
     * If impacts are used from the material/resources impacts, result is just calculated quantity in resource
     * unitForData, this will be multiplied later with the material impacts
     *
     * @param resource
     * @param indicatorId
     * @param resultCategory
     * @param dataset
     * @return
     */

    private CalculationResult getEPDEOLCalculationResult(Resource resource, String indicatorId, ResultCategory resultCategory, Dataset dataset) {
        CalculationResult calculationResult = new CalculationResult()
        calculationResult.indicatorId = indicatorId
        calculationResult.resultCategoryId = resultCategory.resultCategoryId
        calculationResult.calculationResourceId = resource.resourceId
        calculationResult.calculationProfileId = resource.profileId
        calculationResult.originalResourceId = resource.resourceId
        calculationResult.originalProfileId = resource.profileId
        calculationResult.datasetIds = [dataset.manualId]
        calculationResult.unit = dataset.userGivenUnit
        calculationResult.calculatedUnit = dataset.calculatedUnit
        calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
        calculationResult.useEolProcessStage = Boolean.TRUE

        if (resultCategory.eolProcessStagesSum) {
            calculationResult.result = dataset.calculatedQuantity
            calculationResult.calculationFormula = ""
        } else {
            List<String> resourceAvailableImpacts = resource.impacts?.keySet()?.toList()

            if (resultCategory.eolProcessStages && resourceAvailableImpacts && CollectionUtils.containsAny(resultCategory.eolProcessStages, resourceAvailableImpacts)) {
                calculationResult.result = dataset.calculatedQuantity
                calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Use EOL data from resource EPD:</b> ${calculationResult.result} (${resultCategory.resultCategoryId})"
            } else {
                calculationResult.result = 0D
                calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Unable to use EOL data from resource EPD:</b> ${calculationResult.result} (${resultCategory.resultCategoryId})"
            }
        }
        return calculationResult
    }

    /**
     * Result for the disposal (C4) resultCategory
     *
     * @param dataset
     * @param resultCategory
     * @param acceptedEOLTypes
     * @param indicatorId
     * @param countryResource
     * @param resource
     * @param lcaModel
     */

    private CalculationResult disposalProcessingResult(Dataset dataset, ResultCategory resultCategory,
                                                       List<String> acceptedEOLTypes, String indicatorId,
                                                       Resource countryResource, Resource resource,
                                                       String lcaModel, Double fixedDiscountFactor = null, Indicator indicator,
                                                       ResourceType resourceSubType, EolProcessCache eolProcessCache) {
        // This method transforms items into their corresponding disposal types
        CalculationResult calculationResult
        //Indicator indicator = indicatorService.getIndicatorById(indicatorId)
        if (resource) {
            boolean useStage = false

            if (resultCategory.applyStages) {
                List<String> stages = new ArrayList<String>(resultCategory.applyStages)
                stages.remove("A1-A3")

                if (stages && resource.impacts && CollectionUtils.containsAny(resource.impacts.keySet().toList(), stages)) {
                    useStage = true
                }
            }

            if (useStage) {
                calculationResult = getDatasetStageResult(dataset, indicatorId, resultCategory.resultCategoryId, resource)
            } else if (resource.resourceSubType) {
                boolean resourceInAcceptedEOLTypes = true

                if (acceptedEOLTypes) {
                    String eolTypeForResource = resource.eolProcessingType ?: resourceSubType?.eolProcessingType

                    if (eolTypeForResource && !acceptedEOLTypes.contains(eolTypeForResource)) {
                        resourceInAcceptedEOLTypes = false
                    }
                }

                if (resourceInAcceptedEOLTypes) {
                    String disposalResourceId

                    EolProcess eolProcess = eolProcessService.getEolProcessForDataset(dataset, resource, resourceSubType, countryResource, null, lcaModel, eolProcessCache)

                    if (eolProcess && resourceSubType) {
                        if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcess.epdConversionType) {
                            calculationResult = getEPDEOLCalculationResult(resource, indicatorId, resultCategory, dataset)
                            applyLocalCompensation(dataset, calculationResult)
                            //annie - 15336
                            if (fixedDiscountFactor) {
                                calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                                calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                            }
                        } else {
                            disposalResourceId = eolProcess.c4ResourceId

                            if (disposalResourceId && eolProcess.c4ResourceMultiplier && eolProcess.c4ConversionType) {
                                if (dataset.calculatedMass != null) {
                                    calculationResult = new CalculationResult()
                                    calculationResult.indicatorId = indicatorId
                                    calculationResult.resultCategoryId = resultCategory.resultCategoryId
                                    calculationResult.calculationResourceId = disposalResourceId
                                    calculationResult.originalResourceId = resource.resourceId
                                    calculationResult.originalProfileId = resource.profileId
                                    calculationResult.datasetIds = [dataset.manualId]
                                    calculationResult.unit = dataset.userGivenUnit
                                    calculationResult.calculatedUnit = dataset.calculatedUnit
                                    calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers

                                    if (dataset.additionalQuestionAnswers?.get("eolProcessingType")) {
                                        calculationResult.calculationProfileId = dataset.additionalQuestionAnswers.get("eolProcessingType")
                                    } else {
                                        calculationResult.calculationProfileId = resource.eolProcessingType ?: resourceSubType?.eolProcessingType
                                    }

                                    if (eolProcess.c4ConversionType == "mass") {
                                        calculationResult.result = dataset.calculatedMass * (eolProcess.c4ResourceMultiplier ?: 1)
                                    } else {
                                        Double energyConversionFactor = resourceSubType.energyConversionFactorkWhPerKg

                                        if (energyConversionFactor) {
                                            calculationResult.result = energyConversionFactor * (eolProcess.c4ResourceMultiplier ?: 0.9D)
                                        } else {
                                            calculationResult.result = 0
                                        }
                                    }
                                    calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Disposal:</b> ${calculationResult.result.round(2)} (${resultCategory.resultCategoryId})"
                                    applyLocalCompensation(dataset, calculationResult)
                                    //annie - 15336
                                    if (fixedDiscountFactor) {
                                        calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                                        calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                                    }
                                }
                            } else if (!dataset.isConstruction()) {
                                if (disposalResourceId) {
                                    if (!eolProcess.c4ResourceMultiplier||!eolProcess.c4ConversionType) {
                                        loggerUtil.error(log, "DISPOSAL PROCESSING: EOL Process ${eolProcess.eolProcessId} is missing c4ConversionType: ${eolProcess.c4ConversionType} or c4ResourceMultiplier: ${eolProcess.c4ResourceMultiplier}")
                                        flashService.setErrorAlert("DISPOSAL PROCESSING: EOL Process ${eolProcess.eolProcessId} is missing c4ConversionType: ${eolProcess.c4ConversionType} or c4ResourceMultiplier: ${eolProcess.c4ResourceMultiplier}", true)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return calculationResult
    }
    /** Method to get the discount factors
     *@param resultCategory
     * @param timing
     * @return
     */
    private Map<String, Double> resolveDiscountFactors(resultCategory , entity , discountScenarioSourceApplication, ResourceCache resourceCache) {
        //annie
        Map<String , Double> discountFactors = [:]
        if(resultCategory?.applyDiscountScenario){
            if ("fixed" == resultCategory?.applyDiscountScenario?.get("scenariotype")) {
                //load  the scenario matching fixed value but leave empty if none found
                if(discountScenarioSourceApplication && resultCategory?.applyDiscountScenario?.get("scenarioId")) {
                    discountFactors = applicationService.getApplicationDiscountFactor(discountScenarioSourceApplication , resultCategory?.applyDiscountScenario?.get("scenarioId"))
                }
            } else {
                ValueReference userSelectedScenario = resultCategory?.applyDiscountScenario?.get("userSelectedScenario")
                String userSelectedSenarioId = getUserSelectedDiscountScenarioId(entity , userSelectedScenario, resourceCache)
                if(userSelectedSenarioId && !"noDiscount".equals(userSelectedSenarioId)) {
                    discountFactors =  applicationService.getApplicationDiscountFactor(discountScenarioSourceApplication , userSelectedSenarioId)
                }
                //discountFactors = load the scenario matching user selected value but leave it empty if none found
            }
        }
        return discountFactors
    }
    /**
     * Result is first calculated for a resultCategory then after that result is expanded per calculationRule,
     * So e.g. The initial result for ResultCategory A1-A3 is used/manipulated for calculationrules GWP, ODP, ... etc.
     * Based on how the rule should be calculated and how its configured.
     * @see #calculateCalculationRuleImpact
     *
     * @param entity
     * @param dataset
     * @param resultCategory
     * @param indicator
     * @param preparedDenominator
     * @param projectCountry
     * @param allDatasets
     * @param datasetResource
     * @param incinerationEnergyMixResource
     * @param lcaModel
     * @return
     */
    @CompileStatic
    private List<CalculationResult> resolveScenarioResults(Entity entity, Dataset dataset, ResultCategory resultCategory,
                                                           Indicator indicator, PreparedDenominator preparedDenominator, Resource projectCountry,
                                                           List<Dataset> allDatasets, Resource datasetResource,
                                                           Resource incinerationEnergyMixResource, String lcaModel, Application discountScenarioSourceApplication = null,
                                                           Boolean hasFractionalReplacement = null,
                                                           ResourceCache resourceCache, EolProcessCache eolProcessCache) {
        long now = System.currentTimeMillis()
        List<CalculationResult> scenarioResults = new ArrayList<>()
        ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()
        ResourceType resourceSubType = resourceTypeCache?.getResourceSubType(datasetResource)
        Map<String, Object> process = resultCategory != null ? resultCategory.getProcess() : null
        boolean isCombine = false
        //annie
        Map<String, Double> discountFactors = new HashMap<>()
        Double fixedDiscountFactor = null
        //annie
        if (indicator.futureDiscountingEnabled && resultCategory?.impactTiming && resultCategory?.applyDiscountScenario) {

            discountFactors = resolveDiscountFactors(resultCategory, entity, discountScenarioSourceApplication, resourceCache)
            //annie - get the list of discount fators , if the timing is annual/final , final value corresponding to
            //final/avergaed from the discountFactors map to get the fixed discount factor
            // if the timing is anything else (mainly recurring) , then pass the entire discountFactors map to the required method

              if (("final".equals(resultCategory?.impactTiming) || "annual".equals(resultCategory?.impactTiming) ||
                      "external".equals(resultCategory?.impactTiming)) && discountFactors){
                  String factorToSelect
                  if("annual".equals(resultCategory?.impactTiming)){
                      factorToSelect = "averaged"
                  }else if("final".equals(resultCategory?.impactTiming) || "external".equals(resultCategory?.impactTiming)){
                      factorToSelect = "final"
                  }
                  if(factorToSelect){
                      fixedDiscountFactor = discountFactors.get(factorToSelect)
                  }
            }
        }

        /*PP
        put this somewhere in a method called resolveDiscountFactors or so
        Map discountFactors = empty
        Decimal fixedDiscountFactor = empty
        If resultcategory.applyDiscountScenario {
            if (resultcategory.applyDiscountScenario?.get("scenariotype") == "fixed" ) {
                discountFactors = load  the scenario matching fixed value but leave empty if none found
            } else {
                discountFactors = load the scenario matching user selected value but leave it empty if none found
            }

            if resultcategory.impactTiming is not defined
                discountFactors = empty
            elseIf (impactTiming = final)
                discountFactors = empty
                fixedDiscountFactor = get the value from the map that matches "final" key
                // LOG here an error level log as it's a config mistake if it gets here
        }

        then you can pass on the discountFactors or fixedDiscountFactor to following stuff below as param


        You also need an utility function that gets the correct annual discountFactor for you. it should be called getAnnualDiscountFactor and it gets a search param and discountFactors map. It gets the value matching the map and works as follows:
        1. return the matching value if one found
        2. if input value is integer, and it's above 0 and below 200, you will retun the value matching "final" from the table
        3. if input value is not matching one of those log an ERROR and return 1



        PP */
        /*
        Related ticket: SW-1155
        If the result category has both 'applyStages' and 'process' fields defined, then the impact from the resource is calculated as follows:
        - if the resource has impact 'D' (Resource.impacts = [..., D: [...], ...]) and the result category apply stages contains 'D' (ResultCategory.applyStages = [..., D ,...]),
          then the impact is calculated based on the 'applyStages' value without taking into account 'process' value;
        - otherwise the impact is calculated using the 'process' value.
         */

        if (process && !(datasetResource?.impacts?.containsKey(D_STAGE) && (D_STAGE in resultCategory.applyStages))) {
            for (Map.Entry<String, Object> pr : process.entrySet()) {
                String processName = pr.getKey()
                Object processParameters = pr.getValue()
                CalculationResult processResult = null

                if (Constants.QueryCalculationProcess.RECURRING_REPLACEMENT.toString() == processName) {
                    // Note! Can be multiple results / removing already loopped results so gets list of results straight instead of one result
                    scenarioResults = recurringReplacementProcessingResult(dataset, resultCategory, indicator, scenarioResults,
                            entity, preparedDenominator, datasetResource, resourceSubType, discountFactors)
                    //PP add here discountFactors as last optional param
                } else if (Constants.QueryCalculationProcess.TRANSPORT.toString() == processName) {
                    processResult = dataTransportProcessingResult(dataset, resultCategory, entity, indicator,
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.TRANSPORT_DISTANCE),
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.TRANSPORT_RESOURCE),
                            datasetResource, resourceSubType)
                } else if (Constants.QueryCalculationProcess.RECURRING_MAINTENANCE.toString() == processName && preparedDenominator.assessmentPeriod) {
                    processResult = recurrencenceProcessingResult(dataset, resultCategory, indicator.indicatorId, preparedDenominator, datasetResource, discountFactors, indicator)
                    //PP add here discountFactors as last optional param
                } else if (Constants.QueryCalculationProcess.C3_EOL.toString() == processName) {
                    processResult = wasteProcessingResult(dataset, resultCategory, (List<String>) processParameters,
                            indicator.indicatorId, projectCountry, datasetResource, lcaModel, fixedDiscountFactor, indicator, resourceSubType, eolProcessCache)
                    //PP add here fixedDiscountFactor as last optional param
                } else if (Constants.QueryCalculationProcess.WASTE.toString() == processName) {
                    processResult = wasteProcessingResultLegacy(dataset, resultCategory, (List<String>) processParameters,
                            indicator.indicatorId, datasetResource, fixedDiscountFactor, indicator, resourceSubType, resourceCache)
                    //PP add here fixedDiscountFactor as last optional param
                } else if (Constants.QueryCalculationProcess.C4_EOL.toString() == processName) {
                    processResult = disposalProcessingResult(dataset, resultCategory, (List<String>) processParameters,
                            indicator.indicatorId, projectCountry, datasetResource, lcaModel, fixedDiscountFactor, indicator, resourceSubType, eolProcessCache)
                    //PP add here fixedDiscountFactor as last optional param
                } else if (Constants.QueryCalculationProcess.D_EOL.toString() == processName) {
                    //15336 annie
                    //Both "D_EOL" and "BENEFIT" need to allow using discounting in the calculations.
                    //Both of above can use any of the timings. So they would need to be prepared so that it can work for any of the discounting methods between so a) final b) recurring and c) annual.
                    processResult = benefitProcessingResult(dataset, resultCategory, indicator.indicatorId,
                            entity, projectCountry, datasetResource, incinerationEnergyMixResource, lcaModel,
                            indicator, fixedDiscountFactor, discountFactors, preparedDenominator, resourceSubType, eolProcessCache)
                } else if (Constants.QueryCalculationProcess.BENEFIT.toString() == processName) {
                    //15336 annie
                    //Both "D_EOL" and "BENEFIT" need to allow using discounting in the calculations.
                    //Both of above can use any of the timings. So they would need to be prepared so that it can work for any of the discounting methods between so a) final b) recurring and c) annual.
                    processResult = benefitProcessingResultLegacy(dataset, resultCategory, indicator.indicatorId, entity,
                            projectCountry, datasetResource, indicator, fixedDiscountFactor, discountFactors,
                            preparedDenominator, resourceSubType, resourceCache)
                } else if (processParameters && Constants.QueryCalculationProcess.REPLACE_RESOURCE.toString() == processName) {
                    processResult = replaceResourceProcessingResult(dataset, allDatasets, resultCategory,
                            indicator.indicatorId, processParameters, resourceCache)
                } else if (Constants.QueryCalculationProcess.C2_EOL.toString() == processName) {
                    processResult = dataEOLTransportProcessingResult(dataset, resultCategory, indicator, projectCountry, datasetResource,
                            lcaModel, fixedDiscountFactor, resourceSubType, eolProcessCache)
                    //PP add here fixedDiscountFactor as last optional param
                } else if (Constants.QueryCalculationProcess.C1_C4_SUM.toString() == processName) {
                    ResourceType dsrt = resourceSubType
                    EolProcess eolProcess = ((EolProcessService) eolProcessService).getEolProcessForDataset(dataset, datasetResource,
                            dsrt, projectCountry, null, lcaModel, eolProcessCache)
                    //PP add here fixedDiscountFactor as last optional param

                    if (eolProcess) {
                        if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcess.epdConversionType) {
                            processResult = getEPDEOLCalculationResult(datasetResource, indicator.indicatorId, resultCategory, dataset)
                            applyLocalCompensation(dataset, processResult)
                            //annie - 15336
                            if (fixedDiscountFactor) {
                                processResult.calculatedDiscountFactor = fixedDiscountFactor
                                processResult.discountedResult = processResult.result * fixedDiscountFactor
                            }

                        } else {
                            //annie
                            CalculationResult c2Result = dataEOLTransportProcessingResult(dataset, resultCategory, indicator, projectCountry, datasetResource,
                                    lcaModel, fixedDiscountFactor, resourceSubType, eolProcessCache)
                            CalculationResult c3Result = wasteProcessingResult(dataset, resultCategory, (List<String>) processParameters,
                                    indicator.indicatorId, projectCountry, datasetResource, lcaModel, fixedDiscountFactor, indicator, resourceSubType, eolProcessCache)
                            CalculationResult c4Result = disposalProcessingResult(dataset, resultCategory, (List<String>) processParameters,
                                    indicator.indicatorId, projectCountry, datasetResource, lcaModel, fixedDiscountFactor, indicator, resourceSubType, eolProcessCache)

                            if (c2Result) {
                                c2Result.combineId = dataset.manualId
                                scenarioResults.add(c2Result)
                            }
                            if (c3Result) {
                                c3Result.combineId = dataset.manualId
                                scenarioResults.add(c3Result)
                            }
                            if (c4Result) {
                                c4Result.combineId = dataset.manualId
                                scenarioResults.add(c4Result)
                            }
                            isCombine = true
                        }
                    }
                } else if (Constants.QueryCalculationProcess.EOLTRANSPORT.toString() == processName) {
                    processResult = dataEOLTransportProcessingResultLegacy(dataset, resultCategory, indicator,
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.TRANSPORT_DISTANCE),
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.TRANSPORT_RESOURCE),
                            datasetResource, resourceSubType)
                } else if (Constants.QueryCalculationProcess.REFRIGERANT.toString() == processName) {
                    processResult = dataRefrigerantProcessingResult(dataset, resultCategory, indicator,
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.REFRIGERANT_QUANTITY),
                            getProcessTransportParam(process, Constants.QueryCalculationProcessQuestion.REFRIGERANT_RESOURCE),
                            datasetResource, fixedDiscountFactor)
                }

                if (processResult) {
                    scenarioResults.add(processResult)
                }
            }
        } else if (resultCategory.applyProcess) {
            log.debug("Applying process for ${resultCategory.resultCategoryId}")
            scenarioResults = applyProcessingResults(resultCategory.applyProcess, dataset, resultCategory,
                    indicator.indicatorId, resourceCache.getResource(dataset))
        } else if (!(resultCategory.calculateReplacementsOnly || resultCategory.calculateFractionalReplacementsOnly)) {
            //check with Panu
            CalculationResult calculationResult = new CalculationResult()
            calculationResult.indicatorId = indicator.indicatorId
            calculationResult.resultCategoryId = resultCategory.resultCategoryId
            calculationResult.result = dataset.calculatedQuantity
            calculationResult.calculationResourceId = dataset.resourceId
            calculationResult.calculationProfileId = dataset.profileId
            calculationResult.originalResourceId = dataset.resourceId
            calculationResult.originalProfileId = dataset.profileId
            calculationResult.datasetIds = [dataset.manualId]
            calculationResult.unit = dataset.userGivenUnit
            calculationResult.calculatedUnit = dataset.calculatedUnit
            calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
            calculationResult.calculationFormula = dataset.preResultFormula

            if (dataset.mapToResource && dataset.mapToResourceType && dataset.answerIds) {
                calculationResult.calculatedResourceRow = Boolean.TRUE
            }
            applyLocalCompensation(dataset, calculationResult)

            // LOG here some kind of warning if it's fractionalReplacement or fractionalReplacementOnly or fractionalMultiplier ???
            //PPAA 15.4. we need to incorporate it here too if it's set for resultCategory. IT would use a fixedDiscountFactor.
            //annie - 15336
            if (fixedDiscountFactor) {
                calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
            }
            // insert the value using fixeddiscountfactor similarly as in other cases
            scenarioResults.add(calculationResult)
        }

        if (resultCategory.calculateFractionalReplacementsOnly || resultCategory.calculateReplacementsOnly) {
            if (isCombine) {
                List<CalculationResult> newResults = new ArrayList<>()

                scenarioResults.each {
                    List<CalculationResult> temp = recurringReplacementProcessingResult(dataset, resultCategory, indicator, [it],
                            entity, preparedDenominator, datasetResource, resourceSubType)

                    if (temp) {
                        newResults.addAll(temp)
                    }
                }
                scenarioResults = newResults
            } else {
                scenarioResults = recurringReplacementProcessingResult(dataset, resultCategory, indicator, scenarioResults,
                        entity, preparedDenominator, datasetResource, resourceSubType)
            }
        }
        if((resultCategory.calculateReplacementsOnly || hasFractionalReplacement) && resultCategory.applyDiscountScenario && scenarioResults && discountFactors) {
            //annie - part of 15336 - releasing with REL2108-104
            Double serviceLife = getServiceLife(dataset, datasetResource, resourceSubType, entity, indicator)
            log.info("serviceLife is ${serviceLife}")
            log.info("FRACTIONAL REP - RESOURCE ID is ${dataset.resourceId}")
            if(dataset.comment){
                log.info("FRACTIONAL REP - COMMENT is ${dataset.comment}")
            }
            log.info("RESOURCE ID is ${dataset.resourceId}")
            calculateDiscountForReplacements( resultCategory , scenarioResults ,
                    preparedDenominator, serviceLife , discountFactors ,hasFractionalReplacement)

        }
        // TODO move logic for resultCategory.applyEnergyBenefitSharing to a method after release
        if (resultCategory?.applyEnergyBenefitSharing) {
            energyBenefitSharing(entity, resultCategory.applyEnergyBenefitSharing, resultCategory.resultCategoryId,
                    scenarioResults, resourceCache)

            if (indicator?.multiplier != null) {
                for (CalculationResult c : scenarioResults) {
                    if (c.result) {
                        Double multipliedResult = c.result * indicator.multiplier
                        c.calculationFormula = "${c.calculationFormula},<b>Multiplier defined in indicator:" +
                                "</b> ${multipliedResult} = ${c.result.round(2)} * ${indicator.multiplier.round(2)} (${resultCategory.resultCategoryId})"
                        c.result = multipliedResult
                    }
                }
            }
        }
        return scenarioResults
    }

    @CompileStatic
    private static String getProcessTransportParam(Map<String, Object> process, Constants.QueryCalculationProcessQuestion question) {
        List<String> trParams = (List<String>) process.get(question.toString())
        return !org.springframework.util.CollectionUtils.isEmpty(trParams) ? trParams.get(0) : null
    }

    /**
     * calculates discount for replacements including fractional replacements
     * @param resultCategory
     * @param scenarioResults
     * @param preparedDenominator
     * @param serviceLife
     * @param discountFactors
     */
    private void calculateDiscountForReplacements(ResultCategory resultCategory, List<CalculationResult> scenarioResults,
                                                  PreparedDenominator preparedDenominator, Double serviceLife, Map<String, Double> discountFactors, Boolean hasFractionalReplacement = null) {

        if (serviceLife != null && serviceLife > 0D) {
            serviceLife = Math.ceil(serviceLife.doubleValue())
            int recurrences = 1
            Map<String, Double> resultWithDiscountPerOccurencePeriod = new TreeMap<String, Double>()
            Map<Integer, Double> discountAndFractionalMulplierCombined = [:]
            List<Integer> recurrenceMultipliers = []
            Map<Integer, Integer> serviceLifePerOccurence = [:]
            Map<Integer, Double> discountedRecurrences = [:]
            Double remainingCalculationPeriod = preparedDenominator.assessmentPeriod - serviceLife
            for (Double i = serviceLife; i < preparedDenominator.assessmentPeriod; i = i + serviceLife) {
                recurrenceMultipliers.add(new Integer(recurrences.intValue()))
                serviceLifePerOccurence.put(new Integer(recurrences.intValue()), i.toInteger())
                recurrences++
            }
            if (discountFactors) {
                discountedRecurrences = calculateDiscountedRecurrences(preparedDenominator.assessmentPeriod, discountFactors, serviceLifePerOccurence)
                if (discountedRecurrences) {
                    scenarioResults?.each { CalculationResult calculationResult ->
                        //annie - part of 15336 - releasing with REL2108-104
                        if (!calculationResult.calculatedDiscountFactor){

                            recurrenceMultipliers.each { Integer recurrency ->
                                if(resultCategory.calculateReplacementsOnly) {
                                    loggerUtil.info(log , "INSIDE calculateReplacementsOnly REPLACEMENT CONDITION")
                                    if (discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                        resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(),
                                                calculationResult.resultWithoutOccurrencePeriods ? calculationResult.resultWithoutOccurrencePeriods * discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) : 0)
                                    }
                                } else if (hasFractionalReplacement){
                                    loggerUtil.info(log , "INSIDE calculateFractionalReplacementsOnly || applyFractionalReplacementMultiplier REPLACEMENT CONDITION")
                                    loggerUtil.info(log , " RESULT WITHOUT OCCURENCE PERIODS -- > ${calculationResult.resultWithoutOccurrencePeriods}")
                                    loggerUtil.info(log , " RESULT CATEGORY ID-- > ${resultCategory.resultCategoryId}")
                                    // || remainingCalculationPeriod == 0 add OR condition
                                    // check the greater or equal in the nxt line
                                    if (discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) && (serviceLife <= remainingCalculationPeriod)) {
                                        resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(),
                                                calculationResult.resultWithoutOccurrencePeriods ? calculationResult.resultWithoutOccurrencePeriods * discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) : 0)
                                        remainingCalculationPeriod = remainingCalculationPeriod - serviceLife
                                        discountAndFractionalMulplierCombined.put(serviceLifePerOccurence?.get(recurrency) , discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)))
                                    } else if (discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                        resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(),
                                                calculationResult.resultWithoutOccurrencePeriods ?
                                                        calculationResult.resultWithoutOccurrencePeriods * discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) * remainingCalculationPeriod / serviceLife : 0)
                                        discountAndFractionalMulplierCombined.put(serviceLifePerOccurence?.get(recurrency) , discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) * remainingCalculationPeriod / serviceLife)
                                    }
                                }
                            }
                            calculationResult.resultWithDiscountPerOccurencePeriod = resultWithDiscountPerOccurencePeriod
                            if (resultWithDiscountPerOccurencePeriod) {
                                calculationResult.calculatedDiscountFactor = discountedRecurrences?.values().sum()
                                calculationResult.discountedResult = resultWithDiscountPerOccurencePeriod?.values().sum()
                                calculationResult.discountAndFractionalMulplierCombined = discountAndFractionalMulplierCombined
                            }
                        }
                    }

                }
            }
        }
    }

    private static String getResourceTypeKey(ResourceType rt) {
        if (!rt) {
            return null
        }
        return rt.resourceType + "_" + "${rt.subType ?: ''}"
    }

    private static String getResourceTypeKey(Resource dr) {
        if (!dr) {
            return null
        }
        return dr.resourceType + "_" + "${dr.resourceSubType ?: ''}"
    }

    private ResourceType findResourceType(Map<String, ResourceType> cache, Resource dr) {
        if (!dr || !cache) {
            return null
        }

        ResourceType rt = cache.get(getResourceTypeKey(dr))
        if (rt == null && (dr.resourceType || dr.resourceSubType)) {
            String alert = "Resource ${dr.resourceId} / ${dr.profileId} no resourceType found with params resourceType: " +
                    "${dr.resourceType} and subType ${dr.resourceSubType}."
            log.trace(alert)
        }
        return rt
    }


    /**
     * LCC equivalent of calculateCalculationRuleImpact (LCA)
     *
     * @param entity
     * @param resultCategories
     * @param indicator
     * @param calculationRules
     * @param datasets
     * @param parentEntity
     * @param preparedDenominator
     * @return
     */
    private List<CalculationResult> calculateCostForLCC(Entity entity, List<ResultCategory> resultCategories, Indicator indicator,
                                     List<CalculationRule> calculationRules, List<Dataset> datasets, Entity parentEntity,
                                                        PreparedDenominator preparedDenominator, ResourceCache resourceCache, EolProcessCache eolProcessCache) {
        long start = System.currentTimeMillis()
        List<Dataset> allDatasets = entity.datasets?.toList()
        Resource projectCountry = parentEntity?.countryResource
        Resource incinerationEnergyMixResource = parentEntity?.incinerationEnergyMixResource
        String lcaModel = parentEntity?.lcaModel
        List<CalculationResult> resultsByScenarios = []
        Map<CalculationRule, Double> generalInflationRatePerRule = [:]
        Map<String, Double> energyInflationRatePerRule = [:]
        Map<String, Double> waterInflationRatePerRule = [:]
        Map<String, Double> discountRatePerRule = [:]

        calculationRules?.findAll({ !it.virtual })?.each { CalculationRule calculationRule ->
            Double generalInflationRate = calculationRule.generalInflationValueRef ?
                    valueReferenceService.getDoubleValueForEntity(calculationRule.generalInflationValueRef, entity, null, null, resourceCache) :
                    getDoubleFromQuestion(calculationRule.generalInflation, datasets)
            generalInflationRate = generalInflationRate ? generalInflationRate : 0
            generalInflationRatePerRule.put(calculationRule, generalInflationRate)
            Double energyInflationRate = calculationRule.energyInflationValueRef ?
                    valueReferenceService.getDoubleValueForEntity(calculationRule.energyInflationValueRef, entity, null, null, resourceCache) :
                    getDoubleFromQuestion(calculationRule.energyInflation, datasets)
            energyInflationRate = energyInflationRate ? energyInflationRate : 0
            energyInflationRatePerRule.put(calculationRule.calculationRuleId, energyInflationRate)
            Double waterInflationRate = calculationRule.waterInflationValueRef ?
                    valueReferenceService.getDoubleValueForEntity(calculationRule.waterInflationValueRef, entity, null, null, resourceCache) : 0
            waterInflationRate = waterInflationRate ? waterInflationRate : 0
            waterInflationRatePerRule.put(calculationRule.calculationRuleId, waterInflationRate)
            Double discountRate = calculationRule.discountRateValueRef ?
                    valueReferenceService.getDoubleValueForEntity(calculationRule.discountRateValueRef, entity, null, null, resourceCache) :
                    getDoubleFromQuestion(calculationRule.discountRate, datasets)
            discountRate = discountRate ? discountRate : 0
            discountRatePerRule.put(calculationRule.calculationRuleId, discountRate)
        }

        Map<Dataset, Map<ResultCategory, List<CalculationResult>>> resultsPerDataset = [:]

        // need to run a fetch for all C stage resources before going into the resolveScenarioResults loop
        resourceCache.initCStageResources()

        long start2 = System.currentTimeMillis()
        datasets?.each { Dataset dataset ->
            Resource datasetResource = resourceCache.getResource(dataset)
            Map<ResultCategory, List<CalculationResult>> resultsPerCategory = [:]
            resultCategories?.findAll({ !it.virtual })?.each { ResultCategory resultCategory ->
                if (dataset.resultCategoryIds.contains(resultCategory.resultCategoryId)) {
                    //Resolve result for resultCategory for this dataset
                    List<CalculationResult> scenarioResults = resolveScenarioResults(entity, dataset, resultCategory, indicator,
                            preparedDenominator, projectCountry, allDatasets, datasetResource,
                            incinerationEnergyMixResource, lcaModel, null, null, resourceCache, eolProcessCache)?.findAll({
                        !it.calculationRuleId && resultCategory.resultCategoryId.equals(it.resultCategoryId) && it.result != null && it.originalResourceId
                    })
                    resultsPerCategory.put(resultCategory, scenarioResults)
                }
            }
            resultsPerDataset.put(dataset, resultsPerCategory)
        }

        List allScenarioResults = []
        resultsPerDataset.each { ds, results ->
            results.each { cat, scenarioResults ->
                allScenarioResults.addAll(scenarioResults)
            }
        }
        preloadCalculationResources(allScenarioResults, resourceCache, Boolean.TRUE)
        log.info("Calculated all scenario results in ${System.currentTimeMillis() - start2}. Cache size: ${resourceCache?.size()}")

        datasets?.each { Dataset dataset ->
            Resource datasetResource = resourceCache.getResource(dataset)
            resultCategories?.findAll({ !it.virtual })?.each { ResultCategory resultCategory ->
                if (dataset.resultCategoryIds.contains(resultCategory.resultCategoryId)) {
                    List<CalculationResult> scenarioResults = resultsPerDataset.get(dataset)?.get(resultCategory)
                    if (scenarioResults) {
                        scenarioResults.each { CalculationResult calculationResult ->

                            Resource resource = resourceCache.getResource(calculationResult, true)

                            Double calculatedCost
                            // we are always using such cost basis as is actually useful for the resultCategory, failing over to cost_EUR
                            String costBasis = resultCategory.costBasis ? resultCategory.costBasis : "cost_EUR"
                            Double unitCost = 1
                            String costCalculationFormula = ""

                            if (costBasis == "unitCost") {
                                String unitCostInDataset = calculationResult.datasetAdditionalQuestionAnswers?.get("unitCost")

                                if (unitCostInDataset && DomainObjectUtil.isNumericValue(unitCostInDataset)) {
                                    //  unitCost is always user declared; it's not something looked up from resource
                                    unitCost = DomainObjectUtil.convertStringToDouble(unitCostInDataset)
                                }
                            } else if ("totalCost".equals(costBasis)) {
                                String totalCostInDataset = calculationResult.datasetAdditionalQuestionAnswers?.get("totalCost")

                                if (totalCostInDataset && DomainObjectUtil.isNumericValue(totalCostInDataset)) {
                                    calculatedCost = DomainObjectUtil.convertStringToDouble(totalCostInDataset)
                                }
                            } else {
                                unitCost = (Double) getAnyParameterFromResource(costBasis, resource)
                            }

                            if (unitCost && !"totalCost".equals(costBasis)) {
                                if (calculationResult.resultWithoutOccurrencePeriods) {
                                    costCalculationFormula = "${calculationResult.resultWithoutOccurrencePeriods.round(2)} * ${unitCost.round(2)}"
                                    calculatedCost = calculationResult.resultWithoutOccurrencePeriods * unitCost
                                } else {
                                    costCalculationFormula = "${calculationResult.result.round(2)} * ${unitCost.round(2)}"
                                    calculatedCost = calculationResult.result * unitCost
                                }
                            } else {
                                costCalculationFormula = "${calculatedCost ? calculatedCost.round(2) : "0"}"
                                if (!unitCost) {
                                    loggerUtil.warn(log, "calculateCost missing unitCost")
                                    flashService.setWarningAlert("CalculateCost missing unitCost", true)
                                }
                            }

                            if (calculatedCost != null) {
                                generalInflationRatePerRule.each { CalculationRule rule, Double generalInflationRate ->

                                    if (!reusedMaterialIgnore(dataset, resultCategory, rule)) {
                                        CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                                        Double factor = 0
                                        Double inflationRate
                                        Double discountRate = discountRatePerRule.get(rule.calculationRuleId)
                                        Double energyInflationRate = energyInflationRatePerRule.get(rule.calculationRuleId)
                                        Double waterInflationRate = waterInflationRatePerRule.get(rule.calculationRuleId)

                                        if (resultCategory.costInflation?.equals("energy")) {
                                            inflationRate = energyInflationRate
                                        } else if ("water".equals(resultCategory.costInflation)) {
                                            inflationRate = waterInflationRate
                                        } else {
                                            inflationRate = generalInflationRate
                                        }

                                        if ("annual".equals(resultCategory.discountRule)) {
                                            if (preparedDenominator.assessmentPeriod && preparedDenominator.assessmentPeriod >= 1) {
                                                Double annualFactor = 1

                                                for (int i = 0; i < preparedDenominator.assessmentPeriod; i++) {
                                                    annualFactor = annualFactor / (1 + ((discountRate - inflationRate) / 100))
                                                    factor = factor + annualFactor
                                                }
                                            }
                                        } else if ("periodical".equals(resultCategory.discountRule)) {
                                            copy.resultPerOccurencePeriod?.keySet()?.each { occurrencePeriod ->
                                                Double annualFactor = 1

                                                for (int i = 0; i < (int) occurrencePeriod.toDouble(); i++) {
                                                    annualFactor = annualFactor / (1 + ((discountRate - inflationRate) / 100))
                                                }
                                                // the last element by definition is final
                                                factor = factor + annualFactor
                                                // factor = 1 + applicableInflation?.get(occurrencePeriod - 1) - discountRateList?.get(occurrencePeriod - 1)
                                            }
                                        } else if ("final".equals(resultCategory.discountRule)) {
                                            Double annualFactor = 1

                                            for (int i = 0; i < preparedDenominator.assessmentPeriod; i++) {
                                                annualFactor = annualFactor / (1 + ((discountRate - inflationRate) / 100))
                                            }
                                            // the last element by definition is final
                                            factor = annualFactor
                                        } else {
                                            factor = 1
                                        }
                                        copy.calculationRuleId = rule.calculationRuleId
                                        copy.result = factor ? calculatedCost * factor : 0

                                        if (copy.result && preparedDenominator.overallDenominator) {
                                            copy.result = copy.result / preparedDenominator.overallDenominator
                                        } else if (preparedDenominator.overallDenominator == null) {
                                            copy.result = 0
                                        }
                                        copy.calculationFormula = "${calculationResult.calculationFormula},<b>LCC " +
                                                ":</b> ${copy.result.round(2)} = ${costCalculationFormula} * " +
                                                "${factor.round(3)} / ${preparedDenominator.overallDenominator ?: 0} (${resultCategory.discountRule}) (${resultCategory.resultCategoryId} / ${rule.calculationRuleId})"
                                        resultsByScenarios.add(copy)
                                    }

                                }
                            }
                        }

                    }
                }
            }
        }
        log.trace("Calculate Cost For LCC finished in ${System.currentTimeMillis() - start}")
        return resultsByScenarios
    }

    /**
     * If the material row passes the rules filter then allows calculating result for that row, otherwise skips this rule
     * for the material row
     *
     * @param copy
     * @param resource
     * @param ruleFilterParameter
     * @param ruleAcceptValues
     * @param countryResource
     * @param resourceAttributes
     * @param linkedDataset
     * @param indicatorAdditionalQuestionIds
     * @param applyConditionFromFilterParamList
     * @return
     */

    private Boolean resolveRulePassed(CalculationResult copy, Resource resource, ResourceType resourceSubType, String ruleFilterParameter,
                                      List<String> ruleAcceptValues, Resource countryResource, List<String> resourceAttributes,
                                      Dataset linkedDataset, Set<String> indicatorAdditionalQuestionIds, String applyConditionFromFilterParamList = null,
                                      EolProcessCache eolProcessCache) {
        Boolean rulePassed = Boolean.FALSE

        if (ruleFilterParameter) {
            if ("EOLProcessClass".equals(ruleFilterParameter)) {
                String eolProcessClass
                String eolProcessId = copy.datasetAdditionalQuestionAnswers?.get(Constants.EOL_QUESTIONID)

                if (eolProcessId) {
                    if (Constants.EOLProcess.DO_NOTHING.toString() == eolProcessId) {
                        eolProcessClass = Constants.EOLProcess.DO_NOTHING.toString()
                    } else if (Constants.EOLProcess.REUSE_AS_MATERIAL.toString() == eolProcessId) {
                        eolProcessClass = Constants.EOLProcess.REUSE_AS_MATERIAL.toString()
                    } else {
                        eolProcessClass = eolProcessCache?.getEolProcessByEolProcessId(eolProcessId)?.eolProcessClass
                    }
                } else {
                    eolProcessClass = eolProcessService.getEolProcessForSubType(resourceSubType, countryResource, eolProcessCache)?.eolProcessClass
                }

                if (ruleAcceptValues && eolProcessClass && ruleAcceptValues.contains(eolProcessClass)) {
                    rulePassed = Boolean.TRUE
                }
            } else {
                if (resource && resourceAttributes?.contains(ruleFilterParameter)) {
                    String resourceFilterString = getAnyParameterFromResource(ruleFilterParameter, resource)


                    if (resourceFilterString) {
                        if (ruleAcceptValues && ruleAcceptValues.contains(resourceFilterString)) {
                            rulePassed = Boolean.TRUE
                        }
                    }
                } else {
                    // To understand this logic, read Arturs' note 0055389 ticket 15748. Currently we have only one extra condition so made a quick fix for the patch.
                    boolean applyConditionalCheck = applyConditionFromFilterParamList?.equals('presentInDataset') && indicatorAdditionalQuestionIds?.contains(ruleFilterParameter) && linkedDataset?.additionalQuestionAnswers?.get(ruleFilterParameter) != null
                    boolean applyNonConditionalCheck = !applyConditionFromFilterParamList && indicatorAdditionalQuestionIds?.contains(ruleFilterParameter)

                    // If the param is not a resource attribute check from additionalQuestionAnswers of the dataset
                    if (applyConditionalCheck || applyNonConditionalCheck) {
                        String resourceFilterString = linkedDataset?.additionalQuestionAnswers?.get(ruleFilterParameter)

                        if (ruleAcceptValues && resourceFilterString && ruleAcceptValues.contains(resourceFilterString)) {
                            rulePassed = Boolean.TRUE
                        }
                    } else {
                        // If indicator or query doesnt have the additionalQuestion the rule passes
                        rulePassed = Boolean.TRUE
                    }
                }
            }
        }
        return rulePassed
    }

    /**
     * Multiplies result with scaling factor if user has given it in scalingFactor additionalQuestion
     *
     * @param copy
     * @param resultCategory
     * @param calculationRule
     */

    private void applyScalingFactor(CalculationResult copy, ResultCategory resultCategory, CalculationRule calculationRule) {
        String scalingFactor = copy.datasetAdditionalQuestionAnswers?.get("scalingFactor")

        if (scalingFactor) {
            if (scalingFactor.isNumber()) {
                Double factorFromAdditionalQuestion = DomainObjectUtil.convertStringToDouble(scalingFactor)

                if (factorFromAdditionalQuestion) {

                    if (copy.result) {
                        Double factoredResult = copy.result * factorFromAdditionalQuestion
                        copy.calculationFormula = "${copy.calculationFormula},<b>ScalingFactor:</b> ${factoredResult.round(2)} = ${copy.result.round(2)} * ${factorFromAdditionalQuestion.round(2)} (scalingFactor) (${resultCategory.resultCategoryId} / ${calculationRule.calculationRuleId})"
                        copy.result = factoredResult
                    }

                    if (copy.monthlyResults) {
                        copy.monthlyResults = copy.monthlyResults.each { it ->
                            it.value = copy.monthlyResults.get(it.key) != null ? copy.monthlyResults.get(it.key) * factorFromAdditionalQuestion : null
                        }
                    }

                    if (copy.quarterlyResults) {
                        copy.quarterlyResults = copy.quarterlyResults.each { it ->
                            it.value = copy.quarterlyResults.get(it.key) != null ? copy.quarterlyResults.get(it.key) * factorFromAdditionalQuestion : null
                        }
                    }
                }
            }
        }
    }

    /**
     * General method for applying different multipliers to the final result from the calcuationRule
     *
     * @param result
     * @param multiplier
     * @param setToZeroIfNoMultiplier
     * @param resultCategory
     * @param calculationRule
     * @param processName
     */

    private void applyMultiplier(CalculationResult result, Double multiplier, Boolean setToZeroIfNoMultiplier, ResultCategory resultCategory,
                                 CalculationRule calculationRule, String processName, Boolean fromDiscounting = null,
                                    Boolean setToFixedMultiplierValue = null , Integer noOfOccurence = null, Boolean hasFractionalReplacement = null) {
        if (multiplier != null) {
            String typeOfMultplier
            if (fromDiscounting && hasFractionalReplacement) {
                typeOfMultplier = "Discount & fractional impact : "
            } else if (fromDiscounting) {
                typeOfMultplier = "Discount : "
            }else if(hasFractionalReplacement){
                typeOfMultplier = "fractional impact : "
            }else{
                typeOfMultplier = "Characterisation : "
            }
            if (resultCategory.applyDiscountScenario && result.resultWithoutOccurrencePeriods && fromDiscounting && !hasFractionalReplacement && (Constants.IMPACTTIMING_RECURRING.equals(resultCategory.impactTiming)) ) {
                Double multipliedResult = result.resultWithoutOccurrencePeriods * multiplier
                result.calculationFormula = "${result.calculationFormula},<b>${typeOfMultplier}${roundingNumber(multipliedResult)} ${calculationRule.unit?.get("EN") ? calculationRule.unit?.get("EN") : ''}.</b> Calculated from ${roundingNumber(result.resultWithoutOccurrencePeriods)} * ${roundingNumber(multiplier)} (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId}) ${processName}"
                result.result = multipliedResult
            }else if(hasFractionalReplacement && fromDiscounting && resultCategory.applyDiscountScenario && result.resultWithoutOccurrencePeriods){
                StringBuffer formula = new StringBuffer()
                Map<Integer , Double> resultWithDiscountAndFractionalMultiplier = [:]
                result.discountAndFractionalMulplierCombined.each { Integer key , Double value ->
                    if(value){
                        resultWithDiscountAndFractionalMultiplier.put( key, value * result.resultWithoutOccurrencePeriods)
                        formula.append(formula ? "+" : "")
                        formula.append(roundingNumber(value * result.resultWithoutOccurrencePeriods).toString())
                    }
                }
                Double multipliedResult = resultWithDiscountAndFractionalMultiplier?.values().sum()
                result.calculationFormula = "${result.calculationFormula},<b>${typeOfMultplier}${roundingNumber(multipliedResult)} ${calculationRule.unit?.get("EN") ? calculationRule.unit?.get("EN") : ''}.</b> Calculated from ${formula}, (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId}) ${processName}"
                result.result = multipliedResult
            }else if(noOfOccurence > 0 && hasFractionalReplacement && !fromDiscounting){
                Double singleOccValue = result?.result / noOfOccurence
                Double multipliedResult = singleOccValue * multiplier
                result.calculationFormula = "${result.calculationFormula},<b>${typeOfMultplier}${roundingNumber(multipliedResult)} ${calculationRule.unit?.get("EN") ? calculationRule.unit?.get("EN") : ''}.</b> Calculated from ${roundingNumber(singleOccValue)} * ${roundingNumber(multiplier)} (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId}) ${processName}"
                result.result = multipliedResult
            } else if (setToFixedMultiplierValue) {
                Double fixedCalculatonBasis = multiplier
                result.result = fixedCalculatonBasis
                result.calculationFormula = "${result.calculationFormula},<b>${typeOfMultplier}${roundingNumber(fixedCalculatonBasis)} ${calculationRule.unit?.get("EN") ? calculationRule.unit?.get("EN") : ''}.</b> Calculated from ${roundingNumber(fixedCalculatonBasis)} (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId}) ${processName}"
            } else if (result.result) {
                Double multipliedResult = result.result * multiplier
                result.calculationFormula = "${result.calculationFormula},<b>${typeOfMultplier}${roundingNumber(multipliedResult)} ${calculationRule.unit?.get("EN") ? calculationRule.unit?.get("EN") : ''}.</b> Calculated from ${roundingNumber(result.result)} * ${roundingNumber(multiplier)} (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId}) ${processName}"
                result.result = multipliedResult
            }

            if (result.monthlyResults) {
                result.monthlyResults = result.monthlyResults.each { it ->
                    it.value = it.value != null ? result.monthlyResults.get(it.key) * multiplier : null
                }
            }

            if (result.quarterlyResults) {
                result.quarterlyResults = result.quarterlyResults.each { it ->
                    it.value = it.value != null ? result.quarterlyResults.get(it.key) * multiplier : null
                }
            }
        } else if (setToZeroIfNoMultiplier) {
            result.result = 0D
            result.calculationFormula = "${result.calculationFormula},<b>Characterisation : ${result.result} ${calculationRule.unit?.get("EN") ?calculationRule.unit?.get("EN"):''}.</b> Result set to zero since no multiplier found (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId})"

            if (result.monthlyResults) {
                result.monthlyResults = result.monthlyResults.each { it ->
                    it.value = 0D
                }
            }

            if (result.quarterlyResults) {
                result.quarterlyResults = result.quarterlyResults.each { it ->
                    it.value = 0D
                }
            }
        }
    }

    /**
     * General method for applying different dividers to the final result from the calcuationRule
     *
     * @param result
     * @param multiplier
     * @param setToZeroIfNoMultiplier
     * @param resultCategory
     * @param calculationRule
     * @param processName
     */

    private void applyDivider(CalculationResult result, Double divider, Boolean setToZeroIfNoDivider, ResultCategory resultCategory, CalculationRule calculationRule, String processName) {
        if (divider != null) {
            if (result.result) {
                Double dividedResult = result.result / divider
                result.calculationFormula = "${result.calculationFormula},<b>Characterisation: ${roundingNumber(dividedResult)} ${calculationRule.unit?.get("EN")}.</b>  Calculated from ${roundingNumber(result.result)} / ${roundingNumber(divider)} (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId})"
                result.result = dividedResult

            }

            if (result.monthlyResults) {
                result.monthlyResults = result.monthlyResults.each { it ->
                    it.value = it.value ? result.monthlyResults.get(it.key) / divider : null
                }
            }

            if (result.quarterlyResults) {
                result.quarterlyResults = result.quarterlyResults.each { it ->
                    it.value = it.value ? result.quarterlyResults.get(it.key) / divider : null
                }
            }
        } else if (setToZeroIfNoDivider) {
            result.result = 0D
            result.calculationFormula = "${result.calculationFormula},<b>Characterisation: ${result.result} ${calculationRule.unit?.get("EN")}.</b> Result set to zero since no divider found (${resultCategory.resultCategoryId} - ${calculationRule.calculationRuleId})"

            if (result.monthlyResults) {
                result.monthlyResults = result.monthlyResults.each { it ->
                    it.value = 0D
                }
            }

            if (result.quarterlyResults) {
                result.quarterlyResults = result.quarterlyResults.each { it ->
                    it.value = 0D
                }
            }
        }
    }

    /**
     * Resolves the local compensation multiplier
     * @see #newEnergyMixCompensation
     *
     * @param multiplier
     * @param resource
     * @param targetCountryResource
     * @param projectCountry
     * @param resultCategory
     * @param calculationRuleDefaultData
     * @param entity
     * @param calculationResult
     * @param localCompensationMethodVersion
     * @param localCompAreaResources
     * @param calculationRule
     * @param defaultEnergyProfileResourceId
     * @param defaultEnergyProfileProfileId
     * @param rowByRowLocalCompsLicensed
     */

    private Map<String, Object> resolveCalculationRuleMultiplier(String multiplier, Resource resource, Resource targetCountryResource,
                                                                 Resource projectCountry, ResultCategory resultCategory, CalculationRuleDefaultData calculationRuleDefaultData,
                                                                 Entity entity, CalculationResult calculationResult, Integer localCompensationMethodVersion, List<Resource> localCompAreaResources,
                                                                 CalculationRule calculationRule, String defaultEnergyProfileResourceId, String defaultEnergyProfileProfileId,
                                                                 Boolean rowByRowLocalCompsLicensed, Boolean debugLicenseFeature = Boolean.FALSE, ResourceCache resourceCache) {
        Map<String, Object> map
        Double factor

        if (multiplier) {
            if (multiplier.endsWith("_SUM")) {
                factor = getSumImpactFromResource(multiplier, resource, resultCategory)
            } else {
                Double originalFactor = (Double) getCalculationImpactFromResource(multiplier, resource, resultCategory, calculationResult.useEolProcessStage)

                if (Resource.allowedFactorCategories.contains(multiplier)) {
                    if (resultCategory?.compensateEnergyMix && (!resultCategory.applyStages || resultCategory.applyStages.contains("A1-A3") || resultCategory.applyStages.contains("B4-B5"))) {
                        if (calculationResult.applyLocalComps) {
                            map = newEnergyMixCompensation(entity, resultCategory, calculationResult, multiplier,
                                    resource, targetCountryResource, originalFactor,
                                    projectCountry, localCompensationMethodVersion, localCompAreaResources,
                                    calculationRule, defaultEnergyProfileResourceId, defaultEnergyProfileProfileId,
                                    rowByRowLocalCompsLicensed, debugLicenseFeature, resourceCache)
                            if ((localCompensationMethodVersion && localCompensationMethodVersion > 10)) {
                                // Stupid fix for broken code, but cant change old local comp data anymore
                                factor = (Double) map?.get("compensatedImpactFactor")
                            }
                        } else {
                            factor = originalFactor
                        }
                    } else {
                        factor = originalFactor
                    }
                } else {
                    factor = originalFactor
                }
            }
            if (factor == null) {
                if (calculationRuleDefaultData && multiplier.equals(calculationRuleDefaultData.data)) {
                    String defaultDataMultiplier = calculationRuleDefaultData.defaultData
                    Double originalFactor

                    if (calculationRuleDefaultData.defaultValue) {
                        originalFactor = calculationRuleDefaultData.defaultValue
                    } else if (defaultDataMultiplier) {
                        originalFactor = (Double) getCalculationImpactFromResource(defaultDataMultiplier, resource, resultCategory, calculationResult.useEolProcessStage)

                        if (originalFactor && calculationRuleDefaultData.factor) {
                            originalFactor = originalFactor * calculationRuleDefaultData.factor
                        }
                    }

                    if (originalFactor) {
                        if (Resource.allowedFactorCategories.contains(defaultDataMultiplier)) {
                            if (resultCategory?.compensateEnergyMix && (!resultCategory.applyStages || resultCategory.applyStages.contains("A1-A3") || resultCategory.applyStages.contains("B4-B5"))) {
                                if (calculationResult.applyLocalComps) {
                                    map = newEnergyMixCompensation(entity, resultCategory, calculationResult, defaultDataMultiplier,
                                            resource, targetCountryResource, originalFactor,
                                            projectCountry, localCompensationMethodVersion,
                                            localCompAreaResources, calculationRule, defaultEnergyProfileResourceId,
                                            defaultEnergyProfileProfileId, rowByRowLocalCompsLicensed, debugLicenseFeature, resourceCache)
                                    if ((localCompensationMethodVersion && localCompensationMethodVersion > 10)) {
                                        factor = (Double) map?.get("compensatedImpactFactor")
                                    }
                                } else {
                                    factor = originalFactor
                                }
                            } else {
                                factor = originalFactor
                            }
                        } else {
                            factor = originalFactor
                        }
                    }
                }
            }
        }
        if (map) {
            return map
        } else {
            return [compensatedImpactFactor: factor]
        }
    }

    /**
     * Gets the country resources to be used in local compensation.
     * @return
     */

    private List<Resource> localCompensationAreaResources() {
        List<Resource> resources = Resource.withCriteria {
            eq('applicationId', 'system')
            eq('active', true)
            eq('defaultProfile', true)
            or {
                eq('resourceGroup', 'world')
                eq('resourceGroup', 'US-states')
                eq('resourceGroup', 'CA-provinces')
                eq('resourceGroup', 'constructionAreas')
            }
        }
        return resources
    }

    /**
     * Skips calculating row if rule and category has ignoreIfAdditionalQuestionTrue and row has an answer to it
     *
     * @param dataset
     * @param resultCategory
     * @param calculationRule
     * @return
     */

    private boolean reusedMaterialIgnore(Dataset dataset, ResultCategory resultCategory, CalculationRule calculationRule) {

        boolean ignore = false
        if (dataset && resultCategory.ignoreIfAdditionalQuestionTrue && calculationRule.ignoreIfAdditionalQuestionTrue) {
            List<String> additionalQuestionsToCheck = CollectionUtils.retainAll(resultCategory.ignoreIfAdditionalQuestionTrue, calculationRule.ignoreIfAdditionalQuestionTrue)
            if (additionalQuestionsToCheck) {
                for (String questionId in additionalQuestionsToCheck) {

                    boolean ignoreReusedMaterials = dataset?.getAdditionalQuestionAnswers()?.get(questionId)?.toString()?.toBoolean()
                    if (ignoreReusedMaterials) {
                        ignore = true
                    } else {
                        ignore = false
                        break
                    }
                }
            }
        }
        return ignore
    }

    /**
     * The main calculation method, first resolved result per resultCategory, then expands that result to calculate
     * result per calculationRule
     * @see #resolveScenarioResults
     *
     *
     * @param entity the design
     * @param indicator
     * @param calculationRules
     * @param resultCategories
     * @param datasets list of collected datasets that are relevant to the indicator
     * @param monthlyLicensed
     * @param quarterlyLicensed
     * @param parentEntity the project
     * @param preparedDenominator
     * @param byPassDenominator
     * @param indicatorAdditionalQuestionIds
     * @return
     */

    private List<CalculationResult> calculateCalculationRuleImpact(Entity entity,
                                                                   Indicator indicator, List<CalculationRule> calculationRules,
                                                                   List<ResultCategory> resultCategories, List<Dataset> datasets,
                                                                   Boolean monthlyLicensed, Boolean quarterlyLicensed,
                                                                   Entity parentEntity, PreparedDenominator preparedDenominator,
                                                                   Boolean byPassDenominator = Boolean.FALSE,
                                                                   Set<String> indicatorAdditionalQuestionIds = null,
                                                                   ResourceCache resourceCache, EolProcessCache eolProcessCache) {
        if (!datasets || !resultCategories || !calculationRules) {
            return []
        }
        /**
         * Get all attributes of resource and additional questions of indicator
         * used for filtering resources by
         * - calculationRule.ruleFilterParameter
         * - calculationRule.ruleFilterParameterList
         */
        long start = System.currentTimeMillis()
        List<String> resourceAttributes = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)
        indicatorAdditionalQuestionIds = indicatorAdditionalQuestionIds ?: questionService.getAdditionalQuestionIdsForIndicator(indicator, parentEntity)

        List<Resource> localCompAreaResources = localCompensationAreaResources()
        resourceCache.putResources(localCompAreaResources)
        resourceCache.putResources(resourceService.findResources(localCompAreaResources?.countryEnergyResourceId?.unique()))
        List<Dataset> allDatasets = entity?.datasets?.toList()
        List<CalculationResult> resultsByScenarios = []
        String projectCountryResourceId = parentEntity?.countryResourceResourceId
        String lcaModel = parentEntity?.lcaModel
        Boolean rowByRowLocalCompsLicensed = parentEntity?.expertParametersLicensed
        Boolean debugLicenseFeature = parentEntity?.isFeatureLicensed(Feature.DEBUG)

        /**
         * Prepare objects for local comp
         *  - projectCountry
         *  - defaultEnergyProfileResourceId
         *  - defaultEnergyProfileProfileId
         *  - targetCountryResource
         */
        String defaultLocalCompensationEnergyProfile = datasetService.getDefaultLocalCompensationCountryDataset(parentEntity)?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
        String defaultEnergyProfileResourceId
        String defaultEnergyProfileProfileId

        if (defaultLocalCompensationEnergyProfile && defaultLocalCompensationEnergyProfile.tokenize(".")?.size() == 2) {
            defaultEnergyProfileResourceId = defaultLocalCompensationEnergyProfile.tokenize(".")[0]
            defaultEnergyProfileProfileId = defaultLocalCompensationEnergyProfile.tokenize(".")[1]
        }

        if (defaultEnergyProfileProfileId && defaultEnergyProfileResourceId){
            Resource defaultEnergyProfileResource = optimiResourceService.getResourceWithParams(defaultEnergyProfileResourceId, defaultEnergyProfileProfileId, true)
            resourceCache.putResources([defaultEnergyProfileResource])
        }

        Resource incinerationEnergyMixResource = parentEntity?.incinerationEnergyMixResource
        Resource projectCountry = null

        if (projectCountryResourceId) {
            projectCountry = localCompAreaResources.find({ projectCountryResourceId.equals(it.resourceId) })
        }

        Resource targetCountryResource
        Integer localCompensationMethodVersion

        List<Dataset> datasetListForCompensateEnergyMix = valueReferenceService.getDatasetsForEntity(resultCategories?.find { it.compensateEnergyMix }?.compensateEnergyMix, parentEntity)
        String datasetAQAnswer = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)

        if (datasetAQAnswer && datasetAQAnswer.toString().isNumber()) {
            localCompensationMethodVersion = datasetAQAnswer.toString().toInteger()
        }

        if (datasetListForCompensateEnergyMix) {
            Dataset datasetForCompensateEnergyMix = datasetListForCompensateEnergyMix.get(0)
            def datasetAnswer = datasetForCompensateEnergyMix?.answerIds?.get(0)?.toString()
            if (datasetAnswer) {
                targetCountryResource = localCompAreaResources.find({ datasetAnswer.equals(it.resourceId) })
            }
        }

        /**
         * Prepare accountForEntity for calculationRule.multiplyWithOrganizationCarbonCost
         */
        Account accountForEntity

        if (calculationRules?.find({it.multiplyWithOrganizationCarbonCost})) {
            List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
            List<Account> accountsForEntity = validLicenses?.collect({it.accounts ?: []})?.flatten()?.sort({ Account a -> a.companyName})

            if (accountsForEntity?.size()) {
                accountForEntity = accountsForEntity.first()
            }
        }

        /**
         * Prepare discountScenarioSourceApplication for discounting architecture (LCA - GLA, RE2020, LCC)
         * annie - 15336
         */
        Application discountScenarioSourceApplication
        if(indicator.futureDiscountingEnabled) {
            String discountScenarioSourceApplicationId = indicator.indicatorDiscountScenarios?.get("discountScenarioSourceApplicationId")
            if(discountScenarioSourceApplicationId) {
                discountScenarioSourceApplication = applicationService.getApplicationByApplicationId(discountScenarioSourceApplicationId)
            }
        }

        Map<Dataset, Map<ResultCategory, List<CalculationResult>>> resultsPerDataset = [:]
        //this is performance sensitive!
        Map<String, Set<String>> categoriesWithDatasets = new HashMap<>()
        for (Dataset dataset in datasets) {
            for (String resultCategoryId in dataset.resultCategoryIds) {
                if (!categoriesWithDatasets.get(resultCategoryId)) {
                    categoriesWithDatasets.put(resultCategoryId, new HashSet<String>())
                }
                categoriesWithDatasets.get(resultCategoryId).add(dataset.manualId)
            }
        }
        log.trace("Cached datasets per categories: ${categoriesWithDatasets}")

        // need to run a fetch for all C stage resources before going into the resolveScenarioResults loop
        resourceCache.initCStageResources()

        long start2 = System.currentTimeMillis()
        for (Dataset dataset in datasets) {
            Set<String> datasetResultCategoryIds = dataset.resultCategoryIds ? new HashSet<String>(dataset.resultCategoryIds) :
                    new HashSet<String>()
            Resource datasetResource = resourceCache.getResource(dataset)
            Map<ResultCategory, List<CalculationResult>> resultsPerCategory = [:]
            for (ResultCategory resultCategory in resultCategories) {
                if (datasetResultCategoryIds.contains(resultCategory?.resultCategoryId)) {
                    Boolean hasFractionalReplacement = (resultCategory.applyFractionalReplacementMultiplier
                            || resultCategory.calculateFractionalReplacementsOnly).booleanValue()
                    // Start calculations for resultCategory >> afterwards the results are then used to calculate for calculationRules
                    //Resolve result for resultCategory for this dataset
                    List<CalculationResult> scenarioResults = resolveScenarioResults(entity, dataset, resultCategory, indicator,
                            preparedDenominator, projectCountry, allDatasets, datasetResource, incinerationEnergyMixResource,
                            lcaModel, discountScenarioSourceApplication, hasFractionalReplacement, resourceCache, eolProcessCache)?.findAll({
                        !it.calculationRuleId && resultCategory.resultCategoryId.equals(it.resultCategoryId) && it.result != null
                    })
                    resultsPerCategory.put(resultCategory, scenarioResults)
                }
            }
            resultsPerDataset.put(dataset, resultsPerCategory)
        }

        List<CalculationResult> allScenarioResults = []
        resultsPerDataset.each { Dataset ds, Map<ResultCategory, List<CalculationResult>> results ->
            results.each { ResultCategory cat, List<CalculationResult> scenarioResults ->
                allScenarioResults.addAll(scenarioResults)
            }
        }
        preloadCalculationResources(allScenarioResults, resourceCache, Boolean.FALSE)
        log.info("Calculated all scenario results in ${System.currentTimeMillis() - start2}. Cache size: ${resourceCache?.size()}")
        resourceCache.reInitResourceTypeCache()

        for (Dataset dataset in datasets) {
            String datasetManualId = dataset.manualId
            Resource datasetResource = resourceCache.getResource(dataset)
            Set<String> datasetResultCategoryIds = dataset.resultCategoryIds ? new HashSet<String>(dataset.resultCategoryIds) :
                    new HashSet<String>()
            for (ResultCategory resultCategory in resultCategories) {
                String resultCategoryId = resultCategory.resultCategoryId
                Set<String> categoryDatasetIds = categoriesWithDatasets.get(resultCategoryId)

                if (datasetResultCategoryIds.contains(resultCategoryId)) {
                    Boolean hasFractionalReplacement = (resultCategory.applyFractionalReplacementMultiplier
                            || resultCategory.calculateFractionalReplacementsOnly).booleanValue()
                    List<CalculationResult> scenarioResults = resultsPerDataset.get(dataset)?.get(resultCategory)

                    if (scenarioResults) {
                        List<Dataset> categoryDatasets = datasets.findAll {
                            categoryDatasetIds.contains(it.manualId)
                        }
                        // Start calculations for calculationRules
                        for (CalculationRule calculationRule in calculationRules) {
                            if ((!calculationRule.skipRuleForCategories ||
                                    (calculationRule.skipRuleForCategories && !calculationRule.skipRuleForCategories.contains(resultCategoryId))) &&
                                    !reusedMaterialIgnore(dataset, resultCategory, calculationRule)) {

                                List<String> ruleAcceptValues = calculationRule.ruleAcceptValues
                                String ruleFilterParameter = calculationRule.ruleFilterParameter
                                List<Map> ruleFilterParametersList = calculationRule.ruleFilterParametersList
                                CalculationRuleDefaultData calculationRuleDefaultData = calculationRule.defaultDataObject
                                List<CalculationResult> resultsForRule = []
                                List<CalculationResult> needsToBeCombined = []

                                if (!calculationRule.skipImpactCalculation) {
                                    // Environmental data quality penalty implementation
                                    List<String> requiredEnvironmentDataSourceStandards = indicator.requiredEnvironmentDataSourceStandards
                                    Double performancePenaltyMultiplier = indicator.performancePenaltyMultiplier

                                    for (CalculationResult calculationResult in scenarioResults) {
                                        Boolean discountApplied = false
                                        CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                                        Set<String> copyDatasetIds = copy.datasetIds ? new HashSet<String>(copy.datasetIds) :
                                                new HashSet<String>()

                                        /**
                                         * Apply discounting
                                         * annie - 15536 and REL2108-104
                                         */
                                        if (!discountApplied && resultCategory.applyDiscountScenario && copy.result && copy.discountedResult
                                                && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationRule?.calculationRuleId)) {
                                            applyMultiplier(copy, copy?.calculatedDiscountFactor, Boolean.FALSE, resultCategory, calculationRule, "", true, null, null, hasFractionalReplacement)
                                            discountApplied = true
                                        }

                                        /* if(!discountApplied && resultCategory.applyDiscountScenario && copy.result && copy.discountedResult
                                                 && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationRule?.calculationRuleId)
                                                 && hasFractionalReplacement && copy.calculatedDiscountFactor){
                                             discountApplied = true
                                         }*/

                                        Resource resource = resourceCache.getResource(copy, false)
                                        ResourceType resourceSubType = resourceCache.resourceTypeCache.getResourceSubType(resource)

                                        if (resource && !copy.calculationResource) {
                                            copy.calculationResource = resource
                                        } else if (!resource && !copy.calculationResource) {
                                            resource = resourceService.getResource(copy)
                                            resourceSubType = resourceCache.resourceTypeCache.getResourceSubType(resource)
                                        }

                                        List<Dataset> calculationResultDatasets = findCalculationResultDatasets(categoryDatasets, copyDatasetIds)

                                        /**
                                         * Compute monthly and quarterly data for operations tools (if licensed)
                                         */
                                        Map<String, Double> monthlyResults
                                        if (monthlyLicensed) {
                                            List<Map> monthlyQuantities = calculationResultDatasets?.collect{ it ->
                                                it.calculatedMonthlyQuantity }?.findAll{it}

                                            if (monthlyQuantities) {
                                                monthlyQuantities.each { Map<String, Double> quantitiesPerMonth ->
                                                    if (!monthlyResults) {
                                                        monthlyResults = new LinkedHashMap<String, Double>(quantitiesPerMonth)
                                                    } else {
                                                        mergeResultMaps(monthlyResults, quantitiesPerMonth)
                                                    }
                                                }
                                            }
                                        }
                                        Map<String, Double> quarterlyResults

                                        if (quarterlyLicensed) {
                                            List<Map> quarterlyQuantities = calculationResultDatasets?.collect{ it ->
                                                it.calculatedQuarterlyQuantity }?.findAll{it}

                                            if (quarterlyQuantities) {
                                                quarterlyQuantities.each { Map<String, Double> quantitiesPerQuarter ->
                                                    if (!quarterlyResults) {
                                                        quarterlyResults = new LinkedHashMap<String, Double>(quantitiesPerQuarter)
                                                    } else {
                                                        mergeResultMaps(quarterlyResults, quantitiesPerQuarter)
                                                    }
                                                }
                                            }
                                        }

                                        if (monthlyResults) {
                                            copy.monthlyResults = monthlyResults
                                        }

                                        if (quarterlyResults) {
                                            copy.quarterlyResults = quarterlyResults
                                        }

                                        // Apply scaling factor if specified by calculationRule
                                        if (calculationRule.scalingFactor) {
                                            applyScalingFactor(copy, resultCategory, calculationRule)
                                        }

                                        /**
                                         * Run resource filter specified in calculationRule.ruleFilterParameter or calculationRule.ruleFilterParameterList
                                         * Note: resource here is the one used for calculating result by resultCategory
                                         * if doesn't pass filter rule >>> skip this dataset for this calculationRule (impact)
                                         *
                                         * From ticket 15748, Arturs comment 0055389
                                         * How it should work for both ruleFilterParameter & ruleFilterParametersList:
                                         *   1. if there is resource connected check if the filter criteria is resource parameter
                                         *        if yes then use that as filter,
                                         *        if no then check if indicator has the addQ (in any query)
                                         *          if it has then use that as the filter,
                                         *          if not then pass the filter.
                                         *   2. if ruleFilterParametersList has "applyCondition" : "presentInDataset"
                                         *     >>> then not only the addQ has to be in any query of the indicator but it checks from the particular dataset if this addQ is present and only then applies the filter.
                                         */
                                        Boolean rulePassed = Boolean.FALSE

                                        if (ruleFilterParameter) {
                                            rulePassed = resolveRulePassed(copy, resource, resourceSubType, ruleFilterParameter,
                                                    ruleAcceptValues, projectCountry, resourceAttributes,
                                                    dataset, indicatorAdditionalQuestionIds, null, eolProcessCache)
                                        } else if (ruleFilterParametersList) {
                                            rulePassed = Boolean.TRUE
                                            ruleFilterParametersList.each { Map filterParamObj ->
                                                /*
                                                    Example of filterParamObj: {   "epdStandardVersionAddQ": ["a1", "a1a2", "all"], "applyCondition" : "presentInDataset" }
                                                    //TODO: what is this??? a map of whatever??
                                                 */
                                                String applyCondition = filterParamObj?.get('applyCondition')
                                                filterParamObj?.each { filterParamKey, acceptValueList ->
                                                    if (filterParamKey != 'applyCondition') {
                                                        Boolean rulePassedForThisParam = Boolean.FALSE
                                                        rulePassedForThisParam = resolveRulePassed(copy, resource, resourceSubType,
                                                                filterParamKey as String, acceptValueList as List<String>,
                                                                projectCountry, resourceAttributes, dataset, indicatorAdditionalQuestionIds,
                                                                applyCondition, eolProcessCache)
                                                        if (!rulePassedForThisParam) {
                                                            rulePassed = rulePassedForThisParam
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        // check if matching parameter is used and apply if needed
                                        if ((!ruleAcceptValues && !ruleFilterParametersList)|| rulePassed) {
                                            if (resource) {
                                                /**
                                                 * Use calculated mass of dataset as result if calculationRule specified
                                                 * Originated from ticket 12297
                                                 */
                                                if (calculationRule.useCalculatedMassAsResult) {
                                                    // todo: needed for monthly and quarterly results?
                                                    if (dataset.calculatedMass) {
                                                        // 15480 calculatedMass multiply with number of occurrencePeriods (for operations tools when resultCategory.calculateReplacementsOnly: true)
                                                        if (dataset.occurrencePeriods?.size() > 0 && resultCategory.calculateReplacementsOnly) {
                                                            copy.result = dataset.calculatedMass * dataset.occurrencePeriods?.size()
                                                        } else {
                                                            copy.result = dataset.calculatedMass
                                                        }
                                                        copy.calculationFormula = "${copy.calculationFormula},<b>Use calculated mass as result: ${copy.result.round(2)} (${resultCategoryId} - ${calculationRule.calculationRuleId})"
                                                    } else {
                                                        copy.result = 0D
                                                        copy.calculationFormula = "${copy.calculationFormula},<b>Use calculated mass as result: ${copy.result.round(2)}, unable to resolve mass (${resultCategoryId} - ${calculationRule.calculationRuleId})"
                                                    }
                                                }

                                                // TODO move logic for calculationRule.multiply to a method after release
                                                // TODO move logic for impactCategoryMultiplyExceptions to a method after release
                                                //annie - 15822 - biogenic carbon calculation change
                                                //replace the multiply list with the list from impactCategoryMultiplyExceptions map corresponding to the resultCategoryid if it matches with the map key
                                                List<String> multipliers = calculationRule.multiply
                                                if (calculationRule.impactCategoryMultiplyExceptions?.get(resultCategoryId)) {
                                                    multipliers = calculationRule.impactCategoryMultiplyExceptions.get(resultCategoryId)
                                                }

                                                // TODO move logic for useFixedCalculationBasis to a method after release. Double check w Annie the code order for calculationRule.impactCategoryMultiplyExceptions
                                                //== 15869 - Luke
                                                if (calculationRule?.useFixedCalculationBasis) {
                                                    applyMultiplier(copy, calculationRule?.useFixedCalculationBasis, Boolean.TRUE, resultCategory, calculationRule, Constants.USEFIXEDCALCULATIONBASIS, null, Boolean.TRUE)
                                                }
                                                multipliers?.each { String multiplier ->
                                                    // Should remove massConversionFactor mess when useCalculatedMassAsResult confirmed working correctly in all cases
                                                    if (multiplier != "quantity" && (!"massConversionFactor".equals(multiplier) || ("massConversionFactor".equals(multiplier) && !"kg".equals(unitConversionUtil.transformImperialUnitToEuropeanUnit(copy.calculatedUnit))))) {
                                                        Map<String, Object> factor = resolveCalculationRuleMultiplier(multiplier, resource, targetCountryResource, projectCountry,
                                                                resultCategory, calculationRuleDefaultData, entity, calculationResult, localCompensationMethodVersion,
                                                                localCompAreaResources, calculationRule, defaultEnergyProfileResourceId,
                                                                defaultEnergyProfileProfileId, rowByRowLocalCompsLicensed, debugLicenseFeature, resourceCache)
                                                        if (factor.get("compensationExplanation")) {
                                                            copy.calculationFormula = "${copy.calculationFormula},${factor.get("compensationExplanation")}"
                                                        }
                                                        applyMultiplier(copy, (Double) factor.get("compensatedImpactFactor"), Boolean.TRUE, resultCategory, calculationRule, multiplier)

                                                    }
                                                }

                                                // TODO move logic for resultCategory.optionalAdditionalQuestionMultiplier to a method after release
                                                String resultCategoryOptionalAdditionalQuestionMultiplier = resultCategory.optionalAdditionalQuestionMultiplier

                                                if (resultCategoryOptionalAdditionalQuestionMultiplier) {
                                                    String answerForOptionalAdditionalQuestionMultiplier = copy.datasetAdditionalQuestionAnswers?.get(resultCategoryOptionalAdditionalQuestionMultiplier)?.toString()
                                                    Double multiplier = answerForOptionalAdditionalQuestionMultiplier ?
                                                            DomainObjectUtil.isNumericValue(answerForOptionalAdditionalQuestionMultiplier) ?
                                                                    DomainObjectUtil.convertStringToDouble(answerForOptionalAdditionalQuestionMultiplier) : null : null
                                                    applyMultiplier(copy, multiplier, resultCategory.setToZeroIfNoAdditionalQuestionMultiplierFound, resultCategory, calculationRule, resultCategoryOptionalAdditionalQuestionMultiplier)
                                                }

                                                // TODO move logic for resultCategory.additionalQuestionMultipliers to a method after release
                                                List<String> additionalQuestionMultipliers

                                                if (resultCategory.additionalQuestionMultipliers) {
                                                    additionalQuestionMultipliers = new ArrayList<String>(resultCategory.additionalQuestionMultipliers)

                                                    if (calculationRule.additionalQuestionMultipliers) {
                                                        additionalQuestionMultipliers.addAll(calculationRule.additionalQuestionMultipliers)
                                                    }
                                                } else if (calculationRule.additionalQuestionMultipliers) {
                                                    additionalQuestionMultipliers = new ArrayList<String>(calculationRule.additionalQuestionMultipliers)
                                                }


                                                if (additionalQuestionMultipliers) {
                                                    additionalQuestionMultipliers.each { String additionalQuestionId ->
                                                        String answerForAdditionalQuestionMultiplier = copy.datasetAdditionalQuestionAnswers?.get(additionalQuestionId)?.toString()
                                                        Double multiplier = answerForAdditionalQuestionMultiplier ?
                                                                DomainObjectUtil.isNumericValue(answerForAdditionalQuestionMultiplier) ?
                                                                        DomainObjectUtil.convertStringToDouble(answerForAdditionalQuestionMultiplier) : null : null
                                                        applyMultiplier(copy, multiplier, Boolean.TRUE, resultCategory, calculationRule, additionalQuestionId)
                                                    }
                                                }

                                                //SW-1526
                                                if (resultCategory.additionalQuestionMultipliersWithConditions) {
                                                    for (AdditionalQuestionMultipliersWithConditions conditionalMultiplier in resultCategory.additionalQuestionMultipliersWithConditions) {
                                                        if (conditionalMultiplier.additionalQuestionMultipliers) {
                                                            if (!dataset.parentConstructionDataset) {
                                                                datasetService.setParentConstructionDataset(dataset, entity.datasets)
                                                            }
                                                            Map<String, Object> parentDatasetAdditionalQuestionAnswers = dataset.parentConstructionDataset?.additionalQuestionAnswers
                                                            Object val = parentDatasetAdditionalQuestionAnswers?.get(conditionalMultiplier.additionalQuestionMultipliers)
                                                            ValueReference condition = conditionalMultiplier.conditions
                                                            if (val != null && DomainObjectUtil.isNumericValue(val.toString()) && condition) {
                                                                if (parentDatasetAdditionalQuestionAnswers.get(condition.additionalQuestionId) == condition.answerId) {
                                                                    Double multiplier = DomainObjectUtil.convertStringToDouble(val.toString())
                                                                    applyMultiplier(copy, multiplier, Boolean.TRUE, resultCategory, calculationRule, condition.additionalQuestionId)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                // TODO move logic for resultCategory.optionalAdditionalQuestionDivider to a method after release
                                                String optionalAdditionalQuestionDivider = resultCategory.optionalAdditionalQuestionDivider

                                                if (optionalAdditionalQuestionDivider) {
                                                    String answerForOptionalAdditionalQuestionDivider = copy.datasetAdditionalQuestionAnswers?.get(optionalAdditionalQuestionDivider)?.toString()

                                                    if (constructionConstants.DEFAULT.equals(answerForOptionalAdditionalQuestionDivider)) {
                                                        answerForOptionalAdditionalQuestionDivider = DomainObjectUtil.callGetterByAttributeName(optionalAdditionalQuestionDivider, resource)?.toString()
                                                    }

                                                    Double divider = answerForOptionalAdditionalQuestionDivider ?
                                                            DomainObjectUtil.isNumericValue(answerForOptionalAdditionalQuestionDivider) ?
                                                                    DomainObjectUtil.convertStringToDouble(answerForOptionalAdditionalQuestionDivider) : null : null
                                                    applyDivider(copy, divider, resultCategory.setToZeroIfNoAdditionalQuestionMultiplierFound, resultCategory, calculationRule, optionalAdditionalQuestionDivider)
                                                }

                                                // TODO move logic for resultCategory.applyMaintenanceReplacementMultiplier || resultCategory.applyConstructionReplacementMultiplier to a method after release
                                                if (resultCategory.applyMaintenanceReplacementMultiplier || (resultCategory.applyConstructionReplacementMultiplier && preparedDenominator.assessmentPeriod)) {
                                                    Dataset d = calculationResultService.getDatasets(calculationResult, entity)[0]

                                                    if (d.parentConstructionId && d.uniqueConstructionIdentifier) {
                                                        Dataset parent = allDatasets?.find({
                                                            !it.parentConstructionId && d.uniqueConstructionIdentifier.equals(it.uniqueConstructionIdentifier)
                                                        })

                                                        if (parent) {
                                                            Double parentServiceLife = getServiceLife(parent, resource, resourceSubType, entity, indicator)
                                                            Double serviceLife = getServiceLife(d, resource, resourceSubType, entity, indicator)

                                                            Double multiplier

                                                            if (resultCategory.applyMaintenanceReplacementMultiplier && parentServiceLife && serviceLife) {
                                                                multiplier = ((parentServiceLife / serviceLife) - 1)

                                                                if (multiplier < 1) {
                                                                    multiplier = 1
                                                                }
                                                            }

                                                            if (resultCategory.applyConstructionReplacementMultiplier && preparedDenominator.assessmentPeriod && parentServiceLife) {
                                                                if (multiplier != null) {
                                                                    multiplier = ((preparedDenominator.assessmentPeriod / parentServiceLife) * multiplier)
                                                                } else {
                                                                    multiplier = (preparedDenominator.assessmentPeriod / parentServiceLife)
                                                                }
                                                            }

                                                            if (multiplier != null && multiplier > 1) {
                                                                applyMultiplier(copy, multiplier, Boolean.FALSE, resultCategory, calculationRule, "maintenanceReplacement")
                                                            }
                                                        } else {
                                                            loggerUtil.error(log, "applyMaintenanceReplacementMultiplier failed! No parent construction found for component dataset.")
                                                            flashService.setErrorAlert("applyMaintenanceReplacementMultiplier failed! No parent construction found for component dataset.", true)
                                                        }
                                                    }
                                                }

                                                // TODO move logic for resultCategory.applyFractionalReplacementMultiplier to a method after release
                                                if (resultCategory.applyFractionalReplacementMultiplier && preparedDenominator.assessmentPeriod && !discountApplied) {
                                                    Dataset d = calculationResultService.getDatasets(calculationResult, entity)[0]
                                                    Double serviceLife = getServiceLife(d, resource, resourceSubType, entity, indicator)

                                                    //Ticket 16523 - edge case check for permanent serviceLife but resource serviceLife is higher than -1
                                                    if ("permanent".equals(d?.additionalQuestionAnswers?.get("serviceLife")) && resource?.serviceLife > 0D) {
                                                        serviceLife = -1
                                                    }

                                                    if (serviceLife) {
                                                        Double fractionalMultiplier = preparedDenominator.assessmentPeriod / serviceLife

                                                        if (fractionalMultiplier > 1) {
                                                            if (resultCategory.setMinimumReplacementToOne && fractionalMultiplier < 2) {
                                                                fractionalMultiplier = 2
                                                            }
                                                            applyMultiplier(copy, fractionalMultiplier, Boolean.FALSE, resultCategory, calculationRule, "fractionalReplacement")
                                                        }
                                                    }
                                                }

                                                // annie - moved the blocks to new methods
                                                // REL-441 - remove the check for assessmentPeriod, so in case user doesn't set any assessment period, then it should be 0 and we still evaluate the calculateFractionalReplacementsOnly
                                                if (resultCategory?.calculateFractionalReplacementsOnly && !discountApplied) {
                                                    applyFractionalReplacementOnlyMultiplier(resultCategory, calculationRule, preparedDenominator, calculationResult,
                                                            copy, entity, indicator, resource, resourceSubType, hasFractionalReplacement)
                                                }
                                                /* if(!discountApplied && resultCategory?.applyDiscountScenario && (resultCategory?.applyFractionalReplacementMultiplier
                                                         || resultCategory?.applyFractionalReplacementOnlyMultiplier || resultCategory?.calculateFractionalReplacementsOnly)
                                                         && preparedDenominator?.assessmentPeriod && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationRule?.calculationRuleId)) {
                                                     applyDiscountForFractionalReplacements(resultCategory, calculationRule, preparedDenominator,
                                                             copy, discountApplied, indicator , hasFractionalReplacement)
                                                 }*/

                                                // TODO move logic for calculationRule.optionalMultiplier to a method after release
                                                ValueReference optionalMultiplier = calculationRule.optionalMultiplier

                                                if (optionalMultiplier) {
                                                    Double multiplier = valueReferenceService.getDoubleValueForEntity(optionalMultiplier, entity, null, null, resourceCache)
                                                    applyMultiplier(copy, multiplier, Boolean.FALSE, resultCategory, calculationRule, optionalMultiplier.resourceParameter)
                                                }

                                                // TODO move logic for calculationRule.optionalAdditionalQuestionMultiplier to a method after release
                                                String optionalAdditionalQuestionMultiplier = calculationRule.optionalAdditionalQuestionMultiplier

                                                if (optionalAdditionalQuestionMultiplier) {
                                                    String answerForOptionalAdditionalQuestionMultiplier = copy.datasetAdditionalQuestionAnswers?.get(optionalAdditionalQuestionMultiplier)?.toString()
                                                    Double multiplier = answerForOptionalAdditionalQuestionMultiplier ?
                                                            DomainObjectUtil.isNumericValue(answerForOptionalAdditionalQuestionMultiplier) ?
                                                                    DomainObjectUtil.convertStringToDouble(answerForOptionalAdditionalQuestionMultiplier) : null : null
                                                    applyMultiplier(copy, multiplier, calculationRule.setToZeroIfNoAdditionalQuestionMultiplierFound, resultCategory, calculationRule, optionalAdditionalQuestionMultiplier)
                                                }

                                                // Environmental data quality penalty implementation. Now skipping calculationRule mass. Also skipping such items which have no mass.
                                                if (requiredEnvironmentDataSourceStandards && performancePenaltyMultiplier && copy.result && calculationRule.calculationRule != "mass") {
                                                    String resourceEnvStandard = resource.environmentDataSourceStandard
                                                    Double resourceMass = resource.massConversionFactor

                                                    if (resourceMass && resourceMass > 0 && !requiredEnvironmentDataSourceStandards?.contains(resourceEnvStandard)) {
                                                        applyMultiplier(copy, performancePenaltyMultiplier, Boolean.TRUE, resultCategory, calculationRule, "performancePenaltyMultiplier")
                                                        loggerUtil.debug(log, "performancePenaltyMultiplier " + performancePenaltyMultiplier + " applied to resource " + resource.nameFI + " with mass " + resourceMass + " with data standard " + resourceEnvStandard + " for rule " + calculationRule.calculationRule)
                                                    }
                                                }

                                                // TODO move logic for calculationRule.multiplyByImpactBasis to a method after release
                                                if (calculationRule.multiplyByImpactBasis) {
                                                    def impactBasis = resource.impactBasis

                                                    // Multiplying with massConversionFactor no longer makes sense in new lifecycle model since its handled in massCalculation
                                                    if (impactBasis && !["unit", "massConversionFactor"].contains(impactBasis)) {
                                                        try {
                                                            applyMultiplier(copy, getAnyParameterFromResource(impactBasis, resource), Boolean.TRUE, resultCategory, calculationRule, impactBasis)
                                                        } catch (Exception e) {
                                                            loggerUtil.error(log, "Unknown impactBasis: " + impactBasis, e)
                                                            flashService.setErrorAlert("Unknown impactBasis ${impactBasis}: ${e.getMessage()}", true)
                                                        }
                                                    }
                                                }

                                                // TODO move logic for calculationRule.multiplyWithOrganizationCarbonCost to a method after release

                                                if (calculationRule.multiplyWithOrganizationCarbonCost) {
                                                    Double multiplier = accountForEntity?.carbonCost ?: 0
                                                    applyMultiplier(copy, multiplier, Boolean.TRUE, resultCategory, calculationRule, "multiplyWithOrganizationCarbonCost")
                                                }

                                            } else if (resultCategory.process) {
                                                // Nullify result for categories that have processing but calculationResource was not found for some reason
                                                if (copy.calculationResourceId && !com.bionova.optimi.construction.Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER.equals(copy.calculationResourceId)) {
                                                    loggerUtil.error(log, "Calculation error: No resource found for: ${copy.calculationResourceId} / ${copy.calculationProfileId}, and resultCategory ${resultCategoryId} with process: ${resultCategory.process}")
                                                    flashService.setErrorAlert("Calculation error: No resource found for: ${copy.calculationResourceId} / ${copy.calculationProfileId}, and resultCategory ${resultCategoryId} with process: ${resultCategory.process}", true)
                                                }

                                                copy.result = null
                                                copy.monthlyResults = null
                                                copy.quarterlyResults = null
                                            }
                                        } else {
                                            // we nullify results if filter is set and data doesn't match filter criteria
                                            copy.result = null
                                            copy.monthlyResults = null
                                            copy.quarterlyResults = null
                                        }
                                        if (copy.result != null) {
                                            // TODO move logic for preparedDenominator.overallDenominator to a method after release
                                            if (preparedDenominator.overallDenominator && !byPassDenominator) {
                                                if (preparedDenominator.overallDenominator != 1 && !calculationRule.excludeDenominator) {
                                                    applyDivider(copy, preparedDenominator.overallDenominator, Boolean.FALSE, resultCategory, calculationRule, "overallDenominator")
                                                }
                                            } else if (preparedDenominator.overallDenominator == null) {
                                                // For failIfNotCalculable overallDenominator is always null
                                                copy.result = null
                                                copy.monthlyResults = null
                                                copy.quarterlyResults = null
                                            }

                                            if (copy.result != null) {
                                                // TODO move logic for resultCategory.setToZeroIfValueIsNegative to a method after release
                                                if (copy.result < 0 && (resultCategory.setToZeroIfValueIsNegative||calculationRule.setToZeroIfValueIsNegative)) {
                                                    applyMultiplier(copy, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_NEGATIVE)
                                                }
                                                if (copy.result > 0 && (resultCategory.setToZeroIfValueIsPositive||calculationRule.setToZeroIfValueIsPositive)) {
                                                    applyMultiplier(copy, 0, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_TO_ZERO_IF_VALUE_IS_POSITIVE)
                                                }

                                                if (resultCategory.setMinimumValue != null && copy.result < resultCategory.setMinimumValue && !resultCategory.calculateForUniqueAnswersOnly) {
                                                    applyMultiplier(copy, resultCategory.setMinimumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MINIMUM_VALUE, null, Boolean.TRUE)
                                                }
                                                if (resultCategory.setMaximumValue != null && copy.result > resultCategory.setMaximumValue && !resultCategory.calculateForUniqueAnswersOnly) {
                                                    applyMultiplier(copy, resultCategory.setMaximumValue, Boolean.TRUE, resultCategory, calculationRule, Constants.SET_MAXIMUM_VALUE, null, Boolean.TRUE)
                                                }

                                                copy.calculationRuleId = calculationRule.calculationRuleId
                                                if (copy.combineId) {
                                                    needsToBeCombined.add(copy)
                                                } else {
                                                    resultsForRule.add(copy)
                                                }
                                            }
                                        }
                                    }

                                    if (needsToBeCombined) {
                                        CalculationResult combinedResult = new CalculationResult()
                                        combinedResult.indicatorId = indicator.indicatorId
                                        combinedResult.resultCategoryId = resultCategoryId
                                        combinedResult.calculationRuleId = calculationRule.calculationRuleId
                                        combinedResult.calculationResourceId = datasetResource.resourceId
                                        combinedResult.calculationProfileId = datasetResource.profileId
                                        combinedResult.originalResourceId = datasetResource.resourceId
                                        combinedResult.originalProfileId = datasetResource.profileId
                                        combinedResult.datasetIds = [datasetManualId]
                                        combinedResult.unit = dataset.userGivenUnit
                                        combinedResult.calculatedUnit = dataset.calculatedUnit
                                        combinedResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                                        combinedResult.result = needsToBeCombined.collect({it.result}).sum()

                                        String newFormula = ""
                                        needsToBeCombined?.each {
                                            if (it?.calculationFormula) {
                                                newFormula = newFormula + it.calculationFormula.substring(it.calculationFormula.lastIndexOf("<b>"), it.calculationFormula.length()) + ","
                                            }
                                        }
                                        combinedResult.calculationFormula = newFormula
                                        resultsForRule.add(combinedResult)
                                    }
                                } else {
                                    for (CalculationResult calculationResult in scenarioResults) {
                                        CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                                        //annie
                                        if (resultCategory.applyDiscountScenario && copy.discountedResult && copy.result && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationRule?.calculationRuleId)) {
                                            applyMultiplier(copy, copy?.calculatedDiscountFactor, Boolean.FALSE, resultCategory, calculationRule, "", true)
                                        }

                                        copy.calculationRuleId = calculationRule.calculationRuleId
                                        resultsForRule.add(copy)
                                    }
                                }

                                if (!resultsForRule.isEmpty()) {
                                    resultsByScenarios.addAll(resultsForRule)
                                }
                            }
                        }
                    }
                }
            }
        }
        log.debug("Calculate Calculation Rule Impact finished in ${System.currentTimeMillis() - start}")
        return resultsByScenarios
    }

    private void preloadCalculationResources(List<CalculationResult> scenarioResults, ResourceCache cache,
                                                              Boolean isOriginalResource) {
        Set<String> calculationResourceIds = []

        List<CalculationResult> filteredResults = scenarioResults.findAll {
            // set flag fetchIfMissing in getResource() to false since we are only checking if the resource exists in cache, if not we will fetch all together.
            !cache.getResource(it, isOriginalResource, false)
        }

        if(!filteredResults) {
            return
        }

        filteredResults.each { it ->
            if (isOriginalResource){
                calculationResourceIds.add(it.getOriginalResourceId())
            } else {
                calculationResourceIds.add(it.getCalculationResourceId())
            }
        }

        List<Resource> resources = optimiResourceService.getResources(calculationResourceIds as List, null, Boolean.TRUE)
        log.info("Cached ${resources?.size()}/${calculationResourceIds?.size()} calculation resources.")

        if(resources) {
            cache.putResources(resources)
        }
    }

    @CompileStatic
    private static List<Dataset> findCalculationResultDatasets(List<Dataset> categoryDatasets, Set<String> resultDatasetIds) {
        List<Dataset> calculationResultDatasets = new ArrayList<>()

        for (Dataset dataset : categoryDatasets) {
            if (resultDatasetIds.contains(dataset.manualId)) {
                calculationResultDatasets.add(dataset)
            }
        }

        return calculationResultDatasets
    }

    /** Applies multiplier for calculateFractionalReplacementsOnly condition
     * fractionalMultiplier = preparedDenominator.assessmentPeriod / serviceLife
     *if fractionalMultiplier >= 1 then  fractionalMultiplier = fractionalMultiplier - 1
     * moved the code block from above to this new method and added calculateFractionalReplacementsOnly in OR condition
     * @param resultCategory
     * @param calculationRule
     * @param preparedDenominator
     * @param calculationResult
     * @param copy
     * @param entity
     * @param indicator
     * @param resource
     */
    private void applyFractionalReplacementOnlyMultiplier(ResultCategory resultCategory, CalculationRule calculationRule, PreparedDenominator preparedDenominator, CalculationResult calculationResult,
                                                          CalculationResult copy, Entity entity, Indicator indicator,
                                                          Resource resource, ResourceType resourceSubType, Boolean hasFractionalReplacement) {
        if (!resultCategory?.calculateFractionalReplacementsOnly) {
            return
        }
        //annie - REL2108-104
        String processName = Constants.CALCULATE_FRACTIONAL_REPLACEMENTS_ONLY
        // REL-441 - when user doesn't set any assessment period, it should be 0 and we still evaluate the calculateFractionalReplacementsOnly (set result to 0)
        Double assessmentPeriod = preparedDenominator?.assessmentPeriod ?: 0
        boolean setToZero = false

        Dataset d = calculationResultService.getDatasets(calculationResult, entity)[0]
        Double serviceLife = getServiceLife(d, resource, resourceSubType, entity, indicator)
        Integer noOfOccurence

        if (serviceLife) {
            Double fractionalMultiplier = assessmentPeriod / serviceLife
            noOfOccurence = fractionalMultiplier?.intValue()
            if (fractionalMultiplier >= 1) {
                Double modValue = assessmentPeriod % serviceLife
                fractionalMultiplier = fractionalMultiplier - 1
                if (modValue == 0) {
                    noOfOccurence = noOfOccurence - 1
                }
                applyMultiplier(copy, fractionalMultiplier, Boolean.FALSE, resultCategory, calculationRule, processName, false, null, noOfOccurence, hasFractionalReplacement)
            } else {
                setToZero = true
            }
        } else {
            setToZero = true
        }

        if (setToZero) {
            applyMultiplier(copy, 0, Boolean.FALSE, resultCategory, calculationRule, processName)
        }
    }
    /** applies discount for applyFractionalReplacementMultiplier OR calculateFractionalReplacementsOnly
     * moved the existing code block on top to this new method
     * @param resultCategory
     * @param calculationRule
     * @param preparedDenominator
     * @param copy
     * @param discountApplied
     */
    private void applyDiscountForFractionalReplacements(ResultCategory resultCategory , CalculationRule calculationRule, PreparedDenominator preparedDenominator ,
                                                        CalculationResult copy , Boolean discountApplied , Indicator indicator , Boolean hasFractionalReplacement){
        //PPAA - If applyDiscountFactor is USED AND IF applyFractionalReplacementMultiplier AND
        // preparedDenominator.assessmentPeriod AND if discounting is not excluded for impactCategory)
        // put error level log if this loop is ever entered into when a resultCategory."process" has been used or if resultCategory.calculateReplacementsOnly
        // then do a loop
        // in this loop calculate a discountedRecurrences thing (and if you feel like a stretch challenge insert occurrencePeriods)
        // insert the value
        // actually, rather use "calculatedDiscountFactor" always that's (virtually always) between 0 and 1
        String processName = ""
        if (resultCategory?.calculateFractionalReplacementsOnly){
            processName = Constants.CALCULATE_FRACTIONAL_REPLACEMENTS_ONLY
        }else if (resultCategory?.applyFractionalReplacementMultiplier){
            processName = Constants.APPLY_FRACTIONAL_REPLACEMENT_MULTIPLIER
        }
        if(!discountApplied && resultCategory?.applyDiscountScenario && hasFractionalReplacement
                && preparedDenominator?.assessmentPeriod && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationRule?.calculationRuleId)){
            loggerUtil.info(log , "APPLYING FRACTIONAL REPLACEMENT CONDITION")
            if(resultCategory?.process || resultCategory?.calculateReplacementsOnly){
                loggerUtil.error(log , "Code entered somewhere it shouldnt have entered , (resultCategory.process || resultCategory.calculateReplacementsOnly) is true")
                flashService.setErrorAlert("Code entered somewhere it shouldnt have entered , (resultCategory.process || resultCategory.calculateReplacementsOnly) is true", true)
            }
            if(copy?.discountedResult && copy?.result) {
                applyMultiplier(copy, copy?.calculatedDiscountFactor, Boolean.FALSE, resultCategory, calculationRule, processName, true)
                loggerUtil.info(log , "DISCOUNT HAS BEEN APPLIED FOR ${resultCategory.resultCategoryId} , ${calculationRule.calculationRuleId}")
                loggerUtil.info(log , "DISCOUNT FACTOR IS ${copy?.calculatedDiscountFactor}")
                discountApplied = true
            }

        }
    }
    /**
     * Gets assessment period for the tool, either its fixes for the tool or user can give it as a query question
     * answer.
     *
     * @param entity
     * @param indicator
     * @return
     */

    private Double getAssessmentPeriod(Entity entity, Indicator indicator, ResourceCache resourceCache) {
        Double assessmentPeriod
        ValueReference assessmentPeriodValueReference = indicator.assessmentPeriodValueReference

        if (assessmentPeriodValueReference) {
            assessmentPeriod = valueReferenceService.getDoubleValueForEntity(assessmentPeriodValueReference, entity, null, null, resourceCache)
        } else if (indicator.assessmentPeriodFixed != null) {
            assessmentPeriod = indicator.assessmentPeriodFixed
        }
        return assessmentPeriod != null ? assessmentPeriod : 0
    }

    /**
     * Gets userSelectedDiscountScenarioId for the tool, user can give it as a query question
     * answer.
     *
     * @param entity
     * @param indicator
     * @return userSelectedDiscountScenarioId
     */
    //annie
    private String getUserSelectedDiscountScenarioId(Entity entity, ValueReference userSelectedScenario, ResourceCache resourceCache) {
        String userSelectedDiscountScenarioId

        if (userSelectedScenario && entity) {
            userSelectedDiscountScenarioId = valueReferenceService.getValueForEntity(userSelectedScenario, entity, Boolean.FALSE, Boolean.TRUE, resourceCache)
        }
        return userSelectedDiscountScenarioId
    }



    /**
     * When designs calculation is triggered the old results will be removed completely and replaced with the new
     * results from calculation
     * @see #setEntityResults
     *
     * @param entity
     * @param indicatorId
     * @param displayRuleId
     * @return
     */

    private removeExistingResultsByIndicator(Entity entity, String indicatorId, String displayRuleId = null) {
        if (entity && indicatorId) {
           // BasicDBObject filter = new BasicDBObject("entityId", entity.id.toString()).append("indicatorId", indicatorId)
            long now2 = System.currentTimeMillis()
            // List<ObjectId> ids = CalculationResult.collection.distinct("_id", filter, ObjectId.class)?.toList()
            List<ObjectId> ids = CalculationResult.collection.find(entityId:entity.id.toString(),indicatorId:indicatorId , [_id:1])?.collect({it._id})?.toList()
            //loggerUtil.info(log, "removeExistingResultsByIndicator: time to fetch the ids : ${System.currentTimeMillis() - now2} ms")
            try {
                if (ids && entity?.calculationResults) {
                    List<CalculationResult> toRemove = []

                    entity.calculationResults.each { CalculationResult c ->
                        if (c && ids.contains(c.id)) {
                            toRemove.add(c)
                        }
                    }

                    toRemove.each { CalculationResult c ->
                        entity.calculationResults.remove(c)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error in removeExistingResultsByIndicator:", e)
                flashService.setErrorAlert("Error in removeExistingResultsByIndicator: ${e.getMessage()}", true)
            }
            now2 = System.currentTimeMillis()
            CalculationResult.collection.remove([entityId: entity.id.toString(), indicatorId: indicatorId])
            //loggerUtil.info(log, "removeExistingResultsByIndicator: CalculationResult.collection.remove([entityId: entity.id.toString(), indicatorId: indicatorId]) : ${System.currentTimeMillis() - now2} ms")

            if (displayRuleId && entity.calculationTotalResults) {
                Double prevTotal = entity.calculationTotalResults.find { indicatorId == it.indicatorId }?.totalByCalculationRule?.get(displayRuleId)
                calculationMessageService.persistPreviousTotal(entity, indicatorId, prevTotal)
            }
            entity.calculationTotalResults?.removeAll({ indicatorId.equals(it.indicatorId) })
        }
    }

    /**
     * Collects all possible rows that can be calculated for this tool, checks what queries this tool has etc.
     * The design/entity can have datasets from multiple tools so its possible that those materials cant be calculated
     * for the current tool/indicator in calculation. Filters out those datasets.
     *
     * @param entity
     * @param parentEntity
     * @param datasets
     * @param indicator
     * @param resultCategories
     * @param indicatorAdditionalQuestionIds
     * @return
     */
    private List<Dataset> collectDataForCalculation(Entity entity, Entity parentEntity, List<Dataset> datasets, Indicator indicator,
                                                    List<ResultCategory> resultCategories,
                                                    Set<String> indicatorAdditionalQuestionIds = null, ResourceCache resourceCache) {
        List<Dataset> collectedDatasets = []
        indicatorAdditionalQuestionIds = indicatorAdditionalQuestionIds ?:
                questionService.getAdditionalQuestionIdsForIndicator(indicator,parentEntity)

        if (datasets) {
            Boolean useResourcesOnly = !indicator.evaluateNonResourceAnswers

            resultCategories?.findAll({ it && !it.virtual })?.each { ResultCategory resultCategory ->
                List<Dataset> foundDatasets = []
                Dataset defaultDataset = resultCategoryService.getDefaultDataset(resultCategory, entity)

                if (!defaultDataset) {
                    defaultDataset = resultCategoryService.getDefaultDataset(resultCategory, parentEntity)
                }
                String resourceFilterParameter = resultCategory.resourceFilterParameter
                List<String> acceptResourceValues = resultCategory.acceptResourceValues
                List<ResourceFilterCriteria> resourceFilterCriteriaList = resultCategory.resourceFilterCriteriaList
                Map<String, Object> data = resultCategory.data
                Map<String, Set> limitSectionDataToQuestionIds = resultCategory.limitSectionDataToQuestionIds
                Map<String, List<String>> hiddenQuestionsPerSection = indicator.hideQuestions

                List<String> stages = resultCategory.applyStages


                if (stages && !Resource.allowedStages.containsAll(stages)) {
                    throw new IllegalArgumentException("Not allowed stages ${stages} in resultCategory ${resultCategory?.resultCategory}")
                }
                boolean conditionFilled = true

                if (resultCategory.applyConditions) {
                    boolean dataFound = isDataFoundByApplyConditions(resultCategory.applyConditions, datasets)

                    if (resultCategory.applyConditions?.dataPresent && !dataFound) {
                        conditionFilled = false
                    } else if (!resultCategory.applyConditions.dataPresent && dataFound) {
                        conditionFilled = false
                    }
                }

                if (conditionFilled) {
                    data?.each { String dataQueryId, def sectionIds ->

                        if (sectionIds instanceof String) {
                            sectionIds = sectionIds ? [sectionIds] : []
                        }

                        foundDatasets.addAll(filterDatasetsByQuerySectionsAndQuestions(datasets, dataQueryId, sectionIds as Set, limitSectionDataToQuestionIds, hiddenQuestionsPerSection))

                        if (useResourcesOnly) {
                            foundDatasets = foundDatasets?.findAll({ Dataset d -> d.resourceId })
                        }

                        if (foundDatasets) {
                            if (stages) {
                                List<Dataset> stageHandledDatasets = []
                                // stage and stage + process hybrid handling (3241: It turns out we can't handle DGNB EOL processes like planned - need hybrid for stage + process)
                                Set<String> processNames = resultCategory.process?.keySet()
                                boolean useBenefitProcessingFailOver = false
                                boolean useWasteProcessingFailOver = false
                                boolean useWasteAndReplacementFailOver = false

                                if (processNames) {
                                    if (processNames.contains(Constants.QueryCalculationProcess.BENEFIT.toString())) {
                                        useBenefitProcessingFailOver = true
                                    }

                                    if (processNames.contains(Constants.QueryCalculationProcess.WASTE.toString()) && !processNames.contains(Constants.QueryCalculationProcess.RECURRING_REPLACEMENT.toString() && !resultCategory.calculateReplacementsOnly)) {
                                        useWasteProcessingFailOver = true
                                    }

                                    if (processNames.contains(Constants.QueryCalculationProcess.WASTE.toString()) && (processNames.contains(Constants.QueryCalculationProcess.RECURRING_REPLACEMENT.toString()) || resultCategory.calculateReplacementsOnly)) {
                                        useWasteAndReplacementFailOver = true
                                    }
                                }

                                if (useBenefitProcessingFailOver || useWasteProcessingFailOver || useWasteAndReplacementFailOver) {
                                    stages.remove("A1-A3")

                                    if (useBenefitProcessingFailOver) {
                                        List<Dataset> tempForBenefit = foundDatasets.findAll({ Dataset d ->
                                            Resource resource = resourceCache.getResource(d)
                                            // Check 'environmentalBenefitsResourceId' field on both Resource and ResourceType levels.
                                            String environmentalBenefitsResourceId = resource?.environmentalBenefitsResourceId ?: resourceCache.getResourceTypeCache().getResourceSubType(resource)?.environmentalBenefitsResourceId

                                            !d.resourceId || (resource?.impacts && CollectionUtils.containsAny(resource?.impacts?.keySet(), stages)) || environmentalBenefitsResourceId
                                        })

                                        if (tempForBenefit && !tempForBenefit.isEmpty()) {
                                            loggerUtil.debug(log, "ResultCategory ${resultCategory.resultCategory}: Collected ${tempForBenefit.size()} datasets in benefitProcessingFailOver")
                                            stageHandledDatasets.addAll(tempForBenefit)
                                        }
                                    }

                                    if (useWasteProcessingFailOver) {
                                        List<Dataset> tempForWaste = foundDatasets.findAll({ Dataset d ->
                                            !d.resourceId ||
                                                    (resourceCache.getResource(d)?.impacts &&
                                                            resourceCache.getResource(d)?.impacts?.keySet()?.containsAll(stages)) ||
                                                    resourceCache.getResource(d)?.wasteResourceId
                                        })

                                        if (tempForWaste && !tempForWaste.isEmpty()) {
                                            loggerUtil.debug(log, "ResultCategory ${resultCategory.resultCategory}: Collected ${tempForWaste.size()} datasets in wasteProcessingFailOver")
                                            stageHandledDatasets.addAll(tempForWaste)
                                        }
                                    }

                                    if (useWasteAndReplacementFailOver) {
                                        List<Dataset> tempForWasteAndReplacement = foundDatasets.findAll({ Dataset d -> d.resourceId })

                                        if (tempForWasteAndReplacement && !tempForWasteAndReplacement.isEmpty()) {
                                            loggerUtil.debug(log, "ResultCategory ${resultCategory.resultCategory}: Collected ${tempForWasteAndReplacement.size()} datasets in wasteAndReplacementProcessingFailOver")
                                            stageHandledDatasets.addAll(tempForWasteAndReplacement)
                                        }
                                    }
                                } else {
                                    stageHandledDatasets = foundDatasets.findAll({ Dataset d -> !d.resourceId ||
                                            (resourceCache.getResource(d)?.impacts &&
                                                    CollectionUtils.containsAny(resourceCache.getResource(d)?.impacts?.keySet(), stages)) })
                                }

                                if (stageHandledDatasets && !stageHandledDatasets.isEmpty()) {
                                    loggerUtil.debug(log, "ResultCategory ${resultCategory.resultCategory}: Collected totally ${stageHandledDatasets.size()} unique datasets in stage handling")
                                    foundDatasets = new ArrayList<Dataset>(stageHandledDatasets)
                                }
                            }
                        }
                    }

                    if (defaultDataset) {
                        // We have to set new manualId so that defaultDataset is not mixed between categories
                        defaultDataset.defaultDatasetOriginalManualId = defaultDataset.manualId
                        defaultDataset.manualId = new ObjectId().toString()
                        List<Dataset> datasetsWithDefaultQuantityAdded = []
                        Set<String> datasetIdsToRemove = []

                        foundDatasets?.findAll({ it.resourceId && it.quantity == null })?.each {
                            datasetIdsToRemove.add(it.manualId)
                            Dataset copy = datasetService.createCopy(it)
                            copy.calculatedQuantity = defaultDataset.quantity
                            // copy.manualId = new ObjectId().toString()
                            datasetsWithDefaultQuantityAdded.add(copy)
                        }

                        if (!datasetIdsToRemove.isEmpty()) {
                            foundDatasets.removeAll({ datasetIdsToRemove.contains(it.manualId) })
                        }

                        if (!datasetsWithDefaultQuantityAdded.isEmpty()) {
                            foundDatasets.addAll(datasetsWithDefaultQuantityAdded)
                        }

                        if (!foundDatasets) {
                            foundDatasets = [defaultDataset]
                        }
                    }

                    if (foundDatasets) {
                        // found datasets list could have new resources so need to add those
                        resourceCache.addExtraResources(foundDatasets)
                    }

                    foundDatasets?.each { Dataset dataset ->
                        boolean filteringPassed = false
                        Resource resource = resourceCache.getResource(dataset)

                        if (resourceFilterParameter) {
                            filteringPassed = optimiResourceService.fitlterResourceAndDatasetByResourceFilterParameter(resource, dataset, [(resourceFilterParameter): acceptResourceValues], filteringPassed)
                        } else if (resourceFilterCriteriaList) {
                            filteringPassed = true
                            if (resource) {
                                filteringPassed = optimiResourceService.filterResourceByResourceFilterCriteriaObjects(resource, resourceFilterCriteriaList, dataset, indicatorAdditionalQuestionIds)
                            }
                        } else {
                            filteringPassed = true
                        }

                        if (filteringPassed) {
                            Dataset existing = collectedDatasets.find({ dataset.manualId.equals(it.manualId) })

                            if (existing) {
                                existing.resultCategoryIds.add(resultCategory.resultCategoryId)
                            } else {
                                dataset.resultCategoryIds = [resultCategory.resultCategoryId]
                                collectedDatasets.add(dataset)
                            }
                        }
                    }
                } else {
                    loggerUtil.info(log, "Didn't collect datasets for resultCategory ${resultCategory.resultCategory}. No applyConditions filled for: ${resultCategory.applyConditions?.data}")
                }
            }
        }
        loggerUtil.info(log, "AMOUNT OF COLLECTED DATASETS FOR INDICATOR ${indicator.indicatorId}: ${collectedDatasets ? collectedDatasets.size() : 0}")
        return collectedDatasets?.findAll({ it.calculatedQuantity != null })
    }

    /**
     * Filters datasets by queryId, sectionIds and questionIds from the sections
     * @param datasets - list of datasets to filter
     * @param dataQueryId - dataset queryId - only datasets with this queryId will be included in the output datasets.
     * @param sectionIds - set of dataset sectionIds - if it is set then only datasets with these sectionIds
     *        will be included in the output datasets, otherwise there is no filtering by sectionId
     * @param limitSectionDataToQuestionIds - map of sectionId to set of questionIds - if it is set then datasets with these questionIds for the sectionId
     *        will be included in the output datasets, otherwise there is no filtering by questionIds
     * @param hiddenQuestionsPerSection - map of sectionId to list of questionIds that have to be excluded from the result -
     *        if list of questionIds is empty then whole section is excluded from results
     * @return filtered list of datasets
     */
    private List<Dataset> filterDatasetsByQuerySectionsAndQuestions(List<Dataset> datasets, String dataQueryId, Set<String> sectionIds, Map<String, Set> limitSectionDataToQuestionIds, Map<String, List<String>> hiddenQuestionsPerSection) {

        return datasets.findAll { Dataset dataset ->

            if (dataset.queryId != dataQueryId) {
                return false
            }

            if (sectionIds && !sectionIds.contains(dataset.sectionId)) {
                return false
            }

            if (limitSectionDataToQuestionIds) {
                def questionIds = limitSectionDataToQuestionIds[dataset.sectionId]
                if (questionIds) {
                    if (!questionIds.contains(dataset.questionId)) {
                        return false
                    }
                }
            }

            if (hiddenQuestionsPerSection) {
                def hiddenQuestionIds = hiddenQuestionsPerSection[dataset.sectionId]
                if (hiddenQuestionIds?.isEmpty() || hiddenQuestionIds?.contains(dataset.questionId)) {
                    return false
                }
            }

            return true
        }
    }

    private def getAnyParameterFromResource(String parameter, Resource resource, List<String> stages = []) {
        def value
        def firstLetter = parameter.trim().substring(0, 1).toUpperCase()

        def methodName = "get" + firstLetter + parameter.trim().substring(1)

        try {
            if (parameter in HIERARCHY_PARAMETERS) {
                Method method = Resource.class.getDeclaredMethod(methodName, List.class)
                value = method.invoke(resource, stages)
            } else {
                Method method = Resource.class.getDeclaredMethod(methodName, null)
                value = method.invoke(resource, null)
            }
        } catch (NoSuchMethodException e) {
            // parameter might be called from datasets additionalQuestionAnswers so resource might not have such getter, carry on
        } catch (Exception e) {
            loggerUtil.error(log, "Exception in calling Resource method " + methodName + ": " + e)
            flashService.setErrorAlert("Exception in calling Resource method " + methodName + ": ${e.getMessage()}", true)

        }
        return value
    }

    private boolean isDataFoundByApplyConditions(ApplyConditions applyConditions, List<Dataset> datasets) {
        boolean dataFound = true

        if (applyConditions?.data) {
            if (datasets) {
                applyConditions.data.each { String queryId, List<String> sectionIds ->
                    if (!datasets.find({ it.queryId.equals(queryId) && sectionIds.contains(it.sectionId) })) {
                        dataFound = false
                    }
                }
            } else {
                dataFound = false
            }
        }
        return dataFound
    }

    private Double getDoubleFromQuestion(String questionId, List<Dataset> datasets) {
        Double value = null

        if (questionId && datasets) {
            questionId = questionId.replace('$', '')
            Dataset matchingDataset = datasets.find({ questionId.equals(it.questionId) })

            if (matchingDataset?.quantity != null) {
                value = matchingDataset.quantity
            }
        }
        return value
    }

    /**
     * Gets datasets serviceLife, either given by user, or default from resource / resource subtype
     *
     * @param dataset
     * @param resource
     * @param childEntity
     * @param indicator
     * @return
     */
    //TODO Try to use overloaded method below with pre-fetched resource type
    Double getServiceLife(Dataset dataset, Resource resource, Entity childEntity, Indicator indicator) {
        Double serviceLife
        Boolean useSpecificServiceLifes = indicator?.useSpecificServiceLifes
        String defaultServiceLife = childEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)

        if (!useSpecificServiceLifes && resource && defaultServiceLife && ("default".equals(dataset?.additionalQuestionAnswers?.get("serviceLife")) || !dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            if ("resourceServiceLife".equals(defaultServiceLife)||resource.serviceLifeOverride) {
                if (resource.serviceLife != null) {
                    serviceLife = resource.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceService.getSubType(resource)?.serviceLifeTechnical
                }
            } else {
                ResourceType subType = resourceService.getSubType(resource)

                if (subType) {
                    serviceLife = DomainObjectUtil.callGetterByAttributeName(defaultServiceLife, subType)
                }
            }
        } else if ("permanent".equals(dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            serviceLife = resource?.serviceLife
        } else {
            if (dataset?.additionalQuestionAnswers?.get("serviceLife") != null &&
                    DomainObjectUtil.isNumericValue(dataset.additionalQuestionAnswers.get("serviceLife").toString())) {
                serviceLife = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get("serviceLife").toString())
            } else {
                if (resource?.serviceLife != null) {
                    serviceLife = resource?.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceService.getSubType(resource)?.serviceLifeTechnical
                }
            }
        }
        return serviceLife
    }

    //TODO Keep getServiceLife up-to-date with mirrored method in DetailReportService
   Double getServiceLife(Dataset dataset, Resource resource, ResourceType resourceSubType, Entity childEntity, Indicator indicator) {
        Double serviceLife
        Boolean useSpecificServiceLifes = indicator?.useSpecificServiceLifes
        String defaultServiceLife = childEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)

        if (!useSpecificServiceLifes && resource && defaultServiceLife && ("default".equals(dataset?.additionalQuestionAnswers?.get("serviceLife")) || !dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            if ("resourceServiceLife".equals(defaultServiceLife)||resource.serviceLifeOverride) {
                if (resource.serviceLife != null) {
                    serviceLife = resource.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceSubType?.serviceLifeTechnical
                }
            } else {
                ResourceType subType = resourceSubType

                if (subType) {
                    serviceLife = DomainObjectUtil.callGetterByAttributeName(defaultServiceLife, subType)
                }
            }
        } else if ("permanent".equals(dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            serviceLife = resource?.serviceLife
        } else {
            if (dataset?.additionalQuestionAnswers?.get("serviceLife") != null &&
                    DomainObjectUtil.isNumericValue(dataset.additionalQuestionAnswers.get("serviceLife").toString())) {
                serviceLife = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get("serviceLife").toString())
            } else {
                if (resource?.serviceLife != null) {
                    serviceLife = resource.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceSubType?.serviceLifeTechnical
                }
            }
        }
        return serviceLife
    }

    /**
     * Handles result for impact timing
     *
     * @param impactTiming
     * @param calculationResult
     * @param entity
     * @param indicator
     * @param preparedDenominator
     */
    private void impactTimingProcessing(String impactTiming, CalculationResult calculationResult, Entity entity,
                                        Indicator indicator, PreparedDenominator preparedDenominator, ResourceCache resourceCache) {
        if (calculationResult.result != null) {
            ResourceTypeCache resourceTypeCache = resourceCache.resourceTypeCache
            Dataset dataset = null
            Set<Dataset> entityDatasetsForResult = calculationResultService.getDatasets(calculationResult, entity)
            if (entityDatasetsForResult) {
                dataset = entityDatasetsForResult.first()
            }
            //got ambiguous method overloading exception when dataset is null , so adding extra null check
            //related tickets
            //https://oneclicklca.atlassian.net/browse/REL-770
            //https://oneclicklca.atlassian.net/browse/REL-826
            Resource resource = dataset ? resourceCache.getResource(dataset) : null

            if (resource) {
                Map<String, Double> resultPerOccurencePeriod = new TreeMap<String, Double>()

                if ("initial".equalsIgnoreCase(impactTiming)) {
                    resultPerOccurencePeriod.put("0", calculationResult.result)
                } else if ("final".equalsIgnoreCase(impactTiming)) {
                    if (preparedDenominator.assessmentPeriod == null) {
                        resultPerOccurencePeriod.put("61", calculationResult.result)
                    } else {
                        resultPerOccurencePeriod.put((preparedDenominator.assessmentPeriod + 1).intValue().toString(), calculationResult.result)
                    }
                } else if ("external".equalsIgnoreCase(impactTiming)) {
                    if (preparedDenominator.assessmentPeriod != null) {
                        resultPerOccurencePeriod.put((preparedDenominator.assessmentPeriod + 2).intValue().toString(), calculationResult.result)
                    } else {
                        loggerUtil.error(log, "ImpactTiming: cannot calculate external, assessementPeriod missing")
                        flashService.setErrorAlert("ImpactTiming: cannot calculate external, assessementPeriod missing", true)
                    }
                } else if ("annual".equalsIgnoreCase(impactTiming)) {
                    if (preparedDenominator.assessmentPeriod != null) {
                        for (int i = 1; i <= preparedDenominator.assessmentPeriod; i++) {
                            resultPerOccurencePeriod.put(i.toString(), calculationResult.result / preparedDenominator.assessmentPeriod)
                        }
                    } else {
                        loggerUtil.error(log, "ImpactTiming: cannot calculate annual, assessementPeriod missing")
                        flashService.setErrorAlert("ImpactTiming: cannot calculate external, assessementPeriod missing", true)
                    }
                } else if ("recurring".equalsIgnoreCase(impactTiming)) {
                    List datasetResultWithServiceLife = []
                    List<Dataset> resultDatasets = calculationResultService.getDatasets(calculationResult, entity)?.toList()?.unique({
                        it.manualId
                    })

                    if (resultDatasets) {
                        resultDatasets.each { Dataset d ->
                            Resource r = resourceCache.getResource(d)
                            Double serviceLife = getServiceLife(d, r, resourceTypeCache.getResourceSubType(r),
                                    entity, indicator)

                            if (serviceLife) {
                                Double result = calculationResult.calculationResultDatasets?.get(d.manualId)?.result

                                if (result) {
                                    datasetResultWithServiceLife.add([(serviceLife): result])
                                }
                            }
                        }
                    }

                    if (!datasetResultWithServiceLife.isEmpty()) {
                        datasetResultWithServiceLife.each { Map serviceLifeWithResult ->
                            Double serviceLife = serviceLifeWithResult.keySet().first()
                            Double result = serviceLifeWithResult.get(serviceLife)

                            if (serviceLife != null && serviceLife > 0D) {
                                double ceiledServiceLife = Math.ceil(serviceLife.doubleValue())
                                List<Double> periods = []

                                for (Double i = ceiledServiceLife; i < preparedDenominator.assessmentPeriod; i = i + ceiledServiceLife) {
                                    periods.add(i)
                                }

                                periods?.each { Double period ->
                                    Double existing = resultPerOccurencePeriod.get(period.intValue().toString())

                                    if (existing) {
                                        existing = existing + (result / periods.size())
                                        resultPerOccurencePeriod.put(period.intValue().toString(), existing)
                                    } else {
                                        resultPerOccurencePeriod.put(period.intValue().toString(), result / periods.size())
                                    }
                                }
                            }
                        }
                    }
                }
                calculationResult.resultPerOccurencePeriod = resultPerOccurencePeriod
            }
        }
    }
    /**
     * Handles result for recurring replacements
     *
     * @param assessmentPeriod
     * @param discountFactors
     * @param serviceLifePerOccurance
     * @return discountedRecurrences
     * */
    private Map<Integer, Double> calculateDiscountedRecurrences(assessmentPeriod , Map<String, Double> discountFactors , serviceLifePerOccurence){
        //annie - 15336
        Map<Integer, Double> discountedRecurrences = [:]
        if(discountFactors){
            Double lastValue = discountFactors?.get("final")
            loggerUtil.info(log , "serviceLifePerOccurence ======= ${serviceLifePerOccurence}")
            serviceLifePerOccurence?.each ({ Integer recurrence, Integer serviceLife ->
                if (discountFactors?.get(serviceLife.toString())) {
                    discountedRecurrences.put(serviceLife, discountFactors.get(serviceLife.toString()))
                }else if(assessmentPeriod > discountFactors.size() && !discountFactors?.get(serviceLife.toString()) && lastValue >= 0){
                    discountedRecurrences?.put(serviceLife , lastValue)
                }
            })
        }
        loggerUtil.info(log , "discounted Recurrences , discount per servicelife === ${discountedRecurrences}")
        return discountedRecurrences
    }
    /**
     * Handles result for recurring replacements
     *
     * @param dataset
     * @param resultCategory
     * @param indicator
     * @param resultsByScenarios
     * @param entity
     * @param preparedDenominator
     * @param resource
     * @return
     */
    private List<CalculationResult> recurringReplacementProcessingResult(Dataset dataset, ResultCategory resultCategory,
                                                                         Indicator indicator, List<CalculationResult> resultsByScenarios,
                                                                         Entity entity, PreparedDenominator preparedDenominator,
                                                                         Resource resource, ResourceType resourceSubType,
                                                                         Map<String, Double> discountFactors = null) {
        CalculationResult calculationResult
        String resultCategoryId = resultCategory.resultCategoryId
        String indicatorId = indicator?.indicatorId

        if (preparedDenominator.assessmentPeriod != null && preparedDenominator.assessmentPeriod > 0) {
            if (dataset.calculatedQuantity != null) {
                if (resource) {
                    Question question = datasetService.getQuestion(dataset)

                    if (question && (question.recurringReplacement == null || question.recurringReplacement)) {
                        Double serviceLife = getServiceLife(dataset, resource, resourceSubType, entity, indicator)

                        if (serviceLife != null && serviceLife > 0D) {
                            serviceLife = Math.ceil(serviceLife.doubleValue())
                            int recurrences = 1

                            // PP decimal discountedRecurrences = discountFactors.get (serviceLife)
                            List<Integer> occurrencePeriods = new ArrayList<Integer>()
                            Map<String, Double> resultPerOccurencePeriod = new TreeMap<String, Double>()
                            Map<String, Double> resultWithDiscountPerOccurencePeriod = new TreeMap<String, Double>()
                            List<Integer> recurrenceMultipliers = []
                            Map<Integer, Integer> serviceLifePerOccurence = [:]
                            //annie
                            Map<Integer, Double> discountedRecurrences = [:]
                            for (Double i = serviceLife; i < preparedDenominator.assessmentPeriod; i = i + serviceLife) {
                                recurrenceMultipliers.add(new Integer(recurrences.intValue()))
                                serviceLifePerOccurence.put(new Integer(recurrences.intValue()), i.toInteger())
                                recurrences++
                                // PP discountedRecurrences = discountedRecurrences + discountFactors.get (i)
                                occurrencePeriods.add(i)
                            }
                            //annie
                            if(discountFactors) {
                                discountedRecurrences = calculateDiscountedRecurrences(preparedDenominator.assessmentPeriod, discountFactors, serviceLifePerOccurence)
                            }

                            if (resultCategory.process && (resultCategory.calculateReplacementsOnly || resultCategory.calculateFractionalReplacementsOnly)) {
                                //check with Panu
                                calculationResult = resultsByScenarios?.
                                        find({
                                            resultCategoryId.equals(it.resultCategoryId) &&
                                                    it.datasetIds.contains(dataset.manualId) &&
                                                    it.indicatorId.equals(indicatorId)
                                        })

                                if (calculationResult) {
                                    resultsByScenarios.removeAll({
                                        resultCategoryId.equals(it.resultCategoryId) &&
                                                it.datasetIds.contains(dataset.manualId) && it.indicatorId.equals(it.indicatorId)
                                    })

                                    // PP the part below would need a rewrite where it's not flat division operation but where it's actually using the discountedRecurrences. you can write an utility function for this to get series
                                    // of multipliers summed up at given step of X; starting from X and growing by X as long as it's less than Y. Trust this is clear.
                                    if (occurrencePeriods.size() > 0) {
                                        recurrenceMultipliers.each { Integer recurrency ->
                                            resultPerOccurencePeriod.put(serviceLifePerOccurence.get(recurrency).toString(),
                                                    calculationResult.result ? calculationResult.result * recurrency : 0)
                                        }
                                        //annie
                                        if(discountedRecurrences) {
                                            recurrenceMultipliers.each { Integer recurrency ->
                                                if(discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                                    resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(),
                                                            calculationResult.result ? calculationResult.result * discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) : 0)
                                                }
                                            }
                                            //annie
                                            calculationResult.resultWithDiscountPerOccurencePeriod = resultWithDiscountPerOccurencePeriod
                                        }
                                        calculationResult.resultPerOccurencePeriod = resultPerOccurencePeriod
                                        calculationResult.resultWithoutOccurrencePeriods = calculationResult.result
                                        calculationResult.result = calculationResult.result ? calculationResult.result * occurrencePeriods.size() : 0
                                        dataset.occurrencePeriods = occurrencePeriods
                                        calculationResult.calculationFormula = "${calculationResult.calculationFormula},<b>Replacements:</b> ${calculationResult.result.round(2)} = ${calculationResult.resultWithoutOccurrencePeriods ? calculationResult.resultWithoutOccurrencePeriods.round(2) : 0} * ${occurrencePeriods.size()} (periods of occurrence) (${resultCategoryId})"
                                        applyLocalCompensation(dataset, calculationResult)
                                        //annie
                                        if(resultWithDiscountPerOccurencePeriod) {
                                            calculationResult.calculatedDiscountFactor = discountedRecurrences?.values().sum()
                                            calculationResult.discountedResult = resultWithDiscountPerOccurencePeriod?.values().sum()

                                        }
                                        resultsByScenarios.add(calculationResult)
                                    }
                                }
                            } else if (occurrencePeriods.size() > 0) {
                                calculationResult = new CalculationResult()
                                calculationResult.indicatorId = indicatorId
                                calculationResult.datasetIds = [dataset.manualId]
                                calculationResult.unit = dataset.userGivenUnit
                                calculationResult.calculatedUnit = dataset.calculatedUnit
                                calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                                calculationResult.resultCategoryId = resultCategoryId
                                calculationResult.calculationResourceId = resource.resourceId
                                calculationResult.calculationProfileId = resource.profileId
                                calculationResult.originalResourceId = resource.resourceId
                                calculationResult.originalProfileId = resource.profileId

                                // PP the part below would need a rewrite where it's not flat division operation but where it's actually using the
                                // discountedRecurrences. you can write an utility function for this to get series of multipliers summed up at given step of X; starting from X and growing by X as long as it's less than Y. Trust this is clear.

                                //annie - the below is not the final result , if there is discounting that will also be added below
                                calculationResult.result = dataset.calculatedQuantity * occurrencePeriods.size()
                                dataset.occurrencePeriods = occurrencePeriods
                                calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Replacements:</b> ${calculationResult.result.round(2)} = ${dataset.calculatedQuantity.round(2)} * ${occurrencePeriods.size()} (periods of occurrence) (${resultCategoryId})"

                                // PP the part below would need a rewrite where you'd inject value using discountedRecurrences too
                                //annie - question - below to be removed ?
                                recurrenceMultipliers.each { Integer recurrency ->
                                    resultPerOccurencePeriod.put(serviceLifePerOccurence.get(recurrency).toString(), recurrency * dataset.calculatedQuantity)
                                }

                                calculationResult.resultPerOccurencePeriod = resultPerOccurencePeriod
                                calculationResult.resultWithoutOccurrencePeriods = dataset.calculatedQuantity
                                applyLocalCompensation(dataset, calculationResult)
                                //annie - 15336
                                if(discountedRecurrences && !indicator?.indicatorDiscountScenarios?.get("excludeForCalculationRules")?.contains(calculationResult?.calculationRuleId)) {
                                    recurrenceMultipliers.each { Integer recurrency ->
                                        if(discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                            resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence?.get(recurrency).toString(), discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency)) * dataset?.calculatedQuantity)
                                        }
                                    }
                                    //annie
                                    calculationResult.resultWithDiscountPerOccurencePeriod = resultWithDiscountPerOccurencePeriod
                                }
                                //annie
                                if(resultWithDiscountPerOccurencePeriod) {
                                    calculationResult.calculatedDiscountFactor = discountedRecurrences?.values().sum()
                                    calculationResult.discountedResult = resultWithDiscountPerOccurencePeriod?.values().sum()

                                }

                                log.debug("ResultCategory is ${resultCategoryId}. " +
                                        "Dataset Question is ${question.questionId} (${question.recurringReplacement})")
                                log.debug("Adding an extra calculationResult for ${dataset.resourceId}: "
                                        + calculationResult.result + " (${dataset.calculatedQuantity} * ${ occurrencePeriods.size()})")
                                resultsByScenarios.add(calculationResult)
                            }
                        } else {
                            resultsByScenarios?.removeAll({
                                resultCategoryId.equals(it.resultCategoryId) &&
                                        it.datasetIds.contains(dataset.manualId) && it.indicatorId.equals(it.indicatorId)
                            })
                        }
                    } else {
                        resultsByScenarios?.removeAll({
                            resultCategoryId.equals(it.resultCategoryId) &&
                                    it.datasetIds.contains(dataset.manualId) && it.indicatorId.equals(it.indicatorId)
                        })
                    }
                }
            }
        } else if (resultCategory.process && (resultCategory.calculateReplacementsOnly || resultCategory.calculateFractionalReplacementsOnly)) {
            //check with Panu
            calculationResult = resultsByScenarios?.
                    find({
                        resultCategoryId.equals(it.resultCategoryId) &&
                                it.datasetIds.contains(dataset.manualId) &&
                                it.indicatorId.equals(indicatorId)
                    })

            if (calculationResult) {
                resultsByScenarios.removeAll({
                    resultCategoryId.equals(it.resultCategoryId) &&
                            it.datasetIds.contains(dataset.manualId) && it.indicatorId.equals(it.indicatorId)
                })
                calculationResult.resultWithoutOccurrencePeriods = 0D
                calculationResult.result = 0D
                calculationResult.calculationFormula = "${calculationResult.calculationFormula},<b>Replacements:</b> ${calculationResult.result} (Replacements could not be calculated as assessment period was not given) (${resultCategoryId})"
                applyLocalCompensation(dataset, calculationResult)
                resultsByScenarios.add(calculationResult)
            }
        }
        return resultsByScenarios
    }

    /**
     * Handles transportation result
     *
     * @param dataset
     * @param resultCategory
     * @param childEntity
     * @param indicator
     * @param transportDistanceQuestion
     * @param transportResourceQuestion
     * @param originalResource
     * @return
     */

    private CalculationResult dataTransportProcessingResult(Dataset dataset, ResultCategory resultCategory, Entity childEntity,
                                                            Indicator indicator, String transportDistanceQuestion,
                                                            String transportResourceQuestion, Resource originalResource,
                                                            ResourceType resourceSubType) {
        CalculationResult calculationResult
        transportDistanceQuestion = !transportDistanceQuestion ? "transportDistance_km" : transportDistanceQuestion
        transportResourceQuestion = !transportResourceQuestion ? "transportResourceId" : transportResourceQuestion
        String transportResourceId = dataset.additionalQuestionAnswers?.get(transportResourceQuestion)
        if (!transportResourceId) {
            if ("transportDistance_km".equals(transportDistanceQuestion)) {
                if (resourceSubType?.transportResourceId) {
                    transportResourceId = resourceSubType.transportResourceId
                } else if (!originalResource?.construction && resourceSubType) {
                    loggerUtil.error(log, "TransportProcessing: No transPortResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResorceId  --originalResource ${originalResource?.resourceId}")
                    flashService.setErrorAlert("TransportProcessing: No transPortResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResorceId  --originalResource ${originalResource?.resourceId}", true)
                }
            } else if ("transportDistance_kmAus".equals(transportDistanceQuestion)) {
                if (resourceSubType?.transportResourceIdAus) {
                    transportResourceId = resourceSubType.transportResourceIdAus
                } else if (!originalResource?.construction) {
                    loggerUtil.error(log, "TransportProcessing: ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResourceIdAus  --originalResource ${originalResource?.resourceId}")
                    flashService.setErrorAlert("TransportProcessing: ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResourceIdAus  --originalResource ${originalResource?.resourceId}", true)
                }
            } else {
                if (resourceSubType?.transportResourceIdMiles) {
                    transportResourceId = resourceSubType.transportResourceIdMiles
                } else if (resourceSubType?.transportResourceIdRICS) {
                    transportResourceId = resourceSubType.transportResourceIdRICS
                } else if (!originalResource?.construction && resourceSubType) {
                    loggerUtil.error(log, "TransportProcessing: No transPortResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResourceIdMiles --originalResource ${originalResource?.resourceId}")
                    flashService.setErrorAlert("TransportProcessing: No transPortResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing transportResourceIdMiles --originalResource ${originalResource?.resourceId}", true)
                }
            }
        }

        if (dataset.calculatedMass && dataset.calculatedMass > 0 && originalResource) {
            Double transportDistanceAsDouble = getUserAnswerOrDefaultValueForTransport(dataset, indicator, childEntity,
                    transportDistanceQuestion, originalResource, resourceSubType) ?: null
            Double calculatedResult

            if (transportDistanceAsDouble) {
                calculatedResult = (dataset.calculatedMass * transportDistanceAsDouble / 1000)
            }

            if (calculatedResult) {
                calculationResult = new CalculationResult()
                calculationResult.indicatorId = indicator.indicatorId
                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                calculationResult.originalResourceId = originalResource.resourceId
                calculationResult.originalProfileId = originalResource.profileId
                calculationResult.datasetIds = [dataset.manualId]
                calculationResult.unit = dataset.userGivenUnit
                calculationResult.calculatedUnit = dataset.calculatedUnit
                calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Transport: ${roundingNumber(calculatedResult)} tonkm.</b> Calculated from ${roundingNumber(dataset.calculatedMass)} * ${transportDistanceAsDouble} / 1000 (transport distance) (${resultCategory.resultCategoryId})"
                boolean originalTransportResourceIdValid = true

                if (transportResourceId) {
                    calculationResult.calculationResourceId = transportResourceId
                    calculationResult.calculationProfileId = null
                } else {
                    originalTransportResourceIdValid = false
                }

                if (originalTransportResourceIdValid) {
                    calculationResult.result = calculatedResult
                }
            }
        }
        return calculationResult
    }


    /**
     * Handles refrigerant process result: at this stage it only collect refrigerant quantity & dataset quantity. Refrigerant resource impact
     * is collected in later phase
     *
     * @param dataset
     * @param resultCategory
     * @param indicator
     * @param refrigerantQuantityQuestion
     * @param refrigerantResourceQuestion
     * @param originalResource
     * @return
     */

    private CalculationResult dataRefrigerantProcessingResult(Dataset dataset, ResultCategory resultCategory, Indicator indicator, String refrigerantQuantityQuestion,
                                                              String refrigerantResourceQuestion, Resource originalResource, Double fixedDiscountFactor) {
        CalculationResult calculationResult
        if (refrigerantQuantityQuestion && refrigerantResourceQuestion) {
            String refrigerantResourceId = dataset.additionalQuestionAnswers?.get(refrigerantResourceQuestion)

            if (dataset.quantity && dataset.quantity > 0 && originalResource) {
                String refrigerantQuantityText = dataset.additionalQuestionAnswers?.get(refrigerantQuantityQuestion)
                Double calculatedResult
                Double refrigerantQuantity = 0D

                if (refrigerantQuantityText && refrigerantQuantityText.isNumber()) {
                    refrigerantQuantity = DomainObjectUtil.convertStringToDouble(refrigerantQuantityText)
                    calculatedResult = refrigerantQuantity * dataset.quantity
                }

                if (calculatedResult) {
                    calculationResult = new CalculationResult()
                    calculationResult.indicatorId = indicator.indicatorId
                    calculationResult.resultCategoryId = resultCategory.resultCategoryId
                    calculationResult.originalResourceId = originalResource.resourceId
                    calculationResult.originalProfileId = originalResource.profileId
                    calculationResult.datasetIds = [dataset.manualId]
                    calculationResult.unit = dataset.userGivenUnit
                    calculationResult.calculatedUnit = dataset.calculatedUnit
                    calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                    calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Transport: ${roundingNumber(calculatedResult)} tonkm.</b> Calculated from ${roundingNumber(dataset.calculatedMass)} * ${refrigerantQuantity} / 1000 (transport distance) (${resultCategory.resultCategoryId})"
                    calculationResult.result = calculatedResult
                    //annie for 15336 , missing in refrigerant process
                    if (fixedDiscountFactor) {
                        calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                        calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                    }

                    if (refrigerantResourceId) {
                        calculationResult.calculationResourceId = refrigerantResourceId
                        calculationResult.calculationProfileId = null
                    }

                }
            }
        }
        return calculationResult
    }

    /**
     * Handles new EOL transportation result
     *
     * @param dataset
     * @param resultCategory
     * @param indicator
     * @param projectCountry
     * @param originalResource
     * @param lcaModel
     * @return
     */

    private CalculationResult dataEOLTransportProcessingResult(Dataset dataset, ResultCategory resultCategory,
                                                               Indicator indicator, Resource projectCountry,
                                                               Resource originalResource, String lcaModel,
                                                               Double fixedDiscountFactor = null,
                                                               ResourceType resourceSubType, EolProcessCache eolProcessCache) {
        CalculationResult calculationResult

        if (dataset.calculatedMass && dataset.calculatedMass > 0 && originalResource && resourceSubType) {
            String transportResourceId
            Double transportDistanceAsDouble

            EolProcess eolProcess = eolProcessService.getEolProcessForDataset(dataset, originalResource, resourceSubType, projectCountry, null, lcaModel, eolProcessCache)

            if (eolProcess) {
                if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcess.epdConversionType) {
                    calculationResult = getEPDEOLCalculationResult(originalResource, indicator.indicatorId, resultCategory, dataset)
                    applyLocalCompensation(dataset, calculationResult)
                    //annie - 15336
                    if (fixedDiscountFactor) {
                        calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                        calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                    }
                } else {
                    transportDistanceAsDouble = eolProcess.c2Distance_km
                    transportResourceId = eolProcess.c2ResourceId

                    Double calculatedResult

                    if (transportDistanceAsDouble) {
                        calculatedResult = (dataset.calculatedMass * transportDistanceAsDouble / 1000)
                    }

                    if (calculatedResult && transportResourceId) {
                        calculationResult = new CalculationResult()
                        calculationResult.indicatorId = indicator.indicatorId
                        calculationResult.resultCategoryId = resultCategory.resultCategoryId
                        calculationResult.originalResourceId = originalResource.resourceId
                        calculationResult.originalProfileId = originalResource.profileId
                        calculationResult.datasetIds = [dataset.manualId]
                        calculationResult.unit = dataset.userGivenUnit
                        calculationResult.calculatedUnit = dataset.calculatedUnit
                        calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                        calculationResult.calculationResourceId = transportResourceId
                        calculationResult.calculationProfileId = null
                        calculationResult.result = calculatedResult
                        calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Transport in EOL:</b> ${calculationResult.result.round(2)} = ${dataset.calculatedMass.round(2)} * ${transportDistanceAsDouble} / 1000 (transport distance) (${resultCategory.resultCategoryId})"

                        applyLocalCompensation(dataset, calculationResult)
                        //annie - 15336
                        if (fixedDiscountFactor) {
                            calculationResult.calculatedDiscountFactor = fixedDiscountFactor
                            calculationResult.discountedResult = calculationResult.result * fixedDiscountFactor
                        }
                    }
                }
            }
        }
        return calculationResult
    }

    /**
     * Old version of EOL Transportation result, needed for old tool compatibility
     *
     * @param dataset
     * @param resultCategory
     * @param indicator
     * @param transportDistanceQuestion
     * @param transportResourceQuestion
     * @param originalResource
     * @return
     */

    private CalculationResult dataEOLTransportProcessingResultLegacy(Dataset dataset, ResultCategory resultCategory,
                                                                     Indicator indicator, String transportDistanceQuestion,
                                                                     String transportResourceQuestion, Resource originalResource,
                                                                     ResourceType resourceSubType) {
        CalculationResult calculationResult
        String transportResourceId = transportResourceQuestion ? dataset.additionalQuestionAnswers?.get(transportResourceQuestion) : null

        if (!transportResourceId) {
            if (!originalResource?.construction) {
                loggerUtil.error(log, "EOLTransportProcessing: No eolTransportResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing eolTransportResourceId --originalResource ${originalResource?.resourceId} / ${originalResource?.profileId}")
                flashService.setErrorAlert("EOLTransportProcessing: No eolTransportResourceId or ResourceSubType ${resourceSubType?.resourceType} / ${resourceSubType?.subType} subType is missing eolTransportResourceId --originalResource ${originalResource?.resourceId} / ${originalResource?.profileId}", true)
            }
        }

        if (dataset.calculatedMass && dataset.calculatedMass > 0 && originalResource) {
            Double transportDistanceAsDouble

            if (transportDistanceQuestion && dataset.additionalQuestionAnswers?.get(transportDistanceQuestion) != null &&
                    DomainObjectUtil.isNumericValue(dataset.additionalQuestionAnswers.get(transportDistanceQuestion).toString())) {
                transportDistanceAsDouble = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get(transportDistanceQuestion).toString())
            }
            Double calculatedResult

            if (transportDistanceAsDouble) {
                calculatedResult = (dataset.calculatedMass * transportDistanceAsDouble / 1000)
            }

            if (calculatedResult) {
                calculationResult = new CalculationResult()
                calculationResult.indicatorId = indicator.indicatorId
                calculationResult.resultCategoryId = resultCategory.resultCategoryId
                calculationResult.originalResourceId = originalResource.resourceId
                calculationResult.originalProfileId = originalResource.profileId
                calculationResult.datasetIds = [dataset.manualId]
                calculationResult.unit = dataset.userGivenUnit
                calculationResult.calculatedUnit = dataset.calculatedUnit
                calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers
                boolean originalTransportResourceIdValid = true

                if (transportResourceId) {
                    calculationResult.calculationResourceId = transportResourceId
                    calculationResult.calculationProfileId = null
                } else {
                    originalTransportResourceIdValid = false
                }

                if (originalTransportResourceIdValid) {
                    calculationResult.result = calculatedResult
                    applyLocalCompensation(dataset, calculationResult)
                }
            }
        }
        return calculationResult
    }

    /**
     * Gets user given answer or default answer for transportation additionalquestion
     *
     * @param dataset
     * @param indicator
     * @param entity
     * @param questionId
     * @param resource
     * @return
     */

    private Double getUserAnswerOrDefaultValueForTransport(Dataset dataset, Indicator indicator, Entity entity, String questionId,
                                                           Resource resource, ResourceType resourceSubType) {
        Double value

        if (dataset && indicator && entity && questionId && resource) {
            if (dataset.additionalQuestionAnswers?.get(questionId) != null &&
                    DomainObjectUtil.isNumericValue(dataset.additionalQuestionAnswers.get(questionId).toString())) {
                value = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get(questionId).toString())
                // loggerUtil.info(log, "Using user given value ${value}")
            } else if (entity.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) && "default".equals(dataset?.additionalQuestionAnswers?.get(questionId))) {
                String valueToSet = entity.defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)

                if (valueToSet) {
                    ResourceType type = resourceSubType

                    if (type) {
                        def resourceTypeAnswer = DomainObjectUtil.callGetterByAttributeName(valueToSet, type)

                        if (resourceTypeAnswer && resourceTypeAnswer.toString().isNumber()) {
                            value = DomainObjectUtil.convertStringToDouble(resourceTypeAnswer.toString())
                        }

                        if (value == null) {
                            loggerUtil.error(log, "TransportProcessing: ResourceType ${type.resourceType} / ${type.subType} is missing defaultTransport for ${valueToSet}")
                            flashService.setErrorAlert("TransportProcessing: ResourceType ${type.resourceType} / ${type.subType} is missing defaultTransport for ${valueToSet}", true)
                        }
                    } else {
                        loggerUtil.error(log, "TransportProcessing: No resourceType found for resource ${resource.resourceId} / ${resource.profileId}")
                        flashService.setErrorAlert("TransportProcessing: No resourceType found for resource ${resource.resourceId} / ${resource.profileId}", true)
                    }
                }
            }
        }
        return value
    }

    /**
     * Check if resource actually exists and is valid
     *
     * @param resource
     * @param resourceId
     * @return
     */
    // TODO: be sure that all data for that method was loaded to resource cache
    private boolean checkIfResourceIdValid(Resource resource, String resourceId, ResourceCache resourceCache) {
        boolean valid = false

        if (resourceId) {
            if (resourceId.equals(resource?.resourceId)) {
                valid = true
            } else if (resourceCache.getResource(resourceId, null)) {
                valid = true
            }
        }
        return valid
    }

    /**
     * Handles result for recurrence
     *
     * @param dataset
     * @param resultCategory
     * @param indicatorId
     * @param preparedDenominator
     * @param resource
     * @return
     */

    private CalculationResult recurrencenceProcessingResult(Dataset dataset, ResultCategory resultCategory,
                                                            String indicatorId, PreparedDenominator preparedDenominator,
                                                            Resource resource, Map<String, Double> discountFactors = null, Indicator indicator) {
        CalculationResult calculationResult
        //Indicator indicator = indicatorService.getIndicatorById(indicatorId)
        if (preparedDenominator.assessmentPeriod && preparedDenominator.assessmentPeriod > 0) {
            List<Integer> occurrencePeriods = new ArrayList<Integer>()

            if (dataset.calculatedQuantity != null) {
                Integer frequency = resource?.maintenanceFrequency?.intValue()
                Map<String, Double> resultPerOccurencePeriod = new TreeMap<String, Double>()
                //annie - newly added var - 3
                Map<Integer, Integer> serviceLifePerOccurence = [:]
                List<Integer> recurrenceMultipliers = []
                Map<String, Double> resultWithDiscountPerOccurencePeriod = new TreeMap<String, Double>()
                // changed the below - annie - old condition was - if (resource && frequency && frequency != -1 && frequency != 0)
                if (resource && frequency && frequency > 0) {

                    Map<Integer, Double> discountedRecurrences = [:]
                    int multiplier = 1

                    for (int i = frequency; i < preparedDenominator.assessmentPeriod; i = i + frequency) {
                        resultPerOccurencePeriod.put(i.toString(), dataset.calculatedQuantity * multiplier)
                        //annie
                        //newly added
                        serviceLifePerOccurence.put(new Integer(recurrences.intValue()), i.toInteger())
                        recurrenceMultipliers.add(new Integer(recurrences.intValue()))
                        occurrencePeriods.add(i.toString())
                        multiplier++

                    }
                    //annie
                    if(discountFactors) {
                        discountedRecurrences = calculateDiscountedRecurrences(preparedDenominator.assessmentPeriod, discountFactors, serviceLifePerOccurence)
                    }
                    calculationResult = new CalculationResult()
                    calculationResult.indicatorId = indicatorId
                    calculationResult.resultCategoryId = resultCategory.resultCategoryId
                    calculationResult.result = dataset.calculatedQuantity * occurrencePeriods.size()
                    calculationResult.calculationFormula = "${dataset.preResultFormula},<b>Recurring maintenance:</b> ${calculationResult.result.round(2)} = ${dataset.calculatedQuantity.round(2)} * ${occurrencePeriods.size()} (periods of occurrence) (${resultCategory.resultCategoryId})"
                    calculationResult.resultWithoutOccurrencePeriods = dataset.calculatedQuantity
                    calculationResult.calculationResourceId = resource.resourceId
                    calculationResult.calculationProfileId = resource.profileId ? resource.profileId : dataset.calculationProfileId
                    calculationResult.originalResourceId = resource.resourceId
                    calculationResult.originalProfileId = resource.profileId
                    calculationResult.datasetIds = [dataset.manualId]
                    calculationResult.unit = dataset.userGivenUnit
                    calculationResult.calculatedUnit = dataset.calculatedUnit
                    calculationResult.datasetAdditionalQuestionAnswers = dataset.additionalQuestionAnswers



                    if (!resultPerOccurencePeriod.isEmpty()) {
                        calculationResult.resultPerOccurencePeriod = resultPerOccurencePeriod
                    }

                    if (resource.maintenanceResourceId) {
                        calculationResult.calculationResourceId = resource.maintenanceResourceId
                    }
                    applyLocalCompensation(dataset, calculationResult)
                    //annie - 15336
                    if(discountedRecurrences) {
                        recurrenceMultipliers.each { Integer recurrency ->
                            if(discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) {
                                resultWithDiscountPerOccurencePeriod.put(serviceLifePerOccurence.get(recurrency).toString(), ((discountedRecurrences?.get(serviceLifePerOccurence?.get(recurrency))) * dataset?.calculatedQuantity))
                            }
                        }
                        //annie
                        calculationResult.resultWithDiscountPerOccurencePeriod = resultWithDiscountPerOccurencePeriod
                    }
                   //annie - 15336
                    if(resultWithDiscountPerOccurencePeriod) {
                        calculationResult.calculatedDiscountFactor = discountedRecurrences?.values().sum()
                        calculationResult.discountedResult = resultWithDiscountPerOccurencePeriod?.values().sum()
                    }
                }
            }
        }
        return calculationResult
    }

    /**
     * Calculates local compensation multiplier for the row.
     *
     * @param entity
     * @param resultCategory
     * @param calculationResult
     * @param resourceImpact
     * @param originalResource
     *          - the resource being iterated on (e.g steel beam)
     * @param localCompensationCountryResource
     *          - Target country resource, gotten from method: datasetForCompensateEnergyMix which uses compensateEnergyMix
     *            from the resultsCats and parentEntity, which I think is stored from the project params query
     * @param originalFactor
     *          - This is the calculated impact from the resource, created in method: resolveCalculationRuleMultiplier
     * @param projectCountryResource
     *          - This is the country resource saved in the parent entity (e.g unitedKingdom), its id is saved and checked
     *            against the list of resources in localCompAreaResources and passed down from method: calculateCalculationRuleImpact
     * @param localCompensationMethodVersion
     *          - Gets datasets from parent entity and finds localCompensationMethodVersion and converts the answer to int,
     *            (e.g "0" - disabled, "10" - v1, "20" -v2) collected in method: calculateCalculationRuleImpact
     * @param localCompAreaResources
     *          - This is a list of every country in the world a individual resources
     * @param calculationRule
     *          - The current calculation rule being calculated, this whole method is inside a large loop.
     * @param defaultEnergyProfileResourceId
     *          - This is the selected energy profile resourceId (e.g averageElectricityUnitedKingdomEPD) in project
     *            parameters, collected in method: calculateCalculationRuleImpact
     * @param defaultEnergyProfileProfileId
     *          - This is the selected energy profile profileId (e.g IEA2017) in project parameters,
     *            collected in method: calculateCalculationRuleImpact
     * @param rowByRowLocalCompsLicensed
     *          - Checks parentEntity license for expert feature, collected in method: calculateCalculationRuleImpact
     * @return
     */
    // TODO: no any possibility to pre-fetch energy resource before this method. If complexity for that stuff was decreased, please do it.
    private Map<String, Object> newEnergyMixCompensation(Entity entity, ResultCategory resultCategory, CalculationResult calculationResult,
                                                         String resourceImpact, Resource originalResource, Resource localCompensationCountryResource,
                                                         Double originalFactor, Resource projectCountryResource, Integer localCompensationMethodVersion,
                                                         List<Resource> localCompAreaResources, CalculationRule calculationRule, String defaultEnergyProfileResourceId,
                                                         String defaultEnergyProfileProfileId, Boolean rowByRowLocalCompsLicensed, Boolean debugLicenseFeature, ResourceCache resourceCache) {
        String compensationExplanation = ""
        Double compensatedImpactFactor
        ResourceType originalResourceType = resourceCache.getResourceTypeCache().getResourceType(originalResource)
        Boolean usingNewLocalComps = (localCompensationMethodVersion && localCompensationMethodVersion > 10)
        Boolean disabledLocalComps = localCompensationMethodVersion == 0
        Double thickness = usingNewLocalComps ? originalResource.defaultThickness_mm : calculationResult?.getThickness()
        String ruleUnit = calculationRule.unit?.get('EN')

        if (entity && resultCategory && resourceImpact && originalResourceType && originalResource && originalFactor && calculationResult.applyLocalComps) {
            String area

            Resource targetCountryResource
            if (resourceService.getIsLocalResource(originalResource.areas)) {
                // calculationResult.localCompsCountryResourceId seems to be populated from a dataset and answer in applyLocalComp, so could be the selected answer saved in project params, if so, what does that make localCompensationCountryResource
                targetCountryResource = calculationResult.localCompsCountryResourceId ? localCompAreaResources.find({calculationResult.localCompsCountryResourceId.equals(it.resourceId)}) : localCompensationCountryResource? localCompensationCountryResource : projectCountryResource
                area = originalResource.representativeArea
                if(debugLicenseFeature){
                    compensationExplanation = "${compensationExplanation}, <b>Local resource code:</b>"
                    compensationExplanation = "${compensationExplanation}, <b>Is resource local: </b>${resourceService.getIsLocalResource(originalResource.areas)}"
                    compensationExplanation = "${compensationExplanation}, <b>Disabled local comp: </b>${disabledLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>Using v1: </b>${!disabledLocalComps && !usingNewLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>Using v2: </b>${usingNewLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>--------------------------------------------</b>"
                    compensationExplanation = "${compensationExplanation}, <b>calculationResult.localCompsCountryResourceId: </b>${calculationResult.localCompsCountryResourceId}"
                    compensationExplanation = "${compensationExplanation}, <b>local Compensation Country Resource: </b>${localCompensationCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>Project Country Resource: </b>${projectCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>--------------------------------------------</b>"
                    compensationExplanation = "${compensationExplanation}, <b>Target country resource: </b>${targetCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>Area: </b>${originalResource.representativeArea}"
                }
            } else {
                if (!disabledLocalComps || (disabledLocalComps && rowByRowLocalCompsLicensed)) {
                    targetCountryResource = calculationResult.localCompsCountryResourceId ? localCompAreaResources.find({calculationResult.localCompsCountryResourceId.equals(it.resourceId)}) : localCompensationCountryResource
                }

                if (targetCountryResource && originalResource.areas && !originalResource.areas.contains(targetCountryResource.resourceId)) {
                    area = originalResource.areas.first()
                }

                if(debugLicenseFeature){
                    compensationExplanation = "${compensationExplanation}, <b>Non-local resource code:</b>"
                    compensationExplanation = "${compensationExplanation}, <b>Disabled local comp: </b>${disabledLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>Using v1: </b>${!disabledLocalComps && !usingNewLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>Using v2: </b>${usingNewLocalComps}"
                    compensationExplanation = "${compensationExplanation}, <b>--------------------------------------------</b>"
                    compensationExplanation = "${compensationExplanation}, <b>calculationResult.localCompsCountryResourceId: </b>${calculationResult.localCompsCountryResourceId}"
                    compensationExplanation = "${compensationExplanation}, <b>local Compensation Country Resource: </b>${localCompensationCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>project Country Resource: </b>${projectCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>--------------------------------------------</b>"
                    compensationExplanation = "${compensationExplanation}, <b>originalResource.areas: ${originalResource.areas}</b>"
                    compensationExplanation = "${compensationExplanation}, <b>og.Areas doesn't match target resource: ${!originalResource.areas?.contains(targetCountryResource?.resourceId)}</b>"
                    compensationExplanation = "${compensationExplanation}, <b>--------------------------------------------</b>"
                    compensationExplanation = "${compensationExplanation}, <b>Target country resource: </b>${targetCountryResource?.nameEN}"
                    compensationExplanation = "${compensationExplanation}, <b>Area: </b>${area}"
                }
            }


            if (targetCountryResource && area && !area.equals(targetCountryResource.resourceId)) {
                Double electricityKwh
                String unit

                if (usingNewLocalComps) {
                    electricityKwh = originalResourceType.requiredElectricityKwhPerKg // always in kilograms
                    unit = "kg"
                } else {
                    electricityKwh = originalResourceType.requiredElectricityKwh
                    unit = originalResourceType?.standardUnit?.toLowerCase()
                }

                //log.info("RequiredElectricityKwh for resource ${originalResource.resourceId} before conversion: ${electricityKwh}. Using unit: ${unit}, Thickness: ${thickness}")
                Double requiredElectricityKwh = unitConversionUtil.doConversion(electricityKwh, thickness, unit, originalResource, Boolean.TRUE, null, Boolean.TRUE)

                if (electricityKwh && !requiredElectricityKwh && originalResource.massConversionFactor && usingNewLocalComps) {
                    if (originalResource.unitForData == "kg") {
                        requiredElectricityKwh = electricityKwh / originalResource.massConversionFactor
                    } else {
                        requiredElectricityKwh = electricityKwh * originalResource.massConversionFactor
                    }
                }
                //log.info("RequiredElectricityKwh for resource ${originalResource.resourceId} after conversion: ${requiredElectricityKwh}")
                Resource originalCountryResource = localCompAreaResources.find({area.equals(it.resourceId)})
                if (originalCountryResource && requiredElectricityKwh) {
                    Resource targetEnergyResource
                    if ("incineration".equals(calculationResult.eolProcessingType)) {
                        targetEnergyResource = resourceCache.getCountryIncinerationResource(projectCountryResource)
                    } else {
                        if (rowByRowLocalCompsLicensed) {
                            // if row by row available, get energy profile from dataset or default from LCA parameters
                            if (calculationResult.localCompsCountryEnergyResourceId && calculationResult.localCompsCountryEnergyProfileId) {
                                targetEnergyResource = resourceCache.getResource(calculationResult.localCompsCountryEnergyResourceId, calculationResult.localCompsCountryEnergyProfileId, true, true)
                                if(debugLicenseFeature){
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource - gotten from calculation result :</b>"
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                    compensationExplanation = "${compensationExplanation}, <b>localCompsCountryEnergyResourceId: </b>${calculationResult.localCompsCountryEnergyResourceId}"
                                    compensationExplanation = "${compensationExplanation}, <b>localCompsCountryEnergyProfileId: </b>${calculationResult.localCompsCountryEnergyProfileId}"
                                }
                            } else if (defaultEnergyProfileResourceId && defaultEnergyProfileProfileId) {
                                targetEnergyResource = resourceCache.getResource(defaultEnergyProfileResourceId, defaultEnergyProfileProfileId, true, true)
                                if(debugLicenseFeature){
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource - gotten from defaultEnergyProfile (selected in project params) :</b>"
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                    compensationExplanation = "${compensationExplanation}, <b>defaultEnergyProfileResourceId: </b>${defaultEnergyProfileResourceId}"
                                    compensationExplanation = "${compensationExplanation}, <b>defaultEnergyProfileProfileId: </b>${defaultEnergyProfileProfileId}"
                                }
                            } else {
                                if (usingNewLocalComps) {
                                    targetEnergyResource = resourceCache.getResource(targetCountryResource.countryEnergyResourceId, targetCountryResource.countryEnergyResourceProfileId_v2, true, true)
                                    if(debugLicenseFeature){
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource - Using v2 :</b>"
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceId: </b>${targetCountryResource.countryEnergyResourceId}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceProfileId_v2: </b>${targetCountryResource.countryEnergyResourceProfileId_v2}"
                                    }
                                } else {
                                    targetEnergyResource = resourceCache.getResource(targetCountryResource.countryEnergyResourceId, targetCountryResource.countryEnergyResourceProfileId, true, true)
                                    if(debugLicenseFeature){
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource - Using v1 :</b>"
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceId: </b>${targetCountryResource.countryEnergyResourceId}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceProfileId: </b>${targetCountryResource.countryEnergyResourceProfileId}"
                                    }
                                }
                            }
                        } else {
                            if (calculationResult.localCompsCountryEnergyResourceId && calculationResult.localCompsCountryEnergyProfileId) {
                                targetEnergyResource = resourceCache.getResource(calculationResult.localCompsCountryEnergyResourceId, calculationResult.localCompsCountryEnergyProfileId, true, true)
                                if(debugLicenseFeature){
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource - gotten from calculation result :</b>"
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                    compensationExplanation = "${compensationExplanation}, <b>localCompsCountryEnergyResourceId: </b>${calculationResult.localCompsCountryEnergyResourceId}"
                                    compensationExplanation = "${compensationExplanation}, <b>localCompsCountryEnergyProfileId: </b>${calculationResult.localCompsCountryEnergyProfileId}"
                                }
                            } else if (defaultEnergyProfileResourceId && defaultEnergyProfileProfileId) {
                                targetEnergyResource = resourceCache.getResource(defaultEnergyProfileResourceId, defaultEnergyProfileProfileId, true, true)
                                if(debugLicenseFeature){
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource - gotten from defaultEnergyProfile (selected in project params) :</b>"
                                    compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                    compensationExplanation = "${compensationExplanation}, <b>defaultEnergyProfileResourceId: </b>${defaultEnergyProfileResourceId}"
                                    compensationExplanation = "${compensationExplanation}, <b>defaultEnergyProfileProfileId: </b>${defaultEnergyProfileProfileId}"
                                }
                            } else {
                                if (usingNewLocalComps) {
                                    targetEnergyResource = resourceCache.getResource(targetCountryResource.countryEnergyResourceId, targetCountryResource.countryEnergyResourceProfileId_v2, true, true)
                                    if(debugLicenseFeature){
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource - Using v2 :</b>"
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceId: </b>${targetCountryResource.countryEnergyResourceId}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceProfileId_v2: </b>${targetCountryResource.countryEnergyResourceProfileId_v2}"
                                    }
                                } else {
                                    targetEnergyResource = resourceCache.getResource(targetCountryResource.countryEnergyResourceId, targetCountryResource.countryEnergyResourceProfileId, true, true)
                                    if(debugLicenseFeature){
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource - Using v1 :</b>"
                                        compensationExplanation = "${compensationExplanation}, <b>Target energy resource: </b>${targetEnergyResource?.nameEN}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceId: </b>${targetCountryResource.countryEnergyResourceId}"
                                        compensationExplanation = "${compensationExplanation}, <b>targetCountryResource.countryEnergyResourceProfileId: </b>${targetCountryResource.countryEnergyResourceProfileId}"
                                    }
                                }
                            }
                        }
                    }
                    Resource originalEnergyResource = resourceCache.getCountryEnergyResource(originalCountryResource, usingNewLocalComps)
                    if(debugLicenseFeature){
                        compensationExplanation = "${compensationExplanation}, <b>originalEnergyResource: </b>${originalEnergyResource?.nameEN}"
                    }

                    if (targetEnergyResource && originalEnergyResource) {
                        Double targetElectricityImpact = getCalculationImpactFromResource(resourceImpact, targetEnergyResource, resultCategory)
                        Double originalElectricityImpact = getCalculationImpactFromResource(resourceImpact, originalEnergyResource, resultCategory)

                        if (targetElectricityImpact && originalElectricityImpact) {
                            Double targetCountryEffiencyNumber = targetCountryResource.getCountryEffiencyNumber()
                            Double localCountryEffiencyNumber = originalCountryResource.getCountryEffiencyNumber()

                            if (!targetCountryEffiencyNumber) {
                                loggerUtil.warn(log, "newEnergyMixCompensation: targetCountryResource ${targetCountryResource.resourceId} / ${targetCountryResource.profileId} is missing countryEnergyEfficiency, defaulting to NORMAL")
                                flashService.setWarningAlert("newEnergyMixCompensation: targetCountryResource ${targetCountryResource.resourceId} / ${targetCountryResource.profileId} is missing countryEnergyEfficiency, defaulting to NORMAL", true)
                                targetCountryEffiencyNumber = 1D
                            }

                            if (!localCountryEffiencyNumber) {
                                loggerUtil.warn(log, "newEnergyMixCompensation: originalCountryResource ${targetCountryResource.resourceId} / ${targetCountryResource.profileId} is missing countryEnergyEfficiency, defaulting to NORMAL")
                                flashService.setWarningAlert("newEnergyMixCompensation: originalCountryResource ${targetCountryResource.resourceId} / ${targetCountryResource.profileId} is missing countryEnergyEfficiency, defaulting to NORMAL", true)
                                localCountryEffiencyNumber = 1D
                            }
                            compensatedImpactFactor = ((localCountryEffiencyNumber * originalElectricityImpact) - (targetCountryEffiencyNumber * targetElectricityImpact)) * requiredElectricityKwh

                            if (compensatedImpactFactor && originalFactor) {
                                if (usingNewLocalComps) {
                                    Double min = null
                                    Double max = null

                                    if (originalResourceType.subTypeMinImpacts_kg) {
                                        min = unitConversionUtil.doConversion(originalResourceType.subTypeMinImpacts_kg, thickness, unit, originalResource, Boolean.TRUE, null, Boolean.TRUE)

                                        if (!min && originalResource.massConversionFactor) {
                                            if (originalResource.unitForData == "kg") {
                                                min = originalResourceType.subTypeMinImpacts_kg / originalResource.massConversionFactor
                                            } else {
                                                min = originalResourceType.subTypeMinImpacts_kg * originalResource.massConversionFactor
                                            }
                                        }
                                    }

                                    if (originalResourceType.subTypeMaxImpacts_kg) {
                                        max = unitConversionUtil.doConversion(originalResourceType.subTypeMaxImpacts_kg, thickness, unit, originalResource, Boolean.TRUE, null, Boolean.TRUE)

                                        if (!max && originalResource.massConversionFactor) {
                                            if (originalResource.unitForData == "kg") {
                                                max = originalResourceType.subTypeMaxImpacts_kg / originalResource.massConversionFactor
                                            } else {
                                                max = originalResourceType.subTypeMaxImpacts_kg * originalResource.massConversionFactor
                                            }
                                        }
                                    }

                                    if(debugLicenseFeature){
                                        compensationExplanation = "${compensationExplanation}, <b>Local compensation v2 parameters:</b>"
                                        compensationExplanation = "${compensationExplanation}, <b>Original country efficiency multiplier: </b>${localCountryEffiencyNumber} (${originalCountryResource?.resourceId} / ${originalCountryResource?.profileId})"
                                        compensationExplanation = "${compensationExplanation}, <b>Original country electricity impact: </b>${originalElectricityImpact} (${originalEnergyResource?.resourceId} / ${originalEnergyResource?.profileId})"
                                        compensationExplanation = "${compensationExplanation}, <b>Target country efficiency multiplier: </b>${targetCountryEffiencyNumber} (${targetCountryResource?.resourceId} / ${targetCountryResource?.profileId})"
                                        compensationExplanation = "${compensationExplanation}, <b>Target country electricity impact: </b>${targetElectricityImpact} (${targetEnergyResource?.resourceId} / ${targetEnergyResource?.profileId})"
                                        compensationExplanation = "${compensationExplanation}, <b>Original compensation factor: </b>${compensatedImpactFactor}"
                                        compensationExplanation = "${compensationExplanation}, <b>Original impact before compensation: </b>${originalFactor}"
                                        compensationExplanation = "${compensationExplanation}, <b>Max compensation share: </b>${originalResourceType.localCompensationShareLimit} (${originalResourceType?.nameEN?.replaceAll(",","")})"
                                        if(min){
                                            compensationExplanation = "${compensationExplanation}, <b>MIN natural limit</b>: ${min} (from subtype)."
                                        }
                                        if(max){
                                            compensationExplanation = "${compensationExplanation}, <b>MAX natural limit</b>: ${max} (from subtype)."
                                        }
                                    } else {
                                        compensationExplanation = "${compensationExplanation}, <b>Original compensation factor: </b>${compensatedImpactFactor}"
                                        compensationExplanation = "${compensationExplanation}, <b>Original impact before compensation: </b>${originalFactor}"
                                    }


                                    String subExplaination = ""
                                    if (originalResourceType.localCompensationShareLimit) {
                                        Double maximumAllowedCompensationDifference = ((localCountryEffiencyNumber * originalElectricityImpact) - (targetCountryEffiencyNumber * targetElectricityImpact)) / (localCountryEffiencyNumber * originalElectricityImpact) * originalFactor * originalResourceType.localCompensationShareLimit

                                        compensationExplanation = debugLicenseFeature ? "${compensationExplanation}, <b>Maximum allowed compensation difference: </b> ${maximumAllowedCompensationDifference}" : "${compensationExplanation}"


                                        if (Math.abs(compensatedImpactFactor) > Math.abs(maximumAllowedCompensationDifference)) {
                                            subExplaination = ", <b>Compensated impact factor: ${roundingNumber(originalFactor - maximumAllowedCompensationDifference)} ${ruleUnit? ruleUnit:''}</b>. Over compensated factor adjusted. Calculated from: (${roundingNumber(originalFactor)} - ${roundingNumber(maximumAllowedCompensationDifference)}) ${ruleUnit? ruleUnit:''}"
                                            compensatedImpactFactor = originalFactor - maximumAllowedCompensationDifference
                                        } else {
                                            subExplaination = ", <b>Compensated impact factor: ${roundingNumber((originalFactor - compensatedImpactFactor))} ${ruleUnit? ruleUnit:''}</b>. Calculated from: (${roundingNumber(originalFactor)} - ${roundingNumber(compensatedImpactFactor)}) ${ruleUnit}"
                                            compensatedImpactFactor = originalFactor - compensatedImpactFactor
                                        }
                                    } else {
                                        subExplaination = ", <b>Compensated impact factor: ${roundingNumber((originalFactor - compensatedImpactFactor))} ${ruleUnit? ruleUnit:''}</b>. Calculated from: (${roundingNumber(originalFactor)} - ${roundingNumber(compensatedImpactFactor)}) ${ruleUnit}"
                                        compensatedImpactFactor = originalFactor - compensatedImpactFactor
                                    }
                                    Boolean hideCompensatedImpact = Boolean.FALSE
                                    if((min && compensatedImpactFactor < min) || (max && compensatedImpactFactor > max)){
                                        hideCompensatedImpact = Boolean.TRUE
                                    }

                                    if (hideCompensatedImpact) {
                                        compensatedImpactFactor = null
                                        compensationExplanation = "${compensationExplanation}, <b>Compensated impact factor: ${roundingNumber(originalFactor)} ${ruleUnit? ruleUnit:''}</b>. Compensated impact exceeded natural limits, so localization method was not applied."
                                    } else {
                                        compensationExplanation = "${compensationExplanation}${subExplaination}"

                                    }
                                } else {
                                    double percentageChange = Math.abs((compensatedImpactFactor / originalFactor * 100).doubleValue())

                                    if (percentageChange > Math.abs((((localCountryEffiencyNumber * originalElectricityImpact) - (targetCountryEffiencyNumber * targetElectricityImpact)) / originalElectricityImpact * 100).doubleValue())) {
                                        compensatedImpactFactor = ((localCountryEffiencyNumber * originalElectricityImpact) - (targetCountryEffiencyNumber * targetElectricityImpact)) / originalElectricityImpact * originalFactor
                                        compensationExplanation = "${compensationExplanation},<b>Compensation factor: ${roundingNumber(compensatedImpactFactor)} ${ruleUnit? ruleUnit:''}</b>. Over compensated factor adjusted.,<b>Compensated impact: ${roundingNumber(originalFactor) - roundingNumber(compensatedImpactFactor)} ${ruleUnit? ruleUnit:''}</b>. Calculated from: ${roundingNumber(originalFactor)} - ${roundingNumber(compensatedImpactFactor)} ${ruleUnit? ruleUnit:''}"
                                    } else {
                                        compensationExplanation = "${compensationExplanation},<b>Compensation factor: ${roundingNumber(compensatedImpactFactor)} ${ruleUnit? ruleUnit:''}</b>. Calculated from: ((${roundingNumber(localCountryEffiencyNumber)} * ${roundingNumber(originalElectricityImpact)}) - (${roundingNumber(targetCountryEffiencyNumber)} * ${roundingNumber(targetElectricityImpact)})) * ${roundingNumber(requiredElectricityKwh)},<b>Compensated impact: ${roundingNumber(originalFactor) - roundingNumber(compensatedImpactFactor)} ${ruleUnit? ruleUnit:''}</b>. Calculated from: ${roundingNumber(originalFactor)} - ${roundingNumber(compensatedImpactFactor)} ${ruleUnit?ruleUnit:''} "
                                    }
                                }
                            }
                        }
                    }
                } else {

                    if (!targetCountryResource||!originalCountryResource) {
                        String errorMessage = ""
                        if (!targetCountryResource) {
                            errorMessage = errorMessage ? "${errorMessage}, targetCountryResource not found" : "newEnergyMixCompensation: targetCountryResource not found"
                        }

                        if (!originalCountryResource) {
                            errorMessage = errorMessage ? "${errorMessage}, originalCountryResource not found for resourceId: ${area}" : "newEnergyMixCompensation: originalCountryResource not found for resourceId: ${area}"
                        }
                        loggerUtil.error(log,"${errorMessage}, Compensation failed!")
                        flashService.setErrorAlert("${errorMessage}, Compensation failed!", true)
                    }

                    if (!requiredElectricityKwh && originalResourceType?.resourceType != "buildingTechnology") {
                        log.info("Local compenstaion: ${originalResourceType?.resourceType} / ${originalResourceType?.subType} is missing requiredElectricityKwh")
                        loggerUtil.warn(log,"Local compenstaion: ${originalResourceType?.resourceType} / ${originalResourceType?.subType} is missing requiredElectricityKwh")
                        flashService.setWarningAlert("Local compenstaion: ${originalResourceType?.resourceType} / ${originalResourceType?.subType} is missing requiredElectricityKwh", true)
                    }
                }
            }
        }

        if (usingNewLocalComps) {
            compensatedImpactFactor = compensatedImpactFactor != null ? compensatedImpactFactor : originalFactor
        } else {
            compensatedImpactFactor = compensatedImpactFactor != null ? originalFactor - compensatedImpactFactor : originalFactor
        }
        return [compensatedImpactFactor: compensatedImpactFactor, compensationExplanation: compensationExplanation]
    }

    private Double getCalculationImpactFromResource(String parameter, Resource resource, ResultCategory resultCategory, Boolean useEolProcessStage = null) {
        Double value

        if (parameter && resource && resultCategory) {
            if (resultCategory.eolProcessStagesSum && useEolProcessStage) {
                for (String stage in resultCategory.eolProcessStagesSum) {
                    Map stageValues = resource.impacts?.get(stage)

                    if (stageValues) {
                        Double stageValue = (Double) stageValues.get(parameter)

                        if (stageValue) {
                            if (value) {
                                value = value + stageValue
                            } else {
                                value = stageValue
                            }
                        }
                    }
                }

                if (!value) {
                    value = 0D
                }
            } else if (resultCategory.eolProcessStages && useEolProcessStage) {
                for (String stage in resultCategory.eolProcessStages) {
                    Map stageValues = resource.impacts?.get(stage)

                    if (stageValues) {
                        value = (Double) stageValues.get(parameter)
                    }

                    if (value) {
                        break
                    }
                }

                if (!value) {
                    value = 0D
                }
            } else {
                List<String> stages = resultCategory?.applyStages
                boolean impactParameterOk = Resource.allowedImpactCategories.contains(parameter) ? true : false
                List<String> processNames = resultCategory?.process?.keySet()?.toList()
                boolean useBenefitProcessingFailOver = false
                boolean useWasteProcessingFailOver = false

                if (processNames) {
                    if (processNames.contains(Constants.QueryCalculationProcess.BENEFIT.toString())) {
                        useBenefitProcessingFailOver = true
                    }

                    if (processNames.contains(Constants.QueryCalculationProcess.WASTE.toString())) {
                        useWasteProcessingFailOver = true
                    }
                }
                boolean useStage = stages ? true : false

                if (stages && (!resource.impacts || !CollectionUtils.containsAny(resource.impacts?.keySet()?.toList(), stages)) && (useBenefitProcessingFailOver || useWasteProcessingFailOver)) {
                    useStage = false
                }

                if (useStage && impactParameterOk) {
                    for (String stage in stages) {
                        if (Resource.allowedStages.contains(stage)) {
                            Map stageValues = resource.impacts?.get(stage)

                            if (stageValues) {
                                value = (Double) stageValues.get(parameter)

                                if (value != null) {
                                    // loggerUtil.info(log, "Found value ${value} for resource ${resource.resourceId} / ${resource.profileId} using stage ${stage}, impact ${parameter}")
                                    break
                                } else {
                                    // loggerUtil.warn(log, "No impact category ${parameter} found for stage ${stage} for resource ${resource.resourceId} / ${resource.profileId}")
                                }
                            }
                        } else {
                            loggerUtil.error(log, "Found not allowed stage ${stage} from resultCategory ${resultCategory.resultCategory}")
                            flashService.setErrorAlert("Found not allowed stage ${stage} from resultCategory ${resultCategory.resultCategory}", true)
                        }
                    }
                } else {
                    if ("shadowPriceGetter".equals(parameter) && useStage) {
                        Double shadowPrice
                        for (String stage in stages) {
                            if (Resource.allowedStages.contains(stage)) {
                                shadowPrice = resource.getShadowPriceForStage(stage)

                                if (shadowPrice) {
                                    break
                                }
                            } else {
                                loggerUtil.error(log, "Found not allowed stage ${stage} from resultCategory ${resultCategory.resultCategory}")
                                flashService.setErrorAlert("Found not allowed stage ${stage} from resultCategory ${resultCategory.resultCategory}", true)
                            }
                        }

                        if (shadowPrice != null) {
                            value = shadowPrice
                        }
                    } else {
                        String originalValue

                        if (useStage) {
                            originalValue = getAnyParameterFromResource(parameter, resource, stages)?.toString()
                        } else {
                            originalValue = getAnyParameterFromResource(parameter, resource)?.toString()
                        }

                        if (originalValue != null && DomainObjectUtil.isNumericValue(originalValue)) {
                            value = DomainObjectUtil.convertStringToDouble(originalValue)
                        }
                    }
                }
            }
        }
        // Apply energy compensation
        return value
    }

    private Double getSumImpactFromResource(String multiplierName, Resource resource, ResultCategory resultCategory) {
        Double impact

        if (multiplierName && resource && resultCategory) {
            if ("nonRenewablesUsedTotal_MJ_SUM".equals(multiplierName)) {
                Double nonRenewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsEnergy_MJ", resource, resultCategory)
                Double nonRenewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsMaterial_MJ", resource, resultCategory)
                impact = (nonRenewablesUsedAsEnergy_MJ != null ? nonRenewablesUsedAsEnergy_MJ : 0) +
                        (nonRenewablesUsedAsMaterial_MJ != null ? nonRenewablesUsedAsMaterial_MJ : 0)
            } else if ("allEnergyUsedTotal_MJ_SUM".equals(multiplierName)) {
                Double nonRenewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsEnergy_MJ", resource, resultCategory)
                Double nonRenewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsMaterial_MJ", resource, resultCategory)
                Double renewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("renewablesUsedAsMaterial_MJ", resource, resultCategory)
                Double renewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("renewablesUsedAsEnergy_MJ", resource, resultCategory)
                impact = (nonRenewablesUsedAsEnergy_MJ != null ? nonRenewablesUsedAsEnergy_MJ : 0) +
                        (nonRenewablesUsedAsMaterial_MJ != null ? nonRenewablesUsedAsMaterial_MJ : 0) +
                        (renewablesUsedAsMaterial_MJ != null ? renewablesUsedAsMaterial_MJ : 0) +
                        (renewablesUsedAsEnergy_MJ != null ? renewablesUsedAsEnergy_MJ : 0)
            } else if ("allEnergyUsedTotal_kWh_SUM".equals(multiplierName)) {
                impact = resource.allEnergyUsedTotal_kWh_SUM
            } else if ("renewablesUsedTotal_MJ_SUM".equals(multiplierName)) {
                Double renewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("renewablesUsedAsMaterial_MJ", resource, resultCategory)
                Double renewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("renewablesUsedAsEnergy_MJ", resource, resultCategory)
                impact = (renewablesUsedAsMaterial_MJ != null ? renewablesUsedAsMaterial_MJ : 0) +
                        (renewablesUsedAsEnergy_MJ != null ? renewablesUsedAsEnergy_MJ : 0)
            } else if ("primaryEnergy_MJ_SUM".equals(multiplierName)) {
                Double nonRenewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsEnergy_MJ", resource, resultCategory)
                Double renewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("renewablesUsedAsEnergy_MJ", resource, resultCategory)
                impact = (nonRenewablesUsedAsEnergy_MJ != null ? nonRenewablesUsedAsEnergy_MJ : 0) +
                        (renewablesUsedAsEnergy_MJ != null ? renewablesUsedAsEnergy_MJ : 0)
            } else if ("totalWaste_kg_SUM".equals(multiplierName)) {
                Double wasteHazardous_kg = getCalculationImpactFromResource("wasteHazardous_kg", resource, resultCategory)
                Double wasteNonHazardous_kg = getCalculationImpactFromResource("wasteNonHazardous_kg", resource, resultCategory)
                Double wasteRadioactive_kg = getCalculationImpactFromResource("wasteRadioactive_kg", resource, resultCategory)
                Double wasteRadioactiveHigh_kg = getCalculationImpactFromResource("wasteRadioactiveHigh_kg", resource, resultCategory)
                impact = (wasteHazardous_kg != null ? wasteHazardous_kg : 0) +
                        (wasteNonHazardous_kg != null ? wasteNonHazardous_kg : 0) +
                        (wasteRadioactive_kg != null ? wasteRadioactive_kg : 0) +
                        (wasteRadioactiveHigh_kg != null ? wasteRadioactiveHigh_kg : 0)
            } else if ("energyResourcesUsedTotal_MJ_SUM") {
                Double nonRenewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsEnergy_MJ", resource, resultCategory)
                Double nonRenewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("nonRenewablesUsedAsMaterial_MJ", resource, resultCategory)
                Double renewablesUsedAsEnergy_MJ = getCalculationImpactFromResource("renewablesUsedAsEnergy_MJ", resource, resultCategory)
                Double renewablesUsedAsMaterial_MJ = getCalculationImpactFromResource("renewablesUsedAsMaterial_MJ", resource, resultCategory)
                impact = (nonRenewablesUsedAsEnergy_MJ ? nonRenewablesUsedAsEnergy_MJ : 0) +
                        (nonRenewablesUsedAsMaterial_MJ ? nonRenewablesUsedAsMaterial_MJ : 0) +
                        (renewablesUsedAsEnergy_MJ ? renewablesUsedAsEnergy_MJ : 0) +
                        (renewablesUsedAsMaterial_MJ ? renewablesUsedAsMaterial_MJ : 0)
            }
        }
        return impact
    }

    /**
     * Finds final (non-virtual) category ids recursively
     *
     * @param virtualCategories - a {@link List} of virtual categories that have children
     * @param targetIds - a {@link Set} of target ids to look up
     * @param virtualCategoryIds - an optional {@link Set} of virtual category ids in order not to filter several time
     * @return a {@link Set} of final category ids
     */
    @CompileStatic
    Set<String> getFinalCategoryIds(List<ResultCategory> virtualCategories, Set<String> targetIds, Set<String> virtualCategoryIds = []) {
        Set<String> result = new HashSet()
        if(!virtualCategories) {
            return result
        }
        if(!virtualCategoryIds) {
            virtualCategoryIds.addAll(virtualCategories.collect{ it.resultCategoryId })
        }
        for(String targetId : targetIds) {
            if(!virtualCategoryIds.contains(targetId)) {
                result.add(targetId)
            }
        }
        for(ResultCategory cat : virtualCategories) {
            if(targetIds.contains(cat.resultCategoryId) && cat.virtual) {
                result.addAll(getFinalCategoryIds(virtualCategories, new HashSet<String>(cat.virtual), virtualCategoryIds))
            }
        }
        return result
    }

    private void useResultManipulatorForResult(ResultManipulator resultManipulator, Entity entity, List<ResultCategory> virtualCategories,
                                               CalculationResult calculationResult, Indicator indicator, String unitToDisplay,
                                               List<CalculationResult> alreadyCalculatedResults, ResourceCache resourceCache, Boolean useCalculationRule = Boolean.FALSE,
                                               Boolean resultCategory = Boolean.FALSE, String calculateForUniqueAnswersOnly = null,
                                               Boolean allowUnassignedZone = Boolean.FALSE, Double setMinValue = null, Double setMaxValue = null) {
        Long start = new Date().getTime()
        List<ValueReference> multiplierList = resultManipulatorService.getMultipliersAsValueReferences(resultManipulator)
        List<ValueReference> dividerList = resultManipulatorService.getDividersAsValueReferences(resultManipulator)
        Double indicatorFixedMultiplier
        Double indicatorValueReferenceMultiplier
        Double finalMultiplier = resultManipulator.fixedMultiplier
        Double finalDivider
        boolean calculationOk = true
        Set<String> dividerCategoryIds = new HashSet<>()
        if(resultManipulator.divideByCategory && virtualCategories) {
            dividerCategoryIds = getFinalCategoryIds(virtualCategories, Set.of(resultManipulator.divideByCategory))
            if(log.isTraceEnabled()) {
                log.trace("Found divider categories: " + dividerCategoryIds)
            }
        }

        if(resultManipulator.divideByCategory && !dividerCategoryIds) {
            dividerCategoryIds.add(resultManipulator.divideByCategory)
        }

        if (calculationResult && calculationResult.result != null) {
            if (resultManipulator.multiplyWithAssessmentPeriod || resultManipulator.multiplyWSqrtOfAssessmentPeriod) {
                if (indicator?.assessmentPeriodFixed) {
                    indicatorFixedMultiplier = indicator.assessmentPeriodFixed
                    if(resultManipulator.multiplyWSqrtOfAssessmentPeriod && indicatorFixedMultiplier != null) {
                        indicatorFixedMultiplier = Math.sqrt(indicatorFixedMultiplier)
                    }
                }

                if (indicator?.assessmentPeriodValueReference) {
                    indicatorValueReferenceMultiplier = valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, entity,
                            null, null, resourceCache)
                    if(resultManipulator.multiplyWSqrtOfAssessmentPeriod && indicatorValueReferenceMultiplier != null) {
                        indicatorValueReferenceMultiplier = Math.sqrt(indicatorValueReferenceMultiplier)
                    }
                }
            }

            Map<String, Double> multiplierCache = new HashMap<>()
            Map<String, Double> dividerCache = new HashMap<>()

            if (resultManipulator.multiplyWithAssessmentPeriod && !indicatorFixedMultiplier && !indicatorValueReferenceMultiplier) {
                calculationResult.result = 0
                calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                    if (calculationResultDataset.result != null) {
                        calculationResultDataset.result = 0
                        calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> 0 (Assessment period was not given)"
                    }
                }
            } else if(calculateForUniqueAnswersOnly) {
                //calculateForUniqueAnswersOnly is a addQ Id that used for Zone finding FEC tool
                Set<Dataset> entityDatasets = entity.datasets
                //allowUnassignedZone is meant for category outside of building description in FEC tool, that allow mix up zone 00 and allow finding
                // zone outside of building description query for calculation
                Set<String> validZones = []
                List<String> validFECZones = datasetService.getValidFECZones(entityDatasets, calculateForUniqueAnswersOnly,
                        Boolean.FALSE, allowUnassignedZone, allowUnassignedZone)
                if(validFECZones) {
                    validZones.addAll(validFECZones)
                }
                List<String> allDatasetsValid = []
                for(String zoneId : validZones) {
                    finalMultiplier = resultManipulator.fixedMultiplier
                    finalDivider = null
                    Set<Dataset> datasetsForZone = datasetService.getZoneDataSets(zoneId,entityDatasets,calculateForUniqueAnswersOnly)
                    List<String> datasetIdsForZone = datasetsForZone?.collect({it.manualId as String})
                    if(datasetIdsForZone){
                        allDatasetsValid.addAll(datasetIdsForZone)
                    }
                    if (indicatorFixedMultiplier != null) {
                        // calculationResult.result = calculationResult.result * indicatorFixedMultiplier
                        finalMultiplier = finalMultiplier != null ? finalMultiplier * indicatorFixedMultiplier :
                                indicatorFixedMultiplier
                    }

                    if (indicatorValueReferenceMultiplier != null) {
                        // calculationResult.result = calculationResult.result * indicatorValueReferenceMultiplier
                        finalMultiplier = finalMultiplier != null ? finalMultiplier * indicatorValueReferenceMultiplier :
                                indicatorValueReferenceMultiplier
                    }

                    if (multiplierList) {
                        Double multiplier = null

                        for (ValueReference valueReference in multiplierList) {
                            multiplier = getValueReferenceValue(valueReference, entity,
                                     calculateForUniqueAnswersOnly, zoneId, multiplierCache, resourceCache)
                            //log.info("validZones ${validZones} allowUnassignedZone ${allowUnassignedZone}")
                            //log.info("multiplier ${multiplier} ZONE ${zoneId} resultCat ${calculationResult.resultCategoryId} rule ${calculationResult.calculationRuleId}")

                            if (multiplier != null) {
                                finalMultiplier = finalMultiplier != null ? finalMultiplier * multiplier : multiplier
                            } else if (!resultManipulator.allowFailure) {
                                calculationOk = false
                                loggerUtil.warn(log,"Aborting calculation in resultManipulator. Invalid value for multiplier ${valueReference.toString()}: ${multiplier}. Zone: ${zoneId}")
                                flashService.setErrorAlert("Aborting calculation in resultManipulator. Invalid value for multiplier ${valueReference.toString()}: ${multiplier}", true)
                                break
                            }
                        }
                    }

                    if (dividerList && calculationOk) {
                        Double divider

                        for (ValueReference valueReference in dividerList) {
                            divider = getValueReferenceValue(valueReference, entity,
                                    calculateForUniqueAnswersOnly, zoneId, dividerCache, resourceCache)
                            //log.info("validZones ${validZones}")
                            //log.info("divider ${divider} ZONE ${zoneId} resultCat ${calculationResult.resultCategoryId} rule ${calculationResult.calculationRuleId}")

                            if (divider != null && divider != 0) {
                                finalDivider = finalDivider != null ? finalDivider * divider : divider
                            } else {
                                if (!resultManipulator.allowFailure) {
                                    calculationOk = false
                                    log.info("Aborting calculation in resultManipulator. Invalid value for divider ${valueReference.toString()}: ${divider}. Zone: ${zoneId}")
                                    flashService.setWarningAlert("Aborting calculation in resultManipulator. Invalid value for divider ${valueReference.toString()}: ${divider}", true)
                                    break
                                }
                            }
                        }
                    }

                    if (!calculationOk) {
                        log.trace("Calculation not ok, breaking from loop")
                        break
                    }

                    if (calculationOk) {
                        if (useCalculationRule && resultManipulator.divideByRule && alreadyCalculatedResults) {
                            Double targetDivider = alreadyCalculatedResults.find({ it.calculationRuleId == resultManipulator.divideByRule && it.resultCategoryId == calculationResult.resultCategoryId })?.result

                            if (targetDivider) {
                                finalDivider = finalDivider != null ? finalDivider * targetDivider : targetDivider
                            } else if (!resultManipulator.allowFailure) {
                                calculationOk = false
                            }
                        }

                        if (resultCategory && resultManipulator.divideByCategory && alreadyCalculatedResults) {
                            Double targetDivider = alreadyCalculatedResults.findAll{ dividerCategoryIds.contains(it.resultCategoryId) &&
                                    it.calculationRuleId == calculationResult.calculationRuleId }*.result.findAll{it}.sum()

                            if (targetDivider) {
                                finalDivider = finalDivider != null ? finalDivider * targetDivider : targetDivider
                            } else if (!resultManipulator.allowFailure) {
                                calculationOk = false
                            }
                        }
                        if (calculationOk) {
                            if (finalMultiplier != null) {
                                calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                                    if(datasetIdsForZone?.contains(key)){
                                        if (calculationResultDataset.result != null) {
                                            Double originalResult = calculationResultDataset.result
                                            calculationResultDataset.result = calculationResultDataset.result * finalMultiplier
                                            calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> ${calculationResultDataset.result.round(2)} " +
                                                    "${unitToDisplay} = " +
                                                    "${originalResult.round(2)} * ${finalMultiplier.round(2)}"
                                        }
                                    }
                                }
                            }

                            if (finalDivider) {
                                calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                                    if(datasetIdsForZone?.contains(key)){
                                        if (calculationResultDataset.result != null) {
                                            Double originalResult = calculationResultDataset.result
                                            calculationResultDataset.result = calculationResultDataset.result / finalDivider
                                            calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> " +
                                                    "${calculationResultDataset.result.round(2)} ${unitToDisplay} = " +
                                                    "${originalResult.round(2)} / ${finalDivider.round(2)}"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Double finalValue = calculationResult.calculationResultDatasets?.findAll({allDatasetsValid.contains(it.key)})?.collect({it.value.result})?.findAll({it != null})?.sum()
                if(calculationOk && finalValue != null){
                    if(setMinValue != null && finalValue < setMinValue) {
                        calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                            if (calculationResultDataset.result != null) {
                                calculationResultDataset.result = setMinValue
                                calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> Reset using ${Constants.SET_MINIMUM_VALUE} :  ${setMinValue}"
                            }
                        }
                        finalValue = calculationResult.calculationResultDatasets?.findAll({allDatasetsValid.contains(it.key)})?.collect({it.value.result})?.findAll({it != null})?.sum()
                    } else if (setMaxValue != null && finalValue > setMaxValue) {
                        calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                            if (calculationResultDataset.result != null) {
                                calculationResultDataset.result = setMaxValue
                                calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> Reset using ${Constants.SET_MAXIMUM_VALUE} :  ${setMaxValue}"
                            }
                        }
                        finalValue = calculationResult.calculationResultDatasets?.findAll({allDatasetsValid.contains(it.key)})?.collect({it.value.result})?.findAll({it != null})?.sum()
                    }
                    calculationResult.result = finalValue
                } else if (resultManipulator.abortOnFailure) {
                    calculationResult.result = null
                    calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                        if (calculationResultDataset.result != null) {
                            calculationResultDataset.result = null
                            calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> 0 = Unable to resolve result manipulator"
                        }
                    }
                }
            } else {
                if (indicatorFixedMultiplier != null) {
                    // calculationResult.result = calculationResult.result * indicatorFixedMultiplier
                    finalMultiplier = finalMultiplier != null ? finalMultiplier * indicatorFixedMultiplier :
                            indicatorFixedMultiplier
                }

                if (indicatorValueReferenceMultiplier != null) {
                    // calculationResult.result = calculationResult.result * indicatorValueReferenceMultiplier
                    finalMultiplier = finalMultiplier != null ? finalMultiplier * indicatorValueReferenceMultiplier :
                            indicatorValueReferenceMultiplier
                }

                if (multiplierList) {
                    Double multiplier

                    for (ValueReference valueReference in multiplierList) {
                        multiplier = getValueReferenceValue(valueReference, entity,
                                calculateForUniqueAnswersOnly, null, multiplierCache, resourceCache)

                        if (multiplier != null) {
                            finalMultiplier = finalMultiplier != null ? finalMultiplier * multiplier : multiplier
                        } else if (!resultManipulator.allowFailure) {
                            calculationOk = false
                            log.info("Aborting calculation in resultManipulator. Invalid value for multiplier ${valueReference.toString()}: ${multiplier}")
                            flashService.setWarningAlert("Aborting calculation in resultManipulator. Invalid value for multiplier ${valueReference.toString()}: ${multiplier}", true)
                            break
                        }
                    }
                }

                if (dividerList && calculationOk) {
                    Double divider

                    for (ValueReference valueReference in dividerList) {
                        divider = getValueReferenceValue(valueReference, entity,
                                calculateForUniqueAnswersOnly, null, dividerCache, resourceCache)

                        if (divider != null && divider != 0) {
                            finalDivider = finalDivider != null ? finalDivider * divider : divider
                        } else {
                            if (!resultManipulator.allowFailure) {
                                calculationOk = false
                                log.info("Aborting calculation in resultManipulator. Invalid value for divider ${valueReference.toString()}: ${divider}")
                                flashService.setWarningAlert("Aborting calculation in resultManipulator. Invalid value for divider ${valueReference.toString()}: ${divider}", true)
                                break
                            }
                        }
                    }
                }

                if (!calculationOk) {
                    log.trace("Calculation is not ok.")
                }

                if (calculationOk) {
                    if (useCalculationRule && resultManipulator.divideByRule && alreadyCalculatedResults) {
                        Double targetDivider = alreadyCalculatedResults.find({ it.calculationRuleId == resultManipulator.divideByRule && it.resultCategoryId == calculationResult.resultCategoryId })?.result

                        if (targetDivider) {
                            finalDivider = finalDivider != null ? finalDivider * targetDivider : targetDivider
                        } else if (!resultManipulator.allowFailure) {
                            calculationOk = false
                        }
                    }

                    if (resultCategory && resultManipulator.divideByCategory && alreadyCalculatedResults) {
                        Double targetDivider = alreadyCalculatedResults.findAll{ dividerCategoryIds.contains(it.resultCategoryId) &&
                                it.calculationRuleId == calculationResult.calculationRuleId }*.result.findAll{it}.sum()

                        if (targetDivider) {
                            finalDivider = finalDivider != null ? finalDivider * targetDivider : targetDivider
                        } else if (!resultManipulator.allowFailure) {
                            calculationOk = false
                        }
                    }

                    if (calculationOk) {
                        if (finalMultiplier != null) {
                            calculationResult.result = calculationResult.result * finalMultiplier

                            calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                                if (calculationResultDataset.result != null) {
                                    Double originalResult = calculationResultDataset.result
                                    calculationResultDataset.result = calculationResultDataset.result * finalMultiplier
                                    calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> " +
                                            "${calculationResultDataset.result.round(2)} ${unitToDisplay} = " +
                                            "${originalResult.round(2)} * ${finalMultiplier.round(2)}"
                                }
                            }
                        }

                        if (finalDivider) {
                            calculationResult.result = calculationResult.result / finalDivider

                            calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                                if (calculationResultDataset.result != null) {
                                    Double originalResult = calculationResultDataset.result
                                    calculationResultDataset.result = calculationResultDataset.result / finalDivider
                                    calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> " +
                                            "${calculationResultDataset.result.round(2)} ${unitToDisplay} = " +
                                            "${originalResult.round(2)} / ${finalDivider.round(2)}"
                                }
                            }
                        }
                    }
                } else if (resultManipulator.abortOnFailure) {
                    calculationResult.result = null
                    calculationResult.calculationResultDatasets?.each { String key, Map calculationResultDataset ->
                        if (calculationResultDataset.result != null) {
                            calculationResultDataset.result = null
                            calculationResultDataset.calculationFormula = "${calculationResultDataset.calculationFormula},<b>Result multiplication factor:</b> 0 = Unable to resolve result manipulator"
                        }
                    }
                }
            }
        }
        log.trace("Finished using result manipulator in: ${new Date().time - start} ms.")
    }

    private Double getValueReferenceValue(ValueReference valueReference, Entity entity,
                                                 String calculateForUniqueAnswersOnly, String zoneId,
                                          Map<String, Double> valueReferenceCache, ResourceCache resourceCache) {
        String vrKey = valueReference.toString()
        String vrKeyWithZone = zoneId ? vrKey + ": " + zoneId : vrKey
        Double valueReferenceValue = null
        if(zoneId != null) {
            valueReferenceValue = valueReferenceCache.containsKey(vrKeyWithZone) ? valueReferenceCache.get(vrKeyWithZone) :
                    valueReferenceService.getDoubleValueForEntity(valueReference, entity, calculateForUniqueAnswersOnly,
                            zoneId, resourceCache)
            if(!valueReferenceCache.containsKey(vrKeyWithZone)) {
                valueReferenceCache.put(vrKeyWithZone, valueReferenceValue)
            }
            if(valueReferenceValue != null) {
                return valueReferenceValue
            }
        }
        if(valueReferenceValue == null && !valueReferenceCache.containsKey(vrKey)) {
            valueReferenceValue = valueReferenceService.getDoubleValueForEntity(valueReference, entity, null, null, resourceCache)
            valueReferenceCache.put(vrKey, valueReferenceValue)
            log.trace("Initial multiplier failed, Second multiplier = ${valueReferenceValue}")
        }
        return valueReferenceCache.get(vrKey)
    }

    String getUnitToDisplay(Indicator indicator, CalculationRule calculationRule) {
        String unitToDisplay = ""
        String displayRuleId = indicator.displayResult ?: ''
        if (("traciGWP_tn").equalsIgnoreCase(displayRuleId) || ("GWP_tn").equalsIgnoreCase(displayRuleId)) {
            unitToDisplay = calculationRule?.unit?.get("EN") ? calculationRule?.unit?.get("EN") : ''
        } else {
            unitToDisplay = "kg"
        }
        return unitToDisplay
    }
    def copyCalculationResults(ObjectId sourceEntityId, ObjectId targetEntityId) {
        Entity targetEntity

        if (sourceEntityId && targetEntityId) {
            Entity sourceEntity = Entity.get(sourceEntityId)
            targetEntity = Entity.get(targetEntityId)
            List<CalculationResult> sourceResults = CalculationResult.findAllByEntityId(sourceEntityId)

            if (sourceEntity?.calculationTotalResults && sourceResults && targetEntity) {
                targetEntity.calculationTotalResults = new ArrayList<CalculationTotalResult>(sourceEntity.calculationTotalResults)
                List<CalculationResult> calculationResults = new ArrayList<CalculationResult>()

                sourceResults.each { CalculationResult calculationResult ->
                    CalculationResult newResult = new CalculationResult()
                    newResult.properties = calculationResult.properties
                    newResult.id = null
                    newResult.entityId = targetEntityId
                    calculationResults.add(newResult)
                }
                targetEntity.calculationResults = calculationResults
                List<String> indicatorIds = targetEntity.calculationTotalResults.collect({
                    it.indicatorId
                }).unique()

                indicatorIds.each { String indicatorId ->
                    Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

                    if (indicator) {
                        List<String> queryIds = indicator.getQueries(targetEntity)?.collect({ Query query -> query.queryId })

                        if (queryIds) {
                            resolveQueryReadiness(indicator, targetEntity, queryIds)
                        }
                    }
                }
                targetEntity = targetEntity.merge(flush: true)
            }
        }
        return targetEntity
    }

    private resolveQueryReadiness(Indicator indicator, Entity entity, List<String> queryIds) {
        if (entity && queryIds) {
            List<Query> queries = queryService.getQueriesByQueryIds(queryIds, true)
            queries.each { Query query ->
                entity.resolveQueryReady(indicator, query)
            }
        }
    }

    private leedEpdAveragesForSubType(ResourceType resourceSubType, List<Document> resourcesBySubType, Boolean cml, Boolean traci) {
        Map<String, Double> averagesByCategory = [:]

        if (cml) {
            List<Double> impactGWP100_kgCO2e = []
            List<Double> impactODP_kgCFC11e = []
            List<Double> impactAP_kgSO2e = []
            List<Double> impactEP_kgPO4e = []
            List<Double> impactPOCP_kgEthenee = []
            List<Double> nonRenewablesUsedAsEnergy_MJ = []

            resourcesBySubType.each { Document r ->
                if ((r.unitForData?.equalsIgnoreCase(resourceSubType?.standardUnit) || (r.combinedUnits && r.combinedUnits*.toUpperCase().contains(resourceSubType?.standardUnit?.toUpperCase()))) && ((r.dataProperties*.toUpperCase()?.contains("CML") || r.dataProperties*.toUpperCase()?.contains("IMPACT")) && r.verificationStatus?.equalsIgnoreCase("Verified"))) {
                    if (r.impactGWP100_kgCO2e != null) {
                        impactGWP100_kgCO2e.add(r.impactGWP100_kgCO2e)
                    }

                    if (r.impactODP_kgCFC11e != null) {
                        impactODP_kgCFC11e.add(r.impactODP_kgCFC11e)
                    }

                    if (r.impactAP_kgSO2e != null) {
                        impactAP_kgSO2e.add(r.impactAP_kgSO2e)
                    }

                    if (r.impactEP_kgPO4e != null) {
                        impactEP_kgPO4e.add(r.impactEP_kgPO4e)
                    }

                    if (r.impactPOCP_kgEthenee != null) {
                        impactPOCP_kgEthenee.add(r.impactPOCP_kgEthenee)
                    }

                    if (r.nonRenewablesUsedAsEnergy_MJ != null) {
                        nonRenewablesUsedAsEnergy_MJ.add(r.nonRenewablesUsedAsEnergy_MJ)
                    }
                }
            }

            if (impactGWP100_kgCO2e.size() > 0) {
                averagesByCategory.put("impactGWP100_kgCO2e", (Double) impactGWP100_kgCO2e.sum() / impactGWP100_kgCO2e.size())
            }

            if (impactODP_kgCFC11e.size() > 0) {
                averagesByCategory.put("impactODP_kgCFC11e", (Double) impactODP_kgCFC11e.sum() / impactODP_kgCFC11e.size())
            }

            if (impactAP_kgSO2e.size() > 0) {
                averagesByCategory.put("impactAP_kgSO2e", (Double) impactAP_kgSO2e.sum() / impactAP_kgSO2e.size())
            }

            if (impactEP_kgPO4e.size() > 0) {
                averagesByCategory.put("impactEP_kgPO4e", (Double) impactEP_kgPO4e.sum() / impactEP_kgPO4e.size())
            }

            if (impactPOCP_kgEthenee.size() > 0) {
                averagesByCategory.put("impactPOCP_kgEthenee", (Double) impactPOCP_kgEthenee.sum() / impactPOCP_kgEthenee.size())
            }

            if (nonRenewablesUsedAsEnergy_MJ.size() > 0) {
                averagesByCategory.put("nonRenewablesUsedAsEnergy_MJ", (Double) nonRenewablesUsedAsEnergy_MJ.sum() / nonRenewablesUsedAsEnergy_MJ.size())
            }
        } else if (traci) {
            List<Double> traciGWP_kgCO2e = []
            List<Double> traciODP_kgCFC11e = []
            List<Double> traciAP_kgSO2e = []
            List<Double> traciEP_kgNe = []
            List<Double> traciPOCP_kgO3e = []
            List<Double> traciNRPE_MJ = []

            resourcesBySubType.each { Document r ->
                if ((r.unitForData?.equalsIgnoreCase(resourceSubType?.standardUnit) || (r.combinedUnits && r.combinedUnits*.toUpperCase().contains(resourceSubType?.standardUnit?.toUpperCase()))) && r.dataProperties*.toUpperCase()?.contains("TRACI") && r.verificationStatus?.equalsIgnoreCase("Verified")) {
                    if (r.traciGWP_kgCO2e != null ) {
                        traciGWP_kgCO2e.add(r.traciGWP_kgCO2e)
                    }

                    if (r.traciODP_kgCFC11e != null ) {
                        traciODP_kgCFC11e.add(r.traciODP_kgCFC11e)
                    }

                    if (r.traciAP_kgSO2e != null ) {
                        traciAP_kgSO2e.add(r.traciAP_kgSO2e)
                    }

                    if (r.traciEP_kgNe != null ) {
                        traciEP_kgNe.add(r.traciEP_kgNe)
                    }

                    if (r.traciPOCP_kgO3e != null ) {
                        traciPOCP_kgO3e.add(r.traciPOCP_kgO3e)
                    }

                    if (r.traciNRPE_MJ != null ) {
                        traciNRPE_MJ.add(r.traciNRPE_MJ)
                    }
                }
            }

            if (traciGWP_kgCO2e.size() > 0) {
                averagesByCategory.put("traciGWP_kgCO2e", (Double) traciGWP_kgCO2e.sum() / traciGWP_kgCO2e.size())
            }

            if (traciODP_kgCFC11e.size() > 0) {
                averagesByCategory.put("traciODP_kgCFC11e", (Double) traciODP_kgCFC11e.sum() / traciODP_kgCFC11e.size())
            }

            if (traciAP_kgSO2e.size() > 0) {
                averagesByCategory.put("traciAP_kgSO2e", (Double) traciAP_kgSO2e.sum() / traciAP_kgSO2e.size())
            }

            if (traciEP_kgNe.size() > 0) {
                averagesByCategory.put("traciEP_kgNe", (Double) traciEP_kgNe.sum() / traciEP_kgNe.size())
            }

            if (traciPOCP_kgO3e.size() > 0) {
                averagesByCategory.put("traciPOCP_kgO3e", (Double) traciPOCP_kgO3e.sum() / traciPOCP_kgO3e.size())
            }

            if (traciNRPE_MJ.size() > 0) {
                averagesByCategory.put("traciNRPE_MJ", (Double) traciNRPE_MJ.sum() / traciNRPE_MJ.size())
            }
        }
        return averagesByCategory
    }

    private void handleBenchmarkTotal(Map<String, List<Double>> totalsByBenchmark, Map<String, Integer> validValuesByBenchmark, Benchmark benchmark, String currentBenchmark, Double total, Boolean intResult = Boolean.FALSE, String resultByUnit = null) {
        if (resultByUnit) {
            Map<String, Double> existing = benchmark.resultByUnit.get(currentBenchmark)

            if (existing) {
                existing.put((resultByUnit), total)
            } else {
                existing = [(resultByUnit): total]
            }
            benchmark.resultByUnit.put(currentBenchmark, existing)
        } else {
            if (benchmark && currentBenchmark && total != null) {
                if (totalsByBenchmark.get(currentBenchmark)) {
                    List<Double> totals = totalsByBenchmark.get(currentBenchmark)
                    totals.add(total)
                    totalsByBenchmark.put(currentBenchmark, totals)
                } else {
                    totalsByBenchmark.put(currentBenchmark, [total])
                }

                if (validValuesByBenchmark.get(currentBenchmark)) {
                    Integer validValues = validValuesByBenchmark.get(currentBenchmark)
                    validValuesByBenchmark.put(currentBenchmark, validValues + 1)
                } else {
                    validValuesByBenchmark.put(currentBenchmark, 1)
                }

                if (intResult) {
                    benchmark.values.put(currentBenchmark, "${total.intValue()}")
                } else {
                    benchmark.values.put(currentBenchmark, "${total}")
                }
            }
        }
    }

    /**
     * Data benchmarks are unrelated to calculating results for a users design. Benchmarking calculates impacts from
     * resource under a specific resource subtype. Allows seeing how green different materials are and how well they compare
     * to each other.
     *
     * @param resourceSubType
     * @param resources
     * @return
     */

    def calculateResourceBenchmarks(ResourceType resourceSubType, List<Document> resources) {
        if (resourceSubType && resources) {
            List<String> calculateUnits = ["kg", "m3", "m2"]
            Map<String, List<Double>> totalsByBenchmark = [:]
            Map<String, Integer> validValuesByBenchmark = [:]
            Map<String, Double> leedEpdCmlAveragesForSubType = leedEpdAveragesForSubType(resourceSubType, resources, Boolean.TRUE, null)
            Map<String, Double> leedEpdTraciAveragesForSubType = leedEpdAveragesForSubType(resourceSubType, resources, null, Boolean.TRUE)
            List<String> benchmarks = Constants.BENCHMARKS.keySet().toList()

            resources.each { Document resource ->
                Boolean allowVariableThickness = (!resource.get("impactNonLinear") && (resource.get("density")||"m2".equals(resource.get("unitForData"))) && (resource.get("defaultThickness_in")||resource.get("defaultThickness_mm")))
                Benchmark b = new Benchmark()
                b.values = [:]
                b.ranking = [:]
                b.quintiles = [:]
                b.deviations = [:]
                b.resultByUnit = [:]
                b.factor = [:]
                b.factorDeviations = [:]

                if (!resource.excludeFromBenchmark && (resource.combinedUnits && resource.combinedUnits*.toUpperCase().contains(resourceSubType.standardUnit?.toUpperCase()))) {
                    benchmarks.each { String benchmark ->
                        Double total

                        if ("co2_cml".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                total = unitConversionUtil.doConversion(resource.impactGWP100_kgCO2e, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = resource.impactGWP100_kgCO2e / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(resource.impactGWP100_kgCO2e, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = resource.impactGWP100_kgCO2e / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        } else if ("co2_traci".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                total = unitConversionUtil.doConversion(resource.traciGWP_kgCO2e, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = resource.traciGWP_kgCO2e / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(resource.traciGWP_kgCO2e, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = resource.traciGWP_kgCO2e / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        } else if ("co2_inies_a1-c4".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                Map<String, Object> A1C4 = resource.impacts.get("A1-C4")
                                Double impactGWP100_kgCO2e = (Double) A1C4.get("impactGWP100_kgCO2e")
                                total = unitConversionUtil.doConversion(impactGWP100_kgCO2e, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = impactGWP100_kgCO2e / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(impactGWP100_kgCO2e, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = impactGWP100_kgCO2e / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        } else if ("leed_epd_cml".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                Integer points = 0

                                leedEpdCmlAveragesForSubType?.each { String category, Double average ->
                                    def value = resource.get(category)

                                    if (value && value < average) {
                                        points++
                                    }
                                }
                                handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, points, Boolean.TRUE)
                            }
                        } else if ("leed_epd_traci".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                Integer points = 0

                                leedEpdTraciAveragesForSubType?.each { String category, Double average ->
                                    def value = resource.get(category)

                                    if (value && value < average) {
                                        points++
                                    }
                                }
                                handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, points, Boolean.TRUE)
                            }
                        } else if ("ecopoints_direct".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                total = unitConversionUtil.doConversion(resource.impact_ecopoints, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = resource.impact_ecopoints / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(resource.impact_ecopoints, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = resource.impact_ecopoints / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        } else if ("shadowprice_direct".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                total = unitConversionUtil.doConversion(resource.shadowPrice, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = resource.shadowPrice / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(resource.shadowPrice, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = resource.shadowPrice / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        } else if ("utilities".equals(benchmark)) {
                            if (optimiResourceService.getPassesBenchmarkFilter(resource, benchmark)) {
                                total = unitConversionUtil.doConversion(resource.impactGWP100_kgCO2e, resource.defaultThickness_mm, resourceSubType.standardUnit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                if (total == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    total = resource.impactGWP100_kgCO2e / resource.massConversionFactor
                                }

                                if (total != null) {
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, total)
                                }

                                calculateUnits.each { String unit ->
                                    Double resultByUnit = unitConversionUtil.doConversion(resource.impactGWP100_kgCO2e, resource.defaultThickness_mm, unit, null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, allowVariableThickness, null, null, resource.defaultThickness_mm)

                                    if (resultByUnit == null && resource.massConversionFactor && "kg".equalsIgnoreCase(unit)) {
                                        resultByUnit = resource.impactGWP100_kgCO2e / resource.massConversionFactor
                                    }
                                    handleBenchmarkTotal(totalsByBenchmark, validValuesByBenchmark, b, benchmark, resultByUnit, Boolean.FALSE, unit)
                                }
                            }
                        }
                    }

                    Boolean resourceBenchmarked = Boolean.FALSE
                    benchmarks.each { String benchmark ->
                        if (b.values.get(benchmark)) {
                            resourceBenchmarked = Boolean.TRUE
                        } else {
                            b.values.put(benchmark, "NOT_CALCULABLE")
                        }
                    }

                    if (resourceBenchmarked) {
                        b.status = "OK"
                    } else {
                        b.status = "CONVERSION_NOT_POSSIBLE"
                    }
                } else {
                    benchmarks.each { String benchmark ->
                        b.values.put(benchmark, "NOT_CALCULABLE")
                    }

                    if (resource.excludeFromBenchmark) {
                        b.status = "EXCLUDED"
                    } else {
                        b.status = "INCOMPATIBLE_UNITS"
                    }
                }
                resource.put("benchmark", b)
            }

            Map<String, List<Double>> changesInCO2PerBenchmark = [:]

            //rank resources
            benchmarks.each { String benchmark ->

                if ("leed_epd_cml".equals(benchmark) || "leed_epd_traci".equals(benchmark)) {
                    resources = resources.sort({ a, b -> a.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? b.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? 0 : 1 : b.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? -1 : b.benchmark.values.get(benchmark).toInteger() <=> a.benchmark.values.get(benchmark).toInteger() })
                } else {
                    resources = resources.sort({ a, b -> a.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? b.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? 0 : 1 : b.benchmark.values.get(benchmark).equals("NOT_CALCULABLE") ? -1 : a.benchmark.values.get(benchmark).toDouble() <=> b.benchmark.values.get(benchmark).toDouble() })
                }

                Double previousValue = null
                int rank = 1

                resources.each { Document resource ->
                    String resourceBenchmarkValue = resource.benchmark.values.get(benchmark)

                    if (DomainObjectUtil.isNumericValue(resourceBenchmarkValue)) {
                        if (rank > 1) {
                            if (previousValue != null) {
                                Double changeInCO2 = DomainObjectUtil.convertStringToDouble(resourceBenchmarkValue) - previousValue
                                resource.benchmark.factor[benchmark] = changeInCO2

                                List<Double> existing = changesInCO2PerBenchmark.get(benchmark)

                                if (existing) {
                                    existing.add(changeInCO2)
                                } else {
                                    existing = [changeInCO2]
                                }
                                changesInCO2PerBenchmark.put((benchmark), existing)
                            }
                        } else {
                            resource.benchmark.factor[benchmark] = 0D
                        }

                        previousValue = DomainObjectUtil.convertStringToDouble(resourceBenchmarkValue)
                    }
                    resource.benchmark.ranking[benchmark] = rank
                    rank++
                }
            }

            //new calculate quintile && deviation
            benchmarks.each { String benchmark ->
                List<Document> resourcesToCalculateQuintileForThisBenchmark = resources.findAll({
                    it.benchmark.values.get(benchmark) && !it.benchmark.values.get(benchmark).equals("NOT_CALCULABLE")
                })

                if (resourcesToCalculateQuintileForThisBenchmark) {
                    List<String> totalsForBenchmark = resourcesToCalculateQuintileForThisBenchmark.collect({
                        it.benchmark.values.get(benchmark)
                    })
                    resourcesToCalculateQuintileForThisBenchmark.each { Document resource ->
                        Double benchmarkVal = Double.valueOf(resource.benchmark.values.get(benchmark))
                        Integer valuesLargerThanBenchmark = 0
                        Integer valuesSameValueAsBenchmark = 0

                        totalsForBenchmark.each { String total ->
                            if (Double.valueOf(total) > benchmarkVal) {
                                valuesLargerThanBenchmark++
                            } else if (Double.valueOf(total).equals(benchmarkVal)) {
                                valuesSameValueAsBenchmark++
                            }
                        }
                        Double quintile = Math.ceil((Double.valueOf((valuesLargerThanBenchmark + 0.5 * valuesSameValueAsBenchmark) / totalsForBenchmark.size())) * 5)

                        if (quintile && quintile.intValue() >= 1 && quintile.intValue() <= 5) {
                            Integer intQuintile = quintile.intValue()

                            if ("leed_epd_cml".equals(benchmark) || "leed_epd_traci".equals(benchmark)) {
                                //quintile is reversed because higher benchmark value is actually better
                                resource.benchmark.quintiles[benchmark] = intQuintile == 5 ? 1 : intQuintile == 4 ? 2 : intQuintile == 3 ? 3 : intQuintile == 2 ? 4 : intQuintile == 1 ? 5 : 0
                            } else {
                                resource.benchmark.quintiles[benchmark] = intQuintile
                            }
                        } else {
                            log.error("Calculating quintile failed for resource: ${resource.resourceId} / ${resource.profileId}, quintile calculated: ${quintile?.intValue()}")
                        }

                        if (totalsByBenchmark.get(benchmark)) {
                            Double mean = totalsByBenchmark.get(benchmark).sum() / totalsByBenchmark.get(benchmark).size()

                            if (mean) {
                                resource.benchmark.deviations[benchmark] = (Double.valueOf(resource.benchmark.values.get(benchmark)) - mean) / mean
                            }
                        }

                        if (changesInCO2PerBenchmark.get(benchmark)) {
                            Double mean = changesInCO2PerBenchmark.get(benchmark).sum() / changesInCO2PerBenchmark.get(benchmark).size()

                            if (mean) {
                                resource.benchmark.factorDeviations[benchmark] = (Double.valueOf(resource.benchmark.factor.get(benchmark)) - mean) / mean
                            }
                        }
                    }
                }
            }

            resources.each { Document resource ->
                Benchmark b = resource.benchmark
                Resource.collection.updateOne([resourceId: resource.resourceId, profileId: resource.profileId, active: resource.active], [$set: [benchmark: b]])
            }

            log.info("Benchmarked ${resources.size()} resources for subType: ${resourceSubType.subType}")
            //Subtype benchmarks
            BenchmarkResult benchmarkResult = new BenchmarkResult()
            benchmarkResult.validValues = [:]
            benchmarkResult.average = [:]
            benchmarkResult.min = [:]
            benchmarkResult.max = [:]
            benchmarkResult.factor = [:]
            benchmarkResult.median = [:]
            benchmarkResult.standardDeviation = [:]
            benchmarkResult.variance = [:]
            benchmarkResult.changesInCO2 = [:]

            benchmarks.each { String benchmark ->
                List<Double> totalsForThisBenchmark = totalsByBenchmark.get(benchmark)
                benchmarkResult.validValues.put(benchmark, validValuesByBenchmark.get(benchmark) ?: 0)
                benchmarkResult.average.put(benchmark, totalsForThisBenchmark ? totalsForThisBenchmark.sum() / totalsForThisBenchmark.size() : 0)
                benchmarkResult.min.put(benchmark, totalsForThisBenchmark ? totalsForThisBenchmark.min() : 0)
                benchmarkResult.max.put(benchmark, totalsForThisBenchmark ? totalsForThisBenchmark.max() : 0)
                benchmarkResult.median.put(benchmark, totalsForThisBenchmark ? getMedian(totalsForThisBenchmark) : 0)
                benchmarkResult.standardDeviation.put(benchmark, totalsForThisBenchmark ? getStandardDeviation(totalsForThisBenchmark) : 0)
                benchmarkResult.variance.put(benchmark, totalsForThisBenchmark ? getVariance(totalsForThisBenchmark) : 0)
                benchmarkResult.factor.put(benchmark, totalsForThisBenchmark ? Math.round(totalsForThisBenchmark.max() / totalsForThisBenchmark.min()).intValue() : 0)

                List<Double> changesInCO2ForThisBenchmark = changesInCO2PerBenchmark.get(benchmark)
                Map<String, Double> temp = [:]

                def min = changesInCO2ForThisBenchmark?.min() ?: 0
                def max = changesInCO2ForThisBenchmark?.max() ?: 0
                temp.put("min", min)
                temp.put("max", max)
                temp.put("avg", changesInCO2ForThisBenchmark ? changesInCO2ForThisBenchmark.sum() / changesInCO2ForThisBenchmark.size() : 0)
                temp.put("factor", min && max ? max / min : 0)
                benchmarkResult.changesInCO2.put((benchmark), temp)
            }
            resourceSubType.benchmarkResult = benchmarkResult
            resourceSubType.merge(flush: true)
            return true
        } else {
            return false
        }
    }

    public Double getMedian(List<Double> values) {
        Double median = 0

        if (values) {
            values = values.sort()
            int size = values.size()
            int midNumber = (size / 2).intValue()

            if (size % 2 == 0) {
                median = (values[midNumber - 1] + values[midNumber / 2]) / 2.0
            } else {
                median = values[midNumber / 2]
            }
        }
        return median
    }

    public Double getStandardDeviation(List<Double> values){
        if (values) {
            return Math.sqrt(getVariance(values))
        } else {
            return 0
        }
    }

    public Double getVariance(List<Double> values) {
        if (values) {
            Double sum = values.sum()
            Double size = values.size()

            if (sum && size > 1) {
                Double mean = sum / size
                Double variance = (values.collect{ num -> Math.pow((num - mean),2) }.sum() / (size - 1))
                return variance
            } else {
                return 0
            }
        } else {
            return 0
        }
    }

    private void applyLocalCompensation(Dataset dataset, CalculationResult calculationResult) {
        String answer = dataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_QUESTIONID)

        if (answer) {
            if ("noLocalCompensation".equals(answer)||"true".equals(answer)||"false".equals(answer)) {
                calculationResult.applyLocalComps = Boolean.FALSE
            } else {
                calculationResult.applyLocalComps = Boolean.TRUE
                calculationResult.localCompsCountryResourceId = answer.toString()

                String compensationEnergyProfile = dataset?.additionalQuestionAnswers?.get(com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)?.toString()

                if (compensationEnergyProfile && compensationEnergyProfile.tokenize(".")?.size() == 2) {
                    calculationResult.localCompsCountryEnergyResourceId = compensationEnergyProfile.tokenize(".")[0]
                    calculationResult.localCompsCountryEnergyProfileId = compensationEnergyProfile.tokenize(".")[1]
                }
            }
        } else {
            calculationResult.applyLocalComps = Boolean.TRUE
        }
    }

    LcaCheckerResult getLcaCheckerResultForEntity(Entity childEntity, Indicator indicator,
                                                         List<LcaChecker> lcaCheckers, List<Dataset> datasets,
                                                         List<String> suppressedCheckIds, Boolean inCalculation, ResourceCache resourceCache) {
        LcaCheckerResult lcaCheckerResult

        if (lcaCheckers && datasets && indicator && childEntity) {
            String indicatorId = indicator.indicatorId
            List<LcaChecker> checksNotApplicable = []
            Map<LcaChecker, List<Object>> suppressedChecks = [:] //LcaChecker and points, 0 is points, 1 is target e.g. passed / failed / erosion
            Map<LcaChecker, Double> checksPassed = [:]
            Map<LcaChecker, Double> checksFailed = [:]
            Map<LcaChecker, Double> checksAcceptable = [:]
            Map<String, List<CalculationResult>> resultsByCheckIndicators = lcaCheckerService.getOnlyNeededResultsOnce(lcaCheckers, childEntity, inCalculation)
            Double points = 0
            Double maxPoints = 0

            def mapChecks = childEntity.scope?.lcaCheckerMap

            lcaCheckers.each { LcaChecker lcaChecker ->
                List<String> resourceTypes = lcaChecker.applicableResourceTypes
                Map<String, String> projectResultParams = lcaCheckerService.getProjectResultParams(lcaChecker)
                Double pointsErosion = lcaChecker.pointsErosionRange
                Boolean conditionPassed = Boolean.FALSE

                if (lcaChecker.conditionToApply && !"special".equals(lcaChecker.checkCategory)) {

                    String conditionToApply = lcaChecker.conditionToApply
                    String requiredCondition = lcaChecker.requiredCondition

                    if (conditionToApply && requiredCondition) {

                        Boolean isCheckPresent = mapChecks?.get(conditionToApply)?.contains(requiredCondition)
                        if (isCheckPresent) {
                            if (!suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                maxPoints = maxPoints + lcaChecker.points
                            }
                            conditionPassed = Boolean.TRUE
                        }
                    } else {
                        log.error("LcaChecker: Check: ${lcaChecker.checkId} does not contain proper conditionToApply: ${lcaChecker.conditionToApply}")
                    }
                } else if ("special".equals(lcaChecker.checkCategory) || !lcaChecker.conditionToApply) {
                    if (!suppressedCheckIds?.contains(lcaChecker.checkId)) {
                        maxPoints = maxPoints + lcaChecker.points
                    }
                    conditionPassed = Boolean.TRUE
                }

                if (conditionPassed) {
                    if (("completeness".equals(lcaChecker.checkCategory) || "quality".equals(lcaChecker.checkCategory)) && projectResultParams) {
                        String resultIndicatorId = projectResultParams.get("indicatorId1")
                        String resultCategoryId = projectResultParams.get("resultCategoryId1")
                        String calculationRuleId = projectResultParams.get("calculationRuleId1")
                        String section = projectResultParams.get("section1")
                        String resultIndicatorId2 = projectResultParams.get("indicatorId2")
                        String resultCategoryId2 = projectResultParams.get("resultCategoryId2")
                        String calculationRuleId2 = projectResultParams.get("calculationRuleId2")
                        String section2 = projectResultParams.get("section2")

                        if (resultIndicatorId && resultCategoryId && calculationRuleId) {
                            List<CalculationResult> resultsByIndicator = resultsByCheckIndicators.get(resultIndicatorId)
                            Double total = lcaCheckerService.getTotalForRuleOrBySection(datasets, childEntity, resultIndicatorId,
                                    resultCategoryId, calculationRuleId, resultsByIndicator, section, resourceTypes, resourceCache)

                            if ("minimumValue".equals(lcaChecker.checkType)) {
                                if (total >= lcaChecker.checkValue1) {

                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                    } else {
                                        points = points + lcaChecker.points
                                        checksPassed.put(lcaChecker, total)
                                    }
                                } else {
                                    Double errosionPoints = lcaCheckerService.getErosionPoints(lcaChecker.checkType, lcaChecker.points, pointsErosion, total, lcaChecker.checkValue1, null)

                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        if (errosionPoints) {
                                            suppressedChecks.put(lcaChecker, [errosionPoints, "checksAcceptable"])
                                        } else {
                                            suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                        }
                                    } else {
                                        if (errosionPoints) {
                                            points = points + errosionPoints
                                            checksAcceptable.put(lcaChecker, total)
                                        } else {
                                            checksFailed.put(lcaChecker, total ?: 0)
                                        }
                                    }
                                }
                            } else if ("maximumValue".equals(lcaChecker.checkType)) {
                                if (total <= lcaChecker.checkValue1) {
                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                    } else {
                                        points = points + lcaChecker.points
                                        checksPassed.put(lcaChecker, total)
                                    }
                                } else {
                                    Double errosionPoints = lcaCheckerService.getErosionPoints(lcaChecker.checkType, lcaChecker.points, pointsErosion, total, lcaChecker.checkValue1, null)

                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        if (errosionPoints) {
                                            suppressedChecks.put(lcaChecker, [errosionPoints, "checksAcceptable"])
                                        } else {
                                            suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                        }
                                    } else {
                                        if (errosionPoints) {
                                            points = points + errosionPoints
                                            checksAcceptable.put(lcaChecker, total)
                                        } else {
                                            checksFailed.put(lcaChecker, total ?: 0)
                                        }
                                    }
                                }
                            } else if ("valueInRange".equals(lcaChecker.checkType)) {
                                if (total >= lcaChecker.checkValue1 && total <= lcaChecker.checkValue2) {
                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                    } else {
                                        points = points + lcaChecker.points
                                        checksPassed.put(lcaChecker, total)
                                    }
                                } else {
                                    Double errosionPoints = lcaCheckerService.getErosionPoints(lcaChecker.checkType, lcaChecker.points, pointsErosion, total, lcaChecker.checkValue1, lcaChecker.checkValue2)

                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        if (errosionPoints) {
                                            suppressedChecks.put(lcaChecker, [errosionPoints, "checksAcceptable"])
                                        } else {
                                            suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                        }
                                    } else {
                                        if (errosionPoints) {
                                            points = points + errosionPoints
                                            checksAcceptable.put(lcaChecker, total)
                                        } else {
                                            checksFailed.put(lcaChecker, total ?: 0)
                                        }
                                    }
                                }
                            } else if ("ratioInRange".equals(lcaChecker.checkType)) {
                                if (resultIndicatorId2 && resultCategoryId2 && calculationRuleId2) {
                                    Double total2 = lcaCheckerService.getTotalForRuleOrBySection(datasets, childEntity, resultIndicatorId2,
                                            resultCategoryId2, calculationRuleId2, resultsByIndicator, section2, resourceTypes, resourceCache)

                                    if (total && total2) {
                                        Double ratio = (total / total2) * 100

                                        if (ratio > lcaChecker.checkValue1 && ratio < lcaChecker.checkValue2) {
                                            if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                                suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                            } else {
                                                points = points + lcaChecker.points
                                                checksPassed.put(lcaChecker, ratio)
                                            }
                                        } else {
                                            if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                                suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                            } else {
                                                checksFailed.put(lcaChecker, ratio ?: 0)
                                            }
                                        }
                                    } else {
                                        if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                            suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                        } else {
                                            maxPoints = maxPoints - lcaChecker.points
                                            checksNotApplicable.add(lcaChecker)
                                        }
                                    }
                                } else {
                                    log.error("LcaChecker: Check: ${lcaChecker.checkId} does not contain proper projectResult2: ${lcaChecker.projectResult2}, for ratioInRange checks this is required.")
                                }
                            } else {
                                log.error("LcaChecker: unrecognized check type: ${lcaChecker.checkType}, for checker: ${lcaChecker.checkId}")
                            }
                        } else {
                            log.error("LcaChecker: Check: ${lcaChecker.checkId} does not contain proper projectResult1: ${lcaChecker.projectResult1}, for completeness and quality checks this is required.")
                        }
                    } else if ("special".equals(lcaChecker.checkCategory)) {
                        String customCondition = lcaChecker.conditionToApply
                        def datasetsBuildingMaterials = datasets.findAll({ it.queryId == "buildingMaterialsQuery"})
                        def materialIdList = [:]
                        ResultCategory materialsImpact
                        String displayRuleId
                        List<CalculationResult> resultsForIndicator = resultsByCheckIndicators.get(indicatorId)
                        if (datasetsBuildingMaterials) {
                            displayRuleId = indicator.displayResult

                            if (displayRuleId) {
                                Double totalResult = childEntity.getTotalResult(indicator.indicatorId, displayRuleId)
                                List<ResultCategory> resultCategories = indicator.getResolveResultCategories(null)?.findAll({
                                    !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty()
                                })
                                materialsImpact = resultCategories?.get(0)

                                if (totalResult && materialsImpact) {
                                    datasetsBuildingMaterials.each { Dataset dataset ->
                                        String materialId = resourceCache.getResource(dataset)?.resourceId
                                        if (materialId) {
                                            Double score = childEntity.getResultForDataset(dataset.manualId, indicatorId, materialsImpact.resultCategoryId, displayRuleId, resultsForIndicator)
                                            if (score != null && !score.isNaN()) {
                                                Double existingValue = materialIdList?.get(materialId)
                                                if (existingValue != null && !existingValue.isNaN()) {
                                                    materialIdList.put(materialId, score + existingValue)
                                                } else {
                                                    materialIdList.put(materialId, score)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if ("noWarnings".equals(customCondition)) {
                            String dataQualityWarning = queryService.getDatasetsOkForEntity(childEntity, indicator, datasets)

                            if (!dataQualityWarning) {
                                if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                } else {
                                    points = points + lcaChecker.points
                                    checksPassed.put(lcaChecker, 1)
                                }
                            } else {
                                if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                } else {
                                    checksFailed.put(lcaChecker, 0)
                                }
                            }
                        } else if ("minimumDatapoints".equals(customCondition)) {
                            Boolean passed = Boolean.FALSE

                            if (materialIdList) {
                                if (materialIdList.size() > lcaChecker.checkValue1) {
                                    passed = Boolean.TRUE
                                }
                            }

                            if (passed) {
                                if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                } else {
                                    points = points + lcaChecker.points
                                    checksPassed.put(lcaChecker, materialIdList?.size() ?: 1)
                                }
                            } else {
                                if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                } else {
                                    checksFailed.put(lcaChecker, materialIdList?.size() ?: 0)
                                }
                            }
                        } else if ("singleMaterialComprisesTooHighShare".equals(customCondition)) {
                            Boolean passed = Boolean.TRUE
                            Double percentageResult = 0.0
                            if(displayRuleId && materialsImpact && resultsForIndicator) {
                                Double total = childEntity.getResult(indicator.indicatorId, displayRuleId, materialsImpact.resultCategoryId, resultsForIndicator)
                                if (total) {
                                    materialIdList.each { String id, Double value ->
                                        Double percentageScore = value / total * 100
                                        if (percentageScore > lcaChecker.checkValue1) {
                                            passed = Boolean.FALSE
                                        }
                                        if (percentageScore > percentageResult) {
                                            percentageResult = percentageScore
                                        }
                                    }
                                }
                                if (passed) {
                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksPassed"])
                                    } else {
                                        points = points + lcaChecker.points
                                        checksPassed.put(lcaChecker, percentageResult)
                                    }
                                } else {
                                    if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                        suppressedChecks.put(lcaChecker, [lcaChecker.points, "checksFailed"])
                                    } else {
                                        checksFailed.put(lcaChecker, percentageResult)
                                    }
                                }
                            }
                        } else {
                            log.error("LcaChecker: Check: ${lcaChecker.checkId} unrecognized special condition: ${customCondition}")
                        }
                    }
                } else {
                    checksNotApplicable.add(lcaChecker)
                }
            }
            Integer percentage = points ? Math.round((points / maxPoints) * 100).intValue() : 0
            String color = lcaCheckerService.getPercentageColor(percentage)
            String letter = lcaCheckerService.getPercentageLetter(percentage)
            lcaCheckerResult = entityService.getLcaCheckerResult(childEntity, indicatorId, percentage, letter, color, checksPassed, checksFailed, checksAcceptable, checksNotApplicable, suppressedChecks)

        }
        return lcaCheckerResult
    }
    private roundingNumber(Double number) {
        // moved logic to numberUtil
        return numberUtil.roundingNumber(number)
    }
}