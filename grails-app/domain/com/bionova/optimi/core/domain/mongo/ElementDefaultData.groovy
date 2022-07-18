package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ElementDefaultData implements Serializable {

    List<String> buildingTypes
    List<String> regions
    String singleDefaultDataSet
    Map<String, String> defaultDataSetMap

}
