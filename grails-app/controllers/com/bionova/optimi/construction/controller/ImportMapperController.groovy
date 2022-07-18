package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.IndicatorUse
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.APICalculationResponse
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.BimModelWarning
import com.bionova.optimi.core.domain.mongo.ClassRemapping
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.ImportMapperCollapseGrouper
import com.bionova.optimi.core.domain.mongo.ImportMapperDataRule
import com.bionova.optimi.core.domain.mongo.ImportMapperDefaultRule
import com.bionova.optimi.core.domain.mongo.ImportMapperPresetFilter
import com.bionova.optimi.core.domain.mongo.ImportMapperTrainingData
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.PluginUpdatePrompt
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QueryLastUpdateInfo
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.RecognitionRuleset
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ScopeToSave
import com.bionova.optimi.core.domain.mongo.SpecialRule
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.gbxmlparser.Parser
import com.bionova.optimi.gbxmlparser.ParserException
import com.bionova.optimi.gbxmlparser.ParserExceptionUtil
import com.bionova.optimi.importMapper.IncrementalImportResolverReturn
import com.bionova.optimi.ui.rset.RSEnvDatasetDto
import com.bionova.optimi.util.ExcelUtil
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.async.Promise
import grails.converters.JSON
import grails.plugins.VersionComparator
import org.apache.catalina.connector.ClientAbortException
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.openxml4j.exceptions.OpenXML4JException
import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.math.RoundingMode
import java.text.NumberFormat

import static grails.async.Promises.task

//import static grails.async.web.WebPromises.*

/**
 * @author Pasi-Markus Mäkelä
 */
class ImportMapperController extends ExceptionHandlerController {

    def entityService
    def queryService
    def indicatorService
    def datasetService
    def importMapperService
    def applicationService
    def importMapperTrainingDataService
    def licenseService
    def userService
    def fileUtil
    def channelFeatureService
    def recognitionRulesetService
    def featureService
    def oneClickLcaApiService
    def constructionService
    def newCalculationServiceProxy
    def helpConfigurationService
    def unitConversionUtil
    def simulationToolService
    def APICalculationResponseService
    def localizedLinkService
    def productDataListService
    def flashService
    def questionService
    def configurationService
    ScopeFactoryService scopeFactoryService

    static defaultAction = "index"

    private void logMemUsage() {
        StringBuffer sbuf = new StringBuffer()
        Runtime r = Runtime.getRuntime()
        double free =  r.freeMemory() / 1024d / 1024d
        double max =  r.maxMemory() / 1024d / 1024d
        double total =  r.totalMemory() / 1024d / 1024d
        NumberFormat nf = NumberFormat.getNumberInstance()
        nf.setMinimumIntegerDigits(3)
        nf.setMaximumIntegerDigits(4)
        nf.setMinimumFractionDigits(3)
        nf.setMaximumFractionDigits(3)
        sbuf.append("[Free left: " + nf.format(free) + " MB; ")
        sbuf.append("Total used: " + nf.format(total) + " MB; ")
        sbuf.append("Max: " + nf.format(max) + " MB] ")
        log.info("Memory usage: " + sbuf.toString())
        flashService.setFadeInfoAlert("Memory usage: " + sbuf.toString(), true)
    }

