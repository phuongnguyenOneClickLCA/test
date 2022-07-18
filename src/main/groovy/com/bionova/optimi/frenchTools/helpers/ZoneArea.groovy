package com.bionova.optimi.frenchTools.helpers

import groovy.transform.CompileStatic

/**
 * An object that holds the area of each zone and the calculated shared ratio
 */
@CompileStatic
class ZoneArea {
    String zoneId
    Double sref
    Double nocc
    // zonePerBatimentRatio is calculated by { zone area / batiment area }
    Double zonePerBatimentRatio
}
