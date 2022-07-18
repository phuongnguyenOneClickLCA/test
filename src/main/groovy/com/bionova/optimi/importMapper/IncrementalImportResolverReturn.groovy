package com.bionova.optimi.importMapper

import com.bionova.optimi.core.domain.mongo.Dataset
import groovy.transform.CompileStatic

@CompileStatic
class IncrementalImportResolverReturn {

    List<Dataset> newDatasets
    String importError
}
