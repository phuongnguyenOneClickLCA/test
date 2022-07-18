package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCursor
import grails.plugin.springsecurity.SpringSecurityUtils
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
class MassRecalculationController extends ExceptionHandlerController {

    def indicatorService
    def entityService
    def newCalculationServiceProxy
    def licenseService

    def index() {
        if (SpringSecurityUtils.ifAnyGranted(Constants.ROLE_SYSTEM_ADMIN)) {
            Date currentDate = new Date()
            List<License> licenses = License.collection.find([validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.collect({ it as License })
            List<Indicator> indicator = indicatorService.getAllActiveIndicators()?.sort({ indicatorService.getLocalizedName(it).toLowerCase() })
            [indicators: indicator, licenses: licenses.sort({ it.name.toLowerCase() })]
        } else {
            redirect controller: "main", action: "list"
        }
    }

    def logMigration() {
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId

        if (queryId && sectionId && questionId) {
            queryId = queryId.trim()
            sectionId = sectionId.trim()
            questionId = questionId.trim()
            String result = "Found datasets by ${queryId}, ${sectionId}, ${questionId}: "

            MongoCursor results = Entity.collection.find(["datasets.queryId"   : queryId, "datasets.sectionId": sectionId,
                                                          "datasets.questionId": questionId], ["datasets": 1]).iterator()
            int totalAmount = 0

            if (results) {
                while (results.hasNext()) {
                    BasicDBList datasets = results.next().get("datasets")
                    int datasetAmount = datasets.findAll({ BasicDBObject dataset ->
                        queryId.equals(dataset.getString("queryId")) &&
                                sectionId.equals(dataset.getString("sectionId")) && questionId.equals(dataset.getString("questionId"))
                    }).size()
                    totalAmount = totalAmount + datasetAmount
                }
            }
            result = "${result}${totalAmount}"
            render(text: result)
        } else {
            render(text: "Missing required parameters queryId, sectionId or questionId")
        }
    }

    def getEntitiesByIndicator() {
        List<Entity> entities
        Indicator indicator
        boolean relcalculationNeeded = true
        boolean showForceRecalculation = true
        String indicatorId = params.indicatorId
        String licenseId = params.licenseId
        Boolean automaticRecalculation = params.boolean("automaticRecalculation")
        License license = licenseService.getLicenseById(licenseId)
        List<String> entitiesCalulated = (List<String>) session?.getAttribute("calculatedEntities")
        String calculationErrors = session?.getAttribute("calculationErrors")
        
        if (indicatorId) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (license) {
                entities = license.licensedEntities?.findAll({it.indicatorIds?.contains(indicatorId)})
            } else {
                entities = entityService.getEntitiesByIndicator(indicatorId)?.sort({ it.name.toLowerCase() })
            }

            if (entitiesCalulated && entities) {
                entities = entities.findAll({ !entitiesCalulated.contains(it.id.toString()) })
            }

            if ("design".equals(indicator.indicatorUse)) {
                entities = entities?.findAll({ it.hasDesigns })
            } else {
                entities = entities?.findAll({ it.hasOperatingPeriods })
            }
        }
        boolean noLocked = params.noLocked ? true : false
        chain(action: "index", model: [indicator: indicator, entities: entities, relcalculationNeeded: relcalculationNeeded, licenseId: licenseId,
                                       noLocked : noLocked, showForceRecalculation: showForceRecalculation, entitiesCalulated: entitiesCalulated,
                                       automaticRecalculation: automaticRecalculation, calculationErrors: calculationErrors])
    }

    private boolean resolveIfRecalculationNeeded(Indicator indicator, List<Entity> entities) {
        boolean needed = false

        if (indicator && entities) {
            for (Entity entity in entities) {
                if ("design".equals(indicator.indicatorUse)) {
                    if (entity.hasDesigns) {
                        for (Entity design in entity.getDesigns()) {
                            if (!calc.areResultsValid(entity: design, indicator: indicator)) {
                                needed = true
                                break
                            }
                        }
                    }
                } else {
                    if (entity.hasOperatingPeriods) {
                        for (Entity period in entity.getOperatingPeriods()) {
                            if (!calc.areResultsValid(entity: period, indicator: indicator)) {
                                needed = true
                                break
                            }
                        }
                    }
                }

                if (needed) {
                    break
                }
            }
        }
        return needed
    }

    def relcalculate() {
        Boolean forced = params.forceRecalculateSelected ? Boolean.TRUE : Boolean.FALSE
        String indicatorId = params.indicatorId
        String licenseId = "undefined".equals(params.licenseId) || !params.licenseId ? null : params.licenseId
        Boolean automaticRecalculation = params.boolean("automaticRecalculation")
        List<String> entitiesToCalculate = params.list("entitiesToCalculate")
        boolean done = false
        List<String> entitiesAlreadyCalculated = (List<String>) session?.getAttribute("calculatedEntities")
        String calculationErrors = session?.getAttribute("calculationErrors")

        if (indicatorId && entitiesToCalculate) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator) {
                List<Entity> entities = Entity.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(entitiesToCalculate))

                if ("design".equals(indicator.indicatorUse)) {
                    entities = entities?.findAll({ it.hasDesigns })

                    entities?.each { Entity entity ->
                        entity = handleDatasetsWithNullManualId(entity)

                        if (params.noLocked) {
                            entity.getDesigns()?.findAll({ !it.locked && !it.superLocked })?.each { Entity design ->
                                if (automaticRecalculation || forced || !calc.areResultsValid(entity: design, indicator: indicator)) {
                                    design = handleDatasetsWithNullManualId(design)
                                    try {
                                        newCalculationServiceProxy.calculate(design.id, indicator.indicatorId, entity)
                                    } catch (Exception e) {
                                        if (calculationErrors) {
                                            calculationErrors = "${calculationErrors}MassRecalculationError: Unable to calculate project: ${entity.name}, design: ${design.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        } else {
                                            calculationErrors = "MassRecalculationError: Unable to calculate project: ${entity.name}, design: ${design.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        }
                                    }
                                }

                                if (!done) {
                                    done = true
                                }
                            }
                        } else {
                            entity.getDesigns()?.each { Entity design ->
                                if (automaticRecalculation || forced || !calc.areResultsValid(entity: design, indicator: indicator)) {
                                    design = handleDatasetsWithNullManualId(design)
                                    try {
                                        newCalculationServiceProxy.calculate(design.id, indicator.indicatorId, entity)
                                    } catch (Exception e) {
                                        if (calculationErrors) {
                                            calculationErrors = "${calculationErrors}MassRecalculationError: Unable to calculate project: ${entity.name}, design: ${design.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        } else {
                                            calculationErrors = "MassRecalculationError: Unable to calculate project: ${entity.name}, design: ${design.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        }
                                    }
                                }

                                if (!done) {
                                    done = true
                                }
                            }
                        }
                    }
                } else {
                    entities = entities?.findAll({ it.hasOperatingPeriods })

                    entities?.each { Entity entity ->
                        entity = handleDatasetsWithNullManualId(entity)

                        if (params.noLocked) {
                            entity.getOperatingPeriods()?.findAll({ !it.locked && !it.superLocked })?.each { Entity operatingPeriod ->
                                if (automaticRecalculation || forced || !calc.areResultsValid(entity: operatingPeriod, indicator: indicator)) {
                                    operatingPeriod = handleDatasetsWithNullManualId(operatingPeriod)
                                    try {
                                        newCalculationServiceProxy.calculate(operatingPeriod.id, indicator.indicatorId, entity)
                                    } catch (Exception e) {
                                        if (calculationErrors) {
                                            calculationErrors = "${calculationErrors}MassRecalculationError: Unable to calculate project: ${entity.name}, operatingPeriod: ${operatingPeriod.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        } else {
                                            calculationErrors = "MassRecalculationError: Unable to calculate project: ${entity.name}, operatingPeriod: ${operatingPeriod.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        }
                                    }
                                }

                                if (!done) {
                                    done = true
                                }
                            }
                        } else {
                            entity.getOperatingPeriods()?.each { Entity operatingPeriod ->
                                if (automaticRecalculation || forced || !calc.areResultsValid(entity: operatingPeriod, indicator: indicator)) {
                                    operatingPeriod = handleDatasetsWithNullManualId(operatingPeriod)
                                    try {
                                        newCalculationServiceProxy.calculate(operatingPeriod.id, indicator.indicatorId, entity)
                                    } catch (Exception e) {
                                        if (calculationErrors) {
                                            calculationErrors = "${calculationErrors}MassRecalculationError: Unable to calculate project: ${entity.name}, operatingPeriod: ${operatingPeriod.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        } else {
                                            calculationErrors = "MassRecalculationError: Unable to calculate project: ${entity.name}, operatingPeriod: ${operatingPeriod.operatingPeriodAndName}, ${e.getMessage()}<br />"
                                        }
                                    }
                                }

                                if (!done) {
                                    done = true
                                }
                            }

                        }

                    }
                }
            }
        }

        if (!done) {
            flash.fadeErrorAlert = "Error in relcalculation, check logs"
            entitiesToCalculate = null
        } else {
            if (entitiesAlreadyCalculated && entitiesToCalculate) {
                List<String> newList = []
                newList.addAll(entitiesToCalculate)
                newList.addAll(entitiesAlreadyCalculated)
                entitiesToCalculate = newList
            }
        }
        session?.setAttribute("calculatedEntities", entitiesToCalculate)
        session?.setAttribute("calculationErrors", calculationErrors)
        redirect(action: "getEntitiesByIndicator", params: [indicatorId: indicatorId, licenseId: licenseId, relcalculationDone: done,
                                                            automaticRecalculation: automaticRecalculation])
    }

    private Entity handleDatasetsWithNullManualId(Entity entity) {
        boolean mergeNeeded = false

        entity.datasets.each {
            if (!it.manualId) {
                mergeNeeded = true
                it.manualId = new ObjectId().toString()
            }
        }

        if (mergeNeeded) {
            return entity.merge(flush: true)
        } else {
            return entity
        }
    }
}
