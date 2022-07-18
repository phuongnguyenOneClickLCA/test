/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.CDRegionDefaultChoice
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CarbonDesignerDefaultValueConstituents
import com.bionova.optimi.core.domain.mongo.CarbonDesignerRegion
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityTypeConstructionAllocation
import com.bionova.optimi.core.domain.mongo.EntityTypeConstructionGroupAllocation
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.SimulationTool
import com.bionova.optimi.core.domain.mongo.SimulationToolConstituent
import com.bionova.optimi.core.domain.mongo.SimulationToolConstruction
import com.bionova.optimi.core.domain.mongo.SimulationToolConstructionGroup
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyType
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyTypePassivehausBuilder
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyTypeTEKBuilder
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import grails.core.GrailsApplication
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile

import java.math.MathContext

class SimulationToolService {
    GrailsApplication grailsApplication
    def datasetService
    def newCalculationServiceProxy
    def constructionService
    def denominatorUtil
    def domainClassService
    def importMapperService
    def optimiResourceService
    def indicatorService
    def messageSource
    def userService
    def queryService
    def questionService
    def costStructureService
    def resourceFilterCriteriaUtil
    def entityService
    def eolProcessService
    def carbonDesignerRegionService
    def resourceService
    LoggerUtil loggerUtil
    FlashService flashService
    IndicatorQueryService indicatorQueryService

    //*****************************************************
    //Constant values for the Simulation Tool
    //*****************************************************

    //File earlyPhaseTool_query_vXXX
    final QUERY_ID = "earlyPhaseToolQuery"
    //Resource group of all "Resource Building Type" in file [resources_EARLY_PHASE_TOOL_vXXX]
    final EARLY_PHASE_RESOURCE_GROUP_ID = "earlyPhase"
    //Resource group of all Energy Scenario in file [resources_EARLY_PHASE_TOOL_vXXX]
    //It'a a bit messy the file used to load the values of every Energy Scenarios
    final ENERGY_RESOURCE_GROUP_ID = "energyMixNS3720"
    final DEFAULT_REGION = "nordicRegionSimulation"

    final SESSION_EXPIRED_LABEL = "Session Expired"
    final NO_CONSTRUCTION_ALLOCATIONS_LABEL = "No Construction Allocation Resources Loaded For The Region "
    final CALCULATION_EMPTY_LABEL = "Calculation results are empty!"

    final static String BUILDING_MATERIAL_QUERY = "buildingMaterialsQuery"
    final static String ENERGY_QUERY = "buildingOperatingEnergyAndWater"

    final static Double M_TO_FT = 3.28083989501 // Conversion Factor from meter to foot
    final static Map conversionUnitTable = [
            "m": [unit: "ft", conversionFactor: M_TO_FT],
            "m2": [unit: "sq ft", conversionFactor: M_TO_FT**2],
            "m3": [unit: "cu ft", conversionFactor: M_TO_FT**3],
            "mm": [unit: "inch", conversionFactor: 0.0393701],
            "kg": [unit: "lbs", conversionFactor: 2.20462262],

            "kWh": [unit: "kBtu", conversionFactor: 3.41214163313],
            "kBtu": [unit: "kWh", conversionFactor: 0.2930710702],

            "sq ft": [unit: "m2", conversionFactor: 1],
            "ft": [unit: "m", conversionFactor: 1],
            "lbs": [unit: "kg", conversionFactor: 1],
    ]

    final static List<String> allowedPrivateConstructionUnits = ["m","m2","m3","kg"]

    final static Map unitHTML = [
            "sq ft" : "sqft",
    //        "cu ft" : "ft<sup>3</sup>",
            "m2" : "m<sup>2<sup>",
            "m3" : "m<sup>3<sup>",
    ]

    final static String RESET_DEFAULT_MATERIAL = "simulationTool.resetDefaultMaterial"
    //**********************************************************

    //This method calculates the impact of a sub-set of tempEntity --> [constructionDataset, constituentDatasets]
    private Entity addCalculationToTempEntity(Entity parentEntity, Entity tempEntity, Indicator indicator, Set<Dataset> entityDatasets, Dataset constructionDataset, Set<Dataset> constituentDatasets, Entity design, Integer assessmentPeriod) {

        //constituentDatasets = constituentDatasets.findAll({it.quantity!=0})

        Entity tempForCalc = new Entity()
        tempForCalc.defaults = tempEntity.defaults
        List<Dataset> projectLevelDatasets = tempEntity.datasets?.findAll({ it.projectLevelDataset })?.toList()
        List<Dataset> tempForCalcDatasets = []
        tempForCalcDatasets.add(constructionDataset)
        tempForCalcDatasets.addAll(constituentDatasets)

        if (projectLevelDatasets) {
            tempForCalcDatasets.addAll(projectLevelDatasets)
        }

        Dataset assessment

        if (assessmentPeriod && indicator.assessmentPeriodValueReference) {
            assessment = createAssessmentDataset(indicator.assessmentPeriodValueReference, assessmentPeriod)

            if (assessment) {
                tempForCalcDatasets.add(assessment)
            }
        }
        tempForCalc.datasets = tempForCalcDatasets
        //tempForCalc = datasetService.preHandleDatasets(tempForCalc, tempForCalcDatasets, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
        tempForCalc = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parentEntity, tempForCalc, Boolean.TRUE, [indicator.displayResult])
        entityDatasets.addAll(tempForCalc.datasets)
        tempEntity.datasets.addAll(entityDatasets)

