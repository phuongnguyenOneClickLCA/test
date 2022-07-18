package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.taglib.IndicatorTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LocaleResolverUtil
import com.bionova.optimi.grails.OptimiMessageSource
import grails.core.GrailsApplication
import org.apache.commons.lang.StringUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * @author Pasi-Markus Mäkelä
 */
class PortfolioUtil {

    OptimiMessageSource messageSource
    LocaleResolverUtil localeResolverUtil
    GrailsApplication grailsApplication
    DenominatorUtil denominatorUtil
    def userService
    def loggerUtil
    private static final List alphabets = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                                           'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', 'Ö']

    private static Log log = LogFactory.getLog(PortfolioUtil.class)

    public Integer getMaxScoreFromDesignGraphModel(Map graphModel) {
        Integer maxScore

        if (graphModel && graphModel.size() > 1) {
            graphModel.values().each { String score ->
                if (score && score.isNumber() && (!maxScore || score.toDouble() > maxScore)) {
                    maxScore = (int) score.toDouble()
                }
            }
        }

        if (maxScore) {
            int i = 1

            while (maxScore / i > 10) {
                i = i * 10
            }
            maxScore = i * 10 / 2 >= maxScore ? i * 10 / 2 : i * 10
        }
        return maxScore
    }

    public def getLargestScoreFromOperatingGraphModel(Map graphModel, List<String> chosenPeriods) {
        Map<String, Double> largestScoreByPeriod = [:]

        if (chosenPeriods && graphModel && graphModel.size() > 1) {
            chosenPeriods?.each { String period ->
                graphModel.values().each {
                    String score = it.get(period)

                    if (score && (!largestScoreByPeriod.get(period) || score.toDouble() > largestScoreByPeriod.get(period))) {
                        largestScoreByPeriod.put(period, score.toDouble())
                    }
                }
            }
        }
        return largestScoreByPeriod
    }

    public def getSmallestScoreFromOperatingGraphModel(Map graphModel, List<String> chosenPeriods) {
        Map<String, Double> minScoreByPeriod = [:]

        if (chosenPeriods && graphModel && graphModel.size() > 1) {
            chosenPeriods?.each { String period ->
                graphModel.values().each {
                    String score = it.get(period)

                    if (score && (!minScoreByPeriod.get(period) || score.toDouble() < minScoreByPeriod.get(period))) {
                        minScoreByPeriod.put(period, score.toDouble())
                    }
                }
            }
        }
        return minScoreByPeriod
    }

    public Integer getMaxScoreFromOperatingGraphModel(Map graphModel) {
        Integer maxScore

        if (graphModel && graphModel.size() > 1) {
            graphModel.values().each {
                it.values()?.each { String score ->
                    if (score && (!maxScore || score.toDouble() > maxScore)) {
                        maxScore = (int) score.toDouble()
                    }
                }
            }
        }

        if (maxScore) {
            int i = 1

            while (maxScore / i > 10) {
                i = i * 10
            }
            maxScore = i * 10 / 2 >= maxScore ? i * 10 / 2 : i * 10
        }
        return maxScore
    }

    public Map getGraphModelForDesignDenominator(Portfolio portfolio, Map dynamicDenominatorScores, Map denominatorScores, Map<Entity, List<Entity>> entityDesigns,
                                                 Boolean mainPage = Boolean.FALSE) {
        Map model = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int scoresFound = 0
        Double average

        int entityIndex = 1
        int mainPageIndex = 0
        entityDesigns?.each { Entity parent, List<Entity> designs ->
            int designIndex = 1
            boolean hide = portfolio?.anonymous && !parent.nameVisibleInPortfolio

            designs?.each { Entity design ->
                String score

                if (dynamicDenominatorScores) {
                    score = dynamicDenominatorScores?.get(design.id?.toString())
                } else {
                    score = denominatorScores?.get(design.id?.toString())
                }

                if (score != null) {
                    average = average ? average + score.toDouble() : score.toDouble()
                    String label

                    if (mainPage) {
                        if (parent.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                            label = "${abbreviateString(parent.designPFName ? parent.designPFName : parent.shortName, 20)}, ${abbreviateString(design.name, 15)}"
                        } else {
                            label = "${alphabets[mainPageIndex]}"
                            mainPageIndex++
                        }
                    } else {
                        if (hide) {
                            label = "${abbreviateString("${messageSource.getMessage("portfolio.design", [entityIndex, designIndex].toArray(), getLocale())} ${parent.designPFAnon ? parent.designPFAnon : ""}", 15)}"
                        } else {
                            label = "${abbreviateString(parent.designPFName ? parent.designPFName : parent.shortName, 20)}, ${abbreviateString(design.name, 15)}"
                        }
                    }
                    model.put(label, score)
                    scoresFound++
                }
                designIndex++
            }
            entityIndex++
        }


        if (scoresFound < 2) {
            model = new TreeMap()
        }

        if (mainPage && model) {
            model = model.sort({ it.value.toDouble() })
            model.put(messageSource.getMessage("mean", null, getLocale()), df.format(average / scoresFound))
        }
        return model
    }

    private Map getGraphModelForDesigns(Portfolio portfolio, Map entityDesigns, Indicator indicator, Boolean isDisplay = Boolean.FALSE, Boolean mainPage = Boolean.FALSE) {
        Map model = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int entityIndex = 1
        int scoresFound = 0
        int mainPageIndex = 0
        Double average

        if (!indicator?.nonNumericResult) {
            entityDesigns?.each { Entity entity, List<Entity> designs ->
                int designIndex = 1
                boolean hide = portfolio?.anonymous && !entity?.nameVisibleInPortfolio

                designs?.each { Entity design ->
                    if (design) {
                        LcaCheckerResult lcaCheckerResult = design.lcaCheckerResult
                        def score = design?.getDisplayResult(indicator?.indicatorId)

                        if (score && score.toDouble()) {
                            String label
                            average = average ? average + score.toDouble() : score.toDouble()

                            if (mainPage) {
                                if (entity.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                                    label = "${abbreviateString(entity.designPFName ? entity.designPFName : entity.shortName, 20)}, ${abbreviateString(design.anonymousDescription ? design.anonymousDescription : design.name, 15)}"
                                } else {
                                    label = "${alphabets[mainPageIndex]}"
                                    mainPageIndex++
                                }
                            } else {
                                if (hide) {
                                    if (design.anonymousDescription) {
                                        label = "${messageSource.getMessage("portfolio.design_with_anonymousDescription", [entityIndex].toArray(), getLocale())}, ${design.anonymousDescription}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                                    } else {
                                        label = "${abbreviateString("${messageSource.getMessage("portfolio.design", [entityIndex, designIndex].toArray(), getLocale())}, ${entity.designPFAnon ? entity.designPFAnon : ""}",15)}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                                    }
                                } else {
                                    label = "${abbreviateString(entity.designPFName ? entity.designPFName : entity.shortName, 20)} ${abbreviateString(design.anonymousDescription ? design.anonymousDescription : design.name, 15)}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                                }
                            }
                            model.put(label, df.format(score.toDouble()))
                            scoresFound++
                        }
                    }
                    designIndex++
                }
                entityIndex++
            }

            if (scoresFound < 2) {
                model = null
            }

            if (mainPage && model) {
                model = model.sort({ it.value.toDouble() })

                if (average && scoresFound) {
                    model.put(messageSource.getMessage("mean", null, getLocale()), df.format(average / scoresFound))
                }
            }
            return model
        } else {
            return ["nonNumeric": true]
        }
    }

    public Map getGraphModelForOperating(Portfolio portfolio, Map entityPeriods, List<String> operatingPeriods,
                                         Indicator indicator, Boolean isDisplay = Boolean.FALSE, Boolean mainPage = Boolean.FALSE) {
        Map model = new LinkedHashMap()
        Map modelForSum = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int entityIndex = 1
        int mainPageIndex = 0
        Map average = [:]
        Map scoreAmountByPeriod = [:]

        if (!indicator?.nonNumericResult) {

            entityPeriods?.each { Entity entity, Map<String, Entity> operatingPeriodByPeriod ->
                Double sum = 0
                Map scoreByPeriod = new TreeMap()
                int scoresFound = 0

                operatingPeriods?.each { String operatingPeriod ->
                    Entity child = operatingPeriodByPeriod?.get(operatingPeriod)

                    if (child) {
                        def score = child?.getDisplayResult(indicator?.indicatorId)
                        if (score && score.toDouble()) {
                            sum = sum + score.toDouble()
                            scoresFound++
                            Double existingAverage = average.get(operatingPeriod)

                            if (existingAverage) {
                                average.put(operatingPeriod, (existingAverage + score.toDouble()))
                            } else {
                                average.put(operatingPeriod, score.toDouble())
                            }
                            scoreByPeriod.put(operatingPeriod, df.format(score.toDouble()))
                        } else {
                            scoreByPeriod.put(operatingPeriod, "0")
                        }
                    } else {
                        scoreByPeriod.put(operatingPeriod, "0")
                    }
                }

                if (scoresFound) {
                    operatingPeriods?.each { String operatingPeriod ->
                        def scoreAmount = scoreAmountByPeriod.get(operatingPeriod)
                        scoreAmountByPeriod.put(operatingPeriod, (scoreAmount ? scoreAmount + 1 : 1))
                    }
                    String label

                    if (mainPage) {
                        if (entity.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                            label = "${abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)}"
                        } else {
                            label = "${alphabets[mainPageIndex]}"
                            mainPageIndex++
                        }
                    } else {
                        if (portfolio?.anonymous && !entity.nameVisibleInPortfolio) {
                            label = abbreviateString("${messageSource.getMessage("portfolio.target", [entityIndex].toArray(), getLocale())} ${entity.operatingPFAnon ? entity.operatingPFAnon : ""}", 20)
                        } else {
                            label = abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)
                        }
                    }
                    modelForSum.put(label, sum)
                    model.put(label, scoreByPeriod)
                }
                entityIndex++
            }

            if (model && model.keySet().toList().size() < 2) {
                model = null
            }

            if (mainPage && modelForSum && model) {
                Map sortedModel = new LinkedHashMap()
                modelForSum = modelForSum.sort({ it.value })

                modelForSum.each { String key, Double value ->
                    sortedModel.put(key, model.get(key))
                }
                Map resolvedAverage = new LinkedHashMap()

                average?.each { String operatingPeriod, Double value ->
                    resolvedAverage.put(operatingPeriod, df.format(value / scoreAmountByPeriod.get(operatingPeriod)))
                }
                sortedModel.put(messageSource.getMessage("mean", null, getLocale()), resolvedAverage)
                return sortedModel
            } else {
                if (portfolio?.sortByDesc && model) {
                    Map sortedModel = new LinkedHashMap()
                    modelForSum = modelForSum.sort({ -it.value })

                    modelForSum.each { String key, Double value ->
                        sortedModel.put(key, model.get(key))
                    }

                    return sortedModel

                } else {
                    return model
                }
            }
        } else {
            return ["nonNumeric": true]
        }
    }

    public Map getGraphModelForOperatingDenominator(Portfolio portfolio,
                                                    Map<String, Double> dynamicDenominatorScores, Map<String, Double> denominatorScores,
                                                    List<String> operatingPeriods, Map<Entity, Map<String, Entity>> entityPeriods, Boolean mainPage = Boolean.FALSE) {
        Map <String, Map> model = new LinkedHashMap()
        Map <String, Double> modelForSum = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int entityIndex = 1
        int mainPageIndex = 0
        Map scoreAmountByPeriod = [:]
        Map <String, Double> average = [:]

        entityPeriods?.each { Entity entity, Map<String, Entity> operatingPeriodByPeriod ->
            int scoresFound = 0
            Double sum = 0
            Map scoreByPeriod = new TreeMap()

            operatingPeriods?.each { String operatingPeriod ->
                Entity child = operatingPeriodByPeriod?.get(operatingPeriod)

                if (child) {
                    def score

                    if (dynamicDenominatorScores) {
                        score = dynamicDenominatorScores?.get(child.id?.toString())
                    } else {
                        score = denominatorScores?.get(child.id?.toString())
                    }

                    if (score) {
                        sum = sum + score
                        scoreByPeriod.put(operatingPeriod, df.format(score.toDouble()))
                        scoresFound++
                        def existingAverage = average.get(operatingPeriod)

                        if (existingAverage) {
                            average.put(operatingPeriod, (existingAverage + score.toDouble()))
                        } else {
                            average.put(operatingPeriod, score.toDouble())
                        }
                    } else {
                        scoreByPeriod.put(operatingPeriod, "0")
                    }
                } else {
                    scoreByPeriod.put(operatingPeriod, "0")
                }
            }

            if (scoresFound) {
                operatingPeriods?.each { String operatingPeriod ->
                    def scoreAmount = scoreAmountByPeriod.get(operatingPeriod)
                    scoreAmountByPeriod.put(operatingPeriod, (scoreAmount ? scoreAmount + 1 : 1))
                }
                String label

                if (mainPage) {
                    if (entity.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                        label = "${abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)}"
                    } else {
                        label = "${alphabets[mainPageIndex]}"
                        mainPageIndex++
                    }
                } else {
                    if (portfolio?.anonymous && !entity.nameVisibleInPortfolio) {
                        label = abbreviateString("${messageSource.getMessage("portfolio.target", [entityIndex].toArray(), getLocale())}${entity.operatingPFAnon ? ", " + entity.operatingPFAnon : ""}", 20)
                    } else {
                        label = abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)
                    }
                }
                model.put(label, scoreByPeriod)
                modelForSum.put(label, sum)
            }
            entityIndex++
        }

        if (model && model.keySet().toList().size() < 2) {
            model = null
        }

        if (mainPage && modelForSum && model) {
            Map <String, Map> sortedModel = new LinkedHashMap()
            modelForSum = modelForSum.sort({ it.value })

            modelForSum.each { String key, Double value ->
                sortedModel.put(key, model.get(key))
            }
            Map resolvedAverage = new LinkedHashMap()

            average?.each { String operatingPeriod, Double value ->
                resolvedAverage.put(operatingPeriod, df.format(value / (Double) scoreAmountByPeriod.get(operatingPeriod)))
            }
            sortedModel.put(messageSource.getMessage("mean", null, getLocale()), resolvedAverage)
            return sortedModel
        } else {
            return model
        }
    }

    public Map<String, Double> scoreByDenominator(List<String> resourceOrQuestionIds, String resourceId, Entity entity, Indicator indicator, Denominator denominator = null) {
        Double score

        if (resourceOrQuestionIds && entity && indicator && denominator) {
            def divider
            Map datasets = denominatorUtil.getDatasetsForDenominator(denominator, entity)

            if (datasets) {
                def indicatorScore
                def datasetsAsList = []

                datasets.each { String queryId, List<Dataset> dsets ->
                    if (dsets) {
                        datasetsAsList.addAll(dsets)
                    }
                }

                if (datasetsAsList) {
                    if (indicatorScore?.isNumber()) {
                        indicatorScore = indicatorScore.toDouble()

                        for (resourceOrQuestionId in resourceOrQuestionIds) {
                            def denominDatasets

                            if (resourceId) {
                                denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.questionId == resourceOrQuestionId && resourceId.equals(d.resourceId) })
                            } else {
                                denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.questionId == resourceOrQuestionId })
                            }

                            if (!denominDatasets) {
                                denominDatasets = datasetsAsList?.findAll({ Dataset d -> d.resourceId == resourceOrQuestionId })
                            }

                            if (denominDatasets) {
                                for (Dataset denominDataset : denominDatasets) {
                                    divider = divider ? divider * denominDataset.quantity : denominDataset.quantity
                                }
                            }
                        }

                        if (divider) {
                            score = indicatorScore / divider
                        }
                    }
                }
            }
        }

        if (score) {
            String formattedNumber = denominatorUtil.formatByDenominator(indicator, denominator, score, entity)

            if (formattedNumber && DomainObjectUtil.isNumericValue(formattedNumber)) {
                score = DomainObjectUtil.convertStringToDouble(formattedNumber)
            }
            return [(formattedNumber): score]
        } else {
            return null
        }
    }

    private String abbreviateString(String value, Integer maxLength) {
        String abbreviatedString

        if (value && maxLength) {
            if (value.length() > maxLength) {
                abbreviatedString = StringUtils.substring(value,
                        0, maxLength) + "..."
            } else {
                abbreviatedString = value
            }
        } else {
            abbreviatedString = ""
        }
        return abbreviatedString
    }

    private IndicatorTagLib getIndicatorTagLib() {
        IndicatorTagLib indicatorTagLib = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.IndicatorTagLib')
        return indicatorTagLib
    }

    private Locale getLocale() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        HttpServletRequest request = webRequest.getRequest()
        HttpServletResponse response = webRequest.getResponse()
        HttpSession session = webRequest.getSession()
        return localeResolverUtil.resolveLocale(session, request, response)
    }

}
