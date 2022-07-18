package com.bionova.optimi.frenchTools.fec

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.service.CalculationResultService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.fecRSEnv.IndicateursPerformanceCollection
import com.bionova.optimi.xml.fecRSEnv.RSEnv
import com.bionova.optimi.xml.fecRSEnv.TIndicateur
import groovy.transform.CompileStatic

import java.text.DecimalFormat

class SortieProjetUtilFec {
    DatasetService datasetService
    CalculationResultService calculationResultService
    LoggerUtil loggerUtil
    NumberUtil numberUtil
    FlashService flashService

    @CompileStatic
    void setSortieProjetToRSEnv(RSEnv rsEnv, List<CalculationRule> calculationRules, Indicator indicator, List<Entity> designs,
                                Map<String, Integer> batimentIndexMappings, Map<String, Map<String, Integer>> batimentZoneIndexMappings) {
        RSEnv.SortieProjet sortieProjet = new RSEnv.SortieProjet()
        DecimalFormat decimalFormat = new DecimalFormat()
        decimalFormat.setParseBigDecimal(true)

        List<String> resultCategoryIds = ["P", "E", "W", "C"]

        for (Entity design in designs) {
            if (!design) {
                continue
            }

            List<CalculationResult> calculationResults = design.getCalculationResultObjects(indicator?.indicatorId, null, null)
            Integer batimentIndex = batimentIndexMappings.get(design.id.toString())
            RSEnv.SortieProjet.Batiment batiment = new RSEnv.SortieProjet.Batiment()

            batiment.setIndex(batimentIndex)
            setPartDonneesGeneriquesToBatiment(batiment, design)
            FrenchStorage store = setCoefficientsModulateursToBatiment(batiment, calculationResults)

            Map<String, Integer> validZones = batimentZoneIndexMappings.get(design.id.toString())

            // First key == zone index 1, 2..., Second key is resultcategory
            Map<Integer, Map<String, List<CalculationResult>>> resultsPerZonePerCategory = [:]
            Map<Integer, Map<String, List<CalculationResult>>> resultsPerLotPerCategory = [:]
            Map<Integer, Map<String, Map<Integer, List<CalculationResult>>>> resultsPerZonePerCategoryPerLot = [:]

            Map<String, List<CalculationResult>> groupedCalculationResultsByResultCat = [:]
            groupCalculationResults(resultCategoryIds, design, calculationResults, groupedCalculationResultsByResultCat, resultsPerZonePerCategory, resultsPerLotPerCategory, resultsPerZonePerCategoryPerLot)
            setContributeursToBatiment(batiment, groupedCalculationResultsByResultCat, calculationRules, resultsPerLotPerCategory)
            setZonesToBatiment(batiment, design, resultsPerZonePerCategory, validZones, resultCategoryIds, calculationRules, resultsPerZonePerCategoryPerLot, store, decimalFormat, calculationResults)

            IndicateursPerformanceCollection indicateursPerformanceCollection = getPerformanceObject(calculationRules, calculationResults)
            batiment.setIndicateursPerformanceCollection(indicateursPerformanceCollection)

            sortieProjet.getBatiment().add(batiment)
        }
        rsEnv.setSortieProjet(sortieProjet)
    }

