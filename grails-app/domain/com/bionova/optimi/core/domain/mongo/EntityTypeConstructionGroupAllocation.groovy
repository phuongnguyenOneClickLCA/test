/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import org.apache.commons.lang.StringUtils

@GrailsCompileStatic
class EntityTypeConstructionGroupAllocation {
    static mapWith = "mongo"
    String groupId
    String buildingElement
    String unit
    String quantitySource
    Map<String, String> target
    @Translatable
    Map<String, String> name

    List<String>  applicableIndicatorIds
    List<String>  applicableRegionReferenceIds
    List<CarbonDesignerDefaultValueConstituents> defaultValueConstituents

    String questionMarkCode


    static embedded = [
            'defaultValueConstituents'
    ]

    static transients = ['classForUi']

    String getClassForUi() {
        if (buildingElement) {
            return StringUtils.substringBefore(buildingElement, "Group")
        } else {
            return ""
        }
    }

}
