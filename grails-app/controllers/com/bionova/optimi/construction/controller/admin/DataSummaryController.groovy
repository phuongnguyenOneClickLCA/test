/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.*
import com.bionova.optimi.core.service.DataSummaryService
import com.bionova.optimi.domainDTO.IndicatorUsageDTO
import com.mongodb.BasicDBObject
import com.mongodb.client.FindIterable
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Environment
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartHttpServletRequest

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
class DataSummaryController extends ExceptionHandlerController {

    def queryService
    def indicatorService
    def applicationService
    def entityService
    def licenseService
    def userService
    def optimiResourceService
    def kpiReportService
    def nmdResourceService
    DataSummaryService dataSummaryService

    private static final Integer PAGINATION_DEFAULT_OFFSET = 0
    private static final Integer PAGINATION_DEFAULT_LIMIT = 10

    def index() {
        def indicators = indicatorService.getAllIndicators()
        def prodWarning

        if (Environment.current == Environment.PRODUCTION && request.getServerName() in Constants.PROD_DOMAINS) {
            prodWarning = Constants.PROD_WARN
        }
        def indicatorsByDeprecated = indicators?.groupBy{it?.deprecated == Boolean.TRUE}

        [indicators: indicators,
         validIndicators: indicatorsByDeprecated?.get(Boolean.FALSE),
         deprecatedIndicators: indicatorsByDeprecated?.get(Boolean.TRUE),
         prodWarning: prodWarning]
    }

    def synchronized indicatorUsage() {
        String indicatorId = params.indicatorId
        Integer offset = params.int("offset") ?: PAGINATION_DEFAULT_OFFSET
        Integer limit = params.int("max") ?: PAGINATION_DEFAULT_LIMIT

        IndicatorUsageDTO indicatorUsageDTO = dataSummaryService.getIndicatorUsage(indicatorId, limit, offset)

        List<License> allLicenses = indicatorUsageDTO.getAllLicenses()
        List<License> licenses = new ArrayList<>()

        if (allLicenses) {
            allLicenses.each {License it ->
                if (it.licensedIndicatorIds && it.licensedIndicatorIds.contains(indicatorId)) {
                    licenses.add(it)
                }
            }
        }

        [entities: indicatorUsageDTO.getEntities(), licenses: licenses, indicator: indicatorUsageDTO.getIndicator(), hideNavi: true, users: indicatorUsageDTO.getUsers(),
         projectIdAndChildren: indicatorUsageDTO.getProjectIdAndChildren(), projectIdAndLicenses: indicatorUsageDTO.getProjectIdAndLicenses(), totalIndicatorUsage: indicatorUsageDTO.getTotalIndicatorUsage()]
    }

    def queryUsage() {
        String queryId = params.queryId
        Query query = queryService.getQueryByQueryId(queryId, Boolean.TRUE)

        if (!query) {
            query = queryService.getQueryByQueryId(queryId, Boolean.FALSE)
        }
        Map entityNameWithDatasets = entityService.getEntitiesByQueryUsage(queryId)
        [entityNameWithDatasets: entityNameWithDatasets, query: query, hideNavi: true]
    }

    def queries() {
        def language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
        List<Document> queries = []
        FindIterable<Document> asDocuments = queryService.getAllQueriesAsDocuments()

        if (asDocuments) {
            queries.addAll(asDocuments)
        }
        def prodWarning

        if (Environment.current == Environment.PRODUCTION && request.getServerName() in Constants.PROD_DOMAINS) {
            prodWarning = Constants.PROD_WARN
        }
        [queries: queries?.sort({ it.get("queryId") }), prodWarning: prodWarning, language: language]
    }

    def applications() {
        Date start = new Date()
        def applications = applicationService.getAllApplications()
        Date end = new Date()
        log.info("Time for getting apps: ${end.time - start.time}")

        def prodWarning

        if (Environment.current == Environment.PRODUCTION && request.getServerName() in Constants.PROD_DOMAINS) {
            prodWarning = Constants.PROD_WARN
        }
        [applications: applications, prodWarning: prodWarning]
    }

