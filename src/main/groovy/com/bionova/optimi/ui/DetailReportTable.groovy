package com.bionova.optimi.ui

import groovy.transform.CompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class DetailReportTable {
    List<String> headings
    List<DetailReportRow> firstLevelTotals
    List<DetailReportRow> secondLevelTotals
    Map<String, List<DetailReportRow>> reportRows

    public DetailReportTable() {
        firstLevelTotals = new ArrayList<DetailReportRow>()
        secondLevelTotals = new ArrayList<DetailReportRow>()
        reportRows = new LinkedHashMap<String, List<DetailReportRow>>()
    }

}
