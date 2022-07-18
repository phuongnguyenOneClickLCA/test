package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.ApplicationResourceExtraInformationSet
import com.bionova.optimi.core.domain.mongo.ConditionalDisplayOnQuery
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.DataCardRow
import com.bionova.optimi.core.domain.mongo.DataCardSection
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionTableFormat
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.taglib.QueryRenderTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import grails.core.GrailsApplication
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.bson.Document
import org.grails.web.util.WebUtils
import org.springframework.validation.ObjectError

import java.text.DecimalFormat

class UtilService {
    GrailsApplication grailsApplication
    def licenseService
    def featureService
    def userService
    def entityService
    def queryService
    def questionService
    def constructionService
    def datasetService
    def indicatorService
    def additionalQuestionService
    def groovyPageRenderer
    def indicatorQueryService
    def querySectionService
    def messageSource

    def getDefaultConfigurationForDataCard(){
        List<DataCardSection> resolvedSourceListing = []
        // temporary fall back until a default configuration is created
        String defaultApplicationId = 'LCA'
        String extraInfoType = 'long'

        def application = Application.collection.findOne([applicationId: defaultApplicationId], [resourceExtraInformationSets: 1])
        ApplicationResourceExtraInformationSet appSourceListing

        if (application && application.resourceExtraInformationSets) {
            for (Map resourceExtraInformationSet in (application.resourceExtraInformationSets as List<Map>)) {
                if (resourceExtraInformationSet?.resourceInformationId && extraInfoType.contains(resourceExtraInformationSet.resourceInformationId.toString())) {
                    appSourceListing = resourceExtraInformationSet as ApplicationResourceExtraInformationSet
                    resolvedSourceListing = appSourceListing.resourceInformation ?: []
                    break
                }
            }
        }
        return resolvedSourceListing
    }

    def getResolvedLicenseKeysForSourceListing(List<DataCardSection> sourceListing) {
        List<String> requiredLicenseKeys = []
        if (sourceListing) {
            sourceListing?.each { DataCardSection s ->
                if (s.licenseKey) {
                    requiredLicenseKeys.add(s.licenseKey as String)
                }
                s.resourceAttributes.each { DataCardRow r ->
                    if (r.licenseKey) {
                        requiredLicenseKeys.add(r.licenseKey as String)
                    }
                }
            }
        }
        return requiredLicenseKeys
    }

    void handleConditionalHideOnDataCardRow(DataCardRow row, Resource resource) {
        if (row?.conditionalHide) {
            // This is used to hide row based on a condition.
            String hideRuleId = row.conditionalHide
            if ('profileEcoinvent' == hideRuleId) {
                // Quick solution. Will implement more concrete solution when we have better idea what we need
                // Use for hiding the EPD Number row if the profileId is Ecoinvent36.
                row.authorizedToDisplay = resource?.profileId?.equalsIgnoreCase(Resource.ECOINVENT36) ? false : row.authorizedToDisplay
            }
        }
    }

