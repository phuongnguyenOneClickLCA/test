package com.bionova.optimi.core.domain.mongo

class Nmd3ElementOnderdelen {
    static mapWith = "mongo"

    Integer OuderID
    Integer ElementID
    Integer CUAS_ID
    Integer FunctioneleEenheidID

    String ElementCode
    String ElementNaam
    String FunctioneleBeschrijving

    Boolean IsOnderdeel
    Boolean Verplicht
    Boolean IsUitzondering

    List<Nmd3Attributen> Attributen

    static embedded = ['Attributen']


}
