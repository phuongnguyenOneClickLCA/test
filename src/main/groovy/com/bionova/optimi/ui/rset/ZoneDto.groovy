package com.bionova.optimi.ui.rset

import groovy.transform.CompileStatic

@CompileStatic
class ZoneDto {
    Integer index
    String name
    Map<String, List<RSEnvDatasetDto>> impactGroups
}
