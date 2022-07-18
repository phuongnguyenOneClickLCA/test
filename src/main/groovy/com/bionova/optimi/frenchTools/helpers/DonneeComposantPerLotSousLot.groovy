package com.bionova.optimi.frenchTools.helpers

import groovy.transform.CompileStatic

/**
 * Object holds the list of donneeComposant per sous lot per lot
 */
@CompileStatic
class DonneeComposantPerLotSousLot {
    String lotId
    List<DonneeComposantPerSousLot> donneeComposantPerSousLots

}
