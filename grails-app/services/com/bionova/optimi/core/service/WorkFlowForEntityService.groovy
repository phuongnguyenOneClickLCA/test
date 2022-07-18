package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.WorkFlow
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.domain.mongo.WorkFlowStep
import grails.gorm.transactions.Transactional

@Transactional
class WorkFlowForEntityService {

    WorkFlowService workFlowService

    WorkFlowForEntity build(String entityId, List<WorkFlow> allowedWorkFlow, WorkFlowForEntity old, WorkFlowForEntity entity) {

        if (entity) {
            //IF it's null?????
            entity.entityId = entityId

            entity.workFlowList = allowedWorkFlow ? initializeWorkFlowList(allowedWorkFlow) : []

            updateWorkFlowList(old, entity)

            entity.defaultWorkFlowId = getDefaultWorkFlow(old, entity)

            if(old){
                old.properties = entity.properties
                return old
            }
        }
        return entity
    }

    List<WorkFlow> initializeWorkFlowList(List<WorkFlow> workFlowList){

        List<WorkFlow> newList = []

        workFlowList?.each{
            if(it){
                WorkFlow w = new WorkFlow()
                w.workFlowId = it.workFlowId
                w.indicatorIdList = it.indicatorIdList
                w.workFlowType = it.workFlowType
                w.name = it.name
                w.stepList = initializeStepList(it.stepList)
                w.localizedName = it.locName
                newList.add(w)
            }
        }

        return newList

    }

    List<WorkFlowStep> initializeStepList(List<WorkFlowStep> stepList){

        List<WorkFlowStep> newList = []

        stepList?.each{
            if(it){
                WorkFlowStep s = new WorkFlowStep()
                s.stepId = it.stepId
                s.itemId = it.itemId
                s.actionToTake = it.actionToTake
                s.observe = it.observe
                s.toNextScreen = it.toNextScreen
                s.disableReset = it.disableReset
                newList.add(s)
            }
        }

        return newList

    }

    Boolean workFlowIsPresent(String workFlowId, WorkFlowForEntity entity){

        return entity?.workFlowList?.any{it.workFlowId == workFlowId}

    }

    String getDefaultWorkFlow(WorkFlowForEntity old, WorkFlowForEntity entity){

        String defaultWorkFlowId = ""

        if(entity?.workFlowList){
            defaultWorkFlowId = old?.defaultWorkFlowId
            if(!(defaultWorkFlowId && workFlowIsPresent(defaultWorkFlowId, entity))){
                defaultWorkFlowId = entity.workFlowList.first().workFlowId
            }
        }

        return defaultWorkFlowId

    }

    void updateWorkFlowList(WorkFlowForEntity old, WorkFlowForEntity entity) {

        if(old?.workFlowList){
            entity?.workFlowList?.each{ w ->
                WorkFlow oldWorkFlow = old?.workFlowList?.find{it.workFlowId == w.workFlowId}
                if(oldWorkFlow){workFlowService.update(oldWorkFlow, w)}
            }
        }
    }
}
