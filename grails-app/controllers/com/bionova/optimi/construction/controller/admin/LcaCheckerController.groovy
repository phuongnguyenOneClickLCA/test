package com.bionova.optimi.construction.controller.admin

import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartHttpServletRequest

class LcaCheckerController {

    def loggerUtil
    def lcaCheckerService

    def index() {
        [lcaCheckers: lcaCheckerService.getAllLcaCheckers()]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def uploadLcaCheckerExcel() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "index"
            } else {
                try {
                    def mapWithInfo = lcaCheckerService.importLcaCheckers(excelFile)

                    if (mapWithInfo?.get("error")) {
                        flash.errorAlert = "${mapWithInfo?.get("error")}"
                    }
                    chain(action: "index")
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect(action: "index")
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "index"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeLcaChecker() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = lcaCheckerService.deleteLcaChecker(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "LcaChecker removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing LcaChecker failed. See logs"
        }
        redirect action: "index"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeLcaCheckerAjax() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = lcaCheckerService.deleteLcaChecker(id)
        }

        if (deleteOk) {
            render text: "LcaChecker removed successfully"
        } else {
            render text: "Removing LcaChecker failed. See logs"
        }
    }
}
