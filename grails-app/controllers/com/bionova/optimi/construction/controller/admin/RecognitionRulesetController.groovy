package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import org.springframework.web.multipart.MultipartHttpServletRequest

class RecognitionRulesetController {

    def recognitionRulesetService
    def optimiResourceService
    def userService
    def applicationService
    def flashService
    def importMapperService
    def accountService
    def queryService

    def list() {
        List<Account> allAccounts = accountService.getAccounts()
        [recognitionRulesets: recognitionRulesetService.getAllRecognitionRulesets()?.sort({ it.type.toLowerCase() }),allAccounts: allAccounts]
    }

    def form() {
        String recognitionRulesetId = params.id
        RecognitionRuleset recognitionRuleset

        if (recognitionRulesetId) {
            recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)
        }
        List<String> rulesetTypes = ["${Constants.RulesetType.DISCARD.toString()}","${Constants.RulesetType.WARN.toString()}","${Constants.RulesetType.MAPPING.toString()}"]
        List<String> importMapperIds = applicationService.getAllApplicationImportMappers()?.collect({ it.importMapperId })
        List<Resource> countries = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)
        List<String> applicationIds = applicationService.getAllApplicationApplicationIds()
        User user = userService.getCurrentUser()
        List<Account> allAccounts = accountService.getAccounts()
        [user: user, rulesetTypes: rulesetTypes, importMapperIds: importMapperIds, countries: countries, recognitionRulesetId: recognitionRulesetId,
         recognitionRuleset: recognitionRuleset, allAccounts: allAccounts, applicationIds: applicationIds]
    }

    def save() {
        RecognitionRuleset recognitionRuleset
        String id = params.id
        if (id) {
            recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(id)

            if (!recognitionRuleset?.type?.equals(params.type) && "mapping".equals(params.type)) {
                log.info("different type, was: ${recognitionRuleset.type}, now: ${params.type}, resetting data")
                flashService.setFadeInfoAlert("Different type, was: ${recognitionRuleset.type}, now: ${params.type}, resetting data", true)
                recognitionRuleset?.textMatches = null
                recognitionRuleset?.importMapperTrainingDatas = null
                recognitionRuleset?.systemTrainingDatas = null
            }
            recognitionRuleset?.properties = params
        } else {
            recognitionRuleset = new RecognitionRuleset(params)
        }

        if (recognitionRuleset && recognitionRuleset.validate()) {
            recognitionRulesetService.saveRecognitionRuleset(recognitionRuleset)
            flash.fadeSuccessAlert = "Ruleset saved successfully"
            redirect action: "list"
        } else {
            flash.fadeErrorAlert = "Error in saving ruleset: ${renderErrors(bean: recognitionRuleset)}"
            chain action: "form", params: [recognitionRulesetId: id]
        }
    }

    def saveTextmatch() {
        String recognitionRulesetId = params.id
        if (recognitionRulesetId) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)

            if (recognitionRuleset) {
                if (params.textMatch) {
                    String newValue = params.textMatch
                    String comment = params.comment
                    Boolean matchAllVariants = params.matchAllWariants
                    Boolean editingExisting = params.existing

                    if (!recognitionRuleset.textMatches?.find({it?.textMatch?.equalsIgnoreCase(newValue)}) || editingExisting) {
                        TextMatch newTextMatch = new TextMatch()
                        newTextMatch.textMatch = newValue
                        newTextMatch.comment = comment

                        if (matchAllVariants) {
                            newTextMatch.matchAllVariants = Boolean.TRUE
                        }

                        if (editingExisting) {
                            String oldValue = params.oldValue
                            recognitionRuleset.textMatches.remove(recognitionRuleset.textMatches.find({
                                it.textMatch.equalsIgnoreCase(oldValue)
                            }))
                        }

                        if (recognitionRuleset.textMatches) {
                            recognitionRuleset.textMatches.add(newTextMatch)
                        } else {
                            recognitionRuleset.textMatches = [newTextMatch]
                        }

                        recognitionRuleset = recognitionRuleset.merge(flush: true)

                        if (recognitionRuleset) {
                            flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                        } else {
                            flash.fadeErrorAlert = "oops :/"
                        }
                    } else {
                        flash.fadeErrorAlert = "Field with name: ${newValue} already exists!"
                    }
                }
            }
            redirect action: "map", params: [id: recognitionRulesetId]
        }
    }

    def removeTextmatch() {
        String recognitionRulesetId = params.id
        if (recognitionRulesetId) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)

            if (recognitionRuleset) {
                String valueForDeletion = params.textMatch

                if (valueForDeletion) {

                    Boolean deleted = recognitionRuleset.textMatches.remove(recognitionRuleset.textMatches.find({
                        it.textMatch.equalsIgnoreCase(valueForDeletion)
                    }))

                    if (deleted) {
                        recognitionRuleset = recognitionRuleset.merge(flush: true)
                        flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                    } else {
                        flash.fadeErrorAlert = "Could not delete value: ${valueForDeletion}"
                    }
                }
            }
        }
        redirect action: "map", params: [id: recognitionRulesetId]
    }

    def map() {
        String rulesetId = params.id

        if (rulesetId) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(rulesetId)

            if (recognitionRuleset) {
                Query query = queryService.getQueryByQueryId("buildingMaterialsQuery", Boolean.TRUE)
                List<QueryFilter> supportedFilters = query?.supportedFilters
                QueryFilter resourceTypeFilter = supportedFilters?.find({it.resourceAttribute.equals("resourceType")})
                User user = userService.getCurrentUser(Boolean.TRUE)
            
                [recognitionRuleset: recognitionRuleset, supportedFilters: supportedFilters, resourceTypeFilter: resourceTypeFilter,
                 queryId           : query?.queryId, user: user]
            } else {
                flash.fadeErrorAlert = "Error"
                chain action: "list"
            }
        } else {
            flash.fadeErrorAlert = "Error"
            chain action: "list"
        }
    }

    def createUserRuleset() {
        if (recognitionRulesetService.getAllRecognitionRulesets(Constants.RulesetType.USER.toString())) {
            RecognitionRuleset userRuleset = recognitionRulesetService.getAllRecognitionRulesets(Constants.RulesetType.USER.toString()).get(0)

            if (userRuleset) {
                userRuleset.delete(flush: true)
                flash.fadeSuccessAlert = "Successfully redid user ruleset"
            } else {
                flash.fadeErrorAlert = "Could not recreate user ruleset"
            }
        }

        RecognitionRuleset userRecognitionRuleset = new RecognitionRuleset()
        userRecognitionRuleset.type = Constants.RulesetType.USER.toString()
        userRecognitionRuleset.name = "Adaptive Recognition"
        userRecognitionRuleset.save(flush: true)
        redirect action: "list"
    }

    def uploadTrainingData() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(params.id)

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "map", params: [id: params.id]
            } else if (recognitionRuleset && excelFile && !params.boolean("overWrite") && recognitionRuleset?.systemTrainingDatas?.find({ it.fileName?.equals(excelFile.originalFilename) })) {
                flash.errorAlert = "Excel with the same name: ${excelFile.originalFilename} is already imported. Check overwrite if you wish to overwrite all mappings from this file."
                redirect action: "map", params: [id: params.id]
            } else if (recognitionRuleset) {
                try {

                    String fileName = excelFile.originalFilename
                    List<String> trainingDatas = importMapperService.importExcelWorkbook(excelFile)
                    SystemTrainingDataSet newSysTrainData = new SystemTrainingDataSet()
                    newSysTrainData.fileName = fileName
                    newSysTrainData.systemMatches = []

                    if (recognitionRuleset && trainingDatas) {
                        trainingDatas.each { String trainingData ->
                            SystemMatch systemMatch = new SystemMatch()
                            systemMatch.systemMatchId = UUID.randomUUID().toString()
                            systemMatch.systemTrainingData = trainingData
                            newSysTrainData.systemMatches.add(systemMatch)
                        }

                        SystemTrainingDataSet toBeRemoved
                        recognitionRuleset.systemTrainingDatas.each { SystemTrainingDataSet systemTrainingDataSet ->
                            if (systemTrainingDataSet.fileName?.equals(fileName)) {
                                toBeRemoved = systemTrainingDataSet
                            }
                        }
                        if (toBeRemoved) {
                            recognitionRuleset.systemTrainingDatas.remove(toBeRemoved)
                        }


                        if (recognitionRuleset.systemTrainingDatas) {
                            recognitionRuleset.systemTrainingDatas.add(newSysTrainData)
                        } else {
                            recognitionRuleset.systemTrainingDatas = [newSysTrainData]
                        }
                        recognitionRuleset = recognitionRuleset.merge(flush: true)
                        flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                    } else {
                        flash.errorAlert = "Either no recognitionRuleset: ${recognitionRuleset.name}, or no trainingdata: ${trainingDatas} found"
                    }
                    chain action: "map", params: [id: params.id]
                } catch (Exception e) {
                    flash.errorAlert = "Exception: ${e}"
                }
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "list"
        }
    }

    def removeRecognitionRuleset() {
        String rulesetId = params.id

        if (rulesetId) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(rulesetId)

            if (recognitionRuleset) {
                recognitionRuleset.delete(flush: true)
                flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                chain action: "list"
            }
        } else {
            flash.fadeErrorAlert = "Could not get ruleset"
            chain action: "list"
        }
    }

    def removeTrainingDataSet() {
        String rulesetId = params.id
        String fileName = params.fileName

        if (rulesetId && fileName) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(rulesetId)

            if (recognitionRuleset) {
                Boolean emptyMatchesRemoved = Boolean.FALSE
                SystemTrainingDataSet systemTrainingDataSet = recognitionRuleset.systemTrainingDatas?.find({ fileName.equals(it.fileName) })

                if (systemTrainingDataSet) {
                    emptyMatchesRemoved = systemTrainingDataSet.systemMatches?.removeAll({ !it.resourceId })
                }

                if (emptyMatchesRemoved) {
                    if (systemTrainingDataSet.systemMatches && !systemTrainingDataSet.systemMatches.isEmpty()) {
                        recognitionRuleset.merge(flush: true)
                    } else {
                        recognitionRuleset.systemTrainingDatas.remove(systemTrainingDataSet)
                        recognitionRuleset.merge(flush: true)
                    }

                    flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                    chain action: "map", params: [id: rulesetId]
                } else {
                    flash.fadeErrorAlert = "Could not get ruleset"
                    chain action: "map", params: [id: rulesetId]
                }
            }
        } else {
            flash.fadeErrorAlert = "Could not get ruleset"
            chain action: "list"
        }
    }

    def mapResourcesToTrainingData() {
        String recognitionRulesetId = params.id
        Enumeration<String> paramNames = request.getParameterNames()
        RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)

        if (paramNames && recognitionRuleset) {
            paramNames.each { String paramName ->
                if (paramName.contains("resourceId")) {
                    String targetSysData = paramName.tokenize(".")[1]
                    String value = request.getParameter(paramName)
                    String resourceId = value?.tokenize(".")[0]
                    String profileId = value?.tokenize(".")[1]

                    if (targetSysData && resourceId) {
                        recognitionRuleset.systemTrainingDatas.find { SystemTrainingDataSet systemTrainingDataSet ->
                            systemTrainingDataSet.systemMatches?.find { SystemMatch systemMatch ->
                                if (targetSysData.equals(systemMatch.systemMatchId)) {
                                    systemMatch.resourceId = resourceId
                                    systemMatch.profileId = profileId
                                    return true
                                }
                            }
                        }
                    }
                }
            }
            recognitionRuleset = recognitionRuleset.merge(flush: true)
        }
        redirect action: "map", params: [id: recognitionRulesetId]
    }

    def deleteSystemMatch() {
        String recognitionRulesetId = params.recognitionRulesetId
        String fileName = params.trainingDataFileName
        String systemMatchId = params.systemMatchId

        if (recognitionRulesetId && fileName && systemMatchId) {
            RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)

            if (recognitionRuleset) {
                SystemTrainingDataSet systemTrainingDataSet = recognitionRuleset.systemTrainingDatas.find({ it.fileName?.equals(fileName) })

                if (systemTrainingDataSet) {
                    Boolean removed = systemTrainingDataSet.systemMatches?.remove(systemTrainingDataSet.systemMatches?.find({ systemMatchId.equals(it.systemMatchId) }))

                    if (removed) {
                        if (systemTrainingDataSet.systemMatches && !systemTrainingDataSet.systemMatches.isEmpty()) {
                            recognitionRuleset.merge(flush: true, failOnError: true)
                        } else {
                            recognitionRuleset.systemTrainingDatas.remove(systemTrainingDataSet)
                            recognitionRuleset.merge(flush: true, failOnError: true)
                        }
                        flash.fadeSuccessAlert = "Successfully deleted system match!"
                    } else {
                        flash.fadeErrorAlert = "Could not remove system match!"
                    }
                }
            }
        }
        redirect action: "map", params: [id: recognitionRulesetId]
    }

    def remapSystemMatch() {
        String recognitionRulesetId = params.recognitionRulesetId

        Enumeration<String> paramNames = request.getParameterNames()
        RecognitionRuleset recognitionRuleset = recognitionRulesetService.getRecognitionRulesetById(recognitionRulesetId)

        if (recognitionRuleset && paramNames) {
            paramNames.each { String paramName ->
                if (paramName.contains("resourceId")) {
                    String targetSysData = paramName.tokenize(".")[1]
                    String value = request.getParameter(paramName)
                    String resourceId = value?.tokenize(".")[0]
                    String profileId = value?.tokenize(".")[1]

                    if (targetSysData && resourceId) {
                        recognitionRuleset.systemTrainingDatas.find { SystemTrainingDataSet systemTrainingDataSet ->
                            systemTrainingDataSet.systemMatches?.find { SystemMatch systemMatch ->
                                if (targetSysData.equals(systemMatch.systemMatchId)) {
                                    systemMatch.resourceId = resourceId
                                    systemMatch.profileId = profileId
                                    return true
                                }
                            }
                        }
                    }
                }
            }
            recognitionRuleset = recognitionRuleset.merge(flush: true, failOnError: true)
            flash.fadeSuccessAlert = "Successfully remapped!"
        }
        redirect action: "map", params: [id: recognitionRulesetId]
    }
}
