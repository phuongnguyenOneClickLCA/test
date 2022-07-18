package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class PredefinedScopeAndQuery implements Serializable, Validateable {

    static mapWith = "mongo"

    @Translatable
    Map<String, String> scopeName
    String epdScopeId
    List<String> includedStages
    String queryId

    static embedded = ['includedStages']

    static constraints = {
        epdScopeId nullable : true
        queryId nullable : true
    }

    static transients = []

}
