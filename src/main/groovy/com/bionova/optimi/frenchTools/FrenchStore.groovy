package com.bionova.optimi.frenchTools

import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.service.Re2020Service
import com.bionova.optimi.frenchTools.helpers.BatimentArea
import com.bionova.optimi.frenchTools.helpers.DatasetGroup
import com.bionova.optimi.frenchTools.helpers.ResultsPerLot
import com.bionova.optimi.frenchTools.helpers.ResultsPerZone
import com.bionova.optimi.frenchTools.helpers.results.BeResult
import com.bionova.optimi.frenchTools.helpers.results.EnergyResult
import com.bionova.optimi.frenchTools.helpers.results.SiteResult
import com.bionova.optimi.frenchTools.helpers.results.WaterResult
import groovy.transform.CompileStatic

import java.text.DecimalFormat

/**
 * An object to temporarily store some values for passing between methods while exporting RSEE
 */
@CompileStatic
class FrenchStore {

    Entity parentEntity
    List<Entity> designs
    Indicator indicator
    List<CalculationRule> calculationRules
    Set<String> calculationRuleIds
    List<ResultCategory> resultCategories
    Set<String> resultCategoryIds

    /*
        + batimentIndexMappings is a Map < entityId : batiment index >. This is used for mapping the batiment to design
        + batimentZoneIndexMappings has Maps within a Map < entityId : < zoneId of design : zone index of batiment >>. This is used for mapping the zones of the batiment to zones of the design
    */
    Map<String, Map<String, Integer>> batimentZoneIndexMappings
    Map<String, Integer> batimentIndexMappings

    DecimalFormat decimalFormat
    Set<String> queryIdsWithZoneDataset
    List<BatimentArea> srefPerDesign
    // <designId, results same design>
    Map<String, List<CalculationResult>> resultsPerDesign
    // results for category "Be"
    Map<String, Double> assessmentValuePerDesign

    List<DatasetGroup> datasetGroups

    // the fields below are cleared after every batiment in loop in sortie projet util
    // <ruleId, results for rule>
    Map<String, List<CalculationResult>> resultsPerRule
    List<ResultsPerLot> resultsPerLot
    List<ResultsPerZone> resultsPerZone
    List<EnergyResult> energyResults
    List<WaterResult> waterResults
    List<SiteResult> siteResults
    List<BeResult> beResults

    Parcelle parcelle

    FrenchStore() {
        DecimalFormat decimalFormat = new DecimalFormat()
        decimalFormat.setParseBigDecimal(true)
        this.decimalFormat = decimalFormat
        this.assessmentValuePerDesign = new HashMap<>()
        this.parcelle = new Parcelle()
        this.datasetGroups = new ArrayList<>()
        this.srefPerDesign = new ArrayList<>()
        this.resultsPerRule = new HashMap<>()
        this.resultsPerLot = new ArrayList<>()
        this.resultsPerZone = new ArrayList<>()
        this.energyResults = new ArrayList<>()
        this.waterResults = new ArrayList<>()
        this.siteResults = new ArrayList<>()
        this.beResults = new ArrayList<>()
    }

    void setCalculationRule(List<CalculationRule> calculationRules) {
        this.calculationRules = calculationRules
        this.calculationRuleIds = calculationRules?.collect { it.calculationRuleId } as Set<String>
    }

    void setResultCategories(List<ResultCategory> resultCats) {
        this.resultCategories = resultCats
        this.resultCategoryIds = resultCats?.collect { it.resultCategoryId } as Set<String>
    }

    void setIndicator(Indicator ind) {
        this.indicator = ind
    }

    void setDesigns(List<Entity> designs) {
        this.designs = designs
    }

    void clearResults() {
        this.resultsPerRule = new HashMap<>()
        this.resultsPerLot = new ArrayList<>()
        this.resultsPerZone = new ArrayList<>()
        this.energyResults = new ArrayList<>()
        this.waterResults = new ArrayList<>()
        this.siteResults = new ArrayList<>()
        this.beResults = new ArrayList<>()
    }

    static class Parcelle {
        // only export parcelle from design that is selected for export from mapping
        String designId
        Integer surfaceParcelle
        Integer surfaceArrosee
        Integer surfaceVeg
        Integer surfaceImper

        /**
         * parcelle can be a stand alone design. It means that this design selected for exporting parcelle does not export batiment
         * @see Re2020Service#isStandAloneParcelle
         */
        Boolean isStandAlone
    }
}
