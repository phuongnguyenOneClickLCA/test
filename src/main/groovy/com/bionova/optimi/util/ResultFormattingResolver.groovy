package com.bionova.optimi.util

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.ResultFormatting
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.IndicatorReportService
import com.bionova.optimi.core.util.FormatterUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import javax.servlet.http.HttpSession
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class ResultFormattingResolver {

    def denominatorService
    IndicatorReportService indicatorReportService
    def userService

    private static Log log = LogFactory.getLog(ResultFormattingResolver.class)

    ResultFormatting resolveResultFormattingWithParams(Indicator indicator, Denominator denominator, HttpSession session,
                                             Boolean isDisplay, Boolean noScientificNumbers,
                                             String reportItemId = null, Boolean displayMainPage = false) {
        ResultFormatting resultFormatting
        IndicatorReportItem indicatorReportItem

        if (indicator && reportItemId) {
            indicatorReportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, true, indicator.report?.reportItems)?.find({
                reportItemId.equals(it.reportItemId)
            })
        }

        if (indicatorReportItem && indicatorReportItem?.tableSettings?.get("showDecimals") == true) {
            noScientificNumbers = Boolean.TRUE
        }

        if (!denominator && isDisplay) {
            denominator = indicator?.getDisplayDenominator()
        }
        resultFormatting = resolveResultFormatting(indicator, denominator, session, isDisplay, noScientificNumbers, displayMainPage)

        return resultFormatting
    }

    public ResultFormatting resolveResultFormatting(Indicator indicator, Denominator denominator, HttpSession session, Boolean isDisplay = null, Boolean noScientificNumbers = null, Boolean displayMainPage = null) {
        ResultFormatting r = denominatorService.getResultFormattingObject(denominator?.resultFormatting) ?: indicator?.resultFormatting
        ResultFormatting resultFormatting = new ResultFormatting(unit: r?.unit, showDecimals: r?.showDecimals, rounding: r?.rounding, minimumDecimals: r?.minimumDecimals,
                significantDigits: r?.significantDigits, minimumZeros: r?.minimumZeros, useScientificNumbers: displayMainPage ? !noScientificNumbers : r?.useScientificNumbers)
        def sessionResultFormatting = session?.getAttribute("resultFormatting")?.get(indicator?.indicatorId)
        if (sessionResultFormatting && !isDisplay && !"removeFormatting".equals(sessionResultFormatting)) {
            if (resultFormatting) {
                if ("useScientificNumbers".equals(sessionResultFormatting)) {
                    resultFormatting.useScientificNumbers = 3
                } else{
                    if(noScientificNumbers || "showDecimals".equals(sessionResultFormatting) || r?.showDecimals){
                        resultFormatting.useScientificNumbers = null
                        resultFormatting.showDecimals = Boolean.TRUE
                    } else {
                        resultFormatting.useScientificNumbers = resultFormatting.useScientificNumbers ?
                                resultFormatting.useScientificNumbers : 3
                    }
                }
            } else {
                resultFormatting = new ResultFormatting()

                if ("useScientificNumbers".equals(sessionResultFormatting)) {
                    resultFormatting.useScientificNumbers = 3
                }
            }

        }

        return resultFormatting
    }

    public String formatByResultFormatting(User user, Indicator indicator, Denominator denominator, Double number,
                                           HttpSession session, Boolean isDisplay, Boolean noScientificNumbers,
                                           Boolean useMinimumZeros, String unit = null, Boolean disableThousandSeparator = null, String reportItemId = null,
                                           Boolean excelDump = null, Boolean percentageOverride = null, Boolean displayMainPage = false, Boolean allowZeroExportReport = false) {
        String formattedNumber = ""
        IndicatorReportItem indicatorReportItem

        if (indicator && reportItemId) {
            indicatorReportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, true, indicator.report?.reportItems)?.find({
                reportItemId.equals(it.reportItemId)
            })
        }

        if (indicatorReportItem && indicatorReportItem?.tableSettings?.get("showDecimals") == true) {
            noScientificNumbers = Boolean.TRUE
        }

        if (isDisplay && indicator?.hideZeroResults && 0 == number && !allowZeroExportReport) {
            return formattedNumber
        } else {
            if (!denominator && isDisplay) {
                denominator = indicator?.getDisplayDenominator()
            }
            formattedNumber = formatByResultFormattingObject(user, indicator, denominator, number, session, isDisplay, noScientificNumbers, useMinimumZeros, unit, disableThousandSeparator, excelDump,percentageOverride, displayMainPage)
        }
        return formattedNumber
    }

    public String formatByTwoDecimalsAndLocalizedDecimalSeparator(User user, Double number, Boolean disableThousandSeparator = null) {
        String formattedNumber = ""

        if (number != null) {
            BigDecimal bd = new BigDecimal(number)
            bd = bd.round(new MathContext(2))
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale(User.LocaleString.EUROPEAN.locale))

            if (disableThousandSeparator) {
                decimalFormat.applyPattern('#.#########')
            } else {
                decimalFormat.applyPattern('###,###.#########')
            }
            formattedNumber = decimalFormat.format(bd.doubleValue())
        }
        return formattedNumber
    }

    String formatByTwoSignificantDigits(User user, Double number, Boolean disableThousandSeparator = null) {
        String formattedNumber = ""

        if (number != null) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale(User.LocaleString.EUROPEAN.locale))
            if (disableThousandSeparator) {
                decimalFormat.applyPattern('#.#########')
            } else {
                decimalFormat.applyPattern('###,###.#########')
            }

            if (number > -1 && number < 1) {
                // round to 2 significant digits if under 1
                BigDecimal bd = new BigDecimal(number)
                bd = bd.round(new MathContext(2))
                formattedNumber = decimalFormat.format(bd.doubleValue())
            } else {
                // else round 2
                formattedNumber = decimalFormat.format(number.round(2))
            }
        }
        return formattedNumber
    }

    public String formatByUserLocaleAndThousandSeparator(User user, Double number) {
        String formattedNumber = ""

        if (number != null) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale(User.LocaleString.EUROPEAN.locale))
            decimalFormat.applyPattern('###,###')
            formattedNumber = decimalFormat.format(number)
        }
        return formattedNumber
    }

    public String formatByResultFormattingObject(User user, Indicator indicator, Denominator denominator, Double number,
                                                 HttpSession session, Boolean isDisplay, Boolean noScientificNumbers,
                                                 Boolean useMinimumZeros, String unit = null, Boolean disableThousandSeparator = null,
                                                 Boolean excelDump = null, Boolean percentageOverride = null, Boolean displayMainPage = null) {

        ResultFormatting resultFormatting = resolveResultFormatting(indicator, denominator, session, isDisplay, noScientificNumbers, displayMainPage)

        String formattedNumber = formatByResultFormattingObject(resultFormatting, user, indicator, number, isDisplay,
                useMinimumZeros, unit, disableThousandSeparator, excelDump, percentageOverride)

        return formattedNumber
    }

    String formatByResultFormattingObject(ResultFormatting resultFormatting, User user, Indicator indicator, Double number, Boolean isDisplay,
                                                 Boolean useMinimumZeros, String unit = null, Boolean disableThousandSeparator = null,
                                                 Boolean excelDump = null, Boolean percentageOverride = null, Boolean allowZeroExportReport = false) {
        String formattedNumber = ""

        if (isDisplay && indicator?.hideZeroResults && 0 == number && !allowZeroExportReport) {
            return formattedNumber
        }

        if (indicator?.hideZeroResults && 0 == number) {
            number = null
        }

        if (number != null && !number.isNaN()) {
            BigDecimal bd
            Integer digits
            DecimalFormat decimalFormat
            boolean euLocale = user?.localeString == User.LocaleString.EUROPEAN.locale

            if (user) {
                decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString))
            } else {
                euLocale = true
                decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale(User.LocaleString.EUROPEAN.locale))
            }

            if (excelDump) {
                digits = 2
                if (number > -1 && number < 1) {
                    // round to 2 significant digits if under 1
                    bd = new BigDecimal(number)
                    bd = bd.round(new MathContext(digits))
                    number = bd.doubleValue()
                } else {
                    // else round 2
                    number = number.round(2)
                }
                formattedNumber = number.toString().replace(",",".")
            } else if (resultFormatting?.useScientificNumbers && !percentageOverride) {
                bd = new BigDecimal(number)
                digits = resultFormatting.useScientificNumbers + 1
                bd = bd.round(new MathContext(digits))
                number = bd.doubleValue()
                decimalFormat.applyPattern("0.#####E0")
                formattedNumber = decimalFormat.format(number)
                if (euLocale) {
                    formattedNumber = formattedNumber.replace(Constants.Unicode.MINUS_SIGN.code, Constants.Unicode.HYPHEN.code)
                }
            } else {
                if (resultFormatting?.significantDigits) {
                    digits = resultFormatting.significantDigits
                    bd = new BigDecimal(number)

                    if ("up" == resultFormatting?.rounding) {
                        bd = bd.round(new MathContext(digits, RoundingMode.UP))
                    } else if ("down" == resultFormatting?.rounding) {
                        bd = bd.round(new MathContext(digits, RoundingMode.DOWN))
                    } else {
                        bd = bd.round(new MathContext(digits))
                    }
                    number = bd.doubleValue()
                }

                if (useMinimumZeros && resultFormatting?.minimumZeros) {
                    number = FormatterUtil.formatByMinZeros(number, resultFormatting.minimumZeros)
                }

                if (resultFormatting?.showDecimals || percentageOverride) {
                    if (resultFormatting.minimumDecimals) {
                        String pattern = '#.'
                        resultFormatting.minimumDecimals.times {
                            pattern = "${pattern}#"
                        }
                        decimalFormat.applyPattern(pattern)
                    } else {
                        if (disableThousandSeparator) {
                            decimalFormat.applyPattern('#.##')
                        } else {
                            decimalFormat.applyPattern('###,###.##')
                        }
                    }
                } else {
                    if (disableThousandSeparator) {
                        decimalFormat.applyPattern('#')
                    } else {
                        decimalFormat.applyPattern('###,###')
                    }
                    number = Math.round(number)
                }
                formattedNumber = decimalFormat.format(number)
            }
        }

        if (formattedNumber && resultFormatting?.localizedUnit && !isDisplay && unit == null) {
            formattedNumber = formattedNumber + " " + resultFormatting.localizedUnit
        }
        return formattedNumber
    }

    public String autoFormatDecimalsAndLocalizedDecimalSeparator(User user, Double number, Boolean disableThousandSeparator = null, Boolean isPercentage = false) {
        String formattedNumber = ""

        if (user && number) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString))
            if (disableThousandSeparator) {
                decimalFormat.applyPattern('#.##')
            } else {
                decimalFormat.applyPattern('###,###.##')
            }
            if (isPercentage) {
                if (number >= 1) {
                    decimalFormat.setMaximumFractionDigits(0)
                } else if (number < 1 && number >= 0.95) {
                    decimalFormat.setMaximumFractionDigits(2)
                } else if (number < 0.95 && number >= 0.05) {
                    decimalFormat.setMaximumFractionDigits(1)
                }
            } else {
                if (number >= 10) {
                    decimalFormat.setMaximumFractionDigits(0)
                } else if (number > 1 && number < 10) {
                    decimalFormat.setMaximumFractionDigits(1)
                } else if (number < 1 && number >= 0.01) {
                    decimalFormat.setMaximumFractionDigits(2)
                } else if (number < 0) {
                    decimalFormat.setMaximumFractionDigits(2)
                }

            }

            formattedNumber = decimalFormat.format(number)
        } else if (!user && number) {
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale(User.LocaleString.EUROPEAN.locale))
            if (disableThousandSeparator) {
                decimalFormat.applyPattern('#.##')
            } else {
                decimalFormat.applyPattern('###,###.##')
            }
            if (isPercentage) {
                if (number >= 1) {
                    decimalFormat.setMaximumFractionDigits(0)
                } else if (number < 1 && number >= 0.95) {
                    decimalFormat.setMaximumFractionDigits(2)
                } else if (number < 0.95 && number >= 0.05) {
                    decimalFormat.setMaximumFractionDigits(1)
                }
            } else {
                if (number >= 10) {
                    decimalFormat.setMaximumFractionDigits(0)
                } else if (number > 1 && number < 10) {
                    decimalFormat.setMaximumFractionDigits(1)
                } else if (number < 1 && number >= 0.01) {
                    decimalFormat.setMaximumFractionDigits(2)
                } else if (number < 0) {
                    decimalFormat.setMaximumFractionDigits(2)
                }

            }

            formattedNumber = decimalFormat.format(number)
        }
        return formattedNumber
    }
}
