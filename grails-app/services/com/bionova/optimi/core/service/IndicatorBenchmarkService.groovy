package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.IndicatorBenchmark
import com.bionova.optimi.core.util.DomainObjectUtil
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

class IndicatorBenchmarkService {

    def domainClassService
    def importMapperService

    List<IndicatorBenchmark> getAllIndicatorBenchmarks() {
        return IndicatorBenchmark.list()
    }

    IndicatorBenchmark getIndicatorBenchmarkByBenchmarkId(String benchmarkId) {
        IndicatorBenchmark indicatorBenchmark

        if (benchmarkId) {
            indicatorBenchmark = IndicatorBenchmark.findByBenchmarkId(benchmarkId)
        }
        return indicatorBenchmark
    }

    List<IndicatorBenchmark> getIndicatorBenchmarkEmbodiedAndParentIds(String buildingType) {
        List<IndicatorBenchmark> indicatorBenchmarks
        if (buildingType) {
            indicatorBenchmarks = IndicatorBenchmark.collection.find([$or:[[buildingType: buildingType],[buildingType: [$exists: false]]]])?.collect({ it as IndicatorBenchmark })
        } else {
            indicatorBenchmarks = IndicatorBenchmark.collection.find([buildingType: [$exists: false]])?.collect({ it as IndicatorBenchmark })
        }
        return indicatorBenchmarks
    }

    Boolean getIndicatorBenchmarkable(String buildingType) {
        Boolean benchmarkable = Boolean.FALSE

        if (buildingType) {
            Integer benchmarks = IndicatorBenchmark.collection.count([$or:[[buildingType: buildingType],[buildingType: [$exists: false]]]])?.intValue()

            if (benchmarks > 0) {
                benchmarkable = Boolean.TRUE
            }
        } else {
            Integer benchmarks = IndicatorBenchmark.collection.count([buildingType: [$exists: false]])?.intValue()

            if (benchmarks > 0) {
                benchmarkable = Boolean.TRUE
            }
        }
        return benchmarkable
    }

    boolean deleteIndicatorBenchmark(id) {
        boolean deleteOk = false

        if (id) {
            IndicatorBenchmark indicatorBenchmark = IndicatorBenchmark.get(id)

            if (indicatorBenchmark) {
                indicatorBenchmark.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    Map importIndicatorBenchmarks(MultipartFile excelFile) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd")
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(IndicatorBenchmark.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(IndicatorBenchmark.class)
        List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(IndicatorBenchmark.class, Double)
        List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(IndicatorBenchmark.class, List)

        int countOfBenchmarks = 0
        String fileName = ""
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            fileName = excelFile.originalFilename
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
                        int numberOfHeadingCells = headingRow.getPhysicalNumberOfCells()
                        if (numberOfHeadingCells) {
                            for (int y = 0; y < numberOfHeadingCells; y++) {
                                String heading = headingRow.getCell(y)?.toString()
                                if (heading) {
                                    columnHeadings.put(heading, y?.toInteger())
                                }
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
                                    def cellValue = importMapperService.getCellValue(row.getCell(value))
                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                            }

                            String benchmarkId = rowAsMap.get("benchmarkId")

                            if (benchmarkId) {
                                IndicatorBenchmark indicatorBenchmark = new IndicatorBenchmark()
                                IndicatorBenchmark oldBenchmark = getIndicatorBenchmarkByBenchmarkId(benchmarkId)

                                if (oldBenchmark) {
                                    oldBenchmark.delete(flush: true)
                                }

                                rowAsMap.each { String attr, value ->

                                    if (DomainObjectUtil.isNumericValue(value)) {
                                        if (persistingDoubleProperties.contains(attr)) {
                                            value = DomainObjectUtil.convertStringToDouble(value)
                                        } else {
                                            value = DomainObjectUtil.convertStringToDouble(value)
                                        }
                                    }

                                    if (persistingListProperties.contains(attr) && value != null) {
                                        List<String> values = []
                                        value.tokenize(",")?.each {
                                            values.add(it.toString().trim())
                                        }
                                        DomainObjectUtil.callSetterByAttributeName(attr, indicatorBenchmark, values)
                                    } else if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, indicatorBenchmark, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        if(attr == "benchmarkDate"){
                                            if(value) indicatorBenchmark.benchmarkDate = format.parse(value.toString())
                                        }else {
                                            DomainObjectUtil.callSetterByAttributeName(attr, indicatorBenchmark, value, Boolean.TRUE)
                                        }
                                    }
                                }

                                if (indicatorBenchmark.validate()) {
                                    indicatorBenchmark.fileName = fileName
                                    countOfBenchmarks++

                                    if (indicatorBenchmark.id) {
                                        indicatorBenchmark.merge(flush: true, failOnError: true)
                                    } else {
                                        indicatorBenchmark.save(flush: true, failOnError: true)
                                    }

                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${indicatorBenchmark.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${indicatorBenchmark.getErrors()?.getAllErrors()}")
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
        if (countOfBenchmarks) {
            returnable.put("success", "imported: ${countOfBenchmarks} benchmarks from file ${fileName}")
        }
        return returnable
    }

    String resultBenchmarkIndex (IndicatorBenchmark indicatorBenchmark, double score){
        String scoreIndex = ""
        if(indicatorBenchmark && score) {
            if((score < indicatorBenchmark.indexA && score >= indicatorBenchmark.indexMin) || score < indicatorBenchmark.indexMin){
                scoreIndex = "A"
            } else if(score < indicatorBenchmark.indexB && score >= indicatorBenchmark.indexA){
                scoreIndex = "B"
            } else if (score < indicatorBenchmark.indexC && score >= indicatorBenchmark.indexB) {
                scoreIndex = "C"
            } else if (score < indicatorBenchmark.indexD && score >= indicatorBenchmark.indexC) {
                scoreIndex = "D"
            } else if (score < indicatorBenchmark.indexE && score >= indicatorBenchmark.indexD) {
                scoreIndex = "E"
            } else if (score < indicatorBenchmark.indexF && score >= indicatorBenchmark.indexE) {
                scoreIndex = "F"
            } else {
                scoreIndex = "G"
            }
        }
        return scoreIndex
    }

    IndicatorBenchmark getBenchmarkForProjectCountryAndType(List<IndicatorBenchmark> benchmarks, String projectCountry, String projectBuildingType) {
        IndicatorBenchmark indicatorBenchmark = null

        if (benchmarks) {
            if (projectCountry && projectBuildingType) {
                indicatorBenchmark = benchmarks.find({ it.areas?.contains(projectCountry) && it.buildingTypes?.contains(projectBuildingType) })
            }

            if (!indicatorBenchmark && projectCountry) {
                indicatorBenchmark = benchmarks.find({ it.areas?.contains(projectCountry) })
            }

            if (!indicatorBenchmark && projectBuildingType) {
                indicatorBenchmark = benchmarks.find({ it.buildingTypes?.contains(projectBuildingType) && (!it.areas || it.areas?.isEmpty()) })

                if (!indicatorBenchmark) {
                    indicatorBenchmark = benchmarks.find({ it.buildingTypes?.contains(projectBuildingType) })
                }
            }

            if (!indicatorBenchmark) {
                indicatorBenchmark = benchmarks.first()
            }
        }
        return indicatorBenchmark
    }

    String getSimpleDate(Date benchmarkDate) {

        if(benchmarkDate){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(benchmarkDate)
        } else {
            return ""
        }

    }
}
