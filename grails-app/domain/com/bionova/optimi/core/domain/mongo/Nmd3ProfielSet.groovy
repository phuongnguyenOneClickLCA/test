package com.bionova.optimi.core.domain.mongo

class Nmd3ProfielSet {
    static mapWith = "mongo"

    String ProfielSetNaam

    Integer ProfielSetID
    Integer Levensduur
    Integer ScenarioNummer
    Integer ProfielSetEenheidID
    Integer SchalingsFormuleID
    Integer SchalingsMaatX1
    Integer SchalingsMaatX2
    Integer EenheidID_SchalingsMaat
    Integer SchalingA
    Integer SchalingB
    Integer SchalingC
    Integer MinX1
    Integer MinX2
    Integer MaxX1
    Integer MaxX2
    Integer TestWaarde1
    Integer TestWaarde2

    List<Nmd3Profiel> Profiel

    static embedded = ['Profiel']

}
