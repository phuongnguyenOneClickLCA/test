/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import grails.plugin.springsecurity.annotation.Secured

/**
 * @author Pasi-Markus Mäkelä
 */
@Secured(["ROLE_SYSTEM_ADMIN"])
class DomainClassController extends ExceptionHandlerController {

    def list() {
        def domainClasses = grailsApplication.domainClasses?.clazz
        def domainObjects = [ : ]
        def okCounter = 0
        def errorCounter = 0
        def errorClasses = []
        def objects

        domainClasses?.each {
            try {
                objects = it.list()

                if(objects) {
                    okCounter = okCounter + objects?.size()
                    domainObjects.put(it, objects)
                }
            } catch (Exception e) {
                errorClasses.add(it)
                errorCounter += errorCounter
            }
        }

        if (errorClasses && errorCounter > 0) {
            flash.fadeErrorAlert = "Some of the data in the database could not be loaded: " + errorClasses.each { it + ", "}
        } else {
            flash.fadeSuccessAlert = "All of the data in the database was loaded: " + okCounter + " objects"
        }
        [domainObjects: domainObjects, keys: domainObjects?.collect{it.key}]
    }
}
