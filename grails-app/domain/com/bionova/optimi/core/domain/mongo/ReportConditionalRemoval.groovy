package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ReportConditionalRemoval {

    /*
        Defines the conditions to remove a section of the report between the removalMarkerStart and removalMarkerEnd
     */

    String removalRuleId
    String conditionTrigger // Constants ConditionTriggerReportRemoval: "queryValue" | "licenseKey"
    ReportConditionalRemovalQueryValue queryValue // need this if conditionTrigger == "queryValue"
    String licenseKey // need this if conditionTrigger == "licenseKey"
    String removalMarkerStart // must be unique
    String removalMarkerEnd // must be unique

    static constraints = {
        removalRuleId nullable: false
        licenseKey nullable: true
        queryValue nullable: true
        conditionTrigger nullable: false
        removalMarkerStart nullable: false
        removalMarkerEnd nullable: false
    }

    static embedded = ['queryValue']
}
