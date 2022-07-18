/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.Role
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.core.util.SpringUtil
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import grails.util.Holders
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.grails.web.util.WebUtils
import org.jboss.aerogear.security.otp.api.Base32
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.text.SimpleDateFormat
import java.time.LocalDateTime

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class MongoUserDetailsService implements GrailsUserDetailsService {

    public static final String ROLE_STEP_ONE_AUTHENTICATED = "ROLE_PRE_AUTH"
    public static final Set<GrantedAuthority> PRE_AUTH_ROLES = [new SimpleGrantedAuthority(ROLE_STEP_ONE_AUTHENTICATED)].toSet()
    LoggerUtil loggerUtil
    FlashService flashService
    SpringSecurityService springSecurityService
    UserService userService

    @Autowired
    EmailConfiguration emailConfiguration

    /**
     * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
     * we give a user with no granted roles this one which gets past that restriction but
     * doesn't grant anything.
     */
    static final Set NO_ROLES = [
            new SimpleGrantedAuthority(SpringSecurityUtils.NO_ROLE)
    ].toSet()

    private static Boolean isApiLogin() {
        HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()

        // If request has bearerToken or securityToken, no twostep required
        return !request.getContentType()?.toLowerCase()?.contains("form") ||
                request.getParameter("bearerToken") || request.getParameter("securityToken")

    }

    @Override
    UserDetails loadUserByUsername(String username, boolean loadRoles) {
        username = username?.toLowerCase()?.trim()
        log.debug("Attempted user logon: $username")
        Document user = User.collection.findOne([username: username])

        if (!user) {
            throw new UsernameNotFoundException("User not found: ${username}")
        }

        if (log.debugEnabled) {
            log.debug("User found: $username")
        }
        def roles = NO_ROLES
        Boolean twostepAuth = Boolean.FALSE

        if (loadRoles) {
            def authorities = user.get("authorities") as Set<Role>

            if (authorities) {
                Map updatedProperties = [:]
                roles = authorities

                try {
                    String twostepAuthDaysString = getConfigurationService().getConfigurationValue(null, Constants.ConfigName.TWOSTEPAUTH_DAYS.toString())
                    Integer twostepAuthDays = twostepAuthDaysString?.isNumber() ? twostepAuthDaysString.toInteger() : 90
                    Date registrationTime = user.get("registrationTime") ? user.getDate("registrationTime") : null
                    Date currentDate = new Date()
                    // do not ask twostep auth before this + authdays days
                    Date hardCodedDate
                    String parseableDate = getConfigurationService().getConfigurationValue(null, Constants.ConfigName.TWOSTEPAUTH_DAYS_START.toString())

                    try {
                        hardCodedDate = new SimpleDateFormat("dd.MM.yyyy").parse(parseableDate)
                    } catch(Exception e) {
                        loggerUtil.warn(log,"TWOSTEPAUTH_DAYS_START error", e)
                        flashService.setWarningAlert("TWOSTEPAUTH_DAYS_START error: ${e.getMessage()}", true)
                        hardCodedDate = new SimpleDateFormat("dd.MM.yyyy").parse("01.09.2019")
                    }
                    List<String> applicableDomains = getConfigurationService().getConfigurationValue(null, Constants.ConfigName.TWOSTEPAUTH_APPLICABLE_DOMAINS.toString())?.tokenize(",")
                    String usernameDomain = username.substring(username.lastIndexOf('.') + 1)
                    Boolean emailAuthEnabled = user.getBoolean("emailTokenAuthEnabled", false) // Forced
                    boolean emailAuthForNextLoginEnabled = user.getBoolean("emailTokenAuthForNextLoginEnabled", false)

                    // Do not check the twostepAuth unit there has been passed more than twostepAuthDays from registration
                    if (emailAuthEnabled || emailAuthForNextLoginEnabled || !registrationTime || DateUtils.addDays(registrationTime, twostepAuthDays).before(currentDate)) {
                        boolean disableTwoFactorAuthByChannel = false

                        if (user.get("channelToken") != null) {
                            disableTwoFactorAuthByChannel = ChannelFeature.collection.findOne([token: user.getString("channelToken")], [disableTwoFactorAuth: 1])?.getBoolean("disableTwoFactorAuth", false)
                        }
                        Date latestAuthDate = null
                        Date currentMinus90Days = DateUtils.addDays(currentDate, -twostepAuthDays)
                        List<Date> authDates = (List<Date>) user.emailTokenAuths

                        if (authDates) {
                            latestAuthDate = Collections.max(authDates)
                        }

                        Boolean periodicalEmailAuthDisabled = user.getBoolean("disablePeriodicalEmailTokenAuth", false) || authorities.collect({
                            it.authority
                        })?.contains("ROLE_SYSTEM_ADMIN")

                        if (!isApiLogin() && !disableTwoFactorAuthByChannel && (emailAuthEnabled || emailAuthForNextLoginEnabled || (!periodicalEmailAuthDisabled && (!latestAuthDate || latestAuthDate.before(currentMinus90Days)) && DateUtils.addDays(hardCodedDate, twostepAuthDays).before(currentDate) && (!applicableDomains||applicableDomains.contains(usernameDomain))))) {
                            createEmailToken(user, updatedProperties)
                            twostepAuth = Boolean.TRUE
                        }

                        if (!user.get("language") && user.get("country")) {
                            OptimiResourceService optimiResourceService = SpringUtil.getBean("optimiResourceService")
                            List<String> countryAreas = optimiResourceService.getResourceByResourceAndProfileId(user.getString("country"), null)?.areas

                            if (countryAreas) {
                                Resource languageResource = optimiResourceService.getSystemLocaleByAreas(countryAreas)

                                if (languageResource) {
                                    updatedProperties.put("language", languageResource.resourceId)
                                }
                            }
                        }

                        if (!updatedProperties.isEmpty()) {
                            User.collection.updateOne(["_id": user.get("_id")], [$set: updatedProperties])
                        }
                    }
                } catch (Exception e) {
                    loggerUtil.error(log, "Error in setting user lastLogin", e)
                    flashService.setErrorAlert("Error in setting user lastLogin: ${e.getMessage()}", true)
                }
            }
        }

        if (isApiLogin() || !twostepAuth) {
            return createUserDetails(user, roles)
        } else {
            return createUserDetails(user, PRE_AUTH_ROLES)
        }
    }

    private ConfigurationService getConfigurationService() {
        ConfigurationService configurationService = Holders.getApplicationContext().getBean("configurationService")
        return configurationService
    }

    void createEmailToken(Document user, Map updatedProperties) {
        String otpToken = Base32.random()
        updatedProperties.put("emailToken", otpToken)
        def emailTokenValidityInHours = getConfigurationService().getConfigurationValue(null, "emailTokenValidityInHours")
        Date emailTokenValidUntil = DateUtils.addHours(new Date(), emailTokenValidityInHours?.isNumber() ? emailTokenValidityInHours.toDouble().intValue() : 24)
        updatedProperties.put("emailTokenValidUntil", emailTokenValidUntil)
        log.info("EmailToken for user ${user?.username}: ${otpToken}")
        HttpSession session = WebUtils.retrieveGrailsWebRequest()?.session
        UserService userService = SpringUtil.getBean("userService")
        User userObject = userService.getUserByUsername(user?.username) as User
            if(userObject){
                ActiveCampaignService activeCampaignService = SpringUtil.getBean("activeCampaignService")
                String activeCampaignAuthentId
                String userActiveCampaignId = activeCampaignService.getActiveCampaignContactIdForUser(userObject)
                activeCampaignService.updateFieldsForUser(null, userActiveCampaignId, ['124': otpToken])

                activeCampaignAuthentId = activeCampaignService.updateUniqueTagForUser(userActiveCampaignId,ActiveCampaignService.SEND_AUTHENTICATION_TOKEN_TAG_ID, userObject.activeCampaignAuthentId)
                if(activeCampaignAuthentId){
                    if(userObject){
                        userObject.activeCampaignAuthentId = activeCampaignAuthentId
                        userService.updateUser(userObject)
                    }
                }else {
                    loggerUtil.error(log,"ActiveCampaign: An error occured, user not found on AC. Fallback to server email")
                    flashService.setErrorAlert("ActiveCampaign: An error occured, user not found on AC. Fallback to server email", true)
                    sendEmailToken(user?.get("username")?.toString(), otpToken, emailTokenValidUntil)
                }
            }else {
                loggerUtil.error(log,"ActiveCampaign: An error occured, user not found on AC. Fallback to server email")
                flashService.setErrorAlert("ActiveCampaign: An error occured, user not found on AC. Fallback to server email", true)
                sendEmailToken(user?.get("username")?.toString(), otpToken, emailTokenValidUntil)
            }


    }

    void sendEmailToken(String email, String otpToken, Date emailTokenValidUntil) {
        OptimiMailService optimiMailService = SpringUtil.getBean("optimiMailService")
        LocalDateTime now = LocalDateTime.now()
        HttpServletRequest request = WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        optimiMailService.sendMail({
            to email
            from "${emailConfiguration.noReplyEmail}"
            replyTo "${emailConfiguration.noReplyEmail}"
            subject("Your one-time authentication token for One Click LCA")
            html "<p>You are receiving this message as your user account is logging in to <a href=\"https://${request?.serverName ?: com.bionova.optimi.core.Constants.PROD_DOMAIN_NAME}/app/\">One Click LCA</a> " +
                    "on ${now.hour}:${now.minute} on ${now.dayOfMonth} ${now.monthValue} ${now.year}.</p>" +
                    "<p>Here's your one-time authentication token for One Click LCA : ${otpToken}</p>" +
                    "<p>The token is valid until ${new SimpleDateFormat("dd.MM.yyyy HH:mm").format(emailTokenValidUntil)}. " +
                    "If you did not attempt to log in, you do not need to do anything, and your data is safe.</p>" +
                    "<p>If you have any questions feel free to write to One Click LCA support at <a href=\"mailto:${emailConfiguration.supportEmail}\">${emailConfiguration.supportEmail}</a>.</p>"
        }, "${emailConfiguration.noReplyEmail}", Boolean.TRUE)
    }

    @Override
    UserDetails loadUserByUsername(String username) {
        return loadUserByUsername(username, true)
    }

    UserDetails createUserDetails(Document user, Set<Role> authorities) {
        if (user && authorities) {
            List<SimpleGrantedAuthority> grantedAuthorities = authorities.collect {
                new SimpleGrantedAuthority(it.authority)
            }
            new GrailsUser(user.get("username"), user.get("password"), user.get("enabled"),
                    !user.getBoolean("accountExpired", false), !user.getBoolean("passwordExpired", false),
                    !user.getBoolean("accountLocked", false), grantedAuthorities, user.get("_id"))
        } else {
            return null
        }
    }
}
