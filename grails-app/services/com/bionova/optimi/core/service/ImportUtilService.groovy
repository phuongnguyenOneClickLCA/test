package com.bionova.optimi.core.service

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row

import java.lang.reflect.Field

class ImportUtilService {

    def importMapperService

    /**
     * Retrieves values from the header row and checks if they correspond to the field names of the imported entity
     * and if there is a difference in case of the letters, then resolves it.
     *
     * @param headingRow    header row of a spreadsheet
     * @param importedEntityClass   class of the imported entity
     * @return a map where keys are field names of the imported entity to populate and values are indexes of the corresponding cells to get data from
     * @see org.apache.poi.ss.usermodel.Row
     */
    Map<String, Integer> getColumnHeadings(Row headingRow, Class importedEntityClass) {
        Map<String, Integer> columnHeadings = [:]
        Iterator<Cell> cellIterator = headingRow.cellIterator()
        List<String> fieldNames = importedEntityClass.declaredFields.findAll { Field field -> !field.synthetic } *.name

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next()
            String heading = importMapperService.getCellValue(cell)

            if (heading) {
                columnHeadings.put(heading, cell.columnIndex)
            }
        }

        (columnHeadings.keySet() - fieldNames).each { String invalidKey ->
            int value = columnHeadings.remove(invalidKey)
            String validKey = fieldNames.find { String fieldName -> invalidKey.equalsIgnoreCase(fieldName) }

            if (validKey) {
                columnHeadings.put(validKey, value)
            }
        }

        return columnHeadings
    }
}
