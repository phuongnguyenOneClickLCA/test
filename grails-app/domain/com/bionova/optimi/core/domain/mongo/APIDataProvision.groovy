package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class APIDataProvision {
    static mapWith = "mongo"
    String dataCategory
    Boolean authRequired
    Map<String, List<Object>> dataFilters
}
