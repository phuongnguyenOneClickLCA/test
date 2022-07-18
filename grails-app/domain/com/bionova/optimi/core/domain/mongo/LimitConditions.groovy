/*
 *
 * Copyright (c) 2021 by Bionova Oy
 */
package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
/**
 * @author Annie Simon
 * Intent is to limit choices user can make based on answers they gave in previous chocies.
 */
@GrailsCompileStatic
class LimitConditions implements Serializable, Validateable {
    static mapWith = "mongo"
    /** if this keyQuestionId and valueQuestionId match with excluding conditions, then the answer id will be hidden */
    String keyQuestionId
    String valueQuestionId
    /** List that contains excludingConditions for choices which can be matched with key and value questionIds */
    List<Map<String, List<String>>> excludingConditions

    static constraints = {
        keyQuestionId nullable: true
        valueQuestionId nullable: true
        excludingConditions nullable: true
    }
}