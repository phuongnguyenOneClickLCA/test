package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CustomSection implements Serializable {

    @Translatable(alias = "heading")
    Map<String, String> heading
    @Translatable(alias = "subHeading")
    Map<String, String> subHeading
    List<MapValues> questionReferences

    static embedded = ['questionReferences']

    static constraints = {
    }
}
