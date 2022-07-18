package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class MultiplierWithConditions implements Serializable {
    static mapWith = "mongo"
    String resourceAttribute
    List<ValueReference> conditions

    static embedded = ['conditions']

    static constraints = {
        resourceAttribute nullable: true
        conditions nullable: true
    }
}
