/*
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBObject
import grails.validation.Validateable
import groovy.transform.CompileStatic
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.bson.types.ObjectId

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * @author Pasi-Markus Mäkelä/ SoftPM
 */
class License implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String name
    String description
    String type
    String licenseKey
    String existingLicenseKey
    Date validFrom
    Date validUntil
    List licensedFeatureIds = []
    List licensedEntityIds = []
    List removedEntityIds = []
    List previousLicensedEntityIds = []
    List licensedIndicatorIds = []
    List readonlyIndicatorIds = []
    Map licensedUserIdsMap = [:]
    Integer maxEntities
    String managerId // Deprecated, use managerIds
    List<String> managerIds
    List<String> compatibleEntityClasses
    List licenseUserIds
    Boolean freeForAllEntities
    Date dateCreated
    Date lastUpdated
    String lastUpdaterName
    Boolean licenseManagerCanAddLicenseUserIds
    List<LicenseEntityChangeHistory> entityChangeHistories
    Integer trialLength
    String renewalStatus
    String renewalDate
    LicenseJoinCode joinCode
    Boolean projectBased
    Boolean userBased
    Date dateKeyCreated
    List<Map<String, Object>> changeHistories
    Boolean openTrial
    String apiIndicatorId
    String contractNumber
    String clientNumber

    //It floats
    String floatingLicense // Constants floatingLicense
    String floatingLicenseScope
    Integer floatingLicenseMaxUsers
    List<FloatingLicenseUser> floatingLicenseCheckedInUsers

    Boolean preventAddToProject
    Boolean frozen
    List licenseTemplateIds = []
    List<String> licensedWorkFlowIds
    List<String> countries


    // For conditional free for entity check
    List<String> conditionalCountries
    List<String> conditionalStates
    List<String> conditionalEntityClasses
    List<String> conditionalBuildingTypes

    Boolean addon
    Boolean trainingTaken

    //for planetary
    Boolean planetary
    byte[] licenseImage
    // <accountId, templateIds> HUOM: this map might include deleted templates. These templates are marked with field 'deleted' so it won't be found in sw, but the id still exists in this map in db until user link templates again
    Map<String, List<String>> projectTemplateIdsByAccount
    Map<String, String> defaultProjectTemplateIdByAccount
    List<String> publicProjectTemplateIds
    String defaultPublicProjectTemplateId

    String tierId

    static final List<String> renewalStatusChoices = ['autoRenewal', 'fixedPeriod']

    static mapping = {
        type index: true
        openTrial index: true
        trialLength index: true
        validFrom index: true
        validUntil index: true
        frozen index: true
    }

    static embedded = [
            'entityChangeHistories', 'joinCode', 'floatingLicenseCheckedInUsers']
    // static auditable = [handlersOnly:true]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
        licensedFeatureIds nullable: true
        licensedEntityIds nullable: true
        previousLicensedEntityIds nullable: true
        licensedIndicatorIds nullable: true
        licensedUserIdsMap nullable: true
        maxEntities nullable: true
        managerId nullable: true
        managerIds nullable: true
        freeForAllEntities nullable: true
        licenseUserIds nullable: true
        type nullable: false, blank: false
        entityChangeHistories nullable: true
        removedEntityIds nullable: true
        licenseManagerCanAddLicenseUserIds nullable: true
        trialLength(validator: { value, obj ->
            if ((Constants.LicenseType.TRIAL.toString().equals(obj.type) || Constants.LicenseType.EDUCATION.toString().equals(obj.type)) && !value) {
                return ['admin.license.trialLength.nullable']
            }
        }, nullable: true, min: 1, max: 365)
        lastUpdaterName nullable: true, blank: true
        renewalStatus nullable: true, inList: renewalStatusChoices
        renewalDate nullable: true
        licenseKey nullable: true, unique: true
        validFrom nullable: false, blank: false
        validUntil nullable: false, blank: false
        joinCode nullable: true
        projectBased(validator: { value, obj ->
            if (obj.isFloatingLicense && value) {
                return "admin.license.floating.projectBased"
            }
        }, nullable: true)
        userBased nullable: true
        dateKeyCreated nullable: true
        compatibleEntityClasses nullable: false
        changeHistories nullable: true
        openTrial nullable: true
        licenseTemplateIds nullable: true
        apiIndicatorId nullable: true
        countries nullable: true
        licensedWorkFlowIds nullable: true
        floatingLicense nullable: true
        floatingLicenseScope nullable: true
        preventAddToProject nullable: true
        frozen nullable: true
        floatingLicenseCheckedInUsers nullable: true
        floatingLicenseMaxUsers(validator: { value, obj ->
            if (obj.isFloatingLicense && !value) {
                return "admin.license.floating.maxusers.nullable"
            }
        }, nullable: true)
        addon nullable: true
        readonlyIndicatorIds nullable: true
        trainingTaken nullable: true
        contractNumber nullable: true
        clientNumber nullable: true
        conditionalCountries nullable: true
        conditionalStates nullable: true
        conditionalEntityClasses nullable: true
        conditionalBuildingTypes nullable: true
        existingLicenseKey nullable: true
        planetary nullable: true
        licenseImage nullable: true
        projectTemplateIdsByAccount nullable: true
        defaultProjectTemplateIdByAccount nullable: true
        publicProjectTemplateIds nullable: true
        defaultPublicProjectTemplateId nullable: true
        tierId nullable: true
    }

    static transients = [
            "licensedEntities",
            "removedEntities",
            "licensedIndicators",
            "licensedFeatures",
            "licensedUsers",
            "maxEntitiesReached",
            "licensedEntityCount",
            "licensedIndicatorCount",
            "licensedFeatureCount",
            "licensedUserCount",
            "managers",
            "licenseUsers",
            "licenseInvitations",
            "expired",
            "valid",
            "nextRenewalDate",
            "daysToNextRenewal",
            "accounts",
            "educationLicenseKeyValid",
            "expiringToday",
            "expiringTomorrow",
            "licenseTemplates",
            "licensedWorkFlows",
            "isFloatingLicense",
            "readonlyIndicators",
            "contractNumberForUI"
    ]

    transient indicatorService
    transient featureService
    transient userService
    transient accountService
    transient domainClassService
    transient licenseService
    transient queryService
    transient loggerUtil
    transient flashService
    transient projectTemplateService

    private void createChangeHistoryTransients() {
        changeHistories?.each {
            Map<String, Object> changes = it.get("changes")

            if (changes) {
                if (changes?.keySet()?.contains("licenseUserIds")) {
                    List<User> users = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(changes.get("licenseUserIds"))]], [username: 1])?.collect({it as User})
                    changes.put("licenseUsers", users.collect({ it.username }))
                    changes.remove("licenseUserIds")
                }
                def ids = changes.get("licensedUserIdsMap")?.keySet()?.toList()

                if (ids) {
                    List<User> users = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(ids)]], [username: 1])?.collect({it as User})
                    Map usersWithType = [:]

                    if (users) {
                        users.each { User u ->
                            if (u) {
                                usersWithType.put(u.username?.replaceAll(".", ""), changes.get("licensedUserIdsMap").get(u.id.toString()))
                            }
                        }
                    }

                    if (!usersWithType.isEmpty()) {
                        changes.put("licensedUsers", usersWithType)
                        changes.remove("licensedUserIdsMap")
                    }
                }

                if (changes?.keySet()?.contains("licensedIndicatorIds")) {
                    List<Indicator> indicators = []
                    if (changes.get("licensedIndicatorIds")) {
                        indicators = Indicator.collection
                                .find("indicatorId": [$in: changes.get("licensedIndicatorIds")], active: true)
                                .collect { it as Indicator }
                    }
                    changes.put("licensedIndicators", indicators.collect({it.localizedName}))
                    changes.remove("licensedIndicatorIds")
                }


                if (changes?.keySet()?.contains("licensedFeatureIds")) {
                    List<Feature> features = Feature.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(changes.get("licensedFeatureIds"))]],
                            [name: 1, featureClass: 1])?.collect({it as Feature})
                    changes.put("licensedFeatures", features.collect({it.formattedName}))
                    changes.remove("licensedFeatureIds")

                }

                if (changes?.keySet()?.contains("licenseTemplateIds")) {
                    List<LicenseTemplate> licenseTemplates = LicenseTemplate.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(changes.get("licenseTemplateIds"))]],
                            [name: 1])?.collect({it as LicenseTemplate})
                    changes.put("licenseTemplates", licenseTemplates.collect({it.name}))
                    changes.remove("licenseTemplateIds")

                }
            }
        }
    }

    def beforeInsert() {
        dateCreated = new Date()
        lastUpdated = dateCreated
        lastUpdaterName = userService.getCurrentUser()?.username

        if (licenseKey) {
            existingLicenseKey = licenseKey
            dateKeyCreated = new Date()

            if (Constants.LicenseType.TRIAL.toString().equals(type) || Constants.LicenseType.EDUCATION.toString().equals(type)) {
                Calendar calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 30)
                validUntil = calendar.getTime()
            }
        }

        if ("autoRenewal".equals(renewalStatus)) {
            validUntil = getNextRenewalDate()
        } else if ("fixedPeriod".equals(renewalStatus) || trialLength) {
            renewalDate = null
        }
    }

    def beforeUpdate() {
        lastUpdated = new Date()
        lastUpdaterName = userService.getCurrentUser()?.username

        if (licenseKey && !existingLicenseKey) {
            existingLicenseKey = licenseKey
            dateKeyCreated = new Date()

            if (Constants.LicenseType.TRIAL.toString().equals(type) || Constants.LicenseType.EDUCATION.toString().equals(type)) {
                Calendar calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 30)
                validUntil = calendar.getTime()
            }
        } else if (licenseKey && existingLicenseKey && !licenseKey.equals(existingLicenseKey)) {
            existingLicenseKey = licenseKey
            dateKeyCreated = new Date()
        } else if (!licenseKey) {
            existingLicenseKey = null
            dateKeyCreated = null
        }

        if ("autoRenewal".equals(renewalStatus)) {
            validUntil = getNextRenewalDate()
        } else if ("fixedPeriod".equals(renewalStatus) || trialLength) {
            renewalDate = null
        }
        // Add the changeHistory
        Document document = License.collection.findOne(["_id": this.id])

        if (document) {
            List<String> licenseProperties = domainClassService.getPersistentPropertyNamesForDomainClass(License.class)?.findAll({
                !"changeHistories".equals(it) && !"entityChangeHistories".equals(it)
            })

            License oldVersion = document as License
            Map<String, Object> changes = [:]

            licenseProperties?.each { String key ->
                def oldValue = oldVersion[key]
                def value = this[key]

                if ((oldValue && !oldValue.equals(value)) || (!oldValue && value)) {
                    if ("licenseImage".equals(key)) {
                        changes.put(key, "New license image")
                    } else {
                        changes.put(key, value)
                    }
                }
            }

            if (!changes.isEmpty()) {
                Map changeHistory = [:]
                changeHistory.changes = changes
                changeHistory.date = new Date()

                List newLicenseUserIds = changes.get("licenseUserIds")

                if (!lastUpdaterName && newLicenseUserIds && !newLicenseUserIds.isEmpty()) {
                    // Change is done programmatically so there is no last updater, but we can assume it was triggered by the latest license user.
                    def lastUpdaterId = newLicenseUserIds.last()
                    User u

                    if (lastUpdaterId instanceof String) {
                        u = User.get(DomainObjectUtil.stringToObjectId(lastUpdaterId))
                    } else {
                        u = User.get(lastUpdaterId)
                    }

                    if (u) {
                        changeHistory.username = u.username
                    }
                } else {
                    changeHistory.username = lastUpdaterName
                }

                if (changeHistories) {
                    changeHistories.add(changeHistory)

                    if (changeHistories.size() > 10) {
                        changeHistories = changeHistories.takeRight(10) // persist only latest 10 changes
                    }
                } else {
                    changeHistories = [changeHistory]
                }
                createChangeHistoryTransients()
            }
        }
    }

    def getLicensedUsers() {
        def usersWithType = [:]

        if (licensedUserIdsMap) {
            def users

            def ids = licensedUserIdsMap.keySet()?.toList()

            if (ids) {
                users = User.getAll(DomainObjectUtil.stringsToObjectIds(ids))

                if (users) {
                    users.each { User u ->
                        if (u) {
                            usersWithType.put(u, licensedUserIdsMap.get(u.id.toString()))
                        }
                    }
                }
            }
        }
        return usersWithType
    }

    def getLicenseInvitations() {
        return UserLicenseInvitation.collection.find([licenseId: id.toString()])?.collect({it as UserLicenseInvitation})
    }

    def getLicenseUsers() {
        List<User> users

        if (licenseUserIds) {
            users = User.getAll(DomainObjectUtil.stringsToObjectIds(licenseUserIds))
        }
        return users
    }

    def getLicenseUsernames() {
        List<String> usernames

        if (licenseUserIds) {
            BasicDBObject filter = new BasicDBObject("_id", [$in: DomainObjectUtil.stringsToObjectIds(licenseUserIds)])
            usernames = User.collection.distinct("username", filter, String.class)?.toList()
        }
        return usernames
    }

    @Deprecated
    /*def getManager() {
         User manager

         if (managerId) {
             manager = User.get(managerId)
         }
         return manager
     }*/

    //12536
    def getUserName(String managerId){
        User user
        user = userService.getUserById(managerId)
        return user?.username
    }

    //12536 + 15404
    def getManagers() {
        List<User> managers
        if (managerIds) {
            managers = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(managerIds)]])?.collect({
                it as User
            })
        }
        return managers
    }


    def getMaxEntitiesReached() {
        boolean maxEntitiesReached = false

        if (maxEntities && licensedEntityIds?.size() >= maxEntities) {
            maxEntitiesReached = true
        }
        return maxEntitiesReached
    }

    def getLicensedFeatures() {
        List<Feature> features

        if (licensedFeatureIds) {
            features = featureService.getFeaturesByIds(licensedFeatureIds)
        }
        return features
    }

    def getLicensedWorkFlows() {
        List<WorkFlow> workFlows

        if (licensedWorkFlowIds) {
            workFlows = queryService.getQueryByQueryId("workFlowQueryId")?.workFlowList?.findAll({ licensedWorkFlowIds.contains(it.workFlowId) })
        }
        return workFlows
    }

    def getLicenseTemplates() {
        List<LicenseTemplate> licenseTemplates

        if (licenseTemplateIds) {
            licenseTemplates = licenseService.getLicenseTemplatesByIds(licenseTemplateIds)
        }
        return licenseTemplates
    }

    def getLicensedEntities() {
        List<Entity> entities

        if (licensedEntityIds) {
            entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(licensedEntityIds)]])?.collect({
                it as Entity
            })
        }
        return entities
    }

    def getRemovedEntities() {
        def entities

        if (removedEntityIds) {
            entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(removedEntityIds)]])?.collect({
                it as Entity
            })
        }
        return entities
    }

    def getLicensedIndicators() {
        return indicatorService.getIndicatorsByIndicatorIds(licensedIndicatorIds, true)
    }

    def getReadonlyIndicators() {
        return indicatorService.getIndicatorsByIndicatorIds(readonlyIndicatorIds, true)
    }

    def getLicensedEntityCount() {
        int count

        if (licensedEntityIds) {
            count = licensedEntityIds.size()
        }
        return count
    }

    def getLicensedIndicatorCount() {
        def count = 0

        if (licensedIndicatorIds) {
            count = licensedIndicatorIds.size()
        }
        return count
    }

    def getLicensedFeatureCount() {
        def count = 0

        if (licensedFeatureIds) {
            count = licensedFeatureIds.size()
        }
        return count
    }

    def getLicensedUserCount() {
        def count = 0

        if (licensedUserIdsMap) {
            count = licensedUserIdsMap.keySet().size()
        }
        return count
    }

    def getExpired() {
        boolean expired = false
        Date currentDate = new Date()
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        currentDate = dateFormat.parse(dateFormat.format(currentDate))

        if (validUntil < currentDate) {
            expired = true
        }
        return expired
    }

    def getExpiringToday() {
        Boolean expired = Boolean.FALSE

        if (!"autoRenewal".equals(renewalStatus) && validUntil) {
            Date currentDate = new Date()
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sdf.format(currentDate), formatter), LocalDate.parse(sdf.format(validUntil), formatter))
            Integer days = daysBetween.toInteger()

            if (days == 0) {
                expired = Boolean.TRUE
            }
        }
        return expired
    }

    def getExpiringTomorrow() {
        Boolean expires = Boolean.FALSE

        if (!"autoRenewal".equals(renewalStatus) && validUntil) {
            Date currentDate = new Date()
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sdf.format(currentDate), formatter), LocalDate.parse(sdf.format(validUntil), formatter))
            Integer days = daysBetween.toInteger()

            if (days == 1) {
                expires = Boolean.TRUE
            }
        }
        return expires
    }

    def getValid() {
        Boolean valid = Boolean.FALSE

        if (!frozen) {
            Date currentDate = new Date()
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
            currentDate = dateFormat.parse(dateFormat.format(currentDate))

            if (isFloatingLicense) {
                User user = userService.getCurrentUser(true)

                if (user && (userService.getSuperUser(user) || floatingLicenseCheckedInUsers?.find({it.userId == user.id.toString()}))) {
                    if (validFrom && validUntil && validFrom <= currentDate && validUntil > currentDate) {
                        valid = Boolean.TRUE
                    }
                }
            } else {
                if (validFrom && validUntil && validFrom <= currentDate && validUntil > currentDate) {
                    valid = Boolean.TRUE
                }
            }
        }
        return valid
    }

    def getEducationLicenseKeyValid() {
        Boolean valid = Boolean.FALSE
        if (Constants.LicenseType.EDUCATION.toString().equals(type)) {
            if (dateKeyCreated && DateUtils.addDays(dateKeyCreated, 90).after(new Date())) {
                valid = Boolean.TRUE
            }
        }
        return valid
    }

    def getChangeHistory(Entity entity, Document entityAsDocument = null) {
        LicenseEntityChangeHistory entityChangeHistory

        if (entity && entityChangeHistories) {
            entityChangeHistory = entityChangeHistories.find({
                entity.id.toString().equals(it.entityId)
            })
        } else if (entityAsDocument && entityChangeHistories) {
            entityChangeHistory = entityChangeHistories.find({
                entityAsDocument._id.toString().equals(it.entityId)
            })
        }
        return entityChangeHistory
    }

    def getChangeHistoryByEntityId(String entityId) {
        LicenseEntityChangeHistory entityChangeHistory

        if (entityId && entityChangeHistories) {
            entityChangeHistory = entityChangeHistories.find({
                entityId.equals(it.entityId)
            })
        }
        return entityChangeHistory
    }

    def getChangeHistoryByEntityIds(List<String> entityIds) {
        LicenseEntityChangeHistory entityChangeHistory

        if (entityIds && entityChangeHistories) {
            entityChangeHistory = entityChangeHistories.find({
                entityIds.contains(it.entityId)
            })
        }
        return entityChangeHistory
    }

    @Override
    @CompileStatic
    public int compareTo(Object obj) {
        if (obj && obj instanceof License) {
            return name.compareTo(obj.name)
        } else {
            return 1
        }
    }

    def getNextRenewalDate() {
        Date nextRenewalDate

        if (renewalDate) {
            Calendar calendar = Calendar.getInstance()
            int year = calendar.get(Calendar.YEAR)
            int day = (renewalDate.tokenize('.')[0]).toInteger()
            int month = (renewalDate.tokenize('.')[1]).toInteger()
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")

            if (month - 1 > calendar.get(Calendar.MONTH) || (calendar.get(Calendar.MONTH) == month - 1 &&
                    day > calendar.get(Calendar.DAY_OF_MONTH))) {
                nextRenewalDate = sdf.parse(renewalDate + "." + year)
            } else {
                nextRenewalDate = sdf.parse(renewalDate + "." + (year + 1))
            }
        }
        return nextRenewalDate
    }

    def getDaysToNextRenewal() {
        Date nextRenewalDate = getNextRenewalDate()
        Integer days

        if (nextRenewalDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(sdf.format(nextRenewalDate),
                    formatter))
            days = daysBetween.toInteger()
        }
        return days
    }

    def getRenewLicense(Calendar calendar) {
        Date nextRenewalDate = getNextRenewalDate()
        Integer days
        Date now = calendar?.getTime()

        if (now && nextRenewalDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy")
            long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(sdf.format(nextRenewalDate), formatter), LocalDate.parse(sdf.format(now), formatter))
            days = daysBetween.toInteger()
        }
        return days
    }

    def getAccounts() {
        return accountService.getAccountsForLicense(this.id.toString())
    }

    def allowedForUser(User user) {
        if (isFloatingLicense) {
            return floatingAllowedForUser(user)
        } else {
            if (userBased) {
                if (user && (licenseUserIds?.contains(user.id.toString()) || managerIds?.contains(user.id.toString()))) {
                    return true
                } else {
                    return false
                }
            } else {
                return true
            }
        }
    }

    def floatingAllowedForUser(User user) {
        if (userBased) {
            if (user && licenseUserIds?.contains(user.id.toString())) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getIsFloatingLicense() {
        if (floatingLicense) {
            if (floatingLicense == Constants.FloatingLicense.NO.toString()) {
                return false
            } else {
                return true
            }
        } else {
            return false
        }
    }

    def isUserFloating(User user) {
        if (user && floatingLicense && floatingLicenseCheckedInUsers) {
            if (floatingLicense == Constants.FloatingLicense.NO.toString()) {
                return false
            } else {
                return floatingLicenseCheckedInUsers.find({user.id.toString().equals(it.userId)}) ? true : false
            }
        } else {
            return false
        }
    }

    def getContractNumberForUI() {
        String cn = ""
        if (contractNumber) {
            cn = "C${contractNumber}"
        }
        return cn
    }

    List<ProjectTemplate> getCompatibleProjectTemplates(List<ProjectTemplate> templates) {
        return projectTemplateService.getCompatibleProjectTemplates(templates, this)
    }

    void removeLinkedProjectTemplate(ProjectTemplate template, String accountId = null) {
        if (template) {
            try {
                Boolean okToSave = false

                if (template.isPublic) {
                    if (defaultPublicProjectTemplateId == template.id?.toString()) {
                        defaultPublicProjectTemplateId = null
                    }
                    // this is more important
                    okToSave = publicProjectTemplateIds?.remove(template.id?.toString())
                } else {
                    if (accountId) {
                        if (defaultProjectTemplateIdByAccount?.get(accountId) == template.id?.toString()) {
                            defaultProjectTemplateIdByAccount.remove(accountId)
                        }
                        List<String> templateIds = projectTemplateIdsByAccount?.get(accountId)
                        if (templateIds) {
                            templateIds.remove(template.id?.toString())
                            projectTemplateIdsByAccount.put(accountId, templateIds)
                            // this is more important
                            okToSave = true
                        }
                    }
                }

                if (okToSave) {
                    this.merge(flush: true, failOnError: true)
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeLinkedProjectTemplate", e)
                flashService.setErrorAlert("Error occurred in removeLinkedProjectTemplate: ${e.getMessage()}", true)
            }
        }
    }

    void removeIncompatibleProjectTemplates() {
        try {
            if (publicProjectTemplateIds) {
                List<ProjectTemplate> linkedTemplates = projectTemplateService.getTemplatesByIds(publicProjectTemplateIds)

                // remove the ids if some of them has become incompatible
                if (linkedTemplates) {
                    List<String> removeIds = []

                    linkedTemplates.each { ProjectTemplate template ->
                        if (!projectTemplateService.isCompatibleWithLicense(this, template)) {
                            removeIds.add(template.id?.toString())
                            projectTemplateService.removeLinkedLicense(this, template)
                        }
                    }

                    if (removeIds) {
                        publicProjectTemplateIds.removeAll(removeIds)
                    }
                } else {
                    // all templates are gone (for some reason), reset.
                    publicProjectTemplateIds = null
                }
            }

            // remove the default public template if it has become incompatible
            if (!publicProjectTemplateIds || (defaultPublicProjectTemplateId && !publicProjectTemplateIds?.contains(defaultPublicProjectTemplateId))) {
                defaultPublicProjectTemplateId = null
            }

            if (projectTemplateIdsByAccount) {
                List<String> removeAccountIds = []
                projectTemplateIdsByAccount.each { accountId, templateIds ->
                    List<ProjectTemplate> linkedTemplates = projectTemplateService.getTemplatesByIds(publicProjectTemplateIds)

                    // remove the ids if some of them has become incompatible
                    if (linkedTemplates) {
                        List<String> removeIds = []

                        linkedTemplates.each { ProjectTemplate template ->
                            if (!projectTemplateService.isCompatibleWithLicense(this, template)) {
                                removeIds.add(template.id?.toString())
                                projectTemplateService.removeLinkedLicense(this, accountId, template)
                            }
                        }

                        if (removeIds.size() == linkedTemplates.size()) {
                            removeAccountIds.add(accountId)
                        } else {
                            templateIds.removeAll(removeIds)
                            projectTemplateIdsByAccount.put(accountId, templateIds)
                        }
                    }
                }

                if (removeAccountIds) {
                    removeAccountIds.each { projectTemplateIdsByAccount.remove(it) }
                }

            }

            // remove the default templates if some of them have become incompatible
            if (defaultProjectTemplateIdByAccount) {
                List<String> removeList = []
                defaultProjectTemplateIdByAccount.each { accountId, defaultTemplateId ->
                    if (defaultTemplateId) {
                        if (!projectTemplateIdsByAccount?.get(accountId)?.contains(defaultTemplateId)) {
                            removeList.add(accountId)
                        }
                    }
                }
                removeList?.each { defaultProjectTemplateIdByAccount.remove(it) }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in removeIncompatibleProjectTemplates", e)
            flashService.setErrorAlert("Error occurred in removeIncompatibleProjectTemplates: ${e.getMessage()}", true)
        }
    }
}
