/*
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class Role implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String authority
    

    static constraints = {
        authority blank: false, unique: true
    }
}
