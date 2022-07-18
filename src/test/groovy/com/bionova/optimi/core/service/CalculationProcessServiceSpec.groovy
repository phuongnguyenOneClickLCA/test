package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CalculationProcess
import grails.testing.services.ServiceUnitTest
import grails.testing.gorm.DataTest
import spock.lang.Specification

class CalculationProcessServiceSpec extends Specification implements DataTest, ServiceUnitTest<CalculationProcessService>{

    def "check that calculation already running."() {
        String entityId = 'dsf34235rterh4353'
        String parentEntityId = 'ljhgsa123ij4oi132h4'
        String indicatorId = 'BuildingLifecycleAssessment_LEED_International'

        mockDomain(CalculationProcess, [service.createCalculationProcess(entityId, parentEntityId, indicatorId)])

        when:
        Boolean isCalculationProcessRunning = service.isCalculationRunning(entityId, indicatorId)

        then: 'we check if calculation running for the same child entity and indicator?'
        isCalculationProcessRunning == Boolean.TRUE

        when:
        Boolean isCalculationProcessCanBeRunForParent = service.isCalculationRunning(parentEntityId, null)

        then: 'we check if calculation running for any child of parent entity'
        isCalculationProcessCanBeRunForParent == Boolean.TRUE
    }

    def "check search by entityId, indicatorId and status"() {
        mockDomain(CalculationProcess, [
                new CalculationProcess(
                        entityId: 'dsf34235rterh4351',
                        parentEntityId: 'dsfsdf4564v4564v456',
                        indicatorId: 'BuildingLifecycleAssessment_LEED_International',
                        status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                ),
                new CalculationProcess(
                        entityId: 'dsfsdf4564v4564v456',
                        status: CalculationProcess.CalculationStatus.ERROR.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                ),
                new CalculationProcess(
                        entityId: 'dsf34235rterh4353',
                        parentEntityId: 'dsfsdf4564v4564v456',
                        indicatorId: 'BuildingLifecycleAssessment_LEED_International',
                        status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                )
        ])

        when:
        CalculationProcess calculationProcess1 = service.findCalculationProcessByEntityIdAndIndicatorIdAndStatus('dsf34235rterh4353',
                'BuildingLifecycleAssessment_LEED_International', CalculationProcess.CalculationStatus.IN_PROGRESS.name)

        then:
        calculationProcess1 != null

        when:
        Boolean parentCalculationProcess = service.findCalculationProcessByEntityIdAndIndicatorId('dsfsdf4564v4564v456', null)

        then:
        parentCalculationProcess != null

        when:
        CalculationProcess nonExistentCalculationProcess = service.findCalculationProcessByEntityIdAndIndicatorIdAndStatus('dsfsdf4564v4564v456',
                'BuildingLifecycleAssessment_LEED_International', CalculationProcess.CalculationStatus.ERROR.name)

        then:
        nonExistentCalculationProcess == null
    }

    def "check search entityIds by parent"() {
        mockDomain(CalculationProcess, [
                new CalculationProcess(
                        entityId: 'dsf34235rterh4351',
                        parentEntityId: 'dsfsdf4564v4564v456',
                        indicatorId: 'BuildingLifecycleAssessment_LEED_International',
                        status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                ),
                new CalculationProcess(
                        entityId: 'dsfsdf4564v4564v456',
                        status: CalculationProcess.CalculationStatus.ERROR.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                ),
                new CalculationProcess(
                        entityId: 'dsf34235rterh4353',
                        parentEntityId: 'dsfsdf4564v4564v456',
                        indicatorId: 'BuildingLifecycleAssessment_LEED_International',
                        status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                        startTime: new Date(),
                        finishTime: new Date(),
                )
        ])

        when:
        List<CalculationProcess> childEntityIdsInCalculation = service.getCalculatedProcessesForParentEntity('dsfsdf4564v4564v456')

        then:
        childEntityIdsInCalculation != null
        childEntityIdsInCalculation.size() == 2
        childEntityIdsInCalculation.entityId == ['dsf34235rterh4351','dsf34235rterh4353']

        when:
        List<CalculationProcess> childEntityIdsInCalculation2 = service.getCalculatedProcessesForParentEntity('dsfsdf4564v4')

        then:
        childEntityIdsInCalculation2.isEmpty()
        childEntityIdsInCalculation2.size() == 0
    }
}