    def saveImportMapperMappings() {
        String entityId = params.entityId
        String childId = params.childEntityId
        String trainingDataCountry = params.trainingDataCountry
        Entity entity = entityService.getEntityByIdReadOnly(childId)
        Entity parentEntity = entityService.getEntityByIdReadOnly(entityId)
        String entityClass = parentEntity?.entityClass
        List<Dataset> toRender = []
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        String detailsKey = session?.getAttribute("materialHeading")
        Indicator indicator = session?.getAttribute("indicator")
        String defaultQueryId = importMapper?.questionMappingRules?.find({
            it.defaultTargetForEntityClass?.contains(entityClass)
        })?.target?.get("queryId")
        Query query = queryService.getQueryByQueryId(defaultQueryId, Boolean.TRUE)
        List<Question> questions = query?.getAllQuestions()
        List<Question> additionals = []
        User user = userService.getCurrentUser()
        Account account = userService.getAccount()
        String organization = account?.companyName

        questions?.each { Question q ->
            List<Question> showInMapping = q.getAdditionalQuestions(indicator, null, null, parentEntity)?.findAll({ Question aq -> aq.showInMapping || aq.privateClassificationQuestion })

            if (showInMapping) {
                additionals.addAll(showInMapping)
            }
        }

        String resourceGroups = importMapper?.resourceGroups ? importMapper?.resourceGroups?.join(",") : "OTHER"

        try {
            List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
            def datasetsMap = BasicDBObject.parse(params.datasets)
            def remappedIdentified = BasicDBObject.parse(params.remappedIdentified)

            if (ifcDatasets) {
                if (datasetsMap) {
                    datasetsMap.each { String manualId, Map<String, String> resourceAndProfileId ->
                        Dataset dataset = ifcDatasets.find({ manualId.equals(it.manualId) })
                        String resourceId = resourceAndProfileId.get("resourceId")
                        String profileId = resourceAndProfileId.get("profileId")
                        String comment = resourceAndProfileId.get("comment") ?: ""
                        String quantity = resourceAndProfileId.get("quantity") ?: ""
                        String unit = resourceAndProfileId.get("unit") ?: ""

                        if (dataset && resourceId && profileId) {
                            dataset.resourceId = resourceId
                            dataset.profileId = profileId

                            if (unit) {
                                dataset.userGivenUnit = unit
                            }

                            if (quantity && DomainObjectUtil.isNumericValue(quantity)) {
                                dataset.answerIds = [quantity]
                                dataset.quantity = DomainObjectUtil.convertStringToDouble(quantity)
                            }

                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("profileId", profileId)
                                dataset.additionalQuestionAnswers.put("comment", comment)
                            } else {
                                dataset.additionalQuestionAnswers = [profileId: profileId, comment: comment]
                            }

                            if (dataset.allowMapping && user && parentEntity) {
                                dataset.recognizedFrom = "${message(code: "importMapper.trainingData.recognized.weigth3")}"
                                dataset.recognizedFromShort = "${message(code: "importMapper.trainingData.recognized.weigth3.short")}"
                                List<ImportMapperTrainingData> duplicates = importMapperTrainingDataService.getDuplicateTrainingDataForUser(dataset.trainingMatchData, user.id.toString())

                                if (duplicates) {
                                    duplicates.each { ImportMapperTrainingData imp ->
                                        imp.delete(flush: true)
                                    }
                                }
                                String trainingComment = session?.getAttribute("trainingComment")
                                String fileName = session?.getAttribute(Constants.IMPORT_FILENAME)
                                String originatingSystem = session?.getAttribute(Constants.ORIGINATING_SYSTEM)
                                importMapperTrainingDataService.saveTrainingData(dataset.trainingData, session?.getAttribute("applicationId")?.toString(),
                                        importMapper?.importMapperId, trainingComment, originatingSystem, fileName, resourceId, profileId, null, session, trainingDataCountry, user.id.toString(),
                                        organization)
                            }
                            toRender.add(dataset)
                        }
                    }
                }

                if (remappedIdentified) {
                    remappedIdentified.each { String manualId, Map<String, String> resourceAndProfileId ->
                        Dataset dataset = ifcDatasets.find({ manualId.equals(it.manualId) })
                        if(log.isTraceEnabled()) {
                            log.trace("looking for ${manualId} in " +
                                    "${ifcDatasets?.collect{it.manualId}} ifcDatasets.")
                        }
                        String resourceId = resourceAndProfileId.get("resourceId")
                        String profileId = resourceAndProfileId.get("profileId")

                        if (dataset && resourceId && profileId) {
                            dataset.resourceId = resourceId
                            dataset.profileId = profileId

                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("profileId", profileId)
                            } else {
                                dataset.additionalQuestionAnswers = [profileId: profileId]
                            }

                            if (user && parentEntity) {
                                List<ImportMapperTrainingData> duplicates = importMapperTrainingDataService.getDuplicateTrainingDataForUser(dataset.trainingMatchData, user.id.toString())

                                if (duplicates) {
                                    duplicates.each { ImportMapperTrainingData imp ->
                                        imp.delete(flush: true)
                                    }
                                }

                                if (dataset.allowMapping) {
                                    dataset.recognizedFrom = "${message(code: "importMapper.trainingData.recognized.weigth3")}"
                                    dataset.recognizedFromShort = "${message(code: "importMapper.trainingData.recognized.weigth3.short")}"
                                    String trainingComment = session?.getAttribute("trainingComment")
                                    String fileName = session?.getAttribute(Constants.IMPORT_FILENAME)
                                    String originatingSystem = session?.getAttribute(Constants.ORIGINATING_SYSTEM)
                                    importMapperTrainingDataService.saveTrainingData(dataset.trainingData, session?.getAttribute("applicationId")?.toString(),
                                            importMapper?.importMapperId, trainingComment, originatingSystem, fileName, resourceId, profileId, null, session, trainingDataCountry, user.id.toString(),
                                            organization)
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not save user mappings with AJAX in resolver: ${e}")
        }
        ClassRemapping classRemapChoice = entityClass ? importMapper?.allowedClassRemappingsSets?.find({entityClass.equals(it.remappingEntityClass)}) : null
        render(template: "/importMapper/identifiedRows", model: [datasets           : toRender,
                                                                 classRemapChoice   : classRemapChoice,
                                                                 account            : account,
                                                                 detailsKey         : detailsKey,
                                                                 additionalQuestions: additionals.unique(),
                                                                 indicator          : indicator,
                                                                 query              : query,
                                                                 importMapperId     : importMapper?.importMapperId,
                                                                 applicationId      : session?.getAttribute("applicationId"),
                                                                 resourceGroups     : resourceGroups,
                                                                 parentEntity       : parentEntity,
                                                                 entity             : entity,
                                                                 entityId           : entityId]
        )
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
            Boolean doParsing = ["xml","gbxml"].contains(extension)

            if ("xlsx".equals(extension)) {
                if (fileToken) {
                    tempFile = File.createTempFile(fileToken, Constants.XLSX)
                } else {
                    String fileNamePerfix = UUID.randomUUID().toString() + "ifcimport"
                    tempFile = File.createTempFile(fileNamePerfix, Constants.XLSX)
                }
            } else {
                if (fileToken) {
                    tempFile = File.createTempFile(fileToken, Constants.XLS)
                } else {
                    String fileNamePerfix = UUID.randomUUID().toString() + "ifcimport"
                    tempFile = File.createTempFile(fileNamePerfix, Constants.XLS)
                }
            }

            if (doParsing) {
                try {
                    if (multipartFile) {
                        File parsingFile = File.createTempFile(multipartFile.getOriginalFilename(), ".xml")
                        multipartFile.transferTo(parsingFile)
                        FileInputStream fins = new FileInputStream(parsingFile)
                        FileOutputStream fouts = new FileOutputStream(tempFile)
                        Parser parser = new Parser()
                        parser.parse(fins);
                        parser.outputXls(fouts, true, false, false)
                        // includeOpenings = true includeEmpty = false always, includeSpaceIds = true
                        fins.close()
                        fouts.close()
                        parsingFile.delete()
                    } else {
                        FileInputStream fins = new FileInputStream(file)
                        FileOutputStream fouts = new FileOutputStream(tempFile)
                        Parser parser = new Parser()
                        parser.parse(fins);
                        parser.outputXls(fouts, true, false, false)
                        fins.close()
                        fouts.close()
                    }
                } catch (ParserException pe) {
                    loggerUtil.error(log, "Error in parsing gbxml file: ${pe.getMessage()}")
                    String errorMessage = ParserExceptionUtil.getExceptionAsMessage(pe)
                    session?.setAttribute("gbxmlErrorMessage", errorMessage)
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

    def postFile() {
        User user = userService.getCurrentUser()
        oneClickLcaApiService.logApiRequestResponse(request, user, null)
        MultipartHttpServletRequest multipartHttpServletRequest
        Map<Integer, String> responseCode

        try {
            multipartHttpServletRequest = (MultipartHttpServletRequest) request
            String ipAddress = oneClickLcaApiService.getClientIp(multipartHttpServletRequest)
            responseCode = oneClickLcaApiService.uploadFile(multipartHttpServletRequest, user, ipAddress)
            def parameters = params

            if (responseCode.keySet().first() == 200) {
                if (params.APICalculation && params.boolean("APICalculation")) {
                    String fileToken = params.fileToken
                    APICalculationResponse oldData = APICalculationResponseService.getAPICalculationResponseByFileToken(fileToken)

                    if (oldData) {
                        log.info("API: Removed old data found with fileToken: ${fileToken}")
                        oldData.delete(flush: true, failOnError: true)
                    }

                    if (fileToken) {
                        APICalculationResponse apiCalculationResponse = new APICalculationResponse()
                        apiCalculationResponse.status = "OK"
                        apiCalculationResponse.apiFile = null
                        apiCalculationResponse.fileToken = fileToken
                        apiCalculationResponse.save(flush: true, failOnError: true)
                    }

                    Promise p = task {
                        oneClickLcaApiService.calculate(parameters, responseCode.values().first(), user, ipAddress)
                    }
                    p.onError { Throwable err ->
                        log.error("APICalculation: An error occured ${err.message}")
                    }
                }
            }
        } catch (Exception e) {
            responseCode = [(new Integer(HttpServletResponse.SC_BAD_REQUEST)): "Automated data sending failed. If you can export " +
                    "the data manually, you can export and import it in One Click LCA in the following address: <br/>" +
                    "<a href=\"${request?.getScheme()}://${request?.getServerName()}/app/sec/importMapper?applicationId=IFC&amp;importMapperId=IFCFromSimpleBIM\">" +
                    "${request?.getScheme()}://${request?.getServerName()}/app/sec/importMapper?applicationId=IFC&amp;importMapperId=IFCFromSimpleBIM</a>."]
            loggerUtil.error(log, "ImportMapper: Trying to post with invalid request: ${e}")
        }
        oneClickLcaApiService.logApiRequestResponse(request, user, responseCode)
        render status: responseCode.keySet().first().intValue(), text: responseCode.values().first(), contentType: "application/json"
    }

    def importResults() {
        User user = userService.getCurrentUser()
        oneClickLcaApiService.logApiRequestResponse(request, user, null)

        if (request.method == RequestMethod.GET.name()) {
            Map<Integer, Object> responseCode = oneClickLcaApiService.getAPICalculationResponseFile(params)
            int responseStatus = responseCode.keySet().first().intValue()

            if (responseStatus == HttpStatus.OK.value()) {
                Workbook wb = responseCode.values().first()

                if (wb) {
                    oneClickLcaApiService.logApiRequestResponse(request, user, responseCode)
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=API_response_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                } else {
                    responseCode = [(new Integer(HttpServletResponse.SC_GONE)): "Response not found"]
                    oneClickLcaApiService.logApiRequestResponse(request, user, responseCode)
                    sleep(2000) // Sleep for spammy apis :(
                    render status: HttpServletResponse.SC_GONE, text: "Response not found", contentType: "application/json"
                }
            } else {
                oneClickLcaApiService.logApiRequestResponse(request, user, responseCode)
                sleep(2000) // Sleep for spammy apis :(
                render status: responseStatus, text: responseCode.values().first()
            }
        } else {
            Map<Integer, Object> responseCode = [(new Integer(HttpServletResponse.SC_BAD_REQUEST)): "Invalid request"]
            oneClickLcaApiService.logApiRequestResponse(request, user, responseCode)
            sleep(2000) // Sleep for spammy apis :(
            render(status: HttpServletResponse.SC_BAD_REQUEST, text: "Invalid request", contentType: "application/json")
        }
    }

    def main() {
        int currentStep = params.int("currentStep") ?: 1
        Boolean bimModelChecker = params.boolean('bimModelChecker')
        User user = userService.getCurrentUser()
        String applicationId = params.applicationId ?: session?.getAttribute("applicationId")
        String importMapperId = params.importMapperId ?: session?.getAttribute("importMapperId")
        String errorMessage = params.errorMessage
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId ?:   session?.getAttribute("childEntityId")
        Application application = applicationService.getApplicationByApplicationId(applicationId)
        ImportMapper importMapper = application?.importMappers?.find({ importMapperId.equals(it.importMapperId) }) ?:  session?.getAttribute(Constants.IMPORT_MAPPER)
        String originatingSystem = params.originatingSystem
        if (importMapper) {
            if (session?.getAttribute("gbxmlErrorMessage")) {
                flash.errorAlert = session?.getAttribute("gbxmlErrorMessage")
                session.removeAttribute("gbxmlErrorMessage")
            } else if (errorMessage) {
                flash.errorAlert = errorMessage
            }

            if(currentStep == 2){
                redirect action: "fileToSession", params: params
            } else {
                Boolean showSteps = bimModelChecker ? Boolean.FALSE : importMapper.showSteps

                if (importMapper.applyChannel) {
                    channelFeatureService.applyChannelFeature(session, importMapper.applyChannel)
                }
                def entityId = params.entityId
                Entity entity = entityService.getEntityById(entityId,null)

                long now = System.currentTimeMillis()
                Boolean importAvailable = licenseService.getIsImportMapperAvailableForCurrentUser(importMapper)
                log.info("This kikkare took 1: ${System.currentTimeMillis() - now} ms")


                List<HelpConfiguration> helpConfigurations = helpConfigurationService.getConfigurationsForTargetPage('Import')
                String template = "chooseFile"
                Boolean noLicense = Boolean.FALSE
                String header = importMapper.localizedImportMessage ? importMapper.localizedImportMessage : "Importing with \"${importMapper.localizedName}\""

                if(session){
                    removeAllSessionAttributes(session)
                    session.setAttribute(Constants.IMPORT_MAPPER, importMapper)
                    session.setAttribute("importMapperId", importMapper.importMapperId)
                    session.setAttribute("applicationId", applicationId)
                    session.setAttribute(Constants.ORIGINATING_SYSTEM, originatingSystem)
                    session.setAttribute("childEntityId", childEntityId)
                    session.setAttribute(Constants.ENTITY_ID, entity?.id)
                    session.setAttribute(Constants.INDICATOR_ID, indicatorId)
                    session.setAttribute("bimModelChecker", bimModelChecker)
                    session.setAttribute("helpConfigurations", helpConfigurations)

                    if (!importAvailable) {
                        noLicense = Boolean.TRUE
                    }
                    session.setAttribute(Constants.NO_LICENSE, noLicense)
                }



                [importMapperFound: true, showSteps: showSteps,
                             applicationId    : session?.getAttribute("applicationId"), header: header,indicatorId: indicatorId,childEntityId: childEntityId,
                             entityId: entity?.id,entity:entity, fileMaxSize: importMapper.fileMaxSize,noLicense:noLicense,currentStep:currentStep,template: template,
                             appHelpUrl: localizedLinkService.getTransformStringIdsToUrl(importMapper.appHelpUrl), bimModelChecker: bimModelChecker, helpConfigurations:helpConfigurations, showSelectFile:true]
            }
        } else {
            flash.errorAlert = errorMessage ? errorMessage : "Cannot continue IFC import. No mapper found with parameters ${applicationId} & ${importMapperId}"
        }
    }
    def index() {
        removeAllSessionAttributes(session)
        def importFileExists = false
        Boolean bimModelChecker = params.boolean('bimModelChecker')
        User user = userService.getCurrentUser()
        String applicationId = params.applicationId
        String importMapperId = params.importMapperId
        String importFilePath = params.importFilePath
        String originatingSystem = params.originatingSystem
        String pluginVersion = params.addOnVersion
        String errorMessage = params.errorMessage
        String indicatorId = params.indicatorId
        Application application = applicationService.getApplicationByApplicationId(applicationId)
        ImportMapper importMapper = application?.importMappers?.find({ importMapperId.equals(it.importMapperId) })
        String outdatedPluginError

        if (importMapper) {
            Boolean showSteps = bimModelChecker ? Boolean.FALSE : importMapper.showSteps
            if (session?.getAttribute("gbxmlErrorMessage")) {
                flash.errorAlert = session?.getAttribute("gbxmlErrorMessage")
                session?.removeAttribute("gbxmlErrorMessage")
            } else if (errorMessage) {
                flash.errorAlert = errorMessage
            }
            int rowCounts = 0
            if (importFilePath) {
                try {
                    TemporaryImportData postFile = TemporaryImportData.findById(DomainObjectUtil.stringToObjectId(importFilePath))
                    String userId = user?.id?.toString()

                    if (postFile && (!postFile.tiedToUserId || (postFile.tiedToUserId && (postFile.tiedToUserId == userId)))) {
                        postFile.importMapperId = importMapperId
                        postFile.indicatorId = indicatorId
                        postFile.userId = userId
                        postFile.merge(flush: true)
                        session?.setAttribute(Constants.IMPORT_FILENAME, postFile.importFileName)
                        session?.setAttribute(Constants.IMPORT_FILEPATH, importFilePath)
                        importFileExists = true
                        log.info("Found original importFile from ${importFilePath}.")

                        if (postFile.pluginVersion) {
                            pluginVersion = postFile.pluginVersion
                        }

                        if (pluginVersion) {
                            PluginUpdatePrompt pluginUpdatePrompt = importMapper.upgradePrompts?.get(postFile.originatingSystem) ?: importMapper.upgradePrompts?.get(originatingSystem)

                            if (pluginUpdatePrompt) {
                                if (pluginUpdatePrompt.minAddOnVersion) {
                                    // Groovy version comparator, 0 == same version, -1 == using newer version than in minVersion, > 0 == using outdated version
                                    Boolean outdatedVersion = new VersionComparator().compare(pluginUpdatePrompt.minAddOnVersion, pluginVersion) > 0

                                    if (outdatedVersion) {
                                        if (pluginUpdatePrompt.promptLink) {
                                            outdatedPluginError = "<div class=\"alert ${pluginUpdatePrompt.color == "red" ? "alert-error" : pluginUpdatePrompt.color == "yellow" ? "alert-warning" : pluginUpdatePrompt.color == "green" ? "alert-success" : ""}\"><button data-dismiss=\"alert\" class=\"close\" type=\"button\">×</button><strong><a target=\"_blank\" href=\"${localizedLinkService.getTransformStringIdsToUrl(pluginUpdatePrompt.promptLink)}\">${pluginUpdatePrompt.localizedPromptText}</a></strong></div>"
                                        } else {
                                            outdatedPluginError = "<div class=\"alert ${pluginUpdatePrompt.color == "red" ? "alert-error" : pluginUpdatePrompt.color == "yellow" ? "alert-warning" : pluginUpdatePrompt.color == "green" ? "alert-success" : ""}\"><button data-dismiss=\"alert\" class=\"close\" type=\"button\">×</button><strong>${pluginUpdatePrompt.localizedPromptText}</strong></div>"
                                        }
                                    }
                                } else {
                                    log.error("IMPORTMAPPER MISSCONFIGURATION for upgradePrompts: ${postFile.originatingSystem}, missing minAddOnVersion")
                                    flashService.setErrorAlert("IMPORTMAPPER MISSCONFIGURATION for upgradePrompts: ${postFile.originatingSystem}, missing minAddOnVersion", true)
                                }
                            }
                        }
                    } else {
                        if (postFile && postFile.tiedToUserId && (postFile.tiedToUserId != userId)) {
                            log.error("${user?.username}: Import file is tied to another user with it: ${postFile.tiedToUserId}: ${importFilePath}")
                            flash.errorAlert = "The file is tied to another user."
                        } else {
                            log.error("${user?.username}: No import file found from database with ID: ${importFilePath}")
                            flash.errorAlert = "Error in reading the file."
                        }
                    }
                } catch (Exception e) {
                    flash.errorAlert = "Error in reading the file: ${e}"
                }
            } else {
                MultipartFile importFile

                if (request instanceof MultipartHttpServletRequest) {
                    importFile = ((MultipartHttpServletRequest) request).getFile("importFile")
                }

                if (importFile && !importFile.isEmpty()) {
                    try {
                        File resolvedFile = parseXmlFile(importFile, null, null)

                        if (!importMapper?.fileMaxSize || importMapper?.fileMaxSize >= (resolvedFile.length() / 1024)) {
                            if (resolvedFile.length() < Constants.DB_MAX_DOCUMENT_SIZE) {
                                TemporaryImportData postFile = new TemporaryImportData()
                                postFile.importFileName = importFile.name
                                postFile.ipAddress = oneClickLcaApiService.getClientIp((MultipartHttpServletRequest) request)
                                postFile.importFile = resolvedFile.getBytes()
                                postFile.importMapperId = importMapperId
                                postFile.indicatorId = indicatorId
                                postFile.userId = user?.id?.toString()
                                postFile.pluginVersion = pluginVersion
                                String fileExtension = FilenameUtils.getExtension(resolvedFile.name)

                                String extension
                                Workbook wb
                                if ("xlsx".equalsIgnoreCase(fileExtension)) {
                                    try {
                                        wb = new XSSFWorkbook(resolvedFile)
                                    } catch (Exception e) {
                                        wb = new HSSFWorkbook(new ByteArrayInputStream(resolvedFile.getBytes()))
                                        postFile.extension = Constants.XLS
                                    }
                                } else {
                                    try {
                                        wb = new HSSFWorkbook(new ByteArrayInputStream(resolvedFile.getBytes()))
                                        extension = Constants.XLS
                                    } catch (Exception e) {
                                        try {
                                            wb = new XSSFWorkbook(resolvedFile)
                                            extension = Constants.XLSX
                                        } catch (Exception ex) {}
                                    }
                                }
                                postFile.extension = extension ? extension : "xlsx".equalsIgnoreCase(fileExtension) ? Constants.XLSX : Constants.XLS
                                postFile = postFile.save(flush: true, failOnError: true)

                                if (postFile.id) {
                                    session?.setAttribute(Constants.IMPORT_FILENAME, postFile.importFileName)
                                    session?.setAttribute(Constants.IMPORT_FILEPATH, postFile.id.toString())
                                    importFileExists = true
                                }
                                if(wb){
                                    rowCounts = importMapperService.getRowsAmountFromFile(wb, session) ?: 0
                                    wb.close()
                                }
                                resolvedFile.delete()
                            } else {
                                flash.errorAlert = "Imported file is too big (${FileUtils.byteCountToDisplaySize(resolvedFile.length())}). Max size is ${FileUtils.byteCountToDisplaySize(Constants.DB_MAX_DOCUMENT_SIZE)}"
                            }
                        } else {
                            flash.errorAlert = "Imported file is too big (${resolvedFile.length() / 1024} KB). Max size is ${importMapper.fileMaxSize} KB"
                        }
                    } catch (Exception e) {
                        flash.errorAlert = "Error in reading the file: ${e}"
                    }
                }
            }
            boolean indicatorsFound = false
            boolean collapseUsed
            List<Document> entities = []
            String header
            List<ImportMapperPresetFilter> presetFilters
            String chosenFilterId
            Boolean noLicense = Boolean.FALSE
            String indicatorUse
            boolean hideConfigs
            Long fileMaxSize
            Indicator fixedIndicator

            if (importMapper.applyChannel) {
                channelFeatureService.applyChannelFeature(session, importMapper.applyChannel)
            }

            if (importMapper.indicator) {
                fixedIndicator = indicatorService.getIndicatorByIndicatorId(importMapper.indicator, Boolean.TRUE)
            }

            hideConfigs = importMapper.hideConfigs
            indicatorUse = importMapper.indicatorUse
            presetFilters = importMapperService.getPresetFilterObjects(importMapper.presetFilters)
            fileMaxSize = importMapper.fileMaxSize

            if (presetFilters && fixedIndicator) {
                chosenFilterId = presetFilters.find({
                    it.defaultForIndicators && it.defaultForIndicators.contains(fixedIndicator.indicatorId)
                })
            }
            header = importMapper.localizedImportMessage ? importMapper.localizedImportMessage : "Importing with \"${importMapper.localizedName}\""

            long now = System.currentTimeMillis()
            Boolean importAvailable = licenseService.getIsImportMapperAvailableForCurrentUser(importMapper)
            log.info("This kikkare took 1: ${System.currentTimeMillis() - now} ms")

            collapseUsed = importMapper.proposeCollapseLimit

            if (importMapper.compatibleIndicators && !importMapper.compatibleIndicators.isEmpty()) {
                indicatorsFound = true
            } else {
                flash.fadeErrorAlert = "No compatible indicators found for importMapper. Cannot continue with import."
            }

            if (!importAvailable) {
                noLicense = Boolean.TRUE
            }

            boolean importMapperFound = true
            def entityId = params.entityId
            def childEntityId = params.childEntityId
            Map userDefaults = user?.importMapperDefaults

            if (entityId && childEntityId && userDefaults) {
                userDefaults.remove(Constants.ENTITY_ID)
            }
            List<HelpConfiguration> helpConfigurations = helpConfigurationService.getConfigurationsForTargetPage('Import')
            if(session){
                session.removeAttribute(Constants.NO_LICENSE)
                session.setAttribute(Constants.NO_LICENSE, noLicense)
                session.setAttribute(Constants.IMPORT_MAPPER, importMapper)
                session.setAttribute("importMapperId", importMapper.importMapperId)
                session.setAttribute("applicationId", applicationId)
                session.setAttribute(Constants.ORIGINATING_SYSTEM, originatingSystem)
                session.setAttribute("childEntityId", childEntityId)
                session.setAttribute(Constants.ENTITY_ID,entityId)
                session.setAttribute(Constants.INDICATOR_ID, indicatorId)
                session.setAttribute("bimModelChecker", bimModelChecker)
                session.setAttribute("helpConfigurations", helpConfigurations)
            }


            redirect action: "main", params:[importMapperFound: importMapperFound, indicatorsFound: indicatorsFound, outdatedPluginError: outdatedPluginError,rowCounts: rowCounts,
             applicationId    : applicationId, importMapperId: importMapperId, importFileExists: importFileExists,
             collapseUsed     : collapseUsed, header: header, presetFilters    : presetFilters, noLicense: noLicense, indicatorUse: indicatorUse, entityId: entityId,
             childEntityId    : childEntityId, indicatorId: indicatorId, hideConfigs: hideConfigs, fileMaxSize: fileMaxSize, currentStep: 2,
             fixedIndicator   : fixedIndicator, userDefaults: userDefaults, chosenFilterId: chosenFilterId, showSteps: showSteps, bimModelChecker: bimModelChecker, helpConfigurations:helpConfigurations]
        } else {
            redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapperId]
            flash.errorAlert = errorMessage ? errorMessage : "Cannot continue IFC import. No mapper found with parameters ${applicationId} & ${importMapperId}"
        }
    }

    def importSetting() {
        boolean indicatorsFound = false
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        User user = userService.getCurrentUser()
        if (importMapper) {
            if (importMapper.compatibleIndicators && !importMapper.compatibleIndicators.isEmpty()) {
                indicatorsFound = true
            }
            List<HelpConfiguration> helpConfigurations = session?.getAttribute("helpConfigurations")
            Boolean noLicense = session?.getAttribute(Constants.NO_LICENSE)
            Boolean bimModelChecker = session?.getAttribute("bimModelChecker")
            Boolean temporaryCalculation = params.temporaryCalculation
            session?.setAttribute("temporaryCalculation",temporaryCalculation)
            boolean importFileExists = session?.getAttribute(Constants.IMPORT_FILENAME) && session?.getAttribute(Constants.IMPORT_FILEPATH)
            String indicatorId = params.indicatorId ?: session?.getAttribute(Constants.INDICATOR_ID)
            String childEntityId = params.childEntityId ?:  session?.getAttribute("childEntityId")
            String entityId = params.entityId ?: session?.getAttribute(Constants.ENTITY_ID)
            Entity childEntity = entityService.getEntityById(childEntityId,null)
            Entity entity = entityService.getEntityById(entityId,null)
            String outdatedPluginError = params.outdatedPluginError
            Boolean imperial = (UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(user?.unitSystem)||UnitConversionUtil.UnitSystem.METRIC_AND_IMPERIAL.value.equals(user?.unitSystem))
            boolean isRseeImport = params.isRseeImport
            session?.setAttribute("isRseeImport", isRseeImport)

            Indicator fixedIndicator

            if (importMapper.applyChannel) {
                channelFeatureService.applyChannelFeature(session, importMapper.applyChannel)
            }

            if (importMapper.indicator) {
                fixedIndicator = indicatorService.getIndicatorByIndicatorId(importMapper.indicator, Boolean.TRUE)
            }
            List<ImportMapperPresetFilter> presetFilters = importMapperService.getPresetFilterObjects(importMapper.presetFilters)
            String chosenFilterId
            if (presetFilters && fixedIndicator) {
                chosenFilterId = presetFilters.find({
                    it.defaultForIndicators && it.defaultForIndicators.contains(fixedIndicator.indicatorId)
                })
            }
            def ribaStages = Constants.DesignRIBAStages.map()
            ribaStages.put(-1, "entity.form.component")
            Integer currentHighestRiba = entity?.childrenByChildEntities?.findAll({ !it.id.toString().equals(entity.id.toString()) && it.ribaStage != null && !it.deleted })?.collect({ it.ribaStage })?.max()?.intValue()

            List<Entity> entities = []
            if (!noLicense) {
                entities = entityService.getModifiableEntitiesForImportMapper(importMapper)
            }
            def rowCount = params.rowCounts ?: session?.getAttribute("rowCounts")
            Map userDefaults = user?.importMapperDefaults
            def model = [importMapperFound: true, indicatorsFound: indicatorsFound, importFileExists: importFileExists, currentStep:2, fixedIndicator:fixedIndicator, presetFilters:presetFilters, rowCownts: rowCount,
                         collapseUsed     : importMapper.proposeCollapseLimit, entities: entities, ribaStages: ribaStages, noLicense:noLicense, presetFilters:presetFilters, outdatedPluginError: outdatedPluginError,
                         indicatorUse: importMapper.indicatorUse, entityId: entity?.id, entity:entity, childEntity:childEntity, showSetting:true, chosenFilterId:chosenFilterId, rowCounts: params.rowCounts,
                         childEntityId    : childEntityId, indicatorId: indicatorId, hideConfigs: importMapper.hideConfigs, currentHighestRiba: currentHighestRiba,imperial:imperial,
                         showSteps: !bimModelChecker, bimModelChecker: bimModelChecker, helpConfigurations:helpConfigurations, userDefaults: userDefaults, isRseeImport: isRseeImport]
            return model
        } else {
            flash.fadeErrorAlert = "No compatible indicators found for importMapper. Cannot continue with import."
        }
    }

    def createDatasets() {
        String applicationId = session?.getAttribute("applicationId")
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        String entityId = params.entityId
        String childEntityId = params.childEntityId

        String indicatorId = params.indicatorId
        List stepsManual = params.stepsSetting?.tokenize(",")
        Boolean temporaryCalculation = session?.getAttribute("temporaryCalculation")

        Boolean bimModelChecker = session?.getAttribute("bimModelChecker")
        Boolean removeEmptyData = params.boolean("removeEmptyData")
        Boolean incrementalImport = params.boolean("incrementalImport")
        Boolean reviewData = stepsManual?.contains("6")
        Boolean combineManually = stepsManual?.contains("5")
        Boolean filterManually = stepsManual?.contains("4")
        Boolean convertManually = stepsManual?.contains("3")
        Boolean updateQuant = stepsManual?.contains("9")
        session?.setAttribute("incrementalImport", incrementalImport)
        session?.setAttribute("removeEmptyData",removeEmptyData)
        session?.setAttribute("combineManually",combineManually)
        session?.setAttribute("combineAuto",!combineManually)
        session?.setAttribute("dataReview",reviewData)
        session?.setAttribute("filterManually",filterManually)
        session?.setAttribute("updateQuant",updateQuant)
        if(!stepsManual?.contains("7")){
            session?.setAttribute("skipMapping", Boolean.TRUE)
        }


        Boolean convertToMetric = params.boolean('convertToMetric')
        Boolean convertToImperial = params.boolean('convertToImperial')

        String newDesignName = params.name?.replaceAll(Constants.USER_INPUT_REGEX,"")

        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        if (temporaryImportData && ((childEntityId ||newDesignName) && entityId) || temporaryCalculation) {
            log.info("Started creating datasets: User: ${userService.getCurrentUser(true)?.username} file:${temporaryImportData.importFileName}")

            File tempFile = File.createTempFile(UUID.randomUUID().toString(), temporaryImportData.extension)
            FileUtils.writeByteArrayToFile(tempFile, temporaryImportData.importFile)

            String exception
            Workbook wb
            try {
                if (Constants.XLSX.equals(temporaryImportData?.extension)) {
                    wb = new XSSFWorkbook(tempFile)
                } else {
                    wb = new HSSFWorkbook(new ByteArrayInputStream(tempFile.getBytes()))
                }
            } catch (Exception e) {
                exception = e.getMessage()
            }
            tempFile.delete()

            if (!wb) {
                flash.errorAlert = exception ? exception : message(code: 'importMapper.file_type.invalid')
                redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
            } else {
                if (importMapper && wb) {
                    Boolean useAdaptiveRecognition = Boolean.FALSE
                    Boolean trainAdaptiveRecognition = Boolean.FALSE
                    String newDesignRibaStage = params.ribaStage
                    Boolean designNameError = Boolean.FALSE
                    if (temporaryCalculation) {
                        Entity tempEntity = new Entity()
                        tempEntity.name = "Temporary Calculation"
                        tempEntity.entityClass = Constants.EntityClass.DESIGN.toString()
                        tempEntity.indicatorIds = ["${params.indicatorId}"]
                        temporaryImportData.temporaryEntity = tempEntity
                    } else {
                        temporaryImportData.parentEntityId = params.entityId
                        if ("newDesign".equals(childEntityId) || "newPeriod".equals(childEntityId)) {
                            String sanitizedName = newDesignName

                            if (!designAlreadyFound(sanitizedName, temporaryImportData.parentEntityId)) {
                                temporaryImportData.childEntityName = sanitizedName ?: message(code: "entity.new_design")
                            } else {
                                designNameError = Boolean.TRUE
                            }
                        } else {
                            temporaryImportData.childEntityName = entityService.getEntityById(childEntityId)?.operatingPeriodAndName
                            session?.removeAttribute("childEntityId")
                            session?.removeAttribute(Constants.ENTITY_ID)
                            session?.setAttribute("childEntityId",childEntityId)
                            session?.setAttribute(Constants.ENTITY_ID,entityId)
                        }
                        entityService.addIndicatorForEntity(params.entityId, params.indicatorId)
                    }

                    if (!designNameError) {
                        if (newDesignName && !temporaryCalculation) {
                            String sanitizedName = newDesignName
                            session?.setAttribute("newDesign", sanitizedName ?: message(code: "entity.new_design"))
                            session?.setAttribute("newDesignRibaStage", newDesignRibaStage)
                        }

                        if ("newDesign".equals(childEntityId) || "newPeriod".equals(childEntityId)) {
                            childEntityId = null
                        }

                        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                        session?.setAttribute("indicator", indicator)
                        session?.setAttribute(Constants.INDICATOR_ID, indicatorId)

                        if (importMapper?.allowAdaptiveRecognition) {
                            session?.setAttribute("useAdaptiveRecognition", Boolean.TRUE)
                            session?.setAttribute("trainAdaptiveRecognition", Boolean.TRUE)
                            useAdaptiveRecognition = Boolean.TRUE
                            trainAdaptiveRecognition = Boolean.TRUE
                        }
                        String filterId = params.filters
                        ImportMapperPresetFilter presetFilter
                        importMapperService.setPresetFilters(importMapper, indicatorId, session, presetFilter, filterId)

                        try {
                            Boolean useOriginalClass = temporaryCalculation
                            long now = System.currentTimeMillis()
                            boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
                            def importMapperReturn
                            if (useOldImportMapper) {
                                importMapperReturn = importMapperService.oldImportDatasetsByImportMapper(importMapper, entityId, wb, useAdaptiveRecognition,
                                        trainAdaptiveRecognition, presetFilter, removeEmptyData, temporaryCalculation, useOriginalClass,Boolean.FALSE,indicatorId,childEntityId)
                                importMapperReturn.discardedValuesWithAmount = importMapperReturn.get("discardedValues")
                            } else {
                                importMapperReturn = importMapperService.importDatasetsByImportMapper(importMapper, entityId, wb, useAdaptiveRecognition,
                                        trainAdaptiveRecognition, presetFilter, removeEmptyData, temporaryCalculation, useOriginalClass,Boolean.FALSE, indicatorId, childEntityId, updateQuant)
                            }
                            log.info("Time it took to importDatasetsByImportMapper: ${System.currentTimeMillis() - now} ms")

                            if (importMapperReturn) {
                                List<Dataset> okDatasets = importMapperReturn.okDatasets
                                List<Dataset> tooGenericDatasets = importMapperReturn.tooGenericDatasets
                                List<Dataset> rejectedDatasets = importMapperReturn.rejectedDatasets
                                List<Dataset> rejectedAndOkDatasets = importMapperReturn.rejectedAndOkDatasets
                                if (rejectedAndOkDatasets) {
                                    session?.setAttribute("rejectedAndOkDatasets", rejectedAndOkDatasets)
                                }
                                List<String> headers = importMapperReturn.headers
                                Map removedByPresetFilter = importMapperReturn.removedByPresetFilter
                                String missingMandatoryDataError = importMapperReturn.missingMandatoryDataError
                                String materialHeading = importMapperReturn.materialHeading
                                session?.setAttribute("materialHeading", materialHeading)

                                Map<String, Integer> amountOfGroupedByData = [:]
                                if (okDatasets) {
                                    importMapperService.convertDatasetsToUnitSystem(okDatasets, convertToMetric,convertToImperial)
                                    String allowDroppingDataGroupedBy = importMapper.allowDroppingDataGroupedBy?.toUpperCase()
                                    amountOfGroupedByData = importMapperService.getAmountOfGroupedByData(okDatasets, allowDroppingDataGroupedBy)
                                }

                                List<ImportMapperCollapseGrouper> collapseGroupers = importMapperReturn.collapseGroupers
                                def skippedRows = importMapperReturn.skippedRows
                                Map discardedValuesWithAmount = importMapperReturn.discardedValuesWithAmount
                                int skippedOrRejected = 0
                                Map emptyRows = importMapperReturn.emptyRows

                                if (rejectedDatasets) {
                                    skippedOrRejected = rejectedDatasets.size()
                                }

                                if (skippedRows) {
                                    skippedOrRejected = skippedOrRejected + skippedRows
                                }

                                String discardedMaterialsWithValues = ""

                                if (discardedValuesWithAmount) {
                                    int discardedByRemovalRulesets = 0
                                    discardedValuesWithAmount.each { String material, Integer amount ->
                                        discardedMaterialsWithValues = "${discardedMaterialsWithValues}${material} - ${amount} ${message(code: "instances")}<br />"
                                        discardedByRemovalRulesets = discardedByRemovalRulesets ? discardedByRemovalRulesets + amount : amount
                                    }
                                    session?.setAttribute("discardedByRemovalRulesets", discardedByRemovalRulesets)
                                }

                                String discardedEmptyClassAndIfcMaterial

                                if (emptyRows) {
                                    discardedEmptyClassAndIfcMaterial = "${message(code: "importMapper.class_and_ifcmaterial_null")} - ${emptyRows.get("emptyRows")} ${message(code: "instances")}<br />"
                                }



                                int dataAmount = okDatasets ? okDatasets.size() : 0
                                Integer amount = (Integer) removedByPresetFilter.get("removedByPresetFilter") ?: 0

                                if (!"manual".equals(presetFilter?.presetFilterId)) {
                                    session?.setAttribute("filterRemovedSize", amount)
                                } else {
                                    filterManually = true
                                }
                                if (temporaryImportData.importDataPerStep) {
                                    temporaryImportData.importDataPerStep.put(2: temporaryImportData.rowCounts,4:dataAmount)

                                } else {
                                    temporaryImportData.importDataPerStep = [2: temporaryImportData.rowCounts,4:dataAmount]
                                }
                                if(session){
                                    session.removeAttribute("ifcDatasets")
                                    session.removeAttribute("rejectedIfcDatasets")
                                    session.setAttribute("ifcDatasets", okDatasets)
                                    session.setAttribute("rejectedIfcDatasets", rejectedDatasets)
                                    if (tooGenericDatasets && !tooGenericDatasets.isEmpty()) {
                                        session.removeAttribute("tooGenericDatasets")
                                        session.setAttribute("tooGenericDatasets", tooGenericDatasets)
                                    }
                                    session.setAttribute("collapseGroupers", collapseGroupers)
                                    session.setAttribute("discardedMaterialsWithValues", discardedMaterialsWithValues)
                                    session.setAttribute("skippedOrRejected", skippedOrRejected)
                                    session.setAttribute("emptyRows", discardedEmptyClassAndIfcMaterial)
                                    session.setAttribute("headers", headers)
                                    session.removeAttribute("filterManually")
                                    session.setAttribute("filterManually",filterManually)
                                }

                                Integer proposeCollapseLimit = importMapper.proposeCollapseLimit
                                def rows = okDatasets ? okDatasets.size() : 0
                                rows = rows + (rejectedDatasets ? rejectedDatasets.size() : 0)
                                def requiredParams = [entityId: entityId, childEntityId: childEntityId]
                                def combineAuto = session?.getAttribute("combineAuto")

                                if (okDatasets && !okDatasets.isEmpty() && !childEntityId && newDesignName && !temporaryCalculation) {
                                    Entity child = new Entity([entityId: params.entityId, childEntityId: childEntityId, bimModelChecker: bimModelChecker,])
                                    Entity parentEntity = Entity.read(entityId)
                                    child.parentEntityId = parentEntity.id
                                    child.parentName = parentEntity.name

                                    ScopeToSave scope = new ScopeToSave(params)
                                    child.scope = scope
                                    child = scopeFactoryService.getServiceImpl(child?.entityClass).addIndicatorsToEntity(params, child, parentEntity)


                                    if ("operating".equals(importMapper?.indicatorUse)) {
                                        String operatingPeriod = newDesignName.substring(newDesignName?.indexOf(" ", 4) + 1).substring(0, 4)
                                        child.operatingPeriod = operatingPeriod
                                        child.entityClass = Constants.EntityClass.OPERATING_PERIOD.toString()
                                    } else {
                                        Integer ribaStage = session?.getAttribute("newDesignRibaStage")?.toString()?.toInteger()

                                        if (-1 == ribaStage) {
                                            child.component = Boolean.TRUE
                                        } else {
                                            child.ribaStage = ribaStage
                                        }
                                        child.name = newDesignName
                                        child.entityClass = Constants.EntityClass.DESIGN.toString()
                                    }
                                    child = entityService.createEntity(child)
                                    childEntityId = child?.id?.toString()
                                    session?.removeAttribute("newDesign")
                                    session?.removeAttribute("childEntityId")
                                    session?.removeAttribute(Constants.ENTITY_ID)
                                    session?.setAttribute(Constants.ENTITY_ID, entityId)
                                    session?.setAttribute("childEntityId", childEntityId)
                                    requiredParams.put("childEntityId", childEntityId)
                                }

                                String warningMessage = importMapperReturn.warningMessage
                                if (warningMessage && !warningMessage.isEmpty()) {
                                    session?.setAttribute("warningMessage", warningMessage)
                                }
                                Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, importMapper.skipCombineRowLimit, session)
                                flash.errorAlert = importMapperReturn.errorMessage

                                if (!okDatasets && missingMandatoryDataError) {
                                    flash.errorAlert = "${"Parsed ${session?.getAttribute(Constants.FILE_EXTENSION) ?: "Excel or gbXML"} file did not contain valid material definitions. Please verify the file"}<br/>${missingMandatoryDataError}"
                                    redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
                                } else if (okDatasets) {
                                    if (amountOfGroupedByData && !amountOfGroupedByData.isEmpty() && importMapper.allowDroppingDataGroupedBy && (filterManually || combineManually || reviewData || convertManually)) {
                                        session?.setAttribute("amountOfGroupedByData", amountOfGroupedByData)
                                        if(convertManually){
                                            redirect action: "convertData", params: requiredParams
                                        } else if (filterManually) {
                                            redirect action: "groupedBy", params: requiredParams
                                        } else if (combineManually) {
                                            redirect action: "combiner", params: requiredParams
                                        } else if (reviewData){
                                            redirect action: "dataReview", params: requiredParams
                                        }
                                    } else if (skipCombineRowLimit && skipCombineRowLimit < okDatasets.size() && !combineAuto) {
                                        flash.fadeErrorAlert = message(code: "importMapper.too_many_rows", args: [skipCombineRowLimit])
                                        redirect action: "combiner", params: requiredParams
                                    } else {
                                        if (proposeCollapseLimit && rows >= proposeCollapseLimit && collapseGroupers && !combineAuto) {
                                            redirect action: "combiner", params: requiredParams
                                        } else if (proposeCollapseLimit && rows >= proposeCollapseLimit && collapseGroupers && combineAuto) {
                                            redirect action: "combineAll", params: requiredParams
                                        } else {
                                            if (temporaryImportData.importDataPerStep) {
                                                temporaryImportData.importDataPerStep.put(5, okDatasets ? okDatasets.size() : 0)
                                            }
                                            flashService.setReinitForTheNextRequest()
                                            redirect action: "recognizeDatasets", params: requiredParams
                                        }
                                    }
                                } else {
                                    flash.errorAlert = "${message(code: 'importMapper.no_data')}"
                                    redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
                                }
                            } else {
                                flash.errorAlert = "${message(code: 'importMapper.no_data')}"
                                redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
                            }
                        } catch (Exception e) {
                            loggerUtil.error(log, "Exception is:", e)
                            flash.errorAlert = message(code: 'importMapper.file_type.invalid')
                            redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
                        }
                    } else {
                        flash.errorAlert = message(code: "design.duplicate")
                        redirect action: "main", params: [applicationId: params.applicationId, importMapperId: params.importMapperId,bimModelChecker:bimModelChecker, entityId: params.entityId]
                    }
                } else {
                    flash.errorAlert = "Something bad happened. No importMapper found from session."
                    redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
                }
            }
        } else {
            redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId]
        }
    }
    def convertData(){
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        String applicationId = session?.getAttribute("applicationId")
        String importMapperId = session?.getAttribute("importMapperId")
        if(importMapper){
            List<HelpConfiguration> helpConfigurations = session?.getAttribute("helpConfigurations")
            Boolean bimModelChecker = session?.getAttribute("bimModelChecker")
            Boolean noLicense = session?.getAttribute(Constants.NO_LICENSE)
            boolean importFileExists = session?.getAttribute(Constants.IMPORT_FILENAME) && session?.getAttribute(Constants.IMPORT_FILEPATH)
            String indicatorId = session?.getAttribute(Constants.INDICATOR_ID)
            String childEntityId = session?.getAttribute("childEntityId")
            String entityId = session?.getAttribute(Constants.ENTITY_ID)
            Entity entity = entityService.getEntityById(entityId,null)
            Entity childEntity = entityService.getEntityById(childEntityId,null)
            Boolean showSteps = bimModelChecker ? Boolean.FALSE : importMapper.showSteps
            Boolean showCategoryCol = session?.getAttribute("headers")?.contains("CATEGORY")
            def rejectedAndOkDatasets = session?.getAttribute("ifcDatasets")
            Map<Integer, List<String>> classToRemap = [:]

            Integer i = 1 // group index
            rejectedAndOkDatasets?.each { Dataset d ->
                def datasetClass = d.trainingData?.get("CLASS")?.toString()?.toUpperCase()
                if (showCategoryCol) {
                    if (!classToRemap.find({it.value && it.value[4]?.toString()?.toUpperCase()?.equals(datasetClass) && it.value[3]?.toString()?.equals(d.categoryMaterialImport)})) {
                        classToRemap.put(i, [d.originalClass ?: d.persistedOriginalClass, d.queryId, d.sectionId, d.categoryMaterialImport, datasetClass, importMapperService.isResourceDataset(d)])
                        i++
                    }
                } else {
                    if (!classToRemap.find({it.value && it.value[4]?.toString()?.toUpperCase()?.equals(datasetClass)})) {
                        classToRemap.put(i, [d.originalClass ?: d.persistedOriginalClass, d.queryId, d.sectionId, d.categoryMaterialImport, datasetClass, importMapperService.isResourceDataset(d)])
                        i++
                    }
                }
            }

            def sheets = session?.getAttribute("sheets")

            List rules = importMapper.questionMappingRules
            ClassRemapping classRemapChoice = entity?.entityClass ? importMapper.allowedClassRemappingsSets?.find({entity?.entityClass?.equals(it.remappingEntityClass)}) : null
            List<String> classesAvailable = []
            List<String> descriptiveClassesAvailable = []
            if(classRemapChoice){
                if (classRemapChoice.allowedClassRemappings) {
                    classesAvailable.addAll(classRemapChoice?.allowedClassRemappings)
                }
                if (classRemapChoice.allowedDescriptiveClassRemappings) {
                    descriptiveClassesAvailable.addAll(classRemapChoice.allowedDescriptiveClassRemappings)
                }
            }
            List mapClassToQueryName = []
            /*Map unusualMapping = importMapper.questionMappingSpecialCaseTranslations?.collectEntries({[(it.applyValue):it.matchingValues]})
            Map usualMapping = importMapper.questionMappingClassTranslations?.collectEntries({[(it.applyValue):it.matchingValues]})
            List<String> unusualName = unusualMapping?.keySet() as List<String>
            List<String> usualName = usualMapping?.keySet() as List<String>
            if(unusualName) {
                classesAvailable.addAll(unusualName)
            }
            if(usualName){
                classesAvailable.addAll(usualName)
            }*/
            rules.each { def rule ->
                String queryId = rule?.target?.get("queryId")
                String sectionId = rule?.target?.get("sectionId")
                Query query = queryService.getQueryByQueryId(queryId)
                if(query){
                    def sectionLocalizedName = query.getSections().find({it.sectionId?.equalsIgnoreCase(sectionId)})?.localizedName

                    rule.matchingValues.each { String value ->
                        String nameQuery = query.localizedName + " > " + sectionLocalizedName
                        def map = [value,nameQuery]
                        mapClassToQueryName.add(map)
                    }
                }
            }

            TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
            [importFileExists:importFileExists,noLicense:noLicense,bimModelChecker:bimModelChecker,entityId:entityId,mapClassToQueryName: mapClassToQueryName,
             showSteps:showSteps,currentStep: 3,classRemapChoice:classRemapChoice,showCategoryCol:showCategoryCol,temporaryImportData:temporaryImportData,
             entityId: entityId,entity:entity,childEntity:childEntity, childEntityId : childEntityId, indicatorId: indicatorId,classesAvailable:classesAvailable,
             descriptiveClassesAvailable: descriptiveClassesAvailable, rejectedAndOkDatasets:rejectedAndOkDatasets,sheets:sheets,importMapper:importMapper,classToRemap:classToRemap]
        } else {
            flash.errorAlert = "Cannot continue IFC import. No mapper found with parameters ${applicationId} & ${importMapperId}. Session might be expired"
            redirect action: "main", params: [applicationId:applicationId,importMapperId:importMapperId]
        }
    }
    def dataReview() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        def entityId = session?.getAttribute(Constants.ENTITY_ID)
        def childEntityId = session?.getAttribute("childEntityId")
        Entity entity = entityService.getEntityByIdReadOnly(childEntityId.toString())
        Entity parentEntity = entityService.getEntityByIdReadOnly(entityId.toString())
        String trainingDataCountry = parentEntity?.country
        String entityClass = parentEntity?.entityClass
        Indicator indicator = session?.getAttribute("indicator")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        List<Resource> resources
        Boolean noLicense = session?.getAttribute(Constants.NO_LICENSE)
        if (importMapper) {
            ClassRemapping classRemapChoice = entityClass ? importMapper.allowedClassRemappingsSets?.find({entityClass.equals(it.remappingEntityClass)}) : null
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")

            Boolean temporaryCalculation = session?.getAttribute("temporaryCalculation")
            String defaultQueryId

            if (temporaryCalculation) {
                defaultQueryId = importMapper?.questionMappingRules?.find({
                    it.defaultTargetForEntityClass?.contains("building")
                })?.target?.get("queryId")
            } else {
                defaultQueryId = importMapper?.questionMappingRules?.find({
                    it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass)
                })?.target?.get("queryId")
            }
            List<String> displayFields = importMapper?.displayFields
            List<String> unitIdentifyingField = importMapper.unitIdentifyingFields ?: importMapper.unitIdentifyingField ? [importMapper.unitIdentifyingField] : null
            Query query = queryService.getQueryByQueryId(defaultQueryId, Boolean.TRUE)
            List<Question> questions = query?.getAllQuestions()
            List<Question> additionals = []
            questions?.each { Question q ->
                List<Question> additionalQuestions = q.getAdditionalQuestions(indicator, null, null, parentEntity)

                if (additionalQuestions) {
                    additionals.addAll(additionalQuestions)
                }
            }
            additionals = additionals.unique({it.questionId})

            List<Question> resolvedAdditionalQuestions = []
            additionals.each { Question q ->
                if (!"comment".equals(q.questionId)) {
                    if (q.questionId == "serviceLife") {
                        if (!parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)) {
                            if (q.showInMapping) {
                                resolvedAdditionalQuestions.add(q)
                            } else {
                                resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                            }
                        }
                    } else if (q.isTransportQuestion) {
                        if (!parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)) {
                            if (q.showInMapping) {
                                resolvedAdditionalQuestions.add(q)
                            } else {
                                resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                            }
                        }
                    } else if (q?.privateClassificationQuestion) {
                        resolvedAdditionalQuestions.add(q)
                    } else {
                        if (q.showInMapping) {
                            resolvedAdditionalQuestions.add(q)
                        } else {
                            resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                        }
                    }
                }
            }

            List<Dataset> tooGenericDatasets = session?.getAttribute("tooGenericDatasets")
            Boolean expandNotIdentified = Boolean.FALSE

            String applicationId = session?.getAttribute("applicationId")
            String detailsKey = session?.getAttribute("materialHeading")

            if (!detailsKey) {
                if (importMapper.materialHeading) {
                    detailsKey = importMapper.materialHeading
                } else {
                    detailsKey = "IFCMATERIAL"
                }
            }
            String bimcheckerUrl = "${controllerName}/bimChecker"

            List<Dataset> resourceDatasets = []
            List<Dataset> descriptiveDatasets = []

            datasets.each { Dataset dataset ->
                if (importMapperService.isResourceDataset(dataset)) {
                    resourceDatasets << dataset
                } else {
                    descriptiveDatasets << dataset
                }
            }


                [entity                       : entity, parentEntity: parentEntity, resources: resources, questions: questions, entityId: entityId, showSteps: importMapper?.showSteps,currentStep:6,
                 childEntityId                : childEntityId, indicator: indicator, queryId: defaultQueryId, displayFields: displayFields,resourceDatasets:resourceDatasets,descriptiveDatasets:descriptiveDatasets,bimcheckerUrl:bimcheckerUrl,
                 unitIdentifyingField         : unitIdentifyingField, detailsKey: detailsKey, trainingDataCountry: trainingDataCountry,
                 importMapperId               : importMapper.importMapperId, applicationId: applicationId, query: query, temporaryImportData: temporaryImportData,
                 denyMappingDatasets          : tooGenericDatasets, datasetSize: datasets?.size(), classRemapChoice: classRemapChoice,
                 expandNotIdentified: expandNotIdentified, additionalQuestions: resolvedAdditionalQuestions,noLicense:noLicense]
        } else {
            redirect action: "main", params: [errorMessage: "Could not find importMapper with given parameters"]
        }
    }

