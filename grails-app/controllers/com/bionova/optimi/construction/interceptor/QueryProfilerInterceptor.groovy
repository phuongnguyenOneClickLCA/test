/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor
/**
 * @author Pasi-Markus Mäkelä
 */
class QueryProfilerInterceptor {
    QueryProfilerInterceptor() {
        match(controller: 'query', action: 'form')

    }

    boolean before() {
//        log.debug("-----------------QueryProfilerInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        request._timeBeforeRequest = System.currentTimeMillis()
        true
    }

    boolean after() {
        request._timeAfterRequest = System.currentTimeMillis()
        true }

    void afterView() {
        def actionDuration = request._timeAfterRequest - request._timeBeforeRequest
        def viewDuration = System.currentTimeMillis() - request._timeAfterRequest
        // Performance log - query open for entity PARENT, CHILD and query QUERY
        log.debug("Performance log - action duration for query open for entity ${params.entityName}, ${params.childName} and query ${params.queryId}: ${actionDuration}ms")
        log.debug("Performance log - view rendering duration for query open for entity ${params.entityName}, ${params.childName} and query ${params.queryId}: ${viewDuration}ms")
    }
}

