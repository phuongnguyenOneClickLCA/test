package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapper {
    static mapWith = "mongo"
    ObjectId id
    String applicationId // application, which this belongs to
    String importMapperId
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> importMessage
    String licenseKey
    List<String> compatibleEntityClasses
    List<String> questionMappingHeadings
    List<String> skipQuestionMappingValues
    List<ImportMapperQuestionRule> questionMappingRules
    List<ImportMapperDescriptiveQuestionRule> descriptiveQuestionMappingRules
    List<ImportMapperDataRule> dataMappingRules
    List<ImportMapperDescriptiveDataRule> descriptiveDataMappingRules
    List<ImportMapperDefaultRule> defaultValueRules
    List<String> compatibleIndicators
    List<String> trainingMatch
    List<String> displayFields
    List<String> classIdentifyingFields
    Integer proposeCollapseLimit
    List<String> ignoreForCollapse
    List<String> groupByForCollapse
    List<String> groupByForCollapseSpecial // to combine bands of e.g. thickness or solidity within +-50mm, also block user combine for headings
    Map<String, String> mandatoryUnitRemappings
    Integer thicknessCombinationRange
    Integer solidityCombineLimit
    Boolean allowAdaptiveRecognition
    String unitIdentifyingField // Deprecated, use unitIdentifyingFields
    List<String> unitIdentifyingFields
    String allowDroppingDataGroupedBy
    Boolean deleteFile
    Map<String, List<String>> partialQuantificationRule
    Map<String, Boolean> compositeMaterialsRule
    List<String> resourceGroups
    List<String> showProportionOfTotalsForUnitTypes
    List presetFilters
    Boolean alwaysShowAmbiguityResolver
    String arrivalPage
    String compositeSplitBySeparator
    String materialHeading
    Map<String, List<String>> resourceFilterCriteria
    String indicatorUse
    Boolean hideConfigs
    String indicator
    Boolean allowUserOwnResourceMapping
    List<String> questionMappingSpecialCases
    List<String> questionMappingSpecialCaseLookupHeading
    List<ImportMapperTranslation> questionMappingSpecialCaseTranslations
    List<ImportMapperTranslation> questionMappingClassTranslations
    String applyChannel
    Integer silentAndFastPercentage
    Long fileMaxSize // maxSize in kilobytes
    Integer skipCombineRowLimit
    List<String> excludeFromImport
    Map<String, String> securityTokens
    List<String> apiMaintainCompatibilityForUploads // for api bullshit
    List<String> allowedClassRemappings //deprecated, use allowedClassRemappingsSets
    List<ClassRemapping> allowedClassRemappingsSets
    String appHelpUrl
    String importMapperDefaultQueryId
    List<String> noDisplayFields
    List<String> allowedUnits
    Boolean showSteps
    List<Map<String, Map<String, String>>> compositeMapping
    Map<String, PluginUpdatePrompt> upgradePrompts
    Integer maxColumns
    List<ClassificationQuestionTranslation> classificationQuestionTranslations
    List<String> apiCalculationParametersSentBack // api results excel headings
    // Ticket 10112
    List<String> quantityUpdateMandatoryMatchingFields
    List<String> quantityUpdateOptionalMatchingFields

    static hasMany = [questionMappingRules: ImportMapperQuestionRule, dataMappingRules: ImportMapperDataRule,
                      descriptiveQuestionMappingRules: ImportMapperDescriptiveQuestionRule,
                      descriptiveDataMappingRules: ImportMapperDescriptiveDataRule,
                      defaultValueRules: ImportMapperDefaultRule, allowedClassRemappingsSets: ClassRemapping,
                      classificationQuestionTranslations: ClassificationQuestionTranslation]

    static embedded = [
            'questionMappingRules',
            'descriptiveQuestionMappingRules',
            'dataMappingRules',
            'descriptiveDataMappingRules',
            'defaultValueRules',
            'questionMappingSpecialCaseTranslations',
            'questionMappingClassTranslations',
            'allowedClassRemappingsSets',
            'upgradePrompts',
            'classificationQuestionTranslations'
    ]

}
