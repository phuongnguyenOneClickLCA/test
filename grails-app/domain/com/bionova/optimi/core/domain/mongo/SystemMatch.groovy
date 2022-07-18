package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SystemMatch {
    String systemMatchId
    String systemTrainingData
    String resourceId
    String profileId

    static mapWith = "mongo"

    static transients = [
            "resource",
            "active"
    ]

    Resource getResource() {
        Resource resource
        if (resourceId && profileId) {
            resource = Resource.findByResourceIdAndProfileIdAndActive(resourceId, profileId, true)

            if (!resource) {
                resource = Resource.findByResourceIdAndProfileId(resourceId, profileId)
            }
        } else if (resourceId) {
            resource = Resource.findByResourceIdAndActive(resourceId, true)

            if (!resource) {
                resource = Resource.findByResourceId(resourceId)
            }
        }
        return resource
    }

    Boolean getActive() {
        return getResource()?.active
    }
}
