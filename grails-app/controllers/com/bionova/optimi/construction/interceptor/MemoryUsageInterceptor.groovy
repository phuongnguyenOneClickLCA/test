/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.interceptor

import grails.util.Environment
import groovy.transform.CompileStatic

import java.text.NumberFormat

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class MemoryUsageInterceptor {
    MemoryUsageInterceptor() {
        matchAll().excludes(controller:'error')
    }

    boolean before() {
        StringBuffer sbuf = new StringBuffer()

        Runtime r = Runtime.getRuntime()
        double free =  r.freeMemory() / 1024d / 1024d
        double max =  r.maxMemory() / 1024d / 1024d
        double total =  r.totalMemory() / 1024d / 1024d
        double totalFree = max - total + free

        NumberFormat nf = NumberFormat.getNumberInstance()
        nf.setMinimumIntegerDigits(3)
        nf.setMaximumIntegerDigits(4)
        nf.setMinimumFractionDigits(3)
        nf.setMaximumFractionDigits(3)

        if (Environment.current == Environment.PRODUCTION && totalFree < 512d) {
            log.error("FREE MEMORY IS UNDER 1024 MB: ${totalFree} MB")
        }
        sbuf.append("[Free left: " + nf.format(free) + " MB; ")
        sbuf.append("Total used: " + nf.format(total) + " MB; ")
        sbuf.append("Max: " + nf.format(max) + " MB] ")
        log.debug("Memory usage: " + sbuf.toString())
        log.debug("$controllerName >>> $actionName")

        if (session?.getAttribute("dryRunResources")) {
            if (flash?.containsKey("errorAlert")) {
                flash.errorAlert = "You have dry run resource in memory. Please clear them<br /><br />" + flash.get("errorAlert")
            } else {
                flash.errorAlert = "You have dry run resource in memory. Please clear them!"
            }
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
