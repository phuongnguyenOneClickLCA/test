package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional
import org.apache.xpath.operations.Bool

@Transactional
class PrivateClassificationListService {

    def userService
    def questionService
    def queryService

    def getPrivateClassificationList(String id) {
        PrivateClassificationList privateClassificationList
        if (id) {
            privateClassificationList = PrivateClassificationList.get(DomainObjectUtil.stringToObjectId(id))
        }
        return privateClassificationList
    }

    def getPrivateClassificationListFromAccountId(String accountId) {
        PrivateClassificationList privateClassificationList
        if (accountId) {
            privateClassificationList = PrivateClassificationList.findByAccountId(accountId)
        }
        return privateClassificationList
    }

    def getPrivateClassificationListFromQuestionId(String questionId) {
        PrivateClassificationList privateClassificationList
        if (questionId) {
            privateClassificationList = PrivateClassificationList.findByQuestionId(questionId)
        }
        return privateClassificationList
    }

    //--- Obsolete if account can only have 1 list, keeping in case minds are changed later for more lists
    def getPrivateClassificationListsFromAccountId(String accountId) {
        List<PrivateClassificationList> privateClassificationList = []
        if (accountId) {
            privateClassificationList = PrivateClassificationList.findAllByAccountId(accountId)
        }
        return privateClassificationList
    }

    def getPrivateClassificationListsFromAccount(Account privateAccount) {
        List<PrivateClassificationList> privateClassificationList = []
        if (privateAccount) {
            privateClassificationList = PrivateClassificationList.collection.find([accountId: privateAccount.id])?.collect({ it as PrivateClassificationList })
        }
        return privateClassificationList
    }

    Question getPrivateClassificationQuestion() {
        User user = userService?.getCurrentUser()
        Question privateClassificationQuestion = null
        if (user) {
            Account account = userService.getAccount(user)
            if (account) {
                PrivateClassificationList privateClassification = getPrivateClassificationListFromAccountId("${account?.id}")
                if (privateClassification) {
                    List<QuestionAnswerChoice> privateClassificationChoices = []
                    privateClassification.answers.each({
                        privateClassificationChoices.add(new QuestionAnswerChoice(answerId: it.key, answer: ["EN": it.value]))
                    })
                    Map<String, String> privateClassificationLocalisedName = ["EN": privateClassification.name]
                    privateClassificationQuestion = new Question(questionId: privateClassification.questionId, privateClassificationQuestion: Boolean.TRUE,
                            inputType: "select", question: privateClassificationLocalisedName, "questionSequenceNr": 11,
                            "inputWidth": 100, "inheritToChildren": true, choices: privateClassificationChoices)
                }
            }
        }
        return privateClassificationQuestion
    }

    def getPrivateClassificationQuestionWithIndicatorSpecifics(Indicator indicator) {
        User user = userService?.getCurrentUser()
        Question privateClassificationQuestion
        if (indicator) {
            if (user) {
                Account account = userService.getAccount(user)
                if (account) {
                    PrivateClassificationList privateClassification = getPrivateClassificationListFromAccountId("${account?.id}")
                    if (privateClassification) {
                    List<QuestionAnswerChoice> privateClassificationChoices = []
                        privateClassification.answers.each({
                            privateClassificationChoices.add(new QuestionAnswerChoice(answerId: it.key, answer: ["EN": it.value]))
                        })
                        Map<String, String> privateClassificationLocalisedName = ["EN": privateClassification.name]
                        privateClassificationQuestion = new Question(questionId: privateClassification.questionId, privateClassificationQuestion: Boolean.TRUE,
                                inputType: "select", question: privateClassificationLocalisedName, "questionSequenceNr": indicator?.getPrivateClassificationQuestionSequenceNr(),
                                "inputWidth": 100, help: indicator?.privateClassificationHelp, "inheritToChildren": true, choices: privateClassificationChoices)
                    }
                }
            }
        }
        return privateClassificationQuestion
    }
    def getPrivateClassificationQuestionByIndicator(List<String> classificationsMap, Indicator indicator = null,Question mappingQ = null) {
        User user = userService?.getCurrentUser()
        Question privateClassificationQuestion
        if (user) {
            Account account = userService.getAccount(user)
            if (account) {
                PrivateClassificationList privateClassification = getPrivateClassificationListFromAccountId("${account?.id}")
                if (privateClassification) {
                    Integer indexOfPCL = classificationsMap?.indexOf(Constants.ORGANIZATION_CLASSIFICATION_ID) ?: 0
                    Integer indexOfMappingQ = classificationsMap?.indexOf(mappingQ?.questionId) ?: 0
                    Integer sequenceNumber = mappingQ?.questionSequenceNr ? mappingQ.questionSequenceNr + (indexOfPCL - indexOfMappingQ)  : 11
                    List<QuestionAnswerChoice> privateClassificationChoices = []
                    privateClassification.answers.each({
                        privateClassificationChoices.add(new QuestionAnswerChoice(answerId: it.key, answer: ["EN": it.value]))
                    })
                    Map<String, String> privateClassificationLocalisedName = ["EN": privateClassification.name]
                    privateClassificationQuestion = new Question(questionId: privateClassification.questionId, privateClassificationQuestion: Boolean.TRUE,
                            inputType: "select", question: privateClassificationLocalisedName, "questionSequenceNr": sequenceNumber,
                            "inputWidth": 100, help: indicator?.privateClassificationHelp, "inheritToChildren": true, choices: privateClassificationChoices)
                }
            }
        }

        return privateClassificationQuestion
    }
}
