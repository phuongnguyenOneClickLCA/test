package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.service.IndicatorReportService

/**
 * @author Pasi-Markus Mäkelä
 */
class ResultController extends ExceptionHandlerController {
    def indicatorService
    def entityService
    def optimiResourceService
    IndicatorReportService indicatorReportService

    def addDatasetsToResources() {
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String entityClass = params.entityClass
        String indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        if (childEntityId && indicator) {
            Entity entity = entityService.readEntity(childEntityId)
            optimiResourceService.mapUserAnswersToResource(indicator, entity, session)
            List<String> componentSavingOk = session?.getAttribute("componentSaving.ok")
            String componentSavingError = session?.getAttribute("componentSaving.error")
            String componentSavingMessage

            if (componentSavingOk) {
                componentSavingMessage = message(code: componentSavingOk.get(0), args: [componentSavingOk.get(1)])
                flash.fadeSuccessAlert = componentSavingMessage
                session?.removeAttribute("componentSaving.ok")
            }

            if (componentSavingError) {
                flash.fadeErrorAlert = message(code: "resource.save.error", args: [componentSavingError])
                session?.removeAttribute("componentSaving.error")
            }
        }

        if ("design".equals(entityClass)) {
            redirect controller: "design", action: "results", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId]
        } else {
            redirect controller: "operatingPeriod", action: "results", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId]
        }
    }

    def categoryDetails() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String resultCategoryId = params.resultCategoryId
        String reportItemId = params.reportItemId
        Indicator indicator
        ResultCategory resultCategory
        Entity entity
        Entity parentEntity
        List<CalculationRule> calculationRules

        if (entityId && indicatorId && resultCategoryId) {
            Date start = new Date()
            entity = entityService.getEntityWithProjection(entityId, [calculationResults: 0])
            if(entity?.parentEntityId){
                parentEntity = entityService.getEntityById(entity.parentEntityId)
            }
            Date end = new Date()
            log.info("Time for getting entity: ${end.time - start.time}")
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            resultCategory = indicator?.getResolveResultCategories(parentEntity)?.find({resultCategoryId.equals(it.resultCategoryId)})
            IndicatorReportItem reportItem

            if (reportItemId) {
                reportItem = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator.report?.reportItems)?.
                        find({ IndicatorReportItem report -> reportItemId.equals(report.reportItemId) })
            } else {
                reportItem = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, indicator.report?.reportItems)?.find({ IndicatorReportItem report ->
                    "default".equals(report?.tableSource) ||
                            "normalTable".equals(report?.tableSource)
                })
            }

            if (reportItem?.rules) {
                List<CalculationRule> indicatorRules = indicator.getResolveCalculationRules(parentEntity)

                if (indicatorRules) {
                    calculationRules = indicatorRules.findAll({ reportItem.rules.contains(it.calculationRuleId) })?.sort({it -> reportItem?.rules.indexOf(it.calculationRuleId)})

                    if (!calculationRules) {
                        calculationRules = indicatorRules.findAll({ reportItem.rules.contains(it.calculationRule) })?.sort({it -> reportItem?.rules.indexOf(it.calculationRule)})
                    }

                }
            } else {
                calculationRules = indicator.getResolveCalculationRules(parentEntity)
            }
        }
        [indicator: indicator, resultCategory: resultCategory, entity: entity, calculationRules: calculationRules, parentEntity:parentEntity]
    }
}
