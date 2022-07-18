package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class LcaCheckerResult {
    String letter
    String color
    Integer percentage

    Map<String, Double> resultByCheck
    List<String> passed
    List<String> acceptable
    List<String> failed

    String toString() {
        return "[letter: " + letter + ", color: " + color + ", percentage: " + percentage + ", resultByCheck: " + resultByCheck + ", passed: " + passed + ", acceptable: " + acceptable + ", failed: " + failed + "]"
    }
}
