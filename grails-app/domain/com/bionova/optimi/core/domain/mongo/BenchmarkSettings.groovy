package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Trang Le
 */
@GrailsCompileStatic
class BenchmarkSettings implements Serializable {
    static mapWith = "mongo"
    String displayRuleId
    String tableHeading
    Double planetaryBenchmarkMargin
}
