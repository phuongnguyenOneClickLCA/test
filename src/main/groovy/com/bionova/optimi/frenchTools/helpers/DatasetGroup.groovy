package com.bionova.optimi.frenchTools.helpers

import com.bionova.optimi.core.domain.mongo.Dataset
import groovy.transform.CompileStatic

@CompileStatic
class DatasetGroup {
    String designId
    //  <zoneId, datasets>
    Map<String, Set<Dataset>> datasetsPerZone
    // <queryId, datasets same query>
    Map<String, Set<Dataset>> datasetsPerQuery
    // <energy use ref, energy datasets same use>
    Map<Integer, Set<Dataset>> energyDatasetsPerUse
    // <water type ref, water datasets same type>
    Map<Integer, Set<Dataset>> waterDatasetsPerType
    // <category ref, site datasets same type>
    Map<Integer, Set<Dataset>> siteDatasetsPerCategory
    // <be ref, datasets for be same ref>
    Map<Integer, Set<Dataset>> beDatasetsPerRef

    Set<String> energyWaterSiteBeDatasetIds

    DatasetGroup(String designId) {
        this.designId = designId
        this.datasetsPerZone = new HashMap<>()
        this.datasetsPerQuery = new HashMap<>()
        this.energyDatasetsPerUse = new HashMap<>()
        this.waterDatasetsPerType = new HashMap<>()
        this.siteDatasetsPerCategory = new HashMap<>()
        this.beDatasetsPerRef = new HashMap<>()
        this.energyWaterSiteBeDatasetIds = new HashSet<>()
    }
}
