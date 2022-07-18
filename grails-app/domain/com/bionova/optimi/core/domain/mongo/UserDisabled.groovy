package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserDisabled {

    Date time
    String reason
    String disabledBy

    static mapWith = "none"

}
