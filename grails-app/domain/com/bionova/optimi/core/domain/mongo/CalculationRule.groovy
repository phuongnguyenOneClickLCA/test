/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class CalculationRule implements Serializable {
    static mapWith = "mongo"
    String calculationRuleId
    String calculationRule
    @Translatable
    Map <String, String> shortName
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> breeamReportName
    @Translatable
    Map<String, String> unit
    @Translatable
    Map<String, String> help
    List<String> multiply
    Boolean multiplyByImpactBasis
    List<String> ignoreIfAdditionalQuestionTrue
    Map<String,List<String>> impactCategoryMultiplyExceptions

    // For LCC
    String discountRate
    ValueReference discountRateValueRef
    String energyInflation
    ValueReference energyInflationValueRef
    String generalInflation
    ValueReference generalInflationValueRef
    ValueReference waterInflationValueRef

    // adding new filtering capability
    String ruleFilterParameter
    List<String> ruleAcceptValues
    // filter parameters list. This and ruleFilterParameter && ruleAcceptValues should not be used at the same time. Mantis 14808
    /*
        Example:
        "ruleFilterParametersList" : [
            {   "epdStandardVersionAddQ": ["a1", "a1a2", "all"], "applyCondition" : "presentInDataset" },
            {   "outputFlowEN15804": ["reusableMaterialsOutput_kg"] }
        ]
     */
    List ruleFilterParametersList
    // For cityData calculation
    Integer minimumZeros

    Boolean mapToResource
    Boolean hideInGraphs

    // For hotel scaling factors etc
    CalculationRuleDefaultData defaultData
    Boolean scalingFactor

    ValueReference optionalMultiplier
    String optionalAdditionalQuestionMultiplier
    List<String> additionalQuestionMultipliers
    Boolean setToZeroIfNoAdditionalQuestionMultiplierFound
    Boolean setToZeroIfValueIsNegative
    Boolean setToZeroIfValueIsPositive

    List<String> skipRuleForCategories

    ResultManipulator resultManipulator
    List<ResultManipulator> resultManipulators
    List<String> virtual

    Boolean hideInTrial // hides results for rule in trial licenses
    Boolean useCalculatedMassAsResult

    Boolean skipImpactCalculation
    ValueReference unitAsValueReference

    Boolean multiplyWithOrganizationCarbonCost

    Boolean preventResultCategoryResultManipulator
    @Translatable
    Map<String, String> graphName
    Boolean excludeDenominator // If true doesnt apply overall denominator in calculation for this rule

    String mapToImpactCategory // for data sharing

    String ruleIconId
    String licenseKey
    /*
          "discountRate" : "$discountRateA",
        "energyInflation" : "$energyInflationRate",
        "generalInflation" : "$generalInflationRate"
     */

    //== 15869
    Double useFixedCalculationBasis

    //Ticket 16127
    List<String> selectSmallestCalculationRule
    List<String> selectBiggestCalculationRule
    String mapToIndicatorCode_INIES

    static hasMany = [resultManipulators: ResultManipulator]

    static embedded = [
            'calculationQuestion',
            'discountRateValueRef',
            'energyInflationValueRef',
            'generalInflationValueRef',
            'waterInflationValueRef',
            'optionalMultiplier',
            'resultManipulator',
            'defaultData',
            'resultManipulators',
            'unitAsValueReference'
    ]

    static constraints = {
        discountRateValueRef nullable: true
        energyInflationValueRef nullable: true
        generalInflationValueRef nullable: true
        waterInflationValueRef nullable: true
        calculationRuleId nullable: true
        name nullable: true
        unit nullable: true
        shortName nullable: true
        multiplyByImpactBasis nullable: true
        ruleFilterParameter nullable: true
        ruleAcceptValues nullable: true
        ruleFilterParametersList nullable: true
        discountRate nullable: true
        energyInflation nullable: true
        generalInflation nullable: true
        minimumZeros nullable: true
        mapToResource nullable: true
        hideInGraphs nullable: true
        defaultData nullable: true
        scalingFactor nullable: true
        optionalMultiplier nullable: true
        optionalAdditionalQuestionMultiplier nullable: true
        skipRuleForCategories nullable: true
        resultManipulator nullable: true
        resultManipulators nullable: true
        skipImpactCalculation nullable: true
        virtual nullable: true
        ignoreIfAdditionalQuestionTrue nullable: true
        setToZeroIfNoAdditionalQuestionMultiplierFound nullable: true
        unitAsValueReference nullable: true
        breeamReportName nullable: true
        additionalQuestionMultipliers nullable: true
        hideInTrial nullable: true
        preventResultCategoryResultManipulator nullable: true
        setToZeroIfValueIsNegative nullable: true
        setToZeroIfValueIsPositive nullable: true
        graphName nullable: true
        help nullable: true
        multiplyWithOrganizationCarbonCost nullable: true
        useCalculatedMassAsResult nullable: true
        ruleIconId nullable: true
        licenseKey nullable: true
        excludeDenominator nullable: true
        mapToImpactCategory nullable: true
        impactCategoryMultiplyExceptions nullable: true
        useFixedCalculationBasis nullable: true
        selectSmallestCalculationRule nullable: true
        selectBiggestCalculationRule nullable: true
        mapToIndicatorCode_INIES nullable: true
    }

    static transients = ['defaultDataObject']

    def getDefaultDataObject () {
        CalculationRuleDefaultData defaultData

        if (this.defaultData) {
            defaultData = (CalculationRuleDefaultData) this.defaultData
        }
        return defaultData
    }
}
