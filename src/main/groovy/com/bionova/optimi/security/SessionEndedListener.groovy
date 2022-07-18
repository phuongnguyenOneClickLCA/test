package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.FloatingLicenseUser
import com.bionova.optimi.core.domain.mongo.License
import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.context.ApplicationListener
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.session.SessionDestroyedEvent
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 * Session ended listener
 *
 * configured as bean within resources.groovy
 */
class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent> {

    private static final Log log = LogFactory.getLog(SessionEndedListener.class)

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        for (SecurityContext securityContext : event.getSecurityContexts()) {
            Authentication authentication = securityContext?.getAuthentication()
            GrailsUser grailsUser = (GrailsUser) authentication?.getPrincipal()

            // Temporal log for SW-1867.
            log.debug "Destroying session (${authentication.getDetails()}) for user '${grailsUser?.username}'."

            if (grailsUser && grailsUser.username) {

                List<License> floatingLicenses = License.withCriteria {
                    floatingLicenseCheckedInUsers {
                        eq("username", "${grailsUser.username}")
                    }
                }

                if (floatingLicenses) {
                    floatingLicenses.each { License l ->
                        FloatingLicenseUser floatingLicenseUser = l.floatingLicenseCheckedInUsers.find({ it.username == grailsUser.username })

                        if (floatingLicenseUser) {
                            l.floatingLicenseCheckedInUsers.remove(floatingLicenseUser)
                            try {
                                l.merge(flush: true)
                            } catch(Exception e) {

                            }
                        }
                    }
                }
            }
        }
    }
}
