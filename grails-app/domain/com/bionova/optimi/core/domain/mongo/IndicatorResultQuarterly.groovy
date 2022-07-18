package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorResultQuarterly {
    static mapWith = "mongo"
    String calculationRule
    String resultCategory
    String quarter
    Double result
}
