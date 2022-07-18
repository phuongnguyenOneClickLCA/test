// TODO No migration needed
/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable
import groovy.transform.CompileStatic

import java.text.DecimalFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class MapEntity extends Entity implements Comparable, Serializable, Validateable {
    static mapWith = "mongo"
    Integer orderNo
    List longitudes
    List latitudes
    List altitudes
    Double footprintSurfaceM2
    Double buildingHeightM
    Double buildingEnvelopeWallsM2
    Double buildingEnvelopeTotalM2
    Double centroidLatitude
    Double centroidLongitude
    Double transportationPointDistance
    Map<String, Double> results
    String areaName
    String areaId
    Boolean isArea
    String objectClass
    String objectType

    static transients = [
            "displayFootprintSurfaceM2",
            "mapEntitiesByArea"
    ]

    static constraints = {
        longitudes nullable: true
        latitudes nullable: true
        altitudes nullable: true
        centroidLongitude nullable: true
        centroidLatitude nullable: true
        footprintSurfaceM2 nullable: true
        buildingHeightM nullable: true
        buildingEnvelopeWallsM2 nullable: true
        buildingEnvelopeTotalM2 nullable: true
        results nullable: true
        transportationPointDistance nullable: true
        areaName nullable: true, blank: true
        mapEntityIds nullable: true
        areaId nullable: true
        isArea nullable: true
        objectClass nullable: true, blank: true
        objectType nullable: true, blank: true
    }

    def getDisplayFootprintSurfaceM2() {
        if (footprintSurfaceM2) {
            DecimalFormat df = userService.getDefaultDecimalFormat()
            df.applyPattern("###,###.##")
            return df.format(footprintSurfaceM2)
        }
        return ""
    }
    @Override
    @CompileStatic
    public int compareTo(Object obj) {
        if (obj && (obj instanceof MapEntity) && orderNo && obj.orderNo) {
            return orderNo.compareTo(obj.orderNo)
        } else {
            return 1
        }
    }

    def getMapEntitiesInArea() {
        List<MapEntity> mapEntities = new ArrayList<MapEntity>()

        if (isArea) {
            mapEntities = MapEntity.findAllByAreaId(this.id.toString())
        }
        return mapEntities
    }
}
