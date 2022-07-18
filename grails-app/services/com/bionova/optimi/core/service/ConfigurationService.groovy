/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.User
import grails.plugin.springsecurity.annotation.Secured

import javax.servlet.http.HttpServletRequest

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class ConfigurationService {

    private static boolean configurationsUpdated = false
    private static configurations
    // the server domain name of application
    static String serverName = null

    def userService
    def shellCmdService

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def dbBackUp() {
        User user = (User) userService.getCurrentUser()
        def response

        if (userService.isSystemAdmin(user)) {
            response = shellCmdService.executeShellCmd("/home/maintenance/db_backup.sh")
        }
        return response
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def zippedDbBackup() {
        User user = userService.getCurrentUser()
        def response

        if (userService.isSystemAdmin(user)) {
            response = shellCmdService.executeShellCmd("/home/maintenance/manual_backup.sh")
        }
        return response
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Configuration getById(String id) {
        Configuration.get(id)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAll() {
        return getConfigurations()
    }

    /**
     * Old method
     * @param applicationId not used anymore
     * @param configurationName
     * @return
     */
    @Deprecated
    @Secured(["ROLE_AUTHENTICATED"])
    String getConfigurationValue(String applicationId, String configurationName) {
        return getConfigurations()?.find { it.name == configurationName }?.value
    }

    /**
     * New method
     * @param configurationName
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    String getConfigurationValue(String configurationName) {
        return getConfigurations()?.find { it.name == configurationName }?.value
    }

    Map<String, String> getMultipleConfigurationValues(List<String> configurationNames) {
        Map<String, String> all = [:]
        getConfigurations()?.findAll { it.name in configurationNames }?.each { all.put(it.name, it.value) }
        return all
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Configuration getByConfigurationName(applicationId = null, configurationName) {
        if (configurationName) {
            return getConfigurations().find({
                it.name == configurationName
            })
        }
        return null
    }

    def removeConfiguration(applicationId = null, String configurationName) {
        Configuration configuration = getByConfigurationName(configurationName)

        if (configuration) {
            configuration.delete(flush: true)
            configurationsUpdated = true
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Configuration updateConfiguration(Configuration configuration) {
        if (!configuration?.id) {
            throw new RuntimeException("Cannot update ApplicationConfiguration that is not yet persisted!")
        }
        configurationsUpdated = true
        configuration.merge(flush: true, failOnError: true)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def createConfiguration(Configuration configuration) {
        configurationsUpdated = true
        configuration.save(failOnError: true)
    }

    private List<Configuration> getConfigurations() {
        if (!configurations || isConfigurationsUpdated()) {
            List i = Configuration.list()

            if (i) {
                configurations = new ArrayList<Configuration>(i)
            }
        }
        return configurations
    }

    private boolean isConfigurationsUpdated() {
        if (configurationsUpdated) {
            configurationsUpdated = false
            return true
        } else {
            return false
        }
    }

    void updateServerNameConfig(HttpServletRequest request) {
        if (!serverName) {
            serverName = request?.serverName
        }
    }
}
