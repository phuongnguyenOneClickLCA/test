package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.AppendQuestionIdToDisplayResult
import com.bionova.optimi.core.domain.mongo.BenchmarkValue
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.CalculationTotalResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExpandFeature
import com.bionova.optimi.core.domain.mongo.IndicatorReport
import com.bionova.optimi.core.domain.mongo.IndicatorReportDenominator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.IndicatorReportItemFilter
import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.ReportTableCell
import com.bionova.optimi.core.domain.mongo.ReportTableRow
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ResultFormatting
import com.bionova.optimi.core.domain.mongo.ResultManipulator
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.service.IndicatorReportService
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.ui.DetailReportRow
import com.bionova.optimi.ui.DetailReportTable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode
import org.apache.commons.codec.binary.Base64
import org.apache.commons.lang.StringUtils
import org.bson.Document

import java.text.DecimalFormat
import java.text.NumberFormat

class CalculationResultTagLib {

    static namespace = "calc"

    def configurationService
    def indicatorService
    def datasetService
    def loggerUtil
    def queryService
    def optimiResourceService
    def entityService
    def denominatorUtil
    def userService
    def channelFeatureService
    def errorMessageUtil
    def resultFormattingResolver
    def optimiStringUtils
    def detailReportServiceProxy
    def portfolioService
    def applicationService
    def licenseService
    def stringUtilsService
    def calculationRuleService
    def flashService
    def valueReferenceService
    def querySectionService
    def denominatorService
    IndicatorReportService indicatorReportService

    private static final def months = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
    private static final def quarters = ["Q1", "Q2", "Q3", "Q4"]

    def areResultsValid = { attrs ->
        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        List<String> queryIds = attrs.queryIds
        String valid = "true"
        Boolean indicatorReady = attrs.indicatorReady ?: entity.isIndicatorReady(indicator, queryIds)
        if (indicator && !indicator.nonNumericResult && entity && !indicatorReady && !entity.resultsValid(indicator, indicatorReady)) {
            valid = null
        }
        out << valid
    }

    def resultCategoryClarificationText = { attrs ->
        String clarification = attrs.clarification
        ResultCategory resultCategory = attrs.resultCategory
        Entity entity = attrs.entity
        List<Entity> childEntitites = attrs.childEntities

        if (resultCategory && entity) {
            List<ValueReference> clarificationAsValueReferences = resultCategory.clarificationAsValueReferences

            if (clarificationAsValueReferences) {
                clarificationAsValueReferences.each { ValueReference valueReference ->
                    def answer = valueReferenceService.getValueForEntity(valueReference, entity, null, null)
                    if ((answer == null || answer.isEmpty()) && childEntitites) {
                        Entity nextEntityWithValueRef = childEntitites?.find({
                            valueReferenceService.getValueForEntity(valueReference, it, null, null)
                        })
                        if (nextEntityWithValueRef) {
                            answer = valueReferenceService.getValueForEntity(valueReference, nextEntityWithValueRef, null, null)
                        }
                    }

                    if (answer) {
                        if (clarification) {
                            clarification = clarification + ", " + answer
                        } else {
                            clarification = answer
                        }
                    }
                }
            }
        }

        out << clarification

    }


    def renderResultHeadingObject = { attrs ->
        Indicator indicator = attrs.indicator
        Boolean useShortNames = attrs.useShortNames
        Entity entity = attrs.entity
        Entity parent = entity?.parentEntityId ? entity.parentById : entity
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parent)
        List<ResultCategory> resultCategories = attrs.resultCategories ? attrs.resultCategories : indicator?.getResolveResultCategories(parent)
        def doColSpan = attrs.doColSpan
        def isPortfolio = attrs.isPortfolio
        Boolean hideClarificationText = Boolean.TRUE
        String heading = ""
        Boolean hideLinkAndExplain = attrs.hideLinkAndExplain

        for (ResultCategory resultCategory in resultCategories) {
            def clarificationText
            if (isPortfolio) {
                clarificationText = resultCategory?.clarificationText
            } else {
                clarificationText = resultCategoryClarificationText(entity: entity, resultCategory: resultCategory)?.toString()?.trim()
            }

            if (clarificationText && !hideLinkAndExplain) {
                hideClarificationText = Boolean.FALSE
                break
            }
        }

        if (!doColSpan) {
            heading = "<thead id=\"resultsTableHeading\"><tr><th class=\"hideIfQuarterly\" id=\"firstHeading\" style=\"width: 5%;\">&nbsp;</th><th style=\"width: 5%;\">${message(code: "resultCategory.name")}</th>"

        } else {
            if (hideLinkAndExplain) {
                heading = "<thead><tr><th></th><th>${message(code: "resultCategory.name")}</th>"
            } else {
                heading = "<thead><tr><th></th><th style=\"width: 5%;\">${message(code: "resultCategory.name")}</th>"
            }
        }
        if (hideClarificationText == Boolean.FALSE) {
            heading = "<thead><tr><th></th><th style=\"width: 5%;\">${message(code: "resultCategory.name")}</th><th style=\"width: 5%;\">${message(code: "entity.show.task.description")}</th>"
        }


        for (int i = 1; i < 13; i++) {
            heading = "${heading}<th class=\"monthly\">${message(code: "month." + i)}</th>"
        }

        quarters.each {
            heading = "${heading}<th class=\"quarterly\">${it}</th>"
        }

        Account accountForEntity

