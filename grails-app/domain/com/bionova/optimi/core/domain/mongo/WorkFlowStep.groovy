package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class WorkFlowStep implements Serializable, Validateable {

    static mapWith = "mongo"

    String stepId
    Map<String, String> name
    Boolean checked
    Map<String, String> help
    Boolean isExpanded
    String note
    String itemId
    String actionToTake
    String observe
    String toNextScreen
    String disableReset


    String localizedName
    String localizedHelp

    static constraints = {
        name nullable: true
        help nullable: true
        isExpanded nullable: true
        note nullable: true
        itemId nullable: true
        actionToTake nullable: true
        observe nullable: true
        toNextScreen nullable: true
        disableReset nullable: true
        localizedName nullable: true
        localizedHelp nullable: true
    }
}
