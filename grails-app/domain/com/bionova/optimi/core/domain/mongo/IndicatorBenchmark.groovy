package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class IndicatorBenchmark {

    static mapWith = "mongo"

    ObjectId id
    List<String> areas
    String buildingType // DEPRECATED, USE buildingTypes
    List<String> buildingTypes
    List<String> indicatorIds // DEPRECATED, indicator defines if benchmarks allowed.
    String appliedDenominator
    String benchmarkId
    String benchmarkName
    String benchmarkSourceInformation
    String benchmarkDescription
    Double indexG
    Double indexF
    Double indexE
    Double indexD
    Double indexC
    Double indexB
    Double indexA
    Double indexMin
    Double average
    String fileName
    Double sampleSize
    String externalLink

    Date benchmarkDate
    String benchmarkType

    static hasMany = [indicatorIds: String, areas: String, buildingTypes: String]

    static constraints = {
        areas nullable: true
        buildingType nullable: true
        appliedDenominator nullable: true
        fileName nullable: true
        buildingTypes nullable: true
        average nullable: true
        benchmarkSourceInformation nullable: true
        benchmarkDate nullable: true
        benchmarkType nullable: true
        benchmarkDescription nullable: true
        sampleSize nullable: true
        externalLink nullable: true
    }
}
