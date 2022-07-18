package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ResultManipulator
import com.bionova.optimi.core.domain.mongo.ValueReference
import grails.gorm.transactions.Transactional

@Transactional
class ResultManipulatorService {
    ValueReferenceService valueReferenceService

    List<ValueReference> getMultipliersAsValueReferences(ResultManipulator resultManipulator) {
        List<ValueReference> valueReferences = new ArrayList<ValueReference>()

        if (resultManipulator) {
            List multipliers = resultManipulator.multipliers

            if (multipliers) {
                multipliers.each {
                    valueReferences.add((ValueReference) it)
                }
            }
        }
        return valueReferences
    }

    List<ValueReference> getDividersAsValueReferences(ResultManipulator resultManipulator) {
        List<ValueReference> valueReferences = new ArrayList<ValueReference>()

        if (resultManipulator) {
            List dividers = resultManipulator.dividers

            if (dividers) {
                dividers.each {
                    valueReferences.add((ValueReference) it)
                }
            }
        }
        return valueReferences
    }

    Double getMultiplierForEntity(Entity entity, ResultManipulator resultManipulator) {
        Double manipulator

        if (entity && resultManipulator) {
            List<ValueReference> multipliers = getMultipliersAsValueReferences(resultManipulator)

            if (multipliers && !multipliers.isEmpty()) {
                for (ValueReference valueReference in multipliers) {
                    Double value = valueReferenceService.getDoubleValueForEntity(valueReference, entity)

                    if (value != null) {
                        manipulator = manipulator ? manipulator * value : value
                    }
                }
            }

            Double fixedMultiplier = resultManipulator.fixedMultiplier
            if (fixedMultiplier) {
                manipulator = manipulator ? manipulator * fixedMultiplier : fixedMultiplier
            }
        }
        return manipulator
    }

    Double getDividerForEntity(Entity entity, ResultManipulator resultManipulator) {
        Double manipulator

        if (entity && resultManipulator) {
            List<ValueReference> dividers = getDividersAsValueReferences(resultManipulator)

            if (dividers && !dividers.isEmpty()) {
                for (ValueReference valueReference in dividers) {
                    Double value = valueReferenceService.getDoubleValueForEntity(valueReference, entity)

                    if (value != null) {
                        manipulator = manipulator ? manipulator * value : value
                    }
                }
            }
        }
        return manipulator
    }
}
