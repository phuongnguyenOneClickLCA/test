package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Question
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class QuestionServiceSpec extends Specification implements ServiceUnitTest<QuestionService> {

    def setup() {
    }

    def cleanup() {
    }

    void "getDoNotTriggerCalcQuestionIds"() {
        expect:
        service.getDoNotTriggerCalcQuestionIds(questions) == result

        where:
        questions                                                                                                                                                   | result
        [new Question([questionId: 'comment', informationalInput: true])]                                                                                           | ['comment'].toSet()
        [new Question([questionId: 'privateClassification', privateClassificationQuestion: true])]                                                                  | ['privateClassification'].toSet()
        [new Question([questionId: 'comment', informationalInput: true]), new Question([questionId: 'privateClassification', privateClassificationQuestion: true])] | ['comment', 'privateClassification'].toSet()
        [new Question([questionId: 'quantity']), new Question([questionId: 'privateClassification', privateClassificationQuestion: true])]                          | ['privateClassification'].toSet()
        [new Question([questionId: 'quantity']), new Question([questionId: 'unit'])]                                                                                | new HashSet<>()

    }

    void "getRenderStringDoNotTriggerCalcQuestionIds"() {
        expect:
        service.getRenderStringDoNotTriggerCalcQuestionIds(questions) == result

        where:
        questions                                                                                                                                                   | result
        [new Question([questionId: 'comment', informationalInput: true])]                                                                                           | "\"comment\""
        [new Question([questionId: 'privateClassification', privateClassificationQuestion: true])]                                                                  | "\"privateClassification\""
        [new Question([questionId: 'comment', informationalInput: true]), new Question([questionId: 'privateClassification', privateClassificationQuestion: true])] | "\"privateClassification\",\"comment\""
        [new Question([questionId: 'quantity']), new Question([questionId: 'privateClassification', privateClassificationQuestion: true])]                          | "\"privateClassification\""
        [new Question([questionId: 'quantity']), new Question([questionId: 'unit'])]                                                                                | ''
    }
}
