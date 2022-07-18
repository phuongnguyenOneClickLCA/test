package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class CarbonDesigner3DScenario implements Serializable, Validateable {

    ObjectId id
    @Translatable
    Map<String,String> name
    String scenarioType
    String accountId
    List<String> buildingTypeIds
    List<String> regionIds
    List<String> structuralFrameIds
    Set<Dataset> datasets
    Integer minFloors
    Integer maxFloors
    Boolean published = false
    Boolean publicScenario = false

    static constraints = {
        name nullable: false
        scenarioType nullable: false
        accountId nullable: true
        buildingTypeIds nullable: false
        regionIds nullable: false
        datasets nullable: false
        minFloors nullable: true
        maxFloors nullable: true
        structuralFrameIds nullable: true
    }

    static hasMany = [datasets: Dataset]
    static embedded = ["datasets"]

    static transients = []

}
