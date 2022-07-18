package com.bionova.optimi.core.domain.mongo

class Nmd3ProfielsetsEnSchalingBijProduct2 {
    static mapWith = "mongo"

    Integer ProductID
    Integer ProfielSetID
    Integer ScenarioID
    Integer EenheidID_SchalingsMaat
    Integer connectedProductID

    String ProfielSetNaam

    Double Hoeveelheid
    Double SchalingsMaat_X1
    Double SchalingsMaat_X2
    Double SchalingMaxX1
    Double SchalingMinX1
    Double SchalingMaxX2
    Double SchalingMinX2

    Boolean IsSchaalbaar
}
