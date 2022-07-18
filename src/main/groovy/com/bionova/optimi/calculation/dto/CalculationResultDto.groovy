package com.bionova.optimi.calculation.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.bson.types.ObjectId

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder([
        "result",
        "calculationRuleId",
        "resultCategoryId",
        "entityId",
        "indicatorId",
])
class CalculationResultDto {

//    @JsonIgnore
    ObjectId id
    @JsonIgnore
    String entityId
    String indicatorId
    String calculationRuleId
    String resultCategoryId
    BigDecimal result
    BigDecimal discountedResult
    String unit
    String calculatedUnit

    BigDecimal calculatedDiscountFactor

    String calculationResourceId
    String calculationProfileId
    String originalResourceId
    String originalProfileId
    List<String> datasetIds

    String eolProcessingType

    Map<String, BigDecimal> monthlyResults
    Map<String, BigDecimal> quarterlyResults
    Map<String, BigDecimal> resultByDenominator

    BigDecimal resultWithoutOccurrencePeriods
    Map<Integer, BigDecimal> discountAndFractionalMulplierCombined

    Map<String, Map<String, Object>> calculationResultDatasets
    String calculationFormula
    String combineId
    Map<String, Object> datasetAdditionalQuestionAnswers
    Map<String, BigDecimal> resultWithDiscountPerOccurencePeriod

    Boolean applyLocalComps
    Boolean useEolProcessStage
    Boolean calculatedResourceRow

    String localCompsCountryResourceId
    String localCompsCountryEnergyResourceId
    String localCompsCountryEnergyProfileId

    Map<String, BigDecimal> resultPerOccurencePeriod

    Map<String, Map<String, BigDecimal>> monthlyResultByDenominator
}
