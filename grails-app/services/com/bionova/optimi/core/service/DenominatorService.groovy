package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ResultFormatting
import com.bionova.optimi.core.domain.mongo.ResultManipulator
import com.bionova.optimi.core.domain.mongo.ValueReference
import grails.gorm.transactions.Transactional

@Transactional
class DenominatorService {
    // to be cleaned up later
    ValueReferenceService valueReferenceService
    DatasetService datasetService

    List<ValueReference> getDenominatorValuesAsValueReferences(List<ValueReference> denominatorValues) {
        List<ValueReference> valueReferences = new ArrayList<ValueReference>()

        if (denominatorValues) {
            denominatorValues.each {
                valueReferences.add((ValueReference) it)
            }
        }
        return valueReferences
    }

    List<ResultManipulator> getResultManipulatorsAsManipulatorObjects(List<ResultManipulator> resultManipulator) {
        List<ResultManipulator> manipulators = new ArrayList<ResultManipulator>()

        if (resultManipulator) {
            resultManipulator.each {
                manipulators.add((ResultManipulator) it)
            }
        }
        return manipulators
    }

    List<Dataset> getDynamicDenominatorDatasets(Entity entity, Denominator denominator) {
        if (!denominator) {
            return []
        }

        List<Dataset> datasets
        for (ValueReference valueReference in getDenominatorValuesAsValueReferences(denominator.denominatorValues)) {
            if (denominator.requireMonthly) {
                datasets = valueReferenceService.getDatasetsForEntity(valueReference, entity)?.findAll({ datasetService.getMonthlyQuantities(it.monthlyAnswers) && it.resourceId })
            } else {
                datasets = valueReferenceService.getDatasetsForEntity(valueReference, entity)?.findAll({ it.quantity && it.resourceId })
            }
        }
        return datasets
    }

    List<Dataset> getMonthlyValuereferenceDatasets(Entity entity, Denominator denominator) {
        if (!denominator) {
            return []
        }
        List<Dataset> datasets

        for (ValueReference valueReference in getDenominatorValuesAsValueReferences(denominator.denominatorValues)) {
            datasets = valueReferenceService.getDatasetsForEntity(valueReference, entity)
        }
        return datasets
    }

    ResultFormatting getResultFormattingObject(Map resultFormatting) {
        ResultFormatting resultFormattingObject

        if (resultFormatting) {
            resultFormattingObject = new ResultFormatting()
            resultFormattingObject.showDecimals = resultFormatting.get("showDecimals") != null ?
                    resultFormatting.get("showDecimals") : Boolean.FALSE
            resultFormattingObject.unit = resultFormatting.get("unit")
            resultFormattingObject.minimumZeros = resultFormatting.get("minimumZeros")
            resultFormattingObject.minimumDecimals = resultFormatting.get("minimumDecimals")
            resultFormattingObject.rounding = resultFormatting.get("rounding")
            resultFormattingObject.useScientificNumbers = resultFormatting.get("useScientificNumbers")
            resultFormattingObject.significantDigits = resultFormatting.get("significanDigits")
        }
        return resultFormattingObject
    }
}
