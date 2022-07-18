package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DBuildingShape implements Serializable {

    String buildingShapeId
    @Translatable(alias = "name")
    Map<String,String> buildingShapeName
    String buildingShapeImage

    static transients = []
}
