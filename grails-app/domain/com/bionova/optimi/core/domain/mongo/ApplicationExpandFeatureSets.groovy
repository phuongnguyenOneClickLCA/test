package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by Miika on 23.2.2017.
 */
@GrailsCompileStatic
class ApplicationExpandFeatureSets {
    static mapWith = "mongo"
    String name
    IndicatorExpandFeature expandFeatures

    static embedded = ["expandFeatures"]
}
