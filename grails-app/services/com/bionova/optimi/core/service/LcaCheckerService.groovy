package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.LcaChecker
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import org.apache.poi.ss.usermodel.*
import org.springframework.web.multipart.MultipartFile

class LcaCheckerService {

    def domainClassService
    def importMapperService
    def applicationService
    def denominatorUtil
    def queryService

    def getAllLcaCheckers(Boolean licensed = Boolean.TRUE) {

        if (licensed) {
            return LcaChecker.list()
        } else {
            return LcaChecker.findAllByFreeCheck(true)
        }

    }

    def getLcaCheckerByCheckId(String checkId) {
        LcaChecker lcaChecker
        if (checkId) {
            lcaChecker = LcaChecker.findByCheckId(checkId)
        }
        return lcaChecker
    }

    def getTotalForRuleOrBySection(List<Dataset> datasets, Entity childEntity, String resultIndicatorId,
                                   String resultCategoryId, String calculationRuleId, List<CalculationResult> resultsByIndicator, String section,
                                   List<String> resourceTypes, ResourceCache resourceCache) {
        Double total = 0

        if (datasets && childEntity && resultIndicatorId && resultCategoryId && calculationRuleId && resultsByIndicator) {
            List<String> datasetIds = resultsByIndicator.collect({
                it.calculationResultDatasets?.keySet()?.toList()
            })?.flatten()?.unique()

            List<Dataset> foundDatasets = []

            if (datasetIds) {
                foundDatasets = datasets?.findAll({ datasetIds.contains(it.manualId) })?.toList()
            }

            if (section && foundDatasets) {
                foundDatasets = foundDatasets.findAll({ section.equals(it.sectionId) })
            }

            if (resourceTypes && foundDatasets) {
                foundDatasets = foundDatasets.findAll({ resourceTypes.contains(resourceCache.getResource(it)?.resourceType) ||
                        resourceTypes.contains(resourceCache.getResource(it)?.resourceSubType)})
            }

            foundDatasets?.each { Dataset dataset ->
                Double result = childEntity.getResultForDataset(dataset.manualId, resultIndicatorId, resultCategoryId, calculationRuleId, resultsByIndicator)

                if (result != null) {
                    total = total + result
                }
            }
        }
        return total
    }

    def getErosionPoints(String checkType, Double points, Double erosion, Double total, Double checkValue1, Double checkValue2) {
        Double erosionPoints = 0

        if (checkType && erosion && total && points) {
            if ("minimumValue".equals(checkType)) {
                Double allowedErosion = checkValue1 - (checkValue1 * (erosion / 100))

                if (total >= allowedErosion) {
                    Double pointer = total - (checkValue1 - allowedErosion)
                    Double denominator = checkValue1 - (checkValue1 - allowedErosion)
                    Double erosionPercentage = pointer / denominator
                    erosionPoints = points * erosionPercentage
                }
            } else if ("maximumValue".equals(checkType)) {
                Double allowedErosion = checkValue1 + (checkValue1 * (erosion / 100))

                if (total <= allowedErosion) {
                    Double pointer = total - (checkValue1 + allowedErosion)
                    Double denominator = checkValue1 - (checkValue1 + allowedErosion)
                    Double erosionPercentage = pointer / denominator
                    erosionPoints = points * erosionPercentage
                }
            } else if ("valueInRange".equals(checkType)) {
                Double minAllowedErosion = checkValue1 - (checkValue1 * (erosion / 100))
                Double maxAllowedErosion = checkValue2 + (checkValue2 * (erosion / 100))

                if (total <= checkValue1 && total >= minAllowedErosion) {
                    Double pointer = total - (checkValue1 + minAllowedErosion)
                    Double denominator = checkValue1 - (checkValue1 + minAllowedErosion)
                    Double erosionPercentage = pointer / denominator
                    erosionPoints = points * erosionPercentage
                } else if (total >= checkValue2 && total <= maxAllowedErosion) {
                    Double pointer = total - (checkValue2 + maxAllowedErosion)
                    Double denominator = checkValue2 - (checkValue2 + maxAllowedErosion)
                    Double erosionPercentage = pointer / denominator
                    erosionPoints = points * erosionPercentage
                }
            }
        }

        if (erosionPoints) {
            erosionPoints = Math.round(erosionPoints)
        }
        return erosionPoints
    }

    def getProjectResultParams(LcaChecker lcaChecker) {
        Map<String, String> projectResultParams = [:]

        if (lcaChecker) {
            if (lcaChecker.projectResult1) {
                List<String> resultValueRef = lcaChecker.projectResult1.tokenize(".")

                if (resultValueRef.size() > 2) {
                    projectResultParams.put("indicatorId1", resultValueRef[0])
                    projectResultParams.put("resultCategoryId1", resultValueRef[1])
                    projectResultParams.put("calculationRuleId1", resultValueRef[2])

                    if (resultValueRef.size() > 3) {
                        projectResultParams.put("section1", resultValueRef[3])
                    }
                }
            }

            if (lcaChecker.projectResult2) {
                List<String> resultValueRef = lcaChecker.projectResult2.tokenize(".")

                if (resultValueRef.size() > 2) {
                    projectResultParams.put("indicatorId2", resultValueRef[0])
                    projectResultParams.put("resultCategoryId2", resultValueRef[1])
                    projectResultParams.put("calculationRuleId2", resultValueRef[2])

                    if (resultValueRef.size() > 3) {
                        projectResultParams.put("section2", resultValueRef[3])
                    }
                }
            }
        }
        return projectResultParams
    }

