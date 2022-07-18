package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import grails.gorm.transactions.Transactional
import grails.web.api.ServletAttributes
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpSession

class QueryPreparationService implements ServletAttributes {

    def indicatorService
    def entityService
    def queryService
    def datasetService
    def xmlService
    def eolProcessService
    def flashService
    def messageSource
    def loggerUtil
    def calculationProcessService
    def resourceService
    ScopeFactoryService scopeFactoryService
    QuestionService questionService

    @Transactional
    Entity saveProjectLevelQuery(Entity parentEntity, String queryId, boolean skipCalculation, boolean resultsOutOfDate) {
        Boolean periodLevelParamQuery = params.boolean("periodLevelParamQuery")
        Boolean designLevelParamQuery = params.boolean("designLevelParamQuery")
        // For FEC & RE2020 tool, extract the Rset mapping and remove from params as to not generate the wrong datasets in saveDatasetsFromParameters()
        Map<String, String> defaults = getDefaultTransportAndServiceLife(params, queryId)
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalQuestions = additionalQuestionsQuery.getAllQuestions()
        EolProcessCache eolProcessCache = EolProcessCache.init()

        Dataset oldDefaultLocalCompDataset = datasetService.getDefaultLocalCompensationCountryDataset(parentEntity)
        String oldDefaultLocalCompCountry = oldDefaultLocalCompDataset?.answerIds?.get(0)
        String oldDefaultLocalCompProfile = oldDefaultLocalCompDataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
        String oldTransport = parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
        String oldLcaModel = parentEntity?.lcaModel
        String oldLocalCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)

        parentEntity = datasetService.saveDatasetsFromParameters(params, request, parentEntity?.id?.toString(), null, defaults,
                true, null, null, null, skipCalculation, resultsOutOfDate)

