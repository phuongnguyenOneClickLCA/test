package com.bionova.optimi.calculation.dto.embedded

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder([
        "scopeId",
        "frameType",
        "projectType",
        "lcaCheckerList"
])
class ScopeToSaveDto {
    String scopeId
    String frameType
    String projectType
    List<String> lcaCheckerList
}
