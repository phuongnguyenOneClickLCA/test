package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.ToString
import groovy.transform.TypeCheckingMode

@ToString
@GrailsCompileStatic
class SimulationToolConstituent implements Serializable, Validateable {
    static mapWith = "mongo"

    String manualId
    Double co2e

    String name
    String groupId
    String groupName
    Double amount

    Double thickness

    Boolean defaultConstituent
    Boolean allowVariableThickness
    String userGivenUnit

    //Show and allow to change the thickness if allowVariableThickness is true and userGivenUnit is m2 [It's possible that m2 are not the only value acceptable but also for ex. m3]
    Boolean modThickness

    //For the question mark
    String resourceId
    String profileId
    String queryId
    String sectionId
    String questionId

    String comment

    transient optimiResourceService

    static transients = [
            "resource"
    ]

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Resource getResource() {
        if (resourceId) {
            if (profileId) {
                return optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
            } else {
                return optimiResourceService.getResourceByResourceAndProfileId(resourceId, null)
            }
        } else {
            return null
        }
    }

    Double setAmount(Double amount) {
        this.amount = amount
        if (amount == 0) {
            this.co2e = 0
        }
    }

}
