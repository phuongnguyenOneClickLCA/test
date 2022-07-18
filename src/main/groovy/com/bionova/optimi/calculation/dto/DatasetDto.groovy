package com.bionova.optimi.calculation.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import groovy.transform.CompileStatic

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder([
        "manualId",
        "questionId",
        "queryId",
        "sectionId",
        "resourceId",
        "disabledIndicators",
        "profileId",
        "answerIds",
        "projectLevelDataset",
        "appliedNmdScaling"
])
class DatasetDto {

    String manualId
    String questionId
    String queryId
    String sectionId
    String resourceId
    String profileId
    BigDecimal quantity
    BigDecimal points

    Map<String, Object> additionalQuestionAnswers
    String userGivenUnit
    String defaultDatasetOriginalManualId

    // These are new attributes for new calculation
    BigDecimal calculatedQuantity
    Map<String, BigDecimal> calculatedMonthlyQuantity
    Map<String, BigDecimal> calculatedQuarterlyQuantity
    String calculatedUnit
    BigDecimal calculatedMass
    List<String> resultCategoryIds
    List<String> resultCategoryIdsWithStages

    Boolean mapToResource
    String mapToResourceType

    List<Integer> occurrencePeriods
    String parentConstructionId
    String uniqueConstructionIdentifier

    String preResultFormula

    @JsonIgnore
    Map<String, Object> monthlyAnswers
    @JsonIgnore
    Map<String, Object> quarterlyAnswers
    BigDecimal result   // result  reflects outcome so ambiguous use of fields can be avoided

    Boolean projectLevelDataset
    List<String> answerIds
    String productDescription

    @Override
    @CompileStatic
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false
        DatasetDto that = (DatasetDto) o
        return Objects.equals(manualId, that.manualId) && Objects.equals(questionId, that.questionId) &&
                Objects.equals(queryId, that.queryId) && Objects.equals(sectionId, that.sectionId) &&
                Objects.equals(resourceId, that.resourceId) && Objects.equals(profileId, that.profileId) &&
                Objects.equals(quantity, that.quantity) && Objects.equals(points, that.points) &&
                Objects.equals(userGivenUnit, that.userGivenUnit) &&
                Objects.equals(defaultDatasetOriginalManualId, that.defaultDatasetOriginalManualId) &&
                Objects.equals(calculatedQuantity, that.calculatedQuantity) && Objects.equals(calculatedUnit, that.calculatedUnit) &&
                Objects.equals(calculatedMass, that.calculatedMass) && Objects.equals(mapToResource, that.mapToResource) &&
                Objects.equals(mapToResourceType, that.mapToResourceType) && Objects.equals(parentConstructionId, that.parentConstructionId) &&
                Objects.equals(uniqueConstructionIdentifier, that.uniqueConstructionIdentifier) &&
                Objects.equals(preResultFormula, that.preResultFormula) && Objects.equals(result, that.result) &&
                Objects.equals(projectLevelDataset, that.projectLevelDataset) && Objects.equals(productDescription, that.productDescription)
    }

    @Override
    @CompileStatic
    int hashCode() {
        return Objects.hash(manualId, questionId, queryId, sectionId, resourceId, profileId, quantity, points, userGivenUnit,
                defaultDatasetOriginalManualId, calculatedQuantity, calculatedUnit, calculatedMass, mapToResource, mapToResourceType,
                parentConstructionId, uniqueConstructionIdentifier, preResultFormula, result,
                projectLevelDataset, productDescription)
    }
}
