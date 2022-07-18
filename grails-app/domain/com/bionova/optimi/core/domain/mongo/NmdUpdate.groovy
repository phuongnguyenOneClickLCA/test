package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

@GrailsCompileStatic
class NmdUpdate implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    Date date
    String dateString // a string of the date for NMD API (without time)
    Boolean statusOk
    Date updateDate
    String updatedObjectsAsString
    String errorObjectsAsString
    Set<Integer> toepassingIdErrors // ToepassingID that we don't recognize
    Set<Integer> faseIdErrors //FaseID that we don't recognize
    Set<Integer> milieuCategorieIdErrors //MilieuCategorieID that we don't recognize
    Set<Integer> unitErrors // units that we don't recognize
    String newTables
    Boolean hasConfigErrors // means it has some value from API that we do not recognize

    // transients
    String tempNewUpdates
    String tempToepassingIdErrors
    String tempFaseIdErrors
    String tempMilieuCategorieIdErrors
    String tempUnitErrors
    String tempResourceErrors
    String tempResourceNoImpacts
    String tempElementErrors
    String tempDeactivateElementsNotFound
    String tempConstructionErrors
    String tempDeactivateConstructionsErrors
    String tempDeactivateConstructionsNotFound

    static transients = [
            'tempNewUpdates',
            'tempToepassingIdErrors',
            'tempFaseIdErrors',
            'tempMilieuCategorieIdErrors',
            'tempUnitErrors',
            'tempResourceErrors',
            'tempResourceNoImpacts',
            'tempElementErrors',
            'tempDeactivateElementsNotFound',
            'tempConstructionErrors',
            'tempDeactivateConstructionsErrors',
            'tempDeactivateConstructionsNotFound'
    ]


    static constraints = {
        date nullable: true
        statusOk nullable: true
        updateDate nullable: true
        updatedObjectsAsString nullable: true
        dateString nullable: true
        errorObjectsAsString nullable: true
        toepassingIdErrors nullable: true
        faseIdErrors nullable: true
        milieuCategorieIdErrors nullable: true
        unitErrors nullable: true
        tempNewUpdates nullable: true
        tempToepassingIdErrors nullable: true
        tempFaseIdErrors nullable: true
        tempMilieuCategorieIdErrors nullable: true
        tempUnitErrors nullable: true
        tempElementErrors nullable: true
        tempDeactivateElementsNotFound nullable: true
        tempResourceErrors nullable: true
        tempResourceNoImpacts nullable: true
        tempConstructionErrors nullable: true
        tempDeactivateConstructionsErrors nullable: true
        tempDeactivateConstructionsNotFound nullable: true
        newTables nullable: true
        hasConfigErrors nullable: true
    }
}