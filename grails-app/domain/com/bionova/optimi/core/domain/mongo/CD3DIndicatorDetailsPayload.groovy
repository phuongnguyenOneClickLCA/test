package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CD3DIndicatorDetailsPayload implements Serializable {

    String indicatorId
    String indicatorName
    String classificationId
    String classificationName
    String classificationHelp
    Map<String, String> choices
    Boolean inheritToChildren

    static constraints = {
        indicatorId nullable: false
        indicatorName nullable: false
        classificationId nullable: true
        classificationName nullable: true
        classificationHelp nullable: true
        choices nullable: true
        inheritToChildren nullable: false
    }
}
