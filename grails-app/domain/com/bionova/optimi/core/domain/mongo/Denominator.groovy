package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class Denominator implements Serializable, Validateable {
    static mapWith = "mongo"
    String denominatorId
    @Translatable
    Map<String, String> name
    String denominatorType // "overallDenominator", "displayDenominator", "dynamicDenominator"
    List<ValueReference> denominatorValues // list of valueReference objects
    List<ResultManipulator> resultManipulator
    Map resultFormatting // Can contain: Boolean showDecimals, Integer useScientificNumbers, Integer significantDigits, Integer minimumZeros, String rounding, Map unit
    Boolean hideDenominatorValue
    String label // mainly used at portfolio renderind
    Boolean requireMonthly
    Boolean standardizeToM2 //for imperial conversion
    Boolean failIfNotCalculable //for imperial conversion
    Boolean applyFirstMatchOnly //for imperial conversion

    static hasMany = [resultManipulator: ResultManipulator, denominatorValues: ValueReference]

    static embedded = ['resultFormatting', 'resultManipulator', 'denominatorValues']

    static constraints = {
        denominatorId nullable: false, blank: false
        denominatorType nullable: true, inList: ['overallDenominator', 'displayDenominator', 'dynamicDenominator']
        denominatorValues nullable: false
        resultManipulator nullable: true
        resultFormatting nullable: true
        hideDenominatorValue nullable: true
        label nullable: true
        requireMonthly nullable: true
        standardizeToM2 nullable: true
        failIfNotCalculable nullable: true
        applyFirstMatchOnly nullable: true
    }

    static transients = [
            'label'
    ]
}
