/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.util

import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.UserService

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class LoggerUtil {

    def info(log, content, Exception e = null) {
        if (e && content) {
            StringWriter sw = new StringWriter()
            e.printStackTrace(new PrintWriter(sw))
            content = content + ": " + sw.toString()
        }
        log.info(getUsername() + ": " + content)
    }

    def error(log, content, Exception e = null) {
        if (e && content) {
            StringWriter sw = new StringWriter()
            e.printStackTrace(new PrintWriter(sw))
            content = content + ": " + sw.toString()
        }
        log.error(getUsername() + ": " + content)
    }

    def warn(log, content, Exception e = null) {
        if (e && content) {
            StringWriter sw = new StringWriter()
            e.printStackTrace(new PrintWriter(sw))
            content = content + ": " + sw.toString()
        }
        log.warn(getUsername() + ": " + content)
    }

    def fatal(log, content, Exception e = null) {
        if (e && content) {
            StringWriter sw = new StringWriter()
            e.printStackTrace(new PrintWriter(sw))
            content = content + ": " + sw.toString()
        }
        log.fatal(getUsername() + ": " + content)
    }

    def debug(log, content, Exception e = null) {
        if (log.isDebugEnabled()) {
            if (e && content) {
                StringWriter sw = new StringWriter()
                e.printStackTrace(new PrintWriter(sw))
                content = content + ": " + sw.toString()
            }
            log.debug(getUsername() + ": " + content)
        }
    }

    private static String getUsername() {
        UserService userService = SpringUtil.getBean("userService")
        User currentUser = userService.getCurrentUser()

        if (currentUser) {
            return currentUser.username
        } else {
            return "Unknown user"
        }
    }
}
