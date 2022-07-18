/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class IndicatorQuery implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    static final String RESOURCE = "resourceFilterCriteria"
    static final String ORGANISATION_RESOURCE = "organisationResourceFilterCriteria"
    static final String WARNING_RESOURCE = "warningResourceFilterCriteria"
    static final String BLOCK_RESOURCE = "blockResourceFilterCriteria"
    static final String WARNING_MESSAGE_INDICATOR = "warningStringId"
    static final String BLOCK_MESSAGE_INDICATOR = "blockStringId"

    // keys of ResourceFilterCriteria
    static final String ENABLED_PURPOSES_FILTER = "enabledPurposes"
    static final String DATA_PROPERTIES_FILTER = "dataProperties"
    static final String RESOURCE_GROUP_FILTER = "resourceGroup"
    static final String RESOURCE_TYPE_FILTER = "resourceType"
    static final String ENVIRONMENT_DATA_SOURCE_STANDARD_FILTER = "environmentDataSourceStandard"

    @Override
    public String toString() {
        return "\nIndicatorQuery{" +
                ",\n id=" + id +
                ",\n version=" + version +
                ",\n queryId='" + queryId + '\'' +
                ",\n queryCalculationMethod='" + queryCalculationMethod + '\'' +
                ",\n queryCalculationFunction='" + queryCalculationFunction + '\'' +
                ",\n optional=" + optional +
                ",\n widgetQuery=" + widgetQuery +
                ",\n preventChanges=" + preventChanges +
                ",\n additionalQuestionIds=" + additionalQuestionIds +
                ",\n additionalQuestionInputWidth=" + additionalQuestionInputWidth +
                ",\n licenseKey='" + licenseKey + '\'' +
                ",\n requiredLicenseKeys=" + requiredLicenseKeys +
                ",\n disableForLicenseKeys=" + disableForLicenseKeys +
                ",\n resourceFilterCriteria=" + resourceFilterCriteria +
                ",\n organisationResourceFilterCriteria=" + organisationResourceFilterCriteria +
                ",\n warningResourceFilterCriteria=" + warningResourceFilterCriteria +
                ",\n blockResourceFilterCriteria=" + blockResourceFilterCriteria +
                ",\n applyFilterOnCriteria=" + applyFilterOnCriteria +
                ",\n defaultValues=" + defaultValues +
                ",\n requiredResourceId=" + requiredResourceId +
                ",\n projectLevel=" + projectLevel +
                '}';
    }

    static final enum ResourceFilterCriteria {
        ENABLED_PURPOSES(ENABLED_PURPOSES_FILTER),
        DATA_PROPERTIES(DATA_PROPERTIES_FILTER),
        RESOURCE_GROUP(RESOURCE_GROUP_FILTER),
        RESOURCE_TYPE(RESOURCE_TYPE_FILTER),
        ENVIRONMENT_DATA_SOURCE_STANDARD(ENVIRONMENT_DATA_SOURCE_STANDARD_FILTER)

        private final String key

        private ResourceFilterCriteria(String key) {
            this.key = key
        }

        String getKey() {
            return key
        }
    }

    String queryId
    String queryCalculationMethod
    String queryCalculationFunction
    Boolean optional
    Boolean widgetQuery
    Boolean preventChanges
    List<String> additionalQuestionIds
    Map<String, Integer> additionalQuestionInputWidth // Override inline width styling per tool

    // licenseKey, requiredLicenseKeys, disableForLicenseKeys are used in licenseService doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys
    String licenseKey
    // only show query if project has one of the license key
    List<String> requiredLicenseKeys
    // hide query if project has one of the license key
    List<String> disableForLicenseKeys

    Map<String, List<Object>> resourceFilterCriteria // List<String> or Valuereference
    Map<String, List<Object>> organisationResourceFilterCriteria
    Map<String, List<Object>> warningResourceFilterCriteria
    Map<String, List<Object>> blockResourceFilterCriteria

    QueryApplyFilterOnCriteria applyFilterOnCriteria
    Map<String, List<String>> defaultValues
    List<String> requiredResourceId
    Boolean projectLevel

    static constraints = {
        queryCalculationMethod nullable: true, blank: true
        queryCalculationFunction nullable: true, blank: true
        optional nullable: true
        widgetQuery nullable: true
        additionalQuestionIds nullable: true
        licenseKey nullable: true
        resourceFilterCriteria nullable: true
        applyFilterOnCriteria nullable: true
        defaultValues nullable: true
        preventChanges nullable: true
        requiredResourceId nullable: true
        projectLevel nullable: true
        additionalQuestionInputWidth nullable: true
        organisationResourceFilterCriteria nullable: true
        warningResourceFilterCriteria nullable: true
        blockResourceFilterCriteria nullable: true
        requiredLicenseKeys nullable: true
        disableForLicenseKeys nullable: true
    }

    static transients = ['defaultValueObjects']

    int compareTo(Object obj) {
        if(obj && obj instanceof IndicatorQuery) {
            if (this.queryId && obj.queryId) {
                this.queryId.compareTo(obj.queryId)
            } else {
                return 1
            }
        } else {
            return 1
        }
    }

}
