package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class AnalysisParameterSetting implements Serializable {
    static mapWith = "mongo"

    String queryId
    String sectionId
    String questionId
    String resourceImpactFactor

    @Override
    public String toString() {
        return "QueryId: ${queryId}, sectionId: ${sectionId}, questionId: ${questionId}, resourceImpactFactor: ${resourceImpactFactor}"
    }
}
