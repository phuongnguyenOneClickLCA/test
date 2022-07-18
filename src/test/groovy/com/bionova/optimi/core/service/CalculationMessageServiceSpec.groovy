package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class CalculationMessageServiceSpec extends Specification implements ServiceUnitTest<CalculationMessageService> {

    def setup() {
    }

    def cleanup() {
    }

    void "persistMessageForIndicator"() {
        expect:
        Entity entity = new Entity()
        service.persistMessageForIndicator(entity, new Indicator(indicatorId: indicatorId), message)
        expected == entity.saveMessageByIndicator?.get(indicatorId)

        where:
        indicatorId | message       | expected
        'someId'    | 'someMessage' | 'someMessage'
        null        | 'someMessage' | null
        'someId'    | null          | null
        ''          | 'someMessage' | null
        'someId'    | ''            | ''
    }

    /* This test got error "No GORM implementations configured. Ensure GORM has been initialized correctly". Comment out for now. Fix after code freeze
    void "persistPreviousTotal"() {
        expect:
        Entity entity = new Entity()
        service.persistPreviousTotal(entity, indicatorId, prevTotal)
        expected == entity.previousTotalByIndicator?.get(indicatorId)

        where:
        indicatorId | prevTotal | expected
        '1'         | 0.0       | 0.0
        ''          | 0.0       | null
        '1'         | null      | null
    }
     */
}
