package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache

class PortFolioTagLib {
    def resultFormattingResolver
    def userService
    def optimiStringUtils
    def indicatorService
    def optimiResourceService
    def querySectionService
    def denominatorService
    def portfolioService
    def datasetService

    static namespace = "optimi"

    def renderPortFolioResults = { attrs ->
        Indicator indicator = attrs.indicator
        Portfolio portfolio = attrs.portfolio
        String operatingPeriod = attrs.operatingPeriod

        if (portfolio && indicator) {
            List<Entity> childEntities

            if ("design".equals(portfolio.type)) {
                childEntities = portfolioService.getAllChildEntities(portfolio)
            } else {
                if (operatingPeriod) {
                    childEntities = portfolioService.getAllChildEntities(portfolio)?.findAll({
                        operatingPeriod.equals(it.operatingPeriod) && !it.name
                    })
                } else {
                    childEntities = portfolioService.getAllChildEntities(portfolio).findAll({ !it.name })
                }
            }

            if (childEntities && !childEntities.isEmpty()) {
                List<CalculationRule> nonVirtualRules = indicator.getResolveCalculationRules(null)?.findAll({!it.virtual})
                List<ResultCategory> resultCategories = indicator.getResolveResultCategories(null)
                List<ResultCategory> filteredResultCategories = []

                if (indicator.portfolioResultCategories && resultCategories) {
                    indicator.portfolioResultCategories.each { String resultCategoryId ->
                        ResultCategory resultCategory = resultCategories.find({resultCategoryId.equals(it.resultCategoryId)})

                        if (resultCategory) {
                            filteredResultCategories.add(resultCategory)
                        }
                    }
                } else if (indicator.expandFeatures?.categories && resultCategories) {
                    indicator.expandFeatures.categories.each { String resultCategoryId ->
                        ResultCategory resultCategory = resultCategories.find({resultCategoryId.equals(it.resultCategoryId)})

                        if (resultCategory) {
                            filteredResultCategories.add(resultCategory)
                        }
                    }
                } else {
                    filteredResultCategories = resultCategories
                }

                if (portfolio.showOnlySpecificallyAllowed) {
                    childEntities = childEntities.findAll({it.allowAsBenchmark})
                }

                if (portfolio.showOnlyLatestStatus) {
                    childEntities = childEntities.findAll({it.chosenDesign})
                }

                if (portfolio.showOnlyCarbonHeroes) {
                    childEntities = childEntities.findAll({it.carbonHero})
                }

                if (childEntities) {
                    String rows = getResultRowsForPortfolioEntities(indicator, childEntities, nonVirtualRules, filteredResultCategories, portfolio)
                    String totalRow = getTotalResultRowForPortfolioEntities(indicator, childEntities, nonVirtualRules, filteredResultCategories)
                    out << rows + totalRow
                } else {
                    out << ""
                }
            } else {
                out << ""
            }
        }
    }

