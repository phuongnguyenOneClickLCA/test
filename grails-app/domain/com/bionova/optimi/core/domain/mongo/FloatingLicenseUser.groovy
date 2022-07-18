package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class FloatingLicenseUser {
    String userId
    String name
    String username
    Date checkedIn

    static constraints = {
        userId nullable: true
        username nullable: true
        checkedIn nullable: true
    }
}
