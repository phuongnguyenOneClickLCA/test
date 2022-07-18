package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperPresetFilter {
    static mapWith = "mongo"
    String presetFilterId
    @Translatable
    Map<String, String> name
    List<String> includeQuestionMappingValues
    List<String> skipQuestionMappingValues
    List<String> defaultForIndicators

    static constraints = {
        presetFilterId nullable: false
        name nullable: false
        includeQuestionMappingValues nullable: true
        skipQuestionMappingValues nullable: true
        defaultForIndicators nullable: true
    }

    static transients = []

}
