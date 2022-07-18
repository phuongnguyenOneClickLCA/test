package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DBuildingElements implements Serializable {

    //-- Added on query upload
    String elementId
    @Translatable(alias = "name")
    Map<String,String> elementName
    Map<String,String> elementClassifications
    Map<String,String> elementSavingLocation
    String elementHelp
    String elementRequiredUnit
    String allowedContentType
    List<String> allowedSubTypes
    List<String> allowedResources
    List<ElementDefaultData> elementDefaultData

    static embedded = ["elementDefaultData"]

    static transients = []
}
