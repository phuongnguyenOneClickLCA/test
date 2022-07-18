package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class SimulationTool implements Serializable, Validateable {

    static mapWith = "mongo"

    ObjectId id

    //******************************
    //User Input
    //******************************
    Double grossFloorArea
    Integer aboveGroundFloors
    Integer undergroundHeatedFloors
    Integer undergroundUnheatedFloors
    Integer assessmentPeriod
    Boolean isEarthquakeZone // deprecated
    String earthquakeZone
    Boolean allowPrivateConstructions

    String userId
    String entityId
    String designId
    String regionReferenceId
    String lccIndicatorId

    //Energy Section
    Double heatedArea
    Double dut
    Double averageTemperature
    String energyScenarioId
    String buildingTypeId
    //******************************

    //Building Dimension

    Double heightBuilding
    Double widthBuinding
    Double depthBuilding
    Double maxSpanWithoutColumns_m
    Double internalFloorHeight
    Double loadBearingShare
    Integer numberStaircases
    Double totalNetArea
    Double shapeEfficiencyFactor

    //RE2020 - FIP PROJECT
    Double surfaceDeReference
    Double surfaceDeScombles
    Double nombreDeLogement

    List<SimulationToolConstructionGroup> simulationToolConstructionGroups

    List<SimulationToolEnergyType> energyTypeList

    static embedded = ['simulationToolConstructionGroups',
                       'energyTypeList']

    static hasMany = [simulationToolConstructionGroups: SimulationToolConstructionGroup,
                      energyTypeList: SimulationToolEnergyType]

    static constraints = {
        simulationToolConstructionGroups nullable:true
        energyTypeList nullable:true
        heatedArea nullable:true
        dut nullable:true
        averageTemperature nullable:true
        energyScenarioId nullable:true
        lccIndicatorId nullable: true
        heightBuilding nullable: true
        widthBuinding nullable: true
        depthBuilding nullable: true
        maxSpanWithoutColumns_m nullable: true
        internalFloorHeight nullable: true
        loadBearingShare nullable: true
        numberStaircases nullable: true
        totalNetArea nullable: true
        shapeEfficiencyFactor nullable: true
        assessmentPeriod nullable: true
        allowPrivateConstructions nullable: true
        isEarthquakeZone nullable: true
        earthquakeZone nullable: true
        surfaceDeReference nullable: true
        surfaceDeScombles nullable: true
        nombreDeLogement nullable: true
    }

}
