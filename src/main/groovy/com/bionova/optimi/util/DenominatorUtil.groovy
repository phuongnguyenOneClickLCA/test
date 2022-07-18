package com.bionova.optimi.util

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.service.DenominatorService
import com.bionova.optimi.core.service.ValueReferenceService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpSession
import java.text.DecimalFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class DenominatorUtil {
    DenominatorService denominatorService
    def datasetService
    def queryService
    def userService
    def unitConversionUtil
    def resultManipulatorService
    ValueReferenceService valueReferenceService
    ResultFormattingResolver resultFormattingResolver
    private static final Log log = LogFactory.getLog(DenominatorUtil.class)

    public Double getFinalValueForDenominator(Entity entity, Denominator denominator, ResourceCache resourceCache = null) {
        Double finalValue = 1

        if (entity && denominator) {
            if (denominator.denominatorValues) {
                finalValue = getValueReferenceValuesForDenominator(denominator, entity, resourceCache)
            }

            if (denominator.resultManipulator && finalValue != null) {
                Double valueByMultiplier = getMultiplierByDenominator(entity, denominator)

                if (valueByMultiplier != null) {
                    finalValue = valueByMultiplier != 0 ? finalValue / valueByMultiplier : finalValue
                } else {
                    finalValue = null
                }

                if (finalValue != null) {
                    Double valueByDivider = getDividerByDenominator(entity, denominator)

                    if (valueByDivider != null) {
                        finalValue = finalValue * valueByDivider
                    } else {
                        finalValue = null
                    }
                }
            }
        }
        return finalValue
    }

    Double useResultManipulatorForDoubleValue(ResultManipulator resultManipulator, Entity entity, Double value, ResourceCache resourceCache = null) {
        if (value) {
            List<ValueReference> multiplierList = resultManipulatorService.getMultipliersAsValueReferences(resultManipulator)
            List<ValueReference> dividerList = resultManipulatorService.getDividersAsValueReferences(resultManipulator)
            Double finalMultiplier = resultManipulator.fixedMultiplier
            Double finalDivider

            if (multiplierList) {
                Double multiplier

                for (ValueReference valueReference in multiplierList) {
                    multiplier = valueReferenceService.getDoubleValueForEntity(valueReference, entity, null, null, resourceCache)

                    if (multiplier != null) {
                        finalMultiplier = finalMultiplier != null ? finalMultiplier * multiplier : multiplier
                    }
                }
            }

            if (dividerList) {
                Double divider

                for (ValueReference valueReference in dividerList) {
                    divider = valueReferenceService.getDoubleValueForEntity(valueReference, entity, null, null, resourceCache)

                    if (divider != null && divider != 0) {
                        finalDivider = finalDivider != null ? finalDivider * divider : divider
                    }
                }
            }

            if (finalMultiplier != null) {
                value = value * finalMultiplier
            }

            if (finalDivider) {
                value = value / finalDivider
            }
        }
        return value
    }

    public Map<String, Double> getFinalValueForMonthlyDenominator(Entity entity, Denominator denominator) {
        Map<String, Double> finalValues = new LinkedHashMap<String, Double>()

        Constants.MONTHS.each { String month ->
            finalValues.put(month, 1)
        }

        if (entity && denominator?.requireMonthly) {
            if (denominator.denominatorValues) {
                finalValues = getMonthlyValueReferenceValuesForDenominator(denominator, entity)
            }

            if (denominator.resultManipulator && finalValues) {
                Double valueByMultiplier = getMultiplierByDenominator(entity, denominator)

                if (valueByMultiplier != null) {
                    finalValues.each {
                        it.value = it.value != null ? (valueByMultiplier != 0 ? it.value / valueByMultiplier : it.value) : null
                    }
                } else {
                    finalValues = null
                }

                if (finalValues != null) {
                    Double valueByDivider = getDividerByDenominator(entity, denominator)

                    if (valueByDivider != null) {
                        finalValues.each {
                            it.value = it.value != null ? it.value * valueByDivider : null
                        }
                    } else {
                        finalValues = null
                    }
                }
            }
        }
        return finalValues
    }

    public Double getMultiplierByDenominator(Entity entity, Denominator denominator, ResourceCache resourceCache = null) {
        Double multiplier = 1

        if (denominator?.resultManipulator) {
            for (ResultManipulator resultManipulator in denominator.resultManipulator) {
                if (resultManipulator.fixedMultiplier) {
                    multiplier = multiplier * resultManipulator.fixedMultiplier
                }
                List<ValueReference> multipliers = resultManipulatorService.getMultipliersAsValueReferences(resultManipulator)

                if (multipliers) {
                    for (ValueReference valueReference in multipliers) {
                        def datasets = getDatasetsFromEntity(entity, valueReference, Boolean.TRUE)
                        String resourceParam = valueReference.resourceParameter

                        if (datasets) {
                            datasets.each {
                                if (resourceParam) {
                                    try {
                                        Resource resource = resourceCache ? resourceCache.getResource(it) : datasetService.getResource(it)
                                        def resourceParamValue = DomainObjectUtil.callGetterByAttributeName(resourceParam, resource)

                                        if (resourceParamValue != null) {
                                            multiplier = multiplier * resourceParamValue
                                        }
                                    } catch (Exception e) {
                                        log.error("Invalid denominator multiplier from resource parameter: ${resourceParam}: ${e}")
                                    }
                                } else {
                                    multiplier = multiplier * it.quantity
                                }
                            }
                        } else if (!resultManipulator.allowFailure) {
                            multiplier = null
                            break
                        }
                    }
                }
            }
        }
        return multiplier
    }

    public Double getDividerByDenominator(Entity entity, Denominator denominator, ResourceCache resourceCache = null) {
        Double divider = 1

        if (denominator?.resultManipulator) {
            for (ResultManipulator resultManipulator in denominator.resultManipulator) {
                List<ValueReference> dividers = resultManipulatorService.getDividersAsValueReferences(resultManipulator)

                if (dividers) {
                    for (ValueReference valueReference in dividers) {
                        def datasets = getDatasetsFromEntity(entity, valueReference, Boolean.TRUE)
                        String resourceParam = valueReference.resourceParameter

                        if (datasets) {
                            datasets.each {
                                if (resourceParam) {
                                    try {
                                        Resource resource = resourceCache ? resourceCache.getResource(it) : datasetService.getResource(it)
                                        def resourceParamValue = DomainObjectUtil.callGetterByAttributeName(resourceParam, resource)

                                        if (resourceParamValue != null) {
                                            divider = divider * resourceParamValue
                                        }
                                    } catch (Exception e) {
                                        log.error("Invalid denominator divider from resource parameter: ${resourceParam}: ${e}")
                                    }
                                } else {
                                    divider = divider * it.quantity
                                }
                            }
                        } else if (!resultManipulator.allowFailure) {
                            divider = null
                            break
                        }
                    }
                }
            }
        }
        return divider
    }

    public Map<String, List<Dataset>> getDatasetsForDenominator(Denominator denominator, Entity entity) {
        Map datasets = [:]

        if (denominator && entity) {
            List<ValueReference> denominatorValues = denominatorService.getDenominatorValuesAsValueReferences(denominator.denominatorValues)

            if (denominatorValues) {
                if (denominatorValues.find({ it.priority })) {
                    denominatorValues.sort({ it.priority })
                }
                for (ValueReference valueReference in denominatorValues) {
                    def foundDatasets = getDatasetsFromEntity(entity, valueReference)

                    if (foundDatasets) {
                        List<Dataset> datasetsByQueryId = datasets.get(valueReference.queryId)

                        if (datasetsByQueryId) {
                            datasetsByQueryId.addAll(foundDatasets)
                        } else {
                            datasetsByQueryId = foundDatasets
                        }
                        datasets.put(valueReference.queryId, datasetsByQueryId)

                        if (denominator.applyFirstMatchOnly) {
                            break
                        }
                    }
                }
            }
        }
        return datasets
    }

    public Double getValueReferenceValuesForDenominator(Denominator denominator, Entity entity, ResourceCache resourceCache = null) {
        Double value

        if (denominator && entity) {
            def datasets = getDatasetsForDenominator(denominator, entity)

            // Apply no value if not all denominators present, task 10295
            if (datasets && (denominator.applyFirstMatchOnly||(!denominator.applyFirstMatchOnly && datasets.size() >= denominator.denominatorValues?.size()))) {
                datasets.each { String queryId, List<Dataset> dsets ->
                    dsets.each { Dataset dataset ->
                        if (dataset.quantity != null) {
                            Double quantity = new Double(dataset.quantity)

                            if (denominator.standardizeToM2 && "sq ft".equals(dataset.userGivenUnit)) {
                                Resource resource = resourceCache ? resourceCache.getResource(dataset) : datasetService.getResource(dataset)
                                quantity = unitConversionUtil.doConversion(quantity, datasetService.getThickness(dataset.additionalQuestionAnswers), dataset.userGivenUnit, resource, null, null, null, Boolean.FALSE, null, "m2")
                            }

                            Double conversionFactor = denominator.denominatorValues?.find({ dataset.resourceId?.equals(it.resourceId) })?.conversionFactor

                            if (conversionFactor != null) {
                                quantity = quantity * conversionFactor
                            }
                            value = value ? value * quantity : quantity
                        }
                    }
                }
            }
        }
        return value
    }

    public Map<String, Double> getMonthlyValueReferenceValuesForDenominator(Denominator denominator, Entity entity) {
        Map<String, Double> monthlyValues = new LinkedHashMap<String, Double>()

        if (denominator && entity) {
            def datasets = getDatasetsForDenominator(denominator, entity)

            if (datasets) {
                datasets.each { String queryId, List<Dataset> dsets ->
                    dsets.collect({ Dataset d -> d.monthlyAnswers })?.each { Map monthAndValue ->
                        monthAndValue?.each { String month, String value ->

                            if (value && DomainObjectUtil.isNumericValue(value)) {
                                Double existing = monthlyValues.get(month)
                                Double doubleValue = DomainObjectUtil.convertStringToDouble(value)

                                if (existing) {
                                    monthlyValues.put(month, (existing + doubleValue))
                                } else {
                                    monthlyValues.put(month, doubleValue)
                                }
                            }
                        }

                    }
                }
            }
        }
        return monthlyValues
    }

    private List<Dataset> getDatasetsFromEntity(Entity entity, ValueReference valueReference, Boolean forResultManipulation = Boolean.FALSE) {
        List<Dataset> datasets

        if (entity && valueReference) {
            datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, valueReference.queryId, valueReference.sectionId, valueReference.questionId)

            if (!datasets && entity.parentEntityId) {
                Entity parent = entity.getParentById()

                if (parent) {
                    datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(parent, valueReference.queryId, valueReference.sectionId, valueReference.questionId)
                }
            }

            if (valueReference.resourceId) {
                datasets = datasets?.findAll({valueReference.resourceId.equals(it.resourceId)})
            }
        }
        return forResultManipulation && valueReference?.resourceParameter ? datasets?.findAll({ Dataset d -> d.resourceId }) : datasets?.findAll({ Dataset d -> d.quantity })
    }

    public Double getOverallDenominator(Indicator indicator, Entity entity, ResourceCache resourceCache = null) {
        Double denominator = 1
        Denominator overallDenominator = (Denominator) indicator?.resolveDenominators?.find({
            "overallDenominator".equals(it.denominatorType)
        })

        if (overallDenominator && entity) {
            denominator = getFinalValueForDenominator(entity, overallDenominator, resourceCache)

            if (!denominator) {
                if (overallDenominator.failIfNotCalculable) {
                    denominator = null
                } else {
                    denominator = 1
                }
            }

        }
        return denominator
    }

    public String formatByDenominator(Indicator indicator, Denominator formattingDenominator, Double number, Entity entity = null) {
        DecimalFormat df = userService.getDefaultDecimalFormat()
        // df.applyPattern('###,###.00')
        String formattedScore

        if (number != null) {
            if (formattingDenominator && indicator) {
                HttpSession session = WebUtils.retrieveGrailsWebRequest().getCurrentRequest().getSession()
                formattedScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, formattingDenominator, number, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
            } else {
                formattedScore = df.format(number)
            }
        }
        return formattedScore
    }

    String getLocalizedUnit(Denominator denominator) {
        return denominatorService.getResultFormattingObject(denominator?.resultFormatting)?.localizedUnit
    }
}
