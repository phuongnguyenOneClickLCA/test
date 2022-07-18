package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourceFilterRuleVerifyValue {
    static mapWith = "mongo"
    String parameter
    Map valueConstraints

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    boolean valueOkForResource(Resource resource) {
        boolean valueOk = false

        if (resource && parameter && valueConstraints) {
            def value = DomainObjectUtil.callGetterByAttributeName(parameter, resource)

            if (value == null && valueConstraints.get("allowNull")) {
                valueOk = true
            } else if (value != null && "numeric".equals(valueConstraints.get("type")) && (value instanceof Double || value instanceof Integer)) {
                def min = valueConstraints.get("min")
                def max = valueConstraints.get("max")

                if (min != null && max != null) {
                    if (min <= value && max >= value) {
                        valueOk = true
                    }
                }
            }
        }
        return valueOk
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    boolean calculatedValueOkForResource(Resource resource, value) {
        boolean valueOk = false

        if (resource && parameter && valueConstraints) {
            if (value == null && valueConstraints.get("allowNull")) {
                valueOk = true
            } else if (value != null && "numeric".equals(valueConstraints.get("type")) && (value instanceof Double || value instanceof Integer)) {
                def min = valueConstraints.get("min")
                def max = valueConstraints.get("max")

                if (min != null && max != null) {
                    if (min <= value && max >= value) {
                        valueOk = true
                    }
                }
            }
        }
        return valueOk
    }
}
