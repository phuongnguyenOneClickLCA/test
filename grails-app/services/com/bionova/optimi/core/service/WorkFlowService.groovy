package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlow
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.domain.mongo.WorkFlowStep
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil


class WorkFlowService {

    QueryService queryService
    EntityService entityService
    LicenseService licenseService
    WorkFlowStepService workFlowStepService
    WorkFlowForEntityService workFlowForEntityService
    LoggerUtil loggerUtil
    FlashService flashService

    FrameStatus loadFrame(User user, List<HelpConfiguration> helpConfigurations, ChannelFeature channelFeature, WorkFlowForEntity worflowForEntity = null, Boolean resultBarLicensed = Boolean.FALSE) {

        FrameStatus frameStatus

        String userId = user?.id?.toString()
        if (userId) {
            frameStatus = getFrame(userId)

            if (frameStatus){
                Boolean showHelp = helpConfigurations && user?.enableFloatingHelp && channelFeature?.allowFloatingHelp
                Boolean showWorkFlow = worflowForEntity

                List<String> tabList = []
                if (showWorkFlow) {
                    tabList.add("WORKFLOW")
                }
                if (showHelp) {
                    tabList.add("HELP")
                }

                if(resultBarLicensed) {
                    tabList.add("RESULTBAR")
                }

                if (frameStatus.selectedTab && tabList) {
                    if (!tabList.contains(frameStatus.selectedTab)) {
                        frameStatus.selectedTab = tabList.first()
                    }
                } else {
                    frameStatus.selectedTab = ""
                }
            }
        }
        return frameStatus
    }

    FrameStatus getFrame(String userId) {

        FrameStatus frame = FrameStatus.findByUserId(userId)

        if(!frame){
            frame = new FrameStatus(userId: userId)
        }

        return frame
    }


    WorkFlowForEntity getWorkFlowForEntity(Entity entity, List<Indicator> indicatorList, List<License> entityValidLicenses){

        WorkFlowForEntity workFlowForEntity
        WorkFlowForEntity old
        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

        if (entityService.isWorkFlowLicensed(featuresAvailableForEntity)) {

            old = WorkFlowForEntity.findByEntityId(entity?.id?.toString())

            Query queryWorkFlow = queryService.getQueryByQueryId("workFlowQueryId")

            List<WorkFlow> allWorkFlowList = queryWorkFlow?.workFlowList
            List<String> selectedIndicatorIds = indicatorList.collect({it.indicatorId})
            List<WorkFlow> workFlowsForThisEntity = []

            List<String> validWorkFlowIdsForEntity = entity.validWorkFlowIds
            List<String> licensedIndicatorIds = []
            if(entityValidLicenses){
                licensedIndicatorIds = entityValidLicenses?.collect({it.licensedIndicatorIds})?.flatten()
            }
            if (validWorkFlowIdsForEntity) {
                if(indicatorList){
                    allWorkFlowList = allWorkFlowList.findAll({ (validWorkFlowIdsForEntity.contains(it.workFlowId) && selectedIndicatorIds?.intersect(it.indicatorIdList))})
                } else {
                    allWorkFlowList = allWorkFlowList.findAll({ licensedIndicatorIds.intersect(it.indicatorIdList) && it.workFlowType?.equalsIgnoreCase("onBoarding") && validWorkFlowIdsForEntity.contains(it.workFlowId)})

                }
            }


            if (allWorkFlowList) {

                String entityId = entity?.id?.toString() ?: ""

                if(entityId && allWorkFlowList){
                    workFlowForEntity = workFlowForEntityService.build(entityId, allWorkFlowList, old, new WorkFlowForEntity())
                }
            }

            if (workFlowForEntity) {

                workFlowForEntity = saveWorkFlowForEntity(workFlowForEntity)
                workFlowForEntity = initLocalizedSteps(workFlowForEntity, queryWorkFlow)
            }
        }

        return workFlowForEntity
    }

    WorkFlowForEntity initLocalizedSteps(WorkFlowForEntity workFlowForEntity, Query queryWorkFlows) {
        if (workFlowForEntity && queryWorkFlows) {
            List<WorkFlow> allWorkFlowList = queryWorkFlows?.workFlowList
            if (allWorkFlowList) {
                workFlowForEntity.workFlowList?.each { WorkFlow workFlow ->
                    WorkFlow queryWorkFlow = allWorkFlowList.find({it.workFlowId == workFlow.workFlowId})

                    if (queryWorkFlow) {
                        workFlow.stepList?.each { WorkFlowStep workFlowStep ->
                            WorkFlowStep queryWorkFlowStep = queryWorkFlow.stepList?.find({it.stepId == workFlowStep.stepId})

                            if (queryWorkFlowStep) {
                                workFlowStep.localizedName = workFlowStepService.getLocName(queryWorkFlowStep.name)
                                workFlowStep.localizedHelp = workFlowStepService.getLocHelp(queryWorkFlowStep.help)
                            }
                        }
                    }
                }
            }
        }
        return workFlowForEntity
    }

    WorkFlowForEntity loadWorkFlowForEntity(Entity entity, String defaultIndicatorId) {

        WorkFlowForEntity workFlowForEntity

        String entityId = entity?.id?.toString()
        if (entityId) {
            workFlowForEntity = WorkFlowForEntity.findByEntityId(entityId)
            if (workFlowForEntity) {
                Query queryWorkFlows = queryService.getQueryByQueryId("workFlowQueryId")
                workFlowForEntity = initLocalizedSteps(workFlowForEntity, queryWorkFlows)
            }
/*
            if(workFlowForEntity && workFlowForEntity.indicatorIsPresent(defaultIndicatorId)){
                workFlowForEntity.defaultIndicatorId = defaultIndicatorId
                workFlowForEntity = saveWorkFlowForEntity(workFlowForEntity)
            } else{
                workFlowForEntity = null
            }*/
        }

        return workFlowForEntity
    }

    private WorkFlowForEntity saveWorkFlowForEntity(WorkFlowForEntity workFlowForEntity) {
        WorkFlowForEntity wf
        try {
            if (workFlowForEntity) {
                // Kikkare, needs to persist in query workFlow object but not for every entity
                workFlowForEntity.workFlowList?.each { workFlowList ->
                    workFlowList?.stepList?.each {
                        it.name = null
                        it.help = null
                        it.localizedName = null
                        it.localizedHelp = null
                    }
                }

                if (workFlowForEntity.id) {
                    wf = workFlowForEntity.merge(flush: true, failOnError: true)
                } else {
                    wf = workFlowForEntity.save(flush: true, failOnError: true)
                }
            }
        } catch(Exception e) {
            loggerUtil.error(log, "Failed to save WorkFlow for entity:", e)
            flashService.setErrorAlert("Failed to save WorkFlow for entity: ${e.message}", true)
        }
        return wf
    }

    void update(WorkFlow old, WorkFlow workFlow){

        Map oldValueMap = old?.stepList?.collectEntries{[(it.stepId) : [checked: it.checked, isExpanded: it.isExpanded, note: it.note]]}

        if(oldValueMap && workFlow){
            workFlow.stepList?.each {
                def oldValue = oldValueMap[it.stepId]
                it.checked = oldValue?.checked ?: false
                it.isExpanded = oldValue?.isExpanded ?: false
                it.note = oldValue?.note ?: ""

            }

        }

    }

    String getLocName(Map <String, String> name) {

        if (name) {
            String locName = name.get(DomainObjectUtil.getMapKeyLanguage())
            locName = locName ? locName : name.get("EN")

            return locName ? locName : "NAME LOCALIZATION MISSING"
        } else {
            return ""
        }
    }

}