    private String getResultRowsForPortfolioEntities(Indicator indicator, List<Entity> entities, List<CalculationRule> calculationRules, List<ResultCategory> resultCategories, Portfolio portfolio) {
        String indicatorId = indicator?.indicatorId
        def hasResult = false
        String resultRows = ""

        if (!calculationRules) {
            calculationRules = indicator?.getResolveCalculationRules(null)
        }

        if (!calculationRules && indicator.applicationRules) {
            flash.errorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
        }

        if (!resultCategories) {
            resultCategories = indicator?.getResolveResultCategories(null)
        }

        String heading = calc.renderResultHeadingObject(indicator: indicator, calculationRules: calculationRules, entity: entities.get(0), doColSpan: true, isPortfolio: true)?.toString()

        if (heading) {
            resultRows = heading
        }

        Map<String, List<CalculationResult>> resultsPerEntity = [:]

        entities.each { Entity entity ->
            for (CalculationRule calcRule : calculationRules) {
                if (entity?.getDisplayResult(indicatorId)) {
                    hasResult = true
                    break
                }
            }

            if (entity.parentById?.indicatorIds?.contains(indicator.indicatorId)) {
                List<CalculationResult> resultsForIndicator = entity.getCalculationResultObjects(indicator.indicatorId, null, null)
                resultsPerEntity.put((entity.id.toString()), resultsForIndicator)
            }
        }
        String operatingPeriod = entities?.get(0)?.operatingPeriod

        if (indicator && entities && calculationRules && resultCategories && portfolio) {
            Boolean clarifiedText = Boolean.FALSE
            resultCategories.each { ResultCategory category ->
                boolean hideRow = false

                if (category.applyConditions && !hasResult) {
                    hideRow = true
                }

                if (!hideRow) {
                    resultRows = resultRows + "<tr${!hasResult ? ' class=\"noResult\"' : ''}\">"

                    if (!indicator.hideResultCategoryLabels) {
                        resultRows = resultRows + "<td>" + category.resultCategory + "</td>"
                    }
                    resultRows = resultRows + "<td>${category.localizedName}</td>"

                    if (category.clarificationText) {
                        resultRows = resultRows + "<td class=\"clarification\">${category.localizedClarificationText}&nbsp;</td>"
                        clarifiedText = Boolean.TRUE
                    } else if (clarifiedText && !category.clarificationText) {
                        resultRows = resultRows + "<td class=\"clarification\">&nbsp;</td>"
                    }
                    List<String> entityIdsWithScores = []

                    calculationRules.each { CalculationRule calcRule ->
                        Double number
                        Double result

                        entities.each { Entity e ->
                            number = resultsPerEntity.get(e.id.toString())?.findAll({category.resultCategoryId.equals(it.resultCategoryId) && calcRule.calculationRuleId.equals(it.calculationRuleId)})?.collect({
                                it.result
                            })?.sum()

                            if (number) {
                                result = result ? result + number : number

                                if (!entityIdsWithScores.contains(e.id.toString())) {
                                    entityIdsWithScores.add(e.id.toString())
                                }

                                if (indicator?.showAverageInsteadOfTotal) {
                                    result = (result ? result + number : number) / entities.size()
                                }
                            }
                        }

                        def score = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, result, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
                        resultRows = resultRows + "<td class=\"text-right to-bold-result\">${score}</td>"
                    }
                    if (hasResult) {
                        resultRows = resultRows + "<td class=\"text-right\"><a href=\"javascript:;\" style=\"color: #6b9f00;\" onclick=\"fullScreenPopup('${createLink(controller: 'portfolio', action: 'categoryDetails', params: [portfolioId: portfolio?.id, indicatorId: indicatorId, resultCategory: category.resultCategory, operatingPeriod: operatingPeriod, entityId: entityIdsWithScores])}');\">${message(code: 'results.expand')}</a></td>"
                    } else {

                        resultRows = resultRows + "<td class=\"text-right\">&nbsp;</td>"
                    }
                    resultRows = resultRows + "</tr>"

                }
            }
        }
        return resultRows
    }

    private String getTotalResultRowForPortfolioEntities(Indicator indicator, List<Entity> entities, List<CalculationRule> calculationRules, List<ResultCategory> resultCategories) {
        String totalResultRow
        Boolean showClarification = Boolean.FALSE

        if (!indicator?.hideResultCategoryLabels) {
            totalResultRow = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td>&nbsp;</td><td class=\"boldText\">${message(code: 'totalScore')}</td>"
        } else {
            totalResultRow = "<tr id=\"totalRowNew\" class=\"totalRowNew\"><td class=\"boldText\">${message(code: 'totalScore')}</td>"
        }

        if (indicator) {
            if (!calculationRules) {
                calculationRules = indicator?.getResolveCalculationRules(null)
            }
            if (!resultCategories) {
                resultCategories = indicator?.getResolveResultCategories(null)
            }

            resultCategories.each {ResultCategory category ->
                if(category.clarificationText) {
                    showClarification = Boolean.TRUE
                }
            }
            if(showClarification){
                totalResultRow = totalResultRow + "<td class=\"clarification\">&nbsp;</td>"
            }

            if (!calculationRules && indicator.applicationRules) {
                flash.errorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
            }

            calculationRules?.each { CalculationRule rule ->
                Double total

                entities.each { Entity entity ->
                    if (entity.parentById?.indicatorIds?.contains(indicator.indicatorId)) {
                        Double number = entity?.getTotalResult(indicator.indicatorId, rule.calculationRuleId)

                        if (number) {
                            total = total ? total + number : number
                        }
                    }
                }

                def formattedTotalScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, total, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
                totalResultRow = "${totalResultRow}<td class=\"text-right boldText\">${formattedTotalScore}</td>"


            }
        }
        totalResultRow = "${totalResultRow} <td colspan=\"4\"></td></tr>"
        return totalResultRow
    }

