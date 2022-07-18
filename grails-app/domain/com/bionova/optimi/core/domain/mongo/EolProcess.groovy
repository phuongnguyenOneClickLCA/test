package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil

import java.lang.reflect.Method

class EolProcess {
    String eolProcessId
    String eolProcessName
    String eolProcessClass

    String eolProcessNameEN
    String eolProcessNameES
    String eolProcessNameSE
    String eolProcessNameIT
    String eolProcessNameNO
    String eolProcessNameFR
    String eolProcessNameDE
    String eolProcessNameFI
    String eolProcessNameNL

    String c2ResourceId
    Double c2Distance_km
    String c3ResourceId
    Double c3ResourceMultiplier
    String c3ConversionType
    String c4ResourceId
    Double c4ResourceMultiplier
    String c4ConversionType
    String dResourceId
    Double dResourceMultiplier
    String dConversionType

    String epdConversionType //EPD transient used in calculation

    Boolean active
    String fileName

    static mapWith = "mongo"

    static transients = ['epdConversionType']

    static mapping = {
        eolProcessId index: true
    }

    static constraints = {
        c2ResourceId nullable: true
        c2Distance_km nullable: true
        c3ResourceId nullable: true
        c3ResourceMultiplier nullable: true
        c3ConversionType nullable: true
        c4ResourceId nullable: true
        c4ResourceMultiplier nullable: true
        dResourceId nullable: true
        dResourceMultiplier nullable: true
        dConversionType nullable: true
        c4ConversionType nullable: true
        fileName nullable: true
        active nullable: true
        epdConversionType nullable: true
        eolProcessNameEN nullable: true
        eolProcessNameES nullable: true
        eolProcessNameSE nullable: true
        eolProcessNameIT nullable: true
        eolProcessNameNO nullable: true
        eolProcessNameFR nullable: true
        eolProcessNameDE nullable: true
        eolProcessNameFI nullable: true
        eolProcessNameNL nullable: true
        eolProcessClass(validator: { val, obj ->
            if (val) {
                if (!["disposal", "recycling", "backfilling", "downcycling", "energyrecovery", "reuse"].contains(val)) {
                    return ["eolProcessClass: ${val} not in: [disposal, recycling, backfilling, downcycling, energyrecovery, reuse]"]
                }
            }
        }, nullable: true)
    }

}
