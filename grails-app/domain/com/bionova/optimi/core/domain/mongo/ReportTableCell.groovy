package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ReportTableCell implements Serializable {
    static mapWith = "mongo"
    @Translatable
    Map<String, String> text
    String textFormatting // “text”, “bold”, “columnHeading”
    Integer colspan
    ValueReference valueReference
    List valueReferences
    String textColor

    static embedded = ['valueReference']

    static transients = ['valueReferenceObjects']

    List<ValueReference> getValueReferenceObjects() {
        List<ValueReference> valueReferencesList = []

        if (valueReferences) {
            valueReferences.each {
                valueReferencesList.add(it as ValueReference)
            }
        }
        return valueReferencesList
    }
}
