package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.data.ResourceCache
import grails.converters.JSON
import org.bson.Document

class ProductDataListsController {

    def userService
    def productDataListService
    def queryService
    def accountService
    def questionService
    def flashService
    def licenseService
    def datasetService

    def productListManagementPage() {

        User user = userService.getCurrentUser(true)
        List<ProductDataList> productDataLists = productDataListService.getProductDataLists()
        String listId = params.listId

        if (listId) {
            productDataLists = productDataListService.getProductDataListById(listId)
        }

        Boolean editable = Boolean.FALSE

        Query productDataListsQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
        Question classificationParameterQuestion
        List <QuestionAnswerChoice> classificationParams

        if (productDataListsQuery) {
            classificationParameterQuestion = (Question) productDataListsQuery?.getAllQuestions()?.find({"productDataListsType".equals(it.questionId)})
            classificationParams = classificationParameterQuestion?.choices

            if (classificationParams) {
                classificationParams = classificationParams.sort({ a, b -> !a.orderNumber ? !b.orderNumber ? 0 : 1 : !b.orderNumber ? -1 : a.orderNumber <=> b.orderNumber ?: a.userClass <=> b.userClass})
            }
        }

        Map<ProductDataList, Map<String, Integer>> companyListCounts = [:]
        Map<ProductDataList, Map<String, Integer>> premadeListCounts = [:]
        productDataLists?.each { ProductDataList list ->
            Integer all = list.datasets?.size()
            ResourceCache resourceCache = ResourceCache.init(list.datasets)
            Integer actives = list.datasets?.collect({resourceCache.getResource(it)})?.findAll({it?.active})?.size()
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            if (list.privateProductDataListAccountId) {
                companyListCounts.put(list, counts)
            } else {
                premadeListCounts.put(list, counts)
            }
        }

        Map<String, Map<String, String>> uniqueCompanyDataLists = [:]
        List<String> organisationAccountIds = productDataLists?.collect({it?.privateProductDataListAccountId})?.flatten()?.unique()?.collect({it.toString()})
        //organisationAccountIds.forEach({log.info(it)})
        organisationAccountIds?.each ({String accountId ->

            if (accountId != "null") {
                Map<String, String> data = [:]
                List<ProductDataList> orgLists = productDataListService.getProductDataListByAccountId(accountId)
                List<ProductDataList> orgRefLists = productDataListService.getLinkedDataListsFromAccountId(accountId)

                //Organisation Name
                String name = orgLists?.collect({it.privateProductDataListCompanyName})?.flatten()?.unique()?.collect({it.toString()})

                //Total Lists (Maybe normal/reference too?)
                String companyLists = "Company lists: " + orgLists?.size()?.toString()
                String linkedLists = "Reference lists: " + orgRefLists.size()?.toString()
                List<String> totalLists = []
                if (companyLists) { totalLists.add(companyLists) }
                if (linkedLists) { totalLists.add(linkedLists) }

                data.put("totalLists", totalLists.join(",\n")?: "")

                //Type of lists + Total Resources
                List<String> paramsIds = orgLists?.collect({it.classificationParamId})?.flatten()?.unique()?.collect({it.toString()})
                List<String> paramNamesList = []
                List<String> totalListResources = []
                Integer ecoinventLicenseCap
                if (accountId) {
                    Account account = accountService.getAccount(accountId)
                    if (account) {
                        ecoinventLicenseCap = productDataListService.getEcoinventLicenseCap(account)
                    }
                }

                paramsIds?.each ({String paramsId ->
                    String paramName = classificationParams?.find({it.answerId.equals(paramsId)})?.localizedAnswer
                    paramNamesList.add(paramName)
                    Integer combined = 0
                    Integer resourceCap = 0
                    if(orgLists) {
                        orgLists?.forEach({
                            if(it.classificationParamId.equals(paramsId)) {
                                combined += it.datasets?.size()
                            }
                        })
                    }
                    if (orgRefLists) {
                        orgRefLists?.forEach({
                            if(it.classificationParamId.equals(paramsId)) {
                                combined += it.datasets?.size()
                            }
                        })
                    }
                    if (ecoinventLicenseCap) {
                        String warning = (combined > ecoinventLicenseCap)? "${asset.image(src:'img/icon-warning.png', style: 'max-width:14px; padding-bottom: 2px;')}" : ""
                        totalListResources.add(paramName + ": " + combined + " / " + ecoinventLicenseCap + " " + warning)
                    } else {
                        String warning = (combined > resourceCap)? "${asset.image(src:'img/icon-warning.png', style: 'max-width:14px; padding-bottom: 2px;')}" : ""
                        totalListResources.add(paramName + ": " + combined + " / " + resourceCap + " " + warning)
                    }

                })
                data.put("paramNames", paramNamesList.join(",\n")?: "")
                data.put("totalListResources", totalListResources.join(",\n")?: "")

                //send accountId through too
                data.put("accountId", accountId?: "")

                //Put it all into a map
                uniqueCompanyDataLists.put(name.replaceAll("\\[|\\]", ""), data)
            }
        })

        [productDataLists: productDataLists, premadeListCounts: premadeListCounts, companyListCounts: companyListCounts, user: user, editable: editable, classificationParams: classificationParams, id: listId,
         classificationParameterQuestion: classificationParameterQuestion, companyDataLists: uniqueCompanyDataLists]
    }

