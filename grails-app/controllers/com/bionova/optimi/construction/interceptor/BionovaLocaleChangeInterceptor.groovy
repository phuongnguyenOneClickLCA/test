/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor
/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class BionovaLocaleChangeInterceptor {

    BionovaLocaleChangeInterceptor() {
        matchAll().excludes(controller: 'error')
    }

    def localeResolverUtil

    boolean before() {
//        log.debug("-----------------BionovaLocaleChangeInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        localeResolverUtil.resolveLocale(session, request, response)
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
