/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ClassRemapping implements Serializable, Validateable {
    String remappingQuery
    String remappingEntityClass
    List<String> allowedClassRemappings
    List<String> allowedDescriptiveClassRemappings
    Map<String, String> automaticClassRemappings

    static mapWith = "mongo"
}
