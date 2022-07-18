package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ImportMapperDescriptiveDataRule {

    static mapWith = "mongo"

    String target
    List<String> mappedHeadings
}
