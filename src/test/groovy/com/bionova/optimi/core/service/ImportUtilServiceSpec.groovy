package com.bionova.optimi.core.service

import grails.testing.services.ServiceUnitTest
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import spock.lang.Specification

class ImportUtilServiceSpec extends Specification implements ServiceUnitTest<ImportUtilService>{

    ImportMapperService importMapperService

    def setup() {
        importMapperService = Mock(ImportMapperService)
        service.importMapperService = importMapperService
    }

    def cleanup() {
    }

    void "test getting of column headings"() {
        given:
        Row headerRow = Mock()
        Cell cell1 = Mock()
        Cell cell2 = Mock()
        Cell cell3 = Mock()
        Cell cell4 = Mock()
        Iterator<Cell> cellIterator = Mock()

        when:
        Map<String, Integer> result = service.getColumnHeadings(headerRow, com.bionova.optimi.core.domain.mongo.NmdElement.class)

        then:
        1 * headerRow.cellIterator() >> cellIterator
        5 * cellIterator.hasNext() >>> [true, true, true, true, false]
        4 * cellIterator.next() >>> [cell1, cell2, cell3, cell4]
        1 * importMapperService.getCellValue(cell1) >> "elementId"
        1 * importMapperService.getCellValue(cell2) >> "CODE"
        1 * importMapperService.getCellValue(cell3) >> "Name"
        1 * importMapperService.getCellValue(cell4) >> "DescriptioN"
        1 * cell1.getColumnIndex() >> 0
        1 * cell2.getColumnIndex() >> 1
        1 * cell3.getColumnIndex() >> 2
        1 * cell4.getColumnIndex() >> 3
        0 * _

        and:
        assert result == [elementId: 0, code: 1, name: 2, description: 3], "Column headings mapping should contain 4 entries with correct field names."
    }
}
