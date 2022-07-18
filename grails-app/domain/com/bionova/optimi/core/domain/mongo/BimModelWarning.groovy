package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BimModelWarning {
    static mapWith = "mongo"
    String issueClass
    String helpText
    String status
    Integer amount
    Integer amountOfCombined
    Double share
    List<String> datasetIds
    Boolean dontShowInVisuals

    static constraints = {
        issueClass nullable: true
        helpText nullable: true
        status nullable: true
        amount nullable: true
        share nullable: true
        datasetIds nullable:true
        dontShowInVisuals nullable:true
        amountOfCombined nullable: true
    }

    static transients = ['priority', 'recommendedAction']

    Integer getPriority() {
        Integer priority = -1
        if (status) {
            priority = status.equalsIgnoreCase("ERROR") ? 1 : status.equalsIgnoreCase("WARN") ? 2 : status.equalsIgnoreCase("UNIDENTIFIED") ? 3 : status.equalsIgnoreCase("INFO") ? 4 : -1
        }
        return priority
    }

    String getRecommendedAction() {
        String recommendedAction
        if (issueClass) {
            if (issueClass.equalsIgnoreCase("Implausible thickness")) {
                recommendedAction = "Check or correct defined thicknesses"
            } else if (issueClass.equalsIgnoreCase("Other errors")) {
                recommendedAction = "Verify highlighted datapoints"
            } else if (issueClass.equalsIgnoreCase("Invalid quantity")) {
                recommendedAction = "Make sure quantity and unit are present and quantity contains only numeric values"
            } else if (issueClass.equalsIgnoreCase("Generic definitions")) {
                recommendedAction = "Add more specific information about materials or map datapoints manually"
            } else if (issueClass.equalsIgnoreCase("Unidentified data")) {
                recommendedAction = "Map datapoints manually for the first time"
            } else if (issueClass.equalsIgnoreCase("UNCONVENTIONAL CLASS"))  {
                recommendedAction = "Review material classes"
            }  else if (issueClass.equalsIgnoreCase("UNCONVENTIONAL UNIT"))  {
                recommendedAction = "Review and correct material units"
            } else if(issueClass.equalsIgnoreCase("Out of scope")) {
                recommendedAction = "No action. Filter settings available in additional settings"
            } else if (issueClass.equalsIgnoreCase("Non-material objects")) {
                recommendedAction = "No action. These are automatically removed"
            } else {
                recommendedAction = "Verify highlighted datapoints"
            }
        }
        return recommendedAction
    }

}
