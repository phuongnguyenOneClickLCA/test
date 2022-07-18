/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.construction.Constants.IndicatorUse
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.service.ChildEntityService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.frenchTools.helpers.RsetMapping
import com.bionova.optimi.util.DateUtil
import com.bionova.optimi.util.MapUtil
import com.mongodb.BasicDBObject
import grails.validation.Validateable
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpSession
import java.lang.reflect.Method
import java.text.DecimalFormat
import java.time.Duration

/**
 * @author Pasi-Markus Mäkelä
 */
class Entity implements Comparable, Serializable, Validateable {
    // add to set properties that should not be imported from JSON
    public static Set<String> REMOVE_FROM_IMPORTED_JSON = ['_id', 'id', 'creatorId', 'version', 'parentEntityId', 'creatorId',
                                                           'managerIds', 'readonlyUserIds', 'modifierIds', 'calculationResults',
                                                           'calculationTotalResults', 'parentName', 'version']

    static mapWith = "mongo"
    ObjectId id
    ObjectId parentEntityId
    String name
    String operatingPeriod
    String entityClass
    Date dateCreated
    Date lastUpdated
    Date designResultsCleared
    String comment
    ObjectId creatorId
    ObjectId lastUpdaterId
    Boolean deleted = false
    Boolean commited = false
    Boolean master = false
    Boolean locked
    Boolean superLocked
    Boolean archived
    Boolean superArchived
    Boolean verified // Bionova verified DEPRECATE! USE ONLY superVerified from now
    Boolean superVerified // Superuser verified
    Date lockingTime
    Boolean hasImage
    Boolean isPublic
    List<String> resourceIds = []
    List<String> indicatorIds
    List<String> readonlyIndicatorIds
    List<String> modifierIds = []
    List<String> readonlyUserIds = []
    List<String> managerIds = []
    List targetedTaskIds = []
    List noteIds = []
    List benchmarkIds
    List mapEntityIds
    Map queryReady = [:]
    // Used for basic query readiness / non indicator queries only, deprecated for anything else, use per indicator
    Map<String, Map<String, Boolean>> queryReadyPerIndicator
    List<ChildEntity> childEntities
    Map<String, Boolean> monthlyInputEnabledByQuery
    Map<String, Boolean> quarterlyInputEnabledByQuery
    Map<String, Boolean> published
    List<PreProvisionedUserRight> preProvisionedUserRights
    Map<String, List<String>> userIndicators // limitation to indicators an user can see
    String parentName
    String notifiedUsername
    String targetedNote
    Boolean allowAsBenchmark
    Boolean rejected
    Boolean inProgress
    Boolean component
    Boolean carbonHero
    String anonymousDescription

    Map<String, String> carbonBenchmarkIndex //Contain name, score & index by design level

    LcaCheckerResult lcaCheckerResult
    List<String> suppressedLcaCheckers

    // TODO Defaults, please for this is still a question
    List<DefaultValueSet> defaultValueSets //deprecated, use defaults
    Map<String, String> defaults

    List<CalculationTotalResult> calculationTotalResults
    List<CalculationResult> calculationResults
    List<CalculationResult> tempCalculationResults // for temporary entities
    Boolean projectLevelEntity // DEPRECATED
    List<String> projectLevelQueries //DEPRECATED SW-1645
    Map<String, String> queryAndLinkedIndicator //DEPRECATED
    Map<String, List<String>> queryAndLinkedIndicators //DEPRECATED SW-1645
    Boolean enableAsUserTestData
    Boolean chosenDesign
    String newToolsAvailable
    String autocaseDesignId  // Linked to autocase project
    String autocaseProjectId // Linked to autocase project

    @Deprecated
    Double previousTotal // Transient for calculation, previous displayRule total before save
    @Deprecated
    String saveMessage // Transient from calculation, shows how results changed

    Map<String, String> saveMessageByIndicator
    Map<String, Double> previousTotalByIndicator

    Integer ribaStage

    List<String> disabledIndicators // mainly for manually disabling indicators for a design

    Map<String, QueryLastUpdateInfo> queryLastUpdateInfos

    ScopeToSave scope
    List<String> disabledSections
    // Map the scope list ids not selected to the sections to disable in buildingMaterialsQuery

    Boolean isHiddenDesign // BETIE related: specially defined classification
    String calculationClassficationId

    Integer batimentIndex // For FEC export
    Boolean skipCorruptedDataCheck
    Boolean resultsOutOfDate

    /*
        rsetMappings have 2 objects: batimentIndexMappings and batimentZoneIndexMappings
        + batimentIndexMappings is a Map < designId : batiment index >. This is used for mapping the batiment to design
        + batimentZoneIndexMappings has Maps within a Map < designId : < zoneId of design : zone index of batiment >>. This is used for mapping the zones of the batiment to zones of the design
    */
    // For FEC export & import. This field is deprecated, use frenchRsetMappings instead
    @Deprecated
    Map<String, Map> rsetMappings // field to save RSET mappings for all french tools
    List<RsetMapping> frenchRsetMappings

    Map<String, List<String>> customAdditionalQuestionAnswers
    Entity parentEntity

    static hasMany = [datasets                : Dataset, childEntities: ChildEntity,
                      preProvisionedUserRights: PreProvisionedUserRight,
                      calculationTotalResults : CalculationTotalResult, calculationResults: CalculationResult]

    // static mappedBy = [children: "parent"]

    static embedded = [
            "datasets",
            "childEntities",
            "monthlyInputs",
            "preProvisionedUserRights",
            "defaultValueSets",
            "calculationTotalResults",
            "lcaCheckerResult",
            "tempCalculationResults",
            "queryLastUpdateInfos",
            "scope",
            "parentEntity",
            "frenchRsetMappings"
            /*,
            "calculationResults"*/
    ]

    static constraints = {
        name(validator: { val, obj ->
            if (obj.entityClass && EntityClass.OPERATING_PERIOD.toString() != obj?.entityClass && !val) return ['entity.name.blank']
        }, nullable: true, blank: true)
        operatingPeriod(validator: { val, obj ->
            if (EntityClass.OPERATING_PERIOD.toString() == obj?.entityClass && !val) return [
                    'entity.operatingPeriod.blank'
            ]
        }, nullable: true)
        targetedTaskIds nullable: true
        // userEntities nullable: true
        indicatorIds nullable: true
        comment nullable: true, blank: true
        resourceIds nullable: true
        datasets nullable: true
        managerIds nullable: true
        modifierIds nullable: true
        readonlyUserIds nullable: true
        queryReady nullable: true
        noteIds nullable: true
        hasImage nullable: true
        locked nullable: true
        lockingTime nullable: true
        benchmarkIds nullable: true
        isPublic nullable: true
        entityClass nullable: true
        mapEntityIds nullable: true
        parentEntityId nullable: true
        childEntities nullable: true
        // parent nullable: true
        // children nullable: true
        monthlyInputEnabledByQuery nullable: true
        quarterlyInputEnabledByQuery nullable: true
        published nullable: true
        preProvisionedUserRights nullable: true
        userIndicators nullable: true
        parentName nullable: true
        notifiedUsername nullable: true
        creatorId nullable: true
        lastUpdaterId nullable: true
        targetedNote nullable: true
        calculationTotalResults nullable: true
        allowAsBenchmark nullable: true
        anonymousDescription nullable: true, blank: true
        projectLevelQueries nullable: true
        projectLevelEntity nullable: true
        superLocked nullable: true
        queryAndLinkedIndicator nullable: true
        enableAsUserTestData nullable: true
        ribaStage nullable: true
        inProgress nullable: true
        rejected nullable: true
        component nullable: true
        chosenDesign nullable: true
        lcaCheckerResult nullable: true
        verified nullable: true
        superVerified nullable: true
        tempCalculationResults nullable: true
        suppressedLcaCheckers nullable: true
        queryReadyPerIndicator nullable: true
        disabledIndicators nullable: true
        defaults nullable: true
        queryLastUpdateInfos nullable: true
        queryAndLinkedIndicators nullable: true
        carbonHero nullable: true
        readonlyIndicatorIds nullable: true
        carbonBenchmarkIndex nullable: true
        scope nullable: true
        disabledSections nullable: true
        newToolsAvailable nullable: true
        isHiddenDesign nullable: true
        autocaseDesignId nullable: true
        autocaseProjectId nullable: true
        calculationClassficationId nullable: true
        archived nullable: true
        superArchived nullable: true
        designResultsCleared nullable: true
        batimentIndex nullable: true
        customAdditionalQuestionAnswers nullable: true
        rsetMappings nullable: true
        skipCorruptedDataCheck nullable: true
        resultsOutOfDate nullable: true
        parentEntity nullable: true
        frenchRsetMappings nullable: true
        saveMessage nullable: true
        saveMessageByIndicator nullable: true
        previousTotalByIndicator nullable: true
    }

    static mapping = {
        indicatorIds index: true
    }

    static transients = [
            "ready",
            "indicators",
            "resources",
            "resourceNames",
            "commitable",
            "indicatorReady",
            "municipality",
            "country",
            "street",
            "postcode",
            "grossSurface",
            "surfaceRoundTo10k",
            "constructionYear",
            "propertyCode",
            "public",
            "type",
            "status",
            "readonly",
            "manageable",
            "modifiable",
            "typeResourceId",
            "users",
            "seeingAllowed",
            "anonymous",
            "managers",
            "modifiers",
            "readonlyUsers",
            "operatingPeriodAndName",
            "validOperatingLicenses",
            "validDesignLicenses",
            "features",
            "resolveQueryReady",
            "notes",
            "sourceListing",
            "isPortfolio",
            "portfolio",
            "validUserLicenses",
            "queriesEditable",
            "childName",
            "benchmarks",
            "callMethodByName",
            "mapEntities",
            "taskCount",
            "userIds",
            "taskIds",
            "paidIndicatorLicenses",
            "summaryData",
            "designPFName",
            "designPFAnon",
            "operatingPFName",
            "operatingPFAnon",
            "shortName",
            "designs",
            "operatingPeriods",
            "carbonDesigns",
            "mapEntitiesByArea",
            "showDesignPhase",
            "showOperatingPhase",
            "showPortfolio",
            "canBeChildEntity",
            "entityClassResource",
            "childrenByChildEntities",
            "showHistoryLicensed",
            "expertParametersLicensed",
            "smallImage",
            "licenses",
            "expiredDesignLicenses",
            "expiredOperatingLicenses",
            "noAreaMapEntities",
            "componentAbilityLicensed",
            "reportIndicatorLicensed",
            "reportIndicators",
            "monthlyDataLicensed",
            "parentById",
            "specificCostLicensed",
            "hasDesigns",
            "hasOperatingPeriods",
            "notifiedUser",
            "nameVisibleInPortfolio",
            "quarterlyDataLicensed",
            "files",
            "showGWPLicensed",
            "importMapperLicensed",
            "importMapperApplications",
            "designImportMappers",
            "operatingImportMappers",
            "creatorById",
            "lastUpdaterById",
            "generatePdfLicensed",
            "designIds",
            "designChildEntities",
            "operatingChildEntities",
            "carbonDesignChildEntities",
            "designsForResultShowing",
            "operatingPeriodsForResultShowing",
            "showOtherAnswersLicensed",
            "countryForPrioritization",
            "APIExcelSampleLicensed",
            "accountPrivateDatasetsLicensed",
            "countryResource",
            "address",
            "automaticCostCalculationLicensed",
            "bimModelCheckerLicensed",
            "hasValidProductionLicenses",
            "hasAllowedChildren",
            "lcaCheckerAllowed",
            "countryResourceResourceId",
            "businessBenchmarksViewLicensed",
            "entityClassShowTasks",
            "preventReportGenerationLicensed",
            "transportationDefault",
            "isFreeTrial",
            "validWorkFlowIds",
            "hasCarbonHeroes",
            "hasCarbonHeroesAndAllowed",
            "hasCarbonHeroesAndAllowedAndLatestStatus",
            "hasCarbonHeroesAndLatestStatus",
            "carbonHeroesCount",
            "hasAllowedChildrenAndLatestStatus",
            "hasLatestStatus",
            "surface",
            "sankeyAndTreemap",
            "previousTotal",
            "highestChildEntityRibaStage",
            "isMaterialSpecifierEntity",
            "exportToAutocaseEnabled",
            "countryResourceDataset",
            "lcaModel",
            "incinerationEnergyMixResource",
            "GCCADemoLicensed",
            "isLCAParameterQueryReady",
            "numberFloor",
            "costCalculationMethod",
            "indicatorIdsList",
            "parentEntity",
            "nameStringWithoutQuotes"
    ]

