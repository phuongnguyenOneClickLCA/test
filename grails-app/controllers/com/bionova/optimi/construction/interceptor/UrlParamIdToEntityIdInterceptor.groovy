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
class UrlParamIdToEntityIdInterceptor {

    UrlParamIdToEntityIdInterceptor() {
        match(controller: 'entity', action: 'show')
    }

    boolean before() {
//        log.debug("-----------------UrlParamIdToEntityIdInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        def entityId = params.id

        if (entityId) {
            params.entityId = entityId
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

}
