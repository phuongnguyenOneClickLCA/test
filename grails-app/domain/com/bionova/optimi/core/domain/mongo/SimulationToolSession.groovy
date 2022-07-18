package com.bionova.optimi.core.domain.mongo

class SimulationToolSession {

    String sessionId
    String unitSystem

    //******************************
    //User Input
    //******************************
    Double grossFloorArea
    Double netFloorArea
    Integer aboveGroundFloors
    Integer undergroundHeatedFloors
    Integer undergroundUnheatedFloors
    Integer assessmentPeriod
    Boolean isEarthquakeZone // Deprecated zone is either medium or high in earthquakeZone
    String earthquakeZone
    Boolean allowPrivateConstructions

    String userId
    String entityId
    String designId
    String indicatorId
    String regionReferenceId

    //Energy Section
    Double heatedArea
    Double dut
    Double averageTemperature
    String energyScenarioId
    String buildingTypeId
    //******************************

    //LCC
    String lccIndicatorId

    String simulationToolId

    Indicator indicator
    Boolean valid

    Entity tempEntity

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

    static transients = ["totalFloor"]

    SimulationTool getSimulationTool() {

        SimulationTool simulationTool = new SimulationTool()
        simulationTool.entityId = this.entityId
        simulationTool.designId = this.designId
        //The Draft is not related anymore to the indicator, only the impact is related to indicator
        //simulationTool.indicatorId = this.indicatorId
        simulationTool.lccIndicatorId = this.lccIndicatorId
        simulationTool.assessmentPeriod = this.assessmentPeriod

        simulationTool.regionReferenceId = this.regionReferenceId
        simulationTool.buildingTypeId = this.buildingTypeId

        simulationTool.grossFloorArea = this.grossFloorArea
        simulationTool.aboveGroundFloors = this.aboveGroundFloors
        simulationTool.undergroundHeatedFloors = this.undergroundHeatedFloors
        simulationTool.undergroundUnheatedFloors = this.undergroundUnheatedFloors
        simulationTool.isEarthquakeZone = this.isEarthquakeZone
        simulationTool.earthquakeZone = this.earthquakeZone
        simulationTool.allowPrivateConstructions = this.allowPrivateConstructions

        simulationTool.heatedArea = this.heatedArea
        simulationTool.dut = this.dut
        simulationTool.averageTemperature = this.averageTemperature
        simulationTool.energyScenarioId = this.energyScenarioId

        //Building Dimension

        simulationTool.heightBuilding = this.heightBuilding
        simulationTool.widthBuinding = this.widthBuinding
        simulationTool.depthBuilding = this.depthBuilding
        simulationTool.maxSpanWithoutColumns_m = this.maxSpanWithoutColumns_m
        simulationTool.internalFloorHeight = this.internalFloorHeight
        simulationTool.loadBearingShare = this.loadBearingShare
        simulationTool.numberStaircases = this.numberStaircases
        simulationTool.shapeEfficiencyFactor = this.shapeEfficiencyFactor
        simulationTool.totalNetArea = this.totalNetArea

        //RE2020
        simulationTool.surfaceDeReference = this.surfaceDeReference
        simulationTool.surfaceDeScombles = this.surfaceDeScombles
        simulationTool.nombreDeLogement = this.nombreDeLogement

        return simulationTool
    }

    def getTotalFloor(){

        return (aboveGroundFloors?:0) + (undergroundHeatedFloors?:0) + (undergroundUnheatedFloors?:0)
    }

}
