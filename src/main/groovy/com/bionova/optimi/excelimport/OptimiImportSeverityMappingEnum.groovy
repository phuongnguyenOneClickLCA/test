package com.bionova.optimi.excelimport

import org.apache.poi.ss.usermodel.CellType

/**
 * @author Pasi-Markus Mäkelä
 */

enum ImportSeverityLevelEnum {
    Ignore,
    Warning, //just provide warnings during smaller issues
    Error //prevent upload for severe errors
}

enum OptimiImportSeverityMappingEnum {

    //add another mappings as needed
    IgnoreBlankWarningOtherwise({actualType, expectedType ->
        actualType == CellType.BLANK? ImportSeverityLevelEnum.Ignore: ImportSeverityLevelEnum.Warning
    }),
    IgnoreBlankErrorOtherwise({actualType, expectedType ->
        actualType == CellType.BLANK? ImportSeverityLevelEnum.Ignore: ImportSeverityLevelEnum.Error
    }),
    //Crucial
    ErrorAll({actualType, expectedType ->
        ImportSeverityLevelEnum.Error
    }),
    //Required - All fields indicated required (green on excel, starred on edit prop page)
    WarningAll({actualType, expectedType ->
        ImportSeverityLevelEnum.Warning
    }),
    IgnoreAll({actualType, expectedType ->
        ImportSeverityLevelEnum.Ignore
    });


    private final def mappingClo;

    OptimiImportSeverityMappingEnum(mappingClo) {
        this.mappingClo = mappingClo
    }

    /**
     *
     * @param actualType value from Cell.CELL_TYPE_*
     * @param expectedType value from ExpectedPropertyType enum
     * @return value from ImportSeverityLevelEnum
     */
    def severityLevel(actualType, expectedType) {
        mappingClo(actualType, expectedType) ?: ImportSeverityLevelEnum.Warning
    }
}
