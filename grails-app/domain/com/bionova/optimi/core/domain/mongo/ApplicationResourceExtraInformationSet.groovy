package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by miika on 4.11.2016.
 */
@GrailsCompileStatic
class ApplicationResourceExtraInformationSet {
    static mapWith = "mongo"
    String name
    Map<String, Map> resourceExtraInformation
    String resourceInformationId // for data card config
    List<DataCardSection> resourceInformation // for data card config
    List<String> addToDataSources
    List<String> showResultCategory

    static embedded = ['resourceInformation']

    static constraints = {
        name nullable: true
        resourceExtraInformation nullable: true
        resourceInformationId nullable: true
        resourceInformation nullable: true
        addToDataSources nullable: true
        showResultCategory nullable: true
    }
}
