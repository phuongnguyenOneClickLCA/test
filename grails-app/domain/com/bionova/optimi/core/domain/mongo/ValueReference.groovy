package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ValueReference implements Serializable {
    static mapWith = "mongo"
    String queryId
    String sectionId
    String questionId
    String indicatorId
    String portfolioId
    String additionalQuestionId
    String resourceParameter
    String returnResourceValue
    Boolean multipleAnswersAllowed
    String calculationRule
    String exportType
    List<String> denominatorIds
    Double compensationFactor
    String answerId
    List<String> answerIds
    String resourceId
    String profileId
    Integer priority
    Double conversionFactor

    List<MultiplierWithConditions> multipliersWithConditions

    // For referencing other objects in showData.gsp pages
    String page
    List<String> resourceIdReferences
    List<String> resourceTypeReferences
    List<String> resourceSubTypeReferences
    List<String> eolProcessReferences

    // For importing XML, add info to comment in dataset while capturing data from RSET
    String comment

    // This is for using while reading xml, do not use anywhere else or in configuration
    // readXmlValueFor = "projectLevel" || "${designId}"
    String readXmlValueFor

    //RE2020 - FPI Project
    List<String> showQuestionForBuildingTypes

    static embedded = [
            "multipliersWithConditions"
    ]

    static constraints = {
        resourceParameter nullable: true
        returnResourceValue nullable: true
        multipleAnswersAllowed nullable: true
        calculationRule nullable: true
        indicatorId nullable: true
        exportType nullable: true
        additionalQuestionId nullable: true
        denominatorIds nullable: true
        compensationFactor nullable: true
        answerId nullable: true
        answerIds nullable: true
        portfolioId nullable: true
        resourceId nullable: true
        profileId nullable: true
        priority nullable: true
        conversionFactor nullable: true
        page nullable: true
        resourceIdReferences nullable: true
        resourceTypeReferences nullable: true
        resourceSubTypeReferences nullable: true
        eolProcessReferences nullable: true
        multipliersWithConditions nullable: true
        readXmlValueFor nullable: true
        comment nullable: true
        showQuestionForBuildingTypes nullable: true
    }

    @Override
    String toString() {
        return "${queryId}: ${sectionId}: ${questionId}"
    }
}
