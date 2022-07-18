/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class EntityTypeEnergyAllocation {

    List<String> resourceGroupList
    Map<String, String> target

}
