package com.bionova.optimi.ui

import groovy.transform.CompileStatic

/**
 *
 */
@CompileStatic
class DetailReportRow {
    String firstLevelTotalKey
    String secondLevelTotalKey
    Map<String, String> columnWithValue

    public DetailReportRow() {
        columnWithValue = new LinkedHashMap<String, String>()
    }
}
