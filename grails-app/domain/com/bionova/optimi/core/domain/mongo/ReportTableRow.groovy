package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * Created by pmm on 28.4.2015.
 */
@GrailsCompileStatic
class ReportTableRow {
    static mapWith = "mongo"
    List<ReportTableCell> tableCells

    public ReportTableRow(def cellList) {
        if (cellList) {
            tableCells = new ArrayList<ReportTableCell>()

            cellList.each {
               tableCells.add(it as ReportTableCell)
            }
        }
    }
}
