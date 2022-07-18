package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.util.LoggerUtil
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.text.DecimalFormat

@CompileStatic
class NumberUtil {

    Log log = LogFactory.getLog(NumberUtil.class)
    LoggerUtil loggerUtil
    FlashService flashService

    Double roundingNumber(Double number) {
        try {
            if (number != null) {
                if (number >= 0.1) {
                    return number.round(2)
                } else if (number < 0.1 && number > 0.0001) {
                    return number.round(4)
                } else if (number <= 0.0001) {
                    return number.round(6)
                } else {
                    return number
                }
            } else {
                return null
            }
        } catch (e) {
            loggerUtil.error(log, "Error in roundingNumber in NumberUtil", e)
            flashService.setErrorAlert("Error in roundingNumber in NumberUtil: ${e.message}", true)
            return number
        }
    }

    BigDecimal answerToBigDecimal(String answer, DecimalFormat decimalFormat, Dataset dataset, Boolean fallbackValueOne = Boolean.FALSE) {
        if (decimalFormat && answer) {
            try {
                return (decimalFormat.parse(answer?.replace(',', '.')) as BigDecimal).round(5)
                // JohannaP said should be 5 decimals for everything
            } catch (Exception e) {
                loggerUtil.warn(log, "answerToBigDecimal Exception while handling user given input", e)
                flashService.setErrorAlert("answerToBigDecimal Exception while handling user given input ( ${dataset?.queryId} / ${dataset?.sectionId} / ${dataset?.questionId}: ${answer} ): ${e.getMessage()}", true)
            }
        }
        return new BigDecimal(fallbackValueOne ? 1 : 0)
    }

    BigDecimal resultToBigDecimal(Double result, Boolean fallbackValueOne = Boolean.FALSE) {
        if (result && !result.isNaN() && !result.isInfinite()) {
            try {
                return (result as BigDecimal).round(5) // JohannaP said should be 5 decimals for everything
            } catch (Exception e) {
                loggerUtil.warn(log, "resultToBigDecimal Exception while handling user given input", e)
                flashService.setErrorAlert("resultToBigDecimal Exception while handling user given input ( Found invalid value: ${result} ): ${e.getMessage()}", true)
            }
        }
        return new BigDecimal(fallbackValueOne ? 1 : 0)
    }

    Integer answerToInteger(String answer, DecimalFormat decimalFormat, Dataset dataset, Boolean fallbackValueOne = Boolean.FALSE) {
        if (decimalFormat && answer?.replace(',', '.')?.isInteger()) {
            try {
                return decimalFormat.parse(answer.replace(',', '.')).intValue()
            } catch (Exception e) {
                loggerUtil.warn(log, "answerToInteger Exception while handling user given input", e)
                flashService.setErrorAlert("answerToInteger Exception while handling user given input ( ${dataset?.queryId} / ${dataset?.sectionId} / ${dataset?.questionId}: ${answer} ): ${e.getMessage()}", true)
            }
        }
        return new Integer(fallbackValueOne ? 1 : 0)
    }

    Float answerToFloat(String answer, DecimalFormat decimalFormat, Dataset dataset) {
        if (decimalFormat && answer?.replace(',', '.')?.isFloat()) {
            try {
                return decimalFormat.parse(answer.replace(',', '.')).floatValue().round(5)
                // JohannaP said should be 5 decimals for everything
            } catch (Exception e) {
                loggerUtil.warn(log, "answerToFloat Exception while handling user given input", e)
                flashService.setErrorAlert("answerToFloat Exception while handling user given input ( ${dataset?.queryId} / ${dataset?.sectionId} / ${dataset?.questionId}: ${answer} ): ${e.getMessage()}", true)
            }
        }
        return new Float(0)
    }
}
