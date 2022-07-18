package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.domain.mongo.Indicator
import com.gmongo.GMongo
import com.mongodb.BasicDBObject
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.DBObject

import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class DataMigrationController extends ExceptionHandlerController {

    def entityService
    def configurationService
    def flashService
    def optimiResourceService
    def newCalculationServiceProxy
    def loggerUtil

    def index() {
        List<String> domainClasses = ["Indicator", "Query"]
        List<String> parentEntityClasses = optimiResourceService.getParentEntityClassIds()
        Integer entityCount

        if (parentEntityClasses && !parentEntityClasses.isEmpty()) {
            entityCount = entityService.getAllEntitiesCount(parentEntityClasses, false) ?: 0
        }
        String resourceId = params.resourceId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String indicatorId = params.indicatorId
        String resultCategoryId = params.resultCategoryId
        String calculationRuleId = params.calculationRuleId
        String denominatorId = params.denominatorId
        Boolean migrationAllowed = new Boolean(params.migrationAllowed)
        def entities = session?.getAttribute("entities")
        session?.removeAttribute("entities")
        def indicators = session?.getAttribute("indicators")
        session?.removeAttribute("indicators")
        [domainClasses: domainClasses, entityCount: entityCount, resourceId: resourceId, queryId: queryId,
         sectionId: sectionId, indicatorId: indicatorId, entities: entities, migrationAllowed: migrationAllowed,
         resultCategoryId: resultCategoryId, calculationRuleId: calculationRuleId, denominatorId: denominatorId,
         indicators: indicators]
    }

    def search() {
        def indicators
        def entities
        String queryId = params.queryId
        String sectionId = params.sectionId
        String resourceId = params.resourceId
        String indicatorId = params.indicatorId
        String resultCategoryId = params.resultCategoryId
        String calculationRuleId = params.calculationRuleId
        String denominatorId = params.denominatorId

        if (resourceId) {
            entities = entityService.getEntitiesByResourceId(resourceId)
        } else if (indicatorId) {
            entities = entityService.getEntitiesWithResultByIndicatorId(indicatorId)

            if (params.recalcDone) {
                flash.fadeSuccessAlert = "Re-calculation done"
            }
        } else if (resultCategoryId) {
            indicators = Indicator.collection.find([applicationCategories:[$exists:true]],[indicatorId: 1, applicationCategories: 1, active: 1, importFile: 1])?.toList()?.findAll({ it.get("applicationCategories")
                    .find({ String key, List<String> categoryIds -> categoryIds?.contains(resultCategoryId)})
            })
        } else if (calculationRuleId) {
            indicators = Indicator.collection.find([applicationRules:[$exists:true]],[indicatorId: 1, applicationRules: 1, active: 1, importFile: 1])?.toList()?.findAll({ it.get("applicationRules")
                    .find({ String key, List<String> categoryIds -> categoryIds?.contains(calculationRuleId)})
            })
        } else if (denominatorId) {
            indicators = Indicator.collection.find([applicationDenominators:[$exists:true]],[indicatorId: 1, applicationDenominators: 1, active: 1, importFile: 1])?.toList()?.findAll({ it.get("applicationDenominators")
                    .find({ String key, List<String> categoryIds -> categoryIds?.contains(denominatorId) })
            })
        } else {
            entities = entityService.getEntitiesByQueryIdAndSectionId(queryId, sectionId)
        }
        session?.setAttribute("entities", entities)
        session?.setAttribute("indicators", indicators)
        redirect action: "index", params: [resourceId: resourceId, queryId: queryId, sectionId: sectionId,
                                           indicatorId: indicatorId, migrationAllowed: isMigrationAllowed(),
                                           resultCategoryId: resultCategoryId, calculationRuleId: calculationRuleId, denominatorId: denominatorId,]
    }

    def searchDeprecatedAttributes() {
        String domainClass = params.domainClass
        String attribute = params.attribute
        def results = []

        if (domainClass && attribute) {
            try {
                domainClass = domainClass.trim().toLowerCase()
                attribute = attribute.trim()
                def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
                def mongo = new GMongo()
                def db = mongo.getDB(dbName)
                DBCollection dbCollection = db.getCollection(domainClass)

                if (dbCollection) {
                    DBObject query = new BasicDBObject()
                    DBObject attributesProjection

                    if ("query".equals(domainClass)) {
                        attributesProjection = new BasicDBObject(["queryId": 1])
                    } else if ("indicator".equals(domainClass)) {
                        attributesProjection = new BasicDBObject(["indicatorId": 1])
                    }

                    query.append(attribute, new BasicDBObject("\$ne", null))
                    query.append("active", Boolean.TRUE)
                    DBCursor mongoCursor

                    if (attributesProjection) {
                        mongoCursor = dbCollection.find(query, attributesProjection)
                    } else {
                        mongoCursor = dbCollection.find(query)
                    }

                    while (mongoCursor?.hasNext()) {
                        DBObject dbObject = mongoCursor.next()
                        results.add(dbObject.toString())
                    }
                }
            } catch (Exception e) {
                loggerUtil.info(log,"Exception in searchDeprecatedAttributes", e)
                flashService.setErrorAlert("Error in searchDeprecatedAttributes: ${e.message}", true)
            }
        }

        if (!results) {
            results.add("<strong>No results found</strong>")
        }
        chain(action: "index", model: [results: results, domainClass: domainClass, attribute: attribute])
    }

    def doMigration() {
        if (params.relCalculate && params.indicatorId) {
            def indicatorId = params.indicatorId
            def entityIds = params.list("entityId")

            entityIds?.each { entityId ->
                newCalculationServiceProxy.calculate(entityId, indicatorId, null)
            }
            redirect(action: "search", params: [indicatorId: indicatorId, recalcDone: true])
        } else {
            def queryId = params.queryId
            def sectionId = params.sectionId
            def newQueryId = params.newQueryId
            def newSectionId = params.newSectionId
            def entityIds = params.list("entityId")
            boolean migrationOk = entityService.doDatasetMigration(queryId, sectionId, newQueryId, newSectionId, entityIds)

            if (migrationOk) {
                flash.fadeSuccessAlert = "Migration successful"
            } else {
                flash.fadeErrorAlert = "Error in migration"
            }
            redirect(action: "index", params: [queryId: newQueryId, sectionId: newSectionId ? newSectionId : sectionId])
        }
    }

    private boolean isMigrationAllowed() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        boolean allowed = false
        String dataMigrationAllowedDate = configurationService.getConfigurationValue(Constants.APPLICATION_ID, Constants.ConfigName.DATA_MIGRATION_ALLOWED.toString())

        if (dataMigrationAllowedDate && dataMigrationAllowedDate.equals(dateFormat.format(new Date()))) {
            allowed = true
        }
        return allowed
    }
}
