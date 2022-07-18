/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.service.LicenseService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.UserService
import grails.util.Holders
import grails.validation.Validateable
import groovy.transform.CompileStatic
import org.bson.types.ObjectId
import org.springframework.web.util.HtmlUtils

import java.text.Normalizer

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class User implements Comparable, Serializable, Validateable {

    static mapWith = "mongo"
    ObjectId id
    String firstname
    String lastname
    String username
    String name
    String password
    String passwordAgain
    String persistedPassword
    String phone
    String registrationMotive
    Date registrationTime
    String language
    String organizationName
    String country // defaults buildings to user selected country
    // By default Spring Security needs "enabled", means accountEnabled
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    boolean emailValidated = false
    Date enableTime
    List<String> managedEntityIds = []
    List<String> modifiableEntityIds = []
    List<String> readonlyEntityIds = []
    List administeredOrganizationIds = []
    List organizationMembershipIds = []
    List requestToJoinOrganizationIds = []
    List licenseIds = []
    List usableLicenseIds
    List taskEntityIds = []
    List<String> managedLicenseIds
    Map acknowledgedNotifications
    List acknowledgedUpdates
    Date lastLogin
    String channelToken
    String lastLoginChannelToken
    String localeString
    Boolean testRobot // This is used for profiling automatic tester user
    String browserAndOs
    Integer loginCount
    String registrationToken
    String unitSystem
    String mailChimpListId
    Boolean useDefaultValues
    Map<String, String> importMapperDefaults
    Map<String, String> importMapperResourceMappings
    Boolean showStartupTips //not used at the moment(in future)
    Map<String, String> userFilterChoices
    Map<String, String> userSplitFilterChoices
    Map<String, String> userNestedFilters
    Boolean disableHotjar
    String bearerToken
    Date bearerTokenCreated
    List<UserLogin> logins
    UserDisabled userDisabled
    Date activationDeadline
    // If user enable this on user page, sw will display the more errors during runtime to UI. These errors are shown by the methods in FlashService.
    Boolean enableDevDebugNotifications
    Boolean hideBenchmarkIndicators

    // Attribute needed for two factor auth / OTP
    String emailToken
    Boolean emailTokenAuthEnabled
    Boolean emailTokenAuthForNextLoginEnabled
    Boolean disablePeriodicalEmailTokenAuth
    List<Date> emailTokenAuths
    Boolean consent
    Map<String, File> wordDocGraphsForSession
    Map<String, String> pdfGraphsForSession
    Boolean detailsObfuscated
    //first trialCount is for building, second trialCount is added for Infrastructure
    Integer trialCount
    Integer trialCountForInfra
    Boolean enableFloatingHelp
    Boolean allowAdmin
    Boolean firstLogin
    TrialStatus trialStatus

    Boolean showWelcomeTrial

    String zohoContactId
    String pipeDriveUserId
    String pipeDriveDealId
    Map<String, String> securityDatasets
    UserSuspiciousActivity userSuspiciousActivity
    Boolean disableZohoChat
    Boolean disableOnboarding
    String type // ["Student", "Business", "Academia"]
    Boolean wasInvited

    String activeCampaignId
    String activeCampaignUserTypeValue
    String activeCampaignUserTypeTagId
    String activeCampaignAuthentId

    // For RE2020 API request
    Integer idDemandeur

    static hasMany = [authorities: Role, delegatedTasks: Task]

    static mappedBy = [delegatedTasks: "deleguer"]

    static embedded = ["authorities", "logins", "userSuspiciousActivity", "userDisabled", "trialStatus"]

    static transients = [
            "roles",
            "wordDocGraphsForSession",
            "pdfGraphsForSession",
            "internalUseRoles",
            "isCommercialUser"
    ]

    static constraints = {
        username nullable: false, email: true, blank: false, validator: { val, obj ->
            if (val != null && !obj.id) {
                if (User.findByUsername(val.toLowerCase().trim())) {
                    return 'user.not_unique'
                }
            }
        }
        password validator: { val, obj ->
            if (!obj.validatePassword()) {
                return 'user.invalidPassword'
            }
        }
        passwordAgain bindable: true, validator: { val, obj ->
            if (val == null && !obj.id) {
                return 'user.invalidPassword'
            } else {
                val.equals(obj.password) ? true : 'user.no.matching.passwords'
            }
        }
        persistedPassword nullable: true
        organizationName bindable: true, nullable: true
        firstname nullable: true, blank: true, size: 3..25
        lastname nullable: true, blank: true, size: 3..25
        name nullable: false, blank: false, size: 6..50
        phone blank: true, nullable: true, size: 4..25
        enableTime nullable: true
        authorities nullable: true
        administeredOrganizationIds nullable: true
        requestToJoinOrganizationIds nullable: true
        organizationMembershipIds nullable: true
        managedEntityIds nullable: true
        modifiableEntityIds nullable: true
        readonlyEntityIds nullable: true
        registrationMotive nullable: true, blank: true
        language nullable: true
        registrationTime nullable: true
        managedLicenseIds nullable: true
        acknowledgedNotifications nullable: true
        lastLogin nullable: true
        usableLicenseIds nullable: true
        channelToken nullable: true
        localeString nullable: true, blank: true
        testRobot nullable: true
        lastLoginChannelToken nullable: true
        browserAndOs nullable: true
        loginCount nullable: true
        registrationToken nullable: true
        unitSystem nullable: true
        mailChimpListId nullable: true
        useDefaultValues nullable: true
        importMapperDefaults nullable: true
        importMapperResourceMappings nullable: true
        showStartupTips nullable: true
        userFilterChoices nullable: true
        userNestedFilters nullable: true
        disableHotjar nullable: true
        bearerToken nullable: true
        bearerTokenCreated nullable: true
        logins nullable: true
        emailToken nullable: true
        emailTokenAuthEnabled nullable: true
        emailTokenAuthForNextLoginEnabled nullable: true
        activationDeadline nullable: true
        consent nullable: true
        wordDocGraphsForSession nullable: true
        pdfGraphsForSession nullable: true
        detailsObfuscated nullable: true
        trialCount nullable: true
        trialCountForInfra nullable: true
        enableDevDebugNotifications nullable: true
        enableFloatingHelp nullable: true
        hideBenchmarkIndicators nullable: true
        allowAdmin nullable: true
        country nullable: true
        pipeDriveUserId nullable: true
        pipeDriveDealId nullable: true
        zohoContactId nullable: true
        userSplitFilterChoices nullable: true
        securityDatasets nullable: true
        firstLogin nullable: true
        showWelcomeTrial nullable: true
        userSuspiciousActivity nullable: true
        disableZohoChat nullable: true
        type nullable: true
        wasInvited nullable: true
        userDisabled nullable: true
        disablePeriodicalEmailTokenAuth nullable: true
        emailTokenAuths nullable: true
        acknowledgedUpdates nullable: true
        trialStatus nullable: true
        activeCampaignId nullable: true
        activeCampaignUserTypeValue nullable: true
        activeCampaignUserTypeTagId nullable: true
        activeCampaignAuthentId nullable: true
        disableOnboarding nullable: true
        idDemandeur nullable: true

    }

    static final enum LocaleString {
        EUROPEAN("fi"),
        US_CANADA("en-US"),
        UK_INDIA("en-GB")

        String locale

        private LocaleString(String locale) {
            this.locale = locale
        }

        String getLocale() {
            return locale
        }
    }


    public Date getLastLogin() {
        if (logins) {
            return logins.max({ it.time })?.time
        } else {
            return lastLogin
        }
    }

    public Integer getLoginCount() {
        if (logins) {
            return logins.size()
        } else {
            return loginCount
        }
    }

    Set<String> getRoles() {
        Set<String> roles = new HashSet<String>()

        authorities?.each {
            roles.add(it.authority)
        }
        return roles
    }

    def beforeDelete() {
        UserService userService = Holders.getApplicationContext().getBean("userService")
        Entity.withNewSession {
            userService.getManagedEntities(managedEntityIds)?.each { Entity entity ->
                entity.managerIds?.remove(this.id.toString())
                entity.merge(flush: true)
            }

            userService.getReadonlyEntities(readonlyEntityIds)?.each { Entity entity ->
                entity.readonlyUserIds?.remove(this.id.toString())
                entity.merge(flush: true)
            }

            userService.getModifiableEntities(this)?.each { Entity entity ->
                entity.modifierIds?.remove(this.id.toString())
                entity.merge(flush: true)
            }
        }

        License.withNewSession {
            userService.getLicenses(this)?.each { License license ->
                license.licensedUserIdsMap?.remove(this.id.toString())
                license.merge(flush: true)
            }
        }

        Task.withNewSession {
            delegatedTasks?.each { Task task ->
                task.delete(flush: true)
            }
        }

        UserRegistration.withNewSession {
            UserRegistration.findByUsername(username)?.delete(flush: true)
        }
    }

    def beforeInsert() {
        OptimiResourceService optimiResourceService = Holders.getApplicationContext().getBean("optimiResourceService")
        persistedPassword = password
        registrationTime = new Date()
        String normalizedName = Normalizer.normalize(name ?: "", Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
        name = HtmlUtils.htmlEscape(normalizedName)

        if (name) {
            if (name.size() > 50) {
                name = name.take(50)
            }
        } else {
            name = "Undefined"
        }
        phone = phone ? HtmlUtils.htmlEscape(phone) : null
        username = username.trim().toLowerCase()

        if (language) {
            List<Resource> systemLocales = optimiResourceService.getSystemLocales()

            if (!systemLocales?.collect({ it.resourceId.toUpperCase() })?.contains(language.toUpperCase())) {
                language = "en"
            }
        }
    }

    def getInternalUseRoles() {
        if (getRoles().contains(Constants.ROLE_SYSTEM_ADMIN) || getRoles().contains(Constants.ROLE_SUPER_USER) ||
                getRoles().contains(Constants.ROLE_DATA_MGR) || getRoles().contains(Constants.ROLE_SALES_VIEW) || getRoles().contains(Constants.ROLE_CONSULTANT)) {
            return true
        }
        return false
    }

    @Override
    @CompileStatic
    int compareTo(Object o) {
        if (o && o instanceof User) {
            return username.compareTo(o.username)
        } else {
            return 1
        }
    }

    private boolean validatePassword() {
        if (persistedPassword && persistedPassword.equals(password)) {
            return true
        } else if (password) {
            Boolean isLongEnough = password.size() >= 8
            Boolean isContainNumber = password ==~ /.*\d.*/
            Boolean isContainingUpperCase = password ==~ /.*[a-z].*/
            Boolean isContainingLowerCase = password ==~ /.*[A-Z].*/
            return isLongEnough && isContainNumber && isContainingUpperCase && isContainingLowerCase
        } else {
            return false
        }

    }


    def getIsCommercialUser() {
        UserService userService = Holders.getApplicationContext().getBean("userService")
        LicenseService licenseService = Holders.getApplicationContext().getBean("licenseService")
        if (internalUseRoles) {
            return true
        } else {
            List<License> licenses = userService.getUsableAndLicensedLicenses(this)

            if (licenses) {
                if (licenses.find({ Constants.LicenseType.PRODUCTION.toString().equals(it.type) && !it.planetary })) {
                    return true
                } else {
                    Boolean allowedByProjectLevel = Boolean.FALSE
                    List<Entity> parents = userService.getExportableParentEntities(this)

                    if (parents) {
                        for (Entity parent in parents) {
                            List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)

                            if (validLicenses?.find({ it.projectBased && Constants.LicenseType.PRODUCTION.toString().equals(it.type) && !it.planetary})) {
                                allowedByProjectLevel = Boolean.TRUE
                                break
                            }
                        }
                    }
                    return allowedByProjectLevel
                }
            } else {
                return false
            }
        }
    }
}