    private IndicateursPerformanceCollection getPerformanceObject(List<CalculationRule> calculationRules, List<CalculationResult> calculationResults, Set<Dataset> zoneDataSets = null) {

        IndicateursPerformanceCollection indicateursPerformanceCollection = new IndicateursPerformanceCollection()
        String egesCalcRuleUnit = Constants.EMPTY_STRING
        String gesPCECalcRuleUnit = Constants.EMPTY_STRING
        String carbonValueUnit = Constants.EMPTY_STRING
        String carbonValueUnitForEgesMax = Constants.EMPTY_STRING
        BigDecimal totalEges = 0
        BigDecimal gesPCE = 0

        BigDecimal egesPCEMax1 = 0
        BigDecimal egesPCEMax2 = 0
        BigDecimal egesMax1 = 0
        BigDecimal egesMax2 = 0
        if (calculationResults) {
            if (zoneDataSets) {
                for (CalculationResult calculationResult in calculationResults) {
                    if (FrenchConstants.T.equals(calculationResult.resultCategoryId) && FrenchConstants.GWP_M2_SDP.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        totalEges = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        egesCalcRuleUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.GES_PCE.equals(calculationResult.resultCategoryId) && FrenchConstants.GWP_M2_SDP.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        gesPCE = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        gesPCECalcRuleUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED1.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        egesPCEMax1 = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        carbonValueUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED2.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        egesPCEMax2 = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        carbonValueUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED1BIS.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE2.equals(calculationResult.calculationRuleId)) {
                        egesMax1 = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        carbonValueUnitForEgesMax = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED2BIS.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE2.equals(calculationResult.calculationRuleId)) {
                        egesMax2 = numberUtil.resultToBigDecimal(datasetService.getValueForZone(zoneDataSets, calculationResult))
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        carbonValueUnitForEgesMax = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    }
                }
            } else {
                for (CalculationResult calculationResult in calculationResults) {
                    if (FrenchConstants.T.equals(calculationResult.resultCategoryId) && FrenchConstants.GWP_M2_SDP.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        totalEges = numberUtil.resultToBigDecimal(calculationResult.result)
                        egesCalcRuleUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.GES_PCE.equals(calculationResult.resultCategoryId) && FrenchConstants.GWP_M2_SDP.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        gesPCE = numberUtil.resultToBigDecimal(calculationResult.result)
                        gesPCECalcRuleUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED1.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        egesPCEMax1 = numberUtil.resultToBigDecimal(calculationResult.result)
                        carbonValueUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED2.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE.equals(calculationResult.calculationRuleId)) {
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        egesPCEMax2 = numberUtil.resultToBigDecimal(calculationResult.result)
                        carbonValueUnit = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED1BIS.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE2.equals(calculationResult.calculationRuleId)) {
                        egesMax1 = numberUtil.resultToBigDecimal(calculationResult.result)
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        carbonValueUnitForEgesMax = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    } else if (FrenchConstants.CARBON_EMBODIED2BIS.equals(calculationResult.resultCategoryId) && FrenchConstants.CARBONE_VALUE2.equals(calculationResult.calculationRuleId)) {
                        egesMax2 = numberUtil.resultToBigDecimal(calculationResult.result)
                        CalculationRule calculationRule = calculationRules.find { it.calculationRuleId.equals(calculationResult.calculationRuleId) }
                        carbonValueUnitForEgesMax = calculationRule.getLocalizedUnit() ?: Constants.EMPTY_STRING
                    }
                }
            }
        }

        6.times {
            IndicateursPerformanceCollection.IndicateursPerformance indicateursPerformance = new IndicateursPerformanceCollection.IndicateursPerformance()
            if (it.equals(0)) {
                indicateursPerformance.setNom(FrenchConstants.EGES)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(egesCalcRuleUnit)
                indicateursPerformance.setValeur(totalEges)
            } else if (it.equals(1)) {
                indicateursPerformance.setNom(FrenchConstants.EGESMAX1)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(carbonValueUnitForEgesMax)
                indicateursPerformance.setValeur(egesMax1)
            } else if (it.equals(2)) {
                indicateursPerformance.setNom(FrenchConstants.EGESMAX2)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(carbonValueUnitForEgesMax)
                indicateursPerformance.setValeur(egesMax2)
            } else if (it.equals(3)) {
                indicateursPerformance.setNom(FrenchConstants.EGES_PCE)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(gesPCECalcRuleUnit)
                indicateursPerformance.setValeur(gesPCE)
            } else if (it.equals(4)) {
                indicateursPerformance.setNom(FrenchConstants.EGES_PCE_MAX1)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(carbonValueUnit)
                indicateursPerformance.setValeur(egesPCEMax1)
            } else if (it.equals(5)) {
                indicateursPerformance.setNom(FrenchConstants.EGES_PCE_MAX2)
                indicateursPerformance.setRef(it + 1)
                indicateursPerformance.setUnite(carbonValueUnit)
                indicateursPerformance.setValeur(egesPCEMax2)
            }
            indicateursPerformanceCollection.getIndicateursPerformance().add(indicateursPerformance)
        }
        return indicateursPerformanceCollection
    }

