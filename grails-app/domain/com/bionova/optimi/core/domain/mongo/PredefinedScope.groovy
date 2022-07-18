package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class PredefinedScope implements Serializable, Validateable {

    static mapWith = "mongo"

    @Translatable
    Map<String, String> name
    String scopeId
    String scopeQuestionId
    List<String> compatibleIndicatorList
    String projectTypeDefault
    String frameTypeDefault
    List<String> lcaCheckerDefaultList
    String lcaCheckerQuestionId
    ReportIncompleteWarning incompleteWarning
    List<String> epdScopeList

    static embedded = ['compatibleIndicatorList', 'lcaCheckerDefaultList', 'incompleteWarning', 'epdScopeList']

    static constraints = {

        projectTypeDefault              nullable : true
        frameTypeDefault                nullable : true
        lcaCheckerDefaultList           nullable : true
        lcaCheckerQuestionId            nullable : true
        incompleteWarning               nullable : true
        epdScopeList                    nullable : true

    }

    static transients = []

}
