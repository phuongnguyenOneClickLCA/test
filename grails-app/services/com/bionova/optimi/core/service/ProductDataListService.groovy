package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.lang.StringUtils

import javax.servlet.http.HttpServletRequest

@Transactional
class ProductDataListService {

    def queryService
    def accountService
    def datasetService
    def optimiResourceService
    LoggerUtil loggerUtil
    FlashService flashService
    def licenseService
    def userService

    def getProductDataLists() {
        List <ProductDataList> productDataLists = []
        productDataLists = ProductDataList.list()
        return productDataLists
    }

    def getProductDataList(String id) {
        ProductDataList productDataLists
        if (id) {
            productDataLists = ProductDataList.get(DomainObjectUtil.stringToObjectId(id))
        }
        return productDataLists
    }

    def getProductDataListById(String productDataListsId) {
        ProductDataList productDataList
        if (productDataListsId) {
            productDataList = ProductDataList.findByListId(productDataListsId)
        }
        return productDataList
    }

    def getProductDataListByAccountId(String privateProductDataListAccountId) {
        List <ProductDataList> productDataList = []
        if (privateProductDataListAccountId) {
            productDataList = ProductDataList.findAllByPrivateProductDataListAccountId(privateProductDataListAccountId)
        }
        return productDataList
    }

    def getProductDataListByAccountIds(List<String> privateProductDataListAccountIds) {
        List<ProductDataList> productDataList = []
        if (privateProductDataListAccountIds) {
            productDataList = ProductDataList.collection.find([privateProductDataListAccountId: [$in: privateProductDataListAccountIds]])?.collect({it as ProductDataList})
        }
        return productDataList
    }

    def getPresetDataLists() {
        return ProductDataList.findAllByPrivateProductDataListCompanyNameIsNull()
    }

