/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class IndicatorResultRow implements Serializable {
    static mapWith = "mongo"
    ObjectId id
    String entityId
    String indicatorId
    String resultCategory
    List<Dataset> datasetsByResultCategory
    Boolean defaultUsed
    // Boolean calculationOk

    static hasMany = [datasetsByResultCategory: Dataset]

    static mapping = {
        entityId index: true
        indicatorId index: true
        resultCategory index: true
    }

    static embedded = ['datasetsByResultCategory']

    static transients = ['total']

    boolean equals(Object o) {
        return o instanceof IndicatorResultRow && entityId == o.entityId && indicatorId == o.indicatorId && resultCategory == o.resultCategory
    }

    Double getSumPointsByCalculationRule(String calculationRule) {
        Double points

        if (datasetsByResultCategory) {
            datasetsByResultCategory.each { Dataset dataset ->
                Double point = dataset.tempPointsByCalculationRule?.get(calculationRule)

                if (point) {
                    points = points ? points + point : point
                }
            }
        }
        return points
    }

    Double getTotal() {
        Double total

        datasetsByResultCategory?.each { Dataset dataset ->
            Double temp = dataset.points

            if (temp) {
                total = total ? total + temp : temp
            }
        }
        return total
    }
}
