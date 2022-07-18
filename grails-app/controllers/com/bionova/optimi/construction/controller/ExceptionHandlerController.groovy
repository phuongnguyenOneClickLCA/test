package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import grails.util.Environment
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.InvalidDataAccessResourceUsageException
import org.springframework.transaction.HeuristicCompletionException
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.multipart.MultipartException

/**
 * @author Pasi-Markus Mäkelä
 */
class ExceptionHandlerController {

    def loggerUtil
    def optimiMailService
    def flashService

    @Autowired
    EmailConfiguration emailConfiguration

    private static final String EMAIL_FROM = Environment.current == Environment.PRODUCTION ? "contact@oneclicklca.com" : "dev@oneclicklca.com"

    private String getEnvPrefix() {
       if (Environment.current == Environment.PRODUCTION) {
           return "PROD: "
       } else {
           return "DEV: "
       }
    }

    def optimisticLockingException(final OptimisticLockingException exception) {
        log.warn("Handled error logging: Optimistic locking. User or users have attempted concurrent update-type access to data")

        optimiMailService.sendMail({
            to emailConfiguration.notificationEmail
            from getEmailFrom()
            replyTo emailConfiguration.contactEmail
            subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()}Handled error logging: Optimistic locking. User or users have attempted concurrent update-type access to data"))
            html(getExceptionStackTrace(exception))
        }, emailConfiguration.notificationEmail)

        flash.fadeErrorAlert = message(code: "error.optimisticLocking")
        redirect(uri: request.getHeader('referer'))
    }
    
    private String getEmailFrom() {
        return Environment.current == Environment.PRODUCTION ? 
                emailConfiguration.contactEmail : emailConfiguration.devEmail
    }

    def sizeLimitExceededException(final SizeLimitExceededException exception) {
        log.warn("Handled error logging: sizeLimitExceededException. User or users have attempted to upload too big file")

        optimiMailService.sendMail({
            to emailConfiguration.notificationEmail
            from getEmailFrom()
            replyTo emailConfiguration.contactEmail
            subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()}Handled error logging: sizeLimitExceededException. User or users have attempted to upload too big file"))
            html(getExceptionStackTrace(exception))
        }, emailConfiguration.notificationEmail)

        flash.fadeErrorAlert = message(code: "sizeLimitExceededException", args: [(exception.getActualSize() / (1024 * 1024)).toString()])
        redirect(uri: request.getHeader('referer'))
    }

    def multipartException(final MultipartException exception) {
        log.warn("Handled error logging: Client has failed / interrupted to upload file to application server: ${getExceptionStackTrace(exception)}")

        optimiMailService.sendMail({
            to emailConfiguration.notificationEmail
            from getEmailFrom()
            replyTo emailConfiguration.contactEmail
            subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()}Handled error logging: Client has failed / interrupted to upload file to application server."))
            html(getExceptionStackTrace(exception))
        }, emailConfiguration.notificationEmail)
        redirect controller: "main"
    }

    def illegalStateException(final IllegalStateException exception) {
        log.warn("Handled error logging: The user may have submitted the same form several times or logged in with multiple accounts.")

        optimiMailService.sendMail({
            to emailConfiguration.notificationEmail
            from getEmailFrom()
            replyTo emailConfiguration.contactEmail
            subject(optimiMailService.addHostnameToSubject("${getEnvPrefix()}Handled error logging: The user may have submitted the same form several times. This is prevented by session fixation protection."))
            html(getExceptionStackTrace(exception))
        }, emailConfiguration.notificationEmail)

        redirect controller: "main"
    }

    def invalidDataAccessException(final InvalidDataAccessResourceUsageException exception) {
        log.warn("Handled error logging: ${getExceptionStackTrace(exception)}")
        redirect controller: "main"
    }

    def heuristicCompletionException(final HeuristicCompletionException exception) {
        log.warn("Handled error logging. Caused probably by importing indicator / query with map key containing invalid chars (eq. '.', '\$'): ${getExceptionStackTrace(exception)}")
        flash.fadeErrorAlert = "Handled error. Caused probably by importing indicator / query with map key containing invalid chars (eq. '.', '\$')"
        redirect controller: "main"
    }

    def transactionSystemException(final TransactionSystemException exception) {
        log.warn("Handled error logging: TransactionSystemException. User or users have attempted concurrent update-type access to data")
    }

    private String getExceptionStackTrace(Exception exception) {
        StringWriter sw = new StringWriter()
        exception.printStackTrace(new PrintWriter(sw))
        return sw.toString()
    }
}