        List toRemove = []
        try {
            tempEntity.tempCalculationResults?.each { result ->
                if (result.calculationResultDatasets.find({
                    tempForCalcDatasets.collect({ it.manualId }).contains(it.key)
                })) {
                    toRemove.add(result)
                }
            }
            tempEntity.tempCalculationResults.removeAll(toRemove)
            if (tempForCalc.tempCalculationResults) {
                tempEntity.tempCalculationResults.addAll(tempForCalc.tempCalculationResults)
            }
        } catch (ConcurrentModificationException e) {
            loggerUtil.error(log, "CarbonDesigner: Unable to add calculation to entity due to concurrent modification:", e)
            flashService.setErrorAlert("CarbonDesigner: Unable to add calculation to entity due to concurrent modification: ${e.message}", true)
        }
        return tempEntity

    }

    def addCostToConstruction(Entity tempEntity, SimulationToolConstruction simConstruction, Double userGivenCost) {
        Double constructionQuantity = simConstruction.amount

        if (constructionQuantity != null && userGivenCost != null) {

            String constructionId = simConstruction.oid

            def entityDatasets = tempEntity.datasets
            Dataset constructionDataset = entityDatasets.find({
                !it.parentConstructionId && it.uniqueConstructionIdentifier?.endsWith(constructionId)
            })
            Set<Dataset> constructionDatasets = entityDatasets.findAll({ constructionId.equals(it.parentConstructionId) })

            if (constructionDataset && constructionDatasets) {
                constructionDataset.userSetCost = true
                constructionDataset.userSetTotalCost = true

                if (constructionDataset.additionalQuestionAnswers) {
                    constructionDataset.additionalQuestionAnswers.put("costPerUnit", userGivenCost)
                    constructionDataset.additionalQuestionAnswers.put("totalCost", userGivenCost * constructionQuantity)
                } else {
                    constructionDataset.additionalQuestionAnswers = ["costPerUnit": userGivenCost, "totalCost": userGivenCost * constructionQuantity]
                }

                constructionDatasets.each { Dataset d ->
                    d.userSetCost = true
                    d.userSetTotalCost = true

                    if (d.additionalQuestionAnswers) {
                        d.additionalQuestionAnswers.put("costPerUnit", "0")
                        d.additionalQuestionAnswers.put("totalCost", "0")
                    } else {
                        d.additionalQuestionAnswers = ["costPerUnit": "0", "totalCost": "0"]
                    }
                }
            }
        }
        return tempEntity
    }

    def calculationConstructionImpact(Entity parentEntity, Entity tempEntity, Indicator indicator, SimulationToolConstruction simConstruction, String unitSystem, Entity design, Integer assessmentPeriod) {
        Set<Dataset> constructionDatasets

        String constructionId = simConstruction.oid.toString()

        Double constructionQuantity = simConstruction.amount

        String constrComment = simConstruction.comment

        if (constructionQuantity != null) {
            def entityDatasets = tempEntity.datasets
            Dataset constructionDataset = entityDatasets.find({
                !it.parentConstructionId && it.uniqueConstructionIdentifier?.endsWith(constructionId)
            })

            if (constructionDataset) {
                constructionDatasets = entityDatasets.findAll({ it.parentConstructionId && constructionDataset.uniqueConstructionIdentifier.equals(it.uniqueConstructionIdentifier) })

                if (constructionDataset && constructionDatasets) {
                    entityDatasets.remove(constructionDataset)
                    entityDatasets.removeAll(constructionDatasets)
                    constructionDataset.quantity = constructionQuantity
                    constructionDataset.answerIds = ["${constructionQuantity}"]

                    if(constrComment){
                        def addQuestions = constructionDataset.additionalQuestionAnswers
                        if(addQuestions){
                            addQuestions.put("comment", constrComment)
                        } else {
                            addQuestions = ["comment" : constrComment]
                        }
                    }

                    constructionDatasets.each { Dataset d ->
                        Double originalAnswer = (d.originalAnswer?.isNumber()) ? d.originalAnswer?.toDouble() : 0

                        if (originalAnswer != null) {
                            Double quantityAnswer = originalAnswer * constructionDataset.quantity
                            d.quantity = quantityAnswer
                            d.answerIds = ["${d.quantity}"]
                        }

                        //Thickness from simulation tool ??? maybe there is a better way
                        SimulationToolConstituent c = simConstruction?.constituents?.find {
                            it.manualId == d?.additionalQuestionAnswers?.get("datasetIdFromConstruction")
                        }
                        if (c && c.thickness != null) {
                            d.additionalQuestionAnswers.put("thickness_mm", c.thickness)
                            if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                                d.additionalQuestionAnswers.put("thickness_in", c.thickness!=null ? c.thickness * conversionUnitFactor(unitSystem, "mm") : null)
                            }
                        }

                        String comment = c?.comment
                        if(comment) d?.additionalQuestionAnswers?.put("comment", comment)

                        d.defaultConstituent = c?.defaultConstituent
                    }

                    tempEntity = addCalculationToTempEntity(parentEntity, tempEntity, indicator, entityDatasets, constructionDataset, constructionDatasets, design, assessmentPeriod)

                    Denominator overallDenominator = indicator.resolveDenominators?.find({
                        "overallDenominator".equalsIgnoreCase(it.denominatorType)
                    })
                    Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(tempEntity, overallDenominator)
                    simConstruction = getCo2Impact(tempEntity, indicator, simConstruction, null, null, parentEntity, null, overallDenomValue)

                }
            }
            [simConstruction: simConstruction, temEntity: tempEntity]
        }
    }

    def simulateConstructionImpact(Entity parentEntity, Entity tempEntity, Indicator indicator, SimulationToolConstruction simConstruction, String unitSystem, Integer assessmentPeriod) {

        Set<Dataset> constructionDatasets = []

        String constructionId = simConstruction.oid.toString()

        Double constructionQuantity = simConstruction.amount

        if (constructionQuantity != null) {
            def entityDatasets = tempEntity.datasets
            Dataset constructionDataset = entityDatasets.find({
                (!it.parentConstructionId && it.uniqueConstructionIdentifier?.endsWith(constructionId)) || it.projectLevelDataset
            })
            constructionDatasets = entityDatasets.findAll({ constructionId.equals(it.parentConstructionId) })
            if (constructionDataset && constructionDatasets) {

                constructionDataset.quantity = constructionQuantity
                constructionDataset.answerIds = ["${constructionQuantity}"]
                constructionDatasets.each { Dataset d ->
                    Double originalAnswer = (d.originalAnswer?.isNumber()) ? d.originalAnswer?.toDouble() : 0

                    if (originalAnswer != null) {
                        Double quantityAnswer = originalAnswer * constructionDataset.quantity
                        d.quantity = quantityAnswer
                        d.answerIds = ["${d.quantity}"]
                    }

                    //Thickness from simulation tool ??? maybe there is a better way
                    SimulationToolConstituent c = simConstruction.constituents.findResult {
                        it.manualId == d.additionalQuestionAnswers?.get("datasetIdFromConstruction") ? it : null
                    }
                    if (c && c.thickness != null) {
                        d.additionalQuestionAnswers.put("thickness_mm", c.thickness)
                        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                            d.additionalQuestionAnswers.put("thickness_in", c.thickness!=null ? c.thickness * conversionUnitFactor(unitSystem, "mm") : null)
                        }
                    }
                    d.defaultConstituent = c?.defaultConstituent
                }

                Entity tempForCalc = new Entity()
                tempForCalc.defaults = tempEntity.defaults
                List<Dataset> tempForCalcDatasets = []
                tempForCalcDatasets.add(constructionDataset)
                tempForCalcDatasets.addAll(constructionDatasets)

                Dataset assessment

                if (assessmentPeriod && indicator.assessmentPeriodValueReference) {
                    assessment = createAssessmentDataset(indicator.assessmentPeriodValueReference, assessmentPeriod)

                    if (assessment) {
                        tempForCalcDatasets.add(assessment)
                    }
                }

                //tempForCalc = datasetService.preHandleDatasets(tempForCalc, tempForCalcDatasets, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
                tempForCalc.datasets = tempForCalcDatasets

                tempForCalc = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parentEntity, tempForCalc, Boolean.TRUE, [indicator.displayResult])

                Denominator overallDenominator = indicator.resolveDenominators?.find({
                    "overallDenominator".equalsIgnoreCase(it.denominatorType)
                })
                Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(tempForCalc, overallDenominator)

                simConstruction = getCo2Impact(tempForCalc, indicator, simConstruction, null, null, parentEntity, null, overallDenomValue)

            }

            [simConstruction: simConstruction, temEntity: tempEntity]
        }
    }

    def firstCalculationConstructionImpactV2(Entity tempEntity, Indicator indicator, List<SimulationToolConstructionGroup> modelGroups,
                                             List<Indicator> indicators, Entity parentEntity, List<EntityTypeConstructionGroupAllocation> earlyPhaseToolGroups, 
                                             String unitSystem, String regionId, Indicator lccIndicator, Entity design, Integer assessmentPeriod,
                                             List<Construction> createdDynamicConstructions, List<SimulationToolConstructionGroup> modelGroupsFromDraft) {

        if (tempEntity) {
            AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib');
            List<Dataset> entityDatasets = []
            List<Dataset> skipCalculationDatasets = []

            if (parentEntity) {
                if (parentEntity.datasets && parentEntity.datasets.find({ it.projectLevelDataset })) {
                    entityDatasets.addAll(parentEntity.datasets.findAll({ it.projectLevelDataset }))
                }

                if (parentEntity.defaults) {
                    tempEntity.defaults = parentEntity.defaults
                }
            }
            String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)
            Resource projectCountry = parentEntity?.countryResource
            String lcaModel = parentEntity?.lcaModel
            EolProcessCache eolProcessCache = EolProcessCache.init()

            if (earlyPhaseToolGroups && modelGroups && !modelGroups.isEmpty()) {
                int seqNr = 0

                Map<String, List<Question>> additionalQuestionsPerQuestion = [:]

                modelGroups.each { SimulationToolConstructionGroup simGroup ->
                    SimulationToolConstructionGroup simulationToolConstructionGroupFromDraft = modelGroupsFromDraft?.find({it.oid == simGroup.oid})
                    Double initialGroupAmount = simGroup.initialAmount
                    EntityTypeConstructionGroupAllocation groupAllocation = earlyPhaseToolGroups.find { it.groupId == simGroup.oid }

                    Map target

                    if ("defaultsGroup".equals(groupAllocation?.buildingElement)) {
                        target = null // set this later per constituent
                    } else {
                        target = groupAllocation?.target
                    }

                    simGroup.constructions?.each { SimulationToolConstruction simConstruction ->
                        // Original constructionid used if its a private account construction available in all groups!
                        SimulationToolConstruction draftSimConstruction

                        if ("defaultsGroup".equals(groupAllocation?.buildingElement)) {
                            draftSimConstruction = simulationToolConstructionGroupFromDraft?.constructions?.find({it.dynamicDefaultGroupId == groupAllocation.groupId && it.mirrorResourceId == simConstruction.mirrorResourceId})
                        } else {
                            if (simConstruction.originalConstructionId) {
                                draftSimConstruction = simulationToolConstructionGroupFromDraft?.constructions?.find({it.originalConstructionId == simConstruction.originalConstructionId})
                            } else {
                                draftSimConstruction = simulationToolConstructionGroupFromDraft?.constructions?.find({it.constructionId == simConstruction.constructionId})
                            }
                        }

                        if (draftSimConstruction?.comment) {
                            simConstruction.comment = draftSimConstruction.comment
                        }

                        if (draftSimConstruction?.userGivenCost) {
                            simConstruction.userGivenCost = draftSimConstruction.userGivenCost
                        }

                        Construction construction

                        if (simConstruction.dynamicDefault) {
                            construction = createdDynamicConstructions?.find({simConstruction.constructionId.equals(it.constructionId)})
                        } else {
                            construction = simConstruction.originalConstructionId ? constructionService.getConstruction(simConstruction.originalConstructionId) : constructionService.getConstructionWithConstructionId(simConstruction.constructionId)
                        }
                        if (construction) {
                            Double constructionQuantity = (initialGroupAmount ?: 0) * (simConstruction?.share ?: 0)
                            Dataset constructionDataset = new Dataset()
                            constructionDataset.manualId = new ObjectId().toString()
                            constructionDataset.seqNr = seqNr

                            constructionDataset.userGivenUnit = construction.unit
                            constructionDataset.quantity = constructionQuantity
                            constructionDataset.answerIds = ["${constructionQuantity}"]
                            constructionDataset.resourceId = construction.mirrorResourceId

                            if (simConstruction.isCreatedFromPrivateConstruction||simConstruction.dynamicDefault) {
                                constructionDataset.uniqueConstructionIdentifier = UUID.randomUUID().toString() + simConstruction.oid
                            } else {
                                // TODO FIX THIS FOR SAVED CD DRAFT? FOR PRIVATE CONSTRUCTIONS
                                simConstruction?.oid = construction.id.toString()
                                simConstruction?.name = constructionService.getLocalizedLabel(construction)
                                constructionDataset.uniqueConstructionIdentifier = UUID.randomUUID().toString() + construction.id.toString()
                            }

                            if ("defaultsGroup".equals(groupAllocation?.buildingElement)) {
                                target = groupAllocation.defaultValueConstituents?.find({it.defaultValueResource?.equals(construction.mirrorResourceId)})?.target
                            }

                            String queryId = target?.queryId
                            String sectionId = target?.sectionId
                            String questionId = target?.questionId

                            constructionDataset.queryId = queryId
                            constructionDataset.sectionId = sectionId
                            constructionDataset.questionId = questionId

                            simConstruction.queryId = queryId
                            simConstruction.sectionId = sectionId
                            simConstruction.questionId = questionId

                            Map additionals = constructionService.getScalingParameters(construction)

                            if (additionals) {
                                constructionDataset.additionalQuestionAnswers = additionals
                            }

                            if (simConstruction.userGivenCost != null) {
                                constructionDataset.userSetCost = true
                                constructionDataset.userSetTotalCost = true
                                if (constructionDataset.additionalQuestionAnswers) {
                                    if (draftSimConstruction) {
                                        constructionDataset.additionalQuestionAnswers.put("costPerUnit", draftSimConstruction?.userGivenCost ?: simConstruction.userGivenCost)
                                        constructionDataset.additionalQuestionAnswers.put("totalCost", draftSimConstruction?.calculatedCost?: simConstruction.userGivenCost * constructionQuantity)
                                    } else {
                                        constructionDataset.additionalQuestionAnswers.put("costPerUnit", simConstruction.userGivenCost)
                                        constructionDataset.additionalQuestionAnswers.put("totalCost", simConstruction.userGivenCost * constructionQuantity)
                                    }

                                } else {
                                    if (draftSimConstruction?.calculatedCost) {
                                        constructionDataset.additionalQuestionAnswers = ["costPerUnit": simConstruction.userGivenCost, "totalCost": draftSimConstruction.calculatedCost]
                                    } else {
                                        constructionDataset.additionalQuestionAnswers = ["costPerUnit": simConstruction.userGivenCost, "totalCost": simConstruction.userGivenCost * constructionQuantity]
                                    }
                                }
                            }

                            //*****************************************************************************************
                            //inheritToChildren question from construction to constituents
                            //*****************************************************************************************
                            List<Question> additionalQuestions

                            if (additionalQuestionsPerQuestion.keySet()?.toList()?.contains(constructionDataset.questionId)) {
                                additionalQuestions = additionalQuestionsPerQuestion.get(constructionDataset.questionId)
                            } else {
                                additionalQuestions = getInheritedAdditionalQuestion(queryId, sectionId, questionId, indicator)
                                additionalQuestionsPerQuestion.put((constructionDataset.questionId), additionalQuestions?:[])
                            }

                            //*****************************************************************************************

                            if (constructionQuantity) {
                                entityDatasets.add(constructionDataset)
                            } else {
                                skipCalculationDatasets.add(constructionDataset)
                            }

                            if (construction.datasets) {
                                Question mainQuestion = questionService.getQuestion(queryId, questionId)

                                Map defaultConstituentMap = getDefaultConstituentMap(construction, simConstruction)
                                // Creates the constituent datasets from the original construction
                                ResourceCache resourceCache = ResourceCache.init(construction.datasets)
                                for (Dataset constructionSubSet: construction.datasets) {
                                    Dataset dataset = new Dataset()
                                    dataset.manualId = new ObjectId().toString()
                                    dataset.resourceId = constructionSubSet.resourceId
                                    dataset.constructionValue = constructionSubSet.quantity.toString()
                                    dataset.userGivenUnit = constructionSubSet.userGivenUnit
                                    dataset.originalAnswer = "${constructionSubSet.quantity ?: 0}"
                                    dataset.quantity = (constructionSubSet.quantity && constructionDataset.quantity) ? constructionSubSet.quantity * constructionDataset.quantity : 0
                                    dataset.answerIds = [dataset.quantity.toString() ?: "0"]
                                    dataset.profileId = constructionSubSet.profileId
                                    Resource resource = resourceCache.getResource(dataset)
                                    if (simConstruction.isCreatedFromPrivateConstruction||simConstruction.dynamicDefault) {
                                        // TODO FIX CANT LOAD CONSTITUENTS FROM DRAFT?
                                        dataset.additionalQuestionAnswers = [datasetIdFromConstruction: dataset.manualId]
                                    } else {
                                        dataset.additionalQuestionAnswers = [datasetIdFromConstruction: constructionSubSet.manualId]
                                    }

                                    constructionSubSet.additionalQuestionAnswers?.each { String key, def value ->
                                        dataset.additionalQuestionAnswers.put(key, value)
                                    }

                                    //Inherited Additional Question
                                    if (additionalQuestions) {
                                        additionalQuestions.each { Question additionalQuestion ->
                                            if (additionalQuestion.inheritToChildren) {
                                                if (construction.additionalQuestionAnswers?.get(additionalQuestion.questionId) != null) {
                                                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, construction.additionalQuestionAnswers?.get(additionalQuestion.questionId))
                                                } else if (!dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                                                    String answer

                                                    if (additionalQuestion.questionId == Constants.DEFAULT_SERVICELIFE) {
                                                        if (questionService.isWithPermanentServiceLife(mainQuestion, indicator)) {
                                                            answer = "permanent"
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID.equals(additionalQuestion.questionId)) {
                                                        if ("00".equals(localCompensationMethodVersion)) {
                                                            if (resourceService.getIsLocalResource(resource?.areas)) {
                                                                if (projectCountry) {
                                                                    answer = "${projectCountry.resourceId}"
                                                                }
                                                            } else {
                                                                answer = "noLocalCompensation"
                                                            }
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID.equals(additionalQuestion.questionId)) {
                                                        if ("00".equals(localCompensationMethodVersion)) {
                                                            if (resourceService.getIsLocalResource(resource?.areas)) {
                                                                if (projectCountry) {
                                                                    String defaultProfileId = optimiResourceService.getResourceByResourceAndProfileId(projectCountry.countryEnergyResourceId, null)?.profileId

                                                                    if (defaultProfileId) {
                                                                        answer = "${projectCountry.countryEnergyResourceId}.${defaultProfileId}"
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        answer = "${aq.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: resource)}"
                                                    }
                                                    
                                                    if (answer != null) {
                                                        dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, answer)
                                                    }
                                                }
                                            } else if (!dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                                                String answer

                                                if (additionalQuestion.questionId == "serviceLife") {
                                                    if (questionService.isWithPermanentServiceLife(mainQuestion, indicator)) {
                                                        answer = "permanent"
                                                    } else {
                                                        if (design?.defaults?.get(Constants.DEFAULT_SERVICELIFE)) {
                                                            answer = "default"
                                                        } else {
                                                            answer = newCalculationServiceProxy.getServiceLife(dataset, resource, design, indicator)?.toString()
                                                        }
                                                    }
                                                } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID.equals(additionalQuestion.questionId)) {
                                                    if ("00".equals(localCompensationMethodVersion)) {
                                                        if (resourceService.getIsLocalResource(resource?.areas)) {
                                                            if (projectCountry) {
                                                                answer = "${projectCountry.resourceId}"
                                                            }
                                                        } else {
                                                            answer = "noLocalCompensation"
                                                        }
                                                    }
                                                } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID.equals(additionalQuestion.questionId)) {
                                                    if ("00".equals(localCompensationMethodVersion)) {
                                                        if (resourceService.getIsLocalResource(resource?.areas)) {
                                                            if (projectCountry) {
                                                                String defaultProfileId = optimiResourceService.getResourceByResourceAndProfileId(projectCountry.countryEnergyResourceId, null)?.profileId

                                                                if (defaultProfileId) {
                                                                    answer = "${projectCountry.countryEnergyResourceId}.${defaultProfileId}"
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    answer = "${aq.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: resource)}"
                                                }

                                                if (answer != null) {
                                                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, answer)
                                                }
                                            }
                                        }
                                    }

                                    if (!dataset.additionalQuestionAnswers?.get(com.bionova.optimi.core.Constants.EOL_QUESTIONID) && !additionalQuestions?.find({com.bionova.optimi.core.Constants.EOL_QUESTIONID.equals(it.questionId)})) {
                                        String eolProcessId = eolProcessService.getEolProcessForDataset(null, resource, resourceService.getSubType(resource), projectCountry, null, lcaModel, eolProcessCache)?.eolProcessId

                                        if (eolProcessId) {
                                            dataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.EOL_QUESTIONID, eolProcessId)
                                        }
                                    }

                                    if (simConstruction.userGivenCost != null) {
                                        dataset.userSetCost = true
                                        dataset.userSetTotalCost = true
                                        dataset.additionalQuestionAnswers.put("costPerUnit", "0")
                                        dataset.additionalQuestionAnswers.put("totalCost", "0")
                                    }

                                    /* parentConstructionValues copied */
                                    dataset.uniqueConstructionIdentifier = constructionDataset.uniqueConstructionIdentifier
                                    dataset.seqNr = constructionDataset.seqNr
                                    dataset.parentConstructionId = simConstruction.oid

                                    if (simConstruction.dynamicDefault) {
                                        dataset.questionId = constructionSubSet.questionId
                                        dataset.sectionId = constructionSubSet.sectionId
                                        dataset.queryId = constructionSubSet.queryId
                                    } else {
                                        dataset.questionId = constructionDataset.questionId
                                        dataset.sectionId = constructionDataset.sectionId
                                        dataset.queryId = constructionDataset.queryId
                                    }
                                    dataset.defaultConstituent = simConstruction.isCreatedFromPrivateConstruction||simConstruction.dynamicDefault ? constructionSubSet.defaultConstituent : isDefaultForRegion(constructionSubSet, defaultConstituentMap) //constructionSubSet.defaultConstituent //remember to set default constituent
                                    dataset.groupId = constructionSubSet.groupId //and groupid

                                    // TODO DATASET GROUPID FOR PRIVATE CONSTRUCTION????

                                    //Set the thickness from the Draft saved in mongo ???? I dont like this way i should think more about this ????
                                    SimulationToolConstituent draftConstituent = draftSimConstruction?.constituents?.find({it.manualId?.equals(constructionSubSet.manualId)})

                                    if (simConstruction.isCreatedFromPrivateConstruction) {
                                        draftConstituent = draftSimConstruction?.constituents?.find({it.originalManualId?.equals(constructionSubSet.manualId)})
                                    } else {
                                        draftConstituent = draftSimConstruction?.constituents?.find({it.manualId?.equals(constructionSubSet.manualId)})
                                    }

                                    if (draftConstituent) {
                                        dataset.defaultConstituent = simConstruction.isCreatedFromPrivateConstruction||simConstruction.dynamicDefault ? constructionSubSet.defaultConstituent : draftConstituent.defaultConstituent
                                        dataset.additionalQuestionAnswers.put("thickness_mm", draftConstituent.thickness)
                                        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                                            dataset.additionalQuestionAnswers.put("thickness_in", draftConstituent.thickness != null ? draftConstituent.thickness * conversionUnitFactor(unitSystem, "mm") : null)
                                        }

                                        String comment = draftConstituent.comment
                                        if(comment) {
                                            dataset.additionalQuestionAnswers.put("comment", comment)
                                        }

                                    }

                                    if (dataset.validate()) {
                                        if (constructionQuantity) {
                                            entityDatasets.add(dataset)
                                        } else {
                                            skipCalculationDatasets.add(dataset)
                                        }
                                    } else {
                                        log.error("failed to add constructionSubDatasets in import ${dataset.errors.getAllErrors()}")
                                        flashService.setErrorAlert("failed to add constructionSubDatasets in import ${dataset.errors.getAllErrors()}", true)
                                    }
                                }
                            } else {
                                log.error("SimulationTool: No dataset defined for construction: ${construction.constructionId}")
                                flashService.setErrorAlert("SimulationTool: No dataset defined for construction: ${construction.constructionId}", true)
                            }
                            seqNr++
                        }
                    }
                }

                if (tempEntity.defaults?.get(Constants.DEFAULT_TRANSPORT) && indicators) {
                    List<String> transportDistanceQuestionIds = com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list()
                    transportDistanceQuestionIds = datasetService.handlePopulatingDefaultsForTransportationLeg2(tempEntity, transportDistanceQuestionIds)
                    List<String> alreadySetQuestions = []
                    indicators.each { Indicator ind ->
                        List<String> additionalQuestions = ind.indicatorQueries?.collect({
                            it.additionalQuestionIds
                        })?.flatten()?.unique()

                        if (additionalQuestions) {
                            List<String> transportQuestions = additionalQuestions.findAll({
                                transportDistanceQuestionIds.contains(it)
                            })

                            if (transportQuestions) {
                                transportQuestions.each { String questionId ->
                                    if (!alreadySetQuestions.contains(questionId)) {
                                        entityDatasets?.each {
                                            if (it.parentConstructionId) {
                                                if (it.additionalQuestionAnswers) {
                                                    it.additionalQuestionAnswers.put((questionId), "default")
                                                } else {
                                                    it.additionalQuestionAnswers = [(questionId): "default"]
                                                }
                                            }
                                        }
                                        skipCalculationDatasets.each {
                                            if (it.parentConstructionId) {
                                                if (it.additionalQuestionAnswers) {
                                                    it.additionalQuestionAnswers.put((questionId), "default")
                                                } else {
                                                    it.additionalQuestionAnswers = [(questionId): "default"]
                                                }
                                            }
                                        }
                                        alreadySetQuestions.add(questionId)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            long now = System.currentTimeMillis()

            tempEntity = datasetService.preHandleDatasets(tempEntity, entityDatasets, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)

            Dataset assessment

            if (assessmentPeriod && indicator.assessmentPeriodValueReference) {
                assessment = createAssessmentDataset(indicator.assessmentPeriodValueReference, assessmentPeriod)

                if (assessment) {
                    tempEntity.datasets.add(assessment)
                }
            }

            Entity skipcalcDatasetsEntity = new Entity()
            skipcalcDatasetsEntity = datasetService.preHandleDatasets(skipcalcDatasetsEntity, skipCalculationDatasets, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
            String displayRuleId = indicator.displayResult


            tempEntity = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parentEntity, tempEntity, Boolean.TRUE, [indicator.displayResult])

            log.info("CALCULATED IN: ${System.currentTimeMillis() - now}ms")

            if (skipcalcDatasetsEntity.datasets && tempEntity.datasets) {
                tempEntity.datasets.addAll(skipcalcDatasetsEntity.datasets)
            }
            now = System.currentTimeMillis()

            List<CalculationResult> resultsForIndicator = tempEntity.tempCalculationResults?.findAll({
                it.calculationRuleId.equals(displayRuleId)
            })

            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parentEntity)
            Denominator overallDenominator = indicator.resolveDenominators?.find({
                "overallDenominator".equalsIgnoreCase(it.denominatorType)
            })
            Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(tempEntity, overallDenominator)

            modelGroups.each { SimulationToolConstructionGroup simGroup ->
                simGroup.constructions?.each { SimulationToolConstruction simConstruction ->
                    simConstruction = getCo2Impact(tempEntity, indicator, simConstruction, resultsForIndicator, null, parentEntity, resultCategories, overallDenomValue)
                }
            }
            log.info("CO 2 IMPACT HANDLED IN: ${System.currentTimeMillis() - now}ms")

            if (lccIndicator) {
                if (assessmentPeriod && lccIndicator.assessmentPeriodValueReference) {
                    if (assessment) {
                        if (!(assessment.queryId?.equals(lccIndicator.assessmentPeriodValueReference.queryId) &&
                                assessment.sectionId?.equals(lccIndicator.assessmentPeriodValueReference.sectionId) &&
                                assessment.questionId?.equals(lccIndicator.assessmentPeriodValueReference.questionId))) {
                            Dataset lccAssessment = createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, assessmentPeriod)
                            tempEntity.datasets.add(lccAssessment)
                        }
                    } else {
                        Dataset lccAssessment = createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, assessmentPeriod)
                        tempEntity.datasets.add(lccAssessment)
                    }
                }

                // Calculate lcc results
                tempEntity = newCalculationServiceProxy.calculate(null, lccIndicator.indicatorId, parentEntity, tempEntity, Boolean.TRUE, [lccIndicator.displayResult])

                List<ResultCategory> lccResultCategories = lccIndicator.getResolveResultCategories(parentEntity)

                modelGroups.each { SimulationToolConstructionGroup simGroup ->
                    simGroup.constructions?.each { SimulationToolConstruction simConstruction ->
                        simConstruction = getCost(tempEntity, lccIndicator, simConstruction, parentEntity, lccResultCategories)
                    }
                }
            }
        }
    }

    public Dataset createAssessmentDataset(ValueReference assessmentPeriodValueReference, Integer assessmentPeriod) {
        Dataset dataset

        if (assessmentPeriodValueReference && assessmentPeriod) {
            dataset = new Dataset()
            dataset.manualId = new ObjectId().toString()
            dataset.queryId = assessmentPeriodValueReference.queryId
            dataset.sectionId = assessmentPeriodValueReference.sectionId
            dataset.questionId = assessmentPeriodValueReference.questionId
            dataset.quantity = assessmentPeriod
            dataset.answerIds = ["${assessmentPeriod}"]
        }
        return dataset
    }

    public def getCost(Entity tempEntity, Indicator indicator, SimulationToolConstruction simConstruction, Entity parentEntity, List<ResultCategory> resolvedResultCategories = null) {
        String displayRuleId = indicator.displayResult
        List<ResultCategory> resultCategories = resolvedResultCategories ?: indicator.getResolveResultCategories(parentEntity)
        List<CalculationResult> resultsForIndicator = tempEntity.tempCalculationResults?.findAll({
            it.calculationRuleId.equals(displayRuleId)
        })

        Dataset construction = tempEntity.datasets?.find({
            !it.parentConstructionId && it.uniqueConstructionIdentifier?.endsWith(simConstruction.oid)
        })

        if (construction) {
            Double calculatedCost = 0

            resultCategories?.findAll({!it.virtual})?.each {
                Double resultForCategory = tempEntity.getResultForDataset(construction.manualId, indicator.indicatorId, it.resultCategoryId, displayRuleId, resultsForIndicator)

                if (resultForCategory) {
                    calculatedCost = calculatedCost + resultForCategory
                }
            }
            simConstruction.calculatedCost = calculatedCost.round(2)
        }

        return simConstruction
    }

    def createDynamicConstruction(Resource r, String unit, CarbonDesignerDefaultValueConstituents defaultConstituent, String constructionId = null) {
        Construction construction = new Construction()
        construction.constructionId = constructionId ?: "constructionId${new ObjectId().toString()}"
        construction.nameEN = optimiResourceService.getUiLabel(r)
        construction.unit = unit
        construction.dynamicDefaultConstruction = true
        construction.datasets = []
        construction.mirrorResourceId = defaultConstituent.defaultValueResource
        Dataset dataset = new Dataset()
        dataset.manualId = new ObjectId().toString()
        dataset.resourceId = defaultConstituent.defaultValueResource
        dataset.quantity = defaultConstituent.quantityMultiplier
        dataset.answerIds = ["${defaultConstituent.quantityMultiplier}"]
        dataset.userGivenUnit = defaultConstituent.unit
        dataset.queryId = defaultConstituent.target.get("queryId")
        dataset.sectionId = defaultConstituent.target.get("sectionId")
        dataset.questionId = defaultConstituent.target.get("questionId")
        dataset.defaultConstituent = true
        construction.datasets.add(dataset)
        return construction
    }

    private def getCo2Impact(Entity tempEntity, Indicator indicator, SimulationToolConstruction simConstruction, List<CalculationResult> results = null, Boolean draftPresent = Boolean.FALSE, Entity parentEntity,
                             List<ResultCategory> resolvedResultCategories = null, Double resolvedDenominator = null) {

        String constructionId = simConstruction?.oid
        String displayRuleId = indicator.displayResult
        List<ResultCategory> resultCategories = resolvedResultCategories ?: indicator.getResolveResultCategories(parentEntity)
        List<CalculationResult> resultsForIndicator = results ? results : tempEntity.tempCalculationResults?.findAll({
            it.calculationRuleId.equals(displayRuleId)
        })

        List<SimulationToolConstituent> constituents = []

        List<Dataset> datasets = tempEntity.datasets?.findAll({ constructionId.equals(it.parentConstructionId) })?.toList()
        ResourceCache resourceCache = ResourceCache.init(datasets)
        for (Dataset d: datasets)
        {
            // OriginalConstructionid passes since its a private dataset from user

            if (d?.defaultConstituent || simConstruction.isCreatedFromPrivateConstruction || simConstruction.dynamicDefault || resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resourceCache.getResource(d), d.queryId, indicator, null, tempEntity)) {
                Double carbonDataImpact

                resultCategories?.findAll({
                    !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty()
                })?.each { ResultCategory category ->
                    Double value = tempEntity.getResultForDataset(d.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                    if (value) {
                        carbonDataImpact = carbonDataImpact ? carbonDataImpact + value : value
                    }
                }

                if (carbonDataImpact != null) {
                    Boolean dontDivide = Boolean.FALSE

                    if (resolvedDenominator != null) {
                        carbonDataImpact = carbonDataImpact * resolvedDenominator
                    }

                    if (("traciGWP_tn").equalsIgnoreCase(displayRuleId) || ("GWP_tn").equalsIgnoreCase(displayRuleId)) {
                        dontDivide = Boolean.TRUE
                    }
                    if (!dontDivide) {
                        carbonDataImpact = (carbonDataImpact / 1000)
                    }
                }

                SimulationToolConstituent constituent = build(
                        new SimulationToolConstituent(co2e: carbonDataImpact ?: 0),
                        d, simConstruction.isCreatedFromPrivateConstruction
                )
                constituents.add(constituent)
            }
        }
        simConstruction?.constituents = constituents
        return simConstruction
    }

    SimulationToolConstituent build(SimulationToolConstituent constituent, Dataset innerConstituent, Boolean isCreatedFromPrivateConstruction) {
        if (constituent && innerConstituent) {
            constituent.manualId = innerConstituent.additionalQuestionAnswers?.get("datasetIdFromConstruction")
            constituent.defaultConstituent = innerConstituent.defaultConstituent
            constituent.name = datasetService.getLocalizedLabel(innerConstituent)
            if (isCreatedFromPrivateConstruction) {
                constituent.groupId = innerConstituent.manualId
            } else {
                constituent.groupId = innerConstituent.groupId
            }
            Resource resource = datasetService.getResource(innerConstituent)
            constituent.thickness = (datasetService.getThickness(innerConstituent.additionalQuestionAnswers)) ? datasetService.getThickness(innerConstituent.additionalQuestionAnswers) :
                    (resource?.defaultThickness_mm)
            constituent.allowVariableThickness = resource?.allowVariableThickness
            constituent.userGivenUnit = innerConstituent.userGivenUnit

            constituent.modThickness = (constituent.thickness != null &&
                    constituent.allowVariableThickness && constituent.userGivenUnit?.equals("m2"))

//            constituent.amount = innerConstituent.quantity
            constituent.setAmount(innerConstituent.quantity)

            constituent.resourceId = innerConstituent?.resourceId
            constituent.profileId = innerConstituent?.profileId
            constituent.queryId = innerConstituent.queryId
            constituent.sectionId = innerConstituent.sectionId
            constituent.questionId = innerConstituent.questionId

            constituent.comment = innerConstituent.comment

            return constituent
        } else {
            return null
        }
    }

    //******************************************************************************************************
    //ENERGY SECTION
    //******************************************************************************************************

    Map<String, String> getTargetByResourceId(Query query, String resourceId) {

        Map<String, String> target
        Resource r = Resource.findByResourceId(resourceId)

        if (r?.resourceGroup) {
            target = query?.entityTypeEnergyAllocationList?.find {
                it.resourceGroupList?.intersect(r?.resourceGroup)
            }?.target
        }

        if (!target) {
            log.error("Simulation Tool: No target query defined for ResourceId: ${resourceId}")
            flashService.setErrorAlert("Simulation Tool: No target query defined for ResourceId: ${resourceId}", true)
        }
        //?????I SHOULD VALIDATE THE TARGET?????

        return target
    }

    def energyResourceImpact(SimulationToolEnergyType energyType, Indicator indicator, Entity tempEntity, Map<String, String> target, Entity design, Entity parentEntity, Indicator lccIndicator, Integer assessmentPeriod) {

        String resourceId = energyType?.defaultOption
        Double quantity = energyType?.purchase ?: 0
        String typeId = energyType?.typeId

        Dataset dataset = new Dataset()
        dataset.manualId = new ObjectId().toString()
        dataset.resourceId = resourceId
        dataset.quantity = quantity
        dataset.originalAnswer = "${quantity}"
        dataset.answerIds = [quantity.toString() ?: "0"]
        dataset.userGivenUnit = "kWh" // Always for energy resouces?
        if (typeId) {
            String comment = getLocalizedMessage("simulationTool." + typeId)
            dataset.additionalQuestionAnswers = [comment: comment]
        }

        Double cost
        if (energyType && lccIndicator && !energyType.userGivenCost) {
            cost = costStructureService.getCost(datasetService.getResource(dataset), parentEntity, lccIndicator, "kWh", parentEntity?.costCalculationMethod)
        } else if (energyType && energyType.userGivenCost) {
            cost = energyType.userGivenCost
        }

        if (cost) {
            energyType.userGivenCost = cost
            dataset.userSetCost = true
            dataset.userSetTotalCost = true

            if (dataset.additionalQuestionAnswers) {
                dataset.additionalQuestionAnswers.put("costPerUnit", cost)
                dataset.additionalQuestionAnswers.put("totalCost", energyType.totalCost)
            } else {
                dataset.additionalQuestionAnswers = [costPerUnit: cost, totalCost: energyType.totalCost]
            }
        }
        dataset.queryId = target?.queryId
        dataset.sectionId = target?.sectionId
        dataset.questionId = target?.questionId

        List projectLevelDatasets = tempEntity.datasets?.findAll({ it.projectLevelDataset })?.toList()
        List entityDatasets = []
        entityDatasets.add(dataset)

        if (projectLevelDatasets) {
            entityDatasets.addAll(projectLevelDatasets)
        }

        Dataset assessment

        if (assessmentPeriod && indicator.assessmentPeriodValueReference) {
            assessment = createAssessmentDataset(indicator.assessmentPeriodValueReference, assessmentPeriod)

            if (assessment) {
                entityDatasets.add(assessment)
            }
        }
        String displayRuleId = indicator.displayResult
        Entity tempForCalc = new Entity()
        tempForCalc.defaults = tempEntity.defaults
        tempForCalc.datasets = entityDatasets
        tempForCalc = newCalculationServiceProxy.calculate(null, indicator.indicatorId, null, tempForCalc, Boolean.TRUE, [displayRuleId])

        Double co2e

        List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parentEntity)
        List<CalculationResult> resultsForIndicator = tempForCalc.tempCalculationResults?.findAll({
            it.calculationRuleId.equals(displayRuleId)
        })

        tempForCalc.datasets?.each { Dataset d ->
            Double carbonDataImpact

            resultCategories?.findAll({
                !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty()
            })?.each { ResultCategory category ->
                Double value = tempForCalc.getResultForDataset(d.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                if (value) {
                    carbonDataImpact = carbonDataImpact ? carbonDataImpact + value : value
                }
            }

            if (carbonDataImpact != null) {

                Denominator overallDenominator = indicator?.resolveDenominators?.find({
                    "overallDenominator".equalsIgnoreCase(it.denominatorType)
                })
                Boolean dontDivide = Boolean.FALSE

                if (overallDenominator) {

                    Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(tempEntity, overallDenominator)
                    if (overallDenomValue != null) {
                        carbonDataImpact = carbonDataImpact * overallDenomValue
                    }
                }

                if (("traciGWP_tn").equalsIgnoreCase(displayRuleId) || ("GWP_tn").equalsIgnoreCase(displayRuleId)) {
                    dontDivide = Boolean.TRUE
                }
                if (!dontDivide) {
                    carbonDataImpact = (carbonDataImpact / 1000)
                }

                co2e = carbonDataImpact
            }

        }

        //Remove the old value
        List toRemoveCalc = []
        tempEntity.tempCalculationResults?.each { result ->
            if (result.calculationResultDatasets.find({ it.key == energyType?.manualId })) {
                toRemoveCalc.add(result)
            }
        }
        tempEntity.datasets?.removeAll { it.manualId == energyType?.manualId }
        tempEntity.tempCalculationResults.removeAll(toRemoveCalc)

        //Add the new value
        tempEntity.datasets?.add(dataset)
        if (tempForCalc?.tempCalculationResults) {
            tempEntity.tempCalculationResults.addAll(tempForCalc?.tempCalculationResults)
        }

        Double calculatedCost = 0

        if (lccIndicator) {
            // Calculate lcc results
            if (assessmentPeriod && lccIndicator.assessmentPeriodValueReference) {
                if (assessment) {
                    if (!(assessment.queryId?.equals(lccIndicator.assessmentPeriodValueReference.queryId) &&
                            assessment.sectionId?.equals(lccIndicator.assessmentPeriodValueReference.sectionId) &&
                            assessment.questionId?.equals(lccIndicator.assessmentPeriodValueReference.questionId))) {
                        Dataset lccAssessment = createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, assessmentPeriod)
                        tempForCalc.datasets.add(lccAssessment)
                    }
                } else {
                    Dataset lccAssessment = createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, assessmentPeriod)
                    tempForCalc.datasets.add(lccAssessment)
                }
            }
            tempForCalc = newCalculationServiceProxy.calculate(null, lccIndicator.indicatorId, null, tempForCalc, Boolean.TRUE, [lccIndicator.displayResult])
            String lccDisplayRuleId = lccIndicator.displayResult
            List<ResultCategory> lccResultCategories = lccIndicator.getResolveResultCategories(parentEntity)
            List<CalculationResult> resultsForLCCIndicator = tempForCalc.tempCalculationResults?.findAll({
                it.calculationRuleId.equals(lccDisplayRuleId)
            })

            Dataset energyTypeD = tempForCalc.datasets?.find({ it.manualId == dataset.manualId })

            if (energyTypeD) {
                lccResultCategories?.findAll({!it.virtual})?.each {
                    Double resultForCategory = tempEntity.getResultForDataset(energyTypeD.manualId, lccIndicator.indicatorId, it.resultCategoryId, lccDisplayRuleId, resultsForLCCIndicator)

                    if (resultForCategory) {
                        calculatedCost = calculatedCost + resultForCategory
                    }
                }
            }
        }
        return [manualId: dataset?.manualId, co2e: co2e, tempEntity: tempEntity, cost: cost, calculatedCost: calculatedCost]
    }

    List<SimulationToolEnergyType> energyResource(String energyScenarioId, String buildingTypeId, Double heatedArea, String countryId, Double dut, Double averageTemperature) {

        //List<Resource> energyResources = Resource.findAllByResourceGroupInListAndSimulationToolBuildingTypeAndEnergyScenarioAndActive([this.ENERGY_RESOURCE_GROUP_ID], buildingTypeId, energyScenarioId, true)
        List<Resource> energyResources = Resource.collection.find([resourceGroup: this.ENERGY_RESOURCE_GROUP_ID, simulationToolBuildingType: buildingTypeId, energyScenario: energyScenarioId, active: true])?.collect {
            it as Resource
        }

        List<SimulationToolEnergyType> energyTypeList = energyResources?.findResults {
            (it) ? getEnergyType(energyScenarioId, it, heatedArea, countryId, buildingTypeId, dut, averageTemperature) : null
        }

        return energyTypeList
    }

    //FACTORY FOR THE TYPE OF ENERGY - Should be a Abstract Factory
    SimulationToolEnergyType getEnergyType(String energyScenarioId, Resource res, Double heatedArea, String countryId,
                                           String buildingTypeId, Double dut, Double averageTemperature, Resource resBuilding = null) {

        SimulationToolEnergyType energyType

        if (energyScenarioId == SimulationToolEnergyTypePassivehausBuilder.TYPE) {
            energyType = new SimulationToolEnergyTypePassivehausBuilder().build(res, heatedArea, countryId, buildingTypeId, dut, averageTemperature, resBuilding)
        } else {
            energyType = new SimulationToolEnergyTypeTEKBuilder().build(res, heatedArea, countryId, buildingTypeId, dut, averageTemperature, resBuilding)
        }

        return energyType
    }

    def getEnergyTypeOptions(SimulationToolEnergyType energyType, String indicatorId, String designId) {
        Entity design = entityService.getEntityById(designId)
        IndicatorQuery indicatorQuery = indicatorService.getIndicatorByIndicatorId(indicatorId, true)?.indicatorQueries?.find({ "buildingOperatingEnergyAndWater".equals(it.queryId) })
        Map<String, List<Object>> resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(design, IndicatorQuery.RESOURCE, indicatorQuery)

        String typeId = energyType?.typeId
        String optionCountry = energyType?.optionCountry
        String defaultOption = energyType?.defaultOption

        Map<String, List<String>> allResources = [:]
        List<String> electricity = ["electricity", "NS3720Electricity"]
        List<String> primaryHeat = ["electricity", "NS3720Electricity", "NS3720Heat", "districtHeatGLO"]
        List<String> secondaryHeat = ["electricity", "NS3720Electricity", "NS3720Heat", "heatingFuelsKWh"]
        List<String> cooling = ["electricity", "NS3720Electricity", "districtCooling"]

        allResources.put("electricity", electricity)
        allResources.put("primaryHeat", primaryHeat)
        allResources.put("secondaryHeat", secondaryHeat)
        allResources.put("cooling", cooling)

        List<String> resourceGroupNameList = allResources.get(typeId)
        DBObject query = new BasicDBObject()
        //query.put("dataProperties", [$in: ["LCA","NS3720"]])
        query.put("resourceGroup", [$in: resourceGroupNameList])
        query.put("active", true)

        resourceFilterCriteria?.each { String attribute, List<Object> requiredValues ->
            query.put(attribute, [$in: requiredValues])
        }

        def resources = Resource.collection.find(query)?.toList()

        Set<Map> options = []
        String defaultOptionName
        def a = resources?.findResults {
            String isocountryCode = getIsoFlagPath(it.isoCountryCode, it.isoCodesByAreas)
            String localizedName = getLocalizedNameByResourceDocument(it)

            if (it.resourceId == defaultOption) {
                defaultOptionName = localizedName
            }

            if (it.resourceId != optionCountry) {
                return [value: localizedName, data: [resourceId: it.resourceId, country: isocountryCode]]
            } else {
                options.add([value: localizedName, data: [resourceId: it.resourceId, country: isocountryCode]])
                return null
            }
        }

        if (optionCountry && !defaultOptionName) {
            log.error("""Carbon Designer. The Default Resource Defined For Energy Type is not present in the possible options 
                        [resourceId: ${defaultOption}, typeId ${typeId}, countryId: ${energyType?.countryId}, indicatorId: ${indicatorId}, query was: ${query.toString()}]
                        """)
            flashService.setErrorAlert("""Carbon Designer. The Default Resource Defined For Energy Type is not present in the possible options 
                        [resourceId: ${defaultOption}, typeId ${typeId}, countryId: ${energyType?.countryId}, indicatorId: ${indicatorId}, query was: ${query.toString()}]
                        """, true)
            defaultOptionName = ""
        }

        if (a) {
            options.addAll(a)
        }

        return [options: options, defaultOptionName: defaultOptionName]

    }

    def getLocalizedNameByResourceDocument(Map resourceDocument) {

        String lang = LocaleContextHolder.getLocale().getLanguage().toUpperCase() //DomainObjectUtil.getMapKeyLanguage()
        String localizedName = resourceDocument["name" + lang] ?: resourceDocument["nameEN"]

        return localizedName
    }

    def getIsoFlagPath(String isoCountryCode, Map isoCodesByAreas = null) {

        if (!isoCountryCode) {
            isoCountryCode = isoCodesByAreas?.find { it }?.value
        }
        if (isoCountryCode) {
            isoCountryCode = "/app/assets/isoflags/" + isoCountryCode.toLowerCase() + ".png"
        } else {
            isoCountryCode = "/app/assets/isoflags/globe.png"
        }

        return isoCountryCode
    }


    Map<String, EntityTypeConstructionAllocation> getEntityTypeConstructionAllocationByRegion(String regionReferenceId) {

        List<EntityTypeConstructionAllocation> entityTypeList = EntityTypeConstructionAllocation.findAllByRegionReferenceId(regionReferenceId)

        return entityTypeList?.collectEntries {
            [(it.constructionId): it]
        }
    }

    static String roundToString(Number n) {

        if(n == null) return ""

        Double d = new Double(n)
        d = d ?: 0

        if (!d) {
            return 0
        }

        if (d >= 10) {
            return d.round()
        }
        if (d >= 1 && d < 10) {
            return d.round(1)
        }
        if (d >= 0.01 && d < 1) {
            return d.round(2)
        }

        if (d >= 0 && d < 0.01) {
            return "~" + 0
        }

        if (d < 0) {
            return d.round(2)
        }


    }

    def roundToNumber(Double d, Boolean morePrecision = false, Boolean buildingDimensions = false) {

        if (!d) {
            return 0
        }

        if (buildingDimensions) {
            if (d % 1 == 0) {
                return d.toInteger()
            } else {
                if (d < 1) {
                    def bd = new BigDecimal(d)
                    bd = bd.round(new MathContext(1))
                    return bd.doubleValue()
                } else {
                    d = d.round(1)

                    if (d % 1 == 0) {
                        return d.toInteger()
                    } else {
                        return d
                    }
                }
            }
        } else {
            if (d >= 10) {
                return d.round()
            }
            if (d >= 1 && d < 10) {
                def i = d.toInteger()
                if (i == d) {
                    return i
                } else {
                    return d.round(1)
                }
            }
            if(!morePrecision){
                if (d >= 0.01 && d < 1) {
                    return d.round(2)
                }

                if (d >= 0 && d < 0.01) {
                    return 0
                }
            }else{
                if (d >= 0.001 && d < 1) {
                    return d.round(2)
                }

                if (d >= 0 && d < 0.001) {
                    return 0
                }
            }


            if (d < 0) {
                return d.round(2)
            }
        }
    }

    def importSimulationToolExcel(MultipartFile excelFile) {
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityTypeConstructionAllocation.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(EntityTypeConstructionAllocation.class)
        List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityTypeConstructionAllocation.class, Double)
        List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityTypeConstructionAllocation.class, List)
        List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityTypeConstructionAllocation.class, Boolean)

        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            if (numberOfSheets) {
                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.lastRowNum
                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {
                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        Row headingRow = sheet.getRow(0)
                        Iterator<Cell> cellIterator = headingRow.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            String heading = importMapperService.getCellValue(cell)

                            if (heading) {
                                columnHeadings.put(heading, cell.columnIndex)
                            }
                        }

                        Integer rowNro = 0
                        Row row
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
                            Map rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.each { String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                            }

                            String regionReferenceId = rowAsMap.get("regionReferenceId")
                            String constructionId = rowAsMap.get("constructionId")

                            if (regionReferenceId && constructionId) {
                                EntityTypeConstructionAllocation entityType = new EntityTypeConstructionAllocation()
                                EntityTypeConstructionAllocation oldEntityType = EntityTypeConstructionAllocation.findByRegionReferenceIdAndConstructionId(regionReferenceId, constructionId)

                                if (oldEntityType) {
                                    oldEntityType.delete(flush: true)
                                }

                                rowAsMap.each { String attr, value ->
                                    if (DomainObjectUtil.isNumericValue(value)) {
                                        if (persistingDoubleProperties.contains(attr)) {
                                            value = DomainObjectUtil.convertStringToDouble(value)
                                        } else {
                                            value = DomainObjectUtil.convertStringToDouble(value).toInteger()
                                        }
                                    }

                                    if (persistingListProperties.contains(attr) && value != null) {
                                        List<String> values = []
                                        value.tokenize(",")?.each {
                                            values.add(it.toString().trim())
                                        }
                                        DomainObjectUtil.callSetterByAttributeName(attr, entityType, values)
                                    } else if (booleanProperties?.contains(attr)) {
                                        Boolean boolValue = value.toBoolean()

                                        if (boolValue != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, entityType, boolValue)
                                        }
                                    } else if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, entityType, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, entityType, value, Boolean.TRUE)
                                    }
                                }

                                if (entityType.validate()) {
                                    entityType.fileName = fileName
                                    entityType.save(flush: true, failOnError: true)
                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${entityType.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${entityType.getErrors()?.getAllErrors()}")
                                    }
                                }
                            }
                            rowNro++
                        }
                    }
                }
            }
        } catch (Exception e) {
            returnable.put("error", "${e}")
        }
        return returnable
    }

    def getAllEntityTypeConstructionAllocation() {
        return EntityTypeConstructionAllocation.list()
    }

    def deleteConstructionAllocation(id) {
        boolean deleteOk = false

        if (id) {
            EntityTypeConstructionAllocation constructionAllocation = EntityTypeConstructionAllocation.get(id)

            if (constructionAllocation) {
                constructionAllocation.delete()
                deleteOk = true
            }
        }
        return deleteOk
    }

    SimulationTool findSimulationToolDraft(String entityId, String designId) {

        List<SimulationTool> entitySimulationTools = SimulationTool.findAllByEntityId(entityId)
        SimulationTool simulationTool = entitySimulationTools?.find { it?.designId == designId }

        return simulationTool
    }

    def copySimulationToolFromDesignEntity(String entityId, String originalDesignId, String copyDesignId) {

        List<SimulationTool> entitySimulationTools = SimulationTool.findAllByEntityIdAndDesignId(entityId, originalDesignId)

        entitySimulationTools?.each {
            def simulationTool = new SimulationTool()
            simulationTool.properties = it.properties
            simulationTool.designId = copyDesignId
            if (simulationTool.validate()) {
                simulationTool.save(flush: true, failOnError: true)
            } else {
                log.error("""Carbon Designer - Copy Design --> impossible save Simulation Tool :
                            Input Parameter --> [EnityID: ${entityId}, OriginalDesignId: ${
                    originalDesignId
                }, CopyDesignID: ${copyDesignId}] | 
                            Output Parameter --> [OriginalDesignId: ${it.designId}, CopyDesignID: ${
                    simulationTool.designId
                }]
                            """
                )
                flashService.setErrorAlert("""Carbon Designer - Copy Design --> impossible save Simulation Tool :
                            Input Parameter --> [EnityID: ${entityId}, OriginalDesignId: ${
                    originalDesignId
                }, CopyDesignID: ${copyDesignId}] | 
                            Output Parameter --> [OriginalDesignId: ${it.designId}, CopyDesignID: ${
                    simulationTool.designId
                }]
                            """, true)

            }
        }

    }

    def deleteDesign(String parentEntityId, String designId) {

        List<SimulationTool> simulationToolList = SimulationTool.findAllByEntityIdAndDesignId(parentEntityId, designId)

        if (simulationToolList) {
            return deleteSimulationToolList(simulationToolList)
        }

        return Boolean.TRUE
    }

    Boolean deleteParentEntity(String entityId) {

        List<SimulationTool> simulationToolList = SimulationTool.findAllByEntityId(entityId)

        if (simulationToolList) {
            return deleteSimulationToolList(simulationToolList)
        }

        return Boolean.TRUE
    }

    Boolean deleteSimulationToolList(List<SimulationTool> simulationToolList) {

        Boolean result = Boolean.TRUE

        simulationToolList?.each {
            try {
                it.delete(flush: true, failOnError: true)
            } catch (Exception e) {
                result = Boolean.FALSE
                loggerUtil.error(log,"Carbon Designer -> Error delete [EntityId : ${it.entityId}, DesignId : ${it.designId}] -->", e)
                flashService.setErrorAlert("Carbon Designer -> Error delete [EntityId : ${it.entityId}, DesignId : ${it.designId}] --> ${e.message}", true)
            }
        }

        return result
    }

    String getLocalizedMessage(String code, Object[] args = null) {

        if (!code) {
            return ""
        }

        String localized

        try {
            localized = messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
        } catch (Exception e) {
            loggerUtil.error(log, "Error in getLocalizedMessage:", e)
            flashService.setErrorAlert("Error in getLocalizedMessage: ${e.message}", true)
        }

        return localized ?: code


    }


    static String conversionUnit(String unitSystem, String unit) {

        String convertedUnit

        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
            convertedUnit = conversionUnitTable.get(unit)?.unit
            if (!convertedUnit) {} //throw some exception
        } else {
            convertedUnit = unit
        }

        String htmlRepresentation = unitHTML[convertedUnit]
        return htmlRepresentation? htmlRepresentation : convertedUnit

    }

    static Double conversionUnitFactor(String unitSystem, String unit){

        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
            return conversionUnitTable.get(unit)?.conversionFactor ?: 1
        }else {
            return 1
        }
    }

    def getInheritedAdditionalQuestion(String queryId, String sectionId, String questionId, Indicator indicator){

        Query query = queryService.getQueryByQueryId(queryId, true)
        Question question = query?.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
        List<Question> additionalQuestions = question?.getAdditionalQuestions(indicator, null, null, null)

        return additionalQuestions

    }

    def isDraftPresent(String parentEntityId, String designId) {
        Boolean draftPresent = Boolean.FALSE

        if (parentEntityId && designId) {
            long nrOfDrafts = SimulationTool.collection.count([entityId: parentEntityId, designId: designId])

            if (nrOfDrafts) {
                draftPresent = Boolean.TRUE
            }
        }
        return draftPresent
    }

    //Check if a dataset of a specific Construction is default for a group
    def isDefaultForRegion(Dataset d, Map defaultConstituentMap){

        def manualId = defaultConstituentMap?.get(d.groupId)

        if(manualId && d?.manualId == manualId){
            return true
        }else {
            return false
        }
    }

    //Create a map (for a construction and specific region) as key the groupId and as value the manualId of the default Constituent
    def getDefaultConstituentMap(Construction construction, SimulationToolConstruction simConstruction){

        def groupedByGroupId
        def defaultResources = simConstruction?.defaultResources ?: []

        if(construction.datasets){
            groupedByGroupId = construction.datasets.groupBy {it.groupId ?: ""}

            groupedByGroupId.each {
                Dataset d = it.value?.find{defaultResources?.contains(it.resourceId)}
                if(d){
                    it.value = d.manualId
                }else {
                    def manualId = it.value?.find{it.defaultConstituent}?.manualId ?: ""
                    it.value = manualId //?: (it.value && it.value.size() > 0 ? it.value.first() : "")
                }

            }
        }

        return groupedByGroupId ?: [:]
    }

    def importSimulationToolAreasTemplate(MultipartFile excelFile) {
        Map returnable = [:]
        List<String> missingList
        List<String> notANumberList = []

        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            if (numberOfSheets) {
                Query query = queryService.getQueryByQueryId(QUERY_ID, true)
                missingList = (query?.orderedConstructionGroupList?.groupId) ?: []

                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.lastRowNum
                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {
                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        Row headingRow = sheet.getRow(0)
                        Iterator<Cell> cellIterator = headingRow.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            String heading = importMapperService.getCellValue(cell)

                            if (heading) {
                                columnHeadings.put(heading, cell.columnIndex)
                            }
                        }

                        Integer rowNro = 0
                        Row row
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
                            Map rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.each { String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                                String structureId = rowAsMap.get("structureId")
                                String quantity = rowAsMap.get("Quantity")

                                structureId = structureId?.trim()

                                if (structureId) {
                                    Double val = 0
                                    if (DomainObjectUtil.isNumericValue(quantity)) {
                                        val = DomainObjectUtil.convertStringToDouble(quantity)
                                    } else{
                                        notANumberList.add(structureId)
                                    }
                                    returnable.put(structureId, val)
                                    missingList.removeElement(structureId)
                                }
                            }

                            rowNro++
                        }
                    }
                }
            }
        } catch (Exception e) {
            returnable.put("error", "${e}")
        }

        String exceptionMessage = returnable?.error

        if(exceptionMessage){
            log.info("Error uploading CD template --> Exception message: $exceptionMessage")
            flashService.setErrorAlert("Error uploading CD template --> Exception message: $exceptionMessage", true)
            exceptionMessage = messageSource.getMessage("simulationTool.error_upload_file", null, LocaleContextHolder.getLocale())
        }

        missingList = missingList?.findAll {it}
        notANumberList = notANumberList?.findAll {it}

        String errorMessage = getErrorMessage(exceptionMessage, missingList, notANumberList)

        if(errorMessage) returnable.put("error", errorMessage)

        return returnable
    }

    private String getMissingString(List<String> missingList){

        String message

        if(missingList){
            String missMessage = getLocalizedMessage("simulationTool.isMissig")
            message = missingList.collect{ "${getLocalizedMessage(it)}: $missMessage" }
        }

        return (message ?: "")
    }

    private String getNotANumberString(List<String> notANumberList){

        String message

        if(notANumberList){
            String notNumberMessage = getLocalizedMessage("simulationTool.notNumber")
            message = notANumberList.collect{ "${getLocalizedMessage(it)}: $notNumberMessage" }
        }

        return (message ?: "")

    }

    private String getErrorMessage(String exceptionMessage, List<String> missingList, List<String> notANumberList){

        String errorMessage = exceptionMessage

        if(errorMessage){
            return errorMessage
        } else {
            errorMessage = ""
            String missigString = getMissingString(missingList)
            String notANumberString = getNotANumberString(notANumberList)

            errorMessage = missigString ? missigString + "<br>"  : errorMessage
            errorMessage = notANumberString ? errorMessage + notANumberString : errorMessage

            return errorMessage
        }

    }

    def getModelSimulationUploadTemplate(Map map, String regionId, String indicatorId){

        if(map){
            User user = userService.getCurrentUser(true)
            String unitSystem = user?.unitSystem ?: "metric"

            Map<String, String> output = [:]
            List<String> allowedGroupsForRegion = getAllowedConstructionGroupsForRegionReferenceId(regionId, indicatorId)
            Query query = queryService.getQueryByQueryId(QUERY_ID, true)
            List<EntityTypeConstructionGroupAllocation> orderedConstructionGroupList = query?.orderedConstructionGroupList

            Map<String, Double> calculatedAreas = [foundations: map.foundations?:0, groundSlab: map.groundSlab?:0, undergroundWalls: map.undergroundWalls?:0, externalWalls: map.externalWalls?:0, cladding: map.cladding?:0,
                                                   externalWindows: map.externalWindows?:0, externalDoors: map.externalDoors?:0, roofSlab: map.roofSlab?:0, roof: map.roof?:0, floorSlabs: map.floorSlabs?:0, columns: map.columns?:0,
                                                   beams: map.beams?:0, balconies: map.balconies?:0, staircases: map.staircases?:0, floorFinish: map.floorFinish?:0, ceilingFinish: map.ceilingFinish?:0, frostInsulation: map.frostInsulation?:0,
                                                   shelter: map.shelter?:0, internalWallFinishes: map.internalWallFinishes?:0, ventilation: map.ventilation?:0, heating: map.heating?:0, cooling: map.cooling?:0, electrification: map.electrification?:0,
                                                   heatSource: map.heatSource?:0, water: map.water?:0, safety: map.safety?:0, internalWall: map.internalWall?:0, loadBearingInternalWall: map.loadBearingInternalWall?:0, freshWater: map.freshWater?:0,
                                                   wasteWater: map.wasteWater?:0]

            def model =        [foundationsCheck              : true, groundSlabCheck: true,
                                enclosureCheck                : true, structureCheck: true,
                                finishesCheck                 : true, servicesCheck : true,
                                gfa: map.foundations?:1,
                                unitSystem: unitSystem, calculatedAreas: calculatedAreas,
                                heatedArea: map.heatedArea?:0, onUploadTemplate: true,
                                allowedGroupsForRegion: allowedGroupsForRegion, orderedConstructionGroupList: orderedConstructionGroupList]


            return model
        } else {
            return [:]
        }
    }

    def calculateCostForSimulationToolConstruction(Indicator lccIndicator, SimulationToolConstruction simulationToolConstruction) {

    }

    def setFoundationsDefaultToModel(List<SimulationToolConstructionGroup> modelGroups, String foundationsDefault){

        if(!(modelGroups && foundationsDefault)) return modelGroups

        def group = modelGroups.find {it.oid == "foundations"}

        if(group?.constructions?.find{ it.constructionId == foundationsDefault}){
            group.constructions.each {
                if(it.constructionId == foundationsDefault){
                    it.amount = group?.amount ?: 0
                    it.share = 1
                }
                else{
                    it.amount = 0
                    it.share = 0
                }
            }
        }

        return modelGroups
    }

    def getAllowedConstructionGroupsForRegionReferenceId(String regionReferenceId, String indicatorId) {
        List<String> foundGroups = []

        if (regionReferenceId) {
            List<String> constructionIds = EntityTypeConstructionAllocation.findAllByRegionReferenceId(regionReferenceId)?.collect({it.constructionId})

            if (constructionIds) {
                foundGroups = Construction.findAllByConstructionIdInList(constructionIds)?.collect({it.earlyPhaseToolGroup})?.unique()
            }
        }
        List<EntityTypeConstructionGroupAllocation> defaultsGroups = queryService.getQueryByQueryId(QUERY_ID, true)?.orderedConstructionGroupList?.findAll({"defaultsGroup".equals(it.buildingElement)})

        if (defaultsGroups) {
            defaultsGroups.each { EntityTypeConstructionGroupAllocation group ->
                if ((!group.applicableRegionReferenceIds||group.applicableRegionReferenceIds.contains(regionReferenceId)) && (!group.applicableIndicatorIds||group.applicableIndicatorIds.contains(indicatorId))) {
                    foundGroups.add(group.groupId)
                }
            }
        }
        return foundGroups
    }

    def getRegionOptions(List<CarbonDesignerRegion> regions, String defaultCountry, List<String> availableRegionIds, String indicatorId, Entity design, String unitSystem) {
        List<String> countryList = regions?.collectMany{it.countryIds?:[]}
        def map = getCountryFlagMap(countryList)
        List options = []

        regions.each {
            if (availableRegionIds.contains(it.regionReferenceId)) {
                List<String> foundGroups = getAllowedConstructionGroupsForRegionReferenceId(it.regionReferenceId, indicatorId)
                Map regionMap = [:]
                String country = (it.countryIds?.contains(defaultCountry)) ? defaultCountry : (it.countryIds ? it.countryIds.first() : "")
                Double gifa = carbonDesignerRegionService.getDesignGIFA(design, unitSystem, it) ?: 0
                Double heatedArea = carbonDesignerRegionService.getDesignHeatedArea(design, unitSystem, it) ?: 0
                regionMap.put("value", it.localizedName)
                regionMap.put("data", [resourceId: it.regionReferenceId, country: map[country], allowedConstructionGroups: foundGroups,
                                       hideEarthquakeZone: it.hideEarthquakeZone ? true : false, designGIFA: gifa, designHeatedArea: heatedArea,
                                       surfaceCombles: it.surfaceCombles, nombreDeLogement: it.numberOfAccomodation])
                List<Map> zones = []
                it.earthquakeSettings?.each {
                    zones.add([zone: it.earthquakeZone, compatibleBuildingTypes: it.compatibleBuildingTypeList])
                }
                regionMap.put("earthquakeZones", zones)
                options.add(regionMap)
            }
        }
        return options
    }


    def getCountryFlagMap(List<String> countryList){

        List<Document> resources = []

        if(countryList){
            resources = Resource.collection.find([ resourceId : [$in: countryList]])?.toList() ?: []
        }

        return resources?.collectEntries{[(it.resourceId) : getIsoFlagPath(it.isoCountryCode)]} ?: [:]
    }

    def iconBuildingType(String buildingType){

        def res = Resource.findByResourceIdAndActive(buildingType, true)

        def iconNo = res?.resourceGroup?.find {it.contains("picture")}
        iconNo = iconNo?.replaceAll("picture", "")?.trim()

        if(!iconNo) return ""

        switch(iconNo){
            case "1": return "entitytype-smallhouse";
            case "2": return "entitytype-apartmenthouse";
            case "3": return "entitytype-office"
            case "4": return "entitytype-shop"
            case "5": return "entitytype-hotel"
            case "6": return "entitytype-hospital"
            case "7": return "entitytype-sportcenter"
            case "8": return "entitytype-school"
            case "9": return "entitytype-factory"
            case "10": return "entitytype-portfolio"
            default: return ""

        }
    }

    def filterNegative(Number n){

        //return 0 if null or less then 0 otherwise return n
        return n > 0 ? n : 0
    }


    def getCDScenarioValues(String scenarioId, CarbonDesignerRegion region) {

        String energyScenario
        String materialType

        List<CDRegionDefaultChoice> scenarios = region?.changeDefaultChoicesSets

        if(scenarios && scenarioId){

            def scenario = scenarios.find {it.defaultChoiceId == scenarioId}
            if(scenario){
                energyScenario = scenario.energyScenario
                materialType = scenario.materialType
            }
        }

        return [energyScenario: energyScenario ?: "", materialType: materialType ?: ""]
    }

    def setDefaultMaterialType(List<SimulationToolConstructionGroup> modelGroups, String materialType) {

        modelGroups?.each { group ->
            def isPresent = materialIsPresentInGroup(group, materialType)
            if(isPresent){
                group?.constructions?.each { c ->
                    def found = materialIsPresentInConstruction(c, materialType)
                    if (found){
                        c.amount = group?.amount ?: 0
                        c.share = 1
                    }else {
                        c.amount = 0
                        c.share = 0
                    }

                }
            }
        }


    }

    Boolean materialIsPresentInGroup(SimulationToolConstructionGroup group, String materialType){

        def found
        found = group?.constructions?.find{
            found = materialIsPresentInConstruction(it, materialType)

        }

        return found
    }

    Boolean materialIsPresentInConstruction(SimulationToolConstruction construction, String materialType){

        def found
        def materialList = construction?.materialTypeList

        found = materialList?.find{it == materialType}

        return found
    }

    Double resolveShelter(Double grossFloorArea, String buildingType) {
        Double shelter = 0
        if (grossFloorArea && buildingType) {
            if ("apartmentBuildings".equals(buildingType)) {
                if (grossFloorArea >= 1200) {
                    shelter = grossFloorArea * 0.02
                } else {
                    shelter = 0
                }
            } else {
                if (grossFloorArea >= 1500) {
                    shelter = grossFloorArea * 0.01
                } else {
                    shelter = 0
                }
            }

            if (shelter && shelter < 20) {
                shelter = 20
            }
        }
        return shelter
    }

    SimulationToolConstruction build(SimulationToolConstruction simulationToolConstruction, Construction innerConstruction,
                                     String defaultGroupId, String dynamicResourceId) {
        if (innerConstruction) {
            String localizedLabel = constructionService.getLocalizedLabel(innerConstruction)
            if (innerConstruction.allowedPrivateConstruction) {
                String randomId = new ObjectId().toString()
                simulationToolConstruction.oid = randomId
                simulationToolConstruction.constructionId = "constructionId${randomId}".toString()
                simulationToolConstruction.originalConstructionId = innerConstruction.id.toString()
                simulationToolConstruction.name = "(PRIVATE) ${localizedLabel}".toString()
                simulationToolConstruction.mirrorResourceId = innerConstruction.mirrorResourceId
            } else {
                simulationToolConstruction.constructionId = innerConstruction.constructionId
                simulationToolConstruction.name = localizedLabel
                if (innerConstruction.dynamicDefaultConstruction) {
                    simulationToolConstruction.dynamicDefault = true
                    simulationToolConstruction.dynamicDefaultGroupId = defaultGroupId
                    simulationToolConstruction.oid = new ObjectId().toString()
                    simulationToolConstruction.mirrorResourceId = dynamicResourceId
                } else {
                    simulationToolConstruction.oid = innerConstruction.id.toString()
                    simulationToolConstruction.mirrorResourceId = innerConstruction.mirrorResourceId
                }
            }
            simulationToolConstruction.unit = innerConstruction.unit
            simulationToolConstruction.constituents = [] //ADD HERE INSERT OF THE CONSTITUENTS???????
            simulationToolConstruction.profileId = constructionService.getMirrorResourceObject(innerConstruction)?.profileId
            simulationToolConstruction.comment = innerConstruction.comment
            return simulationToolConstruction
        } else {
            return null
        }
    }
}
