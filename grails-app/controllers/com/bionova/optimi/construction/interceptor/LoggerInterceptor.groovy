package com.bionova.optimi.construction.interceptor

import com.bionova.optimi.BootStrap
import groovy.transform.CompileStatic
import org.slf4j.MDC

@CompileStatic
class LoggerInterceptor {

    LoggerInterceptor() {
        matchAll()
    }

    boolean before() {
        MDC.put("hostname", BootStrap.HOST_NAME)
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