    def getLinkedDataListsFromAccount(Account account) {
        List<ProductDataList> linkedLists = []
        if (account) {
            linkedLists = ProductDataList.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(account.linkedProductDataListIds).flatten())
        }
        return linkedLists
    }

    def getLinkedDataListsFromAccountId(String accountId) {
        List<ProductDataList> linkedLists = []
        if (accountId) {
            Account account = accountService.getAccount(accountId)
            if(account) {
                linkedLists = ProductDataList.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(account.linkedProductDataListIds).flatten())
            }
        }
        return linkedLists
    }

    def getLinkedDataListsFromAccountIds(List<String> accountIds) {
        List<ProductDataList> linkedLists = []
        if (accountIds) {
            List<Account> accounts = accountService.getAccountsByIds(accountIds)
            List<String> linkedProductDataListIds = accounts?.findResult({it.linkedProductDataListIds ?: null})?.flatten()?.unique()

            if (linkedProductDataListIds) {
                linkedLists = ProductDataList.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(linkedProductDataListIds))
            }
        }
        return linkedLists
    }


    def getAdditionalQuestions (List<String> questionIds) {
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        def allQuestions = additionalQuestionsQuery?.getAllQuestions()
        List <Question> additionalQuestions = []
        questionIds.each { String questionId ->
            Question q = allQuestions?.find({
                questionId.equals(it.questionId)
            })

            if (q && !additionalQuestions.contains(q)) {
                additionalQuestions.add(q)
            }

        }
        return additionalQuestions
    }

    def saveDatasetsForProductDataLists(GrailsParameterMap parameterMap, HttpServletRequest request, String productDataListId, String queryId) {

        ProductDataList productDataList

        if (productDataListId) {
            try {
                productDataList = getProductDataList(productDataListId)
                def paramNames = parameterMap?.findAll({ !"resourceIds".equals(it.key) })?.collect({
                    it.key.toString()
                })
                List<Dataset> datasets = []
                def sectionId
                def questionId
                def additionalQuestionId
                def tokenized
                def resourceId
                List<String> seqNos
                List<String> userGivenUnits
                List<String> tempDatasetIds
                List<String> persistedClass
                List<String> tempDatasetImportFieldsId
                List<String> tempGroupingDatasetName

                paramNames?.each {
                    if (!com.bionova.optimi.construction.Constants.NON_SAVEABLE_DATASET_PARAMETERS.contains(it)) {
                        if (it.contains(".")) {
                            tokenized = it.tokenize(".")
                            sectionId = tokenized[0]
                            questionId = tokenized[1]

                            if (questionId.contains("_manualId_") && request.getParameterValues(it)) {
                                tempDatasetIds = request.getParameterValues(it).toList()
                                questionId = questionId.split("_manualId_")[0]
                            } else {
                                tempDatasetIds = null
                            }

                            if (questionId.contains("_unit_")) {
                                if (request.getParameterValues(it).toList()) {
                                    userGivenUnits = request.getParameterValues(it).toList()
                                } else {
                                    userGivenUnits = null
                                }
                                userGivenUnits = request.getParameterValues(it).toList()
                                questionId = questionId.split("_unit_")[0]
                            } else {
                                userGivenUnits = null
                            }

                            if (questionId.contains("_seqNo_")) {
                                if (request.getParameterValues(it)) {
                                    seqNos = request.getParameterValues(it).toList()
                                } else {
                                    seqNos = null
                                }
                                questionId = questionId.split("_seqNo_")[0]
                            } else {
                                seqNos = null
                            }

                            if (questionId?.contains("_additional_")) {
                                additionalQuestionId = questionId.split("_additional_")[1]
                                questionId = questionId.split("_additional_")[0]
                            } else {
                                additionalQuestionId = null
                            }

                            if (questionId?.contains("_persistedClass_") && request.getParameterValues(it)) {
                                persistedClass = request.getParameterValues(it).toList()
                                questionId = questionId.split("_persistedClass_")[0]
                            } else {
                                persistedClass = null
                            }

                            if (questionId?.contains("_datasetImportFieldsId_")) {
                                if (request.getParameterValues(it).toList()) {
                                    tempDatasetImportFieldsId = request.getParameterValues(it).toList()
                                } else {
                                    tempDatasetImportFieldsId = null
                                }
                                questionId = questionId.split("_datasetImportFieldsId_")[0]
                            } else {
                                tempDatasetImportFieldsId = null
                            }

                            if (questionId.contains("_groupingDatasetName_") && request.getParameterValues(it)) {
                                tempGroupingDatasetName = request.getParameterValues(it).toList()
                                questionId = questionId.split("_groupingDatasetName_")[0]
                            } else {
                                tempGroupingDatasetName = null
                            }

                            if (tokenized[2]) {
                                resourceId = tokenized[2].trim()
                            } else {
                                resourceId = null
                            }

                            Dataset dataset = datasets?.find({ Dataset d ->
                                d.queryId == queryId && d.sectionId == sectionId && d.questionId == questionId && d.resourceId == resourceId})

                            if (!dataset) {
                                dataset = new Dataset()
                                dataset.queryId = queryId
                                dataset.sectionId = sectionId
                                dataset.questionId = questionId
                                dataset.resourceId = resourceId
                                datasets.add(dataset)
                            }

                            if (additionalQuestionId) {
                                if (dataset.tempAdditionalQuestionAnswers) {
                                    dataset.tempAdditionalQuestionAnswers.put((additionalQuestionId), request.getParameterValues(it))
                                } else {
                                    dataset.tempAdditionalQuestionAnswers = [(additionalQuestionId): request.getParameterValues(it)]
                                }
                            } else if (userGivenUnits && !dataset.tempUserGivenUnits) {
                                dataset.tempUserGivenUnits = new ArrayList<String>(userGivenUnits)
                            } else if (seqNos && !dataset.tempSeqNrs) {
                                dataset.tempSeqNrs = new ArrayList<String>(seqNos)
                            } else if (tempDatasetIds && !dataset.tempDatasetManualIds) {
                                dataset.tempDatasetManualIds = new ArrayList<String>(tempDatasetIds)
                            } else if (tempGroupingDatasetName && !dataset.tempGroupingDatasetName) {
                                dataset.tempGroupingDatasetName = new ArrayList<String>(tempGroupingDatasetName)
                            } else {
                                dataset.answerIds = request.getParameterValues(it)
                            }
                        }
                    }
                }
                datasets = datasetService.removeUnneededDuplicatesFromResourceDatasets(datasets)
                productDataList = saveDatasetsForProductDataList(productDataList, datasets, queryId)
            } catch (Exception e) {
                loggerUtil.error(log, "Error in saving product data lists, error", e)
                flashService.setErrorAlert("Error in saving product data lists: ${e.getMessage()}", true)
            }
        }
        return productDataList
    }

    ProductDataList saveDatasetsForProductDataList(ProductDataList productDataList, List<Dataset> datasets, String queryId) {

        try {
            def saveableDatasets = []
            boolean answersFound

            if (!datasets?.isEmpty()) {

                datasets?.each { Dataset dataset ->
                    answersFound = false

                    if (StringUtils.isNotBlank(dataset.answerIds?.get(0)) || dataset.resourceId || !dataset.monthlyAnswers?.isEmpty() || !dataset.quarterlyAnswers?.isEmpty()) {
                        answersFound = true
                    }

                    if (answersFound) {
                        saveableDatasets.add(dataset)
                    }
                }
            }
            Collection<Dataset> finalDatasets = []
            if (!saveableDatasets.isEmpty()) {
                saveableDatasets = resolveListResources(saveableDatasets)

                def formattedDatasets = []
                Dataset formattedDataset

                for (Dataset dataset in saveableDatasets) {
                    if (dataset.answerIds && !dataset.monthlyAnswers && !dataset.quarterlyAnswers) {
                        // We have to nullify the quantity first so that existing quantities aren't eg doubled
                        dataset.quantity = null

                        for (answer in dataset.answerIds) {
                            def formattedAnswer = answer.toString().replace(',', '.')

                            if (formattedAnswer.isNumber()) {
                                dataset.quantity = dataset.quantity != null ? dataset.quantity + formattedAnswer.toDouble() : formattedAnswer.toDouble()
                            }
                        }
                    }
                    // We have to create copy, otherwise numeric answer is back to null. Don't know the reason for this.
                    formattedDataset = new Dataset()
                    formattedDataset = dataset
                    formattedDatasets.add(formattedDataset)
                }
                formattedDatasets = datasetService.resolveAdditionalQuestionsWithoutQuantity(formattedDatasets)
                formattedDatasets = datasetService.resolveAdditionalQuestions(formattedDatasets)
                datasetService.addDefaultProfilesIfMissing(formattedDatasets)

                if (finalDatasets) {
                    finalDatasets.addAll(formattedDatasets)
                } else {
                    finalDatasets = formattedDatasets
                }
            } else {
                finalDatasets = datasetService.removeExistingDatasets(finalDatasets, queryId)
            }

            if (!finalDatasets) {
                productDataList.datasets = []
            } else {
                productDataList.datasets = finalDatasets.findAll({it.resourceId})
            }
            productDataList = productDataList.merge(flush: true, failOnError: true)

        } catch (Exception e) {
            loggerUtil.error(log, "Error in saving datasets:", e)
            flashService.setErrorAlert("Error in saving datasets: ${e.getMessage()}", true)
        }
        return productDataList
    }

    List<Dataset> resolveListResources(List<Dataset> datasets) {
        def resolvedDatasets = []

        datasets?.each { Dataset dataset ->
            if (!dataset.resourceId) {
                def resourceId = optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, Boolean.TRUE, Boolean.FALSE)?.resourceId

                if (resourceId) {
                    dataset.resourceId = resourceId
                }

            } else if (!dataset.tempAdditionalQuestionAnswers && dataset.resourceId && dataset.answerIds?.size() > 1) {
                for (int i = 1; i < dataset.answerIds.size(); i++) {
                    Dataset anotherRow = datasetService.createCopy(dataset)
                    anotherRow.answerIds = [dataset.answerIds[i]]
                    resolvedDatasets.add(anotherRow)
                }
                dataset.answerIds = [dataset.answerIds[0]]
            }
            resolvedDatasets.add(dataset)
        }
        return resolvedDatasets
    }

    def getEcoinventLicenseCap(Account account) {
        Integer resourceCapFromLicense = 0
        if (account) {
            List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)

            if (licensedFeatureIds) {
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT_INTERNAL_TEST) })) { resourceCapFromLicense = 5 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT50) })) { resourceCapFromLicense = 50 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT100) })) { resourceCapFromLicense = 100 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT150) })) { resourceCapFromLicense = 150 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT200) })) { resourceCapFromLicense = 200 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT300) })) { resourceCapFromLicense = 300 }
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.ECOINVENT400) })) { resourceCapFromLicense = 400 }
            }
        }
        return resourceCapFromLicense
    }

    /**
     * Get list of all resourceIds in data lists associated with account
     * @param licensedFeatureIds list of licensed feature ids of account
     * @param account
     * @return
     */
    List<String> getResourceIdsInDataList(List<String> licensedFeatureIds, Account account) {
        List<String> resourceIdsInDataList = []
        try {
            if (account && licensedFeatureIds?.find({ Feature.ECOINVENT_WITH_CAP?.contains(it) })) {
                List<ProductDataList> productDataLists = getProductDataListByAccountId(account?.id?.toString())
                List<ProductDataList> linkedDataLists = getLinkedDataListsFromAccountId(account?.id?.toString())

                if (linkedDataLists) {
                    resourceIdsInDataList.addAll(linkedDataLists.collect({ it.resourceIds })?.flatten())
                }
                if (productDataLists) {
                    resourceIdsInDataList.addAll(productDataLists.collect({ it.resourceIds })?.flatten())
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in getResourceIdsInDataList", e)
            flashService.setErrorAlert("Error in getResourceIdsInDataList: ${e.getMessage()}", true)
        }
        return resourceIdsInDataList
    }
}
