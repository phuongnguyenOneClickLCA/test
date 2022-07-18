package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.client.FindIterable
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
@Secured(["ROLE_SYSTEM_ADMIN", "ROLE_DEVELOPER"])
class DbCleanUpController extends ExceptionHandlerController {

    def entityService
    def queryService
    def indicatorService
    def optimiResourceService
    def unitConversionUtil
    def userService
    def scheduledJobsService
    def flashService

    @Autowired
    EmailConfiguration emailConfiguration

    def index() {
        Long countOfDeletedEntities = entityService.getCountOfDeletedEntities()
        Long countOfInactiveIndicators = indicatorService.getCountOfAllInactiveIndicators()
        Long countOfInactiveQueries = queryService.getCountOfAllInactiveQueries()

        [countOfDeletedEntities: countOfDeletedEntities,
         countOfInactiveIndicators: countOfInactiveIndicators,
         countOfInactiveQueries: countOfInactiveQueries]
    }

    def deletedEntitiesForCleanUp() {
        List<Document> deletedEntities = entityService.getDeletedEntities()
        render(template: "tabs/deletedEntities", model: [deletedEntities: deletedEntities])
    }

    def inactiveIndicatorsForCleanUp() {
        List<Document> inactiveIndicators = indicatorService.getAllInactiveIndicators()
        render(template: "tabs/inactiveIndicators", model: [inactiveIndicators: inactiveIndicators])
    }

    def inactiveQueriesForCleanUp() {
        List<Document> inactiveQueries = queryService.getAllInactiveQueries()
        render(template: "tabs/inactiveQueries", model: [inactiveQueries: inactiveQueries])
    }

    def nonUsedInactiveResourcesForCleanUp() {
        List<Document> nonUsedInactiveResources = optimiResourceService.getEntityNonUsedInactiveResourcesForDBCleanup()
        render(template: "tabs/nonUsedInactiveResources", model: [nonUsedInactiveResources: nonUsedInactiveResources])
    }

    def deprecatedResults() {
        scheduledJobsService.deprecatedResultsRemoveJob()
        flash.successAlert = "Deprecated results removed!"
        redirect(action: "index")
    }

    def corruptedData() {
        Map<Document, Map<Document, Map<String, Object>>> projectsEntitiesWithIncompatibleUnits = [:]
        [projectsEntitiesWithIncompatibleUnits: projectsEntitiesWithIncompatibleUnits]
    }

