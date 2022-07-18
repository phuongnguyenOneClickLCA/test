/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor
/**
 * @author Pasi-Markus Mäkelä
 */
class CheckBrowserInterceptor {

    CheckBrowserInterceptor() {
        matchAll().excludes(controller:'error')
    }

    def userService
    def loggerUtil

    boolean before() {
//        log.debug("-----------------CheckBrowserInterceptor : before >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
        try {
            if (session) {
                session.setAttribute("validBrowser", userService.isValidBrowser(request))
                session.setAttribute("ie10", userService.isIe10())
                session.setAttribute("isIE", userService.isIe(request))
                session.setAttribute("wrongIE", (userService.isIe10() || userService.isAtLeastIE11(request)))
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in setting valid browser to session: ${e}")
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