    private void setPartDonneesGeneriquesToBatiment(RSEnv.SortieProjet.Batiment batiment, Entity design) {
        Double MDEGD = 0
        Double FDES = 0
        Double PEP = 0

        if (design.datasets) {
            ResourceCache resourceCache = ResourceCache.init(design.datasets.toList())
            for (Dataset dataset in design.datasets) {
                Resource r = resourceCache.getResource(dataset)

                if (r) {
                    if ("FDES" == r.environmentDataSource) {
                        FDES++
                    } else if ("PEP" == r.environmentDataSource) {
                        PEP++
                    } else if (["MDEGD_FDES", "MDEGD_PEP"].contains(r.environmentDataSource)) {
                        MDEGD++
                    }
                }
            }
        }

        if (MDEGD) {
            batiment.setPartDonneesGeneriques(BigDecimal.valueOf((MDEGD / (FDES + PEP + MDEGD))))
        } else {
            batiment.setPartDonneesGeneriques(BigDecimal.valueOf(0))
        }
    }

    @CompileStatic
    private FrenchStorage setCoefficientsModulateursToBatiment(RSEnv.SortieProjet.Batiment batiment, List<CalculationResult> calculationResults) {
        CalculationResult calculationResultForMpark = calculationResults?.find { "Mpark" == it.resultCategoryId && "carboneValue" == it.calculationRuleId }
        RSEnv.SortieProjet.Batiment.CoefficientsModulateurs coefficientsModulateurs = new RSEnv.SortieProjet.Batiment.CoefficientsModulateurs()
        BigDecimal mParkValue = numberUtil.resultToBigDecimal(calculationResultForMpark?.result)
        coefficientsModulateurs.setMpark(mParkValue?.toFloat())
        batiment.setCoefficientsModulateurs(coefficientsModulateurs)
        return new FrenchStorage(mParkValue: mParkValue)
    }

