package com.bionova.optimi.core.domain.mongo

class CarbonDesign extends Entity {

    String carbonDesignId
    Map<String, Object> designInfo
    Map<String, Double> buildingDimensions
    Map<String, Double> resourceSubTypes

    List<CD3DBuildingElementsPayload> buildingElements
    List<CD3DElementGroupPayload> buildingElementGroups
    Double totalCo2e

    static embedded = [
            "buildingElements",
            "buildingElementGroups",
    ]

    static constraints = {
        carbonDesignId nullable: true
        designInfo nullabe: true
        buildingDimensions nullable: true
        buildingElements nullable: true
        resourceSubTypes nullable: true
        totalCo2e nullabe: true
    }
}
