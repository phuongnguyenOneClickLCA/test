package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Nikhil Balan
 */
@GrailsCompileStatic
class AdditionalQuestionMultipliersWithConditions implements Serializable {
    static mapWith = "mongo"
    String additionalQuestionMultipliers
    ValueReference conditions

    static embedded = ['conditions']

    static constraints = {
        additionalQuestionMultipliers nullable: true
        conditions nullable: true
    }
}
