package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.util.UnitConversionUtil
import grails.converters.JSON
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.web.multipart.MultipartHttpServletRequest

class ConstructionController {
    def userService
    def optimiResourceService
    def constructionService
    def datasetService
    def queryService
    def unitConversionUtil
    def questionService
    def accountService
    def configurationService
    def resourceTypeService
    def accountImagesService
    def channelFeatureService
    def flashService


    def index () {
        User user = userService.getCurrentUser(true)
        List<Construction> constructions = []
        String accountId = params.accountId
        String groupId = params.group
        Boolean ungrouped = params.boolean("unGrouped")
        List <ConstructionGroup> constructionGroups = []
        Account account
        ConstructionGroup group

        if (groupId) {
            group = constructionService.getConstructionGroupById(groupId)

            if (group?.privateAccountId) {
                accountId = group.privateAccountId
            }
        }

        Boolean editable = Boolean.FALSE

        if (accountId) {
            account = accountService.getAccount(accountId)
        }
        if (account) {
            constructions = constructionService.getConstructionsByAccountId(accountId)

            if (user && (user.internalUseRoles||account.mainUserIds?.contains(user.id.toString()))) {
                editable = Boolean.TRUE
            }
        } else if (group) {
            constructions = constructionService.getConstructionsByGroup(group.groupId)
            if (user && user.internalUseRoles) {
                editable = Boolean.TRUE
            }
        } else if (ungrouped) {
            constructions = constructionService.getConstructions()?.findAll({!it.constructionGroup})
            constructionGroups = constructionService.getAllConstructionGroups()
            if (user && user.internalUseRoles) {
                editable = Boolean.TRUE
            }
        } else if (user.internalUseRoles) {
            constructions = constructionService.getConstructions()
            if (user && user.internalUseRoles) {
                editable = Boolean.TRUE
            }
        }


        if (constructionGroups) {
            List <String> accounts = accountService.getAccounts()?.collect({it.companyName})
            constructionGroups = constructionGroups.findAll({!accounts.contains(it.name)})
        }
        Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
        Map <String, String> isoCodesForFlags = [:]
        Map <String,List <String>> resourceNames = [:]
        Question classificationParameterQuestion
        List<QuestionAnswerChoice> classificationParams

        if (constructionCreationQuery) {
            classificationParameterQuestion = (Question) constructionCreationQuery?.getAllQuestions()?.find({ Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS.equals(it.questionId) })
            classificationParams = classificationParameterQuestion?.choices

            if (classificationParams) {
                classificationParams = classificationParams.sort({ a, b -> !a.orderNumber ? !b.orderNumber ? 0 : 1 : !b.orderNumber ? -1 : a.orderNumber <=> b.orderNumber ?: a.userClass <=> b.userClass})
            }
        }

        constructions.each { Construction construction ->
            String isoCode = optimiResourceService.getresourceIsoCode(construction.country)
            if (isoCode && !isoCodesForFlags.get(construction.id)) {
                isoCodesForFlags.put(construction.id?.toString(), isoCode)
            }
            List<String> resourceNameList = []

            construction?.resourceIds?.each { String resourceId ->

                Resource resource = optimiResourceService.getResourceWithGorm(resourceId, null, Boolean.FALSE)
                if (resource) {
                    resourceNameList.add(optimiResourceService.getLocalizedName(resource))
                }
                if (resourceNameList && !resourceNameList.isEmpty() && !resourceNames.get(construction.id)) {
                    resourceNames.put(construction.id?.toString(), resourceNameList)
                }
                if (construction.importFile == "Manually added") {
                    construction.importFile = g.message(code: "importFile.manually_added")
                }
            }
        }

        String constructionTypePath

        if (System.getProperty("islocalhost")) {
            constructionTypePath = "/app/assets/constructionTypes/"
        } else {
            String filePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "constructionTypesPath")
            constructionTypePath = filePath
        }
        [constructions: constructions.sort({it?.nameEN}), isoCodesForFlags: isoCodesForFlags, resourceNames: resourceNames, editable: editable,
         classificationParams: classificationParams, classificationParameterQuestion: classificationParameterQuestion,
         account: account, constructionTypePath: constructionTypePath, group: group, user: user, ungrouped:ungrouped, constructionGroups:constructionGroups, queryId:constructionCreationQuery?.queryId,
         showImperial: user?.unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value
        ]
    }

    def constructionsManagementPage() {
        Map<ConstructionGroup, Map<String, Integer>> constructionCountsByGroup = [:]
        List<ConstructionGroup> constructionGroups = constructionService.getAllConstructionGroups()
        Integer privateConstructions = 0
        Integer publicConstructions = 0

        constructionGroups?.each { ConstructionGroup group ->
            Integer actives = Construction.collection.count([constructionGroup: group.groupId, locked: true])?.intValue()
            Integer all = Construction.collection.count([constructionGroup: group.groupId])?.intValue()
            group.privateAccountId? privateConstructions++ : publicConstructions++
            Map<String, Integer> counts = [:]
            counts.put("actives", actives ?: 0)
            counts.put("all", all ?: 0)
            constructionCountsByGroup.put(group, counts)
        }

        long allConstructions = Construction.collection.count()
        long constructionsWithGroup = Construction.collection.count([constructionGroup: [$exists: true]])
        long constructionsWithoutGroup = Construction.collection.count([constructionGroup: null])
        long activeAmount = Construction.collection.count([locked:true])

        User user = userService.getCurrentUser(true)

        [constructionCountsByGroup: constructionCountsByGroup, activeAmount:activeAmount, allConstructions: allConstructions,
         constructionsWithoutGroup: constructionsWithoutGroup, constructionsWithGroup: constructionsWithGroup, user: user, privateCount: privateConstructions,
         publicCount: publicConstructions]
    }

    def showUsedResources() {
        User user = userService.getCurrentUser(true)

        if (user?.internalUseRoles) {
            List<ConstructionGroup> constructionGroups = constructionService.getAllConstructionGroups()
            Map<String, List<Document>> resourcesInConstructions = [:]
            Map<String, Object> query = [classificationParamId: [$ne: "3"]]
            // Getting large amount of data as documents instead converting to domain object is so much faster
            List<Document> constructions = constructionService.getConstructionsAsDocuments(query, [datasets: 1, classificationQuestionId: 1, classificationParamId: 1, nameEN: 1, constructionGroup: 1, importFile: 1])

            constructions.each { Document construction ->
                construction.datasets?.each { dataset ->
                    if (dataset && dataset.resourceId) {
                        String resourceIdAndProfileId = "${dataset.resourceId}.${dataset.profileId}"

                        List<Document> existing = resourcesInConstructions.get(resourceIdAndProfileId) ?: []

                        if (!existing.find({it._id.equals(construction._id)})) {
                            existing.add(construction)
                            resourcesInConstructions.put((resourceIdAndProfileId), existing)
                        }
                    }
                }
            }
            [resourcesInConstructions: resourcesInConstructions, constructionGroups: constructionGroups]
        } else {
            redirect controller: "main", action: "list"
        }
    }

    def constructionGroupForm () {
        ConstructionGroup constructionGroup
        if (params.id) {
            constructionGroup = constructionService.getConstructionGroupById(params.id)
        }
        [constructionGroup:constructionGroup]
    }

    def addNewConstructionGroup () {
        ConstructionGroup constructionGroup = new ConstructionGroup(params)
        constructionGroup.groupId = UUID.randomUUID().toString()
        if (constructionGroup && constructionGroup.validate()) {
            constructionGroup.save(flush:true, failOnError:true)
            redirect action: "constructionsManagementPage"
        }else {
            flash.fadeErrorAlert = "Error in adding group: ${renderErrors(bean: constructionGroup)}"
            redirect action: "constructionsManagementPage"
        }
    }

    def editConstructionGroup() {
        ConstructionGroup constructionGroup
        if (params.id) {
            constructionGroup = constructionService.getConstructionGroupById(params.id)
            constructionGroup?.properties = params

            if (constructionGroup && constructionGroup.validate()) {
                constructionGroup.merge(flush: true, failOnError: true)
                List<Construction> constructionsWithGroup = constructionService.getConstructionsByGroup(constructionGroup.groupId)
                constructionsWithGroup?.each { Construction construction ->
                    construction.constructionGroup = constructionGroup.groupId
                    construction.merge(flush: true, failOnError: true)
                }

                flash.fadeSuccessAlert = "Saved constructionGroup ${constructionGroup.name}"


            }
            redirect action: "constructionsManagementPage"
        }
    }

    def deleteConstructionGroup() {
        ConstructionGroup group
        if (params.id) {
            group = constructionService.getConstructionGroupById(params.id)
            if (group) {
                group.delete(flush: true, failonError: true)
                List<Construction> constructionsWithGroup = constructionService.getConstructionsByGroup(group.groupId)

                constructionsWithGroup?.each { Construction construction ->
                    construction.constructionGroup = null
                    construction.merge(flush: true, failOnError: true)
                }

                redirect action: "constructionsManagementPage"
            }
        }
    }

    def removeConstructionFromGroup() {
        String id = params.id
        Construction construction = null
        if (id) {
            construction = constructionService.getConstruction(id)
            construction.constructionGroup = null
            construction.merge(flush: true, failOnError: true)
        } else {
            flashService.setErrorAlert('removeConstructionFromGroup missing id in params', true)
        }
        render([output: "removed ${constructionService.getLocalizedName(construction)} from group", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addConstructionToGroup() {
        String id = params.id
        String group = params.group
        Construction construction = null
        if (id && group) {
            ConstructionGroup constructionGroup = constructionService.getConstructionGroupById(group)
            construction = constructionService.getConstruction(id)
            construction.constructionGroup = constructionGroup.groupId
            construction.merge(flush: true, failOnError: true)
        } else {
            flashService.setErrorAlert("addConstructionToGroup Missing id and group in params $params", true)
        }
        render([output: "added ${constructionService.getLocalizedName(construction)} to group", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def form () {
        String constructionId = params.id
        Boolean duplicateConstruction = params.boolean("duplicate")
        String classificationQuestionId = params.classificationQuestionId
        String classificationParamId = params.classificationParamId
        String accountId = params.accountId
        String groupId = params.group
        Construction construction
        Construction copyConstruction
        String privateConstructionCompanyName
        Boolean locked = Boolean.FALSE
        User user = userService.getCurrentUser(true)
        Question componentQuestion
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
        def languages = optimiResourceService.getSystemLocales(channelFeature)
        if (constructionId) {
            construction = constructionService.getConstruction(constructionId)
            locked = construction?.locked ? Boolean.TRUE : Boolean.FALSE
        }
        if (construction && duplicateConstruction) {
            def constructionDoc = constructionService.getConstructionAsDocument(construction.id as String)
            copyConstruction = new Construction(constructionDoc)
            copyConstruction.id = new ObjectId()
            copyConstruction.nameEN = "Copy of " + construction.nameEN
            locked = Boolean.FALSE
        }
        Account account

        if (accountId) {
            account = accountService.getAccount(accountId)
            privateConstructionCompanyName = account?.companyName
        }

        Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
        List <QueryFilter> supportedFilters = constructionCreationQuery?.supportedFilters
        List<Resource> countries = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["constructionAreas"], null, null, null, null, null)

        Map <String, String> lockMandatoryFilters = [:]
        Map <String, String> constructionTypes = [:]
        QueryFilter resourceTypeFilter = supportedFilters?.find({it.resourceAttribute.equals("resourceType")})
        String questionIdForResources
        String sectionIdForResources
        QuerySection sectionForDatasets
        Question classificationQuestion
        List <Question> additionalQuestions = []
        List <Question> constructionAdditionalQuestions = []
        Question constructionThicknessQuestion

        if (constructionCreationQuery && classificationQuestionId) {
            classificationQuestion = questionService.getQuestion(constructionCreationQuery, classificationQuestionId)
            List<QuestionAnswerChoice> tempAnswerChoices = classificationQuestion?.choices
            lockMandatoryFilters = tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.lockMandatoryFilters
            constructionTypes = tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.constructionTypes
            constructionAdditionalQuestions = constructionService.getAdditionalQuestions(tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.constructionAdditionalQuestionIds)

            constructionThicknessQuestion = constructionAdditionalQuestions?.find({it.isThicknessQuestion})

            if (constructionThicknessQuestion) {
                constructionAdditionalQuestions.removeIf({it.questionId == constructionThicknessQuestion.questionId})
            }

            String componentQuestionId =  tempAnswerChoices?.find({
                classificationParamId.equals(it.answerId)
            })?.componentQuestionId
            List<Question> questions = constructionCreationQuery.getAllQuestions()
            componentQuestion = questions?.find({ componentQuestionId.equals(it.questionId) })


            if (componentQuestion) {
                questionIdForResources = componentQuestion.questionId
                sectionIdForResources = componentQuestion.sectionId
                sectionForDatasets = constructionCreationQuery.getSections()?.find({ sectionIdForResources.equals(it.sectionId) })
                additionalQuestions = componentQuestion.defineAdditionalQuestions(null, sectionForDatasets, Boolean.TRUE)
            }
        }

        if (lockMandatoryFilters && supportedFilters) {
            user.userSplitFilterChoices = [:]
            List<QueryFilter> lockedFilters = []
            lockMandatoryFilters.each { String key, String value ->
                QueryFilter lockedFilter = supportedFilters.find({ it.resourceAttribute.equals(key) })
                if (lockedFilter) {
                    if (user.userSplitFilterChoices) {
                        user.userSplitFilterChoices.put(key, value)
                    } else {
                        user.userSplitFilterChoices = [(key): value]
                    }
                    lockedFilters.add(lockedFilter)
                }
            }

            if (lockedFilters) {
                supportedFilters.removeAll(lockedFilters)
            } else {
                user.userSplitFilterChoices = [:]
            }
        } else {
            user.userSplitFilterChoices = [:]
        }
        user = userService.updateUser(user)


        String unitSystem = user?.unitSystem
        List<String> units = queryService.getAllowedUnitsByQueryAndUserSettings(constructionCreationQuery,user)

        String fieldName
        if (sectionIdForResources && questionIdForResources) {
            fieldName = sectionIdForResources + "." + questionIdForResources;
        }

        if (locked) {
            flash.warningAlert = "<i class='fa fa-lock'></i>  ${message(code: 'construction.locked_warning')}"
        }
        ConstructionGroup group
        if (groupId) {
            group = constructionService.getConstructionGroupById(groupId)
        }

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

        Boolean showInheritConstructionServiceLife = Boolean.FALSE
        if (constructionAdditionalQuestions && constructionAdditionalQuestions.find({"serviceLife".equals(it.questionId)})) {
            showInheritConstructionServiceLife = Boolean.TRUE
        }
        Boolean showImperial = UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(user?.unitSystem)

        List<ResourceType> constructionResourceTypes = resourceTypeService.getResourceSubTypesByResourceType("construction")
        String resourceSubType = construction ? construction.resourceSubType : null
        List<AccountImages> brandImages = []
        if (user.internalUseRoles) {
            brandImages = accountImagesService.getAllAccountImagesByType(Constants.AccountImageType.BRANDINGIMAGE.toString())
        } else {
            brandImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id?.toString(), Constants.AccountImageType.BRANDINGIMAGE.toString())
        }

        [construction: copyConstruction?:construction, duplicateConstruction:duplicateConstruction, countries: countries, user: user, supportedFilters: supportedFilters, constructionThicknessQuestion: constructionThicknessQuestion,
         resourceTypeFilter:resourceTypeFilter, queryId:constructionCreationQuery?.queryId, questionIdForResources:questionIdForResources, sectionIdForResources:sectionIdForResources,question:componentQuestion, section:sectionForDatasets, additionalQuestions:additionalQuestions, units:units,
         fieldName:fieldName, classificationQuestionId: classificationQuestionId, classificationParamId:classificationParamId, lockMandatoryFilters:lockMandatoryFilters, showImperial: showImperial, account: account, brandImages: brandImages,
         privateConstructionAccountId:accountId, privateConstructionCompanyName:privateConstructionCompanyName,locked:locked, constructionTypes:constructionTypes, showInheritConstructionServiceLife: showInheritConstructionServiceLife,
         group:group, constructionAdditionalQuestions:constructionAdditionalQuestions, languages:languages, additionalQuestionResources: additionalQuestionResources, constructionResourceTypes: constructionResourceTypes, resourceSubType: resourceSubType]

    }


    def save() {
        boolean ok = true
        String classificationQuestionId = params.classificationQuestionId
        String classificationParamId = params.classificationParamId
        String privateConstructionAccountId = params.privateConstructionAccountId
        String companyName = params.privateConstructionCompanyName
        String queryId = params.queryId
        String group = params.group
        List<QuestionAnswerChoice> classificationParams = []
        Construction construction
        Query constructionCreationQuery = queryService.getQueryByQueryId(queryId, true)
        Map<String, String> resourceParameters = [:]
        List<String> additionalQuestionIds = []
        if (classificationQuestionId && classificationParamId) {
            classificationParams = questionService.getQuestion(constructionCreationQuery, classificationQuestionId)?.choices
            resourceParameters = classificationParams?.find({
                classificationParamId.equals(it.answerId)
            })?.resourceParameters

            additionalQuestionIds = classificationParams?.find({
                classificationParamId.equals(it.answerId)
            })?.constructionAdditionalQuestionIds
        }

        if (params.id) {
            construction = constructionService.getConstruction(params.id)
            construction?.setProperties(params)
        } else {
            construction = new Construction(params)
            construction.importFile = "Manually added"
        }
        constructionService.setBooleanProperties(construction, params)
        construction.nameES = construction?.nameES?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameSE = construction?.nameSE?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameIT = construction?.nameIT?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameNO = construction?.nameNO?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameFR = construction?.nameFR?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameDE = construction?.nameDE?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameFI = construction?.nameFI?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameEN = construction?.nameEN?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameNL = construction?.nameNL?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameHU = construction?.nameHU?.replaceAll("'", "’")?.replaceAll("\"", "")
        construction.nameJP = construction?.nameJP?.replaceAll("'", "’")?.replaceAll("\"", "")

        Map <String, Object> additionalQuestionAnswers = [:]
        if (additionalQuestionIds) {
            additionalQuestionIds?.each { String questionId ->
                if (params.get(questionId)) {
                    additionalQuestionAnswers.put(questionId, params.get(questionId))
                }
            }
        }
        if (params.brandImageId) {
            construction.brandName = accountImagesService.getAccountImage(params.brandImageId)?.name
            construction.brandImageId = params.brandImageId
        }

        if (additionalQuestionAnswers) {
            construction.additionalQuestionAnswers = additionalQuestionAnswers
        }

        if (!construction.constructionGroup && privateConstructionAccountId) {
            ConstructionGroup constructionGroup = constructionService.getConstructionGroupByAccountId(privateConstructionAccountId)

            if (!constructionGroup) {
                constructionGroup = new ConstructionGroup()
                constructionGroup.groupId = UUID.randomUUID().toString()
                constructionGroup.name = companyName
                constructionGroup.privateAccountId = privateConstructionAccountId
                if (constructionGroup?.validate()) {
                    constructionGroup = constructionGroup.save(flush: true, failOnError: true)
                } else {
                    ok = false
                    flashService.setErrorAlert("Error in saving constructionGroup: ${renderErrors(bean: constructionGroup)}")
                }
                construction.constructionGroup = constructionGroup.groupId
            } else {
                construction.constructionGroup = constructionGroup.groupId
            }
        }

        if (ok && construction && construction.validate()) {
            if (construction.unit && unitConversionUtil.isImperialUnit(construction.unit)) {
                construction.imperialUnit = construction.unit
                construction.unit = unitConversionUtil.transformImperialUnitToEuropeanUnit(construction.unit)
            } else {
                construction.imperialUnit = null
            }

            if (!construction.constructionId) {
                construction.constructionId = "constructionId${new ObjectId().toString()}".toString()
            }

            construction = construction.save(flush: true, failOnError: true)

            if (construction.locked||params.save) {
                construction = datasetService.saveDatasetsForConstructions(params, request, construction.id?.toString(), session, Query.QUERYID_CONSTRUCTION_CREATOR)
            }

            if (construction.datasets) {
                List<String> resourceIdsForConstruction = []
                construction.datasets.each { Dataset d ->
                    resourceIdsForConstruction.add(d.resourceId)
                }
                construction.resourceIds = resourceIdsForConstruction
                construction = construction.merge(flush: true, failOnError: true)
                if (construction.datasets) {
                    constructionService.createConstructionResource(construction, resourceParameters)
                }
            }
            flash.fadeSaveAlert = "Construction saved successfully"
            redirect action: "form", params: [id: construction.id, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, accountId:privateConstructionAccountId, group:group]
        } else {
            flash.fadeErrorAlert = "Error in saving construction: ${renderErrors(bean: construction)}"
            redirect action: "form", params: [id: params.id, classificationParamId: classificationParamId, classificationQuestionId: classificationQuestionId, accountId:privateConstructionAccountId, group:group]
        }
    }

    def disable() {
        Construction construction
        String renderer = ""
        if (params.id) {
            construction = constructionService.getConstruction(params.id)
            if (construction) {
                construction.locked = Boolean.FALSE
                Resource resource = optimiResourceService.getResourceWithGorm(construction.mirrorResourceId, null, Boolean.TRUE)

                if (resource) {
                    optimiResourceService.deactivateResource(resource)
                }
                construction = construction.merge(flush: true, failOnError:true)
                renderer = "Disable done for construction ${constructionService.getLocalizedName(construction)}"
            } else {
                flashService.setErrorAlert("No construction found with id ${params.id}", true)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params, it's missing id", true)
        }
        render([output: renderer, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def delete() {
        Construction construction
        String renderer = ""
        if (params.id) {
            construction = constructionService.getConstruction(params.id)
            if (construction) {
                User user = userService.getCurrentUser(true)
                Resource mirrorResource = optimiResourceService.getResourceByConstructionId(construction.id?.toString())
                if (mirrorResource) {
                    mirrorResource.active = false
                    mirrorResource = mirrorResource.merge(flush:true, failOnError:true)
                    optimiResourceService.deleteResourceFromCache(mirrorResource)
                }
                construction.delete(flush: true, failonError: true)

                log.info("User ${user ? user.username : "null"} removed ${constructionService.getLocalizedName(construction)} from database.")
                flashService.setFadeSuccessAlert("User ${user ? user.username : "null"} removed ${constructionService.getLocalizedName(construction)} from database.", true)
                renderer = "removeal done for construction ${constructionService.getLocalizedName(construction)} from database."
            } else {
                flashService.setErrorAlert("No construction found with id ${params.id}", true)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params, it's missing id", true)
        }
        render([output: renderer, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def toggleLockedState() {
        Construction construction
        if (params.id) {
            construction = constructionService.getConstruction(params.id)
            if (construction) {
                if (construction?.locked) {
                    construction.locked = Boolean.FALSE
                    flash.fadeSuccessAlert = "${message(code: 'construction.unlock_success')} <i class='fa fa-unlock'></i>"
                    construction.merge(flush: true, failOnError: true)
                }else {
                    construction.locked = Boolean.TRUE
                    construction.merge(flush:true, failOnError:true)
                }
            }

        }

    }

    def importConstructions () {
        User user = userService.getCurrentUser(true)
        String group = params.group
        String accountId = params.accountId
        String accountName = params.accountName
        ConstructionGroup constructionGroup

        if (group) {
            constructionGroup = constructionService.getConstructionGroupById(group)

            if (accountId && constructionGroup && !constructionGroup.privateAccountId) {
                constructionGroup.privateAccountId = accountId
                constructionGroup.save(flush: true, failOnError: true)
            }
        }
        if (accountName && !group) {
            constructionGroup = constructionService.getConstructionGroupByName(accountName)

            if (!constructionGroup) {
                constructionGroup = new ConstructionGroup()
                constructionGroup.groupId = UUID.randomUUID().toString()
                constructionGroup.name = accountName
                constructionGroup.privateAccountId = accountId
                constructionGroup.save(flush: true, failOnError: true)
            }
        }

        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                if (accountId) {
                    redirect action: "index", params:[accountId:accountId]
                } else {
                    redirect action: "index", params:[group:group]
                }
            } else {
                try {
                    if (accountId || userService.getDataManagerAndAdmin(user)) {
                        Query constructionCreationQuery = queryService.getQueryByQueryId(params.queryId, true)
                        Boolean isUploadingNmd = params.boolean('isUploadingNmd')
                        Map importedConstructions = constructionService.importExcel(excelFile, constructionCreationQuery?.queryId, constructionGroup?.groupId, accountId, accountName, isUploadingNmd)
                        String okMessage = ""
                        String errors = ""
                        importedConstructions.each { key, value ->
                            if (key.equals("Exception") || key.equals("ImportErrors")) {
                                errors = "${errors} ${key} -- ${value}"
                            } else {
                                okMessage = "${okMessage} ${key} -- ${value} </br>"
                            }

                        }

                        if (okMessage) {
                            flash.successAlert = okMessage
                        }

                        if (errors) {
                            flash.errorAlert = errors
                        }


                        if (accountId) {
                            redirect(action: "index", params: [accountId:accountId])
                        } else {
                            redirect(action: "index", params: [group:group])
                        }
                    } else {
                        flash.errorAlert = "Unauthorized"
                        log.error("CONSTRUCTION IMPORT: User: ${user.username} is unauthorized to upload constructions!")
                        flashService.setErrorAlert("CONSTRUCTION IMPORT: User: ${user.username} is unauthorized to upload constructions!", true)
                        if (accountId) {
                            redirect action: "index", params:[accountId:accountId]
                        } else {
                            redirect action: "index", params:[group:group]
                        }
                    }
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    if (accountId) {
                        redirect action: "index", params:[accountId:accountId]
                    } else {
                        redirect action: "index", params:[group:group]
                    }
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            if (accountId) {
                redirect action: "index", params:[accountId:accountId]
            } else {
                redirect action: "index", params:[group:group]
            }
        }
    }

    def convertImperialMultiplierToEuropean() {
        String userGivenUnit = params.userGivenUnit
        String value = params.quantity
        String thickness_mm = params.thickness_mm
        String density = params.density

        if (userGivenUnit && value && value.isNumber()) {
            String targetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(userGivenUnit)
            value = unitConversionUtil.doConversion(value.toDouble(), thickness_mm && thickness_mm.isNumber() ? thickness_mm.toDouble() : null, userGivenUnit, null, null, null, null, null, density && density.isNumber() ? density.toDouble() : null, targetUnit)
        } else {
            flashService.setErrorAlert("Incorrect params $params in convertImperialMultiplierToEuropean", true)
        }
        render([output: value, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def changeStatus() {
        Construction construction
        String res = 'ok'

        if (params.id) {
            construction = constructionService.getConstruction(params.id)

            if (construction) {
                Resource mirrorResource = constructionService.getMirrorResourceObject(construction)

                if (!mirrorResource) {
                    // this condition is same as in save() action
                    if (construction.datasets) {
                        String classificationQuestionId = params.classificationQuestionId
                        String classificationParamId = params.classificationParamId
                        if (classificationQuestionId && classificationParamId) {
                            Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                            Map<String, String> resourceParameters = questionService.getQuestion(constructionCreationQuery, classificationQuestionId)?.choices?.find({
                                classificationParamId.equals(it.answerId)
                            })?.resourceParameters
                            mirrorResource = constructionService.createConstructionResource(construction, resourceParameters)
                        } else {
                            res = 'error'
                            flashService.setErrorAlert("Construction $construction.name (uuid: ${params.id}) does not have mirrorResource and params $params is missing classificationQuestionId and/or classificationParamId. Cannot lock/unlock construction.", true)
                        }
                    } else {
                        res = 'error'
                        flashService.setErrorAlert("Construction $construction.name (uuid: ${params.id}) does not have mirrorResource and datasets. Cannot lock/unlock construction.", true)
                    }
                }

                if (mirrorResource) {
                    Boolean lock = params.boolean("lock")
                    Boolean unlock = params.boolean("unlock")

                    if (lock) {
                        mirrorResource.active = Boolean.TRUE
                        mirrorResource.merge(flush: true, failOnError: true)
                        construction.locked = Boolean.TRUE
                        construction.merge(flush: true, failOnError: true)
                        flash.fadeSuccessAlert = "${message(code: 'construction.locked_warning')} <i class='fa fa-lock'></i>"
                    } else if (unlock) {
                        mirrorResource.active = Boolean.FALSE
                        mirrorResource.merge(flush: true, failOnError: true)
                        construction.locked = Boolean.FALSE
                        construction.merge(flush: true, failOnError: true)
                        flash.fadeSuccessAlert = "${message(code: 'construction.unlock_success')} <i class='fa fa-unlock'></i>"
                    }
                }
            } else {
                res = 'error'
                flashService.setErrorAlert("Construction with uuid: ${params.id} cannot be found. Cannot lock/unlock construction.", true)
            }
        } else {
            res = 'error'
            flashService.setErrorAlert("Params $params is missing construction id. Cannot lock/unlock construction.", true)
        }
        render([output: res, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getWarningsForGroups() {
        Map<String, String> groupIdsWithWarnings = [:]

        params.list("groupIds")?.each { String groupId ->
            String warnings = ""
            List<Construction> constructions = constructionService.getConstructionsByGroup(groupId)

            if (constructions) {
                if (constructions.find({constructionService.getMissMatchedData(it.datasets)})) {
                    warnings = warnings + "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${message(code: 'query.resource.not_in_filter')}\" id=\"${groupId}notInFilter\"><i class=\"icon-alert\"></i></a>"
                }

                if (constructions.find({constructionService.getInactiveData(it.datasets)})) {
                    warnings = warnings + "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"Inactive constituents found\" id=\"${groupId}notInFilter\">${asset.image(src: "img/icon-warning.png", style: "max-width:14px")}</i></a>"
                }
            }

            groupIdsWithWarnings.put((groupId), warnings)
        }
        render([groupIdsWithWarnings: groupIdsWithWarnings, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}
