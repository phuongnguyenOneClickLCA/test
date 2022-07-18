package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourceFilterRule {
    static mapWith = "mongo"
    String ruleId
    @Translatable
    Map<String, String> name
    List<String> requiredFields
    Map<String, List<String>> requiredValues
    Map<String, List<String>> illegalValues
    Map<String, Object> dataMaxAge
    Map<String, List<String>> resourceFilterCriteria
    List<String> activeResourceId
    Boolean activeResourceType
    Boolean activeResourceSubType
    List verifyValues
    List verifyCalculatedValues
    List<String> requireIdenticalValues
    Boolean nonShallPass
    Boolean checkConstructionsAlso
    Boolean checkPrivateDataAlso
    String filterGroup

    List<ResourceFilterCriteria> advancedResourceFilterCriteria
    List<ResourceFilterCriteria> advancedRequiredValues

    static transients = [
            'verifyValuesAsObjects',
            'verifyCalculatedValuesAsObjects'
    ]

    static embedded = ['advancedResourceFilterCriteria', 'advancedRequiredValues']

    List<ResourceFilterRuleVerifyValue> getVerifyValuesAsObjects() {
        List<ResourceFilterRuleVerifyValue> objects = []

        if (verifyValues) {
            verifyValues.each {
                objects.add(it as ResourceFilterRuleVerifyValue)
            }
        }
        return objects
    }

    List<ResourceFilterRuleVerifyValue> getVerifyCalculatedValuesAsObjects() {
        List<ResourceFilterRuleVerifyValue> objects = []

        if (verifyCalculatedValues) {
            verifyCalculatedValues.each {
                objects.add(it as ResourceFilterRuleVerifyValue)
            }
        }
        return objects
    }


}
