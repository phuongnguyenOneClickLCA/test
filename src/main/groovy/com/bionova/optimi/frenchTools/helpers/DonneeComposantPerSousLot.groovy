package com.bionova.optimi.frenchTools.helpers

import com.bionova.optimi.xml.re2020RSEnv.TDonneeComposant
import groovy.transform.CompileStatic

@CompileStatic
class DonneeComposantPerSousLot {
    String sousLotId
    List<TDonneeComposant> donneeComposants
}
