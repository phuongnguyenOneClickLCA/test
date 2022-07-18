package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartFile

@Transactional
class HelpConfigurationService {
    def localizationService

    def getAllConfigurations() {
        return HelpConfiguration.list()
    }

    def getHelpConfigurationById(String id) {
        if (id) {
            return HelpConfiguration.findById(DomainObjectUtil.stringToObjectId(id))
        }
    }

    def getConfigurationsForTargetPage(String targetPage) {
        if (targetPage) {
            return HelpConfiguration.findAllByTargetPageAndIndicatorIdIsNull(targetPage)
        }
    }

    def getHelpConfigurationByConfigurationId(String configId) {
        if (configId) {
          return HelpConfiguration.findByHelpConfigurationId(configId)
        }
    }

    def getHelpConfigurationByTargetPageAndQueryId(String targetPage, String queryId) {
        if (queryId && targetPage) {
            return HelpConfiguration.findAllByTargetPageAndQueryId(targetPage, queryId)
        }
    }

    def getHelpConfigurationByTargetPageAndIndicatorId(String targetPage, String indicatorId, Boolean addGenericsAlso = Boolean.FALSE) {
        List<HelpConfiguration> configs = []
        if (targetPage && indicatorId) {
            configs.addAll(HelpConfiguration.findAllByTargetPageAndIndicatorId(targetPage, indicatorId))

            if (addGenericsAlso) {
                configs.addAll(HelpConfiguration.findAllByTargetPageAndIndicatorIdIsNull(targetPage))
            }

            return configs
        }
    }
    def getHelpConfigurationByTargetPageAndIndicatorIdAndQueryId(String targetPage, String indicatorId, String queryId, Boolean addGenericsAlso = Boolean.FALSE) {
        List<HelpConfiguration> configs = []

        if (targetPage && indicatorId && queryId) {
                configs.addAll(HelpConfiguration.findAllByTargetPageAndQueryIdAndIndicatorId(targetPage, queryId, indicatorId))

                if (addGenericsAlso) {
                    configs.addAll(HelpConfiguration.findAllByTargetPageAndIndicatorIdIsNullAndQueryId(targetPage, queryId))
                }
                return configs

        }
    }

