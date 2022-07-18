package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SpecialRule {

    String specialRuleId
    String name
    String type
    String requiredQuantityType
    String unit
    Map<String, Map<String, String>> mappingByDataproperty
    Integer min
    Integer max

    Resource getResourceByDataproperty(String dataproperty) {
        Resource resource

        if (mappingByDataproperty && dataproperty) {
            Map<String, String> resourceIdAndProfileId = mappingByDataproperty.get(dataproperty)

            if (resourceIdAndProfileId) {
                String resourceId = resourceIdAndProfileId.resourceId
                String profileId = resourceIdAndProfileId.profileId

                if (resourceId && profileId) {
                    resource = Resource.findByResourceIdAndProfileIdAndActive(resourceId, profileId, true)
                }
            }
        }
        return resource
    }

}
