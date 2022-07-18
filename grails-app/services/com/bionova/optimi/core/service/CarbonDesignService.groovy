package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.util.UnitConversionUtil
import grails.compiler.GrailsCompileStatic
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import grails.web.api.ServletAttributes
import grails.web.databinding.DataBinder
import groovy.transform.TypeCheckingMode
import org.bson.Document
import org.bson.types.ObjectId

@Transactional
class CarbonDesignService extends GormCleanerService implements DataBinder, ServletAttributes {

    GrailsApplication grailsApplication
    def entityService
    def indicatorService
    def datasetService
    def carbonDesigner3DService
    def queryService
    def constructionService
    def newCalculationServiceProxy
    def carbonDesigner3DAdminService
    def simulationToolService
    def questionService
    def localizationService
    def unitConversionUtil
    TemplateService templateService
    OptimiResourceService optimiResourceService
    UserService userService

    /**
     * Experiment in fetching CDs with only needed properties, to reduce payload size and easier debugging
     * @param carbonDesignId
     * @return
     */
    def getCarbonDesignById(String carbonDesignId) {
        if (carbonDesignId) {
            Document carbonDesign = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(carbonDesignId), "deleted": false],
                    [carbonDesignId  : 1, name: 1, datasets: 1, regionId: 1, buildingType: 1,
                     buildingElements: 1, totalCo2e: 1, buildingStructure: 1])
            if (carbonDesign) {
                return carbonDesign
            } else {
                return null
            }
        } else {
            return null
        }
    }

    /**
     * Creates and maps various params for the creation of a carbon design
     * @param requests
     * @return carbonDesign
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def createCarbonDesignWithParameters(Object requests) {

        String name = requests.designName
        List<String> selectedElementGroups = requests.scope
        String buildingTypeId = requests.buildingTypeId
        String regionId = requests.regionId
        String indicatorId = requests.indicatorId
        Integer calculationPeriod = requests.calculationPeriod.toInteger() ?: 1
        Map<String, Integer> buildingStructure = requests.buildingStructure
        List<String> selectedScenarioIds = requests.scenarioIds
        String structuralFrameId = requests?.structuralFrame?.value

        Query carbonDesignerQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.CARBON_DESIGNER_QUERY, true)
        CarbonDesigner3DRegion selectedRegion = carbonDesignerQuery?.carbonDesigner3DRegions?.find({ region -> region.regionId == regionId })
        CarbonDesigner3DStructuralFrame structuralFrame = carbonDesignerQuery.getStructuralFrames()?.find({ structuralFrameFromQuery -> structuralFrameFromQuery.structuralFrameId == structuralFrameId })

        //Gets and filters out the buildingElementGroups based on selected Scope
        List<CarbonDesigner3DElementGroup> buildingElementGroups = carbonDesignerQuery?.getElementGroups()?.findAll({ selectedElementGroups.contains(it.elementGroupId) })
        Set<String> buildingElementIds = buildingStructure.keySet()
        List<String> matchingDesignScope = []
        List<CD3DElementGroupPayload> elementGroupsForDesign = []
        if (buildingElementGroups) {
            buildingElementGroups.forEach({ CarbonDesigner3DElementGroup elementGroup ->
                CD3DElementGroupPayload elementGroupPayload = new CD3DElementGroupPayload(elementGroup.properties)
                if (elementGroupPayload) {
                    elementGroupsForDesign.add(elementGroupPayload)
                }
                if (elementGroup.matchingDesignScope) {
                    matchingDesignScope.add(elementGroup.matchingDesignScope)
                }
            })
        }
        //Filters out all building elements based on ids from buildingStructures payload, which is a dynamically generated map based on user selection
        List<CarbonDesigner3DBuildingElements> buildingElements = []
        if (buildingElementIds) {
            buildingElements = carbonDesignerQuery?.getBuildingElements()?.findAll({ buildingElementIds.contains(it.elementId) })
        }

        //Create the design and set params
        CarbonDesign carbonDesign = new CarbonDesign()
        String entityClass = com.bionova.optimi.core.Constants.EntityClass.CARBON_DESIGN.getType()
        if (carbonDesign && selectedRegion) {
            bindData(carbonDesign, requests, [exclude: ['scope']])
            carbonDesign.name = name
            carbonDesign.indicatorIds = [indicatorId]
            carbonDesign.entityClass = entityClass
            ScopeToSave scope = new ScopeToSave(lcaCheckerList: matchingDesignScope)
            carbonDesign.scope = scope
            carbonDesign.buildingElementGroups = elementGroupsForDesign
            carbonDesign.designInfo = populateDesignInfo(structuralFrame, selectedRegion, buildingTypeId, calculationPeriod, requests)
            carbonDesign = calculateCarbonDesign(carbonDesign, indicatorId, buildingTypeId, selectedRegion, buildingElements, buildingStructure, calculationPeriod, selectedScenarioIds)
        }
        if (carbonDesign.validate()) {
            carbonDesign.save(flush: true)
            return carbonDesign
        } else {
            return null
        }
    }

    /**
     * Utility method to populate all the unique properties needed to display information to the User
     * @param region
     * @param buildingTypeId
     * @param calculationPeriod
     * @param requests
     * @return
     */
    Map<String, Object> populateDesignInfo(CarbonDesigner3DStructuralFrame structuralFrame, CarbonDesigner3DRegion region, String buildingTypeId, Integer calculationPeriod, Object requests) {

        Map<String, Object> designInfo = [:]

        String regionName
        String buildingTypeName
        String imageAndModel
        Boolean columnsPresent = structuralFrame?.columnsPresent
        Boolean beamsPresent = structuralFrame?.beamsPresent
        Boolean beamsInBothDirections = structuralFrame?.beamsInBothDirections
        Integer grossFloorArea = requests.grossFloorArea ?: 0
        Integer numOfAboveFloors = requests.numOfAboveFloors ?: 0
        Integer numOfUnderHeatedFloors = requests.numOfUnderHeatedFloors ?: 0
        Integer numOfUnderUnheatedFloors = requests.numOfUnderUnheatedFloors ?: 0
        Integer pilingLength = requests.pilingLength ?: 0
        Map<String, String> customSectionValues = requests.customSectionValues

        CarbonDesigner3DBuildingType building = carbonDesigner3DAdminService.getBuildingTypeById(buildingTypeId)
        regionName = region.getLocalizedName()
        buildingTypeName = getLocalizedName(building)
        imageAndModel = building?.imageAndModel

        designInfo.put("regionId", region.regionId)
        designInfo.put("regionName", regionName)
        designInfo.put("buildingTypeId", buildingTypeId)
        designInfo.put("buildingTypeName", buildingTypeName)
        designInfo.put("imageAndModel", imageAndModel)
        designInfo.put("calculationPeriod", calculationPeriod)
        designInfo.put("grossFloorArea", grossFloorArea)
        designInfo.put("numOfAboveFloors", numOfAboveFloors)
        designInfo.put("numOfUnderHeatedFloors", numOfUnderHeatedFloors)
        designInfo.put("numOfUnderUnheatedFloors", numOfUnderUnheatedFloors)
        designInfo.put("pilingLength", pilingLength)
        designInfo.put("columnsPresent", columnsPresent)
        designInfo.put("beamsPresent", beamsPresent)
        designInfo.put("beamsInBothDirections", beamsInBothDirections)
        customSectionValues.forEach({key, value ->
            designInfo.put(key, value)
        })
        return designInfo
    }

    /**
     * This method will find the dataset with matching values, so if the dataset exists it wont create a duplicate
     * @param carbonDesignDatasets
     * @param mappingValues
     * @return
     */
    Dataset getDatasetWithMapValues(Set<Dataset> carbonDesignDatasets, MapValues mappingValues) {
        Dataset dataset
        if (mappingValues.queryId && mappingValues.sectionId && mappingValues.questionId) {
            if (mappingValues.resourceId) {
                dataset = carbonDesignDatasets?.find({ it.questionId == mappingValues.questionId && it.sectionId == mappingValues.sectionId && it.queryId == mappingValues.queryId && it.resourceId == mappingValues.resourceId })
            } else {
                dataset = carbonDesignDatasets?.find({it.questionId == mappingValues.questionId && it.sectionId == mappingValues.sectionId && it.queryId == mappingValues.queryId})
            }
        }
        return dataset
    }

    /**
     * This method creates datasets based on the mapValues and QuestionReferences inside the region data object.
     * It will check for any existing datasets with the same values, if it exists, it will simply edit and update the answers
     * If it doesn't exist, it will create a fresh dataset.
     * It will then fetch the values saved in the designInfo object and map it to the AnswerId field of the dataset or
     * if present to the AdditionalQuestionAnswers field
     * @param carbonDesign
     * @param carbonDesignDatasets
     * @param mapValuesList
     * @return
     */
    def createDatasetsFromMappingValues(CarbonDesign carbonDesign, Set<Dataset> carbonDesignDatasets, List<MapValues> mapValuesList) {
        mapValuesList.forEach {mappingValues ->
            Dataset dataset = getDatasetWithMapValues(carbonDesignDatasets, mappingValues)
            Object value = carbonDesign.designInfo.get(mappingValues.value)
            if (!dataset) {
                dataset = new Dataset()
                dataset.manualId = new ObjectId().toString()
                dataset.queryId = mappingValues.queryId
                dataset.sectionId = mappingValues.sectionId
                dataset.questionId = mappingValues.questionId
                dataset.resourceId = mappingValues.resourceId
                dataset.profileId = mappingValues.profileId
                dataset.additionalQuestionAnswers = [:]
                if (mappingValues.comment) {
                    dataset.additionalQuestionAnswers.put("comment", mappingValues.comment)
                }
                if (value) {
                    if (mappingValues.additionalQuestionId) {
                        dataset.additionalQuestionAnswers.put(mappingValues.additionalQuestionId, value)
                    } else {
                        if (mappingValues.resourceId) {
                            dataset.quantity = value as Double
                        }
                        dataset.answerIds = ["${value}"]
                    }
                }
                carbonDesignDatasets.add(dataset)
            } else {
                if (value) {
                    if (mappingValues.additionalQuestionId) {
                        dataset.additionalQuestionAnswers.put(mappingValues.additionalQuestionId, value)
                    } else {
                        if (mappingValues.resourceId) {
                            dataset.quantity = value as Double
                        }
                        dataset.answerIds = ["${value}"]
                    }
                }
            }
        }
    }

    /**
     * Create and generates all relevant datasets for the design, then preforms a results calculation
     * @param indicatorId - used for calculation standards
     * @param buildingTypeId - used to filter out default data
     * @param region - used to filter out default data and map values for datasets
     * @param buildingElements - used to build default resources from configs
     * @param selectedTemplateIds - used to override buildingElement default resources from configs
     * @return
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    CarbonDesign calculateCarbonDesign(CarbonDesign carbonDesign, String indicatorId, String buildingTypeId, CarbonDesigner3DRegion region, List<CarbonDesigner3DBuildingElements> buildingElements, Map<String, Integer> buildingStructure, Integer calculationPeriod, List<String> selectedTemplateIds) {

        EolProcessCache eolProcessCache = EolProcessCache.init()
        if (carbonDesign) {
            // Get CarbonDesign and params
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity parentEntity = entityService.getEntityById(carbonDesign.parentEntityId)

            //Initialise new variables
            Set<Dataset> carbonDesignDatasets = []

            //Get Parent datasets
            if (parentEntity) {
                if (parentEntity.datasets && parentEntity.datasets.find({ it.projectLevelDataset })) {
                    carbonDesignDatasets.addAll(parentEntity.datasets.findAll({ it.projectLevelDataset }))
                }

                if (parentEntity.defaults) {
                    carbonDesign.defaults = parentEntity.defaults
                }
            }

            Set<Dataset> allTemplateDatasets = []
            if (selectedTemplateIds) {
                List<CarbonDesigner3DScenario> templates = CarbonDesigner3DScenario.findAllWhere(["_id": [$in: DomainObjectUtil.stringsToObjectIds(selectedTemplateIds)]])
                templates.forEach({CarbonDesigner3DScenario template ->
                    allTemplateDatasets.addAll(template.datasets)
                })
            }

            if (region.mapValues) {
                createDatasetsFromMappingValues(carbonDesign, carbonDesignDatasets, region.mapValues)
            }

            if (region.customSection?.questionReferences) {
                createDatasetsFromMappingValues(carbonDesign, carbonDesignDatasets, region.customSection?.questionReferences)
            }

            List<CD3DBuildingElementsPayload> buildingElementsPayload = []
            if (buildingElements) {

                User user = userService.getCurrentUser(true)
                String unitSystem = user?.unitSystem ?: com.bionova.optimi.core.Constants.cd3D.METRIC.get()
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)

                buildingElements.forEach({ CarbonDesigner3DBuildingElements element ->
                    //Sets the calculated area from geometry review into the element structure
                    Double buildingElementArea = buildingStructure?.get(element.elementId.toString()) ?: 0

                    if (allTemplateDatasets.find({ Dataset dataset -> dataset.groupId == element.elementId })) {
                        carbonDesignDatasets.addAll(templateService.getTemplateDatasetsForElement(allTemplateDatasets, element.elementId, buildingElementArea, unitSystem))
                    } else {
                        Integer seqNr = 0
                        ElementDefaultData elementDefaultData = element?.elementDefaultData?.find({ ElementDefaultData data ->
                            (data.buildingTypes.contains(buildingTypeId) || data.buildingTypes.contains("default")) && (data.regions.contains(region.regionId) || data.regions.contains("default"))
                        })

                        if (elementDefaultData) {
                            //Generic values for any dataset Type
                            String queryId = element.elementSavingLocation?.get(com.bionova.optimi.core.Constants.cd3D.QUERYID.get())
                            String sectionId = element.elementSavingLocation?.get(com.bionova.optimi.core.Constants.cd3D.SECTIONID.get())
                            String questionId = element.elementSavingLocation?.get(com.bionova.optimi.core.Constants.cd3D.QUESTIONID.get())
                            String elementId = element.elementId.toString()
                            Set<String> resourceIds = elementDefaultData.defaultDataSetMap.keySet()

                            List<Resource> resources = optimiResourceService.getResources(resourceIds as List<String>)
                            List<Construction> constructions = Construction.findAllWhere("constructionId": [$in: resourceIds])

                            Question mainQuestion = questionService.getQuestion(queryId, questionId)
                            List<Question> additionalQuestions = mainQuestion?.getAdditionalQuestions(indicator, additionalQuestionsQuery, null, null)

                            if (resources) {
                                resources.forEach({Resource resource ->
                                    if (!resource.construction) {
                                        Double resourceShare = elementDefaultData.defaultDataSetMap.get(resource.resourceId) as Double
                                        Double quantity = (buildingElementArea * resourceShare) / 100
                                        Dataset materialDataset = carbonDesigner3DService.createMaterialDataset(seqNr, quantity, queryId, sectionId, questionId, resource, elementId, element, unitSystem, resourceShare, null)
                                        carbonDesigner3DService.handleAdditionalQuestionsForDataset(materialDataset, carbonDesign, parentEntity, indicator, additionalQuestions, mainQuestion, eolProcessCache, resource, resource.subType)
                                        carbonDesignDatasets.add(materialDataset)
                                        seqNr++
                                    }
                                })
                            }
                            if (constructions) {
                                constructions.forEach({Construction construction ->
                                    Double resourceShare = elementDefaultData.defaultDataSetMap.get(construction.constructionId) as Double
                                    Double quantity = (buildingElementArea * resourceShare) / 100
                                    Dataset constructionDataset = carbonDesigner3DService.createConstructionDataset(seqNr, construction, unitSystem, quantity, queryId, sectionId, questionId, elementId, resourceShare, null, element)
                                    carbonDesignDatasets.add(constructionDataset)
                                    Set<Dataset> constituentDatasets = carbonDesigner3DService.createConstituentDatasets(construction, unitSystem, constructionDataset, element, carbonDesign, parentEntity, indicator, additionalQuestions, mainQuestion, eolProcessCache)
                                    carbonDesignDatasets.addAll(constituentDatasets)
                                    seqNr++
                                })
                            }
                        }
                    }
                    CD3DBuildingElementsPayload elementPayload = new CD3DBuildingElementsPayload()
                    elementPayload.elementId = element.elementId
                    elementPayload.elementTotalArea = buildingElementArea
                    buildingElementsPayload.add(elementPayload)
                })
            }

            //Calculate design
            carbonDesign = carbonDesigner3DService.calculateDatasetsWithTempEntity(carbonDesign, carbonDesignDatasets, indicator, parentEntity, calculationPeriod)

            //Some logic to get the elements total values, plus resource co2e share %.
            buildingElementsPayload.forEach({ CD3DBuildingElementsPayload element ->
                carbonDesigner3DService.calculateElementCo2eImpact(carbonDesign, element)
            })

            // Calculate CarbonShare for all elements, resources, etc
            carbonDesign.buildingElements = buildingElementsPayload
            carbonDesigner3DService.calculateDesignCo2eImpact(carbonDesign)
        }
        //Return newly calculated design
        return carbonDesign
    }

    /**
     * Bulk method to remove datasets and recalculate the entire design + co2e impacts
     * @param carbonDesign
     * @param manualId
     * @param indicatorId
     * @return
     */
    def removeDatasetsAndCalculateCarbonDesign(CarbonDesign carbonDesign, String manualId) {

        Dataset datasetToRemove = carbonDesign?.datasets?.find({ dataset -> dataset.manualId == manualId })
        CD3DBuildingElementsPayload buildingElement = carbonDesign?.buildingElements?.find({ it.elementId == datasetToRemove.groupId })

        carbonDesign = carbonDesigner3DService.removeResourceDatasetsFromCarbonDesign(carbonDesign, datasetToRemove)

        //Calculate co2
        carbonDesigner3DService.calculateElementCo2eImpact(carbonDesign, buildingElement)
        carbonDesigner3DService.calculateDesignCo2eImpact(carbonDesign)

        return carbonDesign
    }

    /**
     * Bulk method to update either the Share or Area of the resources and then recalculate the design with the updated values
     * @param carbonDesign
     * @param manualId
     * @param indicatorId
     * @param updatedShare
     * @param updatedArea
     * @param comment
     * @return
     */
    def updateDatasetsAndCalculateCarbonDesign(CarbonDesign carbonDesign, String manualId, String indicatorId, Double updatedShare, Double updatedArea, String comment, String unitSystem) {

        Entity parentEntity = entityService.getEntityById(carbonDesign.parentEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        Set<Dataset> carbonDesignDatasets = carbonDesign.datasets
        Dataset resourceDataset = carbonDesignDatasets?.find({ dataset -> dataset.manualId == manualId })
        carbonDesigner3DService.saveDatasetComment(resourceDataset, comment)

        if ((updatedShare != null || updatedArea != null) && resourceDataset?.additionalQuestionAnswers) {
            List<CD3DBuildingElementsPayload> buildingElements = carbonDesign.buildingElements
            CD3DBuildingElementsPayload buildingElement = buildingElements?.find({ it.elementId == resourceDataset.groupId })
            Double buildingElementArea = buildingElement?.elementTotalArea
            Double resourceShare = resourceDataset.additionalQuestionAnswers.get(com.bionova.optimi.core.Constants.SHARE) as Double
            Double resourceQuantity = resourceDataset?.quantity

            //User changes area amount and recalculates the resource share %
            if (updatedArea != null) {
                resourceQuantity = updatedArea
                resourceShare = (updatedArea / buildingElementArea) * 100
                resourceDataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.SHARE, resourceShare)
            }
            //User changes share % and recalculates the resource Area
            if (updatedShare != null) {
                resourceQuantity = (updatedShare * buildingElementArea) / 100
                resourceDataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.SHARE, updatedShare)
            }

            //Regardless if resource is construction or material, will still need updated quantities
            resourceDataset.quantity = resourceQuantity
            resourceDataset.answerIds = ["${resourceQuantity}"]
            resourceDataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.CO2E, 0)
            resourceDataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.CO2_INTENSITY, 0)
            resourceDataset.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.CARBON_SHARE, 0)


            Set<Dataset> updatedDatasets = []

            //Checks if resource is a construction, if so, fetch and update constituent datasets
            if (resourceDataset.isConstruction()) {
                updatedDatasets.add(resourceDataset)
                Set<Dataset> constituentDatasets = carbonDesignDatasets.findAll({ dataset -> dataset.uniqueConstructionIdentifier == resourceDataset.uniqueConstructionIdentifier && dataset.parentConstructionId }).collect({ it as Dataset })
                if (constituentDatasets) {
                    constituentDatasets.forEach({ Dataset d ->
                        Double originalAnswer = (d.originalAnswer?.isNumber()) ? d.originalAnswer?.toDouble() : 0
                        if (originalAnswer != null) {
                            Double constructionQuantity = resourceDataset?.quantity
                            Double quantityAnswer
                            if(unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value){
                                constructionQuantity /= simulationToolService.conversionUnitFactor(unitSystem, resourceDataset.calculatedUnit)
                                Double answerInMetric = originalAnswer * constructionQuantity
                                quantityAnswer = unitConversionUtil.doConversion(answerInMetric, null, d.userGivenUnit, null, true, null, null, null, null, unitConversionUtil.transformImperialUnitToEuropeanUnit(d.userGivenUnit))
                            } else{
                                quantityAnswer = originalAnswer * constructionQuantity
                            }
                            d.quantity = quantityAnswer
                            d.answerIds = ["${d.quantity}"]
                            d.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.CO2E, 0)
                            d.additionalQuestionAnswers.put(com.bionova.optimi.core.Constants.CO2_INTENSITY, 0)
                        }
                    })
                    updatedDatasets.addAll(constituentDatasets)
                }
            } else {
                updatedDatasets.add(resourceDataset)
            }

            carbonDesign = carbonDesigner3DService.removeResourceDatasetsFromCarbonDesign(carbonDesign, resourceDataset)
            carbonDesign = carbonDesigner3DService.calculateDatasetsWithTempEntity(carbonDesign, updatedDatasets, indicator, parentEntity)
            carbonDesign.datasets = carbonDesign.datasets.sort { a, b -> a.seqNr <=> b.seqNr }

            //Calculate co2
            carbonDesigner3DService.calculateElementCo2eImpact(carbonDesign, buildingElement)
            carbonDesigner3DService.calculateDesignCo2eImpact(carbonDesign)
        }

        return carbonDesign
    }

    /**
     * Bulk method to compile all the relevant datasets from both the carbonDesign and the LCA design and either merges
     * or overwrites them, depending on params
     * @param carbonDesign
     * @param lcaDesign
     * @param overwriteDesign
     * @return
     */
    Entity compileDatasetsForMergingDesigns(CarbonDesign carbonDesign, Entity lcaDesign, Boolean overwriteDesign) {
        Entity projectEntity = entityService.getEntityById(carbonDesign.parentEntityId)
        Set<Dataset> datasets = []

        Set<Dataset> carbonDesignResourceDatasets = carbonDesign.datasets?.findAll({ dataset ->
            dataset.projectLevelDataset || !dataset.projectLevelDataset && dataset?.resourceId
        })?.collect({ it as Dataset })

        if (carbonDesignResourceDatasets) {
            carbonDesignResourceDatasets?.removeAll({ dataset -> dataset?.resourceId && dataset?.quantity == 0 })

            if (carbonDesignResourceDatasets) {
                carbonDesignResourceDatasets.forEach({ dataset ->
                    dataset.manualId = new ObjectId().toString()
                    //originalAnswer is prioritised for displaying quantities of constituents, so need to remove it before going to query
                    dataset.originalAnswer = null
                    //in CD3D, rounding is down in the frontend, however, for query page, it doesn't do this, so need to apply here
                    dataset.quantity = simulationToolService.roundToNumber(dataset.quantity, true)
                    if (dataset.quantity) {
                        dataset?.answerIds = [dataset.quantity.toString() ?: "0"]
                    }

                    if (dataset.isConstruction()) {
                        String newUniqueConstructionIdentifier = UUID.randomUUID().toString() + dataset.id.toString()
                        Set<Dataset> constituents = carbonDesignResourceDatasets.findAll({ constituent -> constituent.uniqueConstructionIdentifier == dataset.uniqueConstructionIdentifier && constituent.parentConstructionId })
                        dataset.uniqueConstructionIdentifier = newUniqueConstructionIdentifier
                        constituents?.forEach({ constituent ->
                            constituent?.uniqueConstructionIdentifier = newUniqueConstructionIdentifier
                        })
                    }
                })
                datasets.addAll(carbonDesignResourceDatasets)
            }
        }

        if (overwriteDesign) {
            Set<Dataset> lockedDatasets = lcaDesign.datasets?.findAll({ dataset -> dataset.locked })?.collect({ it as Dataset })
            if (lockedDatasets) {
                datasets.addAll(lockedDatasets)
            }
        } else {
            Set<Dataset> toMergeDatasets = lcaDesign.datasets?.findAll({ dataset -> "buildingMaterialsQuery" == dataset.queryId || dataset.locked })?.collect({ it as Dataset })
            if (toMergeDatasets) {
                datasets.addAll(toMergeDatasets)
            }
        }

        List<String> uniqueQueryIds = datasets?.findAll({ !it.projectLevelDataset })?.collect({ it.queryId })?.unique()
        List<Indicator> indicatorsToSave = prepIndicatorsToSave(uniqueQueryIds, projectEntity)
        lcaDesign = datasetService.preHandleDatasets(lcaDesign, datasets as List<Dataset>, indicatorsToSave, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
        lcaDesign = calculateDesignPerIndicator(indicatorsToSave, lcaDesign, projectEntity, uniqueQueryIds)

        return lcaDesign
    }

    /**
     * Copy the CD3D and saves it under a new name, everything else should be identical,
     * however some of the logic was lifted up to copyCarbonDesign method in CarbonDesignerApiController
     * @param originalCarbonDesign
     * @return
     */
    CarbonDesign copy(CarbonDesign originalCarbonDesign, String newName) {
        CarbonDesign copiedCarbonDesign = new CarbonDesign(originalCarbonDesign.properties)
        copiedCarbonDesign.name = newName
        if (copiedCarbonDesign.validate()) {
            copiedCarbonDesign.save()
            copiedCarbonDesign.carbonDesignId = copiedCarbonDesign.id.toString()
            copiedCarbonDesign = entityService.createEntity(copiedCarbonDesign) as CarbonDesign
            return copiedCarbonDesign
        }
    }

    /**
     * New method to calculate the designs pre active indicator, as well as readying some questions for the calcs
     * @param indicatorsToSave
     * @param lcaDesign
     * @param projectEntity
     * @param uniqueQueryIds
     * @return
     */
    Entity calculateDesignPerIndicator(List<Indicator> indicatorsToSave, Entity lcaDesign, Entity projectEntity, List<String> uniqueQueryIds) {
        List<String> transportDistanceQuestionIds = com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list()
        transportDistanceQuestionIds = datasetService.handlePopulatingDefaultsForTransportationLeg2(projectEntity, transportDistanceQuestionIds)
        List<String> alreadySetQuestions = []
        AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib') as AdditionalQuestionTagLib
        indicatorsToSave?.each { Indicator indicator ->
            List<String> additionalQuestionIds = indicator?.indicatorQueries?.findAll({ uniqueQueryIds.contains(it.queryId) })?.collect({ it.additionalQuestionIds })?.flatten()?.unique() as List<String>
            List<Question> additionalQuestions = constructionService.getAdditionalQuestions(additionalQuestionIds)

            if (additionalQuestions) {
                ResourceCache resourceCache = ResourceCache.init(lcaDesign.datasets.toList())
                for (Question q: additionalQuestions) {
                    if (!alreadySetQuestions.contains(q.questionId)) {
                        alreadySetQuestions.add(q.questionId)
                        lcaDesign.datasets?.each {
                            if (uniqueQueryIds.contains(it.queryId)) {
                                if (!it.additionalQuestionAnswers) {
                                    it.additionalQuestionAnswers = [:]
                                }

                                if (!it.additionalQuestionAnswers.get(q.questionId)) {
                                    if (transportDistanceQuestionIds.contains(q.questionId) && lcaDesign.defaults?.get(Constants.DEFAULT_TRANSPORT)) {
                                        it.additionalQuestionAnswers.put((q.questionId), Constants.DEFAULT)
                                    } else {
                                        String answer = "${aq.additionalQuestionAnswer(additionalQuestion: q, resource: resourceCache.getResource(it))}"

                                        if (answer) {
                                            it.additionalQuestionAnswers.put((q.questionId), answer)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            lcaDesign = newCalculationServiceProxy.calculate(null, indicator.indicatorId, projectEntity, lcaDesign)
        }
        return lcaDesign
    }

    /**
     * Bulk method to prep and calculate the carbonDesign into an LCA design, will add or create additional questions /
     * datasets eventually to support this operation
     * @param lcaDesign
     * @return
     */
    Entity prepDesignForConversion(Entity lcaDesign) {
        Entity projectEntity = entityService.getEntityById(lcaDesign.parentEntityId)
        List<String> uniqueQueryIds = lcaDesign?.datasets?.findAll({ !it.projectLevelDataset })?.collect({ it.queryId })?.unique()
        List<Indicator> indicatorsToSave = prepIndicatorsToSave(uniqueQueryIds, projectEntity)
        //originalAnswer is prioritised for displaying quantities of constituents, so need to remove it before going to query
        lcaDesign?.datasets?.each { dataset ->
            dataset.originalAnswer = null
            //in CD3D, rounding is down in the frontend, however, for query page, it doesn't do this, so need to apply here
            dataset.quantity = simulationToolService.roundToNumber(dataset.quantity, true)
            if (dataset?.quantity) {
                dataset?.answerIds = [dataset.quantity.toString() ?: "0"]
            }
        }
        lcaDesign = datasetService.preHandleDatasets(lcaDesign, lcaDesign?.datasets as List<Dataset>, indicatorsToSave, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
        lcaDesign = calculateDesignPerIndicator(indicatorsToSave, lcaDesign, projectEntity, uniqueQueryIds)

        return lcaDesign
    }

    /**
     * Method to reduce duplication, generates a list of indicators based on query/project for the new design to
     * be calculated with.
     * @param uniqueQueryIds
     * @param projectEntity
     * @return
     */
    List<Indicator> prepIndicatorsToSave(List<String> uniqueQueryIds, Entity projectEntity) {
        List<Indicator> indicatorsToSave = []
        uniqueQueryIds?.forEach({ String queryId ->
            List<Indicator> indicators = indicatorService.getIndicatorsWithSameQuery(projectEntity, queryId)?.findAll({ (Constants.IndicatorUse.DESIGN.toString() == it.indicatorUse) })

            if (indicators) {
                indicatorsToSave.addAll(indicators)
            }
        })
        indicatorsToSave = indicatorsToSave?.unique({ it.indicatorId })
        return indicatorsToSave
    }

    String getLocalizedName(CarbonDesigner3DBuildingType building) {
        if(!building) {
            return "Localized name missing"
        }
        String fallback = "Localized name missing for buildingType ${building.buildingTypeId}"
        return localizationService.getLocalizedProperty(building, "name", "name", fallback)
    }

    /**
     * Checks the parents childEntities for matching carbon design name.
     * Similar method to designAlreadyFound() and operatingPeriodAlreadyFound() which checks the parentEntity for an existing
     * entity but without the updating functionality (used when re-saving in query)
     * NOTE: when returning any() method is necessary because the designNames contain scope or operating period thus we have to check the elements
     * @param parentEntityId
     * @param carbonDesignName
     * @return Design already exists with name
     */
    Boolean nameAlreadyExist(String parentEntityId, String name) {
        Entity parent = entityService.getEntityById(parentEntityId)
        if (!parent) {
            return false
        }
        List<String> designNames = parent?.childEntities?.findAll({ ChildEntity design -> !design.deleted && com.bionova.optimi.core.Constants.EntityClass.CARBON_DESIGN.toString() == design.entityClass })?.collect({ ChildEntity design -> design.operatingPeriodAndName })
        return designNames.any { String designName -> designName.contains(name) }
    }

    /**
     * Checks the parents childEntities for matching carbon design name.
     * @param carbonDesign
     * @return Design already exists with name
     */
    Boolean nameAlreadyExist(CarbonDesign carbonDesign) {
        String parentEntityId = carbonDesign.parentEntityId
        String name = carbonDesign.name
        return nameAlreadyExist(parentEntityId, name)
    }

    /**
     * Rename an already existing carbon design
     * @param carbonDesign
     * @param newName
     * @return
     */
    CarbonDesign rename(CarbonDesign carbonDesign, String newName) {
        Entity parent = entityService.getEntityById(carbonDesign.parentEntityId)
        ChildEntity entity = parent?.childEntities?.find({
            design ->
                !design.deleted && com.bionova.optimi.core.Constants.EntityClass.CARBON_DESIGN.toString() == design.entityClass && design.entityId.toString() == carbonDesign.carbonDesignId
        })
        entity.operatingPeriodAndName = newName
        carbonDesign.name = newName
        parent.merge(flush: true)
        carbonDesign.save()
    }

    /**
     * Rename an already existing carbon design
     * @param carbonDesignId
     * @param newName
     * @return
     */
    CarbonDesign rename(String carbonDesignId, String newName) {
        CarbonDesign carbonDesign = CarbonDesign.findById(DomainObjectUtil.stringToObjectId(carbonDesignId))
        if (carbonDesign) {
            rename(carbonDesign, newName)
        }
    }

    /**
     * Method will find all carbonDesigns from the project entity (using the child entity system) and then
     * filter out all designs that don't have the present indicatorId.
     * @param projectEntity
     * @param indicatorId
     * @return
     */
    List<CarbonDesign> getCarbonDesignsByIndicatorId(Entity projectEntity, String indicatorId) {
        List<CarbonDesign> carbonDesigns = []
        List<String> designIds = projectEntity.childEntities?.findAll({ childEntity ->
            !childEntity.deleted &&
                    childEntity.entityClass.equals(com.bionova.optimi.core.Constants.EntityClass.CARBON_DESIGN.toString())
        })?.collect({childEntity -> childEntity.entityId.toString() })

        if (designIds) {
            carbonDesigns = Entity.collection.find(["_id"       : [$in: DomainObjectUtil.stringsToObjectIds(designIds)],
                                                    indicatorIds: indicatorId,
                                                    $or         : [
                                                            ['deleted': [$exists: false]],
                                                            ['deleted': false]
                                                    ]
            ])?.collect({
                it as CarbonDesign
            })
        }
        return carbonDesigns
    }
}