    /**
     * This method responsible for updating question field (add/remove red border)
     * Will be triggered after completing saving on query page
     *
     * @param entity
     * @param indicator
     * @param query
     * @return
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    Map getQuestionsStatuses(Entity entity, Indicator indicator, Query query) {

        Map questionStatuses = [:]

        if (query.disableHighlight) {
            log.trace "Skip status determination because of query.disableHighlight"
            return questionStatuses
        }
        String queryId = query.queryId

        query.sections.each { QuerySection section ->

            if (indicator.hideSectionTotally(section)) {
                log.trace "Skip section '${section.sectionId}'. It is hidden for indicator ${indicator.indicatorId}"
                return
            }

            List<Question> questions = querySectionService.getQuestionsByEntityClass(entity.entityClass, queryId, section.questions)
            Map questionStatusesForSection = [:]

            User user = userService.getCurrentUser()
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.UNLOCK_VERIFIED_DATASET], user)
            Boolean unlockVerifiedDatasetLicensed = featuresAllowed.get(Feature.UNLOCK_VERIFIED_DATASET)

            questions.each { Question question ->
                boolean optional = question.defineOptional(section)
                Map questionStatus = [:]

                if (!optional) {
                    Boolean highlight = false

                    def matchQuestionDataset = { Dataset d ->
                        return d.queryId.equals(queryId) && d.sectionId.equals(section.sectionId) && d.questionId.equals(question.questionId)
                    }

                    if (question.resourceGroups) {
                        Boolean hasResourceDataset = entity?.datasets?.any{ matchQuestionDataset(it) && it.resourceId}

                        if (indicatorQueryService.getRequiredResourceIds(indicator, queryId)) {
                            questionStatus.alert = getMissingRequiredResourcesAlert(entity, indicator, query, section, question)
                        }

                        highlight = !hasResourceDataset || questionStatus.alert
                    } else {
                        Boolean hasDataset = entity?.datasets?.any{ matchQuestionDataset(it) }
                        highlight = !hasDataset
                    }
                    questionStatus.highlight = highlight
                }

                if (unlockVerifiedDatasetLicensed) {
                    questionStatus.unlockVerifiedDatasetLink = getUnlockVerifiedDatasetLinkForQuestion(entity, query, section, question)
                }

                if (questionStatus) {
                    questionStatusesForSection[question.questionId] = questionStatus
                }
            }

            if (questionStatusesForSection) {
                questionStatuses[section.sectionId] = questionStatusesForSection
            }
        }

        return questionStatuses
    }

    /**
     * Generates alert if some required resource is missed
     * @param entity
     * @param indicator
     * @param query
     * @param section
     * @param question
     * @return
     */
    private getMissingRequiredResourcesAlert(Entity entity, Indicator indicator, Query query, QuerySection section, Question question) {
        String alert = ""

        Boolean showTypeahead = question.defineShowTypeahead(section)
        if (!showTypeahead) {
            return alert
        }

        String queryId = query.queryId
        Boolean hasMissingRequiredResources = entityService.hasMissingRequiredResources(entity, indicator, queryId)
        if (hasMissingRequiredResources) {
            QueryRenderTagLib queryRenderTagLib = grailsApplication.mainContext.getBean(QueryRenderTagLib.class)
            String fieldName = "${section.sectionId}.${question.questionId}"
            String resourceTableId = "${fieldName}Resources".replace('.', '').trim()
            Map params = [
                    entity    : entity,
                    indicator : indicator,
                    query     : query,
                    queryId   : queryId,
                    questionId: question.questionId,
                    sectionId : section.sectionId,
                    tableId   : resourceTableId,
                    fieldName : fieldName
            ]
            alert = queryRenderTagLib.mandatoryResource(params).toString()
        }
        return alert
    }

    private String getUnlockVerifiedDatasetLinkForQuestion(Entity entity, Query query, QuerySection section, Question question) {

        Dataset dataset = questionService.getDatasetForGsp(question.questionId, query.queryId, section.sectionId, null, null, entity, entity.parentEntity, null)

        def model = [unlockVerifiedDatasetLicensed: true, dataset: dataset]

        QuestionTableFormat tableFormat = section?.useTableFormatting
        Boolean useTableFormat = tableFormat?.questionIds?.contains(question.questionId).asBoolean()

        if (useTableFormat) {
            model.forNonResourceRow = true
        } else {
            String inputType = question.defineInputType(section)
            switch(inputType) {
                case 'textarea':
                    model.changeInputBg = true
                case 'text':
                case 'slider':
                case 'checkbox':
                case 'radio':
                    model.forNotRow = true
                    break
                case 'select':
                    if (!question?.resourceGroups || !question?.defineMultipleChoicesAllowed(section)) {
                        model.forNotRow = true
                    }
            }
        }

        groovyPageRenderer.render(template: "/query/links/unlockVerifiedDatasetLink", model: model)
    }