    def systemEntities() {
        User user = userService.getCurrentUser(true)
        BasicDBObject filter = new BasicDBObject("active", true)
        List<String> ids = Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()?.sort({ it.toLowerCase() })
        List<Entity> entities

        if (userService.getSuperUser(user)) {
            entities = Entity.findAllByEnableAsUserTestDataAndDeletedAndParentEntityIdIsNotNull(Boolean.TRUE, Boolean.FALSE)?.sort({ it.parentEntityId.toString() })
        }
        [entities: entities, indicatorIds: ids]
    }
    def carbonHeroEntities(){
        User user = userService.getCurrentUser(true)
        def indicators = indicatorService.getAllIndicators()
        List<Document> projectsWithCarbonHeroes

        if (userService.getSuperUser(user)) {
            BasicDBObject filter = new BasicDBObject("carbonHero", true)
            filter.put("deleted", false)
            filter.put("parentEntityId", [$exists: true])
            List<ObjectId> ids = Entity.collection.distinct("parentEntityId", filter, ObjectId.class)?.toList()
            projectsWithCarbonHeroes = Entity.collection.find([_id: [$in: ids]])?.toList()
        }
        List<Indicator> benchmarkIndicators = indicators.findAll({ "benchmark".equalsIgnoreCase(it.visibilityStatus) && it.active })
        [benchmarkIndicators: benchmarkIndicators, projectsWithCarbonHeroes: projectsWithCarbonHeroes]
    }

    def activateQuery() {
        Query query = queryService.activateQuery(params.id)

        if (!query.hasErrors()) {
            flash.successAlert = message(code: 'admin.dataSummary.query.enabled', args: [query.queryId])
        }

        if (params.applicationId) {
            redirect controller: "application", action: "form", id: params.applicationId
        } else {
            chain(action: "queries", model: [mongoObject: query])
        }
    }

    def inactivateQuery() {
        Query query = queryService.inactivateQuery(params.id)
        flash.successAlert = message(code: 'admin.dataSummary.query.disabled', args: [query.queryId])

        if (params.applicationId) {
            redirect controller: "application", action: "form", id: params.applicationId
        } else {
            redirect action: "queries"
        }
    }

    def inactivateIndicator() {
        def indicator = indicatorService.getIndicatorById(params.id)
        indicator.active = false
        indicatorService.saveIndicator(indicator)
        flash.successAlert = message(code: 'admin.dataSummary.indicator.disabled', args: [indicator.indicatorId])
        redirect action: "index"
    }

    def activateIndicator() {
        def indicator = indicatorService.getIndicatorById(params.id)
        indicator.active = true
        indicator = indicatorService.saveIndicator(indicator)

        if (!indicator.hasErrors()) {
            flash.successAlert = message(code: 'admin.dataSummary.indicator.enabled', args: [indicator.indicatorId])
        }
        chain(action: "index", model: [mongoObject: indicator])
    }

    def deprecateIndicator() {
        def indicator = indicatorService.getIndicatorById(params.id)

        if (indicator && !indicator.deprecated) {
            indicator.deprecated = true
            indicator.deprecatedDate = new Date()
            indicator = indicatorService.saveIndicator(indicator)

            if (!indicator.hasErrors()) {
                flash.successAlert = message(code: 'admin.dataSummary.indicator.enabled', args: [indicator.indicatorId])
            }
        }
        chain(action: "index", model: [mongoObject: indicator])
    }

    def undeprecateIndicator() {
        def indicator = indicatorService.getIndicatorById(params.id)

        if (indicator && indicator.deprecated) {
            indicator.deprecated = false
            indicator.deprecatedDate = null
            indicator = indicatorService.saveIndicator(indicator)

            if (!indicator.hasErrors()) {
                flash.successAlert = message(code: 'admin.dataSummary.indicator.undeprecated', args: [indicator.indicatorId])
            }
        }
        chain(action: "index", model: [mongoObject: indicator])
    }

