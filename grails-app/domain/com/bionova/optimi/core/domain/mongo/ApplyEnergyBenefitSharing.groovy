package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ApplyEnergyBenefitSharing implements Serializable {
    static mapWith = "mongo"
    ValueReference heatFromCHP
    ValueReference electricityFromCHP
    ValueReference separateHeatProdEff
    ValueReference separateElectricityProdEff
    String applyImpactsFor

    static embedded = [
            'heatFromCHP',
            'electricityFromCHP',
            'separateHeatProdEff',
            'separateElectricityProdEff'
    ]
}
