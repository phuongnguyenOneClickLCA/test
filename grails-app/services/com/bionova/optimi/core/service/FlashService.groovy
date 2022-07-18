package com.bionova.optimi.core.service

import grails.web.mvc.FlashScope
import org.apache.commons.logging.Log
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.springframework.web.context.request.RequestContextHolder

/*
    Use this to get and set flash object
    The flash messages are displayed on index.gsp (IndexController)

    Example:
    try {

    } catch (e) {
        loggerUtil.error(log, "Error in ", e)
        flashService.setErrorAlert("Error in : ${e.getMessage()}", true)
    }
*/

class FlashService {
    def userService
    def loggerUtil

    /**
     * Please send along with the response from backend the flash object with key using this constant. This will flash the backends errors / info in UI (if any). Alerts are set in 360Optimi.js
     * Example:
     *  render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
     *
     * The alert is set using setFlashForAjaxCall() in flashTemplates.js
     */
    public static final String FLASH_OBJ_FOR_AJAX = 'flashObjectForAjax'

    private static final String FLASH_REINIT = 'reinit'

    private FlashScope getFlash() {
        try {
            return WebUtils.retrieveGrailsWebRequest()?.flashScope
        } catch (ignored) {
            try {
                return ((GrailsWebRequest) RequestContextHolder.getRequestAttributes())?.flashScope
            } catch (e) {
                loggerUtil.error(log, "ERROR: cannot get flash object in Flash service", e)
                return null
            }
        }
    }

    /*
        This is to check a condition to only flash when the user enables the debug notification
     */
    private Boolean checkUserDebugMode() {
        return userService.getCurrentUser()?.enableDevDebugNotifications
    }

    private void setFlashAttribute(String attribute, String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        try {
            if (attribute && message) {
                if (!showOnlyInDebugMode || (showOnlyInDebugMode && checkUserDebugMode())) {
                    FlashScope flash = getFlash()
                    String existing = flash?.get(attribute) ?: ''
                    message = message.trim()
                    if (existing) {
                        if (existing.contains(message)) {
                            // mark duplicates by an *
                            flash?.put(attribute, existing.replace(message, '*' + message))
                        } else {
                            flash?.put(attribute, existing + '<br>' + message)
                        }
                    } else {
                        flash?.put(attribute, message)
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "ERROR: cannot set flash attribute in Flash service", e)
        }
    }

    void setReinitForTheNextRequest() {
        try {
            FlashScope flash = getFlash()
            flash.put(FLASH_REINIT, true)
        } catch (e) {
            loggerUtil.error(log, "ERROR: cannot update flash in Flash service", e)
        }
    }

    void reinitForTheNextRequestIfNeeded() {
        try {
            FlashScope flash = getFlash()
            if (flash.get(FLASH_REINIT)) {
                flash.remove(FLASH_REINIT)
                flash.each { key, value ->
                    flash.put(key, value)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "ERROR: cannot reinit flash in Flash service", e)
        }
    }

    void setFadeSaveAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('fadeSaveAlert', message, showOnlyInDebugMode)
    }

    void setFadeSuccessAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('fadeSuccessAlert', message, showOnlyInDebugMode)
    }

    void setFadeInfoAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('fadeInfoAlert', message, showOnlyInDebugMode)
    }

    void setFadeWarningAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('fadeWarningAlert', message, showOnlyInDebugMode)
    }

    void setFadeErrorAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('fadeErrorAlert', message, showOnlyInDebugMode)
    }

    void setSaveAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('saveAlert', message, showOnlyInDebugMode)
    }

    void setSuccessAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('successAlert', message, showOnlyInDebugMode)
    }

    void setInfoAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('infoAlert', message, showOnlyInDebugMode)
    }

    void setWarningAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('warningAlert', message, showOnlyInDebugMode)
    }

    void setErrorAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('errorAlert', message, showOnlyInDebugMode)
    }

    void setPermSaveAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('permSaveAlert', message, showOnlyInDebugMode)
    }

    void setPermSuccessAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('permSuccessAlert', message, showOnlyInDebugMode)
    }

    void setPermInfoAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('permInfoAlert', message, showOnlyInDebugMode)
    }

    void setPermWarningAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('permWarningAlert', message, showOnlyInDebugMode)
    }

    void setPermErrorAlert(String message, Boolean showOnlyInDebugMode = Boolean.FALSE) {
        setFlashAttribute('permErrorAlert', message, showOnlyInDebugMode)
    }
}
