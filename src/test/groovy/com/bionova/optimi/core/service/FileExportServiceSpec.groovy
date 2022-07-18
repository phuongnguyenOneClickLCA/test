package com.bionova.optimi.core.service

import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import spock.lang.Specification

/**
 * Test class for  FileExportService.groovy
 **/
class FileExportServiceSpec extends Specification implements ServiceUnitTest<FileExportService>, AutowiredTest {
    def setup() {
    }

    def cleanup() {
    }

    /**
     * SW-1157
     * unit test for autoSizeColumnWidthOfIndicatorReportSheet method of FileExportService
     * Method modifies the the column width of the input sheet based on its content
     * Method logic modifies the column width based on the 3rd row of indicator report sheet
     * 3rd row contains the headings for calculation rule data.
     **/
    void "autoSizeColumnWidthOfIndicatorReportSheet"() {
        given:
        List<String> infoRowHeadings = ["Entity users", "Project name", "Design name", "Indicator name"]
        List<String> infoRowValues = ["value1", "value2", "value3", "value4"]
        HSSFWorkbook wb = new HSSFWorkbook()
        CellStyle cs = wb.createCellStyle()
        Sheet sheet = wb.createSheet()
        Sheet sheet1 = wb.createSheet()

        //input for sheet
        Row infoRowHeading = sheet.createRow(0)
        Cell infoCell
        for (int i = 0; i < 4; i++) {
            infoCell = infoRowHeading.createCell(i)
            infoCell.setCellStyle(cs)
            infoCell.setCellType(CellType.STRING)
            infoCell.setCellValue(infoRowHeadings[i])
        }

        Row infoRow1 = sheet.createRow(1)
        Cell infoCell1
        for (int i = 0; i < 4; i++) {
            infoCell1 = infoRow1.createCell(i)
            infoCell1.setCellStyle(cs)
            infoCell1.setCellType(CellType.STRING)
            infoCell1.setCellValue(infoRowValues[i])
        }

        Row infoRow2 = sheet.createRow(2)
        Cell infoCell2
        for (int i = 0; i < 4; i++) {
            infoCell2 = infoRow2.createCell(i)
            infoCell2.setCellStyle(cs)
            infoCell2.setCellType(CellType.STRING)
            infoCell2.setCellValue(infoRowValues[i])
        }

        //input for sheet1
        Row infoRowHeading1 = sheet1.createRow(0)
        Cell infoCell3
        for (int i = 0; i < 4; i++) {
            infoCell3 = infoRowHeading1.createCell(i)
            infoCell3.setCellStyle(cs)
            infoCell3.setCellType(CellType.STRING)
            infoCell3.setCellValue(infoRowHeadings[i])
        }


        expect:
        //current width of  3rd column : sheet
        int currentWidth = sheet.getColumnWidth(3)
        //test case1: passing valid sheet with 3 rows of data
        service.autoSizeColumnWidthOfIndicatorReportSheet(sheet)
        // sheet contains 3 rows of data
        // based on 3rd row which contains headings for calculation rules data, column width should be modified based on the content
        assert sheet.getColumnWidth(3) != currentWidth

        //current width of  3rd column : sheet1
        int currentWidth1 = sheet1.getColumnWidth(3)
        //test case2: passing sheet1 with 1 row of data
        service.autoSizeColumnWidthOfIndicatorReportSheet(sheet1)
        // sheet contains 1 row of data
        // no changes expected as code modifies sheet column width only if 3rd row(which contains headings for calculation rules data) is present.
        assert sheet1.getColumnWidth(3) == currentWidth1

        //test case3: passing null
        when:
        service.autoSizeColumnWidthOfIndicatorReportSheet(null)

        then:
        noExceptionThrown()

    }
}
