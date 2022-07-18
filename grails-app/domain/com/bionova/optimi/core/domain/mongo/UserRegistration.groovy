/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class UserRegistration {
    static mapWith = "mongo"
    ObjectId id
    String username
    String token = UUID.randomUUID().toString().replaceAll('-', '')
    Date dateCreated
    
    static mapping = { version false }

    def beforeInsert() {
        dateCreated = new Date()
    }
}
