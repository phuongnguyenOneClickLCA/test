/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.IndicatorUse
import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.CDEarthquakeSetting
import com.bionova.optimi.core.domain.mongo.CDRegionDefaultChoice
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.CarbonDesignerDefaultValueConstituents
import com.bionova.optimi.core.domain.mongo.CarbonDesignerRegion
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityTypeConstructionAllocation
import com.bionova.optimi.core.domain.mongo.EntityTypeConstructionGroupAllocation
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.LcaChecker
import com.bionova.optimi.core.domain.mongo.LcaCheckerResult
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.ReportGenerationRule
import com.bionova.optimi.core.domain.mongo.ReportGenerationRuleContent
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ScopeToSave
import com.bionova.optimi.core.domain.mongo.SimulationTool
import com.bionova.optimi.core.domain.mongo.SimulationToolConstruction
import com.bionova.optimi.core.domain.mongo.SimulationToolConstructionGroup
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyType
import com.bionova.optimi.core.domain.mongo.SimulationToolSession
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.service.LocalizedLinkService
import com.bionova.optimi.core.service.NmdElementService
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.service.ImportMapperService
import com.bionova.optimi.core.taglib.CalculationResultTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.client.FindIterable
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.json.JsonSlurper
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.OptimisticLockingException

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class DesignController extends ExceptionHandlerController {

    def entityService
    def datasetService
    def indicatorService
    def queryService
    def configurationService
    def errorMessageUtil
    def flashService
    def userService
    def newCalculationServiceProxy
    def resourceFilterCriteriaUtil
    def optimiResourceService
    def channelFeatureService
    def featureService
    def helpConfigurationService
    def constructionService
    def unitConversionUtil
    def lcaCheckerService
    def portfolioService
    def optimiExcelImportService
    def simulationToolService
    def workFlowService
    def licenseService
    def questionService
    def costStructureService
    def verificationPointService
    def carbonDesignService
    def resultCategoryService
    def calculationRuleService
    def valueReferenceService
    def carbonDesignerRegionService
    def querySectionService
    def calculationProcessService
    def indicatorReportService
    ScopeFactoryService scopeFactoryService
    ImportMapperService importMapperService
    LocalizedLinkService localizedLinkService
    NmdElementService nmdElementService

    private static final List<String> LCA_CHECKER_LIST_FOR_NEW_TRIAL = ["foundation", "structure", "finishes"]

    def newDesign() {
        List<String> enabledIndicatorIds = params.list("enabledIndicatorIds")
        String newDesignName = params.newDesignName
        String newDesignComment = params.newDesignComment
        Integer ribaStage = params.int("ribaStage")
        String entityId = params.entityId
        Boolean lcaParamHandling = params.boolean("lcaParamHandling")
        Entity entity = entityService.getEntityById(entityId, session)
        Boolean LCCSequenceProject = Boolean.FALSE

        String scopeId = params.scopeId
        String frameType = params.frameType
        String projectType = params.projectType
        List<String> lcaCheckerList

        if (params.newTrial && !params.lcaCheckerList) {
            lcaCheckerList = LCA_CHECKER_LIST_FOR_NEW_TRIAL
        } else {
            lcaCheckerList = params.list("lcaCheckerList")
        }

        try {
            def indicatorsAdded = false

            if (!params.indicatorIds) {
                entity.validDesignLicenses?.each { License license ->
                    license.licensedIndicators?.each { Indicator indicator ->
                        if (IndicatorUse.DESIGN.toString() == indicator.indicatorUse &&
                                !entity.indicatorIds?.contains(indicator.indicatorId)) {
                            entity.indicatorIds?.add(indicator.indicatorId)
                            indicatorsAdded = true
                        }
                    }
                }
            }

            if (indicatorsAdded || params.indicatorIds) {
                entity = entityService.updateEntity(entity)
            }

            def design = new Entity()
            String name = newDesignName ? newDesignName.replaceAll(Constants.USER_INPUT_REGEX,"") : message(code: "entity.new_design")
            design.name = name ? name : message(code: "entity.new_design")
            design.comment = newDesignComment

            if ("building".equalsIgnoreCase(entity?.entityClass) || "infrastructure".equalsIgnoreCase(entity?.entityClass)) {
                if(ribaStage == -1){
                    design.ribaStage = null
                    design.component = Boolean.TRUE
                } else {
                    design.ribaStage = ribaStage
                }
            }
            design.entityClass = EntityClass.DESIGN.getType()
            design.parentEntityId = entity?.id
            design.parentName = entity?.name
            design.chosenDesign = Boolean.FALSE
            def scope = new ScopeToSave(scopeId: scopeId, frameType: frameType, projectType: projectType, lcaCheckerList: lcaCheckerList)
            design.scope = scope
            design.disabledSections = entityService.resolveDisabledSections(design)

            if (!entityService.designAlreadyFound(design)) {
                List<String> indicatorIds = []
                List<Indicator> indicatorsChosen = []

                if (params.indicatorIds) {
                    indicatorIds = params.list("indicatorIds")

                    if (indicatorIds && entity?.indicators) {
                        entity.indicators.each { Indicator indicator ->
                            if (indicatorIds.contains(indicator.indicatorId)) {
                                indicatorsChosen.add(indicator)

                                if (!enabledIndicatorIds?.contains(indicator.indicatorId)) {
                                    if (design.disabledIndicators) {
                                        design.disabledIndicators.add(indicator.indicatorId)
                                    } else {
                                        design.disabledIndicators = [indicator.indicatorId]
                                    }
                                }
                            }
                        }
                    }
                }
                entityService.createEntity(design, entity)

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
                    projectLevelQueries.sort({it.toLowerCase()}) // Important to have LCA params before LCC params
                    entity = entityService.updateProjectLevelEntity(projectLevelQueries, null, queryAndLinkedIndicators, entity?.id?.toString())
                    */

                    List<String> projectLevelQueryIds = indicatorService.getProjectLevelQueryIdsForIndicators(indicatorsChosen)
                    String firstQueryId = entityService.determineFirstQueryIdFromProjectLevelQueryIds(entity, projectLevelQueryIds)

                    /* SW-1645 keep the commented code until after release 0.6.0
                    String firstQueryId

                    if (entity?.projectLevelQueries) {
                        if (entity.queryReady) {
                            for (String queryId in entity.projectLevelQueries) {
                                if (!entity.queryReady.get(queryId)) {
                                    firstQueryId = queryId
                                    break
                                }
                            }
                        } else {
                            firstQueryId = entity.projectLevelQueries.first()
                        }
                    }
                    */

                    // if the call was made from startNewProject() redirect back to it
                    if (session?.getAttribute(SessionAttribute.FORWARD_ORIGIN.attribute) == Constants.ForwardOrigin.STARTNEWPROJECT.origin) {
                        if (firstQueryId && lcaParamHandling && Constants.LCA_PARAMETERS_QUERYID.equals(firstQueryId)) {
                            entityService.setProjectWithDefaultLCAParameters(entity)
                        }
                        session?.setAttribute(SessionAttribute.FORWARD_ORIGIN.attribute, Constants.ForwardOrigin.NEWDESIGN.origin)
                        redirect controller: "projectTemplate", action: "startNewProject", params: [designId: design.id.toString(), entityId: entityId]
                    } else {
                        if (firstQueryId) {
                            if (lcaParamHandling && Constants.LCA_PARAMETERS_QUERYID.equals(firstQueryId)) {
                                entityService.setProjectWithDefaultLCAParameters(entity)
                                if (projectLevelQueryIds?.size() > 1) {
                                    redirect controller: "query", action: "form", params: [entityId: entityId, queryId: projectLevelQueryIds.get(1), projectLevel: true, newProject: true, designLevelParamQuery: true]
                                } else {
                                    chain(controller: "entity", action: "show", id: entity?.id, model: [designStartGuide: message(code: "entity.show.data_missing.guide")])
                                }
                            } else {
                                redirect controller: "query", action: "form", params: [entityId: entityId, queryId: firstQueryId, projectLevel: true, newProject: true, designLevelParamQuery: true]
                            }
                        } else {
                            chain(controller: "entity", action: "show", id: entity?.id, model: [designStartGuide: message(code: "entity.show.data_missing.guide")])
                        }
                    }
                } else {
                    // if the call was made from startNewProject() redirect back to it
                    if (session?.getAttribute(SessionAttribute.FORWARD_ORIGIN.attribute) == Constants.ForwardOrigin.STARTNEWPROJECT.origin) {
                        session?.setAttribute(SessionAttribute.FORWARD_ORIGIN.attribute, Constants.ForwardOrigin.NEWDESIGN.origin)
                        redirect controller: "projectTemplate", action: "startNewProject", params: [designId: design.id.toString(), entityId: entityId]
                    } else {
                        chain(controller: "entity", action: "show", id: entity?.id, model: [designStartGuide: message(code: "entity.show.data_missing.guide")])
                    }
                }
            } else {
                redirect controller: "entity", action: "show", id: entity?.id, model: [designStartGuide: message(code: "entity.show.data_missing.guide")]
            }
        }
        catch (OptimisticLockingException e) {
            loggerUtil.error(log, "OptimisticLockingException in createNew: " + e.getMessage())
            redirect(controller: "entity", action: "show", id: entity?.id)
        }
    }

    def simulationTool() {

        //***********************************************************
        //SESSION Simulation tool
        //***********************************************************

        String entityId = params.entityId
        String designId = params.designId
        String indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        String sessionId = Math.random().toString()

        SimulationToolSession simulationSession = new SimulationToolSession()
        simulationSession.sessionId = sessionId
        simulationSession.entityId = entityId
        simulationSession.designId = designId
        simulationSession.indicatorId = indicatorId
        simulationSession.indicator = indicator


        //***********************************************************

        //************************************************************
        //Check Entity Validity
        //************************************************************
        Entity entity = entityService.getEntityById(entityId)

        if(!entity){
            flash.warningAlert = "Entity not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

        //Check Entity License
        if(!entityService.isSimulationToolLicensed(featuresAvailableForEntity)){
            flash.warningAlert = g.message(code: "simulationTool.licenseNotValid")
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        //************************************************************


        //************************************************************
        //Check Design Validity and Locked or SuperLocked
        //************************************************************

        Entity design = entityService.getEntityById(designId)
        if(!design){
            flash.warningAlert = "Design not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        } else if (design.locked || design.superLocked) {
            flash.warningAlert = g.message(code: "simulationTool.isLocked")
            Boolean designLocked = (design.locked || design.superLocked)
            return [designLocked: designLocked]
        }

        //************************************************************
        //Check Indicator Validity
        //************************************************************
        if(!indicator){
            flash.warningAlert = "Indicator is not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        //Check Indicator License
        if (indicator.allowedCarbonDesignerRegions == null) {
            flash.warningAlert = g.message(code: "simulationTool.isNotEnabled")
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        //************************************************************

        //************************************************************
        //Check User Validity
        //************************************************************
        User user = userService.getCurrentUser(true)
        if(!user){
            flash.warningAlert = "User is not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        String unitSystem = user.unitSystem
        simulationSession.unitSystem = unitSystem
        //************************************************************

        //At the moment the queryId is Hard-coded
        //if query is null or maybe also ????empty????
        //************************************************************
        //Check Query Validity
        //************************************************************
        Query query = queryService.getQueryByQueryId(simulationToolService.QUERY_ID, true)
        if (!query) {
            String noQuery = g.message(code: "simulationTool.noQuery")
            flash.fadeErrorAlert = noQuery
            log.error(noQuery)
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        List<EntityTypeConstructionGroupAllocation> earlyPhaseToolGroups = query?.orderedConstructionGroupList

        if(!earlyPhaseToolGroups) {
            log.error("No Construction Group defined in the query --> orderedConstructionGroupList")
            flash.fadeErrorAlert = g.message(code: "simulationTool.noConstructionGroup")
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        //************************************************************

        //************************************************************
        //Check if an User Draft is present in the db
        //************************************************************
        SimulationTool simulationTool = simulationToolService.findSimulationToolDraft(entityId, designId)

        if(simulationTool){
            simulationSession.simulationToolId =  simulationTool.id?.toString()
            session?.setAttribute(sessionId, simulationSession)
            params.setProperty("sessionId", sessionId)
            params.setProperty("lccIndicatorId", simulationTool.lccIndicatorId)
            redirect action: "carbonDashboard", params: params
            //redirect action: "carbonDashboard", params: [entityId: entityId, designId: designId, indicatorId: indicatorId, sessionId: sessionId]
            return
        }
        //************************************************************
        List<String> allowedRegions = indicator.allowedCarbonDesignerRegions
        List<Resource> buildingTypeResources = Resource.collection.find([resourceGroup: simulationToolService.EARLY_PHASE_RESOURCE_GROUP_ID, active: true])?.collect({ it as Resource })
        Boolean isCompatible = Boolean.TRUE
        Boolean carbonDesignerOnlyLicensed = entity.getCarbonDesignerOnlyLicensed(validLicenses)

        if (carbonDesignerOnlyLicensed) {
            License l = validLicenses.find({it.licensedFeatures?.find({Feature.CARBON_DESIGNER_ONLY.equals(it.featureId)})})
            List<String> allowedRegionsByCDOnlyLicense = l?.licensedFeatures?.findAll({it.regionReferenceId})?.collect({it.regionReferenceId})?.unique()

            if (l?.conditionalBuildingTypes) {
                buildingTypeResources = buildingTypeResources.findAll({l.conditionalBuildingTypes.contains(it.resourceId)})
            }

            List<String> allowedOnlyAndAllowedByTool = []
            if (allowedRegionsByCDOnlyLicense) {
                allowedRegionsByCDOnlyLicense.each {
                    if (allowedRegions?.contains(it)) {
                        allowedOnlyAndAllowedByTool.add(it)
                    }
                }
                allowedRegions = allowedOnlyAndAllowedByTool

                if (allowedRegions.isEmpty()) {
                    isCompatible = Boolean.FALSE
                }
            }
        }

        if (!buildingTypeResources) {
            String noBuildingType = g.message(code: "simulationTool.noBuildingType")
            flash.fadeErrorAlert = noBuildingType
            log.error("CD: project: ${entity?.name}, ${noBuildingType}, carbonDesignerOnlyLicensed: ${carbonDesignerOnlyLicensed}")
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        //Map of the building type group by Region

        List<String> availableRegionIds = buildingTypeResources?.collectMany{it.simulationRegionIds?:[]}?.unique()

        //If allowedRegions is an empty list all the regions will be allowed
        if(allowedRegions){
            availableRegionIds = availableRegionIds?.intersect(allowedRegions)?.toList()
        } else if (!isCompatible) {
            availableRegionIds = null
        }

        if(!availableRegionIds){
            String noRegionDefinedForIndicator = g.message(code: "simulationTool.noRegionDefinedForIndicator")
            flash.fadeErrorAlert = noRegionDefinedForIndicator
            if (isCompatible) {
                log.error("$noRegionDefinedForIndicator $indicatorId")
            }
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        //It contains the regionReferenzeId and respective localizedName [Used to fill the related select]
        List<CarbonDesignerRegion> carbonDesignerRegions = query?.carbonDesignerRegions
        def mapRegionNames = carbonDesignerRegions?.collectEntries {[(it.regionReferenceId) : it.localizedName]} ?: [:]

        Map<String, String> regionReferenceNames = [:]
        //It contains the for every regionReference the list of buildingtype [Used As a JSON for the View]
        Map<String , List<Map<String,String>>> buildingTypeByRegion = [:]

        availableRegionIds?.each{ String regionId ->
            regionReferenceNames.put(regionId, mapRegionNames[regionId] ?: regionId)

            List<Map<String,String>> buildingType = buildingTypeResources.findResults{(it.simulationRegionIds?.contains(regionId)) ?
                    [(it.resourceId): optimiResourceService.getLocalizedName(it)] : null }
            buildingTypeByRegion.put(regionId, buildingType?:[])
        }

        //*****************************************************************************
        //ENERGY SECTION
        //*****************************************************************************

        List<Resource> energyResources = Resource.findAllByResourceGroupInListAndActive([simulationToolService.ENERGY_RESOURCE_GROUP_ID], true)?.collect({ it as Resource })
        energyResources = energyResources?.findAll {availableRegionIds?.contains(it.regionReference)}

        def energyResourcesByRegion = energyResources?.groupBy {it.regionReference}

        def energyScenarioByRegionAndBuilding = energyResourcesByRegion?.each {
            String regionReference = it.key
            List<CDRegionDefaultChoice> changeDefaultChoicesSets = query.carbonDesignerRegions?.find({ CarbonDesignerRegion c -> c.regionReferenceId?.equals(regionReference)})?.changeDefaultChoicesSets

            // Kunnon kikkare :D
            it.value = it.value?.groupBy{it.simulationToolBuildingType}
                    ?.collectEntries { k,v -> [ (k) : ( v.findResults{ r -> ([(r?.energyScenario) : changeDefaultChoicesSets?.find({it.energyScenario?.equals(r.energyScenario)})?.localizedName ?: g.message(code: "energyScenario." + r.energyScenario)])?:null}?.unique()) ?: [] ]}
        }


        //*****************************************************************************
        //Select Default region
        //*****************************************************************************

        String countryId = entity?.countryResourceResourceId
        def defaultRegionCD = carbonDesignerRegions?.find{it.countryIds?.contains(countryId)}

        def defaultRegion = (defaultRegionCD?.regionReferenceId) ?: simulationToolService.DEFAULT_REGION
        Boolean defaultRegionAvailable = Boolean.TRUE

        if(!availableRegionIds.contains(defaultRegion)){
            defaultRegion = availableRegionIds.first()
            defaultRegionAvailable = Boolean.FALSE
        }
        def defaultRegionObject = carbonDesignerRegions?.find({defaultRegion.equals(it.regionReferenceId)})

        Boolean hideEarthquakeZone = defaultRegion && defaultRegionObject?.hideEarthquakeZone ? Boolean.TRUE : Boolean.FALSE
        Boolean hideSurfaceDeReference = defaultRegion && defaultRegionObject?.referenceSurface ? Boolean.FALSE : Boolean.TRUE
        Boolean hideSurfaceDeScombles = defaultRegion && defaultRegionObject?.surfaceCombles ? Boolean.FALSE : Boolean.TRUE
        Boolean hideNombreDeLogement = defaultRegion && defaultRegionObject?.numberOfAccomodation ? Boolean.FALSE : Boolean.TRUE

        List<Map> zones = []
        defaultRegionObject?.earthquakeSettings?.each {
            zones.add([zone: it.earthquakeZone, compatibleBuildingTypes: it.compatibleBuildingTypeList])
        }

        def defaultRegionName = defaultRegionObject?.localizedName ?: ""

        def enabledBuildingElementByRegion = carbonDesignerRegions?.collectEntries{[(it.regionReferenceId) : it.enabledBuildingElement]} ?: [:]
        def selectedBuildingElementByRegion = carbonDesignerRegions?.collectEntries{[(it.regionReferenceId) : it.alwaysSelectedElements]} ?: [:]

        def defaultsGroups = earlyPhaseToolGroups?.findAll({"defaultsGroup".equals(it.buildingElement)})
        Boolean defaultsAvailableForTool = defaultsGroups?.collect({it.applicableIndicatorIds})?.flatten()?.contains(indicatorId)

        if (defaultsAvailableForTool) {
            defaultsGroups.each {
                it.applicableRegionReferenceIds?.each { String regionReferenceId ->
                    List<String> existing = enabledBuildingElementByRegion.get(regionReferenceId)

                    if (existing) {
                        existing.add(it.buildingElement)
                    } else {
                        existing = [it.buildingElement]
                    }
                    enabledBuildingElementByRegion.put(regionReferenceId, existing)
                }
            }
        }
        def enabledBuildingElement = defaultRegionCD?.enabledBuildingElement ?: []
        //*****************************************************************************

        //Gross Internal Floor Area of Entity
        Double grossArea = carbonDesignerRegionService.getDesignGFA(design, unitSystem, entity, defaultRegionCD)
        Double netFloorArea = carbonDesignerRegionService.getDesignGIFA(design, unitSystem, defaultRegionCD)
        Double heatedArea = carbonDesignerRegionService.getDesignHeatedArea(design, unitSystem, defaultRegionCD)
        Integer numberFloor = entity.numberFloor
        String basicQueryId = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.BASIC_QUERY_ID.toString())
        def typeAnswerIds = entity.datasets?.find{it.questionId.equals("type") && it.queryId?.equals(basicQueryId)}?.answerIds
        String defaultBuildingType = typeAnswerIds ? typeAnswerIds.first() : ""

        List<String> surfaceDeScomblesAllowedBuildingTypes = defaultRegionObject?.surfaceCombles?.showQuestionForBuildingTypes
        List<String> nombreDeLogementAllowedBuildingTypes = defaultRegionObject?.numberOfAccomodation?.showQuestionForBuildingTypes

        //Map Design Scope to CD Scopes
        Map<String, List<String>> mapScopesToCD = query?.mapScopesToCD ?: [:]
        List<String> designScopes = design?.scope?.lcaCheckerList
        List<String> mappedScopes
        if (mapScopesToCD && designScopes) {
            mappedScopes = designScopes.collectMany { mapScopesToCD[it]?: [] }

            if (enabledBuildingElement && defaultRegionAvailable) {
                mappedScopes = mappedScopes?.findAll{enabledBuildingElement.contains(it)}
            }
        }

        session?.setAttribute(simulationSession.sessionId, simulationSession)

        List<Indicator> allowedLCCIndicatorsForProject = []
        if (query?.allowedLCCIndicatorIds && entity && !"lumpSums".equals(entity?.costCalculationMethod)) {
            List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.DESIGN.toString())

            if (design && design.disabledIndicators) {
                indicators = indicators.findAll({!design.disabledIndicators.contains(it.indicatorId)})
            }
            indicators = indicators.findAll({query.allowedLCCIndicatorIds.contains(it.indicatorId)})

            if (indicators) {
                allowedLCCIndicatorsForProject.addAll(indicators)
            }
        }

        //Foundations List
        List<Construction> earlyPhaseConstructions = constructionService.getAllEarlyPhaseConstructionGroups()?.collect({it.constructions ?: []})?.flatten()
        List<Construction> foundationsConstructions = earlyPhaseConstructions?.findAll{it.earlyPhaseToolGroup == "foundations"}
        List<EntityTypeConstructionAllocation> entityTypeList = simulationToolService.getAllEntityTypeConstructionAllocation()

        foundationsConstructions?.each { Construction c ->
            List<EntityTypeConstructionAllocation> entityTypeConstructionAllocation = entityTypeList?.findAll({c.constructionId == it.constructionId})

            if (entityTypeConstructionAllocation) {
                c.regionReferenceId = entityTypeConstructionAllocation.collect({it.regionReferenceId}).join(",")
            }
        }
        def regionOptions = simulationToolService.getRegionOptions(carbonDesignerRegions, countryId, availableRegionIds, indicatorId, design, unitSystem) ?: []

        //Building Image Path
        String buildingTypeImageClass = defaultBuildingType ? simulationToolService.iconBuildingType(defaultBuildingType) : ""

        //Alternative Scenario
        //changeDefaultChoicesSets for material type and energy scenario list
        def defaultChoisesByRegion = query?.carbonDesignerRegions?.collectEntries{
            [(it.regionReferenceId) : it.changeDefaultChoicesSets?.collect{[ (it?.defaultChoiceId ?:"") : (it?.localizedName ?:"")]} ?: []
            ]
        } ?: [:]

        // To check if selected buildingtype is compatible with this energy scenario
        def comatibleEnergyScenarioPerRegion = query?.carbonDesignerRegions?.collectEntries{
            [(it.regionReferenceId) : it.changeDefaultChoicesSets?.collect{[ (it?.defaultChoiceId ?:"") : (it?.compatibleBuildingTypeList ?: [])]} ?: []
            ]
        } ?: [:]

        def defaultEnergyScenariosPerBuildingType = query?.carbonDesignerRegions?.collectEntries{
            [(it.regionReferenceId) : it.changeDefaultChoicesSets?.collect{[ (it?.defaultChoiceId ?:"") : (it?.defaultForBuildingTypes ?: null)]} ?: []
            ]
        } ?: [:]

        def alternativeDefaultEnergyScenariosPerBuildingType = query?.carbonDesignerRegions?.collectEntries{
            [(it.regionReferenceId) : it.changeDefaultChoicesSets?.collect{[ (it?.defaultChoiceId ?:"") : (it?.alternativeForBuildingTypes ?: null)]} ?: []
            ]
        } ?: [:]

        Double assessmentPeriod
        Boolean customAssessmentPeriod = Boolean.FALSE
        // Indicator custom assessment period:
        if (indicator.assessmentPeriodValueReference && !indicator.assessmentPeriodFixed) {
            customAssessmentPeriod = Boolean.TRUE
            assessmentPeriod = valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, design)
        }

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.CREATE_CONSTRUCTIONS])
        Boolean hasPrivateData = Boolean.FALSE
        if (featuresAllowed?.get(Feature.CREATE_CONSTRUCTIONS)) {
            Account account = userService.getAccount(user)

            if (account) {
                hasPrivateData = constructionService.getAccountHasPrivateConstructions(account.id.toString())
            }
        }
        [entityId: entityId, indicatorId: indicatorId, designId: designId, sessionId: sessionId,
         buildingTypeResources: buildingTypeResources,
         regionReferenceNames: regionReferenceNames, buildingTypeByRegion: buildingTypeByRegion as JSON, //Json serialization a way to send only the necessary values [marshaller]
         grossArea: grossArea ?:0, designNetFloorArea: netFloorArea ?:0, defaultsAvailableForTool: defaultsAvailableForTool,
         energyScenarioByRegionAndBuilding: energyScenarioByRegionAndBuilding, numberFloor: numberFloor,
         defaultRegion: defaultRegion, defaultRegionName: defaultRegionName, heatedArea: heatedArea ?: 0,
         defaultBuildingType: defaultBuildingType, hideEarthquakeZone: hideEarthquakeZone, defaultEarthquakeZones: zones as JSON,
         entity: entity, design: design, customAssessmentPeriod: customAssessmentPeriod, assessmentPeriod: assessmentPeriod,
         unitSystem: unitSystem, allowedLCCIndicatorsForProject: allowedLCCIndicatorsForProject, hasPrivateData: hasPrivateData,
         mappedScopes: mappedScopes, foundationsConstructions: foundationsConstructions, enabledBuildingElement: enabledBuildingElement,
         regionOptions: regionOptions as JSON, enabledBuildingElementByRegion: enabledBuildingElementByRegion as JSON,
         buildingTypeImageClass: buildingTypeImageClass, defaultChoisesByRegion: defaultChoisesByRegion as JSON,
         comatibleEnergyScenarioPerRegion: comatibleEnergyScenarioPerRegion as JSON, defaultEnergyScenariosPerBuildingType: defaultEnergyScenariosPerBuildingType as JSON,
         alternativeDefaultEnergyScenariosPerBuildingType: alternativeDefaultEnergyScenariosPerBuildingType as JSON, selectedBuildingElementByRegion: selectedBuildingElementByRegion as JSON,
         hideSurfaceDeReference: hideSurfaceDeReference, hideSurfaceDeScombles: hideSurfaceDeScombles, hideNombreDeLogement: hideNombreDeLogement,
         surfaceDeScomblesAllowedBuildingTypes: surfaceDeScomblesAllowedBuildingTypes, nombreDeLogementAllowedBuildingTypes: nombreDeLogementAllowedBuildingTypes]
    }

    def carbonDashboard() {
        User user = userService.getCurrentUser()
        Account currentUserAccount = userService.getAccount(user)
        String sessionId = params.sessionId
        //Check All the parms the Entity
        String entityId = params.entityId
        String designId = params.designId
        String indicatorId = params.indicatorId
        String lccIndicatorId = params.lccIndicatorId
        log.info("lccIndicatorId: ${lccIndicatorId}")
        Boolean allowPrivateConstructions = params.boolean("allowPrivateConstructions")
        Integer assessmentPeriod

        if (params.assessmentPeriod) {
            try {
                assessmentPeriod = params.assessmentPeriod.toString().toDouble().intValue()
            } catch (Exception e) {
                // Unparseable assessmentPeriod
                flash.warningAlert = "Given calculation period is not valid"
                redirect controller: "util", action: "earlyPhaseToolDeleteDraft", params : [sessionId: sessionId]
                return
            }
        }

        //************************************************************
        //Check Entity Validity -- Parent Entity
        //************************************************************
        Entity entity = entityService.getEntityById(entityId)

        if(!entity){
            flash.warningAlert = "Entity not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
        //Check Entity License
        if(!entityService.isSimulationToolLicensed(featuresAvailableForEntity)){
            flash.warningAlert = "The License is not valid!"
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        //************************************************************

        //************************************************************
        //Check Design Validity and Locked or SuperLocked
        //************************************************************
        Entity design = entityService.getEntityById(designId)

        if(!design){
            flash.warningAlert = "Design not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        } else if (design.locked || design.superLocked) {
            flash.warningAlert = "Simulation Tool is locked. To use the Simulation Tool unlock it from the entity main page."
            Boolean designLocked = (design.locked || design.superLocked)
            redirect controller: "entity", action: "show", id: entityId
            return [designLocked: designLocked]
        }
        //************************************************************


        //************************************************************
        //Check Indicator Validity
        //************************************************************
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        if(!indicator){
            flash.warningAlert = "Indicator is not valid"
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        //Check Indicator License
        if (indicator.allowedCarbonDesignerRegions == null) {
            flash.warningAlert = g.message(code: "simulationTool.isNotEnabled")
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        //**********************************************************

        //***************************************************************
        //Check the query if someone copy just the link
        //At the moment the queryId is Hard-coded
        //***************************************************************
        Query query = queryService.getQueryByQueryId(simulationToolService.QUERY_ID, true)
        Map<String, EntityTypeConstructionAllocation> constructionAllocationMap
        List<EntityTypeConstructionGroupAllocation> earlyPhaseToolGroups = query?.orderedConstructionGroupList
        List<String> queryIds = earlyPhaseToolGroups?.collect({ it.target?.get("queryId") })?.flatten()?.unique()

        if(!earlyPhaseToolGroups) {
            log.error("No Construction Group defined in the query --> orderedConstructionGroupList")
            flash.fadeErrorAlert = "No Construction Group defined"
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        //***************************************************************


        //******************************************************
        //Code to Load a saved profile for the simulation tool
        //******************************************************

        List<SimulationToolConstructionGroup> modelGroups = []
        List<SimulationToolConstructionGroup> modelGroupsFromDraft = null

        //Session Value
        Double grossFloorArea
        Integer aboveGroundFloors
        Integer undergroundHeatedFloors
        Integer undergroundUnheatedFloors
        String earthquakeZone
        CDEarthquakeSetting earthquakeSetting
        String foundationsDefault

        String regionReferenceId
        String buildingTypeId
        String energyScenarioId
        Double heatedArea
        Double dut
        Double averageTemperature

        //RE2020
        Double surfaceDeReference
        Double surfaceDeScombles
        Double nombreDeLogement

        SimulationToolSession simulationSession = session?.getAttribute(sessionId)

        if(!simulationSession){
            flash.warningAlert = simulationToolService?.SESSION_EXPIRED_LABEL
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        String unitSystem = simulationSession.unitSystem
        CarbonDesignerRegion carbonRegion
        Map<String, String> alternativeScenarioMap
        Boolean isAlternativeScenarioEnergy = false
        Indicator lccIndicator
        String costUnit
        String localizedCostText
        def defaultMaterialType

        if (lccIndicatorId) {
            Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            Question unitCostQuestion = questionService.getQuestion(additionalQuestionsQuery, "costPerUnit")
            costUnit = valueReferenceService.getValueForEntity(unitCostQuestion?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
            localizedCostText = message(code: "simulationTool.LCC")
            lccIndicator = indicatorService.getIndicatorByIndicatorId(lccIndicatorId, true)
        }
        SimulationTool simTool = simulationToolService.findSimulationToolDraft(entityId, designId)
        Boolean isDraftPresent = false
        def alternativeScenario
        List<Construction> createdDynamicConstructions = []

        Double totalNetArea

        if (simTool) { // Get params from draft
            isDraftPresent = true
            assessmentPeriod = simTool.assessmentPeriod
            allowPrivateConstructions = simTool.allowPrivateConstructions == Boolean.FALSE ? Boolean.FALSE : Boolean.TRUE // If null, allow if has any, saved draft before this param.
            grossFloorArea = simTool?.grossFloorArea
            aboveGroundFloors = simTool?.aboveGroundFloors
            undergroundHeatedFloors = simTool?.undergroundHeatedFloors
            undergroundUnheatedFloors = simTool?.undergroundUnheatedFloors
            earthquakeZone = simTool?.earthquakeZone

            //Energy Value
            buildingTypeId = simTool?.buildingTypeId
            energyScenarioId = simTool?.energyScenarioId
            heatedArea = simTool?.heatedArea
            dut = simTool?.dut
            averageTemperature = simTool?.averageTemperature
            totalNetArea = simTool?.totalNetArea

            //???Check At this point if the indicatorId designId entityId are the same from params and simTool????
            regionReferenceId = simTool.regionReferenceId

            //RE2020
            surfaceDeReference = simTool.surfaceDeReference
            surfaceDeScombles = simTool.surfaceDeScombles
            nombreDeLogement = simTool.nombreDeLogement

            //Incompatible region (In case the region in the draft is not present in the allowedRegion for the indicator, empty list allows all regions!)
            List<String> allowedRegions = indicator?.allowedCarbonDesignerRegions
            if(allowedRegions == null || (!allowedRegions.isEmpty() && !allowedRegions.contains(regionReferenceId))){
                String regionName = g.message(code: regionReferenceId)
                String allowedRegionsNames = allowedRegions?.collect{g.message(code: it)}?.toString() ?: ""
                flash.warningAlert = g.message(code: "simulationTool.incompatibleRegion", args: [regionName, allowedRegionsNames])
            }
            //***************************************************************************************************************
            carbonRegion = query?.carbonDesignerRegions?.find {it.regionReferenceId == regionReferenceId}
            modelGroupsFromDraft = simTool.simulationToolConstructionGroups
        } else {
            grossFloorArea = params.double("grossFloorArea") //Conversion Unit Factor
            aboveGroundFloors = params.int("aboveGroundFloors")
            undergroundHeatedFloors = params.int("undergroundHeatedFloors")
            undergroundUnheatedFloors = params.int("undergroundUnheatedFloors")
            totalNetArea = params.double('totalNetArea')
            regionReferenceId = params.regionReference
            buildingTypeId = params.buildingType
            alternativeScenario = params.alternativeScenario

            carbonRegion = query?.carbonDesignerRegions?.find {it.regionReferenceId == regionReferenceId}

            Boolean isEarthquakeZoneMedium = params.boolean("isEarthquakeZoneMedium")
            Boolean isEarthquakeZoneHigh = params.boolean("isEarthquakeZoneHigh")

            if (isEarthquakeZoneMedium||isEarthquakeZoneHigh) {
                earthquakeZone = isEarthquakeZoneMedium ? "medium" : "high"
                earthquakeSetting = carbonRegion?.earthquakeSettings?.find({ earthquakeZone.equals(it.earthquakeZone) && it.compatibleBuildingTypeList != null && (it.compatibleBuildingTypeList.isEmpty() || it.compatibleBuildingTypeList.contains(buildingTypeId)) })
            }

            foundationsDefault = params.foundationsDefault

            //Energy Value
            heatedArea = params.double("heatedArea") //Conversion Unit Factor
            dut = params.double("dut")
            averageTemperature = params.double("averageTemperature")

            def scenario = params.energyScenario
            def scenarioMap = simulationToolService.getCDScenarioValues(scenario, carbonRegion)
            energyScenarioId = scenarioMap.energyScenario
            defaultMaterialType = scenarioMap.materialType

            alternativeScenarioMap = simulationToolService.getCDScenarioValues(alternativeScenario, carbonRegion)

            //RE2020
            surfaceDeReference = params.double("surfaceDeReference")
            surfaceDeScombles = params.double("surfaceDeScombles")
            nombreDeLogement = params.double("nombreDeLogement")
        }


        constructionAllocationMap = simulationToolService.getEntityTypeConstructionAllocationByRegion(regionReferenceId)

        if(!constructionAllocationMap){
            flash.fadeErrorAlert = g.message(code: "simulationTool.constructionAllocationMissing")
            log.error(simulationToolService.NO_CONSTRUCTION_ALLOCATIONS_LABEL + regionReferenceId)
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        List<String> allowedConstructionIds = constructionAllocationMap?.keySet()?.toList()

        //It contains all the constructions for the early phase - simulation tool
        List<Construction> earlyPhaseConstructions = constructionService.getAllEarlyPhaseConstructionGroups()?.collectMany{ it?.getConstructionsByConstructionIds(allowedConstructionIds) ?:[] }
        Map<Construction, List<String>> allowedPrivateConstructionsPerTarget = [:]

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.CREATE_CONSTRUCTIONS])
        Boolean allowAllPrivateConstructions = currentUserAccount && allowPrivateConstructions && featuresAllowed?.get(Feature.CREATE_CONSTRUCTIONS)

        long now = System.currentTimeMillis()

        if (allowAllPrivateConstructions) {
            List<Construction> userPrivateConstructions = constructionService.getConstructionsByAccountId(currentUserAccount.id?.toString())
            log.info("CARBON DESIGNER FOUND: ${userPrivateConstructions?.size()} PRIVATE CONSTRUCTION FOR ACCOUNT: ${currentUserAccount.companyName}")
            if (userPrivateConstructions) {
                userPrivateConstructions.each {
                    if (simulationToolService.allowedPrivateConstructionUnits.contains(it.unit) && it.datasets) {
                        Resource mirrorResource = constructionService.getMirrorResourceObject(it)

                        if (mirrorResource && mirrorResource.active) {
                            List<String> passedQueries = []
                            queryIds?.each { String qId ->
                                if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(mirrorResource, qId, indicator, null, design)) {
                                    passedQueries.add(qId)
                                }
                            }

                            if (!passedQueries.isEmpty()) {
                                it.allowedPrivateConstruction = true
                                it.datasets.each{ Dataset entry ->
                                    entry.defaultConstituent = true
                                }
                                allowedPrivateConstructionsPerTarget.put(it, passedQueries)
                            }
                        }
                    }
                }
            }
        }
        log.info("CarbonDesigner: Handling ${allowedPrivateConstructionsPerTarget.keySet()?.size()} private constructions took: ${System.currentTimeMillis() - now} ms")
        //It will contain all the constructions have quantity > 0

        String costCalculationMethod = entity?.costCalculationMethod

        if (earlyPhaseConstructions) {
            earlyPhaseToolGroups?.each { groupAllocation ->
                String groupId = groupAllocation?.groupId
                SimulationToolConstructionGroup constructionGroupFromDraft = isDraftPresent ? modelGroupsFromDraft?.find({it.oid?.equals(groupId)}) : null
                String unit = groupAllocation?.unit
                List<Construction> constructions

                if ("defaultsGroup".equals(groupAllocation.buildingElement)) {
                    if (params.double(groupId)||constructionGroupFromDraft?.amount) { // Has quantity qiven
                        groupAllocation.defaultValueConstituents.each { CarbonDesignerDefaultValueConstituents defaultConstituent ->
                            Resource r = optimiResourceService.getResourceWithParams(defaultConstituent.defaultValueResource)

                            if (r) {
                                Construction construction = simulationToolService.createDynamicConstruction(r, unit, defaultConstituent)

                                if (constructions) {
                                    constructions.add(construction)
                                } else {
                                    constructions = [construction]
                                }
                                createdDynamicConstructions.add(construction)
                            }
                        }
                    }
                } else {
                    if(!unit){
                        log.error("The unit in metric system is missing in the query for the Construction Group --> ${groupId} ")
                        flashService.setErrorAlert("The unit in metric system is missing in the query for the Construction Group --> ${groupId} ", true)
                    }

                    //All the constructions of every Construction Group
                    constructions = earlyPhaseConstructions.findAll({ it.earlyPhaseToolGroup.equals(groupId) })

                    // Check if this group has any valid constructions and dont add private constructions if not.
                    if (allowedPrivateConstructionsPerTarget && allowedConstructionIds && constructions?.find({allowedConstructionIds.contains(it.constructionId)}) ) {
                        String targetQId = earlyPhaseToolGroups.find { it.groupId == groupId}?.target?.get("queryId")

                        if (targetQId) {
                            allowedPrivateConstructionsPerTarget.each { Construction key, List<String> passedQueryIds ->
                                if (passedQueryIds && passedQueryIds.contains(targetQId) && unit?.equals(key.unit)) {
                                    if (constructions) {
                                        constructions.add(key)
                                    } else {
                                        constructions = [key]
                                    }
                                }
                            }
                        }
                    }
                }

                if (constructions) {
                    String localizedName = groupAllocation.localizedName ?: g.message(code: groupId) ?: groupId
                    Double groupAmount = constructionGroupFromDraft?.initialAmount ?: params.double(groupId) ?: 0

                    //groupAmout (the quantity of a construction group) could be negative??????
                    if (groupAmount && groupAmount > 0) {
                        if (!constructionGroupFromDraft) {
                            groupAmount = groupAmount / simulationToolService.conversionUnitFactor(unitSystem, unit)
                        }

                        List<SimulationToolConstruction> simConstructions = []
                        constructions.each { Construction construction ->
                            EntityTypeConstructionAllocation constructionAllocation = constructionAllocationMap?.get(construction.constructionId)
                            //Select only the constructions present in the query
                            if(constructionAllocation||construction.allowedPrivateConstruction||construction.dynamicDefaultConstruction){
                                SimulationToolConstruction constructionFromDraft = null

                                if (construction.allowedPrivateConstruction) {
                                    constructionFromDraft = constructionGroupFromDraft?.constructions?.find({it.originalConstructionId == construction.id.toString()})
                                } else if (construction.dynamicDefaultConstruction) {
                                    if (construction.datasets) {
                                        Dataset dtset = construction.datasets.first()
                                        constructionFromDraft = constructionGroupFromDraft?.constructions?.find({it.constituents?.find({it.resourceId == dtset.resourceId}) ? true : false})
                                    }
                                } else {
                                    constructionFromDraft = constructionGroupFromDraft?.constructions?.find({it.constructionId == construction.constructionId})
                                }
                                Double constructionResult
                                Double constructionShare

                                if (isDraftPresent) {
                                    constructionShare = constructionFromDraft?.share?:0
                                    constructionResult = constructionFromDraft?.amount?:0
                                } else {
                                    constructionShare = earthquakeSetting?.constructionShare?.get(construction.constructionId) != null ? earthquakeSetting?.constructionShare?.get(construction.constructionId) : constructionAllocation?.getShare(buildingTypeId)

                                    if (construction.dynamicDefaultConstruction) {
                                        constructionShare = 1
                                        constructionResult = groupAmount
                                    } else {
                                        if (constructionShare != null) {
                                            constructionResult = groupAmount * constructionShare
                                        } else {
                                            constructionResult = 0
                                            constructionShare = 0
                                            if (!construction.allowedPrivateConstruction) {
                                                log.error("Carbon Designer --> RegionId: $regionReferenceId. It's not defined any share for the construction: ${construction.constructionId} on buildingType: ${buildingTypeId}")
                                                flashService.setErrorAlert("Carbon Designer --> RegionId: $regionReferenceId. It's not defined any share for the construction: ${construction.constructionId} on buildingType: ${buildingTypeId}", true)
                                            }
                                        }
                                    }
                                }

                                //Model for the view
                                List<String> materialTypeList = constructionAllocation?.materialTypeList ?:[]
                                List<String> incompatibleBuildingTypes = constructionAllocation?.incompatibleBuildingTypes ?:[]
                                List<String> defaultResources = constructionAllocation?.defaultResources ?:[]

                                Boolean isIncompatible = incompatibleBuildingTypes.find{it == buildingTypeId}
                                SimulationToolConstruction stc = new SimulationToolConstruction(
                                        amount: constructionResult,
                                        share: constructionShare,
                                        materialTypeList: materialTypeList,
                                        defaultResources: defaultResources,
                                        isIncompatible: isIncompatible
                                )

                                def c = simulationToolService.build(stc,
                                        construction, construction.dynamicDefaultConstruction ? groupId : null,
                                        construction.dynamicDefaultConstruction &&
                                                construction.datasets ? construction.datasets.first().resourceId : null)

                                Boolean lumpSumCostMethod = "lumpSums".equals(costCalculationMethod)

                                if (lccIndicator) {
                                    Boolean resourceBasedCost = "materialsSpecificCost".equals(costCalculationMethod)

                                    if (resourceBasedCost && construction.costConstruction_EUR) {
                                        c.userGivenCost = construction.costConstruction_EUR
                                    } else if(lumpSumCostMethod){
                                        c.userGivenCost = 0.0
                                    }else {
                                        Double constituentTotalCost
                                        Double costMultiplier = constructionResult ?: 1

                                        List<Dataset> datasets = construction.datasets?.findAll({it.defaultConstituent})
                                        if (datasets) {
                                            ResourceCache resourceCache = ResourceCache.init(datasets)
                                            for (Dataset d: datasets) {
                                                Double cost

                                                if (d.quantity) {
                                                    // Cost calculation method null here to calculate cost correct for construction without specific cost construction eur
                                                    Double costPerUnit = costStructureService.getCost(resourceCache.getResource(d), entity, lccIndicator, d.userGivenUnit, null, datasetService.getThickness(d.additionalQuestionAnswers))


                                                    if (costPerUnit) {
                                                        cost = costPerUnit * d.quantity * costMultiplier
                                                    }
                                                }

                                                if (cost) {
                                                    if (constituentTotalCost) {
                                                        constituentTotalCost = constituentTotalCost + cost
                                                    } else {
                                                        constituentTotalCost = cost
                                                    }
                                                }

                                            }
                                        }

                                        if (constituentTotalCost && costMultiplier) {
                                            c.userGivenCost = (constituentTotalCost / costMultiplier).round(2)
                                        }
                                    }
                                } else {
                                    if(lumpSumCostMethod){
                                        c.userGivenCost = 0.0
                                    }
                                }
                                simConstructions.add(c)
                            }
                        }
                        //Model for the view
                        //Add the group only if the constructions is not empty
                        if(simConstructions){
                            // Incompatible constructions at the bottom
                            simConstructions = simConstructions.sort({it.isIncompatible})
                            def group = new SimulationToolConstructionGroup(oid: groupId,
                                    name: localizedName,
                                    constructions: simConstructions,
                                    amount: isDraftPresent ? constructionGroupFromDraft?.amount?:0 : groupAmount,
                                    unit: unit,
                                    initialAmount: groupAmount)
                            modelGroups.add(group)
                        } else {
                            log.warn("No constructions define in the excel for Region: ${carbonRegion?.regionReferenceId} and group: ${groupId}")
                            flashService.setWarningAlert("No constructions define in the excel for Region: ${carbonRegion?.regionReferenceId} and group: ${groupId}", true)
                        }
                    }

                }
            }
        } else {
            flash.fadeErrorAlert = "No EarlyPhase Constructions are defined or active"
            log.error("No EarlyPhase Constructions are defined or active in 'Manage Construction'")
            redirect controller: "entity", action: "show", id: entityId
            return
        }

        if(defaultMaterialType){
            simulationToolService.setDefaultMaterialType(modelGroups, defaultMaterialType)
        }
        //******************************************************
        //CHECK THE SIZE OF MODEL GROUP

        if(modelGroups && foundationsDefault){
            modelGroups = simulationToolService.setFoundationsDefaultToModel(modelGroups, foundationsDefault)
        }

        //***************************************************************************
        //Convert in Metric System all the input
        //***************************************************************************
        Double conversionUnitFactorM2 = simulationToolService.conversionUnitFactor(unitSystem, "m2")
        if (!simTool) {
            // Dont convert grossFloorArea if simTool present since its already in metric and might break if user has changed unitSystems
            grossFloorArea = grossFloorArea ? (grossFloorArea / conversionUnitFactorM2) : grossFloorArea
        }
        heatedArea = heatedArea ? (heatedArea / conversionUnitFactorM2) : heatedArea

        //***************************************************************************

        //***************************************************************************
        //Set user input in the session
        //***************************************************************************
        simulationSession?.lccIndicatorId = lccIndicatorId
        simulationSession?.regionReferenceId = regionReferenceId
        simulationSession?.buildingTypeId = buildingTypeId

        simulationSession?.grossFloorArea = grossFloorArea ? grossFloorArea : 1
        simulationSession?.aboveGroundFloors = aboveGroundFloors ?:0
        simulationSession?.undergroundHeatedFloors = undergroundHeatedFloors ?:0
        simulationSession?.undergroundUnheatedFloors = undergroundUnheatedFloors ?:0
        simulationSession?.earthquakeZone = earthquakeZone
        simulationSession?.allowPrivateConstructions = allowPrivateConstructions ?: false

        simulationSession?.energyScenarioId = energyScenarioId
        simulationSession?.heatedArea = heatedArea
        simulationSession?.dut = dut
        simulationSession?.averageTemperature = averageTemperature
        simulationSession?.assessmentPeriod = assessmentPeriod
        simulationSession?.totalNetArea = totalNetArea

        //RE2020
        simulationSession?.surfaceDeReference = surfaceDeReference
        simulationSession?.surfaceDeScombles = surfaceDeScombles
        simulationSession?.nombreDeLogement = nombreDeLogement

        session?.setAttribute(sessionId, simulationSession)
        //***************************************************************************

        //START Calculation

        if(!modelGroups){
            flash.warningAlert = "All the construction groups have 0 quantity"
            log.error("All the construction groups have 0 quantity" + " regionReferenceId: ${regionReferenceId}, buildingTypeId: ${buildingTypeId} ,energyScenarioId: ${energyScenarioId}, heatedArea: ${heatedArea}, dut: ${dut}, averageTemperature: ${averageTemperature}")
            redirect controller: "util", action: "earlyPhaseToolDeleteDraft", params : [sessionId: sessionId]
            return
        }

        List<Indicator> indicatorsToSave = []
        queryIds?.each { String queryId ->
            List<Indicator> indicators = indicatorService.getIndicatorsWithSameQuery(entity, queryId)

            if (indicators) {
                indicatorsToSave.addAll(indicators)
            }
        }
        indicatorsToSave = indicatorsToSave?.unique()
        Entity tempEntity = new Entity()
        simulationToolService.firstCalculationConstructionImpactV2(tempEntity, indicator, modelGroups, indicatorsToSave, entity, earlyPhaseToolGroups,
                unitSystem, regionReferenceId, lccIndicator, design, assessmentPeriod, createdDynamicConstructions, modelGroupsFromDraft)
        //************************************************

        if(!tempEntity || !tempEntity?.datasets || !tempEntity?.tempCalculationResults){
            flash.fadeErrorAlert = simulationToolService.CALCULATION_EMPTY_LABEL
            log.error(simulationToolService.CALCULATION_EMPTY_LABEL + " regionReferenceId: ${regionReferenceId}, buildingTypeId: ${buildingTypeId} ,energyScenarioId: ${energyScenarioId}, heatedArea: ${heatedArea}, dut: ${dut}, averageTemperature: ${averageTemperature}")
            redirect controller: "util", action: "earlyPhaseToolDeleteDraft", params : [sessionId: sessionId]
            return
        }

        //******************************************************
        //ENERGY SECTION
        //******************************************************
        String countryId = entity?.datasets?.find{it?.questionId == "country"}?.answerIds?.find{it}
        dut = dut ?: 0
        averageTemperature = averageTemperature ?: 0

        List<SimulationToolEnergyType> energyTypeList

        if(simTool){
            energyTypeList = simTool?.energyTypeList
        }else {
            if(!energyScenarioId) {
                energyScenarioId = alternativeScenarioMap.energyScenario
                if(energyScenarioId) isAlternativeScenarioEnergy = true
            }
            energyTypeList = simulationToolService.energyResource(energyScenarioId, buildingTypeId, heatedArea, countryId, dut, averageTemperature)
        }

        energyTypeList?.each { SimulationToolEnergyType energyType ->
            if(energyType?.defaultOption){
                Map target = simulationToolService.getTargetByResourceId(query, energyType?.defaultOption)
                if (target) {
                    def result = simulationToolService.energyResourceImpact(energyType, indicator, tempEntity, target, design, entity, lccIndicator, assessmentPeriod)
                    if (result) {
                        energyType.co2e = result.co2e
                        energyType.manualId = result.manualId
                        energyType.userGivenCost = result.cost
                        energyType.calculatedCost = result.calculatedCost ?: 0
                        if (result.tempEntity) {
                            simulationSession?.tempEntity = result.tempEntity
                            session?.setAttribute(sessionId, simulationSession)///??????
                        }
                    }
                }
            }
        }

        //******************************************************

        simulationSession?.tempEntity = tempEntity
        session?.setAttribute(sessionId, simulationSession)

        //Entity entity = entityService.getEntityById(entityId)
        //List<Entity> designList = entity?.childrenByChildEntities?.findAll({ !it.deleted && !it.locked && && EntityClass.DESIGN.toString().equals(it.entityClass) })

        grossFloorArea = grossFloorArea ? grossFloorArea : 1

        Boolean allowMerge
        Boolean overwriteDirectly
        String warningMergeMessage = g.message(code:"simulationTool.swalTextSaveQuery")
        Integer datasetLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, query?.maxMergeLimit, session)
        String buildingQuery = simulationToolService.BUILDING_MATERIAL_QUERY
        Query buildingQueryObject = queryService.getQueryByQueryId(buildingQuery,true)
        String energyQuery = simulationSession.energyScenarioId ? simulationToolService.ENERGY_QUERY : ''

        Integer numberDataset = design.datasets?.findAll{ it.queryId == buildingQuery || it.queryId == energyQuery }?.size() ?: 0

        if(numberDataset == 0){overwriteDirectly = true}

        if(datasetLimit != null){
            allowMerge = (numberDataset <= datasetLimit)
            if(!allowMerge){
                warningMergeMessage = g.message(code:"simulationTool.swalTextSaveQueryNoMerge",args:[buildingQueryObject?.localizedName, numberDataset, datasetLimit])
            }
        } else{
            allowMerge = Boolean.TRUE
        }

        List<String> materialTypeList = modelGroups?.collectMany {it.constructions?:[]}?.collectMany{SimulationToolConstruction c -> c.materialTypeList?:[]}?.findResults{it}?.unique()

        //Add to the list the option to reset from default material chose
        if(materialTypeList){
            materialTypeList.push(simulationToolService.RESET_DEFAULT_MATERIAL)
        }

        //New Parameters for Heading
        String regionReferenceName = carbonRegion?.localizedName ?: ""
        String regionInfoText = localizedLinkService.getTransformStringIdsToLinks(carbonRegion?.localizedRegionInfo ?: "")
        regionInfoText = regionInfoText?.replaceAll("\"", "'")

        int totalFloors = simulationSession?.totalFloor ?: 0

        //************************************************************************************************************************
        //Material Type && Energy Scenario Default
        //************************************************************************************************************************
        //Energy Scenaio Avaible
        List<Resource> energyTypeResources = Resource.findAllByResourceGroupInListAndActive([simulationToolService.ENERGY_RESOURCE_GROUP_ID], true)
        energyTypeResources = energyTypeResources?.findAll {it.regionReference == regionReferenceId && it.simulationToolBuildingType == buildingTypeId}
        List<String> avaibleEnergyScenarioResources = energyTypeResources?.collect{it.energyScenario}?.unique()

        Resource resBuilding = Resource.findByResourceIdAndActive(buildingTypeId, true)

        def energyScenarioMap = [:]
        avaibleEnergyScenarioResources?.each{ energyScenario ->
            def eS = [:]
            energyTypeResources?.findAll{ it.energyScenario == energyScenario}?.each {
                def energyType = simulationToolService.getEnergyType(energyScenario, it, heatedArea, countryId, buildingTypeId, dut, averageTemperature, resBuilding)
                eS << ["${energyType?.typeId}" : [demand_m2 : energyType?.demand_m2 ?: 0, efficiencyFactor : energyType?.efficiencyFactor ?: 0]]
            }
            energyScenarioMap.put(energyScenario, eS)

        }

        //changeDefaultChoicesSets for material type and energy scenario list
        List<CDRegionDefaultChoice> defaultChoicesSets = carbonRegion?.changeDefaultChoicesSets ?: []

        defaultChoicesSets = defaultChoicesSets?.findAll({!it.compatibleBuildingTypeList||it.compatibleBuildingTypeList.contains(buildingTypeId)})

        //Filter by available materialType
        defaultChoicesSets = defaultChoicesSets?.findAll{
            if(!it.materialType) return true

            if(materialTypeList?.contains(it.materialType))
                return true
            else
                return false

        }

        //Filter by available energyScenario
        defaultChoicesSets = defaultChoicesSets?.findAll{
            if(it.energyScenario && !energyTypeList)
                return false
            else
                true
        }
        //************************************************************************************************************************

        def constructionGroupList = query?.orderedConstructionGroupList?.findAll{ g ->
            modelGroups?.find{g.groupId == it.oid}
        }?.collect{
            [groupId : it.groupId,
             buildingElement: it.buildingElement,
             buildingElementName : simulationToolService.getLocalizedMessage(it.buildingElement)
            ]
        }

        Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)

        String commentHeading = additionalQuestionsQuery
                ?.sections?.find{it.sectionId == "additionalQuestionsSection"}
                ?.questions?.find {it.questionId == "comment"}?.localizedQuestion ?: ""

        session?.setAttribute("commentHeading", commentHeading)
        [entityId: entityId, indicatorId: indicatorId, indicatorName : indicatorService.getLocalizedName(indicator) ?: "", defaultGrouping: carbonRegion.defaultGraphBreakdown,
         designId        : designId,
         modelGroups     : modelGroups,
         energyTypeList  : energyTypeList?:[],
         sessionId       : sessionId,
         heatedArea      : heatedArea,
         entity          : entity, design: design, isDraftPresent: isDraftPresent, grossFloorArea: grossFloorArea,
         unitSystem      : unitSystem, lccIndicator: lccIndicator, costUnit: costUnit, localizedCostText: localizedCostText,
         materialTypeList: materialTypeList,
         allowMerge      : allowMerge, overwriteDirectly: overwriteDirectly,
         regionReferenceName: regionReferenceName, totalFloors: totalFloors, calculationPeriod: assessmentPeriod,
         regionInfoText  : regionInfoText,
         energyScenarioMap: energyScenarioMap, defaultChoicesSets: defaultChoicesSets, constructionGroupList: constructionGroupList ?: [],
         simulationSession: simTool ? simTool : simulationSession,
         alternativeScenarioJson: (alternativeScenarioMap?: [:]) as JSON, isAlternativeScenarioEnergy: isAlternativeScenarioEnergy,warningMergeMessage:warningMergeMessage,
         commentHeading: commentHeading, alternativeScenario: alternativeScenario, buildingName: optimiResourceService.getLocalizedName(resBuilding), baselineScenario: params.energyScenario]
         //simulationSession: simulationSession]
    }

//CHECK ALL THE POSSIBLE NULL OBJECT
    def nextDashboard() {
        //Check all the null object like tempEntity and so on
        String sessionId = params.get("sessionId")
        String entityId = params.get("entityId").toString()
        String designId = params.get("designId").toString()
        Entity parent = entityService.getEntityById(entityId)
        Entity designEntity = entityService.getEntityById(designId)
        String indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)

        Boolean mergeSimulation = params.boolean("mergeSimulation") ?: Boolean.FALSE

        SimulationToolSession simulationSession = (SimulationToolSession) session?.getAttribute(sessionId)

        if(!simulationSession){
            flash.warningAlert = simulationToolService?.SESSION_EXPIRED_LABEL
            redirect controller: "entity", action: "show", id: entityId
            return
        }
        Query query = queryService.getQueryByQueryId(simulationToolService.QUERY_ID, true)

        Entity tempEntity = simulationSession.tempEntity

        String jsonModel = (String) params.simModel
        String jsonEnergyTypeListModel = params.energyTypeListModel

        if(jsonModel && tempEntity && designEntity && indicator){

            //Check Simulation Model
            List<SimulationToolConstructionGroup> simModel = (List<SimulationToolConstructionGroup>) new JsonSlurper().parseText(jsonModel);

            List<SimulationToolConstruction> constructions = simModel?.collectMany {it.constructions ?: []}
            //Check if a construction has amount !=0 and has more than one default constituent

            List<SimulationToolConstruction> constructionsWithAmount = constructions?.findAll({it.amount})

            List<Dataset> datasets = []
            List<Dataset> constructionDatasets = []
            List<Dataset> constituentDatasets = []
            List<String> datasetManualIdsToRemove = []

            constructionsWithAmount?.each { simulationToolConstruction ->
                Dataset d = tempEntity.datasets?.find({it?.uniqueConstructionIdentifier?.endsWith(simulationToolConstruction.oid)})
                if(d){
                    if (simulationToolConstruction.dynamicDefault) {
                        Dataset constituent = tempEntity.datasets?.find({it.defaultConstituent && it.parentConstructionId?.equals(simulationToolConstruction.oid)})

                        if (constituent) {
                            if (query && simulationToolConstruction.dynamicDefaultGroupId) {
                                EntityTypeConstructionGroupAllocation entityTypeConstructionGroupAllocation = query.orderedConstructionGroupList?.find({simulationToolConstruction.dynamicDefaultGroupId.equals(it.groupId)})
                                CarbonDesignerDefaultValueConstituents carbonDesignerDefaultValueConstituent = entityTypeConstructionGroupAllocation?.defaultValueConstituents?.find({it.defaultValueResource?.equals(constituent.resourceId)})

                                // Save value doesnt save value but resource, saveResource saves value and resource
                                if (carbonDesignerDefaultValueConstituent && "saveValue".equals(carbonDesignerDefaultValueConstituent.savingMethod)) {
                                    constituent.parentConstructionId = null
                                    constituent.uniqueConstructionIdentifier = null
                                    constituent.answerIds = []
                                    constituent.quantity = null
                                    constituent.queryId = carbonDesignerDefaultValueConstituent.target?.get("queryId")
                                    constituent.sectionId = carbonDesignerDefaultValueConstituent.target?.get("sectionId")
                                    constituent.questionId = carbonDesignerDefaultValueConstituent.target?.get("questionId")

                                    // Copy construction costs to only constituent that doesnt get upper construction row.
                                    if (!constituent.additionalQuestionAnswers) {
                                        constituent.additionalQuestionAnswers = [:]
                                    }

                                    if (d.additionalQuestionAnswers?.get("costPerUnit") != null) {
                                        // Copy construction costs to only constituent that doesnt get upper construction row.
                                        if (!constituent.additionalQuestionAnswers) {
                                            constituent.additionalQuestionAnswers = [:]
                                        }
                                        constituent.additionalQuestionAnswers.put("costPerUnit", d.additionalQuestionAnswers.get("costPerUnit"))
                                        constituent.additionalQuestionAnswers.put("totalCost", d.additionalQuestionAnswers.get("totalCost"))
                                        constituent.additionalQuestionAnswers.put("noUserSetTotal", true)
                                        constituent.userSetCost = true
                                        constituent.userSetTotalCost = false
                                    }
                                } else if (carbonDesignerDefaultValueConstituent && "saveResource".equals(carbonDesignerDefaultValueConstituent.savingMethod)) {
                                    constituent.parentConstructionId = null
                                    constituent.uniqueConstructionIdentifier = null
                                    constituent.queryId = carbonDesignerDefaultValueConstituent.target?.get("queryId")
                                    constituent.sectionId = carbonDesignerDefaultValueConstituent.target?.get("sectionId")
                                    constituent.questionId = carbonDesignerDefaultValueConstituent.target?.get("questionId")

                                    if (d.additionalQuestionAnswers?.get("costPerUnit") != null) {
                                        // Copy construction costs to only constituent that doesnt get upper construction row.
                                        if (!constituent.additionalQuestionAnswers) {
                                            constituent.additionalQuestionAnswers = [:]
                                        }
                                        constituent.additionalQuestionAnswers.put("costPerUnit", d.additionalQuestionAnswers.get("costPerUnit"))
                                        constituent.additionalQuestionAnswers.put("totalCost", d.additionalQuestionAnswers.get("totalCost"))
                                        constituent.additionalQuestionAnswers.put("noUserSetTotal", true)
                                        constituent.userSetCost = true
                                        constituent.userSetTotalCost = false
                                    }
                                } else {
                                    datasetManualIdsToRemove.add(constituent.manualId)
                                }
                            } else {
                                datasetManualIdsToRemove.add(constituent.manualId)
                            }
                        }
                        datasetManualIdsToRemove.add(d.manualId)
                    } else {
                        if (simulationToolConstruction.constituents?.findAll({it.defaultConstituent})?.size() > 1) {
                            if (d.additionalQuestionAnswers?.get("costPerUnit") != null) {
                                d.userSetCost = true
                                d.userSetTotalCost = false
                            }
                            String comment = simulationToolConstruction?.comment
                            if(comment) {
                                def addQ = d.additionalQuestionAnswers
                                if(addQ) addQ.put("comment", comment) else addQ = ["comment" : comment]
                            }
                            constructionDatasets.add(d)
                        } else {
                            Dataset constituent = tempEntity.datasets?.find({it.defaultConstituent && it.parentConstructionId?.equals(simulationToolConstruction.oid)})

                            if (constituent) {
                                constituent.parentConstructionId = null
                                constituent.uniqueConstructionIdentifier = null

                                if (d.additionalQuestionAnswers?.get("costPerUnit") != null) {
                                    // Copy construction costs to only constituent that doesnt get upper construction row.
                                    if (!constituent.additionalQuestionAnswers) {
                                        constituent.additionalQuestionAnswers = [:]
                                    }
                                    Double costPerUnit = d.additionalQuestionAnswers.get("costPerUnit")?.toString()?.replace(',', '.')?.isNumber() ? d.additionalQuestionAnswers.get("costPerUnit")?.toString()?.replace(',', '.')?.toDouble() : 0D
                                    Double totalCost = d.additionalQuestionAnswers.get("totalCost")?.toString()?.replace(',', '.')?.isNumber() ? d.additionalQuestionAnswers.get("totalCost")?.toString()?.replace(',', '.')?.toDouble() : 0D

                                    if (costPerUnit) {
                                        Double divider = constructionService.getOriginalConstituentQuantityForManualId(constituent.additionalQuestionAnswers.get("datasetIdFromConstruction")?.toString())

                                        if (divider) {
                                            costPerUnit = (costPerUnit / divider).round(2)

                                            if (d.quantity) {
                                                totalCost = (costPerUnit * d.quantity).round(0)
                                            } else {
                                                totalCost = 0D
                                            }
                                        }
                                    }
                                    constituent.additionalQuestionAnswers.put("costPerUnit", costPerUnit ? costPerUnit?.toString() : "")
                                    constituent.additionalQuestionAnswers.put("totalCost", totalCost ? totalCost?.toString() : "")
                                    constituent.additionalQuestionAnswers.put("noUserSetTotal", true)
                                    constituent.userSetCost = true
                                    constituent.userSetTotalCost = false
                                }
                                constructionDatasets.add(constituent)
                            } else {
                                log.error("CARBON DESIGNER ERROR: No constituents found for Construction: ${simulationToolConstruction?.constructionId} / ${simulationToolConstruction?.name}!")
                                flashService.setErrorAlert("CARBON DESIGNER ERROR: No constituents found for Construction: ${simulationToolConstruction?.constructionId} / ${simulationToolConstruction?.name}!", true)
                            }
                        }
                    }
                }
            }

            //Take the datasets that have amount !=0 and are default constituent
            tempEntity.datasets?.findResults({(it.defaultConstituent && it.quantity)? it : null})?.each {
                if (it) {
                    if (it.additionalQuestionAnswers?.get("costPerUnit") != null) {
                        it.userSetCost = true

                        if (it.additionalQuestionAnswers?.get("noUserSetTotal")) {
                            it.additionalQuestionAnswers.remove("noUserSetTotal")
                        } else {
                            it.userSetTotalCost = true
                        }
                    }
                    if (!datasetManualIdsToRemove.contains(it.manualId)) {
                        constituentDatasets.add(it)
                    }
                }
            }
            if(constructionDatasets){
                datasets.addAll(constructionDatasets)
            }
            if(constituentDatasets){
                datasets.addAll(constituentDatasets)
            }
            if(jsonEnergyTypeListModel){

                List<SimulationToolEnergyType> energyTypeListModel = new JsonSlurper().parseText(jsonEnergyTypeListModel) as List<SimulationToolEnergyType>

                List<String> energyResourceIdList = energyTypeListModel?.findResults {(it?.defaultOption)? it.manualId : null}
                List<Dataset> energyDatasets = []
                energyResourceIdList?.each{ String manualId ->
                    //it's possible use purchase in the energyResourceIdList but the puchase transient doesnt work with the serialization from json
                    Dataset d =  tempEntity.datasets?.find{it.manualId == manualId && it.quantity}
                    if(d){
                        if (d.additionalQuestionAnswers?.get("costPerUnit") != null) {
                            d.userSetCost = true
                            d.userSetTotalCost = false
                        }
                        energyDatasets.add(d)
                    }
                }

                datasets.addAll(energyDatasets)
            }
            CarbonDesignerRegion region = query?.carbonDesignerRegions?.find({it.regionReferenceId.equals(simulationSession.regionReferenceId)})

            if (region) {
                if (region.grossFloorAreaResource && simulationSession.grossFloorArea) {
                    Dataset gfaDataset = carbonDesignerRegionService.getAreaDataset(designEntity, region.grossFloorAreaResource)

                    if (!gfaDataset) {
                        gfaDataset = new Dataset()
                        gfaDataset.manualId = new ObjectId().toString()
                        gfaDataset.queryId = region.grossFloorAreaResource.queryId
                        gfaDataset.sectionId = region.grossFloorAreaResource.sectionId
                        gfaDataset.questionId = region.grossFloorAreaResource.questionId
                        gfaDataset.resourceId = region.grossFloorAreaResource.resourceId
                        gfaDataset.quantity = simulationSession.grossFloorArea
                        gfaDataset.userGivenUnit = "m2"
                        gfaDataset.answerIds = ["${simulationSession.grossFloorArea}"]
                        datasets.add(gfaDataset)
                    } else if (gfaDataset && !gfaDataset.locked) {
                        gfaDataset.quantity = simulationSession.grossFloorArea
                        gfaDataset.answerIds = ["${simulationSession.grossFloorArea}"]
                        datasets.add(gfaDataset)
                    }
                }

                if (region.grossInternalFloorAreaResource && simulationSession.totalNetArea) {
                    Dataset gifaDataset = carbonDesignerRegionService.getAreaDataset(designEntity, region.grossInternalFloorAreaResource)

                    if (!gifaDataset) {
                        gifaDataset = new Dataset()
                        gifaDataset.manualId = new ObjectId().toString()
                        gifaDataset.queryId = region.grossInternalFloorAreaResource.queryId
                        gifaDataset.sectionId = region.grossInternalFloorAreaResource.sectionId
                        gifaDataset.questionId = region.grossInternalFloorAreaResource.questionId
                        gifaDataset.resourceId = region.grossInternalFloorAreaResource.resourceId
                        gifaDataset.quantity = simulationSession.totalNetArea
                        gifaDataset.userGivenUnit = "m2"
                        gifaDataset.answerIds = ["${simulationSession.totalNetArea}"]
                        datasets.add(gifaDataset)
                    } else if (gifaDataset && !gifaDataset.locked) {
                        gifaDataset.quantity = simulationSession.totalNetArea
                        gifaDataset.answerIds = ["${simulationSession.totalNetArea}"]
                        datasets.add(gifaDataset)
                    }
                }

                if (region.heatedAreaResource && simulationSession.grossFloorArea) {
                    Dataset heatedAreaDataset = carbonDesignerRegionService.getAreaDataset(designEntity, region.heatedAreaResource)

                    if (!heatedAreaDataset) {
                        heatedAreaDataset = new Dataset()
                        heatedAreaDataset.manualId = new ObjectId().toString()
                        heatedAreaDataset.queryId = region.heatedAreaResource.queryId
                        heatedAreaDataset.sectionId = region.heatedAreaResource.sectionId
                        heatedAreaDataset.questionId = region.heatedAreaResource.questionId
                        heatedAreaDataset.resourceId = region.heatedAreaResource.resourceId
                        heatedAreaDataset.quantity = simulationSession.heatedArea
                        heatedAreaDataset.userGivenUnit = "m2"
                        heatedAreaDataset.answerIds = ["${simulationSession.heatedArea}"]
                        datasets.add(heatedAreaDataset)
                    } else if (heatedAreaDataset && !heatedAreaDataset.locked) {
                        heatedAreaDataset.quantity = simulationSession.heatedArea
                        heatedAreaDataset.answerIds = ["${simulationSession.heatedArea}"]
                        datasets.add(heatedAreaDataset)
                    }
                }

                if (region.referenceSurface && simulationSession.surfaceDeReference) {
                    Dataset referenceSurfaceDataset = carbonDesignerRegionService.getAreaDataset(designEntity, region.referenceSurface)
                    List<Question> additionalQuestions = simulationToolService.getInheritedAdditionalQuestion(region.referenceSurface.queryId, region.referenceSurface.sectionId, region.referenceSurface.questionId, indicator)

                    if (!referenceSurfaceDataset) {
                        referenceSurfaceDataset = new Dataset()
                        referenceSurfaceDataset.manualId = new ObjectId().toString()
                        referenceSurfaceDataset.queryId = region.referenceSurface.queryId
                        referenceSurfaceDataset.sectionId = region.referenceSurface.sectionId
                        referenceSurfaceDataset.questionId = region.referenceSurface.questionId
                        referenceSurfaceDataset.resourceId = region.referenceSurface.resourceId
                        referenceSurfaceDataset.quantity = simulationSession.surfaceDeReference
                        referenceSurfaceDataset.userGivenUnit = "m2"
                        referenceSurfaceDataset.answerIds = ["${simulationSession.surfaceDeReference}"]
                        referenceSurfaceDataset.additionalQuestionAnswers = [:]
                        if (additionalQuestions) {
                            additionalQuestions.forEach({ additionalQuestion ->
                                List<QuestionAnswerChoice> choices = additionalQuestion?.choices
                                String answer = additionalQuestion?.defaultValue ?: choices ? choices.get(0) : ""
                                referenceSurfaceDataset.additionalQuestionAnswers.put(additionalQuestion?.questionId, answer)
                            })
                        }
                        referenceSurfaceDataset.additionalQuestionAnswers.put(region.surfaceCombles?.additionalQuestionId, simulationSession.surfaceDeScombles)
                        referenceSurfaceDataset.additionalQuestionAnswers.put(region.numberOfAccomodation?.additionalQuestionId, simulationSession.nombreDeLogement)

                        if (simulationSession.buildingTypeId && region?.buildingUse) {
                            referenceSurfaceDataset.additionalQuestionAnswers.put(region.buildingUse?.additionalQuestionId, simulationSession.buildingTypeId)
                        }
                    } else if (referenceSurfaceDataset && !referenceSurfaceDataset.locked) {
                        referenceSurfaceDataset.quantity = simulationSession.surfaceDeReference
                        referenceSurfaceDataset.answerIds = ["${simulationSession.surfaceDeReference}"]
                        if (referenceSurfaceDataset.additionalQuestionAnswers) {
                            referenceSurfaceDataset.additionalQuestionAnswers.put((region.surfaceCombles.additionalQuestionId), simulationSession.surfaceDeScombles)
                            referenceSurfaceDataset.additionalQuestionAnswers.put((region.numberOfAccomodation.additionalQuestionId), simulationSession.nombreDeLogement)
                        } else {
                            referenceSurfaceDataset.additionalQuestionAnswers = [(region.surfaceCombles.additionalQuestionId): simulationSession.surfaceDeScombles,
                                                                                 (region.numberOfAccomodation.additionalQuestionId): simulationSession.nombreDeLogement]
                        }
                        if (simulationSession.buildingTypeId && region?.buildingUse) {
                            referenceSurfaceDataset.additionalQuestionAnswers.put(region.buildingUse?.additionalQuestionId, simulationSession.buildingTypeId)
                        }
                    }

                    datasets.add(referenceSurfaceDataset)
                }

                if (region.srefBuilding && simulationSession.surfaceDeReference) {
                    Dataset srefBuildingDataset = carbonDesignerRegionService.getAreaDataset(designEntity, region.srefBuilding)

                    if (!srefBuildingDataset) {
                        srefBuildingDataset = new Dataset()
                        srefBuildingDataset.manualId = new ObjectId().toString()
                        srefBuildingDataset.queryId = region.srefBuilding.queryId
                        srefBuildingDataset.sectionId = region.srefBuilding.sectionId
                        srefBuildingDataset.questionId = region.srefBuilding.questionId
                        srefBuildingDataset.quantity = simulationSession.surfaceDeReference
                        srefBuildingDataset.userGivenUnit = "m2"
                        srefBuildingDataset.answerIds = ["${simulationSession.surfaceDeReference}"]
                        datasets.add(srefBuildingDataset)
                    } else if (srefBuildingDataset && !srefBuildingDataset.locked) {
                        srefBuildingDataset.quantity = simulationSession.surfaceDeReference
                        srefBuildingDataset.answerIds = ["${simulationSession.surfaceDeReference}"]
                        datasets.add(srefBuildingDataset)
                    }
                }
            }
            //Convert from metric to Imperial if is the user unit system
            String unitSystem = simulationSession.unitSystem

            if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value) {
                datasets.each { Dataset d ->
                    String european = d.userGivenUnit
                    String imperial = unitConversionUtil.europeanUnitToImperialUnit(d.userGivenUnit)

                    if (imperial) {
                        d.userGivenUnit = imperial
                    }

                    if (d.quantity) {
                        Double imperialQuantity = unitConversionUtil.doConversion(d.quantity, null, european, null, null, null, null, null, null, imperial)

                        if (imperialQuantity) {
                            d.quantity = imperialQuantity
                            d.answerIds = [imperialQuantity.toString()]
                        }
                    }
                }
            }

            //Round All the quantity != null
            datasets?.each{
                if(it.quantity != null) {
                    it.quantity = simulationToolService.roundToNumber(it.quantity, true)
                    it?.answerIds = [it.quantity.toString() ?: "0"]
                }
            }
            if (indicator.assessmentPeriodValueReference) {
                List<Dataset> assessmentDataset = valueReferenceService.getDatasetsForEntity(indicator.assessmentPeriodValueReference, tempEntity)

                if (assessmentDataset) {
                    log.info("ADDING ASSESSMENT DATASET TO PROJECT: ${assessmentDataset.first().toString()}")
                    datasets.add(assessmentDataset.first())
                }
            }
            List<Dataset> projectLevelDatasets = tempEntity.datasets?.findAll({ it.projectLevelDataset })?.toList()

            if (projectLevelDatasets) {
                datasets.addAll(projectLevelDatasets)
            }

            if (mergeSimulation) {
                List<Dataset> toMergeDatasets = designEntity.datasets?.findAll({ "buildingMaterialsQuery".equals(it.queryId)||it.locked })?.toList()

                if (toMergeDatasets) {
                    datasets.addAll(toMergeDatasets)
                }
            } else {
                List<Dataset> lockedDatasets = designEntity.datasets?.findAll({ it.locked })?.toList()

                if (lockedDatasets) {
                    datasets.addAll(lockedDatasets)
                }
            }
            List<String> uniqueQueryIds = datasets?.findAll({!it.projectLevelDataset})?.collect({ it.queryId })?.unique()
            List<Indicator> indicatorsToSave = carbonDesignService.prepIndicatorsToSave(uniqueQueryIds, parent)
            designEntity = datasetService.preHandleDatasets(designEntity, datasets, indicatorsToSave, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
            designEntity = carbonDesignService.calculateDesignPerIndicator(indicatorsToSave, designEntity, parent, uniqueQueryIds)

            //Set a NULL --> problem when we save on query
            designEntity?.datasets?.each {
                it.originalAnswer = null
            }

            //Resolve scope and disabled sections
            designEntity.disabledSections = entityService.resolveDisabledSections(designEntity)

            designEntity = designEntity?.merge(flush: true)

            //DESTROY Simulation Session
            session?.removeAttribute(sessionId)


            //queryId: "buildingMaterialsQuery" it's hardcoded at the moment
            List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)
            def carbonDesignerOnlyLicensed = parent.getCarbonDesignerOnlyLicensed(validLicenses)

            if (carbonDesignerOnlyLicensed) {
                redirect controller: "entity", action: "show", id: entityId
            } else {
                redirect controller: "query", action: "form",
                        params:[entityId:entityId, indicatorId: indicatorId, childEntityId: designId,
                                queryId: "buildingMaterialsQuery"]
            }
        } else {
            log.warn("Unable to save Carbon Designer: got tempEntity: ${tempEntity ? true : false}, designEntity: ${designEntity ? true : false}, indicator: ${indicator?.indicatorId}, jsonModel: ${jsonModel ? true : jsonModel}, params: ${params}")
            flash.warningAlert = "Impossible save to the query!"
            redirect controller: "entity", action: "show", id: entityId
        }


    }


    def form() {
        Entity design
        Entity entity = entityService.getEntityById(params.entityId, session)

        if (params.id) {
            design = entityService.getEntityById(params.id, session)
        } else {
            design = session?.getAttribute(SessionAttribute.DESIGN.getAttribute())
        }
        Integer currentHighestRiba = entity?.childrenByChildEntities?.findAll({ !it.id.toString().equals(entity.id.toString()) && it.ribaStage != null && !it.deleted })?.collect({ it.ribaStage })?.max()?.intValue()
        Map<Integer, String> ribaStages = Constants.DesignRIBAStages.map()
        ribaStages.put(-1, "entity.form.component")
        def originalEntityId = params.originalEntityId
        Entity originalEntity = entityService.getEntityById(originalEntityId, session)
        List<String> disabledIndicatorsForOriginal = originalEntity?.disabledIndicators
        String entityClass = entity?.entityClass
        Boolean showRibaStage = "building".equalsIgnoreCase(entityClass) || "infrastructure".equalsIgnoreCase(entityClass)
        Map<String, String> designStatuses = Constants.DesignStatus.map()
        List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.DESIGN.toString())

        Boolean hideIndicatorSelect = Boolean.FALSE
        if (indicators?.findAll({!"benchmark".equals(it?.visibilityStatus) && !"visibleBenchmark".equals(it?.visibilityStatus)})?.size() == 0) {
            hideIndicatorSelect = Boolean.TRUE
        }
        [design       : design, originalEntityId: originalEntityId, entity: entity, ifcImport: params.ifcImport, ribaStages: ribaStages,
         showRibaStage: showRibaStage, currentHighestRiba:currentHighestRiba, manageable: entity?.manageable, designStatuses: designStatuses,
         indicators: indicators,hideIndicatorSelect: hideIndicatorSelect, disabledIndicatorsForOriginal: disabledIndicatorsForOriginal]
    }

    def saveAverageDesign() {

        Map<String, String> designStatuses = Constants.DesignStatus.map()
        String entityId = params.parentEntityId
        Entity design
        Entity parent = entityService.getEntityById(entityId)
        String entityClass = parent?.entityClass
        String designStatus = params.designStatus
        params.name = params.name?.replaceAll(Constants.USER_INPUT_REGEX, "")
        List<Entity> childDesigns = entityService.getChildEntities(parent, EntityClass.DESIGN.getType()) as List<Entity>

        if (designStatus && designStatuses.get(designStatus)) {
            params.remove("designStatus")
            params.put(designStatus, true)
        }

        design = new Entity(params)
        design.entityClass = EntityClass.DESIGN.getType()
        // SCOPE VALUES
        ScopeToSave scope = new ScopeToSave(params)
        design?.scope = scope
        design?.disabledSections = entityService.resolveDisabledSections(design)

        if (design && design.name) {
            String sanitizedName = design.name.replaceAll("\"", "")
            design.name = sanitizedName ?: message(code: "entity.new_design")
        }

        String originalEntityId = params.originalEntityId
        Entity originalEntity = entityService.getEntityById(originalEntityId, session)

        if (originalEntity) {
            design.ribaStage = originalEntity.ribaStage
            design.comment = originalEntity.comment
        }

        boolean designAlreadyFound = false

        if (childDesigns?.find({ Entity childEntity -> childEntity.name == design.name || childEntity.id == design.id })) {
            designAlreadyFound = true
        }

        if (!designAlreadyFound) {
            if (design.validate() && (design.ribaStage != null || (design.ribaStage == null
                    && !(EntityClass.BUILDING.equals(entityClass) || EntityClass.INFRASTRUCTURE.equals(entityClass))))) {
                // i think the ribaStage represent the stage of construction process , eg: preparation and brief
                // in the design creation drop down , X represents -1 , which means component only , not whole building
                // refer to localization 'entity.form.component'
                if (design.ribaStage == Constants.RIBA_COMPONENTONLY) {
                    design.ribaStage = null
                    design.component = Boolean.TRUE
                } else {
                    design.component = Boolean.FALSE
                }

                if (design.comment) {
                    design.anonymousDescription = design.comment
                }
                design.resultsOutOfDate = Boolean.TRUE
                design = entityService.createEntity(design)
                Entity designParent = design.parentById
                List<Indicator> selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(designParent.id, IndicatorUse.DESIGN.toString())

                if (originalEntity.disabledIndicators) {
                    selectedDesignIndicators.each { Indicator indicator ->
                        if (Constants.VISIBILITY_STATUS_XBENCHMARK.equalsIgnoreCase(indicator.visibilityStatus) || !design.disabledIndicators?.contains(indicator.indicatorId)) {
                            if (indicator.indicatorId == Constants.X_BENCHMARK_EMBODIED_INDICATORID || !design.calculationTotalResults?.find({ it.indicatorId?.equals(indicator.indicatorId) })) {
                                design = newCalculationServiceProxy.calculate(null, indicator.indicatorId, designParent, design)
                            }
                        }
                    }
                }
                //CHECK IF THE BELOW IS REQUIRED
                //newCalculationServiceProxy.copyCalculationResults(originalEntity.id, design.id)
                copyDatasetsAndFilesFromDesigns(design, originalEntity, params, childDesigns)
                entityService.updateEntity(design)
                String copyDesignId = design.id.toString()

                if (copyDesignId && originalEntityId && copyDesignId != originalEntityId) {
                    simulationToolService.copySimulationToolFromDesignEntity(entityId, originalEntityId, copyDesignId)
                }
                flash.fadeSuccessAlert = g.message(code: "average.design.created")


                session?.removeAttribute(SessionAttribute.DESIGN.getAttribute())

                if (params.ifcImport) {
                    redirect controller: "importMapper", action: "chooseEntity"
                } else {
                    redirect(controller: "entity", action: "show", params: [entityId: entityId])
                }
            } else {
                if (design.ribaStage == null) {
                    flash.fadeErrorAlert = "RIBA Stage cannot be empty."
                } else {
                    flash.fadeErrorAlert = renderErrors(bean: design)
                }
                redirect(controller: "entity", action: "show", params: [entityId: entityId])
            }

        } else {
            flash.fadeErrorAlert = message(code: "design.duplicate")

            if (params.ifcImport) {
                redirect controller: "importMapper", action: "chooseEntity"
            } else {
                session?.setAttribute(SessionAttribute.DESIGN.getAttribute(), design)
                redirect(controller: "entity", action: "show", params: [entityId: entityId])
            }
        }
    }
    /**
     *
     * @param entity
     * @param targetDesign
     * @param baseEntity
     * @param params
     *
     * Copies all the files and datasets from all the design to newly created average design
     */
    void copyDatasetsAndFilesFromDesigns(Entity targetDesign, Entity baseEntity, GrailsParameterMap params, List<Entity> designs) {
        Map<String, Double> entityShareMap = [:]
        if (designs && targetDesign) {
            designs.each({ Entity designWithShare ->
                if (params?.containsKey(designWithShare.id?.toString())) {
                    entityShareMap.put(designWithShare.id.toString(), DomainObjectUtil.convertStringToDouble(params.get(designWithShare.id?.toString())))
                }
            })

            designs.each { Entity it ->
                targetDesign = datasetService.copyDatasetsAndFilesForAverageDesign(it, targetDesign, baseEntity, entityShareMap)
            }

            datasetService.removeOtherDatasets(targetDesign)
            Set<Dataset> requiredDatasets = datasetService.findRequiredDatasets(baseEntity)
            targetDesign.datasets ? targetDesign.datasets.addAll(requiredDatasets) : (targetDesign.datasets = requiredDatasets)
        }
    }

    def save() {
        Map<String, String> designStatuses = Constants.DesignStatus.map()
        def entityId = params.parentEntityId
        Entity design
        Entity parent = entityService.getEntityById(entityId, session)
        String entityClass = parent?.entityClass
        String designStatus = params.designStatus
        params.name = params.name?.replaceAll(Constants.USER_INPUT_REGEX,"")

        if (designStatus && designStatuses.get(designStatus)) {
            params.remove("designStatus")
            params.put(designStatus, true)
        }

        Boolean nameChanged = Boolean.FALSE

        if (params.id) {
            design = entityService.getEntityById(params.id, session)

            String newName = params.name
            String newRiba = params.ribaStage?.toString()

            if (design) {
                String name = design.name
                String ribaStage = design.ribaStage?.toString()

                if (name && newName && !name.equals(newName.trim())) {
                    nameChanged = Boolean.TRUE
                }

                if ((!ribaStage && newRiba)||(ribaStage && !newRiba)||(ribaStage && newRiba && !ribaStage.equals(newRiba))) {
                    nameChanged = Boolean.TRUE
                }
            }
            design.properties = params
        } else {
            design = new Entity(params)
            design.entityClass = EntityClass.DESIGN.getType()
        }

        /* Ticket 16300 - For whatever reason it is, ribaStage fails to bind to design with mismatched type on
        *   data binding above, but only on certain user selected languages, so below I am clearing the
        *   errors and manually setting it to pass validation later.
        *   Fails on:
        *   - Finnish
        *   - Norwegian
        *   - Swedish
        *
        *   Works on:
        *   - English
        *   - French
        *   - */
        if (params.ribaStage && !design.ribaStage) {
            design.clearErrors()
            design.ribaStage = params.int("ribaStage")
        }

        // SCOPE VALUES
        def scope = new ScopeToSave(params)
        design?.scope = scope
        design?.disabledSections = entityService.resolveDisabledSections(design)


        if (design && design.name) {
            String sanitizedName = design.name.replaceAll("\"", "")
            design.name = sanitizedName ?: message(code: "entity.new_design")
        }

        if (params.indicatorIdList) {
            List<String> chosenIndicatorIds = params.list("indicatorIdList")
            List<String> disabledIndicatorIds = parent.getIndicatorIds()

            if (chosenIndicatorIds) {
                disabledIndicatorIds.removeAll(chosenIndicatorIds)
                design.disabledIndicators = disabledIndicatorIds
                design.disabledIndicators?.each { String indicatorId ->
                    newCalculationServiceProxy.removeExistingResultsByIndicator(design, indicatorId)
                }

                if (params.id) {
                    Entity designParent = design.parentById
                    chosenIndicatorIds.each { String indicatorId ->
                        if (indicatorId == "xBenchmarkEmbodied" || !design.calculationTotalResults?.find({ it.indicatorId?.equals(indicatorId) })) {
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

                            if (indicator) {
                                design = newCalculationServiceProxy.calculate(null, indicator.indicatorId, designParent, design)
                            }
                        }
                    }
                }
            }
        }

        def originalEntityId = params.originalEntityId

        if (!entityService.designAlreadyFound(design)) {
            if (design.validate() && (design.ribaStage != null || (design.ribaStage == null && !("building".equalsIgnoreCase(entityClass) || "infrastructure".equalsIgnoreCase(entityClass))))) {
                if (design.ribaStage == -1) {
                    design.ribaStage = null
                    design.component = Boolean.TRUE
                } else {
                    design.component = Boolean.FALSE
                }

                if (design.comment) {
                    design.anonymousDescription = design.comment
                }
                if (design.id) {
                    entityService.updateEntity(design, nameChanged)
                    flash.fadeSuccessAlert = g.message(code: "design.updated")
                } else {
                    design = entityService.createEntity(design)

                    if (originalEntityId) {
                        Entity designParent = design.parentById
                        List<Indicator> selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(designParent.id, IndicatorUse.DESIGN.toString())
                        def originalEntity = entityService.getEntityById(originalEntityId, session)

                        List<Indicator> indicatorsToRecalculate = []

                        if (originalEntity.disabledIndicators) {
                            selectedDesignIndicators.each { Indicator i ->
                                if ("benchmark".equalsIgnoreCase(i.visibilityStatus)||!design.disabledIndicators?.contains(i.indicatorId)) {
                                    indicatorsToRecalculate.add(i)
                                }
                            }
                        }

                        design = datasetService.copyDatasetsAndFiles(originalEntity, design)
                        newCalculationServiceProxy.copyCalculationResults(originalEntity.id, design.id)
                        
                        indicatorsToRecalculate.each { Indicator indicator ->
                            if (indicator.indicatorId == "xBenchmarkEmbodied" || !design.calculationTotalResults?.find({ it.indicatorId?.equals(indicator.indicatorId) })) {
                                design = newCalculationServiceProxy.calculate(null, indicator.indicatorId, designParent, design)
                            }
                        }
                        entityService.updateEntity(design)
                        String copyDesignId = design.id.toString()
                        if(copyDesignId && originalEntityId && copyDesignId != originalEntityId){
                            simulationToolService.copySimulationToolFromDesignEntity(entityId, originalEntityId, copyDesignId)
                        }
                        flash.fadeSuccessAlert = g.message(code: "design.copied")
                    } else {
                        flash.fadeSuccessAlert = g.message(code: "design.created")
                    }
                }
                session?.removeAttribute(SessionAttribute.DESIGN.getAttribute())

                if (params.ifcImport) {
                    redirect controller: "importMapper", action: "chooseEntity"
                } else {
                    redirect(controller: "entity", action: "show", params: [entityId: entityId])
                }
            } else {
                if (design.ribaStage == null) {
                    flash.fadeErrorAlert = "RIBA Stage cannot be empty."
                } else {
                    flash.fadeErrorAlert = renderErrors(bean: design)
                }
                //redirect(action: "form", params: [originalEntityId: originalEntityId, entityId: entityId, id: params.id])
                redirect(controller: "entity", action: "show", params: [entityId: entityId])
            }

        } else {
            flash.fadeErrorAlert = message(code: "design.duplicate")

            if (params.ifcImport) {
                redirect controller: "importMapper", action: "chooseEntity"
            } else {
                session?.setAttribute(SessionAttribute.DESIGN.getAttribute(), design)
                //redirect(action: "form", params: [originalEntityId: originalEntityId, entityId: entityId])
                redirect(controller: "entity", action: "show", params: [entityId: entityId])
            }
        }
    }

    def cancel() {
        redirect(controller: "entity", action: "show", params: [entityId: params.entityId])
    }

    def remove() {
        try {
            entityService.delete(params.id)
            flash.fadeSuccessAlert = g.message(code: "design.deleted")
        } catch (Exception e) {
            flash.fadeErrorAlert = "Error in deleting design"
        }
        redirect(controller: "entity", action: "show", params: [entityId: params.entityId])
    }

    def commit() {
        def designId = params.id
        entityService.commitEntity(designId)
        flash.fadeSuccessAlert = g.message(code: "design.commited")
        redirect(controller: "entity", action: "show", id: params.entityId)
    }

    def results() {
        long now = System.currentTimeMillis()
        log.info("Started handling results page attributes")
        Entity entity

        if (params.id) {
            entity = entityService.getEntityByIdReadOnly(params.id)
        } else {
            entity = entityService.getEntityByIdReadOnly(params.entityId)
        }
        String indicatorId = params.indicatorId
        Entity design = entityService.getEntityByIdReadOnly(params.childEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        //sw-1597
        List<Document> nmdElementList
        if(indicatorId.contains(Constants.NMD)){
            nmdElementList = nmdElementService.getNmdElementsAsDocument()
        }

        if (entity && design && indicator) {
            if(Boolean.TRUE.equals(design.resultsOutOfDate)) {
                log.debug("Recalculating outdated results of ${design.id} for indicator ${indicatorId}")
                design = newCalculationServiceProxy.calculate(design.id, indicatorId, entity, design)
            }

            Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(design.id.toString(), indicatorId)

            if(isCalculationRunning){
                flash.permWarningAlert = g.message(code: "entity.calculation.running.result")
                return render(view: "/entity/results", model: [
                        isCalculationRunning: isCalculationRunning,
                        design              : design,
                        indicator           : indicator,
                        nmdElementList      : nmdElementList
                ])
            }

            //indicator.getApplicationRules()

            List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
            List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
            boolean isComponentEntity = entityService.isComponentEntity(entity, featuresAvailableForEntity)
            def queries = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity)

            def additionalInfos = getAdditionalInfos(design, indicator, queries)
            def formattingFeatures = indicatorService.resolveFormattingFeatures(indicator, session)

            def isPortfolio = params.isPortfolio ? true : null

            List<License> nonProdLicenses = entityService.getNonProdLicenses(entity, indicator)

            String basicQueryTable = datasetService.getBasicQueryAnswersForReport(entity)?.collect { String questionName, def userAnswer ->
                '<tr><th>'+ questionName?.encodeAsHTML() +'</th><td>' + userAnswer?.encodeAsHTML() + '</td></tr>'
            }?.join()


            def calculationError = session?.getAttribute(SessionAttribute.CALCULATION_ERROR.toString())

            if (calculationError) {
                flash.errorAlert = calculationError
                session.removeAttribute(SessionAttribute.CALCULATION_ERROR.toString())
            }
            String errorMessage = errorMessageUtil.getErrorMessageFromSession()
            def modifiable = (!entity?.readonly && entity?.getLicenseForIndicator(indicator) && !design?.locked && !design?.superLocked)
            if (errorMessage && modifiable) {
                if (flash?.containsKey("errorAlert")) {
                    flash.errorAlert = flash.get("errorAlert") + "<br />" + errorMessage
                } else {
                    flash.errorAlert = errorMessage
                }
            }
            if(modifiable){
                flash.fadeErrorAlert = errorMessageUtil.getErrorMessageFromSession()
            }

            List<Query> designQueries = queryService.getQueriesByIndicatorAndEntity(indicator, design, featuresAvailableForEntity)
            String notInFilterMessage = modifiable ? entityService.prepareNotInFilterMessage(design, indicator, designQueries*.queryId) : null

            List<Query> allowedQueries = indicator?.sortQueriesByIndicatorQuery(queries)
            allowedQueries = indicatorService.filterQueriesByProjectLevelIndicatorQueries(indicator, allowedQueries, false)

            String channelToken = channelFeatureService.getChannelFeature(session)?.token
            User user = userService.getCurrentUser()

            List<Feature> validFeatures = validLicenses?.collect({it.licensedFeatures})?.flatten()?.unique({it?.featureId})
            List<String> featuresToCheck = [Feature.BETA_FEATURES, Feature.CHAT_SUPPORT, Feature.ANNUAL_CHART,
                                            Feature.WORD_REPORT_GENERATION, Feature.INDICATOR_BENCHMARKS, Feature.INDICATOR_BENCHMARKS_LOCKED,
                                            Feature.PLANETARY_REPORT, Feature.EPD_ILCD_EXPORT, Feature.COMPARE_MATERIAL, Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS]

            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, featuresToCheck, user)
            Boolean annualChartLicensed = featuresAllowed.get(Feature.ANNUAL_CHART)
            Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
            Boolean chatSupportLicensed = featuresAllowed.get(Feature.CHAT_SUPPORT)
            Boolean wordReportGenerationLicensed = featuresAllowed.get(Feature.WORD_REPORT_GENERATION)
            Boolean indicatorBenchmarkLicensed = featuresAllowed.get(Feature.INDICATOR_BENCHMARKS) || featuresAllowed.get(Feature.INDICATOR_BENCHMARKS_LOCKED)
            Boolean sankeyAndOtherLicensed = entity.getAllowedFeatureByProjectLicense([Feature.SANKEY_AND_TREEMAP])
            Boolean planetaryReportLicensed = featuresAllowed.get(Feature.PLANETARY_REPORT)
            Boolean epdIlcdExportLicensed = featuresAllowed.get(Feature.EPD_ILCD_EXPORT)
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
            Map<String,Boolean> chartsToRender = ["bubble":bubbleChartRender,"overview":overviewChartRender,"sankey":sankeyAndOtherChartRender,"annual":annualChartRender,"radial":radialByElementByDesign,"lifeCycle":lifeCycleStageChartRender,
                                                  "stackedStage":barChartByImpactCategoryStackedByStage,"stackedMat":barChartByImpactCategoryStackedByMaterial,"treemap":treemapChartRender,"circular":circularChartRender,"breakdown":lifeCycleChartBreakdownRender]
            String indicatorLicenseName = ""
            if (indicator.licensedNamequalifiers && validFeatures) {

                for (String featureId in Feature.DYNAMIC_FEATUREIDS) {
                    if (validFeatures?.find({featureId.equals(it?.featureId)})) {
                        indicatorLicenseName = indicatorService.getLocalizedLicensedNameQualifier(indicator, featureId)
                        break
                    }
                }
            }


            String arrayOfRulesWordGeneration
            Map<String,String> templatesAndCanvasIdsForReportGenerationMap = [:]
            List<CalculationRule> graphCalculationRules = indicator?.getGraphCalculationRuleObjects(entity)

            if (wordReportGenerationLicensed && graphCalculationRules && indicator?.wordReportGeneration) {
                def reportGenerationRuleFromApplication = indicator.reportGenerationRuleFromApplication
                if (reportGenerationRuleFromApplication) {
                    arrayOfRulesWordGeneration = graphCalculationRules.collect { it.calculationRuleId }?.join(',')
                    Map<String, List<ReportGenerationRuleContent>> contentInjectionSets = indicator.getContentInjectionSetsFromApplication()

                    reportGenerationRuleFromApplication.each { ReportGenerationRule reportGenerationRule ->
                        String canvasIdsForReportGenerationTxt = reportGenerationRule.getAllChartUiIdentifications(contentInjectionSets)?.join(",")
                        templatesAndCanvasIdsForReportGenerationMap.put(reportGenerationRule.reportGenerationRuleId, canvasIdsForReportGenerationTxt)
                    }
                }

            }
            List<HelpConfiguration> helpConfigurations = helpConfigurationService.getHelpConfigurationByTargetPageAndIndicatorId("Results", indicator?.indicatorId, Boolean.TRUE) ?:
                    helpConfigurationService.getConfigurationsForTargetPage("Results") ?: []
            Double secondsTaken = (System.currentTimeMillis() - now) / 1000
            Boolean showRenderingTimes = userService.getShowDevDebugNotifications(user?.enableDevDebugNotifications)


            //***************************************************************************
            //WorkFlow
            //***************************************************************************
            WorkFlowForEntity workFlowForEntity
            String defaultIndicatorId = indicator?.indicatorId

            if(entity && entityService.isWorkFlowLicensed(featuresAvailableForEntity) && defaultIndicatorId){
                workFlowForEntity = workFlowService.loadWorkFlowForEntity(entity, defaultIndicatorId)
            }
            //***************************************************************************

            //***************************************************************************
            //FRAME STATUS
            //***************************************************************************
            ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

            FrameStatus frameStatus = workFlowService.loadFrame(user, helpConfigurations, channelFeature, workFlowForEntity)
            //***************************************************************************

            Boolean lcaCheckerAllowed = entity?.lcaCheckerAllowed && indicator?.enableLcaChecker

            Boolean carbonDesignerOnlyLicensed = entity.getCarbonDesignerOnlyLicensed(validLicenses)
            boolean carbonDesignerAndResultsOnlyLicensed = entityService.getCarbonDesignerAndResultsOnlyLicensed(validLicenses)
            Boolean isCompareDataCompatibleAndLicensed = featuresAllowed.get(Feature.COMPARE_MATERIAL) && indicatorService.addToCompareAllowedForIndicators([indicator])

            List<String> classificationListForIndicator = indicator.classificationsMap
            Query additionalQuestionQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            Map<String,Question> additionalQuestions = classificationListForIndicator?.collectEntries {[(it):questionService.getQuestion(additionalQuestionQuery,it)]}
            String defaultClassQuesId = classificationListForIndicator?.size() > 1 ? classificationListForIndicator?.get(0) : ""
            Question groupingQuestion = additionalQuestions?.get(defaultClassQuesId)
            String defaultFallbackClassification = classificationListForIndicator?.size() == 1 && classificationListForIndicator?.contains(Constants.ORGANIZATION_CLASSIFICATION_ID) ? message(code: "default_class") : null
            // for showing verification points feature
            boolean isIndicatorHavingVerificationPoints = showAndJumpToVPointLicensed ? verificationPointService.isIndicatorHavingVerificationPoints(indicator, entity) : false

            if (carbonDesignerOnlyLicensed && !carbonDesignerAndResultsOnlyLicensed) {
                flash.warningAlert = "Rendering results not permitted by this license"
                redirect controller: "entity", action: "show", id: entity.id.toString()
            } else {
                render view: "/entity/results", model: [entity                                     : entity,
                                                        modifiable                                 : modifiable,
                                                        childEntity                                : design,
                                                        indicator                                  : indicator,
                                                        queries                                    : queries,
                                                        designQueries                              : designQueries,
                                                        defaultFallbackClassification              : defaultFallbackClassification,
                                                        groupingQuestion                           : groupingQuestion,
                                                        additionalInfos                            : additionalInfos,
                                                        isPortfolio                                : isPortfolio,
                                                        formattingFeatures                         : formattingFeatures,
                                                        licenses                                   : validLicenses,
                                                        additionalQuestions                        : additionalQuestions,
                                                        showAndJumpToVPointLicensed                : showAndJumpToVPointLicensed,
                                                        type                                       : "design",
                                                        nonProdLicenses                            : nonProdLicenses,
                                                        showPoweredBy                              : true,
                                                        chatSupportLicensed                        : chatSupportLicensed,
                                                        isIndicatorHavingVerificationPoints        : isIndicatorHavingVerificationPoints,
                                                        secondsTaken                               : secondsTaken,
                                                        showRenderingTimes                         : showRenderingTimes,
                                                        betaFeaturesLicensed                       : betaFeaturesLicensed,
                                                        wordReportGenerationLicensed               : wordReportGenerationLicensed,
                                                        annualChartLicensed                        : annualChartLicensed,
                                                        notInFilterMessage                         : notInFilterMessage,
                                                        user                                       : user,
                                                        channelToken                               : channelToken,
                                                        allowedQueries                             : allowedQueries,
                                                        graphCalculationRules                      : graphCalculationRules,
                                                        arrayOfRulesWordGeneration                 : arrayOfRulesWordGeneration,
                                                        templatesAndCanvasIdsForReportGenerationMap: templatesAndCanvasIdsForReportGenerationMap,
                                                        chartsToRender                             : chartsToRender,
                                                        helpConfigurations                         : helpConfigurations,
                                                        workFlowForEntity                          : workFlowForEntity,
                                                        frameStatus                                : frameStatus,
                                                        indicatorBenchmarkLicensed                 : indicatorBenchmarkLicensed,
                                                        sankeyAndOtherLicensed                     : sankeyAndOtherLicensed,
                                                        planetaryReportLicensed                    : planetaryReportLicensed,
                                                        lcaCheckerAllowed                          : lcaCheckerAllowed,
                                                        basicQueryTable                            : basicQueryTable,
                                                        indicatorLicenseName                       : indicatorLicenseName,
                                                        epdIlcdExportLicensed                      : epdIlcdExportLicensed,
                                                        isCompareDataCompatibleAndLicensed         : isCompareDataCompatibleAndLicensed,
                                                        carbonDesignerAndResultsOnlyLicensed       : carbonDesignerAndResultsOnlyLicensed,
                                                        isComponentEntity                          : isComponentEntity,
                                                        isCalculationRunning                       : isCalculationRunning,
                                                        nmdElementList                             : nmdElementList
                ]
            }
        } else {
            redirect controller: "main"
        }
    }

    def loadReportItem() {

        String output = ""

        String reportItemId = params.reportItemId
        Entity entity = entityService.getEntityByIdReadOnly(params.entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)

        if (indicator && entity && reportItemId) {

            Entity parentEntity = entityService.getEntityByIdReadOnly(params.parentEntityId)
            IndicatorReportItem reportItem = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator.report?.reportItems)?.find({
                reportItemId.equals(it.reportItemId)
            })

            if (reportItem) {

                Map params = [
                        indicator         : indicator,
                        entity            : entity,
                        reportItem        : reportItem,
                        hideBreakdown     : params.boolean("hideBreakdown"),
                        parentEntity      : parentEntity,
                        collapseThis      : params.boolean("collapseThis"),
                        hideEmpty         : params.boolean("hideEmpty"),
                        hideLinkAndExplain: params.boolean("hideLinkAndExplain")
                ]

                output = calculationResultTagLib.renderDefaultTableContent(params)
            }
        }
        render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private CalculationResultTagLib getCalculationResultTagLib() {
        CalculationResultTagLib calculationResultTagLib = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.CalculationResultTagLib')
        return calculationResultTagLib
    }

    private String generateGraphLabels(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules, List<CalculationResult> calculationResultList) {
        String labels = "["
        int index = 0
        if (resultCategories && calculationRules && calculationResultList) {
            resultCategories?.each { ResultCategory category ->
                boolean hasResult = false

                for (CalculationRule calculationRule in calculationRules) {
                    def result = calculationResultList?.find({
                        category.resultCategoryId.equals(it.resultCategoryId) && calculationRule.calculationRuleId.equals(it.calculationRuleId)
                    })?.result

                    if (result != null && result != 0) {
                        hasResult = true
                        break

                    }
                }
                if (hasResult) {
                    String localizedShortName = resultCategoryService.getLocalizedShortName(category)
                    labels = "${labels}${index > 0 ? ', ' : ''}\"${abbr(value: category.resultCategory + " " + localizedShortName, maxLength: 50)}\""
                    index++
                }
            }
        }
        labels = "${labels}]"
        return labels
    }

    private String generateGraphDatasets(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules, List<CalculationResult> resultsByIndicatorId, Entity child, Indicator indicator) {
        List<String> strokeAndPointColors = Constants.CHART_COLORS
        String datasets = "["
        List<String> categoriesWithScores = []
        Map<CalculationRule, Map<String, Double>> scoresForCalculationRule= [:]

        if (resultCategories && calculationRules && child && indicator) {


            calculationRules?.each { CalculationRule calculationRule ->

                Map<String, Double> scores = new LinkedHashMap<String, Double>()
                Double totalScore = (Double) child.getTotalResult(indicator?.indicatorId, calculationRule.calculationRuleId, Boolean.FALSE)

                resultCategories?.each { ResultCategory resultCategory ->
                    Double score = child.getResult(indicator.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsByIndicatorId)


                    if (totalScore && totalScore != 0) {
                        if(score) {
                            scores.put(resultCategory.resultCategoryId, (score / totalScore * 100))
                            if(!categoriesWithScores.find{it == resultCategory.resultCategoryId}){
                                categoriesWithScores.add(resultCategory.resultCategoryId)
                            }
                        }else{
                            score = 0
                            scores.put(resultCategory.resultCategoryId, (score / totalScore * 100))
                        }

                    }
                }

                if (scores && !scores.isEmpty()) {
                    scoresForCalculationRule.put(calculationRule, scores)
                }

            }
            int colorIndex = 0
            scoresForCalculationRule.each{key,value ->
                int scoreIndex = 0
                if (colorIndex + 1 > strokeAndPointColors.size()) {
                    colorIndex = 0
                }
                String escapedForFrench = key.localizedName.replaceAll("\'", "\\’")
                datasets = "${datasets}{label: \"${escapedForFrench}\","
                datasets = "${datasets}  backgroundColor: \"${strokeAndPointColors.get(colorIndex)}\",\n" +
                        "                borderColor: \"${strokeAndPointColors.get(colorIndex)}\",\n"
                datasets = "${datasets}data: ["
                categoriesWithScores.each{ String resultCategory ->

                    if (value?.find{it.key == resultCategory}) {
                        datasets = "${datasets}${scoreIndex > 0 ? ', ' : ''}${value?.get(resultCategory)}"

                        scoreIndex++
                    }


                }
                datasets = "${datasets}]},"
                colorIndex++
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

    // Deprecate code, to be removed by the end of feature 0.5.0 release
    /*def graphs() {
        Entity entity = entityService.readEntity(params.entityId)
        List<Entity> designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())
        def indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.DESIGN.toString())?.
                findAll({ Indicator indicator -> !indicator.nonNumericResult })
        def scores = [:]
        def normalizedScores = [:]
        def validScores = [:]
        def showSummary = false
        def validScoresByIndicator = [:]
        def nonProdLicenses = []

        indicators?.each { Indicator indicator ->
            CalculationRule rule = indicator?.getResolveCalculationRules(entity)?.find({ it.calculationRule })
            ResultCategory category = indicator?.getResolveResultCategories(entity)?.find({ it.resultCategory })
            def designScores = [:]

            designs?.each { design ->
                def designScore = design?.getResult(indicator?.indicatorId, rule?.calculationRuleId, category?.resultCategoryId)

                if (designScore) {
                    def score = designScore.toString()
                    designScores.put((design.id), score && score.isNumber() ? score : "0")

                    if (score?.isNumber()) {
                        validScoresByIndicator.put(indicator, true)

                        if (validScores.get(design) == null) {
                            validScores.put(design, true)
                        }
                    }
                } else {
                    validScores.put(design, false)
                    designScores.put((design.id), message(code: "graphs.indicator.incomplete"))
                }
            }
            scores.put(indicator.indicatorId, designScores)
            def maxScore = 0
            def maxScoreKey

            designs?.each { design ->
                def score = scores.get(indicator.indicatorId)?.get(design.id)

                if (score && score.isNumber() && score.toDouble() >= maxScore) {
                    maxScore = score.toDouble()
                    maxScoreKey = design.id
                }
            }
            designScores = [:]

            designs?.each { design ->
                if (maxScoreKey == design.id) {
                    designScores.put((design.id), "100")
                } else {
                    def score = scores.get(indicator.indicatorId)?.get(design.id)

                    if (score && score.isNumber()) {
                        score = score.toDouble()
                        score = score / maxScore * 100
                        designScores.put((design.id), "" + score.toInteger())
                    }
                }
            }
            normalizedScores.put(indicator.indicatorId, designScores)
            def licenses = entity?.getLicensesForIndicator(indicator)

            String prodType = Constants.LicenseType.PRODUCTION.toString()
            String demoType = Constants.LicenseType.DEMO.toString()
            nonProdLicenses = licenses?.findAll{ it.type != prodType && it.type != demoType} ?: []
        }

        if (validScores?.values()?.contains(true)) {
            showSummary = true
        }
        render view: "/entity/graphs", model: [entities              : designs, indicators: indicators, scores: scores,
                                               normalizedScores      : normalizedScores, entity: entity, showSummary: showSummary,
                                               validScoresByIndicator: validScoresByIndicator, showPoweredBy: true, nonProdLicenses: nonProdLicenses]
    }*/

    def graphsContainer() {
        Entity entity = entityService.getEntityById(params.entityId, session)
        List<Entity> designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        def absoluteScores = [:]
        def normalizedScores = [:]

        CalculationRule rule = indicator?.getResolveCalculationRules(entity)?.find({ it.calculationRule })
        ResultCategory category = indicator?.getResolveResultCategories(entity)?.find({ it.resultCategory })

        designs?.each { design ->
            def score = design?.getResult(indicator?.indicatorId, rule?.calculationRuleId, category?.resultCategoryId)
            absoluteScores.put(design.operatingPeriodAndName, score && score.isNumber() ? score : "0")

        }
        def maxScore = 0
        def maxScoreKey

        designs?.each { design ->
            def score = absoluteScores.get(design.operatingPeriodAndName)

            if (score && score.isNumber() && score.toDouble() >= maxScore) {
                maxScore = score.toDouble()
                maxScoreKey = design.operatingPeriodAndName
            }
        }

        designs?.each { design ->
            if (maxScoreKey == design.operatingPeriodAndName) {
                normalizedScores.put(design.operatingPeriodAndName, "100")
            } else {
                def score = absoluteScores.get(design.operatingPeriodAndName)

                if (score && score.isNumber()) {
                    score = score.toDouble()
                    score = score / maxScore * 100
                    normalizedScores.put(design.operatingPeriodAndName, "" + score.toInteger())
                }
            }
        }
        render view: "/entity/graphsContainer", model: [absoluteScores: absoluteScores as JSON, normalizedScores: normalizedScores as JSON]
    }

    def lock() {
        User user = userService.getCurrentUser()
        String entityId = params.entityId
        String designId = params.designId
        Boolean persistDefaults = params.boolean("persistDefaults")
        Boolean superUserLock = Boolean.FALSE

        if (params.boolean('superUser') && (userService.getSuperUser(user) || userService.getConsultant(user))) {
            superUserLock = Boolean.TRUE
        }
        entityService.lockEntity(designId, superUserLock, persistDefaults)
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    def unlock() {
        User user = userService.getCurrentUser()
        String entityId = params.entityId
        String designId = params.designId
        Boolean superUserUnlock = Boolean.FALSE

        if (params.superUser && (userService.getSuperUser(user) || userService.getConsultant(user))) {
            superUserUnlock = Boolean.TRUE
            entityService.unverifyEntity(designId) // also unverify "superuser verified"
        }
        entityService.unlockEntity(designId, superUserUnlock)
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    def verify() {
        String entityId = params.entityId
        String designId = params.designId
        User user = userService.getCurrentUser()
        if (userService.getSuperUser(user)) {
            entityService.verifyEntity(designId)
        } else {
            String userName = user?.name
            log.error("Non superuser tried to verify a design: ${userName}")
            flashService.setErrorAlert("Non superuser tried to verify a design: ${userName}", true)
        }
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    def unverify() {
        String entityId = params.entityId
        String designId = params.designId
        User user = userService.getCurrentUser()
        if (userService.getSuperUser(user)) {
            entityService.unverifyEntity(designId)
        } else {
            String userName = user?.name
            log.error("Non superuser tried to unverify a design: ${userName}")
            flashService.setErrorAlert("Non superuser tried to verify a design: ${userName}", true)
        }
        redirect(controller: "entity", action: "show", params: [entityId: entityId])
    }

    def getLCAQueryDatasetsForShowing() {
        User user = userService.getCurrentUser(true)
        String output = ""
        String childEntityId = params.entityId
        String portfolioId = params.portfolioId
        Entity child = entityService.readEntity(childEntityId)
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId)

        if (child && portfolio) {
            Entity parent = child.parentById
            List<String> queries = ['basicQuery', Constants.LCA_PARAMETERS_QUERYID, 'surfaceDefinitionsShared']

            if (queries && parent) {
                def userIds = parent.userIds
                output = "${output}<table class=\"table-striped\">"

                if (child.anonymousDescription) {
                    output = "${output}<tr><td colspan=\"2\"><strong>${child.anonymousDescription}</strong></td></tr>"
                }
                if(child.ribaStage >= 0) {
                    output = "${output}<tr><td><strong>${message(code: 'design.riba_stage')}:</strong></td><td>${child.ribaStage} - ${message(code: com.bionova.optimi.core.Constants.DesignRIBAStages.map().get(child.ribaStage))}</td>"
                } else {
                    output = "${output}<tr><td><strong>${message(code: 'design.riba_stage')}:</strong></td><td> N/A </td>"
                }

                queries.each { String queryId ->
                    List<Dataset> datasets

                    if ("basicQuery".equals(queryId) || "surfaceDefinitionsShared".equals(queryId)) {
                        if (userIds && user && ((userIds.contains(user.id) || userIds.contains(user.id.toString())) || user.internalUseRoles)) {
                            if ("basicQuery".equals(queryId)) {
                                datasets = parent.datasets?.toList()?.findAll({ queryId.equals(it.queryId) })
                            } else if ("surfaceDefinitionsShared".equals(queryId)) {
                                datasets = child.datasets?.toList()?.findAll({ queryId.equals(it.queryId) })
                            }
                        }
                    } else {
                        datasets = child.datasets?.toList()?.findAll({ queryId.equals(it.queryId) })
                    }

                    if (datasets) {
                        Query query = queryService.getQueryByQueryId(queryId, true)

                        query?.sections?.each { QuerySection querySection ->
                            if (!querySection.hideInPortfolioIfAnonymized || (querySection.hideInPortfolioIfAnonymized && !portfolio.anonymous) || (querySection.hideInPortfolioIfAnonymized && portfolio.anonymous && (userIds && user && ((userIds.contains(user.id) || userIds.contains(user.id.toString())) || user.internalUseRoles)))) {
                                List<Dataset> sectionDatasets = datasets.findAll({ querySection.sectionId.equals(it.sectionId) })

                                if (sectionDatasets) {
                                    querySection.questions?.findAll{it.questionId !="projectType" && it.questionId !="frameType"}?.each { Question question ->
                                        List<Dataset> questionDatasets = sectionDatasets.findAll({
                                            question.questionId.equals(it.questionId)
                                        })

                                        if (questionDatasets) {
                                            Integer i = 0
                                            ResourceCache resourceCache = ResourceCache.init(questionDatasets)
                                            for (Dataset dataset: questionDatasets) {
                                                if (dataset.answerIds) {
                                                    dataset.answerIds.each { answer ->
                                                        output = "${output}<tr><td>${i > 0 ? "" : "<strong>${question.localizedQuestion}:</strong>"}</td>"
                                                        output = "${output}<td>${answer ? question.choices?.find({ it.answerId?.equals(answer) })?.localizedAnswer ?: optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(answer, null, null)) ?: dataset.resourceId ? "${optimiResourceService.getLocalizedName(resourceCache.getResource(dataset))}: ${answer} ${dataset.userGivenUnit ?: ""}" : "${answer}" : ''}</td></tr>"
                                                        i++
                                                    }
                                                }
                                                i++
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if ("basicQuery".equals(query?.queryId) && child.lastUpdated) {
                            Calendar calendar = Calendar.getInstance()
                            calendar.setTime(child.lastUpdated)
                            output = "${output}<tr><td><strong>${message(code: 'year_of_assessment')}:</strong></td><td>${calendar.get(Calendar.YEAR)}</td>"
                        }
                    }
                }
                //ADD Scope information to the table
                String scopeRows = scopeFactoryService.getServiceImpl(child?.entityClass).getScopeRows(child?.scope)
                output = scopeRows ? output + scopeRows : output

                output = "${output}</table>"
            }
        }
        render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def disableLcaCheckerCheckForDesign() {
        String disabled = "false"
        String designId = params.designId
        String lcaCheckId = params.lcaCheckId
        String indicatorId = params.indicatorId
        Entity childEntity = entityService.readEntity(designId)

        if (childEntity && lcaCheckId && indicatorId) {
            if (childEntity.suppressedLcaCheckers) {
                childEntity.suppressedLcaCheckers.add(lcaCheckId)
            } else {
                childEntity.suppressedLcaCheckers = [lcaCheckId]
            }
            try {
                childEntity = childEntity.merge(flush: true)
                disabled = "true"
            } catch (OptimisticLockingException e) {
                loggerUtil.error(log, "LCA Checker: unable to disable check ${lcaCheckId} for design ${childEntity.name}", e)
                flashService.setErrorAlert("LCA Checker: unable to disable check ${lcaCheckId} for design ${childEntity.name}: ${e.message}", true)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params in disableLcaCheckerCheckForDesign", true)
        }
        render([output: disabled, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def enableLcaCheckerCheckForDesign() {
        String enabled = "false"
        String designId = params.designId
        String lcaCheckId = params.lcaCheckId
        String indicatorId = params.indicatorId
        Entity childEntity = entityService.readEntity(designId)

        if (childEntity && lcaCheckId && indicatorId) {
            if (childEntity.suppressedLcaCheckers) {
                childEntity.suppressedLcaCheckers.remove(lcaCheckId)
                try {
                    childEntity = childEntity.merge(flush: true)
                    enabled = "true"
                } catch (OptimisticLockingException e) {
                    loggerUtil.error(log, "LCA Checker: unable to enable check ${lcaCheckId} for design ${childEntity.name}", e)
                    flashService.setErrorAlert("LCA Checker: unable to enable check ${lcaCheckId} for design ${childEntity.name}: ${e.message}", true)
                }
            } else {
                flashService.setErrorAlert("childEntity.suppressedLcaCheckers is false in enableLcaCheckerCheckForDesign", true)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params in enableLcaCheckerCheckForDesign", true)
        }
        render([output: enabled, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def generateIndicatorResultsExcel() {
        String indicatorId = params.indicatorId
        Boolean alsoNoResults = params.boolean("alsoNoResults")
        Boolean carbonHeroes = params.boolean("carbonHeroes")
        Boolean prodWithoutBenchmark = params.boolean("prodWithoutBenchmark")
        Boolean onlyNoCarbonHeroes = params.boolean("onlyNoCarbonHeroes")
        Boolean onlyLatest = params.boolean("onlyLatest")
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        SXSSFWorkbook wb = new SXSSFWorkbook(100)

        if (indicator) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
            Query basicQuery = queryService.getQueryByQueryId("basicQuery", true)
            Query lcaQuery = queryService.getQueryByQueryId(Constants.LCA_PARAMETERS_QUERYID, true)
            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(null) // TODO: allow all in results excel?
            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(null)
            List<String> resultCategoryIds = resultCategories?.collect({ it.resultCategoryId })
            CellStyle bold = optimiExcelImportService.getBoldCellStyle(wb)
            Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(indicatorService.getLocalizedName(indicator)))
            List<LcaChecker> lcaCheckers = lcaCheckerService.getAllLcaCheckers(true)

            Integer rowIterator = 0
            Row headingRow = sheet.createRow(rowIterator)
            Integer headingIterator = 0

            Cell headingCell

            List<String> headings = ["Database Id","Project","Design","RIBA Stage","Project type","Frame type","License name","License type",
                                     "Show in portfolio","Approve as a verified template","Carbon hero","Datasets","Last modified"]

            headings.each { String heading ->
                headingCell = headingRow.createCell(headingIterator)
                headingCell.setCellStyle(bold)
                headingCell.setCellType(CellType.STRING)
                headingCell.setCellValue(heading)
                headingIterator++
            }

            basicQuery?.sections?.each { QuerySection querySection ->
                querySection.questions?.each { Question question ->
                    headingCell = headingRow.createCell(headingIterator)
                    headingCell.setCellStyle(bold)
                    headingCell.setCellType(CellType.STRING)
                    headingCell.setCellValue(question.localizedQuestion)
                    headingIterator++

                    if ("country".equals(question.questionId)) {
                        headingCell = headingRow.createCell(headingIterator)
                        headingCell.setCellStyle(bold)
                        headingCell.setCellType(CellType.STRING)
                        headingCell.setCellValue("Region")
                        headingIterator++
                    } else if ("type".equals(question.questionId)) {
                        headingCell = headingRow.createCell(headingIterator)
                        headingCell.setCellStyle(bold)
                        headingCell.setCellType(CellType.STRING)
                        headingCell.setCellValue("Macro type")
                        headingIterator++
                    }
                }
            }

            lcaQuery?.sections?.each { QuerySection querySection ->
                querySection.questions?.each { Question question ->
                    headingCell = headingRow.createCell(headingIterator)
                    headingCell.setCellStyle(bold)
                    headingCell.setCellType(CellType.STRING)
                    headingCell.setCellValue(question.localizedQuestion)
                    headingIterator++
                }
            }

            lcaCheckers.each { LcaChecker lcaChecker ->
                headingCell = headingRow.createCell(headingIterator)
                headingCell.setCellStyle(bold)
                headingCell.setCellType(CellType.STRING)
                headingCell.setCellValue(lcaChecker.checkName)
                headingIterator++

                headingCell = headingRow.createCell(headingIterator)
                headingCell.setCellStyle(bold)
                headingCell.setCellType(CellType.STRING)
                headingCell.setCellValue("Status " + lcaChecker.checkName)
                headingIterator++
            }

            headingCell = headingRow.createCell(headingIterator)
            headingCell.setCellStyle(bold)
            headingCell.setCellType(CellType.STRING)
            headingCell.setCellValue("surfaceGIFA")
            headingIterator++

            headingCell = headingRow.createCell(headingIterator)
            headingCell.setCellStyle(bold)
            headingCell.setCellType(CellType.STRING)
            headingCell.setCellValue("surfaceGFA-sqft")
            headingIterator++

            headingCell = headingRow.createCell(headingIterator)
            headingCell.setCellStyle(bold)
            headingCell.setCellType(CellType.STRING)
            headingCell.setCellValue("All Building area answers")
            headingIterator++

            resultCategories.each { ResultCategory resultCategory ->
                calculationRules.each { CalculationRule calculationRule ->
                    if (!resultCategory.ignoreFromTotals?.contains(calculationRule.calculationRuleId) || !resultCategory.ignoreFromTotals?.isEmpty()) {
                        headingCell = headingRow.createCell(headingIterator)
                        headingCell.setCellStyle(bold)
                        headingCell.setCellType(CellType.STRING)
                        headingCell.setCellValue("${resultCategory.resultCategory}: ${calculationRule.localizedName}, ${calculationRuleService.getLocalizedUnit(calculationRule, indicator)}")
                        headingIterator++
                    }
                }
            }

            FindIterable<Document> parentEntities

            if (prodWithoutBenchmark) {
                parentEntities = Entity.collection.find([deleted: [$ne: true], indicatorIds: [$nin: [indicatorId]], childEntities: [$exists: true], $or: [[parentEntityId: [$exists: false]], [parentEntityId: null]]], [_id: 1, name: 1, childEntities: 1, datasets: 1, entityClass: 1])
            } else {
                parentEntities = Entity.collection.find([deleted: [$ne: true], indicatorIds: [$in: [indicatorId]], childEntities: [$exists: true], $or: [[parentEntityId: [$exists: false]], [parentEntityId: null]]], [_id: 1, name: 1, childEntities: 1, datasets: 1, entityClass: 1])
            }

            rowIterator++

            Cell designCell
            Integer size = parentEntities?.size()
            Integer i = 1

            List<Document> resourceDocumentCache = []

            parentEntities?.each { Document p ->
                log.info("Progress: ${i} / ${size}, cache size: ${resourceDocumentCache.size()}")
                List<Document> licenses

                if (prodWithoutBenchmark) {
                    licenses = License.collection.find([type: Constants.LicenseType.PRODUCTION.toString(), compatibleEntityClasses: p.entityClass, $or: [[licensedEntityIds: [$in: [p._id]]], [licensedEntityIds: [$in: [p._id.toString()]]]]], [name: 1, type: 1])?.toList()?.sort({it.type})
                } else {
                    licenses = License.collection.find([type: [$in: [Constants.LicenseType.PRODUCTION.toString(),Constants.LicenseType.EDUCATION.toString()]], compatibleEntityClasses: p.entityClass, $or: [[licensedEntityIds: [$in: [p._id]]], [licensedEntityIds: [$in: [p._id.toString()]]]]], [name: 1, type: 1])?.toList()?.sort({it.type})
                }

                if (licenses) {
                    List<Dataset> datasets = (List<Dataset>) p.datasets?.toList()?.findAll({
                        basicQuery.queryId.equals(it.queryId)
                    })
                    List<String> entityIds = p.childEntities.findAll({ it.get("entityClass")?.equals(EntityClass.DESIGN.toString()) })?.collect({ it.get("entityId") })
                    List<Document> childEntities = Entity.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(entityIds)], deleted: [$ne: true]], [name: 1, childEntities: 1, datasets: 1, calculationTotalResults: 1,
                                                                                                                                                               calculationResults: 1, allowAsBenchmark: 1, ribaStage: 1, superVerified: 1,
                                                                                                                                                               lcaCheckerResult: 1, suppressedLcaCheckers: 1, carbonHero: 1, scope: 1,
                                                                                                                                                               lastUpdated: 1, chosenDesign: 1, dateCreated: 1])?.toList()
                    if (childEntities) {
                        if (carbonHeroes && childEntities.find({it.carbonHero})) {
                            childEntities = childEntities.findAll({it.carbonHero})
                        }
                        if (onlyNoCarbonHeroes) {
                            childEntities = childEntities.findAll({!it.carbonHero})
                        }

                        if (onlyLatest && childEntities) {
                            if (childEntities.find({it.chosenDesign})) {
                                childEntities = [childEntities.find({it.chosenDesign})]
                            } else {
                                Document latestChild = childEntities.findAll({it.dateCreated})?.sort({it.dateCreated})?.reverse()?.first()

                                if (latestChild) {
                                    childEntities = [latestChild]
                                } else {
                                    childEntities = null
                                }
                            }
                        }

                        childEntities?.each { Document e ->
                            List<Document> jsonResultsForIndicator = CalculationResult.collection.find([entityId: e._id.toString(), indicatorId: indicatorId, resultCategoryId: [$in: resultCategoryIds]], [resultCategoryId: 1, calculationRuleId: 1, result: 1])?.toList()

                            if (jsonResultsForIndicator||alsoNoResults||prodWithoutBenchmark) {
                                List allChildDatasets = e.datasets?.toList()
                                Integer datasetsSize = allChildDatasets?.findAll({!it.projectLevelDataset})?.size()
                                LcaCheckerResult lcaCheckerResult = (LcaCheckerResult) e.lcaCheckerResult
                                List<String> suppressedLcaCheckers = (List<String>) e.suppressedLcaCheckers
                                Double GIFA = allChildDatasets?.find({ "surfaceGIFA".equals(it.resourceId) })?.quantity
                                Double GFAsqft = allChildDatasets?.find({ "surfaceGFA-sqft".equals(it.resourceId) })?.quantity
                                List surfaceDefinitionsSharedDatasets = allChildDatasets?.findAll({ "surfaceDefinitionsShared".equals(it.queryId) })

                                def childDatasets = allChildDatasets?.findAll({
                                    lcaQuery.queryId.equals(it.queryId)
                                })
                                Integer designCellIterator = 0

                                Row designRow = sheet.createRow(rowIterator)

                                headings.each { String heading ->
                                    designCell = designRow.createCell(designCellIterator)
                                    if ("Database Id".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(e._id?.toString())
                                    } else if ("Project".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(p.name?.toString())
                                    } else if ("Design".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(e.name?.toString())
                                    } else if ("RIBA Stage".equals(heading)) {
                                        designCell.setCellType(CellType.NUMERIC)
                                        designCell.setCellValue(e.ribaStage?.toString()?.toInteger())
                                    } else if ("Project type".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(e.scope?.projectType ?: "")
                                    } else if ("Frame type".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(e.scope?.frameType ?: "")
                                    } else if ("License name".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(licenses.collect({ it.name }).join(", "))
                                    } else if ("License type".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(licenses.collect({ it.type.toUpperCase() }).unique().join(", "))
                                    } else if ("Show in portfolio".equals(heading)) {
                                        designCell.setCellType(CellType.BOOLEAN)
                                        designCell.setCellValue(!(e.allowAsBenchmark == null || e.allowAsBenchmark == false))
                                    } else if ("Approve as a verified template".equals(heading)) {
                                        designCell.setCellType(CellType.BOOLEAN)
                                        designCell.setCellValue(!(e.superVerified == null || e.superVerified == false))
                                    } else if ("Carbon hero".equals(heading)) {
                                        designCell.setCellType(CellType.BOOLEAN)
                                        designCell.setCellValue(!(e.carbonHero == null || e.carbonHero == false))
                                    } else if ("Datasets".equals(heading)) {
                                        designCell.setCellType(CellType.NUMERIC)
                                        designCell.setCellValue(datasetsSize ?: 0)
                                    } else if ("Last modified".equals(heading)) {
                                        designCell.setCellType(CellType.STRING)
                                        designCell.setCellValue(e.lastUpdated ? dateFormat.format((Date) e.lastUpdated) : "")
                                    }
                                    designCellIterator++
                                }

                                basicQuery.sections.each { QuerySection querySection ->
                                    def sectionDatasets = datasets.findAll({ querySection.sectionId.equals(it.sectionId) })

                                    querySection.questions?.each { Question question ->
                                        def questionDatasets = sectionDatasets?.findAll({
                                            question.questionId.equals(it.questionId)
                                        })

                                        designCell = designRow.createCell(designCellIterator)
                                        designCell.setCellType(CellType.STRING)
                                        String cellValue = ""

                                        questionDatasets?.each { dataset ->
                                            dataset.answerIds?.each { answer ->
                                                if (answer) {
                                                    cellValue = cellValue.size() > 0 ? "${cellValue}, " : "${cellValue}"
                                                    String localizedAnswer = question.choices?.find({ it.answerId?.equals(answer) })?.localizedAnswer

                                                    if (localizedAnswer) {
                                                        cellValue = "${cellValue}${localizedAnswer}"
                                                    } else {
                                                        Document d = resourceDocumentCache.find({answer.toString().equals(it.resourceId)})
                                                        if (!d) {
                                                            d = Resource.collection.findOne([resourceId: answer.toString(), active: true],[resourceId: 1, nameEN: 1, region: 1, filterValue: 1])

                                                            if (d) {
                                                                resourceDocumentCache.add(d)
                                                            }
                                                        }
                                                        if (d) {
                                                            cellValue = "${cellValue}${d.nameEN}"
                                                        } else if (dataset.resourceId) {
                                                            d = resourceDocumentCache.find({dataset.resourceId.equals(it.resourceId)})

                                                            if (!d) {
                                                                d = Resource.collection.findOne([resourceId: dataset.resourceId, active: true],[resourceId: 1, nameEN: 1, region: 1, filterValue: 1])

                                                                if (d) {
                                                                    resourceDocumentCache.add(d)
                                                                }
                                                            }

                                                            if (d) {
                                                                cellValue = "${cellValue}${d.nameEN}: ${answer} ${dataset.userGivenUnit ?: ""}"
                                                            } else {
                                                                cellValue = "${cellValue}${answer}"
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        designCell.setCellValue(cellValue ?: "")
                                        designCellIterator++

                                        if ("country".equals(question.questionId)) {
                                            designCell = designRow.createCell(designCellIterator)
                                            designCell.setCellType(CellType.STRING)
                                            String countryResourceId = questionDatasets?.find({it.questionId == "country"})?.resourceId

                                            if (countryResourceId) {
                                                Document countryDocument = resourceDocumentCache.find({countryResourceId.equals(it.resourceId)})

                                                if (countryDocument) {
                                                    cellValue = countryDocument.get("region")
                                                }
                                            }
                                            designCell.setCellValue(cellValue ?: "")
                                            designCellIterator++
                                        } else if ("type".equals(question.questionId)) {
                                            designCell = designRow.createCell(designCellIterator)
                                            designCell.setCellType(CellType.STRING)
                                            String countryResourceId = questionDatasets?.find({it.questionId == "type"})?.resourceId

                                            if (countryResourceId) {
                                                Document countryDocument = resourceDocumentCache.find({countryResourceId.equals(it.resourceId)})

                                                if (countryDocument) {
                                                    cellValue = countryDocument.get("filterValue")
                                                }
                                            }
                                            designCell.setCellValue(cellValue ?: "")
                                            designCellIterator++
                                        }
                                    }
                                }

                                lcaQuery.sections.each { QuerySection querySection ->
                                    def sectionDatasets = childDatasets.findAll({ querySection.sectionId.equals(it.sectionId) })

                                    querySection.questions?.each { Question question ->
                                        def questionDatasets = sectionDatasets?.findAll({
                                            question.questionId.equals(it.questionId)
                                        })

                                        designCell = designRow.createCell(designCellIterator)
                                        designCell.setCellType(CellType.STRING)
                                        String cellValue = ""

                                        questionDatasets?.each { dataset ->
                                            dataset.answerIds?.each { answer ->
                                                if (answer) {
                                                    cellValue = cellValue.size() > 0 ? "${cellValue}, " : "${cellValue}"
                                                    String localizedAnswer = question.choices?.find({ it.answerId?.equals(answer) })?.localizedAnswer

                                                    if (localizedAnswer) {
                                                        cellValue = "${cellValue}${localizedAnswer}"
                                                    } else {
                                                        Document d = resourceDocumentCache.find({answer.toString().equals(it.resourceId)})
                                                        if (!d) {
                                                            d = Resource.collection.findOne([resourceId: answer.toString(), active: true],[resourceId: 1, nameEN: 1])

                                                            if (d) {
                                                                resourceDocumentCache.add(d)
                                                            }
                                                        }

                                                        if (d) {
                                                            cellValue = "${cellValue}${d.nameEN}"
                                                        } else if (dataset.resourceId) {
                                                            d = resourceDocumentCache.find({dataset.resourceId.equals(it.resourceId)})

                                                            if (!d) {
                                                                d = Resource.collection.findOne([resourceId: dataset.resourceId, active: true],[resourceId: 1, nameEN: 1])

                                                                if (d) {
                                                                    resourceDocumentCache.add(d)
                                                                }
                                                            }

                                                            if (d) {
                                                                cellValue = "${cellValue}${d.nameEN}: ${answer} ${dataset.userGivenUnit ?: ""}"
                                                            } else {
                                                                cellValue = "${cellValue}${answer}"
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        designCell.setCellValue(cellValue ?: "")
                                        designCellIterator++
                                    }
                                }

                                lcaCheckers.each { LcaChecker lcaChecker ->
                                    designCell = designRow.createCell(designCellIterator)
                                    designCell.setCellStyle(bold)
                                    designCell.setCellType(CellType.NUMERIC)

                                    if (lcaCheckerResult && lcaCheckerResult.resultByCheck) {
                                        designCell.setCellValue(lcaCheckerResult.resultByCheck?.get(lcaChecker.checkId) ?: 0)
                                    }
                                    designCellIterator++

                                    designCell = designRow.createCell(designCellIterator)
                                    designCell.setCellStyle(bold)
                                    designCell.setCellType(CellType.STRING)
                                    if (suppressedLcaCheckers?.contains(lcaChecker.checkId)) {
                                        designCell.setCellValue("SUPPRESSED")
                                    } else if (lcaCheckerResult && lcaCheckerResult.resultByCheck) {
                                        if (lcaCheckerResult.failed?.contains(lcaChecker.checkId)) {
                                            if (lcaCheckerResult.resultByCheck?.keySet()?.toList()?.contains(lcaChecker.checkId) && lcaCheckerResult.resultByCheck?.get(lcaChecker.checkId) == null) {
                                                designCell.setCellValue("NOT APPLICABLE")
                                            } else {
                                                designCell.setCellValue("FAILED")
                                            }
                                        } else if (lcaCheckerResult.acceptable?.contains(lcaChecker.checkId)) {
                                            designCell.setCellValue("ACCEPTABLE")
                                        } else if (lcaCheckerResult.passed?.contains(lcaChecker.checkId)) {
                                            designCell.setCellValue("PASSED")
                                        } else {
                                            designCell.setCellValue("UNRESOLVED")
                                        }
                                    }
                                    designCellIterator++
                                }

                                designCell = designRow.createCell(designCellIterator)
                                designCell.setCellType(CellType.NUMERIC)
                                designCell.setCellValue(GIFA ?: 0D)
                                designCellIterator++

                                designCell = designRow.createCell(designCellIterator)
                                designCell.setCellType(CellType.NUMERIC)
                                designCell.setCellValue(GFAsqft ?: 0D)
                                designCellIterator++

                                designCell = designRow.createCell(designCellIterator)
                                designCell.setCellType(CellType.STRING)
                                designCell.setCellValue(surfaceDefinitionsSharedDatasets ? surfaceDefinitionsSharedDatasets.collect({ "${it.resourceId}: ${it.quantity} ${it.userGivenUnit ?: ""}" }).toString().replace("[","").replace("]","") : "")
                                designCellIterator++

                                resultCategories.each { ResultCategory resultCategory ->
                                    calculationRules.each { CalculationRule calculationRule ->
                                        if (!resultCategory.ignoreFromTotals?.contains(calculationRule.calculationRuleId) || !resultCategory.ignoreFromTotals?.isEmpty()) {
                                            Double total = jsonResultsForIndicator?.findAll({ it.resultCategoryId.equals(resultCategory.resultCategoryId) && it.calculationRuleId.equals(calculationRule.calculationRuleId) })?.collect({ it.result })?.sum()

                                            designCell = designRow.createCell(designCellIterator)
                                            designCell.setCellType(CellType.NUMERIC)
                                            designCell.setCellValue(total ?: 0D)
                                            designCellIterator++
                                        }
                                    }
                                }
                                rowIterator++
                            }
                        }
                    }
                }
                i++
            }
        }

        try {
            response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            response.setHeader("Content-disposition", "attachment; filename=${indicatorId}_${new Date().format('dd.MM.yyyy')}.xlsx")
            OutputStream outputStream = response.getOutputStream()
            wb.write(outputStream)
            log.info("Bytes written")
            outputStream.flush()
            outputStream.close()
            boolean disposed = wb.dispose()
            log.info("WB DISPOSED: ${disposed}")
        } catch (Exception e) {
            // client disconnected before bytes were able to be written.
            loggerUtil.warn(log,"generateIndicatorResultsExcel client disconnected before bytes were able to be written: error", e)
        }
    }

    def disableIndicatorForDesign() {
        String designId = params.id
        String indicatorId = params.indicatorId
        Entity design

        if (designId && indicatorId) {
            design = entityService.getEntityById(designId)

            if (design.disabledIndicators) {
                design.disabledIndicators.add(indicatorId)
            } else {
                design.disabledIndicators = [indicatorId]
            }
            design.disabledIndicators = design.disabledIndicators.unique()
            design.disabledIndicators.each { String indicatrId ->
                newCalculationServiceProxy.removeExistingResultsByIndicator(design, indicatrId)
            }
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    def enableIndicatorForDesign() {
        String designId = params.id
        String indicatorId = params.indicatorId
        Entity design

        if (designId && indicatorId) {
            design = entityService.getEntityById(designId)
            Entity parent = design.parentById
            design.disabledIndicators?.remove(indicatorId)
            design = entityService.updateEntity(design)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator) {
                design = newCalculationServiceProxy.calculate(design.id, indicatorId, parent)
            }
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }
    def allowAsBenchmark() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.allowAsBenchmark = Boolean.TRUE
            design  = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    def rejectAsBenchmark() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.allowAsBenchmark = Boolean.FALSE
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }
    def setAsChosen() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.chosenDesign = Boolean.TRUE
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    def unSetAsChosen() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.chosenDesign = Boolean.FALSE
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    def setAsCarbonHero() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.carbonHero = Boolean.TRUE
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    def unsetAsCarbonHero() {
        String designId = params.designId
        Entity design

        if (designId) {
            design = entityService.getEntityById(designId)
            design.carbonHero = null
            design = entityService.updateEntity(design)
        }

        if (design) {
            redirect(controller: 'entity', action: 'show', params: [entityId: design.getParentEntityId().toString()])
        } else {
            throw new RuntimeException("Cannot redirect to entity view, design missing!")
        }
    }

    /*private List<String> resolveCalculationRuleForPieChart(List<ResultCategory> resultCategories, List<CalculationRule> calculationRules, List<CalculationResult> resultsByIndicatorId, Entity child, Indicator indicator) {
        List<CalculationRule> calculationRulesForPieChart = []

        if (resultCategories && calculationRules && child && indicator) {

            Map resultsForIndicatorPerCalculationRule = resultsByIndicatorId?.groupBy { it.calculationRuleId }

            calculationRulesForPieChart = calculationRules.findAll { CalculationRule calculationRule ->

                List<CalculationResult> resultsForCalculationRule = resultsForIndicatorPerCalculationRule?.getAt(calculationRule.calculationRuleId)

                int counter = 0
                Boolean hasFewResults = resultCategories?.findResult { ResultCategory resultCategory ->
                    Double score = child.getResult(indicator.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsForCalculationRule, false)

                    if (score && score != 0) {
                        counter ++
                    }

                    return (counter > 1)
                }

                return hasFewResults
            }
        }

        return calculationRulesForPieChart
    }*/

    /*def initializeDatasetsForResultPage(){
        String indicatorId = request?.JSON.indicatorId
        String entityId = request?.JSON.entityId

        Entity entity = entityService.getEntityById(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
        List<CalculationRule> calculationRules = indicator?.graphCalculationRuleObjects
        String calculationRuleId = request?.JSON.calculationRuleId
        CalculationRule calculationRule = calculationRuleId ? calculationRules?.find({calculationRuleId?.equalsIgnoreCase(it.calculationRuleId)}) : calculationRules[0]
        if(entity && indicator && calculationRule){

            List<CalculationResult> resultForIndicator = entity.getCalculationResultObjects(indicator.indicatorId, calculationRuleId, null)

            String sessionId1 = "resultPageDataset." + entityId +"."+ indicatorId + "-"+calculationRuleId
            String sessionId2 = "resultPageDesign." + entityId
            String sessionId3 = "resultPageIndicator." + indicatorId

            session.setAttribute(sessionId1, resultForIndicator)
            session.setAttribute(sessionId2, entity)
            session.setAttribute(sessionId3, indicator)



        }
        render status: 500, text: "dataset loaded!"
    }*/
}


















