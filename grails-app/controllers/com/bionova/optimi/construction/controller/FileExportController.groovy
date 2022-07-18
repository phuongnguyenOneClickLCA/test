package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.PdfReplacementSetting
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.ValueReference
import com.bionova.optimi.util.PdfUtil
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import org.apache.catalina.connector.ClientAbortException
import org.apache.commons.io.FileUtils
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.interactive.form.PDField
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by pmm on 19.1.2016.
 */
class FileExportController {

    def loggerUtil
    def entityService
    def indicatorService
    def resultFormattingResolver
    def userService
    def wordDocumentService
    def fileExportService
    def optimiResourceService
    def valueReferenceService

    @Autowired
    EmailConfiguration emailConfiguration

    def createPdf() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId

        if (entityId && indicatorId) {
            Entity entity = entityService.readEntity(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (entity && indicator && "pdf".equals(indicator.exportType?.toLowerCase())) {
                String filePath = valueReferenceService.getValueForEntity(indicator.exportTemplate, entity, false)
                List<Dataset> allDatasets = entity.datasets?.toList()

                if (filePath && allDatasets) {
                    List<ResultCategory> resultCategories = indicator.getResolveResultCategories(entity?.parentEntityId ? entity.parentById : entity)
                    PDDocument pdfDocument
                    String fileName = "temp${entityId}.pdf"
                    File file = File.createTempFile(fileName, "pdf")

                    try {
                        File pdfFile = new File(filePath)
                        FileUtils.copyFile(pdfFile, file)
                        pdfDocument = PDDocument.load(file)
                    } catch (IOException ioe) {
                        loggerUtil.info(log, "Could not load file from path ${filePath}. Trying with url. Exception is ${ioe}")
                    }

                    if (!pdfDocument) {
                        try {
                            URL url = new URL(filePath)
                            FileUtils.copyURLToFile(url, file)
                            pdfDocument = PDDocument.load(file)
                        } catch (IOException) {
                            loggerUtil.error(log, "Could not load pdf template from url ${filePath} eather")
                        }
                    }

                    if (pdfDocument) {
                        List<PDDocument> sourceDocs = []
                        int sourceDocIndex = 0

                        indicator?.pdfReplacementSettings?.each { PdfReplacementSetting pdfReplacementSetting ->
                            List<EntityFile> entityFiles = valueReferenceService.getEntityFiles(pdfReplacementSetting.sourceDocument, entity)

                            if (entityFiles && !entityFiles.isEmpty()) {
                                EntityFile entityFile = entityFiles.get(0)

                                try {
                                    File sourceFile = new File(entityFile.name)
                                    FileUtils.writeByteArrayToFile(sourceFile, entityFile.data)

                                    sourceDocs.add(PDDocument.load(sourceFile))

                                    if (sourceDocs.get(sourceDocIndex)) {
                                        pdfReplacementSetting.replacePages?.each { String sourcePageNo, String targetPageNo ->
                                            PdfUtil.replacePage(pdfDocument, sourceDocs.get(sourceDocIndex), (targetPageNo.toInteger() - 1), (sourcePageNo.toInteger() - 1))
                                        }
                                    }
                                    sourceFile.delete()
                                } catch (IOException ioe) {
                                    loggerUtil.error(log, "Error in replacing pdf pages: ${ioe}")
                                }
                            } else {
                                String sourceFilePath = valueReferenceService.getValueForEntity(pdfReplacementSetting.sourceDocument, entity, false)

                                if (sourceFilePath) {
                                    try {
                                        File sourceFile = new File(sourceFilePath)
                                        sourceDocs.add(PDDocument.load(sourceFile))

                                        if (sourceDocs.get(sourceDocIndex)) {
                                            pdfReplacementSetting.replacePages?.each { String sourcePageNo, String targetPageNo ->
                                                PdfUtil.replacePage(pdfDocument, sourceDocs.get(sourceDocIndex), (targetPageNo.toInteger() - 1), (sourcePageNo.toInteger() - 1))
                                            }
                                        }
                                        // sourceFile.delete()
                                    } catch (IOException ioe) {
                                        loggerUtil.info(log, "Could not load file from path ${sourceFilePath}. Trying with url.")
                                    }
                                }
                            }
                            sourceDocIndex++
                        }
                        List<PDField> fields = PdfUtil.getFields(pdfDocument)

                        if (fields) {
                            List<String> fieldNames = fields.collect({ it.fullyQualifiedName })
                            List<ValueReference> valueReferences = fieldNames.collect({ String fieldName -> getFieldNameAsValueReference(fieldName) })
                            Boolean useIndicatorResults = fieldNames.find({
                                it.startsWith("@")
                            }) ? Boolean.TRUE : Boolean.FALSE
                            List<Question> questions = []

                            indicator.getQueries(entity)?.each { Query query ->
                                List<Question> foundQuestions = query.getAllQuestions()?.findAll({ Question q ->
                                    valueReferences?.findAll({
                                        it != null
                                    })?.collect({ it.questionId })?.contains(q.questionId) || "file".equals(q.inputType)
                                })

                                if (foundQuestions && !foundQuestions.isEmpty()) {
                                    questions.addAll(foundQuestions)
                                }
                            }

                            if (!questions.isEmpty() || useIndicatorResults) {
                                fieldNames.each { String fieldName ->
                                    if (fieldName.startsWith("@")) {
                                        String ruleId = fieldName.tokenize(".")[0].substring(1)
                                        String resultCategoryId = fieldName.tokenize(".")[1]
                                        CalculationRule calculationRule = indicator.getResolveCalculationRules(null, true)?.find({
                                            ruleId.equals(it.calculationRuleId)
                                        })

                                        if (!calculationRule) {
                                            calculationRule = indicator.getResolveCalculationRules(null, true)?.find({
                                                ruleId.equals(it.calculationRule)
                                            })
                                        }
                                        ResultCategory resultCategory

                                        if (!"total".equals(resultCategoryId)) {
                                            resultCategory = resultCategories?.find({
                                                resultCategoryId.equals(it.resultCategoryId)
                                            })

                                            if (!resultCategory) {
                                                resultCategory = resultCategories?.find({
                                                    resultCategoryId.equals(it.resultCategory)
                                                })
                                            }
                                        }
                                        Double answer

                                        if (calculationRule && resultCategory) {
                                            answer = entity.getCalculationResultObjects(indicator.indicatorId,
                                                    calculationRule.calculationRuleId, resultCategory.resultCategoryId)?.
                                                    sum({ CalculationResult calculationResult -> calculationResult.result })

                                        } else if (calculationRule && "total".equals(resultCategoryId)) {
                                            answer = entity.getTotalResult(indicator.indicatorId,
                                                    calculationRule.calculationRuleId)
                                        }
                                        String answerAsStr

                                        if (answer != null) {
                                            answerAsStr = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, indicator?.displayDenominator, answer, session, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, "")
                                        }
                                        PdfUtil.setTextField(pdfDocument, fieldName, (answerAsStr ? answerAsStr : "MND"))
                                    } else {
                                        ValueReference valueReference = getFieldNameAsValueReference(fieldName)
                                        String answer

                                        if (valueReference) {
                                            answer = valueReferenceService.getValueForEntity(valueReference, entity, Boolean.FALSE, Boolean.TRUE)

                                            if (valueReference.answerId) {
                                                PdfUtil.setCheckbox(pdfDocument, fieldName, valueReference.answerId.equals(answer) ? true : false)
                                            } else {
                                                PdfUtil.setTextField(pdfDocument, fieldName, answer)
                                            }
                                        } else {
                                            loggerUtil.warn(log, "FAIL! Invalid fieldName ${fieldName}")
                                        }
                                    }
                                }
                                List<Question> imageQuestions = questions.findAll({
                                    "file".equals(it.inputType) && it.exportImage
                                })

                                if (imageQuestions && !imageQuestions.isEmpty()) {
                                    List<EntityFile> images = entity.getFiles()?.findAll({
                                        imageQuestions.collect({ it.questionId }).contains(it.questionId)
                                    })

                                    if (images && !images.isEmpty()) {
                                        imageQuestions.each { Question imageQuestion ->
                                            imageQuestion.exportImage.each { Map imageSettings ->
                                                PdfUtil.addImage(pdfDocument, imageSettings, images.find({
                                                    imageQuestion.questionId.equals(it.questionId)
                                                }))
                                            }
                                        }
                                    }
                                }
                            }
                            PdfUtil.flattenPDF(pdfDocument)
                            FileOutputStream fileOutputStream = new FileOutputStream(file)
                            pdfDocument.save(fileOutputStream)
                            pdfDocument.close()

                            if (!sourceDocs.isEmpty()) {
                                sourceDocs.each { PDDocument sourceDoc ->
                                    if (sourceDoc != null) {
                                        sourceDoc.close()
                                    }
                                }
                            }
                            render(file: file, contentType: "application/pdf")
                            file.delete()
                        }
                    } else {
                        render text: "No pdf template found!", contentType: "text/plain"
                    }
                } else {
                    render text: "No file path found or no answers!"
                }
            }
        }
    }

    def createWordDoc() {
        String childEntityId = params.childEntityId
        String indicatorId = params.indicatorId
        String templateId = params.templateId
        String reportGenerationRuleId = params.reportGenerationRuleId
        File wordFile
        Entity childEntity

        if (childEntityId && indicatorId && templateId) {
            childEntity = entityService.readEntity(childEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            wordFile = wordDocumentService.createDocument(childEntity, indicator, templateId,reportGenerationRuleId)
        }

        if (wordFile) {
            InputStream contentStream

            try {
                webRequest.renderView = false
                String mimeType = wordFile.name.endsWith("docx") ?
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document" : "application/msword"
                response.setHeader("Content-disposition", "attachment; filename=\"" + wordFile.name + "\"")
                response.setHeader("Content-Length", "${wordFile.size()}")
                response.setContentType(mimeType)
                contentStream = wordFile.newInputStream()
                response.outputStream << contentStream
                webRequest.renderView = false
                wordFile.delete()
            } catch (Exception e) {
                log.error("CreateWordDoc Error: Project: ${childEntity?.parentName}, Child: ${childEntity?.name}: ${e}")
            } finally {
                response.outputStream.close()
            }
        } else {
            render text: "Word document could not be created", contentType: "text/plain"
        }
    }

    def saveCanvasToUserSession() {
        String canvasId = params.canvasId
        String imageAsBase64 = params.data
        if (canvasId && imageAsBase64) {
            if (imageAsBase64.contains(",")) {
                imageAsBase64 = imageAsBase64.split(",")[1]
            }
            User user = userService.getCurrentUser()
            byte[] imageBytes = Base64.getMimeDecoder().decode(imageAsBase64)
            File image = new File("${user.id.toString()}${canvasId}.png")
            FileOutputStream fos = new FileOutputStream(image)
            fos.write(imageBytes)
            fos.close()

            Map<String, File> wordDocGraphsForSession = session.getAttribute(Constants.SessionAttribute.WORD_DOC_GRAPHS.attribute)

            if (wordDocGraphsForSession) {
                wordDocGraphsForSession << [(canvasId): image]
            } else {
                wordDocGraphsForSession = [(canvasId): image]
            }

            session.setAttribute(Constants.SessionAttribute.WORD_DOC_GRAPHS.attribute, wordDocGraphsForSession)
        }
        render text: "ok"
    }
    def saveCanvasBase64ToUserSession() {
        String canvasId = params.canvasId
        String imageAsBase64 = params.data

        if (canvasId && imageAsBase64) {
            User user = userService.getCurrentUser()
            if (user.pdfGraphsForSession) {
                user.pdfGraphsForSession.put(canvasId, imageAsBase64)
            } else {
                user.pdfGraphsForSession = [(canvasId): imageAsBase64]
            }
        }
        render text: ""
    }

    private ValueReference getFieldNameAsValueReference(String fieldName) {
        ValueReference valueReference

        if (fieldName && !fieldName.startsWith("@") && fieldName.contains(".")) {
            String copyOfFieldName = new String(fieldName)

            if (copyOfFieldName.contains("@alias")) {
                copyOfFieldName = copyOfFieldName.replaceAll("@alias", "")
            }
            List<String> parts = copyOfFieldName.tokenize('.')

            if (parts && parts.size() > 2) {
                String queryId = parts.get(0)
                String sectionId = parts.get(1)
                String questionId = parts.get(2)
                String additionalQuestionId

                if (questionId && questionId.contains("_")) {
                    additionalQuestionId = questionId.tokenize('_').get(1)
                    questionId = questionId.tokenize('_').get(0)
                }
                String answerId

                if (parts.size() == 4) {
                    answerId = parts.get(3)
                }

                if (queryId && sectionId && questionId) {
                    valueReference = new ValueReference()
                    valueReference.queryId = queryId
                    valueReference.sectionId = sectionId
                    valueReference.questionId = questionId
                    valueReference.answerId = answerId
                    valueReference.additionalQuestionId = additionalQuestionId
                }
            }
        }
        return valueReference
    }

    def autocaseSendFile() {
        User user = userService.getCurrentUser()
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId

        if (indicatorId && childEntityId && user) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Entity childEntity = entityService.readEntity(childEntityId)

            if (indicator && childEntity) {
                Entity parentEntity = childEntity.parentById

                if (parentEntity) {
                    Workbook wb = fileExportService.getDetailedReportWorkbook(childEntity, indicator, false, true)

                    if (wb) {
                        String parseError
                        File exportFile = File.createTempFile("autocaseExport", ".xlsx")
                        try {
                            FileOutputStream out = new FileOutputStream(exportFile)
                            wb.write(out)
                            out.close()
                        } catch (Exception e) {
                            parseError = "${e}"
                            log.error("Error while creating autocase API file: ${e}")
                        }

                        if (!parseError) {
                            Dataset countryDataset = parentEntity.countryResourceDataset
                            String country = countryDataset?.resourceId
                            String stateResourceId = countryDataset?.additionalQuestionAnswers?.get("state")
                            String state

                            if (stateResourceId) {
                                Resource stateResource = optimiResourceService.getResourceWithParams(stateResourceId, "default")

                                if (stateResource && stateResource.stateCode) {
                                    state = stateResource.stateCode
                                }
                            }
                            String address = parentEntity.address
                            String formattedAddress
                            if (address) {
                                formattedAddress = g.abbr(value: parentEntity.address, maxLength: 35)
                            }

                            Double surface = parentEntity.surface
                            Integer sqftSurface

                            if (surface) {
                                //TODO: Use unitconversionutil
                                sqftSurface = (surface * 10.7639104).round(0).intValue()
                            }

                            RestBuilder rest = new RestBuilder()
                            RestResponse response = rest.post("https://api.autocase.com/ocl") {
                                header "api-token", "PZt&n5uEbZmLbWW7KDE!"
                                header "Content-Type", "multipart/form-data"
                                setProperty "email", "${user.username}".toString()
                                if (parentEntity.autocaseDesignId) {
                                    setProperty "designId", "${parentEntity.autocaseDesignId}".toString()
                                }
                                if (parentEntity.autocaseProjectId) {
                                    setProperty "pId", "${parentEntity.autocaseProjectId}".toString()
                                }
                                setProperty "oclcaProjectId", "${parentEntity.id.toString()}".toString()
                                setProperty "oclcaDesignId", "${childEntity.id.toString()}".toString()
                                setProperty "oclcaDesignName", "${childEntity.name.toString()}".toString()
                                setProperty "projectName", "${parentEntity.name}".toString()
                                if (formattedAddress) {
                                    setProperty "city", "${formattedAddress}".toString()
                                }
                                setProperty "buildingType", "${parentEntity.typeResourceId}".toString()
                                if ("canada".equals(country)) {
                                    setProperty "country", "CA"
                                } else {
                                    setProperty "country", "US"
                                }
                                if (state) {
                                    setProperty "state", "${state}".toString()
                                }
                                if (sqftSurface) {
                                    setProperty "buildingArea", sqftSurface
                                }
                                file = exportFile
                            }
                            exportFile.delete()

                            if (response.status == 200) {
                                log.info("AUTOCASE EXPORT OK: Response status: ${response?.status}, Response BODY: ${response?.responseEntity?.body?.toString()}")
                                redirect(url: "https://autocase.com/ocl/?status=success")
                            } else {
                                log.error("AUTOCASE EXPORT ERROR for USER ${user?.username}, Project: ${parentEntity.name}, Design: ${childEntity.operatingPeriodAndName}: Response status: ${response?.status}, Response BODY: ${response?.responseEntity?.body?.toString()}")
                                render(text: "There was an issue with exporting data to Autocase. Try again later or contact support at ${emailConfiguration.supportEmail}")
                            }
                        } else {
                            log.error("AUTOCASE EXPORT ERROR for USER ${user?.username}, Project: ${parentEntity.name}, Design: ${childEntity.operatingPeriodAndName}: Unable to parse .xlsx from workbook: ${parseError}")
                            render(text: "AUTOCASE API: Unable to parse .xlsx from workbook: ${parseError}")
                        }
                    } else {
                        log.error("AUTOCASE EXPORT ERROR for USER ${user?.username}, Project: ${parentEntity.name}, Design: ${childEntity.operatingPeriodAndName}: Unable to generate workbook")
                        render(text: "AUTOCASE API: Unable to generate workbook.")
                    }
                } else {
                    render(text: "AUTOCASE API: You're not allowed to export from this project.")
                }
            }
        }
    }


    def downloadExpandFeatureAsExcel() {
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        boolean downloadSummary = params.downloadSummary ? params.boolean("downloadSummary") : false
        Boolean scientificNotation = params.scientificNotation ? params.boolean("scientificNotation") : false
        Workbook wb

        if (indicatorId && entityId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Entity entity = entityService.readEntity(entityId)

            if (indicator && entity) {
                wb = fileExportService.getDetailedReportWorkbook(entity, indicator, scientificNotation, false, downloadSummary)
            }
        }

        if (wb) {
            try {
                writingOutExcelFile(wb)
            } catch (ClientAbortException e) {
                // client disconnected before bytes were able to be written.
            }
        } else {
            render(text: "Could not download Excel file. Results are missing or invalid ordering of table.")
        }
    }
    def dumpUserTrialToExcel() {
        List<User> userList = User.collection.find(["trialStatus.accountCreated": [$ne:null]], [username: 1, trialStatus: 1, type:1, trialCount:1])?.collect({it as User})
        Workbook wb
        if(userList){
            wb = fileExportService.generateTrialUserWorkBook(
                    userList.findAll { !emailConfiguration.isProtectedEmail(it.username) }
            )
        }
        if(wb){
            try {
                writingOutExcelFile(wb, "TrialUserStatus_${new Date().format('dd_MM_yyyy')}.xlsx", true)
            } catch (ClientAbortException e) {
                // client disconnected before bytes were able to be written.
            }
        } else {
            render(text: "Could not download Excel file. Results are missing or invalid ordering of table.")
        }
    }
    private writingOutExcelFile(Workbook workbook, String header = null, boolean isXlsx = false){
        response.contentType = 'application/vnd.ms-excel'
        if(isXlsx){
            response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        }
        if(header){
            response.setHeader("Content-disposition", "attachment; filename=${header}")
        }else {
            response.setHeader("Content-disposition", "attachment; filename=detailReport_${new Date().format('dd.MM.yyyy_HH:mm:ss')}.xls")
        }
        OutputStream outputStream = response.getOutputStream()
        workbook.write(outputStream)
        outputStream.flush()
        outputStream.close()
    }

    def dumpLicenseUsers() {
        List<String> userIds = session?.getAttribute("dumpLicenseUserIds")

        if (userIds) {
            session?.removeAttribute("dumpLicenseUserIds")
            Workbook wb = fileExportService.getLicenseUsersDumpExcel(userIds)

            if (wb) {
                response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                response.setHeader("Content-disposition", "attachment; filename=usersDump_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            } else {
                render(text: "Something went wrong! :(")
            }
        } else {
            render(text: "Nothing to download! :(")
        }
    }
}
