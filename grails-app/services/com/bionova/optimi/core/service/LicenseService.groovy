/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FloatingLicenseUser
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseEntityChangeHistory
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.LicenseTier
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserLicenseInvitation
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.domainDTO.LicenseDTO
import com.bionova.optimi.util.DateUtil
import com.mongodb.BasicDBObject
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpSession
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDateTime

/**
 * @author Pasi-Markus Mäkelä
 */
class LicenseService {

    private static final Set<String> NO_LICENSE_FEATURES = [Feature.SEND_ME_DATA].asImmutable()

    def entityService
    def loggerUtil
    def userService
    def featureService
    def optimiMailService
    def optimiResourceService
    def indicatorService
    def activeCampaignService
    def flashService
    def channelFeatureService
    def projectTemplateService
    def accountService

    @Autowired
    EmailConfiguration emailConfiguration

    // static transactional = "mongo"

    def getLicensesExpiringInDays(Integer days) {
        List<License> licenses

        if (days) {
            Calendar calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, days.intValue())
            licenses = License.findAllByTypeAndValidUntilLessThanEqualsAndRenewalStatus(Constants.LicenseType.PRODUCTION.toString(),
                    calendar.getTime(), "fixedPeriod")
        }
        return licenses
    }

    void autoRenewLicensesJob() {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
        List<License> autoRenewedLicenses = []

        License.findAllByRenewalStatus("autoRenewal")?.findAll({
            it.getRenewLicense(calendar) == 0
        })?.each { License license ->
            license = license.merge(flush: true, failOnError: true)
            autoRenewedLicenses.add(license)
        }

        if (!autoRenewedLicenses.isEmpty()) {
            String htmlBody = ""

            autoRenewedLicenses.each { License l ->
                if (l) {
                    if (htmlBody) {
                        htmlBody = "${htmlBody}<a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/license/form/${l.id.toString()}\">${l.name}</a><br />"
                    } else {
                        htmlBody = "Autorenew renewed following licenses:<br /><a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/license/form/${l.id.toString()}\">${l.name}</a><br />"
                    }
                }
            }

            if (Environment.current == Environment.PRODUCTION) {
                optimiMailService.sendMail({
                    to emailConfiguration.salesEmail, emailConfiguration.supportEmail
                    from getEmailFrom()
                    replyTo emailConfiguration.contactEmail
                    subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()}License autorenewal notification"))
                    html htmlBody
                }, "${emailConfiguration.salesEmail}, ${emailConfiguration.supportEmail}")
            } else {
                log.info("HTML ${htmlBody}")
            }
        }
    }

    private String getEmailFrom() {
        return Environment.current == Environment.PRODUCTION ?
                emailConfiguration.contactEmail : emailConfiguration.devEmail
    }

    void expiredLicensesJob() {
        Calendar calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 10)
        List<Document> licenses = License.collection.find([renewalStatus: [$ne: "autoRenewal"], validUntil: [$lte: calendar.getTime()]])?.toList()

        if (licenses) {
            DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm")
            String htmlBody = ""

            licenses.each { Document l ->
                if (l) {
                    String contract = l.getString("contractNumber") ? "C${l.getString("contractNumber")} - " : ""
                    String client = l.getString("clientNumber") ? " / Client Number: ${l.getString("contractNumber")}" : ""
                    if (htmlBody) {
                        htmlBody = "${htmlBody}<a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/license/form/${l.get("_id").toString()}\">${contract}${l.getString("name")} / ${simpleDateFormat.format(l.getDate("validUntil"))}${client}</a><br />"
                    } else {
                        htmlBody = "<a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/license/form/${l.get("_id").toString()}\">${contract}${l.getString("name")} / ${simpleDateFormat.format(l.getDate("validUntil"))}${client}</a><br />"
                    }
                }
            }

            if (Environment.current == Environment.PRODUCTION) {
                optimiMailService.sendMail({
                    to emailConfiguration.userLicenceNotificationEmail
                    from getEmailFrom()
                    replyTo emailConfiguration.contactEmail
                    subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()} Licenses expiring in next 10 days"))
                    html htmlBody
                }, emailConfiguration.userLicenceNotificationEmail)
            }
        }
    }

    private String getEnvPrefix() {
        if (Environment.current == Environment.PRODUCTION) {
            return "PROD: "
        } else {
            return "DEV: "
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getLicenses() {
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user) {
            if (userService.getSuperUser(user) || userService.getSalesView(user) || userService.getConsultant(user)) {
                return License.collection.find()?.collect({ it as License })?.sort({ it.name.toLowerCase() })
            } else {
                return userService.getManagedLicenses(user)?.sort({ License license -> license.name.toLowerCase() })
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getLicensesForForm() {
        List<License> licenses
        def result = License.collection.find([:], ["name": 1, "licensedIndicatorIds": 1, "defaultProjectTemplateIdByAccount": 1, "projectTemplateIdsByAccount": 1])

        if (result) {
            licenses = result.collect({ it as License })
        }
        return licenses
    }

    def getLicensesByIds(List licenseIds, Boolean alsoFloatinLicenses = Boolean.TRUE) {
        List<License> licenses

        if (licenseIds) {
            if (alsoFloatinLicenses) {
                licenses = License.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)]])?.collect({ it as License })
            } else {
                licenses = License.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)], floatingLicense: [$in: [null, Constants.FloatingLicense.NO.toString()]]])?.collect({ it as License })
            }

        }
        return licenses
    }

    def getLicensesAsJSONByIds(List licenseIds, Boolean alsoFloatinLicenses) {
        List<Document> licenses

        if (licenseIds) {
            if (alsoFloatinLicenses) {
                licenses = License.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(licenseIds).unique()]], [_id: 1, name: 1, licensedFeatureIds: 1])?.toList()
            } else {
                licenses = License.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(licenseIds).unique()], floatingLicense: [$in: [null, Constants.FloatingLicense.NO.toString()]]], [_id: 1, name: 1, licensedFeatureIds: 1, maxEntities: 1, validUntil: 1, licensedEntityIds: 1, daysToNextRenewal: 1])?.toList()
            }
        }
        return licenses
    }

    def getLicenseForModifying(licenseId) {
        License license

        if (licenseId) {
            license = License.get(licenseId)
        }
        return license
    }


    @Secured(["ROLE_AUTHENTICATED"])
    def getAllLicenses() {
        return License.list()
    }

    def getLicenseById(licenseId) {
        License license

        if (licenseId) {
            if (licenseId instanceof String) {
                license = License.findById(DomainObjectUtil.stringToObjectId(licenseId))
            } else {
                license = License.findById(licenseId)
            }
        }
        return license
    }

    def getLicenseForChangeHistories(id) {
        License license

        if (id)
            try {
                license = License.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(id)], [name: 1, changeHistories: 1]) as License
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting license for changeHistories", e)
                flashService.setErrorAlert("Error in getting license for changeHistories: ${e.getMessage()}", true)
            }
        return license
    }

    def getLicenseByKey(String licenseKey) {
        License license

        if (licenseKey) {
            license = License.findByLicenseKey(licenseKey)
        }
        return license
    }

    def getLicenseByJoinCode(String joinCode) {
        License license

        if (joinCode) {
            try {
                license = License.collection.findOne(["joinCode.code": joinCode]) as License
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting license with joinCode ${joinCode}", e)
                flashService.setErrorAlert("Error in getting license with joinCode ${joinCode}: ${e.getMessage()}", true)
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getValidLicenses(List<String> licenseIds = null) {
        Date currentDate = new Date()

        List<License> licenses = License.withCriteria {
            eq('frozen', null)
            lte('validFrom', currentDate)
            gte('validUntil', currentDate)

            if (licenseIds) {
                inList('id', DomainObjectUtil.stringsToObjectIds(licenseIds))
            }
        }

        return licenses?.sort({ it.name?.toLowerCase() })
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<LicenseDTO> getValidLicenseDTOs(List<String> licenseIds = null) {
        Date currentDate = new Date()

        return License.withCriteria {
            eq('frozen', null)
            lte('validFrom', currentDate)
            gte('validUntil', currentDate)

            if (licenseIds) {
                inList('id', DomainObjectUtil.stringsToObjectIds(licenseIds))
            }

            projections {
                property('id')
                property('name')
                property('compatibleEntityClasses')
                property('planetary')
            }
        }.collect { List fields ->
            new LicenseDTO(id: fields[0], name: fields[1], compatibleEntityClasses: fields[2], planetary: fields[3])
        }.sort { LicenseDTO licenseDTO ->
            licenseDTO.name.toLowerCase()
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Integer getAllValidLicensesCount() {
        Date currentDate = new Date()
        Integer count = License.collection.count([
                validFrom: [$lte: currentDate],
                validUntil: [$gte: currentDate],
                frozen: [$eq: null]
        ])
        return count
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<License> getValidLicensesWithCriteria(Closure searchRestriction, List<String> licenseIds = null) {
        Timestamp currentDate = new Timestamp(new Date().getTime());
        def criteria = License.createCriteria()
        List<ObjectId> ids = []

        if (licenseIds) {
            ids = DomainObjectUtil.stringsToObjectIds(licenseIds)
        }

        List<License> licenses = criteria.list {
            if (ids) {
                'in'('id', ids)
            }

            le('validFrom', currentDate)
            ge('validUntil', currentDate)
            searchRestriction(criteria)
            order('name')
        }.findAll {
            it.frozen == null
        }

        return licenses
    }

    @Secured(["ROLE_SUPER_USER"])
    def getExpiredLicenses() {
        Date currentDate = new Date()
        List<License> licenses = License.findAllByValidFromGreaterThanEqualsOrValidUntilLessThanEquals(currentDate, currentDate)
        return licenses?.sort({ it.name?.toLowerCase() })
    }

    @Secured(["ROLE_SUPER_USER"])
    def getExpiredLicensesWithCriteria(Closure searchRestriction) {
        Timestamp currentDate = new Timestamp(new Date().getTime());
        def criteria = License.createCriteria()

        List<License> licenses = criteria.list {
            or {
                ge('validFrom', currentDate)
                le('validUntil', currentDate)
            }

            searchRestriction(criteria)
            order('name')
        }

        return licenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getUserLicenses(entityType = null, entityClass = null) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<License> licenses = userService.getLicenses(user)
        def resolvedLicenses = []

        if (entityType || entityClass) {
            if (licenses) {
                licenses.each { License l ->
                    def licensedIndicators = l.licensedIndicators

                    if (licensedIndicators) {
                        for (Indicator i in licensedIndicators) {
                            if (i.compatibleEntityTypes && i.compatibleEntityTypes.contains(entityType)) {
                                resolvedLicenses.add(l)
                                break
                            } else if (!i.compatibleEntityTypes && i.compatibleEntityClasses && i.compatibleEntityClasses.contains(entityClass)) {
                                resolvedLicenses.add(l)
                                break
                            } else if (!i.compatibleEntityTypes && !i.compatibleEntityClasses) {
                                resolvedLicenses.add(l)
                                break
                            }
                        }
                    } else {
                        resolvedLicenses.add(l)
                    }
                }
            }
        } else {
            resolvedLicenses = licenses
        }
        return resolvedLicenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getManagedLicenses(entityType, entityClass) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<License> licenses = userService.getManagedLicenses(user)
        def resolvedLicenses = []

        if (entityClass) {
            if (licenses) {
                licenses.each { License l ->
                    if (l.compatibleEntityClasses?.contains(entityClass) || !l.compatibleEntityClasses) {
                        def licensedIndicators = l.licensedIndicators

                        if (licensedIndicators) {
                            for (Indicator i in licensedIndicators) {
                                if (i.compatibleEntityTypes && i.compatibleEntityTypes.contains(entityType)) {
                                    resolvedLicenses.add(l)
                                    break
                                } else if (!i.compatibleEntityTypes && i.compatibleEntityClasses && i.compatibleEntityClasses.contains(entityClass)) {
                                    resolvedLicenses.add(l)
                                    break
                                } else if (!i.compatibleEntityTypes && !i.compatibleEntityClasses) {
                                    resolvedLicenses.add(l)
                                    break
                                }
                            }
                        } else {
                            resolvedLicenses.add(l)
                        }
                    }
                }
            }
        } else {
            resolvedLicenses = licenses
        }
        return resolvedLicenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    boolean isFeatureLicensed(String featureId, String entityType, String entityClass) {
        boolean isLicensed = false
        Date currentDate = new Date()
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (featureId) {
                Feature feature = featureService.getFeatureByFeatureId(featureId)
            if (feature) {
                List<License> validLicenses = findLicensesByFeature(feature).findAll {
                    it.validFrom <= currentDate && it.validUntil >= currentDate
                }

                List<License> usableAndManagedLicenses = getUsableAndManagedLicenses(user, validLicenses)
                List<License> filteredLicenses = filterLicensesByEntityTypeAndEntityClass(usableAndManagedLicenses, entityType, entityClass)
                isLicensed = filteredLicenses.size() > 0
            }
        }

        return isLicensed
    }

    /**
     * Check if a featureId (licenseKey) is licensed among the licenses with licenseIds
     * If user is SU, bypass the check
     * @param featureId
     * @param licenseIds
     * @param user
     * @return
     */
    boolean isFeatureLicensed(String featureId, List<String> licenseIds, User user = null) {
        if (featureId) {
            if (!user) {
                user = userService.getCurrentUser()
            }
            if (userService.getSuperUser(user) || userService.getConsultant(user)) {
                return true
            } else if (licenseIds?.size() > 0) {
                List<License> licenses = getLicensesByIds(licenseIds)
                licenses?.each { License license ->
                    license?.licensedFeatures?.each { Feature feature ->
                        if (feature?.featureId == featureId) {
                            return true
                        }
                    }
                }

            }
        }
        return false
    }

    def getFeatures(List<License> licenses) {
        List<Feature> features = []

        if (licenses) {
            licenses.each { License l ->
                l.licensedFeatures?.each {
                    features.add(it)
                }
            }
        }
        return features.unique({ it.featureId })

    }

    def isFeatureLicensedForEntity(Entity entity, String indicatorId, String featureId) {
        boolean isLicensed = false

        if (entity && featureId) {
            Feature feature = featureService.getFeatureByFeatureId(featureId)

            if (feature) {
                List<License> validLicenses = getValidLicensesForEntity(entity)

                if (validLicenses?.find({
                    it.licensedFeatureIds &&
                            DomainObjectUtil.stringsToObjectIds(it.licensedFeatureIds).contains(feature.id)
                })) {
                    isLicensed = true
                }
            } else {
                loggerUtil.error(log, "Cannot find a license feature with featureId ${featureId} from indicator ${indicatorId}")
                flashService.setErrorAlert("Cannot find a license feature with featureId ${featureId} from indicator ${indicatorId}", true)
            }
        }
        return isLicensed
    }

    def getLicensesByFeature(String featureId) {
        if (featureId) {
            return getAllLicenses()?.findAll({ License l -> l.licensedFeatureIds && l.licensedFeatures?.collect({ Feature f -> f.featureId })?.contains(featureId) })
        }
        return null

    }

    def getLicensesByFeatureId(String id) {
        if (id) {
            return License.collection.find([licensedFeatureIds: [$in: [id]]])?.collect({ it as License })
        }
        return null

    }

    def getLicenseTemplatesByFeatureId(String id) {
        if (id) {
            return LicenseTemplate.collection.find([licensedFeatureIds: [$in: [id]]])?.collect({
                it as LicenseTemplate
            })
        }
        return null

    }

    def getLicensesByIndicator(String indicatorId) {
        List<License> licenses

        if (indicatorId) {
            licenses = License.collection.find([licensedIndicatorIds: [$in: [indicatorId]]])?.collect({ it as License })
        }
        return licenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getLicenseNamesByLicenseIds(List<String> licenseIds, Boolean getFloatingLicense = Boolean.FALSE, Boolean onlyValid = Boolean.FALSE) {
        List<Document> licenses

        if (getFloatingLicense) {
            if (onlyValid) {
                licenses = License.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)], floatingLicense: [$nin: [null, Constants.FloatingLicense.NO.toString()]], validUntil: [$gte: new Date()]], [id: 1, name: 1, validUntil: 1]).toList()
            } else {
                licenses = License.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)], floatingLicense: [$nin: [null, Constants.FloatingLicense.NO.toString()]]], [id: 1, name: 1, validUntil: 1]).toList()
            }
        } else {
            if (onlyValid) {
                licenses = License.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)], floatingLicense: [$in: [null, Constants.FloatingLicense.NO.toString()]], validUntil: [$gte: new Date()]], [id: 1, name: 1, validUntil: 1]).toList()
            } else {
                licenses = License.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(licenseIds)], floatingLicense: [$in: [null, Constants.FloatingLicense.NO.toString()]]], [id: 1, name: 1, validUntil: 1]).toList()
            }
        }
        return licenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getUsableAndManagedPortfolioLicenses() {
        List<License> resolvedLicenses = []
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user) {
            List<License> licenses = userService.getUsableAndManagedLicenses(user)

            if (licenses) {
                licenses.each { License l ->

                    if (l.compatibleEntityClasses?.contains(Constants.ParentEntityClass.PORTFOLIO.toString())) {
                        resolvedLicenses.add(l)
                    }
                }
            }
        }
        return resolvedLicenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getUsableAndManagedLicenses2(String entityClass, Boolean hasLicenses) {
        List<License> resolvedLicenses = []
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user && entityClass) {
            Boolean internalUser = user.internalUseRoles
            Date currentDate = new Date()
            List idsString = []
            if (user.managedLicenseIds) {
                idsString.addAll(user.managedLicenseIds)
            }

            if (user.usableLicenseIds) {
                idsString.addAll(user.usableLicenseIds)
            }
            List<ObjectId> ids = DomainObjectUtil.stringsToObjectIds(idsString).unique() ?: []

            List<License> licenses = License.withCriteria {
                eq('frozen', null)
                lte('validFrom', currentDate)
                gte('validUntil', currentDate)
                eq('compatibleEntityClasses', entityClass)
                and {
                    or {
                        eq('preventAddToProject', null)
                        eq('preventAddToProject', false)
                    }
                    if (!internalUser) {
                        or {
                            eq('freeForAllEntities', true)
                            inList('_id', ids)
                        }
                    }
                    if (hasLicenses) {
                        or {
                            eq('planetary', null)
                            eq('planetary', false)
                        }
                    }
                }
            }

            if (licenses) {
                licenses.each { License l ->
                    if (l.freeForAllEntities) {
                        if (internalUser || (ids.contains(l.id))) {
                            resolvedLicenses.add(l)
                        } else {
                            if (l.conditionalEntityClasses) {
                                if (l.conditionalEntityClasses.contains(entityClass)) {
                                    resolvedLicenses.add(l)
                                }
                            } else {
                                resolvedLicenses.add(l)
                            }
                        }
                    } else {
                        resolvedLicenses.add(l)
                    }
                }
            }
        }
        return resolvedLicenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<License> getUsableAndManagedLicenses(entityType = null, entityClass = null) {
        List<License> resolvedLicenses = []
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user) {
            List<License> licenses = userService.getUsableAndManagedLicenses(user)

            if (entityClass) {
                if (licenses) {
                    licenses.each { License l ->

                        if (l.compatibleEntityClasses?.contains(entityClass) || !l.compatibleEntityClasses) {
                            def licensedIndicators = l.licensedIndicators

                            if (licensedIndicators) {
                                for (Indicator i in licensedIndicators) {
                                    if (i.compatibleEntityTypes && i.compatibleEntityTypes.contains(entityType)) {
                                        resolvedLicenses.add(l)
                                        break
                                    } else if (!i.compatibleEntityTypes && i.compatibleEntityClasses && i.compatibleEntityClasses.contains(entityClass)) {
                                        resolvedLicenses.add(l)
                                        break
                                    } else if (!i.compatibleEntityTypes && !i.compatibleEntityClasses) {
                                        resolvedLicenses.add(l)
                                        break
                                    }
                                }
                            } else {
                                resolvedLicenses.add(l)
                            }
                        }
                    }
                }
            } else {
                resolvedLicenses = licenses
            }
        }
        return resolvedLicenses
    }

    List<License> filterLicensesByEntityTypeAndEntityClass(List<License> licenses, entityType = null, entityClass = null){
        List<License> resolvedLicenses = []

        if (entityClass) {
            if (licenses) {
                licenses.each { License license ->
                    if (license.compatibleEntityClasses?.contains(entityClass) || !license.compatibleEntityClasses) {
                        List<Indicator> licensedIndicators = license.licensedIndicators

                        if (licensedIndicators) {
                            for (Indicator i in licensedIndicators) {
                                if (i.compatibleEntityTypes && i.compatibleEntityTypes.contains(entityType)) {
                                    resolvedLicenses.add(license)
                                    break
                                } else if (!i.compatibleEntityTypes && i.compatibleEntityClasses && i.compatibleEntityClasses.contains(entityClass)) {
                                    resolvedLicenses.add(license)
                                    break
                                } else if (!i.compatibleEntityTypes && !i.compatibleEntityClasses) {
                                    resolvedLicenses.add(license)
                                    break
                                }
                            }
                        } else {
                            resolvedLicenses.add(license)
                        }
                    }
                }
            }
        } else {
            resolvedLicenses = licenses
        }

        return resolvedLicenses
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    List<License> getUsableAndPlanetaryAndTrialLicensesByEntityClass(String entityClass, HttpSession session = null, User user = null, Boolean hasLicense = null, List<License> extraLicenses = []) {
        List<License> licenses = []
        try {
            if (entityClass) {
                if (!user) {
                    user = userService.getCurrentUser()
                }
                if (hasLicense == null) {
                    hasLicense = user?.managedLicenseIds || user?.usableLicenseIds
                }

                licenses = getUsableAndManagedLicenses2(entityClass, hasLicense)

                if (!hasLicense && entityClass == Constants.EntityClass.BUILDING.type) {
                    License planetaryLicense = getPlanetaryLicenseForCountryOrState(user?.country)
                    if (planetaryLicense) {
                        licenses.add(planetaryLicense)
                    }
                }

                if (!session) {
                    session = WebUtils.retrieveGrailsWebRequest()?.getSession()
                }

                if ((user?.trialCount < 1 && (Constants.EntityClass.BUILDING.type.equalsIgnoreCase(entityClass)) || (user?.trialCountForInfra < 1 && (Constants.EntityClass.INFRASTRUCTURE.type.equalsIgnoreCase(entityClass)))) && channelFeatureService?.getChannelFeature(session)?.enableAutomaticTrial) {
                    List<License> autoStartTrials = getActiveAutoStartTrials()?.findAll({
                        !it.compatibleEntityClasses || it.compatibleEntityClasses.contains(entityClass)
                    })

                    if (autoStartTrials) {
                        if (user?.country && autoStartTrials.find({ it.countries?.contains(user?.country) })) {
                            autoStartTrials = autoStartTrials.sort({ a, b -> a.countries?.contains(user?.country) <=> b.countries?.contains(user?.country) ?: b.name <=> a.name }).reverse()
                        } else {
                            autoStartTrials = autoStartTrials.sort({ a, b -> !a.countries <=> !b.countries ?: b.name <=> a.name }).reverse()
                        }
                        licenses += autoStartTrials
                    }
                }
            }

            if (extraLicenses) {
                licenses += extraLicenses
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getAllUsableLicense", e)
            flashService.setErrorAlert("Error occurred in getting the licenses: ${e.getMessage()}", true)
        }

        return licenses
    }

    LicenseTier getLicenseTier(String id) {
        return LicenseTier.findByIdAndDeleted(DomainObjectUtil.stringToObjectId(id), false)
    }

    Set<LicenseTier> getLicenseTiers() {
        return LicenseTier.withCriteria {
            ne('deleted', true)
        } as Set<LicenseTier>
    }

    @Secured([Constants.ROLE_SUPER_USER])
    void saveLicenseTier(GrailsParameterMap params) {
        if (!(params.name as String)?.trim()) {
            flashService.setErrorAlert('Please enter a name for license tier')
            return;
        }

        try {
            if (params.id) {
                LicenseTier.collection.updateOne(["_id": DomainObjectUtil.stringToObjectId(params.id)], [$set: [name: params.name]])
                flashService.setFadeSuccessAlert("License tier ${params.name} edited successfully")
            } else {
                LicenseTier licenseTier = new LicenseTier(name: params.name)
                if (licenseTier.validate()) {
                    licenseTier.save(failOnError: true)
                    flashService.setFadeSuccessAlert("License tier ${params.name} created successfully")
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in saving license tier", e)
            flashService.setErrorAlert("Error in saving license tier ${e.message}")
        }
    }

    @Secured([Constants.ROLE_SUPER_USER])
    void deleteLicenseTier(GrailsParameterMap params) {
        if (!params.id) {
            flashService.setErrorAlert('Something went wrong. No license tier id is passed to backend')
            return;
        }

        try {
            LicenseTier.collection.updateOne(["_id": DomainObjectUtil.stringToObjectId(params.id)], [$set: [deleted: true]])
            flashService.setFadeSuccessAlert("License tier deleted successfully")
        } catch (e) {
            loggerUtil.error(log, "Error in deleting license tier", e)
            flashService.setErrorAlert("Error in deleting license tier ${e.message}")
        }
    }

    @Secured([Constants.ROLE_SUPER_USER])
    void applyLicenseTier(GrailsParameterMap params) {

        try {
            License license = getLicenseById(params.licenseId)
            if (!license) {
                flashService.setErrorAlert('Something went wrong. No license can be found')
                return;
            }

            license.tierId = params.tierId ?: ''

            if (license.validate()) {
                license.save(failOnError: true)
                flashService.setFadeSuccessAlert('License tier applied successfully')
            }
        } catch (e) {
            loggerUtil.error(log, "Error in applying license tier to license", e)
            flashService.setErrorAlert("Error in applying license tier to license ${e.message}")
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def saveLicense(License license, String managerId, List<String> accountIds = null) {
        if (license) {
            if (license.freeForAllEntities && license.licensedEntityIds) {
                license.licensedEntityIds = null
            }

            if (license.freeForAllEntities && license.licensedUserIdsMap && license.compatibleEntityClasses) {
                def entityIds = entityService.getAllEntities(license.compatibleEntityClasses, false)?.collect({ Entity e -> e.id })

                entityIds?.each { ObjectId entityId ->
                    license.licensedUsers?.each { User user, String userType ->
                        entityService.addEntityUser(entityId, user.username, userType)
                    }
                }
            }

            if (!Constants.LicenseType.TRIAL.toString().equals(license.type) && !Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                license.trialLength = null
            }

            if (!license.isFloatingLicense) {
                license.floatingLicenseCheckedInUsers = null
                license.floatingLicenseMaxUsers = null
                license.floatingLicenseScope = null
            }

            license = license.save(flush: true)
        }
        return license
    }

    //12536
    def removeLicenseManagerId(String id, String userId) {
        if (userId) {
            License license1 = License.get(id)
            List<User> manager = license1?.managers

            if (manager) {
                manager.each {
                    if (userId.equals(it.id.toString())) {
                        if (it.managedLicenseIds) {
                            it.managedLicenseIds.remove(license1.id.toString())
                            it.merge(flush: true)
                        }
                    }
                }
            }
            license1?.managerIds?.remove(userId)
            license1.merge(flush: true)
        }
    }
    //12536
    private void removeLicenseManager(License license) {
        List<User> manager = license?.managers

        if (manager) {
            manager.each {
                if (it.managedLicenseIds) {
                    it.managedLicenseIds.remove(license.id.toString())
                }
                it.merge(flush: true)
            }
        }
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getById(id) {
        License license

        if (id) {
            if (id instanceof String) {
                license = License.findById(DomainObjectUtil.stringToObjectId(id))
            } else {
                license = License.findById(id)
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def delete(licenseId) {
        if (licenseId) {
            License license = License.get(licenseId)

            if (license) {
                removeLicenseManager(license)
                def accountResults = Account.collection.find([licenseIds: ["\$in": [license.id.toString()]]])

                if (accountResults) {
                    accountResults.collect({ it as Account }).each { Account account ->
                        account.licenseIds.remove(license.id.toString())
                        account.merge(flush: true)
                    }
                }
                license?.delete(flush: true)
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def addEntity(id, entityId) {
        boolean entityAdded = false
        License license

        if (id && entityId) {
            license = getById(id)

            if (!license.maxEntitiesReached && !license.preventAddToProject) {
                if (!license.licensedEntityIds?.contains(entityId.toString())) {
                    if (license.licensedEntityIds) {
                        license.licensedEntityIds.add(entityId.toString())
                    } else {
                        license.licensedEntityIds = [entityId.toString()]
                    }

                    if (license.removedEntityIds?.contains(entityId.toString())) {
                        license.removedEntityIds.remove(entityId.toString())
                    }
                    User user = userService.getCurrentUser()

                    if (user) {
                        boolean existing = false
                        LicenseEntityChangeHistory licenseEntityChangeHistory = license.entityChangeHistories?.find({
                            entityId.equals(it.entityId)
                        })

                        if (licenseEntityChangeHistory) {
                            existing = true
                        } else {
                            licenseEntityChangeHistory = new LicenseEntityChangeHistory()
                        }
                        licenseEntityChangeHistory.dateAdded = new Date()
                        licenseEntityChangeHistory.addedBy = user.username
                        licenseEntityChangeHistory.entityId = entityId.toString()

                        if (!existing) {
                            if (license.entityChangeHistories) {
                                license.entityChangeHistories.add(licenseEntityChangeHistory)
                            } else {
                                license.entityChangeHistories = [licenseEntityChangeHistory]
                            }
                        }
                    }
                    license.lastUpdaterName = user.username
                    license.merge(flush: true, failOnError: true)
                    entityAdded = true
                    Entity entity = Entity.get(entityId)

                    def licensedUsers = license.licensedUsers

                    if (licensedUsers) {
                        licensedUsers.each { User u, String userType ->
                            entityService.addEntityUser(entity.id, u.username, userType)
                        }
                    }

                    String newToolsAvailable = null
                    Boolean needSave = Boolean.FALSE
                    def licensedIndicatorIds = license.licensedIndicatorIds
                    if (licensedIndicatorIds) {
                        Boolean addNewLicensedIndicatorsFallback = resolveAddingIndicatorIdsFallback(entity)

                        license.licensedIndicators?.each { Indicator indicator ->
                            if (!indicator.deprecated) {
                                if (!entity.indicatorIds?.contains(indicator.indicatorId)) {
                                    if (!Constants.LicenseType.TRIAL.toString().equals(license?.type) && !"benchmark".equalsIgnoreCase(indicator.visibilityStatus)) {
                                        newToolsAvailable = indicator.indicatorUse
                                    }

                                    if (addNewLicensedIndicatorsFallback) {
                                        // To not add tools to project that already has designs, 12088
                                        needSave = Boolean.TRUE
                                        if (entity.indicatorIds) {
                                            entity.indicatorIds.add(indicator.indicatorId)
                                        } else {
                                            entity.indicatorIds = [indicator.indicatorId]
                                        }
                                    }
                                }
                            }
                        }
                    }
                    List<String> readOnlyIndicators = license.readonlyIndicatorIds
                    if (readOnlyIndicators && readOnlyIndicators.size() > 0) {
                        if (entity.readonlyIndicatorIds && !entity.readonlyIndicatorIds.containsAll(readOnlyIndicators)) {
                            entity.readonlyIndicatorIds.addAll(readOnlyIndicators)
                        } else if (!entity.readonlyIndicatorIds) {
                            entity.readonlyIndicatorIds = readOnlyIndicators
                        }
                    }

                    if (newToolsAvailable) {
                        entity.newToolsAvailable = newToolsAvailable
                        entity.merge(flush: true)
                    } else if (needSave) {
                        entity.merge(flush: true)
                    }
                }
            }
        }
        return entityAdded
    }

    private Boolean resolveAddingIndicatorIdsFallback(Entity entity) {
        Boolean fallback = Boolean.FALSE

        if (entity) {
            if (!entity.childEntities) {
                fallback = Boolean.TRUE
            } else {
                if (!entity.childEntities.find({ !it.deleted })) {
                    fallback = Boolean.TRUE
                } else if (!entity.indicatorIds || (entity.indicatorIds && entity.indicatorIds.isEmpty())) {
                    fallback = Boolean.TRUE
                } else {
                    List<String> benchmarkIndicatorIds = getBenchmarkIndicatorIds()

                    if (benchmarkIndicatorIds) {
                        if (!entity.indicatorIds.find({ !benchmarkIndicatorIds.contains(it) })) {
                            fallback = Boolean.TRUE
                        }
                    }
                }
            }
        }
        return fallback
    }

    def getBenchmarkIndicatorIds() {
        BasicDBObject filter = new BasicDBObject("active", true).append("visibilityStatus", "benchmark")
        return Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addIndicator(id, indicatorId) {
        License license

        if (id && indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            license = License.get(id)

            if (!license.licensedIndicatorIds?.contains(indicatorId)) {
                if (license.licensedIndicatorIds) {
                    license.licensedIndicatorIds?.add(indicatorId)
                } else {
                    license.licensedIndicatorIds = [indicatorId]
                }

                if (license.validate()) {
                    license = license.merge(flush: true)
                } else {
                    license = null
                }
            }

            if (indicator) {
                license?.getLicensedEntities()?.each { Entity e ->
                    if (!e.indicatorIds?.contains(indicatorId)) {
                        e.newToolsAvailable = indicator.indicatorUse
                        e.merge(flush: true)
                    }
                }
            }

        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addFeature(id, featureId) {
        License license

        if (id && featureId) {
            license = License.get(id)

            if (!license.licensedFeatureIds?.contains(featureId)) {
                if (license.licensedFeatureIds) {
                    license.licensedFeatureIds.add(featureId)
                } else {
                    license.licensedFeatureIds = [featureId]
                }
                license = license.merge(flush: true)
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addWorkFlow(id, String workFlowId) {
        License license

        if (id && workFlowId) {
            license = License.get(id)

            if (!license.licensedWorkFlowIds?.contains(workFlowId)) {
                if (license.licensedWorkFlowIds) {
                    license.licensedWorkFlowIds.add(workFlowId)
                } else {
                    license.licensedWorkFlowIds = [workFlowId]
                }
                license = license.merge(flush: true)
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addApiIndicator(id, String apiIndicatorId) {
        License license

        if (id) {
            license = License.get(id)

            if (apiIndicatorId) {
                license.apiIndicatorId = apiIndicatorId
            } else {
                license.apiIndicatorId = null
            }
            license = license.merge(flush: true)
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addReadonlyIndicator(id, String readonlyIndicatorId) {
        License license

        if (id) {
            license = License.get(id)

            if (readonlyIndicatorId) {
                if (!license.readonlyIndicatorIds?.contains(readonlyIndicatorId)) {
                    if (license.readonlyIndicatorIds) {
                        license.readonlyIndicatorIds.add(readonlyIndicatorId)
                    } else {
                        license.readonlyIndicatorIds = [readonlyIndicatorId]
                    }
                    license = license.merge(flush: true)
                }

                license.getLicensedEntities()?.each { Entity e ->
                    List<String> readonlyIndicatorIds = e.readonlyIndicatorIds

                    if (readonlyIndicatorIds) {
                        readonlyIndicatorIds.add(readonlyIndicatorId)
                    } else {
                        readonlyIndicatorIds = [readonlyIndicatorId]
                    }
                    Entity.collection.updateOne(["_id": e.getId()], [$set: [readonlyIndicatorIds: readonlyIndicatorIds]])
                }
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def addUser(id, String email, userType, session) {
        String errorFromAdd

        if (id && email && userType) {
            try {
                License license = getById(id)
                User user = User.findByUsername(email.trim().toLowerCase())
                User currentUser = userService.getCurrentUser()

                if (user && license) {
                    def userId = user.id.toString()

                    if (license.licensedUserIdsMap) {
                        license.licensedUserIdsMap.put(userId, userType)
                    } else {
                        license.licensedUserIdsMap = [(userId): userType]
                    }
                    id = license.id.toString()

                    if (user.licenseIds) {
                        if (!user.licenseIds.contains(id)) {
                            user.licenseIds.add(id)
                        }
                    } else {
                        user.licenseIds = [id]
                    }
                    user = userService.updateUser(user)
                    license = license.merge(flush: true)
                    if ("production".equalsIgnoreCase(license.type) && !license.openTrial) {
                        def updateActiveCampaign = activeCampaignService.updateUserType(user, "commercial", session, null, null, currentUser?.username)
                        if (!updateActiveCampaign) {
                            loggerUtil.warn(log, "Error when updating license invitation on Active Campaign")
                            flashService.setErrorAlert("Error when updating license invitation on Active Campaign", true)
                        }
                    }
                    if (license.freeForAllEntities && license.licensedUserIdsMap && license.compatibleEntityClasses) {
                        List<ObjectId> entityIds = entityService.getAllEntities(license.compatibleEntityClasses, false)?.collect({ Entity e -> e.id })

                        entityIds?.each { ObjectId entityId ->
                            entityService.addEntityUser(entityId, user.username, userType, Boolean.TRUE)
                        }
                    } else {
                        license.licensedEntities?.collect({ Entity e -> e.id })?.each { ObjectId entityId ->
                            entityService.addEntityUser(entityId, email.trim(), userType, Boolean.TRUE)
                        }
                    }
                } else if (!user) {
                    errorFromAdd = "User not added. No user with email ${email} found."
                }
            } catch (Exception e) {
                errorFromAdd = "Error in adding user to license: ${e}"
            }
        }
        return errorFromAdd
    }

    def addManager(id, managerId, licenseManagerCanAddLicenseUserIds) {
        boolean addOk = false

        if (id && managerId) {
            License license = getById(id)
            User newManagerToAdd = userService.getUserByUsername(managerId)

            if (newManagerToAdd) {
                if (license.managerIds) {
                    if (!license.managerIds.contains(newManagerToAdd.id.toString())) {
                        license.managerIds.add(newManagerToAdd.id.toString())
                        if (licenseManagerCanAddLicenseUserIds) {
                            license?.licenseManagerCanAddLicenseUserIds = true
                        } else {
                            license?.licenseManagerCanAddLicenseUserIds = false
                        }
                        license = license.merge(flush: true)
                    }
                } else {
                    license.managerIds = [newManagerToAdd.id.toString()]
                    if (licenseManagerCanAddLicenseUserIds) {
                        license?.licenseManagerCanAddLicenseUserIds = true
                    } else {
                        license?.licenseManagerCanAddLicenseUserIds = false
                    }
                    license = license.merge(flush: true)
                }

                if (newManagerToAdd.managedLicenseIds) {
                    if (!newManagerToAdd.managedLicenseIds.contains(license.id.toString())) {
                        newManagerToAdd.managedLicenseIds.add(license.id.toString())
                        newManagerToAdd.merge(flush: true)
                    }
                } else {
                    newManagerToAdd.managedLicenseIds = [license.id.toString()]
                    newManagerToAdd.merge(flush: true)
                }
                addOk = true
            }
        }
        return addOk
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def addLicenseUser(id, String email, session = null) {
        boolean addOk = false

        if (id && email) {
            License license = getById(id)
            User user = User.findByUsername(email.trim().toLowerCase())
            User currentUser = userService.getCurrentUser()

            if (user && license) {
                def userId = user.id.toString()

                if (license.licenseUserIds) {
                    if (!license.licenseUserIds.contains(userId)) {
                        license.licenseUserIds.add(userId)
                    }
                } else {
                    license.licenseUserIds = [userId]
                }
                license = license.merge(flush: true)
                id = license.id.toString()

                if (user.usableLicenseIds) {
                    if (!user.usableLicenseIds.contains(id)) {
                        user.usableLicenseIds.add(id)
                    }
                } else {
                    user.usableLicenseIds = [id]
                }
                user = userService.updateUser(user)
                addOk = true
            } else if (license && !UserLicenseInvitation.collection.findOne([email: email.trim().toLowerCase(), licenseId: id])) {
                UserLicenseInvitation userLicenseInvitation = new UserLicenseInvitation()
                userLicenseInvitation.email = email.trim().toLowerCase()
                userLicenseInvitation.licenseId = id
                userLicenseInvitation.save(flush: true)
            }

            if (license) {
                String activeUserType
                String organizationName
                if ("production".equalsIgnoreCase(license.type)) {
                    activeUserType = "commercial"
                } else if (license.openTrial) {
                    activeUserType = "trial"
                }
                if (currentUser && !userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(currentUser)) {
                    organizationName = currentUser.organizationName
                }
                def updateActiveCampaign = activeCampaignService.updateUserType(user, activeUserType, session, email.trim().toLowerCase(), organizationName, currentUser?.username)
                if (!updateActiveCampaign) {
                    loggerUtil.warn(log, "Error when updating license invitation on Active Campaign")
                    flashService.setErrorAlert("Error when updating license invitation on Active Campaign", true)
                }
            }
        }
        return addOk
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeInvitedUser(String invitationId) {
        if (invitationId) {
            UserLicenseInvitation userLicenseInvitation = UserLicenseInvitation.findById(DomainObjectUtil.stringToObjectId(invitationId))

            if (userLicenseInvitation) {
                userLicenseInvitation.delete(flush: true)
            }
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeLicenseUser(id, userId) {
        License license

        if (id && userId) {
            User user = User.get(userId)
            license = getById(id)
            license.licenseUserIds?.remove(userId.toString())
            if (license.floatingLicenseCheckedInUsers) {
                FloatingLicenseUser floatingLicenseUser = license.floatingLicenseCheckedInUsers.find({ it.userId == user.id.toString() })

                if (floatingLicenseUser) {
                    license.floatingLicenseCheckedInUsers.remove(floatingLicenseUser)
                }
            }
            license = license.merge(flush: true)
            user.usableLicenseIds?.removeIf({ id.toString().equals(it) })
            user.merge(flush: true)
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def checkoutFloatingUser(id, userId) {
        License license

        if (id && userId) {
            User user = User.get(userId)
            license = getById(id)
            if (license.floatingLicenseCheckedInUsers) {
                FloatingLicenseUser floatingLicenseUser = license.floatingLicenseCheckedInUsers.find({ it.userId == user.id.toString() })

                if (floatingLicenseUser) {
                    license.floatingLicenseCheckedInUsers.remove(floatingLicenseUser)
                    license = license.merge(flush: true)
                }
            }
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeUser(id, userId) {
        License license

        if (id && userId) {
            license = getById(id)
            def userType = license.licensedUserIdsMap?.get(userId.toString())
            license.licensedUserIdsMap?.remove(userId.toString())
            license = license.merge(flush: true)
            User user = User.get(userId)
            user.licenseIds?.removeIf({ id.toString().equals(it) })
            user.merge(flush: true)

            if (userType) {
                if (license?.freeForAllEntities && license?.compatibleEntityClasses) {
                    List<Entity> entities = entityService.getAllEntities(license.compatibleEntityClasses, false)

                    entities?.each { Entity e ->
                        entityService.removeEntityUser(e.id, userId, userType)
                    }
                } else {
                    license.licensedEntities?.each { Entity e ->
                        entityService.removeEntityUser(e.id, userId, userType)
                    }
                }
            }
        }
        return license
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def removeEntity(id, entityId, Boolean permanentEntityDeletion) {
        License license

        if (id && entityId) {
            license = getById(id)
            license.licensedEntityIds?.remove(entityId)

            if (!permanentEntityDeletion) {
                if (license.removedEntityIds) {
                    license.removedEntityIds.add(entityId)
                } else {
                    license.removedEntityIds = [entityId]
                }
            }
            User user = userService.getCurrentUser()

            if (user) {
                LicenseEntityChangeHistory licenseEntityChangeHistory = license.entityChangeHistories?.find({
                    entityId.toString().equals(it.entityId)
                })

                if (permanentEntityDeletion && licenseEntityChangeHistory) {
                    license.entityChangeHistories.remove(licenseEntityChangeHistory)
                } else {
                    boolean existing = false

                    if (licenseEntityChangeHistory) {
                        existing = true
                    } else {
                        licenseEntityChangeHistory = new LicenseEntityChangeHistory()
                    }
                    licenseEntityChangeHistory.dateRemoved = new Date()
                    licenseEntityChangeHistory.removedBy = user.username
                    licenseEntityChangeHistory.entityId = entityId.toString()

                    if (!existing) {
                        if (license.entityChangeHistories) {
                            license.entityChangeHistories.add(licenseEntityChangeHistory)
                        } else {
                            license.entityChangeHistories = [licenseEntityChangeHistory]
                        }
                    }
                }

            }
            license.lastUpdaterName = user.username
            license = license.merge(flush: true)
            Entity entity = Entity.get(entityId)

            if (entity && license?.licensedIndicatorIds && !permanentEntityDeletion) {
                List<String> validLicensedIndicatorIds = getValidLicensesForEntity(entity)?.findAll({ !license.id.equals(it.id) })?.collect({ it.licensedIndicatorIds })?.flatten()?.unique()
                List<String> indicatorIdsToRemove = license.licensedIndicatorIds

                if (validLicensedIndicatorIds) {
                    indicatorIdsToRemove.removeAll(validLicensedIndicatorIds)
                }

                if (indicatorIdsToRemove && entity.indicatorIds) {
                    entity.indicatorIds.removeAll(indicatorIdsToRemove)

                    if (validLicensedIndicatorIds && resolveAddingIndicatorIdsFallback(entity)) {
                        // If entity indicatorids happen to be empty after removing license add all valid indicatorIds
                        entity.indicatorIds.addAll(validLicensedIndicatorIds)
                    }
                    entity.merge(flush: true)
                }
            }
            def licensedUsers = license?.licensedUsers

            if (entity && licensedUsers) {
                licensedUsers.each { User u, String userType ->
                    entityService.removeEntityUser(entity.id, u.id.toString(), userType)
                }
            }
        }
        return license
    }

    @Secured(["ROLE_SUPER_USER"])
    def getLicenseTemplateById(String id) {
        if (id) {
            return LicenseTemplate.findById(DomainObjectUtil.stringToObjectId(id))
        } else {
            return null
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def getLicenseTemplatesByIds(List ids) {
        if (ids) {
            return LicenseTemplate.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(ids))
        } else {
            return null
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def getLicensesByTemplateId(String id) {
        if (id) {
            return License.collection.find([licenseTemplateIds: [$in: [id]]])?.collect({ it as License })
        } else {
            return null
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def handleAlreadyUsedTemplateFeatures(License license, LicenseTemplate licenseTemplate, List<String> featuresToRemove, List<String> indicatorsToRemove, List<String> workFlowsToRemove, List<String> projectTemplatesToRemove) {
        if (LicenseTemplate) {
            List<LicenseTemplate> usedOtherTemplates = license?.licenseTemplates?.findAll({
                !licenseTemplate.id.toString().equals(it.id.toString())
            })

            if (usedOtherTemplates && !usedOtherTemplates.isEmpty()) {
                usedOtherTemplates.each {
                    if (featuresToRemove && it.licensedFeatureIds) {
                        featuresToRemove.removeAll(it.licensedFeatureIds)
                    }

                    if (indicatorsToRemove && it.licensedIndicatorIds) {
                        indicatorsToRemove.removeAll(it.licensedIndicatorIds)
                    }

                    if (workFlowsToRemove && it.licensedWorkFlowIds) {
                        workFlowsToRemove.removeAll(it.licensedWorkFlowIds)
                    }

                    if (projectTemplatesToRemove && it.publicProjectTemplateIds) {
                        projectTemplatesToRemove.removeAll(it.publicProjectTemplateIds)
                    }
                }
            }
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def applyTemplateToLicense(License license, LicenseTemplate licenseTemplate, List<String> featuresToRemove = null, List<String> indicatorsToRemove = null, List<String> workFlowsToRemove = null, List<String> projectTemplatesToRemove = null) {
        String error

        if (license) {
            if (!error && licenseTemplate?.licensedIndicatorIds) {
                List<String> entityClasses = optimiResourceService.getParentEntityClassIds()
                def compatibleEntityClasses

                if (license.compatibleEntityClasses) {
                    compatibleEntityClasses = entityClasses?.findAll({ license.compatibleEntityClasses.contains(it) })
                } else {
                    compatibleEntityClasses = entityClasses
                }
                List<String> compatibleIndicatorIds = indicatorService.getIndicatorsByEntityClasses(compatibleEntityClasses)?.findAll({
                    !it.deprecated
                })?.collect({
                    Indicator indicator -> indicator.indicatorId
                })

                if (compatibleIndicatorIds.containsAll(licenseTemplate.licensedIndicatorIds)) {
                    if (license.licensedIndicatorIds) {
                        license.licensedIndicatorIds.addAll(licenseTemplate.licensedIndicatorIds)
                        license.licensedIndicatorIds = license.licensedIndicatorIds.unique()
                    } else {
                        license.licensedIndicatorIds = licenseTemplate.licensedIndicatorIds
                    }
                } else {
                    error = "Unable to apply template to license since it contains incompatible indicators."
                }
            }

            if (!error && indicatorsToRemove && license.licensedIndicatorIds) {
                license.licensedIndicatorIds.removeAll(indicatorsToRemove)
                license.licensedEntities?.each { Entity entity ->
                    if (entity) {
                        List<String> indicatorIds = entity.indicatorIds

                        if (indicatorIds && indicatorsToRemove) {
                            indicatorIds.removeIf({ indicatorsToRemove.contains(it) })
                            Entity.collection.updateOne(["_id": entity.getId()], [$set: [indicatorIds: indicatorIds]])
                        }
                    }
                }
            }

            if (!error && licenseTemplate?.licensedFeatureIds) {
                if (license.licensedFeatureIds) {
                    license.licensedFeatureIds.addAll(licenseTemplate.licensedFeatureIds)
                    license.licensedFeatureIds = license.licensedFeatureIds.unique()
                } else {
                    license.licensedFeatureIds = licenseTemplate.licensedFeatureIds
                }
            }

            if (!error && featuresToRemove && license.licensedFeatureIds) {
                license.licensedFeatureIds.removeAll(featuresToRemove)
            }

            if (!error && licenseTemplate?.licensedWorkFlowIds) {
                if (license.licensedWorkFlowIds) {
                    license.licensedWorkFlowIds.addAll(licenseTemplate.licensedWorkFlowIds)
                    license.licensedWorkFlowIds = license.licensedWorkFlowIds.unique()
                } else {
                    license.licensedWorkFlowIds = licenseTemplate.licensedWorkFlowIds
                }
            }

            if (!error && workFlowsToRemove && license.licensedWorkFlowIds) {
                license.licensedWorkFlowIds.removeAll(workFlowsToRemove)
            }

            if (!error && licenseTemplate?.publicProjectTemplateIds) {
                // add project template ids from license template to license. Only add new ones
                List<String> templateIdsToAdd = licenseTemplate.publicProjectTemplateIds.findAll({ !license.publicProjectTemplateIds?.contains(it) })
                List<ProjectTemplate> templatesToAdd = []

                if (templateIdsToAdd) {
                    templatesToAdd = projectTemplateService.getTemplatesByIds(templateIdsToAdd)
                    // filter out the incompatible project templates to this license
                    templateIdsToAdd = projectTemplateService.doFilterCompatibleTemplatesId(null, null, license, templatesToAdd) ?: []
                }

                if (license.publicProjectTemplateIds) {
                    // only add the compatible ones
                    if (templateIdsToAdd) {
                        license.publicProjectTemplateIds.addAll(templateIdsToAdd)
                    }
                    license.publicProjectTemplateIds = license.publicProjectTemplateIds.unique()
                } else {
                    license.publicProjectTemplateIds = templateIdsToAdd
                }

                // add default project template Id if it is valid (i.e. included in the publicProjectTemplateIds
                if (licenseTemplate?.defaultPublicProjectTemplateId && license.publicProjectTemplateIds?.contains(licenseTemplate?.defaultPublicProjectTemplateId)) {
                    license.defaultPublicProjectTemplateId = licenseTemplate?.defaultPublicProjectTemplateId
                }

                if (templatesToAdd) {
                    // update the compatible project templates
                    templatesToAdd?.each { ProjectTemplate projectTemplate ->
                        projectTemplateService.addLinkedLicense(license, null, projectTemplate)
                    }
                }
            }

            if (!error && projectTemplatesToRemove && license.publicProjectTemplateIds) {
                license.publicProjectTemplateIds.removeAll(projectTemplatesToRemove)

                // remove default project template Id if it is not valid (i.e. not included in the publicProjectTemplateIds
                if (license.defaultPublicProjectTemplateId && !license.publicProjectTemplateIds?.contains(license.defaultPublicProjectTemplateId)) {
                    license.defaultPublicProjectTemplateId = null
                }

                projectTemplateService.getTemplatesByIds(projectTemplatesToRemove)?.each { ProjectTemplate projectTemplate ->
                    projectTemplateService.removeLinkedLicense(license, projectTemplate)
                }
            }

            if (!error && licenseTemplate) {
                if (license.licenseTemplateIds) {
                    license.licenseTemplateIds.add(licenseTemplate.id.toString())
                    license.licenseTemplateIds = license.licenseTemplateIds.unique()
                } else {
                    license.licenseTemplateIds = [licenseTemplate.id.toString()]
                }
            }
        } else {
            error = "No license found."
        }
        return error
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeWorkFlow(id, workFlowId) {
        License license

        if (id && workFlowId) {
            license = getById(id)
            license?.licensedWorkFlowIds?.removeIf({ workFlowId.equals(it) })
            license = license.merge(flush: true)
        }
        return license
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeIndicator(id, indicatorId) {
        License license

        if (id && indicatorId) {
            try {
                license = getById(id)
                if (license) {
                    license.licensedIndicatorIds?.removeIf({ indicatorId.equals(it) })
                    license.removeIncompatibleProjectTemplates()
                    license = license.merge(flush: true, failOnError: true)

                    license.licensedEntities?.each { Entity entity ->
                        if (entity) {
                            List<String> indicatorIds = entity.indicatorIds

                            if (indicatorIds) {
                                indicatorIds.removeIf({ indicatorId.equals(it) })
                                Entity.collection.updateOne(["_id": entity.getId()], [$set: [indicatorIds: indicatorIds]])
                            }
                        }
                    }
                }
            } catch (Exception e) {
                loggerUtil.error(log, "Error in removing indicator from license", e)
                flashService.setErrorAlert("Error in removing indicator from license: ${e.getMessage()}", true)
            }
        }
        return license
    }

    @Secured(["ROLE_SUPER_USER"])
    def removeReadonlyIndicator(id, String indicatorId) {
        License license

        if (id && indicatorId) {
            try {
                license = getById(id)
                license.readonlyIndicatorIds.removeIf({ indicatorId.equals(it) })
                license = license.merge(flush: true, failOnError: true)

                license.licensedEntities?.each { Entity entity ->
                    if (entity) {
                        List<String> indicatorIds = entity.readonlyIndicatorIds

                        if (indicatorIds) {
                            indicatorIds.removeIf({ indicatorId.equals(it) })
                            Entity.collection.updateOne(["_id": entity.getId()], [$set: [readonlyIndicatorIds: indicatorIds]])
                        }
                    }
                }
            } catch (Exception e) {
                loggerUtil.error(log, "Error in removing indicator from license", e)
                flashService.setErrorAlert("Error in removing indicator from license: ${e.getMessage()}", true)
            }
        }
        return license
    }

    @Secured(["ROLE_SUPER_USER"])
    def removeFeature(id, featureId) {
        License license

        if (id && featureId) {
            license = getById(id)
            license?.licensedFeatureIds?.removeIf({ featureId.equals(it) })
            license = license.merge(flush: true)
        }
        return license
    }

    def getLicensesByLicenseUserId(String userId) {
        if (userId) {
            return License.collection.find([licenseUserIds: [$in: [userId]]])?.collect({ it as License })
        } else {
            return null
        }
    }

    def getLicensesByLicensedUserId(String userId) {
        if (userId) {
            Map query = [:]
            query.put("licensedUserIdsMap." + userId, [$exists: true])
            return License.collection.find(query)?.collect({ it as License })
        } else {
            return null
        }
    }

    List<License> getAutoStartTrials() {
        return License.findAllByTypeAndOpenTrial(Constants.LicenseType.TRIAL.toString(), Boolean.TRUE)
    }

    List<License> getActiveAutoStartTrials() {
        def currentDate = new Date()
        return License.findAllByTypeAndOpenTrialAndValidFromLessThanEqualsAndValidUntilGreaterThanEquals(Constants.LicenseType.TRIAL.toString(), Boolean.TRUE, currentDate, currentDate)
    }

    List<License> getValidTrials() {
        return License.findAllByTypeAndValidUntilNotLessThanAndTrialLengthIsNotNull(Constants.LicenseType.TRIAL.toString(), new Date())
    }

    List<License> getValidCommercialLicenses(Date renewalDeadline = null) {
        if (renewalDeadline) {
            return License.findAllByTypeAndValidUntilNotGreaterThanAndValidUntilNotLessThan(Constants.LicenseType.PRODUCTION.toString(), renewalDeadline, new Date())
        } else {
            return License.findAllByTypeAndValidUntilNotLessThan(Constants.LicenseType.PRODUCTION.toString(), new Date())

        }
    }

    List<License> getValidEducationalLicenses() {
        return License.findAllByTypeAndValidUntilNotLessThan(Constants.LicenseType.EDUCATION.toString(), new Date())
    }

    List<License> getValidLicensesWithNoUse() {
        return License.collection.find([validUntil: [$gte: new Date()], $or: [[licensedEntityIds: [$exists: false]], [licensedEntityIds: null], [licensedEntityIds: []]]])?.
                collect({ it as License })
    }

    def getIsImportMapperAvailableForCurrentUser(ImportMapper importMapper) {
        Boolean importMapperAvailable = false
        User user = userService.getCurrentUser()

        if (user && importMapper) {
            List<String> importFeatures = featureService.getImportFeatures(importMapper)?.collect({ it.featureId })
            List<String> entityClasses = importMapper.compatibleEntityClasses

            def importableEntityIds = []
            def manageableEntityIds = user.managedEntityIds

            if (manageableEntityIds) {
                importableEntityIds.addAll(manageableEntityIds)
            }
            def modifiableEntityIds = user.modifiableEntityIds

            if (modifiableEntityIds) {
                importableEntityIds.addAll(modifiableEntityIds)
            }

            if (!importableEntityIds.isEmpty()) {
                List<Entity> projects = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(importableEntityIds)], "entityClass": [$in: entityClasses], "deleted": false])?.collect({ it as Entity })

                if (projects) {
                    for (Entity project in projects) {
                        Boolean isImportFeatureAvailable = getValidLicensesForEntity(project).any {
                            it.licensedFeatures?.any{ importFeatures.contains(it.featureId) }
                        }

                        if (isImportFeatureAvailable) {
                            importMapperAvailable = true
                            break
                        }
                    }
                }
            }
        }

        return importMapperAvailable
    }

    /**
     * Check if some features are allowed in licenses.
     * Allow override for super user or higher
     * @param licenses
     * @param toCheckFeatureIds
     * @return
     */
    Map<String, Boolean> featuresAllowedByLicenses(List<License> licenses, List<String> toCheckFeatureIds, User user = null) {
        if (!(licenses?.size() > 0) || !(toCheckFeatureIds?.size() > 0)) {
            return [:]
        }

        user = user ?: userService.getCurrentUser()
        boolean isSuperUser = userService.getSuperUser(user) ?: false
        Map<String, Boolean> featuresAllowed = [:]
        List<String> licensedFeatureIds = getFeatures(licenses)?.collect { it.featureId }

        toCheckFeatureIds.each { String featureId ->
            if (featureId) {
                boolean isAllowed = isSuperUser || licensedFeatureIds?.contains(featureId)
                featuresAllowed.put(featureId, isAllowed)
            }
        }

        return featuresAllowed
    }

    // Note that this method should be only used for specifically those featureIds that are defined to be user-based by the management
    def featuresAllowedByCurrentUserOrProject(Entity parentEntity, List<String> featureIds, User u = null, Boolean onlyCurrentProject = false, Boolean checkFeaturesForAdmins = false) {
        long now = System.currentTimeMillis()
        Map<String, Boolean> featuresAllowed = [:]
        User user = u ?: userService.getCurrentUser()

        if (!user) {
            log.warn("Cannot determine a user for ${featureIds} features.")
            return featuresAllowed
        }

        getNoLicenseFeatures().each { String noLicenseFeature ->
            featuresAllowed.put(noLicenseFeature, Boolean.TRUE)
        }

        if (featureIds) {
            if (!checkFeaturesForAdmins && userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)) {
                featureIds.each { String featureId ->
                    featuresAllowed.put((featureId), Boolean.TRUE)
                }
            } else {
                List<String> allowedFeatures = []

                if (parentEntity) {
                    parentEntity.features?.each { Feature f ->
                        if (featureIds.contains(f.featureId) && !allowedFeatures.contains(f.featureId)) {
                            allowedFeatures.add(f.featureId)
                        }
                    }
                }

                if (allowedFeatures.size() == featureIds.size()) {
                    featureIds.each { String featureId ->
                        featuresAllowed.put((featureId), Boolean.TRUE)
                    }
                } else if (!onlyCurrentProject) {
                    List<Feature> features = userService.getLicenses(checkFeaturesForAdmins, user)?.collect({ it.licensedFeatures })?.flatten()
                    List<String> ids = features?.collect({ it?.featureId })?.unique()

                    if (ids) {
                        featureIds.findAll({ !allowedFeatures.contains(it) }).each { String featureId ->
                            if (ids.contains(featureId)) {
                                if (!allowedFeatures.contains(featureId)) {
                                    allowedFeatures.add(featureId)
                                }
                            }
                        }
                    }

                    featureIds.each { String featureId ->
                        if (allowedFeatures.contains(featureId)) {
                            featuresAllowed.put((featureId), Boolean.TRUE)
                        } else {
                            featuresAllowed.put((featureId), Boolean.FALSE)
                        }
                    }
                } else {
                    featureIds.each { String featureId ->
                        if (allowedFeatures.contains(featureId)) {
                            featuresAllowed.put((featureId), Boolean.TRUE)
                        } else {
                            featuresAllowed.put((featureId), Boolean.FALSE)
                        }
                    }
                }
            }
        }
        log.info("${user.username} LICENSED FEATURES RESOLVED (${System.currentTimeMillis() - now}ms): Features allowed by user or project: ${featuresAllowed}")
        return featuresAllowed
    }

    Map<String, Boolean> featuresAllowedByCurrentUserOrProject(Entity parentEntity, List<String> featureIds, User user = null,
                                                               List<Feature> featuresAvailableForEntity, List<License> availableLicenses) {
        long now = System.currentTimeMillis()
        Map<String, Boolean> featuresAllowed = [:]

        getNoLicenseFeatures().each { String noLicenseFeature ->
            featuresAllowed.put(noLicenseFeature, Boolean.TRUE)
        }

        if (featureIds) {
            if (userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)) {
                featureIds.each { String featureId ->
                    featuresAllowed.put((featureId), Boolean.TRUE)
                }
            } else {
                Set<String> allowedFeatures = []

                if (parentEntity) {
                    featuresAvailableForEntity.each { Feature f ->
                        if (featureIds.contains(f.featureId)) {
                            allowedFeatures.add(f.featureId)
                        }
                    }
                }

                if (allowedFeatures.size() == featureIds.size()) {
                    featureIds.each { String featureId ->
                        featuresAllowed.put((featureId), Boolean.TRUE)
                    }
                } else {
                    List<Feature> features = getLicenses(false, user, availableLicenses)?.collect({ it.licensedFeatures })?.flatten()
                    Set<String> ids = features?.collect{ it?.featureId } as Set

                    if (ids) {
                        featureIds.findAll{ !allowedFeatures.contains(it) }.each { String featureId ->
                            if (ids.contains(featureId)) {
                                if (!allowedFeatures.contains(featureId)) {
                                    allowedFeatures.add(featureId)
                                }
                            }
                        }
                    }

                    featureIds.each { String featureId ->
                        if (allowedFeatures.contains(featureId)) {
                            featuresAllowed.put((featureId), Boolean.TRUE)
                        } else {
                            featuresAllowed.put((featureId), Boolean.FALSE)
                        }
                    }
                }
            }
        }

        log.info("${user.username} LICENSED FEATURES RESOLVED (${System.currentTimeMillis() - now}ms): Features allowed by user or project: ${featuresAllowed}")
        return featuresAllowed
    }

    static Set<String> getNoLicenseFeatures() {
        return NO_LICENSE_FEATURES
    }

    def getLicensesNotUsedBetweenInDays(int startDate, int endDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        List<String> licenses = []

        Date currentDate = new Date()
        Date currentMinusStartDate = DateUtils.addDays(currentDate, startDate)
        Date currentMinusEndDate = DateUtils.addDays(currentDate, endDate)
        List<Document> licensesNotUsedInBetweenDays = License.collection.find([$and: [[dateCreated: [$lte: currentMinusStartDate, $gte: currentMinusEndDate], validUntil: [$gte: currentDate], type: [$in: [Constants.LicenseType.PRODUCTION.toString(), Constants.LicenseType.EDUCATION.toString()]], addon: [$ne: true], renewalStatus: "autoRenewal"],
                                                                                      [$or: [[licensedEntityIds: null], [licensedEntityIds: []], [licensedEntityIds: [$exists: false]]]],
                                                                                      [$or: [[removedEntityIds: null], [removedEntityIds: []], [removedEntityIds: [$exists: false]]]]]], [name: 1, dateCreated: 1, contractNumber: 1, clientNumber: 1, validUntil: 1])?.toList()

        if (licensesNotUsedInBetweenDays) {
            licensesNotUsedInBetweenDays.each { Document license ->
                String contract = license.contractNumber ? "C${license.contractNumber} - " : ""
                String client = license.clientNumber ? " - Client Number: ${license.clientNumber}" : ""
                String licenseString = "${contract}${license.name} ${client} (Date created: ${dateFormat.format((Date) license.dateCreated)} - Renewal date: ${dateFormat.format((Date) license.validUntil)})"
                licenses.add(licenseString)
            }
        }
        return licenses
    }

    def getLicensesNotUsedInDays(int days) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        List<String> licenses = []

        Date currentDate = new Date()
        Date currentMinusDays = new Date()
        currentMinusDays = DateUtils.addDays(currentMinusDays, days)
        List<Document> licensesNotUsedInDays = License.collection.find([$and: [[dateCreated: [$lte: currentMinusDays], validUntil: [$gte: currentDate], type: [$in: [Constants.LicenseType.PRODUCTION.toString(), Constants.LicenseType.EDUCATION.toString()]], addon: [$ne: true], renewalStatus: "autoRenewal"],
                                                                               [$or: [[licensedEntityIds: null], [licensedEntityIds: []], [licensedEntityIds: [$exists: false]]]],
                                                                               [$or: [[removedEntityIds: null], [removedEntityIds: []], [removedEntityIds: [$exists: false]]]]]], [name: 1, dateCreated: 1, contractNumber: 1, clientNumber: 1, validUntil: 1])?.toList()

        if (licensesNotUsedInDays) {
            licensesNotUsedInDays.each { Document license ->
                String contract = license.contractNumber ? "C${license.contractNumber} - " : ""
                String client = license.clientNumber ? " - Client Number: ${license.clientNumber}" : ""
                String licenseString = "${contract}${license.name} ${client} (Date created: ${dateFormat.format((Date) license.dateCreated)} - Renewal date: ${dateFormat.format((Date) license.validUntil)})"
                licenses.add(licenseString)
            }
        }
        return licenses
    }

    def getLicenseNamesForEntityId(ObjectId entityId) {
        List<String> licenseNames = []

        if (entityId) {
            BasicDBObject filter = new BasicDBObject("\$or", [[licensedEntityIds: [$in: [entityId]]], [licensedEntityIds: [$in: [entityId.toString()]]]])
            licenseNames = License.collection.distinct("name", filter, String.class)?.toList()
        }
        return licenseNames
    }

    def getAddOnLicenses() {
        List<Document> licenses = License.collection.find([addon: true], [name: 1, maxEntities: 1, licensedEntityIds: 1, validUntil: 1]).toList()
        return licenses
    }

    def getCommercialLicenseWithTraininngUnused(int days) {

        Date currentMinusDays = new Date()
        currentMinusDays = DateUtils.addDays(currentMinusDays, days)
        List<Document> licenses = License.collection.find([dateCreated: [$lte: currentMinusDays], validUntil: [$gte: new Date()], type: [$in: [Constants.LicenseType.PRODUCTION.toString(), Constants.LicenseType.EDUCATION.toString()]], addon: [$ne: true], trainingTaken: [$ne: true]], [name: 1, licenseUserIds: 1]).toList()
        return licenses
    }

    def getPlanetaryLicenseForCountryOrState(String countryId, String stateId = null, Map projection = [:]) {
        BasicDBObject filter = new BasicDBObject()
        if (stateId) {
            filter = [$or: [[conditionalStates: [$in: [stateId]]], [conditionalCountries: [$exists: false], conditionalStates: [$exists: false]], [conditionalCountries: [$in: [countryId]], conditionalStates: [$exists: false]]], planetary: true]
        } else {
            filter = [$or: [[conditionalCountries: [$in: [countryId]]], [conditionalCountries: [$exists: false]]], planetary: true]
        }
        License planetaryLicense = License.collection.findOne(filter, projection) as License
        return planetaryLicense
    }

    def findValidLicenseFromUserLicenseIds(List<String> licenseIds, String userId) {
        if (licenseIds && userId) {
            def licenseValid = License.collection.find([validUntil: [$gte: new Date()], active: true, _id: [$in: [DomainObjectUtil.stringsToObjectIds(licenseIds)]], type: Constants.LicenseType.PRODUCTION.toString(), $or: [[licenseUserIds: [$in: [DomainObjectUtil.stringToObjectId(userId)]]], [managerIds: DomainObjectUtil.stringToObjectId(userId)]]])?.toList()
            if (licenseValid) {
                return true
            }
        }
        return false
    }

    private List<Document> licensesWithNoUse(String autoRenewalStat) {
        return License.collection.find([validUntil: [$gte: new Date()], renewalStatus: autoRenewalStat, $or: [[licensedEntityIds: [$exists: false]], [licensedEntityIds: null], [licensedEntityIds: []]]])?.toList()
    }


    def getFixedLicenseWithNoUseReport() {
        List<String> licensesAsString = []
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        List<Document> licenses = licensesWithNoUse("fixedPeriod")
        licenses?.each { Document license ->
            String contract = license.contractNumber ? "C${license.contractNumber} - " : ""
            String client = license.clientNumber ? " - Client Number: ${license.clientNumber}" : ""
            String licenseString = "${contract}${license.name} ${client} (Date created: ${dateFormat.format((Date) license.dateCreated)} - Renewal date: ${dateFormat.format((Date) license.validUntil)})"
            licensesAsString.add(licenseString)
        }

        return licensesAsString
    }

    List<Indicator> getLicensedIndicatorsByEntityClassAndIndicatorUseFromLicenseList(List<License> licenses, String entityClass, String indicatorUse) {
        List<Indicator> indicators = []
        if (licenses && entityClass && indicatorUse) {
            try {
                Set<String> indicatorIds = []
                licenses.each { indicatorIds += it.licensedIndicatorIds }

                if (indicatorIds) {
                    indicators = indicatorService.getIndicatorsByEntityClassAndIndicatorUse(entityClass, indicatorUse, indicatorIds as List<String>)
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in getLicensedIndicatorsFromLicenseList", e)
                flashService.setErrorAlert("Error occurred in getLicensedIndicatorsFromLicenseList: ${e.getMessage()}", true)
            }
        }
        return indicators
    }

    List<License> filterInactivatedFloatingLicenseForUser(List<License> licenses, User user = null) {
        try {
            if (licenses) {
                if (!user) {
                    user = userService.getCurrentUser()
                }
                return licenses.findAll { it.isFloatingLicense && !it.isUserFloating(user) }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in filterInactivatedFloatingLicenseForUser", e)
            flashService.setErrorAlert("Error occurred in filterInactivatedFloatingLicenseForUser: ${e.getMessage()}", true)
        }
        return []
    }

    List<String> getStatesWithPlanetary() {
        List<String> states = []
        try {
            BasicDBObject filter = new BasicDBObject("planetary", true)
            states = License.collection.distinct("conditionalStates", filter, String.class)?.toList()
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getStatesWithPlanetary", e)
            flashService.setErrorAlert("Error occurred in getStatesWithPlanetary: ${e.getMessage()}", true)
        }
        return states
    }

    /**
     *  Filter indicatorQuery / Section / Question by licenseKey and/or requiredLicenseKeys and/or disableForLicenseKeys
     *  In this method, it's referred to as 'object'
     *
     * @param objects can be list of indicatorQuery / Section / Question
     * @param licensedFeatureIds
     * @param entity pass entity if necessary to get the licensedFeatureIds
     * @return list of objects that passes the filter licenseKey, requiredLicenseKeys, and disableForLicenseKeys (if any)
     */
    public <T> List<T> doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys(List<T> objects, List<String> licensedFeatureIds, Entity entity = null) {
        try {
            if (objects) {
                if (!licensedFeatureIds && entity) {
                    licensedFeatureIds = entity.features?.collect({ it.featureId })
                }
                objects = objects.findAll { T object ->
                    // take the object if it's licensed
                    boolean licensed = isQueryOrSectionOrQuestionLicensed(object, licensedFeatureIds)
                    return licensed ?: (object.hideOnly ?: object.noLicenseShowAsDisabled)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys", e)
            flashService.setErrorAlert("Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys: ${e.getMessage()}", true)
        }
        return objects
    }

    /**
     * Check if indicatorQuery / Section / Question has licenseKey and/or requiredLicenseKeys and/or disableForLicenseKeys and is it licensed.
     * In this method, it's referred to as 'object'
     *
     * @param objects
     * @param licensedFeatureIds
     * @param entity pass entity if necessary to get the licensedFeatureIds
     * @return
     */
    boolean isQueryOrSectionOrQuestionLicensed(object, List<String> licensedIds, Entity entity = null) {
        Set<String> licensedFeatureIds = []
        if (licensedIds) {
            licensedFeatureIds.addAll(licensedIds)
        }
        try {
            if (object) {
                if (!licensedFeatureIds && entity) {
                    licensedFeatureIds = entity.features?.collect({ it.featureId }) as Set
                }

                // Object isn't licensed if it requires licenseKey and licensedFeatureIds list doesn't have
                if (object?.licenseKey) {
                    if (!licensedFeatureIds.contains(object?.licenseKey)) {
                        return false
                    }
                }

                // Object isn't licensed if it has list of requiredLicenseKeys and licensedFeatureIds list doesn't have any of them
                if (object?.requiredLicenseKeys) {
                    if (!CollectionUtils.containsAny(licensedFeatureIds, object?.requiredLicenseKeys as Set<String>)) {
                        return false
                    }
                }

                /*
                    Object isn't licensed if it has list of disableForLicenseKeys and licensedFeatureIds list has one of them.
                    Rule of thumb: disableForLicenseKeys takes priority, i.e. even though project has licenseKey to show,
                    object is still not licensed if project has licenseKey in disableForLicenseKeys list.
                 */
                if (object?.disableForLicenseKeys) {
                    for (String licenseKey in object?.disableForLicenseKeys) {
                        if (licenseKey && licensedFeatureIds.contains(licenseKey)) {
                            return false
                        }
                    }
                }
                // object is licensed if it passes all the licenseKey filter (if any) or it doesn't have any filter.
                return true
            } else {
                loggerUtil.warn(log, "Error in isQueryOrSectionOrQuestionLicensed: Method is called but no object is passed for evaluation >>> returning false now.")
                flashService.setErrorAlert("Error in isQueryOrSectionOrQuestionLicensed: Method is called but no object is passed for evaluation >>> returning false now", true)
                return false
            }
        } catch (e) {
            loggerUtil.error(log, "Error in isQueryOrSectionOrQuestionLicensed", e)
            flashService.setErrorAlert("Error in isQueryOrSectionOrQuestionLicensed: ${e.getMessage()}, returning false now", true)
            return false
        }
    }

    /**
     * Put the licensed status of a question to a map. Possibly can put to a second map for questions that are not licensed
     * but to be shown as disabled
     *
     * @param question
     * @param questionLicensedMap a map <questionId, boolean (whether question is licensed)>
     * @param licensedFeatureIds list of featureIds that the project is licensed with
     * @param indicator
     * @param noLicenseShowAsDisabledMap need to pass in a second map if question need to be shown as disabled if not licensed
     */
    void putLicensedStatusOfQuestionToMap(Question question, Map<String, Boolean> questionLicensedMap, List<String> licensedFeatureIds, Indicator indicator, Map<String, Boolean> noLicenseShowAsDisabledMap = null) {
        try {
            if (questionLicensedMap != null && licensedFeatureIds != null && question) {
                if (question.licenseKey || question.requiredLicenseKeys || question.disableForLicenseKeys) {
                    boolean isLicensed = isQueryOrSectionOrQuestionLicensed(question, licensedFeatureIds) ?: question.noLicenseKeyIndicatorIds?.contains(indicator?.indicatorId) ?: false

                    // for places that question need to show as disabled if not licensed, need to pass in a second map to evaluate that condition
                    if (!isLicensed && question.noLicenseShowAsDisabled && noLicenseShowAsDisabledMap != null) {
                        isLicensed = true
                        noLicenseShowAsDisabledMap.put(question.questionId, true)
                    }
                    questionLicensedMap.put(question.questionId, isLicensed)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in putLicensedStatusOfQuestionToMap, questionId ${question?.questionId}", e)
            flashService.setErrorAlert("Error in putLicensedStatusOfQuestionToMap, questionId ${question?.questionId}: ${e.message}", true)
        }
    }

    /**
     * Return a list of featureIds (license keys) that the project is licensed with
     * @param entity
     * @return
     */
    List<String> getLicensedFeatureIdsOfEntity(Entity entity) {
        return entity?.features?.collect { it.featureId }
    }

    /**
     *  Return a list of featureIds (license keys) that the account (company) is licensed with
     * @param account
     * @return
     */
    List<String> getLicensedFeatureIdsFromAccount(Account account) {
        List<String> licensedFeatureIds = []
        accountService.getLicenses(account?.licenseIds).each { License license ->
            license.licensedFeatures.each {
                licensedFeatureIds.add(it.featureId)
            }
        }
        return licensedFeatureIds
    }

    /**
     * Check if has license to access ecoinvent data freely (no limit)
     * @param licensedFeatureIds
     * @return
     */
    boolean hasEcoinventUncappedLicensed(List<String> licensedFeatureIds) {
        return licensedFeatureIds?.find({ Feature.ECOINVENT_UNCAPPED.equalsIgnoreCase(it) }) ? true : false
    }

    List<License> getExpiredOperatingLicenses(Entity entity, List<License> expiredLicenses) {
        List<License> licenses = []

        if (expiredLicenses) {
            for (License license in expiredLicenses) {
                List<Indicator> licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    List<Indicator> indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, entity)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (com.bionova.optimi.construction.Constants.IndicatorUse.OPERATING.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }

        return licenses
    }

    List<License> getExpiredDesignLicenses(Entity entity, List<License> expiredLicenses) {
        List<License> licenses = []

        if (expiredLicenses) {
            for (License license in expiredLicenses) {
                List<Indicator> licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    List<Indicator> indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, entity)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (com.bionova.optimi.construction.Constants.IndicatorUse.DESIGN.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }

        return licenses
    }

    List<Feature> getLicensedFeatures(List<String> licensedFeatureIds) {
        List<Feature> features

        if (licensedFeatureIds) {
            features = featureService.getFeaturesByIds(licensedFeatureIds)
        }

        return features
    }

    List<License> getManagedLicenses(User user, List<License> validLicenses) {
        List<License> licenses = []

        if (userService.getSuperUser(user)) {
            licenses = validLicenses
        } else if (user.managedLicenseIds) {
            licenses = validLicenses.findAll { it.id in DomainObjectUtil.stringsToObjectIds(user.managedLicenseIds) }
        }

        return licenses
    }

    List<LicenseDTO> getManagedLicenseDTOs(User user, List<LicenseDTO> validLicenses) {
        List<LicenseDTO> licenses = []

        if (userService.getSuperUser(user)) {
            licenses = validLicenses
        } else if (user.managedLicenseIds) {
            licenses = validLicenses.findAll { it.id in DomainObjectUtil.stringsToObjectIds(user.managedLicenseIds) }
        }

        return licenses
    }

    List<License> getUsableLicenses(User user, List<License> validLicenses) {
        List<License> usableLicenses = []

        if (userService.getSuperUser(user) || userService.getDataManager(user)) {
            usableLicenses = validLicenses
        } else if (user.usableLicenseIds) {
            usableLicenses = validLicenses.findAll { it.id in DomainObjectUtil.stringsToObjectIds(user.usableLicenseIds) }
        }
        return usableLicenses
    }

    List<LicenseDTO> getUsableLicenseDTOs(User user, List<LicenseDTO> validLicenses) {
        List<LicenseDTO> usableLicenses = []

        if (userService.getSuperUser(user) || userService.getDataManager(user)) {
            usableLicenses = validLicenses
        } else if (user.usableLicenseIds) {
            usableLicenses = validLicenses.findAll { it.id in DomainObjectUtil.stringsToObjectIds(user.usableLicenseIds) }
        }

        return usableLicenses
    }

    List<License> getUsableAndManagedLicenses(User user, List<License> validLicenses) {
        List<License> licenses = []
        def managedLicenses = getManagedLicenses(user, validLicenses)

        if (managedLicenses) {
            licenses.addAll(managedLicenses)
        }
        def usableLicenses = getUsableLicenses(user, validLicenses)

        if(usableLicenses) {
            licenses.addAll(usableLicenses)
        }
        return licenses?.unique({it.id})
    }

    List<LicenseDTO> getUsableAndManagedLicenseDTOs(User user, List<LicenseDTO> relevantLicenses) {
        List<LicenseDTO> licenses = []
        List<String> usableLicenseIds = user.usableLicenseIds ?: []
        List<String> managedLicenseIds = user.managedLicenseIds ?: []

        if (userService.getSuperUser(user) || userService.getDataManager(user)) {
            licenses = relevantLicenses
        } else if (usableLicenseIds || managedLicenseIds) {
            licenses = relevantLicenses.findAll { it.id in DomainObjectUtil.stringsToObjectIds((usableLicenseIds + managedLicenseIds).unique()) }
        }

        return licenses
    }

    List<License> getLicenses(Boolean skipAdminLicensed = false, User user, List<License> validLicenses) {
        List<License> licenses = []

        if (!skipAdminLicensed && (userService.getSuperUser(user) || userService.getDataManager(user))) {
            licenses = getValidLicenses()
        } else {
            def allUsableLicenseIds = []

            if (user.licenseIds && !user.licenseIds.isEmpty()) {
                allUsableLicenseIds.addAll(user.licenseIds)
            }

            if (user.usableLicenseIds && !user.usableLicenseIds.isEmpty()) {
                allUsableLicenseIds.addAll(user.usableLicenseIds)
            }

            if (!allUsableLicenseIds.isEmpty()) {
                licenses = License.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(allUsableLicenseIds))
            }
        }
        return licenses
    }

    List<License> getValidLicensesForEntity(Entity entity) {
        User user = userService.getCurrentUser(true)
        Date currentDate = new Date()
        List<License> licenses
        Entity parent

        if (entity?.parentEntityId) {
            parent = entity.getParentById()
        } else {
            parent = entity
        }

        if (parent) {
            if (user) {
                licenses = License.withCriteria {
                    eq('frozen', null)
                    lte('validFrom', currentDate)
                    gte('validUntil', currentDate)
                    and {
                        or {
                            eq('compatibleEntityClasses', null)
                            eq('compatibleEntityClasses', parent.entityClass)
                        }
                        or {
                            eq('licensedEntityIds', parent.id)
                            eq('licensedEntityIds', parent.id.toString())
                        }
                    }
                }

                if (licenses && !userService.getSuperUser(user)) {
                    // Super user and up gets floating licenses as a valid license even if not checked in, unable to query for this inside or since no hibernate plugin left join
                    licenses = licenses.findAll({ !it.floatingLicense || it.floatingLicense.equals(com.bionova.optimi.core.Constants.FloatingLicense.NO.toString()) || it.isUserFloating(user) })
                }
            } else {
                // Cant get any floating licenses since no user, most likely using API
                licenses = License.withCriteria {
                    eq('frozen', null)
                    lte('validFrom', currentDate)
                    gte('validUntil', currentDate)
                    and {
                        or {
                            eq('floatingLicense', null)
                            eq('floatingLicense', com.bionova.optimi.core.Constants.FloatingLicense.NO.toString())
                        }
                        or {
                            eq('compatibleEntityClasses', null)
                            eq('compatibleEntityClasses', parent.entityClass)
                        }
                        or {
                            eq('licensedEntityIds', parent.id)
                            eq('licensedEntityIds', parent.id.toString())
                        }
                    }
                }
            }
        }

        if (licenses) {
            String parentType = parent.typeResourceId
            String parentCountry = parent.countryResourceResourceId
            LocalDateTime currentDateLocalDateTime = DateUtil.dateToLocalDatetime(currentDate)
            List<License> expiredLicenses = []

            licenses.each {
                if ((it.type.equals(com.bionova.optimi.core.Constants.LicenseType.EDUCATION.toString()) || it.type.equals(com.bionova.optimi.core.Constants.LicenseType.TRIAL.toString()))) {
                    LocalDateTime date = DateUtil.dateToLocalDatetime(it.getChangeHistory(parent)?.dateAdded)

                    if (date) {
                        if (it.trialLength && Duration.between(date, currentDateLocalDateTime).toDays() > it.trialLength) {
                            expiredLicenses.add(it)
                        }
                    } else {
                        expiredLicenses.add(it)
                    }
                } else if (it.freeForAllEntities) {
                    if ((it.conditionalEntityClasses && !it.conditionalEntityClasses.contains(parent.entityClass)) || (it.conditionalBuildingTypes && !it.conditionalBuildingTypes.contains(parentType)) || (it.conditionalCountries && !it.conditionalCountries.contains(parentCountry))) {
                        expiredLicenses.add(it)
                    }
                }
            }

            if (expiredLicenses) {
                licenses.removeAll(expiredLicenses)
            }
        }

        return licenses?.unique({ it.id })
    }

    List<License> getExpiredLicensesForEntity(Entity entity) {
        Date currentDate = new Date()

        List<License> expiredLicenses = []
        Entity parent

        if (entity?.parentEntityId) {
            parent = entity.getParentById()
        } else {
            parent = entity
        }

        if (parent) {
            String parentType = parent.typeResourceId
            String parentCountry = parent.countryResourceResourceId

            List<License> licenses = License.collection.find([$and: [[$or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [parent.entityClass]]]]], [$or: [[licensedEntityIds: [$in: [parent.id]]], [licensedEntityIds: [$in: [parent.id.toString()]]]]]]])?.collect({
                it as License
            })

            licenses?.each({
                if (it.type.equals(com.bionova.optimi.core.Constants.LicenseType.EDUCATION.toString()) && it.trialLength && Duration.between(DateUtil.dateToLocalDatetime(it.getChangeHistory(parent)?.dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() > it.trialLength) {
                    expiredLicenses.add(it)
                } else if (it.type.equals(com.bionova.optimi.core.Constants.LicenseType.TRIAL.toString()) && it.trialLength && Duration.between(DateUtil.dateToLocalDatetime(it.getChangeHistory(parent)?.dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() > it.trialLength) {
                    expiredLicenses.add(it)
                } else if (it.freeForAllEntities) {
                    if ((it.conditionalEntityClasses && !it.conditionalEntityClasses.contains(parent.entityClass)) || (it.conditionalBuildingTypes && !it.conditionalBuildingTypes.contains(parentType)) || (it.conditionalCountries && !it.conditionalCountries.contains(parentCountry))) {
                        expiredLicenses.add(it)
                    } else if (!it.valid) {
                        expiredLicenses.add(it)
                    }
                } else if (!it.valid) {
                    expiredLicenses.add(it)
                }
            })
        }

        return expiredLicenses
    }

    Boolean getReportIndicatorLicensed(Entity entity, List<License> validLicenses)  {
        boolean isLicensed = false

        if (validLicenses) {
            for (License license in validLicenses) {
                List<Indicator> indicators = license.getLicensedIndicators()

                if (indicators) {
                    for (Indicator indicator in indicators) {
                        if ("report".equals(indicator.indicatorUse)) {
                            isLicensed = true
                            break
                        }
                    }
                }

                if (isLicensed) {
                    break
                }
            }
        }

        return isLicensed
    }

    List<License> findLicensesByFeature(Feature feature){
        List<License> licenses = []

        if (feature) {
            licenses = License.collection.find(["licensedFeatureIds": feature.id.toString()]).collect{it as  License}
        }

        return licenses
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getProjectionLicenses() {
        User user = userService.getCurrentUser(Boolean.TRUE)
        if (user) {
            if (userService.getSuperUser(user) || userService.getSalesView(user) || userService.getConsultant(user)) {
                return License.collection.find([:],
                        [entityChangeHistories:1, licensedIndicatorIds:1, compatibleEntityClasses:1, isFloatingLicense:1, floatingLicenseCheckedInUsers:1,
                         licensedEntityIds:1, type:1, validUntil:1, name:1, frozen:1, trialLength:1, validFrom:1,
                ])?.toList(License.class)
            } else {
                return userService.getManagedLicenses(user)
            }
        }
    }
}
