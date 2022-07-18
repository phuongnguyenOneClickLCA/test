package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.MultiplierWithConditions
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import groovy.transform.CompileStatic


class ValueReferenceService {
    def datasetService
    def indicatorService
    def denominatorUtil
    def userService
    def resultFormattingResolver
    def optimiResourceService

    private static final String DIVIDER = "divider"
    private static final String MULTIPLIER = "multiplier"

    List<EntityFile> getEntityFiles(ValueReference valueReference, Entity entity) {
        List<EntityFile> entityFiles

        if (entity && valueReference && valueReference.queryId && valueReference.questionId) {
            entityFiles = entity.getFiles()?.findAll { valueReference.queryId.equals(it.queryId) && valueReference.questionId.equals(it.questionId) }
        }
        return entityFiles
    }

    Map<String, Double> getDividerAndMultiplier(ValueReference valueReference, Entity entity, ResourceCache resourceCache = null) {
        Map<String, Double> dividerAndMultiplier = [:]

        if (valueReference && valueReference.denominatorIds && valueReference.indicatorId && entity) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(valueReference.indicatorId, true)

            if (indicator) {
                List<Denominator> denominators = indicator.denominatorList?.findAll({
                    valueReference.denominatorIds.contains(it.denominatorId)
                })

                if (denominators) {
                    for (Denominator denominator in denominators) {
                        Double divider = denominatorUtil.getDividerByDenominator(entity, denominator)

                        if (divider) {
                            Double existing = dividerAndMultiplier.get(DIVIDER)
                            existing = existing ? existing * divider : divider
                            dividerAndMultiplier.put(DIVIDER, existing)
                        }
                        divider = denominatorUtil.getValueReferenceValuesForDenominator(denominator, entity, resourceCache)

                        if (divider) {
                            Double existing = dividerAndMultiplier.get(DIVIDER)
                            existing = existing ? existing * divider : divider
                            dividerAndMultiplier.put(DIVIDER, existing)
                        }
                        Double multiplier = denominatorUtil.getMultiplierByDenominator(entity, denominator)

                        if (multiplier) {
                            Double existing = dividerAndMultiplier.get(MULTIPLIER)
                            existing = existing ? existing * multiplier : multiplier
                            dividerAndMultiplier.put(MULTIPLIER, existing)
                        }
                    }
                }
            }
        }
        return dividerAndMultiplier
    }

    List<String> getWidgetValueFromEntity(ValueReference valueReference, Entity entity) {
        List<String> value = []
        List<Dataset> datasets = getDatasetsForEntity(valueReference, entity)

        if (datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset dataset in datasets) {
                if (!Constants.NO.equalsIgnoreCase(dataset.additionalQuestionAnswers?.get(Constants.PUBLISH_FOR_EXPORT_QUESTIONID) as String)) {
                    if (dataset.resourceId) {
                        value.add(optimiResourceService.getLocalizedName(resourceCache.getResource(dataset)))
                    } else {
                        value.add(dataset.answerIds[0])
                    }
                }
            }
        }
        return value
    }

    List<Dataset> getDatasetsForEntity(ValueReference valueReference, Entity entity, Boolean failover = true) {
        if (!entity || !valueReference) {
            return []
        }

        List<Dataset> datasets

        datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, valueReference.queryId, valueReference.sectionId, valueReference.questionId)

        if (!datasets && failover && entity.parentEntityId) {
            Entity parent = entity.getParentById()
            datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(parent, valueReference.queryId, valueReference.sectionId, valueReference.questionId)
        }

        if (datasets && !datasets.isEmpty()) {
            if (valueReference.additionalQuestionId && !Constants.QUANTITY_QUESTIONID.equals(valueReference.additionalQuestionId)) {
                datasets = datasets.findAll({
                    it.additionalQuestionAnswers?.keySet()?.contains(valueReference.additionalQuestionId)
                })
            }

            if (valueReference.resourceId) {
                datasets = datasets?.findAll({ valueReference.resourceId.equals(it.resourceId) })
            }
        }
        return datasets
    }

    EntityFile getFileForEntity(ValueReference valueReference, Entity entity) {
        EntityFile entityFile

        if (entity && valueReference && valueReference.queryId && valueReference.sectionId && valueReference.questionId) {
            entityFile = entity.getFiles()?.find({
                valueReference.queryId.equals(it.queryId) && valueReference.questionId.equals(it.questionId) &&
                        valueReference.sectionId.equals(it.sectionId)
            })
        }
        return entityFile
    }

    String getValueForEntity(ValueReference valueReference, Entity entity, Boolean isDisplay,
                             Boolean disableFormatting = Boolean.FALSE, ResourceCache resourceCache = null) {
        if (!entity || !valueReference) {
            return null
        }

        String value

        if (valueReference.indicatorId && valueReference.calculationRule) {
            Indicator tempIndicator = indicatorService.getIndicatorByIndicatorId(valueReference.indicatorId)
            if (tempIndicator.showAverageInsteadOfTotal) {
                value = entity.getAverageInsteadOfTotal(valueReference.indicatorId, valueReference.calculationRule)?.toString()
            } else {
                value = entity.getTotalResult(valueReference.indicatorId, valueReference.calculationRule)?.toString()
            }
        } else {
            List<Dataset> datasets = getDatasetsForEntity(valueReference, entity)

            if (datasets) {
                for (Dataset dataset in datasets) {
                    Resource resource = resourceCache ? resourceCache.getResource(dataset) : datasetService.getResource(dataset)
                    if (valueReference.additionalQuestionId && !valueReference.additionalQuestionId.equals(Constants.QUANTITY_QUESTIONID)) {
                        value = dataset.additionalQuestionAnswers?.get(valueReference.additionalQuestionId)
                    } else if (dataset.resourceId && valueReference.resourceParameter) {
                        if (resource) {
                            Object resourceParameterValue = DomainObjectUtil.callGetterByAttributeName(valueReference.resourceParameter, resource)

                            if (resourceParameterValue) {
                                value = resourceParameterValue.toString()
                            }

                        }
                    } else if (dataset.resourceId && !valueReference.additionalQuestionId) {
                        value = optimiResourceService.getLocalizedName(resource)
                    } else {
                        value = dataset.answerIds[0]
                    }
                }
            }
        }

        if (value != null && DomainObjectUtil.isNumericValue(value) && !disableFormatting) {
            Double valueAsNumber = applyMultiplierDivider(DomainObjectUtil.convertStringToDouble(value), entity, valueReference)

            if (valueAsNumber != null && valueReference.indicatorId) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(valueReference.indicatorId, true)
                Denominator formattingDenominator = indicator.getDenominatorListAsDenominatorObjects()?.find({
                    valueReference.denominatorIds?.contains(it.denominatorId) && it.resultFormatting
                })
                value = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, formattingDenominator, valueAsNumber, null, isDisplay, Boolean.FALSE, Boolean.TRUE, "")
            } else {
                value = valueAsNumber?.toString()
            }
        }
        return value
    }

    /*
    getDoubleValueForEntity try to find value for entity. If valueForUniqueSearch && calculateForUniqueAnswersOnly then
     only collect value of dataset that has addQId with matching value
    @params entity
    @params calculateForUniqueAnswersOnly
    @params valueForUniqueSearch
     */

    Double getDoubleValueForEntity(ValueReference valueReference, Entity entity, String calculateForUniqueAnswersOnly = null,
                                   String valueForUniqueSearch = null, ResourceCache resourceCache = null) {
        if (!entity || !valueReference) {
            return null
        }

        Double value

        if (valueReference.indicatorId && valueReference.calculationRule) {
            value = entity.getTotalResult(valueReference.indicatorId, valueReference.calculationRule)
        } else {
            List<Dataset> datasets = getDatasetsForEntity(valueReference, entity)

            if (datasets) {
                for (Dataset dataset in datasets) {
                    if (calculateForUniqueAnswersOnly && valueForUniqueSearch) {
                        if (dataset.additionalQuestionAnswers?.get(calculateForUniqueAnswersOnly)?.equals(valueForUniqueSearch)) {
                            value = resolveDoubleValue(valueReference, dataset, entity, value, resourceCache)
                        }
                    } else {
                        value = resolveDoubleValue(valueReference, dataset, entity, value, resourceCache)
                    }
                }
            }
        }

        if (value != null) {
            value = applyMultiplierDivider(value, entity, valueReference)
        }
        return value
    }

    private Double applyMultiplierDivider(Double value, Entity entity, ValueReference valueReference) {
        Map<String, Double> dividerAndMultiplier = getDividerAndMultiplier(valueReference, entity)

        if (dividerAndMultiplier) {
            Double divider = dividerAndMultiplier.get(DIVIDER)
            Double multiplier = dividerAndMultiplier.get(MULTIPLIER)

            if (divider) {
                value /= divider
            }

            if (multiplier) {
                value *= multiplier
            }
        }
        return value
    }

    private Double resolveDoubleValue(ValueReference valueReference, Dataset dataset, Entity entity, Double value, ResourceCache resourceCache) {
        if (valueReference?.resourceParameter) {
            String resourceIdFromAdditionalAnswer = valueReference.additionalQuestionId ? dataset?.additionalQuestionAnswers?.get(valueReference.additionalQuestionId)?.toString() ?: '' : ''
            Resource sourceResource = resourceIdFromAdditionalAnswer ? optimiResourceService.getResourceWithParams(resourceIdFromAdditionalAnswer, null, true) : resourceCache ? resourceCache.getResource(dataset) : datasetService.getResource(dataset)
            Object resourceParameterValue = sourceResource ? DomainObjectUtil.callGetterByAttributeName(valueReference.resourceParameter, sourceResource) : null

            if (resourceParameterValue && DomainObjectUtil.isNumericValue(resourceParameterValue.toString())) {
                Double doubleResourceParameterValue = DomainObjectUtil.convertStringToDouble(resourceParameterValue.toString())
                value = value != null ? (value + doubleResourceParameterValue) : doubleResourceParameterValue
            }
        } else if (valueReference?.multipliersWithConditions && dataset?.resourceId) {
            Resource r = resourceCache ? resourceCache.getResource(dataset) : datasetService.getResource(dataset)

            if (r) {
                conditionalMultiplierLoop:
                for (MultiplierWithConditions conditionalMultiplier in valueReference.multipliersWithConditions) {
                    if (conditionalMultiplier.resourceAttribute) {
                        Object val = DomainObjectUtil.callGetterByAttributeName(conditionalMultiplier.resourceAttribute, r)

                        if (val != null && DomainObjectUtil.isNumericValue(val.toString()) && conditionalMultiplier.conditions) {
                            boolean conditionsPassed = true

                            for (ValueReference valRef in conditionalMultiplier.conditions) {
                                if (valRef.answerId || valRef.answerIds) {
                                    List<String> valRelAnswerList = valRef.answerIds ?: [valRef.answerId]
                                    List<Dataset> conditionalDatasets = getDatasetsForEntity(valRef, entity, false)
                                    if (!conditionalDatasets || (conditionalDatasets && !conditionalDatasets.find({ it.answerIds && !it.answerIds.isEmpty() && valRelAnswerList.contains(it.answerIds[0]?.toString()) }))) {
                                        conditionsPassed = false
                                        break
                                    }
                                }
                            }

                            if (conditionsPassed) {
                                Double doubleValue = DomainObjectUtil.convertStringToDouble(val.toString())
                                value = value != null ? (value + doubleValue) : doubleValue
                                break conditionalMultiplierLoop
                            }
                        }
                    }
                }
            }
        } else if (valueReference?.additionalQuestionId && dataset?.additionalQuestionAnswers?.get(valueReference.additionalQuestionId)) {
            Double additionalQuestionValue = DomainObjectUtil.convertStringToDouble(dataset.additionalQuestionAnswers.get(valueReference.additionalQuestionId).toString())
            value = value != null ? (value + additionalQuestionValue) : additionalQuestionValue
        } else if (dataset.quantity != null) {
            value = value != null ? (value + dataset.quantity) : dataset.quantity
        }

        return value
    }

    Boolean resolveIfShown(ValueReference valueReference, Entity entity) {
        if (valueReference?.answerId) {
            String value = getValueForEntity(entity, Boolean.FALSE)
            Boolean show = Boolean.FALSE

            if (value && value.equals(valueReference.answerId)) {
                show = Boolean.TRUE
            }
            return show
        } else {
            return Boolean.TRUE
        }
    }
}
