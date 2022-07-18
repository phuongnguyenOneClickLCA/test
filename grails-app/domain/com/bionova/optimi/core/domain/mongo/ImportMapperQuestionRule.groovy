package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperQuestionRule {
    static mapWith = "mongo"
    Map<String, String> target
    List<String> matchingValues
    Boolean defaultValue // Deprecated, default set by entity class (defaultTargetForEntityClass)
    List<String> defaultTargetForEntityClass
}
