package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class WorkFlow implements Serializable, Validateable {

    static mapWith = "mongo"

    String workFlowId
    Map<String, String> name
    String locName
    String localizedName
    List<String> indicatorIdList
    List<WorkFlowStep> stepList
    String workFlowType

    static embedded = ['indicatorIdList', 'stepList']
}