        if (calculationRules?.find({ it.multiplyWithOrganizationCarbonCost })) {
            List<Account> accountsForEntity = licenseService.getValidLicensesForEntity(entity)?.collect({ it.accounts ?: [] })?.flatten()?.sort({ Account a -> a.companyName })

            if (accountsForEntity?.size()) {
                accountForEntity = accountsForEntity.first()
            }
        }
        calculationRules.each { CalculationRule rule ->
            String help = rule.localizedHelp && !hideLinkAndExplain ? "<span class=\"fiveMarginLeft tableHeadingPopover stickOnHover hidden-print\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-html=\"true\" data-content=\"<p style='font-weight:normal;'>${rule.localizedHelp}</p>\"><i class=\"far fa-question-circle\"></i></span>" : ""
            String filePath = isCalculationImageExits(rule)

            heading = "${heading}" +
                    "<th class=\"number\" > " +
                    "${filePath ? "<img src=\"${filePath}\" width=\"42\" height=\"42\"><br/>" : ""}" +
                    "${useShortNames && rule?.shortName ? rule?.localizedShortName : rule?.localizedName}<br/>" +
                    "${calculationRuleService.getLocalizedUnit(rule, indicator)}  " +
                    "${rule?.multiplyWithOrganizationCarbonCost && accountForEntity?.unitCarbonCost ? accountForEntity.unitCarbonCost : rule?.unitAsValueReference ? valueReferenceService.getValueForEntity(rule.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: "" : ""}${help}</th> "
        }

        heading = heading + "<th colspan=\"3\"></th></thead>"
        pageScope.hideClarificationText = hideClarificationText
        out << heading
    }

    public String isCalculationImageExits(CalculationRule calculationRule) {
        String filePath
        String ruleIconId = calculationRule.ruleIconId
        if (ruleIconId) {
            def testPath = new File("/var/www/static/calculationRule/${ruleIconId}.png")
            if (testPath.exists()) {
                filePath = '/static/calculationRule/' + ruleIconId + '.png'
            } else {
                log.error("missing image for calculationRule from server for calculationRule id: ${calculationRule.calculationRuleId} and ruleIconId: ${ruleIconId}")

                String assetPath = "/app/assets/calculationRule/${ruleIconId}.png"
                if (asset.assetPathExists(src: "/calculationRule/${ruleIconId}.png")) {
                    filePath = assetPath
                } else {
                    log.error("missing image for ${calculationRule.calculationRuleId} and ruleIconId: ${ruleIconId} from assets")
                }
            }
        }
        return filePath
    }

    private String searchQueryFromVirtual(ResultCategory category, Indicator indicator, int step = 1, List<ResultCategory> resultCategories) {

        if (step == 10) {
            log.error("There is a loop in the virtual category ${category?.resultCategoryId}")
        }

        // prevent infinite loop
        if (category?.linkToQueryFromReport && (step > 0 && step <= 10)) {
            if (category?.virtual) {
                String categoryId = category.virtual.first()
                ResultCategory r = resultCategories?.find { categoryId == it.resultCategoryId }
                step++
                return searchQueryFromVirtual(r, indicator, step, resultCategories)
            } else {
                return category?.data?.find { it }?.key
            }
        } else {
            return null
        }
    }

    def renderResultRows = { attrs ->
        long now = System.currentTimeMillis()
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity

        Indicator indicator = attrs.indicator
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parentEntity)
        List<ResultCategory> resultCategories = attrs.resultCategories ? attrs.resultCategories : indicator?.getResolveResultCategories(parentEntity)
        Map<String, CalculationResult> resultsForIndicatorPerResultCategory = attrs.resultsForIndicator?.groupBy { it.resultCategoryId }
        Map<String, List<String>> expandableCategories = attrs.expandableCategories ? attrs.expandableCategories : null
        List<ResultCategory> allResultCategories = attrs.allResultCategories
        String reportItemId = attrs.reportItemId
        String resultRows = ""
        Boolean trialResults = parentEntity?.isFreeTrial
        Boolean hideEmpty = attrs.hideEmpty
        Boolean hideLinkAndExplain = attrs.hideLinkAndExplain

        User user = userService.getCurrentUser()
        ResultFormatting resultFormatting = attrs.resultFormatting ?: resultFormattingResolver.resolveResultFormattingWithParams(indicator, null, session, Boolean.FALSE, Boolean.FALSE, reportItemId)

        resultCategories?.each { ResultCategory category ->

            String resultCategoryId = category.resultCategoryId
            List<CalculationResult> calculationResultsByCategory = resultsForIndicatorPerResultCategory?.getAt(resultCategoryId)
            List<String> expandableCategoriesForThisCategory = expandableCategories?.get(resultCategoryId)
            String virtualExpand = ""

            if (expandableCategoriesForThisCategory) {
                virtualExpand = "<a href=\"javascript:\" onclick=\"toggleUnderLyingVirtuals('${resultCategoryId}virtualToggler${reportItemId}','${reportItemId}resultRowVirtual${resultCategoryId}');\"><i class=\"fa fa-plus-square fiveMarginRight\" id=\"${resultCategoryId}virtualToggler${reportItemId}\"></i></a>"
            }
            String help = category.localizedHelp ? "<span class=\"fiveMarginLeft tableHeadingPopover stickOnHover hidden-print\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-content=\"${category.localizedHelp}\"><i class=\"far fa-question-circle\"></i></span>" : ""

            int ruleIndex = 0
            List<String> resultCategoryQueryIds = category?.data?.keySet()?.toList()
            String queryId
            List<String> virtual = category.virtual

            if (virtual) {
                queryId = category.manualLinkToQueryFromReport ?: searchQueryFromVirtual(category, indicator, 1, allResultCategories)
            } else {
                queryId = category.manualLinkToQueryFromReport ?: resultCategoryQueryIds?.find{it}
            }

            boolean hasResult = false
            boolean hasApplyConditionData = false

            if (calculationRules && calculationResultsByCategory) {
                for (CalculationRule calcRule : calculationRules) {
                    if (calculationResultsByCategory?.find({
                        calcRule.calculationRuleId.equals(it.calculationRuleId)
                    })?.result) {
                        hasResult = true
                        break
                    }
                }
            }

            if (category.applyConditions?.dataPresent && hasResult) {
                hasApplyConditionData = true
            }
            resultRows = "${resultRows}<tr id=\"resultRow${resultCategoryId}\" class=\"${!hasResult ? 'noResult' : 'hasResult'} ${hideEmpty && !hasResult ? 'hidden' : ''} ${category?.applyConditions && !hasApplyConditionData ? 'hidden' : ''}\">"

            if ((((category.manualLinkToQueryFromReport || resultCategoryQueryIds?.size() == 1) && category?.linkToQueryFromReport) || (virtual && queryId && category?.linkToQueryFromReport) ) && !hideLinkAndExplain) {
                if (!indicator?.hideResultCategoryLabels) {
                    resultRows = resultRows + "<td>${virtualExpand}<a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: queryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${category?.resultCategory}</a>${help}</td>"
                    resultRows = resultRows + "<td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: queryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${category?.localizedName}</a></td>"
                } else {
                    resultRows = resultRows + "<td>${virtualExpand}&nbsp;</td><td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: queryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${category?.localizedName}</a>${help}</td>"

                }
            } else if (hideLinkAndExplain) {
                resultRows = resultRows + "<td>${category?.resultCategory}</td><td>${category?.localizedName}</td>"
            } else {
                if (!indicator?.hideResultCategoryLabels) {
                    resultRows = resultRows + "<td>${virtualExpand}${category?.resultCategory}${help}</td><td>${category?.localizedName}</td>"
                } else {
                    resultRows = resultRows + "<td>${virtualExpand}&nbsp</td><td>${category?.localizedName}${help}</td>"
                }
            }

            calculationRules?.each { CalculationRule rule ->
                if (trialResults && (rule.hideInTrial || category.hideInTrial)) {
                    resultRows = "${resultRows}<td class=\"text-right to-bold-result\">TRIAL</td>"
                } else {
                    Double score
                    boolean hideResult = category.hideResultInReport != null && (category.hideResultInReport.size() == 0 ||
                            category.hideResultInReport.contains(rule.calculationRuleId)) ? true : false

                    if (calculationResultsByCategory && !rule.skipRuleForCategories?.contains(resultCategoryId) &&
                            !hideResult) {

                        if (ruleIndex == 0) {

                            months.each {
                                def monthlyResult = calculationResultsByCategory?.find({
                                    rule.calculationRuleId.equals(it.calculationRuleId)
                                })?.monthlyResults?.get(it)
                                if (monthlyResult) {
                                    resultRows = "${resultRows}<td class=\"monthly\">${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, monthlyResult, Boolean.FALSE, Boolean.FALSE)}</td>"
                                } else {
                                    resultRows = "${resultRows}<td class=\"monthly\">&nbsp;</td>"
                                }
                            }

                            quarters.each {
                                def quarterlyResult = calculationResultsByCategory?.find({
                                    rule.calculationRuleId.equals(it.calculationRuleId)
                                })?.quarterlyResults?.get(it)

                                if (quarterlyResult) {
                                    resultRows = "${resultRows}<td class=\"quarterly\">${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, quarterlyResult, Boolean.FALSE, Boolean.FALSE)}</td>"
                                } else {
                                    resultRows = "${resultRows}<td class=\"quarterly\">&nbsp;</td>"
                                }
                            }

                        }
                        String clarificationText = resultCategoryClarificationText(entity: entity, resultCategory: category)?.toString()?.trim()

                        if (clarificationText.length() > 0) {
                            resultRows = resultRows + "<td class=\"clarification\">${clarificationText}&nbsp;</td>"
                        }
                        score = calculationResultsByCategory?.find({
                            rule.calculationRuleId.equals(it.calculationRuleId)
                        })?.result

                        if (score != null) {
                            hasResult = true
                        }

                        def formattedScore = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, score, Boolean.FALSE, Boolean.TRUE, "")
                        resultRows = "${resultRows}<td class=\"text-right to-bold-result\"><span>${formattedScore}</span></td>"

                    } else {
                        resultRows = "${resultRows}<td class=\"text-right to-bold-result\">&nbsp;</td>"
                    }
                }
                ruleIndex++
            }

            if ((!category.preventExpand || userService.getSuperUser(userService.getCurrentUser())) && hasResult && !indicator?.hideExpandAndCollapse) {
                resultRows = resultRows + "<td colspan=\"3\"><a href=\"javascript:;\" style=\"color: #6b9f00;\" onclick=\"fullScreenPopup('" +
                        "${createLink(controller: 'result', action: 'categoryDetails', params: [entityId: entity.id, indicatorId: indicator?.indicatorId, resultCategoryId: resultCategoryId, reportItemId: reportItemId])}');\">${message(code: 'results.expand')}</a></td>"
            } else if (!hasResult) {
                resultRows = resultRows + "<td colspan=\"3\"><a href=\"javascript:;\" style=\"color: #6b9f00;\" onclick=\"hideEmptyRows();\">${message(code: 'results.hide_empty')}</a></td>"
            } else {
                resultRows = resultRows + "<td colspan=\"4\"></td>"
            }

            resultRows = resultRows + "</tr>"

            if (expandableCategoriesForThisCategory) {
                expandableCategoriesForThisCategory.each { String expandedCategoryId ->
                    ResultCategory expandCategory = allResultCategories?.find({
                        expandedCategoryId.equals(it.resultCategoryId)
                    })

                    if (expandCategory) {
                        int index = 0
                        List<String> expandCategoryQueryIds = expandCategory?.data?.keySet()?.toList()
                        String expandQueryId = expandCategoryQueryIds?.find({ String q -> q })

                        resultRows = "${resultRows}<tr id=\"${resultCategoryId}resultRow${expandedCategoryId}\" class=\"hidden ${reportItemId}resultRowVirtual${resultCategoryId}\">"
                        String helpExpandCategory = expandCategory.localizedHelp ? "<span class=\"fiveMarginLeft tableHeadingPopover hidden-print\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-trigger=\"hover\" data-content=\"${expandCategory.localizedHelp}\"><i class=\"far fa-contextquestion-circle\"></i></span>" : ""

                        if (expandCategoryQueryIds?.size() == 1 && expandCategory?.linkToQueryFromReport) {
                            if (!indicator?.hideResultCategoryLabels) {
                                resultRows = resultRows + "<td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: expandQueryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${expandCategory?.resultCategory}${helpExpandCategory}</a></td>"
                                resultRows = resultRows + "<td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: expandQueryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${expandCategory?.localizedName}</a></td>"
                            } else {
                                resultRows = resultRows + "<td>&nbsp;</td><td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: entity?.id, queryId: expandQueryId, entityId: entity?.parentEntityId])}\" class=\"link\" />${expandCategory?.localizedName}</a></td>"

                            }
                        } else {
                            if (!indicator?.hideResultCategoryLabels) {
                                resultRows = resultRows + "<td>${expandCategory?.resultCategory}${helpExpandCategory}</td><td>${expandCategory?.localizedName}</td>"
                            } else {
                                resultRows = resultRows + "<td>&nbsp</td><td>${expandCategory?.localizedName}</td>"
                            }
                        }

                        List<CalculationResult> calculationResultsByExpandCategory = resultsForIndicatorPerResultCategory?.getAt(expandedCategoryId)
                        calculationRules?.each { CalculationRule rule ->
                            Double score

                            if (calculationResultsByExpandCategory && !rule.skipRuleForCategories?.contains(expandedCategoryId) && !((rule.hideInTrial || expandCategory.hideInTrial) && trialResults)) {
                                if (index == 0) {
                                    months.each {
                                        def monthlyResult = calculationResultsByExpandCategory?.find({
                                            rule.calculationRuleId.equals(it.calculationRuleId)
                                        })?.monthlyResults?.get(it)

                                        if (monthlyResult) {
                                            resultRows = "${resultRows}<td class=\"monthly\">${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, monthlyResult, Boolean.FALSE, Boolean.FALSE)}</td>"
                                        } else {
                                            resultRows = "${resultRows}<td class=\"monthly\">&nbsp;</td>"
                                        }
                                    }

                                    quarters.each {
                                        def quarterlyResult = calculationResultsByExpandCategory?.find({
                                            rule.calculationRuleId.equals(it.calculationRuleId)
                                        })?.quarterlyResults?.get(it)

                                        if (quarterlyResult) {
                                            resultRows = "${resultRows}<td class=\"quarterly\">${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, quarterlyResult, Boolean.FALSE, Boolean.FALSE)}</td>"
                                        } else {
                                            resultRows = "${resultRows}<td class=\"quarterly\">&nbsp;</td>"
                                        }
                                    }

                                }
                                String clarificationText = resultCategoryClarificationText(entity: entity, resultCategory: expandCategory)?.toString()?.trim()


                                if (clarificationText.length()) {
                                    resultRows = resultRows + "<td class=\"clarification\">${clarificationText}&nbsp;</td>"
                                }
                                score = calculationResultsByExpandCategory?.find({
                                    rule.calculationRuleId.equals(it.calculationRuleId)
                                })?.result

                                if (score != null) {
                                    hasResult = true
                                }
                                def formattedScore = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, score, Boolean.FALSE, Boolean.TRUE, "")
                                resultRows = "${resultRows}<td class=\"text-right to-bold-result\"><span>${formattedScore}</span></td>"
                            } else if (trialResults && (rule.hideInTrial || expandCategory.hideInTrial)) {
                                resultRows = "${resultRows}<td class=\"text-right to-bold-result\">TRIAL</td>"
                            } else {
                                resultRows = "${resultRows}<td class=\"text-right to-bold-result\">&nbsp;</td>"
                            }
                            index++
                        }

                        if ((!expandCategory.preventExpand || userService.getSuperUser(userService.getCurrentUser())) && !indicator?.hideExpandAndCollapse) {
                            resultRows = resultRows + "<td colspan=\"3\"><a href=\"javascript:;\" style=\"color: #6b9f00;\" onclick=\"fullScreenPopup('" +
                                    "${createLink(controller: 'result', action: 'categoryDetails', params: [entityId: entity.id, indicatorId: indicator?.indicatorId, resultCategoryId: expandedCategoryId, reportItemId: reportItemId])}');\">${message(code: 'results.expand')}</a></td>"
                        } else {
                            resultRows = resultRows + "<td colspan=\"4\"></td>"
                        }
                        resultRows = resultRows + "</tr>"
                    }
                }
            }
        }
        loggerUtil.info(log, "Time it took to render result rows: ${System.currentTimeMillis() - now} ms")
        out << resultRows
    }

    def renderTotalResultRows = { attrs ->
        long now = System.currentTimeMillis()
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Indicator indicator = attrs.indicator
        Boolean hideClarification = attrs.hideClarification
        String reportItemId = attrs.reportItemId
        Boolean trialResults = parentEntity?.isFreeTrial

        if (entity && indicator) {

            User user = userService.getCurrentUser()
            ResultFormatting resultFormatting = attrs.resultFormatting ?: resultFormattingResolver.resolveResultFormattingWithParams(indicator, null, session, Boolean.FALSE, Boolean.FALSE, reportItemId)

            List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parentEntity)
            CalculationTotalResult calculationTotalResult = entity.getCalculationTotalResults()?.find({
                indicator.indicatorId.equals(it.indicatorId)
            })
            String totalResultRows = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td class=\"hideIfQuarterly\">&nbsp;</td><td class=\"boldText\">${message(code: 'totalScore')}</td>"

            if (!indicator?.hideResultCategoryLabels) {
                totalResultRows = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td>&nbsp;</td><td class=\"boldText\">${message(code: 'totalScore')}</td>"
            }
            int ruleIndex = 0

            if (hideClarification == Boolean.FALSE) {
                totalResultRows = "${totalResultRows}<td class=\"clarification\">&nbsp</td> "
            }

            calculationRules?.each { CalculationRule rule ->
                if (rule.hideInTrial && trialResults) {
                    if (ruleIndex == 0 && calculationTotalResult) {
                        months.each {
                            totalResultRows = totalResultRows + "<td class=\"monthly\" >TRIAL</td>"
                        }

                        quarters.each {
                            totalResultRows = totalResultRows + "<td class=\"quarterly\" >TRIAL</td>"
                        }
                    }

                    totalResultRows = "${totalResultRows}<td class=\"text-right boldText\">TRIAL</td>"
                } else {
                    if (ruleIndex == 0 && calculationTotalResult) {
                        def totalByMonth
                        def totalByQuarterly

                        months.each {
                            Map monthlyTotals = calculationTotalResult.monthlyTotalsPerCalculationRule
                            if (monthlyTotals) {
                                totalByMonth = monthlyTotals.get(rule.calculationRuleId)?.get(it)
                            }

                            if (totalByMonth) {
                                totalResultRows = totalResultRows + "<td class=\"monthly\"><strong>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, totalByMonth, Boolean.FALSE, Boolean.TRUE, "")}</strong></td>"
                            } else {
                                totalResultRows = totalResultRows + "<td class=\"monthly\" >&nbsp;</td>"
                            }
                        }

                        quarters.each {
                            Map quarterlyTotals = calculationTotalResult.quarterlyTotalsPerCalculationRule

                            if (quarterlyTotals) {
                                totalByQuarterly = quarterlyTotals.get(rule.calculationRuleId)?.get(it)
                            }
                            if (totalByQuarterly) {
                                totalResultRows = totalResultRows + "<td class=\"quarterly\"><strong>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, totalByQuarterly, Boolean.FALSE, Boolean.TRUE, "")}</strong></td>"

                            } else {
                                totalResultRows = totalResultRows + "<td class=\"quarterly\" >&nbsp;</td>"

                            }

                        }

                    }
                    def formattedTotalScore = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator,
                            entity.getTotalResult(indicator?.indicatorId, rule.calculationRuleId),
                            Boolean.FALSE, Boolean.TRUE, "")

                    totalResultRows = "${totalResultRows}<td class=\"text-right boldText\">${formattedTotalScore}</td>"
                }
                ruleIndex++
            }
            totalResultRows = "${totalResultRows} <td colspan=\"4\"></td></tr>"
            loggerUtil.info(log, "Time it took to render total row: ${System.currentTimeMillis() - now} ms")
            out << totalResultRows
        }
    }

    def renderAverageResultRows = { attrs ->
        long now = System.currentTimeMillis()
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        Boolean hideClarification = attrs.hideClarification
        Entity parentEntity = entity?.parentEntityId ? entity.parentById : entity
        List<ResultCategory> resultCategories = attrs.resultCategories ? attrs.resultCategories : indicator?.getResolveResultCategories(parentEntity)
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parentEntity)
        String reportItemId = attrs.reportItemId

        if (entity && indicator) {
            User user = userService.getCurrentUser()
            ResultFormatting resultFormatting = attrs.resultFormatting ?: resultFormattingResolver.resolveResultFormattingWithParams(indicator, null, session, Boolean.FALSE, Boolean.FALSE, reportItemId)
            Map<String, CalculationResult> resultsForIndicatorPerCalculationRule = attrs.resultsForIndicator?.groupBy { it.calculationRuleId }

            CalculationTotalResult calculationTotalResult = entity.getCalculationTotalResults()?.find({
                indicator.indicatorId.equals(it.indicatorId)
            })

            String totalResultRows = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td class=\"hideIfQuarterly\">&nbsp;</td><td class=\"boldText\">${message(code: 'mean')}</td>"

            if (!indicator?.hideResultCategoryLabels) {
                totalResultRows = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td>&nbsp;</td><td class=\"boldText\">${message(code: 'mean')}</td>"
            }
            int ruleIndex = 0

            calculationRules?.each { CalculationRule rule ->
                Integer resultsAmount = 0
                String calculationRuleId = rule.calculationRuleId
                Double total = (Double) entity.getTotalResult(indicator?.indicatorId, calculationRuleId)
                List<CalculationResult> resultsByCalculationRule = resultsForIndicatorPerCalculationRule?.getAt(calculationRuleId)

                resultCategories.each { ResultCategory resultCategory ->
                    Boolean hideResult = resultCategory.hideResultInReport != null && (resultCategory.hideResultInReport.size() == 0 ||
                            resultCategory.hideResultInReport.contains(calculationRuleId)) ? Boolean.TRUE : Boolean.FALSE


                    if (!rule.skipRuleForCategories?.contains(resultCategory.resultCategoryId) && !hideResult) {
                        if (resultsByCalculationRule?.find({
                            resultCategory.resultCategoryId.equals(it.resultCategoryId) &&
                                    calculationRuleId.equals(it.calculationRuleId)
                        })?.result) {
                            resultsAmount++
                        }
                    }
                }
                Double average
                String formattedAverage
                if (total && resultsAmount > 0) {
                    average = total / resultsAmount

                    if (average) {
                        formattedAverage = resultFormattingResolver.formatByResultFormattingObject(resultFormatting,
                                user, indicator, average, Boolean.FALSE, Boolean.TRUE, "")
                    }
                }

                if (ruleIndex == 0 && calculationTotalResult) {
                    Double averageByMonth
                    Double averageByQuarterly

                    months.each {
                        Integer monthlyResultsAmount = 0
                        resultCategories.each { ResultCategory resCat ->
                            if (resultsByCalculationRule?.find({
                                resCat.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                            })?.monthlyResults?.get(it.toString())) {
                                monthlyResultsAmount++
                            }
                        }

                        Map monthlyTotals = calculationTotalResult.monthlyTotalsPerCalculationRule

                        if (monthlyTotals) {
                            averageByMonth = monthlyTotals.get(calculationRuleId)?.get(it)
                        }

                        if (averageByMonth && monthlyResultsAmount > 0) {
                            averageByMonth = averageByMonth / monthlyResultsAmount
                            totalResultRows = totalResultRows + "<td class=\"monthly\"><strong>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, averageByMonth, Boolean.FALSE, Boolean.TRUE, "")}</strong></td>"
                        } else {
                            totalResultRows = totalResultRows + "<td class=\"monthly\" >&nbsp;</td>"
                        }
                    }

                    quarters.each {
                        Integer quarterlyResultsAmount = 0
                        resultCategories.each { ResultCategory resCat ->
                            if (resultsByCalculationRule?.find({
                                resCat.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                            })?.quarterlyResults?.get(it.toString())) {
                                quarterlyResultsAmount++
                            }
                        }

                        Map quarterlyTotals = calculationTotalResult.quarterlyTotalsPerCalculationRule

                        if (quarterlyTotals) {
                            averageByQuarterly = quarterlyTotals.get(calculationRuleId)?.get(it)
                        }
                        if (averageByQuarterly && quarterlyResultsAmount > 0) {
                            averageByQuarterly = averageByQuarterly / quarterlyResultsAmount
                            totalResultRows = totalResultRows + "<td class=\"quarterly\"><strong>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, averageByQuarterly, Boolean.FALSE, Boolean.TRUE, "")}</strong></td>"

                        } else {
                            totalResultRows = totalResultRows + "<td class=\"quarterly\" >&nbsp;</td>"

                        }

                    }

                }

                if (!hideClarification) {
                    totalResultRows = "${totalResultRows}<td class=\"clarification\">&nbsp</td> "
                }
                totalResultRows = "${totalResultRows}<td class=\"text-right boldText\">${formattedAverage ?: ''}</td>"
                ruleIndex++
            }
            totalResultRows = "${totalResultRows} <td colspan=\"4\"></td></tr>"
            loggerUtil.info(log, "Time it took to render average rows: ${System.currentTimeMillis() - now} ms")
            out << totalResultRows
        }
    }

    def renderResultPerDenominator = { attrs ->
        long now = System.currentTimeMillis()

        Entity entity = attrs.entity
        Entity parentEntity = entity?.parentEntityId ? entity.parentById : entity
        Indicator indicator = attrs.indicator
        List<Denominator> denominators = attrs.denominators
        def colSpan = attrs.colSpan
        NumberFormat numberFormat = new DecimalFormat("#.##")
        boolean noDynamicDatasets = false
        if (entity && denominators && indicator) {

            User user = userService.getCurrentUser()
            Boolean requireMonthly = indicator?.requireMonthly

            for (Denominator denom in denominators) {
                if ("dynamicDenominator".equals(denom.denominatorType) && !denominatorService.getDynamicDenominatorDatasets(entity, denom)) {
                    noDynamicDatasets = true
                    break
                }
            }
            String denominatorResultRows = ""

            if (!noDynamicDatasets) {
                denominatorResultRows = "<tr class=\"${!requireMonthly ? 'totalRowDenom' : ''} \"><td class=\"${!requireMonthly ? 'hideIfQuarterly' : ''}\" colspan=\"${colSpan}\">&nbsp;</td><td class=\"boldText\">${message(code: 'results.denominators')}</td>"
            }
            List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parentEntity)
            calculationRules.each {
                denominatorResultRows = "${denominatorResultRows}<td class=\"text-right boldText\">&nbsp;</td>"
            }
            denominatorResultRows = "${denominatorResultRows}<td colspan=\"${!requireMonthly ? 4 : 20}\"></td></tr>"

            for (Denominator denominator : denominators) {
                if (!denominator) {
                    continue
                }
                String unit
                List<ResultManipulator> resultManipulators = denominatorService.getResultManipulatorsAsManipulatorObjects(denominator.resultManipulator)
                ResultFormatting resultFormatting = resultFormattingResolver.resolveResultFormattingWithParams(indicator, denominator, session, Boolean.FALSE, Boolean.FALSE)

                if ("dynamicDenominator".equals(denominator.denominatorType)) {
                    List<Dataset> datasets = denominatorService.getDynamicDenominatorDatasets(entity, denominator)
                    ResourceCache resourceCache = ResourceCache.init(datasets)

                    datasets?.each { Dataset dataset ->
                        Resource resource = resourceCache.getResource(dataset)
                        Double denominatorQuantity
                        try {
                            denominatorQuantity = DomainObjectUtil.convertStringToDouble(dataset.answerIds.first())
                        } catch (Exception e) {
                            loggerUtil.error("En error occurred during rendering ResultPerDenominator taglib", e)
                        }

                        if (requireMonthly && denominator.requireMonthly) {
                            Map<String, Double> denominatorResultsMonthly
                            denominatorResultRows = "${denominatorResultRows}<tr><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.name ? denominator.localizedName : optimiResourceService.getLocalizedName(resource)}</td>"

                            calculationRules.each { CalculationRule calculationRule ->
                                Double totalForRule = entity.getTotalResult(indicator?.indicatorId, calculationRule.calculationRuleId)

                                denominatorResultsMonthly = entity.getResultByDynamicDenominator(indicator.indicatorId,
                                        calculationRule.calculationRuleId, dataset.manualId, requireMonthly, denominator)

                                Constants.MONTHS.each { String month ->
                                    Double value = denominatorResultsMonthly?.get(month)

                                    if (value != null) {
                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting,
                                                user, indicator, value, Boolean.FALSE, Boolean.TRUE, "")
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(value)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\"></td>"
                                    }

                                }

                                if (totalForRule && denominatorQuantity) {
                                    Double sum = totalForRule / denominatorQuantity

                                    if (sum != null) {
                                        if (resultManipulators) {
                                            resultManipulators.each {
                                                sum = denominatorUtil.useResultManipulatorForDoubleValue(it, entity, sum, resourceCache)
                                            }
                                        }
                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting,
                                                user, indicator, sum, Boolean.FALSE, Boolean.TRUE, "")
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(sum)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                                    }
                                }
                            }
                            denominatorResultRows = "${denominatorResultRows}<td colspan=\"20\"></td></tr>"
                        } else if (requireMonthly && !denominator.requireMonthly) {
                            denominatorResultRows = "${denominatorResultRows}<tr class=\"monthlyRequiredNonMonthly\"><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.name ? denominator.localizedName : ""}</td><td colspan=\"12\"></td>"

                            calculationRules.each { CalculationRule calculationRule ->
                                Double totalForRule = entity.getTotalResult(indicator?.indicatorId, calculationRule.calculationRuleId)

                                if (totalForRule && denominatorQuantity) {
                                    Double sum = totalForRule / denominatorQuantity

                                    if (sum != null) {
                                        if (resultManipulators) {
                                            resultManipulators.each {
                                                sum = denominatorUtil.useResultManipulatorForDoubleValue(it, entity, sum, resourceCache)
                                            }
                                        }

                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                                indicator, sum, Boolean.FALSE, Boolean.TRUE, unit)
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(sum)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                                    }
                                }
                            }
                            denominatorResultRows = "${denominatorResultRows}<td colspan=\"1\"></td></tr>"
                        } else {
                            String datasetUnit = stringUtilsService.convertUnitToHTML(dataset?.userGivenUnit ? dataset?.userGivenUnit : resource?.unitForData ?: '')
                            denominatorResultRows = "${denominatorResultRows}<tr class=\"totalRowDenom\"><td  class=\"hideIfQuarterly\" colspan=\"${colSpan}\">&nbsp;</td><td>${optimiResourceService.getLocalizedName(resource)} ${dataset?.quantity} ${datasetUnit}</td>"
                            calculationRules.each { CalculationRule calculationRule ->
                                Double value = entity.getResultByDynamicDenominator(indicator.indicatorId,
                                        calculationRule.calculationRuleId, dataset.manualId, Boolean.FALSE, denominator)


                                if (value != null) {
                                    String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                            indicator, value, Boolean.FALSE, Boolean.TRUE, unit)
                                    denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(value)}</td>"
                                } else {
                                    denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                                }
                            }
                            denominatorResultRows = "${denominatorResultRows}<td colspan=\"4\"></td></tr>"

                        }
                    }
                } else if ("monthlyValueReference".equals(denominator.denominatorType)) {
                    List<Dataset> datasets = denominatorService.getMonthlyValuereferenceDatasets(entity, denominator)
                    ResourceCache resourceCache = ResourceCache.init(datasets)

                    datasets?.each { Dataset dataset ->
                        Double denominatorQuantity
                        try {
                            denominatorQuantity = DomainObjectUtil.convertStringToDouble(dataset.answerIds.first())
                        } catch (Exception e) {
                            loggerUtil.error("En error occurred during rendering ResultPerDenominator", e)
                        }

                        if (requireMonthly && denominator.requireMonthly) {
                            Map<String, Double> denominatorResultsMonthly
                            denominatorResultRows = "${denominatorResultRows}<tr><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.name ? denominator.localizedName : ""}</td>"

                            calculationRules.each { CalculationRule calculationRule ->
                                Double totalForRule = entity.getTotalResult(indicator?.indicatorId, calculationRule.calculationRuleId)
                                denominatorResultsMonthly = entity.getResultByDynamicDenominator(indicator.indicatorId,
                                        calculationRule.calculationRuleId, dataset.manualId, requireMonthly, denominator)

                                Constants.MONTHS.each { String month ->
                                    Double value = denominatorResultsMonthly?.get(month)

                                    if (value != null) {
                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                                indicator, value, Boolean.FALSE, Boolean.TRUE, "")
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(value)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\"></td>"
                                    }

                                }

                                if (totalForRule && denominatorQuantity) {
                                    Double sum = totalForRule / denominatorQuantity

                                    if (sum != null) {
                                        if (resultManipulators) {
                                            resultManipulators.each {
                                                sum = denominatorUtil.useResultManipulatorForDoubleValue(it, entity, sum, resourceCache)
                                            }
                                        }
                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                                indicator, sum, Boolean.FALSE, Boolean.TRUE, "")
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(sum)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                                    }
                                }
                            }
                            denominatorResultRows = "${denominatorResultRows}<td colspan=\"20\"></td></tr>"
                        } else {
                            denominatorResultRows = "${denominatorResultRows}<tr class=\"monthlyRequiredNonMonthly\"><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.name ? denominator.localizedName : ""}</td><td colspan=\"12\"></td>"

                            calculationRules.each { CalculationRule calculationRule ->
                                Double totalForRule = entity.getTotalResult(indicator?.indicatorId, calculationRule.calculationRuleId)

                                if (totalForRule && denominatorQuantity) {
                                    Double sum = totalForRule / denominatorQuantity

                                    if (sum != null) {
                                        if (resultManipulators) {
                                            resultManipulators.each {
                                                sum = denominatorUtil.useResultManipulatorForDoubleValue(it, entity, sum, resourceCache)
                                            }
                                        }
                                        String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                                indicator, sum, Boolean.FALSE, Boolean.TRUE, unit)
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(sum)}</td>"
                                    } else {
                                        denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                                    }
                                }
                            }
                            denominatorResultRows = "${denominatorResultRows}<td colspan=\"1\"></td></tr>"
                        }
                    }
                } else {
                    unit = denominatorUtil.getLocalizedUnit(denominator)

                    if (requireMonthly) {
                        denominatorResultRows = "${denominatorResultRows}<tr><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.localizedName}</td>"

                        calculationRules.each { CalculationRule calculationRule ->
                            Map<String, Double> denominatorResultsMonthly = entity.getTotalResultByNonDynamicDenominator(indicator?.indicatorId, calculationRule.calculationRuleId,
                                    denominator.denominatorId, requireMonthly)
                            Double sum

                            Constants.MONTHS.each { String month ->
                                Double value = denominatorResultsMonthly?.get(month)

                                if (value != null) {
                                    sum = sum ? sum + value : value
                                    String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                            indicator, value, Boolean.FALSE, Boolean.TRUE, "")
                                    denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(value)}</td>"
                                } else {
                                    denominatorResultRows = "${denominatorResultRows}<td class=\"text-right monthly\"></td>"
                                }
                            }

                            if (sum != null) {
                                String scorePerDynamicDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                        indicator, sum, Boolean.FALSE, Boolean.TRUE, "")
                                denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDynamicDenominator ? scorePerDynamicDenominator : numberFormat?.format(sum)}</td>"
                            } else {
                                denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\"></td>"
                            }
                        }
                    } else {
                        denominatorResultRows = "${denominatorResultRows}<tr class=\"hideIfQuarterly\" ><td colspan=\"${colSpan}\">&nbsp;</td><td>${denominator.localizedName}</td>"
                        String scorePerDenominator

                        calculationRules.each { CalculationRule calculationRule ->
                            Double value = entity.getTotalResultByNonDynamicDenominator(indicator?.indicatorId, calculationRule.calculationRuleId, denominator.denominatorId)
                            scorePerDenominator = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user,
                                    indicator, value, Boolean.FALSE, Boolean.TRUE, unit)

                            denominatorResultRows = "${denominatorResultRows}<td class=\"text-right\">${scorePerDenominator ? scorePerDenominator + " " + (unit ? unit : '') : '&nbsp;'}</td>"
                        }
                    }
                    denominatorResultRows = "${denominatorResultRows}<td colspan=\"20\"></td></tr>"
                }
            }
            loggerUtil.info(log, "Time it took to render denominator result rows: ${System.currentTimeMillis() - now} ms")
            out << denominatorResultRows

        }
    }

    def renderResultCompareButton = { attrs ->
        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        List<IndicatorReportItem> reportItems = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator?.report?.reportItems)
        List<CalculationRule> calculationRules = []
        def nonNumericResult = indicator?.nonNumericResult
        List<CalculationRule> allRules = indicator?.getResolveCalculationRules(parentEntity)

        if (allRules) {
            if (reportItems) {
                reportItems?.findAll({ it.rules })?.each { IndicatorReportItem reportItem ->
                    reportItem.rules?.each { String rule ->
                        CalculationRule calculationRule = allRules?.find({
                            rule.equals(it.calculationRuleId)
                        })

                        if (!calculationRule) {
                            calculationRule = allRules?.find({
                                rule.equals(it.calculationRule)
                            })
                        }

                        if (calculationRule && !calculationRules.contains(calculationRule)) {
                            calculationRules.add(calculationRule)
                        }
                    }
                }
                if (calculationRules.isEmpty()) {
                    calculationRules = allRules
                }
            } else {
                calculationRules = allRules
            }
        }

        String compareDropdown = ""

        if (indicator && entity && parentEntity && calculationRules && !nonNumericResult) {
            List<Entity> toCompareEntities

            if (parentEntity) {
                String entityClass = entity.entityClass

                if ("design".equals(entityClass)) {
                    toCompareEntities = parentEntity.getDesigns()
                } else {
                    toCompareEntities = parentEntity.getOperatingPeriods()
                }

                if (toCompareEntities) {
                    toCompareEntities.removeAll({ it.id.equals(entity.id) })
                }

                if (toCompareEntities) {
                    Boolean originalResults = entityService.hasCalculationResultsObjects(entity, indicator?.indicatorId)
                    Boolean hideCompare = Boolean.FALSE
                    List<String> compareEntitiesWithCalculationResultsObjects = toCompareEntities.findResults { Entity compareEntity ->
                        Boolean hasResults = entityService.hasCalculationResultsObjects(compareEntity, indicator?.indicatorId)
                        hasResults ? compareEntity.id.toString() : null
                    }
                    if (!originalResults && !compareEntitiesWithCalculationResultsObjects) {
                        hideCompare = Boolean.TRUE
                    }

                    if (!hideCompare) {
                        compareDropdown = "<a class=\"dropdown-toggle btn btn-primary ${session?.showStartupTips ? 'startupTip' : ''}\" ${session?.showStartupTips ? 'rel=\"startupTip_top\"' : ''} data-content=\"${message(code: 'startup-tip.results_compare')}\" href=\"#\" data-toggle=\"dropdown\"> ${entity.operatingPeriod ? message(code: 'entity.compare_periods') : message(code: 'entity.compare')} <span class=\"caret\"></span></a><ul class=\"dropdown-menu\">"
                        toCompareEntities.each { Entity compareEntity ->
                            List<String> calcRulesStrings = calculationRules?.unique()?.collect({
                                it.calculationRuleId
                            })
                            String rules

                            calcRulesStrings.each { String rule ->
                                rules = rules ? rules + "," + rule : rule
                            }
                            Boolean checkIfResults = compareEntitiesWithCalculationResultsObjects.contains(compareEntity?.id?.toString())
                            if (checkIfResults && originalResults) {
                                String reportItemIds = reportItems?.findAll({ it.reportItemId })?.collect({
                                    it.reportItemId
                                })?.join(",")
                                compareDropdown = compareDropdown + "<li><a href=\"javascript:;\" onclick=\"compareResults('${entity.id}', '${compareEntity.id}', '${indicator.indicatorId}', '${rules}','${parentEntity?.id}','${reportItemIds ? reportItemIds : ""}');\"> ${compareEntity.operatingPeriodAndName}</a></li>"
                            }
                        }

                        compareDropdown = compareDropdown + "</ul>"

                    }
                }

            }
        }
        out << compareDropdown
    }

    def renderIndicatorReport = { attrs ->
        loggerUtil.info(log, "Started rendering indicator report")
        long now = System.currentTimeMillis()
        Indicator indicator = attrs.indicator
        IndicatorReport indicatorReport = attrs.report
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Boolean hideBreakdown = attrs.hideBreakdown
        Boolean isCompare = attrs.isCompare
        def graphDatasets = attrs.graphDatasets
        //sw-1597
        List<Document> nmdElementList = attrs.nmdElementList

        def reportContent = ""

        if (indicatorReport && indicator && entity) {
            List<IndicatorReportItem> reportItems = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator?.report?.reportItems)
            List<String> sources = reportItems?.findAll({ IndicatorReportItem t -> t.tableSource })?.collect({ IndicatorReportItem it -> it.tableSource })?.flatten() as List<String>
            String tabSource = sources?.size() > 0 ? sources?.get(0) : ''
            int countReportItem = 0
            reportItems?.each { IndicatorReportItem reportItem ->
                Boolean collapseThis = reportItems.find({ it.type == "heading" && it.collapseItemIds?.contains(reportItem.reportItemId) })

                loggerUtil.info(log, "Handling reportItem: \"${reportItem.type}, source: (${reportItem.tableSource ?: 'undefined'})\", Time elapsed from start: ${System.currentTimeMillis() - now} ms")
                if ((!reportItem.showReportItemIf || valueReferenceService.resolveIfShown(reportItem.showReportItemIf, entity)) && (!reportItem.dontShowInCompare || !isCompare)) {
                    if (!graphDatasets && ("heading".equals(reportItem.type))) {
                        if (reportItem.collapseItemIds) {
                            reportContent = "${reportContent}<div class=\"sectionheader_level2\" data-report-item-ids=\"${reportItem.collapseItemIds.join(".")}\" onclick=\"collapseTable(this, '${reportItem.collapseItemIds.join(".")}', true)\"><h2><i style=\"margin-top: 3px; margin-right: 5px;\" class=\"fa fa-plus collapserIcon\"></i><span${reportItem.textColor ? ' style=\"color: #' + reportItem.textColor + ';\"' : ''}>${indicatorService.getLocalizedText(reportItem)}</span></h2></div>"
                        } else {
                            IndicatorExpandFeature expandFeature = indicator?.resolveExpandFeature
                            if (!("queryContent".equals(tabSource)) && countReportItem == 0 && !indicator.preventResultSummaryDownload) {
                                reportContent = "${reportContent}<h2><span${reportItem.textColor ? ' style=\"color: #' + reportItem.textColor + ';\"' : ''}>${indicatorService.getLocalizedText(reportItem)} &nbsp;&nbsp; <a style=\"font-size:12px;font-weight: normal;\" href=\"javascript:\"\n" +
                                        "onclick=\"fullScreenPopup('${createLink(controller: 'entity', action: 'expandResults', params: [entityId: entity?.id, parentId: parentEntity?.id, indicatorId: indicator?.indicatorId, downloadSummary: true])}', '${indicatorService.localizedDurationWarning}');\">${message(code: 'download.results.summary')}</a></span></h2>"
                            } else {
                                reportContent = "${reportContent}<h2><span${reportItem.textColor ? ' style=\"color: #' + reportItem.textColor + ';\"' : ''}>${indicatorService.getLocalizedText(reportItem)}</span></h2>"
                            }
                        }
                    } else if ("table".equals(reportItem.type)) {
                        if ("queryContent".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getTableContentByQueryContent(entity, reportItem, indicator, parentEntity, collapseThis ,nmdElementList)}"
                        } else if ("default".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getDefaultTableContainer(indicator, entity, reportItem, (hideBreakdown != null ? hideBreakdown.booleanValue() : false), parentEntity, collapseThis)}"
                        } else if ("invertedTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getInvertedTableContent(entity, indicator, reportItem.rules, reportItem.categories, reportItem.tableSettings, reportItem.reportItemId, collapseThis)}"
                        } else if ("normalTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getDefaultTableContainer(indicator, entity, reportItem, (hideBreakdown != null ? hideBreakdown.booleanValue() : true), parentEntity, collapseThis)}"
                        } else if ("specifiedTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getSpecifiedTableContent(entity, reportItem.tableRowObjects, reportItem.reportItemId, collapseThis)}"
                        }
                    } else if (("text".equals(reportItem.type))) {
                        String formatting = reportItem.textFormatting
                        String text

                        if (reportItem.textColor) {
                            text = "<span style=\"color: #${reportItem.textColor};\">${indicatorService.getLocalizedText(reportItem)}</span>"
                        } else {
                            text = indicatorService.getLocalizedText(reportItem)
                        }

                        if ("bold".equals(formatting)) {
                            reportContent = "${reportContent}<p class=\"${reportItem.reportItemId}\"${collapseThis ? " style=\"display: none;\"" : ""}><strong class=\"strong\">${text}</strong></p>"
                        } else {
                            reportContent = "${reportContent}<p class=\"${reportItem.reportItemId}\"${collapseThis ? " style=\"display: none;\"" : ""}>${text}</p>"
                        }
                    } else if ("image".equals(reportItem.type)) {
                        reportContent = "${reportContent}${getParentEntityImage(entity, reportItem)}"
                    } else if ("section".equals(reportItem.type)) {
                        reportContent = "${reportContent}${renderSectionByReportItem(entity, reportItem, indicator, parentEntity, collapseThis, nmdElementList)}"
                    }
                }
                if("heading".equals(reportItem.type)) {
                    countReportItem++
                }
            }
        }
        loggerUtil.info(log, "Time it took to handle indicator report: ${System.currentTimeMillis() - now} ms")
        out << reportContent
    }
    def renderIndicatorReportByItem = { attrs ->
        loggerUtil.info(log, "Started rendering indicator report")
        long now = System.currentTimeMillis()
        String reportItemId = attrs.itemId
        Indicator indicator = attrs.indicator
        IndicatorReport indicatorReport = attrs.report
        Entity entity = attrs.entity
        Entity parentEntity = attrs.parentEntity
        Boolean hideBreakdown = attrs.hideBreakdown
        Boolean isCompare = attrs.isCompare
        def graphDatasets = attrs.graphDatasets
        Boolean hideEmpty = Boolean.TRUE
        Boolean hideLinkAndExplain = Boolean.TRUE

        def reportContent = ""

        if (indicatorReport && indicator && entity) {
            List<IndicatorReportItem> reportItems = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator?.report?.reportItems)

            reportItems?.each { IndicatorReportItem reportItem ->
                Boolean collapseThis = reportItems.find({ it.type == "heading" && it.collapseItemIds?.contains(reportItem.reportItemId) })

                loggerUtil.info(log, "Handling reportItem: \"${reportItem.type}, source: (${reportItem.tableSource ?: 'undefined'})\", Time elapsed from start: ${System.currentTimeMillis() - now} ms")
                if ((!reportItem.showReportItemIf || valueReferenceService.resolveIfShown(reportItem.showReportItemIf, entity)) && (!reportItem.dontShowInCompare || !isCompare)) {
                    if ("table".equals(reportItem.type) && reportItem?.reportItemId?.equalsIgnoreCase(reportItemId)) {
                        if ("queryContent".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getTableContentByQueryContent(entity, reportItem, indicator, parentEntity, collapseThis)}"
                        } else if ("default".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getDefaultTableContent(indicator, entity, reportItem, (hideBreakdown != null ? hideBreakdown.booleanValue() : false), parentEntity, collapseThis, hideEmpty, hideLinkAndExplain)}"
                        } else if ("invertedTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getInvertedTableContent(entity, indicator, reportItem.rules, reportItem.categories, reportItem.tableSettings, reportItem.reportItemId, collapseThis)}"
                        } else if ("normalTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getDefaultTableContent(indicator, entity, reportItem, (hideBreakdown != null ? hideBreakdown.booleanValue() : true), parentEntity, collapseThis)}"
                        } else if ("specifiedTable".equals(reportItem.tableSource)) {
                            reportContent = "${reportContent}${getSpecifiedTableContent(entity, reportItem.tableRowObjects, reportItem.reportItemId, collapseThis)}"
                        }
                    }
                }
            }
        }
        loggerUtil.info(log, "Time it took to handle indicator report: ${System.currentTimeMillis() - now} ms")
        out << reportContent
    }

    def renderDefaultTableContent = { attrs ->

        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        IndicatorReportItem reportItem = attrs.reportItem
        boolean hideBreakdown = attrs.hideBreakdown
        Entity parentEntity = attrs.parentEntity
        Boolean collapseThis = attrs.collapseThis
        Boolean hideEmpty = attrs.hideEmpty
        Boolean hideLinkAndExplain = attrs.hideLinkAndExplain

        out << getDefaultTableContent(indicator, entity, reportItem, hideBreakdown, parentEntity, collapseThis, hideEmpty, hideLinkAndExplain)
    }

    private String renderSectionByReportItem(Entity entity, IndicatorReportItem reportItem, Indicator indicator, Entity parentEntity, Boolean collapseThis ,List<Document> nmdElementList = null) {
        String content = ""
        if ("section".equals(reportItem?.type)) {
            content = "${content}<div class=\"container section\">"

            reportItem.reportItems?.each { IndicatorReportItem childItem ->
                if ("heading".equals(childItem.type)) {
                    content = "${content}<div class=\"sectionheader\">"

                    if (reportItem.collapsed) {
                        content = "${content}<button class=\"pull-left sectionexpander\"><i class=\"icon icon-chevron-down collapser\"></i><i class=\"icon icon-chevron-right expander\"></i></button>"
                    } else {
                        content = "${content}<button class=\"pull-left sectionexpander\"><i class=\"icon icon-chevron-down expander\"></i><i class=\"icon icon-chevron-right collapser\"></i></button>"
                    }
                    content = "${content}<h2 class=\"h2expander\"><span${childItem.textColor ? ' style=\"color: #' + childItem.textColor + ';\"' : ''}>${indicatorService.getLocalizedText(childItem)}</span></h2></div>"
                } else {
                    content = "${content}<div class=\"sectionbody\"${reportItem.collapsed ? ' style=\"display: none\"' : ''}>"

                    if ("table".equals(childItem.type)) {
                        if ("queryContent".equals(childItem.tableSource)) {
                            content = "${content}${getTableContentByQueryContent(entity, childItem, indicator, parentEntity, collapseThis, nmdElementList)}"
                        } else if ("default".equals(childItem.tableSource)) {
                            content = "${content}${getDefaultTableContent(indicator, entity, childItem, false, parentEntity, collapseThis)}"
                        } else if ("invertedTable".equals(childItem.tableSource)) {
                            content = "${content}${getInvertedTableContent(entity, indicator, childItem.rules, childItem.categories, childItem.tableSettings, childItem.reportItemId, collapseThis)}"
                        } else if ("normalTable".equals(childItem.tableSource)) {
                            content = "${content}${getDefaultTableContent(indicator, entity, childItem, false, parentEntity, collapseThis)}"
                        } else if ("specifiedTable".equals(childItem.tableSource)) {
                            content = "${content}${getSpecifiedTableContent(entity, childItem.tableRowObjects, childItem.reportItemId, collapseThis)}"
                        }
                    } else if ("text".equals(childItem.type)) {
                        String formatting = childItem.textFormatting
                        String text

                        if (childItem.textColor) {
                            text = "<span style=\"color: #${childItem.textColor};\">${indicatorService.getLocalizedText(childItem)}</span>"
                        } else {
                            text = indicatorService.getLocalizedText(childItem)
                        }

                        if ("bold".equals(formatting)) {
                            content = "${content}<p class=\"${childItem.reportItemId}\"${collapseThis ? " style=\"display: none;\"" : ""}><strong class=\"strong\">${text}</strong></p>"
                        } else {
                            content = "${content}<p class=\"${childItem.reportItemId}\"${collapseThis ? " style=\"display: none;\"" : ""}>${text}</p>"
                        }
                    } else if ("image".equals(childItem.type)) {
                        content = "${content}${getParentEntityImage(entity, childItem)}"
                    }
                    content = "${content}</div>"
                }
            }
            content = "${content}</div>"
        }
    }

    private String getParentEntityImage(Entity entity, IndicatorReportItem reportItem) {
        String entityImage

        if (entity) {
            Map imageParams = reportItem.imageParams
            Entity parent

            if (entity.parentEntityId) {
                parent = entity.getParentById()
            }
            def images
            def defaultImage

            if (parent) {
                defaultImage = opt.entityIcon(entity: parent)
                images = entityService.getEntityImages(parent.id, null)
            } else {
                defaultImage = opt.entityIcon(entity: parent)
                images = entityService.getEntityImages(entity.id, null)
            }
            def maxWidth = imageParams?.get("maxWidth")
            def maxHeight = imageParams?.get("maxHeight")

            if (images) {
                EntityFile file = images[0]

                try {
                    Base64 encoder = new Base64()
                    def data = encoder.encodeBase64String(file.data)
                    entityImage = "<img src=\"data:" + file.contentType + ";base64," + data + "\" style=\"${maxWidth ? 'max-width: ' + maxWidth + 'px;' : ''}${maxHeight ? ' max-height: ' + maxHeight + 'px;' : ''}\" class=\"entityImg\" />"
                } catch (Exception e) {
                }
            } else if (imageParams?.get("useEntityDefaultImage")) {
                entityImage = "<i class=\"entitytype${entity.smallImage ? '-smaller' : ''} ${defaultImage}${entity.smallImage ? '-smaller' : ''}\"></i>"
            }
        }
        return entityImage ? entityImage : ""
    }

    private String getSpecifiedTableContent(Entity entity, List<ReportTableRow> tableRows, String reportItemId, Boolean collapseThis = Boolean.FALSE) {
        String content = ""

        if (entity && tableRows) {
            content = "<table class=\"table table-striped table-condensed table-cellvaligntop${reportItemId ? " " + reportItemId : ''}\"${collapseThis ? " style=\"display: none;\"" : ""}>"

            tableRows.each { ReportTableRow row ->
                content = "${content}<tr>"
                row.tableCells?.each { ReportTableCell cell ->
                    content = "${content}${getReportTableCell(entity, cell)}"
                }
                content = "${content}</tr>"
            }
            content = "${content}</table>"
        }
        return content
    }

    private String getReportTableCell(Entity entity, ReportTableCell reportTableCell) {
        String value

        if (reportTableCell.text) {
            value = reportTableCell.localizedText
        } else if (reportTableCell.valueReference) {
            ValueReference valueReference = reportTableCell.valueReference

            if ("basicQuery".equals(valueReference?.queryId) && entity.parentEntityId) {
                Entity parent = entity.getParentById()
                value = valueReferenceService.getValueForEntity(valueReference, parent, Boolean.TRUE)
            } else {
                value = valueReferenceService.getValueForEntity(valueReference, entity, Boolean.TRUE)
            }

            if (value && valueReference.queryId && valueReference.questionId) {
                Question question = queryService.getQueryByQueryId(valueReference.queryId, true)?.getAllQuestions()?.find({ Question q -> q.questionId.equals(valueReference.questionId) })

                if (("select".equals(question?.inputType) || "checkbox".equals(question?.inputType)) && !question.isResourceQuestion) {
                    value = question.choices?.find({ QuestionAnswerChoice qac -> value.equals(qac.answerId) })?.localizedAnswer
                }
            }
        } else if (reportTableCell.valueReferences) {
            reportTableCell.valueReferenceObjects?.each { ValueReference valueReference ->
                def partValue

                if ("basicQuery".equals(valueReference?.queryId) && entity.parentEntityId) {
                    Entity parent = entity.getParentById()
                    partValue = valueReferenceService.getValueForEntity(valueReference, parent, Boolean.TRUE)
                } else {
                    partValue = valueReferenceService.getValueForEntity(valueReference, entity, Boolean.TRUE)
                }

                if (partValue) {
                    value = value ? value + " " + partValue : partValue
                }
            }
        }

        if (value) {
            Integer colspan = reportTableCell.colspan
            String formatting = reportTableCell.textFormatting
            String color = reportTableCell.textColor

            if (color) {
                value = "<span style=\"color: #${color};\">${value}</span>"
            }

            if (formatting) {
                if ("bold".equals(formatting)) {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}><strong>${value}</strong></td>"
                } else if ("italic".equals(formatting)) {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}><i>${value}</i></td>"
                } else if ("columnHeading".equals(formatting)) {
                    value = "<th${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value}</th>"
                } else {
                    value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value}</td>"
                }
            } else {
                value = "<td${colspan ? ' colspan=\"' + colspan + '\"' : ''}>${value?.isNumber() ? value?.toDouble()?.round() : value}</td>"
            }
        }
        return value ? value : "<td>&nbsp;</td>"
    }

    private String getInvertedTableContent(Entity entity, Indicator indicator, List<String> calculationRuleIds,
                                           List<String> resultCategories, Map tableSettings, String reportItemId,
                                           Boolean collapseThis = Boolean.FALSE) {
        String content = ""

        if (entity && indicator && calculationRuleIds) {
            Entity parentEntity = entity.parentEntityId ? entity.parentById : entity
            List<CalculationRule> indicatorCalculationRules = indicator.getResolveCalculationRules(parentEntity)
            Map<String, CalculationResult> resultsForIndicatorPerCalculationRule = entity.getCalculationResultObjects(indicator.indicatorId, null, null)?.groupBy { it.calculationRuleId }
            if (!indicatorCalculationRules && indicator.applicationRules) {
                flash.errorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
            }
            List<ResultCategory> indicatorResultCategories = []

            List<ResultCategory> resolvedCategories = indicator.getResolveResultCategories(parentEntity)

            if (resultCategories) {
                resultCategories.each { String categoryId ->
                    ResultCategory resultCategory = resolvedCategories?.find({
                        categoryId.equals(it.resultCategoryId)
                    })

                    if (!resultCategory) {
                        resultCategory = resolvedCategories?.find({
                            categoryId.equals(it.resultCategory)
                        })
                    }

                    if (resultCategory) {
                        indicatorResultCategories.add(resultCategory)
                    }
                }
            } else {
                indicatorResultCategories = resolvedCategories
            }

            if (indicatorCalculationRules && indicatorResultCategories && resultsForIndicatorPerCalculationRule) {
                def leftmostColumnWidth = tableSettings?.get("resultTableInvertedLeftmostColumnWidth")
                Boolean showTotal

                if (tableSettings?.get("showTotals") == false) {
                    showTotal = Boolean.FALSE
                } else {
                    showTotal = Boolean.TRUE
                }
                content = "<table class=\"table table-striped table-condensed table-cellvaligntop${reportItemId ? " " + reportItemId : ''}\"${collapseThis ? " style=\"display: none;\"" : ""}>"

                if (leftmostColumnWidth) {
                    content = content + "<tr><th style=\"width: ${leftmostColumnWidth}px;\">&nbsp;</th>"
                } else {
                    content = content + "<tr><th>&nbsp;</th>"
                }

                indicatorResultCategories.each { ResultCategory category ->
                    content = content + "<th>" + category.localizedName + "</th>"
                }

                if (showTotal) {
                    content = content + "<th>${message(code: 'totalScore')}</th>"
                }
                content = content + "</tr>"
                String score
                int index = 0

                User user = userService.getCurrentUser()
                ResultFormatting resultFormatting = resultFormattingResolver.resolveResultFormattingWithParams(indicator, indicator?.displayDenominator, session, Boolean.FALSE, Boolean.FALSE)

                calculationRuleIds.each { String ruleId ->
                    CalculationRule calculationRule = indicatorCalculationRules?.find({ CalculationRule calcRule -> calcRule.calculationRuleId.equals(ruleId) })

                    if (!calculationRule) {
                        calculationRule = indicatorCalculationRules?.find({ CalculationRule calcRule -> calcRule.calculationRule.equals(ruleId) })
                    }

                    if (calculationRule) {
                        if (leftmostColumnWidth) {
                            content = content + "<tr><td style=\"width: ${leftmostColumnWidth}px;\"><strong>" + calculationRule.localizedName + " " + calculationRuleService.getLocalizedUnit(calculationRule, indicator) + "</strong></td>"
                        } else {
                            content = content + "<tr><td><strong>" + calculationRule.localizedName + " " + calculationRuleService.getLocalizedUnit(calculationRule, indicator) + "</strong></td>"
                        }
                        def total

                        List<CalculationResult> resultsByCalculationRule = resultsForIndicatorPerCalculationRule?.getAt(ruleId)
                        indicatorResultCategories.each { ResultCategory resultCategory ->
                            def points

                            if (resultCategory.sumOfCategories) {
                                points = getSumOfCategories(resultsByCalculationRule, indicator, ruleId, resultCategory.sumOfCategories, resolvedCategories)
                            } else {
                                String resultCategoryId = resultCategory.resultCategoryId
                                points = resultsByCalculationRule.find({
                                    it.resultCategoryId.equals(resultCategoryId)
                                })?.result
                            }

                            if (showTotal && points) {
                                total = total ? total + points : points
                            }
                            score = resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, points, Boolean.FALSE, Boolean.FALSE, "")
                            score = score ? score : '&nbsp;'
                            content = content + "<td>" + score + "</td>"
                        }

                        if (showTotal) {
                            if (indicatorResultCategories.size() > 1 && total != null) {
                                content = content + "<td>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, total, Boolean.FALSE, Boolean.FALSE, "")}</td>"
                            } else {
                                content = content + "<td>${resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, total, Boolean.FALSE, Boolean.FALSE, "")}</td>"
                            }
                        }
                        content = content + "</tr>"
                    }
                    index++
                }
                content = content + "</table>"
            }
        }
        return content
    }

    private Double getSumOfCategories(List<CalculationResult> calculationResultsByCalculationRule, Indicator indicator, String calculationRuleId, List<String> sumOfCategories, List<ResultCategory> resolvedCategories) {
        Double sum

        if (calculationResultsByCalculationRule && calculationRuleId && sumOfCategories) {
            List<ResultCategory> resultCategories = resolvedCategories?.findAll({
                sumOfCategories.contains(it.resultCategory)
            })

            if (resultCategories) {
                resultCategories?.each { ResultCategory resultCategory ->
                    Double resultByCategory = calculationResultsByCalculationRule.find({
                        it.resultCategoryId.equals(resultCategory.resultCategoryId)
                    })

                    if (resultByCategory) {
                        sum = sum ? sum + resultByCategory : resultByCategory
                    }
                }
            }
        }
        return sum
    }

    private String getDefaultTableContent(Indicator indicator, Entity entity, IndicatorReportItem reportItem, boolean hideBreakdown, Entity parentEntity = null, Boolean collapseThis = Boolean.FALSE, Boolean hideEmpty = Boolean.FALSE, Boolean hideLinkAndExplain = Boolean.FALSE) {
        List<CalculationRule> calculationRules = []
        List<ResultCategory> resultCategories = []
        List<ResultCategory> allResultCategories = indicator.getResolveResultCategories(parentEntity)
        List<CalculationResult> resultsForIndicator = entity.getCalculationResultObjects(indicator.indicatorId, null, null)
        String reportItemId = reportItem?.reportItemId
        Boolean renderMonthlyView = Boolean.FALSE
        long start = System.currentTimeMillis()
        if (reportItem?.rules) {
            List<CalculationRule> resolvedRules = indicator.getResolveCalculationRules(parentEntity)

            reportItem.rules.each { String rule ->
                CalculationRule calculationRule = resolvedRules?.find({
                    rule.equals(it.calculationRuleId)
                })

                if (!calculationRule) {
                    calculationRule = resolvedRules?.find({
                        rule.equals(it.calculationRule)
                    })
                }

                if (calculationRule) {
                    calculationRules.add(calculationRule)
                }
            }
        } else {
            calculationRules = indicator.getResolveCalculationRules(parentEntity)
        }

        if (!calculationRules && indicator.applicationRules) {
            flash.errorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
        }

        if (reportItem?.categories && !reportItem.categories.isEmpty()) {
            reportItem.categories.each { String category ->
                ResultCategory resultCategory = allResultCategories?.find({
                    category.equals(it.resultCategoryId)
                })

                if (!resultCategory) {
                    resultCategory = allResultCategories?.find({
                        category.equals(it.resultCategory)
                    })
                }

                if (resultCategory) {
                    resultCategories.add(resultCategory)
                }
            }
        } else {
            resultCategories = allResultCategories
        }
        Boolean hideTotals

        if (reportItem?.tableSettings?.get("showTotals") == false) {
            hideTotals = Boolean.TRUE
        } else {
            hideTotals = Boolean.FALSE
        }

        Boolean showDenominators = Boolean.TRUE
        if (reportItem?.tableSettings?.get("showDenominators") == false) {
            showDenominators = Boolean.FALSE
        }

        ResultFormatting resultFormatting = resultFormattingResolver.resolveResultFormattingWithParams(indicator, null, session, Boolean.FALSE, Boolean.FALSE, reportItemId)

        renderMonthlyView = indicator?.requireMonthly ? Boolean.TRUE : indicator.denominatorListAsDenominatorObjects?.find({ Denominator d -> d.requireMonthly }) ? Boolean.TRUE : Boolean.FALSE
        // String idForGraphs = StringUtils.remove(UUID.randomUUID().toString(), "-")
        int colSpan = 1
        if (calculationRules && !calculationRules.isEmpty() && resultCategories && !resultCategories.isEmpty()) {
            log.info("time it took to handle default table content: ${System.currentTimeMillis() - start} ms")
            return render(template: '/entity/defaultResultTable', model: [childEntity                                   : entity, parentEntity: parentEntity, indicator: indicator,
                                                                          calculationRules                              : calculationRules, resultCategories: resultCategories,
                                                                          hideBreakdown                                 : hideBreakdown, hideTotals: hideTotals,
                                                                          /* idForGraphs     : idForGraphs, */ collapsed: reportItem?.collapsed, hideLinkAndExplain: hideLinkAndExplain, hideEmpty: hideEmpty,
                                                                          useShortNames                                 : reportItem?.useRuleShortNames ? true : false,
                                                                          colSpan                                       : colSpan, reportItemId: reportItemId, renderMonthlyView: renderMonthlyView,
                                                                          showDenominators                              : showDenominators, expandableCategories: reportItem?.expandableCategories,
                                                                          allResultCategories                           : allResultCategories, collapseThis: collapseThis,
                                                                          resultFormatting                              : resultFormatting, resultsForIndicator: resultsForIndicator])
        } else {
            return ""
        }
    }

    private String getDefaultTableContainer(Indicator indicator, Entity entity, IndicatorReportItem reportItem,
                                            boolean hideBreakdown, Entity parentEntity = null, Boolean collapseThis = Boolean.FALSE,
                                            Boolean hideEmpty = Boolean.FALSE, Boolean hideLinkAndExplain = Boolean.FALSE) {

        if (indicator && entity && reportItem) {
            return render(template: '/entity/defaultResultTableContainer', model: [
                    indicatorId       : indicator.indicatorId,
                    entityId          : entity.id,
                    reportItemId      : reportItem.reportItemId,
                    hideBreakdown     : hideBreakdown,
                    parentEntityId    : parentEntity?.id,
                    collapseThis      : collapseThis,
                    hideEmpty         : hideEmpty,
                    hideLinkAndExplain: hideLinkAndExplain
            ])
        }
        return ""
    }

    private String getTableContentByQueryContent(Entity entity, IndicatorReportItem reportItem, Indicator indicator, Entity parentEntity, Boolean collapseThis = Boolean.FALSE, List<Document> nmdElementList = null) {
        String table = ""
        Map<String, List<String>> queryContent = reportItem.queryContent
        Map<String, List<String>> hideQuestions = indicator.hideQuestions
        List<Dataset> datasets = new ArrayList<Dataset>()
        List<Question> questions = new ArrayList<Question>()
        List<Question> additionalQuestions = new ArrayList<Question>()
        def showQuestion = reportItem.showQuestion
        boolean showQuantity = false

        if (queryContent) {
            int index = 0

            queryContent?.each { String queryId, List<String> sectionIds ->
                List<String> questionIds

                try {
                    String questionsString = queryContent.keySet().toList().get((index + 1))

                    if ("questions".equals(questionsString)) {
                        questionIds = queryContent.get(questionsString)
                    }
                } catch (Exception e) {

                }
                Query query = queryService.getQueryByQueryId(queryId, true)

                if (query) {
                    sectionIds?.each { String sectionId ->
                        QuerySection querySection = query.getAllSections()?.find({ QuerySection querySection -> sectionId.equals(querySection?.sectionId) })
                        List<Question> questionsBySection = query.getQuestionsBySection(sectionId)

                        if (questionIds) {
                            questionsBySection = questionsBySection?.findAll({ questionIds.contains(it.questionId) })
                        }

                        if (questionsBySection) {
                            Question questionWithQuantity = questionsBySection.find({ Question question -> question?.defineQuantity(querySection) })

                            if (questionWithQuantity) {
                                showQuantity = true
                            }
                            questions.addAll(questionsBySection)
                        }
                        def datasetsBySection = datasetService.getDatasetsByEntityAndQueryIdAndSectionId(entity, queryId, sectionId)
                        List<IndicatorReportItemFilter> filters = reportItem.filters

                        if (!datasetsBySection) {
                            datasetsBySection = datasetService.getDatasetsByEntityAndQueryIdAndSectionId(parentEntity, queryId, sectionId)
                        }

                        if (datasetsBySection) {
                            if (filters) {
                                filters.each { IndicatorReportItemFilter filter ->
                                    datasetsBySection = datasetsBySection?.findAll({ Dataset dataset ->
                                        filter.acceptValues?.collect({
                                            it.toUpperCase()
                                        })?.contains(datasetService.getAdditionalQuestionAnswersForReport(entity, indicator, dataset)?.get(filter.filterParameter)?.toString()?.toUpperCase())
                                    })
                                }
                            }

                            if (questionIds && datasetsBySection) {
                                datasetsBySection = datasetsBySection.findAll({ questionIds.contains(it.questionId) })
                            }

                            if (datasetsBySection) {
                                datasets.addAll(datasetsBySection.toList())
                            }
                        }
                    }
                }
                index++
            }

            if (!datasets.isEmpty()) {
                Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, ["epdDownload"])
                Boolean epdDownloadLicensed = featuresAllowed.get("epdDownload")
                datasets = datasets?.sort({ it.questionId })
                table = "<table class=\"table table-striped table-condensed table-cellvaligntop${reportItem.reportItemId ? " " + reportItem.reportItemId : ""}\"${collapseThis ? " style=\"display: none;\"" : ""}>"

                if (reportItem.additionalQuestions != null) {
                    getAdditionalQuestionsForQueryContent(reportItem.additionalQuestions)?.each { Question additionalQuestion ->
                        if (additionalQuestion && !hideQuestions?.get(additionalQuestion.sectionId)?.contains(additionalQuestion.questionId)) {
                            additionalQuestions.add(additionalQuestion)
                        }
                    }
                } else {
                    List<Question> uniqueAdditionalQuestions = new ArrayList<Question>()

                    questions?.each { Question q ->
                        List<Question> additionals = q.getAdditionalQuestions(indicator, null, null, parentEntity)?.findAll({ Question additionalQ -> !uniqueAdditionalQuestions.contains(additionalQ) })

                        if (additionals) {
                            uniqueAdditionalQuestions.addAll(additionals)
                        }
                    }

                    uniqueAdditionalQuestions?.each { Question additionalQuestion ->
                        if (!hideQuestions?.get(additionalQuestion.sectionId)?.contains(additionalQuestion.questionId)) {
                            additionalQuestions.add(additionalQuestion)
                        }
                    }
                }
                table = "${table}<tr>"

                if (showQuestion) {
                    table = "${table}<th>&nbsp;</th>"
                }
                Boolean headerAdded = Boolean.FALSE
                String previousQuestion
                Boolean hasResources = datasets.find({ it.resourceId }) ? Boolean.TRUE : Boolean.FALSE
                Boolean showUnit = reportItem.showUnit

                ResourceCache resourceCache = ResourceCache.init(datasets)
                for (Dataset dataset in datasets) {
                    Resource resource = resourceCache.getResource(dataset)

                    if (!headerAdded) {
                        table = "${table}<th>${message(code: 'answer')}</th>"

                        if (showQuantity && hasResources) {
                            table = "${table}<th>${message(code: 'query.quantity')}</th>"
                        }

                        if (showUnit) {
                            table = "${table}<th>${message(code: 'entity.show.unit')}</th>"
                        }

                        if (additionalQuestions) {
                            if (reportItem.additionalQuestions != null) {
                                reportItem.additionalQuestions.each { String questionId ->
                                    if (questionId.endsWith(".pointsFactor")) {
                                        table = "${table}<th>Points</th>"
                                    } else {
                                        table = "${table}<th>${additionalQuestions.find({ questionId.replaceAll(".pointsFactor", "").equals(it.questionId) })?.localizedQuestion}</th>"
                                    }
                                }
                            } else {
                                additionalQuestions.each { Question question ->
                                    table = "${table}<th>${question.localizedQuestion}</th>"
                                }
                            }
                        }
                        if (reportItem?.showEPDDownloadLink && epdDownloadLicensed) {
                            table = "${table}<th>${message(code: 'resource.pdfFile_download')}</th>"
                        }
                        table = "${table}</tr>"
                        headerAdded = Boolean.TRUE
                    }
                    def answers = dataset.answerIds

                    if (resource || (answers && !answers.isEmpty())) {
                        Map<String, Object> additionalQuestionAnswers = datasetService.getAdditionalQuestionAnswersForReport(entity, indicator, dataset)
                        def questionName
                        Question question = questions?.find({ it.questionId.equals(dataset.questionId) })

                        if (!hideQuestions?.get(question?.sectionId)?.contains(question?.questionId) && !"hidden".equals(question?.inputType)) {
                            table = "${table}<tr${reportItem?.collapsed ? ' style=\"display: none;\"' : ''}>"

                            if (showQuestion) {
                                questionName = question?.localizedQuestion

                                if (reportItem.dontRepeatQuestion && questionName && questionName.equals(previousQuestion)) {
                                    table = "${table}<td>&nbsp;</td>"
                                } else {
                                    table = "${table}<td>${questionName ? questionName : ''}</td>"
                                }
                                previousQuestion = questionName
                            }

                            if (resource) {
                                table = "${table}<td>${resource.isDummy ? dataset?.groupingDatasetName : optimiResourceService.getLocalizedName(resource)}</td>"

                                if (showQuantity) {
                                    def quantity = !resource.resourceId.equals(answers[0]) ? answers[0] : ''
                                    def original

                                    if (reportItem.reportDenominator) {
                                        original = quantity
                                        quantity = getValueByReportDenominator([quantity], reportItem.reportDenominator, [entity])
                                    }
                                    table = "${table}<td>${quantity}${original ? ' (' + quantity + ')' : ''}</td>"

                                    if (showUnit) {
                                        table = "${table}<td>${dataset.userGivenUnit ? dataset.userGivenUnit : resource.unitForData && quantity != null && !'null'.equals(resource.unitForData) ? resource.unitForData : ''}</td>"
                                    }
                                }
                            } else {
                                table = "${table}<td>"

                                if ("checkbox".equals(question?.inputType)) {
                                    table = "${table}<ul>"

                                    if (answers && !answers.isEmpty()) {
                                        answers.each { String answer ->
                                            table = "${table}<li>${question?.choices?.find({ it.answerId?.equals(answer) })?.localizedAnswer}</li>"
                                        }
                                        table = "${table}</ul>"
                                    } else {
                                        table = "${table}${indicatorService.getLocalizedText(reportItem)}"
                                    }
                                } else {
                                    if (answers && !answers.isEmpty()) {
                                        def originals

                                        if (reportItem.reportDenominator) {
                                            String denominatedAnswer = getValueByReportDenominator(answers, reportItem.reportDenominator, [entity])

                                            if (denominatedAnswer) {
                                                originals = answers
                                                answers = [denominatedAnswer]
                                            }
                                        }

                                        if (answers.size() > 1) {
                                            answers.each { String answer ->
                                                if ("select".equals(question?.inputType)) {
                                                    table = "${table}${question?.choices?.find({ it.answerId?.equals(answer) })?.localizedAnswer}<br />"
                                                } else {
                                                    table = "${table}${answer}<br />"
                                                }
                                            }
                                        } else {
                                            if (StringUtils.isNotBlank(answers[0])) {
                                                if ("select".equals(question?.inputType)) {
                                                    table = "${table}${question?.choices?.find({ it.answerId?.equals(answers[0]) })?.localizedAnswer}"
                                                } else {
                                                    table = "${table}${answers[0]}${originals ? ' (' + originals[0] + ')' : ''}"
                                                }
                                            } else {
                                                table = "${table}${indicatorService.getLocalizedText(reportItem)}"
                                            }
                                        }
                                    } else {
                                        table = "${table}${indicatorService.getLocalizedText(reportItem)}"
                                    }
                                }
                                table = "${table}</td>"

                                if (showUnit) {
                                    table = "<td>${question?.localizedUnit ? question.localizedUnit : ''}</td>"
                                }

                                if (showQuantity && hasResources) {
                                    table = "${table}<td>&nbsp;</td>"
                                }
                            }

                            if (additionalQuestions) {
                                boolean hasPointsFactor = reportItem.additionalQuestions?.find({
                                    it?.endsWith(".pointsFactor")
                                }) ? true : false

                                additionalQuestions.each { Question additionalQuestion ->
                                    String value = additionalQuestionAnswers?.get(additionalQuestion.questionId)?.toString()

                                    if (value && additionalQuestion?.privateClassificationQuestion) {
                                        value = additionalQuestion.choices?.find({value.equalsIgnoreCase(it.answerId)})?.localizedAnswer
                                    }
                                    if (value == null && "singlecheckbox".equals(additionalQuestion.inputType)) {
                                        value = "false"
                                    }
                                    //sw-1597
                                    if (nmdElementList && value && additionalQuestion.questionId == Constants.SFB_CODE_QUESTIONID) {
                                        NmdElement nmdElement = nmdElementList.find { it.elementId == Integer.parseInt(value) }
                                        if (nmdElement) {
                                            value = nmdElement.code + ' ' + nmdElement.name
                                        }
                                    }
                                    String unit = showUnit ? additionalQuestion?.localizedUnit : ''
                                    if (value?.trim()) {
                                        boolean useColor = false
                                        String colorCoding

                                        if (reportItem.rowColorCoding && additionalQuestionAnswers) {
                                            colorCoding = getRowColorCoding(reportItem.rowColorCoding, additionalQuestionAnswers)
                                        }

                                        if (colorCoding &&
                                                additionalQuestion.questionId.equals(reportItem.rowColorCoding.get("colorQuestion"))
                                                && (!hasPointsFactor || additionalQuestion.pointsFactorInUi)) {
                                            useColor = true
                                        }

                                        if ("text".equals(additionalQuestion.inputType)) {
                                            table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${value} ${unit ? unit : ''}</td>"
                                        } else if ("singlecheckbox".equals(additionalQuestion.inputType)) {
                                            String singleCheckboxChoice = additionalQuestion.choices?.find({
                                                value.equalsIgnoreCase(it.answerId)
                                            })?.localizedAnswer
                                            table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${singleCheckboxChoice ? singleCheckboxChoice : value}</td>"
                                        } else {
                                            List<Document> resources = additionalQuestion.getAdditionalQuestionResources(indicator, null)

                                            if ("profileId".equals(additionalQuestion.questionId)) {
                                                table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${(String) value} ${unit ? unit : ''}</td>"
                                            } else if (resources) {
                                                String resourceLocalizedName = ""
                                                if (value.contains(".")) {
                                                    resourceLocalizedName = resources.find({ Document r -> value.contains(r.resourceId.toString()) && value.contains(r.profileId) })?.localizedName
                                                } else {
                                                    resourceLocalizedName = resources.find({ Document r -> value.contains(r.resourceId.toString()) })?.localizedName
                                                }
                                                table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${resourceLocalizedName} ${unit ? unit : ''}</td>"
                                            } else if (additionalQuestion.pointsFactorInUi) {
                                                Double pointsFactor = additionalQuestion.choices?.find({
                                                    value.equals(it.answerId)
                                                })?.pointsFactor
                                                table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${pointsFactor != null ? pointsFactor : ''}</td>"
                                            } else {
                                                table = "${table}<td${useColor ? ' style="' + colorCoding + '"' : ''}>${value} ${unit ? unit : ''}</td>"
                                            }
                                        }
                                    } else {
                                        table = "${table}<td>&nbsp;</td>"
                                    }

                                }
                            }
                            if (reportItem?.showEPDDownloadLink) {
                                String content = ""
                                if (resource && resource.downloadLink && !"-".equals(resource.downloadLink.trim()) && epdDownloadLicensed) {
                                    String link
                                    String displayText

                                    if (resource.downloadLink.startsWith("http")) {
                                        displayText = message(code: 'resource.externalLink')
                                        content = content + "<a href=\"${resource?.downloadLink}\" target=\'_blank\' >${displayText}</a><br />"
                                    } else {
                                        link = createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: resource.resourceId, profileId: resource.profileId])
                                        displayText = message(code: 'resource.pdfFile_download')
                                        content = content + "<a href=\'${link}\' target=\'_blank\'>${displayText}</a><br />"

                                    }
                                }
                                table = "${table}<td>${content}</td>"
                            }
                            table = "${table}</tr>"
                        }
                    }
                }
                table = "${table}</table>"
            } else {
                table = "<div class=\"${reportItem.reportItemId ? reportItem.reportItemId : ""}\" style=\"padding-bottom: 18px;${collapseThis ? " display: none;" : ""}\">${indicatorService.getLocalizedText(reportItem)}</div>"
            }
        }
        return table
    }

    private String getValueByReportDenominator(List<String> values, IndicatorReportDenominator reportDenominator, List<Entity> entities) {
        String result

        if (values && reportDenominator && entities) {
            Double temp

            values.each { String value ->
                if (value && DomainObjectUtil.isNumericValue(value)) {
                    Double doubleValue = DomainObjectUtil.convertStringToDouble(value)
                    temp = temp ? temp + doubleValue : doubleValue
                }
            }

            Double denominator

            entities.each { Entity e ->
                Double denominPerEntity = valueReferenceService.getDoubleValueForEntity(reportDenominator.valueReference, e)

                if (denominPerEntity) {
                    denominator = denominator ? denominator + denominPerEntity : denominPerEntity
                }
            }

            if (temp) {
                if (denominator) {
                    temp = temp / denominator

                    if ("percentage".equalsIgnoreCase(reportDenominator.type)) {
                        temp = temp * 100
                    }
                    DecimalFormat numberFormat

                    if (reportDenominator.decimals) {
                        String decimals = "#."

                        for (int i = 0; i < reportDenominator.decimals; i++) {
                            decimals = decimals + "0"
                        }
                        numberFormat = new DecimalFormat(decimals)
                    } else {
                        numberFormat = new DecimalFormat("###,###.##")
                    }
                    result = numberFormat.format(temp)

                    if ("percentage".equalsIgnoreCase(reportDenominator.type)) {
                        result = result + " %"
                    }
                } else {
                    result = message(code: "portfolio.denominator_missing")
                }
            }
        }
        return result
    }

    private String getRowColorCoding(Map<String, String> rowColorCoding, Map<String, Object> additionalQuestionAnswers) {
        String backgroundColor = ""

        if (rowColorCoding && additionalQuestionAnswers) {

            String questionId = rowColorCoding.get("colorQuestion")
            String answer = (String) additionalQuestionAnswers.get(questionId)

            if (answer) {
                String color = rowColorCoding.get(answer)

                if (color) {
                    if (!color.trim().startsWith("#")) {
                        color = "#${color.trim()}"
                    }
                    backgroundColor = "background-color: ${color};"
                }
            }
        }
        return backgroundColor
    }

    def renderCategoryExpand = { attrs ->
        User user = userService.getCurrentUser()
        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        ResultCategory resultCategory = attrs.resultCategory

        if (indicator && entity && resultCategory) {
            Boolean calculationDetailsEnabled = entity.calculationDetailsEnabled
            List<Query> indicatorQueries = indicator.getQueries(entity)
            String entityClass
            Entity parentEntity = entity?.getParentById()
            entityClass = parentEntity ? parentEntity.entityClass : entity?.entityClass
            Boolean trialResults = parentEntity?.isFreeTrial

            if (indicatorQueries) {
                List<CalculationResult> resultsForIndicator = entity.getCalculationResultObjects(indicator.indicatorId, null, resultCategory.resultCategoryId)
                List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(parentEntity)
                String tableRows = ""

                if (calculationRules) {
                    Map<String, Double> totalByRule = [:]

                    calculationRules.each { CalculationRule calculationRule ->
                        Double total = entity.getResult(indicator.indicatorId, calculationRule.calculationRuleId,
                                resultCategory.resultCategoryId, resultsForIndicator)
                        totalByRule.put(calculationRule.calculationRuleId, total)
                    }

                    indicatorQueries.each { Query query ->
                        query.getSections()?.each { QuerySection querySection ->
                            querySectionService.getAllUnderlyingQuestions(entityClass, querySection)?.each { Question question ->
                                List<Dataset> foundDatasets = entity.
                                        getDatasetsForResultCategoryExpand(indicator.indicatorId,
                                                resultCategory.resultCategoryId, resultsForIndicator)?.
                                        findAll({
                                            query.queryId.equals(it.queryId) && querySection.sectionId.equals(it.sectionId) && question.questionId.equals(it.questionId)
                                        })

                                if (foundDatasets) {
                                    foundDatasets = foundDatasets?.sort({ x, y -> x.resourceId <=> y.resourceId ?: x.quantity <=> y.quantity })
                                    tableRows = "${tableRows}<thead><tr><td class=\"collapserParent\" colspan=\"${4 + calculationRules.size()}\"><button class=\"pull-left sectionexpander\"><i class=\"icon icon-chevron-down collapser\"></i><i class=\"icon icon-chevron-right expander\"></i></button><h2 class=\"expandAndCollapse\">${query?.localizedName}&nbsp;&nbsp;>&nbsp;&nbsp;${querySection?.localizedName}&nbsp;&nbsp;>&nbsp;&nbsp;${question?.localizedQuestion ? question.localizedQuestion : ''}</h2></td></tr></thead>"
                                    tableRows = "${tableRows}<tbody class=\"calculationTotalResults\">"
                                    Map<String, Double> totalPerSection = [:]
                                    Map<String, Double> totalBySection = [:]

                                    foundDatasets?.each { Dataset dataset ->
                                        calculationRules.each { CalculationRule calculationRule ->
                                            Double result = entity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                    resultCategory.resultCategoryId, calculationRule.calculationRuleId,
                                                    resultsForIndicator)
                                            Double total = totalByRule.get(calculationRule.calculationRuleId)

                                            if (result != null && total) {
                                                Double existingPerSection = totalBySection.get(calculationRule.calculationRuleId)

                                                if (existingPerSection) {
                                                    totalBySection.put(calculationRule.calculationRuleId, existingPerSection + result)
                                                } else {
                                                    totalBySection.put(calculationRule.calculationRuleId, result)
                                                }
                                            }
                                        }
                                    }

                                    ResourceCache resourceCache = ResourceCache.init(foundDatasets)
                                    for (Dataset dataset: foundDatasets) {
                                        String sourceListing
                                        Resource resource = resourceCache.getResource(dataset)

                                        if (!resource?.construction || (resource?.construction && "LCC".equalsIgnoreCase(indicator.assessmentType) && (dataset.userSetCost || dataset.userSetTotalCost))) {
                                            String userInputString = dataset.answerIds?.get(0)
                                            String userInput
                                            String partOfConstruction
                                            if (dataset.uniqueConstructionIdentifier) {
                                                def constructionDataset = entity?.datasets?.find({
                                                    dataset.uniqueConstructionIdentifier.equals(it.uniqueConstructionIdentifier) && !it.parentConstructionId
                                                })
                                                partOfConstruction = resourceCache.getResource(constructionDataset)?.isDummy ? constructionDataset.groupingDatasetName : optimiResourceService.getLocalizedName(resourceCache.getResource(constructionDataset))
                                            }

                                            if (userInputString && userInputString.replace(",", ".").isNumber()) {
                                                userInput = resultFormattingResolver.formatByTwoSignificantDigits(user, userInputString.replace(",", ".").toDouble())
                                            } else {
                                                userInput = userInputString
                                            }

                                            String random = UUID.randomUUID().toString()
                                            String link = resource ? opt.renderDataCardBtn(indicatorId: indicator?.indicatorId, resourceId: resource?.resourceId, profileId: resource?.profileId, childEntityId: entity?.id?.toString(), showGWP: true, infoId: random) : ""
                                            if (resultCategory.process) {
                                                Resource calculationResource = entity.getCalculationResourceForDataset(dataset.manualId,
                                                        indicator.indicatorId, resultCategory.resultCategoryId, resultsForIndicator)
                                                tableRows = "${tableRows}<tr style=\"display: none\" class=\"resultRow\">" +
                                                        "<td>${partOfConstruction ? partOfConstruction.encodeAsHTML() : ""}</td>" +
                                                        "<td>${resource ? optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60)?.encodeAsHTML() : optimiStringUtils.truncate(question.localizedQuestion, 60)?.encodeAsHTML()}${sourceListing ? ' ' + sourceListing.encodeAsHTML() : ''}${link}</td>" +
                                                        "<td class=\"text-right transformProcess hidden\">${calculationResource ? "${optimiResourceService.getLocalizedName(calculationResource)}${!calculationResource.active ? "(${message(code: "inactive")})" : ""}" : ''}</td>" +
                                                        "<td class=\"text-right\">${userInput} ${dataset.userGivenUnit ? dataset.userGivenUnit : ''}</td>"
                                            } else {
                                                tableRows = "${tableRows}<tr style=\"display: none\" class=\"resultRow\">" +
                                                        "<td>${partOfConstruction ? partOfConstruction.encodeAsHTML() : ""}</td>" +
                                                        "<td>${resource ? optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60)?.encodeAsHTML() : optimiStringUtils.truncate(question.localizedQuestion, 60)?.encodeAsHTML()}${sourceListing ? ' ' + sourceListing.encodeAsHTML() : ''}${link}</td>" +
                                                        "<td class=\"text-right\">${userInput} ${dataset.userGivenUnit ? dataset.userGivenUnit : ''}</td>"
                                            }

                                            calculationRules.each { CalculationRule calculationRule ->
                                                if (trialResults && (calculationRule.hideInTrial || resultCategory.hideInTrial)) {
                                                    tableRows = "${tableRows}<td class=\"text-right\">" +
                                                            "<span class=\"numerical\" style=\"display:none;\">TRIAL</span>" +
                                                            "<span class=\"percentages\">TRIAL</span></td>"
                                                } else {
                                                    Double result = entity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                            resultCategory.resultCategoryId, calculationRule.calculationRuleId,
                                                            resultsForIndicator)

                                                    Double percentage
                                                    Double totalOfSection = totalBySection.get(calculationRule.calculationRuleId)

                                                    if (result != null && totalOfSection) {
                                                        Double existingPerSection = totalPerSection.get(calculationRule.calculationRuleId)

                                                        if (existingPerSection) {
                                                            totalPerSection.put(calculationRule.calculationRuleId, existingPerSection + result)
                                                        } else {
                                                            totalPerSection.put(calculationRule.calculationRuleId, result)
                                                        }

                                                        if (totalOfSection) {
                                                            percentage = result / totalOfSection * 100
                                                        }
                                                    }
                                                    tableRows = "${tableRows}<td class=\"text-right\">" +
                                                            "<span class=\"numerical\" style=\"display:none;\">${resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, result, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}${calculationDetailsEnabled ? resultCategory.virtual ? "<br/><i data-unselectable=\"${message(code: "see_calculation_failover.virtual_category")}\" class=\"notCopyable\"></i>" : "<br/><a data-unselectable=\"${message(code: "see_calculation")}\" class=\"calculationFormula notCopyable\" onclick=\"showCalculationFormula(this, '${entity.id.toString()}', '${dataset.manualId}','${indicator.indicatorId}','${resultCategory.resultCategoryId}','${calculationRule.calculationRuleId}', 600);\"></a>" : ""}</span>" +
                                                            "<span class=\"percentages\"${percentage && percentage > 30 ? ' style=\"color: #FF2D41;\"' : ''}>${percentage && percentage > 10 ? '<strong>' : ''}${percentage ? resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, percentage, session, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "", null, null, null, Boolean.TRUE) + ' %' : ''}</span>${percentage && percentage > 10 ? '<strong>' : ''}</td>"
                                                }

                                            }
                                            tableRows = "${tableRows}<td style=\"text-align: left; white-space: normal;\">${dataset.getComment()?.encodeAsHTML()}</td></tr>"
                                        }
                                    }
                                    tableRows = "${tableRows}<tr id=\"totalSectionShare\" class=\"sectionTableRow\"><td>&nbsp;</td><td class='constructionTh'>&nbsp;</td><td class=\"text-right\"><strong><span class=\"percentages\">${message(code: 'results.expand.section_share')}</span><span class=\"numerical\" style=\"display:none;\">${message(code: 'results.expand.section_total')}</span></strong></td>"

                                    calculationRules.each { CalculationRule calculationRule ->
                                        if (trialResults && (calculationRule.hideInTrial || resultCategory.hideInTrial)) {
                                            tableRows = "${tableRows}<td class=\"text-right\">" +
                                                    "<span class=\"numerical\" style=\"display:none;\"><strong>TRIAL</strong></span>" +
                                                    "<span class=\"percentages\">TRIAL</span></td>"
                                        } else {
                                            Double totalPercentage
                                            Double total = totalByRule.get(calculationRule.calculationRuleId)
                                            Double perSection = totalPerSection.get(calculationRule.calculationRuleId)

                                            if (total && perSection != null) {
                                                totalPercentage = perSection / total * 100
                                            }
                                            tableRows = "${tableRows}<td class=\"text-right\">" +
                                                    "<span class=\"numerical\" style=\"display:none;\"><strong>${resultFormattingResolver.formatByResultFormatting(user, indicator, null, perSection, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}</strong></span>" +
                                                    "<span class=\"percentages\"${totalPercentage && totalPercentage > 30 ? ' style=\"color: #FF2D41;\"' : ''}>${totalPercentage && totalPercentage > 10 ? '<strong>' : ''}${totalPercentage ? resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, totalPercentage, session, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "", null, null, null, Boolean.TRUE) + ' %' : ''}</span>${totalPercentage && totalPercentage > 10 ? '<strong>' : ''}</td>"
                                        }

                                    }
                                    tableRows = "${tableRows}<td>&nbsp;</td></tr></tbody>"
                                }
                            }
                        }
                    }
                }
                out << tableRows
            }
        }
    }

    def renderCategoryTotalRow = { attrs ->
        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        ResultCategory resultCategory = attrs.resultCategory
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(entity?.parentEntityId ? entity.parentById : entity)

        if (indicator && entity && resultCategory) {
            String totalCategoryRow

            if (resultCategory.process) {
                totalCategoryRow = "<tr style=\"border-top: 2px solid black;\"><td class='hidden constructionTh'>&nbsp;</td><td colspan=\"2\"></td><td class=\"bold\">${message(code: 'totalScore')}</td>"

            } else {
                totalCategoryRow = "<tr style=\"border-top: 2px solid black;\"><td class='hidden constructionTh'>&nbsp;</td><td colspan=\"1\"></td><td class=\"bold\">${message(code: 'totalScore')}</td>"

            }

            calculationRules.each { CalculationRule rule ->
                def scoreByCategory

                if (rule) {
                    scoreByCategory = entity?.getResult(indicator?.indicatorId, rule?.calculationRuleId, resultCategory?.resultCategoryId)
                }
                totalCategoryRow = totalCategoryRow + "<td class=\"text-right\">${resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, scoreByCategory, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}</td>"
            }

            totalCategoryRow = "${totalCategoryRow}<td>&nbsp;</td></tr>"

            out << totalCategoryRow
        }
    }

    def averageScore = { attrs ->
        List<Entity> entities = attrs.entities
        Map<Entity, List<Entity>> entityMap = attrs.entityMap
        Indicator indicator = attrs.indicator
        def total = 0
        def entitiesWithScore = 0
        def average

        if ((entities || entityMap) && indicator) {
            if (!entities) {
                entities = []
                entityMap.each { Entity parent, List<Entity> children ->
                    if (children) {
                        entities.addAll(children)
                    }
                }
            }
            entities.each { Entity entity ->
                if (entity.parentById?.indicatorIds?.contains(indicator.indicatorId)) {
                    def score = entity?.getDisplayResult(indicator.indicatorId)

                    if (score) {
                        Double number = score.toDouble()
                        entitiesWithScore++
                        total = total + number
                    }
                }
            }
        }

        if (total > 0) {
            average = total / entitiesWithScore
            out << resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, average, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE)
        }
    }


    private String getAppendValueToDisplayResult(Indicator indicator, Entity entity) {
        String appendValue
        AppendQuestionIdToDisplayResult appendQuestionIdToDisplayResult = indicator?.appendQuestionIdToDisplayResult

        if (appendQuestionIdToDisplayResult && entity?.datasets) {
            Dataset dataset = entity.datasets.find({ Dataset d ->
                d.queryId == appendQuestionIdToDisplayResult.queryId &&
                        d.sectionId == appendQuestionIdToDisplayResult.sectionId &&
                        d.questionId == appendQuestionIdToDisplayResult.questionId
            })

            if (dataset) {
                if (dataset.resourceId) {
                    appendValue = optimiResourceService.getLocalizedName(datasetService.getResource(dataset))
                } else {
                    appendValue = dataset.answerIds[0]
                }
            }
        }
        return appendValue
    }

    def renderDisplayResult = { attrs ->
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        Boolean useAppendValue = attrs.useAppendValue?.toBoolean()
        Boolean noScientificNumbers = attrs.noScientificNumbers?.toBoolean()
        Boolean displayMainPage = attrs.displayMainPage?.toBoolean()
        List<String> queryIds = attrs.queryIds

        out << indicatorService.renderDisplayResult(entity, indicator, attrs.featuresAvailableForEntity, queryIds,
                useAppendValue, displayMainPage, noScientificNumbers, session)
    }

    def scoreByCalculationRuleAndResultCategory = { attrs ->
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        CalculationRule rule = attrs.calculationRule
        ResultCategory resultCategory = attrs.resultCategory
        def score

        if (indicator && resultCategory && rule && entity) {
            score = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, indicator?.displayDenominator, entity?.getResult(indicator?.indicatorId, rule?.calculationRuleId, resultCategory?.resultCategoryId), session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
        }


        out << score
    }

    private String getScorebyCalculationRuleAndResultCategory(Entity entity, Indicator indicator, CalculationRule rule, ResultCategory resultCategory) {
        def score

        if (indicator && resultCategory && rule && entity) {
            score = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, indicator?.displayDenominator, entity?.getResult(indicator?.indicatorId, rule?.calculationRuleId, resultCategory?.resultCategoryId), session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
        }
        return score
    }

    def totalScoreByCalculationRule = { attrs ->
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        CalculationRule rule = attrs.calculationRule
        def score

        if (indicator && rule && entity) {
            score = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, indicator?.displayDenominator, entity.getTotalResult(indicator.indicatorId, rule.calculationRuleId), session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
        }
        out << score
    }

    def portfolioTotalScoreByCalculationRule = { attrs ->
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        String calculationRuleId = attrs.calculationRuleId
        def score

        if (indicator && calculationRuleId && entity) {
            score = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, indicator?.displayDenominator, entity.getTotalResult(indicator.indicatorId, calculationRuleId), session, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "")
        }
        out << score
    }

    def renderCalculationRuleInfoById = { attrs ->
        String info = ""
        Indicator indicator = attrs.indicator
        String calculationRuleId = attrs.calculationRuleId

        if (indicator && calculationRuleId) {
            String applicationId = indicator.applicationRules?.find({
                it.value?.contains(calculationRuleId)
            })?.key?.toString()

            if (applicationId) {
                def rule = applicationService.getApplicationByApplicationId(applicationId)?.calculationRules?.find({
                    calculationRuleId.equals(it.calculationRuleId)
                })

                if (rule) {
                    info = "${rule.localizedName} / ${calculationRuleService.getLocalizedUnit(rule, indicator)}"
                }
            }

            if (!info && indicator.calculationRules) {
                def rule = indicator.calculationRules.find({ calculationRuleId.equals(it.calculationRuleId) })

                if (rule) {
                    info = "${rule.localizedName} / ${calculationRuleService.getLocalizedUnit(rule, indicator)}"
                }
            }
        }
        out << info
    }

    def totalScore = { attrs ->
        List<Entity> entities = attrs.entities
        Indicator indicator = attrs.indicator
        def isDisplay = attrs.isDisplay
        def calculationRuleId = attrs.calculationRuleId
        def total = 0
        def entitiesWithScore = 0

        if (entities && indicator) {
            entities.each { Entity entity ->
                if (entity.parentById?.indicatorIds?.contains(indicator.indicatorId)) {
                    def score

                    if (calculationRuleId) {
                        score = entity.getTotalResult(indicator.indicatorId, calculationRuleId)
                    } else {
                        score = entity.getDisplayResult(indicator?.indicatorId)
                    }

                    if (score) {
                        Double number = score.toDouble()

                        if (isDisplay) {
                            Denominator displayDenominator = indicator.getDisplayDenominator()

                            if (displayDenominator) {
                                Double valueForDisplayDenominator = denominatorUtil.getFinalValueForDenominator(entity, displayDenominator)

                                if (valueForDisplayDenominator) {
                                    number = number / valueForDisplayDenominator
                                }
                            }
                        }
                        entitiesWithScore++
                        total = total + number
                    }
                }
            }
        }

        if (total > 0) {
            if (indicator.showAverageInsteadOfTotal && entitiesWithScore > 0) {
                total = total / entitiesWithScore
            }
            out << resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, total, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
        }

    }

    def totalScoreForBenchmark = { attrs ->
        List<Entity> entities = attrs.entities
        Indicator indicator = attrs.indicator
        def isDisplay = attrs.isDisplay
        def showOnlySpecificallyAllowed = attrs.showOnlySpecificallyAllowed
        def showOnlyCarbonHeroes = attrs.showOnlyCarbonHeroes
        def showOnlyLatestStatus = attrs.showOnlyLatestStatus
        def showAverage = attrs.showAverage
        Double total = 0
        Integer validEntities = 0

        if (entities && indicator) {
            entities.each { Entity entity ->
                def score = entity.getDisplayResult(indicator?.indicatorId)

                if (score) {
                    Double number = score.toDouble()

                    if (isDisplay) {
                        Denominator displayDenominator = indicator.getDisplayDenominator()

                        if (displayDenominator) {
                            Double valueForDisplayDenominator = denominatorUtil.getFinalValueForDenominator(entity, displayDenominator)

                            if (valueForDisplayDenominator) {
                                number = number / valueForDisplayDenominator
                            }
                        }
                    }

                    if (showOnlyLatestStatus) {
                        if (entity.chosenDesign) {
                            if (showOnlyCarbonHeroes) {
                                if (entity.carbonHero) {
                                    if (showOnlySpecificallyAllowed) {
                                        if (entity.allowAsBenchmark) {
                                            total = total + number
                                            validEntities++
                                        }
                                    } else {
                                        total = total + number
                                        validEntities++
                                    }
                                }
                            } else {
                                if (showOnlySpecificallyAllowed) {
                                    if (entity.allowAsBenchmark) {
                                        total = total + number
                                        validEntities++
                                    }
                                } else {
                                    total = total + number
                                    validEntities++
                                }
                            }
                        }
                    } else {
                        if (showOnlyCarbonHeroes) {
                            if (entity.carbonHero) {
                                if (showOnlySpecificallyAllowed) {
                                    if (entity.allowAsBenchmark) {
                                        total = total + number
                                        validEntities++
                                    }
                                } else {
                                    total = total + number
                                    validEntities++
                                }
                            }
                        } else {
                            if (showOnlySpecificallyAllowed) {
                                if (entity.allowAsBenchmark) {
                                    total = total + number
                                    validEntities++
                                }
                            } else {
                                total = total + number
                                validEntities++
                            }
                        }
                    }
                }
            }
        }

        if (total > 0) {
            if (showAverage && validEntities > 0) {
                total = total / validEntities
            }
            out << resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, total, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
        }

    }

    def renderIndicatorExpandFeature = { attrs ->
        Indicator indicator = attrs.indicator
        Entity entity = attrs.entity
        IndicatorExpandFeature expandFeature = indicator?.resolveExpandFeature
        String content = ""
        StringBuilder contentBuilder = new StringBuilder()
        Boolean showDownloadExcel = Boolean.FALSE

        if ((expandFeature || attrs.downloadSummary) && entity ) {
            DetailReportTable reportTable
            if (attrs.downloadSummary) {
                reportTable = detailReportServiceProxy.createResultSummaryTable(entity, indicator)
            } else {
                reportTable = detailReportServiceProxy.createDetailReportTable(entity, indicator)
            }

            if (reportTable) {
                log.debug("Report table is ready.")

                showDownloadExcel = Boolean.TRUE

                if (reportTable.headings) {
                    contentBuilder.append("<thead><tr>")

                    reportTable.headings.each { String heading ->
                        contentBuilder.append("<th>${heading}</th>")
                    }
                    contentBuilder.append("</tr></thead>")
                }

                content = contentBuilder.toString()

                if (attrs.downloadSummary) {
                    content = getContentForResultSummary(reportTable, content)
                } else {
                    content = getContentForIndicatorExpandFeature(reportTable, content)
                }
            } else {
                content = "<tr><th>${message(code: 'results.expand.no_scores')}</th></tr>"
            }
        }
        pageScope.showDownloadExcel = showDownloadExcel
        out << content
    }

    String getContentForResultSummary(reportTable,content) {
        StringBuilder contentBuilder = new StringBuilder()
        if(content) {
            contentBuilder.append(content)
        }
        try {
            if (reportTable.reportRows) {

                reportTable.reportRows.each { String key, List<DetailReportRow> reportRows ->
                    reportRows.each { DetailReportRow reportRow ->
                        contentBuilder.append("<tr>")

                        reportRow.columnWithValue?.values()?.each { String value ->
                            contentBuilder.append("<td>${value}</td>")
                        }
                        DetailReportRow firstLevelTotalRow = reportTable.firstLevelTotals?.find({
                            key.equals(it.firstLevelTotalKey)
                        })
                        if (firstLevelTotalRow) {

                            firstLevelTotalRow.columnWithValue?.values()?.each { String value ->
                                contentBuilder.append("<td>${value}</td>")
                            }
                        }
                        contentBuilder.append("</tr>")
                    }

                }

            }
        } catch (e) {
            loggerUtil.error(log, 'Error in getContentForResultSummary()', e)
            flashService.setErrorAlert("Error in generating content for result summary report: ${e.getMessage()}", Boolean.TRUE)
        }
        return contentBuilder.toString()
    }

    String getContentForIndicatorExpandFeature(reportTable,content) {
        StringBuilder contentBuilder = new StringBuilder()
        if(content) {
            contentBuilder.append(content)
        }
        try{
            if (reportTable.reportRows) {
                DetailReportRow previousRow

                reportTable.reportRows.each { String key, List<DetailReportRow> reportRows ->
                    reportRows.each { DetailReportRow reportRow ->
                        if (previousRow?.secondLevelTotalKey && !previousRow.secondLevelTotalKey.equals(reportRow.secondLevelTotalKey)) {
                            DetailReportRow secondLevelTotalRow = reportTable.secondLevelTotals?.find({
                                previousRow.secondLevelTotalKey.equals(it.secondLevelTotalKey)
                            })

                            if (secondLevelTotalRow) {
                                contentBuilder.append("<tr>")

                                secondLevelTotalRow.columnWithValue?.values()?.each { String value ->
                                    contentBuilder.append("<th>${value}</th>")
                                }
                            }
                        }
                        contentBuilder.append("<tr>")

                        reportRow.columnWithValue?.values()?.each { String value ->
                            contentBuilder.append("<td>${value}</td>")
                        }
                        contentBuilder.append("</tr>")
                        previousRow = reportRow
                    }
                    DetailReportRow firstLevelTotalRow = reportTable.firstLevelTotals?.find({
                        key.equals(it.firstLevelTotalKey)
                    })

                    if (firstLevelTotalRow) {
                        contentBuilder.append("</tr>")

                        firstLevelTotalRow.columnWithValue?.values()?.each { String value ->
                            contentBuilder.append("<th>${value}</th>")
                        }
                        contentBuilder.append("</tr>")
                    }
                }

                if (previousRow?.secondLevelTotalKey) {
                    DetailReportRow secondLevelTotalRow = reportTable.secondLevelTotals?.find({
                        previousRow.secondLevelTotalKey.equals(it.secondLevelTotalKey)
                    })

                    if (secondLevelTotalRow) {
                        contentBuilder.append("</tr>")

                        secondLevelTotalRow.columnWithValue?.values()?.each { String value ->
                            contentBuilder.append("<th>${value}</th>")
                        }
                        contentBuilder.append("</tr>")
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, 'Error in getContentForIndicatorExpandFeature()', e)
            flashService.setErrorAlert("Error in generating content for Indicator Expand Feature report: ${e.getMessage()}", Boolean.TRUE)
        }
        return contentBuilder.toString()
    }

    def benchmarkValue = { attrs ->
        Indicator indicator = attrs.indicator
        BenchmarkValue benchmark = attrs.benchmark
        String portfolioId = attrs.portfolioId

        if (indicator && (benchmark || portfolioId)) {
            Portfolio portfolio

            if (portfolioId) {
                portfolio = portfolioService.getBenchmarkPortfolio(portfolioId)
            } else {
                portfolio = benchmark.portfolio
            }
            def portfolioIndicators = portfolio?.indicators

            if (portfolioIndicators?.contains(indicator)) {
                def entities = portfolio?.entities
                def entityTotal = 0
                def childEntities = []
                def foundEntities

                if (Constants.PortfolioType.DESIGN.toString() == portfolio.type) {
                    entities?.each { Entity entity ->
                        if (entity.indicatorIds?.contains(indicator.indicatorId)) {
                            foundEntities = entityService.getChildEntities(entity, Constants.EntityClass.DESIGN.getType())

                            if (foundEntities) {
                                childEntities.addAll(foundEntities)
                            }
                        }
                    }
                } else if (Constants.PortfolioType.OPERATING.toString() == portfolio.type) {
                    entities?.each { Entity entity ->
                        if (entity.indicatorIds?.contains(indicator.indicatorId)) {
                            foundEntities = entityService.getChildEntities(entity, Constants.EntityClass.OPERATING_PERIOD.getType())

                            if (portfolio.chosenPeriods) {
                                foundEntities = foundEntities.findAll({ Entity e -> portfolio.chosenPeriods.contains(e.operatingPeriod) })
                            }

                            if (foundEntities) {
                                childEntities.addAll(foundEntities)
                            }
                        }
                    }
                }

                if (childEntities && !childEntities.isEmpty()) {
                    entityTotal = totalScoreForBenchmark(entities: childEntities, indicator: indicator, isDisplay: true,
                            showOnlyCarbonHeroes: portfolio?.showOnlyCarbonHeroes, showOnlySpecificallyAllowed: portfolio?.showOnlySpecificallyAllowed,
                            showOnlyLatestStatus: portfolio?.showOnlyLatestStatus, showAverage: portfolio?.showAverageInsteadOfTotal)
                }

                out << entityTotal
            } else {
                out << message(code: "not_available")
            }
        }

    }

    def renderIndicatorUnit = { attrs ->
        Indicator indicator = attrs.indicator
        Entity parent = attrs.entity
        String unit

        if (indicator) {
            CalculationRule unitAsValueRefRule = indicator.getResolveCalculationRules(parent)?.find({ it.unitAsValueReference })

            if (parent && unitAsValueRefRule) {
                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule.unitAsValueReference, parent, Boolean.FALSE, Boolean.TRUE)
            }

            if (!unit) {
                unit = indicatorService.getDisplayUnit(indicator)
            }
        }
        out << unit
    }

    def graphMean = { attrs ->
        Map graphModel = attrs.graphModel
        String operatingPeriod = attrs.operatingPeriod
        Double answer = 0D

        if (graphModel && operatingPeriod) {
            List resultsByOperatingPeriod = graphModel.values()?.collect({ it.get(operatingPeriod) })

            if (resultsByOperatingPeriod) {
                List<Double> calculableResults = []
                resultsByOperatingPeriod.each {
                    if (it != null && it.isNumber()) {
                        calculableResults.add(it.toDouble())
                    }
                }

                if (calculableResults && !calculableResults.isEmpty()) {
                    answer = (calculableResults.sum() / calculableResults.size()).round(3)
                }
            }
        }

        if (answer && answer < 0) {
            answer = 0D
        }
        out << answer
    }

    def graphMax = { attrs ->
        Double answer = 0D
        try {
            Double maxValue = attrs.maxValue
            Double meanValue = attrs.meanValue?.toString()?.toDouble()

            if (maxValue && meanValue) {
                answer = (meanValue + ((maxValue - meanValue) * 0.9)).round(3)
            }

            if (answer && answer < 0) {
                answer = 0D
            }
        } catch (Exception e) {
            log.error("graphMax threshold calc: ${e}")
        }

        out << answer
    }

    def graphMin = { attrs ->
        Map graphModel = attrs.graphModel
        String operatingPeriod = attrs.operatingPeriod
        Double minScore = attrs.minScore
        Double answer = 0D

        if (graphModel && operatingPeriod && minScore) {
            List resultsByOperatingPeriod = graphModel.values()?.collect({ it.get(operatingPeriod) })

            if (resultsByOperatingPeriod) {
                List<Double> calculableResults = []
                resultsByOperatingPeriod.each {
                    if (it != null && it.isNumber()) {
                        calculableResults.add(it.toDouble())
                    }
                }

                if (calculableResults && !calculableResults.isEmpty()) {
                    answer = ((calculableResults.sum() / calculableResults.size() + minScore) * 0.5).round(3)
                }
            }
        }

        if (answer && answer < 0) {
            answer = 0D
        }
        out << answer
    }

    private List<Question> getAdditionalQuestionsForQueryContent(List<String> additionalQuestionIds) {
        List<Question> additionalQuestions = []
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)

        if (additionalQuestionsQuery?.sections && additionalQuestionIds) {
            QuerySection additionalQuestionsSection = additionalQuestionsQuery?.sections[0]

            additionalQuestionIds?.each { String questionId ->
                Question question = additionalQuestionsSection.questions?.find({
                    questionId?.replaceAll(".pointsFactor", "")?.equals(it.questionId)
                })

                if (question) {
                    Question copy = new Question()
                    copy.properties = question.properties

                    if (questionId.endsWith(".pointsFactor")) {
                        copy.pointsFactorInUi = true
                    }
                    additionalQuestions.add(copy)
                }

            }
        }
        return additionalQuestions
    }
}
