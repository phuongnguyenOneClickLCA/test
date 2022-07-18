package com.bionova.optimi.construction.interceptor

import groovy.transform.CompileStatic

@CompileStatic
class QueryInterceptor {
    QueryInterceptor() {
        matchAll().excludes(controller: 'query', action: 'form').excludes(controller: 'error')
    }

    boolean before() {
//        log.debug("-----------------QueryInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        if (session) {
            session.removeAttribute("filterOnCriteria")
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
