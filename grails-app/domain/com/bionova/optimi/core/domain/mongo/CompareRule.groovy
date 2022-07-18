package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class CompareRule {
    static mapWith = "mongo"

    String calculationRuleId
    Map<String, Double> pointValue
    Double fixedMultiplier
}
