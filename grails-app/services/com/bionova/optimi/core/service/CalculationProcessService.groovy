package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CalculationProcess
import grails.gorm.transactions.Transactional

@Transactional
class CalculationProcessService {

    Boolean isCalculationRunning(String entityId, String indicatorId){
        return CalculationProcess.withCriteria{
            or{
                eq("entityId", entityId)
                eq("parentEntityId", entityId)
            }

            if(indicatorId){
                eq("indicatorId", indicatorId)
            }

            eq("status", CalculationProcess.CalculationStatus.IN_PROGRESS.name)
        } ? Boolean.TRUE : Boolean.FALSE
    }

    List<CalculationProcess> getCalculatedProcessesForParentEntity(String parentEntityId){
        return CalculationProcess.withCriteria{
            eq("parentEntityId", parentEntityId)
            eq("status", CalculationProcess.CalculationStatus.IN_PROGRESS.name)
        }
    }

    void saveCalculationProcess(CalculationProcess calculationProcess){
        calculationProcess?.save(flush: true)
    }

    void deleteCalculationProcess(CalculationProcess calculationProcess){
        if(calculationProcess) {
            deleteCalculationProcess(calculationProcess.entityId, calculationProcess.parentEntityId, calculationProcess.indicatorId)
        }
    }

    void deleteCalculationProcess(String entityId, String parentEntityId, String indicatorId){
        CalculationProcess.collection.deleteMany([entityId      : entityId,
                                                  parentEntityId: parentEntityId,
                                                  indicatorId   : indicatorId])
    }

    void deleteByIdCalculationProcess(CalculationProcess calculationProcess){
        CalculationProcess.collection.deleteOne([id: calculationProcess?.id])
    }

    CalculationProcess createCalculationProcess(String entityId, String parentEntityId, String indicatorId){
        CalculationProcess calculationProcess = null

        if (entityId){
            calculationProcess =  new CalculationProcess(
                    entityId: entityId,
                    parentEntityId: parentEntityId,
                    indicatorId: indicatorId,
                    status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                    startTime: new Date(),
            )
        }

        return calculationProcess
    }

    CalculationProcess createCalculationProcessAndSave(String entityId, String parentEntityId, String indicatorId){
        CalculationProcess calculationProcess = createCalculationProcess(entityId, parentEntityId, indicatorId)
        saveCalculationProcess(calculationProcess)

        return calculationProcess
    }

    CalculationProcess findCalculationProcessByEntityIdAndIndicatorIdAndStatus(String entityId, String indicatorId, String status){
        List<CalculationProcess> calculationProcessesList = CalculationProcess.withCriteria{
            eq("entityId", entityId)
            eq("indicatorId", indicatorId)
            eq("status", status)
        }

        return calculationProcessesList ? calculationProcessesList.first() : null
    }

    CalculationProcess findCalculationProcessByEntityIdAndIndicatorId(String entityId, String indicatorId){
        List<CalculationProcess> calculationProcessesList = CalculationProcess.withCriteria{
            eq("entityId", entityId)
            eq("indicatorId", indicatorId)
        }

        return calculationProcessesList ? calculationProcessesList.first() : null
    }

    CalculationProcess findCalculationProcessById(Long id){
        return CalculationProcess.get(id)
    }

    void setErrorStatus(CalculationProcess calculationProcess){
        if(calculationProcess) {
            calculationProcess.status = CalculationProcess.CalculationStatus.ERROR.name
            calculationProcess.finishTime = new Date()
            calculationProcess.save(flush: true)
        }
    }
}
