package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.rest.token.AccessToken
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.bson.Document
import org.springframework.security.core.AuthenticationException
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class BearerTokenAuthenticationFilter extends GenericFilterBean {
    SpringSecurityService springSecurityService

    Log log = LogFactory.getLog(BearerTokenAuthenticationFilter.class)

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!springSecurityService.isLoggedIn()) {
            String bearerToken = request.getParameter("bearerToken")

            if (!bearerToken) {
                bearerToken = findTokenFromHeader((HttpServletRequest) request)
            }

            if (bearerToken) {
                // TODO check that token is not older than 8 hours
                Document user = User.collection.findOne([bearerToken: bearerToken])
                Date bearerTokenCreated = user?.get("bearerTokenCreated")
                Date date = new Date()

                if (user && bearerTokenCreated && date.time - bearerTokenCreated.time <= 28800000) {
                    try {
                        springSecurityService.reauthenticate(user.get("username"), user.get("password"))
                    } catch (AuthenticationException e) {
                        log.error("Could not reauthenticate user: ", e)
                    }
                } else {
                    log.warn("Could not find user ${user?.get("username")} with bearerToken or bearerToken older than 8 hours, " +
                            "request params: ${request.parameterMap}")
                }
            }
        }
        chain.doFilter(request, response)
    }

    String findTokenFromHeader(HttpServletRequest request) {
        String tokenValue = null
        String header = request.getHeader('Authorization')

        if (header && header.startsWith('Bearer') && header.length() >= 8) {
            tokenValue = header.substring(7)
        }
        return tokenValue
    }
}
