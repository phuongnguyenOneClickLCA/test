package com.bionova.optimi.ui.rset

import groovy.transform.CompileStatic

@CompileStatic
class DesignRowRsetTable {
    String designId
    String designName
    List<RsetBatiment> rsetBatiments
    List<ZoneForRender> zonesForRender
    Boolean isSelectedForParcelle

    DesignRowRsetTable() {
        this.rsetBatiments = []
    }

    static class RsetBatiment {
        String name
        Integer index
        Boolean isSelected
        Boolean isDisabled
    }
}
