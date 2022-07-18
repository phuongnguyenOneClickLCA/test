/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class IndicatorResult implements Serializable {
    static mapWith = "mongo"
    String indicatorId
    Map<String, Map<String, Double>> results
    Map<String, Map<String, Integer>> calculationStatus
    Map<String, List<Dataset>> datasetsByResultCategory
    Map<String, Integer> datasetAmountByResultCategory
    List<IndicatorResultMonthly> resultsMonthly
    List<IndicatorResultQuarterly> resultsQuarterly
    Boolean resultComplete

    static embedded = ['resultsMonthly', 'resultsQuarterly']

    static transients = [
            "createCopy",
            "datasetsByResultCategory"
    ]

    static constraints = {
        indicatorId nullable: false
        results nullable: true
        datasetsByResultCategory nullable: true
        resultComplete nullable: true
        datasetAmountByResultCategory nullable: true
        resultsMonthly nullable: true
        resultsQuarterly nullable: true
    }

    IndicatorResult createCopy() {
        IndicatorResult copy = new IndicatorResult()
        copy.indicatorId = this.indicatorId

        if (this.results) {
            copy.results = new LinkedHashMap<String, Map<String, Double>>(this.results)
        }

        if (this.datasetsByResultCategory) {
            copy.datasetsByResultCategory = new LinkedHashMap<String, List<Dataset>>(this.datasetsByResultCategory)
        }

        if (this.datasetAmountByResultCategory) {
            copy.datasetAmountByResultCategory = new LinkedHashMap<String, Integer>(this.datasetAmountByResultCategory)
        }
        copy.resultComplete = this.resultComplete

        if (this.calculationStatus) {
            copy.calculationStatus = new LinkedHashMap<String, Map<String, Integer>>(this.calculationStatus)
        }
        return copy
    }

    /*def getTotalOrAverage(String calculationRule, String dividerCategory = null, ResultCategory scoreByCategory = null) {
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        def resultsByCalculationRule = []
        Double totalByCalculationRule
        List<String> resultCategories = indicator.resolveResultCategories?.findAll({
            (!it.ignoreFromTotals?.contains(calculationRule) || it.ignoreFromTotals == null) && !it.ignoreFromTotals?.isEmpty()
        })?.collect({ it.resultCategory })
        CalculationRule rule = indicator.resolveCalculationRules?.find({ it.calculationRule.equals(calculationRule) })

        if (results?.get(calculationRule)) {
            resultsByCalculationRule.add(results.get(calculationRule))
        }
        boolean invalidTotal = false
        List<String> categoriesWithResult = []

        resultsByCalculationRule?.each { Map resultByCategory ->
            if (scoreByCategory) {
                def value = resultByCategory.get(scoreByCategory.resultCategory)

                if (value != null) {
                    if (!categoriesWithResult.contains(scoreByCategory.resultCategory)) {
                        categoriesWithResult.add(scoreByCategory.resultCategory)
                    }
                    totalByCalculationRule = totalByCalculationRule ? totalByCalculationRule + value : value
                }
            } else {
                resultByCategory?.each { key, value ->
                    if (resultCategories?.contains(key)) {
                        if (value != null) {
                            categoriesWithResult.add(key)
                            totalByCalculationRule = totalByCalculationRule ? totalByCalculationRule + value : value
                        }
                    }
                }
            }
        }

        if (indicator?.showAverageInsteadOfTotal && !invalidTotal) {
            def datasetAmount = 0

            if (dividerCategory) {
                datasetAmount = datasetsByResultCategory?.get(dividerCategory)
            } else {
                datasetAmountByResultCategory?.each { key, value ->
                    if (categoriesWithResult?.contains(key)) {
                        datasetAmount = datasetAmount + value
                    }
                }
            }

            if (datasetAmount && totalByCalculationRule) {
                totalByCalculationRule = totalByCalculationRule / datasetAmount
            }
        }
        return totalByCalculationRule
    }*/

    /*def getResultByRuleAndCategory(String calculationRule, String resultCategory, Boolean useIgnoreFromTotals = Boolean.TRUE) {
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Double totalByCalculationRuleAndCategory
        List<String> resultCategories

        if (useIgnoreFromTotals) {
            resultCategories = indicator.resolveResultCategories?.findAll({
                (!it.ignoreFromTotals?.contains(calculationRule) || it.ignoreFromTotals == null) && !it.ignoreFromTotals?.isEmpty()
            })?.collect({ it.resultCategory })
        } else {
            resultCategories = indicator.resolveResultCategories?.collect({ it.resultCategory })
        }

        if (resultCategories?.contains(resultCategory)) {
            CalculationRule rule = indicator.resolveCalculationRules?.find({
                it.calculationRule.equals(calculationRule)
            })
            Double result = results?.get(calculationRule)?.get(resultCategory)

            if (result != null) {
                totalByCalculationRuleAndCategory = totalByCalculationRuleAndCategory ? totalByCalculationRuleAndCategory + result : result
            }

            if (indicator?.showAverageInsteadOfTotal && totalByCalculationRuleAndCategory) {
                def datasetAmount = datasetsByResultCategory?.get(resultCategory)

                if (datasetAmount && totalByCalculationRuleAndCategory) {
                    totalByCalculationRuleAndCategory = totalByCalculationRuleAndCategory / datasetAmount
                }
            }
        }
        return totalByCalculationRuleAndCategory
    }*/

    Integer getCalculationStatusForWholeRule(String calculationRule) {
        Integer status

        if (calculationRule) {
            Map<String, Integer> statusByCategories = calculationStatus?.get(calculationRule)

            if (statusByCategories) {
                status = statusByCategories.values()?.find({ it != Constants.CalculationStatus.OK.status })

                if (!status) {
                    status = Constants.CalculationStatus.OK.status
                }
            }
        } else {
            calculationStatus?.each { String rule, Map<String, Integer> statusByCategories ->
                if (statusByCategories) {
                    status = statusByCategories.values()?.find({ it != Constants.CalculationStatus.OK.status })

                    if (!status) {
                        status = Constants.CalculationStatus.OK.status
                    }
                }
            }
        }
        return status
    }

    boolean equals(Object o) {
        return o instanceof IndicatorResult && indicatorId == o.indicatorId && results == o.results
    }
}
