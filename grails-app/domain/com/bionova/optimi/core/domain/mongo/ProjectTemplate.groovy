package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class ProjectTemplate {

    ObjectId id
    String name
    String projectName
    String designName
    String compatibleEntityClass
    String childEntityId // child entityId
    String buildingTypeResourceId
    String countryResourceId
    String productType
    Boolean applyLcaDefaults
    Boolean deleted = false
    Boolean openCarbonDesigner
    Boolean expandAllInputs
    List<String> useIndicatorIds
    String openIndicator
    String openQuery
    Boolean isPublic = false
    Boolean isMandatory = false // if template is mandatory to a license, user cannot change template
    Boolean allowChange = false // if allowChange is false, user cannot edit the information set in template while creating a project
    List<String> publicLinkedLicenseIds
    List<String> publicLinkedLicenseTemplateIds
    Map<String, List<String>> linkedLicenseIdsByAccount

    static mapWith = "mongo"

    static constraints = {
        name nullable: true
        projectName nullable: true
        designName nullable: true
        compatibleEntityClass nullable: true
        childEntityId nullable: true
        applyLcaDefaults nullable: true
        buildingTypeResourceId nullable: true
        productType nullable: true
        countryResourceId nullable: true
        deleted nullable: true
        openCarbonDesigner nullable: true
        expandAllInputs nullable: true
        useIndicatorIds nullable: true
        openIndicator nullable: true
        openQuery nullable: true
        isPublic nullable: true
        isMandatory nullable: true
        allowChange nullable: true
        publicLinkedLicenseIds nullable: true
        publicLinkedLicenseTemplateIds nullable: true
        linkedLicenseIdsByAccount nullable: true
    }

    static transients = ['buildingTypeName']
}
