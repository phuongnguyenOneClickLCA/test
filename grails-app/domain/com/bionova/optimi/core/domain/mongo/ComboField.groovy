package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Makela
 */
@GrailsCompileStatic
class ComboField implements Serializable {
    static mapWith = "mongo"
    String comboFieldId
    @Translatable
    Map<String, String> comboFieldName
    List<String> content

    static transients = []
}
