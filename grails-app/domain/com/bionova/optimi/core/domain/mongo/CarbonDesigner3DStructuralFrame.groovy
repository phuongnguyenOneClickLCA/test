package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DStructuralFrame implements Serializable {

    String structuralFrameId
    @Translatable(alias = "name")
    Map<String,String> structuralFrameName
    Boolean columnsPresent
    Boolean beamsPresent
    Boolean beamsInBothDirections
    Double secondaryBeamsFactor
    Double shearWallsPercentage
    Boolean removeColumnsInPerimeter
    Double diagonalWindBracingsFactor
    Boolean connectingPartsPresent
    Boolean otherRMCPresent
    Double maxSpanWithoutColumns_m
    Double loadBearingInternalWallShare

    static constraints = {
        secondaryBeamsFactor nullable: true, range: 0..0.6
        shearWallsPercentage nullable: true, range: 0..0.6
        removeColumnsInPerimeter nullable: true
        diagonalWindBracingsFactor nullable: true
        connectingPartsPresent nullable: true
        otherRMCPresent nullable: true
        maxSpanWithoutColumns_m nullable: true
        loadBearingInternalWallShare nullable: true, range: 0..100
    }

}
