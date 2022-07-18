package com.bionova.optimi.core.domain.mongo

class Nmd3Profiel {
    static mapWith = "mongo"

    String ProfielNaam

    Integer ProfielID
    Integer CategorieID
    Integer FaseID

    List<Nmd3ProfielMilieuEffecten> ProfielMilieuEffecten

    static embedded = ['ProfielMilieuEffecten']
}