    def downloadExcelFile() {
        String temporaryImportDataId = session?.getAttribute(Constants.IMPORT_FILEPATH)
        List <BimModelWarning> warnings = (List<BimModelWarning>) session?.getAttribute("bimWarnings") ?: []
        List <String> nonLocalizedHeaders = session?.getAttribute("bimWarningHeaders")
        List <String> bimheaders = []
        if(nonLocalizedHeaders){
            nonLocalizedHeaders.each {String header ->
                String localizedHeader = "${message(code: header)}"
                bimheaders.push(localizedHeader)
            }
        }


        TemporaryImportData postFile

        if (temporaryImportDataId) {
            postFile = TemporaryImportData.findById(DomainObjectUtil.stringToObjectId(temporaryImportDataId))
        }

        if (postFile) {
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), postFile.extension)
            FileUtils.writeByteArrayToFile(tempFile, postFile.importFile)

            String exception
            Workbook wb
            try {
                if (Constants.XLSX.equals(postFile.extension)) {
                    wb = new XSSFWorkbook(tempFile)
                } else {
                    wb = new HSSFWorkbook(new ByteArrayInputStream(tempFile.getBytes()))
                }
            } catch (Exception e) {
                exception = e.getMessage()
            }
            tempFile.delete()

            if (warnings && !warnings.isEmpty() && bimheaders && !bimheaders.isEmpty() && wb) {
                List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
                List<Dataset> datasets = session?.getAttribute("rejectedAndOkDatasets") ?: []

                if (datasets && !datasets.isEmpty()) {
                    List<String> datasetHeaders = ["Resource name", "Material", "Quantity", "Share", "CLASS", "Note", "Type", "Issues"]
                    ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
                    String detailsKey = session?.getAttribute("materialHeading")

                    if (!detailsKey) {
                        if (importMapper.materialHeading) {
                            detailsKey = importMapper.materialHeading
                        } else {
                            detailsKey = "IFCMATERIAL"
                        }
                    }
                    wb = importMapperService.injectBimModelWarningsToExcel(warnings, bimheaders, datasets, datasetHeaders, detailsKey, wb, ifcDatasets)
                }
            }

