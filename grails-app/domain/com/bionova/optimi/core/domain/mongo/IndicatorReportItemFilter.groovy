package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorReportItemFilter {
    static mapWith = "mongo"
    String filterParameter
    List<String> acceptValues
}
