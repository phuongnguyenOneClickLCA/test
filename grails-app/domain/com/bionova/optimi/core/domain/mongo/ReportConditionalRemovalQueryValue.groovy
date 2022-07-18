package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ReportConditionalRemovalQueryValue {

    /*
        Defines the condition of the query's value to remove a section in the report
     */

    String entity // Constants EntityType: "parent" | "child"
    String queryId
    String sectionId
    String questionId
    String additionalQuestionId
    String condition // Constants ValueEvaluation: "empty" | "notEmpty" | "positive" | "negative" | "equal" | "notEqual"
    String refValue // needed if condition is "equal" | "notEqual"

    static constraints = {
        entity nullable: false
        queryId nullable: true
        sectionId nullable: true
        questionId nullable: true
        additionalQuestionId nullable: true
        condition nullable: false
        refValue nullable: true
    }
}