    def renderDenominators = { attrs ->
        Indicator indicator = attrs.indicator
        List<Entity> entities = attrs.entities
        Boolean showPortfolioSourceListings = attrs.showPortfolioSourceListings
        String resourceIdsAsOptions = "<option value=\"\" data-showPortfolioSourceListings=\"${showPortfolioSourceListings}\" selected=\"selected\" >${message(code: 'choose_a_denom')}</option>"
        List<Denominator> denominators = indicator?.resolveDenominators
        String localizedUnitIndicator = indicatorService.getDisplayUnit(indicator)
        if (entities && denominators) {
            denominators.each { Denominator denominator ->
                Map options = [:]

                if (denominator) {
                    String label

                    if ("dynamicDenominator".equals(denominator.denominatorType) || "monthlyValueReference".equals(denominator.denominatorType)) {
                        List<Dataset> datasets

                        for (Entity entity in entities) {
                            if ("dynamicDenominator".equals(denominator.denominatorType)) {
                                datasets = denominatorService.getDynamicDenominatorDatasets(entity, denominator)
                            } else {
                                datasets = denominatorService.getMonthlyValuereferenceDatasets(entity, denominator)
                            }

                            if (datasets) {
                                ResourceCache resourceCache = ResourceCache.init(datasets)
                                for (Dataset dataset: datasets) {
                                    def score

                                    try {
                                        score = DomainObjectUtil.convertStringToDouble(dataset.answerIds.first().toString())
                                    } catch (Exception e) {
                                        loggerUtil.error("En error occurred during rendering denominators", e)
                                    }

                                    if (score) {
                                        if (denominator.requireMonthly || (indicator.requireMonthly && !denominator.requireMonthly)) {
                                            label = "${indicatorService.getDisplayUnit(indicator)} / ${denominator?.localizedName}"
                                        } else {
                                            label = "${indicatorService.getDisplayUnit(indicator)} / ${optimiResourceService.getLocalizedName(resourceCache.getResource(dataset))} ${resourceCache.getResource(dataset).unitForData ? resourceCache.getResource(dataset).unitForData : ''}"
                                        }
                                        String datasetIds = options.get(label)

                                        if (datasetIds) {
                                            datasetIds = "${datasetIds},${dataset.manualId}"
                                        } else {
                                            datasetIds = dataset.manualId
                                        }
                                        options.put(label, datasetIds)
                                    }
                                }
                            }
                        }

                        if (options && !options.isEmpty()) {
                            options.each {
                                resourceIdsAsOptions = "${resourceIdsAsOptions}<option data-showPortfolioSourceListings=\"${showPortfolioSourceListings}\" data-denominator=\"dynamic\" data-denominatorId=\"${denominator.denominatorId}\" value=\"${it.value}\">${it.key}</option>"
                            }
                        }
                    } else {
                        String name = denominator?.localizedName
                        resourceIdsAsOptions = "${resourceIdsAsOptions}<option data-showPortfolioSourceListings=\"${showPortfolioSourceListings}\" data-denominator=\"nonDynamic\" data-denominatorId=\"${denominator.denominatorId}\" value=\"${denominator?.denominatorId}\">${name}</option>"
                    }

                }
            }
        } else if (localizedUnitIndicator) {
            resourceIdsAsOptions = "${resourceIdsAsOptions}<option data-showPortfolioSourceListings=\"${showPortfolioSourceListings}\" value=\"\">${localizedUnitIndicator}</option>"
        }
        out << resourceIdsAsOptions

    }

