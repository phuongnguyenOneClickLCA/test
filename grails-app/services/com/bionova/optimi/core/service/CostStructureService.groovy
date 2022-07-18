package com.bionova.optimi.core.service


import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import org.apache.commons.lang.StringUtils
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.util.CellReference
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.web.multipart.MultipartFile

/**
 * @author Pasi-Markus Mäkelä
 */
class CostStructureService {
    LoggerUtil loggerUtil
    def optimiExcelImportService
    def domainClassService
    FlashService flashService
    def datasetService
    def valueReferenceService

    def importCostStructures(MultipartFile excelFile) {
        def returnable = [:]

        try {
            def fileName = excelFile.originalFilename
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            def numberOfSheets = workbook?.getNumberOfSheets()
            def sheets = []
            def amountOfOkCostStructures = 0
            def amountOfRejectedCostStructure = 0
            def errorMessage = ""
            def warnMessage = ""
            Map propertyConfigMap = [:]
            def resourceTypeColumnsList = []
            List<CostStructure> okCostStructureList = []
            List<CostStructure> errorCostStructureList = []

            List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(CostStructure.class)
            List<PersistentProperty> resourceTypePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(CostStructure.class)

            resourceTypePersistentProperties?.each { PersistentProperty property ->
                if (property.type.equals(String.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.StringType])
                } else if (property.type.equals(Double.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.DoubleType])
                } else if (property.type.equals(Integer.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.IntType])
                } else if (property.type.equals(Boolean.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.BooleanType])
                } else {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.StringType])
                }

                if (!"id".equals(property.name)) {
                    resourceTypeColumnsList.add(property.name)
                }
            }

            if (numberOfSheets) {
                for (int i = 0; i < numberOfSheets; i++) {
                    Sheet sheet = workbook.getSheetAt(i)

                    if (!sheet.getSheetName()?.contains('@')) {
                        sheets.add(sheet)
                        def headerRowNo = sheet.getFirstRowNum()
                        Row headerRow = sheet.getRow(headerRowNo)
                        Map cellIndexesMap = optimiExcelImportService.createColumnMapFromColumnNames(resourceTypeColumnsList, headerRow)
                        Map columnMap = [:]

                        if (cellIndexesMap.keySet()?.toList()?.containsAll(mandatoryProperties)) {
                            cellIndexesMap?.keySet()?.each { attributeName ->
                                def cellIndex = cellIndexesMap.get(attributeName)
                                columnMap.put(CellReference.convertNumToColString(cellIndex as int), attributeName)
                            }
                            def configMap = [sheet: (sheet.getSheetName()), startRow: 1, columnMap: (columnMap)]
                            List resourceTypeParamsList = optimiExcelImportService.columns(workbook, configMap, null, propertyConfigMap)
                            String invalidFieldChars = "\"”'"

                            resourceTypeParamsList?.each { Map resourceTypeParamMap ->
                                String name = resourceTypeParamMap?.get("name")?.toString()?.trim()
                                String resourceSubType = resourceTypeParamMap?.get("resourceSubType")?.toString()?.trim()

                                if (!resourceTypeParamMap.subMap(mandatoryProperties)?.collect({
                                    it.value
                                })?.contains("") && !resourceTypeParamMap.subMap(mandatoryProperties)?.collect({
                                    it.value
                                })?.contains(null)) {

                                    if (name) {
                                        String invalidChar = ""

                                        resourceTypeParamMap?.each { String attributeName, attributeValue ->
                                            if (attributeName == 'listObject' && attributeValue) {
                                                if (attributeValue.contains(",")) {
                                                    def listed = []
                                                    attributeValue.tokenize(",")?.each {
                                                        listed.add(it.trim())
                                                    }
                                                    resourceTypeParamMap.put(attributeName, listed)
                                                } else {
                                                    resourceTypeParamMap.put(attributeName, [attributeValue])
                                                }
                                            }

                                            if (attributeValue instanceof String && StringUtils.containsAny(attributeValue, invalidFieldChars)) {
                                                invalidChar = invalidChar + "Found invalid character from CostStructure ${name} in field ${attributeName}: ${attributeValue}<br />"
                                            }
                                        }

                                        if (!invalidChar) {
                                            CostStructure costStructureObject = CostStructure.findByName(name)

                                            if (costStructureObject) {
                                                resourceTypePersistentProperties?.each { PersistentProperty domainClassProperty ->
                                                    DomainObjectUtil.callSetterByAttributeName(domainClassProperty.name, costStructureObject, resourceTypeParamMap.get(domainClassProperty.name), Boolean.TRUE)
                                                }
                                            } else {
                                                costStructureObject = new CostStructure(resourceTypeParamMap)
                                            }
                                            costStructureObject.importFile = fileName

                                            if (costStructureObject.validate()) {
                                                okCostStructureList.add(costStructureObject)
                                            } else {
                                                errorCostStructureList.add(costStructureObject)
                                                amountOfRejectedCostStructure++
                                            }
                                        } else {
                                            amountOfRejectedCostStructure++
                                            errorMessage = errorMessage + invalidChar
                                        }
                                    }
                                } else {
                                    amountOfRejectedCostStructure++
                                    Map missingValues = resourceTypeParamMap.subMap(mandatoryProperties)?.findAll({
                                        !it.value
                                    })
                                    errorMessage = errorMessage + "ResourceType in sheet ${sheet?.sheetName} / row ${resourceTypeParamMap.get("rowNo")} is missing required values ${missingValues?.keySet()?.toList()}<br />"
                                }
                            }
                        } else {
                            List<String> missingMandatoryFields = new ArrayList<String>(mandatoryProperties)
                            missingMandatoryFields.removeAll(cellIndexesMap.keySet()?.toList())
                            errorMessage = errorMessage + " Rejected sheet ${sheet.getSheetName()} totally, missing required fields: ${missingMandatoryFields}<br />"
                        }
                    }
                }
            }

            if (!okCostStructureList.isEmpty()) {
                CostStructure.withNewTransaction {
                    okCostStructureList.each { CostStructure costStructure ->
                        if (costStructure.id) {
                            costStructure.merge(flush: true)
                        } else {
                            loggerUtil.info(log, "Adding new costStructure ${costStructure.name} for the imported row.")
                            costStructure.save(flush: true)
                        }
                        List duplicates = CostStructure.findAllByName(costStructure.name)

                        if (duplicates && duplicates.size() > 1) {
                            List existing = returnable.get("duplicates")

                            if (existing && existing instanceof List) {
                                List temp = new ArrayList(existing)
                                temp.addAll(new ArrayList(duplicates))
                                returnable.put("duplicates", temp)
                            } else {
                                returnable.put("duplicates", new ArrayList(duplicates))
                            }
                        }
                    }
                }
                amountOfOkCostStructures = okCostStructureList.size()
            }

            if (!errorCostStructureList.isEmpty()) {
                amountOfRejectedCostStructure = amountOfRejectedCostStructure + errorCostStructureList.size()

                errorCostStructureList.each { ResourceType resourceType ->
                    errorMessage = errorMessage + resourceType.errors.getAllErrors()
                }
            }
            log.info("Uploaded costStructures with ${amountOfOkCostStructures} ok and ${amountOfRejectedCostStructure} not ok")
            flashService.setFadeInfoAlert("Uploaded costStructures with ${amountOfOkCostStructures} ok and ${amountOfRejectedCostStructure} not ok", true)

            returnable.put("file", fileName)
            returnable.put("sheets", sheets)
            returnable.put("okCostStructures", okCostStructureList)
            returnable.put("errorCostStructures", errorCostStructureList)
            returnable.put("errorMessage", errorMessage)
            returnable.put("warnMessage", warnMessage)
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in importing resourceTypes", e)
            flashService.setErrorAlert("Error in importing resourceTypes: ${e.getMessage()}", true)
            returnable.put("errorMessage", e)
        }
        return returnable
    }

    def getCostStructures() {
        return CostStructure.list()?.sort({ it?.name })
    }

    def deleteCostStructure(id) {
        Boolean deleteOk = Boolean.FALSE

        if (id) {
            CostStructure costStructure = CostStructure.get(id)

            if (costStructure) {
                costStructure.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    def getCostStructureByResourceSubType(String subType) {
        return CostStructure.findByResourceSubType(subType)
    }

    def getCost(Resource r, Entity entity, Indicator indicator, String unitToShow, String costCalculationMethod, Double userGivenThickness = null) {
        Double cost

        if (r) {
            CostStructure costStructure = getCostStructureByResourceSubType(r.resourceSubType)

            if (indicator && entity) {
                Double currencyMultiplier = valueReferenceService.getDoubleValueForEntity(indicator.currencyExchangeValueReference, entity)
                Double finalMultiplier = valueReferenceService.getDoubleValueForEntity(indicator.countryIndexValueReference, entity)
                Double labourCostCraftsman = valueReferenceService.getDoubleValueForEntity(indicator.labourCostCraftsmanValueReference, entity) ?: 0
                Double labourCostWorker = valueReferenceService.getDoubleValueForEntity(indicator.labourCostWorkerValueReference, entity) ?: 0
                cost = datasetService.resolveCostFromParams(costStructure, unitToShow, r.unitForData, null,
                        r.allowVariableThickness, currencyMultiplier, finalMultiplier, null, labourCostWorker, labourCostCraftsman,
                        userGivenThickness, r.defaultThickness_mm, r.defaultThickness_in, r.density, r.massConversionFactor, r.resourceTypeCostMultiplier,
                        costCalculationMethod, r.costConstruction_EUR, r.cost_EUR)
            }
        }
        return cost
    }

    def getTotalCost(Resource r, Entity entity, Indicator indicator, String unitToShow, String quantity, String costCalculationMethod) {
        Double cost = getCost(r, entity, indicator, unitToShow, costCalculationMethod)
        Double totalCost

        if (cost && quantity && DomainObjectUtil.isNumericValue(quantity)) {
            Double doubleQuantity = DomainObjectUtil.convertStringToDouble(quantity)

            if (cost) {
                totalCost = (cost * doubleQuantity).round(0)
            }
        }

        return totalCost
    }
}