    def getCorruptedData() {
        FindIterable<Document> parentEntities = Entity.collection.find([deleted: false, parentEntityId: [$exists: false], childEntities: [$exists: true]], [_id: 1, name: 1])
        Map<Document, Map<Document, Map<String, Object>>> projectsEntitiesWithIncompatibleUnits = [:]

        if (parentEntities) {
            Integer entitiesSize = parentEntities.size()
            log.info("Corrupted data check: checking ${entitiesSize} entities.")
            Date currentDate = new Date()
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
            currentDate = dateFormat.parse(dateFormat.format(currentDate))
            Integer i = 1
            parentEntities.each { Document p ->
                FindIterable<Document> entities = Entity.collection.find([deleted: false, skipCorruptedDataCheck: [$ne: true], parentEntityId: p._id, datasets: [$exists: true]], [parentName: 1, name: 1, parentEntityId: 1, _id: 1, datasets: 1])
                Boolean hadCorruptedData = false
                Map<Document, Map<String, Object>> entitiesWithIncompatibleUnits = [:]
                log.info("Progress: ${i} / ${entitiesSize}, ${entities?.size()}")

                if (entities) {
                    entities.each { Document e ->
                        Boolean designHadCorruptedData = false
                        if (e.datasets) {
                            e.datasets.each { Document d ->
                                if (!d.noQueryRender && d.userGivenUnit && !d.uniqueConstructionIdentifier && d.resourceId && d.profileId) {
                                    Document r = Resource.collection.findOne([resourceId: d.resourceId, profileId: d.profileId, active: true], [resourceId: 1, nameEN:1, unitForData:1, combinedUnits: 1])

                                    if (r) {
                                        List<String> allowedUnits = []

                                        Map<String, List<String>> matchingUnits

                                        if (r && r.combinedUnits && r.unitForData) {
                                            r.combinedUnits.each { String allowedUnit ->

                                                def unitPairs = unitConversionUtil.allUnitPairs()

                                                Map pairs = unitPairs.find({ it.pairs.containsKey(allowedUnit) })?.pairs

                                                if (pairs) {
                                                    if (matchingUnits) {
                                                        List<String> existing = new ArrayList<String>(matchingUnits.get(r.unitForData.trim().toLowerCase()))

                                                        pairs.get(allowedUnit).each { String unit ->
                                                            if (!existing.contains(unit)) {
                                                                existing.add(unit)
                                                            }
                                                        }
                                                        matchingUnits.put(r.unitForData.trim().toLowerCase(), existing)
                                                    } else {
                                                        matchingUnits = new LinkedHashMap<String, List<String>>()
                                                        matchingUnits.put(r.unitForData.trim().toLowerCase(), pairs.get(allowedUnit))
                                                    }
                                                }
                                            }
                                        }

                                        if (r.combinedUnits) {
                                            allowedUnits.addAll((List<String>) r.combinedUnits)
                                        }
                                        if (matchingUnits) {
                                            allowedUnits.addAll(matchingUnits.get(r.unitForData.trim().toLowerCase()))
                                        }
                                        allowedUnits = allowedUnits.unique()

                                        String thicknessin = d.additionalQuestionAnswers.get("thickness_in")
                                        String thicknessmm = d.additionalQuestionAnswers.get("thickness_mm")
                                        Double thickness_in
                                        Double thickness_mm
                                        Boolean failingThicknessMatch = Boolean.FALSE

                                        if (thicknessin && thicknessmm) {
                                            thickness_in = DomainObjectUtil.isNumericValue(thicknessin) ? DomainObjectUtil.convertStringToDouble(thicknessin) : null
                                            thickness_mm = DomainObjectUtil.isNumericValue(thicknessmm) ? DomainObjectUtil.convertStringToDouble(thicknessmm) : null

                                            if (thickness_in != null && thickness_mm != null) {
                                                Double min = thickness_mm * 0.95
                                                Double max = thickness_mm * 1.05
                                                Double inchesInMM = thickness_in * 25.4

                                                if (inchesInMM < min || inchesInMM > max) {
                                                    failingThicknessMatch = Boolean.TRUE
                                                }
                                            } else {
                                                log.error("CORRUPTED DATA: Entity: ${e.parentName} / ${e.name}: Could not parse dataset thickness to double: thickness_mm: ${thicknessmm}, thickness_in: ${thicknessin}")
                                                flashService.setWarningAlert("CORRUPTED DATA: Entity: ${e.parentName} / ${e.name}: Could not parse dataset thickness to double: thickness_mm: ${thicknessmm}, thickness_in: ${thicknessin}", true)
                                            }
                                        }

                                        if ((allowedUnits && !allowedUnits*.toLowerCase().contains(d.userGivenUnit.toLowerCase()))||failingThicknessMatch) {
                                            Map m = entitiesWithIncompatibleUnits.get(e)
                                            if(!m){
                                                entitiesWithIncompatibleUnits.put(e, [:])
                                            }
                                            List<String> existing = m?.get("dataset")
                                            List<String> existingThickness = m?.get("thickness")

                                            if (failingThicknessMatch) {
                                                if (existingThickness) {
                                                    existingThickness.add("${r.nameEN.toString()} (${r.resourceId}), Thickness MM (<b>${thickness_mm}</b>) does not match Thickness IN (<b>${thickness_in}</b>) with 5% margin")
                                                } else {
                                                    existingThickness = ["${r.nameEN.toString()} (${r.resourceId}), Thickness MM (<b>${thickness_mm}</b>) does not match Thickness IN (<b>${thickness_in}</b>) with 5% margin"]
                                                }
                                            }
                                            if ((allowedUnits && !allowedUnits*.toLowerCase().contains(d.userGivenUnit.toLowerCase()))) {
                                                if (existing) {
                                                    existing.add("${r.nameEN.toString()} (${r.resourceId}), saved in: <b>${d.userGivenUnit.toLowerCase()}</b>, allowed are: <b>${allowedUnits}</b>")
                                                } else {
                                                    existing = ["${r.nameEN.toString()} (${r.resourceId}), saved in: <b>${d.userGivenUnit.toLowerCase()}</b>, allowed are: <b>${allowedUnits}</b>"]
                                                }
                                            }
                                            hadCorruptedData = true
                                            designHadCorruptedData = true
                                            entitiesWithIncompatibleUnits.get(e)?.put("dataset",existing)
                                            entitiesWithIncompatibleUnits.get(e)?.put("thickness",existingThickness)
                                        }
                                    }
                                }
                            }
                        }

                        if (!designHadCorruptedData) {
                            Entity.collection.updateOne([_id: e._id], [$set: [skipCorruptedDataCheck: true]])
                        }
                    }
                }

                if (hadCorruptedData) {
                    List<Document> licenses = License.collection.find([licensedEntityIds: [$in: [p._id.toString(), p._id]]])?.toList()
                    if (licenses) {
                        Iterator<Document> iterator = licenses.iterator()

                        while (iterator.hasNext()) {
                            Document l = iterator.next()
                            Boolean valid = Boolean.FALSE

                            if (!l.frozen) {
                                if (l.validFrom && l.validUntil && l.validFrom <= currentDate && l.validUntil > currentDate) {
                                    valid = Boolean.TRUE
                                }
                            }
                            l.put("valid", valid)
                        }
                        if (entitiesWithIncompatibleUnits) {
                            entitiesWithIncompatibleUnits?.each {
                                if (it) {
                                    it.value?.put("licenseList", licenses)
                                }
                            }
                        }

                    }
                    projectsEntitiesWithIncompatibleUnits.put(p, entitiesWithIncompatibleUnits)
                }
                i++
            }
        }
        //redirect action: "corruptedData", params: [projectsEntitiesWithIncompatibleUnits: projectsEntitiesWithIncompatibleUnits]
        String htmlContent = g.render([template: "/dbCleanUp/corruptedDataTables", model: [projectsEntitiesWithIncompatibleUnits: projectsEntitiesWithIncompatibleUnits]])
        Map responseData = [htmlContent: htmlContent, (flashService.FLASH_OBJ_FOR_AJAX): flash]
        render(responseData as JSON)
    }