    def addNewProductList() {
        ProductDataList productDataList = new ProductDataList(params)
        productDataList.listId = UUID.randomUUID().toString()
        if (productDataList && productDataList.validate()) {
            productDataList.save(flush:true, failOnError:true)
            flash.fadeSuccessAlert = "Successfully created data list: ${params.name}"
            redirect action: "productListManagementPage"
        }else {
            flash.errorAlert = "Error in created data list: ${params.name}"
            redirect action: "productListManagementPage"
        }
    }

    def addNewCompanyProductDataList() {
        ProductDataList productDataList = new ProductDataList(params)
        productDataList.listId = UUID.randomUUID().toString()
        if (productDataList && productDataList.validate()) {
            productDataList.save(flush:true, failOnError:true)
            flash.fadeSuccessAlert = "Successfully created data list: ${params.name?.encodeAsHTML()}"
            redirect action: "manageLibraries", params: [accountId: params.privateProductDataListAccountId]
        } else {
            flash.errorAlert = "Error in adding list: ${renderErrors(bean: productDataList)}"
            redirect action: "manageLibraries", params: [accountId: params.privateProductDataListAccountId]
        }

    }

    def editProductDataList() {

        ProductDataList productDataList
        if (params.id) {
            productDataList = productDataListService.getProductDataList(params.id)
            productDataList?.properties = params

            if (productDataList && productDataList.validate()) {
                productDataList.merge(flush: true, failOnError: true)
                flash.fadeSuccessAlert = "Renamed data list to: ${productDataList.name}"
            }
            redirect action: "productListManagementPage"
        }
    }

    def deleteProductDataList() {
        ProductDataList productDataList
        if (params.id) {
            productDataList = productDataListService.getProductDataList(params.id)

            if (productDataList) {
                productDataList.delete(flush: true, failOnError: true)
                redirect action: "productListManagementPage"
            }
        }
    }


    def openChangeResourceNameModal() {
        render template: "changeResourceName",
                model: [resourceTableId: params.parentTableId,
                        sectionId: params.sectionId, questionId: params.questionId,
                        resourceId: params.resourceId, resourceName: params.resourceName]
    }

    def manageLibraries() {
        User user = userService.getCurrentUser()
        Account account
        String accountId = params.accountId
        List<ProductDataList> productDataLists = productDataListService.getProductDataListByAccountId(accountId)
        List<ProductDataList> referenceDataLists = productDataListService.getPresetDataLists().sort { it?.name?.toLowerCase() }

        if (accountId) {
            account = accountService.getAccount(accountId)
        } else {
            account = chainModel?.get("account")
        }

        List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)

