package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorExportContent implements Serializable {
    static mapWith = "mongo"
    List<String> calculationRules
    Map<String, List> queries
    Boolean parentEntityDataExport
}
