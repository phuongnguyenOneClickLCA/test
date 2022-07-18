/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as CoreConstants
import com.bionova.optimi.core.domain.mongo.AppendQuestionIdToDisplayResult
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.ApplicationIndicatorDefinitions
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExpandFeature
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.FileUtil
import com.gmongo.GMongo
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.apache.commons.collections.CollectionUtils
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class IndicatorService extends GormCleanerService {

    def shellCmdService
    def loggerUtil
    def configurationService
    def optimiSecurityService
    def applicationService
    def flashService
    def localizedLinkService
    def messageSource
    def calculationRuleService
    def denominatorUtil
    def indicatorService
    def entityService
    def licenseService
    def queryService
    def optimiResourceService
    def resultFormattingResolver
    def userService
    def datasetService

    //TODO: it is not thread safe
    private static boolean indicatorsUpdated = false
    //TODO: it is not thread safe
    private static List<Indicator> indicators

    /**
     * Provides indicators for entity by id
     * @param entityId
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByEntity(def entityId) {
        List<Indicator> indicators = null

        if (entityId) {
            Entity entity = Entity.get(entityId)
            indicators = getIndicatorsByEntityObject(entity)
        }

        return indicators
    }

    /**
     * Provides indicators for entity
     * @param entity
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByEntityObject(Entity entity) {

        List<Indicator> indicators = null
        if (!entity) {
            return indicators
        }

        List<String> indicatorIds = entity.indicatorIds
        if (indicatorIds) {
            indicators = getIndicatorsByIndicatorIds(indicatorIds, true)

            if (indicators) {
                indicators = getIndicatorsByEntityTypeAndClass(indicators, entity)
            }
        }

        return indicators
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getIndicatorsByIndicatorIdsAndEntityTypeAndClass(List<String> indicatorIds, String entityType, String entityClass) {
        List<Indicator> indicators = []

        if (indicatorIds && entityClass) {
            try {
                indicators = getIndicatorsByIndicatorIds(indicatorIds, true)

                if (indicators) {
                    indicators = getIndicatorsByEntityTypeAndClass(indicators, null, entityType, entityClass)
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in getIndicatorsByIndicatorIdsAndEntityTypeAndClass", e)
                flashService.setErrorAlert("Error occurred in getIndicatorsByIndicatorIdsAndEntityTypeAndClass: ${e.getMessage()}", true)
            }

        }
        return indicators
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getIndicatorsByEntityAndIndicatorUse(entityId, String indicatorUse) {
        List<Indicator> indicators = null

        if (entityId && indicatorUse) {
            try {
                indicators = getIndicatorsByEntity(entityId)

                if (indicators) {
                    indicators = filterListIndicatorsByIndicatorUse(indicators, indicatorUse)
                }
            } catch (Exception e) {
                loggerUtil.warn(log, "Error in setting indicators for entity", e)
                flashService.setErrorAlert("Error in setting indicators for entity: ${e.getMessage()}", true)
            }
        }
        return indicators
    }

    def getIndicatorsWithSameIndicatorQueries(Entity entity, String savedQueryId) {
        List<Indicator> indicatorsWithSameQuery = []

        if (entity) {
            List<Indicator> indicators

            if (entity.parentEntityId) {
                indicators = getIndicatorsByEntity(entity.parentEntityId)
            } else {
                indicators = getIndicatorsByEntity(entity.id)
            }

            if (indicators) {
                for (Indicator indicator in indicators) {
                    if ("complex" == indicator.assessmentMethod && indicator.indicatorQueries) {
                        def queryIds = indicator.indicatorQueries.collect({it.queryId})

                        if (savedQueryId && queryIds?.contains(savedQueryId)) {
                            indicatorsWithSameQuery.add(indicator)
                        }
                    }
                }
            }
        }
        return indicatorsWithSameQuery
    }

    List<Indicator> getIndicatorsWithSameQuery(Entity entity, String savedQueryId) {
        List<Indicator> indicatorsWithSameQuery = []

        if (entity) {
            List<Indicator> indicators

            if (entity.parentEntityId) {
                indicators = getIndicatorsByEntity(entity.parentEntityId)
            } else {
                indicators = getIndicatorsByEntity(entity.id)
            }
            
            if (indicators) {
                for (Indicator indicator in indicators) {
                    if ("complex" == indicator.assessmentMethod) {
                        def queryIds = []

                        indicator.getQueries(entity)?.each { Query query ->
                            queryIds.add(query.queryId)
                        }

                        if (savedQueryId && queryIds?.contains(savedQueryId)) {
                            indicatorsWithSameQuery.add(indicator)
                        }
                    }
                }
            }
        }

        return indicatorsWithSameQuery
    }
    def getIndicatorsWithSameQueries(Entity entity, List<String> savedQueryIdsList) {
        List<Indicator> indicatorsWithSameQuery = []

        if (entity) {
            List<Indicator> indicators

            if (entity.parentEntityId) {
                Entity parent = entity.getParentById()

                if (parent) {
                    indicators = parent.indicators
                }
            } else {
                indicators = entity.indicators
            }

            if (indicators) {
                for (Indicator indicator in indicators) {
                    if ("complex" == indicator.assessmentMethod) {
                        def queryIds = []

                        indicator.getQueries(entity)?.each { Query query ->
                            queryIds.add(query.queryId)
                        }

                        if (savedQueryIdsList && CollectionUtils.containsAny(queryIds, savedQueryIdsList)) {
                            indicatorsWithSameQuery.add(indicator)
                        }
                    }
                }
            }
        }
        return indicatorsWithSameQuery
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByEntityTypeAndClass(List<Indicator> indicators, Entity entity, String entityType = null, String entityClass = null) {
        List<Indicator> resolvedIndicators = []
        if (entity?.parentEntityId) {
            Entity parent = entity.getParentById()

            if (parent) {
                String typeResourceId = parent.getTypeResourceId()
                indicators?.each { Indicator indicator ->
                    if (indicator.compatibleEntityTypes && indicator.compatibleEntityClasses) {
                        if (typeResourceId && indicator.compatibleEntityTypes.contains(typeResourceId) &&
                                indicator.compatibleEntityClasses.contains(parent.entityClass)) {
                            resolvedIndicators.add(indicator)
                        }
                    } else if (indicator.compatibleEntityTypes) {
                        if (typeResourceId && indicator.compatibleEntityTypes.contains(typeResourceId)) {
                            resolvedIndicators.add(indicator)
                        }
                    } else if (indicator.compatibleEntityClasses) {
                        if (indicator.compatibleEntityClasses.contains(parent.entityClass))
                            resolvedIndicators.add(indicator)
                    } else {
                        resolvedIndicators.add(indicator)
                    }
                }
            }
        } else if (entity) {
            String typeResourceId = entity.getTypeResourceId()
            indicators?.each { Indicator indicator ->
                if (indicator.compatibleEntityTypes && indicator.compatibleEntityClasses) {
                    if (typeResourceId && indicator.compatibleEntityTypes.contains(typeResourceId) &&
                            indicator.compatibleEntityClasses.contains(entity.entityClass)) {
                        resolvedIndicators.add(indicator)
                    }
                } else if (indicator.compatibleEntityTypes) {
                    if (typeResourceId && indicator.compatibleEntityTypes.contains(typeResourceId)) {
                        resolvedIndicators.add(indicator)
                    }
                } else if (indicator.compatibleEntityClasses) {
                    if (indicator.compatibleEntityClasses.contains(entity.entityClass))
                        resolvedIndicators.add(indicator)
                } else {
                    resolvedIndicators.add(indicator)
                }
            }
        } else if (entityClass) {
            indicators?.each { Indicator indicator ->
                if (indicator.compatibleEntityTypes && indicator.compatibleEntityClasses) {
                    if (indicator.compatibleEntityTypes.contains(entityType) &&
                            indicator.compatibleEntityClasses.contains(entityClass)) {
                        resolvedIndicators.add(indicator)
                    }
                } else if (indicator.compatibleEntityTypes) {
                    if (indicator.compatibleEntityTypes.contains(entityType)) {
                        resolvedIndicators.add(indicator)
                    }
                } else if (indicator.compatibleEntityClasses) {
                    if (indicator.compatibleEntityClasses.contains(entityClass))
                        resolvedIndicators.add(indicator)
                } else {
                    resolvedIndicators.add(indicator)
                }
            }
        }
        return resolvedIndicators
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getIndicatorsByEntityClassAndIndicatorUse(String entityClass, String indicatorUse, List<String> indicatorIds = []) {
        def resolvedIndicators = []

        if (entityClass && indicatorUse) {
            List<Indicator> indicators = indicatorIds ? getIndicatorsByIndicatorIds(indicatorIds, true) : getAllActiveIndicators()
            resolvedIndicators = indicators?.findAll({ Indicator indicator ->
                (!indicator.compatibleEntityClasses || indicator.compatibleEntityClasses.contains(entityClass)) &&
                        (!indicator.indicatorUse || indicatorUse.equals(indicator.indicatorUse))
            })
        }
        return resolvedIndicators
    }

    Indicator getIndicatorWithoutCache(String indicatorId) {
        return Indicator.findByIndicatorIdAndActive(indicatorId, true)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Indicator getIndicatorByIndicatorId(String indicatorId, boolean active = true) {
        Indicator ind = null

        if (indicatorId) {
            if (active) {
                ind = getIndicator(indicatorId)
            } else {
                ind = getInactiveIndicators()?.find({ Indicator indicator -> indicator.indicatorId == indicatorId })
            }
        }
        return ind
    }

    def getIndicatorByIndicatorIdAndToken(String indicatorId, token, boolean active) {
        if (optimiSecurityService.tokenOk(token)) {
            if (indicatorId) {
                if (active) {
                    return getIndicator(indicatorId)
                } else {
                    return getInactiveIndicators()?.find({ Indicator indicator -> indicator.indicatorId == indicatorId })
                }
            }
        } else {
            throw new SecurityException("Trying to access without invalid token: " + token)
        }
    }


    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByIndicatorIds(List<String>indicatorIds, boolean active = true) {
        long now = System.currentTimeMillis()
        List<Indicator> indicators = []

        if (indicatorIds) {
            indicators = getIndicators(indicatorIds, active)
        }
        if(log.isTraceEnabled()) {
            log.trace("got  ${indicators?.size()} indicators by indicatorIds: ${System.currentTimeMillis() - now}")
        }
        return indicators
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @Cacheable('indicator')
    def getIndicatorById(id) {
        Indicator indicator

        if (id) {
            //TODO: 'id.equals(ind.id?.toString()) || id.equals(ind.id)' what is that?! Argument could be of any type??
            indicator = getIndicators(true)?.find({ Indicator ind -> id.equals(ind.id?.toString()) || id.equals(ind.id) })

            if (!indicator) {
                indicator = getInactiveIndicators()?.find({ Indicator ind -> id.equals(ind.id.toString()) || id.equals(ind.id) })
            }
        }
        return indicator
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllIndicators() {
        List<Indicator> allIndicators = []
        def activeIndicators = getIndicators(true)

        if (activeIndicators) {
            allIndicators.addAll(activeIndicators)
        }
        def inactiveIndicators = getInactiveIndicators()

        if (inactiveIndicators) {
            allIndicators.addAll(inactiveIndicators)
        }
        return allIndicators
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllActiveIndicators() {
        List<Indicator> indicators = getIndicators(true)

        if (indicators) {
            return new ArrayList<Indicator>(indicators)
        }
        return null
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllInactiveIndicators() {
        return Indicator.collection.find(getAllInactiveIndicatorsQuery())?.toList()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Long getCountOfAllInactiveIndicators() {
        return Indicator.collection.count(getAllInactiveIndicatorsQuery())
    }

    private Map getAllInactiveIndicatorsQuery() {
        [active: false]
    }

    /**
     * Provides indicators for entity which were selected for current user
     * @param entity
     * @param skipCompareIndicator - if it is true then Compare Indicator is excluded from the result list
     * otherwise Compare Indicator is added to the end of the list in case it is available for user
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getSelectedIndicatorsForCurrentUser(Entity entity, Boolean skipCompareIndicator = false) {

        List<Indicator> indicators = []

        if (!entity) {
            return indicators
        }

        indicators = getIndicatorsByEntityObject(entity)
        if (!indicators) {
            return indicators
        }

        Indicator compareIndicator = indicators.find { it.indicatorId == CoreConstants.COMPARE_INDICATORID }

        indicators = filterListIndicatorsByPossibleIndicatorUse(indicators)
        indicators = filterListIndicatorsForCurrentUser(indicators, entity)

        if (skipCompareIndicator) {
            if (compareIndicator) {
                indicators.remove(compareIndicator)
            }
        } else {
            Boolean isDesignIndicatorAvailableToCompareInList = indicators?.any { isDesignIndicatorAvailableToCompare(it) }

            if (isDesignIndicatorAvailableToCompareInList && entityService.isCompareAvailableForCurrentUser(entity, indicators)) {
                compareIndicator = compareIndicator ?: getIndicatorByIndicatorId(CoreConstants.COMPARE_INDICATORID)
                if (compareIndicator) {
                    indicators << compareIndicator
                }
            }
        }

        return indicators
    }

    /**
     * Filters indicators list by indicators selected by user
     * @param indicators
     * @param entity
     * @return
     */
    List<Indicator> filterListIndicatorsForCurrentUser(List<Indicator> indicators, Entity entity) {

        if (!indicators || !entity) {
            return []
        }

        List<Indicator> filteredIndicators = indicators

        User user = userService.getCurrentUser()

        List<String> userIndicatorIds = entity.userIndicators?.get(user?.id.toString())
        if (userIndicatorIds) {
            filteredIndicators = indicators.findAll { userIndicatorIds.contains(it.indicatorId) }
        }

        return filteredIndicators
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByIndicatorUse(indicatorUse, Entity entity, Boolean onlyLicensed) {
        // loggerUtil.debug(log, "Start of IndicatorService.getIndicatorsByIndicatorUse")

        List<Indicator> indicators

        if (onlyLicensed) {
            indicators = getLicensedIndicatorsForEntity(entity, Boolean.FALSE, Boolean.TRUE)
        } else {
            indicators = getIndicators(true)
        }
        indicators = filterListIndicatorsByIndicatorUse(indicators, indicatorUse)
        return getIndicatorsByEntityTypeAndClass(indicators, entity)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Indicator> getIndicatorsByIndicatorUse(List<Indicator> indicators, Entity entity, String indicatorUse) {
        indicators = filterListIndicatorsByIndicatorUse(indicators, indicatorUse)
        return getIndicatorsByEntityTypeAndClass(indicators, entity)
    }

    /**
     * Filters indicators list by indicatorUse. Compare indicator is excluded.
     * @param indicators
     * @param indicatorUse
     * @return
     */
    List<Indicator> filterListIndicatorsByIndicatorUse(List<Indicator> indicators, String indicatorUse) {
        return indicators?.findAll{ Indicator indicator -> indicator.indicatorUse == indicatorUse && !isMaterialSpecifierVisibilityStatus(indicator) }
    }

    /**
     * Filters indicators list by all possible indicatorUse (design", "operating"). Compare indicator is excluded.
     * @param indicators
     * @return
     */
    List<Indicator> filterListIndicatorsByPossibleIndicatorUse(List<Indicator> indicators) {
        return filterListIndicatorsByIndicatorUseList(indicators, Constants.IndicatorUse.values()*.toString())
    }

    /**
     * Filters indicators list by list of indicatorUse. Compare indicator is excluded.
     * @param indicators
     * @param indicatorUseList
     * @return
     */
    List<Indicator> filterListIndicatorsByIndicatorUseList(List<Indicator> indicators, List<String> indicatorUseList) {
        return indicators?.findAll{ Indicator indicator -> indicatorUseList?.contains(indicator.indicatorUse) && !isMaterialSpecifierVisibilityStatus(indicator) }
    }

    private Boolean isMaterialSpecifierVisibilityStatus(Indicator indicator) {
        "materialSpecifier".equals(indicator.visibilityStatus)
    }

    private Boolean isDesignIndicatorAvailableToCompare(Indicator indicator) {
        indicator.indicatorUse == Constants.IndicatorUse.DESIGN.toString() && !isMaterialSpecifierVisibilityStatus(indicator)
    }

    /**
     *  Return a map with queries and sections from result categories
     * @param indicator
     * @param resultCategories
     * @param allResultCategories
     * @return <queryid, list of sections>
     */
    Map<String, List<String>> getQueriesAndSectionsFromResultCategories(Indicator indicator, List<ResultCategory> resultCategories, List<ResultCategory> allResultCategories) {
        Map<String, List<String>> allowedQueriesAndSections = [:]

        if (indicator && resultCategories) {
            resultCategories.each { ResultCategory resultCategory ->
                if (resultCategory.data) {
                    resultCategory.data.each { String queryId, Object sectionIds ->
                        List<String> existing = allowedQueriesAndSections.get(queryId)

                        if (existing) {
                            if (sectionIds) {
                                if (sectionIds instanceof List) {
                                    existing.addAll(sectionIds)
                                } else if (sectionIds instanceof String) {
                                    existing.add(sectionIds)
                                }
                            }
                        } else {
                            if (sectionIds) {
                                if (sectionIds instanceof List) {
                                    existing = sectionIds
                                } else if (sectionIds instanceof String) {
                                    existing = [sectionIds]
                                }
                            }
                        }
                        allowedQueriesAndSections.put(queryId, existing)
                    }
                } else if (resultCategory.virtual) {
                    resultCategory.virtual.each { String virtualCategoryId ->
                        ResultCategory virtualResultCategory = allResultCategories?.find({ virtualCategoryId.equals(it.resultCategoryId) })

                        if (virtualResultCategory && virtualResultCategory.data) {
                            virtualResultCategory.data.each { String queryId, Object sectionIds ->
                                List<String> existing = allowedQueriesAndSections.get(queryId)

                                if (existing) {
                                    if (sectionIds) {
                                        if (sectionIds instanceof List) {
                                            existing.addAll(sectionIds)
                                        } else if (sectionIds instanceof String) {
                                            existing.add(sectionIds)
                                        }
                                    }
                                } else {
                                    if (sectionIds) {
                                        if (sectionIds instanceof List) {
                                            existing = sectionIds
                                        } else if (sectionIds instanceof String) {
                                            existing = [sectionIds]
                                        }
                                    }
                                }
                                allowedQueriesAndSections.put(queryId, existing)
                            }
                        }
                    }
                }
            }
        }
        return allowedQueriesAndSections
    }

    @Secured(["ROLE_SYSTEM_AUTHENTICATED"])
    @CacheEvict(value = 'indicator', allEntries = true)
    def saveIndicator(Indicator indicator) {
        if (indicator) {
            def activeIndicators = getAllIndicators()?.findAll({ i -> i.indicatorId == indicator.indicatorId && i.id != indicator.id && i.active })

            if (activeIndicators && indicator.active) {
                indicator.errors.reject("indicator.duplicate_active", "There can be only one active indicator with same indicatorId")
            } else {
                indicatorsUpdated = true
                indicator.save(flush: true, failOnError: true)
            }
        }
        return indicator
    }

    //TODO: unify with #saveIndicator() as #merge() works the same as #save()
    @CacheEvict(value = 'indicator', allEntries = true)
    def mergeIndicator(Indicator indicator) {
        if (indicator) {
            indicator = indicator.merge(flush: true, failOnError: true)
            indicatorsUpdated = true
        }
        return indicator
    }

    @Secured(["ROLE_SYSTEM_AUTHENTICATED"])
    def getIndicatorsByEntityClasses(List<String> entityClasses) {
        List<Indicator> indicators = getAllActiveIndicators()
        List<Indicator> compatibleIndicators = []

        if (entityClasses) {
            indicators?.each { Indicator indicator ->
                if (indicator.compatibleEntityClasses) {
                    if (!CollectionUtils.intersection(indicator.compatibleEntityClasses, entityClasses).isEmpty()) {
                        compatibleIndicators.add(indicator)
                    }
                } else {
                    compatibleIndicators.add(indicator)
                }
            }
        } else {
            compatibleIndicators = indicators
        }
        return compatibleIndicators
    }

    private void loadIndicatorDefaultsFromApplication(Indicator indicator) {
            Map<String, String> indicatorDefaults = indicator?.indicatorDefaults
            Application application = applicationService.getApplicationByApplicationId(indicatorDefaults?.get("applicationId"))

            if (application) {
                ApplicationIndicatorDefinitions applicationIndicatorDefinitions = application.indicatorCoreSettings?.find({ indicatorDefaults.get("coreSettings")?.equals(it.indicatorSettingsId) })
                IndicatorExpandFeature expandFeature = application.indicatorReportGeneration?.find({ indicatorDefaults.get("reportGeneration")?.equals(it.expandFeaturesId) })

                if (applicationIndicatorDefinitions) {
                    applicationIndicatorDefinitions.properties.each { String propertyName, Object propertyValue ->
                        if (propertyValue != null) {
                            try {
                                DomainObjectUtil.callSetterByAttributeName(propertyName, indicator, propertyValue, Boolean.TRUE)
                            } catch (NoSuchMethodException e) {
                                // Just some applicationIndicatorDefinition attributes that cannot be set for indicator, carry on
                            }
                        }
                    }
                }

                if (expandFeature) {
                    indicator.expandFeatures = expandFeature
                }
            }

    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'indicator', allEntries = true)
    def importIndicator(MultipartFile jsonFile) {
        List<Indicator> existingIndicators = new ArrayList<Indicator>(Indicator.list())
        File tempFile = File.createTempFile("indicator", ".json")
        jsonFile.transferTo(tempFile)
        tempFile.text = FileUtil.getJSONArrayStringFromJSONFile(tempFile)
        def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
        def dbUsername = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbUsername")?.value
        def dbPassword = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbPassword")?.value
        def cmdResponse
        def importFile

        try {
            if (tempFile.text?.contains("–")) {
                throw new Exception("Illegal character found: '–', please replace U+2013 dashes with '-' U+002D hyphen / minus.")
            }

            String usernamePasswordStr = (dbUsername && dbPassword) ? " --username $dbUsername --password $dbPassword" : ""
            def cmdToExecute = "mongoimport --db $dbName $usernamePasswordStr --collection indicator --jsonArray --file $tempFile.path"
            cmdResponse = shellCmdService.executeShellCmd(cmdToExecute)
            List<Indicator> newIndicators = new ArrayList<Indicator>(Indicator.list())

            if (existingIndicators) {
                newIndicators.removeAll(existingIndicators)
            }
            def createDate = new Date()
            def dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS")
            def importTime = dateFormat.format(createDate)
            importFile = jsonFile.getOriginalFilename()

            newIndicators.each { Indicator indicator ->
                log.debug("Importing an indicator: ${indicator.indicatorId}")

                indicator.importTime = importTime
                indicator.importFile = importFile
                indicator.active = true
                cmdResponse = "${cmdResponse}, IndicatorId: ${indicator.indicatorId}"

                if (indicator.indicatorDefaults) {
                    loadIndicatorDefaultsFromApplication(indicator)
                }

                if (indicator.validate()) {
                    indicator = indicator.save(flush: true, failOnError: true)
                    cmdResponse = "${cmdResponse}, ObjectId: ${indicator.id}"

                    def sameIndicators = existingIndicators?.findAll({ ei -> ei.indicatorId.equals(indicator.indicatorId) })
                    sameIndicators?.each { si ->
                        si.active = false
                        si.save(failOnError: true, flush: true)
                    }
                } else {
                    cmdResponse = "Probably you imported wrong type of json file (query as indicator etc.): ${indicator.getErrors()}"
                    indicator.delete(flush: true)
                }
            }
            indicatorsUpdated = true
        } catch (Exception e) {
            def mongo = new GMongo()
            def db = mongo.getDB(dbName)
            db.indicator.remove([importTime: null])
            def errorMessage

            if (e instanceof ValidationException) {
                errorMessage = e.errors
            } else {
                errorMessage = e.message
            }
            loggerUtil.warn(log, "Error in importing indicators: " + errorMessage)
            flashService.setErrorAlert("Error in importing indicators: " + errorMessage, true)
            cmdResponse = "Probably you imported wrong type of json file. Errors is: " + errorMessage
        }
        tempFile.delete()
        return "File name: ${importFile}. " + cmdResponse
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'indicator', allEntries = true)
    def deleteIndicator(Indicator indicator) {
        if (indicator) {
            List<LicenseTemplate> templatesWithRemovedIndicator = LicenseTemplate.collection.find([licensedIndicatorIds: indicator.indicatorId])?.collect({ it as LicenseTemplate })
            if (templatesWithRemovedIndicator) {
                templatesWithRemovedIndicator.each { LicenseTemplate licenseTemplate ->
                    licenseTemplate.licensedIndicatorIds.removeAll(indicator.indicatorId)
                    licenseTemplate.save(flush: true)
                }
            }
            indicator.delete(flush: true, failOnError: true)
            indicatorsUpdated = true
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'indicator', allEntries = true)
    def deleteIndicatorById(String id) {
        if (id) {
            Indicator indicator = getAllIndicators()?.find({ DomainObjectUtil.stringToObjectId(id).equals(it.id) })
            removeIndicator(indicator)
        }
    }

    //TODO: refactor, as it also removes indicator like #deleteIndicator() but has a different logic
    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'indicator', allEntries = true)
    protected def removeIndicator(Indicator indicator) {
        if (indicator) {
            // Remove from licensetemplates only if indicator is active or inactive and no active one found
            if (indicator.active || (!indicator.active && !getIndicators(true)?.find({ indicator.indicatorId?.equals(it.indicatorId) }))) {
                List<LicenseTemplate> templatesWithRemovedIndicator = LicenseTemplate.collection.find([licensedIndicatorIds: indicator.indicatorId])?.collect({ it as LicenseTemplate })
                if (templatesWithRemovedIndicator) {
                    templatesWithRemovedIndicator.each { LicenseTemplate licenseTemplate ->
                        licenseTemplate.licensedIndicatorIds.removeAll(indicator.indicatorId)
                        licenseTemplate.save(flush: true)
                    }
                }
            }
            indicator.delete(flush: true, failOnError: true)
            indicatorsUpdated = true
        }
    }


    private List<Indicator> getLicensedIndicatorsForEntity(Entity entity, Boolean alsoExpired = Boolean.FALSE, Boolean alsoDepricated = Boolean.FALSE) {
        List<Indicator> licensedIndicators

        if (entity) {
            if (alsoExpired) {
                List<License> expiredLicenses = licenseService.getExpiredLicensesForEntity(entity)?.findAll({ it.licensedIndicatorIds })

                if (expiredLicenses) {
                    licensedIndicators = getIndicatorsByIndicatorIds(
                            expiredLicenses.collect{ it.licensedIndicatorIds }?.flatten()
                    )
                }
            }
            List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)?.findAll({ it.licensedIndicatorIds })

            if (validLicenses) {
                licensedIndicators = getIndicatorsByIndicatorIds(
                        validLicenses.collect{ it.licensedIndicatorIds}?.flatten()
                )
            }
        }

        return filterLicensedIndicators(licensedIndicators, alsoDepricated)
    }

    List<Indicator> getValidLicensedIndicatorsForEntity(Entity entity, List<License> validLicenses, Boolean alsoDepricated = Boolean.FALSE) {
        List<Indicator> licensedIndicators = getIndicatorsByIndicatorIds(validLicenses.findResults{ it.licensedIndicatorIds }?.flatten())
        return filterLicensedIndicators(licensedIndicators, alsoDepricated)
    }

    private List<Indicator> filterLicensedIndicators(List<Indicator> indicators, boolean alsoDepricated){
        if (indicators) {
            if (alsoDepricated) {
                return indicators.unique{ it.indicatorId }
            } else {
                return indicators.findAll{ Indicator indicator -> !indicator.deprecated }
                        ?.unique({
                            it.indicatorId
                        })
            }
        }
    }

    @Cacheable('indicator')
    Indicator getIndicator(String indicatorId) {
        return Indicator.findByIndicatorIdAndActive(indicatorId, true, [cache: true])
    }

    @Cacheable('indicator')
    List<Indicator> getIndicators(List<String> indicatorIds, Boolean active = true) {
        ArrayList<Indicator> result = new ArrayList<>()
        Indicator.withCriteria {
            and {
                'in'('indicatorId', indicatorIds)
                eq('active', active)
            }
            arguments([hint: ['indicatorId': 1]])
        }.each {
            result.add(it)
        }
        return result
    }

    /*
        This method is not thread safe. It is not right to use static fields to cache data within a singleton.
        According to grails mongo plugin documentation, it has an implicit cache for repeatable reads.
     */
    @Deprecated
    List<Indicator> getIndicators() {
        if (!indicators || isIndicatorsUpdated()) {
            try {
                List i = Indicator.findAllByActive(true).sort({it.indicatorId})

                if (i) {
                    indicators = new ArrayList<Indicator>(i)
                }
            } catch (Exception e) {
                log.trace("Failed to get indicators", e)
            }
        }
        return indicators
    }

    List<Indicator> getIndicators(Boolean active) {
        return Indicator.findAllByActive(active, [sort: 'indicatorId', order: 'asc', cache: true])
    }

    private List<Indicator> getInactiveIndicators() {
        List<Indicator> indicators = null

        try {
            indicators = Indicator.collection.find([active: false])?.sort([indicatorId: 1])?.collect({it as Indicator})
        } catch (Exception e) {
            log.error("Error while getting inactive indicators.", e)
        }
        return indicators
    }

    @Deprecated
    private boolean isIndicatorsUpdated() {
        if (indicatorsUpdated) {
            indicatorsUpdated = false
            return true
        } else {
            return false
        }
    }

    def getApiIndicatorIdForUser(User user, List<String> allowedFeatures = null) {
        String apiIndicatorId

        if (user) {
            List<Entity> parents = userService.getExportableParentEntities(user)
            List<String> allowedFeatureIds = allowedFeatures ?: [Feature.IFC_FROM_SIMPLE_BIM, Feature.API_SAMPLE_EXCEL]

            allowingProjectBasedEntityLoop:
            for (Entity parent in parents) {
                List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)

                for (License license in validLicenses?.findAll({ it.projectBased })) {
                    List<String> featureIds = license.licensedFeatures?.collect({ it?.featureId })

                    if (featureIds && CollectionUtils.containsAny(allowedFeatureIds, featureIds)) {
                        if (license.apiIndicatorId) {
                            log.info("API: ${user.username} Using API indicatorId ${license.apiIndicatorId} from license: ${license.name}")
                            flashService.setFadeInfoAlert("API: ${user.username} Using API indicatorId ${license.apiIndicatorId} from license: ${license.name}", true)
                            apiIndicatorId = license.apiIndicatorId
                            break allowingProjectBasedEntityLoop
                        }
                    }
                }
            }

            if (!apiIndicatorId) {
                List<License> userLicenses = userService.getLicenses(user)
                for (License license in userLicenses) {
                    List<String> featureIds = license.licensedFeatures?.collect({ it?.featureId })

                    if (featureIds && CollectionUtils.containsAny(allowedFeatureIds, featureIds)) {
                        if (license.apiIndicatorId) {
                            log.info("API: ${user.username} Using API indicatorId ${license.apiIndicatorId} from license: ${license.name}")
                            flashService.setFadeInfoAlert("API: ${user.username} Using API indicatorId ${license.apiIndicatorId} from license: ${license.name}", true)
                            apiIndicatorId = license.apiIndicatorId
                            break
                        }
                    }
                }
            }
        }
        return apiIndicatorId
    }

    Boolean addToCompareAllowedForIndicators(List<Indicator> indicatorList) {
        Boolean atLeastOneIndicatorAllowComparing = indicatorList?.any { !it.preventComparing }

        if (!atLeastOneIndicatorAllowComparing) {
            return false
        }

        List<String> queryIds = indicatorList?.collect({ it.indicatorQueries?.collect({ it.queryId }) })?.flatten()?.unique()
        if (queryIds && !queryIds.isEmpty()) {
            return Query.collection.count([queryId: [$in: queryIds], active: true, allowedFeatures: com.bionova.optimi.core.Constants.ADD_TO_COMPARE]) > 0
        } else {
            return Boolean.FALSE
        }
    }

    Boolean compareDataLicensedAndCompatible(Map<String, Boolean> featuresAllowed, Query query, Indicator indicator) {
        if (featuresAllowed && !featuresAllowed.isEmpty() && query && indicator) {
            return featuresAllowed?.get(Feature.COMPARE_MATERIAL) && query?.allowedFeatures?.contains(com.bionova.optimi.core.Constants.ADD_TO_COMPARE) && !indicator?.preventComparing
        } else {
            return Boolean.FALSE
        }
    }

    /**
     * Remove the benchmark tool if no other tools in list are compatible with benchmark tool
     * @param indicatorIds
     * @return
     */
    List<String> doFilterBenchmarkToolInIndicatorList(List<String> indicatorIds) {
        try {
            if (indicatorIds) {
                List<Indicator> indicators = getIndicatorsByIndicatorIds(indicatorIds)
                if (indicators) {
                    boolean isBenchmarkCompatible = indicators.find({ it.enableCarbonHeroesBenchmarks && !com.bionova.optimi.core.Constants.BENCHMARK_VISIBILITY_STATUS.contains(it.visibilityStatus) })
                    if (!isBenchmarkCompatible) {
                        List<String> toRemoveBenchmarkIndicatorIds = indicators.findAll({ com.bionova.optimi.core.Constants.BENCHMARK_VISIBILITY_STATUS.contains(it.visibilityStatus) })?.collect({ it.indicatorId })
                        if (toRemoveBenchmarkIndicatorIds?.size() > 0) {
                            indicatorIds = indicatorIds.findAll({ !toRemoveBenchmarkIndicatorIds.contains(it) })
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in doFilterBenchmarkToolInIndicatorList", e)
            flashService.setErrorAlert("Error in doFilterBenchmarkToolInIndicatorList: ${e.getMessage()}", true)
        }
        return indicatorIds
    }

    List<Indicator> getLicensedIndicatorsOfLicenses(List<License> licenses) {
        List<Indicator> inds = []
        if (licenses) {
            try {
                List<String> indicatorIds = licenses?.collect({it.licensedIndicatorIds})?.flatten()?.unique()
                if (indicatorIds) {
                    inds = getIndicatorsByIndicatorIds(indicatorIds)
                }
            } catch (e) {
                loggerUtil.error(log, "Error in getLicensedIndicatorsOfLicenses", e)
                flashService.setErrorAlert("Error in getLicensedIndicatorsOfLicenses: ${e.message}", true)
            }
        }
        return inds
    }

    /**
     * Put to a map all indicators that are compatible to same entity class to same key. 
     * If some classes that do not have indicators, map still has key with empty list
     * @param indicators
     * @param entityClasses
     * @return map <entityClass, list of indicators that are compatible with the class>
     */
    Map<String, List<Indicator>> getIndicatorListByEntityClassMap(List<Indicator> indicators, List<String> entityClasses) {
        Map<String, List<Indicator>> map = [:]
        if (indicators && entityClasses) {
            try {
                for (String entityClass in entityClasses) {
                    map.put(entityClass, indicators.findAll({it.compatibleEntityClasses?.contains(entityClass)})?.sort({indicatorService.getLocalizedName(it)}) ?: [])
                }
            } catch (e) {
                loggerUtil.error(log, "Error in getIndicatorListByEntityClass", e)
                flashService.setErrorAlert("Error in getIndicatorListByEntityClass: ${e.message}", true)
            }
        }
        return map
    }

    /**
     * Check if indicator has some query using ecoinvent data
     * if resourceFilterCriteria has "Ecoinvent36" in "enabledPurposes" list
     * @param indicator
     * @return
     */
    boolean isUsingEcoinventData(Indicator indicator) {
        boolean isUsing = false
        try {
            if (indicator?.indicatorQueries) {
                for (IndicatorQuery indicatorQuery in indicator.indicatorQueries) {
                    List<String> resourceEnabledPurpose = indicatorQuery?.resourceFilterCriteria?.get(IndicatorQuery.ENABLED_PURPOSES_FILTER) ?: []
                    if (resourceEnabledPurpose?.find({ Resource.ECOINVENT36.equalsIgnoreCase(it) })) {
                        isUsing = true
                        break
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in isUsingEcoinventData", e)
            flashService.setErrorAlert("Error in isUsingEcoinventData: ${e.message}", true)
        }
        return isUsing
    }

    List<CalculationRule> getCalculationRules(String indicatorId, Entity parentEntity) {
        if(indicatorId != null) {
            Indicator indicator = getIndicatorByIndicatorId(indicatorId, true)
            return getCalculationRules(indicator, parentEntity)
        }
        return Collections.emptyList()
    }

    List<CalculationRule> getCalculationRules(Indicator indicator, Entity parentEntity) {
        if (indicator) {
            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(parentEntity, true)
            return calculationRules
        }
        return Collections.emptyList()
    }

    def getDisplayUnit(Indicator indicator, Boolean doAlteration = true) {
        String displayUnit = "Undefined"
        if(!indicator) {
            return displayUnit
        }

        if (indicator.displayResult) {
            CalculationRule calcRule = indicator.getResolveCalculationRules(null, true)?.find{
                indicator.displayResult.equals(it.calculationRuleId)
            }
            displayUnit = calculationRuleService.getLocalizedUnit(calcRule, indicator, null, doAlteration)
        } else {
            def denom = indicator.getDisplayDenominator()

            if (denom) {
                displayUnit = denominatorUtil.getLocalizedUnit(denom)
            } else {
                displayUnit = "Undefined"
            }
        }
        return displayUnit
    }

    String getLocalizedText(IndicatorReportItem indicatorReportItem) {
        if (!indicatorReportItem) {
            return
        }
        String localizedText = indicatorReportItem.getLocalizedText()
        localizedText = localizedLinkService.getTransformStringIdsToLinks(localizedText)
        return localizedText ? localizedText : ""
    }

    String getLocalizedAltText(IndicatorReportItem indicatorReportItem) {
        if (!indicatorReportItem) {
            return
        }
        String localizedText = indicatorReportItem.getLocalizedAltText()
        localizedText = localizedLinkService.getTransformStringIdsToLinks(localizedText)
        return localizedText ? localizedText : ""
    }

    String getLocalizedDurationWarning() {
        Object[] args = []
        String warning = ""
        try {
            warning = messageSource.getMessage("results.expand.duration_warning", args, LocaleContextHolder.getLocale())
        } catch (Exception e) {
            log.trace("Exception while getting duration warning", e)
            warning = ""
        }

        return warning
    }

    String getLocalizedResultsHeadline(Indicator indicator) {
        if(!indicator) {
            return null
        }
        String localizedHeadline = indicator.getLocalizedResultsHeadline()
        localizedHeadline = localizedLinkService.getTransformStringIdsToLinks(localizedHeadline)
        return localizedHeadline
    }

    String getLocalizedPurpose(Indicator indicator) {
        if(!indicator) {
            return null
        }
        String localizedPurpose = indicator.getLocalizedPurpose()
        localizedPurpose = localizedLinkService.getTransformStringIdsToLinks(localizedPurpose)
        return localizedPurpose
    }

    String getLocalizedResultReportLinkText(Indicator indicator) {
        if(!indicator) {
            return null
        }
        String localizedResultReportLinkText = null

        if (indicator.resultReportLinkText) {
            localizedResultReportLinkText = indicator.getLocalizedResultReportLinkText()
            localizedResultReportLinkText = localizedLinkService.getTransformStringIdsToLinks(localizedResultReportLinkText)
        }
        return localizedResultReportLinkText
    }

    String getLocalizedRenameReportVisualisation(Indicator indicator, String chartId) {
        if(!indicator) {
            return null
        }
        String localizedChartName = null
        if (chartId) {
            Map<String, String> chartNames = indicator.renameReportVisualisation?.get(chartId)
            localizedChartName = chartNames?.get(getLanguage()) ?: chartNames?.get("EN")
        }
        return localizedChartName
    }

    String getLocalizedLicensedNameQualifier(Indicator indicator, String featureId) {
        if(!indicator) {
            return null
        }
        String localizedLicenseName = ""

        if (featureId && indicator.licensedNamequalifiers) {
            localizedLicenseName = indicator.licensedNamequalifiers.get(featureId)?.get(getLanguage()) ?:
                    indicator.licensedNamequalifiers.get(featureId)?.get("EN") ?: ""
        }
        return localizedLicenseName
    }

    String getLocalizedShortName(Indicator indicator) {
        if(!indicator) {
            return null
        }
        if (indicator.shortName) {
            return localizedLinkService.getTransformStringIdsToLinks(indicator.getLocalizedShortName())
        } else {
            return getLocalizedName(indicator)
        }
    }

    String getLocalizedName(Indicator indicator) {
        if(!indicator) {
            return null
        }
        String localizedName = indicator.getLocalizedName()
        localizedName = localizedLinkService.getTransformStringIdsToLinks(localizedName)
        return localizedName
    }

    private static String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage().toUpperCase()
    }

    String renderDisplayResult(Entity entity, Indicator indicator, List<Feature> featuresAvailableForEntity, List<String> queryIds,
                               Boolean useAppendValue, Boolean displayMainPage, Boolean noScientificNumbers, HttpSession session){
        StringBuilder result = new StringBuilder()

        if (entity && indicator) {
            def appendValue

            if (useAppendValue) {
                appendValue = getAppendValueToDisplayResult(indicator, entity)
            }

            List<Query> indicatorQueries = queryIds ? queryService.getQueriesByQueryIds(queryIds, true) :
                    queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity)

            indicatorQueries?.each { Query query ->
                if (entity.queryReadyPerIndicator?.get(indicator.indicatorId) == null) {
                    entity.resolveQueryReady(indicator, query)
                }
            }

            if (entity.isIndicatorReady(indicator, indicatorQueries?.queryId)) {
                if (indicator.nonNumericResult) {
                    if (appendValue) {
                        result.append(appendValue)
                    } else {
                        result.append(messageSource.getMessage('ready', null, LocaleContextHolder.getLocale()))

                    }
                } else {
                    Double displayResult = entity.getDisplayResult(indicator.indicatorId)
                    String formattedValue = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator,
                            indicator.getDisplayDenominator(), displayResult, session,
                            Boolean.TRUE, noScientificNumbers, Boolean.FALSE, "", null,
                            null, null, null, displayMainPage)

                    if (appendValue) {
                        if (formattedValue) {
                            formattedValue = "${formattedValue} ${appendValue}"
                        } else {
                            formattedValue = appendValue
                        }
                    }

                    if (!formattedValue || formattedValue.isEmpty()) {
                        result.append("${messageSource.getMessage('ready', null, LocaleContextHolder.getLocale())}")
                    } else {
                        result.append("${formattedValue}")
                    }
                }
            } else {
                result.append("<span style=\"color: #FF2D41;\">${messageSource.getMessage('data_missing', null, LocaleContextHolder.getLocale())}</span>")
            }
        }

        return result.toString()
    }

    private String getAppendValueToDisplayResult(Indicator indicator, Entity entity) {
        String appendValue
        AppendQuestionIdToDisplayResult appendQuestionIdToDisplayResult = indicator?.appendQuestionIdToDisplayResult

        if (appendQuestionIdToDisplayResult && entity?.datasets) {
            Dataset dataset = entity.datasets.find({ Dataset d ->
                d.queryId == appendQuestionIdToDisplayResult.queryId &&
                        d.sectionId == appendQuestionIdToDisplayResult.sectionId &&
                        d.questionId == appendQuestionIdToDisplayResult.questionId
            })

            if (dataset) {
                if (dataset.resourceId) {
                    appendValue = optimiResourceService.getLocalizedName(datasetService.getResource(dataset))
                } else {
                    appendValue = dataset.answerIds[0]
                }
            }
        }
        return appendValue
    }

    Map<String, Boolean> resolveFormattingFeatures(Indicator indicator, HttpSession session = null) {

        Map<String, Boolean> formattingFeatures = [
                'useScientificNumbers': indicator.useScientificNumbers != null,
                'showDecimals': indicator.showDecimals,
                'removeFormatting': null
        ]

        if (!session) {
            return formattingFeatures
        }

        def chosenFormatting = session.getAttribute(Constants.SessionAttribute.RESULT_FORMATTING.toString())
        String indicatorId = indicator.indicatorId

        if (chosenFormatting && ((Map) chosenFormatting).keySet().contains(indicatorId)) {
            def formatting = chosenFormatting.get(indicatorId)

            if ("showDecimals".equals(formatting)) {
                formattingFeatures.put(formatting, Boolean.TRUE)
            } else if ("useScientificNumbers".equals(formatting)) {
                formattingFeatures.put(formatting, Boolean.TRUE)
            }
        }

        return formattingFeatures
    }

    /**
     * Filters list of queries by project level indicator queries
     * @param indicator
     * @param queries
     * @param isProjectLevel - if it is true project level queries are returned, otherwise non project level queries are returned
     * @return
     */
    List<Query> filterQueriesByProjectLevelIndicatorQueries(Indicator indicator, List<Query> queries, Boolean isProjectLevel) {

        List<Query> filteredQueries = queries

        if (!queries) {
            return filteredQueries
        }

        if (!indicator?.indicatorQueries) {
            return []
        }

        Map<Boolean, List<IndicatorQuery>> indicatorQueriesByProjectLevel = indicator.indicatorQueries.groupBy { it.projectLevel.asBoolean() }

        List<String> allowedQueryIds = indicatorQueriesByProjectLevel.get(isProjectLevel)*.queryId

        filteredQueries = allowedQueryIds ? queries.findAll { allowedQueryIds.contains(it.queryId) } : []

        return filteredQueries
    }

    /**
     * Provides queryIds of project level queries for a list of indicators
     *
     * This method makes db calls to check whether queries exist if onlyExisting is true, think twice before use, or extend the method!!!
     *
     * @param indicatorList
     * @param onlyExisting - check whether queries exist
     * @return
     */
    List<String> getProjectLevelQueryIdsForIndicators(List<Indicator> indicatorList, Boolean onlyExisting = true) {

        List<String> projectLevelQueryIds = []

        if (!indicatorList) {
            return projectLevelQueryIds
        }

        Map<String, List<Indicator>> queryAndLinkedIndicators = groupIndicatorsByProjectLevelQueryIds(indicatorList, onlyExisting)
        projectLevelQueryIds = queryAndLinkedIndicators*.key

        projectLevelQueryIds?.sort { it.toLowerCase() } // Important to have LCA params before LCC params

        return projectLevelQueryIds
    }

    /**
     * Groups a list of indicators by Project Level QueryIds from indicatorQueries.
     *
     * This method makes db calls to check whether queries exist if onlyExisting is true, think twice before use, or extend the method!!!
     *
     * @param indicatorList
     * @param onlyExisting - check whether queries exist
     * @return map contains the Project Level QueryIds with lists of indicators that have such query.
     */
    Map<String, List<Indicator>> groupIndicatorsByProjectLevelQueryIds(List<Indicator> indicatorList, Boolean onlyExisting = true) {

        Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = [:]

        if (!indicatorList) {
            return indicatorsByProjectLevelQueryIds
        }

        indicatorList.each { Indicator indicator ->

            List<IndicatorQuery> projectLevelIndicatorQueries
            if (indicator.indicatorId == CoreConstants.COMPARE_INDICATORID) {
                projectLevelIndicatorQueries = [indicator.indicatorQueries?.find { it.projectLevel }]
            } else {
                projectLevelIndicatorQueries = indicator.indicatorQueries?.findAll { it.projectLevel }
            }

            projectLevelIndicatorQueries?.each { IndicatorQuery indicatorQuery ->

                String queryId = indicatorQuery.queryId

                def indicatorsForQuery = indicatorsByProjectLevelQueryIds.get(queryId) ?: []
                indicatorsForQuery << indicator
                indicatorsByProjectLevelQueryIds.put(queryId, indicatorsForQuery)
            }
        }

        if (onlyExisting && indicatorsByProjectLevelQueryIds) {
            indicatorsByProjectLevelQueryIds = filterIndicatorsByProjectLevelQueryIdsByExistingQueries(indicatorsByProjectLevelQueryIds)
        }

        return indicatorsByProjectLevelQueryIds
    }

    /**
     * Filter IndicatorsByProjectLevelQueryIds by existing queries
     * @param indicatorsByProjectLevelQueryIds
     * @return filtered indicatorsByProjectLevelQueryIds
     */
    private Map<String, List<Indicator>> filterIndicatorsByProjectLevelQueryIdsByExistingQueries(Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = [:]) {

        List<String> queryIds = indicatorsByProjectLevelQueryIds*.key
        List<String> existingQueryIds = queryService.getQueriesByQueryIds(queryIds)*.queryId

        if (!existingQueryIds?.containsAll(queryIds)) {
            List<String> nonexistentQueryIds = queryIds - existingQueryIds
            List<String> affectedIndicatorIds = nonexistentQueryIds.findResults {
                indicatorsByProjectLevelQueryIds.get(it)*.indicatorId
            }?.flatten()
            log.warn "Nonexistent indicator queries: ${nonexistentQueryIds}. Please check the following indicators: ${affectedIndicatorIds}"
        }

        if (!existingQueryIds) {
            return [:]
        }

        return indicatorsByProjectLevelQueryIds.findAll { it.key in existingQueryIds }
    }
}