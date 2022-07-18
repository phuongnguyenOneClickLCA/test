package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CD3DIndicatorDetailsPayloadSpec extends Specification implements DomainUnitTest<CD3DIndicatorDetailsPayload> {

    void 'indicator id cannot be null'() {
        when:
        domain.indicatorId = null

        then:
        !domain.validate(['indicatorId'])
        domain.errors['indicatorId'].code == 'nullable'
    }

    void 'indicator name cannot be null'() {
        when:
        domain.indicatorName = null

        then:
        !domain.validate(['indicatorName'])
        domain.errors['indicatorName'].code == 'nullable'
    }
}
