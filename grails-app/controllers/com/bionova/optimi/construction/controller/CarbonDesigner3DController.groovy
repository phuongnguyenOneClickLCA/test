package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartHttpServletRequest

class CarbonDesigner3DController {

    def accountService
    def carbonDesigner3DAdminService
    def userService
    def queryService
    def flashService
    def localeResolverUtil
    def templateService

    /**
     * This method is to get all the scenarios linked to an organisation and display them on the page, while preforming
     * some basic checks for main/internal users to allow editing or deleting them.
     * @return
     */
    def viewScenarios() {
        String accountId = params.accountId
        Account account = accountService.getAccount(accountId)
        List<CarbonDesigner3DScenario> scenarios = CarbonDesigner3DScenario.findAllByAccountId(accountId)
        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        List<CarbonDesigner3DRegion> carbonDesigner3DRegions = carbonDesignerQuery?.getCarbonDesigner3DRegions()
        List<CarbonDesigner3DBuildingType> carbonDesigner3DAllBuildingTypes = carbonDesigner3DAdminService.getAllBuildingTypes()
        User user = userService.getCurrentUser()
        Boolean editable = Boolean.FALSE
        if (account && user && (user.internalUseRoles || account.mainUserIds?.contains(user.id.toString()))) {
            editable = true
        }
        if (scenarios) {
            [scenarios: scenarios, account: account, editable: editable, regions: carbonDesigner3DRegions, buildingTypes: carbonDesigner3DAllBuildingTypes]
        }
    }

    /**
     * This is the admin page equivalent to viewScenarios() and will show all public created scenarios, which can be
     * viewed and managed within the page.
     * @return
     */
    def manageCarbonDesigner3DScenarios() {
        List<CarbonDesigner3DScenario> publicScenarios = CarbonDesigner3DScenario.findAllByPublicScenario(Boolean.TRUE)
        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        List<CarbonDesigner3DRegion> carbonDesigner3DRegions = carbonDesignerQuery?.getCarbonDesigner3DRegions()
        List<CarbonDesigner3DBuildingType> carbonDesigner3DAllBuildingTypes = carbonDesigner3DAdminService.getAllBuildingTypes()
        List<CarbonDesigner3DStructuralFrame> carbonDesigner3DStructuralFrames = carbonDesignerQuery?.getStructuralFrames()
        if (publicScenarios) {
            [publicScenarios: publicScenarios, regions: carbonDesigner3DRegions, buildingTypes: carbonDesigner3DAllBuildingTypes, structuralFrames: carbonDesigner3DStructuralFrames]
        }
    }

