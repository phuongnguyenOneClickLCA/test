package com.bionova.optimi.construction.interceptor

import com.bionova.optimi.construction.Constants
import grails.web.mvc.FlashScope
import groovy.transform.CompileStatic
import org.grails.web.util.WebUtils

/*
    Intercept req to deliberately preserve flash scope after multiple redirects.
 */

@CompileStatic
class FlashInterceptor {

    FlashInterceptor() {
        match controller: 'projectTemplate', action: 'startNewProject'
        match controller: 'entity', action: 'saveIndicators'
        match controller: 'design', action: 'newDesign'
        match controller: 'design', action: 'simulationTool'
        match controller: 'query', action: 'form'
    }

    FlashScope getFlash() {
        WebUtils.retrieveGrailsWebRequest()?.flashScope
    }

    boolean before() {
        try {
            if (session.getAttribute(Constants.SessionAttribute.FLASH_SCOPE.attribute)) {
                FlashScope flash = getFlash()
                session.getAttribute(Constants.SessionAttribute.FLASH_SCOPE.attribute)?.each { String key, Object value ->
                    flash?.put(key,value)
                }
            }
        } catch (e) {
            log.error("Error in FlashInterceptor before $e")
        }
        true
    }

    boolean after() {
        FlashScope flash = getFlash()
        if (flash) {
            session.setAttribute(Constants.SessionAttribute.FLASH_SCOPE.attribute, flash)
        }
        true
    }

    void afterView() {
        // no-op
    }
}
