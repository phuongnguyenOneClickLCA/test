package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorXmlMapper {

    static mapWith = "mongo"

    ValueReference target
    List<String> source
    Boolean sumAsOneIfMultiple
    Boolean residential
    Double quantityMultiplier
    Boolean findOnlyOne

    // This is a fast hack for the patch, should make this configurable next time if we need more condition like this
    Boolean PV

    static embedded = ['target']
}
