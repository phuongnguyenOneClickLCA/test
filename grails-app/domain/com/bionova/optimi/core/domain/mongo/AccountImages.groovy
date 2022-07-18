package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class AccountImages {

    ObjectId id
    String name
    String type
    String style
    String accountId
    byte[] image

    static mapWith = "mongo"
    static constraints = {
        name unique: true
    }
}
