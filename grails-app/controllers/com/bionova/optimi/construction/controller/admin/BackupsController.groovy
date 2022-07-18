/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.User
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.io.FileUtils

/**
 * @author Pasi-Markus Mäkelä
 */
@Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
class BackupsController extends ExceptionHandlerController {
    def configurationService
    def userService

    def index() {
        def backups = [:]
        String backupsPath = "/home/maintenance/"

        if (backupsPath) {
            new File(backupsPath).eachFile() { file ->
                if (file?.getName()?.startsWith("db_backup_manual")) {
                    backups.put(file.getName(), backupsPath + file.getName())
                }
            }
        }
        [backups: backups?.sort({ it.key })]
    }

    def zippedBackup() {
        def response = configurationService.zippedDbBackup()

        if (response?.contains("error")) {
            flash.errorAlert = "Error in creating backup: ${response}"
        } else {
            flash.successAlert = "Backup created successfully. You can download it from DEV."
        }
        redirect controller: "main"
    }

    def downloadBackup() {
        User user = userService.getCurrentUser(Boolean.TRUE)
        String backupsPath = "/home/maintenance/"
        String fileName = params.fileName
        File file

        if (fileName && userService.isSystemAdmin(user)) {
            file = FileUtils.getFile(backupsPath + fileName)
        }

        if (file) {
            render(file: file, fileName: "${fileName}.zip", contentType: "application/zip")
        }
    }
}
