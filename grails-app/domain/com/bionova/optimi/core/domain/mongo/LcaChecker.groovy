package com.bionova.optimi.core.domain.mongo


import com.bionova.optimi.core.util.FormatterUtil
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

@GrailsCompileStatic
class LcaChecker {

    static mapWith = "mongo"

    ObjectId id
    String checkId
    String checkType
    String checkCategory
    String checkName
    String errorText
    String conditionToApply
    String requiredCondition
    String projectResult1
    String projectResult2
    String applicableResourceType // to be deprecated
    List<String> applicableResourceTypes // 15730
    Double points
    Double pointsErosionRange
    Double checkValue1
    Double checkValue2
    Boolean freeCheck
    String fileName
    String unit
    String displayUnitImperial
    String displayUnitMetric
    Double averageValue

    transient stringUtilsService

    static constraints = {
        checkType nullable: true
        conditionToApply nullable: true
        requiredCondition nullable: true
        pointsErosionRange nullable: true
        checkValue1 nullable: true
        checkValue2 nullable: true
        projectResult1 nullable: true
        projectResult2 nullable: true
        fileName nullable: true
        applicableResourceType nullable: true
        applicableResourceTypes nullable: true
        freeCheck nullable: true
        unit nullable: true
        displayUnitImperial nullable: true
        displayUnitMetric nullable: true
        averageValue nullable: true


    }

    transient unitConversionUtil

    static transients = [
            "projectResult1IndicatorId",
            "projectResult2IndicatorId"
    ]

    String getProjectResult1IndicatorId() {
        if (projectResult1) {
            return projectResult1.tokenize(".")[0]
        } else {
            return null
        }
    }

    String getProjectResult2IndicatorId() {
        if (projectResult2) {
            return projectResult2.tokenize(".")[0]
        } else {
            return null
        }
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getFormattedScoreForUser(User user, Double score, String format) {
        String formattedScore = ""

        if (score != null) {
            if (unit && unit != "%") {
                if ("imperial".equals(user?.unitSystem)) {
                    String targetUnit = unitConversionUtil.europeanUnitToImperialUnit(unit)
                    Double imperialScore = unitConversionUtil.doConversion(score, null, unit, null, null, null, null, null, null, targetUnit)

                    if (imperialScore < 1) {
                        formattedScore = "${imperialScore.round(3)}"
                    } else {
                        formattedScore = FormatterUtil.formatNumber(imperialScore, format)
                    }
                } else {
                    if (score < 1) {
                        formattedScore = "${score.round(3)}"
                    } else {
                        formattedScore = FormatterUtil.formatNumber(score, format)
                    }
                }
            } else {
                if (score < 1) {
                    formattedScore = "${score.round(3)}"
                } else {
                    formattedScore = FormatterUtil.formatNumber(score, format)
                }
            }
        }
        return formattedScore
    }

    String getErrorText(Double score) {
        String renderError = ""
        if (errorText) {
            if (score == 0) {
                renderError = "Has no materials"
            } else {
                renderError = errorText
            }
        }
        return renderError
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getUnitForUser(User user) {
        if ("imperial".equals(user?.unitSystem)) {
            return unitConversionUtil.europeanUnitToImperialUnit(unit)
        } else {
            return unit
        }
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getDisplayUnitForUser(User user) {
        if ("imperial".equals(user?.unitSystem)) {
            if (displayUnitImperial)
                return stringUtilsService.convertUnitToHTML(displayUnitImperial)
        } else {
            if (displayUnitMetric)
                return stringUtilsService.convertUnitToHTML(displayUnitMetric)
        }
        return ''
    }
}
