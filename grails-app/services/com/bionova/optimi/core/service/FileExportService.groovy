package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.TrialStatus
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.ui.DetailReportRow
import com.bionova.optimi.ui.DetailReportTable
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.text.DecimalFormat
import java.text.NumberFormat

class FileExportService {

    def detailReportServiceProxy
    def userService
    def indicatorService
    def trialStatusService
    FlashService flashService
    LoggerUtil loggerUtil
    DatasetService datasetService

    def getDetailedReportWorkbook(Entity entity, Indicator indicator, Boolean scientificNotation, Boolean createXlsxWorkbook , Boolean downloadSummary = Boolean.FALSE) {
        Workbook wb

        if (entity && indicator) {
            User currentUser = userService.getCurrentUser()
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(currentUser?.localeString) ?: new Locale("fi"))
            decimalFormat.applyPattern("0.##E0")
            decimalFormat.setMinimumFractionDigits(2)
            DetailReportTable reportTable
            if(downloadSummary){
                reportTable = detailReportServiceProxy.createResultSummaryTable(entity, indicator, Boolean.TRUE, Boolean.TRUE, createXlsxWorkbook)
            }else {
                reportTable = detailReportServiceProxy.createDetailReportTable(entity, indicator, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, createXlsxWorkbook)
            }


            if (reportTable && reportTable.headings) {
                if (createXlsxWorkbook) {
                    wb = new XSSFWorkbook()
                } else {
                    wb = new HSSFWorkbook()
                }
                CellStyle cs = wb.createCellStyle()
                Font f = wb.createFont()
                f.setBold(true)
                cs.setFont(f)
                Sheet sheet = wb.createSheet()

                List<String> infoRowHeadings = ["Entity users", "Project name", "Design name", "Indicator name"]
                if (indicator?.indicatorQueries?.collect({it.queryId})?.contains(Constants.EPD_DECLARED_UNIT_QUERYID)) {
                    infoRowHeadings.add('Mass per declared unit, kg')
                }
                Row infoRowHeading = sheet.createRow(0)
                int infoCellIndex = 0
                Cell infoCell

                infoRowHeadings.each { String heading ->
                    infoCell = infoRowHeading.createCell(infoCellIndex)
                    infoCell.setCellStyle(cs)
                    infoCell.setCellType(CellType.STRING)
                    infoCell.setCellValue(heading)
                    infoCellIndex++
                }

                Row infoRow = sheet.createRow(1)
                infoCellIndex = 0
                infoRowHeadings.each { String heading ->
                    infoCell = infoRow.createCell(infoCellIndex)
                    infoCell.setCellType(CellType.STRING)
                    if ("Entity users".equals(heading)) {
                        infoCell.setCellValue(entity.parentById?.users?.collect({ User user -> user.name })?.toString()?.replace("[", "")?.replace("]", ""))
                    } else if ("Project name".equals(heading)) {
                        infoCell.setCellValue(entity.parentName)
                    } else if ("Design name".equals(heading)) {
                        infoCell.setCellValue(entity.operatingPeriodAndName)
                    } else if ("Indicator name".equals(heading)) {
                        infoCell.setCellValue(indicatorService.getLocalizedName(indicator))
                    } else if ("Mass per declared unit, kg".equals(heading)) {
                        infoCell.setCellValue(datasetService.getAnswerIdOfDatasetByEntityQueryIdSectionIdQuestionId(entity, Constants.EPD_DECLARED_UNIT_QUERYID, Constants.DECLARED_UNIT_SECTIONID, Constants.MASS_PER_UNIT_QUESTIONID))
                    }
                    infoCellIndex++
                }

                Row row = sheet.createRow(2)
                Cell cell
                CellStyle style = wb.createCellStyle()
                DataFormat format = wb.createDataFormat()
                style.setDataFormat(format.getFormat("0.00E+00"))
                int cellIndex = 0

                reportTable.headings.each { String heading ->
                    cell = row.createCell(cellIndex)
                    cell.setCellStyle(cs)
                    cell.setCellType(CellType.STRING)
                    cell.setCellValue(heading)
                    cellIndex++
                }
                if(downloadSummary) {
                    generateSheetForReportSummary(sheet, cellIndex, cell, row, cs, scientificNotation, style, reportTable)
                }else{
                    generateSheetForIdicatorReport(sheet, cellIndex, cell, row, cs, scientificNotation, style, reportTable)
                }

                //sw-1157 : bug fix for negative value shown as ## in downloaded excel using scientific numbers
                if (scientificNotation) {
                    autoSizeColumnWidthOfIndicatorReportSheet(sheet)
                }
            }
        }
        return wb
    }

