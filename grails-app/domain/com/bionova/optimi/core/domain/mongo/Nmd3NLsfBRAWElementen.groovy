package com.bionova.optimi.core.domain.mongo

class Nmd3NLsfBRAWElementen {
    static mapWith = "mongo"

    Integer ElementID
    Integer ZoekDatum
    Integer FunctioneleEenheidID

    String ElementCode
    String ElementNaam
    String FunctioneleBeschrijving
    String Toelichting

    Boolean IsNLsfB
    Boolean IsRaw
    Boolean IsHoofdstuk
    Boolean IsUitzondering

    List<Nmd3Attributen> Attributen

    static embedded = ['Attributen']


}
