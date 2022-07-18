package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.ToString

//It Represents a kind of Energy type used in a building - like heating, electicity or cooling
@ToString
class SimulationToolEnergyType implements Serializable, Validateable {

    static mapWith = "mongo"

    static final PRIMARY_HEAT = "primaryHeat"
    static final SECONDARY_HEAT = "secondaryHeat"
    static final COOLING = "cooling"
    static final ELECTRICITY = "electricity"

    String manualId

    //It represents the id of the type of energy like heatingId, electricityId or coolingId
    String typeId

    Double heatedArea
    Double demand_m2
    Double efficiencyFactor

    Double co2e

    //LCC
    Double userGivenCost
    Double calculatedCost

    //It contains the resourceId of the default options
    String defaultOption
    String defaultOptionName
    String countryId

    String optionCountry

    //PASSIVEHUS
    String buildingTypeId
    Double DUT
    Double averageTemperature

    static transients = ["demand","purchase","totalCost"]


    def getDemand(){

        return (this.heatedArea?:0) *  (this.demand_m2?:0)
    }

    def getPurchase(){

        if(this.efficiencyFactor){
            return (this.heatedArea?:0) *  (this.demand_m2?:0) / (this.efficiencyFactor?:1)
        }else{
            return 0
        }
    }

    def getTotalCost() {
        Double totalCost = 0
        Double purchase = getPurchase()

        if (purchase && userGivenCost) {
            totalCost = userGivenCost * purchase
        }
        return totalCost
    }


}
