/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.util

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class FormatterUtil {

    @CompileStatic(TypeCheckingMode.SKIP)
    static Double formatByMinZeros(Double number, Integer minZeros) {
        if (number && minZeros != null) {
            long multiplier = 1

            if (minZeros == 0) {
                number = Math.round(number).toDouble()
            } else {
                for (int i = 0; i < minZeros; i++) {
                    multiplier = multiplier * 10
                }

                if (number.toLong() > multiplier) {
                    number = number / multiplier
                    number = Math.round(number).toDouble()
                    number = number * multiplier.toDouble()
                }
            }
        }
        return number
    }

    static String doubleToString(Double d) {
        if (d != null) {
            if (d % 1.0 != 0) {
                return String.format("%s", d)
            } else {
                return String.format("%.0f", d)
            }
        } else {
            return null
        }
    }

    static String formatNumber(Double number, String format, Integer maxIntegerDigits = null, Integer minIntegerDigits = null,
                               Integer maxFractionDigits = null, Integer minFractionDigits = null) {
        String formatted

        if (number && format) {
            DecimalFormatSymbols dcfs = new DecimalFormatSymbols()
            DecimalFormat decimalFormat = new DecimalFormat(format, dcfs)

            // ensure formatting accuracy
            decimalFormat.setParseBigDecimal(true)

            if (maxIntegerDigits) {
                decimalFormat.setMaximumIntegerDigits(maxIntegerDigits)
            }
            if (minIntegerDigits != null) {
                decimalFormat.setMinimumIntegerDigits(minIntegerDigits)
            }
            if (maxFractionDigits != null) {
                decimalFormat.setMaximumFractionDigits(maxFractionDigits)
            }
            if (minFractionDigits != null) {
                decimalFormat.setMinimumFractionDigits(minFractionDigits)
            }

            try {
                formatted = decimalFormat.format(number)
            }
            catch(ArithmeticException e) {
                formatted = number
            }
        }
        return formatted
    }

    /**
     * Rounds all values to 2 decimal places and makes all negative values 0 as default for CD3D
     * @param value
     * @return
     */
    static Double roundToZeroIfNegative(Double value) {
        if (value >= 10) {
            return value.round()
        }
        if (value >= 1 && value < 10) {
            return value.round(1)
        }
        if (value >= 0.01 && value < 1) {
            return value.round(2)
        }
        if (value >= 0 && value < 0.01) {
            return 0
        }
        if (value < 0) {
            return 0
        }
        return value
    }

    /**
     * Makes all negative and invalid values 0 as default for CD3D
     * @param value
     * @return
     */
    static Double setAsZeroIfNegative(Double value) {
        if (value < 0) {
            return 0
        }
        return value
    }
}