/**
 * sw-1157
 * bug fix for negative value shown as ## in downloaded excel using scientific numbers
 */
    void autoSizeColumnWidthOfIndicatorReportSheet(Sheet sheet) {
        if (sheet && !sheet.isEmpty()) {
            HSSFRow headerRow = sheet.getRow(Constants.INDICATOR_REPORT_HEADING_ROW_NUM)
            Integer lastColIndex = headerRow?.getLastCellNum()
            for (int colNum = 0; colNum < lastColIndex; colNum++) {
                sheet.autoSizeColumn(colNum)
            }
        }
    }

    def generateSheetForReportSummary(sheet , cellIndex , cell , row , cs , scientificNotation ,style , reportTable){
        try{
            if (reportTable.reportRows) {
                int rowIndex = 3

                reportTable.reportRows.each { String key, List<DetailReportRow> reportRows ->
                    reportRows.each { DetailReportRow reportRow ->

                        row = sheet.createRow(rowIndex)
                        cellIndex = 0

                        reportRow.columnWithValue?.values()?.each { String value ->

                            cell = row.createCell(cellIndex)
                            if (value && value.replaceAll(",", ".").isNumber()) {
                                value = value.replaceAll(",", ".")
                                cell.setCellType(CellType.NUMERIC)

                                if (scientificNotation) {
                                    cell.setCellStyle(style)
                                }
                                cell.setCellValue(value.toDouble())
                            } else {
                                cell.setCellType(CellType.STRING)
                                cell.setCellValue(value)
                            }
                            cellIndex++
                        }
                        DetailReportRow firstLevelTotalRow = reportTable.firstLevelTotals?.find({
                            key.equals(it.firstLevelTotalKey)
                        })
                        if (firstLevelTotalRow) {
                            firstLevelTotalRow.columnWithValue?.values()?.each { String value ->
                                cell = row.createCell(cellIndex)
                                cell.setCellStyle(cs)

                                if (value && value.replaceAll(",", ".").isNumber()) {
                                    value = value.replaceAll(",", ".")
                                    cell.setCellType(CellType.NUMERIC)

                                    if (scientificNotation) {
                                        cell.setCellStyle(style)
                                    }
                                    cell.setCellValue(value.toDouble())
                                } else {
                                    cell.setCellType(CellType.STRING)
                                    cell.setCellValue(value)
                                }
                                cellIndex++
                            }
                        }
                        rowIndex++
                    }

                }

            }

        }catch(e){
            loggerUtil.error(log, 'Error in generateSheetForReportSummary()', e)
            flashService.setErrorAlert("Error in generating sheet for result summary report: ${e.getMessage()}", Boolean.TRUE)
        }

    }
    def generateSheetForIdicatorReport(sheet , cellIndex , cell , row , cs , scientificNotation ,style , reportTable){
        try {
            if (reportTable.reportRows) {
                int rowIndex = 3
                DetailReportRow previousRow

                reportTable.reportRows.each { String key, List<DetailReportRow> reportRows ->
                    reportRows.each { DetailReportRow reportRow ->
                        if (previousRow?.secondLevelTotalKey && !previousRow.secondLevelTotalKey.equals(reportRow.secondLevelTotalKey)) {
                            DetailReportRow secondLevelTotalRow = reportTable.secondLevelTotals?.find({
                                previousRow.secondLevelTotalKey.equals(it.secondLevelTotalKey)
                            })

                            if (secondLevelTotalRow) {
                                row = sheet.createRow(rowIndex)
                                cellIndex = 0

                                secondLevelTotalRow.columnWithValue?.values()?.each { String value ->
                                    cell = row.createCell(cellIndex)
                                    cell.setCellStyle(cs)

                                    if (value && value.replaceAll(",", ".").isNumber()) {
                                        value = value.replaceAll(",", ".")
                                        cell.setCellType(CellType.NUMERIC)

                                        if (scientificNotation) {
                                            cell.setCellStyle(style)
                                        }
                                        cell.setCellValue(value.toDouble())
                                    } else {
                                        cell.setCellType(CellType.STRING)
                                        cell.setCellValue(value)
                                    }
                                    cellIndex++
                                }
                                rowIndex++
                            }
                        }
                        row = sheet.createRow(rowIndex)
                        cellIndex = 0

                        reportRow.columnWithValue?.values()?.each { String value ->

                            cell = row.createCell(cellIndex)
                            if (value && value.replaceAll(",", ".").isNumber()) {
                                value = value.replaceAll(",", ".")
                                cell.setCellType(CellType.NUMERIC)

                                if (scientificNotation) {
                                    cell.setCellStyle(style)
                                }
                                cell.setCellValue(value.toDouble())
                            } else {
                                cell.setCellType(CellType.STRING)
                                cell.setCellValue(value)
                            }
                            cellIndex++
                        }
                        rowIndex++
                        previousRow = reportRow
                    }

                    DetailReportRow firstLevelTotalRow = reportTable.firstLevelTotals?.find({
                        key.equals(it.firstLevelTotalKey)
                    })

                    if (firstLevelTotalRow) {
                        cellIndex = 0
                        row = sheet.createRow(rowIndex)

                        firstLevelTotalRow.columnWithValue?.values()?.each { String value ->
                            cell = row.createCell(cellIndex)
                            cell.setCellStyle(cs)

                            if (value && value.replaceAll(",", ".").isNumber()) {
                                value = value.replaceAll(",", ".")
                                cell.setCellType(CellType.NUMERIC)

                                if (scientificNotation) {
                                    cell.setCellStyle(style)
                                }
                                cell.setCellValue(value.toDouble())
                            } else {
                                cell.setCellType(CellType.STRING)
                                cell.setCellValue(value)
                            }
                            cellIndex++
                        }
                        rowIndex++
                    }
                }

                if (previousRow?.secondLevelTotalKey) {
                    DetailReportRow secondLevelTotalRow = reportTable.secondLevelTotals?.find({
                        previousRow.secondLevelTotalKey.equals(it.secondLevelTotalKey)
                    })

                    if (secondLevelTotalRow) {
                        row = sheet.createRow(rowIndex)
                        cellIndex = 0

                        secondLevelTotalRow.columnWithValue?.values()?.each { String value ->
                            cell = row.createCell(cellIndex)
                            cell.setCellStyle(cs)

                            if (value && value.replaceAll(",", ".").isNumber()) {
                                value = value.replaceAll(",", ".")
                                cell.setCellType(CellType.NUMERIC)

                                if (scientificNotation) {
                                    cell.setCellStyle(style)
                                }
                                cell.setCellValue(value.toDouble())
                            } else {
                                cell.setCellType(CellType.STRING)
                                cell.setCellValue(value)
                            }
                            cellIndex++
                        }
                    }
                }
            }
        }catch(e){
            loggerUtil.error(log, 'Error in generateSheetForIndicatorReport()', e)
            flashService.setErrorAlert("Error in generating sheet for indicator expand feature report: ${e.getMessage()}", Boolean.TRUE)
        }

    }
    def generateTrialUserWorkBook (List<User> userList){
        Workbook wb
        if(userList){
            wb = new XSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            Sheet sheet = wb.createSheet()
            Row noteRow = sheet.createRow(0)
            Cell noteCell = noteRow.createCell(1)
            noteCell.setCellValue("Time shown is the difference in minutes from the time the action take place comparing to the time where first project were created")

            List<String> infoRowHeadings = ["Username","User type","Account created","Trial count","First project", "First project name","Trial activated", "Trial license name", "Trial tool", "First design","Parameter query", "First query input","Second design","Result page introduced","Completed trial"]
            Row infoRowHeading = sheet.createRow(1)
            int infoCellIndex = 0
            Cell infoCell

            infoRowHeadings.each { String heading ->
                infoCell = infoRowHeading.createCell(infoCellIndex)
                infoCell.setCellStyle(cs)
                infoCell.setCellType(CellType.STRING)
                infoCell.setCellValue(heading)
                infoCellIndex++
            }



            int rowNum = 2
            userList.each { User user ->
                if(user.trialStatus){
                    TrialStatus trialStatus = user.trialStatus
                    Row row = sheet.createRow(rowNum)
                    Cell userName = row.createCell(0)
                    userName.setCellValue(user.username.toString())
                    Cell userType = row.createCell(1)
                    userType.setCellValue(user?.type?.toString())
                    Cell accountCreated = row.createCell(2)
                    accountCreated.setCellValue(user?.registrationTime?.toLocaleString())
                    Cell trialCount = row.createCell(3)
                    trialCount.setCellValue(user?.trialCount)
                    Cell firstProject = row.createCell(4)
                    firstProject.setCellValue(trialStatus.firstProject?.toLocaleString())
                    Cell projectName = row.createCell(5)
                    projectName.setCellValue(trialStatus.entityName.toString())
                    Cell trialActivated = row.createCell(6)
                    trialActivated.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.activeTrial, trialStatus.firstProject).toString())
                    Cell trialLicenseName = row.createCell(7)
                    trialLicenseName.setCellValue(trialStatus.licenseName.toString())
                    Cell indicator = row.createCell(8)
                    indicator.setCellValue(trialStatus.indicatorName.toString())
                    Cell firstDesign = row.createCell(9)
                    firstDesign.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.firstDesign, trialStatus.firstProject).toString())
                    Cell paramterQuery = row.createCell(10)
                    paramterQuery.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.projectParameter, trialStatus.firstProject).toString())
                    Cell firstQueryInput = row.createCell(11)
                    firstQueryInput.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.firstQueryInput, trialStatus.firstProject).toString())
                    Cell secondDesign = row.createCell(12)
                    secondDesign.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.secondDesign, trialStatus.firstProject).toString())
                    Cell resultPage = row.createCell(13)
                    resultPage.setCellValue(trialStatusService.getTimeDifferencesFromProjectCreation(trialStatus.viewResult, trialStatus.firstProject).toString())
                    Cell completedTrial = row.createCell(14)
                    completedTrial.setCellValue(trialStatus.completedTrial.toString())
                    rowNum ++
                }

            }

        }
        return wb
    }

    def getLicenseUsersDumpExcel(List<String> userIds) {
        Workbook wb

        if (userIds) {
            List<User> users = User.findAllByIdInList(DomainObjectUtil.stringsToObjectIds(userIds))
            wb = new XSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            Sheet sheet = wb.createSheet()
            Row row = sheet.createRow(0)
            List<String> infoRowHeadings = ["Name","Country","Organization","Email","License name", "Has floating license"]
            int infoCellIndex = 0
            Cell cell

            infoRowHeadings.each { String heading ->
                cell = row.createCell(infoCellIndex)
                cell.setCellStyle(cs)
                cell.setCellType(CellType.STRING)
                cell.setCellValue(heading)
                infoCellIndex++
            }
            int rowNum = 1
            users.each { User u ->
                List<License> licenses = userService.getUsableLicenses(u)
                String licensesString = licenses?.collect({it.name})?.toString()?.take(Constants.MAX_EXCEL_CELL_SIZE)
                row = sheet.createRow(rowNum)
                cell = row.createCell(0)
                cell.setCellValue(u.name)
                cell = row.createCell(1)
                cell.setCellValue(u.country)
                cell = row.createCell(2)
                cell.setCellValue(u.organizationName)
                cell = row.createCell(3)
                cell.setCellValue(u.username)
                cell = row.createCell(4)
                cell.setCellValue(licensesString)
                cell = row.createCell(5)
                cell.setCellValue((licenses?.findAll({it.isFloatingLicense})?.size() > 0).toString())
                rowNum++
            }
        }
        return wb

    }

}
