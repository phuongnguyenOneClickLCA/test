package com.bionova.optimi.ui.rset

import groovy.transform.CompileStatic

@CompileStatic
class BatimentDto {
    Integer index
    String name
    List<ZoneDto> zoneCollection

    BatimentDto() {
        zoneCollection = []
    }
}