    def importQuery() {
        try {
            def queryFile = request.getFile("queryFile")

            if (!queryFile || queryFile?.empty) {
                flash.errorAlert = message(code: "import.file.required")
            } else {
                Map returnable = queryService.importQuery(queryFile)
                flash.warningAlert = "${returnable?.get("fileName")} ${returnable?.get("cmdResponse")}"

                if (returnable?.get("duplicateError")) {
                    flash.errorAlert = "${returnable?.get("duplicateError")}"
                }
            }
        } catch (Exception e) {
            if (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.CUSTOM) {
                flash.errorAlert = e
            }
            log.warn("Exception while importQuery: ", e)
        }

        if (params.applicationId && params.id) {
            redirect controller: "application", action: "form", id: params.id
        } else {
            redirect action: "queries"
        }
    }

    def importApplication() {
        if (request instanceof MultipartHttpServletRequest) {
            def applicationFile = request.getFile("applicationFile")

            if (!applicationFile || applicationFile?.empty) {
                flash.errorAlert = message(code: "import.file.required")
            } else {
                String response = applicationService.importApplication(applicationFile)

                if (response?.toLowerCase()?.contains("error")) {
                    flash.errorAlert = "Import tool response: ${response}"
                } else {
                    flash.warningAlert = "Import tool response: ${response}"
                }
            }
        }
        redirect action: "applications"
    }

    def importIndicator() {
        if (request instanceof MultipartHttpServletRequest) {
            def indicatorFile = request.getFile("indicatorFile")

            if (!indicatorFile || indicatorFile?.empty) {
                flash.errorAlert = message(code: "import.file.required")
            } else {
                flash.warningAlert = "Import tool response: " + indicatorService.importIndicator(indicatorFile)
            }
        }
        redirect action: "index"
    }

    def deleteQuery() {
        def query = queryService.getQueryById(params.id)
        queryService.deleteQuery(query)
        flash.successAlert = message(code: 'admin.dataSummary.query.deleted', args: [query.queryId])
        redirect action: "queries"
    }

    def deleteApplication() {
        boolean deleteOk = applicationService.removeApplication(params.id)

        if (deleteOk) {
            flash.successAlert = message(code: 'admin.dataSummary.application.deleted')
        }
        redirect action: "applications"
    }

