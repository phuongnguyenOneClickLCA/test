package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CarbonDesigner3DBuildingType implements Serializable {

    static mapWith = "mongo"

    String buildingTypeId
    String imageAndModel
    List<BuildingTypeDefaultData> defaultData
    String fileName
    String buildingDescription

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


    static constraints = {
        defaultData nullable: true
        nameFI nullable: true
        nameEN nullable: false, blank: false
        nameNO nullable: true
        nameDE nullable: true
        nameFR nullable: true
        nameNL nullable: true
        nameES nullable: true
        nameSE nullable: true
        nameIT nullable: true
        nameHU nullable: true
        nameJP nullable: true
        fileName nullable: true
        buildingDescription nullable: true
    }

    static transients = []
}
