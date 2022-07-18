package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DimensioningVariable implements Serializable, Validateable {

    static mapWith = "mongo"

    List<String> countryIds
    Map<String, Double> coolingkWtom2GFARatio
    Map<String, Double> heatingkWtom2GFARatio
    Map<String, Double> loadBearingInternalWallShare
    Boolean applyAsDefault

}