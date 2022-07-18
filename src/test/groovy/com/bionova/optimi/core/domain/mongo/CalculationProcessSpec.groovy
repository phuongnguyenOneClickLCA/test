package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CalculationProcessSpec extends Specification implements DomainUnitTest<CalculationProcess> {

    void 'entityId cannot be null'() {
        when:
        domain.entityId = null

        then:
        !domain.validate(['entityId'])
        domain.errors['entityId'].code == 'nullable'
    }

    void 'status cannot be null'() {
        when:
        domain.status = null

        then:
        !domain.validate(['status'])
        domain.errors['status'].code == 'nullable'
    }

    void 'startTime cannot be null'() {
        when:
        domain.startTime = null

        then:
        !domain.validate(['startTime'])
        domain.errors['startTime'].code == 'nullable'
    }

    void 'entityId name cannot be blank'() {
        when:
        domain.entityId = ''

        then:
        !domain.validate(['entityId'])
    }


    void 'status name cannot be blank'() {
        when:
        domain.status = ''

        then:
        !domain.validate(['status'])
    }

    def "entityId and indicatorId  unique constraint"() {
        when: 'New unique calculationProcess'
        CalculationProcess calculationProcess = new CalculationProcess(
                entityId: 'dsf34235rterh4353',
                parentEntityId: 'ljhgsa123ij4oi132h4',
                indicatorId: 'BuildingLifecycleAssessment_LEED_International',
                status: CalculationProcess.CalculationStatus.IN_PROGRESS.name,
                startTime: new Date(),
                finishTime: new Date(),
        )

        then: 'calculationProcess is valid instance'
        calculationProcess.validate()

        and: 'we can save it'
        calculationProcess.save()

        and: 'there is one additional calculationProcess created'
        CalculationProcess.count() == old(CalculationProcess.count()) + 1
    }
}
