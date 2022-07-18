package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.frenchTools.FrenchConstants
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.TypeCheckingMode
import org.bson.Document
import org.springframework.context.i18n.LocaleContextHolder
import javax.servlet.http.HttpServletResponse

class CarbonDesignerApiController extends ExceptionHandlerController {

    def entityService
    def userService
    def queryService
    def carbonDesigner3DAdminService
    def indicatorService
    def carbonDesigner3DService
    def carbonDesignService
    def questionService
    def templateService

    /**
     * Finds all carbon designs in the Project entity (found from Id in payload) and maps the name and Id for sending to
     * CD3D frontend. This was to reduce loading speed and payload size.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getCarbonDesigns() {
        String parentEntityId = params.get("parentEntityId")
        String indicatorId = params.get("indicatorId")
        Boolean hasDesigns = Boolean.FALSE
        if (parentEntityId && indicatorId) {
            Entity projectEntity = entityService.getEntityById(parentEntityId)
            if (projectEntity) {
                List<CarbonDesign> carbonDesigns = carbonDesignService.getCarbonDesignsByIndicatorId(projectEntity, indicatorId)
                if (carbonDesigns) {
                    Map<String, String>[] mappedCarbonDesigns = carbonDesigns.collect({ carbonDesign -> ["carbonDesignId": carbonDesign.carbonDesignId, "carbonDesignName": carbonDesign.name] })
                    hasDesigns = Boolean.TRUE
                    render(mappedCarbonDesigns as JSON)
                }
            }
        }
        if (!hasDesigns) {
            render([])
        }
    }

    /**
     * This method is used to get some basic user details, used to expand or limit the users
     * options within the carbon designer 3D application.
     * @return UserId
     * @return UserRole
     * @return hasOrganisation
     * @return hasScenarioFeature
     * @return userLocale
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getUserDetails() {
        String projectEntityId = params.get("parentEntityId")

        User user = userService.getCurrentUser(true)

        String userId
        Set<String> userRole
        Boolean hasOrganisation = Boolean.FALSE
        Boolean hasScenarioFeature = Boolean.FALSE
        Boolean isLite = Boolean.FALSE
        String locale
        String accountId
        String organisationName
        Entity projectEntity = entityService.getEntityById(projectEntityId)

        if (user && projectEntity) {
            userId = user.id.toString()
            userRole = user.getRoles()
            String unitSystem = user.unitSystem
            String userName = userService.getFirstNameForUi(user.name)
            hasScenarioFeature = projectEntity.isFeatureLicensed(Feature.CARBONDESIGNER3DSCENARIOS)
            isLite = projectEntity.isFeatureLicensed(Feature.CARBONDESIGNER3DLITEMODE)
            locale = user?.language

            Account account = userService.getAccount(user)
            if (account) {
                hasOrganisation = Boolean.TRUE
                accountId = account?.id?.toString()
                organisationName = account?.companyName
            }
            render([userId         : userId, userRole: userRole, userName: userName, hasScenarioFeature: hasScenarioFeature, locale: locale,
                    hasOrganisation: hasOrganisation, organisationId: accountId, organisationName: organisationName, unitSystem: unitSystem,
                    isLite:isLite
            ] as JSON)
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${params}")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Failed to find Project or User")
        }
    }

    /**
     * API method to get a specific carbon design based on carbonDesignId
     * @return CarbonDesign as JSON
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getCarbonDesign() {
        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (carbonDesign) {
            carbonDesign.resourceSubTypes = carbonDesigner3DService.getByMaterialGraphMappings(carbonDesign)
            render(carbonDesign as JSON)
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to get carbon design")
        }
    }

    /**
     * Fetches all relevant data to pre-populate the carbon design creation form
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getCarbonDesignCreationData() {
        String parentEntityId = params.get("parentEntityId")
        String indicatorId = params.get("indicatorId")
        Entity projectEntity = entityService.getEntityById(parentEntityId)
        if (projectEntity) {
            String projectCountry = projectEntity.getCountryResourceResourceId()
            Double grossFloorArea = projectEntity.getSurface()
            Integer numOfAboveFloors = projectEntity.getNumberFloor()
            Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)

            if (carbonDesignerQuery && projectCountry && indicatorId) {

                List<CarbonDesigner3DRegion> carbonDesignerRegions = carbonDesignerQuery?.getCarbonDesigner3DRegions()?.findAll({ region ->
                    region?.regionCompatibleTools?.contains(indicatorId)
                })

                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                Boolean showCalculationPeriod = (indicator && !indicator.assessmentPeriodFixed && indicator.assessmentPeriodValueReference)

                List<CarbonDesigner3DBuildingShape> carbonDesignerShapes = carbonDesignerQuery?.getBuildingShapes()
                List<CarbonDesigner3DStructuralFrame> carbonDesigner3DStructuralFrames = carbonDesignerQuery?.getStructuralFrames()
                List<CarbonDesigner3DBuildingType> carbonDesigner3DAllBuildingTypes = carbonDesigner3DAdminService.getAllBuildingTypes()
                Map<String, Object>[] carbonDesigner3DScenarios = templateService.getAllCarbonDesigner3DScenarios(carbonDesignerRegions)
                List<Document> allQuestions = carbonDesigner3DService.getAllRegionQuestions(carbonDesignerRegions, indicator)

                render([carbonDesignerRegions   : carbonDesignerRegions,
                        carbonDesignerShapes    : carbonDesignerShapes,
                        structuralFrames        : carbonDesigner3DStructuralFrames,
                        allBuildingTypes        : carbonDesigner3DAllBuildingTypes,
                        scenarios               : carbonDesigner3DScenarios,
                        grossFloorArea          : grossFloorArea,
                        numOfAboveFloors        : numOfAboveFloors,
                        questions               : allQuestions,
                        showCalculationPeriod   : showCalculationPeriod] as JSON)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${params}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Missing query, country, indicatorId")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${params}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to find project")
        }
    }

    /**
     * Generic API call to fetch configuration data and store in in the state, current fetches elements and elementGroups
     * Used for localisation and help.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getCD3DConfigurations() {
        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        if (!carbonDesignerQuery) { //TODO - when merged to features, refactor to use new utility method
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${params}")
            return response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "$Constants.CARBON_DESIGNER_QUERY is missing")
        }
        List<CD3DElementGroupPayload> elementGroups = carbonDesignerQuery?.getElementGroups()
                ?.collect({ CarbonDesigner3DElementGroup elementGroup -> new CD3DElementGroupPayload(elementGroup.properties)})
        List<CD3DBuildingElementsPayload> elements = carbonDesignerQuery?.getBuildingElements()
                ?.collect({ CarbonDesigner3DBuildingElements element -> new CD3DBuildingElementsPayload(element.properties) })
        render([elementGroups: elementGroups,
                elements     : elements] as JSON)
    }

    /**
     * On initial Geometry Review page load, this will receive a payload full of existing form data used in calculating
     * the building dimensions.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def calculateBuildingDimensions() {
        def requests = request.JSON
        if (requests) {
            Map<String, Double> buildingDimensions = carbonDesigner3DService.createBuildingDimensionsMap(requests)
            if (buildingDimensions) {
                render(buildingDimensions as JSON)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Building dimensions failed to calculate")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }
    }

    /**
     * Calculates the building structure areas, this will preform all building element calculations, regardless of user selection.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def calculateBuildingStructureAreas() {

        def requests = request.JSON

        if (requests) {
            Map<String, Double> buildingStructuresMap = carbonDesigner3DService.createBuildingStructureAreasMap(requests)
            if (buildingStructuresMap) {
                render(buildingStructuresMap as JSON)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Building structure areas failed to calculate")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }
    }

    /**
     * API method fired when submitting the createDesign form. It will accept the payload and proceed to populate
     * the newly created carbon design.
     * @return
     */
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def createCarbonDesign() {
        long now = System.currentTimeSeconds()
        Object requests = request.JSON
        if (!requests) {
            return sendErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }
        String name = requests.designName
        String parentEntityId = requests.parentEntityId
        Boolean nameExists = carbonDesignService.nameAlreadyExist(parentEntityId, name)
        if (nameExists) {
            return sendErrorResponse(HttpServletResponse.SC_CONFLICT, "${g.message(code: "design.duplicate")}")
        }

        CarbonDesign carbonDesign = carbonDesignService.createCarbonDesignWithParameters(requests)
        if (!carbonDesign) {
            return sendErrorResponse(HttpServletResponse.SC_NOT_FOUND, "${g.message(code: "query.entity_deleted.warning")}")
        }
        carbonDesign.carbonDesignId = carbonDesign.id.toString()
        carbonDesign = entityService.createEntity(carbonDesign, null, Boolean.FALSE) as CarbonDesign
        log.info("CarbonDesigner3D - Carbon design generated in: ${System.currentTimeSeconds() - now} seconds or ${(System.currentTimeSeconds() - now) / 60} minutes")
        render([carbonDesignId: carbonDesign.carbonDesignId, carbonDesignName: carbonDesign.name] as JSON)
    }

