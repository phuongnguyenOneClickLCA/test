package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CD3DBuildingElementsPayload implements Serializable {

    //Configuration Options
    String elementId
    Map<String, String> elementName
    String elementHelp
    String elementRequiredUnit

    //Populated during after design calculation
    Double elementTotalArea
    Double elementTotalShare
    Double elementTotalCo2eTons
    Double elementTotalCo2eShare

    static constraints = {
        elementId nullable: false
        elementName nullable: false
        elementRequiredUnit nullable: false
        elementHelp nullable: true
        elementTotalArea nullable: true
        elementTotalShare nullable: true
        elementTotalCo2eTons nullable: true
        elementTotalCo2eShare nullable: true
    }
}
