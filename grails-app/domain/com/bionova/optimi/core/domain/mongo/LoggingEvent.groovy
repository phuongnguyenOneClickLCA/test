package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * Created by pmm on 19.1.2016.
 */
@GrailsCompileStatic
class LoggingEvent {
    static mapWith = "mongo"
    ObjectId id
    String type // "exception" or "quota"
    String exception
    Date timestamp
}
