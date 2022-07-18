package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class MapValues implements Serializable {

    String value
    String queryId
    String sectionId
    String questionId
    String resourceId
    String profileId
    String additionalQuestionId
    String comment
    Set<String> showQuestionForBuildingTypes

    static constraints = {
        value nullable: true
        resourceId nullable: true
        profileId nullable: true
        additionalQuestionId nullable: true
        comment nullable: true
    }
}
