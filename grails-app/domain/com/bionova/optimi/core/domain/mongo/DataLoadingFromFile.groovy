package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Annie Simon
 */
@GrailsCompileStatic
class DataLoadingFromFile {
    static mapWith = "mongo"
    @Translatable(alias = "name")
    Map<String, String> buttonName
    @Translatable(alias = "loadWarning")
    Map<String, String> loadResourceWarning
    Map<String, String> noMatchesWarning
    Map<String, String> loadResourcesTarget
    Map<String, String> filterParametersList
    String loadingResourcesFile

    static constraints = {
        buttonName nullable: true
        loadResourceWarning nullable: true
        loadResourcesTarget nullable: true
        filterParametersList nullable: true
        loadingResourcesFile nullable: true
        noMatchesWarning nullable: true
    }

    static transients = []
}
