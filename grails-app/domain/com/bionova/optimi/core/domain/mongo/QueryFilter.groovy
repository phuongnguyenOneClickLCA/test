package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class QueryFilter {
    static mapWith = "mongo"
    @Translatable
    Map<String, String> name
    String resourceAttribute
    String filterWidth
    Map<String, String> countryCode
    String userClass
    Map<String, Map<String, String>> matchFilterValues

    static transients = []

}
