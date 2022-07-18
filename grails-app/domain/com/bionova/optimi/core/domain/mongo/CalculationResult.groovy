package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import groovy.transform.CompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class CalculationResult {
    static mapWith = "mongo"

    ObjectId id
    String entityId
    String indicatorId
    String calculationRuleId
    String resultCategoryId
    Double result
    Double resultWithoutOccurrencePeriods
    String calculationResourceId
    String calculationProfileId
    String combineId // Transient for SUM EOL
    String originalResourceId
    String originalProfileId
    Map<String, Double> resultByDenominator
    Map<String, Map<String, Double>> monthlyResultByDenominator
    List<String> datasetIds
    Map<String, Double> resultPerOccurencePeriod
    Map<String, Object> datasetAdditionalQuestionAnswers
    Map<String, Double> monthlyResults
    Map<String, Double> quarterlyResults
    Map<String, Map<String, Object>> calculationResultDatasets
    String unit // mainly for energyMixCompensation
    String calculatedUnit
    Boolean calculatedResourceRow
    Boolean applyLocalComps
    // if localCompsCountryResourceId equals == noLocalCompensation then applyLocalComps = false, else applyLocalComps = true
    String localCompsCountryResourceId
    String localCompsCountryEnergyResourceId
    String localCompsCountryEnergyProfileId
    String eolProcessingType
    Map<String, Double> resultWithDiscountPerOccurencePeriod
    Double discountedResult
    Double calculatedDiscountFactor
    Map<Integer, Double> discountAndFractionalMulplierCombined

    Boolean useEolProcessStage

    String calculationFormula // This parameter should keep inside any info about how to final result was calculated

    // This is just to resolve calculationresult to avoid unnessessary db fetches
    Resource calculationResource

    static mapping = {
        entityId index: true
        indicatorId index: true
    }

    static transients = [
            'copyForImpactCalculation',
            'resource',
            'originalResource',
            'datasetAdditionalQuestionAnswers',
            'thickness',
            'resultWithoutOccurrencePeriods',
            'calculationRule',
            'datasetIds',
            'unit',
            'applyLocalComps',
            'eolProcessingType',
            'localCompsCountryResourceId',
            'localCompsCountryEnergyProfileId',
            'localCompsCountryEnergyResourceId',
            'calculatedUnit',
            'calculationResource',
            'useEolProcessStage',
            'calculationResourceIds',
            'calculationProfileIds',
            'calculationFormula'

    ]

    static constraints = {
        resultWithoutOccurrencePeriods nullable: true
        calculationResourceId nullable: true
        calculationProfileId nullable: true
        originalResourceId nullable: true
        originalProfileId nullable: true
        resultByDenominator nullable: true
        datasetIds nullable: true
        resultPerOccurencePeriod nullable: true
        datasetAdditionalQuestionAnswers nullable: true
        monthlyResults nullable: true
        quarterlyResults nullable: true
        calculationResultDatasets nullable: true
        entityId nullable: true
        monthlyResultByDenominator nullable: true
        calculatedResourceRow nullable: true
        applyLocalComps nullable: true
        eolProcessingType nullable: true
        calculationFormula nullable: true
        calculatedUnit nullable: true
        calculationResource nullable: true
        useEolProcessStage nullable: true
        combineId nullable: true
        resultWithDiscountPerOccurencePeriod nullable: true
        discountedResult nullable: true
        calculatedDiscountFactor nullable: true
        discountAndFractionalMulplierCombined nullable: true
    }
    static embedded = ['calculationResultDatasets']

    @CompileStatic
    static CalculationResult getCopyForImpactCalculation(CalculationResult parent) {
        CalculationResult calculationResult = new CalculationResult()
        calculationResult.indicatorId = parent.indicatorId
        calculationResult.entityId = parent.entityId
        calculationResult.resultCategoryId = parent.resultCategoryId
        calculationResult.result = parent.result
        calculationResult.calculationResourceId = parent.calculationResourceId
        calculationResult.calculationProfileId = parent.calculationProfileId
        calculationResult.originalResourceId = parent.originalResourceId
        calculationResult.originalProfileId = parent.originalProfileId
        calculationResult.datasetIds = parent.datasetIds
        calculationResult.calculatedResourceRow = parent.calculatedResourceRow
        calculationResult.eolProcessingType = parent.eolProcessingType
        calculationResult.calculationFormula = parent.calculationFormula
        calculationResult.unit = parent.unit
        calculationResult.calculatedUnit = parent.calculatedUnit
        calculationResult.calculationResource = parent.calculationResource
        calculationResult.localCompsCountryEnergyProfileId = parent.localCompsCountryEnergyProfileId
        calculationResult.localCompsCountryEnergyResourceId = parent.localCompsCountryEnergyResourceId
        calculationResult.localCompsCountryResourceId = parent.localCompsCountryResourceId
        calculationResult.combineId = parent.combineId
        //annie
        calculationResult.discountedResult = parent.discountedResult
        calculationResult.calculatedDiscountFactor = parent.calculatedDiscountFactor
        calculationResult.discountAndFractionalMulplierCombined = parent.discountAndFractionalMulplierCombined

        calculationResult.resultWithoutOccurrencePeriods = parent.resultWithoutOccurrencePeriods

        if (parent.resultPerOccurencePeriod) {
            calculationResult.resultPerOccurencePeriod = new HashMap<String, Double>(parent.resultPerOccurencePeriod)
        }
        //annie
        if (parent.resultWithDiscountPerOccurencePeriod) {
            calculationResult.resultWithDiscountPerOccurencePeriod = new HashMap<String, Double>(parent.resultWithDiscountPerOccurencePeriod)
        }

        if (parent.calculationResultDatasets) {
            calculationResult.calculationResultDatasets = new HashMap<String, Map<String, Object>>(parent.calculationResultDatasets)
        }

        if (parent.datasetAdditionalQuestionAnswers) {
            calculationResult.datasetAdditionalQuestionAnswers = new HashMap<String, Object>(parent.datasetAdditionalQuestionAnswers)
        }
        return calculationResult
    }

    Double getThickness() {
        Double thickness

        if (datasetAdditionalQuestionAnswers) {
            String value = datasetAdditionalQuestionAnswers.get("thickness_mm")
            boolean usingInches = false

            if (!value) {
                value = datasetAdditionalQuestionAnswers.get("thickness_in")

                if (value) {
                    usingInches = true
                }
            }

            if (value && DomainObjectUtil.isNumericValue(value)) {
                thickness = DomainObjectUtil.convertStringToDouble(value)

                if (thickness && usingInches) {
                    thickness = thickness * 25.4 as Double
                }
            }
        }
        return thickness
    }
}
