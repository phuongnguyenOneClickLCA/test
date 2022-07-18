/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class ResultCategory implements Serializable {
    static mapWith = "mongo"
    String resultCategoryId
    String resultCategory
    String resultDivider
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> breeamReportName
    @Translatable
    Map<String, String> shortName
    Map<String, Object> data // Value can be String or list
    Map<String, Set> limitSectionDataToQuestionIds
    Map<String, Object> process // Value used to be List<String> but replace resource processing uses also valueref
    Boolean calculateReplacementsOnly
    Boolean calculateFractionalReplacementsOnly
    @Translatable
    Map<String, String> clarificationText
    @Translatable
    Map<String, String> clarificationTextDefault
    String defaultResourceId
    Boolean skipOverallDenominator
    Boolean linkToQueryFromReport
    String manualLinkToQueryFromReport
    // For LCC
    String discountRule
    String costBasis
    String costInflation

    // filter parameters. These allow filtering away non useful things from the result category so the assessment does not carry them forward
    Boolean removeEmptyResources
    String resourceFilterParameter
    List<String> acceptResourceValues
    // RE2020 REL2018-130 new type of filter parameter, reuse ResourceFilterCriteria
    List<ResourceFilterCriteria> resourceFilterCriteriaList

    List ignoreFromTotals
    List hideResultInReport
    @Translatable
    Map<String, String> help

    ApplyConditions applyConditions

    List<ResultManipulator> resultManipulators

    List<String> sumOfCategories

    Boolean preventExpand
    Boolean hideInGraphs
    List<ValueReference> clarificationAsValueReferences

    ApplyEnergyBenefitSharing applyEnergyBenefitSharing
    ValueReference compensateEnergyMix

    List<String> applyStages

    List<String> virtual

    List<String> additionalQuestionMultipliers
    String optionalAdditionalQuestionMultiplier // needed for BREEAM UK, task 3426
    String optionalAdditionalQuestionDivider
    Boolean setToZeroIfNoAdditionalQuestionMultiplierFound
    Boolean setToZeroIfValueIsNegative
    Boolean setToZeroIfValueIsPositive

    Boolean applyFractionalReplacementMultiplier
    Boolean setMinimumReplacementToOne
    Boolean applyMaintenanceReplacementMultiplier
    Boolean applyConstructionReplacementMultiplier

    //pointcalculation
    String comparison
    String targetResultCategory
    String actualResultCategory
    List<CompareRule> compareRules
    List<String> compatibleLcaModels // task 11579, shows only if relevant model selected for project
    List<String> compatibleLcaModelsIndicatorIds // if empty needs to match all tools

    //new processing to replace process
    ApplyProcess applyProcess

    // new way of creating periodical results and also to replace recurring replacement
    String impactTiming
    List<String> eolProcessStages
    List<String> eolProcessStagesSum

    Boolean hideInTrial // hides results for rule in trial licenses

    List<String> ignoreIfAdditionalQuestionTrue
    String mapToStage // for data sharing
    String calculateForUniqueAnswersOnly // For FEC Tool ticket: 15717
    Boolean allowUnassignedZone // For FEC Tool ticket: 15809

    //GLA discounting
    Map<String, Object> applyDiscountScenario

    //Ticket 16127
    List<String> selectSmallestResultCategory
    List<String> selectBiggestResultCategory
    //REL2018 - 132
    Double setMinimumValue
    Double setMaximumValue

    //SW-1526
    List<AdditionalQuestionMultipliersWithConditions> additionalQuestionMultipliersWithConditions
    List<String> mapToPhaseCode_INIES

    static hasMany = [resultManipulators : ResultManipulator, clarificationAsValueReferences: ValueReference, compareRules: CompareRule, resourceFilterCriteriaList: ResourceFilterCriteria,
                      additionalQuestionMultipliersWithConditions: AdditionalQuestionMultipliersWithConditions]

    static embedded = [
            'resultManipulators',
            'clarificationAsValueReferences',
            'applyEnergyBenefitSharing',
            'compensateEnergyMix',
            'applyConditions',
            'compareRules',
            'applyProcess',
            'resourceFilterCriteriaList',
            'additionalQuestionMultipliersWithConditions'
    ]

    static constraints = {
        resultCategoryId nullable: true
        process nullable: true
        defaultResourceId nullable: true
        clarificationText nullable: true
        clarificationTextDefault nullable: true
        discountRule nullable: true
        costBasis nullable: true
        costInflation nullable: true
        removeEmptyResources nullable: true
        resourceFilterParameter nullable: true
        acceptResourceValues nullable: true
        resourceFilterCriteriaList nullable: true
        ignoreFromTotals nullable: true
        hideResultInReport nullable: true
        resultDivider nullable: true
        resultManipulators nullable: true
        applyConditions nullable: true
        skipOverallDenominator nullable: true
        sumOfCategories nullable: true
        preventExpand nullable: true
        clarificationAsValueReferences nullable: true
        applyEnergyBenefitSharing nullable: true
        compensateEnergyMix nullable: true
        calculateReplacementsOnly nullable: true
        calculateFractionalReplacementsOnly nullable: true
        applyStages nullable: true
        virtual nullable: true
        ignoreIfAdditionalQuestionTrue nullable: true
        optionalAdditionalQuestionMultiplier nullable: true
        hideInGraphs nullable: true
        shortName nullable: true
        linkToQueryFromReport nullable: true
        optionalAdditionalQuestionDivider nullable: true
        applyFractionalReplacementMultiplier nullable: true
        comparison nullable: true
        targetResultCategory nullable: true
        actualResultCategory nullable: true
        compareRules nullable: true
        setToZeroIfNoAdditionalQuestionMultiplierFound nullable: true
        applyProcess nullable: true
        setMinimumReplacementToOne nullable: true
        applyMaintenanceReplacementMultiplier nullable: true
        applyConstructionReplacementMultiplier nullable: true
        breeamReportName nullable: true
        impactTiming nullable: true
        additionalQuestionMultipliers nullable: true
        setToZeroIfValueIsNegative nullable: true
        setToZeroIfValueIsPositive nullable: true
        help nullable: true
        hideInTrial nullable: true
        eolProcessStages nullable: true
        compatibleLcaModels nullable: true
        eolProcessStagesSum nullable: true
        compatibleLcaModelsIndicatorIds nullable: true
        manualLinkToQueryFromReport nullable: true
        mapToStage nullable: true
        calculateForUniqueAnswersOnly nullable: true
        allowUnassignedZone nullable: true
        applyDiscountScenario nullable: true
        selectSmallestResultCategory nullable: true
        selectBiggestResultCategory nullable: true
        setMaximumValue nullable: true
        setMinimumValue nullable: true
        additionalQuestionMultipliersWithConditions nullable: true
        mapToPhaseCode_INIES nullable: true
    }

    static transients = [
            "defaultDataset",
            "hasDefaultQuantityValueReference",
    ]

    private String getLanguage() {
        return LocaleContextHolder.getLocale().getLanguage().toUpperCase()
    }
}


