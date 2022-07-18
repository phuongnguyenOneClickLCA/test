package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.ToString

//It Represents a kind of Energy type used in a building - like heating, electicity or cooling
@ToString
abstract class SimulationToolEnergyTypeBuilder implements Serializable, Validateable {

    static mapWith = "mongo"

    static final PRIMARY_HEAT = "primaryHeat"
    static final SECONDARY_HEAT = "secondaryHeat"
    static final COOLING = "cooling"
    static final ELECTRICITY = "electricity"

    SimulationToolEnergyType energyType
    Resource resBuilding


    SimulationToolEnergyType build(Resource res, Double heatedArea, String countryId, String buildingTypeId, Double dut, Double averageTemperature, Resource resBuilding = null){

        if(res && heatedArea!=null && buildingTypeId){
            energyType = new SimulationToolEnergyType();
            energyType.co2e = 0
            //reused random column name
            energyType.typeId = res?.representativeArea
            energyType.buildingTypeId = buildingTypeId
            energyType.heatedArea = heatedArea?:0
            //Decide if they could be null
            energyType.DUT = dut
            energyType.averageTemperature = averageTemperature
            
            energyType.demand_m2 = calculateDemand_m2(res)?:0
            //Random name for this value -- Recycled an old field in the excel
            energyType.efficiencyFactor = res?.cleanWaterNetUse_m3 ?:0

            //??????????????????????????????????????????????
            energyType.countryId = countryId ?: ""
            energyType.defaultOption = findDefaultOption(res)
            energyType.optionCountry = findCountryDef()
            energyType.defaultOptionName = ""
            //??????????????????????????????????????????????

            if(resBuilding) this.resBuilding = resBuilding

            return energyType
        }else{
            return null
        }
    }


    protected String findDefaultOption(Resource res){

        if(!energyType.typeId){return null}

        if(res.defaultCdEnergyResource){
            return res.defaultCdEnergyResource
        } else {
            return findCountryDef()
        }

    }

    protected String findCountryDef(){

        if(!energyType.countryId) { return ""}

        Resource countryResource = Resource.findByResourceIdAndActive([energyType.countryId], true)

        if (!countryResource) { return "" }

        String countryEnergyResourceId = countryResource.countryEnergyResourceId ?: ""

        switch (energyType.typeId) {
            case this.ELECTRICITY       : return countryEnergyResourceId
            case this.PRIMARY_HEAT      : return countryEnergyResourceId
            case this.SECONDARY_HEAT    : return countryEnergyResourceId
            case this.COOLING           : return countryEnergyResourceId
            default                     : return ""
        }
    }

/*
    protected String setDefaultOptionValue(Resource res){

        if(energyType.typeId){
            Resource countryResource = Resource.findByResourceIdAndActive([energyType.countryId], true)
            switch (energyType.typeId) {
                case this.ELECTRICITY       : energyType.optionCountry = countryResource?.electricityDefaultResourceId ?: ""
                case this.PRIMARY_HEAT      : energyType.optionCountry = countryResource?.primaryHeatingDefaultResourceId ?: ""
                case this.SECONDARY_HEAT    : energyType.optionCountry = countryResource?.secondaryHeatingDefaultResourceId ?: ""
                case this.COOLING           : energyType.optionCountry = countryResource?.coolingDefaultResourceId ?: ""
                default                     : energyType.optionCountry = ""
            }

            if(res?.defaultResource){
                energyType.defaultOption = res.defaultResource
            } else {
                energyType.defaultOption = energyType.optionCountry
            }

        } else {
            energyType.defaultOption = ""
            energyType.defaultOptionName = ""
            energyType.optionCountry = ""
        }

    }*/

    abstract protected Double calculateDemand_m2(Resource res);

}
