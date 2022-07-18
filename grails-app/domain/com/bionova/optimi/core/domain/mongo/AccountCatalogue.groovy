package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class AccountCatalogue {

    static mapWith = "mongo"

    ObjectId id
    String slug
    String name
    List<String> countries

    static constraints = {
        slug unique: true
    }
}
