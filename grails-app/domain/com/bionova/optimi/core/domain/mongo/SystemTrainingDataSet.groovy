package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by miika on 4.11.2016.
 */
@GrailsCompileStatic
class SystemTrainingDataSet {
    static mapWith = "mongo"
    String fileName
    List<SystemMatch> systemMatches

    static constraints = {
        fileName nullable: true
        systemMatches nullable: true
    }

    static hasMany = [systemMatches: SystemMatch]

    static embedded = [
            "systemMatches"
    ]
}
