package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourceFilterCriteria {
    static mapWith = "mongo"

    String attribute
    List<Object> values
    String resolve // all, any, none, exists, greaterThan, lessThan
    String applyCondition
}
