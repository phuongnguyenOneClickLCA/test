/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class EntityTypeConstructionAllocation {

    static mapWith = "mongo"

    //Unique identifier regionReferenceId && constructionId
    String regionReferenceId
    String constructionId

    List<String> materialTypeList
    List<String> incompatibleBuildingTypes

    Double defaultShare

    Double oneDwellingBuildings
    Double apartmentBuildings
    Double industrialBuildings
    Double offices
    Double retailBuildings
    Double hotels
    Double schoolPrimary
    Double sportsHalls
    Double generalEducationBuildings
    Double socialWelfareBuildings
    Double prisons
    Double culturalBuildings
    Double hospitals
    Double rowHouses
    Double dayCareCentres
    //RE2020 - FPI Project
    Double otherBuildingsFEC
    Double singleFamilyHouseFEC
    Double apartmentBuildingFEC
    Double dayCareCenterFEC
    Double primarySchoolFEC
    Double secondarySchoolDayFEC
    Double secondarySchoolNightFEC
    Double researchBuildingsFEC
    Double campusBuildingFEC
    Double hotel0NightFEC
    Double hotel2NightFEC
    Double hotel3NightFEC
    Double hotel4NightFEC
    Double hotel0DayFEC
    Double hotel3DayFEC
    Double officeBuildingFEC
    Double commercialCateringFEC
    Double restaurant1FEC
    Double restaurant2FEC
    Double restaurant3FEC
    Double commercialBuildingsFEC
    Double schoolSportsHallFEC
    Double retirementHomeFEC
    Double healthcareCenterNightFEC
    Double healthcareCenterDayFEC
    Double terminalBuildingsFEC
    Double industrialBuildings3x8FEC
    Double industrialBuildings8h18hFEC
    Double courtFEC
    Double sportsHallFEC
    Double schoolCatering1FEC
    Double schoolCatering3FEC

    String fileName

    List<String> defaultResources

    static constraints = {
        defaultShare nullable: true
        oneDwellingBuildings nullable: true
        apartmentBuildings nullable: true
        industrialBuildings nullable: true
        offices nullable: true
        retailBuildings nullable: true
        hotels nullable: true
        schoolPrimary nullable: true
        sportsHalls nullable: true
        generalEducationBuildings nullable: true
        socialWelfareBuildings nullable: true
        prisons nullable: true
        culturalBuildings nullable: true
        hospitals nullable: true
        fileName nullable: true
        materialTypeList nullable: true
        incompatibleBuildingTypes nullable: true
        defaultResources nullable: true
        rowHouses nullable: true
        dayCareCentres nullable: true
        //RE2020 - FPI Project
        otherBuildingsFEC nullable: true
        singleFamilyHouseFEC nullable: true
        apartmentBuildingFEC nullable: true
        dayCareCenterFEC nullable: true
        primarySchoolFEC nullable: true
        secondarySchoolDayFEC nullable: true
        secondarySchoolNightFEC nullable: true
        researchBuildingsFEC nullable: true
        campusBuildingFEC nullable: true
        hotel0NightFEC nullable: true
        hotel2NightFEC nullable: true
        hotel3NightFEC nullable: true
        hotel4NightFEC nullable: true
        hotel0DayFEC nullable: true
        hotel3DayFEC nullable: true
        officeBuildingFEC nullable: true
        commercialCateringFEC nullable: true
        restaurant1FEC nullable: true
        restaurant2FEC nullable: true
        restaurant3FEC nullable: true
        commercialBuildingsFEC nullable: true
        schoolSportsHallFEC nullable: true
        retirementHomeFEC nullable: true
        healthcareCenterNightFEC nullable: true
        healthcareCenterDayFEC nullable: true
        terminalBuildingsFEC nullable: true
        industrialBuildings3x8FEC nullable: true
        industrialBuildings8h18hFEC nullable: true
        courtFEC nullable: true
        sportsHallFEC nullable: true
        schoolCatering1FEC nullable: true
        schoolCatering3FEC nullable: true
    }

    Double getShare(String buildingType){

        Double share = (Double) DomainObjectUtil.callGetterByAttributeName(buildingType, this)

        share = (share != null) ? share : defaultShare

        return share

    }

}
