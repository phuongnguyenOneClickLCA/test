package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.APIDataProvision
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorBenchmark
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import com.mongodb.BasicDBObject
import groovy.json.JsonBuilder
import groovy.json.StringEscapeUtils
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document

class ResourceDumpService {

    def domainClassService
    def applicationService
    def productDataListService
    def indicatorBenchmarkService
    def optimiStringUtils
    LoggerUtil loggerUtil
    FlashService flashService

    def getEnvironmentDataSourceTypesForDump() {
        Map<String, String> formattedTypes = [:]
        BasicDBObject environmentDataSourceTypesQuery = new BasicDBObject()
        environmentDataSourceTypesQuery.put("applicationId", "LCA")
        environmentDataSourceTypesQuery.put("dataProperties", [$nin:["LCA"]])
        environmentDataSourceTypesQuery.put("active", true)
        List<String> environmentDataSourceTypes = Resource.collection.distinct("environmentDataSourceType", environmentDataSourceTypesQuery, String.class)?.toList()

        environmentDataSourceTypes?.each { String type ->
            formattedTypes.put(type, type)
        }
        return formattedTypes
    }

    def getCountriesForDump() {
        Map<String, String> formattedCountries = [LOCAL:"LOCAL"]
        List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1])?.toList()?.sort({ it.nameEN })
        countries?.each { Document c ->
            formattedCountries.put(c.resourceId?.toString(), c.nameEN?.toString())
        }
        return formattedCountries
    }

    def getSubTypesForDump() {
        Map<String, String> formattedSubTypes = [:]
        List<Document> allSubTypes = ResourceType.collection.find([subType: [$exists: true], active: true], [resourceType: 1, subType: 1, nameEN: 1])?.toList()?.sort({ it.nameEN })
        allSubTypes?.each { Document s ->
            BasicDBObject query = new BasicDBObject()
            query.put("applicationId", "LCA")
            query.put("dataProperties", [$nin:["LCA"]])
            query.put("resourceType", s.resourceType)
            query.put("resourceSubType", s.subType)
            query.put("active", true)
            Integer count = Resource.collection.count(query)?.intValue()

            if (count > 0) {
                formattedSubTypes.put(s.subType?.toString(), s.nameEN?.toString())
            }
        }
        return formattedSubTypes
    }

    def getEpdProgramsForDump() {
        Map<String, String> formattedEpdPrograms = [:]
        BasicDBObject query = new BasicDBObject()
        query.put("applicationId", "LCA")
        query.put("dataProperties", [$nin:["LCA"]])
        query.put("active", true)
        List<String> epdPrograms = Resource.collection.distinct("epdProgram", query, String.class)?.toList()
        epdPrograms?.each { String program ->
            formattedEpdPrograms.put(program, program)
        }
        return formattedEpdPrograms
    }

    def getResourcesForDump(Map<String, List<Object>> filter = null) {
        BasicDBObject projection = new BasicDBObject([_id: 1, staticFullName: 1, dataProperties: 1, isoCodesByAreas: 1, combinedUnits: 1,
                                                      resourceSubType: 1, environmentDataSourceType: 1, searchString: 1, epdProgram: 1,
                                                      isMultiPart: 1, defaultThickness_mm:1,allowVariableThickness:1,impactNonLinear:1,
                                                      resourceGroup: 1, enabledPurposes:1])

        List<Document> resources

        if (filter) {
            BasicDBObject query = new BasicDBObject()
            filter.each { String key, List<Object> values ->
                query.put((key), [$in: values])
            }
            query.put("active", true)
            query.put("privateDataset", [$exists: false])
            resources = Resource.collection.find(query, projection)?.toList()
        } else {
            resources = Resource.collection.find([applicationId: "LCA", active: true, dataProperties: [$nin:["LCA"]], privateDataset: [$exists: false]], projection)?.toList()
        }


        List<Document> filteredResources = []
        resources?.each { Document r ->
            List<String> dataProperties = (List<String>) r.dataProperties

            if (dataProperties && !dataProperties.isEmpty()) {
                r["isoCountryCode"] = r.isoCodesByAreas?.values()?.first()
                r["staticFullName"] = optimiStringUtils.removeIllegalCharacters(r.staticFullName)
                r["area"] = r.isoCodesByAreas?.keySet()?.first()
                r["areas"] = r.isoCodesByAreas?.keySet()?.toList()
                r.remove("isoCodesByAreas")
                r["allowedUnits"] = r.combinedUnits
                r["_id"] = r._id.toString()
                r["isMultiPart"] = r.isMultiPart?:Boolean.FALSE
                if(r.impactNonLinear && !DomainObjectUtil.callGetterByAttributeName("allowVariableThickness" , (Resource)r) && r.defaultThickness_mm) {
                    r["fixedThickness_mm"] = r?.defaultThickness_mm
                }
                filteredResources.add(r)
            }
        }
        return filteredResources
    }

    def getNonMaterialResourcesForDump() {
        BasicDBObject projection = new BasicDBObject([_id: 1, staticFullName: 1, dataProperties: 1, isoCodesByAreas: 1, combinedUnits: 1,
                                                      resourceSubType: 1, environmentDataSourceType: 1, searchString: 1, epdProgram: 1, resourceGroup: 1, enabledPurposes: 1])
        List<Document> resources = Resource.collection.find([applicationId: "LCA-utility", active: true, privateDataset: [$exists: false]], projection)?.toList()
        List<Document> filteredResources = []
        resources?.each { Document r ->
            List<String> dataProperties = (List<String>) r.dataProperties

            if (dataProperties && !dataProperties.isEmpty()) {
                r["isoCountryCode"] = r.isoCodesByAreas?.values()?.first()
                r["area"] = r.isoCodesByAreas?.keySet()?.first()
                r["areas"] = r.isoCodesByAreas?.keySet()?.toList()
                r.remove("isoCodesByAreas")
                r["allowedUnits"] = r.combinedUnits
                r["_id"] = r._id.toString()
                filteredResources.add(r)
            }
        }
        return filteredResources
    }

    def getBuildingTypesForDump() {
        List<Map<String, String>> listFormattedBuildingTypes = []
        BasicDBObject projection = new BasicDBObject([nameEN: 1, resourceId: 1])
        List<Document> resources = Resource.collection.find([applicationId: "system", active: true, resourceGroup: "buildingTypes", privateDataset: [$exists: false]], projection)?.toList()
        resources?.each { Document r ->
            Map<String, String> formattedBuildingTypes = [name:r.nameEN, id:r.resourceId]
            listFormattedBuildingTypes.add(formattedBuildingTypes)
        }
        return listFormattedBuildingTypes
    }

    def getIndicatorsForDump() {
        List<Map<String, String>> listFormattedIndicators = []
        BasicDBObject projection = new BasicDBObject([name: 1, indicatorId: 1])
        List<Document> indicators = Indicator.collection.find([indicatorUse: "design", visibilityStatus: null, active: true, exportToRevitBenchmark: true], projection)?.toList()
        indicators?.each { Document i ->
            Map<String, String> formattedIndicator = [name:i.name?.get("EN"), indicatorId:i.indicatorId]
            listFormattedIndicators.add(formattedIndicator)
        }
        return listFormattedIndicators
    }

    def createAccountSpecificPrivateDataDump(Account account) {
        File file

        if (account) {
            BasicDBObject projection = new BasicDBObject([_id: 1, staticFullName: 1, dataProperties: 1, isoCodesByAreas: 1, combinedUnits: 1,
                                                          resourceSubType: 1, environmentDataSourceType: 1, searchString: 1, epdProgram: 1, isMultiPart:1,
                                                          defaultThickness_mm:1,allowVariableThickness:1,impactNonLinear:1, resourceGroup: 1, enabledPurposes: 1])

            List<Document> resources = Resource.collection.find([active: true, privateDatasetAccountId: account.id.toString()], projection)?.toList()

            List<Document> filteredResources = []
            resources?.each { Document r ->
                List<String> dataProperties = (List<String>) r.dataProperties

                if (dataProperties && !dataProperties.isEmpty()) {
                    r["staticFullName"] = optimiStringUtils.removeIllegalCharacters(r.staticFullName?.toString())
                    r["isoCountryCode"] = r.isoCodesByAreas?.values()?.first()
                    r["area"] = r.isoCodesByAreas?.keySet()?.first()
                    r["areas"] = r.isoCodesByAreas?.keySet()?.toList()
                    r.remove("isoCodesByAreas")
                    r["allowedUnits"] = r.combinedUnits
                    r["_id"] = r._id.toString()
                    r["isMultiPart"] = r.isMultiPart?:Boolean.FALSE
                    if(r.impactNonLinear && !DomainObjectUtil.callGetterByAttributeName("allowVariableThickness" , (Resource)r) && r.defaultThickness_mm) {
                        r["fixedThickness_mm"] = r?.defaultThickness_mm
                    }
                    filteredResources.add(r)
                }
            }

            List<Document> licensedResources = []
            List<ProductDataList> productDataLists = productDataListService.getProductDataListByAccountId(account.id.toString())
            List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccountId(account?.id?.toString())

            if (productDataLists) {
                ResourceCache resourceCache
                productDataLists.each {
                    resourceCache = ResourceCache.init(it.datasets)
                    it.datasets?.each {
                        Resource r = resourceCache.getResource(it)

                        if (r && r.active && r.dataProperties) {
                            Document rd = new Document()
                            rd["isMultiPart"] = r.isMultiPart?:Boolean.FALSE
                            rd["staticFullName"] = optimiStringUtils.removeIllegalCharacters(r.staticFullName)
                            rd["dataProperties"] = r.dataProperties
                            rd["isoCountryCode"] = r.isoCodesByAreas?.values()?.first()
                            rd["area"] = r.isoCodesByAreas?.keySet()?.first()
                            rd["areas"] = r.isoCodesByAreas?.keySet()?.toList()
                            rd["allowedUnits"] = r.combinedUnits
                            rd["resourceSubType"] = r.resourceSubType
                            rd["environmentDataSourceType"] = r.environmentDataSourceType
                            rd["searchString"] = r.searchString
                            rd["epdProgram"] = r.epdProgram
                            if(r.impactNonLinear && !DomainObjectUtil.callGetterByAttributeName("allowVariableThickness" , (Resource)r) && r.defaultThickness_mm) {
                                rd["fixedThickness_mm"] = r?.defaultThickness_mm
                            }
                            rd["_id"] = r.id.toString()
                            licensedResources.add(rd)
                        }
                    }
                }
            }
            if (linkedDataLists) {
                ResourceCache resourceCache
                linkedDataLists.each {
                    resourceCache = ResourceCache.init(it.datasets)
                    it.datasets?.each {
                        Resource r = resourceCache.getResource(it)

                        if (r && r.active && r.dataProperties) {
                            Document rd = new Document()
                            rd["isMultiPart"] = r.isMultiPart?:Boolean.FALSE
                            rd["staticFullName"] = optimiStringUtils.removeIllegalCharacters(r.staticFullName)
                            rd["dataProperties"] = r.dataProperties
                            rd["isoCountryCode"] = r.isoCodesByAreas?.values()?.first()
                            rd["area"] = r.isoCodesByAreas?.keySet()?.first()
                            rd["areas"] = r.isoCodesByAreas?.keySet()?.toList()
                            rd["allowedUnits"] = r.combinedUnits
                            rd["resourceSubType"] = r.resourceSubType
                            rd["environmentDataSourceType"] = r.environmentDataSourceType
                            rd["searchString"] = r.searchString
                            rd["epdProgram"] = r.epdProgram
                            if(r.impactNonLinear && !DomainObjectUtil.callGetterByAttributeName("allowVariableThickness" , (Resource)r) && r.defaultThickness_mm) {
                                rd["fixedThickness_mm"] = r?.defaultThickness_mm
                            }
                            rd["_id"] = r.id.toString()
                            rd["resourceGroup"] = r.resourceGroup
                            rd["enabledPurposes"] = r.enabledPurposes
                            licensedResources.add(rd)
                        }
                    }
                }
            }



            if (filteredResources||licensedResources) {
                String filename = "${account.companyName?.replaceAll("\\W", "_")?.replaceAll("\\s{2,}", "_")?.trim()}_private_data"
                file = File.createTempFile(filename, ".json")
                Document d = new Document()
                d.put("private_resources", filteredResources)
                d.put("licensed_resources", licensedResources)
                file.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(d).toPrettyString()))
            }
        }
        return file
    }

    def createApplicationSpecificDumps() {
        Application application = applicationService.getApplicationByApplicationId("IFC")
        if (application && application.apiDataProvisions) {
            application.apiDataProvisions.each { APIDataProvision apiDataProvision ->
                File dump = new File("/var/www/static/dumps/${apiDataProvision.dataCategory}_${Constants.RESOURCE_DUMP_FILENAME}.json")
                dump.getParentFile().mkdirs()
                dump.createNewFile()
                List<Document> resources = getResourcesForDump(apiDataProvision.dataFilters)
                Document d = new Document()
                d.put("dataCategory", apiDataProvision.dataCategory)
                d.put("resources", resources)
                dump.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(d).toString()))
            }
        }
        return true
    }

    def createResourceDumpFile() {
        try {
            File dump = new File("/var/www/static/dumps/${Constants.RESOURCE_DUMP_FILENAME}.json")
            dump.getParentFile().mkdirs()
            dump.createNewFile()

            List<Document> resources = getResourcesForDump()
            List<Document> nonMaterialResources = getNonMaterialResourcesForDump()
            Map<String, String> environmentDataSourceTypes = getEnvironmentDataSourceTypesForDump()
            Map<String, String> countries = getCountriesForDump()
            Map<String, String> resourceSubTypes = getSubTypesForDump()
            Map<String, String> epdPrograms = getEpdProgramsForDump()
            List<Map<String, String>> buildingTypes = getBuildingTypesForDump()
            List<Map<String, String>> indicators = getIndicatorsForDump()
            def dataDomains = Application.collection.findOne([applicationId: "IFC"])?.exportedDataDomains

            Document d = new Document()
            d.put("environmentDataSourceTypes", environmentDataSourceTypes)
            d.put("resourceSubTypes", resourceSubTypes)
            d.put("countries", countries)
            d.put("exportedDataDomains", dataDomains)
            d.put("epdPrograms", epdPrograms)
            d.put("availablebuildingTypes", buildingTypes)
            d.put("availableCalculationTools", indicators)
            d.put("resources", resources)
            d.put("nonMaterialResources", nonMaterialResources)

            dump.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(d).toString()))
            return true
        } catch (Exception e) {
            loggerUtil.error(log, "Revit JSON creation failed:", e)
            flashService.setErrorAlert("Error in JSON creation failed: ${e.getMessage()}", true)
        }
    }

    def createIndicatorBenckmarckDumpFile() {
        Boolean created = Boolean.FALSE
        try {
            String fileName = Constants.INDICATOR_BENCHMARK_DUMP_FILENAME
            File dump = new File("/var/www/static/dumps/${fileName}")
            dump.getParentFile().mkdirs()
            dump.createNewFile()

            ///CREATE EXCEL FILE
            List<String> persistingProperties = domainClassService.getPersistentPropertyNamesForDomainClass(IndicatorBenchmark.class)
            List<IndicatorBenchmark> indicatorBenchmarkList = IndicatorBenchmark.findAll();

            Workbook workbook = new XSSFWorkbook();
            String sheetName = WorkbookUtil.createSafeSheetName(Constants.INDICATOR_BENCHMARK);
            Sheet sheet = workbook.createSheet(sheetName);

            if(persistingProperties && indicatorBenchmarkList){
                int rowCont = 0;
                Row rowHeadind = sheet.createRow(rowCont++);

                persistingProperties.eachWithIndex { columnName, i ->
                    rowHeadind.createCell(i).setCellValue(columnName)
                }

                for (IndicatorBenchmark indicator: indicatorBenchmarkList) {
                    if (!indicator) {
                        continue
                    }
                    Row row = sheet.createRow(rowCont++)

                    persistingProperties.eachWithIndex { attribute, i ->
                        def value = (attribute && indicator) ? DomainObjectUtil.callGetterByAttributeName(attribute, indicator) : ""
                        if(value instanceof Date){
                            value = (value && indicator) ? indicatorBenchmarkService.getSimpleDate(indicator.benchmarkDate) : ""
                        } else{
                            value = value ? value.toString() : ""
                        }
                        row.createCell(i).setCellValue(value)
                    }
                }
            }
            ///END CREATE EXCEL FILE

            FileOutputStream out = new FileOutputStream(dump);
            workbook.write(out);
            out.close();

            created = Boolean.TRUE
        } catch (Exception e) {
            loggerUtil.error(log, "Indicator Benchmark file creation failed:", e)
            flashService.setErrorAlert("Indicator Benchmark file creation failed: ${e.message}", true)
        }
        return created
    }

    def createSpecialRulesFile() {
        Boolean created = Boolean.FALSE
        try {
            def specialRules = Application.collection.findOne([applicationId: "LCA"])?.specialRules
            File dump = new File("/var/www/static/dumps/${Constants.SPECIAL_RULES_FILENAME}.json")
            dump.getParentFile().mkdirs()
            dump.createNewFile()
            dump.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(specialRules).toPrettyString()))
            created = Boolean.TRUE
        } catch (Exception e) {
            loggerUtil.error(log, "Revit JSON creation failed:", e)
            flashService.setErrorAlert("Revit JSON creation failed: ${e.message}", true)
        }
        return created
    }
}
