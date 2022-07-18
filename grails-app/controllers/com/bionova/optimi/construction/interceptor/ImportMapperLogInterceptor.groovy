package com.bionova.optimi.construction.interceptor

import groovy.transform.CompileStatic

import javax.servlet.http.HttpServletRequest

@CompileStatic
class ImportMapperLogInterceptor {
    ImportMapperLogInterceptor() {
        match(controller: 'importMapper', action: '*')

    }

    boolean before() {
//        log.debug("-----------------ImportMapperLogInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        if (("GET") == request.getMethod().toUpperCase()) {
            log.info("ImportMapper GET call: ${request.getRequestURL().toString()}")
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

    private String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