        boolean hasEditListFeature = licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.EDIT_ORGANISATION_DATA_LISTS) }) || user.internalUseRoles

        List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccount(account)

        Boolean editable = Boolean.FALSE

        if (user && (user.internalUseRoles||(account && account.mainUserIds?.contains(user.id.toString())))) {
            editable = Boolean.TRUE
        }

        Query productDataListsQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
        Question classificationParameterQuestion
        List <QuestionAnswerChoice> classificationParams

        if (productDataListsQuery) {
            classificationParameterQuestion = (Question) productDataListsQuery?.getAllQuestions()?.find({"productDataListsType".equals(it.questionId)})
            classificationParams = classificationParameterQuestion?.choices

            if (classificationParams) {
                classificationParams = classificationParams.sort({ a, b -> !a.orderNumber ? !b.orderNumber ? 0 : 1 : !b.orderNumber ? -1 : a.orderNumber <=> b.orderNumber ?: a.userClass <=> b.userClass})
            }
        }

        int maxResourcesFromLicense = productDataListService.getEcoinventLicenseCap(account)

        int totalResources = 0
        Map<ProductDataList, Map<String, Integer>> companyListCounts = [:]
        productDataLists?.each { ProductDataList list ->
            Integer all = list.datasets?.size()
            totalResources += all
            ResourceCache resourceCache = ResourceCache.init(list.datasets)
            Integer actives = list.datasets?.collect({resourceCache.getResource(it)})?.findAll({it?.active})?.size()
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            companyListCounts.put(list, counts)
        }

        Map<ProductDataList, Map<String, Integer>> linkedDataListsCounts = [:]
        linkedDataLists?.each { ProductDataList list ->
            Integer all = list.datasets?.size()
            totalResources += all
            ResourceCache resourceCache = ResourceCache.init(list.datasets)
            Integer actives = list.datasets?.collect({resourceCache.getResource(it)})?.findAll({it?.active})?.size()
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            linkedDataListsCounts.put(list, counts)
        }

        log.info("${totalResources}, ${maxResourcesFromLicense}")
        if(totalResources > maxResourcesFromLicense) {
            flash.permErrorAlert = message(code: "pdl.list_capacity_exceeded")
        } else if (totalResources == maxResourcesFromLicense) {
            flash.warningAlert = message(code: "pdl.list_capacity_reached")
        }

        if (editable) {
            [productDataLists: productDataLists, hasEditListFeature: hasEditListFeature, companyListCounts: companyListCounts, totalResources: totalResources, maxResources: maxResourcesFromLicense, referenceListCounts: linkedDataListsCounts, referenceDataLists: referenceDataLists, account: account, user:user, editable: editable, accountId: accountId, classificationParams: classificationParams,
             classificationParameterQuestion: classificationParameterQuestion]
        } else {
            flash.errorAlert = "Permission to data list feature denied"
            redirect controller: "main", action: "list"
        }

    }


    def form() {
        String classificationQuestionId = params.classificationQuestionId
        String classificationParamId = params.classificationParamId
        boolean admin = params.boolean("admin")
        ProductDataList productDataList = productDataListService.getProductDataList(params.id)

        String privateProductDataListAccountId
        String privateProductDataListCompanyName
        String accountId = params.accountId
        Account account

        User user = userService.getCurrentUser(true)

        if (accountId) {
            account = accountService.getAccount(accountId)
            privateProductDataListAccountId = accountId
            privateProductDataListCompanyName = account?.companyName
        }

        List<ProductDataList> companyDataLists = productDataListService.getProductDataListByAccountId(accountId)
        List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccount(account)

        Query productDataListsQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
        List <QueryFilter> supportedFilters = productDataListsQuery?.supportedFilters?.clone()
        Map <String, String> lockMandatoryFilters = [:]
        QueryFilter resourceTypeFilter = supportedFilters?.find({it.resourceAttribute.equals("resourceType")})
        Question classificationQuestion
        Question componentQuestion
        String questionIdForResources
        String sectionIdForResources
        QuerySection sectionForDatasets
        List <Question> additionalQuestions = []
        List <Question> productDataListAdditionalQuestions = []
        Question productDataListThicknessQuestion

        if (productDataListsQuery && classificationQuestionId) {
            classificationQuestion = questionService.getQuestion(productDataListsQuery, classificationQuestionId)
            List<QuestionAnswerChoice> tempAnswerChoices = classificationQuestion?.choices
            lockMandatoryFilters = tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.lockMandatoryFilters

            productDataListAdditionalQuestions = productDataListService.getAdditionalQuestions(tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.constructionAdditionalQuestionIds)

            productDataListThicknessQuestion = productDataListAdditionalQuestions?.find({it.isThicknessQuestion})

            if (productDataListThicknessQuestion) {
                productDataListAdditionalQuestions.removeIf({it.questionId == productDataListThicknessQuestion.questionId})
            }

            String componentQuestionId = tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.componentQuestionId

            List<Question> questions = productDataListsQuery.getAllQuestions()
            componentQuestion = questions?.find({ componentQuestionId.equals(it.questionId) })


            if (componentQuestion) {
                questionIdForResources = componentQuestion.questionId
                sectionIdForResources = componentQuestion.sectionId
                sectionForDatasets = productDataListsQuery.getSections()?.find({ sectionIdForResources.equals(it.sectionId) })
                additionalQuestions = componentQuestion.defineAdditionalQuestions(null, sectionForDatasets, Boolean.TRUE)
            }
        }
        Map<String,String> userSplitFilterChoices = [:]
        if (lockMandatoryFilters && supportedFilters) {
            List<QueryFilter> lockedFilters = []
            lockMandatoryFilters.each { String key, String value ->
                QueryFilter lockedFilter = supportedFilters.find({ it.resourceAttribute.equals(key) })
                log.info("got the thing? ${lockedFilter?.resourceAttribute}")
                if (lockedFilter) {
                    if (userSplitFilterChoices) {
                        userSplitFilterChoices.put(key, value)
                    } else {
                        userSplitFilterChoices = [(key): value]
                    }
                    lockedFilters.add(lockedFilter)
                }
            }

            if (lockedFilters) {
                supportedFilters.removeAll(lockedFilters)
            } else {
                userSplitFilterChoices = [:]
            }
        }
        user.userSplitFilterChoices = userSplitFilterChoices
        user = userService.updateUser(user)

        Map<String, List<Document>> additionalQuestionResources = [:]
        if (additionalQuestions) {
            additionalQuestions.each { Question question ->
                List<Document> resources = question.getAdditionalQuestionResources(null, null)

                if (resources) {
                    additionalQuestionResources.put(question.questionId, resources)
                } else if (question.groupedQuestionIds) {
                    questionService.getGroupedQuestions(question)?.each {
                        List<Document> r = it.getAdditionalQuestionResources(null, null)

                        if (r) {
                            additionalQuestionResources.put(it.questionId, r)
                        }
                    }
                }
            }
        }

        String fieldName
        if (sectionIdForResources && questionIdForResources) {
            fieldName = sectionIdForResources + "." + questionIdForResources;
        }

        int totalResourcesAllowed = productDataListService.getEcoinventLicenseCap(account)
        Integer resourceIdCount = 0
        productDataList.datasets.each { Dataset d ->
            if(d.resourceId) {
                resourceIdCount ++
            }
        }
        log.info("FORM RESOURCES = ${resourceIdCount}")

        int totalResources = 0
        companyDataLists?.each { ProductDataList list ->
            totalResources += list.datasets?.size()
        }
        linkedDataLists?.each { ProductDataList list ->
            totalResources += list.datasets?.size()
        }

        Boolean locked = Boolean.FALSE
        if (!user.internalUseRoles && !productDataList.privateProductDataListAccountId) {
            locked = Boolean.TRUE
            log.info("Non-SU is viewing a premade list, locked status: ${locked}")
        }

        [productDataList: productDataList, admin: admin, locked: locked, resourcesInList: resourceIdCount, maxResources: totalResourcesAllowed, totalResources: totalResources, user: user, additionalQuestions:additionalQuestions, supportedFilters: supportedFilters, account: account,
         resourceTypeFilter:resourceTypeFilter, queryId:productDataListsQuery?.queryId, privateProductDataListAccountId: privateProductDataListAccountId, fieldName: fieldName, question:componentQuestion, section:sectionForDatasets,
         privateProductDataListCompanyName: privateProductDataListCompanyName, classificationQuestionId: classificationQuestionId, classificationParamId:classificationParamId,
         lockMandatoryFilters:lockMandatoryFilters, additionalQuestionResources: additionalQuestionResources, productDataListAdditionalQuestions: productDataListAdditionalQuestions, questionIdForResources:questionIdForResources, sectionIdForResources:sectionIdForResources]
    }

    def save() {
        log.info("SAVE PARAM> ${params}")
        String classificationQuestionId = params.classificationQuestionId
        String classificationParamId = params.classificationParamId
        String queryId = params.queryId
        boolean admin = params.boolean("admin")
        ProductDataList productDataList
        List<QuestionAnswerChoice> classificationParams = []
        List<String> additionalQuestionIds = []

        ProductDataList premadeList = productDataListService.getProductDataList(params.presetListId)

        String accountId = params.privateProductDataListAccountId
        String accountName = params.privateProductDataListCompanyName
        if(!accountId) {
            productDataList = productDataListService.getProductDataList(params.listId)
            accountId = productDataList.privateProductDataListAccountId
            accountName = productDataList.privateProductDataListCompanyName
        }

        Query productDataListsQuery = queryService.getQueryByQueryId(queryId, true)

        if (classificationQuestionId && classificationParamId) {
            classificationParams = questionService.getQuestion(productDataListsQuery, classificationQuestionId)?.choices

            additionalQuestionIds = classificationParams?.find({
                classificationParamId.equals(it.answerId)
            })?.constructionAdditionalQuestionIds
        }
        boolean adminOverride = params.boolean("adminOverride")
        if (params.listId) {
            productDataList = productDataListService.getProductDataList(params.listId)
            if(adminOverride && !productDataList.privateProductDataListAccountId) {
                productDataList?.properties = params
                productDataList.privateProductDataListAccountId = null
                productDataList.privateProductDataListCompanyName = null
            } else {
                productDataList?.properties = params
                productDataList.privateProductDataListAccountId = accountId
                productDataList.privateProductDataListCompanyName = accountName
            }
        } else {
            productDataList = new ProductDataList(params)
            productDataList.importFile = "Manually added"
        }

        Map <String, Object> additionalQuestionAnswers = [:]
        if (additionalQuestionIds) {
            additionalQuestionIds?.each { String questionId ->
                if (params.get(questionId)) {
                    additionalQuestionAnswers.put(questionId, params.get(questionId))
                }
            }
        }

        if (additionalQuestionAnswers) {
            productDataList.additionalQuestionAnswers = additionalQuestionAnswers
        }

        if (productDataList && productDataList.validate()) {

            productDataList = productDataList.save(flush: true, failOnError: true)

            if (params.action) {
                productDataList = productDataListService.saveDatasetsForProductDataLists(params, request, productDataList.id?.toString(), "productDataListsQuery")
            }

            log.info("maxResources - PARAMS> ${params.maxResources}")
            log.info("totalResources - PARAMS> ${params.totalResources}")

            Integer premadeListSize = 0
            Integer currentResources = 0
            Integer totalCount = 0
            Integer licenseCapacity = params.int("maxResources")
            boolean resourceCheck = false

            log.info("CAPACITY> ${licenseCapacity}")
            log.info("ADMIN-OVERRIDE> ${adminOverride}")
            log.info("ADMIN-OVERRIDE PARAMS> ${params.adminOverride}")

            productDataList.datasets.each { Dataset d ->
                if(d.resourceId) {
                    currentResources ++
                }
            }
            log.info("CR - DATASET> ${currentResources}")

            if (!currentResources) {
                currentResources = params.int("totalResources")
                log.info("COUNT FROM FORM> ${currentResources}")
            }
            log.info("FINAL RESOURCE COUNT> ${currentResources}")


            if (premadeList && premadeList.datasets) {
                if (!productDataList.datasets) {
                    productDataList.datasets = []
                }
                premadeList.datasets.each { Dataset d ->
                    if(d.resourceId) {
                        premadeListSize ++
                    }
                }
                log.info("PREMADE SIZE> ${premadeListSize}")
                totalCount = premadeListSize + currentResources

                if (totalCount <= licenseCapacity || adminOverride) {
                    resourceCheck = true
                    productDataList.datasets.addAll(premadeList.datasets)
                }
            }

            log.info("PREMADE SIZE AFTER PRECHECK> ${premadeListSize}")
            totalCount = premadeListSize + currentResources
            if (totalCount <= licenseCapacity || adminOverride) {
                resourceCheck = true
            }

            if (productDataList.datasets) {

                List<String> resourceIdsForProductDataList = []
                productDataList.datasets.each { Dataset d ->
                    resourceIdsForProductDataList.add(d.resourceId)
                }
                log.info("SIZE OF RESOURCEIDSFORLIST> ${resourceIdsForProductDataList?.size()}")
                productDataList.resourceIds = resourceIdsForProductDataList
                productDataList = productDataList.merge(flush: true, failOnError: true)
            }

            log.info("${resourceCheck}")
            if(admin) {
                flash.fadeSaveAlert = "Successfully saved data list: ${productDataList.name?.encodeAsHTML()}"
                redirect action: "form", params: [id: productDataList.id, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, name: productDataList.name, accountId: accountId, admin: admin]
            } else if (resourceCheck) {
                flash.fadeSaveAlert = "Successfully saved data list: ${productDataList.name?.encodeAsHTML()}"
                redirect action: "form", params: [id: productDataList.id, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, name: productDataList.name, accountId: accountId]
            } else {
                flash.permErrorAlert = "Maximum resource cap exceeded ${renderErrors(bean: productDataList)}"
                redirect action: "form", params: [id: productDataList.id, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, name: productDataList.name, accountId: accountId]
            }
        } else {
            flash.errorAlert = "Error in saving data list ${renderErrors(bean: productDataList)}"
            redirect action: "form", params: [id: params.listId, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, name: productDataList.name]
        }
    }

    def addReferenceListToAccount() {
        log.info("AddReferenceList: ${params}")
        String accountId = params.accountId
        String referenceDataListId = params.referenceDataListId
        Integer totalResources = 0
        totalResources = params.int("totalResources")
        Integer maxResources = 0
        maxResources = params.int("maxResources")
        User user = userService.getCurrentUser(true)
        log.info("has an account ${accountId} + ${referenceDataListId}")
        ProductDataList refList = productDataListService.getProductDataList(referenceDataListId)



        Account account = accountService.getAccount(accountId)
        Boolean editable = Boolean.FALSE
        Boolean admin = userService.isSystemAdmin(user) || userService.getSuperUser(user) || userService.getDataManager(user) ? Boolean.TRUE : Boolean.FALSE
        Boolean successful = Boolean.FALSE
        if (user && (admin || (account && account.mainUserIds?.contains(user.id.toString())))) {
            editable = Boolean.TRUE
        }

        Query productDataListsQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
        Question classificationParameterQuestion
        List <QuestionAnswerChoice> classificationParams

        if (productDataListsQuery) {
            classificationParameterQuestion = (Question) productDataListsQuery?.getAllQuestions()?.find({"productDataListsType".equals(it.questionId)})
            classificationParams = classificationParameterQuestion?.choices

            if (classificationParams) {
                classificationParams = classificationParams.sort({ a, b -> !a.orderNumber ? !b.orderNumber ? 0 : 1 : !b.orderNumber ? -1 : a.orderNumber <=> b.orderNumber ?: a.userClass <=> b.userClass})
            }
        }
        String errorMessage = ""

        if (account) {

            Map<ProductDataList, Map<String, Integer>> linkedDataListsCounts = [:]
            Boolean exceededMaxResources = totalResources + refList.datasets?.size() > maxResources

            log.info("has an account")
            log.info("Checking account for Ids: ${account.linkedProductDataListIds}")
            List<String> linkedProductDataListIds = account.linkedProductDataListIds
            log.info("Checking list for Ids: ${account.linkedProductDataListIds}")
            if(exceededMaxResources && !admin) {
                linkedDataListsCounts = calculateLinkedListAmount(account)
                String htmlContent = g.render([template: "referenceListTable", model: [referenceListCounts: linkedDataListsCounts, classificationParams: classificationParams, classificationParameterQuestion: classificationParameterQuestion, editable: editable, account: account, user: user]])
                errorMessage = "This reference list will exceed your license capacity, please contact support to upgrade your license"
                Map responseData = [htmlContent: htmlContent, addedAlready: false, capExceeded: true, errorMessage: errorMessage, admin: admin, (flashService.FLASH_OBJ_FOR_AJAX): flash]
                render(responseData as JSON)
            } else {
                if(linkedProductDataListIds && !linkedProductDataListIds.isEmpty()) {
                    log.info("account list does not have matching ID's and resources is less than max cap")

                    if (!linkedProductDataListIds?.contains(referenceDataListId) && (!exceededMaxResources || admin)) {
                        account.linkedProductDataListIds.add(referenceDataListId)
                        log.info("NoList & LessthanMax: ${account.linkedProductDataListIds}")
                        successful = Boolean.TRUE
                        if (admin && exceededMaxResources) {
                            errorMessage = "This reference list has exceeded the license capacity for this organisation, please remove lists or resources"
                        }
                    }
                } else {
                    log.info("has no ids linked, going to create fresh list")
                    account.linkedProductDataListIds = [referenceDataListId]
                    log.info("${account.linkedProductDataListIds}")
                    successful = Boolean.TRUE
                }

                linkedDataListsCounts = calculateLinkedListAmount(account)

                String htmlContent = g.render([template: "referenceListTable", model: [referenceListCounts: linkedDataListsCounts, classificationParams: classificationParams, classificationParameterQuestion: classificationParameterQuestion, editable: editable, account: account, user: user]])
                if(successful) {
                    accountService.saveAccount(account)
                    Map responseData = [htmlContent: htmlContent, resourceCount: refList?.datasets?.size(), errorMessage: errorMessage, admin: admin, (flashService.FLASH_OBJ_FOR_AJAX): flash]
                    render(responseData as JSON)
                } else {
                    errorMessage = "Already added reference list: ${refList.name}"
                    Map responseData = [htmlContent: htmlContent, addedAlready: true, capExceeded: false, errorMessage: errorMessage, admin: admin, (flashService.FLASH_OBJ_FOR_AJAX): flash]
                    render(responseData as JSON)
                }

            }

        }

    }

    def calculateLinkedListAmount(Account account) {
        List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccount(account)
        Map<ProductDataList, Map<String, Integer>> linkedDataListsCounts = [:]
        linkedDataLists?.each { ProductDataList list ->
            Integer all = list.datasets?.size()
            ResourceCache resourceCache = ResourceCache.init(list.datasets)
            Integer actives = list.datasets?.collect({resourceCache.getResource(it)})?.findAll({it?.active})?.size()
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            linkedDataListsCounts.put(list, counts)
        }
        return linkedDataListsCounts
    }

    def removeReferenceList() {
        String linkedListId = params.id
        String accountId = params.accountId
        Account account
        ProductDataList refList = productDataListService.getProductDataList(linkedListId)

        User user = userService.getCurrentUser(true)
        Boolean editable = Boolean.FALSE
        if (user && (user.internalUseRoles||(account && account.mainUserIds?.contains(user.id.toString())))) {
            editable = Boolean.TRUE
        }

        Query productDataListsQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
        Question classificationParameterQuestion
        List <QuestionAnswerChoice> classificationParams

        if (productDataListsQuery) {
            classificationParameterQuestion = (Question) productDataListsQuery?.getAllQuestions()?.find({"productDataListsType".equals(it.questionId)})
            classificationParams = classificationParameterQuestion?.choices

            if (classificationParams) {
                classificationParams = classificationParams.sort({ a, b -> !a.orderNumber ? !b.orderNumber ? 0 : 1 : !b.orderNumber ? -1 : a.orderNumber <=> b.orderNumber ?: a.userClass <=> b.userClass})
            }
        }

        if (accountId) {
            account = accountService.getAccount(accountId)
            if (account) {
                log.info("Pre Removal: ${account.linkedProductDataListIds}")
                account.linkedProductDataListIds.remove(linkedListId)
                log.info("Post Removal: ${account.linkedProductDataListIds}")
                accountService.saveAccount(account)
            }
        }

        List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccount(account)
        Map<ProductDataList, Map<String, Integer>> linkedDataListsCounts = [:]
        linkedDataLists?.each { ProductDataList list ->
            Integer all = list.datasets?.size()
            ResourceCache resourceCache = ResourceCache.init(list.datasets)
            Integer actives = list.datasets?.collect({resourceCache.getResource(it)})?.findAll({it?.active})?.size()
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            linkedDataListsCounts.put(list, counts)
        }
        String htmlContent = g.render([template: "referenceListTable", model: [referenceListCounts: linkedDataListsCounts, classificationParams: classificationParams, classificationParameterQuestion: classificationParameterQuestion, editable: editable, account: account, user: user]])
        Map responseData = [htmlContent: htmlContent, resourceCount: refList?.datasets?.size(), (flashService.FLASH_OBJ_FOR_AJAX): flash]
        render(responseData as JSON)
    }

    def alertTemplatePage() {
        User user = userService.getCurrentUser(true)
        flash.fadeSaveAlert = "Fading save successful message"
        flash.fadeSuccessAlert = "Fading upload successful message"
        flash.fadeInfoAlert = "Fading info shown"
        flash.fadeWarningAlert = "Fading warning message"
        flash.fadeErrorAlert = "Fading error message"
        flash.saveAlert = "Save successful message"
        flash.successAlert = "Upload successful message"
        flash.infoAlert = "Info shown"
        flash.warningAlert = "Warning message"
        flash.errorAlert = "Error message"
        flash.permSaveAlert = "Permanent save successful message"
        flash.permSuccessAlert = "Permanent upload successful message"
        flash.permInfoAlert = "Permanent info shown"
        flash.permWarningAlert = "Permanent warning message"
        flash.permErrorAlert = "Permanent error message"
        [user: user]
    }
}
