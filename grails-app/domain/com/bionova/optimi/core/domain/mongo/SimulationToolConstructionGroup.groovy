package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable

class SimulationToolConstructionGroup implements Serializable, Validateable {
    static mapWith = "mongo"
    String oid
    String name

    //At the moment the construction shares are not ok
    Double amount
    String unit
    Double co2e

    Double initialAmount

    List<SimulationToolConstruction> constructions

    static embedded = ['constructions']

    transient simulationToolService

    Double getCo2e(){
        if(constructions && constructions.size()>0){
            co2e = constructions.collect{it.co2e}.sum()?:0
        }
        return co2e = (co2e) ? co2e : 0
    }

    Double getAmout(){
        if(constructions && constructions.size() > 0){
            amount = constructions.collect{it.amount ?: 0}.sum() ?: 0
        }
        return amount = (amount) ? amount : 0
    }

    Double getTotalCost() {
        Double cost = 0
        if(constructions && constructions.size() > 0){
            constructions.each { SimulationToolConstruction simulationToolConstruction ->
                if (simulationToolConstruction.calculatedCost) {
                    cost = cost + simulationToolConstruction.calculatedCost
                }
            }
        }
        return cost
    }

}
