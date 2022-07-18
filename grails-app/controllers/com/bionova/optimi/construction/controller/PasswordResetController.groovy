/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.User
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class PasswordResetController extends ExceptionHandlerController {

    def userService
    def channelFeatureService

    @Autowired
    EmailConfiguration emailConfiguration

    def resetPassword() {
        if (params.t) {
            def user = userService.getUserByToken(params.t)

            if (!user) {
                redirect controller: "index", action: "message"
            } else {
                [user: user, t: params.t]
            }
        }
    }

    def savePassword() {
        User user = userService.getUserById(params.id)

        if (user) {
            user.properties = params

            if (user.validate()) {
                userService.resetPassword(user)
                flash.fadeSuccessAlert = g.message(code: 'passwordReset.reset_ok')
                redirect controller: "index", action: "message"
            } else {
                flash.fadeErrorAlert = renderErrors(bean: user)
                chain action: "resetPassword", model: [user: user, t: params?.t], params: [t: params?.t]
            }
        }
    }

    def sendResetPasswordMail() {
        def email = params.email
        EmailValidator emailValidator = EmailValidator.getInstance()

        if (!email) {
            flash.fadeErrorAlert = g.message(code: "passwordReset.email_empty")
        } else if (!emailValidator.isValid(email)) {
            flash.fadeErrorAlert = g.message(code: "passwordReset.email_invalid_format")
        } else {
            User user = userService.getUserByUsername(email.trim())

            if (user && !user.accountLocked) {
                if (user.enabled) {
                    if (!user.registrationToken) {
                        user.registrationToken = UUID.randomUUID().toString().replaceAll('-', '')
                        log.info("TOKEN 3: New token created for user ${user.username}: ${user.registrationToken}")
                        user = userService.updateUser(user)
                    }
                    def resetUrl = generateResetPasswordLink([t: user.registrationToken])

                    try {
                        sendResetMail(user, resetUrl)
                        flash.fadeSuccessAlert = message(code: "passwordReset.email_sent")
                    }
                    catch (Exception e) {
                        flash.errorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                    }
                } else {
                    flash.errorAlert = message(code: "passwordReset.please_enable_account")
                }
            }
        }
        String channelLoginUrl = getChannelLoginUrl(session)
        chain controller: "index", action: "message", model: [channelLoginUrl: channelLoginUrl]
    }

    private String getChannelLoginUrl(HttpSession session) {
        return channelFeatureService.processLoginUrl(getChannelFeature(session)?.loginUrl, request)
    }

    private ChannelFeature getChannelFeature(HttpSession session) {
        ChannelFeature channelFeature = session?.getAttribute(Constants.SessionAttribute.CHANNEL_FEATURE.toString()) as ChannelFeature

        if (!channelFeature) {
            channelFeature = channelFeatureService.getDefaultChannel()
        }
        return channelFeature
    }

    private String generateResetPasswordLink(linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: "passwordReset", action: "resetPassword",
                params: linkParams)
    }

    private void sendResetMail(User user, resetUrl) {
        def emailTo = user.username
        def emailSubject = g.message(code: 'passwordReset.mail.subject')
        def emailFrom = g.message(code: 'email.support')
        def replyToAddress = message(code: "email.support")
        def emailBody = g.message(code: 'passwordReset.mail.body', args: [resetUrl])
        optimiMailService.sendMail({
            to emailTo
            from emailFrom
            replyTo replyToAddress
            subject emailSubject
            body emailBody
        }, emailTo)
    }
}
