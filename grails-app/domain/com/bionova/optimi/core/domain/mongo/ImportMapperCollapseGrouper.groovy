package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperCollapseGrouper {
    static mapWith = "mongo"
    String grouperId
    Map<String, String> collapseHeadingAndValue
    List<String> datasetIds
    Integer datasetAmount

    Boolean isDescriptive
}
