package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ClassificationQuestionTranslation {
    static mapWith = "mongo"
    String additionalQuestionId
    List<ImportMapperTranslation> translations

    static embedded = ['translations']
}
