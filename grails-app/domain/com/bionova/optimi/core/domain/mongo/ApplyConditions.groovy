package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ApplyConditions implements Serializable {
    static mapWith = "mongo"
    Boolean dataPresent
    Map<String, List<String>> data
}
