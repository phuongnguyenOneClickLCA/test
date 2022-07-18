package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.helpers.ResultsPerLot
import com.bionova.optimi.frenchTools.helpers.ResultsPerSousLot
import com.bionova.optimi.frenchTools.re2020.SortieProjetUtilRe2020
import com.bionova.optimi.xml.re2020RSEnv.TCoefModIcconstruction
import com.bionova.optimi.xml.re2020RSEnv.TCoefModIcenergie
import spock.lang.Specification

class SortieProjetUtilRe2020Spec extends Specification {

    private SortieProjetUtilRe2020 sortieProjetUtilRe2020
    private CalculationResultService calculationResultService
    private DatasetService datasetService

    def setup() {
        sortieProjetUtilRe2020 = new SortieProjetUtilRe2020()
        calculationResultService = new CalculationResultService()
        datasetService = new DatasetService()
        sortieProjetUtilRe2020.calculationResultService = calculationResultService
        sortieProjetUtilRe2020.datasetService = datasetService
    }

    def cleanup() {
    }

    void "check addResultToMap"() {
        given:
        Map<String, BigDecimal> resultMap = [:]
        String refCat1 = 'A1'
        Double result1 = 1d
        String refCat2 = 'A2'
        Double result2 = 2d

        when:
        3.times {
            sortieProjetUtilRe2020.addResultToMap(resultMap, refCat1, result1)
            sortieProjetUtilRe2020.addResultToMap(resultMap, refCat2, result2)
        }

        then:
        resultMap.get(refCat1) == 3
        resultMap.get(refCat2) == 6
    }

    void "setResultsPerLot"() {
        given:
        Map<String, Double> resultPerLot = [
                '1': 1.0,
                '2': 2.0,
        ]
        String testRule = 'testRule'
        CalculationResult originalResult = new CalculationResult(resultCategoryId: 'testCat', calculationRuleId: testRule)


        when: "started with an empty list"
        List<ResultsPerLot> empty = []
        sortieProjetUtilRe2020.setResultsPerLot(resultPerLot, empty, originalResult, testRule)

        then: "2 objects added, with each same results as in the resultPerLot map"
        empty.size() == 2
        empty.each {
            assert it.resultsPerRule.get(testRule)?.size() == 1
            if (it.lotId == '1') {
                assert it.resultsPerRule.get(testRule)[0].result == 1
            } else if (it.lotId == '2') {
                assert it.resultsPerRule.get(testRule)[0].result == 2
            }
        }


        when: "started with list with 1 existing ResultsPerLot obj"
        ResultsPerLot lot1 = new ResultsPerLot('1')
        Double existingResult = 999
        lot1.resultsPerRule[testRule] = [new CalculationResult(result: existingResult)]
        List<ResultsPerLot> notEmpty = [lot1]
        sortieProjetUtilRe2020.setResultsPerLot(resultPerLot, notEmpty, originalResult, testRule)

        then: "2 objects added, lot 1 should have 2 results, lot 2 have only one."
        notEmpty.size() == 2
        notEmpty.each {
            if (it.lotId == '1') {
                assert it.resultsPerRule.get(testRule)?.size() == 2
                assert it.resultsPerRule.get(testRule)[0].result == existingResult
                assert it.resultsPerRule.get(testRule)[1].result == 1
            } else if (it.lotId == '2') {
                assert it.resultsPerRule.get(testRule)?.size() == 1
                assert it.resultsPerRule.get(testRule)[0].result == 2
            }
        }
    }

    void "setResultsPerLotPerSousLot"() {
        given:
        String testRule = 'testRule'
        CalculationResult originalResult = new CalculationResult(resultCategoryId: 'testCat', calculationRuleId: testRule)
        Map<String, Double> resultPerSousLot = [
                '1.1': 1.0,
                '2.2': 2.0,
        ]
        Map<String, Map<String, Double>> resultPerLotPerSousLot = new HashMap<String, Map<String, Double>>() {
            {
                put('1', resultPerSousLot);
                put('2', resultPerSousLot);
            }
        }

        when: "started with an empty list"
        List<ResultsPerLot> empty = []
        sortieProjetUtilRe2020.setResultsPerLotPerSousLot(resultPerLotPerSousLot, empty, originalResult, testRule)

        then: "2 objects for lots added, each lot has 2 souslots, each souslot has 1 result"
        empty.size() == 2
        empty.each { lot ->
            assert lot.resultsPerRule?.get(testRule) == null
            assert lot.resultsPerSousLots.size() == 2
            lot.resultsPerSousLots.each { souslot ->
                assert souslot.resultsPerRule.get(testRule).size() == 1
            }
        }


        when: "started with a list that has one lot"
        ResultsPerLot lot1 = new ResultsPerLot('1')
        ResultsPerSousLot sousLot11 = new ResultsPerSousLot('1.1')
        CalculationResult dummy = new CalculationResult(result: 999, calculationRuleId: testRule)
        sousLot11.resultsPerRule.put(testRule, [dummy])
        lot1.resultsPerSousLots.add(sousLot11)
        List<ResultsPerLot> notEmpty = [lot1]
        sortieProjetUtilRe2020.setResultsPerLotPerSousLot(resultPerLotPerSousLot, notEmpty, originalResult, testRule)

        then: "1 object for lot added, each lot has 2 souslots, souslot 1.1 for lot 1 has 2 results and all other souslot has 1 results"
        notEmpty.size() == 2
        notEmpty.each { lot ->
            assert lot.resultsPerRule?.get(testRule) == null
            assert lot.resultsPerSousLots.size() == 2
            lot.resultsPerSousLots.each { souslot ->
                if (lot.lotId == '1' && souslot.sousLotId == '1.1') {
                    assert souslot.resultsPerRule.get(testRule).size() == 2
                    souslot.resultsPerRule.get(testRule).each { resultObj ->
                        assert resultObj.result == 999 || resultObj.result == 1
                    }
                } else {
                    assert souslot.resultsPerRule.get(testRule).size() == 1
                }
            }
        }
    }

