/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.UnitConversionUtil
import grails.converters.JSON
import grails.validation.Validateable
import groovy.transform.CompileStatic
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.io.FileUtils
import org.bson.Document
import org.springframework.context.i18n.LocaleContextHolder

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Question implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    String questionId
    String inputType // how to render in html, eg. select, text, textarea etc
    String originalInputType
    Map<String, Object> sliderParameters
    String defaultValue // this is a answerId of QuestionAnswerChoice or resourceId of resource or text
    String defaultValueFromUser // this is user attribute
    ValueReference defaultValueFromParentEntity
    Double defaultQuantity // this is default added to resource quantity when adding resource rows.
    List<String> resourceGroups
    List<String> skipResourceTypes
    List<String> allowResourceTypes
    List<String> allowedExtensions
    String invalidExtensionMessage
    List<String> compatiblePrivateDataTypes
    Long maximumSize
    Boolean multipleChoicesAllowed
    Boolean showAnswerInResults
    Boolean optional
    Boolean recurringReplacement
    Boolean showTypeahead
    Boolean preventDoubleEntries
    Boolean applyIndicatorRequirements
    Boolean useTextIfListEmpty
    Boolean applyIndicatorResourceFilterCriteria // Works for resource based questions
    @Translatable
    Map<String, String> question // QuestionText
    @Translatable
    Map<String, String> questionShort // QuestionText
    @Translatable
    Map<String, String> help
    @Translatable
    Map<String, String> unit
    @Translatable
    Map<String, String> noChoiceDisplayText
    List<QuestionAnswerChoice> choices
    List<String> entityClasses
    Map<String, String> filterResources
    Integer inputWidth
    Integer inputHeight // Mainly for textareas
    Integer userAdjustableMaxWidth // for input type text, if defined, will change from type text to textarea
    Map valueConstraints
    String defaultResourceId
    String defaultValueFromResource
    String defaultValueFromCountryResource
    String defaultValueFromResourceSubtype
    Boolean showDefaultResourceOnly
    List<String> requiredEnvironmentDataSourceStandards // transient, indicators sets this to filter resources

    // licenseKey, requiredLicenseKeys, disableForLicenseKeys are used in licenseService doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys
    String licenseKey
    Boolean noLicenseShowAsDisabled // Allows additionalQuestion without licenseKey BUT shows as disabled
    List<String> noLicenseKeyIndicatorIds // These indicators dont need the licenseKey
    // only show section if project has one of the license key
    List<String> requiredLicenseKeys
    // hide section if project has one of the license key
    List<String> disableForLicenseKeys

    Integer fileMaxAmount // only used if inputType file
    List<String> groupedQuestionIds
    // This tell in additionalQuestion, to which other additional questions are grouped to this question. Eg. transportResourceId is grouped to transportDistance....
    QuestionSpecifiedSettingDisplayMode specifiedSettingDisplayMode

    // these need to be fixed to reflect new way of dealing with additionalQuestions
    Boolean quantity
    List<String> additionalQuestionIds
    String mapToResourceType
    CopyFromQuestion copyAnswer
    CopyFromQuestion copyQuantity
    Map<String, String> additionalQuestionDefaults // for main question for additionalQuestions

    // For pdf creation
    List exportImage //  [ "page" : 1, "y" : 202, "x" : 2, "width": 2, "height" : 2]
    Boolean expandable

    String queryId
    String sectionId
    Boolean pointsFactorInUi
    Boolean showFullText
    Boolean injectXmlButton
    Boolean showInMapping
    Boolean materialServiceLifeWarning
    Boolean calculatedOutputField
    // if true, it means that any edits on the input for this question should not trigger calculation
    Boolean informationalInput

    //Private Classifications
    Boolean privateClassificationQuestion
    Boolean showAsAlternativeClassification

    Integer questionSequenceNr

    ValueReference unitAsValueReference
    Boolean disableEditingForConstruction
    Boolean dataByConstituent
    Boolean disableEditingForComponent
    Boolean hideValueInQuery
    Boolean inheritToChildren
    Boolean irreversible
    Boolean preventIndicatorAdditionalQuestions
    Boolean eolChoicesFromSubType
    List<String> subTypeChoices // Shows resourceSubTYpes by group
    Boolean showAsInteger // additionalQuestion boolean for text type

    String dataSort
    String helpType
    String relatedQuestionId

    String compatibleUnitSystem // metric or imperial
    List<String> compatibleLcaModels // task 12187, shows only if relevant model selected for project
    String constructionDefaultSubType
    Boolean noEmptyOption
    String mapToResourceParameter

    String imageType

    @Deprecated
    String verificationPoint // pls use verificationPoints
    List<String> verificationPoints
    //NMD 3
    String addQunitByResource
    Map<String, List<Object>> additionalQuestionDisplayCriteria
    Map<String, Map<String, List<String>>> javascriptAdditionalCalcParams
    ResourceService resourceService

    //sw-1525
    Map<String, Boolean> enablingCondition
    Integer paramUnitId
    Boolean usedForFunctionalUnit


    static embedded = [
            'choices',
            'specifiedSettingDisplayMode',
            'copyAnswer',
            'copyQuantity',
            'unitAsValueReference',
            'defaultValueFromParentEntity'
    ]

    static marshalling = {
        shouldOutputIdentifier false
        shouldOutputVersion false
        shouldOutputClass false
    }

    static transients = [
            "defineInputType",
            "defineUnit",
            "defineAdditionalQuestions",
            "defineQuantity",
            "defineOptional",
            "defineMultipleChoicesAllowed",
            "resourceProfiles",
            "resources",
            "onlyNumeric",
            "minAllowed",
            "maxAllowed",
            "requiredEnvironmentDataSourceStandards",
            "queryId",
            "sectionId",
            "originalInputType",
            "formattedMaxSize",
            "uiNumericCheck",
            "isThicknessQuestion",
            "isTransportQuestion",
            "isResourceQuestion",
            "pointsFactorInUi",
            "readXmlButton",
            "isMassPerUnitQuestion"
    ]

    static constraints = {
        exportImage nullable: true
        questionId nullable: true
        defaultValue nullable: true
        defaultResourceId nullable: true
        inputType nullable: true
        allowedExtensions nullable: true
        invalidExtensionMessage nullable: true
        maximumSize nullable: true
        help nullable: true
        choices nullable: true
        resourceGroups nullable: true
        skipResourceTypes nullable: true
        quantity nullable: true
        multipleChoicesAllowed nullable: true
        showAnswerInResults nullable: true
        optional nullable: true
        recurringReplacement nullable: true
        showTypeahead nullable: true
        sliderParameters nullable: true
        preventDoubleEntries nullable: true
        entityClasses nullable: true
        mapToResourceType nullable: true
        userAdjustableMaxWidth nullable: true
        inputWidth nullable: true
        inputHeight nullable: true
        valueConstraints nullable: true
        defaultValueFromResource nullable: true
        defaultValueFromResourceSubtype nullable: true
        applyIndicatorRequirements nullable: true
        useTextIfListEmpty nullable: true
        licenseKey nullable: true
        fileMaxAmount nullable: true
        specifiedSettingDisplayMode nullable: true
        groupedQuestionIds nullable: true
        copyAnswer nullable: true
        queryId nullable: true
        sectionId nullable: true
        expandable nullable: true
        pointsFactorInUi nullable: true
        showFullText nullable: true
        injectXmlButton nullable: true
        questionSequenceNr nullable: true
        showDefaultResourceOnly nullable: true
        showInMapping nullable: true
        materialServiceLifeWarning nullable: true
        unitAsValueReference nullable: true
        disableEditingForConstruction nullable: true
        disableEditingForComponent nullable: true
        hideValueInQuery nullable: true
        inheritToChildren nullable: true
        irreversible nullable: true
        defaultQuantity nullable: true
        defaultValueFromUser nullable: true
        dataSort nullable: true
        dataByConstituent nullable: true
        defaultValueFromCountryResource nullable: true
        preventIndicatorAdditionalQuestions nullable: true
        additionalQuestionDefaults nullable: true
        noChoiceDisplayText nullable: true
        eolChoicesFromSubType nullable: true
        helpType nullable: true
        copyQuantity nullable: true
        noLicenseKeyIndicatorIds nullable: true
        showAsInteger nullable: true
        relatedQuestionId nullable: true
        compatibleUnitSystem nullable: true
        noLicenseShowAsDisabled nullable: true
        compatibleLcaModels nullable: true
        constructionDefaultSubType nullable: true
        defaultValueFromParentEntity nullable: true
        subTypeChoices nullable: true
        noEmptyOption nullable: true
        applyIndicatorResourceFilterCriteria nullable: true
        mapToResourceParameter nullable: true
        allowResourceTypes nullable: true
        privateClassificationQuestion nullable: true
        showAsAlternativeClassification nullable: true
        compatiblePrivateDataTypes nullable: true
        imageType nullable: true
        verificationPoint nullable: true
        verificationPoints nullable: true
        addQunitByResource nullable: true
        requiredLicenseKeys nullable: true
        disableForLicenseKeys nullable: true
        additionalQuestionDisplayCriteria nullable: true
        calculatedOutputField nullable: true
        informationalInput nullable: true
        enablingCondition nullable: true
        paramUnitId nullable: true
        usedForFunctionalUnit nullable: true
    }

    transient optimiResourceService
    transient queryService
    transient loggerUtil
    transient licenseService
    transient messageSource
    transient localeResolverUtil
    transient resourceTypeService
    transient questionService
    transient unitConversionUtil
    transient userService
    transient eolProcessService
    transient localizedLinkService
    transient privateClassificationListService
    transient accountImagesService
    transient datasetService
    transient valueReferenceService
    transient indicatorQueryService


    private String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage().toUpperCase()
    }

    def Question createHiddenAdditionalQuestionCopy() {
        Question copy = new Question()
        copy.inputType = "hidden"
        copy.questionId = new String(this.questionId)
        if (this.choices) {
            copy.choices = new ArrayList<QuestionAnswerChoice>(this.choices)
        }
        if (this.eolChoicesFromSubType) {
            copy.eolChoicesFromSubType = Boolean.TRUE
        }
        if (this.defaultValue) {
            copy.defaultValue = new String(this.defaultValue)
        }
        if (this.defaultValueFromResource) {
            copy.defaultValueFromResource = new String(this.defaultValueFromResource)
        }
        if (this.defaultValueFromResourceSubtype) {
            copy.defaultValueFromResourceSubtype = new String(this.defaultValueFromResourceSubtype)
        }
        return copy
    }

    def getIsThicknessQuestion() {
        Boolean isThickness = false

        if ("thickness_mm".equals(questionId) || "thickness_in".equals(questionId)) {
            isThickness = true
        }
        return isThickness
    }
    private Boolean getIsMassPerUnitQuestion() {
        Boolean isMassPerUnitQuestion = false
        if (Constants.MASS_PER_UNIT_KG_QUESTIONID.equals(questionId)) {
            isMassPerUnitQuestion = true
        }
        return isMassPerUnitQuestion
    }

    def getIsTransportQuestion() {
        Boolean isTransport = false

        if (Constants.TransportDistanceQuestionId.list().contains(questionId)) {
            isTransport = true
        }
        return isTransport
    }

    def getFormattedMaxSize() {
        if (maximumSize) {
            return FileUtils.byteCountToDisplaySize(maximumSize)
        }
        return ""
    }

    def defineInputType(QuerySection parentSection) {
        def inputType

        if (parentSection?.defaults?.inputType) {
            inputType = parentSection?.defaults?.inputType
        } else {
            inputType = this.inputType
        }
        return inputType
    }

    def defineShowTypeahead(QuerySection parentSection) {
        def showTypeahead

        if (parentSection?.defaults?.showTypeahead != null) {
            showTypeahead = parentSection?.defaults?.showTypeahead
        } else {
            showTypeahead = this.showTypeahead
        }
        return showTypeahead != null ? showTypeahead : Boolean.FALSE
    }

    def defineUnit(QuerySection parentSection) {
        def unit

        if (parentSection?.defaults?.getLocalizedUnit()) {
            unit = parentSection?.defaults?.getLocalizedUnit()
        } else {
            unit = getLocalizedUnit()
        }
        return unit
    }

    def defineAdditionalQuestions(Indicator indicator = null, QuerySection parentSection, Boolean handleGrouped = Boolean.FALSE, Entity parent = null, Boolean skipLicenseCheck = Boolean.FALSE) {
        def defaultAdditionalQuestions = parentSection?.defaults?.additionalQuestions
        List<Question> additionalQuestions = []

        if (defaultAdditionalQuestions) {
            additionalQuestions.addAll(defaultAdditionalQuestions)
        }
        List<Question> indicatorAdditionalQuestions = getAdditionalQuestions(indicator, null, null, parent)
        if (indicatorAdditionalQuestions) {
            if (!additionalQuestions.isEmpty()) {
                indicatorAdditionalQuestions = indicatorAdditionalQuestions.findAll({
                    !additionalQuestions.collect({ Question q -> q.questionId })?.contains(it.questionId)
                })

                if (indicatorAdditionalQuestions && !indicatorAdditionalQuestions.isEmpty()) {
                    additionalQuestions.addAll(indicatorAdditionalQuestions)
                }
            } else {
                additionalQuestions.addAll(indicatorAdditionalQuestions)
            }
        }

        if (handleGrouped && additionalQuestions && !additionalQuestions.isEmpty()) {
            additionalQuestions = handleGroupedQuestions(additionalQuestions)
        }

        if (skipLicenseCheck) {
            return additionalQuestions
        } else {
            return additionalQuestions?.findAll({ it.isQuestionLicensed(parent) })
        }
    }

    def handleGroupedQuestions(List <Question> questions) {
        if (questions) {
            List<String> groupIds = questions.findResults({it.groupedQuestionIds?:null}).flatten()
            return questions.findAll({!groupIds.contains(it.questionId)})
        }
    }

    def inGroupedQuestions() {
        boolean inGroupedQuestions = false
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID)

        if (additionalQuestionsQuery) {
            Question questionGroupingThisQuestion = additionalQuestionsQuery.getAllQuestions()?.find({ Question q -> q.groupedQuestionIds && q.groupedQuestionIds.contains(this.questionId) })

            if (questionGroupingThisQuestion) {
                inGroupedQuestions = true
            }
        }
        return inGroupedQuestions
    }

    def defineQuantity(QuerySection parentSection) {
        Boolean defaultQuantity = parentSection?.defaults?.quantity

        if (defaultQuantity != null) {
            return defaultQuantity
        } else {
            return quantity
        }
    }

    def defineOptional(QuerySection parentSection) {
        def defaultOptional = parentSection?.defaults?.optional

        if (defaultOptional != null) {
            return defaultOptional
        } else {
            return optional
        }
    }

    def defineMultipleChoicesAllowed(QuerySection parentSection) {
        def defaultAllowed = parentSection?.defaults?.multipleChoicesAllowed

        if (defaultAllowed != null) {
            return defaultAllowed
        } else {
            return multipleChoicesAllowed
        }
    }

    def getAdditionalQuestionResources(Indicator indicator, String queryId, String query = null) {
        List<Document> resources
        if (skipResourceTypes || resourceGroups || allowResourceTypes) {
            resources = optimiResourceService.getAdditionalQuestionResources(resourceGroups, skipResourceTypes, requiredEnvironmentDataSourceStandards, query, indicator, queryId, applyIndicatorResourceFilterCriteria, allowResourceTypes)
        }
        return resources
    }

    /**
     * Gets additional questions
     * in case additionalQuestionQuestions and aqQuery are null, can be slow in a loop
     * TODO: remove possibility to use without additionalQuestionQuestions parameter
     *
     * @param indicator
     * @param aqQuery
     * @param additionalQuestionQuestions
     * @param parentEntity
     * @return a list of additional questions
     */
    List<Question> getAdditionalQuestions(Indicator indicator = null, Query aqQuery = null, List additionalQuestionQuestions = null, Entity parentEntity = null) {
        Boolean imperialAdditionalQuestions = UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(userService.getCurrentUser()?.unitSystem)
        Query additionalQuestionsQuery = aqQuery ? aqQuery : queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalQuestions = []
        String lcaModel = parentEntity?.lcaModel


        if (additionalQuestionsQuery?.sections && additionalQuestionIds) {
            QuerySection additionalQuestionsSection = additionalQuestionsQuery?.sections[0]

            additionalQuestionIds?.each { String questionId ->
                Question question = additionalQuestionsSection.questions?.find({ questionId.equals(it.questionId) })
                if (question && isValidAdditionalQuestion(question, imperialAdditionalQuestions)) {
                    additionalQuestions.add(question)
                }
            }
        }

        if (indicator && queryId && !preventIndicatorAdditionalQuestions) {
            List<IndicatorQuery> indicatorQueriesWithAdditionalQuestions = indicator.indicatorQueries?.findAll({
                it.additionalQuestionIds && it.queryId.equals(queryId)
            })

            if (indicatorQueriesWithAdditionalQuestions) {
                List<Question> questions = additionalQuestionQuestions ? additionalQuestionQuestions : additionalQuestionsQuery?.getAllQuestions()
                for (IndicatorQuery indicatorQuery in indicatorQueriesWithAdditionalQuestions) {
                    indicatorQuery.additionalQuestionIds?.each { String questionId ->
                        Question q = questions?.find({
                            questionId.equals(it.questionId)
                        })
                        if(!q && questionId?.equalsIgnoreCase(Constants.ORGANIZATION_CLASSIFICATION_ID)){
                            q = privateClassificationListService.getPrivateClassificationQuestionByIndicator(indicator.classificationsMap,indicator, questions?.find {it.showInMapping && indicatorQuery?.additionalQuestionIds?.contains(it.questionId) })

                        }
                        if (q && isValidAdditionalQuestion(q, imperialAdditionalQuestions)) {
                            additionalQuestions.add(q)
                        }
                    }
                }
                long now = System.currentTimeMillis()
                if (indicator?.indicatorId?.equalsIgnoreCase(Constants.COMPARE_INDICATORID) && parentEntity  && questions){
                    List<Question> addQForMapping = getAllAddQForMatCompare(parentEntity,questions)
                    if(addQForMapping){
                        additionalQuestions.addAll(addQForMapping)
                    }
                }
                //log.info("TIME ${System.currentTimeMillis() - now}")
            }
        }

        return additionalQuestions?.findAll({
            it && !it?.compatibleLcaModels || (it?.compatibleLcaModels &&
                    ((lcaModel && it?.compatibleLcaModels?.contains(lcaModel)) || (it?.noLicenseKeyIndicatorIds && indicator &&
                            it?.noLicenseKeyIndicatorIds?.contains(indicator.indicatorId))))
        })?.unique({ Question q -> q?.questionId })
    }

    private static boolean isValidAdditionalQuestion(Question question, Boolean imperialAdditionalQuestions) {
        if (!question.compatibleUnitSystem) {
            return true
        } else {
            if (imperialAdditionalQuestions && UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(question.compatibleUnitSystem)) {
                return true
            } else if (!imperialAdditionalQuestions && !UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(question.compatibleUnitSystem)) {
                return true
            }
        }
        return false
    }

    private getAllAddQForMatCompare(Entity parentEntity, List<Question> questions){
        List<Indicator> indicatorList = parentEntity.indicators
        List <String> allClassificationMaps = indicatorList?.collect({it?.classificationsMap})?.flatten()?.findAll({it})?.unique()
        Query additionalQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID,true)
        List<Question> additionalQuestionsForMapping = allClassificationMaps?.collect({it -> questionService.getQuestion(additionalQuery,it)})

        return additionalQuestionsForMapping
    }

    def getResourceProfiles(resourceId, profileId = null, Indicator indicator = null, Entity entity = null) {
        List<Resource> profiles

        if (resourceId) {
            List<Resource> resources = optimiResourceService.getResourceProfilesByResourceId(resourceId, profileId, false)

            if (resources && !resources.isEmpty()) {
                profiles = new ArrayList<Resource>(resources)

                if (skipResourceTypes) {
                    profiles.removeAll({it.resourceType && skipResourceTypes.contains(it.resourceType)})
                }

                if (allowResourceTypes) {
                    profiles.removeAll({ it.resourceType && !allowResourceTypes.contains(it.resourceType) && !allowResourceTypes.contains(it.resourceSubType)})
                }

                if (resourceGroups) {
                    profiles.removeAll({it.resourceGroup && !resourceGroups.intersect(it.resourceGroup)})
                }

                if (profiles && queryId && indicator && !profileId) {
                    IndicatorQuery indicatorQueryWithResourceFilter = indicator.indicatorQueries?.find({
                        it.queryId.equals(this.queryId) && it.resourceFilterCriteria
                    })
                    if (indicatorQueryWithResourceFilter) {
                        Map<String, List<Object>> resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(entity, IndicatorQuery.RESOURCE, indicatorQueryWithResourceFilter)

                        for (Resource r in resources) {
                            Iterator<String> iterator = resourceFilterCriteria.keySet().iterator()

                            while (iterator.hasNext()) {
                                String resourceAttribute = iterator.next()

                                if (resourceAttribute) {
                                    List<Object> requiredValues = resourceFilterCriteria.get(resourceAttribute)

                                    if (requiredValues?.size()) {
                                        def resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, r, "${resourceAttribute}: ${requiredValues}")

                                        if (resourceValue != null) {
                                            if (resourceValue instanceof List) {
                                                if (!CollectionUtils.containsAny(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }), requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                                    profiles.remove(r)
                                                    break
                                                }
                                            } else if (!requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                                profiles.remove(r)
                                                break
                                            }
                                        } else {
                                            if (!requiredValues.contains(null)) {
                                                profiles.remove(r)
                                                break
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return profiles
    }

    Map<String, List<Object>>  getResourceFilterCriteria(Indicator indicator, Entity parent) {
        Map<String, List<Object>> resourceFilterCriteria = [:]
        IndicatorQuery indicatorQueryWithResourceFilter

        if (queryId) {
            indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                it.queryId.equals(this.queryId) && it.resourceFilterCriteria
            })

            if (indicatorQueryWithResourceFilter) {
                resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(parent, IndicatorQuery.RESOURCE, indicatorQueryWithResourceFilter)
            }
        }
        return resourceFilterCriteria
    }

    def getWarningResourceFilterCriteria(Indicator indicator, Entity parent) {
        Map<String, List<Object>> resourceFilterCriteria = [:]
        IndicatorQuery indicatorQueryWithResourceFilter
        if (queryId) {
            indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                it.queryId.equals(this.queryId) && it.warningResourceFilterCriteria
            })
            if (indicatorQueryWithResourceFilter) {
                resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(parent, IndicatorQuery.WARNING_RESOURCE, indicatorQueryWithResourceFilter)
            }
        }
        return resourceFilterCriteria
    }

    def getBlockResourceFilterCriteria(Indicator indicator, Entity parent) {
        Map<String, List<Object>> resourceFilterCriteria = [:]
        IndicatorQuery indicatorQueryWithResourceFilter
        if (queryId) {
            indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                it.queryId.equals(this.queryId) && it.blockResourceFilterCriteria
            })
            if (indicatorQueryWithResourceFilter) {
                resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(parent, IndicatorQuery.BLOCK_RESOURCE, indicatorQueryWithResourceFilter)
            }
        }
        return resourceFilterCriteria
    }

    def getOrganisationResourceFilterCriteria(Indicator indicator, Entity parent) {
        Map<String, List<Object>> resourceFilterCriteria = [:]
        IndicatorQuery indicatorQueryWithResourceFilter

        if (queryId) {
            indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                it.queryId.equals(this.queryId) && it.organisationResourceFilterCriteria
            })

            if (indicatorQueryWithResourceFilter) {
                resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(parent, IndicatorQuery.ORGANISATION_RESOURCE, indicatorQueryWithResourceFilter)
            }
        }
        return resourceFilterCriteria
    }

    def getOnlyNumeric() {

        if ("numeric".equals(valueConstraints?.get("type"))) {
            return true
        } else {
            return false

        }
    }

    def getMinAllowed() {
        return valueConstraints?.get("min")
    }

    def getMaxAllowed() {
        return valueConstraints?.get("max")
    }

    def isQuestionLicensed(Entity entity) {
       return licenseService.isQueryOrSectionOrQuestionLicensed(this, null, entity)
    }

    def getUiNumericCheck() {
        String valueCheck

        if (("text".equals(inputType) || "textarea".equals(inputType)) && onlyNumeric) {
            Locale locale = localeResolverUtil.resolveLocale()
            String message = messageSource.getMessage("query.quantity_type.warning", null, locale)
            valueCheck = "onclick=\"numericInputCheck();\" rel=\"popover\" data-trigger=\"focus\" data-html=\"true\" data-content=\"${message}\""
        }


        return valueCheck
    }

    def getIsResourceQuestion() {
        boolean resourceQuestion = false

        if (resourceGroups || skipResourceTypes || allowResourceTypes) {
            resourceQuestion = true
        }
        return resourceQuestion
    }

    Map<String, Map<String, String>> getanswerIdLimitConditionMap() {
        Map<String, JSON> answerIdLimitConditionMap = [:]
        choices?.each {
            if (it.limitConditions) {
                answerIdLimitConditionMap.put(it.answerId, it.limitConditions as JSON)
            }
        }
        return answerIdLimitConditionMap
    }

    private Locale getLocale() {
        return localeResolverUtil.resolveLocale()
    }

    def getDefaultValueByResourceId(Resource resource) {
        def value

        if (resource && defaultValueFromResource) {
            value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromResource, resource)
        }

        if ("costPerUnit".equals(questionId) && value) {
            DecimalFormat dfCost = new DecimalFormat("#.##")
            value = dfCost.format(value)
        }
        return value
    }

    def getDefaultValueFromResourceSubtype(Resource resource) {
        //5681 Tries to get value from resource first and then subType
        def value
        if (resource && defaultValueFromResourceSubtype) {
            value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromResourceSubtype, resource)
            if (value == null) {
                ResourceType resourceSubType = resource.subType

                if (resourceSubType) {
                    value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromResourceSubtype, resourceSubType)
                }
            }
        }
        return value
    }


    /**
     * This method takes an entity and checks the defaults transport question
     * If fetching RICS transport value, resource subtype is used
     * If defaultValueFromResourceSubtype parameter exists in the resource, fetches the resource value
     * If it doesn't exist within the resource, then it is fetched from the subtype
     * @param entity
     * @param resource
     * @return transport value
     */
    def getDefaultValueFromResourceSubtype(Entity entity, Resource resource) {
        def value
        if (entity && entity.defaults && resource) {
            String valueToGet = entity.defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)

            if (valueToGet.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                ResourceType subType = resource.subType

                if (questionId.equals(Constants.TRANSPORT_RESOURCE_LEG2_QUESTIONID)) {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS_LEG2, subType)
                } else {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS, subType)
                }
            } else {
                value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromResourceSubtype, resource)
                if (value == null) {
                    ResourceType subType = resource.subType
                    if (subType) {
                        value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromResourceSubtype, subType)
                    }
                }
            }
        }
        return value
    }

    def getReadXmlButton(Indicator indicator = null) {
        boolean showButton = false

        if (indicator?.readValuesFromXML && queryId && sectionId) {
            IndicatorXmlMapper indicatorXmlMapper = indicator.readValuesFromXML.
                    find({ queryId.equals(it.target?.queryId) && sectionId.equals(it.target?.sectionId) &&
                    questionId.equals(it.target?.questionId)})

            if (indicatorXmlMapper) {
                showButton = true
            }
        }
        return showButton
    }

    @CompileStatic
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Question question = (Question) o

        if (queryId != question.queryId) return false
        if (questionId != question.questionId) return false
        if (sectionId != question.sectionId) return false

        return true
    }

    @CompileStatic
    int hashCode() {
        int result
        result = (questionId != null ? questionId.hashCode() : 0)
        result = 31 * result + (queryId != null ? queryId.hashCode() : 0)
        result = 31 * result + (sectionId != null ? sectionId.hashCode() : 0)
        return result
    }

    @CompileStatic
    int compareTo(Object obj) {
        if (obj && obj instanceof Question) {
            if (this.questionSequenceNr && obj.questionSequenceNr) {
               return this.questionSequenceNr.compareTo(obj.questionSequenceNr)
            } else if (!this.questionSequenceNr && obj.questionSequenceNr) {
                return 1
            } else if (this.questionSequenceNr && !obj.questionSequenceNr) {
                return -1
            } else {
                return 0
            }
        } else {
            return 1
        }
    }

    def getCountryResourceDefault(Entity parentEntity) {
        String answer

        if (parentEntity && defaultValueFromCountryResource) {
            answer = DomainObjectUtil.callGetterByAttributeName(defaultValueFromCountryResource, parentEntity.countryResource)?.toString()
        }
        return answer
    }

}
