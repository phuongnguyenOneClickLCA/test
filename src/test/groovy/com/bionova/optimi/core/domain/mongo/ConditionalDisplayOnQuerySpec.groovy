package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ConditionalDisplayOnQuerySpec extends Specification implements DomainUnitTest<ConditionalDisplayOnQuery> {

    void "method cannot be null"() {
        when: 'method is null'
        domain.method = null
        domain.resolveMethod = "all"
        domain.additionalQuestionIds = ['test_question_id']

        then: 'validation should fail'
        !domain.validate()
        domain.errors['method'].code == 'nullable'
    }

    void "method value is invalid"() {
        when: 'method  value is invalid'
        domain.method = 'test_method'
        domain.resolveMethod = "any"
        domain.additionalQuestionIds = ['test_question_id']

        then: 'validation should fail'
        !domain.validate()
        domain.errors['method'].code == 'not.inList'
    }

    void "resolve method cannot be null"() {
        when: 'resolve method is null'
        domain.method = "show"
        domain.resolveMethod = null
        domain.additionalQuestionIds = ['test_question_id']

        then: 'validation should fail'
        !domain.validate()
        domain.errors['resolveMethod'].code == 'nullable'
    }

    void "resolve method value is invalid"() {
        when: 'resolve method  value is invalid'
        domain.method = "hide"
        domain.resolveMethod = 'test_resolve_method'
        domain.additionalQuestionIds = ['test_question_id']

        then: 'validation should fail'
        !domain.validate()
        domain.errors['resolveMethod'].code == 'not.inList'
    }

    void "additional question ids cannot be null"() {
        when: 'additional question ids is null'
        domain.method = "show"
        domain.resolveMethod = "all"
        domain.additionalQuestionIds = null

        then: 'validation should fail'
        !domain.validate()
        domain.errors['additionalQuestionIds'].code == 'nullable'
    }

    void "additional question ids cannot be empty"() {
        when: 'additional question ids is empty'
        domain.method = "hide"
        domain.resolveMethod = "any"
        domain.additionalQuestionIds = []

        then: 'validation should fail'
        !domain.validate()
        domain.errors['additionalQuestionIds'].code == 'minSize.notmet'
    }

    void "entity is valid"() {
        when: 'all fields are correct'
        domain.method = "show"
        domain.resolveMethod = "none"
        domain.additionalQuestionIds = ['test_question_id_1', 'test_question_id_2']

        then: 'validation should pass'
        domain.validate()
    }
}
