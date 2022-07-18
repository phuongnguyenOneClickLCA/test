package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.converters.JSON
import grails.util.Environment
import org.apache.commons.lang.time.DateUtils
import org.springframework.beans.factory.annotation.Autowired

import java.text.SimpleDateFormat

class SendMeDataRequestController {

    def userService
    def temporaryImportDataService
    def licenseService
    def optimiMailService
    def entityService
    def indicatorService
    def domainClassService
    def sendMeDataRequestService
    def optimiResourceService
    def queryService
    def questionService
    def newCalculationServiceProxy
    def applicationService
    def flashService

    @Autowired
    EmailConfiguration emailConfiguration

    def index() {}

    public static final String privateResourceCreationQueryId = "privateResourceCreator"

    def sendFileToAnotherUser() {
        String email = request.JSON?.email
        String note = request.JSON?.note
        String error = ""

        if (email) {
            User currentUser = userService.getCurrentUser()
            User user = userService.getUserByUsername(email)
            String importFileName = session?.getAttribute("importFileName")
            String importFilePath = session?.getAttribute("importFilePath")
            ImportMapper importMapper = session?.getAttribute("importMapper")
            String applicationId = session?.getAttribute("applicationId")

            if (user) {
                Boolean sendMeDataAllowedForTarget = licenseService
                        .featuresAllowedByCurrentUserOrProject(null, [Feature.SEND_ME_DATA], user)
                        .get(Feature.SEND_ME_DATA)

                if (sendMeDataAllowedForTarget) {
                    if (importFileName && importFilePath && importMapper && applicationId && currentUser) {
                        TemporaryImportData postFile = temporaryImportDataService.getTemporaryImportDataById(importFilePath)

                        if (postFile) {
                            String actualLink

                            if (Environment.current == Environment.PRODUCTION) {
                                actualLink = "<a href=\"https://${request.serverName}/app/sec/importMapper?applicationId=${applicationId}&importMapperId=${importMapper.importMapperId}&importFilePath=${importFilePath}\">https://${request.serverName}/app/sec/importMapper?applicationId=${applicationId}&importMapperId=${importMapper.importMapperId}&importFilePath=${importFilePath}</a>."
                            } else {
                                actualLink = "<a href=\"https://${request.serverName}/app/sec/importMapper?applicationId=${applicationId}&importMapperId=${importMapper.importMapperId}&importFilePath=${importFilePath}\">https://${request.serverName}/app/sec/importMapper?applicationId=${applicationId}&importMapperId=${importMapper.importMapperId}&importFilePath=${importFilePath}</a>"
                            }
                            String htmlBody = "<p>User ${currentUser.name} (${currentUser.username}) has sent you a file for importing to One click LCA with the following note:<br/><br/>${note}</p><br/>Link to complete the import process: ${actualLink}"

                            if (postFile.importDate) {
                                htmlBody = "${htmlBody}<br/><br/>The link is valid for 30 days and will expire on ${new SimpleDateFormat("dd.MM.yyyy").format(DateUtils.addDays(postFile.importDate, 30))}"
                            }

                            if (log.isDebugEnabled()) {
                                log.debug("Sending mail link:")
                                log.debug(actualLink)
                            }
                            optimiMailService.sendMail({
                                to user.username
                                from "noreply@oneclicklca.com"
                                replyTo "noreply@oneclicklca.com"
                                subject "One Click LCA: You have been sent an import file: ${importFileName}."
                                html htmlBody
                            }, user.username)

                            postFile.tiedToUserId = user.id.toString()
                            postFile.merge(flush: true)
                            sendMeDataRequestService.createSendMeDataRequest(currentUser.id.toString(), user.id.toString(), "BOM")
                        } else {
                            error = "Unexpected error 1, contact support <a href=\"mailto:${emailConfiguration.supportEmail}\">${emailConfiguration.supportEmail}</a>"
                        }
                    } else {
                        log.error("IMPORTMAPPER: sendFileToAnotherUser error, missing session attributes? importFileName: ${importFileName}, importFilePath: ${importFilePath}, importMapper: ${importMapper?.importMapperId}, applicationId: ${applicationId}, currentUser: ${currentUser?.username}")
                        error = "Unexpected error 2, contact support <a href=\"mailto:${emailConfiguration.supportEmail}\">${emailConfiguration.supportEmail}</a>"
                    }
                } else {
                    error = "User ${user.username} doesn't have \"Send me data\" feature enabled (Expert level license)."
                }
            } else {
                error = "No user found with given email."
            }
        } else {
            error = "Enter user email"
        }
        render([error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getSendResourceToAnotherUserHTML() {
        String htmlPart1 = ""
        String htmlPart2 = ""
        String htmlPart3 = ""
        String title = ""
        String cancelText = ""
        String continueText = ""
        String missingEmailText = ""
        String sendDataText = ""
        String reviewDataText = ""
        String sendDataSuccessText = ""
        String sessionExpiredText = ""
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        Map questionAutocompleteChoices = [:]

        if (entityId && indicatorId) {
            Entity entity = entityService.readEntity(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Query privateResourceCreationQuery = queryService.getQueryByQueryId("privateResourceCreator", true)

            if (entity && indicator && privateResourceCreationQuery) {
                Entity parentEntity = entity.getParentById()
                List<Query> queries = indicator.getQueries(entity)
                List<Question> linkedQuestions = []
                queries?.each {
                    it.allQuestions?.each { Question q ->
                        if (q.mapToResourceParameter) {
                            linkedQuestions.add(q)
                        }
                    }
                }
                Query basicQuery = queryService.getBasicQuery()
                basicQuery.allQuestions?.each{Question q ->
                    if(q.mapToResourceParameter){
                        linkedQuestions.add(q)
                    }
                }
                int sectionNumber = 0

                htmlPart2 = "<form id=\"sendMeDataForm\"><input type=\"hidden\" name=\"entityId\" value=\"${entityId}\"/><input type=\"hidden\" name=\"indicatorId\" value=\"${indicatorId}\"/>"

                privateResourceCreationQuery?.sections?.each { QuerySection section ->
                    htmlPart2 = "${htmlPart2}${g.render(template: "/query/section", model: [entity: entity, parentEntity:parentEntity,query: privateResourceCreationQuery, section: section, display: true, isMain: true, sectionNumber: ++sectionNumber, modifiable: true, preventChanges: false, sendMeData: true, linkedQuestions: linkedQuestions, allowedSections: ["dataType","descriptiveData"], indicator: indicator, checkQuestionType: true]).toString()}"

                    section.questions?.each {
                        if (it.showTypeahead) {
                            def choices = questionService.getJsonAutocompleteChoices(it)
                            if (choices) {
                                questionAutocompleteChoices.put((it.questionId), choices)
                            }
                        }
                    }
                }
                htmlPart2 = "${htmlPart2}<input id='anotherUserEmail' name='anotherUserEmail' style='display:none' type='text' /></form>"
                htmlPart1 = "<div style=\"float:left;\"><p style=\"text-align: left;\">${message(code: 'sendMeData.info1')?.replaceAll("\"", "'")}</p><br />" +
                        "<ul><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet1')?.replaceAll("\"", "'")}</li><li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet2')?.replaceAll("\"", "'")}</li>" +
                        "<li class=\"importMapperNoLicenseList\">${message(code: 'sendMeData.bullet3')?.replaceAll("\"", "'")}</li></ul><br />" +
                        "<div id='sendToAnotherUserInput' style='margin-top: 5px;'><p style=\"text-align: left;\">${message(code: 'sendMeData.info2')?.replaceAll("\"", "'")}</p>"+
                        "<table style=\"width:100%; margin-top: 20px;\"><tr><td style=\"width: 150px;\"><label for=\"anotherUserEmail\"><strong>${message(code: 'sendMeData.emailHeading')?.replaceAll("\"", "'")}</strong></label></td>"+
                        "<td><input id='anotherUserEmail' class='redBorder input-xxlarge' onchange='removeBorder(this)' name='anotherUserEmail' type='text' /></td></tr></table></div></div>"
                title = message(code: 'sendMeData.heading')
                cancelText = message(code: 'cancel')
                continueText = message(code: 'continue')
                reviewDataText = message(code: 'review')
                missingEmailText = message(code: 'sendMeData.missingEmailError')
                sendDataText = message(code: 'sendMeData.button')
                sendDataSuccessText = message(code: 'sendMeData.success')
                sessionExpiredText = message(code: 'sessionExpired')
            }
        }
        render([htmlPart1       : htmlPart1, htmlPart2: htmlPart2, questionAutocompleteChoices: questionAutocompleteChoices, title: title, cancelText: cancelText, continueText: continueText,
                missingEmailText: missingEmailText, sendDataText: sendDataText, sendDataSuccessText: sendDataSuccessText, sessionExpiredText: sessionExpiredText , reviewDataText : reviewDataText, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    def renderQuestionsForSendMeData(){
        String dataTypeSelected = request.JSON.dataTypeSelected
        int sectionNumber = 0
        Query privateResourceCreationQuery = queryService.getQueryByQueryId("privateResourceCreator", true)
        String output = ""
        Map questionAutocompleteChoices = [:]
        Map outMap = [:]
        privateResourceCreationQuery?.sections?.each { QuerySection section ->
            boolean sectionAllowed = Boolean.TRUE
            if(section.compatiblePrivateDataTypes && !section.compatiblePrivateDataTypes?.contains(dataTypeSelected)){
                sectionAllowed = Boolean.FALSE
            }
            if(!section.sectionId.equalsIgnoreCase("privateDataType") && sectionAllowed) {
                section.questions?.each {
                    if (it.showTypeahead) {
                        def choices = questionService.getJsonAutocompleteChoices(it)
                        if (choices) {
                            questionAutocompleteChoices.put((it.questionId), choices)
                        }
                    }
                }
                output = "${output}${g.render(template: "/query/section", model: [query: privateResourceCreationQuery, section: section, display: true, isMain: true, sectionNumber: ++sectionNumber, modifiable: true, showMandatory : true, preventChanges: false, checkQuestionType: true, dataTypeSelected: dataTypeSelected]).toString()}"


            }
        }
        outMap.put("page",output)
        outMap.put("questionChoices",questionAutocompleteChoices)
        render([outMap: outMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    def verifySendMeDataAllowedForTarget() {
        String error = ""
        User currentUser = userService.getCurrentUser()
        String email = params.email
        if (!email) {
            error = message(code: 'sendMeData.missingEmailError')
        } else {
            User targetUser = userService.getUserByUsername(email)
            if (!targetUser) {
                error = message(code: 'sendMeData.userNotFound', args: [email])
            } else {
                Boolean sendMeDataAllowedForTarget = licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.SEND_PRIVATE_DATA], currentUser)?.get(Feature.SEND_PRIVATE_DATA)
                if (!sendMeDataAllowedForTarget) {
                    error = message(code: 'sendMeData.currentUserNotAllowed', args: [currentUser?.username])
                } else if (!userService.getAccount(targetUser)) {
                    error = message(code: 'sendMeData.targetError', args: [email])
                }
            }
        }
        render([error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }


    def reviewSendMeData() {
        Map<String, String[]> paramMap = request.getParameterMap()
        String email = paramMap?.get("anotherUserEmail")?.first()
        String entityId = paramMap?.get("entityId")?.first()
        String indicatorId = paramMap?.get("indicatorId")?.first()
        String error = ""
        User currentUser = userService.getCurrentUser()

        if (email) {
            User user = userService.getUserByUsername(email)

            if (user) {
                Boolean sendMeDataAllowedForTarget = licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.SEND_PRIVATE_DATA], currentUser)?.get(Feature.SEND_PRIVATE_DATA)

                if (sendMeDataAllowedForTarget) {
                    Entity entity = entityService.getEntityById(entityId, session)
                    Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

                    if (entity && indicator && currentUser) {
                        Account account = userService.getAccount(user)
                        String helpText
                        if(indicator?.sendPrivateDataType){
                            helpText = message(code: 'help.sendPrivateData'+"."+indicator?.sendPrivateDataType)
                        }
                        if (account) {

                            List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Double)
                            List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, List)
                            List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)
                            Resource resource = new Resource()
                            resource.resourceId = "privateDataset" + UUID.randomUUID().toString()
                            resource.profileId = "default"
                            resource.defaultProfile = true
                            resource.privateDataset = true
                            resource.privateDatasetAccountId = account.id.toString()
                            resource.privateDatasetAccountName = account.companyName
                            resource.environmentDataSourceType = "private"

                            paramMap.each { String paramName, String[] paramValue ->
                                if (paramName.contains(".") && paramValue) {
                                    String resourceAttribute = paramName.tokenize(".")[1]
                                    def resourceAttributeValue = paramValue.first()

                                    if (resourceAttributeValue) {
                                        try {
                                            if (persistingDoubleProperties.contains(resourceAttribute)) {
                                                resourceAttributeValue = resourceAttributeValue.replaceAll(",", ".")?.toDouble()
                                            } else if (persistingListProperties.contains(resourceAttribute)) {
                                                resourceAttributeValue = [resourceAttributeValue]
                                            } else if (booleanProperties.contains(resourceAttribute)) {
                                                resourceAttributeValue = resourceAttributeValue.toBoolean()
                                            }
                                            DomainObjectUtil.callSetterByAttributeName(resourceAttribute, resource, resourceAttributeValue)
                                        } catch (Exception e) {
                                            // handle unsettable params?
                                        }
                                    }
                                }
                            }
                            Entity parentEntity = entity.parentById
                            List<ResultCategory> mapToResourceCategories = indicator.getResolveResultCategories(parentEntity)?.findAll({ it.mapToStage })
                            List<CalculationRule> mapToResourceRules = indicator.getResolveCalculationRules(parentEntity)?.findAll({ it.mapToImpactCategory })
                            List<CalculationResult> calculationResults = entity.getCalculationResultObjects(indicator.indicatorId, null, null)

                            Map<String, Map<String, Object>> impacts = [:]
                            List<String> allowedMapToStageCategoriesWithoutResults = []

                            mapToResourceCategories?.each { ResultCategory resultCategory ->
                                if (Resource.allowedStages.contains(resultCategory.mapToStage)) {
                                    boolean hasResults = false
                                    mapToResourceRules?.each { CalculationRule calculationRule ->
                                        if (Resource.allowedImpactCategories.contains(calculationRule.mapToImpactCategory)) {
                                            Double result = calculationResults?.find({ resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRule.calculationRuleId.equals(it.calculationRuleId) })?.result

                                            if (result != null) {
                                                hasResults = true
                                                Map<String, Object> existing = impacts.get(resultCategory.mapToStage)

                                                if (existing) {
                                                    existing.put((calculationRule.mapToImpactCategory), result)
                                                } else {
                                                    existing = [(calculationRule.mapToImpactCategory): result]
                                                }
                                                impacts.put((resultCategory.mapToStage), existing)

                                                if ("A1-A3".equals(resultCategory.mapToStage)) {
                                                    try {
                                                        DomainObjectUtil.callSetterByAttributeName(calculationRule.mapToImpactCategory, resource, result)
                                                    } catch (e) {
                                                        log.error("Send resource to another user error: ${e}")

                                                    }
                                                }
                                            }
                                        } else {
                                            log.error("Send resource to another user error: Indicator ${indicator.indicatorId} contains calculationRule ${calculationRule.calculationRuleId} with invalid mapToImpactCategory: ${calculationRule.mapToImpactCategory}")
                                        }
                                    }

                                    if (!hasResults) {
                                        allowedMapToStageCategoriesWithoutResults.add(resultCategory.resultCategoryId)
                                    }
                                } else {
                                    log.error("Send resource to another user error: Indicator ${indicator.indicatorId} contains resultCategory ${resultCategory.resultCategoryId} with invalid mapToStage stage: ${resultCategory.mapToStage}")
                                }
                            }

                            if (allowedMapToStageCategoriesWithoutResults) {
                                Entity tempEntity = new Entity()
                                tempEntity.defaults = entity.defaults
                                tempEntity.datasets = entity.datasets
                                tempEntity = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parentEntity, tempEntity, Boolean.TRUE, mapToResourceRules?.collect({ it.calculationRuleId }),Boolean.TRUE)
                                List<CalculationResult> tempCalculationResults = tempEntity.tempCalculationResults

                                if (tempCalculationResults) {
                                    mapToResourceCategories.findAll({ allowedMapToStageCategoriesWithoutResults.contains(it.resultCategoryId) })?.each { ResultCategory resultCategory ->
                                        if (Resource.allowedStages.contains(resultCategory.mapToStage)) {
                                            mapToResourceRules?.each { CalculationRule calculationRule ->
                                                if (Resource.allowedImpactCategories.contains(calculationRule.mapToImpactCategory)) {
                                                    Double result = tempCalculationResults?.find({ resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRule.calculationRuleId.equals(it.calculationRuleId) })?.result

                                                    if (result != null) {
                                                        Map<String, Object> existing = impacts.get(resultCategory.mapToStage)

                                                        if (existing) {
                                                            existing.put((calculationRule.mapToImpactCategory), result)
                                                        } else {
                                                            existing = [(calculationRule.mapToImpactCategory): result]
                                                        }
                                                        impacts.put((resultCategory.mapToStage), existing)

                                                        if ("A1-A3".equals(resultCategory.mapToStage)) {
                                                            try {
                                                                DomainObjectUtil.callSetterByAttributeName(calculationRule.mapToImpactCategory, resource, result)
                                                            } catch (e) {
                                                                log.error("Send resource to another user error: ${e}")
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            resource.impacts = impacts
                            resource.active = false
                            resource.isNewPrivateDataset = true

                            session?.setAttribute("resource" , resource)
                            session?.setAttribute("user" , user)
                            session?.setAttribute("currentUser" , currentUser)

                            String output = ""
                            Map outMap = [:]
                            Map properties
                            Set<String> impactsWithData = []
                            Map displayableProperties = [:]
                            //copy
                            if (resource) {
                                List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)

                                if (domainClassProperties) {
                                    properties = resource.properties.findAll({
                                        domainClassProperties.contains(it.key)
                                    })

                                    if (properties) {
                                        properties.put("allowVariableThickness (dynamic)", resource.allowVariableThickness)
                                        resource.impacts?.each { stage, impactsData ->
                                            impactsWithData += impactsData.keySet()
                                        }
                                        impactsWithData.sort()
                                        Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)
                                        properties?.each {privateResourceCreationQuery?.allQuestions?.each{Question q->
                                            if(it.key.equals(q.questionId)){
                                             displayableProperties.put(q.localizedQuestion,it.value)}
                                            }
                                        }
                                        displayableProperties.put("impacts",impacts)
                                        log.info("displayableProperties : ${displayableProperties}")
                                    }
                                }
                            }
                            String mandatoryDataMissingError
                            Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)
                            privateResourceCreationQuery?.sections?.each { QuerySection section ->
                                if(!section?.sectionId.equals("privateDataType")){
                                    section?.questions?.findAll({ !it.optional })?.each { Question question ->
                                        def value = DomainObjectUtil.callGetterByAttributeName(question.questionId, resource)
                                        if (!value) {
                                            if (mandatoryDataMissingError) {
                                                mandatoryDataMissingError = "${mandatoryDataMissingError}, ${question.localizedQuestion}"
                                            } else {
                                                mandatoryDataMissingError = g.message(code: "privateData.missing_mandatory_data", args: [question.localizedQuestion])
                                            }
                                        }
                                    }
                                }
                            }
                            if (mandatoryDataMissingError) {
                                error = mandatoryDataMissingError
                            }

                            Question privateDataTypeQuestion = questionService.getQuestion(privateResourceCreationQuery, "privateDataType")
                            List<QuestionAnswerChoice> privateDataTypeAnswerChoices = privateDataTypeQuestion?.choices
                            Map <String, String> resourceParameters = [:]
                            resourceParameters = privateDataTypeAnswerChoices?.find({
                                indicator.sendPrivateDataType.equals(it.answerId)
                            })?.resourceParameters

                            if (resourceParameters) {
                                resourceParameters?.each { String key, String mapValue ->
                                    if (persistingListProperties?.contains(key) && mapValue !=null) {
                                        DomainObjectUtil.callSetterByAttributeName(key, resource, mapValue?.tokenize(",")?.toList())
                                    }
                                }
                            }

                            if(!error) {
                                output = "${output}${g.render(template: "/account/reviewPrivateDatasets", model: [helpText:helpText ,properties : displayableProperties?.sort({ it.key.toString() }), hideNavi: true, resource: resource, impactsWithData: impactsWithData,questionService : questionService, showDataReferences: applicationService.getApplicationByApplicationId("LCA")?.showDataReferences?.find({ "resource".equals(it.page) })]).toString()}"
                                outMap.put("page",output)
                                render([outMap: outMap , htmlPart3: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                            }
                        } else {
                            error = message(code: 'sendMeData.targetError', args: [email])
                        }
                    } else {
                        log.error("SEND ME DATA: sending resource error, missing attributes? entity: ${entity?.id?.toString()}, indicator: ${indicator?.indicatorId}, currentUser: ${currentUser?.username}")
                        error = message(code: 'sendMeData.error.contactSupport', args: ['2'])
                    }
                } else {
                    error = message(code: 'sendMeData.currentUserNotAllowed', args: [currentUser?.username])
                }
            } else {
                error = message(code: 'sendMeData.userNotFound', args: [email])
            }
        } else {
            error = message(code: 'sendMeData.missingEmailError')
        }
        render([error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def sendDataToAnotherUser(){
        String error = ""
        Resource resource = (Resource)session?.getAttribute("resource")
        if (resource.validate()) {
            resource = optimiResourceService.saveResource(resource)

            User currentUser = (User)session?.getAttribute("currentUser")
            User user = (User)session?.getAttribute("user")
            Account account = userService.getAccount(user)
            if (resource && currentUser && account && user) {
                String link = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                        controller: "account", action: "form",
                        params: [id: account?.id])
                // using multiple args in one message did not work for some reasons, hence need to split
                String htmlBody = message(code: 'sendMeData.email.body1', args: [currentUser.username]) + message(code: 'sendMeData.email.body2', args: [resource.nameEN]) + message(code: 'sendMeData.email.body4') + message(code: 'sendMeData.email.body3', args: [link])

                optimiMailService.sendMail({
                    to user.username
                    from emailConfiguration.contactEmail
                    subject(message(code: 'sendMeData.email.subject'))
                    html htmlBody
                }, "${user.username}")

                sendMeDataRequestService.createSendMeDataRequest(currentUser.id.toString(), user.id.toString(), "EPD")
            } else {
                log.error("SEND ME DATA: sending resource error, resource did not save? ${resource?.errors?.getAllErrors()}")
                error = message(code: 'sendMeData.error.contactSupport', args: ['3'])
            }
        } else {
            def errorsList = resource.errors.getAllErrors()
            error = message(code: 'sendMeData.unableToCreateDataset')
            errorsList?.each {
                error = "${error}${message(error: it)}<br/>"
            }
        }
        render([error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

    }
}
