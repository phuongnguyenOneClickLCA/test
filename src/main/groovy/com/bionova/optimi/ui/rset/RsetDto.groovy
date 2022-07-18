package com.bionova.optimi.ui.rset

import groovy.transform.CompileStatic

@CompileStatic
class RsetDto {

    // questionId of the question that RSET file was uploaded to
    String questionId
    List<BatimentDto> batimentCollection

    RsetDto() {
        this.batimentCollection = []
    }
}
