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
class ClearDryRunResourcesInterceptor {
    ClearDryRunResourcesInterceptor() {
        matchAll().excludes(controller: 'import').excludes(controller: 'error')

    }

    boolean before() {
//        log.debug("-----------------ClearDryRunResourcesInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        session?.removeAttribute("dryRunResources")
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}

