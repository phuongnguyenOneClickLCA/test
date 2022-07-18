/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor
/**
 * @author Pasi-Markus Mäkelä
 */
class EntityProfilerInterceptor {
    EntityProfilerInterceptor() {
        match(controller: 'entity', action: 'show')

    }

    boolean before() {
//        log.debug("-----------------EntityProfilerInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        request._timeBeforeRequest = System.currentTimeMillis()

        true
    }

    boolean after() {
        request._timeAfterRequest = System.currentTimeMillis()
        true }

    void afterView() {
        def actionDuration = request._timeAfterRequest - request._timeBeforeRequest
        def viewDuration = System.currentTimeMillis() - request._timeAfterRequest
        // Performance log - entity main page for entity NAME
        log.debug("Performance log - action duration for entity main page for entity ${params.name}: ${actionDuration}ms")
        log.debug("Performance log - view rendering duration for entity main page for entity ${params.name}: ${viewDuration}ms")
    }
}

