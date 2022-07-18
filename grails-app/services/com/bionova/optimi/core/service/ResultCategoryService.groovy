package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.util.LoggerUtil


class ResultCategoryService {
    LoggerUtil loggerUtil
    ValueReferenceService valueReferenceService
    DatasetService datasetService

    String getLocalizedShortName(ResultCategory resultCategory, int trimCounter = 0) {
        String localizedShortName = ""
        if (!resultCategory) {
            return localizedShortName
        }
        localizedShortName = resultCategory.localizedShortName ?: resultCategory.localizedName
        return localizedShortName && trimCounter && trimCounter < localizedShortName.size() ?
                localizedShortName.take(trimCounter) + "..." : localizedShortName
    }

    Dataset getDefaultDataset(ResultCategory resultCategory, Entity entity) {
        Dataset defaultDataset
        Dataset datasetForQuantity

        ValueReference defaultQuantityValueReference = resultCategory.resultManipulators?.find{
            it?.defaultQuantity
        }?.defaultQuantity

        try {
            if (defaultQuantityValueReference) {
                List<Dataset> datasets = valueReferenceService.getDatasetsForEntity(defaultQuantityValueReference, entity)

                if (datasets) {
                    datasetForQuantity = datasets[0]
                }
            }

            if (datasetForQuantity) {
                defaultDataset = datasetService.createCopy(datasetForQuantity)
                defaultDataset.points = datasetForQuantity.quantity ?: 0D
                defaultDataset.quantity = datasetForQuantity.quantity
                defaultDataset.calculatedQuantity = datasetForQuantity.quantity
                defaultDataset.resourceId = resultCategory.defaultResourceId
            }
        } catch (Exception e) {
            loggerUtil.error(log, "getDefaultDataset failed with exception: ${e}")
        }

        return defaultDataset
    }
}
