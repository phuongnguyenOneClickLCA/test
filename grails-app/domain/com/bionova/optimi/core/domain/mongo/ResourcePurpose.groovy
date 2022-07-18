package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourcePurpose {
    static mapWith = "mongo"

    String attribute

    Map<String, List<Object>> filterConditions
    List<ResourceFilterCriteria> validityCondititions

    static embedded = [
            'validityCondititions'
    ]
}
