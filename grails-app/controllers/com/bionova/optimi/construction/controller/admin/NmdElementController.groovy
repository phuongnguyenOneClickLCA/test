package com.bionova.optimi.construction.controller.admin


import com.bionova.optimi.core.service.NmdApiService
import com.bionova.optimi.json.nmd.NmdDeactivateResources
import com.bionova.optimi.json.nmd.NmdNewResources
import com.bionova.optimi.json.nmd.NmdUpdateResponse
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonSlurper
import org.apache.commons.lang.time.DateUtils
import org.springframework.web.multipart.MultipartHttpServletRequest
import wslite.json.JSONObject

import java.net.http.HttpResponse
import java.text.SimpleDateFormat

class NmdElementController {

    def loggerUtil
    def nmdElementService
    def flashService
    def nmdApiService
    def nmdResourceService
    def userService

    @Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
    def list(){
        [nmdElements: nmdElementService.getNmdElements(), userService: userService]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])

    def uploadNmdElementExcel() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                loggerUtil.error(log, "Error in when uploading NMD ExcelFile: file not found",)
                flashService.setErrorAlert("Error in when uploading NMD ExcelFile: file not found", Boolean.TRUE)
                redirect action: "list"
            } else {
                try {
                    def mapWithInfo = nmdElementService.importNmdElementExcel(excelFile)
                    if (mapWithInfo?.get("error")) {
                        flash.errorAlert = "${mapWithInfo?.get("error")}"
                    }
                    chain(action: "list")
                } catch (Exception e) {
                    loggerUtil.error(log, "Error in when uploading NMD ExcelFile", e)
                    flashService.setErrorAlert("Error in when uploading NMD ExcelFile: ${e.getMessage()} ", Boolean.TRUE)
                    redirect(action: "list")
                }
            }
        } else {
            loggerUtil.error(log, "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest).")
            flashService.setErrorAlert("Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest).", Boolean.TRUE)
            redirect action: "list"
        }
    }

    def getApiUpdateForNmdElementManual(){
        String updateStatus = nmdApiService.getNMDDailyUpdate(NmdApiService.NMD_FALLBACK_DATE, true)
        render text: updateStatus
    }
}