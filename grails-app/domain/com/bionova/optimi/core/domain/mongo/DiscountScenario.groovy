package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Annie Simon
 */
@GrailsCompileStatic
class DiscountScenario implements Serializable {
    static mapWith = "mongo"
    String discountScenarioId
    Map<String, String> discountScenarioName
    Map<String, String> discountScenarioDescription
    Map<String, Double> scenarioDataSeries
}
