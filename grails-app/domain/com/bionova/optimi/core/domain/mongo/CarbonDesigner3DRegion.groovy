package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DRegion implements Serializable {

    //=== Fullstack properties
    String regionId
    @Translatable(alias = "name")
    Map<String,String> regionName
    List<String> regionBuildingTypes
    CustomSection customSection

    //=== Backend properties
    List<String> regionCompatibleTools
    Map<String, List<String>> regionMaterialRequirements
    List<MapValues> mapValues

    //=== Frontend only properties
    @Translatable(alias = "regionHelp")
    Map<String,String> regionHelp
    List<String> regionBuildingElementGroups
    List<String> showOptionalStructuralElements
    String regionDefaultStructuralFrame
    List<String> regionAlternativeStructuralFrame

    static embedded = ['customSection', 'mapValues']
    static transients = []
}
