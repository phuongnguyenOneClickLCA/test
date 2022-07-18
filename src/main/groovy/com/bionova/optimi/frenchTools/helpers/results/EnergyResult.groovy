package com.bionova.optimi.frenchTools.helpers.results

import groovy.transform.CompileStatic

@CompileStatic
class EnergyResult extends AbstractFrenchResult {

    Integer energyUseRef

    EnergyResult(Integer energyUseRef) {
        super()
        this.energyUseRef = energyUseRef
    }
}
