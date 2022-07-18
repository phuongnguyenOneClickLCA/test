package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.apache.commons.lang.time.DateUtils

@GrailsCompileStatic
class LicenseJoinCode {

    String code
    Date dateCreated

    static constraints = {
        code minSize: 6, unique: true
        dateCreated nullable: true
    }

    static transients = ['valid']

    Boolean getValid() {
        Boolean valid = Boolean.FALSE

        if (dateCreated && DateUtils.addDays(dateCreated, 30).after(new Date())) {
            valid = Boolean.TRUE
        }
        return valid
    }
}