    def renderPortfolioCategoryExpand = { attrs ->
        Indicator indicator = attrs.indicator
        ResultCategory resultCategory = attrs.resultCategory
        List<Entity> entities = attrs.entities
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(null)
        calculationRules = calculationRules?.sort({ it.localizedName })

        entities.each { Entity entity ->
            if (indicator && entity && resultCategory) {
                List<CalculationResult> indicatorResults = entity.getCalculationResultObjects(indicator.indicatorId, null, resultCategory.resultCategoryId)
                List<Dataset> entityDatasets = entity.getDatasetsForResultCategoryExpand(indicator.indicatorId, resultCategory.resultCategoryId)

                List<Query> indicatorQueries = indicator.getQueries(entity)
                boolean showGWP = entity.showGWPLicensed

                String entityClass
                Entity parentEntity = entity?.getParentById()
                entityClass = parentEntity ? parentEntity.entityClass : entity?.entityClass

                if (indicatorQueries) {
                    String tableRows = ""

                    if (calculationRules) {
                        Map<String, Double> totalByRule = [:]

                        calculationRules.each { CalculationRule calculationRule ->
                            Double total
                            entities.each { Entity entitiesForTotal ->
                                if (!total) {
                                    total = indicatorResults?.findAll({calculationRule.calculationRuleId?.equals(it.calculationRuleId)})?.collect({
                                        it.result
                                    })?.sum()
                                } else {
                                    Double sum = indicatorResults?.findAll({calculationRule.calculationRuleId?.equals(it.calculationRuleId)})?.collect({
                                        it.result
                                    })?.sum()

                                    if (sum) {
                                        total = total + sum
                                    }
                                }
                            }
                            totalByRule.put(calculationRule.calculationRuleId, total)
                        }

                        indicatorQueries.each { Query query ->
                            query.getSections()?.each { QuerySection querySection ->
                                querySectionService.getAllUnderlyingQuestions(entityClass, querySection)?.each { Question question ->
                                    List<Dataset> foundDatasets = entityDatasets?.findAll({query.queryId.equals(it.queryId) && querySection.sectionId.equals(it.sectionId) && question.questionId.equals(it.questionId)})

                                    if (foundDatasets) {
                                        foundDatasets = foundDatasets?.sort({ x, y -> x.resourceId <=> y.resourceId ?: x.quantity <=> y.quantity })
                                        tableRows = "${tableRows}<thead><tr><td class=\"collapserParent\" colspan=\"${4 + calculationRules.size()}\"><button class=\"pull-left sectionexpander\"><i class=\"icon icon-chevron-down collapser\"></i><i class=\"icon icon-chevron-right expander\"></i></button><h2 class=\"expandAndCollapse\">${query?.localizedName}&nbsp;&nbsp;>&nbsp;&nbsp;${querySection?.localizedName}&nbsp;&nbsp;>&nbsp;&nbsp;${question?.localizedQuestion ? question.localizedQuestion : ''}</h2></td></tr></thead>"
                                        tableRows = "${tableRows}<tbody class=\"calculationTotalResults\">"
                                        Map<String, Double> totalPerSection = [:]

                                        ResourceCache resourceCache = ResourceCache.init(foundDatasets)
                                        for (Dataset dataset: foundDatasets) {
                                            String sourceListing
                                            Resource resource = resourceCache.getResource(dataset)
                                            String userInputString = dataset.answerIds?.get(0)
                                            def userInput

                                            if (!userInputString.isEmpty() && userInputString?.replace(",", ".")?.isNumber()) {
                                                userInput = resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(userService.getCurrentUser(), userInputString?.replace(",", ".")?.toDouble())
                                            } else {
                                                userInput = userInputString
                                            }

                                            if (resultCategory.process) {
                                                Resource calculationResource = entity.getCalculationResourceForDataset(dataset.manualId, indicator.indicatorId, resultCategory.resultCategoryId)
                                                tableRows = "${tableRows}<tr style=\"display: none\" class=\"resultRow\"><td>${resource ? optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60) : ''}${sourceListing ? ' ' + sourceListing : ''}</td><td class=\"text-right\">${calculationResource ? "${optimiResourceService.getLocalizedName(calculationResource)}${!calculationResource.active ? "(${message(code: "inactive")})" : ""}" : ''}</td><td class=\"text-right\">${userInput} ${dataset.userGivenUnit ? dataset.userGivenUnit : ''}</td>"

                                            } else {
                                                tableRows = "${tableRows}<tr style=\"display: none\" class=\"resultRow\"><td>${resource ? optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60) : ''}${sourceListing ? ' ' + sourceListing : ''}</td><td class=\"text-right\">${userInput} ${dataset.userGivenUnit ? dataset.userGivenUnit : ''}</td>"
                                            }

                                            calculationRules.each { CalculationRule calculationRule ->
                                                Double result = indicatorResults?.findAll({calculationRule.calculationRuleId?.equals(it.calculationRuleId)})?.
                                                        find({
                                                            it.calculationResultDatasets?.keySet()?.toList()?.contains(dataset.manualId)
                                                        })?.calculationResultDatasets?.get(dataset.manualId)?.result
                                                Double percentage
                                                Double total = totalByRule.get(calculationRule.calculationRuleId)

                                                if (result != null && total) {
                                                    Double existingPerSection = totalPerSection.get(calculationRule.calculationRuleId)

                                                    if (existingPerSection) {
                                                        totalPerSection.put(calculationRule.calculationRuleId, existingPerSection + result)
                                                    } else {
                                                        totalPerSection.put(calculationRule.calculationRuleId, result)
                                                    }

                                                    if (total) {
                                                        percentage = result / total * 100
                                                    }
                                                }

                                                tableRows = "${tableRows}<td class=\"text-right\">" +
                                                        "<span class=\"numerical\" style=\"display:none;\">${resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, result, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}</span>" +
                                                        "<span class=\"percentages\"${percentage && percentage > 30 ? ' style=\"color: #FF2D41;\"' : ''}>${percentage && percentage > 10 ? '<strong>' : ''}${percentage ? resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, percentage, session, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "") + ' %' : ''}</span>${percentage && percentage > 10 ? '<strong>' : ''}</td>"
                                            }
                                            tableRows = "${tableRows}<td style=\"text-align: left; white-space: normal;\">${dataset.getComment()}</td>"
                                            tableRows = "${tableRows}<td style=\"text-align: left; white-space: normal;\">${entity.getParentName()}</td></tr>"
                                        }
                                        tableRows = "${tableRows}<tr id=\"totalSectionShare\" class=\"sectionTableRow\"><td>&nbsp;</td><td class=\"text-right\"><strong><span class=\"percentages\">${message(code: 'results.expand.section_share')}</span><span class=\"numerical\" style=\"display:none;\">${message(code: 'results.expand.section_total')}</span></strong></td>"

                                        calculationRules.each { CalculationRule calculationRule ->
                                            Double totalPercentage
                                            Double total = totalByRule.get(calculationRule.calculationRuleId)
                                            Double perSection = totalPerSection.get(calculationRule.calculationRuleId)
                                            Double totalForNumeric = perSection

                                            if (total && perSection != null) {
                                                totalPercentage = perSection / total * 100
                                            }
                                            tableRows = "${tableRows}<td class=\"text-right\">" +
                                                    "<span class=\"numerical\" style=\"display:none;\"><strong>${resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, totalForNumeric, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}</strong></span>" +
                                                    "<span class=\"percentages\"${totalPercentage && totalPercentage > 30 ? ' style=\"color: #FF2D41;\"' : ''}>${totalPercentage && totalPercentage > 10 ? '<strong>' : ''}${totalPercentage ? resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, totalPercentage, session, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, "") + ' %' : ''}</span>${totalPercentage && totalPercentage > 10 ? '<strong>' : ''}</td>"
                                        }
                                        tableRows = "${tableRows}<td>&nbsp;</td><td>&nbsp;</td></tr></tbody>"
                                    }
                                }
                            }
                        }
                    }
                    out << tableRows
                }
            }
        }
    }

