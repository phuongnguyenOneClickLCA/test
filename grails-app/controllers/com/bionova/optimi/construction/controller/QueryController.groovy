/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.CalculationProcess
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.DataLoadingFeature
import com.bionova.optimi.core.domain.mongo.DataLoadingFromFile
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryApplyFilterOnCriteria
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.service.CalculationMessageService
import com.bionova.optimi.core.service.QueryParamsService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.ui.VerificationPoint
import com.bionova.optimi.ui.rset.RSEnvDatasetDto
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.collections.CollectionUtils
import org.apache.poi.ss.usermodel.Workbook
import org.bson.Document

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class QueryController extends ExceptionHandlerController {

    def indicatorService
    def queryService
    def entityService
    def datasetService
    def taskService
    def userService
    def errorMessageUtil
    def questionService
    def denominatorUtil
    def helpConfigurationService
    def newCalculationServiceProxy
    def workFlowService
    def licenseService
    def channelFeatureService
    def simulationToolService
    def unitConversionUtil
    def stringUtilsService
    def eolProcessService
    def resourceTypeService
    def productDataListService
    def querySectionService
    def xmlService
    def accountImagesService
    def additionalQuestionService
    def groovyPageRenderer
    def optimiResourceService
    def constructionService
    def verificationPointService
    def featureService
    def re2020Service
    def valueReferenceService
    def queryPreparationService
    def calculationProcessService
    QueryParamsService queryParamsService
    CalculationMessageService calculationMessageService

    // Deprecate code, to be removed by the end of feature 0.5.0 release
    def form() {
        long now = System.currentTimeMillis()
        log.info("Started getting attrs for query form, params: ${params}")
        String queryId = params.queryId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        Entity parentEntity = entityService.getEntityById(params.entityId, session)

        Entity entity

        if ("materialSpecifier".equals(queryId)) {
            entity = entityService.getMaterialSpecifierEntity(parentEntity)
        } else {
            List<String> projectLevelQueryIds = entityService.getProjectLevelQueryIdsForCurrentUser(parentEntity)
            entity = entityService.getEntityWithProjectionForQuery(params.childEntityId, queryId, projectLevelQueryIds)
        }
        Boolean projectLevel = params.boolean('projectLevel')
        Boolean newProject = params.boolean('newProject')
        Boolean periodLevelParamQuery = params.boolean('periodLevelParamQuery')
        Boolean designLevelParamQuery = params.boolean('designLevelParamQuery')

        if (parentEntity && ((indicator && entity) || (queryId && projectLevel))) {
            Query query = queryService.getQueryByQueryIdWithoutCache(queryId, Boolean.TRUE)
            // Check that query is allowed for the indicator, no indicator for project level queries
            if (query && (!indicator || indicator.indicatorQueries?.find({ query.queryId.equals(it.queryId) }))) {
                User user = userService.getCurrentUser()
                DataLoadingFeature dataLoadingFeature = query.dataLoadingFeature
                DataLoadingFromFile dataLoadingFromFile = query?.dataLoadingFromFile
                Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.BETA_FEATURES, Feature.CHAT_SUPPORT, Feature.SPLIT_OR_CHANGE, Feature.SEND_ME_EPD_REQUEST, Feature.COMPARE_MATERIAL, Feature.UNLOCK_VERIFIED_DATASET, Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS], user)
                Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
                Boolean chatSupportLicensed = featuresAllowed.get(Feature.CHAT_SUPPORT)
                Boolean splitOrChangeLicensed = featuresAllowed.get(Feature.SPLIT_OR_CHANGE)
                Boolean unlockVerifiedDatasetLicensed = featuresAllowed.get(Feature.UNLOCK_VERIFIED_DATASET)
                Boolean showAndJumpToVPointLicensed = featuresAllowed.get(Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS)

                List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
                List<Feature> licensedFeatures = []

                if (validLicenses) {
                    validLicenses.each { License license ->
                        license.licensedFeatures?.each { Feature feature ->
                            licensedFeatures.add(feature)
                        }
                    }
                }

                List<String> licensedFeatureIds = licensedFeatures?.collect({ it.featureId }) ?: []
                // remove sections and questions by licenseKey / requiredLicenseKeys / disableForLicenseKeys params
                queryService.doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery(query, licensedFeatureIds)

                Boolean downloadQueryDataLicensed = licensedFeatures.find({ it.featureId == Feature.DOWNLOAD_QUERY_DATA }) ? true : false

                Boolean userReadOnlyQuery = Boolean.FALSE
                if (parentEntity.readonlyUserIds?.contains(user?.id?.toString())
                        && !parentEntity.modifierIds?.contains(user?.id?.toString())
                        && !parentEntity.managerIds?.contains(user?.id?.toString())) {
                    userReadOnlyQuery = Boolean.TRUE
                }

                Boolean carbonDesignerOnlyLicensed = parentEntity.getCarbonDesignerOnlyLicensed(validLicenses)

                if (projectLevel) {
                    Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(parentEntity.id.toString(), null)
                    Boolean noDefaults = params.boolean('noDefaults')
                    Map<String, List<String>> projectLevelHideableQuestions = entityService.getHiddenQuestionsOnProjectLevel(parentEntity, query.queryId)

                    def showMandatory = !query.disableHighlight
                    def modifiable = parentEntity.modifiable && validLicenses?.size() > 0

                    String errorFromCheck = entityService.queryModifiable(parentEntity, null, null, validLicenses, true)

                    if (errorFromCheck) {
                        if (errorFromCheck == "readonly") {
                            flash.warningAlert = "Data shown in read only mode. You lack privileges to use this tool for editing purposes. Please procure or check out a corresponding license or add-on."
                        }
                        modifiable = false
                        userReadOnlyQuery = true
                    }

                    List<Question> allQueryQuestions = query.allQuestions
                    Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                    List<Question> queryAdditionalQuestions = []
                    allQueryQuestions?.each { Question question ->
                        question.getAdditionalQuestions(null, additionalQuestionsQuery, null, parentEntity)?.each { Question additionalQuestion ->
                            if (!queryAdditionalQuestions.contains(additionalQuestion)) {
                                queryAdditionalQuestions.add(additionalQuestion)
                            }
                        }
                    }
                    Map<String, List<Document>> additionalQuestionResources = [:]
                    Map<String, Boolean> additionalQuestionLicensed = [:]

                    if (queryAdditionalQuestions) {
                        queryAdditionalQuestions.each { Question additionalQuestion ->
                            additionalQuestionService.putAdditionalQuestionResourcesToMap(additionalQuestion, additionalQuestionResources, indicator, queryId, session)
                            licenseService.putLicensedStatusOfQuestionToMap(additionalQuestion, additionalQuestionLicensed, licensedFeatureIds, indicator)
                        }
                    }

                    // the Rset mapping table for french FEC tool and Re2020, key is the questionId
                    Map<String, String> rsetImportMappingTable = xmlService.getRsetTableMappingRender(queryId, parentEntity, session)

                    [
                            parentEntity                 : parentEntity,
                            query                        : query,
                            queryId                      : queryId,
                            projectLevel                 : projectLevel,
                            projectLevelHideableQuestions: projectLevelHideableQuestions,
                            modifiable                   : modifiable,
                            showMandatory                : showMandatory,
                            dataLoadingFeature           : dataLoadingFeature,
                            noDefaults                   : noDefaults,
                            additionalQuestionResources  : additionalQuestionResources,
                            additionalQuestionLicensed   : additionalQuestionLicensed,
                            designLevelParamQuery        : designLevelParamQuery,
                            periodLevelParamQuery        : periodLevelParamQuery,
                            newProject                   : newProject,
                            userReadOnlyQuery            : userReadOnlyQuery,
                            betaFeaturesLicensed         : betaFeaturesLicensed,
                            chatSupportLicensed          : chatSupportLicensed,
                            downloadQueryDataLicensed    : downloadQueryDataLicensed,
                            user                         : user,
                            rsetImportMappingTable       : rsetImportMappingTable,
                            dataLoadingFromFile          : dataLoadingFromFile,
                            isCalculationRunning         : isCalculationRunning
                    ]
                } else if (carbonDesignerOnlyLicensed) {
                    flash.warningAlert = "Rendering query not permitted by this license"
                    redirect controller: "entity", action: "show", id: parentEntity.id.toString()
                } else {
                    Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(entity.id.toString(), indicator.indicatorId)
                    List<ImportMapper> foundDesignImportMappers = []
                    List<ImportMapper> foundOperatingImportMappers = []
                    List<ImportMapper> designImportMappers = parentEntity.designImportMappers
                    List<ImportMapper> operatingImportMappers = parentEntity.operatingImportMappers

                    if (indicator?.indicatorUse?.equalsIgnoreCase('operating') && operatingImportMappers) {
                        foundOperatingImportMappers = operatingImportMappers?.findAll({
                            !it.compatibleIndicators || it.compatibleIndicators?.contains(indicator?.indicatorId)

                        })
                    } else if (indicator?.indicatorUse?.equalsIgnoreCase('design') && designImportMappers) {
                        foundDesignImportMappers = designImportMappers?.findAll({
                            !it.compatibleIndicators || it.compatibleIndicators?.contains(indicator?.indicatorId)
                        })
                    }
                    def showMandatory = !query?.disableHighlight
                    def task = taskService.getTaskByEntityAndQueryId(entity, query?.queryId)
                    def printable = params.printable
                    def entityName = entity?.operatingPeriodAndName ? entity.operatingPeriodAndName : entity?.name
                    def showHistory = false
                    def showOtherDesigns = false
                    def otherDesigns
                    IndicatorQuery indicatorQuery = indicator?.indicatorQueries?.find({ query?.queryId?.equals(it?.queryId) })
                    QueryApplyFilterOnCriteria filterOnCriteria = indicatorQuery?.applyFilterOnCriteria
                    String sessionFilterOnCriteria = session?.getAttribute("filterOnCriteria")

                    if (filterOnCriteria) {
                        if (sessionFilterOnCriteria && !filterOnCriteria.choices?.contains(sessionFilterOnCriteria)) {
                            session.removeAttribute("filterOnCriteria")
                        }

                        if (filterOnCriteria.mandatoryFilter && !(params.filterOnCriteria || sessionFilterOnCriteria)) {
                            flash.fadeErrorAlert = message(code: "query.mandatory_filter")
                        }

                        if (params.filterOnCriteria) {
                            session?.setAttribute("filterOnCriteria", params.filterOnCriteria)
                        }
                    } else {
                        session?.removeAttribute("filterOnCriteria")
                    }
                    Boolean workflowLicensed = licensedFeatures.find({ it.featureId == Feature.WORK_FLOW }) ? true : false
                    Boolean bimModelCheckerLicensed = licensedFeatures.find({ it.featureId == Feature.BIM_MODEL_CHECKER }) ? true : false
                    Boolean showOtherAnswers = licensedFeatures.find({ it.featureId == Feature.SHOW_OTHER_ANSWERS }) ? true : false
                    Boolean simulationToolLicensed = licensedFeatures.find({ it.featureId == Feature.CARBON_DESIGNER }) ? true : false
                    Boolean importMapperLicensed = licensedFeatures.find({ it.featureId == Feature.IFC_FROM_SIMPLE_BIM || it.featureId == Feature.IMPORT_FROM_FILE }) ? true : false
                    Boolean loadQueryFromDefault = licensedFeatures.find({ it.featureId == Feature.LOAD_QUERY_FROM_DEFAULT }) ? true : false
                    Boolean giveTaskLicensed = licensedFeatures.find({ it.featureId == Feature.GIVE_TASK }) ? true : false
                    Boolean ecoinventUncapped = licensedFeatures.find({ it.featureId == Feature.ECOINVENT_UNCAPPED }) ? true : false
                    Boolean groupMaterialsAllowed = featureService.isGroupMaterialFeatureAllowedByLicensedFeatures(query, licensedFeatures, indicator)
                    Boolean createConstructionsAllowed = (groupMaterialsAllowed && licensedFeatures.find({ it.featureId == Feature.CREATE_CONSTRUCTIONS })) ? true : false
                    Boolean publicGroupAllowed = (createConstructionsAllowed && licensedFeatures.find({ it.featureId == Feature.SUBMIT_PUBLIC_CONSTRUCTIONS })) ? true : false
                    Boolean sendMeEPDRequestLicensed = licensedFeatures.find({ it.featureId == Feature.SEND_ME_EPD_REQUEST }) ? true : false

                    List<ResourceType> constructionResourceTypes = []
                    List<String> unitForConstruction

                    if (groupMaterialsAllowed) {
                        constructionResourceTypes = resourceTypeService.getResourceSubTypesByResourceType("construction")
                        Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                        unitForConstruction = queryService.getAllowedUnitsByQueryAndUserSettings(constructionCreationQuery, user)
                    }


                    if (showOtherAnswers && EntityClass.DESIGN.toString().equals(entity?.entityClass) && parentEntity.getChildCount(EntityClass.DESIGN.toString()) > 1 && !query.hidePreviousAnswers) {
                        showOtherDesigns = true
                    } else if (EntityClass.OPERATING_PERIOD.toString().equals(entity?.entityClass) && parentEntity.getChildCount(EntityClass.OPERATING_PERIOD.toString()) > 1 && !query.hidePreviousAnswers) {
                        showHistory = true
                    }

                    def monthlyInputEnabled
                    def quarterlyInputEnabled
                    Boolean allowMonthlyData = query?.allowMonthlyData && EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass) && licensedFeatures.find({ it.featureId == Feature.MONTHLY_DATA })
                    Boolean allowQuarterlyData = query?.allowMonthlyData && EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass) && licensedFeatures.find({ it.featureId == Feature.QUARTERLY_DATA })

                    if (allowMonthlyData || allowQuarterlyData) {
                        if (allowQuarterlyData) {
                            quarterlyInputEnabled = entity.quarterlyInputEnabledByQuery?.get(query.queryId)
                        }

                        if (indicator?.requireMonthly && !entity.monthlyInputEnabledByQuery?.get(query.queryId)) {
                            monthlyInPutRequireMonthly(parentEntity?.id?.toString(), entity.id?.toString(), indicator, queryId)
                        }

                        if (allowMonthlyData && !quarterlyInputEnabled) {
                            monthlyInputEnabled = entity.monthlyInputEnabledByQuery?.get(query.queryId)
                        }
                    }
                    List<QueryFilter> supportedFilters = query.supportedFilters
                    QueryFilter resourceTypeFilter = supportedFilters?.find({ it.resourceAttribute.equals("resourceType") })
                    QueryFilter areaFilter = supportedFilters?.find({ it.resourceAttribute.equals("areas") })

                    Account account = userService.getAccount(user)
                    Boolean compareDataAllowedForMatSpecQuery = query.queryId.equalsIgnoreCase("materialSpecifier") && featuresAllowed.get(Feature.COMPARE_MATERIAL)
                    Boolean compareMaterialsAllowed = indicatorService.compareDataLicensedAndCompatible(featuresAllowed, query, indicator)
                    Entity compareEntity
                    if (compareMaterialsAllowed && !entity.isMaterialSpecifierEntity) {
                        compareEntity = entityService.getMaterialSpecifierEntity(parentEntity)
                    }

                    def modifiable = params.ifcImport != null || (parentEntity?.modifiable && validLicenses && (validLicenses.find({ it?.licensedIndicatorIds?.contains(indicator?.indicatorId) }) || compareDataAllowedForMatSpecQuery) && !parentEntity?.readonlyIndicatorIds?.contains(indicator?.indicatorId))
                    Boolean preventChanges = indicator?.preventChanges(query)
                    String unitSystem = userService.getCurrentUser(Boolean.FALSE)?.unitSystem

                    List<ProductDataList> productDataList = productDataListService.getProductDataListByAccountId(account?.id?.toString())

                    List<Question> allQueryQuestions = query.allQuestions
                    Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                    List additionalQuestionQuestions = additionalQuestionsQuery?.getAllQuestions()
                    List<Question> queryAdditionalQuestions = []
                    List<Question> showTypeAheadQuestions = []
                    Question showInMappingQuestion = null
                    Question privateClassificationQuestion = null
                    List<AccountImages> brandImages = []
                    if (user.internalUseRoles) {
                        brandImages = accountImagesService.getAllAccountImagesByType(Constants.AccountImageType.BRANDINGIMAGE.toString())
                    } else {
                        brandImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id?.toString(), Constants.AccountImageType.BRANDINGIMAGE.toString())
                    }
                    allQueryQuestions?.each { Question question ->
                        question.getAdditionalQuestions(indicator, additionalQuestionsQuery, additionalQuestionQuestions, parentEntity)?.each { Question additionalQuestion ->
                            if (!queryAdditionalQuestions.contains(additionalQuestion)) {
                                queryAdditionalQuestions.add(additionalQuestion)
                            }

                            if (additionalQuestion?.privateClassificationQuestion) {
                                privateClassificationQuestion = additionalQuestion
                            }

                            if (additionalQuestion?.showInMapping) {
                                showInMappingQuestion = additionalQuestion
                            }
                        }

                        if (question?.showTypeahead) {
                            showTypeAheadQuestions.add(question)
                        }

                        Map<String, List<Object>> organisationResourceFilterCriteria = question?.getOrganisationResourceFilterCriteria(indicator, parentEntity)
                        if (organisationResourceFilterCriteria) {
                            if (!account || (account && !productDataList) && !ecoinventUncapped) {
                                flash.warningAlert = "${message(code: "pdl.query_warning")}"
                            }
                        }
                    }
                    Boolean automaticCostLicensed
                    String unitCostCurrency
                    String totalCostCurrency

                    if (indicator?.isCostCalculationAllowed) {
                        Question unitCostQuestion = questionService.getQuestion(additionalQuestionsQuery, "costPerUnit")
                        Question totalCostQuestion = questionService.getQuestion(additionalQuestionsQuery, "totalCost")
                        unitCostCurrency = valueReferenceService.getValueForEntity(unitCostQuestion?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                        totalCostCurrency = valueReferenceService.getValueForEntity(totalCostQuestion?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                        automaticCostLicensed = licensedFeatures.find({ it.featureId == Feature.AUTOMATIC_COST_GENERATION }) ? true : false

                        if (!automaticCostLicensed && !queryAdditionalQuestions.isEmpty() && CollectionUtils.containsAny(queryAdditionalQuestions.collect({ it.questionId }), ["costPerUnit", "totalCost"])) {
                            flash.warningAlert = "${message(code: "query.automatic_cost_license")}"
                        }
                    }

                    List<Document> queriesForNavBar = queryService.getQueriesForNavBar(parentEntity, indicator, licensedFeatures, Boolean.TRUE)
                    List<Document> allowedQueries = []

                    if (queriesForNavBar) {
                        List<String> projectLevelQueries = indicator.indicatorQueries?.findAll({ it.projectLevel })?.collect({ it.queryId })
                        allowedQueries = projectLevelQueries ? queriesForNavBar.findAll({ !projectLevelQueries.contains(it.queryId) }) : queriesForNavBar
                    }
                    Map<String, List<Document>> additionalQuestionResources = [:]
                    Map<String, Boolean> additionalQuestionLicensed = [:]
                    Map<String, Boolean> additionalQuestionShowAsDisabled = [:]
                    Map<String, List<Question>> groupedQuestionsPerAdditionalQuestion = [:]
                    //Map<String, Boolean> inGroupedQuestion = [:]

                    if (queryAdditionalQuestions) {
                        queryAdditionalQuestions.each { Question additionalQuestion ->
                            additionalQuestionService.putAdditionalQuestionResourcesToMap(additionalQuestion, additionalQuestionResources, indicator, queryId, session)
                            licenseService.putLicensedStatusOfQuestionToMap(additionalQuestion, additionalQuestionLicensed, licensedFeatureIds, indicator, additionalQuestionShowAsDisabled)

                            if (additionalQuestion.groupedQuestionIds) {
                                additionalQuestion.groupedQuestionIds.each { String questionId ->
                                    Question groupedQuestion = queryAdditionalQuestions.find({ it.questionId == questionId })

                                    if (groupedQuestion) {
                                        def existing = groupedQuestionsPerAdditionalQuestion.get(additionalQuestion.questionId)

                                        if (existing) {
                                            existing.add(groupedQuestion)
                                        } else {
                                            existing = [groupedQuestion]
                                        }
                                        groupedQuestionsPerAdditionalQuestion.put(additionalQuestion.questionId, existing)
                                    }
                                }
                            }
                            //Boolean inGrouped = additionalQuestionQuestions.find({ Question q -> q.groupedQuestionIds && q.groupedQuestionIds.contains(question.questionId) }) ? true : false
                            //inGroupedQuestion.put(additionalQuestion.questionId, inGrouped)
                        }
                    }
                    List<Question> allQuestionsForMoving = []

                    if (query && query.sections?.size() > 1 && query.allowedFeatures?.contains("moveMaterials")) {
                        allQuestionsForMoving = allQueryQuestions
                        Map<String, List<String>> hideQuestions = indicator.hideQuestions
                        if (hideQuestions) {
                            hideQuestions.each { String key, List<String> values ->
                                allQuestionsForMoving.removeIf({ key.equals(it.sectionId) && (values.isEmpty() || values.contains(it.questionId)) })
                            }
                        }
                    }

                    boolean existTransportationMethod = false
                    if ("buildingTransportQuery".equals(query.queryId)) {
                        def datasetCheck = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, query.queryId, "scenario", 'regionalTransportPattern')
                        if (datasetCheck) {
                            existTransportationMethod = true
                        }
                    }
                    List<HelpConfiguration> helpConfigurations = helpConfigurationService.getHelpConfigurationByTargetPageAndIndicatorIdAndQueryId("Query", indicator?.indicatorId, queryId, Boolean.TRUE) ?: helpConfigurationService.getHelpConfigurationByTargetPageAndQueryId("Query", queryId)
                    Boolean showRenderingTimes = userService.getShowDevDebugNotifications(user?.enableDevDebugNotifications)

                    //***************************************************************************
                    //WorkFlow
                    //***************************************************************************
                    WorkFlowForEntity workFlowForEntity
                    String defaultIndicatorId = indicator?.indicatorId

                    if (workflowLicensed && parentEntity && defaultIndicatorId) {
                        workFlowForEntity = workFlowService.loadWorkFlowForEntity(parentEntity, defaultIndicatorId)
                    }
                    //***************************************************************************
                    //ResultBar
                    //***************************************************************************


                    //***************************************************************************
                    //SIMULATION TOOL
                    //***************************************************************************


                    Boolean draftPresent = simulationToolService.isDraftPresent(parentEntity?.id?.toString(), entity?.id?.toString())
                    //***************************************************************************
                    //FRAME STATUS
                    //***************************************************************************
                    ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

                    FrameStatus frameStatus = workFlowService.loadFrame(user, helpConfigurations, channelFeature, workFlowForEntity, true)

                    //***************************************************************************

                    //***************************************************************************
                    //Scope Checker Disable Section
                    //***************************************************************************
                    Boolean isBuildindMaterialQuery = (query?.queryId == "buildingMaterialsQuery")
                    List<String> datasetSectionIds = entity.datasets?.collect({ it.sectionId })
                    List<String> disabledSections = entity.disabledSections

                    if (disabledSections == null) {
                        disabledSections = entityService.resolveDisabledSections(entity)

                        try {
                            Entity.collection.updateOne(["_id": entity.id], [$set: [disabledSections: disabledSections]])
                        } catch (Exception e) {
                            log.info("Whoops: ${e}")
                        }

                        if (disabledSections && datasetSectionIds) {
                            disabledSections.removeAll(datasetSectionIds)
                        }
                    } else if (disabledSections && datasetSectionIds) {
                        disabledSections.removeAll(datasetSectionIds)
                    }

                    Boolean lcaCheckerAllowed = parentEntity?.lcaCheckerAllowed && indicator?.enableLcaChecker

                    //***************************************************************************
                    String defaultLocalCompCountry = datasetService.getDefaultLocalCompensationCountry(parentEntity)
                    String projectCountry = parentEntity?.datasets?.find({ d -> d.questionId == "country" })?.answerIds?.get(0)

                    String errorFromCheck = entityService.queryModifiable(parentEntity, entity, indicator, validLicenses, compareDataAllowedForMatSpecQuery)
                    if (errorFromCheck) {
                        if (errorFromCheck == "readonly") {
                            flash.warningAlert = "Data shown in read only mode. You lack privileges to use this tool for editing purposes. Please procure or check out a corresponding license or add-on."
                        }
                        modifiable = false
                        userReadOnlyQuery = true
                    }

                    // Different tools allow different filters in the same query so need to clear unusable filters from user
                    userService.handleUnusableFilters(session, user, indicatorQuery, entity, showTypeAheadQuestions, indicator, query)
                    Double secondsTaken = (System.currentTimeMillis() - now) / 1000


                    List<IndicatorQuery> projectLevelQueries = indicator.indicatorQueries?.findAll({ it.projectLevel })

                    String constructionCreatedMessage = session?.getAttribute("privateConstructionCreated")
                    if (constructionCreatedMessage) {
                        session.removeAttribute("privateConstructionCreated")
                    }

                    List<String> newlyAddedDatasets = session?.getAttribute("newlyAddedDatasets")
                    if (newlyAddedDatasets) {
                        session.removeAttribute("newlyAddedDatasets")
                    }

                    if (projectLevelQueries && projectLevelQueries.find({ !parentEntity?.queryReady?.get(it.queryId) })) {
                        String links

                        projectLevelQueries.each {
                            if (!parentEntity?.queryReady?.get(it.queryId)) {
                                Query q = queryService.getQueryByQueryId(it.queryId, true)

                                if (q) {
                                    Map params = [entityId: parentEntity?.id?.toString(), queryId: q.queryId, projectLevel: true, noDefaults: true]

                                    if (com.bionova.optimi.construction.Constants.IndicatorUse.OPERATING.toString().equals(indicator.indicatorUse)) {
                                        params.put("operatingLevelParamQuery", true)
                                    } else {
                                        params.put("designLevelParamQuery", true)
                                    }

                                    if (links) {
                                        links = "${links}, ${opt.link(controller: "query", action: "form", params: params) { "${q.localizedName}" }}"
                                    } else {
                                        links = "${opt.link(controller: "query", action: "form", params: params) { "${q.localizedName}" }}"
                                    }
                                }
                            }
                        }

                        if (links) {
                            flash.errorAlert = "${message(code: "query.parametersNotReady")} ${links}"
                        }
                    }
                    Boolean showResultBar = licensedFeatures.find({ it.featureId == Feature.RESULT_SIDEBAR }) ? true : false
                    Boolean commaThousandSeparation = user?.localeString ? !User.LocaleString.EUROPEAN.locale.equals(user.localeString) : false
                    boolean isPlanetaryOnly = validLicenses?.findAll({ !it.planetary })?.size() > 0 ? false : true
                    String lcaModel = parentEntity?.lcaModel
                    String costCalculationMethod = parentEntity?.costCalculationMethod
                    String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)
                    String compareBasketForThisProject = session?.getAttribute("${parentEntity?.id}CompareResourceBasket") ?: ""
                    // Check if resource row limit reached
                    String maxRowLimitPerDesignReached = indicator?.getDesignMaxRowsReached(entity?.id?.toString()) ? "${message(code: "query_row_limit_reached")} (${indicator.maxRowLimitPerDesign} ${message(code: "importMapper.combiner.info2")})" : null
                    //Todo: FEC TOOL WARNING TO BE MOVED TO SERVICE
                    if (FrenchConstants.RSET_TOOLS.contains(indicator.indicatorId)) {
                        List<String> validZones = datasetService.getValidFECZones(entity.datasets, null, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE)
                        String errorMessageForFECBuildingDesc = ""
                        if (validZones?.size() > 1 && validZones?.contains("00")) {
                            errorMessageForFECBuildingDesc = "${errorMessageForFECBuildingDesc}${message(code: "fec.building_description_mixup_zones")}.<br>"
                        }

                        if (validZones?.size() > 1) {
                            Set<String> nonDuplicatedZone = validZones?.toSet()

                            if (validZones?.size() != nonDuplicatedZone?.size()) {
                                errorMessageForFECBuildingDesc = "${errorMessageForFECBuildingDesc}${message(code: "fec.building_description_duplication")}."
                            }
                        }
                        if (errorMessageForFECBuildingDesc) {
                            flash.errorAlert = errorMessageForFECBuildingDesc
                        }

                        flash.warningAlert = "${message(code: 'fec.one_zone_assigned_resource_needed')}"
                    }

                    // this param comes from startNewProject() when user sets in quick start templates
                    if (params.boolean(Constants.PARAM_EXPAND_ALL_SECTIONS_IN_QUERY)) {
                        queryService.expandAllSectionsInQuery(query)
                    }

                    // check datasets if design have verified ones
                    querySectionService.setHasVerifiedDatasetsInSections(query, entity)

                    // for showing verification points feature
                    boolean isIndicatorHavingVerificationPoints = showAndJumpToVPointLicensed ? verificationPointService.isIndicatorHavingVerificationPoints(indicator, parentEntity) : false
                    boolean isQueryHavingVerificationPoints = isIndicatorHavingVerificationPoints ? verificationPointService.isQueryHavingVerificationPoints(query, indicator) : false
                    Map<String, String> questionsWithAdditionalCalculationScripts = queryService.groupAdditionalCalculationScriptsByQuestions(query)
                    EolProcessCache eolProcessCache = EolProcessCache.init()
                    Map<String, List<String>> additionalCalcParamsForCurrentQuery = queryService.getAdditionalCalcFromQueryForParticularQuery(query);

                    log.info("${parentEntity?.name} / ${entity?.name}: Got all stuff in : ${System.currentTimeMillis() - now} ms, rendering ${entity?.datasets?.findAll({ queryId.equals(it.queryId) })?.size()} datasets for query: ${queryId}.")

                    return [indicator                                : indicator,
                            parentEntity                             : parentEntity,
                            entity                                   : entity,
                            query                                    : query,
                            task                                     : task,
                            foundDesignImportMappers                 : foundDesignImportMappers,
                            foundOperatingImportMappers              : foundOperatingImportMappers,
                            entityName                               : entityName,
                            printable                                : printable,
                            showMandatory                            : showMandatory,
                            queryForm                                : true,
                            unitSystem                               : user?.unitSystem,
                            simulationToolLicensed                   : simulationToolLicensed,
                            showResultBar                            : showResultBar,
                            showHistory                              : showHistory,
                            showOtherDesigns                         : showOtherDesigns,
                            otherDesigns                             : otherDesigns,
                            secondsTaken                             : secondsTaken,
                            showRenderingTimes                       : showRenderingTimes,
                            costCalculationMethod                    : costCalculationMethod,
                            allowMonthlyData                         : allowMonthlyData,
                            allowQuarterlyData                       : allowQuarterlyData,
                            monthlyInputEnabled                      : monthlyInputEnabled,
                            quarterlyInputEnabled                    : quarterlyInputEnabled,
                            compareEntity                            : compareEntity,
                            modifiable                               : modifiable,
                            filterOnCriteria                         : filterOnCriteria,
                            unitSystem                               : unitSystem,
                            preventChanges                           : preventChanges,
                            supportedFilters                         : supportedFilters,
                            showInMappingQuestion                    : showInMappingQuestion,
                            privateClassificationQuestion            : privateClassificationQuestion,
                            user                                     : user,
                            account                                  : account,
                            userReadOnlyQuery                        : userReadOnlyQuery,
                            resourceTypeFilter                       : resourceTypeFilter,
                            areaFilter                               : areaFilter,
                            additionalQuestionLicensed               : additionalQuestionLicensed,
                            dataLoadingFeature                       : dataLoadingFeature,
                            projectLevel                             : projectLevel,
                            newProject                               : newProject,
                            bimModelCheckerLicensed                  : bimModelCheckerLicensed,
                            additionalQuestionShowAsDisabled         : additionalQuestionShowAsDisabled,
                            periodLevelParamQuery                    : periodLevelParamQuery,
                            designLevelParamQuery                    : designLevelParamQuery,
                            allowedQueries                           : allowedQueries,
                            unitCostCurrency                         : unitCostCurrency,
                            totalCostCurrency                        : totalCostCurrency,
                            brandImages                              : brandImages,
                            splitOrChangeLicensed                    : splitOrChangeLicensed,
                            automaticCostLicensed                    : automaticCostLicensed,
                            additionalQuestionResources              : additionalQuestionResources,
                            helpConfigurations                       : helpConfigurations,
                            allQuestionsForMoving                    : allQuestionsForMoving,
                            queryId                                  : queryId,
                            workFlowForEntity                        : workFlowForEntity,
                            betaFeaturesLicensed                     : betaFeaturesLicensed,
                            chatSupportLicensed                      : chatSupportLicensed,
                            downloadQueryDataLicensed                : downloadQueryDataLicensed,
                            constructionResourceTypes                : constructionResourceTypes,
                            frameStatus                              : frameStatus,
                            existTranportMethod                      : existTransportationMethod,
                            defaultLocalCompCountry                  : defaultLocalCompCountry,
                            importMapperLicensed                     : importMapperLicensed,
                            groupedQuestionsPerAdditionalQuestion    : groupedQuestionsPerAdditionalQuestion,
                            isBuildindMaterialQuery                  : isBuildindMaterialQuery,
                            disabledSections                         : disabledSections,
                            lcaCheckerAllowed                        : lcaCheckerAllowed,
                            projectCountry                           : projectCountry,
                            loadQueryFromDefault                     : loadQueryFromDefault,
                            commaThousandSeparation                  : commaThousandSeparation,
                            giveTaskLicensed                         : giveTaskLicensed,
                            draftPresent                             : draftPresent,
                            lcaModel                                 : lcaModel,
                            groupMaterialsAllowed                    : groupMaterialsAllowed,
                            isPlanetaryOnly                          : isPlanetaryOnly,
                            newlyAddedDatasets                       : newlyAddedDatasets,
                            sendMeEPDRequestLicensed                 : sendMeEPDRequestLicensed,
                            createConstructionsAllowed               : createConstructionsAllowed,
                            publicGroupAllowed                       : publicGroupAllowed,
                            constructionCreatedMessage               : constructionCreatedMessage,
                            maxRowLimitPerDesignReached              : maxRowLimitPerDesignReached,
                            localCompensationMethodVersion           : localCompensationMethodVersion,
                            compareMaterialsAllowed                  : compareMaterialsAllowed,
                            compareDataAllowedForMatSpecQuery        : compareDataAllowedForMatSpecQuery,
                            compareBasketForThisProject              : compareBasketForThisProject,
                            unitForConstruction                      : unitForConstruction,
                            unlockVerifiedDatasetLicensed            : unlockVerifiedDatasetLicensed,
                            isQueryHavingVerificationPoints          : isQueryHavingVerificationPoints,
                            isIndicatorHavingVerificationPoints      : isIndicatorHavingVerificationPoints,
                            showAndJumpToVPointLicensed              : showAndJumpToVPointLicensed,
                            scrollToVP                               : params.scrollToVP,
                            questionsWithAdditionalCalculationScripts: questionsWithAdditionalCalculationScripts,
                            isCalculationRunning                     : isCalculationRunning,
                            eolProcessCache                          : eolProcessCache,
                            doNotTriggerCalcQuestionIdsString        : questionService.getRenderStringDoNotTriggerCalcQuestionIds(allQueryQuestions + queryAdditionalQuestions),
                            additionalCalcParamsForCurrentQuery      : additionalCalcParamsForCurrentQuery
                    ]
                }
            } else {
                loggerUtil.error(log, "Query ${queryId} not found or it is not allowed for indicator: ${indicator?.indicatorId}")
                redirect controller: "main"
            }
        } else {
            loggerUtil.warn(log, "Missing required attributes parentEntity, entity or indicator")
            flash.fadeErrorAlert = message(code: "query.entity_deleted.warning")
            redirect controller: "main"
        }
    }

    def copyAnswers() {
        String targetEntityId = params.targetEntityId
        String fromEntityId = params.fromEntityId
        String queryId = params.queryId
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        Integer ok = datasetService.copyDatasetsByQueryId(fromEntityId, targetEntityId, indicatorId, queryId, true)

        if (ok == 1) {
            flash.fadeSuccessAlert = message(code: "query.copy_answers.ok")
        } else if (ok == 2) {
            flash.fadeErrorAlert = message(code: "query.copy_answers.too_many_rows")
        } else if (ok == 3) {
            flash.fadeErrorAlert = message(code: "query.copy_answers.no_data_found")
        } else {
            flash.fadeErrorAlert = message(code: "query.copy_answers.error")
        }
        redirect action: "form", params: [entityId: entityId, childEntityId: targetEntityId, indicatorId: indicatorId, queryId: queryId]
    }

    def enableMonthlyInput() {
        def childEntityId = params.childEntityId
        def entityId = params.entityId
        def indicatorId = params.indicatorId
        def queryId = params.queryId

        if (childEntityId && entityId && indicatorId && queryId) {
            Entity childEntity = entityService.getEntityById(childEntityId, session)

            if (childEntity.monthlyInputEnabledByQuery) {
                childEntity.monthlyInputEnabledByQuery.put(queryId, Boolean.TRUE)
            } else {
                childEntity.monthlyInputEnabledByQuery = [(queryId): Boolean.TRUE]
            }
            childEntity.quarterlyInputEnabledByQuery?.remove(queryId)

            childEntity.datasets?.findAll({ queryId.equals(it.queryId) })?.each { Dataset dataset ->
                dataset.quarterlyAnswers = null
            }
            entityService.updateEntity(childEntity)
            redirect action: "form", params: [childEntityId: childEntityId, entityId: entityId, indicatorId: indicatorId,
                                              queryId      : queryId]
        } else {
            redirect controller: "main"
        }
    }

    def monthlyInPutRequireMonthly(String parentId, String childEntityId, Indicator indicator, String queryId) {

        if (childEntityId && indicator && queryId && parentId) {
            Entity childEntity = entityService.getEntityById(childEntityId, session)
            if (childEntity) {
                if (childEntity.monthlyInputEnabledByQuery) {
                    childEntity.monthlyInputEnabledByQuery.put(queryId, Boolean.TRUE)
                } else {
                    childEntity.monthlyInputEnabledByQuery = [(queryId): Boolean.TRUE]
                }
                childEntity.quarterlyInputEnabledByQuery?.remove(queryId)

                childEntity.datasets?.findAll({ queryId.equals(it.queryId) })?.each { Dataset dataset ->
                    dataset.quarterlyAnswers = null
                }
                entityService.updateEntity(childEntity)
                redirect action: "form", params: [childEntityId: childEntityId, entityId: parentId, indicatorId: indicator?.indicatorId,
                                                  queryId      : queryId]
            }
        } else {
            redirect controller: "main"
        }
    }

    def enableQuarterlyInput() {
        def childEntityId = params.childEntityId
        def entityId = params.entityId
        def indicatorId = params.indicatorId
        def queryId = params.queryId

        if (childEntityId && entityId && indicatorId && queryId) {
            Entity childEntity = entityService.getEntityById(childEntityId, session)

            if (childEntity.quarterlyInputEnabledByQuery) {
                childEntity.quarterlyInputEnabledByQuery.put(queryId, Boolean.TRUE)
            } else {
                childEntity.quarterlyInputEnabledByQuery = [(queryId): Boolean.TRUE]
            }
            childEntity.monthlyInputEnabledByQuery?.remove(queryId)

            childEntity.datasets?.findAll({ queryId.equals(it.queryId) })?.each { Dataset dataset ->
                dataset.monthlyAnswers = null
            }
            entityService.updateEntity(childEntity)
            redirect action: "form", params: [childEntityId: childEntityId, entityId: entityId, indicatorId: indicatorId,
                                              queryId      : queryId]
        } else {
            redirect controller: "main"
        }
    }

    def enableAnnualInput() {
        def childEntityId = params.childEntityId
        def entityId = params.entityId
        def indicatorId = params.indicatorId
        def queryId = params.queryId

        if (childEntityId && entityId && indicatorId && queryId) {
            Entity childEntity = entityService.getEntityById(childEntityId, session)
            childEntity.monthlyInputEnabledByQuery?.remove(queryId)
            childEntity.quarterlyInputEnabledByQuery?.remove(queryId)

            childEntity.datasets?.findAll({ queryId.equals(it.queryId) })?.each { Dataset dataset ->
                dataset.monthlyAnswers = null
                dataset.quarterlyAnswers = null
            }
            entityService.updateEntity(childEntity)
            redirect action: "form", params: [childEntityId: childEntityId, entityId: entityId, indicatorId: indicatorId,
                                              queryId      : queryId]
        } else {
            redirect controller: "main"
        }
    }
    // TODO: Will be run only for project level stuff.
    // HUNG: DO NOT REMOVE LOGIC FOR BOTH PROJECT AND DESIGN LEVEL. DESIGN LEVEL IS STILL USED FOR CHANGING TABS
    def save() {
        session?.removeAttribute("filterOnCriteria")
        session?.removeAttribute("deletedDatasets")
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String queryId = params.queryId
        String enableScopeSectionId = params.enableScopeSectionId
        Boolean projectLevel = params.boolean("projectLevel")
        Boolean newProject = params.boolean("newProject")

        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
        Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityById(entityId, session)
        Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(projectLevel ? entityId : childEntityId, indicatorId)

        if (isCalculationRunning || queryParamsService.skipTriggeringCalculationAndSaveFromParams(params)) {
            flashService.setPermWarningAlert(g.message(code: "entity.calculation.running"))
            log.warn("Skipped calculation because it's already running.")

            if (projectLevel) {
                return redirect(action: "form", params: [entityId             : entityId, queryId: queryId, projectLevel: projectLevel,
                                                         newProject           : newProject, designLevelParamQuery: params.boolean("designLevelParamQuery"),
                                                         periodLevelParamQuery: params.boolean("periodLevelParamQuery")])
            } else {
                if (params.targetQueryId) {
                    return  redirect(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: entity?.id, queryId: params.targetQueryId])
                } else if (params.results && EntityClass.DESIGN.toString() == entity?.entityClass) {
                    return  redirect (controller: "design", action: "results", params: [entityId: entityId, childEntityId: entity?.id, indicatorId: indicatorId])
                } else if (params.results && EntityClass.OPERATING_PERIOD.toString() == entity?.entityClass) {
                    return redirect (controller: "operatingPeriod", action: "results", params: [entityId: entityId, childEntityId: entity?.id, indicatorId: indicatorId])
                } else {
                    return  redirect(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: entity?.id, queryId: queryId])
                }
            }
        }

        Query originalQuery = queryService.getQueryByQueryId(queryId, true)
        boolean resultsOutOfDate = params.skipCalculation != null && params.skipCalculation.toBoolean() &&
                !Boolean.TRUE.equals(originalQuery?.noCalculationData)
        boolean skipCalculation = Boolean.TRUE.equals(originalQuery?.noCalculationData) || resultsOutOfDate ||
                queryParamsService.skipTriggeringCalculationFromParams(params)

        if (skipCalculation) {
            log.info("Query ${queryId} for indicatior ${indicatorId}, and entity ${entity?.id?.toString()} will be saved with no calculations.")
        }

        if (projectLevel) {
            //if some changes were done on project level we should recalculate existing calculationResults
            parentEntity = queryPreparationService.saveProjectLevelQuery(parentEntity, queryId, skipCalculation, resultsOutOfDate)
            CalculationProcess calculationProcess = calculationProcessService.createCalculationProcessAndSave(parentEntity.id?.toString(), null, null)
            entityService.recalculateParentEntityNew(parentEntity, queryId)
            calculationProcessService.deleteCalculationProcess(calculationProcess)
        } else if ((params.save || params.formChanged || enableScopeSectionId) && !entity?.locked) {
            queryPreparationService.saveDesignLevelQuery(entity,parentEntity, indicator, skipCalculation, resultsOutOfDate)
        }

        if (params.save || params.upload) {
            if (projectLevel) {
                if (parentEntity?.queryReady?.get(queryId)) {
                    if (newProject) {
                        String nextQueryId = null
                        List<String> projectLevelQueryIds = entityService.getProjectLevelQueryIdsForCurrentUser(parentEntity)

                        if (projectLevelQueryIds) {
                            // This is to check if there are other projectLevelQueries for the user to fill in before navigating to entity show
                            int index = projectLevelQueryIds.indexOf(queryId)
                            int indexes = projectLevelQueryIds.size() - 1

                            if (index > -1 && indexes > -1 && index < indexes) {
                                nextQueryId = projectLevelQueryIds.get(index + 1)
                            }
                        }

                        if (nextQueryId) {
                            chain(action: "form", params: [entityId             : entityId, queryId: nextQueryId, projectLevel: projectLevel,
                                                           newProject           : newProject, designLevelParamQuery: params.boolean("designLevelParamQuery"),
                                                           periodLevelParamQuery: params.boolean("periodLevelParamQuery")])
                        } else {
                            redirect controller: "entity", action: "show", params: [entityId: entityId]
                        }
                    } else {
                        redirect controller: "entity", action: "show", params: [entityId: entityId]
                    }
                } else {
                    flash.fadeErrorAlert = message(code: "query.missing_data")
                    chain(action: "form", params: [entityId             : entityId, queryId: queryId, projectLevel: projectLevel,
                                                   newProject           : newProject, designLevelParamQuery: params.boolean("designLevelParamQuery"),
                                                   periodLevelParamQuery: params.boolean("periodLevelParamQuery")])
                }
            } else {
                chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: entity?.id, queryId: queryId])
            }
        } else if (params.createPdf) {
            // createPdf doesnt exist here, why written like this? For now redirect to main, could be a deprecated feature
            //createPdf(entity, queryId, response)
            //chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: entity?.id, queryId: queryId])
            redirect controller: "main"
        } else if (params.targetQueryId) {
            chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: entity?.id, queryId: params.targetQueryId])
        } else if (params.results && EntityClass.DESIGN.toString() == entity?.entityClass) {
            redirect controller: "design", action: "results", params: [entityId: entityId, childEntityId: entity?.id, indicatorId: indicatorId]
        } else if (params.results && EntityClass.OPERATING_PERIOD.toString() == entity?.entityClass) {
            redirect controller: "operatingPeriod", action: "results", params: [entityId: entityId, childEntityId: entity?.id, indicatorId: indicatorId]
        } else if (params.compareMat && EntityClass.MATERIAL_SPECIFIER.toString() == entity?.entityClass) {
            redirect controller: "entity", action: "resourcesComparisonEndUser", params: [parentEntityId: parentEntity?.id, indicatorId: indicatorId]
        } else if (params.task) {
            redirect controller: "task", action: "form", params: [entityId: entityId, childEntityId: entity?.id, indicatorId: indicatorId, queryId: queryId]
        } else {
            redirect controller: "main"
        }

    }

    def saveQueryRemotely() {
        session?.removeAttribute("filterOnCriteria")
        session?.removeAttribute("deletedDatasets")
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String queryId = params.queryId
        String enableScopeSectionId = params.enableScopeSectionId
        Boolean projectLevel = params.boolean("projectLevel")

        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
        Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityById(entityId, session)
        Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(projectLevel ? entityId : childEntityId, indicatorId)
        String saveMessage = 'ok'

        if (isCalculationRunning) {
            flashService.setPermWarningAlert(g.message(code: "entity.calculation.running"))
            log.warn("Skipped calculation because it's already running.")
            return render([output: "error", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

        Query originalQuery = queryService.getQueryByQueryId(queryId, true)
        boolean resultsOutOfDate = params.skipCalculation != null && params.skipCalculation.toBoolean() &&
                !Boolean.TRUE.equals(originalQuery?.noCalculationData)
        boolean skipCalculation = Boolean.TRUE.equals(originalQuery?.noCalculationData) || resultsOutOfDate ||
                queryParamsService.skipTriggeringCalculationFromParams(params)

        if (skipCalculation) {
            log.debug("Query ${queryId} for indicatior ${indicatorId} will be saved with no calculations.")
        }

        try {
            //project level calculation not used in this method now (it's run in save() method), but let's keep it there for now
            if (projectLevel) {
                //if some changes were done on project level we should recalculate existing calculationResults
                parentEntity = queryPreparationService.saveProjectLevelQuery(parentEntity, queryId, skipCalculation, resultsOutOfDate)
                CalculationProcess calculationProcess = calculationProcessService.createCalculationProcessAndSave(parentEntity.id?.toString(), null, null)
                entityService.recalculateParentEntityNew(parentEntity, queryId)
                calculationProcessService.deleteCalculationProcess(calculationProcess)
            } else if ((params.save || params.formChanged || enableScopeSectionId) && !entity?.locked) {
                queryPreparationService.saveDesignLevelQuery(entity,parentEntity, indicator, skipCalculation, resultsOutOfDate)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Query controller: Error in saveQueryRemotely ", e)
            flash.fadeErrorAlert = g.message(code: "entity.calculation.error")
            saveMessage = "error"
        }

        render([output: saveMessage, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    // TODO: triggered for some features like 'move materials', calculation should be performed not in separate thread
    def saveQueryFormAjax() {
        long now = System.currentTimeMillis()
        String saveMessage = "saved"
        try {
            String entityId = params.entityId
            String childEntityId = params.childEntityId
            Boolean projectLevel = params.boolean("projectLevel")
            Entity parentEntity = entityService.getEntityById(entityId, session)
            Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
            String indicatorId = params.indicatorId

            String queryId = params.queryId
            Query originalQuery = queryService.getQueryByQueryId(queryId, true)
            boolean resultsOutOfDate = params.skipCalculation != null && params.skipCalculation.toBoolean() &&
                    !Boolean.TRUE.equals(originalQuery?.noCalculationData)
            boolean skipCalculation = Boolean.TRUE.equals(originalQuery?.noCalculationData) || resultsOutOfDate ||
                    queryParamsService.skipTriggeringCalculationFromParams(params)
            Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(projectLevel ? entityId : childEntityId, indicatorId)

            if (isCalculationRunning) {
                flashService.setPermWarningAlert(g.message(code: "entity.calculation.running"))
                return render([output: "error", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }

            if (skipCalculation) {
                log.debug("Query ${queryId} for indicatior ${indicatorId} will be saved with no calculations.")
            }

            if (resultsOutOfDate) {
                log.debug("Query ${queryId} for indicatior ${indicatorId} will be out of date.")
                String link = queryService.createResultsLink(parentEntity, childEntityId, indicatorId)
                saveMessage = g.message(code: 'query.saved',
                        args: [link])
                flash.fadeWarningAlert = g.message(code: 'query.saved', args: [link])
            }

            Indicator indicator = indicatorId ? indicatorService.getIndicatorByIndicatorId(indicatorId, true) : null
            if ((entity || projectLevel) && parentEntity) {
                if (projectLevel) {
                    parentEntity = datasetService.saveDatasetsFromParameters(params, request, entityId, null,
                            null, projectLevel, null, null, indicator, skipCalculation, resultsOutOfDate)
                    CalculationProcess calculationProcess = calculationProcessService.createCalculationProcessAndSave(parentEntity.id?.toString(), null, null)
                    entityService.recalculateParentEntity(parentEntity, queryId)
                    calculationProcessService.deleteCalculationProcess(calculationProcess)
                } else {
                    entity = datasetService.saveDatasetsFromParameters(params, request, childEntityId, null,
                            null, null, null, null, indicator, skipCalculation, resultsOutOfDate)
                    entityService.calculateEntity(entity, indicatorId, queryId, false, skipCalculation)
                }
            }

            loggerUtil.info(log, "Query controller: saveQueryFormAjax: ${System.currentTimeMillis() - now} ms")
            flashService.setInfoAlert("Query controller: saveQueryFormAjax: save query in ${System.currentTimeMillis() - now} ms", true)
        } catch (Exception e) {
            loggerUtil.error(log, "Query controller: Error in saveQueryFormAjax", e)
            flashService.setPermErrorAlert("Query controller: Error in saveQueryFormAjax ${e.getMessage()}", true)
            flashService.setPermErrorAlert(message(code: "unknownError"))
            saveMessage = "error"
        }

        render([output: saveMessage, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def reset() {
        def entityId = params.entityId
        def queryId = params.queryId
        def indicatorId = params.indicatorId
        chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, queryId: queryId])
    }

    def generateExcelFromQueryDatasets() {
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String queryId = params.queryId
        Entity entity = entityService.getEntityById(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

        if (entity && indicator) {
            Entity child = entityService.getChildEntity(entity, childEntityId)
            Query query = queryService.getQueryByIndicatorAndQueryId(indicator, queryId)

            if (child && query) {
                Set<Dataset> queryDatasets = datasetService.getDatasetsByEntityAndQueryId(child, queryId)

                if (queryDatasets) {
                    Workbook wb = queryService.generateExcel(queryDatasets, query, child, indicator, entity)

                    if (wb) {
                        response.contentType = 'application/vnd.ms-excel'
                        response.setHeader("Content-disposition", "attachment; filename=${entity.name}_${child.operatingPeriodAndName}_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                        OutputStream outputStream = response.getOutputStream()
                        wb.write(outputStream)
                        outputStream.flush()
                        outputStream.close()
                    } else {
                        flash.fadeErrorAlert = "Could not generate Excel from query datasets"
                        chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: childEntityId, queryId: queryId])
                    }
                } else {
                    flash.fadeErrorAlert = "No datasets to export"
                    chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: childEntityId, queryId: queryId])
                }
            }
        }
    }


    def generateAPIExcelFromQueryDatasets() {
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String queryId = "buildingMaterialsQuery"
        Entity entity = entityService.getEntityById(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

        if (entity && indicator) {
            Entity child = entityService.getChildEntity(entity, childEntityId)
            Query query = queryService.getQueryByIndicatorAndQueryId(indicator, queryId)

            if (child && query) {
                Set<Dataset> queryDatasets = datasetService.getDatasetsByEntityAndQueryId(child, queryId)

                if (queryDatasets && query) {
                    Workbook wb = queryService.generateAPIExcel(queryDatasets, query, indicator, child)

                    if (wb) {
                        response.contentType = 'application/vnd.ms-excel'
                        response.setHeader("Content-disposition", "attachment; filename=API_sample_Excel_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                        OutputStream outputStream = response.getOutputStream()
                        wb.write(outputStream)
                        outputStream.flush()
                        outputStream.close()
                    }
                } else {
                    flash.fadeErrorAlert = "No datasets to export"
                    chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: childEntityId, queryId: queryId])
                }
            }
        }
    }

    def getImpactsPerSection() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String unit = message(code: 'tons')
        Entity childEntity = entityService.readEntity(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Query query = queryService.getQueryByQueryId(queryId, Boolean.TRUE)
        String impacts = ""
        if (indicator && childEntity && sectionId && query) {
            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(childEntity?.parentEntityId ? childEntity?.parentById : childEntity)
            String displayRuleId = indicator.displayResult
            String massRuleId = indicator.massRuleId
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, displayRuleId, null)
            List<CalculationResult> massRuleIdResultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, massRuleId, null)
            List<CalculationResult> resultsForIndicatorFailover = childEntity.getCalculationResultObjects(indicator.indicatorId, "GWP_failover", null)


            if (resultsForIndicator || resultsForIndicatorFailover) {
                Map<String, Double> totalBySection = [:]
                Denominator overallDenominator = indicator?.resolveDenominators?.find({ "overallDenominator".equalsIgnoreCase(it.denominatorType) })
                // this is shit, get results for all sections even though it only uses one sectionId? todo: rewrite
                resultCategories?.findAll({ !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty() })?.each { ResultCategory resultCategory ->
                    query.getSections()?.each { QuerySection querySection ->
                        if (sectionId.equals(querySection.sectionId)) {
                            querySectionService.getAllUnderlyingQuestions(childEntity.entityClass, querySection)?.each { Question question ->
                                List<Dataset> foundDatasets = childEntity.datasets?.findAll({
                                    query.queryId.equals(it.queryId) && querySection.sectionId.equals(it.sectionId) && question.questionId.equals(it.questionId)
                                })?.toList()

                                if (foundDatasets) {
                                    foundDatasets.each { Dataset dataset ->
                                        Double result = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                resultCategory.resultCategoryId, displayRuleId,
                                                resultsForIndicator, false)

                                        if (!result && resultsForIndicatorFailover) {
                                            result = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                    resultCategory.resultCategoryId, "GWP_failover",
                                                    resultsForIndicatorFailover, false)
                                        }

                                        if (result) {
                                            Double existingValue = totalBySection.get(querySection.sectionId)

                                            if (!existingValue) {
                                                totalBySection.put(querySection.sectionId, result)
                                            } else {
                                                totalBySection.put(querySection.sectionId, result + existingValue)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Double totalPerSection

                if (totalBySection) {
                    totalPerSection = totalBySection?.get(sectionId)
                }

                Double totalGWP = (Double) childEntity.getTotalResult(indicator.indicatorId, displayRuleId)

                if (!totalGWP) {
                    Double totalGWPfailover = (Double) childEntity.getTotalResult(indicator.indicatorId, "GWP_failover")

                    if (totalGWPfailover) {
                        totalGWP = totalGWPfailover
                    }
                }

                if (totalPerSection && totalGWP) {
                    if (overallDenominator) {
                        Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                        if (overallDenomValue != null) {
                            totalPerSection = totalPerSection * overallDenomValue
                            totalGWP = totalGWP * overallDenomValue
                        }
                    }
                    Boolean dontDivide = Boolean.FALSE
                    Integer percentageValue
                    Double tonnes

                    if (("traciGWP_tn").equalsIgnoreCase(displayRuleId) || ("GWP_tn").equalsIgnoreCase(displayRuleId)) {
                        dontDivide = Boolean.TRUE
                    } else if (totalPerSection < 100) {
                        dontDivide = Boolean.TRUE
                        unit = "kg"
                    }

                    if (!dontDivide) {
                        tonnes = totalPerSection / 1000
                    } else {
                        tonnes = totalPerSection
                    }
                    percentageValue = Math.round((totalPerSection / totalGWP) * 100)?.intValue()

                    if (tonnes) {
                        def roundedTonnes
                        if (tonnes > 1) {
                            roundedTonnes = Math.round(tonnes)?.intValue()
                        } else {
                            roundedTonnes = tonnes.round(2)
                        }
                        impacts = "<div class='questionHeadText'><div class=\'co2CloudContainer smoothTip tooltip--right\' data-tooltip=\"${message(code: 'carbon_cloud_info')}\"><i class='fa fa-cloud queryImpactCloud'></i></div>  ${roundedTonnes ? roundedTonnes : 0} ${unit} CO<sub>2</sub>e</div>"
                    }
                    if (percentageValue) {
                        impacts = impacts + "<div class='questionHeadText'>- ${percentageValue ? percentageValue : 0} %</div>"
                    }

                }
            }
            if (massRuleId) {
                impacts = getMassToDisplayPerSection(indicator, massRuleId, impacts, massRuleIdResultsForIndicator, resultCategories, query, sectionId, childEntity)
            }
        }
        render([output: impacts, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    /**
     * Gets mass per section string to display in the front end
     * @param indicator
     * @param massRuleId
     * @param impacts
     * @param massRuleIdResultsForIndicator
     * @param resultCategories
     * @param query
     * @param sectionId
     * @param childEntity
     * @return
     */

    private String getMassToDisplayPerSection(Indicator indicator, String massRuleId, String impacts, List<CalculationResult> massRuleIdResultsForIndicator, List<ResultCategory> resultCategories, Query query, String sectionId, Entity childEntity) {
        String unit = "kg"
        try {
            if (massRuleIdResultsForIndicator) {
                Map<String, Double> totalMassBySection = [:]
                resultCategories?.findAll({ !it.ignoreFromTotals?.contains(massRuleId) && !it.ignoreFromTotals?.isEmpty() })?.each { ResultCategory resultCategory ->
                    query.getSections()?.each { QuerySection querySection ->
                        if (sectionId.equals(querySection.sectionId)) {
                            querySectionService.getAllUnderlyingQuestions(childEntity.entityClass, querySection)?.each { Question question ->
                                List<Dataset> foundDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, query.queryId , querySection.sectionId , question.questionId)
                                if (foundDatasets) {
                                    foundDatasets.each { Dataset dataset ->
                                        Double result = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                resultCategory.resultCategoryId, massRuleId,
                                                massRuleIdResultsForIndicator, false)

                                        if (result) {
                                            Double existingValue = totalMassBySection.get(querySection.sectionId)

                                            if (!existingValue) {
                                                totalMassBySection.put(querySection.sectionId, result)
                                            } else {
                                                totalMassBySection.put(querySection.sectionId, result + existingValue)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Double totalMassPerSection

                if (totalMassBySection) {
                    totalMassPerSection = totalMassBySection?.get(sectionId)
                }
                Double totalMass = (Double) childEntity.getTotalResult(indicator.indicatorId, massRuleId)
                if (totalMassPerSection) {
                    Integer percentageValue
                    Double tonnes = totalMassPerSection
                    if (tonnes) {
                        def roundedTonnes
                        if (tonnes > 1) {
                            roundedTonnes = Math.round(tonnes)?.intValue()
                        } else {
                            roundedTonnes = tonnes.round(2)
                        }
                        impacts = impacts + "&nbsp<div class='questionHeadText'><div class=\'co2CloudContainer smoothTip tooltip--right\' data-tooltip=\"${message(code: 'mass_per_section')}\"><i class='fa fa-weight-hanging queryImpactCloud'></i></div>  ${roundedTonnes ? roundedTonnes : 0} ${unit} mass</div>"
                    }
                    if (percentageValue) {
                        impacts = impacts
                    }

                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getMassToDisplayPerSection", e)
            flashService.setErrorAlert("Error occurred in getting the mass per section string : ${e.getMessage()}", true)
        }

        return impacts
    }

    def getImpactsPerRow() {
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId
        String rows = params.rows
        String paramsUnit = params.unit
        Map<String, String> carbonDataImpactPerRow
        Map<String, String> carbonDataPercentagePerRow
        List<String> manualIds
        Map<String, String> impactUnitMap = [:]

        if (rows) {
            if (rows.contains(",")) {
                manualIds = rows.tokenize(",")
            } else {
                manualIds = [rows]
            }
        }
        if (manualIds) {
            Entity childEntity = entityService.getEntityById(childEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
            String displayRuleId = indicator?.displayResult

            if (childEntity && indicator && displayRuleId) {
                User user = userService.getCurrentUser()
                DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
                decimalFormat.applyPattern('#,###,###.##')
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP)
                Set<Dataset> entityDatasets = childEntity.datasets
                Denominator overallDenominator = indicator.resolveDenominators?.find({ "overallDenominator".equalsIgnoreCase(it.denominatorType) })
                List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, displayRuleId, null)
                List<CalculationResult> resultsForIndicatorFailover = childEntity.getCalculationResultObjects(indicator.indicatorId, "GWP_failover", null)
                List<ResultCategory> resultCategories = indicator.getResolveResultCategories(childEntity?.parentEntityId ? childEntity?.parentById : childEntity)?.findAll({ !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty() })

                if (entityDatasets && (resultsForIndicator || resultsForIndicatorFailover) && resultCategories) {
                    ResourceCache resourceCache = ResourceCache.init(entityDatasets as List)
                    manualIds.each { String manualId ->
                        String unit = paramsUnit
                        Dataset dataset = entityDatasets.find({ manualId.equals(it.manualId) })
                        String impact
                        String impactPercentage
                        Double carbonDataImpact
                        Double carbonDataPercentage

                        if (dataset) {
                            Dataset connectedDataset

                            if (dataset.connectedDatasetManualId) {
                                connectedDataset = entityDatasets.find({ dataset.connectedDatasetManualId.equals(it.manualId) })
                            }

                            def constructionDatasets

                            if (resourceCache.getResource(dataset)?.construction) {
                                constructionDatasets = entityDatasets.findAll({
                                    it.uniqueConstructionIdentifier.equals(dataset.uniqueConstructionIdentifier)
                                })
                            }

                            resultCategories?.each { ResultCategory category ->
                                if (resourceCache.getResource(dataset)?.construction) {
                                    if (constructionDatasets) {
                                        constructionDatasets.each { Dataset d ->
                                            if (d.connectedDatasetManualId) {
                                                connectedDataset = entityDatasets.find({ dataset.connectedDatasetManualId.equals(it.manualId) })
                                            }
                                            Double value = childEntity.getResultForDataset(d.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator, false)

                                            if (!value && resultsForIndicatorFailover) {
                                                value = childEntity.getResultForDataset(d.manualId, indicator.indicatorId, category.resultCategoryId, "GWP_failover", resultsForIndicatorFailover, false)
                                            }

                                            if (value) {
                                                carbonDataImpact = carbonDataImpact ? carbonDataImpact + value : value
                                            }

                                            if (connectedDataset) {
                                                Double connectedDatasetValue = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator, false)

                                                if (!connectedDatasetValue && resultsForIndicatorFailover) {
                                                    connectedDatasetValue = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, "GWP_failover", resultsForIndicatorFailover, false)
                                                }

                                                if (connectedDatasetValue) {
                                                    carbonDataImpact = carbonDataImpact ? carbonDataImpact + connectedDatasetValue : connectedDatasetValue
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Double score = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator, false)

                                    if (!score && resultsForIndicatorFailover) {
                                        score = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, "GWP_failover", resultsForIndicatorFailover, false)
                                    }

                                    if (score) {
                                        carbonDataImpact = carbonDataImpact ? carbonDataImpact + score : score
                                    }

                                    if (connectedDataset) {
                                        Double connectedDatasetScore = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                                        if (!connectedDatasetScore && resultsForIndicatorFailover) {
                                            connectedDatasetScore = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, "GWP_failover", resultsForIndicatorFailover, false)
                                        }

                                        if (connectedDatasetScore) {
                                            carbonDataImpact = carbonDataImpact ? carbonDataImpact + connectedDatasetScore : connectedDatasetScore
                                        }
                                    }
                                }
                            }
                            Double totalGWP = (Double) childEntity.getTotalResult(indicator.indicatorId, displayRuleId)

                            if (!totalGWP) {
                                Double totalGWPfailover = (Double) childEntity.getTotalResult(indicator.indicatorId, "GWP_failover")

                                if (totalGWPfailover) {
                                    totalGWP = totalGWPfailover
                                }
                            }

                            if (carbonDataImpact != null) {
                                Boolean dontDivide = Boolean.FALSE
                                if (overallDenominator) {
                                    Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                                    if (overallDenomValue != null) {
                                        carbonDataImpact = carbonDataImpact * overallDenomValue
                                        totalGWP = totalGWP * overallDenomValue
                                    }
                                }

                                if (Constants.NO_DIVISION_RULES.contains(displayRuleId)) {
                                    dontDivide = Boolean.TRUE
                                } else if (carbonDataImpact < 100) {
                                    dontDivide = Boolean.TRUE
                                    unit = "kg"
                                }

                                carbonDataPercentage = carbonDataImpact / totalGWP * 100
                                if (!dontDivide) {
                                    carbonDataImpact = (carbonDataImpact / 1000)
                                }


                                //rounding rules:
                                if (carbonDataImpact >= 10) {
                                    decimalFormat.setMaximumFractionDigits(0)
                                    impact = "${decimalFormat.format(carbonDataImpact)}${unit}";
                                } else if (carbonDataImpact > 1 && carbonDataImpact < 10) {
                                    decimalFormat.setMaximumFractionDigits(1)
                                    impact = "${decimalFormat.format(carbonDataImpact)}${unit}"
                                } else if (carbonDataImpact < 1 && carbonDataImpact >= 0.01) {
                                    decimalFormat.setMaximumFractionDigits(2)
                                    impact = "${decimalFormat.format(carbonDataImpact)}${unit}"
                                } else if (carbonDataImpact < 0) {
                                    decimalFormat.setMaximumFractionDigits(2)
                                    impact = "${decimalFormat.format(carbonDataImpact)}${unit}"
                                } else if (carbonDataImpact == 0) {
                                    impact = ""
                                } else {
                                    impact = "~0${unit}"
                                }

                                if (carbonDataPercentage >= 1) {
                                    decimalFormat.setMaximumFractionDigits(0)
                                    impactPercentage = " - ${decimalFormat.format(carbonDataPercentage)}%"
                                } else if (carbonDataPercentage < 1 && carbonDataPercentage >= 0.95) {
                                    decimalFormat.setMaximumFractionDigits(2)
                                    impactPercentage = " - ${decimalFormat.format(carbonDataPercentage)}%"
                                } else if (carbonDataPercentage < 0.95 && carbonDataPercentage >= 0.05) {
                                    decimalFormat.setMaximumFractionDigits(1)
                                    impactPercentage = " - ${decimalFormat.format(carbonDataPercentage)}%"
                                } else if (carbonDataPercentage < 0.05) {
                                    impactPercentage = " - ~0%"
                                } else {
                                    impactPercentage = ""
                                }

                            }
                        }

                        if (carbonDataImpactPerRow) {
                            carbonDataImpactPerRow.put((manualId), impact ?: "")
                            impactUnitMap.put(manualId, unit)
                        } else {
                            carbonDataImpactPerRow = [(manualId): impact ?: ""]
                            impactUnitMap.put(manualId, unit)

                        }
                        if (carbonDataPercentagePerRow) {
                            carbonDataPercentagePerRow.put((manualId), impactPercentage ?: "")
                        } else {
                            carbonDataPercentagePerRow = [(manualId): impactPercentage ?: ""]

                        }
                    }
                }
            }
        }

        if (!carbonDataImpactPerRow) {
            manualIds.each { String manualId ->
                if (carbonDataImpactPerRow) {
                    carbonDataImpactPerRow.put((manualId), "")
                } else {
                    carbonDataImpactPerRow = [(manualId): ""]
                }
            }
        }
        if (!carbonDataPercentagePerRow) {
            manualIds.each { String manualId ->
                if (carbonDataPercentagePerRow) {
                    carbonDataPercentagePerRow.put((manualId), "")
                } else {
                    carbonDataPercentagePerRow = [(manualId): ""]
                }
            }
        }
        render([carbonDataImpactPerRow: carbonDataImpactPerRow, carbonDataPercentagePerRow: carbonDataPercentagePerRow, impactUnitMap: impactUnitMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def moveDatasetToAnotherQuestion() {
        List<String> datasetManualIds = params.list('manualId')
        String targetQuestionId = params.targetQuestionId
        String queryId = params.queryId
        String entityId = params.entityId
        String indicatorId = params.indicatorId

        if (entityId && datasetManualIds && targetQuestionId && queryId) {
            Entity child = entityService.getEntityByIdReadOnly(entityId)
            Question question = queryService.getQueryByQueryId(queryId, Boolean.TRUE)?.getAllQuestions()?.find({ it.questionId.equals(targetQuestionId) })
            List<Dataset> datasetsToMove = []

            datasetManualIds.each { String manualId ->
                Dataset d = child.datasets.find({ it.manualId.equalsIgnoreCase(manualId) })
                if (d) {
                    if ("permanent".equals(d.additionalQuestionAnswers?.get("serviceLife")) && question.recurringReplacement != false) {
                        d.additionalQuestionAnswers.remove("serviceLife")
                    }
                    datasetsToMove.add(d)

                    if (d.connectedDatasetManualId) {
                        Dataset connectedDataset = child.datasets.find({ it.manualId?.equalsIgnoreCase(d.connectedDatasetManualId) })

                        if (connectedDataset) {
                            if ("permanent".equals(connectedDataset.additionalQuestionAnswers?.get("serviceLife")) && question.recurringReplacement != false) {
                                connectedDataset.additionalQuestionAnswers.remove("serviceLife")
                            }
                            datasetsToMove.add(connectedDataset)
                        }
                    }
                }
            }

            if (datasetsToMove) {
                datasetsToMove.each { Dataset dataset ->
                    dataset.questionId = targetQuestionId
                    dataset.sectionId = question.sectionId
                }
            }

            if (indicatorId) {
                Entity parent = child.parentById
                child = newCalculationServiceProxy.calculate(null, indicatorId, parent, child)
            }
            child.merge(flush: true, failOnError: true)
        }
        render text: "ok"
    }

    def carbonDataBreakdown() {
        User user = userService.getCurrentUser()
        String output = ""
        String datasetManualId = params.manualId
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId
        String unit = params.unit
        Entity childEntity = entityService.getEntityById(childEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
        Dataset dataset = childEntity?.datasets?.find({ datasetManualId.equals(it.manualId) })
        Map<String, String> resultCategoryAndScore = [:]
        Boolean dontDivide = Boolean.FALSE

        if (dataset && childEntity && indicator) {
            Boolean calculationDetailsEnabled = childEntity.calculationDetailsEnabled
            String displayRuleId = indicator.displayResult
            List<ResultCategory> resultCategories = indicator.getResolveResultCategories(childEntity?.parentEntityId ? childEntity?.parentById : childEntity)
            Denominator overallDenominator = indicator?.resolveDenominators?.find({ "overallDenominator".equalsIgnoreCase(it.denominatorType) })

            if (displayRuleId) {
                List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicatorId, displayRuleId, null)
                if (Constants.NO_DIVISION_RULES.contains(displayRuleId)) {
                    dontDivide = Boolean.TRUE
                }
                resultCategories?.findAll({ !it.ignoreFromTotals?.contains(displayRuleId) && !it.ignoreFromTotals?.isEmpty() })?.each { ResultCategory category ->
                    Double score
                    if (datasetService.getResource(dataset)?.construction) {
                        def datasets = childEntity.datasets.findAll({
                            it.uniqueConstructionIdentifier.equals(dataset.uniqueConstructionIdentifier)
                        })

                        if (datasets) {
                            datasets.each { Dataset d ->
                                Double value = childEntity.getResultForDataset(d.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                                if (d.connectedDatasetManualId) {
                                    Dataset connectedDataset = childEntity.datasets.find({ d.connectedDatasetManualId.equals(it.manualId) })

                                    if (connectedDataset) {
                                        Double connectedDatasetScore = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                                        if (connectedDatasetScore) {
                                            if (value != null) {
                                                value = value + connectedDatasetScore
                                            }
                                        }
                                    }
                                }

                                if (value) {
                                    if (overallDenominator) {

                                        Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                                        if (overallDenomValue != null) {
                                            value = value * overallDenomValue
                                        }
                                    }

                                    score = score ? score + value : value

                                }
                            }

                            if (score != null) {
                                if (score < 100 && !dontDivide) {
                                    dontDivide = Boolean.TRUE
                                    if (!Constants.NO_DIVISION_RULES.contains(displayRuleId)) {
                                        unit = "kg"
                                    }
                                }
                                if (!dontDivide) {
                                    score = (score / 1000)
                                }
                                def carbonDataImpact = score

                                if (!carbonDataImpact != null) {
                                    //rounding rules:
                                    if (carbonDataImpact >= 10) {
                                        carbonDataImpact = carbonDataImpact.toInteger()?.toString()
                                    } else if (carbonDataImpact > 1 && carbonDataImpact < 10) {
                                        carbonDataImpact = carbonDataImpact.round(1)?.toString()
                                    } else if (carbonDataImpact < 1 && carbonDataImpact >= 0.01) {
                                        carbonDataImpact = carbonDataImpact.round(2)?.toString()
                                    } else if (carbonDataImpact < 0) {
                                        carbonDataImpact = carbonDataImpact.round(2)?.toString()
                                    } else if (carbonDataImpact == 0) {
                                        carbonDataImpact = ""
                                    } else {
                                        carbonDataImpact = "~0"
                                    }

                                    if (unit) {
                                        carbonDataImpact = "${carbonDataImpact} ${unit}"
                                    }
                                    resultCategoryAndScore.put(category.localizedShortName ?: category.resultCategory, carbonDataImpact)
                                }
                            }
                        }

                    } else {
                        CalculationResult calculationResult = resultsForIndicator?.findAll({
                            it.calculationRuleId.equals(displayRuleId) &&
                                    it.resultCategoryId.equals(category.resultCategoryId)
                        })?.find({ it.calculationResultDatasets?.keySet()?.toList()?.contains(dataset.manualId) })


                        score = calculationResult?.calculationResultDatasets?.get(dataset.manualId)?.result

                        if (dataset.connectedDatasetManualId) {
                            Dataset connectedDataset = childEntity.datasets.find({ dataset.connectedDatasetManualId.equals(it.manualId) })

                            if (connectedDataset) {
                                Double connectedDatasetScore = childEntity.getResultForDataset(connectedDataset.manualId, indicator.indicatorId, category.resultCategoryId, displayRuleId, resultsForIndicator)

                                if (connectedDatasetScore) {
                                    if (score != null) {
                                        score = score + connectedDatasetScore
                                    }
                                }
                            }
                        }

                        if (score) {

                            if (overallDenominator) {

                                Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                                if (overallDenomValue != null) {
                                    score = score * overallDenomValue
                                }
                            }
                            if (score < 100) {
                                dontDivide = Boolean.TRUE
                                if (!Constants.NO_DIVISION_RULES.contains(displayRuleId)) {
                                    unit = "kg"
                                }
                            }
                            if (!dontDivide) {
                                score = (score / 1000)
                            }

                            def carbonDataImpact = score

                            if (carbonDataImpact != null) {
                                //rounding rules:
                                if (carbonDataImpact >= 10) {
                                    carbonDataImpact = carbonDataImpact.toInteger()?.toString()
                                } else if (carbonDataImpact > 1 && carbonDataImpact < 10) {
                                    carbonDataImpact = carbonDataImpact.round(1)?.toString()
                                } else if (carbonDataImpact < 1 && carbonDataImpact >= 0.01) {
                                    carbonDataImpact = carbonDataImpact.round(2)?.toString()
                                } else if (carbonDataImpact < 0) {
                                    carbonDataImpact = carbonDataImpact.round(2)?.toString()
                                } else if (carbonDataImpact == 0) {
                                    carbonDataImpact = ""
                                } else {
                                    carbonDataImpact = "~0"
                                }

                                if (unit) {
                                    carbonDataImpact = "${carbonDataImpact} ${unit}"
                                }

                                if (calculationDetailsEnabled) {
                                    carbonDataImpact = "${carbonDataImpact}<a class=\"margin-left-5 calculationFormula\" onclick=\"showCalculationFormula(this, '${childEntityId}', '${dataset.manualId}','${indicator.indicatorId}','${category.resultCategoryId}','${displayRuleId}', 600);\">${message(code: "see_calculation")}</a>"
                                }
                                resultCategoryAndScore.put(category.localizedShortName ?: category.resultCategory, carbonDataImpact)
                            }
                        }
                    }

                }

            }
        }
        if (resultCategoryAndScore && !resultCategoryAndScore.isEmpty()) {
            resultCategoryAndScore.each { String key, String value ->
                output = output + "${key}: ${value}<br/>"
            }
        }
        render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getDataQualityWarning() {
        String globalQualityWarning = Constants.EMPTY_STRING
        List<String> filteredOutManualIds = []

        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId

        if (entityId && indicatorId && queryId) {
            Entity entity = entityService.getEntityById(entityId, session)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Query query = queryService.getQueryByQueryId(queryId, true)

            if (entity && indicator && query) {
                List<QueryFilter> supportedFilters = query.supportedFilters
                QueryFilter upstreamDBClassifiedFilter = supportedFilters?.find({ "upstreamDBClassified".equals(it.resourceAttribute) })
                Map qualityWarningData = queryService.getDataQualityWarning(entity, indicator, queryId, upstreamDBClassifiedFilter, userService.getCurrentUser())
                globalQualityWarning = groovyPageRenderer.render(template: "/query/blocks/globalDataQualityWarning", model: [warnings: qualityWarningData.qualityWarnings])
                filteredOutManualIds = qualityWarningData.filteredOutConstructionManualIds
            }
        }

        render([globalQualityWarning: globalQualityWarning, filteredOutManualIds: filteredOutManualIds, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getMissmatchingDataWarning() {
        String toRender = ""
        String entityId = params.entityId
        String queryId = params.queryId

        if (entityId && queryId) {
            Entity entity = entityService.getEntityById(entityId, session)

            if (entity) {
                List<Dataset> datasetsForWarnings = entity.datasets?.findAll({ it.queryId == queryId })?.toList()

                if (datasetsForWarnings) {
                    List<Dataset> incompatibleUnitDatasets = []
                    ResourceCache resourceCache = ResourceCache.init(datasetsForWarnings)
                    for (Dataset d: datasetsForWarnings) {
                        if (!d.noQueryRender) {
                            Resource resource = resourceCache.getResource(d)

                            if (resource) {
                                List<String> compatibleUnits = unitConversionUtil.getCompatibleUnitsForResource(resource)

                                if (d.userGivenUnit && compatibleUnits && !compatibleUnits*.toLowerCase()?.contains(d.userGivenUnit.toLowerCase()) && !d.uniqueConstructionIdentifier) {
                                    incompatibleUnitDatasets.add(d)
                                }
                            }
                        }
                    }

                    if (incompatibleUnitDatasets) {
                        toRender = "<div class=\"container\"><div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                                "<strong>${message(code: 'incompatible_warning_heading', args: [incompatibleUnitDatasets.size()])}</strong><ul>"

                        resourceCache = ResourceCache.init(incompatibleUnitDatasets)
                        for (Dataset d: incompatibleUnitDatasets) {
                            toRender = "${toRender}<li>${message(code: 'data_incompatible_unit', args: [resourceCache.getResource(d)?.staticFullName, d.userGivenUnit])} </li>"
                        }
                        toRender = "${toRender}</ul></div></div>"
                    }
                }
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def importFromDesignDatasets() {
        String targetEntityId = params.targetEntityId
        String fromEntityId = params.fromEntityId
        String queryId = params.queryId
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String typeOfImport = params.typeOfImport

        Boolean mergeDataset = params.mergeDataset ? Boolean.TRUE : Boolean.FALSE
        Boolean overwriteExisting = params.overwriteExistingOnly ? Boolean.TRUE : Boolean.FALSE
        Boolean mainPageCheck = params.boolean("mainPageCheck")
        if ("all".equalsIgnoreCase(typeOfImport)) {
            List<String> queryIdsList = indicatorService.getIndicatorByIndicatorId(indicatorId)?.indicatorQueries?.findAll({ !it.projectLevel })?.collect({ it.queryId })
            datasetService.copyDatasetsByQueryIdsList(fromEntityId, targetEntityId, indicatorId, queryIdsList, true, mergeDataset, overwriteExisting)
        } else if ("one".equalsIgnoreCase(typeOfImport) && queryId) {
            datasetService.copyDatasetsByQueryIdsList(fromEntityId, targetEntityId, indicatorId, [queryId], true, mergeDataset, overwriteExisting)
        }

        if (mainPageCheck) {
            redirect controller: "entity", action: "show", id: entityId
        } else {
            redirect action: "form", params: [entityId: entityId, childEntityId: targetEntityId, indicatorId: indicatorId, queryId: queryId]
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getGroupEditContent() {
        String queryId = params.queryId
        Set<String> questionIds = params.list('questionId')
        Set<String> sectionIds = params.list('sectionId')
        Set<String> manualIdsPerQuestion = params.list('manualIdsPerQuestion')
        Set<String> manualIdsPerResourceUUID = params.list('manualIdsPerResourceUUID') ?: []
        Set<String> constituentsPerConstruction = params.list('constituentsPerConstruction')
        List<String> constituentResourceUUIDs = params.list('constituentResourceUUID') ?: []
        List<String> resourceUUIDs = params.list('resourceUUID') ?: []
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String toRender = ''
        Map<String, Integer> nameMaxLength = [:]
        Map<String, Set<String>> manualIdsWithPermanentServiceLifeMap = [:]

        if (indicatorId && queryId && questionIds && resourceUUIDs) {
            Entity design = entityService.getEntityById(entityId)
            Entity parentEntity = design?.parentById
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Query query = queryService.getQueryByQueryId(queryId)

            Set<Question> questions = questionService.getQuestions(query, questionIds)
            Map<String, Set<String>> manualIdsPerQuestionMap = questionService.getManualIdsPerQuestionIdMap(manualIdsPerQuestion)
            Map<String, Set<String>> manualIdsPerResourceUuidMap = questionService.getManualIdsPerResourceUuidMap(manualIdsPerResourceUUID)
            Map<String, Set<String>> constituentsPerConstructionMap = questionService.getConstituentsPerConstructionMap(constituentsPerConstruction)

            // for front end to update serviceLife questions
            manualIdsWithPermanentServiceLifeMap = questionService.getManualIdsWithPermanentServiceLifeQuestionMap(manualIdsPerQuestionMap, questions, indicator)
            // for serviceLife, so user can change service life if there's one resource in a section that can. Logic to update the correct ones is in frontend
            Question questionWithoutPermanentServiceLife = questions?.find({ !questionService.isWithPermanentServiceLife(it, indicator) })

            // need to always fetch new addQ query from db since we will modify it
            Set<Question> additionalQuestions = questionService.getAdditionalQuestionsOfQuestions(questions, indicator, parentEntity, true)
            additionalQuestionService.removeAdditionalQuestionsForGroupEditing(additionalQuestions, indicator)

            Map<String, List<Document>> additionalQuestionResources = [:]
            Map<String, Boolean> additionalQuestionLicensed = [:]
            Map<String, Boolean> additionalQuestionShowAsDisabled = [:]
            List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsOfEntity(parentEntity)

            for (Question additionalQuestion in additionalQuestions) {
                additionalQuestionService.putAdditionalQuestionResourcesToMap(additionalQuestion, additionalQuestionResources, indicator, queryId, session)
                licenseService.putLicensedStatusOfQuestionToMap(additionalQuestion, additionalQuestionLicensed, licensedFeatureIds, indicator, additionalQuestionShowAsDisabled)
            }

            int selectedCount = resourceUUIDs?.size() ?: 0
            Map<String, Integer> resourceOccurrenceCount = stringUtilsService.countItemOccurrenceInList(resourceUUIDs)
            Set<Resource> allResources = optimiResourceService.getResourcesByDbIds((resourceUUIDs + constituentResourceUUIDs)?.toSet() as Set<String>)
            Set<Resource> selectedResources = allResources?.findAll({ resourceUUIDs.contains(it.id.toString()) })
            Set<Resource> constituentResources = allResources - selectedResources ?: []
            Resource firstNonConstructionResource = allResources?.find({ !it.construction }) ?: allResources?.size() > 0 ? allResources[0] : null
            optimiResourceService.populateGroupingNameToResources(selectedResources, design?.datasets, manualIdsPerResourceUuidMap)

            nameMaxLength = questionService.getNameMaxLengthOfQuestions(additionalQuestions)

            // for local comp
            Resource resourceWithLocalCompAvailable = selectedResources?.find({ optimiResourceService.isLocalCompApplicable(it) })
            String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)
            String defaultLocalCompCountry = datasetService.getDefaultLocalCompensationCountry(parentEntity)

            // for transport question
            Question transportAddQ = additionalQuestions?.find({ it.isTransportQuestion })
            Resource resourceWithSelectableTransport = optimiResourceService.getResourceWithoutDisabledNoMassTransport(allResources, transportAddQ)

            // remove addQs are in group (keep first one)
            additionalQuestions = questionService.handleGroupedQuestions(additionalQuestions as List<Question>)
            // need to limit the adjustable size of text question (if some have) so user does not expand it over the modal's width
            questionService.reconfigureAdjustableInputWidthLimitForTextQuestions(additionalQuestions, 300)

            toRender = g.render(template: "/query/modal/groupEditModal", model: [additionalQuestions                : additionalQuestions.sort(),
                                                                                 additionalQuestionLicensed         : additionalQuestionLicensed,
                                                                                 additionalQuestionShowAsDisabled   : additionalQuestionShowAsDisabled,
                                                                                 additionalQuestionResources        : additionalQuestionResources,
                                                                                 query                              : query,
                                                                                 resourceOccurrenceCount            : resourceOccurrenceCount,
                                                                                 constructionService                : constructionService,
                                                                                 optimiResourceService              : optimiResourceService,
                                                                                 selectedCount                      : selectedCount,
                                                                                 selectedResources                  : selectedResources.sort(),
                                                                                 constituentResources               : constituentResources.sort(),
                                                                                 constituentsPerConstructionMap     : constituentsPerConstructionMap,
                                                                                 manualIdsPerResourceUuidMap        : manualIdsPerResourceUuidMap,
                                                                                 firstNonConstructionResource       : firstNonConstructionResource,
                                                                                 resourceWithLocalCompAvailable     : resourceWithLocalCompAvailable,
                                                                                 resourceWithSelectableTransport    : resourceWithSelectableTransport,
                                                                                 indicator                          : indicator,
                                                                                 questions                          : questions,
                                                                                 questionWithoutPermanentServiceLife: questionWithoutPermanentServiceLife,
                                                                                 localCompensationMethodVersion     : localCompensationMethodVersion,
                                                                                 defaultLocalCompCountry            : defaultLocalCompCountry,
                                                                                 mainSectionId                      : sectionIds?.size() > 0 ? sectionIds[0] : [],
                                                                                 parentEntity                       : parentEntity,
                                                                                 entity                             : design])
        }
        render([output                              : toRender,
                nameMaxLength                       : nameMaxLength,
                manualIdsWithPermanentServiceLifeMap: manualIdsWithPermanentServiceLifeMap,
                (flashService.FLASH_OBJ_FOR_AJAX)   : flash] as JSON)
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getVerificationPointsModal() {
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String toRender = ''
        if (indicatorId && entityId) {
            Entity entity = entityService.getEntityById(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            if (entity && indicator) {
                Set<VerificationPoint> verificationPoints = verificationPointService.getVerificationPointsForIndicatorAndEntity(indicator, entity, childEntityId)
                toRender = verificationPoints ? groovyPageRenderer.render(template: "/query/modal/verificationPointModal", model: [verificationPoints: verificationPoints?.sort(), queryId: queryId]) : message(code: 'verificationPoints.notFound')
            }
        }
        render([output: toRender ?: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}
