/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as Const
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.Constants.EntityUserType
import com.bionova.optimi.core.Constants.LicenseType
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.BenchmarkValue
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.ChildEntity
import com.bionova.optimi.core.domain.mongo.CopyFromQuestion
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.LcaChecker
import com.bionova.optimi.core.domain.mongo.LcaCheckerResult
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.MapEntity
import com.bionova.optimi.core.domain.mongo.MapUserUpdate
import com.bionova.optimi.core.domain.mongo.Note
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.PreProvisionedUserRight
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryLastUpdateInfo
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.Task
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.JSONUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.gmongo.GMongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBObject
import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.util.Holders
import grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.codec.binary.Base64
import org.apache.commons.collections4.CollectionUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.grails.datastore.mapping.query.api.Criteria
import org.grails.web.util.WebUtils
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.HtmlUtils

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 *
 */
class EntityService extends GormCleanerService {

    GrailsApplication grailsApplication
    def queryService
    def indicatorService
    def userService
    def loggerUtil
    def configurationService
    def optimiSecurityService
    def optimiResourceService
    def channelFeatureService
    def errorMessageUtil
    def newCalculationServiceProxy
    def domainClassService
    def simulationToolService
    def featureService
    def accountService
    def accountImagesService
    def flashService
    def stringUtilsService
    def licenseService
    def datasetService
    def optimiMailService
    def childEntityService
    def indicatorQueryService
    def resourceService
    def resourceFilterCriteriaUtil
    def messageSource
    ScopeFactoryService scopeFactoryService
    QuestionService questionService

    static transactional = "mongo"
    private static ObjectMapper mapper = new ObjectMapper();

    def getCalculationResultsForEntity(Entity entity, String indicatorId) {
        List<CalculationResult> calculationResults

        if (entity && indicatorId) {
            try {
                calculationResults = (Entity.collection.find(["_id": entity.id, "calculationResults.indicatorId": indicatorId],
                        ["calculationResults": 1]) as Entity)?.calculationResults?.
                        findAll({ indicatorId.equals(it.indicatorId) })
            } catch (Exception e) {

            }
        }
        return calculationResults
    }

