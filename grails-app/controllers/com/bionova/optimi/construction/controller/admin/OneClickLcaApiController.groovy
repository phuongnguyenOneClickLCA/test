package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.service.EntityService
import com.bionova.optimi.core.service.NewCalculationService
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.json.StringEscapeUtils
import org.apache.catalina.connector.ClientAbortException
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.grails.web.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMethod

import javax.servlet.http.HttpServletResponse

/**
 * @author Pasi-Markus Mäkelä
 */
class OneClickLcaApiController extends ExceptionHandlerController {

    def optimiResourceService
    def APICalculationResponseService
    def temporaryImportDataService
    def userService
    def oneClickLcaApiService
    def configurationService
    def shellCmdService
    def optimiExcelImportService
    def applicationService
    def resourceDumpService
    def resourceTypeService
    def unitConversionUtil
    NewCalculationService newCalculationService
    EntityService entityService

    def form() {
        User user = userService.getCurrentUser()
        List<ImportMapper> importMappers = applicationService.getAllApplicationImportMappers()
        List<String> licensedApiImports = importMappers.collect({ it.securityTokens?.keySet() })?.flatten()

        [user: user, licensedApiImports: licensedApiImports, importMappersIds: importMappers?.collect({ it.importMapperId })]
    }

    def list() {
        def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
        String sizes = shellCmdService.executeShellCmd("mongo $dbName --quiet /home/maintenance/js/collectionSizes.js")
        long temporaryImportDataSize = 0
        long apiResponseSize = 0

        if (sizes && sizes.indexOf("temporaryImportData") > -1 && sizes.indexOf("APICalculationResponse") > -1) {
            try {
                temporaryImportDataSize = StringUtils.substringBetween(sizes, "temporaryImportData:\"", "\"").toLong()
                apiResponseSize = StringUtils.substringBetween(sizes, "APICalculationResponse:\"", "\"").toLong()
            } catch (Exception e) {
                log.error("API respones list: ${e}")
            }
        }
        List<APICalculationResponse> apiCalculationResponses = APICalculationResponse.list()
        List<TemporaryImportData> temporaryImportDatas = TemporaryImportData.findAllByApiCalculationIsNullOrApiCalculation(false)
        List<TemporaryImportData> apiCalcTemporaryImportDatas = TemporaryImportData.findAllByApiCalculation(true)
        [APICalculationResponses: apiCalculationResponses, temporaryImportDatas: temporaryImportDatas, apiCalcTemporaryImportDatas: apiCalcTemporaryImportDatas,
         apiResponseSize: FileUtils.byteCountToDisplaySize(apiResponseSize), temporaryImportDataSize: FileUtils.byteCountToDisplaySize(temporaryImportDataSize)]
    }

