package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Benchmark {
    String status
    Map<String, String> values
    Map<String, Integer> ranking
    Map<String, Integer> quintiles
    Map<String, Double> deviations
    Map<String, Double> factor
    Map<String, Double> factorDeviations
    Map<String, Map<String, Double>> resultByUnit
    Double co2Deviation //deprecated
    Integer co2Quintile //deprecated

    static mapWith = "mongo"

    static constraints = {
        co2Deviation nullable: true
        co2Quintile nullable: true
    }

    public String toString() {
        return "[status: " + status + ", values: " + values + ", ranking: " + ranking + ", quintiles: " + quintiles + ", deviations: " + deviations + ", factor: " + factor + ", resultByUnit: " + resultByUnit +"]"
    }
}