    /**
     * Deletes the selected carbon design by removing the entity and the child entity attached to the parent
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def deleteCarbonDesign() {
        def requests = request.JSON
        try {
            entityService.delete(requests.carbonDesignId)
            render(["deleted": true] as JSON)
        } catch (Exception e) {
            log.warn("CD3D-deleteCarbonDesign: Failed to delete design ${e}")
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to delete design")
        }
    }

    /**
     * Copies the current carbon design by mapping a new instance with it's properties and then creates a childEntity
     * for the project entity before return newly created design.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def copyCarbonDesign() {
        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        String newCarbonDesignName = requests.newCarbonDesignName

        if (newCarbonDesignName.trim().length() <= 0) {
            return sendErrorResponse(HttpServletResponse.SC_PRECONDITION_FAILED, "The name cannot be empty")
        }

        CarbonDesign originalCarbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (!originalCarbonDesign) {
            return sendErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }

        Boolean nameExists = carbonDesignService.nameAlreadyExist(originalCarbonDesign.parentEntityId.toString(), newCarbonDesignName)
        if (nameExists) {
            return sendErrorResponse(HttpServletResponse.SC_CONFLICT, "${g.message(code: "design.duplicate")}")
        }

        CarbonDesign copiedCarbonDesign = carbonDesignService.copy(originalCarbonDesign, newCarbonDesignName)
        if (!copiedCarbonDesign) {
            return sendErrorResponse(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to create copy of ${originalCarbonDesign.name}")
        }

        render(["carbonDesignId": copiedCarbonDesign.carbonDesignId, "carbonDesignName": copiedCarbonDesign.name] as JSON)
    }

    /**
     * API fires when an LCA design is selected in convert design. It will create appropriate datasets for the LCA design
     * and append all carbon design datasets into the LCA design. If the overwriteDesign property is received it will
     * remove all resource datasets from the LCA design and append the carbon design over it.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def mergeCarbonDesign() {

        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        String lcaDesignId = requests.lcaDesignId
        Boolean overwriteDesign = requests.overwriteDesign

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        Entity lcaDesign = entityService.getEntityById(lcaDesignId)

        if (carbonDesign && lcaDesign) {
            lcaDesign = carbonDesignService.compileDatasetsForMergingDesigns(carbonDesign, lcaDesign, overwriteDesign)
            lcaDesign.disabledSections = entityService.resolveDisabledSections(lcaDesign)
            if (lcaDesign.validate()) {
                lcaDesign = lcaDesign?.merge(flush: true, failOnError: true)
                render([entityId: lcaDesign?.parentEntityId?.toString()] as JSON)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to validate ${lcaDesign.name}, errors: ${renderErrors(bean: lcaDesign)}")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }
    }

    /**
     * API fires when no LCA design is selected in convert design for merging/overwriting. It will then create a fresh
     * LCA design with all the required properties from the carbonDesign
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def convertCarbonDesign() {
        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        carbonDesign?.datasets?.removeAll({ dataset ->
            (dataset?.resourceId && dataset?.quantity == 0) || dataset.projectLevelDataset
        })

        Entity lcaDesign = new Entity(carbonDesign.properties)
        lcaDesign.entityClass = Constants.EntityClass.DESIGN.toString()
        lcaDesign.ribaStage = 2
        lcaDesign.disabledSections = entityService.resolveDisabledSections(lcaDesign)
        if (!entityService.designAlreadyFound(lcaDesign)) {
            if (lcaDesign.validate()) {
                lcaDesign = entityService.createEntity(lcaDesign)
                lcaDesign = carbonDesignService.prepDesignForConversion(lcaDesign)
                lcaDesign.save(flush: true)
                render([entityId: lcaDesign?.parentEntityId?.toString()] as JSON)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to validate ${lcaDesign.name}, errors: ${renderErrors(bean: lcaDesign)}")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_CONFLICT}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_CONFLICT, "${g.message(code: "design.duplicate")}")
        }
    }

    /**
     * API fires when the convert to LCA design button is pressed, this will fetch a list of design class entities from
     * the project entity and map the ids and names of the designs. (Similar to getCarbonDesigns() payload)
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getLCADesignsFromProject() {
        def requests = request.JSON
        String parentEntityId = requests.parentEntityId
        Entity projectEntity = entityService.getEntityById(parentEntityId)
        if (projectEntity) {
            List<ChildEntity> lcaDesigns = projectEntity.getDesignChildEntities()
            if (lcaDesigns) {
                Map<String, String>[] mappedLCADesigns = lcaDesigns?.collect({ childEntity -> ["lcaDesignId": childEntity.entityId.toString(), "lcaDesignName": childEntity.operatingPeriodAndName] })
                if (mappedLCADesigns) {
                    render(mappedLCADesigns as JSON)
                }
            } else {
                render([])
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "${g.message(code: "error.header")}")
        }
    }

    /**
     * API entry point for when the create scenario button is pressed in the modal
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def createScenario() {
        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        String scenarioName = requests.scenarioName
        Boolean isPublic = requests.public
        String[] selectedElementIds = requests.selectedElementIds
        Boolean saveEmptyResources = requests.saveEmptyResources
        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        User user = userService.getCurrentUser(true)

        if (carbonDesign && user) {
            String locale = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
            Map<String, String> scenarioNameMap = [:]

            if (locale && scenarioName) {
                scenarioNameMap.put(locale, scenarioName)
            }
            Set<Dataset> resourceDatasets = carbonDesign.datasets.findAll({ Dataset dataset -> dataset.resourceId && selectedElementIds.contains(dataset.groupId) && (saveEmptyResources || dataset.quantity) })

            if (scenarioNameMap && resourceDatasets) {
                String regionId = carbonDesign?.designInfo?.get("regionId")
                CarbonDesigner3DScenario scenario = templateService.initialiseScenario(scenarioNameMap, resourceDatasets, regionId)
                String successMessage = ""

                if (isPublic) {
                    scenario = templateService.createPublicScenario(scenario)
                    successMessage = " has been successfully saved to public scenarios, please approve for external use"
                } else {
                    Account organisation = userService.getAccount(user)

                    if (organisation) {
                        String buildingTypeId = carbonDesign?.designInfo?.get("buildingTypeId")
                        scenario = templateService.createPrivateScenario(scenario, organisation, user?.id?.toString(), regionId, buildingTypeId)
                        successMessage = " ${g.message(code: "cd3d.scenario_success_to_org")} " + organisation.companyName
                    } else {
                        log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "${g.message(code: "cd3d.scenario_no_org_found")}")
                    }
                }

                if (scenario.validate()) {
                    scenario.save(flush: true)
                    render(["message": scenarioName + successMessage] as JSON)
                } else {
                    log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, scenarioName + "${g.message(code: "cd3d.scenario_failed_to_save")} ${renderErrors(bean: scenario)}")
                }
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "${g.message(code: "cd3d.scenario_no_name_or_resources")}")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "${g.message(code: "cd3d.scenario_no_user_or_designs")}")
        }
    }

    /**
     * API fires when a resource is selected from the add resource dropdown on each element tab. It will create
     * uncalculated datasets and add them to the design, for users to specify quantities and amounts
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def addResourceDataset() {
        def requests = request.JSON
        String resourceId = requests.resourceId
        String carbonDesignId = requests.carbonDesignId
        String elementId = requests.elementId
        String indicatorId = requests.indicatorId
        Integer seqNr = 0
        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (carbonDesign) {

            Double buildingElementTotalArea = carbonDesign.buildingElements.find({ element -> element.elementId == elementId }).elementTotalArea
            CarbonDesigner3DBuildingElements buildingElement = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)?.getBuildingElements()?.find({ element ->
                element?.elementId?.equals(elementId)
            })

            Set<Dataset> elementDatasets = carbonDesign?.datasets?.findAll({ dataset -> dataset.groupId == buildingElement.elementId })?.collect({ it as Dataset })
            if (elementDatasets) {
                seqNr = elementDatasets.max({ a, b -> a.seqNr <=> b.seqNr }).seqNr
            }
            Entity parentEntity = entityService.getEntityById(carbonDesign.parentEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Set<Dataset> carbonDesignDatasets = carbonDesigner3DService.createResourceDataset(buildingElement, buildingElementTotalArea, resourceId, 0, seqNr + 1, null, carbonDesign, parentEntity, indicator)
            if (carbonDesignDatasets) {
                carbonDesign.datasets.addAll(carbonDesignDatasets)
                if (carbonDesign.validate()) {
                    carbonDesign.save(flush: true)
                    render(status: HttpServletResponse.SC_NO_CONTENT)
                } else {
                    log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                    response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to add resource to ${carbonDesign.name}, errors: ${renderErrors(bean: carbonDesign)}")
                }
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${request.JSON}")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "${g.message(code: "error.header")}")
        }
    }

    /**
     * API is fired when a user edits either the dataset: Share, Area or Comment and will proceed to calculate the specific
     * datasets in a temporary entity.
     * If there is no Area or Share sent in payload and just a comment, it will skip the calculations and just amend the comment
     * to the additionalQuestionAnswers.
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def updateResourceDataset() {
        def requests = request.JSON
        User user = userService.getCurrentUser()
        String unitSystem = user?.unitSystem ?: com.bionova.optimi.core.Constants.cd3D.METRIC.get()
        String manualId = requests.manualId
        String carbonDesignId = requests.carbonDesignId
        String indicatorId = requests.indicatorId
        Double updatedShare = requests.resourceShare?.toString()?.isDouble() ? requests.resourceShare?.toString()?.toDouble() : null
        Double updatedArea = requests.resourceArea?.toString()?.isDouble() ? requests.resourceArea?.toString()?.toDouble() : null
        String comment = requests.comment

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign

        if (carbonDesign) {
            carbonDesign = carbonDesignService.updateDatasetsAndCalculateCarbonDesign(carbonDesign, manualId, indicatorId, updatedShare, updatedArea, comment, unitSystem)
            if (carbonDesign.validate()) {
                carbonDesign.save(flush: true)
                render(status: HttpServletResponse.SC_NO_CONTENT)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to update resource in ${carbonDesign.name}, errors: ${renderErrors(bean: carbonDesign)}")
            }
        }
    }

    /**
     * API is fired on the delete resource button, it will remove the selected datasets and recalculate the co2 share
     * for all other elements / datasets
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def removeResourceDataset() {

        def requests = request.JSON
        String manualId = requests.manualId
        String carbonDesignId = requests.carbonDesignId

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (carbonDesign) {
            carbonDesign = carbonDesignService.removeDatasetsAndCalculateCarbonDesign(carbonDesign, manualId)
            if (carbonDesign.validate()) {
                carbonDesign.save(flush: true)
                render(status: HttpServletResponse.SC_NO_CONTENT)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to remove resource to ${carbonDesign.name}, errors: ${renderErrors(bean: carbonDesign)}")
            }
        }
    }

    /**
     * This API is called everytime a building element tab is opened, fetching the specific alternative resources and
     * private constructions from the users organisation (if they pass the resourceFilterCriteria)
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getElementResources() {
        def requests = request.JSON
        String carbonDesignId = requests.carbonDesignId
        String elementId = requests.elementId
        String indicatorId = requests.indicatorId

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        User user = userService.getCurrentUser()

        if (carbonDesign && indicator) {

            CarbonDesigner3DBuildingElements buildingElement = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)?.getBuildingElements()?.find({ element ->
                element?.elementId?.equals(elementId)
            })
            Boolean allowResourceSubtypes = buildingElement?.allowedContentType?.equalsIgnoreCase("resourceSubTypes") && buildingElement.allowedSubTypes
            Boolean allowSpecificResources = buildingElement?.allowedContentType?.equalsIgnoreCase("specificResources") && buildingElement.allowedResources
            Account currentUserAccount = userService.getAccount(user)

            Set<Map<String, String>> mappedResources = []

            if (allowResourceSubtypes) {
                mappedResources.addAll(carbonDesigner3DService.collectAndMapResourcesBySubtypeForElement(carbonDesign, indicator, buildingElement, currentUserAccount))
            }
            if (allowSpecificResources) {
                mappedResources.addAll(carbonDesigner3DService.collectAndMapSpecificResourcesForElement(buildingElement))
            }
            if (mappedResources) {
                render(mappedResources as JSON)
            } else {
                render([])
            }
        }
    }

    /**
     * This API is called inside the Edit Construction modal, when the resource link is clicked to become a dropdown
     * This will then fetch all material alternatives, based on the subtype, indicator filters and region data properties
     *
     * @return List of Mapped Resources
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getMaterialAlternatives() {
        String manualId = params.get("manualId")
        String carbonDesignId = params.get("carbonDesignId")
        String indicatorId = params.get("indicatorId")

        if (carbonDesignId && indicatorId && manualId) {
            Set<Map<String, String>> mappedResources = carbonDesigner3DService.collectAndMapMaterialAlternatives(carbonDesignId, indicatorId, manualId)
            render(mappedResources as JSON)
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_BAD_REQUEST}. Sent request: ${params}")
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }
    }

    /**
     * This API is called on the 'update' construction button, inside the Edit Constructions modal,
     * where we have to replace 1+ resource datasets and recalculate.
     *
     * @return CarbonDesign as JSON
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def replaceConstituentDatasets() {
        def requests = request.JSON
        String manualId = params.get("constructionManualId")
        String carbonDesignId = params.get("carbonDesignId")
        String indicatorId = params.get("indicatorId")
        List<Map<String, String>> changedConstituentMap = requests.changedConstituents

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (carbonDesign) {
            carbonDesign = carbonDesigner3DService.replaceConstituentDatasetsAndCalculate(carbonDesign, indicatorId, manualId, changedConstituentMap)
            if (carbonDesign.validate()) {
                carbonDesign.save(flush: true)
                render(status: HttpServletResponse.SC_NO_CONTENT)
            } else {
                log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_PRECONDITION_FAILED}. Sent request: ${request.JSON}. Params: ${params}")
                response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Failed to replace constituent material in ${carbonDesign.name}, errors: ${renderErrors(bean: carbonDesign)}")
            }
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${request.JSON}. Params: ${params}")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "${g.message(code: "error.header")}")
        }
    }

    /**
     * Renames an existing carbon design
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def renameCarbonDesign() {
        def jsonRequest = request.JSON
        String carbonDesignId = jsonRequest.carbonDesignId
        String newCarbonDesignName = jsonRequest.newCarbonDesignName

        if (newCarbonDesignName.trim().length() <= 0) {
            return sendErrorResponse(HttpServletResponse.SC_PRECONDITION_FAILED, "The name cannot be empty")
        }

        CarbonDesign carbonDesign = entityService.getEntityById(carbonDesignId) as CarbonDesign
        if (!carbonDesign) {
            return sendErrorResponse(HttpServletResponse.SC_BAD_REQUEST, "${g.message(code: "error.header")}")
        }

        Boolean nameExists = carbonDesignService.nameAlreadyExist(carbonDesign.parentEntityId.toString(), newCarbonDesignName)
        if (nameExists) {
            return sendErrorResponse(HttpServletResponse.SC_CONFLICT, "${g.message(code: "design.duplicate")}")
        }

        carbonDesignService.rename(carbonDesign, newCarbonDesignName)
        render(["carbonDesignId": carbonDesign.carbonDesignId, "carbonDesignName": carbonDesign.name] as JSON)
    }

    /**
     * This API is used for constructing the Classification Graph, when fired it gets the indicatorId from parameters,
     * then retrieves the relevant classification and indicator details according to the indicatorId
     * Get classification doesn't work with organizationSpecificClassification as we have no way to preload/select
     * custom user values to resources.
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def getIndicatorDetails() {
        String indicatorId = params.get("indicatorId")
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
        if (indicator) {
            String defaultClassification
            List<String> frenchIndicatorIds = FrenchConstants.FR_TOOLS
            if (frenchIndicatorIds.contains(indicatorId)) {
                defaultClassification = FrenchConstants.SUB_SECTIONS_FEC_QUESTIONID
            } else {
                List<String> classificationIds = indicator.classificationsMap
                defaultClassification = (classificationIds && classificationIds.size() > 0)
                        ? classificationIds.get(0)
                        : null
            }
            CD3DIndicatorDetailsPayload payload = new CD3DIndicatorDetailsPayload()
            if (defaultClassification && defaultClassification != Constants.ORGANIZATION_CLASSIFICATION_ID) {
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID)
                Question classificationParams = questionService.getQuestion(additionalQuestionsQuery, defaultClassification)
                Map<String, String> choices = new HashMap<String, String>()
                for (QuestionAnswerChoice questionAnswerChoice : classificationParams?.choices) {
                    choices.put(questionAnswerChoice.answerId, questionAnswerChoice.getLocalizedAnswer())
                }
                payload.classificationId = defaultClassification
                payload.classificationName = classificationParams?.getLocalizedQuestion()
                payload.classificationHelp = classificationParams?.getLocalizedHelp()
                payload.choices = choices
                payload.inheritToChildren = classificationParams?.inheritToChildren!=null ? classificationParams?.inheritToChildren : true
            }
            payload.indicatorId = indicatorId
            payload.indicatorName = indicator.getLocalizedName()
            render(payload as JSON)
        } else {
            log.error("CarbonDesigner3D - request failed with status code ${HttpServletResponse.SC_NOT_FOUND}. Sent request: ${request.JSON}. Params: ${params}")
            render(status: HttpServletResponse.SC_NOT_FOUND, text: "Failed to find an indicator with the given id")
        }
    }

    /**
     * Error handling method to both return a response but also log to the console
     * @param statusCode
     * @param errorMessage
     * @return
     */
    def sendErrorResponse(Integer statusCode, String errorMessage) {
        log.error("CarbonDesigner3D - request failed with status code ${statusCode}. Sent Params: ${params} Sent request: ${request.JSON}")
        response.sendError(statusCode, errorMessage)
    }
}