    def renderPortfolioCategoryTotalRow = { attrs ->
        Indicator indicator = attrs.indicator
        ResultCategory resultCategory = attrs.resultCategory
        List<CalculationRule> calculationRules = attrs.calculationRules ? attrs.calculationRules : indicator?.getResolveCalculationRules(null)
        List<Entity> entities = attrs.entities

        if (indicator && entities && resultCategory) {
            String totalCategoryRow

            if (resultCategory.process) {
                totalCategoryRow = "<tr style=\"border-top: 2px solid black;\"><td colspan=\"2\"></td><td class=\"bold\">${message(code: 'totalScore')}</td>"

            } else {
                totalCategoryRow = "<tr style=\"border-top: 2px solid black;\"><td colspan=\"1\"></td><td class=\"bold\">${message(code: 'totalScore')}</td>"

            }

            calculationRules.each { CalculationRule rule ->
                def scoreByCategory

                entities.each { Entity entity ->
                    if (rule && !scoreByCategory) {
                        scoreByCategory = entity.getCalculationResultObjects(indicator.indicatorId, rule.calculationRuleId, resultCategory.resultCategoryId)?.collect({
                            it.result
                        })?.sum()
                    } else if (rule && scoreByCategory) {
                        Double sum = entity.getCalculationResultObjects(indicator.indicatorId, rule.calculationRuleId, resultCategory.resultCategoryId)?.collect({
                            it.result
                        })?.sum()

                        if (sum) {
                            scoreByCategory = scoreByCategory + sum
                        }
                    }
                }
                totalCategoryRow = totalCategoryRow + "<td class=\"text-right\">${resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, scoreByCategory, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")}</td>"
            }
            totalCategoryRow = "${totalCategoryRow}<td>&nbsp;</td><td>&nbsp;</td></tr>"

            out << totalCategoryRow
        }

    }

}
