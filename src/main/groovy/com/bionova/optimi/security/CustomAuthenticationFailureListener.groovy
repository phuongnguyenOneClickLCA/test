package com.bionova.optimi.security

import com.bionova.optimi.core.service.LoginService

import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.context.ApplicationListener

class CustomAuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    LoginService loginService

    @Override
    void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        loginService.loginFailed(event.authentication.principal)
    }
}
