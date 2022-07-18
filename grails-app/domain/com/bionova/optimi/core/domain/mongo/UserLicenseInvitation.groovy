package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class UserLicenseInvitation {

    static mapWith = "mongo"

    ObjectId id
    String email
    String licenseId
}
