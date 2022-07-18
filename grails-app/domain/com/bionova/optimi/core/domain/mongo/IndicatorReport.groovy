package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorReport implements Serializable, Validateable {
    static mapWith = "mongo"
    Boolean mainLevelReport
    Boolean showEntityName
    @Translatable
    Map<String, String> mainLevelButtonTitle
    String iterateOverEach
    String backgroundImage
    String appliedStyleSheet
    List reportItems

    static constraints = {
        appliedStyleSheet nullable: true
        backgroundImage nullable: true
        iterateOverEach nullable: true
        mainLevelReport nullable: true
        showEntityName nullable: true
    }

    static transients = []

    static embedded = ['reportItems']
}
