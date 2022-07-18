package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.UserBlacklist
import com.bionova.optimi.core.domain.mongo.UserLocation
import com.bionova.optimi.core.domain.mongo.UserSuspiciousActivity
import com.bionova.optimi.core.util.DomainObjectUtil
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.model.CityResponse
import com.maxmind.geoip2.record.City
import com.maxmind.geoip2.record.Country
import com.maxmind.geoip2.record.Location
import com.maxmind.geoip2.record.Postal
import com.maxmind.geoip2.record.Subdivision
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import groovy.time.TimeCategory
import org.apache.commons.lang3.StringUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.geeks.browserdetection.ComparisonType
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.session.SessionInformation
import org.springframework.security.core.session.SessionRegistry
import spock.lang.Specification

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.LoggerUtil

import com.mongodb.client.MongoCollection

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.security.Key

class UserServiceSpec extends Specification implements ServiceUnitTest<UserService>, AutowiredTest {

    EmailConfiguration emailConfiguration
    LoggerUtil loggerUtil
    FlashService flashService
    OptimiUserAgentIdentService optimiUserAgentIdentService
    LicenseService licenseService
    AccountService accountService
    UserService userService
    SessionRegistry sessionRegistry
    SpringSecurityService springSecurityService

    Closure doWithSpring() {{ ->
        emailConfiguration(EmailConfiguration)
    }}

    def setup() {
        emailConfiguration = Mock(EmailConfiguration)
        service.emailConfiguration = emailConfiguration
        loggerUtil = Mock(LoggerUtil)
        service.loggerUtil = loggerUtil
        flashService = Mock(FlashService)
        service.flashService = flashService
        optimiUserAgentIdentService = Mock(OptimiUserAgentIdentService)
        service.optimiUserAgentIdentService = optimiUserAgentIdentService
        licenseService = Mock(LicenseService)
        service.licenseService = licenseService
        accountService = Mock(AccountService)
        service.accountService = accountService
        sessionRegistry = Mock(SessionRegistry)
        service.sessionRegistry = sessionRegistry
        springSecurityService = Mock(SpringSecurityService)
        service.springSecurityService = springSecurityService
    }

    def cleanup() {
    }

    void "check no obfuscation of the user details as users are not found"() {
        given:
        GroovySpy(User, global: true)
        MongoCollection mongoCollection = Mock(MongoCollection)

        when:
        service.obfuscateUsersDetails()

        then:
        1 * User.withCriteria(_ as Closure) >> []
        0 * service.emailConfiguration.isProtectedEmail(_ as String)
        0 * User.collection >> mongoCollection
        0 * mongoCollection.updateMany(*_)
        0 * mongoCollection.updateOne(*_)
    }

    void "check no obfuscation of the user details as found users are protected"() {
        given:
        GroovySpy(User, global: true)
        MongoCollection mongoCollection = Mock(MongoCollection)

        when:
        service.obfuscateUsersDetails()

        then:
        1 * User.withCriteria(_ as Closure) >> ["test1@scand.com", "test2@scand.com"]
        1 * emailConfiguration.isProtectedEmail("test1@scand.com") >> true
        1 * emailConfiguration.isProtectedEmail("test2@scand.com") >> true
        1 * User.collection >> mongoCollection
        1 * mongoCollection.updateMany(["username": [$in: ['test1@scand.com', 'test2@scand.com']]], [$set: [detailsObfuscated: true]])
        0 * mongoCollection.updateOne(*_)
    }

    void "check obfuscation of the user details"() {
        given:
        GroovySpy(User, global: true)
        MongoCollection mongoCollection = Mock(MongoCollection)

        when:
        service.obfuscateUsersDetails()

        then:
        1 * User.withCriteria(_ as Closure) >> ["test1@scand.com", "test2@scand.org"]
        1 * emailConfiguration.isProtectedEmail("test1@scand.com") >> true
        1 * emailConfiguration.isProtectedEmail("test2@scand.org") >> false
        2 * User.collection >> mongoCollection
        1 * mongoCollection.updateMany(["username": [$in: ['test1@scand.com']]], [$set: [detailsObfuscated: true]])
        1 * mongoCollection.updateOne(*_)
    }

