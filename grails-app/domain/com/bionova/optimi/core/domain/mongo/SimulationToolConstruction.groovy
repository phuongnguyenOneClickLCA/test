package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.ToString

@ToString
class SimulationToolConstruction implements Serializable, Validateable {
    static mapWith = "mongo"

    //ObjectId of inner Construction
    String oid

    String constructionId
    String name
    String unit
    Double amount
    Double co2e
    Double co2eIntensity
    Double share

    //LCC
    Double userGivenCost
    Double calculatedCost

    //For the question mark
    String mirrorResourceId
    String queryId
    String sectionId
    String questionId

    String profileId

    List<String> materialTypeList
    List<String> defaultResources

    List<SimulationToolConstituent> constituents

    Boolean isIncompatible
    Boolean dynamicDefault
    String dynamicDefaultGroupId

    String comment
    String originalConstructionId


    static embedded = ['constituents']

    static constrains = {
        name nullable: true
        materialTypeList nullable: true
        defaultResources nullable: true
        isIncompatible nullable: true
        originalConstructionId nullable: true
        dynamicDefault nullable: true
        dynamicDefaultGroupId nullable: true
    }

    static transients = ['isCreatedFromPrivateConstruction']

    Double getCo2eIntensity() {
        Double co2 = getCo2e()
        if (amount && co2){
            return co2/amount * 1000
        } else{
            return 0
        }
    }

    Boolean getIsCreatedFromPrivateConstruction() {
        return originalConstructionId ? true : false
    }

    Double getCo2e(){
        return constituents.findResults{it?.defaultConstituent ? it?.co2e : null}?.sum() ?: 0
    }

}
