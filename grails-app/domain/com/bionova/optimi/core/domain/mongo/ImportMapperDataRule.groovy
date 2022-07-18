package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperDataRule {
    static mapWith = "mongo"
    String target
    List<String> mappedHeadings
    List<String> resourceMappingFields
    List<String> discardRowsWithValues
}
