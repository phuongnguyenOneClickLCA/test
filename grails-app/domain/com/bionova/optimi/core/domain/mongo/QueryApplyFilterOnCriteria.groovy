package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 *
 * This is used for filtering resources BREEAM queries
 */
@GrailsCompileStatic
class QueryApplyFilterOnCriteria {
    static mapWith = "mongo"
    String name
    @Translatable
    Map<String, String> filterText
    List<String> choices
    Boolean mandatoryFilter

    static transients = []
}
