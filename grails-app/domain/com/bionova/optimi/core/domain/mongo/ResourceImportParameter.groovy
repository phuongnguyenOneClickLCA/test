package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 *
 * @author Pasi-Markus Mäkelä
 *
 */
@GrailsCompileStatic
class ResourceImportParameter implements Serializable {
    static mapWith = "mongo"
    String queryId
    Map<String, String> defaultImportSectionAndQuestionId
    List<String> resourceIdImportHeadingMapping
    List<String> quantityImportHeadingMapping
    List<String> resourceIdResourceMapping
    @Translatable
    Map<String, String> helpText

    static transients = []

}