    transient queryService
    transient entityService
    transient indicatorService
    transient optimiResourceService
    transient channelFeatureService
    transient datasetService
    transient loggerUtil
    transient userService
    transient applicationService
    transient taskService
    transient denominatorUtil
    transient optimiStringUtils
    transient configurationService
    transient resourceService
    transient licenseService
    transient childEntityService

    def getCreatorById() {
        User creator

        if (creatorId) {
            creator = User.collection.findOne(["_id": creatorId]) as User
        }
        return creator
    }

    def getLastUpdaterById() {
        User lastUpdater

        if (lastUpdaterId) {
            lastUpdater = User.collection.findOne(["_id": lastUpdaterId]) as User
        }
        return lastUpdater
    }

    def getNotifiedUser() {
        User user

        if (notifiedUsername) {
            user = User.findByUsername(notifiedUsername)
        }
        return user
    }

    Entity getParentById() {
        if (!parentEntity && parentEntityId) {
            try {
                parentEntity = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(parentEntityId)]) as Entity
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting parent entity by id ${parentEntityId}", e)
            }
        }
        return parentEntity
    }

    def getDesignChildEntities(Boolean alsoLocked = Boolean.FALSE) {
        if (alsoLocked) {
            return childEntities.findAll({ !it.deleted && it.entityClass.equals(EntityClass.DESIGN.toString()) })
        } else {
            return childEntities.findAll({
                !it.deleted && !it.locked && !it.superLocked && it.entityClass.equals(EntityClass.DESIGN.toString())
            })
        }
    }

    def getOperatingChildEntities(Boolean alsoLocked = Boolean.FALSE) {
        if (alsoLocked) {
            return childEntities.findAll({
                !it.deleted && it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
            })
        } else {
            return childEntities.findAll({
                !it.deleted && !it.locked && !it.superLocked && it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
            })
        }
    }

    def getCarbonDesignChildEntities(Boolean alsoLocked = Boolean.FALSE) {
        if (alsoLocked) {
            return childEntities.findAll({ !it.deleted && it.entityClass.equals(EntityClass.CARBON_DESIGN.toString()) })
        } else {
            return childEntities.findAll({
                !it.deleted && !it.locked && !it.superLocked && it.entityClass.equals(EntityClass.CARBON_DESIGN.toString())
            })
        }
    }

    def getDesignsForResultShowing(Boolean alsoLocked = Boolean.FALSE, Boolean alsoDatasets = Boolean.FALSE) {
        def designIds

        if (alsoLocked) {
            designIds = childEntities?.findAll({
                !it.deleted && it.entityClass.equals(EntityClass.DESIGN.toString())
            })?.collect({ it.entityId })
        } else {
            designIds = childEntities?.findAll({
                !it.deleted && !it.locked && !it.superLocked && it.entityClass.equals(EntityClass.DESIGN.toString())
            })?.collect({ it.entityId })
        }

        if (designIds) {
            if (alsoDatasets) {
                return Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]],
                        ["parentEntityId": 1, "name": 1, "datasets": 1, "calculationTotalResults": 1])?.toList()
            } else {
                return Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]],
                        ["parentEntityId": 1, "name": 1, "calculationTotalResults": 1])?.toList()
            }
        } else {
            return null
        }
    }

    def getOperatingPeriodsForResultShowing(Boolean alsoLocked = Boolean.FALSE, Boolean alsoDatasets = Boolean.FALSE) {
        def designIds

        if (alsoLocked) {
            designIds = childEntities?.findAll({
                !it.deleted && it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
            })?.collect({ it.entityId })
        } else {
            designIds = childEntities?.findAll({
                !it.deleted && !it.locked && !it.superLocked && it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
            })?.collect({ it.entityId })
        }

        if (designIds) {
            if (alsoDatasets) {
                return Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]],
                        ["parentEntityId"         : 1, "operatingPeriod": 1, "name": 1, "datasets": 1,
                         "calculationTotalResults": 1])?.collect({
                    it as Entity
                })
            } else {
                return Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]],
                        ["parentEntityId": 1, "operatingPeriod": 1, "name": 1, "calculationTotalResults": 1])?.
                        collect({ it as Entity })
            }
        } else {
            return null
        }
    }

    def getDesigns() {
        def designs = []
        def designIds = childEntities?.findAll({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.DESIGN.toString())
        })?.collect({ it.entityId })

        if (designIds) {
            designs = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]])?.collect({
                it as Entity
            })
        }
        return designs
    }

    def getDesignIds() {
        def designIds = childEntities?.findAll({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.DESIGN.toString())
        })?.collect({ it.entityId })
        return designIds
    }

    def getOperatingPeriods() {
        def periods = []
        def periodIds = childEntities?.findAll({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
        })?.collect({ it.entityId })

        if (periodIds) {
            periods = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(periodIds)]])?.collect({
                it as Entity
            })
        }
        return periods
    }

    List<CarbonDesign> getCarbonDesigns() {
        List<CarbonDesign> carbonDesigns = []
        List<String> designIds = childEntities?.findAll({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.CARBON_DESIGN.toString())
        })?.collect({ it.entityId.toString() })

        if (designIds) {
            carbonDesigns = collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designIds)]])?.collect({
                it as CarbonDesign
            })
        }
        return carbonDesigns
    }

    def getHasDesigns() {
        boolean hasDesigns = false

        if (childEntities?.find({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.DESIGN.toString())
        })) {
            hasDesigns = true
        }
        return hasDesigns
    }

    def getHasOperatingPeriods() {
        boolean hasOperatingPeriods = false

        if (childEntities?.find({
            !it.deleted &&
                    it.entityClass.equals(EntityClass.OPERATING_PERIOD.toString())
        })) {
            hasOperatingPeriods = true
        }
        return hasOperatingPeriods
    }

    def hasOperatingPeriod(String period) {
        boolean has = false

        if (period) {
            if (getOperatingPeriods().find({ period.equals(it.operatingPeriod) })) {
                has = true
            }
        }
        return has
    }

    def getChildCount(String entityClass) {
        def children = childEntities?.findAll({
            !it.deleted &&
                    it.entityClass.equals(entityClass)
        })
        return children ? children.size() : 0
    }

    def getMapEntities() {
        def mapEntities

        if (mapEntityIds) {
            mapEntities = MapEntity.getAll(DomainObjectUtil.stringsToObjectIds(mapEntityIds))?.sort({ it.orderNo })
        }
        return mapEntities
    }

    def getNoAreaMapEntities() {
        getMapEntities()?.findAll({ MapEntity me -> !me.isArea })
    }

    def getMapEntitiesByArea() {
        def areas = getMapEntities()?.findAll({ MapEntity me -> me.isArea })
        def mapEntitiesByArea = [:]

        if (areas) {
            areas?.each { MapEntity area ->
                mapEntitiesByArea.put(area, area.getMapEntitiesInArea())
            }
        }
        return mapEntitiesByArea
    }

    def getBenchmarks() {
        def benchmarks

        if (benchmarkIds) {
            benchmarks = BenchmarkValue.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(benchmarkIds)]])?.collect({
                it as BenchmarkValue
            })
        }
        return benchmarks
    }

    def getNotes() {
        def notes

        if (noteIds) {
            notes = Note.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(noteIds)]])?.collect({
                it as Note
            })
        }
        return notes
    }

    def getManagers() {
        def managers

        if (managerIds) {
            managers = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(managerIds)]], [username: 1, name: 1])?.collect({
                it as User
            })
        }
        return managers
    }

    def getModifiers() {
        def modifiers

        if (modifierIds) {
            modifiers = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(modifierIds)]], [username: 1, name: 1])?.collect({
                it as User
            })
        }
        return modifiers
    }

    def getReadonlyUsers() {
        def readonlyUsers

        if (readonlyUserIds) {
            readonlyUsers = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(readonlyUserIds)]], [username: 1, name: 1])?.collect({
                it as User
            })
        }
        return readonlyUsers
    }

    def beforeInsert() {
        dateCreated = new Date()
        lastUpdated = dateCreated
        User user = userService.getCurrentUser(Boolean.TRUE)

        if (user) {
            creatorId = user.id
            lastUpdaterId = user.id
        }
        name = name?.trim()
    }

    def beforeUpdate() {
        lastUpdated = new Date()
        User user = userService.getCurrentUser(Boolean.TRUE)
        designResultsCleared = null // Clear the clearing date if project gets updated
        skipCorruptedDataCheck = null

        if (user) {
            lastUpdaterId = user.id
        }
        name = name?.trim()
    }

    def getTargetedTasks() {
        def tasks

        if (targetedTaskIds) {
            tasks = Task.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(targetedTaskIds)]])?.collect({
                it as Task
            })
        }
        return tasks
    }

    def getTasks() {
        def relatedTasks = []

        if (targetedTasks) {
            relatedTasks.addAll(targetedTasks)
        }
        def childEntities = getChildrenByChildEntities()

        if (childEntities) {
            def childTasks

            childEntities.each { Entity e ->
                childTasks = e.targetedTasks

                if (childTasks) {
                    relatedTasks.addAll(childTasks)
                }
            }
        }
        return relatedTasks
    }

    def getTaskCount() {
        def taskIds = getTaskIds()
        def count = taskIds ? taskIds.size() : 0
        return count
    }

    def getTaskIds() {
        def taskIds = []

        if (targetedTaskIds?.size() > 0) {
            taskIds.addAll(targetedTaskIds)
        }
        def childEntities = getChildrenByChildEntities()

        childEntities?.each { Entity e ->
            def childTaskIds = e.targetedTaskIds

            if (childTaskIds?.size() > 0) {
                taskIds.addAll(childTaskIds)
            }
        }
        return taskIds?.unique()
    }

    def getOpenTasks() {
        return getTasks()
    }

    List<Indicator> getIndicators() {
        indicatorService.getIndicatorsByEntity(this.id)
    }

    def getResources(String resourceGroup) {
        optimiResourceService.getResourcesByResourceGroupAndResourceIds(resourceGroup, resourceIds)
    }

    def getResourceNames(String resourceGroup) {
        def resources = getResources(resourceGroup)
        def resourceNames = resources?.collect { optimiResourceService.getLocalizedName(it) }
        return resourceNames
    }

    public int compareTo(Object obj) {
        if (obj && obj instanceof Entity) {
            if (this.parentEntityId && obj.parentEntityId) {
                if (this.ribaStage != null && obj.ribaStage != null) {
                    return this.ribaStage.compareTo(obj.ribaStage)
                } else if (this.ribaStage != null && obj.ribaStage == null) {
                    return -1
                } else if (this.ribaStage == null && obj.ribaStage != null) {
                    return 1
                } else {
                    return this.operatingPeriodAndName.toUpperCase().compareTo(obj.operatingPeriodAndName.toUpperCase())
                }
            } else if (this.name) {
                return this.name.trim().compareTo(obj.name ? obj.name.trim() : "")
            } else {
                return 1
            }
        } else {
            return 1
        }
    }
    // TODO: this stuff often using in a loops, but indicator.getQueryIds make 2 calls for getting validLicenses and for entityFeatures. Should be adjusted
    def isIndicatorReady(Indicator indicator, List<String> queryIds = null, boolean onlyMandatoryQueryNeeded = false, boolean checkDisabledIndicator = false) {
        def ready = true

        if (indicator) {
            Map<String, Boolean> queriesReady = queryReadyPerIndicator?.get(indicator.indicatorId)
            def queries = queryIds ? queryIds : indicator.getQueryIds(this)
            List<IndicatorQuery> nonOptionalQueries = indicator.indicatorQueries?.findAll({ IndicatorQuery iq -> !iq.optional })
            def optionalQueryIds = indicator.indicatorQueries?.findAll({ IndicatorQuery iq -> iq.optional })?.collect({
                it.queryId
            })

            if (nonOptionalQueries && !nonOptionalQueries.isEmpty()) {
                if (queries) {
                    for (String queryId in queries) {
                        if (!optionalQueryIds?.contains(queryId)) {
                            if (queriesReady?.get(queryId) != null) {
                                if (!queriesReady?.get(queryId)) {
                                    ready = false
                                    break
                                }
                            } else {
                                if (!queryReady?.get(queryId)) {
                                    ready = false
                                    break
                                }
                            }
                        }
                    }
                }
            } else if (optionalQueryIds && !optionalQueryIds.isEmpty() && !onlyMandatoryQueryNeeded && !datasets?.find({
                optionalQueryIds.contains(it.queryId)
            })) {
                ready = false
            }
            if (checkDisabledIndicator) {
                if (disabledIndicators?.contains(indicator?.indicatorId)) {
                    ready = false
                }
            }
        } else {
            ready = false
        }
        return ready
    }

    def indicatorsReady(List<Indicator> indicators) {
        boolean ready = true
        List<String> entityIndicatorIds

        if (parentEntityId) {
            Entity parent = getParentById()
            entityIndicatorIds = parent?.indicatorIds
        } else {
            entityIndicatorIds = indicatorIds
        }
        // indicators = indicators?.findAll({ it.indicatorId in getIndicators().collect({it.indicatorId}) })
        if (indicators && entityIndicatorIds) {
            indicators = indicators.findAll({ entityIndicatorIds.contains(it.indicatorId) })

            if (indicators) {
                for (Indicator indicator in indicators) {
                    if (!isIndicatorReady(indicator)) {
                        ready = false
                        break
                    }
                }
            }
        }
        return ready
    }

    def getCommitable() {
        def ready = false
        def indicators = getIndicators()

        try {
            indicators?.each {
                ready = isIndicatorReady(it.indicatorId)

                if (!ready) {
                    throw new Exception("Found not ready indicator, breaking from loop...")
                }
            }
        } catch (Exception e) {
            // Do nothing, just found not ready indicator
        }
        return ready
    }

    def getBuildingIconPrefix() {
        def entityTypeResourceId = getTypeResourceId()
        def iconType

        if (entityTypeResourceId) {
            Resource buildindTypeResource = optimiResourceService.getResourceWithParams(entityTypeResourceId, null, null)

            buildindTypeResource?.resourceGroup?.each {
                if (it.contains("picture")) {
                    def iconNo = it.replaceAll("picture", "")?.trim()

                    if ("1" == iconNo) {
                        iconType = "buildingtype-smallhouse"
                    } else if ("2" == iconNo) {
                        iconType = "buildingtype-apartmenthouse"
                    } else if ("3" == iconNo) {
                        iconType = "buildingtype-office"
                    } else if ("4" == iconNo) {
                        iconType = "buildingtype-shop"
                    } else if ("5" == iconNo) {
                        iconType = "buildingtype-hotel"
                    } else if ("6" == iconNo) {
                        iconType = "buildingtype-hospital"
                    } else if ("7" == iconNo) {
                        iconType = "buildingtype-sportcenter"
                    } else if ("8" == iconNo) {
                        iconType = "buildingtype-school"
                    } else if ("9" == iconNo) {
                        iconType = "buildingtype-factory"
                    }
                }
            }
        }
        return iconType
    }

    def getMunicipality() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "municipality" })

        if (dataset) {
            return dataset.answerIds[0]?.toString()
        } else {
            return null
        }
    }

    def getCountry() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })

        if (dataset) {
            return optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null))
        } else {
            return null
        }
    }

    def getLcaModel() {
        def dataset = datasets?.find({ d -> d.queryId == Constants.LCA_PARAMETERS_QUERYID && d.questionId == "lcaModel" })

        if (dataset && dataset.answerIds) {
            return dataset.answerIds[0]?.toString()
        } else {
            return null
        }
    }

    def getCountryForPrioritization() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })

        if (dataset) {
            return optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null)?.getNameEN()
        } else {
            return null
        }
    }

    def getResourceFromBasicQueryQuestion(String questionId) {
        def dataset = datasets?.find({ d -> d.queryId.equals(getBasicQueryId()) && d.questionId.equals(questionId) })

        if (dataset) {
            return optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null)
        } else {
            return null
        }
    }

    def getStreet() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "street" })

        if (dataset) {
            return dataset.answerIds[0]?.toString()
        } else {
            return null
        }
    }

    def getPostcode() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "postcode" })

        if (dataset) {
            return dataset.answerIds[0]
        } else {
            return null
        }
    }


    def getGrossSurface() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "grossSurface" })

        if (dataset) {
            if (dataset.answerIds && dataset.answerIds[0].isNumber()) {
                def surface = dataset.answerIds[0].toDouble()
                DecimalFormat df = userService.getDefaultDecimalFormat()
                df.applyPattern("###,###.##")
                return df.format(surface)
            } else {
                return dataset.answerIds[0]
            }
        } else {
            return null
        }
    }

    def getSurfaceRoundTo10k() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "grossSurface" })

        if (dataset && dataset.answerIds) {
            if (dataset.answerIds[0].isNumber()) {
                Integer surface = Math.round(Math.ceil(dataset.answerIds[0].toDouble() / 10000) * 10000)?.intValue()
                return surface
            } else {
                Double tempAnswer = dataset.answerIds[0]?.toString()?.replaceAll(",", ".")?.toDouble()
                Integer surface = Math.round(Math.ceil(tempAnswer / 10000) * 10000)?.intValue()
                return surface
            }
        } else {
            return null
        }
    }

    def getSurface() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "grossSurface" })

        if (dataset && dataset.answerIds && DomainObjectUtil.isNumericValue(dataset.answerIds[0]?.toString())) {
            return DomainObjectUtil.convertStringToDouble(dataset.answerIds[0].toString())
        }

        return null
    }


    def getConstructionYear() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "constructionYear" })

        if (dataset) {
            return dataset.answerIds[0]
        } else {
            return null
        }
    }

    def getPublic() {
        boolean isPublic = false
        User user = userService.getCurrentUser()

        if (user) {
            ChannelFeature channelFeature = channelFeatureService.getChannelFeatureByToken(user.channelToken)

            if (channelFeature && published?.get(channelFeature.token)) {
                isPublic = true
            }
        }
        return isPublic
    }

    def getPropertyCode() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "propertyCode" })

        if (dataset) {
            return dataset.answerIds[0]
        } else {
            return null
        }
    }

    def getType() {
        Dataset dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "type" })

        if (dataset) {
            return optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null))
        } else {
            return null
        }
    }

    def getIsPortfolio() {
        Dataset dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "type" })
        return (dataset && "portfolio" == optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null)?.resourceId) ||
                EntityClass.PORTFOLIO.toString() == entityClass
    }

    def getStatus() {
        def dataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "status" })

        if (dataset) {
            return optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null))
        } else {
            return null
        }
    }

    def getReadonly() {
        def readonly = false
        def user = getCurrentUser()

        if (user) {
            if (DomainObjectUtil.stringsToObjectIds(readonlyUserIds)?.contains(user.id) && !getModifiable()) {
                readonly = true
            }
        }

        if ("operatingPeriod".equals(entityClass) && !validOperatingLicenses) {
            readonly = true
        } else if ("design".equals(entityClass) && !validDesignLicenses) {
            readonly = true
        }
        return readonly
    }

    def getManageable() {
        def manageable = false

        if (!this.id) {
            manageable = true
        } else {
            User user = getCurrentUser()

            if (user) {
                if (DomainObjectUtil.stringsToObjectIds(managerIds)?.contains(user.id) || userService.getSuperUser(user)) {
                    manageable = true
                }
            }
        }
        return manageable
    }

    def getModifiable() {
        def modifiable = false

        if (!this.id) {
            modifiable = true
        } else {
            User user = getCurrentUser()

            if (user) {
                if (DomainObjectUtil.stringsToObjectIds(modifierIds)?.contains(user.id) || getManageable() || userService.getConsultant(user)) {
                    modifiable = true
                }
            }
        }
        return modifiable
    }

    def getSeeingAllowed() {
        return getReadonly() || getManageable() || getModifiable() || getPublic()
    }

    def getTypeResourceId() {
        def basicQueryId = getBasicQueryId()
        def typeForResourceId
        def datasetForType = datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "type" })

        if (datasetForType) {
            typeForResourceId = datasetForType.answerIds?.size() > 0 ? datasetForType.answerIds.get(0) : null
        }
        return typeForResourceId
    }

    def getAddress() {
        def basicQueryId = getBasicQueryId()
        Dataset dataset = datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "address" })

        if (dataset) {
            return dataset.answerIds[0]?.toString()
        } else {
            return null
        }
    }

    def getUserIds() {
        def userIds = []

        if (managerIds) {
            userIds.addAll(managerIds)
        }

        if (modifierIds) {
            userIds.addAll(modifierIds)
        }

        if (readonlyUserIds) {
            userIds.addAll(readonlyUserIds)
        }
        return userIds?.unique()
    }

    def getManagersAndModifiersEmails() {
        List<String> emails = []
        def userIds = []

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

    def getUsers() {
        def userIds = getUserIds()
        def users = []

        if (userIds?.size() > 0) {
            users = User.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(userIds)]], [username: 1, name: 1, emailValidated: 1, accountLocked: 1, enabled: 1, accountExpired: 1, country: 1, organizationName: 1, usableLicenseIds: 1, licenseIds: 1])?.collect({
                it as User
            })
        }
        return users
    }

    def getNameVisibleInPortfolio() {
        if (getUserIds()?.contains(getCurrentUser()?.id) || getUserIds()?.contains(getCurrentUser()?.id?.toString())) {
            return Boolean.TRUE
        }
        return Boolean.FALSE
    }

    def getUsersFromUserList(List<Document> users) {
        def userIds = getUserIds()
        List<Document> foundUsers = []


        if (userIds && users) {
            users?.each { Document u ->
                if (userIds.contains(u._id.toString()) || userIds.contains(u._id)) {
                    foundUsers.add(u)
                }
            }
        }
        return foundUsers
    }

    def getUsersForUi(List<Document> users) {
        def foundUsers = getUsersFromUserList(users)
        def uiUsers = []

        if (foundUsers?.size() > 2) {
            boolean managerFound = false

            for (Document u in foundUsers) {
                if (!managerFound && (this.managerIds?.contains(u._id) || this.managerIds?.contains(u._id.toString()))) {
                    uiUsers.add(0, u)
                    managerFound = true
                } else {
                    uiUsers.add(u)
                }
            }
        } else {
            uiUsers = foundUsers
        }
        return uiUsers
    }

    def getTasksFromTaskList(List<Task> tasks) {
        def taskIds = getTaskIds()
        def foundTasks = []

        if (taskIds?.size() > 0 && tasks?.size() > 0) {
            tasks?.each { Task t ->
                if (taskIds.contains(t.id.toString()) || taskIds.contains(t.id)) {
                    foundTasks.add(t)
                }
            }
        }
        return foundTasks
    }


    private User getCurrentUser() {
        User user = userService.getCurrentUser()
        return user
    }

    def getHasValidProductionLicenses() {
        def currentDate = new Date()
        def licenses
        Entity parent

        if (this.parentEntityId) {
            parent = this.getParentById()
        }

        if (parent) {
            licenses = License.collection.count([frozen: null, type: Constants.LicenseType.PRODUCTION.toString(), validFrom: [$lte: currentDate], validUntil: [$gte: currentDate], $or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [parent.entityClass]]]], licensedEntityIds: [$in: [parent.id, parent.id.toString()]]])
        } else {
            licenses = License.collection.count([frozen: null, type: Constants.LicenseType.PRODUCTION.toString(), validFrom: [$lte: currentDate], validUntil: [$gte: currentDate], $or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [this.entityClass]]]], licensedEntityIds: [$in: [this.id, this.id.toString()]]])
        }

        if (licenses) {
            return true
        } else {
            return false
        }
    }

    /**
     * Checks if a trial license (that is still valid / has expired) has been applied
     *
     * @return true if trial license has been applied
     */
    def getIsFreeTrial() {
        Boolean trial = Boolean.FALSE
        List<License> licenses = licenseService.getValidLicensesForEntity(this)

        if (licenses) {
            trial = licenses.find{ Constants.LicenseType.TRIAL.toString().equals(it.type) } ? Boolean.TRUE : Boolean.FALSE
        } else {
            trial = licenseService.getExpiredLicensesForEntity(this)?.find({ Constants.LicenseType.TRIAL.toString().equals(it.type) }) ? Boolean.TRUE : Boolean.FALSE
        }
        return trial
    }

    def getLicenses() {
        List<License> licenses
        Entity parent

        if (this.parentEntityId) {
            parent = this.getParentById()
        }

        if (parent) {
            licenses = License.collection.find([$and: [[$or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [parent.entityClass]]]]], [$or: [[freeForAllEntities: true], [licensedEntityIds: [$in: [parent.id]]], [licensedEntityIds: [$in: [parent.id.toString()]]]]]]])?.collect({
                it as License
            })
        } else {
            licenses = License.collection.find([$and: [[$or: [[compatibleEntityClasses: [$exists: false]], [compatibleEntityClasses: [$in: [this.entityClass]]]]], [$or: [[freeForAllEntities: true], [licensedEntityIds: [$in: [this.id]]], [licensedEntityIds: [$in: [this.id.toString()]]]]]]])?.collect({
                it as License
            })
        }
        return licenses?.unique({ it.id })
    }

    def getPaidIndicatorLicenses() {
        Date currentDate = new Date()
        List<License> licenses = License.collection.find([validFrom: [$lte: currentDate], validUntil: [$gte: currentDate], licensedIndicatorIds: [$exists: true], $or: [[licensedEntityIds: [$in: [this.id]]], [licensedEntityIds: [$in: [this.id.toString()]]]]])?.collect({
            it as License
        })
        return licenses
    }

    def getLicenseForIndicator(Indicator indicator) {
        License license

        if (indicator) {
            if (IndicatorUse.OPERATING.toString().equals(indicator.indicatorUse)) {
                def operatingLicenses = getValidOperatingLicenses()
                license = operatingLicenses?.find({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            } else if (IndicatorUse.DESIGN.toString().equals(indicator.indicatorUse)) {
                def designLicenses = getValidDesignLicenses()
                license = designLicenses?.find({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            }
        }
        return license
    }

    def getLicensesForIndicator(Indicator indicator) {
        List<License> licenses

        if (indicator) {
            if (IndicatorUse.OPERATING.toString().equals(indicator.indicatorUse)) {
                def operatingLicenses = getValidOperatingLicenses()
                licenses = operatingLicenses?.findAll({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            } else if (IndicatorUse.DESIGN.toString().equals(indicator.indicatorUse)) {
                def designLicenses = getValidDesignLicenses()
                licenses = designLicenses?.findAll({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            }
        }
        return licenses
    }

    def getExpiredLicensesForIndicator(Indicator indicator) {
        List<License> licenses

        if (indicator) {
            if (IndicatorUse.OPERATING.toString().equals(indicator.indicatorUse)) {
                def operatingLicenses = getExpiredOperatingLicenses()
                licenses = operatingLicenses?.findAll({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            } else if (IndicatorUse.DESIGN.toString().equals(indicator.indicatorUse)) {
                def designLicenses = getExpiredDesignLicenses()
                licenses = designLicenses?.findAll({ License l -> l.licensedIndicatorIds.contains(indicator.indicatorId) })
            }
        }
        return licenses
    }

    def getValidOperatingLicenses() {
        // loggerUtil.debug(log, "Start of Entity.getValidOperatingLicenses")
        def licenses = []
        def validLicenses = licenseService.getValidLicensesForEntity(this)

        if (validLicenses) {
            for (License license in validLicenses) {
                def licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    def indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, this)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (IndicatorUse.OPERATING.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }
        // loggerUtil.debug(log, "End of Entity.getValidOperatingLicenses")
        return licenses
    }

    def getExpiredOperatingLicenses() {
        def licenses = []
        List<License> expiredLicenses = licenseService.getExpiredLicensesForEntity(this)

        if (expiredLicenses) {
            for (License license in expiredLicenses) {
                def licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    def indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, this)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (IndicatorUse.OPERATING.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }
        return licenses
    }

    def getValidDesignLicenses() {
        // loggerUtil.debug(log, "Start of Entity.getValidDesignLicenses")
        def licenses = []
        def validLicenses = licenseService.getValidLicensesForEntity(this)

        if (validLicenses) {
            for (License license in validLicenses) {
                def licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    def indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, this)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (IndicatorUse.DESIGN.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }
        // loggerUtil.debug(log, "End of Entity.getValidDesignLicenses")
        return licenses
    }

    def getExpiredDesignLicenses() {
        def licenses = []
        List<License> expiredLicenses = licenseService.getExpiredLicensesForEntity(this)

        if (expiredLicenses) {
            for (License license in expiredLicenses) {
                def licensedIndicators = license.licensedIndicators

                if (licensedIndicators) {
                    def indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, this)

                    if (indicators) {
                        for (Indicator indicator in indicators) {
                            if (IndicatorUse.DESIGN.toString() == indicator.indicatorUse) {
                                licenses.add(license)
                                break
                            }
                        }
                    }
                }
            }
        }
        return licenses
    }

    def getFeatures() {
        List<Feature> features
        List<License> licenses = licenseService.getValidLicensesForEntity(this)

        if(licenses){
            List<String> licensedFeatureIds = licenses.licensedFeatureIds.flatten()
            features = licenseService.getLicensedFeatures(licensedFeatureIds)
        }

        return features ?: []
    }

    def getCostCalculationMethod() {
        def methodDataset = datasets?.find({ it.queryId == "LCCProjectParametersQuery" && it.questionId == "costCalculationMethod" })
        String method
        if (methodDataset && methodDataset.answerIds) {
            method = methodDataset.answerIds[0]
        } else {
            method = "automaticCostGeneration"
        }
        return method
    }

    def getImportMapperApplications() {
        return applicationService.getAllApplicationsThatHaveImportMappers()
    }

    def getDesignImportMappers() {
        List<ImportMapper> importMappers = []

        applicationService.getAllApplicationsThatHaveImportMappersAsJSON()?.each { Document app ->
            app.importMappers.each { importMapper ->
                if (!importMapper.compatibleEntityClasses || importMapper.compatibleEntityClasses?.contains(this.entityClass)) {
                    importMappers.add(importMapper as ImportMapper)
                }
            }
        }
        return importMappers
    }

    def getOperatingImportMappers() {
        List<ImportMapper> importMappers = []

        applicationService.getAllApplicationsThatHaveImportMappersAsJSON()?.each { Document app ->
            app.importMappers.each { importMapper ->
                if (!importMapper.compatibleEntityClasses || importMapper.compatibleEntityClasses?.contains(this.entityClass)) {
                    importMappers.add(importMapper as ImportMapper)
                }
            }
        }
        return importMappers
    }

    def getShowHistoryLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == "showHistory"
        })
        return (feature && operatingPeriod ? Boolean.TRUE : Boolean.FALSE)
    }

    def getExpertParametersLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.EXPERT_PARAMETERS
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getGCCADemoLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.GCCA_DEMO
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getImportMapperLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.IFC_FROM_SIMPLE_BIM || feature.featureId == Feature.IMPORT_FROM_FILE
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getLcaCheckerAllowed() {
        return EntityClass.BUILDING.toString().equals(entityClass)
    }

    def getComponentAbilityLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.COMPONENT_ABILITY
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getPreventReportGenerationLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.PREVENT_REPORT_GENERATION
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getGeneratePdfLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.GENERATE_PDF
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getCalculationDetailsEnabled() {
        def feature = getFeatures()?.find({ Feature feature ->
            Feature.CALCULATION_DETAILS.equals(feature.featureId)
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }


    def getAPIExcelSampleLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.API_SAMPLE_EXCEL
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getMonthlyDataLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.MONTHLY_DATA
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getAutomaticCostCalculationLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.AUTOMATIC_COST_GENERATION
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getQuarterlyDataLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == Feature.QUARTERLY_DATA
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getShowGWPLicensed() {
        return true
    }

    def getAllowedFeatureByProjectLicense(List<String> featureIds) {
        def allowedMap = [:]
        List<String> features = getFeatures()?.collect({ it.featureId })
        featureIds?.each { String id ->
            if (features?.contains(id)) {
                allowedMap.put(id, true)
            }
        }
        return allowedMap
    }

    def getSpecificCostLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId.equals("specificCost")
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getPreventMultipleDesignsLicensed(List<License> entityValidLicenses) {
        if (entityValidLicenses) {
            Boolean licensed = false
            for (License l in entityValidLicenses) {
                if (l.licensedFeatures?.find({ Feature.PREVENT_MULTIPLE_DESIGNS.equals(it.featureId) })) {
                    licensed = true
                } else {
                    licensed = false
                    break
                }
            }
            return licensed
        } else {
            return false
        }
    }

    def getCarbonDesignerOnlyLicensed(List<License> entityValidLicenses) {
        if (entityValidLicenses) {
            Boolean licensed = false
            for (License l in entityValidLicenses) {
                if (l.licensedFeatures?.find({ Feature.CARBON_DESIGNER_ONLY.equals(it.featureId) })) {
                    licensed = true
                } else if (l.licensedFeatures?.find({ Feature.CARBON_DESIGNER.equals(it.featureId) })) {
                    licensed = false
                    break
                }
            }
            return licensed
        } else {
            return false
        }
    }

    def getWorkFlowLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId.equals(Feature.WORK_FLOW)
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getValidWorkFlowIds() {
        List<License> licenses = licenseService.getValidLicensesForEntity(this)
        List<String> validWorkFlowIds = licenses?.findAll({ it.licensedWorkFlowIds })?.collect({ it.licensedWorkFlowIds })?.flatten()?.unique()
        return validWorkFlowIds
    }

    def getShowOtherAnswersLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId.equals(Feature.SHOW_OTHER_ANSWERS)
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getAccountPrivateDatasetsLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId.equals(Feature.ACCOUNT_PRIVATE_DATASETS)
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getBimModelCheckerLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId.equals(Feature.BIM_MODEL_CHECKER)
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getBusinessBenchmarksViewLicensed() {
        def feature = getFeatures()?.find({ Feature feature ->
            feature.featureId == "businessBenchmarksView"
        })

        if (feature) {
            return true
        } else {
            return false
        }
    }

    def getExportToAutocaseEnabled() {
        String country = getCountryResourceResourceId()

        if (country && ["canada", "usa"].contains(country.toLowerCase())) {
            def feature = getFeatures()?.find({ Feature feature ->
                feature.featureId == Feature.EXPORT_TO_AUTOCASE
            })

            if (feature) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }


    def isFeatureLicensed(String featureId) {
        boolean licensed = true

        if (featureId && !getFeatures()?.find({ Feature feature -> featureId.equals(feature.featureId) })) {
            licensed = false
        }
        return licensed
    }

    def getSourceListing(Indicator indicator = null) {
        List<String> queryIds

        if (indicator?.queriesForSourceListing) {
            queryIds = indicator.queriesForSourceListing
        } else {
            queryIds = indicator?.getQueries(this)?.collect({ Query q -> q.queryId })
        }
        List<Resource> resolvedResources = []

        if (queryIds) {
            def resourceDatasets = datasets?.findAll({ Dataset d -> d.resourceId && queryIds.contains(d.queryId) })

            resourceDatasets?.each { Dataset d ->
                Resource resource = datasetService.getResource(d)

                if (resource) {
                    Resource existingResource = resolvedResources.find({ Resource r -> r.resourceId.equals(resource.resourceId) && r.profileId?.equals(resource.profileId) })

                    if (existingResource) {
                        if (d.additionalQuestionAnswers) {
                            existingResource.datasetAdditionalQuestionAnswers.add(d.additionalQuestionAnswers)
                        }
                        existingResource.linkedDatasetManualIds.add(d.manualId)
                    } else {
                        if (d.additionalQuestionAnswers) {
                            resource.datasetAdditionalQuestionAnswers = [d.additionalQuestionAnswers]
                        } else {
                            resource.datasetAdditionalQuestionAnswers = []
                        }
                        resource.linkedDatasetManualIds = [d.manualId]
                        resolvedResources.add(resource)
                    }
                }
            }

            /*List<Resource> resources = resourceDatasets?.collect({ Dataset d -> d.resource })?.findAll({ Resource r -> r != null })
            // We might have datasets with old resourceIds for non existing resources

            if (resources) {
                for (Resource resource in resources) {
                    if (!resolvedResources.find({ Resource r -> r.resourceId.equals(resource.resourceId) && r.profileId?.equals(resource.profileId) })) {
                        resolvedResources.add(resource)
                    }
                }
            }*/
        }
        return resolvedResources?.sort()
    }

    def getOperatingPeriodAndName() {
        String returnable

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

    def resolveQueryReady(Indicator indicator, String queryId) {
        Query query = null
        if (queryId) {
            query = queryService.getQueryByQueryId(queryId, true)
        }
        return resolveQueryReady(indicator, query)
    }

    //TODO: move to service
    def resolveQueryReady(Indicator indicator, Query query) {
        // loggerUtil.debug(log, "Start of Entity.resolveQueryReady")
        def ready = false
        String queryId = query?.queryId
        if (query) {
            try {
                if (queryId) {
                    def questionsMap = query.getMandatoryQuestions(this.entityClass)
                    Map<String, List<String>> hiddenQuestionsOnProjectLevel = entityService.getHiddenQuestionsOnProjectLevel(this, query.queryId)

                    if (questionsMap) {
                        List<String> keys = questionsMap.keySet().toList()

                        readinessCheck:
                        for (String mapKey in keys) {
                            List<Question> questions = questionsMap.get(mapKey)

                            def datasetsBySectionId = datasets?.findAll({ Dataset d ->
                                d.sectionId == mapKey && (StringUtils.isNotBlank(d.answerIds?.get(0)) || d.resourceId)
                            })

                            for (Question question in questions) {
                                Boolean isWholeSectionHiddenOnProjectLevel = hiddenQuestionsOnProjectLevel.containsKey(mapKey) &&
                                        !hiddenQuestionsOnProjectLevel.get(mapKey)

                                if (indicator?.hideQuestions?.get(mapKey)?.contains(question?.questionId) ||
                                        hiddenQuestionsOnProjectLevel.get(mapKey)?.contains(question?.questionId) ||
                                        isWholeSectionHiddenOnProjectLevel) {
                                    ready = true
                                } else if (question.quantity) {
                                    if (!datasetsBySectionId?.find({ Dataset d -> question.questionId.equals(d.questionId) && d.quantity != null })) {
                                        ready = false
                                        break readinessCheck
                                    } else {
                                        ready = true
                                    }
                                } else if (question.inputType == 'file') {
                                    if (!getFiles()?.find({ queryId.equals(it.queryId) && question.questionId.equals(it.questionId) })) {
                                        ready = false
                                        break readinessCheck
                                    } else {
                                        ready = true
                                    }
                                } else {
                                    if (!datasetsBySectionId?.find({ Dataset d -> question.questionId.equals(d.questionId) })) {
                                        ready = false
                                        break readinessCheck
                                    } else {
                                        ready = true
                                    }
                                }
                            }
                        }
                    } else {
                        questionsMap = query.getNonMandatoryQuestions(this.entityClass)

                        if (questionsMap) {
                            for (String sectionId in questionsMap.keySet()) {
                                if (datasets?.find({ Dataset d ->
                                    sectionId.equals(d.sectionId) && questionsMap.
                                            get(sectionId).contains(d.questionId) && (d.answerIds || d.resourceId)
                                })) {
                                    ready = true
                                    break
                                } else {
                                    ready = false
                                }
                            }
                        }
                    }

                    if (indicator) {
                        IndicatorQuery indicatorQuery = indicator.indicatorQueries?.find({
                            it.queryId?.equals(query.queryId)
                        })
                        if (indicatorQuery && indicatorQuery.requiredResourceId) {
                            def datasets = datasetService.getDatasetsByEntityAndQueryId(this, indicatorQuery.queryId)
                            if (datasets) {
                                indicatorQuery.requiredResourceId.each { String requiredId ->
                                    if (!datasets.find({ it.resourceId?.equals(requiredId) })) {
                                        ready = false
                                    }
                                }
                            } else {
                                ready = false
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // Do nothing, just breaking from the loop
                if (e.getMessage() != "Found unanswered question, breaking from loop...") {
                    throw e
                }
            }
        }

        if (ready) {
            if (queryReady) {
                queryReady.put(queryId, true)
            } else {
                queryReady = [(queryId): true]
            }
            Task task = taskService.getTaskByEntityAndQueryId(this, queryId)

            if (task) {
                task.completed = true
                taskService.updateTask(task)
            }

            if (indicator) {
                if (queryReadyPerIndicator) {
                    def existing = queryReadyPerIndicator.get(indicator.indicatorId)

                    if (existing) {
                        existing.put(queryId, true)
                    } else {
                        existing = [(queryId): true]
                    }
                    queryReadyPerIndicator.put(indicator.indicatorId, existing)
                } else {
                    queryReadyPerIndicator = [(indicator.indicatorId): [(queryId): true]]
                }
            }
        } else {
            if (queryReady) {
                queryReady.put(queryId, false)
            } else {
                queryReady = [(queryId): false]
            }

            if (indicator) {
                if (queryReadyPerIndicator) {
                    def existing = queryReadyPerIndicator.get(indicator.indicatorId)

                    if (existing) {
                        existing.put(queryId, false)
                    } else {
                        existing = [(queryId): false]
                    }
                    queryReadyPerIndicator.put(indicator.indicatorId, existing)
                } else {
                    queryReadyPerIndicator = [(indicator.indicatorId): [(queryId): false]]
                }
            }
        }
        return ready
    }

    def getMissingRequiredResources(Indicator indicator, String queryId, Query q = null) {
        List<Resource> missingResources = []

        if (queryId || q) {
            Query query

            if (q) {
                query = q
            } else {
                query = queryService.getQueryByQueryId(queryId, true)
            }

            if (query) {
                if (indicator) {
                    IndicatorQuery indicatorQuery = indicator.indicatorQueries?.find({
                        it.queryId?.equals(query.queryId)
                    })

                    if (indicatorQuery && indicatorQuery.requiredResourceId) {
                        def datasets = datasetService.getDatasetsByEntityAndQueryId(this, indicatorQuery.queryId)

                        if (datasets) {
                            indicatorQuery.requiredResourceId.each { String requiredId ->
                                if (!datasets.find({ it.resourceId?.equals(requiredId) })) {
                                    Resource resource = optimiResourceService.getResourceByResourceAndProfileId(requiredId, null)
                                    if (resource) {
                                        missingResources.add(resource)
                                    }
                                }
                            }
                        } else {
                            indicatorQuery.requiredResourceId.each { String requiredId ->
                                Resource resource = optimiResourceService.getResourceByResourceAndProfileId(requiredId, null)
                                if (resource) {
                                    missingResources.add(resource)
                                }
                            }
                        }
                    }
                }
            }
        }
        return missingResources
    }

    def getResultRows(indicatorId) {
        return IndicatorResultRow.findAllByEntityIdAndIndicatorId(this.id.toString(), indicatorId)
    }

    def getPortfolio() {
        if (isPortfolio) {
            return Portfolio.findByEntityId(this.id?.toString())
        }
        return null
    }

    // Helper method for getting design's name or or operatingPeriod's period + name
    def getChildName() {
        if (EntityClass.DESIGN.toString() == entityClass) {
            return name
        } else if (EntityClass.OPERATING_PERIOD.toString() == entityClass) {
            return operatingPeriodAndName
        }
    }

    def getBasicQueryId() {
        return configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.BASIC_QUERY_ID.toString())
    }

    def getIncompleteQueries(Indicator indicator) {
        def incompleteQueries = []
        def queries = indicator.getQueries(this, Boolean.TRUE)
        def optionalQueryIds = indicator?.indicatorQueries?.findAll({ it.optional })?.collect({ it.queryId })

        queries?.each { Query query ->
            if (!optionalQueryIds?.contains(query.queryId) && (!queryReadyPerIndicator?.get(indicator?.indicatorId)?.get(query.queryId) && !queryReady?.get(query.queryId))) {
                incompleteQueries.add(query)
            }
        }
        return incompleteQueries
    }

    def callMethodByName(String name) {
        def value

        if (name) {
            String firstChar = name.trim().substring(0, 1).toUpperCase()
            String methodName = "get" + firstChar + name.trim().substring(1)

            try {
                Method method = Entity.class.getDeclaredMethod(methodName, null)
                value = method.invoke(this, null)
            }
            catch (Exception e) {
                value = null
            }

            if (!value) {
                value = getValueFromBasicQuery(name)
            }
        }
        return value
    }

    private String getValueFromBasicQuery(String questionId) {
        def value

        if (questionId) {
            Dataset dataset = datasets?.findAll({ getBasicQueryId().equals(it.queryId) })?.
                    find({ questionId.equals(it.questionId) })

            if (dataset) {
                value = dataset.resourceId ? optimiResourceService.getLocalizedName(datasetService.getResource(dataset)) : dataset.answerIds[0]
            }
        }
        return value
    }

    def getNumberFloor() {
        String basicQueryId = getBasicQueryId()
        Integer floors = null
        Dataset dataset = datasets?.find({ basicQueryId?.equals(it.queryId) && "numberFloor".equals(it.questionId) })

        if (dataset && dataset.quantity) {
            floors = dataset.quantity.intValue()
        }
        return floors
    }

    def getSummaryData() {
        Query basicQuery = queryService.getBasicQuery()
        EntitySummaryData summaryData = basicQuery?.summaryData?.find({ EntitySummaryData sd -> sd.entityClass.equals(entityClass) })
        return summaryData
    }


    def getDesignPFName() {
        def designPFName = ""
        EntitySummaryData summaryData = getSummaryData()

        if (summaryData) {
            List<String> display = summaryData.designPFName

            if (display) {
                def value

                display.each { String property ->
                    value = callMethodByName(property)

                    if (value) {
                        if (designPFName) {
                            designPFName = designPFName + ", " + value
                        } else {
                            designPFName = designPFName + value
                        }
                    }
                }
            }
        }
        return designPFName
    }

    def getDesignPFAnon() {
        def designPFAnon = ""
        EntitySummaryData summaryData = getSummaryData()

        if (summaryData) {
            List<String> display = summaryData.designPFAnon

            if (display) {
                def value

                display.each { String property ->
                    value = callMethodByName(property)

                    if (value) {
                        if (designPFName) {
                            designPFAnon = designPFAnon + ", " + value
                        } else {
                            designPFAnon = designPFAnon + value
                        }
                    }
                }
            }
        }
        return designPFAnon
    }

    def getOperatingPFName() {
        def operatingPFName = ""
        EntitySummaryData summaryData = getSummaryData()

        if (summaryData) {
            List<String> display = summaryData.operatingPFName

            if (display) {
                def value

                display.each { String property ->
                    value = callMethodByName(property)

                    if (value) {
                        if (operatingPFName) {
                            operatingPFName = operatingPFName + ", " + value
                        } else {
                            operatingPFName = operatingPFName + value
                        }
                    }
                }
            }
        }
        return operatingPFName
    }

    def getOperatingPFAnon() {
        def operatingPFAnon = ""
        EntitySummaryData summaryData = getSummaryData()

        if (summaryData) {
            List<String> display = summaryData.operatingPFAnon

            if (display) {
                def value

                display.each { String property ->
                    value = callMethodByName(property)

                    if (value) {
                        if (designPFName) {
                            operatingPFAnon = operatingPFAnon + ", " + value
                        } else {
                            operatingPFAnon = operatingPFAnon + value
                        }
                    }
                }
            }
        }
        return operatingPFAnon
    }

    def getShortName() {
        def shortName = ""
        EntitySummaryData summaryData = getSummaryData()

        if (summaryData) {
            List<String> display = summaryData.shortName

            if (display) {
                def value

                display.each { String property ->
                    value = callMethodByName(property)

                    if (value) {
                        if (shortName) {
                            shortName = shortName + ", " + value
                        } else {
                            shortName = shortName + value
                        }
                    }
                }
            }
        }
        return shortName
    }

    def getSummaryDataValue(String property) {
        def answer = callMethodByName(property)

        if (!answer) {
            Dataset dataset = datasets?.find({ d -> d.queryId.equals(getBasicQueryId()) && d.questionId.equals(property) })

            if (dataset) {
                answer = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(dataset.answerIds[0], null, null))

                if (!answer) {
                    answer = dataset.answerIds[0]
                }
            }
        }
        return answer
    }

    @Deprecated
    //TODO This method should be cleaned out and moved to a service, if still needed after UX rewrite of main page"
    def getLocalizedEntityClass() {
        Resource r = getEntityClassResource()
        def localized = optimiResourceService.getLocalizedName(r)
        return localized ? localized : ''
    }

    def getShowDesignPhase() {
        boolean show = false
        Resource r = getEntityClassResource()

        if (r && r.hasDesignPhase) {
            show = true
        }
        return show
    }

    def getShowOperatingPhase() {
        boolean show = false
        Resource r = getEntityClassResource()

        if (r && r.hasOperatingPhase) {
            show = true
        }
        return show
    }

    def getShowPortfolio() {
        boolean show = false
        Resource r = getEntityClassResource()

        if (r && r.hasPortfolio) {
            show = true
        }
        return show
    }

    def getCanBeChildEntity() {
        boolean canBe = false

        Resource r = getEntityClassResource()

        if (r && r.canBeChildEntity) {
            canBe = true
        }
        return canBe
    }

    def getSmallImage() {
        Resource resource = getEntityClassResource()
        return resource?.minimizeBasicData
    }

    def getEntityClassResource() {
        Resource resource = optimiResourceService.getResourceWithParams(entityClass, null, null)
        return resource
    }

    def getEntityClassShowTasks() {
        return (Boolean) Resource.collection.findOne([resourceId: entityClass, active: true], [showTasks: 1])?.showTasks
    }

    def getValueByAnalysisParameterSetting(AnalysisParameterSetting setting) {
        def value

        if (setting) {
            Dataset dataset = datasets?.find({ Dataset d ->
                d.queryId.equals(setting.queryId) && d.sectionId.equals(setting.sectionId) &&
                        d.questionId.equals(setting.questionId)
            })

            if (dataset) {
                if (dataset.resourceId && setting.resourceImpactFactor) {
                    value = DomainObjectUtil.callGetterByAttributeName(setting.resourceImpactFactor, datasetService.getResource(dataset))
                } else {
                    value = dataset.answerIds[0]
                }
            }
        }
        return value
    }

    def Integer getHighestChildEntityRibaStage() {
        Integer highestChildRiba

        if (childEntities) {
            def entityIds = childEntities.findAll({ !it?.deleted && it?.entityId }).collect({ it.entityId })

            if (entityIds) {
                List ribas = Entity.withCriteria {
                    inList("_id", entityIds)
                    projections {
                        property("ribaStage")
                    }
                }

                if (ribas && !ribas.isEmpty()) {
                    highestChildRiba = ribas.max()
                }
            }
        }
        return highestChildRiba
    }


    def List<Entity> getChildrenByChildEntities() {
        def children

        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            children = Entity.getAll(DomainObjectUtil.stringsToObjectIds(entityIds))
        }
        return children?.findAll({ it != null })
    }

    def getHasCarbonHeroesAndAllowedAndLatestStatus() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], carbonHero: true, allowAsBenchmark: true, chosenDesign: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getHasCarbonHeroesAndLatestStatus() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], carbonHero: true, chosenDesign: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getHasCarbonHeroesAndAllowed() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], carbonHero: true, allowAsBenchmark: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getCarbonHeroesCount() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            return Entity.collection.count([_id: [$in: entityIds], carbonHero: true])
        } else {
            return 0
        }
    }

    def getHasCarbonHeroes() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], carbonHero: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getHasAllowedChildrenAndLatestStatus() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], allowAsBenchmark: true, chosenDesign: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getHasLatestStatus() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], chosenDesign: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getHasAllowedChildren() {
        if (childEntities) {
            def entityIds = childEntities.collect({ it.entityId })
            def children = Entity.collection.count([_id: [$in: entityIds], allowAsBenchmark: true])

            if (children) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    def getReportIndicatorLicensed() {
        List<License> licenses = licenseService.getValidLicensesForEntity(this)
        boolean isLicensed = false

        if (licenses) {
            for (License license in licenses) {
                def indicators = license.getLicensedIndicators()

                if (indicators) {
                    for (Indicator indicator in indicators) {
                        if ("report".equals(indicator.indicatorUse)) {
                            isLicensed = true
                            break
                        }
                    }
                }

                if (isLicensed) {
                    break
                }
            }
        }
        return isLicensed
    }

    def getReportIndicators() {
        def indicators = []
        List<License> licenses = licenseService.getValidLicensesForEntity(this)

        if (licenses) {
            for (License license in licenses) {
                def foundIndicators = license.getLicensedIndicators()?.findAll({ Indicator ind -> "report".equals(ind.indicatorUse) })

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

    def hasAnyIndicator(List<Indicator> indicators) {
        boolean hasAny = false

        if (indicators) {
            List<String> indicatorIds = indicators.collect({ it.indicatorId })
            List<Indicator> entityIndicators = getIndicators()

            if (entityIndicators) {
                for (Indicator indicator in entityIndicators) {
                    if (indicatorIds.contains(indicator.indicatorId)) {
                        hasAny = true
                        break
                    }
                }
            }
        }
        return hasAny
    }

    List<EntityFile> getFiles() {
        return EntityFile.findAllByEntityId(this.id.toString())
    }

    def getDefaultValueSet(String applicationId, String defaultValueId, String defaultValueSetId) {
        DefaultValueSet defaultValueSet

        if (applicationId && defaultValueId && defaultValueSetId) {
            defaultValueSet = defaultValueSets?.find({
                applicationId.equals(it.applicationId) &&
                        defaultValueId.equals(it.defaultValueId) && defaultValueSetId.equals(it.defaultValueSetId)
            })
        }
        return defaultValueSet
    }

    def hasIndicatorResults(Indicator indicator) {
        boolean hasResults = false

        if (indicator) {
            List<ChildEntity> childEntities = childEntities

            if (childEntities) {
                childEntities.find({ ChildEntity childEntity ->
                    if (indicator.displayResult) {
                        def totalsByRule = childEntityService.getEntity(childEntity.entityId)?.calculationTotalResults?.find({ indicator.indicatorId.equals(it?.indicatorId) })?.totalByCalculationRule

                        if (totalsByRule?.get(indicator.displayResult)) {
                            hasResults = true
                            return true
                        }
                    }
                })
            }
        }
        return hasResults
    }

    def childHasIndicatorResults(Indicator indicator) {
        boolean hasResults = false

        if (indicator && calculationTotalResults) {
            def totalsByRule = calculationTotalResults.find({ indicator.indicatorId.equals(it?.indicatorId) })?.totalByCalculationRule

            if (totalsByRule?.get(indicator.displayResult)) {
                hasResults = true
            }
        }
        return hasResults
    }

    // From this on starts the showning of new calculationTotalResults
    // If giving calculationResults as argument, rememember IT HAS TO BE RESULTS PER INDICATOR
    def Double getResult(String indicatorId, String calculationRuleId, String resultCategoryId,
                         List<CalculationResult> resultsForIndicator = null, Boolean failOver = true) {
        Double result
        if (resultsForIndicator) {
            result = resultsForIndicator.findAll({
                it.calculationRuleId.equals(calculationRuleId) &&
                        it.resultCategoryId.equals(resultCategoryId)
            })?.collect({ it.result })?.sum()
        } else if (failOver) {
            result = getCalculationResultObjects(indicatorId, calculationRuleId, resultCategoryId)?.
                    collect({ it.result })?.sum()
        }
        return result
    }

    def Double getTotalByResultCategory(String resultCategoryId, List<CalculationResult> resultsForIndicator) {
        Double result
        if (resultsForIndicator) {
            result = resultsForIndicator.findAll({ it.resultCategoryId.equals(resultCategoryId) })?.collect({
                it.result
            })?.sum()
        }
        return result
    }

    //TODO: what is this?? Different return types???
    // Map<String, Map <String, Double>> monthlyTotalsPerCalculationRule
    // Map<String, Double> totalByCalculationRule
    def getTotalResult(String indicatorId, String calculationRuleId, Boolean requireMonthly = Boolean.FALSE) {
        if (indicatorId && calculationRuleId) {
            if (requireMonthly) {
                return calculationTotalResults?.find({ indicatorId.equals(it.indicatorId) })?.
                        monthlyTotalsPerCalculationRule?.get(calculationRuleId)
            } else {
                return calculationTotalResults?.find({ indicatorId.equals(it.indicatorId) })?.
                        totalByCalculationRule?.get(calculationRuleId)
            }
        } else {
            return null
        }
    }

    def getResultByDynamicDenominator(String indicatorId, String calculationRuleId, String datasetManualId,
                                      Boolean requireMonthly = Boolean.FALSE, Denominator denominator = null) {
        if (indicatorId && calculationRuleId && datasetManualId) {
            if (requireMonthly) {
                Map<String, Double> monthlyTotals = getTotalResult(indicatorId, calculationRuleId, true)
                Map<String, Double> monthlyResultsPerDenominator

                if (monthlyTotals) {
                    Map<String, Double> monthlyDenominators = getValueForDenominator(indicatorId, datasetManualId, true)
                    def multiplier
                    def divider

                    if (denominator && denominator.resultManipulator) {
                        multiplier = denominatorUtil.getMultiplierByDenominator(this, denominator)
                        divider = denominatorUtil.getDividerByDenominator(this, denominator)
                    }

                    if (monthlyDenominators) {
                        monthlyTotals.each { String month, Double value ->
                            if (value != null) {
                                Double valueForDenominator = monthlyDenominators.get(month)

                                if (valueForDenominator) {
                                    if (!monthlyResultsPerDenominator) {
                                        monthlyResultsPerDenominator = new LinkedHashMap<String, Double>()
                                    }
                                    Double valueByDenominator = value / valueForDenominator

                                    if (multiplier != null) {
                                        valueByDenominator = valueByDenominator * multiplier
                                    }

                                    if (divider) {
                                        valueByDenominator = valueByDenominator / divider
                                    }
                                    monthlyResultsPerDenominator.put(month, valueByDenominator)
                                }
                            }
                        }
                    }
                }
                return monthlyResultsPerDenominator
            } else {
                Double total = getTotalResult(indicatorId, calculationRuleId)
                Double result

                if (total != null) {
                    Double valueForDenominator = getValueForDenominator(indicatorId, datasetManualId)

                    if (valueForDenominator) {
                        result = total / valueForDenominator
                    }
                }
                return result
            }
        } else {
            return null
        }
    }

    def getValueForDenominator(String indicatorId, String denominatorOrDatasetManualId,
                               Boolean requireMonthly = Boolean.FALSE) {
        if (indicatorId && denominatorOrDatasetManualId && calculationTotalResults) {
            if (requireMonthly) {
                Map<String, Double> monthlyValues = calculationTotalResults.find({
                    indicatorId.equals(it.indicatorId)
                })?.monthlyDenominatorValues?.
                        get(denominatorOrDatasetManualId)
                return monthlyValues
            } else {
                Double value = calculationTotalResults.find({ indicatorId.equals(it.indicatorId) })?.denominatorValues?.
                        get(denominatorOrDatasetManualId)
                return value
            }

        } else {
            return null
        }
    }

    def getTotalResultByNonDynamicDenominator(String indicatorId, String calculationRuleId, String denominatorId,
                                              Boolean requireMonthly = Boolean.FALSE) {
        if (indicatorId && calculationRuleId) {
            if (requireMonthly) {
                Map<String, Double> denominatorResultsMonthly
                Map<String, Double> totalsMonthly = getTotalResult(indicatorId, calculationRuleId, requireMonthly)

                if (totalsMonthly) {
                    Map<String, Double> denominatorsMonthly = getValueForDenominator(indicatorId, denominatorId,
                            requireMonthly)

                    if (denominatorsMonthly) {
                        Constants.MONTHS.each { String month ->
                            Double result = totalsMonthly.get(month)
                            Double denominator = denominatorsMonthly.get(month)

                            if (result != null && denominator) {
                                if (!denominatorResultsMonthly) {
                                    denominatorResultsMonthly = new LinkedHashMap<String, Double>()
                                }
                                denominatorResultsMonthly.put(month, (result / denominator))
                            }
                        }
                    }
                }
                return denominatorResultsMonthly
            } else {
                Double totalResult = getTotalResult(indicatorId, calculationRuleId)
                Double totalResultByDenominator

                if (totalResult != null) {
                    Double valueForDenominator = getValueForDenominator(indicatorId, denominatorId)

                    if (valueForDenominator) {
                        totalResultByDenominator = totalResult / valueForDenominator
                    }
                }
                return totalResultByDenominator
            }
        } else {
            return null
        }
    }

    def Double getResultByNonDynamicDenominator(String indicatorId, String denominatorId, String calculationRuleId,
                                                String resultCategoryId, Boolean requireMonthly = Boolean.FALSE) {
        if (indicatorId && denominatorId && calculationTotalResults && (calculationRuleId || resultCategoryId)) {
            CalculationTotalResult indicatorCalculationResult = calculationTotalResults.
                    find({ indicatorId.equals(it.indicatorId) })

            if (requireMonthly) {
                Map<String, Double> denominatorValuesMonthly = indicatorCalculationResult?.
                        monthlyDenominatorValues?.get(denominatorId)
                Map<String, Double> resultsMonthly
                Map<String, Double> resultsByDenominatorMonthly

                if (denominatorValuesMonthly) {
                    if (calculationRuleId && resultCategoryId) {
                        CalculationResult.findAllByEntityIdAndIndicatorIdAndCalculationRuleIdAndResultCategoryId(id.toString(),
                                indicatorId, calculationRuleId, resultCategoryId)?.findAll({ it.monthlyResults })?.
                                collect({ it.monthlyResults })?.each {
                            if (resultsMonthly) {
                                resultsMonthly = MapUtil.mergeResultMaps(resultsMonthly, it)
                            } else {
                                resultsMonthly = new LinkedHashMap<String, Double>(it)
                            }
                        }
                    } else if (calculationRuleId) {
                        resultsMonthly = getTotalResult(indicatorId, calculationRuleId, requireMonthly)
                    } else {
                        CalculationResult.findAllByEntityIdAndIndicatorIdAndResultCategoryId(id.toString(),
                                indicatorId, resultCategoryId)?.findAll({ it.monthlyResults })?.
                                collect({ it.monthlyResults })?.each {
                            if (resultsMonthly) {
                                resultsMonthly = MapUtil.mergeResultMaps(resultsMonthly, it)
                            } else {
                                resultsMonthly = new LinkedHashMap<String, Double>(it)
                            }
                        }
                    }

                    if (resultsMonthly) {
                        Constants.MONTHS.each { String month ->
                            Double valuePerMonth = resultsMonthly.get(month)
                            Double denominatorValue = denominatorValuesMonthly.get(month)

                            if (valuePerMonth != null && denominatorValue) {
                                if (!resultsByDenominatorMonthly) {
                                    resultsByDenominatorMonthly = new LinkedHashMap<String, Double>()
                                }
                                resultsByDenominatorMonthly.put(month, (valuePerMonth / denominatorValue))
                            }
                        }
                    }
                }
                return resultsByDenominatorMonthly
            } else {
                Double valueForDenominator = indicatorCalculationResult?.denominatorValues?.get(denominatorId)
                Double result

                if (valueForDenominator) {
                    if (calculationRuleId && resultCategoryId) {
                        result = CalculationResult.findAllByEntityIdAndIndicatorIdAndCalculationRuleIdAndResultCategoryId(id.toString(),
                                indicatorId, calculationRuleId, resultCategoryId)?.sum({ it.result })
                    } else if (calculationRuleId) {
                        result = getTotalResult(indicatorId, calculationRuleId)
                    } else {
                        result = CalculationResult.findAllByEntityIdAndIndicatorIdAndResultCategoryId(id.toString(),
                                indicatorId, resultCategoryId)?.
                                sum({ it.result })

                    }

                    if (result != null) {
                        result = result / valueForDenominator
                    }
                }
                return result
            }
        } else {
            return null
        }
    }

    List<CalculationResult> getCalculationResultObjects(String indicatorId, String calculationRuleId, String resultCategoryId) {
        List<CalculationResult> foundResults

        if (indicatorId) {
            foundResults = CalculationResult.withCriteria{
                eq("entityId", id.toString())
                eq("indicatorId", indicatorId)

                if (calculationRuleId){
                    eq("calculationRuleId", calculationRuleId)
                }

                if (resultCategoryId){
                    eq("resultCategoryId", resultCategoryId)
                }
            }
        }

        return foundResults
    }

    def List<CalculationResult> getCalculationResultObjectsByResultCategoryIds(String indicatorId, List<String> resultCategoryIds) {
        List<CalculationResult> foundResults

        if (indicatorId && resultCategoryIds) {
            foundResults = CalculationResult.collection.find([entityId: id.toString(), indicatorId: indicatorId, resultCategoryId: [$in: resultCategoryIds]])?.collect({ it as CalculationResult })
        }
        return foundResults
    }

    def List<CalculationResult> getCalculationResultObjectsByObjectIds(List<ObjectId> objectIds) {
        List<CalculationResult> foundResults = []

        if (objectIds) {
            objectIds.each { ObjectId id ->
                foundResults.add(CalculationResult.findById(id))
            }
        }
        return foundResults
    }

    def Double getDisplayResult(String indicatorId, String benchmarkSettingRule = null) {
        Double displayResult
        if (indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            if (indicator) {
                String displayRule = benchmarkSettingRule ?: indicator.displayResult
                if (displayRule) {
                    if (indicator.displayDenominator && !benchmarkSettingRule) {
                        displayResult = getTotalResultByNonDynamicDenominator(indicatorId, indicator.displayResult,
                                indicator.displayDenominator.denominatorId)
                    } else {
                        displayResult = getTotalResult(indicatorId, displayRule)
                    }

                    if (displayResult && indicator.showAverageInsteadOfTotal) {
                        Entity parent = parentEntityId ? getParentById() : this
                        CalculationRule rule = indicator.getResolveCalculationRules(parent)?.find({
                            it.calculationRuleId?.equals(displayRule)
                        })
                        List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parent)
                        List<CalculationResult> resultsForIndicator = getCalculationResultObjects(indicator.indicatorId, rule?.calculationRuleId, null)

                        if (resultCategories && rule) {
                            Integer resultsAmount = 0
                            resultCategories.each { ResultCategory resultCategory ->
                                Boolean hideResult = resultCategory.hideResultInReport != null && (resultCategory.hideResultInReport.size() == 0 ||
                                        resultCategory.hideResultInReport.contains(rule.calculationRuleId)) ? Boolean.TRUE : Boolean.FALSE


                                if (!rule.skipRuleForCategories?.contains(resultCategory.resultCategoryId) && !hideResult) {
                                    if (resultsForIndicator?.find({
                                        resultCategory.resultCategoryId.equals(it.resultCategoryId) &&
                                                rule.calculationRuleId.equals(it.calculationRuleId)
                                    })?.result) {
                                        resultsAmount++
                                    }
                                }
                            }

                            if (resultsAmount > 0) {
                                displayResult = displayResult / resultsAmount
                            }
                        }
                    }
                } else if (!indicator.nonNumericResult) {
                    log.error("Indicator ${indicatorId} is missing displayResult")
                }
            } else {
                log.error("No indicator found for indicatorId: ${indicatorId}")
            }
        }
        return displayResult
    }

    def getAverageInsteadOfTotal(String indicatorId, String calculationRuleId) {
        Double result
        if (indicatorId && calculationRuleId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator.showAverageInsteadOfTotal) {
                result = (Double) getTotalResult(indicatorId, calculationRuleId)
                Entity parent = parentEntityId ? getParentById() : this
                List<ResultCategory> resultCategories = indicator.getResolveResultCategories(parent)
                List<CalculationResult> resultsForIndicator = getCalculationResultObjects(indicator.indicatorId, calculationRuleId, null)

                if (resultCategories) {
                    Integer resultsAmount = 0
                    CalculationRule rule = indicator.getResolveCalculationRules(parent)?.find({
                        it.calculationRuleId?.equals(calculationRuleId)
                    })

                    resultCategories.each { ResultCategory resultCategory ->
                        Boolean hideResult = resultCategory.hideResultInReport != null && (resultCategory.hideResultInReport.size() == 0 ||
                                resultCategory.hideResultInReport.contains(calculationRuleId)) ? Boolean.TRUE : Boolean.FALSE


                        if (!rule.skipRuleForCategories?.contains(resultCategory.resultCategoryId) && !hideResult) {
                            if (resultsForIndicator?.find({
                                resultCategory.resultCategoryId.equals(it.resultCategoryId) &&
                                        calculationRuleId.equals(it.calculationRuleId)
                            })?.result) {
                                resultsAmount++
                            }
                        }
                    }

                    if (resultsAmount > 0) {
                        result = result / resultsAmount
                    }
                }
            } else {
                log.error("Indicator does not have averageresult ${indicatorId}")
            }
        }
        return result
    }


    def Boolean resultsValid(Indicator indicator, Boolean indicatorReady = null) {
        Boolean valid = Boolean.FALSE

        if (indicator) {
            if (indicatorReady || isIndicatorReady(indicator)) {
                valid = Boolean.TRUE
            } else {
                List<String> categoryIds = indicator.getResolveResultCategories(parentEntityId ? parentById : this)?.collect({ it.resultCategoryId })

                if (categoryIds) {
                    CalculationTotalResult calculationTotalResult =
                            calculationTotalResults.find({ indicator.indicatorId.equals(it.indicatorId) })

                    if (calculationTotalResult) {
                        List<String> categoryIdsFromResults = calculationTotalResult.dataValidity?.keySet()?.toList()

                        if (categoryIdsFromResults && categoryIdsFromResults.sort().equals(categoryIds.sort())) {
                            valid = Boolean.TRUE
                        }
                    }
                    if (!indicator.nonNumericResult && !calculationResults) {
                        valid = Boolean.TRUE
                    }
                }
            }
        }
        return valid
    }

    def List<Dataset> getDatasetsForResultCategoryExpand(String indicatorId, String resultCategoryId,
                                                         List<CalculationResult> resultsForIndicator = null, Boolean failOver = true) {
        List<Dataset> datasets

        if (indicatorId && resultCategoryId) {
            if (!resultsForIndicator && failOver) {
                resultsForIndicator = getCalculationResultObjects(indicatorId, null, resultCategoryId)
            }

            List<String> datasetIds = resultsForIndicator?.collect({
                it.calculationResultDatasets?.keySet()?.toList()
            })?.flatten()?.unique()

            if (datasetIds) {
                datasets = this.datasets?.findAll({ datasetIds.contains(it.manualId) })?.toList()
            }
        }
        return datasets
    }

    List<Dataset> getDatasetsByIndicator(String indicatorId, List<CalculationResult> resultsForIndicator = null, Boolean failOver = true) {
        List<Dataset> datasets

        if (indicatorId) {
            if (!resultsForIndicator && failOver) {
                resultsForIndicator = getCalculationResultObjects(indicatorId, null, null)
            }
            List<String> datasetIds = resultsForIndicator?.collect({
                it.calculationResultDatasets?.keySet()
            })?.flatten()?.unique()

            if (datasetIds) {
                datasets = this.datasets?.findAll({ datasetIds.contains(it.manualId) })?.toList()
            }
        }
        return datasets
    }

    def List<CalculationResult> getResultObjectsForDataset(String datasetId, String resultCategoryId, String calculationRuleId, List<CalculationResult> resultsForIndicator) {
        List<CalculationResult> foundResults

        if (datasetId && resultsForIndicator) {
            if (resultCategoryId && calculationRuleId) {
                foundResults = resultsForIndicator.findAll({ it.calculationRuleId.equals(calculationRuleId) && it.resultCategoryId.equals(resultCategoryId) && it.calculationResultDatasets?.keySet()?.contains(datasetId) })
            } else if (resultCategoryId) {
                foundResults = resultsForIndicator.findAll({ it.resultCategoryId.equals(resultCategoryId) && it.calculationResultDatasets?.keySet()?.contains(datasetId) })
            } else if (calculationRuleId) {
                foundResults = resultsForIndicator.findAll({ it.calculationRuleId.equals(calculationRuleId) && it.calculationResultDatasets?.keySet()?.contains(datasetId) })
            } else {
                foundResults = resultsForIndicator.findAll({ it.calculationResultDatasets?.keySet()?.contains(datasetId) })
            }
        }
        return foundResults
    }

    def Double getResultForDataset(String datasetId, String indicatorId, String resultCategoryId,
                                   String calculationRuleId, List<CalculationResult> resultsForIndicator = null, Boolean failOver = true) {
        Double result

        if (datasetId && indicatorId && (resultCategoryId || calculationRuleId)) {
            if (resultCategoryId && calculationRuleId) {
                CalculationResult foundResult

                if (resultsForIndicator) {
                    foundResult = resultsForIndicator.findAll({
                        it.calculationRuleId.equals(calculationRuleId) &&
                                it.resultCategoryId.equals(resultCategoryId)
                    })?.
                            find({ it.calculationResultDatasets?.keySet()?.toList()?.contains(datasetId) })
                } else if (failOver) {
                    foundResult = getCalculationResultObjects(indicatorId, calculationRuleId,
                            resultCategoryId)?.find({
                        it.calculationResultDatasets?.keySet()?.toList()?.contains(datasetId)
                    })
                }

                if (foundResult) {
                    result = foundResult.calculationResultDatasets?.get(datasetId)?.result
                }
            } else if (resultCategoryId) {
                List<CalculationResult> foundResults

                if (resultsForIndicator) {
                    foundResults = resultsForIndicator.findAll({ it.resultCategoryId.equals(resultCategoryId) })
                } else if (failOver) {
                    foundResults = getCalculationResultObjects(indicatorId, null, resultCategoryId)
                }

                if (foundResults) {
                    foundResults.each { CalculationResult calculationResult ->
                        Double resultPerDataset = calculationResult.calculationResultDatasets?.get(datasetId)?.result

                        if (resultPerDataset != null) {
                            result = result ? result + resultPerDataset : resultPerDataset
                        }
                    }
                }
            } else {
                List<CalculationResult> foundResults

                if (resultsForIndicator) {
                    foundResults = resultsForIndicator.findAll({ it.calculationRuleId.equals(calculationRuleId) })
                } else if (failOver) {
                    foundResults = getCalculationResultObjects(indicatorId, calculationRuleId, null)
                }

                if (foundResults) {
                    foundResults.each { CalculationResult calculationResult ->
                        Double resultPerDataset = calculationResult.calculationResultDatasets?.get(datasetId)?.result

                        if (resultPerDataset != null) {
                            result = result ? result + resultPerDataset : resultPerDataset
                        }
                    }
                }
            }
        }
        return result
    }

    def getCalculationResourceForDataset(String datasetId, String indicatorId, String resultCategoryId,
                                         List<CalculationResult> resultsForIndicator = null) {
        Resource resource

        if (datasetId && indicatorId && resultCategoryId) {
            List<CalculationResult> foundResults

            if (resultsForIndicator) {
                foundResults = resultsForIndicator.findAll({ resultCategoryId.equals(it.resultCategoryId) })
            } else {
                foundResults = getCalculationResultObjects(indicatorId, null, resultCategoryId)
            }

            if (foundResults) {
                CalculationResult result = foundResults.find({ it.calculationResultDatasets?.get(datasetId) })
                resource = resourceService.getCalculationResourcePerDataset(result, datasetId, datasets)
            }
        }
        return resource
    }

    def getIncinerationEnergyMixResource() {
        def dataset = datasets?.find({ d -> d.queryId == Constants.LCA_PARAMETERS_QUERYID && d.questionId == "incinerationEnergyMix" })
        if (dataset && dataset.answerIds) {
            return optimiResourceService.getResourceWithParams(dataset.answerIds[0].toString(), dataset.additionalQuestionAnswers?.get("profileId"), null)
        } else {
            return null
        }
    }

    def getStateResource() {
        Dataset defaultStateDatasetForLocalResources
        Resource stateResource

        if (parentEntityId) {
            defaultStateDatasetForLocalResources = getParentById()?.datasets?.find({ d -> d.queryId == getBasicQueryId() && d.additionalQuestionAnswers?.state })
        } else {
            defaultStateDatasetForLocalResources = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.additionalQuestionAnswers?.state })
        }

        String stateResourceId = defaultStateDatasetForLocalResources?.additionalQuestionAnswers?.state
        stateResource = stateResourceId ? optimiResourceService.getResourceWithParams(stateResourceId, null, null) : null

        return stateResource
    }

    def getCountryResource() {
        Dataset defaultCountryDatasetForLocalResources
        Resource countryResource

        if (parentEntityId) {
            defaultCountryDatasetForLocalResources = getParentById()?.datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })
        } else {
            defaultCountryDatasetForLocalResources = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })
        }

        if (defaultCountryDatasetForLocalResources?.answerIds) {
            countryResource = optimiResourceService.getResourceWithParams(defaultCountryDatasetForLocalResources.answerIds[0].toString(), null, null)
        }
        return countryResource
    }

    def getCountryResourceResourceId() {
        String countryResource
        Dataset defaultCountryDatasetForLocalResources = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })

        if (defaultCountryDatasetForLocalResources?.answerIds) {
            countryResource = defaultCountryDatasetForLocalResources.answerIds[0].toString()
        }
        return countryResource
    }

    def getCountryResourceDataset() {
        Dataset countryDataset = datasets?.find({ d -> d.queryId == getBasicQueryId() && d.questionId == "country" })
        return countryDataset
    }

    def getTransportationDefault() {
        Dataset d

        if (parentEntityId) {
            d = getParentById()?.datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "transportationDefault" })
        } else {
            d = datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "transportationDefault" })
        }
        String defaultTransportation = d?.answerIds ? d.answerIds[0].toString() : ''

        return defaultTransportation
    }

    def getServiceLifeDefault() {
        Dataset d

        if (parentEntityId) {
            d = getParentById()?.datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "serviceLifeDefault" })
        } else {
            d = datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "serviceLifeDefault" })
        }
        String defaultServiceLife = d?.answerIds ? d.answerIds[0].toString() : ''

        return defaultServiceLife
    }

    def getEOLCalculationMethod() {
        Dataset d

        if (parentEntityId) {
            d = getParentById()?.datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "lcaModel" })
        } else {
            d = datasets?.find({ it.queryId == Constants.LCA_PARAMETERS_QUERYID && it.questionId == "lcaModel" })
        }
        String eolCalculationMethod = d?.answerIds ? d.answerIds[0].toString() : ''

        return eolCalculationMethod
    }

    def getIsAllParameterQueriesReady(List<String> queryIds) {
        Boolean allReady = Boolean.TRUE

        if (queryIds) {
            if (queryReady) {
                for (String queryId in queryIds) {
                    Boolean ready = queryReady.get(queryId)

                    if (!ready) {
                        allReady = Boolean.FALSE
                        break
                    }
                }
            } else {
                allReady = Boolean.FALSE
            }
        }
        return allReady
    }

    def getIsLCAParameterQueryReady() {
        return queryReady?.get(Constants.LCA_PARAMETERS_QUERYID) ? true : false
    }

    def getDefaultTransportFromSubType(Resource resource) {
        Integer defaultValue

        if (defaults && resource) {
            String valueToGet = defaults.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT)

            if (valueToGet) {
                ResourceType subType = resourceService.getSubType(resource)

                if (subType) {
                    String quantityAnswer = DomainObjectUtil.callGetterByAttributeName(valueToGet, subType)?.toString()

                    if (quantityAnswer != null && quantityAnswer.isNumber()) {
                        defaultValue = Math.round(quantityAnswer.toDouble()).intValue()
                    }
                }
            }
        }

        return defaultValue
    }

    def getNameStringWithoutQuotes() {
        String escapedName = ""
        if (operatingPeriodAndName) {
            escapedName = optimiStringUtils.escapeSingleAndDoubleQuotes(operatingPeriodAndName)
        }
        return escapedName
    }

    def getIndicatorIdsList() {
        List<String> indicatorsIdList = []
        if (parentEntityId) {
            Entity parent = getParentById()
            indicatorsIdList = parent?.indicators?.collect({ it.indicatorId })
        } else {
            if (indicators) {
                indicatorsIdList = indicators.collect({ it.indicatorId })
            }
        }

        return indicatorsIdList
    }

    def getConflictingIndicatorsSelected(List<Indicator> selectedIndicators) {
        Boolean conflictingIndicatorsSelected = Boolean.FALSE
        if (selectedIndicators) {
            for (Indicator ind in selectedIndicators) {
                if (ind.compatibleIndicatorIds && !disabledIndicators?.contains(ind.indicatorId)) {
                    List<String> compatibleIndicatorIds = new ArrayList<String>(ind.compatibleIndicatorIds)
                    compatibleIndicatorIds.addAll(["xBenchmarkEmbodied", "xBenchmarkEmbodiedA1-A3", "xBenchmarkOperating", ind.indicatorId])

                    if (selectedIndicators.find({ !compatibleIndicatorIds.contains(it.indicatorId) && !disabledIndicators?.contains(it.indicatorId) })) {
                        conflictingIndicatorsSelected = Boolean.TRUE
                        break
                    }
                }
            }
        }
        return conflictingIndicatorsSelected
    }

    def getIsMaterialSpecifierEntity() {
        return EntityClass.MATERIAL_SPECIFIER.type.toString().equals(entityClass)
    }
}
