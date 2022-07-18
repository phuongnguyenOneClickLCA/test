package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import grails.web.api.ServletAttributes
import org.apache.poi.ss.usermodel.*
import org.springframework.web.multipart.MultipartFile

class EolProcessService{

    def domainClassService
    def importMapperService
    def applicationService
    def denominatorUtil
    def localizationService

    def getAllEolProcesses() {
        return EolProcess.list()
    }

    def getEolProcessByEolProcessId(String eolProcessId) {
        EolProcess eolProcess = null

        if (eolProcessId) {
            eolProcess = EolProcess.findByEolProcessId(eolProcessId)
        }
        return eolProcess
    }

    def getEolProcessesByEolProcessIds(List<String> eolProcessIds) {
        List<EolProcess> eolProcesses = null

        if (eolProcessIds) {
            eolProcesses = EolProcess.findAllByEolProcessIdInList(eolProcessIds)
        }
        return eolProcesses
    }

    // TODO: only one place left where it used - UtilController.renderRowGroupingRow, should be replaced in a future by using method wti EolProcessCache
    EolProcess getEolProcessForDataset(Dataset dataset, Resource datasetResource, ResourceType subType, Resource countryResource, Boolean moduleD = Boolean.FALSE, String lcaModel = null) {
        String eolProcessId = dataset?.additionalQuestionAnswers?.get(Constants.EOL_QUESTIONID)
        EolProcess eolProcess = null

        if (eolProcessId) {
            // Dataset eolProcessId only in calculation, in query additionalQuestions renders answer.
            if (Constants.EOLProcess.DO_NOTHING.toString() != eolProcessId) {
                if (Constants.EOLProcess.REUSE_AS_MATERIAL.toString() == eolProcessId) {
                    if (moduleD) {
                        if (!subType?.impossibleToReuseAsMaterial) {
                            // Reuses itself as end-of-life for benefit (D) module
                            eolProcess = new EolProcess(dConversionType: Constants.EOLProcess.REUSE_AS_MATERIAL.toString(), dResourceId: dataset?.resourceId, dResourceMultiplier: -1)
                        }
                    }
                } else if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcessId) {
                    // Uses stage values from resultcategory eolProcessStages
                    eolProcess = new EolProcess()
                    eolProcess.epdConversionType = Constants.EOLProcess.IMPACTS_FROM_EPD.toString()
                } else {
                    eolProcess = getEolProcessByEolProcessId(eolProcessId)
                }
            }
        } else {
            if (lcaModel && "20EPD".equals(lcaModel) && datasetResource?.impacts && org.apache.commons.collections.CollectionUtils.containsAny(["C1", "C2", "C3", "C4", "C3-C4", "C1-C4"], datasetResource.impacts.keySet().toList())) {
                eolProcess = new EolProcess(eolProcessId: "${Constants.EOLProcess.IMPACTS_FROM_EPD.toString()}", epdConversionType: "${Constants.EOLProcess.IMPACTS_FROM_EPD.toString()}", eolProcessName: "Use EOL defined in EPD")
            } else {
                if (countryResource && subType) {
                    if (countryResource.countryRegulatedWasteHandling == true) {
                        eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
                    } else if (countryResource.countryRegulatedWasteHandling == false) {
                        eolProcess = getEolProcessByEolProcessId(subType.eolProcessNonRegulated)
                    } else {
                        eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
                    }
                } else if (subType) {
                    eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
                }
            }
        }
        return eolProcess
    }

    //duplicated method with EolProcessCache
    EolProcess getEolProcessForDataset(Dataset dataset, Resource datasetResource, ResourceType subType, Resource countryResource, Boolean moduleD = Boolean.FALSE, String lcaModel = null, EolProcessCache eolProcessCache) {
        String eolProcessId = dataset?.additionalQuestionAnswers?.get(Constants.EOL_QUESTIONID)
        EolProcess eolProcess = null

        if (eolProcessId) {
            // Dataset eolProcessId only in calculation, in query additionalQuestions renders answer.
            if (Constants.EOLProcess.DO_NOTHING.toString() != eolProcessId) {
                if (Constants.EOLProcess.REUSE_AS_MATERIAL.toString() == eolProcessId) {
                    if (moduleD) {
                        if (!subType?.impossibleToReuseAsMaterial) {
                            // Reuses itself as end-of-life for benefit (D) module
                            eolProcess = new EolProcess(dConversionType: Constants.EOLProcess.REUSE_AS_MATERIAL.toString(), dResourceId: dataset?.resourceId, dResourceMultiplier: -1)
                        }
                    }
                } else if (Constants.EOLProcess.IMPACTS_FROM_EPD.toString() == eolProcessId) {
                    // Uses stage values from resultcategory eolProcessStages
                    eolProcess = new EolProcess()
                    eolProcess.epdConversionType = Constants.EOLProcess.IMPACTS_FROM_EPD.toString()
                } else {
                    eolProcess = eolProcessCache?.getEolProcessByEolProcessId(eolProcessId)
                }
            }
        } else {
            if (lcaModel && "20EPD".equals(lcaModel) && datasetResource?.impacts && org.apache.commons.collections.CollectionUtils.containsAny(["C1", "C2", "C3", "C4", "C3-C4", "C1-C4"], datasetResource.impacts.keySet().toList())) {
                eolProcess = new EolProcess(eolProcessId: "${Constants.EOLProcess.IMPACTS_FROM_EPD.toString()}", epdConversionType: "${Constants.EOLProcess.IMPACTS_FROM_EPD.toString()}", eolProcessName: "Use EOL defined in EPD")
            } else {
                if (countryResource && subType) {
                    if (countryResource.countryRegulatedWasteHandling == true) {
                        eolProcess = eolProcessCache?.getEolProcessByEolProcessId(subType.eolProcessRegulated)
                    } else if (countryResource.countryRegulatedWasteHandling == false) {
                        eolProcess = eolProcessCache?.getEolProcessByEolProcessId(subType.eolProcessNonRegulated)
                    } else {
                        eolProcess = eolProcessCache?.getEolProcessByEolProcessId(subType.eolProcessRegulated)
                    }
                } else if (subType) {
                    eolProcess = eolProcessCache?.getEolProcessByEolProcessId(subType.eolProcessRegulated)
                }
            }
        }
        return eolProcess
    }

    def getEolProcessForSubType(ResourceType subType, Resource countryResource) {
        EolProcess eolProcess
        if (countryResource && subType) {
            if (countryResource.countryRegulatedWasteHandling == true) {
                eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
            } else if (countryResource.countryRegulatedWasteHandling == false) {
                eolProcess = getEolProcessByEolProcessId(subType.eolProcessNonRegulated)
            } else {
                eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
            }
        } else if (subType) {
            eolProcess = getEolProcessByEolProcessId(subType.eolProcessRegulated)
        }
        return eolProcess
    }

    //duplicated method with EolProcessCache
    def getEolProcessForSubType(ResourceType subType, Resource countryResource, EolProcessCache eolProcessCache) {
        EolProcess eolProcess
        if (countryResource && subType) {
            if (countryResource.countryRegulatedWasteHandling == true) {
                eolProcess = eolProcessCache.getEolProcessByEolProcessId(subType.eolProcessRegulated)
            } else if (countryResource.countryRegulatedWasteHandling == false) {
                eolProcess = eolProcessCache.getEolProcessByEolProcessId(subType.eolProcessNonRegulated)
            } else {
                eolProcess = eolProcessCache.getEolProcessByEolProcessId(subType.eolProcessRegulated)
            }
        } else if (subType) {
            eolProcess = eolProcessCache.getEolProcessByEolProcessId(subType.eolProcessRegulated)
        }
        return eolProcess
    }

    def deleteEolProcess(id) {
        boolean deleteOk = false

        if (id) {
            EolProcess eolProcess = EolProcess.get(id)

            if (eolProcess) {
                eolProcess.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    def importEolProcesses(MultipartFile excelFile) {
        Map returnable = [:]
        List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EolProcess.class)
        List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(EolProcess.class)
        List<String> persistingDoubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EolProcess.class, Double)
        List<String> persistingListProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EolProcess.class, List)
        List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EolProcess.class, Boolean)

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
                            Map<String, String> rowAsMap = [:]
                            row = rowIterator.next()

                            if (rowNro != 0) {
                                columnHeadings.each { String key, Integer value ->
                                    String cellValue = importMapperService.getCellValue(row.getCell(value))

                                    if (cellValue) {
                                        rowAsMap.put(key, cellValue)
                                    }
                                }
                            }

                            String eolProcessId = rowAsMap.get(Constants.EOL_QUESTIONID)

                            if (eolProcessId) {
                                EolProcess eolProcess = new EolProcess()
                                EolProcess oldEolProcess = EolProcess.findByEolProcessId(eolProcessId)

                                if (oldEolProcess) {
                                    oldEolProcess.delete(flush: true)
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
                                        value.toString().tokenize(",")?.each {
                                            values.add(it.toString().trim())
                                        }
                                        DomainObjectUtil.callSetterByAttributeName(attr, eolProcess, values)
                                    } else if (booleanProperties?.contains(attr)) {
                                        Boolean boolValue = value?.toString()?.toBoolean()

                                        if (boolValue != null) {
                                            DomainObjectUtil.callSetterByAttributeName(attr, eolProcess, boolValue)
                                        }
                                    } else if (mandatoryProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, eolProcess, value, Boolean.FALSE)
                                    } else if (persistingProperties.contains(attr)) {
                                        DomainObjectUtil.callSetterByAttributeName(attr, eolProcess, value, Boolean.TRUE)
                                    }
                                }

                                if (eolProcess.validate()) {
                                    eolProcess.fileName = fileName
                                    eolProcess.save(flush: true, failOnError: true)
                                } else {
                                    if (returnable.get("error")) {
                                        String existing = returnable.get("error")
                                        returnable.put("error", "${existing}<br/>${eolProcess.getErrors()?.getAllErrors()}")
                                    } else {
                                        returnable.put("error", "${eolProcess.getErrors()?.getAllErrors()}")
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

    String getLocalizedName(EolProcess eolProcess) {
        if(!eolProcess) {
            return null
        }
        String fallback = "${eolProcess.eolProcessName}"
        return localizationService.getLocalizedProperty(eolProcess, "eolProcessName",
                "eolProcessName", fallback)
    }
}
