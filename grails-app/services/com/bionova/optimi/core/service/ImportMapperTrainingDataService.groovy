package com.bionova.optimi.core.service

import com.bionova.optimi.command.ImportMapperTrainingDataSearchFilter
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.ImportMapperTrainingData
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.domainDTO.ImportMapperTrainingDataDTO
import com.mongodb.BasicDBObject
import com.mongodb.client.AggregateIterable
import com.mongodb.client.result.DeleteResult
import org.apache.commons.lang3.StringUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder

import javax.servlet.http.HttpSession
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class ImportMapperTrainingDataService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy")
    private static final DateFormat DATE_FORMAT_FOR_SORT = new SimpleDateFormat("yyyyMMdd")
    private static final String UNKNOWN_USER = "Unknown user"
    private static final String INVALID_KEY = "Invalid key"
    private static final String COUNT_KEY = "records_number"
    private static final String PAGINATED_RESULTS_KEY = "paginatedResults"
    private static final String TOTAL_COUNT_KEY = "totalCount"

    def userService
    def resourceFilterCriteriaUtil
    def optimiResourceService
    def unitConversionUtil
    def messageSource
    def applicationService
    LoggerUtil loggerUtil
    FlashService flashService

    def getAllTrainingData() {
        return ImportMapperTrainingData.list()
    }

    def getAllTrainingDataCount() {
        return ImportMapperTrainingData.collection.count()
    }

    String getAllReportedTrainingDataCount() {
        List<Document> reportedAsBadAndSameMappings = getAllReportedTrainingData()
        Integer totalSize = reportedAsBadAndSameMappings?.size()
        Integer repotedAsBadSize = reportedAsBadAndSameMappings?.findAll({ it.reportedAsBad })?.size()
        return "${repotedAsBadSize} (${totalSize})"
    }

    long removeImportMapperTrainingDataWithInvalidFields() {
        List<String> invalidRecords = []
        List<ObjectId> invalidObjectIds = ImportMapperTrainingData.collection.find(
                [$or: [
                        [resourceId: [$in: [StringUtils.EMPTY, null]]],
                        [profileId: [$in: [StringUtils.EMPTY, null]]],
                        [applicationId: [$in: [StringUtils.EMPTY, null]]],
                        [importMapperId: [$in: [StringUtils.EMPTY, null]]]
                ]], [_id: 1, resourceId: 1, profileId: 1, applicationId: 1, importMapperId: 1])
                .collect { Document trainingData ->
                    invalidRecords << "(_id: ${trainingData._id}, resourceId: ${trainingData.resourceId}, profileId: ${trainingData.profileId}, " +
                            "applicationId: ${trainingData.applicationId}, importMapperId: ${trainingData.importMapperId})"
                    return trainingData._id
                }

        long countOfRemovedRecords = 0

        if (invalidObjectIds) {
            countOfRemovedRecords = ImportMapperTrainingData.collection.remove([_id: [$in: invalidObjectIds]]).deletedCount

            loggerUtil.error(log, "TRAININGDATA ERROR: ${countOfRemovedRecords} import mapper training data records were removed due to null 'resourceId' " +
                    "or/and null 'profileId' or/and null 'importMapperId' or/and invalid 'applicationId'. Deleted records: ${invalidRecords.join(', ')}.")
        } else {
            loggerUtil.info(log, "No import mapper training data records with null 'resourceId' or/and null 'profileId' or/and null 'importMapperId' " +
                    "or/and invalid 'applicationId' were found.")
        }

        return countOfRemovedRecords
    }

    long removeInvalidImportMapperTrainingData() {
        List<Application> applications = applicationService.getAllApplications()
        List<String> applicationIds = applications.collect { Application application -> application.applicationId }
        Map<String, List<ImportMapper>> importMappers = [:]

        List<String> invalidRecords = []
        List<ObjectId> invalidObjectIds = ImportMapperTrainingData.collection.find().findAll { Document trainingData ->
            String applicationId = trainingData.applicationId

            if (applicationId in applicationIds) {
                if (!importMappers.containsKey(applicationId)) {
                    importMappers << [(applicationId): applications.find { Application application -> application.applicationId == applicationId }.importMappers]
                }

                ImportMapper importMapper = importMappers.get(applicationId).find { trainingData.importMapperId == it.importMapperId }

                if (importMapper) {
                    return false
                } else {
                    invalidRecords << "(_id: ${trainingData._id}, applicationId: ${trainingData.applicationId}, importMapperId: ${trainingData.importMapperId}, " +
                            "application.importMappers: ${importMappers.get(applicationId)*.importMapperId})"

                    return true
                }
            } else {
                invalidRecords << "(_id: ${trainingData._id}, applicationId: ${trainingData.applicationId}, importMapperId: ${trainingData.importMapperId}, " +
                        "application.importMappers: ${importMappers.get(applicationId)*.importMapperId})"

                return true
            }
        }.collect { Document trainingData ->
            trainingData._id
        }

        long countOfRemovedRecords = 0

        if (invalidObjectIds) {
            countOfRemovedRecords = ImportMapperTrainingData.collection.remove([_id: [$in: invalidObjectIds]]).deletedCount

            loggerUtil.error(log, "TRAININGDATA ERROR: ${countOfRemovedRecords} import mapper training data records were removed due to invalid 'applicationId' and " +
                    "'importMapperId'. Deleted records: ${invalidRecords.join(', ')}.")
        } else {
            loggerUtil.info(log, "No import mapper training data records with invalid 'applicationId' and 'importMapperId' were found.")
        }

        return countOfRemovedRecords
    }

    Map getTrainingData(ImportMapperTrainingDataSearchFilter searchFilter) {
        Map result = [:]
        List<Application> applications = applicationService.getAllApplications()
        Map<String, List<ImportMapper>> importMappers = [:]

        Document data = ImportMapperTrainingData.collection.aggregate(getImportMappersQuery(searchFilter)).getAt(0)
        result << [(TOTAL_COUNT_KEY): (data.get(TOTAL_COUNT_KEY)?.getAt(0)?.get(COUNT_KEY) ?: 0L)]

        List<ImportMapperTrainingDataDTO> importMapperTrainingDataDTOs = data.get(PAGINATED_RESULTS_KEY)
                .collect { Document trainingData ->
                    ImportMapperTrainingDataDTO importMapperTrainingData = new ImportMapperTrainingDataDTO()
                    String applicationId = trainingData.applicationId

                    if (!importMappers.containsKey(applicationId)) {
                        importMappers << [(applicationId): applications.find { Application application -> application.applicationId == applicationId }.importMappers]
                    }

                    ImportMapper importMapper = importMappers.get(applicationId).find { trainingData.importMapperId == it.importMapperId }
                    String incomingKey

                    if (importMapper) {
                        importMapper.trainingMatch.each { String key ->
                            String value = trainingData.trainingData.get(key)

                            if (value) {
                                incomingKey = incomingKey ? "${incomingKey}, ${key}: ${value}" : "${key}: ${value}"
                            }
                        }
                    }

                    importMapperTrainingData.id = trainingData._id
                    importMapperTrainingData.applicationId = applicationId
                    importMapperTrainingData.incomingKey = incomingKey ?: INVALID_KEY
                    importMapperTrainingData.importMapperId = trainingData.importMapperId
                    importMapperTrainingData.profileId = trainingData.profileId
                    importMapperTrainingData.resourceId = trainingData.resourceId
                    importMapperTrainingData.trainingData = trainingData.trainingData
                    importMapperTrainingData.userLocale = trainingData.userLocale

                    String id = trainingData._id.toString()
                    importMapperTrainingData.deleteId = id
                    importMapperTrainingData.remapId = id

                    Date time = trainingData.time
                    importMapperTrainingData.time = DATE_FORMAT.format(time)
                    importMapperTrainingData.timeForSort = DATE_FORMAT_FOR_SORT.format(time)

                    Map userData = trainingData.userdata
                    importMapperTrainingData.username = userData ? (userData.username ?: UNKNOWN_USER) : UNKNOWN_USER

                    Map resourceData = trainingData.resourcedata

                    if (resourceData) {
                        importMapperTrainingData.resourceSubType = resourceData.resourceSubType
                        importMapperTrainingData.active = resourceData.active
                    }
                    log.info "resourceData1 ${trainingData}"

                    return importMapperTrainingData
                }

        result << [(PAGINATED_RESULTS_KEY): importMapperTrainingDataDTOs]

        return result
    }

    def getAllReportedTrainingData() {
        List<Document> trainingDatas = []
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")
        DateFormat dateForSort = new SimpleDateFormat("yyyyMMdd")

        Map<String, List<ImportMapper>> importMappers = [:]
        List<Document> trainingDatasReportedBad = ImportMapperTrainingData.collection.find([reportedAsBad: true])?.toList()

        if (trainingDatasReportedBad) {
            List<Document> usersAsDocuments = User.collection.find([username: [$exists:true]],[username:1, _id: 1])?.toList()
            trainingDatasReportedBad.each { Document bad ->
                if (bad.resourceId && bad.profileId && bad.applicationId && bad.importMapperId) {
                    List<Document> asBadTrainingData = ImportMapperTrainingData.collection.find(["resourceId": bad.resourceId, "profileId": bad.profileId, "trainingData.CLASS": bad.trainingData.get("CLASS"), "trainingData.RESOURCEID": bad.trainingData.get("RESOURCEID")])?.toList()

                    if (asBadTrainingData) {
                        asBadTrainingData.sort({it.reportedAsBad}).reverse().each { Document asBad ->
                            if (asBad.resourceId && asBad.profileId && asBad.applicationId && asBad.importMapperId) {
                                if (!importMappers.get(asBad.applicationId)) {
                                    Application application = applicationService.getApplicationByApplicationId(asBad.applicationId?.toString())
                                    if (application) {
                                        importMappers.put((asBad.applicationId.toString()), application.importMappers)
                                    } else {
                                        importMappers.put((asBad.applicationId.toString()), [])
                                        loggerUtil.error(log,"TRAININGDATA ERROR: TRAINING DATA ${asBad._id} WITH UNRECOGNIZED APPLICATIONID: ${asBad.applicationId?.toString()}")
                                        flashService.setErrorAlert("TRAININGDATA ERROR: TRAINING DATA ${asBad._id} WITH UNRECOGNIZED APPLICATIONID: ${asBad.applicationId?.toString()}", true)
                                    }
                                    importMappers.put((asBad.applicationId.toString()), application.importMappers)
                                }
                                ImportMapper importMapper = importMappers.get(asBad.applicationId.toString())?.find({ asBad.importMapperId.toString().equals(it.importMapperId) })

                                if (importMapper) {
                                    String incomingKey

                                    importMapper.trainingMatch.each { String key ->
                                        String value = asBad.trainingData.get(key)

                                        if (value) {
                                            incomingKey = incomingKey ? "${incomingKey}, ${key}: ${value}" : "${key}: ${value}"
                                        }
                                    }
                                    asBad.put("id", asBad._id.toString())
                                    asBad.put("deleteId", asBad._id.toString())
                                    asBad.put("remapId", asBad._id.toString())
                                    asBad.put("removeBadMappingStatusId", asBad.reportedAsBad ? asBad._id.toString() : "")

                                    Date time = (Date) asBad.time
                                    Date timeForSort = (Date) time.clone()

                                    asBad.put("time", dateFormat.format(time))
                                    asBad.put("timeForSort", dateForSort.format(timeForSort))
                                    asBad.put("incomingKey", incomingKey)
                                    asBad.put("username", asBad.user ? usersAsDocuments?.find({it._id?.toString()?.equals(asBad.user)})?.username ?: "Unknown user" : "Unknown user")
                                    asBad.put("reportedBadBy", asBad.reportedAsBad ? asBad.reportedAsBadByUser ? usersAsDocuments?.find({it._id?.toString()?.equals(asBad.reportedAsBadByUser)})?.username ?: "Unknown user" : "Unknown user" : "")
                                    trainingDatas.add(asBad)
                                } else {
                                    ImportMapperTrainingData.collection.remove([_id: asBad._id])
                                }
                            } else {
                                ImportMapperTrainingData.collection.remove([_id: asBad._id])
                            }
                        }
                    }

                } else {
                    ImportMapperTrainingData.collection.remove([_id: bad._id])
                }
            }
        }
        return trainingDatas
    }

    def saveTrainingData(Map<String, String> trainingData, String applicationId, String importMapperId, String trainingComment,
                         String originatingSystem, String originalFileName, String resourceId, String profileId, Boolean compositeMaterial, HttpSession session,
                         String trainingDataCountry, String userId, String organization) {
        if (trainingData && applicationId && importMapperId) {
            User user = userService.getCurrentUser()
            ImportMapperTrainingData importMapperTrainingData = new ImportMapperTrainingData()
            importMapperTrainingData.trainingData = trainingData
            importMapperTrainingData.applicationId = applicationId
            importMapperTrainingData.importMapperId = importMapperId
            importMapperTrainingData.trainingComment = trainingComment
            importMapperTrainingData.originatingSystem = originatingSystem
            importMapperTrainingData.originalFileName = originalFileName
            importMapperTrainingData.isDefault = Boolean.TRUE

            if (compositeMaterial) {
                importMapperTrainingData.resourceId = null
                importMapperTrainingData.profileId = null
                importMapperTrainingData.compositeMaterial = Boolean.TRUE
                importMapperTrainingData.trainingData?.put("COMPOSITE", "true")
            } else {
                importMapperTrainingData.resourceId = resourceId
                importMapperTrainingData.profileId = profileId
            }
            importMapperTrainingData.time = new Date()
            importMapperTrainingData.user = userId ? userId : user?.id?.toString()
            importMapperTrainingData.userLocale = trainingDataCountry
            importMapperTrainingData.organization = organization
            importMapperTrainingData.save(flush: true)
        }
    }

    def updateTrainingData(ImportMapperTrainingData trainingData) {
        if (trainingData?.id && !trainingData?.hasErrors()) {
            trainingData.merge(flush: true)
        }
    }

    def getTrainingDataForDataset(Map<String, String> trainingData, String queryId, String unit = null, String country = null,
                                  String organization = null, Indicator indicator = null, Entity child = null,
                                  List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        AggregateIterable<Document> trainingDataList
        List<Document> trainingDataAsDocuments = []
        if (trainingData) {
            User user = userService.getCurrentUser()
            Set<String> keys = trainingData.keySet()
            BasicDBObject query = new BasicDBObject()

            if (keys) {
                keys.each { String key ->
                    if (trainingData.get(key)) {
                        query.put("trainingData." + key.toUpperCase(),
                                [$regex: "\\Q" + trainingData.get(key) + "\\E", $options: "i"])
                    }
                }
            }
            Document thirdCond = null

            if (country) {
                Document countryEq = new Document("\$eq", ["${country}", "\$userLocale"])

                ArrayList thirdCondArray = new ArrayList()
                thirdCondArray.add(countryEq)
                thirdCondArray.add(1) // weight if Condition true
                thirdCondArray.add(0)

                thirdCond = new Document("\$cond", thirdCondArray)
            }

            Document secondCond = null

            if (organization) {
                Document organizationEq = new Document("\$eq", ["${organization}", "\$organization"])

                ArrayList secondCondArray = new ArrayList()
                secondCondArray.add(organizationEq)
                secondCondArray.add(2) // weight if Condition true
                secondCondArray.add(thirdCond ? thirdCond : 0)

                secondCond = new Document("\$cond", secondCondArray)
            }

            Document localEq = new Document("\$eq", ["${user?.id?.toString()}", "\$user"])
            ArrayList firstCondArray = new ArrayList()
            firstCondArray.add(localEq)
            firstCondArray.add(3) // weight if Condition true, largest weight is at the top of the list
            firstCondArray.add(secondCond ? secondCond : thirdCond ? thirdCond : 0) // if Condition false evaluate second condition

            Document firstCond = new Document("\$cond", firstCondArray)
            Document weight = new Document("weight", firstCond)
            weight.put("user",1)
            weight.put("resourceId",1)
            weight.put("profileId",1)
            weight.put("isDefault",1)
            weight.put("time",1)
            weight.put("reportedAsBad",1)
            weight.put("oclId", 1)

            Document project = new Document("\$project", weight)
            Document sort = new Document("\$sort", new Document("weight", -1).append("time", -1))
            Document match = new Document("\$match", query)

            if (match && project && sort) {
                trainingDataList = ImportMapperTrainingData.collection.aggregate(Arrays.asList(match, project, sort))
                if(log.isTraceEnabled()) {
                    log.trace("Query is: ${Arrays.asList(match, project, sort)}")
                }
                if (trainingDataList) {
                    trainingDataList.each({ Document tdAsDoc ->

                        String resourceId = tdAsDoc.get("resourceId")
                        String profileId = tdAsDoc.get("profileId")
                        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
                        tdAsDoc.put("resource", resource)

                        String privateDatasetAccountId = userService.getAccount(user)?.id?.toString()
                        if (resource && resource.privateDataset &&
                                ((privateDatasetAccountId && resource.privateDatasetAccountId != privateDatasetAccountId) || indicator.preventPrivateData) ) {
                            resource = null
                        }

                        if (resource) {
                            if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(resource, queryId, indicator,
                                    null, child, resourceIdsInDataList, isUncapped)) {
                                if (unit) {
                                    List<String> unitsForResource = unitConversionUtil.getCompatibleUnitsForResource(resource)

                                    if (unitsForResource && unitsForResource*.toLowerCase().contains(unit.trim().toLowerCase())) {
                                        trainingDataAsDocuments.add(tdAsDoc)
                                    }
                                } else {
                                    trainingDataAsDocuments.add(tdAsDoc)
                                }
                            }
                        } else {
                            DeleteResult removed = ImportMapperTrainingData.collection.remove(["_id": tdAsDoc.get("_id")])
                            loggerUtil.warn(log,"""
                                        TrainingData: removing trainingData with an inactive resource: 
                                        ResourceId: ${resourceId} / ProfileId: ${profileId}, 
                                        removed: ${removed?.getDeletedCount()} ${removed?.wasAcknowledged()}
                            """)
                            flashService.setWarningAlert("""
                                        TrainingData: removing trainingData with an inactive resource: 
                                        ResourceId: ${resourceId} / ProfileId: ${profileId}, 
                                        removed: ${removed?.getDeletedCount()} ${removed?.wasAcknowledged()}
                            """, true)
                        }
                    })
                }
            }
        }
        return trainingDataAsDocuments
    }

    def getTrainingDataForDataset(Dataset dataset, String applicationId, String importMapperId, String country = null, String organization = null, Indicator indicator = null,
                                  Entity parent = null, Entity child = null, List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        List<Document> trainingDataAsDocuments = []
        if (!dataset) {
            return trainingDataAsDocuments
        }

        Map<String, String> trainingData = dataset.trainingMatchData
        if (trainingData && applicationId && importMapperId) {
            trainingDataAsDocuments = getTrainingDataForDataset(trainingData, dataset.queryId, dataset.userGivenUnit,
                    country, organization, indicator, child, resourceIdsInDataList, isUncapped)
        }
        return trainingDataAsDocuments
    }

    def getGroupByText(Integer weight, Boolean info = Boolean.FALSE, String organisationName = null) {
        String text

        try {
            if (weight != null) {
                if (info) {
                    if(organisationName){
                        text = messageSource.getMessage("typeahead.info${weight}", [organisationName].toArray(), LocaleContextHolder.getLocale())
                    } else {
                        text = messageSource.getMessage("typeahead.info${weight}", null, LocaleContextHolder.getLocale())

                    }
                } else {
                    text = messageSource.getMessage("typeahead.group${weight}",null, LocaleContextHolder.getLocale())
                }
            }
        } catch (NoSuchMessageException e) {
            loggerUtil.error(log, "Error in getGroupByText", e)
            flashService.setErrorAlert("Error in getGroupByText: ${e.getMessage()}", true)
        }
        return text
    }

    def getWeightFlavor(Integer weight, Boolean system = Boolean.FALSE, Boolean shortHand = Boolean.FALSE, List<String> args = null) {
        String text =null

        try {
            if (shortHand) {
                if (system) {
                    text = messageSource.getMessage("importMapper.trainingData.recognized.system.short", null,
                            LocaleContextHolder.getLocale())
                } else if (weight != null && weight <= 6 && weight >= 0) {
                    text = messageSource.getMessage("importMapper.trainingData.recognized.weigth${weight}.short", args?.toArray(),
                            LocaleContextHolder.getLocale())
                }
            } else {
                if (system) {
                    text = messageSource.getMessage("importMapper.trainingData.recognized.system", null,
                            LocaleContextHolder.getLocale())
                } else if (weight != null && weight <= 6 && weight >= 0) {
                    text = messageSource.getMessage("importMapper.trainingData.recognized.weigth${weight}", null,
                            LocaleContextHolder.getLocale())
                }
            }
        } catch (NoSuchMessageException e) {
            loggerUtil.error(log, "Error in getWeightFlavor", e)
            flashService.setErrorAlert("Error in getWeightFlavor: ${e.getMessage()}", true)
        }
        return text
    }

    Map <String, Document> getTrainingDataForDatasetList(List<Dataset> datasetList, String applicationId, String importMapperId,
                                                       Indicator indicator, String entityCountry, String organization,
                                                       List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        Map <String, Document> importMapperTrainingDataPerDataset = null

        if (datasetList && applicationId && importMapperId) {
            Map<String, List<Document>> datasPerDataset = getAllTrainingDataForDatasetList(datasetList,
                        entityCountry, organization, indicator, null, resourceIdsInDataList, isUncapped)

            if (datasPerDataset) {
                importMapperTrainingDataPerDataset = datasPerDataset.collectEntries {[it.key, it.value?.find()] }
            }
        }

        return importMapperTrainingDataPerDataset
    }

    Map <String, Document> getTrainingDataForDatasetListByImportDisplayField(List<Dataset> datasetList, String importDisplayFieldName,
                                                                           Indicator indicator, String entityCountry, String organization,
                                                                           List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        Map <String, Document> importMapperTrainingDataPerDataset = null

        if (datasetList && importDisplayFieldName) {
            Map<String, List<Document>> datasPerDataset = getAllTrainingDataForDatasetListByImportDisplayField(datasetList,
                    importDisplayFieldName, entityCountry, organization, indicator, null, resourceIdsInDataList, isUncapped)

            if (datasPerDataset) {
                importMapperTrainingDataPerDataset = datasPerDataset.collectEntries {[it.key, it.value?.find()] }
            }
        }

        return importMapperTrainingDataPerDataset
    }

    Map<String, List<Document>> getAllTrainingDataForDatasetList(List<Dataset> datasetList, String country = null,
                                                               String organization = null, Indicator indicator = null, Entity child = null,
                                                               List<String> resourceIdsInDataList = null, boolean isUncapped = false) {

        Map<String, List<Document>> trainingDataPerDataset = [:]

        Set<Map<String, String>> allTrainingDataList = datasetList.findResults{ it.trainingMatchData } as Set

        if (!allTrainingDataList) {
            return trainingDataPerDataset
        }

        List<Document> trainingDataAsDocuments =
                getTrainingDataForTrainingMatchDataList(allTrainingDataList, country, organization, indicator)

        trainingDataPerDataset = datasetList.collectEntries { Dataset dataset ->

            Map<String, String> trainingMatchData = dataset.trainingMatchData
            String queryId = dataset.queryId
            String unit = dataset.userGivenUnit

            List<Document> filteredTrainingData = filterTrainingData(trainingDataAsDocuments, trainingMatchData, queryId, unit, indicator,
                    child, resourceIdsInDataList, isUncapped)

            return [(dataset.manualId): filteredTrainingData]
        }

        return trainingDataPerDataset
    }

    Map<String, List<Document>> getAllTrainingDataForDatasetListByImportDisplayField(List<Dataset> datasetList,
                                                                                   String importDisplayFieldName, String country = null,
                                                                                   String organization = null, Indicator indicator = null, Entity child = null,
                                                                                   List<String> resourceIdsInDataList = null, boolean isUncapped = false) {

        Map<String, List<Document>> trainingDataPerDataset = [:]

        Set<Map<String, String>> allTrainingDataList = [] as Set
        datasetList.each { Dataset dataset ->
            String importDisplayFieldValue = dataset.allImportDisplayFields?.collect({ it?.get(importDisplayFieldName) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(importDisplayFieldName) : null
            if (importDisplayFieldValue) {
                allTrainingDataList << [(importDisplayFieldName): importDisplayFieldValue]
            }
        }

        if (!allTrainingDataList) {
            return trainingDataPerDataset
        }

        List<Document> trainingDataAsDocuments =
                getTrainingDataForTrainingMatchDataList(allTrainingDataList, country, organization, indicator)

        trainingDataPerDataset = datasetList.collectEntries { Dataset dataset ->

            String queryId = dataset.queryId
            String unit = dataset.userGivenUnit
            String importDisplayFieldValue = dataset.allImportDisplayFields?.collect({ it?.get(importDisplayFieldName) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(importDisplayFieldName) : null

            List<Document> filteredTrainingData = []

            if (importDisplayFieldValue) {
                Map<String, String> trainingDataCandidate = [(importDisplayFieldName): importDisplayFieldValue]

                filteredTrainingData = filterTrainingData(trainingDataAsDocuments, trainingDataCandidate,
                        queryId, unit, indicator, child, resourceIdsInDataList, isUncapped)
            }
            return [(dataset.manualId): filteredTrainingData]
        }

        return trainingDataPerDataset
    }

    private List<Document> getTrainingDataForTrainingMatchDataList(Set<Map<String, String>> trainingMatchDataList, String country = null,
                                                           String organization = null, Indicator indicator = null) {

        List<Document> trainingDataAsDocuments = []
        if (!trainingMatchDataList) {
            return trainingDataAsDocuments
        }

        List<Document> trainingDataQuery = getTrainingDataQuery(trainingMatchDataList, country, organization)
        if (!trainingDataQuery) {
            return trainingDataAsDocuments
        }

        User user = userService.getCurrentUser()
        AggregateIterable<Document> trainingDataList = ImportMapperTrainingData.collection.aggregate(trainingDataQuery)

        trainingDataList?.each({ Document tdAsDoc ->

            String resourceId = tdAsDoc.get("resourceId")
            String profileId = tdAsDoc.get("profileId")
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
            tdAsDoc.put("resource", resource)

            String privateDatasetAccountId = userService.getAccount(user)?.id?.toString()
            if (resource && resource.privateDataset &&
                    ((privateDatasetAccountId && resource.privateDatasetAccountId != privateDatasetAccountId) || indicator.preventPrivateData) ) {
                resource = null
            }

            if (resource) {
                trainingDataAsDocuments.add(tdAsDoc)
            } else {
                DeleteResult removed = ImportMapperTrainingData.collection.remove(["_id": tdAsDoc.get("_id")])
                loggerUtil.warn(log,"""
                            TrainingData: removing trainingData with an inactive resource: 
                            ResourceId: ${resourceId} / ProfileId: ${profileId}, 
                            removed: ${removed?.getDeletedCount()} ${removed?.wasAcknowledged()}
                """)
                flashService.setWarningAlert("""
                            TrainingData: removing trainingData with an inactive resource: 
                            ResourceId: ${resourceId} / ProfileId: ${profileId}, 
                            removed: ${removed?.getDeletedCount()} ${removed?.wasAcknowledged()}
                """, true)
            }
        })

        return trainingDataAsDocuments
    }

    private List<Document> getTrainingDataQuery(Set<Map<String, String>> trainingMatchDataList, String country = null, String organization = null) {

        if (!trainingMatchDataList) {
            return []
        }

        User user = userService.getCurrentUser()
        BasicDBObject trainingDataMatchQuery = new BasicDBObject()

        List<BasicDBObject> trainingDataQueries = trainingMatchDataList.collect { Map<String, String> trainingData ->
            BasicDBObject query = new BasicDBObject()
            trainingData.each { String key, String value ->
                if (value) {
                    query.put("trainingData." + key.toUpperCase(),
                            [$regex: "\\Q" + value + "\\E", $options: "i"])
                }
            }
            return query
        }

        if (trainingDataQueries?.size() > 1) {
            trainingDataMatchQuery.put("\$or", trainingDataQueries)
        } else if(trainingDataQueries?.size() == 1) {
            trainingDataMatchQuery = trainingDataQueries.first()
        }

        Document thirdCond = null

        if (country) {
            Document countryEq = new Document("\$eq", ["${country}", "\$userLocale"])

            ArrayList thirdCondArray = new ArrayList()
            thirdCondArray.add(countryEq)
            thirdCondArray.add(1) // weight if Condition true
            thirdCondArray.add(0)

            thirdCond = new Document("\$cond", thirdCondArray)
        }

        Document secondCond = null

        if (organization) {
            Document organizationEq = new Document("\$eq", ["${organization}", "\$organization"])

            ArrayList secondCondArray = new ArrayList()
            secondCondArray.add(organizationEq)
            secondCondArray.add(2) // weight if Condition true
            secondCondArray.add(thirdCond ? thirdCond : 0)

            secondCond = new Document("\$cond", secondCondArray)
        }

        Document localEq = new Document("\$eq", ["${user?.id?.toString()}", "\$user"])
        ArrayList firstCondArray = new ArrayList()
        firstCondArray.add(localEq)
        firstCondArray.add(3) // weight if Condition true, largest weight is at the top of the list
        firstCondArray.add(secondCond ? secondCond : thirdCond ? thirdCond : 0) // if Condition false evaluate second condition

        Document firstCond = new Document("\$cond", firstCondArray)
        Document weight = new Document("weight", firstCond)
        weight.put("user",1)
        weight.put("resourceId",1)
        weight.put("profileId",1)
        weight.put("isDefault",1)
        weight.put("time",1)
        weight.put("reportedAsBad",1)
        weight.put("oclId", 1)
        weight.put("trainingData", 1)

        Document project = new Document("\$project", weight)
        Document sort = new Document("\$sort", new Document("weight", -1).append("time", -1))
        Document match = new Document("\$match", trainingDataMatchQuery)

        if (!(match && project && sort)) {
            log.trace("Invalid Query: match='${match}', project='${project}', sort='${sort}'")
            return
        }

        List<Document> trainingDataQuery = Arrays.asList(match, project, sort)

        if (log.isTraceEnabled()) {
            log.trace("Query is: ${trainingDataQuery}")
        }

        return trainingDataQuery
    }


    private List<Document> filterTrainingData(List<Document> trainingDataToFilter, Map<String, String> trainingMatchData,
                                              String queryId, String unit, Indicator indicator = null, Entity child = null,
                                              List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        return trainingDataToFilter.findAll { Document trainingData ->

            if (!isTrainingDataMatch(trainingData, trainingMatchData)) {
                return false
            }

            return isResourceValid(trainingData.resource, queryId, unit, indicator,
                    child, resourceIdsInDataList, isUncapped)
        }
    }

    private Boolean isTrainingDataMatch(Document trainingData, Map<String, String> trainingMatchData) {
        if (!trainingData || !trainingMatchData) {
            return false
        }

        boolean match = true
        trainingMatchData.each { String key, String value ->

            if (match && !(trainingData.trainingData?."$key" =~ /(?i)\Q$value\E/)) {
                match = false
            }
        }

        return match
    }

    private Boolean isResourceValid(Resource resource, String queryId, String unit,
                                    Indicator indicator = null, Entity child = null,
                                    List<String> resourceIdsInDataList = null, boolean isUncapped = false) {

        boolean isValid = false

        if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(resource, queryId, indicator,
                null, child, resourceIdsInDataList, isUncapped)) {
            if (unit) {
                List<String> unitsForResource = unitConversionUtil.getCompatibleUnitsForResource(resource)

                if (unitsForResource && unitsForResource*.toLowerCase().contains(unit.trim().toLowerCase())) {
                    isValid = true
                }
            } else {
                isValid = true
            }
        }

        return isValid
    }

    def getTrainingData(Dataset dataset, String applicationId, String importMapperId, Indicator indicator,
                        String entityCountry, String organization, Entity parent,
                        List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        Document importMapperTrainingData = null

        if (dataset && applicationId && importMapperId) {
            List<Document> datas = getTrainingDataForDataset(dataset, applicationId, importMapperId, entityCountry,
                    organization, indicator, parent, null, resourceIdsInDataList, isUncapped)
            if (datas) {
                importMapperTrainingData = datas.first()
            }
        }

        return importMapperTrainingData
    }

    def getTrainingData(Map<String, String> trainingDataCandidate, String queryId, String unit = null, Indicator indicator,
                        String entityCountry, String organization, Entity child,
                        List<String> resourceIdsInDataList = null, boolean isUncapped = false) {
        Document importMapperTrainingData = null

        if (trainingDataCandidate ) {
            List<Document> datas = getTrainingDataForDataset(trainingDataCandidate, queryId, unit, entityCountry,
                    organization, indicator, child, resourceIdsInDataList, isUncapped)
            if (datas) {
                importMapperTrainingData = datas.first()
            }
        }

        return importMapperTrainingData
    }

    def deleteTrainingData(String id) {
        boolean deleteOk = false

        if (id) {
            ImportMapperTrainingData trainingData = ImportMapperTrainingData.findById(DomainObjectUtil.stringToObjectId(id))

            if (trainingData) {
                trainingData.delete(flush: true, failOnError:true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    def getTrainingDataById(String id) {
        ImportMapperTrainingData trainingData

        if (id) {
            trainingData = ImportMapperTrainingData.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return trainingData
    }

    def getDuplicateTrainingDataForUser(Map<String, String> trainingData, String userId) {
        List<ImportMapperTrainingData> duplicates
        if (trainingData && userId) {
            List<String> keys = trainingData.keySet().toList()
            Map query = [:]

            if (keys) {
                keys.each { String key ->
                    if (trainingData.get(key)) {
                        query.put("trainingData." + key.toUpperCase(), [$regex: "\\Q" + trainingData.get(key) + "\\E", $options: "i"])
                    }
                }
            }
            query.put("user", userId)

            if (query) {
                duplicates = ImportMapperTrainingData.collection.find(query)?.collect({ it as ImportMapperTrainingData })
            }
        }
        return duplicates
    }

    private List<BasicDBObject> getImportMappersQuery(ImportMapperTrainingDataSearchFilter searchFilter) {
        List<BasicDBObject> pipeline = []
        List<BasicDBObject> paginatedResultsSubPipeline = []

        pipeline << new BasicDBObject([
                $match: [
                        resourceId: getRegexRestrictionByParameter(searchFilter.resourceId),
                        profileId: getRegexRestrictionByParameter(searchFilter.profileId),
                        importMapperId: getRegexRestrictionByParameter(searchFilter.importMapperId),
                        applicationId: getRestrictionByApplicationId(searchFilter.applicationId)
                ]
        ])

        pipeline << new BasicDBObject([$sort: [_id: 1]])

        if ((searchFilter.resourceStatus != null) || searchFilter.resourceSubType) {
            pipeline << new BasicDBObject([
                    $lookup: [
                            from: "resource",
                            let: [ resource_id: "\$resourceId", profile_id: "\$profileId" ],
                            pipeline: [
                                    [ $match: [ $expr: [ $and: [
                                            [ $eq: [ "\$resourceId", "\$\$resource_id" ] ],
                                            [ $eq: [ "\$profileId", "\$\$profile_id" ] ]
                                    ] ] ] ],
                                    [ $project: [ _id: 1, resourceId: 1, active: 1, resourceSubType: 1 ] ]
                            ],
                            as: "resourcedata"
                    ]
            ])

            if (searchFilter.resourceStatus != null) {
                pipeline << new BasicDBObject([ $match: [ "resourcedata.0.active": searchFilter.resourceStatus ] ])
            }

            if (searchFilter.resourceSubType) {
                pipeline << new BasicDBObject([ $match: [ "resourcedata.0.resourceSubType": searchFilter.resourceSubType ] ])
            }

            if (searchFilter.offset) {
                paginatedResultsSubPipeline << new BasicDBObject([$skip: searchFilter.offset])
            }

            if (searchFilter.limit) {
                paginatedResultsSubPipeline << new BasicDBObject([$limit: searchFilter.limit])
            }
        } else {
            if (searchFilter.offset) {
                paginatedResultsSubPipeline << new BasicDBObject([$skip: searchFilter.offset])
            }

            if (searchFilter.limit) {
                paginatedResultsSubPipeline << new BasicDBObject([$limit: searchFilter.limit])
            }

            paginatedResultsSubPipeline << new BasicDBObject([
                    $lookup: [
                            from: "resource",
                            let: [ resource_id: "\$resourceId", profile_id: "\$profileId" ],
                            pipeline: [
                                    [ $match: [ $expr: [ $and: [
                                            [ $eq: [ "\$resourceId", "\$\$resource_id" ] ],
                                            [ $eq: [ "\$profileId", "\$\$profile_id" ] ]
                                    ] ] ] ],
                                    [ $project: [ _id: 1, resourceId: 1, active: 1, resourceSubType: 1 ] ]
                            ],
                            as: "resourcedata"
                    ]
            ])
        }

        paginatedResultsSubPipeline << new BasicDBObject([
                $lookup: [
                        from: "user",
                        let: [ user_id: [ $convert: [ input: "\$user", to: "objectId", onError: null ] ] ],
                        pipeline: [
                                [ $match: [ $expr: [ $eq: [ "\$_id",  "\$\$user_id" ] ] ] ],
                                [ $project: [ _id: 1, username: 1 ] ]
                        ],
                        as: "userdata"
                ]
        ])

        paginatedResultsSubPipeline << new BasicDBObject([
                $project: [
                        _id: 1,
                        applicationId: 1,
                        importMapperId: 1,
                        profileId: 1,
                        resourceId: 1,
                        resourcedata: [ $arrayElemAt: [ "\$resourcedata", 0 ] ],
                        trainingData: 1,
                        time: 1,
                        userdata: [ $arrayElemAt: [ "\$userdata", 0 ] ],
                        userLocale: 1
                ]
        ])

        pipeline << new BasicDBObject([
                $facet: [
                        (PAGINATED_RESULTS_KEY): paginatedResultsSubPipeline,
                        (TOTAL_COUNT_KEY): [[ $count: COUNT_KEY ]]
                ]
        ])

        log.debug "Result pipeline is: ${pipeline}"

        return pipeline
    }

    private Map getRegexRestrictionByParameter(String parameter) {
        Map regexRestrictionByParameter

        if (parameter) {
            regexRestrictionByParameter = [ $regex: parameter ]
        } else {
            regexRestrictionByParameter = [ $nin: [StringUtils.EMPTY, null] ]
        }

        return regexRestrictionByParameter
    }

    private Map getRestrictionByApplicationId(String applicationId) {
        Map restrictionByApplicationId

        if (applicationId) {
            restrictionByApplicationId = [ $eq: applicationId ]
        } else {
            restrictionByApplicationId = [ $in: Application.collection.distinct("applicationId", String.class).toList() ]
        }

        return restrictionByApplicationId
    }
}
