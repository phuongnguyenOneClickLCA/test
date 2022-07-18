/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.util

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.ChannelFeatureService
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.service.UserService
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.web.util.WebUtils
import org.springframework.web.servlet.i18n.SessionLocaleResolver

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
class LocaleResolverUtil {

    SessionLocaleResolver localeResolver
    UserService userService
    ChannelFeatureService channelFeatureService
    FlashService flashService
    LoggerUtil loggerUtil

    private static final Log log = LogFactory.getLog(LocaleResolverUtil.class)

    @CompileStatic
    def resolveLocale(HttpSession session = null, HttpServletRequest request = null, HttpServletResponse response = null) {
        Locale locale
        try {
            request = request ? request : getRequest()
            response = response ? response : getResponse()
        } catch (e) {
            if (!e.message?.contains('You cannot use the session in non-request rendering operations')) {
                loggerUtil.warn(log, "Error in resolveLocale LocaleResolverUtil", e)
                flashService.setErrorAlert("Error in resolveLocale LocaleResolverUtil: ${e.getMessage()}", true)
            }
        }

        User user = userService.getCurrentUser(true)
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
        List<String> allowLanguageForChannel = channelFeature.getLanguagesForChannel()
        String languageSet = Constants.DEFAULT_LOCALE
        String lang
        if (user?.language) {
            lang = user.language
        } else if (request?.getParameter("lang")) {
            lang = request.getParameter("lang").toLowerCase()
        } else if (session?.getAttribute("lang")) {
            lang = session?.getAttribute("lang")
        } else {
            String languageFromRequest = request?.getLocale()?.language

            if (languageFromRequest) {
                lang = languageFromRequest
            }

        }
        if(lang && allowLanguageForChannel && (allowLanguageForChannel.contains(lang) || allowLanguageForChannel.contains(lang.toUpperCase()))){
            languageSet = lang
        }
        locale = new Locale(languageSet)
        try {
            localeResolver.setLocale(request, response, locale)
        } catch (e) {
            if (!e.message?.contains('You cannot use the session in non-request rendering operations')) {
                loggerUtil.warn(log, "Error in resolveLocale LocaleResolverUtil >>> setLocale", e)
                flashService.setErrorAlert("Error in resolveLocale LocaleResolverUtil >>> setLocale: ${e.getMessage()}", true)
            }
        }
        session?.setAttribute("lang", languageSet)
        session?.setAttribute("urlLang", session?.getAttribute("lang")?.toString()?.toUpperCase())
        return locale
    }

    @CompileStatic
    private HttpServletRequest getRequest() {
        return WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
    }

    @CompileStatic
    private HttpServletResponse getResponse() {
        return WebUtils.retrieveGrailsWebRequest().getCurrentResponse()
    }
}
