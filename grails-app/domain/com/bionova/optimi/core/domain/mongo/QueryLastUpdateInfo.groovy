package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class QueryLastUpdateInfo {
    List<String> usernames
    List<Date> updates
}
