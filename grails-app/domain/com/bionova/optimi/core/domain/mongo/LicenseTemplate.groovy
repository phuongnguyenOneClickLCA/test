/*
 * Copyright (c) 2018 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class LicenseTemplate {
    static mapWith = "mongo"
    ObjectId id
    String name
    List licensedFeatureIds = []
    List licensedIndicatorIds = []
    List<String> licensedWorkFlowIds = []
    List<String> publicProjectTemplateIds
    String defaultPublicProjectTemplateId

    static constraints = {
        name nullable: false, blank: false
        publicProjectTemplateIds nullable: true
        defaultPublicProjectTemplateId nullable: true
    }
}
