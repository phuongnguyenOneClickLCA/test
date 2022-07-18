package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.ConditionalDisplayOnQuery
import com.bionova.optimi.core.domain.mongo.DataCardRow
import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UtilServiceSpec extends Specification implements ServiceUnitTest<UtilService>, DomainUnitTest<ConditionalDisplayOnQuery> {

    void "check displaying of the data card row when the configuration is null"() {
        given:
        DataCardRow dataCardRow = new DataCardRow()
        List<String> additionalQuestionIds = ['question_id_1', 'question_id_2', 'question_id_3']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as configuration is null."
    }

    void "check displaying of the data card row when the configuration is invalid"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "any", additionalQuestionIds: []
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_1', 'question_id_2', 'question_id_3']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as configuration is invalid."
    }

    void "check displaying of the data card row when the method is 'show' and the resolve method is 'all'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "all", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1', 'question_id_4', 'question_id_3', 'question_id_2']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'show', the resolve method is 'all' and " +
                "the list of incoming additional question ids contains all elements from the configuration list of additional question ids."
    }

    void "check no displaying of the data card row when the method is 'show' and the resolve method is 'all'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "all", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_6']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1', 'question_id_4', 'question_id_3', 'question_id_2']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'show', the resolve method is 'all' and " +
                "the list of incoming additional question ids doesn't contain all elements from the configuration list of additional question ids."
    }

    void "check displaying of the data card row when the method is 'show' and the resolve method is 'any'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "any", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'show', the resolve method is 'any' and " +
                "the list of incoming additional question ids contains at least one element from the configuration list of additional question ids."
    }

    void "check no displaying of the data card row when the method is 'show' and the resolve method is 'any'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "any", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_4']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'show', the resolve method is 'any' and " +
                "the list of incoming additional question ids doesn't contain any elements from the configuration list of additional question ids."
    }

    void "check displaying of the data card row when the method is 'show' and the resolve method is 'none'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "none", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_6', 'question_id_5', 'question_id_4']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'show', the resolve method is 'none' and " +
                "the list of incoming additional question ids doesn't contain any elements from the configuration list of additional question ids."
    }

    void "check no displaying of the data card row when the method is 'show' and the resolve method is 'none'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "show", resolveMethod: "none", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_1', 'question_id_4']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'show', the resolve method is 'none' and " +
                "the list of incoming additional question ids contains at least one element from the configuration list of additional question ids."
    }

    void "check hiding of the data card row when the method is 'hide' and the resolve method is 'all'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "all", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1', 'question_id_4', 'question_id_3', 'question_id_2']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'hide', the resolve method is 'all' and " +
                "the list of incoming additional question ids contains all elements from the configuration list of additional question ids."
    }

    void "check no hiding of the data card row when the method is 'hide' and the resolve method is 'all'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "all", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1', 'question_id_4', 'question_id_3']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'hide', the resolve method is 'all' and " +
                "the list of incoming additional question ids doesn't contain all elements from the configuration list of additional question ids."
    }

    void "check hiding of the data card row when the method is 'hide' and the resolve method is 'any'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "any", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_5', 'question_id_1', 'question_id_3']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'show', the resolve method is 'any' and " +
                "the list of incoming additional question ids contains at least one element from the configuration list of additional question ids."
    }

    void "check no hiding of the data card row when the method is 'hide' and the resolve method is 'any'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "any", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_4', 'question_id_5', 'question_id_6']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'hide', the resolve method is 'any' and " +
                "the list of incoming additional question ids doesn't contain any elements from the configuration list of additional question ids."
    }

    void "check hiding of the data card row when the method is 'hide' and the resolve method is 'none'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "none", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_4', 'question_id_5', 'question_id_6']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert !result, "Result should be 'false' as the method is 'hide', the resolve method is 'none' and " +
                "the list of incoming additional question ids doesn't contain any elements from the configuration list of additional question ids."
    }

    void "check no hiding of the data card row when the method is 'hide', the resolve method is 'none'"() {
        given:
        ConditionalDisplayOnQuery conditionalDisplayOnQuery = new ConditionalDisplayOnQuery(
                method: "hide", resolveMethod: "none", additionalQuestionIds: ['question_id_1', 'question_id_2', 'question_id_3']
        )
        DataCardRow dataCardRow = new DataCardRow(conditionalDisplayOnQuery: conditionalDisplayOnQuery)
        List<String> additionalQuestionIds = ['question_id_1', 'question_id_4']

        when:
        boolean result = service.checkIfDataCardRowIsForDisplaying(dataCardRow, additionalQuestionIds)

        then:
        assert result, "Result should be 'true' as the method is 'hide' and the resolve method is 'none' and " +
                "the list of incoming additional question ids contains at least one element from the configuration list of additional question ids."
    }
}
