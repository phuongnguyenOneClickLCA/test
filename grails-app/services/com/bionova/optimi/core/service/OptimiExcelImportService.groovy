/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.excelimport.OptimiImportCellCollector
import com.bionova.optimi.excelimport.OptimiNoopImportCellCollector
import gnu.trove.map.hash.THashMap
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.util.CellUtil
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.time.LocalDate

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiExcelImportService {

    static scope = "prototype"
    static transactional = false
    def errorMessageUtil
    FlashService flashService

    def convertColumnMapManyRows(Sheet currentSheet, Map config, int firstRow, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, propertyConfigurationMap = null,
                                 int lastRow = -1) {
        if (currentSheet == null) return []
        boolean blankRowBreak = false
        int blankRowCount = 0
        def returnList = []
        def lastValidRow = 0

        for (int rowIndex = firstRow; (rowIndex < lastRow || ((lastRow == -1)) && !blankRowBreak); rowIndex++) {
            Map returnParams = convertColumnMapOneRow(currentSheet, config, rowIndex, pcc, evaluator, propertyConfigurationMap)

            if (!returnParams) {
                blankRowCount += 1
            } else {
                lastValidRow = rowIndex
                blankRowCount = 0
                returnList << returnParams
            }
            blankRowBreak = (blankRowCount > 100)
        }
        log.info("Last line imported for sheet " + currentSheet.getSheetName() + " is line " + lastValidRow)
        returnList
    }

    def convertColumnMapOneRow(Sheet currentSheet, Map config, int rowIndex, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, propertyConfigurationMap = null) {
        pcc = pcc ?: OptimiNoopImportCellCollector.NoopInstance
        Map returnParams = new THashMap()
        def anyNonNullValues = false
        Row row = currentSheet.getRow(rowIndex)

        if (!row) {
            return returnParams
        }

        config.columnMap.each { columnName, propertyName ->
            try {
                def value = getCellValueByColName(row, columnName, pcc, evaluator, propertyConfigurationMap?.get(propertyName))
                if (value == null || "".equals(value)) {
                    //cheking for null, because otherwise 0 value will fail here
                    returnParams.put(propertyName, propertyConfigurationMap?.get(propertyName)?.defaultValue)
                } else {
                    anyNonNullValues = true
                    returnParams.put(propertyName, value)
                }
            } catch (Exception e) {
                String error = "Exception caught at sheet $currentSheet.sheetName row ${rowIndex + 1} column $columnName while trying to set property $propertyName"
                HttpSession session = getRequest().getSession(false)
                String message = session?.getAttribute("errorMessage")

                if (message) {
                    message = "<br/>${error}"
                } else {
                    message = error
                }
                session?.setAttribute("errorMessage", message)
                log.error(error)
                flashService.setErrorAlert(message, true)
            }
        }

        if (anyNonNullValues) {
            //validate rows
            pcc.checkReportRow(row, config, this)
            returnParams.put("rowNo", rowIndex + 1)
            return returnParams
        }
        return [:]
    }

    /**
     * Looks through supplied index (columnIndex), and matches a siteID in that column, returning the row of the match
     */
    Row findRowById(Sheet sheet, String siteId, int columnIndex) {
        for (Row r : sheet) {
            if (r.getCell(columnIndex)?.stringCellValue == siteId) {
                log.info "found ID $siteId"
                return r
            }
        }
        return null
    }

    /**
     * receives a list of column names, i.e. ['Site', 'ID'], and tries to find the indexes of the columns matching those names
     * returning a map, i.e. ['Site':2, 'ID':8]
     */
    Map createColumnMapFromColumnNames(List COLUMN_NAMES, Row headerRow) {
        def columnIndexMap = [:]

        for (Cell c : headerRow) {
            if (COLUMN_NAMES.contains(c.stringCellValue.trim())) {
                if (c.cellType != CellType.STRING) {
                    log.error("Column type for cell ${c.columnIndex}, row ${c.rowIndex} is not a string")
                    flashService.setFadeWarningAlert("Column type for cell ${c.columnIndex}, row ${c.rowIndex} is not a string", true)
                    throw new IllegalStateException("Column type for cell ${c.columnIndex}, row ${c.rowIndex} is not a string")
                } else {
                    log.debug "Found index for column ${c.stringCellValue}"
                    columnIndexMap[c.stringCellValue] = c.columnIndex
                }
            }
        }
        // log.info "columnIndexMap $columnIndexMap"
        columnIndexMap
    }

    String checkForDeprecatedAndDuplicateFields(List<String> deprecatedFields, Row headerRow) {
        String error = ""
        String duplicateHeadings = ""
        List<String> headings = []

        for (Cell c : headerRow) {
            String headerRowCellValue = c.stringCellValue
            if (headerRowCellValue && !headerRowCellValue.startsWith("@")) {
                if (deprecatedFields*.toUpperCase().contains(headerRowCellValue.toUpperCase().trim())) {
                    error = error + "Excel contains a deprecated heading: ${headerRowCellValue}, on sheet: ${headerRow.sheet.sheetName}, on column: ${CellReference.convertNumToColString(c.columnIndex)} <br />"
                }

                if (!headings.contains(headerRowCellValue.toLowerCase())) {
                    headings.add(headerRowCellValue.toLowerCase())
                } else {
                    duplicateHeadings = duplicateHeadings + "Excel contains a duplicate heading: ${headerRowCellValue}, on sheet: ${headerRow.sheet.sheetName}, on column: ${CellReference.convertNumToColString(c.columnIndex)} <br />"
                }
            }
        }

        if (duplicateHeadings) {
            error = "${error}${duplicateHeadings}"
        }
        return error
    }

    //To Transpose the Values
    Map verticalValues(Workbook workbook, Map config) {
        return columns(workbook, config).inject([:]) { acc, nameValuePair ->
            acc << [(nameValuePair.name): nameValuePair.value]
        }
    }

    def columns(Workbook workbook, Map config, OptimiImportCellCollector pcc = null, propertyConfigurationMap = [:],
                int lastRow = -1) {
        convertColumnMapConfigManyRows(workbook, config, pcc, null, propertyConfigurationMap, lastRow)
    }

    /**
     * dual columns(Workbook, ...
     */
    def setColumns(List inputList, Workbook workbook, Map config, propertyConfigurationMap = [:], int lastRow = -1) {
        convertManyRowsToColumnMapConfig(inputList, workbook, config, null, propertyConfigurationMap, lastRow)
    }


    def convertColumnMapConfigManyRows(Workbook workbook, Map config, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, propertyConfigurationMap = [:],
                                       int lastRow = -1) {
        if (!evaluator) {
            evaluator = workbook.creationHelper.createFormulaEvaluator()
        }
        def sheet = workbook.getSheet(config.sheet)

        if (propertyConfigurationMap == [:]) {
            propertyConfigurationMap = config.configMap ?: [:]
        }

        if (config.containsKey('lastRow')) {
            lastRow = config.lastRow
        }
        return convertColumnMapManyRows(sheet, config, config.startRow, pcc, evaluator, propertyConfigurationMap, lastRow)
    }

    /**
     * dual convertColumnMapConfigManyRows(Workbook, ...
     */
    def convertManyRowsToColumnMapConfig(List inputList, Workbook workbook, Map config, FormulaEvaluator evaluator = null, propertyConfigurationMap = [:], int lastRow = -1) {
        if (!evaluator) evaluator = workbook.creationHelper.createFormulaEvaluator()
        def sheet = workbook.getSheet(config.sheet)
        if (propertyConfigurationMap == [:]) {
            propertyConfigurationMap = config.configMap ?: [:]
        }
        convertManyRowsToColumnMap(inputList, sheet, config.columnMap, config.startRow, evaluator, propertyConfigurationMap, lastRow)
    }

    /**
     * dual convertColumnMapManyRows(Sheet, ...
     */
    def convertManyRowsToColumnMap(List inputList, Sheet currentSheet, Map columnMap, int firstRow, FormulaEvaluator evaluator = null, propertyConfigurationMap = null, int lastRow = -1) {
        if (currentSheet == null) return
        int rowIndex = firstRow
        inputList.each { inputParams ->
            convertOneRowToColumnMap(inputParams, currentSheet, columnMap, rowIndex, evaluator, propertyConfigurationMap)
            rowIndex++
        }
    }

    /**
     * dual convertOneRowToColumnMap(Sheet, ...
     */
    void convertOneRowToColumnMap(inputParams, Sheet currentSheet, Map columnMap, int rowIndex, FormulaEvaluator evaluator = null, propertyConfigurationMap = null) {
        def row = currentSheet.getRow(rowIndex) ?: currentSheet.createRow(rowIndex)
        columnMap.each { columnName, propertyName ->
            try {
                def value = inputParams[propertyName]
                setCellValueByColName(value, row, columnName, evaluator, propertyConfigurationMap?.get(propertyName))
            } catch (Exception e) {
                log.warn "Exception caught at row $rowIndex column $columnName while trying to get property $propertyName", e
                flashService.setErrorAlert("Exception caught at row $rowIndex column $columnName while trying to get property $propertyName : ${e.getMessage()}", true)
                //continue in the loop, so can collect other properties
            }
        }
    }

    def getCellValueForSheetNameAndCell(Workbook workbook, String sheetName, String cellName) {
        def sheet = workbook.getSheet(sheetName)
        if (!sheet) return null
        FormulaEvaluator evaluator = workbook.creationHelper.createFormulaEvaluator()
        try {
            def cell = getCell(sheet, cellName)
            def value = getCellValue(cell, null, evaluator)
            log.debug "\t\tValue for $cellName is $value "
            //println "\t\tValue for $cellName is $value "
            return value
        } catch (Exception e) {
            log.error "Exception in cell $cellName thrown while getting cell values", e
            flashService.setErrorAlert("Exception in cell $cellName thrown while getting cell values : ${e.getMessage()}", true)
            //println "Exception in cell $cellName thrown while getting cell values $e"
            return null
        }
    }

    //cells and values are equivalent
    def cells(Workbook workbook, Map config, OptimiImportCellCollector pcc = null, Map propertyConfigurationMap = [:]) {
        convertFromCellMapToMapWithValues(workbook, config, pcc, null, propertyConfigurationMap)
    }

    //cells and values are equivalent
    def values(Workbook workbook, Map config, OptimiImportCellCollector pcc = null, Map propertyConfigurationMap = [:]) {
        convertFromCellMapToMapWithValues(workbook, config, pcc, null, propertyConfigurationMap)
    }

    //setCells and setValues are equivalent
    void setCells(Map inputMap, Workbook workbook, Map config, Map propertyConfigurationMap = [:]) {
        convertMapWithValuesToCellMap(inputMap, workbook, config, propertyConfigurationMap)
    }

    /** * dual to values(Workbook, ...  */
    void setValues(Map inputMap, Workbook workbook, Map config, Map propertyConfigurationMap = [:]) {
        convertMapWithValuesToCellMap(inputMap, workbook, config, propertyConfigurationMap)
    }


    def convertFromCellMapToMapWithValues(Workbook workbook, Map config, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, Map propertyConfigurationMap = [:]) {
        def sheet = workbook.getSheet(config.sheet)
        if (!sheet) throw new IllegalArgumentException("Did not find sheet named ${config.sheet}")
        if (propertyConfigurationMap == [:]) {
            propertyConfigurationMap = config.configMap ?: [:]
        }
        convertFromCellMapToMapWithValues(sheet, config.cellMap, pcc, evaluator, propertyConfigurationMap)
    }

    /**
     * dual convertFromCellMapToMapWithValues(Workbook, ...
     */
    void convertMapWithValuesToCellMap(Map inputMap, Workbook workbook, Map config, Map propertyConfigurationMap = [:]) {
        def sheet = workbook.getSheet(config.sheet)
        if (!sheet) throw new IllegalArgumentException("Did not find sheet named ${config.sheet}")
        if (propertyConfigurationMap == [:]) {
            propertyConfigurationMap = config.configMap ?: [:]
        }
        convertMapWithValuesToCellMap(inputMap, sheet, config.cellMap, propertyConfigurationMap)
    }

    def convertFromCellMapToMapWithValues(Sheet currentSheet, Map cellMap, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, Map propertyConfigurationMap = [:]) {
        Map objectParams = [:]
        evaluator = evaluator ?: currentSheet.workbook.creationHelper.createFormulaEvaluator()
        cellMap.each { String cellName, String propertyName ->
            try {
                def cell = getCell(currentSheet, cellName)
                def value = getCellValue(cell, pcc, evaluator, propertyConfigurationMap[propertyName])
                //println "\t\tValue for $propertyName is $value "
                log.debug "\t\tValue for $propertyName is $value "
                //fix by Jackson
                if (value == null) {
                    log.warn "No value in cell $cellName set.  Was trying to find $propertyName"
                    flashService.setFadeWarningAlert("No value in cell $cellName set.  Was trying to find $propertyName", true)
                } else {
                    objectParams[propertyName] = value
                }

            } catch (Exception e) {
                log.error "Exception in cell $cellName getting $propertyName thrown while getting cell values", e
                flashService.setErrorAlert("Exception in cell $cellName getting $propertyName thrown while getting cell values : ${e.getMessage()}", true)
                //println "Exception in cell $cellName getting $propertyName thrown while getting cell values $e"
            }
        }
        log.debug "Returning objectParams $objectParams"
        objectParams
    }

    /**
     * dual convertFromCellMapToMapWithValues(Sheet, ...
     */
    void convertMapWithValuesToCellMap(Map objectParams, Sheet currentSheet, Map cellMap, Map propertyConfigurationMap = [:]) {
        FormulaEvaluator evaluator = currentSheet.workbook.creationHelper.createFormulaEvaluator()
        cellMap.each { String cellName, String propertyName ->
            try {
                def cell = getCellOrCreate(currentSheet, cellName)
                def value = objectParams[propertyName]
                setCellValue(value, cell, evaluator, propertyConfigurationMap[propertyName])
            } catch (Exception e) {
                log.error "Exception in cell $cellName setting $propertyName thrown while setting cell values", e
                flashService.setErrorAlert("Exception in cell $cellName getting $propertyName thrown while getting cell values : ${e.getMessage()}", true)
                //println "Exception in cell $cellName setting $propertyName thrown while setting cell values $e"
            }
        }
    }


    Serializable getCellValueByColName(Row row, String columnName, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, Map propertyConfiguration = [:]) {
        int colIndex = CellReference.convertColStringToIndex(columnName)
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
        //println "\t\t\tCell is [$cell], colIndex $colIndex, colName $columnName, ${row.rowNum}"
        getCellValue(cell, pcc, evaluator, propertyConfiguration)
    }

    /**
     * dual getCellValueByColName(Row, ...
     */
    void setCellValueByColName(value, Row row, String columnName, FormulaEvaluator evaluator = null, Map propertyConfiguration = [:]) {
        int colIndex = CellReference.convertColStringToIndex(columnName)
        Cell cell = row.getCell(colIndex) ?: row.createCell(colIndex, CellType.BLANK)
        setCellValue(value, cell, evaluator, propertyConfiguration)
    }

    void setCellValueByColIndex(value, Row row, int colIndex, FormulaEvaluator evaluator = null, Map propertyConfiguration = [:]) {
        Cell cell = row.getCell(colIndex) ?: row.createCell(colIndex, CellType.BLANK)
        setCellValue(value, cell, evaluator, propertyConfiguration)
    }


    def getCellValueByCellName(Sheet currentSheet, String cellName, FormulaEvaluator evaluator = null) {
        if (evaluator == null) {
            evaluator = currentSheet.workbook.creationHelper.createFormulaEvaluator()
        }
        def cell = getCell(currentSheet, cellName)
        getCellValue(cell, null, evaluator)
    }

    /**
     * Returns date or string, or numeric, depending on what's in the cell
     */
    Serializable getCellValue(Cell origcell, OptimiImportCellCollector pcc = null, FormulaEvaluator evaluator = null, propertyConfiguration = [:]) {
        pcc = pcc ?: OptimiImportCellCollector.NoopInstance
        Cell cell = origcell
        // DEBUG, code below threw XmlValueDisconnectedException
        //Cell cell = evaluator ? evaluator.evaluateInCell(origcell) : origcell; //evaluates formula and returns value

        if (cell == null) {
            pcc.reportCell(origcell, propertyConfiguration)
            return propertyConfiguration?.defaultValue ?: null
        }

        CellType resolvedCellType

        if (cell.cellType == CellType.FORMULA) {
            resolvedCellType = cell.cachedFormulaResultType
        } else {
            resolvedCellType = cell.cellType
        }

        if (!propertyConfiguration?.defaultValue) {
            if (!propertyConfiguration)  {
                propertyConfiguration = [:]
            }
            propertyConfiguration.defaultValue = null
        }

        switch (resolvedCellType) {
            case CellType.STRING:

                if (!propertyConfiguration || propertyConfiguration.expectedType == ExpectedPropertyType.StringType) {
                    String strValue = cell.stringCellValue?.trim()

                    if (propertyConfiguration && strValue == propertyConfiguration?.valueEquivalentToNull) {
                        log.info("Found a value that's not null (value ${strValue}), but configuration property says to return null anyway")
                        flashService.setWarningAlert("Found a value that's not null (value ${strValue}), but configuration property says to return null anyway", true)
                        return null
                    }
                    //log.warn "Encountered unexpected string cell Value ${cell.stringCellValue} at row ${cell.getRowIndex()} column ${cell.getColumnIndex()}"
                    return strValue
                }

                if (propertyConfiguration?.expectedType == ExpectedPropertyType.DateType || propertyConfiguration?.expectedType == ExpectedPropertyType.DateJavaType) {
                    def stringDate = cell.stringCellValue?.trim()

                    try {
                        def df = new java.text.SimpleDateFormat('MM/dd/yy')
                        df.setLenient(false) //would fail on Nonexistent dates (ie. February 30th, April 31)
                        Date javaDate = df.parse(stringDate)

                        if (propertyConfiguration?.expectedType == ExpectedPropertyType.DateJavaType) {
                            if (pcc.checkReportValue(javaDate, cell, propertyConfiguration)) {
                                return propertyConfiguration?.defaultValue
                            }
                            return javaDate
                        }
                        LocalDate localDate = com.bionova.optimi.util.DateUtil.dateToLocalDate(javaDate)

                        if (pcc.checkReportValue(localDate, cell, propertyConfiguration)) {
                            return propertyConfiguration?.defaultValue
                        }
                        return localDate
                    } catch (e) {
                        pcc.reportCell(cell, propertyConfiguration)
                        return propertyConfiguration?.defaultValue
                    }
                    //return propertyConfiguration.defaultValue
                }

                if (propertyConfiguration?.expectedType == ExpectedPropertyType.IntType) {
                    log.warn "${getCellAddresString(cell)} Expected Type is INT, but cell type is String, trying to extract numeric value"
                    errorMessageUtil.setErrorMessage("${getCellAddresString(cell)} Expected Type is INT, but cell type is String, trying to extract numeric value", true, true)
                    try {
                        return cell.stringCellValue?.trim()?.toInteger()
                    } catch (Exception e) {
                        log.warn "${getCellAddresString(cell)} Cannot get numeric value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}"
                        flashService.setWarningAlert("${getCellAddresString(cell)} Cannot get numeric value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}. Has error: ${e.getMessage()}", true)
                        pcc.reportCell(cell, propertyConfiguration)
                        return propertyConfiguration.defaultValue
                    }
                }

                if (propertyConfiguration?.expectedType == ExpectedPropertyType.DoubleType) {
                    log.warn "${getCellAddresString(cell)}Expected Type is DOUBLE, but cell type is String, trying to extract numeric value"
                    errorMessageUtil.setErrorMessage("${getCellAddresString(cell)}Expected Type is DOUBLE, but cell type is String, trying to extract numeric value", true, true)
                    try {
                        return cell.stringCellValue?.trim()?.toDouble()
                    } catch (Exception e) {
                        log.warn "${getCellAddresString(cell)} Cannot get double value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}"
                        flashService.setWarningAlert("${getCellAddresString(cell)} Cannot get double value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}. Has error: ${e.getMessage()}", true)
                        pcc.reportCell(cell, propertyConfiguration)
                        return propertyConfiguration.defaultValue
                    }
                }

                if (propertyConfiguration?.expectedType == ExpectedPropertyType.BooleanType) {
                    log.warn "${getCellAddresString(cell)} Expected Type is Boolean, but cell type is String, trying to cast to Boolean value"
                    flashService.setWarningAlert("${getCellAddresString(cell)} Expected Type is Boolean, but cell type is String, trying to cast to Boolean value", true)

                    try {
                        return new Boolean(cell.stringCellValue?.trim())
                    } catch (Exception e) {
                        log.warn "${getCellAddresString(cell)} Cannot get Boolean value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}"
                        flashService.setWarningAlert("${getCellAddresString(cell)} Cannot get Boolean value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}. Has error: ${e.getMessage()}", true)
                        pcc.reportCell(cell, propertyConfiguration)
                        return propertyConfiguration.defaultValue
                    }
                }


                if (propertyConfiguration?.expectedType == ExpectedPropertyType.EmailType) {
                    String strValue = cell.stringCellValue?.trim()

                    if (propertyConfiguration && strValue == propertyConfiguration?.valueEquivalentToNull) {
                        log.info("Found a value that's not null (value ${strValue}), but configuration property says to return null anyway")
                        flashService.setWarningAlert("Found a value that's not null (value ${strValue}), but configuration property says to return null anyway", true)
                        return null
                    }
                    def emailValidator = org.apache.commons.validator.EmailValidator.newInstance()

                    if (emailValidator.isValid(strValue)) {
                        return strValue
                    }
                    log.warn "${getCellAddresString(cell)} Cannot get email value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}"
                    flashService.setWarningAlert("${getCellAddresString(cell)} Cannot get email value, returning default value specified for this type, which is ${propertyConfiguration.defaultValue}", true)
                    pcc.reportCell(cell, propertyConfiguration)
                    return propertyConfiguration.defaultValue
                }

                log.warn "${getCellAddresString(cell)} Potential issue -  ${getCellAddresString(cell)}the excel file claims the type is String, but expecting something else for cell with value $origcell. Returning default value of ${propertyConfiguration?.defaultValue}"
                flashService.setWarningAlert("${getCellAddresString(cell)} Potential issue -  ${getCellAddresString(cell)}the excel file claims the type is String, but expecting something else for cell with value $origcell. Returning default value of ${propertyConfiguration?.defaultValue}", true)
                //println "Potential issue - the excel file claims the type is String, but expecting something else for cell with value $origcell. Returning default value of ${propertyConfiguration.defaultValue}"

                pcc.reportCell(cell, propertyConfiguration)
                return propertyConfiguration?.defaultValue

            case CellType.NUMERIC:
                //println "numeric cell $origcell"
                if (propertyConfiguration?.expectedType == ExpectedPropertyType.StringType) {
                    cell.setCellType(CellType.STRING);
                    return cell.stringCellValue
                }

                if (propertyConfiguration?.expectedType == ExpectedPropertyType.BooleanType) {
                    if (1D == cell.numericCellValue) {
                        return Boolean.TRUE
                    } else {
                        return Boolean.FALSE
                    }
                }

                if (DateUtil.isCellDateFormatted(cell)) {
                    Date javaDate = cell.dateCellValue

                    if (propertyConfiguration?.expectedType == ExpectedPropertyType.DateJavaType) {
                        if (pcc.checkReportValue(javaDate, cell, propertyConfiguration)) {
                            return propertyConfiguration?.defaultValue
                        }
                        return javaDate
                    }
                    LocalDate localDate = com.bionova.optimi.util.DateUtil.dateToLocalDate(javaDate)

                    if (pcc.checkReportValue(localDate, cell, propertyConfiguration)) {
                        return propertyConfiguration?.defaultValue
                    }
                    return localDate
                } else {
                    def numeric = cell.numericCellValue

                    if (pcc.checkReportValue(numeric, cell, propertyConfiguration)) {
                        return propertyConfiguration?.defaultValue
                    }

                    if (numeric != null) {
                        if (propertyConfiguration?.expectedType == ExpectedPropertyType.IntType) {
                            return numeric.toInteger()
                        } else {
                            return numeric
                        }
                    } else {
                        return propertyConfiguration?.defaultValue
                    }
                }
                break;
            case CellType.ERROR:
                log.warn "CELL Type is ERROR value: $cell.errorCellValue ${getCellAddresString(cell)}"
                flashService.setWarningAlert("CELL Type is ERROR value: $cell.errorCellValue ${getCellAddresString(cell)}", true)
                pcc.reportCell(cell, propertyConfiguration)
                return null
            case CellType.BOOLEAN:
                return cell.booleanCellValue
            case CellType.BLANK:
                pcc.reportCell(cell, propertyConfiguration)
                return null;
            default:
                log.warn "Unexpected cell type.  ${getCellAddresString(cell)} Ignoring.  Cell Value [${cell}] type ${cell.cellType}"
                flashService.setWarningAlert("Unexpected cell type.  ${getCellAddresString(cell)} Ignoring.  Cell Value [${cell}] type ${cell.cellType}", true)
        }
        log.error "WARNING: RETURNING NULL FROM getCellValue.  UNEXPECTED CONDITION ${getCellAddresString(cell)}"
        flashService.setErrorAlert("WARNING: RETURNING NULL FROM getCellValue.  UNEXPECTED CONDITION ${getCellAddresString(cell)}", true)
        pcc.reportCell(cell, propertyConfiguration)
        return null;
    }

    def getCellAddresString(Cell cell) {
        Sheet sheet = cell.sheet
        "Sheet: ${sheet?.sheetName}, Cell row ${cell.getRowIndex()},  column ${CellUtil.getCell(sheet?.getRow(0), cell.getColumnIndex())?.stringCellValue} "
    }

    def getBoldCellStyle(Workbook wb) {
        if (wb) {
            CellStyle boldcell = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            boldcell.setFont(f)
            return boldcell
        } else {
            return null
        }
    }

    def getDateCellStyle(Workbook wb) {
        if (wb) {
            CellStyle cellStyle = wb.createCellStyle()
            CreationHelper createHelper = wb.getCreationHelper()
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.yyyy"))
            cellStyle.setAlignment(HorizontalAlignment.LEFT)
            return cellStyle
        } else {
            return null
        }
    }

    /**
     * dual getCellValue(Cell, ...
     */
    void setCellValue(value, Cell origcell, FormulaEvaluator evaluator = null, propertyConfiguration = [:]) {
        if (!propertyConfiguration || !propertyConfiguration.expectedType) {
            //... null handling
            //directly compatible with expected type of setCellValue
            if ([Double.TYPE, String.class, Boolean.TYPE].any { it.isInstance(value) }) {
                origcell.setCellValue(value)
                return
            }
            //conversion compatible with expected type of setCellValue
            if (value instanceof Number) {
                //avoid float->double widening to generate double values like 441.489990234375 instead of 441.49
                origcell.setCellValue(Double.valueOf(value.toString()))
                return
            }
            //Date
            if ([Date.class, LocalDate.class].any { it.isInstance(value) }) {
                origcell.setCellValue((value instanceof LocalDate) ? com.bionova.optimi.util.DateUtil.localDateToDate(value) : value)
                return
            }
            return
        }

        switch (propertyConfiguration.expectedType) {
            case ExpectedPropertyType.EmailType:
            case ExpectedPropertyType.StringType:
                if (value == null) {
                    //dsq-OSM - maybe prefer to write out the valueEquivalentToNull if defined
                    origcell.setCellValue('')
                    return
                }
                if (value instanceof String) {
                    origcell.setCellValue(value)
                    return
                }
                //... type convertion handling
                break;
            case ExpectedPropertyType.IntType:
            case ExpectedPropertyType.DoubleType:
                if (value == null) {
                    return
                }
                if (value instanceof Number) {
                    origcell.setCellValue(Double.valueOf(value.toString()))
                    origcell.setCellType(CellType.NUMERIC)
                    return
                }
                log.warn "value $value was supposed tobe numeric but isn't"
                flashService.setWarningAlert("Value $value was supposed tobe numeric but isn't", true)
                //... type convertion handling
                break;
            case ExpectedPropertyType.DateType:
            case ExpectedPropertyType.DateJavaType:
                if (value == null) {
                    return
                }
                if ([Date.class, LocalDate.class].any { it.isInstance(value) }) {
                    //dsq-OSM - not sure how to properly handle this date related stuff
                    def style = origcell.sheet.workbook.createCellStyle()
                    style.cloneStyleFrom(origcell.getCellStyle())
                    style.setDataFormat((short) 0x0e)
                    origcell.setCellStyle(style)
                    origcell.setCellValue((value instanceof LocalDate) ? com.bionova.optimi.util.DateUtil.localDateToDate(value) : value)
                    return
                }
                //... type convertion handling
                break;
            default:
                log.error "Unexpected property type.  Ignoring.  Property Value [${value}] type ${propertyConfiguration.expectedType}"
                flashService.setErrorAlert("Unexpected property type.  Ignoring.  Property Value [${value}] type ${propertyConfiguration.expectedType}", true)
        }
        log.error "WARNING: RETURNING FROM setCellValue.  UNEXPECTED CONDITION expectedType ${propertyConfiguration.expectedType} / ${value}"
        flashService.setErrorAlert("WARNING: RETURNING FROM setCellValue.  UNEXPECTED CONDITION expectedType ${propertyConfiguration.expectedType} / ${value}", true)
        return;
    }

    Cell getCell(Sheet currentSheet, String ref) {
        CellReference cellReference = new CellReference(ref);
        Row row = currentSheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol())
        //println "returning cell $cell"
        cell
    }

    Cell getCellOrCreate(Sheet currentSheet, String ref) {
        CellReference cellReference = new CellReference(ref);
        Row row = currentSheet.getRow(cellReference.getRow());
        row.getCell(cellReference.getCol()) ?: row.createCell(cellReference.getCol(), CellType.BLANK)
    }

    private enum ExpectedPropertyType {
        IntType([name: 'number']),
        StringType([name: 'text']),
        DateType([name: 'date']),
        DateJavaType([name: 'date']),
        DoubleType([name: 'number']),
        EmailType([name: 'email']),
        BooleanType([name: 'boolean'])

        final String userViewableName

        public ExpectedPropertyType(Map parameters = [:]) {
            this.userViewableName = parameters?.name ?: this.name()
        }
    }

    private HttpServletRequest getRequest() {
        try {
            return WebUtils.retrieveGrailsWebRequest().getCurrentRequest()
        } catch (Exception e) {
            return null
        }
    }
}
