package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.AppliedNmdScaling
import com.bionova.optimi.core.domain.mongo.Dataset
import grails.gorm.transactions.Transactional

@Transactional
class NmdCalculationService {

    def numberUtil

    /**
     * Set to datasets preResultFormula the quantity and multiplier when scaling was applied
     * @param datasets
     */
    void setAppliedScalingToPreResultFormula(List<Dataset> datasets) {
        if (!datasets) {
            return
        }

        for (Dataset dataset in datasets) {
            if (!dataset?.appliedNmdScaling) {
                continue
            }

            AppliedNmdScaling appliedNmdScaling = dataset.appliedNmdScaling
            dataset.preResultFormula = "${dataset.preResultFormula ?: ''}<b>Quantity: ${numberUtil.roundingNumber(appliedNmdScaling.calculatedQuantity)}.</b> Calculated from ${appliedNmdScaling.quantity} * ${numberUtil.roundingNumber(appliedNmdScaling.multiplier)} (Scaling multiplier)<br />"
        }
    }
}
