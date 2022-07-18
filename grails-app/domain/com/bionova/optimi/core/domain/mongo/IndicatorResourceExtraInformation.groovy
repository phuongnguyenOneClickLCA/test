package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by miika on 24.10.2016.
 */
@GrailsCompileStatic
class IndicatorResourceExtraInformation {
    String usage
    Map<String, String> applicationExtraInformation
}
