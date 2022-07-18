package com.bionova.optimi.util

import grails.web.mvc.FlashScope
import groovy.transform.CompileStatic
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class ErrorMessageUtil {

    public void setErrorMessage(String message, Boolean setToSessionAlso = Boolean.FALSE, Boolean permanent = Boolean.FALSE) {
        FlashScope flashScope = getFlashScope()

        if (flashScope != null && message) {
            if (permanent) {
                String existing = flashScope.get("errorAlert")

                if (existing && !existing.contains(message)) {
                    flashScope.put("errorAlert", (existing + "<br />" + message))
                } else {
                    flashScope.put("errorAlert", message)
                }

                if (setToSessionAlso) {
                    HttpSession session = getRequest()?.getSession(false)
                    existing = session?.getAttribute("errorAlert")

                    if (existing && !existing.contains(message)) {
                        session?.setAttribute("errorAlert", (existing + "<br />" + message))
                    } else {
                        session?.setAttribute("errorAlert", message)
                    }
                }
            } else {
                String existing = flashScope.get("fadeErrorAlert")

                if (existing && !existing.contains(message)) {
                    flashScope.put("fadeErrorAlert", (existing + "<br />" + message))
                } else {
                    flashScope.put("fadeErrorAlert", message)
                }

                if (setToSessionAlso) {
                    HttpSession session = getRequest()?.getSession(false)
                    existing = session?.getAttribute("fadeErrorAlert")

                    if (existing && !existing.contains(message)) {
                        session?.setAttribute("fadeErrorAlert", (existing + "<br />" + message))
                    } else {
                        session?.setAttribute("fadeErrorAlert", message)
                    }
                }
            }

        }
    }

    public void setWarningMessage(String message, Boolean setToSessionAlso = Boolean.FALSE) {
        FlashScope flashScope = getFlashScope()

        if (flashScope != null && message) {
            String existing = flashScope.get("warningAlert")

            if (existing && !existing.contains(message)) {
                flashScope.put("warningAlert", (existing + "<br />" + message))
            } else {
                flashScope.put("warningAlert", message)
            }

            if (setToSessionAlso) {
                HttpSession session = getRequest()?.getSession(false)
                existing = session?.getAttribute("warningAlert")

                if (existing && !existing.contains(message)) {
                    session?.setAttribute("warningAlert", (existing + "<br />" + message))
                } else {
                    session?.setAttribute("warningAlert", message)
                }
            }
        }
    }

    public String getWarnMessageFromSession() {
        HttpSession session = getRequest().getSession(false)
        String message = session?.getAttribute("warningAlert")
        session?.removeAttribute("warningAlert")
        return message
    }

    public String getErrorMessageFromSession() {
        HttpSession session = getRequest().getSession(false)
        String message = session?.getAttribute("errorAlert")
        session?.removeAttribute("errorAlert")
        return message
    }

    private HttpServletRequest getRequest() {
        try {
            return WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        } catch (Exception e) {
            return null
        }
    }

    private FlashScope getFlashScope() {
        try {
            return WebUtils.retrieveGrailsWebRequest().flashScope
        } catch (Exception e) {
            return null
        }
    }
}
