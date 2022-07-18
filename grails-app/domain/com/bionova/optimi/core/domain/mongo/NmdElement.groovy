package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Trang Le
 */
@GrailsCompileStatic
class NmdElement implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    Integer elementId //column name: Element_ID
    String code //column name: Code
    String name //column name: Elementnaam
    String description //column name: FunctioneleBeschrijving
    Integer unitId //column name: FunctioneleEenheidID
    String unit //translation from NMD Unit system to our system
    Boolean isNlSfb //column name: IsNLsfB
    Boolean isRaw //column name: IsRAW
    String additionalData //column name: Toelichting
    Boolean isPart //column name: IsOnderdeel
    Integer cuasId //column name: CUAS_ID
    Boolean active //column name: IsGedeactiveerd
    Boolean isChapter //column name: IsHoofdstuk
    Boolean mandatory //column name: Verplicht
    Boolean isException //column name: IsUitzondering
    Boolean isProcess //column name: IsProces
    String functions//column name: Functie
    String activationDate //column name: DatumActief
    String deactivationDate //column name: DatumInActief
    String elementType // 'b&u: ' or 'gww: ' splitted from column Code
    List<Integer> parentElementIds // normally it should have just 1 parent, in some cases it has multiple
    String importSource
    Date importDate
    Date updatedDate
    Date nmdUpdateDate // date updated from API

    static final Set<String> persistingIntegerListProperties = new HashSet<String>(['parentElementIds'])

    static constraints = {
        elementId nullable: true
        code nullable: true
        name nullable: true
        description nullable: true
        unitId nullable: true
        unit nullable: true
        isNlSfb nullable: true
        isRaw nullable: true
        additionalData nullable: true
        isPart nullable: true
        cuasId nullable: true
        active nullable: true
        isChapter nullable: true
        mandatory nullable: true
        isException nullable: true
        isProcess nullable: true
        functions nullable: true
        parentElementIds nullable: true
        activationDate nullable: true
        deactivationDate nullable: true
        importSource nullable: true
        importDate nullable: true
        nmdUpdateDate nullable: true
        updatedDate nullable: true
        elementType nullable: true
    }

    def beforeInsert() {
        importDate = new Date()
    }

    def beforeUpdate() {
        updatedDate = new Date()
    }
}