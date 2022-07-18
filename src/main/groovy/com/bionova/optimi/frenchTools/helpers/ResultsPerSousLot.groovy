package com.bionova.optimi.frenchTools.helpers

import com.bionova.optimi.core.domain.mongo.CalculationResult
import groovy.transform.CompileStatic

@CompileStatic
class ResultsPerSousLot {

    String sousLotId
    // results for this souslot (same sousLotId) and categorized in the map
    // < calculationRuleId, list of results>
    Map<String, List<CalculationResult>> resultsPerRule

    ResultsPerSousLot(String sousLotId) {
        this.sousLotId = sousLotId
        this.resultsPerRule = [:]
    }
}
