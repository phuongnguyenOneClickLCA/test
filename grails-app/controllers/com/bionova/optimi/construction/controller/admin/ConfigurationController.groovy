/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.Configuration
import grails.plugin.springsecurity.annotation.Secured

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
class ConfigurationController extends ExceptionHandlerController {

    def configurationService

    def form() {
        def configurations = configurationService.getAll()
        [configurations: configurations]
    }

    def save() {
        Configuration configuration

        if(params.id) {
            configuration = configurationService.getById(params.id)
            if (!configuration) {
                flashService.setErrorAlert("No configuration is found with id ${params.id}")
            } else {
                configuration.name = params.name
                configuration.value = params.value
                configurationService.updateConfiguration(configuration)
            }
        } else {
            configuration = new Configuration(params)
            configurationService.createConfiguration(configuration)
        }
        redirect action: "form"
    }

    def list() {
        // Do nothing, just implemented because of AbstractDomainObjectController
    }
}