            try {
                if (Constants.XLSX.equals(postFile.extension)) {
                    response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                } else {
                    response.contentType = 'application/vnd.ms-excel'
                }
                response.setHeader("Content-disposition", "attachment; filename=OneClickLCA_${new Date().format('dd-MM-yyyy HH:mm:ss')}${postFile.extension}")
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (ClientAbortException|OpenXML4JRuntimeException|OpenXML4JException e) {
                // client disconnected before bytes were able to be written.
                log.warn("ImportMapper downloadExcelFile() failed: ${e}")
            }
        } else {
            render(text: "Excel not found")
        }
    }

    def generateExcelFromDatasets() {
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        List<String> headers = session?.getAttribute("headers")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        String returnUserTo = params.returnUserTo

        if (ifcDatasets && headers) {
            Workbook wb = importMapperService.generateExcel(ifcDatasets, headers, importMapper)

            if (wb) {
                try {
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=importMapper_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                } catch (ClientAbortException e) {
                    // client disconnected before bytes were able to be written.
                }
            } else if (returnUserTo) {
                flash.fadeErrorAlert = "Could not generate Excel from datasets"
                chain(action: returnUserTo, params: [entityId: params.entityId, childEntityId: params.childEntityId])
            }
        } else if (returnUserTo) {
            flash.fadeErrorAlert = "Could not generate Excel from datasets"
            chain(action: returnUserTo, params: [entityId: params.entityId, childEntityId: params.childEntityId])
        }
    }

    def generateExcelFromTempCalculationResult() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        Indicator indicator = session?.getAttribute("indicator")

        if (temporaryImportData && temporaryImportData.temporaryEntity?.tempCalculationResults && indicator && ifcDatasets) {
            try {
                User user = userService.getCurrentUser()
                Workbook wb = importMapperService.generateExcelForResults(ifcDatasets, temporaryImportData.temporaryEntity.tempCalculationResults, temporaryImportData.temporaryEntity, indicator, user, session)

                if (wb) {
                    response.contentType = 'application/vnd.ms-excel'
                    response.setHeader("Content-disposition", "attachment; filename=temporaryCalculationResults_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                }
            } catch (Exception e) {
                flash.fadeErrorAlert = "No results found!"
                log.error("Could not create Excel for user temporary CalculationResults: ${e}")
                redirect action: "temporaryCalculations"
            }
        } else {
            flash.fadeErrorAlert = "No results found!"
            redirect action: "temporaryCalculations"
        }
    }

    def designAlreadyFound(String designName, String parentEntityId) {
        boolean alreadyFound = false

        if (designName && parentEntityId) {
            Entity parent = entityService.getEntityById(parentEntityId)

            if (parent?.getDesigns()?.find({ it.name == designName })) {
                alreadyFound = true
            }
        }
        return alreadyFound
    }

    def fileToSession() {
        long start = System.currentTimeMillis()
        MultipartFile importFile = fileUtil.getFileFromRequest(request)
        Integer rowCounts = params.int("rowCounts") ?: 0
        Boolean alreadyInSession = (session?.getAttribute(Constants.IMPORT_FILENAME) && session?.getAttribute(Constants.IMPORT_FILEPATH))
        Boolean bimModelChecker = params.boolean('bimModelChecker')
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        TemporaryImportData temporaryImportData
        if (importMapper) {
            User user = userService.getCurrentUser()
            String indicatorId = params.indicatorId
            Date now = new Date()

            if (alreadyInSession) {
                String importFilePath = (String) session?.getAttribute(Constants.IMPORT_FILEPATH)
                temporaryImportData = TemporaryImportData.findById(DomainObjectUtil.stringToObjectId(importFilePath))
                rowCounts = temporaryImportData?.rowCounts ?: session?.getAttribute("rowCounts") ?: 0
                session?.setAttribute(Constants.FILE_EXTENSION, temporaryImportData.extension)
            } else {
                temporaryImportData = new TemporaryImportData()
                String extension = "xlsx".equalsIgnoreCase(FilenameUtils.getExtension(importFile?.originalFilename)) ? Constants.XLSX : Constants.XLS
                session?.setAttribute(Constants.FILE_EXTENSION, extension)
                temporaryImportData.extension = extension
                temporaryImportData.importFileName = importFile?.originalFilename
                temporaryImportData.originatingSystem = session?.getAttribute(Constants.ORIGINATING_SYSTEM) ? session?.getAttribute(Constants.ORIGINATING_SYSTEM).toString() : Constants.MANUAL
            }
            temporaryImportData.importMapperId = importMapper.importMapperId
            temporaryImportData.indicatorId = indicatorId
            temporaryImportData.userId = user?.id?.toString()

            String childEntityId = params.childEntityId
            String filterId = params.filters

            ImportMapperPresetFilter presetFilter
            importMapperService.setPresetFilters(importMapper, indicatorId, session, presetFilter, filterId)

            if (params.entityId || params.indicatorId) {
                importMapperService.updateUserDefaults(params.entityId, params.indicatorId, user)
            }

            Boolean fileHandlingOk = alreadyInSession
            Boolean tooManyColumns = Boolean.FALSE

            if (!fileHandlingOk) {
                File tempFile = parseXmlFile(importFile, null, null)

                if (tempFile) {
                    if (!importMapper.fileMaxSize || importMapper.fileMaxSize >= (tempFile.length() / 1024)) {
                        Workbook wb
                        if (Constants.XLSX.equals(temporaryImportData.extension)) {
                            try {
                                wb = importMapperService.xlsxFileParser(tempFile, importMapper.maxColumns)
                            } catch (IndexOutOfBoundsException e) {
                                tooManyColumns = Boolean.TRUE
                            } catch (Exception e) {
                                wb = new HSSFWorkbook(new ByteArrayInputStream(tempFile.getBytes()))
                                temporaryImportData.extension = Constants.XLS
                            }
                        } else {
                            try {
                                ExcelUtil.validateMaxColumnConstrain(tempFile)
                                wb = new HSSFWorkbook(new ByteArrayInputStream(tempFile.getBytes()))
                            } catch (IndexOutOfBoundsException e) {
                                tooManyColumns = Boolean.TRUE
                            } catch (Exception e) {
                                try {
                                    wb = importMapperService.xlsxFileParser(tempFile, importMapper.maxColumns)
                                    temporaryImportData.extension = Constants.XLSX
                                } catch (IndexOutOfBoundsException index) {
                                    tooManyColumns = Boolean.TRUE
                                } catch (Exception ex) {
                                    log.error(ex.getMessage())
                                }
                            }
                        }

                        if (wb && tempFile) {
                            rowCounts = importMapperService.getRowsAmountFromFile(wb) ?: 0
                            temporaryImportData.rowCounts = rowCounts
                            wb.close()
                            if (importFile.getSize() < Constants.DB_MAX_DOCUMENT_SIZE) {
                                temporaryImportData.importFile = tempFile.getBytes()
                                fileHandlingOk = true
                            } else {
                                log.error("User: ${user?.username} imported a file that exceeds the database MaxDocumentSize, unable to save original file.")
                                flashService.setErrorAlert("User: ${user?.username} imported a file that exceeds the database MaxDocumentSize, unable to save original file.", true)
                            }
                            session?.setAttribute(Constants.IMPORT_FILENAME, importFile.name)
                        }
                    }
                    tempFile.delete()
                }
            }
            if (fileHandlingOk) {
                if (temporaryImportData.id) {
                    temporaryImportData = temporaryImportData.merge(flush: true)
                } else {
                    temporaryImportData = temporaryImportData.save(flush: true)
                    session?.setAttribute(Constants.IMPORT_FILEPATH, temporaryImportData.id.toString())
                }
                session?.setAttribute(Constants.TEMPORARY_IMPORT_DATA, temporaryImportData)
                log.info("Time it took to put importfile to session & handle params: ${System.currentTimeMillis() - start} ms")
                //params << [entityId: params.entityId, childEntityId: childEntityId, bimModelChecker: bimModelChecker,rowCounts:rowCounts]
                redirect action: "importSetting", params: [temporaryCalculation: params.temporaryCalculation, entityId: params.entityId, childEntityId: childEntityId,
                                                           bimModelChecker: bimModelChecker,rowCounts:rowCounts, indicatorId: params.indicatorId, outdatedPluginError: params.outdatedPluginError]
            } else {
                if (tooManyColumns) {
                    flash.errorAlert = "${message(code: "importMapper.maxColumns_exception")} ${importMapper.maxColumns ?: Constants.MAX_COLUMNS}"
                } else {
                    flash.errorAlert = message(code: "importMapper.file_exception")
                }
                redirect action: "main", params: [applicationId: params.applicationId, importMapperId: params.importMapperId, bimModelChecker:bimModelChecker]
            }
        } else {
            flash.errorAlert = message(code: "importMapper.choose_targets")
            redirect action: "main", params: [applicationId: params.applicationId, importMapperId: params.importMapperId,bimModelChecker:bimModelChecker]
        }
    }

    /**
     * Special path for automatic import from RSET parameters page
     * @return
     */
    def rseeFileToSession() {
        removeAllSessionAttributes(session)
        long start = System.currentTimeMillis()
        Integer rowCounts = 0
        String importMapperId = params.importMapperId
        String applicationId = params.applicationId
        String rseeZoneMapping = params.rseeZoneMapping
        TemporaryImportData temporaryImportData
        Application application = applicationService.getApplicationByApplicationId(applicationId)
        ImportMapper importMapper = application?.importMappers.find{ importMapperId.equals(it.importMapperId) }
        Boolean noLicense = !licenseService.getIsImportMapperAvailableForCurrentUser(importMapper)

        if (importMapper) {
            User user = userService.getCurrentUser()
            String indicatorId = params.indicatorId
            Date now = new Date()
            temporaryImportData = new TemporaryImportData()
            session?.setAttribute(Constants.FILE_EXTENSION, Constants.XLS)
            temporaryImportData.extension = Constants.XLS
            temporaryImportData.originatingSystem = session?.getAttribute(Constants.ORIGINATING_SYSTEM) ? session?.getAttribute(Constants.ORIGINATING_SYSTEM).toString() : Constants.MANUAL
            temporaryImportData.importMapperId = importMapper.importMapperId
            temporaryImportData.indicatorId = indicatorId
            temporaryImportData.userId = user?.id?.toString()

            String childEntityId = params.childEntityId

            ImportMapperPresetFilter presetFilter
            importMapperService.setPresetFilters(importMapper, indicatorId, session, presetFilter)

            if (params.entityId || params.indicatorId) {
                importMapperService.updateUserDefaults(params.entityId, params.indicatorId, user)
            }

            boolean fileHandlingOk = false
            boolean tooManyColumns = false

            String entityId = params.entityId
            String batimentIndex = params.batimentIndex
            String batimentName = params.batimentName
            Entity entity = entityService.getEntityById(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

            if (entity && indicator) {
                if (!batimentIndex.isInteger()) {
                    flash.fadeErrorAlert = message(code: "excel_generation_fail.invalid_datasets")
                    chain(action: "form", params: [indicatorId: indicatorId, entityId: entityId, childEntityId: childEntityId])
                } else {
                    Map rseeResult = importMapperService.writeRseeToExcel(indicatorId, entityId, batimentIndex, batimentName, rowCounts, temporaryImportData, user, indicator, fileHandlingOk, rseeZoneMapping)
                    fileHandlingOk = rseeResult.fileHandlingOk
                    rowCounts = rseeResult.rowCounts
                    session?.setAttribute(Constants.IMPORT_FILENAME, "${entity.name}_${childEntityId}_${now}.xls")
                    session?.setAttribute(Constants.IMPORT_MAPPER, importMapper)
                    temporaryImportData.importFileName = "${entity.name}_${childEntityId}_${now}.xls"
                }
            }

            if (fileHandlingOk) {

                if (temporaryImportData.id) {
                    temporaryImportData = temporaryImportData.merge(flush: true)
                } else {
                    temporaryImportData = temporaryImportData.save(flush: true)
                    session?.setAttribute(Constants.IMPORT_FILEPATH, temporaryImportData.id.toString())
                }
                
                session?.setAttribute("applicationId", applicationId)
                session?.setAttribute(Constants.TEMPORARY_IMPORT_DATA, temporaryImportData)
                session?.setAttribute(Constants.NO_LICENSE, noLicense)
                log.info("Time it took to put importfile to session & handle params: ${System.currentTimeMillis() - start} ms")
                redirect action: "importSetting", params: [temporaryCalculation: params.temporaryCalculation, entityId: params.entityId, childEntityId: childEntityId, isRseeImport: true,
                                                           bimModelChecker: null, rowCounts:rowCounts, indicatorId: params.indicatorId, outdatedPluginError: params.outdatedPluginError]
            } else {

                if (tooManyColumns) {
                    flash.errorAlert = "${message(code: "importMapper.maxColumns_exception")} ${importMapper.maxColumns ?: Constants.MAX_COLUMNS}"
                } else {
                    flash.errorAlert = message(code: "importMapper.file_exception")
                }

                redirect action: "main", params: [applicationId: params.applicationId, importMapperId: params.importMapperId, bimModelChecker: null]
            }
        } else {
            flash.errorAlert = message(code: "importMapper.choose_targets")
            redirect action: "main", params: [applicationId: params.applicationId, importMapperId: params.importMapperId, bimModelChecker: null]
        }
    }

    def designsForEntity() {
        def options = ""

        if (params.entityId) {
            Entity entity = entityService.getEntityByIdReadOnly(params.entityId)
            ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
            if (entity && importMapper) {
                Indicator indicator = session?.getAttribute("indicator") ?: indicatorService.getIndicatorByIndicatorId(session?.getAttribute(Constants.INDICATOR_ID), true)
                List<Entity> children
                boolean operating = "operating".equals(indicator?.indicatorUse) || "operating".equals(importMapper.indicatorUse) ? true : false

                if (operating) {
                    children = entity.getOperatingPeriods()?.sort({ Entity e -> e.operatingPeriodAndName.toLowerCase() })
                } else {
                    children = entity.getDesigns()?.sort({ Entity e -> e.name.toLowerCase() })
                }
                String childEntityId = params.childEntityId
                String newChildName

                List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
                Boolean preventMultipleDesignsLicened = entity.getPreventMultipleDesignsLicensed(validLicenses)

                if (children) {
                    if (!preventMultipleDesignsLicened) {
                        if (operating) {
                            Integer maxOperatingPeriod = children.collect({ Entity e -> e.operatingPeriod?.toInteger() }).max()
                            newChildName = "New period: ${maxOperatingPeriod + 1}"
                            options = "<option value=\"newPeriod\">${newChildName}</option>"
                        } else {
                            newChildName = "New design"
                            options = "<option value=\"newDesign\">${newChildName}</option>"
                        }
                    }
                    children.findAll({ !it.locked })?.each { Entity child ->
                        options = options + "<option value='${child.id.toString()}'${childEntityId && childEntityId.equals(child.id.toString()) ? ' selected=\"selected\"' : ''}>${operating ? child.operatingPeriodAndName : child.name}</option>"
                    }
                } else if (!preventMultipleDesignsLicened) {
                    if (operating) {
                        newChildName = "New period: ${Calendar.getInstance().get(Calendar.YEAR)}"
                        options = "<option value=\"newPeriod\">${newChildName}</option>"
                    } else {
                        newChildName = "New design"
                        options = "<option value=\"newDesign\">${newChildName}</option>"
                    }
                }
            }
        }
        render([output: options, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def indicatorsForEntity() {
        def options = ""

        if (params.entityId) {
            Entity entity = entityService.getEntityByIdReadOnly(params.entityId)
            Entity childEntity = entityService.getEntityByIdReadOnly(params.childEntityId)
            List<Indicator> indicators = []
            if (childEntity?.entityClass == "operatingPeriod" || params.childEntityId == "newPeriod") {
                indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.OPERATING.toString())
            } else {
                indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.DESIGN.toString())
            }

            if (indicators) {
                ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

                if (importMapper?.compatibleIndicators) {
                    indicators = indicators.findAll({ importMapper.compatibleIndicators.contains(it.indicatorId)})
                }

                String indicatorId = params.indicatorId
                if (indicators) {
                    indicators.sort({ indicatorService.getLocalizedName(it) }).each { Indicator indicator ->
                        if (!entity.readonlyIndicatorIds?.contains(indicator.indicatorId)) {
                            options = options + "<option value=\"${indicator.indicatorId}\"${indicatorId && indicatorId.equals(indicator.indicatorId) ? ' selected=\"selected\"' : ''}>${indicatorService.getLocalizedName(indicator)}</option>"
                        }
                    }
                }
            }
        }
        render([output: options, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def presetFiltersByIndicator() {
        def options = ""
        String indicatorId = params.indicatorId
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        List<ImportMapperPresetFilter> presetFilters = importMapperService.getPresetFilterObjects(importMapper?.presetFilters)

        if (presetFilters) {
            String defaultFilterId

            if (indicatorId) {
                defaultFilterId = presetFilters.find({
                    it.defaultForIndicators &&
                            it.defaultForIndicators.contains(indicatorId)
                })?.presetFilterId
            }

            presetFilters.each { ImportMapperPresetFilter filter ->
                options = "${options}<option value=\"${filter.presetFilterId}\"${filter.presetFilterId?.equals(defaultFilterId) ? ' selected=\"selected\"' : ''}>${filter.localizedName}</option>"
            }
        }
        render([output: options, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def handleConvertData(){
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        if (importMapper) {
            def entityId = session?.getAttribute(Constants.ENTITY_ID)
            def childEntityId = session?.getAttribute("childEntityId")
            String warningMessage = session?.getAttribute("warningMessage")
            Entity entity = entityService.getEntityByIdReadOnly(childEntityId.toString())
            Entity parentEntity = entityService.getEntityByIdReadOnly(entityId.toString())

            Boolean hasAdditionalCat = params.boolean('hasAdditionalCat')
            List mapToChange = params.mapToChange?.tokenize(",")

            List<Dataset> newOkDataset = session?.getAttribute("ifcDatasets")
            if (mapToChange) {
                mapToChange.each { String item ->
                    List<String> subItems = item.tokenize(".")
                    String oldClass = subItems?.first()
                    String newClass = subItems?.getAt(1)
                    String additionalCat
                    if (hasAdditionalCat) {
                        additionalCat = subItems?.get(2)
                    }
                    newOkDataset = importMapperService.changeDatasetsClass(newOkDataset, importMapper, newClass, parentEntity?.entityClass, oldClass, additionalCat)
                }
                String allowDroppingDataGroupedBy = importMapper.allowDroppingDataGroupedBy?.toUpperCase()
                Map<String, Integer> amountOfGroupedByData = importMapperService.getAmountOfGroupedByData(newOkDataset, allowDroppingDataGroupedBy)
                session?.removeAttribute("amountOfGroupedByData")
                session?.setAttribute("amountOfGroupedByData",amountOfGroupedByData)
            }
            session?.removeAttribute("ifcDatasets")
            session?.setAttribute("ifcDatasets", newOkDataset)
            def requiredParams = [entityId: entityId, childEntityId: childEntityId]
            if(session?.getAttribute("filterManually")){
                redirect action: "groupedBy", params: requiredParams
            } else if(session?.getAttribute("combineManually")){
                temporaryImportData.importDataPerStep.put(4,newOkDataset?.size())
                redirect action: "combiner", params: requiredParams
            } else {
                temporaryImportData.importDataPerStep.put(4,newOkDataset?.size())
                redirect action: "combineAll", params: requiredParams
            }
        }
    }

    def groupedBy() {
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        if (importMapper) {
            def entityId = session?.getAttribute(Constants.ENTITY_ID)
            def childEntityId = session?.getAttribute("childEntityId")
            String warningMessage = session?.getAttribute("warningMessage")
            Entity entity = entityService.getEntityByIdReadOnly(childEntityId.toString())
            Entity parentEntity = entityService.getEntityByIdReadOnly(entityId.toString())
            ImportMapperPresetFilter presetFilter = session?.getAttribute(Constants.PRESET_FILTER)
            List<String> skipQuestionMappingValues = presetFilter?.skipQuestionMappingValues
            String defaultQueryId = importMapper?.questionMappingRules?.find({
                it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass)
            })?.target?.get("queryId")
            Indicator indicator = session?.getAttribute("indicator")
            Map amountOfGroupedByData = session?.getAttribute("amountOfGroupedByData")
            TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
            if (!amountOfGroupedByData?.isEmpty()) {
                if (warningMessage && !warningMessage.isEmpty()) {
                    flash.warningAlert = warningMessage
                    session?.removeAttribute("warningMessage")
                }
                [entity             : entity, parentEntity: parentEntity, entityId: entityId, childEntityId: childEntityId, indicator: indicator,currentStep: 4,
                 queryId            : defaultQueryId, amountOfGroupedByData: amountOfGroupedByData, skipQuestionMappingValues: skipQuestionMappingValues,
                 temporaryImportData: temporaryImportData, showSteps: importMapper?.showSteps]
            } else {
                redirect action: "recognizeDatasets", params: [entityId: entityId, childEntityId: childEntityId]
            }
        } else {
            redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"), importMapperId: importMapper?.importMapperId, errorMessage: "This import session has expired. You might have the import page open in multiple tabs."]
        }
    }

    private void removeDatasetsByFilter(ImportMapperPresetFilter presetFilter, List<Dataset> datasets) {
        if (presetFilter && datasets) {
            if (presetFilter.includeQuestionMappingValues) {
                datasets.removeAll({
                    !presetFilter.includeQuestionMappingValues.collect({
                        it.toUpperCase()
                    }).contains(it.importMapperGroupedBy?.toUpperCase())
                })
            } else if (presetFilter.skipQuestionMappingValues) {
                datasets.removeAll({
                    presetFilter.skipQuestionMappingValues.collect({
                        it.toUpperCase()
                    }).contains(it.importMapperGroupedBy?.toUpperCase())
                })
            }
        }
    }

    def combiner() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        if (importMapper) {
            String entityId = params.entityId
            String childEntityId = params.childEntityId
            Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
            Entity parentEntity = entityService.getEntityByIdReadOnly(entityId)
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")
            List<String> headersFromExcel = new ArrayList<String>((List<String>) session?.getAttribute("headers") ?: [])
            String warningMessage = session?.getAttribute("warningMessage")
            Indicator indicator = session?.getAttribute("indicator")

            List<String> allowedMaterialHeadings = importMapper.dataMappingRules?.find({
                "resourceId".equals(it.target)
            })?.mappedHeadings

            List<String> quantityHeaders = importMapper.dataMappingRules?.find({
                "quantity".equals(it.target)
            })?.mappedHeadings
            List<String> unitIdentifyingField = importMapper.unitIdentifyingFields ?: importMapper.unitIdentifyingField ? [importMapper.unitIdentifyingField] : null

            List<String> descriptiveAdditionalQuestionHeaders = importMapper.descriptiveDataMappingRules*.mappedHeadings?.flatten()

            if (datasets && headersFromExcel) {
                headersFromExcel.removeAll(quantityHeaders)
                headersFromExcel.removeAll(descriptiveAdditionalQuestionHeaders)
                headersFromExcel.remove(Constants.DESCRIPTIVE_DATA_COLUMN_NAME)

                List<String> groupByForCollapseSpecial = importMapper?.groupByForCollapseSpecial

                if (groupByForCollapseSpecial && headersFromExcel) {
                    List<String> originalHeaders = new ArrayList<String>(headersFromExcel)
                    headersFromExcel.removeAll(groupByForCollapseSpecial)
                    groupByForCollapseSpecial.removeAll({ !originalHeaders*.toUpperCase().contains(it.toUpperCase()) })
                }

                List<String> emptyDataFields = []

                headersFromExcel.each { String header ->
                    if (datasets.findAll({ it.dataForCollapser?.get(header) != null })?.size() == 0) {
                        emptyDataFields.add(header)
                    }
                }

                if (!emptyDataFields.isEmpty()) {
                    headersFromExcel.removeAll(emptyDataFields)
                }

                if (warningMessage) {
                    flash.warningAlert = warningMessage
                    session?.removeAttribute("warningMessage")
                }

                Map<String, List<ImportMapperCollapseGrouper>> grouperGroupsByClass = [:]
                Map<String, List<ImportMapperCollapseGrouper>> descriptiveGrouperGroupsByClass = [:]
                List<ImportMapperCollapseGrouper> collapseGroupers = session?.getAttribute("collapseGroupers")

                if (!collapseGroupers) {
                    collapseGroupers = importMapperService.createCollapseGroupers(datasets, null, importMapper.groupByForCollapse,
                            importMapper.dataMappingRules, headersFromExcel, importMapper, null, importMapper.groupByForCollapseSpecial)
                }

                List<String> headings = []

                boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
                if (useOldImportMapper) {

                List<ImportMapperCollapseGrouper> groupersToRemove = []
                String usedKey
                List<String> importedClasses = []

                if (collapseGroupers) {
                    collapseGroupers.each { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
                        if (importMapperCollapseGrouper.collapseHeadingAndValue && !importMapperCollapseGrouper.collapseHeadingAndValue.isEmpty()) {
                            headings.addAll(importMapperCollapseGrouper.collapseHeadingAndValue.keySet().toList())

                            if (importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")) {
                                importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")?.toString())
                            } else {
                                usedKey = importMapperCollapseGrouper.collapseHeadingAndValue.keySet()?.toList()?.get(0)
                                importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get(usedKey)?.toString())
                            }
                        } else {
                            groupersToRemove.add(importMapperCollapseGrouper)
                        }
                    }

                    if (!groupersToRemove.isEmpty()) {
                        collapseGroupers.removeAll(groupersToRemove)
                    }
                } else {
                    headings = headersFromExcel?.findAll({it.equalsIgnoreCase("CLASS")||importMapper.groupByForCollapse*.toUpperCase()?.contains(it.toUpperCase())||unitIdentifyingField*.toUpperCase()?.contains(it.toUpperCase())})
                }

                importedClasses?.unique()?.each { String classAsString ->
                    def found
                    if (usedKey) {
                        found = collapseGroupers.findAll({
                            it.collapseHeadingAndValue?.get(usedKey)?.toString()?.equals(classAsString)
                        })
                    } else {
                        found = collapseGroupers.findAll({
                            it.collapseHeadingAndValue?.get("CLASS")?.toString()?.equals(classAsString)
                        })
                    }

                    if (found) {
                        if (usedKey) {
                            String key = "${usedKey}: ${classAsString}"
                            grouperGroupsByClass.put(key, found.sort({ -it.datasetAmount }))
                        } else {
                            grouperGroupsByClass.put(classAsString, found.sort({ -it.datasetAmount }))
                        }
                    }
                }
                } else {
                    grouperGroupsByClass = importMapperService.groupCollapseGroupersByClass(collapseGroupers)
                    descriptiveGrouperGroupsByClass = grouperGroupsByClass.findAll { String groupClass, List<ImportMapperCollapseGrouper> groups ->
                        groups?.find()?.isDescriptive
                    }
                    grouperGroupsByClass -= descriptiveGrouperGroupsByClass

                    if (collapseGroupers) {
                        collapseGroupers.each { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
                            if (importMapperCollapseGrouper.collapseHeadingAndValue && !importMapperCollapseGrouper.collapseHeadingAndValue.isEmpty()) {
                                headings.addAll(importMapperCollapseGrouper.collapseHeadingAndValue.keySet().toList())
                            }
                        }
                    } else {
                        headings = headersFromExcel?.findAll({ it.equalsIgnoreCase("CLASS") || importMapper.groupByForCollapse*.toUpperCase()?.contains(it.toUpperCase()) || unitIdentifyingField*.toUpperCase()?.contains(it.toUpperCase()) })
                    }
                }

                Map<String, List<String>> skipHeadingsForKey = [:]

                if (grouperGroupsByClass && !grouperGroupsByClass.isEmpty()) {
                    grouperGroupsByClass.each { String key, List<ImportMapperCollapseGrouper> groups ->
                        headings.unique().each { String heading ->
                            if (!groups.find({ it.collapseHeadingAndValue?.get(heading) })) {
                                if (skipHeadingsForKey.get(key)) {
                                    List<String> existing = skipHeadingsForKey.get(key)
                                    existing.add(heading)
                                    skipHeadingsForKey.put(key, existing)
                                } else {
                                    skipHeadingsForKey.put(key, [heading])
                                }
                            }
                        }
                    }
                }
                Integer datasetsSize = datasets.size()
                Integer groupers = collapseGroupers?.size()
                Integer collapseDatasets = collapseGroupers?.collect({ it.datasetAmount })?.sum()
                Integer datapointsAfterCombine

                if (datasetsSize != null && collapseDatasets != null && groupers != null) {
                    datapointsAfterCombine = datasetsSize - collapseDatasets + groupers
                }

                String defaultQueryId = importMapper?.questionMappingRules?.find({
                    it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass)
                })?.target?.get("queryId")

                if (temporaryImportData) {
                    if (temporaryImportData.importDataPerStep) {
                        temporaryImportData.importDataPerStep.put(5, datasets ? datasets.size() : 0)
                    } else {
                        temporaryImportData.importDataPerStep = [5: datasets ? datasets.size() : 0]
                    }
                }

                Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, importMapper.skipCombineRowLimit, session)
                List<String> groupByForCollapse = headings.unique()


                [entityId    : entityId, childEntityId: childEntityId, headers: headersFromExcel, entity: entity, showSteps: importMapper?.showSteps, unitIdentifyingField: unitIdentifyingField,
                 parentEntity: parentEntity, temporaryImportData: temporaryImportData, allowedMaterialHeadings: allowedMaterialHeadings, datapointsAfterCombine: datapointsAfterCombine,currentStep: 5,
                 groupByForCollapseSpecial: groupByForCollapseSpecial, datasetsSize: datasetsSize, queryId: defaultQueryId, indicator: indicator, jsonGroupers: importMapper.groupByForCollapse,
                 grouperGroupsByClass   : grouperGroupsByClass?.sort({ it.key }), skipHeadingsForKey : skipHeadingsForKey, groupByForCollapse: groupByForCollapse, quantityHeaders: quantityHeaders,
                 descriptiveGrouperGroupsByClass: descriptiveGrouperGroupsByClass?.sort({ it.key }),
                 skipCombineRowLimit: skipCombineRowLimit, thicknessCombinationRange: importMapper?.thicknessCombinationRange]

            } else {
                redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"), importMapperId: importMapper?.importMapperId, errorMessage: "There no longer are any datasets, aborting."]
            }
        } else {
            redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"), importMapperId: importMapper?.importMapperId, errorMessage: "This import session has expired. You might have the import page open in multiple tabs."]
        }
    }

    def dropGroupedBy() {
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        def requiredParams = [entityId: entityId, childEntityId: childEntityId]
        def manualCombine = session?.getAttribute("combineManually")

        if (params.deleteDataset) {
            ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
            String manualId = params.deleteDatasetManualId
            String deletedDatasetGroup
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")
            Map<String, Integer> amoutOfGroupedByData = session?.getAttribute("amountOfGroupedByData")
            List<ImportMapperCollapseGrouper> collapseGroupers = session?.getAttribute("collapseGroupers")
            Boolean deleteOk

            if (manualId && datasets) {

                Integer iterating = 0
                Integer index = -1
                datasets.each { Dataset dataset ->
                    if (dataset.manualId.equals(manualId)) {
                        deletedDatasetGroup = dataset.importMapperGroupedBy
                        index = iterating
                    } else {
                        iterating++
                    }
                }

                if (index > -1) {
                    deleteOk = datasets.remove(index)
                }

                if (deleteOk) {
                    if (collapseGroupers) {
                        collapseGroupers.find { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
                            if (importMapperCollapseGrouper.datasetIds?.contains(manualId)) {
                                importMapperCollapseGrouper.datasetIds?.remove(manualId)
                                importMapperCollapseGrouper.datasetAmount--
                                return true
                            }
                        }
                    }

                    amoutOfGroupedByData.find { String group, Integer amount ->
                        if (group.equalsIgnoreCase(deletedDatasetGroup)) {
                            amoutOfGroupedByData.put(group, amount - 1)
                            return true
                        }
                    }
                }
            }

            if (deleteOk) {
                flash.fadeSuccessAlert = "Successfully removed dataset from group: ${deletedDatasetGroup}"
            } else {
                log.error("Tried to remove dataset from GroupedBy but failed!")
                flash.fadeErrorAlert = "Could not remove dataset!"
            }

            if (datasets.size() > 0) {
                redirect action: "groupedBy", params: requiredParams
            } else {
                flash.fadeErrorAlert = "There no longer are any datasets, aborting."
                redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"), importMapperId: importMapper?.importMapperId,entityId: entityId, childEntityId: childEntityId]
            }
        } else {
            if (params.groupedBy) {
                List<String> groupedBy = params.list("groupedBy")
                List<Dataset> datasets = session?.getAttribute("ifcDatasets")
                List<ImportMapperCollapseGrouper> collapseGroupers = session?.getAttribute("collapseGroupers")
                ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
                Map<String, Integer> amoutOfGroupedByData = session?.getAttribute("amountOfGroupedByData")
                datasets?.removeAll({ !groupedBy.contains(it.importMapperGroupedBy) })
                collapseGroupers?.removeAll({
                    !groupedBy.contains(it.collapseHeadingAndValue?.get(importMapper.allowDroppingDataGroupedBy.toUpperCase()))
                })
                List<String> removeFromMap = []
                amoutOfGroupedByData?.each { String key, Integer amount ->
                    if (!groupedBy.contains(key)) {
                        removeFromMap.add(key)
                    }
                }
                amoutOfGroupedByData?.keySet()?.removeAll(removeFromMap)

                if (temporaryImportData) {
                    if (temporaryImportData.importDataPerStep) {
                        temporaryImportData.importDataPerStep.put(4, datasets ? datasets.size() : 0)
                    } else {
                        temporaryImportData.importDataPerStep = [4: datasets ? datasets.size() : 0]
                    }
                }

                if (manualCombine) {
                    redirect action: "combiner", params: requiredParams
                } else {
                    redirect action: "combineAll", params: requiredParams
                }
            } else {
                flash.warningAlert = "Choose at least one group"
                redirect action: "groupedBy", params: requiredParams
            }
        }
    }

    def combine() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(session?.getAttribute(Constants.ENTITY_ID), session?.getAttribute("indicator"), importMapper.skipCombineRowLimit, session)

        if (params.grouperId) {
            List<ImportMapperCollapseGrouper> collapseGroupers = session?.getAttribute("collapseGroupers")

            if (datasets && collapseGroupers) {
                List<String> grouperIds = params.list("grouperId")

                if (grouperIds) {
                    List<ImportMapperCollapseGrouper> userSelectedGroupers = collapseGroupers
                            .findAll { grouperIds.contains(it.grouperId) }
                    importMapperService.combineDatasets(userSelectedGroupers, datasets)
                    collapseGroupers.removeAll(userSelectedGroupers)
                }
            }
            params.remove("grouperId")
        }

        if (temporaryImportData) {
            if (temporaryImportData.importDataPerStep) {
                temporaryImportData.importDataPerStep.put(5, datasets ? datasets.size() : 0)
            } else {
                temporaryImportData.importDataPerStep = [5: datasets ? datasets.size() : 0]
            }
        }

        if (datasets && skipCombineRowLimit && skipCombineRowLimit < datasets.size()) {
            flash.errorAlert = message(code: "importMapper.too_many_rows", args: [skipCombineRowLimit])
            redirect action: "combiner", params: [entityId: params.entityId, childEntityId: params.childEntityId]
        } else {
                redirect action: "recognizeDatasets", params: [entityId: params.entityId, childEntityId: params.childEntityId]
        }
    }

    def combineAll() {
        def childEntityId = params.childEntityId
        def entityId = params.entityId
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        List<ImportMapperCollapseGrouper> collapseGroupers = session?.getAttribute("collapseGroupers")
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)

        if (datasets && collapseGroupers) {
            session?.setAttribute("combineSuccessful", true)
            importMapperService.combineDatasets(collapseGroupers, datasets)
            session?.removeAttribute("collapseGroupers")

            if (temporaryImportData) {
                if (temporaryImportData.importDataPerStep) {
                    temporaryImportData.importDataPerStep.put(5, datasets ? datasets.size() : 0)
                } else {
                    temporaryImportData.importDataPerStep = [5: datasets ? datasets.size() : 0]
                }
            }
        }
        redirect action: "recognizeDatasets", params: [entityId: entityId, childEntityId: childEntityId, combineAll: true]
    }

    def recognizeDatasets() {
        List<Dataset> newDatasets = []
        Map<String, String[]> paramMap = request.getParameterMap()
        long now = System.currentTimeMillis()
        List<Dataset> tooGenericDatasets = session?.getAttribute("tooGenericDatasets")
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        Indicator indicator = session?.getAttribute("indicator")
        String applicationId = session?.getAttribute("applicationId")
        List<RecognitionRuleset> systemRulesets = recognitionRulesetService.getAllRecognitionRulesets(Constants.RulesetType.MAPPING.toString())
        List<String> requiredFieldsForRecognition = importMapperService.getRequiredFieldsForRecognition(importMapper)
        Boolean bimModelChecker = session?.getAttribute('bimModelChecker')
        def entityId = params.entityId
        def childEntityId = params.childEntityId
        Boolean skipReview = params.boolean("skipReview")

        flashService.reinitForTheNextRequestIfNeeded()

        if (importMapper && datasets && indicator) {
            Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, importMapper.skipCombineRowLimit, session)

            if (datasets && skipCombineRowLimit && skipCombineRowLimit < datasets.size()) {
                flash.errorAlert = message(code: "importMapper.too_many_rows", args: [skipCombineRowLimit])
                redirect action: "combiner", params: [entityId: entityId, childEntityId: childEntityId]
            } else {
                Entity child = entityService.readEntity(childEntityId)
                Entity entity = entityService.readEntity(entityId)
                Account account = userService.getAccount(userService.getCurrentUser())
                List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)
                List<String> resourceIdsInDataList = []
                boolean isUncapped = licenseService.hasEcoinventUncappedLicensed(licensedFeatureIds)

                if (!isUncapped) {
                    resourceIdsInDataList = productDataListService.getResourceIdsInDataList(licensedFeatureIds, account)
                }


                List<String> allAllowedUnits = importMapper.allowedUnits ? importMapper.allowedUnits : Constants.ALL_UNITS
                String entityCountry

                if (entity) {
                    entityCountry = entity?.countryForPrioritization
                }
                String entityClass = entity?.entityClass
                ClassRemapping classRemapChoice = entityClass ? importMapper.allowedClassRemappingsSets?.find({entityClass.equals(it.remappingEntityClass)}) : null

                String localizedRecognizedFromHeading = message(code: "importMapper.dataset.recognizedFrom")

                if (classRemapChoice && classRemapChoice.automaticClassRemappings && tooGenericDatasets) {
                    tooGenericDatasets.each { Dataset d ->
                        String oldClass = d.trainingData?.get("CLASS")?.toUpperCase()

                        Boolean isResourceDataset = importMapperService.isResourceDataset(dataset)
                        List<String> allowedClassRemappings = isResourceDataset ? classRemapChoice.allowedClassRemappings : classRemapChoice.allowedDescriptiveClassRemappings
                        if (oldClass && !allowedClassRemappings?.contains(oldClass)) {
                            String newClass = classRemapChoice.automaticClassRemappings.get(oldClass)
                            importMapperService.changeDatasetClass(d, importMapper, newClass, entityClass)
                        }
                    }
                }

                datasets.collate(500).each { List<Dataset> datasetsBatch ->
                    String organization = account?.companyName
                    Map<String, Document> trainingDataPerDataset = importMapperTrainingDataService.getTrainingDataForDatasetList(
                            datasetsBatch, applicationId, importMapper.importMapperId,
                            indicator, entityCountry, organization, resourceIdsInDataList, isUncapped)
                    Map <String, Document> trainingDataOCLIDPerDataset = importMapperTrainingDataService.getTrainingDataForDatasetListByImportDisplayField(
                            datasetsBatch, com.bionova.optimi.construction.Constants.ExcelCellHeaders.OCLID.name(),
                            indicator, entityCountry, organization, resourceIdsInDataList, isUncapped)

                    datasetsBatch.each { Dataset dataset ->

                        Boolean isResourceDataset = importMapperService.isResourceDataset(dataset)

                        if (classRemapChoice && classRemapChoice.automaticClassRemappings) {
                            String oldClass = dataset.trainingData?.get("CLASS")?.toUpperCase()
                            List<String> allowedClassRemappings = isResourceDataset ? classRemapChoice.allowedClassRemappings : classRemapChoice.allowedDescriptiveClassRemappings
                            if (oldClass && !allowedClassRemappings?.contains(oldClass)) {
                                String newClass = classRemapChoice.automaticClassRemappings.get(oldClass)
                                importMapperService.changeDatasetClass(dataset, importMapper, newClass, entityClass)
                            }
                        }
                        if (isResourceDataset) {
                            if (allAllowedUnits.contains(dataset.userGivenUnit?.toLowerCase())) {
                                Document importMapperTrainingData = trainingDataPerDataset?.getAt(dataset.manualId)
                                Document importMapperOCLIDTrainingData = trainingDataOCLIDPerDataset?.getAt(dataset.manualId)
                                Boolean isRseeImport = session?.getAttribute("isRseeImport")
                                importMapperService.recognizeDataset(dataset, systemRulesets, requiredFieldsForRecognition,
                                        applicationId, importMapper, indicator, entityCountry, null, child, account,
                                        localizedRecognizedFromHeading, resourceIdsInDataList, isUncapped,
                                        importMapperTrainingData, importMapperOCLIDTrainingData, isRseeImport)
                            } else {
                                dataset.importMapperPartialQuantification = Boolean.TRUE
                                dataset.allowMapping = Boolean.FALSE
                                dataset.resourceId = null
                                dataset.profileId = null
                                dataset.userGivenUnit = null
                            }
                        } else if (dataset.allImportDisplayFields) {
                            importMapperService.fillDatasetImportFieldsId(dataset)
                        }

                        String valueForClass = dataset.trainingData?.get("CLASS")

                        if (valueForClass) {
                            ImportMapperDefaultRule importMapperDefaultRule = importMapper.defaultValueRules?.find({
                                valueForClass.equalsIgnoreCase(it.classMatch)
                            })

                            if (importMapperDefaultRule) {
                                importMapperService.setDefaultValuesForDataset(dataset, importMapperDefaultRule)
                            }
                        }
                    }
                }

                if(paramMap){
                    newDatasets = importMapperService.setParamValuesForDatasets(datasets, paramMap)
                    if(!newDatasets.isEmpty()){
                        datasets = newDatasets
                    }
                }
                boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
                if (!useOldImportMapper) {
                    importMapperService.combineDescriptiveDatasets(datasets, importMapper)
                }

                session?.setAttribute("ifcDatasets", datasets)
                log.info("Time it took to RESOLVE AND COMBINE: ${System.currentTimeMillis() - now} ms")

                if (bimModelChecker) {
                    redirect action: "bimModelCheckerStandAlone", params: [entityId: entityId, childEntityId: childEntityId]

                } else {
                    if(session?.getAttribute("dataReview") && !skipReview){
                        redirect action: "dataReview", params: [entityId: entityId, childEntityId: childEntityId]
                    } else {
                        redirect action: "resolver", params: [entityId: entityId, childEntityId: childEntityId]
                    }
                }
            }
        } else {
            if (!session?.getAttribute("gbxmlErrorMessage")) {
                session?.setAttribute("gbxmlErrorMessage", "${message(code: 'importMapper.no_data')}")
            }
            redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapper?.importMapperId,
                                               indicatorId  : indicator?.indicatorId, entityId: entityId, childEntityId: childEntityId]
        }
    }

    def resolver() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityByIdReadOnly(entityId)
        String trainingDataCountry = parentEntity?.country
        String entityClass = parentEntity?.entityClass
        Indicator indicator = session?.getAttribute("indicator")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        String importError = params.importError
        String resourceGroups
        List<Resource> resources

        if (importMapper) {
            ClassRemapping classRemapChoice = entityClass ? importMapper.allowedClassRemappingsSets?.find({entityClass.equals(it.remappingEntityClass)}) : null
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")

            resourceGroups = importMapper?.resourceGroups ? importMapper.resourceGroups.join(",") : "OTHER"

            Boolean temporaryCalculation = session?.getAttribute("temporaryCalculation")
            String defaultQueryId

            if (temporaryCalculation) {
                defaultQueryId = importMapper?.questionMappingRules?.find({
                    it.defaultTargetForEntityClass?.contains("building")
                })?.target?.get("queryId")
            } else {
                defaultQueryId = importMapper?.questionMappingRules?.find({
                    it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass)
                })?.target?.get("queryId")
            }
            List<String> displayFields = importMapper?.displayFields
            List<String> unitIdentifyingField = importMapper.unitIdentifyingFields ?: importMapper.unitIdentifyingField ? [importMapper.unitIdentifyingField] : null
            Query query = queryService.getQueryByQueryId(defaultQueryId, Boolean.TRUE)
            List<Question> questions = query?.getAllQuestions()
            List<Question> additionals = []
            questions?.each { Question q ->
                List<Question> additionalQuestions = q.getAdditionalQuestions(indicator, null, null, parentEntity)

                if (additionalQuestions) {
                    additionals.addAll(additionalQuestions)
                }
            }
            additionals = additionals.unique({it.questionId})
            List<Question> resolvedAdditionalQuestions = []
            additionals.each { Question q ->
                if (!"comment".equals(q.questionId)) {
                    if (q.questionId == "serviceLife") {
                        if (!parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)) {
                            if (q.showInMapping) {
                                resolvedAdditionalQuestions.add(q)
                            } else {
                                resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                            }
                        }
                    } else if (q.isTransportQuestion) {
                        if (!parentEntity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)) {
                            if (q.showInMapping) {
                                resolvedAdditionalQuestions.add(q)
                            } else {
                                resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                            }
                        }
                    } else if (q?.privateClassificationQuestion) {
                        resolvedAdditionalQuestions.add(q)
                    } else {
                        if (q.showInMapping) {
                            resolvedAdditionalQuestions.add(q)
                        } else {
                            resolvedAdditionalQuestions.add(q.createHiddenAdditionalQuestionCopy())
                        }
                    }
                }
            }
            List<Dataset> notIdentifiedDatasets
            List<Dataset> notIdentifiedDescriptiveDatasets
            List<Dataset> invalidUnitDatasets
            Double notIdentifiedPercentual
            Double notIdentifiedDescriptivePercentual
            List<Dataset> identifiedDatasets
            List<Dataset> identifiedDescriptiveDatasets
            Double identifiedPercentual
            Double identifiedDescriptivePercentual
            List<Dataset> tooGenericDatasets = session?.getAttribute("tooGenericDatasets")
            Boolean expandNotIdentified = Boolean.FALSE
            String warning = flash.warningAlert ?: ""

            if (datasets) {
                importMapperService.calculatePercentageOfTotal(datasets, importMapper)
                List<Dataset> resourceDatasets = []
                List<Dataset> descriptiveDatasets = []

                datasets.each { Dataset dataset ->
                    if (importMapperService.isResourceDataset(dataset)) {
                        resourceDatasets << dataset
                    } else {
                        descriptiveDatasets << dataset
                    }
                }

                notIdentifiedDatasets = resourceDatasets.findAll({
                    !it.resourceId && it.userGivenUnit
                })
                invalidUnitDatasets = resourceDatasets.findAll({
                    !it.userGivenUnit
                })
                notIdentifiedDescriptiveDatasets = descriptiveDatasets.findAll{ !it.answerIds }

                loggerUtil.info(log, "Number of not identified data: ${notIdentifiedDatasets ? notIdentifiedDatasets.size() : 0}")
                loggerUtil.info(log, "Number of invalid unit data: ${invalidUnitDatasets ? invalidUnitDatasets.size() : 0}")

                if (invalidUnitDatasets && !invalidUnitDatasets.isEmpty()){
                    warning = "${warning}${ message(code: "import.invalid_unit_data_amount", args: [invalidUnitDatasets.size()])}"
                }
                if ((notIdentifiedDatasets && !notIdentifiedDatasets.isEmpty())) {
                    notIdentifiedDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    notIdentifiedDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        notIdentifiedPercentual = notIdentifiedPercentual ? notIdentifiedPercentual + d.percentageOfTotal : d.percentageOfTotal
                    }

                    if (notIdentifiedPercentual) {
                        Integer silentAndFastPercentage = importMapper.silentAndFastPercentage ? importMapper.silentAndFastPercentage : 5

                        if (notIdentifiedPercentual > silentAndFastPercentage) {
                            expandNotIdentified = Boolean.TRUE
                        }
                        session?.setAttribute("notIdentifiedPercentual", notIdentifiedPercentual)
                    }
                }
                if (notIdentifiedDescriptiveDatasets) {
                    notIdentifiedDescriptiveDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    notIdentifiedDescriptiveDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        notIdentifiedDescriptivePercentual = notIdentifiedDescriptivePercentual ? notIdentifiedDescriptivePercentual + d.percentageOfTotal : d.percentageOfTotal
                    }
                }
                if (notIdentifiedDatasets || notIdentifiedDescriptiveDatasets) {
                    List<Integer> notIdentifiedDatasetsCounts = [notIdentifiedDatasets?.size() ?: 0]
                    if (descriptiveDatasets) {
                        notIdentifiedDatasetsCounts << (notIdentifiedDescriptiveDatasets?.size() ?: 0)
                    }
                    warning = "${warning} ${message(code: "import.unidentified_data_amount", args: [notIdentifiedDatasetsCounts?.join(' / ')]) + " " + message(code: "importMapper.resolver.ambiguous_warning") }."
                }
                if(warning.length() > 0){
                    flash.warningAlert = warning
                }
                identifiedDatasets = resourceDatasets.findAll{ it.resourceId }
                identifiedDescriptiveDatasets = descriptiveDatasets.findAll{ it.answerIds }

                if (identifiedDatasets && !identifiedDatasets.isEmpty()) {
                    identifiedDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    identifiedDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        identifiedPercentual = identifiedPercentual ? identifiedPercentual + d.percentageOfTotal : d.percentageOfTotal
                    }
                }
                if (identifiedDescriptiveDatasets) {
                    identifiedDescriptiveDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    identifiedDescriptiveDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        identifiedDescriptivePercentual = identifiedDescriptivePercentual ? identifiedDescriptivePercentual + d.percentageOfTotal : d.percentageOfTotal
                    }
                }
                loggerUtil.info(log, "Number of identified data: ${identifiedDatasets ? identifiedDatasets.size() : 0}")
                session?.setAttribute("identifiedDatasets", identifiedDatasets)
            }
            String okMessage
            String importMessage = getImportMessage(session)

            if (importMessage) {
                okMessage = okMessage ? "${okMessage} ${importMessage}" : importMessage
            }
            session?.setAttribute("okMessage", okMessage)

            String applicationId = session?.getAttribute("applicationId")
            String detailsKey = session?.getAttribute("materialHeading")

            if (!detailsKey) {
                if (importMapper.materialHeading) {
                    detailsKey = importMapper.materialHeading
                } else {
                    detailsKey = "IFCMATERIAL"
                }
            }
            String warnMessage
            String warningFromImport = session?.getAttribute("warningMessage")

            if (session?.getAttribute("skipMappingRejects")) {
                warnMessage = session?.getAttribute("skipMappingRejects")
                session.removeAttribute("skipMappingRejects")
            }

            if (warningFromImport && !warningFromImport.isEmpty()) {
                if (warnMessage) {
                    warnMessage = warnMessage + "<br />${warningFromImport}"
                } else {
                    warnMessage = "${warningFromImport}"
                }
            }
            User user = userService.getCurrentUser()
            String userResourceAttributesAsString
            String userResourceAttributeValueAsString
            String userResourceType
            String userSubType

            if (user?.userFilterChoices) {
                List<String> userResourceAttributeForFilter = new ArrayList<String>()
                List<String> userResourceAttributeValueForfilter = new ArrayList<String>()
                user.userFilterChoices.each { String key, String value ->
                    userResourceAttributeForFilter.add(key)
                    userResourceAttributeValueForfilter.add(value)
                }
                userResourceAttributesAsString = userResourceAttributeForFilter?.join(",")
                userResourceAttributeValueAsString = userResourceAttributeValueForfilter?.join(",")
            }
            if (user?.userNestedFilters) {
                user.userNestedFilters.each { String key, String value ->
                    userSubType = value
                    userResourceType = key
                }
            }
            List<QueryFilter> supportedFilters = query?.supportedFilters
            QueryFilter resourceTypeFilter = supportedFilters?.find({ it.resourceAttribute.equals("resourceType") })
            List<String> genericDatasetsWarnings = []

            tooGenericDatasets.each { Dataset genericDataset ->
                importMapper.dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->

                    if ("RESOURCEID".equalsIgnoreCase(importMapperDataRule.target)) {
                        String warningString = genericDataset.trainingData?.get(importMapperDataRule.target.toUpperCase())

                        if (warningString) {
                            genericDatasetsWarnings.add(warningString)
                        }
                    }
                }
            }

            session?.setAttribute("genericDatasetsWarnings", genericDatasetsWarnings)

            if (temporaryImportData) {
                Integer unidentifiedSize = notIdentifiedDatasets ? notIdentifiedDatasets.size() : 0
                Integer identifiedSize = identifiedDatasets ? identifiedDatasets.size() : 0
                Integer unidentifiedDescriptiveSize = notIdentifiedDescriptiveDatasets ? notIdentifiedDescriptiveDatasets.size() : 0
                Integer identifiedDescriptiveSize = identifiedDescriptiveDatasets ? identifiedDescriptiveDatasets.size() : 0

                if (temporaryImportData.importDataPerStep) {
                    temporaryImportData.importDataPerStep.put(6, datasets?.size())
                } else {
                    temporaryImportData.importDataPerStep = [:]
                }
                temporaryImportData.importDataPerStep.put(7, identifiedSize)
                temporaryImportData.importDataPerStep.put(8, unidentifiedSize)
                temporaryImportData.importDataPerStep.put(71, identifiedDescriptiveSize)
                temporaryImportData.importDataPerStep.put(81, unidentifiedDescriptiveSize)
            }

            Boolean skipMapping = session?.getAttribute("skipMapping")
            Boolean skipMappingFails = Boolean.FALSE
            Integer silentAndFastPercentage = importMapper.silentAndFastPercentage ? importMapper.silentAndFastPercentage : 5

            if (skipMapping && datasets) {
                if (identifiedDatasets) {
                    if (notIdentifiedDatasets && !notIdentifiedDatasets.isEmpty()) {
                        Integer total = identifiedDatasets.size() + notIdentifiedDatasets.size()

                        if (total) {
                            Integer notIdentifiedPercent = notIdentifiedDatasets.size() / total * 100

                            if (notIdentifiedPercent > silentAndFastPercentage) {
                                skipMappingFails = Boolean.TRUE
                            }
                        }
                    }
                } else {
                    skipMappingFails = Boolean.TRUE
                }
            }

            if (skipMapping && !skipMappingFails && !importError) {
                redirect action: "mapResolvedResources", params: [childEntityId: childEntityId, entityId: entityId, indicatorId: indicator?.indicatorId, queryId: query?.queryId, skipMappingSuccess: true]
            } else {
                if (skipMappingFails) {
                    flash.errorAlert = message(code: 'importMapper.fastAndSilent.unrecognized_data', args: [silentAndFastPercentage])
                }
                List<HelpConfiguration> helpConfigurations = helpConfigurationService.getConfigurationsForTargetPage('ImportMapping')
                List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
                boolean isPlanetaryOnly = validLicenses?.findAll({!it?.planetary})?.size() > 0 ? false : true

                Account account = userService.getAccount(user)
                [entity                            : entity,
                 parentEntity                      : parentEntity,
                 notIdentifiedDatasets             : notIdentifiedDatasets,
                 isPlanetaryOnly                   : isPlanetaryOnly,
                 notIdentifiedPercentual           : notIdentifiedPercentual,
                 notIdentifiedDescriptiveDatasets  : notIdentifiedDescriptiveDatasets,
                 notIdentifiedDescriptivePercentual: notIdentifiedDescriptivePercentual,
                 identifiedDatasets                : identifiedDatasets,
                 identifiedPercentual              : identifiedPercentual,
                 identifiedDescriptiveDatasets     : identifiedDescriptiveDatasets,
                 identifiedDescriptivePercentual   : identifiedDescriptivePercentual,
                 resources                         : resources,
                 questions                         : questions,
                 entityId                          : entityId,
                 showSteps                         : importMapper?.showSteps, currentStep: 7,
                 childEntityId                     : childEntityId,
                 indicator                         : indicator,
                 queryId                           : defaultQueryId,
                 displayFields                     : displayFields,
                 unitIdentifyingField              : unitIdentifyingField,
                 resourceGroups                    : resourceGroups,
                 detailsKey                        : detailsKey,
                 trainingDataCountry               : trainingDataCountry,
                 importMapperId                    : importMapper.importMapperId,
                 applicationId                     : applicationId,
                 account                           : account,
                 supportedFilters                  : supportedFilters,
                 query                             : query,
                 user                              : user,
                 userResourceAttributesAsString    : userResourceAttributesAsString,
                 userResourceAttributeValueAsString: userResourceAttributeValueAsString,
                 temporaryImportData               : temporaryImportData,
                 userResourceType                  : userResourceType,
                 userSubType                       : userSubType,
                 resourceTypeFilter                : resourceTypeFilter,
                 denyMappingDatasets               : tooGenericDatasets,
                 datasetSize                       : datasets?.size(),
                 okMessage                         : okMessage,
                 warningMessage                    : warnMessage,
                 classRemapChoice                  : classRemapChoice,
                 genericDatasetsWarnings           : genericDatasetsWarnings,
                 expandNotIdentified               : expandNotIdentified,
                 additionalQuestions               : resolvedAdditionalQuestions,
                 helpConfigurations                : helpConfigurations]
            }
        } else {
            redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"),importMapperId: session?.getAttribute("importMapperId"), errorMessage: "Could not find importMapper with given parameters"]
        }
    }

    private String getImportMessage(HttpSession session) {
        String importMessage = ""
        List<Dataset> okDatasets = session?.getAttribute("ifcDatasets")

        if (okDatasets && !okDatasets.isEmpty()) {
            Integer filterRemoved = session?.getAttribute("filterRemovedSize")

            if (filterRemoved != null && filterRemoved > 0) {
                importMessage = importMessage + message(code: "ifc.dataset_import.filter_by_class", args: [filterRemoved], encodeAs: "None")
            }


            def discardedMaterialsWithValues = session?.getAttribute("discardedMaterialsWithValues")

            if (discardedMaterialsWithValues) {
                importMessage = importMessage + message(code: "ifc.dataset_import.filter_by_description", args: [discardedMaterialsWithValues], encodeAs: "None",)
            }

            def emptyClassAndIfcMaterials = session?.getAttribute("emptyRows")

            if (emptyClassAndIfcMaterials) {
                importMessage = importMessage + emptyClassAndIfcMaterials
            }
            if (session?.getAttribute("combineSuccessful")) {
                importMessage = importMessage + message(code: "ifc_dataset_import.combine_successful", encodeAs: "None",)
            }
        }
        return importMessage
    }

    def listAmbiguousData() {
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        List<Dataset> ambiguousDatasets
        List<Dataset> partialQuantificationDatasets
        List<Dataset> unquantifiedDatasets
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        List<Dataset> identifiedDatasets = session?.getAttribute("identifiedDatasets")
        List<Dataset> tooGenericDatasets = session?.getAttribute("tooGenericDatasets")
        List<String> genericDatasetsWarnings = session?.getAttribute("genericDatasetsWarnings")
        String okMessage = session?.getAttribute("okMessage")
        String warningMessage = session?.getAttribute("warningMessage")

        if (datasets && !datasets.isEmpty()) {
            ambiguousDatasets = datasets.findAll({
                !it.resourceId || it.importMapperCompositeMaterial || it.importMapperPartialQuantification
            })?.unique({ it.manualId })
            partialQuantificationDatasets = datasets.findAll({ it.importMapperPartialQuantification })?.unique({
                it.manualId
            })
            unquantifiedDatasets = datasets.findAll({ !it.quantity })?.unique({ it.manualId })
        }
        String detailsKey = session?.getAttribute("materialHeading")

        if (!detailsKey) {
            if (importMapper?.materialHeading) {
                detailsKey = importMapper.materialHeading
            } else {
                detailsKey = "IFCMATERIAL"
            }
        }
        [ambiguousDatasets : ambiguousDatasets, materialKey: detailsKey,
         identifiedDatasets: identifiedDatasets, denyMappingDatasets: tooGenericDatasets, genericDatasetsWarnings: genericDatasetsWarnings,
         okMessage         : okMessage, warningMessage: warningMessage, datasetSize: datasets?.size(), partialQuantificationDatasets: partialQuantificationDatasets, unquantifiedDatasets: unquantifiedDatasets]
    }

    def ignoreWarning() {

        String manualId = request?.JSON?.manualId
        String output = ""

        if(manualId){
            List<Dataset> datasets = session?.getAttribute("identifiedDatasets")
            int index = datasets?.findIndexOf{it?.manualId == manualId} ?: -1

            if(index!=-1){
                datasets.get(index)?.ignoreWarning = Boolean.TRUE
                session?.setAttribute("identifiedDatasets", datasets)
                output = "Update dataset"
            } else {
                output = "Dataset not present"
            }

        } else{
           output = "ManualId empty"
        }

        render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def mapResolvedResources() {
        Map<String, String[]> paramMap = request.getParameterMap()
        def childEntityId = params.childEntityId
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        def entityId = params.entityId
        def indicatorId = params.indicatorId
        Indicator indicator = session?.getAttribute("indicator") ?: indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<Dataset> newDatasets = []
        def queryId = params.queryId
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        Boolean skipMapping = session?.getAttribute("skipMapping")
        Boolean incrementalImport = session?.getAttribute("incrementalImport")
        Boolean temporaryCalculation = session?.getAttribute("temporaryCalculation")
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        Boolean skipMappingSuccess = params.boolean('skipMappingSuccess')
        Boolean updateQuant = session?.getAttribute("updateQuant")
        if (datasets && ((entityId && childEntityId) || temporaryCalculation) && indicatorId && queryId && importMapper) {
            Entity parentEntity = entityService.getEntityById(entityId)
            Entity entity
            if (childEntityId) {
                entity = entityService.getEntityById(childEntityId)
                if (entity && entity.disabledIndicators?.contains(indicatorId)) {
                    entity.disabledIndicators.remove(indicatorId)
                }
            }

            boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
            if (useOldImportMapper) {
                if (skipMapping && skipMappingSuccess) {
                    newDatasets = datasets.findAll({ it.resourceId })
                } else if (paramMap) {
                    newDatasets = importMapperService.setParamValuesForDatasets(datasets, paramMap)
                }
                newDatasets = newDatasets?.findAll({ it.resourceId || it.importMapperCompositeMaterial || it.importMapperDecideLater })
            } else {
                if (skipMapping && skipMappingSuccess) {
                    newDatasets = datasets.findAll({ it.resourceId || !importMapperService.isResourceDataset(it) })
                } else if (paramMap) {
                    newDatasets = importMapperService.setParamValuesForDatasets(datasets, paramMap)
                }
                newDatasets = newDatasets?.findAll({ it.resourceId || it.importMapperCompositeMaterial || it.importMapperDecideLater || !importMapperService.isResourceDataset(it) })
            }
            importMapperService.handleDatasetTargets(newDatasets, indicator, importMapper, parentEntity?.entityClass)

            if (temporaryCalculation && temporaryImportData) {
                session?.setAttribute("ifcDatasets", !newDatasets.isEmpty() ? newDatasets : datasets)

                List<Question> resolvedQuestions = []
                newDatasets?.each { Dataset dataset ->
                    Question question = resolvedQuestions.find({ it.questionId.equals(dataset.questionId) })

                    if (!question) {
                        question = datasetService.getQuestion(dataset)

                        if (question) {
                            resolvedQuestions.add(question)
                        }
                    }

                    if (question?.additionalQuestionDefaults) {
                        question.additionalQuestionDefaults.each { String key, String value ->
                            if (dataset.additionalQuestionAnswers) {
                                if (!dataset.additionalQuestionAnswers.get(key)) {
                                    dataset.additionalQuestionAnswers.put(key, value)
                                }
                            } else {
                                dataset.additionalQuestionAnswers = [(key): value]
                            }
                        }
                    }

                    if ((dataset.importMapperCompositeMaterial && !dataset.resourceId) || dataset.importMapperDecideLater) {
                        String unit = dataset.userGivenUnit?.toLowerCase()?.trim()

                        if (unit) {
                            Map<String, String> resourceAndProfileId = importMapper.compositeMapping?.find({
                                it.get(unit)
                            })?.get(unit)
                            dataset.resourceId = resourceAndProfileId?.get("resourceId")
                            dataset.profileId = resourceAndProfileId?.get("profileId")

                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("profileId", resourceAndProfileId?.get("profileId"))
                            } else {
                                dataset.additionalQuestionAnswers = [profileId: resourceAndProfileId?.get("profileId")]
                            }
                            dataset.allowMapping = Boolean.FALSE
                            dataset.systemTrained = Boolean.TRUE
                        }
                    }
                }
                if (!updateQuant) {
                    if (newDatasets && !newDatasets.isEmpty()) {
                        temporaryImportData.temporaryEntity = saveDatasets(temporaryImportData.temporaryEntity, session, newDatasets, indicatorId, null, temporaryCalculation)
                    } else if (datasets && newDatasets.isEmpty()) {
                        temporaryImportData.temporaryEntity = saveDatasets(temporaryImportData.temporaryEntity, session, datasets, indicatorId, null, temporaryCalculation)
                    }
                    temporaryImportData.importDataPerStep = null
                    temporaryImportData = temporaryImportData.merge(flush: true, failOnError: true)
                }


                if ((newDatasets || datasets) && temporaryImportData?.temporaryEntity?.tempCalculationResults) {
                    redirect action: "temporaryCalculations"
                } else {
                    String errorMessage = "Parsed ${session?.getAttribute(Constants.FILE_EXTENSION) ?: "Excel or gbXML"} Excel or gbXML file did not contain valid material definitions. Please verify the file and mappings"
                    redirect action: "main", params: [applicationId: session?.getAttribute("applicationId"), importMapperId: importMapper?.importMapperId,
                                                       errorMessage : errorMessage]
                }
            } else {
                String importError
                String trainingDataCountry = parentEntity?.country

                List<Question> resolvedQuestions = []

                List<SpecialRule> specialRules = applicationService.getApplicationByApplicationId("LCA")?.specialRules
                List<Dataset> fractionDatasets = []

                ResourceCache resourceCache = ResourceCache.init(newDatasets)
                for (Dataset dataset: newDatasets) {
                    Resource resource = resourceCache.getResource(dataset)
                    Question question = resolvedQuestions.find({ it.questionId.equals(dataset.questionId) })

                    if (!question) {
                        question = datasetService.getQuestion(dataset)

                        if (question) {
                            resolvedQuestions.add(question)
                        }
                    }

                    if (question?.additionalQuestionDefaults) {
                        question.additionalQuestionDefaults.each { String key, String value ->
                            if (dataset.additionalQuestionAnswers) {
                                if (!dataset.additionalQuestionAnswers.get(key)) {
                                    dataset.additionalQuestionAnswers.put(key, value)
                                }
                            } else {
                                dataset.additionalQuestionAnswers = [(key): value]
                            }
                        }
                    }

                    if ((dataset.importMapperCompositeMaterial && !dataset.resourceId) || dataset.importMapperDecideLater) {
                        String unit = dataset.userGivenUnit?.toLowerCase()?.trim()

                        if (unit) {
                            Map<String, String> resourceAndProfileId = importMapper.compositeMapping?.find({
                                it.get(unit)
                            })?.get(unit)
                            dataset.resourceId = resourceAndProfileId?.get("resourceId")
                            dataset.profileId = resourceAndProfileId?.get("profileId")

                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("profileId", resourceAndProfileId?.get("profileId"))
                            } else {
                                dataset.additionalQuestionAnswers = [profileId: resourceAndProfileId?.get("profileId")]
                            }
                            dataset.allowMapping = Boolean.FALSE
                            dataset.systemTrained = Boolean.TRUE
                        }
                    }

                    if (resource && dataset.queryId && dataset.specialRuleShare && dataset.specialRuleId) {
                        SpecialRule specialRule = specialRules.find({ dataset.specialRuleId.equals(it.specialRuleId) })
                        List<String> dataProperties = indicator.indicatorQueries?.find({ it.queryId?.equals(dataset.queryId) })?.resourceFilterCriteria?.get(IndicatorQuery.DATA_PROPERTIES_FILTER)
                        String dataPropertyToUse = null

                        if (specialRule && specialRule.mappingByDataproperty && dataProperties) {
                            dataPropertyToUse = specialRule.mappingByDataproperty.keySet().find({ dataProperties.contains(it) })
                        }
                        String datasetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(dataset.userGivenUnit)

                        if (specialRule && dataPropertyToUse && (!specialRule.requiredQuantityType || (specialRule.requiredQuantityType && datasetUnit && specialRule.requiredQuantityType.equalsIgnoreCase(datasetUnit)))) {
                            Map<String, String> replacementResourceIdAndProfileId = specialRule.mappingByDataproperty?.get(dataPropertyToUse)

                            if (replacementResourceIdAndProfileId) {
                                String resourceId = replacementResourceIdAndProfileId.resourceId
                                String profileId = replacementResourceIdAndProfileId.profileId
                                Double percentage = dataset.specialRuleShare
                                Double originalQuantity = dataset.quantity
                                Double splitQuantity = 0

                                if (percentage && originalQuantity) {
                                    splitQuantity = (percentage / 100.0) * originalQuantity
                                    originalQuantity = ((100 - percentage) / 100.0) * originalQuantity
                                }

                                if (originalQuantity && splitQuantity) {
                                    Dataset splitDataset = datasetService.createCopy(dataset)
                                    dataset.quantity = originalQuantity
                                    dataset.answerIds = ["${originalQuantity}"]
                                    splitDataset.quantity = splitQuantity
                                    splitDataset.answerIds = ["${splitQuantity}"]
                                    splitDataset.manualId = new ObjectId().toString()
                                    splitDataset.resourceId = resourceId
                                    splitDataset.profileId = profileId
                                    splitDataset.allowMapping = Boolean.FALSE

                                    if (splitDataset.additionalQuestionAnswers) {
                                        splitDataset.additionalQuestionAnswers.put("profileId", profileId)
                                        splitDataset.additionalQuestionAnswers.put("comment", "Special rule: ${specialRule.specialRuleId}")
                                    } else {
                                        splitDataset.additionalQuestionAnswers = [profileId: profileId, comment: "Special rule: ${specialRule.specialRuleId}"]
                                    }
                                    fractionDatasets.add(splitDataset)
                                }
                            }
                        }
                    }
                    dataset.allImportDisplayFields = null
                }

                if (newDatasets && fractionDatasets) {
                    newDatasets.addAll(fractionDatasets)
                }

                if (newDatasets && !newDatasets.isEmpty()) {
                    if(!updateQuant){
                        List<Dataset> mutatedWithConstructionsDatasets = getMutatedWithConstructionsDatasets(newDatasets, indicator)

                        if (mutatedWithConstructionsDatasets) {
                            newDatasets.addAll(mutatedWithConstructionsDatasets)
                        }
                        if (entity.datasets) {
                            if (!useOldImportMapper) {
                                importMapperService.combineDescriptiveDatasets(newDatasets, importMapper)
                            }
                            Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, importMapper.skipCombineRowLimit, session)
                            IncrementalImportResolverReturn resolvedIncrementalImportMap = incrementalImportResolver(newDatasets, entity.datasets, incrementalImport, skipCombineRowLimit)
                            newDatasets = resolvedIncrementalImportMap?.newDatasets
                            importError = resolvedIncrementalImportMap?.importError
                        }
                    }
                    if (!importError) {
                        newDatasets.each { Dataset dataset ->
                            if (dataset.quantity && (dataset.quantity % 1D) != 0D) {
                                if (dataset.quantity >= 1D) {
                                    dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()
                                } else {
                                    dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(3, RoundingMode.HALF_UP).toDouble()
                                }
                                dataset.answerIds = [dataset.quantity.toString()]
                            }
                        }
                        if (!updateQuant) {
                            if (!useOldImportMapper) {
                                importMapperService.resolveIncrementalImportForDescriptiveDatasets(newDatasets, entity, incrementalImport)
                            }
                            saveDatasets(entity, session, newDatasets, indicatorId, trainingDataCountry)
                        }
                    }

                } else {
                    importError = "No datapoints to import. Have at least one row mapped to continue."
                }
                logMemUsage()
                if (importError) {
                    flash.errorAlert = importError
                    redirect action: "resolver", params: [entityId: entityId, childEntityId: childEntityId,importError:importError]
                } else if (updateQuant) {
                    session.setAttribute("ifcDatasets", !newDatasets.isEmpty() ? newDatasets : datasets)
                    redirect action: "updateQuant", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId,queryId: queryId]
                }else if ("query".equals(importMapper.arrivalPage)) {
                    removeAllSessionAttributes(session)
                    redirect controller: "query", action: "form", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId, queryId: queryId, ifcImport: true]
                } else {
                    removeAllSessionAttributes(session)
                    redirect controller: "design", action: "results", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId]
                }
            }
        } else {
            String noDatasetsMessage

                if (skipMapping) {
                    noDatasetsMessage = message(code: "importMapper.fastAndSilent.not_available")
                } else {
                    noDatasetsMessage = "Parsed ${session?.getAttribute(Constants.FILE_EXTENSION) ?: "Excel or gbXML"} file did not contain valid material definitions. Please verify the file"
                }
                redirect action: "main", params: [applicationId : session?.getAttribute("applicationId"),
                                                  importMapperId: importMapper?.importMapperId, errorMessage: noDatasetsMessage]
        }
    }

    private IncrementalImportResolverReturn incrementalImportResolver(List<Dataset> newDatasets, Set<Dataset> existingDataset, Boolean incrementalImport, Integer skipCombineRowLimit, Map existingDataMap = null, List<String> ignoreDatasetList = null ) {
        String importError = ""
        IncrementalImportResolverReturn returnable = new IncrementalImportResolverReturn()
        if (incrementalImport) {
            Map<String, Integer> queryIdAndDatasetAmount = [:]
            if(ignoreDatasetList){
                newDatasets = newDatasets.findAll({!ignoreDatasetList.contains(it.manualId)})
            }

            newDatasets?.each { Dataset d ->
                boolean removeFromDesign = existingDataMap && existingDataMap.containsKey(d.manualId)
                if (d.queryId && !removeFromDesign) {
                    Integer existing = queryIdAndDatasetAmount.get(d.queryId)
                    if (existing) {
                        existing = existing + 1
                    } else {
                        existing = 1
                    }
                    queryIdAndDatasetAmount.put((d.queryId), existing)
                }
            }

            existingDataset.each { Dataset d ->
                if (d.queryId) {
                    Integer existing = queryIdAndDatasetAmount.get(d.queryId)
                    if (existing) {
                        existing = existing + 1
                    } else {
                        existing = 1
                    }
                    queryIdAndDatasetAmount.put((d.queryId), existing)
                }
            }

            queryIdAndDatasetAmount.each { String query, Integer datasetAmount ->
                if (datasetAmount > skipCombineRowLimit) {
                    Query q = queryService.getQueryByQueryId(query, true)
                    importError = "${importError ? importError : ""} ${message(code:"importMapper.rowLimitExceed",args:[q?.localizedName,datasetAmount,skipCombineRowLimit])}.<br />"
                }
            }
            if (existingDataMap) {
                Set<String> datasetToRemove = []
                existingDataMap.each { k,v ->
                    Dataset datasetToChange = existingDataset.find({it.manualId == v})
                    Dataset newQuantDataset = newDatasets?.find({ it.manualId == k })
                    if(datasetToChange && newQuantDataset && !datasetToChange.locked){
                        Double newQuant = newQuantDataset.quantity
                        datasetToChange.quantity = newQuant
                        datasetToChange.answerIds = [newQuant.toString()]
                        existingDataset.addAll([datasetToChange])
                        datasetToRemove.addAll(newQuantDataset.manualId)
                    }
                }
                newDatasets = newDatasets?.findAll({!datasetToRemove.contains(it.manualId)})
            }
            newDatasets?.addAll(existingDataset)
        } else {
            List<Dataset> lockedDatasets = existingDataset.findAll({ it.locked })?.toList()

            if (lockedDatasets) {
                newDatasets.addAll(lockedDatasets)
            }
        }

        returnable.newDatasets = newDatasets
        returnable.importError = importError
        return returnable
    }

    private List<Dataset> getMutatedWithConstructionsDatasets (List<Dataset> newDatasets,Indicator indicator) {
        List<Dataset> mutatedWithConstructionsDatasets = []
        if(newDatasets && indicator){
            if (newDatasets.find({ it.uniqueConstructionIdentifier })) {
                List<Dataset> constructionDatasets = newDatasets.findAll({ it.uniqueConstructionIdentifier })
                int i = 0

                for (Dataset cD in constructionDatasets) {
                    cD.seqNr = i
                    i++;
                }

                ResourceCache resourceCache = ResourceCache.init(constructionDatasets)
                for (Dataset constructionDataset: constructionDatasets) {
                    Construction construction = constructionService.getConstruction(resourceCache.getResource(constructionDataset)?.constructionId)
                    if (construction) {
                        List<Dataset> datasetsForConstruction = constructionService.getFormattedConstructionDatasets(construction, constructionDataset, indicator)

                        if (datasetsForConstruction) {
                            mutatedWithConstructionsDatasets.addAll(datasetsForConstruction)
                        }
                    }
                }
            }
        }

        return mutatedWithConstructionsDatasets
    }

    def updateQuant() {
        def entityId = params.entityId
        def childEntityId = params.childEntityId
        def indicatorId = params.indicatorId
        def queryId = params.queryId
        def datasets = session?.getAttribute("ifcDatasets")
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        Map<String,List<String>> allmatchesCases = [:]
        Set<Dataset> entityDatasets = []
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        String warningMessage = session?.getAttribute("warningMessage")

        try {
            if (temporaryImportData.importDataPerStep) {
                temporaryImportData.importDataPerStep.put(7, datasets?.size())
            } else {
                temporaryImportData.importDataPerStep = [7: datasets?.size(),datasets:datasets]
            }
            Entity entity = entityService.getEntityById(entityId)
            Entity childEntity = entityService.getEntityById(childEntityId)
            Indicator indicator = session?.getAttribute("indicator") ?: indicatorService.getIndicatorByIndicatorId(indicatorId,true)
            Set<Dataset> changedQuantity = []
            Set<Dataset> newData = []
            Set<Dataset> unchangedQuantity = []
            Set<Dataset> multipleFoundData = []
            Set<Dataset> lockedData = []
            String defaultQueryId = importMapper?.questionMappingRules?.find({
                it.defaultTargetForEntityClass?.contains(entity.entityClass)
            })?.target?.get("queryId")
            Query query = queryService.getQueryByQueryId(defaultQueryId, Boolean.TRUE)
            List<Question> questions = query?.getAllQuestions()
            List<Question> additionals = []

            questions?.each { Question q ->
                List<Question> showInMapping = q.getAdditionalQuestions(indicator, null, null, entity)?.findAll({ Question aq -> aq.showInMapping })

                if (showInMapping) {
                    additionals.addAll(showInMapping)
                }
            }
            if (childEntity) {
                entityDatasets = childEntity.datasets
                identifyImportingData(datasets, entityDatasets,changedQuantity,unchangedQuantity,multipleFoundData,lockedData,newData,allmatchesCases)
            }

            [entityId:entityId,temporaryImportData:temporaryImportData,datasets:datasets,queryId:queryId,indicator:indicator,
             indicatorId: indicatorId,childEntity:childEntity,entity: entity,entityDatasets:entityDatasets,additionalQuestions:additionals?.unique(),
             unchangedQuantity:unchangedQuantity,changedQuantity:changedQuantity,multipleFoundData:multipleFoundData,newData:newData,
             lockedData:lockedData, showSteps:importMapper?.showSteps, currentStep: 9,warningMessage:warningMessage,allmatchesCases:allmatchesCases]
        } catch(e) {
            loggerUtil.error(log,"IMPORTMAPPER: updateQuant error:", e)
            flashService.setErrorAlert("IMPORTMAPPER: updateQuant error:", true)
        }

    }

    def saveQuantUpdate() {
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String ingoreIfcDataset = params.ingoreIfcDataset
        Map<String, String[]> paramMap = request.getParameterMap()
        List<Dataset> newDatasets
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        String warningMessage = session?.getAttribute("warningMessage")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        Boolean incrementalImport = session?.getAttribute("incrementalImport")
        try {
            if (paramMap) {
                //Updating the quantity of datasets based of the paramMap
                newDatasets = importMapperService.setParamValuesForDatasets(datasets, paramMap)
            }
            Entity entity = entityService.getEntityById(entityId)
            Entity childEntity = entityService.getEntityById(childEntityId)
            String trainingDataCountry = entity?.country
            Indicator indicator = session?.getAttribute("indicator") ?: indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

            if (newDatasets && childEntity) {
                String importError
                // Handling the change of quantity in new datasets while merging with old dataset
                if (childEntity.datasets) {
                    Map existingDataMap = findMappedValueFromParams(paramMap)
                    List<String> ignoreDatasetList
                    if(ingoreIfcDataset){
                        ignoreDatasetList = ingoreIfcDataset.tokenize(".")
                    }

                    Integer skipCombineRowLimit = importMapperService.getLimitAccordingToUsedLicenseFeature(entityId, indicator, importMapper.skipCombineRowLimit, session)
                    IncrementalImportResolverReturn resolvedIncrementalImportMap = incrementalImportResolver(newDatasets, childEntity.datasets, incrementalImport, skipCombineRowLimit, existingDataMap, ignoreDatasetList)
                    newDatasets = resolvedIncrementalImportMap?.newDatasets
                    importError = resolvedIncrementalImportMap?.importError
                    List<Dataset> mutatedWithConstructionsDatasets = getMutatedWithConstructionsDatasets(newDatasets, indicator)

                    if (mutatedWithConstructionsDatasets) {
                        newDatasets.addAll(mutatedWithConstructionsDatasets)
                    }
                }
                if (!importError) {
                    newDatasets.each { Dataset dataset ->
                        if (dataset.quantity && (dataset.quantity % 1D) != 0D) {
                            if (dataset.quantity >= 1D) {
                                dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(2, RoundingMode.HALF_UP).toDouble()
                            } else {
                                dataset.quantity = BigDecimal.valueOf(dataset.quantity).setScale(3, RoundingMode.HALF_UP).toDouble()
                            }
                            dataset.answerIds = [dataset.quantity.toString()]
                        }
                    }
                    saveDatasets(childEntity, session, newDatasets, indicatorId, trainingDataCountry)
                    removeAllSessionAttributes(session)
                }
                if (importError) {
                    flash.errorAlert = importError
                    redirect action: "resolver", params: [entityId: entityId, childEntityId: childEntityId,importError: importError]
                } else if ("query".equals(importMapper.arrivalPage)) {
                    redirect controller: "query", action: "form", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId, queryId: queryId, ifcImport: true]
                } else {
                    redirect controller: "design", action: "results", params: [entityId: entityId, childEntityId: childEntityId, indicatorId: indicatorId]
                }
            } else {
                warningMessage = "something went wrong, please contact admin."
                flash.errorAlert = warningMessage
                log.warn("IMPORTMAPPER: saveQuantUpdate error: newDatasets OR childEntity with id ${childEntityId} not found")
                redirect action: "main", params: [entityId: entityId, childEntityId: childEntityId,warningMessage:warningMessage]
            }
        } catch (e) {
            loggerUtil.error(log, "IMPORTMAPPER: saveQuantUpdate error", e)
            flashService.setErrorAlert("Error in IMPORTMAPPER saveQuantUpdate: ${e.message}", true)
            redirect action: "main", params: [entityId: entityId, childEntityId: childEntityId]
        }

    }
    /*
    identifyImportingData use to identify newly imported datasets with existing datasets of the design when updateQuant is selected by user
    *param newDatasets: newly imported datasets
    *param oldDatasets: datasets existing from target design
    *param changedQuant: contains list of identified datasets but has changes in quantity & not locked (from old datasets), initiated as empty
    *param unchangedQuant: contains list of identified datasets and unchanged quantity & not locked (from old datasets), initiated as empty
    *param multipleFoundData: new datasets coming from import that are matched by more than 1 existing dataset, initiated as empty
    *param lockedData: contains list of identified datasets but locked (from old datasets), initiated as empty
    *param newData: contains list of new datasets (no matches found), initiated as empty
    *param existingData: Map of matching old dataset vs new dataset: [newDatasetId:oldDatasetId], initiated as empty. Used for later in saveQuantUpdate > incrementalImportResolver
    *IDENTIFICATION LOGIC:
        1a. It matches for updating based on importMapper.quantityUpdateMandatoryMatchingFields
        1b. If more than 1 result found, loop again based on importMapper.quantityUpdateOptionalMatchingFields
        2. It matches automatically items where single hit found. It never matches automatically for updating where more than 1 found.
    */
    private identifyImportingData(List<Dataset> newDatasets, Set<Dataset> oldDatasets,Set<Dataset> changedQuant, Set<Dataset> unchangedQuant,Set<Dataset> multipleFoundData,Set<Dataset> lockedData,Set<Dataset> newData, Map<String,Set<String>> allmatchesCases) {
        try {
            long now = System.currentTimeMillis()
            if (newDatasets) {
                ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
                List<String> mandatoryMatchingFields = importMapper.quantityUpdateMandatoryMatchingFields
                List<String> optionalMatchingFields = importMapper.quantityUpdateOptionalMatchingFields
                Map<String,Map<String,String>> oldDatasetsMapImportFields = oldDatasets?.collectEntries({
                    [(it.manualId): datasetService.getDatasetImportFieldsById(it.datasetImportFieldsId)?.importFields?.last()]
                })
                Set<String> foundUnchangedQuantDatasetId = []
                newDatasets.each { dataset ->
                    if(dataset && dataset.manualId){
                        String datasetManualId = dataset.manualId
                        Map<String,String> importValueForThisDataset = datasetService.getDatasetImportFieldsById(dataset.datasetImportFieldsId)?.importFields?.last()
                        Map<String,Map<String,String>> found = oldDatasetsMapImportFields?.findAll({
                            it.value && matchingValueBasedOnMap(it.value, importValueForThisDataset, mandatoryMatchingFields)
                        })
                        Map<String,Map<String,String>> foundOptional = [:]

                        if(found?.size() > 1){
                            found = found.findAll({  it.value && matchingValueBasedOnMap(it.value,importValueForThisDataset,optionalMatchingFields) })
                        }

                        if (found?.size() == 1) {
                            String matchDatasetId = found.keySet()?.first()
                            Dataset matchDataset = oldDatasets?.find({it.manualId == matchDatasetId})
                            if (matchDataset?.locked) {
                                lockedData.add(dataset)
                            } else if (matchDataset?.quantity == dataset.quantity) {
                                unchangedQuant.add(dataset)
                                foundUnchangedQuantDatasetId.addAll(found.keySet())
                            } else {
                                changedQuant.add(dataset)
                            }
                            allmatchesCases.put(datasetManualId, found.keySet())

                        } else if(found?.size() > 1) {
                            Set<Dataset> foundDatasetSameQuant = oldDatasets?.findAll({found.keySet().contains(it.manualId) && it.quantity == dataset.quantity})
                            Set<String> foundDatasetSameQuantId = foundDatasetSameQuant?.collect({it.manualId})?.toSet()
                            if(foundDatasetSameQuant?.size() == 1){
                                unchangedQuant.add(dataset)
                                foundUnchangedQuantDatasetId.addAll(foundDatasetSameQuantId)
                                allmatchesCases.put(datasetManualId,foundDatasetSameQuantId)
                            } else if(foundDatasetSameQuant){
                                multipleFoundData.add(dataset)
                                allmatchesCases.put(datasetManualId, foundDatasetSameQuantId)
                            } else {
                                multipleFoundData.add(dataset)
                                allmatchesCases.put(datasetManualId, found.keySet())
                            }
                        } else {
                            newData.add(dataset)
                        }
                    }
                }
                // Need to run the initial mapping first to get all datapoint mapped out, and at this step resolving the
                // ambiguous data for the second time to strip out all unchanged data that is mapped
                if (unchangedQuant && foundUnchangedQuantDatasetId && allmatchesCases && (multipleFoundData || changedQuant)) {
                    Map<String,Set<String>> modifiedMatchesCase = [:]
                    allmatchesCases.each { String newDataId,Set<String> oldDataListId ->
                        if (oldDataListId && foundUnchangedQuantDatasetId && !unchangedQuant.find({it.manualId == newDataId}) && foundUnchangedQuantDatasetId.findAll({oldDataListId.contains(it)})) {
                            Set<String> oldDataListIdFiltered = oldDataListId.findAll({!foundUnchangedQuantDatasetId.contains(it)})
                            Dataset duplicatedDatasetMultiple = multipleFoundData.find {it.manualId == newDataId}
                            Dataset duplicatedDatasetSingle = changedQuant.find {it.manualId == newDataId}
                            if (oldDataListIdFiltered?.size() == 1) {
                                if (duplicatedDatasetMultiple) {
                                    multipleFoundData.remove(duplicatedDatasetMultiple)
                                    changedQuant.addAll(duplicatedDatasetMultiple)
                                    modifiedMatchesCase.put(newDataId,oldDataListIdFiltered)
                                } else if (duplicatedDatasetSingle) {
                                    changedQuant.remove(duplicatedDatasetSingle)
                                    newData.addAll(duplicatedDatasetSingle)
                                    modifiedMatchesCase.put(newDataId,null)
                                }
                            } else if (oldDataListIdFiltered) {
                                modifiedMatchesCase.put(newDataId,oldDataListIdFiltered)
                            } else {
                                if (duplicatedDatasetSingle) {
                                    changedQuant.remove(duplicatedDatasetSingle)
                                    newData.addAll(duplicatedDatasetSingle)
                                    modifiedMatchesCase.put(newDataId,null)
                                }
                            }
                        }
                    }
                    modifiedMatchesCase?.each {String key, Set<String> values ->
                        if(key && values) {
                            allmatchesCases.put(key,values)
                        } else if (key) {
                            allmatchesCases.remove(key)
                        }
                    }
                }

            }
            log.info("time taken for identifyImportingData ${System.currentTimeMillis() - now}")
        } catch (e) {
            loggerUtil.error(log, "IMPORTMAPPER: identifyImportingData error:", e)
            flashService.setErrorAlert("MPORTMAPPER: identifyImportingData error: ${e.message}", true)
        }
    }
    /*
    matchingValueBasedOnMap used nested in findAll function to compare 2 map based on a list of dynamic key values
    *param firstMap: first map to compare
    *param secondMap: second map to compare
    *param keyValuesList: List of key to get value and compare between 2 lists
    */
    private Boolean matchingValueBasedOnMap(Map<String,String> firstMap,Map<String,String> secondMap,List<String> keyValuesList){
        Boolean isMatched = Boolean.TRUE
        if(firstMap && secondMap && keyValuesList){
            keyValuesList?.each{keyTxt ->
                // if any of the maps contain the field value, comparison should be executed, if both are are null the skip comparison
                if((firstMap.get(keyTxt) || secondMap.get(keyTxt)) && !firstMap.get(keyTxt)?.equalsIgnoreCase(secondMap.get(keyTxt))){
                    isMatched = Boolean.FALSE
                }
            }
        } else {
            isMatched = Boolean.FALSE
        }
        return isMatched
    }
    /*
       findMappedValueFromParams try to find the new map for newDataset and existingDataset based on paramMap sent from request
       *param paramMap: coming from checkbox in UI
       */
    private Map findMappedValueFromParams(Map<String,String[]> paramMap) {
        Map returnMap = [:]
        try {
            if(paramMap){
                paramMap.each { String paramName, String[] paramValue ->
                    if (paramName.contains('mappingId')) {
                        List<String> valueList = paramValue.toList()
                        List<String> tokenizedList = valueList?.size() > 0 ? valueList.get(0).tokenize(".") : null
                        if(tokenizedList?.size() == 2){
                            String newDatasetId = tokenizedList[0]
                            String oldDatasetId = tokenizedList[1]
                            if(newDatasetId && oldDatasetId){
                                returnMap.put(newDatasetId,oldDatasetId)
                            }
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "IMPORTMAPPER: findMappedValueFromParams error:", e)
            flashService.setErrorAlert("MPORTMAPPER: findMappedValueFromParams error: ${e.message}", true)
        }
        return returnMap
    }

    def temporaryCalculations() {
        User user = userService.getCurrentUser()
        Boolean apiExcelAllowed = Boolean.FALSE

        if (user) {
            List<License> licenses = userService.getUsableAndManagedLicenses(user)
            Feature apiSampleExcel = featureService.getFeatureByFeatureId(Feature.API_SAMPLE_EXCEL)

            if (licenses && !licenses.isEmpty() && licenses.find({
                it.licensedFeatureIds.contains(apiSampleExcel?.id?.toString())
            })) {
                apiExcelAllowed = Boolean.TRUE
            }
        }
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        [temporaryImportData: temporaryImportData, apiExcelAllowed: apiExcelAllowed]
    }

    private Entity saveDatasets(Entity entity, HttpSession session, List<Dataset> datasets, String indicatorId, String trainingDataCountry = null, Boolean tempCalc = Boolean.FALSE) {
        Indicator indicator = session?.getAttribute("indicator") ?: indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

        if (session && session?.getAttribute("trainAdaptiveRecognition") && !tempCalc) {
            ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
            String applicationId = session?.getAttribute("applicationId")
            String trainingComment = session?.getAttribute("trainingComment")
            String fileName = session?.getAttribute(Constants.IMPORT_FILENAME)
            String originatingSystem = session?.getAttribute(Constants.ORIGINATING_SYSTEM)
            trainAdaptiveRecognition(datasets, applicationId, importMapper?.importMapperId, trainingComment, originatingSystem, fileName, indicator, trainingDataCountry)
        }
        List<String> uniqueQueryIds = datasets?.collect({ it.queryId })?.unique()
        List<Indicator> indicatorsToSave = []

        if (!tempCalc) {
            uniqueQueryIds?.each { String queryId ->
                List<Indicator> indicators = indicatorService.getIndicatorsWithSameQuery(entity, queryId)

                if (indicators) {
                    indicatorsToSave.addAll(indicators)
                }
            }
        }
        indicatorsToSave = indicatorsToSave?.unique({it.indicatorId})
        Map<String, QueryLastUpdateInfo> entityQueryLastUpdateInfos = entity?.queryLastUpdateInfos

        uniqueQueryIds?.each { String queryId ->
            if (entityQueryLastUpdateInfos) {
                QueryLastUpdateInfo queryLastUpdateInfo = entityQueryLastUpdateInfos.get(queryId)

                if (queryLastUpdateInfo) {
                    queryLastUpdateInfo.usernames.add(0, userService?.getCurrentUser()?.name)
                    queryLastUpdateInfo.updates.add(0, new Date())

                    if (queryLastUpdateInfo.updates.size() > 3) {
                        queryLastUpdateInfo.updates.remove(queryLastUpdateInfo.updates.size() - 1)
                        queryLastUpdateInfo.usernames.remove(queryLastUpdateInfo.updates.size() - 1)
                    }
                    entityQueryLastUpdateInfos.put(queryId, queryLastUpdateInfo)
                } else {
                    entityQueryLastUpdateInfos.put(queryId, new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()]))
                }
            } else {
                entityQueryLastUpdateInfos = [(queryId):  new QueryLastUpdateInfo(usernames: [userService?.getCurrentUser()?.name], updates: [new Date()])]
            }
        }
        entity?.queryLastUpdateInfos = entityQueryLastUpdateInfos
        entity = datasetService.preHandleDatasets(entity, datasets, indicatorsToSave, Boolean.TRUE, Boolean.FALSE, tempCalc)

        if (tempCalc) {
            entity = newCalculationServiceProxy.calculate(null, indicator.indicatorId, null, entity, tempCalc)
        } else {
            Entity parent = entity.getParentById()
            List<String> transportDistanceQuestionIds = Constants.TransportDistanceQuestionId.list()
            transportDistanceQuestionIds = datasetService.handlePopulatingDefaultsForTransportationLeg2(parent, transportDistanceQuestionIds)
            List<String> alreadySetQuestions = []
            AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib')

            indicatorsToSave?.each { Indicator ind ->
                List<String> additionalQuestionIds = ind.indicatorQueries?.findAll({uniqueQueryIds.contains(it.queryId)})?.collect({ it.additionalQuestionIds })?.flatten()?.unique()
                List<Question> additionalQuestions = constructionService.getAdditionalQuestions(additionalQuestionIds)

                if (additionalQuestions) {
                    additionalQuestions.each { Question q ->
                        if (!alreadySetQuestions.contains(q.questionId)) {
                            alreadySetQuestions.add(q.questionId)
                            List<Dataset> datasetsList = entity.datasets?.toList()
                            if (datasetsList) {
                                ResourceCache resourceCache = ResourceCache.init(datasetsList)
                                for (Dataset it: datasetsList)
                                {
                                    if (uniqueQueryIds.contains(it.queryId)) {
                                        if (!it.additionalQuestionAnswers) {
                                            it.additionalQuestionAnswers = [:]
                                        }

                                        if (!it.additionalQuestionAnswers.get(q.questionId)) {
                                            if (transportDistanceQuestionIds.contains(q.questionId) && entity.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)) {
                                                it.additionalQuestionAnswers.put((q.questionId), "default")
                                            } else if ("serviceLife".equals(q.questionId)) {
                                                def answer

                                                if (entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE)) {
                                                    answer = "default"
                                                } else {
                                                    answer = newCalculationServiceProxy.getServiceLife(it, resourceCache.getResource(it), entity, indicator)?.toString()
                                                }

                                                if (answer) {
                                                    it.additionalQuestionAnswers.put((q.questionId), answer)
                                                }
                                            } else {
                                                def answer = "${aq.additionalQuestionAnswer(additionalQuestion: q, resource: resourceCache.getResource(it), entity: entity)}"

                                                if (answer) {
                                                    it.additionalQuestionAnswers.put((q.questionId), answer)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                entity = newCalculationServiceProxy.calculate(null, ind.indicatorId, parent, entity)
            }
            entity.disabledSections = entityService.resolveDisabledSections(entity)
            entity = entity.merge(flush: true)
        }
        return entity
    }

    def toMain() {
        String importMapperId = session?.getAttribute(Constants.IMPORT_MAPPER)?.importMapperId
        String applicationId = session?.getAttribute("applicationId")
        removeAllSessionAttributes(session)
        redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapperId]
    }

    private void trainAdaptiveRecognition(List<Dataset> datasets, String applicationId, String importMapperId, String trainingComment, String originatingSystem, String originalFileName, Indicator indicator, String trainingDataCountry) {
        if (datasets && importMapperId && applicationId) {
            User user = userService.getCurrentUser()
            String organization = userService.getAccount(user)?.companyName

            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset dataset: datasets) {
                if (!dataset.importMapperCompositeMaterial && dataset.trainingMatchData && dataset.resourceId && dataset.allowMapping && user) {
                    String profileId = dataset.profileId ? dataset.profileId : resourceCache.getResource(dataset)?.profileId
                    List<ImportMapperTrainingData> duplicatesForUserAndMaterial = importMapperTrainingDataService.getDuplicateTrainingDataForUser(dataset.trainingMatchData, user.id.toString())

                    if (duplicatesForUserAndMaterial) {
                        duplicatesForUserAndMaterial.each { ImportMapperTrainingData imp ->
                            imp.delete(flush: true)
                        }
                    }
                    importMapperTrainingDataService.saveTrainingData(dataset.trainingData, applicationId, importMapperId, trainingComment, originatingSystem, originalFileName, dataset.resourceId, profileId, null, session, trainingDataCountry, user.id.toString(), organization)
                }
            }
        }
    }

    private void removeAllSessionAttributes(HttpSession session) {
        if (session) {
            session.removeAttribute(Constants.IMPORT_FILENAME)
            session.removeAttribute(Constants.IMPORT_FILEPATH)
            session.removeAttribute("indicator")
            session.removeAttribute(Constants.IMPORT_MAPPER)
            session.removeAttribute("applicationId")
            session.removeAttribute(Constants.ORIGINATING_SYSTEM)
            session.removeAttribute("useAdaptiveRecognition")
            session.removeAttribute(Constants.PRESET_FILTER)
            session.removeAttribute("useCollapse")
            session.removeAttribute("collapseAll")
            session.removeAttribute("trainAdaptiveRecognition")
            session.removeAttribute("trainingComment")
            session.removeAttribute("ifcDatasets")
            session.removeAttribute("collapseGroupers")
            session.removeAttribute("amountOfGroupedByData")
            session.removeAttribute("filterRemovedSize")
            session.removeAttribute("discardedMaterialsWithValues")
            session.removeAttribute("skippedOrRejected")
            session.removeAttribute("combineSuccessful")
            session.removeAttribute("skipMapping")
            session.removeAttribute("skipMappingPassed")
            session.removeAttribute("newDesign")
            session.removeAttribute("newChildId")
            session.removeAttribute("combineManually")
            session.removeAttribute("removeEmptyData")
            session.removeAttribute("headers")
            session.removeAttribute("incrementalImport")
            session.removeAttribute("reviewDataIdentification")
            session.removeAttribute(Constants.TEMPORARY_IMPORT_DATA)
            session.removeAttribute("temporaryCalculation")
            session.removeAttribute("warningMessage")
            session.removeAttribute("tooGenericDatasets")
            session.removeAttribute("skippedCombine")
            session.removeAttribute(Constants.FILE_EXTENSION)
            session.removeAttribute("identifiedDatasets")
            session.removeAttribute("genericDatasetsWarnings")
            session.removeAttribute("okMessage")
            session.removeAttribute("warningMessage")
            session.removeAttribute("materialHeading")
            session.removeAttribute("compositePercentual")
            session.removeAttribute("compositeDatasets")
            session.removeAttribute("discardedByRemovalRulesets")
            session.removeAttribute("bimWarningHeaders")
            session.removeAttribute(Constants.PRESET_FILTER)
            session.removeAttribute("bimWarnings")
            session.removeAttribute('bimModelChecker')
            session.removeAttribute("newDesignRibaStage")
            session.removeAttribute(Constants.NO_LICENSE)
            session.removeAttribute("entityIds")
            session.removeAttribute("updateQuant")
            session.removeAttribute("existingDataMap")

        }
    }

    def cancel() {
        String newChildId = session?.getAttribute("newChildId")
        String entityId = session?.getAttribute(Constants.ENTITY_ID)
        String indicatorId = session?.getAttribute(Constants.INDICATOR_ID)
        String applicationId = session?.getAttribute("applicationId")
        String bimModelChecker = session?.getAttribute("bimModelChecker")
        String importMapperId = ((ImportMapper) session?.getAttribute(Constants.IMPORT_MAPPER))?.importMapperId

        if (newChildId) {
            entityService.delete(newChildId)
        }
        removeAllSessionAttributes(session)
        redirect action: "main", params: [applicationId: applicationId, importMapperId: importMapperId,indicatorId:indicatorId, entityId:entityId,bimModelChecker: bimModelChecker]
    }

    private List<Dataset> splitComposites(Dataset dataset, String compositeSplitBySeparator) {
        List<Dataset> datasetsForComposite = []

        if (dataset.importDisplayFields?.get("IFCMATERIAL")?.contains(compositeSplitBySeparator)) {
            List<String> ifcMaterials = dataset.importDisplayFields.get("IFCMATERIAL").tokenize(compositeSplitBySeparator)
            dataset.importDisplayFields.put("IFCMATERIAL", ifcMaterials.get(0))

            for (int i = 1; i < ifcMaterials.size(); i++) {
                Dataset copy = datasetService.createCopy(dataset)
                copy.manualId = new ObjectId()
                copy.importDisplayFields.put("IFCMATERIAL", ifcMaterials.get(i))
                datasetsForComposite.add(copy)
            }
        }
        return datasetsForComposite
    }

    private void deleteImportMapperFile(HttpSession session) {
        String importFilePath = session?.getAttribute(Constants.IMPORT_FILEPATH)

        if (importFilePath) {
            File importFile = FileUtils.getFile(importFilePath)

            if (importFile) {
                importFile.delete()
            }
        }
    }

    def bimChecker() {
        List<Dataset> rejectedAndOkDatasets = session?.getAttribute("rejectedAndOkDatasets")
        List<Dataset> resolverDatasets = session?.getAttribute("ifcDatasets")
        //Double filteredByClassFilters = session.getAttribute("filterRemovedSize")
        //List<Dataset> identifiedDatasets = session.getAttribute("identifiedDatasets")
        List<String> headers = ["entity.localizedEntityClass", "issue_amount.bimChecker","number.in_model", "percent.in_model", "Explanation of issue", "recommend_action"]
        List<BimModelWarning> warnings = []
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        Integer amountToCountAgainst = temporaryImportData?.importDataPerStep?.get(2)
        Boolean bimModelCheckerStandAlone = session?.getAttribute('bimModelChecker') ?: Boolean.FALSE

        if (rejectedAndOkDatasets && !rejectedAndOkDatasets.isEmpty() && amountToCountAgainst) {
            warnings = importMapperService.getBimModelWarningsForDatasets(rejectedAndOkDatasets, resolverDatasets, amountToCountAgainst)
        }
        String data = "["
        Integer percentage
        Integer issues

        if (warnings && !warnings.isEmpty()) {
            //List<String> resolverManualIds = resolverDatasets.collect({ it.manualId })
            issues = warnings?.findAll({it.amountOfCombined && !it.dontShowInVisuals && !it.status?.equalsIgnoreCase("INFO")})?.collect({it.amountOfCombined})?.sum()
            session?.setAttribute("bimWarnings", warnings)
            session?.setAttribute("bimWarningHeaders", headers)
            /*

            Below is related to unused doughnut graph?

            Double warningsAmount = warnings?.findAll({it.amount && it.status.equalsIgnoreCase("WARN")})?.collect({it.amount})?.sum()
            Double errorsAmount = warnings?.findAll({it.amount && it.status.equalsIgnoreCase("error")})?.collect({it.amount})?.sum()
            Double unidentifiedAmount = resolverDatasets?.findAll({!it.resourceId && !it.importMapperPartialQuantification})?.size()
            Double identifiedAmount = identifiedDatasets?.size()
            data = "${data} ${identifiedAmount ?: 0}, ${unidentifiedAmount ?: 0}, ${warningsAmount ?: 0}, ${errorsAmount ?: 0}]"
            Double errorPoints = errorsAmount ? errorsAmount * -5 : 0
            Double warningPoints = warningsAmount ? warningsAmount * -2 : 0
            Double unidentifiedPoints = unidentifiedAmount ? unidentifiedAmount * -1 : 0
            Double toCalculateFrom = amountToCountAgainst != null && filteredByClassFilters != null ? amountToCountAgainst - filteredByClassFilters : 0

            if (toCalculateFrom) {
                percentage = (((toCalculateFrom + errorPoints + warningPoints + unidentifiedPoints) / toCalculateFrom) * 100)?.toInteger()

                if (percentage && percentage < 0) {
                    percentage = 0
                }
                log.debug("calculating bimchecker percentage: (${toCalculateFrom} + ${errorPoints} + ${warningPoints} + ${unidentifiedPoints}) / ${toCalculateFrom} * 100  =  ${percentage} ")

            } else {
                percentage = 0
            }
           */
        }
        render template: "bimchecker", model: [warnings: warnings, headers: headers, bimModelCheckerStandAlone:bimModelCheckerStandAlone,
                                               data:data, percentage:percentage, issues: issues, amountToCountAgainst: amountToCountAgainst]
    }

    def bimModelCheckerStandAlone() {
        TemporaryImportData temporaryImportData = session?.getAttribute(Constants.TEMPORARY_IMPORT_DATA)
        def entityId = params.entityId
        def childEntityId = params.childEntityId
        Entity entity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityByIdReadOnly(entityId)
        Indicator indicator = session?.getAttribute("indicator")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        if (importMapper) {
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")
            String defaultQueryId = importMapper?.questionMappingRules?.find({
                it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass)
            })?.target?.get("queryId")
            Query query = queryService.getQueryByQueryId(defaultQueryId, Boolean.TRUE)

            def compositeDatasets
            Double compositePercentual
            def partialQuantificationDatasets
            def notIdentifiedDatasets
            Double notIdentifiedPercentual
            List<Dataset> identifiedDatasets
            Double identifiedPercentual
            List<Dataset> tooGenericDatasets = session?.getAttribute("tooGenericDatasets")

            if (datasets) {
                importMapperService.calculatePercentageOfTotal(datasets, importMapper)
                compositeDatasets = datasets.findAll({ it.importMapperCompositeMaterial })
                loggerUtil.info(log, "Number of combosite data: ${compositeDatasets ? compositeDatasets.size() : 0}")

                if (compositeDatasets && !compositeDatasets.isEmpty()) {
                    compositeDatasets.sort({ it.percentageOfTotal }).reverse(true)

                    compositeDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        compositePercentual = compositePercentual ? compositePercentual + d.percentageOfTotal : d.percentageOfTotal
                    }
                    session?.setAttribute("compositeDatasets", compositeDatasets)
                    session?.setAttribute("compositePercentual", compositePercentual)
                }
                partialQuantificationDatasets = datasets.findAll({ it.importMapperPartialQuantification })
                loggerUtil.info(log, "Number of partial quantification data: ${partialQuantificationDatasets ? partialQuantificationDatasets.size() : 0}")

                notIdentifiedDatasets = datasets.findAll({
                    !it.resourceId && !it.importMapperCompositeMaterial && !it.importMapperPartialQuantification
                })
                loggerUtil.info(log, "Number of not identified data: ${notIdentifiedDatasets ? notIdentifiedDatasets.size() : 0}")

                if (notIdentifiedDatasets && !notIdentifiedDatasets.isEmpty()) {
                    notIdentifiedDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    notIdentifiedDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        notIdentifiedPercentual = notIdentifiedPercentual ? notIdentifiedPercentual + d.percentageOfTotal : d.percentageOfTotal
                    }

                    if (notIdentifiedPercentual) {
                        session?.setAttribute("notIdentifiedPercentual", notIdentifiedPercentual)
                    }
                }
                identifiedDatasets = datasets.findAll({
                    it.resourceId && !it.importMapperCompositeMaterial && !it.importMapperPartialQuantification
                })

                if (identifiedDatasets && !identifiedDatasets.isEmpty()) {
                    identifiedDatasets.sort({ it.percentageOfTotal }).reverse(true)
                    identifiedDatasets.findAll({ it.percentageOfTotal != null })?.each { Dataset d ->
                        identifiedPercentual = identifiedPercentual ? identifiedPercentual + d.percentageOfTotal : d.percentageOfTotal
                    }
                }
                loggerUtil.info(log, "Number of identified data: ${identifiedDatasets ? identifiedDatasets.size() : 0}")
                session?.setAttribute("identifiedDatasets", identifiedDatasets)
            }
            String okMessage
            String importMessage = getImportMessage(session)

            if (importMessage) {
                okMessage = okMessage ? "${okMessage} ${importMessage}" : importMessage
            }
            session?.setAttribute("okMessage", okMessage)

            String applicationId = session?.getAttribute("applicationId")

            String warnMessage
            String warningFromImport = session?.getAttribute("warningMessage")

            if (warningFromImport && !warningFromImport.isEmpty()) {
                if (warnMessage) {
                    warnMessage = warnMessage + "<br />${warningFromImport}"
                } else {
                    warnMessage = "${warningFromImport}"
                }
            }

            if (temporaryImportData) {
                Integer unidentifiedSize = notIdentifiedDatasets ? notIdentifiedDatasets.size() : 0
                Integer identifiedSize = identifiedDatasets ? identifiedDatasets.size() : 0

                if (temporaryImportData.importDataPerStep) {
                    temporaryImportData.importDataPerStep.put(6, datasets?.size())
                    temporaryImportData.importDataPerStep.put(7, identifiedSize)
                    temporaryImportData.importDataPerStep.put(8, unidentifiedSize)
                } else {
                    temporaryImportData.importDataPerStep = [7: identifiedSize, 8: unidentifiedSize]
                }
            }


            String bimcheckerUrl = "${controllerName}/bimChecker"

                [entity                       : entity, parentEntity: parentEntity,
                 entityId: entityId, showSteps: importMapper?.showSteps,
                 childEntityId                : childEntityId, indicator: indicator, queryId: defaultQueryId,
                 importMapperId               : importMapper.importMapperId, applicationId: applicationId,
                 temporaryImportData          : temporaryImportData,
              okMessage: okMessage, warningMessage: warnMessage, bimcheckerUrl: bimcheckerUrl]
        }
    }

    def renderNewCombinerFromCriteria() {
        List<String> userDefinedGroupers = JSON.parse(params.newCombineCriteria)
        List<String> specialCombineCriteria = JSON.parse(params.specialCombineCriteria)
        List<Dataset> datasets = session?.getAttribute("ifcDatasets")
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)

        Map<String, String> temp = JSON.parse(params.specialCombineRange)
        Map<String, Double> customSpecialCombineRange = [:]

        temp?.each { String key, String value ->
            if (DomainObjectUtil.isNumericValue(value)) {
                customSpecialCombineRange.put((key), DomainObjectUtil.convertStringToDouble(value))
            }
        }
        Integer datapointsAfterCombine
        Integer datasetsSize = datasets?.size()
        Map<String, List<String>> skipHeadingsForKey = [:]
        List<String> headings = []
        Map<String, List<ImportMapperCollapseGrouper>> grouperGroupsByClass = [:]
        List<ImportMapperCollapseGrouper> collapseGroupers

        if (userDefinedGroupers || specialCombineCriteria) {
            collapseGroupers = importMapperService.createCollapseGroupers(datasets, userDefinedGroupers, null,
                    null, null, importMapper, null, specialCombineCriteria, customSpecialCombineRange)
            session?.setAttribute("collapseGroupers", collapseGroupers)

            boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
            if (useOldImportMapper) {
            List<ImportMapperCollapseGrouper> groupersToRemove = []
            String usedKey
            List<String> importedClasses = []
            collapseGroupers?.each { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
                if (importMapperCollapseGrouper.collapseHeadingAndValue && !importMapperCollapseGrouper.collapseHeadingAndValue.isEmpty()) {
                    headings.addAll(importMapperCollapseGrouper.collapseHeadingAndValue.keySet().toList())

                    if (importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")) {
                        importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")?.toString())
                    } else {
                        usedKey = importMapperCollapseGrouper.collapseHeadingAndValue.keySet()?.toList()?.get(0)
                        importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get(usedKey)?.toString())
                    }
                } else {
                    groupersToRemove.add(importMapperCollapseGrouper)
                }
            }

            if (!groupersToRemove.isEmpty()) {
                collapseGroupers.removeAll(groupersToRemove)
            }

            importedClasses?.unique()?.each { String classAsString ->
                def found
                if (usedKey) {
                    found = collapseGroupers.findAll({
                        it.collapseHeadingAndValue?.get(usedKey)?.toString()?.equals(classAsString)
                    })
                } else {
                    found = collapseGroupers.findAll({
                        it.collapseHeadingAndValue?.get("CLASS")?.toString()?.equals(classAsString)
                    })
                }

                if (found) {
                    if (usedKey) {
                        String key = "${usedKey}: ${classAsString}"
                        grouperGroupsByClass.put(key, found.sort({ -it.datasetAmount }))
                    } else {
                        grouperGroupsByClass.put(classAsString, found.sort({ -it.datasetAmount }))
                    }
                }
            }
            } else {
                List<ImportMapperCollapseGrouper> resourceCollapseGroupers = collapseGroupers.findAll { !it.isDescriptive }
                grouperGroupsByClass = importMapperService.groupCollapseGroupersByClass(resourceCollapseGroupers)
                resourceCollapseGroupers?.each { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
                    if (importMapperCollapseGrouper.collapseHeadingAndValue && !importMapperCollapseGrouper.collapseHeadingAndValue.isEmpty()) {
                        headings.addAll(importMapperCollapseGrouper.collapseHeadingAndValue.keySet().toList())
                    }
                }
            }



            if (grouperGroupsByClass && !grouperGroupsByClass.isEmpty()) {
                grouperGroupsByClass.each { String key, List<ImportMapperCollapseGrouper> groups ->
                    headings.unique().each { String heading ->
                        if (!groups.find({ it.collapseHeadingAndValue?.get(heading) })) {
                            if (skipHeadingsForKey.get(key)) {
                                List<String> existing = skipHeadingsForKey.get(key)
                                existing.add(heading)
                                skipHeadingsForKey.put(key, existing)
                            } else {
                                skipHeadingsForKey.put(key, [heading])
                            }
                        }
                    }
                }
            }

            Integer groupers = collapseGroupers?.size()
            Integer collapseDatasets = collapseGroupers?.collect({ it.datasetAmount })?.sum()

            if (datasetsSize != null && collapseDatasets != null && groupers != null) {
                datapointsAfterCombine = datasetsSize - collapseDatasets + groupers
            }
        }

        String templateAsString = g.render(template: "/importMapper/combinerTable", model: [datasetsSize        : datasetsSize, datapointsAfterCombine: datapointsAfterCombine,
                                                                                            grouperGroupsByClass: grouperGroupsByClass?.sort({ it.key }), skipHeadingsForKey: skipHeadingsForKey,
                                                                                            groupByForCollapse  : headings?.unique(), skipCombineRowLimit: importMapper?.skipCombineRowLimit]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private def getAdditionalQuestion(Dataset constructionDataset, Indicator indicator, Map<String, List<Question>> additionalQuestionsPerQuestion){

        String constructionDatasetQueryId = constructionDataset.queryId
        String sectionId = constructionDataset.sectionId
        String questionId = constructionDataset.questionId
        List<Question> additionalQuestions

        if(indicator){
            if (additionalQuestionsPerQuestion?.containsKey(questionId)) {
                additionalQuestions = additionalQuestionsPerQuestion.get(questionId)
            } else {
                additionalQuestions = simulationToolService.getInheritedAdditionalQuestion(constructionDatasetQueryId, sectionId, questionId, indicator)
                additionalQuestionsPerQuestion.put(questionId, additionalQuestions ?: [])
            }
        }

        return [additionalQuestions: additionalQuestions, additionalQuestionsPerQuestion: additionalQuestionsPerQuestion]

    }

    private Dataset setAdditionalQuestionsToDataset(Dataset dataset, List<Question> additionalQuestions, Construction construction, Question mainQuestion){

        additionalQuestions?.each { Question additionalQuestion ->
            if (additionalQuestion.inheritToChildren) {

                if (construction.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, construction.additionalQuestionAnswers?.get(additionalQuestion.questionId))
                }
            } else {
                def answer

                if (additionalQuestion.questionId == "serviceLife") {
                    if (questionService.isWithPermanentServiceLife(mainQuestion)) {
                        answer = "permanent"
                    }
                } else {
                    answer = "${aq.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: datasetService.getResource(dataset))}"
                }

                if (answer != null) {
                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, answer)
                }
            }
        }

        return dataset
    }

    def removeOrReportTrainingData() {
        Map<String, String> returnable = [:]
        String trainingDataId = params.trainingDataId
        String status = ""

        if (trainingDataId) {
            User user = userService.getCurrentUser()
            ImportMapperTrainingData importMapperTrainingData = importMapperTrainingDataService.getTrainingDataById(trainingDataId)

            if (user && importMapperTrainingData) {
                Boolean remove = importMapperTrainingData.user == user.id?.toString()

                if (remove) {
                    importMapperTrainingData.delete(flush: true)
                    returnable.put("status", " - <i class=\"fa fa-check oneClickColorScheme\" aria-hidden=\"true\"></i> ${message(code: "importMapper.trainingData.mappingRemoved")}")
                } else {
                    if (!importMapperTrainingData.reportedAsBad) {
                        importMapperTrainingData.reportedAsBad = true
                        importMapperTrainingData.reportedAsBadByUser = user.id?.toString()
                        importMapperTrainingData.save(flush: true)
                    }
                    returnable.put("status", " - <i class=\"fa fa-check oneClickColorScheme\" aria-hidden=\"true\"></i> ${message(code: "importMapper.trainingData.mappingReported")}")
                }
                returnable.put("remove", remove ? "true" : "false")
            }
        }
        render([returnable: returnable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

    }

    def changeClassForDatasets(){
        String newClass = request.JSON.newClass
        String oldClass = request.JSON.oldClass
        String additionalCat = request.JSON.additionalCat
        ImportMapper importMapper = session?.getAttribute(Constants.IMPORT_MAPPER)
        def entityId = session?.getAttribute(Constants.ENTITY_ID)
        String entityClass = entityService.getEntityByIdReadOnly(entityId.toString())?.entityClass
        Map outputMap = [:]
        if(newClass && oldClass && importMapper){
            def datasets = session?.getAttribute("ifcDatasets")
            if(datasets){
                def datasetsNeedChange
                if(additionalCat){
                    datasetsNeedChange = datasets.findAll({}).toList()

                }else {
                    datasetsNeedChange = datasets.findAll({  }).toList()
                }
                def textMap = importMapperService.changeDatasetsClass(datasets,importMapper,newClass,entityClass,oldClass,additionalCat)
                if(textMap){
                    Query query = queryService.getQueryByQueryId(textMap.first())
                    QuerySection section = query?.getSections()?.find({ it.sectionId.equals(textMap[1])})
                    if(query && section){
                        outputMap = [query:query.localizedName, section:section.localizedName]
                    }
                }
            }
        }
        render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
}

