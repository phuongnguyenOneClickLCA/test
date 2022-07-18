package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.LoginService

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent


/**
 * Created by the one and only.
 */
public class CustomAuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    Log log = LogFactory.getLog(CustomAuthenticationSuccessListener.class)
    def userService
    LoginService loginService

    /*  Sets session MaxInactiveInterval based on conditions  */

    void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        try {
            def session = grails.plugin.springsecurity.web.SecurityRequestHolder.getRequest().getSession(false)
            User user = User.findByUsername(event.authentication.principal.username)

            loginService.loginSucceeded(user.username)

            if (user && !user.getIsCommercialUser() && session) {
                session.setMaxInactiveInterval(3600) // 1h if user is not commercial user
            }
        } catch (Exception e) {
            log.error("UNEXPECTED ERROR ON CustomAuthenticationSuccessListener: ${e}")
        }
    }
}

