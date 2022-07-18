/*
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class Application implements Serializable, Validateable {
    static mapWith = "mongo"

    ObjectId id
    @Translatable
    Map name
    String licenceKey
    String applicationId
    List<Denominator> denominators
    List<ResultCategory> resultCategories
    List<CalculationRule> calculationRules
    List<ObjectId> applicationEditorIds
    String importTime
    String importFile
    List<ImportMapper> importMappers
    List<ResourceFilterRule> filterRules
    List<DefaultValue> defaultValues
    List<ApplicationResultFormatting> resultFormattings // TODO DEPRECATED AND TO BE REMOVED
    List resourceExtraInformationSets
    List expandFeatureSets
    List<String> importMapperWarns //TODO deprecated
    List<String> importMapperDiscards //TODO deprecated
    List<SystemTrainingDataSet> systemTrainingDatas //TODO deprecated
    Map<String, Map<String, String>> helpPageBullets
    String helpPageText
    Map<String, String> exportedDataDomains

    List<ApplicationIndicatorDefinitions> indicatorCoreSettings
    List<IndicatorExpandFeature> indicatorReportGeneration
    List<QueryFilter> defaultFilters
    ReportGenerationRule reportGenerationRules
    List<ReportGenerationRule> reportGenerationRuleSets
    List<ReportConditionalRemoval> reportConditionalRemovalRules
    Map<String, List<ReportGenerationRuleContent>> contentInjectionSets // Map< setId , list of injection rules >
    List<SpecialRule> specialRules
    List<PredefinedScope> predefinedScopes
    List<String> privateDataSelfDeclarationAllowedDataProperties // 12009 task
    List<ValueReference> showDataReferences

    List<ResourcePurpose> resourcePurposes // Task 12248, for LCA APP
    List<APIDataProvision> apiDataProvisions
    //GLA discounting
    List<DiscountScenario> discountScenarios

    // Map <object's attribute, List<Map< string to be replaced, new string>>>
    Map<String, List<Map<String, String>>> updateStringOnImport

    //NMD
    Map<String, String> nmdUnitToOCL
    Map<String, String> nmdFaseIdToOCLStages
    Map<String, String> nmdToepassingsIdToApplication
    Map<String, String> nmdMillieuCategoryIdToOCLImpactName
    List<String> nmdElementTypes

    static hasMany = [configurations           : Configuration, targetedLicences: License, denominators: Denominator,
                      resultCategories         : ResultCategory, calculationRules: CalculationRule, importMappers: ImportMapper,
                      filterRules              : ResourceFilterRule, systemTrainingDatas: SystemTrainingDataSet, indicatorCoreSettings: ApplicationIndicatorDefinitions,
                      indicatorReportGeneration: IndicatorExpandFeature, defaultFilters: QueryFilter, defaultValues: DefaultValue, specialRules: SpecialRule,
                      reportGenerationRuleSets : ReportGenerationRule, predefinedScopes: PredefinedScope, showDataReferences: ValueReference, resourcePurposes: ResourcePurpose,
                      apiDataProvisions        : APIDataProvision]

    static constraints = {
        name nullable: true
        targetedLicences nullable: true
        licenceKey nullable: true
        configurations nullable: true
        applicationId nullable: false
        denominators nullable: true
        resultCategories nullable: true
        calculationRules nullable: true
        applicationEditorIds nullable: true
        importTime nullable: true
        importFile nullable: true
        importMappers nullable: true
        filterRules nullable: true
        defaultValues nullable: true
        resultFormattings nullable: true
        resourceExtraInformationSets nullable: true
        expandFeatureSets nullable: true
        importMapperWarns nullable: true
        importMapperDiscards nullable: true
        systemTrainingDatas nullable: true
        helpPageBullets nullable: true
        helpPageText nullable: true
        indicatorCoreSettings nullable: true
        indicatorReportGeneration nullable: true
        defaultFilters nullable: true
        reportGenerationRules nullable: true
        exportedDataDomains nullable: true
        specialRules nullable: true
        reportGenerationRuleSets nullable: true
        predefinedScopes nullable: true
        privateDataSelfDeclarationAllowedDataProperties nullable: true
        showDataReferences nullable: true
        resourcePurposes nullable: true
        apiDataProvisions nullable: true
        contentInjectionSets nullable: true
        discountScenarios nullable: true
        updateStringOnImport nullable: true
        nmdUnitToOCL nullable: true
        nmdFaseIdToOCLStages nullable: true
        nmdToepassingsIdToApplication nullable: true
        nmdMillieuCategoryIdToOCLImpactName nullable: true
        nmdElementTypes nullable: true
    }


    static embedded = [
            'denominators',
            'resultCategories',
            'calculationRules',
            'importMappers',
            'filterRules',
            'defaultValues',
            'resultFormattings',
            'resourceExtraInformationSets',
            'expandFeatureSets',
            'systemTrainingDatas',
            'indicatorCoreSettings',
            'indicatorReportGeneration',
            'defaultFilters',
            'reportGenerationRules',
            'specialRules',
            'reportGenerationRuleSets',
            'predefinedScopes',
            'showDataReferences',
            'resourcePurposes',
            'apiDataProvisions',
            'reportConditionalRemovalRules',
            'contentInjectionSets',
            'discountScenarios'
    ]

    static mapping = {
        version false
    }
}
