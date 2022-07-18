/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor

import com.bionova.optimi.construction.Constants.SessionAttribute
import grails.util.Holders
import org.grails.web.util.WebUtils

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class CheckRefererInterceptor {

    CheckRefererInterceptor() {
        matchAll().excludes(controller:'error')
    }

    boolean before() {
        String reqUrl = request.getRequestURL().toString()
        String queryString = request.getQueryString()

        if (queryString != null) {
            reqUrl += "?" + queryString
        }
        reqUrl = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS").format(new Date()) + ": " + reqUrl
        synchronized (WebUtils.getSessionMutex(session)) {
            List<String> requestUrls = session?.getAttribute(SessionAttribute.REQUEST_URLS.toString()) as List
            if (requestUrls && requestUrls.size() == 10) {
                try {
                    List<String> requestUrlsTemp = new ArrayList<String>()
                    for (int i = 0; i < 9; i++) {
                        requestUrlsTemp.add(i, (String) requestUrls.get(i + 1))
                    }
                    requestUrls = requestUrlsTemp
                    requestUrls.add(9, reqUrl)
                } catch (Exception e) {
                    log.error("An error while manipulating requestUrls: ", e)
                    requestUrls = new ArrayList<String>()
                    requestUrls.add(reqUrl)
                }
            } else if (requestUrls && requestUrls.size() < 10) {
                requestUrls.add(reqUrl)
            } else {
                requestUrls = new ArrayList<String>()
                requestUrls.add(reqUrl)
            }
            session?.setAttribute(SessionAttribute.REQUEST_URLS.toString(), requestUrls)
        }

        def config = Holders.config
        if (config?.grails?.serverURL) {
            def validRefererPrefix = "^${config.grails.serverURL}".replace("http", "https?")
            def referer = request.getHeader('Referer')
            return referer && referer =~ validRefererPrefix
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
