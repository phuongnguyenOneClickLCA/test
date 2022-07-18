package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.DefaultValueSetValue

/**
 * @author Pasi-Markus Mäkelä
 */
class DefaultValueService {

    static transactional = "mongo"

    def getDefaultValueSetValue(String applicationId, String defaultValueId, String defaultValueSetId,
                                String resourceType) {
        DefaultValueSetValue defaultValueSetValue

        if (applicationId && defaultValueId && defaultValueSetId && resourceType) {
             defaultValueSetValue = DefaultValueSetValue.
                     findByApplicationIdAndDefaultValueIdAndDefaultValueSetIdAndResourceType(applicationId,
                        defaultValueId, defaultValueSetId, resourceType)
        }
        return defaultValueSetValue
    }

    def getDefaultValueSetValues(String applicationId, String defaultValueId, String defaultValueSetId) {
        List<DefaultValueSetValue> defaultValueSetValues

        if (applicationId && defaultValueId && defaultValueSetId) {
            defaultValueSetValues = DefaultValueSetValue.
                    findAllByApplicationIdAndDefaultValueIdAndDefaultValueSetId(applicationId, defaultValueId,
                            defaultValueSetId)
        }
        return defaultValueSetValues
    }

    def saveDefaultValueSetValue(String applicationId, String defaultValueId, String defaultValueSetId,
                                 String resourceType, Double value) {
        DefaultValueSetValue defaultValueSetValue = getDefaultValueSetValue(applicationId, defaultValueId,
                defaultValueSetId, resourceType)

        if (defaultValueSetValue) {
            defaultValueSetValue.value = value
            defaultValueSetValue = defaultValueSetValue.merge(flush: true)
        } else {
            defaultValueSetValue = new DefaultValueSetValue()
            defaultValueSetValue.applicationId = applicationId
            defaultValueSetValue.defaultValueId = defaultValueId
            defaultValueSetValue.defaultValueSetId = defaultValueSetId
            defaultValueSetValue.resourceType = resourceType
            defaultValueSetValue.value = value
            defaultValueSetValue = defaultValueSetValue.save(flush: true)
        }
        return defaultValueSetValue
    }

    def removeDefaultValueSetValue(String applicationId, String defaultValueId, String defaultValueSetId,
                                   String resourceType) {
         boolean removeOk = false
         DefaultValueSetValue defaultValueSetValue = getDefaultValueSetValue(applicationId, defaultValueId,
            defaultValueSetId, resourceType)

        if (defaultValueSetValue) {
            defaultValueSetValue.delete(flush: true)
            removeOk = true
        }
        return removeOk
    }
}
