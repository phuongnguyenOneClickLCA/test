package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

/**
 * Created by pmm on 25.4.2016.
 */
@GrailsCompileStatic
class DefaultValueSet {
    static mapWith = "mongo"
    String defaultValueSetId
    @Translatable
    Map<String, String> name
    List<DefaultValueSetValue> defaultValueSetValues
    String defaultValueId
    String applicationId

    static transients = [
            'defaultValueSetValues'
    ]

    transient defaultValueService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<DefaultValueSetValue> getDefaultValueSetValues() {
        return defaultValueService.getDefaultValueSetValues(applicationId, defaultValueId, defaultValueSetId)
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    DefaultValueSetValue getDefaultValueSetValueByResourceType(String resourceType) {
        return defaultValueService.getDefaultValueSetValue(applicationId, defaultValueId, defaultValueSetId,
                resourceType)
    }
}
