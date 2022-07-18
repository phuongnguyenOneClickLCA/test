/*
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class BenchmarkValue implements Comparable, Serializable {
    static mapWith = "mongo"
    ObjectId id
    // Double average
    Portfolio portfolio
    String type  // design / operating

    static transients = ["name"]

    transient portfolioService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getName() {
        String name
        Entity entity = portfolioService.getEntity(portfolio?.entityId)

        if (entity) {
            name = "Benchmark: " + entity.name
        }
        return name
    }

    @Override
    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    public int compareTo(Object o) {
        if (o && o instanceof BenchmarkValue) {
            return this.name?.compareTo(o.name)
        } else {
            return 1
        }
    }
}