    void "check no enabling token authentication for next login"() {
        given:
        GroovySpy(User, global: true)
        User user = Mock()

        when:
        User result = service.enableTokenAuthForNextLogin("testuser@scand.com")

        then:
        1 * user.asBoolean() >> true
        1 * User.findByUsername("testuser@scand.com") >> user
        1 * user.getProperty("emailTokenAuthForNextLoginEnabled") >> true
        0 * _

        and:
        result == user
    }

    void "check enabling token authentication for next login"() {
        given:
        GroovySpy(User, global: true)
        User user = Mock()

        when:
        User result = service.enableTokenAuthForNextLogin("testuser@scand.com")

        then:
        1 * user.asBoolean() >> true
        1 * User.findByUsername("testuser@scand.com") >> user
        1 * user.getProperty("emailTokenAuthForNextLoginEnabled") >> false
        2 * user.setEmailTokenAuthForNextLoginEnabled(true)
        1 * user.merge(flush: true, failOnError: true) >> user
        0 * _

        and:
        result == user
    }

    void "check generation of the email hash"() {
        given:
        User user = Mock()

        when:
        String result = service.generateEmailHash(user)

        then:
        2 * user.getUsername() >> "testuser@scand.com"
        0 * _

        and:
        assert result != null, "Result of the generation should be non null, as username is presented."
    }

    void "check no generation of the email hash due to empty username"() {
        given:
        User user = Mock()

        when:
        String result = service.generateEmailHash(user)

        then:
        1 * user.getUsername() >> ""
        0 * _

        and:
        assert result == null, "Result of the generation should be null, as username is blank."
    }

    void "check decoding of the email hash"() {
        given:
        final String key = "4FE7B1D22DBEBW7G"
        final String algorithm = "AES"

        Key aesKey = new SecretKeySpec(key.getBytes(), algorithm)
        Cipher cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, aesKey)
        byte[] encrypted = cipher.doFinal("testuser@scand.com".getBytes())
        String hash = Base64.getUrlEncoder().encodeToString(encrypted)

        when:
        String result = service.decodeEmailHash(hash)

