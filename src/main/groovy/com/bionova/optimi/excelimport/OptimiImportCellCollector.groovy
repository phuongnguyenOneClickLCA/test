package com.bionova.optimi.excelimport

import org.apache.poi.ss.usermodel.Cell

/**
 * @author Pasi-Markus Mäkelä
 */
public interface OptimiImportCellCollector {

    void reportCell(Cell cell, propertyConfiguration)

    void checkReportRow(row, config, excelImportService)

    boolean checkReportValue(Object value, Cell cell, Object propertyConfiguration)

    void report(message)

    void reportPrepend(message)

}