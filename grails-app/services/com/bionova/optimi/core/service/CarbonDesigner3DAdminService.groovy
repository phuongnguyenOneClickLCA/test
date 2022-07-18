package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.BuildingTypeDefaultData
import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DBuildingType
import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DRegion
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional
import org.apache.poi.ss.usermodel.*
import org.springframework.web.multipart.MultipartFile

@Transactional
class CarbonDesigner3DAdminService {

    def domainClassService
    def importMapperService
    def queryService

    /**
     * Parses the provide excel to create and build out the CarbonDesigner3D BuildingTypes
     * @param excelFile
     * @return
     */
    def importCarbonDesigner3DBuildingTypes(MultipartFile excelFile) {
        Map returnable = [:]
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")

            if (numberOfSheets) {
                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int rowCount = sheet?.lastRowNum
                    if (rowCount && sheet && !sheet.sheetName.contains("@")) {
                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        log.info("rowcount ${rowCount}")
                        Row headingRow = sheet.getRow(0)
                        Iterator<Cell> cellIterator = headingRow.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            String heading = importMapperService.getCellValue(cell)

                            if (heading) {
                                columnHeadings.put(heading, cell.columnIndex)
                            }
                        }

                        Integer rowNro = 0
                        Row row
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
                            Map rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.forEach({ String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                })
                            }

                            String buildingTypeId = rowAsMap.get("buildingTypeId")
                            Boolean parentType = rowAsMap.get("parentType").toString()?.toBoolean()
                            log.info("buildingTypeId: " + buildingTypeId)
                            log.info("parentType: " + parentType)
                            log.info("rowAsMap: " + rowAsMap)

                            if (buildingTypeId && parentType) {
                                List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(CarbonDesigner3DBuildingType.class)
                                List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(CarbonDesigner3DBuildingType.class)
                                List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(CarbonDesigner3DBuildingType.class, Double)
                                List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(CarbonDesigner3DBuildingType.class, List)
                                List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(CarbonDesigner3DBuildingType.class, Boolean)

                                CarbonDesigner3DBuildingType buildingType = new CarbonDesigner3DBuildingType()
                                CarbonDesigner3DBuildingType oldBuildingType = CarbonDesigner3DBuildingType.findByBuildingTypeId(buildingTypeId)

                                if (oldBuildingType) {
                                    oldBuildingType.delete(flush: true)
                                }

                                rowAsMap.forEach({ String attr, value ->
                                    if (DomainObjectUtil.isNumericValue(value)) {
                                        if (persistingDoubleProperties.contains(attr)) {
                                            value = DomainObjectUtil.convertStringToDouble(value)
                                        } else {
                                            value = DomainObjectUtil.convertStringToDouble(value).toInteger()
                                        }
                                    }
                                    if (persistingListProperties.contains(attr) && value != null) {
                                        List<String> values = []
                                        value.tokenize(",")?.each {
                                            values.add(it.toString().trim())
                                        }
                                        DomainObjectUtil.callSetterByAttributeName(attr, buildingType, values)
                                    } else if (booleanProperties?.contains(attr)) {
                                        Boolean boolValue = value.toBoolean()

                                        if (boolValue != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, buildingType, boolValue)
                                        }
                                    } else if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, buildingType, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, buildingType, value, Boolean.TRUE)
                                    }
                                })

                                if (buildingType.validate()) {
                                    buildingType.fileName = fileName
                                    buildingType.save(flush: true, failOnError: true)
                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${entityType.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${buildingType.getErrors()?.getAllErrors()}")
                                    }
                                }
                            } else if (buildingTypeId && !parentType) {

                                List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(BuildingTypeDefaultData.class)
                                List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(BuildingTypeDefaultData.class)
                                List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(BuildingTypeDefaultData.class, Double)
                                List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(BuildingTypeDefaultData.class, List)
                                List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(BuildingTypeDefaultData.class, Boolean)

                                CarbonDesigner3DBuildingType parentBuildingType = CarbonDesigner3DBuildingType.findByBuildingTypeId(buildingTypeId)
                                if (parentBuildingType) {

                                    BuildingTypeDefaultData newDefaultData = new BuildingTypeDefaultData()

                                    String parameterId = rowAsMap.get("parameterId")
                                    BuildingTypeDefaultData oldDefaultData = BuildingTypeDefaultData.findByBuildingTypeIdAndParameterId(buildingTypeId, parameterId)
                                    if (oldDefaultData) {
                                        oldDefaultData.delete(flush: true)
                                    }

                                    //If row count is later than defaultValue, then create a new map and save that
                                    Boolean isDefaultValueRow = Boolean.FALSE
                                    Map<String, Double> regionDefaultValues = [:]
                                    rowAsMap.forEach({ String attr, value ->
                                        if (!isDefaultValueRow) {
                                            if (DomainObjectUtil.isNumericValue(value)) {
                                                if (persistingDoubleProperties.contains(attr)) {
                                                    value = DomainObjectUtil.convertStringToDouble(value)
                                                } else {
                                                    value = DomainObjectUtil.convertStringToDouble(value).toInteger()
                                                }
                                            }
                                            if (persistingListProperties.contains(attr) && value != null) {
                                                List<String> values = []
                                                value.tokenize(",")?.each {
                                                    values.add(it.toString().trim())
                                                }
                                                DomainObjectUtil.callSetterByAttributeName(attr, newDefaultData, values)
                                            } else if (booleanProperties?.contains(attr)) {
                                                Boolean boolValue = value.toBoolean()

                                                if (boolValue != null) {
                                                    DomainObjectUtil.callSetterByAttributeName(attr, newDefaultData, boolValue)
                                                }
                                            } else if (mandatoryProperties.contains(attr)) {
                                                DomainObjectUtil.callSetterByAttributeName(attr, newDefaultData, value, Boolean.FALSE)
                                            } else if (persistingProperties.contains(attr)) {
                                                DomainObjectUtil.callSetterByAttributeName(attr, newDefaultData, value, Boolean.TRUE)
                                            }
                                            if (attr == "defaultValue") {
                                                isDefaultValueRow = Boolean.TRUE
                                            }
                                        } else {
                                            if (DomainObjectUtil.isNumericValue(value)) {
                                                value = DomainObjectUtil.convertStringToDouble(value)
                                            }
                                            regionDefaultValues.put(attr, value)
                                        }
                                    })

                                    if (regionDefaultValues) {
                                        DomainObjectUtil.callSetterByAttributeName("regionDefaultValues", newDefaultData, regionDefaultValues, Boolean.TRUE)
                                    }

                                    if (newDefaultData.validate()) {
                                        newDefaultData.save(flush: true, failOnError: true)
                                    } else {
                                        if (returnable.get("error")) {
                                            String existing = returnable.get("error")
                                            returnable.put("error", "${existing}<br/>${newDefaultData.getErrors()?.getAllErrors()}")
                                        } else {
                                            returnable.put("error", "${newDefaultData.getErrors()?.getAllErrors()}")
                                        }
                                    }

                                    if (parentBuildingType.defaultData) {
                                        parentBuildingType.defaultData.add(newDefaultData)
                                    } else {
                                        parentBuildingType.defaultData = []
                                        parentBuildingType.defaultData.add(newDefaultData)
                                    }
                                    if (parentBuildingType.validate()) {
                                        parentBuildingType.save(flush: true, failOnError: true)
                                    } else {
                                        if (returnable.get("error")) {
                                            String existing = returnable.get("error")
                                            returnable.put("error", "${existing}<br/>${parentBuildingType.getErrors()?.getAllErrors()}")
                                        } else {
                                            returnable.put("error", "${parentBuildingType.getErrors()?.getAllErrors()}")
                                        }
                                    }
                                }
                            }
                            rowNro++
                        }
                    }
                }
            }
        } catch (Exception e) {
            returnable.put("error", "${e}")
        }
        return returnable
    }

    /**
     * Returns all CarbonDesigner3DBuildingTypes as a list
     * @return
     */
    def getAllBuildingTypes() {
        return CarbonDesigner3DBuildingType.list()
    }

    /**
     * Finds and returns a specific building type based on Id
     * @param id
     * @return
     */
    def getBuildingTypeById(String id) {
        return CarbonDesigner3DBuildingType.findByBuildingTypeId(id)
    }

    def getAllBuildingTypesByRegionId(String regionId) {
        Query carbonDesignerQuery = queryService.getQueryByQueryId(Constants.CARBON_DESIGNER_QUERY, true)
        CarbonDesigner3DRegion region = carbonDesignerQuery?.carbonDesigner3DRegions?.find({ (it.regionId == regionId) })
        List<CarbonDesigner3DBuildingType> buildingTypes
        if (region) {
            buildingTypes = CarbonDesigner3DBuildingType.list()
            List<String> regionBuildingTypes = region.regionBuildingTypes
            buildingTypes = buildingTypes.findAll {regionBuildingTypes.contains(it.buildingTypeId)}
        }
        return buildingTypes
    }
}
