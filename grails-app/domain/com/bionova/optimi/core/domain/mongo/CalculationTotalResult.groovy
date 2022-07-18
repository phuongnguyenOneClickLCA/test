package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CalculationTotalResult {
    String indicatorId
    Double displayResult
    Map<String, Double> totalByCalculationRule
    Map<String, Double> denominatorValues // Key is denominator id or resourceId
    Map<String, Map<String, Double>> monthlyDenominatorValues
    Map<String, Map <String, Double>> monthlyTotalsPerCalculationRule
    Map<String, Map <String, Double>> quarterlyTotalsPerCalculationRule
    Map<String, Map<String, Object>> dataValidity

    static mapWith = "mongo"

    static constraints = {
        displayResult nullable: true
        totalByCalculationRule nullable: true
        monthlyTotalsPerCalculationRule nullable: true
        quarterlyTotalsPerCalculationRule nullable: true
        monthlyDenominatorValues nullable: true
        denominatorValues nullable: true
        dataValidity nullable: true
    }
}
