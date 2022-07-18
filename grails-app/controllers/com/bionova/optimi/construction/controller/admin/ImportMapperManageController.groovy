package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.SystemTrainingDataSet
import grails.converters.JSON

class ImportMapperManageController extends ExceptionHandlerController {

    def applicationService
    def importMapperService

    def form() {
        def applicationIds = applicationService.getAllApplicationsThatHaveImportMappers()?.collect({ it.applicationId })
        String appIdFromAdd = params.appId
        [applicationIds: applicationIds, appId: appIdFromAdd]
    }

    def importMapperFeatures() {
        String applicationId = params.applicationId
        List<String> importMapperWarns = []
        List<String> importMapperDiscards = []
        List<SystemTrainingDataSet> systemTrainingDataSets = []

        if (applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                if (application.importMapperWarns) {
                    importMapperWarns.addAll(application.importMapperWarns)
                }
                if (application.importMapperDiscards) {
                    importMapperDiscards.addAll(application.importMapperDiscards)
                }
                if (application.systemTrainingDatas) {
                    systemTrainingDataSets.addAll(application.systemTrainingDatas)
                }
            }
        }
        String templateAsString = g.render(template: "/importMapperManage/importMapperFeatures", model: [applicationId: applicationId, importMapperWarns: importMapperWarns.sort(),
                                                                                                   importMapperDiscards: importMapperDiscards.sort(), systemTrainingDataSets: systemTrainingDataSets]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addObjects() {
        if (params.id) {
            if (params.inputWarning && params.addWarning) {
                Boolean addOk = applicationService.addWarning(params.id, params.inputWarning, params.matchAllWarningWariants)

                if (addOk) {
                    flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                } else {
                    flash.errorAlert = "Oops :("
                }
            } else if (params.inputDiscard && params.addDiscard) {
                Boolean addOk = applicationService.addDiscard(params.id, params.inputDiscard, params.matchAllDiscardWariants)

                if (addOk) {
                    flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
                } else {
                    flash.errorAlert = "Oops :("
                }
            }
            redirect action: "form", params: [appId: params.id]
        } else {
            flash.fadeErrorAlert = "Application not detected"
            chain action: "form"
        }
    }

    def removeImportMapperWarning() {
        if (params.id && params.importMapperWarning) {
            Boolean removeOk = applicationService.removeWarning(params.id, params.importMapperWarning)

            if (removeOk) {
                flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
            } else {
                flash.errorAlert = "Oops :("
            }
            redirect action: "form", params: [appId: params.id]
        } else {
            flash.fadeErrorAlert = "Application not detected"
            chain action: "form"
        }
    }

    def removeImportMapperDiscard() {
        if (params.id && params.importMapperDiscard) {
            Boolean removeOk = applicationService.removeDiscard(params.id, params.importMapperDiscard)

            if (removeOk) {
                flash.fadeSuccessAlert = "<i class=\"fa fa-magic\" aria-hidden=\"true\"></i> Success!"
            } else {
                flash.errorAlert = "Oops :("
            }
            redirect action: "form", params: [appId: params.id]
        } else {
            flash.fadeErrorAlert = "Application not detected"
            chain action: "form"
        }
    }
}