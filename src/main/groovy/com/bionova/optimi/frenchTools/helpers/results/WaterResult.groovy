package com.bionova.optimi.frenchTools.helpers.results

import groovy.transform.CompileStatic

@CompileStatic
class WaterResult extends AbstractFrenchResult {
    Integer waterTypeRef

    WaterResult(Integer waterTypeRef) {
        super()
        this.waterTypeRef = waterTypeRef
    }
}