    def getPercentageColor(Integer percentage) {
        String color = "#D8D4D4"

        if (percentage != null) {
            if (percentage < 14) {
                color = "#EF1C39"
            } else if (percentage < 29) {
                color = "#F78521"
            } else if (percentage < 43) {
                color = "#F7AC64"
            } else if (percentage < 57) {
                color = "#FFCC00"
            } else if (percentage < 71) {
                color = "#8DC641"
            } else if (percentage < 85) {
                color = "#19B059"
            } else if (percentage <= 100) {
                color = "#00845A"
            } else {
                color = "#D8D4D4"
            }
        }
        return color
    }

    def getPercentageLetter(Integer percentage) {
        String letter = "G"

        if (percentage != null) {
            if (percentage < 14) {
                letter = "G"
            } else if (percentage < 29) {
                letter = "F"
            } else if (percentage < 43) {
                letter = "E"
            } else if (percentage < 57) {
                letter = "D"
            } else if (percentage < 71) {
                letter = "C"
            } else if (percentage < 85) {
                letter = "B"
            } else if (percentage <= 100) {
                letter = "A"
            } else {
                letter = "G"
            }
        }
        return letter
    }

    def getOnlyNeededResultsOnce(List<LcaChecker> lcaCheckers, Entity childEntity, Boolean inCalculation = false) {
        Map<String, List<CalculationResult>> resultsByCheckIndicator = [:]

        if (lcaCheckers && childEntity) {
            List<String> indicatorIdsToCheck = []

            lcaCheckers.each { LcaChecker lcaChecker ->
                String iId = lcaChecker.projectResult1IndicatorId
                String iId2 = lcaChecker.projectResult2IndicatorId

                if (iId && !indicatorIdsToCheck.contains(iId)) {
                    indicatorIdsToCheck.add(iId)
                }

                if (iId2 && !indicatorIdsToCheck.contains(iId2)) {
                    indicatorIdsToCheck.add(iId2)
                }
            }

            indicatorIdsToCheck.unique().each { String indicatrId ->
                List<CalculationResult> resultsByIndicator

                if (inCalculation) {
                    resultsByIndicator = childEntity.calculationResults.findAll({ childEntity.id.toString().equals(it?.entityId) && indicatrId.equals(it?.indicatorId) })
                } else {
                    resultsByIndicator = childEntity.getCalculationResultObjects(indicatrId, null, null)
                }

                if (resultsByIndicator) {
                    resultsByCheckIndicator.put(indicatrId, resultsByIndicator)
                }
            }
        }
        return resultsByCheckIndicator
    }

    def deleteLcaChecker(id) {
        boolean deleteOk = false

        if (id) {
            LcaChecker lcaChecker = LcaChecker.get(id)

            if (lcaChecker) {
                lcaChecker.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    def importLcaCheckers(MultipartFile excelFile) {
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(LcaChecker.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(LcaChecker.class)
        List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(LcaChecker.class, Double)
        List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(LcaChecker.class, List)
        List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(LcaChecker.class, Boolean)

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
                        while(rowIterator.hasNext()) {
                            Map rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.each { String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                            }

                            String checkId = rowAsMap.get("checkId")

                            if (checkId) {
                                LcaChecker lcaChecker = new LcaChecker()
                                LcaChecker oldLcaChecker = LcaChecker.findByCheckId(checkId)

                                if (oldLcaChecker) {
                                    oldLcaChecker.delete(flush: true)
                                }

                                rowAsMap.each { String attr, value ->
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
                                        DomainObjectUtil.callSetterByAttributeName(attr, lcaChecker, values)
                                    } else if (booleanProperties?.contains(attr)) {
                                        Boolean boolValue

                                        if (value instanceof Integer) {
                                            if (value == 1) {
                                                boolValue = Boolean.TRUE
                                            } else if (value == 0) {
                                                boolValue = Boolean.FALSE
                                            }
                                        } else {
                                            boolValue = value.toBoolean()
                                        }

                                        if (boolValue != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, lcaChecker, boolValue)
                                        }
                                    } else if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, lcaChecker, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, lcaChecker, value, Boolean.TRUE)
                                    }
                                }

                                if (lcaChecker.validate()) {
                                    lcaChecker.fileName = fileName
                                    lcaChecker.save(flush: true, failOnError: true)
                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${lcaChecker.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${lcaChecker.getErrors()?.getAllErrors()}")
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
}
