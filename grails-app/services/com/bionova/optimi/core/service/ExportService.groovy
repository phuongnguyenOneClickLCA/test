package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationTotalResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import grails.web.mapping.LinkGenerator
import groovy.json.JsonBuilder
import org.apache.commons.io.FileUtils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class ExportService {

    CalculationResultService calculationResultService
    UserService userService
    QuerySectionService querySectionService
    DatasetService datasetService
    LinkGenerator grailsLinkGenerator
    AccountImagesService accountImagesService
    XmlService xmlService
    WordDocumentService wordDocumentService
    FlashService flashService
    LoggerUtil loggerUtil
    QuestionService questionService
    QueryService queryService

    /**
     * creates an array of generic details for the epd hub json
     *
     * @param metaData
     * @param childEntity
     * @param entity
     * @param indicator
     */
    void createMetaDataForEPDHubJson(Map<String, Object> metaData, Entity childEntity, Entity entity, Indicator indicator) {

        Date currentDate = new Date()
        User user = userService.getCurrentUser()
        if(entity && childEntity) {
            metaData.put("timeOfExport", getTime(currentDate))
            metaData.put("exportUser", user?.username)
            metaData.put("timeLastEdited", getTime(childEntity?.lastUpdated))
            metaData.put("timeCreated", getTime(childEntity.dateCreated))
            metaData.put("childEntityId", childEntity.id.toString())
            metaData.put("childEntityName", childEntity.name)
            metaData.put("parentEntityId", entity.id.toString())
            metaData.put("parentEntityName", entity.name)
            metaData.put("designReady", childEntity.isIndicatorReady(indicator))
            metaData.put("designURL", createDesignURL(entity.id.toString()))
        }
    }
    /**
     * returns date in the specified date format
     * @param date
     * @return
     */
    String getTime(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        return dateFormat.format(date)
    }
    /**
     * to create the design url for metadata array
     * @param entityId
     * @return
     */
    String createDesignURL(String entityId) {

        return grailsLinkGenerator.link(controller: "entity", action: "show", params: [entityId: entityId, showToolsNote: true], absolute: true)?.toString()
    }
    /**
     * gets the calculation datasets for each category
     * we need transport resource information in the JSON somewhere too. This is not currently exported
     * as the datasets under each question will not contain the process based resources. To solve this we also
     * need to add list of resources in the resultsByCategory part. So we can see clearly which resources have been used
     * for which result category calculation.
     *
     * @param resources
     * @param allowedManualIds
     * @param calculationResultDatasets
     * @param resultByCategory
     */
    void injectTransportResourceInfo(Set<Dataset> calculationResultDatasets, Map<String, Object> resultByCategory, Indicator indicator, ResourceCache resourceCache) {
        List<Map<String, Object>> listOfResDatasetMaps = []
        removeUnnecessaryAddQ(indicator, calculationResultDatasets as List<Dataset>)
        calculationResultDatasets.each { Dataset dataset ->
            collectRequiredDatasetAndResourceParams(listOfResDatasetMaps, dataset, resourceCache.getResource(dataset), indicator)
        }
        resultByCategory.put("calculationDatasets", listOfResDatasetMaps)
    }
    /**
     * creates an array of results based on the result category
     *
     * @param resultCategories
     * @param resultByCategoryList
     * @param indicatorResults
     * @param childEntity
     * @param indicator
     */
    void createResultByCategory(List<ResultCategory> resultCategories, List<Map<String, Object>> resultByCategoryList, List<CalculationResult> indicatorResults, Entity childEntity, Indicator indicator, ResourceCache resourceCache) {

        resultCategories.each { ResultCategory r ->
            Map<String, Double> ruleIdResultsMap = [:]
            Map<String, Object> resultByCategory = [:]

            indicatorResults.each { CalculationResult calculationResult ->
                if (calculationResult?.resultCategoryId == r?.resultCategoryId) {
                    ruleIdResultsMap.put(calculationResult.calculationRuleId, calculationResult.result)
                    resultByCategory.put("resultCategoryId", calculationResult.resultCategoryId)
                    resultByCategory.put("resultsByCalculationRule", ruleIdResultsMap)
                    Set<Dataset> calculationResultDatasets = calculationResultService.getDatasets(calculationResult, childEntity)
                    if (calculationResultDatasets) {
                        injectTransportResourceInfo(calculationResultDatasets, resultByCategory, indicator, resourceCache)
                    }
                }
            }

            if (resultByCategory) {
                resultByCategoryList.add(resultByCategory)
            }
        }
    }
    /**
     * Digital epd hub json contains results by category , calculation datasets for each result category ,
     * total results by rule , metadata of generic details , queries , sections , questions and all the datasets
     *
     * @param childEntity
     * @param projEntity
     * @param indicator
     * @param queriesListMap
     * @param totalsByRule
     * @param resultByCategoryList
     * @param metaData
     */
    void createDigitalEPDJson(Entity childEntity, Entity projEntity, Indicator indicator, Map<String, Object> queriesListMap,
                              List<Map<String, Map<String, Double>>> totalsByRule, List<Map<String, Object>> resultByCategoryList, Map<String, Object> metaData) {

        List<CalculationResult> indicatorResults
        List<ResultCategory> resultCategories
        Map<String, Double> totalByCalculationRule
        Map<String, Map<String, Double>> totalByRuleMap = [:]
        List<Map<String, Object>> queries = []

        List<String> exportedResultCategoryIds = getFilterValues(indicator, "exportedResultCategoryIds")
        List<String> exportedCalculationRuleIds = getFilterValues(indicator, "exportedCalculationRuleIds")

        indicatorResults = childEntity.getCalculationResultObjects(indicator.indicatorId, null, null)
        resultCategories = indicator.getResolveResultCategories(projEntity).findAll({ ResultCategory rc -> rc.resultCategoryId in exportedResultCategoryIds })
        totalByCalculationRule = childEntity.calculationTotalResults.find({ CalculationTotalResult it -> (indicator.indicatorId == it?.indicatorId) })?.totalByCalculationRule?.findAll { Map.Entry<String, Double> it -> it.key in exportedCalculationRuleIds }
        totalByRuleMap.put("totalByCalculationRule", totalByCalculationRule)
        totalsByRule.add(totalByRuleMap)
        Set<Dataset> resourcesToBeCached = projEntity.datasets
        resourcesToBeCached.addAll(childEntity.datasets)
        ResourceCache resourceCache = ResourceCache.init(resourcesToBeCached?.toList())
        createQueriesArray(indicator, projEntity, childEntity, queries, resourceCache)
        queriesListMap.put("queries", queries)
        if (resultCategories && indicatorResults) {
            createResultByCategory(resultCategories, resultByCategoryList, indicatorResults, childEntity, indicator, resourceCache)
        }
        createMetaDataForEPDHubJson(metaData, childEntity, projEntity, indicator)
    }
    /**
     * Method that creates the list of all queries , sections, questions and datasets
     *
     * @param indicator
     * @param childEntity
     * @param queries
     */
    void createQueriesArray(Indicator indicator, Entity projEntity, Entity childEntity, List<Map<String, Object>> queries, ResourceCache resourceCache) {

        List<Query> listOfQueries = indicator.getQueries(childEntity)
        listOfQueries.add(queryService.getBasicQuery())
        listOfQueries.each { Query query ->
            Map<String, Object> queriesMap = [:]
            List ListOfSectionsMap = []
            queriesMap.put("queryId", query.queryId)

            query.getSections()?.each { QuerySection querySection ->
                if (!indicator.hideSectionTotally(querySection)) {
                    Map<String, Object> sectionsMap = [:]
                    sectionsMap.put("sectionId", querySection.sectionId)
                    ListOfSectionsMap.add(sectionsMap)
                    queriesMap.put("sections", ListOfSectionsMap)
                    createQuestionsArray(sectionsMap, projEntity, childEntity, querySection, indicator, query, resourceCache)
                }
            }
            queries.add(queriesMap)
        }
    }
    /**
     * creates the questions array which will contain all questions and datasets
     *
     * @param sectionsMap
     * @param childEntity
     * @param querySection
     * @param indicator
     * @param query
     * @param resourceCache
     */
    void createQuestionsArray(Map<String, Object> sectionsMap, Entity entity, Entity childEntity, QuerySection querySection, Indicator indicator, Query query, ResourceCache resourceCache) {
        List<Map<String, Object>> questions = []
        Entity entitytoUse = childEntity
        if(query.queryId == "basicQuery"){
            entitytoUse = entity
        }
        querySectionService.getAllUnderlyingQuestions(entitytoUse.entityClass, querySection)?.each { Question question ->

            if (!indicator.hideQuestions?.get(querySection.sectionId)?.contains(question.questionId)) {
                Map<String, Object> questionsMap = [:]
                questionsMap.put("questionId", question.questionId)
                questionsMap.put("inputType", question.inputType)
                questionsMap.put("resourceBasedQuestion", question.inputType == "select" && question.resourceGroups)
                questionsMap.put("question", question.question)
                questionsMap.put("help", question.help)
                questionsMap.put("verificationPoints", question.verificationPoints)
                createDatasetsList(entitytoUse, query, querySection, question, questionsMap, indicator, resourceCache)
                questions.add(questionsMap)
                sectionsMap.put("questions", questions)
            }
        }
    }
    /**
     * Method to collect all datasets used in the entity based on query/section/question
     * @param childEntity
     * @param query
     * @param querySection
     * @param question
     * @param questionsMap
     * @param indicator
     * @param resourceCache
     */
    void createDatasetsList(Entity childEntity, Query query, QuerySection querySection, Question question, Map<String, Object> questionsMap, Indicator indicator, ResourceCache resourceCache) {
        List<Dataset> foundDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, query.queryId, querySection.sectionId, question.questionId)
        Resource resource
        List<Map<String, Object>> listOfResDatasetMaps = []
        removeUnnecessaryAddQ(indicator, foundDatasets)
        foundDatasets.each { Dataset dataset ->
            resource = resourceCache.getResource(dataset)
            collectRequiredDatasetAndResourceParams(listOfResDatasetMaps, dataset, resource, indicator)
        }
        questionsMap.put("datasets", listOfResDatasetMaps)
    }
    /**
     * to get the config for required params from the addQ, dataset and resource
     * @param indicator
     * @param filter
     * @return
     */
    List<String> getFilterValues(Indicator indicator, String filter) {
        Map<String, List<String>> digitalEPD = indicator.epdHubExportContent.get("digitalEPD")
        return digitalEPD.get(filter)
    }
    /**
     * json needs only the addQs mentioned in the config
     * @param indicator
     * @param datasets
     */
    void removeUnnecessaryAddQ(Indicator indicator, List<Dataset> datasets) {
        List<String> additionalQuestionIds = getFilterValues(indicator, "additionalQuestionIds")
        if (additionalQuestionIds && datasets) {
            datasets.each { Dataset dataset ->
                dataset.additionalQuestionAnswers?.removeAll { Map.Entry<String, Object> addQ -> !additionalQuestionIds.contains(addQ.key) }
            }
        }
    }
    /**
     *json needs only a few params from dataset and resource as mentioned in the config
     * @param listOfResDatasetMaps
     * @param dataset
     * @param resource
     * @param indicator
     */
    void collectRequiredDatasetAndResourceParams(List<Map<String, Object>> listOfResDatasetMaps, Dataset dataset, Resource resource, Indicator indicator) {
        String answer
        List<String> resourceParameters = getFilterValues(indicator, "resourceParameters")
        List<String> datasetParameters = getFilterValues(indicator, "datasetParameters")
        Map<String, Object> ResourceAndDatasetMap = [:]
        ResourceAndDatasetMap.putAll(datasetParameters?.collectEntries { String datasetParam -> [(datasetParam): dataset.getProperty(datasetParam)] })
        ResourceAndDatasetMap.put("userGivenQuantity", dataset.quantity)
        Question question = questionService.getQuestion(dataset.queryId, dataset.questionId)

        if (question && question.getChoices()) {
            ResourceAndDatasetMap.put("answerId", dataset.answerIds?.get(0))
            answer = question.getChoices()?.find({ QuestionAnswerChoice it -> dataset.answerIds?.get(0)?.equals(it.answerId) })?.localizedAnswer
            ResourceAndDatasetMap.put("answer", answer)
        } else {
            ResourceAndDatasetMap.put("answer", dataset.answerIds?.get(0))
        }
        ResourceAndDatasetMap.putAll(resourceParameters?.collectEntries { String resParam -> [(resParam): resource?.getProperty(resParam)] })
        listOfResDatasetMaps.add(ResourceAndDatasetMap)
    }
    /**
     * method to generate json file from json object for digital epd zip
     * @param childEntity
     * @param indicator
     * @param projEntity
     * @return
     */
    File exportEPDJson(Entity childEntity, Indicator indicator, Entity projEntity) {

        File jsonTempFile
        JsonBuilder jsonObject = getDigitalEPDJsonObject(childEntity, indicator, projEntity)
        jsonTempFile = new File("epdjson")
        jsonTempFile.write(jsonObject.toPrettyString())
        return jsonTempFile

    }
    /**
     * method to generate json object for creating json file for digital epd zip
     * @param childEntity
     * @param indicator
     * @param projEntity
     * @return
     */
    JsonBuilder getDigitalEPDJsonObject(Entity childEntity, Indicator indicator, Entity projEntity){

        List<Map<String, Map<String, Double>>> totalsByRule = []
        List<Map<String, Map<String, Double>>> resultByCategoryList = []
        Map<String, Object> metaData = [:]
        Map<String, Object> queriesListMap = [:]

        if (indicator && childEntity && projEntity) {
            createDigitalEPDJson(childEntity, projEntity, indicator, queriesListMap, totalsByRule, resultByCategoryList, metaData)
        }
        Map jsonMap = [metaData: metaData, resultByCategory: resultByCategoryList, calculationTotalResults: totalsByRule, questionAnswers: queriesListMap]
        return new JsonBuilder(jsonMap)
    }
    /**
     * method to extract various image files for digital epd
     * config specifies the query , section and question from where the image needs to be extracted
     * @param imageType
     * @param childEntity
     * @param exportQueryContent
     * @return
     */
    File exportImagesForDigitalEPD(String imageType, Entity childEntity, Object exportQueryContent) {

        String id
        String queryId
        String sectionId
        String questionId
        Object exportQueryContentImages = exportQueryContent[imageType]

        if (!exportQueryContentImages) {
            return null
        } else {
            queryId = exportQueryContentImages["queryId"]?.getAt(0)
            sectionId = exportQueryContentImages["sectionId"]?.getAt(0)
            questionId = exportQueryContentImages["questionId"]?.getAt(0)

            Dataset dataset = childEntity.datasets?.find({ Dataset it ->
                queryId == it.queryId && sectionId == it.sectionId && questionId == it.questionId
            })

            if (dataset?.answerIds) {
                id = dataset.answerIds.get(0)
            }
            AccountImages productImage = accountImagesService.getAccountImage(id)
            return getEPDImageByType(productImage)
        }
    }
    /**
     * method to get image by type for digital epd
     * @param productImage
     * @return
     */
    File getEPDImageByType(AccountImages productImage){
        File generatedImage
        if (productImage) {
            generatedImage = new File("accountImage")
            try {
                FileUtils.writeByteArrayToFile(generatedImage, productImage.image)
            } catch (IOException io) {
                loggerUtil.error(log, "IOException ! : ${io}")
                flashService.setErrorAlert("IOException occured while writing image to file : ${io.message}", true)
            }
        }
        return generatedImage
    }
    /**
     * method to add the created file to digital epd zip
     * @param tempFile
     * @param zip
     * @param fileName
     */
    void addDataToZipFile(File tempFile, ZipOutputStream zip, String fileName) {
        ZipEntry fileEntry = new ZipEntry(fileName)
        zip.putNextEntry(fileEntry)
        if (tempFile) {
            zip.write(tempFile.bytes)
            tempFile.delete()
        }
    }
    /**
     * method to get json for digital epd
     * @param childEntity
     * @param indicator
     * @param entity
     * @param tempFile
     * @param zip
     * @param fileName
     */
    void generateEPDJson(Entity childEntity, Indicator indicator, Entity entity, File tempFile, ZipOutputStream zip, String fileName) {
        tempFile = exportEPDJson(childEntity, indicator, entity)
        addDataToZipFile(tempFile, zip, fileName)
    }
    /**
     * method to extract various image files for digital epd
     * @param indicator
     * @param tempFile
     * @param fileNames
     * @param zip
     */
    void generateImageFiles(Entity childEntity, Indicator indicator, File tempFile, Map<String, String> fileNames, ZipOutputStream zip) {

        Object listOfFilesToExport = indicator.epdHubExportContent.get("exportQueryContent")
        if (listOfFilesToExport) {
            List<String> imageFiles = listOfFilesToExport.collect { it.entrySet() }?.collect({ it.key })?.flatten()
            imageFiles.each { String it ->
                tempFile = exportImagesForDigitalEPD(it, childEntity, listOfFilesToExport)
                addDataToZipFile(tempFile, zip, fileNames.get(it))
            }
        }
    }
    /**
     * method to generate the epd ILCD EPD XML for digital epd
     * @param indicator
     * @param childEntity
     * @param entity
     * @param tempFile
     * @param zip
     * @param fileName
     */
    void generateILCDEPDXml(Entity childEntity, Indicator indicator, Entity entity, File tempFile, ZipOutputStream zip, String fileName) {
        tempFile = xmlService.getILCDEPDXml(indicator, childEntity, entity)
        addDataToZipFile(tempFile, zip, fileName)
    }
    /**
     * method to generate the epd word export for digital epd
     * config mentions which ruleId and template to use to generate the epd word doc
     * @param childEntity
     * @param indicator
     * @param tempFile
     * @param zip
     * @param fileName
     * @param reportGenerationRuleId
     */
    void generateEPDWordExport(Entity childEntity, Indicator indicator, File tempFile, ZipOutputStream zip, String fileName, String reportGenerationRuleId) {

        tempFile = wordDocumentService.createDocument(childEntity, indicator, reportGenerationRuleId, reportGenerationRuleId)
        addDataToZipFile(tempFile, zip, fileName)
    }
}
