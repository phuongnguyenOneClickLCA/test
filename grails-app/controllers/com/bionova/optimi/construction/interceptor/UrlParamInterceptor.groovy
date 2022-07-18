/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor

import groovy.transform.CompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class UrlParamInterceptor {

    UrlParamInterceptor() {
        matchAll().excludes(controller: 'error')
    }

    boolean before() {
//        log.debug("-----------------UrlParamInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        def entityId = params.entityId

        if (entityId) {
            request.setAttribute("entityId", entityId)
        } else {
            params.entityId = request.getAttribute("entityId")
        }

        // Response headers are set in 'before' method (instead of 'afterView' one) as a workaround for the asynchronous requests.
        response.setHeader('Cache-Control', 'no-cache, no-store, must-revalidate, private')
        response.setHeader('Pragma', 'no-cache')
        response.setHeader('Strict-Transport-Security', 'max-age=31536000')
        response.setHeader('X-Frame-Options', 'SAMEORIGIN')

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
