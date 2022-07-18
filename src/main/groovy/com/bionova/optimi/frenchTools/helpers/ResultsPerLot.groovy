package com.bionova.optimi.frenchTools.helpers

import com.bionova.optimi.core.domain.mongo.CalculationResult
import groovy.transform.CompileStatic

@CompileStatic
class ResultsPerLot {

    String lotId
    // results for this lot (same lotId) and categorized in the map
    // < calculationRuleId, list of results>
    Map<String, List<CalculationResult>> resultsPerRule
    // list of grouped objects for storing results per souslot that belongs to the current lot
    List<ResultsPerSousLot> resultsPerSousLots

    ResultsPerLot(String lotId) {
        this.lotId = lotId
        this.resultsPerRule = [:]
        this.resultsPerSousLots = []
    }
}
