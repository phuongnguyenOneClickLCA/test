/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.Role
import com.bionova.optimi.core.domain.mongo.RoleMigrationMapping
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserBlacklist
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import groovy.json.JsonBuilder
import org.apache.commons.lang.time.DateUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.util.HtmlUtils

import javax.servlet.http.Cookie
import java.text.DateFormat
import java.text.Normalizer
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class UserController extends AbstractDomainObjectController {

    private static final Integer PAGINATION_DEFAULT_OFFSET = 0
    private static final Integer PAGINATION_DEFAULT_LIMIT = 10

    def entityService
    def localeResolverUtil
    def optimiResourceService
    def channelFeatureService
    def accountService
    def licenseService
    def optimiUserAgentIdentService
    def userService
    def indicatorService
    def groovyPageRenderer

    @Autowired
    EmailConfiguration emailConfiguration

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    def storeSecurityData() {
        User user = userService.getCurrentUser()

        // Lets store security info, if user doesn't have them
        if (user && !user.securityDatasets) {
            Date currentDate = new Date()
            String browser = optimiUserAgentIdentService.getBrowser()
            String browserVersion = optimiUserAgentIdentService.getBrowserVersion()
            List<Cookie> currentCookies = optimiUserAgentIdentService.getCurrentCookies()
            Integer numberOfCookies = optimiUserAgentIdentService.getNumberOfCookies()
            String timeAndDate = dateFormat.format(currentDate)
            String screenHeight = params.screenHeight
            String screenWidth = params.screenWidth
            String os = optimiUserAgentIdentService.getOperatingSystem()
            String acceptedLanguages = optimiUserAgentIdentService.getAcceptedLanguages()
            def timeDifference = ""

            if (params.epochMillisOfClient) {
                timeDifference = params.long("epochMillisOfClient") - currentDate.getTime()
            }
            Map<String, String> securityData = [:]
            securityData.put("browser", browser)
            securityData.put("browserVersion", browserVersion)
            securityData.put("numberOfCookies", numberOfCookies.toString())
            securityData.put("currentCookies", currentCookies?.collect({ (it.name + ": " + it.value).toString() }))
            securityData.put("TimeAndDate", timeAndDate)
            securityData.put("OsAndVersion", os)
            securityData.put("TimeDifferenceClientMinusServer", timeDifference.toString())
            securityData.put("screenInfo", "Height: ${screenHeight}, Width: ${screenWidth}")
            securityData.put("acceptedLanguages", acceptedLanguages)
            User.collection.updateOne(["_id": user.id], [$set: [securityDatasets: securityData]])
        }
        render([output: "ok", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def permanentEntityDeletion() {
        User user = userService.getUserById(params.id)
        boolean deletionOk = entityService.deletePermanentlyCreatedEntities(user, params.boolean("hasDebugLicense"))

        if (deletionOk) {
            flash.fadeSuccessAlert = "All created entities permanently deleted from db!"
        } else {
            flash.fadeErrorAlert = "You have made a horrible mistake and tried without permission to permanently delete all the entities you have created. You will be punished!"
        }
        redirect action: "form", id: user.id
    }

    @Secured(["ROLE_SALES_VIEW"])
    def list() { }

    @Secured(["ROLE_SALES_VIEW"])
    def activatedUsersCount() {
        render userService.getActivatedUsersCount()
    }

    @Secured(["ROLE_SALES_VIEW"])
    def notActivatedUsersCount() {
        render userService.getNotActivatedUsersCount()
    }

    @Secured(["ROLE_SALES_VIEW"])
    def usersWithSuspiciousActivityCount() {
        render userService.getUsersWithSuspiciousActivityCount()
    }

    @Secured(["ROLE_SALES_VIEW"])
    def usersWithEnhancedPrivilegesCount() {
        render userService.getUsersWithEnhancedPrivilegesCount()
    }

    @Secured(["ROLE_SALES_VIEW"])
    def activatedUserList() {

        Integer offset = params.int("offset") ?: PAGINATION_DEFAULT_OFFSET
        Integer limit = params.int("max") ?: PAGINATION_DEFAULT_LIMIT

        Map query = [:]
        if (params.activatedUsersSearch) {
            query = userService.getUsersSearchQuery(params.activatedUsersSearch)
        }

        User currentUser = userService.getCurrentUser()

        List<Document> activatedUsers = userService.getActivatedUsers(offset, limit, query)
        Long activatedUsersCount = userService.getActivatedUsersCount(query)
        List<Document> enhancedActivatedUsers = userService.enhanceActivatedUsersDocuments(activatedUsers)

        render template: "tabs/blocks/activatedUserList",
                model: [activatedUsers     : enhancedActivatedUsers,
                        activatedUsersCount: activatedUsersCount,
                        currentUser        : currentUser]
    }

    @Secured(["ROLE_SALES_VIEW"])
    def nonActiveUserTab() {

        List<Document> notActivatedUsers = userService.getAllNotActivatedUsers()
        User currentUser = userService.getCurrentUser()

        render template: "tabs/nonActiveUserTab",
                model: [notActivatedUsers     : notActivatedUsers,
                        currentUser        : currentUser]
    }

    @Secured(["ROLE_SALES_VIEW"])
    def blacklistTab() {

        List<UserBlacklist> allBlackListFound = userService.getAllUserBlackLists()
        List<Document> disabledUsers = userService.getAllDisabledUsers()

        render template: "tabs/blacklistTab",
                model: [allBlackListFound: allBlackListFound,
                        disabledUsers: disabledUsers]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def roleMigrationMappingTab() {

        List<RoleMigrationMapping> roleMigrationMappings = userService.getRoleMigrationMappings()
        List<Role> roles = userService.getRolesForMapping()

        render template: "tabs/roleMigrationMappingTab",
                model: [roleMigrationMappings: roleMigrationMappings, roles: roles]
    }

    @Secured(["ROLE_SALES_VIEW"])
    def usersWithEnhancedPrivilegesTab() {

        List<Document> usersWithEnhancedPrivileges = userService.getAllUsersWithEnhancedPrivileges()

        render template: "tabs/usersWithEnhancedPrivilegesTab",
                model: [usersWithEnhancedPrivileges     : usersWithEnhancedPrivileges]
    }

    @Secured(["ROLE_SALES_VIEW"])
    def suspiciousActTab() {

        User currentUser = userService.getCurrentUser()
        List<User> usersWithSuspiciousActivity = userService.getAllUsersWithSuspiciousActivity()?.collect({ it as User })

        String suspiciousUsersFilteredWith

        if (usersWithSuspiciousActivity) {
            if (params.searchSuspiciousUsers) {
                String searchSuspicious = params.searchSuspicious
                String startDateString = params.startDate
                String endDateString = params.endDate

                try {
                    DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy")
                    DateFormat simpleDateFormat2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                    Date startDate
                    Date endDate

                    if (startDateString) {
                        startDate = simpleDateFormat.parse(startDateString)
                    }

                    if (endDateString) {
                        endDate = simpleDateFormat.parse(endDateString)
                    }

                    Integer scoreHigherThan = searchSuspicious ? searchSuspicious.toDouble().intValue() : null

                    if (scoreHigherThan) {
                        usersWithSuspiciousActivity = usersWithSuspiciousActivity?.findAll({ it.userSuspiciousActivity.totalScore >= scoreHigherThan })
                    }

                    if (startDate && endDate) {
                        usersWithSuspiciousActivity = usersWithSuspiciousActivity.findAll({ User u ->
                            u.userSuspiciousActivity.loggingInWhenSessionIsValid?.find({ it.before(endDate) && it.after(startDate) }) ||
                                    u.userSuspiciousActivity.physicallyImpossibleTravelSpeed?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) && it.after(startDate) }) ||
                                    u.userSuspiciousActivity.differentBrowser?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) && it.after(startDate) }) ||
                                    u.userSuspiciousActivity.loggingInFromAnotherCountry?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) && it.after(startDate) })
                        })
                        suspiciousUsersFilteredWith = "Filtered with users with any suspicious activity between: ${startDateString} - ${endDateString}"
                    } else if (startDate) {
                        usersWithSuspiciousActivity = usersWithSuspiciousActivity.findAll({ User u ->
                            u.userSuspiciousActivity.loggingInWhenSessionIsValid?.find({ it.after(startDate) }) ||
                                    u.userSuspiciousActivity.physicallyImpossibleTravelSpeed?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.after(startDate) }) ||
                                    u.userSuspiciousActivity.differentBrowser?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.after(startDate) }) ||
                                    u.userSuspiciousActivity.loggingInFromAnotherCountry?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.after(startDate) })
                        })
                        suspiciousUsersFilteredWith = "Filtered with users with any suspicious activity after: ${startDateString}"
                    } else if (endDate) {
                        usersWithSuspiciousActivity = usersWithSuspiciousActivity.findAll({ User u ->
                            u.userSuspiciousActivity.loggingInWhenSessionIsValid?.find({ it.before(endDate) }) ||
                                    u.userSuspiciousActivity.physicallyImpossibleTravelSpeed?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) }) ||
                                    u.userSuspiciousActivity.differentBrowser?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) }) ||
                                    u.userSuspiciousActivity.loggingInFromAnotherCountry?.keySet()?.collect({ String date -> simpleDateFormat2.parse(date) })?.find({ it.before(endDate) })
                        })
                        suspiciousUsersFilteredWith = "Filtered with users with any suspicious activity before: ${endDateString}"
                    }

                    if (scoreHigherThan) {
                        if (suspiciousUsersFilteredWith) {
                            suspiciousUsersFilteredWith = "${suspiciousUsersFilteredWith} and total score ≥ ${scoreHigherThan}"
                        } else {
                            suspiciousUsersFilteredWith = "Filtered with users with any suspicious activity total score ≥ ${scoreHigherThan}"
                        }
                    }
                } catch (Exception e) {
                    flash.errorAlert = "${e}"
                }
            }
            usersWithSuspiciousActivity = usersWithSuspiciousActivity.sort { -it.userSuspiciousActivity.totalScore }
        }

        render template: "tabs/suspiciousActTab",
                model: [suspiciousUsersFilteredWith: suspiciousUsersFilteredWith,
                        usersWithSuspiciousActivity: usersWithSuspiciousActivity,
                        currentUser: currentUser]
    }

    @Secured(["ROLE_SALES_VIEW"])
    def clearSuspiciousActivity() {
        User currentUser = userService.getCurrentUser()
        String daysString = params.clearSuspicious
        String userId = params.id
        Boolean clearAll = params.boolean('clearAll')

        if ((clearAll || (daysString && daysString.isNumber())) && userService.getSuperUser(currentUser) && userId) {
            User user = User.findById(DomainObjectUtil.stringToObjectId(userId))

            if (user && user.userSuspiciousActivity) {
                if (clearAll) {
                    Boolean needsSaving = Boolean.FALSE

                    if (user.userSuspiciousActivity.loggingInWhenSessionIsValid?.size()) {
                        user.userSuspiciousActivity.loggingInWhenSessionIsValid = null
                        needsSaving = Boolean.TRUE
                    }
                    if (user.userSuspiciousActivity.physicallyImpossibleTravelSpeed?.size()) {
                        user.userSuspiciousActivity.physicallyImpossibleTravelSpeed = null
                        needsSaving = Boolean.TRUE
                    }
                    if (user.userSuspiciousActivity.loggingInFromAnotherCountry?.size()) {
                        user.userSuspiciousActivity.loggingInFromAnotherCountry = null
                        needsSaving = Boolean.TRUE
                    }
                    if (user.userSuspiciousActivity.differentBrowser?.size()) {
                        user.userSuspiciousActivity.differentBrowser = null
                        needsSaving = Boolean.TRUE
                    }

                    if (needsSaving) {
                        if (user.userSuspiciousActivity.suspiciousActivityClearedBy) {
                            user.userSuspiciousActivity.suspiciousActivityClearedBy.put(new Date().toString(), currentUser.username + " cleared all suspicious activity")
                        } else {
                            user.userSuspiciousActivity.suspiciousActivityClearedBy = [(new Date().toString()): currentUser.username + " cleared all suspicious activity"]
                        }
                        user.merge(flush: true, failOnError: true)
                        flash.fadeSuccessAlert = "Suspicious activity cleared!"
                    } else {
                        flash.fadeSuccessAlert = "Nothing to clear from user with the given params."
                    }
                } else {
                    Date currentDate = new Date()
                    Integer days = daysString.toDouble().intValue()
                    Date currentMinusXDays = DateUtils.addDays(currentDate, -days)
                    DateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
                    Integer originalSize = user.userSuspiciousActivity.loggingInWhenSessionIsValid?.size() ?: 0
                    user.userSuspiciousActivity.loggingInWhenSessionIsValid?.removeIf({ it.before(currentMinusXDays) })
                    Integer afterRemoveSize = user.userSuspiciousActivity.loggingInWhenSessionIsValid?.size() ?: 0
                    Boolean needsSaving = originalSize != afterRemoveSize

                    List<String> physicallyImpossibleTravelSpeedKeysToRemove = []
                    user.userSuspiciousActivity.physicallyImpossibleTravelSpeed?.each { String key, List<String> value ->
                        Date date = simpleDateFormat.parse(key)

                        if (date.before(currentMinusXDays)) {
                            physicallyImpossibleTravelSpeedKeysToRemove.add(key)
                        }
                    }

                    if (!physicallyImpossibleTravelSpeedKeysToRemove.isEmpty()) {
                        needsSaving = Boolean.TRUE

                        physicallyImpossibleTravelSpeedKeysToRemove.each { String key ->
                            user.userSuspiciousActivity.physicallyImpossibleTravelSpeed.remove(key)
                        }
                    }

                    List<String> differentBrowserKeysToRemove = []
                    user.userSuspiciousActivity.differentBrowser?.each { String key, List<String> value ->
                        Date date = simpleDateFormat.parse(key)

                        if (date.before(currentMinusXDays)) {
                            differentBrowserKeysToRemove.add(key)
                        }
                    }

                    if (!differentBrowserKeysToRemove.isEmpty()) {
                        needsSaving = Boolean.TRUE

                        differentBrowserKeysToRemove.each { String key ->
                            user.userSuspiciousActivity.differentBrowser.remove(key)
                        }
                    }

                    List<String> loggingInFromAnotherCountryKeysToRemove = []
                    user.userSuspiciousActivity.loggingInFromAnotherCountry?.each { String key, List<String> value ->
                        Date date = simpleDateFormat.parse(key)

                        if (date.before(currentMinusXDays)) {
                            loggingInFromAnotherCountryKeysToRemove.add(key)
                        }
                    }

                    if (!loggingInFromAnotherCountryKeysToRemove.isEmpty()) {
                        needsSaving = Boolean.TRUE

                        loggingInFromAnotherCountryKeysToRemove.each { String key ->
                            user.userSuspiciousActivity.loggingInFromAnotherCountry.remove(key)
                        }
                    }

                    if (needsSaving) {
                        if (user.userSuspiciousActivity.suspiciousActivityClearedBy) {
                            user.userSuspiciousActivity.suspiciousActivityClearedBy.put(new Date().toString(), currentUser.username + " cleared suspicious activity older than ${currentMinusXDays.format("dd.MM.yyyy")}")
                        } else {
                            user.userSuspiciousActivity.suspiciousActivityClearedBy = [(new Date().toString()): currentUser.username + " cleared suspicious activity older than ${currentMinusXDays.format("dd.MM.yyyy")}"]
                        }
                        user.merge(flush: true, failOnError: true)
                        flash.fadeSuccessAlert = "Suspicious activity cleared!"
                    } else {
                        flash.fadeSuccessAlert = "Nothing to clear from user with the given params."
                    }
                }
            } else {
                flash.fadeSuccessAlert = "User doesnt have any suspicious activity."
            }
        } else {
            if (!userService.getSuperUser(currentUser)) {
                flash.errorAlert = "This action is only allowed for superusers and above"
                log.error("USER: ${currentUser?.username} tried to clear suspicious activities but they are not superuser or above.")
            } else {
                flash.errorAlert = "Cant clear suspisious activity: Unable to parse ${daysString} to an integer."
            }
        }
        redirect(action: "form", params: [id: userId])
    }

    @Secured(["ROLE_SALES_VIEW"])
    def downloadActiveUsers() {
        User currentUser = userService.getCurrentUser()

        if (userService.getSalesView(currentUser) || userService.getDataManager(currentUser)) {
            List<User> activatedUsers = User.collection.find([emailValidated: true, authorities: [$elemMatch: [authority: Constants.ROLE_AUTHENTICATED]]])?.collect({
                it as User
            })
            Workbook wb = new XSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            Sheet sheet = wb.createSheet()

            List<String> infoRowHeadings = ["Name", "Email", "Consent", "Channel token", "Company / Organisation", "Area of interest", "Country", "Language",
                                            "Last login", "Account created", "Account locked", "User linked licenses", "Active / Deactive"]

            int infoCellIndex = 0
            int rowIndex = 0
            Row headingRow = sheet.createRow(rowIndex)
            Cell infoCell
            infoRowHeadings.each { String heading ->
                infoCell = headingRow.createCell(infoCellIndex)
                infoCell.setCellStyle(cs)
                infoCell.setCellType(CellType.STRING)
                infoCell.setCellValue(heading)
                infoCellIndex++
            }
            rowIndex++

            activatedUsers.each { User user ->
                Row infoRow = sheet.createRow(rowIndex)
                infoCellIndex = 0
                infoRowHeadings.each { String heading ->
                    infoCell = infoRow.createCell(infoCellIndex)
                    infoCell.setCellType(CellType.STRING)
                    if ("Name" == heading) {
                        infoCell.setCellValue(user.name)
                    } else if ("Email" == heading) {
                        infoCell.setCellValue(user.username)
                    } else if ("Consent" == heading) {
                        infoCell.setCellValue(user.consent ? "Yes" : "No")
                    } else if ("Channel token" == heading) {
                        infoCell.setCellValue(user.channelToken)
                    } else if ("Company / Organisation" == heading) {
                        infoCell.setCellValue(user.organizationName)
                    } else if ("Area of interest" == heading) {
                        infoCell.setCellValue(user.registrationMotive)
                    } else if ("Country" == heading) {
                        infoCell.setCellValue(user.country?.capitalize())
                    } else if ("Language" == heading) {
                        infoCell.setCellValue(user.language?.toUpperCase())
                    } else if ("Last login" == heading) {
                        infoCell.setCellValue(user.getLastLogin()?.format("dd.MM.yyyy HH:mm:ss"))
                    } else if ("Account created" == heading) {
                        infoCell.setCellValue(user.registrationTime?.format("dd.MM.yyyy HH:mm:ss"))
                    } else if ("Account locked" == heading) {
                        infoCell.setCellValue(user.accountLocked ? "Yes" : "No")
                    } else if ("User linked licenses" == heading) {
                        infoCell.setCellValue(userService.getLicenses(user)?.collect({ it.name })?.toString())
                    } else if ("Active / Deactive" == heading) {
                        infoCell.setCellValue(user.enabled ? "Enabled" : "Disabled")
                    }
                    infoCellIndex++
                }
                rowIndex++
            }

            if (wb) {
                response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                response.setHeader("Content-disposition", "attachment; filename=OCLCA_USERS_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            } else {
                render(text: "Could not download Excel file. Results are missing or invalid ordering of table.")
            }
        } else {
            render(text: "Unauthorized")
        }
    }

    def acknowledgeNotification() {
        if (params.id) {
            userService.acknowledgeNotification(params.id, session)
        }
        render text: ""
    }

    def acknowledgeUpdate() {
        if (params.id) {
            userService.acknowledgeUpdate(params.id, session)
        }
        render text: ""
    }
    /**
     * Loads details for the given user id.
     */
    def form() {
        User user

        if (params.id) {
            user = userService.getUserById(params.id)
        } else {
            user = session?.getAttribute(SessionAttribute.USER.getAttribute())
            session?.removeAttribute(SessionAttribute.USER.getAttribute())
        }

        if (user) {
            def basicAccounts = accountService.getBasicAccountsForUser(user)
            def managedAccounts = accountService.getManagedAccountsForUser(user)
            def linkedAccounts = accountService.getAllAccountsForUser(user)
            def roles = Role.list()
            def authorities = []
            User currentUser = userService.getCurrentUser()
            def ownAccount = false
            Boolean viewingAllowed = false

            if (currentUser.id == user?.id) {
                ownAccount = true
                viewingAllowed = true
            } else if (currentUser.internalUseRoles) {
                viewingAllowed = true
            }

            if (viewingAllowed) {
                roles?.each { Role role ->
                    if (role.authority != "ROLE_ORG_ADMIN") {
                        authorities.add(role)
                    }
                }
                ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
                def languages = optimiResourceService.getSystemLocales(channelFeature)
                def userRegistrationMotives = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["registrationMotive"], null)?.collect({
                    optimiResourceService.getLocalizedName(it)
                })
                def userLocales = [(message(code: 'locale.fi')): new Locale("fi"), (message(code: 'locale.' + Locale.US)): Locale.US, (message(code: 'locale.' + Locale.UK)): Locale.UK]
                def unitSystems = UnitConversionUtil.UnitSystem.values()

                if (!currentUser.internalUseRoles) {
                    // Dont allow both for non internal users
                    unitSystems = unitSystems.findAll({ !UnitConversionUtil.UnitSystem.METRIC_AND_IMPERIAL.value.equals(it.value) })
                }

                String localizedName = "name${DomainObjectUtil.getMapKeyLanguage()}"
                List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1, (localizedName): 1])?.toList()

                if (countries) {
                    if (countries.find({ it.get(localizedName) })) {
                        countries = countries.sort({ it.get(localizedName) })
                    } else {
                        countries = countries.sort({ it.get("nameEN") })
                        localizedName = "nameEN"
                    }
                }

                Indicator apiIndicator = indicatorService.getIndicatorByIndicatorId(indicatorService.getApiIndicatorIdForUser(user), true)
                boolean isProdBuildInDev = Environment.current == Environment.PRODUCTION && !(request.serverName in Constants.PROD_DOMAINS)
                Boolean development = Environment.current == Environment.DEVELOPMENT || isProdBuildInDev

                // Check license to show the robot checkbox only for user used for sw testing only
                Boolean hasDebugLicense = licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.DEBUG])?.get(Feature.DEBUG)

                [user           : user, authorities: authorities, languages: languages, userRegistrationMotives: userRegistrationMotives, linkedAccounts: linkedAccounts,
                 ownAccount     : ownAccount, userLocales: userLocales, unitSystems: unitSystems, basicAccounts: basicAccounts, currentUser: currentUser, development: development,
                 managedAccounts: managedAccounts, countries: countries, localizedName: localizedName, apiIndicator: apiIndicator, userTypes: Constants.UserType.map(), hasDebugLicense: hasDebugLicense]
            } else {
                redirect controller: "main"
            }
        } else {
            redirect controller: "main"
        }
    }

    /**
     * Enables / disables the user with the given id.
     */
    def modifyState() {
        if (params.id) {
            def user = userService.getUserById(params.id)

            if (user) {
                user.enabled = params.enabled == "Enable"

                if (user.enabled) {
                    flash.fadeSuccessAlert = g.message(code: 'user.enabled', args: [user.username])
                } else {
                    flash.fadeSuccessAlert = g.message(code: 'user.disabled', args: [user.username])
                }
                user.save(flush: true)
            }
        }
        redirect action: "list"
    }

    /**
     * Saves the user with the given id.
     */
    def saveInternal() {
        User user

        if (params.id) {
            user = userService.getUserById(params.id)
            user.properties = params
        } else {
            user = new User(params)
        }

        if (user?.name) {
            String normalizedName = Normalizer.normalize(user.name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
            user.name = HtmlUtils.htmlEscape(normalizedName)

            if (user.name) {
                if (user.name.size() > 50) {
                    user.name = user.name.take(50)
                }
            }
        }

        if (user.validate()) {
            if (user.id) {
                if (!params.emailTokenAuthEnabled) {
                    user.emailTokenAuthEnabled = null
                }
                if (!params.disablePeriodicalEmailTokenAuth) {
                    user.disablePeriodicalEmailTokenAuth = null
                }
                userService.updateUser(user)
                localeResolverUtil.resolveLocale(session, request, response)
                flash.fadeSuccessAlert = g.message(code: "user.updated")
            } else {
                userService.createUser(user, session)
                localeResolverUtil.resolveLocale(session, request, response)
                flash.fadeSuccessAlert = g.message(code: "user.created")
            }
            User currentUser = userService.getCurrentUser()

            if (userService.isSystemAdmin(currentUser) || userService.getSuperUser(currentUser)) {
                if (currentUser.id.equals(user?.id)) {
                    redirect controller: "main"
                } else {
                    redirect action: "list"
                }
            } else {
                redirect controller: "main"
            }
        } else {
            flash.fadeErrorAlert = renderErrors(bean: user)
            session?.setAttribute(SessionAttribute.USER?.getAttribute(), user)
            redirect(action: "form")
        }
    }

    def remove() {
        def user = userService.getUserById(params.id)

        if (user) {
            User currentUser = userService.getCurrentUser()

            if (user.id == currentUser?.id) {
                userService.deleteUser(user)
                redirect controller: "logout"
            } else if (userService.isSystemAdmin(currentUser) || ((!user.emailValidated || user.activationDeadline) && userService.getSuperUser(currentUser))) {
                userService.deleteUser(user)
                flash.fadeSuccessAlert = "${message(code: "user.deleted")}"
                redirect action: "list"
            } else {
                loggerUtil.error(log, "User tried to delete another user!")
                redirect controller: "index"
            }
        } else {
            redirect controller: "index"
        }
    }

    def activateUser() {
        if (params.id) {
            userService.activateUserById(params.id, session)
            flash.fadeSuccessAlert = message(code: "user.activated")
        }
        redirect action: "list"
    }

    def resendActivation() {
        def userId = params.id

        if (userId) {
            User user = userService.getUserById(userId)

            if (user) {
                if (!user.registrationToken) {
                    user.registrationToken = UUID.randomUUID().toString().replaceAll('-', '')
                    log.info("TOKEN 4: New token created for user ${user.username}: ${user.registrationToken}")
                    user = userService.updateUser(user)
                }
                Locale locale = localeResolverUtil.resolveLocale(session, request, response)
                String hash = userService.generateEmailHash(user)
                def registerUrl = generateRegisterLink([t   : user.registrationToken, channelToken: user.channelToken,
                                                        lang: locale?.language, hash: hash])

                try {
                    log.info("Trying to resend  activation email for user " + user.username)
                    sendRegistrationMail(user, registerUrl, user.channelToken)
                    log.info("Resent activation email for user " + user.username)
                    flash.fadeSuccessAlert = g.message(code: "index.reactivation")
                } catch (Exception e) {
                    // userService.deleteUser(userRegistration.username)
                    flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                }
            }
        }
        redirect action: "list"
    }

    private String generateRegisterLink(linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: "index", action: "activateUser",
                params: linkParams)
    }

    private void sendRegistrationMail(User user, registerUrl, String channelToken) {
        def emailTo = user.username
        def emailSubject = g.message(code: 'index.registration_mail.subject')
        def emailFrom = g.message(code: 'email.support')
        def replyToAddress = message(code: "email.support")
        def loginUrl = channelFeatureService.processLoginUrl(channelFeatureService.getChannelFeatureByToken(channelToken)?.loginUrl, request)
        def emailBody = g.message(code: "index.registration_mail.body", args: [registerUrl, loginUrl], encodeAs: "Raw")
        optimiMailService.sendMail({
            to emailTo
            from emailFrom
            replyTo replyToAddress
            subject emailSubject
            body emailBody
        }, emailTo)
    }

    def cancel() {
        User user = userService.getCurrentUser()

        if (userService.isSystemAdmin(user) || userService.getSuperUser(user)) {
            redirect action: "list"
        } else {
            redirect controller: "main"
        }
    }

    def changeLanguage() {
        String userId = params.userId
        String lang = params.lang
        Boolean skipEmailActivateModal = params.boolean("skipEmailActivateModal")

        if (lang && userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))

            if (user) {
                user.language = lang
                userService.updateUser(user)
            }
        }
        redirect controller: "main", action: "list", params: [skipEmailActivateModal: skipEmailActivateModal]
    }

    def changeUnitSystem() {
        String userId = params.userId
        String value = params.value
        String returnable = ""
        if (value && userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
            if (user) {
                user.unitSystem = value
                userService.updateUser(user)
                returnable = "ok"
            } else {
                returnable = "failed to save userUnitSystem"
            }
        }
        render([output: returnable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def changeLocale() {
        String userId = params.userId
        String value = params.value
        String returnable = ""
        if (value && userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
            if (user) {
                user.localeString = value
                userService.updateUser(user)
                returnable = "ok"
            } else {
                returnable = "failed to save user locale"
            }
        }
        render([output: 'ok!', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def changeUserName() {
        String userId = request.JSON?.userId
        String value = request.JSON?.value
        String returnable = "${message(code: 'default.invalid.email.message')}"

        if (value && userId) {
            User existingWithEmail = userService.getUserByUsername(value)

            if (!existingWithEmail) {
                User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
                if (user) {
                    user.username = value.trim().toLowerCase()

                    if (user.validate()) {
                        try {
                            userService.updateUser(user)
                            returnable = "ok"
                        } catch (Exception e) {
                            loggerUtil.warn(log, "Unable to change user username", e)
                            flashService.setErrorAlert("Unable to change user username: ${e.message}", true)
                        }
                    } else {
                        def errorsList = user.errors.allErrors

                        returnable = ""
                        errorsList?.each {
                            returnable = "${returnable}${message(error: it)}<br/>"
                        }
                    }
                }
            } else {
                returnable = "${message(code: 'user.not_unique')}"
            }
        }
        render([output: returnable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def removeActivationDeadline() {
        String userId = params.userId
        User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))

        if (user && user.activationDeadline) {
            userService.activateUserById(userId, session)
            flash.fadeSuccessAlert = "User activation deadline cleared and user activated!"
        }

        redirect action: "form", params: [id: userId]
    }

    def userHasSetSettings() {
        String returnable = ""
        session?.setAttribute("settingsSet", true)
        returnable = "user has set settings ${session?.getAttribute("settingsSet")}"
        render text: returnable
    }

    def renderUserLicenseInformation() {
        String userId = params.id
        User user
        if (userId) {
            user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
        }
        if (user) {
            String templateAsString = g.render(template: "/user/userLicenseInformation", model: [licenses: userService.getLicenses(user), user: user]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            flashService.setErrorAlert("Cannot get user object with userId $userId", true)
            render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def userAsJson() {
        String userId = params.id
        Document user
        if (userId) {
            BasicDBObject projection = new BasicDBObject([password: 0, passwordAgain: 0, persistedPassword: 0])
            user = userService.getUserAsDocumentById(userId, projection)
        }

        if (user) {
            File tempFile = File.createTempFile("user", ".json")
            tempFile.write(new JsonBuilder(user).toPrettyString())
            response.setHeader "Content-disposition", "attachment; filename=${tempFile.name}"
            response.setHeader("Content-Length", "${tempFile.size()}")
            InputStream contentStream = tempFile.newInputStream()
            tempFile.delete()
            response.outputStream << contentStream
            contentStream.close()
            webRequest.renderView = false
        }
    }

    def disableHelpFloat() {
        String userId = params.id
        String returnable = ""
        if (userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
            if (user) {
                user.enableFloatingHelp = Boolean.FALSE
                userService.updateUser(user)
                returnable = "ok"
            } else {
                returnable = "failed to save user ${user?.id} @disableHelpFloat"
            }
        }
        render text: "ok!"
    }

    def disableWecomeTrial() {
        String userId = params.user
        String returnable = ""
        if (userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
            if (user) {
                user.showWelcomeTrial = Boolean.FALSE
                userService.updateUser(user)
                returnable = 'updated'
            } else {
                returnable = "failed to save user ${user?.id} @disableWelcomeTrial"
            }
        }
        render([output: returnable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def activeUsersAsJson() {
        String selectedUser = params.selectedUser
        def formattedForSelect2 = []
        int i = 0
        User.collection.find([enabled: true], [_id: 1, name: 1, username: 1])?.toList()?.each {
            if (i == 0) {
                Document d = new Document()
                d["id"] = ""
                d["text"] = ""
                formattedForSelect2.add(d)
            }
            Document d = new Document()
            d["id"] = it._id.toString()
            d["text"] = "${it.name} (${it.username})"

            if (selectedUser == it._id.toString()) {
                d["selected"] = true
            }

            formattedForSelect2.add(d)
            i++
        }
        render([users: formattedForSelect2, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addToBlackList() {
        UserBlacklist existingRule
        boolean hasPrefix = false
        String suffix = params.suffix?.trim()
        String prefix = params.prefix?.trim()
        if (suffix && prefix) {
            existingRule = UserBlacklist.findByPrefixAndSuffix(prefix, suffix)
            hasPrefix = true
        } else if (suffix) {
            existingRule = UserBlacklist.findBySuffix(suffix)
        }
        if (!existingRule) {
            UserBlacklist blacklist = new UserBlacklist()
            blacklist.suffix = suffix
            blacklist.prefix = prefix
            blacklist.timeAdded = new Date()
            User user = userService.getCurrentUser()
            blacklist.ruleAddedBy = user?.name ? user.name : "Unknown"
            blacklist = blacklist.save(flush: true)
            userService.disableUsersByBlacklistRules(blacklist, hasPrefix)

            flash.fadeSuccessAlert = "Added successfully"
        } else {
            flash.fadeErrorAlert = "Rule already existed, added by ${existingRule?.ruleAddedBy} at ${existingRule?.timeAdded}"
        }

        redirect action: "list"
    }

    def deleteBlacklistRule() {
        UserBlacklist existingRule
        boolean hasPrefix = false
        if (params.id) {
            existingRule = UserBlacklist.findById(DomainObjectUtil.stringToObjectId(params.id))
            if (existingRule) {
                if (existingRule.prefix && existingRule.prefix.length() > 0) {
                    hasPrefix = true
                }
                userService.enableUsersByBlacklistRules(existingRule, hasPrefix)
                existingRule.delete()
                flash.fadeSuccessAlert = "Remove blacklist successfully"
            } else {
                flash.fadeErrorAlert = "Error deleting rule, id does not match. Contact dev team"
            }
        } else {
            loggerUtil.error(log, "Delete blacklist uncessfully")
            flash.fadeErrorAlert = "Error deleting rule, id not exist. Contact dev team"


        }
        redirect action: "list"

    }

    def removeUserFromBlacklist() {
        if (params.id) {
            def user = userService.getUserById(params.id)

            if (user) {
                user.enabled = true
                user.userDisabled = null

                if (user.enabled) {
                    flash.fadeSuccessAlert = g.message(code: 'user.enabled', args: [user.username])
                } else {
                    flash.fadeErrorAlert = "Error enabling user"
                }
                user.save(flush: true)
            } else {
                flash.fadeErrorAlert = "Error enabling user, cannot find user with this id ${params.id}. Contact dev team"
            }
        } else {
            loggerUtil.error(log, "Delete blacklist uncessfully")
            flash.fadeErrorAlert = "Error enabling user, id not exist. Contact dev team"
        }
        redirect action: "list"
    }

    def trialStatusList() {
        List<User> userList = User.collection.find(
                ["trialStatus.accountCreated": [$ne: null]],
                [username: 1, trialStatus: 1, type: 1, trialCount: 1]
        ) asList()
        render view: "/user/trialStatusList",
                model: [
                        userList: userList.findAll {
                            !emailConfiguration.isProtectedEmail(it.username)
                        }
                ]
    }

    def toggleFavoriteMaterial() {
        String resourceId = params.resourceId
        String userId = params.userId
        String returnText = "404"

        if (resourceId && userId) {
            User user = userService.getCurrentUser()
            Account account = accountService.getAccountByUser(user)
            if (account) {
                Map<String, String> favouriteMaterialsAndUserId = account.favoriteMaterialIdAndUserId
                if (favouriteMaterialsAndUserId && !favouriteMaterialsAndUserId.isEmpty()) {

                    boolean deselectMaterial = favouriteMaterialsAndUserId?.containsKey(resourceId)

                    if (deselectMaterial) {
                        account.favoriteMaterialIdAndUserId.remove(resourceId)
                    } else {
                        account.favoriteMaterialIdAndUserId.put(resourceId, userId)
                    }
                } else {
                    favouriteMaterialsAndUserId = [:]
                    favouriteMaterialsAndUserId.put(resourceId, userId)
                    account.favoriteMaterialIdAndUserId = favouriteMaterialsAndUserId
                }
                accountService.saveAccount(account)
                returnText = "ok"
            }
        }
        render([output: returnText, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def disableOnboardingForUser() {
        User user = userService.getCurrentUser()
        if (user) {
            user.disableOnboarding = true
            userService.updateUser(user)
        }
        render([output: "ok", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addRoleMigrationMapping(){
        try{
            userService.saveRoleMigrationMapping(params)
            flash.fadeSuccessAlert = "Mapping successfully saved."
        } catch(Exception ex){
            loggerUtil.error(log, "Mapping can't not be saved", ex)
            flash.fadeErrorAlert = "Mapping can't not be saved: ${ex.getMessage()}"
        }

        String mappingTables = groovyPageRenderer.render(template: "/user/tabs/blocks/roleMigrationUserTable",
                model: [roleMigrationMappings: RoleMigrationMapping.list(), roles: Role.list()])
        render([output: mappingTables, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def deleteRoleMigrationMapping(){
        try{
            userService.deleteRoleMigrationMapping(params)
            flash.fadeSuccessAlert = "Mapping successfully deleted."
        } catch(Exception ex){
            loggerUtil.error(log, "Mapping can't not be deleted", ex)
            flash.fadeErrorAlert = "Mapping can't not be deleted: ${ex.getMessage()}"
        }

        render([(flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def uploadOnKeyUpUsers(){
        List<String> users = userService.uploadOnKeyUpUsers(params.q)
        render([output: users, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}
