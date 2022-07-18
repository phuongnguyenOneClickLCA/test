/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.User
import com.mongodb.MongoCursorNotFoundException
import grails.converters.JSON
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException
import org.codehaus.groovy.runtime.StackTraceUtils
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.grails.exceptions.ExceptionUtils
import org.springframework.http.HttpStatus

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class ErrorController {

    def loggerUtil
    def userService
    def flashService

    def show() {
        if (!request.forwardURI.startsWith("/app/cd/")) {
            Exception exception = request.getAttribute('javax.servlet.error.exception')
            if (exception) {
                String stackTrace = getStackTrace(exception)
                def errorCause
                boolean isOptimisticLocking = false

                if (exception.cause instanceof MongoCursorNotFoundException || ExceptionUtils.getRootCause(exception) instanceof MongoCursorNotFoundException) {
                    loggerUtil.warn(log, stackTrace)
                    loggerUtil.warn(log, "URLs with time are: " + session?.getAttribute(SessionAttribute.REQUEST_URLS.toString()))
                } else {
                    loggerUtil.error(log, stackTrace)
                    loggerUtil.error(log, "URLs with time are: " + session?.getAttribute(SessionAttribute.REQUEST_URLS.toString()))
                }
                try {
                    flashService.setErrorAlert("Error: ${stackTrace?.replaceAll("\n", "<br>")?.replaceAll("\t", '<span style="margin-left: 40px"></span>')}", true)
                } catch (ignored) {
                }

                if (exception.cause instanceof SecurityException) {
                    errorCause = message(code: "error.securityException")
                } else if (exception.cause instanceof OptimisticLockingException) {
                    isOptimisticLocking = true
                } else if (exception.cause instanceof SizeLimitExceededException) {
                    SizeLimitExceededException theException = exception.cause
                    errorCause = message(code: "sizeLimitExceededException", args: [(theException.getActualSize() / (1024 * 1024)).toString()])
                } else {
                    errorCause = message(code: "error.general")
                }

                if (isOptimisticLocking) {
                    flash.errorAlert = message(code: "error.optimisticLocking")
                    redirect controller: "main"
                } else {
                    User user = userService.getCurrentUser(Boolean.TRUE)
                    def model = [stackTrace: stackTrace, errorCause: errorCause]

                    if (user?.testRobot) {
                        model.put("hideNavi", true)
                    }
                    render(view: 'show', model: model)
                }
            }
            render(view: 'show')
        } else {
            logException()
            render(["message": request.getAttribute('javax.servlet.error.message')] as JSON)
        }
    }

    def notFound() {
        // Redirect 404 to index
        if (!request.forwardURI.startsWith("/app/cd/")) {
            List<String> calledMethodNames = StackTraceUtils.sanitize(new Throwable()).stackTrace?.collect({
                it.methodName
            })?.unique()
            if (calledMethodNames) {
                calledMethodNames.removeAll(Constants.REMOVE_FROM_STACKTRACE)
            }
            log.info("ERROR CONTROLLER 404, called from: ${calledMethodNames}")
            if (request.xhr) {
                render status: HttpStatus.NOT_FOUND, text: "Oops... Something went wrong! Please contact your administrator."
            } else {
                flashService.setFadeErrorAlert("ERROR CONTROLLER 404, called from: ${calledMethodNames}", true)
                redirect controller: "index", action: "index"
            }
        } else {
            logException()
            render(["message": request.getAttribute('javax.servlet.error.message')] as JSON)
        }
    }

    private void logException() {
        Exception exception = request.getAttribute('javax.servlet.error.exception')
        if (exception) {
            String stackTrace = getStackTrace(exception)
            loggerUtil.error(log, stackTrace)
            loggerUtil.error(log, "URLs with time are: " + session?.getAttribute(SessionAttribute.REQUEST_URLS.toString()))
        }
    }

    private String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
