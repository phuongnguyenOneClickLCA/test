package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.Constants
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartHttpServletRequest

class CarbonDesignerController {

    def simulationToolService

    def constructionUpload() {
        [entityTypeAllocationList: simulationToolService.getAllEntityTypeConstructionAllocation()]
    }

    @Secured([Constants.ROLE_DATA_MGR, "ROLE_SYSTEM_ADMIN"])
    def uploadSimulationToolExcel() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "constructionUpload"
            } else {
                try {
                    def mapWithInfo = simulationToolService.importSimulationToolExcel(excelFile)

                    if (mapWithInfo?.get("error")) {
                        flash.errorAlert = "${mapWithInfo?.get("error")}"
                    }
                    chain(action: "constructionUpload")
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect(action: "constructionUpload")
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "constructionUpload"
        }
    }


    @Secured([Constants.ROLE_DATA_MGR, "ROLE_SYSTEM_ADMIN"])
    def removeConstructionAllocation() {
        String id = params.id
        if (delete(id)) {
            flash.fadeSuccessAlert = "Construction allocation removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing Construction allocation failed. See logs"
        }
        redirect action: "constructionUpload"
    }

    @Secured([Constants.ROLE_DATA_MGR, "ROLE_SYSTEM_ADMIN"])
    def removeConstructionAllocationAjax() {
        String ids = params.ids
        List<String> listOfIds = ids.split(',')
        Boolean isSuccessful = Boolean.FALSE
        if (listOfIds) {
            listOfIds.forEach({ constructionId ->
                delete(constructionId)
            })
            isSuccessful = Boolean.TRUE
        } else {
            isSuccessful = Boolean.FALSE
        }
        render([output: isSuccessful] as JSON)
    }

    private Boolean delete(id) {
        boolean deleteOk = false
        if (id) {
            deleteOk = simulationToolService.deleteConstructionAllocation(id)
        }
        return deleteOk
    }

}
