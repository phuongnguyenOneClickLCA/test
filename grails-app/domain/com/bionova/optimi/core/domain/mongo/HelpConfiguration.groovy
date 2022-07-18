package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class HelpConfiguration {
    static mapWith = "mongo"
    String targetPage
    String contentType
    String contentNameFI
    String contentNameEN
    String contentNameNO
    String contentNameDE
    String contentNameFR
    String contentNameNL
    String contentNameES
    String contentNameSE
    String contentNameIT

    String additionalTextEN
    String additionalTextES
    String additionalTextSE
    String additionalTextIT
    String additionalTextFI
    String additionalTextNO
    String additionalTextDE
    String additionalTextFR
    String additionalTextNL
    String queryId
    String indicatorId
    String contentURL
    ObjectId id
    String helpConfigurationId


    static constraints = {
        targetPage nullable: true
        contentType nullable: true
        contentURL nullable: true
        helpConfigurationId nullable: true
        contentNameEN nullable: true
        contentNameFI nullable:true
        contentNameNO nullable: true
        contentNameDE nullable: true
        contentNameFR nullable: true
        contentNameNL nullable: true
        contentNameES nullable: true
        contentNameSE nullable: true
        contentNameIT nullable: true
        additionalTextEN nullable: true
        additionalTextES nullable: true
        additionalTextFI nullable: true
        additionalTextNO nullable: true
        additionalTextDE nullable: true
        additionalTextFR nullable: true
        additionalTextNL nullable: true
        additionalTextSE nullable: true
        additionalTextIT nullable: true

        queryId nullable:true
        indicatorId nullable:true
    }

    static transients = []

}
