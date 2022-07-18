package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.DefaultValue
import com.bionova.optimi.core.domain.mongo.DefaultValueSet
import com.bionova.optimi.core.domain.mongo.DefaultValueSetValue

/**
 * Created by pmm on 25.4.2016.
 */
class DefaultManagerController {

    def applicationService
    def entityService
    def defaultValueService
    def optimiResourceService
    def resourceTypeService
    def questionService

    def index() {
        List<Application> applicationsWithDefaults = applicationService.getApplicationsWithDefaults()
        [applications: applicationsWithDefaults]
    }

    def form() {
        String applicationId = params.applicationId
        String applicationDefaultId = params.applicationDefaultId
        String defaultValueSetId = params.defaultValueSetId
        Application application
        DefaultValue applicationDefault
        DefaultValueSet defaultValueSet

        if (applicationId && applicationDefaultId && defaultValueSetId) {
            application = applicationService.getApplicationByApplicationId(applicationId)

            if (application) {
                applicationDefault = application.defaultValues?.find({
                    applicationDefaultId.equals(it.defaultValueId)
                })
                defaultValueSet = applicationDefault?.defaultValueSets?.find({
                    defaultValueSetId.equals(it.defaultValueSetId)
                })

                if (defaultValueSet) {
                    defaultValueSet.applicationId = applicationId
                    defaultValueSet.defaultValueId = applicationDefaultId
                }
            }
        }
        [application: application, applicationDefault: applicationDefault, defaultValueSet: defaultValueSet]
    }

    def addDefaultValueSetValue() {
        String applicationId = params.applicationId
        String applicationDefaultId = params.applicationDefaultId
        String defaultValueSetId = params.defaultValueSetId
        List<String> resourceTypeValueParams = params?.keySet()?.toList()?.findAll({
            it.toString()?.startsWith("resourceTypeValue.")
        })
        DefaultValueSetValue defaultValueSetValue

        if (applicationId && applicationDefaultId && defaultValueSetId && resourceTypeValueParams) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)
            DefaultValueSet defaultValueSet = application.defaultValues?.find({
                applicationDefaultId.equals(it.defaultValueId)
            })?.
                    defaultValueSets?.find({ defaultValueSetId.equals(it.defaultValueSetId) })

            if (defaultValueSet) {
                resourceTypeValueParams.each { String resourceTypeValueParam ->
                    String value = params[resourceTypeValueParam]
                    String resourceType = resourceTypeValueParam.tokenize('.')[1]

                    if (resourceType && value && value.replace(',', '.').trim().isNumber()) {
                        defaultValueSetValue = defaultValueService.saveDefaultValueSetValue(applicationId, applicationDefaultId,
                                defaultValueSetId, resourceType, value.replace(',', '.').trim().toDouble())
                    }
                }
            }

            if (defaultValueSetValue?.id) {
                flash.fadeSuccessAlert = message(code: "admin.defaultManager.global_defaults.updated")
            } else {
                flash.fadeSuccessAlert = message(code: "admin.defaultManager.global_defaults.not_updated")
            }
        }
        redirect action: "form", params: [applicationId    : applicationId, applicationDefaultId: applicationDefaultId,
                                          defaultValueSetId: defaultValueSetId]
    }

    def removeValue() {
        String applicationId = params.applicationId
        String applicationDefaultId = params.applicationDefaultId
        String defaultValueSetId = params.defaultValueSetId
        String resourceType = params.resourceType
        boolean removeOk = false

        if (applicationId && applicationDefaultId && defaultValueSetId && resourceType) {
            removeOk = defaultValueService.removeDefaultValueSetValue(applicationId, applicationDefaultId,
                    defaultValueSetId, resourceType)

            if (removeOk) {
                flash.fadeSuccessAlert = "Value removed successfully."
            } else {
                flash.fadeErrorAlert = "Error in removing the value."
            }
        }
        redirect action: "form", params: [applicationId: applicationId, applicationDefaultId: applicationDefaultId]
    }

}
