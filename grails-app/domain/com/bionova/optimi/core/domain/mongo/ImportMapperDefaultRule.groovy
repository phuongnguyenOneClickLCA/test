package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ImportMapperDefaultRule {
    static mapWith = "mongo"

    String classMatch
    Map<String, String> defaults

    static constraints = {
    }
}
