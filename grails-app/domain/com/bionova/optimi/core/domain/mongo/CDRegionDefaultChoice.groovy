package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class CDRegionDefaultChoice implements Serializable, Validateable {

    String defaultChoiceId
    @Translatable
    Map<String,String> name
    String materialType
    String energyScenario
    List<String> compatibleBuildingTypeList
    List<String> defaultForBuildingTypes
    List<String> alternativeForBuildingTypes

    static transients = []

}
