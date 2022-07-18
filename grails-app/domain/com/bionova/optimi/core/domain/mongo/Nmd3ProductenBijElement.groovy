package com.bionova.optimi.core.domain.mongo

class Nmd3ProductenBijElement {
    static mapWith = "mongo"


    Integer ElementID
    Integer ProductID
    Integer OuderProductID
    Integer AantalProfielSets
    Integer ExtraScenarios
    Integer FunctioneleEenheidID
    Integer CategorieID
    Integer Levensduur

    String ProductNaam
    String ProductCode
    String Toelichting_Dataleverancier

    Boolean ElementIsVerplicht
    Boolean ProfielSetGekoppeld
    Boolean IsElementDekkend
    Boolean IsSchaalbaar

    Integer connectedElementID

    List<Nmd3Attributen> GekoppeldeElementAttributen

    static embedded = ['GekoppeldeElementAttributen']


}
