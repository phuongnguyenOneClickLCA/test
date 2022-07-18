package com.bionova.optimi.frenchTools.helpers.results

import groovy.transform.CompileStatic

@CompileStatic
class SiteResult extends AbstractFrenchResult {

    Integer categoryRef

    SiteResult(Integer categoryRef) {
        super()
        this.categoryRef = categoryRef
    }
}
