package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.UserSuspiciousActivity
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.session.SessionInformation
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationException

class CustomConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(CustomConcurrentSessionControlAuthenticationStrategy.class)

    CustomConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
        super(sessionRegistry)
    }
    protected void allowableSessionsExceeded(List<SessionInformation> sessions,
                                             int allowableSessions, SessionRegistry registry)
            throws SessionAuthenticationException {
        if (sessions) {
            setDuplicateLoggingAttempt(sessions.get(0).principal)
        }
        super.allowableSessionsExceeded(sessions, allowableSessions, registry)

    }

    private void setDuplicateLoggingAttempt(GrailsUser grailsUser) {
        User user = User.findByUsername(grailsUser.username)
        UserSuspiciousActivity userSuspiciousActivity = user.userSuspiciousActivity
        List<Date> loggingInWhenSessionIsValid

        if (userSuspiciousActivity) {
            loggingInWhenSessionIsValid = userSuspiciousActivity.loggingInWhenSessionIsValid
        } else {
            userSuspiciousActivity = new UserSuspiciousActivity()
        }

        if (loggingInWhenSessionIsValid) {
            loggingInWhenSessionIsValid.add(new Date())
        } else {
            loggingInWhenSessionIsValid = new ArrayList<Date>()
            loggingInWhenSessionIsValid.add(new Date())
        }
        userSuspiciousActivity.loggingInWhenSessionIsValid = loggingInWhenSessionIsValid
        user.userSuspiciousActivity = userSuspiciousActivity
        user.merge(flush: true)
    }
}
