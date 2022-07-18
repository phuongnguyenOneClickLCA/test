package com.bionova.optimi.frenchTools.helpers

import groovy.transform.CompileStatic

@CompileStatic
class RsetMapping {

    // the questionId of the rset file (determines if mapping belongs to fec or re2020 tool)
    String questionId

    /*
        batimentIndexMappings is a Map < designId : batiment index >. This is used for mapping the batiment to design
        batimentZoneIndexMappings has Maps within a Map < designId : < zoneId of design : zone index of batiment >>. This is used for mapping the zones of the batiment to zones of the design
    */
    Map<String, Integer> batimentIndexMappings
    Map<String, Map<String, Integer>> batimentZoneIndexMappings
    String designIdForParcelle
}
