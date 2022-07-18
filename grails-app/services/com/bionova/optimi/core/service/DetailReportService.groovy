package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExpandFeature
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ResultFormatting
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.taglib.UiTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LocaleResolverUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.grails.OptimiMessageSource
import com.bionova.optimi.ui.DetailReportRow
import com.bionova.optimi.ui.DetailReportTable
import com.bionova.optimi.util.DenominatorUtil
import com.bionova.optimi.util.OptimiStringUtils
import com.bionova.optimi.util.ResultFormattingResolver
import grails.core.GrailsApplication
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
class DetailReportService {

    DenominatorUtil denominatorUtil
    OptimiMessageSource messageSource
    LocaleResolverUtil localeResolverUtil
    ResultFormattingResolver resultFormattingResolver
    def queryService
    def userService
    def optimiResourceService
    OptimiStringUtils optimiStringUtils
    def datasetService
    EolProcessService eolProcessService
    def privateClassificationListService
    GrailsApplication grailsApplication
    FlashService flashService
    LoggerUtil loggerUtil
    def calculationRuleService
    def calculationResultService
    ResourceTypeService resourceTypeService
    IndicatorReportService indicatorReportService

    static scope = "session"

    public synchronized DetailReportTable createDetailReportTable(Entity entity, Indicator indicator, Boolean disableThousandSeparator = null,
                                                     Boolean html = Boolean.TRUE, Boolean excelDump = Boolean.FALSE, Boolean englishOnly = null) {
        DetailReportTable reportTable
        IndicatorExpandFeature expandFeature = indicator?.resolveExpandFeature
        UiTagLib opt = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.UiTagLib')
        
        if (expandFeature && entity) {
            User user = userService.getCurrentUser()
            Locale locale = getLocale(englishOnly)
            Entity parentEntity = entity.parentById
            Resource projectCountry = parentEntity?.countryResource
            String lcaModel = parentEntity?.lcaModel
            List<CalculationResult> resultsForIndicator = entity.getCalculationResultObjects(indicator.indicatorId, null, null)

            if (resultsForIndicator) {

                List<String> additionalQuestions = expandFeature.additionalQuestions
                List<String> additionalFields = expandFeature.additionalFields
                List<CalculationRule> calculationRules = getAllCalculationRules(parentEntity, indicator, expandFeature)

                if (calculationRules && !calculationRules.isEmpty()) {
                    List<Dataset> allValidDatasets = getAllValidDatasets(entity, indicator, expandFeature, resultsForIndicator)
                    EolProcessCache eolProcessCache = EolProcessCache.init()

                    if (!allValidDatasets.isEmpty()) {

                        reportTable = new DetailReportTable()
                        List<Question> additionalQuestionObjects = getAdditionalQuestionObjects(indicator, additionalQuestions)

                        List<Question> sortBy = []
                        Map<String, List<Dataset>> groupedDatasets = new TreeMap<String, List<Dataset>>()

                        if (expandFeature.sortByAdditionalQuestions) {
                            expandFeature.sortByAdditionalQuestions.each { String questionId ->
                                Question sortByQuestion = additionalQuestionObjects?.find({
                                    questionId.equals(it.questionId)
                                })

                                if (sortByQuestion) {
                                    sortBy.add(sortByQuestion)
                                }
                            }

                            if (!sortBy.isEmpty()) {
                                Dataset previousDataset

                                allValidDatasets.sort({ a, b -> datasetService.getAdditionalQuestionAnswersAsString(sortBy, a.additionalQuestionAnswers) <=> datasetService.getAdditionalQuestionAnswersAsString(sortBy, b.additionalQuestionAnswers) ?: a.resultCategory <=> b.resultCategory ?: a.quantity <=> b.quantity }).each { Dataset d ->
                                    String answersAsString = datasetService.getAdditionalQuestionAnswersAsString(sortBy, d.additionalQuestionAnswers) ?: "undefined"
                                    String resultCategory = d.resultCategory

                                    if (resultCategory) {
                                        String answerAndResultCategory = "${answersAsString}${resultCategory}"
                                        if (!previousDataset) {
                                            groupedDatasets.put(answerAndResultCategory, [d])
                                        } else {
                                            String previousDatasetAnswer = datasetService.getAdditionalQuestionAnswersAsString(sortBy, previousDataset.additionalQuestionAnswers) ?: "undefined"

                                            if (answersAsString.equals(previousDatasetAnswer) && resultCategory.equals(previousDataset.resultCategory)) {
                                                List<Dataset> existing = groupedDatasets.get("${answersAsString}${resultCategory}")
                                                existing.add(d)
                                                groupedDatasets.put(answerAndResultCategory, existing)
                                            } else {
                                                groupedDatasets.put(answerAndResultCategory, [d])
                                            }
                                        }
                                    }
                                    previousDataset = d
                                }
                            }
                        } else if (expandFeature.sortByQuestionId) {
                            Dataset previousDataset

                            allValidDatasets.sort({ a, b ->
                                datasetService.getQuestion(a)?.questionId?.toLowerCase() <=> datasetService.getQuestion(b)?.questionId?.toLowerCase() ?:
                                        a.resultCategory <=> b.resultCategory ?: a.quantity <=> b.quantity
                            }).each { Dataset d ->
                                String question = datasetService.getQuestion(d)?.questionId?.toLowerCase()

                                if (question) {
                                    if (!previousDataset) {
                                        groupedDatasets.put(question, [d])
                                    } else if (question.equals(datasetService.getQuestion(previousDataset)?.questionId?.toLowerCase())) {
                                        List<Dataset> existing = groupedDatasets.get(question)
                                        existing.add(d)
                                        groupedDatasets.put(question, existing)
                                    } else {
                                        groupedDatasets.put(question, [d])
                                    }
                                }
                                previousDataset = d
                            }
                        } else if (expandFeature.sortByCategoryId) {
                            Dataset previousDataset

                            allValidDatasets.sort({ a, b -> a.resultCategoryId <=> b.resultCategoryId ?: a.quantity <=> b.quantity }).each { Dataset d ->
                                String resultCategoryId = d.resultCategoryId?.toLowerCase()

                                if (resultCategoryId) {
                                    if (!previousDataset) {
                                        groupedDatasets.put(resultCategoryId, [d])
                                    } else if (resultCategoryId.equals(previousDataset.resultCategoryId?.toLowerCase())) {
                                        List<Dataset> existing = groupedDatasets.get(resultCategoryId)
                                        existing.add(d)
                                        groupedDatasets.put(resultCategoryId, existing)
                                    } else {
                                        groupedDatasets.put(resultCategoryId, [d])
                                    }
                                }
                                previousDataset = d
                            }
                        }

                        if (!groupedDatasets.isEmpty()) {
                            ResourceCache resourceCache = initResourceCache(groupedDatasets.values().flatten(), additionalQuestionObjects)
                            ResourceTypeCache resourceTypeCache = resourceCache.getResourceTypeCache()

                            List<DatasetImportFields> allDatasetImportFields
                            if (additionalFields.any { it.equalsIgnoreCase("ifcMaterial") }) {
                                List<String> datasetImportFieldsIds = groupedDatasets.values().flatten().findResults { it.datasetImportFieldsId }?.unique()
                                allDatasetImportFields = datasetImportFieldsIds ? datasetService.getDatasetImportFieldsByIdList(datasetImportFieldsIds) : []
                            }

                            ResultFormatting resultFormatting = resultFormattingResolver.resolveResultFormatting(indicator, null, getSession(), false, false)

                            reportTable.headings = new ArrayList<String>()

                            if (expandFeature.showResultCategory) {
                                reportTable.headings.add(getLocaleMessage("results.expand.section", null, locale))
                            }
                            reportTable.headings.add(getLocaleMessage("results.expand.resource", null, locale))
                            reportTable.headings.add(getLocaleMessage("results.expand.input", null, locale))
                            reportTable.headings.add(getLocaleMessage("entity.show.unit", null, locale))

                            calculationRules.each { CalculationRule calculationRule ->
                                if (englishOnly) {
                                    reportTable.headings.add("${calculationRule.name?.get("EN")} ${calculationRule.unit?.get("EN")}")
                                } else {
                                    reportTable.headings.add("${calculationRule.localizedName} ${calculationRuleService.getLocalizedUnit(calculationRule, indicator)}")
                                }
                            }

                            reportTable.headings.add(getLocaleMessage("results.expand.question", null, locale))

                            if (additionalQuestions) {
                                additionalQuestions.each { String questionId ->
                                    Question question = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })

                                    if (question) {
                                        if (englishOnly) {
                                            reportTable.headings.add("${question.question?.get("EN")}")
                                        } else {
                                            reportTable.headings.add("${question.localizedQuestion}")
                                        }
                                    }
                                }
                            }

                            if (additionalFields) {
                                additionalFields.each { String field ->
                                    if ("resourceType".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("resource.type", null, locale))
                                    } else if ("environmentDataSource".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("resource.environmentDataSource", null, locale))
                                    } else if ("localizedName".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("entity.name", null, locale))
                                    } else if ("construction".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("construction.construction", null, locale))
                                    } else if ("process".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("result.expand.transform_process", null, locale))
                                    } else if ("occurrencePeriods".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("result_per_occurrence", null, locale))
                                    } else if ("ifcMaterial".equals(field)) {
                                        reportTable.headings.add(getLocaleMessage("detailedReport.ifcMaterial", null, locale))
                                    } else {
                                        reportTable.headings.add(field)
                                    }
                                }
                            }


                            if (expandFeature.sortByAdditionalQuestions) {
                                List<Question> totalsAdditionalQuestions = []

                                expandFeature.totalsByAdditionalQuestions?.each { String questionId ->
                                    Question question = additionalQuestionObjects.find({
                                        questionId.equals(it.questionId)
                                    })

                                    if (question) {
                                        totalsAdditionalQuestions.add(question)
                                    }
                                }

                                Map<String, TreeMap<String, Double>> totalsByAdditionalQuestionAndRule = new LinkedHashMap<String, TreeMap<String, Double>>()
                                String totalsString
                                String previousTotalString
                                Dataset lastDatasetInList
                                int index = 0

                                Map resultsForIndicatorPerCalculationRule = resultsForIndicator.groupBy { it.calculationRuleId }
                                groupedDatasets.each { String key, List<Dataset> datasets ->
                                    int datasetAmount = datasets.size()
                                    Map totalByRule = [:]

                                    for (Dataset d: datasets) {
                                        String userInput = excelDump ? d.answerIds?.get(0) : resultFormattingResolver.formatByTwoSignificantDigits(user, d.answerIds?.get(0) as Double, disableThousandSeparator)
                                        Resource resource = resourceCache.getResource(d)

                                        if (!resource) {
                                            // hacky fallback :c
                                            List<Map<String, Object>> calculationResults = resultsForIndicator.findAll({ it.resultCategoryId.equals(d?.resultCategoryId) && it.calculationResultDatasets?.keySet()?.toList()?.contains(d?.manualId) })?.collect({it.calculationResultDatasets?.get(d?.manualId)})

                                            if (calculationResults) {
                                                List<String> resourceIds = calculationResults.collect({it.get("resourceId")})
                                                List<String> profileIds = calculationResults.collect({it.get("profileId")})

                                                if (resourceIds && resourceIds.unique().size() == 1 && profileIds && profileIds.unique().size() == 1) {
                                                    resource = resourceCache.getResource(resourceIds[0], profileIds[0])
                                                }
                                            }
                                        }

                                        if (!resource?.construction || (resource?.construction && "LCC".equalsIgnoreCase(indicator.assessmentType) && (d.userSetCost || d.userSetTotalCost))) {
                                            ResourceType subType = resourceTypeCache.getResourceSubType(resource)
                                            DetailReportRow reportRow = new DetailReportRow()

                                            if (totalsAdditionalQuestions) {
                                                totalsString = datasetService.getAdditionalQuestionAnswersAsString(totalsAdditionalQuestions, d.additionalQuestionAnswers) ?: "undefined"
                                                reportRow.secondLevelTotalKey = totalsString

                                                if (previousTotalString && !previousTotalString.equals(totalsString) && lastDatasetInList) {
                                                    Map<String, Double> theTotal = totalsByAdditionalQuestionAndRule.get(previousTotalString)

                                                    if (theTotal) {
                                                        DetailReportRow secondLevelTotal = new DetailReportRow()
                                                        secondLevelTotal.secondLevelTotalKey = previousTotalString

                                                        if (expandFeature.showResultCategory) {
                                                            secondLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), "")
                                                        }
                                                        secondLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "")
                                                        secondLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "")
                                                        secondLevelTotal.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")

                                                        calculationRules.each { CalculationRule calculationRule ->
                                                            secondLevelTotal.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null,"EN") : calculationRule.getLocalizedUnit()}",
                                                                    resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, theTotal.get(calculationRule.calculationRule),
                                                                            Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))
                                                        }

                                                        secondLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), "")

                                                        if (additionalQuestions) {
                                                            Resource datasetResource = resourceCache.getResource(d)
                                                            additionalQuestions.each { String questionId ->
                                                                String answer = ""
                                                                Question question = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })

                                                                if (totalsAdditionalQuestions.collect({
                                                                    it.questionId
                                                                }).contains(questionId)) {
                                                                    String userAnswer = lastDatasetInList?.additionalQuestionAnswers?.get(questionId)

                                                                    if (questionId.equals("serviceLife")) {
                                                                        ResourceType resourceSubType = resourceTypeCache.getResourceSubType(datasetResource)
                                                                        String serviceLife = "${getServiceLife(d, datasetResource, resourceSubType, entity, indicator)?.toInteger() ?: ""}"

                                                                        if ("-1" == serviceLife) {
                                                                            answer = getLocaleMessage("resource.serviceLife.as_building", null, locale)
                                                                        } else {
                                                                            answer = serviceLife
                                                                        }
                                                                    } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(questionId)) {
                                                                        if ("default".equals(userAnswer)) {
                                                                            answer = entity?.getDefaultTransportFromSubType(datasetResource)
                                                                        } else if (userAnswer) {
                                                                            answer = userAnswer
                                                                        }
                                                                    } else if (com.bionova.optimi.core.Constants.EOL_QUESTIONID.equals(questionId)) {
                                                                        EolProcess eolProcess = eolProcessService.getEolProcessForDataset(d, resource, subType, projectCountry, null, lcaModel, eolProcessCache)

                                                                        if (eolProcess) {
                                                                            answer = eolProcessService.getLocalizedName(eolProcess)
                                                                        }
                                                                    } else {
                                                                        if (userAnswer) {
                                                                            if ("select".equals(question.inputType) || "checkbox".equals(question.inputType) || "radio".equals(question.inputType)) {
                                                                                if (question.isResourceQuestion) {
                                                                                    answer = optimiStringUtils.truncate(optimiResourceService.getUiLabel(resourceCache.getResource(userAnswer, null)), 60)
                                                                                } else {
                                                                                    answer = question.choices?.find({
                                                                                        userAnswer.equals(it.answerId)
                                                                                    })?.localizedAnswer
                                                                                }
                                                                            } else {
                                                                                answer = userAnswer
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                secondLevelTotal.columnWithValue.put(question?.questionId, answer)
                                                            }
                                                        }

                                                        if (additionalFields) {
                                                            additionalFields.each { String field ->
                                                                if ("resourceType".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                                } else if ("environmentDataSource".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                                } else if ("localizedName".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                                } else if ("construction".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                                } else if ("process".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                                } else if ("occurrencePeriods".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), "")
                                                                } else if ("ifcMaterial".equals(field)) {
                                                                    secondLevelTotal.columnWithValue.put(getLocaleMessage("detailedReport.ifcMaterial", null, locale), "")
                                                                } else {
                                                                    secondLevelTotal.columnWithValue.put(field, "")
                                                                }
                                                            }
                                                        }
                                                        reportTable.secondLevelTotals.add(secondLevelTotal)
                                                    }
                                                }
                                            }
                                            if (expandFeature.showResultCategory) {
                                                reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), d.resultCategory)
                                            }

                                            String random = UUID.randomUUID().toString()
                                            String datasetStatusIcon = datasetService.generateVerifiedIconFromDataset(d)
                                            String link = resource ? opt.renderDataCardBtn(indicatorId: indicator?.indicatorId, resourceId: resource?.resourceId, profileId: resource?.profileId, childEntityId: entity?.id?.toString(), showGWP: true, infoId: random) : ""
                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "${html ? "${optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60)}${link}${datasetStatusIcon}" : "${optimiResourceService.getUiLabel(resource)}"}")
                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "${userInput}")

                                            if (d.userGivenUnit || resource?.unitForData) {
                                                reportRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "${d.userGivenUnit ? d.userGivenUnit : resource.unitForData}")
                                            } else {
                                                reportRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")
                                            }

                                            calculationRules.each { CalculationRule calculationRule ->
                                                Double totalResult

                                                List<CalculationResult> resultsForCalculationRule = resultsForIndicatorPerCalculationRule?.getAt(calculationRule.calculationRuleId)
                                                // Pass null as calculationRuleId because resultsForCalculationRule are already filtered by calculationRuleId
                                                Double result = entity.getResultForDataset(d.manualId, indicator.indicatorId,
                                                        d.resultCategoryId, null, resultsForCalculationRule, false)

                                                if (!d.resultCategoryObject?.ignoreFromTotals?.contains(calculationRule.calculationRuleId) &&
                                                        !d.resultCategoryObject?.ignoreFromTotals?.isEmpty()) {
                                                    totalResult = totalResult ? totalResult + result : result
                                                }

                                                reportRow.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                                                        resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, result,
                                                                Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))


                                                if (totalResult) {
                                                    Double total = totalByRule.get(calculationRule.calculationRule)
                                                    total = total ? total + totalResult : totalResult

                                                    if (datasetAmount > 1) {
                                                        totalByRule.put(calculationRule.calculationRule, total)
                                                    }

                                                    if (totalsString) {
                                                        Map<String, Double> existingTheTotal = totalsByAdditionalQuestionAndRule.get(totalsString)
                                                        Double theTotal = existingTheTotal?.get(calculationRule.calculationRule)
                                                        theTotal = theTotal ? theTotal + totalResult : totalResult

                                                        if (!existingTheTotal) {
                                                            existingTheTotal = new TreeMap<String, Double>()
                                                        }
                                                        existingTheTotal.put(calculationRule.calculationRule, theTotal)
                                                        totalsByAdditionalQuestionAndRule.put(totalsString, existingTheTotal)
                                                    }
                                                }
                                            }

                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), datasetService.getQuestion(d)?.localizedQuestion)

                                            if (additionalQuestions) {
                                                Resource datasetResource = resourceCache.getResource(d)
                                                additionalQuestions.each { String questionId ->
                                                    String userAnswer = d.additionalQuestionAnswers?.get(questionId)
                                                    String answer = ""
                                                    Question question = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })

                                                    if (questionId.equals("serviceLife")) {
                                                        ResourceType resourceSubType = resourceTypeCache.getResourceSubType(datasetResource)
                                                        String serviceLife = "${getServiceLife(d, datasetResource, resourceSubType, entity, indicator)?.toInteger() ?: ""}"

                                                        if ("-1" == serviceLife) {
                                                            answer = getLocaleMessage("resource.serviceLife.as_building", null, locale)
                                                        } else {
                                                            answer = serviceLife
                                                        }
                                                    } else if (Constants.TransportDistanceQuestionId.list().contains(questionId)) {
                                                        if ("default".equals(userAnswer)) {
                                                            answer = entity?.getDefaultTransportFromSubType(datasetResource)
                                                        } else if (userAnswer) {
                                                            answer = userAnswer
                                                        }
                                                    } else if (Constants.EOL_QUESTIONID.equals(questionId)) {
                                                        EolProcess eolProcess = eolProcessService.getEolProcessForDataset(d, resource, subType, projectCountry, null, lcaModel, eolProcessCache)

                                                        if (eolProcess) {
                                                            answer = eolProcessService.getLocalizedName(eolProcess)
                                                        }
                                                    } else {
                                                        if (userAnswer) {
                                                            if (question &&
                                                                    ("select".equals(question.inputType) ||
                                                                            "checkbox".equals(question.inputType) ||
                                                                            "radio".equals(question.inputType))) {
                                                                if (question.isResourceQuestion) {
                                                                    answer = optimiStringUtils.truncate(optimiResourceService.getUiLabel(resourceCache.getResource(userAnswer, null)), 60)
                                                                } else {
                                                                    answer = question.choices?.find({
                                                                        userAnswer.equals(it.answerId)
                                                                    })?.localizedAnswer
                                                                }
                                                            } else {
                                                                answer = userAnswer
                                                            }
                                                        }
                                                    }
                                                    if (question) {
                                                        reportRow.columnWithValue.put(question.questionId, answer)
                                                    } else {
                                                        log.debug("Question ${questionId} was not found by id.")
                                                    }
                                                }
                                            }

                                            if (additionalFields) {
                                                additionalFields.each { String field ->
                                                    if ("resourceType".equals(field)) {
                                                        if (subType) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), resourceTypeService.getLocalizedName(subType))
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                        }
                                                    } else if ("environmentDataSource".equals(field)) {
                                                        if (resource?.environmentDataSource) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), resource.environmentDataSource)
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                        }
                                                    } else if ("localizedName".equals(field)) {

                                                        if (optimiResourceService.getLocalizedName(resource)) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), optimiResourceService.getLocalizedName(resource))
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                        }
                                                    } else if ("construction".equals(field)) {
                                                        if (d.uniqueConstructionIdentifier && d.uniqueConstructionIdentifier != null && d.parentConstructionId) {
                                                            Dataset constrctionDataset = allValidDatasets.find({d.uniqueConstructionIdentifier.equals(it.uniqueConstructionIdentifier) && !it.parentConstructionId })
                                                            Resource constructionResource = resourceCache.getResource(constrctionDataset)
                                                            String partOfConstruction = constructionResource?.isDummy ? constrctionDataset?.groupingDatasetName :
                                                                    optimiResourceService.getLocalizedName(constructionResource)
                                                            reportRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), partOfConstruction ? partOfConstruction : "")
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                        }
                                                    } else if ("process".equals(field)) {
                                                        if (d.resultCategoryObject?.process) {
                                                            Resource calculationResource = entity.getCalculationResourceForDataset(d.manualId,
                                                                    indicator.indicatorId, d.resultCategoryId, resultsForIndicator)
                                                            reportRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), calculationResource ? "${optimiResourceService.getLocalizedName(calculationResource)}${!calculationResource.active ? "(${getLocaleMessage("inactive", null, locale)})" : ""}" : "")
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                        }
                                                    } else if ("occurrencePeriods".equals(field)) {
                                                        // TODO: Trang: comment out to deprecate the function, will delete after testing done
                                                        //Double assestmentPeriod = (Double) indicator?.assessmentPeriodFixed ? indicator.assessmentPeriodFixed : indicator?.assessmentPeriodValueReference?.getDoubleValueForEntity(entity) ? indicator?.assessmentPeriodValueReference?.getDoubleValueForEntity(entity) : 0
                                                        //Integer serviceLife = getServiceLife(d, resource, entity, indicator)?.toInteger()
                                                        //List<Integer> resultsForDatasetOccurrency = calculateOccurrencePeriods(serviceLife, assestmentPeriod, d.resultCategoryObject)
                                                        List<Integer> resultsForDatasetOccurrency = d.occurrencePeriods
                                                        reportRow.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), resultsForDatasetOccurrency ? resultsForDatasetOccurrency?.join(",")?.toString() : "")
                                                    } else if ("class".equalsIgnoreCase(field)) {
                                                        reportRow.columnWithValue.put(field, d.persistedOriginalClass ?: "")
                                                    } else if ("ifcMaterial".equalsIgnoreCase(field)) {
                                                        DatasetImportFields datasetImportFields = allDatasetImportFields.find { it.id.toString() == d.datasetImportFieldsId }
                                                        String ifcMaterial = ""

                                                        if (datasetImportFields && datasetImportFields.importFields) {
                                                            ifcMaterial = datasetImportFields.importFields.first().get("IFCMATERIAL")

                                                            if (!ifcMaterial) {
                                                                ifcMaterial = datasetImportFields.importFields.first().get("MATERIAL")
                                                            }
                                                        }
                                                        reportRow.columnWithValue.put(field, ifcMaterial ?: "")
                                                    } else {
                                                        if (resource) {
                                                            String value = DomainObjectUtil.callGetterByAttributeName(field, resource)

                                                            if (!value && subType) {
                                                                value = DomainObjectUtil.callGetterByAttributeName(field, subType)
                                                            }
                                                            reportRow.columnWithValue.put(field, value ?: "")
                                                        } else {
                                                            reportRow.columnWithValue.put(field, "")
                                                        }
                                                    }
                                                }
                                            }
                                            previousTotalString = totalsString

                                            List<DetailReportRow> reportRows = reportTable.reportRows.get(key)
                                            reportRow.firstLevelTotalKey = key

                                            if (reportRows) {
                                                reportRows.add(reportRow)
                                            } else {
                                                reportRows = [reportRow]
                                            }
                                            reportTable.reportRows.put(key, reportRows)
                                        }
                                    }
                                     lastDatasetInList = datasets.last()

                                    if (datasetAmount > 1 && totalsAdditionalQuestions) {
                                        DetailReportRow firstLevelTotal = new DetailReportRow()
                                        firstLevelTotal.firstLevelTotalKey = key

                                        if (expandFeature.showResultCategory) {
                                            firstLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), lastDatasetInList?.resultCategory)
                                        }
                                        firstLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "")
                                        firstLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "")
                                        firstLevelTotal.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")

                                        calculationRules.each { CalculationRule calculationRule ->
                                            firstLevelTotal.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                                                    resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, totalByRule.get(calculationRule.calculationRule),
                                                            Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))
                                        }

                                        firstLevelTotal.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), "")

                                        if (additionalQuestions) {
                                            additionalQuestions.each { String questionId ->
                                                String answer = ""
                                                Question question = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })

                                                if (!sortBy || sortBy.collect({ it.questionId }).contains(questionId)) {
                                                    String userAnswer = lastDatasetInList?.additionalQuestionAnswers?.get(questionId)
                                                    Resource lastDatasetResource = resourceCache.getResource(lastDatasetInList)

                                                    if (questionId.equals("serviceLife")) {
                                                        ResourceType lastDatasetResourceSubType = resourceTypeCache.getResourceSubType(lastDatasetResource)
                                                        String serviceLife = "${getServiceLife(lastDatasetInList, lastDatasetResource, lastDatasetResourceSubType, entity, indicator)?.toInteger() ?: ""}"

                                                        if ("-1" == serviceLife) {
                                                            answer = getLocaleMessage("resource.serviceLife.as_building", null, locale)
                                                        } else {
                                                            answer = serviceLife
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(questionId)) {
                                                        if ("default".equals(userAnswer)) {
                                                            answer = entity?.getDefaultTransportFromSubType(lastDatasetResource)
                                                        } else if (userAnswer) {
                                                            answer = userAnswer
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.EOL_QUESTIONID.equals(questionId)) {
                                                        ResourceType lastDatasetResourceSubType = resourceTypeCache.getResourceSubType(lastDatasetResource)
                                                        EolProcess eolProcess = eolProcessService.getEolProcessForDataset(lastDatasetInList, lastDatasetResource, lastDatasetResourceSubType, projectCountry, null, lcaModel, eolProcessCache)

                                                        if (eolProcess) {
                                                            answer = eolProcessService.getLocalizedName(eolProcess)
                                                        }
                                                    } else {
                                                        if (userAnswer) {
                                                            if ("select".equals(question.inputType) || "checkbox".equals(question.inputType) || "radio".equals(question.inputType)) {
                                                                if (question.isResourceQuestion) {
                                                                    answer = optimiStringUtils.truncate(optimiResourceService.getUiLabel(resourceCache.getResource(userAnswer, null)), 60)
                                                                } else {
                                                                    answer = question.choices?.find({
                                                                        userAnswer.equals(it.answerId)
                                                                    })?.localizedAnswer
                                                                }
                                                            } else {
                                                                answer = userAnswer
                                                            }
                                                        }
                                                    }
                                                }
                                                firstLevelTotal.columnWithValue.put(question?.questionId, answer)
                                            }
                                        }

                                        if (additionalFields) {
                                            additionalFields.each { String field ->
                                                if ("resourceType".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                } else if ("environmentDataSource".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                } else if ("localizedName".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                } else if ("construction".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                } else if ("process".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                } else if ("occurrencePeriods".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), "")
                                                } else if ("ifcMaterial".equals(field)) {
                                                    firstLevelTotal.columnWithValue.put(getLocaleMessage("detailedReport.ifcMaterial", null, locale), "")
                                                } else {
                                                    firstLevelTotal.columnWithValue.put(field, "")
                                                }

                                            }

                                        }
                                        reportTable.firstLevelTotals.add(firstLevelTotal)
                                    }
                                    index++

                                    if (index == groupedDatasets.keySet().toList().size() && previousTotalString && totalsAdditionalQuestions) {
                                        Map<String, Double> theTotal = totalsByAdditionalQuestionAndRule.get(previousTotalString)

                                        if (theTotal) {
                                            DetailReportRow secondLevelTotalRow = new DetailReportRow()
                                            secondLevelTotalRow.secondLevelTotalKey = previousTotalString

                                            if (expandFeature.showResultCategory) {
                                                secondLevelTotalRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), "")
                                            }
                                            secondLevelTotalRow.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "")
                                            secondLevelTotalRow.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "")
                                            secondLevelTotalRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")

                                            calculationRules.each { CalculationRule calculationRule ->
                                                secondLevelTotalRow.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                                                        resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, theTotal.get(calculationRule.calculationRule),
                                                                Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))
                                            }

                                            secondLevelTotalRow.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), "")

                                            if (additionalQuestions) {
                                                additionalQuestions.each { String questionId ->
                                                    String answer = ""
                                                    Question question = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })

                                                    if (totalsAdditionalQuestions.collect({ it.questionId }).contains(questionId)) {
                                                        String userAnswer = lastDatasetInList.additionalQuestionAnswers?.get(questionId)
                                                        Resource lastDatasetResource = resourceCache.getResource(lastDatasetInList)

                                                        if (questionId.equals("serviceLife")) {
                                                            ResourceType lastDatasetResourceSubType = resourceTypeCache.getResourceSubType(lastDatasetResource)
                                                            String serviceLife = "${getServiceLife(lastDatasetInList, lastDatasetResource, lastDatasetResourceSubType, entity, indicator)?.toInteger() ?: ""}"

                                                            if ("-1" == serviceLife) {
                                                                answer = getLocaleMessage("resource.serviceLife.as_building", null, locale)
                                                            } else {
                                                                answer = serviceLife
                                                            }
                                                        } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(questionId)) {
                                                            if ("default".equals(userAnswer)) {
                                                                answer = entity?.getDefaultTransportFromSubType(lastDatasetResource)
                                                            } else if (userAnswer) {
                                                                answer = userAnswer
                                                            }
                                                        } else if (com.bionova.optimi.core.Constants.EOL_QUESTIONID.equals(questionId)) {
                                                            ResourceType lastDatasetResourceSubType = resourceTypeCache.getResourceSubType(lastDatasetResource)
                                                            EolProcess eolProcess = eolProcessService.getEolProcessForDataset(lastDatasetInList, lastDatasetResource, lastDatasetResourceSubType, projectCountry, null, lcaModel, eolProcessCache)

                                                            if (eolProcess) {
                                                                answer = eolProcessService.getLocalizedName(eolProcess)
                                                            }
                                                        } else {
                                                            if (userAnswer) {
                                                                if ("select".equals(question.inputType) || "checkbox".equals(question.inputType) || "radio".equals(question.inputType)) {
                                                                    if (question.isResourceQuestion) {
                                                                        answer = optimiStringUtils.truncate(optimiResourceService.getUiLabel(userAnswer, null), 60)
                                                                    } else {
                                                                        answer = question.choices?.find({
                                                                            userAnswer.equals(it.answerId)
                                                                        })?.localizedAnswer
                                                                    }
                                                                } else {
                                                                    answer = userAnswer
                                                                }
                                                            }
                                                        }
                                                    }
                                                    secondLevelTotalRow.columnWithValue.put(question?.questionId, answer)
                                                }
                                            }

                                            if (additionalFields) {
                                                additionalFields.each { String field ->

                                                    if ("resourceType".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                    } else if ("environmentDataSource".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                    } else if ("localizedName".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                    } else if ("construction".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                    } else if ("process".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                    } else if ("occurrencePeriods".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), "")
                                                    } else if ("ifcMaterial".equals(field)) {
                                                        secondLevelTotalRow.columnWithValue.put(getLocaleMessage("detailedReport.ifcMaterial", null, locale), "")
                                                    } else {
                                                        secondLevelTotalRow.columnWithValue.put(field, "")
                                                    }

                                                }

                                            }
                                            reportTable.secondLevelTotals.add(secondLevelTotalRow)
                                        }
                                    }
                                }
                            } else if (expandFeature.sortByQuestionId || expandFeature.sortByCategoryId) {
                                Map<String, TreeMap<String, Double>> totalsByQuestionOrCategoryAndRule = new LinkedHashMap<String, TreeMap<String, Double>>()

                                Map resultsForIndicatorPerCalculationRule = resultsForIndicator.groupBy { it.calculationRuleId }

                                groupedDatasets.each { String key, List<Dataset> datasets ->
                                    Dataset lastDatasetInList
                                    int datasetAmount = datasets.size()
                                    for (Dataset d: datasets) {
                                        Resource resource = resourceCache.getResource(d)
                                        if (!resource?.construction || (resource?.construction && "LCC".equalsIgnoreCase(indicator.assessmentType) && (d.userSetCost || d.userSetTotalCost))) {
                                            ResourceType subType = resourceTypeCache.getResourceSubType(resource)
                                            String userInput = excelDump ? d.answerIds?.get(0) : resultFormattingResolver.formatByTwoSignificantDigits(user, d.answerIds?.get(0) as Double, disableThousandSeparator)

                                            DetailReportRow reportRow = new DetailReportRow()

                                            if (expandFeature.showResultCategory) {
                                                reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), d.resultCategory)
                                            }
                                            String random = UUID.randomUUID().toString()
                                            String datasetStatusIcon = datasetService.generateVerifiedIconFromDataset(d)
                                            String link = resource ? opt.renderDataCardBtn(indicatorId: indicator?.indicatorId, resourceId: resource?.resourceId, profileId: resource?.profileId, childEntityId: entity?.id.toString(), showGWP: true, infoId: random) : ""
                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "${html ? "${optimiStringUtils.truncate(optimiResourceService.getUiLabel(resource), 60)}${link}${datasetStatusIcon}" : "${optimiResourceService.getUiLabel(resource)}"}")
                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "${userInput}")

                                            if (d.userGivenUnit || resource?.unitForData) {
                                                reportRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "${d.userGivenUnit ? d.userGivenUnit : resource.unitForData}")
                                            } else {
                                                reportRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")
                                            }

                                            calculationRules.each { CalculationRule calculationRule ->
                                                Double totalResult

                                                List<CalculationResult> resultsForCalculationRule = resultsForIndicatorPerCalculationRule?.getAt(calculationRule.calculationRuleId)
                                                // Pass null as calculationRuleId because resultsForCalculationRule are already filtered by calculationRuleId
                                                Double result = entity.getResultForDataset(d.manualId, indicator.indicatorId,
                                                        d.resultCategoryId, null, resultsForCalculationRule, false)

                                                if (!d.resultCategoryObject?.ignoreFromTotals?.contains(calculationRule.calculationRuleId) &&
                                                        !d.resultCategoryObject?.ignoreFromTotals?.isEmpty()) {
                                                    totalResult = totalResult ? totalResult + result : result
                                                }
                                                reportRow.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                                                        resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, result,
                                                                Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))

                                                if (totalResult && datasetAmount > 0) {
                                                    if (expandFeature.totalsByQuestionId||expandFeature.totalsByCategoryId) {
                                                        Map<String, Double> existingTheTotal = totalsByQuestionOrCategoryAndRule.get(key)
                                                        Double theTotal = existingTheTotal?.get(calculationRule.calculationRule)
                                                        theTotal = theTotal ? theTotal + totalResult : totalResult

                                                        if (!existingTheTotal) {
                                                            existingTheTotal = new TreeMap<String, Double>()
                                                        }

                                                        existingTheTotal.put(calculationRule.calculationRule, theTotal)
                                                        totalsByQuestionOrCategoryAndRule.put(key, existingTheTotal)
                                                    }
                                                }
                                            }

                                            reportRow.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), datasetService.getQuestion(d)?.localizedQuestion)

                                            if (additionalQuestions) {
                                                Resource datasetResource = resourceCache.getResource(d)
                                                additionalQuestions.each { String questionId ->
                                                    Question additionalQuestion = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })
                                                    //skip adding column value if additionalQuestion wasn't found to avoid column misalignment
                                                    if (!additionalQuestion){
                                                        return
                                                    }

                                                    String userAnswer = d.additionalQuestionAnswers?.get(questionId)
                                                    String answer = ""

                                                    if (questionId.equals("serviceLife")) {
                                                        ResourceType resourceSubType = resourceTypeCache.getResourceSubType(datasetResource)
                                                        String serviceLife = "${getServiceLife(d, datasetResource, resourceSubType, entity, indicator)?.toInteger() ?: ""}"

                                                        if ("-1" == serviceLife) {
                                                            answer = getLocaleMessage("resource.serviceLife.as_building", null, locale)
                                                        } else {
                                                            answer = serviceLife
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(questionId)) {
                                                        if ("default".equals(userAnswer)) {
                                                            answer = entity?.getDefaultTransportFromSubType(datasetResource)
                                                        } else if (userAnswer) {
                                                            answer = userAnswer
                                                        }
                                                    } else if (com.bionova.optimi.core.Constants.EOL_QUESTIONID.equals(questionId)) {
                                                        EolProcess eolProcess = eolProcessService.getEolProcessForDataset(d, resource, subType, projectCountry, null, lcaModel, eolProcessCache)

                                                        if (eolProcess) {
                                                            answer = eolProcessService.getLocalizedName(eolProcess)
                                                        }
                                                    } else {
                                                        if (userAnswer) {
                                                            if ("select".equals(additionalQuestion?.inputType) || "checkbox".equals(additionalQuestion?.inputType) || "radio".equals(additionalQuestion?.inputType)) {
                                                                if (additionalQuestion.isResourceQuestion) {
                                                                    answer = optimiStringUtils.truncate(optimiResourceService.getUiLabel(resourceCache.getResource(userAnswer, null)), 60)
                                                                } else {
                                                                    answer = additionalQuestion.choices?.find({
                                                                        userAnswer.equals(it.answerId)
                                                                    })?.localizedAnswer
                                                                }
                                                            } else {
                                                                answer = userAnswer
                                                            }
                                                        }
                                                    }
                                                    reportRow.columnWithValue.put(additionalQuestion?.questionId, answer)
                                                }
                                            }

                                            if (additionalFields) {
                                                additionalFields.each { String field ->

                                                    if ("resourceType".equals(field)) {
                                                        if (subType) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), resourceTypeService.getLocalizedName(subType))
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                        }
                                                    } else if ("environmentDataSource".equals(field)) {
                                                        if (resource?.environmentDataSource) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), resource.environmentDataSource)
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                        }

                                                    } else if ("localizedName".equals(field)) {
                                                        if (optimiResourceService.getLocalizedName(resource)) {
                                                            reportRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), optimiResourceService.getLocalizedName(resource))
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                        }
                                                    } else if ("construction".equals(field)) {
                                                        if (d.uniqueConstructionIdentifier && d.uniqueConstructionIdentifier != null && d.parentConstructionId) {
                                                            Dataset constrctionDataset = allValidDatasets.find({d.uniqueConstructionIdentifier.equals(it.uniqueConstructionIdentifier) && !it.parentConstructionId })
                                                            Resource constructionResource = resourceCache.getResource(constrctionDataset)
                                                            String partOfConstruction = constructionResource?.isDummy ? constrctionDataset?.groupingDatasetName :
                                                                    optimiResourceService.getLocalizedName(constructionResource)
                                                            reportRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), partOfConstruction ? partOfConstruction : "")
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                        }
                                                    } else if ("process".equals(field)) {
                                                        if (d.resultCategoryObject?.process) {
                                                            Resource calculationResource = entity.getCalculationResourceForDataset(d.manualId,
                                                                    indicator.indicatorId, d.resultCategoryId, resultsForIndicator)
                                                            reportRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), calculationResource ?
                                                                    "${optimiResourceService.getLocalizedName(calculationResource)}${!calculationResource.active ? "(${getLocaleMessage("inactive", null, locale)})" : ""}" : "")
                                                        } else {
                                                            reportRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                        }
                                                    } else if ("occurrencePeriods".equals(field)) {
                                                        // TODO: Trang: comment out to deprecate the function, will delete after testing done
                                                        //Double assestmentPeriod = (Double) indicator?.assessmentPeriodFixed ? indicator.assessmentPeriodFixed : indicator?.assessmentPeriodValueReference?.getDoubleValueForEntity(entity) ? indicator?.assessmentPeriodValueReference?.getDoubleValueForEntity(entity) : 0
                                                        //Integer serviceLife = getServiceLife(d, resource, entity, indicator)?.toInteger()
                                                        //List <Integer> resultsForDatasetOccurrency = calculateOccurrencePeriods(serviceLife, assestmentPeriod, d.resultCategoryObject)
                                                        List <Integer> resultsForDatasetOccurrency = d.occurrencePeriods
                                                        reportRow.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), resultsForDatasetOccurrency ? resultsForDatasetOccurrency?.join(",")?.toString() : "")
                                                    } else if ("class".equalsIgnoreCase(field)) {
                                                        reportRow.columnWithValue.put(field, d.persistedOriginalClass ?: "")
                                                    } else if ("ifcMaterial".equalsIgnoreCase(field)) {
                                                        DatasetImportFields datasetImportFields = allDatasetImportFields.find { it.id.toString() == d.datasetImportFieldsId }
                                                        String ifcMaterial = ""

                                                        if (datasetImportFields && datasetImportFields.importFields) {
                                                            ifcMaterial = datasetImportFields.importFields.first().get("IFCMATERIAL")

                                                            if (!ifcMaterial) {
                                                                ifcMaterial = datasetImportFields.importFields.first().get("MATERIAL")
                                                            }
                                                        }
                                                        reportRow.columnWithValue.put(field, ifcMaterial ?: "")
                                                    } else {
                                                        if (resource) {
                                                            String value = DomainObjectUtil.callGetterByAttributeName(field, resource)

                                                            if (!value && subType) {
                                                                value = DomainObjectUtil.callGetterByAttributeName(field, subType)
                                                            }
                                                            reportRow.columnWithValue.put(field, value ?: "")
                                                        } else {
                                                            reportRow.columnWithValue.put(field, "")
                                                        }
                                                    }
                                                }
                                            }

                                            List<DetailReportRow> reportRows = reportTable.reportRows.get(key)
                                            reportRow.secondLevelTotalKey = key

                                            if (reportRows) {
                                                reportRows.add(reportRow)
                                            } else {
                                                reportRows = [reportRow]
                                            }
                                            reportTable.reportRows.put(key, reportRows)
                                            lastDatasetInList = d

                                        }

                                    }

                                    if (lastDatasetInList && totalsByQuestionOrCategoryAndRule) {
                                        Map<String, Double> theTotal = totalsByQuestionOrCategoryAndRule.get(key)

                                        if (theTotal) {
                                            DetailReportRow totalRow = new DetailReportRow()

                                            if (expandFeature.showResultCategory) {
                                                if (expandFeature.sortByCategoryId) {
                                                    totalRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), lastDatasetInList.resultCategoryId)
                                                } else {
                                                    totalRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), "")
                                                }
                                            }
                                            totalRow.columnWithValue.put(getLocaleMessage("results.expand.resource", null, locale), "")
                                            totalRow.columnWithValue.put(getLocaleMessage("results.expand.input", null, locale), "")
                                            totalRow.columnWithValue.put(getLocaleMessage("entity.show.unit", null, locale), "")

                                            calculationRules.each { CalculationRule calculationRule ->
                                                totalRow.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                                                        resultFormattingResolver.formatByResultFormattingObject(resultFormatting, user, indicator, theTotal.get(calculationRule.calculationRule),
                                                                Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, excelDump))
                                            }

                                            totalRow.columnWithValue.put(getLocaleMessage("results.expand.question", null, locale), datasetService.getQuestion(lastDatasetInList)?.localizedQuestion)

                                            if (additionalQuestions) {
                                                additionalQuestions.each { String questionId ->
                                                    Question additionalQuestion = additionalQuestionObjects?.find({ Question q -> questionId.equals(q.questionId) })
                                                    totalRow.columnWithValue.put(additionalQuestion?.questionId, "")
                                                }
                                            }

                                            if (additionalFields) {
                                                additionalFields.each { String field ->

                                                    if ("resourceType".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("resource.type", null, locale), "")
                                                    } else if ("environmentDataSource".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("resource.environmentDataSource", null, locale), "")
                                                    } else if ("localizedName".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("entity.name", null, locale), "")
                                                    } else if ("construction".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("construction.construction", null, locale), "")
                                                    } else if ("process".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("result.expand.transform_process", null, locale), "")
                                                    } else if ("occurrencePeriods".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("result_per_occurrence", null, locale), "")
                                                    } else if ("ifcMaterial".equals(field)) {
                                                        totalRow.columnWithValue.put(getLocaleMessage("detailedReport.ifcMaterial", null, locale), "")
                                                    } else {
                                                        totalRow.columnWithValue.put(field, "")
                                                    }

                                                }

                                            }
                                            totalRow.secondLevelTotalKey = key
                                            reportTable.secondLevelTotals.add(totalRow)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return reportTable
    }

    private Locale getLocale(Boolean englishOnly = null) {
        if (englishOnly) {
            return new Locale("en", "US")
        } else {
            GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
            HttpServletRequest request = webRequest.getRequest()
            HttpServletResponse response = webRequest.getResponse()
            HttpSession session = webRequest.getSession()
            return localeResolverUtil.resolveLocale(session, request, response)
        }
    }

    private HttpSession getSession() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        HttpServletRequest request = webRequest.getRequest()
        return request?.getSession()
    }
    private getLocaleMessage(String key, def args = null,  Locale locale) {
        String value = key
        try {
            value = messageSource.getMessage(value, args, locale)
        } catch (Exception e) {
            loggerUtil.error(log, "Error in getLocaleMessage:", e)
            flashService.setErrorAlert("Error in getLocaleMessage: ${e.getMessage()}", true)
        }
        return value
    }
    // TODO: Trang: comment out to deprecate the function, will delete after testing done. Now we use occurrencePeriods in dataset
    /*private calculateOccurrencePeriods(Integer serviceLife, Double assestmentPeriod, ResultCategory category) {
        List <Integer> periods = []

        if (serviceLife && serviceLife > 0 && assestmentPeriod && category.calculateReplacementsOnly) {
            for (Integer i = serviceLife; i < assestmentPeriod; i = i + serviceLife) {
                periods.add(i)
            }
        }
        return periods
    }*/

    private Double getServiceLife(Dataset dataset, Resource resource, ResourceType resourceSubType, Entity childEntity, Indicator indicator) {
        Double serviceLife
        Boolean useSpecificServiceLifes = indicator?.useSpecificServiceLifes
        String defaultServiceLife = childEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)

        if (!useSpecificServiceLifes && resource && defaultServiceLife && ("default".equals(dataset?.additionalQuestionAnswers?.get("serviceLife")) || !dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            if ("resourceServiceLife".equals(defaultServiceLife)||resource.serviceLifeOverride) {
                if (resource.serviceLife != null) {
                    serviceLife = resource.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceSubType?.serviceLifeTechnical
                }
            } else if (resourceSubType) {
                serviceLife = DomainObjectUtil.callGetterByAttributeName(defaultServiceLife, resourceSubType)
            }
        } else if ("permanent".equals(dataset?.additionalQuestionAnswers?.get("serviceLife"))) {
            serviceLife = resource?.serviceLife
        } else {
            if (dataset?.additionalQuestionAnswers?.get("serviceLife") != null &&
                    DomainObjectUtil.isNumericValue(dataset.additionalQuestionAnswers.get("serviceLife").toString())) {
                serviceLife = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get("serviceLife").toString())
            } else {
                if (resource?.serviceLife != null) {
                    serviceLife = resource.serviceLife
                } else {
                    // Failover to technical servicelife
                    serviceLife = resourceSubType?.serviceLifeTechnical
                }
            }
        }
        return serviceLife
    }
     synchronized DetailReportTable createResultSummaryTable(Entity entity, Indicator indicator,Boolean disableThousandSeparator = null, Boolean excelDump = Boolean.FALSE, Boolean englishOnly = null) {
         DetailReportTable reportTable
         if (entity) {
             reportTable = new DetailReportTable()
             User user = userService.getCurrentUser()
             Entity parentEntity = entity.parentById
             List<CalculationResult> resultsForIndicator = entity?.getCalculationResultObjects(indicator.indicatorId, null, null)

             if (resultsForIndicator) {
                 List<IndicatorReportItem> reportItems = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator?.report?.reportItems)?.findAll({it.categories})
                 List<IndicatorReportItem> tempReportItems = []
                 //patch - 16709
                 List<IndicatorReportItem> sources = reportItems?.findAll({it?.reportItemId})?.flatten() as List<IndicatorReportItem>
                 if(sources){
                     tempReportItems.addAll(sources.get(0))
                 }
                 if(tempReportItems){
                     reportItems = tempReportItems
                 }

                 List<CalculationRule> allCalculationRules = indicator?.getResolveCalculationRules(parentEntity)
                 List<CalculationRule> calculationRules
                 if (allCalculationRules) {
                     if (reportItems) {
                         calculationRules = allCalculationRules?.findAll({
                             it?.calculationRuleId && reportItems?.collect({IndicatorReportItem ir -> ir?.rules})?.flatten()?.contains(it?.calculationRuleId)
                         })?.sort({ it -> reportItems?.collect ({IndicatorReportItem i -> i?.rules})?.flatten()?.indexOf(it?.calculationRuleId) })
                     } else {
                         calculationRules = new ArrayList<CalculationRule>(allCalculationRules)
                     }
                 }
                 if(!calculationRules){
                     calculationRules = new ArrayList<CalculationRule>(allCalculationRules)
                 }

                 List<ResultCategory> resultCategories = []
                 List<ResultCategory> allResultCategories = []
                 resultCategories = indicator?.getResolveResultCategories(parentEntity ?: entity)?.findAll({
                     it.resultCategoryId && reportItems?.collect({IndicatorReportItem ir -> ir.categories})?.flatten()?.contains(it.resultCategoryId)
                 })?.sort({ it -> reportItems?.collect ({IndicatorReportItem i -> i?.categories})?.flatten()?.indexOf(it?.resultCategoryId) })
                 resultCategories?.each({ ResultCategory it ->
                     boolean hasResult = false
                     boolean hasApplyConditionData = false
                     if (calculationRules ) {
                         for (CalculationRule calcRule : calculationRules) {
                             if (resultsForIndicator?.find({ CalculationResult cr ->
                                 it.resultCategoryId.equals(cr.resultCategoryId) && calcRule.calculationRuleId.equals(it.calculationRuleId)
                             })?.result) {
                                 hasResult = true
                                 break
                             }
                         }
                     }
                     if (it.applyConditions?.dataPresent && hasResult) {
                         hasApplyConditionData = true
                     }
                     if (!it.applyConditions || hasApplyConditionData) {
                         allResultCategories?.add(it)
                         List<ResultCategory> temp = indicator?.getResolveResultCategories(parentEntity ?: entity)?.findAll({ ResultCategory r ->
                             r.resultCategoryId && reportItems?.collect({ IndicatorReportItem ir -> ir?.expandableCategories?.get(it.resultCategoryId) })?.flatten()?.contains(r?.resultCategoryId)
                         })
                         temp = temp?.sort({ ResultCategory rc -> reportItems?.collect({ IndicatorReportItem ir -> ir?.expandableCategories?.get(it?.resultCategoryId) })?.flatten()?.indexOf(rc?.resultCategoryId) })
                         allResultCategories?.addAll(temp)
                     }
                 })

                 String tableSource
                 if(reportItems) {
                     //patch - 16709
                     List<String> tableSourceList = reportItems?.findAll({ IndicatorReportItem t -> t?.tableSource })?.collect({ IndicatorReportItem it -> it?.tableSource })?.flatten() as List<String>
                     tableSource = tableSourceList?.size() > 0 ? tableSourceList?.get(0) : ''
                 }
                 reportTable = getReportTable(reportTable ,allResultCategories, calculationRules , tableSource , disableThousandSeparator , indicator , englishOnly , resultsForIndicator , user , excelDump)
             }
             }
         return reportTable
     }

    DetailReportTable getReportTable(reportTable,resultCategories , calculationRules , tableSource , disableThousandSeparator , indicator , englishOnly , resultsForIndicator , user , excelDump){
        try {
            if (resultCategories && !resultCategories.isEmpty() && calculationRules && !calculationRules.isEmpty()) {
                reportTable.headings = new ArrayList<String>()
               if(!("invertedTable").equals(tableSource)){
                   reportTable.headings.add(getLocaleMessage("results.expand.section", null, locale))
               }
                reportTable.headings.add(getLocaleMessage("results.expand.resultCategory", null, locale))
                if("invertedTable".equals(tableSource)){
                    reportTable =  getInvertedTable(reportTable,resultCategories , calculationRules , tableSource , indicator , englishOnly , resultsForIndicator , user , disableThousandSeparator , excelDump)
                }else{
                    reportTable = getDefaultTable(reportTable,resultCategories , calculationRules , tableSource , indicator , englishOnly , resultsForIndicator , user , disableThousandSeparator , excelDump)
                }



            }
        }catch(e){
            loggerUtil.error(log, 'Error in getReportTable()', e)
            flashService.setErrorAlert("Error in getReportTable(): ${e.getMessage()}", Boolean.TRUE)
        }
        return  reportTable
    }
      DetailReportTable  getDefaultTable(reportTable,resultCategories , calculationRules , tableSource , indicator , englishOnly , resultsForIndicator , user , disableThousandSeparator ,excelDump){
          calculationRules.each { CalculationRule calculationRule ->
              if (englishOnly) {
                  reportTable.headings.add("${calculationRule.name?.get("EN")} ${calculationRule.unit?.get("EN")}")
              } else {
                  reportTable.headings.add("${calculationRule.localizedName} ${calculationRuleService.getLocalizedUnit(calculationRule, indicator)}")
              }
          }
          resultCategories.each { ResultCategory resultCategory ->
              DetailReportRow reportRow = new DetailReportRow()
              if(!("invertedTable").equals(tableSource)){
                  reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), resultCategory.resultCategory)
              }
              // reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), resultCategory.resultCategory)
              reportRow.columnWithValue.put(getLocaleMessage("results.expand.resultCategory", null, locale), "${englishOnly ? resultCategory.name?.get("EN") : resultCategory.localizedName}")

              List<DetailReportRow> reportRows = reportTable.reportRows.get(resultCategory.resultCategoryId)
              // reportRow.secondLevelTotalKey = key

              if (reportRows) {
                  reportRows.add(reportRow)
              } else {
                  reportRows = [reportRow]
              }
              reportTable.reportRows.put(resultCategory.resultCategoryId, reportRows)

              DetailReportRow firstLevelTotal = new DetailReportRow()
              firstLevelTotal.firstLevelTotalKey = resultCategory.resultCategoryId
              calculationRules.each { CalculationRule calculationRule ->
                  firstLevelTotal.columnWithValue.put("${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}",
                          resultFormattingResolver.formatByResultFormatting(user, indicator, null, resultsForIndicator.find({ it.calculationRuleId == calculationRule.calculationRuleId && it.resultCategoryId == resultCategory.resultCategoryId })?.result, getSession(),
                                  Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, null, excelDump))
              }
              reportTable.firstLevelTotals.add(firstLevelTotal)

          }
          return reportTable
    }
    DetailReportTable  getInvertedTable(reportTable,resultCategories , calculationRules , tableSource , indicator , englishOnly , resultsForIndicator , user , disableThousandSeparator , excelDump) {

        resultCategories.each { ResultCategory resultCategory ->
            if (englishOnly) {
                reportTable.headings.add("${resultCategory.name?.get("EN")}")
            } else {
                reportTable.headings.add("${resultCategory.localizedName}")
            }
        }
        calculationRules.each { CalculationRule calculationRule ->
            DetailReportRow reportRow = new DetailReportRow()

            if(!("invertedTable").equals(tableSource)){
                reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), resultCategory.resultCategory)
            }
            // reportRow.columnWithValue.put(getLocaleMessage("results.expand.section", null, locale), resultCategory.resultCategory)
            reportRow.columnWithValue.put(getLocaleMessage("results.expand.resultCategory", null, locale), "${englishOnly ? calculationRule.name?.get("EN") : calculationRule.localizedName} ${englishOnly ? calculationRuleService.getLocalizedUnit(calculationRule, null, "EN") : calculationRule.getLocalizedUnit()}")

            List<DetailReportRow> reportRows = reportTable.reportRows.get(calculationRule.calculationRuleId)

            if (reportRows) {
                reportRows.add(reportRow)
            } else {
                reportRows = [reportRow]
            }
            reportTable.reportRows.put(calculationRule.calculationRuleId, reportRows)

            DetailReportRow firstLevelTotal = new DetailReportRow()
            firstLevelTotal.firstLevelTotalKey = calculationRule.calculationRuleId
            resultCategories.each { ResultCategory resultCategory ->
                firstLevelTotal.columnWithValue.put("${englishOnly ? resultCategory.name?.get("EN") : resultCategory.localizedName}",
                        resultFormattingResolver.formatByResultFormatting(user, indicator, null, resultsForIndicator.find({ it.calculationRuleId == calculationRule.calculationRuleId && it.resultCategoryId == resultCategory.resultCategoryId })?.result, getSession(),
                                Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "", disableThousandSeparator, null, excelDump))
            }
            reportTable.firstLevelTotals.add(firstLevelTotal)

        }
        return reportTable
    }

    private ResourceCache initResourceCache(List<Dataset> datasets, List<Question> additionalQuestions) {

        ResourceCache resourceCache = ResourceCache.init(datasets)

        List<String> resourceAdditionalQuestionIds = additionalQuestions?.findResults { Question question ->
            question.isResourceQuestion ? question.questionId : null
        }

        if (!resourceAdditionalQuestionIds) {
            return resourceCache
        }

        Set<String> resourceIds = []
        datasets.each { Dataset dataset ->
            resourceIds.addAll(resourceAdditionalQuestionIds.findResults { String questionId ->
                dataset.additionalQuestionAnswers?.get(questionId)
            })
        }

        if (resourceIds) {
            resourceCache.cache(resourceIds)
        }

        return resourceCache
    }

    private List<Dataset> getAllValidDatasets(Entity entity, Indicator indicator, IndicatorExpandFeature expandFeature, List<CalculationResult> resultsForIndicator) {

        List<Dataset> allValidDatasets = []

        List<ResultCategory> resultCategories = getAllResultCategories(entity, indicator, expandFeature)
        if (!resultCategories || resultCategories.isEmpty()) {
            log.debug "No resultCategories for entity ${entity} and indicator '${indicator.indicatorId}'"
            return allValidDatasets
        }

        Map resultsForIndicatorPerResultCategory = resultsForIndicator.groupBy { it.resultCategoryId }

        Set<Dataset> allValidUniqueDatasets = [] as Set
        resultCategories.each { ResultCategory resultCategory ->
            List<CalculationResult> calculationResultsByCategory = resultsForIndicatorPerResultCategory?.getAt(resultCategory.resultCategoryId)

            if (calculationResultsByCategory) {
                calculationResultsByCategory.each { CalculationResult calculationResult ->
                    Set<Dataset> sets = calculationResultService.getDatasets(calculationResult, entity)
                    if (sets) {
                        allValidUniqueDatasets.addAll(sets)
                    }
                }
            }

        }

        if (!allValidUniqueDatasets.isEmpty()) {
            allValidUniqueDatasets = allValidUniqueDatasets.unique({ it.manualId })
            List<Dataset> allValidDatasetsByResultCategory = []

            allValidUniqueDatasets.each { Dataset d ->
                resultCategories.each { ResultCategory resultCategory ->

                    List<CalculationResult> resultsForResultCategory = resultsForIndicatorPerResultCategory?.getAt(resultCategory.resultCategoryId)
                    Double result = entity.getResultForDataset(d.manualId, indicator.indicatorId, resultCategory.resultCategoryId, null, resultsForResultCategory, false)

                    if (result != null) {
                        Dataset dataset = new Dataset(manualId: d.manualId, queryId: d.queryId,
                                sectionId: d.sectionId, questionId: d.questionId, resourceId: d.resourceId,
                                profileId: d.profileId, answerIds: d.quantity, quantity: d.quantity, points: d.points, occurrencePeriods: d.occurrencePeriods,
                                additionalQuestionAnswers: d.additionalQuestionAnswers, resultCategoryObject: resultCategory, persistedOriginalClass: d.persistedOriginalClass,
                                userGivenUnit: d.userGivenUnit, calculationResourceId: d.calculationResourceId, calculationProfileId: d.calculationProfileId, uniqueConstructionIdentifier: d.uniqueConstructionIdentifier,
                                parentConstructionId: d.parentConstructionId, userSetCost: d.userSetCost, userSetTotalCost: d.userSetTotalCost, datasetImportFieldsId: d.datasetImportFieldsId, groupingDatasetName: d.groupingDatasetName)
                        dataset.resultCategory = resultCategory.resultCategory
                        // Transient fields not binding in grails 4?
                        dataset.resultCategoryId = resultCategory.resultCategoryId
                        // Transient fields not binding in grails 4?
                        dataset.verified = d.verified
                        dataset.unlockedFromVerifiedStatus = d.unlockedFromVerifiedStatus
                        allValidDatasetsByResultCategory.add(dataset)
                    }
                }
            }

            allValidDatasets = allValidDatasetsByResultCategory
        }

        return allValidDatasets
    }

    private List<ResultCategory> getAllResultCategories(Entity entity, Indicator indicator, IndicatorExpandFeature expandFeature) {

        Entity parentEntity = entity.parentById

        List<ResultCategory> resultCategories = expandFeature.categories && !expandFeature.categories.isEmpty() ?
                indicator.getResolveResultCategories(parentEntity ?: entity)?.findAll({
                    expandFeature.categories.contains(it.resultCategoryId)
                }) : indicator.getResolveResultCategories(parentEntity ?: entity)

        //task 8268
        if (resultCategories && indicator.expandFeatureCategoryIds) {
            resultCategories = resultCategories.findAll({ indicator.expandFeatureCategoryIds.contains(it.resultCategoryId) })
        }

        return resultCategories
    }

    private List<CalculationRule> getAllCalculationRules(Entity parentEntity, Indicator indicator, IndicatorExpandFeature expandFeature) {

        List<CalculationRule> calculationRules = []

        List<CalculationRule> allCalculationRules = indicator.getResolveCalculationRules(parentEntity)
        if (!allCalculationRules) {
            log.debug "No Calculation Rules for entity ${parentEntity} and indicator '${indicator.indicatorId}'"
            return calculationRules
        }

        if (expandFeature.calculationRules) {
            calculationRules = allCalculationRules.findAll({
                it.calculationRuleId && expandFeature.calculationRules.contains(it.calculationRuleId)
            })?.sort({it -> expandFeature?.calculationRules?.indexOf(it.calculationRuleId)})
        } else {
            calculationRules = new ArrayList<CalculationRule>(allCalculationRules)
        }

        return calculationRules
    }

    private List<Question> getAdditionalQuestionObjects(Indicator indicator, List<String> additionalQuestions) {

        List<Question> additionalQuestionObjects = []

        if (!additionalQuestions) {
            log.debug "No Additional Questions"
            return additionalQuestionObjects
        }

        Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        additionalQuestionObjects = additionalQuestionsQuery.getAllQuestions()?.findAll({ Question q -> additionalQuestions.contains(q.questionId) })
        if (indicator?.classificationsMap?.contains(Constants.ORGANIZATION_CLASSIFICATION_ID)) {
            Question privateClassificationQuestion = privateClassificationListService.getPrivateClassificationQuestion()
            if (privateClassificationQuestion) {
                additionalQuestions.contains(privateClassificationQuestion.questionId)?: additionalQuestions.add(privateClassificationQuestion.questionId)
                additionalQuestionObjects.add(privateClassificationQuestion)
            }
        }

        return additionalQuestionObjects
    }
}
