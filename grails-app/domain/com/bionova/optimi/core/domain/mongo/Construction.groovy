package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.service.UserService
import grails.compiler.GrailsCompileStatic
import grails.util.Holders
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

@GrailsCompileStatic
class Construction implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    List<String> resourceIds
    String name
    String unit
    String imperialUnit // Just to persist and show construction imperial unit if user given
    List<String> combinedUnits // currently only used for NMD constructions. (Aug 2021)
    String nmdAlternativeUnit
    Double nmdUnitConversionFactor
    String technicalSpec
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
    String country
    Boolean locked
    Boolean publicConstruction // DEPRECATED
    List<Dataset> datasets
    Date edited
    List<String> dataProperties
    String mirrorResourceId
    String lastEditor
    String classificationParamId
    String classificationQuestionId
    String privateConstructionAccountId
    String privateConstructionCompanyName
    String constructionType
    String environmentDataSource
    String environmentDataSourceType
    String constructionGroup
    String representativeArea
    String constructionId
    Double serviceLife
    Boolean inheritConstructionServiceLife // Will require servicelife and will be inherited to constituents "inheritToChildren"
    String transportResourceId
    Double thickness_mm
    Double thickness_mm2
    Double defaultThickness_mm
    Double defaultThickness_mm2
    Double difference
    Double difference2
    Double thickness_in
    Double transportDistance_km
    Boolean unbundable = Boolean.TRUE
    Map <String, Object> additionalQuestionAnswers
    Integer scalingType
    Integer productScalingFactor
    Boolean preventExpand
    String productDescription
    String resourceSubType
    String importFile
    Double costConstruction_EUR
    String brandName
    String brandImageId
    Boolean hideConstituentImpacts // if TRUE, the carbon cloud impacts will be hidden for the constituents
    //NMD
    String rechtev_grootheid_dim1
    String rechtev_eenheid_dim1
    String rechtev_grootheid_dim2
    String rechtev_eenheid_dim2
    //EARLY PHASE
    String earlyPhaseToolGroup
    String groupId
    Boolean defaultConstituent
    Boolean allowedPrivateConstruction // Transient
    Boolean dynamicDefaultConstruction // Transient

    Map<String, QueryLastUpdateInfo> queryLastUpdateInfos

    String regionReferenceId // transient used in cd

    //FOR NMD
    String nmdElementId
    String nmdCategoryId
    String nmdElementCode
    String nmdElementnaam
    String nmdProductType
    String nmdProductApplication
    String nmdDatabaseVersion
    Date nmdUpdateTime
    Integer nmdProductId
    List<Integer> nmdKindElementIds

    //SW-1524
    Boolean nmdReuseAvailable
    Double nmdReuseFactor

    //0014841
    String imgLink
    String copyrightText

    static constraints = {
        resourceIds nullable: true
        name nullable: true
        unit nullable: true
        imperialUnit nullable: true
        technicalSpec nullable: true
        country nullable: true
        locked nullable: true
        publicConstruction nullable: true
        edited nullable: true
        dataProperties nullable: true
        mirrorResourceId nullable: true
        datasets nullable: true
        lastEditor nullable: true
        classificationParamId nullable: true
        classificationQuestionId nullable: true
        privateConstructionAccountId nullable: true
        privateConstructionCompanyName nullable: true
        constructionType nullable: true
        environmentDataSource nullable: true
        environmentDataSourceType nullable: true
        constructionGroup nullable: true
        representativeArea nullable: true
        constructionId nullable: true
        serviceLife nullable:true
        transportResourceId nullable:true
        transportDistance_km nullable:true
        thickness_in nullable: true
        thickness_mm nullable: true
        nameFI nullable: true
        nameEN nullable: false, blank:false
        nameNO nullable: true
        nameDE nullable: true
        nameFR nullable: true
        nameNL nullable: true
        nameES nullable: true
        nameSE nullable: true
        nameIT nullable: true
        nameHU nullable: true
        nameJP nullable: true
        unbundable nullable: true
        additionalQuestionAnswers nullable: true
        scalingType nullable: true
        productScalingFactor nullable: true
        thickness_mm2 nullable: true
        defaultThickness_mm nullable: true
        defaultThickness_mm2 nullable: true
        difference nullable: true
        difference2 nullable: true
        preventExpand nullable: true
        groupId nullable: true
        defaultConstituent nullable: true
        earlyPhaseToolGroup nullable: true
        productDescription nullable: true
        queryLastUpdateInfos nullable: true
        importFile nullable: true
        inheritConstructionServiceLife nullable: true
        regionReferenceId nullable: true
        allowedPrivateConstruction nullable: true
        dynamicDefaultConstruction nullable: true
        resourceSubType nullable: true
        costConstruction_EUR nullable: true
        nmdElementId nullable : true
        nmdCategoryId nullable: true
        imgLink nullable: true
        copyrightText nullable: true
        brandName nullable: true
        brandImageId nullable: true
        hideConstituentImpacts nullable: true
        rechtev_grootheid_dim1 nullable: true
        rechtev_eenheid_dim1 nullable: true
        rechtev_grootheid_dim2 nullable: true
        rechtev_eenheid_dim2 nullable: true
        nmdElementnaam nullable: true
        nmdElementCode nullable: true
        combinedUnits nullable: true
        nmdUnitConversionFactor nullable: true
        nmdAlternativeUnit nullable: true
        nmdProductType nullable: true
        nmdProductApplication nullable: true
        nmdDatabaseVersion nullable: true
        nmdUpdateTime nullable: true
        nmdProductId nullable: true
        nmdKindElementIds nullable: true
        nmdReuseAvailable nullable: true
        nmdReuseFactor nullable: true
    }

    static hasMany = [datasets: Dataset]
    static embedded = ["datasets"]

    def transients = [
            "queryLastUpdateInfos",
            "regionReferenceId",
            "allowedPrivateConstruction",
            "dynamicDefaultConstruction"
    ]

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def beforeInsert () {
        UserService userService = Holders.getApplicationContext().getBean("userService")
        User user = userService.getCurrentUser(true)
        if (user) {
            lastEditor = user.id.toString()
        }
        edited = new Date()
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    def beforeUpdate() {
        UserService userService = Holders.getApplicationContext().getBean("userService")
        User user = userService.getCurrentUser(true)
        if (user) {
            lastEditor = user.id.toString()
        }
        edited = new Date()
    }

    @Override
    int compareTo(Object o) {
        return 0
    }
}
