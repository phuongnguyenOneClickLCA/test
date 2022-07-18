package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants.ExcelCellHeaders
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.APICalculationResponse
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.ClassRemapping
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.ImportMapperCollapseGrouper
import com.bionova.optimi.core.domain.mongo.ImportMapperDefaultRule
import com.bionova.optimi.core.domain.mongo.ImportMapperPresetFilter
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.RecognitionRuleset
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.gbxmlparser.Parser
import com.bionova.optimi.gbxmlparser.ParserException
import grails.gorm.transactions.Transactional
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.docx4j.org.apache.xpath.operations.Bool
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletRequest

@Transactional
class OneClickLcaApiService {

    static transactional = "mongo"

    def applicationService
    def userService
    def importMapperService
    def temporaryImportDataService
    def indicatorService
    def datasetService
    def configurationService
    def springSecurityService
    def APICalculationResponseService
    def newCalculationService
    def recognitionRulesetService
    def constructionService
    def productDataListService
    def unitConversionUtil
    def licenseService
    def entityService
    def importMapperTrainingDataService

    def uploadFile(MultipartHttpServletRequest request, User user, String ipAddress = null) {
        Map<Integer, String> responseCode
        try {
            responseCode = checkPreRequirements(request, null, Boolean.TRUE, user)

            if (!responseCode) {
                String securityToken = request.getParameter("securityToken")
                String pluginVersion = request.getParameter("addOnVersion")
                MultipartFile importFile = request.getFile("importFile")
                String fileToken = request.getParameter("fileToken")
                String applicationId = request.getParameter("applicationId") ?: configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_APPLICATION_ID.toString())?.value
                String importMapperId = request.getParameter("importMapperId") ?: configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_IMPORT_MAPPER_ID.toString())?.value
                Application application = applicationService.getApplicationByApplicationId(applicationId)
                ImportMapper importMapper = application?.importMappers?.find({
                    importMapperId.equals(it.importMapperId)
                })
                Map<String, String> securityTokens = importMapper?.securityTokens
                log.info("import was used with plugin ${securityTokens?.get(securityToken)}")

                Boolean apiCalculation = request.getParameter("APICalculation") ? request.getParameter("APICalculation").toBoolean() : false

                File resolvedFile = parseXmlFile(importFile, null, fileToken)

                if (resolvedFile) {
                    if (!importMapper?.fileMaxSize || importMapper?.fileMaxSize >= (resolvedFile.length() / 1024)) {
                        if (resolvedFile.length() < Constants.DB_MAX_DOCUMENT_SIZE) {
                            Boolean tooManyColumns = Boolean.FALSE
                            TemporaryImportData postFile = new TemporaryImportData()
                            postFile.importFileName = importFile.originalFilename
                            postFile.ipAddress = ipAddress
                            postFile.importFile = resolvedFile.getBytes()
                            postFile.originatingSystem = securityTokens?.get(securityToken)
                            postFile.pluginVersion = pluginVersion
                            postFile.apiCalculation = apiCalculation

                            if (user) {
                                postFile.tiedToUserId = user.id.toString()
                            }

                            String fileExtension = FilenameUtils.getExtension(resolvedFile.name)

                            String extension
                            Workbook wb
                            if ("xlsx".equalsIgnoreCase(fileExtension)) {
                                try {
                                    wb = importMapperService.xlsxFileParser(resolvedFile, importMapper?.maxColumns)
                                    extension = ".xlsx"
                                } catch (IndexOutOfBoundsException e) {
                                    tooManyColumns = Boolean.TRUE
                                } catch (Exception e) {
                                    wb = new HSSFWorkbook(new ByteArrayInputStream(resolvedFile.getBytes()))
                                    extension = ".xls"
                                }
                            } else {
                                try {
                                    wb = new HSSFWorkbook(new ByteArrayInputStream(resolvedFile.getBytes()))
                                    extension = ".xls"
                                } catch (Exception e) {
                                    try {
                                        wb = importMapperService.xlsxFileParser(resolvedFile, importMapper?.maxColumns)
                                        extension = ".xlsx"
                                    } catch (IndexOutOfBoundsException index) {
                                        tooManyColumns = Boolean.TRUE
                                    } catch (Exception ex) {}
                                }
                            }

                            if (wb && !tooManyColumns) {
                                int rowCounts = importMapperService.getRowsAmountFromFile(wb)
                                postFile.rowCounts = rowCounts
                                wb.close()
                                postFile.extension = extension ? extension : "xlsx".equalsIgnoreCase(fileExtension) ? ".xlsx" : ".xls"
                                postFile = postFile.save(flush: true, failOnError: true)

                                if (postFile.id) {
                                    responseCode.put(200, postFile.id.toString())
                                }
                            } else {
                                responseCode.put(413, "Imported file contains too many columns. Maximum number of columns with data is ${importMapper?.maxColumns ?: Constants.MAX_COLUMNS}")
                            }
                        } else {
                            responseCode.put(413, "Imported file is too big (${FileUtils.byteCountToDisplaySize(resolvedFile.length())}). Max size is ${FileUtils.byteCountToDisplaySize(Constants.DB_MAX_DOCUMENT_SIZE)}")
                            log.error("User: ${user?.username} imported a file that exceeds the database MaxDocumentSize (${FileUtils.byteCountToDisplaySize(Constants.DB_MAX_DOCUMENT_SIZE)}), unable to save post file.")
                        }
                    } else {
                        responseCode.put(413, "Imported file is too big (${resolvedFile.length() / 1024} KB). Max size is ${importMapper.fileMaxSize} KB")
                    }
                    resolvedFile.delete()
                } else {
                    log.error("Could not create the import file")
                    responseCode.put(400, "Invalid request")
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return responseCode
    }


    private File parseXmlFile(MultipartFile multipartFile, File file, String fileToken) {
        File tempFile

        if ((multipartFile && !multipartFile.isEmpty()) || file) {
            String extension

            if (file) {
                extension = FilenameUtils.getExtension(file.name)?.toLowerCase()
            } else {
                extension = FilenameUtils.getExtension(multipartFile.originalFilename)?.toLowerCase()
            }

            Boolean doParsing = "xml".equals(extension)

            if ("xlsx".equals(extension)) {
                if (fileToken) {
                    tempFile = File.createTempFile(fileToken, ".xlsx")
                } else {
                    String fileNamePerfix = UUID.randomUUID().toString() + "ifcimport"
                    tempFile = File.createTempFile(fileNamePerfix, ".xlsx")
                }
            } else {
                if (fileToken) {
                    tempFile = File.createTempFile(fileToken, ".xls")
                } else {
                    String fileNamePerfix = UUID.randomUUID().toString() + "ifcimport"
                    tempFile = File.createTempFile(fileNamePerfix, ".xls")
                }
            }

            if (doParsing) {
                try {
                    if (multipartFile) {
                        File parsingFile = File.createTempFile(multipartFile.originalFilename, ".xml")
                        multipartFile.transferTo(parsingFile)
                        FileInputStream fins = new FileInputStream(parsingFile)
                        FileOutputStream fouts = new FileOutputStream(tempFile)
                        Parser parser = new Parser()
                        parser.parse(fins)
                        parser.outputXls(fouts, true, false, false)
                        // includeOpenings = true includeEmpty = false always, includeSpaceIds = true
                        fins.close()
                        fouts.close()
                        parsingFile.delete()
                    } else {
                        FileInputStream fins = new FileInputStream(file)
                        FileOutputStream fouts = new FileOutputStream(tempFile)
                        Parser parser = new Parser()
                        parser.parse(fins)
                        parser.outputXls(fouts, true, false, false)
                        fins.close()
                        fouts.close()
                    }
                } catch (ParserException pe) {
                    log.error("Error in parsing gbxml file: ${pe.getMessage()}")
                    tempFile = null
                }
            } else {
                if (multipartFile) {
                    multipartFile.transferTo(tempFile)
                } else {
                    FileUtils.copyFile(file, tempFile)
                }
            }
        }
        return tempFile
    }

    private Map<Integer, String> checkPreRequirements(MultipartHttpServletRequest request, Map parameters, Boolean fileUpload = Boolean.FALSE, User user = null, Boolean resultDownload = Boolean.FALSE) {
        Map<Integer, String> response = [:]
        String fileToken
        String importFile
        String securityToken
        String applicationId
        String importMapperId

        if (parameters) {
            fileToken = parameters.get("fileToken")
            importFile = parameters.get("importFile")
            securityToken = parameters.get("securityToken")
            applicationId = parameters.get("applicationId") ? parameters.get("applicationId") :
                configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                    com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_APPLICATION_ID.toString())?.value
            importMapperId = parameters.get("importMapperId") ? parameters.get("importMapperId") :
                    configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                            com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_IMPORT_MAPPER_ID.toString())?.value
        } else {
            fileToken = request.getParameter("fileToken")
            importFile = request.getFile("importFile")
            securityToken = request.getParameter("securityToken")
            applicationId = request.getParameter("applicationId") ? request.getParameter("applicationId") :
                    configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                            com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_APPLICATION_ID.toString())?.value
            importMapperId = request.getParameter("importMapperId") ? request.getParameter("importMapperId") :
                    configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                            com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_IMPORT_MAPPER_ID.toString())?.value
        }
        
        ImportMapper importMapper = applicationService.getApplicationByApplicationId(applicationId)?.importMappers?.find({
            importMapperId?.equals(it.importMapperId)
        })
        Map<String, String> securityTokens = importMapper?.securityTokens
        if (!response && fileUpload && (!fileToken || !importFile || (!securityToken && !user))) {
            log.error("ImportMapper API: Missing params 1: FileToken: ${fileToken}, ImportFile: ${importFile}, SecurityToken: ${securityToken} and User: ${user?.username}")
            response.put(400, "Missing necessary parameters. Required are fileToken and importFile and securityToken or valid authentication")
        } else if (!response && (!fileToken || (!securityToken && !user))) {
            log.error("ImportMapper API: Missing params 2: FileToken: ${fileToken}, SecurityToken: ${securityToken} and User: ${user?.username}")
            response.put(400, "Missing necessary parameters. Required are fileToken and securityToken or valid authentication")
        }

        if (!response && !securityTokens?.containsKey(securityToken) && !user) {
            log.error("ImportMapper API: Invalid securityToken ${securityToken} or unauthorized user")
            response.put(401, "Unauthorized")
        }

        if (!response && user) {
            String licensedSoftware = securityTokens?.get(securityToken)
            List<String> allowedFeatures

            if (resultDownload) {
                allowedFeatures = [Feature.API_SAMPLE_EXCEL]
            } else {
                allowedFeatures = ["import${licensedSoftware}", Feature.IFC_FROM_SIMPLE_BIM, Feature.API_SAMPLE_EXCEL]
            }
            Boolean allowedByProjectBasedLicense = allowedByProjectBasedLicense(user, allowedFeatures)

            if (!allowedByProjectBasedLicense) {
                List<License> licenses = userService.getLicenses(user)
                List<Feature> features = licenses?.collect({ it.licensedFeatures })?.flatten()?.unique()
                List<String> featureIds = features?.collect({ it?.featureId })

                if (!fileUpload && (!licensedSoftware || !featureIds || !CollectionUtils.containsAny(allowedFeatures, featureIds))) {
                    if (licensedSoftware) {
                        log.error("ImportMapper API: User ${user.username} does not have Business: Import from ${licensedSoftware} licensed.")
                    } else {
                        log.error("ImportMapper API: User ${user.username} did not provide a valid securityToken: ${securityToken}.")
                    }
                    response.put(403, "License not available")
                } else {
                    log.info("ImportMapper API: User ${user.username} authenticated API usage with user based licenses: ${licenses?.collect({ it.name })}")
                }
            }
        }
        return response
    }

    def allowedByProjectBasedLicense(User user, List<String> allowedFeatureIds) {
        Boolean allowed = Boolean.FALSE

        if (user && allowedFeatureIds) {
            List<Entity> parents = userService.getExportableParentEntities(user)

            featureAllowedLoop:
            for (Entity parent in parents) {
                List<License> validLicenses = licenseService.getValidLicensesForEntity(parent).findAll{ it.projectBased }

                for (License license in validLicenses) {
                    List<String> featureIds = license.licensedFeatures?.collect({ it?.featureId })

                    if (featureIds && CollectionUtils.containsAny(allowedFeatureIds, featureIds)) {
                        allowed = Boolean.TRUE
                        log.info("One Click LCA API: User ${user.username} authenticated API usage with project: ${parent.name} and project based license: ${license.name}")
                        break featureAllowedLoop
                    }
                }
            }
        }
        return allowed
    }

    def getApiIndicatorIdFromUserLicense(ImportMapper importMapper, String securityToken, User user) {
        String apiIndicatorId
        String licensedSoftware = importMapper.securityTokens?.get(securityToken)

        if (licensedSoftware && user) {
            List<String> allowedFeatureIds = ["import${licensedSoftware}", Feature.IFC_FROM_SIMPLE_BIM, Feature.API_SAMPLE_EXCEL]
            apiIndicatorId = indicatorService.getApiIndicatorIdForUser(user, allowedFeatureIds)
        }
        return apiIndicatorId
    }

    def calculate(Map parameters, String importFilePath, User user, String ipAddress = null) {
        Map<Integer, String> responseCode = checkPreRequirements(null, parameters, Boolean.FALSE, user)

        if (!responseCode && importFilePath) {
            String securityToken = parameters.get("securityToken")
            String applicationId = parameters.get("applicationId") ? parameters.get("applicationId") :
                    configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                            com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_APPLICATION_ID.toString())?.value
            String importMapperId = parameters.get("importMapperId") ? parameters.get("importMapperId") :
                    configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                            com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_IMPORT_MAPPER_ID.toString())?.value
            String fileToken = parameters.get("fileToken")
            Boolean APICalculation = parameters.get("APICalculation") ?
                    new Boolean(parameters.get("APICalculation")) : Boolean.FALSE

            TemporaryImportData temporaryImportData = TemporaryImportData.findById(DomainObjectUtil.stringToObjectId(importFilePath))
            Workbook wb

            if (temporaryImportData) {
                File tempFile = File.createTempFile(UUID.randomUUID().toString(), temporaryImportData.extension)
                FileUtils.writeByteArrayToFile(tempFile, temporaryImportData.importFile)

                String exception
                try {
                    if (".xlsx".equals(temporaryImportData?.extension)) {
                        wb = new XSSFWorkbook(tempFile)
                    } else {
                        wb = new HSSFWorkbook(new ByteArrayInputStream(tempFile.getBytes()))
                    }
                } catch (Exception e) {
                    exception = e.getMessage()
                }
                tempFile.delete()

                Application application = applicationService.getApplicationByApplicationId(applicationId)
                ImportMapper importMapper = application?.importMappers?.find({
                    importMapperId.equals(it.importMapperId)
                })
                String APIIndicator = parameters.get("APIIndicator") ? parameters.get("APIIndicator") :
                        configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID,
                                com.bionova.optimi.construction.Constants.ConfigName.API_DEFAULT_INDICATOR_ID.toString())?.value
                Boolean APINoCombine = parameters.get("APINoCombine") ?
                        new Boolean(parameters.get("APINoCombine")) : Boolean.FALSE
                Boolean APINoFiltering = parameters.get("APINoFiltering") ?
                        new Boolean(parameters.get("APINoFiltering")) : Boolean.FALSE
                Boolean maintainCompatibility = importMapper?.apiMaintainCompatibilityForUploads?.contains(securityToken)

                if (APICalculation && APIIndicator && importMapper) {
                    Date date = new Date()

                    if (user && !userService.getSuperUser(user) && !userService.getDataManager(user) && !userService.isDeveloper(user)) {
                        String indicatorIdForcedByLicense = getApiIndicatorIdFromUserLicense(importMapper, securityToken, user)

                        if (indicatorIdForcedByLicense) {
                            log.debug("APIIndicator was forced to change by the license:" +
                                    " from ${APIIndicator} to ${indicatorIdForcedByLicense}")
                            APIIndicator = indicatorIdForcedByLicense
                        }
                    }
                    temporaryImportData.importMapperId = importMapper.importMapperId
                    temporaryImportData.indicatorId = APIIndicator

                    Entity tempEntity = new Entity()
                    tempEntity.name = "Temporary Calculation - ${date}"
                    tempEntity.entityClass = Constants.EntityClass.DESIGN.toString()
                    tempEntity.indicatorIds = ["${APIIndicator}"]
                    temporaryImportData.temporaryEntity = tempEntity
                    Boolean temporaryCalculation = APICalculation

                    removeOldResponses(ipAddress)

                    APICalculationResponse apiCalculationResponse = APICalculationResponseService.getAPICalculationResponseByFileToken(fileToken)

                    if (!apiCalculationResponse) {
                        apiCalculationResponse = new APICalculationResponse()
                        log.error("API: UNEXPECTED ERROR, NO APICalculationResponse Found with filetoken: ${fileToken}")
                    }

                    apiCalculationResponse.status = "OK"
                    apiCalculationResponse.userId = user ? user.id.toString() : null
                    apiCalculationResponse.apiFile = null
                    apiCalculationResponse.originalFile = temporaryImportData.importFile
                    apiCalculationResponse.originalFileExtension = temporaryImportData.extension
                    apiCalculationResponse.params = parameters
                    apiCalculationResponse.fileToken = fileToken
                    apiCalculationResponse.ipAddress = ipAddress
                    apiCalculationResponse.indicatorId = APIIndicator

                    String errorMessage

                    if (wb) {
                        try {
                            List<RecognitionRuleset> systemRulesets = recognitionRulesetService.getAllRecognitionRulesets(Constants.RulesetType.MAPPING.toString())
                            List<String> requiredFieldsForRecognition = importMapperService.getRequiredFieldsForRecognition(importMapper)
                            Boolean useAdaptiveRecognition = Boolean.TRUE
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(APIIndicator,true)
                            ImportMapperPresetFilter presetFilter = importMapperService.getPresetFilterObjects(importMapper.presetFilters)?.find({
                                it.defaultForIndicators?.contains(indicator?.indicatorId)
                            })
                            String resolvedEntityClass = indicator?.compatibleEntityClasses && !indicator?.compatibleEntityClasses?.isEmpty() ? indicator.compatibleEntityClasses.first() : null
                            log.info("API: resolvedEntityClass: ${resolvedEntityClass}")
                            ClassRemapping classRemapChoice = resolvedEntityClass ? importMapper.allowedClassRemappingsSets?.find({resolvedEntityClass.equals(it.remappingEntityClass)}) : null
                            Boolean useOriginalClass = temporaryCalculation
                            long now = System.currentTimeMillis()
                            log.info("API: Starting to create datasets...")
                            boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
                            def importMapperReturn
                            if (useOldImportMapper) {
                                importMapperReturn = importMapperService.oldImportDatasetsByImportMapper(importMapper, null, wb, useAdaptiveRecognition,
                                        Boolean.TRUE, APINoFiltering ? null : presetFilter, Boolean.TRUE, temporaryCalculation, useOriginalClass,Boolean.TRUE,indicator.indicatorId,null)
                                importMapperReturn.discardedValuesWithAmount = importMapperReturn.get("discardedValues")
                            } else {
                                importMapperReturn = importMapperService.importDatasetsByImportMapper(importMapper, null, wb, useAdaptiveRecognition,
                                        Boolean.TRUE, APINoFiltering ? null : presetFilter, Boolean.TRUE, temporaryCalculation, useOriginalClass,Boolean.TRUE,indicator.indicatorId,null)
                            }
                            log.info("API: Time it took to create datasets: ${System.currentTimeMillis() - now} ms")

                            List<Dataset> okDatasets = importMapperReturn.okDatasets
                            List<ImportMapperCollapseGrouper> collapseGroupers = importMapperReturn.collapseGroupers
                            List<String> headers = importMapperReturn.headers
                            String thicknessHeading = importMapperReturn.thicknessHeading

                            List<Dataset> rejectedAndOkDatasets = importMapperReturn.rejectedAndOkDatasets

                            if (okDatasets && rejectedAndOkDatasets) {
                                List okManualIds = okDatasets.collect({ it.manualId })

                                if (okManualIds) {
                                    rejectedAndOkDatasets = rejectedAndOkDatasets.findAll({ it.manualId && !okManualIds.contains(it.manualId) })
                                } else {
                                    rejectedAndOkDatasets = null
                                }
                            } else {
                                rejectedAndOkDatasets = null
                            }

                            log.info("API: Starting to filter and combine if chosen...")
                            now = System.currentTimeMillis()

                            if (okDatasets && !okDatasets.isEmpty()) {
                                Account account = userService.getAccount(user)
                                List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)
                                List<String> resourceIdsInDataList = []
                                boolean isUncapped = licenseService.hasEcoinventUncappedLicensed(licensedFeatureIds)

                                if (!isUncapped) {
                                    resourceIdsInDataList = productDataListService.getResourceIdsInDataList(licensedFeatureIds, account)
                                }
                                if (presetFilter && !APINoFiltering && (presetFilter.skipQuestionMappingValues || presetFilter.includeQuestionMappingValues)) {
                                    if (presetFilter.includeQuestionMappingValues) {
                                        okDatasets.removeAll({
                                            !presetFilter.includeQuestionMappingValues.collect({
                                                it.toUpperCase()
                                            }).contains(it.importMapperGroupedBy?.toUpperCase())
                                        })
                                    } else if (presetFilter.skipQuestionMappingValues) {
                                        okDatasets.removeAll({
                                            presetFilter.skipQuestionMappingValues.collect({
                                                it.toUpperCase()
                                            }).contains(it.importMapperGroupedBy?.toUpperCase())
                                        })
                                    }
                                }

                                if (!APINoCombine && collapseGroupers) {
                                    importMapperService.combineDatasets(collapseGroupers, okDatasets)
                                }

                                log.info("API: Time it took to filter and combine: ${System.currentTimeMillis() - now} ms")
                                log.info("API: Starting to recognize datasets...")
                                now = System.currentTimeMillis()

                                okDatasets.each { Dataset dataset ->
                                    if (classRemapChoice && classRemapChoice.automaticClassRemappings) {
                                        String oldClass = dataset.trainingData?.get("CLASS")?.toUpperCase()

                                        Boolean isResourceDataset = importMapperService.isResourceDataset(dataset)
                                        List<String> allowedClassRemappings = isResourceDataset ? classRemapChoice.allowedClassRemappings : classRemapChoice.allowedDescriptiveClassRemappings
                                        if (oldClass && !allowedClassRemappings?.contains(oldClass)) {
                                            String newClass = classRemapChoice.automaticClassRemappings.get(oldClass)
                                            importMapperService.changeDatasetClass(dataset, importMapper, newClass, resolvedEntityClass)
                                        }
                                    }
                                }

                                String organization = account?.companyName
                                Map<String, Document> trainingDataPerDataset = importMapperTrainingDataService.getTrainingDataForDatasetList(okDatasets, applicationId,
                                        importMapper.importMapperId, indicator, null, organization, resourceIdsInDataList, isUncapped)
                                Map <String, Document> trainingDataOCLIDPerDataset = importMapperTrainingDataService.getTrainingDataForDatasetListByImportDisplayField(
                                        okDatasets, ExcelCellHeaders.OCLID.name(), indicator, null, organization, resourceIdsInDataList, isUncapped)

                                okDatasets.each { Dataset dataset ->
                                    Document importMapperTrainingData = trainingDataPerDataset?.getAt(dataset.manualId)
                                    Document importMapperOCLIDTrainingData = trainingDataOCLIDPerDataset?.getAt(dataset.manualId)

                                    importMapperService.recognizeDataset(dataset, systemRulesets, requiredFieldsForRecognition, applicationId, importMapper, indicator,
                                            null, Boolean.TRUE,null, account,null,
                                            resourceIdsInDataList, isUncapped, importMapperTrainingData, importMapperOCLIDTrainingData)
                                }

                                List<Dataset> failedToRecognizeDatasets = okDatasets.findAll({ !it.resourceId })

                                if (failedToRecognizeDatasets) {
                                    if (rejectedAndOkDatasets) {
                                        failedToRecognizeDatasets.addAll(rejectedAndOkDatasets)
                                    }
                                } else if (rejectedAndOkDatasets) {
                                    failedToRecognizeDatasets = rejectedAndOkDatasets
                                }
                                okDatasets = okDatasets.findAll({ it.resourceId })

                                log.info("API: Time it took to recognize datasets: ${System.currentTimeMillis() - now} ms")

                                if (okDatasets && !okDatasets.isEmpty()) {
                                    okDatasets.each { Dataset dataset ->
                                        String valueForClass = dataset.trainingData?.get("CLASS")

                                        if (valueForClass) {
                                            ImportMapperDefaultRule importMapperDefaultRule = importMapper.defaultValueRules.find({
                                                valueForClass.equalsIgnoreCase(it.classMatch)
                                            })

                                            if (importMapperDefaultRule) {
                                                importMapperService.setDefaultValuesForDataset(dataset, importMapperDefaultRule)
                                            }
                                        }
                                    }

                                    List<Dataset> mutatedWithConstructionsDatasets = []
                                    if (okDatasets.find({ it.uniqueConstructionIdentifier })) {
                                        List<Dataset> constructionDatasets = okDatasets.findAll({ it.uniqueConstructionIdentifier })
                                        int i = 0

                                        for (Dataset cD in constructionDatasets) {
                                            cD.seqNr = i
                                            i++
                                        }

                                        ResourceCache resourceCache = ResourceCache.init(constructionDatasets)
                                        for (Dataset constructionDataset: constructionDatasets) {
                                            Resource resource = resourceCache.getResource(constructionDataset)
                                            Construction construction = constructionService.getConstruction(resource?.constructionId)

                                            if (construction) {
                                                Map additionals = constructionService.getScalingParameters(construction)

                                                if (additionals) {
                                                    if (constructionDataset.additionalQuestionAnswers) {
                                                        constructionDataset.additionalQuestionAnswers.putAll(additionals)
                                                    } else {
                                                        constructionDataset.additionalQuestionAnswers = additionals
                                                    }
                                                }

                                                Double europeanMultiplier
                                                if (unitConversionUtil.isImperialUnit(constructionDataset.userGivenUnit)) {
                                                    String targetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(constructionDataset.userGivenUnit)
                                                    europeanMultiplier = unitConversionUtil.doConversion(constructionDataset.quantity, datasetService.getThickness(constructionDataset.additionalQuestionAnswers), constructionDataset.userGivenUnit, null, null, null, null, null, resource?.density, targetUnit)
                                                }

                                                List<Dataset> datasets = construction.datasets?.findAll({ it.defaultConstituent || it.defaultConstituent == null })
                                                resourceCache = ResourceCache.init(datasets)
                                                for (Dataset constructionSubSet: datasets)
                                                {
                                                    Dataset dataset = new Dataset()
                                                    dataset.resourceId = constructionSubSet.resourceId

                                                    if (unitConversionUtil.isImperialUnit(constructionDataset.userGivenUnit))  {
                                                        String constructionUnit = unitConversionUtil.europeanUnitToImperialUnit(constructionSubSet.userGivenUnit)
                                                        dataset.userGivenUnit = constructionUnit
                                                        Double convertedQuantity = unitConversionUtil.doConversion(constructionSubSet.quantity, datasetService.getThickness(constructionSubSet.additionalQuestionAnswers), constructionUnit, resourceCache.getResource(constructionSubSet), null, null, null, Boolean.TRUE, null, constructionSubSet.userGivenUnit)
                                                        dataset.constructionValue = convertedQuantity?.toString()
                                                        dataset.quantity = convertedQuantity && europeanMultiplier ? convertedQuantity * europeanMultiplier : 0
                                                        dataset.answerIds = [dataset.quantity.toString() ?: "0"]
                                                    } else {
                                                        dataset.constructionValue = constructionSubSet.quantity.toString()
                                                        dataset.userGivenUnit = constructionSubSet.userGivenUnit
                                                        dataset.quantity = constructionSubSet.quantity && constructionDataset.quantity ? constructionSubSet.quantity * constructionDataset.quantity : 0
                                                        dataset.answerIds = [dataset.quantity.toString() ?: "0"]
                                                    }

                                                    dataset.profileId = constructionSubSet.profileId
                                                    //it was commented out 04.05.2022, delete this code if it not lead any issues in month period.
                                                    //dataset.userGivenUnit = constructionSubSet.userGivenUnit
                                                    dataset.additionalQuestionAnswers = [datasetIdFromConstruction: constructionSubSet.manualId]

                                                    constructionSubSet.additionalQuestionAnswers?.each { String key, def value ->
                                                        dataset.additionalQuestionAnswers.put(key, value)
                                                    }

                                                    /* parentConstructionValues copied */
                                                    dataset.uniqueConstructionIdentifier = constructionDataset.uniqueConstructionIdentifier
                                                    dataset.seqNr = constructionDatasets.find({
                                                        it.uniqueConstructionIdentifier.equals(dataset.uniqueConstructionIdentifier)
                                                    }).seqNr
                                                    dataset.parentConstructionId = constructionDataset.manualId //ONLY LIKE THIS IN API, NORMALLY: constructionDataset.resource?.constructionId
                                                    dataset.questionId = constructionDataset.questionId
                                                    dataset.sectionId = constructionDataset.sectionId
                                                    dataset.queryId = constructionDataset.queryId
                                                    mutatedWithConstructionsDatasets.add(dataset)
                                                }
                                            }
                                        }
                                    }

                                    if (mutatedWithConstructionsDatasets) {
                                        okDatasets.addAll(mutatedWithConstructionsDatasets)
                                    }
                                    temporaryImportData.temporaryEntity = datasetService.preHandleDatasets(temporaryImportData.temporaryEntity, okDatasets, [indicator], Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
                                    long time = System.currentTimeMillis()
                                    log.info("API: Starting to calculate entity results...")
                                    temporaryImportData.temporaryEntity = newCalculationService.calculate(null, indicator.indicatorId, null, temporaryImportData.temporaryEntity, Boolean.TRUE)
                                    log.info("API: Time it took to calculate entity reuslts: ${System.currentTimeMillis() - time} ms")

                                    temporaryImportData.temporaryEntity.datasets?.each { Dataset dataset ->
                                        if (dataset.quantity && (dataset.quantity % 1D) != 0D) {
                                            if (dataset.quantity >= 1D) {
                                                dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
                                            } else {
                                                dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(3, BigDecimal.ROUND_HALF_UP).toDouble()
                                            }
                                            dataset.answerIds = [dataset.quantity.toString()]
                                        }
                                    }
                                    //temporaryImportData = temporaryImportData.save(flush: true)

                                    List<Dataset> datasets = []

                                    if (temporaryImportData.temporaryEntity?.datasets) {
                                        datasets.addAll(temporaryImportData.temporaryEntity.datasets.toList())
                                    }

                                    if (datasets && headers) {
                                        long time2 = System.currentTimeMillis()
                                        log.info("API: Starting to generate results workbook...")
                                        Workbook resultwb = importMapperService.generateExcelForResults(datasets, temporaryImportData.temporaryEntity?.tempCalculationResults, temporaryImportData.temporaryEntity,
                                                indicator, user, null, Boolean.FALSE, failedToRecognizeDatasets, importMapper, thicknessHeading)
                                        log.info("API: Time it took to generate results workbook: ${System.currentTimeMillis() - time2} ms")

                                        if (resultwb) {
                                            ByteArrayOutputStream bos = new ByteArrayOutputStream()
                                            resultwb.write(bos)
                                            apiCalculationResponse.apiFile = bos.toByteArray()
                                            bos.close()
                                            resultwb.close()
                                            apiCalculationResponse.merge(flush: true, failOnError: true)
                                        } else {
                                            errorMessage = "FAILED, could not generate results workbook."
                                        }
                                    } else {
                                        errorMessage = "FAILED, no datasets for temp entity."
                                    }
                                } else {
                                    errorMessage = "FAILED, no datasets after filtering."
                                }
                            } else {
                                errorMessage = "FAILED, zero datasets recognized from import file."
                            }
                        } catch (Exception e) {
                            errorMessage = "FAILED, ${e}"
                        }
                    } else {
                        errorMessage = "FAILED, No Workbook found."
                    }

                    if (errorMessage) {
                        log.error("API CALCULATION ERROR: ${errorMessage}")
                        apiCalculationResponse.status = errorMessage
                        apiCalculationResponse.merge(flush: true, failOnError: true)
                        responseCode = [(500): "Response calculation failed"]
                    }
                } else if (!APIIndicator) {
                    responseCode = [(403): "Indicator not available"]
                }
                log.info("API CALCULATION: MAINTAINCOMPATIBILITY: ${maintainCompatibility} FOR DATA: ${temporaryImportData.id}")
                if (!maintainCompatibility) {
                    //Maintain compatibility with Simplebim, they send apicalc true even if its not apicalc but done in software, deleting the tempfile would then break their import in software...
                    TemporaryImportData.collection.remove([_id: temporaryImportData.id])
                }
            } else {
                responseCode = [(500): "Response calculation failed"]
            }
        }
        return responseCode
    }

    def getAPICalculationResponseFile(Map parameters) {
        User user = springSecurityService.getCurrentUser()
        Map<Integer, Object> responseCode = checkPreRequirements(null, parameters, Boolean.FALSE, user, Boolean.TRUE)

        if (!responseCode) {
            APICalculationResponse apiCalculationResponse = APICalculationResponse.findByFileToken(parameters.get("fileToken"))

            if (apiCalculationResponse) {
                if (apiCalculationResponse.apiFile) {
                    Workbook wb = new HSSFWorkbook(new ByteArrayInputStream(apiCalculationResponse.apiFile))
                    responseCode = [(200): wb]
                } else if (apiCalculationResponse.status?.contains("FAILED")) {
                    responseCode = [(500): "Response calculation failed"]
                } else {
                    responseCode = [(404): "Response not ready"]
                }
            } else {
                responseCode = [(410): "Response not found"]
            }
        }
        return responseCode
    }

    public String getClientIp(MultipartHttpServletRequest request) {
        String remoteAddr
        String xForwardedFor

        for (String header : request.getHeaderNames().toList()) {
            if ("x-forwarded-for".equalsIgnoreCase(header)) {
                xForwardedFor = request.getHeader(header)
                break
            }
        }

        if (xForwardedFor) {
            if (xForwardedFor.indexOf(",") != -1) {
                remoteAddr = xForwardedFor.substring(xForwardedFor.lastIndexOf(",") + 2)
            } else {
                remoteAddr = xForwardedFor
            }
        } else {
            remoteAddr = request.getRemoteAddr()
        }
        return remoteAddr
    }
    public String getRemoteAdd(HttpServletRequest request){
        String remoteAdd = ""
        if(request){
            remoteAdd = request.getHeader("X-Forwarded-For") ?: request.remoteAddr

        }
        return remoteAdd
    }

    def removeOldResponses(String ipAddress) {
        if (ipAddress) {
            List<APICalculationResponse> apiCalculationResponses = APICalculationResponseService.getAPICalculationResponsesByIp(ipAddress)

            if (apiCalculationResponses && apiCalculationResponses.size() > 4) {
                apiCalculationResponses = apiCalculationResponses.sort({ it.dateCreated })
                Integer recordsToRemove = apiCalculationResponses.size() - 4

                for (Integer i = 0; i < recordsToRemove; i++) {
                    APICalculationResponse.collection.remove([_id: apiCalculationResponses.get(i).id])
                }
            }
        }
    }

    def removeOldImports(String ipAddress) {
        if (ipAddress) {
            List<TemporaryImportData> temporaryImportDatas = temporaryImportDataService.getTemporaryImportDatasByIpAddress(ipAddress)

            if (temporaryImportDatas && temporaryImportDatas.size() > 4) {
                temporaryImportDatas = temporaryImportDatas.sort({ it.importDate })
                Integer recordsToRemove = temporaryImportDatas.size() - 4

                for (Integer i = 0; i < recordsToRemove; i++) {
                    TemporaryImportData.collection.remove([_id: temporaryImportDatas.get(i).id])
                }
            }
        }
    }

    def logApiRequestResponse(HttpServletRequest request, User user, Map<Integer, Object> responseCode) {
        if (user) {
            if (request && !responseCode) {
                log.info("API request parameters from user ${user.username}, IP ${getRemoteAdd(request)} for endpoint ${request.getRequestURI()}: ${request.getParameterMap()?.findAll {it.key != "importFile"}}")
            } else if (request && responseCode) {
                if (responseCode.values().first() instanceof String) {
                    log.info("API response from user ${user.username},IP ${getRemoteAdd(request)} for endpoint ${request.getRequestURI()}: STATUS: ${responseCode.keySet().first().intValue()}, BODY: ${responseCode.values().first()}")
                } else {
                    log.info("API response from user ${user.username},IP ${getRemoteAdd(request)} for endpoint ${request.getRequestURI()}: STATUS: ${responseCode.keySet().first().intValue()}, BODY: ${responseCode.values().first().class}")
                }
            }
        } else {
            log.warn("API request parameters from USER NOT FOUND,IP ${getRemoteAdd(request)} for endpoint ${request?.getRequestURI()}: ${request?.getParameterMap()?.findAll {it.key != "importFile"}}")
        }
    }
}
