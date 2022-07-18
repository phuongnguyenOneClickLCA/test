package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MapValuesSpec extends Specification implements DomainUnitTest<MapValues> {

    void 'queryId cannot be null'() {
        when:
        domain.queryId = null

        then:
        !domain.validate(['queryId'])
        domain.errors['queryId'].code == 'nullable'
    }

    void 'sectionId cannot be null'() {
        when:
        domain.sectionId = null

        then:
        !domain.validate(['sectionId'])
        domain.errors['sectionId'].code == 'nullable'
    }

    void 'questionId cannot be null'() {
        when:
        domain.questionId = null

        then:
        !domain.validate(['questionId'])
        domain.errors['questionId'].code == 'nullable'
    }

}
