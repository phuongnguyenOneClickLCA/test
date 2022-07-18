package com.bionova.optimi.calculation.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

@JsonIgnoreProperties(ignoreUnknown = true)
class CalculationTotalResultDto {
    String indicatorId
    BigDecimal displayResult
    Map<String, BigDecimal> totalByCalculationRule
    Map<String, BigDecimal> denominatorValues
    Map<String, Map<String, BigDecimal>> monthlyDenominatorValues
    Map<String, Map<String, BigDecimal>> monthlyTotalsPerCalculationRule
    Map<String, Map<String, BigDecimal>> quarterlyTotalsPerCalculationRule
    Map<String, Map<String, Object>> dataValidity

    @Override
    String toString() {
        return "CalculationTotalResultDto{" +
                "indicatorId='" + indicatorId + '\'' +
                ", displayResult=" + displayResult +
                ", totalByCalculationRule=" + totalByCalculationRule +
                '}'
    }

    @Override
    @CompileStatic
    boolean equals(Object o) {
        if (this == o) return true
        if (!(o instanceof CalculationTotalResultDto)) return false
        CalculationTotalResultDto that = (CalculationTotalResultDto) o
        return Objects.equals(indicatorId, that.indicatorId) && Objects.equals(displayResult, that.displayResult) &&
                Objects.equals(totalByCalculationRule, that.totalByCalculationRule) &&
                Objects.equals(denominatorValues, that.denominatorValues) &&
                Objects.equals(monthlyDenominatorValues, that.monthlyDenominatorValues) &&
                Objects.equals(monthlyTotalsPerCalculationRule, that.monthlyTotalsPerCalculationRule) &&
                Objects.equals(quarterlyTotalsPerCalculationRule, that.quarterlyTotalsPerCalculationRule)
    }

    @Override
    @CompileStatic
    int hashCode() {
        return Objects.hash(indicatorId, displayResult, totalByCalculationRule, denominatorValues,
                monthlyDenominatorValues, monthlyTotalsPerCalculationRule, quarterlyTotalsPerCalculationRule)
    }
}
