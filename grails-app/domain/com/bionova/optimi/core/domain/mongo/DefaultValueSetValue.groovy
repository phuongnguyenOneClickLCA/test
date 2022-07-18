package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class DefaultValueSetValue {
    static mapWith = "mongo"
    ObjectId id
    String applicationId
    String defaultValueId
    String defaultValueSetId
    String resourceType
    Double value

    static transients = [
            'formattedValue'
    ]

    static mapping = {
        compoundIndex applicationId: 1, defaultValueId: 1, defaultValueSetId: 1
        compoundIndex applicationId: 1, defaultValueId: 1, defaultValueSetId: 1, resourceType: 1
    }

    transient userService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getFormattedValue() {
        String formattedValue

        if (value) {
            formattedValue = userService.getDefaultDecimalFormat().format(value)
        }
        return formattedValue
    }

    boolean equals(Object o) {
        boolean equals = false

        if (o instanceof DefaultValueSetValue && o.applicationId == applicationId &&
                o.defaultValueId == defaultValueId && o.defaultValueSetId == defaultValueSetId &&
                o.resourceType == resourceType) {
            equals = true
        }
    }
}
