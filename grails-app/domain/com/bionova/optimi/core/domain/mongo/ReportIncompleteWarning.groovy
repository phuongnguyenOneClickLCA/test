package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic;

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ReportIncompleteWarning {
    static mapWith = "mongo"
    String controlledAdditionalQuestionId
    List<String> requiredValues
    Map<String, String> requiredText
    List<String> recommendedValues
    Map<String, String> recommendationText

    static constraints = {
        requiredValues nullable: true
        requiredText nullable: true
        recommendedValues nullable: true
        recommendationText nullable: true
    }

    String localizedRequired() {
        return localize(requiredText)
    }

    String localizedRecommended() {
        return localize(recommendationText)
    }

    String localize(Map values) {

        if (values) {
            String lang = DomainObjectUtil.getMapKeyLanguage()
            String localized = values[lang] ?: (values["EN"] ?: "NAME LOCALIZATION MISSING")
            return localized
        } else {
            return ""
        }
    }

}
