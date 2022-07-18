/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class QuerySectionDefault implements Serializable, Validateable {
    static mapWith = "mongo"
    String inputType
    List<String> additionalQuestionIds
    @Translatable
    Map<String, String> unit
    Boolean quantity
    Boolean optional
    Boolean multipleChoicesAllowed
    List<String> allowedFileTypes // only used if inputType file
    Integer fileMaxSize // only used if inputType file
    Integer fileMaxAmount // only used if inputType file
    Boolean showTypeahead

    static transients = [
            "additionalQuestions"
    ]

    static constraints = {
        inputType nullable: true, blank: true
        additionalQuestionIds nullable: true
        quantity nullable: true
        optional nullable: true
        multipleChoicesAllowed nullable: true
        allowedFileTypes nullable: true
        fileMaxSize nullable: true
        fileMaxAmount nullable: true
        showTypeahead nullable: true
    }

    transient queryService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<Question> getAdditionalQuestions() {
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalQuestions = []

        if (additionalQuestionsQuery?.sections && additionalQuestionIds) {
            QuerySection additionalQuestionsSection = additionalQuestionsQuery?.sections[0]

            if (additionalQuestionsSection) {
                additionalQuestionIds.each { String questionId ->
                    Question question = additionalQuestionsSection.questions?.find({ questionId.equals(it.questionId) })

                    if (question) {
                        additionalQuestions.add(question)
                    }
                }
            }
        }
        return additionalQuestions
    }
}
