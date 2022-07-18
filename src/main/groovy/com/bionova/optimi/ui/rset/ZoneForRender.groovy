package com.bionova.optimi.ui.rset

import groovy.transform.CompileStatic

@CompileStatic
class ZoneForRender {

    String zoneId
    String batimentIndexAndDesignId
    List<RsetZone> rsetZones

    ZoneForRender() {
        this.rsetZones = []
    }

    static class RsetZone {
        Integer index
        String name
        Boolean isSelected
        Boolean isDisabled
        String dataValue
        String value
    }
}
