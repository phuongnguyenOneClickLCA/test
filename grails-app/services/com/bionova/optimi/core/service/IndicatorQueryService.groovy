package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.DefaultValue
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.ValueReference
import grails.gorm.transactions.Transactional
import org.bson.Document

@Transactional
class IndicatorQueryService {

    ValueReferenceService valueReferenceService
    ApplicationService applicationService

    Map<String, List<Object>> getFormattedFilterCriteria(Entity child, String filterCriteriaType, IndicatorQuery indicatorQuery) {
        Map<String, List<Object>> formattedFilterCriteria = [:] // String or boolean answers

        if (indicatorQuery?."${filterCriteriaType}") {
            indicatorQuery."${filterCriteriaType}".each { String key, value ->
                if (value && value instanceof List && !value.isEmpty()) {
                    value.each { val ->
                        def formattedValue

                        if (val instanceof Document) {
                            ValueReference valueReference = val as ValueReference
                            if (child) {
                                formattedValue = valueReferenceService.getValueForEntity(valueReference, child, Boolean.FALSE, Boolean.TRUE)
                            }
                        } else {
                            formattedValue = val
                        }

                        if (formattedValue != null || (val == null && formattedValue == null)) {
                            def existing = formattedFilterCriteria.get(key)

                            if (existing) {
                                existing.add(formattedValue)
                            } else {
                                existing = [formattedValue]
                            }
                            formattedFilterCriteria.put(key, existing)
                        }
                    }
                }
            }
        }
        return formattedFilterCriteria
    }


    List<DefaultValue> getDefaultValueObjects(Map<String, List<String>> defaultValues) {
        List<DefaultValue> defaultValueObjects = []

        if (defaultValues) {
            defaultValues.each { String applicationId, List<String> resourceTypes ->
                Application application = applicationService.getApplicationByApplicationId(applicationId)
                List<DefaultValue> appDefaultValues = application.defaultValues?.findAll({ DefaultValue defaultValue ->
                    resourceTypes.contains(defaultValue.resourceType)})

                if (appDefaultValues && !appDefaultValues.isEmpty()) {
                    defaultValueObjects.addAll(appDefaultValues)
                }
            }
        }
        return defaultValueObjects
    }

     /**
     * Provides required resources for indicator and query
     * @param indicator
     * @param queryId
     * @return
     */
    List<String> getRequiredResourceIds(Indicator indicator, String queryId) {

        IndicatorQuery indicatorQuery = indicator.indicatorQueries?.find({
            it.queryId?.equals(queryId)
        })

        return indicatorQuery?.requiredResourceId
    }

}
