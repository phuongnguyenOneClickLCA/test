package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class PreparedDenominator implements Serializable, Validateable {
    static mapWith = "mongo"
    String denominatorId
    String denominatorType
    Double overallDenominator
    Double assessmentPeriod
    Map<String, Double> denominators
    Map<String, Map<String, Double>> monthlyDenominators
}