    def getFileFromApiResponse() {
        if (params.id && params.results) {
            APICalculationResponse apiCalculationResponse = APICalculationResponseService.getAPICalculationResponseById(params.id.toString())
            if (apiCalculationResponse) {
                Workbook wb = new HSSFWorkbook(new ByteArrayInputStream(apiCalculationResponse.apiFile))
                response.contentType = 'application/vnd.ms-excel'
                response.setHeader("Content-disposition", "attachment; filename=${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            }
        } else if (params.id && params.original) {
            APICalculationResponse apiCalculationResponse = APICalculationResponseService.getAPICalculationResponseById(params.id.toString())
            if (apiCalculationResponse) {
                Workbook wb
                if (".xlsx".equals(apiCalculationResponse.originalFileExtension)) {
                    wb = new XSSFWorkbook(new ByteArrayInputStream(apiCalculationResponse.originalFile))
                    response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    response.setHeader("Content-disposition", "attachment; filename=${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                } else {
                    wb = new HSSFWorkbook(new ByteArrayInputStream(apiCalculationResponse.originalFile))
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                }
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            }
        }
    }

    @Secured(["ROLE_DEVELOPER"])
    def resourceDumps() {
        [dataCategories: Application.collection.distinct("apiDataProvisions.dataCategory", String.class)?.toList()]
    }

    @Secured(["ROLE_SUPER_USER"])
    def downloadResourceDump() {
        String upstreamDBClassified = params.upstreamDBClassified
        List<String> impactCategories = params.list('impactCategories')

        if (upstreamDBClassified && impactCategories) {
            try {
                List<String> headings = ["staticFullName", "active", "areas", "pcr", "upstreamDBClassified", "unitForData", "resourceType", "resourceSubType", "dataProperties"] +
                        "standardUnit" + impactCategories

                List<Document> resources = optimiResourceService.getResourcesForResourceDump(upstreamDBClassified, impactCategories)

                if (resources) {
                    Workbook wb = new XSSFWorkbook()
                    CellStyle bold = optimiExcelImportService.getBoldCellStyle(wb)
                    Sheet sheet = wb.createSheet()
                    int rowIndex = 0
                    Row row = sheet.createRow(rowIndex)
                    Cell cell
                    int cellIndex = 0

                    headings.each { String heading ->
                        cell = row.createCell(cellIndex)
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(heading)
                        cell.setCellStyle(bold)
                        cellIndex++
                    }

                    resources.each { Document r ->
                        rowIndex++
                        Row contentRow = sheet.createRow(rowIndex)
                        cellIndex = 0

                        ResourceType resourceSubType = resourceTypeService.getResourceType(r.resourceType, r.resourceSubType, true)
                        Resource resource = r as Resource

                        headings.each { String heading ->

                            String value
                            if(heading == "standardUnit"){
                                value = resourceSubType?.standardUnit ?: ""
                            }else if(resourceSubType && impactCategories?.contains(heading)) {
                                Double impactValue = DomainObjectUtil.callGetterByAttributeName(heading, resource)
                                if (impactValue != null) {
                                    value = unitConversionUtil.doConversion(impactValue, resource.defaultThickness_mm, resourceSubType.standardUnit, resource, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)
                                    //if(!resourceSubType.standardUnit.equalsIgnoreCase(resource.unitForData)) log.info("$resource.unitForData --> $resourceSubType.standardUnit : [${r[heading]} : $value]")
                                }
                            }else {
                                value = r.get(heading) ? "areas".equals(heading) || "dataProperties".equals(heading) ? r.get(heading).toString().replace("[", "").replace("]", "") : r.get(heading).toString() : ""
                            }
                            
                            cell = contentRow.createCell(cellIndex)
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(value)
                            cellIndex++
                        }
                    }
                    response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    response.setHeader("Content-disposition", "attachment; filename=ResourceDump_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                } else {
                    flash.errorAlert = "No resources found with given params: ${params}"
                    redirect(controller: 'resourceType', action:'benchmark')
                }
            } catch (Exception e) {
                flash.errorAlert = "${e}"
                redirect(controller: 'resourceType', action:'benchmark')
            }
        } else {
            flash.errorAlert = "Missing params: ${params}"
            redirect(controller: 'resourceType', action:'benchmark')
        }
    }

    def getFileFromTemporaryImportData() {
        if (params.id) {
            try {
                TemporaryImportData importData = temporaryImportDataService.getTemporaryImportDataById(params.id.toString())
                Workbook wb
                if (".xlsx".equals(importData.extension) || importData.importFileName?.endsWith(".xlsx")) {
                    wb = new XSSFWorkbook(new ByteArrayInputStream(importData.importFile))
                    response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                    response.setHeader("Content-disposition", "attachment; filename=${importData.importFileName}")
                } else {
                    wb = new HSSFWorkbook(new ByteArrayInputStream(importData.importFile))
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=${importData.importFileName}")
                }
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (Exception e) {
                flash.errorAlert = "${e}"
                redirect action: "list"
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourceLibrary() {
        String dataCategory = params.get("dataCategory")
        User user = userService.getCurrentUser(true)

        if (user) {
            if (dataCategory) {
                boolean auth = false
                APIDataProvision apiDataProvision

                if ("fullResourceList".equals(dataCategory)) {
                    auth = true
                } else {
                    apiDataProvision = applicationService.getApplicationAsDocumentByApplicationId("IFC", [apiDataProvisions: 1])?.apiDataProvisions?.find({it.dataCategory == dataCategory}) as APIDataProvision

                    if (apiDataProvision.authRequired) {
                        if (user) {
                            List<License> licenses = userService.getLicenses(user)
                            List<String> features = licenses?.collect({ it.licensedFeatures })?.flatten()?.findAll({ Feature f -> f?.featureId})?.collect({ Feature f ->f.featureId})
                            String featureId = Feature.IFC_FROM_SIMPLE_BIM

                            if (features?.contains(featureId)||oneClickLcaApiService.allowedByProjectBasedLicense(user, [featureId])) {
                                auth = true
                            }
                        }
                    } else {
                        auth = true
                    }
                }

                if (apiDataProvision||"fullResourceList".equals(dataCategory)) {
                    if (auth) {
                        File dump

                        if ("fullResourceList".equals(dataCategory)) {
                            dump = new File("/var/www/static/dumps/${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json")
                        } else {
                            dump = new File("/var/www/static/dumps/${apiDataProvision?.dataCategory}_${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json")
                        }

                        if (dump.exists()) {
                            try {
                                JSONObject dumpJSON = new JsonSlurper().parse(dump)

                                if (user && dumpJSON && dumpJSON.resources) {
                                    Account account = userService.getAccount(user)
                                    log.info("API: getResourceLibrary, dataCategory: ${dataCategory}, user: ${user?.username}, account: ${account?.companyName} (${account?.id?.toString()})")

                                    if (account) {
                                        File privateDataFile = resourceDumpService.createAccountSpecificPrivateDataDump(account)

                                        if (privateDataFile && privateDataFile.exists()) {
                                            JSONObject privateDumpJSON = new JsonSlurper().parse(privateDataFile)

                                            if (privateDumpJSON.private_resources) {
                                                dumpJSON.resources.addAll(privateDumpJSON.private_resources)
                                            }

                                            if (privateDumpJSON.licensed_resources) {
                                                dumpJSON.resources.addAll(privateDumpJSON.licensed_resources)
                                            }
                                            privateDataFile.delete()
                                        }
                                    }
                                }

                                File temp = File.createTempFile(UUID.randomUUID().toString(), ".json")
                                temp.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(dumpJSON).toString()))
                                response.setHeader("Content-disposition", "attachment; filename=${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json")
                                response.setHeader("Content-Length", "${temp.size()}")
                                InputStream contentStream = temp.newInputStream()
                                response.outputStream << contentStream
                                contentStream.close()
                                webRequest.renderView = false
                                temp.delete()
                            } catch (ClientAbortException e) {
                                // client disconnected before bytes were able to be written.
                            }
                        } else {
                            log.error("importMappingFile: Unable to find JSON mapping dump from path: /var/www/static/dumps/${apiDataProvision?.dataCategory}_${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json!")
                            render status: HttpServletResponse.SC_NOT_FOUND, text: "File not found", contentType: "application/json"
                        }
                    } else {
                        log.error("importMappingFile: Unauthenticated user tried to download the dump.")
                        render status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthenticated user", contentType: "application/json"
                    }
                } else {
                    log.error("importMappingFile: Not APIDataProvisions found with dataCategory ${dataCategory} for user: ${user?.username}")
                    render status: HttpServletResponse.SC_BAD_REQUEST, text: "Bad parameters", contentType: "application/json"
                }
            } else {
                render status: HttpServletResponse.SC_BAD_REQUEST, text: "Bad parameters", contentType: "application/json"
            }
        } else {
            render status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthenticated user", contentType: "application/json"
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def autocase() {
        User user = userService.getCurrentUser(true)

        if (user) {
            if (request.method == RequestMethod.GET.name()) {
                String oclcaProjectId = params.get("oclcaProjectId")
                String autocaseProjectId = params.get("pId")
                String autocaseDesignId = params.get("designId")
                String elecUsage = params.get("elecUsage")
                String natGasUsage = params.get("natGasUsage")
                String waterUsage = params.get("waterUsage")
                String city = params.get("city")
                String state = params.get("state")
                String countryCode = params.get("country")
                String projectName = params.get("projectName")
                String country

                if (countryCode) {
                    if ("CA".equals(countryCode)) {
                        country = "canada"
                    } else {
                        country = "USA"
                    }
                } else {
                    country = "canada"
                }

                String buildingType = params.get("buildingType")

                if (autocaseDesignId||oclcaProjectId) {
                    Entity entity

                    if (oclcaProjectId) {
                        entity = Entity.findByIdAndDeleted(DomainObjectUtil.stringToObjectId(oclcaProjectId), false)

                        if (entity && !entity.autocaseProjectId && autocaseProjectId) {
                            entity.autocaseProjectId = autocaseProjectId
                            entity = entity.merge(flush: true)
                        }
                    }

                    if (!entity && autocaseDesignId) {
                        entity = Entity.findByAutocaseDesignIdAndDeleted(autocaseDesignId, false)

                        if (entity && !entity.autocaseProjectId && autocaseProjectId) {
                            entity.autocaseProjectId = autocaseProjectId
                            entity = entity.merge(flush: true)
                        }
                    }

                    if (entity) {
                        redirect(controller: "entity", action: "show", params: [entityId: entity.id, name: entity.name])
                    } else if (autocaseDesignId) {
                        Map parameters = [new: true, entityClass: com.bionova.optimi.core.Constants.ParentEntityClass.BUILDING.toString(), autocaseDesignId: autocaseDesignId]

                        if (autocaseProjectId) {
                            parameters.put("autocaseProjectId", autocaseProjectId)
                        }
                        parameters.put("country", country)
                        if (state && country) {
                            Resource stateResource = optimiResourceService.getResourceByStateCode(state)
                            if (stateResource) {
                                if ("canada".equals(country) && stateResource.resourceGroup?.contains("CA-provinces")) {
                                    parameters.put("state", stateResource.resourceId)
                                } else if ("USA".equals(country) && stateResource.resourceGroup?.contains("US-states")) {
                                    parameters.put("state", stateResource.resourceId)
                                }
                            }
                        }
                        if (buildingType) {
                            parameters.put("buildingType", buildingType)
                        }
                        if (city) {
                            parameters.put("address", city)
                        }
                        if (projectName) {
                            projectName = projectName.replaceAll(com.bionova.optimi.core.Constants.USER_INPUT_REGEX,"")
                            if (projectName) {
                                parameters.put("projectName", projectName)
                            }
                        }
                        redirect(controller: "entity", action: "form", params: parameters)
                    } else {
                        log.error("AUTOCASE API: Unable to proceed with user: ${user.username}, could not find entity with: OCLCA EntityId: ${oclcaProjectId}, Autocase DesignId: ${autocaseDesignId}")
                        render(status: HttpServletResponse.SC_INTERNAL_SERVER_ERROR, text: "An error has occurred")
                    }
                } else {
                    render(status: HttpServletResponse.SC_BAD_REQUEST, text: "Missing parameters")
                }
            } else {
                render(status: HttpServletResponse.SC_BAD_REQUEST, text: "Invalid request")
            }
        } else {
            render(status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthorized")
        }
    }

    def importMappingFile() {
        User user = userService.getCurrentUser()

        if (user) {
            List<License> licenses = userService.getLicenses(user)
            List<Feature> features = licenses?.collect({ it.licensedFeatures })?.flatten()?.unique()
            String featureId = Feature.IFC_FROM_SIMPLE_BIM
            Boolean allowedByProjectBasedLicense = oneClickLcaApiService.allowedByProjectBasedLicense(user, [featureId])

            if (allowedByProjectBasedLicense || features?.collect({ it?.featureId })?.contains(featureId)) {
                File dump = new File("/var/www/static/dumps/${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json")

                if (dump.exists()) {
                    try {
                        response.setHeader("Content-disposition", "attachment; filename=${dump.name}")
                        response.setHeader("Content-Length", "${dump.size()}")
                        InputStream contentStream = dump.newInputStream()
                        response.outputStream << contentStream
                        contentStream.close()
                        webRequest.renderView = false
                    } catch (ClientAbortException e) {
                        // client disconnected before bytes were able to be written.
                    }
                } else {
                    log.error("importMappingFile: Unable to find JSON mapping dump from path: /var/www/static/dumps/${com.bionova.optimi.core.Constants.RESOURCE_DUMP_FILENAME}.json!")
                    render status: HttpServletResponse.SC_NOT_FOUND, text: "File not found", contentType: "application/json"
                }
            } else {
                log.error("importMappingFile: User ${user.username} does not have Business: Import to One Click LCA licensed.")
                render status: HttpServletResponse.SC_FORBIDDEN, text: "License not available", contentType: "application/json"
            }
        } else {
            log.error("importMappingFile: Unauthenticated user tried to download the dump.")
            render status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthenticated user", contentType: "application/json"
        }
    }

    @Secured(["ROLE_DATA_MGR"])
    def manualSpecialRulesJSON() {
        User user = userService.getCurrentUser()

        if (user && userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)) {
            Boolean created = resourceDumpService.createSpecialRulesFile()

            if (created) {
                importSpecialRules()
            } else {
                render text: "Something went wrong, error logged"
            }
        } else {
            render text: "unauthorized"
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def importSpecialRules() {
        User user = userService.getCurrentUser(true)

        if (user) {
            List<License> licenses = userService.getLicenses(user)
            List<Feature> features = licenses?.collect({ it.licensedFeatures })?.flatten()?.unique()
            String featureId = Feature.IFC_FROM_SIMPLE_BIM
            Boolean allowedByProjectBasedLicense = oneClickLcaApiService.allowedByProjectBasedLicense(user, [featureId])

            if (allowedByProjectBasedLicense || features?.collect({ it?.featureId })?.contains(featureId)) {
                File dump = new File("/var/www/static/dumps/${com.bionova.optimi.core.Constants.SPECIAL_RULES_FILENAME}.json")

                if (dump.exists()) {
                    response.setHeader("Content-disposition", "attachment; filename=${dump.name}")
                    response.setHeader("Content-Length", "${dump.size()}")
                    InputStream contentStream = dump.newInputStream()
                    response.outputStream << contentStream
                    contentStream.close()
                    webRequest.renderView = false
                } else {
                    log.error("importSpecialRules: Unable to find JSON specialRules dump from path: /var/www/static/dumps/${com.bionova.optimi.core.Constants.SPECIAL_RULES_FILENAME}.json!")
                    render status: HttpServletResponse.SC_NOT_FOUND, text: "File not found", contentType: "application/json"
                }
            } else {
                log.error("importSpecialRules: User ${user.username} does not have Business: Import to One Click LCA licensed.")
                render status: HttpServletResponse.SC_FORBIDDEN, text: "License not available", contentType: "application/json"
            }
        } else {
            log.error("importSpecialRules: Unauthenticated user tried to download importSpecialRules.")
            render status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthenticated user", contentType: "application/json"
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def dumpIndicatorBenchmarks(){

        User user = userService.getCurrentUser(true)

        if (user) {
            List<License> licenses = userService.getLicenses(user)
            List<Feature> features = licenses?.collect({ it.licensedFeatures })?.flatten()?.unique()
            String featureId = Feature.IFC_FROM_SIMPLE_BIM
            Boolean allowedByProjectBasedLicense = oneClickLcaApiService.allowedByProjectBasedLicense(user, [featureId])

            if (allowedByProjectBasedLicense || features?.collect({ it?.featureId })?.contains(featureId)) {
                String fileName = com.bionova.optimi.core.Constants.INDICATOR_BENCHMARK_DUMP_FILENAME
                String path = "/var/www/static/dumps/${fileName}"

                File file = new File(path)

                if (!file.exists()) {
                    Boolean created = resourceDumpService.createIndicatorBenckmarckDumpFile()
                    if (created) file = new File(path)
                }

                if (file.exists()) {
                    render file: file, fileName: "dumpIndicatorBenchmark", contentType: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                } else {
                    log.error("importSpecialRules: Unable to find IndicatorBenchmarks dump from path: /var/www/static/dumps/${com.bionova.optimi.core.Constants.INDICATOR_BENCHMARK}!")
                    render status: HttpServletResponse.SC_NOT_FOUND, text: "File not found", contentType: "application/json"                }
            } else {
                log.error("IndicatorBenchmarks: User ${user.username} does not have Business: Import to One Click LCA licensed.")
                render status: HttpServletResponse.SC_FORBIDDEN, text: "License not available", contentType: "application/json"
            }
        } else {
            log.error("IndicatorBenchmarks: Unauthenticated user tried to download indicatorBenchmarks.")
            render status: HttpServletResponse.SC_UNAUTHORIZED, text: "Unauthenticated user", contentType: "application/json"
        }

    }

    @Secured(["ROLE_DATA_MGR"])
    def manualMappingJSON() {
        User user = userService.getCurrentUser()

        if (user && userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)) {
            Boolean created = resourceDumpService.createResourceDumpFile()
            //This doesnt seem to be used at all so Im commenting it out
            //Boolean created2 = resourceDumpService.createApplicationSpecificDumps()

            if (created) {
                importMappingFile()
            } else {
                render text: "Something went wrong"
            }
        } else {
            render text: "unauthorized"
        }
    }


    def remove() {
        String id = params.id
        Boolean deleteOk = Boolean.FALSE

        if (id) {
            deleteOk = APICalculationResponseService.deleteAPICalculationResponse(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "APICalculationResponse removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing APICalculationResponse failed. See logs"
        }
        redirect action: "list"
    }

    def runTestCalculation() {
        String designId = params.designId
        String indicatorId = params.indicatorId

        if (designId && indicatorId) {
            Date now = new Date()
            long start = System.currentTimeMillis()
            Entity design = entityService.getEntityById(designId, null, Boolean.TRUE)
            
            if (design && design.parentById) {
                // if calculation runs successfully, presumably it would return an Entity
                if (newCalculationService.calculate(designId, indicatorId, design.parentById, design)) {
                    long delta = System.currentTimeMillis() - start
                    render([elapsedTimeInSeconds: "${delta / 1000}", status: 'ok'] as JSON)
                } else {
                    render status: HttpStatus.INTERNAL_SERVER_ERROR, text: "Error occured during the calculation. Check log, time is: ${now}"
                }
            } else {
                render status: HttpStatus.INTERNAL_SERVER_ERROR, text: "Could not find design and project from db"
            }
        } else {
            render status: HttpStatus.BAD_REQUEST, text: "Request parameters missing designId or indicatorId"
        }
    }
}
