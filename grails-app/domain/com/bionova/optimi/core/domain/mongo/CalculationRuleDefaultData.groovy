package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class CalculationRuleDefaultData implements Serializable {
    static mapWith = "mongo"
    String data
    Double defaultValue
    String defaultData
    Double factor

    static constraints = {
        data nullable: true
        defaultValue nullable: true
        defaultData nullable: true
        factor nullable: true
    }


    public String toString() {
        return "data: ${this.data}, defaultValue: ${this.defaultValue}, defaultData: ${this.defaultData}, factor: ${this.factor}"
    }
}
