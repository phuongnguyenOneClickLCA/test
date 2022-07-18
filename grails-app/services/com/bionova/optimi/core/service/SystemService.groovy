package com.bionova.optimi.core.service

import java.text.NumberFormat

/**
 *
 */
class SystemService {
    FlashService flashService

    def logMemoryUsage() {
        Runtime r = Runtime.getRuntime()
        double free =  r.freeMemory() / 1024d / 1024d
        double max =  r.maxMemory() / 1024d / 1024d
        double total =  r.totalMemory() / 1024d / 1024d

        NumberFormat nf = NumberFormat.getNumberInstance()
        nf.setMinimumIntegerDigits(3)
        nf.setMaximumIntegerDigits(4)
        nf.setMinimumFractionDigits(3)
        nf.setMaximumFractionDigits(3)

        StringBuffer sbuf = new StringBuffer()
        sbuf.append("[Free: " + nf.format(free) + " MB; ")
        sbuf.append("Total: " + nf.format(total) + " MB; ")
        sbuf.append("Max: " + nf.format(max) + " MB] ")
        log.debug("Memory usage: " + sbuf.toString())
        flashService.setFadeInfoAlert("Memory usage: " + sbuf.toString(), true)
    }
}
