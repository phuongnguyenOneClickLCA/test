/*
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class ApplicationIndicatorDefinitions implements Serializable, Validateable {
    static mapWith = "mongo"

    String indicatorSettingsId
    String indicatorUse
    String assessmentMethod
    String displayResult
    String connectedBenchmark
    String groupingClassificationParamId
    List<String> connectedBenchmarks
    Boolean enableLcaChecker
    Boolean enableCarbonHeroesBenchmarks
    Map<String, Object> wordReportGeneration
    List<String> allowedCarbonDesignerRegions

    List<String> compatibleEntityClasses
    List<String> queriesForSourceListing
    List<String> reportVisualisation

    ResultFormatting resultFormatting

    static embedded = [
            'resultFormatting'
    ]
}
