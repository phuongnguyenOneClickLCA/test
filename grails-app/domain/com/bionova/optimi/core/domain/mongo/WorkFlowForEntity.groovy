package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class WorkFlowForEntity implements Serializable, Validateable {

    static mapWith = "mongo"

    ObjectId id

    String entityId
    //String defaultIndicatorId

    String defaultWorkFlowId
    List<WorkFlow> workFlowList

    static embedded = ['workFlowList']

}