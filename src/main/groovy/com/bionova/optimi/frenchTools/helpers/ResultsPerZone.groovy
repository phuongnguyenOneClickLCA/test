package com.bionova.optimi.frenchTools.helpers

import com.bionova.optimi.core.domain.mongo.CalculationResult
import groovy.transform.CompileStatic

@CompileStatic
class ResultsPerZone {
    String zoneId
    // results for this zone (same zoneId) and categorized in the map
    // < calculationRuleId, list of results>
    Map<String, List<CalculationResult>> resultsPerRule
    // list of grouped objects for storing results per lot that belongs to the current zone
    List<ResultsPerLot> resultsPerLots

    ResultsPerZone(String zoneId) {
        this.zoneId = zoneId
        this.resultsPerRule = [:]
        this.resultsPerLots = []
    }
}