    def copyFiles(Entity sourceEntity, Entity targetEntity) {
        if (sourceEntity && targetEntity) {
            List<String> filePersistentProperties = domainClassService.getPersistentPropertyNamesForDomainClass(EntityFile.class)?.findAll({
                !"entityId".equals(it)
            })

            sourceEntity.getFiles()?.each { EntityFile entityFile ->
                EntityFile copyFile = new EntityFile()
                copyFile.properties = entityFile.properties.getProperties().subMap(filePersistentProperties)
                copyFile.entityId = targetEntity.id.toString()
                copyFile.save(flush: true)
            }
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getDeletedEntities() {
        return Entity.collection.find(getDeletedEntriesQuery(), ["_id": 1, "name": 1, "entityClass": 1])?.toList()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Long getCountOfDeletedEntities() {
        return Entity.collection.count(getDeletedEntriesQuery())
    }

    private Map getDeletedEntriesQuery() {
        ["deleted": true, "entityClass": [$in: optimiResourceService.getParentEntityClassIds(Boolean.TRUE)]]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteEntityPermanently(String entityId) {
        boolean deleteOk = false

        if (entityId) {
            Entity entity = getEntityForShowing(entityId)

            if (entity) {
                List<Entity> childEntities = getChildEntitiesForShowing(entity, null, null, Boolean.TRUE)

                if (childEntities) {
                    childEntities.each { Entity child ->
                        child.targetedTasks?.each { Task task ->
                            task.delete(flush: true)
                        }
                        Entity.collection.remove(["_id": child.id])
                        CalculationResult.collection.remove([entityId: child.id.toString()])
                    }
                }

                entity.targetedTasks?.each { Task task ->
                    task.delete(flush: true)
                }

                entity.getNotes()?.each { Note note ->
                    note.delete(flush: true)
                }
                List<EntityFile> entityFiles = getEntityFilesByType(entity.id.toString(), null)

                entityFiles?.each { EntityFile entityFile ->
                    entityFile.delete(flush: true)
                }
                Entity.collection.remove(["_id": entity.id])
                deleteOk = true
            }
        }
        return deleteOk
    }

    def deletePermanentlyManagedEntities(User user) {
        def deletionOk = false

        if (user.testRobot) {
            List<Entity> managedEntities = userService.getManagedEntities(user?.managedEntityIds)

            if (managedEntities) {
                def entityIds = managedEntities.collect({ it.id.toString() })
                user.managedEntityIds?.removeAll(entityIds)

                managedEntities.each { Entity entity ->
                    if (entity.childEntities) {
                        entity.getChildrenByChildEntities()?.each { Entity childEntity ->
                            childEntity.targetedTasks?.each { Task task ->
                                task.delete(flush: true)
                            }
                            childEntity.delete()
                        }
                    }

                    entity.targetedTasks?.each { Task task ->
                        task.delete(flush: true)
                    }
                    entity.delete()
                }
            }
            deletionOk = true
        }
        return deletionOk
    }
    /**
     * number of designs should be greater than 1 and
     * license feature should be available for the average creation functionality to work
     *
     * @param designsSize
     * @param parentEntity
     * @return
     */
    boolean checkIfAverageDesignFeatureEnabled(Integer designsSize, Entity parentEntity) {
        if (!designsSize || !parentEntity) {
            return Boolean.FALSE
        } else if (designsSize < com.bionova.optimi.core.Constants.MINIMUM_NO_OF_DESIGNS_FOR_AVERAGING) {
            return Boolean.FALSE
        } else {
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.ALLOW_AVERAGE_DESIGN_CREATION])
            return featuresAllowed.get(Feature.ALLOW_AVERAGE_DESIGN_CREATION)
        }
    }

    boolean deletePermanentlyCreatedEntities(User user, boolean hasDebugLicense) {
        boolean deletionOk = false

        if (user.testRobot || hasDebugLicense) {
            List<Entity> entities = Entity.findAllByCreatorId(user.id)

            if (entities) {
                def entityIds = entities.collect({ it.id })
                user.managedEntityIds?.removeAll(entityIds)

                entities.each { Entity entity ->
                    if (entity.childEntities) {
                        entity.getChildrenByChildEntities()?.each { Entity childEntity ->
                            childEntity.targetedTasks?.each { Task task ->
                                task.delete(flush: true)
                            }
                            childEntity.delete()
                        }
                    }

                    entity.targetedTasks?.each { Task task ->
                        if (task) {
                            task.delete(flush: true)
                        }
                    }
                    List<License> licenses = entity.licenses

                    if (licenses) {
                        LicenseService licenseService = Holders.getApplicationContext().getBean("licenseService")

                        licenses.each { License license ->
                            licenseService.removeEntity(license.id, entity.id.toString(), Boolean.TRUE)
                        }
                    }
                    entity.delete()
                }
            }
            deletionOk = true
        }
        return deletionOk
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def doDatasetMigration(String queryId, String sectionId, String newQueryId, String newSectionId, List entityIds) {
        boolean migrationOk = false

        if (queryId && newQueryId && entityIds) {
            def entities = Entity.getAll(DomainObjectUtil.stringsToObjectIds(entityIds))

            if (sectionId && newSectionId) {
                for (Entity entity in entities) {
                    def datasetsToMigrate = entity.datasets?.findAll({
                        queryId.equals(it.queryId) && sectionId.equals(it.sectionId)
                    })

                    if (datasetsToMigrate) {
                        datasetsToMigrate.each { Dataset dataset ->
                            dataset.queryId = newQueryId
                            dataset.sectionId = newSectionId
                        }
                        entity.merge(flush: true)
                        migrationOk = true
                    }
                }
            } else {
                for (Entity entity in entities) {
                    def datasetsToMigrate = entity.datasets?.findAll({ queryId.equals(it.queryId) })

                    if (datasetsToMigrate) {
                        datasetsToMigrate.each { Dataset dataset ->
                            dataset.queryId = newQueryId
                        }
                        entity.merge(flush: true)
                        migrationOk = true
                    }
                }
            }
        }
        return migrationOk
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntitiesByQueryIdAndSectionId(String queryId, String sectionId) {
        Map entities = [:]

        if (queryId) {
            List<String> questionIds

            List<Entity> foundEntities
            BuildableCriteria criteria = Entity.createCriteria()

            if (sectionId) {
                foundEntities = criteria.list {
                    datasets {
                        eq('sectionId', sectionId)
                        and {
                            eq('queryId', queryId)
                        }
                    }
                }
            } else {
                foundEntities = criteria.list {
                    datasets {
                        eq('queryId', queryId)
                    }
                }
            }

            if (foundEntities) {
                for (Entity entity in foundEntities) {
                    Entity parent

                    if (entity.parentEntityId) {
                        parent = entity.getParentById()
                    }
                    def datasetAmount

                    if (questionIds && !questionIds.isEmpty()) {
                        datasetAmount = entity.datasets.findAll({ questionIds.contains(it.questionId) })?.size()
                    } else {
                        if (sectionId) {
                            datasetAmount = entity.datasets.findAll({
                                queryId.equals(it.queryId) && sectionId.equals(it.sectionId)
                            })?.size()
                        } else {
                            datasetAmount = entity.datasets.findAll({ queryId.equals(it.queryId) })?.size()
                        }
                    }

                    if (parent) {
                        if (!entities.keySet().contains(parent.name)) {
                            entities.put(parent.name, [(entity): datasetAmount])
                        } else {
                            Map existing = entities.get(parent.name)
                            existing.put(entity, datasetAmount)
                            entities.put(parent.name, existing)
                        }
                    } else {
                        if (!entities.keySet().contains(entity.name)) {
                            entities.put(entity.name, [(entity): datasetAmount])
                        } else {
                            Map existing = entities.get(entity.name)
                            existing.put(entity, datasetAmount)
                            entities.put(entity.name, existing)
                        }
                    }
                }
            }
        }
        return entities
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntitiesByResourceId(String resourceId) {
        Map entities = [:]

        if (resourceId) {
            def criteria = Entity.createCriteria()
            List<Entity> foundEntities = criteria.list {
                datasets {
                    eq('resourceId', resourceId)
                }
            }

            if (foundEntities) {
                for (Entity entity in foundEntities) {
                    Entity parent

                    if (entity.parentEntityId) {
                        parent = entity.getParentById()
                    }
                    def datasetAmount = entity.datasets.findAll({ resourceId.equals(it.resourceId) })?.size()

                    if (parent) {
                        if (!entities.keySet().contains(parent.name)) {
                            entities.put(parent.name, [(entity): datasetAmount])
                        } else {
                            Map existing = entities.get(parent.name)
                            existing.put(entity, datasetAmount)
                            entities.put(parent.name, existing)
                        }
                    }
                }
            }
        }
        return entities
    }

    def getUserEntitiesByClassesAndIndicatorIds(List<String> entityClasses, List<String> indicatorIds) {
        List<Entity> entities

        if (entityClasses && indicatorIds) {
            User user = userService.getCurrentUser(Boolean.TRUE)
            def results = Entity.collection.find([entityClass: [$in: entityClasses], indicatorIds: [$in: indicatorIds], managerIds: [$in: [user.id.toString()]]])

            if (results) {
                entities = results.collect({ it as Entity })
            }
        }
        return entities
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllEntities(List<String> entityClasses, boolean alsoDeleted) {
        def entities

        if (entityClasses) {
            entities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, alsoDeleted)
        }
        return entities
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllChildEntitiesAsDocuments() {
        return Entity.collection.find([deleted: false, parentEntityId: [$exists: true], datasets: [$exists: true]], [parentEntityId: 1, _id: 1, datasets: 1])?.toList()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getChoosableEntities(List<String> entityClasses, List<String> licensedEntityIds, Boolean alsoDeleted) {
        List<Document> entities

        if (entityClasses) {
            if (licensedEntityIds) {
                entities = Entity.collection.find([_id: [$nin: DomainObjectUtil.stringsToObjectIds(licensedEntityIds.unique())], entityClass: [$in: entityClasses], deleted: alsoDeleted], [name: 1, _id: 1])?.toList()
            } else {
                entities = Entity.collection.find([entityClass: [$in: entityClasses], deleted: alsoDeleted], [name: 1, _id: 1])?.toList()
            }
        }
        return entities
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllEntitiesCount(List<String> entityClasses, boolean alsoDeleted) {
        Integer entities

        if (entityClasses) {
            entities = Entity.collection.count([entityClass: [$in: entityClasses], deleted: alsoDeleted])?.intValue()
        }
        return entities
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getPreviousOperatingPeriods(String parentEntityId, String operatingPeriodId) {
        List<Entity> previousOperatingPeriods

        if (parentEntityId && operatingPeriodId) {
            Entity parentEntity = Entity.get(parentEntityId)
            Entity operatingPeriod = Entity.get(operatingPeriodId)

            if (parentEntity && operatingPeriod.operatingPeriod) {
                Integer currentPeriod = operatingPeriod.operatingPeriod.toInteger()
                previousOperatingPeriods = parentEntity.getOperatingPeriods()?.findAll({ it.operatingPeriod.toInteger() < currentPeriod })?.sort({ it.operatingPeriod })
            }
        }
        return previousOperatingPeriods
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    private List<String> getAllowedParentEntityIds() {
        def entityIds = []
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user) {
            user.readonlyEntityIds?.each { entityId ->
                if (!entityIds.contains(entityId)) {
                    entityIds.add(entityId)
                }
            }

            user.modifiableEntityIds?.each { entityId ->
                if (!entityIds.contains(entityId)) {
                    entityIds.add(entityId)
                }
            }

            user.managedEntityIds?.each { entityId ->
                if (!entityIds.contains(entityId)) {
                    entityIds.add(entityId)
                }
            }

            user.taskEntityIds?.each { entityId ->
                if (!entityIds.contains(entityId)) {
                    entityIds.add(entityId)
                }
            }
        }
        return entityIds
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getModifiableEntities(List<String> entityClasses) {
        def foundEntities = []

        if (entityClasses) {
            User user = userService.getCurrentUser(Boolean.TRUE)

            if (userService.getSuperUser(user)) {
                foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false)
            } else {
                def entityIds = user?.managedEntityIds
                def modifiableEntityIds = user?.modifiableEntityIds

                if (modifiableEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(modifiableEntityIds)
                    } else {
                        entityIds = modifiableEntityIds
                    }
                }

                def readonlyEntityIds = user?.readonlyEntityIds

                if (readonlyEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(readonlyEntityIds)
                    } else {
                        entityIds = readonlyEntityIds
                    }
                }

                if (entityIds) {
                    entityIds = entityIds.unique()
                    List<Entity> entities = Entity.getAll(DomainObjectUtil.stringsToObjectIds(entityIds))
                    foundEntities = entities?.findAll({ Entity entity -> entity != null && entityClasses.contains(entity.entityClass) && !entity.deleted })
                }
            }
        }
        return foundEntities
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getModifiableEntitiesForPortfolio(List<String> entityClasses) {
        List<Document> foundEntities = []

        if (entityClasses) {
            User user = userService.getCurrentUser(Boolean.TRUE)

            if (userService.getSuperUser(user)) {
                foundEntities = Entity.collection.find([entityClass: [$in: entityClasses], deleted: false], [name: 1, indicatorIds: 1])?.toList()
            } else {
                def entityIds = user?.managedEntityIds
                def modifiableEntityIds = user?.modifiableEntityIds

                if (modifiableEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(modifiableEntityIds)
                    } else {
                        entityIds = modifiableEntityIds
                    }
                }

                def readonlyEntityIds = user?.readonlyEntityIds

                if (readonlyEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(readonlyEntityIds)
                    } else {
                        entityIds = readonlyEntityIds
                    }
                }

                if (entityIds) {
                    entityIds = entityIds.unique()
                    foundEntities = Entity.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(entityIds)], entityClass: [$in: entityClasses], deleted: false], [name: 1, indicatorIds: 1])?.toList()
                }
            }
        }
        return foundEntities
    }

    def isEntityOkForLicenseType(String id) {
        Boolean ok = Boolean.FALSE

        if (id) {
            def currentDate = new Date()
            Integer validLicenses = License.collection.count([licensedEntityIds: [$in: [id]], validFrom: [$lte: currentDate], validUntil: [$gte: currentDate]])?.intValue()

            if (validLicenses > 0) {
                ok = Boolean.TRUE
            }
        }
        return ok
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getModifiableEntitiesForImportMapper(ImportMapper importMapper) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<Entity> foundEntities = []

        if (user && importMapper) {
            List<String> importFeatures = featureService.getImportFeatures(importMapper)?.collect({ it.featureId })
            List<String> entityClasses = importMapper.compatibleEntityClasses

            def importableEntityIds = []
            def manageableEntityIds = user.managedEntityIds

            if (manageableEntityIds) {
                importableEntityIds.addAll(manageableEntityIds)
            }
            def modifiableEntityIds = user.modifiableEntityIds

            if (modifiableEntityIds) {
                importableEntityIds.addAll(modifiableEntityIds)
            }
            if (!importableEntityIds.isEmpty()) {
                List<Entity> projects = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(importableEntityIds)], "entityClass": [$in: entityClasses], "deleted": false])?.collect({ it as Entity })
                if (projects) {
                    for (Entity project in projects) {
                        if (licenseService.getValidLicensesForEntity(project)?.find({ it.licensedFeatures?.find({ importFeatures.contains(it.featureId) }) })) {
                            boolean isPrivilegeUser = user.internalUseRoles
                            if (!isPrivilegeUser && !project?.getPublic()) {
                                foundEntities.add(project)
                            } else {
                                foundEntities.add(project)
                            }
                        }
                    }
                }
            }
        }
        return foundEntities
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getModifiableAndPublicEntities(List<String> entityClasses, Integer offset = null, Integer limit = null, HttpSession session = null, Boolean superUserSearchEnabled) {
        Boolean allFound = Boolean.FALSE
        def foundEntities = []

        Entity.withStatelessSession {
            if (entityClasses) {
                User user = userService.getCurrentUser(Boolean.TRUE)

                if (userService.getSuperUser(user) && superUserSearchEnabled) {
                    if (limit) {
                        foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [max: limit, sort: 'name'])

                        if (foundEntities.size() < limit) {
                            allFound = Boolean.TRUE
                        }
                    } else if (offset) {
                        foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [offset: offset, sort: 'name'])
                        allFound = Boolean.TRUE
                    } else {
                        foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [sort: 'name'])
                        allFound = Boolean.TRUE
                    }
                } else {
                    def entityIds = user?.managedEntityIds
                    def modifiableEntityIds = user?.modifiableEntityIds

                    if (modifiableEntityIds) {
                        if (entityIds) {
                            entityIds.addAll(modifiableEntityIds)
                        } else {
                            entityIds = modifiableEntityIds
                        }
                    }
                    def readonlyEntityIds = user?.readonlyEntityIds

                    if (readonlyEntityIds) {
                        if (entityIds) {
                            entityIds.addAll(readonlyEntityIds)
                        } else {
                            entityIds = readonlyEntityIds
                        }
                    }

                    if (session) {
                        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

                        if (channelFeature && !channelFeature.hidePublic) {
                            List<Entity> publishedEntities = Entity.findAllByPublishedIsNotNull()

                            if (publishedEntities) {
                                def publicEntityIds = publishedEntities.findAll({ Entity e -> e.published.get(channelFeature.token) })?.collect({
                                    it.id
                                })

                                if (publicEntityIds) {
                                    if (entityIds) {
                                        entityIds.removeAll(publicEntityIds)
                                        entityIds.addAll(publicEntityIds)
                                    } else {
                                        entityIds = publicEntityIds
                                    }
                                }
                            }
                        }
                    }

                    if (entityIds) {
                        entityIds = entityIds.sort({ it.toString() }).unique()
                        /*
                        if (limit) {
                            foundEntities = Entity.findAllByIdInListAndEntityClassInListAndDeleted(DomainObjectUtil.stringsToObjectIds(entityIds), entityClasses, false, [max: limit, sort: 'name'])

                            if (foundEntities?.size() < limit) {
                                allFound = Boolean.TRUE
                            }
                        } else if (offset) {
                            foundEntities = Entity.findAllByIdInListAndEntityClassInListAndDeleted(DomainObjectUtil.stringsToObjectIds(entityIds), entityClasses, false, [offset: offset, sort: 'name'])
                            allFound = Boolean.TRUE
                        } else {
                            foundEntities = Entity.findAllByIdInListAndEntityClassInListAndDeleted(DomainObjectUtil.stringsToObjectIds(entityIds), entityClasses, false, [sort: 'name'])
                            allFound = Boolean.TRUE
                        }
                         */
                        foundEntities = Entity.findAllByIdInListAndEntityClassInListAndDeleted(DomainObjectUtil.stringsToObjectIds(entityIds), entityClasses, false, [sort: 'name'])
                        allFound = Boolean.TRUE
                    }
                }
            }
        }
        return [(allFound): foundEntities]
    }

    def getEntityForShowing(String entityId, Map customProjection = null) {
        Entity entity

        try {
            if (entityId) {
                Map projectionMap = customProjection ?: [datasets      : 1, hasImage: 1, entityClass: 1, name: 1, managerIds: 1,
                                                         modifierIds   : 1, readonlyUserIds: 1, childEntities: 1, published: 1,
                                                         commited      : 1, deleted: 1, master: 1, targetedTaskIds: 1, indicatorIds: 1,
                                                         noteIds       : 1, benchmarkIds: 1, operatingPeriod: 1, defaultValueSets: 1,
                                                         userIndicators: 1, dateCreated: 1, ribaStage: 1, defaults: 1]
                DBObject searchById = new BasicDBObject("_id", new ObjectId(entityId))
                DBObject projection = new BasicDBObject(projectionMap)
                entity = Entity.collection.findOne(searchById, projection) as Entity
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in getting entity", e)
            flashService.setErrorAlert("Error in getting entity: ${e.getMessage()}", true)
        }
        return entity
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getEntityMainList(List<String> entityClasses, Integer offset = null, Integer limit = null, HttpSession session = null, Boolean superUserSearchEnabled) {
        Boolean allFound = Boolean.FALSE
        List<Entity> foundEntities
        def projection = [datasets: 1, hasImage: 1, entityClass: 1, name: 1, managerIds: 1, modifierIds: 1, readonlyUserIds: 1, childEntities: 1, published: 1, indicatorIds: 1, archived: 1]
        if (entityClasses) {
            User user = userService.getCurrentUser(Boolean.TRUE)

            if (userService.getSuperUser(user) && superUserSearchEnabled) {
                if (limit) {
                    // foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [max: limit, sort: 'name'])
                    foundEntities = Entity.collection.find(["entityClass": [$in: entityClasses], "deleted": [$ne: true]], projection)?.limit(limit)?.collect({ it as Entity })

                    if (foundEntities?.size() < limit) {
                        allFound = Boolean.TRUE
                    }
                } else if (offset) {
                    // foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [offset: offset, sort: 'name'])

                    foundEntities = Entity.collection.find(["entityClass": [$in: entityClasses], "deleted": [$ne: true]], projection)?.skip(offset)?.collect({ it as Entity })
                    allFound = Boolean.TRUE
                } else {
                    // foundEntities = Entity.findAllByEntityClassInListAndDeleted(entityClasses, false, [sort: 'name'])

                    foundEntities = Entity.collection.find(["entityClass": [$in: entityClasses], "deleted": [$ne: true]], projection)?.collect({ it as Entity })
                    allFound = Boolean.TRUE
                }
            } else {
                def entityIds = user?.managedEntityIds
                def modifiableEntityIds = user?.modifiableEntityIds

                if (modifiableEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(modifiableEntityIds)
                    } else {
                        entityIds = modifiableEntityIds
                    }
                }
                def readonlyEntityIds = user?.readonlyEntityIds

                if (readonlyEntityIds) {
                    if (entityIds) {
                        entityIds.addAll(readonlyEntityIds)
                    } else {
                        entityIds = readonlyEntityIds
                    }
                }

                if (session) {
                    ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

                    if (channelFeature && !channelFeature.hidePublic) {
                        BasicDBObject query = new BasicDBObject()
                        String published = "published.${channelFeature.token}"
                        query.put("parentEntityId", null)
                        query.put((published), true)

                        List<ObjectId> ids = Entity.collection.distinct("_id", query, ObjectId.class)?.toList()

                        if (ids) {
                            if (entityIds) {
                                entityIds.addAll(ids)
                            } else {
                                entityIds = ids
                            }
                        }
                    }
                }

                if (entityIds) {
                    entityIds = entityIds.unique({ it.toString() })
                    foundEntities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(entityIds)], "entityClass": [$in: entityClasses], "deleted": [$ne: true]], projection)?.collect({ it as Entity })
                    allFound = Boolean.TRUE
                }
            }
        }
        if (foundEntities) {
            foundEntities = foundEntities.sort({ Entity e -> e?.name })
        } else {
            foundEntities = []
        }
        return [(allFound): foundEntities]
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Entity getEntityById(entityId, HttpSession session, Boolean allowSaleView = Boolean.FALSE) {
        if (entityId) {
            def entityDbObject = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(entityId)])
            Entity entity = entityDbObject as Entity

            if (entity && !entity.deleted) {
                User user = userService.getCurrentUser()

                if (!isUserAllowedToAccessEntity(entity, user, allowSaleView)) {
                    loggerUtil.info(log, "User " + user?.username + " tried to illegally access entity " + entity)
                    entity = null
                }
                return entity
            } else {
                return null
            }
        } else {
            return null
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Entity getEntityById(entityId) {
        if (entityId) {
            def entityDbObject = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(entityId)])
            Entity entity = entityDbObject as Entity

            if (entity && !entity.deleted) {
                User user = userService.getCurrentUser()

                if (!isUserAllowedToAccessEntity(entity, user, false)) {
                    loggerUtil.info(log, "User " + user?.username + " tried to illegally access entity " + entity)
                    entity = null
                }
                return entity
            } else {
                return null
            }
        } else {
            return null
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Entity> getEntitiesByIds(List<String> entityIds, Entity parentEntity) {
        if (entityIds) {
            List<Entity> entities = Entity.findAllByIdInList(entityIds)
            User user = userService.getCurrentUser()

            return entities.findAll{ Entity entity ->
                if (entity && !entity.deleted) {

                    if (isUserAllowedToAccessEntity(entity, user, false, parentEntity)) {
                        return true
                    } else {
                        loggerUtil.info(log, "User " + user?.username + " tried to illegally access entity " + entity)
                        return false
                    }
                } else {
                    return false
                }
            }
        } else {
            return null
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<Entity> getAllEntitiesByIds(List<ObjectId> entityIds) {

        if (!entityIds) {
            return []
        }

        BasicDBObject query = new BasicDBObject()
        query["_id"] = ["\$in": entityIds]

        List<Entity> entities = Entity.collection.find(query)?.collect({ it as Entity })

        return entities
    }

    String getOperatingPeriodAndName(def entity) {
        String returnable

        String name = entity.name
        String operatingPeriod = entity.operatingPeriod
        Integer ribaStage = entity.ribaStage

        if (operatingPeriod) {
            returnable = operatingPeriod + (name ? " " + name : "")
        } else {
            returnable = name
        }

        if (ribaStage != null) {
            returnable = ribaStage + " - " + returnable
        }

        return returnable
    }

    List<String> getManagersAndModifiersEmails(def entity) {
        List<String> emails = []
        def userIds = []

        List<String> modifierIds = entity.modifierIds
        List<String> managerIds = entity.managerIds

        if (managerIds) {
            userIds.addAll(managerIds)
        }

        if (modifierIds) {
            userIds.addAll(modifierIds)
        }

        if (userIds) {
            BasicDBObject filter = new BasicDBObject("_id", [$in: DomainObjectUtil.stringsToObjectIds(userIds.unique())])
            emails = User.collection.distinct("username", filter, String.class)?.toList()
        }
        return emails
    }

    boolean isUserAllowedToAccessEntity(Entity entity, User user = null, Boolean allowSaleView = false, Entity parent = null) {
        if (!user) {
            user = userService.getCurrentUser()
        }

        if (userService.getSuperUser(user) || entity?.enableAsUserTestData || allowSaleView || userService.getConsultant(user)) {
            return true
        }

        if (entity) {
            List<String> allowedEntityIds = getAllowedParentEntityIds()

            if (!parent && entity.parentEntityId) {
                parent = entity?.getParentById()
            }

            if (parent) {
                if (parent.public || allowedEntityIds.contains(parent.id.toString()) || allowedEntityIds.contains(parent.id)) {
                    return true
                }
            } else if (entity.portfolio?.publishAsBenchmark) {
                return true
            } else {
                if (entity.public || allowedEntityIds.contains(entity.id.toString()) || allowedEntityIds.contains(entity.id)) {
                    return true
                }
            }
        }
        return false
    }

    Entity getEntityWithProjectionForQuery(String entityId, String queryId, List<String> projectLevelQueryIds) {
        Entity entity = null

        if (entityId && queryId) {
            // This aggregation will fetch only needed datasets for the current queryId and possible project level queryIds
            BasicDBObject query = new BasicDBObject().append("_id", DomainObjectUtil.stringToObjectId(entityId))
            Document match = new Document("\$match", query)

            Document queryIdConditions

            if (projectLevelQueryIds) {
                Document mainQueryIdEq = new Document('$eq', ['$$dataset.queryId', "${queryId}"])

                List queryIdConditionsList = [mainQueryIdEq]

                projectLevelQueryIds.each {
                    Document projectLevelQueryIdEq = new Document('$eq', ['$$dataset.queryId', "${it}"])
                    queryIdConditionsList.add(projectLevelQueryIdEq)
                }
                queryIdConditions = new Document('$or', queryIdConditionsList)
            } else {
                queryIdConditions = new Document('$eq', ['$$dataset.queryId', "${queryId}"])
            }

            Document project = new Document("\$project", ["datasets"                    : [$filter: [input: "\$datasets", as: "dataset", cond: queryIdConditions]],
                                                          "queryReady"                  : 1,
                                                          "name"                        : 1,
                                                          "operatingPeriod"             : 1,
                                                          "parentEntityId"              : 1,
                                                          "entityClass"                 : 1,
                                                          "dateCreated"                 : 1,
                                                          "quarterlyInputEnabledByQuery": 1,
                                                          "monthlyInputEnabledByQuery"  : 1,
                                                          locked                        : 1,
                                                          projectLevelQueries           : 1,
                                                          defaults                      : 1,
                                                          queryLastUpdateInfos          : 1,
                                                          "queryReadyPerIndicator"      : 1,
                                                          "deleted"                     : 1,
                                                          scope                         : 1,
                                                          disabledSections              : 1,
                                                          saveMessageByIndicator        : 1,
                                                          previousTotalByIndicator      : 1,
                                                          "resultsOutOfDate"            : 1
            ])
            List<Document> entities = Entity.collection.aggregate(Arrays.asList(match, project))?.toList()

            if (entities) {
                entity = entities.first() as Entity

                if (entity && entity.deleted) {
                    entity = null
                }
            }
        }
        return entity
    }

    def getEntityByIdReadOnly(String id) {
        if (id) {
            Entity entity = Entity.read(id)
            if (entity && !entity.deleted) {
                User user = userService.getCurrentUser()

                if (!userService.getSuperUser(user)) {
                    def allowedEntityIds = getAllowedParentEntityIds()
                    boolean isAllowed = false

                    if (entity.public || allowedEntityIds.contains(entity.id.toString()) || allowedEntityIds.contains(entity.id)) {
                        isAllowed = true
                    } else {
                        if (entity.parentEntityId) {
                            Entity parent = entity?.getParentById()

                            if (parent.public || allowedEntityIds.contains(parent.id.toString()) || allowedEntityIds.contains(parent.id)) {
                                isAllowed = true
                            }
                        } else if (entity.portfolio?.publishAsBenchmark) {
                            isAllowed = true
                        }
                    }

                    if (!isAllowed) {
                        loggerUtil.info(log, "User " + user?.username + " tried to illegally access entity " + entity)
                        entity = null
                    }
                }
                return entity
            } else {
                return null
            }

        } else {
            return null
        }
    }

    def readEntity(String entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(entityId)]) as Entity
        }
        return entity
    }

    def getLcaCheckerResult(Entity childEntity, String indicatorId, Integer percentage, String letter, String color,
                            Map<LcaChecker, Double> checksPassed,
                            Map<LcaChecker, Double> checksFailed,
                            Map<LcaChecker, Double> checksAcceptable,
                            List<LcaChecker> checksNotApplicable,
                            Map<LcaChecker, List<Object>> suppressedChecks) {
        LcaCheckerResult lcaCheckerResult

        if (childEntity && indicatorId && percentage != null && letter && color) {
            List<String> passed = []
            List<String> acceptable = []
            List<String> failed = []
            Map<String, Double> resultByCheck = [:]

            if (checksPassed) {
                checksPassed.each { LcaChecker lcaChecker, Double result ->
                    resultByCheck.put((lcaChecker.checkId), result)
                    passed.add(lcaChecker.checkId)
                }
            }

            if (checksAcceptable) {
                checksAcceptable.each { LcaChecker lcaChecker, Double result ->
                    resultByCheck.put((lcaChecker.checkId), result)
                    acceptable.add(lcaChecker.checkId)
                }
            }

            if (suppressedChecks) {
                suppressedChecks.each { LcaChecker lcaChecker, List<Object> result ->
                    resultByCheck.put((lcaChecker.checkId), result.get(0))

                    if (result.get(1) == "checksFailed") {
                        failed.add(lcaChecker.checkId)
                    } else if (result.get(1) == "checksAcceptable") {
                        acceptable.add(lcaChecker.checkId)
                    } else {
                        passed.add(lcaChecker.checkId)
                    }
                }
            }

            if (checksFailed) {
                checksFailed.each { LcaChecker lcaChecker, Double result ->
                    resultByCheck.put((lcaChecker.checkId), result)
                    failed.add(lcaChecker.checkId)
                }
            }

            if (checksNotApplicable) {
                checksNotApplicable.each { LcaChecker lcaChecker ->
                    resultByCheck.put((lcaChecker.checkId), null)
                    failed.add(lcaChecker.checkId)
                }
            }

            if (childEntity.lcaCheckerResult) {
                lcaCheckerResult = childEntity.lcaCheckerResult
            } else {
                lcaCheckerResult = new LcaCheckerResult()
            }
            lcaCheckerResult.percentage = percentage
            lcaCheckerResult.letter = letter
            lcaCheckerResult.color = color
            lcaCheckerResult.passed = passed
            lcaCheckerResult.acceptable = acceptable
            lcaCheckerResult.failed = failed
            lcaCheckerResult.resultByCheck = resultByCheck
        }
        return lcaCheckerResult
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getEntityFilesByType(String entityId, String type) {
        if (entityId) {
            if (type) {
                return EntityFile.findAllByEntityIdAndType(entityId, type.trim().toLowerCase())
            } else {
                return EntityFile.findAllByEntityId(entityId)
            }
        } else {
            return null
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def deleteEntityFilesByType(String entityId, String type) {
        if (entityId && type) {
            def files = EntityFile.findAllByEntityIdAndType(entityId, type.trim().toLowerCase())

            files?.each { EntityFile ef ->
                ef.delete(flush: true)
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getEntityImages(entityId, Boolean thumbnail) {
        List<EntityFile> files

        if (entityId) {
            if (thumbnail) {
                files = EntityFile.findAllByEntityIdAndQueryIdAndQuestionIdAndImageAndThumbnail(entityId, "basicQuery", "entityImage", true, true)
            } else {
                files = EntityFile.findAllByEntityIdAndQueryIdAndQuestionIdAndImage(entityId, "basicQuery", "entityImage", true)?.findAll({
                    !it.thumbnail
                })
            }
        }
        return files
    }

    def getEntityFile(String entityId, String queryId, String sectionId, String questionId) {
        if (entityId && queryId && sectionId && questionId) {
            try {
                return EntityFile.collection.findOne([entityId: entityId, queryId: queryId, sectionId: sectionId, questionId: questionId]) as EntityFile
            } catch (Exception e) {
                return null
            }
        }
        return null
    }

    def getThumbImagesForEntities(List<Entity> entities) {
        def images = [:]

        if (entities) {
            def entityIds = entities.collect({ it.id.toString() })
            def results = EntityFile.collection.find(["entityId": [$in: entityIds], "queryId": "basicQuery", "questionId": "entityImage", "image": true, "thumbnail": true])
            List<EntityFile> thumbImages

            if (results) {
                thumbImages = results.collect({ it as EntityFile })
            }

            thumbImages?.each { EntityFile ef ->
                if (!images.get(ef.entityId)) {
                    Base64 encoder = new Base64()
                    images.put(ef.entityId, "<img src=\"data:" + ef.contentType + ";base64," + encoder.encodeBase64String(ef.data) + "\" />")
                }
            }
        }
        return images
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getChildEntity(Entity entity, String childEntityId) {
        if (entity.childEntities) {
            return childEntityService.getEntity(entity.childEntities.find({ it.entityId.toString() == childEntityId })?.entityId)
        } else {
            return null
        }
    }

    def getEntityByIdAndToken(entityId, token) {
        if (optimiSecurityService.tokenOk(token)) {
            if (entityId) {
                Entity entity = Entity.get(entityId)

                if (!entity?.deleted) {
                    return entity
                } else {
                    return null
                }
            } else {
                return null
            }
        } else {
            throw new SecurityException("Trying to access without invalid token: " + token)
        }
    }

    @Secured(["ROLE_TASK_AUTHENTICATED"])
    def getRawEntityData(entityId) {
        if (entityId) {
            Entity entity = getEntityById(entityId)

            if (entity) {
                def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
                GMongo mongo = new GMongo()
                DB db = mongo.getDB(dbName)
                DBObject searchById = new BasicDBObject("_id", entity.id)
                DBObject rawEntity = db.entity.findOne(searchById)
                return rawEntity
            }
        } else {
            return null
        }
    }

    def getEntitiesByIndicatorId(String indicatorId) {
        List<Entity> entities = []

        if (indicatorId) {
            def entityClasses = optimiResourceService.getParentEntityClassIds()
            List<Entity> modifiableEntities = getModifiableEntities(entityClasses)

            if (modifiableEntities) {
                modifiableEntities.each { Entity e ->
                    List<License> validLicenses = licenseService.getValidLicensesForEntity(e)

                    if (validLicenses) {
                        for (License license in validLicenses) {
                            if (license.licensedIndicatorIds?.contains(indicatorId)) {
                                entities.add(e)
                                break
                            }
                        }
                    }
                }
            }
        }
        return entities
    }

    private boolean copyAnswersFromParent(Entity parentEntity, Entity childEntity, List<Indicator> indicators = null) {
        User user = userService.getCurrentUser()
        Account account = accountService.getManagedOrBasicAccountsForUser(user, [companyName   : 1, licenseIds: 1, addressLine1: 1, addressLine2: 1,
                                                                                 addressLine3  : 1, email: 1, postcode: 1, town: 1, state: 1, country: 1, vatNumber: 1, catalogueSlugs: 1, showLogoInSoftware: 1, branding: 1, favoriteMaterialIdAndUserId: 1, mainUserIds: 1,
                                                                                 companyWebsite: 1, organizationPhoneNr: 1, organizationEmail: 1, organizationContactPerson: 1, defaultProductImage: 1, defaultManufacturingDiagram: 1, defaultBrandingImage: 1])?.get(0)
        boolean copied = false

        if (parentEntity && childEntity) {
            Set<Dataset> projectLevelDatasets = parentEntity.datasets?.findAll({ it.projectLevelDataset })

            if (projectLevelDatasets) {
                if (childEntity.datasets) {
                    childEntity.datasets.addAll(projectLevelDatasets)
                } else {
                    childEntity.datasets = projectLevelDatasets
                }
            }
            childEntity.defaults = parentEntity.defaults

            if (indicators) {
                indicators.each { Indicator indicator ->
                    List<Question> copyAnswerQuestions = getAllQuestionsWithCopyAnswer(parentEntity, indicators)

                    if (copyAnswerQuestions) {
                        copyAnswerQuestions.each { Question question ->
                            def answer

                            CopyFromQuestion copyFromQuestion = question.copyAnswer

                            if (copyFromQuestion.copyFromParent) {
                                List datasetAnswers = parentEntity?.datasets?.find({
                                    copyFromQuestion.queryId.equals(it.queryId) &&
                                            copyFromQuestion.sectionId.equals(it.sectionId) && copyFromQuestion.questionId.equals(it.questionId)
                                })?.answerIds

                                if (datasetAnswers && !datasetAnswers.isEmpty()) {
                                    answer = datasetAnswers.get(0)
                                }
                            } else if (copyFromQuestion.customCopy) {
                                if ("projectName".equals(copyFromQuestion.customCopy)) {
                                    answer = parentEntity?.name
                                } else if ("designName".equals(copyFromQuestion.customCopy)) {
                                    answer = childEntity?.name
                                } else if ("userName".equals(copyFromQuestion.customCopy)) {
                                    answer = user?.name
                                } else if ("organizationAddress".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.addressLine1 ?: account?.addressLine2 ?: account?.addressLine3 ?: null
                                } else if ("organizationPostalCode".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.postcode
                                } else if ("organizationCity".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.town
                                } else if ("organizationName".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.companyName
                                } else if ("organizationWebsite".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.companyWebsite
                                } else if ("organizationPhoneNr".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.organizationPhoneNr
                                } else if ("organizationEmail".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.organizationEmail
                                } else if ("organizationContactPerson".equals(copyFromQuestion.customCopy)) {
                                    answer = account?.organizationContactPerson
                                } else if ("organizationProductImage".equals(copyFromQuestion.customCopy)) {
                                    answer = accountImagesService.getDefaultProductImage(account?.defaultProductImage)?.id
                                } else if ("organizationManufacturingDiagram".equals(copyFromQuestion.customCopy)) {
                                    answer = accountImagesService.getDefaultManufacturingDiagram(account?.defaultManufacturingDiagram)?.id
                                } else if ("organizationBrand".equals(copyFromQuestion.customCopy)) {
                                    answer = accountImagesService.getDefaultBrandingImage(account?.defaultBrandingImage)?.id
                                } else {
                                    loggerUtil.error(log, "COPY ANSWER FROM PARENT: question: ${question.questionId}, unrecognized customCopy: ${copyFromQuestion.customCopy}")
                                    flashService.setErrorAlert("COPY ANSWER FROM PARENT: question: ${question.questionId}, unrecognized customCopy: ${copyFromQuestion.customCopy}", true)
                                }

                            }
                            /* Not supported atm
                            else {
                                copyFromDataset = entity?.datasets?.find({
                                    copyFromQuestion.queryId.equals(it.queryId) &&
                                            copyFromQuestion.sectionId.equals(it.sectionId) && copyFromQuestion.questionId.equals(it.questionId)
                                })
                            }*/

                            if (answer != null) {
                                Dataset dataset = new Dataset()
                                dataset.queryId = question.queryId
                                dataset.sectionId = question.sectionId
                                dataset.questionId = question.questionId
                                dataset.answerIds = ["${answer}"]

                                if (childEntity.datasets) {
                                    childEntity.datasets.add(dataset)
                                } else {
                                    childEntity.datasets = [dataset]
                                }
                                copied = true
                            }
                        }
                    }
                }
            }
        }
        return copied
    }

    private List<Question> getAllQuestionsWithDefaultValue(Entity entity, List<Indicator> indicators) {
        List<Question> questions = []

        indicators?.each { Indicator indicator ->
            indicator?.getQueries(entity)?.each { Query query ->
                if (!Constants.PARAMETER_QUERYIDS.contains(query.queryId)) {
                    query.getAllQuestions()?.findAll({ Question question -> question.defaultValue && !question.multipleChoicesAllowed })?.each { Question question ->
                        if (!questions.find({ question.questionId.equals(it?.questionId) })) {
                            questions.add(question)
                        }
                    }
                }
            }
        }
        return questions
    }

    private List<Question> getAllQuestionsWithCopyAnswer(Entity entity, List<Indicator> indicators) {
        List<Question> questions = []

        indicators?.each { Indicator indicator ->
            indicator?.getQueries(entity)?.each { Query query ->
                query.getAllQuestions()?.
                        findAll({ Question question -> question.copyAnswer })?.each { Question question ->
                    if (!questions.find({ question.questionId.equals(it?.questionId) })) {
                        questions.add(question)
                    }
                }
            }
        }
        return questions
    }

    private boolean copyDefaultAnswers(Entity parentEntity, Entity childEntity, List<Indicator> indicators) {
        boolean defaultsCopied = false

        if (childEntity && indicators) {
            List<Question> defaultValueQuestions = getAllQuestionsWithDefaultValue(parentEntity, indicators)

            if (defaultValueQuestions) {
                defaultsCopied = true

                defaultValueQuestions.each { Question question ->
                    Dataset dataset = new Dataset()
                    dataset.queryId = question.queryId
                    dataset.sectionId = question.sectionId
                    dataset.questionId = question.questionId

                    if (optimiResourceService.getResourceByResourceAndProfileId(question.defaultValue, "")) {
                        dataset.resourceId = question.defaultValue
                    } else {
                        dataset.answerIds = [question.defaultValue]
                    }

                    if (childEntity.datasets) {
                        childEntity.datasets.add(dataset)
                    } else {
                        childEntity.datasets = [dataset]
                    }
                }
            }
        }
        return defaultsCopied
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def setParentRibaStage(Entity parent, Entity child) {
        Boolean stageChanged = Boolean.FALSE

        if (parent && child) {
            if (child.ribaStage != null) {
                if (parent.ribaStage != null) {
                    if (parent.ribaStage < child.ribaStage) {
                        parent.ribaStage = new Integer(child.ribaStage)
                        stageChanged = Boolean.TRUE
                    }
                } else {
                    parent.ribaStage = new Integer(child.ribaStage)
                    stageChanged = Boolean.TRUE
                }
            }
        }
        return stageChanged
    }
    /**
     *
     * @param entity
     * @param copyAnswer set to false if copying the answers and defaults from parent is not needed
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    Entity createEntity(Entity entity, Entity parentEntity = null, Boolean copyAnswer = true) {
        try {
            User currentUser = userService.getCurrentUser(Boolean.TRUE)

            if (entity.parentEntityId) {
                Entity chosenPreviousEntity = getChosenEntity(entity.parentEntityId)

                if (chosenPreviousEntity) {
                    if (entity.ribaStage != null && entity.ribaStage > chosenPreviousEntity.ribaStage) {
                        entity.chosenDesign = Boolean.TRUE
                        chosenPreviousEntity.chosenDesign = null
                        chosenPreviousEntity.merge(flush: true, failOnError: true)
                    }
                } else {
                    entity.chosenDesign = Boolean.TRUE
                }

                if (!parentEntity) {
                    parentEntity = entity.getParentById()
                }

                List<Indicator> indicators = parentEntity?.indicators?.findAll({ Indicator ind -> entity.entityClass.equals(ind.indicatorUse) })
                if (copyAnswer) {
                    boolean answersCopied = copyAnswersFromParent(parentEntity, entity, indicators)
                    boolean defaultsCopied = copyDefaultAnswers(parentEntity, entity, indicators)
                }

                entity.parentName = parentEntity?.name
                ChildEntity child = new ChildEntity()
                child.parentId = parentEntity.id
                child.entityClass = entity.entityClass
                entity = entity.save(flush: true, failOnError: true)
                child.entityId = entity.id
                child.operatingPeriodAndName = entity.getOperatingPeriodAndName()

                if (parentEntity.childEntities) {
                    parentEntity.childEntities.add(child)
                } else {
                    parentEntity.childEntities = [child]
                }

                setParentRibaStage(parentEntity, entity)
                parentEntity.merge(flush: true, failOnError: true)
            } else {
                entity = entity.save(flush: true, failOnError: true)
            }

            if (entity && currentUser) {
                entity.managerIds?.add(currentUser.id.toString())
                currentUser.managedEntityIds?.add(entity.id.toString())
                isUserAddedToDemoProject(entity, currentUser, "ManagedEntityIds modified in createEntity()")
                try {
                    entity = entity.merge(flush: true, failOnError: true)
                    userService.updateUser(currentUser)

                    licenseService.getValidLicensesForEntity(entity)?.each { License license ->
                        license.licensedUserIdsMap?.each { userId, userType ->
                            User user = User.get(userId)
                            if (!currentUser?.username?.equals(user?.username)) {
                                addEntityUser(entity.id, user.username, userType)
                            }
                        }
                    }

                } catch (OptimisticLockingException e) {
                    loggerUtil.error(log, "Error in creating entity ${entity?.name}", e)
                    flashService.setErrorAlert("Error in creating entity ${entity?.name}: ${e.getMessage()}", true)
                }
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in creating entity", e)
            flashService.setErrorAlert("Error in creating entity: ${e.getMessage()}", true)

            if (entity?.id) {
                entity = Entity.get(entity.id)
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def updateEntity(Entity entity, Boolean nameChanged = Boolean.FALSE) {
        if (!entity.id) {
            throw new RuntimeException("Cannot update entity without id.")
        }

        if (entity.parentEntityId && entity.chosenDesign) {
            Entity chosenPreviousEntity = getChosenEntity(entity.parentEntityId)
            if (chosenPreviousEntity && !chosenPreviousEntity.id?.toString()?.equalsIgnoreCase(entity.id?.toString())) {
                chosenPreviousEntity.chosenDesign = null
                chosenPreviousEntity.merge(flush: true, failOnError: true)
            }
        }

        if (entity.parentEntityId && entity.ribaStage != null) {
            Entity parent = entity.getParentById()
            Boolean stageChanged = Boolean.FALSE

            if (parent && parent.ribaStage != null) {
                Integer newRibaStage = parent.childrenByChildEntities?.findAll({ !it.id.toString().equals(entity.id.toString()) && it.ribaStage != null && !it.deleted })?.collect({ it.ribaStage })?.max()?.intValue()

                if (newRibaStage != null && entity.ribaStage > newRibaStage) {
                    newRibaStage = entity.ribaStage
                }

                if (newRibaStage != null && parent.ribaStage != newRibaStage) {
                    parent.ribaStage = new Integer(newRibaStage)
                    stageChanged = Boolean.TRUE
                }
            }

            if (stageChanged) {
                parent = parent.merge(flush: true)
            }
        }

        entity = entity.merge(flush: true, failOnError: true)

        if (nameChanged) {
            if (entity.childEntities) {
                entity.getChildrenByChildEntities()?.each { Entity child ->
                    child.parentName = entity.name
                    child.merge(flush: true)
                }
            } else if (entity.parentEntityId) {
                Entity parent = entity.getParentById()
                ChildEntity childEntity = parent.childEntities?.
                        find({ ChildEntity child -> entity.id.equals(child.entityId) })
                if (childEntity) {
                    childEntity.operatingPeriodAndName = entity.getOperatingPeriodAndName()
                }
                parent.merge(flush: true)
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def addEntityUser(entityId, String email, String userType, Boolean skipUserBasedCheck = Boolean.FALSE) {
        User user

        if (entityId && email && userType) {
            HttpSession session = WebUtils.retrieveGrailsWebRequest()?.getSession()
            Entity entity = Entity.get(entityId)

            if (entity) {
                email = email.trim().toLowerCase()
                List<License> licenses = licenseService.getValidLicensesForEntity(entity)
                user = User.findByUsername(email)
                Boolean userBasedLicensing = licenses && !licenses?.find({ !it.userBased }) ? Boolean.TRUE : Boolean.FALSE
                List licenseUsersIds = licenses?.collect({ it.licenseUserIds })?.flatten()?.unique()

                if (user && !userService.getTaskAuthenticated(user) && ((!userBasedLicensing || licenseUsersIds?.contains(user.id.toString())) || skipUserBasedCheck)) {
                    String userId = user.id.toString()
                    PreProvisionedUserRight preProvisionedUserRight = entity.preProvisionedUserRights?.find({
                        it.username.equals(email)
                    })

                    if (preProvisionedUserRight) {
                        entity.preProvisionedUserRights.remove(preProvisionedUserRight)
                    }

                    if (EntityUserType.MANAGER.toString() == userType && !entity.managerIds?.contains(userId)) {
                        entity.managerIds.add(userId)
                        entity.modifierIds?.remove(userId)
                        entity.readonlyUserIds?.remove(userId)
                        entity.save()
                        user.managedEntityIds?.add(entity.id.toString())
                        user.modifiableEntityIds?.remove(entity.id.toString())
                        user.readonlyEntityIds?.remove(entity.id.toString())
                        user = userService.updateUser(user)
                        isUserAddedToDemoProject(entity, user, "ManagedEntityIds modified in addEntityUser()")
                    } else if (EntityUserType.MODIFIER.toString() == userType && !entity.modifierIds?.contains(userId)
                            && !entity.managerIds?.contains(userId)) {
                        entity.modifierIds.add(userId)
                        entity.readonlyUserIds?.remove(userId)
                        entity.save()
                        user.modifiableEntityIds?.add(entity.id.toString())
                        user.readonlyEntityIds?.remove(entity.id.toString())
                        user = userService.updateUser(user)
                        isUserAddedToDemoProject(entity, user, "ModifiableEntityIds modified in addEntityUser()")
                    } else if (EntityUserType.READONLY.toString() == userType && !entity.modifierIds?.contains(userId)
                            && !entity.managerIds?.contains(userId) && !entity.readonlyUserIds?.contains(userId)) {
                        entity.readonlyUserIds.add(userId)
                        entity.save()
                        user.readonlyEntityIds?.add(entity.id.toString())
                        user = userService.updateUser(user)
                    }
                } else if (!userBasedLicensing) {
                    PreProvisionedUserRight preProvisionedUserRight = entity.preProvisionedUserRights?.find({
                        it.username.equals(email)
                    })
                    boolean hasExisting = true

                    if (!preProvisionedUserRight) {
                        hasExisting = false
                        preProvisionedUserRight = new PreProvisionedUserRight()
                    }
                    preProvisionedUserRight.uuid = new ObjectId().toString()
                    preProvisionedUserRight.username = email
                    preProvisionedUserRight.userRight = userType

                    if (!hasExisting) {
                        if (entity.preProvisionedUserRights) {
                            entity.preProvisionedUserRights.add(preProvisionedUserRight)
                        } else {
                            entity.preProvisionedUserRights = [preProvisionedUserRight]
                        }
                    }
                    entity.save()
                } else if (userBasedLicensing && licenses.find({ it.licensedFeatures?.collect({ it.featureId })?.contains(Feature.ALLOW_ADDING_ANY_USER) }) && (EntityUserType.READONLY.toString() == userType || EntityUserType.MODIFIER.toString() == userType)) {
                    // 10679
                    if (user && !userService.getTaskAuthenticated(user)) {
                        String userId = user.id.toString()
                        PreProvisionedUserRight preProvisionedUserRight = entity.preProvisionedUserRights?.find({
                            it.username.equals(email)
                        })

                        if (preProvisionedUserRight) {
                            entity.preProvisionedUserRights.remove(preProvisionedUserRight)
                        }

                        if (EntityUserType.MODIFIER.toString() == userType && !entity.modifierIds?.contains(userId)
                                && !entity.managerIds?.contains(userId)) {
                            entity.modifierIds.add(userId)
                            entity.readonlyUserIds?.remove(userId)
                            entity.save()
                            user.modifiableEntityIds?.add(entity.id.toString())
                            user.readonlyEntityIds?.remove(entity.id.toString())
                            user = userService.updateUser(user)
                            isUserAddedToDemoProject(entity, user, "ModifiableEntityIds modified in addEntityUser() for userBasedLicensing")
                        } else if (EntityUserType.READONLY.toString() == userType && !entity.modifierIds?.contains(userId)
                                && !entity.managerIds?.contains(userId) && !entity.readonlyUserIds?.contains(userId)) {
                            entity.readonlyUserIds.add(userId)
                            entity.save()
                            user.readonlyEntityIds?.add(entity.id.toString())
                            user = userService.updateUser(user)
                        }
                    } else {
                        PreProvisionedUserRight preProvisionedUserRight = entity.preProvisionedUserRights?.find({
                            it.username.equals(email)
                        })
                        boolean hasExisting = true

                        if (!preProvisionedUserRight) {
                            hasExisting = false
                            preProvisionedUserRight = new PreProvisionedUserRight()
                        }
                        preProvisionedUserRight.uuid = new ObjectId().toString()
                        preProvisionedUserRight.username = email
                        preProvisionedUserRight.userRight = userType

                        if (!hasExisting) {
                            if (entity.preProvisionedUserRights) {
                                entity.preProvisionedUserRights.add(preProvisionedUserRight)
                            } else {
                                entity.preProvisionedUserRights = [preProvisionedUserRight]
                            }
                        }
                        entity.save()
                    }
                } else {
                    user = null
                }
            }
        }
        return user
    }

    def removePreProvisionedUser(entityId, String email, String uuid) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)
        }

        if (entity && entity.preProvisionedUserRights) {
            PreProvisionedUserRight preProvisionedUserRight

            if (uuid) {
                preProvisionedUserRight = entity.preProvisionedUserRights.find({
                    uuid.equals(it.uuid)
                })
            } else {
                preProvisionedUserRight = entity.preProvisionedUserRights.find({
                    it.username.equals(email)
                })
            }

            if (preProvisionedUserRight) {
                entity.preProvisionedUserRights.remove(preProvisionedUserRight)
                entity.merge()
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Map<String, String> removeEntityUser(entityId, userId, userType = null) {
        Map<String, String> returnable = [:]
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)
        }

        if (entity && userId) {
            User user = User.get(userId)
            def userIds = entity.getUserIds()
            def managerIds = entity.managerIds

            // We cannot remove the last user or last manager
            if (user && userIds?.size() > 1) {
                if (EntityUserType.MANAGER.toString() == userType && (managerIds?.contains(userId) || managerIds?.contains(user.id))) {
                    if (managerIds?.size() > 1) {
                        entity.managerIds?.remove(user.id.toString())
                        entity.save()
                        user.managedEntityIds.removeIf {it == entity.id.toString()}
                        user.save(flush: true)
                    } else {
                        returnable.put("errorKey", "entity.users.last_user")
                    }
                } else if (EntityUserType.MODIFIER.toString() == userType && (entity.modifierIds?.contains(userId) || entity.modifierIds?.contains(user.id))) {
                    entity.modifierIds?.remove(user.id.toString())
                    entity.save()
                    user.modifiableEntityIds.removeIf {it == entity.id.toString()}
                    user.save(flush: true)
                } else if (EntityUserType.READONLY.toString() == userType && (entity.readonlyUserIds?.contains(userId) || entity.readonlyUserIds?.contains(user.id))) {
                    entity.readonlyUserIds?.remove(user.id.toString())
                    entity.save()
                    user.readonlyEntityIds.removeIf {it == entity.id.toString()}
                    user.save(flush: true)
                } else if (!userType) {
                    if (entity.managerIds?.contains(userId) || entity.managerIds?.contains(user.id)) {
                        entity.managerIds?.remove(user.id.toString())
                        entity.save()
                        user.managedEntityIds.removeIf {it == entity.id.toString()}
                        user.save(flush: true)
                    } else if (entity.modifierIds?.contains(userId) || entity.modifierIds?.contains(user.id)) {
                        entity.modifierIds?.remove(user.id.toString())
                        entity.save()
                        user.modifiableEntityIds.removeIf {it == entity.id.toString()}
                        user.save(flush: true)
                    } else if (entity.readonlyUserIds?.contains(userId) || entity.readonlyUserIds?.contains(user.id)) {
                        entity.readonlyUserIds?.remove(user.id.toString())
                        entity.save()
                        user.readonlyEntityIds.removeIf {it == entity.id.toString()}
                        user.save(flush: true)
                    }
                }
            } else if (userIds?.size() == 1) {
                returnable.put("errorKey", "entity.users.last_user")
            }
        }
        return returnable
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntitiesWithResultByIndicatorId(String indicatorId) {
        def entities = [:]
        def childEntityClasses = optimiResourceService.getChildEntityClasses()?.collect({ Resource resource -> resource.resourceId })

        if (childEntityClasses) {
            Criteria criteria = Entity.createCriteria()
            List<Entity> foundEntities = criteria.list {
                calculationTotalResults {
                    eq('indicatorId', indicatorId)
                }
                and {
                    inList('entityClass', childEntityClasses)
                }
            }

            if (foundEntities) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

                if (!indicator) {
                    indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.FALSE)
                }

                for (Entity entity in foundEntities) {
                    Entity parent

                    if (entity.parentEntityId) {
                        parent = entity.getParentById()

                        if (parent && indicator?.compatibleEntityClasses?.contains(parent.entityClass)) {
                            List existing = entities.get(parent.name)

                            if (existing) {
                                existing.add(entity)
                            } else {
                                existing = [entity]
                            }
                            entities.put(parent.name, existing)
                        }
                    }
                }
            }
        }
        return entities
    }

    def addIndicatorForEntity(entityId, indicatorId) {
        if (entityId && indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

            if (indicator && !indicator.deprecated) {
                Entity entity = Entity.get(entityId)
                List<String> indicatorIds = entity.indicatorIds

                if (!indicatorIds?.contains(indicatorId)) {
                    if (indicatorIds) {
                        indicatorIds.add(indicatorId)
                    } else {
                        indicatorIds = [indicatorId]
                    }
                    entity.indicatorIds = indicatorIds
                    entity.merge(flush: true)
                }
            } else {
                errorMessageUtil.setErrorMessage("Indicator ${indicatorService.getLocalizedName(indicator)} is deprecated, it cannot be added")
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def addIndicatorIdsForEntity(entityId, List<String> indicatorIds, indicatorUse, HttpSession session = null) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)
        }
        List indicatorsToDisable = []

        if (entity && indicatorIds) {

            def indicatorListEntity = indicatorService.getIndicatorsByEntity(entity.id)?.findAll({ it.indicatorUse == indicatorUse })
            def indicatorIdsToRemove = indicatorListEntity?.collect { it.indicatorId }
            List<String> indicatorIdsToRecalculate = []

            indicatorIds?.each {
                if (!indicatorIdsToRemove?.contains(it)) {
                    indicatorIdsToRecalculate.add(it)
                }
            }

            Indicator firstIndicator = indicatorListEntity ? indicatorListEntity.first() : null
            indicatorsToDisable = getIndicatorsToDisable(entity, firstIndicator, indicatorIds) ?: []

            def indicatorsEqual = false

            if (indicatorIdsToRemove) {
                if (indicatorIds.size() == indicatorIdsToRemove.size() && indicatorIds.containsAll(indicatorIdsToRemove)) {
                    indicatorsEqual = true
                }

                if (!indicatorsEqual) {
                    def entityIndicatorIds = entity.indicatorIds?.toArray()?.toList()
                    entityIndicatorIds.removeAll(indicatorIdsToRemove)
                    entity.indicatorIds = entityIndicatorIds
                }
            }

            if (!indicatorsEqual) {
                if (entity.indicatorIds) {
                    entity.indicatorIds.addAll(indicatorIds)
                } else {
                    entity.indicatorIds = indicatorIds
                }

                List<Indicator> allEntityIndicators = indicatorService.getIndicatorsByIndicatorIds(indicatorIds, true)
                Boolean doesntUseDefaults = Boolean.TRUE
                if (allEntityIndicators && allEntityIndicators.find({ it.indicatorQueries?.find({ it.projectLevel && com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) }) })) {
                    doesntUseDefaults = Boolean.FALSE
                }

                Map<String, String> defaults = [:]
                if (doesntUseDefaults) {
                    entity.defaults = null
                } else {
                    Dataset defaultTransportDataset = entity.datasets?.find({ it.projectLevelDataset && it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID && it.sectionId == com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID && it.questionId == "transportationDefault" })
                    Dataset defaultServiceLifeDataset = entity.datasets?.find({ it.projectLevelDataset && it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID && it.sectionId == com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID && it.questionId == "serviceLifeDefault" })

                    if (defaultTransportDataset && defaultTransportDataset.answerIds) {
                        String defaultTransport = defaultTransportDataset.answerIds[0]
                        defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT), defaultTransport ?: null)
                    }

                    if (defaultServiceLifeDataset && defaultServiceLifeDataset.answerIds) {
                        String defaultServiceLife = defaultServiceLifeDataset.answerIds[0]
                        defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE), defaultServiceLife ?: null)
                    }
                    entity.defaults = defaults
                }
                entity = entity.merge(flush: true, failOnError: true)

                def indicators = entity.indicators
                def entityClass

                if ("operating" == indicatorUse) {
                    entityClass = EntityClass.OPERATING_PERIOD.toString()
                } else {
                    entityClass = EntityClass.DESIGN.toString()
                }
                def entities = getChildEntities(entity, entityClass)

                if (entities) {
                    Iterator<Entity> entityIterator = entities.iterator()

                    AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib')
                    Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                    List<Question> additionalQuestionQuestions = additionalQuestionsQuery?.getAllQuestions()
                    Map<String, Question> resolvedQuestions = [:]
                    Map<String, List<Question>> resolvedAdditionalQuestionsPerIndicatorAndQuestions = [:]

                    while (entityIterator.hasNext()) {
                        Entity childEntity = entityIterator.next()
                        childEntity.disabledIndicators = (childEntity.disabledIndicators ?: []) + indicatorsToDisable
                        childEntity.disabledIndicators = childEntity.disabledIndicators?.unique()

                        if (doesntUseDefaults) {
                            childEntity.defaults = null
                            childEntity.datasets?.each {
                                if (it.additionalQuestionAnswers) {
                                    if ("default".equals(it.additionalQuestionAnswers.get("serviceLife"))) {
                                        it.additionalQuestionAnswers.remove("serviceLife")
                                    }
                                    if ("default".equals(it.additionalQuestionAnswers.get("transportDistance_km"))) {
                                        it.additionalQuestionAnswers.remove("transportDistance_km")
                                    }

                                    if ("default".equals(it.additionalQuestionAnswers.get("transportDistance_mi"))) {
                                        it.additionalQuestionAnswers.remove("transportDistance_mi")
                                    }

                                    if ("default".equals(it.additionalQuestionAnswers.get("transportDistanceTRACI_km"))) {
                                        it.additionalQuestionAnswers.remove("transportDistanceTRACI_km")
                                    }

                                    if ("default".equals(it.additionalQuestionAnswers.get("transportTreteknisk_km"))) {
                                        it.additionalQuestionAnswers.remove("transportTreteknisk_km")
                                    }

                                    if ("default".equals(it.additionalQuestionAnswers.get("transportDistance_kmAus"))) {
                                        it.additionalQuestionAnswers.remove("transportDistance_kmAus")
                                    }
                                }
                            }
                        } else {
                            childEntity.defaults = defaults
                        }

                        indicators?.each { Indicator indicator ->
                            if (indicatorIds.contains(indicator.indicatorId)) {
                                if (indicatorIdsToRecalculate.contains(indicator.indicatorId)) {
                                    // Resolve default additionalquestionanswers for indicators
                                    ResourceCache resourceCache = ResourceCache.init(childEntity.datasets.toList())
                                    for (Dataset d: childEntity.datasets)
                                    {
                                        Question question = resolvedQuestions.get(d.questionId)

                                        if (!question) {
                                            question = datasetService.getQuestion(d)

                                            if (question) {
                                                resolvedQuestions.put((d.questionId), question)
                                            }
                                        }

                                        if (question) {
                                            String key = "${indicator.indicatorId}${question.questionId}"
                                            List<Question> additionalQuestions = resolvedAdditionalQuestionsPerIndicatorAndQuestions.get(key)

                                            if (additionalQuestions == null) {
                                                additionalQuestions = question.getAdditionalQuestions(indicator, additionalQuestionsQuery, additionalQuestionQuestions, entity)

                                                if (additionalQuestions) {
                                                    resolvedAdditionalQuestionsPerIndicatorAndQuestions.put((key), additionalQuestions)
                                                } else {
                                                    resolvedAdditionalQuestionsPerIndicatorAndQuestions.put((key), [])
                                                }
                                            }

                                            if (additionalQuestions) {
                                                for (Question additionalQuestion: additionalQuestions) {
                                                    if (!d?.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                                                        String defaultValue = "${aq.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: resourceCache.getResource(d))}"

                                                        if (defaultValue) {
                                                            if (d.additionalQuestionAnswers) {
                                                                d.additionalQuestionAnswers.put(additionalQuestion.questionId, defaultValue)
                                                            } else {
                                                                d.additionalQuestionAnswers = [(additionalQuestion.questionId): defaultValue]
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                    childEntity = newCalculationServiceProxy.calculate(null, indicator.indicatorId, entity, childEntity)
                                }
                            } else {
                                removeExistingResults(childEntity, indicator)
                            }
                        }
                        childEntity = childEntity.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    private removeExistingResults(Entity entity, Indicator indicator) {
        if (entity && indicator) {
            List<CalculationResult> calculationResults = CalculationResult.findAllByEntityIdAndIndicatorId(entity.id.toString(), indicator.indicatorId)

            if (calculationResults) {
                entity.calculationResults?.removeAll(calculationResults)
                CalculationResult.collection.remove(["entityId": entity.id.toString(), "indicatorId": indicator.indicatorId])
            }

            if (entity.calculationTotalResults?.find({ indicator.indicatorId.equals(it.indicatorId) })) {
                entity.calculationTotalResults?.removeAll({ indicator.indicatorId.equals(it.indicatorId) })
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def commitEntity(entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)
        }

        if (entity) {
            entity.commited = true
            entity.merge(flush: true, failOnError: true)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getChildEntities(Entity parentEntity, String childEntityClass, Map projection = [:]) {
        // loggerUtil.debug(log, "Start of EntityService.getChildEntities")
        def children

        if ("design".equals(childEntityClass)) {
            List<ObjectId> designIds = parentEntity?.childEntities?.findAll({
                !it.deleted &&
                        it.entityClass.equals(EntityClass.DESIGN.toString())
            })?.collect({ it.entityId })

            if (designIds) {
                children = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]],
                        projection)?.collect({ it as Entity })
            }
        } else {
            List<ObjectId> operatingPeriodIds = parentEntity?.childEntities?.findAll({
                !it.deleted &&
                        it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
            })?.collect({ it.entityId })

            children = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(operatingPeriodIds)]],
                    projection)?.collect({ it as Entity })
        }
        return children
    }

    def getEntityWithProjection(def id, Map projection) {
        Entity entity

        if (id && projection) {
            def result = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(id)], projection)

            if (result) {
                entity = result as Entity
            }
        }
        return entity
    }

    def getChildEntitiesForShowing(Entity entity, String entityClass, List<ObjectId> childIds = null, Boolean alsoDeleted = Boolean.FALSE) {
        def children = []

        if (entity) {
            if (!childIds) {
                if (entityClass) {
                    if (alsoDeleted) {
                        childIds = entity?.childEntities?.findAll({ it.entityClass.equals(entityClass) })?.collect({
                            it.entityId
                        })
                    } else {
                        childIds = entity?.childEntities?.findAll({
                            !it.deleted &&
                                    it.entityClass.equals(entityClass)
                        })?.collect({ it.entityId })
                    }
                } else {
                    if (alsoDeleted) {
                        childIds = entity?.childEntities?.collect({ it.entityId })
                    } else {
                        childIds = entity?.childEntities?.findAll({ !it.deleted })?.collect({ it.entityId })
                    }
                }
            }

            if (childIds) {
                Map projection = [commited               : 1, deleted: 1, master: 1, locked: 1, name: 1, lockingTime: 1,
                                  calculationTotalResults: 1, queryReady: 1, operatingPeriod: 1, component: 1,
                                  parentEntityId         : 1, entityClass: 1, targetedNote: 1, allowAsBenchmark: 1,
                                  datasets               : 1, anonymousDescription: 1, superLocked: 1, enableAsUserTestData: 1,
                                  ribaStage              : 1, chosenDesign: 1, superVerified: 1,
                                  targetedTaskIds        : 1, queryReadyPerIndicator: 1,
                                  disabledIndicators     : 1, defaults: 1, carbonHero: 1, scope: 1, isHiddenDesign: 1,
                                  queryLastUpdateInfos   : 1, dateCreated: 1, resultsOutOfDate: 1
                ]
                children = Entity.collection.find(["_id": [$in: childIds]], projection)?.collect({ it as Entity })
            }
        }
        return children?.sort()
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def delete(entityId) {
        Entity entity

        try {
            entity = getEntityById(entityId)

            if (entity) {
                entity.deleted = true
                entity.getTasks()?.each { Task task ->
                    if (task) {
                        task.delete(flush: true)
                    }
                }
                entity.targetedTaskIds = []

                Portfolio portfolio = entity.portfolio

                if (portfolio) {
                    portfolio.entityDeleted = true
                    portfolio.merge(flush: true)
                }

                if (entity.calculationResults) {
                    CalculationResult.collection.remove([entityId: entity.id.toString()])
                    entity.calculationResults = null
                    entity.calculationTotalResults = null
                }

                entity = entity.merge(failOnError: true)

                if (entity.parentEntityId) {
                    //The entity could be a Design
                    simulationToolService.deleteDesign(entity.parentEntityId.toString(), entity.id.toString())

                    Entity parent = Entity.get(entity.parentEntityId)
                    if (parent) {
                        Boolean ribaStageChanged = Boolean.FALSE
                        if (parent.ribaStage != null || entity.chosenDesign) {
                            List<Entity> childEntitiesObj = parent.childrenByChildEntities
                            Integer newRibaStage
                            if (parent.ribaStage != null) {
                                newRibaStage = childEntitiesObj?.findAll({
                                    !it.id.toString().equals(entity.id.toString()) && it.ribaStage != null && !it.deleted
                                })?.collect({ it.ribaStage })?.max()?.intValue()

                                if (newRibaStage != null && parent.ribaStage != newRibaStage) {
                                    parent.ribaStage = new Integer(newRibaStage)
                                    ribaStageChanged = Boolean.TRUE
                                }
                            }
                            if (entity.chosenDesign) {
                                Entity newChosenEntity
                                if (newRibaStage) {
                                    newChosenEntity = childEntitiesObj?.find({ it.ribaStage == newRibaStage && it.id != entity.id && !it.deleted })
                                } else {
                                    newChosenEntity = childEntitiesObj?.sort({ it.lastUpdated })?.find({ !it.deleted && it.id != entity.id })
                                }
                                if (newChosenEntity) {
                                    newChosenEntity.chosenDesign = Boolean.TRUE
                                    newChosenEntity.merge(failOnError: true)
                                }
                            }
                        }


                        ChildEntity childEntity = parent.childEntities?.find({ it.entityId.equals(entity.id) })

                        if (childEntity) {
                            childEntity.deleted = Boolean.TRUE
                            parent.merge()
                        } else if (ribaStageChanged) {
                            parent.merge()
                        }
                    }
                } else {
                    //The entity is a Parent Entity (A Building Project)
                    simulationToolService.deleteParentEntity(entity.id.toString())

                    if (entity.childEntities) {
                        entity.childrenByChildEntities?.each { Entity child ->
                            if (child.calculationResults) {
                                CalculationResult.collection.remove([entityId: child.id.toString()])
                                child.calculationResults = null
                                child.calculationTotalResults = null
                                child.merge(failOnError: true)
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in deleting entity ${entity?.operatingPeriodAndName}", e)
            flashService.setErrorAlert("Error in deleting entity ${entity?.operatingPeriodAndName}: ${e.getMessage()}", true)
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def archive(entityId, Boolean superArchive) {
        User user = userService.getCurrentUser()
        Entity parentEntity

        try {
            parentEntity = getEntityById(entityId)

            if (parentEntity) {
                parentEntity.archived = true

                if (superArchive && userService.getSuperUser(user)) {
                    parentEntity.superArchived = true
                }

                if (parentEntity.childEntities) {
                    parentEntity.childEntities.each {
                        it.locked = true
                        if (superArchive && userService.getSuperUser(user)) {
                            it.superLocked = true
                        }
                    }

                    parentEntity.childrenByChildEntities?.each { Entity entity ->
                        entity.locked = true
                        if (superArchive && userService.getSuperUser(user)) {
                            entity.superLocked = true
                        }

                        if (entity.defaults && entity.datasets) {
                            ResourceCache resourceCache = ResourceCache.init(entity.datasets.toList())
                            for (Dataset d: entity.datasets) {
                                entity.defaults.each { String valueToSet, String defaultValue ->
                                    if (Constants.DEFAULT_SERVICELIFE.toString() == valueToSet) {
                                        if ("default".equals(d.additionalQuestionAnswers?.get("serviceLife")) || !d.additionalQuestionAnswers?.get("serviceLife")) {
                                            Double serviceLife = newCalculationServiceProxy.getServiceLife(d, resourceCache.getResource(d), entity, null)

                                            if (serviceLife != null) {
                                                if (d.additionalQuestionAnswers) {
                                                    d.additionalQuestionAnswers.put("serviceLife", "${serviceLife}")
                                                } else {
                                                    d.additionalQuestionAnswers = [serviceLife: "${serviceLife}"]
                                                }
                                            }
                                        }
                                    } else if (Constants.DEFAULT_TRANSPORT.toString() == valueToSet) {
                                        if (d.additionalQuestionAnswers) {
                                            def value

                                            if (defaultValue) {
                                                ResourceType type = resourceService.getSubType(resourceCache.getResource(d))

                                                if (type) {
                                                    def resourceTypeAnswer = DomainObjectUtil.callGetterByAttributeName(defaultValue, type)

                                                    if (resourceTypeAnswer && resourceTypeAnswer.toString().isNumber()) {
                                                        value = resourceTypeAnswer.toString().toDouble()
                                                    }
                                                }
                                            }

                                            if (value != null) {
                                                com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().each { String transportQuestionId ->
                                                    if ("default".equals(d.additionalQuestionAnswers.get(transportQuestionId))) {
                                                        d.additionalQuestionAnswers.put((transportQuestionId), "${value}")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        entity.merge(failOnError: true)
                    }
                }
                parentEntity = parentEntity.merge(flush: true, failOnError: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in archiving entity ${parentEntity?.name}", e)
            flashService.setErrorAlert("Error in archiving entity ${parentEntity?.name}: ${e.getMessage()}", true)
        }
        return parentEntity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def unarchive(entityId) {
        Entity parentEntity

        try {
            parentEntity = getEntityById(entityId)

            if (parentEntity) {
                parentEntity.archived = null
                parentEntity.superArchived = null

                if (parentEntity.childEntities) {
                    parentEntity.childEntities.each {
                        it.locked = null
                        it.superLocked = null
                    }

                    parentEntity.childrenByChildEntities?.each { Entity entity ->
                        entity.locked = null
                        entity.superLocked = null
                        entity.merge(failOnError: true)
                    }
                }
                parentEntity = parentEntity.merge(failOnError: true)
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Error in unarchiving entity ${parentEntity?.name}", e)
            flashService.setErrorAlert("Error in unarchiving entity ${parentEntity?.name}: ${e.getMessage()}", true)
        }
        return parentEntity
    }

    def operatingPeriodAlreadyFound(Entity childEntity) {
        def alreadyFound = false

        if (EntityClass.OPERATING_PERIOD.toString() == childEntity.entityClass) {
            Entity parent = childEntity.getParentById()

            if (parent) {
                def children = parent.getChildEntities()

                if (childEntity.id) {
                    if (parent?.getOperatingPeriods()?.find({
                        it.operatingPeriodAndName.equals(childEntity.operatingPeriodAndName) &&
                                !it.id.equals(childEntity.id)
                    })) {
                        alreadyFound = true
                    }
                } else {
                    if (parent?.getOperatingPeriods()?.find({
                        it.operatingPeriodAndName.equals(childEntity.operatingPeriodAndName)
                    })) {
                        alreadyFound = true
                    }
                }
            }
        }
        return alreadyFound
    }

    def designAlreadyFound(Entity childEntity) {
        def alreadyFound = false

        if (EntityClass.DESIGN.toString() == childEntity.entityClass) {
            Entity parent = childEntity.getParentById()
            Boolean updating = false

            if (parent) {
                def children = parent.getChildEntities()

                if (childEntity.id && children) {
                    for (ChildEntity child in children) {
                        if (child.entityId == childEntity.id) {
                            updating = true
                            break
                        }
                    }
                }

                if (!updating) {
                    if (parent?.getDesigns()?.find({ it.name == childEntity.name })) {
                        alreadyFound = true
                    }
                }
            }
        }
        return alreadyFound
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def lockEntity(String entityId, Boolean superUserLock, Boolean persistDefaults = Boolean.FALSE) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity && !entity.locked) {
                entity.locked = Boolean.TRUE
                entity.superLocked = superUserLock
                entity.lockingTime = new Date()

                if (persistDefaults && entity.defaults && entity.datasets) {
                    ResourceCache resourceCache = ResourceCache.init(entity.datasets.toList())
                    for (Dataset d: entity.datasets) {
                        entity.defaults.each { String valueToSet, String defaultValue ->
                            if (Constants.DEFAULT_SERVICELIFE.toString() == valueToSet) {
                                if ("default".equals(d.additionalQuestionAnswers?.get("serviceLife")) || !d.additionalQuestionAnswers?.get("serviceLife")) {
                                    Double serviceLife = newCalculationServiceProxy.getServiceLife(d, resourceCache.getResource(d), entity, null)

                                    if (serviceLife != null) {
                                        if (d.additionalQuestionAnswers) {
                                            d.additionalQuestionAnswers.put("serviceLife", "${serviceLife}")
                                        } else {
                                            d.additionalQuestionAnswers = [serviceLife: "${serviceLife}"]
                                        }
                                    }
                                }
                            } else if (Constants.DEFAULT_TRANSPORT.toString() == valueToSet) {
                                if (d.additionalQuestionAnswers) {
                                    def value

                                    if (defaultValue) {
                                        ResourceType type = resourceService.getSubType(resourceCache.getResource(d))

                                        if (type) {
                                            def resourceTypeAnswer = DomainObjectUtil.callGetterByAttributeName(defaultValue, type)

                                            if (resourceTypeAnswer && resourceTypeAnswer.toString().isNumber()) {
                                                value = resourceTypeAnswer.toString().toDouble()
                                            }
                                        }
                                    }

                                    if (value != null) {
                                        com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().each { String transportQuestionId ->
                                            if ("default".equals(d.additionalQuestionAnswers.get(transportQuestionId))) {
                                                d.additionalQuestionAnswers.put((transportQuestionId), "${value}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.locked = Boolean.TRUE
                            childEntity.superLocked = superUserLock
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def verifyEntity(String entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity) {
                entity.superVerified = Boolean.TRUE
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.superVerified = Boolean.TRUE
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def unverifyEntity(String entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity) {
                entity.superVerified = Boolean.FALSE
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.superVerified = Boolean.FALSE
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def unlockEntity(String entityId, Boolean superUserUnlock) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity && entity.locked) {
                entity.locked = Boolean.FALSE

                if (superUserUnlock) {
                    entity.superLocked = Boolean.FALSE
                }
                entity.lockingTime = null
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.locked = Boolean.FALSE

                            if (superUserUnlock) {
                                childEntity.superLocked = Boolean.FALSE
                            }
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def addBenchmarks(entityId, portfolioIds, type) {
        Entity entity = getEntityById(entityId)

        if (entity && portfolioIds && type) {
            portfolioIds.each { portfolioId ->
                Portfolio portfolio = Portfolio.get(portfolioId)
                BenchmarkValue benchmark = new BenchmarkValue(type: type)
                benchmark.portfolio = portfolio
                benchmark = benchmark.save(flush: true)

                if (entity.benchmarkIds) {
                    entity.benchmarkIds.add(benchmark.id?.toString())
                } else {
                    entity.benchmarkIds = [benchmark.id?.toString()]
                }
            }
            entity = entity.merge(flush: true)
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def removeBenchmark(entityId, benchmarkId) {
        Entity entity = getEntityById(entityId)
        BenchmarkValue benchmark = BenchmarkValue.get(benchmarkId)

        if (entity && benchmark) {
            if (entity.benchmarkIds?.contains(benchmark.id.toString())) {
                entity.benchmarkIds.remove(benchmark.id.toString())
                entity.merge(flush: true)
            }
            benchmark.delete(flush: true)
        }
    }

    def removeEntityFile(String fileId) {
        if (fileId) {
            EntityFile file = EntityFile.get(fileId)

            if (file) {
                file.delete(flush: true)
            }
        }
    }

    def getMapEntityById(mapEntityId) {
        if (mapEntityId) {
            return MapEntity.get(mapEntityId)
        }
    }

    def setUserIndicators(String entityId, String userId, List<String> indicatorIds) {
        if (entityId && userId) {
            Entity entity = Entity.get(entityId)

            if (entity) {
                if (indicatorIds) {
                    if (entity.userIndicators) {
                        entity.userIndicators.put(userId, indicatorIds.unique())
                    } else {
                        entity.userIndicators = [(userId): indicatorIds.unique()]
                    }
                } else {
                    entity.userIndicators?.remove(userId)
                }
                entity.merge(flush: true)
            }
        }
    }

    def getEntitiesByIndicator(String indicatorId) {
        def entityClasses = optimiResourceService.getParentEntityClassIds()
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

        if (!indicator) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.FALSE)
        }
        List<Entity> entities

        if (indicator?.compatibleEntityClasses) {
            entities = Entity.collection.find([indicatorIds: [$in: [indicatorId]], deleted: [$ne: true], $and: [[entityClass: [$in: entityClasses]], [entityClass: [$in: indicator.compatibleEntityClasses]]]])?.collect({ it as Entity })
        } else {
            entities = Entity.collection.find([indicatorIds: [$in: [indicatorId]], deleted: [$ne: true], entityClass: [$in: entityClasses]])?.collect({ it as Entity })
        }
        return entities
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntitiesByQueryUsage(String queryId) {
        Map<String, List<Dataset>> entitiesAndDatasets = [:]

        if (queryId) {
            Entity.withStatelessSession {
                List<Entity> entities = Entity.withCriteria {
                    datasets {
                        eq('queryId', queryId)
                    }
                }

                if (entities) {
                    if (entities) {
                        entities.each { Entity entity ->
                            if (entity.parentEntityId) {
                                entitiesAndDatasets.put("${entity.getParentById()?.name}, ${entity.operatingPeriodAndName}", entity.datasets.
                                        findAll({ Dataset d -> queryId.equals(d.queryId) })?.toList())
                            } else {
                                entitiesAndDatasets.put(entity.operatingPeriodAndName, entity.datasets.
                                        findAll({ Dataset d -> queryId.equals(d.queryId) })?.toList())
                            }
                        }
                    }
                }
            }
        }
        return entitiesAndDatasets
    }

    def searchEntitiesByName(String name) {
        List<Entity> entities
        String[] names = []

        if (name) {
            name = HtmlUtils.htmlUnescape(name.trim())
            name = name.replaceAll("[^a-zA-Z0-9 -]", "")
            names = name.split(" ")
        }

        if (name?.length() > 2 && names) {
            def entityClasses = optimiResourceService.getParentEntityClassIds()
            Map nameMap = new HashMap<String, Object>()
            String regex = ""

            names.each { it ->
                regex += ("(?=.*" + it + ")")

            }
            nameMap.put("\$options", "i");
            nameMap.put("\$regex", regex)
            BasicDBObject query = new BasicDBObject()
            Map nameQuery = new HashMap<String, Object>()
            Map countryQuery = new HashMap<String, Object>()
            Map addressQuery = new HashMap<String, Object>()
            nameQuery.put("name", nameMap)
            Document doc = new Document("\$elemMatch", [questionId: "country", resourceId: nameMap])
            Document addDoc = new Document("\$elemMatch", [basicQuery: "address", resourceId: nameMap])
            countryQuery.put("datasets", doc)
            addressQuery.put("datasets", addDoc)
            query.put("\$or", [nameQuery, countryQuery, addressQuery])
            query.put("deleted", [$ne: true])
            query.put("entityClass", [$in: entityClasses])
            def results = Entity.collection.find(query)

            if (results) {
                entities = results.collect({ it as Entity }).sort({ it.name.toLowerCase() })
            }
        }
        return entities
    }

    def getModifiableEntitiesByEntityClassAndQueryReady(String parentEntityClass, String childEntityClass, String queryId, Entity originalEntity = null, String indicatorId) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<Entity> foundEntities = []

        if (user && parentEntityClass && childEntityClass && queryId) {
            def entities = userService.getModifiableEntitiesForQuery(user.modifiableEntityIds, user.managedEntityIds)?.findAll({ Entity e -> parentEntityClass.equals(e.entityClass) })?.sort({ Entity e -> e.name.toLowerCase() })

            if (!entities && userService.isSystemAdmin(user) && originalEntity) {
                def managerAndModifierIds = []

                if (originalEntity.managerIds) {
                    managerAndModifierIds.addAll(originalEntity.managerIds)
                }

                if (originalEntity.modifierIds) {
                    managerAndModifierIds.addAll(originalEntity.modifierIds)
                }
                managerAndModifierIds = managerAndModifierIds?.unique()

                if (managerAndModifierIds) {
                    entities = Entity.collection.find([$or: [["managerIds": [$in: managerAndModifierIds]], ["modifierIds": [$in: managerAndModifierIds]]], "entityClass": parentEntityClass],
                            ["childEntities": 1])?.
                            collect({ it as Entity })?.unique({ it.id })
                }
            }

            if (entities) {
                entities.each { Entity entity ->
                    def children = getChildEntities(entity, childEntityClass, ["parentName": 1, "name": 1, "operatingPeriod": 1, "queryReady": 1, "queryReadyPerIndicator": 1])?.findAll({ Entity e -> (e.queryReady?.get(queryId) || e.queryReadyPerIndicator?.get(indicatorId)?.get(queryId)) })

                    if (children) {
                        foundEntities.addAll(children.sort({ Entity e -> e.operatingPeriodAndName.toLowerCase() }))
                    }
                }
            }
        }
        return foundEntities
    }

    /* (SW-1645) don't store the data in the entity, but calculate on the fly
    def updateProjectLevelEntity(List<String> projectLevelQueries, Entity parentEntity, Map<String, List<String>> queryAndLinkedIndicators, String entityId = null) {
        if (entityId) {
            Entity parent = getEntityById(entityId)
            parent.projectLevelQueries = projectLevelQueries
            parent.queryAndLinkedIndicators = queryAndLinkedIndicators
            parent = updateEntity(parent)
            return parent
        } else {
            parentEntity.projectLevelQueries = projectLevelQueries
            parentEntity.queryAndLinkedIndicators = queryAndLinkedIndicators
            parentEntity = updateEntity(parentEntity)
            return parentEntity
        }
    }
    */

    /**
     * Checks whether Compare is available for current user
     * @param parentEntity
     * @param selectedIndicators
     * @return
     */
    Boolean isCompareAvailableForCurrentUser(Entity parentEntity, List<Indicator> selectedIndicators) {

        Boolean isCompareAvailable = false

        if (!findMaterialSpecifierChildEntity(parentEntity)) {
            return isCompareAvailable
        }
        User user = userService.getCurrentUser()

        List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
        List<Feature> featuresAvailableForEntity = getFeatures(validLicenses)

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity,
                [Feature.COMPARE_MATERIAL], user, featuresAvailableForEntity, validLicenses)

        Boolean compareFeatureLicensed = featuresAllowed.get(Feature.COMPARE_MATERIAL) && indicatorService.addToCompareAllowedForIndicators(selectedIndicators)

        if (compareFeatureLicensed) {
            isCompareAvailable = true
        }

        return isCompareAvailable
    }

    /**
     * Determines the first unready project level QueryId for an entity and a list of indicators
     * @param entity
     * @param indicators
     * @return
     */
    String determineFirstQueryId(Entity entity, List<Indicator> indicators) {

        String firstQueryId = null

        if (!entity || !indicators) {
            return firstQueryId
        }

        List<String> projectLevelQueryIds = indicatorService.getProjectLevelQueryIdsForIndicators(indicators)
        firstQueryId = determineFirstQueryIdFromProjectLevelQueryIds(entity, projectLevelQueryIds)

        return firstQueryId
    }

    /**
     * Determines the first unready project level QueryId for an entity and a list of project level QueryIds
     * @param entity
     * @param projectLevelQueryIds
     * @return
     */
    String determineFirstQueryIdFromProjectLevelQueryIds(Entity entity, List<String> projectLevelQueryIds) {

        String firstQueryId = null

        if (!entity || !projectLevelQueryIds) {
            return firstQueryId
        }

        if (entity.queryReady) {
            for (String queryId in projectLevelQueryIds) {
                if (!entity.queryReady.get(queryId)) {
                    firstQueryId = queryId
                    break
                }
            }
        } else {
            firstQueryId = projectLevelQueryIds.first()
        }

        return firstQueryId
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def enableEntityAsTestDataset(String entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity && !entity.enableAsUserTestData) {
                entity.enableAsUserTestData = Boolean.TRUE
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.enableAsUserTestData = Boolean.TRUE
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def disableEntityAsTestDataset(String entityId) {
        Entity entity

        if (entityId) {
            entity = Entity.get(entityId)

            if (entity && entity.enableAsUserTestData) {
                entity.enableAsUserTestData = Boolean.FALSE
                entity = entity.merge(flush: true, failOnError: true)

                if (entity.parentEntityId) {
                    Entity parent = entity.getParentById()

                    if (parent) {
                        ChildEntity childEntity = parent.childEntities?.find({ entity.id.equals(it.entityId) })

                        if (childEntity) {
                            childEntity.enableAsUserTestData = Boolean.FALSE
                        }
                        parent.merge(flush: true)
                    }
                }
            }
        }
        return entity
    }

    def getAllTestEntities(String entityClass, List<String> indicatorIds) {
        def returnableEntities = []

        if (indicatorIds) {
            def entities = Entity.collection.find([enableAsUserTestData: true, entityClass: entityClass, deleted: false])?.collect({ it as Entity })

            if (entities) {
                entities.each {
                    if (it.parentById?.indicatorIds?.find({ indicatorIds.contains(it) })) {
                        returnableEntities.add(it)
                    }
                }
            }
        }
        return returnableEntities
    }

    def getChosenEntity(ObjectId parentEntityId) {
        Entity design = Entity.findByParentEntityIdAndChosenDesignAndDeleted(parentEntityId, Boolean.TRUE, Boolean.FALSE)
        if (design) {
            return design
        } else {
            return null
        }
    }

    def getChosenEntityForMainList(ObjectId parentEntityId) {
        return Entity.collection.findOne([parentEntityId: parentEntityId, chosenDesign: true, deleted: [$ne: true]], [calculationTotalResults: 1])
    }

    def getEntitiesFromIds(List<String> entityIds) {
        List<Entity> entities = []
        if (entityIds) {
            entities = Entity.findAllByIdInList(entityIds).sort({ it.ribaStage })
        }
        return entities
    }

    def getEntityFromId(String entityId) {
        Entity entity = null

        if (entityId) {
            entity = Entity.findById(entityId)
        }

        return entity
    }

    def getLogoCertificationByEntityQueryIdSectionIdAndQuestion(String resourceId) {
        String imgLink = ""

        if (resourceId) {
            resourceId = resourceId.toLowerCase()
            if (resourceId.startsWith('leed')) {
                imgLink = "logoCertificate/leed.png"
            } else if (resourceId.startsWith('hqe')) {
                imgLink = "logoCertificate/hqe.png"
            } else if (resourceId.startsWith('dgnb') || resourceId.startsWith('bnb')) {
                imgLink = "logoCertificate/dgnb.png"
            } else if (resourceId.startsWith('breeam')) {
                imgLink = "logoCertificate/breeamuk.png"
            } else if (resourceId.startsWith('beprc')) {
                imgLink = "logoCertificate/beprc.png"
            }
        }
        return imgLink
    }

    def getCarbonHeroesWithEntityIds(List<ObjectId> entityIds) {
        List<Document> carbonHeroes

        if (entityIds) {
            carbonHeroes = Entity.collection.find([_id: [$in: entityIds], carbonHero: true, deleted: [$ne: true]])?.toList()
        }
        return carbonHeroes
    }

    def resolveDisabledSections(Entity entity) {
        List<String> disabledSections = []

        if (entity) {
            def projectScopeList = entity.scope?.lcaCheckerList ?: []
            def map = scopeFactoryService.getServiceImpl(entity.entityClass).getMapSectionToProjectScope()
            List<String> allSectionIds = scopeFactoryService.getServiceImpl(entity.entityClass).getProjectScopeSectionIds() ?: []

            if (entity.scope) {
                if (projectScopeList) {
                    allSectionIds.each {
                        if (!projectScopeList.contains(it)) {
                            disabledSections.addAll(map.get(it) ?: [])
                        }
                    }
                } else {
                    allSectionIds.each {
                        disabledSections.addAll(map.get(it) ?: [])
                    }
                }

                List<String> entitySectionIds = entity?.datasets?.sectionId?.unique() ?: []

                entitySectionIds.each { sectionId ->
                    def scope = map.find { it.value?.contains(sectionId) }?.key
                    if (scope) projectScopeList.add(scope)
                }

                entity.scope.lcaCheckerList = projectScopeList.unique()
            }

        }

        return disabledSections
    }

    def putNewLine(String s, int limit) {

        if (!s || s.size() < limit) return s

        List<String> list = s.split(" ").toList()

        String res = ""
        int i = 1
        while (list) {
            String word = list.remove(0)
            res += " " + word
            if (res.size() > (i * limit)) {
                res += "<br>"
                i++
            }
        }

        return res
    }

    List<String> getIndicatorsToDisable(Entity entity, Indicator firstIndicator, List<String> indicatorIds) {

        def compatibleIndicatorIds = firstIndicator?.compatibleIndicatorIds
        List<String> newIndicatorList = entity.indicatorIds ? indicatorIds.findAll { !entity.indicatorIds.contains(it) } : indicatorIds

        if (compatibleIndicatorIds) {
            return newIndicatorList?.findAll { !compatibleIndicatorIds.contains(it) } ?: []

        } else {
            return newIndicatorList?.findAll {
                def i = indicatorService.getIndicatorByIndicatorId(it, true)
                def compList = i?.compatibleIndicatorIds
                if (!compList)
                    return false
                else
                    return !compList.contains(firstIndicator?.indicatorId)
            } ?: []
        }

    }

    def getModifiableEntitiesByEntityClassAndQueryReadyAsMap(String parentEntityClass, String childEntityClass, String queryId, Entity originalEntity = null, String indicatorId, String originalDesign) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        Map<String, List<Entity>> mapForFoundEntities = [:]
        def managerAndModifierIds = []
        def idsForPublic = []
        if (user && parentEntityClass && childEntityClass && queryId && !userService.getAccounts(user)?.isEmpty() && indicatorId) {

            Account userOrganization = userService.getAccounts(user)?.get(0)

            //______MyEntity_______
            def myEntities = userService.getModifiableEntitiesForQueryAsJson(user.modifiableEntityIds, user.managedEntityIds)?.findAll({ parentEntityClass.equals(it.entityClass) && it?.indicatorIds?.contains(indicatorId) })?.sort({ it.name.toLowerCase() })
            if (!myEntities && userService.isSystemAdmin(user) && originalEntity) {


                if (originalEntity.managerIds) {
                    managerAndModifierIds.addAll(originalEntity.managerIds)
                }

                if (originalEntity.modifierIds) {
                    managerAndModifierIds.addAll(originalEntity.modifierIds)
                }
                managerAndModifierIds = managerAndModifierIds?.unique()

                if (managerAndModifierIds) {
                    myEntities = Entity.collection.find([$or: [["managerIds": [$in: managerAndModifierIds]], ["modifierIds": [$in: managerAndModifierIds]]], "deleted": ["\$ne": true], "entityClass": parentEntityClass], ["id": 1, "name": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()?.unique({ it?.id })?.sort({ it?.name?.toLowerCase() })
                }
            } else if (myEntities) {
                managerAndModifierIds = myEntities.collect({ it.id })
            }
            if (myEntities) {
                mapForFoundEntities.put("myEntity", myEntities)

            }


            //_______MyOrganization-READONLY ENTITY_________


            def entities = userService.getReadOnlyEntitiesForQueryAsJson(user.readonlyEntityIds)?.findAll({ parentEntityClass.equals(it.entityClass) && it?.indicatorIds?.contains(indicatorId) })?.sort({ it.name.toLowerCase() })
            if (entities) {
                mapForFoundEntities.put("organizationEntity", entities)
            }

            //____PublicEntity____
            if (originalEntity.isFeatureLicensed(Feature.ALLOW_IMPORT_PUBLIC_ENTITIES)) {
                def publicEntities = Entity.collection.find([isPublic: true, "deleted": ["\$ne": true]],
                        ["id": 1, "name": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()?.findAll({ parentEntityClass.equals(it.entityClass) && it?.indicatorIds?.contains(indicatorId) })?.sort({ it?.name })

                if (publicEntities) {
                    mapForFoundEntities.put("publicEntity", publicEntities)
                }
            }

        }
        return mapForFoundEntities
    }

    Map<String, Map<String, List<Document>>> getChildrenByEntityClassAndQueryReadyWithCustomClass(String parentEntityClass, String childEntityClass, Entity originalEntity = null, List<String> indicatorIds, String originalDesign, Set<String> calculationClassList = null, Boolean allowImportPublic = false) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        Map<String, Map<String, List<Document>>> mapForFoundEntities = [:]
        def managerAndModifierIds = []
        List<Document> myDesigns = []
        List<Document> otherDesigns = []
        List<Document> publicDesigns = []
        if (user && parentEntityClass && childEntityClass && !userService.getAccounts(user)?.isEmpty() && indicatorIds) {
            List<Document> myEntities = userService.getModifiableEntitiesAsDocument(user.modifiableEntityIds, user.managedEntityIds)?.findAll({ it ->
                if (parentEntityClass.equalsIgnoreCase(it.entityClass)) {
                    if (it?.indicatorIds && CollectionUtils.containsAny(it?.indicatorIds, indicatorIds)) {
                        return true
                    } else {
                        return indicatorIds?.contains(com.bionova.optimi.core.Constants.COMPARE_INDICATORID)
                    }
                } else {
                    return false
                }
            })?.sort({ it.name.toLowerCase() })
            if (!myEntities && userService.isSystemAdmin(user) && originalEntity) {
                if (originalEntity.managerIds) {
                    managerAndModifierIds.addAll(originalEntity.managerIds)
                }
                if (originalEntity.modifierIds) {
                    managerAndModifierIds.addAll(originalEntity.modifierIds)
                }

                if (managerAndModifierIds) {
                    myEntities = Entity.collection.find([$or: [["managerIds": [$in: managerAndModifierIds?.unique()]], ["modifierIds": [$in: managerAndModifierIds]]], "deleted": ["\$ne": true], "entityClass": parentEntityClass], ["_id": 1, "name": 1, "childEntities": 1, "entityClass": 1])?.toList()?.sort({ it?.name?.toLowerCase() })
                }
            }
            if (myEntities) {
                myEntities = myEntities.findAll({ !indicatorService.getIndicatorsByIndicatorIds(it.indicatorIds, true)?.find({ Indicator ind -> ind.preventCopyingQueryContentsFromIndicator }) })
                List<String> myDesignIds = myEntities.collect({ it.childEntities })?.flatten()?.findAll({ it != null && !it.entityId?.toString()?.equals(originalDesign) })?.collect({ it?.entityId })
                myDesigns = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(myDesignIds)], "deleted": ["\$ne": true], "entityClass": childEntityClass], ["_id": 1, "name": 1, operatingPeriod: 1, "indicatorIds": 1, "entityClass": 1, "calculationClassficationId": 1, "queryLastUpdateInfos": 1, parentEntityId: 1, parentName: 1, superVerified: 1])?.toList()
                Map<String, List<Document>> mapClassMyDesigns = [:]
                if (myDesigns) {
                    calculationClassList?.each { String classId ->
                        mapClassMyDesigns.put(classId, myDesigns.findAll({ classId.equals(it.calculationClassficationId) })?.sort({ it.parentEntityId }))
                    }
                    mapClassMyDesigns.put("other", myDesigns.findAll({ !it.calculationClassficationId }))

                    mapForFoundEntities.put("myEntity", mapClassMyDesigns)
                }
            }
            //_______MyOrganization-READONLY ENTITY_________


            List<Document> otherEntities = userService.getReadOnlyEntitiesForQueryAsDocument(user.readonlyEntityIds)?.findAll({ it ->
                if (it.indicatorIds && parentEntityClass.equals(it.entityClass) && CollectionUtils.containsAny(it?.indicatorIds, indicatorIds)) {
                    return true
                }
                return false
            })
            if (otherEntities) {
                otherEntities = otherEntities.findAll({ !indicatorService.getIndicatorsByIndicatorIds(it.indicatorIds, true)?.find({ Indicator ind -> ind.preventCopyingQueryContentsFromIndicator }) })

                List<String> otherDesignIds = otherEntities.collect({ it.childEntities })?.flatten()?.findAll({
                    it != null && !it.entityId?.toString()?.equals(originalDesign)
                })?.collect({ it?.entityId })
                otherDesigns = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(otherDesignIds)], "deleted": ["\$ne": true], "entityClass": childEntityClass], ["_id": 1, "name": 1, operatingPeriod: 1, "indicatorIds": 1, "entityClass": 1, "calculationClassficationId": 1, "queryLastUpdateInfos": 1, parentEntityId: 1, parentName: 1, superVerified: 1])?.toList()
                Map<String, List<Document>> mapClassOtherDesigns = [:]
                if (otherDesigns) {
                    calculationClassList?.each { String classId ->
                        mapClassOtherDesigns.put(classId, otherDesigns.findAll({
                            classId.equals(it.calculationClassficationId)
                        })?.sort({ it.parentEntityId }))
                    }
                    mapClassOtherDesigns.put("other", otherDesigns.findAll({ !it.calculationClassficationId }))

                    mapForFoundEntities.put("organizationEntity", mapClassOtherDesigns)
                }
            }


            //____PublicEntity____
            if (allowImportPublic || originalEntity?.isFeatureLicensed(Feature.ALLOW_IMPORT_PUBLIC_ENTITIES)) {

                List<Document> publicEntities = Entity.collection.find([isPublic: true, "deleted": ["\$ne": true], "entityClass": parentEntityClass, "indicatorIds": [$in: indicatorIds]],
                        ["id": 1, "name": 1, "childEntities": 1, "entityClass": 1, "indicatorIds": 1])?.toList()

                if (publicEntities) {
                    publicEntities = publicEntities.findAll({ !indicatorService.getIndicatorsByIndicatorIds(it.indicatorIds, true)?.find({ Indicator ind -> ind.preventCopyingQueryContentsFromIndicator }) })

                    List<String> publicDesignIds = publicEntities.collect({ it.childEntities })?.flatten()?.findAll({ it != null && !it.entityId?.toString()?.equals(originalDesign) })?.collect({ it?.entityId })
                    publicDesigns = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(publicDesignIds)], "deleted": ["\$ne": true], "entityClass": childEntityClass], ["_id": 1, "name": 1, operatingPeriod: 1, "indicatorIds": 1, "entityClass": 1, "calculationClassficationId": 1, "queryLastUpdateInfos": 1, parentEntityId: 1, parentName: 1, superVerified: 1])?.toList()
                    Map<String, List<Document>> mapClassPublicDesigns = [:]
                    if (publicDesigns) {
                        calculationClassList?.each { String classId ->
                            mapClassPublicDesigns.put(classId, publicDesigns.findAll({ classId.equals(it?.calculationClassficationId) })?.sort({ it?.parentEntityId }))
                        }
                        mapClassPublicDesigns.put("other", publicDesigns.findAll({ !it?.calculationClassficationId }))

                        mapForFoundEntities.put("publicEntity", mapClassPublicDesigns)
                    }
                }
            }


            //____VerifiedEntity____
            if (userService.getSuperUser(user)) {
                Map<String, List<Document>> mapClassVerifiedDesigns = [:]
                List<Document> verifiedDesigns = ([*myDesigns, *publicDesigns, *otherDesigns] as List<Document>).findAll { it.superVerified }
                if (verifiedDesigns) {
                    calculationClassList?.each { String classId ->
                        mapClassVerifiedDesigns.put(classId, verifiedDesigns.findAll({ classId.equals(it?.calculationClassficationId) })?.sort({ it?.parentEntityId }))
                    }
                    mapClassVerifiedDesigns.put("other", verifiedDesigns.findAll({ !it.calculationClassficationId }))
                    // to render Verified designs first in the option list
                    mapForFoundEntities = ["verifiedEntities": mapClassVerifiedDesigns, *: mapForFoundEntities]
                }
            }
        }

        return mapForFoundEntities
    }
// FOLLOWING FUNCTION IS DEPRECATED, will remove after release for safety
/*
    def getCopyableParentsByEntityClassAndQueryReady(String parentEntityClass, String childEntityClass, String queryId, Entity originalEntity = null, String indicatorId, String originalDesign) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<Document> parents = []
        def managerAndModifierIds = []

        if (user && parentEntityClass && childEntityClass && queryId && !user.getAccounts()?.isEmpty() && indicatorId) {
            List<Document> myEntities = user.getModifiableEntitiesAsDocument()?.findAll({ parentEntityClass.equalsIgnoreCase(it.entityClass) && it?.indicatorIds?.contains(indicatorId)})
            if(!myEntities && user.systemAdmin && originalEntity){
                if (originalEntity.managerIds) {
                    managerAndModifierIds.addAll(originalEntity.managerIds)
                }
                if (originalEntity.modifierIds) {
                    managerAndModifierIds.addAll(originalEntity.modifierIds)
                }

                if (managerAndModifierIds) {
                    myEntities = Entity.collection.find([$or: [["managerIds": [$in: managerAndModifierIds?.unique()]], ["modifierIds": [$in: managerAndModifierIds]]],"deleted": ["\$ne": true], "entityClass": parentEntityClass], ["_id":1, "name": 1, "childEntities": 1, "entityClass": 1])?.toList()
                }
            }
            if(myEntities){
                parents.add(myEntities)
            }
            //_______MyOrganization-READONLY ENTITY_________


            List<Document> otherEntities = user?.getReadOnlyEntitiesForQueryAsDocument()?.findAll({ parentEntityClass.equals(it.entityClass) && it?.indicatorIds?.contains(indicatorId)})?.toList()
            if(otherEntities) {
                parents.add(otherEntities)
            }


            //____PublicEntity____
            if(originalEntity.isFeatureLicensed(Feature.ALLOW_IMPORT_PUBLIC_ENTITIES)){
                List<Document> publicEntities = Entity.collection.find([isPublic: true, "deleted": ["\$ne": true], "entityClass": parentEntityClass, indicatorIds: indicatorId],
                        ["id":1, "name": 1, "childEntities": 1, "entityClass": 1, "indicatorIds":1])?.toList()

                if(publicEntities) {
                    parents.add(publicEntities)
                }
            }

        }
        return parents?.flatten()
    }
*/

    def getDisplayResultForMainList(Document entity, Indicator indicator) {
        Double score

        if (entity && indicator) {
            score = entity.calculationTotalResults?.find({ indicator.indicatorId.equals(it.indicatorId) })?.totalByCalculationRule?.get(indicator.displayResult)
        }
        return score

    }

    def queryModifiable(Entity parentEntity, Entity entity, Indicator indicator, List<License> validLicenses, Boolean projectLevel = Boolean.FALSE, Boolean isMaterialSpecQueryAndLicensed = Boolean.FALSE) {
        String error = null
        User user = userService.getCurrentUser()

        if (userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user) || userService.getConsultant(user)) {
            error = null
        } else {
            if (projectLevel) {
                if (validLicenses) {
                    if (validLicenses.find({ it.allowedForUser(user) })) {
                        error = null
                    } else {
                        error = "readonly"
                    }
                } else {
                    error = "readonly"
                }
            } else {
                if (entity && parentEntity && indicator) {
                    if (validLicenses) {
                        if (parentEntity.readonlyIndicatorIds?.contains(indicator.indicatorId)) {
                            error = "fail"
                        } else if (entity.locked || entity.superLocked) {
                            error = "locked"
                        } else {
                            if (user && user.id) {
                                if (parentEntity.readonlyUserIds?.contains(user.id.toString()) && !parentEntity.modifierIds?.contains(user.id.toString()) && !parentEntity.managerIds?.contains(user.id.toString())) {
                                    error = "readonly"
                                } else {
                                    if (!isMaterialSpecQueryAndLicensed) {
                                        if (validLicenses.find({ it.projectBased })) {
                                            error = null
                                        } else if (userService.getLicenses(user)?.find({ it.userBased && (!it.isFloatingLicense || it.isUserFloating(user)) && (it.licensedIndicatorIds?.contains(indicator.indicatorId)) })) {
                                            error = null
                                        } else if (validLicenses.find({ it.isFloatingLicense && it.isUserFloating(user) && it.licensedIndicatorIds?.contains(indicator.indicatorId) })) {
                                            error = null
                                        } else {
                                            error = "readonly"
                                        }
                                    }

                                }
                            } else {
                                error = "fail"
                            }
                        }
                    } else {
                        if (userService.getLicenses(user)?.find({ it.userBased && (!it.isFloatingLicense || it.isUserFloating(user)) && it.licensedIndicatorIds?.contains(indicator.indicatorId) })) {
                            error = null
                        } else {
                            error = "readonly"
                        }
                    }
                } else {
                    error = "fail"
                }
            }
        }
        return error
    }

    Map<String, String> renderPopupTableForTimeUpdate(List<Entity> designs) {
        Map<String, List<MapUserUpdate>> designUpdateTimes = [:]
        Map<String, String> stringTableMap = [:]
        if (designs) {
            designs.each { Entity des ->
                def lastUpdate = des.queryLastUpdateInfos?.collectMany { it.value?.updates ?: [] }
                def lastUpdateName = des.queryLastUpdateInfos?.collectMany { it.value?.usernames ?: [] }

                List<MapUserUpdate> listTuple = []
                if (lastUpdate && lastUpdateName && (lastUpdate.size() == lastUpdateName.size())) {
                    for (int i = 0; i < lastUpdate.size(); i++) {
                        listTuple.add(new MapUserUpdate(userName: lastUpdateName.get(i), update: lastUpdate.get(i)))
                    }
                    listTuple = listTuple.sort({ it.update }).takeRight(3)
                    designUpdateTimes.put(des.id.toString(), listTuple)
                }
            }


            if (designUpdateTimes) {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy")
                designUpdateTimes.each { designId, updateTimes ->
                    String tableClass = "table table-striped"
                    String dateCreatedString = "Date created"
                    String lastUpdatedString = "Last updated"
                    def dateCreated = designs.find({ it.id.toString().equalsIgnoreCase(designId) })?.dateCreated
                    String tableRows = "<tr><td><strong>${dateCreatedString}</strong></td><td>${dateCreated ? dateFormat.format(dateCreated) : ''}</td></tr>" +
                            "<tr><td colspan=\'2\'><strong>${lastUpdatedString}</strong></td></tr>"
                    updateTimes.sort({ it.update }).reverse().each { MapUserUpdate mapUserUpdate ->
                        tableRows += "<tr><td>${mapUserUpdate.userName?.encodeAsHTML()}</td><td>${dateFormat.format(mapUserUpdate.update)}</td></tr>"
                    }
                    def stringTable = """
                    <br>
                    <table class='${tableClass}'>
                        $tableRows
                    </table>"""
                    stringTableMap.put(designId, stringTable)

                }
            }

        }

        return stringTableMap
    }

    def getMaterialSpecifierEntity(Entity parentEntity) {
        Entity entity = null

        if (parentEntity) {
            ChildEntity childMaterialSpecifier = findMaterialSpecifierChildEntity(parentEntity)
            if (childMaterialSpecifier) {
                log.info("SAVED MATERIAL SPECIFIER ENTITY FOUND!")
                entity = childEntityService.getEntity(childMaterialSpecifier.entityId)
            } else {
                Entity materialSpecifier = new Entity()
                materialSpecifier.name = "Compare data"
                materialSpecifier.parentEntityId = parentEntity.id
                materialSpecifier.entityClass = EntityClass.MATERIAL_SPECIFIER.type.toString()
                entity = createEntity(materialSpecifier, parentEntity)
                log.info("NEW MATERIAL SPECIFIER ENTITY CREATED!")
            }
        }
        return entity
    }

    ChildEntity findMaterialSpecifierChildEntity(Entity parentEntity) {
        parentEntity.childEntities?.find { EntityClass.MATERIAL_SPECIFIER.type.toString().equals(it.entityClass) }
    }

    private Map<String, Boolean> areResourcesAlreadyAdded(List<String> resourceIds, Entity entity, String queryId) {
        Map<String, Boolean> resourcesChecked = [:]
        List<String> resourceIdsList = entity?.datasets?.toList()?.findAll({ it.queryId == queryId }).collect({ it.resourceId }) ?: []
        resourceIds?.each { String resourceId ->
            resourcesChecked.put(resourceId, resourceIdsList.contains(resourceId))
        }
        return resourcesChecked
    }

    void setProjectWithDefaultLCAParameters(Entity parentEntity) {
        if (parentEntity) {
            AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib')
            Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            List<Question> additionalQuestions = additionalQuestionsQuery?.getAllQuestions()

            Query lcaQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID, true)
            List<Dataset> projectLevelDatasets = []
            Map<String, String> defaults = [:]

            lcaQuery.getAllQuestions()?.each { Question question ->
                def defaultValue
                if (question.choices) {
                    if (question.defaultValue) {
                        defaultValue = question.choices.find({ it.answerId == question.defaultValue })?.answerId
                    } else if (question.defaultValueFromCountryResource) {
                        String value = question.getCountryResourceDefault(parentEntity)

                        if (value) {
                            defaultValue = question.choices.find({ it.answerId == value })?.answerId
                        }
                    } else if (question.defaultValueFromUser) {
                        String value = questionService.getDefaultValueFromUserAttribute(question.defaultValueFromUser)

                        if (value) {
                            defaultValue = question.choices.find({ it.answerId == value })?.answerId
                        }
                    }
                } else if (question.defaultValueFromCountryResource) {
                    defaultValue = question.getCountryResourceDefault(parentEntity)
                } else if (question.defaultValueFromUser) {
                    defaultValue = questionService.getDefaultValueFromUserAttribute(question.defaultValueFromUser)
                }

                if (defaultValue) {
                    if ("transportationDefault".equals(question.questionId)) {
                        defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT), defaultValue)
                    } else if ("serviceLifeDefault".equals(question.questionId)) {
                        defaults.put((com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE), defaultValue)
                    }
                    Dataset d = new Dataset()
                    d.manualId = new ObjectId().toString()
                    d.queryId = lcaQuery.queryId
                    d.sectionId = question.sectionId
                    d.questionId = question.questionId
                    d.answerIds = [defaultValue]
                    d.projectLevelDataset = Boolean.TRUE

                    if (question.resourceGroups) {
                        d.resourceId = defaultValue
                    }

                    if (question.additionalQuestionIds) {
                        d.additionalQuestionAnswers = [:]
                        question.additionalQuestionIds.each { String additionalQuestionId ->
                            Question additionalQuestion = additionalQuestions?.find({ additionalQuestionId.equals(it.questionId) })

                            if (additionalQuestion) {
                                if (com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID.equals(additionalQuestionId)) {
                                    String targetDefaultResourceId = optimiResourceService.getResourceWithParams(defaultValue, "default")?.countryEnergyResourceId

                                    List<Document> allChoises = additionalQuestion.getAdditionalQuestionResources(null, null)?.findAll({ it.areas && it.areas.contains(defaultValue) })?.sort({ it.defaultProfile })?.reverse()
                                    Document p = allChoises?.find({ targetDefaultResourceId == it.resourceId && it.defaultProfile }) ?: !allChoises?.isEmpty() ? allChoises?.first() : null

                                    if (p) {
                                        String valueString = "${p.resourceId}.${p.profileId}"
                                        d.additionalQuestionAnswers.put((additionalQuestionId), valueString)
                                    }
                                } else if ("profileId".equals(additionalQuestionId)) {
                                    String additionalQuestionAnswer = optimiResourceService.getResourceWithParams(defaultValue)?.profileId

                                    if (additionalQuestionAnswer) {
                                        d.additionalQuestionAnswers.put((additionalQuestionId), additionalQuestionAnswer)
                                    }
                                }
                            }
                        }
                    }
                    projectLevelDatasets.add(d)
                }
            }

            if (projectLevelDatasets) {
                if (parentEntity.datasets) {
                    parentEntity.datasets.removeIf({ it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID })
                    parentEntity.datasets.addAll(projectLevelDatasets)
                } else {
                    parentEntity.datasets = projectLevelDatasets
                }

                parentEntity.childrenByChildEntities?.each {
                    if (it.datasets) {
                        it.datasets.removeIf({ it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID })
                        it.datasets.addAll(projectLevelDatasets)
                    } else {
                        it.datasets = projectLevelDatasets
                    }
                    it.defaults = defaults
                    it.save(flush: true)
                }
                parentEntity.resolveQueryReady(null, com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID)
                parentEntity.defaults = defaults
                parentEntity.save(flush: true)
            }
        }
    }

    def getDatasetAmountForEntityAndIndicator(String entityId, Indicator indicator) {
        int amount = 0

        if (entityId) {
            List<String> validQueryIds = indicator?.indicatorQueries?.collect({ it.queryId })
            def datasets = Entity.collection.findOne([_id: DomainObjectUtil.stringToObjectId(entityId)], [datasets: 1])?.datasets

            if (datasets) {
                amount = datasets.findAll({ !it.projectLevelDataset && validQueryIds?.contains(it.queryId) })?.size() ?: 0
            }
        }
        return amount

    }

    //=== Temp Method/Code for isolating useCases for demo account being added.
    def isUserAddedToDemoProject(Entity entity, User userAdded, String methodMessage) {
        List<String> demoProjects = ["55522eb47d1e86c1cca605a7",
                                     "5565b5dd7d1eb6f3d1ae2b2e",
                                     "5b2a36428e202b04127cdec9",
                                     "5b2a38fa8e202b04127ce16b",
                                     "5b2a39b88e202b04127ce217",
                                     "5b2a3be58e202b04127ce5d2",
                                     "5b2a40778e202b04127ce950",
                                     "5b2a44cf8e202b04127cfbcb",
                                     "5b30d66d8e202b74e7eea91c",
                                     "5b30d8518e202b74e7eeaa1a",
                                     "5b30e0288e202b74e7eeb1f8",
                                     "5b30e0658e202b74e7eeb200",
                                     "5b30e08d8e202b74e7eeb207",
                                     "5b3235c48e202b74e7f0ecdf",
                                     "5b3237998e202b74e7f0f14e",
                                     "5b323d2a8e202b74e7f0f709",
                                     "591b385f5d96ff2e15050078"];

        if (demoProjects.contains(entity?.id.toString())) {
            log.error("DEMO-PROJECT - ${userAdded?.username} was added to demo project: ${entity?.name}. ${methodMessage}")
            flashService.setErrorAlert("DEMO-PROJECT - ${userAdded?.username} was added to demo project: ${entity?.name}. ${methodMessage}", true)
        }
    }

    Entity createOrGetProjectEntity(GrailsParameterMap params, HttpSession session, HttpServletRequest request, Query basicQuery = null) {
        Entity entity
        Boolean checkMandatory = params.boolean("checkMandatory")
        def entityClass = params.entityClass
        List<String> licenseIds = params.list("licenseId")
        String automaticTrial
        params.name = params.name?.replaceAll(com.bionova.optimi.core.Constants.USER_INPUT_REGEX, "")

        if (!entityClass) {
            entityClass = EntityClass.BUILDING.getType()
        }
        Boolean nameChanged = Boolean.FALSE

        if (params.entityId) {
            entity = getEntityById(params.entityId, session)
            String newName = params.name

            if (entity) {
                String name = entity.name
                entity.properties = params

                if (name && newName && !name.equals(newName.trim())) {
                    nameChanged = Boolean.TRUE
                }
            }
        } else {
            entity = new Entity(params)
        }

        if (entity) {
            entity.entityClass = entityClass
        }

        if (entity.validate()) {
            if (entity.id) {
                entity = updateEntity(entity, nameChanged)
                flashService.setFadeSuccessAlert("${entityClass?.capitalize()} ${entity?.name} ${stringUtilsService.getLocalizedText('entity.entity_updated')}")
            } else {
                entity = createEntity(entity)
                flashService.setFadeSuccessAlert("${entityClass?.capitalize()} ${entity?.name} ${stringUtilsService.getLocalizedText('entity.entity_created')}")
            }

            if (entity.id) {
                if (licenseIds) {
                    User user = userService.getCurrentUser(true)

                    licenseIds.each { String licenseId ->
                        License license = licenseService.getLicenseById(licenseId)

                        if (license) {
                            if (license.openTrial) {
                                if (((user.trialCount < 1 || user.trialCountForInfra < 1) || license.freeForAllEntities) && license.compatibleEntityClasses?.contains(entityClass)) {
                                    if (!licenseService.addEntity(licenseId, entity.id)) {
                                        flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('license.full'))
                                    } else {
                                        automaticTrial = license.name
                                    }
                                } else {
                                    flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('trial_done_no_prod'))
                                }
                            } else {
                                if (!licenseService.addEntity(licenseId, entity.id)) {
                                    flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('license.full'))
                                }
                            }
                        }
                    }
                    if (automaticTrial) {
                        if (user) {
                            if (entityClass.equalsIgnoreCase("infrastructure")) {
                                user.trialCountForInfra = user.trialCountForInfra ? user.trialCountForInfra += 1 : 1
                            } else if (entityClass.equalsIgnoreCase("building")) {
                                user.trialCount = user.trialCount ? user.trialCount += 1 : 1
                            }
                            userService.updateUser(user)
                        }
                    }
                }
            }
            basicQuery = basicQuery ?: queryService.getBasicQuery()

            if (basicQuery) {
                try {
                    entity = datasetService.saveDatasetsFromParameters(params, request, entity?.id?.toString(), session, null)
                    entity = calculateEntity(entity, params.indicatorId, params.queryId, false, false)
                } catch (Exception e) {
                    loggerUtil.warn(log, "Error in saving datasets: ${e}")
                    flashService.setFadeErrorAlert(stringUtilsService.getLocalizedText('entity.wrong_image_type'))

                }

                if (checkMandatory) {
                    Map mandatoryQuestions = basicQuery.getMandatoryQuestions(entity?.entityClass)

                    if (mandatoryQuestions) {
                        String missingAnswers
                        mandatoryQuestions.each { key, values ->
                            if (values) {
                                values.each { Question question ->
                                    Dataset dataset = entity.datasets?.find({ d -> d.queryId == basicQuery.queryId && d.questionId == question.questionId })

                                    if (!dataset) {
                                        if (!missingAnswers) {
                                            missingAnswers = "${question.localizedQuestion}"
                                        } else {
                                            missingAnswers = "${missingAnswers}, ${question.localizedQuestion}"
                                        }
                                    }
                                }
                            }
                        }

                        if (missingAnswers) {
                            flashService.setErrorAlert(stringUtilsService.getLocalizedText('entity.mandatory_answers_missing'))
                        }
                    }
                }
            }
        }
        return entity
    }

    void sendJoinCodeActivatedMail(License license) {
        List<User> managers = license?.managers
        if (managers) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
            User user = userService.getCurrentUser()
            String messageSubject = "Student ${user?.username} joined license ${license?.name}"
            String messageBody = "Your student ${user?.username} has joined the license ${license.name} with license key:" +
                    " ${license.licenseKey}. The license is valid until ${dateFormat.format(license.validUntil)}"

            managers.forEach({
                String emailTo = it?.username
                if (emailTo) {
                    optimiMailService.sendMail({
                        to emailTo
                        from "noreply@oneclicklca.com"
                        replyTo "noreply@oneclicklca.com"
                        subject(messageSubject)
                        html(messageBody)
                    }, emailTo)
                }
            })
        }
    }

    @Deprecated
    Boolean isComponentEntity(Entity entity) {
        return EntityClass.COMPONENT.type.equalsIgnoreCase(entity?.entityClass) || entity?.componentAbilityLicensed
    }

    Boolean isComponentEntity(Entity entity, List<Feature> entityFeatures) {
        return EntityClass.COMPONENT.type.equalsIgnoreCase(entity?.entityClass) || isComponentAbilityLicensed(entityFeatures)
    }

    /**
     *  Basically it's the same method as old one, but was added for new functionality as calc in background.
     *  It using overload method calculateOtherIndicatorsIfNeeded where you will have access to all childEntites
     *  and as result you can create CalculationProcess records for all design/indicator pairs.
     *
     *  One more benefits  of it:
     *  - only one call to DB for getting all child entities (previously we make a call for each)
     * @param parentEntity
     * @param queryId
     */
    void recalculateParentEntityNew(Entity parentEntity, String queryId) {
        List<String> childEntityIdsForCalculation = getChildEntitiesForParentProjectCalculation(parentEntity)?.entityId
        List<Entity> childEntitiesForCalculation = getEntitiesByIds(childEntityIdsForCalculation, parentEntity)
        newCalculationServiceProxy.calculateOtherIndicatorsIfNeeded(childEntitiesForCalculation, null, queryId, parentEntity)
    }

    void recalculateParentEntity(Entity parentEntity, String queryId) {
        getChildEntitiesForParentProjectCalculation(parentEntity)?.each { ChildEntity childEntity ->
            newCalculationServiceProxy.calculateOtherIndicatorsIfNeeded(childEntity.entityId?.toString(), null, queryId, parentEntity)
        }
    }

    List<ChildEntity> getChildEntitiesForParentProjectCalculation(Entity parentEntity){
        return parentEntity?.childEntities?.findAll {
            !it.deleted &&
                    (it.entityClass.equals(EntityClass.DESIGN.toString()) ||
                            it.entityClass.equals(EntityClass.CARBON_DESIGN.toString()) ||
                            it.entityClass.equals(EntityClass.MATERIAL_SPECIFIER.toString()))
        }
    }

    boolean getCarbonDesignerAndResultsOnlyLicensed(List<License> entityValidLicenses) {
        if (entityValidLicenses) {
            Boolean licensed = false
            for (License l in entityValidLicenses) {
                if (l.licensedFeatures?.find { Feature.CARBON_DESIGNER_RESULTS_REPORT.equals(it.featureId) }) {
                    licensed = true
                }
            }
            return licensed
        } else {
            return false
        }
    }

    Map<String, List<String>> getHiddenQuestionsOnProjectLevel(Entity entity, String queryId) {
        Map<String, List<String>> projectLevelHiddenQuestions = [:]

        if (!entity.parentEntityId) {
            Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = getIndicatorsByProjectLevelQueryIdsForCurrentUser(entity)

            List<Indicator> linkedIndicators = indicatorsByProjectLevelQueryIds?.get(queryId)

            linkedIndicators?.each { Indicator ind ->
                Map<String, List<String>> hideQuestions = ind.hideQuestions

                if (hideQuestions) {
                    hideQuestions.each { String key, List<String> values ->
                        if (linkedIndicators.findAll({ values == it.hideQuestions?.get(key) })?.size() == linkedIndicators.size()) {
                            projectLevelHiddenQuestions.put(key, values)
                        }
                    }
                }
            }
            /* SW-1645 keep the commented code until after release 0.6.0
            List<String> linkedIndicatorIds = entity.queryAndLinkedIndicators?.get(queryId)

            if (linkedIndicatorIds) {
                List<Indicator> linkedIndicators = indicatorService.getIndicatorsByIndicatorIds(linkedIndicatorIds, true)

                linkedIndicators?.each { Indicator ind ->
                    Map<String, List<String>> hideQuestions = ind.hideQuestions

                    if (hideQuestions) {
                        hideQuestions.each { String key, List<String> values ->
                            if (linkedIndicators.findAll({ values == it.hideQuestions?.get(key) })?.size() == linkedIndicators.size()) {
                                projectLevelHiddenQuestions.put(key, values)
                            }
                        }
                    }
                }
            }*/
        }

        return projectLevelHiddenQuestions
    }

    /**
     * Provides queryIds of project level queries for an entity
     *
     * This method makes db calls for many indicators, think twice before use, or extend the method!!!
     * @param entity
     * @return
     */
    List<String> getProjectLevelQueryIdsForCurrentUser(Entity entity) {

        List<String> projectLevelQueryIds = []

        if (!entity || entity.parentEntityId) {
            return projectLevelQueryIds
        }

        List<Indicator> parentEntityIndicators = indicatorService.getSelectedIndicatorsForCurrentUser(entity)
        projectLevelQueryIds = indicatorService.getProjectLevelQueryIdsForIndicators(parentEntityIndicators)

        return projectLevelQueryIds
    }

    /**
     * Provides a list of indicators by Project Level QueryIds for an entity
     *
     * This method makes db calls for many indicators, think twice before use, or extend the method!!!
     * @param entity
     * @return map contains the Project Level QueryIds with lists of indicators that have such query.
     */
    Map<String, List<Indicator>> getIndicatorsByProjectLevelQueryIdsForCurrentUser(Entity entity, Boolean skipCompareIndicator = true) {

        Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = [:]

        if (!entity || entity.parentEntityId) {
            return indicatorsByProjectLevelQueryIds
        }

        List<Indicator> parentEntityIndicators = indicatorService.getSelectedIndicatorsForCurrentUser(entity, skipCompareIndicator)
        indicatorsByProjectLevelQueryIds = indicatorService.groupIndicatorsByProjectLevelQueryIds(parentEntityIndicators)

        return indicatorsByProjectLevelQueryIds
    }


    @Secured(["ROLE_SUPER_USER"])
    void importDesign(MultipartFile jsonFile, String parentEntityId, String designName) {
        if (!parentEntityId) {
            flashService.setErrorAlert("Something went wrong. Missing parentEntityId, inform a developer")
            return
        }

        boolean ok = true
        Entity design = null

        try {
            File tempFile = File.createTempFile("entity", ".json")
            jsonFile.transferTo(tempFile)
            Map designMap

            try {
                designMap = mapper.readValue(tempFile, Map.class)
            } catch (IOException ioException) {
                log.error "Exception occurred during parsing of the JSON content:", ioException
                flashService.setErrorAlert("The imported file is not in the correct JSON format! " +
                                           "Exception occurred during parsing of the JSON content: ${ioException}. " +
                                           "Stacktrace: ${ioException.stackTrace}")

                return
            }

            if (!designMap) {
                flashService.setErrorAlert("Something went wrong or file might be empty. designMap cannot be generated, inform a developer")
                return
            }

            String entityClass = designMap.get('entityClass')
            if (!entityClass) {
                flashService.setErrorAlert("File is missing entityClass. Please upload JSON file exported from OCL only.")
                return
            } else if (entityClass != EntityClass.DESIGN.toString()) {
                flashService.setErrorAlert("Please upload JSON file for design only. Other entity classes are not supported.")
                return
            }

            Map<String, Map> queryLastUpdateInfosMap = designMap.get('queryLastUpdateInfos') as Map<String, Map>
            designMap.remove('queryLastUpdateInfos')

            for (String key in Entity.REMOVE_FROM_IMPORTED_JSON) {
                designMap.remove(key)
            }

            design = new Entity(designMap)
            design.name = designName
            design.parentEntityId = DomainObjectUtil.stringToObjectId(parentEntityId)

            if (queryLastUpdateInfosMap) {
                Map<String, QueryLastUpdateInfo> queryLastUpdateInfos = [:]
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+0000'")
                // date from mongodb is weird, hence need to parse manually
                queryLastUpdateInfosMap.each { String key, Map updateInfo ->
                    if (key && updateInfo) {
                        List<String> usernames = updateInfo.get('usernames') as List<String>
                        List<Date> updates = (updateInfo.get('updates') as List<String>)?.collect { format.parse(it) }
                        if (updates && usernames) {
                            queryLastUpdateInfos.put(key, new QueryLastUpdateInfo(usernames: usernames, updates: updates))
                        }
                    }
                }
                if (queryLastUpdateInfos) {
                    design.queryLastUpdateInfos = queryLastUpdateInfos
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred. Design import aborted.", e)
            flashService.setErrorAlert("Design import aborted. Error occurred: ${e}. Stacktrace: ${e.stackTrace}")
            ok = false
        }

        if (ok && design?.validate()) {
            design = createEntity(design, null, false)
            if (design?.id) {
                flashService.setFadeSuccessAlert("Imported successfully design ${design.name}.")
            }
        }
    }

    List<Indicator> getReportIndicators(Entity entity, List<License> validLicenses) {
        List<Indicator> indicators = []

        if (validLicenses) {
            for (License license in validLicenses) {
                List<Indicator> foundIndicators = license.getLicensedIndicators()?.findAll({ Indicator ind -> "report".equals(ind.indicatorUse) })

                if (foundIndicators) {
                    foundIndicators.each { Indicator ind ->
                        if (!indicators.contains(ind)) {
                            indicators.add(ind)
                        }
                    }
                }
            }
        }
        return indicators
    }

    List<Feature> getFeatures(List<License> validLicenses) {
        List<Feature> features

        if(validLicenses){
            List<String> licensedFeatureIds = validLicenses.licensedFeatureIds.flatten().unique()
            features = licenseService.getLicensedFeatures(licensedFeatureIds)
        }

        return features ?: []
    }

    boolean isBenchmarkLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.BENCHMARK
        }
    }

    boolean isCompareLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.OLD_SCHOOL_COMPARE
        }
    }

    boolean isOldGraphsLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.OLD_SCHOOL_GRAPHS
        }
    }

    boolean isSimulationToolLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.CARBON_DESIGNER
        }
    }

    boolean isWorkFlowLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.WORK_FLOW
        }
    }

    boolean isComponentAbilityLicensed(List<Feature> features) {
        return features?.any{ Feature feature ->
            feature.featureId == Feature.COMPONENT_ABILITY
        }
    }

    boolean hasSourceListing(Entity entity, Indicator indicator = null, List<String> queryIds = null) {

        boolean result = false

        if (indicator?.queriesForSourceListing) {
            queryIds = indicator.queriesForSourceListing
        } else {
            queryIds = queryIds ?: indicator?.getQueries(entity)?.collect({ Query q -> q.queryId })
        }

        if (queryIds) {
            result = entity?.datasets?.any({ Dataset d -> d.resourceId && queryIds.contains(d.queryId) && datasetService.getResource(d) })
        }

        return result
    }

    Map<String, List<CalculationResult>> getCalculationResultsObjects(List<Entity> entities, String indicatorId, String calculationRuleId, String resultCategoryId) {
        List<String> designIds = entities.collect{ it.id.toString() }
        List<CalculationResult> foundResults

        foundResults = CalculationResult.withCriteria{
            'in'("entityId", designIds)
            eq("indicatorId", indicatorId)

            if (calculationRuleId){
                eq("calculationRuleId", calculationRuleId)
            }

            if (resultCategoryId){
                eq("resultCategoryId", resultCategoryId)
            }
        }

        return foundResults.groupBy{it.entityId}
    }

    Boolean hasCalculationResultsObjects(Entity entity, String indicatorId, String calculationRuleId = null, String resultCategoryId = null) {
        hasCalculationResultsObjects(entity?.id?.toString(), indicatorId, calculationRuleId, resultCategoryId)
    }

    Boolean hasCalculationResultsObjects(String entityId, String indicatorId, String calculationRuleId = null, String resultCategoryId = null) {
        if (!entityId || !indicatorId) {
            return
        }

        BasicDBObject query = new BasicDBObject([entityId: entityId, indicatorId: indicatorId])

        if (calculationRuleId) {
            query.calculationRuleId = calculationRuleId
        }

        if (resultCategoryId) {
            query.resultCategoryId = resultCategoryId
        }

        return CalculationResult.collection.findOne(query, [_id: 1]).asBoolean()
    }

    Entity calculateEntity(Entity entity, String indicatorId, String queryId, boolean temporaryCalculation, boolean skipCalculation = false){
        if (indicatorId && !skipCalculation) {
            if (temporaryCalculation) {
                entity = newCalculationServiceProxy.calculate(null, indicatorId, null, entity)
            } else {
                Entity.withSession { def session ->
                    Entity parent = entity.getParentById()
                    entity = newCalculationServiceProxy.calculate(entity.id, indicatorId, parent)
                    loggerUtil.info(log, "calculation entity after one indicator: ${entity}")

                    if (entity) {
                        // Check that entity was not lost during calculation, e.g. deleted???
                        newCalculationServiceProxy.calculateOtherIndicatorsIfNeeded(entity.id.toString(), indicatorId, queryId, parent)
                    }

                    loggerUtil.info(log, "calculation entity after all indicators: ${entity}")
                }
            }
        }

        return entity
    }
    
    /**
     * Checks whether entity has missing required resources for the query
     * @param entity
     * @param indicator
     * @param queryId
     * @return
     */
    Boolean hasMissingRequiredResources(Entity entity, Indicator indicator, String queryId) {

        Boolean hasMissingRequiredResources = indicatorQueryService.getRequiredResourceIds(indicator, queryId)?.any { String requiredId ->
            Boolean hasRequiredResourceDataset = entity?.datasets?.any({ d ->
                return d.queryId.equals(queryId) && d.resourceId && d.resourceId == requiredId
            })
            return !hasRequiredResourceDataset
        }

        return hasMissingRequiredResources
    }

    List<License> getNonProdLicenses(Entity entity, Indicator indicator) {

        List<License> nonProdLicenses = []

        if (!entity || !indicator) {
            return nonProdLicenses
        }

        def licenses = entity?.getLicensesForIndicator(indicator)

        if (!licenses) {
            licenses = entity?.getExpiredLicensesForIndicator(indicator)
        }
        String prodType = LicenseType.PRODUCTION.toString()
        String demoType = LicenseType.DEMO.toString()
        nonProdLicenses = licenses?.findAll{ it.type != prodType && it.type != demoType} ?: []

        return nonProdLicenses
    }

    String prepareNotInFilterMessage(Entity entity, Indicator indicator, List<String> queryIds) {

        Map<String, Map<String, String>> notInFilterQueries = [:]
        Map<String, Map<String, String>> undefinedMaterialsQueries = [:]

        Set<Dataset> allEntityDatasets = datasetService.getDatasetsByEntity(entity)
        ResourceCache resourceCache = ResourceCache.init(allEntityDatasets as List)
        List<Query> queryList = queryService.getAllQueriesForDatasets(allEntityDatasets)
        Boolean notInFilter = Boolean.FALSE

        allEntityDatasets.each { Dataset dataset ->
            String queryId = dataset.queryId

            if (queryId && queryIds) {
                Query query = queryList.find{ it.queryId == queryId }

                if (queryIds.contains(queryId) && dataset?.resourceId) {
                    Resource resource = resourceCache.getResource(dataset)

                    if (resource?.decideLaterResource) {
                        undefinedMaterialsQueries.put(queryId, query.name)
                    } else {

                        // Do not validate constituents and dummy resources/constructions; validate constituents of the dummy (locally made) constructions.
                        if ((!dataset.parentConstructionId && !resource?.isDummy) || (dataset.isConstituent() && (dataset.parentConstructionId == Const.DUMMY_RESOURCE_ID))) {
                            notInFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, queryId, indicator, null, entity) ? Boolean.FALSE : Boolean.TRUE
                        }

                        if (notInFilter && query) {
                            notInFilterQueries.put(queryId, query.name)
                        }
                    }
                }
            }
        }

        String notInFilterMessage
        String undefinedMaterialsMessage

        if (notInFilterQueries || undefinedMaterialsQueries) {
            String locale = DomainObjectUtil.getMapKeyLanguage()

            if (notInFilterQueries) {
                notInFilterMessage = messageSource.getMessage('query.resource.warning.general.filterCriteriaFailureWithList',
                        [notInFilterQueries.collect { String queryId, Map<String, String> query -> query.get(locale) ?: query.get("EN") }.join(', ')] as Object[],
                        "Some of the used data are incompatible with the calculation tool. Please ensure that the data quality requirements are met: {0}.",
                        LocaleContextHolder.locale)
            }

            if (undefinedMaterialsQueries) {
                undefinedMaterialsMessage = "${messageSource.getMessage('query.resource.undefined_materials_warning', null, LocaleContextHolder.locale)}: " +
                        undefinedMaterialsQueries.collect { String queryId, Map<String, String> query ->
                            "${query.get(locale) ?: query.get("EN")}"
                        }.join(', ')
            }

            if (notInFilterMessage && undefinedMaterialsMessage) {
                notInFilterMessage = "${notInFilterMessage}<br/>${undefinedMaterialsMessage}"
            } else if (!notInFilterMessage && undefinedMaterialsMessage) {
                notInFilterMessage = "${undefinedMaterialsMessage}"
            }
        }

        return notInFilterMessage
    }
}