        then:
        assert result != null, "Result of the decoding should be non null, as the email hash is presented."
    }

    void "check no decoding of the email hash due to empty hash"() {
        given:

        when:
        String result = service.decodeEmailHash("")

        then:
        assert result == null, "Result of the decoding should be null as the email hash is blank."
    }

    void "check getting of user location by the ip"() {
        given:
        String ipAddress = "ipAddress"

        User user = Mock()
        DatabaseReader reader = Mock()
        Country country = Mock()
        Location location = Mock()

        File file = GroovySpy(global: true, useObjenesis: true)
        DatabaseReader.Builder builder = GroovySpy(global: true, useObjenesis: true)
        UserLocation userLocation = GroovySpy(global: true)
        InetAddress inetAddress = GroovySpy(global: true)
        CityResponse response = GroovySpy(global: true)
        Postal postal = GroovySpy(global: true)
        City city = GroovySpy(global: true)
        Subdivision subdivision = GroovySpy(global: true)

        when:
        UserLocation result = service.getLocationForIp(ipAddress, user)

        then:
        1 * new File(_ as String) >> file
        1 * new DatabaseReader.Builder(file) >> builder
        1 * builder.build() >> reader
        1 * InetAddress.getByName(ipAddress) >> inetAddress
        1 * inetAddress.asBoolean() >> true
        1 * reader.city(inetAddress) >> response
        1 * response.asBoolean() >> true
        1 * new UserLocation() >> userLocation
        1 * response.getCountry() >> country
        1 * country.getName() >> "country_name"
        1 * country.getIsoCode() >> "iso_code"
        1 * userLocation.setCountryName("country_name")
        1 * userLocation.setCountryCode("iso_code")
        1 * response.getPostal() >> postal
        1 * postal.getCode() >> "postal_code"
        1 * userLocation.setPostalCode("postal_code")
        1 * response.getCity() >> city
        1 * city.getName() >> "city_name"
        1 * userLocation.setCity("city_name")
        1 * response.getMostSpecificSubdivision() >> subdivision
        1 * subdivision.getName() >> "subdivision_name"
        1 * userLocation.setRegion("subdivision_name")
        1 * response.getLocation() >> location
        1 * location.getLongitude() >> null
        1 * userLocation.setLongitude(null)
        1 * location.getLatitude() >> (null)
        1 * userLocation.setLatitude(null)
        0 * _

        and:
        assert result == userLocation, "Result location should be non null as all parameters are presented."
    }

    void "check no getting of user location by the ip due to null inet address"() {
        given:
        String ipAddress = "ipAddress"

        User user = Mock()
        DatabaseReader reader = Mock()

        File file = GroovySpy(global: true, useObjenesis: true)
        DatabaseReader.Builder builder = GroovySpy(global: true, useObjenesis: true)
        InetAddress inetAddress = GroovySpy(global: true)

        when:
        UserLocation result = service.getLocationForIp(ipAddress, user)

        then:
        1 * new File(_ as String) >> file
        1 * new DatabaseReader.Builder(file) >> builder
        1 * builder.build() >> reader
        1 * InetAddress.getByName(ipAddress) >> inetAddress
        1 * inetAddress.asBoolean() >> false
        0 * _

        and:
        assert result == null, "Result location should be null as the inet address is null."
    }

    void "check no getting of user location by the ip due to the exception"() {
        given:
        String ipAddress = "ipAddress"
        Exception exception = new IOException("Test exception")

        User user = Mock()

        File file = GroovySpy(global: true, useObjenesis: true)
        DatabaseReader.Builder builder = GroovySpy(global: true, useObjenesis: true)

        when:
        UserLocation result = service.getLocationForIp(ipAddress, user)

        then:
        1 * new File(_ as String) >> file
        1 * new DatabaseReader.Builder(file) >> builder
        1 * builder.build() >> { throw exception }
        2 * user.getUsername() >> "testuser@scand.com"
        1 * loggerUtil.warn(_ as ch.qos.logback.classic.Logger, "Error in getting user testuser@scand.com geolocation:", exception)
        1 * flashService.setErrorAlert("Error in getting user testuser@scand.com geolocation: Test exception", true)
        0 * _

        and:
        assert result == null, "Result location should be null as the exception has occurred."
    }

    void "check getting of activated users count"() {
        given:
        MongoCollection mongoCollection = Mock()
        GroovySpy(User, global: true)

        when:
        Long result = service.getActivatedUsersCount()

        then:
        1 * User.collection >> mongoCollection
        1 * mongoCollection.count([emailValidated: true]) >> 10
        0 * _

        and:
        assert result == 10, "Number of active users should be 10."
    }

    void "check getting of non-activated users count"() {
        given:
        MongoCollection mongoCollection = Mock()
        GroovySpy(User, global: true)

        when:
        Long result = service.getNotActivatedUsersCount()

        then:
        1 * User.collection >> mongoCollection
        1 * mongoCollection.count([$or: [[emailValidated: false], [activationDeadline: [$ne:null]]]]) >> 5
        0 * _

        and:
        assert result == 5, "Number of non-active users should be 5."
    }

    void "check getting of suspicious users count"() {
        given:
        MongoCollection mongoCollection = Mock()
        GroovySpy(User, global: true)

        when:
        Long result = service.getUsersWithSuspiciousActivityCount()

        then:
        1 * User.collection >> mongoCollection
        1 * mongoCollection.count([emailValidated: true, userSuspiciousActivity: [$ne: null]]) >> 1
        0 * _

        and:
        assert result == 1, "Number of suspicious users should be 1."
    }

    void "check getting users black list"() {
        given:
        GroovySpy(UserBlacklist, global: true)

        when:
        List<UserBlacklist> result = service.getAllUserBlackLists()

        then:
        1 * UserBlacklist.getAll() >> [new UserBlacklist(), new UserBlacklist()]
        0 * _

        and:
        assert result.size() == 2, "Users black list should contain 2 elements."
    }

    void "check getting of privileged users count"() {
        given:
        MongoCollection mongoCollection = Mock()
        GroovySpy(User, global: true)

        when:
        Long result = service.getUsersWithEnhancedPrivilegesCount()

        then:
        1 * User.collection >> mongoCollection
        1 * mongoCollection.count([authorities: [$elemMatch: [authority: [$in: service.ENHANCED_USER_ROLES]]]]) >> 3
        0 * _

        and:
        assert result == 3, "Number of suspicious users should be 3."
    }

    void "check enhancement of activated users documents"() {
        given:
        UserSuspiciousActivity suspiciousActivity1 = Mock()
        UserSuspiciousActivity suspiciousActivity2 = Mock()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId accountObjectId1 = new ObjectId('000000000000000000000010')
        ObjectId accountObjectId2 = new ObjectId('000000000000000000000011')
        ObjectId accountObjectId3 = new ObjectId('000000000000000000000012')
        Document account1 = new Document([_id: accountObjectId1, companyName: 'company_1', mainUserIds: ['user_id_1'], userIds: ['user_id_2', 'user_id_3']])
        Document account2 = new Document([_id: accountObjectId2, companyName: 'company_2', mainUserIds: [], userIds: ['user_id_1', 'user_id_2']])
        Document account3 = new Document([_id: accountObjectId3, companyName: 'company_3', mainUserIds: ['user_id_2'], userIds: ['user_id_1', 'user_id_4']])
        List<Document> activatedUsers = [
                new Document([
                        _id: userObjectId1,
                        username: 'testuser1@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_SYSTEM_ADMIN],
                                [authority: Constants.ROLE_SUPER_USER],
                                [authority: Constants.ROLE_SALES_VIEW]
                        ],
                        licenseIds: ['licence_id_1', 'licence_id_2', 'licence_id_3'],
                        usableLicenseIds: ['licence_id_4'],
                        userSuspiciousActivity: suspiciousActivity1
                ]),
                new Document([
                        _id: userObjectId2,
                        username: 'testuser2@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_DEVELOPER],
                                [authority: Constants.ROLE_SALES_VIEW]
                        ],
                        licenseIds: ['licence_id_1', 'licence_id_2'],
                        usableLicenseIds: ['licence_id_5'],
                        userSuspiciousActivity: suspiciousActivity2
                ]),
                new Document([
                        _id: userObjectId3,
                        username: 'testuser3@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_SUPER_USER]
                        ],
                        licenseIds: ['licence_id_3', 'licence_id_4'],
                        usableLicenseIds: ['licence_id_5', 'licence_id_6']
                ])
        ]
        Map<String, List<Document>> userAccountMapping = [
                (hexUserId1): [account1, account2, account3],
                (hexUserId2): [account1, account2, account3],
                (hexUserId3): [account1],
                (hexUserId4): [account3]
        ]

        when:
        List<Document> result = service.enhanceActivatedUsersDocuments(activatedUsers)

        then:
        1 * accountService.getAllUserAccountMapping() >> userAccountMapping
        1 * licenseService.getAllValidLicensesCount() >> 2
        1 * suspiciousActivity1.getTotalScore() >> 5
        1 * suspiciousActivity2.getTotalScore() >> 1
        0 * _

        and:
        assert result == [
                new Document([
                        _id: hexUserId1,
                        accounts: [
                                [_id: accountObjectId1, companyName: 'company_1'],
                                [_id: accountObjectId2, companyName: 'company_2'],
                                [_id: accountObjectId3, companyName: 'company_3']
                        ],
                        licenses: 2,
                        username: 'testuser1@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_SYSTEM_ADMIN],
                                [authority: Constants.ROLE_SUPER_USER],
                                [authority: Constants.ROLE_SALES_VIEW]
                        ],
                        licenseIds: ['licence_id_1', 'licence_id_2', 'licence_id_3'],
                        usableLicenseIds: ['licence_id_4'],
                        userSuspiciousActivity: suspiciousActivity1,
                        suspiciousActivityScore: 5
                ]),
                new Document([
                        _id: hexUserId2,
                        accounts: [
                                [_id: accountObjectId1, companyName: 'company_1'],
                                [_id: accountObjectId2, companyName: 'company_2'],
                                [_id: accountObjectId3, companyName: 'company_3']
                        ],
                        licenses: 3,
                        username: 'testuser2@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_DEVELOPER],
                                [authority: Constants.ROLE_SALES_VIEW]
                        ],
                        licenseIds: ['licence_id_1', 'licence_id_2'],
                        usableLicenseIds: ['licence_id_5'],
                        userSuspiciousActivity: suspiciousActivity2,
                        suspiciousActivityScore: 1
                ]),
                new Document([
                        _id: hexUserId3,
                        accounts: [
                                [_id: accountObjectId1, companyName: 'company_1']
                        ],
                        licenses: 2,
                        username: 'testuser3@bionova.fi',
                        authorities: [
                                [authority: Constants.ROLE_SUPER_USER]
                        ],
                        licenseIds: ['licence_id_3', 'licence_id_4'],
                        usableLicenseIds: ['licence_id_5', 'licence_id_6'],
                        suspiciousActivityScore: 0
                ])
        ]
    }

    void "check getting of active users"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 5.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 30.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser3, "session_id_4", use(TimeCategory) { currentDate - 12.hours })

        User user1 = Mock()
        User user2 = Mock()

        GroovySpy(User, global: true)
        GroovySpy(DomainObjectUtil, global: true)

        when:
        List<User> result = service.getActiveUsers()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3, sessionInformation4]

        and:
        // this block describes the logic of the target 'UserService#getActiveUsers()' method
        1 * DomainObjectUtil.stringsToObjectIds([hexUserId1, hexUserId2]) >> [userObjectId1, userObjectId2]
        1 * User.findAllByIdInList([userObjectId1, userObjectId2]) >> [user1, user2]
        1 * user1.getLastLogin() >> use(TimeCategory) { currentDate - 10.minutes }
        1 * user2.getLastLogin() >> use(TimeCategory) { currentDate - 40.minutes }
        0 * _

        and:
        assert result == [user1, user2], "Result list of active users should contain 2 elements."
    }

    void "check no getting of active users"() {
        given:

        when:
        List<User> result = service.getActiveUsers()

        then:
        1 * sessionRegistry.getAllPrincipals() >> []
        0 * _

        and:
        assert result == null, "Result list of active users should be null as session registry doesn't contain any principals."
    }

    void "check getting of active free users"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 10.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 45.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", use(TimeCategory) { currentDate - 7.hours })

        User user1 = Mock()
        User user2 = Mock()
        User user4 = Mock()

        GroovySpy(User, global: true)
        GroovySpy(DomainObjectUtil, global: true)

        when:
        List<User> result = service.getActiveFreeUsers()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        1 * sessionRegistry.getAllSessions(grailsUser4, false) >> [sessionInformation4]

        and:
        // this block describes the logic of the target 'UserService#getActiveFreeUsers()' method
        1 * DomainObjectUtil.stringsToObjectIds([hexUserId1, hexUserId2, hexUserId4]) >> [userObjectId1, userObjectId2, userObjectId4]
        1 * User.findAllByIdInList([userObjectId1, userObjectId2, userObjectId4]) >> [user1, user2, user4]
        1 * user1.getIsCommercialUser() >> false
        1 * user2.getIsCommercialUser() >> true
        1 * user4.getIsCommercialUser() >> false
        0 * _

        and:
        assert result == [user1, user4], "Result list of active free users should contain 2 elements."
    }

    void "check no getting of active free users"() {
        given:

        when:
        List<User> result = service.getActiveFreeUsers()

        then:
        1 * sessionRegistry.getAllPrincipals() >> []
        0 * _

        and:
        assert result == [], "Result list of active free users should be empty as session registry doesn't contain any principals."
    }

    void "check getting of active user ids"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        String hexUserId5 = '000000000000000000000005'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        ObjectId userObjectId5 = new ObjectId(hexUserId5)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        GrailsUser grailsUser5 = new GrailsUser("test_user_5", "password_5", true, true, true, true, [], userObjectId5)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 5.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 15.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", use(TimeCategory) { currentDate - 7.hours })
        SessionInformation sessionInformation5 = new SessionInformation(grailsUser5, "session_id_5", use(TimeCategory) { currentDate - 8.hours })
        sessionInformation2.expireNow() // set sessionInformation2 as expired

        when:
        List<String> result = service.getActiveUserIds()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4, grailsUser5]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        1 * sessionRegistry.getAllSessions(grailsUser4, false) >> [sessionInformation4]
        1 * sessionRegistry.getAllSessions(grailsUser5, false) >> [sessionInformation5]

        and:
        // this block describes the logic of the target 'UserService#getActiveUserIds()' method
        0 * _

        and:
        assert result == ['000000000000000000000001', '000000000000000000000004'], "Result list of active user ids should contain 2 elements."
    }

    void "check no getting of active user ids"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        String hexUserId5 = '000000000000000000000005'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        ObjectId userObjectId5 = new ObjectId(hexUserId5)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        GrailsUser grailsUser5 = new GrailsUser("test_user_5", "password_5", true, true, true, true, [], userObjectId5)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 5.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 15.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", use(TimeCategory) { currentDate - 9.hours })
        SessionInformation sessionInformation5 = new SessionInformation(grailsUser5, "session_id_5", use(TimeCategory) { currentDate - 8.hours })
        sessionInformation1.expireNow() // set sessionInformation1 as expired
        sessionInformation2.expireNow() // set sessionInformation2 as expired

        when:
        List<String> result = service.getActiveUserIds()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4, grailsUser5]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        1 * sessionRegistry.getAllSessions(grailsUser4, false) >> [sessionInformation4]
        1 * sessionRegistry.getAllSessions(grailsUser5, false) >> [sessionInformation5]

        and:
        // this block describes the logic of the target 'UserService#getActiveUserIds()' method
        0 * _

        and:
        assert result == [], "Result list of active free user ids should be empty as session registry doesn't contain any users with valid sessions."
    }

    void "check getting of last request by user id"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        Date lastRequest1 = use(TimeCategory) { currentDate - 5.minutes }
        Date lastRequest2 = use(TimeCategory) { currentDate - 15.minutes }
        Date lastRequest4 = use(TimeCategory) { currentDate - 4.hours }
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", lastRequest1)
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", lastRequest2)
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", lastRequest4)
        SessionInformation sessionInformation5 = new SessionInformation(grailsUser4, "session_id_5", use(TimeCategory) { currentDate - 6.hours })

        GroovySpy(DomainObjectUtil, global: true)

        when:
        Map<ObjectId, Date> result = service.getLastRequestByUserId()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        // and the logic of the target 'UserService#getLastRequestByUserId()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4]
        2 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        2 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        2 * sessionRegistry.getAllSessions(grailsUser4, false) >>> [[sessionInformation4, sessionInformation5], [sessionInformation4]]
        0 * _

        and:
        assert result == [(userObjectId1): lastRequest1, (userObjectId2): lastRequest2, (userObjectId4): lastRequest4], "Result map of last requests by user ids should contain 2 entries."
    }

    void "check no getting of last request by user id"() {
        given:

        when:
        Map<ObjectId, Date> result = service.getLastRequestByUserId()

        then:
        1 * sessionRegistry.getAllPrincipals() >> []
        0 * _

        and:
        assert result == [:], "Result map of last requests by user ids should be empty as session registry doesn't contain any principals."
    }

    void "check getting of active user names"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        String hexUserId5 = '000000000000000000000005'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        ObjectId userObjectId5 = new ObjectId(hexUserId5)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        GrailsUser grailsUser5 = new GrailsUser("test_user_5", "password_5", true, true, true, true, [], userObjectId5)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 5.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 15.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", use(TimeCategory) { currentDate - 7.hours })
        SessionInformation sessionInformation5 = new SessionInformation(grailsUser5, "session_id_5", use(TimeCategory) { currentDate - 6.hours })
        sessionInformation1.expireNow() // set sessionInformation1 as expired
        sessionInformation2.expireNow() // set sessionInformation2 as expired

        when:
        List<String> result = service.getActiveUserNames()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4, grailsUser5]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        1 * sessionRegistry.getAllSessions(grailsUser4, false) >> [sessionInformation4]
        1 * sessionRegistry.getAllSessions(grailsUser5, false) >> [sessionInformation5]

        and:
        // this block describes the logic of the target 'UserService#getActiveUserNames()' method
        0 * _

        and:
        assert result == ['test_user_4', 'test_user_5'], "Result list of active user names should contain 2 elements."
    }

    void "check no getting of active user names"() {
        given:
        Date currentDate = new Date()
        String hexUserId1 = '000000000000000000000001'
        String hexUserId2 = '000000000000000000000002'
        String hexUserId3 = '000000000000000000000003'
        String hexUserId4 = '000000000000000000000004'
        String hexUserId5 = '000000000000000000000005'
        ObjectId userObjectId1 = new ObjectId(hexUserId1)
        ObjectId userObjectId2 = new ObjectId(hexUserId2)
        ObjectId userObjectId3 = new ObjectId(hexUserId3)
        ObjectId userObjectId4 = new ObjectId(hexUserId4)
        ObjectId userObjectId5 = new ObjectId(hexUserId5)
        GrailsUser grailsUser1 = new GrailsUser("test_user_1", "password_1", true, true, true, true, [], userObjectId1)
        GrailsUser grailsUser2 = new GrailsUser("test_user_2", "password_2", true, true, true, true, [], userObjectId2)
        GrailsUser grailsUser3 = new GrailsUser("test_user_3", "password_3", true, true, true, true, [], userObjectId3)
        GrailsUser grailsUser4 = new GrailsUser("test_user_4", "password_4", true, true, true, true, [], userObjectId4)
        GrailsUser grailsUser5 = new GrailsUser("test_user_5", "password_5", true, true, true, true, [], userObjectId5)
        SessionInformation sessionInformation1 = new SessionInformation(grailsUser1, "session_id_1", use(TimeCategory) { currentDate - 5.minutes })
        SessionInformation sessionInformation2 = new SessionInformation(grailsUser2, "session_id_2", use(TimeCategory) { currentDate - 15.minutes })
        SessionInformation sessionInformation3 = new SessionInformation(grailsUser3, "session_id_3", use(TimeCategory) { currentDate - 1.day })
        SessionInformation sessionInformation4 = new SessionInformation(grailsUser4, "session_id_4", use(TimeCategory) { currentDate - 9.hours })
        SessionInformation sessionInformation5 = new SessionInformation(grailsUser5, "session_id_5", use(TimeCategory) { currentDate - 8.hours })
        sessionInformation1.expireNow() // set sessionInformation1 as expired
        sessionInformation2.expireNow() // set sessionInformation2 as expired

        when:
        List<String> result = service.getActiveUserNames()

        then:
        // this block describes the logic of the private 'UserService#getGrailsUsersWithValidSessions()' method
        1 * sessionRegistry.getAllPrincipals() >> [grailsUser1, grailsUser2, grailsUser3, grailsUser4, grailsUser5]
        1 * sessionRegistry.getAllSessions(grailsUser1, false) >> [sessionInformation1]
        1 * sessionRegistry.getAllSessions(grailsUser2, false) >> [sessionInformation2]
        1 * sessionRegistry.getAllSessions(grailsUser3, false) >> [sessionInformation3]
        1 * sessionRegistry.getAllSessions(grailsUser4, false) >> [sessionInformation4]
        1 * sessionRegistry.getAllSessions(grailsUser5, false) >> [sessionInformation5]

        and:
        // this block describes the logic of the target 'UserService#getActiveUserNames()' method
        0 * _

        and:
        assert result == [], "Result list of active free user names should be empty as session registry doesn't contain any users with valid sessions."
    }

    void "check user logout"() {
        given:
        String rememberMeCookieName = "test_cookie"
        String contextPath = "test_context_path"

        HttpServletRequest request = Mock()
        HttpServletResponse response = Mock()
        HttpSession session = Mock()
        Cookie cookie = Mock()
        SecurityContext securityContext = Mock()

        GroovySpy(Cookie, global: true, useObjenesis: true)
        GroovySpy(StringUtils, global: true)
        GroovySpy(SecurityContextHolder, global: true)

        when:
        service.logoutUser(request, response, rememberMeCookieName)

        then:
        1 * new Cookie(rememberMeCookieName, null) >> cookie
        1 * cookie.setMaxAge(0)
        2 * request.getContextPath() >> contextPath
        1 * StringUtils.isNotEmpty(contextPath) >> true
        1 * cookie.setPath(contextPath)
        1 * response.addCookie(cookie)
        1 * SecurityContextHolder.getContext() >> securityContext
        1 * securityContext.setAuthentication(null)
        1 * request.getSession(false) >> session
        1 * session.invalidate()
        0 * _
    }

    void "check that user uses IE11 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isAtLeastIE11(request)

        then:
        1 * request.getHeader("User-Agent") >> "Test_Trident"
        0 * _

        and:
        assert result, "Result should be 'true' as 'User-Agent' header contains 'trident' word."
    }

    void "check that user doesn't use IE11 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isAtLeastIE11(request)

        then:
        1 * request.getHeader("User-Agent") >> "Test_Msie"
        0 * _

        and:
        assert !result, "Result should be 'false' as 'User-Agent' header contains 'msie' word."
    }

    void "check that user uses Edge browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isEdge(request)

        then:
        1 * request.getHeader("User-Agent") >> "Test_Edge"
        0 * _

        and:
        assert result, "Result should be 'true' as 'User-Agent' header contains 'edge' word."
    }

    void "check that user doesn't use Edge browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isEdge(request)

        then:
        1 * request.getHeader("User-Agent") >> "Test_User_Agent"
        0 * _

        and:
        assert !result, "Result should be 'false' as 'User-Agent' header doesn't contain 'edge' word."
    }

    void "check logging of user's browser and OS"() {
        when:
        service.logUserBrowserAndOs()

        then:
        1 * optimiUserAgentIdentService.getBrowser() >> "Edge"
        1 * optimiUserAgentIdentService.getBrowserVersion() >> "11.0"
        1 * optimiUserAgentIdentService.getOperatingSystem() >> "Windows"
        1 * loggerUtil.info(_ as ch.qos.logback.classic.Logger, "Browser and OS: Edge 11.0, Windows")
        0 * _
    }

    void "check no logging of user's browser and OS due to the exception"() {
        given:
        Exception exception = new IOException("Test exception")

        when:
        service.logUserBrowserAndOs()

        then:
        1 * optimiUserAgentIdentService.getBrowser() >> { throw exception }
        1 * loggerUtil.warn(_ as ch.qos.logback.classic.Logger, "Error in logging user browser and os: ${exception}")
        0 * _
    }

    void "check getting of browser"() {
        when:
        String result = service.getBrowser()

        then:
        1 * optimiUserAgentIdentService.getBrowser() >> "Opera"
        0 * _

        and:
        assert result == "Opera", "Result should be 'Opera'."
    }

    void "check validity of Msie v11.0 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isValidBrowser(request)

        then:
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.EQUAL, "10") >> false
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.GREATER, "10") >> true
        0 * _

        and:
        assert result == true, "Result should be 'true', as Msie browser version is greater than 10."
    }

    void "check validity of Firefox v18.0 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isValidBrowser(request)

        then:
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.EQUAL, "10") >> false
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.GREATER, "10") >> false
        1 * optimiUserAgentIdentService.isFirefox(ComparisonType.EQUAL, "18") >> true
        0 * _

        and:
        assert result == true, "Result should be 'true', as Firefox browser version is 18."
    }

    void "check validity of Chrome v24.0 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isValidBrowser(request)

        then:
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.EQUAL, "10") >> false
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.GREATER, "10") >> false
        1 * optimiUserAgentIdentService.isFirefox(ComparisonType.EQUAL, "18") >> false
        1 * optimiUserAgentIdentService.isFirefox(ComparisonType.GREATER, "18") >> false
        1 * optimiUserAgentIdentService.isChrome(ComparisonType.EQUAL, "23") >> false
        1 * optimiUserAgentIdentService.isChrome(ComparisonType.GREATER, "23") >> true
        0 * _

        and:
        assert result == true, "Result should be 'true', as Chrome browser version is greater than 23."
    }

    void "check validity of Safari v6.0 browser"() {
        given:
        HttpServletRequest request = Mock()

        when:
        boolean result = service.isValidBrowser(request)

        then:
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.EQUAL, "10") >> false
        1 * optimiUserAgentIdentService.isMsie(ComparisonType.GREATER, "10") >> false
        1 * optimiUserAgentIdentService.isFirefox(ComparisonType.EQUAL, "18") >> false
        1 * optimiUserAgentIdentService.isFirefox(ComparisonType.GREATER, "18") >> false
        1 * optimiUserAgentIdentService.isChrome(ComparisonType.EQUAL, "23") >> false
        1 * optimiUserAgentIdentService.isChrome(ComparisonType.GREATER, "23") >> false
        1 * optimiUserAgentIdentService.isSafari(ComparisonType.EQUAL, "6.0") >> true
        0 * _

        and:
        assert result == true, "Result should be 'true', as Safari browser version is 6."
    }

    void "check retrieving of the current user if the user is logged in"() {
        given:
        User user = Mock()

        when:
        User result = service.getCurrentUser()

        then:
        1 * springSecurityService.isLoggedIn() >> true
        1 * springSecurityService.getCurrentUser() >> user
        1 * user.asType(User.class) >> user
        0 * _

        and:
        assert result == user, "Current user should be found, as the user is logged in."
    }

    void "check no retrieving of the current user if the user is logged out"() {
        when:
        User result = service.getCurrentUser()

        then:
        1 * springSecurityService.isLoggedIn() >> false
        0 * _

        and:
        assert result == null, "Current user shouldn't be found, as the user is logged out."
    }

    void "check if the user is logged in"() {
        when:
        boolean result = service.isLoggedIn()

        then:
        1 * springSecurityService.isLoggedIn() >> true
        0 * _

        and:
        assert result, "Result should be 'true', as the user is logged in according to the 'springSecurityService'."
    }

    void "check if the user isn't logged in"() {
        when:
        boolean result = service.isLoggedIn()

        then:
        1 * springSecurityService.isLoggedIn() >> false
        0 * _

        and:
        assert !result, "Result should be 'false', as the user isn't logged in according to the 'springSecurityService'."
    }
}
