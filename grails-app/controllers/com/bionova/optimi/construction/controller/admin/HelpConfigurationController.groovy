package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.User
import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest

class HelpConfigurationController {

    def helpConfigurationService
    def userService
    def flashService

    def index() {
        List <HelpConfiguration> allConfigurations = helpConfigurationService.getAllConfigurations()
        List<String> configurationTableHeaders = ["targetPage", "contentType","contentName", "contentURL", "additionalText", "queryId(If present)", "indicatorId"]
        [allConfigurations:allConfigurations, configurationTableHeaders:configurationTableHeaders]
    }

    def importConfigurations () {

        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "index"
            } else {
                try {

                    Map importedConfigurations =  helpConfigurationService.importExcel(excelFile)
                    if (importedConfigurations.get("exception")) {
                        flash.fadeErrorAlert = "Error in import ${importedConfigurations.get("exception")}"
                        if (importedConfigurations.get("parseError")) {
                            flash.warningAlert = "parsing error at ${importedConfigurations.get("parseError")}"
                        }
                    } else {
                        String okMessage = "Imported:"

                        importedConfigurations.each { key, value ->
                            okMessage = "${okMessage} ${key} -- ${value} </br>"

                        }
                        flash.fadeSuccessAlert = okMessage
                    }

                    redirect(action: "index")
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect action: "index"
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "index"
        }
    }

    def delete() {
        HelpConfiguration configuration
        String renderer = ""
        if (params.id) {
            configuration = helpConfigurationService.getHelpConfigurationById(params.id)
            if (configuration) {
                User user = userService.getCurrentUser(true)

                configuration.delete(flush: true, failonError: true)

                log.info("User ${user ? user.username : "null"} removed ${helpConfigurationService.getLocalizedContentName(configuration)} from database.")
                flashService.setSuccessAlert("User ${user ? user.username : "null"} removed ${helpConfigurationService.getLocalizedContentName(configuration)} from database.", true)
                renderer = "removeal done for construction ${helpConfigurationService.getLocalizedContentName(configuration)} from database."
            }
        }
        render([output: renderer, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}
