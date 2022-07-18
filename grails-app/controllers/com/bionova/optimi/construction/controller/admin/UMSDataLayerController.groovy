package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.User
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class UMSDataLayerController{

    def userService
    def entityService
    def licenseService

    @Secured(["ROLE_AUTHENTICATED"])
    def getUser() {
        User user = userService.getCurrentUser(true)

        render ([user: user] as JSON)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getLicenses(){
        def request = request.JSON
        List<String> licenseIds = request.licenseIds
        Boolean alsoFloatingLicenses = request.alsoFloatingLicenses

        List<License> licenses = licenseService.getLicensesByIds(licenseIds, alsoFloatingLicenses)

        render ([licenses: licenses] as JSON)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getOrganization(){
        String userId = params.get('userId')
        User user = userService.getUserById(userId)

        Account account = user?.getAccount()
        render ([organization: account] as JSON)
    }


    //TODO: discuss the json size
    @Secured(["ROLE_AUTHENTICATED"])
    def getEntities(){
        def request = request.JSON
        List<String> entityIds = request.entityIds

        List <Entity> entities = entityService.getEntitiesByIds(entityIds, null)

        render([entities: entities] as JSON)
    }

}