    def removeInactiveResources() {
        if (params.id) {
            List<String> ids = params.list("id")

            ids.each { String id ->
                optimiResourceService.deleteResource(id)
            }
            flash.fadeSuccessAlert = "Resources deleted succesfully!"
        }
        redirect(action: "index")
    }

    def removeDatasetImportFields() {
        if (params.id) {
            List<String> ids = params.list("id")

            ids.each { String id ->
                DatasetImportFields.collection.remove(["_id": DomainObjectUtil.stringToObjectId(id)])
            }
            flash.fadeSuccessAlert = "DatasetImportFields deleted succesfully!"
        }
        redirect(action: "index")
    }

    def removeDeletedEntities() {
        if (params.id) {
            List<String> entityIds = params.list("id")

            entityIds.each { String entityId ->
                entityService.deleteEntityPermanently(entityId)
            }
            flash.fadeSuccessAlert = "Entities deleted succesfully!"
        }
        redirect(action: "index")
    }

    def removeInactiveIndicators() {
        if (params.id) {
            List<String> ids = params.list("id")

            ids.each { String id ->
                indicatorService.deleteIndicatorById(id)
            }
            flash.fadeSuccessAlert = "Indicators deleted succesfully!"
        }
        redirect(action: "index")
    }

    def removeInactiveQueries() {
        if (params.id) {
            List<String> ids = params.list("id")

            ids.each { String id ->
                queryService.deleteQueryById(id)
            }
            flash.fadeSuccessAlert = "Queries deleted succesfully!"
        }
        redirect(action: "index")
    }
}
