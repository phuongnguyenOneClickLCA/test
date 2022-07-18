package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ApplicationResultFormatting {
    String name
    ResultFormatting resultFormatting

    static embedded = ["resultFormatting"]
}
