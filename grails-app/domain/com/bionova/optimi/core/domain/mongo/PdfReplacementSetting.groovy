package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by pmm on 27/04/16.
 */
@GrailsCompileStatic
class PdfReplacementSetting {
    static mapWith = "mongo"
    Map<String, String> replacePages
    ValueReference sourceDocument

    static embedded = ['sourceDocument']
}
