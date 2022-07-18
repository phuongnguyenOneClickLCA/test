package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorResultMonthly implements Serializable {
    static mapWith = "mongo"
    String calculationRule
    String resultCategory
    String month
    Double result
}
