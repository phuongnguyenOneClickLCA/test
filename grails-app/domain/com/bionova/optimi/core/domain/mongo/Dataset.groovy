/*
/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.calculation.AppliedNmdScaling
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.MapUtils

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@GrailsCompileStatic
class Dataset implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    static final long serialVersionUID = 1L
    static final String VERIFIABLE_INPUT = 'verifiableDatasetInput'

    String manualId
    String queryId
    String sectionId
    String questionId // entityId + queryId + sectionId  + questionId are always unique
    String resourceId
    String profileId
    // if resourceId, entityId + indicatorId + queryId + sectionId  + questionId + resourceId are always unique
    Map<String, Object> tempAdditionalQuestionAnswers
    // if additionalQuestionId, entityId + queryId + sectionId  + questionId + additionalQuestionId are always unique
    Map<String, Object> additionalQuestionAnswers
    List answerIds // in multiselection (select, checkbox etc) the selected values or just one value (text, textarea)
    Double points
    Double quantity
    Double numericAnswer // Deprecated, only used to initialized quantity once.
    String lastUpdater // User.id.toString() of the last updater
    Date lastUpdated
    List<Integer> occurrencePeriods // this is used for recurring operations calculations
    Boolean mapToResource
    String mapToResourceType
    Map<String, Object> monthlyAnswers = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    Map<String, Object> quarterlyAnswers = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    Double result    // result  reflects outcome so ambiguous use of fields can be avoided
    Map<String, Double> tempPointsByCalculationRule
    Map<String, String> trainingData // for storing all the trainingData values in eg. ifcImport
    Map<String, String> trainingMatchData // temp for storing all the matching trainingData values in eg. ifcImport
    Map<String, String> descriptiveDisplayData
    Boolean suggestedResourceMatch
    Map<String, String> importDisplayFields
    Map<String, String> collapseValues
    Map<String, String> userDefinedGrouping
    String collapseGrouperId
    String importMapperGroupedBy
    Boolean importMapperPartialQuantification
    Boolean importMapperCompositeMaterial
    Boolean importMapperDecideLater
    String userGivenUnit
    List<String> tempUserGivenUnits
    List<String> tempSeqNrs
    Double percentageOfTotal
    Double shareOfTotal
    String usedUnitSystem
    List<Dataset> splittedComposites
    String originalAnswer // need to store the users origina answer / quantity before conversion
    Boolean noQueryRender
    Boolean remapInImportMapper
    Boolean createdFromVirtualResource

    String resultCategory // needed for BREEAM UK, task 3425
    String resultCategoryId // needed for detailed report
    ResultCategory resultCategoryObject // needed for detailed report

    // This only set in calculation to avoid unneeded resource fetches
    Resource calculationResource
    String calculationResourceId
    String calculationProfileId

    // These are new attributes for new calculation
    Double calculatedQuantity
    Map<String, Double> calculatedMonthlyQuantity
    Map<String, Double> calculatedQuarterlyQuantity
    String calculatedUnit
    Double calculatedMass
    List<String> resultCategoryIds
    List<String> resultCategoryIdsWithStages

    // Transients for new calculation
    Boolean dividedInVirtualResources
    String virtualParentId
    Question resolvedQuestion

    Boolean systemTrained //for system training purposes
    Boolean allowMapping
    Map<String, String> dataForCollapser //importmapper transient collapser
    String originalClass //for API imports
    String persistedOriginalClass //for persisting untranslated class from importmapper
    Boolean wasResolved
    String wasCombinedInto
    Double area_m2
    List<String> tempArea_m2 // Deprecated
    Double volume_m3
    List<String> tempVolume_m3 // Deprecated
    Double mass_kg
    List<String> tempPersistedOriginalClass
    String ifcMaterialValue
    String ifcMaterialValueOrg //15439
    Integer seqNr

    Boolean userSetCost
    List<String> tempUserSetCosts
    Boolean userSetTotalCost
    List<String> tempUserSetTotalCosts
    String uniqueConstructionIdentifier
    String constructionValue
    String parentConstructionId
    Dataset parentConstructionDataset // transient
    List<String> tempConstructionValues
    List<String> tempConstructionIdentifiers
    List<String> tempConstructionIds
    List<String> tempConstituentStatus
    Double tempConvertedQuantity
    List<Map<String, String>> allImportDisplayFields //combined from
    List<String> originalDatasetsManualIds // manualIds of the datasets this one was combined from, mainly transient for bim checker
    Boolean discarded // for bim checker discarded datasets transient
    Boolean filtered // for bim checker filtered datasets transient
    String datasetImportFieldsId //combined from
    List<String> tempDatasetImportFieldsId
    String scalingType // for construction constituents *e.q fixed or scaling.
    String recognizedFrom
    String recognizedFromShort
    String defaultDatasetOriginalManualId
    List <String> tempDatasetManualIds

    Double resultAbsolute //API construction result transient
    Double resultByVolume //API construction result transient
    Integer weight // ImportMapper dataset recognition transietn
    String importWarning
    String importError
    Boolean unconventionalClassification
    Boolean unconventionalUnit
    Boolean undefinedErrorType
    String connectedDatasetManualId
    String preResultFormula // Transient for calculation, to record mass && unit conversion before a result is generated
    String importMapperThickness_m

    Double specialRuleShare // Importmapper transient
    String specialRuleId // ImportMapper transient
    String categoryMaterialImport //Importmapper transient
    //EARLY PHASE
    String groupId
    Boolean defaultConstituent
    Boolean projectLevelDataset
    List<String> defaultForRegionList //Not used anymore

    List<String> tempIgnoreWarnings
    Boolean ignoreWarning
    List<String> tempLocked
    Boolean locked
    Boolean verified
    Boolean unlockedFromVerifiedStatus
    String verifiedFromEntityId

    List<String> tempVerifiedStatuses
    List<String> tempUnlockedFromVerifiedStatuses
    List<String> tempVerifiedFromEntityIds

    String nmdProfileSetString
    List<String> tempNmdProfileSetString
    List<String> tempGroupingDatasetName
    AppliedNmdScaling appliedNmdScaling

    // Only for construction datasets, values for UI
    String imperialUnit
    Double imperialQuantity

    //Flexible Resource
    Double layerUValue

    List<String> flexibleResourceIdList

    String groupingDatasetName

    String groupingType // Transient for chart rendering

    // Used to mark the datasets generated by the sw from importing.
    Boolean generatedFromImport
    Double massPerUnit_kg

    Boolean unlinkedFromParentConstruction // for constituents that has edited quantity by user
    String productDescription

    static constraints = {
        createdFromVirtualResource nullable: true
        manualId nullable: true
        queryId nullable: false
        sectionId nullable: false
        questionId nullable: true
        points nullable: true
        quantity nullable: true
        resourceId nullable: true
        profileId nullable: true
        result nullable: true
        tempAdditionalQuestionAnswers nullable: true
        additionalQuestionAnswers nullable: true
        occurrencePeriods nullable: true
        answerIds nullable: true
        mapToResource nullable: true
        mapToResourceType nullable: true
        monthlyAnswers nullable: true
        numericAnswer nullable: true
        tempPointsByCalculationRule nullable: true
        tempConstituentStatus nullable: true
        lastUpdater nullable: true
        lastUpdated nullable: true
        suggestedResourceMatch nullable: true
        trainingData nullable: true
        trainingMatchData nullable: true
        importDisplayFields nullable: true
        collapseValues nullable: true
        userDefinedGrouping nullable: true
        collapseGrouperId nullable: true
        importMapperGroupedBy nullable: true
        importMapperPartialQuantification nullable: true
        importMapperCompositeMaterial nullable: true
        importMapperDecideLater nullable: true
        userGivenUnit nullable: true
        usedUnitSystem nullable: true
        tempUserGivenUnits nullable: true
        percentageOfTotal nullable: true
        shareOfTotal nullable: true
        quarterlyAnswers nullable: true
        splittedComposites nullable: true
        originalAnswer nullable: true
        remapInImportMapper nullable: true
        resultCategoryObject nullable: true
        calculationResource nullable: true
        calculationResourceId nullable: true
        calculationProfileId nullable: true
        calculatedQuantity nullable: true
        calculatedMonthlyQuantity nullable: true
        calculatedQuarterlyQuantity nullable: true
        calculatedUnit nullable: true
        calculatedMass nullable: true
        resultCategoryIds nullable: true
        dividedInVirtualResources nullable: true
        virtualParentId nullable: true
        resolvedQuestion nullable: true
        resultCategoryId nullable: true
        systemTrained nullable: true
        allowMapping nullable: true
        dataForCollapser nullable: true
        seqNr nullable: true
        tempSeqNrs nullable: true
        originalClass nullable: true
        userSetCost nullable: true
        userSetTotalCost nullable: true
        tempUserSetCosts nullable: true
        tempUserSetTotalCosts nullable: true
        ifcMaterialValue nullable: true
        uniqueConstructionIdentifier nullable: true
        constructionValue nullable: true
        parentConstructionId nullable: true
        tempConstructionIdentifiers nullable: true
        tempConstructionValues nullable: true
        tempConstructionIds nullable: true
        tempConvertedQuantity nullable: true
        allImportDisplayFields nullable: true
        scalingType nullable:true
        persistedOriginalClass nullable: true
        tempPersistedOriginalClass nullable: true
        area_m2 nullable: true
        tempArea_m2 nullable: true
        volume_m3 nullable: true
        tempVolume_m3 nullable: true
        recognizedFrom nullable: true
        recognizedFromShort nullable: true
        datasetImportFieldsId nullable: true
        tempDatasetImportFieldsId nullable: true
        defaultDatasetOriginalManualId nullable: true
        tempDatasetManualIds nullable: true
        tempNmdProfileSetString nullable: true
        resultAbsolute nullable: true
        resultByVolume nullable: true
        importWarning nullable:true
        importError nullable:true
        unconventionalClassification nullable:true
        unconventionalUnit nullable:true
        undefinedErrorType nullable:true
        noQueryRender nullable: true
        groupId nullable: true
        defaultConstituent nullable: true
        originalDatasetsManualIds nullable: true
        discarded nullable: true
        filtered nullable: true
        weight nullable: true
        projectLevelDataset nullable: true
        connectedDatasetManualId nullable: true
        ignoreWarning nullable: true
        preResultFormula nullable: true
        importMapperThickness_m nullable: true
        wasResolved nullable: true
        wasCombinedInto nullable: true
        tempIgnoreWarnings nullable: true
        locked nullable: true
        tempLocked nullable: true
        verified nullable: true
        unlockedFromVerifiedStatus nullable: true
        verifiedFromEntityId nullable: true
        defaultForRegionList nullable: true
        specialRuleShare nullable: true
        specialRuleId nullable: true
        categoryMaterialImport nullable: true
        layerUValue nullable: true
        flexibleResourceIdList nullable: true
        nmdProfileSetString nullable: true
        imperialUnit nullable: true
        imperialQuantity nullable: true
        groupingDatasetName nullable: true
        tempGroupingDatasetName nullable: true
        mass_kg nullable: true
        groupingType nullable: true
        ifcMaterialValueOrg nullable: true
        generatedFromImport nullable: true
        massPerUnit_kg nullable: true
        unlinkedFromParentConstruction nullable: true
        tempVerifiedStatuses nullable: true
        tempUnlockedFromVerifiedStatuses nullable: true
        tempVerifiedFromEntityIds nullable: true
        appliedNmdScaling nullable: true
        productDescription nullable: true
    }

    static mapping = { version false }

    static embedded = ['splittedComposites', 'resultCategoryObject', 'appliedNmdScaling']

    static transients = [
            "createdFromVirtualResource",
            "resource",
            "filteredAnswers",
            "createCopy",
            "tempAdditionalQuestionAnswers",
            "tempUserGivenUnits",
            "resourceProfile",
            "thickness",
            "comment",
            "question",
            "importDisplayFields",
            "suggestedResourceMatch",
            "collapseValues",
            "collapseGrouperId",
            "userDefinedGrouping",
            "importMapperGroupedBy",
            "splittedComposites",
            "remapInImportMapper",
            "resultCategory",
            "resultCategoryId",
            "calculationResource",
            "answer",
            "resultCategoryIds",
            "dividedInVirtualResources",
            "virtualParentId",
            "resourceForCalculation",
            "calculationResourceId",
            "calculationProfileId",
            "resolvedQuestion",
            "monthlyQuantities",
            "systemTrained",
            "allowMapping",
            "dataForCollapser",
            "tempSeqNrs",
            "originalClass",
            "tempUserSetCosts",
            "tempUserSetTotalCosts",
            "ifcMaterialValue",
            "ifcMaterialValueOrg",
            "tempConvertedQuantity",
            "tempPersistedOriginalClass",
            "tempArea_m2",
            "tempVolume_m3",
            "recognizedFrom",
            "recognizedFromShort",
            "allImportDisplayFields",
            "descriptiveDisplayData",
            "tempDatasetImportFieldsId",
            "lastUpdaterName",
            "datasetResourceActive",
            "resultAbsolute",
            "resultByVolume",
            "originalDatasetsManualIds",
            "discarded",
            "filtered",
            "weight",
            "tempConstructionValues",
            "tempConstructionIds",
            "tempConstructionIdentifiers",
            "tempDatasetManualIds",
            "tempPointsByCalculationRule",
            "tempVerifiedStatuses",
            "tempUnlockedFromVerifiedStatuses",
            "tempVerifiedFromEntityIds",
            "trainingMatchData",
            "preResultFormula",
            "importMapperThickness_m",
            "wasResolved",
            "wasCombinedInto",
            "tempIgnoreWarnings",
            "tempLocked",
            "EOLProcessClass",
            "specialRuleId",
            "specialRuleShare",
            "categoryMaterialImport",
            "tempNmdProfileSetString",
            "tempGroupingDatasetName",
            "resourceAsDocument",
            "groupingType",
            "parentConstructionDataset"
    ]


    private Boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    String getComment() {
        String comment = additionalQuestionAnswers?.get("comment")
        return comment ? comment : ""
    }

    def setCalculationResource(Resource resource) {
        if (resource) {
            calculationResource = resource
            calculationResourceId = resource.resourceId
            calculationProfileId = resource.profileId
        }
    }

    public String toString() {
        return "[queryId: " + queryId + ", sectionId: " + sectionId + ", questionId: " + questionId + ", resourceId: " + resourceId + ", profileId: " + profileId +
                ", answerIds: " + answerIds + ", quantity: " + quantity + ", additionalQuestionAnswers: " + additionalQuestionAnswers + ", userGivenUnit: " + userGivenUnit + "]"
    }

    public int compareTo(Object obj) {
        int compared = 0

        if (obj && obj instanceof Dataset) {
            if (this.seqNr && obj.seqNr) {
                compared = this.seqNr.compareTo(obj.seqNr)
            }

            if (compared == 0) {
                if (this.manualId && obj.manualId) {
                    return this.manualId.compareTo(obj.manualId)
                } else if (this.resourceId && obj.resourceId) {
                    return this.resourceId.compareTo(obj.resourceId)
                } else {
                    return 1
                }
            } else {
                return compared
            }
        } else {
            return 1
        }
    }

    public boolean equals(Object o) {
        boolean equals = false

        if (o instanceof Dataset) {
            if (manualId && o.manualId && manualId == o.manualId) {
                equals = true
            } else if (o.queryId == queryId && o.sectionId == sectionId && o.questionId == questionId) {
                equals = true

                if (resourceId && o.resourceId && resourceId != o.resourceId) {
                    equals = false
                }
            }
        }
        return equals
    }

    Boolean isConstruction(){
        return (Boolean) (this.uniqueConstructionIdentifier && !this.parentConstructionId)
    }

    Boolean isConstituent(){
        return (Boolean) (this.uniqueConstructionIdentifier && this.parentConstructionId)
    }

    public void setAnswerIds(List answerIds) {
        this.answerIds = answerIds

        if (this.answerIds && quantity == null) {
            String answerForQuantity = this.answerIds.find({
                it != null && DomainObjectUtil.isNumericValue(it.toString())
            })?.toString()

            if (answerForQuantity) {
                this.quantity = DomainObjectUtil.convertStringToDouble(answerForQuantity)
            }
        }
    }
}
