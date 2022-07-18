package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class QuestionTableFormat {
    static mapWith = "mongo"
    List<String> questionIds
    List<String> columnWidths
}
