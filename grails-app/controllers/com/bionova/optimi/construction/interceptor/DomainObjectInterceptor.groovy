package com.bionova.optimi.construction.interceptor

class DomainObjectInterceptor {
    DomainObjectInterceptor() {
        matchAll().excludes(controller: 'entity', action: 'show').excludes(controller: 'error')
    }

    boolean before() {
//        log.debug("-----------------DomainObjectInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        session?.entity?.clearErrors()
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
