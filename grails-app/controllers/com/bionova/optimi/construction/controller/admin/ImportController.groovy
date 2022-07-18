/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourcePurpose
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.data.ResourceCache
import com.mongodb.BasicDBObject
import grails.async.Promise
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import groovy.json.JsonBuilder
import groovy.json.StringEscapeUtils
import org.apache.catalina.connector.ClientAbortException
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
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
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import java.text.DateFormat
import java.text.SimpleDateFormat

import static grails.async.Promises.task

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class ImportController extends ExceptionHandlerController {

    static defaultAction = "form"
    def optimiResourceService
    def applicationService
    def resourceTypeService
    def userService
    def entityService
    def indicatorService
    def domainClassService
    def indicatorBenchmarkService
    def flashService
    def newCalculationServiceProxy
    def configurationService
    def importMapperService
    def constructionService
    def loggerUtil
    def nmdElementService

    private String pdfCheckProgress = ""
    private Boolean pdfCheckInProgress = false

    @Secured(["ROLE_DEVELOPER"])
    def calculatedResources() {
        User user = userService.getCurrentUser()
        List<License> licenses = userService.getLicenses(user)?.findAll({
            it.licensedFeatures?.
                    collect({ Feature feature -> feature.featureId })?.contains(Feature.COMPONENT_ABILITY)
        })
        Map resultsPerIndicator = [:]

        if (licenses) {
            List<Indicator> alreadyHandledIndicators = []

            licenses.each { License license ->
                license.licensedIndicators?.each { Indicator indicator ->
                    if (!alreadyHandledIndicators.contains(indicator) && indicator?.getResolveCalculationRules(null, true)?.
                            find({ it.mapToResource })) {
                        license.licensedEntities.each { Entity parentEntity ->
                            if ("design".equals(indicator.indicatorUse)) {
                                parentEntity.designs?.each { Entity design ->
                                    Double score = design.getDisplayResult(indicator.indicatorId)

                                    if (score != null) {
                                        Map<Entity, Double> existing = resultsPerIndicator.get(indicator)

                                        if (existing) {
                                            existing.put(design, score)
                                        } else {
                                            existing = [(design): score]
                                        }
                                        resultsPerIndicator.put(indicator, existing)
                                    }
                                }
                            } else {
                                parentEntity.operatingPeriods?.each { Entity operatingPeriod ->
                                    Double score = operatingPeriod.getDisplayResult(indicator.indicatorId)

                                    if (score != null) {
                                        Map<Entity, Double> existing = resultsPerIndicator.get(indicator)

                                        if (existing) {
                                            existing.put(operatingPeriod, score)
                                        } else {
                                            existing = [(operatingPeriod): score]
                                        }
                                        resultsPerIndicator.put(indicator, existing)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        [resultsPerIndicator: resultsPerIndicator]
    }

    def resourcesWithMultipleDefaultProfiles() {
        []
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def resourcesFromIndicator() {
        String indicatorId = params.indicatorId
        List<Resource> resources = []
        Workbook wb
        Map<Entity, List<Resource>> resourcesByEntity = [:]
        Indicator indicator

        if (indicatorId) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            User user = userService.getCurrentUser()
            List<License> licenses = userService.getLicenses(user)?.findAll({
                it.licensedIndicatorIds?.contains(indicator.indicatorId)
            })

            if (licenses) {
                List<Entity> entities = []

                licenses.each { License license ->
                    license.licensedEntities.each { Entity parentEntity ->
                        if ("design".equals(indicator.indicatorUse)) {
                            parentEntity.designs?.each { Entity design ->
                                Double score = design.getDisplayResult(indicator.indicatorId)

                                if (score != null && !entities.contains(design)) {
                                    entities.add(design)
                                }
                            }
                        } else {
                            parentEntity.operatingPeriods?.each { Entity operatingPeriod ->
                                Double score = operatingPeriod.getDisplayResult(indicator.indicatorId)

                                if (score != null && !entities.contains(operatingPeriod)) {
                                    entities.add(operatingPeriod)
                                }
                            }

                        }
                    }
                }

                if (!entities.isEmpty()) {
                    entities.each { Entity entity ->
                        Resource resource = optimiResourceService.mapUserAnswersToResource(indicator, entity, session)

                        if (resource) {
                            if (resourcesByEntity.get(entity)) {
                                def list = resourcesByEntity.get(entity)
                                list.add(resource)
                                resourcesByEntity.put(entity, list)
                            } else {
                                resourcesByEntity.put(entity, [resource])
                            }
                            resources.add(resource)
                        }
                    }
                }
            }
        }

        log.info("resourcesByEntity ${resourcesByEntity}")

        if (!resources.isEmpty() && !resourcesByEntity.isEmpty()) {
            wb = new XSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(true)
            cs.setFont(f)
            Sheet sheet = wb.createSheet()
            int rowIndex = 0
            Row row = sheet.createRow(rowIndex)
            Cell cell
            int cellIndex = 0
            List<String> headings = getHeadings(indicator)
            headings?.each { String heading ->
                cell = row.createCell(cellIndex)
                cell.setCellStyle(cs)
                cell.setCellType(CellType.STRING)
                cell.setCellValue(heading)
                cellIndex++
            }

            def dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            resourcesByEntity.each { Entity entity, List<Resource> entityResources ->
                entityResources.each { Resource resource ->
                    rowIndex++
                    Row contentRow = sheet.createRow(rowIndex)
                    cellIndex = 0

                    headings?.each { String heading ->
                        boolean castToString = false
                        cell = contentRow.createCell(cellIndex)
                        cell.setCellStyle(cs)


                        if ("Entity".equals(heading)) {
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(entity?.parentName)
                        } else if ("Design".equals(heading)) {
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(entity?.operatingPeriodAndName)
                        } else if ("Indicator".equals(heading)) {
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(indicatorService.getLocalizedName(indicator))
                        } else if ("Last Updated".equals(heading)) {
                            def updateDate = dateFormat.format(entity?.lastUpdated)
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(updateDate)
                        } else if (resource[heading]) {
                            if (resource[heading] instanceof String) {
                                cell.setCellType(CellType.STRING)
                            } else if (resource[heading] instanceof Double) {
                                cell.setCellType(CellType.NUMERIC)
                            } else if (resource[heading] instanceof Boolean) {
                                cell.setCellType(CellType.BOOLEAN)
                            } else {
                                cell.setCellType(CellType.STRING)
                                castToString = true
                            }

                            if (castToString) {
                                cell.setCellValue(resource[heading].toString())
                            } else {
                                cell.setCellValue(resource[heading])
                            }
                        }
                        cellIndex++
                    }
                }
            }
        }

        if (wb) {
            response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            response.setHeader("Content-disposition", "attachment; filename=${indicatorId}.xlsx")
            OutputStream outputStream = response.getOutputStream()
            wb.write(outputStream)
            outputStream.flush()
            outputStream.close()
        } else {
            render(text: "Could not download Excel file, no result created resources found")
        }
    }

    private List<String> getHeadings(Indicator indicator){

        List<String> headings = ["Entity", "Design", "Indicator", "Last Updated", "importFile", "staticFullName",
                                 "firstUploadTime", "combinedUnits", "upstreamDBClassified", "searchString",
                                 "benchmark", "isoCountryCode", "isoCodesByAreas", "resourceType", "resourceId",
                                 "profileId", "active", "multipleProfiles", "defaultProfile", "applicationId",
                                 "dataProperties", "resourceGroup", "nameFI", "nameEN", "nameNO", "nameHU", "nameJP", "unit",
                                 "unitForData", "impactNonLinear", "area", "areas", "energyConversionFactor",
                                 "ImpactBasis", "upstreamDB", "verificationStatus", "environmentDataSource",
                                 "detailedDataSource", "environmentDataSourceStandard", "environmentDataPeriod",
                                 "epdProgram"]

        List<CalculationRule> calRules = indicator.getResolveCalculationRules(null)
        headings.addAll(calRules?.collect({it.calculationRuleId}))
        return headings
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def resourceFromResult() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        Resource resource
        Indicator indicator
        Entity entity
        Workbook wb

        if (entityId && indicatorId) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            entity = entityService.readEntity(entityId)
            resource = optimiResourceService.mapUserAnswersToResource(indicator, entity, session)

            if (resource) {
                wb = new XSSFWorkbook()
                CellStyle cs = wb.createCellStyle()
                Font f = wb.createFont()
                f.setBold(true)
                cs.setFont(f)
                Sheet sheet = wb.createSheet()
                Row row = sheet.createRow(0)
                Cell cell
                int cellIndex = 0
                List<String> headings = getHeadings(indicator)
                headings?.each { String heading ->
                    cell = row.createCell(cellIndex)
                    cell.setCellStyle(cs)
                    cell.setCellType(CellType.STRING)
                    cell.setCellValue(heading)
                    cellIndex++
                }
                Row contentRow = sheet.createRow(1)
                cellIndex = 0

                headings.each { String heading ->
                    boolean castToString = false
                    cell = contentRow.createCell(cellIndex)
                    cell.setCellStyle(cs)
                    if ("Entity".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(entity?.parentName)
                    } else if ("Design".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(entity?.operatingPeriodAndName)
                    } else if ("Indicator".equals(heading)) {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(indicatorService.getLocalizedName(indicator))
                    } else if ("Last Updated".equals(heading)) {
                        def dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                        def updateDate = dateFormat.format(entity?.parentById?.lastUpdated)
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(updateDate)
                    } else {
                        if (resource[heading] instanceof String) {
                            cell.setCellType(CellType.STRING)
                        } else if (resource[heading] instanceof Double) {
                            cell.setCellType(CellType.NUMERIC)
                        } else if (resource[heading] instanceof Boolean) {
                            cell.setCellType(CellType.BOOLEAN)
                        } else {
                            cell.setCellType(CellType.STRING)
                            castToString = true
                        }

                        if (castToString) {
                            cell.setCellValue(resource[heading].toString())
                        } else {
                            cell.setCellValue(resource[heading])
                        }
                    }
                    cellIndex++
                }
            }
        }

        if (wb) {
            response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            response.setHeader("Content-disposition", "attachment; filename=${entity.operatingPeriodAndName?.replaceAll("[^a-zA-Z0-9.-]", " ")}_${indicator.indicatorId?.replaceAll("[^a-zA-Z0-9.-]", " ")}.xlsx")
            OutputStream outputStream = response.getOutputStream()
            wb.write(outputStream)
            outputStream.flush()
            outputStream.close()
        } else {
            flash.fadeErrorAlert = "Could not create an Excel file"
            chain(action: "calculatedResources")
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DATA_MGR"])
    def form() {
        User user = userService.getCurrentUser(true)
        boolean isProd = false
        if (Environment.current == Environment.PRODUCTION && request.getServerName() in Constants.PROD_DOMAINS) {
            isProd = true
        }
        if (user && user.internalUseRoles) {
            BasicDBObject filter = new BasicDBObject("active",true)
            List<String> applicationIds = Application.collection.distinct("applicationId", String.class)?.toList()?.sort()
            List<String> resourceGroups = Resource.collection.distinct("resourceGroup", String.class)?.toList()?.sort()
            List<String> epdPrograms = Resource.collection.distinct("epdProgram", filter, String.class)?.toList()?.sort()
            def prodWarning
            List<Resource> dryRunResources = getDryRunResources()
            String dryRunNotification

            if (dryRunResources) {
                dryRunNotification = message(code: "admin.import.dry_run_notification")
            }

            if (Environment.current == Environment.PRODUCTION && request.getServerName() in Constants.PROD_DOMAINS) {
                prodWarning = Constants.PROD_WARN
            }
            Map resourceWithMultipleProfiles

            Boolean searchedForMultipleProfiles = false
            if (params.resourceWithMultipleProfiles) {
                resourceWithMultipleProfiles = optimiResourceService.getResourcesWithMultipleProfiles()
                searchedForMultipleProfiles = true
            }

            Map<String, List<String>> applicationIdAndSupportedResourceGroups = [:]

            applicationIds?.each { String applicationId ->
                resourceGroups?.each { String resourceGroup ->
                    def hasResource = Resource.collection.findOne([applicationId: applicationId, resourceGroup: [$in: [resourceGroup]]], [_id: 1])

                    if (hasResource) {
                        List<String> existing = applicationIdAndSupportedResourceGroups.get(applicationId)

                        if (existing) {
                            existing.add(resourceGroup)
                        } else {
                            existing = [resourceGroup]
                        }
                        applicationIdAndSupportedResourceGroups.put(applicationId, existing)
                    }
                }
            }
            [applicationIds              : applicationIds, resourceGroups: resourceGroups, prodWarning: prodWarning,
             dryRunResources             : dryRunResources, dryRunNotification: dryRunNotification,
             resourceWithMultipleProfiles: resourceWithMultipleProfiles, searchedForMultipleProfiles: searchedForMultipleProfiles, epdPrograms: epdPrograms,
             pdfCheckInProgress: params.boolean("pdfCheckInProgress"), applicationIdAndSupportedResourceGroups: applicationIdAndSupportedResourceGroups, "isProd": isProd ]
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
    def indicatorBenchmarks() {
        [indicatorBenchmarks: indicatorBenchmarkService.getAllIndicatorBenchmarks()]
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def clearDryRunResources() {
        session?.removeAttribute("dryRunResources")
        flash.fadeSuccessAlert = message(code: "admin.import.dry_run_resources.cleared")
        redirect(action: "form")
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showInactiveResources() {
        List<Resource> resources = optimiResourceService.getOnlyInactiveResources()
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} inactive resources"
        chain(action: "form", model: [resources: resources, resourceType: "Inactive", deleteRedirect: "showInactiveResources"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showResourcesWithoutApp() {
        List<Resource> resources = optimiResourceService.getResourcesWithoutApplicationId(Boolean.TRUE)
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} without applicationId"
        chain(action: "form", model: [resources: resources, resourceType: "Without application", deleteRedirect: "showResourcesWithoutApp"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def resourceUsage() {
        String resourceId = params.resourceId
        String profileId = params.profileId
        def returnable = optimiResourceService.findResourceUsage(resourceId, profileId)
        Map<String, List<Dataset>> entitiesAndDatasets = returnable.get("entitiesAndDatasets")
        Map<String, List<String>> managersAndModifiers = returnable.get("managersAndModifiers")

        [entitiesAndDatasets: entitiesAndDatasets, managersAndModifiers: managersAndModifiers, hideNavi: true]
    }
   //15362 - check if the resource is used in any construction
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def resourceUsageInConstructions() {
        User user = userService.getCurrentUser(true)

        if (user?.internalUseRoles) {
            String resourceId = params.resourceId
            List<ConstructionGroup> constructionGroups = constructionService.getAllConstructionGroups()
            Map<String, List<Document>> resourcesInConstructions = [:]
            Map<String, Object> query = [classificationParamId: [$ne: "3"],resourceIds : [$in : [resourceId]]]
            // Getting large amount of data as documents instead converting to domain object is so much faster
            List<Document> constructions = constructionService.getConstructionsAsDocuments(query, [datasets: 1, classificationQuestionId: 1, classificationParamId: 1, nameEN: 1, constructionGroup: 1, importFile: 1])

            constructions.each { Document construction ->
                construction.datasets?.findAll({it.resourceId == resourceId}).toList().each {
                    String resourceIdAndProfileId = "${it.resourceId}.${it.profileId}"

                    List<Document> existing = resourcesInConstructions.get(resourceIdAndProfileId) ?: []

                    if (!existing.find({it._id.equals(construction._id)})) {
                        existing.add(construction)
                        resourcesInConstructions.put((resourceIdAndProfileId), existing)
                    }
                }
            }
            [resourcesInConstructions: resourcesInConstructions, constructionGroups: constructionGroups]
        } else {
            redirect controller: "main", action: "list"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showData() {
        Resource resource
        Map properties
        Set<String> impactsWithData = []
        String resourceUUID = params.resourceUUID
        String resourceId = params.resourceId
        String profileId = params.profileId

        if (resourceUUID) {
            resource = optimiResourceService.getResourceByDbId(resourceUUID)
        } else if (resourceId) {
            resource = optimiResourceService.getResourceWithGorm(resourceId, profileId, true)
        }

        if (resource) {
            List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)

            if (domainClassProperties) {
                properties = resource.properties.findAll({
                    domainClassProperties.contains(it.key)
                })

                if (properties) {
                    properties.put("allowVariableThickness (dynamic)", resource.allowVariableThickness)
                    resource.impacts?.each { stage, impacts ->
                        impactsWithData += impacts.keySet()
                    }
                    impactsWithData.sort()
                }
            }
        }

        [properties        : properties?.sort({ it.key.toString() }), hideNavi: true, resource: resource, impactsWithData: impactsWithData,
         showDataReferences: applicationService.getApplicationByApplicationId("LCA")?.showDataReferences?.find({ "resource".equals(it.page) })]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showConstruction() {
        Construction construction = null
        Map properties = null
        String constructionUUID = params.constructionUUID
        String constructionId = params.constructionId

        if (constructionUUID) {
            construction = constructionService.getConstruction(constructionUUID)
        } else {
            construction = constructionService.getConstructionWithConstructionId(constructionId)
        }

        if (construction) {
            List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Construction.class)

            if (domainClassProperties) {
                properties = construction.properties.findAll({
                    domainClassProperties.contains(it.key)
                })
            }
        }

        [properties: properties?.sort({ it.key.toString() }), hideNavi: true, construction: construction]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showNmdElement() {
        NmdElement nmdElement = null
        Map properties = null
        String elementUUID = params.elementUUID
        String elementId = params.elementId

        if (elementUUID) {
            nmdElement = nmdElementService.getNmdElement(elementUUID)
        } else if (elementId?.isInteger()) {
            nmdElement = nmdElementService.getNmdElement(elementId.toInteger())
        }

        if (nmdElement) {
            List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(NmdElement.class)

            if (domainClassProperties) {
                properties = nmdElement.properties.findAll({
                    domainClassProperties.contains(it.key)
                })
            }
        }

        [properties: properties?.sort({ it.key.toString() }), hideNavi: true, nmdElement: nmdElement]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def ungrouped() {
        def resources = Resource.collection.find([resourceGroup: null])?.toList()?.collect({ it as Resource })
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} resources with no resourceGroup"
        chain(action: "form", model: [resources: resources, resourceType: "Ungrouped", deleteRedirect: "ungrouped"])
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def duplicates() {
        List<Resource> resources = optimiResourceService.getDuplicateResources()
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} duplicate resources"
        chain(action: "form", model: [resources: resources, resourceType: "Duplicates", deleteRedirect: "duplicates"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteDuplicates() {
        List<Resource> resources = optimiResourceService.getDuplicateResources()
        Integer prog = resources?.size()
        Integer i = 0
        Map<String, List<Resource>> duplicatesByResourceAndProfileId = [:]

        String previousResourceIdandProfileId

        resources?.each { Resource r ->
            log.info("progress = ${i} / ${prog}")
            if (resources?.findAll({ Resource r2 -> r2.resourceId?.trim()?.equals(r.resourceId?.trim()) && r2.profileId?.trim()?.equals(r.profileId?.trim()) })?.size() > 1) {
                previousResourceIdandProfileId = "${r.resourceId}${r.profileId}"
                if (!duplicatesByResourceAndProfileId.get(previousResourceIdandProfileId)) {
                    duplicatesByResourceAndProfileId.put(previousResourceIdandProfileId, [r])
                } else {
                    List<Resource> foo = duplicatesByResourceAndProfileId.get(previousResourceIdandProfileId)
                    foo.add(r)
                    duplicatesByResourceAndProfileId.put(previousResourceIdandProfileId, foo)
                }
            }
            i++
        }

        duplicatesByResourceAndProfileId.each { String key, List<Resource> value ->
            Integer index = 0
            value.each { Resource resource ->
                if (index != 0) {
                    Boolean deleted = optimiResourceService.deleteResource(resource.id.toString())
                    if (deleted) {
                        log.info("Deleted duplicate resource: ${resource.resourceId} / ${resource.profileId}")
                    }
                }
                index++
            }
        }
        chain(action: "form", model: [resources: optimiResourceService.getDuplicateResources(), resourceType: "Duplicates", deleteRedirect: "duplicates"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteSelectedIndicatorBenchmarks() {
        List<String> ids = params.list('indicatorBenchmarksToRemove')

        if (ids) {
            ids.each { String id ->
                indicatorBenchmarkService.deleteIndicatorBenchmark(id)
            }
            flash.fadeSuccessAlert = "Deleted!"
        } else {
            flash.fadeErrorAlert = "Select something."
        }
        chain(action: "indicatorBenchmarks")
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showDuplicateStaticFullNames() {
        List<Resource> resources = optimiResourceService.getDuplicateResourcesByStaticFullName()
        resources = resources?.sort({ it.staticFullName })
        String deleteRedirect = 'showDublicateStaticFullNames'
        if (params.boolean('exportExcel')) {
            List<String> headers = ['ResourceId', 'ProfileId', 'Active', 'Virtual', 'Virtual parts',
                                    'Show data', 'Stage count', 'Stages', 'See instances', 'Default Profile',
                                    'Density', 'ApplicationId', 'Groups', 'Name', 'StaticFullname',
                                    'Unit', 'GWP100', 'ServiceLife', 'Mass', 'Energy',
                                    'Import file', 'Delete link']

            Workbook wb = optimiResourceService.generateExcelForResources(resources, headers)

            if (wb) {
                try {
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=duplicate_staticFullName_resources_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (ClientAbortException e) {
                    // client disconnected before bytes were able to be written.
                }
            } else {
                flash.fadeErrorAlert = "Could not generate Excel for duplicated static full name resources. Found ${resources?.size()} resources with duplicate staticFullName"
                chain(action: "form", model: [deleteRedirect: deleteRedirect])
            }
        } else {
            flash.fadeSuccessAlert = params.deleteMessage
            flash.errorAlert = params.errorMessage
            flash.successAlert = "Found ${resources?.size()} resources with duplicate staticFullName"
            chain(action: "form", model: [resources: resources, resourceType: "Duplicate staticFullName", deleteRedirect: deleteRedirect])
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showResourcesWithUnusualThickness() {
        List<Document> allResources = optimiResourceService.getAllResourcesAsDocuments(true, "LCA")
        List<Resource> failedResources = []
        List<Resource> verifiedResources = []
        Integer prog = allResources.size()
        Integer i = 1
        allResources?.each { Document r ->
            Boolean fails = Boolean.FALSE
            Boolean verifiedFails = Boolean.FALSE
            log.info("Progress: ${i} / ${prog}")
            if (r.defaultThickness_mm != null || r.defaultThickness_in != null) {
                ResourceType subType = resourceTypeService.getResourceTypeByResourceTypeAndSubType(r.resourceType, r.resourceSubType, Boolean.TRUE)
                if (subType) {
                    if (subType.maxThickness_mm != null) {
                        if (r.defaultThickness_mm != null && r.defaultThickness_mm > subType.maxThickness_mm) {
                            if (r.dataManuallyVerified) {
                                verifiedFails = Boolean.TRUE
                            } else {
                                fails = Boolean.TRUE
                            }

                        }

                        if (r.defaultThickness_in != null && r.defaultThickness_in * 25.4 > subType.maxThickness_mm) {
                            if (r.dataManuallyVerified) {
                                verifiedFails = Boolean.TRUE
                            } else {
                                fails = Boolean.TRUE
                            }
                        }
                    }

                    if (subType.minThickness_mm != null) {
                        if (r.defaultThickness_mm != null && r.defaultThickness_mm < subType.minThickness_mm) {
                            if (r.dataManuallyVerified) {
                                verifiedFails = Boolean.TRUE
                            } else {
                                fails = Boolean.TRUE
                            }
                        }

                        if (r.defaultThickness_in != null && r.defaultThickness_in * 25.4 < subType.minThickness_mm) {
                            if (r.dataManuallyVerified) {
                                verifiedFails = Boolean.TRUE
                            } else {
                                fails = Boolean.TRUE
                            }
                        }
                    }
                }
            }
            if (fails) {
                failedResources.add(r as Resource)
            }
            if (verifiedFails) {
                verifiedResources.add(r as Resource)
            }
            i++
        }
        failedResources = failedResources.sort({ it.staticFullName })
        verifiedResources = verifiedResources.sort({ it.staticFullName })
        flash.successAlert = "Found ${failedResources?.size()} (+${verifiedResources.size()} manually verified) resources with defaultThickness outside of subTypes min-max range"
        chain(action: "form", model: [unusualThicknessResources: failedResources, unusualThicknessVerifiedResources: verifiedResources, resourceType: "Unusual thickness", deleteRedirect: "showResourcesWithUnusualThickness"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def showWasteAndBenefitWithoutKilogramUnit() {
        List<Resource> failingWaste = []
        List<Resource> failingBenefit = []
        BasicDBObject query = new BasicDBObject()
        query.put("applicationId", "LCA")
        query.put("active", true)
        Resource.collection.distinct("wasteResourceId", query, String.class)?.toList()?.each { String resourceId ->
            Resource r = Resource.findByResourceIdAndActive(resourceId, true)

            if (r && !"kg".equalsIgnoreCase(r.unitForData)&&!"kwh".equalsIgnoreCase(r.unitForData)) {
                failingWaste.add(r)
            }
        }
        Resource.collection.distinct("environmentalBenefitsResourceId", query, String.class)?.toList()?.each { String resourceId ->
            Resource r = Resource.findByResourceIdAndActive(resourceId, true)

            if (r && !"kg".equalsIgnoreCase(r.unitForData)&&!"kwh".equalsIgnoreCase(r.unitForData)) {
                failingBenefit.add(r)
            }
        }
        failingWaste = failingWaste.sort({ it.staticFullName })
        failingBenefit = failingBenefit.sort({ it.staticFullName })
        log.info("failingWaste: ${failingWaste.size()}, failingBenefit: ${failingBenefit.size()}")
        flash.successAlert = "Found ${failingWaste?.size()} resources failing waste check, Found ${failingBenefit?.size()} resources failing benefit check"
        chain(action: "form", model: [failingWasteResources: failingWaste, failingBenefitResources: failingBenefit, resourceType: "Fails KG check", deleteRedirect: "showWasteAndBenefitWithoutKilogramUnit"])
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def checkBrokenDownloadLinks() {
        if (pdfCheckInProgress) {
            flash.errorAlert = "PDF Check currently running: ${pdfCheckProgress}"
            redirect(action: "form")
        } else {
            String epdProgram = params.downloadLinkEpdProgram
            pdfCheckInProgress = true
            Promise p = task {
                List<Document> resources

                if (!epdProgram||epdProgram == "all") {
                    resources = Resource.collection.find([active:true, downloadLink: [$ne: "-"]])?.toList()
                } else {
                    resources = Resource.collection.find([active:true, epdProgram: epdProgram, downloadLink: [$ne: "-"]])?.toList()
                }

                List<Document> noDownloadLinkResources = []
                Integer prog = resources.size()
                Integer i = 1

                String epdFilePathPrefix = configurationService.getConfigurationValue(null, "epdFilePathPrefix")
                PDFTextStripper pdfTextStripper = new PDFTextStripper()
                pdfTextStripper.setStartPage(1)
                pdfTextStripper.setEndPage(2)

                Map<Integer, String> websiteErrors = [:] // Website could not be opened from following resources:<br/>
                Integer websiteErrorCounter = 1
                Map<Integer, String> errors = [:]
                Integer errorsCounter = 1
                Map<Integer, String> warns = [:] // PDF found but could not read first page, might be false positive (no text on first page):<br/>
                Integer warnsCounter = 1

                resources.each { Document resource ->
                    String progress = "Progress: ${i} / ${prog}"
                    log.info("${progress}")
                    pdfCheckProgress = progress
                    if (resource.downloadLink?.startsWith("http")) {
                        try {
                            URL u = new URL(resource.downloadLink.toString())
                            HttpURLConnection huc = (HttpURLConnection) u.openConnection()
                            huc.setRequestMethod("HEAD")
                            // Some websites don't like programmatic access so pretend to be a browser
                            huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/74.0")
                            Integer code = huc.getResponseCode()
                            if (!200.equals(code)) {
                                websiteErrors.put(websiteErrorCounter, "${resource.resourceId} / ${resource.profileId}, Response ${code} from link: ${resource.downloadLink}")
                                websiteErrorCounter++
                            }
                            huc.disconnect()
                        } catch (Exception e) {
                            websiteErrors.put(websiteErrorCounter, "${resource.resourceId} / ${resource.profileId}, Exception ${e} from link ${resource.downloadLink}")
                            websiteErrorCounter++
                        }
                    } else {
                        String filePath = epdFilePathPrefix && resource.downloadLink ? "${epdFilePathPrefix}${resource.downloadLink}" : null

                        if (filePath) {
                            try {
                                File file = FileUtils.getFile(filePath)
                                PDDocument doc = PDDocument.load(file)
                                String text = pdfTextStripper.getText(doc)
                                doc.close()
                                if (text) {
                                    log.info("PDF was successfully read from Resource: ${resource.resourceId} / ${resource.profileId}, Link: ${resource.downloadLink}")
                                } else {
                                    warns.put(warnsCounter, "${resource.resourceId} / ${resource.profileId}, Link: ${resource.downloadLink}")
                                    warnsCounter++
                                }
                            } catch (Exception e) {
                                errors.put(errorsCounter, "${resource.resourceId} / ${resource.profileId}, Exception: ${e}")
                                errorsCounter++
                            }
                        } else if ("LCA".equals(resource.applicationId) && resource.epdProgram) {
                            noDownloadLinkResources.add(resource)
                        }
                    }
                    i++
                }

                if (!noDownloadLinkResources.isEmpty()) {
                    noDownloadLinkResources.each { Document resource ->
                        errors.put(errorsCounter, "${resource.resourceId} / ${resource.profileId}, No downloadLink found for EPD resource")
                        errorsCounter++

                    }
                }

                if (warns || errors || websiteErrors) {
                    File pdfErrorDump = new File("/var/www/static/dumps/downloadLinkCheckOutput.json")
                    pdfErrorDump.getParentFile().mkdirs()
                    pdfErrorDump.createNewFile()

                    Document d = new Document()
                    d.put("websiteErrors", websiteErrors)
                    d.put("errors", errors)
                    d.put("warns", warns)
                    pdfErrorDump.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(d).toPrettyString()))
                    pdfCheckInProgress = false
                } else {
                    pdfCheckInProgress = false
                }
            }
            p.onError { Throwable err ->
                log.error("PDF CHECK ERROR: ${err}")
                flashService.setErrorAlert("Error in checkBrokenDownloadLinks: ${err.message}", true)
            }
            redirect(action: "form", params: [pdfCheckInProgress: true])
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def downloadPDFCheck() {
        if (pdfCheckInProgress) {
            render(text: "PDF Check currently running: ${pdfCheckProgress}")
        } else {
            File dump = new File("/var/www/static/dumps/downloadLinkCheckOutput.json")

            if (dump.exists()) {
                try {
                    response.setHeader("Content-disposition", "attachment; filename=${dump.name}")
                    response.setHeader("Content-Length", "${dump.size()}")
                    InputStream contentStream = dump.newInputStream()
                    response.outputStream << contentStream
                    contentStream.close()
                    webRequest.renderView = false
                    dump.delete()
                } catch (ClientAbortException e) {
                    // client disconnected before bytes were able to be written.
                }
            } else {
                render(text: "No PDF check file found. Check in progress: ${pdfCheckInProgress}")
            }
        }
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    def noprofiles() {
        def resources = Resource.collection.find([profileId: null])?.toList()?.collect({it as Resource})
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} resources with no profileId"
        chain(action: "form", model: [resources: resources, resourceType: "No profiles", deleteRedirect: "noprofiles"])
    }
    /*@Secured(["ROLE_SYSTEM_ADMIN"])
    def nonUsedInactive() {
        List<Resource> resources = optimiResourceService.getEntityNonUsedInactiveResources()
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} non-used inactive resources"
        chain(action: "form", model: [resources   : resources, showDeleteAll: true,
                                      resourceType: "Non used inactive resources", deleteRedirect: "nonUsedInactive"])
    }*/
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteAllNonUsedInactive() {
        List<ObjectId> resourceIds = optimiResourceService.getEntityNonUsedInactiveResources()?.collect({
            it.id.toString()
        })

        if (resourceIds) {
            optimiResourceService.deleteResources(resourceIds)
            flash.fadeSuccessAlert = "All non used inactive resources has been deleted"
        }
        redirect(action: "form")
    }

    /*@Secured(["ROLE_SYSTEM_ADMIN"])
    def nonUsedActive() {
        List<Resource> resources = optimiResourceService.getEntityNonUsedActiveResources()
        flash.fadeSuccessAlert = params.deleteMessage
        flash.errorAlert = params.errorMessage
        flash.successAlert = "Found ${resources?.size()} non-used active resources"
        chain(action: "form", model: [resources: resources, resourceType: "non used active resources", deleteRedirect: "nonUsedActive"])
    }*/
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def upload() {
        def file = request.getFile("file")

        if (!file || file?.empty) {
            flash.fadeErrorAlert = g.message(code: "import.file.required")
            redirect action: "form"
        } else {
            try {
                optimiResourceService.convertCsvFileToResource(file)
                flash.fadeSuccessAlert = g.message(code: "import.import_ok")
            } catch (Exception e) {
                flash.errorAlert = e.getMessage()
            }
            redirect action: "form"
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteResource() {
        def deleteOk = optimiResourceService.deleteResource(params.id)
        String returnMessage
        String errorMessage

        if (deleteOk) {
            returnMessage = message(code: "admin.import.resource_delete_ok")
        } else {
            errorMessage = "Deletion already done. Don't push the \"Delete\" multiple times"
        }
        def deleteRedirect = params.deleteRedirect

        if (deleteRedirect) {
            redirect action: deleteRedirect, params: [deleteMessage: returnMessage, errorMessage: errorMessage]
        } else {
            if (deleteOk) {
                String success = message(code: "admin.import.resource_delete_ok")
                flash.successAlert = success
            } else {
                String error = "Deletion already done. Don't push the \"Delete\" multiple times"
                flash.errorAlert = error
            }
            redirect action: "form"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def activateResource() {
        def ok = optimiResourceService.activateResource(params.id)
        if (ok) {
            flash.successAlert = "Resource activated"
        } else {
            flash.errorAlert = "Something went wrong"
        }
        redirect action: "form"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deactivateResource() {
        def ok = optimiResourceService.deactivateResourceById(params.id)
        if (ok) {
            flash.successAlert = "Resource deactivated"
        } else {
            flash.errorAlert = "Something went wrong"
        }
        redirect action: "form"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteResources() {
        List<String> ids
        boolean deleteOk

        if (params.id) {
            ids = params.list("id")
            deleteOk = optimiResourceService.deleteResources(ids)
        }
        String returnMessage
        String errorMessage

        if (deleteOk) {
            returnMessage = message(code: "admin.import.resource_delete_ok")
        } else {
            errorMessage = "Deletion already done. Don't push the \"Delete\" multiple times"
        }
        redirect action: "duplicates", params: [deleteMessage: returnMessage, errorMessage: errorMessage]
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def uploadExcel() {
        log.info("PART 1: we here")
        if (request instanceof MultipartHttpServletRequest) {
            session?.removeAttribute("dryRunResources")
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "form"
            } else {
                try {
                    Boolean dryRun = params.dryRun ? Boolean.TRUE : Boolean.FALSE
                    Boolean isUploadingNmd = params.boolean('isUploadingNmd')
                    def mapWithInfo = optimiResourceService.importExcelWorkbook(excelFile, dryRun, null, isUploadingNmd)
                    def file = mapWithInfo.get("file")
                    def sheets = mapWithInfo.get("sheets")
                    def okResources = mapWithInfo.get("okResources")
                    def rejectedResources = mapWithInfo.get("rejectedResources")
                    def duplicateDefaults = mapWithInfo.get("duplicateDefaults")
                    def duplicateResourceAndProfiles = mapWithInfo.get("duplicateResourceAndProfiles")
                    def rejectedDefaultProfiles = mapWithInfo.get("rejectedDefaultProfiles")
                    def errorResources = mapWithInfo.get("errorResources")
                    def deprecatedFieldsError = mapWithInfo.get("deprecatedFieldsError")
                    def importWarnings = mapWithInfo.get("importWarnings")
                    def errorMessage
                    String notActiveResMessage = mapWithInfo.get("notActiveResMessage")
                    def warnMessage = notActiveResMessage ? notActiveResMessage + "<br/><br/>": ""


                    if (okResources) {
                        flash.successAlert = "Imported ${file}, ${sheets ? sheets.size() : 0} sheets, successfully imported ${okResources} resources, rejected ${rejectedResources} resources."
                        session?.setAttribute("importIncomplete", true)
                    } else {
                        errorMessage = "Import rejected due to error(s) in the file"
                    }

                    if (mapWithInfo.get("errorMessage")) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + mapWithInfo.get("errorMessage") : mapWithInfo.get("errorMessage")
                    }

                    if (errorResources) {
                        errorResources.each { Resource resource ->
                            errorMessage = errorMessage ? errorMessage + "<br />" + renderErrors(bean: resource) : renderErrors(bean: resource)
                        }
                    }

                    if (rejectedDefaultProfiles) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + rejectedDefaultProfiles : rejectedDefaultProfiles
                    }

                    if (errorMessage) {
                        flash.errorAlert = errorMessage
                    }

                    if (importWarnings) {
                        warnMessage += importWarnings
                    }

                    if (duplicateDefaults) {
                        warnMessage += "Duplicate defaultProfiles: "

                        duplicateDefaults.each { Resource r ->
                            warnMessage = warnMessage + "resourceId and profileId: ${r.resourceId} ${r.profileId}, "
                        }
                    }

                    if (duplicateResourceAndProfiles) {
                        warnMessage = warnMessage + "Duplicate resource and profile ids: "

                        duplicateResourceAndProfiles.each { Resource r ->
                            warnMessage = warnMessage + "${r.resourceId} / ${r.profileId}<br />"
                        }
                    }

                    if (deprecatedFieldsError) {
                        warnMessage = warnMessage ? warnMessage + "<br />" + deprecatedFieldsError : deprecatedFieldsError
                    }

                    if (warnMessage) {
                        flash.warningAlert = warnMessage
                    }
                    def model = [:]

                    if (dryRun) {
                        List<Resource> resources = mapWithInfo.get("resources")

                        if (resources) {
                            session?.setAttribute("dryRunResources", resources)
                        }
                    }
                    chain(action: "form", model: model)
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect(action: "form")
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "form"
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def entityProfileCheck() {
        def noProfileDatasets
        def entities = Entity.list()
        def datasets

        entities?.each { Entity e ->
            datasets = e.datasets

            if (datasets) {
                noProfileDatasets = datasets.findAll({ it.resourceId && !it.profileId })

                if (noProfileDatasets) {
                    loggerUtil.info(log, "Found ${noProfileDatasets.size()} datasets without profileId for entity ${e.name} (deleted: ${e.deleted ? 'true' : 'false'}")
                }
            }
        }
        redirect action: "form"
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def applicationResources() {
        String applicationId = params.applicationId
        String resourceGroup = params.resourceGroup
        List<Document> applicationResources = []

        if (applicationId && resourceGroup) {
            BasicDBObject query = new BasicDBObject()
            query.put("applicationId", applicationId)
            query.put("resourceGroup", resourceGroup)
            applicationResources = optimiResourceService.getResourcesAsDocumentsByQuery(query)
        }
        String templateAsString = g.render(template: "/import/resourceList", model: [resources: applicationResources, resourceTableOnly: true]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resourcesById() {
        List<Document> resources = []
        String id = params.resourceId

        if (id) {
            if (ObjectId.isValid(id)) {
                Resource.collection.find(["_id": DomainObjectUtil.stringToObjectId(id)])?.toList()?.each { Document d ->
                    d.localizedName = optimiResourceService.getLocalizedNameFromDocument(d)
                    resources.add(d)
                }
            } else {
                Resource.collection.find(["resourceId": [$regex: ".*\\Q${id}\\E.*", $options: "i"]])?.toList()?.each { Document d ->
                    d.localizedName = optimiResourceService.getLocalizedNameFromDocument(d)
                    resources.add(d)
                }
            }
        }
        User user = userService.getCurrentUser()
        Boolean allowEdit = userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)
        String templateAsString = g.render(template: "/import/resourceList", model: [resources: resources?.sort({it.resourceId}), resourceTableOnly: true, allowEdit: allowEdit]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def applicationFilters() {
        List<Map> filterRulesByGroup = []

        if (params.applicationId) {
            filterRulesByGroup = applicationService.getApplicationFilterRules(params.applicationId)
        }
        render([rules: filterRulesByGroup, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def setMultipleProfiles() {
        List<String> resourceIds = Resource.collection.distinct("resourceId", String.class)?.toList()
        Integer i = 1
        Integer amountOfResourceIds = resourceIds.size()
        Integer currentAmountOfResources = Resource.count()

        resourceIds.each { String r ->
            log.info("Setting multipleProfiles: ${i} / ${amountOfResourceIds}")

            if (Resource.collection.count(["resourceId": r, "active": true]) > 1) {
                Resource.findAllByResourceIdAndActiveAndMultipleProfilesInList(r, true, [null, false])?.each { Resource resource ->
                    resource.multipleProfiles = Boolean.TRUE
                    resource.searchString = optimiResourceService.generateSearchString(resource)
                    resource.merge(flush: true)
                }
            } else {
                Resource.findAllByResourceIdAndActiveAndMultipleProfiles(r, true, true)?.each { Resource resource ->
                    resource.multipleProfiles = Boolean.FALSE
                    resource.searchString = optimiResourceService.generateSearchString(resource)
                    resource.merge(flush: true)
                }
            }
            i++
        }
        logSetterMethods(currentAmountOfResources, "setMultipleProfiles")
        render([output: i, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def setIsoCountryCodes() {
        List<String> skipResourceGroups = ["world","US-states","CA-provinces","otherAreas","constructionAreas"]
        Integer i = 1
        List<String> resourceIds = Resource.collection.distinct("resourceId", String.class)?.toList()
        Integer amountOfResourceIds = resourceIds.size()
        Integer currentAmountOfResources = Resource.count()

        resourceIds.each { String r ->
            log.info("setting isoCountryCodes ${i} / ${amountOfResourceIds}")
            Resource.findAllByResourceIdAndActive(r, true)?.each { Resource resource ->
                if (resource && (!resource.resourceGroup || !CollectionUtils.containsAny(resource.resourceGroup, skipResourceGroups))) {
                    Map<String, String> isoCodesByAreas = optimiResourceService.getresourceIsoCodes(resource.areas, resource.additionalArea)

                    if (isoCodesByAreas && !isoCodesByAreas.equals(resource.isoCodesByAreas)) {
                        resource.isoCodesByAreas = isoCodesByAreas

                        if (!resource.merge(flush: true)) {
                            log.error("Setting isoCountryCodes: Resource ${resource.resourceId} / ${resource.profileId} did not validate: ${resource.errors?.allErrors?.toString()}")
                            flashService.setErrorAlert("Setting isoCountryCodes: Resource ${resource.resourceId} / ${resource.profileId} did not validate: ${resource.errors?.allErrors?.toString()}", true)
                        }
                    }
                }
            }
            i++
        }
        logSetterMethods(currentAmountOfResources, "setIsoCountryCodes")
        render([output: i, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def setResourceTypes() {
        Integer i = 1
        Integer currentAmountOfResources = Resource.count()
        List<String> resourceIds = Resource.collection.distinct("resourceId", String.class)?.toList()
        Integer amountOfResourceIds = resourceIds.size()

        resourceIds.each { String r ->
            log.info("setting isoCountryCodes ${i} / ${amountOfResourceIds}")
            Resource.findAllByResourceIdAndActive(r, true)?.each { Resource resource ->
                if (resource && resource.resourceSubType) {
                    String resourceType = resourceTypeService.getResourceTypeBySubType(resource.resourceSubType)

                    if (resourceType) {
                        if (!resourceType.equals(resource.resourceType)) {
                            resource.resourceType = resourceType
                            if (!resource.merge(flush: true)) {
                                log.error("Setting resourceTypes: Resource ${resource.resourceId} / ${resource.profileId} did not validate: ${resource.errors?.allErrors?.toString()}")
                                flashService.setErrorAlert("Setting resourceTypes: Resource ${resource.resourceId} / ${resource.profileId} did not validate: ${resource.errors?.allErrors?.toString()}", true)
                            }
                        }
                    } else {
                        log.warn("Resource: ${resource.resourceId} / ${resource.profileId}, had a subType: ${resource.resourceSubType}, but did not find a subType for it.")
                        flashService.setWarningAlert("Resource: ${resource.resourceId} / ${resource.profileId}, had a subType: ${resource.resourceSubType}, but did not find a subType for it.", true)
                    }
                }
            }
            i++
        }
        logSetterMethods(currentAmountOfResources, "setResourceTypes")
        render([output: i, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    private void logSetterMethods(Integer amountBefore, String buttonPressed) {
        Integer allResources = Resource.count()

        if (allResources && amountBefore && buttonPressed) {
            if (allResources != amountBefore) {
                log.error("MagicButtons might have generated duplicate resources: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}")
                flashService.setErrorAlert("MagicButtons might have generated duplicate resources: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}", true)
            } else {
                log.info("Successfull magic button press: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}")
                flashService.setFadeSuccessAlert("Successfull magic button press: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}")
            }
        } else {
            log.error("Something went wrong while trying to log magicButtons: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}")
            flashService.setErrorAlert("Something went wrong while trying to log magicButtons: Button pressed = ${buttonPressed}, Amount of resources before press = ${amountBefore}, Amount of resources now = ${allResources}", true)
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def runApplicationFilterRule() {
        String applicationId = params.applicationId
        Boolean allRules = params.boolean('allRules')
        boolean includeIds = params.boolean('includeIds')
        List<String> ruleId = params.list('ruleId')
        List<Resource> applicationResources
        String templatesAsString = ""

        if (applicationId) {
            List<Resource> dryRunResources = getDryRunResources()

            if (dryRunResources) {
                applicationResources = dryRunResources.findAll({ applicationId.equals(it.applicationId) })
            } else {
                applicationResources = optimiResourceService.getAllResources(Boolean.FALSE, includeIds, applicationId)
            }

            if (applicationResources) {
                if (allRules) {
                    Application application = applicationService.getApplicationByApplicationId(applicationId)
                    ruleId = application?.filterRules?.collect({ it.ruleId })
                }

                if (ruleId) {
                    int size = ruleId.size()
                    int i = 1

                    for (String id in ruleId) {
                        log.info("FilterRules: Running rule ${id}, progress: ${i} / ${size}")
                        Map returnable = optimiResourceService.runFilterRule(applicationResources, applicationId, id, allRules)

                        if (returnable?.get("error")) {
                            templatesAsString = returnable.get("error")
                            break
                        } else {
                            templatesAsString = "${templatesAsString}${g.render(template: "resourceList", model: [feedback: returnable])}"
                            i++
                        }
                    }
                }
            }
        }
        render([output: templatesAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def runApplicationResourcePurposeOld() {
        Boolean removePurposes = params.boolean("removePurposes")
        List<String> resourcePurposes = params.list('resourcePurpose')
        String templatesAsString = ""

        if (resourcePurposes) {
            List<ResourcePurpose> resourcePurposesList = applicationService.getApplicationByApplicationId("LCA")?.resourcePurposes

            resourcePurposes.each { String resourcePurpose ->
                ResourcePurpose rp = resourcePurposesList?.find({it.attribute == resourcePurpose})

                if (rp) {
                    List<Resource> filteredResources

                    if (removePurposes) {
                        filteredResources = optimiResourceService.getResourcesWithResourcePurpose(rp)
                    } else {
                        List<Resource> resourcePurposeResources = optimiResourceService.getResourcesByResourcePurpose(rp)

                        if (resourcePurposeResources) {
                            filteredResources = optimiResourceService.runAdvancedResourceFilterCriteria(resourcePurposeResources, rp.validityCondititions)
                        }
                    }

                    if (filteredResources) {
                        filteredResources.each {
                            if (!it.enabledPurposes) {
                                it.enabledPurposes = []
                            }
                            if (it.disableAutomaticPurposes) {
                                it.automaticallyEnabledPurposes = []
                                it.enabledPurposes = []
                            } else {
                                if (!it.automaticallyEnabledPurposes) {
                                    it.automaticallyEnabledPurposes = []
                                }

                                if (removePurposes) {
                                    it.automaticallyEnabledPurposes.removeIf({it.equals(rp.attribute)})
                                    it.enabledPurposes.removeIf({it.equals(rp.attribute)})
                                } else {
                                    if (!it.automaticallyEnabledPurposes.contains(rp.attribute)) {
                                        it.automaticallyEnabledPurposes.add(rp.attribute)
                                    }
                                    it.enabledPurposes.addAll(it.automaticallyEnabledPurposes)
                                }
                            }
                            if (it.manuallyEnabledPurposes) {
                                it.enabledPurposes.addAll(it.manuallyEnabledPurposes)
                            }
                            it.enabledPurposes = it.enabledPurposes.unique()
                            optimiResourceService.deleteResourceFromCache(it)
                            it.merge(flush: true, failOnError: true)
                        }
                        templatesAsString = "${templatesAsString}${g.render(template: "resourcePurposes", model: [resources: filteredResources, purpose: resourcePurpose, removePurposes: removePurposes])}"
                    }
                }
            }
        }
        render([output: templatesAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def runApplicationResourcePurpose() {
        Boolean removePurposes = params.boolean("removePurposes")
        List<String> resourcePurposes = params.list('resourcePurpose')
        String templatesAsString = ""

        if (resourcePurposes) {

            Map<String, List<Document>> filteredResourcesPerResourcePurposeAttribute = optimiResourceService.processApplicationResourcePurpose(resourcePurposes, removePurposes)

            filteredResourcesPerResourcePurposeAttribute.each { String resourcePurposeAttribute, List<Document> filteredResources ->
                if (filteredResources) {
                    templatesAsString = "${templatesAsString}${g.render(template: "resourcePurposes", model: [resources: filteredResources, purpose: resourcePurposeAttribute, removePurposes: removePurposes])}"
                }
            }
        }
        render([output: templatesAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllResources() {
        render template: "resourceList", model: [resources: optimiResourceService.getAllResources(Boolean.TRUE)]
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def logResourcesAndDatasets() {
        def allResources = optimiResourceService.getAllResources(Boolean.TRUE)
        loggerUtil.info(log, "ALL FOUND RESOURCES: " + allResources?.size())

        def resourceGroups = [
                "districtHeating",
                "districtCooling",
                "unspecifiedResource",
                "heatingFuels",
                "heatingFuelsKWh",
                "trafficFuels",
                "meat",
                "fishAndCrustacean",
                "dairyProducts",
                "cheese",
                "eggs",
                "processedFood",
                "convenienceFood",
                "vegetables",
                "fruits",
                "breadAndPastries",
                "floursAndFlakes",
                "ricePastaAndSimilar",
                "otherDryFoods",
                "fatAndOil",
                "softDrinks",
                "alcoholicBeverages",
                "buildingTypes",
                "stagesOfConstruction",
                "typesOfOrganizations",
                "europe",
                "finlandProvince",
                "finlandCity",
                "WW",
                "world",
                "swedenCity",
                "norwayCity",
                "denmarkCity",
                "IcelandCity",
                "russiaCity",
                "estoniaCity",
                "latviaCity",
                "lithuaniaCity",
                "hungaryCity",
                "intensityOfUseFood",
                "intensityOfUse",
                "registrationMotive",
                "waste",
                "constructionWaste",
                "water",
                "buildingTechnologyAverages",
                "buildingTechnology",
                "OTHER",
                "SITE_G",
                "defaultcost",
                "operatingCostB1average",
                "operatingCostB2average",
                "operatingCostB3B4average",
                "electricity",
                "producedElectricity",
                "refrigerants",
                "transportGoods",
                "transportPassengerKm",
                "transportPassengerPKm",
                "LCCA0building",
                "LCCA1A5building",
                "operatingCostB1building",
                "operatingCostB2building",
                "operatingCostB3B4building",
                "buildingProcurement",
                "defaultValue"
        ]
        def resourcesWithResourceGroups = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(resourceGroups, null)
        loggerUtil.info(log, "RESOURCES WITH RESOURCE GROUPS: " + resourcesWithResourceGroups?.size())

        if (allResources && resourcesWithResourceGroups) {
            allResources.removeAll(resourcesWithResourceGroups)
            loggerUtil.info(log, "RESOURCES WITHOUT RESOURCE GROUPS: " + allResources?.collect({ it.resourceId }))
        }

        // Dataset reporting
        def entities = Entity.list()
        def datasets
        def datasetsWithResourceIds = []

        entities?.each { Entity e ->
            datasets = e.datasets

            if (datasets) {
                datasets.each { Dataset d ->
                    if (d.resourceId) {
                        datasetsWithResourceIds.add(d)
                    }
                }
            }
        }
        loggerUtil.info(log, "DATASETS WITH RESOURCES: " + datasetsWithResourceIds?.size())

        Resource resource
        def datasetsInResourceGroups = []

        if (datasetsWithResourceIds) {
            ResourceCache resourceCache = ResourceCache.init(datasetsWithResourceIds)
            for (Dataset d: datasetsWithResourceIds)
            {
                resource = resourceCache.getResource(d)

                if (resource && resource.resourceGroup) {
                    for (String group in resource.resourceGroup) {
                        if (resourceGroups.contains(group)) {
                            datasetsInResourceGroups.add(d)
                            break
                        }
                    }
                }
            }
        }
        loggerUtil.info(log, "DATASETS WITH RESOURCES IN RESOURCE GROUPS: " + datasetsInResourceGroups?.size())
        redirect action: "form"
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def resourceStatusList() {
        List<Resource> resources
        Boolean pcrShow = Boolean.FALSE

        if (params.enabledPurposes) {
            resources = optimiResourceService.getResourcesByAttributeNameAndValue("enabledPurposes", params.enabledPurposes)
        } else if (params.importFile) {
            resources = optimiResourceService.getResourcesByAttributeNameAndValue("importFile", params.importFile)
        } else if (params.resourceGroup) {
            resources = optimiResourceService.getResourcesByResourceGroup(params.list("resourceGroup"), params.applicationId, params.boolean("noConstructions"))
        } else if (params.skipResourceGroup) {
            resources = optimiResourceService.getResourcesBySkipResourceGroup(params.list("skipResourceGroup"), params.applicationId, params.boolean("noConstructions"))
        } else if (params.environmentDataSourceType) {
            resources = optimiResourceService.getResourcesByAttributeNameAndValue("environmentDataSourceType", params.environmentDataSourceType)
        }  else if (params.inactive) {
            resources = optimiResourceService.getResourcesByAttributeNameAndValue("active", false)
        } else if (params.area) {
            if ("unlinked".equals(params.area)) {
                resources = Resource.collection.find([applicationId: "LCA", active: true, $or: [[areas: [$exists: false]], [areas: null]], privateDataset: [$ne: true]])?.collect({ it as Resource })
            } else {
                resources = Resource.collection.find([applicationId: "LCA", active: true, areas: params.area, privateDataset: [$ne: true]])?.collect({ it as Resource })
            }
        } else if (params.upstreamDB) {
            if (("unlinked").equals(params.upstreamDB)) {
                resources = optimiResourceService.getResourcesWithAttributeNotExists("upstreamDB")
            } else {
                resources = optimiResourceService.getResourcesByAttributeNameAndValue("upstreamDB", params.upstreamDB)
            }
        } else if (params.epdProgram) {
            if (params.applicationId) {
                if ("unlinked".equals(params.applicationId) && "EPD NOT DEFINED".equals(params.epdProgram)) {
                    resources = Resource.collection.find([applicationId: null, epdProgram: null, active: true, construction: [$ne: true], privateDataset: [$ne: true]])?.collect({ it as Resource })
                } else if ("unlinked".equals(params.applicationId)) {
                    resources = Resource.collection.find([applicationId: null, epdProgram: params.epdProgram, active: true, construction: [$ne: true], privateDataset: [$ne: true]])?.collect({ it as Resource })
                } else if ("EPD NOT DEFINED".equals(params.epdProgram)) {
                    resources = Resource.collection.find([applicationId: params.applicationId, epdProgram: null, active: true, construction: [$ne: true], privateDataset: [$ne: true]])?.collect({ it as Resource })
                } else {
                    resources = Resource.collection.find([applicationId: params.applicationId, epdProgram: params.epdProgram, active: true, construction: [$ne: true], privateDataset: [$ne: true]])?.collect({ it as Resource })
                }
            } else {
                if (params.boolean("noConstructions")) {
                    resources = Resource.collection.find([epdProgram: [$in: params.list("epdProgram")], active: true, constructionId: [$exists:false]])?.collect({ it as Resource })
                } else {
                    resources = Resource.collection.find([epdProgram: [$in: params.list("epdProgram")], active: true])?.collect({ it as Resource })
                }
            }
        } else if (params.applicationId && params.pcr) {
            resources = Resource.findAllByApplicationIdAndPcrAndActive(params.applicationId.toString(), params.pcr.toString(), true)
            pcrShow = Boolean.TRUE
        } else if (params.applicationId) {
            if ("unlinked".equals(params.applicationId)) {
                resources = optimiResourceService.getResourcesWithAttributeNotExists("applicationId")
            } else {
                resources = optimiResourceService.getResourcesByAttributeNameAndValue("applicationId", params.applicationId)
            }
        } else if (params.dataProperties) {
            def dataProperties = params.list("dataProperties")

            if (dataProperties instanceof List && dataProperties.size() > 1) {
                resources = optimiResourceService.getResourcesByAttributeNameAndListValue("dataProperties", dataProperties)
            } else {
                if ("unlinked".equals(params.dataProperties)) {
                    resources = optimiResourceService.getResourcesWithAttributeNotExists("dataProperties")
                } else {
                    resources = optimiResourceService.getResourcesByAttributeNameAndValue("dataProperties", params.dataProperties)
                }
            }
        }
        [resources: resources?.sort({ it.resourceId }), pcrShow: pcrShow]
    }
    @Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
    def resourceStatusReport() {
        long now = System.currentTimeMillis()
        List<String> applicationIds = applicationService.getAllApplicationApplicationIds()
        Map<String, List<Document>> uniqueEPDForLCA = [:]
        Map<String, List<String>> uniqueEPDNumberForLCA = [:]
        Map<String, Integer> resourcesPerApplication = [:]
        Map<String, Map> resourcesPerResourceGroupAndUnlinked = [:]
        List<String> allResourceGroups = optimiResourceService.getAllResourceGroups()
        log.info("Status report progress: 1 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        applicationIds?.each { String applicationId ->
            Integer resourcesCount = Resource.collection.count([applicationId: applicationId, active: true])?.intValue()

            if (resourcesCount) {
                resourcesPerApplication.put(applicationId, resourcesCount)

                if ("LCA".equalsIgnoreCase(applicationId)) {
                    List<Document> resources = Resource.collection.find([applicationId: applicationId, active: true, epdProgram: [$exists: true], construction: [$ne: true], privateDataset: [$ne: true]],[applicationId: 1, epdProgram: 1, epdNumber:1])?.toList()
                    resources?.each { Document r ->

                        List<Document> byEPD = uniqueEPDForLCA.get((String) r.epdProgram)

                        if (byEPD) {
                            byEPD.add(r)
                        } else {
                            byEPD = [r]
                        }
                        uniqueEPDForLCA.put((String) r.epdProgram, byEPD)
                    }
                    log.info("Status report progress: 2 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
                    uniqueEPDForLCA?.each { String epdProgram, List<Document> resourcesByEPD ->
                        List<String> epdNumbers = resourcesByEPD.collect({ (String) it.epdNumber })

                        if (epdNumbers && !epdNumbers.isEmpty()) {
                            uniqueEPDNumberForLCA.put(epdProgram, epdNumbers.unique())
                        } else {
                            uniqueEPDNumberForLCA.put(epdProgram, [])
                        }
                    }
                    log.info("Status report progress: 3 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
                }

                allResourceGroups?.each { String resourceGroup ->
                    Integer resourcesByGroup = Resource.collection.count([applicationId: applicationId, active: true, resourceGroup: [$in: [resourceGroup]]])?.intValue()

                    if (resourcesByGroup != null) {
                        Map existing = resourcesPerResourceGroupAndUnlinked.get(applicationId)

                        if (existing) {
                            existing.put(resourceGroup, resourcesByGroup)
                        } else {
                            existing = [(resourceGroup): resourcesByGroup]
                        }
                        resourcesPerResourceGroupAndUnlinked.put(applicationId, existing)
                    }
                }
            }
        }
        allResourceGroups?.each { String resourceGroup ->
            Integer unlinked = Resource.collection.count([active: true, resourceGroup:[$in: [resourceGroup]], $or: [[applicationId: [$exists: false]], [applicationId: null]]])?.intValue()

            if (unlinked) {
                Map existing = resourcesPerResourceGroupAndUnlinked.get("unlinked")

                if (existing) {
                    existing.put(resourceGroup, unlinked)
                } else {
                    existing = [(resourceGroup): unlinked]
                }
                resourcesPerResourceGroupAndUnlinked.put("unlinked", existing)
            }
        }

        resourcesPerApplication.put("unlinked", Resource.collection.count([active: true, $or: [[applicationId: [$exists: false]], [applicationId: null]]])?.intValue() ?: 0)
        log.info("Status report progress: 4 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<String, Integer> countOfResourcesByCountry = optimiResourceService.getResourceCountsByArea()
        log.info("Status report progress: 5 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<List<String>, List<Document>> resourcesWithDataProperties = optimiResourceService.getResourceDocumentsByDataProperties()
        log.info("Status report progress: 6 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<List<String>, List<Document>> resourcesWithEnabledPurposes = optimiResourceService.getResourceDocumentsByEnabledPurposes()
        log.info("Status report progress: 7 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<String, Integer> countOfResourcesByUpstreamDB = optimiResourceService.getResourceCountsByUpstreamDBs()
        log.info("Status report progress: 8 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<String, Integer> databaseKPIs = optimiResourceService.getDatabaseKPIs()
        log.info("Status report progress: 9 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<String, Integer> databasePCRs = optimiResourceService.getDatabasePCRs()
        log.info("Status report progress: 10 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")
        Map<String, Integer> importFilesResourceAmounts = optimiResourceService.getImportFilesResourceAmounts()
        log.info("Status report progress: 11 / 11 time elapsed: ${System.currentTimeMillis() - now}ms")

        [resourcesPerApplication             : resourcesPerApplication, countOfResourcesByCountry: countOfResourcesByCountry, databaseKPIs: databaseKPIs,
         resourcesPerResourceGroupAndUnlinked: resourcesPerResourceGroupAndUnlinked, resourcesWithDataProperties: resourcesWithDataProperties,
         countOfResourcesByUpstreamDB        : countOfResourcesByUpstreamDB, uniqueEPDForLCA: uniqueEPDForLCA?.sort({ -it.value?.size() }), uniqueEPDNumberForLCA: uniqueEPDNumberForLCA,
         databasePCRs                        : databasePCRs, importFilesResourceAmounts: importFilesResourceAmounts, resourcesWithEnabledPurposes: resourcesWithEnabledPurposes]
    }

    private List<Resource> getDryRunResources() {
        return session?.getAttribute("dryRunResources")
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def uploadIndicatorBenchmarks() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "indicatorBenchmarks"
            } else {
                try {
                    def mapWithInfo = indicatorBenchmarkService.importIndicatorBenchmarks(excelFile)

                    if (mapWithInfo?.get("error")) {
                        flash.errorAlert = "${mapWithInfo?.get("error")}"
                    }
                    if (mapWithInfo?.get("success")) {
                        flash.fadeSuccessAlert = "${mapWithInfo?.get("success")}"
                    }
                    chain(action: "indicatorBenchmarks")
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                    redirect(action: "indicatorBenchmarks")
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "indicatorBenchmarks"
        }
    }
    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeIndicatorBenchmark() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = indicatorBenchmarkService.deleteIndicatorBenchmark(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "IndicatorBenchmark removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing IndicatorBenchmark failed. See logs"
        }
        redirect action: "indicatorBenchmarks"
    }

    @Secured(["ROLE_SUPER_USER"])
    def runFilterRules() {
        BasicDBObject filter = new BasicDBObject("filterRules",[$exists: true])
        List<String> applicationIds = Application.collection.distinct("applicationId", filter, String.class)?.toList()?.sort()
        List<Map> lcaRules = applicationService.getApplicationFilterRules("LCA")
        List<Map> lcaResourcePurposes = applicationService.getApplicationResourcePurposes("LCA")

        List<Resource> dryRunResources = getDryRunResources()
        String dryRunNotification

        if (dryRunResources) {
            dryRunNotification = message(code: "admin.import.dry_run_notification")
        }

        [applicationIds: applicationIds, lcaRules: lcaRules, lcaResourcePurposes: lcaResourcePurposes, dryRunNotification: dryRunNotification]

    }

    @Secured(["ROLE_DATA_MGR"])
    def compareResources() {
        Indicator internalLcaTool = indicatorService.getIndicatorByIndicatorId("internalToolLCA", true)
        List<ResultCategory> resultCategories = internalLcaTool?.getResolveResultCategories(null) // TODO what allow all, still used?
        List<CalculationRule> calculationRules = internalLcaTool?.getResolveCalculationRules(null, true)
        List<ResourceType> resourceTypes = resourceTypeService.getActiveResourceTypes()

        BasicDBObject filter = new BasicDBObject("active", true)
        List<String> dataProperties = Resource.collection.distinct("dataProperties", filter, String.class)?.toList()?.sort({it.toLowerCase()})

        File[] files = new File("/var/www/static/resourceCompareFilter")?.listFiles()
        String currentFilterName = files ? files.first().name : null
        [resultCategories: resultCategories, calculationRules: calculationRules, resourceTypes: resourceTypes, dataProperties: dataProperties,
         currentFilterName: currentFilterName]

    }

    @Secured(["ROLE_DATA_MGR"])
    def runCompareResources() {

        List<Dataset> datasets = []
        List<CalculationResult> calculationResults = []
        String baseCategoryId = params.baseCategoryId
        String comparisonCategoryId = params.comparisonCategoryId
        String calculationRuleId = params.calculationRuleId
        String resourceType = params.resourceType
        String resourceSubType = params.resourceSubType
        String dataProperty = params.dataProperty

        String fix = params.fixedBasedCategory
        Double fixedBasedCategory = fix?.isNumber() ? fix.toDouble() : null
        fix = params.fixedComparisonCategory
        Double fixedComparisonCategory = fix?.isNumber() ? fix.toDouble() : null

        if ((baseCategoryId || fixedBasedCategory!=null)  && (comparisonCategoryId || fixedComparisonCategory!=null) && calculationRuleId) {
            log.info("baseCategoryId: ${baseCategoryId}, comparisonCategoryId: ${comparisonCategoryId}, " +
                    "calculationRuleId: ${calculationRuleId}, resourceType: ${resourceType}," +
                    "dataProperty: ${dataProperty}, fixedBasedCategory: ${fixedBasedCategory}, fixedComparisonCategory: ${fixedComparisonCategory}")
            Application application = applicationService.getApplicationByApplicationId("LCA")
            Indicator indicator = indicatorService.getIndicatorByIndicatorId("internalToolLCA", true)

            if (application && indicator) {
                List<String> categoriesToCompare = []

                if(fixedBasedCategory == null) categoriesToCompare.add(baseCategoryId)
                if(fixedComparisonCategory == null) categoriesToCompare.add(comparisonCategoryId)

                List<ResultCategory> resolvedResultCategories = indicator?.getResolveResultCategories(null)
                List<ResultCategory> resultCategories = resolvedResultCategories?.findAll({ categoriesToCompare.contains(it.resultCategoryId) })
                CalculationRule calculationRule = application.calculationRules?.find({ calculationRuleId.equals(it.calculationRuleId) })


                List<CalculationRule> rulesForDynamicResult = []

                if (calculationRule?.virtual) {
                    List<CalculationRule> neededVirtualRules = application.calculationRules.findAll({calculationRule.virtual.contains(it.calculationRuleId)})

                    if (neededVirtualRules) {
                        rulesForDynamicResult.add(calculationRule)
                        rulesForDynamicResult.addAll(neededVirtualRules)
                    }
                } else {
                    rulesForDynamicResult.add(calculationRule)
                }

                List<ResultCategory> categoriesForDynamicResult = []

                resultCategories?.each { ResultCategory resultCategory ->
                    if (resultCategory?.virtual) {
                        List<ResultCategory> neededVirtualCategories = resolvedResultCategories.findAll({resultCategory.virtual.contains(it.resultCategoryId)})

                        if (neededVirtualCategories) {
                            categoriesForDynamicResult.add(resultCategory)
                            categoriesForDynamicResult.addAll(neededVirtualCategories)
                        }
                    } else {
                        categoriesForDynamicResult.add(resultCategory)
                    }
                }

                if (categoriesForDynamicResult && rulesForDynamicResult) {
                    List<String> resultCategoryIds = categoriesForDynamicResult.collect({it.resultCategoryId})
                    log.info("COMPARISON: Creating datasets..., Categories: ${resultCategoryIds}, Rule: ${calculationRule.calculationRuleId}")

                    List<String> filteredResourceIds = getResourceCompareFilterResourceIds()

                    BasicDBObject mapQuery = new BasicDBObject()
                    mapQuery.put("applicationId", "LCA")
                    mapQuery.put("active", true)

                    if (filteredResourceIds) {
                        mapQuery.put("resourceId", [$nin: filteredResourceIds])
                    }

                    def projection = [resourceId: 1, profileId: 1, unitForData: 1]

                    if (resourceType) {
                        mapQuery.put("resourceType", resourceType)
                    }

                    if (resourceSubType) {
                        mapQuery.put("resourceSubType", resourceSubType)
                    }

                    if (dataProperty) {
                        mapQuery.put("dataProperties", dataProperty)
                    }

                    Resource.collection.find(mapQuery, projection)?.toList()?.each { Document r ->
                        Dataset d = new Dataset()
                        d.resourceId = r.resourceId
                        d.profileId = r.profileId
                        d.additionalQuestionAnswers = [profileId: r.profileId]
                        d.userGivenUnit = r.unitForData
                        d.quantity = 1
                        d.answerIds = ["1"]
                        d.manualId = new ObjectId().toString()
                        d.resultCategoryIds = resultCategoryIds
                        datasets.add(d)
                    }
                    log.info("COMPARISON: Starting calculation with: ${datasets?.size()} datasets")
                    calculationResults = newCalculationServiceProxy.resourceComparison(indicator, rulesForDynamicResult, categoriesForDynamicResult, datasets)
                    log.info("COMPARISON: Calculation done, results: ${calculationResults?.size()}")
                }
            }
        }

        String templateAsString = g.render(template: "/import/resourceCompareTable", model: [datasets          : datasets, calculationResults: calculationResults, calculationRuleId: calculationRuleId,
                                                                                     baseCategoryId    : baseCategoryId, comparisonCategoryId: comparisonCategoryId,
                                                                                     fixedBasedCategory: fixedBasedCategory, fixedComparisonCategory: fixedComparisonCategory]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getFailingResourcesForRule() {
        String resourcesFor = params.find({ it.key.toString().startsWith("resourcesFor") })?.key?.toString()

        if (resourcesFor) {
            String ruleId = StringUtils.substringAfter(resourcesFor, "resourcesFor")

            if (ruleId) {
                List<String> resourceIds = params.list(ruleId)

                if (resourceIds) {
                    Workbook wb = new XSSFWorkbook()
                    CellStyle cs = wb.createCellStyle()
                    Font f = wb.createFont()
                    f.setBold(true)
                    cs.setFont(f)
                    cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
                    cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)

                    Sheet sheet = wb.createSheet()

                    List<String> infoRowHeadings = ["resourceId", "staticFullName", "resourceType", "resourceSubType", "combinedUnits", "allowVariableThickness", "impactNonLinear", "epdProgram", "importFile"]

                    int infoCellIndex = 0
                    int rowIndex = 0
                    Row headingRow = sheet.createRow(rowIndex)
                    Cell infoCell
                    infoRowHeadings.each { String heading ->
                        infoCell = headingRow.createCell(infoCellIndex)
                        infoCell.setCellStyle(cs)
                        infoCell.setCellType(CellType.STRING)
                        infoCell.setCellValue(heading)
                        infoCellIndex++
                    }
                    rowIndex++

                    resourceIds.each { String resourceId ->
                        Document resource = optimiResourceService.getResourceAsDocument(resourceId, null)
                        Row infoRow = sheet.createRow(rowIndex)
                        infoCellIndex = 0
                        infoRowHeadings.each { String heading ->
                            infoCell = infoRow.createCell(infoCellIndex)
                            infoCell.setCellType(CellType.STRING)
                            infoCell.setCellValue(resource.get(heading)?.toString()?.replace("[","")?.replace("]",""))
                            infoCellIndex++
                        }
                        rowIndex++
                    }

                    infoRowHeadings.size().times {
                        if (it != 0) {
                            sheet.autoSizeColumn(it)
                        }
                    }

                    sheet.setAutoFilter(new CellRangeAddress(0, infoCell.rowIndex, 0, infoCell.columnIndex))

                    if (wb) {
                        response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                        response.setHeader("Content-disposition", "attachment; filename=${ruleId}_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                        OutputStream outputStream = response.getOutputStream()
                        wb.write(outputStream)
                        outputStream.flush()
                        outputStream.close()
                    } else {
                        render(text: "Could not download Excel file :(")
                    }
                } else {
                    render(text: "ResourceIds: ${resourceIds} for ruleId ${ruleId}, Could not download excel")
                }
            } else {
                render(text: "RuleId ${ruleId} for resourcesFor: ${resourcesFor}, Could not download excel")
            }
        } else {
            render(text: "No resource for selected: ${resourcesFor}, Could not download excel")
        }
    }

    def showMultipleDefaultProfiles() {
        def resources = optimiResourceService.findDuplicateDefaultProfiles()
        flash.successAlert = "Found ${resources?.size()} resources with duplicate default profiles"
        chain(action: "form", model: [resources: resources, resourceType: "Duplicate defaults", deleteRedirect: "showMultipleDefaultProfiles"])
    }

    def showThicknessesNotMatching() {
        def resources = optimiResourceService.findFailingThicknessMatch()
        flash.successAlert = "Found ${resources?.size()} resources failing thickness match"
        chain(action: "form", model: [resources: resources, resourceType: "Failing thickness match", deleteRedirect: "showThicknessesNotMatching"])
    }

    def showNoDefaultProfile() {
        def resources = optimiResourceService.findFailingDefaultProfile()
        flash.successAlert = "Found ${resources?.size()} resources with no defaultProfile"
        chain(action: "form", model: [resources: resources, resourceType: "Failing no defaultProfile", deleteRedirect: "showNoDefaultProfile"])
    }

    def showFailingMassConversionFactor() {
        Boolean active = params.boolean('active')
        def resources = optimiResourceService.findFailingMassConversionFactor(active)
        flash.successAlert = "Found ${resources?.size()} resources failing massConversionFactor"
        chain(action: "form", model: [resources: optimiResourceService.findFailingMassConversionFactor(active), resourceType: "Failing massconversion", deleteRedirect: "showFailingMassConversionFactor"])
    }

    def searchResourcesByDate() {
        String toRender = ""
        String startDateString = params.startDate
        String endDateString = params.endDate

        if (startDateString && endDateString) {
            DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy")
            try {
                Date startDate = simpleDateFormat.parse(startDateString)
                Date endDate = simpleDateFormat.parse(endDateString)

                if (startDate.equals(endDate) || startDate.before(endDate)) {
                    List<Resource> resources = Resource.findAllByFirstUploadTimeGreaterThanEqualsAndFirstUploadTimeLessThanEquals(startDate, endDate)

                    if (resources) {
                        toRender = "<table class=\"table\"><thead><tr>" +
                                "<td><b>ResourceId / ProfileId</b></td>" +
                                "<td><b>StaticFullName</b></td>" +
                                "<td><b>Active</b></td>" +
                                "<td><b>UnitForData (combinedUnits)</b></td>" +
                                "<td><b>SubType</b></td>" +
                                "<td><b>Areas</b></td>" +
                                "<td><b>EnvironmentDataSourceType</b></td>" +
                                "<td><b>FirstUploadTime</b></td>" +
                                "<td><b>ImportFile</b></td>" +
                                "</tr></thead><tbody>"
                        resources.each { Resource r ->
                            toRender = "${toRender}<tr>" +
                                    "<td>${r.resourceId} / ${r.profileId} <a href=\"javascript:\" onclick=\"window.open('${createLink(controller: "import", action: "showData", params: [resourceUUID: r.id])}', '_blank', 'width=1024, height=768, scrollbars=1');stopBubblePropagation(event);\"><i class=\"fas fa-search-plus\"></i></a></td>" +
                                    "<td>${r.staticFullName}</td>" +
                                    "<td>${r.active ? "true" : "false"}</td>" +
                                    "<td>${r.unitForData} (${r.combinedUnits ? r.combinedUnits.join(", ") : r.unitForData})</td>" +
                                    "<td>${resourceTypeService.getLocalizedName(r.subType)}</td>" +
                                    "<td>${r.areas ? r.areas.join(", ") : ""}</td>" +
                                    "<td>${r.environmentDataSourceType}</td>" +
                                    "<td>${simpleDateFormat.format(r.firstUploadTime)}</td>" +
                                    "<td>${r.importFile}</td>" +
                                    "</tr>"
                        }
                        toRender = "${toRender}</tbody></table>"
                    } else {
                        toRender = "<b>No resources found between ${startDateString} - ${endDateString}</b>"
                    }
                } else {
                    toRender = "<b>Fool, start date needs to be before end date.</b>"
                }
            } catch (Exception e) {
                toRender = "<b>${e}</b>"
            }
        } else {
            toRender = "<b>No resources found between ${startDateString} - ${endDateString}</b>"
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def uploadResourceCompareFilter() {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile excelFile = request.getFile("file")

            if (!excelFile) {
                flash.fadeErrorAlert = g.message(code: "import.file.required")
                redirect action: "compareResources"
            } else {
                File directory = new File("/var/www/static/resourceCompareFilter")
                directory.mkdirs()
                FileUtils.cleanDirectory(directory)
                excelFile.transferTo(new File("/var/www/static/resourceCompareFilter/${excelFile.originalFilename}"))
                flash.fadeSuccessAlert = "Filter ${excelFile.originalFilename} uploaded successfully!"
                redirect action: "compareResources"
            }
        } else {
            flash.fadeErrorAlert = "Cannot import excel file, because invalid request: ${request?.class} (should be MultipartHttpServletRequest)."
            redirect action: "compareResources"
        }
    }

    def downloadResourceCompareFilter() {
        File directory = new File("/var/www/static/resourceCompareFilter")
        File[] files = directory.listFiles()
        File filterFile = files ? files.first() : null

        if (filterFile && filterFile.exists()) {
            response.setHeader("Content-disposition", "attachment; filename=${filterFile.name}")
            response.setHeader("Content-Length", "${filterFile.size()}")
            InputStream contentStream = filterFile.newInputStream()
            response.outputStream << contentStream
            contentStream.close()
            webRequest.renderView = false
        } else {
            flash.fadeErrorAlert = "No file :("
            redirect action: "compareResources"
        }
    }

    def deleteResourceCompareFilter() {
        File directory = new File("/var/www/static/resourceCompareFilter")
        File[] files = directory.listFiles()
        File filterFile = files ? files.first() : null

        if (filterFile && filterFile.exists()) {
            boolean deleted = filterFile.delete()

            if (deleted) {
                flash.fadeSuccessAlert = "File deleted!"
            } else {
                flash.fadeErrorAlert = "Unable to delete the file."
            }
            redirect action: "compareResources"
        } else {
            flash.fadeErrorAlert = "No file :("
            redirect action: "compareResources"
        }
    }

    def getResourceCompareFilterResourceIds() {
        List<String> filteredResourceIds = []
        File directory = new File("/var/www/static/resourceCompareFilter")
        File[] files = directory.listFiles()
        File filterFile = files ? files.first() : null

        if (filterFile && filterFile.exists()) {
            try {
                Workbook workbook = WorkbookFactory.create(filterFile)

                if (workbook) {
                    Integer numberOfSheets = workbook.getNumberOfSheets()

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

                                if (heading && "resourceid".equalsIgnoreCase(heading)) {
                                    columnHeadings.put(heading, cell.columnIndex)
                                }
                            }

                            Integer rowNro = 0
                            Row row
                            Iterator<Row> rowIterator = sheet.rowIterator()
                            while (rowIterator.hasNext()) {
                                row = rowIterator.next()

                                if (rowNro != 0) {
                                    columnHeadings.each { String key, Integer value ->
                                        if ("resourceid".equalsIgnoreCase(key)) {
                                            String cellValue = importMapperService.getCellValue(row.getCell(value))

                                            if (cellValue) {
                                                filteredResourceIds.add(cellValue.trim())
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
                loggerUtil.error(log, "RESOURCE COMPARE FILTER ERROR", e)
                flashService.setErrorAlert("RESOURCE COMPARE FILTER ERROR: ${e.message}", true)
            }
        }
        log.info("FILTERED RESOURCEIDS: ${filteredResourceIds}")
        return filteredResourceIds
    }
}
