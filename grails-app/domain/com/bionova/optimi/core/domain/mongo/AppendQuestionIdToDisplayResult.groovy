package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 *
 * @author Pasi-Markus Mäkelä
 *
 */
@GrailsCompileStatic
class AppendQuestionIdToDisplayResult implements Serializable {
    static mapWith = "mongo"
    String queryId
    String sectionId
    String questionId
    Boolean showNumericAnswer

    static constraints = {
        showNumericAnswer nullable: true
    }
}
