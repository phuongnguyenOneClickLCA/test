package com.bionova.optimi.core.service

import com.bionova.optimi.comparator.ResultListDatasetConstructionComparator
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.data.ResourceCache
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

@GrailsCompileStatic
class CalculationResultService {

    DatasetService datasetService

    /**
     *
     * @param designs
     * @return Map<designId, list of calculation results>
     */
    Map<String, List<CalculationResult>> getCalculationResultsPerDesign(List<Entity> designs, String indicatorId) {
        if (!designs || !indicatorId) {
            return null
        }

        List<String> entityIds = designs.collect { it.id.toString() }
        List<CalculationResult> results = CalculationResult.findAllByEntityIdInListAndIndicatorId(entityIds, indicatorId)

        if (!results) {
            return null
        }

        Map<String, List<CalculationResult>> resultsPerDesign = [:]
        for (Entity design in designs) {
            List<CalculationResult> designResults = results.findAll { it.entityId == design.id.toString() }
            if (designResults) {
                resultsPerDesign.put(design.id.toString(), designResults)
            }
        }
        return resultsPerDesign
    }

    CalculationResult getCalcResultByResultCatAndCalcRule(String resultCatId, String calcRuleId, List<CalculationResult> results) {
        if (resultCatId && calcRuleId && results) {
            return results.find { it.calculationRuleId == calcRuleId && it.resultCategoryId == resultCatId }
        }
        return null
    }

    Double getResultOfDataset(CalculationResult calculationResult, Dataset dataset) {
        if (!dataset || !calculationResult) {
            return null
        }
        Double result = calculationResult.calculationResultDatasets?.get(dataset.manualId)?.result as Double

        if (!result || result.isNaN()) {
            return 0d
        }

        return result
    }

    Double getResultOfDataset(CalculationResult calculationResult, String manualId) {
        if (!manualId || !calculationResult) {
            return null
        }
        Double result = calculationResult.calculationResultDatasets?.get(manualId)?.result as Double

        if (!result || result.isNaN()) {
            return 0d
        }

        return result
    }

    void sumResultSameGroup(Double result, String key, Map<String, Double> resultsPerGroup) {
        if (result == null || key == null || resultsPerGroup == null) {
            return
        }

        Double existing = resultsPerGroup.get(key)

        if (existing) {
            existing += result
        } else {
            existing = result
        }
        resultsPerGroup.put(key, existing)
    }

    void groupCalculationResultByKey(CalculationResult result, String key, Map<String, List<CalculationResult>> resultsPerGroup) {
        if (result == null || !key || resultsPerGroup == null) {
            return
        }
        List<CalculationResult> results = resultsPerGroup.get(key)

        if (results) {
            results.add(result)
        } else {
            results = [result] as List<CalculationResult>
        }
        resultsPerGroup.put(key, results)
    }

    Set<Dataset> getDatasetsFromCalculationResult(CalculationResult result, Entity entity) {
        if (!entity || !result) {
            return null
        }

        if (result.calculationResultDatasets) {
            return entity.datasets?.findAll {
                result.calculationResultDatasets.keySet().contains(it.manualId)
            }
        } else if (result.datasetIds) {
            return entity.datasets?.findAll {
                result.datasetIds.contains(it.manualId)
            }
        } else {
            return null
        }
    }

    Set<Dataset> getDatasets(CalculationResult calculationResult, Entity entity) {
        if (entity && calculationResult?.calculationResultDatasets) {
            Set<String> keys = calculationResult.calculationResultDatasets.keySet()

            return entity.datasets?.findAll {
                keys.contains(it.manualId)
            }
        } else if (entity && calculationResult?.datasetIds) {
            return entity.datasets?.findAll{
                calculationResult.datasetIds.contains(it.manualId)
            }
        }

        return null
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Set<Dataset> getCalculationResultDatasets(Entity entity, Indicator indicator, List<CalculationResult> indicatorResults) {

        Set<Dataset> datasets = []

        Set<Dataset> uniqueCalculationResultDatasets = []
        indicatorResults.each { CalculationResult calculationResult ->
            Set<Dataset> calculationResultDatasets = getDatasets(calculationResult, entity)
            List<Resource> resourceForCurrentCalculationResult = calculationResultDatasets?.findResults { datasetService.getResource(it) }
            Set<String> uniqueDatasetIds = []
            for (Resource resIter in resourceForCurrentCalculationResult) {
                if (resIter?.linkedDatasetManualIds) {
                    uniqueDatasetIds.addAll(resIter.linkedDatasetManualIds)
                }
            }

            if (calculationResultDatasets) {
                uniqueCalculationResultDatasets.addAll(calculationResultDatasets.findAll { Dataset ds ->
                    ds?.manualId && uniqueDatasetIds?.contains(ds.manualId)
                })
            }
        }
        if (indicator.isResourceAggregationDisallowed) {
            // finding constituents and linking them to their constructions
            datasets = uniqueCalculationResultDatasets?.findAll { it?.resourceId }
                    ?.each { datasetService.setParentConstructionDataset(it, uniqueCalculationResultDatasets) }
            // explicitly adding constructions to the dataset list in case they are not there
            uniqueCalculationResultDatasets?.findAll { it?.parentConstructionDataset }
                    ?.collect(datasets) { it?.parentConstructionDataset }
            // sorting the dataset list
            datasets = datasets?.toSorted(new ResultListDatasetConstructionComparator())
        } else {
            datasets = uniqueCalculationResultDatasets
        }

        return datasets
    }
}
