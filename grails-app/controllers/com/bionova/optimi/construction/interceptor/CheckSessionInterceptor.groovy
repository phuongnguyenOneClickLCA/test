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
class CheckSessionInterceptor {

    CheckSessionInterceptor() {
        matchAll().excludes(controller: 'error')
    }

    boolean before() {
//        log.debug("-----------------CheckSessionInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")

        if (!session) {
            response.sendRedirect("/")
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