    /**
     * This method responsible for updating new added rows or copied rows or another rows where link was changes to select/input
     * Will be triggered after completing calculation on query page
     *
     * @param entity
     * @param indicator
     * @param query
     * @param datasetIds - this is munualIds of designs from the query form, which should be reloaded.
     * @return
     */
    Map getResourceRowsRepresentation(Entity entity, Indicator indicator, Query query, List<String> datasetIds){
        List<Dataset> datasetsForUpdate = entity.datasets.findAll{ Dataset dataset ->
            dataset.manualId in datasetIds
        }?.toList()

        if (!datasetsForUpdate){
            return [:]
        }

        Map resourceRepresentationResult = [:]
        QueryRenderTagLib queryRenderTagLib = grailsApplication.mainContext.getBean(QueryRenderTagLib.class)
        Entity parentEntity = entity.getParentById()
        EolProcessCache eolProcessCache = EolProcessCache.init()
        ResourceCache resourceCache = ResourceCache.init(datasetsForUpdate)
        ResourceTypeCache resourceTypeCache = resourceCache.getResourceTypeCache()
        List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
        Boolean splitViewAllowed = query?.allowedFeatures?.contains("splitOrChangeView")
        Boolean preventChanges = indicator?.preventChanges(query)
        User user = userService.getCurrentUser()
        List<Feature> licensedFeatures = []

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity,
                [Feature.BETA_FEATURES,
                 Feature.CHAT_SUPPORT,
                 Feature.SPLIT_OR_CHANGE,
                 Feature.SEND_ME_EPD_REQUEST,
                 Feature.COMPARE_MATERIAL,
                 Feature.UNLOCK_VERIFIED_DATASET,
                 Feature.SHOW_AND_NAVIGATE_VERIFICATION_POINTS
                ], user)

        Map<String, Integer> additionalQuestionInputWidth = indicator.indicatorQueries?.find{
            it.queryId == query.queryId
        }?.additionalQuestionInputWidth

        if (validLicenses) {
            validLicenses.each { License license ->
                license.licensedFeatures?.each { Feature feature ->
                    licensedFeatures.add(feature)
                }
            }
        }

