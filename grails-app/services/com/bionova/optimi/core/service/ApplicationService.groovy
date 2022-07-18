package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.ApplicationExpandFeatureSets
import com.bionova.optimi.core.domain.mongo.ApplicationIndicatorDefinitions
import com.bionova.optimi.core.domain.mongo.ApplicationResourceExtraInformationSet
import com.bionova.optimi.core.domain.mongo.DefaultValue
import com.bionova.optimi.core.domain.mongo.DefaultValueSet
import com.bionova.optimi.core.domain.mongo.DiscountScenario
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExpandFeature
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceFilterRule
import com.bionova.optimi.core.domain.mongo.ResourcePurpose
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.util.FileUtil
import com.gmongo.GMongo
import com.mongodb.BasicDBObject
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class ApplicationService {

    def queryService
    def configurationService
    LoggerUtil loggerUtil
    def shellCmdService
    def userService
    def messageSource
    def indicatorService
    def optimiResourceService
    def featureService
    FlashService flashService
    def grailsCacheAdminService

    static final String NMD_applicationId = 'NMD'

    def getCurrentApplicationForUser() {

    }

    List<Map> getApplicationFilterRules(String applicationId) {
        List<Map> filterRulesByGroup = []
        if (applicationId) {
            Application application = getApplicationByApplicationId(applicationId)

            if (application && application.filterRules) {
                for (ResourceFilterRule filterRule in application.filterRules) {
                    if (filterRule.filterGroup) {
                        Map rulesGroup = filterRulesByGroup.find({ filterRule.filterGroup.equals(it.get("text")) })
                        if (rulesGroup) {
                            List children = rulesGroup.get("children")
                            children.add([id: filterRule.ruleId, text: filterRule.localizedName])
                            rulesGroup.put("children", children)
                        } else {
                            rulesGroup = [id: '', text: filterRule.filterGroup, children: [[id: filterRule.ruleId, text: filterRule.localizedName]]]
                            filterRulesByGroup.add(rulesGroup)
                        }
                    } else {
                        Map otherRulesGroup = filterRulesByGroup.find({ "Other application filterRules".equals(it.get("text")) })
                        if (otherRulesGroup) {
                            List children = otherRulesGroup.get("children")
                            children.add([id: filterRule.ruleId, text: filterRule.localizedName])
                            otherRulesGroup.put("children", children)
                        } else {
                            otherRulesGroup = [id: '', text: 'Other application filterRules', children: [[id: filterRule.ruleId, text: filterRule.localizedName]]]
                            filterRulesByGroup.add(otherRulesGroup)
                        }
                    }
                }
            }
        }
        return filterRulesByGroup
    }

    List<Map> getApplicationResourcePurposes(String applicationId) {
        List<Map> filterRulesByGroup = []
        if (applicationId) {
            Application application = getApplicationByApplicationId(applicationId)

            if (application && application.resourcePurposes) {
                for (ResourcePurpose resourcePurpose in application.resourcePurposes) {
                    Map otherRulesGroup = filterRulesByGroup.find({ "Application resourcePurposes".equals(it.get("text")) })
                    if (otherRulesGroup) {
                        List children = otherRulesGroup.get("children")
                        children.add([id: resourcePurpose.attribute, text: resourcePurpose.attribute])
                        otherRulesGroup.put("children", children)
                    } else {
                        otherRulesGroup = [id: '', text: 'Application resourcePurposes', children: [[id: resourcePurpose.attribute, text: resourcePurpose.attribute]]]
                        filterRulesByGroup.add(otherRulesGroup)
                    }
                }
            }
        }
        return filterRulesByGroup
    }

    List<Application> getApplicationsForCurrentUser() {
        List<Application> applications
        User user = userService.getCurrentUser()

        if (user) {
            applications = getApplicationsForUser(user)
        }
        return applications
    }

    List<Application> getApplicationsForUser(User user) {
        List<Application> applications

        if (user) {
            if (userService.getSuperUser(user)) {
                applications = getAllApplications()
            } else {
                applications = Application.collection.find([applicationEditorIds:[$in:[user.id]]])?.collect({it as Application})
            }
        }
        return applications
    }

    Application saveApplication(Application application) {
        if (application && !application.hasErrors()) {
            if (application.id) {
                application = application.merge(flush: true, failOnError: true)
            } else {
                application = application.save(flush: true, failOnError: true)
            }
        }
        return application
    }

    boolean removeApplication(id) {
        boolean removeOk = false

        if (id) {
            Application application = getApplicationById(id)

            if (application) {
                loggerUtil.info(log, "Removing application ${application.applicationId}")
                application.delete(flush: true)
                removeOk = true
            }
        }
        return removeOk
    }

    boolean addApplicationEditor(String id, String email) {
        boolean addOk = false

        if (id && email) {
            Application application = getApplicationById(id)
            User user = User.findByUsername(email.trim().toLowerCase())

            if (user && application && !application.applicationEditorIds?.contains(user.id)) {
                if (application.applicationEditorIds) {
                    application.applicationEditorIds.add(user.id)
                } else {
                    application.applicationEditorIds = [user.id]
                }
                application.merge(flush: true)
                addOk = true
            }
        }
        return addOk
    }

    boolean removeApplicationEditor(String id, String userId) {
        boolean removeOk = false

        if (id && userId) {
            Application application = getApplicationById(id)
            ObjectId userIdAsObjectId = new ObjectId(userId)

            if (application && application.applicationEditorIds?.contains(userIdAsObjectId)) {
                application.applicationEditorIds.remove(userIdAsObjectId)
                application.merge(flush: true)
                removeOk = true
            }
        }
        return removeOk
    }

    Application getApplicationById(id) {
        Application application

        if (id) {
            application = Application.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return application
    }

    Application getApplicationByApplicationId(String applicationId) {
        Application application

        if (applicationId) {
            application = Application.findByApplicationId(applicationId)
        }
        return application
    }

    Document getApplicationAsDocumentByApplicationId(String applicationId, Map projection = null) {
        Document application

        if (applicationId) {
            if (projection) {
                application = Application.collection.findOne([applicationId: applicationId], projection)
            } else {
                application = Application.collection.findOne([applicationId: applicationId])
            }
        }
        return application
    }

    List<Document> getApplicationsAsDocumentsByApplicationIdList(List applicationIdList, Map projection = [:]) {
        List<Document> applications

        if (applicationIdList) {
            applications = Application.collection.find([applicationId: [$in: applicationIdList]], projection).toList()
        }
        return applications
    }

    List<Application> getApplicationsByImportMapperFeatureIds(List<String> featureIds) {
        List<Application> applications = []

        if (featureIds && !featureIds.isEmpty()) {
            applications = Application.collection.find([importMappers:[$elemMatch:[licenseKey:[$in:featureIds]]]])?.collect({it as Application})
        }
        return applications
    }

    Application getApplicationByDenominatorId(String denominatorId) {
        Application application

        if (denominatorId) {
            application = Application.collection.findOne([denominators:[$elemMatch:[denominatorId:denominatorId]]]) as Application
        }
        return application
    }

    List<Application> getAllApplications() {
        return Application.list().sort({ it.applicationId })
    }

    List<String> getAllApplicationApplicationIds() {
        return Application.collection.distinct("applicationId", String.class)?.toList()?.sort()
    }

    List<Application> getApplicationsWithDefaults() {
        return Application.findAllByDefaultValuesIsNotNull()
    }

    List<Query> getApplicationQueries(String applicationId) {
        List<Query> queries = queryService.getQueriesByApplicationId(applicationId)

        if (queries) {
            return queries.unique({ it.queryId })
        } else {
            return null
        }
    }

    List<Query> getApplicationQueryIds(String applicationId) {
        BasicDBObject filter = new BasicDBObject("applicationId", applicationId)
        return Query.collection.distinct("queryId", filter, String.class)?.toList()?.sort()
    }

    List<Indicator> getApplicationIndicators(String applicationId) {
        List<Indicator> indicators

        if (applicationId) {
            indicators = indicatorService.getAllIndicators()?.findAll({
                (it.applicationRules || it.applicationCategories || it.applicationDenominators) &&
                        (it.applicationRules?.keySet()?.toList()?.contains(applicationId) || it.applicationCategories?.keySet()?.toList()?.contains(applicationId) ||
                                it.applicationDenominators?.keySet()?.toList()?.contains(applicationId))
            })
        }

        if (indicators) {
            return new ArrayList<Indicator>(indicators).unique({ it.indicatorId })
        } else {
            return null
        }
    }

    List<Indicator> getApplicationIndicatorIds(String applicationId) {
        BasicDBObject filter = new BasicDBObject()
        BasicDBObject applicationRules = new BasicDBObject()
        BasicDBObject applicationCategories = new BasicDBObject()
        BasicDBObject applicationDenominators = new BasicDBObject()
        applicationRules["applicationRules." + applicationId] = [$exists: true]
        applicationCategories["applicationCategories." + applicationId] = [$exists: true]
        applicationDenominators["applicationDenominators." + applicationId] = [$exists: true]
        filter.put("\$or", [applicationRules,applicationCategories,applicationDenominators])
        return Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()?.sort()
    }

    List<Resource> getApplicationResources(String applicationId) {
        List<Resource> applicationResources

        if (applicationId) {
            List<Resource> resources = optimiResourceService.getResourcesByApplicationId(applicationId)

            if (resources && !resources.isEmpty()) {
                applicationResources = new ArrayList<Resource>(resources)
            }
        }
        return applicationResources
    }

    long getApplicationResourcesCount(String applicationId) {
        if (applicationId) {
            return Resource.collection.count([applicationId: applicationId, active: true])
        } else {
            return 0
        }
    }

    private void loadApplicationFiltersForQueries(String applicationId, List<QueryFilter> defaultFilters) {
        if (applicationId && defaultFilters) {
            List<Query> queries = Query.findAllByActiveAndApplicationFiltersIsNotNull(true)?.findAll({
                it.applicationFilters.get(applicationId)
            })

            if (queries) {
                queries.each { Query query ->
                    query.supportedFilters = defaultFilters
                    queryService.saveQuery(query)
                }
            }
        }
    }

    private void loadApplicationDefaultsForIndicators(String applicationId, List<ApplicationIndicatorDefinitions> indicatorCoreSettings, List<IndicatorExpandFeature> indicatorReportGeneration) {
        if (applicationId) {
            List<Indicator> indicators = Indicator.findAllByActiveAndIndicatorDefaultsIsNotNull(true)
            List<String> indicatorsToSave = []

            if (indicatorCoreSettings) {
                indicatorCoreSettings.each { ApplicationIndicatorDefinitions applicationIndicatorDefinition ->
                    String indicatorSettingsId = applicationIndicatorDefinition.indicatorSettingsId

                    if (indicatorSettingsId && indicators) {
                        indicators.each { Indicator indicator ->
                            if (indicatorSettingsId.equals(indicator.indicatorDefaults.get("coreSettings")) && applicationId.equals(indicator.indicatorDefaults.get("applicationId"))) {
                                applicationIndicatorDefinition.properties.each { String propertyName, Object propertyValue ->
                                    if (propertyValue != null) {
                                        try {
                                            DomainObjectUtil.callSetterByAttributeName(propertyName, indicator, propertyValue, Boolean.TRUE)
                                        } catch (NoSuchMethodException e) {
                                            // Just some applicationIndicatorDefinition attributes that cannot be set for indicator, carry on
                                        }
                                    }
                                }

                                if (!indicatorsToSave.contains(indicator.indicatorId)) {
                                    indicatorsToSave.add(indicator.indicatorId)
                                }
                            }
                        }
                    }
                }
            }

            if (indicatorReportGeneration) {
                indicatorReportGeneration.each { IndicatorExpandFeature indicatorExpandFeature ->
                    String expandFeaturesId = indicatorExpandFeature.expandFeaturesId

                    if (expandFeaturesId && indicators) {
                        indicators.each { Indicator indicator ->
                            if (expandFeaturesId.equals(indicator.indicatorDefaults.get("reportGeneration")) && applicationId.equals(indicator.indicatorDefaults.get("applicationId"))) {
                                indicator.expandFeatures = indicatorExpandFeature

                                if (!indicatorsToSave.contains(indicator.indicatorId)) {
                                    indicatorsToSave.add(indicator.indicatorId)
                                }
                            }
                        }
                    }
                }
            }

            if (!indicatorsToSave.isEmpty()) {
                indicators.each { Indicator indicator ->
                    if (indicatorsToSave.contains(indicator.indicatorId)) {
                        indicatorService.mergeIndicator(indicator)
                    }
                }
            }
        }
    }

    @Cacheable(com.bionova.optimi.core.Constants.NMD_APPLICATION)
    Application getNmdApplication() {
        return getApplicationByApplicationId(ApplicationService.NMD_applicationId)
    }

    void clearCacheForNmdApplication() {
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.NMD_APPLICATION)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_NMD_FASE_ID_TO_OCLSTAGES)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_NMD_TOEPASSINGS_ID_TO_APPLICATION)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_NMD_MILLIEU_CATEGORY_ID_TO_OCLIMPACT_NAME)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_NMD_ELEMENT_TYPES)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_NMD_UNIT_TO_OCL)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_UPDATE_STRING_ON_IMPORT_NMD)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_KNOWN_NMD_UNITS)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_KNOWN_FASE_IDS)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_KNOWN_TOEPASSING_IDS)
        grailsCacheAdminService.clearCache(com.bionova.optimi.core.Constants.GET_KNOWN_MILIEU_CATEGORIE_IDS)
    }

    def importApplication(MultipartFile jsonFile) {
        // quite many NMD configs (stored in NMD application) are cache, hence need to clear them
        clearCacheForNmdApplication()
        List<Application> existingApplications = new ArrayList<Application>(Application.list())
        File tempFile = File.createTempFile("application", ".json")
        jsonFile.transferTo(tempFile)
        tempFile.text = FileUtil.getJSONArrayStringFromJSONFile(tempFile)
        def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
        def dbUsername = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbUsername")?.value
        def dbPassword = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbPassword")?.value
        String usernamePasswordStr = (dbUsername && dbPassword) ? " --username $dbUsername --password $dbPassword" : ""
        def cmdToExecute = "mongoimport --db $dbName $usernamePasswordStr --collection application --jsonArray --file $tempFile.path"
        loggerUtil.info(log, "=================== cmdToExecute: ${cmdToExecute}")
        flashService.setFadeInfoAlert("cmdToExecute: ${cmdToExecute}", true)
        def cmdResponse
        String importFile

        try {
            if (tempFile.text?.contains("–")) {
                throw new Exception("Illegal character found: '–', please replace U+2013 dashes with '-' U+002D hyphen / minus.")
            }

            cmdResponse = shellCmdService.executeShellCmd(cmdToExecute)
            loggerUtil.info(log, "=================== cmdResponse: ${cmdResponse}")
            flashService.setFadeInfoAlert("cmdToExecute: ${cmdToExecute}", true)
            List<Application> newApplications = new ArrayList<Application>(Application.list())

            if (existingApplications) {
                newApplications.removeAll({ Application app ->
                    existingApplications.collect({
                        it.id
                    }).contains(app.id)
                })
            }
            def createDate = new Date()
            def dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS")
            String importTime = dateFormat.format(createDate)
            importFile = jsonFile.getOriginalFilename()

            newApplications.each { Application application ->
                application.importTime = importTime
                application.importFile = importFile
                cmdResponse = "${cmdResponse}, ApplicationId: ${application.applicationId}"

                if (application.indicatorCoreSettings || application.indicatorReportGeneration) {
                    loadApplicationDefaultsForIndicators(application.applicationId, application.indicatorCoreSettings, application.indicatorReportGeneration)
                }

                if (application.defaultFilters) {
                    loadApplicationFiltersForQueries(application.applicationId, application.defaultFilters)
                }

                if (application.validate()) {
                    application.defaultValues?.each { DefaultValue defaultValue ->
                        defaultValue.defaultValueSets?.each { DefaultValueSet defaultValueSet ->
                            defaultValueSet.applicationId = application.applicationId
                            defaultValueSet.defaultValueId = defaultValue.defaultValueId
                        }
                    }
                    application = application.save(flush: true, failOnError: true)
                    cmdResponse = "${cmdResponse}, ObjectId: ${application.id}"
                    def sameApplications = existingApplications?.findAll({ Application app -> app.applicationId.equals(application.applicationId) })

                    sameApplications?.each { Application app ->
                        app.delete(flush: true)
                    }
                } else {
                    String errors = ""

                    application.getErrors()?.getFieldErrors()?.each { FieldError fieldError ->
                        errors = "${errors} ${messageSource.getMessage("application.${fieldError.getField()}.${fieldError.getCode()}", null, LocaleContextHolder.getLocale())}"
                    }
                    loggerUtil.info(log, "Application errors: ${application.errors.allErrors}")
                    flashService.setErrorAlert("Application errors: ${application.errors.allErrors}", true)
                    cmdResponse = "Error in importing application: ${application.errors}"
                    application.delete(flush: true)
                }
            }

            List<ImportMapper> importMappers = getAllApplicationImportMappers()

            if (importMappers) {
                List<String> licensedApiImports = importMappers.collect({ it.securityTokens?.values()?.toList() })?.flatten()

                if (licensedApiImports) {
                    licensedApiImports.unique().each { String licensedSoftware ->
                        if (licensedSoftware) {
                            Feature licensedSoftwareImport = featureService.getFeatureByFeatureId("import${licensedSoftware}")

                            if (!licensedSoftwareImport) {
                                licensedSoftwareImport = new Feature()
                                licensedSoftwareImport.featureId = "import${licensedSoftware}"
                                licensedSoftwareImport.featureClass = com.bionova.optimi.core.Constants.LicenseFeatureClass.BUSINESS.toString()
                                licensedSoftwareImport.name = "Import from ${licensedSoftware}"
                                featureService.saveFeature(licensedSoftwareImport)
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            def mongo = new GMongo()
            def db = mongo.getDB(dbName)
            db.application.remove([importTime: null])
            def errorMessage = e.message
            loggerUtil.error(log, "Error in importing application: " + errorMessage)
            flashService.setErrorAlert("Error in importing application: " + errorMessage, true)
            cmdResponse = "Probably you imported wrong type of json file. Errors is: " + errorMessage
        }
        tempFile.delete()
        return "File name: ${importFile}. " + cmdResponse
    }

    List<Application> getAllApplicationsThatHaveImportMappers() {
        return Application.findAllByImportMappersIsNotNull()
    }

    List<Application> getAllApplicationsThatHaveImportMappersAsJSON() {
        return Application.collection.find([importMappers: [$ne: null]])?.toList()
    }

    List<ImportMapper> getAllApplicationImportMappers() {
        List<ImportMapper> importMappers = []

        getAllApplicationsThatHaveImportMappers()?.each {
            importMappers.addAll(it.importMappers)
        }
        return importMappers
    }

    Boolean addWarning(String applicationId, String warning, String matchAllVariants = null) {
        List<String> warnings = []
        if (applicationId && warning) {
            if (matchAllVariants) {
                warning = warning + "*"
            }
            Application application = getApplicationByApplicationId(applicationId)
            if (application) {
                if (application.importMapperWarns && !application.importMapperWarns.isEmpty()) {
                    if (application.importMapperWarns*.toLowerCase().contains(warning.toLowerCase())) {
                            return Boolean.FALSE
                        } else {
                            application.importMapperWarns.add(warning)
                            application.merge(flush: true, failOnError: true)
                            return Boolean.TRUE
                        }
                } else {
                    warnings.add(warning)
                    application.importMapperWarns = warnings
                    application.merge(flush: true, failOnError: true)
                    return Boolean.TRUE
                }
            } else {
                return Boolean.FALSE
            }
        } else {
            return Boolean.FALSE
        }
    }

    Boolean addDiscard(String applicationId, String discard, String matchAllVariants = null) {
        List<String> discards = []
        if (applicationId && discard) {
            if (matchAllVariants) {
                discard = discard + "*"
            }
            Application application = getApplicationByApplicationId(applicationId)
            if (application) {
                if (application.importMapperDiscards && !application.importMapperDiscards.isEmpty()) {
                    if (application.importMapperDiscards*.toLowerCase().contains(discard.toLowerCase())) {
                            return Boolean.FALSE
                        } else {
                            application.importMapperDiscards.add(discard)
                            application.merge(flush: true, failOnError: true)
                            return Boolean.TRUE
                        }
                } else {
                    discards.add(discard)
                    application.importMapperDiscards = discards
                    application.merge(flush: true, failOnError: true)
                    return Boolean.TRUE
                }
            } else {
                return Boolean.FALSE
            }
        } else {
            return Boolean.FALSE
        }
    }

    Boolean removeWarning(String applicationId, String warning) {
        if (applicationId && warning) {
            Application application = getApplicationByApplicationId(applicationId)
            if (application) {
                application.importMapperWarns.remove(warning)
                application.merge(flush: true, failOnError: true)
                return Boolean.TRUE
            } else {
                return Boolean.FALSE
            }
        } else {
            return Boolean.FALSE
        }
    }

    Boolean removeDiscard(String applicationId, String discard) {
        if (applicationId && discard) {
            Application application = getApplicationByApplicationId(applicationId)
            if (application) {
                application.importMapperDiscards.remove(discard)
                application.merge(flush: true, failOnError: true)
                return Boolean.TRUE
            } else {
                return Boolean.FALSE
            }
        } else {
            return Boolean.FALSE
        }
    }

    Map<String, Double> getApplicationDiscountFactor(Application discountScenarioSourceApplication , String scenarioId) {
        Map<String, Double> scenarioDataSeries = [:]

        if(discountScenarioSourceApplication){
            scenarioDataSeries = discountScenarioSourceApplication.discountScenarios?.find{
                scenarioId.equals(it?.discountScenarioId)
            }?.scenarioDataSeries
        }

        return scenarioDataSeries
    }

    List<ApplicationResourceExtraInformationSet> getExtraInformationSetObjects(List resourceExtraInformationSets) {
        List<ApplicationResourceExtraInformationSet> applicationResourceExtraInformationSets = []

        resourceExtraInformationSets?.each {
            applicationResourceExtraInformationSets.add(it as ApplicationResourceExtraInformationSet)
        }
        return applicationResourceExtraInformationSets
    }

    List<ApplicationExpandFeatureSets> getExpandFeatureSetObjects(List expandFeatureSets) {
        List<ApplicationExpandFeatureSets> applicationExpandFeatureSets = []

        expandFeatureSets?.each {
            applicationExpandFeatureSets.add(it as ApplicationExpandFeatureSets)
        }
        return applicationExpandFeatureSets
    }

    ApplicationResourceExtraInformationSet getExtraInformationSetObjectsByName(String name, List resourceExtraInformationSets) {
        ApplicationResourceExtraInformationSet applicationResourceExtraInformationSet

        if (name) {
            List<ApplicationResourceExtraInformationSet> applicationResourceExtraInformationSets = getExtraInformationSetObjects(resourceExtraInformationSets)

            if (applicationResourceExtraInformationSets) {
                applicationResourceExtraInformationSets.each { ApplicationResourceExtraInformationSet set ->
                    if (set.name && name.contains(set?.name)) {
                        applicationResourceExtraInformationSet = set
                    }

                }

            }

        }
        return applicationResourceExtraInformationSet
    }

    IndicatorExpandFeature getExpandFeatureByName(String name, List expandFeatureSets) {
        IndicatorExpandFeature expandFeature

        if (name && expandFeatureSets) {
            List<ApplicationExpandFeatureSets> applicationExpandFeatureSets = getExpandFeatureSetObjects(expandFeatureSets)

            if (applicationExpandFeatureSets) {
                applicationExpandFeatureSets.each { ApplicationExpandFeatureSets set ->

                    if (name.contains(set?.name)) {
                        expandFeature = set.expandFeatures
                    }

                }
            }
        }
        return expandFeature
    }

    List<User> getApplicationEditors(List<ObjectId> applicationEditorIds) {
        List<User> editors

        if (applicationEditorIds) {
            editors = User.getAll(DomainObjectUtil.stringsToObjectIds(applicationEditorIds))
        }
        return editors
    }

    boolean getEditable(List<ObjectId> applicationEditorIds) {
        User currentUser = userService.getCurrentUser(Boolean.FALSE)
        boolean editable = false

        if (userService.isSystemAdmin(currentUser) || (applicationEditorIds && applicationEditorIds.contains(currentUser.id))) {
            editable = true
        }
        return editable
    }

    long getResourceCount(String applicationId) {
        return getApplicationResourcesCount(applicationId)
    }
}
