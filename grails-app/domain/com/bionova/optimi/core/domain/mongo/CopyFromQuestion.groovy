package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CopyFromQuestion {
    static mapWith = "mongo"
    String queryId
    String sectionId
    String questionId
    Boolean copyFromParent
    String customCopy
}
