/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.validation.Validateable
import org.apache.commons.collections4.CollectionUtils
import org.bson.types.ObjectId

import java.lang.reflect.Method
import java.time.Year

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class Resource implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    static final String QUALITY_WARNING_WARN_TYPE = "WARN"
    static final String QUALITY_WARNING_BLOCK_TYPE = "BLOCK"

    private static final List<String> GWPA2_FOSSIL_FAILOVER_HIERARCHY = ['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList
    private static final List<String> GWPA2_TOTAL_FAILOVER_HIERARCHY = ['impactGWP100_kgCO2e_total', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList
    private static final List<String> GWPA1_FAILOVER_HIERARCHY = ['impactGWP100_kgCO2e', 'impactGWP100_kgCO2e_fossil', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList
    private static final List<String> GWP_LIFE_CYCLE_FAILOVER_HIERARCHY = ['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList
    private static final List<String> GWP_DIRECT_FAILOVER_HIERARCHY = ['impactGWP_direct_kgCO2e', 'impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e'] as LinkedList

    ObjectId id
    String resourceId
    String profileId
    String importFile
    Boolean active
    String nameFI
    String nameEN
    String nameNO
    String nameDE
    String nameFR
    String nameNL
    String nameES
    String nameSE
    String nameIT
    String nameHU
    String nameJP
    String profileNameEN
    List<String> resourceGroup
    String unit // DEPRECATED, same info as unitForData
    Double density

    // These are copied from resourceProfile
    String area //deprecated
    List<String> areas // The area where this resource profile can be applied (eg. Finnish concrete can be applied in Finland)
    String additionalArea
    String representativeArea // Used in LOCAL area resources if cannot get area from project
    List<String> neighbouringCountries
    Boolean defaultProfile // If true, this resource is chosen by default (user has to change this) MUST BE IN A RESOURCE apparently when creating new ones

    Double defaultThickness_mm
    Double defaultThickness_in
    Double massConversionFactor
    Double maintenanceFrequency
    Double serviceLife
    Boolean serviceLifeOverride // Will use serviceLife instead of project level default service life from subType if those are present
    Boolean inheritConstructionServiceLife // Will require servicelife and will be inherited to constituents "inheritToChildren"
    Double energyConversionFactor
    String impactBasis
    String physicalPropertiesSource
    String environmentDataSourceType
    String environmentDataSourceStandard
    String environmentDataSource
    String serviceLifeSource
    String wasteResourceId
    String environmentalBenefitsResourceId
    String eolProcessingType
    String transportResourceId
    String transportResourceIdleg2 //16001
    String transportResourceIdBoverket
    String transportResourceIdBoverketLeg2
    String maintenanceResourceId
    Integer environmentDataPeriod
    Double environmentalBenefitsMultiplier
    Double impactGWP100_kgCO2e
    Double impactODP_kgCFC11e
    Double impactAP_kgSO2e
    Double impactEP_kgPO4e
    Double impactPOCP_kgEthenee
    Double impactADP_kgSbe
    Double impactADPElements_kgSbe
    Double impactADPFossilFuels_MJ
    Double impactADPFossilFuels_kgSbe
    Double impact_ecopoints
    Double wasteHazardous_kg
    Double wasteNonHazardous_kg
    Double wasteRadioactive_kg
    Double wasteRadioactiveHigh_kg
    Double cost_EUR
    Double costConstruction_EUR
    Double biogenicCarbonStorage_kgCO2e
    Double impactGWP100_kgCO2e_biogenic
    Double impactGWP_direct_kgCO2e
    Double shareOfRecycledMaterial
    Double shareOfRenewableMaterial
    Double defaultTransportDistance_km // DEPRECATED
    Double defaultTransportDistance_kmleg2 //16001
    Double defaultTransportBoverket
    Double defaultTransportBoverketLeg2
    Double heatingDegreeDay
    Double heatingDegreeDayNormalPeriod
    Double waterPollution_m3
    Double airPollution_m3
    Double impactGWP100_kgCO2e_fossil
    Double impactGWP100_kgCO2e_luluc

    Double impactPERRT_MJ
    Double impactPERNRT_MJ

    Double impactGWP_fossil_kg_CO2e_EnvDec

    Double renewablesUsedAsEnergy_MJ
    Double renewablesUsedAsMaterial_MJ
    Double nonRenewablesUsedAsEnergy_MJ
    Double nonRenewablesUsedAsMaterial_MJ

    Double recyclingMaterialUse_kg
    Double renewableRecylingFuelUse_MJ
    Double nonRenewableRecylingFuelUse_MJ
    Double cleanWaterNetUse_m3
    Double impactADPElements_FR_kgSbe

    Double reusableMaterialsOutput_kg
    Double recyclableMaterialsOutput_kg
    Double energyMaterialsOutput_kg
    Double exportedEnergyOutput_MJ
    Double humanToxiHTP_kgDCB
    Double freshWaterToxiFAETP_kgDCB
    Double seaWaterToxiMAETP_kgDCB
    Double terrestrialToxiTETP_kgDCB
    Double shadowPrice

    Double impactGWP100_AR5_kgCO2e
    Double resourceDepletionWater_m3
    Double ionisingRadiation_kgU235eq
    Double particulateMatter_kgPM25eq
    Double impactODP_WMO1999_kgCFC11e
    Double landTransformation_m2

    // For Dutch database
    Double exportedEnergyOutput_EEE_MJ
    Double exportedEnergyOutput_ETE_MJ

    // EntityClasses
    Boolean superUserOnly
    Boolean hasDesignPhase
    Boolean hasOperatingPhase
    Boolean hasPortfolio
    Boolean canBeChildEntity
    Boolean imageResource

    // Basically for cityData
    Boolean minimizeBasicData
    Boolean showTasks

    // Waste management

    Double localHeatCoEff
    Double solarRadiationHorizontal
    Double m2PerInhabitant
    Double groundTemperature

    // For channel feature, in which channels resource is allowed
    List<String> channelTokens

    String filterValue

    String epdProgram
    List<String> epdProgramList
    String pcr
    String verificationStatus
    String countryCurrencySymbol

    String applicationId

    List<String> missingRequiredFilterFields
    List<String> missingRequiredFilterValues
    List<String> illegalFilterValues
    List<String> failingMaxAgeFilterValues
    List<String> failingFilterRules
    List<String> failingVerifyValues
    List<String> failingActiveResourceIds
    String failingRequireIdenticalValues
    Boolean nonActiveResourceType
    Boolean nonActiveSubType
    Boolean isDummy
    String groupingName // transient, can be used to store name from grouping dataset if resource is dummy

    List<String> allowedUnits // Deprecated, using combinedUnits
    List<String> combinedUnits // all allowed units for resource

    Double preConsumerWaste_kg // DEPRECATED
    Double postConsumerWaste_kg // DEPRECATED

    String upstreamDB
    String pcrQuality
    String stateCode
    Double compensationFactor

    // Support for TRACI 2.1
    List<String> dataProperties
    Double traciGWP_kgCO2e
    Double traciODP_kgCFC11e
    Double traciAP_kgSO2e
    Double traciEP_kgNe
    Double traciPOCP_kgO3e
    Double traciNRPE_MJ

    Boolean virtual // DEPRECATED
    List<ResourceCompositePart> compositeParts

    Boolean useInRegistration
    Boolean allowInRegistration

    Boolean hideConstituentImpacts

    String unitForData

    Double fuelUseLiters
    Double shareOfLoad

    Double unitCost

    List<String> validity
    String epdURL
    String epdNumber
    String epdNumberAlt

    String description

    String manufacturer
    String commercialName

    Map<String, Map<String, Object>> impacts
    String stage

    String resourceType // eg. concrete, so user given defaults match to resource
    String resourceSubType

    String technicalSpec
    Double thermalLambda

    Boolean multipleProfiles
    String downloadLink
    String recycledContent
    String productDescription

    String searchString
    String detailedDataSource

    String isoCountryCode //Only for area defining resources to set isoCodesByAreas for other resources
    Map<String, String> isoCodesByAreas
    String countryEnergyEfficiency
    String countryEnergyResourceId
    String countryIncinerationResourceId
    Boolean countryRegulatedWasteHandling

    String countryEnergyRecoveryResourceId // Module D energy
    String countryEnergyRecoveryResourceProfileId // Profile for module D
    String countryEnergyResourceProfileId // Local comp energy profile
    String countryEnergyResourceProfileId_v2 // Local comp energy profile for V2

    Benchmark benchmark
    Boolean excludeFromBenchmark
    Boolean dataManuallyVerified

    String staticFullName
    String upstreamDBClassified
    String privateDatasetAccountId
    String privateDatasetAccountName
    String privateDatasetImportedByUserId
    String defaultTransportDefinition
    Boolean privateDataset
    Boolean decideLaterResource

    Boolean biogenicCarbonSeparated
    Boolean biogenicCarbonIsSustainable
    Boolean biogenicCarbonEstimated
    Double biogenicCarbonStorageProduct_kgCO2
    Double biogenicCarbonStoragePackaging_kgCO2
    Double carbonisationPotentialProduct_kgCO2e
    Double impactAP_molHeq
    Double impactODP_WMO2014_kgCFC11e

    // ISO21930+France+NSF+GHG
    Double impact_AP_ISO21930_kg_SO2e
    Double wasteRadioactiveIntAndLow_kg
    Double impactRE_MJ
    Double quantityGHG_CO2_fossil_kg
    Double quantityGHG_CO2_biogenic_kg
    Double quantityGHG_CH4_fossil_kg
    Double quantityGHG_CH4_biogenic_kg
    Double quantityGHG_N2O_kg
    Double quantityGHG_HFC_kg
    Double quantity_PFC_kg
    Double quantity_SF6_kg
    Double impactADPElements_NSF_kgSbe
    Double wasteHazardous_FR_kg
    Double wasteNonHazardous_FR_kg

    //kgr
    Double carShare
    Double publicBusShare
    Double publicRailShare
    Double walkBikeShare
    Double carDriveDistance_km
    Double publicTransportDistance_km
    Boolean construction
    String constructionId
    String constructionType
    Integer genericDataTopUpPercentage

    //Product Data Lists (EPD-TOOL)
    Boolean productDataList
    String productDataListId
    String productDataListType

    Double resourceTypeCostMultiplier

    List<String> resistanceProperties //fire, water, vapour, wear, freeze, sulphate, bio-co2 stored (dynamic)

    //SIMULATION TOOL - EARLY PHASE TOOL - Resource file related //TODO - These should be depreciated and removed after old CD is retired
    Double maxBuildingDepth_m
    Double doorsToGroundfFloorAreaRatio
    Double windowsToAboveGroundAreaRatio
    Double windowsMaximumOfExternalWalls
    Double balconiesToAboveGroundAreaRatio
    Double internalFloorHeight_m
    Double internalWallsToExternalWallsRatio
    Double floorThickness_m
    Double envelopeThickness_m
    Double lenghtDepthRatio
    Double roofShapeEfficiencyFactor
    Double buildingShapeEfficiencyFactor
    Double maxSpanWithoutColumns_m
    Double maxStairCaseDistance_m
    Double externalPavedAreasPerGFA

    List<String> simulationRegionIds

    //SIMULATION TOOL - Energy Section [Statsbygg_resources_v10.xlsx]
    String simulationToolBuildingType
    String energyScenario
    String regionReference
    String defaultCdEnergyResource

    //SIMULATION TOOL - PASSIVEHAUS PARAMETER
    Double passivhaus_EPH0
    Double passivhaus_X
    Double passivhaus_K1
    Double passivhaus_K2
    Double passivhaus_Beta
    Double passivhaus_waterEnergy

    Boolean impactNonLinear
    String defaultHeatCarrierId
    Date firstUploadTime
    Date deactivateTime

    Boolean isNewPrivateDataset // is false after the first activation
    Date firstActivationTime // for private dataset activation

    //SNBPE
    Double biogenicCarbonContentProduct_kgC
    Double biogenicCarbonContentPackaging_kgC

    // EN 15804+A2 - March 2019:
    Double impactEPFreshwater_kgPO4e
    Double impactEPMarine_kgNe
    Double impactEPTerrestrial_molNe
    Double impactPOCP_kgNMVOCe
    Double cleanWaterDeprivation_m3
    Double particulateMatter_incidence
    Double ecoToxicityFreshwater_CTUe
    Double humanToxicityCancer_CTUh
    Double humanToxicityNonCancer_CTUh
    Double potentialSoilQualityIndex

    // Image link & copyright & image description
    String imgLink
    String copyrightText
    String imgDescription

    String noteForDisabling
    String epdcFilePath

    //NMD3
//    Integer nmd3ElementId // REL-304 deprecate this field
    Integer nmdProductId
    // list of all allowed units coming from mirror construction. Currently for NMD construction resources (08.2021)
    List<String> mirrorConstructionCombinedUnits
    Double nmdUnitConversionFactor
    String nmdAlternativeUnit
    //Preparing for US states datasets
    String showState
    Boolean isState

    List datasetAdditionalQuestionAnswers // Transient for help showing data sources
    List<String> linkedDatasetManualIds // Transient for help showing data sources

    Boolean productSpecificOrLowVariation
    Integer carbonSavingVsBenchmark // 12134
    String carbonBenchmark // 12134
    String region // 11550
    Boolean isMultiPart // 10269 , 0015235
    Boolean multipart //0014969

    // 12248 automatic Purpose
    Boolean disableAutomaticPurposes
    List<String> automaticallyEnabledPurposes
    List<String> manuallyEnabledPurposes
    List<String> enabledPurposes

    Boolean showWarning
    String warningText
    String additionalInfo

    // 13034 EPD insanity
    List<String> specificCharacteristics
    String datasetType
    Boolean marketDataset
    // 0013439 for EcoInvent
    String casNumber

    // NMD
    String nmdElementId
    Integer nmdSchalingsFormuleID
    Double defaultThickness_mm2
    Double nmdSchalingsFormuleA1
    Double nmdSchalingsFormuleA2
    Double nmdSchalingsFormuleB1
    Double nmdSchalingsFormuleB2
    Double nmdSchalingsFormuleC
    Double nmdSchalingMinX1
    Double nmdSchalingMinX2
    Double nmdSchalingMaxX1
    Double nmdSchalingMaxX2
    String nmdSchalingUnit
    String rechtev_grootheid_dim1
    String rechtev_eenheid_dim1
    String rechtev_grootheid_dim2
    String rechtev_eenheid_dim2
    String nmdElementCode
    String nmdElementnaam
    String nmdCategoryId
    String nmdProductApplication
    String nmdDatabaseVersion
    String nmdProductType
    Date nmdUpdateTime
    List<Integer> nmdKindElementIds

    String resourceQualityWarning
    String resourceQualityWarningText
    //private data changes
    String privateDataType
    String brandImageId

    //15897
    Double siteWastage
    Double siteWastageBoverket

    //REL2108-141
    String refrigerantResourceId
    Boolean useStagesSeparated
    Double stockC_kgC
    Boolean stockCEstimated

    Date edited

    //16386
    List<String> allowToExportToExternalSources

    String thirdPartyVerifier // REL2108-88
    Boolean isConstituent
    Boolean isConstruction

    Double repairRateJP

    //SW-1524
    Boolean nmdReuseAvailable
    Double nmdReuseFactor
    String functionalUnit

    //sw-2238 : BETie
    String verifiersName
    String verificationDate
    String verificationProof
    String verificationNumber


    def transients = [
            "profiles",
            "latestProfile",
            "defaultResourceProfile",
            "hasUnit",
            "impactDataFactorCategories",
            "callMethodByName",
            "missingRequiredFilterValues",
            "missingRequiredFilterFields",
            "failingMaxAgeFilterValues",
            "failingVerifyValues",
            "failingFilterRules",
            "failingActiveResourceIds",
            "failingRequireIdenticalValues",
            "nonActiveResourceType",
            "nonActiveSubType",
            "allowedStages",
            "allowedImpactCategories",
            "hasStages",
            "noStages",
            "resourceTypeObject",
            "uiLabel",
            "countryEffiencyNumber",
            "isoFlag",
            "illegalFilterValues",
            "isLocalResource",
            "subType",
            "defaultUnit",
            "subtypeEolProcessingType",
            "subtypeDeconstructionType",
            "isoFlagPath",
            "isExpiredDataPoint",
            "impactGWP100_kgCO2e_total",
            "allowVariableThickness",
            "isLocalCompsApplicable",
            "datasetAdditionalQuestionAnswers",
            "linkedDatasetManualIds",
            "qMetadata",
            "totalWasteAndMaterialOutputs_kg",
            "allEnergyUsedTotal_kWh_SUM",
            "impactGWPdirectFailoverGetter",
            "impactGWPlifeCycleFailoverGetter",
            "impactGWPdirectFailoverGetterBasis",
            "impactGWPlifeCycleFailoverGetterBasis",
            "shadowPriceGetter",
            "expirationYear",
            "impactGWPA1FailoverGetter",
            "impactGWPA1FailoverGetterBasis",
            "impactGWPA2TotalFailoverGetter",
            "impactGWPA2TotalFailoverGetterBasis",
            "impactGWPA2FossilFailoverGetter",
            "impactGWPA2FossilFailoverGetterBasis"
    ]

    static embedded = ['compositeParts', 'benchmark']

    static hasMany = [areas: String, resourceGroup: String]

    static mapping = {
        resourceId index: true
        profileId index: true
        nameEN index: true
        compoundIndex resourceId:1, profileId:1
        version false
    }

    transient optimiResourceService
    transient configurationService
    transient userService
    transient loggerUtil
    transient flashService
    transient resourceTypeService
    transient costStructureService

    enum CONSTANTS {

        PRIVATE("private")

        private final String value

        CONSTANTS(String value){
            this.value = value
        }
        String value(){value}
    }

    public static final List<String> allowedStages = ["A1-A3", "A4", "A5", "A1-C4", "B1", "B2", "B3", "B4", "B5", "B6", "B7",
                                                      "C1", "C2", "C3", "C4", "C1-C4", "C3-C4", "D", "D1", "D2", "A4-A5", "B1-B7"]

    public static final List<String> allowedImpactCategories = ["impactGWP100_kgCO2e", "impactGWP100_kgCO2e_biogenic",
                                                                "impactODP_kgCFC11e", "impactAP_kgSO2e", "impactEP_kgPO4e", "impactPOCP_kgEthenee",
                                                                "impactADPElements_kgSbe", "impactADPFossilFuels_MJ", "wasteHazardous_kg",
                                                                "wasteNonHazardous_kg", "wasteRadioactive_kg",
                                                                "nonRenewablesUsedAsEnergy_MJ", "nonRenewablesUsedAsMaterial_MJ",
                                                                "renewablesUsedAsEnergy_MJ", "renewablesUsedAsMaterial_MJ",
                                                                "recyclingMaterialUse_kg", "renewableRecylingFuelUse_MJ",
                                                                "nonRenewableRecylingFuelUse_MJ", "cleanWaterNetUse_m3",
                                                                "reusableMaterialsOutput_kg", "recyclableMaterialsOutput_kg",
                                                                "energyMaterialsOutput_kg", "exportedEnergyOutput_MJ", "traciGWP_kgCO2e",
                                                                "traciODP_kgCFC11e", "traciAP_kgSO2e", "traciEP_kgNe",
                                                                "traciPOCP_kgO3e", "traciNRPE_MJ", "waterPollution_m3",
                                                                "airPollution_m3", "wasteRadioactiveHigh_kg", "impact_ecopoints",
                                                                "humanToxiHTP_kgDCB", "freshWaterToxiFAETP_kgDCB", "seaWaterToxiMAETP_kgDCB",
                                                                "terrestrialToxiTETP_kgDCB", "shadowPrice", "impactGWP100_kgCO2e_fossil",
                                                                "impactGWP100_kgCO2e_luluc", "impactGWP100_AR5_kgCO2e", "resourceDepletionWater_m3",
                                                                "ionisingRadiation_kgU235eq", "particulateMatter_kgPM25eq", "impactODP_WMO1999_kgCFC11e",
                                                                "landTransformation_m2", "impactPERRT_MJ", "impactPERNRT_MJ", "impactEPFreshwater_kgPO4e",
                                                                "impactEPMarine_kgNe", "impactEPTerrestrial_molNe", "impactPOCP_kgNMVOCe", "cleanWaterDeprivation_m3",
                                                                "particulateMatter_incidence", "ecoToxicityFreshwater_CTUe", "humanToxicityCancer_CTUh",
                                                                "humanToxicityNonCancer_CTUh", "potentialSoilQualityIndex", "impactAP_molHeq", "impactODP_WMO2014_kgCFC11e",
                                                                "impactADPElements_FR_kgSbe", "impact_AP_ISO21930_kg_SO2e", "wasteRadioactiveIntAndLow_kg", "impactRE_MJ",
                                                                "quantityGHG_CO2_fossil_kg", "quantityGHG_CO2_biogenic_kg", "quantityGHG_CH4_fossil_kg", "quantityGHG_CH4_biogenic_kg",
                                                                "quantityGHG_N2O_kg", "quantityGHG_HFC_kg", "quantity_PFC_kg", "quantity_SF6_kg", "impactADPElements_NSF_kgSbe",
                                                                "wasteHazardous_FR_kg", "wasteNonHazardous_FR_kg", "impactGWP_fossil_kg_CO2e_EnvDec", "exportedEnergyOutput_EEE_MJ", "exportedEnergyOutput_ETE_MJ",
                                                                "impactADPFossilFuels_kgSbe"]

    public static final List<String> allowedFactorCategories = ["impactGWP100_kgCO2e", "impactODP_kgCFC11e", "impactAP_kgSO2e",
                                                                "impactEP_kgPO4e", "impactPOCP_kgEthenee", "impactADPFossilFuels_MJ",
                                                                "nonRenewablesUsedAsEnergy_MJ", "renewablesUsedAsEnergy_MJ", "traciGWP_kgCO2e",
                                                                "traciODP_kgCFC11e", "traciAP_kgSO2e", "traciEP_kgNe", "traciPOCP_kgO3e",
                                                                "traciNRPE_MJ", "impactGWP100_AR5_kgCO2e", "impactODP_WMO1999_kgCFC11e", "impactEPFreshwater_kgPO4e",
                                                                "impactEPMarine_kgNe","impactEPTerrestrial_molNe","impactPOCP_kgNMVOCe","cleanWaterDeprivation_m3",
                                                                "particulateMatter_incidence", "ecoToxicityFreshwater_CTUe","humanToxicityCancer_CTUh",
                                                                "humanToxicityNonCancer_CTUh","potentialSoilQualityIndex","impactAP_molHeq","impactODP_WMO2014_kgCFC11e"]

    private static final Map<String, String> GWP_MESSAGES = [
            impactGWP100_kgCO2e       : "Using EN 15804+A1 based GWP data (CML)",
            impactGWP100_kgCO2e_fossil: "Using EN 15804+A2 based fossil GWP data",
            traciGWP_kgCO2e           : "Using TRACI 2.1. based GWP data",
            impactGWP_direct_kgCO2e   : "Using direct emissions GWP data - not LCA-based dataset",
            impactGWP100_kgCO2e_total : "Using EN 15804+A2 based total GWP data"
    ].asImmutable()

    private String validateStage(Map impacts) {
        String validationMessage
        boolean valid = true

        if (impacts) {
            List<String> stages = impacts.keySet().toList()

            if (!allowedStages.containsAll(stages)) {
                validationMessage = "Invalid stages in resource ${resourceId} / ${profileId}: "
                stages.removeAll(allowedStages)
                int index = 0

                stages.each {
                    if (index == 0) {
                        validationMessage = "${validationMessage} '${it}'"
                    } else {
                        validationMessage = ", ${validationMessage} '${it}'"
                    }
                    index++
                }
                valid = false
            }

            if (valid) {
                for (String theStage in stages) {
                    Map impactCategoryWithValue = impacts.get(theStage)
                    List<String> impactsAsList

                    if (impactCategoryWithValue) {
                        impactsAsList = impactCategoryWithValue.keySet().toList()
                        for (String impact in impactsAsList) {
                            if (!allowedImpactCategories.contains(impact)) {
                                if (validationMessage) {
                                    validationMessage = validationMessage + ", ${impact}"
                                } else {
                                    validationMessage = "Invalid impactCategories in resource ${resourceId} / ${profileId} for stage: ${theStage}: ${impact}"
                                }
                            }
                        }
                        if (validationMessage) {
                            break
                        }
                    }
                }
            }
        }
        return validationMessage
    }

    static final String ECOINVENT36 = 'Ecoinvent36'
    static final String NMD30 = 'NMD30'

    static constraints = {
        stage nullable: true, blank: true
        resourceId(validator: { val, obj ->
            if (val && (val.contains(".") || val.contains(",") || val.contains(";"))) return ['resoure.resourceId.invalid_chars']
        }, nullable: false, blank: false)
        profileId(validator: { val, obj ->
            if (val && (val.contains(".") || val.contains(",") || val.contains(";"))) return ['resoure.profileId.invalid_chars']
        }, nullable: false, blank: false)
        importFile nullable: true, blank: true
        unit nullable: true, blank: true
        density nullable: true
        active nullable: true
        nameFI nullable: true
        nameNO nullable: true
        nameDE nullable: true
        nameFR nullable: true
        nameNL nullable: true
        nameES nullable: true
        nameSE nullable: true
        nameIT nullable: true
        nameHU nullable: true
        nameJP nullable: true
        profileNameEN nullable: true
        resourceGroup nullable: true
        area nullable: true
        defaultProfile nullable: true
        defaultThickness_mm nullable: true
        defaultThickness_in nullable: true
        massConversionFactor nullable: true
        maintenanceFrequency nullable: true
        serviceLife nullable: true
        energyConversionFactor nullable: true
        impactBasis nullable: true
        physicalPropertiesSource nullable: true
        environmentDataSourceType nullable: true
        environmentDataSourceStandard nullable: true
        environmentDataSource nullable: true
        environmentDataPeriod nullable: true
        impactGWP100_kgCO2e nullable: true
        impactODP_kgCFC11e nullable: true
        impactAP_kgSO2e nullable: true
        impactEP_kgPO4e nullable: true
        impactPOCP_kgEthenee nullable: true
        impactADP_kgSbe nullable: true
        impactADPElements_kgSbe nullable: true
        impactADPFossilFuels_MJ nullable: true
        impact_ecopoints nullable: true
        wasteHazardous_kg nullable: true
        wasteNonHazardous_kg nullable: true
        wasteRadioactive_kg nullable: true
        wasteRadioactiveHigh_kg nullable: true
        serviceLifeSource nullable: true
        cost_EUR nullable: true
        costConstruction_EUR nullable: true
        biogenicCarbonStorage_kgCO2e nullable: true
        impactGWP100_kgCO2e_biogenic nullable: true
        shareOfRecycledMaterial nullable: true
        shareOfRenewableMaterial nullable: true
        wasteResourceId nullable: true
        environmentalBenefitsResourceId nullable: true
        eolProcessingType nullable: true
        transportResourceId nullable: true
        transportResourceIdleg2 nullable: true
        transportResourceIdBoverket nullable: true
        transportResourceIdBoverketLeg2 nullable: true
        defaultTransportDistance_km nullable: true
        defaultTransportDistance_kmleg2 nullable: true
        defaultTransportBoverket nullable: true
        defaultTransportBoverketLeg2 nullable: true
        maintenanceResourceId nullable: true
        heatingDegreeDay nullable: true
        heatingDegreeDayNormalPeriod nullable: true
        renewablesUsedAsEnergy_MJ nullable: true
        renewablesUsedAsMaterial_MJ nullable: true
        nonRenewablesUsedAsEnergy_MJ nullable: true
        nonRenewablesUsedAsMaterial_MJ nullable: true
        recyclingMaterialUse_kg nullable: true
        renewableRecylingFuelUse_MJ nullable: true
        nonRenewableRecylingFuelUse_MJ nullable: true
        cleanWaterNetUse_m3 nullable: true
        reusableMaterialsOutput_kg nullable: true
        recyclableMaterialsOutput_kg nullable: true
        energyMaterialsOutput_kg nullable: true
        exportedEnergyOutput_MJ nullable: true
        superUserOnly nullable: true
        hasDesignPhase nullable: true
        hasOperatingPhase nullable: true
        hasPortfolio nullable: true
        canBeChildEntity nullable: true
        imageResource nullable: true
        minimizeBasicData nullable: true
        showTasks nullable: true
        environmentalBenefitsMultiplier nullable: true
        localHeatCoEff nullable: true
        solarRadiationHorizontal nullable: true
        m2PerInhabitant nullable: true
        groundTemperature nullable: true
        filterValue nullable: true
        pcr nullable: true
        epdProgram nullable: true
        epdProgramList nullable: true
        impactGWP_direct_kgCO2e nullable: true
        verificationStatus nullable: true
        applicationId nullable: true
        missingRequiredFilterFields nullable: true
        missingRequiredFilterValues nullable: true
        illegalFilterValues nullable: true
        failingMaxAgeFilterValues nullable: true
        failingActiveResourceIds nullable: true
        failingRequireIdenticalValues nullable: true
        failingFilterRules nullable: true
        failingVerifyValues nullable: true
        nonActiveResourceType nullable: true
        nonActiveSubType nullable: true
        allowedUnits nullable: true
        preConsumerWaste_kg nullable: true
        postConsumerWaste_kg nullable: true
        upstreamDB nullable: true
        pcrQuality nullable: true
        compensationFactor nullable: true
        dataProperties nullable: true
        traciAP_kgSO2e nullable: true
        traciEP_kgNe nullable: true
        traciNRPE_MJ nullable: true
        traciGWP_kgCO2e nullable: true
        traciODP_kgCFC11e nullable: true
        traciPOCP_kgO3e nullable: true
        virtual nullable: true
        compositeParts nullable: true
        useInRegistration nullable: true
        allowInRegistration nullable: true
        hideConstituentImpacts nullable: true
        unitForData nullable: true
        fuelUseLiters nullable: true
        shareOfLoad nullable: true
        unitCost nullable: true
        validity nullable: true
        epdURL nullable: true
        epdNumber nullable: true
        description nullable: true
        manufacturer nullable: true
        commercialName nullable: true
        impacts(validator: { val, obj ->
            String validationMessage = obj.validateStage(val)

            if (validationMessage) {
                return ['admin.resource.impacts.invalid', validationMessage]
            }
        }, nullable: true)
        resourceType nullable: true
        resourceSubType nullable: true
        technicalSpec nullable: true
        waterPollution_m3 nullable: true
        airPollution_m3 nullable: true
        downloadLink nullable: true
        recycledContent nullable: true
        productDescription nullable: true
        searchString nullable: true
        multipleProfiles nullable: true
        detailedDataSource nullable: true
        isoCountryCode nullable: true
        countryEnergyEfficiency nullable: true, inList: ["LOW", "NORMAL", "HIGH"]
        countryEnergyResourceId nullable: true
        countryIncinerationResourceId nullable: true
        countryRegulatedWasteHandling nullable: true
        benchmark nullable: true
        staticFullName nullable: true
        upstreamDBClassified nullable: true
        privateDatasetAccountId nullable: true
        privateDataset nullable: true
        isNewPrivateDataset nullable: true
        firstActivationTime nullable: true // for private dataset
        humanToxiHTP_kgDCB nullable: true
        freshWaterToxiFAETP_kgDCB nullable: true
        seaWaterToxiMAETP_kgDCB nullable: true
        terrestrialToxiTETP_kgDCB nullable: true
        shadowPrice nullable: true
        privateDatasetAccountName nullable: true
        carShare nullable: true
        publicBusShare nullable: true
        publicRailShare nullable: true
        walkBikeShare nullable: true
        carDriveDistance_km nullable: true
        publicTransportDistance_km nullable: true
        construction nullable: true
        constructionId nullable: true
        constructionType nullable: true
        //EPD - Product Lists
        productDataList nullable: true
        productDataListId nullable: true
        productDataListType nullable: true
        // END
        resourceTypeCostMultiplier nullable: true
        excludeFromBenchmark nullable: true
        representativeArea nullable: true
        decideLaterResource nullable: true
        channelTokens nullable: true
        impactGWP100_kgCO2e_fossil nullable: true
        impactGWP100_kgCO2e_luluc nullable: true
        biogenicCarbonIsSustainable nullable: true
        biogenicCarbonStorageProduct_kgCO2 nullable: true
        biogenicCarbonStoragePackaging_kgCO2 nullable: true
        carbonisationPotentialProduct_kgCO2e nullable: true
        neighbouringCountries nullable: true
        additionalArea nullable: true
        areas nullable: true
        isoCodesByAreas nullable: true
        resistanceProperties nullable: true
        dataManuallyVerified nullable: true
        maxBuildingDepth_m nullable: true
        doorsToGroundfFloorAreaRatio nullable: true
        windowsToAboveGroundAreaRatio nullable: true
        windowsMaximumOfExternalWalls nullable: true
        balconiesToAboveGroundAreaRatio nullable: true
        internalFloorHeight_m nullable: true
        internalWallsToExternalWallsRatio nullable: true
        floorThickness_m nullable: true
        envelopeThickness_m nullable: true
        lenghtDepthRatio nullable: true
        roofShapeEfficiencyFactor nullable: true
        buildingShapeEfficiencyFactor nullable: true
        maxSpanWithoutColumns_m nullable: true
        maxStairCaseDistance_m nullable: true
        externalPavedAreasPerGFA nullable: true
        combinedUnits nullable: true
        simulationRegionIds nullable: true
        simulationToolBuildingType nullable: true
        energyScenario nullable: true
        passivhaus_EPH0	nullable: true
        passivhaus_X	nullable: true
        passivhaus_K1	nullable: true
        passivhaus_K2	nullable: true
        passivhaus_Beta nullable: true
        passivhaus_waterEnergy nullable: true
        regionReference nullable: true
        defaultCdEnergyResource nullable: true
        impactGWP100_AR5_kgCO2e nullable: true
        resourceDepletionWater_m3 nullable: true
        ionisingRadiation_kgU235eq nullable: true
        particulateMatter_kgPM25eq nullable: true
        impactODP_WMO1999_kgCFC11e nullable: true
        landTransformation_m2 nullable: true
        biogenicCarbonSeparated nullable: true
        biogenicCarbonEstimated nullable: true
        defaultTransportDefinition nullable: true
        impactNonLinear nullable: true
        defaultHeatCarrierId nullable: true
        impactPERRT_MJ nullable: true
        impactPERNRT_MJ nullable: true
        countryEnergyRecoveryResourceId nullable: true
        countryEnergyRecoveryResourceProfileId nullable: true
        countryEnergyResourceProfileId nullable: true
        countryEnergyResourceProfileId_v2 nullable: true
        thermalLambda nullable: true
        biogenicCarbonContentProduct_kgC nullable: true
        biogenicCarbonContentPackaging_kgC nullable: true
        impactEPFreshwater_kgPO4e nullable: true
        impactEPMarine_kgNe nullable: true
        impactEPTerrestrial_molNe nullable: true
        impactPOCP_kgNMVOCe nullable: true
        cleanWaterDeprivation_m3 nullable: true
        particulateMatter_incidence nullable: true
        ecoToxicityFreshwater_CTUe nullable: true
        humanToxicityCancer_CTUh nullable: true
        humanToxicityNonCancer_CTUh nullable: true
        potentialSoilQualityIndex nullable: true
        inheritConstructionServiceLife nullable: true
        serviceLifeOverride nullable: true
        countryCurrencySymbol nullable: true
        imgLink nullable: true
        copyrightText nullable: true
        imgDescription nullable: true
        noteForDisabling nullable: true
        epdcFilePath nullable: true
//        nmd3ElementId nullable: true // REL-304 deprecate this field
        nmdProductId nullable: true
        epdNumberAlt nullable: true
        firstUploadTime nullable: true
        deactivateTime nullable: true
        showState nullable: true
        datasetAdditionalQuestionAnswers nullable: true
        linkedDatasetManualIds nullable: true
        isState nullable: true
        stateCode nullable: true
        genericDataTopUpPercentage nullable: true
        privateDatasetImportedByUserId nullable: true
        impactAP_molHeq nullable: true
        impactODP_WMO2014_kgCFC11e nullable: true
        carbonSavingVsBenchmark nullable: true
        carbonBenchmark nullable: true
        productSpecificOrLowVariation nullable: true
        region nullable: true
        isMultiPart nullable: true
        multipart nullable: true
        isDummy nullable: true
        disableAutomaticPurposes nullable: true
        automaticallyEnabledPurposes nullable: true
        manuallyEnabledPurposes nullable: true
        enabledPurposes nullable: true
        showWarning nullable: true
        warningText nullable: true
        specificCharacteristics nullable: true
        datasetType nullable: true
        marketDataset nullable: true
        impactADPElements_FR_kgSbe nullable: true
        additionalInfo nullable: true
        casNumber nullable: true
        resourceQualityWarning nullable: true, inList: ["WARN", "BLOCK"]
        resourceQualityWarningText nullable: true
        nmdElementId nullable: true
        nmdSchalingsFormuleID nullable: true
        nmdSchalingsFormuleA1 nullable: true
        nmdSchalingsFormuleA2 nullable: true
        nmdSchalingsFormuleB1 nullable: true
        nmdSchalingsFormuleB2 nullable: true
        nmdSchalingsFormuleC nullable: true
        nmdSchalingMinX1 nullable: true
        nmdSchalingMinX2 nullable: true
        nmdSchalingMaxX1 nullable: true
        nmdSchalingMaxX2 nullable: true
        nmdAlternativeUnit nullable: true
        nmdCategoryId nullable: true
        nmdProductApplication nullable: true
        nmdDatabaseVersion nullable: true
        nmdProductType nullable: true
        rechtev_grootheid_dim1 nullable: true
        rechtev_eenheid_dim1 nullable: true
        rechtev_grootheid_dim2 nullable: true
        rechtev_eenheid_dim2 nullable: true
        defaultThickness_mm2 nullable: true
        nmdSchalingUnit nullable: true
        nmdElementCode nullable: true
        nmdElementnaam nullable: true
        impact_AP_ISO21930_kg_SO2e nullable: true
        wasteRadioactiveIntAndLow_kg nullable: true
        impactRE_MJ nullable: true
        quantityGHG_CO2_fossil_kg nullable: true
        quantityGHG_CO2_biogenic_kg nullable: true
        quantityGHG_CH4_fossil_kg nullable: true
        quantityGHG_CH4_biogenic_kg nullable: true
        quantityGHG_N2O_kg nullable: true
        quantityGHG_HFC_kg nullable: true
        quantity_PFC_kg nullable: true
        quantity_SF6_kg nullable: true
        impactADPElements_NSF_kgSbe nullable: true
        wasteHazardous_FR_kg nullable: true
        wasteNonHazardous_FR_kg nullable: true
        privateDataType nullable: true
        brandImageId nullable: true
        impactGWP_fossil_kg_CO2e_EnvDec nullable: true
        siteWastage nullable: true
        siteWastageBoverket nullable: true
        exportedEnergyOutput_EEE_MJ nullable: true
        exportedEnergyOutput_ETE_MJ nullable: true
        impactADPFossilFuels_kgSbe nullable: true
        edited nullable: true
        allowToExportToExternalSources nullable: true
        thirdPartyVerifier nullable: true
        //REL2108-141
        refrigerantResourceId nullable: true
        useStagesSeparated nullable: true
        stockC_kgC nullable: true
        stockCEstimated nullable: true
        groupingName nullable: true
        mirrorConstructionCombinedUnits nullable: true
        nmdUnitConversionFactor nullable: true
        nmdUpdateTime nullable: true
        isConstituent nullable: true
        isConstruction nullable: true
        nmdKindElementIds nullable: true
        repairRateJP nullable: true
        nmdReuseAvailable nullable: true
        nmdReuseFactor nullable: true
        functionalUnit nullable: true
        verifiersName nullable: true
        verificationDate nullable: true
        verificationProof nullable: true
        verificationNumber nullable: true
    }

    def beforeUpdate() {
        if(active == false && deactivateTime == null){
            deactivateTime = new Date()
        } else if(active == true){
            deactivateTime = null
        }
        edited = new Date()
    }
    def getCountryEffiencyNumber() {
        Double number

        if (countryEnergyEfficiency) {
            if ("LOW".equals(countryEnergyEfficiency)) {
                number = 1.18
            } else if ("NORMAL".equals(countryEnergyEfficiency)) {
                number = 1D
            } else {
                number = 0.91
            }
        }
        return number
    }

    // These subType value getters are used from resultCategories
    def getSubtypeDeconstructionType() {
        return subType?.deconstructionType
    }

    def getHasUnit() {
        if (unitForData?.trim()) {
            return true
        } else {
            return false
        }
    }

    @Override
    public int compareTo(Object obj) {
        if (obj && obj instanceof Resource) {
            if (optimiResourceService.getLocalizedName(this) && optimiResourceService.getLocalizedName(obj)) {
                optimiResourceService.getLocalizedName(this).compareTo(optimiResourceService.getLocalizedName(obj))
            } else {
                return 1
            }
        } else {
            return 1
        }
    }

    def getHasStages() {
        String returnable

        if (impacts && impacts.keySet().size() > 1) {
            return "TRUE"
        } else {
            return "FALSE"
        }
    }

    def getNoStages() {
        if (!impacts || impacts?.keySet()?.size() < 2) {
            return "TRUE"
        } else {
            return "FALSE"
        }
    }

    //TODO: this one is slow in a loop, please use ResourceCache/ResourceTypeCache LOCAL instances till we have a better solution
    @Deprecated
    ResourceType getResourceTypeObject() {
        ResourceType resourceTypeObject = null

        if (resourceType && resourceSubType) {
            resourceTypeObject = resourceTypeService.getResourceType(resourceType, resourceSubType)
        } else if (resourceType) {
            resourceTypeObject = resourceTypeService.getResourceType(resourceType)
        } else if (resourceSubType) {
            String rt = resourceTypeService.getResourceTypeBySubType(resourceSubType)

            if (rt) {
                resourceTypeObject = resourceTypeService.getResourceType(rt, resourceSubType)
            }
        }

        if (resourceTypeObject == null && (resourceType || resourceSubType)) {
            log.error("Resource ${resourceId} / ${profileId} no resourceType found with params resourceType: ${resourceType} and subType ${resourceSubType}.")
            flashService.setErrorAlert("Resource ${resourceId} / ${profileId} no resourceType found with params resourceType: ${resourceType} and subType ${resourceSubType}", true)
        }
        return resourceTypeObject
    }

    //TODO: can be very slow in a loop, please use ResourceCache/ResourceTypeCache LOCAL instances till we have a better solution
    @Deprecated
    ResourceType getSubType() {
        if(log.isTraceEnabled()) {
            log.trace("Fetching resource type for ${resourceId}")
        }
        long now = System.currentTimeMillis()
        ResourceType subResourceType = null

        if (resourceSubType) {
            subResourceType = resourceTypeService.getResourceTypeByResourceTypeAndSubType(resourceType, resourceSubType, Boolean.TRUE)

            if (!subResourceType && (resourceType||resourceSubType)) {
                log.error("Resource ${resourceId} / ${profileId} no subResourceType found with params resourceType: ${resourceType} and subType ${resourceSubType}. Elapsed: ${System.currentTimeMillis() - now}")
                flashService.setErrorAlert("Resource ${resourceId} / ${profileId} no subResourceType found with params resourceType: ${resourceType} and subType ${resourceSubType}", true)
            }
        }
        return subResourceType
    }

    def getDefaultUnit() {
        return subType?.defaultUnitForAddingData
    }

    def getIsExpiredDataPoint() {
        boolean isExpiredDataPoint = false

        if(environmentDataSourceType && environmentDataPeriod && "LCA".equals(applicationId) && resourceGroup && CollectionUtils.containsAny(["buildingTechnology","BMAT"], resourceGroup)) {
            Integer yearNow = Year.now().getValue()

            if (environmentDataSourceType.equalsIgnoreCase(com.bionova.optimi.core.Constants.GENERIC_EDST)) {
                isExpiredDataPoint = yearNow - environmentDataPeriod > 10
            } else {
                isExpiredDataPoint = yearNow - environmentDataPeriod > 5
            }
        }
        return isExpiredDataPoint

    }

    def getExpirationYear() {
        Integer expirationYear

        if(environmentDataSourceType && environmentDataPeriod && "LCA".equals(applicationId) && resourceGroup && CollectionUtils.containsAny(["buildingTechnology","BMAT"], resourceGroup)) {
            if (environmentDataSourceType.equalsIgnoreCase(com.bionova.optimi.core.Constants.GENERIC_EDST)) {
                expirationYear = environmentDataPeriod + 10
            } else {
                expirationYear = environmentDataPeriod + 5
            }
        }
        return expirationYear
    }

    def getAllowVariableThickness() {
        Boolean allow = Boolean.FALSE

        if (!impactNonLinear && (density||"m2".equals(unitForData)) && (defaultThickness_in||defaultThickness_mm)) {
            allow = Boolean.TRUE
        }
        return allow
    }

    def getIsAdditionalQuestionInherited(Question additionalQuestion) {
        boolean inherit = false

        if (additionalQuestion) {
            if ("serviceLife".equals(additionalQuestion.questionId) && inheritConstructionServiceLife) {
                inherit = true
            }
        }
        return inherit
    }

    def getIsLocalCompsApplicable() {
        boolean applicable = false
        def resourceSubType = getSubType()

        if (!privateDataset && resourceSubType && (resourceSubType.requiredElectricityKwh != null || resourceSubType.requiredElectricityKwhPerKg != null) && (resourceSubType.standardUnit && combinedUnits && combinedUnits*.toLowerCase().contains(resourceSubType.standardUnit.toLowerCase()))) {
            applicable = true
        }
        return applicable
    }

    def getPassesBenchmarkFilter(String benchmark) {
        Boolean passes = false
        if (benchmark) {
            if ("co2_cml".equals(benchmark)) {
                if (impactGWP100_kgCO2e != null && dataProperties?.contains("CML") && "LCA".equals(applicationId)) {
                    passes = true
                }
            } else if ("co2_traci".equals(benchmark)) {
                if (traciGWP_kgCO2e != null && dataProperties?.contains("TRACI")) {
                    passes = true
                }
            } else if ("co2_inies_a1-c4".equals(benchmark)) {
                if ("INIES".equalsIgnoreCase(epdProgram) && impacts?.get("A1-C4") && dataProperties && CollectionUtils.containsAny(["CML", "INIES"], dataProperties)) {
                    passes = true
                }
            } else if ("leed_epd_cml".equals(benchmark)) {
                if (dataProperties?.contains("CML") && verificationStatus?.equalsIgnoreCase("Verified")) {
                    passes = true
                }
            } else if ("leed_epd_traci".equals(benchmark)) {
                if (dataProperties?.contains("TRACI") && verificationStatus?.equalsIgnoreCase("Verified")) {
                    passes = true
                }
            } else if ("ecopoints_direct".equals(benchmark)) {
                if (dataProperties?.contains("IMPACT") && impact_ecopoints != null) {
                    passes = true
                }
            } else if ("shadowprice_direct".equals(benchmark)) {
                if (shadowPrice != null) {
                    passes = true
                }
            } else if ("utilities".equals(benchmark)) {
                if (impactGWP100_kgCO2e != null && dataProperties?.contains("LCA") && "LCA-utility".equals(applicationId)) {
                    passes = true
                }
            }
        }
        return passes
    }

    def getQMetadata() {
        return Math.sqrt((
                Math.pow(com.bionova.optimi.core.Constants.GENERIC_EDST.equals(environmentDataSourceType) ? 0.2 : 0.02, 2) +
                        Math.pow(!productSpecificOrLowVariation ? 0.2 : 0.02, 2) +
                        Math.pow("plant".equals(environmentDataSourceType) ? 0.02 : 0.2, 2)
        ))
    }

    def getTotalWasteAndMaterialOutputs_kg() {
        return (wasteHazardous_kg ?: 0) +
                (wasteNonHazardous_kg ?: 0) +
                (wasteRadioactive_kg ?: 0) +
                (reusableMaterialsOutput_kg ?: 0) +
                (recyclableMaterialsOutput_kg ?: 0) +
                (energyMaterialsOutput_kg ?: 0)
    }

    def getAllEnergyUsedTotal_kWh_SUM() {
        Double totalEnergyUseMJ = (renewablesUsedAsEnergy_MJ ?: 0) +
                (nonRenewablesUsedAsEnergy_MJ ?: 0) +
                (renewableRecylingFuelUse_MJ ?: 0) +
                (nonRenewableRecylingFuelUse_MJ ?: 0)

        if (totalEnergyUseMJ) {
            return (totalEnergyUseMJ / 3.6) // Convert MJ to kWh
        } else {
            return 0
        }
    }

    def getImpactGWP100_kgCO2e_total() {
        //#16269 special case if all 3 are absent
        if (impactGWP100_kgCO2e_fossil == null
                && impactGWP100_kgCO2e_biogenic == null
                && impactGWP100_kgCO2e_luluc == null) {
            return null
        }
        return (impactGWP100_kgCO2e_fossil ?: 0) +
                (impactGWP100_kgCO2e_biogenic ?: 0) +
                (impactGWP100_kgCO2e_luluc ?: 0)
    }

    def getImpactGWPdirectFailoverGetter(List<String> stages = null) {
        return getImpactByPropsHierarchyAndStages(GWP_DIRECT_FAILOVER_HIERARCHY, stages, 0)
    }

    def getImpactGWPlifeCycleFailoverGetter(List<String> stages = null) {
        return getImpactByPropsHierarchyAndStages(GWP_LIFE_CYCLE_FAILOVER_HIERARCHY, stages, 0)
    }

    def getImpactGWPdirectFailoverGetterBasis() {
        return (
                impactGWP_direct_kgCO2e != null ? GWP_MESSAGES.get('impactGWP_direct_kgCO2e') :
                        impactGWP100_kgCO2e_fossil != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e_fossil') :
                                impactGWP100_kgCO2e != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e') :
                                        traciGWP_kgCO2e != null ? GWP_MESSAGES.get('traciGWP_kgCO2e') : null
        )
    }

    def getImpactGWPlifeCycleFailoverGetterBasis() {
        return (
                impactGWP100_kgCO2e_fossil != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e_fossil') :
                        impactGWP100_kgCO2e != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e') :
                                traciGWP_kgCO2e != null ? GWP_MESSAGES.get('traciGWP_kgCO2e') : null
        )
    }

    def getImpactGWPA1FailoverGetter(List<String> stages = null) {
        return getImpactByPropsHierarchyAndStages(GWPA1_FAILOVER_HIERARCHY, stages)
    }

    def getImpactGWPA1FailoverGetterBasis() {
        return (
                impactGWP100_kgCO2e != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e') :
                        impactGWP100_kgCO2e_fossil != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e_fossil') :
                                traciGWP_kgCO2e != null ? GWP_MESSAGES.get('traciGWP_kgCO2e') :
                                        impactGWP_direct_kgCO2e != null ? GWP_MESSAGES.get('impactGWP_direct_kgCO2e') :
                                                null
        )
    }

    def getImpactGWPA2TotalFailoverGetter(List<String> stages = null) {
        return getImpactByPropsHierarchyAndStages(GWPA2_TOTAL_FAILOVER_HIERARCHY, stages)
    }

    def getImpactGWPA2TotalFailoverGetterBasis() {
        return (
                impactGWP100_kgCO2e_total != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e_total') :
                        impactGWP100_kgCO2e != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e') :
                                traciGWP_kgCO2e != null ? GWP_MESSAGES.get('traciGWP_kgCO2e') :
                                        impactGWP_direct_kgCO2e != null ? GWP_MESSAGES.get('impactGWP_direct_kgCO2e') :
                                                null
        )
    }

    def getImpactGWPA2FossilFailoverGetter(List<String> stages = null) {
        return getImpactByPropsHierarchyAndStages(GWPA2_FOSSIL_FAILOVER_HIERARCHY, stages)
    }

    def getImpactGWPA2FossilFailoverGetterBasis() {
        return (impactGWP100_kgCO2e_fossil != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e_fossil') :
                impactGWP100_kgCO2e != null ? GWP_MESSAGES.get('impactGWP100_kgCO2e') :
                        traciGWP_kgCO2e != null ? GWP_MESSAGES.get('traciGWP_kgCO2e') :
                                impactGWP_direct_kgCO2e != null ? GWP_MESSAGES.get('impactGWP_direct_kgCO2e') :
                                        null)
    }

    def getImpactByPropsHierarchyAndStages(List<String> propsHierarchy, List<String> stages = null, def defaultValue = null) {
        def result

        if (stages) {
            String targetPropertyName = propsHierarchy.find { String propertyName -> this.getProperty(propertyName) != null }

            if (targetPropertyName && (targetPropertyName in allowedImpactCategories)) {
                result = stages.findAll { String stage -> stage in allowedStages }.findResult { String stage ->
                    impacts?.get(stage)?.getOrDefault(targetPropertyName, null) as Double
                }
            }
        }

        if (!stages || (result == null)) {
            result = propsHierarchy.findResult defaultValue, { String propertyName -> this.getProperty(propertyName) }
        }

        return result
    }

    def getResolvedGWPImpacts(){
        Map<String, Double> resolvedImpacts = [:]

        if (benchmark?.resultByUnit?.get("co2_cml")) {
            resolvedImpacts.putAll(benchmark.resultByUnit.get("co2_cml"))
        } else if (benchmark?.resultByUnit?.get("co2_traci")) {
            resolvedImpacts.putAll(benchmark.resultByUnit.get("co2_traci"))
        } else if (unitForData) {
            if (impactGWP100_kgCO2e != null) {
                if (impactGWP100_kgCO2e == 0 && impacts?.get("A1-C4")?.get("impactGWP100_kgCO2e")) {
                    resolvedImpacts.put(unitForData, impacts.get("A1-C4").get("impactGWP100_kgCO2e") as Double)
                } else {
                    resolvedImpacts.put(unitForData, impactGWP100_kgCO2e)
                }
            } else if (traciGWP_kgCO2e != null) {
                resolvedImpacts.put(unitForData, traciGWP_kgCO2e)
            } else if (impactGWP_direct_kgCO2e != null) {
                resolvedImpacts.put(unitForData, impactGWP_direct_kgCO2e)
            }
        }

        return resolvedImpacts
    }

    def getResolvedGWPImpactsByStages(List<String> targetStages) {
        Map<String, Double> resolvedImpacts = [:]

        if (impacts) {
            targetStages.each { String targetStage ->
                Map<String, Object> stageImpacts = impacts.get(targetStage) ?: [:]
                if (stageImpacts) {
                    if (stageImpacts.get('impactGWP100_kgCO2e') != null) {
                        Double gwp = stageImpacts.get('impactGWP100_kgCO2e') as Double
                        resolvedImpacts.put(targetStage, gwp)
                    } else if (stageImpacts.get('traciGWP_kgCO2e') != null) {
                        Double gwp = stageImpacts.get('traciGWP_kgCO2e') as Double
                        resolvedImpacts.put(targetStage, gwp)
                    } else if (stageImpacts.get('impactGWP_direct_kgCO2e') != null) {
                        Double gwp =  stageImpacts.get('impactGWP_direct_kgCO2e') as Double
                        resolvedImpacts.put(targetStage, gwp)
                    }
                }
            }
        }
        return resolvedImpacts
    }

    def getShadowPriceForStage(String stage) {
        Double sp = 0.0

        if (stage) {
            Map stagedResults = impacts?.get(stage)

            if (stagedResults) {
                if (stagedResults.get("impactADPElements_kgSbe")) {
                    sp += stagedResults.get("impactADPElements_kgSbe") * 0.16
                }
                if (stagedResults.get("impactADPFossilFuels_MJ")) {
                    sp += stagedResults.get("impactADPFossilFuels_MJ") * 0.16
                }
                if (stagedResults.get("impactGWP100_kgCO2e")) {
                    sp += stagedResults.get("impactGWP100_kgCO2e") * 0.05
                }
                if (stagedResults.get("impactODP_kgCFC11e")) {
                    sp += stagedResults.get("impactODP_kgCFC11e") * 30
                }
                if (stagedResults.get("impactPOCP_kgEthenee")) {
                    sp += stagedResults.get("impactPOCP_kgEthenee") * 2
                }
                if (stagedResults.get("impactAP_kgSO2e")) {
                    sp += stagedResults.get("impactAP_kgSO2e") * 4
                }
                if (stagedResults.get("impactEP_kgPO4e")) {
                    sp += stagedResults.get("impactEP_kgPO4e") * 9
                }
                if (stagedResults.get("humanToxiHTP_kgDCB")) {
                    sp += stagedResults.get("humanToxiHTP_kgDCB") * 0.09
                }
                if (stagedResults.get("freshWaterToxiFAETP_kgDCB")) {
                    sp += stagedResults.get("freshWaterToxiFAETP_kgDCB") * 0.03
                }
                if (stagedResults.get("seaWaterToxiMAETP_kgDCB")) {
                    sp += stagedResults.get("seaWaterToxiMAETP_kgDCB") * 0.0001
                }
                if (stagedResults.get("terrestrialToxiTETP_kgDCB")) {
                    sp += stagedResults.get("terrestrialToxiTETP_kgDCB") * 0.06
                }
            }
        }
        return sp
    }

    def getShadowPriceGetter() {
        Double shadowPrice = 0.0

        if (impactADPElements_kgSbe) {
            shadowPrice += impactADPElements_kgSbe * 0.16
        }
        if (impactADPFossilFuels_MJ) {
            shadowPrice += impactADPFossilFuels_MJ * 0.16
        }
        if (impactGWP100_kgCO2e) {
            shadowPrice += impactGWP100_kgCO2e * 0.05
        }
        if (impactODP_kgCFC11e) {
            shadowPrice += impactODP_kgCFC11e * 30
        }
        if (impactPOCP_kgEthenee) {
            shadowPrice += impactPOCP_kgEthenee * 2
        }
        if (impactAP_kgSO2e) {
            shadowPrice += impactAP_kgSO2e * 4
        }
        if (impactEP_kgPO4e) {
            shadowPrice += impactEP_kgPO4e * 9
        }
        if (humanToxiHTP_kgDCB) {
            shadowPrice += humanToxiHTP_kgDCB * 0.09
        }
        if (freshWaterToxiFAETP_kgDCB) {
            shadowPrice += freshWaterToxiFAETP_kgDCB * 0.03
        }
        if (seaWaterToxiMAETP_kgDCB) {
            shadowPrice += seaWaterToxiMAETP_kgDCB * 0.0001
        }
        if (terrestrialToxiTETP_kgDCB) {
            shadowPrice += terrestrialToxiTETP_kgDCB * 0.06
        }
        return shadowPrice
    }
}