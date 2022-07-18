package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DElementGroup implements Serializable {

    String elementGroupId
    @Translatable(alias = "name")
    Map<String,String> elementGroupName
    String matchingDesignScope
    List<String> includedElements

    static transients = []
}
