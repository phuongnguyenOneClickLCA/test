package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class CostStructure implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String name
    Double materialCost
    String materialCostCurrency
    Double labourUnitsWorker
    Double labourUnitsCraftsman
    String costUnit
    String resourceSubType
    String importFile
    Double thickness_mm
    Double density

    static constraints = {
        materialCost nullable: true
        materialCostCurrency nullable: true
        labourUnitsWorker nullable: true
        labourUnitsCraftsman nullable: true
        importFile nullable: true
        resourceSubType nullable: true
        thickness_mm nullable: true
        density nullable: true
    }
}
