package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourceCompositePart {
    static mapWith = "mongo"

    String resourceId
    String profileId
    Double quantity
    Integer partNo
}
