package com.bionova.optimi.frenchTools.helpers.results

import groovy.transform.CompileStatic

@CompileStatic
class BeResult extends AbstractFrenchResult {
    Integer ref

    BeResult(Integer ref) {
        super()
        this.ref = ref
    }
}
