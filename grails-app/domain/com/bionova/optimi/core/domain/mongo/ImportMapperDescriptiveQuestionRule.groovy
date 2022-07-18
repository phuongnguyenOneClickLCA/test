package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ImportMapperDescriptiveQuestionRule {

    static mapWith = "mongo"

    Map<String, String> target
    List<String> matchingValues
    Integer maxLengthTrim

    void setMaxLengthTrim(Integer maxLengthTrim) {
        this.maxLengthTrim = maxLengthTrim ?: 100
    }
}
