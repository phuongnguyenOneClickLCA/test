package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class UserDataRequest {
    ObjectId id
    Date created
    String name
    String email
    String entity
    String dataRequestLink
    String subject
    String message
    String filePath

    static constraints = {
        created nullable: true
        name nullable: true
        email nullable: true
        entity nullable: true
        dataRequestLink nullable: true
        subject nullable: true
        message nullable: true
        filePath nullable: true
    }
}
