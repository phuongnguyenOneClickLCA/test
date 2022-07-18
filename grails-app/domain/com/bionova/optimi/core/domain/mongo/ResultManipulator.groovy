package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResultManipulator implements Serializable {
    static mapWith = "mongo"
    List multipliers
    List dividers
    Double fixedMultiplier
    Double addToResult
    Boolean abortOnFailure
    Boolean allowFailure
    ValueReference defaultQuantity
    ValueReference reduceByValue
    ValueReference totalsBasedOnWeightedAverage
    Boolean multiplyWithAssessmentPeriod
    Boolean multiplyWSqrtOfAssessmentPeriod

    String divideByRule // Only in calculationRules resultManipulators
    String divideByCategory // Only in resultCategory resultManipulators

    static embedded = ['defaultQuantity', 'reduceByValue']

    static constraints = {
        multipliers nullable: true
        dividers nullable: true
        totalsBasedOnWeightedAverage nullable: true
        fixedMultiplier nullable: true
        addToResult nullable: true
        abortOnFailure nullable: true
        allowFailure nullable: true
        defaultQuantity nullable: true
        reduceByValue nullable: true
        multiplyWithAssessmentPeriod nullable: true
        multiplyWSqrtOfAssessmentPeriod nullable: true
        divideByRule nullable: true
        divideByCategory nullable: true
    }

    @Override
    public String toString() {
        return "\nResultManipulator{" +
                ",\n id=" + id +
                ",\n version=" + version +
                ",\n multipliers=" + multipliers +
                ",\n dividers=" + dividers +
                ",\n fixedMultiplier=" + fixedMultiplier +
                ",\n addToResult=" + addToResult +
                ",\n abortOnFailure=" + abortOnFailure +
                ",\n allowFailure=" + allowFailure +
                ",\n defaultQuantity=" + defaultQuantity +
                ",\n reduceByValue=" + reduceByValue +
                ",\n totalsBasedOnWeightedAverage=" + totalsBasedOnWeightedAverage +
                ",\n multiplyWithAssessmentPeriod=" + multiplyWithAssessmentPeriod +
                ",\n multiplyWSqrtOfAssessmentPeriod=" + multiplyWSqrtOfAssessmentPeriod +
                ",\n divideByRule='" + divideByRule + '\'' +
                ",\n divideByCategory='" + divideByCategory + '\'' +
                '}';
    }
}