    def deleteIndicator() {
        def indicator = indicatorService.getIndicatorById(params.id)
        indicatorService.deleteIndicator(indicator)
        flash.successAlert = message(code: 'admin.dataSummary.indicator.deleted', args: [indicator.indicatorId])
        redirect action: "index"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getLicenseTypeUsers() {
        String licenseType = params.licenseType
        String indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

        List<License> licenses = licenseService.getValidLicenses()?.findAll({ it.compatibleEntityClasses?.contains("building") && licenseType.equalsIgnoreCase(it.type) })
        List<String> users = []
        int count = 0

        if (licenses && indicatorId) {
            licenses = licenses.findAll({ it.licensedIndicatorIds?.contains(indicatorId) })
            licenses.each { License license ->
                license.licensedEntities?.each { Entity entity ->
                    entity.users.each { User user ->
                        if (user && user.username && !user.accountLocked && !user.accountExpired && user.enabled &&
                                user.emailValidated && !users.contains(user.username) && !user.username.contains("bionova")) {
                            users.add(user.username)
                        }
                    }
                }

            }
        }

        if (users) {
            count = users.unique().size()
        }
        render([output: "<strong>Amount: ${count.toString()}<br/> ${licenseType ? "LicenseType: ${licenseType}<br/>" : ""} ${indicator ? "Indicator: ${indicatorService.getLocalizedName(indicator)}<br/>" : ""}Usernames as list:</strong>${users.unique()?.toString()}", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def productDataLists(){
        List<Resource> resources
        List<String> applicationIds = Application.collection.distinct("applicationId", String.class)?.toList()?.sort()
        params.max = params.max ?: 50

        int ecoCount = optimiResourceService.getEcoinventDatapointsCount()
        resources = optimiResourceService.getResourcesByAttributeNameAndValue("applicationId", "LCA-Ecoinvent")
        List<String> resourceType = resources.resourceSubType.unique()
        [resources: resources?.sort({ it.resourceId }), resourceSubType: resourceType, ecoCount: ecoCount, params: params, applicationIds: applicationIds]
    }

    def nmdUpdateRecords(){
        List<NmdUpdate> updateList = NmdUpdate.list()
        Set<Integer> unrecognizableUnitId = []
        Set<Integer> unrecognizableFaseId = []
        Set<Integer> unrecognizableToepassingId = []
        Set<Integer> unrecognizableMilieuCategorieId = []

        if (updateList) {
            for (NmdUpdate update in updateList) {
                if (!update) {
                    continue
                }

                if (update.unitErrors) {
                    unrecognizableUnitId.addAll(update.unitErrors)
                }
                if (update.faseIdErrors) {
                    unrecognizableFaseId.addAll(update.faseIdErrors)
                }
                if (update.toepassingIdErrors) {
                    unrecognizableToepassingId.addAll(update.toepassingIdErrors)
                }
                if (update.milieuCategorieIdErrors) {
                    unrecognizableMilieuCategorieId.addAll(update.milieuCategorieIdErrors)
                }
            }

            if (unrecognizableUnitId) {
                Set<Integer> knownUnits = nmdResourceService.getKnownNmdUnits()
                if (knownUnits) {
                    unrecognizableUnitId -= knownUnits
                }
            }

            if (unrecognizableFaseId) {
                Set<Integer> knownFaseIds = nmdResourceService.getKnownFaseIds()
                if (knownFaseIds) {
                    unrecognizableFaseId -= knownFaseIds
                }
            }

            if (unrecognizableToepassingId) {
                Set<Integer> knownToePassingIds = nmdResourceService.getKnownToepassingIds()
                if (knownToePassingIds) {
                    unrecognizableToepassingId -= knownToePassingIds
                }
            }

            if (unrecognizableMilieuCategorieId) {
                Set<Integer> knownMilieuCategorieIds = nmdResourceService.getKnownMilieuCategorieIds()
                if (knownMilieuCategorieIds) {
                    unrecognizableMilieuCategorieId -= knownMilieuCategorieIds
                }
            }
        }
        [updateList                     : updateList,
         unrecognizableUnitId           : unrecognizableUnitId,
         unrecognizableFaseId           : unrecognizableFaseId,
         unrecognizableToepassingId     : unrecognizableToepassingId,
         unrecognizableMilieuCategorieId: unrecognizableMilieuCategorieId]
    }

    def manualKpiUpdate(){
    }
    def triggerManualKpiUpdate(){
        kpiReportService.sendWeeklyKPIReportJob(true)
        kpiReportService.sendWeeklyKPIReportForLicenseDetails(true)
        render([output: 'ok', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def indicatorUsagePaging () {
        String indicatorId = params.indicatorId
        Integer offset = params.int("offset") ?: PAGINATION_DEFAULT_OFFSET
        Integer limit = params.int("max") ?: PAGINATION_DEFAULT_LIMIT

        IndicatorUsageDTO indicatorUsageDTO = dataSummaryService.getIndicatorUsage(indicatorId, limit, offset)

        render( template: "indicatorUsageList",
                model:[projectIdAndChildren: indicatorUsageDTO.getProjectIdAndChildren(),
                       projectIdAndLicenses: indicatorUsageDTO.getProjectIdAndLicenses(),
                       entities: indicatorUsageDTO.getEntities(), totalIndicatorUsage: indicatorUsageDTO.getTotalIndicatorUsage()])
    }
}
