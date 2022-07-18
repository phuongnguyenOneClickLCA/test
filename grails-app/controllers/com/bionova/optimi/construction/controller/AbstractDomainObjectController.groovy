/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import org.grails.datastore.mapping.core.OptimisticLockingException

/**
 * @author Pasi-Markus Mäkelä
 *
 * Abstract class for domain object controllers.
 */
abstract class AbstractDomainObjectController extends ExceptionHandlerController {

    def userService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "list"

    abstract list()

    abstract saveInternal()

    def save() {
        try {
            saveInternal()
        } catch (OptimisticLockingException e) {
            flash.errorAlert = message(code: "error.optimisticLocking")
            redirect controller: "main"
        }
    }

    protected getUser() {
        return userService.getCurrentUser()
    }
}