    def importExcel(MultipartFile excelFile) {
        Map returnable = [:]
        List<HelpConfiguration> helpConfigurations = []
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            List<Map<String, String>> rowsAsMaps = []

            if (numberOfSheets) {
                returnable.put("filename:", fileName)
                returnable.put("numberOfSheets:", numberOfSheets)


                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.physicalNumberOfRows
                    List<Row> rowsPerSheet = []

                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {

                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        returnable.put("sheetWithData:${sheet.sheetName}", "rowCount: ${rowCount}")

                        Row headingRow = sheet.getRow(0)
                        int numberOfHeadingCells = headingRow.getPhysicalNumberOfCells()
                        if (numberOfHeadingCells) {
                            for (int y = 0; y < numberOfHeadingCells; y++) {
                                String heading = headingRow.getCell(y)?.toString()
                                if (heading) {
                                    columnHeadings.put(heading, y?.toInteger())
                                }
                            }
                        }
                        log.info("headings handled, number of headings ${numberOfHeadingCells}")

                        // handleRows

                        Integer rowNro = 0
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next()
                            if (rowNro != 0) {
                                rowsPerSheet.add(row)
                            }
                            rowNro++
                        }

                        log.info("handled rows: ${rowsPerSheet.size()}")

                        if (rowsPerSheet) {
                            rowsPerSheet.each { Row rowPerSheet ->
                                Map<String, String> rowAsMap = [:]
                                columnHeadings.each { String key, Integer value ->
                                    try {
                                        String valueOfColumnHeading
                                        Cell cell = rowPerSheet.getCell(value)
                                        if (cell) {
                                            if (cell.getCellTypeEnum() == CellType.STRING) {
                                                valueOfColumnHeading = cell.getStringCellValue()
                                            } else if(cell.getCellTypeEnum() == CellType.NUMERIC) {
                                                valueOfColumnHeading = cell.getNumericCellValue()
                                            } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                                                valueOfColumnHeading = cell.getNumericCellValue()
                                            } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                                                valueOfColumnHeading = cell.getBooleanCellValue()
                                            }
                                        }
                                        if (valueOfColumnHeading) {
                                            rowAsMap.put(key, valueOfColumnHeading)
                                        }
                                    } catch (Exception e) {
                                        returnable.put("parseError", "Parsing error at sheet ${sheet.sheetName}, at row${rowPerSheet} and cell ${rowPerSheet.getCell(value)}")
                                        log.error("Exception at construction import rowhandling ${e}")
                                    }

                                }
                                if (rowAsMap) {
                                    rowsAsMaps.add(rowAsMap)
                                }
                            }
                        }
                    }
                }
            }
            if (rowsAsMaps) {
                helpConfigurations = createConfigurationsFromMaps(rowsAsMaps)
            }
            returnable.put("created configurations", helpConfigurations?.size())
            log.info("Imported excelfile ${fileName} with configurations: ${helpConfigurations?.size()}")

        } catch (Exception e) {
            returnable.put("exception", e.getMessage())
            log.error("${e}")
        }
        return returnable
    }

    def createConfigurationsFromMaps(List<Map<String, String>> configurationsAsMaps) {
        List<HelpConfiguration> configs = []
        configurationsAsMaps.each { Map<String, String> configuration ->
            HelpConfiguration config = getHelpConfigurationByConfigurationId(configuration.get("helpConfigurationId"))
            if (!config) {
                config = new HelpConfiguration()
                config.helpConfigurationId = configuration.get("helpConfigurationId")
            }
            config.targetPage = configuration.get("targetPage")
            config.contentNameEN = configuration.get("contentNameEN")
            config.contentNameES = configuration.get("contentNameES")
            config.contentNameSE = configuration.get("contentNameSE")
            config.contentNameIT = configuration.get("contentNameIT")
            config.contentNameDE = configuration.get("contentNameDE")
            config.contentNameFI = configuration.get("contentNameFI")
            config.contentNameFR = configuration.get("contentNameFR")
            config.contentNameNO = configuration.get("contentNameNO")
            config.contentNameNL =  configuration.get("contentNameNL")
            config.additionalTextEN = configuration.get("additionalTextEN")
            config.additionalTextES = configuration.get("additionalTextES")
            config.additionalTextSE = configuration.get("additionalTextSE")
            config.additionalTextIT = configuration.get("additionalTextIT")
            config.additionalTextDE = configuration.get("additionalTextDE")
            config.additionalTextFI = configuration.get("additionalTextFI")
            config.additionalTextFR = configuration.get("additionalTextFR")
            config.additionalTextNO = configuration.get("additionalTextNO")
            config.additionalTextNL =  configuration.get("additionalTextNL")
            config.contentType = configuration.get("contentType")
            config.contentURL = configuration.get("contentURL")
            config.queryId = configuration.get("queryId")
            config.indicatorId = configuration.get("indicatorId")
            if (config.validate()) {
                config.merge(flush:true, failOnError:true)
                configs.add(config)
            }
        }
        return configs
    }

    String getLocalizedContentName(HelpConfiguration helpConfiguration) {
        if(!helpConfiguration) {
            return "Localized name missing for configuration"
        }
        String fallback = "Localized name missing for configuration ${helpConfiguration.helpConfigurationId}"
        localizationService.getLocalizedProperty(helpConfiguration, "contentName", "contentName", fallback)
    }

    String getLocalizedAdditionalText(HelpConfiguration helpConfiguration) {
        if(!helpConfiguration) {
            return "Localized additionalText missing for configuration"
        }
        String fallback = "Localized additionalText missing for configuration ${helpConfiguration.helpConfigurationId}"
        localizationService.getLocalizedProperty(helpConfiguration, "additionalText", "additionalText", fallback)
    }
}
