package com.bionova.optimi.construction.controller


import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.SimulationToolConstruction
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyType
import com.bionova.optimi.core.domain.mongo.SimulationToolSession
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.converters.JSON

class SimulationToolController {
    def simulationToolService
    def indicatorService
    def entityService
    def newCalculationServiceProxy
    def flashService


    def updateConstructionCost() {
        SimulationToolConstruction simConstruction
        String sessionId = request.JSON?.sessionId
        String jsonConstruction = request.JSON?.jsonConstruction
        String userGivenCost = request.JSON?.userGivenCost

        SimulationToolSession simulationSession = (SimulationToolSession) session?.getAttribute(sessionId)

        if (!userGivenCost||(userGivenCost && !userGivenCost.replaceAll(",", ".").isNumber())) {
            userGivenCost = "0"
        }

        if (simulationSession && userGivenCost && DomainObjectUtil.isNumericValue(userGivenCost)) {
            Indicator lccIndicator = indicatorService.getIndicatorByIndicatorId(simulationSession.lccIndicatorId, true)
            Entity design = entityService.getEntityById(simulationSession.designId)
            Entity parentEntity = design?.parentById

            Double userGivenCostDouble = DomainObjectUtil.convertStringToDouble(userGivenCost)
            Entity tempEntity = simulationSession?.tempEntity

            if (jsonConstruction && tempEntity) {
                simConstruction = JSON.parse(jsonConstruction) as SimulationToolConstruction

                if (simConstruction){
                    Entity updatedTempEntity = simulationToolService.addCostToConstruction(tempEntity, simConstruction, userGivenCostDouble)

                    if (updatedTempEntity) {
                        Entity tempForCalc = new Entity()
                        tempForCalc.defaults = updatedTempEntity.defaults
                        tempForCalc.datasets = updatedTempEntity.datasets?.findAll({it.projectLevelDataset})
                        Dataset constructionDataset = updatedTempEntity.datasets?.find({
                            !it.parentConstructionId && it.uniqueConstructionIdentifier?.endsWith(simConstruction.oid)
                        })

                        if (constructionDataset) {
                            tempForCalc.datasets.add(constructionDataset)

                            if (lccIndicator) {
                                Dataset assessment

                                if (simulationSession.assessmentPeriod && lccIndicator.assessmentPeriodValueReference) {
                                    assessment = simulationToolService.createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, simulationSession.assessmentPeriod)

                                    if (assessment) {
                                        tempForCalc.datasets.add(assessment)
                                    }
                                }

                                // Calculate lcc results
                                tempForCalc = newCalculationServiceProxy.calculate(null, lccIndicator.indicatorId, null, tempForCalc, Boolean.TRUE, [lccIndicator.displayResult])
                                simConstruction = simulationToolService.getCost(tempForCalc, lccIndicator, simConstruction, parentEntity)
                            }
                        }
                        simConstruction.userGivenCost = userGivenCostDouble
                        simulationSession.tempEntity = updatedTempEntity
                    }
                }
                session?.setAttribute(sessionId, simulationSession)
            }
        }
        render([simConstruction: simConstruction, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def updateEnergyCost() {
        Double calculatedCost = 0
        String sessionId = request.JSON?.sessionId
        String userGivenCost = request.JSON?.userGivenCost
        String jsonEnergyType = request.JSON?.jsonEnergyType
        String purchaseKwh = request.JSON?.purchaseKwh
        boolean isUserGivenCostANumber = DomainObjectUtil.isNumericValue(userGivenCost)
        boolean isPurchaseKwhANumber = DomainObjectUtil.isNumericValue(purchaseKwh)

        SimulationToolSession simulationSession = (SimulationToolSession) session?.getAttribute(sessionId)


        if (!userGivenCost||(userGivenCost && !isUserGivenCostANumber)) {
            userGivenCost = "0"
        }

        if (simulationSession && userGivenCost && isUserGivenCostANumber && purchaseKwh && isPurchaseKwhANumber) {
            Indicator lccIndicator = indicatorService.getIndicatorByIndicatorId(simulationSession.lccIndicatorId, true)
            Entity design = entityService.getEntityById(simulationSession.designId)
            Double userGivenCostDouble = DomainObjectUtil.convertStringToDouble(userGivenCost)
            Double purchaseKwhDouble = DomainObjectUtil.convertStringToDouble(purchaseKwh)
            Entity tempEntity = simulationSession?.tempEntity

            if (jsonEnergyType && tempEntity) {
                SimulationToolEnergyType energyType = JSON.parse(jsonEnergyType) as SimulationToolEnergyType

                if (energyType && energyType.manualId && tempEntity) {
                    Dataset dataset = tempEntity.datasets?.find({it.manualId == energyType.manualId})

                    if (dataset) {
                        dataset.userSetCost = true
                        dataset.userSetTotalCost = true

                        if (dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers.put("costPerUnit", userGivenCostDouble)
                            dataset.additionalQuestionAnswers.put("totalCost", userGivenCostDouble * purchaseKwhDouble)
                        } else {
                            dataset.additionalQuestionAnswers = [costPerUnit: userGivenCostDouble, totalCost: userGivenCostDouble * purchaseKwhDouble]
                        }

                        Entity tempForCalc = new Entity()
                        tempForCalc.defaults = tempEntity.defaults
                        tempForCalc.datasets = tempEntity.datasets?.findAll({it.projectLevelDataset})
                        tempForCalc.datasets.add(dataset)

                        if (lccIndicator) {
                            Dataset assessment

                            if (simulationSession.assessmentPeriod && lccIndicator.assessmentPeriodValueReference) {
                                assessment = simulationToolService.createAssessmentDataset(lccIndicator.assessmentPeriodValueReference, simulationSession.assessmentPeriod)

                                if (assessment) {
                                    tempForCalc.datasets.add(assessment)
                                }
                            }
                            // Calculate lcc results
                            tempForCalc = newCalculationServiceProxy.calculate(null, lccIndicator.indicatorId, null, tempForCalc, Boolean.TRUE, [lccIndicator.displayResult])
                            String lccDisplayRuleId = lccIndicator.displayResult
                            List<ResultCategory> lccResultCategories = lccIndicator.getResolveResultCategories(design?.parentEntityId ? design.parentById : design)
                            List<CalculationResult> resultsForLCCIndicator = tempForCalc.tempCalculationResults?.findAll({
                                it.calculationRuleId.equals(lccDisplayRuleId)
                            })

                            lccResultCategories?.findAll({!it.virtual})?.each {
                                Double resultForCategory = tempEntity.getResultForDataset(dataset.manualId, lccIndicator.indicatorId, it.resultCategoryId, lccDisplayRuleId, resultsForLCCIndicator)

                                if (resultForCategory) {
                                    calculatedCost = calculatedCost + resultForCategory
                                }
                            }
                        }
                        simulationSession.tempEntity = tempEntity
                    }
                }
                session?.setAttribute(sessionId, simulationSession)
            }
        }
        render([calculatedCost: calculatedCost, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}
