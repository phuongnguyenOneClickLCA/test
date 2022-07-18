package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä.
 */
@GrailsCompileStatic
class IndicatorReportDenominator {
    static mapWith = "mongo"

    ValueReference valueReference
    String type
    Integer decimals

    static embedded = ['valueReference']
}
