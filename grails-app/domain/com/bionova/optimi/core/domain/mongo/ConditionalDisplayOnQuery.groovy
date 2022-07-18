package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ConditionalDisplayOnQuery {
    static mapWith = "mongo"

    String method
    String resolveMethod
    List<String> additionalQuestionIds

    static constraints = {
        method inList: Constants.ConditionalDisplayMethod.values()*.name
        resolveMethod inList: Constants.ConditionalDisplayResolveMethod.values()*.name
        additionalQuestionIds nullable: false, minSize: 1
    }
}
