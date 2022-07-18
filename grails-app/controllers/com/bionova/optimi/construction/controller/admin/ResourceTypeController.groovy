package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Benchmark
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBObject
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import org.apache.commons.collections.CollectionUtils
import org.apache.poi.ss.usermodel.Workbook
import org.bson.Document
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class ResourceTypeController extends ExceptionHandlerController {

    def resourceTypeService
    def optimiResourceService
    def newCalculationService
    def domainClassService
    def configurationService
    def userService
    def entityService
    def licenseService
    def applicationService

    def index() {
        def activeResourceTypes = resourceTypeService.getActiveResourceTypes()
        def inactiveResourceTypes = resourceTypeService.getInActiveResourceTypes()
        String locale = DomainObjectUtil.getMapKeyLanguage()
        [activeResourceTypes: activeResourceTypes, inactiveResourceTypes: inactiveResourceTypes, locale: locale]
    }

    def upload() {
        if (request instanceof MultipartHttpServletRequest) {
            def excelFile = request.getFile("xlsFile")

            if (!excelFile || excelFile?.empty) {
                flash.errorAlert = g.message(code: "import.file.required")
                redirect action: "index"
            } else {
                try {
                    def mapWithInfo = resourceTypeService.importResourceTypes(excelFile)
                    def file = mapWithInfo.get("file")
                    def sheets = mapWithInfo.get("sheets")
                    def okResourceTypes = mapWithInfo.get("okResourceTypes")
                    def errorResourceTypes = mapWithInfo.get("errorResourceTypes")
                    def errorMessage


                    if (okResourceTypes) {
                        def message = g.message(code: "admin.resourceType.import.ok", args: [file, sheets ? sheets.size() : 0, okResourceTypes.size(), errorResourceTypes ? errorResourceTypes.size() : 0])
                        flash.successAlert = message
                        session?.setAttribute("importIncomplete", true)
                    } else {
                        errorMessage = "Import rejected due to error(s) in the file"
                    }

                    if (mapWithInfo.get("errorMessage")) {
                        errorMessage = errorMessage ? errorMessage + "<br />" + mapWithInfo.get("errorMessage") : mapWithInfo.get("errorMessage")
                    }

                    if (mapWithInfo.get("warnMessage")) {
                        flash.warningAlert = mapWithInfo.get("warnMessage")
                    }

                    if (errorMessage) {
                        flash.errorAlert = errorMessage
                    }
                } catch (Exception e) {
                    flash.errorAlert = e.getMessage()
                }
                chain(action: "index")
            }
        } else {
            flash.errorAlert = "Cannot import excel file, because invalid request: ${request.class} (should be MultipartHttpServletRequest)."
            redirect action: "index"
        }
    }

    def remove() {
        String id = params.id
        boolean deleteOk = false

        if (id) {
            deleteOk = resourceTypeService.deleteResourceType(id)
        }

        if (deleteOk) {
            flash.fadeSuccessAlert = "ResourceType removed successfully"
        } else {
            flash.fadeErrorAlert = "Removing resourceType failed. See logs"
        }
        redirect action: "index"
    }

    def removeFromAjax() {
        String id = params.id
        log.info("SUBTYPE $id")
        boolean deleteOk = false

        if (id) {
            deleteOk = resourceTypeService.deleteResourceType(id)
        }

        if (deleteOk) {
            render text: "ResourceType removed successfully"
        } else {
            render text: "Removing resourceType failed. See logs"
        }
    }

    def showData() {
        String resourceTypeId = params.id
        String resourceType = params.resourceType
        String resourceSubType = params.resourceSubType
        ResourceType rt
        Map properties = [:]

        if (resourceTypeId||resourceType||resourceSubType) {
            if (resourceTypeId) {
                rt = resourceTypeService.getResourceTypeById(resourceTypeId)
            } else if (resourceType) {
                rt = resourceTypeService.getResourceType(resourceType)
            } else if (resourceSubType) {
                String resourceTypeBySubType = resourceTypeService.getResourceTypeBySubType(resourceSubType)

                if (resourceTypeBySubType) {
                    rt = resourceTypeService.getResourceType(resourceTypeBySubType, resourceSubType)
                }
            }

            if (rt) {
                List<String> domainClassProperties = domainClassService.getPersistentPropertyNamesForDomainClass(ResourceType.class)

                if (domainClassProperties) {
                    domainClassProperties.each { property ->
                        properties.put((property), DomainObjectUtil.callGetterByAttributeName(property, rt))
                    }
                }
            }
        }
        [resourceType: rt, properties: properties, hideNavi: true,
         showDataReferences: applicationService.getApplicationByApplicationId("LCA")?.showDataReferences?.find({"resourceType".equals(it.page)})]
    }

    def showResources() {
        List<String> benchmarks = Constants.BENCHMARKS.keySet().toList()
        Double threshold = params.double('failingThreshold')
        String filterType = params.filterType
        String resourceType = params.resourceType
        String subType = params.subType
        String standardUnit = params.standardUnit
        Boolean showFailsConversion = params.boolean("failsConversion")
        Boolean showFailsConversionKg = params.boolean("failsConversionKg")

        if (showFailsConversionKg) {
            standardUnit = "kg"
        }
        Boolean requiredUnitFails = params.boolean("requiredUnitFails")
        Boolean badUnitFails = params.boolean("badUnitFails")
        List<String> dataProperties = []
        List<String> unitsForData = []
        List<Resource> resourcesByResourcetype = []

        List<String> requiredUnits = []
        List<String> avoidableUnits = []

        if (resourceType && subType) {
            if (threshold != null) {
                if ("standardDeviation".equals(filterType)) {
                    resourcesByResourcetype = optimiResourceService.getResourcesByResourceSubTypeAndDeviationThreshold(resourceType, subType, threshold)
                } else if ("changeInImpact".equals(filterType)) {
                    resourcesByResourcetype = optimiResourceService.getResourcesByResourceSubTypeAndFactorThreshold(resourceType, subType, threshold)
                }
            } else {
                if (params.excludeFromBenchmark) {
                    resourcesByResourcetype = optimiResourceService.getResourcesByResourceSubType(resourceType, subType, params.boolean("excludeFromBenchmark"))
                } else {
                    resourcesByResourcetype = optimiResourceService.getResourcesByResourceSubType(resourceType, subType)
                }

                if (resourcesByResourcetype) {
                    session?.setAttribute("resourcesByResourcetype", resourcesByResourcetype)

                    resourcesByResourcetype?.each { Resource resource ->
                        if (resource.dataProperties) {
                            dataProperties.addAll(resource.dataProperties)
                        }

                        if (resource.unitForData) {
                            unitsForData.addAll(resource.unitForData)
                        }
                    }
                }
            }
        } else if (resourceType) {
            resourcesByResourcetype = optimiResourceService.getResourcesByResourceType(resourceType)

            if (resourcesByResourcetype) {
                session?.setAttribute("resourcesByResourcetype", resourcesByResourcetype)

                resourcesByResourcetype?.each { Resource resource ->
                    if (resource.dataProperties) {
                        dataProperties.addAll(resource.dataProperties)
                    }

                    if (resource.unitForData) {
                        unitsForData.addAll(resource.unitForData)
                    }
                }
            }
        }

        if (resourcesByResourcetype && (showFailsConversion || showFailsConversionKg || requiredUnitFails || badUnitFails)) {
            List<Resource> failsConversion = []
            if ((showFailsConversion||showFailsConversionKg) && standardUnit) {
                resourcesByResourcetype.each { Resource resource ->
                    Double value
                    if (!resource.unitForData?.equalsIgnoreCase(standardUnit)) {
                        value = resourceTypeService.convertResourceUnitToStandardUnit(100, resource, standardUnit)

                        if (value == null) {
                            failsConversion.add(resource)
                        }
                    }
                }
            } else if (requiredUnitFails || badUnitFails) {
                ResourceType resourceTypeObject = resourceTypeService.getResourceType(resourceType, subType)
                requiredUnits = resourceTypeObject?.requiredUnits*.toLowerCase()
                avoidableUnits = resourceTypeObject?.avoidableUnits*.toLowerCase()

                resourcesByResourcetype.each { Resource resource ->
                    List<String> allowedUnits = resource.combinedUnits

                    if (requiredUnitFails) {
                        if ((requiredUnits && allowedUnits && !allowedUnits.containsAll(requiredUnits)) || (requiredUnits && !allowedUnits)) {
                            failsConversion.add(resource)
                        }
                    } else {
                        if (avoidableUnits && allowedUnits && CollectionUtils.containsAny(allowedUnits, avoidableUnits*.toLowerCase())) {
                            failsConversion.add(resource)
                        }
                    }
                }
            }
            resourcesByResourcetype = failsConversion
        }
        [resources      : resourcesByResourcetype, resourceType: resourceType, dataProperties: dataProperties,
         unitsForData   : unitsForData, showFailsConversion: showFailsConversion, showFailsConversionKg: showFailsConversionKg, standardUnit: standardUnit, threshold: threshold,
         benchmarks     : benchmarks, requiredUnitFails: requiredUnitFails, badUnitFails: badUnitFails, requiredUnits: requiredUnits, filterType: filterType,
         avoidableUnits : avoidableUnits, benchmarkNames: Constants.BENCHMARKS]
    }

    def benchmarkGraph() {
        String isoFilePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "isoFlagUrlPath")
        List<Resource> resourcesBySubType
        ResourceType subType
        String resourceId = params.resourceId
        String profileId = params.profileId
        List<String> benchmarkToShow = params.list('benchmarkToShow')
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        List<String> drawableBenchmarks = benchmarkToShow ?: Constants.BENCHMARKS.keySet().toList()
        Resource resource
        Map<String, String> countryCodesAndLocalizedName = [:]
        Resource countryOfProject
        String stateIdOfProject = params.stateIdOfProject
        Integer resourcesAmount
        Boolean showNewBenchmark = Boolean.FALSE
        String benchmarkName = ""

        boolean hasDownloadEPDLicense = false

        if (resourceId) {
            resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
        }
        if(entityId){
            Entity entity = entityService.getEntityById(entityId)
            Entity parentEntity = entity?.getParentById()
            if(!parentEntity){
                parentEntity = entity
            }
            countryOfProject = parentEntity?.countryResource
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.BETA_FEATURES])
            showNewBenchmark = featuresAllowed.get(Feature.BETA_FEATURES)
        } else {
            User user = userService.getCurrentUser()
            if(user?.internalUseRoles){
                showNewBenchmark = Boolean.TRUE
            }
        }

        User user = userService.getCurrentUser()
        Account account = userService.getAccount(user)
        if (account) {
            List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsFromAccount(account)

            if (licensedFeatureIds?.find({ it.equalsIgnoreCase(Feature.EPD_DOWNLOAD) })) { hasDownloadEPDLicense = true }
        }

        if (params.resourceSubTypeId) {
            subType = resourceTypeService.getResourceTypeById(params.resourceSubTypeId)

            if (subType) {
                if (benchmarkToShow) {
                    benchmarkName = benchmarkToShow && benchmarkToShow.size() == 1 ? Constants.BENCHMARKS.get(benchmarkToShow.first()) : ""

                    optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"])?.each { Resource r ->
                        Boolean hasValidResult = Boolean.FALSE

                        if ("INCOMPATIBLE_UNITS".equals(r.benchmark?.status)) {
                            hasValidResult = Boolean.TRUE
                        } else {
                            for (String benchmark in benchmarkToShow) {
                                if ("OK".equals(r.benchmark?.status) && r.benchmark?.values?.get(benchmark) && r.getPassesBenchmarkFilter(benchmark)) {
                                    hasValidResult = Boolean.TRUE
                                    break
                                }
                            }
                        }

                        if (hasValidResult) {
                            if (resourcesBySubType) {
                                resourcesBySubType.add(r)
                            } else {
                                resourcesBySubType = [r]
                            }
                        }
                    }
                } else {
                    resourcesBySubType = optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType,null, ["environmentDataSource": "Climate Earth"])?.findAll({ "OK".equals(it.benchmark?.status) || "INCOMPATIBLE_UNITS".equals(it.benchmark?.status) })
                }
            }

            if (resourcesBySubType) {
                resourcesAmount = resourcesBySubType.size()
                resourcesBySubType.each { Resource r ->
                    String area = r.isoCodesByAreas?.keySet()?.first()
                    String isoCode = r.isoCodesByAreas?.get(area)

                    if (isoCode && !countryCodesAndLocalizedName.get(isoCode)) {
                        String localizedCountryName = optimiResourceService.getCountryLocalizedName(area)
                        countryCodesAndLocalizedName.put(isoCode, localizedCountryName)
                    }
                }
            }
        }
        Boolean localEnvironment = Environment.current == System.getProperty("islocalhost")
        [subType: subType, resourcesBySubType: resourcesBySubType, drawableBenchmarks: drawableBenchmarks,resourcesAmount:resourcesAmount,showNewBenchmark:showNewBenchmark,benchmarkName:benchmarkName,
         showPercentages: params.percentages, userResource: resource, benchmarkToShow: benchmarkToShow, localEnvironment: localEnvironment, isoFilePath: isoFilePath,countryOfProject:countryOfProject,stateIdOfProject:stateIdOfProject,
         entityId: entityId, indicatorId: indicatorId, allBenchmarks: Constants.BENCHMARKS, countryCodesAndLocalizedName: countryCodesAndLocalizedName, user: user, downloadEPDLicense: hasDownloadEPDLicense]
    }

    def reDrawTableByCountry () {
        List<Resource> resourcesBySubType = []
        ResourceType subType
        String resourceId = params.resourceId
        List<String> benchmarkToShow = params.list('benchmarkToShow')
        String isoCodes = params.isoCodes
        String profileId = params.profileId
        List<String> drawableBenchmarks = benchmarkToShow ?: Constants.BENCHMARKS.keySet().toList()
        Resource resource
        Map<String, String> countryCodesAndLocalizedName = [:]
        Map<String, String> countryAreaAndLocalizedName = [:]

        if (resourceId) {
            resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
        }

        if (params.resourceSubTypeId) {
            subType = resourceTypeService.getResourceTypeById(params.resourceSubTypeId)

            if (subType) {
                List<Resource> resources

                if (benchmarkToShow) {
                    optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType,null,["environmentDataSource": "Climate Earth"])?.each { Resource r ->
                        Boolean hasValidResult = Boolean.FALSE

                        if ("INCOMPATIBLE_UNITS".equals(r.benchmark?.status)) {
                            hasValidResult = Boolean.TRUE
                        } else {
                            for (String benchmark in benchmarkToShow) {
                                if ("OK".equals(r.benchmark?.status) && r.benchmark?.values?.get(benchmark)) {
                                    hasValidResult = Boolean.TRUE
                                    break
                                }
                            }
                        }

                        if (hasValidResult) {
                            if (resources) {
                                resources.add(r)
                            } else {
                                resources = [r]
                            }
                        }
                    }
                } else {
                    resources = optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType,null, ["environmentDataSource": "Climate Earth"])?.findAll({ "OK".equals(it.benchmark?.status) || "INCOMPATIBLE_UNITS".equals(it.benchmark?.status) })
                }

                if (isoCodes && resources) {
                    List<String> listOfIsoCodes = isoCodes?.tokenize(",")?.toList()

                    resources.each { Resource r ->
                        String area = r.isoCodesByAreas?.keySet()?.first()
                        String isoCode = r.isoCodesByAreas?.get(area)

                        if (isoCode && !countryCodesAndLocalizedName.get(isoCode)) {
                            String localizedCountryName = optimiResourceService.getCountryLocalizedName(area)
                            countryCodesAndLocalizedName.put(isoCode, localizedCountryName)
                            countryAreaAndLocalizedName.put(area, localizedCountryName)
                        }
                        if (listOfIsoCodes?.contains(isoCode)) {
                            resourcesBySubType.add(r)
                        }
                    }
                }
            }

        }


        User user = userService.getCurrentUser(true)
        String templateAsString = g.render(template: "/resourceType/materialsTable", model: [subType        : subType, resourcesBySubType: resourcesBySubType, drawableBenchmarks: drawableBenchmarks,
                                                                                             showPercentages: params.percentages, userResource: resource, benchmarkToShow: benchmarkToShow,
                                                                                             allBenchmarks  : Constants.BENCHMARKS, countryCodesAndLocalizedName: countryCodesAndLocalizedName, countryAreaAndLocalizedName: countryAreaAndLocalizedName, user: user]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resourcesByDataProperty() {
        String dataproperty = params.dataproperty
        List<Resource> resourcesByDataProperty = []
        List<Resource> resourcesByResourcetype = getResourcesByResourcetype()

        if (dataproperty && resourcesByResourcetype) {
            resourcesByDataProperty = resourcesByResourcetype.findAll({ it.dataProperties?.contains(dataproperty) })
        }
        String templateAsString = g.render(template: "/resourceType/resourceList", model: [resources: resourcesByDataProperty]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resourcesFromSession() {
        List<Resource> resourcesByDataProperty = []
        List<Resource> resourcesByResourcetype = getResourcesByResourcetype()

        if (resourcesByResourcetype) {
            resourcesByDataProperty = resourcesByResourcetype.findAll()
        }
        String templateAsString = g.render(template: "/resourceType/resourceList", model: [resources: resourcesByDataProperty]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def List<Resource> getResourcesByResourcetype() {
        return session?.getAttribute("resourcesByResourcetype")
    }

    @Secured(["ROLE_DATA_MGR"])
    def benchmark() {
        List<ResourceType> activeResourceSubTypes = []
        Boolean showthresholdColumn = Boolean.FALSE
        Double threshold = params.double('threshold')
        String filterType = params.filterType
        Map<String, Integer> subtypeFailingThreshold = [:]
        Map<String, List<String>> subtypeBenchmarkFailed = [:]

        if (threshold != null && filterType) {
            if (filterType == "standardDeviation") {
                List<ResourceType> activeResourceSubTypesWithResults = ResourceType.findAllByActiveAndSubTypeIsNotNullAndBenchmarkResultIsNotNull(true)

                if (activeResourceSubTypesWithResults) {
                    activeResourceSubTypesWithResults.each { ResourceType subType ->
                        List<Resource> resources = optimiResourceService.getResourcesByResourceSubTypeAndDeviationThreshold(subType.resourceType, subType.subType, threshold)
                        Integer amountFailing = resources?.size()

                        if (amountFailing) {
                            List<String> benchmark = resources?.collect({it.benchmark?.deviations?.find{pair -> pair.value > threshold}})
                            subtypeFailingThreshold.put(subType.id.toString(), amountFailing)
                            subtypeBenchmarkFailed.put(subType.subType, benchmark?.collect({it.key})?.unique())

                        }
                    }
                } else {
                    flash.errorAlert = "No ResourceSubTypes with benchmark results found."
                }
            } else if (filterType == "changeInImpact") {
                List<ResourceType> activeResourceSubTypesWithResults = ResourceType.findAllByActiveAndSubTypeIsNotNullAndBenchmarkResultIsNotNull(true)

                if (activeResourceSubTypesWithResults) {
                    activeResourceSubTypesWithResults.each { ResourceType subType ->
                        List<Resource> resources = optimiResourceService.getResourcesByResourceSubTypeAndFactorThreshold(subType.resourceType, subType.subType, threshold)
                        Integer amountFailing = resources?.size()

                        if (amountFailing) {
                            List<String> benchmark = resources?.collect({it.benchmark?.factorDeviations?.find{pair -> pair.value > threshold}})
                            subtypeFailingThreshold.put(subType.id.toString(), amountFailing)
                            subtypeBenchmarkFailed.put(subType.subType, benchmark?.collect({it.key})?.unique())

                        }
                    }
                } else {
                    flash.errorAlert = "No ResourceSubTypes with benchmark results found."
                }
            }
        }

        if (subtypeFailingThreshold) {
            showthresholdColumn = Boolean.TRUE
            subtypeFailingThreshold.each { String id, Integer amount ->
                ResourceType subType = ResourceType.findById(DomainObjectUtil.stringToObjectId(id))

                if (subType) {
                    subType.failingGivenThreshold = amount
                    activeResourceSubTypes.add(subType)
                }
            }
        } else {
            activeResourceSubTypes = ResourceType.findAllByActiveAndSubTypeIsNotNull(true)?.sort({ it.resourceType })
        }
        activeResourceSubTypes = activeResourceSubTypes?.sort({it.subType})
        Integer amountOfActiveResourceSubtypes = activeResourceSubTypes ? activeResourceSubTypes.size() : 0
        Map<String, String> benchmarkNames = Constants.BENCHMARKS
        BasicDBObject filter = new BasicDBObject("active",true)
        List<String> impactCategories = Resource.allowedImpactCategories.sort({ it.toLowerCase() })
        List<String> upstreamDBClassified = Resource.collection.distinct("upstreamDBClassified", filter, String.class)?.toList()?.sort({ it.toLowerCase() })
        List<String> subTypeGroups = activeResourceSubTypes?.collect({it.subTypeGroup})?.unique()
        Map<String, List<ResourceType>> groupedSubTypes = [:]

        subTypeGroups?.each { String subTypeGroup ->
            groupedSubTypes.put(subTypeGroup, activeResourceSubTypes.findAll {subTypeGroup.equals(it.subTypeGroup)})
        }
        [groupedSubTypes: groupedSubTypes, amountOfActiveResourceSubtypes: amountOfActiveResourceSubtypes, benchmarks: benchmarkNames.keySet().toList(), benchmarkNames: benchmarkNames,
         showthresholdColumn   : showthresholdColumn, threshold: threshold, impactCategories: impactCategories, upstreamDBClassified: upstreamDBClassified, subtypeBenchmarkFailed: subtypeBenchmarkFailed,
         filterType: params.filterType]
    }

    def benchmarkSelected() {
        List<String> benchmarkSubTypeIds = params.list("benchmarkSubType")
        String errorMessage
        String warningMessage
        String filterType = params.filterType
        Double threshold

        if (params.dataRanges) {
            Workbook wb = resourceTypeService.generateDataRangesExcel(benchmarkSubTypeIds)
            response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            response.setHeader("Content-disposition", "attachment; filename=DataRanges_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
            OutputStream outputStream = response.getOutputStream()
            wb.write(outputStream)
            outputStream.flush()
            outputStream.close()
        } else {
            if (params.getSubTypesByResourcesFailingGivenThreshold) {
                String th = params.deviationThreshold

                if (th) {
                    try {
                        threshold = DomainObjectUtil.convertStringToDouble(th)
                    } catch (Exception e) {
                        flash.errorAlert = "Please enter a valid number: ${e}"
                    }
                }
            } else {
                if (benchmarkSubTypeIds) {
                    List<ResourceType> benchmarkSubTypes = []

                    benchmarkSubTypeIds.each { String id ->
                        ResourceType subType = resourceTypeService.getResourceTypeById(id)
                        if (subType?.standardUnit) {
                            benchmarkSubTypes.add(subType)
                        } else {
                            subType.benchmarkResult = null
                            resourceTypeService.saveResourceType(subType)

                            Benchmark benchmark = new Benchmark()
                            benchmark.status = "NO_STANDARDUNIT_FOR_SUBTYPE"
                            Resource.collection.updateMany([resourceType: subType.resourceType, resourceSubType: subType.subType, active: true], [$set: [benchmark: benchmark]])

                            if (!warningMessage) {
                                warningMessage = "No standardUnit for ResourceSubType: ${subType.subType}, stanardUnit: ${subType.standardUnit} </br>"
                            } else {
                                warningMessage = warningMessage + "No standardUnit for ResourceSubType: ${subType.subType}, stanardUnit: ${subType.standardUnit} </br>"
                            }
                        }
                    }

                    if (benchmarkSubTypes && !benchmarkSubTypes.isEmpty()) {
                        int calculatedSubTypes = 0
                        benchmarkSubTypes.each { ResourceType subType ->
                            List<Document> resourcesBySubType = optimiResourceService.getResourcesAsDocumentsByResourceSubType(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"])

                            if (resourcesBySubType && resourcesBySubType.size() >= 5) {
                                Boolean ok = newCalculationService.calculateResourceBenchmarks(subType, resourcesBySubType)

                                if (ok) {
                                    calculatedSubTypes++
                                }
                            } else {
                                subType.benchmarkResult = null
                                resourceTypeService.saveResourceType(subType)

                                if (!errorMessage) {
                                    errorMessage = "Found no sets of resources that would have more than 5 resources for subType: ${subType.resourceType} / ${subType.subType}, Resources found: ${resourcesBySubType ? resourcesBySubType.size() : "0"}</br>"
                                } else {
                                    errorMessage = errorMessage + "Found no sets of resources that would have more than 5 resources for subType: ${subType.resourceType} / ${subType.subType}, Resources found: ${resourcesBySubType ? resourcesBySubType.size() : "0"}</br>"
                                }
                                Benchmark benchmark = new Benchmark()
                                benchmark.status = "TOO_SMALL_SAMPLE"
                                Resource.collection.updateMany([resourceType: subType.resourceType, resourceSubType: subType.subType, active: true], [$set: [benchmark: benchmark]])
                            }
                        }
                        flash.successAlert = "Benchmarked ${calculatedSubTypes} groups of resources by subType"
                        session?.removeAttribute("importIncomplete")
                    }
                    if(warningMessage){
                        flash.warningAlert = warningMessage
                    }
                    if (errorMessage) {
                        flash.errorAlert = errorMessage
                    }
                } else {
                    flash.fadeErrorAlert = "Did you even choose anything? >:("
                }
            }

            if (threshold != null) {
                redirect action: "benchmark", params: [threshold: threshold, filterType: filterType]
            } else {
                redirect action: "benchmark"
            }
        }
    }

    def graphByBenchmark() {
        String subTypeId = params.resourceSubTypeId
        Boolean showPercentages = params.showPercentages ? Boolean.TRUE : Boolean.FALSE
        List<String> strokeAndPointColors = ['rgba(127,196,204,1)', 'rgba(151,187,205,1)', 'gray', 'rgba(255,98,89,1)', 'rgba(0,128,0,1)', 'rgba(125,197,150,1)', 'rgba(115,49,70,0.8)', 'rgba(243,153,131,1)', 'rgba(0,26,101,0.8)']
        List<String> fillColors = ['rgba(127,196,204,0.1)', 'rgba(151,187,205,0.1)', 'rgba(0,0,0,0.1)', 'rgba(255,98,89,0.1)', 'rgba(0,128,0,0.1)', 'rgba(125,197,150,0.1)', 'rgba(115,49,70,0.1)', 'rgba(243,153,131,0.1)', 'rgba(0,26,101,0.1)']
        String graphDatasets
        String graphLabels
        Boolean drawBars = Boolean.FALSE
        Double scaleStepWidth
        Integer customGraphWidth
        String subTypeName
        String subTypeUnit
        String resourceId = params.resourceId
        String entityId = params.entityId
        String profileId = params.profileId
        List<String> benchmarkToShow = params.list('benchmarkToShow')
        String isoCodes = params.isoCodes
        String stateIdOfProject = params.stateIdOfProject
        String benchmarkName = ""
        Resource userResource
        if (resourceId) {
            userResource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
        }
        List<String> benchmarks = benchmarkToShow ?: Constants.BENCHMARKS.keySet().toList()

        if (subTypeId) {
            ResourceType subType = resourceTypeService.getResourceTypeById(subTypeId)

            if (subType) {
                Double largestMax
                benchmarks.each { String b ->
                    if (!largestMax) {
                        largestMax = subType.benchmarkResult?.max?.get(b)
                    } else if (largestMax && subType?.benchmarkResult?.max?.get(b) > largestMax) {
                        largestMax = subType.benchmarkResult?.max?.get(b)
                    }
                }

                if (largestMax) {
                    scaleStepWidth = largestMax / 10
                }

                subTypeId = subType.id
                subTypeName = resourceTypeService.getLocalizedName(subType)
                subTypeUnit = subType.standardUnit
                List<Resource> resourcesBySubType = optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"])

                if (resourcesBySubType) {

                    if (isoCodes) {
                        List<String> listOfIsoCodes = isoCodes?.tokenize(",")?.toList()
                        resourcesBySubType = resourcesBySubType?.findAll { listOfIsoCodes?.contains(it.isoCodesByAreas?.values()?.first()) }
                    }
                    List<Resource> showableResources = []
                    resourcesBySubType.each { Resource resource ->

                        if (resource.benchmark && resource.benchmark.values && !resource.benchmark.values.isEmpty()) {
                            for (String benchmarkTitle in benchmarks) {
                                if (resource.benchmark.values.get(benchmarkTitle) != null && !resource.benchmark.values.get(benchmarkTitle).equals("NOT_CALCULABLE")) {
                                    showableResources.add(resource)
                                    break
                                }
                            }
                        }
                    }

                    if (!showableResources.isEmpty() && showableResources.size() > 1) {
                        graphDatasets = "["
                        graphLabels = "["

                        if (showableResources.size() > 25) {
                            customGraphWidth = showableResources.size() * 40
                        }
                        Integer colorIndex = 0
                        String strokeAndPointColor
                        String colorFill

                        benchmarks.each { String benchmarkLabel ->
                            if (colorIndex + 1 > fillColors.size()) {
                                colorIndex = 0
                            }
                            colorFill = fillColors.get(colorIndex)
                            strokeAndPointColor = strokeAndPointColors.get(colorIndex)
                            graphDatasets = "${graphDatasets}  {label: \"${g.message(code: 'results')}, ${Constants.BENCHMARKS.get(benchmarkLabel)}\","
                            graphDatasets = "${graphDatasets}  fillColor: \"${colorFill}\",\n" +
                                    "                strokeColor: \"${strokeAndPointColor}\",\n" +
                                    "                pointColor: \"${strokeAndPointColor}\",\n" +
                                    "                pointStrokeColor: \"#fff\",\n" +
                                    "                pointHighlightFill: \"#fff\",\n" +
                                    "                pointHighlightStroke:\"rgba(220,220,220,1)\","
                            graphDatasets = "${graphDatasets} data: ["
                            Integer scoreIndex = 0
                            Double maxOfBenchmark = subType.benchmarkResult?.max?.get(benchmarkLabel)

                            if (benchmarkToShow && benchmarkToShow.size() == 1) {
                                showableResources = showableResources.sort({ a, b -> a.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE") ? b.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE") ? 0 : 1 : b.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE") ? -1 : Double.valueOf(a.benchmark?.values?.get(benchmarkToShow.first())) <=> Double.valueOf(b.benchmark?.values?.get(benchmarkToShow.first()))})
                            }

                            showableResources.each { Resource resource ->
                                if (showPercentages) {
                                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${resource.benchmark?.values?.get(benchmarkLabel) != null && !resource.benchmark?.values?.get(benchmarkLabel)?.equals("NOT_CALCULABLE") ? Double.valueOf(resource.benchmark.values.get(benchmarkLabel)) / maxOfBenchmark * 100 : '0'}"
                                } else {
                                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${resource.benchmark?.values?.get(benchmarkLabel) != null && !resource.benchmark?.values?.get(benchmarkLabel)?.equals("NOT_CALCULABLE") ? resource.benchmark.values.get(benchmarkLabel) : '0'}"
                                }
                                scoreIndex++
                            }
                            graphDatasets = "${graphDatasets}]},"
                            colorIndex++

                        }

                        if (userResource) {
                            Integer quintile = userResource.benchmark?.quintiles?.get(benchmarkToShow.first())
                            String color = quintile == 5 ? "#33A800" : quintile == 4 ? "#76CD02" : quintile == 3 ? "#D5F707" : quintile == 2 ? "#FEB005" : quintile == 1 ? "#D93D00" : "#d3d3d3"
                            graphDatasets = "${graphDatasets}  {label: \"${userResource.staticFullName}\","
                            graphDatasets = "${graphDatasets}  fillColor: \"rgba(220,220,220,0)\",\n" +
                                    "                strokeColor: \"${color}\",\n" +
                                    "                pointColor: \"${color}\",\n" +
                                    "                pointStrokeColor: \"#fff\",\n" +
                                    "                pointHighlightFill: \"#fff\",\n" +
                                    "                pointHighlightStroke:\"rgba(220,220,220,1)\","
                            graphDatasets = "${graphDatasets} data: ["
                            Integer scoreIndex = 0
                            Double maxOfBenchmark = subType.benchmarkResult?.max?.get(benchmarkToShow.first())

                            showableResources.each { Resource resource ->
                                if (showPercentages) {
                                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${userResource.benchmark?.values?.get(benchmarkToShow.first()) != null && !userResource.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE") ? Double.valueOf(userResource.benchmark.values.get(benchmarkToShow.first())) / maxOfBenchmark * 100 : '0'}"
                                } else {
                                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${userResource.benchmark?.values?.get(benchmarkToShow.first()) != null && !userResource.benchmark?.values?.get(benchmarkToShow.first())?.equals("NOT_CALCULABLE") ? userResource.benchmark.values.get(benchmarkToShow.first()) : '0'}"
                                }
                                scoreIndex++
                            }
                            graphDatasets = "${graphDatasets}]},"
                        }

                        benchmarks.each { String benchmarkLabel ->
                            graphDatasets = "${graphDatasets}  {label: \"${g.message(code: 'subTypeBenchmarkGraph_mean_for_benchmark')} ${Constants.BENCHMARKS.get(benchmarkLabel)}\","
                            graphDatasets = "${graphDatasets}  fillColor: \"rgba(220,220,220,0)\",\n" +
                                    "                strokeColor: \"rgba(0,0,0,0.6)\",\n" +
                                    "                pointColor: \"rgba(0,0,0,0.6)\",\n" +
                                    "                pointStrokeColor: \"#fff\",\n" +
                                    "                pointHighlightFill: \"#fff\",\n" +
                                    "                pointHighlightStroke:\"rgba(220,220,220,1)\","
                            graphDatasets = "${graphDatasets} data: ["
                            Integer meanIndex = 0
                            Double maxOfBenchmark = subType.benchmarkResult?.max?.get(benchmarkLabel)

                            List<Double> values = showableResources.findAll({
                                it.benchmark.values.get(benchmarkLabel) && !it.benchmark.values.get(benchmarkLabel).equals("NOT_CALCULABLE")
                            })?.collect({ it.benchmark.values.get(benchmarkLabel).toDouble() })
                            Double meanForThisBenchmark = values ? values.sum() / values.size() : null

                            showableResources.each { Resource resource ->
                                if (showPercentages) {
                                    graphDatasets = "${graphDatasets}${meanIndex > 0 ? ', ' : ''}${meanForThisBenchmark ? meanForThisBenchmark / maxOfBenchmark * 100 : '0'}"
                                } else {
                                    graphDatasets = "${graphDatasets}${meanIndex > 0 ? ', ' : ''}${meanForThisBenchmark ? meanForThisBenchmark : '0'}"
                                }
                                meanIndex++
                            }
                            graphDatasets = "${graphDatasets}]},"
                        }

                        graphDatasets = "${graphDatasets}]"

                        Integer labelIndex = 0

                        showableResources.each { Resource resource ->
                            graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${labelIndex + 1}. ${abbr(value: resource.nameEN, maxLength: 30)}, ${optimiResourceService.getCountryLocalizedName(resource.areas?.first()) ?: resource.areas?.first()}\""
                            labelIndex++
                        }
                        graphLabels = "${graphLabels}]"
                        benchmarkName = benchmarkToShow && benchmarkToShow.size() == 1 ? Constants.BENCHMARKS.get(benchmarkToShow.first()) : ""
                    }
                }
            }
        }
        String templateAsString = g.render(template: "/resourceType/graphByBenchmark", model: [graphDatasets    : graphDatasets ?: "[]", graphLabels: graphLabels ?: "[]",entityId : entityId,stateIdOfProject: stateIdOfProject,
                                                                                               drawBars         : drawBars, scaleStepWidth: scaleStepWidth,
                                                                                               customGraphWidth : customGraphWidth, subTypeName: subTypeName, subTypeUnit: subTypeUnit,
                                                                                               subTypeId        : subTypeId, showPercentages: showPercentages, userResource: userResource, benchmarkToShow: benchmarkToShow, benchmarkName: benchmarkName]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    def graphBenchmarkGroupByRegion(){
        String subTypeId = params.resourceSubTypeId
        Boolean showPercentages = params.showPercentages ? Boolean.TRUE : Boolean.FALSE
        String subTypeName
        String subTypeUnit
        String benchmarkUnit
        Integer resourcesAmount
        String resourceId = params.resourceId
        String profileId = params.profileId
        String projectCountryId = params.projectCoutryId
        String stateIdOfProject = params.stateIdOfProject
        String benchmarkToShow = params.benchmarkToShow
        String benchmarkName = ""
        Resource userResource
        List<Resource> resourcesByNeighbor = []
        List<Resource> resourcesByCountry = []
        List<Resource> resourcesByState = []
        List<Resource> allResources = []
        Map<String, Map<String, Map<String,Double>>> valueBenchmarkByRegion = [:]
        Map<String, Map<String,Map<String,Double>>> valueForGraph = [:]
        Map<String,String> typeOfRegion = ["state":null,"country":null,"neighbour":null,"all":null]
        Map<String,Map<String,List<Double>>> regionsAndValueListByBenchmark = [:]
        List<String> localizedNameForRegion = []
        List<String> benchmarkRanking = ["Very low", "Low", "Average", "High", "Very high"]
        int cutOffPercentage = params.int("threshold")
        Map<String,Double> valuesForNeighbour = [:]
        Map<String,Double> valuesForCountry = [:]
        Map<String,Double> valuesForState = [:]
        Map<String,Double> valuesForAll = [:]
        Map<String, List<Resource>> mapResourceByBenchmark = [:]
        Double minScale = 0.0
        Resource stateForProject
        Resource countryForProject
        ResourceType subType
        List<String> neighborCountries
        List<String> benchmarks = benchmarkToShow ? [benchmarkToShow] : Constants.BENCHMARKS.keySet().toList()
        String sourceForCountry = ""

        if (resourceId) {
            userResource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
        }
        if (subTypeId && benchmarks && !benchmarks.isEmpty()) {

            subType = resourceTypeService.getResourceTypeById(subTypeId)
            benchmarkToShow = benchmarks.first()

            if (subType) {

                if(projectCountryId){

                    countryForProject = optimiResourceService.getResourceByResourceAndProfileId(projectCountryId, null)
                    neighborCountries = countryForProject?.neighbouringCountries

                    if (stateIdOfProject) {
                        stateForProject = optimiResourceService.getResourceByResourceAndProfileId(stateIdOfProject, null)
                    }
                } else {

                    User user = userService.getCurrentUser()
                    if(user?.country){
                        countryForProject = optimiResourceService.getResourceByResourceAndProfileId(user.country, null)
                        neighborCountries = countryForProject?.neighbouringCountries
                        if(countryForProject){
                            sourceForCountry = g.message(code:"country_from_user")
                        }
                    }
                }



                subTypeName = resourceTypeService.getLocalizedName(subType)
                subTypeUnit = subType?.standardUnit.toLowerCase()
                benchmarkName = Constants.BENCHMARKS.get(benchmarkToShow)
                benchmarkUnit = Constants.BENCHMARKSUNIT.get(benchmarkToShow)
                allResources = optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"])
                String key = ""
                String keyState

                if (allResources && !allResources.isEmpty()) {

                    benchmarks.each { String benchmarkId ->
                        Map<String, Map<String,Double>> regionsAndValueList = [:]
                        allResources = allResources.sort({ it.benchmark?.values?.get(benchmarkId) })?.findAll({!it?.benchmark?.values?.get(benchmarkId)?.equalsIgnoreCase("NOT_CALCULABLE") && it?.benchmark?.values?.get(benchmarkId) != null})
                        Map<String, Map<String, Double>> meanAndMedianForThisBenchmark = [:]
                        Map<String, List<Double>> resultsForThisBenchmark = [:]
                        //CHECKING IF AREA IS STATE LEVEL OR COUNTRY LEVEL
                        if(stateForProject){
                            key = optimiResourceService.getLocalizedName(stateForProject)
                            typeOfRegion.put("state", key)
                            resourcesByState = allResources?.findAll({ it?.areas?.contains(stateForProject?.resourceId) })
                            valuesForState = resourcesByState?.collectEntries({[(it.id.toString()) : it?.benchmark?.values?.get(benchmarkId)?.toDouble()]})?.sort({it.value})
                            regionsAndValueList.put(key, valuesForState)
                            meanAndMedianForThisBenchmark.put(key, getMedianAndMean(valuesForState))
                        }
                        if(countryForProject){
                            key = optimiResourceService.getLocalizedName(countryForProject)
                            typeOfRegion.put("country", key)
                            resourcesByCountry = allResources?.findAll({ it?.areas?.contains(countryForProject?.resourceId) })
                            valuesForCountry = resourcesByCountry?.collectEntries({[(it.id.toString()) : it?.benchmark?.values?.get(benchmarkId)?.toDouble()]})?.sort({it.value})
                            regionsAndValueList.put(key, valuesForCountry)
                            meanAndMedianForThisBenchmark.put(key, getMedianAndMean(valuesForCountry))
                        }
                        if (neighborCountries && !neighborCountries.isEmpty()) {
                            key = message(code: "neighbour_countries")
                            typeOfRegion.put("neighbour", key)

                            resourcesByNeighbor = allResources.findAll({it.areas?.intersect(neighborCountries) })
                            valuesForNeighbour = resourcesByNeighbor?.collectEntries({[(it.id.toString()) : it?.benchmark?.values?.get(benchmarkId)?.toDouble()]})?.sort({it.value})
                            regionsAndValueList.put(key, valuesForNeighbour)

                            meanAndMedianForThisBenchmark.put(key, getMedianAndMean(valuesForNeighbour))
                        }
                        valuesForAll = allResources?.collectEntries({[(it.id.toString()) : it?.benchmark?.values?.get(benchmarkId)?.toDouble()]})?.sort({it.value})
                        resourcesAmount = valuesForAll?.size()
                        key = message(code: "all")
                        typeOfRegion.put("all", key)

                        regionsAndValueList.put(key, valuesForAll)

                        meanAndMedianForThisBenchmark.put(key, getMedianAndMean(valuesForAll))
                        valueBenchmarkByRegion.put(benchmarkId, meanAndMedianForThisBenchmark)
                        Map<String, Map<String, Double>> returnList = getMapForBenchmarkGraph(regionsAndValueList, cutOffPercentage)

                        valueForGraph.put(benchmarkId, returnList)

                        //LIST WITH ONLY BENCHMARK VALUE
                        regionsAndValueListByBenchmark.put(benchmarkId, regionsAndValueList)
                        //LIST WITH FULL PRODUCT
                        mapResourceByBenchmark.put(benchmarkId,allResources)
                    }
                    localizedNameForRegion = valueForGraph.get(benchmarkToShow).collect({
                        it.value.collect { it.key } asList()
                    })?.flatten()?.unique()
                }
            }
        }
        String templateAsString = g.render(template: "/resourceType/graphBenchmarkGroupByRegion", model: [sourceForCountry: sourceForCountry, subTypeName: subTypeName, subTypeUnit: subTypeUnit, valueForGraph: valueForGraph, typeOfRegion: typeOfRegion, localizedNameForRegion: localizedNameForRegion, localizedNameForRegionAsJSON: localizedNameForRegion as JSON, countryForProject: countryForProject, stateForProject: stateForProject,
                                                                                                          subTypeId       : subTypeId, showPercentages: showPercentages, userResource: userResource, minScale: minScale, valueBenchmarkByRegion: valueBenchmarkByRegion, benchmarkUnit: benchmarkUnit, mapResourceByRegion: mapResourceByBenchmark, cutOffPercentage: cutOffPercentage,
                                                                                                          resourcesAmount : resourcesAmount, benchmarkToShow: benchmarkToShow, benchmarkName: benchmarkName, regionsAndValueListByBenchmark: regionsAndValueListByBenchmark]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    private getMedianAndMean(Map<String,Double> valueList){
        Double mean
        Double median
        Double percentileEighty
        int midNumber
        int size
        List<Double> values = valueList?.collect({it.value}).toList()
        if(values && !values.isEmpty()){
            size = values?.size()
            Double sum = values?.sum()
            int indexOfEighty = (int)(size*0.8)
            percentileEighty = values[indexOfEighty]
            mean = sum / size
            midNumber = (int)(size/2)
            median = size %2 != 0 ? values[midNumber] : (values[midNumber] + values[midNumber-1])/2
        }
        return [sampleSize: size, percentileEighty: percentileEighty, mean: mean, median: median]
    }
    private getMapForBenchmarkGraph(  Map<String, Map<String,Double>> regionsAndValueList, cutOffPercentage){
        Map<String, Double> benchmarkRanking = ["Very high":100,"High":80, "Average":60, "Low":40,"Very low": 20,"min":0]
        int percent = 5
        Map<String,Map<String,Double>> returnList = [:]
        Double preW = 0.0
        Double preX = 0.0
        Double preY = 0.0
        Double preZ = 0.0
        if(regionsAndValueList){
            if(cutOffPercentage != null){
                percent= cutOffPercentage
            }
            benchmarkRanking = ["Very high":(100-percent),"High":80, "Average":60, "Low":40,"Very low": 20,"min":percent]

            benchmarkRanking.each {key,threshold ->
                Map<String, Double> mapForThreshold = [:]
                regionsAndValueList.each {region, resultMap ->
                    List<Double> resultList = resultMap?.collect({it.value})
                    if(resultList && resultList.size() > 4){
                        int position = Math.round((resultList.size() * threshold) / 100).intValue()
                        Double z = 0.0
                        if(position > 0){
                            position = position - 1
                        }
                        if(position < resultList.size()) {
                            z = resultList.get(position)
                        }
                        mapForThreshold.put(region, z)
                    }

                }
                returnList.put(key, mapForThreshold)

            }


        }
        return returnList

    }

    def filterResourcesBySubTypeAndBenchmark() {
        String isoFilePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "isoFlagUrlPath")
        List<Resource> resourcesBySubType
        ResourceType subType
        String benchmarkToShow = request?.JSON?.benchmarkToShow
        List<String> resourceIdList = request?.JSON?.resourceIdList?.split(",").collect({ it as String })
        String unit = request?.JSON?.unit
        Map<String, String> countryCodesAndLocalizedName = [:]
        Double benchmarkValue = request?.JSON?.value
        Integer resourcesAmount = 0
        String benchmarkUnit
        if (request?.JSON?.subtypeId) {
            subType = resourceTypeService.getResourceTypeById(request?.JSON?.subtypeId)

            if (subType) {
                if (benchmarkToShow) {
                    benchmarkUnit = Constants.BENCHMARKSUNIT.get(benchmarkToShow)

                    resourcesBySubType = optimiResourceService.getResourcesByResourceSubTypeAndResourceIds(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"], resourceIdList)
                    if (resourcesBySubType) {
                        if (benchmarkValue) {
                            resourcesBySubType = resourcesBySubType?.findAll({ it?.benchmark?.values?.get(benchmarkToShow) && !it?.benchmark?.values?.get(benchmarkToShow)?.equalsIgnoreCase("NOT_CALCULABLE") && it?.benchmark?.values?.get(benchmarkToShow).toDouble() <= benchmarkValue })?.sort({ it.benchmark?.values?.get(benchmarkToShow).toDouble() })
                        } else {
                            resourcesBySubType = resourcesBySubType?.findAll({ it?.benchmark?.values?.get(benchmarkToShow) && !it?.benchmark?.values?.get(benchmarkToShow)?.equalsIgnoreCase("NOT_CALCULABLE") })?.sort({ it.benchmark?.values?.get(benchmarkToShow).toDouble() })
                        }

                        resourcesAmount = resourcesBySubType.size()
                        resourcesBySubType.each { Resource r ->
                            String area = r.isoCodesByAreas?.keySet()?.first()
                            String isoCode = r.isoCodesByAreas?.get(area)

                            if (isoCode && !countryCodesAndLocalizedName.get(isoCode)) {
                                String localizedCountryName = optimiResourceService.getCountryLocalizedName(area)
                                countryCodesAndLocalizedName.put(isoCode, localizedCountryName)
                            }
                        }
                    }
                }
            }
        }
        User user = userService.getCurrentUser(true)
        Boolean downloadEPDLicense = licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.EPD_DOWNLOAD], user).get(Feature.EPD_DOWNLOAD)
        Boolean localEnvironment = Environment.current == System.getProperty("islocalhost")
        String templateAsString = g.render(template: "/resourceType/benchmarkMaterialTable", model: [subType: subType, benchmarkUnit: benchmarkUnit, area: request?.JSON?.area, unit: unit, resourcesBySubType: resourcesBySubType, resourcesAmount: resourcesAmount, benchmarkToShow: benchmarkToShow, localEnvironment: localEnvironment, isoFilePath: isoFilePath, allBenchmarks: Constants.BENCHMARKS, countryCodesAndLocalizedName: countryCodesAndLocalizedName, user: user, downloadEPDLicense: downloadEPDLicense]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def generateImpactsExcelFromSubtype() {
        String resourceType = params.resourceType
        String resourceSubType = params.subType
        String stage = params.stage
        Boolean impactsAsKg = params.boolean("impactsAsKg")

        if (resourceType && resourceSubType) {
            ResourceType subType = resourceTypeService.getResourceType(resourceType, resourceSubType, Boolean.TRUE)
            List<Resource> resourcesByResourceType = optimiResourceService.getResourcesByResourceSubType(resourceType, resourceSubType,null, ["environmentDataSource": "Climate Earth"])

            if (subType && resourcesByResourceType && stage) {
                Workbook wb = resourceTypeService.generateImpactsExcel(subType, resourcesByResourceType, stage, impactsAsKg)

                if (wb) {
                    response.contentType = 'application/vnd.ms-excel'
                    impactsAsKg ? response.setHeader("Content-disposition", "attachment; filename=${resourceSubType}_${stage}_impacts_(KG)_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls") : response.setHeader("Content-disposition", "attachment; filename=${resourceSubType}_${stage}_impacts_(${subType.standardUnit})_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    //response.setHeader("Content-disposition", "attachment; filename=${resourceSubType}_${stage}_impacts_(${subType.standardUnit})_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                    OutputStream outputStream = response.getOutputStream()
                    wb.write(outputStream)
                    outputStream.flush()
                    outputStream.close()
                }
            }
        }
    }

    // Deprecate code, to be removed by the end of feature 0.5.0 release
//    def upstreamDBAverages() {
//        List<ResourceType> benchmarkSubTypes = resourceTypeService.getActiveResourceTypes()?.findAll({ it.subType && it.standardUnit })
//
//        if (benchmarkSubTypes) {
//            List<String> upstreamDBs = ["Ecoinvent", "Gabi"]
//
//            benchmarkSubTypes.each { ResourceType subType ->
//                List<Resource> resourcesBySubType = optimiResourceService.getResourcesByResourceSubType(subType.resourceType, subType.subType, null, ["environmentDataSource": "Climate Earth"])?.findAll({ it.unitForData && it.dataProperties?.contains("CML") })
//
//                if (resourcesBySubType && resourcesBySubType.size() >= 5) {
//                    subType.resourceAveragesByUpstreamDBClassified = resourceTypeService.calculateAveragesByUpstreamDBClassified(upstreamDBs, resourcesBySubType, subType)
//                }
//            }
//        }
//        List<String> dontShowPls = ["impactGWP100_kgCO2e_biogenic","biogenicCarbonStorage_kgCO2e"]
//        List<String> impactCategories = new ArrayList<String>(Resource.allowedImpactCategories)
//        impactCategories?.removeAll(dontShowPls)
//        [benchmarkSubTypes: benchmarkSubTypes?.sort({ a, b -> a.resourceAveragesByUpstreamDBClassifiedAmounts?.values()?.sum() == null ? b.resourceAveragesByUpstreamDBClassifiedAmounts?.values()?.sum() == null ? 0 : 1 : b.resourceAveragesByUpstreamDBClassifiedAmounts?.values()?.sum() == null ? -1 : (a.resourceAveragesByUpstreamDBClassifiedAmounts?.values()?.sum()) <=> (b.resourceAveragesByUpstreamDBClassifiedAmounts?.values()?.sum())})?.reverse(), impactCategories: impactCategories]
//    }

    def generateQAExcel() {
        String id = params.id

        if (id) {
            ResourceType resourceType = resourceTypeService.getResourceTypeById(id)

            if (resourceType) {
                List<Resource> resourcesByResourceType = optimiResourceService.getResourcesByResourceTypeObject(resourceType)

                if (resourcesByResourceType) {
                    Workbook wb = resourceTypeService.generateQAExcel(resourceType, resourcesByResourceType)

                    if (wb) {
                        String filename = "${resourceType.resourceType}_${resourceType.isSubType ? "(${resourceType.subType})_" : ''}${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx"
                        response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                        response.setHeader("Content-disposition", "attachment; filename=${filename}")
                        OutputStream outputStream = response.getOutputStream()
                        wb.write(outputStream)
                        outputStream.flush()
                        outputStream.close()
                    } else {
                        render text: "Could not generate excel for: ${resourceType?.resourceType} / ${resourceType?.subType}"
                    }
                } else {
                    render text: "No resources found for resourceType: ${resourceType?.resourceType} / ${resourceType?.subType}"
                }
            } else {
                render text: "No resourceType found with id: ${id}"
            }
        } else {
            render text: "No id: ${id}"
        }
    }
}