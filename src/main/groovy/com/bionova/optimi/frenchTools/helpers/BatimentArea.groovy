package com.bionova.optimi.frenchTools.helpers

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

/**
 * An object holding the area of the design / batiment, and area of each zone.
 */
@CompileStatic
class BatimentArea {

    String designId
    Double sref
    Double nocc
    Boolean allZonesHaveSharedRatio
    List<ZoneArea> zoneAreas

    BatimentArea(String designId) {
        this.designId = designId
        this.zoneAreas = []
    }

    void setZoneArea(String zoneId, Double sref, Double nocc) {
        if (!zoneId || sref == null) {
            return
        }
        Integer existingZoneAreaIndex = zoneAreas.findIndexOf { it.zoneId == zoneId }
        if (existingZoneAreaIndex >= 0) {
            ZoneArea zoneArea = zoneAreas[existingZoneAreaIndex]
            zoneArea.setSref(zoneArea.sref + sref)
            zoneArea.setNocc(zoneArea.nocc + nocc)
            zoneAreas.set(existingZoneAreaIndex, zoneArea)
        } else {
            zoneAreas.add(new ZoneArea(zoneId: zoneId, sref: sref, nocc: nocc))
        }
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    void setZonePerBatimentRatio() {
        if (zoneAreas && sref) {
            for (ZoneArea zoneArea in zoneAreas) {
                if (zoneArea.sref) {
                    zoneArea.zonePerBatimentRatio = zoneArea.sref / sref
                }
            }

            allZonesHaveSharedRatio = zoneAreas.every { it.zonePerBatimentRatio != null }
        }
    }
}
