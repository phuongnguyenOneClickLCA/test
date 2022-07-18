package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.User
import grails.plugin.springsecurity.rest.RestAuthenticationSuccessHandler
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.core.Authentication

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by pmm on 8/7/17.
 */
public class CustomRestAuthenticationSuccessHandler extends RestAuthenticationSuccessHandler {

    AccessTokenJsonRenderer renderer
    Log log = LogFactory.getLog(CustomRestAuthenticationSuccessHandler.class)

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        response.contentType = 'application/json'
        response.characterEncoding = 'UTF-8'
        response.addHeader 'Cache-Control', 'no-store'
        response.addHeader 'Pragma', 'no-cache'
        AccessToken accessToken = authentication as AccessToken

        try {
            User user = User.findByUsername(accessToken.principal.username)
            User.collection.updateOne(["_id": user.id], [$set: [bearerToken: accessToken.accessToken, bearerTokenCreated: new Date()]])
        } catch (Exception e) {
            log.error("Exception in setting bearerToken to user: ${e}")
        }
        String json = renderer.generateJson(accessToken)
        response << json
    }
}

