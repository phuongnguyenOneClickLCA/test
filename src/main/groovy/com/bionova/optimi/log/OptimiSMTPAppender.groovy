package com.bionova.optimi.log

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.boolex.OnMarkerEvaluator
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.boolex.EvaluationException
import grails.util.Environment
import grails.util.Holders

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiSMTPAppender extends OnMarkerEvaluator {
    private static Date quotaTimestamp
    private static Date lastExceptionSent
    private static final Integer maxEmails = 5
    private static Integer emailsSent
    private static Boolean quotaMailSent = Boolean.FALSE
    private static final Integer quarantinePeriod = 5

    @Override
    boolean evaluate(ILoggingEvent event) throws EvaluationException {
        Date date = new Date()

        if (!isQuotaReached() && !isExceptionCountReached()) {
            quotaMailSent = Boolean.FALSE
            lastExceptionSent = date

            if (!emailsSent || emailsSent < maxEmails) {
                emailsSent = emailsSent ? emailsSent + 1 : 1
            }

            //will send an email if: throwable exists and level is ERROR despite the marker is null OR if ERROR marker exists:
            //{@link ch.qos.logback.classic.Logger#error(String msg, Throwable t)}
            //{@link ch.qos.logback.classic.Logger#error(Marker marker, String msg, Throwable t)}
            if ((event?.getThrowableProxy() && event.getLevel()?.levelInt >= Level.ERROR_INT) || super.evaluate(event)) {
                return true
            }
        } else {
            if (isExceptionCountReached()) {
                quotaTimestamp = new Date()
                emailsSent = 0
            }

            if (!quotaMailSent) {
                def mailService = getMailService()
                if (quotaTimestamp == null) {
                    quotaTimestamp = new Date()
                }

                mailService.sendMail({
                    to "${emailConfiguration?.notificationEmail ?: '360optimi-notification@oneclicklca.com'}"
                    from "${emailConfiguration?.contactEmail ?: 'contact@oneclicklca.com'}"
                    replyTo "${emailConfiguration?.contactEmail ?: 'contact@oneclicklca.com'}"
                    subject(mailService.addHostnameToSubject("${getEnvPrefix()} Temporary error email message delivery quota ${maxEmails} reached. No new messages are sent in ${quarantinePeriod} minutes"))
                    html("""During the last ${quarantinePeriod} minutes the error message delivery quota ${maxEmails} has been filled.<br />
                            Quota timestamp is ${new SimpleDateFormat("dd.MM.yyyy HH:mm:ss,SSS").format(quotaTimestamp)}.<br />
                            Email message delivery resumes after the quarantine period of ${quarantinePeriod} minutes is passed.<br />
                            All errors can be found in the error logs.""")
                }, "${emailConfiguration?.notificationEmail ?: '360optimi-notification@oneclicklca.com'}")
                quotaMailSent = Boolean.TRUE
            }
        }
        return false
    }

    //Specifying a type can cause a groovy cast exception for the bean obtained from the application context
    //example: Cannot cast object 'com.bionova.optimi.core.service.OptimiMailService@34b4ade' with class
    // 'com.bionova.optimi.core.service.OptimiMailService' to class 'com.bionova.optimi.core.service.OptimiMailService'
    private def getMailService() {
        return Holders.getApplicationContext().getBean("optimiMailService")
    }

    private def getEmailConfiguration() {
        return Holders.getApplicationContext().getBean("emailConfiguration")
    }

    private String getEnvPrefix() {
        if (Environment.current == Environment.PRODUCTION) {
            return "PROD: "
        } else if (Environment.current == Environment.DEVELOPMENT) {
            return "DEV: "
        } else {
            return "LOCAL_DEV: "
        }
    }

    private Boolean isQuotaReached() {
        Boolean reached = Boolean.FALSE
        Date date = new Date()

        if (quotaTimestamp && ((date.getTime() - quotaTimestamp.getTime()) / 60000) < quarantinePeriod) {
            reached = Boolean.TRUE
        }
        return reached
    }

    private Boolean isExceptionCountReached() {
        Boolean reached = Boolean.FALSE
        Date date = new Date()

        if (emailsSent && lastExceptionSent && emailsSent == maxEmails && ((date.getTime() - lastExceptionSent.getTime()) / 60000) < quarantinePeriod) {
            reached = Boolean.TRUE
        }
        return reached
    }

}
