package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class HelpSettings {
    static mapWith = "mongo"
    Integer width
    Boolean expanded
    String bgColour
    String textColour
}