    void "initCoefModIcconstructionToIndicateurPerfEnvMap"() {
        given:
        Map indicateurPerfEnvMap = [:]
        String key = FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICCONSTRUCTION.attribute


        when: "additionalQuestionAnswers in dataset has 4 values"
        sortieProjetUtilRe2020.initCoefModIcconstructionToIndicateurPerfEnvMap(
                indicateurPerfEnvMap,
                new Dataset(
                        additionalQuestionAnswers: [
                                (FrenchConstants.MIGEO_QUESTIONID)                  : 1,
                                (FrenchConstants.MICOMBLES_QUESTIONID)              : 2,
                                (FrenchConstants.MISURF_QUESTIONID)                 : 3,
                                (FrenchConstants.ICCONSTRUCTION_MAXMOYEN_QUESTIONID): 4
                        ]
                )
        )

        then: "TCoefModIcconstruction is added to map with 4 needed values"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcconstruction
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).migeo == 1
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).micombles == 2
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).misurf == 3
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).icConstructionMaxmoyen == 4


        when: "additionalQuestionAnswers in dataset has 2 values"
        sortieProjetUtilRe2020.initCoefModIcconstructionToIndicateurPerfEnvMap(
                indicateurPerfEnvMap,
                new Dataset(
                        additionalQuestionAnswers: [
                                (FrenchConstants.MIGEO_QUESTIONID)    : 1,
                                (FrenchConstants.MICOMBLES_QUESTIONID): 2,
                                //(FrenchConstants.MISURF_QUESTIONID)                 : 3,
                                //(FrenchConstants.ICCONSTRUCTION_MAXMOYEN_QUESTIONID): 4
                        ]
                )
        )

        then: "TCoefModIcconstruction is added to map with only 2 needed values, 2 missing values are 0"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcconstruction
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).migeo == 1
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).micombles == 2
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).misurf == 0
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).icConstructionMaxmoyen == 0


        when: "no dataset"
        sortieProjetUtilRe2020.initCoefModIcconstructionToIndicateurPerfEnvMap(indicateurPerfEnvMap, null)

        then: "TCoefModIcconstruction is added to map with all values are 0"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcconstruction
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).migeo == 0
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).micombles == 0
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).misurf == 0
        ((TCoefModIcconstruction) indicateurPerfEnvMap.get(key)).icConstructionMaxmoyen == 0
    }

    void "initCoefModIcenergieToIndicateurPerfEnvMap"() {
        given:
        Map indicateurPerfEnvMap = [:]
        String key = FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICENERGIE.attribute


        when: "additionalQuestionAnswers in dataset has 4 values"
        sortieProjetUtilRe2020.initCoefModIcenergieToIndicateurPerfEnvMap(
                indicateurPerfEnvMap,
                new Dataset(
                        additionalQuestionAnswers: [
                                (FrenchConstants.MCGEO_QUESTIONID)             : 1,
                                (FrenchConstants.MCCOMBLES_QUESTIONID)         : 2,
                                (FrenchConstants.MCSURF_MOY_QUESTIONID)        : 3,
                                (FrenchConstants.MCSURF_TOT_QUESTIONID)        : 4,
                                (FrenchConstants.MCCAT_QUESTIONID)             : 5,
                                (FrenchConstants.ICENERGIE_MAXMOYEN_QUESTIONID): 6
                        ]
                )
        )

        then: "TCoefModIcenergie is added to map with 4 needed values"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcenergie
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcgeo == 1
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccombles == 2
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfMoy == 3
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfTot == 4
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccat == 5
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).icEnergieMaxmoyen == 6


        when: "additionalQuestionAnswers in dataset has 2 values"
        sortieProjetUtilRe2020.initCoefModIcenergieToIndicateurPerfEnvMap(
                indicateurPerfEnvMap,
                new Dataset(
                        additionalQuestionAnswers: [
                                (FrenchConstants.MCGEO_QUESTIONID)     : 1,
                                (FrenchConstants.MCCOMBLES_QUESTIONID) : 2,
                                (FrenchConstants.MCSURF_MOY_QUESTIONID): 3,
                                (FrenchConstants.MCSURF_TOT_QUESTIONID): 4,
                                //(FrenchConstants.MCCAT_QUESTIONID)             : 5,
                                //(FrenchConstants.ICENERGIE_MAXMOYEN_QUESTIONID): 6
                        ]
                )
        )

        then: "TCoefModIcenergie is added to map with only 2 needed values, 2 missing values are 0"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcenergie
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcgeo == 1
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccombles == 2
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfMoy == 3
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfTot == 4
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccat == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).icEnergieMaxmoyen == 0

        when: "no dataset"
        sortieProjetUtilRe2020.initCoefModIcenergieToIndicateurPerfEnvMap(indicateurPerfEnvMap, null)

        then: "TCoefModIcenergie is added to map with all values are 0"
        indicateurPerfEnvMap.get(key) instanceof TCoefModIcenergie
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcgeo == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccombles == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfMoy == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mcsurfTot == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).mccat == 0
        ((TCoefModIcenergie) indicateurPerfEnvMap.get(key)).icEnergieMaxmoyen == 0
    }
}
