/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.validation.Validateable
import groovy.transform.CompileStatic
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Indicator implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String indicatorId
    String indicatorUse // can be "operation" or "design"
    String importTime
    String importFile
    String assessmentMethod
    String assessmentType
    ValueReference assessmentPeriodValueReference
    Double assessmentPeriodFixed
    String displayResult
    List<String> additionalPortfolioCalculationRules // shown in addition to displayResult
    Boolean active
    @Translatable
    Map<String, String> name
    @Translatable
    Map<String, String> shortName
    Map<String, String> unit // TODO deprecated
    @Translatable
    Map<String, String> purpose
    @Translatable
    Map<String, String> resultsHeadline
    @Translatable
    Map<String, String> resultReportLinkText
    List<IndicatorQuery> indicatorQueries
    Map<String, List<String>> hideQuestions
    List<CalculationRule> calculationRules
    List<ResultCategory> resultCategories
    List<String> compatibleEntityTypes
    List<String> compatibleEntityClasses
    List<String> requiredEnvironmentDataSourceStandards
    Double performancePenaltyMultiplier
    Boolean showDecimals // TODO deprecated
    Integer significantDigits // TODO deprecated
    Integer minimumZeros // TODO deprecated
    String rounding // TODO deprecated
    Double multiplier
    Integer useScientificNumbers // TODO deprecated
    AppendQuestionIdToDisplayResult appendQuestionIdToDisplayResult
    List<ResourceImportParameter> importParameters
    List<Denominator> denominatorList
    Boolean showAverageInsteadOfTotal
    List<String> relatedMainEntityQueries
    Boolean hideZeroResults // This is to hide zero calculationTotalResults in entity main page
    IndicatorExportContent exportContent
    ResultFormatting resultFormatting
    Boolean nonNumericResult
    Boolean allowMonthlyData
    String compareCalculationRule
    IndicatorReport report
    Boolean enableExport
    Boolean evaluateNonResourceAnswers
    Map<String, Map> sourceListing
    Map<String, Map> portfolioSourceListing
    Boolean addableCategories
    Boolean hideResultCategoryLabels
    List<String> resourceExtraInformation
    TreeMap<String, List<String>> applicationDenominators
    TreeMap<String, List<String>> applicationRules
    TreeMap<String, List<String>> applicationCategories
    TreeMap<String, List<String>> applicationResultFormatting // TODO DEPRECATED AND TO BE REMOVED
    List applicationResourceExtraInformation // TODO DEPRECATED PLS USE indicatorDefaults
    ValueReference widgetTemplateUrl
    Boolean allowAverageInPortfolio
    List<String> queriesForSourceListing
    Boolean showDiscountRates // TODO DEPRICATE THIS, WHEN THE NEW LCC IS USED EVERYWHERE
    Boolean deprecated
    String exportType //  "PDF" tai "Excel" tai "HTML"
    ValueReference exportTemplate
    List<PdfReplacementSetting> pdfReplacementSettings
    List<ValueReference> exportSources
    Boolean exportResultTables
    IndicatorExpandFeature expandFeatures
    Map<String, List<String>> applicationDefaults
    Boolean displayDesignResultCompare
    List<String> reportVisualisation
    String exportXML
    ValueReference rsetSource
    ValueReference fecEpdSource
    Map<String, String> applicationExpandFeatures
    List<IndicatorXmlMapper> readValuesFromXML
    String wooCommerceProductId
    Boolean requireMonthly
    String indicatorHelpUrl
    String connectedBenchmark // DEPRECATED, USE connectedBenchmarks
    List<String> connectedBenchmarks
    Boolean multiDataWarning
    ValueReference labourCostCraftsmanValueReference
    ValueReference labourCostWorkerValueReference
    ValueReference countryIndexValueReference
    ValueReference currencyExchangeValueReference
    Boolean hideSourceListing
    Boolean hideExpandAndCollapse
    Boolean handleVirtualLast
    Map<String, List<String>> wordReportGeneration
    Boolean breeamTypeExcelReport
    List<String> excelExportCategoryIds
    List<String> excelExportRuleIds // limit the rules that get exported 14018
    String visibilityStatus
    List <String> graphResultCategories
    List <String> graphCalculationRules
    String excelExportName
    Map<String, String> indicatorDefaults  //should always have applicationId and coreSettings and/or reportGeneration
    List<String> allowedCarbonDesignerRegions  //Early Phases - Simulation Tool
    Boolean supressDataFilterWarnings
    Boolean useSpecificServiceLifes // RESOURCES DO NOT USE DEFAULT SERVICELIFE FROM SUBTYPE INSTEAD RESOURCES SERVICELIFE
    Boolean hideSectionResult // hides carbon impact data for all query sections
    List<String> expandFeatureCategoryIds // to limit detailed report resultCategories by Id
    Boolean enableLcaChecker
    List<String> compatibleIndicatorIds // This tool is ONLY compatible with the listed indicatorIds and should not be allowed to be chosen together with any other tools
    List<String> portfolioResultCategories // for renderPortFolioResults in portfoliotaglib
    Boolean exportToRevitBenchmark // to be dumped for revit
    Boolean isResourceAggregationDisallowed // if TRUE the same resource will NOT be merged in the result list

    Boolean useNewLifeCycleModel // if true = calculates results with newCalculationService

    Date deprecatedDate
    List<String> certificationImageResourceId
    Boolean activateCostCalculation
    Boolean enableCarbonHeroesBenchmarks
    String groupingClassificationParamId
    Map<String, Map<String, String>> licensedNamequalifiers

    // Planetary
    Integer maxRowLimitPerDesign
    Boolean preventCopyingQueryContentsFromIndicator
    Boolean preventPrivateData
    Boolean showOnlyLicensedRules
    Boolean isPlanetaryIndicator
    Map<String,Map<String,String>> renameReportVisualisation
    BenchmarkSettings benchmarkSettings
    Boolean allowIlcdXmlExport

    // Private classifications
    Boolean allowOrganisationSpecificClassification // TODO: deprecate, check the existence in classificationsMap
    Boolean showAlternativeClassification // TODO: deprecate, check the existence in classificationsMap
    Integer privateClassificationQuestionSequenceNr // TODO: deprecate, can use same sequence number as default class
    List<String> classificationsMap // classification and its weight, the position of the classification determine its priority EG:['defaultClassificationName','alternativeClassName','organizationSpecificClassification']
    @Translatable
    Map<String, String> privateClassificationHelp
    String sendPrivateDataType

    //deprecation
    Map<String , String> deprecationNote

    //GLA Discount
    Map<String, Object> indicatorDiscountScenarios
    boolean futureDiscountingEnabled
    //16692 To calculate default result for existing design
    List<String> calculateByQueryDefaultIfAbsent
    //ValueReference userSelectedScenario

    //Nmd 3
    Boolean showConstructionImpact
    Boolean applyNmdScaling

    Boolean preventGrouping
    Boolean preventComparing

    //result summary - 15697
    Boolean preventResultSummaryDownload
    String massRuleId

    // allow editing a construction constituent quantity, please use allowEditingConstituentQuantity transient
    Boolean allowConstituentQuantityEdit

    //REL2108-70
    List<String> excludeAdditionalQuestionsInGroupEdit

    //sw-763 average designs
    Boolean allowCreatingAverageDesign
    Map<String, Object> epdHubExportContent
    Boolean allowXmlExport_BETIE

    // RE2020
    Boolean iniesDataRequestAPI

    static hasMany = [denominatorList : Denominator, resultCategories: ResultCategory, calculationRules: CalculationRule,
                      indicatorQueries: IndicatorQuery, importParameters: ResourceImportParameter]

    static embedded = [
            "indicatorQueries",
            "calculationRules",
            "resultCategories",
            "appendQuestionIdToDisplayResult",
            "resultFormatting",
            "indicatorDenominators",
            "denominatorList",
            "assessmentPeriodValueReference",
            "importParameters",
            "exportContent",
            "report",
            "expandFeatures",
            "widgetTemplateUrl",
            "exportTemplate",
            "pdfReplacementSettings",
            "readValuesFromXML",
            "labourCostCraftsmanValueReference",
            "labourCostWorkerValueReference",
            "countryIndexValueReference",
            "currencyExchangeValueReference",
            "wordReportGeneration",
            "benchmarkSettings"
    ]

    static constraints = {
        showDiscountRates nullable: true
        indicatorQueries nullable: true
        hideQuestions nullable: true
        calculationRules nullable: true
        resultCategories nullable: true
        indicatorUse inList: ["operating", "design", "report", "compoundReportTemplate"]
        active nullable: true
        importTime nullable: true
        importFile nullable: true
        assessmentMethod nullable: true
        assessmentType nullable: true
        assessmentPeriodValueReference nullable: true
        assessmentPeriodFixed nullable: true
        purpose nullable: true
        unit nullable: true
        resultsHeadline nullable: true
        showDecimals nullable: true
        significantDigits nullable: true
        minimumZeros nullable: true
        rounding nullable: true
        multiplier nullable: true
        compatibleEntityTypes nullable: true
        compatibleEntityClasses nullable: true
        requiredEnvironmentDataSourceStandards nullable: true
        performancePenaltyMultiplier nullable: true
        displayResult nullable: true
        useScientificNumbers nullable: true
        appendQuestionIdToDisplayResult nullable: true
        importParameters nullable: true
        shortName nullable: true
        showAverageInsteadOfTotal nullable: true
        relatedMainEntityQueries nullable: true
        hideZeroResults nullable: true
        exportContent nullable: true
        resultReportLinkText nullable: true
        resultFormatting nullable: true
        nonNumericResult nullable: true
        allowMonthlyData nullable: true
        denominatorList nullable: true
        report nullable: true
        compareCalculationRule nullable: true
        evaluateNonResourceAnswers nullable: true
        enableExport nullable: true
        sourceListing nullable: true
        addableCategories nullable: true
        hideResultCategoryLabels nullable: true
        resourceExtraInformation nullable: true
        applicationDenominators nullable: true
        applicationRules nullable: true
        applicationCategories nullable: true
        applicationResultFormatting nullable: true
        applicationResourceExtraInformation nullable: true
        widgetTemplateUrl nullable: true
        allowAverageInPortfolio nullable: true
        portfolioSourceListing nullable: true
        queriesForSourceListing nullable: true
        deprecated nullable: true
        exportType nullable: true
        exportTemplate nullable: true
        exportResultTables nullable: true
        displayDesignResultCompare nullable: true
        expandFeatures nullable: true
        pdfReplacementSettings nullable: true
        applicationDefaults nullable: true
        reportVisualisation nullable: true
        exportXML nullable: true
        rsetSource nullable: true
        applicationExpandFeatures nullable: true
        readValuesFromXML nullable: true
        wooCommerceProductId nullable: true
        requireMonthly nullable: true
        indicatorHelpUrl nullable: true
        connectedBenchmark nullable: true
        multiDataWarning nullable: true
        labourCostCraftsmanValueReference nullable: true
        labourCostWorkerValueReference nullable: true
        countryIndexValueReference nullable: true
        indicatorDefaults nullable: true
        currencyExchangeValueReference nullable: true
        hideSourceListing nullable: true
        hideExpandAndCollapse nullable: true
        fecEpdSource nullable: true
        handleVirtualLast nullable: true
        wordReportGeneration nullable: true
        breeamTypeExcelReport nullable: true
        excelExportCategoryIds nullable: true
        excelExportRuleIds nullable: true
        visibilityStatus nullable: true
        graphResultCategories nullable: true
        graphCalculationRules nullable: true
        excelExportName nullable: true
        additionalPortfolioCalculationRules nullable: true
        allowedCarbonDesignerRegions nullable: true
        supressDataFilterWarnings nullable: true
        useSpecificServiceLifes nullable: true
        hideSectionResult nullable: true
        expandFeatureCategoryIds nullable: true
        enableLcaChecker nullable: true
        deprecatedDate nullable: true
        compatibleIndicatorIds nullable: true
        connectedBenchmarks nullable: true
        useNewLifeCycleModel nullable: true
        certificationImageResourceId nullable: true
        portfolioResultCategories nullable: true
        exportToRevitBenchmark nullable: true
        isResourceAggregationDisallowed nullable: true
        activateCostCalculation nullable: true
        enableCarbonHeroesBenchmarks nullable: true
        groupingClassificationParamId nullable: true
        maxRowLimitPerDesign nullable: true
        preventCopyingQueryContentsFromIndicator nullable: true
        preventPrivateData nullable: true
        licensedNamequalifiers nullable: true
        showOnlyLicensedRules nullable: true
        renameReportVisualisation nullable: true
        isPlanetaryIndicator nullable: true
        benchmarkSettings nullable: true
        allowOrganisationSpecificClassification nullable: true
        showAlternativeClassification nullable: true
        privateClassificationQuestionSequenceNr nullable: true
        privateClassificationHelp nullable: true
        allowIlcdXmlExport nullable: true
        sendPrivateDataType nullable: true
        classificationsMap nullable: true
        deprecationNote nullable: true
        futureDiscountingEnabled nullable: true
        indicatorDiscountScenarios nullable: true
        //userSelectedScenario nullable: true
        showConstructionImpact nullable: true
        preventResultSummaryDownload nullable: true
        calculateByQueryDefaultIfAbsent nullable: true
        massRuleId nullable: true
        allowConstituentQuantityEdit nullable: true
        excludeAdditionalQuestionsInGroupEdit nullable: true
        applyNmdScaling nullable: true
        preventGrouping nullable: true
        preventComparing nullable: true
        allowCreatingAverageDesign nullable: true
        iniesDataRequestAPI nullable: true
        epdHubExportContent nullable: true
        allowXmlExport_BETIE nullable: true

    }

    static mapping = {
        compoundIndex indicatorId: 1, active: 1
        indicatorId index: true
        version false
    }

    static transients = [
            "queries",
            "denominatorName",
            "displayDenominator",
            "dynamicDenominators",
            "nonDynamicDenominators",
            "dynamicDenominatorList",
            "nonDynamicDenominatorList",
            "compareCalculationRuleObject",
            "denominatorListAsDenominatorObjects",
            "resolveDenominators",
            "applicationDefaultObjects",
            "useThicknessInInches",
            "extraInformationObjects",
            "resolveResourceExtraInformationInfoBubble",
            "resolveResourceExtraInformationDropdown",
            "resolveResourceExtraInformationResultPage",
            "resolveResourceExtraInformationBgReport",
            "resolveExpandFeature",
            "pieGraphHeading",
            "nameStringWithoutQuotes",
            "resolveDatasetExtraInformationResultPage",
            "resolveDatasetExtraInformationBgReport",
            "resolveApplicationResourceExtraInformationSet",
            "isCostCalculationAllowed",
            "isUsingLCAParameters",
            "indicatorHelpUrlTranslation",
            "allowEditingConstituentQuantity"
    ]

    transient queryService
    transient denominatorUtil
    transient applicationService
    transient indicatorBenchmarkService
    transient optimiStringUtils
    transient entityService
    transient licenseService
    transient localizedLinkService

    def getResolveExpandFeature() {
        IndicatorExpandFeature indicatorExpandFeature

        if (applicationExpandFeatures) {
            Application application = applicationService.getApplicationByApplicationId(applicationExpandFeatures.keySet().toList().get(0))
            def appId = applicationExpandFeatures.keySet().toList().get(0)
            def setName = applicationExpandFeatures.get(appId)

            if (application && setName) {
                indicatorExpandFeature = applicationService.getExpandFeatureByName(setName, application.expandFeatureSets)
            }

        } else if (expandFeatures) {
            indicatorExpandFeature = expandFeatures
        }
        return indicatorExpandFeature
    }

    def getExtraInformationObjects() {
        List<IndicatorResourceExtraInformation> indicatorResourceExtraInformation = []

        applicationResourceExtraInformation?.each {
            indicatorResourceExtraInformation.add(it as IndicatorResourceExtraInformation)
        }
        return indicatorResourceExtraInformation
    }

    // new configuration for data cards from November 2020.
    def getResolveResourceExtraInformationInfoBubble() {
        List<DataCardSection> resolvedSourceListing = []
        String applicationId = indicatorDefaults?.get("applicationId")
        String extraInfoType = indicatorDefaults?.get("infoBubbleInfo")

        if (indicatorDefaults && extraInfoType && applicationId) {
            def application = Application.collection.findOne([applicationId: applicationId], [resourceExtraInformationSets: 1])
            ApplicationResourceExtraInformationSet appSourceListing

            if (application && application.resourceExtraInformationSets) {
                for (Map resourceExtraInformationSet in (application.resourceExtraInformationSets as List<Map>)) {
                    if (resourceExtraInformationSet?.resourceInformationId && extraInfoType.contains(resourceExtraInformationSet.resourceInformationId.toString())) {
                        appSourceListing = resourceExtraInformationSet as ApplicationResourceExtraInformationSet
                        resolvedSourceListing = appSourceListing.resourceInformation ?: []
                        break
                    }
                }
            }
        }
        return resolvedSourceListing
    }

    def getResolveResourceExtraInformationDropdown() {
        List<String> resolvedExtraInformations = []
        String applicationId = indicatorDefaults?.get("applicationId")
        String extraInfoType = indicatorDefaults?.get("resourceDropDownInfo")

        if (indicatorDefaults && extraInfoType && applicationId) {
            def application = Application.collection.findOne([applicationId: applicationId], [resourceExtraInformationSets: 1])

            if (application) {
                ApplicationResourceExtraInformationSet appSourceListing

                if (application.resourceExtraInformationSets) {
                    for (Map resourceExtraInformationSet in (application.resourceExtraInformationSets as List<Map>)) {
                        if (resourceExtraInformationSet?.name && extraInfoType.contains(resourceExtraInformationSet.name.toString())) {
                            appSourceListing = resourceExtraInformationSet as ApplicationResourceExtraInformationSet
                            break
                        }
                    }
                }

                if (appSourceListing?.resourceExtraInformation) {
                    resolvedExtraInformations.addAll(appSourceListing.resourceExtraInformation.keySet().toList())
                }
            }
        } else if (applicationResourceExtraInformation) {
            List<IndicatorResourceExtraInformation> objectsList = getExtraInformationObjects()
            objectsList?.each { IndicatorResourceExtraInformation extraInformation ->

                if ("resourceDropDown".contains(extraInformation?.usage)) {
                    def application = Application.collection.findOne([applicationId: extraInformation.applicationExtraInformation.keySet().toList().get(0)], [resourceExtraInformationSets: 1])
                    def appId = extraInformation.applicationExtraInformation.keySet().toList().get(0)
                    def extraInformationSetName = extraInformation.applicationExtraInformation.get(appId)

                    if (application) {
                        ApplicationResourceExtraInformationSet appSourceListing

                        if (application.resourceExtraInformationSets) {
                            for (Map resourceExtraInformationSet in (application.resourceExtraInformationSets as List<Map>)) {
                                if (resourceExtraInformationSet?.name && extraInformationSetName.contains(resourceExtraInformationSet.name.toString())) {
                                    appSourceListing = resourceExtraInformationSet as ApplicationResourceExtraInformationSet
                                    break
                                }
                            }
                        }

                        if (appSourceListing) {
                            resolvedExtraInformations.addAll(appSourceListing.resourceExtraInformation.keySet().toList())
                        }
                    }
                }
            }
        }

        if (this.resourceExtraInformation) {
            resolvedExtraInformations.addAll(this.resourceExtraInformation)
        }
        return resolvedExtraInformations
    }

    def getResolveResourceExtraInformationResultPage() {
        Map<String, Map> resolvedSourceListing = new TreeMap<String, Map>()
        String applicationId = indicatorDefaults?.get("applicationId")
        String extraInfoType = indicatorDefaults?.get("resultPageInfo")

        if (indicatorDefaults && extraInfoType && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInfoType, application.resourceExtraInformationSets)

                if (appSourceListing) {
                    resolvedSourceListing.putAll(appSourceListing.resourceExtraInformation)
                }
            }
        } else if (applicationResourceExtraInformation) {
            List<IndicatorResourceExtraInformation> objectsList = getExtraInformationObjects()
            objectsList?.each { IndicatorResourceExtraInformation extraInformation ->

                if ("resultPage".contains(extraInformation?.usage)) {
                    Application application = applicationService.getApplicationByApplicationId(extraInformation.applicationExtraInformation.keySet().toList().get(0))
                    def appId = extraInformation.applicationExtraInformation.keySet().toList().get(0)
                    def extraInformationSetName = extraInformation.applicationExtraInformation.get(appId)

                    if (application) {
                        ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInformationSetName, application.resourceExtraInformationSets)

                        if (appSourceListing) {
                            resolvedSourceListing.putAll(appSourceListing.resourceExtraInformation)
                        }
                    }
                }
            }
        }

        if (this.sourceListing) {
            resolvedSourceListing.putAll(this.sourceListing)
        }
        return resolvedSourceListing
    }

    def getResolveResourceExtraInformationBgReport() {
        Map<String, Map> resolvedSourceListing = new TreeMap<String, Map>()
        String applicationId = indicatorDefaults?.get("applicationId")
        String filteredDatasourcesTable = indicatorDefaults?.get("filteredDatasourcesTable")

        if (indicatorDefaults && filteredDatasourcesTable && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(filteredDatasourcesTable, application.resourceExtraInformationSets)

                if (appSourceListing) {
                    resolvedSourceListing.putAll(appSourceListing.resourceExtraInformation)
                }
            }
        }
        if (this.sourceListing) {
            resolvedSourceListing.putAll(this.sourceListing)
        }
        return resolvedSourceListing
    }

    def getResolveDatasetExtraInformationBgReport() {
        List<String> resolvedSourceListingAdditionalQuestions = []
        String applicationId = indicatorDefaults?.get("applicationId")
        String filteredDatasourcesTable = indicatorDefaults?.get("filteredDatasourcesTable")

        if (indicatorDefaults && filteredDatasourcesTable && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(filteredDatasourcesTable, application.resourceExtraInformationSets)

                if (appSourceListing?.addToDataSources) {
                    resolvedSourceListingAdditionalQuestions.addAll(appSourceListing.addToDataSources)
                }
            }
        }
        return resolvedSourceListingAdditionalQuestions
    }

    def getResolveDatasetExtraInformationResultPage() {
        List<String> resolvedSourceListingAdditionalQuestions = []
        String applicationId = indicatorDefaults?.get("applicationId")
        String extraInfoType = indicatorDefaults?.get("resultPageInfo")

        if (indicatorDefaults && extraInfoType && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInfoType, application.resourceExtraInformationSets)

                if (appSourceListing?.addToDataSources) {
                    resolvedSourceListingAdditionalQuestions.addAll(appSourceListing.addToDataSources)
                }
            }
        } else if (applicationResourceExtraInformation) {
            List<IndicatorResourceExtraInformation> objectsList = getExtraInformationObjects()
            objectsList?.each { IndicatorResourceExtraInformation extraInformation ->

                if ("resultPage".contains(extraInformation?.usage)) {
                    Application application = applicationService.getApplicationByApplicationId(extraInformation.applicationExtraInformation.keySet().toList().get(0))
                    def appId = extraInformation.applicationExtraInformation.keySet().toList().get(0)
                    def extraInformationSetName = extraInformation.applicationExtraInformation.get(appId)

                    if (application) {
                        ApplicationResourceExtraInformationSet appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInformationSetName, application.resourceExtraInformationSets)

                        if (appSourceListing?.addToDataSources) {
                            resolvedSourceListingAdditionalQuestions.addAll(appSourceListing.addToDataSources)
                        }
                    }
                }
            }
        }
        return resolvedSourceListingAdditionalQuestions
    }

    def getResolveApplicationResourceExtraInformationSet() {
        ApplicationResourceExtraInformationSet appSourceListing
        String applicationId = indicatorDefaults?.get("applicationId")
        String extraInfoType = indicatorDefaults?.get("resultPageInfo")

        if (indicatorDefaults && extraInfoType && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInfoType, application.resourceExtraInformationSets)
            }
        } else if (applicationResourceExtraInformation) {
            List<IndicatorResourceExtraInformation> objectsList = getExtraInformationObjects()

            for (IndicatorResourceExtraInformation extraInformation in objectsList) {
                if ("resultPage".contains(extraInformation?.usage)) {
                    Application application = applicationService.getApplicationByApplicationId(extraInformation.applicationExtraInformation.keySet().toList().get(0))
                    def appId = extraInformation.applicationExtraInformation.keySet().toList().get(0)
                    def extraInformationSetName = extraInformation.applicationExtraInformation.get(appId)

                    if (application) {
                        appSourceListing = applicationService.getExtraInformationSetObjectsByName(extraInformationSetName, application.resourceExtraInformationSets)
                        break
                    }
                }
            }
        }
        return appSourceListing
    }

    def getUseThicknessInInches() {
        Boolean use = false

        List<IndicatorQuery> indicatorQueriesWithAdditionalQuestions = indicatorQueries?.findAll({
            it.additionalQuestionIds
        })

        if (indicatorQueriesWithAdditionalQuestions) {
            for (IndicatorQuery indicatorQuery in indicatorQueriesWithAdditionalQuestions) {
                if (indicatorQuery.additionalQuestionIds.contains("thickness_in")) {
                    use = true
                    break
                }
            }
        }
        return use
    }

    def getResolveResultCategories(Entity parentEntity) {
        List<ResultCategory> resultCategories = []
        String lcaModel = parentEntity?.lcaModel

        if (applicationCategories) {
            List<Document> applicationsWithResultCategories = applicationService.getApplicationsAsDocumentsByApplicationIdList(applicationCategories*.key, [applicationId: 1, resultCategories: 1])

            applicationCategories.each { String applicationId, List<String> categoryIds ->
                List<Map> applicationCategories = applicationsWithResultCategories?.find{it.applicationId == applicationId }?.resultCategories

                if (applicationCategories) {
                    List<ResultCategory> appCategories = []

                    if (categoryIds) {
                        applicationCategories.each { Map resultCategory ->
                            String categoryId = (resultCategory.get("resultCategoryId"))

                            if (categoryIds.find({ it == categoryId })) {
                                appCategories.add(resultCategory as ResultCategory)
                            }
                        }
                    }

                    if (!appCategories.isEmpty()) {
                        resultCategories.addAll(appCategories)
                    }
                }
            }
        }

        this.resultCategories?.each { ResultCategory resultCategory ->
            if (!resultCategories?.find({ resultCategory.resultCategoryId.equals(it.resultCategoryId) })) {
                resultCategories.add(resultCategory)
            }
        }
        List<ResultCategory> resolvedResultCategories = []
        resultCategories.each { ResultCategory resultCategory ->
            if (resultCategory.compatibleLcaModels) {
                if (resultCategory.compatibleLcaModelsIndicatorIds == null) {
                    resolvedResultCategories.add(resultCategory)
                } else {
                    if (resultCategory.compatibleLcaModelsIndicatorIds.isEmpty()||resultCategory.compatibleLcaModelsIndicatorIds.contains(indicatorId)) {
                        if (lcaModel && resultCategory.compatibleLcaModels.contains(lcaModel)) {
                            resolvedResultCategories.add(resultCategory)
                        }
                    } else {
                        resolvedResultCategories.add(resultCategory)
                    }
                }
            } else {
                resolvedResultCategories.add(resultCategory)
            }
        }
        return resolvedResultCategories
    }

    def getResolveCalculationRules(Entity parentEntity, Boolean skipLicenseKeyCheck = false) {
        List<String> checkLicensedFeatures = []
        List<CalculationRule> calculationRules = []

        if (applicationRules) {

            List<Document> applicationsWithCalculationRules = applicationService.getApplicationsAsDocumentsByApplicationIdList(applicationRules*.key, [applicationId: 1, calculationRules: 1])

            applicationRules.each { String applicationId, List<String> ruleIds ->
                List<Map> applicationRules = applicationsWithCalculationRules?.find{it.applicationId == applicationId }?.calculationRules

                if (applicationRules) {
                    List<CalculationRule> appRules = []

                    ruleIds?.each { String rule ->
                        CalculationRule calculationRule = applicationRules.find({rule.equals(it.calculationRuleId)}) as CalculationRule

                        if (!calculationRule) {
                            calculationRule = applicationRules.find({ rule.equals(it.calculationRule) }) as CalculationRule
                        }
                        
                        if (calculationRule) {
                            if (calculationRule.licenseKey && !checkLicensedFeatures.contains(calculationRule.licenseKey)) {
                                checkLicensedFeatures.add(calculationRule.licenseKey)
                            }
                            appRules.add(calculationRule)
                        }
                    }

                    if (!appRules.isEmpty()) {
                        calculationRules.addAll(appRules)
                    }
                }
            }

        }

        this.calculationRules?.each { CalculationRule calculationRule ->
            if (!calculationRules?.find({ calculationRule.calculationRule.equals(it.calculationRule) })) {
                if (calculationRule.licenseKey && !checkLicensedFeatures.contains(calculationRule.licenseKey)) {
                    checkLicensedFeatures.add(calculationRule.licenseKey)
                }
                calculationRules.add(calculationRule)
            }
        }

        if (!checkLicensedFeatures.isEmpty() && !skipLicenseKeyCheck && showOnlyLicensedRules) {
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, checkLicensedFeatures, null, true, true)
            calculationRules = calculationRules.findAll({!it.licenseKey||featuresAllowed.get(it.licenseKey)})
        }
        return calculationRules
    }

    def getResolveDenominators() {
        List<Denominator> denominators = []

        if (applicationDenominators) {
            applicationDenominators.each { String applicationId, List<String> denominatorIds ->
                List<Map> appDenoms = applicationService.getApplicationAsDocumentByApplicationId(applicationId, [denominators: 1])?.denominators

                if (appDenoms) {
                    denominatorIds.each { String denominatorId ->
                        Denominator denominator = appDenoms.find({denominatorId.equals(it.denominatorId)}) as Denominator

                        if (denominator) {
                            denominators.add(denominator)
                        }
                    }
                }
            }
        }

        if (this.denominatorList) {
            denominators.addAll(this.denominatorList)
        }
        return denominators
    }

    def getQueries(Entity entity = null, Boolean sortByIndicatorQuery = Boolean.FALSE) {
        List<Query> queries = queryService.getQueriesByIndicatorAndEntity(this, entity)

        return sortByIndicatorQuery ? sortQueriesByIndicatorQuery(queries) : queries
    }

    List<Query> sortQueriesByIndicatorQuery(List<Query> queries) {

        List<Query> sortedQueries = []

        if (queries) {
            indicatorQueries?.each { IndicatorQuery ind ->
                Query query = queries.find({ it.queryId?.equals(ind.queryId) })

                if (query) {
                    sortedQueries.add(query)
                }
            }
        }
        return sortedQueries
    }

    // TODO: one usage left, inside the Entity.isIndicatorReady. After adjustment should be deleted
    List<String> getQueryIds(Entity entity) {
        return queryService.getQueryIdsByIndicatorAndEntity(this, entity)
    }

    def getDenominatorName(queryId, sectionId) {
        Query query = queryService.getQueryByQueryId(queryId, true)
        QuerySection section = query?.sections?.find({ it.sectionId == sectionId })

        if (!section) {
            section = query?.includedSections?.find({ it.sectionId == sectionId })
        }

        if (section) {
            return section.localizedName
        } else {
            return ""
        }
    }

    def getDisplayDenominator() {
        List<Denominator> denominators = resolveDenominators
        Denominator denominator

        if (denominators) {
            denominator = denominators.find({ "displayDenominator".equals(it.denominatorType) })
        }
        return denominator
    }

    def getDenominatorListAsDenominatorObjects() {
        List<Denominator> denominators = new ArrayList<Denominator>()
        Denominator denominator

        resolveDenominators?.each {
            denominator = (Denominator) it

            if (!"overallDenominator".equals(denominator.denominatorType)) {
                denominators.add(denominator)
            }
        }
        return denominators
    }

    def getDynamicDenominatorList() {
        List<Denominator> denominators = []
        Denominator denominator

        resolveDenominators?.each {
            denominator = (Denominator) it

            if ("dynamicDenominator".equals(denominator.denominatorType)) {
                denominators.add(denominator)
            }
        }
        return denominators
    }

    def getNonDynamicDenominatorList() {
        List<Denominator> denominators = []
        Denominator denominator

        resolveDenominators?.each {
            denominator = (Denominator) it

            if (!"dynamicDenominator".equals(denominator.denominatorType) &&
                    !"overallDenominator".equals(denominator.denominatorType)) {
                denominators.add(denominator)
            }
        }
        return denominators
    }

    def isMonthlyInputEnabledForAnyQuery(Entity entity) {
        boolean enabled = false
        def queryIds = indicatorQueries?.collect({ it.queryId })

        if (queryIds) {
            for (String queryId : queryIds) {
                if (entity.monthlyInputEnabledByQuery?.get(queryId)) {
                    enabled = true
                    break
                }
            }
        }
        return enabled
    }

    def isQuarterlyInputEnabledForAnyQuery(Entity entity) {
        boolean enabled = false
        def queryIds = indicatorQueries?.collect({ it.queryId })

        if (queryIds) {
            for (String queryId : queryIds) {
                if (entity.quarterlyInputEnabledByQuery?.get(queryId)) {
                    enabled = true
                    break
                }
            }
        }
        return enabled
    }

    def isQueryOptional(String queryId) {
        boolean optional = false

        if (indicatorQueries?.find({ queryId.equals(it.queryId) })?.optional) {
            optional = true
        }
        return optional
    }

    def getCompareCalculationRuleObject() {
        CalculationRule calculationRule

        if (compareCalculationRule) {
            calculationRule = getResolveCalculationRules(null)?.find({ compareCalculationRule.equals(it.calculationRule) })
        }
        return calculationRule
    }

    /*def getGraphResultCategories(String calculationRule) {
        List<ResultCategory> graphCategories = []
        List<IndicatorReportItem> reportItems = this.report?.reportItemsAsReportItemObjects?.findAll({it.categories})

        if (reportItems) {
            reportItems.categories?.each {
                it.each { String category ->
                    ResultCategory resultCategory = this.resolveResultCategories?.find({
                        category.equals(it.resultCategoryId)
                    })

                    if (!resultCategory) {
                        resultCategory = this.resolveResultCategories?.find({
                            category.equals(it.resultCategory)
                        })
                    }

                    if (resultCategory && !resultCategory.ignoreFromTotals?.contains(calculationRule) && !resultCategory.hideResultInReport?.contains(calculationRule)  && !resultCategory.hideInGraphs && !graphCategories.contains(resultCategory)) {
                        graphCategories.add(resultCategory)
                    }
                }
            }

        } else {
            this.resolveResultCategories?.each { ResultCategory resultCategory ->
                if (!resultCategory.ignoreFromTotals?.contains(calculationRule) && !resultCategory.hideResultInReport?.contains(calculationRule) && !resultCategory.hideInGraphs) {
                    graphCategories.add(resultCategory)
                }
            }
        }


        return graphCategories
    }*/

    def getGraphResultCategoryObjects(Entity parentEntity) {
        List<ResultCategory> graphCategories = []

        if (this.graphResultCategories && !this.graphResultCategories.isEmpty()) {
            List<ResultCategory> resolvedCategories = this.getResolveResultCategories(parentEntity)
            this.graphResultCategories.each { String category ->
                ResultCategory resultCategory = resolvedCategories?.find({
                    category.equals(it.resultCategoryId)})
                if (resultCategory) {
                    graphCategories.add(resultCategory)
                }

            }
        } else {
            graphCategories = this.getResolveResultCategories(parentEntity)?.findAll({!it.hideInGraphs})
        }
        return graphCategories
    }

    def getGraphCalculationRuleObjects(Entity parentEntity, List<String> rulesIgnoreHideInGraph = null, List<CalculationRule> resolvedCalculationRules = null) {
        List<CalculationRule> calculationRules = []

        if (this.graphCalculationRules && !graphCalculationRules.isEmpty()) {
            List<CalculationRule> indicatorRules = resolvedCalculationRules ?: getResolveCalculationRules(parentEntity)
            if (indicatorRules) {
                this.graphCalculationRules.each { String rule ->
                    CalculationRule calculationRule = indicatorRules.find({ rule.equals(it.calculationRuleId) })

                    if (calculationRule) {
                        calculationRules.add(calculationRule)
                    }
                }
            }
        } else {
            List<CalculationRule> calculationRulesNoFilter = resolvedCalculationRules ?: getResolveCalculationRules(parentEntity)
            if(rulesIgnoreHideInGraph) {
                calculationRules = calculationRulesNoFilter?.findAll({rulesIgnoreHideInGraph?.contains(it.calculationRuleId) || (!rulesIgnoreHideInGraph?.contains(it.calculationRuleId) && !it.hideInGraphs)})
            } else {
                calculationRules = calculationRulesNoFilter?.findAll({!it.hideInGraphs})
            }

        }
        return calculationRules
    }



    /*def getReportItemCategories(String ruleId, Boolean showHidden = Boolean.FALSE, Boolean showIgnoredOnes = Boolean.TRUE) {
        List<ResultCategory> reportItemCategories = []
        List<IndicatorReportItem> reportItems = this.report?.reportItemsAsReportItemObjects?.findAll({it.categories})
        if (reportItems) {
            reportItems.categories?.each {
                it.each { String category ->
                    ResultCategory resultCategory = this.resolveResultCategories?.find({
                        category.equals(it.resultCategoryId)
                    })

                    if (!resultCategory) {
                        resultCategory = this.resolveResultCategories?.find({
                            category.equals(it.resultCategory)
                        })
                    }

                    if (resultCategory && (showIgnoredOnes  || !resultCategory.ignoreFromTotals?.contains(ruleId)) && (showHidden || !resultCategory.hideResultInReport?.contains(ruleId)) && !reportItemCategories.contains(resultCategory)) {
                        reportItemCategories.add(resultCategory)
                    }
                }
            }

        } else {
            this.resolveResultCategories?.each { ResultCategory resultCategory ->
                if ((showIgnoredOnes  || !resultCategory.ignoreFromTotals?.contains(ruleId)) && (showHidden || !resultCategory.hideResultInReport?.contains(ruleId))) {
                    reportItemCategories.add(resultCategory)
                }
            }
        }
        return reportItemCategories
    }*/
    // DEPRECATE getAllLocalizedUnits ?
    /*def getAllLocalizedUnits() {
        def units = []

        if (unit) {
            units.add(localizedUnit)
        }

        resolveDenominators?.each { Denominator denominator ->
            if (denominator.localizedUnit) {
                units.add(localizedUnit)
            }
        }
        return units
    }*/

    def getApplicationDefaultObjects() {
        List<DefaultValue> applicationDefaultsObjects = []

        if (applicationDefaults) {
            applicationDefaults.each { String applicationId, List<String> applicationDefaultIds ->
                Application application = applicationService.getApplicationByApplicationId(applicationId)
                List<DefaultValue> defaultsForApplication = application?.defaultValues?.findAll({
                    applicationDefaultIds.contains(it.defaultValueId)
                })

                if (defaultsForApplication) {
                    defaultsForApplication.each {
                        it.applicationId = applicationId
                    }
                    applicationDefaultsObjects.addAll(defaultsForApplication)
                }
            }
        }
        return applicationDefaultsObjects
    }

    /**
     * section is hidden totally if sectionId is defined as key, with empty list
     * @param section
     * @return
     */
    boolean hideSectionTotally(QuerySection section) {
        boolean hide = false

        if (section && hideQuestions && hideQuestions.containsKey(section.sectionId) &&
                !hideQuestions.get(section.sectionId)) {
            hide = true
        }
        return hide
    }

    def howManyChildEntitiesReady(List<Entity> childEntities) {
        Integer size = childEntities?.findAll({ Entity design -> design.isIndicatorReady(this) })?.size()
        return size ? size : 0

    }

    def preventChanges(Query query) {
        Boolean prevent = false
        if (query) {
            IndicatorQuery indicatorQuery = indicatorQueries?.find({ IndicatorQuery iq -> query.queryId.equals(iq.queryId) })

            if (indicatorQuery) {
                prevent = indicatorQuery.preventChanges
            }
        }
        return prevent
    }

    def getPieGraphHeading() {
        String heading

        if (displayResult) {
            heading = getResolveCalculationRules(null, true)?.find({displayResult.equals(it.calculationRuleId)})?.localizedName
        } else {
            heading = "Undefined"
        }
        return heading
    }

    def isBenchmarkable(String entityType) {
        if (enableCarbonHeroesBenchmarks) {
            //return indicatorBenchmarkService.getIndicatorBenchmarkable(entityType)
            return true
        } else {
            return false
        }
    }

    def getReportGenerationRuleFromApplication() {
        List<ReportGenerationRule> reportGenerationRules = []

        if (wordReportGeneration) {
            String applicationId = wordReportGeneration.keySet().first()
            List<String> reportGenerationRuleIds = wordReportGeneration.get(applicationId)
            Document application = applicationService.getApplicationAsDocumentByApplicationId(applicationId, [reportGenerationRuleSets: 1])

            if (application) {
                if (application.reportGenerationRuleSets) {
                    reportGenerationRuleIds.each { String reportGenerationRuleId ->
                        ReportGenerationRule reportGenerationRule = application.reportGenerationRuleSets.find({it.reportGenerationRuleId.equals(reportGenerationRuleId)})

                        if (reportGenerationRule) {
                            reportGenerationRules.add(reportGenerationRule)
                        }
                    }
                }
            }
        }
        return reportGenerationRules
    }

    def getReportConditionalRemovalRulesFromApplication() {
        List<ReportConditionalRemoval> reportRemovalRules = []

        if (wordReportGeneration) {
            String applicationId = wordReportGeneration.keySet().first()
            Application application = applicationService.getApplicationByApplicationId(applicationId)
            reportRemovalRules = application?.reportConditionalRemovalRules ?: []
        }
        return reportRemovalRules
    }

    def getContentInjectionSetsFromApplication() {
        Map<String, List<ReportGenerationRuleContent>> contentInjectionSets = [:]

        if (wordReportGeneration) {
            String applicationId = wordReportGeneration.keySet().first()
            Application application = applicationService.getApplicationByApplicationId(applicationId)
            contentInjectionSets = application?.contentInjectionSets ?: [:]
        }
        return contentInjectionSets
    }

    def getNameStringWithoutQuotes() {
       String escapedName = ""
        if (localizedName) {
            escapedName = optimiStringUtils.escapeSingleAndDoubleQuotes(localizedName)
        }
        return escapedName
    }

    def getIsCostCalculationAllowed() {
        return "LCC".equalsIgnoreCase(assessmentType)||activateCostCalculation
    }

    def getIsUsingLCAParameters() {
        return indicatorQueries?.find({ Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId)}) ? true : false
    }

    def getDesignMaxRowsReached(String designId) {
        Boolean isReached = null
        if (maxRowLimitPerDesign && designId) {
            Integer datasetAmount = entityService.getDatasetAmountForEntityAndIndicator(designId, this)
            if (datasetAmount >= maxRowLimitPerDesign) {
                isReached = true
            }
        }
        return isReached
    }
    
    def getIndicatorHelpUrlTranslation() {
        return localizedLinkService.getTransformStringIdsToUrl(indicatorHelpUrl)
    }

    /**
     * @return false if allowConstituentQuantityEdit is specified as false in config
     */
    boolean getAllowEditingConstituentQuantity() {
        if (allowConstituentQuantityEdit == false) {
            return false
        } else {
            return true
        }
    }

    @Override
    public String toString() {
        return "\nIndicator{" +
                ",\n id=" + id +
                ",\n indicatorId='" + indicatorId + '\'' +
                ",\n indicatorUse='" + indicatorUse + '\'' +
                ",\n importTime='" + importTime + '\'' +
                ",\n importFile='" + importFile + '\'' +
                ",\n assessmentMethod='" + assessmentMethod + '\'' +
                ",\n assessmentType='" + assessmentType + '\'' +
                ",\n assessmentPeriodValueReference=" + assessmentPeriodValueReference +
                ",\n assessmentPeriodFixed=" + assessmentPeriodFixed +
                ",\n displayResult='" + displayResult + '\'' +
                ",\n additionalPortfolioCalculationRules=" + additionalPortfolioCalculationRules +
                ",\n active=" + active +
                ",\n name=" + name +
                ",\n shortName=" + shortName +
                ",\n unit=" + unit +
                ",\n purpose=" + purpose +
                ",\n resultsHeadline=" + resultsHeadline +
                ",\n resultReportLinkText=" + resultReportLinkText +
                ",\n indicatorQueries=" + indicatorQueries +
                ",\n hideQuestions=" + hideQuestions +
                ",\n calculationRules=" + calculationRules +
                ",\n resultCategories=" + resultCategories +
                ",\n compatibleEntityTypes=" + compatibleEntityTypes +
                ",\n compatibleEntityClasses=" + compatibleEntityClasses +
                ",\n requiredEnvironmentDataSourceStandards=" + requiredEnvironmentDataSourceStandards +
                ",\n performancePenaltyMultiplier=" + performancePenaltyMultiplier +
                ",\n showDecimals=" + showDecimals +
                ",\n significantDigits=" + significantDigits +
                ",\n minimumZeros=" + minimumZeros +
                ",\n rounding='" + rounding + '\'' +
                ",\n multiplier=" + multiplier +
                ",\n useScientificNumbers=" + useScientificNumbers +
                ",\n appendQuestionIdToDisplayResult=" + appendQuestionIdToDisplayResult +
                ",\n importParameters=" + importParameters +
                ",\n denominatorList=" + denominatorList +
                ",\n showAverageInsteadOfTotal=" + showAverageInsteadOfTotal +
                ",\n relatedMainEntityQueries=" + relatedMainEntityQueries +
                ",\n hideZeroResults=" + hideZeroResults +
                ",\n exportContent=" + exportContent +
                ",\n resultFormatting=" + resultFormatting +
                ",\n nonNumericResult=" + nonNumericResult +
                ",\n allowMonthlyData=" + allowMonthlyData +
                ",\n compareCalculationRule='" + compareCalculationRule + '\'' +
                ",\n report=" + report +
                ",\n enableExport=" + enableExport +
                ",\n evaluateNonResourceAnswers=" + evaluateNonResourceAnswers +
                ",\n sourceListing=" + sourceListing +
                ",\n portfolioSourceListing=" + portfolioSourceListing +
                ",\n addableCategories=" + addableCategories +
                ",\n hideResultCategoryLabels=" + hideResultCategoryLabels +
                ",\n resourceExtraInformation=" + resourceExtraInformation +
                ",\n applicationDenominators=" + applicationDenominators +
                ",\n applicationRules=" + applicationRules +
                ",\n applicationCategories=" + applicationCategories +
                ",\n applicationResultFormatting=" + applicationResultFormatting +
                ",\n applicationResourceExtraInformation=" + applicationResourceExtraInformation +
                ",\n widgetTemplateUrl=" + widgetTemplateUrl +
                ",\n allowAverageInPortfolio=" + allowAverageInPortfolio +
                ",\n queriesForSourceListing=" + queriesForSourceListing +
                ",\n showDiscountRates=" + showDiscountRates +
                ",\n deprecated=" + deprecated +
                ",\n exportType='" + exportType + '\'' +
                ",\n exportTemplate=" + exportTemplate +
                ",\n pdfReplacementSettings=" + pdfReplacementSettings +
                ",\n exportSources=" + exportSources +
                ",\n exportResultTables=" + exportResultTables +
                ",\n expandFeatures=" + expandFeatures +
                ",\n applicationDefaults=" + applicationDefaults +
                ",\n displayDesignResultCompare=" + displayDesignResultCompare +
                ",\n reportVisualisation=" + reportVisualisation +
                ",\n exportXML='" + exportXML + '\'' +
                ",\n rsetSource=" + rsetSource +
                ",\n fecEpdSource=" + fecEpdSource +
                ",\n applicationExpandFeatures=" + applicationExpandFeatures +
                ",\n readValuesFromXML=" + readValuesFromXML +
                ",\n wooCommerceProductId='" + wooCommerceProductId + '\'' +
                ",\n requireMonthly=" + requireMonthly +
                ",\n indicatorHelpUrl='" + indicatorHelpUrl + '\'' +
                ",\n connectedBenchmark='" + connectedBenchmark + '\'' +
                ",\n connectedBenchmarks=" + connectedBenchmarks +
                ",\n multiDataWarning=" + multiDataWarning +
                ",\n labourCostCraftsmanValueReference=" + labourCostCraftsmanValueReference +
                ",\n labourCostWorkerValueReference=" + labourCostWorkerValueReference +
                ",\n countryIndexValueReference=" + countryIndexValueReference +
                ",\n currencyExchangeValueReference=" + currencyExchangeValueReference +
                ",\n hideSourceListing=" + hideSourceListing +
                ",\n hideExpandAndCollapse=" + hideExpandAndCollapse +
                ",\n handleVirtualLast=" + handleVirtualLast +
                ",\n wordReportGeneration=" + wordReportGeneration +
                ",\n breeamTypeExcelReport=" + breeamTypeExcelReport +
                ",\n excelExportCategoryIds=" + excelExportCategoryIds +
                ",\n excelExportRuleIds=" + excelExportRuleIds +
                ",\n visibilityStatus='" + visibilityStatus + '\'' +
                ",\n graphResultCategories=" + graphResultCategories +
                ",\n graphCalculationRules=" + graphCalculationRules +
                ",\n excelExportName='" + excelExportName + '\'' +
                ",\n indicatorDefaults=" + indicatorDefaults +
                ",\n allowedCarbonDesignerRegions=" + allowedCarbonDesignerRegions +
                ",\n supressDataFilterWarnings=" + supressDataFilterWarnings +
                ",\n useSpecificServiceLifes=" + useSpecificServiceLifes +
                ",\n hideSectionResult=" + hideSectionResult +
                ",\n expandFeatureCategoryIds=" + expandFeatureCategoryIds +
                ",\n enableLcaChecker=" + enableLcaChecker +
                ",\n compatibleIndicatorIds=" + compatibleIndicatorIds +
                ",\n portfolioResultCategories=" + portfolioResultCategories +
                ",\n exportToRevitBenchmark=" + exportToRevitBenchmark +
                ",\n isResourceAggregationDisallowed=" + isResourceAggregationDisallowed +
                ",\n useNewLifeCycleModel=" + useNewLifeCycleModel +
                ",\n deprecatedDate=" + deprecatedDate +
                ",\n certificationImageResourceId=" + certificationImageResourceId +
                ",\n activateCostCalculation=" + activateCostCalculation +
                ",\n enableCarbonHeroesBenchmarks=" + enableCarbonHeroesBenchmarks +
                ",\n groupingClassificationParamId='" + groupingClassificationParamId + '\'' +
                ",\n licensedNamequalifiers=" + licensedNamequalifiers +
                ",\n maxRowLimitPerDesign=" + maxRowLimitPerDesign +
                ",\n preventCopyingQueryContentsFromIndicator=" + preventCopyingQueryContentsFromIndicator +
                ",\n preventPrivateData=" + preventPrivateData +
                ",\n showOnlyLicensedRules=" + showOnlyLicensedRules +
                ",\n isPlanetaryIndicator=" + isPlanetaryIndicator +
                ",\n renameReportVisualisation=" + renameReportVisualisation +
                ",\n benchmarkSettings=" + benchmarkSettings +
                ",\n allowIlcdXmlExport=" + allowIlcdXmlExport +
                ",\n allowOrganisationSpecificClassification=" + allowOrganisationSpecificClassification +
                ",\n showAlternativeClassification=" + showAlternativeClassification +
                ",\n privateClassificationQuestionSequenceNr=" + privateClassificationQuestionSequenceNr +
                ",\n classificationsMap=" + classificationsMap +
                ",\n privateClassificationHelp=" + privateClassificationHelp +
                ",\n sendPrivateDataType='" + sendPrivateDataType + '\'' +
                ",\n deprecationNote=" + deprecationNote +
                ",\n indicatorDiscountScenarios=" + indicatorDiscountScenarios +
                ",\n futureDiscountingEnabled=" + futureDiscountingEnabled +
                ",\n calculateByQueryDefaultIfAbsent=" + calculateByQueryDefaultIfAbsent +
                ",\n showConstructionImpact=" + showConstructionImpact +
                ",\n applyNmdScaling=" + applyNmdScaling +
                ",\n preventGrouping=" + preventGrouping +
                ",\n preventComparing=" + preventComparing +
                ",\n preventResultSummaryDownload=" + preventResultSummaryDownload +
                ",\n massRuleId='" + massRuleId + '\'' +
                ",\n allowConstituentQuantityEdit=" + allowConstituentQuantityEdit +
                ",\n excludeAdditionalQuestionsInGroupEdit=" + excludeAdditionalQuestionsInGroupEdit +
                '}';
    }

    @CompileStatic
    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Indicator indicator = (Indicator) o

        if (id != indicator.id) return false
        if (indicatorId != indicator.indicatorId) return false

        return true
    }

    @CompileStatic
    int hashCode() {
        int result
        result = id.hashCode()
        result = 31 * result + indicatorId.hashCode()
        return result
    }
}

