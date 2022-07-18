package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Notification
import com.bionova.optimi.core.domain.mongo.Organization
import com.bionova.optimi.core.domain.mongo.PreProvisionedUserRight
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.Role
import com.bionova.optimi.core.domain.mongo.RoleMigrationMapping
import com.bionova.optimi.core.domain.mongo.UpdatePublish
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserBlacklist
import com.bionova.optimi.core.domain.mongo.UserDisabled
import com.bionova.optimi.core.domain.mongo.UserLicenseInvitation
import com.bionova.optimi.core.domain.mongo.UserLocation
import com.bionova.optimi.core.domain.mongo.UserSuspiciousActivity
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.DateUtil
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import com.maxmind.geoip2.record.Country
import com.maxmind.geoip2.record.Location
import com.maxmind.geoip2.record.Subdivision
import com.mongodb.BasicDBObject
import com.mongodb.client.result.DeleteResult
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.util.Environment
import org.apache.commons.lang.time.DateUtils
import org.apache.commons.lang3.StringUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.codehaus.groovy.runtime.StackTraceUtils
import org.geeks.browserdetection.ComparisonType
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.session.SessionInformation
import org.springframework.web.util.HtmlUtils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.security.Key
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Duration

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class UserService extends GormCleanerService {

    private static final List<String> ENHANCED_USER_ROLES = [Constants.ROLE_SYSTEM_ADMIN, Constants.ROLE_SUPER_USER, Constants.ROLE_SALES_VIEW, Constants.ROLE_DATA_MGR, Constants.ROLE_CONSULTANT, Constants.ROLE_DEVELOPER]

    def accountService
    def springSecurityService
    def loggerUtil
    def optimiUserAgentIdentService
    def optimiMailService
    def channelFeatureService
    def licenseService
    def entityService
    def optimiResourceService
    def sessionRegistry
    def domainClassService
    def activeCampaignService
    def applicationService
    FlashService flashService
    IndicatorQueryService indicatorQueryService

    @Autowired
    EmailConfiguration emailConfiguration

    static transactional = "mongo"

    private static final String key = "4FE7B1D22DBEBW7G"

    def defineUserLocaleInRegister() {

    }

    String generateEmailHash(User user) {
        if (user.username) {
            try {
                Key aesKey = new SecretKeySpec(key.getBytes(), "AES")
                Cipher cipher = Cipher.getInstance("AES")
                // encrypt the text
                cipher.init(Cipher.ENCRYPT_MODE, aesKey)
                byte[] encrypted = cipher.doFinal(user.username.getBytes())
                return Base64.getUrlEncoder().encodeToString(encrypted)
            } catch(Exception e) {
                loggerUtil.error(log, "Exception when encoding email hash", e)
                flashService.setErrorAlert("Exception when encoding email hash ${e.message}", true)
                return null
            }
        } else {
            return null
        }
    }

    String decodeEmailHash(String hash) {
        if (hash) {
            try {
                Key aesKey = new SecretKeySpec(key.getBytes(), "AES")
                Cipher cipher = Cipher.getInstance("AES")
                // decrypt the hash
                cipher.init(Cipher.DECRYPT_MODE, aesKey)
                String decrypted = new String(cipher.doFinal(Base64.getUrlDecoder().decode(hash.getBytes())))
                return decrypted
            } catch (Exception e) {
                loggerUtil.error(log, "Exception when decoding email hash:", e)
                flashService.setErrorAlert("Exception when decoding email hash: ${e.message}", true)
                return null
            }
        } else {
            return null
        }
    }

    UserLocation getLocationForIp(String ipAddress, User user) {
        UserLocation userLocation

        if (ipAddress) {
            try {
                File file = new File(MongoUserDetailsService.class.getClassLoader().getResource("GeoLite2-City.mmdb").getFile())
                DatabaseReader reader = new DatabaseReader.Builder(file).build()
                InetAddress inetAddress = InetAddress.getByName(ipAddress)

                if (inetAddress) {
                    CityResponse response = reader.city(inetAddress)

                    if (response) {
                        userLocation = new UserLocation()
                        Country country = response.getCountry()
                        userLocation.countryName = country?.name
                        userLocation.countryCode = country?.isoCode
                        userLocation.postalCode = response.getPostal()?.code
                        userLocation.city = response.city?.name
                        Subdivision subdivision = response.getMostSpecificSubdivision()
                        userLocation.region = subdivision?.getName()
                        Location location = response.getLocation()
                        userLocation.longitude = location?.longitude
                        userLocation.latitude = location?.latitude
                    }
                }
            } catch (IOException e) {
                loggerUtil.warn(log, "Error in getting user ${user?.username} geolocation:", e)
                flashService.setErrorAlert("Error in getting user ${user?.username} geolocation: ${e.message}", true)
            }
        }
        return userLocation
    }

    private String getClientIp() {
        HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        String remoteAddr
        String xForwardedFor

        for (String header : request.getHeaderNames().toList()) {
            if ("x-forwarded-for".equalsIgnoreCase(header)) {
                xForwardedFor = request.getHeader(header)
                break
            }
        }

        if (xForwardedFor) {
            if (xForwardedFor.indexOf(",") != -1) {
                remoteAddr = xForwardedFor.substring(xForwardedFor.lastIndexOf(",") + 2);
            } else {
                remoteAddr = xForwardedFor;
            }
        } else {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getAllActivatedUsers() {
        getActivatedUsers(0, 0)
    }

    @Secured(["ROLE_SALES_VIEW"])
    Map getUsersSearchQuery(String q) {
        [$or: [
                ["username": ['$regex': q]],
                ["lastName": ['$regex': q]],
                ["firstName": ['$regex': q]]
        ]]
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getActivatedUsers(int offset, int limit, Map query = [:]) {
        User.collection.find(activatedUsersQuery + query, [name                  : 1, username: 1, enableTime: 1, type: 1,
                                                           _id                   : 1, channelToken: 1, organizationName: 1,
                                                           registrationMotive    : 1, language: 1, authorities: 1,
                                                           logins                : 1, registrationTime: 1, accountLocked: 1,
                                                           licenseIds            : 1, usableLicenseIds: 1, enabled: 1, country: 1,
                                                           userSuspiciousActivity: 1]).skip(offset).limit(limit)?.toList()
    }

    @Secured(["ROLE_SALES_VIEW"])
    Long getActivatedUsersCount(Map query = [:]) {
        return User.collection.count(activatedUsersQuery + query)
    }

    private Map getActivatedUsersQuery() {
        [emailValidated: true]
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getAllNotActivatedUsers() {
        User.collection.find(notActivatedUsersQuery, [name            : 1, username: 1, type: 1,
                                                      organizationName: 1, registrationMotive: 1,
                                                      language        : 1, registrationTime: 1])?.toList()
    }

    @Secured(["ROLE_SALES_VIEW"])
    Long getNotActivatedUsersCount() {
        return User.collection.count(notActivatedUsersQuery)
    }

    private Map getNotActivatedUsersQuery() {
        [$or: [[emailValidated: false], [activationDeadline: [$ne:null]]]]
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getAllUsersWithSuspiciousActivity() {
        User.collection.find(usersWithSuspiciousActivityQuery,
                [name                  : 1, username: 1, enableTime: 1, type: 1,
                 _id                   : 1, channelToken: 1, organizationName: 1,
                 registrationMotive    : 1, language: 1, authorities: 1,
                 logins                : 1, registrationTime: 1, accountLocked: 1,
                 licenseIds            : 1, usableLicenseIds: 1, enabled: 1, country: 1,
                 userSuspiciousActivity: 1])?.toList()
    }

    @Secured(["ROLE_SALES_VIEW"])
    Long getUsersWithSuspiciousActivityCount() {
        return User.collection.count(usersWithSuspiciousActivityQuery)
    }

    private Map getUsersWithSuspiciousActivityQuery() {
        Map query = getActivatedUsersQuery()
        query.userSuspiciousActivity = [$ne: null]
        return query
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<UserBlacklist> getAllUserBlackLists() {
        UserBlacklist.getAll()
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getAllDisabledUsers() {
        User.collection.find(disabledUsersQuery, [name              : 1, username: 1, enableTime: 1, type: 1, userDisabled: 1,
                                                  id                : 1, channelToken: 1, organizationName: 1,
                                                  registrationMotive: 1, language: 1])?.toList()?.sort({ it?.userDisabled?.time })


    }

    private Map getDisabledUsersQuery() {
        [$or: [["userDisabled": ["\$ne": null]], ["enabled": ["\$ne": true]]]]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<RoleMigrationMapping> getRoleMigrationMappings() {
        RoleMigrationMapping.list()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<Role> getRolesForMapping() {
        Role.findAllByAuthorityNotEqual("ROLE_ORG_ADMIN")
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> getAllUsersWithEnhancedPrivileges() {
        User.collection.find(usersWithEnhancedPrivilegesQuery,
                [username: 1, "authorities.authority": 1])?.toList()?.collect { Document user ->
            user.authorities = user.authorities.collect { it.authority }
            return user
        }
    }

    @Secured(["ROLE_SALES_VIEW"])
    Long getUsersWithEnhancedPrivilegesCount() {
        return User.collection.count(usersWithEnhancedPrivilegesQuery)
    }

    private Map getUsersWithEnhancedPrivilegesQuery() {
        [authorities: [$elemMatch: [authority: [$in: ENHANCED_USER_ROLES]]]]
    }

    @Secured(["ROLE_SALES_VIEW"])
    List<Document> enhanceActivatedUsersDocuments(List<Document> activatedUsers) {

        List<Document> enhancedActivatedUsers = []

        // TODO: get accounts only for activatedUsers
        Map<String, List<Document>> userAccountMapping = accountService.getAllUserAccountMapping()
        Integer validLicensesCount = licenseService.getAllValidLicensesCount()

        activatedUsers.each { Document d ->
            d._id = d._id.toString()

            d.accounts = userAccountMapping.get(d._id)?.collect { account ->
                ["_id": account._id, "companyName": account.companyName]
            }

            if (d.authorities?.find({
                it.authority?.equals(Constants.ROLE_SYSTEM_ADMIN) || it.authority?.equals(Constants.ROLE_SUPER_USER)
            })) {
                d.licenses = validLicensesCount
            } else {
                List<String> licenses = []

                if (d.licenseIds) {
                    licenses.addAll(d.licenseIds)
                }
                if (d.usableLicenseIds) {
                    licenses.addAll(d.usableLicenseIds)
                }
                d.licenses = licenses.unique().size()

            }

            UserSuspiciousActivity userSuspiciousActivity = d.get("userSuspiciousActivity")
            if (userSuspiciousActivity) {
                d.put("suspiciousActivityScore", userSuspiciousActivity.getTotalScore())
            } else {
                d.put("suspiciousActivityScore", 0)
            }
            enhancedActivatedUsers.add(d)
        }

        return enhancedActivatedUsers
    }

    List<User> getActiveUsers() {
        List<User> users
        def grailsUsersWithValidSession = getGrailsUsersWithValidSessions()

        if (!grailsUsersWithValidSession.isEmpty()) {
            def ids = grailsUsersWithValidSession.collect({ it.id.toString() })

            if (ids) {
                users = User.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(ids))
            }
        }

        if (users) {
            users = users.sort({ it.getLastLogin() })
            Collections.reverse(users)
        }
        return users
    }

    List<User> getActiveFreeUsers() {
        List<User> users = []
        def grailsUsersWithValidSession = getGrailsUsersWithValidSessions()

        if (!grailsUsersWithValidSession.isEmpty()) {
            def ids = grailsUsersWithValidSession.collect({ it.id.toString() })

            if (ids) {
                users = User.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(ids))

                if (users) {
                    users = users.findAll({!it.getIsCommercialUser()})
                }
            }
        }
        return users
    }

    List<String> getActiveUserIds() {
        return getGrailsUsersWithValidSessions()?.collect({ it.id.toString() })?.unique()
    }

    Map<Object, Object> getLastRequestByUserId() {
        def userIdAndLastRequest = [:]

        getGrailsUsersWithValidSessions()?.each { GrailsUser grailsUser ->
            Date lastRequest = sessionRegistry.getAllSessions(grailsUser, false)?.
                    findAll({it.lastRequest})?.sort({it.lastRequest.time})?.get(0)?.lastRequest

            if (lastRequest) {
                userIdAndLastRequest.put(grailsUser.id, lastRequest)
            }
        }
        return userIdAndLastRequest
    }

    List<String> getActiveUserNames() {
        return getGrailsUsersWithValidSessions()?.collect({ it.username })
    }

    private List<GrailsUser> getGrailsUsersWithValidSessions() {
        List<GrailsUser> grailsUsers = sessionRegistry.getAllPrincipals()
        List<GrailsUser> grailsUsersWithValidSession = []

        if (grailsUsers) {
            List<SessionInformation> sessionInformations = []

            grailsUsers.each { GrailsUser user ->
                sessionRegistry.getAllSessions(user, false).each {
                    if (!sessionInformations.contains(it)) {
                        sessionInformations.add(it)
                    }
                }
            }
            Date currentDate = new Date()

            sessionInformations.each { SessionInformation sessionInfo ->
                /* checks to see if current session is not expired and last request was made less than 8 hours ago
                * Our sessions expire at 8 (2.88e+7 ms) hours, however springSecurity does not realise this properly and the sessionRegistry valid sessions
                * contain "expired" sessions, hence why we need to check for lastRequest time aswell to get realistic users.
                * */
                if ((currentDate.time - sessionInfo.lastRequest.time) < 2.88e+7 && !sessionInfo.expired && !grailsUsersWithValidSession.contains(sessionInfo.principal)) {
                    grailsUsersWithValidSession.add(sessionInfo.principal)
                }
            }
        }
        return grailsUsersWithValidSession
    }

    void newUserAccountsJob() {
        Calendar cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        Date yesterday = cal.getTime()
        List<User> newUsers = User.findAllByRegistrationTimeGreaterThanEquals(yesterday)

        if (newUsers && Environment.current == Environment.PRODUCTION) {
            String htmlBody
            Calendar calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1)
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
            String date = dateFormat.format(calendar.getTime())

            newUsers.each { User u ->
                if (htmlBody) {
                    htmlBody = "${htmlBody}<a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/user/form/${u.id.toString()}\">${u.name}</a>, ${getAccountForJob(u)?.companyName ?: "No company account"}, ${u.enabled ? "Activated" : "Not Activated"}, ${u.username}, ${u.phone}<br />"
                } else {
                    htmlBody = "<a href=\"https://${Constants.PROD_DOMAIN_NAME}/app/sec/user/form/${u.id.toString()}\">${u.name}</a>, ${getAccountForJob(u)?.companyName ?: "No company account"}, ${u.enabled ? "Activated" : "Not Activated"}, ${u.username}, ${u.phone}<br />"
                }
            }

            optimiMailService.sendMail({
                to emailConfiguration.userLicenceNotificationEmail
                from emailConfiguration.contactEmail
                replyTo emailConfiguration.contactEmail
                subject(optimiMailService.addHostnameToSubject("PROD: ${newUsers.size()} new users created ${date}"))
                html htmlBody
            }, emailConfiguration.userLicenceNotificationEmail)
        } else{
            log.error("Sending ${newUsers?.size()} users info")
        }
    }

    private String getEnvPrefix() {
        if (Environment.current == Environment.PRODUCTION) {
            return "PROD: "
        } else {
            return "DEV: "
        }
    }

    List<User> getUsersByIds(List userIds) {
        List<User> users

        if (userIds) {
            users = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(userIds)]])?.
                    collect({ it as User })
        }
        return users
    }

    DecimalFormat getDefaultDecimalFormat() {
        DecimalFormat decimalFormat
        User user = getCurrentUser()

        if (user) {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(getUserLocale(user?.localeString))
        } else {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("fi"))
        }
        decimalFormat.applyPattern('###.##')
        return decimalFormat
    }

    @Secured(["ROLE_AUTHENTICATED"])
    void logoutUser(HttpServletRequest request, HttpServletResponse response, String rememberMeCookieName) {
        Cookie cookie = new Cookie(rememberMeCookieName, null)
        cookie.setMaxAge(0)
        cookie.setPath(StringUtils.isNotEmpty(request.getContextPath()) ? request.getContextPath() : "/")
        response.addCookie(cookie)
        SecurityContextHolder.getContext().setAuthentication(null)
        request.getSession(false)?.invalidate()
    }

    @Deprecated
    User getUserFromSession(HttpSession session) {
        return getCurrentUser()
    }

    @Deprecated
    void updateUserToSession(User user) {
        if (user) {
            updateUser(user)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Notification getUnacknowledgedNotification(HttpSession session) {
        Notification notification
        User user = getCurrentUser()

        if (user) {
            Date registrationTime = user.registrationTime
            ChannelFeature channelFeature = getChannelFeatureService().getChannelFeature(session)
            def acknowledgedNotifications = User.get(user.id)?.acknowledgedNotifications
            def notifications = Notification.findAllByEnabled(true)

            if (notifications) {
                if (registrationTime) {
                    if (channelFeature) {
                        notifications = notifications.findAll({
                            it.channelFeatureIds?.contains(channelFeature.id.toString()) && (!it.showIfAccountCreatedBefore || registrationTime.before(it.showIfAccountCreatedBefore))
                        })
                    } else {
                        notifications = notifications.findAll({
                            !it.channelFeatureIds && (!it.showIfAccountCreatedBefore || registrationTime.before(it.showIfAccountCreatedBefore))
                        })
                    }
                } else {
                    if (channelFeature) {
                        notifications = notifications.findAll({
                            it.channelFeatureIds?.contains(channelFeature.id.toString())
                        })
                    } else {
                        notifications = notifications.findAll({ !it.channelFeatureIds })
                    }
                }

                if (notifications) {
                    if (acknowledgedNotifications) {
                        def acknowledgedNotificationIds = []

                        acknowledgedNotifications?.each { key, value ->
                            acknowledgedNotificationIds.add(key)
                        }
                        notifications = notifications.findAll({
                            !acknowledgedNotificationIds.contains(it.id.toString())
                        })?.
                                sort({ it.dateCreated })
                    }

                    if (notifications) {
                        notification = notifications[0]
                    }
                }
            }
        }
        return notification
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Notification acknowledgeNotification(String notificationId, HttpSession session) {
        if (notificationId) {
            Notification notification = Notification.get(notificationId)

            if (notification) {
                User user = getCurrentUser()
                user = User.read(user.id)

                if (!user.acknowledgedNotifications?.keySet()?.contains(notification.id.toString())) {
                    if (user.acknowledgedNotifications) {
                        user.acknowledgedNotifications.put(notification.id.toString(), new Date())
                    } else {
                        user.acknowledgedNotifications = [(notification.id.toString()): new Date()]
                    }
                    updateUser(user)
                }
            }
        }
    }
    @Secured(["ROLE_AUTHENTICATED"])
    void acknowledgeUpdate(String updateId, HttpSession session) {
        if (updateId) {
            UpdatePublish update = UpdatePublish.get(updateId)

            if (update) {
                User user = getCurrentUser()
                if(user){
                    if (user.acknowledgedUpdates) {
                        if (!user.acknowledgedUpdates.contains(update.id.toString())){
                            user.acknowledgedUpdates.add(update.id.toString())
                        }

                    } else {
                        user.acknowledgedUpdates = [update.id.toString()]
                    }
                    updateUser(user)
                }

            }
        }
    }
    boolean isAtLeastIE11(HttpServletRequest request) {
        boolean atLeastIE11 = false
        String userAgent = request?.getHeader("User-Agent")

        if (userAgent && userAgent.toLowerCase().contains("trident") && !userAgent.toLowerCase().contains("msie")) {
            atLeastIE11 = true
        }
        return atLeastIE11
    }

    boolean isEdge(HttpServletRequest request) {
        boolean edgeBrowser = false
        String userAgent = request?.getHeader("User-Agent")

        if (userAgent && userAgent.toLowerCase().contains("edge")) {
            edgeBrowser = true
        }
        return edgeBrowser
    }

    @Secured(["ROLE_AUTHENTICATED"])
    void logUserBrowserAndOs() {
        try {
            loggerUtil.info(log, "Browser and OS: " + optimiUserAgentIdentService.getBrowser() + " " +
                    optimiUserAgentIdentService.getBrowserVersion() + ", " + optimiUserAgentIdentService.getOperatingSystem())
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in logging user browser and os: ${e}")
        }
    }

    String getBrowser() {
        return optimiUserAgentIdentService.getBrowser()
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Boolean isValidBrowser(HttpServletRequest request) {
        Boolean valid = false

        try {
            if (optimiUserAgentIdentService.isMsie(ComparisonType.EQUAL, "10") || optimiUserAgentIdentService.isMsie(ComparisonType.GREATER, "10")) {
                valid = true
            } else if (optimiUserAgentIdentService.isFirefox(ComparisonType.EQUAL, "18") || optimiUserAgentIdentService.isFirefox(ComparisonType.GREATER, "18")) {
                valid = true
            } else if (optimiUserAgentIdentService.isChrome(ComparisonType.EQUAL, "23") || optimiUserAgentIdentService.isChrome(ComparisonType.GREATER, "23")) {
                valid = true
            } else if (optimiUserAgentIdentService.isSafari(ComparisonType.EQUAL, "6.0") || optimiUserAgentIdentService.isSafari(ComparisonType.GREATER, "6.0")) {
                valid = true
            } else if (isAtLeastIE11(request)) {
                valid = true
            } else if (isEdge(request)) {
                valid = true
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in resolving user-agent: ${e}")
            valid = false
        }
        return valid
    }

    String getBrowserAndOs() {
        return "Browser: ${optimiUserAgentIdentService.getBrowser()}, Version: ${optimiUserAgentIdentService.getBrowserVersion()}, OS: ${optimiUserAgentIdentService.getOperatingSystem()}"
    }

    Boolean isIe10() {
        Boolean ie10 = false

        try {
            if (optimiUserAgentIdentService.isMsie(org.geeks.browserdetection.ComparisonType.GREATER, "9")) {
                ie10 = true
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in resolving, if user agent is IE10: ${e}")
        }
        return ie10
    }

    Boolean isIe(request) {
        Boolean ie = false

        try {
            if (optimiUserAgentIdentService.isMsie() || isIe10() || isAtLeastIE11(request)) {
                ie = true
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in resolving, if user agent is IE: ${e}")
        }
        return ie
    }

    List getUsersByEntities(List<Entity> entities) {
        //loggerUtil.info(log, "Start of getting users")
        def users = []

        if (entities) {
            def userIds = []

            entities.each { Entity e ->
                if (e.userIds) {
                    userIds.addAll(e.userIds)
                }
            }

            if (userIds) {
                userIds = userIds.unique()
                users = User.getAll(DomainObjectUtil.stringsToObjectIds(userIds))?.findAll({ it != null })
            }
        }
        //loggerUtil.info(log, "End of getting users")
        if (users) {
            return users.unique({ it.id }).sort()
        } else {
            return null
        }
    }

    List<Document> getUsersAsDocumentsByEntities(List<Entity> entities) {
        List<Document> users = []

        if (entities) {
            def userIds = []

            entities.each { Entity e ->
                def ids = e.userIds
                if (ids) {
                    userIds.addAll(ids)
                }
            }

            if (userIds) {
                userIds = userIds.unique()
                users = User.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(userIds)]],[name: 1])?.toList()
            }
        }
        if (users) {
            return users
        } else {
            return null
        }
    }

    User createUser(User user, HttpSession session) {
        if (user.validate()) {
            Role role = Role.findByAuthority(Constants.ROLE_AUTHENTICATED) ?: new Role(authority: Constants.ROLE_AUTHENTICATED).save(flush: true, failOnError: true)
            user.authorities = [
                    role
            ]
            Date currentDate = new Date()
            user.enabled = true
            String suffixToCheck = user?.username?.split("@")[1]
            String preffixToCheck = user?.username?.split("@")[0]

            UserBlacklist blacklistList = UserBlacklist.collection.find([suffix: suffixToCheck]) as UserBlacklist
            log.info("BLACKLIST: ${blacklistList}")
            if (blacklistList) {
                if((blacklistList.prefix && preffixToCheck.equalsIgnoreCase(blacklistList.prefix)) || !blacklistList.prefix){
                    UserDisabled disabled = new UserDisabled()
                    disabled.time = currentDate
                    disabled.reason = "bad email ending detected"
                    disabled.disabledBy = blacklistList.ruleAddedBy ? blacklistList.ruleAddedBy : "Unknown"
                    user.userDisabled = disabled
                    user.enabled = false
                }
            }
            user.activationDeadline = DateUtils.addHours(currentDate, 24)
            ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

            if (channelFeature) {
                user.channelToken = channelFeature.token
            }
            if (obfuscateEmail(user)) {
                user.detailsObfuscated = true
            }
            log.info("CHANNEL_TOKEN: ${user.channelToken}")
            user.registrationToken = UUID.randomUUID().toString().replaceAll('-', '')
            log.info("TOKEN 0: New token created for user ${user.username}: ${user.registrationToken}")
            user = user.save(failOnError: true, flush: true)
            log.info("New user created: " + user?.username)
        }
        return user
    }

    @Secured(["ROLE_AUTHENTICATED"])
    void deleteUser(String username) {
        if (username) {
            def user = User.findByUsername(username.trim().toLowerCase())

            if (user) {
                deleteUser(user)
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    void deleteUser(User user) {
        if (user) {
            def licenses = licenseService.getLicensesByLicenseUserId(user.id.toString())

            if (licenses) {
                licenses.each { License license ->
                    loggerUtil.info(log, "Removing user: ${user.username} from license: ${license.name}")
                    license = licenseService.removeLicenseUser(license.id, user.id)
                }
            }
            licenses = licenseService.getLicensesByLicensedUserId(user.id.toString())

            if (licenses) {
                licenses.each { License license ->
                    loggerUtil.info(log, "Removing user: ${user.username} from license: ${license.name}")
                    license = licenseService.removeUser(license.id, user.id)
                }
            }
            loggerUtil.info(log, "Removing user: trying to delete user ${user.username} from database.")
            DeleteResult removed = User.collection.remove(_id: user.id)
            loggerUtil.info(log, "Removing user: ${removed?.getDeletedCount()} user removed: ${removed?.wasAcknowledged()}")
        }
    }

    Map<User, String> activateUser(String token, HttpSession session) {
        loggerUtil.info(log, "Trying to activate user with token: ${token}")
        User user = User.findByRegistrationToken(token)
        String errorMessage = ""
        Map<User, String> returnable = [:]

        if (user) {
            loggerUtil.info(log, "Found user by token with username: ${user.username}")

            if (!user.emailValidated || user.activationDeadline) {
                loggerUtil.info(log, "Found user object: ${user.username}")

                try {
                    user = enableUser(user)
                    // This has to be done just to avoid validation error
                    user.passwordAgain = user.password
                    user.emailValidated = true
                    setPreProvisionedRights(user)
                    user = updateUser(user)
                    List<UserLicenseInvitation> userLicenseInvitations = UserLicenseInvitation.findAllByEmail(user.username)

                    if (userLicenseInvitations) {
                        userLicenseInvitations.each { UserLicenseInvitation userLicenseInvitation ->
                            licenseService.addLicenseUser(userLicenseInvitation.licenseId, user.username, session)
                            userLicenseInvitation.delete(flush: true)
                        }
                    }
                    loggerUtil.info(log, "User ${user.username} activated successfully")
                    returnable.put(user, errorMessage)
                } catch (OptimisticLockingException op) {
                    loggerUtil.info(log, "User ${user.username} activation optimisticLocking, most likely clicked the activation link multiple times.")
                    returnable.put(user, errorMessage)
                } catch (Exception e) {
                    errorMessage = "Unable to activate user: ${e}"
                    returnable.put(user, errorMessage)
                    loggerUtil.error(log, "Error in activating ${user.username}", e)
                }
            } else {
                errorMessage = "User is already activated"
                returnable.put(user, errorMessage)
                loggerUtil.warn(log, "User already activated with token: ${token}")
            }
        } else {
            errorMessage = "User not found with token ${token}"
            returnable.put(new User(), errorMessage)
            loggerUtil.warn(log, "User not found with registration token: ${token}")
        }

        return returnable
    }

    private void setPreProvisionedRights(User user) {
        if (user) {
            String username = user.username
            BuildableCriteria criteria = Entity.createCriteria()

            def entities = criteria.list {
                preProvisionedUserRights {
                    eq('username', username)
                }
            }

            if (entities) {
                entities.each { Entity entity ->
                    PreProvisionedUserRight preProvisionedUserRight = entity.preProvisionedUserRights.find({
                        it.username.equals(username)
                    })
                    String userRight = preProvisionedUserRight?.userRight

                    if (Constants.EntityUserType.MANAGER.toString().equals(userRight)) {
                        entityService.addEntityUser(entity.id, username, Constants.EntityUserType.MANAGER.toString())
                    } else if (Constants.EntityUserType.MODIFIER.toString().equals(userRight)) {
                        entityService.addEntityUser(entity.id, username, Constants.EntityUserType.MODIFIER.toString())
                    } else if (Constants.EntityUserType.READONLY.toString().equals(userRight)) {
                        entityService.addEntityUser(entity.id, username, Constants.EntityUserType.READONLY.toString())
                    }
                }
            }
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    void activateUserById(userId, HttpSession session) {
        if (userId) {
            User user = getUserById(userId)
            if (user) {
                user.authorities = [
                        Role.findByAuthority(Constants.ROLE_AUTHENTICATED)
                ]
                // This has to be done just to avoid validation error
                user.passwordAgain = user.password
                user.emailValidated = true
                setPreProvisionedRights(user)
                user = enableUser(user)
                user.merge(flush: true, failOnError: true)
                List<UserLicenseInvitation> userLicenseInvitations = UserLicenseInvitation.findAllByEmail(user.username)

                if (userLicenseInvitations) {
                    userLicenseInvitations.each { UserLicenseInvitation userLicenseInvitation ->
                        licenseService.addLicenseUser(userLicenseInvitation.licenseId, user.username, session)
                        userLicenseInvitation.delete(flush: true)
                    }
                }
            }
        }
    }

    User getUserByToken(token) {
        User user = null

        if (token) {
            user = User.findByRegistrationToken(token)
        }
        return user
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    User updateUser(User user) {
        if (user && user.id) {
            try {
                user = user.merge(failOnError: true, flush: true)
            } catch (Exception e) {
                // User object in session is dirty, get updated user from DB and handle optimistic locking
                String failedToFailover

                User currentUser = getCurrentUser(true)

                if (currentUser && currentUser.id.equals(user.id)) {
                    User updatedUser = User.collection.findOne([username: currentUser.username]) as User

                    if (updatedUser) {
                        user = updatedUser
                    } else {
                        failedToFailover = "User not found from database with: ${currentUser?.username}"
                    }
                } else {
                    failedToFailover = "Current user: ${user?.username} (${user?.id}) doesnt match user from sec context: ${currentUser?.username} (${currentUser?.id})"
                }

                if (failedToFailover) {
                    List<String> calledMethodNames = StackTraceUtils.sanitize(new Throwable()).stackTrace?.collect({
                        it.methodName
                    })?.unique()

                    if (calledMethodNames) {
                        calledMethodNames.removeAll(Constants.REMOVE_FROM_STACKTRACE)
                    }
                    loggerUtil.error(log, "Failed to save user with failover, reason: ${failedToFailover}, User: ${user?.username}: Exception is: ${e}, called from: ${calledMethodNames}", e)
                    flashService.setErrorAlert("Failed to save user with failover, reason: ${failedToFailover}, User: ${user?.username}: Exception is: ${e.message}, called from: ${calledMethodNames}", true)
                }
            }
        }
        return user
    }

    void resetPassword(User user) {
        if (!user.id) {
            throw new RuntimeException("Cannot update user without id.")
        }
        user.merge(failOnError: true, flush: true)
    }

    User enableUser(User user) {
        user.enabled = true
        user.userDisabled = null
        user.enableTime = new Date()
        user.activationDeadline = null
        Boolean ok = activeCampaignService.addTagForUser(user.activeCampaignId,[ActiveCampaignService.ACTION_ACCOUNT_OCL_ACTIVATED])
        if(ok){
            loggerUtil.info(log, "ActiveCampaign: add activation tag to user ${user.username} successfully")
        } else {
            log.error("ERROR ActiveCampaign: Failed to add activation tag to user ${user.username}")
            flashService.setErrorAlert("ERROR ActiveCampaign: Failed to add activation tag to user ${user.username}", true)
        }
        return user
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<User> getAll() {
        User.list()
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<User> getAllEnabled() {
        return User.findAllByEnabled(true)
    }

    /**
     * Retrieves the list of all existing users that given user can manage.
     */
    @Secured(["ROLE_SALES_VIEW"])
    List<User> getManageableUsers(User user, Boolean activated = Boolean.FALSE) {

        if (activated) {
            return User?.findAllByEmailValidated(true)
        } else {
            return User.findAllByEmailValidatedOrActivationDeadlineIsNotNull(false)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<User> getTaskAuthenticatedUsers(User user) {
        def users = []

        if (isSystemAdmin(user) || getSuperUser(user)) {
            users.addAll(User.list())
        }
        def taskUsers = []

        users?.each { User usr ->
            if (getTaskAuthenticated(usr)) {
                taskUsers.add(usr)
            }
        }
        return taskUsers
    }

    /**
     * Returns the user with given id.
     */
    @Secured(["ROLE_AUTHENTICATED"])
    User getUserById(userId) {
        if (userId) {
            return User.get(userId)
        }
        return null
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Document getUserAsDocumentById(String userId, BasicDBObject projection = null) {
        if (userId) {
            if (projection) {
                return User.collection.findOne([_id: DomainObjectUtil.stringToObjectId(userId)], projection)
            } else {
                return User.collection.findOne([_id: DomainObjectUtil.stringToObjectId(userId)])
            }
        }
        return null
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Document getUserAsDocumentByUsername(String username) {
        if (username) {
            return User.collection.findOne([username: username])
        } else {
            return null
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<User> getUsers() {
        User currentUser = getCurrentUser()
        List<User> users = []

        if (isSystemAdmin(currentUser) || getSuperUser(currentUser)) {
            users.addAll(getAll())
        } else {
            users.add(currentUser)
        }
        return users
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<User> getUsersForForm() {
        List<User> users = null
        def result = User.collection.find([enabled:true,userDisabled:null], [name: 1, username: 1])

        if (result) {
            users = result.collect({ it as User })
        }
        return users
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<User> getUsersForAccount(String emailForm) {
        List<User> users
        def result = User.collection.find([
                enabled     : true,
                userDisabled: null,
                username    : [$regex: /${emailForm}$/]],
                [name: 1, username: 1]
        )

        if (result) {
            users = result.collect({ it as User })
        }
        return users
    }

    @Secured(["ROLE_AUTHENTICATED"])
    User getCurrentUser(Boolean fromSecContext = Boolean.FALSE) {
        User currentUser = null

        if(fromSecContext && log.traceEnabled) {
            log.trace("Parameter fromSecContext is not supported.")
        }

        try {
            if (isLoggedIn()) {
                currentUser = springSecurityService.getCurrentUser() as User
            }
        } catch (Exception e) {
            log.error("Error getting a current user: ", e)
        }
        return currentUser
    }

    boolean isLoggedIn() {
        return springSecurityService.isLoggedIn()
    }

    @Secured(["ROLE_AUTHENTICATED"])
    User getUserByUsername(username) {
        if (username) {
            return User.findByUsername(username.trim().toLowerCase())
        } else {
            return null
        }
    }

    void handleUnusableFilters(HttpSession session, User user, IndicatorQuery indicatorQuery, Entity child, List<Question> queryQuestions, Indicator indicator, Query query) {

        if (user && indicatorQuery && indicatorQuery.resourceFilterCriteria && child && (user.userFilterChoices||user.userSplitFilterChoices) && queryQuestions) {
            List<String> questionsResourceGroups = queryQuestions.findResults({it.resourceGroups ?: null}).flatten().unique()
            //List<String> questionsSkipResourceTypes = queryQuestions.findResults({it.skipResourceTypes ?: null}).flatten().unique()
            List<String> persistingBooleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)
            //List<String> questionsResourceGroups = []
            List<String> questionsSkipResourceTypes = []
            Boolean needsSave = Boolean.FALSE
            Map<String, List<Object>> resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(child, IndicatorQuery.RESOURCE, indicatorQuery)

            String quintilesString = indicator.connectedBenchmarks ? "quintiles${indicator.connectedBenchmarks.first()}" : null

            if (user.userFilterChoices) {
                List keysToRemove = []
                user.userFilterChoices.each { String key, String value ->
                    if (key.startsWith("quintiles")) {
                        if (!(key.equals(quintilesString) && query?.supportedFilters?.find({"co2Quintile".equals(it.resourceAttribute)}))) {
                            keysToRemove.add(key)
                        }
                    } else if (!persistingBooleanProperties.contains(key)) {
                        List values = optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, questionsSkipResourceTypes, key, resourceFilterCriteria)

                        //if the values list has a parameter with comma (,)remove the comma to match with saved data which is saved without comma
                        for(int i = 0; i <values.size(); i++){
                            if(values.get(i).contains(","))
                                values[i] = values.get(i).replace(",", "");
                        }

                        if (!values?.contains(value)) {
                            keysToRemove.add(key)
                        }
                    }
                }

                keysToRemove.each {
                    if("state".equalsIgnoreCase(it) && keysToRemove.contains("areas")){
                        user.userFilterChoices.remove(it)
                        needsSave = Boolean.TRUE
                    } else if (!"state".equalsIgnoreCase(it)) {
                        user.userFilterChoices.remove(it)
                        needsSave = Boolean.TRUE
                    }
                }
            }

            if (user.userSplitFilterChoices) {
                List keysToRemove = []
                user.userSplitFilterChoices.each { String key, String value ->
                    if (!persistingBooleanProperties.contains(key)) {
                        List values = optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, questionsSkipResourceTypes, key, resourceFilterCriteria)

                        if (!values?.contains(value)) {
                            keysToRemove.add(key)
                            needsSave = Boolean.TRUE
                        }
                    }
                }

                keysToRemove.each {
                    if("state".equalsIgnoreCase(it) && keysToRemove.contains("areas")){
                        user.userSplitFilterChoices.remove(it)
                        needsSave = Boolean.TRUE
                    } else if (!"state".equalsIgnoreCase(it)) {
                        user.userSplitFilterChoices.remove(it)
                        needsSave = Boolean.TRUE
                    }
                }
            }

            if (needsSave) {
                user = updateUser(user)
            }
        }
    }

    void disableUsersByBlacklistRules(UserBlacklist rule, Boolean hasPrefix = false) {
        if (rule) {

            if(hasPrefix){
                String username = rule.prefix + "@"+rule.suffix

                UserDisabled disabled = new UserDisabled()
                disabled.time = new Date()
                disabled.disabledBy = rule.ruleAddedBy ? rule.ruleAddedBy : "Unknown"
                disabled.reason = "specific email detected"
                User user = getUserByUsername(username)
                if(user){
                    user.enabled = false
                    user.userDisabled = disabled
                    user.merge(flush: true)
                }
            } else {
                def suffixRegex =  ".*" + rule.suffix
                List<Document> usersFound = User.collection.find(['username': ['$regex': suffixRegex]], [id: 1, userDisabled: 1]).toList()

                if(usersFound){

                    usersFound.each {Document userText ->
                        User user1 = getUserById(userText.id)

                        UserDisabled disabled = new UserDisabled()
                        disabled.time = new Date()
                        disabled.disabledBy = rule.ruleAddedBy ? rule.ruleAddedBy : "Unknown"
                        disabled.reason = "bad email ending detected"

                        user1.enabled = false
                        user1.userDisabled = disabled
                        user1.merge(flush: true)
                    }
                }


            }
        }
    }
    boolean enableUsersByBlacklistRules(UserBlacklist rule, Boolean hasPrefix = false) {
        if (rule) {

            if(hasPrefix){
                String username = rule.prefix + "@"+rule.suffix

                User user = getUserByUsername(username)
                if(user){
                    user = enableUser(user)
                    user.merge(flush: true, failOnError: true)
                }
            } else {
                def suffixRegex =  ".*" + rule.suffix
                List<Document> usersFound = User.collection.find(['username': ['$regex': suffixRegex]], [id: 1, userDisabled: 1]).toList()

                if(usersFound){
                    usersFound.each {Document userText ->
                        User user1 = getUserById(userText.id)
                        user1 = enableUser(user1)
                        user1.merge(flush: true, failOnError: true)

                    }
                }

            }
            return true
        }
        return false
    }

    /**
     * Obfuscates user's name, email and phone for development and custom {@link Environment}
     * Can be hooked once before {@link User} is inserted
     *
     * @param {@link User}
     * @return 'true' if successful
     */
    Boolean obfuscateEmail(User user) {
        if (user && (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM) &&
                !user.detailsObfuscated) {

            if (!emailConfiguration.isProtectedEmail(user.username)) {
                log.info("Obfuscating user ${user.username}")
                String random = UUID?.randomUUID()?.toString()?.take(25)
                user.name = random
                user.username = random + "@${user.username?.split("@")[1]}"
                user.phone = "0000000000"
                user.firstname = random
                user.lastname = random
            }
            return Boolean.TRUE
        }
        return Boolean.FALSE
    }

    /**
     *
     * @param {@link User}
     * @return date format of user locale, or European by default
     */
    String getUserDateFormat(User user) {
        if (user && user.localeString) {
           return Constants.DATE_FORMAT.get(user?.localeString) ?: Constants.DATE_FORMAT.get(User.LocaleString.EUROPEAN.locale)
        }
        return Constants.DATE_FORMAT.get(User.LocaleString.EUROPEAN.locale)
    }

    void saveRoleMigrationMapping(Map params){
        String targetValue = params.targetRole
        String username = params.username
        RoleMigrationMapping roleMapping = null

        if(params.mappingId){
            roleMapping = RoleMigrationMapping.get(params.mappingId as Long)
            roleMapping.setTargetRole(targetValue)
        } else {
            roleMapping = new RoleMigrationMapping(targetRole: targetValue, username: username)
        }

        roleMapping.save(failOnError: true, flash: true)
    }

    void deleteRoleMigrationMapping(Map params){
        RoleMigrationMapping roleMapping = RoleMigrationMapping.get(params.mappingId)
        roleMapping.delete(failOnError: true, flash: true)
    }

    void uploadOnKeyUpUsers(String q){
        User.withCriteria{
            or{
                ilike("username", "%${q}%")
                ilike("lastName", "%${q}%")
                ilike("firstName", "%${q}%")
            }

            maxResults(10)
        }.collect{it.username}
    }

    void transformUserRoles(){
        List mappings = RoleMigrationMapping.list()

        mappings.each{RoleMigrationMapping mapping ->
            Role targetRole = Role.findByAuthority(mapping.targetRole)

            def updateResult = User.collection.updateOne(
                    ["username": mapping.username], [$set: [authorities: [targetRole]]]
            )

            log.info("UPDATE MAPPING RESULT FOR (targetRole: ${mapping.targetRole}, username: ${mapping.username}): Matched: ${updateResult.matchedCount}, Modified: ${updateResult.modifiedCount}")
        }
    }

    void obfuscateUsersDetails() {
        List<String> projectionResult = User.withCriteria {
            or {
                isNull("detailsObfuscated")
                eq("detailsObfuscated", Boolean.FALSE)
            }
            projections {
                property("username")
            }
        }

        if (projectionResult) {
            projectionResult.groupBy { String username ->
                emailConfiguration.isProtectedEmail(username)
            }.each { boolean skipObfuscation, List<String> usernames ->
                if (skipObfuscation) {
                    User.collection.updateMany(["username": [$in: usernames]], [$set: [detailsObfuscated: true]])
                } else {
                    usernames.each { String username ->
                        String random = UUID?.randomUUID()?.toString()?.take(25)
                        String newUsername = random + "@${username?.split("@")[1]}"
                        User.collection.updateOne(["username": username], [$set: [name: random, phone: "0000000000", username: newUsername, detailsObfuscated: true, firstname: random, lastname: random]])
                    }
                }
            }
        }
    }

    User enableTokenAuthForNextLogin(String username) {
        User user = getUserByUsername(username)

        if (user) {
            if (user.emailTokenAuthForNextLoginEnabled) {
                log.warn "Token authentication for next login was already set for '${username}' user."
            } else {
                try {
                    user.emailTokenAuthForNextLoginEnabled = true
                    user = user.merge(flush: true, failOnError: true)

                    log.info "Token authentication fot next login was enabled for '${username}' user."
                } catch (Exception e) {
                    log.error "The following exception happened during enabling of the token authentication for next login for '${username}' user: ", e
                }
            }
        } else {
            log.warn "User '${username}' doesn't exist in the database."
        }

        return user
    }

    void updateEmailTokenAuths(ObjectId userId, Boolean emailTokenAuthForNextLoginEnabled = false) {
        if (emailTokenAuthForNextLoginEnabled) {
            User.collection.updateOne(["_id": userId], [$push: [emailTokenAuths: new Date()], $set: [emailTokenAuthForNextLoginEnabled: false]])
        } else {
            User.collection.updateOne(["_id": userId], [$push: [emailTokenAuths: new Date()]])
        }
    }

    List<Entity> getCreatedEntitiesById(ObjectId id) {
        return Entity.findAllByCreatorId(id)
    }

    List<Entity> getUpdatedEntitiesById(ObjectId id) {
        def results = Entity.collection.find(["lastUpdaterId": id])

        if (results) {
            return results.collect({ it as Entity })
        } else {
            return null
        }
    }

    boolean getEditableApplications(User user) {
        boolean hasEditable = false

        if (applicationService.getApplicationsForUser(user)) {
            hasEditable = true
        }
        return hasEditable
    }

    Locale getUserLocale(String localeString) {
        if (localeString) {
            return Locale.forLanguageTag(localeString)
        } else {
            return new Locale("fi")
        }
    }

    Resource getCountryResource(String country) {
        if (country) {
            return Resource.findByResourceIdAndActive(country, true)
        } else {
            return null
        }
    }

    public List<License> getLicenses(Boolean skipAdminLicensed = false, User user) {
        if (!user) {
            return null
        }
        List<License> licenses = []

        if (!skipAdminLicensed && (getSuperUser(user) || getDataManager(user) || getConsultant(user))) {
            licenses = licenseService.getValidLicenses()
        } else {
            def allUsableLicenseIds = []

            if (user?.licenseIds && !user.licenseIds.isEmpty()) {
                allUsableLicenseIds.addAll(user.licenseIds)
            }

            if (user?.usableLicenseIds && !user.usableLicenseIds.isEmpty()) {
                allUsableLicenseIds.addAll(user.usableLicenseIds)
            }

            if (!allUsableLicenseIds.isEmpty()) {
                licenses = License.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(allUsableLicenseIds))
            }
        }
        return licenses
    }

    public Boolean getHasLicenses(User user) {
        if (user) {
            if (getSuperUser(user) || getDataManager(user) || getConsultant(user)) {
                return true
            } else {
                if (user.licenseIds || user.managedLicenseIds || user.usableLicenseIds) {
                    return true
                }
            }
        }
        return false
    }

    public Boolean getIsPlanetaryOnlyUser(User user) {
        if (!user) {
            return false
        }
        if (getSuperUser(user) || getDataManager(user) || getConsultant(user)) {
            return false
        } else {
            Date currentDate = new Date()
            List ids = []
            if (user.usableLicenseIds) {
                ids.addAll(user.usableLicenseIds)
            }

            if (user.licenseIds) {
                ids.addAll(user.licenseIds)
            }

            List<Document> entities = getModifiableEntitiesAsDocument(user.modifiableEntityIds, user.managedEntityIds)

            if (entities) {
                List<String> entityIds = entities.findResults({ (it._id) ? it._id.toString() : null })

                if (ids || entityIds) {
                    List<License> licenses
                    if (ids && entityIds) {
                        licenses = License.collection.find([frozen: null, $or: [[_id: [$in: DomainObjectUtil.stringsToObjectIds(ids)]], [licensedEntityIds: [$in: entityIds]]], validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.toList()?.collect({ it as License })
                    } else if (ids) {
                        licenses = License.collection.find([frozen: null, _id: [$in: DomainObjectUtil.stringsToObjectIds(ids)], validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.toList()?.collect({ it as License })
                    } else if (entityIds) {
                        licenses = License.collection.find([frozen: null, licensedEntityIds: [$in: entityIds], validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.toList()?.collect({ it as License })
                    }

                    if (licenses) {
                        boolean isPlanetaryOnly = true
                        for (License license in licenses) {
                            if (!license.planetary) {
                                if (Constants.LicenseType.PRODUCTION.toString().equals(license.type)) {
                                    isPlanetaryOnly = false
                                    break
                                } else if (Constants.LicenseType.TRIAL.toString().equals(license.type) || Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                                    Date dateAdded = license.getChangeHistoryByEntityIds(entityIds)?.dateAdded

                                    if (dateAdded && !(license.trialLength && Duration.between(DateUtil.dateToLocalDatetime(dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() > license.trialLength)) {
                                        isPlanetaryOnly = false
                                        break
                                    }
                                } else {
                                    isPlanetaryOnly = false
                                    break
                                }
                            }
                        }
                        return isPlanetaryOnly
                    } else {
                        return false
                    }
                } else {
                    return false
                }
            } else if (ids) {
                List<License> licenses = License.collection.find([frozen: null, _id: [$in: DomainObjectUtil.stringsToObjectIds(ids)], validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.toList()?.collect({ it as License })

                if (licenses) {
                    boolean isPlanetaryOnly = true
                    for (License license in licenses) {
                        if (!license.planetary) {
                            if (Constants.LicenseType.PRODUCTION.toString().equals(license.type) || Constants.LicenseType.DEMO.toString().equals(license.type)) {
                                isPlanetaryOnly = false
                                break
                            }
                        }
                    }
                    return isPlanetaryOnly
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }

    List<Document> getUsableValidFloatingLicensesAsJSONForMain(User user) {
        List<Document> floatingLicenses = []
        Date currentDate = new Date()
        def allUsableLicenseIds = []

        if (user?.licenseIds) {
            user.licenseIds.each {
                if (it instanceof String) {
                    allUsableLicenseIds.add(DomainObjectUtil.stringToObjectId(it))
                } else {
                    allUsableLicenseIds.add(it)
                }
            }
        }

        if (user?.usableLicenseIds) {
            user.usableLicenseIds.each {
                if (it instanceof String) {
                    allUsableLicenseIds.add(DomainObjectUtil.stringToObjectId(it))
                } else {
                    allUsableLicenseIds.add(it)
                }
            }
        }
        allUsableLicenseIds = allUsableLicenseIds.unique()

        if (allUsableLicenseIds) {
            floatingLicenses = License.collection.find([_id: [$in: allUsableLicenseIds], frozen: null, validFrom: [$lte: currentDate], validUntil: [$gte: currentDate], floatingLicense: [$nin: [null, Constants.FloatingLicense.NO.toString()]]], [_id: 1, name: 1, floatingLicenseCheckedInUsers: 1])?.toList()

            if (floatingLicenses) {
                floatingLicenses.each {
                    it["isFloatingLicense"] = true
                }
            }
        }
        return floatingLicenses
    }

    List<License> getUsableValidFloatingLicenses(User user) {
        List<License> floatingLicenses = []
        Date currentDate = new Date()
        def allUsableLicenseIds = []

        if (user?.licenseIds) {
            user.licenseIds.each {
                if (it instanceof String) {
                    allUsableLicenseIds.add(DomainObjectUtil.stringToObjectId(it))
                } else {
                    allUsableLicenseIds.add(it)
                }
            }
        }

        if (user?.usableLicenseIds) {
            user.usableLicenseIds.each {
                if (it instanceof String) {
                    allUsableLicenseIds.add(DomainObjectUtil.stringToObjectId(it))
                } else {
                    allUsableLicenseIds.add(it)
                }
            }
        }
        allUsableLicenseIds = allUsableLicenseIds.unique()
        if (allUsableLicenseIds) {
            floatingLicenses = License.collection.find([_id: [$in: allUsableLicenseIds], licenseUserIds: user.id.toString(), frozen: null, validFrom: [$lte: currentDate], validUntil: [$gte: currentDate], floatingLicense: [$nin: [null, Constants.FloatingLicense.NO.toString()]]])?.toList()?.collect({ it as License })
        }
        return floatingLicenses
    }

    List getManagedLicenses(User user) {
        def licenses = []

        if (getSuperUser(user) || getConsultant(user)) {
            licenses = licenseService.getValidLicenses()
        } else if (user?.managedLicenseIds) {
            licenses = licenseService.getValidLicenses(user.managedLicenseIds)
        }
        return licenses
    }

    List<License> getUsableAndManagedLicenses(User user) {
        List<License> licenses = []
        def managedLicenses = getManagedLicenses(user)

        if (managedLicenses) {
            licenses.addAll(managedLicenses)
        }
        def usableLicenses = getUsableLicenses(user)

        if (usableLicenses) {
            licenses.addAll(usableLicenses)
        }
        return licenses?.unique({ it.id })
    }

    List<License> getUsableAndLicensedLicenses(User user) {
        List<License> usableLicenses = []
        List ids = []

        if (user?.usableLicenseIds) {
            ids.addAll(user.usableLicenseIds)
        }

        if (user?.licenseIds) {
            ids.addAll(user.licenseIds)
        }

        if (ids) {
            List<License> licenses = licenseService.getValidLicenses(ids)

            if (licenses) {
                usableLicenses.addAll(licenses)
            }
        }
        return usableLicenses.unique({ it.id })
    }

    List<License> getUsableLicenses(User user) {
        List<License> usableLicenses = []

        if (getSuperUser(user) || getDataManager(user) || getConsultant(user)) {
            usableLicenses = licenseService.getValidLicenses()
        } else if (user?.usableLicenseIds) {
            usableLicenses = licenseService.getValidLicenses(user.usableLicenseIds)
        }
        return usableLicenses
    }

    List<String> getUsableEntityClasses(User user) {
        Date currentDate = new Date()
        List<String> entityClasses = []

        BasicDBObject filter = new BasicDBObject()
        filter.put("validFrom", [$lte: currentDate])
        filter.put("validUntil", [$gte: currentDate])

        if (getSuperUser(user) || getDataManager(user) || getConsultant(user)) {
            // No extra filtering :D
        } else if (user?.usableLicenseIds) {
            filter.put("_id", [$in: DomainObjectUtil.stringsToObjectIds(user.usableLicenseIds)])
        } else {
            filter.put("type", Constants.LicenseType.TRIAL.toString())
            filter.put("openTrial", Boolean.TRUE)
        }

        entityClasses = License.collection.distinct("compatibleEntityClasses", filter, String.class)?.toList()
        return entityClasses
    }

    List<String> getUsableTrialEntityClasses() {
        Date currentDate = new Date()
        List<String> entityClasses = []

        BasicDBObject filter = new BasicDBObject()
        filter.put("validFrom", [$lte: currentDate])
        filter.put("validUntil", [$gte: currentDate])

        filter.put("type", Constants.LicenseType.TRIAL.toString())
        filter.put("openTrial", Boolean.TRUE)

        entityClasses = License.collection.distinct("compatibleEntityClasses", filter, String.class)?.toList()
        return entityClasses
    }


    List getTaskEntities(List taskEntityIds) {
        def entities = []

        if (taskEntityIds) {
            entities = Entity.getAll(DomainObjectUtil.stringsToObjectIds(taskEntityIds))
        }
        return entities
    }

    List getAdministeredOrganizations(User user) {
        def organizations = []

        user?.administeredOrganizationIds?.each {
            def organization = Organization.get(it)

            if (organization) {
                organizations.add(organization)
            }
        }
        return organizations
    }

    List getOrganizationMemberships(User user) {
        def organizations = []

        user?.organizationMembershipIds?.each {
            def organization = Organization.get(it)

            if (organization) {
                organizations.add(organization)
            }
        }
        return organizations
    }

    List getRequestToJoinOrganizations(List requestToJoinOrganizationIds) {
        def organizations = []

        requestToJoinOrganizationIds?.each {
            organizations.add(Organization.get(it))
        }
        return organizations
    }

    List<Entity> getManagedEntities(List<String> managedEntityIds) {
        def entities

        if (managedEntityIds) {
            entities = Entity.getAll(DomainObjectUtil.stringsToObjectIds(managedEntityIds))
        }
        return entities?.findAll({ Entity e -> !e.deleted })
    }

    List<Entity> getModifiableEntities(User user) {
        def entities

        if (user) {
            if (user.modifiableEntityIds || user.managedEntityIds) {
                def entityIds = []

                if (user.modifiableEntityIds) {
                    entityIds.addAll(user.modifiableEntityIds)
                }

                if (user.managedEntityIds) {
                    entityIds.addAll(user.managedEntityIds)
                }
                entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())]])?.collect({ it as Entity })
            }
        }
        return entities?.findAll({ Entity e -> e && !e.deleted })
    }

    List<Document> getModifiableEntitiesAsDocument(List<String> modifiableEntityIds, List<String> managedEntityIds) {
        List<Document> entities

        if (modifiableEntityIds || managedEntityIds) {
            def entityIds = []

            if (modifiableEntityIds) {
                entityIds.addAll(modifiableEntityIds)
            }

            if (managedEntityIds) {
                entityIds.addAll(managedEntityIds)
            }
            entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())], deleted: [$ne: true]])?.toList()
        }
        return entities
    }

    List<Entity> getModifiableEntitiesForQuery(List<String> modifiableEntityIds, List<String> managedEntityIds) {
        def entities

        if (modifiableEntityIds || managedEntityIds) {
            def entityIds = []

            if (modifiableEntityIds) {
                entityIds.addAll(modifiableEntityIds)
            }

            if (managedEntityIds) {
                entityIds.addAll(managedEntityIds)
            }
            entities = Entity.collection.find(["_id"    : [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())],
                                               "deleted": ["\$ne": true]],
                    ["name": 1, "childEntities": 1, "entityClass": 1])?.collect({ it as Entity })
        }
        return entities
    }

    List<Entity> getModifiableEntitiesForQueryAsJson(List<String> modifiableEntityIds, List<String> managedEntityIds) {
        def entities

        if (modifiableEntityIds || managedEntityIds) {
            def entityIds = []

            if (modifiableEntityIds) {
                entityIds.addAll(modifiableEntityIds)
            }

            if (managedEntityIds) {
                entityIds.addAll(managedEntityIds)
            }
            entities = Entity.collection.find(["_id"    : [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())],
                                               "deleted": ["\$ne": true]],
                    ["name": 1, "id": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()
        }
        return entities
    }

    List<Entity> getReadonlyEntities(List<String> readonlyEntityIds) {
        def entities = []

        if (readonlyEntityIds) {
            entities = Entity.findAllByIdInListAndDeleted(DomainObjectUtil.stringsToObjectIds(readonlyEntityIds), Boolean.FALSE)
        }
        return entities
    }

    List<Entity> getReadOnlyEntitiesForQueryAsJson(List<String> readonlyEntityIds) {
        def entities
        def entityIds = []

        if (readonlyEntityIds) {
            entityIds.addAll(readonlyEntityIds)

            entities = Entity.collection.find(["_id"    : [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())],
                                               "deleted": ["\$ne": true]],
                    ["name": 1, "id": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()
        }
        return entities
    }

    List<Document> getReadOnlyEntitiesForQueryAsDocument(List<String> readonlyEntityIds) {
        List<Document> entities
        def entityIds = []

        if (readonlyEntityIds) {
            entityIds.addAll(readonlyEntityIds)

            entities = Entity.collection.find(["_id"    : [$in: DomainObjectUtil.stringsToObjectIds(entityIds.unique())],
                                               "deleted": ["\$ne": true]],
                    ["name": 1, "id": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()
        }
        return entities
    }

    List getEntityIdsByLicenses(User user) {
        def entityIdsByLicenses = []

        getLicenses(user)?.each { License li ->
            if (li.licensedEntityIds) {
                entityIdsByLicenses.addAll(li.licensedEntityIds)
            }
        }

        if (entityIdsByLicenses) {
            entityIdsByLicenses = entityIdsByLicenses.unique()
        }
        return entityIdsByLicenses
    }

    List<Entity> getAllowedEntities(User user) {
        def allowedEntityIds = getAllowedEntityIds(user)
        def entities = []

        if (allowedEntityIds) {
            entities = Entity.findAllByIdInListAndDeleted(DomainObjectUtil.stringsToObjectIds(allowedEntityIds), Boolean.FALSE)
        }
        return entities

    }

    List<Entity> getAllowedParentEntities(User user, Boolean deleted = Boolean.FALSE) {
        def allowedEntityIds = getAllowedEntityIds(user)
        def entities = []

        if (allowedEntityIds) {
            entities = Entity.findAllByIdInListAndDeletedAndParentEntityId(DomainObjectUtil.stringsToObjectIds(allowedEntityIds), deleted, null)
        }
        return entities
    }

    List<Entity> getExportableParentEntities(User user) {
        List<Entity> entities

        if (user) {
            if (user.modifiableEntityIds || user.managedEntityIds) {
                def entityIds = []

                if (user.modifiableEntityIds) {
                    entityIds.addAll(user.modifiableEntityIds)
                }

                if (user.managedEntityIds) {
                    entityIds.addAll(user.managedEntityIds)
                }

                entities = Entity.findAllByIdInListAndDeletedNotEqualAndParentEntityId(DomainObjectUtil.stringsToObjectIds(entityIds.unique()), true, null)
            }
        }
        return entities
    }

    def getAllowedEntityIds(User user) {
        def allowedEntityIds = []

        if (user) {
            if (user.managedEntityIds) {
                allowedEntityIds.addAll(user.managedEntityIds)
            }

            if (user.modifiableEntityIds) {
                allowedEntityIds.addAll(user.modifiableEntityIds)
            }

            if (user.readonlyEntityIds) {
                allowedEntityIds.addAll(user.readonlyEntityIds)
            }

            if (user.taskEntityIds) {
                allowedEntityIds.addAll(user.taskEntityIds)
            }
        }

        if (allowedEntityIds.size() > 0) {
            allowedEntityIds = allowedEntityIds.unique()
        }
        return allowedEntityIds
    }

    List<GrantedAuthority> parseGrantedAuthorities(User user) {
        List<GrantedAuthority> requiredAuthorities = new ArrayList<GrantedAuthority>()

        if (user.getRoles()) {
            for (String auth : user.getRoles()) {
                auth = auth.trim()

                if (auth.length() > 0) {
                    requiredAuthorities.add(new SimpleGrantedAuthority(auth))
                }
            }
        }
        return requiredAuthorities
    }

    boolean isSystemAdmin(User user) {
        return user && user.getRoles().contains(Constants.ROLE_SYSTEM_ADMIN)
    }

    boolean isDeveloper(User user) {
        return user && user.getRoles().contains(Constants.ROLE_DEVELOPER)
    }

    boolean isSystemAdminOrSuperUserOrDataManagerOrDeveloper(User user) {
        final List<String> requiredRoles = [Constants.ROLE_SYSTEM_ADMIN, Constants.ROLE_SUPER_USER, Constants.ROLE_DEVELOPER, Constants.ROLE_DATA_MGR]

        return user && user.getRoles().any { String role ->
            role in requiredRoles
        }
    }

    boolean getDataManager(User user) {
        return user && user.getRoles().contains(Constants.ROLE_DATA_MGR)
    }

    boolean getConsultant(User user) {
        return user && user.getRoles().contains(Constants.ROLE_CONSULTANT)
    }

    boolean getDataManagerAndAdmin(User user) {
        if (user) {
            if (user.getRoles().contains(Constants.ROLE_DATA_MGR) || user.getRoles().contains(Constants.ROLE_SYSTEM_ADMIN)) {
                return true
            }
        }
        return false
    }

    boolean getSuperUser(User user) {
        if (user) {
            if (user.getRoles().contains(Constants.ROLE_SYSTEM_ADMIN) || user.getRoles().contains(Constants.ROLE_SUPER_USER)) {
                return true
            }
        }
        return false
    }

    boolean getTaskAuthenticated(User user) {
        return user && user.getRoles().contains(Constants.ROLE_TASK_AUTHENTICATED)
    }

    boolean getSalesView(User user) {
        if (user)
            if (user.getRoles().contains(Constants.ROLE_SYSTEM_ADMIN) || user.getRoles().contains(Constants.ROLE_SUPER_USER) ||
                    user.getRoles().contains(Constants.ROLE_SALES_VIEW)) {
            return true
        }
        return false
    }

    boolean getAuthenticated(User user) {
        if (user) {
            if (user.getRoles().contains(Constants.ROLE_SYSTEM_ADMIN) || user.getRoles().contains(Constants.ROLE_SUPER_USER) ||
                    user.getRoles().contains(Constants.ROLE_SALES_VIEW) || user.getRoles().contains(Constants.ROLE_AUTHENTICATED) ||
                    user.getRoles().contains(Constants.ROLE_CONSULTANT)) {
                return true
            }
        }
        return false
    }

    def getOrganizations(User user) {
        if (!user) {
            return null
        }
        def organizations = []

        if (getAdministeredOrganizations(user)) {
            organizations.addAll(getAdministeredOrganizations(user))
        }

        getOrganizationMemberships(user)?.each {
            if (!organizations?.contains(it)) {
                organizations.add(it)
            }
        }
        return organizations
    }

    String getFirstNameForUi(String name) {
        String firstname

        if (name) {
            if (name.contains(" ")) {
                firstname = name.tokenize(" ")[0]
            } else {
                firstname = name
            }
        }
        if (firstname && firstname.length() > 15) {
            firstname = firstname.substring(0, 15) + "..."
        }
        firstname = HtmlUtils.htmlEscape(firstname)
        return firstname
    }

    List<Account> getAccounts(User user) {
        if (!user) {
            return null
        }
        List<Account> accounts = accountService.getManagedOrBasicAccountsForUser(user)

        /*if (!accounts) {
            accounts = accountService.getBasicAccountsForUser(this)
        }*/
        return accounts
    }

    Account getRequestToJoinAccount(ObjectId id) {
        Account account

        try {
            account = Account.collection.findOne([requestToJoinUserIds: id.toString()]) as Account
        } catch (Exception e) {

        }
        return account
    }

    Account getAccountForJob(User user) {
        if (!user) {
            return null
        }
        Account account = Account.findByMainUserIdsInList([user.id.toString()])

        if (!account) {
            account = Account.findByUserIdsInList([user.id.toString()])
        }
        return account
    }

    Account getAccount(User user) {
        if (user) {
            List<Account> accounts = getAccounts(user)

            if (accounts) {
                return accounts.get(0)
            }
        }
        return null
    }

    Account getAccountForMenu(User user) {
        if (user) {
            Map projection = [companyName: 1, licenseIds: 1, showLogoInSoftware: 1, branding: 1]
            List<Account> accounts = accountService.getManagedAccountsForUser(user, projection)

            if (!accounts) {
                accounts = accountService.getBasicAccountsForUser(user, projection)
            }

            if (accounts) {
                return accounts.get(0)
            }
        }
        return null
    }

    boolean getShowDevDebugNotifications(Boolean enableDevDebugNotifications) {
        return (enableDevDebugNotifications && (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM))
    }

    boolean getShowBenchmarkIndicators(User user) {
        return (user && (!user.hideBenchmarkIndicators && user.internalUseRoles))
    }

    def getSuspiciousActivityScore(UserSuspiciousActivity userSuspiciousActivity) {
        if (userSuspiciousActivity) {
            return userSuspiciousActivity.getTotalScore()
        } else {
            return 0
        }
    }

    List<License> getActiveAutoStartTrials() {
        def currentDate = new Date()
        return License.findAllByTypeAndOpenTrialAndValidFromLessThanEqualsAndValidUntilGreaterThanEquals(Constants.LicenseType.TRIAL.toString(), Boolean.TRUE, currentDate, currentDate)
    }

    Map<String, String> getFavoriteMaterials(User user) {
        if (!user) {
            return null
        }
        Account account = getAccount(user)
        Map<String, String> favoriteMaterials
        if (account) {
            favoriteMaterials = account.favoriteMaterialIdAndUserId
        }
        return favoriteMaterials
    }

    boolean getContactSupportAvailable(User user) {
        if (user) {
            if (user.internalUseRoles) {
                return true
            } else {
                List<License> licenses = getUsableAndLicensedLicenses(user)

                if (licenses) {
                    if (licenses.find({ (Constants.LicenseType.PRODUCTION.toString().equals(it.type) && !it.planetary) || Constants.LicenseType.TRIAL.toString().equals(it.type) })) {
                        return true
                    } else {
                        Boolean allowedByProjectLevel = Boolean.FALSE
                        List<Entity> parents = getExportableParentEntities(user)

                        if (parents) {
                            for (Entity parent in parents) {
                                List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)

                                if (validLicenses?.find({ (Constants.LicenseType.PRODUCTION.toString().equals(it.type) && !it.planetary) || Constants.LicenseType.TRIAL.toString().equals(it.type) })) {
                                    allowedByProjectLevel = Boolean.TRUE
                                    break
                                }
                            }
                        }
                        return allowedByProjectLevel
                    }
                } else {
                    Boolean allowedByProjectLevel = Boolean.FALSE
                    List<Entity> parents = getExportableParentEntities(user)

                    if (parents) {
                        for (Entity parent in parents) {
                            List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)

                            if (validLicenses?.find({ (Constants.LicenseType.PRODUCTION.toString().equals(it.type) && !it.planetary) || Constants.LicenseType.TRIAL.toString().equals(it.type) })) {
                                allowedByProjectLevel = Boolean.TRUE
                                break
                            }
                        }
                    }
                    return allowedByProjectLevel
                }
            }
        }
        return false
    }
}
