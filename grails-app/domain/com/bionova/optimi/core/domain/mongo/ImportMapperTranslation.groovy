package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperTranslation {
    static mapWith = "mongo"
    String applyValue
    List<String> matchingValues
}
