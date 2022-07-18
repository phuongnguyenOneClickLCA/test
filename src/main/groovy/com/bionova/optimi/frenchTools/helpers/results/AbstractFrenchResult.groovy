package com.bionova.optimi.frenchTools.helpers.results

import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.frenchTools.helpers.ResultsPerZone

abstract class AbstractFrenchResult {
    Map<String, List<CalculationResult>> resultsPerRule
    List<ResultsPerZone> resultsPerZones

    AbstractFrenchResult() {
        this.resultsPerRule = new HashMap<>()
        this.resultsPerZones = new ArrayList<>()
    }
}
