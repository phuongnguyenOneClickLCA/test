package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class CDEarthquakeSetting implements Serializable, Validateable {

    String earthquakeZone
    List<String> compatibleBuildingTypeList
    Map<String,Double> quantityMultiplier
    Map<String,Double> constructionShare

}
