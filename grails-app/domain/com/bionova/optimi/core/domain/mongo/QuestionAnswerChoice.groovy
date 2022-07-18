/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class QuestionAnswerChoice implements Serializable, Validateable {
    static mapWith = "mongo"
    String answerId
    Double pointsFactor
    List<Question> ifChosen // if chosen, the questions that are shown
    List<String> ifChosenSectionIds // If chosen, what sections are shown
    List<String> additionalQuestionIds
    @Translatable
    Map<String, String> answer // label of choice
    @Translatable
    Map<String, String> help
    Map <String, String> lockMandatoryFilters
    Map <String, String> resourceParameters
    List<String> copyImpactsToStages //13986
    Map <String, String> constructionTypes
    String componentQuestionId
    List<String> showOnlyForQuestionIds
    List<String> constructionAdditionalQuestionIds
    List<String> allowedUnitSystems
    List<String> allowedResourceSubTypes // to show this answer the resource needs to have this resource subtype
    List<String> allowedUnits // to show this answer the resource needs to have one of these units
    String userClass
    String relatedQuestionAnswerId
    Integer orderNumber
    String licenseKey
    Boolean disabled // true if choice not licensed, shows for user but cant select

    List<String> materialsQuerySections
    List<LimitConditions> limitConditions
    //sw-2244
    Map<String, String> enabledQuestions

    private static final String ADDITIONAL_QUESTIONS_QUERY_ID = "additionalQuestionsQuery"

    static transients = [
        "ifChosenSections",
        "additionalQuestions"
    ]

    static embedded = ["ifChosen", "limitConditions"]

    static constraints = {
        ifChosen nullable: true
        pointsFactor nullable: true
        ifChosenSectionIds nullable: true
        additionalQuestionIds nullable: true
        help nullable: true
        showOnlyForQuestionIds nullable: true
        lockMandatoryFilters nullable: true
        resourceParameters nullable: true
        constructionTypes nullable: true
        componentQuestionId nullable: true
        constructionAdditionalQuestionIds nullable: true
        allowedUnitSystems nullable: true
        allowedResourceSubTypes nullable: true
        allowedUnits nullable: true
        userClass nullable: true
        materialsQuerySections nullable: true
        relatedQuestionAnswerId nullable: true
        orderNumber nullable: true
        licenseKey nullable: true
        disabled nullable: true
        copyImpactsToStages nullable: true
        limitConditions nullable: true
        enabledQuestions nullable: true
    }

    transient queryService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<QuerySection> getIfChosenSections() {
        List<QuerySection> ifChosenSections = []

        if (ifChosenSectionIds) {
            ifChosenSections = QuerySection.findAllBySectionIdInList(ifChosenSectionIds)
        }
        return ifChosenSections
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<Question> getAdditionalQuestions() {
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List<Question> additionalQuestions = []

        if (additionalQuestionsQuery?.sections) {
            QuerySection additionalQuestionsSection = additionalQuestionsQuery?.sections[0]
            additionalQuestionsSection.questions?.each { question ->
                if (additionalQuestionIds?.contains(question.questionId)) {
                    additionalQuestions.add(question)
                }
            }
        }
        return additionalQuestions
    }
}