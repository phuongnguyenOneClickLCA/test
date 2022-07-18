package com.bionova.optimi.importMapper

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.ImportMapperCollapseGrouper
import grails.compiler.GrailsCompileStatic
import org.apache.poi.ss.usermodel.Sheet

@GrailsCompileStatic
class ImportedByImportMapperReturn {

    List<Sheet> sheets
    List<String> headers = []

    List<Dataset> okDatasets = []
    List<Dataset> discardedDatasets = []
    List<Dataset> rejectedDatasets = []
    List<ImportMapperCollapseGrouper> collapseGroupers = []

    Map<String, Integer> discardedValuesWithAmount = [:]
    Map<String, Integer> emptyRows = [:]
    Map<String, Integer> removedByPresetFilter = [:]
    Map<String, Integer> truncatedFields = [:]

    Integer skippedRows = 0
    String materialHeading = null
    String thicknessHeading = null

    String warningMessage = ""
    String missingMandatoryDataError = null
    String errorMessage

    ImportMapperIterationStatistic iterationStatistic = new ImportMapperIterationStatistic()


    List<Dataset> getTooGenericDatasets() {
        okDatasets.findAll({ !it.allowMapping })
    }

    List<Dataset> getRejectedAndOkDatasets() {

        List<Dataset> rejectedAndOkDatasets = []

        if (okDatasets) {
            rejectedAndOkDatasets.addAll(okDatasets)
        }
        if (rejectedDatasets) {
            rejectedAndOkDatasets.addAll(rejectedDatasets)
        }
        if (discardedDatasets) {
            rejectedAndOkDatasets.addAll(discardedDatasets)
        }

        return rejectedAndOkDatasets
    }

}
