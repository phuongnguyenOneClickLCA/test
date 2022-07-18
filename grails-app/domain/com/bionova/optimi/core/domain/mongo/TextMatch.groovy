package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class TextMatch {
    String textMatch
    String comment
    Boolean matchAllVariants

    static mapWith = "mongo"
}
