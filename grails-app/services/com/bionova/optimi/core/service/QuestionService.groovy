/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryApplyFilterOnCriteria
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LocaleResolverUtil
import com.bionova.optimi.core.util.LoggerUtil
import groovy.transform.CompileStatic
import org.apache.commons.collections4.CollectionUtils
import org.bson.Document

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class QuestionService {

    QueryService queryService
    PrivateClassificationListService privateClassificationListService
    LoggerUtil loggerUtil
    FlashService flashService
    def stringUtilsService
    def accountImagesService
    def configurationService
    def localizedLinkService
    def messageSource
    DatasetService datasetService
    OptimiResourceService optimiResourceService
    ResourceTypeService resourceTypeService
    EolProcessService eolProcessService
    UserService userService
    ValueReferenceService valueReferenceService
    ResourceService resourceService
    IndicatorQueryService indicatorQueryService
    LocaleResolverUtil localeResolverUtil

    Question getQuestion(queryId, questionId) {
        Question question = null
        def query = queryService.getQueryByQueryId(queryId, true)

        if (query && questionId) {
            question = query.getAllQuestions()?.find({ Question q -> questionId.equals(q.questionId) })
        }
        return question
    }

    def getQuestion(Query query, String questionId) {
        Question question

        if (query) {
            if(Constants.ORGANIZATION_CLASSIFICATION_ID.equalsIgnoreCase(questionId)){
                question = privateClassificationListService.getPrivateClassificationQuestion()
            } else {
                for (section in query.sections) {
                    question = section.questions?.find({ q -> q.questionId == questionId })

                    if (question) {
                        break
                    }
                }

                if (!question) {
                    for (section in query.includedSections) {
                        question = section.questions?.find({ q -> q.questionId == questionId })

                        if (question) {
                            break
                        }
                    }
                }
            }

        }
        return question
    }

    def handleGroupedQuestions(List<Question> questions) {
        if (questions) {
            List<String> groupedIds = questions?.findAll({it.groupedQuestionIds})?.collectMany({it.groupedQuestionIds})
            return questions?.findAll({!groupedIds.contains(it.questionId)})
        } else {
            return null
        }
    }

    def translateElementCategoryToBreeamCategory(String elementCategory, String questionId) {
        String translatedCategory

        /*
            1.0 Substructure
            2.1 Frame
            2.2 Upper floors
            2.3 Roof
            2.4 Stairs and ramps
            2.5 External walls
            2.6 Windows and external doors
            2.7 Internal walls and partitions
            5.5.1 Heat source
            5.6 Space Heating and Air Conditioning
            5.7 Ventilation
            5.9 Fuel Installations / Systems
            8.2 Roads, paths and pavings
         */

        if (elementCategory) {
            if (elementCategory.startsWith("1")) {
                if (elementCategory.startsWith("11")) {
                    translatedCategory = "1.0 Substructure"
                }
            } else if (elementCategory.startsWith("2")) {
                if (elementCategory.startsWith("21")) {
                    translatedCategory = "2.1 Frame"
                } else if (elementCategory.startsWith("22")) {
                    translatedCategory = "2.2 Upper floors"
                } else if (elementCategory.startsWith("23")) {
                    translatedCategory = "2.3 Roof"
                } else if (elementCategory.startsWith("24")) {
                    translatedCategory = "2.4 Stairs and ramps"
                } else if (elementCategory.startsWith("25")) {
                    translatedCategory = "2.5 External walls"
                } else if (elementCategory.startsWith("26")) {
                    translatedCategory = "2.6 Windows and external doors"
                } else if (elementCategory.startsWith("27")) {
                    translatedCategory = "2.7 Internal walls and partitions"
                }
            } else if (elementCategory.startsWith("3")) {

            } else if (elementCategory.startsWith("4")) {

            } else if (elementCategory.startsWith("5")) {
                if (elementCategory.startsWith("55")) {
                    translatedCategory = "5.5.1 Heat source"
                } else if (elementCategory.startsWith("56")) {
                    translatedCategory = "5.6 Space Heating and Air Conditioning"
                } else if (elementCategory.startsWith("57")) {
                    translatedCategory = "5.7 Ventilation"
                } else if (elementCategory.startsWith("59")) {
                    translatedCategory = "5.9 Fuel Installations / Systems"
                }
            } else if (elementCategory.startsWith("6")) {

            } else if (elementCategory.startsWith("7")) {

            } else if (elementCategory.startsWith("8")) {
                if (elementCategory.startsWith("82")) {
                    translatedCategory = "8.2 Roads, paths and pavings"
                }
            } else if (elementCategory.startsWith("9")) {

            }
        }

        if (!translatedCategory && questionId) {
            translatedCategory = getDefaultBreeamCategoryByQuestionId(questionId)
        }
        return translatedCategory ? translatedCategory : "2.1 Frame"
    }

    def getDefaultBreeamCategoryByQuestionId(String questionId) {
        String defaultCategory

        /*

        foundations >>> 1.0 Substructure

        externalWalls >>> 2.5 External walls
        columnsLoadBearing >>> 2.1 Frame
        internalWalls >>> 2.7 Internal walls and partitions

        horizontalStructures >>> 2.2 Upper floors

        otherStructures >>> 2.4 Stairs and ramps
        windowsAndDoors >>> 2.6 Windows and external doors
        coverings >>> 2.7 Internal walls and partitions

        externalAreas >>> 8.2 Roads, paths and pavings

        buildingTechnology >>> 5.9 Fuel Installations / Systems

         */

        if (questionId) {
            if ("foundations".equals(questionId)) {
                defaultCategory = "1.0 Substructure"
            } else if ("externalWalls".equals(questionId)) {
                defaultCategory = "2.5 External walls"
            } else if ("columnsLoadBearing".equals(questionId)) {
                defaultCategory = "2.1 Frame"
            } else if ("internalWalls".equals(questionId)) {
                defaultCategory = "2.7 Internal walls and partitions"
            } else if ("otherStructures".equals(questionId)) {
                defaultCategory = "2.4 Stairs and ramps"
            } else if ("windowsAndDoors".equals(questionId)) {
                defaultCategory = "2.6 Windows and external doors"
            } else if ("externalAreas".equals(questionId)) {
                defaultCategory = "8.2 Roads, paths and pavings"
            } else if ("buildingTechnology".equals(questionId)) {
                defaultCategory = "5.9 Fuel Installations / Systems"
            } else if ("coverings".equals(questionId)) {
                defaultCategory = "2.7 Internal walls and partitions"
            }
        }

        return defaultCategory
    }

    def getDefaultTransportDistanceFromSubType(ResourceType subType, String defaultValueSetId) {
        Integer defaultDistance

        if (subType && defaultValueSetId) {
            Double defaultFromSubType

            if ("nordicTransportDistances".equals(defaultValueSetId)) {
                defaultFromSubType = subType.defaultTransportNordic
            } else if ("europeTransportDistances".equals(defaultValueSetId)) {
                defaultFromSubType = subType.defaultTransportEurope
            } else if ("ukTransportDistances".equals(defaultValueSetId)) {
                defaultFromSubType = subType.defaultTransportUK
            } else if ("usTransportDistances".equals(defaultValueSetId)) {
                defaultFromSubType = subType.defaultTransportUS
            } else if ("canadaTransportDistances".equals(defaultValueSetId)) {
                defaultFromSubType = subType.defaultTransportCanada
            }

            if (defaultFromSubType != null) {
                defaultDistance = Math.round(defaultFromSubType).intValue()
            }
        }
        return defaultDistance
    }

    /**
     * Get <questionId, List of additional question resources> map
     * @param questions
     * @param queryId
     * @return
     */
    Map<String, List<Document>> getAdditionalQuestionResourceDocumentsByQuestionIdMap(Set<Question> questions, String queryId) {
        Map<String, List<Document>> map = [:]
        try {
            if (questions && queryId) {
                getAdditionalQuestionsOfQuestions(questions)?.each { Question question ->
                    if (question) {
                        List<Document> resources = question.getAdditionalQuestionResources(null, queryId)
                        if (resources) {
                            map.put(question.questionId, resources)
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getAdditionalQuestionResourceDocumentsByQuestionIdMap", e)
            flashService.setErrorAlert("Error occurred in getAdditionalQuestionResourceDocumentsByQuestionIdMap: ${e.getMessage()}", true)
        }
        return map
    }

    String getAnswerForNonResourceBasedQuestion(Question question, Dataset dataset) {
        String answer = ''
        try {
            if (dataset?.answerIds?.size() > 0) {
                String answerId = dataset.answerIds[0] ?: ''
                if (question?.inputType == Constants.InputType.SELECT.toString()) {
                    // questions with type select have answers which are ids (short version), hence need to get the long version of it
                    String finalAnswer = answerId ? question?.choices?.find { it.answerId == answerId }?.localizedAnswer : ''
                    if (!finalAnswer && Constants.EPD_VISUAL_IMAGE_QUESTIONIDS.contains(dataset.questionId)) {
                        // for the EPD visuals section that the long versions of the question answers are the image names
                        finalAnswer = accountImagesService.getAccountImage(answerId)?.name ?: ''
                    }
                    answer = finalAnswer ?: answerId
                } else {
                    answer = answerId
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getAnswerForNonResourceBasedQuestion", e)
            flashService.setErrorAlert("Error occurred in getAnswerForNonResourceBasedQuestion: ${e.getMessage()}", true)
        }
         return answer
    }

    /**
     * Get additional questions of a set of questions
     * @param questions
     * @param indicator pass indicator to get addQs from indicatorQueries and classification addQ
     * @param parentEntity project entity
     * @param fetchFromDb true if need to always fetch additional query from db
     * @return additional questions
     */
    Set<Question> getAdditionalQuestionsOfQuestions(Set<Question> questions, Indicator indicator = null, Entity parentEntity = null, Boolean fetchFromDb = false) {
        Set<Question> additionalQuestions = []
        Set<String> ids = []
        try {
            if (questions) {
                Query additionalQuestionsQuery = fetchFromDb ? Query.findByQueryIdAndActive(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true) : queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                List<Question> additionalDefaultQuestions = additionalQuestionsQuery.getAllQuestions()
                questions.each { Question question ->
                    question?.getAdditionalQuestions(indicator, additionalQuestionsQuery, additionalDefaultQuestions, parentEntity)?.each { Question additionalQuestion ->
                        if (!ids?.contains(additionalQuestion?.questionId)) {
                            additionalQuestions.add(additionalQuestion)
                            ids.add(additionalQuestion?.questionId)
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getAdditionalQuestionsOfQuestions", e)
            flashService.setErrorAlert("Error occurred in getAdditionalQuestionsOfQuestions: ${e.getMessage()}", true)
        }
        return additionalQuestions
    }

    Set<Question> getQuestions(Query query, Set<String> questionIds) {
        try {
            if (query && questionIds) {
                return query.allQuestions?.findAll { questionIds.contains(it.questionId) }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getQuestions", e)
            flashService.setErrorAlert("Error occurred in getQuestions: ${e.getMessage()}", true)
        }
        return []
    }
    Set<String> getAdditionalQuestionIdsForIndicator(Indicator indicator, Entity parentEntity) {
        Set<String> indicatorAdditionalQuestionIds = []
        Set<String> queryIds = []
        indicator?.indicatorQueries?.each { IndicatorQuery indicatorQuery ->
            if(indicatorQuery.queryId) {
                queryIds.add(indicatorQuery.queryId)
            }
        }

        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalDefaultQuestions = additionalQuestionsQuery.getAllQuestions()

        queryService.getQueriesByQueryIds(queryIds as List).each { Query query ->
            query.getAllQuestions()?.each { Question q ->
                Set<String> addQIds = q.getAdditionalQuestions(indicator, additionalQuestionsQuery, additionalDefaultQuestions, parentEntity)
                        .collect { it.questionId } as Set<String>
                if (addQIds) {
                    indicatorAdditionalQuestionIds.addAll(addQIds)
                }
            }
        }
        return indicatorAdditionalQuestionIds
    }

    Map<String, Integer> getNameMaxLengthOfQuestions(Set<Question> questions) {
        Map<String, Integer> nameMaxLength = [:]
        try {
            if (questions) {
                for (Question question in questions) {
                    nameMaxLength.put(question?.questionId, stringUtilsService.calculateNameMaxLength(question?.inputWidth))
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getNameMaxLengthOfQuestions", e)
            flashService.setErrorAlert("Error occurred in getNameMaxLengthOfQuestions: ${e.getMessage()}", true)
        }
        return nameMaxLength
    }

    /**
     * if recurringReplacement is false (not null), the question is with permanent service life
     * @param question
     * @return true if question is with permanent service life
     */
    //(REL-616) Behavior from indicator should override existing question behavior
    boolean isWithPermanentServiceLife(Question question, Indicator indicator) {
        return indicator?.useSpecificServiceLifes ? false : question?.recurringReplacement == false
    }

    /**
     * filter questions with permanent service life
     * @param questions
     * @return
     */
    Set<Question> getQuestionsWithPermanentServiceLife(Set<Question> questions, Indicator indicator) {
        return questions?.findAll { isWithPermanentServiceLife(it, indicator) }
    }

    /**
     * Tokenize a set of strings in format "manualId.questionId" and put them to a map
     * @param manualIdsPerQuestion
     * @return a map <questionId, set of manualId>
     */
    Map<String, Set<String>> getManualIdsPerQuestionIdMap(Set<String> manualIdsPerQuestion) {
        return stringUtilsService.tokenizeStringsToMapWithCommonKeyAndSetValue(manualIdsPerQuestion)
    }

    /**
     * Tokenize a set of strings in format "manualId.resourceUuid" and put them to a map
     * @param manualIdsPerResourceUuid
     * @return a map <resourceUuid, set of manualId>
     */
    Map<String, Set<String>> getManualIdsPerResourceUuidMap(Set<String> manualIdsPerResourceUuid) {
        return stringUtilsService.tokenizeStringsToMapWithCommonKeyAndSetValue(manualIdsPerResourceUuid)
    }

    /**
     * Tokenize a set of strings in format "constituentResourceUUID.parentConstructionResourceUUID" and put them to a map
     * @param constituentsPerConstruction
     * @return a map <parentResourceUUID, set of constituent resourceUUID>
     */
    Map<String, Set<String>> getConstituentsPerConstructionMap(Set<String> constituentsPerConstruction) {
        return stringUtilsService.tokenizeStringsToMapWithCommonKeyAndSetValue(constituentsPerConstruction)
    }

    /**
     * Get a map that tells which manualIds belong to a questionId that has permanent service life
     * @param manualIdsByQuestionIdMap a map <questionId, set of manualIds>
     * @param questions
     * @return a map <questionId, set of manualIds>
     */
    Map<String, Set<String>> getManualIdsWithPermanentServiceLifeQuestionMap(Map<String, Set<String>> manualIdsByQuestionIdMap, Set<Question> questions, Indicator indicator) {
        Map<String, Set<String>> map = [:]
        try {
            if (questions && manualIdsByQuestionIdMap) {
                Set<Question> questionsWithPermanentServiceLife = getQuestionsWithPermanentServiceLife(questions, indicator)
                if (questionsWithPermanentServiceLife) {
                    for (Question q in questionsWithPermanentServiceLife) {
                        // if the question with permanent service life is in the map that has a manualIds list, put it to the new map
                        if (manualIdsByQuestionIdMap?.get(q?.questionId)) {
                            Set<String> manualIds = manualIdsByQuestionIdMap?.get(q?.questionId)
                            map.put(q?.questionId, manualIds)
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in getManualIdsWithPermanentServiceLifeQuestionMap", e)
            flashService.setErrorAlert("Error in getManualIdsWithPermanentServiceLifeQuestionMap: ${e.getMessage()}", true)
        }
        return map
    }

    /**
     * Currently input type text questions can have adjustable width enabled by config. This method reconfigure (override)
     * that limit.
     * @param questions
     * @param limit
     */
    void reconfigureAdjustableInputWidthLimitForTextQuestions(Set<Question> questions, Integer limit) {
        if (questions && limit != null) {
            for (Question q in questions) {
                if (q?.userAdjustableMaxWidth != null) {
                    q.userAdjustableMaxWidth = limit
                }
            }
        }
    }

    // this method runs very slow
    Set<Question> cloneQuestions(Set<Question> questions) {
        try {
            if (questions) {
                Set<Question> clones = new HashSet<Question>(questions?.size())
                for (Question q in questions) {
                    clones.add(new Question(q.properties))
                }
                return clones
            }
        } catch (e) {
            loggerUtil.error(log, "Error in cloneQuestions", e)
            flashService.setErrorAlert("Error in cloneQuestions: ${e.getMessage()}", true)
        }
        return []
    }

    Question getProductTypeQuestion() {
        String basicQueryId = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.BASIC_QUERY_ID.toString())
        return getQuestion(basicQueryId, Constants.PRODUCT_TYPE_QUESTIONID)
    }

    /**
     * Check if question is meant to be hidden from config
     * Similar function {@link QueryService#removeHiddenQuestions}
     * @param indicator
     * @param section that includes question
     * @param question
     * @return
     */
    boolean isQuestionHidden(Indicator indicator, QuerySection section, Question question) {
        if (indicator?.hideQuestions?.containsKey(section?.sectionId)) {
            if (indicator?.hideSectionTotally(section)) {
                return true
            }
            return indicator?.hideQuestions?.get(section?.sectionId)?.contains(question?.questionId) ?: false
        }
        return false
    }

    QuestionAnswerChoice getQuestionAnswerChoice(String answerId, String msg, Locale locale = null, boolean disabled = false) {
        Map answer = [EN: answerId]
        if(msg) {
            answer = [EN: locale ? getLocaleMessage(msg, locale) : msg]
        }
        return getQuestionAnswerChoice(answerId, answer, disabled)
    }

    QuestionAnswerChoice getQuestionAnswerChoice(String answerId, Map answer, Boolean disabled = Boolean.FALSE) {
        if(answerId == null) {
            return null
        }
        QuestionAnswerChoice choice = new QuestionAnswerChoice()
        choice.answerId = answerId
        choice.answer = answer
        if(disabled) {
            choice.disabled = Boolean.TRUE
        }
        return choice
    }

    String getLocaleMessage(String key, Locale locale) {
        String value = key
        try {
            value = messageSource.getMessage(value, null, locale)
        } catch (Exception e) {
            loggerUtil.error(log,"getLocaleMessage in Question", e)
        }
        return value
    }

    String getLocalizedQuestionShort(Question question) {
        if (!question) {
            return null
        }
        String localizedQuestionShort = question.getQuestionShort()

        if (localizedQuestionShort) {
            return localizedQuestionShort
        } else {
            return question.getLocalizedQuestion()
        }
    }

    String getLocalizedHelp(Question question) {
        if (!question) {
            return null
        }
        String localizedHelp = question.getLocalizedHelp()
        return localizedLinkService.getTransformStringIdsToLinks(localizedHelp)
    }

    /**
     * Get a string for rendering on GSP which is a list of questionIds that do not trigger calculation
     * @param questions
     * @return
     */
    String getRenderStringDoNotTriggerCalcQuestionIds(List<Question> questions) {
        Set<String> notTriggerCalcQuestionIds = getDoNotTriggerCalcQuestionIds(questions)
        if (notTriggerCalcQuestionIds) {
            return "\"${notTriggerCalcQuestionIds.join('","')}\""
        }
        return ''
    }

    /**
     * Get list of questions from config that are for informational purposes only, i.e. the edits of these questions do not neet to trigger calculation
     * Note: the private classification is not from config, and it should not trigger calc either
     * @param questions
     * @return
     */
    Set<String> getDoNotTriggerCalcQuestionIds(List<Question> questions) {
        return questions?.findAll { it.informationalInput || it.privateClassificationQuestion }?.collect { it.questionId }?.toSet()
    }

    Boolean isQuestionUsedForAdditionalJSCalculation(Query query, String sectionId, String questionId, Map additionalCalcParams){
        List<String> questionsForSection = additionalCalcParams.get(sectionId)
        return questionId in questionsForSection
    }

    @CompileStatic
    Map getAdditionalCalculationParamsForQuestion(Question question, Entity entity){
        Map additionalCalculationParams = [:]

        if (question?.javascriptAdditionalCalcParams){
            List<String> queries = question.javascriptAdditionalCalcParams.keySet() as List

            for (String queryId: queries){
                Map<String, List<String>> additionalParamsForQuery = question.javascriptAdditionalCalcParams.get(queryId)
                List<String> sectionsFromConfig = additionalParamsForQuery?.keySet() as List

                for (String sectionId: sectionsFromConfig){
                    List<String> questionIds = additionalParamsForQuery.get(sectionId)
                    Map <String, String> additionalCalculationParamsFromSection = entity?.datasets.findAll{
                        it.queryId == queryId && it.sectionId == sectionId &&
                                (questionIds ? it.questionId in questionIds : true)
                    }.collectEntries{
                        [(it.questionId): it.answerIds[0]?.toString()]
                    }

                    if (additionalCalculationParamsFromSection){
                        additionalCalculationParams.putAll(additionalCalculationParamsFromSection)
                    }
                }
            }
        }

        return additionalCalculationParams
    }

    Dataset getDatasetForGsp(String questionId, String queryId, String sectionId, List<Question> linkedQuestions, Boolean sendMeData, Entity entity, Entity parentEntity, String resourceId) {
        return datasetService.getQuestionAnswerDataset(questionId, queryId, sectionId, linkedQuestions, sendMeData, entity, parentEntity, resourceId)
    }

    String getAnswerForUI(answer, List<Document> resources, Question question) {
        String localizedAnswer = ""

        if (answer != null) {
            if (question.questionId == Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                String profileResourceId = answer.toString().tokenize(".")[0]
                String profileProfileId = answer.toString().tokenize(".")[1]

                if (profileResourceId && profileProfileId) {
                    localizedAnswer = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(profileResourceId, profileProfileId))
                }
            } else {
                def choices = getFilteredChoices(null, null, null, null, true, question)

                if (choices) {
                    localizedAnswer = choices.find({it.answerId == answer})?.localizedAnswer
                } else {
                    if (resources) {
                        localizedAnswer = resources.find({it.resourceId == answer})?.localizedName
                    } else {
                        localizedAnswer = answer
                    }
                }
            }
        }
        localizedAnswer = localizedAnswer ?: ""
        return localizedAnswer
    }

    List<QuestionAnswerChoice> getFilteredChoices(Entity parentEntity, Question parentQuestion, Resource resource, String userGivenAnswer = null,
                           Boolean skipLicenseKeyCheck = null, EolProcessCache eolProcessCache = null, Question question) {
        List<QuestionAnswerChoice> filteredChoices = []

        if (question?.choices) {
            List<String> combinedUnits = resource?.combinedUnits
            List<String> licensedFeatures = question.choices.find({it.licenseKey}) ? parentEntity?.features?.collect({it.featureId}) : []

            try {
                if (question.imageType &&
                        (question.imageType.equals(Constants.AccountImageType.PRODUCTIMAGE.toString()) ||
                        question.imageType.equals(Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString()) ||
                        question.imageType.equals(Constants.AccountImageType.BRANDINGIMAGE.toString()))) {
                    User user = userService.getCurrentUser()
                    Account account = userService.getAccount(user)
                    List<AccountImages> accountImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id?.toString(), question.imageType)
                    accountImages.forEach({
                        question.choices.add(getQuestionAnswerChoice(it.id.toString(), it.name))
                    })
                }

                question.choices.each { QuestionAnswerChoice choice ->
                    Boolean choiseAllowed = Boolean.TRUE

                    if (choiseAllowed && choice.allowedUnits) {
                        if (combinedUnits) {
                            if (CollectionUtils.containsAny(combinedUnits, choice.allowedUnits)) {
                                choiseAllowed = Boolean.TRUE
                            } else {
                                choiseAllowed = Boolean.FALSE
                            }
                        } else {
                            choiseAllowed = Boolean.FALSE
                        }
                    }

                    if (choiseAllowed && choice.allowedResourceSubTypes) {
                        if (resource && resource.resourceSubType && choice.allowedResourceSubTypes.contains(resource.resourceSubType)) {
                            choiseAllowed = Boolean.TRUE
                        } else {
                            choiseAllowed = Boolean.FALSE
                        }
                    }

                    if (choiseAllowed && parentQuestion) {
                        if (!choice.showOnlyForQuestionIds || choice.showOnlyForQuestionIds.contains(parentQuestion.questionId)) {
                            choiseAllowed = Boolean.TRUE
                        } else {
                            choiseAllowed = Boolean.FALSE
                        }
                    }

                    Boolean allowSeeingChoiceDisabled = false

                    if (choiseAllowed && choice.licenseKey && !skipLicenseKeyCheck) {
                        if (licensedFeatures.contains(choice.licenseKey)) {
                            choiseAllowed = Boolean.TRUE
                        } else {
                            choiseAllowed = Boolean.FALSE

                            if (["costCalculationMethod"].contains(question.questionId)) {
                                allowSeeingChoiceDisabled = true
                            }
                        }
                    }

                    if (choiseAllowed) {
                        filteredChoices.add(choice)
                    } else if (allowSeeingChoiceDisabled) {
                        //Groovy fast constructor sometimes doesn't work in domain
                        filteredChoices.add(getQuestionAnswerChoice("", choice.answer, true))
                    }
                }
            } catch(ConcurrentModificationException e) {
                // Threw concurrent modification exception for unknown reason, additionalQestionsQuery is in memory but should not be edited anywhere?
            }
        } else if (question?.eolChoicesFromSubType) {
            Locale locale = localeResolverUtil.resolveLocale()
            ResourceType subType = resource?.resourceTypeObject

            if (resource?.impacts && org.apache.commons.collections.CollectionUtils.containsAny(
                    ["C1", "C2", "C3", "C4", "C3-C4", "C1-C4"], resource?.impacts?.keySet()?.toList())) {

                filteredChoices.add(getQuestionAnswerChoice(Constants.EOLProcess.IMPACTS_FROM_EPD.toString(), "eolProcess.eol_from_epd", locale)) // "Use EOL defined in EPD"
            }
            filteredChoices.add(getQuestionAnswerChoice(Constants.EOLProcess.DO_NOTHING.toString(), "eolProcess.do_nothing", locale)) // "Do nothing"
            if (!subType?.impossibleToReuseAsMaterial) {
                filteredChoices.add(getQuestionAnswerChoice(Constants.EOLProcess.REUSE_AS_MATERIAL.toString(), "eolProcess.reuse_as_material", locale)) // "Reuse as material"
            }

            if (subType && subType.eolProcessAllowedChoices) {
                List<EolProcess> eolProcesses

                if(eolProcessCache){
                    eolProcesses = eolProcessCache.getEolProcessesByEolProcessIds(subType.eolProcessAllowedChoices)
                } else {
                    eolProcesses = eolProcessService.getEolProcessesByEolProcessIds(subType.eolProcessAllowedChoices)
                }

                if (eolProcesses) {
                    EolProcess inactiveEolProcess
                    if (userGivenAnswer) {
                        inactiveEolProcess = eolProcesses.find({userGivenAnswer.equals(it.eolProcessId) && !it.active})
                    }

                    eolProcesses.sort({eolProcessService.getLocalizedName(it)  }).each { EolProcess eolProcess ->
                        if (eolProcess.active) {
                            filteredChoices.add(getQuestionAnswerChoice(
                                    eolProcess.eolProcessId, eolProcessService.getLocalizedName(eolProcess)))
                        }
                    }

                    if (inactiveEolProcess) {
                        filteredChoices.add(getQuestionAnswerChoice(
                                inactiveEolProcess.eolProcessId, eolProcessService.getLocalizedName(inactiveEolProcess)))
                    }
                }
            }
        } else if (question?.subTypeChoices) {
            resourceTypeService.getActiveResourceTypes()?.each { ResourceType resourceType ->
                if (resourceType.subType && question.subTypeChoices.contains(resourceType.subTypeGroup)) {
                    filteredChoices.add(getQuestionAnswerChoice(
                            resourceType.subType, resourceTypeService.getLocalizedName(resourceType)))
                }
            }
            filteredChoices = filteredChoices.sort({it.answer.get("EN")?.toLowerCase()})
        }
        return filteredChoices
    }

    String getFormattedAnswer(answer, Boolean perUnit = null, Boolean total = null) {
        String formattedAnswer = "0"

        if (answer) {
            User user = userService.getCurrentUser()
            try {
                if (answer && perUnit) {
                    DecimalFormat dfCost = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
                    dfCost.applyPattern('#,###,###.##')
                    formattedAnswer = dfCost.format(answer.toString()?.replace(",",".")?.toDouble())
                } else if (answer && total) {
                    DecimalFormat dfTotal = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
                    dfTotal.applyPattern('#,###,###')
                    formattedAnswer = dfTotal.format(answer.toString()?.replace(",",".")?.toDouble())
                }
            } catch (Exception e) {
                formattedAnswer = "0"
            }
        }
        return formattedAnswer
    }

    boolean getDisableReadXmlButton(Indicator indicator, Entity entity) {
        boolean disable = true

        if (indicator && entity) {
            EntityFile entityFile = valueReferenceService.getFileForEntity(indicator.rsetSource, entity)

            if (entityFile) {
                disable = false
            }
        }
        return disable
    }

    def getDefaultValueFromOtherResourceSubtype(Question question, Entity entity, Resource resource) {
        def value
        if (entity && entity.defaults && resource) {
            String valueToGet = entity.defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)

            if (valueToGet) {
                ResourceType subType = resourceService.getSubType(resource)

                if (subType) {

                    if (valueToGet.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                        value = DomainObjectUtil.callGetterByAttributeName(question?.defaultValueFromResourceSubtype, subType)
                    } else {
                        value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS, subType)
                    }
                }
            }
        }
        return value
    }

    Integer getDefaultTransportFromSubType(Entity entity, Resource resource, String questionId) {
        Integer defaultValue

        if (entity && entity.defaults && resource) {
            String valueToGet = entity.defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
            if (valueToGet) {
                ResourceType subType = resourceService.getSubType(resource)

                if (subType) {
                    String quantityAnswer

                    if (questionId?.equals(Constants.TransportDistanceQuestionId.TRANSPORTDISTANCE_KMLEG2.toString())) {
                        if (valueToGet.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                            quantityAnswer = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS_LEG2, subType)?.toString()
                        }
                    } else {
                        quantityAnswer = DomainObjectUtil.callGetterByAttributeName(valueToGet, subType)?.toString()
                    }

                    if (quantityAnswer != null && quantityAnswer.isNumber()) {
                        defaultValue = Math.round(quantityAnswer.toDouble()).intValue()
                    }
                }
            }
        }
        return defaultValue
    }

    String getTransportLeg2DefaultValueFromResourceOrSubType(Entity entity, Resource resource, Question question) {
        String value
        if (entity && entity.defaults && resource) {
            String defaultTransportValue = entity.defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)

            if (defaultTransportValue && question?.defaultValueFromResource) {
                if (defaultTransportValue.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                    value = DomainObjectUtil.callGetterByAttributeName(question.defaultValueFromResource, resource)
                } else {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS_LEG2, resourceService.getSubType(resource))
                }
            } else if (question?.defaultValueFromResourceSubtype) {
                if (defaultTransportValue.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                    value = DomainObjectUtil.callGetterByAttributeName(question.defaultValueFromResourceSubtype, resource)
                } else {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS_LEG2, resourceService.getSubType(resource))
                }
            }
        }
        return value
    }

    String getDefaultValueFromResourceOrSubType(Entity entity, Resource resource, Question question) {
        String value
        if (entity && resource) {
            String defaultTransportValue
            if(entity?.defaults) {
                defaultTransportValue = entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)
            }
            if (question?.defaultValueFromResource) {
                if (defaultTransportValue && defaultTransportValue?.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS, resourceService.getSubType(resource))
                } else {
                    value = DomainObjectUtil.callGetterByAttributeName(question.defaultValueFromResource, resource)
                }
            } else if (question?.defaultValueFromResourceSubtype) {
                if (defaultTransportValue && defaultTransportValue?.equals(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT_RICS)) {
                    value = DomainObjectUtil.callGetterByAttributeName(com.bionova.optimi.construction.Constants.TRANSPORT_RESOURCE_ID_RICS, resourceService.getSubType(resource))
                } else {
                    value = DomainObjectUtil.callGetterByAttributeName(question.defaultValueFromResourceSubtype, resource)
                    if (!value) {
                        value = DomainObjectUtil.callGetterByAttributeName(question.defaultValueFromResourceSubtype, resourceService.getSubType(resource))
                    }
                }
            }
        }
        return value
    }

    String getDefaultValueFromUserAttribute(String defaultValueFromUser) {
        User user = userService.getCurrentUser()
        String value

        if (defaultValueFromUser) {
            value = DomainObjectUtil.callGetterByAttributeName(defaultValueFromUser, user)?.toString()
        }
        return value
    }

    List<Map>  getJsonAutocompleteChoices(Question question) {
        List<Map> filteredChoices = []
        if (!question) {
            return filteredChoices
        }

        if (question.choices) {
            question.choices.each {
                Map map = [:]
                map.put("value", it.localizedAnswer)
                map.put("data", [resourceId: it.answerId, questionId: question.questionId])
                filteredChoices.add(map)
            }
        } else if (question.subTypeChoices) {
            resourceTypeService.getActiveResourceTypes()?.each { ResourceType resourceType ->
                if (resourceType.subType && question.subTypeChoices.contains(resourceType.subTypeGroup)) {
                    Map map = [:]
                    map.put("value", resourceTypeService.getLocalizedName(resourceType))
                    map.put("data", [resourceId: resourceType.subType, questionId: question.questionId])
                    filteredChoices.add(map)
                }
            }
            filteredChoices = filteredChoices.sort({it.get("value")})
        } else if (question.resourceGroups) {
            getFilteredResources(null, null, null, null, Boolean.TRUE,
                    null, null, null, Boolean.TRUE, question)?.findAll({
                it != null
            })?.each {
                Map map = [:]
                map.put("value", optimiResourceService.getLocalizedName(it))
                map.put("data", [resourceId: it.resourceId, questionId: question.questionId])
                filteredChoices.add(map)
            }
        }
        return filteredChoices
    }

    List<Resource> getFilteredResources(Entity entity, Indicator indicator = null, String queryId = null,
                              String filterOnCriteriaFromUi = null, Boolean unique = Boolean.TRUE, Integer limit = null,
                              Integer skip = null, String queryString = null, Boolean alsoInactive = Boolean.FALSE, Question question) {
        if (!question) {
            return []
        }

        if (!question.originalInputType) {
            question.originalInputType = question.inputType
        }
        def resources = getResources(Boolean.FALSE, limit, skip, queryString, alsoInactive, question)
        List<Resource> foundResources

        if (resources && !resources.isEmpty()) {
            foundResources = new ArrayList<Resource>(resources)

            if (question.filterResources && entity) {
                def requiredValue

                resources.each { Resource r ->
                    boolean filterOk = true

                    question.filterResources.each { key, value ->
                        requiredValue = entity.getResourceFromBasicQueryQuestion(key)?.resourceId

                        if (!requiredValue) {
                            requiredValue = DomainObjectUtil.callGetterByAttributeName(key, entity)
                        }

                        if (requiredValue != null) {
                            def resourceValue

                            resourceValue = DomainObjectUtil.callGetterByAttributeName(value, r, "${key}: ${value}")

                            if (resourceValue) {
                                if (resourceValue instanceof List && !resourceValue.collect({
                                    it.toString().toLowerCase()
                                }).contains(requiredValue.toString().toLowerCase())) {
                                    filterOk = false
                                } else if (!resourceValue.toString().toLowerCase().equals(requiredValue.toString().toLowerCase())) {
                                    filterOk = false
                                }
                            } else {
                                filterOk = false
                            }

                            if (!filterOk) {
                                foundResources.remove(r)
                            }
                        }
                    }
                }
            }
            IndicatorQuery indicatorQueryWithResourceFilter

            if (queryId) {
                indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                    it.queryId.equals(question.queryId) && it.resourceFilterCriteria
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
                                                foundResources.remove(r)
                                                break
                                            }
                                        } else if (!requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                            foundResources.remove(r)
                                            break
                                        }
                                    } else {
                                        if (!requiredValues.contains(null)) {
                                            foundResources.remove(r)
                                            break
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (filterOnCriteriaFromUi && indicator && queryId) {
                QueryApplyFilterOnCriteria applyFilterOnCriteria = indicator.indicatorQueries?.find({
                    queryId.equals(it.queryId) && it.applyFilterOnCriteria
                })?.applyFilterOnCriteria
                foundResources = foundResources.findAll({ Resource r -> filterOnCriteriaFromUi.equals(DomainObjectUtil.callGetterByAttributeName(applyFilterOnCriteria.name, r)) })
            }
        }

        if (question.useTextIfListEmpty && (!foundResources || foundResources?.isEmpty())) {
            question.inputType = "text"
        } else {
            question.inputType = question.originalInputType
        }

        if (unique) {
            return foundResources?.unique({ Resource r -> r.resourceId })
        } else {
            return foundResources
        }
    }

    List<Resource> getResources(Boolean alsoNonDefaults = Boolean.FALSE, Integer limit = null, Integer skip = null,
                     String queryString = null, Boolean alsoInactive = Boolean.FALSE, Question question) {
        if (!question) {
            return []
        }
        List<Resource> resources

        if (question.skipResourceTypes || question.resourceGroups || question.allowResourceTypes) {
            resources = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(question.resourceGroups,
                    question.skipResourceTypes, alsoNonDefaults, limit, skip, queryString, alsoInactive, question.allowResourceTypes)
        }

        if (resources && question.requiredEnvironmentDataSourceStandards) {
            resources = resources.findAll({ Resource r -> r.environmentDataSourceStandard in question.requiredEnvironmentDataSourceStandards })
        }
        return resources
    }

    List<Question> getGroupedQuestions(Question question) {
        List<Question> groupedQuestions = []

        if (!question) {
            return groupedQuestions
        }

        String queryId = question.queryId ?: "additionalQuestionsQuery"
        if (queryId && question.groupedQuestionIds) {
            Query query = queryService.getQueryByQueryId(queryId, true)
            groupedQuestions = query.getAllQuestions()?.findAll({ Question q -> question.groupedQuestionIds.contains(q.questionId) })
        }
        return groupedQuestions
    }
}
