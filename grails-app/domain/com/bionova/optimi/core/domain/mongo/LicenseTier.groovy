package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class LicenseTier {

    static mapWith = "mongo"

    ObjectId id
    String name
    Boolean deleted = false

    static constraints = {
        name nullable: false
        deleted nullable: true
    }
}
