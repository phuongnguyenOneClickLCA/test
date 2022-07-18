package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.ToString

//It Represents a kind of Energy type used in a building - like heating, electicity or cooling
@ToString
class SimulationToolEnergyTypePassivehausBuilder extends SimulationToolEnergyTypeBuilder implements Serializable, Validateable {

    static final TYPE = "passivhusId"
    static final APARTMENT_BUILDING = "apartmentBuildings"
    static final FAMILY_HOUSE = "oneDwellingBuildings"

    protected Double calculateDemand_m2(Resource res) {

        //Resource resBuilding = Resource.findByResourceIdAndActive([buildingTypeId], true)

        switch(this.energyType.typeId){

            case this.PRIMARY_HEAT : return (demandHeat() * 0.6 )
            case this.SECONDARY_HEAT : return (demandHeat()  * 0.4 )
            case this.COOLING : return demandCooling()
            //Random name for this value -- Recycled an old field in the excel
            case this.ELECTRICITY : return res.wasteHazardous_kg?:0

            default : return 0
        }

    }

    private Double demandHeat() {

        String buildingTypeId = this.energyType.buildingTypeId

        if(buildingTypeId == APARTMENT_BUILDING || buildingTypeId == FAMILY_HOUSE){
            return demandHeatSmallBuilding()
        }

        //Resource from simulationTool buildingType
        if(!this.resBuilding) this.resBuilding = Resource.findByResourceIdAndActive([buildingTypeId], true)

        //?????? LOG ERROR IF THE VALUES ARE NULL????
        Double EPH0 = resBuilding.passivhaus_EPH0?:0
        Double X = resBuilding.passivhaus_X?:0
        Double K1 = resBuilding.passivhaus_K1?:0
        Double K2 = resBuilding.passivhaus_K2?:0
        Double waterEnergy = resBuilding.passivhaus_waterEnergy?:0

        Double avgTemp = this.energyType.averageTemperature
        Double heatedArea = this.energyType.heatedArea

        if(avgTemp < 6.3) {
            if (heatedArea < 1000) {
                //(EPH0+(X*(1000-this.heatedArea)/100)+((K1+K2*(1000-heatedArea)/100))*(6.3-avgTemp)),(EPH0+K1*(6.3-avgTemp)))
                return EPH0 + (X * (1000 - heatedArea) / 100) + (K1 + K2 * (1000 - heatedArea) / 100) * (6.3 - avgTemp) + waterEnergy
            } else {
                return EPH0 + K1 * (6.3 - avgTemp) + waterEnergy
            }
        } else {
            if (heatedArea < 1000) {
                //IF(H$18<1000,H$23+(H$24*(1000-H$18)/100),H$23)
                //=IF(heatedArea<1000,EPH0+(X*(1000-heatedArea)/100),EPH0)
                return EPH0 + (X * (1000 - heatedArea) / 100) + waterEnergy
            } else {
                return EPH0 + waterEnergy
            }
        }

    }

    private Double demandHeatSmallBuilding() {

        String buildingTypeId = this.energyType.buildingTypeId

        //Resource from simulationTool buildingType
        if(!this.resBuilding) this.resBuilding = Resource.findByResourceIdAndActive([buildingTypeId], true)

        Double EPH0 = resBuilding.passivhaus_EPH0?:0
        Double X = resBuilding.passivhaus_X?:0
        Double K1 = resBuilding.passivhaus_K1?:0
        Double K2 = resBuilding.passivhaus_K2?:0
        Double waterEnergy = resBuilding.passivhaus_waterEnergy?:0

        Double avgTemp = this.energyType.averageTemperature
        Double heatedArea = this.energyType.heatedArea

        if(avgTemp < 6.3) {
            //IF(heatedArea<250)
            if (heatedArea < 250) {
                //((15+5.4*(250-heatedArea)/100)+(2.1+0.59*(250-heatedArea)/100)*(6.3-avgTemp))
                return 15 + (5.4 * (250 - heatedArea) / 100) + (2.1 + 0.59 * (250 - heatedArea) / 100) * (6.3 - avgTemp) + waterEnergy
            } else {
                //15+2.1*(6.3-avgTemp)
                return 15 + 2.1 * (6.3 - avgTemp) + waterEnergy
            }
        } else {
            //IF(heatedArea<250)
            if (heatedArea < 250) {
                // 15+5.4*(250-heatedArea)/100)
                return 15 + (5.4 * (250 - heatedArea) / 100) + waterEnergy
            } else {
                return 15 + waterEnergy
            }
        }

    }

    private Double demandCooling() {

        String buildingTypeId = this.energyType.buildingTypeId

        if(buildingTypeId == APARTMENT_BUILDING || buildingTypeId == FAMILY_HOUSE){
            return 0
        }

        //Resource from simulationTool buildingType
        if(!this.resBuilding) this.resBuilding = Resource.findByResourceIdAndActive([buildingTypeId], true)

        Double Beta = resBuilding.passivhaus_Beta?:0
        Double DUT = this.energyType.DUT

        if(DUT > 20) {
            return Beta * (DUT - 20)
        }else {
            return 0
        }

    }
}
