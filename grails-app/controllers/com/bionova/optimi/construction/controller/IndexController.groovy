/*
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.Constants.ConfigName
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.Role
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserLicenseInvitation
import com.bionova.optimi.core.service.ActiveCampaignService
import com.bionova.optimi.core.service.MongoUserDetailsService
import com.bionova.optimi.exception.MaximumFreeUsersReachedException
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.plugin.springsecurity.LoginController
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.Environment
import org.apache.commons.lang.StringEscapeUtils
import org.bson.Document
import org.codehaus.groovy.runtime.StackTraceUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.web.util.HtmlUtils

import javax.servlet.http.HttpSession
import java.text.Normalizer

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class IndexController extends LoginController {

    static allowedMethods = [createAccount: "POST"]

    def userService
    def springSecurityService
    def optimiResourceService
    def configurationService
    def localeResolverUtil
    def channelFeatureService
    def accountService
    def applicationService
    def optimiMailService
    def mongoUserDetailsService
    def activeCampaignService

    @Autowired
    EmailConfiguration emailConfiguration

    private static final String EMAIL_TOKEN_RESPONSE_KEY = "email_token_response"


    def index() {
        def config = SpringSecurityUtils.securityConfig
        configurationService.updateServerNameConfig(request)

        List<ChannelFeature> channelFeatures = ChannelFeature.list()
        channelFeatureService.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        if (springSecurityService.isLoggedIn() && SpringSecurityUtils.ifAnyGranted(com.bionova.optimi.core.Constants.ROLE_AUTHENTICATED)) {
            redirect controller: "main", action: "list"
        } else {
            if (SpringSecurityUtils.ifAnyGranted(MongoUserDetailsService.ROLE_STEP_ONE_AUTHENTICATED)) {
                redirect action: "steptwoauth"
            } else {
                String channelToken = params.channelToken
                String accountKey = params.accountKey
                String applicationId = params.applicationId
                String populateUserName = params.populateUserName
                String originatingSystem = params.originatingSystem ?: "none"
                Account account
                String language = params.lang ? params.lang : "en"
                String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
                Boolean customStylingEnabled = Boolean.FALSE
                List<ChannelFeature> channelFeatureList = channelFeatureService?.getAllChannelFeatures()

                if (accountKey) {
                    account = accountService?.getAccountByLoginKey(accountKey)
                    session?.setAttribute("accountKey", accountKey)
                }
                List<ChannelFeature> channelFeaturesForAnotherApplicationLogin = channelFeatureList?.findAll({ ChannelFeature channelFeature -> channelFeature?.showInLoginPage })
                ChannelFeature channel = channelFeatureList.find({ ChannelFeature channelFeature -> channelFeature.token.equals(channelToken) })

                if (!accountKey && (channel?.backgroundImage || channel?.loginPageCss)) {
                    customStylingEnabled = Boolean.TRUE
                }
                Map<String, String> helpPageBullets
                String helpPageText

                if (applicationId && originatingSystem) {
                    Application application = applicationService.getApplicationByApplicationId(applicationId)
                    helpPageBullets = application?.helpPageBullets?.get(originatingSystem) ? application?.helpPageBullets?.get(originatingSystem) : application?.helpPageBullets?.get("none")
                    helpPageText = application?.helpPageText
                }
                if (Environment.current == Environment.DEVELOPMENT) {
                    flash.permErrorAlert = message(code:'environment_development_warning')
                }
                [postUrl: postUrl, login: true, rememberMeParameter: config.rememberMe.parameter, language: language, channelToken: channelToken, channelFeatureList: channelFeatureList,
                 channel: channel, customStylingEnabled: customStylingEnabled, account: account, channelFeaturesForAnotherApplicationLogin: channelFeaturesForAnotherApplicationLogin, applicationId: applicationId, helpPageBullets: helpPageBullets, helpPageText: helpPageText, populateUserName: populateUserName]
            }
        }
    }

    def nonValidatedAccountHandler() {
        User user = userService.getCurrentUser(true)
        user?.enabled = Boolean.FALSE
        if (user && user.validate()) {
            user.merge(flush: true, failOnError: true)
        }
        session?.setAttribute("username", "${user?.username}")
        [username: user?.username, deadline: user?.activationDeadline]
    }

    def steptwoauth() {
        List<String> calledMethodNames = StackTraceUtils.sanitize(new Throwable()).stackTrace?.collect({
            it.methodName
        })?.unique()

        if (calledMethodNames) {
            calledMethodNames.removeAll(com.bionova.optimi.core.Constants.REMOVE_FROM_STACKTRACE)
        }
        log.info("STEPTWO AUTH, called from: ${calledMethodNames}")
        Boolean customStylingEnabled = Boolean.FALSE
        ChannelFeature channel = channelFeatureService.getChannelFeature(session)

        if (channel?.backgroundImage || channel?.loginPageCss) {
            customStylingEnabled = Boolean.TRUE
        }
        [channel: channel, customStylingEnabled: customStylingEnabled]
    }

    def authenticateByEmailToken() {
        if (!request.post) {
            log.error("Authentication method not supported: $request.method")
            redirect action: "index"
        } else {
            String username = springSecurityService.getCurrentUser()?.username

            if (username) {
                if (params.resend) {
                    Document user = userService.getUserAsDocumentByUsername(username)
                    Map updatedProperties = [:]
                    mongoUserDetailsService.createEmailToken(user, updatedProperties)

                    if (!updatedProperties.isEmpty()) {
                        User.collection.updateOne(["_id": user.get("_id")], [$set: updatedProperties])
                    }
                    flash.fadeSuccessAlert = message(code: "twostepauth.resend_success")
                    redirect action: "steptwoauth"
                } else {
                    String emailTokenResponse = request.getParameter(EMAIL_TOKEN_RESPONSE_KEY)
                    Document userDocument = User.collection.findOne([username: username], [_id: 1, emailToken: 1, authorities: 1, emailTokenValidUntil: 1, emailTokenAuthForNextLoginEnabled: 1])
                    String emailToken = userDocument?.get("emailToken")
                    Date emailTokenValidUntil = userDocument?.getDate("emailTokenValidUntil")
                    Boolean verifiedResponse = emailToken == emailTokenResponse

                    if (!verifiedResponse || (emailTokenValidUntil && new Date().after(emailTokenValidUntil))) {
                        log.info("Incorrect email token response from ${username}")
                        flash.fadeErrorAlert = message(code: "index.incorrect_email_token")
                        redirect action: "steptwoauth"
                    } else {
                        Authentication auth = springSecurityService.getAuthentication()
                        // Load user roles
                        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>()
                        List<Role> roles = userDocument.get("authorities")?.collect({ it as Role })
                        authorities.addAll(roles.collect({ new SimpleGrantedAuthority(it.authority) }))
                        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
                        SecurityContextHolder.getContext().setAuthentication(newAuth)
                        userService.updateEmailTokenAuths(userDocument.get("_id"), userDocument.emailTokenAuthForNextLoginEnabled)

                        redirect controller: "main", action: "list"
                    }
                }
            } else {
                redirect action: "index"
            }
        }
    }

    def form() {
        String language = params.lang ? params.lang : "en"
        boolean isPlanetary = params.planetaryRegistration ? true : false
        List<String> countriesWithPlanetary = []

        def userLocales = [(message(code: 'locale.fi')): new Locale("fi"), (message(code: 'locale.' + Locale.US)): Locale.US, (message(code: 'locale.' + Locale.UK)): Locale.UK]
        def userRegistrationMotives = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["registrationMotive"], null)?.collect({
            optimiResourceService.getLocalizedName(it)
        })
        def unitSystems = UnitConversionUtil.UnitSystem.values()
        Locale selectedLocale = localeResolverUtil.resolveLocale(session, request, response)
        String localizedName = "name${language.toUpperCase()}"
        List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1, (localizedName): 1])?.toList()?.sort({
            it.get(localizedName)
        })
        if(isPlanetary){
            BasicDBObject filter = new BasicDBObject("planetary", true)
            countriesWithPlanetary = License.collection.distinct("conditionalCountries",filter,String.class)?.toList()
        }
        if (countries) {
            if (countries.find({ it.get(localizedName) })) {
                countries = countries.sort({ it.get(localizedName) })
            } else {
                countries = countries.sort({ it.get("nameEN") })
                localizedName = "nameEN"
            }
        }
        String termsAndConditionsUrl = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "termsAndConditionsUrl")

        [userRegistrationMotives: userRegistrationMotives, userLocales: userLocales, locale: selectedLocale,countriesWithPlanetary:countriesWithPlanetary,
         unitSystems            : unitSystems, language: language, countries: countries, localizedName: localizedName,isPlanetary:isPlanetary,
         userTypes              : com.bionova.optimi.core.Constants.UserType.map(),termsAndConditionsUrl: termsAndConditionsUrl]
    }

    def cancel() {
        def logoutUrl = getChannelLoginUrl(session)

        if (!logoutUrl) {
            logoutUrl = configurationService.getByConfigurationName(Constants.APPLICATION_ID, ConfigName.FRONTPAGE_URL.toString())?.value
        }

        if (logoutUrl) {
            redirect url: logoutUrl
        } else {
            redirect action: "index"
        }
    }

    def createAccount() {
        User user = new User(params)
        log.info("${params}")
        Boolean spamValidator = params.email ? true : false
        log.info("${spamValidator}")
        List<String> countryAreas = optimiResourceService.getResourceByResourceAndProfileId(user.country, null)?.areas

        if (countryAreas) {
            Resource languageResource = optimiResourceService.getSystemLocaleByAreas(countryAreas)

            if (languageResource) {
                user.language = languageResource.resourceId
            }
        }

        if (!user.language) {
            Locale locale = localeResolverUtil.resolveLocale(session, request, response)
            user.language = locale?.language ? locale.language : params.language
        }
        String inputOrganizationName = ""
        Boolean orgNameTooShort = false
        user.enableFloatingHelp = true
        user.disableZohoChat = false

        if (user.organizationName) {
            inputOrganizationName = user.organizationName
        }

        ChannelFeature channelFeature = getChannelFeature(session)

        if (!user.registrationMotive) {
            if (channelFeature?.nameAsRegistrationMotive) {
                user.registrationMotive = channelFeature.name
            }
        }

        if (inputOrganizationName.length() < 3) {
            orgNameTooShort = true
        }

        if (user.name) {
            String normalizedName = Normalizer.normalize(user.name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
            user.name = HtmlUtils.htmlEscape(normalizedName)

            if (user.name) {
                if (user.name.size() > 50) {
                    user.name = user.name.take(50)
                }
            }
        }

        if (user.validate() && !orgNameTooShort && user.country && !spamValidator) {
            Boolean isPartOfLicense = UserLicenseInvitation.countByEmail(user.username) ? true : false

            if (isPartOfLicense) {
                user.wasInvited = Boolean.TRUE
            } else {
                user.wasInvited = Boolean.FALSE
            }
            user = userService.createUser(user, session)

            if (user) {
                String htmlBody = "" +
                        "Name: ${user.name}<br/>" +
                        "Organization: ${user.organizationName}<br/>" +
                        "Email: ${user.username}<br/>" +
                        "Consent: ${user.consent ? "TRUE" : "FALSE"}<br/>" +
                        "Phone: ${user.phone}<br/>" +
                        "Type: ${user.type}<br/>" +
                        "Is part of license: ${isPartOfLicense}"
                optimiMailService.sendMail({
                    async true
                    to emailConfiguration.registrationEmail
                    from emailConfiguration.contactEmail
                    subject("PROD: New user registered")
                    html htmlBody
                }, emailConfiguration.registrationEmail)
            }
            String hash = userService.generateEmailHash(user)
            String registerUrl = generateRegisterLink([t: user.registrationToken, channelToken: channelFeature?.token, lang: user.language, hash: hash])
            Boolean activationLinkOk = activeCampaignService.createContact(user, registerUrl, channelFeature, session)

            if (activationLinkOk) {
                flash.fadeSuccessAlert = g.message(code: "index.account_created.info")
            } else {
                flash.errorAlert = g.message(code: "email.error", args: [emailConfiguration.supportEmail])
            }
            String populateUserName = user?.username
            // Lets set user to null, so given, right credentials aren't visible anymore
            user = null

            redirect action: "index", params: [accountCreated: true, channelToken: channelFeature?.token, populateUserName: populateUserName]
        } else {
            user.password = null
            /*
             Manual validation for organization && country because adding it to the domain
             object constraints as nullable: false would halt saving other user stuff for users with no organization / country
            */
            String errors = ""
            Boolean noOrganization = !user.organizationName
            Boolean noCountry = !user.country
            def errorsList = user.errors.allErrors

            if (errorsList || noOrganization || orgNameTooShort || noCountry || spamValidator) {
                errors = "<ul>"
                errorsList?.each {
                    errors = "${errors}<li>${message(error: it)}</li>"
                }

                if (noCountry) {
                    errors = "${errors}<li>${message(code: "user.country.nullable")}</li>"
                }
                if (noOrganization) {
                    errors = "${errors}<li>${message(code: "user.organization.nullable")}</li>"
                }
                if (orgNameTooShort) {
                    errors = "${errors}<li>${message(code: "user.organizationName.size.error")}</li>"
                }
                if (spamValidator) {
                    errors = "${errors}<li>Your account cannot be validated currently, please try again later</li>"
                }
                errors = "${errors}</ul>"

            }
            chain(action: "form", model: [user: user, errors: errors, validationFail: true], params: [lang: user?.language])
        }
    }

    def resendActivation() {
        def username = session?.getAttribute("username")

        if (username) {
            User user = userService.getUserByUsername(username)
            if (!user.registrationToken) {
                user.registrationToken = UUID.randomUUID().toString().replaceAll('-', '')
                log.info("TOKEN 1: New token created for user ${user.username}: ${user.registrationToken}")
                user = userService.updateUser(user)
            }
            ChannelFeature channelFeature = getChannelFeature(session)
            Locale locale = localeResolverUtil.resolveLocale(session, request, response)
            String hash = userService.generateEmailHash(user)
            def registerUrl = generateRegisterLink([t   : user.registrationToken, channelToken: channelFeature.token,
                                                    lang: locale?.language, hash: hash])

            try {
                log.info("Trying to resend  activation email for user " + username)
                sendRegistrationMail(user, registerUrl, channelFeature)
                log.info("Resent activation email for user " + username)
                flash.fadeSuccessAlert = g.message(code: "index.reactivation")
            } catch (Exception e) {
                flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
            }
            session?.removeAttribute("username")
            redirect action: "message"
        } else {
            render text: "Unable to resend activation at this time, please contact our support."
        }
    }

    def resendActivationInSoftware() {
        def username = session?.getAttribute("username")
        Boolean logout = params.boolean("logout")

        if (!username) {
            username = userService.getCurrentUser()?.username
        }

        if (username) {
            User user = userService.getUserByUsername(username)

            if (user) {
                if (!user.registrationToken) {
                    user.registrationToken = UUID.randomUUID().toString().replaceAll('-', '')
                    log.info("TOKEN 2: New token created for user ${user.username}: ${user.registrationToken}")
                    user = userService.updateUser(user)
                }
                ChannelFeature channelFeature = getChannelFeature(session)
                Locale locale = localeResolverUtil.resolveLocale(session, request, response)
                String hash = userService.generateEmailHash(user)
                def registerUrl = generateRegisterLink([t   : user.registrationToken, channelToken: channelFeature.token,
                                                        lang: locale?.language, hash: hash])

                try {
                    log.info("Trying to resend  activation email for user " + username)
                    sendRegistrationMail(user, registerUrl, channelFeature)
                    log.info("Resent activation email for user " + username)
                    flash.fadeSuccessAlert = g.message(code: "index.reactivation")
                } catch (Exception e) {
                    flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                }
            } else {
                log.error("ResendActivationInSoftware: Unable to get user with username: ${username}, current logged in user is: ${userService.getCurrentUser()?.username}")
                flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
            }
            session?.removeAttribute("username")
        } else {
            flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
        }

        if (logout) {
            redirect controller: "logout"
        } else {
            redirect controller: 'main', action: "list"
        }
    }

    def activateUser() {
        if (params.t && !"null".equals(params.t)) {
            String usernameFromHash = userService.decodeEmailHash(params.hash)
            log.info("Trying to enable user with token: ${params.t}, email form hash: ${usernameFromHash}")
            session?.removeAttribute("username")
            Map<User, String> returnable = userService.activateUser(params.t, session)
            List returnedUserInList = returnable?.keySet()?.toList()

            if (returnedUserInList && !returnedUserInList.isEmpty()) {
                User user = returnedUserInList.get(0)
                String errorMessage = returnable.get(user)

                if (errorMessage) {
                    if ("User is already activated".equals(errorMessage)) {
                        flash.fadeSuccessAlert = message(code: "index.user_already_activated")
                    } else {
                        String userName = user?.username
                        flash.fadeErrorAlert = message(code: "index.user_registration_error")
                        log.error("Something went wrong with activating user ${userName} and token ${params.t}: ${errorMessage}")
                        /*
                        Returnable contains dummy new User() with no name if user was not found by a token.
                        Thus, support emails have no sense, logging is enough.
                         */
                        if (Environment.current == Environment.PRODUCTION && userName) {
                            String emailTo = message(code: "email.support")
                            String emailFrom = message(code: "email.support")

                            optimiMailService.sendMail({
                                to emailTo
                                from emailFrom
                                replyTo emailTo
                                subject "User activation failed"
                                body "Activating user ${userName} failed. Failing token is ${params.t}, error is ${errorMessage}"
                            }, emailTo)

                            optimiMailService.sendMail({
                                to emailConfiguration.activationFailedEmail
                                from emailFrom
                                replyTo emailConfiguration.activationFailedEmail
                                subject "User activation failed"
                                body "Activating user ${userName} failed. Failing token is ${params.t}, error is ${errorMessage}"
                            }, emailConfiguration.activationFailedEmail)
                        }
                    }
                } else {
                    flash.fadeSuccessAlert = g.message(code: 'index.user_registered')
                    ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
                    if (!channelFeature && user && user.channelToken) {
                        channelFeature = channelFeatureService.getChannelFeatureByToken(user.channelToken)
                    } else if (user && user.channelToken && channelFeature && !user.channelToken.equals(channelFeature.token)) {
                        channelFeature = channelFeatureService.getChannelFeatureByToken(user.channelToken)
                    }

                    if (channelFeature && "energy7".equals(channelFeature.token) && user.activeCampaignId) {
                        Boolean ok = activeCampaignService.addTagForUser(user.activeCampaignId, [ActiveCampaignService.USER_MALTA_CHANNEL_TAG_ID]) // malta channel tag
                    }
                }
            } else {
                flash.fadeErrorAlert = message(code: "index.user_registration_error")
                log.error("Something went wrong with activating user: ${returnable?.values()}")
            }

            if (userService.isLoggedIn()) {
                session?.setAttribute("removeConfirmationPrompts", true)
                redirect controller: "main", action: "list"
            } else {
                redirect action: "message"
            }
        } else {
            flash.fadeErrorAlert = message(code: "index.user_registration_error")
            redirect controller: "main", action: "list"
        }
    }

    private String generateRegisterLink(linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: "index", action: "activateUser",
                params: linkParams)
    }

    private void sendRegistrationMail(User user, registerUrl, ChannelFeature channelFeature) {
        def emailTo = user?.username
        def emailSubject = g.message(code: 'index.registration_mail.subject')
        def emailFrom = g.message(code: 'email.support')
        def replyToAddress = message(code: "email.support")
        def emailBody = g.message(code: "index.registration_mail.body", args: [registerUrl, channelFeatureService.processLoginUrl(channelFeature?.loginUrl, request)], encodeAs: "Raw")
        optimiMailService.sendMail({
            to emailTo
            from emailFrom
            replyTo replyToAddress
            subject emailSubject
            body emailBody
        }, emailTo)
    }

    def authfail() {
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        boolean accountNotEnabled = false

        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "login.expired")
            } else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "login.passwordExpired")
            } else if (exception instanceof DisabledException) {
                accountNotEnabled = true
                //UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY is deprecated
                def username = session[SpringSecurityUtils.SPRING_SECURITY_LAST_USERNAME_KEY]

                if (username) {
                    username = StringEscapeUtils.unescapeHtml(username)
                    session?.setAttribute("username", username)
                }
                msg = g.message(code: "login.disabled")
                String link = " <a href=\"${createLink(controller: 'index', action: "resendActivation")}\">${message(code: 'index.resend_activation')}</a>"
                msg = msg + link

            } else if (exception instanceof LockedException) {
                // Actually our accounts get never locked, but we give this exception, if !emailValidated to given right
                // errorMessage
                msg = g.message(code: "login.locked")
            } else if (exception instanceof MaximumFreeUsersReachedException) {
                msg = g.message(code: "maximum_free_users_reached")
                log.error("MAXIMUM FREE USERS REACHED")
            } else {
                msg = g.message(code: "login.fail")
            }
        }
        flash.fadeErrorAlert = msg
        String channelLoginUrl = getChannelLoginUrl(session)

        if (accountNotEnabled) {
            if (channelLoginUrl.contains("?")) {
                channelLoginUrl = channelLoginUrl + "&notEnabled=true"
            } else {
                channelLoginUrl = channelLoginUrl + "?notEnabled=true"

            }
        } else {
            if (channelLoginUrl.contains("?")) {
                channelLoginUrl = channelLoginUrl + "&loginError=true"

            } else {
                channelLoginUrl = channelLoginUrl + "?loginError=true"

            }
        }
        redirect url: channelLoginUrl
    }

    def deniedError() {
        //Redirect all unauthorized accessing to index
        redirect controller: "index"
    }

    def message() {
        def logoutUrl = chainModel?.channelLoginUrl

        if (!logoutUrl) {
            logoutUrl = getChannelLoginUrl(session)

            if (!logoutUrl) {
                logoutUrl = configurationService.getByConfigurationName(Constants.APPLICATION_ID, ConfigName.FRONTPAGE_URL.toString())?.value
            }
        }
        def accountCreatedInfo = params.accountCreated ? message(code: "index.account_created.info") : ''
        [logoutUrl: logoutUrl, accountCreatedInfo: accountCreatedInfo]
    }

    private String getChannelLoginUrl(HttpSession session) {
        return channelFeatureService.processLoginUrl(getChannelFeature(session)?.loginUrl, request)
    }

    private ChannelFeature getChannelFeature(HttpSession session) {
        ChannelFeature channelFeature = session?.getAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString())

        if (!channelFeature) {
            channelFeature = channelFeatureService.getDefaultChannel()
        }
        return channelFeature
    }

    def loginUrl() {
        SavedRequest savedRequest = SpringSecurityUtils.getSavedRequest(session)
        String channelLoginUrl = getChannelLoginUrl(session)

        if (channelLoginUrl && savedRequest?.getParameterValues("applicationId")) {
            if (channelLoginUrl.contains("?")) {
                channelLoginUrl = "${channelLoginUrl}&applicationId=${savedRequest.getParameterValues("applicationId")[0]}"
            } else {
                channelLoginUrl = "${channelLoginUrl}?applicationId=${savedRequest.getParameterValues("applicationId")[0]}"
            }

            if (savedRequest?.getParameterValues("originatingSystem")) {
                channelLoginUrl = "${channelLoginUrl}&originatingSystem=${savedRequest.getParameterValues("originatingSystem")[0]}"
            }
        }
        redirect url: channelLoginUrl
    }
}
