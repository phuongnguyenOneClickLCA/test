package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.taglib.AdditionalQuestionTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.UnitConversionUtil
import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.web.multipart.MultipartFile

import java.lang.reflect.Method

@Transactional
class ConstructionService {
    GrailsApplication grailsApplication
    UserService userService
    OptimiResourceService optimiResourceService
    QueryService queryService
    QuestionService questionService
    DomainClassService domainClassService
    ImportMapperService importMapperService
    SimulationToolService simulationToolService
    UnitConversionUtil unitConversionUtil
    LoggerUtil loggerUtil
    FlashService flashService
    ConfigurationService configurationService
    StringUtilsService stringUtilsService
    NmdResourceService nmdResourceService
    LocalizationService localizationService
    DatasetService datasetService

    List <Construction> getConstructions() {
        List <Construction> constructions = Construction.list()
        return constructions
    }

    Construction getConstruction(String id) {
        Construction construction
        if (id) {
            construction = Construction.get(DomainObjectUtil.stringToObjectId(id))
        }
        return construction
    }

    public Boolean getConstructionInheritServiceLife(String id) {
        List inheritConstructionServiceLife = Construction.withCriteria {
            eq("id", DomainObjectUtil.stringToObjectId(id))
            projections {
                property('inheritConstructionServiceLife')
            }
        }
        if (!inheritConstructionServiceLife.isEmpty()) {
            Boolean inherit = inheritConstructionServiceLife.first() ? Boolean.TRUE : Boolean.FALSE
            return inherit
        } else {
            return Boolean.FALSE
        }
    }

    Document getConstructionAsDocument(String id, Map projection = null) {
        Document construction
        if (id) {
            if (projection) {
                construction = Construction.collection.findOne([_id:DomainObjectUtil.stringToObjectId(id)], projection)
            } else {
                construction = Construction.collection.findOne([_id:DomainObjectUtil.stringToObjectId(id)])
            }
        }
        return construction
    }

    List<Document> getConstructionsAsDocuments(Map<String, Object> query, Map projection = null) {
        List<Document> constructions
        if (projection) {
            constructions = Construction.collection.find(query, projection)?.toList()
        } else {
            constructions = Construction.collection.find(query)?.toList()
        }
        return constructions
    }

    List<Construction> getConstructionsByConstructionId(String constructionId) {
        if (!constructionId) {
            return null
        }

        return Construction.findAllByConstructionId(constructionId)
    }

    Construction getConstructionWithConstructionId(String constructionId) {
        Construction construction = null
        if (constructionId) {
            construction = Construction.findByConstructionId(constructionId)
        }
        return construction
    }

    List<Construction> getConstructionsByAccountId(String privateConstructionAccountId) {
        List<Construction> constructions = []
        if (privateConstructionAccountId) {
            constructions = Construction.findAllByPrivateConstructionAccountId(privateConstructionAccountId)
        }
        return constructions
    }

    Boolean getAccountHasPrivateConstructions(String privateConstructionAccountId) {
        Boolean hasPrivateData = Boolean.FALSE
        if (Construction.collection.count([privateConstructionAccountId: privateConstructionAccountId])) {
            hasPrivateData = Boolean.TRUE
        }
        return hasPrivateData
    }

    List <Construction> getConstructionsByGroup(String group) {
        List <Construction> constructions = []
        if (group) {
            constructions = Construction.findAllByConstructionGroup(group)
        }
        return constructions
    }

    List <ConstructionGroup> getAllConstructionGroups() {
        return ConstructionGroup.list()
    }

    List <ConstructionGroup> getAllEarlyPhaseConstructionGroups() {
        return ConstructionGroup.findAllByEarlyPhase(true)
    }

    ConstructionGroup getConstructionGroupById(String id) {
        ConstructionGroup constructionGroup
        if (id) {
            constructionGroup = ConstructionGroup.get(DomainObjectUtil.stringToObjectId(id))
        }
        return constructionGroup
    }

    ConstructionGroup getConstructionGroupByGroupId(String groupId) {
        ConstructionGroup constructionGroup
        if (groupId) {
            constructionGroup = ConstructionGroup.findByGroupId(groupId)
        }
        return constructionGroup
    }
    ConstructionGroup getConstructionGroupByName(String name) {
        ConstructionGroup constructionGroup
        if (name) {
            constructionGroup = ConstructionGroup.findByName(name)
        }
        return constructionGroup
    }

    ConstructionGroup getConstructionGroupByAccountId(String accountId) {
        ConstructionGroup constructionGroup
        if (accountId) {
            constructionGroup = ConstructionGroup.findByPrivateAccountId(accountId)
        }
        return constructionGroup
    }

    List <Question> getAdditionalQuestions (List<String> questionIds) {
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        List <Question> allQuestions = additionalQuestionsQuery?.getAllQuestions()
        List <Question> additionalQuestions = []
        questionIds.each { String questionId ->
            Question q = allQuestions?.find({
                questionId.equals(it.questionId)
            })

            if (q && !additionalQuestions.contains(q)) {
                additionalQuestions.add(q)
            }

        }
        return additionalQuestions
    }

    void updateStringFieldsInConstruction(Map<String, List<Map<String, String>>> config, Construction construction) {
        if (!construction || !config) {
            return
        }
        stringUtilsService.updateStringInObject(config, construction)
    }

