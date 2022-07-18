/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesignerDefaultValueConstituents {
    static mapWith = "mongo"
    String defaultValueElement
    String defaultValueResource
    String savingMethod
    String unit
    String quantitySource
    Double quantityMultiplier
    Map<String, String> target
}
