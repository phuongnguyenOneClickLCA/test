package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBObject
import grails.gorm.transactions.Transactional
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.StringUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.web.multipart.MultipartFile

import java.text.DecimalFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class ResourceTypeService {

    def loggerUtil
    def unitConversionUtil
    def optimiResourceService
    def optimiExcelImportService
    def domainClassService
    def localizationService
    FlashService flashService
    ResourceService resourceService

    private static boolean resourceTypesUpdated = false
    private static List<ResourceType> resourceTypes

    def importResourceTypes(MultipartFile excelFile) {
        def returnable = [:]
        List<String> allowedUnits = ["m2", "m3", "kg"]

        try {
            def fileName = excelFile.originalFilename
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            def numberOfSheets = workbook?.getNumberOfSheets()
            def sheets = []
            def amountOfRejectedResourceTypes = 0
            def errorMessage = ""
            def warnMessage = ""
            Map propertyConfigMap = [:]
            def resourceTypeColumnsList = []
            List<ResourceType> okResourceTypeList = []
            List<ResourceType> errorResourceTypeList = []

            List<String> listProperties = domainClassService.getPersistentPropertyNamesForDomainClass(ResourceType.class, List)
            List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(ResourceType.class)
            List<PersistentProperty> resourceTypePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(ResourceType.class)

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
                                String resourceType = resourceTypeParamMap?.get("resourceType")?.toString()?.trim()
                                String subType = resourceTypeParamMap?.get("subType")?.toString()?.trim()
                                String standardUnit = resourceTypeParamMap?.get("standardUnit")?.toString()?.trim()

                                if (standardUnit) {
                                    if (!allowedUnits*.toUpperCase().contains(standardUnit.toUpperCase()) && !standardUnit.equalsIgnoreCase("KG")) {
                                        warnMessage = warnMessage + "ResourceType: ${resourceType}, with subType: ${subType}, standardUnit: ${standardUnit} is not one of ${allowedUnits} <br/>"
                                    }
                                }

                                if (!resourceTypeParamMap.subMap(mandatoryProperties)?.collect({
                                    it.value
                                })?.contains("") && !resourceTypeParamMap.subMap(mandatoryProperties)?.collect({
                                    it.value
                                })?.contains(null)) {

                                    if (resourceType) {
                                        def invalidChar = ""

                                        resourceTypeParamMap?.each { String attributeName, attributeValue ->
                                            if (listProperties.contains(attributeName) && attributeValue) {
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
                                        }

                                        resourceTypeParamMap?.each { key, value ->
                                            if (value instanceof String && StringUtils.containsAny(value, invalidFieldChars)) {
                                                invalidChar = invalidChar + "Found invalid character from resourceType ${resourceType} in field ${key}: ${value}<br />"
                                            }
                                        }

                                        if (!invalidChar) {
                                            ResourceType resourceTypeObject = getResourceType(resourceType, subType)

                                            if (resourceTypeObject) {
                                                resourceTypePersistentProperties?.each { PersistentProperty domainClassProperty ->
                                                    DomainObjectUtil.callSetterByAttributeName(domainClassProperty.name, resourceTypeObject, resourceTypeParamMap.get(domainClassProperty.name), Boolean.TRUE)
                                                }
                                            } else {
                                                resourceTypeObject = new ResourceType(resourceTypeParamMap)
                                            }
                                            resourceTypeObject.importFile = fileName

                                            List<String> allAllowedEolProcesses = []
                                            String eolProcessRegulated = resourceTypeObject.eolProcessRegulated
                                            String eolProcessNonRegulated = resourceTypeObject.eolProcessNonRegulated
                                            List<String> eolProcessAllowedChoices = resourceTypeObject.eolProcessAllowedChoices

                                            if (eolProcessRegulated) {
                                                allAllowedEolProcesses.add(eolProcessRegulated)
                                            }
                                            if (eolProcessNonRegulated) {
                                                allAllowedEolProcesses.add(eolProcessNonRegulated)
                                            }
                                            if (eolProcessAllowedChoices) {
                                                allAllowedEolProcesses.addAll(eolProcessAllowedChoices)
                                            }

                                            if (!allAllowedEolProcesses.isEmpty()) {
                                                resourceTypeObject.eolProcessAllowedChoices = allAllowedEolProcesses.unique()
                                            }

                                            if (resourceTypeObject.validate()) {
                                                okResourceTypeList.add(resourceTypeObject)
                                            } else {
                                                errorResourceTypeList.add(resourceTypeObject)
                                                amountOfRejectedResourceTypes++
                                            }
                                        } else {
                                            errorMessage = errorMessage + invalidChar
                                            amountOfRejectedResourceTypes++
                                        }
                                    }
                                } else {
                                    amountOfRejectedResourceTypes++
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

            if (!okResourceTypeList.isEmpty()) {
                ResourceType.withNewTransaction {
                    okResourceTypeList.each { ResourceType resourceType ->
                        if (resourceType.id) {
                            resourceType.merge(flush: true)
                        } else {
                            loggerUtil.info(log, "Adding new resourceType ${resourceType.resourceType} for the imported row.")
                            resourceType.save(flush: true)
                        }
                        List duplicates = ResourceType.findAllByResourceType(resourceType.resourceType)

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
                resourceTypesUpdated = true
            }

            if (!errorResourceTypeList.isEmpty()) {
                amountOfRejectedResourceTypes = amountOfRejectedResourceTypes + errorResourceTypeList.size()

                errorResourceTypeList.each { ResourceType resourceType ->
                    errorMessage = errorMessage + resourceType.errors.getAllErrors()
                }
            }
            returnable.put("file", fileName)
            returnable.put("sheets", sheets)
            returnable.put("okResourceTypes", okResourceTypeList)
            returnable.put("errorResourceTypes", errorResourceTypeList)
            returnable.put("errorMessage", errorMessage)
            returnable.put("warnMessage", warnMessage)
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in importing resourceTypes", e)
            returnable.put("errorMessage", e)
        }
        return returnable
    }

    List<ResourceType> getResourceTypesBySkipResourceTypes(List<String> skipResourceTypes) {
        if (skipResourceTypes) {
            return getActiveResourceTypes()?.findAll({ !skipResourceTypes.contains(it.resourceType) })
        } else {
            return getActiveResourceTypes()
        }
    }

    List<ResourceType> getResourceTypesByResourceTypeList(List<String> resourceTypeTypes) {
        if (resourceTypeTypes) {
            return getActiveResourceTypes()?.findAll({ resourceTypeTypes.contains(it.resourceType) })
        } else {
            return null
        }
    }

    List<ResourceType> getResourceSubTypesByResourceType(String resourceType) {
        if (resourceType) {
            return getActiveResourceTypes(resourceType)?.findAll({ it.subType })
        } else {
            return null
        }
    }

    Boolean getResourceTypeHasSubTypes(String resourceType) {
        Boolean hasSubTypes = Boolean.FALSE
        if (resourceType) {
            if (getActiveResourceTypes(resourceType)?.find({ it.subType })) {
                hasSubTypes = Boolean.TRUE
            }
        }
        return hasSubTypes
    }

    List<ResourceType> getInActiveResourceTypes() {
        return ResourceType.findAllByActive(false)
    }

    List<ResourceType> getAllResourceTypeObjectsByTypes(List<String> resourceTypes, List<String> resourceSubTypes = null,
                                                        Boolean onlyActive = null) {
        if (onlyActive != null) {
            if (resourceSubTypes) {
                ResourceType
                        .findAllByResourceTypeInListAndSubTypeInListAndActive(resourceTypes, resourceSubTypes, onlyActive)
            } else {
                return ResourceType
                        .findAllByResourceTypeInListAndActive(resourceTypes, onlyActive)
            }
        }
        if (resourceSubTypes) {
            return ResourceType.findAllByResourceTypeInListAndSubTypeInList(resourceTypes, resourceSubTypes)
        }
        return ResourceType.findAllByResourceTypeInList(resourceTypes)
    }

    @Transactional(readOnly = true)
    List<ResourceType> findResourceTypes(List<Resource> resources) {
        List<String> types = resources.collect { it?.getResourceType() }.findAll { it }
        List<String> subTypes = resources.collect { it?.getResourceSubType() }.findAll { it }
        return findResourceTypes(types, subTypes)
    }

    @Transactional(readOnly = true)
    List<ResourceType> findResourceTypes(List<String> resourceTypes, List<String> resourceSubTypes) {
        return getAllResourceTypeObjectsByTypes(resourceTypes, resourceSubTypes, Boolean.TRUE)
    }

    ResourceType getResourceType(String resourceType, String resourceSubType = null, Boolean onlyActive = Boolean.FALSE) {
        ResourceType resourceTypeObject = null

        if (resourceType) {
            if (resourceSubType) {
                if (onlyActive) {
                    resourceTypeObject = ResourceType.findByResourceTypeAndSubTypeAndActive(resourceType, resourceSubType, Boolean.TRUE)
                }

                if (!resourceTypeObject && !onlyActive) {
                    resourceTypeObject = ResourceType.findByResourceTypeAndSubType(resourceType, resourceSubType)
                }
            } else {
                if (onlyActive) {
                    resourceTypeObject = ResourceType.findByResourceTypeAndActiveAndSubTypeIsNull(resourceType, Boolean.TRUE)
                }

                if (!resourceTypeObject && !onlyActive) {
                    resourceTypeObject = ResourceType.findByResourceTypeAndSubTypeIsNull(resourceType)
                }
            }
        }
        return resourceTypeObject
    }

    ResourceType getResourceTypeById(String resourceTypeId) {
        ResourceType resourceType = null

        if (resourceTypeId) {
            resourceType = getActiveResourceTypes()?.find({ resourceTypeId.equals(it.id.toString()) })
        }
        return resourceType
    }

    String getResourceTypeBySubType(String subType) {
        String resourceType = null

        if (subType) {
            resourceType = ResourceType.findBySubTypeAndActive(subType, Boolean.TRUE)?.resourceType
        }
        return resourceType
    }

    ResourceType getResourceTypeByResourceTypeAndSubType(String resourceType, String resourceSubType, Boolean active = Boolean.TRUE) {
        ResourceType type = null

        if (resourceSubType && resourceType) {
            type = ResourceType.findByResourceTypeAndSubTypeAndActive(resourceType, resourceSubType, active)
        } else if (resourceSubType) {
            type = ResourceType.findBySubTypeAndActive(resourceSubType, active)
        } else if (resourceType) {
            type = ResourceType.findByResourceTypeAndActiveAndSubTypeIsNull(resourceType, active)
        }
        return type
    }

    ResourceType getResourceTypeObjectBySubType(String subType, Boolean active = Boolean.TRUE) {
        ResourceType resourceType = null

        if (subType) {
            if (active) {
                resourceType = getActiveResourceTypes(null, subType)?.getAt(0)
            } else {
                resourceType = ResourceType.findBySubTypeAndActive(subType, Boolean.FALSE)
            }
        }
        return resourceType
    }

    Document getSubTypeDocumentBySubType(String subType, Map projection = null, Boolean active = Boolean.TRUE) {
        Document resourceType

        if (subType) {
            if (projection) {
                resourceType = ResourceType.collection.findOne([subType: subType, active: active], projection)
            } else {
                resourceType = ResourceType.collection.findOne([subType: subType, active: active])
            }
        }
        return resourceType
    }

    List<Document> getSubTypeDocumentsBySubTypes(List<String> subTypes, Map projection = null, Boolean active = Boolean.TRUE) {
        List<Document> resourceTypes

        if (subTypes) {
            if (projection) {
                resourceTypes = ResourceType.collection.find([subType: [$in: subTypes], active: active], projection)?.toList()
            } else {
                resourceTypes = ResourceType.collection.find([subType: [$in: subTypes], active: active])?.toList()
            }
        }
        return resourceTypes
    }

    boolean deleteResourceType(id) {
        boolean deleteOk = false

        if (id) {
            ResourceType resourceType = ResourceType.get(id)

            if (resourceType) {
                resourceType.delete(flush: true)
                deleteOk = true
                resourceTypesUpdated = true
            }
        }
        return deleteOk
    }

    ResourceType saveResourceType(ResourceType resourceType) {
        if (!resourceType.hasErrors()) {
            if (resourceType.id) {
                resourceType.merge(flush: true, failOnError: true)
            } else {
                resourceType.save(flush: true, failOnError: true)
            }
            resourceTypesUpdated = true
        } else {
            loggerUtil.error(log, "Could not save the resourceType: ${resourceType.getErrors()}")
            flashService.setErrorAlert("Could not save the resourceType: ${resourceType.getErrors()}", true)
        }
        return resourceType
    }

    Workbook generateImpactsExcel(ResourceType resourceSubType, List<Resource> resourcesBySubType, String stage, Boolean impactsAsKg) {
        Workbook wb

        if (resourceSubType && resourcesBySubType) {
            wb = new HSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            Sheet sheet = wb.createSheet()
            Row headerRow = sheet.createRow(0)
            Integer i = 0
            List<String> headings = ["resourceId", "profileId", "resourceSubType", "staticFullName", "defaultThickness_mm", "density", "massConversionFactor", "dataProperties", "upstreamDB"]
            List<String> impacts = Resource.allowedImpactCategories

            Integer numberOfHeadings = headings.size() + impacts.size()

            headings.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }

            impacts.sort().each { String impact ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(impact)
                i++
            }
            i = 1

            resourcesBySubType.each { Resource resource ->
                if (resource.impacts?.get(stage)?.values()?.find({ it })) {
                    Row datasetRow = sheet.createRow(i)
                    Integer j = 0

                    headings.each { String heading ->
                        Cell cell = datasetRow.createCell(j)
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(DomainObjectUtil.callGetterByAttributeName(heading, resource)?.toString())
                        j++
                    }

                    impacts.sort().each { String impact ->
                        Cell cell = datasetRow.createCell(j)
                        cell.setCellType(CellType.NUMERIC)
                        Double impactValue = (Double) resource.impacts?.get(stage)?.get(impact)

                        if (impactValue != null) {
                            Double convertedImpact = 0
                            if (impactsAsKg) {

                                if (!"kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    convertedImpact = unitConversionUtil.doConversion(impactValue, resource.defaultThickness_mm, "kg", resource, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)
                                    if (convertedImpact == null && resource.massConversionFactor) {
                                        convertedImpact = impactValue / resource.massConversionFactor
                                    }
                                } else if (resource.massConversionFactor) {
                                    convertedImpact = impactValue / resource.massConversionFactor
                                }

                            } else {
                                convertedImpact = unitConversionUtil.doConversion(impactValue, resource.defaultThickness_mm, resourceSubType.standardUnit, resource, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)

                                if (convertedImpact == null && resource.massConversionFactor && "kg".equalsIgnoreCase(resourceSubType.standardUnit)) {
                                    convertedImpact = impactValue / resource.massConversionFactor
                                }
                            }

                            cell.setCellValue(convertedImpact)
                        }
                        j++
                    }
                    i++
                }
            }

            numberOfHeadings.times {
                if (!3.equals(it)) {
                    sheet.autoSizeColumn(it)
                }
            }
        }
        return wb
    }

    Workbook generateDataRangesExcel(List<String> benchmarkSubTypeIds) {
        Workbook wb

        if (benchmarkSubTypeIds) {
            wb = new XSSFWorkbook()
            CellStyle topcs = wb.createCellStyle()
            topcs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.index)
            topcs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            CellStyle mincs = wb.createCellStyle()
            mincs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.index)
            mincs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            Sheet sheet = wb.createSheet()
            Row headerRow = sheet.createRow(0)
            Integer i = 0
            List<String> impacts = ["impactGWP100_kgCO2e", "biogenicCarbonStorage_kgCO2e", "impactODP_kgCFC11e", "impactAP_kgSO2e", "impactEP_kgPO4e", "impactPOCP_kgEthenee", "impactADPElements_kgSbe", "impactADPFossilFuels_MJ", "renewablesUsedAsEnergy_MJ", "renewablesUsedAsMaterial_MJ", "embodiedRenewableEnergy_MJ", "nonRenewablesUsedAsEnergy_MJ", "nonRenewablesUsedAsMaterial_MJ", "embodiedEnergy_MJ", "recyclingMaterialUse_kg", "renewableRecylingFuelUse_MJ", "nonRenewableRecylingFuelUse_MJ", "cleanWaterNetUse_m3", "wasteHazardous_kg", "wasteNonHazardous_kg", "wasteRadioactive_kg", "reusableMaterialsOutput_kg", "recyclableMaterialsOutput_kg", "energyMaterialsOutput_kg", "exportedEnergyOutput_MJ", "traciGWP_kgCO2e", "traciODP_kgCFC11e", "traciAP_kgSO2e", "traciEP_kgNe", "traciPOCP_kgO3e", "traciNRPE_MJ"]
            List<String> headings = ["ResourceType", "ResourceSubType", "StandardUnit"]
            headings.addAll(impacts)

            Integer numberOfHeadings = headings.size()

            headings.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }
            i = 1

            benchmarkSubTypeIds.each { String subTypeId ->
                ResourceType subType = getResourceTypeById(subTypeId)
                String standardUnit = subType.standardUnit

                if (standardUnit) {
                    String subtypeMapKey = "${subType.resourceType}.${subType.subType}.${subType.standardUnit}"
                    BasicDBObject query = new BasicDBObject()
                    query.put("resourceType", subType.resourceType)
                    query.put("resourceSubType", subType.subType)
                    query.put("active", true)
                    List<Document> resourcesBySubType = Resource.collection.find(query)?.toList()?.findAll({ Document resource -> resource.combinedUnits && resource.combinedUnits*.toUpperCase().contains(standardUnit.toUpperCase()) })
                    Integer neededRows

                    if (resourcesBySubType) {
                        Map<String, Map<String, Map<String, List<Double>>>> impactsForSubtype = [:]
                        impacts.each { String impact ->
                            // Dont look
                            List<Double> impactValues = resourcesBySubType.findAll({
                                it.get(impact) != null
                            })?.collect({
                                unitConversionUtil.doConversion((Double) it.get(impact), it.defaultThickness_mm, standardUnit, new Resource(defaultThickness_mm: it.defaultThickness_mm), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, it.density, it.unitForData, (!it.impactNonLinear && it.density && (it.defaultThickness_in || it.defaultThickness_mm)))
                            })?.findAll({ it != null })?.sort()?.reverse()

                            if (impactValues) {
                                Integer size = impactValues.size()
                                Integer topAndMinValuesToTake

                                if (size >= 20) {
                                    topAndMinValuesToTake = 10
                                } else {
                                    topAndMinValuesToTake = Math.floor(size / 2).intValue()
                                }

                                if (!neededRows || topAndMinValuesToTake * 2 > neededRows) {
                                    neededRows = topAndMinValuesToTake * 2
                                }

                                if (topAndMinValuesToTake > 0) {
                                    List<Double> top10 = impactValues.take(topAndMinValuesToTake)
                                    List<Double> bottom10 = impactValues.takeRight(topAndMinValuesToTake)

                                    Map<String, List<Double>> temp = [:]
                                    temp.put("min", bottom10)
                                    temp.put("top", top10)

                                    def existing = impactsForSubtype.get(subtypeMapKey)

                                    if (existing) {
                                        existing.put(impact, temp)
                                    } else {
                                        existing = [(impact): temp]
                                    }
                                    impactsForSubtype.put(subtypeMapKey, existing)
                                }
                            }
                        }

                        if (impactsForSubtype) {
                            impactsForSubtype.each { String subTypeName, Map<String, Map<String, List<Double>>> impactsMap ->
                                List<String> TypeSubTypeAndUnit = subtypeMapKey.tokenize('.')
                                neededRows.times { Integer rowNo ->
                                    Row datasetRow = sheet.createRow(i)
                                    Integer j = 0

                                    headings.each { String heading ->
                                        Cell cell = datasetRow.createCell(j)

                                        if ("ResourceType" == heading) {
                                            cell.setCellType(CellType.STRING)
                                            cell.setCellValue(TypeSubTypeAndUnit.get(0))
                                        } else if ("ResourceSubType" == heading) {
                                            cell.setCellType(CellType.STRING)
                                            cell.setCellValue(TypeSubTypeAndUnit.get(1))
                                        } else if ("StandardUnit" == heading) {
                                            cell.setCellType(CellType.STRING)
                                            cell.setCellValue(TypeSubTypeAndUnit.get(2) != "null" ? TypeSubTypeAndUnit.get(2) : "")
                                        } else {
                                            cell.setCellType(CellType.NUMERIC)
                                            Map<String, List<Double>> minAndTopForImpact = impactsMap.get(heading)

                                            if (minAndTopForImpact) {
                                                List<Double> top = minAndTopForImpact.get("top")
                                                List<Double> min = minAndTopForImpact.get("min")
                                                List<Double> all = []
                                                all.addAll(top)
                                                all.addAll(min)

                                                Double value

                                                try {
                                                    value = all.get(rowNo)
                                                } catch (IndexOutOfBoundsException e) {
                                                    // ok
                                                }

                                                if (value != null) {
                                                    if (min.contains(value) && top.contains(value)) {
                                                        if (top.size() >= rowNo + 1) {
                                                            cell.setCellStyle(topcs)
                                                        } else {
                                                            cell.setCellStyle(mincs)
                                                        }
                                                    } else {
                                                        if (min.contains(value)) {
                                                            cell.setCellStyle(mincs)
                                                        } else if (top.contains(value)) {
                                                            cell.setCellStyle(topcs)
                                                        }
                                                    }
                                                    cell.setCellValue(value)
                                                }
                                            }
                                        }
                                        j++
                                    }
                                    i++
                                }
                            }
                        }
                    }
                }
            }
            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }
        }
        return wb
    }

    Workbook generateQAExcel(ResourceType resourceType, List<Resource> resourcesBySubType) {
        Workbook wb

        if (resourceType && resourcesBySubType) {
            wb = new XSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            Sheet sheet = wb.createSheet()
            Row headerRow = sheet.createRow(0)
            Integer i = 0
            List<String> headings = ["CLASS", "MATERIAL", "QUANTITY", "QTY_TYPE", "altQTY_TYPE"]

            Integer numberOfHeadings = headings.size()

            headings.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }
            i = 1

            resourcesBySubType.each { Resource resource ->
                Row datasetRow = sheet.createRow(i)
                Integer j = 0

                headings.each { String heading ->
                    Cell cell = datasetRow.createCell(j)

                    if ("CLASS".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue("OTHER")
                    } else if ("MATERIAL".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resource.staticFullName ?: "")
                    } else if ("QUANTITY".equals(heading)) {
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(1)
                    } else if ("QTY_TYPE".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resource.unitForData ? resource.unitForData.toLowerCase() : "")
                    } else if ("altQTY_TYPE".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resourceType.isSubType ? resourceType.standardUnit?.toLowerCase() : resourceService.getSubType(resource)?.standardUnit?.toLowerCase() ?: "")
                    }
                    j++
                }
                i++
            }

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }
        }
        return wb
    }

    Map<String, Map<String, Double>> calculateAveragesByUpstreamDBClassified(List<String> upstreamDBClassified, List<Resource> resourcesByResourceType, ResourceType subType) {
        Map<String, Map<String, Double>> result = [:]
        List<String> impactCategories = Resource.allowedImpactCategories

        upstreamDBClassified?.each { String upstreamDB ->
            List<Resource> resourcesByUpstreamDb = resourcesByResourceType.findAll({ upstreamDB.equals(it.upstreamDBClassified) })

            if (resourcesByUpstreamDb) {
                Map<String, Double> averageByImpactCategory = [:]

                impactCategories?.each { String impactCategory ->
                    Double total = 0
                    List<Resource> resourcesByImpactCategory = []
                    resourcesByUpstreamDb.each { Resource resource ->
                        Double value = (Double) DomainObjectUtil.callGetterByAttributeName(impactCategory, resource)

                        if (value != null) {
                            if (!resource.unitForData?.equalsIgnoreCase(subType.standardUnit)) {
                                value = unitConversionUtil.doConversion(value, resource.defaultThickness_mm, subType.standardUnit, resource, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)
                            }

                            if (value != null) {
                                resourcesByImpactCategory.add(resource)
                                total = total + value
                            }
                        }
                    }

                    if (!resourcesByImpactCategory.isEmpty()) {
                        Double average = total / resourcesByImpactCategory.size()
                        averageByImpactCategory.put(impactCategory, average)

                        if (subType.resourceAveragesByUpstreamDBClassifiedAmounts) {
                            subType.resourceAveragesByUpstreamDBClassifiedAmounts.put(upstreamDB, resourcesByImpactCategory.size())
                        } else {
                            subType.resourceAveragesByUpstreamDBClassifiedAmounts = [(upstreamDB): resourcesByImpactCategory.size()]
                        }
                    }
                }

                if (averageByImpactCategory && !averageByImpactCategory.isEmpty()) {
                    result.put(upstreamDB, averageByImpactCategory)
                }
            }
        }
        return result
    }

    Double convertResourceUnitToStandardUnit(Double value, Resource resource, String standardUnit) {
        Double convertedValue = unitConversionUtil.doConversion(value, resource.defaultThickness_mm, standardUnit, null, Boolean.TRUE,
                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, resource.density, resource.unitForData, resource.allowVariableThickness)

        if (convertedValue == null && resource.massConversionFactor && "kg".equalsIgnoreCase(standardUnit)) {
            convertedValue = value / resource.massConversionFactor
        }
        return convertedValue
    }

    Double convertDocResourceUnitToStandardUnit(Double value, Document resource, String standardUnit) {
        Boolean allowVariableThickness = (!resource.get("impactNonLinear") && (resource.get("density") || "m2".equals(resource.get("unitForData"))) && (resource.get("defaultThickness_in") || resource.get("defaultThickness_mm")))
        Double convertedValue = unitConversionUtil.doConversion(value, (Double) resource.get("defaultThickness_mm"), standardUnit, null,
                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, (Double) resource.get("density"),
                (String) resource.get("unitForData"), allowVariableThickness)

        if (convertedValue == null && resource.massConversionFactor && "kg".equalsIgnoreCase(standardUnit)) {
            convertedValue = value / (Double) resource.massConversionFactor
        }
        return convertedValue
    }

    @Deprecated
    List<ResourceType> getActiveResourceTypes() {
        return getResourceTypes()
    }

    List<ResourceType> getActiveResourceTypes(String resourceType, String subType = null) {
        if (!resourceType && !subType) {
            return Collections.emptyList()
        } else if (!subType) {
            return ResourceType.findAllByResourceTypeAndActive(resourceType, true)
        } else if (!resourceType) {
            return ResourceType.findAllBySubTypeAndActive(subType, true)
        }
        return ResourceType.findAllByResourceTypeAndSubTypeAndActive(resourceType, subType, true)
    }

    //TODO: this method can potentially fetch too many objects and store them in not a thread-safe manner
    private List<ResourceType> getResourceTypes() {
        if (!resourceTypes || isResourceTypesUpdated()) {
            try {
                List r = ResourceType.findAllByActive(true)

                if (r) {
                    resourceTypes = new ArrayList<ResourceType>(r)
                }
            } catch (Exception e) {

            }
        }
        return resourceTypes
    }

    private boolean isResourceTypesUpdated() {
        if (resourceTypesUpdated) {
            resourceTypesUpdated = false
            return true
        } else {
            return false
        }
    }

    List<ResourceType> getLinkedSubTypes(linkedSubTypes) {
        return ResourceType.collection.find([subType: [$in: linkedSubTypes]])?.collect({ it as ResourceType })
    }

    String getLocalizedName(ResourceType resourceType) {
        if (!resourceType) {
            return "Localized name missing for resourceType"
        }
        String fallback = "Localized name missing for resourceType ${resourceType}"
        return localizationService.getLocalizedProperty(resourceType, "name", "name", fallback)
    }

    String getLocalizedShortName(ResourceType resourceType) {
        if (!resourceType) {
            return "Localized shortName missing for resourceType"
        }
        String shortName = localizationService.getLocalizedProperty(resourceType, "shortName", "shortName",
                "")
        return shortName ?: getLocalizedName(resourceType)
    }

    List<ResourceType> getSubTypes(ResourceType resourceType) {
        if (!resourceType.subType) {
            return getResourceTypesByResourceTypeList([resourceType.resourceType])?.findAll({it.subType})
        } else {
            return null
        }
    }

    List<Resource> getResources(String resourceType) {
        return optimiResourceService.getResourcesByResourceType(resourceType)
    }

    List<Resource> getResourcesBySubType(ResourceType resourceType) {
        return optimiResourceService.getResourcesByResourceSubType(resourceType.resourceType, resourceType.subType)
    }

    Integer getResourceSubTypeCount(ResourceType resourceType) {
        Integer count
        if (resourceType) {
            count = optimiResourceService.getResourcesCountByResourceType(resourceType.resourceType, resourceType.subType)
        }
        return count ? count : 0
    }

    Integer getNestedFiltersCount(Map<String, List<Object>> resourceFilterCriteria = null, Map <String, String> userGivenFilter = null, ResourceType resourceType) {
        return optimiResourceService.getResourceCountForNestedFilters(resourceType.resourceType, resourceType.subType, resourceFilterCriteria, userGivenFilter)
    }
    Integer getResourceSubTypeCountByExcluded(Boolean excluded, ResourceType resourceType) {
        Integer count
        if (excluded != null && resourceType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType.resourceType)
            query.put("resourceSubType", resourceType.subType)
            query.put("active", true)
            if (excluded) {
                query.put("excludeFromBenchmark", true)
            } else {
                query.put("\$or", [["excludeFromBenchmark": null], ["excludeFromBenchmark": false]])
            }
            count = Resource.collection.count(query)?.intValue()
        }
        return count ? count : 0
    }

    Integer getResourceSubTypeCountByDB(String upstreamDB, ResourceType resourceType) {
        Integer count
        if (upstreamDB && resourceType) {
            count = Resource.findAllByResourceTypeAndResourceSubTypeAndUpstreamDBClassified(resourceType.resourceType, resourceType.subType, upstreamDB)?.size()
        }
        return count ? count : 0
    }

    Integer getResourceCount(String resourceType) {
        return optimiResourceService.getResourcesCountByResourceType(resourceType)
    }

    Double getAverageByImpactCategory(String upstreamDB, String impactCategory, Map<String, Map<String, Double>> resourceAveragesByUpstreamDBClassified) {
        Double average
        if (resourceAveragesByUpstreamDBClassified && upstreamDB && impactCategory) {
            Map<String, Double> upstreamDBAverages = resourceAveragesByUpstreamDBClassified.get(upstreamDB)

            if (upstreamDBAverages) {
                average = upstreamDBAverages.get(impactCategory)
            }
        }
        return average
    }

    String getImpactCategoryDifference(String impactCategory, Map<String, Map<String, Double>> resourceAveragesByUpstreamDBClassified) {
        DecimalFormat df = new DecimalFormat("#")
        String difference
        if (impactCategory && resourceAveragesByUpstreamDBClassified) {
            if (resourceAveragesByUpstreamDBClassified.size() > 1) {
                Double gabi
                Double ecoinvent
                resourceAveragesByUpstreamDBClassified.each { String key, Map<String, Double> upstreamDBAverages ->
                    if ("ecoinvent".equalsIgnoreCase(key)) {
                        ecoinvent = upstreamDBAverages.get(impactCategory)
                    } else if ("gabi".equalsIgnoreCase(key)) {
                        gabi = upstreamDBAverages.get(impactCategory)
                    }
                }

                if (gabi != null && ecoinvent != null) {
                    Double doubleDifference = ((gabi - ecoinvent)/ecoinvent) * 100

                    if (doubleDifference < -80 || doubleDifference > 500) {
                        difference = "<span style=\"color: #FF2D41;\"><strong>${df.format(doubleDifference)} %</strong></span>"
                    } else if (doubleDifference < -25 || doubleDifference > 25) {
                        difference = "<span><strong>${df.format(doubleDifference)} %</strong></span>"
                    } else {
                        difference = "<span>${df.format(doubleDifference)} %</span>"
                    }
                }
            }
        }
        return difference ?: null
    }

    Map<String, Integer> getCalculateFailsForSubtype(ResourceType resourceType) {
        Map<String, Integer> failsMap = [:]

        if (resourceType) {
            if (resourceType.resourceType && resourceType.subType) {
                Map projection = [unitForData:1, defaultThickness_mm:1, density:1, impactNonLinear:1, combinedUnits: 1, massConversionFactor: 1]
                List<Document> resourcesForSubType = optimiResourceService.getResourcesForCalculationFailCheck(resourceType.resourceType, resourceType.subType, projection)

                if (resourcesForSubType) {
                    Integer conversionFails = 0
                    Integer conversionFailsKg = 0
                    Integer requiredUnitFails = 0
                    Integer badUnitFails = 0

                    resourcesForSubType.each { Document resource ->
                        List<String> allowedUnits = (List<String>) resource.combinedUnits

                        if (resourceType.standardUnit && !resourceType.standardUnit.equalsIgnoreCase(resource.unitForData)) {
                            if (convertDocResourceUnitToStandardUnit(100, resource, resourceType.standardUnit) == null) {
                                conversionFails++
                            }
                        }

                        if (convertDocResourceUnitToStandardUnit(100, resource, "kg") == null) {
                            conversionFailsKg++
                        }

                        if ((resourceType.requiredUnits && allowedUnits && !allowedUnits.containsAll(resourceType.requiredUnits*.toLowerCase())) || (resourceType.requiredUnits && !allowedUnits)) {
                            requiredUnitFails++
                        }

                        if (resourceType.avoidableUnits && allowedUnits && CollectionUtils.containsAny(allowedUnits, resourceType.avoidableUnits*.toLowerCase())) {
                            badUnitFails++
                        }
                    }
                    failsMap.put("conversionFails", conversionFails)
                    failsMap.put("conversionFailsKg", conversionFailsKg)
                    failsMap.put("requiredUnitFails", requiredUnitFails)
                    failsMap.put("badUnitFails", badUnitFails)
                }
            }
        }
        return failsMap
    }

    Map<String, Integer> getAvailableStages(ResourceType resourceType) {
        Map<String, Integer> stagesAndCount = [:]

        if (resourceType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType.resourceType)
            query.put("resourceSubType", resourceType.subType)
            query.put("active", true)

            Resource.collection.find(query, [impacts:1])?.each { Document r ->
                r.impacts?.each { String stage, Map<String, Object> impactsPerStage ->
                    if (impactsPerStage?.values()?.find({it})) {
                        def count = stagesAndCount.get(stage)

                        if (count) {
                            count++
                        } else {
                            count = 1
                        }
                        stagesAndCount.put(stage, count)
                    }
                }
            }
        }
        return stagesAndCount
    }
}
