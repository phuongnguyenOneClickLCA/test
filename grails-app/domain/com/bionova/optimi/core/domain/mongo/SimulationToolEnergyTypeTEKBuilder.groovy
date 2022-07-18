package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.ToString

//It Represents a kind of Energy type used in a building - like heating, electicity or cooling
//
@ToString
class SimulationToolEnergyTypeTEKBuilder extends SimulationToolEnergyTypeBuilder implements Serializable, Validateable {



    protected Double calculateDemand_m2(Resource res) {
        //Random name for this value -- Recycled an old field in the excel
        return res?.wasteHazardous_kg ?:0
    }

}