    private void groupCalculationResults(List<String> resultCategoryIds, Entity design, List<CalculationResult> calculationResults,
                                         Map<String, List<CalculationResult>> groupedCalculationResultsByResultCat,
                                         Map<Integer, Map<String, List<CalculationResult>>> resultsPerZonePerCategory,
                                         Map<Integer, Map<String, List<CalculationResult>>> resultsPerLotPerCategory,
                                         Map<Integer, Map<String, Map<Integer, List<CalculationResult>>>> resultsPerZonePerCategoryPerLot) {
        for (String resultCategoryId in resultCategoryIds) {
            List<CalculationResult> found = calculationResults?.findAll {
                it.resultCategoryId?.equals(resultCategoryId) && !FrenchConstants.EXCLUDE_CALCULATION_RULES.contains(it.calculationRuleId)
            }

            if (found) {
                groupedCalculationResultsByResultCat.put(resultCategoryId, found)

                for (CalculationResult calculationResult in found) {
                    Map<Integer, Double> resultsPerZone = [:]
                    Map<Integer, Double> resultsPerLot = [:]
                    Map<Integer, Map<Integer, Double>> resultsPerZonePerLot = [:]

                    List<Dataset> datasets = calculationResultService.getDatasets(calculationResult, design)?.toList()

                    if (datasets) {
                        Integer maxLotNumbers = 14

                        for (Dataset dataset in datasets) {
                            Integer datasetZoneIndex = dataset.additionalQuestionAnswers?.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID) && dataset.additionalQuestionAnswers.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID).toString().isNumber() ? dataset.additionalQuestionAnswers.get(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID).toString().toInteger() : 1

                            if (datasetZoneIndex) {
                                String lotIndexString = datasetService.getLotOfDataset(dataset)
                                Integer lotIndex = lotIndexString?.isInteger() ? lotIndexString.toInteger() : null

                                Double resultForDataset = calculationResultService.getResultOfDataset(calculationResult, dataset)
                                Double zoneResult = resultsPerZone.get(datasetZoneIndex)

                                if (zoneResult) {
                                    zoneResult = zoneResult + resultForDataset
                                } else {
                                    zoneResult = resultForDataset
                                }
                                resultsPerZone.put(datasetZoneIndex, zoneResult)

                                if (lotIndex && lotIndex > 0 && lotIndex <= maxLotNumbers) {
                                    Double lotResult = resultsPerLot.get(lotIndex)

                                    if (lotResult) {
                                        lotResult = lotResult + resultForDataset
                                    } else {
                                        lotResult = resultForDataset
                                    }
                                    resultsPerLot.put(lotIndex, lotResult)

                                    Map<Integer, Double> lotResultsForZone = resultsPerZonePerLot.get(datasetZoneIndex) ?: [:]
                                    Double lotzoneResult = lotResultsForZone?.get(lotIndex)

                                    if (lotzoneResult) {
                                        lotzoneResult = lotzoneResult + resultForDataset
                                    } else {
                                        lotzoneResult = resultForDataset
                                    }
                                    lotResultsForZone.put(lotIndex, lotzoneResult)
                                    resultsPerZonePerLot.put(datasetZoneIndex, lotResultsForZone)
                                }
                            }
                        }
                    }

                    resultsPerZone.each { Integer zoneId, Double result ->
                        CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                        copy.result = result
                        copy.calculationRuleId = calculationResult.calculationRuleId

                        Map<String, List<CalculationResult>> existing = resultsPerZonePerCategory.get(zoneId)

                        if (existing) {
                            List<CalculationResult> resultsByCategoryResults = existing.get(resultCategoryId)

                            if (resultsByCategoryResults) {
                                resultsByCategoryResults.add(copy)
                            } else {
                                resultsByCategoryResults = [copy]
                            }
                            existing.put(resultCategoryId, resultsByCategoryResults)
                        } else {
                            existing = [(resultCategoryId): [copy]]
                        }
                        resultsPerZonePerCategory.put(zoneId, existing)
                    }

                    resultsPerLot.sort { it.key }.each { Integer lotId, Double result ->
                        CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                        copy.result = result
                        copy.calculationRuleId = calculationResult.calculationRuleId

                        Map<String, List<CalculationResult>> existing = resultsPerLotPerCategory.get(lotId)

                        if (existing) {
                            List<CalculationResult> resultsByCategoryResults = existing.get(resultCategoryId)

                            if (resultsByCategoryResults) {
                                resultsByCategoryResults.add(copy)
                            } else {
                                resultsByCategoryResults = [copy]
                            }
                            existing.put(resultCategoryId, resultsByCategoryResults)
                        } else {
                            existing = [(resultCategoryId): [copy]]
                        }
                        resultsPerLotPerCategory.put(lotId, existing)
                    }

                    resultsPerZonePerLot.each { Integer zoneId, Map<Integer, Double> resultPerLot ->
                        resultPerLot.sort { it.key }.each { Integer lotId, Double result ->
                            CalculationResult copy = CalculationResult.getCopyForImpactCalculation(calculationResult)
                            copy.result = result
                            copy.calculationRuleId = calculationResult.calculationRuleId

                            Map<String, Map<Integer, List<CalculationResult>>> existing = resultsPerZonePerCategoryPerLot.get(zoneId)

                            if (existing) {
                                Map<Integer, List<CalculationResult>> resultsByCategoryResults = existing.get(resultCategoryId) ?: [:]
                                List<CalculationResult> results = resultsByCategoryResults.get(lotId)

                                if (results) {
                                    results.add(copy)
                                } else {
                                    results = [copy]
                                }
                                resultsByCategoryResults.put(lotId, results)
                                existing.put(resultCategoryId, resultsByCategoryResults)
                            } else {
                                existing = [(resultCategoryId): [(lotId): [copy]]] as Map<String, Map<Integer, List<CalculationResult>>>
                            }
                            resultsPerZonePerCategoryPerLot.put(zoneId, existing)
                        }
                    }
                }
            } else {
                groupedCalculationResultsByResultCat.put(resultCategoryId, [])
            }
        }
    }

    @CompileStatic
    private void setContributeursToBatiment(RSEnv.SortieProjet.Batiment batiment, Map<String, List<CalculationResult>> groupedCalculationResultsByResultCat,
                                            List<CalculationRule> calculationRules, Map<Integer, Map<String, List<CalculationResult>>> resultsPerLotPerCategory) {
        Integer sequentialRefNumber = 1

        groupedCalculationResultsByResultCat.each { String resultCategoryId, List<CalculationResult> groupedCalcResults ->
            RSEnv.SortieProjet.Batiment.Contributeur contributeur = new RSEnv.SortieProjet.Batiment.Contributeur()
            contributeur.setRef(sequentialRefNumber)
            TIndicateur contributeurCollection = new TIndicateur()
            setIndicatuersToContributeurCollection(contributeurCollection, calculationRules, groupedCalcResults)

            resultsPerLotPerCategory.each { Integer lotRef, Map<String, List<CalculationResult>> resultByResultCategory ->
                List<CalculationResult> resultsForCategory = resultByResultCategory.get(resultCategoryId)
                RSEnv.SortieProjet.Batiment.Contributeur.Lot lot = new RSEnv.SortieProjet.Batiment.Contributeur.Lot()
                lot.setRef(lotRef)
                TIndicateur lotContributeurCollection = new TIndicateur()

                if (resultsForCategory) {
                    setIndicatuersToContributeurCollection(lotContributeurCollection, calculationRules, resultsForCategory)
                } else {
                    // set valeur = 0
                    setIndicatuersToContributeurCollection(lotContributeurCollection, calculationRules, null)
                }
                lot.setIndicateursCollection(lotContributeurCollection)
                contributeur.getLot().add(lot)
            }
            sequentialRefNumber++
            contributeur.setIndicateursCollection(contributeurCollection)
            batiment.getContributeur().add(contributeur)
        }
    }

    @CompileStatic
    private void setZonesToBatiment(RSEnv.SortieProjet.Batiment batiment, Entity design,
                                    Map<Integer, Map<String, List<CalculationResult>>> resultsPerZonePerCategory, Map<String, Integer> validZones,
                                    List<String> resultCategoryIds, List<CalculationRule> calculationRules,
                                    Map<Integer, Map<String, Map<Integer, List<CalculationResult>>>> resultsPerZonePerCategoryPerLot,
                                    FrenchStorage store, DecimalFormat decimalFormat, List<CalculationResult> calculationResults) {
        List<String> validZonesForSdpArea = datasetService.getValidFECZones(design.datasets)
        Boolean useZeroZoneForArea = Boolean.FALSE
        if (validZonesForSdpArea?.contains(FrenchConstants.FEC_UNASSIGNED_ZONE)) {
            useZeroZoneForArea = Boolean.TRUE
        }
        resultsPerZonePerCategory.each { Integer zoneId, Map<String, List<CalculationResult>> resultsPerCategory ->
            RSEnv.SortieProjet.Batiment.Zone zone = new RSEnv.SortieProjet.Batiment.Zone()
            Integer batimentZoneIndex = validZones?.get(zoneId.toString())
            if (batimentZoneIndex != null) {
                zone.setIndex(batimentZoneIndex)
            }

            setContributeursToZone(zone, resultCategoryIds, resultsPerCategory, calculationRules, resultsPerZonePerCategoryPerLot, zoneId)

            Set<Dataset> zoneDatasets = null
            if (useZeroZoneForArea) {
                zoneDatasets = datasetService.getZoneDataSets(FrenchConstants.FEC_UNASSIGNED_ZONE, design.datasets)
            } else {
                zoneDatasets = datasetService.getZoneDataSets(zoneId.toString(), design.datasets)
            }

            setCoefficientsModulateursToZone(zone, zoneDatasets, store, decimalFormat, calculationResults)

            IndicateursPerformanceCollection indicateursPerformanceCollection = new IndicateursPerformanceCollection()
            //Get zone datasets only if useZeroZoneForArea is false, meaning there are valid zone and not only "00" zone
            if (useZeroZoneForArea) {
                indicateursPerformanceCollection = getPerformanceObject(calculationRules, calculationResults)
            } else {
                indicateursPerformanceCollection = getPerformanceObject(calculationRules, calculationResults, zoneDatasets)
            }

            zone.setIndicateursPerformanceCollection(indicateursPerformanceCollection)

            batiment.getZone().add(zone)
        }
    }

    private void setContributeursToZone(RSEnv.SortieProjet.Batiment.Zone zone, List<String> resultCategoryIds, Map<String, List<CalculationResult>> resultsPerCategory, List<CalculationRule> calculationRules,
                                        Map<Integer, Map<String, Map<Integer, List<CalculationResult>>>> resultsPerZonePerCategoryPerLot, Integer zoneId) {
        int sequentialZoneRefNumber = 1

        for (String resultCategoryId in resultCategoryIds) {
            List<CalculationResult> groupedCalcResults = resultsPerCategory.get(resultCategoryId)

            RSEnv.SortieProjet.Batiment.Zone.Contributeur zoneContributeur = new RSEnv.SortieProjet.Batiment.Zone.Contributeur()
            zoneContributeur.setRef(sequentialZoneRefNumber)

            TIndicateur zoneLotContributeurCollection = new TIndicateur()

            if (groupedCalcResults) {
                setIndicatuersToContributeurCollection(zoneLotContributeurCollection, calculationRules, groupedCalcResults)
            } else {
                // set valeur = 0
                setIndicatuersToContributeurCollection(zoneLotContributeurCollection, calculationRules, null)
            }
            zoneContributeur.setIndicateursCollection(zoneLotContributeurCollection)

            Map<Integer, List<CalculationResult>> resultPerLot = resultsPerZonePerCategoryPerLot.get(zoneId)?.get(resultCategoryId)

            resultPerLot?.each { Integer lotRef, List<CalculationResult> resultsPerLot ->
                RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot zoneContributeurLot = new RSEnv.SortieProjet.Batiment.Zone.Contributeur.Lot()
                zoneContributeurLot.setRef(lotRef)
                TIndicateur lotContributeurCollection = new TIndicateur()

                if (resultsPerLot) {
                    setIndicatuersToContributeurCollection(lotContributeurCollection, calculationRules, resultsPerLot)
                } else {
                    // set valeur = 0
                    setIndicatuersToContributeurCollection(lotContributeurCollection, calculationRules, null)
                }
                zoneContributeurLot.setIndicateursCollection(lotContributeurCollection)
                zoneContributeur.getLot().add(zoneContributeurLot)
            }
            zone.getContributeur().add(zoneContributeur)
            sequentialZoneRefNumber++
        }
    }

    private void setCoefficientsModulateursToZone(RSEnv.SortieProjet.Batiment.Zone zone, Set<Dataset> zoneDatasets, FrenchStorage store,
                                                  DecimalFormat decimalFormat, List<CalculationResult> calculationResults) {
        Dataset fecSdpDataset = zoneDatasets?.find { FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID == it.queryId && FrenchConstants.DESCRIPTION_PER_ZONE_SECTIONID == it.sectionId && FrenchConstants.SDP_QUESTIONID == it.questionId }

        RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs zoneCoefficientsModulateurs = new RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs()
        zoneCoefficientsModulateurs.setMpark(store?.mParkValue?.toFloat())
        BigDecimal mgcTypeValue = numberUtil.answerToBigDecimal(fecSdpDataset?.additionalQuestionAnswers?.get('mctypeFEC')?.toString(), decimalFormat, fecSdpDataset) ?: 0.0
        zoneCoefficientsModulateurs.setMgctype(mgcTypeValue?.toFloat())
        BigDecimal mgcgeoValue = numberUtil.answerToBigDecimal(fecSdpDataset?.additionalQuestionAnswers?.get('mgcgeoFEC')?.toString(), decimalFormat, fecSdpDataset) ?: 0.0
        zoneCoefficientsModulateurs.setMgcgeo(mgcgeoValue?.toFloat())
        BigDecimal mgcaltValue = numberUtil.answerToBigDecimal(fecSdpDataset?.additionalQuestionAnswers?.get('mgcaltFEC')?.toString(), decimalFormat, fecSdpDataset) ?: 0.0
        zoneCoefficientsModulateurs.setMgcalt(mgcaltValue?.toFloat())
        BigDecimal mgcsurfValue = numberUtil.answerToBigDecimal(fecSdpDataset?.additionalQuestionAnswers?.get('mgcsurfFEC')?.toString(), decimalFormat, fecSdpDataset) ?: 0.0
        zoneCoefficientsModulateurs.setMgcsurf(mgcsurfValue?.toFloat())
        8.times {
            int ref = it + 1
            RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils valeursSeuils = new RSEnv.SortieProjet.Batiment.Zone.CoefficientsModulateurs.ValeursSeuils()
            valeursSeuils.setRef(ref)
            String resultCategory = FrenchConstants.MAP_VALEURS_SEUILS_RESULT_CATEGOR_IDS.get(ref)
            CalculationResult carbonValueCalcResult = calculationResults?.find { FrenchConstants.CARBONE_VALUE == it.calculationRuleId && resultCategory == it.resultCategoryId }
            Double result = datasetService.getValueForZone(zoneDatasets, carbonValueCalcResult)
            valeursSeuils.setValeur(numberUtil.resultToBigDecimal(result))
            zoneCoefficientsModulateurs.getValeursSeuils().add(valeursSeuils)
        }
        zone.setCoefficientsModulateurs(zoneCoefficientsModulateurs)
    }

    private void setIndicatuersToContributeurCollection(TIndicateur contributeurCollection, List<CalculationRule> calculationRules, List<CalculationResult> calculationResults) {
        for (CalculationRule calculationRule in calculationRules) {
            if (!FrenchConstants.EXCLUDE_CALCULATION_RULES.contains(calculationRule.calculationRuleId) && Constants.FEC_CALC_RULEID_INDICATEUR_REF.get(calculationRule.calculationRuleId)) {
                TIndicateur.Indicateur indicateur = new TIndicateur.Indicateur()
                indicateur.setRef(Constants.FEC_CALC_RULEID_INDICATEUR_REF.get(calculationRule.calculationRuleId))
                indicateur.setNom(calculationRule.localizedName ?: '')
                indicateur.setUnite(calculationRule.getLocalizedUnit() ?: '')

                CalculationResult calculationResult = calculationResults?.find { it.calculationRuleId?.equals(calculationRule.calculationRuleId) }
                BigDecimal valeur = calculationResult != null ? numberUtil.resultToBigDecimal(calculationResult?.result) : BigDecimal.valueOf(0)
                indicateur.setValeur(valeur)
                contributeurCollection.getIndicateur().add(indicateur)
            }
        }
    }
}
