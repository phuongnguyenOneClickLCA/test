package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResultFormatting implements Serializable {
    static mapWith = "mongo"
    @Translatable
    Map<String, String> unit
    Boolean showDecimals
    Integer significantDigits
    Integer minimumZeros
    Integer minimumDecimals
    Integer useScientificNumbers
    String rounding

    static transients = ['localizedUnit']

    static constraints = {
        unit nullable: true
        showDecimals nullable: true
        significantDigits nullable: true
        minimumZeros nullable: true
        minimumDecimals nullable: true
        useScientificNumbers nullable: true
        rounding nullable: true
    }

}
