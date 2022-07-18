package com.bionova.optimi.ui

import com.bionova.optimi.core.service.VerificationPointService
import groovy.transform.CompileStatic


// Verification Point Location
@CompileStatic
class VPLocation {
    /**
     * At one place (a question or section), the config can define multiple verification points.
     * This whole list is considered as a group vPoints at that place >>> groupName. {@link VerificationPointService#getGroupedVpoints}
     */
    String groupName
    String id
    String queryId
    String queryName
    String localizedName
    String url

    VPLocation() {
        id = UUID.randomUUID().toString()
        groupName = new String()
        queryId = new String()
        localizedName = new String()
        url = new String()
    }

}