        // For FEC + RE2020 RSET import
        if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL == queryId) {
            saveBatimentAndZonesMapping(parentEntity, params, session, queryId)
        }

        Dataset newDefaultLocalCompDataset = datasetService.getDefaultLocalCompensationCountryDataset(parentEntity)
        String newDefaultLocalCompCountry = newDefaultLocalCompDataset?.answerIds?.get(0)
        String newDefaultLocalCompProfile = newDefaultLocalCompDataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
        String newTransport = parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
        String newLcaModel = parentEntity?.lcaModel
        String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)

        Resource projectCountry = parentEntity?.countryResource
        Boolean handleLocalComps = Boolean.FALSE
        Boolean handleLocalCompsProfile = Boolean.FALSE
        Boolean handleLcaModel = Boolean.FALSE
        Boolean handleTransport = Boolean.FALSE

        if ((!oldTransport && newTransport) || (oldTransport && !oldTransport.equals(newTransport))) {
            handleTransport = Boolean.TRUE
        }

        if ((!oldDefaultLocalCompCountry && newDefaultLocalCompCountry) || (oldDefaultLocalCompCountry && !oldDefaultLocalCompCountry.equals(newDefaultLocalCompCountry)) || ("00".equals(localCompensationMethodVersion) && !"00".equals(oldLocalCompensationMethodVersion)) || ("00".equals(oldLocalCompensationMethodVersion) && !"00".equals(localCompensationMethodVersion))) {
            handleLocalComps = Boolean.TRUE
        } else if ((!oldDefaultLocalCompProfile && newDefaultLocalCompProfile) || (oldDefaultLocalCompProfile && !oldDefaultLocalCompProfile.equals(newDefaultLocalCompProfile))) {
            handleLocalCompsProfile = Boolean.TRUE
        }

        if ((!oldLcaModel && newLcaModel) || (oldLcaModel && !oldLcaModel.equals(newLcaModel))) {
            handleLcaModel = Boolean.TRUE
        }

        /* SW-1645 keep the commented code until after release 0.6.0
        List<String> linkedIndicators = parentEntity?.queryAndLinkedIndicators?.get(queryId)
        */

        Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = entityService.getIndicatorsByProjectLevelQueryIdsForCurrentUser(parentEntity)
        List<Indicator> linkedIndicators = indicatorsByProjectLevelQueryIds?.get(queryId)

        if (periodLevelParamQuery) {
            List<String> childEntityIds = getActualChildIdsByEntityClasses(parentEntity,
                    [Constants.EntityClass.OPERATING_PERIOD.toString()])

            childEntityIds?.each { periodId ->
                Entity p = datasetService.saveDatasetsFromParameters(params, request, periodId.toString(),
                        null, defaults, true, null, null,
                        null, skipCalculation, resultsOutOfDate)

                if (linkedIndicators) {
                    p = p.merge(flush: true)
                }
            }
        }

        if (designLevelParamQuery) {
            List<String> childEntityIds = getActualChildIdsByEntityClasses(parentEntity,
                    [Constants.EntityClass.DESIGN.toString(), Constants.EntityClass.MATERIAL_SPECIFIER.toString()])

            childEntityIds?.each { String designId ->
                Entity d = datasetService.saveDatasetsFromParameters(params, request, designId, null,
                        defaults, true, null, null,
                        null, skipCalculation, resultsOutOfDate)

                if (d?.datasets) {
                    List<Dataset> datasets = d.datasets.toList()
                    ResourceCache resourceCache = ResourceCache.init(datasets)
                    if (handleLocalComps) {
                        for (Dataset dataset: datasets) {
                            ResourceType subType = resourceService.getSubType(resourceCache.getResource(dataset))

                            if (!dataset.projectLevelDataset) {
                                def applyLocalComps = dataset.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_QUESTIONID)

                                if ("00".equals(localCompensationMethodVersion)) {
                                    // 15153 Local resource keeps local comp answer
                                    if (dataset.additionalQuestionAnswers && !resourceService.getIsLocalResource(resourceCache.getResource(dataset)?.areas)) {
                                        dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_QUESTIONID, "noLocalCompensation")
                                        dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                    } else {
                                        // If disabled is selected for local compensation but the user changes the target country
                                        // in the same saving process, apply the new country to local resources. This should improve certain workflows
                                        if (applyLocalComps && applyLocalComps.equals(oldDefaultLocalCompCountry) && newDefaultLocalCompCountry) {
                                            dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_QUESTIONID, newDefaultLocalCompCountry)
                                            if (newDefaultLocalCompProfile) {
                                                dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                            } else {
                                                dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                            }
                                        }
                                    }
                                } else {
                                    if (applyLocalComps && applyLocalComps.equals(oldDefaultLocalCompCountry)) {
                                        if (newDefaultLocalCompCountry) {
                                            dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_QUESTIONID, newDefaultLocalCompCountry)
                                            if (newDefaultLocalCompProfile) {
                                                dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                            } else {
                                                dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                            }
                                        } else {
                                            dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_QUESTIONID)
                                            dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                        }
                                    } else if ((!applyLocalComps || "noLocalCompensation".equals(applyLocalComps)) && newDefaultLocalCompCountry) {
                                        if (dataset.additionalQuestionAnswers) {
                                            dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_QUESTIONID, newDefaultLocalCompCountry)

                                            if (newDefaultLocalCompProfile) {
                                                dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                            } else {
                                                dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                            }
                                        } else {
                                            if (newDefaultLocalCompProfile) {
                                                dataset.additionalQuestionAnswers = [(Constants.LOCAL_COMP_QUESTIONID): newDefaultLocalCompCountry, (Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID): newDefaultLocalCompProfile]
                                            } else {
                                                dataset.additionalQuestionAnswers = [(Constants.LOCAL_COMP_QUESTIONID): newDefaultLocalCompCountry]
                                            }
                                        }
                                    } else if (resourceService.getIsLocalResource(resourceCache.getResource(dataset)?.areas) && applyLocalComps && !oldDefaultLocalCompCountry
                                            && newDefaultLocalCompCountry && applyLocalComps == projectCountry?.resourceId) {
                                        // This is for a special workflow, where you select no target country,
                                        // then disable LC and then re-enable it and select a new country.
                                        // This is because local resources were staying at project level - 16591
                                        dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_QUESTIONID, newDefaultLocalCompCountry)

                                        if (newDefaultLocalCompProfile) {
                                            dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                        } else {
                                            dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                        }
                                    }
                                }

                                if (handleLcaModel) {
                                    updateEolProcessInformation(dataset, subType, projectCountry, oldLcaModel, newLcaModel, eolProcessCache)
                                }
                            }
                        }
                    } else if (handleLocalCompsProfile) {
                        for (Dataset dataset: datasets) {
                            ResourceType subType = resourceService.getSubType(resourceCache.getResource(dataset))

                            if (!dataset.projectLevelDataset) {
                                def applyLocalCompsProfile = dataset.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                if (applyLocalCompsProfile && applyLocalCompsProfile.equals(oldDefaultLocalCompProfile)) {
                                    if (newDefaultLocalCompProfile) {
                                        dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                    } else {
                                        dataset.additionalQuestionAnswers.remove(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                                    }
                                } else if (!applyLocalCompsProfile && newDefaultLocalCompProfile) {
                                    if (dataset.additionalQuestionAnswers) {
                                        dataset.additionalQuestionAnswers.put(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID, newDefaultLocalCompProfile)
                                    }
                                }

                                if (handleLcaModel) {
                                    updateEolProcessInformation(dataset, subType, projectCountry, oldLcaModel, newLcaModel, eolProcessCache)
                                }
                            }
                        }
                    } else if (handleLcaModel) {
                        for (Dataset dataset: datasets) {
                            ResourceType subType = resourceService.getSubType(resourceCache.getResource(dataset))
                            if (!dataset.projectLevelDataset) {
                                updateEolProcessInformation(dataset, subType, projectCountry, oldLcaModel, newLcaModel, eolProcessCache)

                            }
                        }
                    }
                }

                if (linkedIndicators) {
                    if (handleTransport) {
                        populateTransportInfoBasedOnLinkedIndicators(parentEntity, d, linkedIndicators, additionalQuestions)
                    }
                    try {
                        d.merge(flush: true)
                    } catch (IllegalArgumentException iaex) {
                        log.error("Error while saving an entity: ", iaex)
                    }
                } else if (handleLocalComps) {
                    try {
                        d.merge(flush: true)
                    } catch (IllegalArgumentException iaex) {
                        log.error("Error while saving an entity: ", iaex)
                    }
                }
            }
        }

        List<String> childEntityIdsForCalculation = entityService.getChildEntitiesForParentProjectCalculation(parentEntity)?.entityId
        List<Entity> childEntitiesForCalculation = entityService.getEntitiesByIds(childEntityIdsForCalculation, parentEntity)

        for (Entity entity in childEntitiesForCalculation){
            updateAdditionalJSCalculationParameters(queryId, entity)
        }

        return parentEntity
    }

    void updateEolProcessInformation(Dataset dataset, ResourceType subType, Resource projectCountry, String oldLcaModel, String newLcaModel, EolProcessCache eolProcessCache) {
        String eolProcessId = dataset.additionalQuestionAnswers?.get(Constants.EOL_QUESTIONID)
        Resource resource = datasetService.getResource(dataset)

        if (eolProcessId) {
            // dont send dataset to avoid getting user defined
            EolProcess oldDefaultEolProcess = eolProcessService.getEolProcessForDataset(null, resource, subType, projectCountry, null, oldLcaModel, eolProcessCache)

            if (oldDefaultEolProcess && eolProcessId.equals(oldDefaultEolProcess.eolProcessId)) {
                EolProcess newDefaultEolProcess = eolProcessService.getEolProcessForDataset(null, resource, subType, projectCountry, null, newLcaModel, eolProcessCache)

                if (newDefaultEolProcess) {
                    if (dataset.additionalQuestionAnswers) {
                        dataset.additionalQuestionAnswers.put(Constants.EOL_QUESTIONID, newDefaultEolProcess.eolProcessId)
                    } else {
                        dataset.additionalQuestionAnswers = [(Constants.EOL_QUESTIONID): newDefaultEolProcess.eolProcessId]
                    }
                }
            }
        } else {
            EolProcess newDefaultEolProcess = eolProcessService.getEolProcessForDataset(null, resource, subType, projectCountry, null, newLcaModel, eolProcessCache)

            if (newDefaultEolProcess) {
                if (dataset.additionalQuestionAnswers) {
                    dataset.additionalQuestionAnswers.put(Constants.EOL_QUESTIONID, newDefaultEolProcess.eolProcessId)
                } else {
                    dataset.additionalQuestionAnswers = [(Constants.EOL_QUESTIONID): newDefaultEolProcess.eolProcessId]
                }
            }
        }
    }

    //Bugfix: SW-1277-Added parentEntity parameter
    @Transactional
    void saveDesignLevelQuery(Entity entity, Entity parentEntity, Indicator indicator, boolean skipCalculation, boolean resultsOutOfDate) {
        String enableScopeSectionId = params.enableScopeSectionId
        Map<String, String> defaults = [:]
        String queryId = params.queryId

        if (enableScopeSectionId) {
            if (entity?.scope && enableScopeSectionId) {
                def map = scopeFactoryService.getServiceImpl(entity?.entityClass).getMapSectionToProjectScope()
                enableScopeSectionId = map.find { it.value?.contains(enableScopeSectionId) }?.key

                if (enableScopeSectionId) { //????IF sectionId it's not a valid section????
                    if (entity.scope.lcaCheckerList) {
                        entity.scope.lcaCheckerList.add(enableScopeSectionId)
                    } else {
                        entity.scope.lcaCheckerList = [enableScopeSectionId]
                    }
                    entity.disabledSections = entityService.resolveDisabledSections(entity)
                } else {
                    log.error("SectionId is not valid updating ProjectScope from buldingMaterialsQuery page")
                    flashService.setErrorAlert("SectionId is not valid updating ProjectScope from buldingMaterialsQuery page", true)
                }
            }
        }

        entity = datasetService.saveDatasetsFromParameters(params, request, entity.id.toString(), null, defaults,
                null, null, null, indicator, skipCalculation, resultsOutOfDate)
        updateAdditionalJSCalculationParameters(queryId, entity)

        //created calculationProcess record here to avoid delay when new thread started
        if (!skipCalculation) {
            calculationProcessService.createCalculationProcessAndSave(entity.id?.toString(), entity?.parentEntityId?.toString(), indicator.indicatorId)
        }

        entityService.calculateEntity(entity, indicator.indicatorId, queryId, false, skipCalculation)

        boolean saveError = false

        if (flash.fadeErrorAlert) {
            saveError = true
        }

        if (!saveError && Boolean.TRUE.equals(entity.resultsOutOfDate)) {
            if (entity?.isIndicatorReady(indicator)) {
                String link = queryService.createResultsLink(parentEntity, entity.id.toString(), indicator.indicatorId)
                flash.fadeWarningAlert = messageSource.getMessage('query.saved', [link].toArray(), LocaleContextHolder.getLocale())
            } else {
                flash.fadeWarningAlert = messageSource.getMessage('query.saved.not_ready', null, LocaleContextHolder.getLocale())
            }
        }
    }

    Map<String, String> getDefaultTransportAndServiceLife(Map params, String queryId) {
        Map<String, String> defaults = [:]

        if (Constants.LCA_PARAMETERS_QUERYID.equals(queryId)) {
            String defaultTransport = params?.get("calculationDefaults.transportationDefault")
            String defaultServiceLife = params?.get("calculationDefaults.serviceLifeDefault")
            defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT), defaultTransport ?: null)
            defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE), defaultServiceLife ?: null)
        }

        return defaults
    }

    // For FEC + RE2020 RSET import
    void saveBatimentAndZonesMapping(Entity parentEntity, Map params, HttpSession session, String queryId) {
        Map<String, String> batimentMapping = params.batimentMapping
        Map zoneMapping = params.zoneMapping
        Map parcelleMapping = params.parcelleMapping
        params.remove('zoneMapping')
        params.remove('batimentMapping')
        params.remove('parcelleMapping')
        // save the rset import mapping
        parentEntity = xmlService.saveBatimentAndZonesMapping(parentEntity, batimentMapping, zoneMapping, parcelleMapping)

        if (params.boolean("injectFromXml")) {
            //only import from clicking the import button, not save
            xmlService.injectAnswerFromRset(parentEntity, session)
        }
    }

    List<String> getActualChildIdsByEntityClasses(Entity parentEntity, List<String> allowedEntityClasses) {
        return parentEntity?.childEntities?.findAll { !it.deleted && it.entityClass in allowedEntityClasses }?.collect({ it.entityId.toString() })
    }

    /* SW-1645 keep the commented code until after release 0.6.0
    void populateInfoBasedOnLinkedIndicators(Entity parentEntity, Entity childEntity, List<String> linkedIndicators, List<Question> additionalQuestionsFromAdditionalQuery) {
    */
    void populateTransportInfoBasedOnLinkedIndicators(Entity parentEntity, Entity childEntity, List<Indicator> indicators, List<Question> additionalQuestionsFromAdditionalQuery) {
        Question additionalQuestionTransportMean = additionalQuestionsFromAdditionalQuery?.find({ Question it -> it.questionId == Constants.TRANSPORT_RESOURCE_QUESTIONID })
        Question additionalQuestionTransportMeanLeg2 = additionalQuestionsFromAdditionalQuery?.find({ Question it -> it.questionId == Constants.TRANSPORT_RESOURCE_LEG2_QUESTIONID })
        String newTransport = parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
        /* SW-1645 keep the commented code until after release 0.6.0
        List<Indicator> indicators = indicatorService.getIndicators(linkedIndicators, true)
        */
        indicators.each { Indicator indicator ->
            List<String> additionalQuestionIdsFromIndicator = indicator?.indicatorQueries?.collect({ it.additionalQuestionIds })?.flatten()?.unique()
            boolean isOperationNeeded = additionalQuestionsFromAdditionalQuery.any { Question it ->
                additionalQuestionIdsFromIndicator.contains(it.questionId) && it.isTransportQuestion ||
                        it.questionId == additionalQuestionTransportMean.questionId || it.questionId == additionalQuestionTransportMeanLeg2.questionId
            }

            if (additionalQuestionsFromAdditionalQuery && additionalQuestionIdsFromIndicator && isOperationNeeded) {
                ResourceCache resourceCache
                for (Question q in additionalQuestionsFromAdditionalQuery) {
                    Map<String, List<Dataset>> datasetGroupedByQueryId = childEntity.datasets.groupBy { it.queryId }

                    for (Map.Entry<String, List<Dataset>> entry : datasetGroupedByQueryId.entrySet()) {
                        //moved check for indicatorQueries from Dataset to Question loop. To avoid useless iterations for dataset
                        if (indicator?.indicatorQueries?.any { entry.getKey() == it.queryId && it.additionalQuestionIds?.contains(q.questionId) }) {
                            resourceCache = ResourceCache.init(entry.getValue())
                            for (Dataset dataset in entry.getValue()) {
                                if (!dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers = [:]
                                }
                                if (q.isTransportQuestion) {
                                    if (!dataset.additionalQuestionAnswers.get(q.questionId)) {
                                        if (newTransport) {
                                            dataset.additionalQuestionAnswers.put(q.questionId, "default")
                                        } else if ("default".equals(dataset?.additionalQuestionAnswers?.get(q.questionId))) {
                                            dataset.additionalQuestionAnswers.remove(q.questionId)
                                        }
                                    }
                                } else if (additionalQuestionTransportMean && q.questionId == additionalQuestionTransportMean?.questionId && newTransport) {

                                    String transportResourceId = dataset.additionalQuestionAnswers?.get(q.questionId)
                                    String defaultTransportMeanOther = questionService.getDefaultValueFromOtherResourceSubtype(additionalQuestionTransportMean, parentEntity, resourceCache.getResource(dataset)).toString()

                                    if (transportResourceId && transportResourceId.equalsIgnoreCase(defaultTransportMeanOther)) {
                                        String defaultTransportMean = additionalQuestionTransportMean.getDefaultValueFromResourceSubtype(parentEntity, resourceCache.getResource(dataset)).toString()
                                        dataset.additionalQuestionAnswers.put(q.questionId, defaultTransportMean)
                                    }
                                } else if (additionalQuestionTransportMeanLeg2 && q.questionId == additionalQuestionTransportMeanLeg2?.questionId && newTransport) {

                                    String transportResourceIdLeg2 = dataset.additionalQuestionAnswers?.get(q.questionId)
                                    String defaultTransportMeanOther = questionService.getTransportLeg2DefaultValueFromResourceOrSubType(parentEntity, resourceCache.getResource(dataset), additionalQuestionTransportMeanLeg2)

                                    if (transportResourceIdLeg2 && transportResourceIdLeg2.equalsIgnoreCase(defaultTransportMeanOther)) {
                                        String defaultTransportMean = questionService.getDefaultValueFromResourceOrSubType(parentEntity, resourceCache.getResource(dataset), additionalQuestionTransportMeanLeg2)
                                        dataset.additionalQuestionAnswers.put(q.questionId, defaultTransportMean)
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
     *
     * @param queryId - Query where some question was changed and need to update values in entityQuery
     * @param childEntity - Design entity where need to update values
     */
    void updateAdditionalJSCalculationParameters(String queryId, Entity childEntity){
        Query currentQuery = queryService.getQueryByQueryId(queryId, true)
        List<Query> allQueriesForEntity = queryService.getQueriesForEntity(childEntity)
        Set<Dataset> updatedDatasets = []

        allQueriesForEntity?.removeAll {
            it.queryId == currentQuery.queryId
        }

        for (Query entityQuery in allQueriesForEntity) {
            Map<String, List<String>> additionalCalcParams = queryService.getAdditionalCalcFromQueryForParticularQuery(entityQuery, queryId)

            if (additionalCalcParams && entityQuery.linkedJavascriptRules){
                //additionalCalcParams not need further, it's like a check that exists some params for recalculation
                updatedDatasets.addAll(datasetService.recalculateDatasetsByAdditionalJsScripts(entityQuery, currentQuery.queryId, childEntity))
            }
        }
        //Will replace old Datasets by updated version
        if (updatedDatasets) {
            childEntity.datasets.addAll(updatedDatasets)
            childEntity.save(flush: true, failOnError: true)
        }
    }
}