        List<String> licensedFeatureIds = licensedFeatures?.collect({ it.featureId }) ?: []
        Boolean groupMaterialsAllowed = featureService.isGroupMaterialFeatureAllowedByLicensedFeatures(query, licensedFeatures, indicator)
        Boolean createConstructionsAllowed = (groupMaterialsAllowed && licensedFeatureIds.any({ it == Feature.CREATE_CONSTRUCTIONS })) ? true : false
        Boolean publicGroupAllowed = (createConstructionsAllowed && licensedFeatureIds.any({ it == Feature.SUBMIT_PUBLIC_CONSTRUCTIONS })) ? true : false
        Boolean splitOrChangeLicensed = featuresAllowed.get(Feature.SPLIT_OR_CHANGE)
        Boolean compareDataAllowedForMatSpecQuery = query.queryId.equalsIgnoreCase("materialSpecifier") && featuresAllowed.get(Feature.COMPARE_MATERIAL)
        String errorFromCheck = entityService.queryModifiable(parentEntity, entity, indicator, validLicenses, compareDataAllowedForMatSpecQuery)
        Boolean modifiable = Boolean.FALSE
        Boolean quarterlyInputEnabled = Boolean.FALSE
        Boolean allowMonthlyData = query?.allowMonthlyData && Constants.EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass) && licensedFeatures.find({ it.featureId == Feature.MONTHLY_DATA })
        Boolean allowQuarterlyData = query?.allowMonthlyData && Constants.EntityClass.OPERATING_PERIOD.toString().equals(entity.entityClass) && licensedFeatures.find({ it.featureId == Feature.QUARTERLY_DATA })
        Boolean monthlyInputEnabled = entity?.monthlyInputEnabledByQuery?.get(query.queryId) && allowMonthlyData
        Map <String, String> questionsWithAdditionalCalculationScripts = queryService.groupAdditionalCalculationScriptsByQuestions(query)
        String defaultLocalCompCountry = datasetService.getDefaultLocalCompensationCountry(parentEntity)
        DecimalFormat decimalFormat = userService.getDefaultDecimalFormat()
        Boolean automaticCostLicensed

        if (indicator?.isCostCalculationAllowed) {
            automaticCostLicensed = licensedFeatures.find({ it.featureId == Feature.AUTOMATIC_COST_GENERATION }) ? true : false
        }

        if (allowMonthlyData || allowQuarterlyData) {
            if (allowQuarterlyData) {
                quarterlyInputEnabled = entity.quarterlyInputEnabledByQuery?.get(query.queryId)
            }

            if (allowMonthlyData && !quarterlyInputEnabled) {
                monthlyInputEnabled = entity.monthlyInputEnabledByQuery?.get(query.queryId)
            }
        }

        if (!errorFromCheck) {
            modifiable = (parentEntity?.modifiable && validLicenses &&
                    (validLicenses.find({ it?.licensedIndicatorIds?.contains(indicator?.indicatorId) }) ||
                            compareDataAllowedForMatSpecQuery) &&
                    !parentEntity?.readonlyIndicatorIds?.contains(indicator?.indicatorId))
        }

        for (Dataset dataset in datasetsForUpdate){
            Resource resource = resourceCache.getResource(dataset)
            Question question = query.getQuestionsBySection(dataset.sectionId)?.find{ it.questionId == dataset.questionId }
            List<Question> additionalQuestions = getAdditionalQuestions(question, indicator, parentEntity, entity, query)

            Map<String, List<Document>> additionalQuestionResources = [:]
            Map<String, Boolean> additionalQuestionLicensed = [:]
            Map<String, Boolean> additionalQuestionShowAsDisabled = [:]

            if (additionalQuestions) {
                for (Question additionalQuestion in additionalQuestions) {
                    additionalQuestionService.putAdditionalQuestionResourcesToMap(additionalQuestion, additionalQuestionResources, indicator, query.queryId, WebUtils.retrieveGrailsWebRequest().getSession())
                    licenseService.putLicensedStatusOfQuestionToMap(additionalQuestion, additionalQuestionLicensed, licensedFeatureIds, indicator, additionalQuestionShowAsDisabled)
                }

                additionalQuestions = questionService.handleGroupedQuestions(additionalQuestions)
            }

            Map params = [
                    dataset                                  : dataset,
                    resource                                 : resource,
                    indicator                                : indicator,
                    query                                    : query,
                    entity                                   : entity,
                    parentEntity                             : parentEntity,
                    multipleChoicesAllowed                   : question ? question.multipleChoicesAllowed : true,
                    groupMaterialsAllowed                    : groupMaterialsAllowed,
                    createConstructionsAllowed               : createConstructionsAllowed,
                    publicGroupAllowed                       : publicGroupAllowed,
                    constructionPage                         : false,
                    productDataListPage                      : false,
                    splitViewAllowed                         : splitViewAllowed,
                    splitPage                                : false,
                    changeView                               : false,
                    hideAllAnsweredQuestions                 : false,
                    basicQuery                               : false,
                    additionalQuestionInputWidth             : additionalQuestionInputWidth,
                    questionsWithAdditionalCalculationScripts: questionsWithAdditionalCalculationScripts,
                    preventChanges                           : preventChanges,
                    splitOrChangeLicensed                    : splitOrChangeLicensed,
                    modifiable                               : modifiable,
                    monthlyInputEnabled                      : monthlyInputEnabled,
                    quarterlyInputEnabled                    : quarterlyInputEnabled,
                    eolProcessCache                          : eolProcessCache,
                    resourceCache                            : resourceCache,
                    defaultLocalCompCountry                  : defaultLocalCompCountry,
                    initializeAdditionalQuestionResources    : true,
                    additionalQuestions                      : additionalQuestions,
                    additionalQuestionResources              : additionalQuestionResources,
                    additionalQuestionLicensed               : additionalQuestionLicensed,
                    additionalQuestionShowAsDisabled         : additionalQuestionShowAsDisabled,
                    automaticCostLicensed                    : automaticCostLicensed
            ]

            String result = queryRenderTagLib.renderResourceRow(params).toString()
            resourceRepresentationResult.put(dataset.manualId, result)
        }

        return resourceRepresentationResult
    }

    List<Question> getAdditionalQuestions(Question question, Indicator indicator, Entity parentEntity, Entity entity, Query query){
        List<Question> additionalQuestions = question?.getAdditionalQuestions(indicator, null, null, parentEntity)?.sort()

        List<Indicator> indicators = indicatorService.getIndicatorsWithSameIndicatorQueries(entity, query.queryId)?.findAll({ it.indicatorId != indicator.indicatorId && it.visibilityStatus != "benchmark"})
        if (indicators && question) {
            List<String> questionIds = additionalQuestions?.collect({ it.questionId })

            indicators.each { Indicator i ->
                List<Question> indicatorAdditionals = question.getAdditionalQuestions(i, null, null, parentEntity)?.findAll({ !questionIds?.contains(it.questionId) })

                if (indicatorAdditionals) {
                    indicatorAdditionals.each { Question indicatorAdditional ->
                        if (!additionalQuestions.find({it.questionId == indicatorAdditional.questionId})) {
                            Question newHiddenQuestionCopy = indicatorAdditional.createHiddenAdditionalQuestionCopy() // To not edit the aq query in memory

                            if (additionalQuestions) {
                                additionalQuestions.add(newHiddenQuestionCopy)
                            } else {
                                additionalQuestions = [newHiddenQuestionCopy]
                            }
                        }
                    }
                }
            }
        }

        return additionalQuestions
    }

    def getAdditionalQuestionAnswerFromConstruction(Dataset dataset, Map scalingParameters, List<Question> additionalQuestions = [], Construction construction = null){
        Map additionalQuestionAnswerFromConstruction = dataset.additionalQuestionAnswers

        if (scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION1_NMD)) {
            if (additionalQuestionAnswerFromConstruction) {
                additionalQuestionAnswerFromConstruction.put((com.bionova.optimi.core.Constants.DIMENSION1_NMD), scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION1_NMD))
            } else {
                additionalQuestionAnswerFromConstruction = [(com.bionova.optimi.core.Constants.DIMENSION1_NMD): scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION1_NMD)]
            }
        }

        if (scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION2_NMD)) {
            if (additionalQuestionAnswerFromConstruction) {
                additionalQuestionAnswerFromConstruction.put((com.bionova.optimi.core.Constants.DIMENSION2_NMD), scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION2_NMD))
            } else {
                additionalQuestionAnswerFromConstruction = [(com.bionova.optimi.core.Constants.DIMENSION2_NMD): scalingParameters?.get(com.bionova.optimi.core.Constants.DIMENSION2_NMD)]
            }
        }

        if (additionalQuestionAnswerFromConstruction && additionalQuestions?.any { it.inheritToChildren } && construction?.additionalQuestionAnswers) {
            additionalQuestions.findAll({ it.inheritToChildren }).each {
                def constructionAnswer = construction.additionalQuestionAnswers.get(it.questionId)
                if (constructionAnswer) {
                    additionalQuestionAnswerFromConstruction.put(it.questionId, constructionAnswer)
                }
            }
        }

        return additionalQuestionAnswerFromConstruction
    }

    Map getAdditionalQuestionAnswers(List<Question> additionalQuestions, Question question, Resource resource, ResourceType resourceType,
                                     Map additionalQuestionAnswerFromConstruction, DecimalFormat decimalFormat,
                                     Double originalThickness){
        Map additionalQuestionAnswers = [:]

        if (additionalQuestions) {
            Map<String, String> additionalQuestionDefaults = question?.additionalQuestionDefaults


            for (Question additionalQuestion in additionalQuestions) {
                if (additionalQuestionAnswerFromConstruction && !additionalQuestionAnswerFromConstruction.isEmpty() && additionalQuestionAnswerFromConstruction.get(additionalQuestion.questionId)) {
                    additionalQuestionAnswers?.put(additionalQuestion.questionId, additionalQuestionAnswerFromConstruction?.get(additionalQuestion.questionId))
                } else {
                    if (additionalQuestionDefaults && additionalQuestionDefaults.get(additionalQuestion.questionId)) {
                        String addQValue = DomainObjectUtil.callGetterByAttributeName(additionalQuestion.questionId, resourceType)
                        if(addQValue){
                            additionalQuestionAnswers.put(additionalQuestion?.questionId, addQValue)
                        }else {
                            additionalQuestionAnswers.put(additionalQuestion?.questionId, additionalQuestionDefaults.get(additionalQuestion?.questionId))
                        }
                    } else if ("thickness_mm".equals(additionalQuestion.questionId) && resource.allowVariableThickness) {
                        if (originalThickness) {
                            if (originalThickness < 1) {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, "${originalThickness}")
                            } else {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, decimalFormat.format(originalThickness))
                            }
                        } else if (resource.defaultThickness_mm) {
                            if (resource.defaultThickness_mm < 1) {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, "${resource.defaultThickness_mm}")
                            } else {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, decimalFormat.format(resource.defaultThickness_mm))
                            }
                        }

                    } else if ("thickness_in".equals(additionalQuestion.questionId) && resource.allowVariableThickness) {
                        if (originalThickness) {
                            if (originalThickness < 1) {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, "${originalThickness}")
                            } else {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, decimalFormat.format(originalThickness))
                            }
                        } else if (resource.defaultThickness_in) {
                            if (resource.defaultThickness_in < 1) {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, "${resource.defaultThickness_in}")
                            } else {
                                additionalQuestionAnswers.put(additionalQuestion.questionId, decimalFormat.format(resource.defaultThickness_in))
                            }
                        }
                    }
                }
            }
        }
        return additionalQuestionAnswers
    }

    boolean checkIfDataCardRowIsForDisplaying(DataCardRow dataCardRow, List<String> additionalQuestionIds) {
        boolean dataCardRowIsForDisplaying = true
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = dataCardRow.conditionalDisplayOnQuery

        if (conditionalDisplayOnQuery && conditionalDisplayOnQuery.validate()) {
            if (additionalQuestionIds == null) {
                additionalQuestionIds = []
            }

            switch (conditionalDisplayOnQuery.resolveMethod) {
                case Constants.ConditionalDisplayResolveMethod.ALL.name:
                    dataCardRowIsForDisplaying = additionalQuestionIds.containsAll(conditionalDisplayOnQuery.additionalQuestionIds)
                    break
                case Constants.ConditionalDisplayResolveMethod.ANY.name:
                    dataCardRowIsForDisplaying = !additionalQuestionIds.disjoint(conditionalDisplayOnQuery.additionalQuestionIds)
                    break
                case Constants.ConditionalDisplayResolveMethod.NONE.name:
                    dataCardRowIsForDisplaying = additionalQuestionIds.disjoint(conditionalDisplayOnQuery.additionalQuestionIds)
                    break
            }

            if (conditionalDisplayOnQuery.method == Constants.ConditionalDisplayMethod.HIDE.name) {
                dataCardRowIsForDisplaying = !dataCardRowIsForDisplaying
            }
        } else {
            if (!conditionalDisplayOnQuery) {
                log.debug "ConditionalDisplayOnQuery config is null. DataCardRow (resourceAttribute: ${dataCardRow.resourceAttribute}, " +
                          "resourceSubTypeAttribute: ${dataCardRow.resourceSubTypeAttribute}, customAttribute: ${dataCardRow.customAttribute}) " +
                          "will be displayed."
            } else {
                log.debug "ConditionalDisplayOnQuery config is invalid: ${conditionalDisplayOnQuery.errors.allErrors.collect { ObjectError error -> messageSource.getMessage(error, Locale.getDefault()) } .join('; ')}. " +
                          "DataCardRow (resourceAttribute: ${dataCardRow.resourceAttribute}, resourceSubTypeAttribute: ${dataCardRow.resourceSubTypeAttribute}, " +
                          "customAttribute: ${dataCardRow.customAttribute}) will be displayed."
            }
        }

        return dataCardRowIsForDisplaying
    }
}
