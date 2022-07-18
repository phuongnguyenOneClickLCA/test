/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.io.IOUtils

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
class LogsController extends ExceptionHandlerController {

    def configurationService

    def index() {
        def configurations = configurationService.getAll()
        def logsPath = configurations?.find ({ config -> config.name == Constants.ConfigName.LOG_PATH.toString()})?.value
        def logs = [:]

        if(logsPath) {
            new File(logsPath).eachFile() { file->
                logs.put(file.getName(), file.getPath())
            }
        } else {
            loggerUtil.error(log, "No logsUrl or logsPath set.")
        }
        [logs: logs]
    }

    def downloadLogFile() {
        String filePath = params.filePath

        if (filePath) {
            File logFile = new File(filePath)

            if (logFile) {
                InputStream contentStream

                try {
                    response.setHeader "Content-disposition", "attachment; filename=${logFile.name}"
                    response.setHeader("Content-Length", "${logFile.size()}")
                    response.setContentType("texxt/plain")
                    contentStream = logFile.newInputStream()
                    response.outputStream << contentStream
                    webRequest.renderView = false
                } finally {
                    IOUtils.closeQuietly(contentStream)
                }
            }
        } else {
            render text: "Log file not found!!!"
        }
    }
}

