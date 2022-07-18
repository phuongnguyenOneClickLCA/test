package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class RoleMigrationMapping implements Serializable, Validateable {
    static mapWith = "mongo"

    String username
    String targetRole

    static constraints = {
        targetRole blank: false, nullable: false
        username blank: false, nullable: false, unique: true
    }
}
