/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class Configuration implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String name
    String value

    static constraints = {
        name nullable: false
        value nullable: false
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    int compareTo(Object obj) {
        if (obj && obj instanceof Configuration) {
            if (this.name) {
                this.name.compareTo(obj.name)
            } else {
                return 1
            }
        } else {
            return 1
        }
    }
}
