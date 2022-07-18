/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Ya boi
 */
@GrailsCompileStatic
class ApplyProcess implements Serializable {
    static mapWith = "mongo"
    String processingType
    String resourceIdSource
    String definitionSources
    String transportResourceSource
    String transportDistanceSource
    List<String> quantityMultiplierSource
    String applyDutchTransportFactorBy //deprecated
}


