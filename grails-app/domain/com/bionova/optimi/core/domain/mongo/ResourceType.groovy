package com.bionova.optimi.core.domain.mongo


import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ResourceType implements Validateable {
    static mapWith = "mongo"
    ObjectId id
    String resourceType
    List<String> linkedSubTypes
    Boolean linkedSubTypeCheck
    Boolean multipart
    String subType
    List<String> subTypes
    Double wasteShareInUsePhase // duplicate
    Double wasteShareInUsePhase_Low
    Double wasteShareInUsePhase_High
    Double compensationFactor
    Double requiredElectricityKwh
    Double requiredElectricityKwh_v2 // DEPRECATED, v2 uses requiredElectricityKwhPerKg
    Double requiredElectricityKwhPerKg // For new local compensation
    Double subTypeMinImpacts_kg
    Double subTypeMaxImpacts_kg
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
    Boolean active
    String importFile
    String importTime
    Double siteWastage
    Double siteWastageBoverket
    Double unitCostEUR // unneeded
    String standardUnit
    String unitCostDenominator
    BenchmarkResult benchmarkResult
    Map<String, Map<String, Double>> resourceAveragesByUpstreamDBClassified
    Map<String, Integer> resourceAveragesByUpstreamDBClassifiedAmounts
    String transportResourceId
    String transportResourceIdAus
    String transportResourceIdMiles
    String transportResourceIdBoverket
    String transportResourceIdBoverketLeg2
    Double shareOfRecycledMaterial
    Double shareOfRenewableMaterial
    Double annualRepairRate
    Integer failingGivenThreshold
    Integer serviceLifeCommercial
    Integer serviceLifeTechnical
    Integer serviceLifeRICS
    Double defaultTransportNordic
    Double defaultTransportUK
    Double defaultTransportEurope
    Double defaultTransportUS
    Double defaultTransportCanada
    Double defaultTransportAustralasia
    Double defaultTransportGlobal
    Double defaultTransportRICS
    Double defaultTransportRICSleg2
    Double defaultTransportBoverket
    Double defaultTransportBoverketLeg2
    String transportResourceIdRICS
    String transportResourceIdRICSleg2
    String uniClass
    String uniClassBRE
    String csiMasterformat
    Double maxThickness_mm
    Double minThickness_mm
    String technicalSpec
    Double annualMaintAsShareOfCost
    Double annualRepairAsShareOfCost
    List<String> requiredUnits
    List<String> avoidableUnits
    String defaultUnitForAddingData
    String deconstructionType
    Double localCompensationShareLimit

    String eolProcessRegulated
    String eolProcessNonRegulated
    List<String> eolProcessAllowedChoices
    Boolean impossibleToReuseAsMaterial

    String subTypeGroup
    String shortNameFI
    String shortNameEN
    String shortNameNO
    String shortNameDE
    String shortNameFR
    String shortNameNL
    String shortNameES
    String shortNameSE
    String shortNameIT

    Double energyConversionFactorkWhPerKg
    Double cementitiousContentPercentage

    String wasteResourceId // failover for legacy v1 EOL in calculation
    String environmentalBenefitsResourceId // failover for legacy v1 EOL in calculation
    String eolProcessingType
    Double environmentalBenefitsMultiplier // failover for legacy v1 EOL in calculation

    String BREEAMIntlElement
    String lotsFranceEC
    String subSectionsFEC
    String elementCategory
    String talo2000
    String BSAB
    String BSAB83
    String classificationOmniClass
    String NS3541
    String classificationEnLevels
    Double repairRateJP

    String siteWastageReference
    String siteWastageBoverketReference
    String defaultTransportBoverketReference
    String defaultTransportRICSReference
    String defaultTransportNordicReference
    String defaultTransportGlobalReference
    String defaultTransportEuropeReference
    String defaultTransportUKReference
    String defaultTransportUSReference
    String defaultTransportCanadaReference
    String defaultTransportAustralasiaReference
    String serviceLifeCommercialReference
    String serviceLifeTechnicalReference
    String serviceLifeRICSReference

    BigDecimal defaultTransportJapan
    String transportResourceIdJapan
    Integer serviceLifeJapan
    BigDecimal siteWastageJapan

    static constraints = {
        subType nullable: true
        linkedSubTypes nullable: true
        linkedSubTypeCheck nullable: true
        multipart nullable: true
        wasteShareInUsePhase nullable: true
        compensationFactor nullable: true
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
        active nullable: true
        importFile nullable: true
        importTime nullable: true
        unitCostEUR nullable: true
        standardUnit nullable: true
        unitCostDenominator nullable: true, inList: ["kg", "m3"]
        benchmarkResult nullable: true
        requiredElectricityKwh nullable: true
        requiredElectricityKwh_v2 nullable: true
        resourceAveragesByUpstreamDBClassified nullable: true
        resourceAveragesByUpstreamDBClassifiedAmounts nullable: true
        transportResourceId nullable: true
        transportResourceIdMiles nullable: true
        transportResourceIdRICS nullable: true
        transportResourceIdRICSleg2 nullable: true
        transportResourceIdBoverket nullable: true
        transportResourceIdBoverketLeg2 nullable: true
        shareOfRecycledMaterial nullable: true
        shareOfRenewableMaterial nullable: true
        annualRepairRate nullable: true
        failingGivenThreshold nullable: true
        serviceLifeCommercial nullable: true
        serviceLifeTechnical nullable: true
        serviceLifeRICS nullable: true
        defaultTransportNordic nullable: true
        defaultTransportUK nullable: true
        defaultTransportEurope nullable: true
        defaultTransportUS nullable: true
        defaultTransportGlobal nullable: true
        defaultTransportRICS nullable: true
        defaultTransportRICSleg2 nullable: true
        uniClass nullable: true
        uniClassBRE nullable: true
        csiMasterformat nullable: true
        maxThickness_mm nullable: true
        minThickness_mm nullable: true
        technicalSpec nullable: true
        requiredUnits nullable: true
        avoidableUnits nullable: true
        annualMaintAsShareOfCost nullable: true
        annualRepairAsShareOfCost nullable: true
        deconstructionType nullable:true
        defaultTransportCanada nullable: true
        defaultUnitForAddingData nullable: true
        defaultTransportAustralasia nullable: true
        defaultTransportBoverket nullable: true
        defaultTransportBoverketLeg2 nullable: true
        wasteShareInUsePhase_Low nullable: true
        wasteShareInUsePhase_High nullable: true
        transportResourceIdAus nullable: true
        localCompensationShareLimit nullable: true
        subTypeGroup nullable: true
        eolProcessRegulated nullable: true
        eolProcessNonRegulated nullable: true
        eolProcessAllowedChoices nullable: true
        requiredElectricityKwhPerKg nullable: true
        impossibleToReuseAsMaterial nullable: true
        shortNameFI nullable: true
        shortNameEN nullable: true
        shortNameNO nullable: true
        shortNameDE nullable: true
        shortNameFR nullable: true
        shortNameNL nullable: true
        shortNameES nullable: true
        shortNameSE nullable: true
        shortNameIT nullable: true
        energyConversionFactorkWhPerKg nullable: true
        cementitiousContentPercentage nullable: true
        wasteResourceId nullable: true
        environmentalBenefitsResourceId nullable: true
        environmentalBenefitsMultiplier nullable: true
        eolProcessingType nullable: true
        subTypeMinImpacts_kg nullable: true
        subTypeMaxImpacts_kg nullable: true
        BREEAMIntlElement(nullable: true)
        lotsFranceEC nullable: true
        subSectionsFEC nullable: true
        elementCategory nullable: true
        talo2000 nullable: true
        BSAB(nullable: true)
        BSAB83(nullable: true)
        classificationOmniClass nullable: true
        NS3541(nullable: true)
        classificationEnLevels nullable: true
        siteWastage nullable: true
        siteWastageBoverket nullable: true
        repairRateJP nullable: true
        siteWastageReference nullable: true
        siteWastageBoverketReference nullable: true
        defaultTransportBoverketReference nullable: true
        defaultTransportRICSReference nullable: true
        defaultTransportNordicReference nullable: true
        defaultTransportGlobalReference nullable: true
        defaultTransportEuropeReference nullable: true
        defaultTransportUKReference nullable: true
        defaultTransportUSReference nullable: true
        defaultTransportCanadaReference nullable: true
        defaultTransportAustralasiaReference nullable: true
        serviceLifeCommercialReference nullable: true
        serviceLifeTechnicalReference nullable: true
        serviceLifeRICSReference nullable: true
        defaultTransportJapan nullable: true
        transportResourceIdJapan nullable: true
        serviceLifeJapan nullable: true
        siteWastageJapan nullable: true
    }

    static transients = [
            'isSubType',
            'subTypes',
            'resourceAveragesByUpstreamDBClassified',
            'resourceAveragesByUpstreamDBClassifiedAmounts',
            'failingGivenThreshold'
    ]

    static embedded = ['benchmarkResult']

    static mapping = {
        resourceType index: true
        subType index: true
        compoundIndex resourceType:1, subType:1
    }

    def beforeUpdate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        importTime = dateFormat.format(new Date())
    }

    def beforeInsert() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        importTime = dateFormat.format(new Date())
    }

    def getIsSubType() {
        if (subType) {
            return true
        } else {
            return false
        }
    }

    String getBREEAMIntlElement() {
        return BREEAMIntlElement
    }

    String getNS3541() {
        return NS3541
    }

    String getBSAB() {
        return BSAB
    }

    String getBSAB83() {
        return BSAB83
    }

    String getLotsFranceEC() {
        return lotsFranceEC
    }

    String getSubSectionsFEC() {
        return subSectionsFEC
    }

    String getElementCategory() {
        return elementCategory
    }

    String getTalo2000() {
        return talo2000
    }

    String getClassificationOmniClass() {
        return classificationOmniClass
    }

    String getClassificationEnLevels() {
        return classificationEnLevels
    }
}
