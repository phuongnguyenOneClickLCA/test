package com.bionova.optimi.calculation.dto.embedded

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.bson.types.ObjectId

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder([
        "entityClass",
        "deleted",
        "locked",
        "superLocked",
        "superVerified",
        "operatingPeriodAndName",
        "projectLevelQueries",
        "queryAndLinkedIndicator",
        "enableAsUserTestData",
        "chosenDesign"
])
class ChildEntityDto {
    @JsonIgnore
    ObjectId entityId
    @JsonIgnore
    ObjectId parentId

    String entityClass
    Boolean deleted
    Boolean locked
    Boolean superLocked
    Boolean superVerified
    String operatingPeriodAndName
    List<String> projectLevelQueries
    Map<String, String> queryAndLinkedIndicator
    Boolean enableAsUserTestData
    Boolean chosenDesign
}