    /**
     * Scenarios need to be published by a main user or internal user in order to fetched and displayed
     * inside carbon designer. This method is universally used in both the admin and organisational pages.
     *
     * If the "publish" button is pressed on the admin page, a boolean is sent within the params, as "isAdminPage", if it
     * is present, this boolean will trigger a redirection back to the admin page.
     *
     * The organisational page, needs the accountId to be present to be redirected there,
     * this should be present in all parameter calls on this page specifically for that organisations redirection.
     * @return
     */
    def publishScenario() {
        String scenarioId = params.scenarioId
        Boolean isAdminPage = params.isAdminPage
        if (scenarioId) {
            CarbonDesigner3DScenario scenario = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(scenarioId))
            if (scenario) {
                scenario.published = Boolean.TRUE
                scenario.save()
                flashService.setSuccessAlert("Successfully published scenario: ${scenario.getLocalizedName()}")
            } else {
                flashService.setErrorAlert("No scenario found")
            }
        }
        if (isAdminPage) {
            redirect action: "manageCarbonDesigner3DScenarios"
        } else {
            redirect action: "viewScenarios", params: [accountId: params.accountId]
        }
    }

    /**
     * Scenarios need to be published by a main user or internal user in order to fetched and displayed
     * inside carbon designer. This method is universally used in both the admin and organisational pages.
     *
     * If the "publish" button is pressed on the admin page, a boolean is sent within the params, as "isAdminPage", if it
     * is present, this boolean will trigger a redirection back to the admin page.
     *
     * The organisational page, needs the accountId to be present to be redirected there,
     * this should be present in all parameter calls on this page specifically for that organisations redirection.
     * @return
     */
    def unpublishScenario() {
        String scenarioId = params.scenarioId
        Boolean isAdminPage = params.isAdminPage
        if (scenarioId) {
            CarbonDesigner3DScenario scenario = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(scenarioId))
            if (scenario) {
                scenario.published = Boolean.FALSE
                scenario.save()
                flashService.setSuccessAlert("Successfully unpublished scenario: ${scenario.getLocalizedName()}")
            } else {
                flashService.setErrorAlert("No scenario found")
            }
        }
        if (isAdminPage) {
            redirect action: "manageCarbonDesigner3DScenarios"
        } else {
            redirect action: "viewScenarios", params: [accountId: params.accountId]
        }
    }

    /**
     * Scenarios have the ability to be renamed, from a user perspective they can only name the scenario in one locale
     * which will always default to the EN property for simplicity sake. However, public scenarios (admin only feature)
     * can create a new name specifically for every locale, meaning when the scenarios are fetched and mapped for carbon designer
     * it will display the name for the users selected locale.
     *
     * If the "save" button is pressed on the admin page, a boolean is sent within the params, as "isAdminPage", if it
     * is present, this boolean will trigger a redirection back to the admin page.
     *
     * The organisational page, needs the accountId to be present to be redirected there,
     * this should be present in all parameter calls on this page specifically for that organisations redirection.
     * @return
     */
    def renameScenario() {
        String scenarioId = params.scenarioId
        Boolean isAdminPage = params.isAdminPage
        if (scenarioId) {
            CarbonDesigner3DScenario scenario = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(scenarioId))
            if (scenario) {
                String locale = params.locale ?: "EN"
                String scenarioName = params.name
                scenario.name.put(locale, scenarioName)
                scenario.save()
                flashService.setSuccessAlert("Successfully renamed scenario: ${scenario.getLocalizedName()}")
            } else {
                flashService.setErrorAlert("No scenario found")
            }
        }
        if (isAdminPage) {
            redirect action: "manageCarbonDesigner3DScenarios"
        } else {
            redirect action: "viewScenarios", params: [accountId: params.accountId]
        }
    }

    /**
     * Scenarios can have their regions and buildingType properties updated, when a scenario is created it saves the selected
     * region and buildingType from the design as the default values, however, for public scenarios in particular, having
     * the ability to add more regions / buildingTypes will reduce the overall workload and reduce duplication.
     *
     * This method will check the params provided and update the corresponding property changed from a grails multi-select box
     * One noticed oddity was, if only a single value was selected, grails returns it as a string, while multiply options
     * come as a list, this is the reason for the instance of checks.
     *
     * If the "publish" button is pressed on the admin page, a boolean is sent within the params, as "isAdminPage", if it
     * is present, this boolean will trigger a redirection back to the admin page.
     *
     * The organisational page, needs the accountId to be present to be redirected there,
     * this should be present in all parameter calls on this page specifically for that organisations redirection.
     * @return
     */
    def updateScenario() {
        String scenarioId = params.scenarioId
        Boolean isAdminPage=params.boolean('isAdminPage')
        Integer minFloors = params.minFloors ? params.minFloors as Integer : null
        Integer maxFloors = params.maxFloors ? params.maxFloors as Integer : null
        List <String> regionIds = params.regions ? (params.regions instanceof String ? [params.regions] : params.regions) : null
        List <String> buildingTypes = params.buildingTypes ? (params.buildingTypes instanceof String ? [params.buildingTypes] : params.buildingTypes) : null
        List <String> structuralFrameIds = params.structuralFrameIds ? (params.structuralFrameIds instanceof String ? [params.structuralFrameIds] : params.structuralFrameIds) : null


        if (scenarioId) {
            CarbonDesigner3DScenario scenario = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(scenarioId))
            if (scenario) {
                CarbonDesigner3DScenario tmpTemplate = templateService.setFloorLimits(scenario, maxFloors, minFloors)
                if (regionIds) {
                    scenario.regionIds = regionIds
                }

                if (regionIds) {
                    scenario.buildingTypeIds = buildingTypes
                }

                scenario.structuralFrameIds = structuralFrameIds

                if (tmpTemplate) {
                    scenario.minFloors = tmpTemplate.minFloors
                    scenario.maxFloors = tmpTemplate.maxFloors
                    scenario.save()
                    flashService.setSuccessAlert("Successfully updated scenario: ${scenario.getLocalizedName()}")
                } else {
                    flashService.setErrorAlert("Maximum value for floors should be higher than minimum value and vice versa")
                }
            } else {
                flashService.setErrorAlert("No scenario found")
            }
        }
        if (isAdminPage) {
            redirect action: "manageCarbonDesigner3DScenarios"
        } else {
            redirect action: "viewScenarios", params: [accountId: params.accountId]
        }
    }

    /**
     * Scenarios can be deleted, this method will simply find the corresponding object from the provided id and if found
     * will delete the object.
     *
     * If the "publish" button is pressed on the admin page, a boolean is sent within the params, as "isAdminPage", if it
     * is present, this boolean will trigger a redirection back to the admin page.
     *
     * The organisational page, needs the accountId to be present to be redirected there,
     * this should be present in all parameter calls on this page specifically for that organisations redirection.
     * @return
     */
    def deleteScenario() {
        String scenarioId = params.scenarioId
        Boolean isAdminPage = params.isAdminPage
        if (scenarioId) {
            CarbonDesigner3DScenario scenario = CarbonDesigner3DScenario.findById(DomainObjectUtil.stringToObjectId(scenarioId))
            if (scenario) {
                flashService.setSuccessAlert("Successfully deleted scenario: ${scenario.getLocalizedName()}")
                scenario.delete()
            } else {
                flashService.setErrorAlert("No scenario found")
            }
        }
        if (isAdminPage) {
            redirect action: "manageCarbonDesigner3DScenarios"
        } else {
            redirect action: "viewScenarios", params: [accountId: params.accountId]
        }
    }

    /**
     * This method collects all the building types to be displayed on the page for review.
     * @return
     */
    def buildingTypesUpload() {
        List<CarbonDesigner3DBuildingType> buildingTypes = carbonDesigner3DAdminService.getAllBuildingTypes()
        [buildingTypes: buildingTypes]
    }

    /**
     * Method is ran after a buildingTypes excel has been uploaded and the import has been pressed,
     * it will then send the excel file to the service to map and create all the CarbonDesigner3DBuildingType domain objects from it
     * @return
     */
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def createBuildingTypesFromExcel() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flashService.setErrorAlert("${g.message(code: "import.file.required")}")
                redirect action: "buildingTypesUpload"
            } else {
                try {
                    def mapWithInfo = carbonDesigner3DAdminService.importCarbonDesigner3DBuildingTypes(excelFile)

                    if (mapWithInfo?.get("error")) {
                        flashService.setErrorAlert("${mapWithInfo?.get("error")}")
                    } else {
                        flashService.setSuccessAlert("Successfully created building types")
                    }
                    chain(action: "buildingTypesUpload")
                } catch (Exception e) {
                    flashService.setErrorAlert("${e.getMessage()}")
                    log.error(e.getMessage())
                    redirect(action: "buildingTypesUpload")
                }
            }
        } else {
            flashService.setErrorAlert("Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest).")
            redirect action: "buildingTypesUpload"
        }
    }
 }
