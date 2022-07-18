/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class EntitySummaryData implements Serializable {
   static mapWith = "mongo"
   String entityClass
   List<String> displayName
   List<ComboField> comboFields
    List<String> designPFName
    List<String> operatingPFName
    List<String> designPFAnon
    List<String> operatingPFAnon
    List<String> shortName

    static embedded = ['comboFields']
}
