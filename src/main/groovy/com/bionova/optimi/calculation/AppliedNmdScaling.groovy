package com.bionova.optimi.calculation

/**
 * Object to store the numbers when the NMD scaling multiplier was calculated with
 */
class AppliedNmdScaling {
    String quantity // user input
    Double dimension1_mm
    Double dimension2_mm
    Double multiplier
    Double calculatedQuantity // quantity after applying scaling multiplier
}
