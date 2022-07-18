package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BuildingTypeDefaultData {

    String buildingTypeId
    String parameterId
    Double minValue
    Double maxValue
    Double defaultValue
    Map<String, Double> regionDefaultValues

    static constraints = {
        minValue nullable: true
        maxValue nullable: true
        defaultValue nullable: true
        regionDefaultValues nullable: true
    }
}
