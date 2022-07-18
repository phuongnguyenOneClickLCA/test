/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.BootStrap
import com.bionova.optimi.configuration.EmailConfiguration
import grails.plugins.mail.MailService
import grails.util.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailMessage
import org.springframework.mail.MailSendException

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiMailService {
    MailService mailService

    @Autowired
    EmailConfiguration emailConfiguration

    def loggerUtil
    def flashService
    ShellCmdService shellCmdService

    MailMessage sendMail(Closure callable, String mailTo, Boolean authenticationToken = Boolean.FALSE) {
        try {
            if (authenticationToken) {
                mailService.sendMail(callable)
            } else {
                if ((Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM) &&
                        (emailConfiguration.isProtectedEmail(mailTo) || emailConfiguration.testingEmail.equalsIgnoreCase(mailTo))) {
                    loggerUtil.info(log, "Sending email in development to " + mailTo)
                    mailService.sendMail(callable)
                } else if ((Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM) &&
                        !emailConfiguration.isProtectedEmail(mailTo)) {
                    loggerUtil.info(log, "Prevented sending email in development to " + mailTo)
                } else {
                    loggerUtil.info(log, "Sending email in " + Environment.current.getName() + " to " + mailTo)
                    mailService.sendMail(callable)
                }
            }
        } catch (MailSendException e) {
            loggerUtil.warn(log, "Could not send email, maybe email service is not available on this server", e)
            flashService.setFadeWarningAlert("Could not send email, maybe email service is not available on this server. Exception: ${e.message}", true)
            return null
        }
    }

    String addHostnameToSubject(String subject) {
        return BootStrap.HOST_NAME + " " + subject;
    }
}
