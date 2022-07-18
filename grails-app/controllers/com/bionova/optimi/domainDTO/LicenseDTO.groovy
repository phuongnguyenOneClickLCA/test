package com.bionova.optimi.domainDTO

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class LicenseDTO {
    ObjectId id
    String name
    List<String> compatibleEntityClasses
    Boolean planetary
}
