/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection


/**
 * @author Pasi-Markus Mäkelä
 */
class QuestionAnswerChoiceService {
    
    def queryService
    def querySectionService
    
    def getAnswerChoiceByQueryIdAndSectionIdAndAnswerId(queryId, sectionId, answerId) {
        Query query = queryService.getQueryByQueryId(queryId, true)
        QuerySection section = query?.sections?.find ({ s ->
            s.sectionId == sectionId
        })
        def questionAnswerChoice
        
        try {
            section?.questions?.each { question ->
                question.choices?.each { choice ->
                    if(choice.answerId == answerId) {
                        questionAnswerChoice = choice
                        throw new Exception("Right questionAnswerChoice found, breaking...")
                    }
                    
                    choice.ifChosen?.each { ifChosenQuestion ->
                        ifChosenQuestion.choices?.each { ifChosenQuestionChoice ->
                            if(ifChosenQuestionChoice.answerId == answerId) {
                                questionAnswerChoice = ifChosenQuestionChoice
                                throw new Exception("Right questionAnswerChoice found, breaking...")
                            }
                        }
                    }
                    
                    choice.getIfChosenSections()?.each { ifChosenSection ->
                        ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                            ifChosenSectionQuestion.choices?.each { ifChosenSectionQuestionChoice ->
                                if(ifChosenSectionQuestionChoice.answerId == answerId) {
                                    questionAnswerChoice = ifChosenSectionQuestionChoice
                                    throw new Exception("Right questionAnswerChoice found, breaking...")
                                }
                            }
                        }
                    }
                }
            }
            
            querySectionService.getIncludedSections(section?.includeSectionIds)?.each{ includedSection ->
                includedSection.questions?.each { question ->
                    question.choices?.each { choice ->
                        if(choice.answerId == answerId) {
                            questionAnswerChoice = choice
                            throw new Exception("Right questionAnswerChoice found, breaking...")
                        }
                        
                        choice.ifChosen?.each { ifChosenQuestion ->
                            ifChosenQuestion.choices?.each { ifChosenQuestionChoice ->
                                if(ifChosenQuestionChoice.answerId == answerId) {
                                    questionAnswerChoice = ifChosenQuestionChoice
                                    throw new Exception("Right questionAnswerChoice found, breaking...")
                                }
                            }
                        }
                        
                        choice.getIfChosenSections()?.each { ifChosenSection ->
                            ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                                ifChosenSectionQuestion.choices?.each { ifChosenSectionQuestionChoice ->
                                    if(ifChosenSectionQuestionChoice.answerId == answerId) {
                                        questionAnswerChoice = ifChosenSectionQuestionChoice
                                        throw new Exception("Right questionAnswerChoice found, breaking...")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            section?.sections?.each{ QuerySection includedSection ->
                includedSection.questions?.each { question ->
                    question.choices?.each { choice ->
                        if(choice.answerId == answerId) {
                            questionAnswerChoice = choice
                            throw new Exception("Right questionAnswerChoice found, breaking...")
                        }
                        
                        choice.ifChosen?.each { ifChosenQuestion ->
                            ifChosenQuestion.choices?.each { ifChosenQuestionChoice ->
                                if(ifChosenQuestionChoice.answerId == answerId) {
                                    questionAnswerChoice = ifChosenQuestionChoice
                                    throw new Exception("Right questionAnswerChoice found, breaking...")
                                }
                            }
                        }
                        
                        choice.getIfChosenSections()?.each { ifChosenSection ->
                            ifChosenSection.questions?.each { ifChosenSectionQuestion ->
                                ifChosenSectionQuestion.choices?.each { ifChosenSectionQuestionChoice ->
                                    if(ifChosenSectionQuestionChoice.answerId == answerId) {
                                        questionAnswerChoice = ifChosenSectionQuestionChoice
                                        throw new Exception("Right questionAnswerChoice found, breaking...")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // do nothing, just to break from iteration
        }
        return questionAnswerChoice
    }
}
