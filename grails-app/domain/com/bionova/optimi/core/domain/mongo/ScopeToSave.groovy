package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode

@GrailsCompileStatic
class ScopeToSave implements Serializable, Validateable {
    static mapWith = "mongo"

    String scopeId
    String frameType
    String projectType
    List<String> lcaCheckerList // choose a proper name

    static constraints = {
        scopeId nullable: true
        frameType nullable: true
        projectType nullable: true
        lcaCheckerList nullable: true
    }

    static transients = ["lcaCheckerMap"]

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def getLcaCheckerMap() {

        def map = [:]
        map = projectType ? map + ["projectType" : [projectType]] : map
        map = frameType ? map + ["frameType" : [frameType]] : map
        map = lcaCheckerList ? map + ["projectScope" : lcaCheckerList] : map

        return map
    }
}