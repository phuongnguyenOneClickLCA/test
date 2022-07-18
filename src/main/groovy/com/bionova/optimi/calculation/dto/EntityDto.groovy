package com.bionova.optimi.calculation.dto

import com.bionova.optimi.calculation.dto.embedded.LcaCheckerResultDto
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.bionova.optimi.calculation.dto.embedded.ChildEntityDto
import com.bionova.optimi.calculation.dto.embedded.ScopeToSaveDto
import org.bson.types.ObjectId

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder([
        "name",
        "parentName",
        "datasets",
        "queryReady",
        "disabledSections",
        "disabledIndicators",
        "scope",
        "managerIds",
        "defaults",
        "ribaStage",
        "entityClass",
        "queryReadyPerIndicator",
        "childEntities",
        "deleted",
        "commited",
        "master",
        "component",
        "dateCreated",
        "lastUpdated"
])
class EntityDto {
    @JsonIgnore
    ObjectId id
    @JsonIgnore
    ObjectId parentEntityId

    List<CalculationResultDto> tempCalculationResults
    List<CalculationResultDto> calculationResults

    String name
    String parentName
    List<DatasetDto> datasets
    Map<String, Boolean> queryReady
    List<String> disabledSections
    List<String> disabledIndicators
    ScopeToSaveDto scope
    List<String> managerIds
    Map<String, String> defaults
    Integer ribaStage
    String entityClass
    String saveMessage

    Map<String, Map<String, Boolean>> queryReadyPerIndicator
    List<ChildEntityDto> childEntities
    List<CalculationTotalResultDto> calculationTotalResults

    Boolean deleted
    Boolean commited
    Boolean master
    Boolean component

    LcaCheckerResultDto lcaCheckerResult
    List<String> suppressedLcaCheckers
    BigDecimal previousTotal
    List<String> indicatorIds

    @JsonIgnore
    creatorId
    @JsonIgnore
    ObjectId lastUpdaterId

    @JsonIgnore
    Date dateCreated
    @JsonIgnore
    Date lastUpdated

    Boolean resultsOutOfDate
}
