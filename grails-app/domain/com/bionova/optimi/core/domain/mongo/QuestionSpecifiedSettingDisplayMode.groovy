package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class QuestionSpecifiedSettingDisplayMode {
    static mapWith = "mongo"
    Double specifiedSetting
    @Translatable(alias = "displayText")
    Map<String, String> specifiedDisplayText
    @Translatable
    Map<String, String> hoverOverText
    Boolean setEmptyWhenClicked
    Boolean aligntoRight
    Boolean thousandSeparator

    static transients = []
}
