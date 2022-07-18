package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.service.StringUtilsService
import com.bionova.optimi.core.util.LoggerUtil
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ReportGenerationRule {

    String reportGenerationRuleId
    List<ReportGenerationRuleTemplate> allowedTemplates
    List<String> contentInjection
    List<String> conditionalRemovals
    List<Map> resultTextReplacementIfNull // Map has keys (String) "text" and (List<String>) "resultCategoryIds"

    transient StringUtilsService stringUtilsService
    transient LoggerUtil loggerUtil

    static embedded = ['allowedTemplates']

    static constraints = {
        reportGenerationRuleId nullable: false
        allowedTemplates nullable: false
        contentInjection nullable: false
        resultTextReplacementIfNull nullable: true
        conditionalRemovals nullable: true
    }

    def getAllChartUiIdentifications(Map<String, List<ReportGenerationRuleContent>> contentInjectionSetsFromApplication) {
        return getContentGenerationRulesForTemplate(contentInjectionSetsFromApplication)?.
                findResults({it.uiIdentification})?.flatten()?.unique()
    }

    def getTextReplacement(ReportGenerationRuleContent ruleContent) {
        String textReplacement = null

        if (resultTextReplacementIfNull && ruleContent?.resultCategoryId) {
            textReplacement = resultTextReplacementIfNull.find({(it.resultCategoryIds as List<String>)?.contains(ruleContent.resultCategoryId)})?.get("text")
        }

        return textReplacement
    }

    List<ReportConditionalRemoval> getRemovalRulesForTemplate(List<ReportConditionalRemoval> removalRulesFromApplication) {
        List<ReportConditionalRemoval> removalRulesForTemplate = []

        if (removalRulesFromApplication) {
            try {
                List<String> removalMarkers = []
                conditionalRemovals?.each { String removalId ->
                    if (removalId) {
                        ReportConditionalRemoval removalRule = removalRulesFromApplication.find { it.removalRuleId == removalId }
                        if (removalRule) {
                            removalRulesForTemplate += removalRule
                            if (removalRule.removalMarkerStart) {
                                removalMarkers.add(removalRule.removalMarkerStart)
                            }
                            if (removalRule.removalMarkerEnd) {
                                removalMarkers.add(removalRule.removalMarkerEnd)
                            }
                        }
                    }
                }

                // check for duplicates in removal markers
                if (removalMarkers) {
                    List<String> duplicates = stringUtilsService.getDuplicates(removalMarkers)
                    if (duplicates) {
                        duplicates.toSet().each { String duplicate ->
                            if (duplicate) {
                                removalRulesForTemplate = removalRulesForTemplate.findAll { it.removalMarkerStart.toString() != duplicate || it.removalMarkerEnd.toString() != duplicate }
                            }
                        }
                        log.warn("ERROR IN CONFIGURATION, duplicated removalMarkerStart and removalMarkerEnd is not allowed in report (reportGenerationRuleId: ${reportGenerationRuleId}). List of duplicated markers:  ${duplicates}")
                    }
                }
            } catch (Exception e) {
                loggerUtil.warn(log,"Error occured while trying to compile the reportRemovalRules", e)
            }
        }

        return removalRulesForTemplate
    }

    List<ReportGenerationRuleContent> getContentGenerationRulesForTemplate(Map<String, List<ReportGenerationRuleContent>> contentInjectionSetsFromApplication) {
        List<ReportGenerationRuleContent> ruleContents = []

        if (contentInjectionSetsFromApplication && contentInjection) {
            List<String> targetMarkers = []
            contentInjection.each { String setId ->
                if (setId) {
                    List<ReportGenerationRuleContent> selectedSet = contentInjectionSetsFromApplication.get(setId).collect { it as ReportGenerationRuleContent }
                    if (selectedSet) {
                        ruleContents += selectedSet
                        targetMarkers += selectedSet.collect { it.targetMarker }
                    }
                }
            }

            if (targetMarkers) {
                // check for duplicates in target markers
                List<String> duplicates = stringUtilsService.getDuplicates(targetMarkers)
                if (duplicates) {
                    duplicates.toSet().each { String duplicate ->
                        if (duplicate) {
                            ruleContents = ruleContents.findAll { it.targetMarker.toString() != duplicate }
                        }
                    }
                    log.warn("ERROR IN CONFIGURATION, duplicated targetMarker is not allowed in a report (reportGenerationRuleId: ${reportGenerationRuleId}). List of duplicated markers:  ${duplicates}")
                }
            }

        }

        return ruleContents
    }
}