    Map importExcel(MultipartFile excelFile, String queryId, String group, String accountId = null, String accountName, Boolean isUploadingNmd = false) {
        Map returnable = [:]
        List<Construction> constructions = []
        try {
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            String fileName = excelFile.originalFilename
            Integer numberOfSheets = workbook?.getNumberOfSheets()

            log.info("number of sheets ${numberOfSheets}")
            flashService.setFadeInfoAlert("number of sheets ${numberOfSheets}", true)

            Map<String, List<Map<String, String>>> mapOfConstructions = [:]
            List<String> constructionIds = []
            List<Map<String, String>> rowsAsMaps = []

            if (numberOfSheets) {
                returnable.put("filename:", fileName)
                returnable.put("numberOfSheets:", numberOfSheets)


                for (int i = 0; i < numberOfSheets; i++) {
                    Map<String, Integer> columnHeadings = [:]
                    Sheet sheet = workbook.getSheetAt(i)
                    int physicalRows = sheet.physicalNumberOfRows
                    List<Row> rowsPerSheet = []

                    if (sheet && !sheet.sheetName.contains("@")) {

                        // handle headings
                        log.info("handling sheet ${sheet.sheetName}")
                        returnable.put("sheetWithData: ${sheet.sheetName}", "physicalNumberOfRows: ${physicalRows}")

                        Row headingRow = sheet.getRow(0)
                        Iterator<Cell> cellIterator = headingRow.cellIterator()
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next()
                            String heading = importMapperService.getCellValue(cell)

                            if (heading) {
                                columnHeadings.put(heading, cell.columnIndex)
                            }
                        }
                        // handleRows

                        Integer rowNro = 0
                        Iterator<Row> rowIterator = sheet.rowIterator()
                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next()
                            if (rowNro != 0) {
                                rowsPerSheet.add(row)
                            }
                            rowNro++
                        }

                        log.info("handled rows: ${rowsPerSheet.size()}")

                        if (rowsPerSheet) {
                            rowsPerSheet.each { Row rowPerSheet ->
                                Map<String, String> rowAsMap = [:]
                                columnHeadings.each { String key, Integer value ->
                                    Cell cell = rowPerSheet.getCell(value)
                                    String valueOfColumnHeading = importMapperService.getCellValue(cell)

                                    if (valueOfColumnHeading) {
                                        if (key.equalsIgnoreCase("classificationParamId")) {
                                            valueOfColumnHeading = StringUtils.substringBefore(valueOfColumnHeading, ".")
                                        }
                                        if (valueOfColumnHeading.contains("\"") || valueOfColumnHeading.contains("\'")) {
                                            throw new IllegalArgumentException("Error in parsing a cell at sheet [${sheet.sheetName}], at row [${rowPerSheet.rowNum}] and cell [${value}]: ${rowPerSheet.getCell(value)}, it contains illegal characters \" or \'")

                                        } else if (!valueOfColumnHeading.startsWith("@")) {
                                            rowAsMap.put(key, valueOfColumnHeading)
                                        }
                                    }
                                }
                                String constructionId = importMapperService.getCellValue(rowPerSheet.getCell(0))
                                if (constructionId && !constructionId.contains("@") && !constructionIds.contains(constructionId) && columnHeadings.get("parent")) {
                                    constructionIds.add(constructionId)
                                }
                                if (rowAsMap.size()) {
                                    rowsAsMaps.add(rowAsMap)
                                }
                            }
                        }
                    }
                }
                constructionIds.each { String constructionId ->
                    List<Map<String, String>> mapsPerConstruction = rowsAsMaps.findAll({ it.get("constructionId")?.equals(constructionId) })
                    mapOfConstructions.put(constructionId, mapsPerConstruction)
                }

                Map<String, Object> importedConstructions = createImportedConstructions(mapOfConstructions, queryId, group, accountId, accountName, fileName, isUploadingNmd)
                constructions = importedConstructions?.get("constructions")
                String errorConstructions = importedConstructions?.get("errors")

                if (errorConstructions) {
                    returnable.put("ImportErrors", errorConstructions)
                }
            }
            returnable.put("created constructions", constructions?.size())
            log.info("Imported excelfile ${fileName} with constructions: ${constructions?.size()}")
            flashService.setFadeInfoAlert("Imported excelfile ${fileName} with constructions: ${constructions?.size()}", true)

        } catch (IOException ex) {
            // Catch invalid file.
            if (!returnable.get("created constructions")) {
                returnable.put("created constructions", 0)
            }
            returnable.put("Exception", "File type is not supported. Supported upload formats are valid xls or xlsx files")
        } catch (Exception e) {
            returnable.put("Exception", e.getMessage())
            if (!returnable.get("created constructions")) {
                returnable.put("created constructions", 0)
            }
            loggerUtil.error(log, "Construction import error ", e)
            flashService.setErrorAlert("Error occurred in : ${e.getMessage()}", true)
        }
        return returnable
    }

    Map<String, Object> createImportedConstructions(Map<String, List<Map<String, String>>> mapOfConstructions, String queryId, String group, String accountId = null, String accountName = null, String importFile = null, Boolean isUploadingNmd = false) {
        String importErrors = ""
        List<Construction> constructions = []
        Query constructionCreationQuery = queryService.getQueryByQueryId(queryId, true)

        List<PersistentProperty> constructionPersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Construction.class)

        List<String> persistingRegularProperties = constructionPersistentProperties?.collect({ it.name })
        List<String> stringProperties = constructionPersistentProperties?.findAll({ it.type.equals(String) })?.collect({ it.name })
        List<String> integerProperties = constructionPersistentProperties?.findAll({ it.type.equals(Integer) })?.collect({ it.name })
        List<String> booleanProperties = constructionPersistentProperties?.findAll({ it.type.equals(Boolean) })?.collect({ it.name })
        List<String> persistingListProperties = constructionPersistentProperties?.findAll({it.type?.equals(List)})?.collect({ it.name })

        // get config to remove chars in nmd construction
        Map<String, List<Map<String, String>>> updateStringForNmdConstructionConfig = nmdResourceService.getUpdateStringOnImportNmd()

        mapOfConstructions.each { String constructionId, List<Map<String, String>> maps ->
            Map<String, String> parentConstructionMap = maps.find({ it.get("parent") })
            Construction construction = getConstructionWithConstructionId(constructionId)

            if (!construction) {
                construction = new Construction()
            }

            Map<String, Object> additionalQuestionAnswers = [:]
            parentConstructionMap.each { String key, String value ->

                if (persistingRegularProperties?.contains(key) && value != null) {
                    if (booleanProperties?.contains(key)) {
                        Boolean boolValue = value.toBoolean()

                        if (boolValue != null) {
                            DomainObjectUtil.callSetterByAttributeName(key, construction, boolValue)
                        }
                    } else if (DomainObjectUtil.isNumericValue(value) && !stringProperties?.contains(key)) {
                        Double parsedNumber = DomainObjectUtil.convertStringToDouble(value)

                        if (integerProperties?.contains(key) && parsedNumber && (parsedNumber % 1) == 0) {
                            parsedNumber = parsedNumber.intValue()
                        }
                        DomainObjectUtil.callSetterByAttributeName(key, construction, parsedNumber)
                    } else {
                        DomainObjectUtil.callSetterByAttributeName(key, construction, value)
                    }
                }
                if (persistingListProperties?.contains(key) && value != null) {
                    DomainObjectUtil.callSetterByAttributeName(key, construction, value?.tokenize(",")?.toList())
                }
                List<String> additionalQuestionIds = questionService.getQuestion(constructionCreationQuery, construction.classificationQuestionId)?.choices?.find({
                    it.answerId.equals(construction.classificationParamId)
                })?.constructionAdditionalQuestionIds

                additionalQuestionIds?.each { String questionId ->
                    if (questionId.equals(key)) {
                        additionalQuestionAnswers.put(questionId, value)
                    }
                }
            }

            String comment = parentConstructionMap?.get("comment")
            if(comment) additionalQuestionAnswers.put("comment", comment)

            if (additionalQuestionAnswers) {
                construction.additionalQuestionAnswers = additionalQuestionAnswers
            }
            construction.constructionId = constructionId
            construction.constructionGroup = group
            construction.privateConstructionAccountId = accountId
            construction.privateConstructionCompanyName = accountName
            construction.importFile = importFile

            if (isUploadingNmd) {
                updateStringFieldsInConstruction(updateStringForNmdConstructionConfig, construction)
            }

            if (construction && construction.validate()) {
                QuestionAnswerChoice classificationParam = questionService.getQuestion(constructionCreationQuery, construction.classificationQuestionId)?.choices?.find({ construction.classificationParamId.equals(it.answerId) })
                if (!classificationParam) {
                    importErrors += "Rejected construction: ${construction.constructionId} because it has invalid classificationParamId ${construction.classificationParamId}. No match could be found with classificationQuestionId ${construction.classificationQuestionId}<br>"
                } else {
                    Map<String, String> resourceParameters = classificationParam?.resourceParameters
                    String componentQuestionId =  classificationParam?.componentQuestionId
                    Map<String, String> lockMandatoryFilters = classificationParam?.lockMandatoryFilters
                    construction = construction.save(flush: true, failOnError: true)
                    Question componentQuestion = constructionCreationQuery?.allQuestions?.find({componentQuestionId?.equals(it.questionId)})
                    List<String> additionalQuestionIds = componentQuestion?.additionalQuestionIds
                    String sectionId = componentQuestion?.sectionId
                    Map<String, Object> newlyCreatedConstruction = createConstructionDatasetsForImport(construction, maps.findAll({!it.get("parent")}), queryId,sectionId, componentQuestionId, additionalQuestionIds)
                    construction = newlyCreatedConstruction?.get("construction")
                    String constituentError = newlyCreatedConstruction?.get("errors")

                    Boolean constructionPassesFilter = Boolean.TRUE
                    String filterError = ""

                    if (constituentError) {
                        filterError = "${filterError}${constituentError}"
                        constructionPassesFilter = Boolean.FALSE
                    } else if (construction.datasets && !construction.datasets.isEmpty()) {
                        if (lockMandatoryFilters) {
                            ResourceCache resourceCache = ResourceCache.init(construction.datasets)
                            for (Dataset dataset: construction.datasets) {
                                Resource resource = resourceCache.getResource(dataset)

                                if (resource) {
                                    lockMandatoryFilters.each { String key, String value ->
                                        def resourceValue = DomainObjectUtil.callGetterByAttributeName(key, resource)

                                        if (resourceValue) {
                                            if (resourceValue instanceof List) {
                                                if (!resourceValue.contains(value)) {
                                                    filterError = "${filterError}Rejected construction: ${construction.constructionId}, its constituent ${resource.resourceId} fails filter check for Key: ${key} and value: ${value}, Resource has value: ${resourceValue}<br />"
                                                    constructionPassesFilter = Boolean.FALSE
                                                }
                                            } else {
                                                if (resourceValue.toString() != value) {
                                                    filterError = "${filterError}Rejected construction: ${construction.constructionId}, its constituent ${resource.resourceId} fails filter check for Key: ${key} and value: ${value}, Resource has value: ${resourceValue}<br />"
                                                    constructionPassesFilter = Boolean.FALSE
                                                }
                                            }
                                        }

                                    }
                                } else {
                                    filterError = "${filterError}Rejected construction: ${construction.constructionId}, no active resource found for resourceId: ${dataset.resourceId} for constituent<br />"
                                    constructionPassesFilter = Boolean.FALSE
                                }
                            }
                        }
                    } else {
                        filterError = "${filterError}Rejected construction: ${construction.constructionId}, no constituents<br />"
                        constructionPassesFilter = Boolean.FALSE
                    }

                    if (constructionPassesFilter) {
                        createConstructionResource(construction, resourceParameters)
                        constructions.add(construction)
                    } else {
                        importErrors = "${importErrors}${filterError}"
                        construction.delete(flush: true)
                    }
                }
            } else {
                importErrors = "${importErrors}${construction.errors.getAllErrors()}<br>"
            }
        }
        Map<String, Object> returnable = [:]
        returnable.put("constructions", constructions)
        returnable.put("errors", importErrors)
        return returnable

    }

    Map<String, Object> createConstructionDatasetsForImport(Construction construction, List<Map<String, String>> maps, String queryId, String sectionId, String questionId, additionalQuestionIds) {
        List<Dataset> datasets = []
        String filterError = ""

        if (construction && maps) {
            maps?.each { Map<String, String> datasetMap ->
                String resourceIdFromMap = datasetMap.get("resourceId")

                if (optimiResourceService.getResourceWithGorm(resourceIdFromMap, null, Boolean.TRUE)) {
                    Dataset dataset = new Dataset()
                    dataset.quantity = datasetMap.get("quantity")?.toDouble()
                    dataset.resourceId = datasetMap.get("resourceId")
                    dataset.answerIds = [dataset.quantity.toString()]
                    dataset.userGivenUnit = datasetMap.get("unit")
                    dataset.scalingType = datasetMap.get("scalingType")
                    dataset.groupId = datasetMap.get("groupId")

                    String layerUValue = datasetMap.get("layerUValue")
                    dataset.layerUValue = layerUValue?.isNumber() ? layerUValue.toDouble() : null
                    dataset.defaultConstituent = true
                    dataset.flexibleResourceIdList = getFlexibleResourceList(datasetMap) ?: null

                    String comment = datasetMap.get("comment")
                    /*
                    String defaultConstituent = datasetMap.get("defaultConstituent")
                    List<String> defaultForRegionList = datasetMap.get("defaultForRegionList")?.split(",")?.toList()
                    defaultForRegionList = defaultForRegionList?.collect{it?.trim()}
                    dataset.defaultForRegionList = defaultForRegionList ?: []


                    if (defaultConstituent) {
                        if (defaultConstituent.isNumber()) {
                            dataset.defaultConstituent = defaultConstituent.toDouble().intValue() == 1 ? Boolean.TRUE : Boolean.FALSE
                        } else {
                            dataset.defaultConstituent = defaultConstituent.toBoolean()
                        }
                    }
                    */


                    dataset.questionId = questionId
                    dataset.queryId = queryId
                    dataset.sectionId = sectionId

                    Map additionalQuestionAnswers = [:]

                    if(comment) additionalQuestionAnswers.put("comment", comment)
                    additionalQuestionIds?.each { String id ->
                        if (datasetMap.containsKey(id)) {
                            additionalQuestionAnswers.put(id, datasetMap.get(id))
                        }
                    }

                    if (additionalQuestionAnswers) {
                        dataset.additionalQuestionAnswers = additionalQuestionAnswers
                    }

                    if (dataset) {
                        datasets.add(dataset)
                        if(dataset.flexibleResourceIdList){
                            List<Dataset> flexibleResources = createFlexibleResourceList(dataset)
                            if(flexibleResources) datasets.addAll(flexibleResources)
                        }
                    }
                } else {
                    filterError = "${filterError}Rejected construction: <b>${construction.constructionId}</b>, its constituent ${resourceIdFromMap} was unable to find a corresponding resource<br />"
                }
            }

            if (datasets) {
                datasets = handleMissingManualIds(datasets)
                datasets = addDefaultProfilesIfMissing(datasets)

                if (construction.inheritConstructionServiceLife && construction.serviceLife != null) {
                    datasets.each {
                        if (it.additionalQuestionAnswers) {
                            it.additionalQuestionAnswers.put("serviceLife", construction.serviceLife)
                        } else {
                            it.additionalQuestionAnswers = [serviceLife: construction.serviceLife]
                        }
                    }
                }

                construction.datasets = datasets
                construction.resourceIds = datasets.collect({it.resourceId})
                construction.merge(flush: true, failOnError: true)
            }
        }
        Map<String, Object> returnable = [:]
        returnable.put("construction", construction)
        if (filterError) {
            returnable.put("errors", filterError)
        }
        return returnable

    }

    List<Dataset> addDefaultProfilesIfMissing(List<Dataset> datasets) {
        datasets?.each { Dataset d ->
            if (d.resourceId && !d.profileId) {
                String defaultProfile = optimiResourceService.getDefaultProfileForResource(d.resourceId)

                if (defaultProfile) {
                    d.profileId = defaultProfile

                    if (d.additionalQuestionAnswers) {
                        d.additionalQuestionAnswers.put("profileId", defaultProfile)
                    } else {
                        d.additionalQuestionAnswers = [profileId: defaultProfile]
                    }
                }
            }
        }
        return datasets
    }

    List<Dataset> handleMissingManualIds(List<Dataset> datasets) {
        datasets?.each { Dataset d ->
            if (!d.manualId) {
                d.manualId = new ObjectId().toString()
            }
        }
        return datasets
    }

    /**
     * Method to create construction mirror resource
     *
     * @param construction
     * @param resourceParameters
     * @param persistingListProperties
     * @param persistingRegularProperties
     * @param runByCron true if the method was called by cron job (no user)
     * @return
     */
    Resource createConstructionResource(Construction construction, Map<String, String> resourceParameters, List<String> persistingListProperties = null, List<String> persistingRegularProperties = null, Boolean runByCron = false) {
        if (!construction) {
            return null
        }
        Resource mirrorResource = null
        User user = runByCron ? null : userService.getCurrentUser()
        Resource resource = optimiResourceService.getResourceWithGorm(construction?.mirrorResourceId, null, Boolean.TRUE)
        boolean createNew = false

        if (!resource) {
            resource = new Resource()
            resource.resourceId = "construction" + UUID.randomUUID().toString()
            resource.defaultProfile = Boolean.TRUE
            createNew = true
        }

        resource.construction = Boolean.TRUE
        resource.isMultiPart = Boolean.TRUE
        resource.constructionId = construction.id?.toString()
        resource.nameEN = construction.nameEN
        resource.nameES = construction.nameES
        resource.nameSE = construction.nameSE
        resource.nameIT = construction.nameIT
        resource.nameNL = construction.nameNL
        resource.nameNO = construction.nameNO
        resource.nameDE = construction.nameDE
        resource.nameFI = construction.nameFI
        resource.nameFR = construction.nameFR
        resource.nameHU = construction.nameHU
        resource.nameJP = construction.nameJP
        resource.manufacturer = construction.brandName
        resource.brandImageId = construction.brandImageId
        resource.imgLink = construction.imgLink
        resource.copyrightText = construction.copyrightText
        resource.unitForData = construction.unit
        resource.productDescription = construction.productDescription
        resource.costConstruction_EUR = construction.costConstruction_EUR
        resource.importFile = construction.importFile
        if (construction.country) {
            resource.areas = [construction.country]
            String isoCountryCode = optimiResourceService.getresourceIsoCode(construction.country)

            if (isoCountryCode) {
                resource.isoCodesByAreas = [(construction.country): isoCountryCode]
            }
        }
        resource.constructionType = construction.constructionType
        resource.environmentDataSource = construction.environmentDataSource
        resource.environmentDataSourceType = construction.environmentDataSourceType
        resource.technicalSpec = construction.technicalSpec
        resource.representativeArea = construction.representativeArea
        resource.defaultThickness_mm = construction.thickness_mm
        resource.defaultThickness_in = construction.thickness_in
        resource.serviceLife = construction.serviceLife
        resource.inheritConstructionServiceLife = construction.inheritConstructionServiceLife
        resource.transportResourceId = construction.transportResourceId

        // NMD
        resource.rechtev_eenheid_dim1 = construction.rechtev_eenheid_dim1
        resource.rechtev_grootheid_dim1 = construction.rechtev_grootheid_dim1
        resource.rechtev_eenheid_dim2 = construction.rechtev_eenheid_dim2
        resource.rechtev_grootheid_dim2 = construction.rechtev_grootheid_dim2
        resource.nmdElementId = construction.nmdElementId
        resource.nmdElementnaam = construction.nmdElementnaam
        resource.nmdElementCode = construction.nmdElementCode
        resource.nmdCategoryId = construction.nmdCategoryId
        resource.nmdProductType = construction.nmdProductType
        resource.nmdProductApplication = construction.nmdProductApplication
        resource.nmdDatabaseVersion = construction.nmdDatabaseVersion
        resource.nmdProductId = construction.nmdProductId
        resource.nmdUpdateTime = construction.nmdUpdateTime
        resource.nmdKindElementIds = construction.nmdKindElementIds
        resource.mirrorConstructionCombinedUnits = construction.combinedUnits
        resource.nmdReuseAvailable= construction.nmdReuseAvailable
        resource.nmdReuseFactor= construction.nmdReuseFactor

        resource.hideConstituentImpacts = construction.hideConstituentImpacts

        if (construction.nmdUnitConversionFactor) {
            resource.nmdUnitConversionFactor = construction.nmdUnitConversionFactor
            resource.nmdAlternativeUnit = construction.nmdAlternativeUnit
        }
        // ==

        if (construction.thickness_mm || construction.thickness_in) {
            resource.impactNonLinear = Boolean.FALSE
        } else {
            resource.impactNonLinear = Boolean.TRUE
        }

        if (construction.privateConstructionAccountId) {
            resource.privateDatasetAccountId = construction.privateConstructionAccountId
            resource.privateDataset = Boolean.TRUE
            resource.environmentDataSourceType = Resource.CONSTANTS.PRIVATE.value()

            if (user) {
                resource.privateDatasetImportedByUserId = user.id.toString()
            }
        }

        resource.active = construction.locked ?: false

        if (!resource.firstUploadTime) {
            resource.firstUploadTime = new Date()
        }

        /* json defined */
        // in some places we do pass these in, some we don't
        if (!persistingListProperties || !persistingRegularProperties) {
            List<PersistentProperty> resourcePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Resource.class)
            persistingListProperties = resourcePersistentProperties?.findAll({ it.type?.equals(List) })?.collect({ it.name })
            persistingRegularProperties = resourcePersistentProperties?.findAll({ !it.type?.equals(List) })?.collect({ it.name })
        }

        if (construction.resourceSubType) {
            resource.resourceSubType = construction.resourceSubType
            resource.resourceType = null
        }

        resourceParameters?.each { String key, String value ->
            if (persistingRegularProperties?.contains(key) && value != null) {
                DomainObjectUtil.callSetterByAttributeName(key, resource, value)
            }
            if (persistingListProperties?.contains(key) && value != null) {
                DomainObjectUtil.callSetterByAttributeName(key, resource, value?.tokenize(",")?.toList())
            }
        }

        if (resource.validate()) {
            resource = optimiResourceService.saveResource(resource)
            String msg = ''

            if (createNew) {
                construction.mirrorResourceId = resource.resourceId
                construction.merge(flush: true, failOnError: true)
                msg = "User:${user?.username} created construction ${construction.nameEN} with mirroring resourceId ${resource.resourceId} and datasets ${construction.datasets} "
            } else {
                msg = "User:${user?.username}  updated construction ${construction.nameEN} resourceId ${resource.resourceId} with datasets ${construction.datasets}"
            }

            if (!runByCron) {
                log.info(msg)
                flashService.setSuccessAlert(msg, true)
            }

            mirrorResource = resource
        }
        return mirrorResource
    }

    public Map getNMD3ScalingParameters(Resource resource) {
        if (resource && resource.nmdSchalingsFormuleID) {
            Map scalingParams = [:]
            scalingParams.put("nmdSchalingsFormuleID", resource.nmdSchalingsFormuleID)

            if (resource.defaultThickness_mm != null) {
                scalingParams.put(Constants.DIMENSION1_NMD, resource.defaultThickness_mm)
            }

            if (resource.defaultThickness_mm2 != null) {
                scalingParams.put(Constants.DIMENSION2_NMD, resource.defaultThickness_mm2)
            }

            if (resource.nmdSchalingsFormuleA1 != null) {
                scalingParams.put("nmdSchalingsFormuleA1", resource.nmdSchalingsFormuleA1)
            }

            if (resource.nmdSchalingsFormuleA2 != null) {
                scalingParams.put("nmdSchalingsFormuleA2", resource.nmdSchalingsFormuleA2)
            }

            if (resource.nmdSchalingsFormuleB1 != null) {
                scalingParams.put("nmdSchalingsFormuleB1", resource.nmdSchalingsFormuleB1)
            }

            if (resource.nmdSchalingsFormuleB2 != null) {
                scalingParams.put("nmdSchalingsFormuleB2", resource.nmdSchalingsFormuleB2)
            }

            if (resource.nmdSchalingsFormuleC != null) {
                scalingParams.put("nmdSchalingsFormuleC", resource.nmdSchalingsFormuleC)
            }
            return scalingParams
        } else {
            return null
        }
    }

    public Map<String, Double> getScalingParameters(Construction construction) {
        if (construction) {
            Map<String, Double> scalingParams = [:]

            if (construction.scalingType != null) {
                scalingParams.put("scalingType", construction.scalingType.toDouble())
            }

            if (construction.thickness_mm != null) {
                scalingParams.put("thickness_mm", construction.thickness_mm)
            }

            if (construction.thickness_mm2 != null) {
                scalingParams.put("thickness_mm2", construction.thickness_mm2)
            }

            if (construction.defaultThickness_mm != null) {
                scalingParams.put("defaultThickness_mm1", construction.defaultThickness_mm)
            }

            if (construction.defaultThickness_mm2 != null) {
                scalingParams.put("defaultThickness_mm2", construction.defaultThickness_mm2)
            }

            if (construction.difference != null) {
                scalingParams.put("difference", construction.difference)
            }

            if (construction.difference2 != null) {
                scalingParams.put("difference2", construction.difference2)
            }

            if (construction.additionalQuestionAnswers?.get(Constants.DIMENSION1_NMD) != null &&
                    DomainObjectUtil.isNumericValue(construction.additionalQuestionAnswers.get(Constants.DIMENSION1_NMD).toString())) {
                scalingParams.put(Constants.DIMENSION1_NMD, DomainObjectUtil.convertStringToDouble(construction.additionalQuestionAnswers.get(Constants.DIMENSION1_NMD).toString()))
            }

            if (construction.additionalQuestionAnswers?.get(Constants.DIMENSION2_NMD) != null && DomainObjectUtil.isNumericValue(construction.additionalQuestionAnswers.get(Constants.DIMENSION2_NMD).toString())) {
                scalingParams.put(Constants.DIMENSION2_NMD, DomainObjectUtil.convertStringToDouble(construction.additionalQuestionAnswers.get(Constants.DIMENSION2_NMD).toString()))
            }

            if (construction.additionalQuestionAnswers?.get(Constants.ADDITIONAL_FACTOR_NMD30_QUESTIONID) != null &&
                    DomainObjectUtil.isNumericValue(construction.additionalQuestionAnswers.get(Constants.ADDITIONAL_FACTOR_NMD30_QUESTIONID).toString())) {
                scalingParams.put(Constants.ADDITIONAL_FACTOR_NMD30_QUESTIONID, DomainObjectUtil.convertStringToDouble(construction.additionalQuestionAnswers.get(Constants.ADDITIONAL_FACTOR_NMD30_QUESTIONID).toString()))
            }

            return scalingParams
        } else {
            return null
        }
    }

    public Map<String, Object> getScalingParametersFromDocument(Document construction) {
        if (construction) {
            Map<String, Object> scalingParams = [:]

            if (construction.scalingType != null) {
                scalingParams.put("scalingType", construction.scalingType)
            }

            if (construction.thickness_mm != null) {
                scalingParams.put("thickness_mm", construction.thickness_mm)
            }

            if (construction.thickness_mm2 != null) {
                scalingParams.put("thickness_mm2", construction.thickness_mm2)
            }

            if (construction.defaultThickness_mm != null) {
                scalingParams.put("defaultThickness_mm1", construction.defaultThickness_mm)
            }

            if (construction.defaultThickness_mm2 != null) {
                scalingParams.put("defaultThickness_mm2", construction.defaultThickness_mm2)
            }

            if (construction.difference != null) {
                scalingParams.put("difference", construction.difference)
            }

            if (construction.difference2 != null) {
                scalingParams.put("difference2", construction.difference2)
            }

            if (construction.additionalQuestionAnswers?.get(Constants.DIMENSION1_NMD) != null && DomainObjectUtil.isNumericValue(construction.additionalQuestionAnswers.get(Constants.DIMENSION1_NMD).toString())) {
                scalingParams.put(Constants.DIMENSION1_NMD, DomainObjectUtil.convertStringToDouble(construction.additionalQuestionAnswers.get(Constants.DIMENSION1_NMD).toString()))
            }

            if (construction.additionalQuestionAnswers?.get(Constants.DIMENSION2_NMD) != null && DomainObjectUtil.isNumericValue(construction.additionalQuestionAnswers.get(Constants.DIMENSION2_NMD).toString())) {
                scalingParams.put(Constants.DIMENSION2_NMD, DomainObjectUtil.convertStringToDouble(construction.additionalQuestionAnswers.get(Constants.DIMENSION2_NMD).toString()))
            }
            return scalingParams
        } else {
            return null
        }
    }

    List<Dataset> getFormattedConstructionDatasets(Construction construction, Dataset constructionDataset, Indicator indicator) {
        List<Dataset> datasets = []

        if (construction) {
            AdditionalQuestionTagLib aq = grailsApplication.mainContext.getBean('com.bionova.optimi.core.taglib.AdditionalQuestionTagLib');
            //*****************************************************************************************
            //inheritToChildren question from construction to constituents
            //*****************************************************************************************
            String constructionDatasetQueryId = constructionDataset.queryId
            String sectionId = constructionDataset.sectionId
            String questionId = constructionDataset.questionId
            Question mainQuestion = questionService.getQuestion(constructionDatasetQueryId, questionId)
            List<Question> additionalQuestions = simulationToolService.getInheritedAdditionalQuestion(constructionDatasetQueryId, sectionId, questionId, indicator)


            //*****************************************************************************************

            Map additionals = getScalingParameters(construction)

            if (additionals) {
                if (constructionDataset.additionalQuestionAnswers) {
                    constructionDataset.additionalQuestionAnswers.putAll(additionals)
                } else {
                    constructionDataset.additionalQuestionAnswers = additionals
                }
            }

            Double europeanMultiplier
            if (unitConversionUtil.isImperialUnit(constructionDataset.userGivenUnit)) {
                String targetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(constructionDataset.userGivenUnit)
                europeanMultiplier = unitConversionUtil.doConversion(constructionDataset.quantity, datasetService.getThickness(constructionDataset.additionalQuestionAnswers), constructionDataset.userGivenUnit, null, null, null, null, null, datasetService.getResource(constructionDataset)?.density, targetUnit)
            }

            List<Dataset> datasetsConstruction = construction.datasets?.findAll({ it.defaultConstituent || it.defaultConstituent == null })
            if (datasetsConstruction) {
                ResourceCache resourceCache = ResourceCache.init(datasetsConstruction)
                Resource resourceConstructionDataset = datasetService.getResource(constructionDataset)
                for (Dataset constructionSubSet: datasetsConstruction)
                {
                    Dataset dataset = new Dataset()
                    dataset.manualId = new ObjectId().toString()
                    dataset.resourceId = constructionSubSet.resourceId

                    if (unitConversionUtil.isImperialUnit(constructionDataset.userGivenUnit))  {
                        String constructionUnit = unitConversionUtil.europeanUnitToImperialUnit(constructionSubSet.userGivenUnit)
                        dataset.userGivenUnit = constructionUnit
                        Double convertedQuantity = unitConversionUtil.doConversion(constructionSubSet.quantity, datasetService.getThickness(constructionSubSet.additionalQuestionAnswers), constructionUnit, resourceCache.getResource(constructionSubSet), null, null, null, Boolean.TRUE, null, constructionSubSet.userGivenUnit)
                        dataset.constructionValue = convertedQuantity?.toString()
                        dataset.quantity = convertedQuantity && europeanMultiplier ? convertedQuantity * europeanMultiplier : 0
                        dataset.answerIds = [dataset.quantity.toString() ?: "0"]
                    } else {
                        dataset.constructionValue = constructionSubSet.quantity.toString()
                        dataset.userGivenUnit = constructionSubSet.userGivenUnit
                        dataset.quantity = constructionSubSet.quantity && constructionDataset.quantity ? constructionSubSet.quantity * constructionDataset.quantity : 0
                        dataset.answerIds = [dataset.quantity.toString() ?: "0"]
                    }

                    dataset.profileId = constructionSubSet.profileId
                    dataset.additionalQuestionAnswers = [datasetIdFromConstruction: constructionSubSet.manualId]

                    constructionSubSet.additionalQuestionAnswers?.each { String key, def value ->
                        dataset.additionalQuestionAnswers.put(key, value)
                    }

                    //*****************************************************************************************
                    //Inherited Additional Question
                    //*****************************************************************************************
                    if (additionalQuestions) {
                        additionalQuestions.each { Question additionalQuestion ->
                            if (additionalQuestion.inheritToChildren) {
                                def additionalFromContructionDataset = constructionDataset?.additionalQuestionAnswers?.get(additionalQuestion.questionId)
                                if (additionalFromContructionDataset) {
                                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, additionalFromContructionDataset)
                                }
                            } else if (!dataset.additionalQuestionAnswers?.get(additionalQuestion.questionId)) {
                                // If it doesnt already have an answer from construction sub set dataset.
                                def answer

                                if (additionalQuestion.questionId == "serviceLife") {
                                    if (questionService.isWithPermanentServiceLife(mainQuestion, indicator)) {
                                        answer = "permanent"
                                    }
                                } else {
                                    answer = "${aq.additionalQuestionAnswer(additionalQuestion: additionalQuestion, resource: datasetService.getResource(dataset))}"
                                }

                                if (answer != null) {
                                    dataset.additionalQuestionAnswers.put(additionalQuestion.questionId, answer)
                                }
                            }
                        }
                    }
                    //*****************************************************************************************

                    /* parentConstructionValues copied */
                    dataset.uniqueConstructionIdentifier = constructionDataset.uniqueConstructionIdentifier
                    dataset.seqNr = constructionDataset.seqNr
                    dataset.parentConstructionId = resourceConstructionDataset?.constructionId
                    dataset.questionId = constructionDataset.questionId
                    dataset.sectionId = constructionDataset.sectionId
                    dataset.queryId = constructionDataset.queryId
                    datasets.add(dataset)
                }
            }
        }
        return datasets
    }

    public void setBooleanProperties(Construction construction, params) {
        if (params) {
            if (params.lockandpublish) {
                construction.locked = true
            } else if (params.unlockandunpublish) {
                construction.locked = false
            }

            if (params.unbundable) {
                construction.unbundable = true
            } else {
                construction.unbundable = false
            }

            if (params.preventExpand) {
                construction.preventExpand = true
            } else {
                construction.preventExpand = false
            }

            if (params.inheritConstructionServiceLife) {
                construction.inheritConstructionServiceLife = true
            } else {
                construction.inheritConstructionServiceLife = false
            }
        }
    }

    List<String> getFlexibleResourceList(datasetMap){

        List<String> flexibleResourceIdList = []

        (0..14).each {
            String resId = datasetMap.get("flexibleResourceId" + it)
            if(resId)flexibleResourceIdList.add(resId)
        }

        return flexibleResourceIdList
    }

    List<Dataset> createFlexibleResourceList(Dataset flexibleResource){

        if(!flexibleResource) return []

        List<String> idsList = flexibleResource.flexibleResourceIdList
        List<Dataset> resourceList = []

        idsList?.forEach {
            Dataset d = datasetService.createCopy(flexibleResource)
            d.defaultConstituent = false
            d.resourceId = it
            if (d) resourceList.add(d)
        }

        return resourceList
    }

    /**
     * Deactivate all constructions with same constructionId.
     * @param construction
     * @param group - use this to add to group if construction doesn't belong to any group (fix for existing constructions)
     * @return true if deactivation ran successfully for all of them
     */
    boolean deactivateConstructionsById(String constructionId, ConstructionGroup group = null) {
        if (!constructionId) {
            return false
        }

        List<Construction> constructions = getConstructionsByConstructionId(constructionId)
        if (!constructions) {
            return false
        }

        int failedCount = 0
        for (Construction construction in constructions) {
            addConstructionGroupToConstruction(construction, group)
            boolean success = deactivateConstruction(construction)
            if (!success) {
                failedCount++
            }
        }
        return failedCount == 0
    }

    /**
     *
     * @param construction
     * @return true if deactivation ran successfully
     */
    boolean deactivateConstruction(Construction construction) {
        if (construction) {
            construction.locked = Boolean.FALSE
            Resource resource = optimiResourceService.getResourceWithGorm(construction.mirrorResourceId, null, Boolean.TRUE)

            if (resource) {
                optimiResourceService.deactivateResource(resource)
            }
            try {
                construction.merge(flush: true, failOnError: true)
            } catch (e) {
                loggerUtil.error(log, "ERROR while deactivating construction ${construction?.id?.toString()}", e)
                flashService.setErrorAlert("ERROR while deactivating construction ${construction?.id?.toString()}: $e.message", true)
                return false
            }
            return true
        }
        return false
    }

    Double getOriginalConstituentQuantityForManualId(String manualId) {
        Double quantity = 0

        if (manualId) {
            List datasets = Construction.collection.findOne(["datasets.manualId": manualId], [datasets: 1])?.get("datasets")

            if (datasets) {
                quantity = datasets.find({manualId.equals(manualId)})?.quantity
            }
        }
        return quantity
    }

    String renderConstructionTypeImage(Resource resource) {
        String constructionTypeImage = ''
        if (resource?.construction) {
            String constructionType = resource?.constructionType
            def asset = grailsApplication.mainContext.getBean('asset.pipeline.grails.AssetsTagLib')
            if (constructionType) {
                if (System.getProperty("islocalhost")) {
                    constructionTypeImage = "<img src='/app/assets/constructionTypes/${constructionType}.png' class='constructionType'></img>"
                    if (!asset.assetPathExists(src: "/constructionTypes/${constructionType}.png")) {
                        loggerUtil.error(log, "missing image for construction type ${constructionType}")
                        constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
                    }
                } else {
                    String filePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "constructionTypesPath")
                    def testPath = new File("/var/www/${filePath}${constructionType}.png")
                    if (testPath.exists()) {
                        constructionTypeImage = "<img src='${filePath}${constructionType}.png' class='constructionType'></img>"

                    } else {
                        loggerUtil.error(log,"missing image for construction type ${constructionType} with filepath ${testPath?.absolutePath} from server")
                        constructionTypeImage = "<img src='/app/assets/constructionTypes/${constructionType}.png' class='constructionType'></img>"
                        if (!asset.assetPathExists(src: "/constructionTypes/${constructionType}.png")) {
                            loggerUtil.error(log,"missing image for construction type ${constructionType} from assets")
                            constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
                        }
                    }
                }
            } else if (resource?.isDummy) {
                constructionTypeImage = "<img src='/app/assets/constructionTypes/structure.png' class='constructionType'/>"
            } else {
                constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
            }
        }

        return constructionTypeImage

    }

    /**
     * Add constructionGroup to construction if it hasn't got any
     * @param construction
     * @param group
     */
    void addConstructionGroupToConstruction(Construction construction, ConstructionGroup group) {
        if (construction && !construction.constructionGroup && group) {
            construction.constructionGroup = group.groupId
        }
    }

    /**
     * Link the dataset of a constituent back to its parent construction.
     * This is a flag that if it's true, currently the quantity of constituent will not change when construction quantity changes
     * @param d
     */
    void relinkConstituentDatasetToConstruction(Dataset d) {
        if (d.unlinkedFromParentConstruction) {
            d.unlinkedFromParentConstruction = false
        }
    }

    String getLocalizedName(Construction construction) {
        if(!construction) {
            return "Localized name missing for construction"
        }
        String fallback = "Localized name missing for construction ${construction?.constructionId}"
        return localizationService.getLocalizedProperty(construction, "name", "name", fallback)
    }

    String getLocalizedLabel(Construction construction) {
        Resource r = getMirrorResourceObject(construction)
        String label
        String resourceLocalizedName = optimiResourceService.getLocalizedName(r)
        String technicalSpec = r?.technicalSpec
        String commercialName = r?.commercialName
        String manufacturer = r?.manufacturer

        if (resourceLocalizedName && (technicalSpec || commercialName || manufacturer)) {
            label = "${resourceLocalizedName}${technicalSpec ? ", " + technicalSpec : ""}" +
                    "${commercialName ? ", " + commercialName : ""}${manufacturer ? " (" + manufacturer + ")" : ""}"
        } else {
            label = "${resourceLocalizedName ?: r?.nameEN ?: construction.nameEN ?: ""}"
        }
        // Remove line breaks to prevent data teams errors smh
        label = label.replace("\r\n", "").replace("\n", "")
        return label
    }

    Resource getMirrorResourceObject(Construction construction) {
        if(!construction) {
            return null
        }
        return optimiResourceService.getResourceWithParams(construction.mirrorResourceId, null,
                Boolean.TRUE, Boolean.TRUE)
    }

    String getLastEditorUsername(String lastEditor) {
        String username = ""
        if (lastEditor) {
            username = User.get(lastEditor)?.username
        }
        return username
    }

    ConstructionGroup getConstructionGroupAsObject(String constructionGroup) {
        return getConstructionGroupByGroupId(constructionGroup)
    }

    Boolean getMissMatchedData(List<Dataset> datasets) {
        Boolean missMatch = Boolean.FALSE
        List<Dataset> incompatibleUnitDatasets = []

        if (datasets) {
            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset d: datasets) {
                Resource resource = resourceCache.getResource(d)

                if (resource) {
                    List<String> compatibleUnits = unitConversionUtil.getCompatibleUnitsForResource(resource)

                    if (d.userGivenUnit && compatibleUnits && !compatibleUnits*.toLowerCase()?.contains(d.userGivenUnit.toLowerCase()) && !d.uniqueConstructionIdentifier) {
                        incompatibleUnitDatasets.add(d)
                    }
                }
            }
        }

        if (incompatibleUnitDatasets && !incompatibleUnitDatasets.isEmpty()) {
            missMatch = Boolean.TRUE
        }

        return missMatch
    }

    List <Dataset> getInactiveData(List<Dataset> datasets) {
        List <Dataset> inactiveDatasets = []
        if (datasets) {
            inactiveDatasets = datasets.findAll({!datasetService.getDatasetResourceActive(it) && it.resourceId})
        }
        return inactiveDatasets
    }

    List <Dataset> getActiveData(List<Dataset> datasets) {
        List <Dataset> activeDatasets = []
        if (datasets) {
            activeDatasets = datasets.findAll({datasetService.getDatasetResourceActive(it)})
        }
        return activeDatasets
    }

    List<Dataset> getConstructionComponentsToQuery(List<Dataset> datasets) {
        List<Dataset> dsets = []
        if (datasets) {

            if (datasets.find({it.defaultConstituent})) {
                dsets = datasets.findAll({it.defaultConstituent})
            } else {
                dsets = datasets
            }
        }
        return dsets
    }

    String getComment(Map <String, Object> additionalQuestionAnswers) {
        return additionalQuestionAnswers?.get("comment") ?: ""
    }
}
