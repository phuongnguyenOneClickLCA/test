/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.BootStrap
import com.bionova.optimi.core.util.LoggerUtil
import groovy.transform.CompileStatic


/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@CompileStatic
class ShellCmdService {

    LoggerUtil loggerUtil

    def executeShellCmd(String cmd) {
        def cmdResponse = ""

            try {
                Runtime runtime = Runtime.getRuntime()
                Process process = runtime.exec(cmd)
                BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()))
                def line

                while ((line = buf.readLine()) != null) {
                    cmdResponse = cmdResponse + line
                }

                BufferedReader errorBuf = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                def errorLine, errorResponse = ""

                while ((errorLine = errorBuf.readLine()) != null) {
                    errorResponse = errorResponse + errorLine
                }
                cmdResponse = (errorResponse.contains("Failed") ? errorResponse: cmdResponse)
            } catch (Exception e) {
                cmdResponse = e.getMessage()
            }
        return cmdResponse
    }

    String getHostName() {
        try {
            return executeShellCmd('hostname')
        } catch (e) {
            loggerUtil.error(log, "Error while trying to get hostname", e)
            return 'unknown'
        }
    }

    void initHostnameConstant() {
        BootStrap.HOST_NAME = getHostName()
    }
}
