package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional

@Transactional
class CalculationRuleService {
    def stringUtilsService
    def denominatorUtil

    String getLocalizedUnit(CalculationRule calculationRule, Indicator indicator = null,
                         String onlyThisLang = null, Boolean doUnitAlteration = true) {
        String errMsg = "LOCALIZATION MISSING"
        if (!calculationRule) {
            return ""
        }
        if (calculationRule.unit) {
            String localizedUnit
            if (!onlyThisLang) {
                localizedUnit = calculationRule.getLocalizedUnit()
            } else {
                localizedUnit = calculationRule.unit.get(onlyThisLang) ?: calculationRule.unit.get("EN")
            }

            if (indicator && !calculationRule.excludeDenominator) {
                def overallDenomUnit = denominatorUtil.getLocalizedUnit(
                        indicator.resolveDenominators?.find({ "overallDenominator".equalsIgnoreCase(it.denominatorType) }))
                if (overallDenomUnit && localizedUnit) {
                    localizedUnit = localizedUnit.toString() + overallDenomUnit.toString()
                }
            }
            if (localizedUnit) {
                if (doUnitAlteration) {
                    localizedUnit = stringUtilsService.convertUnitToHTML(localizedUnit)
                }
                return localizedUnit
            } else {
                return errMsg
            }
        } else {
            return ""
        }
    }
}
