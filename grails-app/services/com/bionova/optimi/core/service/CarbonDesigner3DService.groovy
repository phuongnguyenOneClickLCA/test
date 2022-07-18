package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.FormatterUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.compiler.GrailsCompileStatic
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.TypeCheckingMode
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class CarbonDesigner3DService {

    GrailsApplication grailsApplication
    def entityService
    def carbonDesigner3DService
    def carbonDesigner3DAdminService
    def newCalculationServiceProxy
    def indicatorService
    def resourceFilterCriteriaUtil
    def constructionService
    def optimiResourceService
    def flashService
    def denominatorUtil
    def simulationToolService
    def userService
    def datasetService
    def queryService
    def unitConversionUtil
    def eolProcessService
    def questionService
    def accountImagesService
    def resourceTypeService
    def resourceService

    /**
     * Takes a carbon design, runs through its temp results and calculates the co2e and co2Intensity for all materials
     * and constructions afterwards
     * @param carbonDesign
     * @param indicator
     * @param parentEntity
     * @return
     */
    def calculateCo2eImpact(CarbonDesign carbonDesign, Indicator indicator, Entity parentEntity) {

        Denominator overallDenominator = indicator.resolveDenominators?.find({
            "overallDenominator".equalsIgnoreCase(it.denominatorType)
        })
        Double resolvedDenominator = denominatorUtil.getFinalValueForDenominator(carbonDesign, overallDenominator)

        String displayRuleId = indicator.displayResult
        // Once design is calculated, we need to get the co2 value for each resource, with a calculation result
        List<CalculationResult> resultsForIndicator = carbonDesign.tempCalculationResults?.findAll({
            (it.calculationRuleId == displayRuleId)
        })

        List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parentEntity)

        //Gets the calculated Co2e from results for Materials / Constituent materials
        Set<Dataset> datasets = carbonDesign.datasets?.findAll({Dataset dataset -> dataset.resourceId && !dataset.construction })
        if (datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets as List<Dataset>)
            for (Dataset dataset: datasets)
            {
                if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resourceCache.getResource(dataset), dataset.queryId, indicator, null, carbonDesign)) {
                    Double carbonDataImpact

                    resultCategories?.findAll({
                        !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty()
                    })?.forEach({ ResultCategory category ->
                        Double value = carbonDesign.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                        if (value) {
                            carbonDataImpact = carbonDataImpact ? carbonDataImpact + value : value
                        }
                    })

                    if (carbonDataImpact != null) {
                        Boolean dontDivide = Boolean.FALSE
                        if (resolvedDenominator != null) {
                            carbonDataImpact = carbonDataImpact * resolvedDenominator
                        }
                        if ((Constants.CalculationRuleId.TRACI_GWP_TN.toString()).equalsIgnoreCase(displayRuleId) || (Constants.CalculationRuleId.GWP_TN.toString()).equalsIgnoreCase(displayRuleId)) {
                            dontDivide = Boolean.TRUE
                        }
                        if (!dontDivide) {
                            carbonDataImpact = (carbonDataImpact / 1000)
                        }

                        if (dataset.quantity) {
                            dataset.additionalQuestionAnswers.put(Constants.CO2E, carbonDataImpact)
                            if (carbonDataImpact) {
                                Double co2Intensity = (carbonDataImpact / dataset.quantity) * 1000
                                dataset.additionalQuestionAnswers.put(Constants.CO2_INTENSITY, co2Intensity)
                            }
                        }
                    }
                }
            }
        }

        ///Gets the calculated Co2e for Constructions, from the construction constituent datasets
        carbonDesign.datasets?.findAll({Dataset dataset -> dataset.construction })?.forEach({ Dataset constructionDataset ->
            Set<Dataset> constituentDatasets = carbonDesign.datasets?.findAll({it.parentConstructionId && it.uniqueConstructionIdentifier == constructionDataset.uniqueConstructionIdentifier})?.collect({it as Dataset})
            Double carbonDataImpact = constituentDatasets?.collect({it.additionalQuestionAnswers.get(Constants.CO2E)})?.sum() as Double ?: 0
            constructionDataset.additionalQuestionAnswers.put(Constants.CO2E, carbonDataImpact)
            if (carbonDataImpact && constructionDataset.quantity) {
                Double co2Intensity = (carbonDataImpact / constructionDataset.quantity) * 1000
                constructionDataset.additionalQuestionAnswers.put(Constants.CO2_INTENSITY, co2Intensity)
            }
        })

        return carbonDesign
    }

    /**
     * Calculates the co2e for each element, calculated from datasets. Also updates each datasets co2Share as it needs
     * the total element co2e for the calculation.
     * @param carbonDesign
     * @param buildingElement
     * @return
     */
    def calculateElementCo2eImpact(CarbonDesign carbonDesign, CD3DBuildingElementsPayload buildingElement) {
        Set<Dataset> elementDatasets = carbonDesign.datasets?.findAll({dataset -> buildingElement.elementId == dataset.groupId && !dataset.parentConstructionId })?.collect({it as Dataset})
        Double elementTotalShare = elementDatasets?.collect({it.additionalQuestionAnswers?.get(Constants.SHARE)})?.sum() as Double ?: 0
        Double elementTotalCo2eTons = elementDatasets?.collect({it.additionalQuestionAnswers?.get(Constants.CO2E)})?.sum() as Double ?: 0

        //Using elementTotalCo2e to calculate individual resource co2Share percent for all resources in Building Element
        elementDatasets?.forEach({elementDataset ->
            Double co2e = elementDataset.additionalQuestionAnswers.get(Constants.CO2E) as Double
            if (elementTotalCo2eTons && co2e) {
                Double datasetCo2Share = (co2e / elementTotalCo2eTons) * 100
                if (datasetCo2Share) {
                    elementDataset.additionalQuestionAnswers.put(Constants.CARBON_SHARE, datasetCo2Share)
                }
            }

        })
        buildingElement.elementTotalShare = elementTotalShare
        buildingElement.elementTotalCo2eTons = elementTotalCo2eTons
    }

    /**
     * Calculates the total Co2e for the design + the totalCo2eShare for each element, as it needs the designs total co2e
     * for the calculation, similar to the datasets.
     * @param carbonDesign
     * @return
     */
    def calculateDesignCo2eImpact(CarbonDesign carbonDesign) {
        Double totalCo2e = carbonDesign?.buildingElements?.collect({element -> element.elementTotalCo2eTons})?.sum() as Double ?: 0
        carbonDesign?.buildingElements?.forEach({ element ->
            if (element.elementTotalCo2eTons && totalCo2e) {
                element.elementTotalCo2eShare = (element.elementTotalCo2eTons / totalCo2e) * 100
            }
        })
        carbonDesign.totalCo2e = totalCo2e
    }

    /**
     * Fills up the additionalQuestionAnswers map of datasets with  values from element.elementClassifications map
     * @param carbonDesign
     * @param additionalQuestionAnswers
     * @return additionalQuestionAnswers
     */
    def addElementClassifications(CarbonDesigner3DBuildingElements element, Map<String, Object> additionalQuestionAnswers) {
        if (element.elementClassifications) {
            element.elementClassifications.forEach({ classification, value ->
                additionalQuestionAnswers.put(classification, value)
            })
        }
        return additionalQuestionAnswers
    }

    /**
     * Creates all required datasets for the provided resources per element, this will create a single dataset for a material resource
     * and create a construction dataset, as well as its constituent datasets.
     * This method is used to both create new resources from scratch based on configuration provided data and also create
     * new resources with data from existing datasets (merge/replacing a dataset)
     * @param element
     * @param resourceId
     * @param resourceShare
     * @param seqNr
     * @param indicatorAdditionalQuestions
     * @param comment
     * @return
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Set<Dataset> createResourceDataset(CarbonDesigner3DBuildingElements element, Double elementTotalArea, String resourceId, Double resourceShare, Integer seqNr, String comment = null, CarbonDesign carbonDesign, Entity parentEntity, Indicator indicator) {
        EolProcessCache eolProcessCache = EolProcessCache.init()
        User user = userService.getCurrentUser(true)
        String unitSystem = user?.unitSystem ?: Constants.cd3D.METRIC.get()

        Set<Dataset> carbonDesignDatasets = []
        //Calculating quantity for resources, based on Area * Share%
        Double buildingElementArea = elementTotalArea ?: 0
        Double quantity = (buildingElementArea * resourceShare) / 100

        //Generic values for any dataset Type
        String queryId = element.elementSavingLocation?.get(Constants.cd3D.QUERYID.get())
        String sectionId = element.elementSavingLocation?.get(Constants.cd3D.SECTIONID.get())
        String questionId = element.elementSavingLocation?.get(Constants.cd3D.QUESTIONID.get())
        String elementId = element.elementId.toString()

        Question mainQuestion = questionService.getQuestion(queryId, questionId)
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalQuestions = mainQuestion?.getAdditionalQuestions(indicator, additionalQuestionsQuery, null, null)

        Resource resource = optimiResourceService.getResourceWithParams(resourceId)
        if (resource && !resource.construction) {
            Dataset materialDataset = createMaterialDataset(seqNr, quantity, queryId, sectionId, questionId, resource, elementId, element, unitSystem, resourceShare, comment)
            carbonDesignDatasets.add(materialDataset)
        }

        if (!resource || (resource && resource.construction)) {
            Construction construction
            if (resource) {
                construction = constructionService.getConstruction(resource?.constructionId)
            } else {
                construction = constructionService.getConstructionWithConstructionId(resourceId)
            }

            if (construction) {
                Dataset constructionDataset = createConstructionDataset(seqNr, construction, unitSystem, quantity, queryId, sectionId, questionId, elementId, resourceShare, comment, element)
                carbonDesignDatasets.add(constructionDataset)
                Set<Dataset> constituentDatasets = createConstituentDatasets(construction, unitSystem, constructionDataset, element, carbonDesign, parentEntity, indicator, additionalQuestions, mainQuestion, eolProcessCache)
                carbonDesignDatasets.addAll(constituentDatasets)
            }
        }

        return carbonDesignDatasets
    }

    Dataset createMaterialDataset(int seqNr, double quantity, String queryId, String sectionId, String questionId, Resource resource, String elementId, CarbonDesigner3DBuildingElements element, String unitSystem, double resourceShare, String comment) {
        // Initial dataset for resource and start populating with configuration data
        Dataset materialDataset = new Dataset()
        materialDataset.manualId = new ObjectId().toString()
        materialDataset.seqNr = seqNr
        materialDataset.quantity = quantity
        materialDataset.answerIds = ["${quantity}"]
        materialDataset.queryId = queryId
        materialDataset.sectionId = sectionId
        materialDataset.questionId = questionId
        materialDataset.resourceId = resource.resourceId
        materialDataset.profileId = null
        materialDataset.groupId = elementId
        materialDataset.userGivenUnit = handleUserGivenUnitBasedOnUnitSystem(element.elementRequiredUnit, unitSystem)
        materialDataset.additionalQuestionAnswers = [(Constants.cd3D.RESOURCENAME.get()): optimiResourceService.getUiLabel(resource)]
        materialDataset.additionalQuestionAnswers.put(Constants.SHARE, resourceShare)
        materialDataset.additionalQuestionAnswers.put(Constants.CO2E, 0)

        if (comment) {
            saveDatasetComment(materialDataset, comment)
        }

        materialDataset.additionalQuestionAnswers = addElementClassifications(element, materialDataset.additionalQuestionAnswers)

        //Thickness
        if (resource?.defaultThickness_mm || resource?.defaultThickness_in) {
            materialDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_MM.get(), resource?.defaultThickness_mm)
            if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                materialDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_IN.get(), resource?.defaultThickness_in)
            }
            materialDataset.additionalQuestionAnswers.put(Constants.cd3D.ALLOWVARIABLETHICKNESS.get(), resource?.allowVariableThickness)
        }
        return materialDataset
    }

    Dataset createConstructionDataset(int seqNr, Construction construction, String unitSystem, double quantity, String queryId, String sectionId, String questionId, String elementId, double resourceShare, String comment, CarbonDesigner3DBuildingElements element) {
        Dataset constructionDataset = new Dataset()
        constructionDataset.manualId = new ObjectId().toString()
        constructionDataset.seqNr = seqNr
        constructionDataset.userGivenUnit = handleUserGivenUnitBasedOnUnitSystem(construction.unit, unitSystem)
        constructionDataset.quantity = quantity
        constructionDataset.answerIds = ["${quantity}"]
        constructionDataset.resourceId = construction.mirrorResourceId

        constructionDataset.uniqueConstructionIdentifier = UUID.randomUUID().toString() + construction.id.toString()
        constructionDataset.queryId = queryId
        constructionDataset.sectionId = sectionId
        constructionDataset.questionId = questionId
        constructionDataset.groupId = elementId

        constructionDataset.additionalQuestionAnswers = [(Constants.cd3D.RESOURCENAME.get()): constructionService.getLocalizedLabel(construction)]
        constructionDataset.additionalQuestionAnswers.put(Constants.SHARE, resourceShare)
        constructionDataset.additionalQuestionAnswers.put(Constants.CO2E, 0)

        if (comment) {
            saveDatasetComment(constructionDataset, comment)
        }

        constructionDataset.additionalQuestionAnswers = addElementClassifications(element, constructionDataset.additionalQuestionAnswers)
        construction.additionalQuestionAnswers?.forEach({ String key, Object value ->
            constructionDataset.additionalQuestionAnswers.put(key, value)
        })
        Map scalingParameters = constructionService.getScalingParameters(construction)
        if (scalingParameters) {
            constructionDataset.additionalQuestionAnswers.putAll(scalingParameters)
        }
        return constructionDataset
    }

    Set<Dataset> createConstituentDatasets(Construction construction, String unitSystem, Dataset constructionDataset, CarbonDesigner3DBuildingElements element, CarbonDesign carbonDesign, Entity parentEntity, Indicator indicator, List<Question> additionalQuestions, Question mainQuestion, EolProcessCache eolProcessCache) {
        if (construction.datasets) {
            Set<Dataset> constituentDatasets = []
            ResourceCache resourceCache = ResourceCache.init(construction.datasets)
            ResourceTypeCache resourceTypeCache = resourceCache.getResourceTypeCache()
            for (Dataset originalConstituentDataset : construction.datasets) {
                if (originalConstituentDataset.defaultConstituent || originalConstituentDataset.defaultConstituent == null) {
                    Dataset constituentDataset = new Dataset()
                    constituentDataset.manualId = new ObjectId().toString()
                    constituentDataset.resourceId = originalConstituentDataset.resourceId
                    constituentDataset.constructionValue = originalConstituentDataset.quantity.toString()
                    constituentDataset.userGivenUnit = handleUserGivenUnitBasedOnUnitSystem(originalConstituentDataset.userGivenUnit, unitSystem)
                    constituentDataset.originalAnswer = "${originalConstituentDataset.quantity ?: 0}"
                    if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                        // Convert construction quantity to metric, then convert calculated quantity to Imperial
                        Double originalQuantity = (originalConstituentDataset.quantity && constructionDataset.quantity) ? originalConstituentDataset.quantity * (constructionDataset.quantity / simulationToolService.conversionUnitFactor(unitSystem, construction.unit)) : 0
                        Double convertedQuantity = unitConversionUtil.doConversion(originalQuantity, null, originalConstituentDataset.userGivenUnit, null, null, null, null, null, null, constituentDataset.userGivenUnit)
                        constituentDataset.quantity = convertedQuantity
                    } else {
                        constituentDataset.quantity = (originalConstituentDataset.quantity && constructionDataset.quantity) ? originalConstituentDataset.quantity * constructionDataset.quantity : 0
                    }

                    constituentDataset.answerIds = [constituentDataset.quantity.toString() ?: "0"]
                    constituentDataset.profileId = originalConstituentDataset.profileId

                    constituentDataset.additionalQuestionAnswers = [datasetIdFromConstruction: originalConstituentDataset.manualId]

                    constituentDataset.additionalQuestionAnswers = addElementClassifications(element, constituentDataset.additionalQuestionAnswers)

                    constituentDataset.additionalQuestionAnswers.put(Constants.CO2E, 0)
                    constituentDataset.additionalQuestionAnswers.put(Constants.cd3D.RESOURCENAME.get(), datasetService.getLocalizedLabel(originalConstituentDataset))
                    originalConstituentDataset.additionalQuestionAnswers?.forEach({ String key, Object value ->
                        constituentDataset.additionalQuestionAnswers.put(key, value)
                    })

                    constituentDataset.uniqueConstructionIdentifier = constructionDataset.uniqueConstructionIdentifier
                    constituentDataset.seqNr = constructionDataset.seqNr
                    constituentDataset.parentConstructionId = construction.id.toString()
                    constituentDataset.queryId = constructionDataset.queryId
                    constituentDataset.sectionId = constructionDataset.sectionId
                    constituentDataset.questionId = constructionDataset.questionId
                    constituentDataset.groupId = constructionDataset.groupId

                    //Thickness
                    Double thickness = datasetService.getThickness(originalConstituentDataset.additionalQuestionAnswers)
                    if (thickness) {
                        constituentDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_MM.get(), thickness)
                        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                            constituentDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_IN.get(), thickness * simulationToolService.conversionUnitFactor(unitSystem, "mm"))
                        }
                        constituentDataset.additionalQuestionAnswers.put(Constants.cd3D.ALLOWVARIABLETHICKNESS.get(), resourceCache.getResource(originalConstituentDataset)?.allowVariableThickness)
                    }
                    constituentDataset = carbonDesigner3DService.handleAdditionalQuestionsForDataset(constituentDataset, carbonDesign, parentEntity, indicator, additionalQuestions, mainQuestion, eolProcessCache, resourceCache, resourceTypeCache)
                    constituentDatasets.add(constituentDataset)
                }
            }
            return constituentDatasets
        } else {
            log.error("CarbonDesigner3D: No dataset defined for construction: ${construction.constructionId}")
            flashService.setErrorAlert("CarbonDesigner3D: No dataset defined for construction: ${construction.constructionId}", true)
        }
    }


    /**
     * Sets the datasets userGivenUnit with a value, based on unitSystem and if there is a conversion unit available,
     * if not fall back to provided unit.
     * @param dataset
     * @param providedUnit
     * @param unitSystem
     * @return
     */
    String handleUserGivenUnitBasedOnUnitSystem(String providedUnit, String unitSystem) {
        String unitForDataset
        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
            String imperial = unitConversionUtil.europeanUnitToImperialUnit(providedUnit)

            if (imperial && unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                unitForDataset = imperial
            } else {
                unitForDataset = providedUnit
            }
        } else {
            unitForDataset = providedUnit
        }
        return unitForDataset

    }

    /**
     * Checks the transport questions from additional questions provided from the indicator and creates an
     * additionalQuestionAnswer for the dataset based on each question.
     * @param additionalQuestions
     * @param dataset
     * @return
     */
    @Deprecated
    def getTransportQuestionsForResource(List<Question> additionalQuestions, Dataset dataset) {

        if (additionalQuestions) {
            List<String> transportDistanceQuestionIds = Constants.TransportDistanceQuestionId.list()
            List<String> transportQuestions = additionalQuestions.findAll({
                transportDistanceQuestionIds.contains(it)
            }) as List<String>

            if (transportQuestions && dataset) {
                transportQuestions.forEach({ String questionId ->
                    dataset.additionalQuestionAnswers.put((questionId), "default")
                })
            }
        }
        return dataset
    }

    /**
     * Utility method to create an additionalQuestionAnswers and save the provided comment into the dataset, it can
     * accept empty and blank strings
     * @param dataset
     * @param comment
     * @return
     */
    def saveDatasetComment(Dataset dataset, String comment) {
        if (dataset && dataset.additionalQuestionAnswers) {
            String notNullComment = comment == null || comment.isAllWhitespace() ? "" : comment.trim()
            dataset.additionalQuestionAnswers.put(Constants.cd3D.COMMENT.get(), notNullComment)
        }
    }

    /**
     * Utility method to remove any resource datasets from a particular design, this will check if the provided dataset
     * is a construction and subsequently remove any constituent datasets related to it.
     * @param carbonDesign
     * @param datasetToRemove
     * @return
     */
    def removeResourceDatasetsFromCarbonDesign(CarbonDesign carbonDesign, datasetToRemove) {
        Set<Dataset> datasetsToRemove = []
        if (datasetToRemove.isConstruction()) {
            datasetsToRemove.add(datasetToRemove)
            Set<Dataset> constituentDatasets = carbonDesign.datasets?.findAll({dataset -> dataset.parentConstructionId && dataset.uniqueConstructionIdentifier == datasetToRemove.uniqueConstructionIdentifier})?.collect({it as Dataset})
            datasetsToRemove.addAll(constituentDatasets)
        } else {
            datasetsToRemove.add(datasetToRemove)
        }

        if (datasetsToRemove) {
            carbonDesign.datasets.removeAll(datasetsToRemove)
        }
        return carbonDesign
    }

    /**
     * Calculates the provided datasets inside a temporary entity, in order to speed up the calculative process
     * and then add the new datasets into the carbon design.
     * Used when adding or replacing a resource inside CD3D - Also used when creating a brand new carbonDesign
     * @param carbonDesign
     * @param datasetsToCalculate
     * @param indicator
     * @param parentEntity
     * @return
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def calculateDatasetsWithTempEntity(CarbonDesign carbonDesign, Set<Dataset> datasetsToCalculate, Indicator indicator, Entity parentEntity, Integer calculationPeriod = null) {
        CarbonDesign tempEntityForCalculation = new CarbonDesign()
        tempEntityForCalculation.defaults = parentEntity.defaults
        Set<Dataset> projectLevelDatasets = parentEntity.datasets?.findAll({ it.projectLevelDataset })?.collect({it as Dataset})
        if (projectLevelDatasets) {
            datasetsToCalculate.addAll(projectLevelDatasets)
        }

        tempEntityForCalculation = datasetService.preHandleDatasets(tempEntityForCalculation as Entity, datasetsToCalculate as List<Dataset>, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE) as CarbonDesign

        if (indicator.assessmentPeriodValueReference ) {
            Dataset calculationPeriodDataset
            if (carbonDesign.datasets) {
                calculationPeriodDataset = carbonDesign.datasets.find({dataset -> dataset.questionId == indicator.assessmentPeriodValueReference.questionId})
            } else if (calculationPeriod) {
                calculationPeriodDataset = carbonDesigner3DService.createCalculationPeriodDataset(indicator.assessmentPeriodValueReference, calculationPeriod)
            }
            if (calculationPeriodDataset) {
                tempEntityForCalculation.datasets.add(calculationPeriodDataset)
            }
        }

        tempEntityForCalculation = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parentEntity, tempEntityForCalculation, Boolean.TRUE, [indicator.displayResult])
        tempEntityForCalculation = carbonDesigner3DService.calculateCo2eImpact(tempEntityForCalculation, indicator, parentEntity)

        // Check for newly created design vs already existing
        if (carbonDesign.datasets) {
            carbonDesign.datasets.addAll(tempEntityForCalculation.datasets)
        } else {
            carbonDesign.datasets = tempEntityForCalculation.datasets
        }
        return carbonDesign
    }

    /**
     * Creates and returns the specific dataset for the calculatePeriod based on provided info from the indicator
     * @param assessmentPeriodValueReference
     * @param calculationPeriod
     * @return
     */
    Dataset createCalculationPeriodDataset(ValueReference assessmentPeriodValueReference, Integer calculationPeriod) {
        Dataset dataset
        if (assessmentPeriodValueReference && calculationPeriod) {
            dataset = new Dataset()
            dataset.manualId = new ObjectId().toString()
            dataset.queryId = assessmentPeriodValueReference.queryId
            dataset.sectionId = assessmentPeriodValueReference.sectionId
            dataset.questionId = assessmentPeriodValueReference.questionId
            dataset.quantity = calculationPeriod
            dataset.answerIds = ["${calculationPeriod}"]
        }
        return dataset
    }

    /**
     * Using the payload data, individually calculate all building dimension parameters.
     * Calculations are done in metric, due to default building parameters being scoped in Meters. If the user is using
     * imperial units, the grossFloorArea provided is in SQFT, so we divide the GFA / conversionFactor, to get the M2 value
     * Run all calculations in metrics, then multiply all metric values to get accurate Imperial units at the end.
     * @param requests
     * @return
     */
    def createBuildingDimensionsMap(Object requests) {

        String regionId = requests.regionId
        String buildingTypeId = requests.buildingTypeId
        CarbonDesigner3DBuildingType buildingType = carbonDesigner3DAdminService.getBuildingTypeById(buildingTypeId)

        Double grossFloorArea = requests.grossFloorArea?.toDouble() ?: 0d
        Integer aboveGroundFloors = requests.numOfAboveFloors?.toInteger() ?: 0
        Integer undergroundHeatedFloors = requests.numOfUnderHeatedFloors?.toInteger() ?: 0
        Integer undergroundUnheatedFloors = requests.numOfUnderUnheatedFloors?.toInteger() ?: 0
        Integer undergroundTotalFloors = undergroundHeatedFloors + undergroundUnheatedFloors
        Double structuralMaxSpanWithoutColumns_m = requests.structuralMaxSpanWithoutColumns_m
        Double structuralLoadBearingInternalWallShare = requests.structuralLoadBearingInternalWallShare

        User user = userService.getCurrentUser(true)
        String unitSystem = user?.unitSystem
        grossFloorArea = grossFloorArea / simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M2)

        // All building type params and their default values, either from region specific value or just the general one
        Double maxSpanWithoutColumnsDefault_m = getDefaultValueForParameter(buildingType, "maxSpanWithoutColumns_m", regionId)
        Double defaultInternalFloorHeight = getDefaultValueForParameter(buildingType, "internalFloorHeight_m", regionId)
        Double defaultFloorThickness_m = getDefaultValueForParameter(buildingType, "floorThickness_m", regionId)
        Double defaultEnvelopeThickness_m = getDefaultValueForParameter(buildingType, "envelopeThickness_m", regionId)
        Double defaultRoofShapeEfficiencyFactor = getDefaultValueForParameter(buildingType, "roofShapeEfficiencyFactor", regionId)
        Double lengthDepthRatio = getDefaultValueForParameter(buildingType, "lengthDepthRatio", regionId)
        Double maxBuildingDepth_m = getDefaultValueForParameter(buildingType, "maxBuildingDepth_m", regionId)
        Double buildingShapeEfficiencyFactor = getDefaultValueForParameter(buildingType, "shapeEfficiencyFactor", regionId)
        Double doorsToGroundFloorAreaRatio = getDefaultValueForParameter(buildingType, "doorsToGroundFloorAreaRatio", regionId)
        Double windowsToAboveGroundAreaRatio = getDefaultValueForParameter(buildingType, "windowsToAboveGroundAreaRatio", regionId)
        Double windowsMaximumOfExternalWalls = getDefaultValueForParameter(buildingType, "windowsMaximumOfExternalWalls", regionId)
        Double balconiesToAboveGroundAreaRatio = getDefaultValueForParameter(buildingType, "balconiesToAboveGroundAreaRatio", regionId)
        Double internalWallsToExternalWallsRatio = getDefaultValueForParameter(buildingType, "internalWallsToExternalWallsRatio", regionId)
        Double maxStairCaseDistance_m = getDefaultValueForParameter(buildingType, "maxStairCaseDistance_m", regionId)
        Double loadBearingInternalWallShare = getDefaultValueForParameter(buildingType, "loadBearingInternalWallShare", regionId)
        Double externalPavedAreasPerGFA = getDefaultValueForParameter(buildingType, "externalPavedAreasPerGFA", regionId)

        Double totalFloors = (aboveGroundFloors + undergroundTotalFloors)
        Double floorThickness_m = defaultFloorThickness_m ?: 0d
        Double internalFloorHeight = defaultInternalFloorHeight ?: 0d
        Double singleFloorHeight = (internalFloorHeight + floorThickness_m)
        Double singleFloorGrossArea = (grossFloorArea / totalFloors ?: 1d)
        Double aboveGroundHeight = (aboveGroundFloors * singleFloorHeight)

        Double shorterExternalWallLength = Math.sqrt(singleFloorGrossArea / (lengthDepthRatio ?: 1d))
        shorterExternalWallLength = (shorterExternalWallLength > (maxBuildingDepth_m ?: 0d)) ? (maxBuildingDepth_m ?: 0d) : shorterExternalWallLength

        Double shapeEfficiencyFactor = buildingShapeEfficiencyFactor ?: 0d
        Double longerExternalWallLength = (singleFloorGrossArea) / (shorterExternalWallLength ?: 1d) * (shapeEfficiencyFactor ?: 1d)
        Double envelopeThickness_m = defaultEnvelopeThickness_m ?: 0d
        Double buildingExternalWallLength = 2 * (shorterExternalWallLength + longerExternalWallLength)
        Double singleFloorNetArea = singleFloorGrossArea - (buildingExternalWallLength * envelopeThickness_m)

        Double totalNetArea = requests.designNetFloorArea //TODO - this value doesn't exist currently in configs
        if (!totalNetArea) {
            totalNetArea = singleFloorNetArea * totalFloors
        }

        Double height = aboveGroundHeight
        Double width = longerExternalWallLength ?: 0d
        Double depth = shorterExternalWallLength ?: 0d
        Double maxSpanWithoutColumns_m = structuralMaxSpanWithoutColumns_m ?: maxSpanWithoutColumnsDefault_m
        Double loadBearingShare = structuralLoadBearingInternalWallShare >= 0 ? structuralLoadBearingInternalWallShare : (loadBearingInternalWallShare ?: 0d)
        Double numberStaircases = Math.ceil((longerExternalWallLength / (maxStairCaseDistance_m ?: 1d)) - 1)
        numberStaircases = (numberStaircases > 1) ? numberStaircases : 1d
        numberStaircases = totalFloors > 1 ? numberStaircases : 0d

        Double roofShapeEfficiencyFactor = defaultRoofShapeEfficiencyFactor ?: 0d

        //======= Imperial conversion
        if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
            height = height * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            width = width * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            depth = depth * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            internalFloorHeight = internalFloorHeight * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            maxSpanWithoutColumns_m = maxSpanWithoutColumns_m * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            floorThickness_m = floorThickness_m * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            envelopeThickness_m = envelopeThickness_m * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            maxBuildingDepth_m = maxBuildingDepth_m * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            maxStairCaseDistance_m = maxStairCaseDistance_m * simulationToolService.conversionUnitFactor(unitSystem, Constants.UNIT_M)
            totalNetArea = unitConversionUtil.doConversion(totalNetArea, null, Constants.UNIT_M2, null, null, null, null, null, null, Constants.UNIT_SQFT)
        }

        Map<String, Double> buildingDimensions =
                [height                           : FormatterUtil.setAsZeroIfNegative(height),
                 width                            : FormatterUtil.setAsZeroIfNegative(width),
                 depth                            : FormatterUtil.setAsZeroIfNegative(depth),
                 totalFloors                      : FormatterUtil.setAsZeroIfNegative(totalFloors),
                 internalFloorHeight              : FormatterUtil.setAsZeroIfNegative(internalFloorHeight),
                 floorThickness                   : FormatterUtil.setAsZeroIfNegative(floorThickness_m),
                 totalNetArea                     : FormatterUtil.setAsZeroIfNegative(totalNetArea),
                 maxSpanWithoutColumns            : FormatterUtil.setAsZeroIfNegative(maxSpanWithoutColumns_m),
                 loadBearingShare                 : FormatterUtil.setAsZeroIfNegative(loadBearingShare),
                 envelopeThickness                : FormatterUtil.setAsZeroIfNegative(envelopeThickness_m),
                 roofShapeEfficiencyFactor        : FormatterUtil.setAsZeroIfNegative(roofShapeEfficiencyFactor),
                 lengthDepthRatio                 : FormatterUtil.setAsZeroIfNegative(lengthDepthRatio),
                 maxBuildingDepth                 : FormatterUtil.setAsZeroIfNegative(maxBuildingDepth_m),
                 doorsToGroundFloorAreaRatio      : FormatterUtil.setAsZeroIfNegative(doorsToGroundFloorAreaRatio),
                 windowsToAboveGroundAreaRatio    : FormatterUtil.setAsZeroIfNegative(windowsToAboveGroundAreaRatio),
                 windowsMaximumOfExternalWalls    : FormatterUtil.setAsZeroIfNegative(windowsMaximumOfExternalWalls),
                 balconiesToAboveGroundAreaRatio  : FormatterUtil.setAsZeroIfNegative(balconiesToAboveGroundAreaRatio),
                 internalWallsToExternalWallsRatio: FormatterUtil.setAsZeroIfNegative(internalWallsToExternalWallsRatio),
                 maxStairCaseDistance             : FormatterUtil.setAsZeroIfNegative(maxStairCaseDistance_m),
                 numberStaircases                 : FormatterUtil.setAsZeroIfNegative(numberStaircases),
                 shapeEfficiencyFactor            : FormatterUtil.setAsZeroIfNegative(shapeEfficiencyFactor),
                 externalPavedAreasPerGFA         : FormatterUtil.setAsZeroIfNegative(externalPavedAreasPerGFA)]

        return buildingDimensions
    }

    /**
     * Using payload parameters, will calculate each building element and return a map with all calculated areas
     * Values sent in can be either imperial or metric, the math equals the same
     */
    def createBuildingStructureAreasMap(Object requests) {

        //FormData request parameters
        String buildingTypeId = requests.buildingTypeId
        String regionId = requests.regionId
        String structuralFrameId = requests.structuralFrameId
        CarbonDesigner3DBuildingType buildingType = carbonDesigner3DAdminService.getBuildingTypeById(buildingTypeId)
        CarbonDesigner3DStructuralFrame structuralFrame
        if (structuralFrameId) {
            Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
            structuralFrame = carbonDesignerQuery.getStructuralFrames()?.find({
                structuralFrameFromQuery -> structuralFrameFromQuery.structuralFrameId == structuralFrameId })
        }

        Double coolingkWtom2GFARatio = getDefaultValueForParameter(buildingType, "coolingkWtom2GFARatio", regionId)
        Double heatingkWtom2GFARatio = getDefaultValueForParameter(buildingType, "heatingkWtom2GFARatio", regionId)
        Double externalPavedAreasPerGFA = getDefaultValueForParameter(buildingType, "externalPavedAreasPerGFA", regionId)

        Double grossFloorArea = requests.grossFloorArea ?: 0d
        Integer aboveGroundFloors = requests.numOfAboveFloors?.toInteger() ?: 0
        Integer undergroundHeatedFloors = requests.numOfUnderHeatedFloors?.toInteger() ?: 0
        Integer undergroundUnheatedFloors = requests.numOfUnderUnheatedFloors?.toInteger() ?: 0
        Integer undergroundTotalFloors = undergroundHeatedFloors + undergroundUnheatedFloors

        // Building Dimensions request parameters
        Double height = requests.height
        Double width = requests.width
        Double depth = requests.depth
        Double totalFloors = requests.totalFloors
        Double floorThickness = requests.floorThickness
        Double internalFloorHeight = requests.internalFloorHeight
        Double maxSpanWithoutColumns = requests.maxSpanWithoutColumns
        Double envelopeThickness = requests.envelopeThickness
        Double roofShapeEfficiencyFactor = requests.roofShapeEfficiencyFactor
        Double loadBearingShare = (requests.loadBearingShare / 100)
        Double numberStaircases = requests.numberStaircases

        Double balconiesToAboveGroundAreaRatio = requests.balconiesToAboveGroundAreaRatio
        Double doorsToGroundFloorAreaRatio = requests.doorsToGroundFloorAreaRatio
        Double internalWallsToExternalWallsRatio = requests.internalWallsToExternalWallsRatio
        Double windowsToAboveGroundAreaRatio = requests.windowsToAboveGroundAreaRatio
        Double windowsMaximumOfExternalWalls = requests.windowsMaximumOfExternalWalls

        // Request data, calculated in buildingDimensions
        Double buildingExternalWallLength = 2 * (depth + width)

        Double singleFloorGrossArea = (grossFloorArea) / (totalFloors ?: 1d)
        //=== Maybe need to add the internalFloorHeight = height + totalFloors + floor_m logic here, instead of above
        //internalFloorHeight = internalFloorHeight ?: height && totalFloors ? (height / totalFloors - floorThickness_m) : 0
        Double singleFloorHeight = internalFloorHeight + floorThickness
        Double singleFloorNetArea = singleFloorGrossArea - (buildingExternalWallLength * envelopeThickness)

        Double aboveGroundHeight = height
        Double belowGroundHeight = (undergroundTotalFloors) * (singleFloorHeight)


        //Doors, Facades, Windows and Walls
        Double undergroundWallsArea = buildingExternalWallLength * singleFloorHeight * undergroundTotalFloors
        Double aboveGroundFacadeArea = buildingExternalWallLength * singleFloorHeight * aboveGroundFloors

        Double a = aboveGroundFloors * singleFloorGrossArea * (windowsToAboveGroundAreaRatio ?: 0d)
        Double b = aboveGroundFacadeArea * (windowsMaximumOfExternalWalls ?: 0d)
        Double externalWindowsArea = (a > b) ? b : a
        Double externalDoorsArea = (doorsToGroundFloorAreaRatio ?: 0d) * singleFloorGrossArea
        Double externalWallArea = aboveGroundFacadeArea - externalDoorsArea - externalWindowsArea
        externalWallArea = (externalWallArea > 0) ? externalWallArea : 0d

        // Columns and Beam calculations
        Integer numOfLoadBearingAxes = (Integer) (Math.round(width / (maxSpanWithoutColumns ?: 1)) + 1d)
        numOfLoadBearingAxes = (numOfLoadBearingAxes > 2) ? numOfLoadBearingAxes : 2d // it has to have at minimum one axis on both sides
        Integer numOfColumnsPerAxis = (Integer) (Math.round(depth / (maxSpanWithoutColumns ?: 1)) + 1d)
        numOfColumnsPerAxis = (numOfColumnsPerAxis > 2) ? numOfColumnsPerAxis : 2d // it has to have at minimum one column on both sides

        Double nrOfRequiredColumnsPerFloor = numOfColumnsPerAxis * numOfLoadBearingAxes
        Double lengthOfRequiredColumns = 0d
        Double lengthOfRequiredBeams = 0d

        //Structural frame adjustments to columns and beam calculations
        if (!structuralFrame.columnsPresent) {
            numOfColumnsPerAxis = 0d
            numOfLoadBearingAxes = 0d
            lengthOfRequiredColumns = 0d
        } else if (structuralFrame.columnsPresent && structuralFrame.removeColumnsInPerimeter) {
            lengthOfRequiredColumns = ((nrOfRequiredColumnsPerFloor * totalFloors) * singleFloorHeight) - (((numOfLoadBearingAxes + numOfColumnsPerAxis - 4) * 2) * (totalFloors) * (singleFloorHeight))
        } else {
            lengthOfRequiredColumns = (nrOfRequiredColumnsPerFloor) * (totalFloors) * (singleFloorHeight)
        }

        if (!structuralFrame.beamsPresent) {
            lengthOfRequiredBeams = 0d
        } else if (structuralFrame.beamsPresent && structuralFrame.beamsInBothDirections) {
            lengthOfRequiredBeams = ((numOfLoadBearingAxes * depth) + numOfColumnsPerAxis * width) * totalFloors
        } else {
            lengthOfRequiredBeams = numOfLoadBearingAxes * depth * totalFloors
        }

        //Building Structure - individual element calculations
        Double foundations = grossFloorArea
        Double groundSlab = singleFloorGrossArea
        Double foundationCleanliness = singleFloorGrossArea
        Double undergroundWalls = undergroundWallsArea
        Double externalWalls = externalWallArea
        Double cladding = externalWallArea
        Double externalWindows = externalWindowsArea
        Double externalDoors = externalDoorsArea
        Double roofSlab = singleFloorGrossArea
        Double roof = singleFloorGrossArea * roofShapeEfficiencyFactor
        Double floorSlabs = singleFloorGrossArea * (totalFloors - 1)
        Double columns = lengthOfRequiredColumns
        Double beams = lengthOfRequiredBeams
        Double balcony = (aboveGroundFloors > 1) ? aboveGroundFloors * singleFloorGrossArea * (balconiesToAboveGroundAreaRatio ?: 0d) : 0d
        Double staircases = (aboveGroundHeight + belowGroundHeight) * numberStaircases
        Double floorFinish = (aboveGroundFloors + undergroundHeatedFloors) * singleFloorNetArea
        Double ceilingFinish = floorFinish
        Double heatedArea = requests.designHeatedArea ?: (singleFloorNetArea ?: 0d) * ((aboveGroundFloors ?: 0d) + (undergroundHeatedFloors ?: 0d)) //TODO-- designHeatedArea - not specified yet
        Double frostInsulation = (depth + width) * 2
        Double ventilation = grossFloorArea
        Double heating = grossFloorArea
        Double cooling = (grossFloorArea) * (coolingkWtom2GFARatio)
        Double electrification	= grossFloorArea
        Double heatSource	= (grossFloorArea) * (heatingkWtom2GFARatio)
        Double water = grossFloorArea
        Double safety = grossFloorArea
        Double freshWater = grossFloorArea
        Double wasteWater = grossFloorArea
        Double shelter = simulationToolService.resolveShelter(grossFloorArea, buildingTypeId)
        Double internalWall = aboveGroundFacadeArea * (internalWallsToExternalWallsRatio ?: 0d)
        Double loadBearingInternalWall = internalWall * loadBearingShare
        internalWall = internalWall * (1 - loadBearingShare)
        Double internalWallFinishes = (internalWall * 2) + (loadBearingInternalWall * 2) + externalWallArea

        //structural frame, element calculation support
        Double secondaryBeams = structuralFrame?.secondaryBeamsFactor ? structuralFrame?.secondaryBeamsFactor * beams : 0d
        Double shearWalls = structuralFrame?.shearWallsPercentage ? structuralFrame?.shearWallsPercentage * aboveGroundFacadeArea : 0d
        Double windBracings = structuralFrame?.diagonalWindBracingsFactor ? structuralFrame?.diagonalWindBracingsFactor * aboveGroundFacadeArea : 0d
        Double connectingParts = structuralFrame?.connectingPartsPresent ? grossFloorArea : 0d
        Double secantPilingWall = (depth + width) * 2
        Double otherRMC = structuralFrame?.otherRMCPresent ? singleFloorGrossArea : 0d
        Double soilStabilization = singleFloorGrossArea * 1.1

        //FPI, element calculation support
        Double externalPavedAreas = externalPavedAreasPerGFA * grossFloorArea
        Double sanitarySpaces = grossFloorArea
        Double telecommunications = grossFloorArea
        Double heatingUnit = grossFloorArea
        Double energyNetwork = grossFloorArea
        Double numberElevators = numberStaircases

        Map<String, Double> buildingStructuresMap =
                [foundations            : FormatterUtil.roundToZeroIfNegative(foundations),
                 groundSlab             : FormatterUtil.roundToZeroIfNegative(groundSlab),
                 foundationCleanliness  : FormatterUtil.roundToZeroIfNegative(foundationCleanliness),
                 undergroundWalls       : FormatterUtil.roundToZeroIfNegative(undergroundWalls),
                 externalWalls          : FormatterUtil.roundToZeroIfNegative(externalWalls),
                 cladding               : FormatterUtil.roundToZeroIfNegative(cladding),
                 externalWindows        : FormatterUtil.roundToZeroIfNegative(externalWindows),
                 externalDoors          : FormatterUtil.roundToZeroIfNegative(externalDoors),
                 roofSlab               : FormatterUtil.roundToZeroIfNegative(roofSlab),
                 roof                   : FormatterUtil.roundToZeroIfNegative(roof),
                 floorSlabs             : FormatterUtil.roundToZeroIfNegative(floorSlabs),
                 columns                : FormatterUtil.roundToZeroIfNegative(columns),
                 beams                  : FormatterUtil.roundToZeroIfNegative(beams),
                 balconies              : FormatterUtil.roundToZeroIfNegative(balcony),
                 staircases             : FormatterUtil.roundToZeroIfNegative(staircases),
                 floorFinish            : FormatterUtil.roundToZeroIfNegative(floorFinish),
                 ceilingFinish          : FormatterUtil.roundToZeroIfNegative(ceilingFinish),
                 frostInsulation        : FormatterUtil.roundToZeroIfNegative(frostInsulation),
                 ventilation            : FormatterUtil.roundToZeroIfNegative(ventilation),
                 heating                : FormatterUtil.roundToZeroIfNegative(heating),
                 cooling                : FormatterUtil.roundToZeroIfNegative(cooling),
                 electrification        : FormatterUtil.roundToZeroIfNegative(electrification),
                 heatSource             : FormatterUtil.roundToZeroIfNegative(heatSource),
                 water                  : FormatterUtil.roundToZeroIfNegative(water),
                 safety                 : FormatterUtil.roundToZeroIfNegative(safety),
                 freshWater             : FormatterUtil.roundToZeroIfNegative(freshWater),
                 wasteWater             : FormatterUtil.roundToZeroIfNegative(wasteWater),
                 shelter                : FormatterUtil.roundToZeroIfNegative(shelter),
                 internalWall           : FormatterUtil.roundToZeroIfNegative(internalWall),
                 loadBearingInternalWall: FormatterUtil.roundToZeroIfNegative(loadBearingInternalWall),
                 internalWallFinishes   : FormatterUtil.roundToZeroIfNegative(internalWallFinishes),
                 heatedArea             : FormatterUtil.roundToZeroIfNegative(heatedArea),
                 secondaryBeams         : FormatterUtil.roundToZeroIfNegative(secondaryBeams),
                 shearWalls             : FormatterUtil.roundToZeroIfNegative(shearWalls),
                 windBracings           : FormatterUtil.roundToZeroIfNegative(windBracings),
                 connectingParts        : FormatterUtil.roundToZeroIfNegative(connectingParts),
                 secantPilingWall       : FormatterUtil.roundToZeroIfNegative(secantPilingWall),
                 otherRMC               : FormatterUtil.roundToZeroIfNegative(otherRMC),
                 soilStabilization      : FormatterUtil.roundToZeroIfNegative(soilStabilization),
                 externalPavedAreas     : FormatterUtil.roundToZeroIfNegative(externalPavedAreas),
                 sanitarySpaces         : FormatterUtil.roundToZeroIfNegative(sanitarySpaces),
                 telecommunications     : FormatterUtil.roundToZeroIfNegative(telecommunications),
                 heatingUnit            : FormatterUtil.roundToZeroIfNegative(heatingUnit),
                 energyNetwork          : FormatterUtil.roundToZeroIfNegative(energyNetwork),
                 elevators              : FormatterUtil.roundToZeroIfNegative(numberElevators)]

        return buildingStructuresMap
    }

    /**
     * Gets the default value from buildingType for the selected parameterID, if provided a regionId, it will check if
     * there is a defaultRegionValue and return that if successful.
     * @param buildingType
     * @param parameterId
     * @param regionId
     * @return
     */
    Double getDefaultValueForParameter(CarbonDesigner3DBuildingType buildingType, String parameterId, String regionId) {

        Double defaultRegionValue = null
        if (regionId) {
            defaultRegionValue = buildingType?.defaultData?.find({
                it?.regionDefaultValues?.containsKey(regionId) && it.parameterId == parameterId
            })?.regionDefaultValues?.get(regionId)
        }
        Double defaultValue = buildingType?.defaultData?.find({it?.parameterId == parameterId})?.defaultValue
        return defaultRegionValue ?: defaultValue ?: 0

    }

    /**
     * Rounds to 1 decimal and if no value, returns a Zero
     * @param value
     * @return
     */
    Double roundCarbonShare(Double value) {
        if (!value) {
            return 0
        }
        return value.round(1)
    }

    /**
     * Utility method to collect and filter all private constructions based on the building element query and map them for
     * a payload to the front-end
     * Reason for depreciation: Simply adding a check to filter private dataset in resource query (collectAndMapResourcesBySubtypeForElement)
     * finds all organisation constructions and private datasets
     * @param carbonDesign
     * @param indicator
     * @param buildingElement
     * @param currentUserAccount
     * @return
     */
    @Deprecated
    List<Map<String, String>> collectAndMapPrivateConstructionsForElement(CarbonDesign carbonDesign, Indicator indicator, CarbonDesigner3DBuildingElements buildingElement, Account currentUserAccount) {
        List<Map<String, String>> listOfMappedResources = []

        List<Construction> userPrivateConstructions = constructionService.getConstructionsByAccountId(currentUserAccount?.id?.toString())
        log.info("CarbonDesigner3D: Found - ${userPrivateConstructions?.size()} private constructions from account: ${currentUserAccount?.companyName}")

        //Create the mapped object for each private construction that passed the resourceOkAgainstFilterCriteria
        if (userPrivateConstructions) {
            userPrivateConstructions.forEach({privateConstruction ->
                Map<String, String> allowedPrivateConstructions = [:]
                if (buildingElement?.elementRequiredUnit == privateConstruction?.unit && privateConstruction?.datasets
                        && privateConstruction.constructionId && buildingElement?.allowedSubTypes?.contains(privateConstruction?.resourceSubType)) {
                    Resource mirrorResource = constructionService.getMirrorResourceObject(privateConstruction)
                    if (mirrorResource && mirrorResource.active) {
                        Boolean passedFilterCriteria = Boolean.FALSE
                        String elementQueryId = buildingElement.elementSavingLocation.get(Constants.cd3D.QUERYID.get())
                        if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(mirrorResource, elementQueryId, indicator, null, carbonDesign as Entity)) {
                            passedFilterCriteria = Boolean.TRUE
                        }

                        if (passedFilterCriteria) {
                            allowedPrivateConstructions.put("resourceCategory", "privateConstruction")
                            String brandImageId = privateConstruction.brandImageId
                            if (brandImageId) {
                                log.info("BrandImage added from collectAndMapPrivateConstructionsForElement for a private construction")
                                addBrandImage(brandImageId, allowedPrivateConstructions)
                            }
                            allowedPrivateConstructions.put("resourceId", privateConstruction?.mirrorResourceId)
                            allowedPrivateConstructions.put(Constants.cd3D.RESOURCENAME.get(), constructionService.getLocalizedName(privateConstruction))
                        }
                    }
                }
                if (allowedPrivateConstructions) {
                    listOfMappedResources.addAll(allowedPrivateConstructions)
                }
            })
        }
        return listOfMappedResources
    }

    /**
     * Utility method to find all resources based on subtype, dataProperties (from region) and required unit. Any returned resources,
     * will filter through the resourceOkAgainstFilterCriteria and if successful a map is created of the resource.
     * @param carbonDesign
     * @param indicator
     * @param buildingElement
     * @return
     */
    def collectAndMapResourcesBySubtypeForElement(CarbonDesign carbonDesign, Indicator indicator, CarbonDesigner3DBuildingElements buildingElement, Account currentUserAccount ) {
        List<Map<String, String>> listOfMappedResources = []

        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        String regionId = carbonDesign?.designInfo?.get("regionId")
        CarbonDesigner3DRegion region = carbonDesignerQuery?.carbonDesigner3DRegions?.find({CarbonDesigner3DRegion region -> (region.regionId == regionId) })

        List<String> dataProperties = region?.regionMaterialRequirements?.get("dataProperties")
        List<String> allowedSubTypes = buildingElement?.allowedSubTypes
        String privateDatasetAccountId = currentUserAccount?.id?.toString()

        allowedSubTypes.forEach({ String subType ->

            BasicDBObject query = createDBQuery(subType, dataProperties, privateDatasetAccountId, buildingElement.elementRequiredUnit)
            List<Resource> subTypeResources = Resource.collection.find(query).collect({it as Resource})

            subTypeResources.forEach({ Resource resourceWithSubType ->
                Map<String, String> allowedResourcesMap = [:]
                Boolean passedFilterCriteria = Boolean.FALSE
                String elementQueryId = buildingElement.elementSavingLocation.get(Constants.cd3D.QUERYID.get())
                if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resourceWithSubType, elementQueryId, indicator, null, carbonDesign as Entity)) {
                    passedFilterCriteria = Boolean.TRUE
                }
                if (passedFilterCriteria) {
                    //This field is to display or filter resources in the front-end dropdown or add icons
                    if (resourceWithSubType.construction) {
                        allowedResourcesMap.put("resourceCategory", "construction")
                        String brandImageId = constructionService.getConstructionWithConstructionId(resourceWithSubType.constructionId)?.brandImageId
                        if (brandImageId) {
                            log.info("BrandImage added from collectAndMapResourcesBySubtypeForElement for a construction")
                            addBrandImage(brandImageId, allowedResourcesMap)
                        }
                    } else {
                        allowedResourcesMap.put("resourceCategory", "material")
                        String brandImageId = resourceWithSubType.brandImageId
                        if (brandImageId) {
                            log.info("BrandImage added from collectAndMapResourcesBySubtypeForElement for a material")
                            addBrandImage(brandImageId, allowedResourcesMap)
                        }
                    }
                    if (resourceWithSubType.privateDataset && resourceWithSubType.privateDatasetAccountId) {
                        allowedResourcesMap.put("privateDataset", Boolean.TRUE.toString())
                    }
                    allowedResourcesMap.put("resourceId", resourceWithSubType.resourceId)
                    allowedResourcesMap.put(Constants.cd3D.RESOURCENAME.get(), optimiResourceService.getUiLabel(resourceWithSubType))
                }
                if (allowedResourcesMap) {
                    listOfMappedResources.addAll(allowedResourcesMap)
                }
            })
        })

        return listOfMappedResources
    }

    /**
     * Utility method to map the specific resourceIds put into the building element configurations, as these are specifically
     * entered, they are not filtered through the resourceOkAgainstFilterCriteria method and as long as they have an Id, they
     * will be mapped.
     * @param buildingElement
     * @return
     */
    def collectAndMapSpecificResourcesForElement(CarbonDesigner3DBuildingElements buildingElement ) {
        List<Map<String, String>> listOfMappedResources = []

        List<String> allowedResourceIds = buildingElement?.allowedResources
        if (allowedResourceIds) {
            allowedResourceIds.forEach({resourceId ->
                Document resourceAsDocument = optimiResourceService.getResourceAsDocument(resourceId, null)
                Map<String, String> allowedResourcesMap = [:]
                //If there is no resourceAsDocument, chances are the resource is a construction instead, so failsafe to
                // check if they are and create a map
                if (resourceAsDocument) {
                    if (resourceAsDocument.get("resourceId")) {
                        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
                        String localeName = resourceAsDocument.get("name"+language) ? resourceAsDocument.get("name"+language).toString() : resourceAsDocument.get("nameEN").toString()
                        allowedResourcesMap.put("resourceCategory", "material")
                        allowedResourcesMap.put("resourceId", resourceAsDocument.get("resourceId").toString())
                        allowedResourcesMap.put(Constants.cd3D.RESOURCENAME.get(), localeName)
                        String brandImageId = resourceAsDocument.get("brandImageId").toString()
                        if (brandImageId) {
                            log.info("BrandImage added from collectAndMapSpecificResourcesForElement for a material")
                            addBrandImage(brandImageId, allowedResourcesMap)
                        }
                    }
                } else {
                    Construction construction = constructionService.getConstructionWithConstructionId(resourceId)
                    if (construction && construction.constructionId) {
                        allowedResourcesMap.put("resourceCategory", "construction")
                        allowedResourcesMap.put("resourceId", construction.mirrorResourceId)
                        String brandImageId = construction.brandImageId
                        if (brandImageId) {
                            log.info("BrandImage added from collectAndMapSpecificResourcesForElement for a construction")
                            addBrandImage(brandImageId, allowedResourcesMap)
                        }
                        allowedResourcesMap.put(Constants.cd3D.RESOURCENAME.get(), constructionService.getLocalizedName(construction))
                    }
                }
                if (resourceAsDocument.get("privateDataset") && resourceAsDocument.get("privateDatasetAccountId")) {
                    allowedResourcesMap.put("privateDataset", Boolean.TRUE.toString())
                }
                if (allowedResourcesMap) {
                    listOfMappedResources.addAll(allowedResourcesMap)
                }

            })
        }
        return listOfMappedResources
    }

    /**
     * Injects the brandImage into the allowedResourcesMap, used throughout the various resource related APIS for CD3D
     * @param brandImageId
     * @param allowedResourcesMap
     * @return
     */
    def addBrandImage(String brandImageId, Map<String, String> allowedResourcesMap) {
        if (brandImageId && allowedResourcesMap) {
            String b64BrandImage = accountImagesService.getAccountImageAsB64Data(brandImageId)
            if (b64BrandImage) {
                allowedResourcesMap.put("brandImage", b64BrandImage)
            }
        }
    }

    /**
     * Method will collect and map all questions from all available regions. This means no matter what region is selected
     * in the front-end, no additional calculation is needed
     * @param carbonDesignerRegions
     * @param indicator
     * @return
     */
    List<Document> getAllRegionQuestions(List<CarbonDesigner3DRegion> carbonDesignerRegions, Indicator indicator) {
        List<Document> allQuestions = []
        carbonDesignerRegions.forEach({region ->
            if (region.customSection) {
                region.customSection.questionReferences.forEach({mappedValues ->
                    Query query = queryService.getQueryByQueryId(mappedValues.queryId, true)
                    Question question = query?.getQuestionsBySection(mappedValues.sectionId)?.find({ it.questionId == mappedValues.questionId })
                    Document questionAsDocument = new Document()
                    List<Document> resourceChoices = []
                    if (mappedValues.additionalQuestionId) {
                        List<Question> additionalQuestions = question?.getAdditionalQuestions(indicator, null, null, null)
                        question = additionalQuestions.find({it.questionId == mappedValues.additionalQuestionId})
                    }
                    if (question.resourceGroups && !mappedValues.resourceId) {
                        resourceChoices = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(question.resourceGroups, null, null, null, null, null)
                                .collect({ resource -> ["answerId": resource.resourceId, "answer": resourceService.getUiLabel(resource)] as Document})
                    }
                    if (mappedValues.resourceId) {
                        questionAsDocument.putAll([questionId  : mappedValues.resourceId, questionName: question.question,
                                                   inputType   : "text", help: question.help, validation: [type: "numeric", min: "0"],
                                                   optional    : question.optional, unit: question.unit
                        ])
                    } else {
                        questionAsDocument.putAll([questionId  : question.questionId, questionName: question.question,
                                                   inputType   : question.inputType, help: question.help, validation: question.valueConstraints,
                                                   optional    : question.optional, unit: question.unit, choices: question.choices?:resourceChoices,
                                                   defaultValue: question.defaultValue
                        ])
                    }
                    allQuestions.add(questionAsDocument)
                })
            }
        })
        return allQuestions
    }

    /**
     * This is a bulk method to collect all the material alternatives, filtering through several conditions and then
     * map them into a lightweight object for the frontend
     *
     * @param carbonDesignId
     * @param indicatorId
     * @param manualId
     * @return
     */
    def collectAndMapMaterialAlternatives(String carbonDesignId, String indicatorId, String manualId) {

        //Add checks and feed data to this method
        Set<Map<String, String>> listOfMappedResources = []

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        Dataset originalMaterialDataset = carbonDesign?.datasets?.find({dataset -> dataset.manualId == manualId})

        if (originalMaterialDataset) {
            Resource resource = originalMaterialDataset?.resourceId ? optimiResourceService.getResourceWithParams(originalMaterialDataset?.resourceId, null, true) : null

            if (resource) {

                ResourceType resourceType = resourceService.getSubType(resource)
                String resourceSubType = resourceType?.subType
                String queryId = originalMaterialDataset?.queryId

                // ResourceFilterCriteria
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
                List<CarbonDesigner3DMaterialConstituentDefault> materialDefaultsWithMatchingSubTypes = carbonDesignerQuery?.materialConstituentDefaults?.findAll({ (it.initialSubType == resourceSubType) })
                String regionId = carbonDesign?.designInfo?.get("regionId")
                CarbonDesigner3DRegion region = carbonDesignerQuery?.carbonDesigner3DRegions?.find({CarbonDesigner3DRegion region -> (region.regionId == regionId) })
                List<String> dataProperties = region?.regionMaterialRequirements?.get("dataProperties")
                User user = userService.getCurrentUser()
                String privateDatasetAccountId = userService.getAccount(user)?.id?.toString()
                List<String> eligibleSubTypes = [resourceSubType]
                if (materialDefaultsWithMatchingSubTypes) {
                    materialDefaultsWithMatchingSubTypes.forEach({materialDefaultObj ->
                        eligibleSubTypes.addAll(materialDefaultObj.eligibleSubTypes)
                    })
                }

                eligibleSubTypes.forEach({ String subType ->

                    BasicDBObject query = createDBQuery(subType, dataProperties, privateDatasetAccountId, originalMaterialDataset?.userGivenUnit)
                    List<Resource> subTypeResources = Resource.collection.find(query).collect( { it as Resource})

                    subTypeResources.forEach({ Resource resourceWithSubType ->
                        Map<String, String> allowedResourcesMap = [:]
                        Boolean passedFilterCriteria = Boolean.FALSE

                        if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resourceWithSubType, queryId, indicator, null, carbonDesign as Entity)) {
                            passedFilterCriteria = Boolean.TRUE
                        }
                        if (passedFilterCriteria) {
                            allowedResourcesMap.put(Constants.cd3D.RESOURCEID.get(), resourceWithSubType.resourceId)
                            allowedResourcesMap.put(Constants.cd3D.RESOURCENAME.get(), optimiResourceService.getUiLabel(resourceWithSubType))
                            String brandImageId = resourceWithSubType.brandImageId
                            if (brandImageId) {
                                log.info("BrandImage added from collectAndMapMaterialAlternatives for a material")
                                addBrandImage(brandImageId, allowedResourcesMap)
                            }
                            if (resourceWithSubType.privateDataset && resourceWithSubType.privateDatasetAccountId) {
                                allowedResourcesMap.put("privateDataset", Boolean.TRUE.toString())
                            }
                        }
                        if (allowedResourcesMap) {
                            listOfMappedResources.addAll(allowedResourcesMap)
                        }
                    })
                })
            }
        }
        return listOfMappedResources
    }

    /**
     * Simple method to create and build the DB query, this is used by the collectAndMapMaterialAlternatives and
     * collectAndMapResourcesBySubtypeForElement to fetch resources for their respective dropdowns
     * @param subType
     * @param dataProperties
     * @param privateDatasetAccountId
     * @param unit
     * @return
     */
    BasicDBObject createDBQuery(String subType, List<String> dataProperties, String privateDatasetAccountId, String unit) {
        BasicDBObject query = new BasicDBObject()
        query.put(Constants.cd3D.RESOURCESUBTYPE.get(), subType)
        query.put("active", true)
        query.put("dataProperties", [$in: dataProperties])
        if (privateDatasetAccountId) {
            query.put("\$or", [[privateDatasetAccountId: privateDatasetAccountId], [privateDataset: null]])
        } else {
            query.put("privateDataset", [$exists: false])
        }
        query.put(Constants.cd3D.COMBINEDUNITS.get(), unit)
        return query
    }

    /**
     * This is a bulk method to replace the material datasets with new resources and properties and recalculate the
     * construction and design afterwards.
     *
     * @param carbonDesign
     * @param indicatorId
     * @param constructionManualId
     * @param changedConstituentMap
     * @return
     */
    CarbonDesign replaceConstituentDatasetsAndCalculate(CarbonDesign carbonDesign, String indicatorId, String constructionManualId,
                                               List<Map<String, String>> changedConstituentMap) {

        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Entity parentEntity = entityService.getEntityById(carbonDesign.parentEntityId)

        Dataset constructionDataset = carbonDesign.datasets?.find({dataset -> dataset?.manualId == constructionManualId })
        CD3DBuildingElementsPayload buildingElement = carbonDesign?.buildingElements?.find({it.elementId == constructionDataset.groupId})

        Set<Dataset> datasetsToCalculate = []
        Set<Dataset> unmodifiedConstituentDatasets = carbonDesign.datasets?.findAll({dataset -> dataset.parentConstructionId && dataset.uniqueConstructionIdentifier == constructionDataset.uniqueConstructionIdentifier})?.collect({it as Dataset})

        datasetsToCalculate.add(constructionDataset)
        carbonDesign.datasets.remove(constructionDataset)

        changedConstituentMap.forEach({ map ->
            Dataset originalConstituentDataset = carbonDesign.datasets?.find({ dataset -> map.get(Constants.cd3D.MANUALID.get()) == dataset?.manualId })
            String resourceId = map.containsKey(Constants.cd3D.RESOURCEID.get()) ? map.get(Constants.cd3D.RESOURCEID.get()) : null
            String comment = map.containsKey(Constants.cd3D.COMMENT.get()) ? map.get(Constants.cd3D.COMMENT.get()) : null
            Double thickness = map.containsKey(Constants.cd3D.THICKNESS.get()) ? map.get(Constants.cd3D.THICKNESS.get()) as Double : null

            if (comment != null) {
                saveDatasetComment(originalConstituentDataset, comment)
            }

            if (thickness != null) {
                originalConstituentDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_MM.get(), thickness)
                if (originalConstituentDataset.additionalQuestionAnswers.get(Constants.cd3D.THICKNESS_IN.get())) {
                    originalConstituentDataset.additionalQuestionAnswers.put(Constants.cd3D.THICKNESS_IN.get(), thickness)
                }
            }

            Resource resource = optimiResourceService.getResourceWithParams(resourceId)
            if (resource) {
                Dataset newConstituentDataset = new Dataset(originalConstituentDataset.properties)
                newConstituentDataset.manualId = new ObjectId().toString()
                newConstituentDataset.resourceId = resource.resourceId
                newConstituentDataset.profileId = resource.profileId
                newConstituentDataset.additionalQuestionAnswers.put(Constants.cd3D.RESOURCENAME.get(), optimiResourceService.getUiLabel(resource))
                newConstituentDataset.additionalQuestionAnswers.put(Constants.cd3D.PROFILEID.get(), resource.profileId)

                //Remove modified datasets from list above, then remove from carbonDesign to reduce duplicates/conflicts
                unmodifiedConstituentDatasets.remove(originalConstituentDataset)
                carbonDesign = carbonDesigner3DService.removeResourceDatasetsFromCarbonDesign(carbonDesign, originalConstituentDataset)
                datasetsToCalculate.add(newConstituentDataset)
            }
        })
        datasetsToCalculate.addAll(unmodifiedConstituentDatasets)
        carbonDesign = calculateDatasetsWithTempEntity(carbonDesign, datasetsToCalculate, indicator, parentEntity)
        carbonDesign.datasets = carbonDesign.datasets.sort {a, b -> a.seqNr <=> b.seqNr}

        calculateElementCo2eImpact(carbonDesign, buildingElement)
        calculateDesignCo2eImpact(carbonDesign)

        return carbonDesign
    }

    Dataset handleAdditionalQuestionsForDataset(Dataset dataset, CarbonDesign carbonDesign, Entity parentEntity, Indicator indicator, List<Question> additionalQuestions, Question mainQuestion, EolProcessCache eolProcessCache, ResourceCache resourceCache, ResourceTypeCache resourceTypeCache) {
        Resource resource = resourceCache.getResource(dataset)
        handleAdditionalQuestionsForDataset(dataset, carbonDesign, parentEntity, indicator, additionalQuestions, mainQuestion, eolProcessCache, resource, resourceTypeCache.getResourceSubType(resource))
    }
    /**
     * Method for handling all the required additional questions, such as local comp, EOL, serviceLife for materials
     * and constituent datasets
     * @param dataset
     * @param carbonDesign
     * @param parentEntity
     * @param indicator
     * @param additionalQuestions
     * @param additionalQuestionTagLib
     * @param mainQuestion
     * @return
     */
    Dataset handleAdditionalQuestionsForDataset(Dataset dataset, CarbonDesign carbonDesign, Entity parentEntity, Indicator indicator, List<Question> additionalQuestions, Question mainQuestion, EolProcessCache eolProcessCache, Resource resource, ResourceType resourceSubType) {

        List<String> transportDistanceQuestionIds = Constants.TransportDistanceQuestionId.list()
        transportDistanceQuestionIds = datasetService.handlePopulatingDefaultsForTransportationLeg2(parentEntity, transportDistanceQuestionIds)
        String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)
        Resource projectCountry = parentEntity?.countryResource
        String lcaModel = parentEntity?.lcaModel

        if (additionalQuestions) {
            for (Question additionalQuestion: additionalQuestions) {
                if (additionalQuestion.inheritToChildren) {
                    if (!dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                        String answer

                        if (additionalQuestion.questionId == com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) {
                            if (questionService.isWithPermanentServiceLife(mainQuestion, indicator)) {
                                answer = "permanent"
                            }
                        } else if (Constants.LOCAL_COMP_QUESTIONID.equals(additionalQuestion.questionId)) {
                            if ("00".equals(localCompensationMethodVersion)) {
                                if (resourceService.getIsLocalResource(resource?.areas)) {
                                    if (projectCountry) {
                                        answer = "${projectCountry.resourceId}"
                                    }
                                } else {
                                    answer = "noLocalCompensation"
                                }
                            }
                        } else if (Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID.equals(additionalQuestion.questionId)) {
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
                            AdditionalQuestionTagLib additionalQuestionTagLib = grailsApplication.mainContext.getBean(AdditionalQuestionTagLib.class)
                            answer = "${additionalQuestionTagLib.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: resource)}"
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
                            if (carbonDesign?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)) {
                                answer = "default"
                            } else {
                                answer = newCalculationServiceProxy.getServiceLife(dataset, resource, null, carbonDesign, indicator)?.toString()
                            }
                        }
                    } else if (Constants.LOCAL_COMP_QUESTIONID.equals(additionalQuestion.questionId)) {
                        if ("00".equals(localCompensationMethodVersion)) {
                            if (resourceService.getIsLocalResource(resource?.areas)) {
                                if (projectCountry) {
                                    answer = "${projectCountry.resourceId}"
                                }
                            } else {
                                answer = "noLocalCompensation"
                            }
                        }
                    } else if (Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID == additionalQuestion.questionId) {
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
                    } else if (transportDistanceQuestionIds.contains(additionalQuestion.questionId)) {
                        answer = com.bionova.optimi.construction.Constants.DEFAULT
                    } else {
                        AdditionalQuestionTagLib additionalQuestionTagLib = grailsApplication.mainContext.getBean(AdditionalQuestionTagLib.class)
                        answer = "${additionalQuestionTagLib.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: resource, entity: parentEntity)}"
                    }

                    if (answer != null) {
                        dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, answer)
                    }
                }
            }
        }

        if (!dataset.additionalQuestionAnswers?.get(Constants.EOL_QUESTIONID) && !additionalQuestions?.find({ Constants.EOL_QUESTIONID.equals(it.questionId)})) {
            String eolProcessId = eolProcessService.getEolProcessForDataset(null, resource, resourceSubType, projectCountry, null, lcaModel, eolProcessCache)?.eolProcessId
            if (eolProcessId) {
                dataset.additionalQuestionAnswers.put(Constants.EOL_QUESTIONID, eolProcessId)
            }
        }

        return dataset
    }

    /**
     * Fetches and calculates all co2e values for each individual subTyp and returns a map to send to CD3D for use in the
     * By Materials graph.
     * Utilises the new resourceCache methodology
     * @param carbonDesign
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    Map<String, Double> getByMaterialGraphMappings(CarbonDesign carbonDesign) {
        Map<String, Double> categories = [:]
        Set<Dataset> datasets = carbonDesign?.datasets?.findAll({ Dataset dataset ->
                (dataset?.resourceId && dataset?.quantity)
            })
        if (datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets as List<Dataset>)
            ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()

            datasets.forEach({ Dataset dataset ->
                Map<String, Object> additionalQuestionAnswers = dataset.additionalQuestionAnswers
                Resource resource = resourceCache.getResource(dataset.resourceId, dataset.profileId)
                String resourceSubTypeName = resourceTypeService.getLocalizedName(resourceTypeCache.getResourceSubType(resource))
                Boolean isConstructionDataset = dataset.isConstruction()
                if (additionalQuestionAnswers && resourceSubTypeName && !isConstructionDataset) {
                    Double co2e = additionalQuestionAnswers.co2e as Double
                    if (co2e) {
                        categories[resourceSubTypeName] = categories[resourceSubTypeName]
                                ? (categories[resourceSubTypeName] as Double) + co2e
                                : co2e
                    }
                }
            })
        }
        return categories
    }
    /*
        END OF LINE - NO MORE NEW METHODS - REFACTOR OR CREATE A NEW SERVICE
     */
}
