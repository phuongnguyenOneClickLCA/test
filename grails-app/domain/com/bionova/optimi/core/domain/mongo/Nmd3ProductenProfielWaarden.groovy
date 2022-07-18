package com.bionova.optimi.core.domain.mongo

class Nmd3ProductenProfielWaarden {
    static mapWith = "mongo"


    Integer ProductID
    Integer FunctioneleEenheidID
    Integer ProductLevensduur

    def u_waarde
    def zta
    def rc_waarde
    def lambda
    def bim_code

    String ProductNaam
    String ProductCode

    Boolean IsElementDekkend
    Boolean IsSchaalbaar

    List<Nmd3ProfielSet> ProfielSet

    static embedded = ['ProfielSet']


}
