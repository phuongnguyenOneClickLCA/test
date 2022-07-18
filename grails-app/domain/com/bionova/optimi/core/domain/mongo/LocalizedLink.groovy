package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class LocalizedLink {
    String translationId
    String linkURL
    String nameEN
    String nameES
    String nameSE
    String nameIT
    String nameNO
    String nameFR
    String nameDE
    String nameFI
    String nameNL
    String nameHU
    String nameJP

    static mapWith = "mongo"

    static transients = []

    static constraints = {
        translationId nullable: false
        linkURL nullable: false
        nameEN nullable: false
        nameES nullable: true
        nameSE nullable: true
        nameIT nullable: true
        nameNO nullable: true
        nameFR nullable: true
        nameDE nullable: true
        nameFI nullable: true
        nameNL nullable: true
        nameHU nullable: true
        nameJP nullable: true
    }
}
