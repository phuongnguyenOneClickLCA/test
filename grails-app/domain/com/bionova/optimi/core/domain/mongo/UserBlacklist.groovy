package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class UserBlacklist implements Serializable {

    static mapWith = "mongo"

    ObjectId id
    String suffix
    String prefix
    String ruleAddedBy
    Date timeAdded

    static constraints = {
        suffix nullable: false
        prefix nullable: true
        ruleAddedBy nullable: true
        timeAdded nullable: true

    }

}
