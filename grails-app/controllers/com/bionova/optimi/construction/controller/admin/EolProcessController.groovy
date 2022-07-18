package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.core.domain.mongo.EolProcess
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.web.multipart.MultipartHttpServletRequest


class EolProcessController {

    def eolProcessService
    def domainClassService
    def applicationService

    def index() {
        List<EolProcess> allEolProcesses = eolProcessService.getAllEolProcesses()
        List<EolProcess> activeEolProcesses = allEolProcesses?.findAll({it.active})
        List<EolProcess> inactiveEolProcesses = allEolProcesses?.findAll({!it.active})
        [eolProcesses: activeEolProcesses, inactiveEolProcesses: inactiveEolProcesses]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def uploadEolProcessExcel() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "index"
            } else {
                try {
                    def mapWithInfo = eolProcessService.importEolProcesses(excelFile)

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
    def removeEolProcess() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = eolProcessService.deleteEolProcess(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "EOLProcess removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing EOLProcess failed. See logs"
        }
        redirect action: "index"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeEolProcessAjax() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = eolProcessService.deleteEolProcess(id)
        }

        if (deleteOk) {
            render text: "EOLProcess removed successfully"
        } else {
            render text: "Removing EOLProcess failed. See logs"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showData() {
        EolProcess eolProcess
        Map properties
        String eolProcessId = params.eolProcessId

        if (eolProcessId) {
            eolProcess = eolProcessService.getEolProcessByEolProcessId(eolProcessId)

            if (eolProcess) {
                List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EolProcess.class)

                if (domainClassProperties) {
                    properties = eolProcess.properties.findAll({
                        domainClassProperties.contains(it.key)
                    })?.sort({ it.key.toString() })
                }
            }
        }
        [properties: properties, hideNavi: true, eolProcess: eolProcess,
         showDataReferences: applicationService.getApplicationByApplicationId("LCA")?.showDataReferences?.find({"eolProcess".equals(it.page)})]
    }
}
