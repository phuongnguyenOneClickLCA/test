package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BenchmarkResult {
    Map<String, Integer> validValues
    Map<String, Double> average
    Map<String, Double> min
    Map<String, Double> max
    Map<String, Double> median
    Map<String, Double> standardDeviation
    Map<String, Double> variance
    Map<String, Integer> factor
    Map<String, Map<String, Double>> changesInCO2 // Inner map has keys min, max, avg and factor

    static mapWith = "mongo"
}
