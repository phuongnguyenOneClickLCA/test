package com.bionova.optimi.construction.interceptor

import groovy.transform.CompileStatic

import javax.servlet.http.HttpServletRequest

@CompileStatic
class ImportMapperLogPostFileInterceptor {
    ImportMapperLogPostFileInterceptor() {
        match(controller: 'importMapper', action: 'postFile')

    }

    boolean before() {
//        log.debug("-----------------ImportMapperLogPostFileInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        if (("GET") == request.getMethod().toUpperCase()) {
            log.info("GET request ImportMapperFile interface call. It should not be allowed!")
        } else {
            log.info("ImportMapperFile interface call: ${request.getRequestURL().toString()}. Parameters are ${request.getParameterNames()?.toList()}")
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
