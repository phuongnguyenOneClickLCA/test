package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DRegion
import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DScenario
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.UnitConversionUtil
import grails.gorm.transactions.Transactional

@Transactional
class TemplateService {

    def queryService
    def userService
    def carbonDesigner3DAdminService
    def simulationToolService
    def unitConversionUtil


    /**
     * This method will fetch all of the possible structural frames according to the regions of the template
     * @param templateId
     * @return
     */
    List<String> getPossibleStructuralFramesByTemplateId(String templateId) {
        CarbonDesigner3DScenario template = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(templateId))
        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        List<CarbonDesigner3DRegion> carbonDesigner3DRegions = carbonDesignerQuery?.carbonDesigner3DRegions?.findAll({ region -> template.regionIds.contains(region.regionId) })
        List<String> structuralFrames = []
        for (region in carbonDesigner3DRegions) {
            structuralFrames.addAll(region.regionAlternativeStructuralFrame)
            structuralFrames.add(region.regionDefaultStructuralFrame)
        }

        return structuralFrames
    }


    /**
     * This method takes a template and max, min floor values and validates them. If everything is correct it returns the template if not it returns null.
     * @param template
     * @param maxFloors
     * @param minFloors
     * @return
     */
    CarbonDesigner3DScenario setFloorLimits(CarbonDesigner3DScenario template, Integer maxFloors, Integer minFloors) {
        if (minFloors) {
            if (!(maxFloors ? minFloors <= maxFloors : (template.maxFloors ? minFloors <= template.maxFloors : true))) {
                return null
            }
            template.minFloors = minFloors
        } else {
            template.minFloors = null
        }

        if (maxFloors) {
            if (!(minFloors ? maxFloors >= minFloors : (template.minFloors ? maxFloors >= template.minFloors : true))) {
                return null
            }
            template.maxFloors = maxFloors
        } else {
            template.maxFloors = null
        }

        return template
    }

    /**
     * This method will fetch all published scenarios that are public and from the organisation, it will then preform
     * a basic filtering of these scenarios based on the available region for the tool (indicator). After the filtering
     * it will then created a mapped version of the scenario, with basic string based properties for Carbon Designer 3D
     * form creation data
     * @param carbonDesignerRegions
     * @return
     */
    Map<String, Object>[] getAllCarbonDesigner3DScenarios(List<CarbonDesigner3DRegion> carbonDesignerRegions) {

        List<String> regionIds = carbonDesignerRegions?.collect({ region -> region.regionId})
        User user = userService.getCurrentUser()
        Account account = userService.getAccount(user)
        String accountId = account?.id?.toString()

        List<CarbonDesigner3DScenario> scenarios = []

        List<CarbonDesigner3DScenario> publicScenarios = CarbonDesigner3DScenario.findAllByPublicScenarioAndPublished(Boolean.TRUE, Boolean.TRUE)
        if (publicScenarios) {
            scenarios.addAll(publicScenarios)
        }
        if (accountId) {
            List<CarbonDesigner3DScenario> organisationScenarios = CarbonDesigner3DScenario.findAllByAccountIdAndPublished(accountId, Boolean.TRUE)

            if (organisationScenarios) {
                scenarios.addAll(organisationScenarios)
            }
        }
        Map<String, Object>[] mappedScenarios = scenarios.findAll({ scenario -> regionIds.any({scenario.regionIds.contains(it)})}).collect({
            [scenarioName: it.getLocalizedName(), scenarioId: it.id.toString(), buildingTypeIds: it.buildingTypeIds, regionIds: it.regionIds, maxFloors: it.maxFloors, minFloors : it.minFloors,
             structuralFrameIds: it.structuralFrameIds]})
        return mappedScenarios
    }

    /**
     * This method will initialise and create the basic scenario with properties that are common to both public/private
     * scenarios.
     * @param scenarioNameMap
     * @param resourceDatasets
     * @param regionId
     * @param buildingTypeId
     * @return
     */
    CarbonDesigner3DScenario initialiseScenario(Map<String, String> scenarioNameMap, Set<Dataset> resourceDatasets, String regionId) {
        CarbonDesigner3DScenario scenario = new CarbonDesigner3DScenario()
        scenario.name = scenarioNameMap
        scenario.scenarioType = "material"
        scenario.regionIds = [regionId]
        scenario.datasets = resourceDatasets
        return scenario
    }

    /**
     * This will set certain properties for the scenario to be public. This will save it in the database and be viewable
     * under the admin tab.
     * @param scenario
     * @return
     */
    CarbonDesigner3DScenario createPublicScenario(CarbonDesigner3DScenario scenario) {
        scenario.publicScenario = Boolean.TRUE
        scenario.buildingTypeIds = carbonDesigner3DAdminService.getAllBuildingTypes()?.collect({it.buildingTypeId})
        return scenario
    }

    /**
     * This will create a private user scenario and save it to the users organisation, it will try to fetch a list of all
     * building types for the selected design region to save to the scenario and will fall back to the designs buildingType
     * if there is an issue
     * @param scenario
     * @param organisation
     * @param userId
     * @param regionId
     * @param buildingTypeId
     * @return
     */
    CarbonDesigner3DScenario createPrivateScenario(CarbonDesigner3DScenario scenario, Account organisation, String userId, String regionId, String buildingTypeId) {

        scenario.accountId = organisation?.id?.toString()
        List<String> filteredBuildingTypes = carbonDesigner3DAdminService.getAllBuildingTypesByRegionId(regionId)?.collect({it.buildingTypeId})
        if (filteredBuildingTypes) {
            scenario.buildingTypeIds = filteredBuildingTypes
        } else {
            scenario.buildingTypeIds = [buildingTypeId]
        }
        //Main users scenarios are automatically published
        if (organisation.mainUserIds?.contains(userId)) {
            scenario.published = Boolean.TRUE
        }

        return scenario
    }

    /**
     * Method for filtering out the
     * @param allScenariosDatasets
     * @param elementId
     * @param buildingElementArea
     * @return
     */
    Set<Dataset> getTemplateDatasetsForElement(Set<Dataset> allScenariosDatasets, String elementId, Double buildingElementArea, String unitSystem) {
        Set<Dataset> scenarioDatasetsForElement = allScenariosDatasets.findAll({ dataset -> dataset.groupId == elementId })
        if (!scenarioDatasetsForElement) {
            return []
        }
        scenarioDatasetsForElement.forEach({ scenarioDatasetForElement ->
            //This should fetch all construction and material datasets, excluding constituents as these don't have a share
            if (scenarioDatasetForElement.isConstruction() || (!scenarioDatasetForElement.isConstruction() && !scenarioDatasetForElement.isConstituent())) {
                Double resourceShare = scenarioDatasetForElement?.additionalQuestionAnswers?.get(Constants.SHARE) as Double
                if (resourceShare) {
                    Double quantity = (buildingElementArea * resourceShare) / 100
                    scenarioDatasetForElement.quantity = quantity
                    scenarioDatasetForElement.answerIds = ["${quantity}"]
                    if (buildingElementArea == 0) {
                        scenarioDatasetForElement.additionalQuestionAnswers?.put(Constants.CO2E, 0)
                        scenarioDatasetForElement.additionalQuestionAnswers?.put(Constants.CO2_INTENSITY, 0)
                        scenarioDatasetForElement.additionalQuestionAnswers?.put(Constants.CARBON_SHARE, 0)
                    }
                }
            }
        })
        //We need to iterate twice here, as we need all construction values above to be updated, order of datasets might not always be hierarchical
        scenarioDatasetsForElement.forEach({ scenarioDatasetForElement ->
            if (scenarioDatasetForElement.isConstituent()) {
                Dataset constructionDataset = scenarioDatasetsForElement.find({ Dataset dataset -> dataset.uniqueConstructionIdentifier == scenarioDatasetForElement.uniqueConstructionIdentifier && dataset.isConstruction() })
                Double constructionQuantity = constructionDataset?.quantity
                String constructionUnit = constructionDataset?.calculatedUnit
                Double quantity = 0
                if (buildingElementArea == 0) {
                    scenarioDatasetForElement.quantity = quantity
                    scenarioDatasetForElement.answerIds = ["${quantity}"]
                    scenarioDatasetForElement.additionalQuestionAnswers?.put(Constants.CO2E, 0)
                    scenarioDatasetForElement.additionalQuestionAnswers?.put(Constants.CO2_INTENSITY, 0)
                }
                if (scenarioDatasetForElement?.constructionValue && constructionQuantity) {
                    if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                        constructionQuantity /= simulationToolService.conversionUnitFactor(unitSystem, constructionUnit)
                        Double originalQuantity = (scenarioDatasetForElement.constructionValue as Double) * constructionQuantity
                        quantity = unitConversionUtil.doConversion(originalQuantity, null, scenarioDatasetForElement.userGivenUnit, null, true, null, null, null, null, unitConversionUtil.transformImperialUnitToEuropeanUnit(scenarioDatasetForElement.userGivenUnit))
                    } else {
                        quantity = (scenarioDatasetForElement.constructionValue as Double) * constructionQuantity
                    }
                    scenarioDatasetForElement.quantity = quantity
                    scenarioDatasetForElement.answerIds = ["${quantity}"]
                }
            }
        })

        return scenarioDatasetsForElement
    }

}
