package com.bionova.optimi

import grails.util.Holders

class UrlMappings {

    static mappings = {
        def excludeControllers = ['index', 'error', 'passwordReset', 'queryTask', 'logout', 'actuator', 'health']
        def secAdminControllers = ['dataMigration', 'backups', 'configuration', 'dataSummary', 'domainClass', 'import',
                                   'logs', 'notification', 'channel', 'activeUsers', 'adaptiveRecognitionData', 'importMapperManage',
                                   'recognitionRuleset', 'costStructure', 'oneClickLcaApi', 'localizationFile', 'massRecalculation',
                                   'lcaChecker', 'eolProcess', 'helpConfiguration', 'dbCleanUp']
        for (controller in Holders.grailsApplication.controllerClasses) {
            if (controller.packageName == "com.bionova.optimi.construction.controller" || controller.packageName == "com.bionova.optimi.construction.controller.admin") {
                def cName = controller.logicalPropertyName

                if (excludeControllers.indexOf(cName) < 0) {
                    if (secAdminControllers.indexOf(cName) < 0) {
                        "/sec/${cName}/$action?/$id?"(controller: cName)
                    } else {
                        "/sec/admin/${cName}/$action?/$id?"(controller: cName)
                    }
                }
            }
        }
        "/"(controller: "index")
        "/login"(controller: "index", action: "loginUrl")
        "/logout"(controller: "logout", action: "index")
        "/steptwoauth" (controller: "index", action: "steptwoauth")
        "/authenticatebytoken" (controller: 'index', action: 'authenticateByEmailToken')
        "/register"(controller: "index", action: "form")
        "/message"(controller: "index", action: "message")
        "/reactivate"(controller: "index", action: "resendActivation")
        "/reactivateinside"(controller: "index", action: "resendActivationInSoftware")
        "/nonValidatedAccountHandler"(controller: "index", action: "nonValidatedAccountHandler")
        "/querytask"(controller: "queryTask", action: "index")
        "/querytaskform"(controller: "queryTask", action: "form")
        "/taskform"(controller: "queryTask", action: "taskform")
        "/querytasksave"(controller: "queryTask", action: "save")
        "/tasksave"(controller: "queryTask", action: "savetask")
        "/enable"(controller: "index", action: "activateUser")
        "/addresource"(controller: "util", action: "addResourceRow")
        "/previousperiods"(controller: "util", action: "renderPreviousOperatingPeriods")
        "/designresults"(controller: "util", action: "renderDesignResults")
        "/profiles"(controller: "util", action: "getResourceProfiles")
        "/languagetextfield"(controller: "util", action: "addLanguageTextField")
        "/languagetextarea"(controller: "util", action: "addLanguageTextArea")
        "/resources"(controller: "util", action: "getResources")
        "/jsonresources"(controller: "util", action: "getResourcesAsJson")
        "/removefile"(controller: "util", action: "removeEntityFile")
        "/entityimage"(controller: "util", action: "loadEntityImage")
        "/unitchangewarning"(controller: "util", action: "setUnitChangeWarning")
        "/sendReset"(controller: "passwordReset", action: "sendResetPasswordMail")
        "/resetform"(controller: "passwordReset", action: "resetPassword")
        "/reset"(controller: "passwordReset", action: "savePassword")
        "/addcompositerow"(controller: "util", action: "addCompositeRow")
        "/authfail"(controller: "index", action: "authfail")
        "/filterchoices"(controller: "util", action: "getFilterChoicesByQueryFilter")
        "/importMapperFile"(controller: "importMapper", action: "postFile")
        "/importResults"(controller: "importMapper", action: "importResults")
        "/importMappingFile"(controller: "oneClickLcaApi", action: "importMappingFile")
        "/importSpecialRules"(controller: "oneClickLcaApi", action: "importSpecialRules")
        "/dumpIndicatorBenchmarks"(controller: "oneClickLcaApi", action: "dumpIndicatorBenchmarks")
        "/sec/importQuery/$id?"(controller: 'dataSummary', action: 'importQuery')
        "/rendersourceListing"(controller: "util", action: "renderSourceListing")
        "/rendercollapserdata"(controller: "util", action: "renderCollapserData")
        "/rendergroupedbydata"(controller: "util", action: "renderGroupedByData")
        "/renderdenieddatasets"(controller: "util", action: "renderDeniedDatasets")
        "/rendersystemtrainingdata"(controller: "util", action: "renderSystemTrainingData")
        "/copyentityanswers"(controller: "util", action: "copyEntityAnswersModal")
        "/otherdesignsdropdown"(controller: "util", action: "showOtherDesignsDropDown")
//        "/getrsetvalue"(controller: "util", action: "copyAnswerFromRset") // Hung: comment out since it's not in use anywhere 12.02.2021. Remove after few months
//        "/injectrsetvalue"(controller: "util", action: "injectAnswerFromRset") // Hung: to be deprecated. Can be removed after few months 12.02.2021
        "/readfecepd"(controller: "util", action: "readFecEpd")
        "/getquickfilters"(controller: "util", action: "getQuickFilters")
        "/saveuserfilters"(controller: "util", action: "userFilterChoices")
        "/getnestedfilters"(controller: "util", action: "getNestedFilters")
        "/renderresultcomparepercategory"(controller: "util", action: "renderResultComparePerCategory")
        "/saveuserinput"(controller: "util", action: "saveUserInput")
        "/calculatecost"(controller: "util", action: "calculateCost")
        "/dataloadingfeatureresources"(controller: "util", action: "getResourcesForDataLoadingFeature")
        "/dataloadingfeatureotherquestionvalue"(controller: "util", action: "getDataLoadingFeatureOtherQuestionValue")
        "/changeclass"(controller: "util", action: "changeImportMapperDatasetClass")
        "/saveimportmappermappings"(controller: "importMapper", action: "saveImportMapperMappings")
        "/sustainablealternatives"(controller: "util", action: "sustainableAlternatives")
        "/compiledreportdesigns"(controller: "util", action: "compiledReportDesigns")
        "/compiledreportdownloadlink"(controller: "util", action: "compiledReportDownloadLink")
        "/constructionresources"(controller: "util", action: "getResourcesForConstruction")
        "/multidataajaxwarningforcompare"(controller: "util", action: "multiDataAjaxWarningForCompare")
        "/logjserror"(controller: "util", action: "logJSError")
        "/rendercombineddisplayfields"(controller: "util", action: "renderCombinedDisplayFields")
        "/deleteifcdataset"(controller: "util", action: "deleteIfcDataset")
        "/renderDatasetInformation"(controller: "util", action: "renderDatasetInformation")
        "/splitOrChangeDataset"(controller: "util", action: "splitOrChangeDataset")
        "/splitDataset"(controller: "util", action: "splitDataset")
        "/sec/admin/license/trialproject"(controller: "license", action: "trialProjects")
        "/sessionInformation" (controller: "util", action: "sessionInformation")
        "/maintainActivity" (controller: "util", action: "maintainActivity")
        "/renderRSEnv"(controller: "xmlHandler", action: "renderRSEnv")
        "/health"(controller: "health", action: "index")
        "/getRecipesForDataLoadingFromFile" (controller: "util", action: "getRecipesForDataLoadingFromFile")


        // Under /api/ prefix
        "/api/autocase"(controller: "oneClickLcaApi", action: "autocase")
        "/api/getResourceLibrary"(controller: "oneClickLcaApi", action: "getResourceLibrary")
        "/api/getBenchmarks"(controller: "oneClickLcaApi", action: "dumpIndicatorBenchmarks")
        "/api/runCalc"(controller: "oneClickLcaApi", action: "runTestCalculation")
        "/api/startCalculationOnWeb"(controller: "importMapper", action: "postFile")
        "/api/startCalculationRequest"(controller: "importMapper", action: "postFile")
        "/api/getCalculationResults"(controller: "importMapper", action: "importResults")

        // CarbonDesigner3D Endpoints
        // CarbonDesigner3D - Initial data
        "/cd/getCarbonDesigns"(controller: "carbonDesignerApi", action: "getCarbonDesigns")
        "/cd/getCarbonDesign"(controller: "carbonDesignerApi", action: "getCarbonDesign")
        "/cd/getUserDetails"(controller: "carbonDesignerApi", action: "getUserDetails")
        "/cd/getCarbonDesignTest"(controller: "carbonDesignerApi", action: "getCarbonDesignTest")
        "/cd/getCarbonDesignCreationData"(controller: "carbonDesignerApi", action: "getCarbonDesignCreationData")
        "/cd/getIndicatorDetails"(controller: "carbonDesignerApi", action: "getIndicatorDetails")
        "/cd/getCD3DConfigurations"(controller: "carbonDesignerApi", action: "getCD3DConfigurations")


        // CarbonDesigner3D - Geometry Review calculations
        "/cd/calculateBuildingDimensions"(controller: "carbonDesignerApi", action: "calculateBuildingDimensions")
        "/cd/calculateBuildingDimensionsTest"(controller: "carbonDesignerApi", action: "calculateBuildingDimensionsTest")
        "/cd/calculateBuildingStructureAreas"(controller: "carbonDesignerApi", action: "calculateBuildingStructureAreas")

        // CarbonDesigner3D - Design related APIs
        "/cd/createCarbonDesign"(controller: "carbonDesignerApi", action: "createCarbonDesign")
        "/cd/saveCarbonDesign"(controller: "carbonDesignerApi", action: "saveCarbonDesign")
        "/cd/copyCarbonDesign"(controller: "carbonDesignerApi", action: "copyCarbonDesign")
        "/cd/deleteCarbonDesign"(controller: "carbonDesignerApi", action: "deleteCarbonDesign")
        "/cd/convertCarbonDesign"(controller: "carbonDesignerApi", action: "convertCarbonDesign")
        "/cd/getLCADesignsFromProject"(controller: "carbonDesignerApi", action: "getLCADesignsFromProject")
        "/cd/mergeCarbonDesign"(controller: "carbonDesignerApi", action: "mergeCarbonDesign")
        "/cd/createScenario"(controller: "carbonDesignerApi", action: "createScenario")
        "/cd/renameCarbonDesign"(controller: "carbonDesignerApi", action: "renameCarbonDesign")

        // CarbonDesigner3D - Resource management
        "/cd/addResourceDataset"(controller: "carbonDesignerApi", action: "addResourceDataset")
        "/cd/updateResourceDataset"(controller: "carbonDesignerApi", action: "updateResourceDataset")
        "/cd/removeResourceDataset"(controller: "carbonDesignerApi", action: "removeResourceDataset")
        "/cd/getElementResources"(controller: "carbonDesignerApi", action: "getElementResources")
        "/cd/getMaterialAlternatives"(controller: "carbonDesignerApi", action: "getMaterialAlternatives")
        "/cd/replaceConstituentDatasets"(controller: "carbonDesignerApi", action: "replaceConstituentDatasets")

        // Datacard - Entry point
        "/datacard/getDataCardInfo"(controller: "dataCard", action: "getDataCardInfo")

        //Temporary data layer for UMS
        "/tdl/getUser"(controller: "UMSDataLayer", action: "getUser")
        "/tdl/getEntities"(controller: "UMSDataLayer", action: "getEntities")
        "/tdl/getOrganization"(controller: "UMSDataLayer", action: "getOrganization")
        "/tdl/getLicenses"(controller: "UMSDataLayer", action: "getLicenses")

        // Explicit admin pages
        "/sec/admin/resourceType/index"(controller: "resourceType", action: "index")
        "/sec/admin/resourceType/upstreamDBAverages"(controller: "resourceType", action: "upstreamDBAverages")
        "/sec/admin/construction/constructionsManagementPage"(controller: "construction", action: "constructionsManagementPage")
        "/sec/admin/carbonDesigner/constructionUpload"(controller: "carbonDesigner", action: "constructionUpload")
        "/sec/admin/carbonDesigner3D/buildingTypesUpload"(controller: "carbonDesigner3D", action: "buildingTypesUpload")
        "/sec/admin/carbonDesigner3D/manageCarbonDesigner3DScenarios"(controller: "carbonDesigner3D", action: "manageCarbonDesigner3DScenarios")
        "/sec/admin/dbCleanUp/getCorruptedData"(controller: "dbCleanUp", action: "getCorruptedData")
        "/sec/admin/nmdElement/getApiUpdateForNmdElementManual"(controller: "nmdElement", action: "getApiUpdateForNmdElementManual")
        "/sec/admin/projectTemplate/managePublicTemplate"(controller: "projectTemplate", action: "managePublicTemplate")

        "/static"(redirect: "/assets")

        //epdhub export
        "/exportEPDJson" (controller: "export", action: "exportEPDJson")

        // Error handling
        "500"(controller: "error", action: "show")
        "404"(controller: "error", action: "notFound")
        "403"(controller: "error", action: "notFound")
        "401"(controller: "error", action: "notFound")
    }
}
