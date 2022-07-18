package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AccountCatalogue
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DScenario
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.OptimiStringUtils
import com.mongodb.BasicDBObject
import grails.converters.JSON
import grails.util.Environment
import grails.web.servlet.mvc.GrailsParameterMap
import org.bson.Document
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpSession

class AccountController {

    def accountService
    def licenseService
    def userService
    def queryService
    def optimiResourceService
    def optimiMailService
    def channelFeatureService
    def configurationService
    def constructionService
    def customSoapService
    def domainClassService
    def flashService
    def maskingService
    def fileUtil
    def accountImagesService
    def activeCampaignService
    def questionService
    def projectTemplateService
    OptimiStringUtils optimiStringUtils

    @Autowired
    EmailConfiguration emailConfiguration

    public static final String privateResourceCreationQueryId = "privateResourceCreator"

    def index() {
        User currentUser = userService.getCurrentUser(false)
        if (userService.getSalesView(currentUser) || userService.getConsultant(currentUser)) {
            def result = accountService.getAccountsForRender()
            [accounts: result.accountsForRender, privateDatasetColumnSortable: result.privateDatasetColumnSortable]
        } else {
            redirect controller: "main", action: "list"
        }
    }

    def form() {
        long now = System.currentTimeMillis()
        log.info("Account: Starting to load account")
        User user = userService.getCurrentUser()
        HttpSession session = WebUtils.retrieveGrailsWebRequest()?.currentRequest?.getSession()
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
        Boolean hideEcommerce = channelFeatureService.getHideEcommerceStatus(channelFeature)
        String userEmailForm = params.emailForm ? params.emailForm : "@" + user.username.tokenize('@')?.get(1)
        Account account
        Account existingAccount
        List<License> accountLicenses
        boolean formDisabled = !userService.getSuperUser(user)
        boolean editable = false

        if (!params.id && params.join && userEmailForm) {
            Document accountResult = Account.collection.findOne([emailForm: userEmailForm])

            if (accountResult) {
                existingAccount = accountResult as Account
                flash.successAlert = message(code: "account.request_to_join", args: [existingAccount.companyName])
            }
        }
        List<License> licenses = licenseService.getLicensesForForm()?.sort({ it.name.toLowerCase() })
        List<AccountCatalogue> catalogues = AccountCatalogue.list()?.sort({ it.slug })
        List<User> users
        boolean vatEditable = false
        List<Resource> countries = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)
        List<Resource> states = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["US-states", "CA-provinces"], null, null, null, null, null)
        List<String> currencySymList = countries?.collect({it.countryCurrencySymbol})?.findAll({it && it != null})?.unique()
        String companyName

        if (params.id) {
            account = accountService.getAccount(params.id)
        } else {
            account = chainModel?.get("account")
        }
        String emailForm

        if (account) {
            if (user && (user.internalUseRoles || account.mainUserIds?.contains(user.id.toString()))) {
                editable = true
            }

            emailForm = account.emailForm
            users = userService.getUsersForAccount(emailForm)?.sort({ it.name.toLowerCase() })
            if (users) {
                if (account.userIds) {
                    users.removeAll({ account.userIds.contains(it.id.toString()) })
                }
                if (account.mainUserIds) {
                    users.removeAll({ account.mainUserIds.contains(it.id.toString()) })
                }
            } else {
                users = null
            }

            companyName = account.companyName

            if (account.licenseIds && licenses) {
                accountLicenses = licenses.findAll({ account.licenseIds.contains(it.id.toString()) })
                licenses.removeAll({ account.licenseIds.contains(it.id.toString()) })
            }
            if (userService.getSalesView(user) || account.mainUserIds?.contains(user?.id.toString())) {
                vatEditable = true
                formDisabled = false
            }

            if (catalogues && account.catalogueSlugs) {
                catalogues.removeAll({ account.catalogueSlugs.contains(it.slug) })
            }
        } else {
            vatEditable = true
            formDisabled = false
            companyName = userService.getCurrentUser().organizationName
            String invalidEmailsAsString = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                    com.bionova.optimi.construction.Constants.ConfigName.UNALLOWED_ACCOUNT_EMAILS.toString())?.value

            if (invalidEmailsAsString) {
                List<String> invalidEmails = invalidEmailsAsString.tokenize(',')

                if (!invalidEmails.find({ userEmailForm.contains(it.trim().toLowerCase()) })) {
                    emailForm = userEmailForm
                }
            }

        }

        Boolean privateDatasets = Boolean.FALSE
        Boolean privateLibraries = Boolean.FALSE
        Boolean ecoiventUncapped = Boolean.FALSE
        Boolean privateClassifications = Boolean.FALSE
        Boolean privateEpdcs = Boolean.FALSE
        Boolean createConstructions = Boolean.FALSE
        Boolean isQuickStartTemplateLicensed = Boolean.FALSE
        Boolean hasCarbonDesigner3DScenariosFeature = Boolean.FALSE
        List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)
        licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.ACCOUNT_PRIVATE_DATASETS, Feature.CREATE_CONSTRUCTIONS, Feature.ACCOUNT_PRIVATE_EPDCS, Feature.MANAGE_PRIVATE_DATA, Feature.QUICK_START_TEMPLATE])?.each { String featureId, Boolean allowed ->
            if (allowed && !licensedFeatureIds?.contains(featureId)) {
                licensedFeatureIds.add(featureId)
            }
        }

        licensedFeatureIds.each { String featureId ->
            if (Feature.ECOINVENT_WITH_CAP.contains(featureId)) {
                if (licensedFeatureIds?.find({Feature.EDIT_ORGANISATION_DATA_LISTS.equalsIgnoreCase(it)})) {
                    privateLibraries = Boolean.TRUE
                }
            } else if (featureId.equalsIgnoreCase(Feature.ECOINVENT_UNCAPPED)) {
                ecoiventUncapped = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase(Feature.ACCOUNT_PRIVATE_DATASETS) || featureId.equalsIgnoreCase(Feature.MANAGE_PRIVATE_DATA)) {
                privateDatasets = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase(Feature.ACCOUNT_PRIVATE_CLASSIFICATIONS)) {
                privateClassifications = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase(Feature.CREATE_CONSTRUCTIONS)) {
                createConstructions = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase(Feature.ACCOUNT_PRIVATE_EPDCS)) {
                privateEpdcs = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase(Feature.QUICK_START_TEMPLATE)) {
                isQuickStartTemplateLicensed = Boolean.TRUE
            } else if (featureId.equalsIgnoreCase((Feature.CARBONDESIGNER3DSCENARIOS))) {
                hasCarbonDesigner3DScenariosFeature = Boolean.TRUE
            }
        }

        Question classificationParameterQuestionInies
        List <QuestionAnswerChoice> classificationParamsInies

        if (privateEpdcs) {
            Query iniesQuery = queryService.getQueryByQueryId("iniesXmlEPDParameters", true)
            if (iniesQuery) {
                classificationParameterQuestionInies = (Question) iniesQuery?.getAllQuestions()?.find({"xmlClassificationParameters".equals(it.questionId)})
                classificationParamsInies = classificationParameterQuestionInies?.choices
            }

        }

        Integer privateDatasetsCount = 0
        Integer newPrivateDatasetsCount = 0
        List<Document> accountPrivateDatasets = Resource.collection.find([privateDatasetAccountId: account?.id?.toString()], [isNewPrivateDataset: 1])?.toList()
        accountPrivateDatasets?.each { dataset ->
            if (dataset.isNewPrivateDataset) {
                newPrivateDatasetsCount++
            } else {
                privateDatasetsCount++
            }
        }

        def accountPrivateDatasetsAmount = newPrivateDatasetsCount > 0 ? "${privateDatasetsCount} + " + '<span class="custom-badge-green">' + newPrivateDatasetsCount + '</span>' : privateDatasetsCount
        Integer accountPrivateConstructionsAmount = constructionService.getConstructionsByAccountId(account?.id?.toString())?.size()

        List<ChannelFeature> channelFeatures = channelFeatureService?.getAllChannelFeatures()
        boolean favouriteMaterials = account?.favoriteMaterialIdAndUserId?.size() > 0

        List<AccountImages> productImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id as String, Constants.AccountImageType.PRODUCTIMAGE.toString())
        List<AccountImages> manufacturingDiagrams = accountImagesService.getAccountImagesByAccountIdAndType(account?.id as String, Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString())
        List<AccountImages> brandingImages = accountImagesService.getAccountImagesByAccountIdAndType(account?.id as String, Constants.AccountImageType.BRANDINGIMAGE.toString())

        List<ProjectTemplate> accountProjectTemplates = isQuickStartTemplateLicensed ? accountService.getProjectTemplates(account?.projectTemplateIds) : []

        //CarbonDesigner3D logic below here
        Integer approvedScenarios = CarbonDesigner3DScenario.findAllByAccountIdAndPublished(account?.id?.toString(), Boolean.TRUE).size()
        Integer unapprovedScenarios = CarbonDesigner3DScenario.findAllByAccountIdAndPublished(account?.id?.toString(), Boolean.FALSE).size()
        Boolean hasOrgScenarios = (approvedScenarios > 0 || unapprovedScenarios > 0) ? Boolean.TRUE : Boolean.FALSE
        log.info("Account: Time it took to load entire page: ${System.currentTimeMillis() - now} ms")

        Map model = [account      : account, licenses: licenses, users: users, countries: countries, states: states, editable: editable, accountLicenses: accountLicenses,
         companyName  : companyName, user: user, vatEditable: vatEditable, catalogues: catalogues, productImages: productImages, manufacturingDiagrams: manufacturingDiagrams, isQuickStartTemplateLicensed: isQuickStartTemplateLicensed,
         formDisabled : formDisabled, showInfoMessage: params.newAccount, channelFeatures: channelFeatures, brandingImages: brandingImages, accountProjectTemplates: accountProjectTemplates,
         emailForm    : emailForm, privateDatasets: privateDatasets, privateClassifications: privateClassifications, privateLibraries: privateLibraries, accountPrivateDatasetsAmount: accountPrivateDatasetsAmount, hasFavoriteResource: favouriteMaterials,
         hideEcommerce: hideEcommerce, accountPrivateConstructionsAmount: accountPrivateConstructionsAmount, createConstructions: createConstructions, ecoiventUncapped: ecoiventUncapped,
         privateEpdcs : privateEpdcs, classificationParameterQuestionInies:classificationParameterQuestionInies, classificationParamsInies:classificationParamsInies, currencySymList: currencySymList,
                     hasOrgScenarios: hasOrgScenarios, unapprovedScenarios: unapprovedScenarios, approvedScenarios: approvedScenarios, hasScenariosFeature: hasCarbonDesigner3DScenariosFeature]

        if (isQuickStartTemplateLicensed) {
            projectTemplateService.populateModelForTemplateModal(model, session, accountLicenses)
        }
        model
    }

    def privateDatasets() {
        User user = userService.getCurrentUser()
        Account account1 = userService.getAccount(user)
        //0014931
        boolean createPrivateDatasetsAllowed = Boolean.TRUE
        List<String> licensedFeatureIds = []
        account1?.licenses?.each { License license ->
            license?.licensedFeatures?.each {
                licensedFeatureIds.add(it.featureId)
            }
        }

        licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.ACCOUNT_PRIVATE_DATASETS , Feature.MANAGE_PRIVATE_DATA])?.each { String featureId, Boolean allowed ->
            if (allowed && !licensedFeatureIds?.contains(featureId)) {
                licensedFeatureIds.add(featureId)
            }
        }
        if(!licensedFeatureIds?.contains(Feature.ACCOUNT_PRIVATE_DATASETS) && licensedFeatureIds?.contains(Feature.MANAGE_PRIVATE_DATA)){
            createPrivateDatasetsAllowed = Boolean.FALSE
        }

        Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)
        if (privateResourceCreationQuery && user) {
            String accountId = params.accountId
            Account account
            List<Resource> resources = []
            if (accountId) {
                account = accountService.getAccount(accountId)
                resources = optimiResourceService.getResourcesByPrivateDatasetAccountId(accountId)
            } else {
                account = chainModel?.get("account")
                resources = optimiResourceService.getResourcesByPrivateDatasetAccountId(account?.id?.toString())
            }
            Boolean accessible
            Boolean editable = Boolean.FALSE

            if (user?.internalUseRoles||(account && account.mainUserIds?.contains(user.id.toString()))) {
                editable = Boolean.TRUE
                accessible = Boolean.TRUE
            } else if (account && account.userIds?.contains(user.id.toString())) {
                accessible = Boolean.TRUE
            }

            if (accessible) {
                List<String> privateDataSelfDeclarationAllowedDataProperties = privateResourceCreationQuery?.privateDataSelfDeclarationAllowedDataProperties
                List<String> dataProperties = []

                if (privateDataSelfDeclarationAllowedDataProperties) {
                    BasicDBObject filter = new BasicDBObject("active", true)
                    dataProperties = Resource.collection.distinct("dataProperties", filter, String.class)?.toList()?.findAll({privateDataSelfDeclarationAllowedDataProperties.contains(it)})?.sort({it.toLowerCase()})
                }
                [privateResourceCreationQuery: privateResourceCreationQuery, account: account, resources: resources,
                 editable: editable, dataProperties: dataProperties, accountId: accountId, showMandatory: !privateResourceCreationQuery.disableHighlight , createPrivateDatasetsAllowed: createPrivateDatasetsAllowed]
            } else {
                flash.fadeErrorAlert = "No rights to access organization ${account?.companyName} private data."
                redirect(controller: "main", action: "list")
            }
        } else {
            flash.fadeErrorAlert = "No query found with queryId ${privateResourceCreationQueryId}"
            redirect(controller: "main", action: "list")
        }
    }

    def uploadEPDC() {
        User user = userService.getCurrentUser()
        String accountId = params.accountId
        String classificationParameterId = params.classificationParameterId
        String classificationQuestionId = params.classificationQuestionId
        Account account
        List<Resource> resources = []
        if (accountId) {
            account = accountService.getAccount(accountId)
            resources = optimiResourceService.getResourcesByPrivateDatasetAccountId(accountId)?.findAll({it.resourceId.contains("iniesResource")})
        } else {
            account = chainModel?.get("account")
            resources = optimiResourceService.getResourcesByPrivateDatasetAccountId(account?.id?.toString())?.findAll({it.resourceId.contains("iniesResource")})

        }
        Boolean editable = Boolean.FALSE

        if (user && (user.internalUseRoles||(account && account.mainUserIds?.contains(user.id.toString())))) {
            editable = Boolean.TRUE
        }
        List<String> privateDataSelfDeclarationAllowedDataProperties = queryService.getQueryByQueryId(privateResourceCreationQueryId)?.privateDataSelfDeclarationAllowedDataProperties
        List<String> dataProperties

        if (privateDataSelfDeclarationAllowedDataProperties) {
            BasicDBObject filter = new BasicDBObject("active", true)
            dataProperties = Resource.collection.distinct("dataProperties", filter, String.class)?.toList()?.findAll({privateDataSelfDeclarationAllowedDataProperties.contains(it)})?.sort({it.toLowerCase()})
        }
        [account: account, resources: resources,classificationParameterId:classificationParameterId, classificationQuestionId:classificationQuestionId, dataProperties: dataProperties,
         accountId: accountId, editable: editable]
    }

    def changeResourceDataProperties() {
        String accountId = params.accountId
        String classificationParameterId = params.classificationParameterId
        String classificationQuestionId = params.classificationQuestionId
        String dataProperty = params.dataProperty
        Boolean remove = params.boolean("remove")
        String resourceId = params.resourceId
        Boolean redirectToPrivateDatasets = params.boolean("privateDatasets")
        Boolean ok = Boolean.FALSE
        String resourceName = ""

        if (dataProperty && resourceId) {
            Resource resource = Resource.findByResourceId(resourceId)

            if (resource) {
                if (remove) {
                    if (resource.dataProperties) {
                        resource.dataProperties.remove(dataProperty)
                        ok = Boolean.TRUE
                        resourceName = resource.staticFullName
                    }

                    if (resource.manuallyEnabledPurposes) {
                        resource.manuallyEnabledPurposes.removeIf({it.equalsIgnoreCase(dataProperty)})
                    }

                    if (resource.enabledPurposes) {
                        resource.enabledPurposes.removeIf({it.equalsIgnoreCase(dataProperty)})
                    }
                } else {
                    if (resource.dataProperties) {
                        resource.dataProperties.add(dataProperty)
                        ok = Boolean.TRUE
                        resourceName = resource.staticFullName
                    } else {
                        resource.dataProperties = [dataProperty]
                        ok = Boolean.TRUE
                        resourceName = resource.staticFullName
                    }

                    if (resource.manuallyEnabledPurposes) {
                        if (!resource.manuallyEnabledPurposes.contains(dataProperty)) {
                            resource.manuallyEnabledPurposes.add(dataProperty)
                        }
                    } else {
                        resource.manuallyEnabledPurposes = [dataProperty]
                    }

                    if (resource.enabledPurposes) {
                        if (!resource.enabledPurposes.contains(dataProperty)) {
                            resource.enabledPurposes.add(dataProperty)
                        }
                    } else {
                        resource.enabledPurposes = [dataProperty]
                    }
                }
                resource.merge(flush: true)
            }
        }

        if (ok) {
            if (remove) {
                flash.fadeSuccessAlert = "DataProperty ${dataProperty} removed from resource: ${resourceName}"
            } else {
                flash.fadeSuccessAlert = "DataProperty ${dataProperty} added to resource: ${resourceName}"
            }
        } else {
            flash.errorAlert = "Something went wrong :("
        }
        if (redirectToPrivateDatasets) {
            redirect(action: "privateDatasets", params: [accountId: accountId])
        } else {
            redirect(action: "uploadEPDC", params: [accountId: accountId, classificationParameterId: classificationParameterId, classificationQuestionId: classificationQuestionId])
        }
    }

    def listPrivateDatasets() {
        Map<String, List<Resource>> accountAndResource = [:]
        List<Account> accounts = accountService.getAccounts()
        Integer datasetAmount = 0
        accounts?.each { Account account ->
            List<Resource> accountResources = optimiResourceService.getResourcesByPrivateDatasetAccountId(account.id?.toString())
            if (accountResources) {
                accountAndResource.put(account.companyName, accountResources)
                datasetAmount = datasetAmount + accountResources.size()
            }

        }
        [accountAndResource: accountAndResource, datasetAmount: datasetAmount]
    }
    def updatePrivateData() {
        String accountId = params.accountId
        String resourceId = params.resourceId
        Account account = accountService.getAccount(accountId)
        Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)

        if (account && resourceId && privateResourceCreationQuery){
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, null, true)
            GrailsParameterMap parameterMap = params
            def paramNames = parameterMap?.collect({ it.key.toString() })

            List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)
            List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(Resource.class)
            List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Double)
            List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, List)
            List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)

            Map<String, Double> A1A3Impacts = [:]
            List<String> dataProperties = []
            paramNames?.each { String paramName ->
                // Check that paramName is valid to give, paramName.tokenize(".")[1] === the resource attribute
                if (paramName && paramName.contains(".") && paramName.tokenize(".").size() == 2) {
                    String attr = paramName.tokenize(".")[1]
                    String value = request.getParameter(paramName)

                    if (DomainObjectUtil.isNumericValue(value)) {
                        if (persistingDoubleProperties.contains(attr)) {
                            value = DomainObjectUtil.convertStringToDouble(value)
                        } else {
                            value = DomainObjectUtil.convertStringToDouble(value).intValue()
                        }
                    }

                    if (persistingListProperties.contains(attr) && value != null) {
                        List<String> values = []
                        value.toString().tokenize(",")?.each {
                            values.add(it.toString().trim())
                        }
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, values)
                    } else if (booleanProperties?.contains(attr)) {
                        Boolean boolValue = value?.toString()?.toBoolean()

                        if (boolValue != null) {
                            DomainObjectUtil.callSetterByAttributeName(attr, resource, boolValue)
                        }
                    } else if (mandatoryProperties.contains(attr)) {
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, value, Boolean.FALSE)
                    } else if (persistingProperties.contains(attr)) {
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, value, Boolean.TRUE)
                    }

                    if (Resource.allowedImpactCategories.contains(attr) && value && value instanceof Double) {
                        A1A3Impacts.put((attr), value)
                        if (attr.startsWith("impact")) {
                            if (!dataProperties.contains("CML")) {
                                dataProperties.add("CML")
                            }
                        } else if (attr.startsWith("traci")) {
                            if (!dataProperties.contains("TRACI")) {
                                dataProperties.add("TRACI")
                            }
                        }
                    }
                }
            }
            if (resource.impacts) {
                resource.impacts.put("A1-A3", A1A3Impacts)
            } else {
                resource.impacts = ["A1-A3": A1A3Impacts]
            }
            resource.dataProperties = dataProperties

            optimiResourceService.generateDynamicValues(resource)
            optimiResourceService.setMagicButtonValues(resource)

            if (resource.validate()) {
                String mandatoryDataMissingError
                privateResourceCreationQuery.sections?.each { QuerySection section ->
                    section?.questions?.findAll({!it.optional})?.each { Question question ->
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

                if (!mandatoryDataMissingError) {
                    flash.successAlert = "${g.message(code: "privateData.create_success", args: [resource.nameEN])}"
                    resource.merge(flush: true)
                } else {
                    flash.errorAlert = "${mandatoryDataMissingError}"
                }
            } else {
                flash.errorAlert = "${g.message(code: "privateData.create_input_error", args: [resource.errors.getAllErrors()])}"
            }


        }else {
            flash.errorAlert = "${g.message(code: "privateData.unable_to_update")}"
        }
       redirect action: "privateDatasets", params: [accountId: accountId]
    }

    def createPrivateData() {
        Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)
        String accountId = params.accountId
        Account account = accountService.getAccount(accountId)

        if (account && privateResourceCreationQuery) {
            Resource resource = new Resource()
            resource.resourceId = "privateDataset" + UUID.randomUUID().toString()
            resource.profileId = "default"
            resource.defaultProfile = true

            GrailsParameterMap parameterMap = params
            def paramNames = parameterMap?.collect({ it.key.toString() })

            List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)
            List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(Resource.class)
            List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Double)
            List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, List)
            List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)

            Map<String, Double> A1A3Impacts = [:]
            List<String> dataProperties = []

            Question privateDataTypeQuestion = questionService.getQuestion(privateResourceCreationQuery, "privateDataType")
            List<QuestionAnswerChoice> privateDataTypeAnswerChoices = privateDataTypeQuestion?.choices
            Map <String, String> resourceParameters = [:]
            List <String> stagesToCopy = []
            paramNames?.each { String paramName ->
                // Check that paramName is valid to give, paramName.tokenize(".")[1] === the resource attribute
                if (paramName && paramName.contains(".") && paramName.tokenize(".").size() == 2) {
                    String attr = paramName.tokenize(".")[1]
                    Object value = request.getParameter(paramName)

                   //sw-2244
                    //skip numeric conversion for verificationBetie verification Number(text field).
                    Boolean skipNumericConverion = false;
                    if(attr in Constants.PRIVATE_DATA_NUMERIC_CONVERSION_SKIP_LIST) {
                        skipNumericConverion=true;
                    }

                        if(!skipNumericConverion) {
                            if (DomainObjectUtil.isNumericValue(value)) {
                                if (persistingDoubleProperties.contains(attr)) {
                                    value = DomainObjectUtil.convertStringToDouble(value)
                                } else {
                                    value = DomainObjectUtil.convertStringToDouble(value).intValue()
                                }
                            }
                        }


                    if (privateDataTypeAnswerChoices?.find({value.equals(it.answerId)})) {
                        resourceParameters = privateDataTypeAnswerChoices?.find({
                            value.equals(it.answerId)
                        })?.resourceParameters
                    }


                    if (persistingListProperties.contains(attr) && value != null) {
                        List<String> values = []
                        value.toString().tokenize(",")?.each {
                            values.add(it.toString().trim())
                        }
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, values)
                    } else if (booleanProperties?.contains(attr)) {
                        Boolean boolValue = value?.toString()?.toBoolean()

                        if (boolValue != null) {
                            DomainObjectUtil.callSetterByAttributeName(attr, resource, boolValue)
                        }
                    } else if (mandatoryProperties.contains(attr)) {
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, value, Boolean.FALSE)
                    } else if (persistingProperties.contains(attr)) {
                        DomainObjectUtil.callSetterByAttributeName(attr, resource, value, Boolean.TRUE)
                    }

                    if (Resource.allowedImpactCategories.contains(attr) && value && value instanceof Double) {
                        A1A3Impacts.put((attr), value)
                        //	0015779: PATCH: Remove automatic mapping to dataproperties in private data creation
                       /* if (attr.startsWith("impact")) {
                            if (!dataProperties.contains("CML") ) {
                                dataProperties.add("CML")
                            }
                        } else if (attr.startsWith("traci")) {
                            if (!dataProperties.contains("TRACI")) {
                                dataProperties.add("TRACI")
                            }
                        }*/
                    }
                }
            }

            if (resourceParameters) {
                resourceParameters?.each { String key, String mapValue ->
                    if (persistingProperties?.contains(key) && mapValue != null) {
                        DomainObjectUtil.callSetterByAttributeName(key, resource, mapValue)
                    }
                    if (persistingListProperties?.contains(key) && mapValue !=null) {
                        DomainObjectUtil.callSetterByAttributeName(key, resource, mapValue?.tokenize(",")?.toList())
                    }
                }
            }

            resource.impacts = ["A1-A3": A1A3Impacts]
            if (resource.dataProperties) {
                resource.dataProperties?.addAll(dataProperties)
                resource.dataProperties = resource.dataProperties.unique()
            } else {
                resource.dataProperties = dataProperties
            }
            //13986
            privateDataTypeAnswerChoices?.each{
                if(it?.copyImpactsToStages && (request.getParameter("privateDataType.privateDataType").equals(it.answerId))) {
                    stagesToCopy.addAll(it.copyImpactsToStages)
                }
            }
            if(A1A3Impacts && stagesToCopy){
                stagesToCopy.each{
                    resource.impacts.put(it , A1A3Impacts)
                }
            }

            resource.privateDatasetAccountId = account.id.toString()
            resource.privateDataset = Boolean.TRUE
            resource.environmentDataSourceType = Resource.CONSTANTS.PRIVATE.value()
            resource.privateDatasetAccountName = account.companyName
            resource.isNewPrivateDataset = false // only true if the dataset is not active when it's created (the dataset is sent from another user)
            resource.active = true
            resource.importFile = "Manually added"
            optimiResourceService.generateDynamicValues(resource)
            optimiResourceService.setMagicButtonValues(resource)

            if (resource.validate()) {
                String mandatoryDataMissingError
                privateResourceCreationQuery.sections?.each { QuerySection section ->
                    section?.questions?.findAll({!it.optional})?.each { Question question ->
                        //sw-2244
                        //skipValidation flag is added to skip unnecessary mandatory input field validations.
                        boolean skipValidation = false
                        if (!paramNames.contains(section.sectionId + "." + question.questionId)) {
                            skipValidation = true
                        }
                        // BETie verification - skip validation for verification number if selected verification proof is not iniesIdentifier
                        else if (question.questionId == Constants.PRIVATE_DATA_VERIFICATION_NUMBER && !resource.verificationNumber && resource.verificationProof != Constants.VerificationProof.INES_IDENTIFIER.getName()) {
                            skipValidation = true
                        }
                        if (!skipValidation) {
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

                if (!mandatoryDataMissingError) {
                    flash.successAlert = "${g.message(code: "privateData.create_success", args: [resource.nameEN])}"
                    resource.save(flush: true)
                } else {
                    flash.errorAlert = "${mandatoryDataMissingError}"
                }
            } else {
                flash.errorAlert = "${g.message(code: "privateData.create_input_error", args: [resource.errors.getAllErrors()])}"
            }
        } else {
            flash.errorAlert = "${g.message(code: "privateData.unable_to_create")}"
        }
        redirect action: "privateDatasets", params: [accountId: accountId]
    }

    def uploadPrivateDatasetExcel() {
        String accountId = params.accountId
        Account account = accountService.getAccount(accountId)
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "privateDatasets", params: [accountId: accountId]
            } else {
                try {
                    def mapWithInfo = optimiResourceService.importExcelWorkbook(excelFile, false, account)
                    def file = mapWithInfo.get("file")
                    def sheets = mapWithInfo.get("sheets")
                    def okResources = mapWithInfo.get("okResources")
                    def rejectedResources = mapWithInfo.get("rejectedResources")
                    def duplicateDefaults = mapWithInfo.get("duplicateDefaults")
                    def duplicateResourceAndProfiles = mapWithInfo.get("duplicateResourceAndProfiles")
                    def rejectedDefaultProfiles = mapWithInfo.get("rejectedDefaultProfiles")
                    def errorResources = mapWithInfo.get("errorResources")
                    def deprecatedFieldsError = mapWithInfo.get("deprecatedFieldsError")
                    def dataQualityError = mapWithInfo.get("dataQualityError")
                    def warnMessage = ""
                    def errorMessage

                    if (okResources) {
                        flash.fadeSuccessAlert = "Imported ${file}, ${sheets ? sheets.size() : 0} sheets, successfully imported ${okResources} resources, rejected ${rejectedResources} resources."
                    } else {
                        errorMessage = "Import rejected due to error(s) in the file"
                    }

                    if (mapWithInfo.get("errorMessage")) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + mapWithInfo.get("errorMessage") : mapWithInfo.get("errorMessage")
                    }

                    if (errorResources) {
                        errorResources.each { Resource resource ->
                            errorMessage = errorMessage ? errorMessage + "<br />" + renderErrors(bean: resource) : renderErrors(bean: resource)
                        }
                    }

                    if (rejectedDefaultProfiles) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + rejectedDefaultProfiles : rejectedDefaultProfiles
                    }

                    if (errorMessage) {
                        flash.errorAlert = errorMessage
                    }


                    if (duplicateDefaults) {
                        warnMessage = "Duplicate defaultProfiles: "

                        duplicateDefaults.each { Resource r ->
                            warnMessage = warnMessage + "resourceId and profileId: ${r.resourceId} ${r.profileId}, "
                        }
                    }

                    if (duplicateResourceAndProfiles) {
                        warnMessage = warnMessage + "Duplicate resource and profile ids: "

                        duplicateResourceAndProfiles.each { Resource r ->
                            warnMessage = warnMessage + "${r.resourceId} / ${r.profileId}<br />"
                        }
                    }

                    if (deprecatedFieldsError) {
                        warnMessage = warnMessage ? warnMessage + "<br />" + deprecatedFieldsError : deprecatedFieldsError
                    }

                    if (dataQualityError) {
                        warnMessage = warnMessage ? warnMessage + "<br />" + dataQualityError : dataQualityError
                    }

                    if (warnMessage) {
                        flash.fadeWarningAlert = warnMessage
                    }

                    redirect(action: "privateDatasets", params: [accountId: accountId])
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect(action: "privateDatasets", params: [accountId: accountId])
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "privateDatasets", params: [accountId: accountId]
        }
    }

    def enablePrivateDataset() {
        String accountId = params.accountId
        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(params.resourceId, null, true)

        if (resource) {
            resource.active = true
            if (!resource.firstActivationTime && resource.isNewPrivateDataset) {
                resource.isNewPrivateDataset = false
                resource.firstActivationTime = new Date()
            }
            Boolean removed = optimiResourceService.deleteResourceFromCache(resource)
            resource = resource.save(flush: true)
            flash.fadeSuccessAlert = "${message(code: "resource.enable", args: [resource?.nameEN])}"
        } else {
            flash.fadeErrorAlert = "${message(code: "resource.enable_error")}"
        }
        redirect action: "privateDatasets", params: [accountId: accountId]
    }

    def disablePrivateDataset() {
        String accountId = params.accountId
        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(params.resourceId, null, true)

        if (resource) {
            resource.active = false
            Boolean removed = optimiResourceService.deleteResourceFromCache(resource)
            resource = resource.save(flush: true)
            flash.fadeSuccessAlert = "${message(code: "resource.disable", args: [resource?.nameEN])}"
        } else {
            flash.fadeErrorAlert = "${message(code: "resource.disable_error")}"
        }
        redirect action: "privateDatasets", params: [accountId: accountId]
    }
   /* def editPrivateDataset() {
        String accountId = params.accountId
        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(params.resourceId, null, true)

        if (resource) {
             resource = resource.save(flush: true)
            flash.fadeSuccessAlert = "${message(code: "resource.updated", args: [resource?.nameEN])}"
        } else {
            flash.fadeErrorAlert = "${message(code: "resource.update_error")}"
        }
        redirect action: "privateDatasets", params: [accountId: accountId]
    }*/

    def editPrivateDataset() {
        String accountId = request?.JSON.accountId
        String resourceId = request?.JSON.resourceId
        if(accountId){
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, null, true)
            if (resource) {
                Query privateResourceCreationQuery = queryService.getQueryByQueryId(privateResourceCreationQueryId, true)
                /*BasicDBObject filter = new BasicDBObject("active", true)
                List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({it.nameEN})
                Map<String, String> typeForUpdate = [event: 'Event', update: 'Update']
                List<String> indicatorIds =  Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()?.sort({ it.toLowerCase() })
                def channelFeatures = channelFeatureService.getAllChannelFeatures()*/
                //def model = [update: updatePublish, countries: countries, typeForUpdate: typeForUpdate, channelFeatures:channelFeatures,indicatorIds: indicatorIds, licenseTypes:Constants.LicenseType.list(), licenceFeatureClass: Constants.LicenseFeatureClass.list()]
                Map model = [privateResourceCreationQuery:privateResourceCreationQuery , entityClassResource:resource , accountId:accountId , resourceId : resourceId]
                String templateAsString = g.render(template: "/account/editPrivateDataModal", model: model).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                render([output: "Render Update Public modal: No object found with ID: ${resource.resourceId}", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            render([output: "Render Update Public modal: no ID given: ${accountId}", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }
    def deletePrivateDataset() {
        String privateEpdFilePath = optimiResourceService.getResourcePrivateEpdFilePath(params.resourceId)
        def deleteOk = optimiResourceService.deleteResource(params.resourceId)
        String returnMessage
        String errorMessage
        String accountId = params.accountId
        String privateEpdDelete = params.privateEpdDelete


        if (deleteOk) {
            returnMessage = message(code: "admin.import.resource_delete_ok")
            flash.fadeSuccessAlert = returnMessage
        } else {
            errorMessage = "Deletion already done. Don't push the \"Delete\" multiple times"
            flash.fadeErrorAlert = errorMessage
        }
        if (privateEpdDelete) {
            String classificationParameterId = params.classificationParameterId
            String classificationQuestionId = params.classificationQuestionId
            if (deleteOk && privateEpdFilePath) {
                File epdcXmlFile = new File(privateEpdFilePath)

                if (epdcXmlFile && epdcXmlFile.exists()) {
                    epdcXmlFile.delete()
                }
            }
            redirect action: "uploadEPDC", params: [accountId: accountId, classificationQuestionId:classificationQuestionId,
                                                    classificationParameterId:classificationParameterId]
        }else {
            redirect action: "privateDatasets", params: [accountId: accountId]
        }
    }

    def save() {
        Account account
        boolean sendEmail = false
        Boolean showInfoMessage
        accountService.parseNumberInParamsForAccount(params)
        if (params.id) {
            account = accountService.getAccount(params.id)
            account?.properties = params
        } else {
            account = new Account(params)
            account.mainUserIds = [userService.getCurrentUser(false)?.id.toString()]
            sendEmail = true
            showInfoMessage = Boolean.TRUE

            String country = params.country
            account.catalogueSlugs = AccountCatalogue.list()?.findAll({ it.countries?.contains(country) })?.collect({
                it.slug
            })
        }

        if (account && account.validate()) {
            try {
                account = accountService.saveAccount(account)

                if (sendEmail && activeCampaignService.isActiveCampaignAllowed()) {
                    sendAccountCreatedEmail(account)
                }

                if (!showInfoMessage) {
                    flash.fadeSuccessAlert = "Account saved successfully"
                }
            } catch (Exception e) {
                flash.errorAlert = "Error in saving account: ${e}"
            }
            redirect action: "form", params: [id: account.id, newAccount: showInfoMessage]
        } else {
            flash.errorAlert = "Error in saving account: ${renderErrors(bean: account)}"

            if (account?.id) {
                redirect action: "form", params: [id: account.id]
            } else {
                chain action: "form", model: [account: account]
            }
        }
    }

    private void sendAccountCreatedEmail(Account account) {
        String messageSubject

        if (Environment.current == Environment.DEVELOPMENT) {
            messageSubject = "DEV: "
        } else if (Environment.current == Environment.PRODUCTION) {
            messageSubject = "PROD: "
        }
        messageSubject = "${messageSubject}New account for company ${account.companyName} created"
        String messageBody = "Account information:<br /><br />"
        List<PersistentProperty> accountProperties = domainClassService.getPersistentPropertiesForDomainClass(Account.class).findAll({
            !"id".equals(it.name) && !"version".equals(it.name) && !"class".equals(it.name) &&
                    !"mainUserIds".equals(it.name) && !"userids".equals(it.name) && !"licenseIds".equals(it.name) &&
                    !"branding".equals(it.name) && !"backgroundImage".equals(it.name)
        })

        accountProperties.each { PersistentProperty property ->
            messageBody = "${messageBody}${property.name}: ${account[property.name]}<br />"
        }
        messageBody = "${messageBody}MainUsers: ${account.mainUsers?.collect({ it.name })}<br />"
        optimiMailService.sendMail({
            to emailConfiguration.supportEmail, emailConfiguration.salesLeadEmail
            from emailConfiguration.accountEmail
            replyTo emailConfiguration.noReplyEmail
            subject(messageSubject)
            html(messageBody)
        }, "${emailConfiguration.supportEmail},${emailConfiguration.salesLeadEmail}")
    }

    def delete() {
        Account account = accountService?.getAccount(params.id)

        if (account) {
            accountService.delete(account)
            flash.fadeSuccessAlert = "account removed succesfully"
        }


        chain action: "index"

    }

    def addUser() {
        String newUser = ""
        User user

        if (params.id) {
            if (params.userId) {
                user = userService.getUserById(params.userId)
            } else if (params.mainUserId) {
                user = userService.getUserById(params.mainUserId)
            }
            Account account = userService.getAccount(user)
            if (user && !account) {
                if (params.mainUserId) {
                    user = accountService.addMainUser(params.mainUserId, params.id)
                } else {
                    user = accountService.addUser(params.userId, params.id)
                }
                newUser = "<tr><td>${user.name} (${user.username})</td>" +
                        "<td>${link(action: 'removeUser', id: params.id, params: [userId: params.userId], class: 'btn btn-danger') { message(code: 'delete') }}</td></tr>"

            } else if (user && account && params.id.toString().equals(account.id.toString())) {
                newUser = "<div class=\"alert alert-error fadetoggle\" data-fadetoggle-delaytime=\"10000\">\n" +
                        "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>\n" +
                        "    <strong>${message(code: 'user.account.equals', args: [user?.username])}</strong></div>"
            } else {
                newUser = "<div class=\"alert alert-error fadetoggle\" data-fadetoggle-delaytime=\"10000\">\n" +
                        "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>\n" +
                        "    <strong>${message(code: 'user.account.exists', args: [user?.username])}</strong></div>"
            }
        }
        render([output: newUser, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def removeUser() {
        if (params.id) {
            Account account = accountService.getAccount(params.id)

            if (account) {
                if (params.userId) {
                    account.userIds?.remove(params.userId)
                } else if (params.mainUserId) {
                    account.mainUserIds?.remove(params.mainUserId)
                }
                account.merge(flush: true)
            }
            redirect action: "form", id: params.id
        } else {
            redirect action: "index"
        }
    }

    def addLicense() {
        def newLicense = ""
        License license

        if (params.id && params.licenseId) {
            license = accountService.addLicense(params.licenseId, params.id)

            if (license) {
                newLicense = "<tr><td>${license.name})</td>" +
                        "<td>${link(action: 'removeLicense', id: params.id, params: [licenseId: params.licenseId], class: 'btn btn-danger') { message(code: 'delete') }}</td></tr>"
            }

        }
        render([output: newLicense, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addCatalogue() {
        String newSlug = ""

        if (params.id && params.slug) {
            Account account = Account.get(params.id)
            AccountCatalogue accountCatalogue = AccountCatalogue.findBySlug(params.slug)

            if (account && accountCatalogue) {
                if (account.catalogueSlugs) {
                    account.catalogueSlugs.add(params.slug)
                } else {
                    account.catalogueSlugs = [params.slug]
                }
                account.merge(flush: true)
                newSlug = "<tr><td>${accountCatalogue.slug}</td><td>${accountCatalogue.name}</td>" +
                        "<td>${link(action: 'removeCatalogueFromAccount', id: params.id, params: [slug: accountCatalogue.slug], class: 'btn btn-danger') { message(code: 'delete') }}</td></tr>"
            }
        }
        render([output: newSlug, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def removeCatalogueFromAccount() {
        if (params.id && params.slug) {
            Account account = Account.get(params.id)

            if (account?.catalogueSlugs?.contains(params.slug)) {
                account.catalogueSlugs.remove(params.slug)
                account.merge(flush: true)
            }
            redirect action: "form", id: params.id
        } else {
            redirect action: "index"
        }
    }

    def removeLicense() {
        if (params.id && params.licenseId) {
            Account account = accountService.getAccount(params.id)

            if (account) {
                account.licenseIds?.remove(params.licenseId)
                account.merge(flush: true)
            }
            redirect action: "form", id: params.id
        } else {
            redirect action: "index"
        }
    }

    // Deprecate code, to be removed by the end of feature 0.5.0 release
//    def accountCatalogues() {
//        [accountCatalogues: AccountCatalogue.list()?.sort({ it.slug }),
//         countries  form(      : optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)]
//    }

    def saveCatalogue() {
        AccountCatalogue accountCatalogue

        if (params.id) {
            accountCatalogue = AccountCatalogue.get(params.id)
            accountCatalogue.properties = params

            if (!params.countries) {
                accountCatalogue.countries = null
            }
        } else {
            accountCatalogue = new AccountCatalogue(params)
        }

        if (accountCatalogue.validate()) {
            accountCatalogue = accountCatalogue.save(flush: true)
            flash.fadeSuccessAlert = "Account catalogue ${accountCatalogue.name} saved successfully"
        } else {
            flash.errorAlert = renderErrors(bean: accountCatalogue)
        }
        redirect action: "accountCatalogues"
    }

    def removeCatalogue() {
        if (params.id) {
            AccountCatalogue accountCatalogue = AccountCatalogue.get(params.id)

            if (accountCatalogue) {
                accountCatalogue.delete(flush: true)
                flash.fadeSuccessAlert = "Account catalogue ${accountCatalogue.name} removed successfully"
            } else {
                flash.fadeErrorAlert = "Error in removing account catalogue"
            }
        } else {
            flash.fadeErrorAlert = "Error in removing account catalogue"
        }
        redirect action: "accountCatalogues"
    }

    def requestToJoin() {
        String returnable = ""

        if (params.id) {
            Account account = accountService.getAccount(params.id)
            User user = userService.getCurrentUser()
            boolean requestOk = false

            if (!account.requestToJoinUserIds) {
                account.requestToJoinUserIds = [user.id.toString()]
                requestOk = true
            } else if (!account.requestToJoinUserIds.contains(user.id.toString())) {
                account.requestToJoinUserIds.add(user.id.toString())
                requestOk = true
            }

            if (requestOk) {
                accountService.saveAccount(account)
                returnable = "${message(code: 'account.request.to_join', args: [account.companyName])} "
                List<String> emailTo = []

                accountService.getMainUsers(account.mainUserIds)?.each { User mainUser ->
                    emailTo.add(mainUser.username)
                }

                if (emailTo) {
                    optimiMailService.sendMail({
                        to emailTo
                        from "account@oneclicklca.com"
                        replyTo "noreply@oneclicklca.com"
                        subject("User ${user.name} (${user.username}) has requested to join your organization ${account.companyName} on One Click LCA")
                        html("<p>Hi!</p><p>User ${user.name} (${user.username}) has requested to join your organization's ${account.companyName} account on One Click LCA.</p>" +
                                "<p>You can accept the request or deny the request <a href=\"${createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath", controller: 'account', action: 'form', id: account.id)}\">here.</a>")
                    }, emailConfiguration.supportEmail)
                }
            }
        }
        render([output: returnable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def acceptRequest() {
        if (params.id && params.userId) {
            Account account = accountService.getAccount(params.id)

            if (account.requestToJoinUserIds?.contains(params.userId)) {
                boolean addOk = false

                if (!account.userIds) {
                    account.userIds = [params.userId]
                    addOk = true
                } else if (!account.userIds.contains(params.userId)) {
                    account.userIds.add(params.userId)
                    addOk = true
                }
                account.requestToJoinUserIds.remove(params.userId)
                accountService.saveAccount(account)

                if (addOk) {
                    flash.fadeSuccessAlert = message(code: "account.request_to_join.accept", args: [userService?.getUserById(params.userId)?.username])
                }
            }
        }
        redirect(action: "form", id: params.id)
    }

    def declineRequest() {
        if (params.id && params.userId) {
            Account account = accountService.getAccount(params.id)

            if (account.requestToJoinUserIds?.contains(params.userId)) {
                account.requestToJoinUserIds.remove(params.userId)
                accountService.saveAccount(account)
                flash.fadeErrorAlert = message(code: "account.request_to_join.decline", args: [userService?.getUserById(params.userId)?.username])
            }
        }
        redirect(action: "form", id: params.id)
    }

    def validateVat() {
        String countryCode = params.countryCode
        String vatNumber = params.vatNumber
        Map<String, String> validationData = customSoapService.validateVat(countryCode, vatNumber)
        render validationData as JSON
    }

    def manageFavorites() {
        String accountId = params.accountId
        User user = userService.getCurrentUser()
        Account account
        Map<Resource, String> resources = [:]
        def resourceList
        boolean hasDownloadEPDLicense = false

        Map<String, String> countryCodesAndLocalizedName = [:]
        if(accountId){
            account = accountService.getAccount(accountId)
            if (account && account.favoriteMaterialIdAndUserId && account.favoriteMaterialIdAndUserId.size() > 0){

                List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)
                if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.EPD_DOWNLOAD) })) { hasDownloadEPDLicense = true }

                account.favoriteMaterialIdAndUserId.each {String resourceText, String userId ->
                    def values = resourceText.tokenize(".")
                    if(values){
                        Resource r = optimiResourceService.getResourceByResourceAndProfileId(values[0],values[1])
                        if(r){
                            String area = r?.isoCodesByAreas?.keySet()?.first()
                            String isoCode = r?.isoCodesByAreas?.get(area)

                            if (isoCode && !countryCodesAndLocalizedName?.get(isoCode)) {
                                String localizedCountryName = optimiResourceService.getCountryLocalizedName(area)
                                countryCodesAndLocalizedName.put(isoCode, localizedCountryName)
                            }
                            User userWhoFaved = userService.getUserById(userId)
                            if (userWhoFaved) {
                                resources.put(r, maskingService.maskEmail(userWhoFaved?.username))
                            } else {
                                resources.put(r, "")

                            }
                        }

                    }

                }

            }
        }
        [resources: resources, account:account, countryCodesAndLocalizedName: countryCodesAndLocalizedName, downloadEPDLicense: hasDownloadEPDLicense, user: user]

    }

    def addAccountImage() {
        Map responseData
        String accountId = params.accountId
        String imageName = optimiStringUtils.removeIllegalCharacters(params.imageName)
        String imageType = params.imageType
        String imageStyle = params.imageStyle
        MultipartFile imageFile = fileUtil.getFileFromRequest(request)
        log.info("AccountId: ${accountId}")
        log.info("imageName: ${imageName}")
        log.info("imageType: ${imageType}")
        log.info("imageStyle: ${imageStyle}")
        log.info("imageFile: ${imageFile}")
        log.info("CONTENT TYPE HIT: ${imageFile.getContentType()}")

        if (accountId && imageFile && (imageFile.getContentType() == "image/jpg" || imageFile.getContentType() == "image/jpeg" || imageFile.getContentType() == "image/gif" || imageFile.getContentType() == "image/png")) {

            Account account = accountService.getAccount(accountId)
            AccountImages accountImages = new AccountImages(name: imageName, type: imageType, style: imageStyle, accountId: accountId, image: imageFile)
            if (account && accountImages && accountImages.validate()) {
                try{
                    accountImages.save(flush:true, failOnError:true)
                    def data = accountImagesService.getAccountImageAsB64Data(accountImages.id.toString())
                    String defaultImage = ""
                    String name = "${accountImages.name}"
                    String style = "${accountImages.style}"
                    String image = "<img src=\"data:image/jpeg;" + "base64," + data + "\" id=\"${accountImages.name ?:''}\"/>"
                    Boolean isDefaultImage = false

                    if (imageType.equals(Constants.AccountImageType.PRODUCTIMAGE.toString())) {
                        if (!account.defaultProductImage) {
                            account.defaultProductImage = accountImages.id.toString()
                            isDefaultImage = true
                            account.save(flush:true, failOnError:true)
                        }
                        defaultImage = "${account?.defaultProductImage?.equals(accountImages.id.toString()) ? '<i class=\"defaultImage far fa-check-circle\"></i>' : ''}"

                    } else if (imageType.equals(Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString())) {
                        if (!account.defaultManufacturingDiagram) {
                            account.defaultManufacturingDiagram = accountImages.id.toString()
                            isDefaultImage = true
                            account.save(flush:true, failOnError:true)
                        }
                        defaultImage = "${account?.defaultManufacturingDiagram?.equals(accountImages.id.toString()) ? '<i class=\"defaultImage far fa-check-circle\"></i>' : ''}"

                    } else if (imageType.equals(Constants.AccountImageType.BRANDINGIMAGE.toString())) {
                        if (!account.defaultBrandingImage) {
                            account.defaultBrandingImage = accountImages.id.toString()
                            isDefaultImage = true
                            account.save(flush:true, failOnError:true)
                        }
                        defaultImage = "${account?.defaultBrandingImage?.equals(accountImages.id.toString()) ? '<i class=\"defaultImage far fa-check-circle\"></i>' : ''}"
                    }
                    String buttons = "<td><a href=\"/app/sec/account/setDefaultImage?accountId=${account.id}&amp;accountImageId=${accountImages.id.toString()}\" class=\"${isDefaultImage? "removeClicks" : ""}\"><input type=\"button\" value=\"${message(code: 'account.set_default')}\" class=\"btn btn-primary\" style=\"width: 100%; margin-bottom: 3px;\"/></a>" +
                                     "<a href=\"/app/sec/account/deleteAccountImage?accountId=${account.id}&amp;accountImageId=${accountImages.id.toString()}\"><input type=\"button\" value=\"${message(code: 'delete')}\" class=\"btn btn-danger\" style=\"width: 100%; margin-bottom: 3px;\"/></a></td></tr>"

                    if (imageType.equals(Constants.AccountImageType.BRANDINGIMAGE.toString())) {
                        responseData = [default: defaultImage, name: name, image: image, buttons: buttons, (flashService.FLASH_OBJ_FOR_AJAX): flash]
                    } else {
                        responseData = [default: defaultImage, name: name, style: style, image: image, buttons: buttons, (flashService.FLASH_OBJ_FOR_AJAX): flash]
                    }
                    render(responseData as JSON)
                } catch (Exception e) {
                    responseData = [errorMessage: "Error saving accountImage: ${e.getMessage()}", (flashService.FLASH_OBJ_FOR_AJAX): flash]
                    log.error("Error saving accountImage: ${e.getMessage()}")
                }

            } else {
                responseData = [errorMessage: "<h4 class=\"alert-error\">${g.message(code: "results.incomplete_queries")}</h4>" + renderErrors(bean: accountImages), (flashService.FLASH_OBJ_FOR_AJAX): flash]
                log.error("Missing account, no unique name or accountImage problems" + renderErrors(bean: accountImages))
            }

        } else {
            responseData = [errorMessage: g.message(code: "entity.wrong_image_type"), (flashService.FLASH_OBJ_FOR_AJAX): flash]
        }
        render(responseData as JSON)
    }

    def setDefaultImage() {
        String accountId = params.accountId
        String accountImageId = params.accountImageId

        if(accountId && accountImageId) {
            Account account = accountService.getAccount(accountId)
            AccountImages accountImage = accountImagesService.getAccountImage(accountImageId)

            if(account && accountImage) {
                try {
                    if (accountImage.type.equals(Constants.AccountImageType.PRODUCTIMAGE.toString())) {
                        account.defaultProductImage = accountImage.id.toString()
                        flash.fadeSuccessAlert = "${accountImage.name} set as default product image"
                    } else if (accountImage.type.equals(Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString())) {
                        account.defaultManufacturingDiagram = accountImage.id.toString()
                        flash.fadeSuccessAlert = "${accountImage.name} set as default manufacturing diagram"
                    } else if (accountImage.type.equals(Constants.AccountImageType.BRANDINGIMAGE.toString())) {
                        account.defaultBrandingImage = accountImage.id.toString()
                        flash.fadeSuccessAlert = "${accountImage.name} set as default brand image"
                    }
                    account.save(flush:true, failOnError:true)
                } catch (Exception e) {
                    flash.errorAlert = "Failed to set default image: ${e.getMessage()}"
                    log.error("Setting default account image: ${e.getMessage()}")
                }

            }
            redirect action: "form", id: accountId
        }
    }

    def deleteAccountImage() {
        String accountId = params.accountId
        String accountImageId = params.accountImageId

        if(accountId && accountImageId) {
            Account account = accountService.getAccount(accountId)
            AccountImages accountImage = accountImagesService.getAccountImage(accountImageId)

            if(account && accountImage) {
                try {
                    if (accountImage.type.equals(Constants.AccountImageType.PRODUCTIMAGE.toString())) {
                        if (account.defaultProductImage.equals(accountImage.id.toString())) {
                            account.defaultProductImage = null
                        }
                        flash.fadeSuccessAlert = "${accountImage.name} is successfully deleted"
                        accountImage.delete(flush: true, failOnError: true)

                    } else if (accountImage.type.equals(Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString())) {
                        if (account.defaultManufacturingDiagram.equals(accountImage.id.toString())) {
                            account.defaultManufacturingDiagram = null
                        }
                        flash.fadeSuccessAlert = "${accountImage.name} is successfully deleted"
                        accountImage.delete(flush: true, failOnError: true)
                    } else if (accountImage.type.equals(Constants.AccountImageType.BRANDINGIMAGE.toString())) {
                        if (account.defaultBrandingImage.equals(accountImage.id.toString())) {
                            account.defaultBrandingImage = null
                        }
                        flash.fadeSuccessAlert = "${accountImage.name} is successfully deleted"
                        accountImage.delete(flush: true, failOnError: true)
                    }
                    account.save(flush:true, failOnError:true)
                } catch (Exception e) {
                    flash.errorAlert = "Failed to delete image: ${e.getMessage()}"
                    log.error("Failed to delete account image: ${e.getMessage()}")
                }

            }
            redirect action: "form", id: accountId
        }
    }
}
