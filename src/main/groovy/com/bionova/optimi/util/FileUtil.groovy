package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.service.FecService
import com.bionova.optimi.core.service.Re2020Service
import com.bionova.optimi.core.service.XmlService
import com.bionova.optimi.core.util.ImageUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.grails.OptimiMessageSource
import com.bionova.optimi.xml.fecRSEnv.RSET
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class FileUtil {
    Log log = LogFactory.getLog(FileUtil.class)
    LoggerUtil loggerUtil
    def queryService
    ErrorMessageUtil errorMessageUtil
    OptimiMessageSource messageSource
    Re2020Service re2020Service
    FecService fecService


    public MultipartFile getFileFromRequest(HttpServletRequest request) {
        MultipartFile file

        if ("post".equalsIgnoreCase(request.method) && request instanceof MultipartHttpServletRequest) {
            try {
                Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap()

                if (fileMap) {
                    file = fileMap.values()?.toList()?.get(0)
                }
            } catch (Exception e) {
                loggerUtil.warn(log, "Error in getting file from request: ${e}")
                file = null
            }
        } else {
            loggerUtil.warn(log, "File parsing failed, because invalid request method: ${request.method} or invalid request class: ${request.class}")
        }
        return file
    }

    public Map<String, MultipartFile> getMultiPartFilesFromRequest(HttpServletRequest request) {
        Map<String, MultipartFile> files

        if ("post".equalsIgnoreCase(request.method) && request instanceof MultipartHttpServletRequest) {
            try {
                files = ((MultipartHttpServletRequest) request).getFileMap()
            } catch (Exception e) {
                loggerUtil.warn(log, "Error in getting files from request: ${e}")
                files = null
            }
        } else {
            loggerUtil.warn(log, "Parsing files failed, because invalid request method: ${request.method} or invalid request class: ${request.class}")
        }
        return files
    }

    public void handleFiles(Entity entity, HttpServletRequest request, List<Dataset> datasets) {
        String queryId = datasets?.find({ d -> d.queryId != null })?.queryId
        Map<Entity, List<String>> entityAndQuestionIds = addFilesToEntity(request, entity, queryId)

        if (entityAndQuestionIds) {
            entity = entityAndQuestionIds.keySet().first()
            List<String> questionIds = entityAndQuestionIds.get(entity)

            if (questionIds && !questionIds.isEmpty()) {
                List<Dataset> removableDatasets = datasets.findAll({ Dataset ds -> queryId.equals(ds.queryId) && questionIds.contains(ds.questionId) })

                if (removableDatasets) {
                    datasets.removeAll(removableDatasets)
                }
            }
        }
    }

    public static String getJSONArrayStringFromJSONFile(File file) {
        String fileContent = file.text.trim()
        fileContent = (fileContent.startsWith('[') ? fileContent : ("[" + fileContent))
        fileContent = (fileContent.endsWith(']') ? fileContent : (fileContent + "]"))
        return fileContent
    }

    private Map<Entity, List<String>> addFilesToEntity(HttpServletRequest request, Entity entity, queryId) {
        List<String> questionIds = []
        Map fileMap = getMultiPartFilesFromRequest(request)
        def imageFound = false

        if (fileMap) {
            fileMap.keySet().toList().each { String param ->
                if (param) {
                    String questionId
                    String sectionId

                    if (param.contains(".")) {
                        sectionId = param.tokenize(".")[0]
                        questionId = param.tokenize(".")[1]
                    }

                    if (!questionId || !sectionId) {
                        throw new RuntimeException("SectionId or questionId missing from dataset. Cannot add the file!")
                    }
                    questionIds.add(questionId)
                    MultipartFile file = fileMap.get(param)

                    if (file && !file.empty) {
                        Question question = queryService.getQueryByQueryId(queryId, Boolean.TRUE)?.getAllQuestions()?.find({ Question q -> q.questionId.equals(questionId) })

                        if (!question) {
                            throw new RuntimeException("Cannot add file, because question not found. Perhaps unsupported persistent question!")
                        }
                        boolean fileOk = isFileOk(queryId, sectionId, question, file, request, entity)

                        if (fileOk) {
                            String contentType = file.getContentType()
                            String fileName = file.getOriginalFilename()
                            String fileType
                            Integer typeIndex = contentType?.indexOf("/")

                            if (typeIndex) {
                                fileType = contentType.substring(typeIndex + 1)
                            }
                            String entityId = entity.id.toString()
                            EntityFile entityFile = new EntityFile(entityId: entityId, data: file.getBytes(), contentType: contentType, name: fileName, type: fileType, queryId: queryId, sectionId: sectionId, questionId: questionId)

                            if (entityFile.isImage) {
                                entityFile.image = true
                            }

                            // Special handlign for entity image
                            if (entityFile.isImage && "entityImage".equals(questionId)) {
                                imageFound = true
                                //getting file from html form
                                File tempFile = File.createTempFile(entity?.id?.toString(), "." + fileType)
                                file.transferTo(tempFile)
                                File thumbTempFile = File.createTempFile("thumb_" + entity?.id?.toString(), "." + fileType)
                                //transfer file from form to the savePath
                                ImageUtil.cropImage(tempFile, thumbTempFile, 40, 40)
                                byte[] data = thumbTempFile.getBytes()
                                //resize our file to the 120x120 and save resized images to the thumbPlace
                                // BufferedImage thumbnail = Scalr.resize(image, 150);
                                EntityFile thumbImage = new EntityFile(entityId: entityId, data: data, queryId: queryId, sectionId: sectionId, questionId: questionId, contentType: contentType, name: "thumb_" + fileName, type: fileType, image: true, thumbnail: true)
                                // Remove temp files
                                tempFile.delete()
                                thumbTempFile.delete()
                                List<EntityFile> images = EntityFile.findAllByEntityIdAndQueryIdAndSectionIdAndQuestionIdAndImage(entityId, queryId, sectionId, questionId, true)

                                if (!images || images.isEmpty()) {
                                    images = EntityFile.findAllByEntityIdAndImage(entityId, true)
                                }
                                List<EntityFile> existingThumbnails = images?.findAll { it.thumbnail }
                                List<EntityFile> existingImages = images?.findAll { !it.thumbnail }

                                existingThumbnails?.each { EntityFile ef ->
                                    ef.delete(flush: true)
                                }

                                existingImages?.each { EntityFile ef ->
                                    ef.delete(flush: true)
                                }
                                thumbImage.save(flush: true)
                            }
                            entityFile.save(flush: true)
                            entity.hasImage = imageFound

                            // Clear user given RSET mappings for zone etc. if new RSET file is uploaded
                            if (FrenchConstants.RSET_QUESTIONS.contains(questionId)) {
                                entity.frenchRsetMappings?.removeIf { it.questionId == questionId }
                            }
                            entity = entity.merge(flush: true)
                        }
                    }
                }
            }
        }

        if (!questionIds.isEmpty()) {
            return [(entity): questionIds]
        } else {
            return null
        }
    }

    private boolean isFileOk(String queryId, String sectionId, Question question, MultipartFile file, MultipartHttpServletRequest request, Entity entity) {
        Boolean fileOk = true
        String extension = file.getContentType()
        Integer extensionIndex = extension?.indexOf("/")

        if (extensionIndex) {
            extension = extension.substring(extensionIndex + 1)
        }
        long fileSize = file.getSize()

        if (question?.allowedExtensions && !question.allowedExtensions.contains(extension)) {
            fileOk = false
            errorMessageUtil.setErrorMessage(messageSource.getMessage(question?.invalidExtensionMessage ?: 'file.invalid_extension', null, LocaleContextHolder.getLocale()))
        }

        if (question?.maximumSize && question.maximumSize < fileSize) {
            fileOk = false
            errorMessageUtil.setErrorMessage(messageSource.getMessage("entity.too_big_file", null, LocaleContextHolder.getLocale()))
        }
        List<EntityFile> existingFiles = entity?.getFiles()?.findAll({
            queryId?.equals(it.queryId) && sectionId?.equals(it.sectionId) && question?.questionId.equals(it.questionId)
        })
        Integer fileMaxAmount = question?.fileMaxAmount

        if (fileMaxAmount != null && existingFiles && existingFiles.size() >= fileMaxAmount) {
            fileOk = false
            errorMessageUtil.setErrorMessage(messageSource.getMessage("entity.file.max_amount_exceeded", [existingFiles.size()].toArray(), LocaleContextHolder.getLocale()))
        }

        if (FrenchConstants.FEC_QUERYID_PROJECT_LEVEL.equals(queryId) && FrenchConstants.RSET_QUESTIONS.contains(question?.questionId)) {

            if (FrenchConstants.FEC_RSET_QUESTIONID == question.questionId) {
                fileOk = fecService.isFecRsetFileOk(file)
            } else if (FrenchConstants.RE2020_RSET_QUESTIONID == question.questionId) {
                fileOk = re2020Service.isRe2020RsetFileOk(file)
            } else {
                fileOk = false
                errorMessageUtil.setErrorMessage(messageSource.getMessage("xml.invalidFile_error", null, LocaleContextHolder.getLocale()), true, true)
            }
        }
        return fileOk
    }
}
