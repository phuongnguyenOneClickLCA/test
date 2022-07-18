package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import grails.web.servlet.mvc.GrailsParameterMap


class QueryParamsService {

    /**
     * Check in request parameters if it has triggerCalculation param, which is sent from query form.
     * if it is false, we should skip the calculation
     * @param params
     * @return
     */
    boolean skipTriggeringCalculationFromParams(GrailsParameterMap params) {
        if (params?.containsKey(Constants.TRIGGER_CALCULATION_PARAMETER_KEY)) {
            boolean triggerCalc = transformToBoolean(params.get(Constants.TRIGGER_CALCULATION_PARAMETER_KEY))
            params.remove(Constants.TRIGGER_CALCULATION_PARAMETER_KEY)
            // if params says triggerCalculation == true means do not skip
            return !triggerCalc
        }
        return false
    }

    /**
     * This method is used only in queryController.save and will have 'true' value when calculation process still going
     * It's needed to simple query tab change, without running saving and calculation actions
     * @param params
     * @return
     */
    boolean skipTriggeringCalculationAndSaveFromParams(GrailsParameterMap params) {
        boolean skipCalcAndSave = false

        if (params?.containsKey(Constants.REDIRECT_WITHOUT_CALCULATION_AND_SAVE)) {
            skipCalcAndSave = transformToBoolean(params.get(Constants.REDIRECT_WITHOUT_CALCULATION_AND_SAVE))
            params.remove(Constants.REDIRECT_WITHOUT_CALCULATION_AND_SAVE)
        }

        return skipCalcAndSave
    }

    private transformToBoolean(String value){
        return Boolean.valueOf(Boolean.valueOf(value as String))
    }
}
