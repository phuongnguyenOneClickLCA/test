package com.bionova.optimi

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.CalculationProcess
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.ShellCmdService
import com.mongodb.BasicDBObject
import grails.async.Promise
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.util.Environment
import org.springframework.beans.factory.annotation.Autowired

import static grails.async.Promises.task

class  BootStrap {

    def configurationService
    def indicatorService
    def queryService
    def featureService
    def eolProcessService
    def resourceTypeService
    def localizedLinkService
    def userService
    def consulRegistrationService
    def xmlService
    static String HOST_NAME
    ShellCmdService shellCmdService

    @Autowired
    EmailConfiguration emailConfiguration

    def init = { servletContext ->
        long now = System.currentTimeMillis()
        shellCmdService.initHostnameConstant()
        SpringSecurityUtils.clientRegisterFilter('hostValidationFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 1)
        SpringSecurityUtils.clientRegisterFilter('bearerTokenAuthenticationFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)
        SpringSecurityUtils.clientRegisterFilter('csrfFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 20)

        //kpiReportService.sendWeeklyKPIReport()
        /*
        Example for creating a new licenseKey
        featureService.createOrUpdateFeature(Feature.EXAMPLE, com.bionova.optimi.core.Constants.LicenseFeatureClass.EPD.toString(), 'This is an example to create license key')
        */
        CalculationProcess.collection.remove(new BasicDBObject());

        if (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM) {
            /* Do not remove */

            Promise p = task {
                userService.obfuscateUsersDetails()
                xmlService.transferRsetMappingToNewField()
                indicatorService.getAllActiveIndicators()
                resourceTypeService.getActiveResourceTypes()
                eolProcessService.getAllEolProcesses()
                queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                localizedLinkService.getAllLinks()

                Configuration logUrl = configurationService.getByConfigurationName(Constants.APPLICATION_ID, Constants.ConfigName.LOG_URL.toString())

                if (logUrl?.value?.contains("www.360optimi.com")) {
                    logUrl.value = logUrl.value.replace("www.360optimi.com", "80.69.162.100")
                    configurationService.updateConfiguration(logUrl)
                }
                User user = User.findByUsername("xautotest@testtest.com")

                if (user) {
                    user.testRobot = Boolean.TRUE
                    user.merge(flush: true)
                }
                log.info("BOOTSTRAP ASYNC DONE!")

            }
            p.onError { Throwable err ->
                log.error("BOOTSTRAP: An error occured ${err.message}")
            }

            userService.transformUserRoles()
        }
        // PRODUCTION. PLEASE DO NOT MODIFY WITHOUT ASKING PASI-MARKUS OR PANU!!!
        else if (Environment.current == Environment.PRODUCTION) {

            /* Do not remove */
            log.info("${new Date()}: Start of filling cache")
            xmlService.transferRsetMappingToNewField()
            indicatorService.getAllActiveIndicators()
            resourceTypeService.getActiveResourceTypes()
            eolProcessService.getAllEolProcesses()
            queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            localizedLinkService.getAllLinks()
            log.info("${new Date()}: End of filling cache")

            User user = User.findByUsername("xautotest@testtest.com")

            if (user) {
                user.testRobot = Boolean.TRUE
                user.merge(flush: true)
            }
            /* Do not remove */
        }

        log.info("Is registered in Consul: " + consulRegistrationService.isRegistered())
        log.info("END OF BOOTSTRAP: ${System.currentTimeMillis() - now} ms")
    }
    def destroy = {
    }
}
