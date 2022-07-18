package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as Const
import com.bionova.optimi.core.Constants.RulesetType
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.BimModelWarning
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.ClassificationQuestionTranslation
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.ImportMapperCollapseGrouper
import com.bionova.optimi.core.domain.mongo.ImportMapperDataRule
import com.bionova.optimi.core.domain.mongo.ImportMapperDefaultRule
import com.bionova.optimi.core.domain.mongo.ImportMapperDescriptiveDataRule
import com.bionova.optimi.core.domain.mongo.ImportMapperDescriptiveQuestionRule
import com.bionova.optimi.core.domain.mongo.ImportMapperPresetFilter
import com.bionova.optimi.core.domain.mongo.ImportMapperQuestionRule
import com.bionova.optimi.core.domain.mongo.ImportMapperTranslation
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReport
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.RecognitionRuleset
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.SystemMatch
import com.bionova.optimi.core.domain.mongo.SystemTrainingDataSet
import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.domain.mongo.TextMatch
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.FormatterUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.excelimport.SheetHandler
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.importMapper.ImportMapperIterationStatistic
import com.bionova.optimi.importMapper.ImportedByImportMapperReturn
import com.bionova.optimi.ui.rset.RSEnvDatasetDto
import com.bionova.optimi.util.UnitConversionUtil
import com.mongodb.BasicDBObject
import grails.util.GrailsClassUtils
import grails.web.servlet.mvc.GrailsParameterMap
import groovy.transform.CompileStatic
import org.apache.commons.collections.CollectionUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.ss.formula.FormulaParseException
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.util.XMLHelper
import org.apache.poi.xssf.eventusermodel.XSSFReader
import org.apache.poi.xssf.model.SharedStringsTable
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.HtmlUtils
import org.xml.sax.InputSource
import org.xml.sax.XMLReader

import javax.servlet.http.HttpSession
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Pasi-Markus Mäkelä
 */
class ImportMapperService {

    static transactional = "mongo"

    def importMapperTrainingDataService
    def resourceFilterCriteriaUtil
    def userService
    def optimiResourceService
    def configurationService
    def queryService
    def unitConversionUtil
    def datasetService
    def resultFormattingResolver
    def recognitionRulesetService
    def featureService
    def domainClassService
    def questionService
    def indicatorService
    def privateClassificationListService
    def calculationRuleService
    def flashService
    def entityService
    def licenseService

    LoggerUtil loggerUtil
    IndicatorReportService indicatorReportService
    ResourceService resourceService
    XmlService xmlService

    private final static String OCLID = "OCLID"
    private final static String MATERIAL = "MATERIAL"

    void deleteTemporaryImportData(String id) {
        if (id) {
            TemporaryImportData.collection.remove([_id: DomainObjectUtil.stringToObjectId(id)])
        }
    }

    def oldImportDatasetsByImportMapper(ImportMapper importMapper, String parentEntityId,
                                        Workbook workbook, Boolean useAdaptiveRecognition = null,
                                        Boolean trainAdaptiveRecognition = null, ImportMapperPresetFilter presetFilter = null,
                                        Boolean removeEmptyData = Boolean.TRUE, Boolean temporaryCalculation = Boolean.FALSE,
                                        Boolean useOriginalClass = Boolean.FALSE, Boolean api = Boolean.FALSE,
                                        String indicatorId, String childEntityId) {
        def returnable = [:]
        DataFormatter formatter = new DataFormatter()

        if (importMapper && (parentEntityId || temporaryCalculation) && workbook) {

            Entity parentEntity = Entity.read(parentEntityId)
            Map<String, String> targetQuestionRule = importMapper.questionMappingRules.find({
                it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass ?: "building")
            })?.target
            Question question = null
            if (targetQuestionRule) {
                question = ((QuestionService) questionService)
                        .getQuestion(targetQuestionRule.get("queryId"), targetQuestionRule.get("questionId"))
            }

            List<ImportMapperDataRule> dataRules = importMapper.dataMappingRules?.
                    findAll({ !"resourceId".equalsIgnoreCase(it.target) })

            Entity child = Entity.read(childEntityId)
            Indicator indicator = ((IndicatorService) indicatorService).getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
            List<License> validLicenses = licenseService.getValidLicensesForEntity(child)
            List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
            List<String> queryIdsForIndicator = queryService.getQueryIdsByIndicatorAndEntity(indicator, child, featuresAvailableForEntity)
            Map<String, String> dataMapping = [:]
            List<String> trainingMatch = []
            List<String> noDisplayFields = importMapper.noDisplayFields
            String materialHeading = null
            String thicknessHeading = null

            List<String> classIdentifyingFields = importMapper.classIdentifyingFields ?: ["CLASS"]

            Set<String> acceptedClasses = []
            importMapper.questionMappingRules?.each {
                if (queryIdsForIndicator && queryIdsForIndicator.contains(it.target?.queryId)) {
                    it.matchingValues?.each {
                        if (!acceptedClasses.contains(it.toLowerCase())) {
                            acceptedClasses.add(it.toLowerCase())
                        }
                    }
                }

            }
            List<String> allAllowedUnits = importMapper.allowedUnits ?
                    importMapper.allowedUnits : com.bionova.optimi.core.Constants.ALL_UNITS

            importMapper.dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->
                if (importMapper.trainingMatch && !importMapper.trainingMatch.isEmpty() && importMapper.trainingMatch*.toUpperCase().contains(importMapperDataRule.target?.toUpperCase())) {
                    if (importMapperDataRule.mappedHeadings && !importMapperDataRule.mappedHeadings.isEmpty()) {
                        trainingMatch.addAll(importMapperDataRule.mappedHeadings*.toUpperCase())
                    }
                }
                importMapperDataRule.mappedHeadings?.each { String heading ->
                    dataMapping.put(heading.toUpperCase(), importMapperDataRule.target?.toUpperCase())
                }
            }

            if (importMapper.trainingMatch && importMapper.trainingMatch*.toUpperCase().contains("CLASS")) {
                classIdentifyingFields.each { String classHeading ->
                    if (!trainingMatch.contains(classHeading)) {
                        trainingMatch.add(classHeading)
                    }
                }
            }

            List<String> dataMappingIndexes = dataMapping.keySet()?.toList()
            List<String> excludeColumnsFromImport = importMapper.excludeFromImport

            if (parentEntity || temporaryCalculation) {
                if (!importMapper.compatibleEntityClasses ||
                        importMapper.compatibleEntityClasses?.contains(parentEntity?.entityClass ?: "building") || temporaryCalculation) {

                    List<String> discardedValues = []
                    List<String> warningValues = []
                    //new recognition rulesets
                    List<RecognitionRuleset> warnRulesets = ((RecognitionRulesetService) recognitionRulesetService)
                            .getAllRecognitionRulesets(com.bionova.optimi.core.Constants.RulesetType.WARN.toString())
                    List<RecognitionRuleset> discardRulesets = ((RecognitionRulesetService) recognitionRulesetService)
                            .getAllRecognitionRulesets(com.bionova.optimi.core.Constants.RulesetType.DISCARD.toString())

                    if (warnRulesets) {
                        warnRulesets.each { RecognitionRuleset recognitionRuleset ->
                            recognitionRuleset.textMatches?.each { TextMatch textMatch ->
                                String txtMatch = textMatch.textMatch
                                if (textMatch.matchAllVariants) {
                                    warningValues.add(txtMatch + '*')
                                } else {
                                    warningValues.add(txtMatch)
                                }
                            }
                        }
                    }

                    if (discardRulesets) {
                        discardRulesets.each { RecognitionRuleset recognitionRuleset ->
                            recognitionRuleset.textMatches?.each { TextMatch textMatch ->
                                String txtMatch = textMatch.textMatch
                                if (textMatch.matchAllVariants) {
                                    discardedValues.add(txtMatch + '*')
                                } else {
                                    discardedValues.add(txtMatch)
                                }
                            }
                        }
                    }

                    try {
                        Integer numberOfSheets = workbook?.getNumberOfSheets()
                        ImportMapperDataRule resourceMappingRule = importMapper.dataMappingRules?.find({
                            "resourceId".equals(it.target)
                        })

                        resourceMappingRule?.discardRowsWithValues?.each { String discardedValue ->
                            discardedValues.add(discardedValue.toUpperCase())
                        }

                        User user = ((UserService) userService).getCurrentUser()
                        Map<String, Integer> discardedValuesWithAmount = [:]
                        Map<String, Integer> emptyRows = [:]
                        Map<String, Integer> removedByPresetFilter = [:]
                        List<String> headers = []
                        def sheets = []
                        List<Dataset> okDatasets = []
                        List<Dataset> discardedDatasets = []
                        def rejectedDatasets = []
                        int skippedRows = 0
                        String warningMessage = ""
                        String missingMandatoryDataError = null
                        Map<String, Boolean> additionalQuestionIdIsNumeric = [:]
                        Set<String> additionalQuestionIds = []
                        def rowsRolled = 0
                        def columnsRolled = 0
                        def totalIterated = 0

                        if (numberOfSheets) {
                            Configuration additionalQuestionQueryConfig = ((ConfigurationService) configurationService).getByConfigurationName(
                                    Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString()
                            )
                            def additionalQuestionQueryId = additionalQuestionQueryConfig?.value

                            if (additionalQuestionQueryId) {
                                Query additionalQuestionsQuery = ((QueryService) queryService).getQueryByQueryId(additionalQuestionQueryId, true)
                                additionalQuestionsQuery?.allQuestions?.each({ Question q ->
                                    additionalQuestionIds.add(q.questionId)
                                    additionalQuestionIdIsNumeric.put((q.questionId), "numeric".equals(q.valueConstraints?.get("type")))
                                })
                            } else {
                                loggerUtil.error(log, "Cannot get additionalQuestionQuery. Probably missing configuration for additionalQuestionQueryId or query not imported at all.")
                                flashService.setWarningAlert("Cannot get additionalQuestionQuery. Probably missing configuration for additionalQuestionQueryId or query not imported at all.", true)
                            }
                            additionalQuestionIds.removeAll(((DomainClassService) domainClassService).getPersistentPropertyNamesForDomainClass(Dataset.class))

                            Question privateClassificationQuestion
                            String organizationClassificationId = com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID
                            if (indicator?.classificationsMap?.contains(organizationClassificationId)) {
                                privateClassificationQuestion = ((PrivateClassificationListService) privateClassificationListService)
                                        .getPrivateClassificationQuestion()
                                additionalQuestionIds.add(organizationClassificationId)
                            }
                            List<String> questionMappingHeadings = []
                            List<String> skipQuestionMappingValues = []
                            List<String> displayFields = []
                            List<String> resourceMappingHeadings = new ArrayList<String>(resourceMappingRule.mappedHeadings)

                            importMapper.questionMappingHeadings?.each { String heading ->
                                questionMappingHeadings.add(heading.toUpperCase())
                            }
                            Map<String, Boolean> compositeMaterialsRule = importMapper.compositeMaterialsRule
                            Map<String, List<String>> partialQuantificationRule = importMapper.partialQuantificationRule

                            List<String> unitIdentifyingField = importMapper.unitIdentifyingFields ?: importMapper.unitIdentifyingField ? [importMapper.unitIdentifyingField] : null

                            importMapper.skipQuestionMappingValues?.each { String skipValue ->
                                skipQuestionMappingValues.add(skipValue.toUpperCase())
                            }

                            importMapper.displayFields?.each { String displayField ->
                                displayFields.add(displayField.toUpperCase())
                            }

                            for (int i = 0; i < numberOfSheets; i++) {
                                Sheet sheet = workbook.getSheetAt(i)

                                if (!sheet.getSheetName()?.startsWith("@")) {
                                    sheets.add(sheet)
                                    int headerRowNo = sheet.getFirstRowNum()
                                    Row headerRow = sheet.getRow(headerRowNo)
                                    Iterator<Cell> headerCellIterator = headerRow.iterator()
                                    List<Integer> questionMappingCellIndices = []
                                    Integer classCellIndex
                                    Integer quantityCellIndex
                                    Integer quantityTypeCellIndex
                                    Integer categoryCellIndex = null
                                    Integer ignoreCellIndex
                                    Integer area_m2CellIndex
                                    Integer volume_m3CellIndex
                                    Integer mass_kgCellIndex
                                    Integer ifcMaterialCellIndex
                                    Integer warningCellIndex
                                    Integer thickness_mCellIndex
                                    Integer volumeFractionCellIndex
                                    Integer volumeFractionMethodCellIndex
                                    Integer oclIdCellIndex
                                    List<Integer> resourceMappingCellIndices = []
                                    Map<Integer, String> headerCellValues = [:]
                                    Map<Integer, String> displayFieldIndices = [:]
                                    Map<Integer, String> trainingMatchIndices = [:]
                                    List<Integer> questionMappingSpecialCaseIndices = []
                                    List<String> questionMappingSpecialCaseLookupHeadings = importMapper.questionMappingSpecialCaseLookupHeading?.collect({ it.toUpperCase() })

                                    while (headerCellIterator.hasNext()) {
                                        Cell headerCell = headerCellIterator.next()
                                        String headerCellValue = null
                                        if (headerCell) {
                                            headerCellValue = formatter.formatCellValue(headerCell)
                                        }

                                        if (!headerCellValue?.startsWith("@") && (!excludeColumnsFromImport || !excludeColumnsFromImport*.toUpperCase().contains(headerCellValue?.toUpperCase()))) {
                                            headerCellValues.put(new Integer(headerCell.getColumnIndex()), headerCellValue)
                                            headers.add(headerCellValue?.trim())

                                            if (questionMappingSpecialCaseLookupHeadings && questionMappingSpecialCaseLookupHeadings.contains(headerCellValue.toUpperCase())) {
                                                questionMappingSpecialCaseIndices.add(new Integer(headerCell.getColumnIndex()))
                                            }

                                            if (displayFields?.contains(headerCellValue?.toUpperCase())) {
                                                displayFieldIndices.put(new Integer(headerCell.getColumnIndex()), headerCellValue)
                                            }

                                            if (questionMappingHeadings?.contains(headerCellValue?.toUpperCase())) {
                                                questionMappingCellIndices.add(new Integer(headerCell.getColumnIndex()))
                                            } else if (resourceMappingHeadings?.collect({
                                                it.toUpperCase()
                                            })?.contains(headerCellValue.toUpperCase())) {
                                                resourceMappingCellIndices.add(new Integer(headerCell.getColumnIndex()))
                                            }

                                            if ((useAdaptiveRecognition || trainAdaptiveRecognition) && ((trainingMatch.contains(headerCellValue?.toUpperCase()) && dataMapping.get(headerCellValue?.toUpperCase())) ||
                                                    (trainingMatch.contains(headerCellValue?.toUpperCase()) && classIdentifyingFields*.toUpperCase().contains(headerCellValue?.toUpperCase())))) {
                                                if (classIdentifyingFields*.toUpperCase().contains(headerCellValue?.toUpperCase())) {
                                                    trainingMatchIndices.put(new Integer(headerCell.getColumnIndex()), "CLASS")
                                                } else {
                                                    trainingMatchIndices.put(new Integer(headerCell.getColumnIndex()), dataMapping.get(headerCellValue?.toUpperCase()))
                                                }
                                            }

                                            if (classIdentifyingFields.any { headerCellValue.trim()?.equalsIgnoreCase(it) }) {
                                                classCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("quantity".equalsIgnoreCase(dataMapping?.get(headerCellValue.toUpperCase().trim()))) {
                                                quantityCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("area_m2".equalsIgnoreCase(headerCellValue.trim())) {
                                                area_m2CellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("volume_m3".equalsIgnoreCase(headerCellValue.trim())) {
                                                volume_m3CellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("mass_kg".equalsIgnoreCase(headerCellValue.trim())) {
                                                mass_kgCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if (unitIdentifyingField.any { headerCellValue.trim().equalsIgnoreCase(it) }) {
                                                quantityTypeCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("warnings".equalsIgnoreCase(headerCellValue.trim())) {
                                                warningCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("thickness_m".equalsIgnoreCase(headerCellValue.trim())) {
                                                thickness_mCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("specialRuleShare".equalsIgnoreCase(headerCellValue.trim())) {
                                                volumeFractionCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("specialRuleId".equalsIgnoreCase(headerCellValue.trim())) {
                                                volumeFractionMethodCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("category".equalsIgnoreCase(headerCellValue.trim())) {
                                                categoryCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if ("ignore".equalsIgnoreCase(headerCellValue.trim())) {
                                                ignoreCellIndex = new Integer(headerCell.getColumnIndex())
                                            } else if (OCLID.equalsIgnoreCase(headerCellValue.trim())) {
                                                oclIdCellIndex = new Integer(headerCell.getColumnIndex())
                                            }
                                        }
                                    }

                                    if (resourceMappingCellIndices && !resourceMappingCellIndices.isEmpty()) {
                                        Integer prioHeading
                                        Integer priority
                                        resourceMappingCellIndices.each { Integer index ->
                                            if (!prioHeading) {
                                                priority = dataMappingIndexes?.indexOf(headerCellValues.get(index)?.toUpperCase())
                                                prioHeading = index
                                            } else if (dataMappingIndexes?.indexOf(headerCellValues.get(index)?.toUpperCase()) < priority) {
                                                priority = dataMappingIndexes?.indexOf(headerCellValues.get(index)?.toUpperCase())
                                                prioHeading = index
                                            }
                                        }

                                        if (prioHeading) {
                                            ifcMaterialCellIndex = prioHeading
                                        } else {
                                            ifcMaterialCellIndex = resourceMappingCellIndices.get(0)
                                        }
                                    }
                                    Iterator<Row> sheetRowIterator = sheet.iterator()
                                    List<ImportMapperTranslation> questionMappingClassTranslations = importMapper.questionMappingClassTranslations
                                    List<ClassificationQuestionTranslation> classificationQuestionTranslations = importMapper.classificationQuestionTranslations

                                    if (ifcMaterialCellIndex != null && quantityCellIndex != null && quantityTypeCellIndex != null) {
                                        materialHeading = headerCellValues.get(ifcMaterialCellIndex)
                                        long now = System.currentTimeMillis()
                                        int resourceCompatible = 0

                                        if (question) {
                                            Map<String, List<Object>> resourceFilterCriteria = question.getResourceFilterCriteria(indicator, child)

                                            BasicDBObject filter = new BasicDBObject()
                                            filter.put("active", true)

                                            if (question.skipResourceTypes) {
                                                filter.put("resourceType", [$nin: question.skipResourceTypes])
                                                filter.put("resourceSubType", [$nin: question.skipResourceTypes])
                                            } else if (question.allowResourceTypes) {
                                                filter.put("\$or", [[resourceType: [$in: question.allowResourceTypes]], [resourceSubType: [$in: question.allowResourceTypes]]])
                                            }

                                            if (question.resourceGroups) {
                                                filter.put("resourceGroup", [$in: question.resourceGroups])
                                            }
                                            if (question.requiredEnvironmentDataSourceStandards && !question.requiredEnvironmentDataSourceStandards.isEmpty()) {
                                                filter.put("environmentDataSourceStandard", [$in: question.requiredEnvironmentDataSourceStandards])
                                            }
                                            if (question.filterResources && !question.filterResources.isEmpty()) {
                                                question.filterResources.each { String key, String value ->
                                                    filter[key] = value
                                                }
                                            }
                                            if (resourceFilterCriteria && !resourceFilterCriteria.isEmpty()) {
                                                resourceFilterCriteria.each { String resourceAttribute, List<Object> requiredValues ->
                                                    Map<String, List<Object>> inClause = [:]
                                                    inClause.put('$in', requiredValues)
                                                    filter[resourceAttribute] = inClause
                                                }
                                            }
                                            resourceCompatible = Resource.collection.count(filter) as int
                                        }

                                        while (sheetRowIterator.hasNext()) {
                                            Row row = sheetRowIterator.next()
                                            rowsRolled++

                                            if (row.getRowNum() > headerRowNo) {
                                                String ifcMaterialValue = getCellValue(row.getCell(ifcMaterialCellIndex))
                                                String oclIdValue
                                                //REL-381
                                                if (oclIdCellIndex) {
                                                    oclIdValue = getCellValue(row.getCell(oclIdCellIndex))
                                                }
                                                String ifcMaterialValueOrg = ifcMaterialValue
                                                if (ifcMaterialValue) {
                                                    ifcMaterialValue = ifcMaterialValue.trim()?.replaceAll("\\s{2,}|_", " ")
                                                    // replace extra spaces and underscore with space
                                                }

                                                String quantityValue = getCellValue(row.getCell(quantityCellIndex))
                                                if (quantityValue && ("0".equals(quantityValue) || "0.0".equals(quantityValue))) {
                                                    quantityValue = null
                                                }
                                                String quantityTypeValue = getCellValue(row.getCell(quantityTypeCellIndex))
                                                boolean skipRow = false

                                                quantityTypeValue = remapUnitQuantityType(importMapper, quantityTypeValue)

                                                if ((removeEmptyData && (!ifcMaterialValue || !quantityValue || !quantityTypeValue)) ||
                                                        (!ifcMaterialValue && !quantityValue && !quantityTypeValue)) {
                                                    Integer amount = emptyRows.get("emptyRows")

                                                    if (amount) {
                                                        amount++
                                                    } else {
                                                        amount = 1
                                                    }
                                                    emptyRows.put("emptyRows", amount)
                                                } else {
                                                    Map<String, String> dataMappingPriorities = [:]
                                                    Map<String, String> matchingValues = [:]
                                                    Dataset dataset = new Dataset()
                                                    dataset.ifcMaterialValueOrg = ifcMaterialValueOrg
                                                    dataset.usedUnitSystem = user?.unitSystem ? user.unitSystem :
                                                            UnitConversionUtil.UnitSystem.METRIC.value
                                                    dataset.manualId = new ObjectId().toString()
                                                    Map<String, String> trainingData = [:]
                                                    Map<String, String> allDataFieldsFromImport = [:]
                                                    dataset.allowMapping = Boolean.TRUE
                                                    boolean questionMappingFound = false
                                                    Iterator<Cell> cellIterator = row.iterator()
                                                    int foo = 0
                                                    boolean oneCellValueFound = false

                                                    while (cellIterator.hasNext() && !skipRow) {
                                                        totalIterated++
                                                        foo++
                                                        Cell cell = cellIterator.next()
                                                        Integer columnIndex = new Integer(cell.getColumnIndex())
                                                        String headerCellValue = headerCellValues.get(columnIndex)?.trim()

                                                        if (columnIndex == classCellIndex) {
                                                            String classValue = getCellValue(cell)
                                                            if (useOriginalClass) {
                                                                dataset.originalClass = classValue
                                                            } else {
                                                                dataset.persistedOriginalClass = classValue
                                                            }
                                                        }
                                                        handleImportMapperQuestionMappingSpecialCases(importMapper, questionMappingCellIndices as Set, questionMappingSpecialCaseIndices, row, cell)
                                                        handleImportMapperQuestionMappingClassTranslations(questionMappingClassTranslations, classCellIndex, cell)


                                                        String value = remapUnitQuantityType(importMapper, getCellValue(cell))

                                                        if (columnIndex == categoryCellIndex) {
                                                            String categoryValue = value
                                                            dataset.categoryMaterialImport = categoryValue
                                                        }
                                                        if (columnIndex == classCellIndex) {
                                                            if (!acceptedClasses.contains(value?.toLowerCase())) {
                                                                dataset.importError = dataset.importError ?
                                                                        dataset.importError + " and Data with unconventional classification: ${value}" :
                                                                        "Data with unconventional classification: ${value}"
                                                                dataset.unconventionalClassification = Boolean.TRUE
                                                            }
                                                        }
                                                        String copyValue = value ? HtmlUtils.htmlEscape(value.trim()) : ""
                                                        if (value) {
                                                            if (columnIndex == ifcMaterialCellIndex) {
                                                                value = HtmlUtils.htmlEscape(value.trim()?.replaceAll("\\s{2,}|_", " "))
                                                            } else {
                                                                value = HtmlUtils.htmlEscape(value.trim()?.replaceAll("\\s{2,}", " "))
                                                            }
                                                        } else {
                                                            value = ""
                                                        }

                                                        if (compositeMaterialsRule && headerCellValue) {
                                                            def valueForCompositeMaterialRule = compositeMaterialsRule.get(headerCellValue.toUpperCase())

                                                            if (valueForCompositeMaterialRule && valueForCompositeMaterialRule.toString().toUpperCase().equals(value?.toUpperCase())) {
                                                                dataset.importMapperCompositeMaterial = Boolean.TRUE
                                                            }
                                                        }

                                                        if (partialQuantificationRule && headerCellValue) {
                                                            List<String> partialQuantificationValues = partialQuantificationRule.get(headerCellValue.toUpperCase())

                                                            if (partialQuantificationValues && partialQuantificationValues.collect({
                                                                it.toUpperCase()
                                                            }).contains(value.toUpperCase())) {
                                                                dataset.importMapperPartialQuantification = Boolean.TRUE
                                                            }
                                                        }

                                                        if (trainingMatchIndices?.keySet()?.contains(columnIndex)) {
                                                            if (!matchingValues.get(trainingMatchIndices.get(columnIndex))) {
                                                                matchingValues.put(trainingMatchIndices.get(columnIndex), HtmlUtils.htmlUnescape(value ?: ""))
                                                                dataMappingPriorities.put(trainingMatchIndices.get(columnIndex), headerCellValues.get(columnIndex)?.toUpperCase())
                                                            } else {
                                                                if (dataMappingIndexes?.indexOf(headerCellValues.get(columnIndex)?.toUpperCase()) < dataMappingIndexes?.indexOf(dataMappingPriorities.get(trainingMatchIndices.get(columnIndex)))) {
                                                                    matchingValues.put(trainingMatchIndices.get(columnIndex), HtmlUtils.htmlUnescape(value ?: ""))
                                                                }
                                                            }
                                                        }

                                                        //new trainingdata
                                                        if (headerCellValue && value) {
                                                            if (classIdentifyingFields*.toUpperCase().contains(headerCellValue.toUpperCase())) {
                                                                trainingData.put("CLASS", HtmlUtils.htmlUnescape(value ?: ""))
                                                            } else if (dataMapping.get(headerCellValue.toUpperCase()) && !trainingData.get(dataMapping.get(headerCellValue.toUpperCase()))) {
                                                                trainingData.put(dataMapping.get(headerCellValue.toUpperCase()), HtmlUtils.htmlUnescape(value ?: ""))
                                                            }
                                                        }

                                                        if (headerCellValue && value) {
                                                            oneCellValueFound = true
                                                            boolean isNumericValue = DomainObjectUtil.isNumericValue(value)
                                                            // allDataFieldsFromImport.put(headerCellValue.replace(".",""), HtmlUtils.htmlUnescape(value))
                                                            allDataFieldsFromImport.put(headerCellValue.replace(".", ""), HtmlUtils.htmlUnescape(copyValue))

                                                            if (displayFieldIndices?.keySet()?.toList()?.contains(columnIndex)) {
                                                                if (dataset.importDisplayFields) {
                                                                    dataset.importDisplayFields.put(displayFieldIndices.get(columnIndex), copyValue?.toLowerCase())
                                                                } else {
                                                                    dataset.importDisplayFields = [(displayFieldIndices.get(columnIndex)): copyValue?.toLowerCase()]
                                                                }
                                                            }

                                                            if (unitIdentifyingField?.any { headerCellValue.equalsIgnoreCase(it) }) {
                                                                if ("count".equalsIgnoreCase(value)) {
                                                                    dataset.userGivenUnit = "unit"
                                                                } else {
                                                                    dataset.userGivenUnit = value.toLowerCase().trim()
                                                                }
                                                            }

                                                            if (area_m2CellIndex && area_m2CellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                                                dataset.area_m2 = DomainObjectUtil.convertStringToDouble(value).round(2)
                                                            }

                                                            if (volume_m3CellIndex && volume_m3CellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                                                dataset.volume_m3 = DomainObjectUtil.convertStringToDouble(value).round(2)
                                                            }

                                                            if (mass_kgCellIndex && mass_kgCellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                                                dataset.mass_kg = DomainObjectUtil.convertStringToDouble(value).round(2)
                                                            }

                                                            if (warningCellIndex && warningCellIndex.equals(cell.getColumnIndex())) {
                                                                dataset.importWarning = value
                                                            }

                                                            if (thickness_mCellIndex && thickness_mCellIndex.equals(cell.getColumnIndex())) {
                                                                dataset.importMapperThickness_m = value
                                                            }

                                                            if (volumeFractionCellIndex && volumeFractionCellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                                                dataset.specialRuleShare = DomainObjectUtil.convertStringToDouble(value)
                                                            }

                                                            if (volumeFractionMethodCellIndex && volumeFractionMethodCellIndex.equals(cell.getColumnIndex())) {
                                                                dataset.specialRuleId = value
                                                            }

                                                            if (quantityTypeCellIndex && quantityTypeCellIndex.equals(cell.getColumnIndex())) {
                                                                if (!allAllowedUnits*.toLowerCase()?.contains(value.toLowerCase())) {
                                                                    dataset.importError = dataset.importError ? dataset.importError + " and Data with unconventional unit: ${value}" : "Data with unconventional unit: ${value}"
                                                                    dataset.unconventionalUnit = Boolean.TRUE
                                                                }
                                                            }

                                                            if (ignoreCellIndex && ignoreCellIndex.equals(cell.getColumnIndex()) && value && value.toBoolean()) {
                                                                skipRow = true
                                                            }

                                                            if (presetFilter && presetFilter.skipQuestionMappingValues && trainingData.get("CLASS") && presetFilter.skipQuestionMappingValues.contains(trainingData.get("CLASS").toUpperCase())) {
                                                                Integer amount = removedByPresetFilter.get("removedByPresetFilter")

                                                                if (amount) {
                                                                    amount++
                                                                } else {
                                                                    amount = 1
                                                                }
                                                                removedByPresetFilter.put("removedByPresetFilter", amount)
                                                                dataset.filtered = Boolean.TRUE
                                                                skipRow = true
                                                            } else {
                                                                if (questionMappingCellIndices?.contains(columnIndex) &&
                                                                        !dataset.queryId && !dataset.sectionId && !dataset.questionId) {
                                                                    if (!skipQuestionMappingValues?.contains(value.toUpperCase())) {
                                                                        List<ImportMapperQuestionRule> importMapperQuestionRulesForIndicator = importMapper.questionMappingRules.findAll({ queryIdsForIndicator?.contains(it.target.get("queryId")) })

                                                                        questionLoop:
                                                                        for (ImportMapperQuestionRule importMapperQuestionRule in importMapperQuestionRulesForIndicator) {
                                                                            for (String matchingValue in importMapperQuestionRule.matchingValues) {
                                                                                if (matchingValue.toUpperCase().equals(value.toUpperCase())) {
                                                                                    dataset.queryId = importMapperQuestionRule.target?.get("queryId")
                                                                                    dataset.sectionId = importMapperQuestionRule.target?.get("sectionId")
                                                                                    dataset.questionId = importMapperQuestionRule.target?.get("questionId")
                                                                                    questionMappingFound = true
                                                                                    break questionLoop
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        questionMappingFound = true
                                                                    }
                                                                } else if (resourceMappingCellIndices?.contains(columnIndex) && !dataset.resourceId) {
                                                                    Boolean discardRow = Boolean.FALSE
                                                                    String unescapedForCompare = HtmlUtils.htmlUnescape(value ?: "")
                                                                    for (String discard in discardedValues) {
                                                                        if (discard.endsWith("*")) {
                                                                            if (unescapedForCompare.toUpperCase().startsWith(
                                                                                    discard.replaceAll("\\*", "")?.toUpperCase())) {
                                                                                dataset.discarded = Boolean.TRUE
                                                                                discardRow = Boolean.TRUE
                                                                                break
                                                                            }
                                                                        } else {
                                                                            if (discard.equalsIgnoreCase(unescapedForCompare)) {
                                                                                dataset.discarded = Boolean.TRUE
                                                                                discardRow = Boolean.TRUE
                                                                                break
                                                                            }
                                                                        }
                                                                    }

                                                                    if (!discardRow) {
                                                                        for (String warning in warningValues) {
                                                                            if (warning.endsWith("*")) {
                                                                                if (unescapedForCompare.toUpperCase().startsWith(
                                                                                        warning.replaceAll("\\*", "")?.toUpperCase())) {
                                                                                    dataset.allowMapping = Boolean.FALSE
                                                                                    break
                                                                                }
                                                                            } else {
                                                                                if (warning.equalsIgnoreCase(unescapedForCompare)) {
                                                                                    dataset.allowMapping = Boolean.FALSE
                                                                                    break
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        Integer amount = discardedValuesWithAmount.get(value)

                                                                        if (amount) {
                                                                            amount++
                                                                        } else {
                                                                            amount = 1
                                                                        }
                                                                        discardedValuesWithAmount.put(value, amount)
                                                                        skipRow = true
                                                                    }
                                                                } else {
                                                                    Cell hCell = headerRow.getCell(columnIndex.intValue())
                                                                    String headerValue = null
                                                                    if (hCell) {
                                                                        headerValue = formatter.formatCellValue(hCell)
                                                                    }

                                                                    if (headerValue) {
                                                                        ImportMapperDataRule importMapperDataRule

                                                                        for (ImportMapperDataRule dataRule in dataRules) {
                                                                            for (String mappedHeading in dataRule.mappedHeadings) {
                                                                                if (mappedHeading.toUpperCase().equals(headerValue.toUpperCase())) {
                                                                                    importMapperDataRule = dataRule
                                                                                    break
                                                                                }
                                                                            }

                                                                            if (importMapperDataRule) {
                                                                                break
                                                                            }
                                                                        }

                                                                        if (importMapperDataRule) {
                                                                            String target = importMapperDataRule.target
                                                                            if (additionalQuestionIds.contains(target)) {

                                                                                if (!thicknessHeading && "thickness_mm".equals(target)) {
                                                                                    thicknessHeading = headerValue
                                                                                }

                                                                                if (value) {
                                                                                    ClassificationQuestionTranslation classificationQuestionTranslation = classificationQuestionTranslations?.find({ target.equals(it.additionalQuestionId) })

                                                                                    if (classificationQuestionTranslation) {
                                                                                        classificationLoop:
                                                                                        for (ImportMapperTranslation translation in classificationQuestionTranslation.translations) {
                                                                                            for (String matchingValue in translation.matchingValues) {
                                                                                                if (matchingValue.endsWith("*")) {
                                                                                                    if (value.toUpperCase().startsWith(matchingValue.replaceAll("\\*", "").toUpperCase())) {
                                                                                                        value = translation.applyValue
                                                                                                        break classificationLoop
                                                                                                    }
                                                                                                } else if (matchingValue.equalsIgnoreCase(value)) {
                                                                                                    value = translation.applyValue
                                                                                                    break classificationLoop
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }

                                                                                    Boolean numeric = additionalQuestionIdIsNumeric.get(target)
                                                                                    if (target.equals(organizationClassificationId) && privateClassificationQuestion) {
                                                                                        target = privateClassificationQuestion?.questionId
                                                                                    }

                                                                                    if (!numeric || value.toString().replace(",", ".").isNumber()) {
                                                                                        if (dataset.additionalQuestionAnswers) {
                                                                                            dataset.additionalQuestionAnswers.put(target, value.toString())

                                                                                            if ("costPerUnit".equals(target)) {
                                                                                                dataset.userSetCost = Boolean.TRUE
                                                                                            } else if ("totalCost".equals(target)) {
                                                                                                dataset.userSetTotalCost = Boolean.TRUE
                                                                                            }
                                                                                        } else {
                                                                                            dataset.additionalQuestionAnswers = [(target): value.toString()]

                                                                                            if ("costPerUnit".equals(target)) {
                                                                                                dataset.userSetCost = Boolean.TRUE
                                                                                            } else if ("totalCost".equals(target)) {
                                                                                                dataset.userSetTotalCost = Boolean.TRUE
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                Class propertyClass = GrailsClassUtils.getPropertyType(Dataset.class, target)

                                                                                if (propertyClass) {
                                                                                    if (propertyClass.equals(Double.class)) {
                                                                                        if (value.isNumber() && value.toDouble() != 0D) {
                                                                                            DomainObjectUtil.callSetterByAttributeName(target, dataset, value.toDouble())
                                                                                        } else {
                                                                                            if ("quantity".equalsIgnoreCase(target) && !value.isNumber()) {
                                                                                                dataset.importMapperPartialQuantification = true
                                                                                                dataset.answerIds = [value]
                                                                                            } else {
                                                                                                DomainObjectUtil.callSetterByAttributeName(target, dataset, 0D)
                                                                                            }
                                                                                            // errorMessageUtil.setErrorMessage(messageSource.getMessage("importMapper.non_numerical_quantities", null, "Non processable quantities (bad number or unit) found. Values set to zero.", LocaleContextHolder.getLocale()), true)
                                                                                        }
                                                                                    } else {
                                                                                        DomainObjectUtil.callSetterByAttributeName(target, dataset, value)
                                                                                    }
                                                                                } else {
                                                                                    loggerUtil.warn(log, "Couldn't find propertyClass for dataset with target: ${target}")
                                                                                    flashService.setFadeWarningAlert("Couldn't find propertyClass for dataset with target: ${target}", true)
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                    if (columnsRolled == 0 || columnsRolled < foo) {
                                                        columnsRolled = foo
                                                    }

                                                    if (!skipRow && oneCellValueFound) {
                                                        if (!questionMappingFound) {
                                                            if (targetQuestionRule) {
                                                                if (question) {
                                                                    if (resourceCompatible > 0) {
                                                                        dataset.queryId = targetQuestionRule.get("queryId")
                                                                        dataset.sectionId = targetQuestionRule.get("sectionId")
                                                                        dataset.questionId = targetQuestionRule.get("questionId")
                                                                    }
                                                                }
                                                            } else {
                                                                loggerUtil.error(log, "IMPORTMAPPER: Misconfiguration! No default target found for entityClass: ${parentEntity?.entityClass ?: "building"}")
                                                                flashService.setErrorAlert("IMPORTMAPPER: Misconfiguration! No default target found for entityClass: ${parentEntity?.entityClass ?: "building"}", true)
                                                            }
                                                        }
                                                        dataset.dataForCollapser = allDataFieldsFromImport

                                                        if (noDisplayFields) {
                                                            Map<String, String> filteredFieldsFromImport = [:]
                                                            allDataFieldsFromImport?.each { String key, String value ->
                                                                if (!noDisplayFields*.toUpperCase().contains(key.toUpperCase())) {
                                                                    filteredFieldsFromImport.put(key, value)
                                                                }
                                                            }
                                                            dataset.allImportDisplayFields = [filteredFieldsFromImport]
                                                        } else {
                                                            dataset.allImportDisplayFields = [allDataFieldsFromImport]
                                                        }

                                                        if (dataset.quantity != null && !dataset.answerIds) {
                                                            dataset.answerIds = [FormatterUtil.doubleToString(dataset.quantity)]
                                                        }

                                                        if (trainAdaptiveRecognition) {
                                                            if (trainingData) {
                                                                if ((ifcMaterialValue && !ifcMaterialValue.equalsIgnoreCase(trainingData.get("RESOURCEID"))) ||
                                                                        (oclIdValue && !oclIdValue.equalsIgnoreCase(trainingData.get(OCLID)))
                                                                ) {
                                                                    if (ifcMaterialValue) {
                                                                        trainingData["RESOURCEID"] = ifcMaterialValue
                                                                    }
                                                                    if (oclIdValue) {
                                                                        trainingData[OCLID] = oclIdValue
                                                                    }
                                                                }
                                                            }
                                                            dataset.trainingMatchData = matchingValues
                                                            dataset.trainingData = trainingData
                                                            dataset.ifcMaterialValue = ifcMaterialValue
                                                        }

                                                        if (dataset.importMapperThickness_m && !dataset.additionalQuestionAnswers?.get("thickness_mm")) {
                                                            if (dataset.importMapperThickness_m.replaceAll(",", ".").trim().isNumber()) {
                                                                Double thicknessMinMM = dataset.importMapperThickness_m.replaceAll(",", ".").trim().toDouble()

                                                                if (thicknessMinMM) {
                                                                    if (dataset.additionalQuestionAnswers) {
                                                                        dataset.additionalQuestionAnswers["thickness_mm"] = "${thicknessMinMM * 1000}"
                                                                    } else {
                                                                        dataset.additionalQuestionAnswers = ["thickness_mm": "${thicknessMinMM * 1000}"]
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (dataset.validate() && dataset.queryId && dataset.questionId && dataset.sectionId && (!dataset.importMapperPartialQuantification || dataset.answerIds)) {
                                                            okDatasets.add(dataset)
                                                        } else {
                                                            rejectedDatasets.add(dataset)
                                                            def errors = dataset.getErrors()?.getAllErrors()

                                                            if (errors) {
                                                                loggerUtil.error(log, "Error in dataset: " + errors)
                                                                flashService.setErrorAlert("Error in dataset: " + errors, true)
                                                            }
                                                        }
                                                    } else {
                                                        discardedDatasets.add(dataset)
                                                        skippedRows++
                                                    }
                                                }
                                            }
                                        }
                                        log.info("Elapsed by mapper: ${System.currentTimeMillis() - now}")
                                        flashService.setFadeInfoAlert("Elapsed by mapper: ${System.currentTimeMillis() - now}", true)
                                    } else {
                                        missingMandatoryDataError = ""
                                        if (ifcMaterialCellIndex == null) {
                                            missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column IFCMATERIAL<br/>"
                                        }

                                        if (quantityCellIndex == null) {
                                            missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column QUANTITY<br/>"
                                        }

                                        if (quantityTypeCellIndex == null) {
                                            missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column QTY_TYPE<br/>"
                                        }
                                    }
                                }
                            }
                        }
                        loggerUtil.info(log, "Rows iterated: ${rowsRolled}")
                        loggerUtil.info(log, "Max cells iterated: ${columnsRolled}")
                        loggerUtil.info(log, "Total iterations: ${totalIterated}")
                        List<Dataset> rejectedAndOkDatasets = []

                        if (okDatasets) {
                            rejectedAndOkDatasets.addAll(okDatasets)
                        }
                        if (rejectedDatasets) {
                            rejectedAndOkDatasets.addAll(rejectedDatasets)
                        }
                        if (discardedDatasets) {
                            rejectedAndOkDatasets.addAll(discardedDatasets)
                        }

                        returnable.put("sheets", (sheets))
                        returnable.put("okDatasets", (okDatasets))
                        returnable.put("tooGenericDatasets", (okDatasets.findAll({ !it.allowMapping })))
                        returnable.put("rejectedDatasets", (rejectedDatasets))
                        returnable.put("skippedRows", skippedRows)
                        returnable.put("discardedValues", discardedValuesWithAmount)
                        returnable.put("emptyRows", emptyRows)
                        returnable.put("removedByPresetFilter", removedByPresetFilter)
                        returnable.put("warningMessage", warningMessage)
                        returnable.put("collapseGroupers", createCollapseGroupers(okDatasets, null,
                                importMapper.groupByForCollapse, importMapper.dataMappingRules,
                                headers, importMapper, api, importMapper.groupByForCollapseSpecial))
                        returnable.put("headers", headers)
                        returnable.put("missingMandatoryDataError", missingMandatoryDataError)
                        returnable.put("materialHeading", materialHeading)
                        returnable.put("rejectedAndOkDatasets", (rejectedAndOkDatasets))
                        returnable.put("thicknessHeading", thicknessHeading)
                    } catch (Exception e) {
                        loggerUtil.warn(log, "Error in importing datasets", e)
                        loggerUtil.warn(log, "Error in importing IFC file. Perhaps not a proper Excel file.")
                        flashService.setErrorAlert("Error in importing datasets ${e.getMessage()}", true)
                        flashService.setErrorAlert("Error in importing IFC file. Perhaps not a proper Excel file.", true)

                    }
                } else {
                    returnable.put("errorMessage", "Non compatible entityClass. ImportMapper requires ${importMapper.compatibleEntityClasses}, but entityClass is ${parentEntity?.entityClass ?: "building"}")
                }
            } else {
                loggerUtil.error(log, "No entity or parentEntity for entity found")
                flashService.setErrorAlert("No entity or parentEntity for entity found", true)
            }
        }
        return returnable
    }


    ImportedByImportMapperReturn importDatasetsByImportMapper(ImportMapper importMapper, String parentEntityId,
                                                              Workbook workbook, Boolean useAdaptiveRecognition = null,
                                                              Boolean trainAdaptiveRecognition = null, ImportMapperPresetFilter presetFilter = null,
                                                              Boolean removeEmptyData = Boolean.TRUE, Boolean temporaryCalculation = Boolean.FALSE,
                                                              Boolean useOriginalClass = Boolean.FALSE, Boolean api = Boolean.FALSE,
                                                              String indicatorId = null, String childEntityId = null, Boolean updateOnlyQuantity = Boolean.FALSE) {

        ImportedByImportMapperReturn importMapperReturn = new ImportedByImportMapperReturn()
        DataFormatter formatter = new DataFormatter()

        if (!importMapper || !(parentEntityId || temporaryCalculation) || !workbook) {
            return importMapperReturn
        }

        Entity parentEntity = Entity.read(parentEntityId)

        if (!temporaryCalculation) {
            if (!parentEntity) {
                loggerUtil.error(log, "No entity or parentEntity for entity found")
                flashService.setErrorAlert("No entity or parentEntity for entity found", true)
                return importMapperReturn
            }

            if (importMapper.compatibleEntityClasses &&
                    !importMapper.compatibleEntityClasses?.contains(parentEntity?.entityClass ?: "building")) {
                importMapperReturn.errorMessage = "Non compatible entityClass. ImportMapper requires ${importMapper.compatibleEntityClasses}, but entityClass is ${parentEntity?.entityClass ?: "building"}"
                return importMapperReturn
            }
        }

        Map<String, String> targetQuestionRule = importMapper.questionMappingRules.find({
            it.defaultTargetForEntityClass?.contains(parentEntity?.entityClass ?: "building")
        })?.target
        Question question = null
        if (targetQuestionRule) {
            question = ((QuestionService) questionService)
                    .getQuestion(targetQuestionRule.get("queryId"), targetQuestionRule.get("questionId"))
        }

        Entity child = Entity.read(childEntityId)
        Indicator indicator = ((IndicatorService) indicatorService).getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
        List<License> validLicenses = licenseService.getValidLicensesForEntity(child)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

        List<Query> queriesForIndicator = queryService.getQueriesByIndicatorAndEntity(indicator, child, featuresAvailableForEntity)
        List<Question> allQuestions = queriesForIndicator*.allQuestions?.flatten()
        List<String> queryIdsForIndicator = queriesForIndicator*.queryId
        List<ImportMapperQuestionRule> resourceQuestionRulesForIndicator = importMapper.questionMappingRules.findAll { queryIdsForIndicator?.contains(it.target.get("queryId")) }
        List<ImportMapperQuestionRule> descriptiveQuestionRulesForIndicator = importMapper.descriptiveQuestionMappingRules.findAll { queryIdsForIndicator?.contains(it.target.get("queryId")) }

        Set<String> acceptedClasses = getAcceptedClassesFromRules(resourceQuestionRulesForIndicator)
        Set<String> acceptedDescriptiveClasses = getAcceptedClassesFromRules(descriptiveQuestionRulesForIndicator)

        Map<String, String> dataMapping = [:]
        List<String> trainingMatch = []
        importMapper.dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->
            if (importMapper.trainingMatch && !importMapper.trainingMatch.isEmpty() && importMapper.trainingMatch*.toUpperCase().contains(importMapperDataRule.target?.toUpperCase())) {
                if (importMapperDataRule.mappedHeadings && !importMapperDataRule.mappedHeadings.isEmpty()) {
                    trainingMatch.addAll(importMapperDataRule.mappedHeadings*.toUpperCase())
                }
            }
            importMapperDataRule.mappedHeadings?.each { String heading ->
                dataMapping.put(heading.toUpperCase(), importMapperDataRule.target?.toUpperCase())
            }
        }

        List<String> classIdentifyingFields = importMapper.classIdentifyingFields ?: ["CLASS"]
        if (importMapper.trainingMatch && importMapper.trainingMatch*.toUpperCase().contains("CLASS")) {
            classIdentifyingFields.each { String classHeading ->
                if (!trainingMatch.contains(classHeading)) {
                    trainingMatch.add(classHeading)
                }
            }
        }

        List<String> dataMappingIndexes = dataMapping.keySet()?.toList()

        List<String> discardedValues = getValuesFromRecognitionRulesetsByType(RulesetType.DISCARD)
        List<String> warningValues = getValuesFromRecognitionRulesetsByType(RulesetType.WARN)

        try {
            Integer numberOfSheets = workbook?.getNumberOfSheets()
            ImportMapperDataRule resourceMappingRule = importMapper.dataMappingRules?.find({
                "resourceId".equals(it.target)
            })

            resourceMappingRule?.discardRowsWithValues?.each { String discardedValue ->
                discardedValues.add(discardedValue.toUpperCase())
            }

            User user = ((UserService) userService).getCurrentUser()
            List<Sheet> sheets = []

            Map<String, Boolean> additionalQuestionIdIsNumeric = [:]
            Set<String> additionalQuestionIds = []

            if (numberOfSheets) {
                Configuration additionalQuestionQueryConfig = ((ConfigurationService) configurationService).getByConfigurationName(
                        Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString()
                )
                String additionalQuestionQueryId = additionalQuestionQueryConfig?.value

                if (additionalQuestionQueryId) {
                    Query additionalQuestionsQuery = ((QueryService) queryService).getQueryByQueryId(additionalQuestionQueryId, true)
                    additionalQuestionsQuery?.allQuestions?.each({ Question q ->
                        additionalQuestionIds.add(q.questionId)
                        additionalQuestionIdIsNumeric.put((q.questionId), "numeric".equals(q.valueConstraints?.get("type")))
                    })
                } else {
                    loggerUtil.error(log, "Cannot get additionalQuestionQuery. Probably missing configuration for additionalQuestionQueryId or query not imported at all.")
                    flashService.setWarningAlert("Cannot get additionalQuestionQuery. Probably missing configuration for additionalQuestionQueryId or query not imported at all.", true)
                }
                additionalQuestionIds.removeAll(((DomainClassService) domainClassService).getPersistentPropertyNamesForDomainClass(Dataset.class))

                Question privateClassificationQuestion = null
                String organizationClassificationId = com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID
                if (indicator?.classificationsMap?.contains(organizationClassificationId)) {
                    privateClassificationQuestion = ((PrivateClassificationListService) privateClassificationListService)
                            .getPrivateClassificationQuestion()
                    additionalQuestionIds.add(organizationClassificationId)
                }
                List<String> questionMappingHeadings = importMapper.questionMappingHeadings*.toUpperCase()
                List<String> resourceMappingHeadings = new ArrayList<String>(resourceMappingRule.mappedHeadings)

                for (int i = 0; i < numberOfSheets; i++) {
                    Sheet sheet = workbook.getSheetAt(i)

                    if (!sheet.getSheetName()?.startsWith("@")) {
                        sheets.add(sheet)
                        if (isDescriptiveDataSheet(sheet)) {
                            // we don't import descriptive data if user selects to update only quantity
                            if (!updateOnlyQuantity) {
                                importMapperReturn = importDescriptiveDataFromSheet(importMapper, importMapperReturn,
                                        sheet, formatter, descriptiveQuestionRulesForIndicator, acceptedDescriptiveClasses,
                                        classIdentifyingFields, additionalQuestionIdIsNumeric, additionalQuestionIds,
                                        allQuestions, user, removeEmptyData, useOriginalClass)
                            }
                        } else {
                            importMapperReturn = importResourcesDataFromSheet(importMapper, importMapperReturn,
                                    sheet, formatter, child, indicator, resourceQuestionRulesForIndicator,
                                    questionMappingHeadings, resourceMappingHeadings, trainingMatch,
                                    discardedValues, warningValues, acceptedClasses,
                                    classIdentifyingFields, targetQuestionRule, question,
                                    additionalQuestionIdIsNumeric, additionalQuestionIds,
                                    dataMapping, dataMappingIndexes, user,
                                    privateClassificationQuestion,
                                    useAdaptiveRecognition, trainAdaptiveRecognition,
                                    presetFilter, removeEmptyData, useOriginalClass)
                        }
                    }
                }
            }
            ImportMapperIterationStatistic iterationStatistic = importMapperReturn.iterationStatistic
            loggerUtil.info(log, "Rows iterated: ${iterationStatistic.rowsRolled}")
            loggerUtil.info(log, "Max cells iterated: ${iterationStatistic.columnsRolled}")
            loggerUtil.info(log, "Total iterations: ${iterationStatistic.totalIterated}")

            Map<String, Integer> truncatedFields = importMapperReturn.truncatedFields
            if (truncatedFields) {
                String warning = """The following fields have been trimmed to the maximum lenght: <ul>
${truncatedFields.collect { String fieldName, Integer maxLengthTrim ->
                    "<li>${fieldName}: maximum length ${maxLengthTrim} characters</li>"
                }?.join()}</ul>"""
                log.warn("The following fields have been trimmed to the maximum lenght: ${truncatedFields}")
                flashService.setWarningAlert(warning)
            }

            importMapperReturn.sheets = sheets
            importMapperReturn.collapseGroupers = createCollapseGroupers(importMapperReturn.okDatasets, null,
                    importMapper.groupByForCollapse, importMapper.dataMappingRules,
                    importMapperReturn.headers, importMapper, api, importMapper.groupByForCollapseSpecial)

        } catch (Exception e) {
            loggerUtil.warn(log, "Error in importing datasets", e)
            loggerUtil.warn(log, "Error in importing IFC file. Perhaps not a proper Excel file.")
            flashService.setErrorAlert("Error in importing datasets ${e.getMessage()}", true)
            flashService.setErrorAlert("Error in importing IFC file. Perhaps not a proper Excel file.", true)

        }

        return importMapperReturn
    }

    /**
     * Get all textMatch values for Recognition Rulesets of Ruleset Type
     * @param rulesetType
     * @return
     */
    private List<String> getValuesFromRecognitionRulesetsByType(RulesetType rulesetType) {

        List<String> values = []
        //new recognition rulesets
        List<RecognitionRuleset> rulesets = ((RecognitionRulesetService) recognitionRulesetService)
                .getAllRecognitionRulesets(rulesetType.toString())

        if (rulesets) {
            rulesets.each { RecognitionRuleset recognitionRuleset ->
                recognitionRuleset.textMatches?.each { TextMatch textMatch ->
                    String txtMatch = textMatch.textMatch
                    if (textMatch.matchAllVariants) {
                        values.add(txtMatch + '*')
                    } else {
                        values.add(txtMatch)
                    }
                }
            }
        }

        return values
    }

    /**
     * Checks if value is match to values from Recognition Rulesets
     * @param valuesFromRecognitionRulesets
     * @param value
     * @return
     */
    private Boolean isValueMatchToRecognitionRulesetsValues(List<String> valuesFromRecognitionRulesets, String value) {

        Boolean match = false

        String unescapedForCompare = HtmlUtils.htmlUnescape(value ?: "")
        for (String rulesetValue in valuesFromRecognitionRulesets) {
            if (rulesetValue.endsWith("*")) {
                if (unescapedForCompare.toUpperCase().startsWith(
                        rulesetValue.replaceAll("\\*", "")?.toUpperCase())) {
                    match = Boolean.TRUE
                    break
                }
            } else {
                if (rulesetValue.equalsIgnoreCase(unescapedForCompare)) {
                    match = Boolean.TRUE
                    break
                }
            }
        }

        return match
    }

    /**
     * Get all accepted classes from QuestionMappingRules
     * @param questionMappingRules {@link com.bionova.optimi.core.domain.mongo.ImportMapperQuestionRule}
     *                          or {@link com.bionova.optimi.core.domain.mongo.ImportMapperDescriptiveQuestionRule}
     * @return
     */
    private Set<String> getAcceptedClassesFromRules(List questionMappingRules) {

        Set<String> acceptedClasses = []

        questionMappingRules?.each { questionMappingRule ->
            acceptedClasses.addAll(questionMappingRule.matchingValues*.toLowerCase())
        }

        return acceptedClasses
    }

    /**
     * Find QuestionMappingRule from list of rules by matching value
     * @param questionMappingRuleList - list of {@link ImportMapperQuestionRule}
     *                                       or {@link ImportMapperDescriptiveQuestionRule}
     * @param matchingValue
     * @return {@link ImportMapperQuestionRule} or {@link ImportMapperDescriptiveQuestionRule} depends on input list
     */
    private def findQuestionRuleFromListByMatchingValue(List questionMappingRuleList, String matchingValue) {

        def questionMappingRule = questionMappingRuleList.find {
            it.matchingValues.any { it.equalsIgnoreCase(matchingValue) }
        }

        return questionMappingRule
    }

    /**
     * Checks whether sheet contains descriptive data
     * @param sheet
     * @return true if the sheet contains column CONTENT
     */
    private Boolean isDescriptiveDataSheet(Sheet sheet) {

        Boolean isDescriptive = false

        int headerRowNo = sheet.getFirstRowNum()
        Row headerRow = sheet.getRow(headerRowNo)
        Iterator<Cell> headerCellIterator = headerRow.iterator()

        while (headerCellIterator.hasNext()) {
            Cell headerCell = headerCellIterator.next()
            if (headerCell) {
                String headerCellValue = headerCell.getStringCellValue()
                if (headerCellValue && Const.DESCRIPTIVE_DATA_COLUMN_NAME.equalsIgnoreCase(headerCellValue.trim())) {
                    isDescriptive = true
                    break
                }
            }
        }

        return isDescriptive
    }

    private ImportedByImportMapperReturn importResourcesDataFromSheet(ImportMapper importMapper,
                                                                      ImportedByImportMapperReturn importMapperReturn,
                                                                      Sheet sheet, DataFormatter formatter, Entity child, Indicator indicator,
                                                                      List<ImportMapperQuestionRule> questionMappingRules,
                                                                      List<String> questionMappingHeadings, List<String> resourceMappingHeadings,
                                                                      List<String> trainingMatch, List<String> discardedValues, List<String> warningValues, Set<String> acceptedClasses,
                                                                      List<String> classIdentifyingFields, Map<String, String> targetQuestionRule, Question question,
                                                                      Map<String, Boolean> additionalQuestionIdIsNumeric, Set<String> additionalQuestionIds,
                                                                      Map<String, String> dataMapping, List<String> dataMappingIndexes, User user,
                                                                      Question privateClassificationQuestion,
                                                                      Boolean useAdaptiveRecognition, Boolean trainAdaptiveRecognition,
                                                                      ImportMapperPresetFilter presetFilter, Boolean removeEmptyData,
                                                                      Boolean useOriginalClass) {

        int headerRowNo = sheet.getFirstRowNum()
        Row headerRow = sheet.getRow(headerRowNo)
        Iterator<Cell> headerCellIterator = headerRow.iterator()
        List<Integer> questionMappingCellIndices = []
        Integer classCellIndex
        Integer quantityCellIndex
        Integer quantityTypeCellIndex
        Integer categoryCellIndex = null
        Integer ignoreCellIndex
        Integer area_m2CellIndex
        Integer volume_m3CellIndex
        Integer mass_kgCellIndex
        Integer ifcMaterialCellIndex
        Integer warningCellIndex
        Integer thickness_mCellIndex
        Integer volumeFractionCellIndex
        Integer volumeFractionMethodCellIndex
        Integer oclIdCellIndex
        List<Integer> resourceMappingCellIndices = []
        Map<Integer, String> headerCellValues = [:]
        Map<Integer, String> displayFieldIndices = [:]
        Map<Integer, String> trainingMatchIndices = [:]
        List<Integer> questionMappingSpecialCaseIndices = []
        List<String> questionMappingSpecialCaseLookupHeadings = importMapper.questionMappingSpecialCaseLookupHeading*.toUpperCase()

        List<ImportMapperDataRule> dataRules = importMapper.dataMappingRules?.
                findAll({ !"resourceId".equalsIgnoreCase(it.target) })

        List<String> excludeColumnsFromImport = importMapper.excludeFromImport
        List<String> displayFields = importMapper.displayFields*.toUpperCase() ?: []
        List<String> noDisplayFields = importMapper.noDisplayFields
        List<String> skipQuestionMappingValues = importMapper.skipQuestionMappingValues*.toUpperCase() ?: []

        List<String> allAllowedUnits = importMapper.allowedUnits ?: com.bionova.optimi.core.Constants.ALL_UNITS
        Map<String, Boolean> compositeMaterialsRule = importMapper.compositeMaterialsRule
        Map<String, List<String>> partialQuantificationRule = importMapper.partialQuantificationRule

        List<String> unitIdentifyingField = importMapper.unitIdentifyingFields ?: importMapper.unitIdentifyingField ? [importMapper.unitIdentifyingField] : null

        String organizationClassificationId = com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID

        while (headerCellIterator.hasNext()) {
            Cell headerCell = headerCellIterator.next()
            String headerCellValue = null
            if (headerCell) {
                headerCellValue = formatter.formatCellValue(headerCell)
            }

            if (!headerCellValue?.startsWith("@") && (!excludeColumnsFromImport || !excludeColumnsFromImport*.toUpperCase().contains(headerCellValue?.toUpperCase()))) {
                headerCellValues.put(new Integer(headerCell.getColumnIndex()), headerCellValue)
                importMapperReturn.headers.add(headerCellValue?.trim())

                if (questionMappingSpecialCaseLookupHeadings && questionMappingSpecialCaseLookupHeadings.contains(headerCellValue.toUpperCase())) {
                    questionMappingSpecialCaseIndices.add(new Integer(headerCell.getColumnIndex()))
                }

                if (displayFields?.contains(headerCellValue?.toUpperCase())) {
                    displayFieldIndices.put(new Integer(headerCell.getColumnIndex()), headerCellValue)
                }

                if (questionMappingHeadings?.contains(headerCellValue?.toUpperCase())) {
                    questionMappingCellIndices.add(new Integer(headerCell.getColumnIndex()))
                } else if (resourceMappingHeadings?.collect({
                    it.toUpperCase()
                })?.contains(headerCellValue.toUpperCase())) {
                    resourceMappingCellIndices.add(new Integer(headerCell.getColumnIndex()))
                }

                if ((useAdaptiveRecognition || trainAdaptiveRecognition) && ((trainingMatch.contains(headerCellValue?.toUpperCase()) && dataMapping.get(headerCellValue?.toUpperCase())) ||
                        (trainingMatch.contains(headerCellValue?.toUpperCase()) && classIdentifyingFields*.toUpperCase().contains(headerCellValue?.toUpperCase())))) {
                    if (classIdentifyingFields*.toUpperCase().contains(headerCellValue?.toUpperCase())) {
                        trainingMatchIndices.put(new Integer(headerCell.getColumnIndex()), "CLASS")
                    } else {
                        trainingMatchIndices.put(new Integer(headerCell.getColumnIndex()), dataMapping.get(headerCellValue?.toUpperCase()))
                    }
                }

                if (classIdentifyingFields.any { headerCellValue.trim()?.equalsIgnoreCase(it) }) {
                    classCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("quantity".equalsIgnoreCase(dataMapping?.get(headerCellValue.toUpperCase().trim()))) {
                    quantityCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("area_m2".equalsIgnoreCase(headerCellValue.trim())) {
                    area_m2CellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("volume_m3".equalsIgnoreCase(headerCellValue.trim())) {
                    volume_m3CellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("mass_kg".equalsIgnoreCase(headerCellValue.trim())) {
                    mass_kgCellIndex = new Integer(headerCell.getColumnIndex())
                } else if (unitIdentifyingField.any { headerCellValue.trim().equalsIgnoreCase(it) }) {
                    quantityTypeCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("warnings".equalsIgnoreCase(headerCellValue.trim())) {
                    warningCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("thickness_m".equalsIgnoreCase(headerCellValue.trim())) {
                    thickness_mCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("specialRuleShare".equalsIgnoreCase(headerCellValue.trim())) {
                    volumeFractionCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("specialRuleId".equalsIgnoreCase(headerCellValue.trim())) {
                    volumeFractionMethodCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("category".equalsIgnoreCase(headerCellValue.trim())) {
                    categoryCellIndex = new Integer(headerCell.getColumnIndex())
                } else if ("ignore".equalsIgnoreCase(headerCellValue.trim())) {
                    ignoreCellIndex = new Integer(headerCell.getColumnIndex())
                } else if (OCLID.equalsIgnoreCase(headerCellValue.trim())) {
                    oclIdCellIndex = new Integer(headerCell.getColumnIndex())
                }
            }
        }

        if (resourceMappingCellIndices && !resourceMappingCellIndices.isEmpty()) {
            Integer prioHeading
            Integer priority
            resourceMappingCellIndices.each { Integer index ->
                Integer dataMappingIndex = dataMappingIndexes?.indexOf(headerCellValues.get(index)?.toUpperCase())
                if (!prioHeading || (dataMappingIndex < priority)) {
                    priority = dataMappingIndex
                    prioHeading = index
                }
            }

            ifcMaterialCellIndex = prioHeading ?: resourceMappingCellIndices.get(0)
        }
        Iterator<Row> sheetRowIterator = sheet.iterator()
        List<ImportMapperTranslation> questionMappingClassTranslations = importMapper.questionMappingClassTranslations
        List<ClassificationQuestionTranslation> classificationQuestionTranslations = importMapper.classificationQuestionTranslations

        if (ifcMaterialCellIndex != null && quantityCellIndex != null && quantityTypeCellIndex != null) {
            importMapperReturn.materialHeading = headerCellValues.get(ifcMaterialCellIndex)
            long now = System.currentTimeMillis()
            Integer resourceCompatibleCount = getCompatibleResourceCountForQuestion(child, indicator, question)

            while (sheetRowIterator.hasNext()) {
                Row row = sheetRowIterator.next()
                importMapperReturn.iterationStatistic.rowsRolled++

                if (row.getRowNum() > headerRowNo) {
                    String ifcMaterialValue = getCellValue(row.getCell(ifcMaterialCellIndex))
                    String oclIdValue
                    //REL-381
                    if (oclIdCellIndex) {
                        oclIdValue = getCellValue(row.getCell(oclIdCellIndex))
                    }
                    String ifcMaterialValueOrg = ifcMaterialValue
                    if (ifcMaterialValue) {
                        ifcMaterialValue = normalizeValue(ifcMaterialValue, true, true, false)
                    }

                    String quantityValue = getCellValue(row.getCell(quantityCellIndex))
                    if (quantityValue && ("0".equals(quantityValue) || "0.0".equals(quantityValue))) {
                        quantityValue = null
                    }
                    String quantityTypeValue = getCellValue(row.getCell(quantityTypeCellIndex))
                    boolean skipRow = false

                    quantityTypeValue = remapUnitQuantityType(importMapper, quantityTypeValue)

                    if ((removeEmptyData && (!ifcMaterialValue || !quantityValue || !quantityTypeValue)) ||
                            (!ifcMaterialValue && !quantityValue && !quantityTypeValue)) {
                        Integer amount = importMapperReturn.emptyRows.get("emptyRows") ?: 0
                        amount++
                        importMapperReturn.emptyRows.put("emptyRows", amount)
                    } else {
                        Map<String, String> dataMappingPriorities = [:]
                        Map<String, String> matchingValues = [:]
                        Dataset dataset = new Dataset()
                        dataset.ifcMaterialValueOrg = ifcMaterialValueOrg
                        dataset.usedUnitSystem = user?.unitSystem ? user.unitSystem :
                                UnitConversionUtil.UnitSystem.METRIC.value
                        dataset.manualId = new ObjectId().toString()
                        Map<String, String> trainingData = [:]
                        Map<String, String> allDataFieldsFromImport = [:]
                        dataset.allowMapping = Boolean.TRUE
                        boolean questionMappingFound = false
                        Iterator<Cell> cellIterator = row.iterator()
                        int foo = 0
                        boolean oneCellValueFound = false

                        while (cellIterator.hasNext() && !skipRow) {
                            importMapperReturn.iterationStatistic.totalIterated++
                            foo++
                            Cell cell = cellIterator.next()
                            Integer columnIndex = new Integer(cell.getColumnIndex())
                            String headerCellValue = headerCellValues.get(columnIndex)?.trim()

                            if (columnIndex == classCellIndex) {
                                String classValue = getCellValue(cell)
                                if (useOriginalClass) {
                                    dataset.originalClass = classValue
                                } else {
                                    dataset.persistedOriginalClass = classValue
                                }
                            }
                            handleImportMapperQuestionMappingSpecialCases(importMapper, questionMappingCellIndices as Set, questionMappingSpecialCaseIndices, row, cell)
                            handleImportMapperQuestionMappingClassTranslations(questionMappingClassTranslations, classCellIndex, cell)


                            String value = remapUnitQuantityType(importMapper, getCellValue(cell))

                            if (columnIndex == categoryCellIndex) {
                                dataset.categoryMaterialImport = value
                            }
                            if (columnIndex == classCellIndex) {
                                if (!acceptedClasses.contains(value?.toLowerCase())) {
                                    dataset.importError = dataset.importError ?
                                            dataset.importError + " and Data with unconventional classification: ${value}" :
                                            "Data with unconventional classification: ${value}"
                                    dataset.unconventionalClassification = Boolean.TRUE
                                }
                            }
                            String copyValue = normalizeValue(value)
                            value = normalizeValue(value, true, (columnIndex == ifcMaterialCellIndex))

                            if (compositeMaterialsRule && headerCellValue) {
                                def valueForCompositeMaterialRule = compositeMaterialsRule.get(headerCellValue.toUpperCase())

                                if (valueForCompositeMaterialRule && valueForCompositeMaterialRule.toString().toUpperCase().equals(value?.toUpperCase())) {
                                    dataset.importMapperCompositeMaterial = Boolean.TRUE
                                }
                            }

                            if (partialQuantificationRule && headerCellValue) {
                                List<String> partialQuantificationValues = partialQuantificationRule.get(headerCellValue.toUpperCase())

                                if (partialQuantificationValues && partialQuantificationValues.collect({
                                    it.toUpperCase()
                                }).contains(value.toUpperCase())) {
                                    dataset.importMapperPartialQuantification = Boolean.TRUE
                                }
                            }

                            if (trainingMatchIndices?.keySet()?.contains(columnIndex)) {
                                String trainingMatchValue = trainingMatchIndices.get(columnIndex)
                                if (!matchingValues.get(trainingMatchValue)) {
                                    matchingValues.put(trainingMatchValue, HtmlUtils.htmlUnescape(value ?: ""))
                                    dataMappingPriorities.put(trainingMatchValue, headerCellValues.get(columnIndex)?.toUpperCase())
                                } else {
                                    if (dataMappingIndexes?.indexOf(headerCellValues.get(columnIndex)?.toUpperCase()) < dataMappingIndexes?.indexOf(dataMappingPriorities.get(trainingMatchValue))) {
                                        matchingValues.put(trainingMatchValue, HtmlUtils.htmlUnescape(value ?: ""))
                                    }
                                }
                            }

                            if (headerCellValue && value) {
                                //new trainingdata
                                if (classIdentifyingFields*.toUpperCase().contains(headerCellValue.toUpperCase())) {
                                    trainingData.put("CLASS", HtmlUtils.htmlUnescape(value ?: ""))
                                } else if (dataMapping.get(headerCellValue.toUpperCase()) && !trainingData.get(dataMapping.get(headerCellValue.toUpperCase()))) {
                                    trainingData.put(dataMapping.get(headerCellValue.toUpperCase()), HtmlUtils.htmlUnescape(value ?: ""))
                                }

                                oneCellValueFound = true
                                boolean isNumericValue = DomainObjectUtil.isNumericValue(value)
                                // allDataFieldsFromImport.put(headerCellValue.replace(".",""), HtmlUtils.htmlUnescape(value))
                                allDataFieldsFromImport.put(headerCellValue.replace(".", ""), HtmlUtils.htmlUnescape(copyValue))

                                if (displayFieldIndices?.keySet()?.toList()?.contains(columnIndex)) {
                                    if (dataset.importDisplayFields) {
                                        dataset.importDisplayFields.put(displayFieldIndices.get(columnIndex), copyValue?.toLowerCase())
                                    } else {
                                        dataset.importDisplayFields = [(displayFieldIndices.get(columnIndex)): copyValue?.toLowerCase()]
                                    }
                                }

                                if (unitIdentifyingField?.any { headerCellValue.equalsIgnoreCase(it) }) {
                                    if ("count".equalsIgnoreCase(value)) {
                                        dataset.userGivenUnit = "unit"
                                    } else {
                                        dataset.userGivenUnit = value.toLowerCase().trim()
                                    }
                                }

                                if (area_m2CellIndex && area_m2CellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                    dataset.area_m2 = DomainObjectUtil.convertStringToDouble(value).round(2)
                                }

                                if (volume_m3CellIndex && volume_m3CellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                    dataset.volume_m3 = DomainObjectUtil.convertStringToDouble(value).round(2)
                                }

                                if (mass_kgCellIndex && mass_kgCellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                    dataset.mass_kg = DomainObjectUtil.convertStringToDouble(value).round(2)
                                }

                                if (warningCellIndex && warningCellIndex.equals(cell.getColumnIndex())) {
                                    dataset.importWarning = value
                                }

                                if (thickness_mCellIndex && thickness_mCellIndex.equals(cell.getColumnIndex())) {
                                    dataset.importMapperThickness_m = value
                                }

                                if (volumeFractionCellIndex && volumeFractionCellIndex.equals(cell.getColumnIndex()) && isNumericValue) {
                                    dataset.specialRuleShare = DomainObjectUtil.convertStringToDouble(value)
                                }

                                if (volumeFractionMethodCellIndex && volumeFractionMethodCellIndex.equals(cell.getColumnIndex())) {
                                    dataset.specialRuleId = value
                                }

                                if (quantityTypeCellIndex && quantityTypeCellIndex.equals(cell.getColumnIndex())) {
                                    if (!allAllowedUnits*.toLowerCase()?.contains(value.toLowerCase())) {
                                        dataset.importError = dataset.importError ? dataset.importError + " and Data with unconventional unit: ${value}" : "Data with unconventional unit: ${value}"
                                        dataset.unconventionalUnit = Boolean.TRUE
                                    }
                                }

                                if (ignoreCellIndex && ignoreCellIndex.equals(cell.getColumnIndex()) && value && value.toBoolean()) {
                                    skipRow = true
                                }

                                if (presetFilter && presetFilter.skipQuestionMappingValues && trainingData.get("CLASS") && presetFilter.skipQuestionMappingValues.contains(trainingData.get("CLASS").toUpperCase())) {
                                    Integer amount = importMapperReturn.removedByPresetFilter.get("removedByPresetFilter")

                                    if (amount) {
                                        amount++
                                    } else {
                                        amount = 1
                                    }
                                    importMapperReturn.removedByPresetFilter.put("removedByPresetFilter", amount)
                                    dataset.filtered = Boolean.TRUE
                                    skipRow = true
                                } else {
                                    if (questionMappingCellIndices?.contains(columnIndex) &&
                                            !dataset.queryId && !dataset.sectionId && !dataset.questionId) {
                                        if (!skipQuestionMappingValues?.contains(value.toUpperCase())) {
                                            ImportMapperQuestionRule importMapperQuestionRule = findQuestionRuleFromListByMatchingValue(questionMappingRules, value)
                                            if (importMapperQuestionRule) {
                                                dataset.queryId = importMapperQuestionRule.target?.get("queryId")
                                                dataset.sectionId = importMapperQuestionRule.target?.get("sectionId")
                                                dataset.questionId = importMapperQuestionRule.target?.get("questionId")
                                                questionMappingFound = true
                                            }
                                        } else {
                                            questionMappingFound = true
                                        }
                                    } else if (resourceMappingCellIndices?.contains(columnIndex) && !dataset.resourceId) {
                                        Boolean isDiscardRow = isValueMatchToRecognitionRulesetsValues(discardedValues, value)
                                        if (isDiscardRow) {
                                            dataset.discarded = Boolean.TRUE
                                            Integer amount = importMapperReturn.discardedValuesWithAmount.get(value) ?: 0
                                            amount++
                                            importMapperReturn.discardedValuesWithAmount.put(value, amount)
                                            skipRow = true
                                        } else {
                                            Boolean isWarningRow = isValueMatchToRecognitionRulesetsValues(warningValues, value)
                                            if (isWarningRow) {
                                                dataset.allowMapping = Boolean.FALSE
                                            }
                                        }
                                    } else {
                                        Cell hCell = headerRow.getCell(columnIndex.intValue())
                                        String headerValue = hCell ? formatter.formatCellValue(hCell) : null

                                        if (headerValue) {
                                            ImportMapperDataRule importMapperDataRule = dataRules.find { ImportMapperDataRule dataRule ->
                                                dataRule.mappedHeadings.find { it.equalsIgnoreCase(headerValue) }
                                            }

                                            if (importMapperDataRule) {
                                                String target = importMapperDataRule.target
                                                if (additionalQuestionIds.contains(target)) {

                                                    if (!importMapperReturn.thicknessHeading && "thickness_mm".equals(target)) {
                                                        importMapperReturn.thicknessHeading = headerValue
                                                    }

                                                    if (value) {
                                                        ClassificationQuestionTranslation classificationQuestionTranslation = classificationQuestionTranslations?.find({ target.equals(it.additionalQuestionId) })

                                                        if (classificationQuestionTranslation) {
                                                            value = applyClassificationQuestionTranslation(classificationQuestionTranslation, value)
                                                        }

                                                        Boolean numeric = additionalQuestionIdIsNumeric.get(target)
                                                        if (target.equals(organizationClassificationId) && privateClassificationQuestion) {
                                                            target = privateClassificationQuestion?.questionId
                                                        }

                                                        if (!numeric || DomainObjectUtil.isNumericValue(value)) {
                                                            dataset.additionalQuestionAnswers = dataset.additionalQuestionAnswers ?: [:]
                                                            dataset.additionalQuestionAnswers.put(target, value.toString())

                                                            if ("costPerUnit".equals(target)) {
                                                                dataset.userSetCost = Boolean.TRUE
                                                            } else if ("totalCost".equals(target)) {
                                                                dataset.userSetTotalCost = Boolean.TRUE
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Class propertyClass = GrailsClassUtils.getPropertyType(Dataset.class, target)

                                                    if (propertyClass) {
                                                        if (propertyClass.equals(Double.class)) {
                                                            if (value.isNumber() && value.toDouble() != 0D) {
                                                                DomainObjectUtil.callSetterByAttributeName(target, dataset, value.toDouble())
                                                            } else {
                                                                if ("quantity".equalsIgnoreCase(target) && !value.isNumber()) {
                                                                    dataset.importMapperPartialQuantification = true
                                                                    dataset.answerIds = [value]
                                                                } else {
                                                                    DomainObjectUtil.callSetterByAttributeName(target, dataset, 0D)
                                                                }
                                                                // errorMessageUtil.setErrorMessage(messageSource.getMessage("importMapper.non_numerical_quantities", null, "Non processable quantities (bad number or unit) found. Values set to zero.", LocaleContextHolder.getLocale()), true)
                                                            }
                                                        } else {
                                                            DomainObjectUtil.callSetterByAttributeName(target, dataset, value)
                                                        }
                                                    } else {
                                                        loggerUtil.warn(log, "Couldn't find propertyClass for dataset with target: ${target}")
                                                        flashService.setFadeWarningAlert("Couldn't find propertyClass for dataset with target: ${target}", true)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        def columnsRolled = importMapperReturn.iterationStatistic.columnsRolled
                        if (columnsRolled == 0 || columnsRolled < foo) {
                            importMapperReturn.iterationStatistic.columnsRolled = foo
                        }

                        if (!skipRow && oneCellValueFound) {
                            if (!questionMappingFound) {
                                if (targetQuestionRule) {
                                    if (question) {
                                        if (resourceCompatibleCount > 0) {
                                            dataset.queryId = targetQuestionRule.get("queryId")
                                            dataset.sectionId = targetQuestionRule.get("sectionId")
                                            dataset.questionId = targetQuestionRule.get("questionId")
                                        }
                                    }
                                } else {
                                    loggerUtil.error(log, "IMPORTMAPPER: Misconfiguration! No default target found for entityClass: ${parentEntity?.entityClass ?: "building"}")
                                    flashService.setErrorAlert("IMPORTMAPPER: Misconfiguration! No default target found for entityClass: ${parentEntity?.entityClass ?: "building"}", true)
                                }
                            }
                            dataset.dataForCollapser = allDataFieldsFromImport

                            if (noDisplayFields) {
                                Map<String, String> filteredFieldsFromImport = [:]
                                allDataFieldsFromImport?.each { String key, String value ->
                                    if (!noDisplayFields*.toUpperCase().contains(key.toUpperCase())) {
                                        filteredFieldsFromImport.put(key, value)
                                    }
                                }
                                dataset.allImportDisplayFields = [filteredFieldsFromImport]
                            } else {
                                dataset.allImportDisplayFields = [allDataFieldsFromImport]
                            }

                            if (dataset.quantity != null && !dataset.answerIds) {
                                dataset.answerIds = [FormatterUtil.doubleToString(dataset.quantity)]
                            }

                            if (trainAdaptiveRecognition) {
                                if (trainingData) {
                                    if ((ifcMaterialValue && !ifcMaterialValue.equalsIgnoreCase(trainingData.get("RESOURCEID"))) ||
                                            (oclIdValue && !oclIdValue.equalsIgnoreCase(trainingData.get(OCLID)))
                                    ) {
                                        if (ifcMaterialValue) {
                                            trainingData["RESOURCEID"] = ifcMaterialValue
                                        }
                                        if (oclIdValue) {
                                            trainingData[OCLID] = oclIdValue
                                        }
                                    }
                                }
                                dataset.trainingMatchData = matchingValues
                                dataset.trainingData = trainingData
                                dataset.ifcMaterialValue = ifcMaterialValue
                            }

                            if (dataset.importMapperThickness_m && !dataset.additionalQuestionAnswers?.get("thickness_mm")) {
                                if (dataset.importMapperThickness_m.replaceAll(",", ".").trim().isNumber()) {
                                    Double thicknessMinMM = dataset.importMapperThickness_m.replaceAll(",", ".").trim().toDouble()

                                    if (thicknessMinMM) {
                                        if (dataset.additionalQuestionAnswers) {
                                            dataset.additionalQuestionAnswers["thickness_mm"] = "${thicknessMinMM * 1000}"
                                        } else {
                                            dataset.additionalQuestionAnswers = ["thickness_mm": "${thicknessMinMM * 1000}"]
                                        }
                                    }
                                }
                            }
                            if (dataset.validate() && dataset.queryId && dataset.questionId && dataset.sectionId && (!dataset.importMapperPartialQuantification || dataset.answerIds)) {
                                importMapperReturn.okDatasets.add(dataset)
                            } else {
                                importMapperReturn.rejectedDatasets.add(dataset)
                                def errors = dataset.getErrors()?.getAllErrors()

                                if (errors) {
                                    loggerUtil.error(log, "Error in dataset: " + errors)
                                    flashService.setErrorAlert("Error in dataset: " + errors, true)
                                }
                            }
                        } else {
                            importMapperReturn.discardedDatasets.add(dataset)
                            importMapperReturn.skippedRows++
                        }
                    }
                }
            }
            log.info("Elapsed by mapper: ${System.currentTimeMillis() - now}")
            flashService.setFadeInfoAlert("Elapsed by mapper: ${System.currentTimeMillis() - now}", true)
        } else {
            String missingMandatoryDataError = ""
            if (ifcMaterialCellIndex == null) {
                missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column IFCMATERIAL<br/>"
            }

            if (quantityCellIndex == null) {
                missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column QUANTITY<br/>"
            }

            if (quantityTypeCellIndex == null) {
                missingMandatoryDataError = "${missingMandatoryDataError}Missing mandatory data column QTY_TYPE<br/>"
            }
            importMapperReturn.missingMandatoryDataError = missingMandatoryDataError
        }

        return importMapperReturn
    }

    /**
     * Get count of compatible resources for question
     * @param entity
     * @param indicator
     * @param question
     * @return
     */
    private Integer getCompatibleResourceCountForQuestion(Entity entity, Indicator indicator, Question question) {

        Integer resourceCompatibleCount = 0

        if (question) {
            Map<String, List<Object>> resourceFilterCriteria = question.getResourceFilterCriteria(indicator, entity)

            BasicDBObject filter = new BasicDBObject()
            filter.put("active", true)

            if (question.skipResourceTypes) {
                filter.put("resourceType", [$nin: question.skipResourceTypes])
                filter.put("resourceSubType", [$nin: question.skipResourceTypes])
            } else if (question.allowResourceTypes) {
                filter.put("\$or", [[resourceType: [$in: question.allowResourceTypes]], [resourceSubType: [$in: question.allowResourceTypes]]])
            }

            if (question.resourceGroups) {
                filter.put("resourceGroup", [$in: question.resourceGroups])
            }
            if (question.requiredEnvironmentDataSourceStandards && !question.requiredEnvironmentDataSourceStandards.isEmpty()) {
                filter.put("environmentDataSourceStandard", [$in: question.requiredEnvironmentDataSourceStandards])
            }
            if (question.filterResources && !question.filterResources.isEmpty()) {
                question.filterResources.each { String key, String value ->
                    filter[key] = value
                }
            }
            if (resourceFilterCriteria && !resourceFilterCriteria.isEmpty()) {
                resourceFilterCriteria.each { String resourceAttribute, List<Object> requiredValues ->
                    Map<String, List<Object>> inClause = [:]
                    inClause.put('$in', requiredValues)
                    filter[resourceAttribute] = inClause
                }
            }
            resourceCompatibleCount = Resource.collection.countDocuments(filter) as Integer
        }

        return resourceCompatibleCount
    }

    /**
     * Apply ClassificationQuestionTranslation to value
     * @param classificationQuestionTranslation
     * @param value
     * @return
     */
    private String applyClassificationQuestionTranslation(ClassificationQuestionTranslation classificationQuestionTranslation, String value) {

        for (ImportMapperTranslation translation in classificationQuestionTranslation?.translations) {
            for (String matchingValue in translation.matchingValues) {
                if (matchingValue.endsWith("*")) {
                    if (value.toUpperCase().startsWith(matchingValue.replaceAll("\\*", "").toUpperCase())) {
                        return translation.applyValue
                    }
                } else if (matchingValue.equalsIgnoreCase(value)) {
                    return translation.applyValue
                }
            }
        }

        return value
    }

    /**
     * Normalize value with parameters
     * @param value
     * @param replaceExtraWhitespaces - if true then replaces extra whitespaces
     * @param replaceUnderscore - if true then replaces all underscore characters with a space character
     * @param escapeHtml - if true then escapes HTML
     * @return
     */
    private String normalizeValue(String value, Boolean replaceExtraWhitespaces = false, Boolean replaceUnderscore = false, Boolean escapeHtml = true) {

        String resultValue = ""

        if (value) {
            List<String> regexToReplaceList = []
            if (replaceExtraWhitespaces) {
                regexToReplaceList << "\\s{2,}"
            }
            if (replaceUnderscore) {
                regexToReplaceList << "_"
            }
            String regexToReplace = regexToReplaceList?.join("|")

            resultValue = normalizeValueWithRegexp(value, regexToReplace, escapeHtml)
        }

        return resultValue
    }

    /**
     * Normalize value with parameters
     * @param value
     * @param regexToReplace - if true then replaces all characters satisfied the regex with a space character
     * @param escapeHtml - if true then escapes HTML
     * @return
     */
    private String normalizeValueWithRegexp(String value, String regexToReplace, Boolean escapeHtml = true) {

        String resultValue = ""

        if (value) {
            resultValue = value.trim()
            if (regexToReplace) {
                resultValue = resultValue?.replaceAll(regexToReplace, " ")
            }
            if (escapeHtml) {
                resultValue = HtmlUtils.htmlEscape(resultValue)
            }
        }

        return resultValue
    }

    private void importAdditionalQuestionsIntoDatasetFromRow(Dataset dataset, Row row, Map<Integer, String> headerCellValues,
                                                             ImportedByImportMapperReturn importMapperReturn,
                                                             Map<String, String> allDataFieldsFromImport,
                                                             List<Integer> skippedColumnIndexes,
                                                             Map<String, Boolean> additionalQuestionIdIsNumeric,
                                                             Set<String> additionalQuestionIds,
                                                             Question question, List dataMappingRules) {

        Iterator<Cell> cellIterator = row.iterator()
        int iteratedCellsCount = 0

        while (cellIterator.hasNext()) {
            iteratedCellsCount++

            Cell cell = cellIterator.next()
            Integer columnIndex = cell.getColumnIndex()

            if (skippedColumnIndexes && skippedColumnIndexes.contains(columnIndex)) {
                continue
            }

            String headerValue = headerCellValues.get(columnIndex)?.trim()

            if (headerValue) {
                String value = getCellValue(cell)

                allDataFieldsFromImport.put(headerValue.replace(".", ""), HtmlUtils.htmlUnescape(normalizeValue(value)))
                value = normalizeValue(value, true)

                if (!value) {
                    continue
                }

                ImportMapperDescriptiveDataRule importMapperDataRule = dataMappingRules.find { ImportMapperDescriptiveDataRule dataRule ->
                    dataRule.mappedHeadings.find { it.equalsIgnoreCase(headerValue) }
                }

                if (!importMapperDataRule) {
                    continue
                }

                String target = importMapperDataRule.target
                if (additionalQuestionIds.contains(target)) {

                    if (question?.additionalQuestionIds?.contains(target)) {

                        Boolean numeric = additionalQuestionIdIsNumeric.get(target)
                        if (!numeric || DomainObjectUtil.isNumericValue(value)) {
                            dataset.additionalQuestionAnswers = dataset.additionalQuestionAnswers ?: [:]
                            dataset.additionalQuestionAnswers.put(target, value.toString())
                            dataset.descriptiveDisplayData += [(headerValue): HtmlUtils.htmlUnescape(value.toString())]
                        }
                    } else {
                        String message = "Unrelated additional question: '${target}'"
                        dataset.importWarning = dataset.importWarning ? "${dataset.importWarning} and ${message}" : message
                    }
                }
            }
        }

        def columnsRolled = importMapperReturn.iterationStatistic.columnsRolled
        if (columnsRolled == 0 || columnsRolled < iteratedCellsCount) {
            importMapperReturn.iterationStatistic.columnsRolled = iteratedCellsCount
        }
    }

    private ImportedByImportMapperReturn importDescriptiveDataFromSheet(ImportMapper importMapper,
                                                                        ImportedByImportMapperReturn importMapperReturn,
                                                                        Sheet sheet, DataFormatter formatter,
                                                                        List<ImportMapperQuestionRule> questionMappingRules,
                                                                        Set<String> acceptedClasses, List<String> classIdentifyingFields,
                                                                        Map<String, Boolean> additionalQuestionIdIsNumeric, Set<String> additionalQuestionIds,
                                                                        List<Question> questions, User user, Boolean removeEmptyData,
                                                                        Boolean useOriginalClass) {

        int headerRowNo = sheet.getFirstRowNum()
        Row headerRow = sheet.getRow(headerRowNo)
        Iterator<Cell> headerCellIterator = headerRow.iterator()

        Integer classCellIndex
        Integer contentCellIndex

        Map<Integer, String> headerCellValues = [:]

        List<String> excludeColumnsFromImport = importMapper.excludeFromImport
        List<String> noDisplayFields = importMapper.noDisplayFields
        List<ImportMapperTranslation> questionMappingClassTranslations = importMapper.questionMappingClassTranslations
        List<ImportMapperDescriptiveDataRule> dataRules = importMapper.descriptiveDataMappingRules
        //List<String> skipQuestionMappingValues = importMapper.skipQuestionMappingValues*.toUpperCase() ?: []

        while (headerCellIterator.hasNext()) {
            Cell headerCell = headerCellIterator.next()
            String headerCellValue = null
            if (headerCell) {
                headerCellValue = formatter.formatCellValue(headerCell)
            }

            if (!headerCellValue?.startsWith("@") && (!excludeColumnsFromImport || !excludeColumnsFromImport*.toUpperCase().contains(headerCellValue?.toUpperCase()))) {
                headerCellValues.put(new Integer(headerCell.getColumnIndex()), headerCellValue)
                importMapperReturn.headers.add(headerCellValue?.trim())

                if (classIdentifyingFields.any { headerCellValue.trim()?.equalsIgnoreCase(it) }) {
                    classCellIndex = new Integer(headerCell.getColumnIndex())
                } else if (Const.DESCRIPTIVE_DATA_COLUMN_NAME.equalsIgnoreCase(headerCellValue.trim())) {
                    contentCellIndex = new Integer(headerCell.getColumnIndex())
                }
            }
        }

        if (classCellIndex == null || contentCellIndex == null) {
            return importMapperReturn
        }

        Iterator<Row> sheetRowIterator = sheet.iterator()
        long now = System.currentTimeMillis()
        while (sheetRowIterator.hasNext()) {
            Row row = sheetRowIterator.next()
            importMapperReturn.iterationStatistic.rowsRolled++

            if (row.getRowNum() > headerRowNo) {
                Cell classCell = row.getCell(classCellIndex)
                String classValue = getCellValue(classCell)
                String contentValue = getCellValue(row.getCell(contentCellIndex))

                if ((removeEmptyData && (!classValue || !contentValue)) ||
                        (!classValue && !contentValue)) {
                    Integer amount = importMapperReturn.emptyRows.get("emptyRows") ?: 0
                    amount++
                    importMapperReturn.emptyRows.put("emptyRows", amount)
                } else {
                    Dataset dataset = new Dataset()

                    dataset.usedUnitSystem = user?.unitSystem ? user.unitSystem :
                            UnitConversionUtil.UnitSystem.METRIC.value
                    dataset.manualId = new ObjectId().toString()

                    Map<String, String> allDataFieldsFromImport = [:]
                    dataset.allowMapping = Boolean.TRUE

                    importMapperReturn.iterationStatistic.totalIterated++

                    if (useOriginalClass) {
                        dataset.originalClass = classValue
                    } else {
                        dataset.persistedOriginalClass = classValue
                    }
                    dataset.trainingData = [CLASS: HtmlUtils.htmlUnescape(classValue ?: "")]
                    dataset.descriptiveDisplayData = dataset.trainingData

                    if (!acceptedClasses.contains(classValue?.toLowerCase())) {
                        dataset.importError = dataset.importError ?
                                dataset.importError + " and Data with unconventional classification: ${classValue}" :
                                "Data with unconventional classification: ${classValue}"
                        dataset.unconventionalClassification = Boolean.TRUE
                    }

                    handleImportMapperQuestionMappingClassTranslations(questionMappingClassTranslations, classCellIndex, classCell)


                    if (classValue && contentValue) {

                        allDataFieldsFromImport.put(headerCellValues.get(classCellIndex), HtmlUtils.htmlUnescape(normalizeValue(classValue)))
                        allDataFieldsFromImport.put(headerCellValues.get(contentCellIndex), HtmlUtils.htmlUnescape(normalizeValue(contentValue)))

                        classValue = normalizeValue(classValue, true)
                        contentValue = normalizeValue(contentValue, true)

                        ImportMapperDescriptiveQuestionRule descriptiveQuestionRule = findQuestionRuleFromListByMatchingValue(questionMappingRules, classValue)
                        if (descriptiveQuestionRule) {
                            dataset.queryId = descriptiveQuestionRule.target?.get("queryId")
                            dataset.sectionId = descriptiveQuestionRule.target?.get("sectionId")
                            dataset.questionId = descriptiveQuestionRule.target?.get("questionId")

                            Question question = questions.find {
                                it.queryId == dataset.queryId && it.sectionId == dataset.sectionId && it.questionId == dataset.questionId
                            }

                            Integer maxLengthTrim = descriptiveQuestionRule.maxLengthTrim
                            if (contentValue.length() > maxLengthTrim) {
                                dataset.importWarning = "Field has been trimmed to the maximum lenght: ${maxLengthTrim} characters"
                                importMapperReturn.truncatedFields.putAt(classValue, maxLengthTrim)
                                contentValue = contentValue.take(maxLengthTrim)
                            }
                            dataset.descriptiveDisplayData += [(Const.DESCRIPTIVE_DATA_COLUMN_NAME): HtmlUtils.htmlUnescape(contentValue ?: "")]

                            Boolean isNumeric = question?.onlyNumeric
                            if (!isNumeric || DomainObjectUtil.isNumericValue(contentValue)) {
                                dataset.answerIds = [contentValue]
                            } else {
                                String numericError = "Value is not numeric: '${contentValue}'"
                                dataset.importError = dataset.importError ? "${dataset.importError} and ${numericError}" : numericError
                            }

                            importAdditionalQuestionsIntoDatasetFromRow(dataset, row, headerCellValues,
                                    importMapperReturn, allDataFieldsFromImport, [classCellIndex, contentCellIndex],
                                    additionalQuestionIdIsNumeric, additionalQuestionIds, question, dataRules)
                        }

                        dataset.dataForCollapser = allDataFieldsFromImport

                        if (noDisplayFields) {
                            Map<String, String> filteredFieldsFromImport = [:]
                            allDataFieldsFromImport?.each { String key, String value ->
                                if (!noDisplayFields*.toUpperCase().contains(key.toUpperCase())) {
                                    filteredFieldsFromImport.put(key, value)
                                }
                            }
                            dataset.allImportDisplayFields = [filteredFieldsFromImport]
                        } else {
                            dataset.allImportDisplayFields = [allDataFieldsFromImport]
                        }

                        if (dataset.validate() && dataset.queryId && dataset.questionId && dataset.sectionId) {
                            importMapperReturn.okDatasets.add(dataset)
                        } else {
                            importMapperReturn.rejectedDatasets.add(dataset)
                            def errors = dataset.getErrors()?.getAllErrors()

                            if (errors) {
                                loggerUtil.error(log, "Error in dataset: " + errors)
                                flashService.setErrorAlert("Error in dataset: " + errors, true)
                            }
                        }
                    } else {
                        importMapperReturn.discardedDatasets.add(dataset)
                        importMapperReturn.skippedRows++
                    }
                }
            }
        }

        log.info("Elapsed by mapper: ${System.currentTimeMillis() - now}")
        flashService.setFadeInfoAlert("Elapsed by mapper: ${System.currentTimeMillis() - now}", true)

        return importMapperReturn
    }

    def recognizeDataset(Dataset dataset, List<RecognitionRuleset> systemRulesets, List<String> requiredFieldsForRecognition,
                         String applicationId, ImportMapper importMapper,
                         Indicator indicator, String entityCountry, Boolean api = Boolean.FALSE, Entity child = null,
                         Account account = null, String localizedRecognizedFromHeading = null, List<String> resourceIdsInDataList = null,
                         boolean isUncapped = false, Document importMapperTrainingDataForDataset = null, Document importMapperOCLIDTrainingData = null, Boolean isRseeImport = Boolean.FALSE) {

        if (!isResourceDataset(dataset)) {
            return
        }

        if (dataset && systemRulesets && requiredFieldsForRecognition && applicationId && importMapper && indicator) {
            String accountId = account?.id?.toString()
            String organization = account?.companyName
            String ifcMaterialValue = dataset.ifcMaterialValue
            String ifcMaterialValueOrg = dataset.ifcMaterialValueOrg
            List<String> units = unitConversionUtil.getUnitAndItsEquivalent(dataset.userGivenUnit)
            Question question = questionService.getQuestion(dataset.queryId, dataset.questionId)
            Map<String, List<Object>> resourceFilterCriteria
            BasicDBObject filter = new BasicDBObject()
            Resource resourceData = datasetService.getResource(dataset)
            if (question) {
                resourceFilterCriteria = question?.getResourceFilterCriteria(indicator, child)
                Map<String, Object> mainFilter = [:]
                if (question.skipResourceTypes) {
                    mainFilter.put("resourceType", [$nin: question.skipResourceTypes])
                    mainFilter.put("resourceSubType", [$nin: question.skipResourceTypes])
                } else if (question.allowResourceTypes) {
                    mainFilter.put("\$or", [[resourceType: [$in: question.allowResourceTypes]], [resourceSubType: [$in: question.allowResourceTypes]]])
                }

                if (question.resourceGroups) {
                    mainFilter.put("resourceGroup", [$in: question.resourceGroups])
                }
                if (question.requiredEnvironmentDataSourceStandards && !question.requiredEnvironmentDataSourceStandards.isEmpty()) {
                    mainFilter.put("environmentDataSourceStandard", [$in: question.requiredEnvironmentDataSourceStandards])
                }
                if (question.filterResources && !question.filterResources.isEmpty()) {
                    question.filterResources.each { String key, String value ->
                        mainFilter[key] = value
                    }
                }
                if (resourceFilterCriteria && !resourceFilterCriteria.isEmpty()) {
                    resourceFilterCriteria.each { String resourceAttribute, List<Object> requiredValues ->
                        mainFilter[resourceAttribute] = [$in: requiredValues]
                    }
                }
                filter = new BasicDBObject(mainFilter)
                filter.put("active", true)

            }

            String fullName = dataset.allImportDisplayFields?.collect({ it?.get("FULL_NAME") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("FULL_NAME") : null
            String resourceDatabaseId = dataset.allImportDisplayFields?.collect({ it?.get(OCLID) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(OCLID) : null
            String staticFullName = dataset.allImportDisplayFields?.collect({ it?.get("STATICFULLNAME") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("STATICFULLNAME") : null
            String epdNumber = dataset.allImportDisplayFields?.collect({ it?.get("EPDNUMBER") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("EPDNUMBER") : null

            if (!ifcMaterialValueOrg) {
                ifcMaterialValueOrg = dataset.allImportDisplayFields?.collect({ it?.get(MATERIAL) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(MATERIAL) : null
            }

            if (!dataset.resourceId && importMapperOCLIDTrainingData) {
                enrichWithTrainingData(importMapperOCLIDTrainingData, dataset,
                        indicator, child, entityCountry, resourceIdsInDataList,
                        isUncapped, units, question)
            }

            if (!dataset.resourceId) {
                Map returnable = optimiResourceService.getAllResourcesAsDocumentsForRecognitionInOrder(
                        resourceDatabaseId, fullName, staticFullName, ifcMaterialValueOrg, epdNumber, accountId, filter, isRseeImport)
                List<Document> resources = returnable.get("resources")

                Integer weight = returnable.get("weight")
                if (resources) {
                    for (Document resource in resources) {
                        // 16075: the resources list already passed resourceFilterCriteria from above, second check for organisationResourceFilterCriteria,
                        // if the resource does not belong to the organisationResourceFilterCriteria then it passed. If the resource fit in organisationResourceFilterCriteria
                        // then check if license is capped or not, if not capped then pass all resources, if capped then check if resource exist in organisation data list
                        if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(null, dataset.queryId, indicator, resource, child, resourceIdsInDataList, isUncapped, units)) {
                            dataset.resourceId = resource.resourceId?.toString()
                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put("profileId", resource.profileId?.toString())
                            } else {
                                dataset.additionalQuestionAnswers = [profileId: resource.profileId?.toString()]
                            }
                            dataset.profileId = resource.profileId?.toString()
                            dataset.systemTrained = Boolean.TRUE
                            dataset.recognizedFrom = importMapperTrainingDataService.getWeightFlavor(weight)
                            dataset.recognizedFromShort = importMapperTrainingDataService.getWeightFlavor(weight, Boolean.FALSE, Boolean.TRUE, [entityCountry])
                            dataset.suggestedResourceMatch = Boolean.TRUE
                            break
                        }
                    }
                }
            }

            if (!dataset.resourceId) {
                enrichWithTrainingData(importMapperTrainingDataForDataset, dataset,
                        indicator, child, entityCountry, resourceIdsInDataList,
                        isUncapped, units, question)
            }

            if ((!dataset.resourceId || dataset.weight == 0) && ifcMaterialValue) {
                if (systemRulesets) {
                    systemRulesets = systemRulesets.sort({ it.accountId != accountId })
                    SystemMatch wildCardMatch
                    rulesetloop:
                    for (RecognitionRuleset recognitionRuleset in systemRulesets) {
                        if (recognitionRuleset && ((accountId && recognitionRuleset.accountId == accountId) || !recognitionRuleset.accountId)) {
                            for (SystemTrainingDataSet systemTrainingDataSet in recognitionRuleset.systemTrainingDatas) {
                                for (SystemMatch systemMatch in systemTrainingDataSet.systemMatches) {
                                    String sysTrainedData = systemMatch.systemTrainingData
                                    String resourceId = systemMatch.resourceId
                                    String profileId = systemMatch.profileId

                                    if (resourceId) {
                                        if (sysTrainedData.endsWith("*")) {
                                            if (ifcMaterialValue.toUpperCase().startsWith(sysTrainedData.replaceAll("\\*", "").toUpperCase())) {
                                                wildCardMatch = systemMatch
                                            }
                                        } else if (sysTrainedData.equalsIgnoreCase(ifcMaterialValue)) {
                                            dataset.resourceId = resourceId

                                            if (dataset.additionalQuestionAnswers) {
                                                dataset.additionalQuestionAnswers.put("profileId", profileId)
                                            } else {
                                                dataset.additionalQuestionAnswers = [profileId: profileId]
                                            }
                                            dataset.profileId = profileId
                                            resourceData = datasetService.getResource(dataset)
                                            if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(resourceData, dataset.queryId, indicator, null, child, resourceIdsInDataList, isUncapped, units, question)) {
                                                dataset.suggestedResourceMatch = Boolean.TRUE
                                                dataset.systemTrained = Boolean.TRUE
                                                dataset.recognizedFrom = importMapperTrainingDataService.getWeightFlavor(null, Boolean.TRUE)
                                                dataset.recognizedFromShort = importMapperTrainingDataService.getWeightFlavor(null, Boolean.TRUE, Boolean.TRUE, [entityCountry])

                                                break rulesetloop
                                            } else {
                                                dataset.resourceId = null
                                                dataset.profileId = null
                                                dataset.additionalQuestionAnswers?.remove("profileId")
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                    if (wildCardMatch && !dataset.resourceId) {

                        dataset.resourceId = wildCardMatch.resourceId

                        if (dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers.put("profileId", wildCardMatch.profileId)
                        } else {
                            dataset.additionalQuestionAnswers = [profileId: wildCardMatch.profileId]
                        }
                        dataset.profileId = wildCardMatch.profileId
                        resourceData = datasetService.getResource(dataset)
                        if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(resourceData, dataset.queryId, indicator, null, child, resourceIdsInDataList, isUncapped, units, question)) {
                            dataset.suggestedResourceMatch = Boolean.TRUE
                            dataset.systemTrained = Boolean.TRUE
                            dataset.recognizedFrom = importMapperTrainingDataService.getWeightFlavor(null, Boolean.TRUE)
                            dataset.recognizedFromShort = importMapperTrainingDataService.getWeightFlavor(null, Boolean.TRUE, Boolean.TRUE, [entityCountry])
                        } else {
                            dataset.resourceId = null
                            dataset.profileId = null
                            dataset.additionalQuestionAnswers?.remove("profileId")
                        }
                    }
                }
            }

            requiredFieldsForRecognition.find { String requiredField ->
                if (!dataset.trainingData?.get(requiredField)) {
                    dataset.allowMapping = Boolean.FALSE
                    return true
                }
            }

            if (!dataset.suggestedResourceMatch) {
                if (dataset.resourceId && !resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(resourceData, dataset.queryId, indicator, null, child, resourceIdsInDataList, isUncapped, units, question)) {
                    dataset.resourceId = null
                    dataset.profileId = null
                    dataset.additionalQuestionAnswers?.remove("profileId")
                    dataset.recognizedFrom = null
                    dataset.recognizedFromShort = null
                    dataset.systemTrained = null
                }
            }

            if (!dataset.uniqueConstructionIdentifier && resourceData?.construction) {
                String identifier = UUID.randomUUID().toString() + resourceData?.constructionId
                dataset.uniqueConstructionIdentifier = identifier
            }

            if (!api && dataset.allImportDisplayFields) {

                if (localizedRecognizedFromHeading) {
                    if (dataset.recognizedFrom) {
                        dataset.allImportDisplayFields.each {
                            Map<String, String> clone = it.clone()
                            it.clear()
                            it.put((localizedRecognizedFromHeading), dataset.recognizedFrom)
                            it.putAll(clone)
                        }
                    } else {
                        dataset.allImportDisplayFields.each {
                            Map<String, String> clone = it.clone()
                            it.clear()
                            it.put((localizedRecognizedFromHeading), importMapperTrainingDataService
                                    .getWeightFlavor(3, Boolean.FALSE))
                            it.putAll(clone)
                        }
                    }
                }

                fillDatasetImportFieldsId(dataset)
            }
        }
    }

    void fillDatasetImportFieldsId(Dataset dataset) {
        DatasetImportFields datasetImportFields

        if (!dataset.datasetImportFieldsId) {
            datasetImportFields = new DatasetImportFields()
            datasetImportFields.importFields = dataset.allImportDisplayFields
            datasetImportFields = datasetImportFields.save(flush: true)
            dataset.datasetImportFieldsId = datasetImportFields.id.toString()
        } else {
            datasetImportFields = datasetService.getDatasetImportFieldsById(dataset.datasetImportFieldsId)
            if (datasetImportFields) {
                datasetImportFields.importFields = dataset.allImportDisplayFields
                datasetImportFields.merge(flush: true)
            }
        }
    }

    private void enrichWithTrainingData(Document importMapperTrainingData, Dataset dataset,
                                        Indicator indicator, Entity child, String entityCountry, List<String> resourceIdsInDataList,
                                        Boolean isUncapped, List<String> units, Question question) {
        if (importMapperTrainingData) {
            String profileId = importMapperTrainingData.get("profileId")
            String resourceId = importMapperTrainingData.get("resourceId")
            Resource resource = (Resource) importMapperTrainingData.get("resource")
            dataset.resourceId = resourceId
            dataset.profileId = profileId
            if (resource) {
                dataset.setCalculationResource(resource)
            }
            if (dataset.additionalQuestionAnswers) {
                dataset.additionalQuestionAnswers.put("profileId", profileId)
            } else {
                dataset.additionalQuestionAnswers = [profileId: profileId]
            }
            if (resourceFilterCriteriaUtil.resourceOkAgainstAllFilterCriteria(
                    datasetService.getResource(dataset), dataset.queryId, indicator, null, child, resourceIdsInDataList, isUncapped, units, question)
            ) {
                dataset.suggestedResourceMatch = Boolean.TRUE
                Integer weight = Integer.valueOf((String) importMapperTrainingData.get("weight"))
                dataset.weight = weight
                dataset.recognizedFrom = importMapperTrainingDataService.getWeightFlavor(weight)
                dataset.recognizedFromShort = importMapperTrainingDataService.getWeightFlavor(weight, Boolean.FALSE, Boolean.TRUE, [entityCountry])
            } else {
                dataset.resourceId = null
                dataset.profileId = null
                dataset.additionalQuestionAnswers?.remove("profileId")
            }
        }
    }

    Workbook generateExcel(List<Dataset> datasets, List<String> headers, ImportMapper importMapper) {
        Workbook wb

        if (datasets && headers) {

            wb = new HSSFWorkbook()
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            Sheet sheet = wb.createSheet()
            Row headerRow = sheet.createRow(0)
            Integer i = 0
            Integer numberOfHeadings = headers.size()

            headers.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }
            i = 1

            datasets.each { Dataset dataset ->
                Row datasetRow = sheet.createRow(i)
                int j = 0

                headers.each { String header ->
                    Cell cell = datasetRow.createCell(j)
                    String value

                    if ("quantity".equalsIgnoreCase(header) || "volume".equalsIgnoreCase(header)) {
                        value = dataset.quantity?.toString()
                    } else if ("qty_type".equalsIgnoreCase(header) || (importMapper?.unitIdentifyingFields && importMapper?.unitIdentifyingFields*.toUpperCase()?.contains(header.toUpperCase()))) {
                        value = dataset.userGivenUnit?.toString()
                    } else if ("AREA_M2".equalsIgnoreCase(header) && dataset.area_m2) {
                        value = dataset.area_m2
                    } else if ("VOLUME_M3".equalsIgnoreCase(header) && dataset.volume_m3) {
                        value = dataset.volume_m3
                    } else if ("MASS_KG".equalsIgnoreCase(header) && dataset.mass_kg) {
                        value = dataset.mass_kg
                    } else if (dataset.dataForCollapser?.get(header)) {
                        value = dataset.dataForCollapser.get(header)?.toString()
                    } else if (dataset.importDisplayFields?.get(header)) {
                        value = dataset.importDisplayFields.get(header)?.toString()
                    } else if (dataset.additionalQuestionAnswers?.get(header.toLowerCase())) {
                        value = dataset.additionalQuestionAnswers.get(header.toLowerCase())?.toString()
                    } else {
                        value = ""
                    }

                    if (value?.isNumber()) {
                        cell.setCellType(CellType.NUMERIC)

                        if (value.isInteger()) {
                            cell.setCellValue(Integer.valueOf(value))
                        } else if (value.isDouble()) {
                            cell.setCellValue(Double.valueOf(value))
                        }
                    } else {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(HtmlUtils.htmlUnescape(value ?: ""))
                    }
                    j++
                }
                i++
            }

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }
        }
        return wb
    }

    Workbook injectBimModelWarningsToExcel(List<BimModelWarning> bimModelWarnings, List<String> bimHeaders, List<Dataset> datasets, List<String> datasetHeaders, String detailsKey, Workbook wb, List<Dataset> ifcDatasets) {
        if (wb && bimModelWarnings && bimHeaders && datasetHeaders && datasets) {
            Date timeStamp = new Date()
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss")
            String sheetName = "Issues-${dateFormat.format(timeStamp)}"
            Sheet sheet = wb.createSheet(sheetName)
            Row headerRow = sheet.createRow(0)
            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            Integer i = 0
            Integer numberOfHeadings = bimHeaders.size()

            bimHeaders.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }
            i = 1

            bimModelWarnings.each { BimModelWarning warning ->
                Row warningRow = sheet.createRow(i)
                int j = 0

                bimHeaders.each { String header ->
                    Cell cell = warningRow.createCell(j)
                    String value
                    if ("Type".equalsIgnoreCase(header)) {
                        value = warning.status
                    } else if ("issue".equalsIgnoreCase(header)) {
                        value = warning.issueClass?.toLowerCase()?.capitalize()
                    } else if ("Explanation of issue".equalsIgnoreCase(header)) {
                        value = warning.helpText
                    } else if ("% in model".equalsIgnoreCase(header)) {
                        value = warning.share
                    } else if ("# in model".equalsIgnoreCase(header)) {
                        value = warning.amount
                    } else if ("Recommended action".equalsIgnoreCase(header)) {
                        value = warning.recommendedAction
                    } else {
                        value = ""
                    }

                    if (value?.isNumber()) {
                        cell.setCellType(CellType.NUMERIC)

                        if (value.isInteger()) {
                            cell.setCellValue(Integer.valueOf(value))
                        } else if (value.isDouble()) {
                            cell.setCellValue(Double.valueOf(value))
                        }
                    } else {
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(HtmlUtils.htmlUnescape(value ?: ""))
                    }
                    j++
                }
                i++
            }

            //fix 8178
            bimModelWarnings = bimModelWarnings?.sort({ it.priority })

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }

            List<Dataset> datasetsWithProblems = []
            List<Dataset> discardedDatasets = []
            List<Dataset> filteredDatasets = []
            List<Dataset> ambiguousDatasets = []
            List<Dataset> compositeDatasets = []

            List<Dataset> tooHighThickness = []
            List<Dataset> tooLowThickness = []
            List<Dataset> zeroThickness = []

            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset d : datasets) {
                if (d.discarded) {
                    discardedDatasets.add(d)
                    datasetsWithProblems.add(d)
                } else if (d.filtered) {
                    filteredDatasets.add(d)
                    datasetsWithProblems.add(d)
                } else {
                    Resource r = resourceCache.getResource(d)
                    ResourceType rt = r?.resourceTypeObject

                    //Thickness
                    Double thickness = datasetService.getThickness(d.additionalQuestionAnswers)

                    if (!d.resourceId && !ifcDatasets?.find({ it.originalDatasetsManualIds?.contains(d.manualId) })?.resourceId) {
                        ambiguousDatasets.add(d)
                        datasetsWithProblems.add(d)
                    } else if (d.importMapperCompositeMaterial) {
                        compositeDatasets.add(d)
                        datasetsWithProblems.add(d)
                    } else if (rt && thickness && thickness > rt.maxThickness_mm) {
                        tooHighThickness.add(d)
                        datasetsWithProblems.add(d)
                    } else if (rt && thickness && thickness < rt.minThickness_mm) {
                        tooLowThickness.add(d)
                        datasetsWithProblems.add(d)
                    } else if (rt && thickness && thickness == 0.0) {
                        zeroThickness.add(d)
                        datasetsWithProblems.add(d)
                    } else if (d.importError || d.importWarning || !d.quantity || !d.userGivenUnit) {
                        datasetsWithProblems.add(d)
                    }
                }
            }

            if (datasetsWithProblems && !datasetsWithProblems.isEmpty()) {
                CellStyle styleForErrorTypes = wb.createCellStyle()
                CellStyle styleForWarnTypes = wb.createCellStyle()
                styleForErrorTypes.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.index)
                styleForErrorTypes.setFillPattern(FillPatternType.SOLID_FOREGROUND)
                styleForWarnTypes.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_ORANGE.index)
                styleForWarnTypes.setFillPattern(FillPatternType.SOLID_FOREGROUND)

                i = 2 + i
                Row secondHeaderRow = sheet.createRow(i)
                Integer y = 0

                datasetHeaders.each { String header ->
                    Cell headerCell = secondHeaderRow.createCell(y)
                    headerCell.setCellStyle(cs)
                    headerCell.setCellType(CellType.STRING)
                    headerCell.setCellValue(header)
                    y++
                }
                i = i + 1

                resourceCache = ResourceCache.init(datasetsWithProblems)
                for (Dataset dataset : datasetsWithProblems) {
                    Dataset combinedDataset = ifcDatasets?.find({ it.originalDatasetsManualIds?.contains(dataset.manualId) })
                    Row datasetRow = sheet.createRow(i)
                    int j = 0

                    datasetHeaders.each { String header ->
                        Cell cell = datasetRow.createCell(j)
                        String value

                        if ("material".equalsIgnoreCase(header)) {
                            value = combinedDataset ? combinedDataset.importDisplayFields?.get(detailsKey) : dataset.importDisplayFields?.get(detailsKey)
                        } else if ("Resource name".equalsIgnoreCase(header)) {
                            value = combinedDataset ? optimiResourceService.getLocalizedName(resourceCache.getResource(combinedDataset)) ?: "no name found" :
                                    optimiResourceService.getLocalizedName(resourceCache.getResource(dataset)) ?: "no name found"
                        } else if ("Quantity".equalsIgnoreCase(header)) {
                            value = dataset?.quantity?.round(2)?.toString() ?: ""
                            if (dataset.userGivenUnit != null) {
                                value = value + " / ${dataset.userGivenUnit}"
                            }
                            if (dataset.additionalQuestionAnswers?.get("thickness_mm")) {
                                value = value + " / ${dataset.additionalQuestionAnswers.get("thickness_mm")} mm"
                            }
                        } else if ("CLASS".equalsIgnoreCase(header)) {
                            value = dataset.persistedOriginalClass
                        } else if ("Share".equalsIgnoreCase(header) && dataset.percentageOfTotal) {
                            value = dataset.percentageOfTotal?.round(2)
                        } else if ("note".equalsIgnoreCase(header)) {
                            value = dataset.additionalQuestionAnswers?.get("comment")
                        } else if ("type".equalsIgnoreCase(header)) {
                            BimModelWarning warning = bimModelWarnings?.find({ it.datasetIds?.contains(dataset.manualId) })

                            if (warning) {
                                value = "${warning.status}"

                                if (warning.status?.equalsIgnoreCase("error")) {
                                    cell.setCellStyle(styleForErrorTypes)
                                } else if (warning.status?.equalsIgnoreCase("warn")) {
                                    cell.setCellStyle(styleForWarnTypes)
                                }
                            }

                        } else if ("Issues".equalsIgnoreCase(header)) {
                            if (discardedDatasets.find({ it.manualId.equals(dataset.manualId) })) {
                                value = "Objects signifying empty, air or comparable value"
                            } else if (filteredDatasets.find({ it.manualId.equals(dataset.manualId) })) {
                                value = "Not relevant for chosen LCA tool"
                            } else {
                                if (!dataset.resourceId && dataset.quantity && dataset.userGivenUnit) {
                                    if (!dataset.allowMapping) {
                                        value = value ? value + " and Generic definitions" : "Generic definitions"
                                    } else {
                                        value = value ? value + " and Unidentified material" : "Unidentified material"
                                    }
                                }
                                if (!dataset.quantity || !dataset.userGivenUnit) {
                                    value = value ? value + " and No quantity or unrecognized unit" : "No quantity or unrecognized unit"
                                }
                                if (dataset.importMapperCompositeMaterial) {
                                    value = value ? value + " and Composite material" : "Composite material"
                                }

                                if (tooHighThickness.find({ it.manualId.equals(dataset.manualId) })) {
                                    value = value ? value + " and Implausibly thick object for this material type" : "Implausibly thick object for this material type"
                                }
                                if (tooLowThickness.find({ it.manualId.equals(dataset.manualId) })) {
                                    value = value ? value + " and Implausibly thin object for this material type" : "Implausibly thin object for this material type"
                                }
                                if (zeroThickness.find({ it.manualId.equals(dataset.manualId) })) {
                                    value = value ? value + " and Material has no thickness even though required by material type" : "Material has no thickness even though required by material type"

                                }
                                if (dataset.importWarning) {
                                    value = value ? value + " and ${dataset.importWarning}" : "${dataset.importWarning}"
                                }
                                if (dataset.importError) {
                                    value = value ? value + " and ${dataset.importError}" : "${dataset.importError}"
                                }
                            }
                        } else {
                            value = ""
                        }

                        if (value?.isNumber()) {
                            cell.setCellType(CellType.NUMERIC)

                            if (value.isInteger()) {
                                cell.setCellValue(Integer.valueOf(value))
                            } else if (value.isDouble()) {
                                cell.setCellValue(Double.valueOf(value))
                            }
                        } else {
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(HtmlUtils.htmlUnescape(value ?: ""))
                        }
                        j++
                    }
                    i++
                }
                wb.setSheetOrder(sheetName, 0)
                try {
                    wb.setSheetName(1, "Original Data${dateFormat.format(timeStamp)}")
                } catch (FormulaParseException e) {
                    // Task 8669
                    // Dont rename the original sheet since it contains formulas that refer a sheet that doesnt exists
                    // and Apache POI throws FormulaParseException
                }
                wb.setActiveSheet(0)
            }
        }
        return wb
    }

    Workbook generateExcelForResults(List<Dataset> ifcDatasets, List<CalculationResult> calculationResults, Entity tempEntity, Indicator indicator, User user, HttpSession session,
                                     Boolean generateResultsSheet = Boolean.TRUE, List<Dataset> failedToRecognizeDatasets = null, ImportMapper importMapper = null, String thicknessHeading = null) {
        Workbook wb

        if ((calculationResults || ifcDatasets) && indicator) {
            wb = new HSSFWorkbook()
            IndicatorReport indicatorReport = indicator.report
            List<CalculationRule> allCalculationRules = indicator.getResolveCalculationRules(null)
            List<ResultCategory> allResultCategories = indicator.getResolveResultCategories(null)

            if (indicatorReport && generateResultsSheet) {
                List<CalculationRule> calculationRules = []
                List<ResultCategory> resultCategories = []

                indicatorReportService.getReportItemsAsReportItemObjects(null, indicatorReport.reportItems)?.each { IndicatorReportItem reportItem ->
                    if ("table".equals(reportItem.type)) {
                        if (reportItem.rules) {
                            reportItem.rules.each { String rule ->
                                CalculationRule calculationRule = allCalculationRules?.find({
                                    rule.equals(it.calculationRuleId)
                                })

                                if (!calculationRule) {
                                    calculationRule = allCalculationRules?.find({
                                        rule.equals(it.calculationRule)
                                    })
                                }

                                if (calculationRule) {
                                    calculationRules.add(calculationRule)
                                }
                            }
                        } else {
                            calculationRules = allCalculationRules
                        }

                        if (reportItem.categories && !reportItem.categories.isEmpty()) {
                            reportItem.categories.each { String category ->
                                ResultCategory resultCategory = allResultCategories?.find({
                                    category.equals(it.resultCategoryId)
                                })

                                if (!resultCategory) {
                                    resultCategory = allResultCategories?.find({
                                        category.equals(it.resultCategory)
                                    })
                                }

                                if (resultCategory) {
                                    resultCategories.add(resultCategory)
                                }
                            }
                        } else {
                            resultCategories = allResultCategories
                        }
                    }
                }

                if (calculationRules && !calculationRules.isEmpty() && resultCategories && !resultCategories.isEmpty()) {
                    CellStyle cs = wb.createCellStyle()
                    Font f = wb.createFont()
                    f.setBold(Boolean.TRUE)
                    cs.setFont(f)
                    cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
                    cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
                    Sheet sheet = wb.createSheet()
                    wb.setSheetName(wb.getSheetIndex(sheet), "results")
                    Row headerRow = sheet.createRow(0)
                    Integer i = 0
                    Integer numberOfHeadings = 2 + calculationRules.size()

                    Cell headerCellResultCategory = headerRow.createCell(i)
                    headerCellResultCategory.setCellType(CellType.STRING)
                    headerCellResultCategory.setCellStyle(cs)
                    headerCellResultCategory.setCellValue("ResultCategory")
                    i++
                    Cell headerCellSector = headerRow.createCell(i)
                    headerCellSector.setCellType(CellType.STRING)
                    headerCellSector.setCellStyle(cs)
                    headerCellSector.setCellValue("Sector")
                    i++

                    calculationRules.each { CalculationRule rule ->
                        Cell headerCell = headerRow.createCell(i)
                        headerCell.setCellStyle(cs)
                        headerCell.setCellType(CellType.STRING)
                        headerCell.setCellValue("${rule?.localizedShortName ? rule?.localizedShortName : rule?.localizedName} ${calculationRuleService.getLocalizedUnit(rule, indicator)}")
                        i++
                    }
                    i = 1

                    resultCategories.each { ResultCategory category ->
                        Integer j = 0
                        Row resultCategoryRow = sheet.createRow(i)

                        Cell resultCategory = resultCategoryRow.createCell(j)
                        resultCategory.setCellType(CellType.STRING)
                        resultCategory.setCellValue(category.resultCategory)
                        j++
                        Cell sector = resultCategoryRow.createCell(j)
                        sector.setCellType(CellType.STRING)
                        sector.setCellValue(category.localizedName)
                        j++

                        calculationRules.each { CalculationRule rule ->
                            Cell calculationResult = resultCategoryRow.createCell(j)
                            calculationResult.setCellType(CellType.STRING)

                            Double score = calculationResults?.find({
                                category.resultCategoryId?.equals(it?.resultCategoryId) &&
                                        rule.calculationRuleId.equals(it.calculationRuleId)
                            })?.result

                            String formattedScore = resultFormattingResolver.formatByResultFormatting(user, indicator, null, score, session, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE)
                            calculationResult.setCellValue(formattedScore)
                            j++
                        }

                        i++
                    }

                    if (tempEntity.calculationTotalResults) {
                        Integer j = 1
                        CellStyle totalRowStyle = wb.createCellStyle()
                        totalRowStyle.setFont(f)
                        totalRowStyle.setBorderTop(BorderStyle.MEDIUM)

                        Row totalRow = sheet.createRow(i)
                        Cell totalCell = totalRow.createCell(j)
                        totalCell.setCellType(CellType.STRING)
                        totalCell.setCellValue("Total")
                        totalCell.setCellStyle(totalRowStyle)
                        j++

                        calculationRules.each { CalculationRule calculationRule ->
                            Cell totalValue = totalRow.createCell(j)
                            totalValue.setCellStyle(totalRowStyle)
                            totalValue.setCellType(CellType.STRING)

                            Double total = tempEntity.calculationTotalResults?.find({
                                indicator.indicatorId.equals(it.indicatorId)
                            })?.totalByCalculationRule?.get(calculationRule.calculationRuleId)

                            String formattedTotal = resultFormattingResolver.formatByResultFormatting(user, indicator, null, total, session, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE)
                            totalValue.setCellValue(formattedTotal)
                            j++
                        }
                    }

                    numberOfHeadings.times {
                        sheet.autoSizeColumn(it)
                    }
                }
            }

            Boolean apiExcelAllowed = Boolean.FALSE
            if (user) {
                List<License> licenses = userService.getUsableAndManagedLicenses(user)
                Feature apiSampleExcel = featureService.getFeatureByFeatureId(Feature.API_SAMPLE_EXCEL)

                if (licenses && !licenses.isEmpty() && licenses.find({
                    it.licensedFeatureIds.contains(apiSampleExcel?.id?.toString())
                })) {
                    apiExcelAllowed = Boolean.TRUE
                }
            }

            if (!generateResultsSheet) {
                apiExcelAllowed = Boolean.TRUE
            }

            if (ifcDatasets && tempEntity && indicator && apiExcelAllowed) {
                ResultCategory resultCategory = allResultCategories?.find({
                    it.resultCategoryId.contains("A1-A3")
                })
                CalculationRule calculationRule = allCalculationRules?.find({
                    it.calculationRuleId.contains("GWP")
                })

                if (resultCategory && calculationRule) {
                    CellStyle cs = wb.createCellStyle()
                    Font f = wb.createFont()
                    f.setBold(Boolean.TRUE)
                    cs.setFont(f)
                    cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
                    cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
                    Sheet sheet = wb.createSheet()
                    CellStyle ignored = wb.createCellStyle()
                    Font ignoredFont = wb.createFont()
                    ignoredFont.setColor(HSSFColor.HSSFColorPredefined.RED.index)
                    ignored.setFont(ignoredFont)
                    wb.setSheetName(wb.getSheetIndex(sheet), "response")
                    Row headerRow = sheet.createRow(0)
                    Integer i = 0

                    List<String> headings = ["CLASS", "IFCMATERIAL", "THICKNESS", "RESULT_STATUS", "RESULT_ABSOLUTE", "RESULT_BY_VOLUME", "RESULT_SHARE",
                                             "INTENSITY_QUINTILE", "ABSOLUTE_SHARE_QUINTILE", "DATA_LABEL", "DATA_ID", "STATICFULLNAME", "RESOURCEID",
                                             "EPDNUMBER", "WARNINGS", "UNIT"]


                    if (importMapper?.apiCalculationParametersSentBack) {
                        headings.addAll(importMapper.apiCalculationParametersSentBack)
                    }

                    headings.each { String heading ->
                        Cell headerCellclass = headerRow.createCell(i)
                        headerCellclass.setCellStyle(cs)
                        headerCellclass.setCellType(CellType.STRING)
                        headerCellclass.setCellValue(heading)
                        i++
                    }

                    Integer numberOfHeadings = headings.size()
                    i = 1

                    ifcDatasets = handleConstructionDatasetResults(ifcDatasets, tempEntity, indicator.indicatorId, resultCategory.resultCategoryId, calculationRule.calculationRuleId, calculationResults)

                    Map<String, Double> resultAbsolutePerManualId = [:]
                    Map<String, Double> intencityPerManualId = [:]

                    if (failedToRecognizeDatasets && ifcDatasets) {
                        ifcDatasets.addAll(failedToRecognizeDatasets)
                    }

                    ifcDatasets?.each { Dataset dataset ->
                        Double resultForDataset
                        if (dataset.resultAbsolute) {
                            resultForDataset = dataset.resultAbsolute
                        } else {
                            resultForDataset = tempEntity.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRule.calculationRuleId, calculationResults)
                        }
                        Double carbonDataImpact

                        allResultCategories?.findAll({ !it.ignoreFromTotals?.contains(indicator.displayResult) && !it.ignoreFromTotals?.isEmpty() })?.each { ResultCategory category ->
                            Double value = tempEntity.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, indicator.displayResult, calculationResults)

                            if (value) {
                                carbonDataImpact = carbonDataImpact ? carbonDataImpact + value : value
                            }
                        }

                        if (carbonDataImpact != null) {
                            if (!("traciGWP_tn").equalsIgnoreCase(indicator.displayResult) && !("GWP_tn").equalsIgnoreCase(indicator.displayResult)) {
                                carbonDataImpact = (carbonDataImpact / 1000)
                            }
                        }

                        if (resultForDataset) {
                            resultAbsolutePerManualId.put((dataset.manualId), resultForDataset)
                        }

                        if (dataset.resultByVolume || (resultForDataset && dataset.quantity)) {
                            intencityPerManualId.put((dataset.manualId), dataset.resultByVolume ?: resultForDataset / dataset.quantity)
                        }
                    }

                    Double resultAbsoluteSum = resultAbsolutePerManualId.values()?.sum()

                    ResourceCache resourceCache = ResourceCache.init(ifcDatasets)
                    for (Dataset dataset : ifcDatasets) {
                        String resourceId = dataset.allImportDisplayFields?.collect({ it?.get("RESOURCEID") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("RESOURCEID") : null
                        Resource resource = resourceCache.getResource(dataset)
                        String staticFullName = dataset.allImportDisplayFields?.findAll({ it?.get("STATICFULLNAME") })?.collect({ it?.get("STATICFULLNAME") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("STATICFULLNAME") : resource?.staticFullName
                        String epdNumber = dataset.allImportDisplayFields?.findAll({ it?.get("EPDNUMBER") })?.collect({ it?.get("EPDNUMBER") })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get("EPDNUMBER") : resource?.epdNumber
                        String originalThickness

                        if (thicknessHeading) {
                            originalThickness = dataset.allImportDisplayFields?.findAll({ it?.get(thicknessHeading) })?.collect({ it?.get(thicknessHeading) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(thicknessHeading) : dataset.additionalQuestionAnswers?.get("thickness_mm")
                        } else {
                            originalThickness = dataset.additionalQuestionAnswers?.get("thickness_mm")
                        }

                        Map<String, Object> apiCalculationParametersSentBack = [:]
                        importMapper?.apiCalculationParametersSentBack?.each { String param ->
                            def answer = dataset.allImportDisplayFields?.findAll({ it?.get(param) })?.collect({ it?.get(param) })?.unique()?.size() == 1 ? dataset.allImportDisplayFields.first().get(param) : null

                            if (answer) {
                                apiCalculationParametersSentBack.put((param), answer)
                            }
                        }
                        ResourceType rt = resource?.resourceTypeObject

                        Double resultForDataset = resultAbsolutePerManualId.get(dataset.manualId)
                        Double resultByVolume = intencityPerManualId.get(dataset.manualId)

                        Integer co2IntensityQuintile
                        Integer resultAbsoluteQuintile

                        Integer valuesLargerThanBenchmark = 0
                        Integer valuesSameValueAsBenchmark = 0

                        if (resultByVolume) {
                            intencityPerManualId.values()?.each { Double total ->
                                if (total > resultByVolume) {
                                    valuesLargerThanBenchmark++
                                } else if (total.equals(resultByVolume)) {
                                    valuesSameValueAsBenchmark++
                                }
                            }
                            co2IntensityQuintile = Math.ceil((Double.valueOf((valuesLargerThanBenchmark + 0.5 * valuesSameValueAsBenchmark) / intencityPerManualId.values()?.size())) * 5)?.intValue()
                        }

                        if (resultForDataset) {
                            valuesLargerThanBenchmark = 0
                            valuesSameValueAsBenchmark = 0
                            resultAbsolutePerManualId.values()?.each { Double total ->
                                if (total > resultForDataset) {
                                    valuesLargerThanBenchmark++
                                } else if (total.equals(resultForDataset)) {
                                    valuesSameValueAsBenchmark++
                                }
                            }
                            resultAbsoluteQuintile = Math.ceil((Double.valueOf((valuesLargerThanBenchmark + 0.5 * valuesSameValueAsBenchmark) / resultAbsolutePerManualId.values()?.size())) * 5)?.intValue()
                        }
                        Row datasetRow = sheet.createRow(i)
                        Integer j = 0
                        Cell cell = datasetRow.createCell(j) // CLASS
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(dataset.originalClass ?: dataset.trainingData?.get("CLASS"))
                        j++

                        cell = datasetRow.createCell(j) // IFCMATERIAL
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(dataset.trainingData?.get("RESOURCEID"))
                        j++

                        cell = datasetRow.createCell(j) // THICKNESS
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(originalThickness)
                        j++

                        cell = datasetRow.createCell(j) // RESULT_STATUS
                        cell.setCellType(CellType.STRING)

                        if (resultForDataset) {
                            cell.setCellValue("SUCCESS")
                        } else {
                            cell.setCellValue("IGNORED")
                            cell.setCellStyle(ignored)
                        }
                        j++

                        cell = datasetRow.createCell(j) // RESULT_ABSOLUTE
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(resultForDataset)
                        j++

                        cell = datasetRow.createCell(j) // RESULT_BY_VOLUME
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(resultByVolume)
                        j++

                        cell = datasetRow.createCell(j) // RESULT_SHARE
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(resultForDataset && resultAbsoluteSum ? resultForDataset / resultAbsoluteSum * 100 : null)
                        j++

                        cell = datasetRow.createCell(j) // INTENSITY_QUINTILE
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(co2IntensityQuintile)
                        j++

                        cell = datasetRow.createCell(j) //ABSOLUTE_SHARE_QUINTILE
                        cell.setCellType(CellType.NUMERIC)
                        cell.setCellValue(resultAbsoluteQuintile)
                        j++

                        cell = datasetRow.createCell(j) // DATA_LABEL
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resource?.staticFullName ? resource.staticFullName.toString() : "")
                        j++

                        cell = datasetRow.createCell(j) // DATA_ID
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resource?.id ? resource.id.toString() : "")
                        j++

                        cell = datasetRow.createCell(j) // STATICFULLNAME
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(staticFullName ?: "")
                        j++

                        cell = datasetRow.createCell(j) // RESOURCEID
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(resourceId ?: "")
                        j++

                        cell = datasetRow.createCell(j) // EPDNUMBER
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(epdNumber ?: "")
                        j++

                        cell = datasetRow.createCell(j) // WARNINGS
                        cell.setCellType(CellType.STRING)

                        if (dataset.discarded) {
                            cell.setCellValue("Objects signifying empty, air or comparable value")
                        } else if (dataset.filtered) {
                            cell.setCellValue("Not relevant for chosen LCA tool")
                        } else {
                            String value = ""

                            //Thickness
                            Double thickness = datasetService.getThickness(dataset.additionalQuestionAnswers)

                            if (!dataset.allowMapping && dataset.userGivenUnit && dataset.quantity) {
                                value = value ? value + " and Generic definitions" : "Generic definitions"
                            }

                            if (!dataset.quantity || !dataset.userGivenUnit) {
                                value = value ? value + " and no quantity or unrecognized unit" : "No quantity or unrecognized unit"
                            }

                            if (dataset.importMapperCompositeMaterial) {
                                value = value ? value + " and composite material" : "Composite material"
                            }

                            if (resource?.impactNonLinear && !resource?.allowVariableThickness) {
                                value = value ? value + "and this material has a set fixed thickness and thickness variations are not considered" : "This material has a set fixed thickness and thickness variations are not considered"
                            }

                            if (rt && thickness && thickness > rt.maxThickness_mm) {
                                value = value ? value + " and implausibly thick object for this material type" : "Implausibly thick object for this material type"
                            }

                            if (rt && thickness && thickness < rt.minThickness_mm) {
                                value = value ? value + " and implausibly thin object for this material type" : "Implausibly thin object for this material type"
                            }

                            if (rt && thickness && thickness == 0.0) {
                                value = value ? value + " and material has no thickness even though required by material type" : "Material has no thickness even though required by material type"
                            }

                            if (dataset.importWarning) {
                                value = value ? value + " and ${dataset.importWarning}" : "${dataset.importWarning}"
                            }

                            if (dataset.importError) {
                                value = value ? value + " and ${dataset.importError}" : "${dataset.importError}"
                            }
                            cell.setCellValue(value)
                        }
                        j++
                        cell = datasetRow.createCell(j) // UNIT
                        cell.setCellType(CellType.STRING)
                        cell.setCellValue(dataset.userGivenUnit ?: "")
                        j++

                        importMapper?.apiCalculationParametersSentBack?.each { String param ->
                            cell = datasetRow.createCell(j)
                            cell.setCellType(CellType.STRING)
                            cell.setCellValue(apiCalculationParametersSentBack.get(param)?.toString() ?: "")
                            j++
                        }
                        i++
                    }

                    numberOfHeadings.times {
                        sheet.autoSizeColumn(it)
                    }
                }
            }
        }
        return wb
    }

    List<Dataset> handleConstructionDatasetResults(List<Dataset> datasets, Entity tempEntity, String indicatorId, String resultCategoryId,
                                                   String calculationRuleId, List<CalculationResult> resultsForIndicator) {
        List<String> constituentsToRemove = []
        if (datasets && tempEntity && indicatorId && resultCategoryId && calculationRuleId && resultsForIndicator) {
            Iterator<Dataset> datasetIterator = datasets.iterator()

            while (datasetIterator.hasNext()) {
                Dataset dataset = datasetIterator.next()
                if (dataset.parentConstructionId) {
                    constituentsToRemove.add(dataset.manualId)
                    Double resultForDataset = tempEntity.getResultForDataset(dataset.manualId, indicatorId, resultCategoryId, calculationRuleId, resultsForIndicator)

                    if (resultForDataset && dataset.quantity) {
                        Dataset parentConstDataset = datasets.find({ it.manualId?.equals(dataset.parentConstructionId) })

                        if (parentConstDataset) {
                            parentConstDataset.resultAbsolute = parentConstDataset.resultAbsolute ? parentConstDataset.resultAbsolute + resultForDataset : resultForDataset

                            if (parentConstDataset.quantity) {
                                parentConstDataset.resultByVolume = parentConstDataset.resultByVolume ? parentConstDataset.resultByVolume + (resultForDataset / parentConstDataset.quantity) : (resultForDataset / parentConstDataset.quantity)
                            }
                        }
                    }
                }
            }
            datasets.removeAll({ constituentsToRemove.contains(it.manualId) })
        }
        return datasets
    }

    @CompileStatic
    private void handleImportMapperQuestionMappingSpecialCases(ImportMapper importMapper, Set<Integer> questionMappingIndices, List<Integer> questionMappingSpecialCaseIndices, Row row, Cell cell) {
        try {
            if (importMapper?.questionMappingSpecialCases && questionMappingIndices.contains(cell.getColumnIndex()) &&
                    cell.getCellType() == CellType.STRING &&
                    importMapper.questionMappingSpecialCases.any({ it.equalsIgnoreCase(cell.getStringCellValue()) })) {
                for (Integer index in questionMappingSpecialCaseIndices) {
                    String valueForQuestionMappingSpecialCase = row.getCell(index.intValue())?.getStringCellValue()

                    if (valueForQuestionMappingSpecialCase) {
                        ImportMapperTranslation specialCaseTranslation = importMapper.questionMappingSpecialCaseTranslations?.find({
                            it.matchingValues.any({
                                valueForQuestionMappingSpecialCase.equalsIgnoreCase(it)
                            })
                        })

                        if (specialCaseTranslation) {
                            cell.setCellValue(specialCaseTranslation.applyValue)
                            break
                        }
                    }
                }
            }
        } catch (IllegalStateException ise) {
            log.error("Cannot find a value: ", ise)
        }
    }

    String getCellValue(Cell cell, boolean isStreamingReader = false, boolean autoTrim = false) {
        // Monitorjbl xlsx streamer doesnt handle FORMULA type cell quite right, so need extra handling
        String cellValue = null
        if (cell) {
            Boolean formula = Boolean.FALSE
            CellType cellType = cell.cellType


            if (cellType == CellType.FORMULA) {
                cellType = cell.getCachedFormulaResultType()
                formula = Boolean.TRUE
            }

            if (cellType == CellType.STRING) {
                if (formula && !isStreamingReader) {
                    cellValue = cell.getRichStringCellValue().toString()
                } else {
                    cellValue = cell.getStringCellValue()
                }
            } else if (cellType == CellType.NUMERIC) {
                double doubleValue = cell.getNumericCellValue()
                cellValue = doubleValue && doubleValue % 1 == 0 ? String.valueOf(doubleValue.intValue()) : String.valueOf(doubleValue)
            } else if (cellType == CellType.BOOLEAN) {
                if (isStreamingReader) {
                    cellValue = cell.getStringCellValue()
                } else {
                    cellValue = String.valueOf(cell.getBooleanCellValue())
                }
            }
        }
        if (autoTrim && cellValue) {
            return cellValue.trim()
        }
        return cellValue
    }

    @CompileStatic
    private void handleImportMapperQuestionMappingClassTranslations(List<ImportMapperTranslation> importMapperTranslations, Integer classMappingIndex, Cell cell) {
        if (importMapperTranslations && classMappingIndex != null && cell.getColumnIndex() == classMappingIndex.intValue()) {
            String cellValue = getCellValue(cell)
            ImportMapperTranslation importMapperTranslation = importMapperTranslations?.find({
                it.matchingValues?.any({ String value -> cellValue?.equalsIgnoreCase(value) })
            })

            if (importMapperTranslation) {
                cell.setCellType(CellType.STRING)
                cell.setCellValue(importMapperTranslation.applyValue)
            } else {
                cell.setCellType(CellType.STRING)
                cell.setCellValue(cellValue)
            }
        }
    }

    List<String> translateHeadingsForGrouping(List<String> headings, List<String> jsonDefinedGroupers, List<ImportMapperDataRule> importMapperDataRules) {
        List<String> translatedGroupers = []
        if (headings && jsonDefinedGroupers && importMapperDataRules) {
            jsonDefinedGroupers.each { String grouper ->
                if (headings*.toUpperCase().contains(grouper.toUpperCase())) {
                    translatedGroupers.add(grouper)
                } else {
                    importMapperDataRules.each { ImportMapperDataRule importMapperDataRule ->
                        if (importMapperDataRule.mappedHeadings.contains(grouper)) {
                            importMapperDataRule.mappedHeadings.each { String heading ->
                                if (headings*.toUpperCase().contains(heading.toUpperCase())) {
                                    translatedGroupers.add(heading)
                                }
                            }
                        }
                    }
                }
            }
        }
        return translatedGroupers
    }

    List<ImportMapperCollapseGrouper> createCollapseGroupers(List<Dataset> datasets, List<String> userDefinedGroupers = null,
                                                             List<String> jsonDefinedGroupers = null, List<ImportMapperDataRule> importMapperDataRules = null,
                                                             List<String> headings = null, ImportMapper importMapper = null, Boolean api = Boolean.FALSE,
                                                             List<String> specialGroupers = null, Map<String, Double> customSpecialCombineRange = null) {
        List<String> classIdentifyingFields = importMapper?.classIdentifyingFields ?: ["CLASS"]
        List<ImportMapperCollapseGrouper> collapseGrouperList = []
        datasets = datasets?.findAll({ !it.importMapperPartialQuantification })
        List<String> translatedGroupers = translateHeadingsForGrouping(headings, jsonDefinedGroupers, importMapperDataRules)

        if (!datasets?.isEmpty()) {
            if (!userDefinedGroupers && !jsonDefinedGroupers && specialGroupers && !api) {
                collapseGrouperList = combineDatasetsBySpecialRule(datasets, importMapper, specialGroupers, customSpecialCombineRange)
            } else {
                for (Dataset dataset in datasets) {
                    if (userDefinedGroupers) {
                        dataset.userDefinedGrouping = [:]
                        dataset.dataForCollapser?.each { String groupHeading, String value ->
                            String group = userDefinedGroupers.find({ it.equalsIgnoreCase(groupHeading) })

                            if (value && group) {
                                dataset.userDefinedGrouping.put(groupHeading, value.toUpperCase())
                            }
                        }
                    } else if (jsonDefinedGroupers && importMapperDataRules) {
                        dataset.userDefinedGrouping = [:]
                        if (translatedGroupers) {
                            translatedGroupers.each { String group ->
                                if (dataset.originalClass && classIdentifyingFields*.toUpperCase().contains(group?.toUpperCase())) {
                                    dataset.userDefinedGrouping.put(group, dataset.originalClass)
                                } else {
                                    dataset.dataForCollapser?.each { String groupHeading, String value ->
                                        if (groupHeading.equalsIgnoreCase(group) && value) {
                                            dataset.userDefinedGrouping.put(groupHeading, value.toUpperCase())
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ImportMapperCollapseGrouper collapseGrouper
                    if ((userDefinedGroupers && !collapseGrouperList.isEmpty()) || (jsonDefinedGroupers && importMapperDataRules)) {
                        collapseGrouper = collapseGrouperList.find({
                            it.collapseHeadingAndValue.equals(dataset.userDefinedGrouping)
                        })
                    }

                    if (collapseGrouper) {
                        collapseGrouper.datasetAmount = collapseGrouper.datasetAmount + 1
                        collapseGrouper.datasetIds.add(dataset.manualId?.toString())
                    } else {
                        collapseGrouper = new ImportMapperCollapseGrouper()
                        collapseGrouper.grouperId = new ObjectId().toString()
                        collapseGrouper.collapseHeadingAndValue = new HashMap<String, String>(dataset.userDefinedGrouping)
                        collapseGrouper.datasetAmount = 1
                        collapseGrouper.datasetIds = [dataset.manualId?.toString()]
                        collapseGrouper.isDescriptive = !isResourceDataset(dataset)
                        collapseGrouperList.add(collapseGrouper)
                    }
                    dataset.collapseGrouperId = collapseGrouper.grouperId
                }

                if (!api) {
                    if (collapseGrouperList && !collapseGrouperList.isEmpty()) {
                        collapseGrouperList = expandGroupsBySpecialRules(datasets, collapseGrouperList, importMapper, specialGroupers, customSpecialCombineRange)
                    }
                }
            }
        }
        return collapseGrouperList?.findAll({ it.datasetAmount > 1 })
    }

    Map<String, List<ImportMapperCollapseGrouper>> groupCollapseGroupersByClass(List<ImportMapperCollapseGrouper> collapseGroupers) {

        Map<String, List<ImportMapperCollapseGrouper>> grouperGroupsByClass = [:]

        List<ImportMapperCollapseGrouper> groupersToRemove = []
        String usedKey
        List<String> importedClasses = []

        collapseGroupers?.each { ImportMapperCollapseGrouper importMapperCollapseGrouper ->
            if (importMapperCollapseGrouper.collapseHeadingAndValue && !importMapperCollapseGrouper.collapseHeadingAndValue.isEmpty()) {
                if (importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")) {
                    importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get("CLASS")?.toString())
                } else {
                    usedKey = importMapperCollapseGrouper.collapseHeadingAndValue.keySet()?.toList()?.get(0)
                    importedClasses.add(importMapperCollapseGrouper.collapseHeadingAndValue.get(usedKey)?.toString())
                }
            } else {
                groupersToRemove.add(importMapperCollapseGrouper)
            }
        }

        if (!groupersToRemove.isEmpty()) {
            collapseGroupers.removeAll(groupersToRemove)
        }

        importedClasses?.unique()?.each { String classAsString ->
            def found
            if (usedKey) {
                found = collapseGroupers.findAll({
                    it.collapseHeadingAndValue?.get(usedKey)?.toString()?.equals(classAsString)
                })
            } else {
                found = collapseGroupers.findAll({
                    it.collapseHeadingAndValue?.get("CLASS")?.toString()?.equals(classAsString)
                })
            }

            if (found) {
                if (usedKey) {
                    String key = "${usedKey}: ${classAsString}"
                    grouperGroupsByClass.put(key, found.sort({ -it.datasetAmount }))
                } else {
                    grouperGroupsByClass.put(classAsString, found.sort({ -it.datasetAmount }))
                }
            }
        }
        return grouperGroupsByClass
    }

    List<ImportMapperCollapseGrouper> combineDatasetsBySpecialRule(List<Dataset> datasets, ImportMapper importMapper, List<String> specialGroupers, Map<String, Double> customSpecialCombineRange) {
        List<ImportMapperCollapseGrouper> collapseGrouperList = []

        if (importMapper && importMapper.groupByForCollapseSpecial && specialGroupers && datasets) {
            Integer thicknessRange = importMapper.thicknessCombinationRange ?: 5

            List<String> groupByForCollapseSpecial = importMapper.groupByForCollapseSpecial.findAll({
                specialGroupers.contains(it)
            })

            Map<String, Map<Integer, List<String>>> perkeleJaSaatana = [:]

            if (groupByForCollapseSpecial) {
                Map<String, Map<String, Double>> specialCombine = [:]

                datasets.each { Dataset dataset ->
                    dataset.userDefinedGrouping = [:]
                    List<String> keys = dataset.dataForCollapser?.keySet()?.toList()

                    if (keys) {
                        groupByForCollapseSpecial.each { String special ->
                            String key = keys.find({ it.equalsIgnoreCase(special) })

                            if (key) {
                                def value = dataset.dataForCollapser.get(key)
                                dataset.userDefinedGrouping.put(key, "${value ? "~${value}" : ""}")

                                if (value && value.isNumber()) {
                                    Map<String, Double> existing = specialCombine.get(key)

                                    if (existing) {
                                        existing.put((dataset.manualId.toString()), value.toDouble())
                                    } else {
                                        existing = [(dataset.manualId.toString()): value.toDouble()]
                                    }
                                    specialCombine.put(key, existing)
                                }
                            }
                        }
                    }
                }

                if (specialCombine) {
                    specialCombine.each { String key, Map<String, Double> manualIdAndSpecialCombineValue ->
                        Map<Integer, List<String>> manualIdsPerExpandedGroup = [:]
                        if (manualIdAndSpecialCombineValue) {
                            Double specialRange = customSpecialCombineRange?.get(key) != null ? customSpecialCombineRange.get(key) : thicknessRange.toDouble()
                            manualIdAndSpecialCombineValue = manualIdAndSpecialCombineValue.sort({ a, b -> a.value <=> b.value })

                            Double previousValue
                            int groupIndex = 0
                            manualIdAndSpecialCombineValue.each { String manualId, Double value ->
                                if (previousValue != null) {
                                    if ("SOLIDITY".equalsIgnoreCase(key)) {
                                        Integer group = 0

                                        if (value <= 25) {
                                            group = 0
                                        } else if (value > 25 && value <= 50) {
                                            group = 1
                                        } else if (value > 50 && value <= 75) {
                                            group = 2
                                        } else if (value > 75 && value <= 100) {
                                            group = 3
                                        }

                                        List<String> existing = manualIdsPerExpandedGroup.get(group)

                                        if (existing) {
                                            existing.add(manualId)
                                        } else {
                                            existing = [manualId]
                                        }
                                        manualIdsPerExpandedGroup.put(group, existing)
                                    } else {
                                        if (value <= (previousValue + specialRange)) {
                                            List<String> existing = manualIdsPerExpandedGroup.get(groupIndex)

                                            if (existing) {
                                                existing.add(manualId)
                                            } else {
                                                existing = [manualId]
                                            }
                                            manualIdsPerExpandedGroup.put(groupIndex, existing)
                                        } else {
                                            previousValue = value
                                            groupIndex++
                                            manualIdsPerExpandedGroup.put(groupIndex, [manualId])
                                        }
                                    }

                                } else {
                                    if ("SOLIDITY".equalsIgnoreCase(key)) {
                                        Integer group = 0

                                        if (value <= 25) {
                                            group = 0
                                        } else if (value > 25 && value <= 50) {
                                            group = 1
                                        } else if (value > 50 && value <= 75) {
                                            group = 2
                                        } else if (value > 75 && value <= 100) {
                                            group = 3
                                        }
                                        manualIdsPerExpandedGroup.put(group, [manualId])
                                    } else {
                                        manualIdsPerExpandedGroup.put(groupIndex, [manualId])
                                    }
                                    previousValue = value
                                }
                            }
                            perkeleJaSaatana.put(key, manualIdsPerExpandedGroup)
                        }
                    }

                    Map<Integer, List<String>> ultimateGroups = [:]

                    int groupingIndex = 0
                    List<String> idsChecked = []

                    datasets.each { Dataset dataset ->
                        if (!idsChecked.contains(dataset.manualId)) {
                            String manualId = dataset.manualId

                            List<List<String>> datasetGroups = []

                            perkeleJaSaatana.each { String specialGroup, Map<Integer, List<String>> foo ->
                                foo.each { Integer group, List<String> manualIds ->
                                    if (manualIds.contains(manualId)) {
                                        datasetGroups.add(manualIds)
                                    }
                                }
                            }

                            List<String> ultimateGroup
                            datasetGroups.each { List<String> group ->
                                if (!ultimateGroup) {
                                    ultimateGroup = group
                                }
                                ultimateGroup = CollectionUtils.retainAll(ultimateGroup, group)
                            }

                            if (ultimateGroup && ultimateGroup.size() > 1) {
                                idsChecked.addAll(ultimateGroup)
                                ultimateGroups.put(groupingIndex, ultimateGroup)
                                groupingIndex++
                            }
                        }
                    }

                    if (ultimateGroups) {
                        ultimateGroups.each { Integer group, List<String> manualIds ->
                            ImportMapperCollapseGrouper splitGrouper = new ImportMapperCollapseGrouper()
                            splitGrouper.grouperId = new ObjectId().toString()
                            splitGrouper.collapseHeadingAndValue = new HashMap<String, String>(datasets.find({ manualIds.contains(it.manualId) })?.userDefinedGrouping)
                            groupByForCollapseSpecial.each {
                                splitGrouper.collapseHeadingAndValue.put((it), "")
                            }
                            splitGrouper.datasetAmount = manualIds.size()
                            splitGrouper.datasetIds = manualIds

                            datasets.findAll({ manualIds.contains(it.manualId.toString()) })?.each { Dataset dataset ->
                                dataset.collapseGrouperId = splitGrouper.grouperId
                            }

                            collapseGrouperList.add(splitGrouper)
                        }
                    }
                }
            }
        }
        return collapseGrouperList
    }

    List<ImportMapperCollapseGrouper> expandGroupsBySpecialRules(List<Dataset> datasets, List<ImportMapperCollapseGrouper> collapseGrouperList, ImportMapper importMapper, List<String> specialGroupers, Map<String, Double> customSpecialCombineRange) {
        Boolean unableToCreateAnyGroupsWithMoreThanOneDataset = Boolean.FALSE
        List<ImportMapperCollapseGrouper> filteredGroupers = []

        if (importMapper && importMapper.groupByForCollapseSpecial && specialGroupers && datasets) {
            Integer thicknessRange = importMapper.thicknessCombinationRange ?: 5
            List<String> groupByForCollapseSpecial = importMapper.groupByForCollapseSpecial.findAll({ specialGroupers.contains(it) })

            if (groupByForCollapseSpecial) {
                Map<String, Map<Integer, List<String>>> perkeleJaSaatana = [:]

                collapseGrouperList.each { ImportMapperCollapseGrouper collapseGrouper ->
                    Map<String, Map<String, Double>> specialCombine = [:]
                    List<Dataset> groupDataset = datasets.findAll({ collapseGrouper.datasetIds?.contains(it.manualId.toString()) })

                    groupDataset?.each { Dataset dataset ->
                        List<String> keys = dataset.dataForCollapser?.keySet()?.toList()

                        if (keys) {
                            groupByForCollapseSpecial.each { String special ->
                                String key = keys.find({ it.equalsIgnoreCase(special) })

                                if (key) {
                                    def value = dataset.dataForCollapser.get(key)

                                    if (value && value.isNumber()) {
                                        Map<String, Double> existing = specialCombine.get(key)

                                        if (existing) {
                                            existing.put((dataset.manualId.toString()), value.toDouble())
                                        } else {
                                            existing = [(dataset.manualId.toString()): value.toDouble()]
                                        }
                                        specialCombine.put(key, existing)
                                    }
                                }
                            }
                        }
                    }

                    if (specialCombine) {
                        specialCombine.each { String key, Map<String, Double> manualIdAndSpecialCombineValue ->
                            Double specialRange = customSpecialCombineRange?.get(key) != null ? customSpecialCombineRange?.get(key) : thicknessRange.toDouble()

                            Map<Integer, List<String>> manualIdsPerExpandedGroup = [:]
                            if (manualIdAndSpecialCombineValue) {
                                manualIdAndSpecialCombineValue = manualIdAndSpecialCombineValue.sort({ a, b -> a.value <=> b.value })

                                Double previousValue
                                int groupIndex = 0
                                manualIdAndSpecialCombineValue.each { String manualId, Double value ->
                                    if (previousValue != null) {
                                        if ("SOLIDITY".equalsIgnoreCase(key)) {
                                            Integer group = 0

                                            if (value <= 25) {
                                                group = 0
                                            } else if (value > 25 && value <= 50) {
                                                group = 1
                                            } else if (value > 50 && value <= 75) {
                                                group = 2
                                            } else if (value > 75 && value <= 100) {
                                                group = 3
                                            }

                                            List<String> existing = manualIdsPerExpandedGroup.get(group)

                                            if (existing) {
                                                existing.add(manualId)
                                            } else {
                                                existing = [manualId]
                                            }
                                            manualIdsPerExpandedGroup.put(group, existing)
                                        } else {
                                            if (value <= (previousValue + specialRange)) {
                                                List<String> existing = manualIdsPerExpandedGroup.get(groupIndex)

                                                if (existing) {
                                                    existing.add(manualId)
                                                } else {
                                                    existing = [manualId]
                                                }
                                                manualIdsPerExpandedGroup.put(groupIndex, existing)
                                            } else {
                                                previousValue = value
                                                groupIndex++
                                                manualIdsPerExpandedGroup.put(groupIndex, [manualId])
                                            }
                                        }

                                    } else {
                                        if ("SOLIDITY".equalsIgnoreCase(key)) {
                                            Integer group = 0

                                            if (value <= 25) {
                                                group = 0
                                            } else if (value > 25 && value <= 50) {
                                                group = 1
                                            } else if (value > 50 && value <= 75) {
                                                group = 2
                                            } else if (value > 75 && value <= 100) {
                                                group = 3
                                            }
                                            manualIdsPerExpandedGroup.put(group, [manualId])
                                        } else {
                                            manualIdsPerExpandedGroup.put(groupIndex, [manualId])
                                        }
                                        previousValue = value
                                    }
                                }
                                perkeleJaSaatana.put(key, manualIdsPerExpandedGroup)
                            }
                        }

                        Map<Integer, List<String>> ultimateGroups = [:]

                        int groupingIndex = 0
                        List<String> idsChecked = []

                        groupDataset?.each { Dataset dataset ->
                            if (!idsChecked.contains(dataset.manualId)) {
                                String manualId = dataset.manualId

                                List<List<String>> datasetGroups = []

                                perkeleJaSaatana.each { String specialGroup, Map<Integer, List<String>> foo ->
                                    foo.each { Integer group, List<String> manualIds ->
                                        if (manualIds.contains(manualId)) {
                                            datasetGroups.add(manualIds)
                                        }
                                    }
                                }

                                List<String> ultimateGroup
                                datasetGroups.each { List<String> group ->
                                    if (!ultimateGroup) {
                                        ultimateGroup = group
                                    }
                                    ultimateGroup = CollectionUtils.retainAll(ultimateGroup, group)
                                }

                                if (ultimateGroup && ultimateGroup.size() > 1) {
                                    idsChecked.addAll(ultimateGroup)
                                    ultimateGroups.put(groupingIndex, ultimateGroup)
                                    groupingIndex++
                                }
                            }
                        }

                        if (ultimateGroups) {
                            ultimateGroups.each { Integer group, List<String> manualIds ->
                                ImportMapperCollapseGrouper splitGrouper = new ImportMapperCollapseGrouper()
                                splitGrouper.grouperId = new ObjectId().toString()
                                splitGrouper.collapseHeadingAndValue = new HashMap<String, String>(groupDataset.find({ manualIds.contains(it.manualId) })?.userDefinedGrouping)
                                groupByForCollapseSpecial.each {
                                    splitGrouper.collapseHeadingAndValue.put((it), "")
                                }
                                splitGrouper.datasetAmount = manualIds.size()
                                splitGrouper.datasetIds = manualIds

                                groupDataset.findAll({ manualIds.contains(it.manualId.toString()) })?.each { Dataset dataset ->
                                    dataset.collapseGrouperId = splitGrouper.grouperId
                                }

                                filteredGroupers.add(splitGrouper)
                            }
                        } else {
                            unableToCreateAnyGroupsWithMoreThanOneDataset = Boolean.TRUE
                        }
                    } else {
                        filteredGroupers.add(collapseGrouper)
                    }
                }
            }
        }

        if (filteredGroupers.isEmpty() && !unableToCreateAnyGroupsWithMoreThanOneDataset) {
            return collapseGrouperList
        } else {
            return filteredGroupers
        }
    }

    void calculatePercentageOfTotal(List<Dataset> datasets, ImportMapper importMapper) {
        if (datasets && importMapper?.showProportionOfTotalsForUnitTypes) {
            Double total = 0
            String targetUnit = importMapper.showProportionOfTotalsForUnitTypes.get(0).toUpperCase()
            List<String> allUnits = importMapper.showProportionOfTotalsForUnitTypes.collect({ it.toUpperCase() })
            List<String> allCalculableDatasetUnits = datasets.findAll({ it.userGivenUnit })?.collect({
                unitConversionUtil.transformImperialUnitToEuropeanUnit(it.userGivenUnit)
            })*.toUpperCase().unique().findAll({ allUnits.contains(it.toUpperCase()) })

            if (allCalculableDatasetUnits) {
                if (1 == allCalculableDatasetUnits.size()) {
                    targetUnit = allCalculableDatasetUnits.first()
                }

                datasets.each { Dataset dataset ->
                    Double quantity

                    if (dataset.userGivenUnit && dataset.quantity && allUnits?.contains(unitConversionUtil.transformImperialUnitToEuropeanUnit(dataset.userGivenUnit).toUpperCase())) {
                        if (!targetUnit.equalsIgnoreCase(dataset.userGivenUnit)) {
                            String userThickness = dataset.additionalQuestionAnswers?.get("thickness_mm")
                            Double userThicknessAsDouble

                            if (userThickness && DomainObjectUtil.isNumericValue(userThickness)) {
                                userThicknessAsDouble = DomainObjectUtil.convertStringToDouble(userThickness)
                            }
                            quantity = unitConversionUtil.doConversion(dataset.quantity, userThicknessAsDouble, dataset.userGivenUnit, null, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, null, targetUnit, Boolean.TRUE)
                        } else {
                            quantity = dataset.quantity
                        }

                        if (quantity) {
                            dataset.tempConvertedQuantity = quantity
                            total = total + quantity
                        }
                    }
                }

                if (total) {
                    datasets.each { Dataset dataset ->
                        if (dataset.tempConvertedQuantity) {
                            dataset.percentageOfTotal = dataset.tempConvertedQuantity / total * 100
                        }
                    }
                }
            }
        }
    }

    List<String> importExcelWorkbook(MultipartFile excelFile) {
        //TODO: can be slow in case of large files, everything gets into memory
        List<String> trainingData = []
        try {
            def fileName = excelFile.originalFilename
            Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            Sheet sheet = workbook.getSheetAt(0)
            Iterator<Row> rowIt = sheet.rowIterator()
            Row row = rowIt.next()
            String header = getRowData(row)
            while (rowIt.hasNext()) {
                row = rowIt.next()
                trainingData.add(getRowData(row)?.trim())
            }
            log.info("Uploaded file: ${fileName}, Imported ${trainingData?.size()} rows from column: ${header}")
            flashService.setFadeInfoAlert("Uploaded file: ${fileName}, Imported ${trainingData?.size()} rows from column: ${header}", true)
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in importExcelWorkbook", e)
            flashService.setFadeErrorAlert("Error in importExcelWorkbook: ${e.getMessage()}", true)
        }
        return trainingData
    }

    String getRowData(Row row) {
        String data = getCellValue(row.getCell(0))
        return data
    }

    Map<String, Integer> getAmountOfGroupedByData(List<Dataset> okDatasets, String allowDroppingDataGroupedBy) {
        Map<String, Integer> amountOfGroupedByData = [:]
        if (okDatasets && allowDroppingDataGroupedBy) {
            okDatasets.each { Dataset dataset ->
                if (allowDroppingDataGroupedBy && dataset.dataForCollapser?.get(allowDroppingDataGroupedBy)) {
                    String groupedBy = dataset.dataForCollapser.get(allowDroppingDataGroupedBy).toUpperCase()
                    dataset.importMapperGroupedBy = groupedBy
                    Integer amount = amountOfGroupedByData.get(groupedBy)

                    if (amount) {
                        amountOfGroupedByData.put(groupedBy, (amount + 1))
                    } else {
                        amountOfGroupedByData.put(groupedBy, 1)
                    }
                }
            }
        }
        return amountOfGroupedByData
    }

    void convertDatasetsToUnitSystem(List<Dataset> okDatasets, Boolean convertToMetric, Boolean convertToImperial) {
        if (okDatasets) {
            if (convertToMetric) {
                okDatasets.each { Dataset dataset ->
                    if (dataset.quantity && com.bionova.optimi.core.Constants.IMPERIAL_UNITS.contains(dataset.userGivenUnit)) {
                        String targetUnit = unitConversionUtil.transformImperialUnitToEuropeanUnit(dataset.userGivenUnit)

                        if (targetUnit && targetUnit != dataset.userGivenUnit) {
                            String userThickness_mm = dataset.additionalQuestionAnswers?.get("thickness_mm")
                            String userThickness_in = dataset.additionalQuestionAnswers?.get("thickness_in")
                            Double userThicknessAsDouble_mm
                            Double userThicknessAsDouble_in

                            if (userThickness_mm && DomainObjectUtil.isNumericValue(userThickness_mm)) {
                                userThicknessAsDouble_mm = DomainObjectUtil.convertStringToDouble(userThickness_mm)
                            }

                            if (userThickness_in && DomainObjectUtil.isNumericValue(userThickness_in)) {
                                userThicknessAsDouble_in = DomainObjectUtil.convertStringToDouble(userThickness_in)
                            }

                            if (userThicknessAsDouble_mm && !userThicknessAsDouble_in) {
                                userThicknessAsDouble_in = userThicknessAsDouble_mm * 0.0393700787
                                dataset.additionalQuestionAnswers.put("thickness_in", "${userThicknessAsDouble_in}")
                            } else if (userThicknessAsDouble_in && !userThicknessAsDouble_mm) {
                                userThicknessAsDouble_mm = userThicknessAsDouble_in * 25.4
                                dataset.additionalQuestionAnswers.put("thickness_mm", "${userThicknessAsDouble_mm}")
                            }
                            Double europeanQuantity = unitConversionUtil.doConversion(dataset.quantity, null, dataset.userGivenUnit, null, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, null, targetUnit, Boolean.TRUE)

                            if (europeanQuantity) {
                                dataset.quantity = europeanQuantity
                                dataset.answerIds = ["${europeanQuantity}"]
                                dataset.userGivenUnit = targetUnit
                            }
                        }
                    }
                }
            } else if (convertToImperial) {
                okDatasets.each { Dataset dataset ->
                    if (dataset.quantity && com.bionova.optimi.core.Constants.METRIC_UNITS.contains(dataset.userGivenUnit)) {
                        String targetUnit = unitConversionUtil.europeanUnitToImperialUnit(dataset.userGivenUnit)

                        if (targetUnit && targetUnit != dataset.userGivenUnit) {
                            String userThickness_mm = dataset.additionalQuestionAnswers?.get("thickness_mm")
                            String userThickness_in = dataset.additionalQuestionAnswers?.get("thickness_in")
                            Double userThicknessAsDouble_mm
                            Double userThicknessAsDouble_in

                            if (userThickness_mm && DomainObjectUtil.isNumericValue(userThickness_mm)) {
                                userThicknessAsDouble_mm = DomainObjectUtil.convertStringToDouble(userThickness_mm)
                            }

                            if (userThickness_in && DomainObjectUtil.isNumericValue(userThickness_in)) {
                                userThicknessAsDouble_in = DomainObjectUtil.convertStringToDouble(userThickness_in)
                            }

                            if (userThicknessAsDouble_mm && !userThicknessAsDouble_in) {
                                userThicknessAsDouble_in = userThicknessAsDouble_mm * 0.0393700787
                                dataset.additionalQuestionAnswers.put("thickness_in", "${userThicknessAsDouble_in}")
                            } else if (userThicknessAsDouble_in && !userThicknessAsDouble_mm) {
                                userThicknessAsDouble_mm = userThicknessAsDouble_in * 25.4
                                dataset.additionalQuestionAnswers.put("thickness_mm", "${userThicknessAsDouble_mm}")
                            }
                            Double imperialQuantity = unitConversionUtil.doConversion(dataset.quantity, null, dataset.userGivenUnit, null, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, null, targetUnit, Boolean.TRUE)

                            if (imperialQuantity) {
                                dataset.quantity = imperialQuantity
                                dataset.answerIds = ["${imperialQuantity}"]
                                dataset.userGivenUnit = targetUnit
                            }
                        }
                    }
                }
            }
        }
    }

    void setDefaultValuesForDataset(Dataset dataset, ImportMapperDefaultRule importMapperDefaultRule) {
        if (dataset && importMapperDefaultRule) {
            importMapperDefaultRule.defaults?.each { String additionalQuestionId, String answer ->
                if ("thickness_mm".equals(additionalQuestionId) || "thickness_in".equals(additionalQuestionId)) {
                    Double valueAsDouble

                    if (answer && DomainObjectUtil.isNumericValue(answer)) {
                        valueAsDouble = DomainObjectUtil.convertStringToDouble(answer)
                    }

                    if (valueAsDouble) {
                        if (dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers.put(additionalQuestionId, answer)
                        } else {
                            dataset.additionalQuestionAnswers = [(additionalQuestionId): answer]
                        }
                    } else {
                        Double defaultThickness = "thickness_mm".equals(additionalQuestionId) ?
                                datasetService.getResource(dataset)?.defaultThickness_mm : datasetService.getResource(dataset)?.defaultThickness_in

                        if (dataset.additionalQuestionAnswers) {
                            dataset.additionalQuestionAnswers.put(additionalQuestionId, defaultThickness ? defaultThickness.toString() : answer)
                        } else {
                            dataset.additionalQuestionAnswers = [(additionalQuestionId): defaultThickness ? defaultThickness.toString() : answer]
                        }
                    }
                } else {
                    if (dataset.additionalQuestionAnswers) {
                        if (!dataset.additionalQuestionAnswers.get(additionalQuestionId)) {
                            dataset.additionalQuestionAnswers.put(additionalQuestionId, answer)
                        }
                    } else {
                        dataset.additionalQuestionAnswers = [(additionalQuestionId): answer]
                    }
                }
            }
        }
    }

    List<Dataset> setParamValuesForDatasets(List<Dataset> datasets, Map<String, String[]> paramMap) {
        List<Dataset> newDatasets = []
        Map<String, Map<String, String>> newImportFields = [:]
        String datasetId
        if (paramMap && datasets) {
            paramMap.each { String paramName, String[] paramValue ->
                if (paramName.contains('.')) {
                    datasetId = paramName.tokenize(".")[1]

                    if (datasetId) {
                        Dataset dataset = newDatasets?.find({ datasetId.equals(it.manualId) })

                        if (!dataset) {
                            dataset = datasets.find({ datasetId.equals(it.manualId) })

                            if (dataset) {
                                dataset.importMapperCompositeMaterial = Boolean.FALSE
                                newDatasets.add(dataset)
                            }
                        }

                        if (dataset) {
                            String value = paramMap.get(paramName)?.toList()?.get(0)

                            if (paramName.startsWith("noTrainingDataset")) {
                                dataset.allowMapping = false
                            } else if (paramName.contains("rememberMapping")) {
                                if (value == "false") {
                                    dataset.allowMapping = false
                                }
                            } else if (paramName.contains("additional_")) {
                                String additionalQuestionId = paramName.tokenize(".")[0]?.replace("additional_", "")

                                if (additionalQuestionId && value) {
                                    if (dataset.additionalQuestionAnswers) {
                                        dataset.additionalQuestionAnswers.put(additionalQuestionId, value)
                                    } else {
                                        dataset.additionalQuestionAnswers = [(additionalQuestionId): value]
                                    }
                                }
                            } else if (paramName.contains("resourceId") || paramName.contains("remapResourceId") || paramName.contains("identifiedResourceId")) {
                                if (value) {
                                    if (paramName.contains("remapResourceId")) {
                                        dataset.remapInImportMapper = Boolean.TRUE
                                    }
                                    // We have to nullify the profileId to not get original resources profileId
                                    dataset.profileId = null
                                    dataset.resourceId = value
                                }
                            } else if (paramName.contains("quantity")) {
                                dataset.answerIds = [value]
                            } else if (paramName.contains("userGivenUnit")) {
                                dataset.userGivenUnit = value
                            } else if (paramName.contains("userGivenQuantity")) {
                                if (value && DomainObjectUtil.isNumericValue(value)) {
                                    dataset.answerIds = [value]
                                    dataset.quantity = DomainObjectUtil.convertStringToDouble(value)
                                }
                            } else if (paramName.contains("comment")) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put("comment", value)
                                } else {
                                    dataset.additionalQuestionAnswers = [comment: value]
                                }
                                if (dataset.datasetImportFieldsId) {
                                    String importHeader = "COMMENT"
                                    Map<String, String> existing = newImportFields.get(dataset.datasetImportFieldsId)
                                    if (existing) {
                                        existing.put(importHeader, value.toString())
                                    } else {
                                        existing = [(importHeader): value.toString()]
                                    }
                                    newImportFields.put(dataset.datasetImportFieldsId, existing)
                                }
                            } else if (paramName.contains("thickness_mm")) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put("thickness_mm", value)
                                } else {
                                    dataset.additionalQuestionAnswers = [thickness_mm: value]
                                }
                            } else if (paramName.contains("composite")) {
                                dataset.importMapperDecideLater = Boolean.TRUE
                            } else if (paramName.contains("answerIds")) {
                                dataset.answerIds = value ? [value] : null
                            }
                        }
                    }
                }
            }
            List<Dataset> toRemove = []
            ResourceCache resourceCache = ResourceCache.init(newDatasets)
            for (Dataset d : newDatasets) {
                Resource resource = resourceCache.getResource(d)
                if ("incompatible".equals(d.resourceId)) {
                    toRemove.add(d)
                } else if (resource?.construction) {
                    String identifier = UUID.randomUUID().toString() + resource?.constructionId
                    d.uniqueConstructionIdentifier = identifier
                }
            }
            if (newImportFields) {
                datasetService.upDatasetImportFieldsByIdAndValueMap(newImportFields)
            }
            if (toRemove) {
                toRemove.each { Dataset d ->
                    newDatasets.removeIf({ it.manualId == d.manualId })
                }
            }
        }
        return newDatasets
    }

    public List<BimModelWarning> getBimModelWarningsForDatasets(List<Dataset> datasets, List<Dataset> resolverDatasets, Integer amountToCountAgainst) {
        List<BimModelWarning> warnings = []

        if (datasets) {
            List<Dataset> discardedDatasets = []
            List<Dataset> filteredDatasets = []
            List<Dataset> ambiguousDatasets = []
            List<Dataset> unquantifiedDatasets = []
            List<Dataset> compositeDatasets = []
            List<Dataset> implausibleThicknesses = []
            List<Dataset> geometryErrors = []
            List<Dataset> otherErrors = []
            List<Dataset> unconventionalClassificationSets = []
            List<Dataset> unconventionalUnitSets = []
            List<Dataset> tooGenericDatasets = []

            int i = 0

            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset d : datasets) {
                if (d.discarded) {
                    discardedDatasets.add(d)
                } else if (d.filtered) {
                    filteredDatasets.add(d)
                } else {
                    Dataset combinedInto = resolverDatasets?.find({ it.originalDatasetsManualIds?.contains(d.manualId) })

                    if (combinedInto) {
                        d.wasCombinedInto = combinedInto.manualId
                    } else if (resolverDatasets?.find({ it.manualId == d.manualId })) {
                        d.wasResolved = Boolean.TRUE
                    }

                    if (isResourceDataset(d)) {
                        Resource r = resourceCache.getResource(d)
                        ResourceType rt = resourceService.getSubType(r)

                        if (!d.resourceId && !combinedInto?.resourceId) {
                            ambiguousDatasets.add(d)
                        }
                        if (!d.quantity || !d.userGivenUnit) {
                            unquantifiedDatasets.add(d)
                        }
                        if (!d.allowMapping && d.quantity && d.userGivenUnit) {
                            tooGenericDatasets.add(d)
                        }
                        if (d.importWarning) {
                            geometryErrors.add(d)
                        }
                        if (d.importError && d.undefinedErrorType) {
                            otherErrors.add(d)
                        }
                        if (d.importError && d.unconventionalClassification) {
                            unconventionalClassificationSets.add(d)
                        }
                        if (d.importError && d.unconventionalUnit) {
                            unconventionalUnitSets.add(d)
                        }
                        if (d.importMapperCompositeMaterial) {
                            compositeDatasets.add(d)
                        }

                        //Thickness
                        Double thickness = datasetService.getThickness(d.additionalQuestionAnswers)
                        if (rt && thickness && (thickness > rt.maxThickness_mm || thickness < rt.minThickness_mm || thickness == 0.0)) {
                            implausibleThicknesses.add(d)
                        }
                    } else {
                        if (d.importWarning) {
                            geometryErrors.add(d)
                        }
                        if (d.importError && d.undefinedErrorType) {
                            otherErrors.add(d)
                        }
                    }
                    i++
                }
            }

            if (filteredDatasets && !filteredDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = filteredDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }

                // findResults{it?.defaultConstituent ? it?.co2e : null}?.sum()
                BimModelWarning filteredWarning = new BimModelWarning(datasetIds: filteredDatasets.collect({ it.manualId }), amount: filteredDatasets.size(), amountOfCombined: filteredDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + filteredDatasets.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'INFO', issueClass: 'OUT OF SCOPE', helpText: 'Not relevant for chosen LCA tool')
                if (filteredWarning) {
                    warnings.add(filteredWarning)
                }
            }

            if (discardedDatasets && !discardedDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = discardedDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning discardedMaterialsWarning = new BimModelWarning(datasetIds: discardedDatasets.collect({ it.manualId }), amount: discardedDatasets.size(), amountOfCombined: discardedDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + discardedDatasets.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'INFO', issueClass: 'Non-material objects', helpText: 'Objects signifying empty, air or comparable value')
                if (discardedMaterialsWarning) {
                    warnings.add(discardedMaterialsWarning)
                }
            }

            if (ambiguousDatasets && !ambiguousDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = ambiguousDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning ambigousWarning = new BimModelWarning(datasetIds: ambiguousDatasets.collect({ it.manualId }), amount: ambiguousDatasets.size(), amountOfCombined: ambiguousDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + ambiguousDatasets.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'UNIDENTIFIED', issueClass: 'UNIDENTIFIED DATA', helpText: 'No automatic mapping identified', dontShowInVisuals: Boolean.TRUE)
                if (ambigousWarning) {
                    warnings.add(ambigousWarning)
                }
            }

            if (unquantifiedDatasets && !unquantifiedDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = unquantifiedDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning quantityWarning = new BimModelWarning(datasetIds: unquantifiedDatasets.collect({ it.manualId }), amount: unquantifiedDatasets.size(), amountOfCombined: unquantifiedDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + unquantifiedDatasets.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'WARN', issueClass: 'INVALID QUANTITY', helpText: 'Quantity can not be read from the data')
                if (quantityWarning) {
                    warnings.add(quantityWarning)
                }
            }

            if (tooGenericDatasets && !tooGenericDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = tooGenericDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning genericWarning = new BimModelWarning(datasetIds: tooGenericDatasets.collect({ it.manualId }), amount: tooGenericDatasets.size(), amountOfCombined: tooGenericDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + tooGenericDatasets.findAll({ it.wasResolved }).size(), share: share?.round(2), status: 'WARN', issueClass: 'GENERIC DEFINITIONS', helpText: 'Labels too generic to be identifiable')
                if (genericWarning) {
                    warnings.add(genericWarning)
                }
            }

            if (compositeDatasets && !compositeDatasets.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = compositeDatasets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning quantityWarning = new BimModelWarning(datasetIds: compositeDatasets.collect({ it.manualId }), share: share?.round(2), amount: compositeDatasets.size(), amountOfCombined: compositeDatasets.findResults({ it.wasCombinedInto ?: null }).unique().size() + compositeDatasets.findAll({ it.wasResolved }).size(), status: 'WARN', issueClass: 'COMPOSITE MATERIALS', helpText: 'Multi-constituent materials with no layer structure')
                if (quantityWarning) {
                    warnings.add(quantityWarning)
                }
            }

            if (implausibleThicknesses && !implausibleThicknesses.isEmpty()) {
                Double share

                if (amountToCountAgainst) {
                    share = implausibleThicknesses.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning thicknessWarning = new BimModelWarning(datasetIds: implausibleThicknesses.collect({ it.manualId }), amount: implausibleThicknesses.size(), amountOfCombined: implausibleThicknesses.findResults({ it.wasCombinedInto ?: null }).unique().size() + implausibleThicknesses.findAll({ it.wasResolved }).size(), share: share?.round(2), status: 'ERROR', issueClass: 'IMPLAUSIBLE THICKNESS', helpText: 'Materials with implausible thickness present')
                if (thicknessWarning) {
                    warnings.add(thicknessWarning)
                }
            }
            if (otherErrors) {
                Double share

                if (amountToCountAgainst) {
                    share = otherErrors.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning otherErrorsWarning = new BimModelWarning(datasetIds: otherErrors.collect({ it.manualId }), amount: otherErrors.size(), amountOfCombined: otherErrors.findResults({ it.wasCombinedInto ?: null }).unique().size() + otherErrors.findAll({ it.wasResolved }).size(), share: share?.round(2), status: 'ERROR', issueClass: 'OTHER ERRORS', helpText: 'Technical or encoding errors in data')
                if (otherErrorsWarning) {
                    warnings.add(otherErrorsWarning)
                }
            }
            if (geometryErrors) {
                Double share

                if (amountToCountAgainst) {
                    share = geometryErrors.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning geometryErrorsWarning = new BimModelWarning(datasetIds: geometryErrors.collect({ it.manualId }), amount: geometryErrors.size(), amountOfCombined: geometryErrors.findResults({ it.wasCombinedInto ?: null }).unique().size() + geometryErrors.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'WARN', issueClass: 'Geometry errors', helpText: 'Technical or other errors in data')
                if (geometryErrorsWarning) {
                    warnings.add(geometryErrorsWarning)
                }
            }
            if (unconventionalClassificationSets) {
                Double share

                if (amountToCountAgainst) {
                    share = unconventionalClassificationSets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning unconventionalClassificationSetsWarning = new BimModelWarning(datasetIds: unconventionalClassificationSets.collect({ it.manualId }), amount: unconventionalClassificationSets.size(), amountOfCombined: unconventionalClassificationSets.findResults({ it.wasCombinedInto ?: null }).unique().size() + unconventionalClassificationSets.findAll({ it.wasResolved }).size(), share: share?.round(2), status: 'ERROR', issueClass: 'UNCONVENTIONAL CLASS', helpText: 'Data with unconventional classification')
                if (unconventionalClassificationSetsWarning) {
                    warnings.add(unconventionalClassificationSetsWarning)
                }
            }
            if (unconventionalUnitSets) {
                Double share

                if (amountToCountAgainst) {
                    share = unconventionalUnitSets.size() / amountToCountAgainst * 100
                } else {
                    share = 0
                }
                BimModelWarning unconventionalUnitSetsWarning = new BimModelWarning(datasetIds: unconventionalUnitSets.collect({ it.manualId }), amount: unconventionalUnitSets.size(), amountOfCombined: unconventionalUnitSets.findResults({ it.wasCombinedInto ?: null }).unique().size() + unconventionalUnitSets.findAll({ it.wasResolved }).size(), share: share.round(2), status: 'ERROR', issueClass: 'UNCONVENTIONAL UNIT', helpText: 'Data with unconventional unit')
                if (unconventionalUnitSetsWarning) {
                    warnings.add(unconventionalUnitSetsWarning)
                }
            }
        }
        return warnings
    }

    void combineDescriptiveDatasets(List<Dataset> allDatasets, importMapper) {

        List<Dataset> descriptiveDatasets = filterDescriptiveDatasets(allDatasets)
        if (!descriptiveDatasets) {
            return
        }

        List<ImportMapperCollapseGrouper> collapseGroupers = createCollapseGroupers(descriptiveDatasets, ["CLASS"], null,
                null, null, importMapper)
        if (!collapseGroupers) {
            return
        }

        Map<String, List<ImportMapperCollapseGrouper>> grouperGroupsByClass = groupCollapseGroupersByClass(collapseGroupers)
        List<ImportMapperCollapseGrouper> classCollapseGroupers = grouperGroupsByClass.values()?.flatten()

        if (classCollapseGroupers) {
            combineDatasets(classCollapseGroupers, allDatasets)
        }

    }

    void combineDatasets(List<ImportMapperCollapseGrouper> collapseGroupers, List<Dataset> datasets) {
        if (collapseGroupers && datasets) {
            for (ImportMapperCollapseGrouper collapseGrouper in collapseGroupers) {
                String collapseGrouperId = collapseGrouper.grouperId
                List<Dataset> toCombineDatasets = datasets.findAll { it ->
                    collapseGrouperId.equals(it.collapseGrouperId)
                }

                if (toCombineDatasets) {
                    Map<String, String> importDisplayFields
                    List<Map<String, String>> allImportDisplayFields = []
                    List<String> materialHeadings = []

                    for (int i = 0; i < toCombineDatasets.size(); i++) {
                        allImportDisplayFields.add(toCombineDatasets.get(i).allImportDisplayFields?.get(0))
                        materialHeadings.add(toCombineDatasets.get(i).ifcMaterialValue)

                        if (!importDisplayFields && i < (toCombineDatasets.size() - 1)) {
                            Map datasetImportDisplayFields = toCombineDatasets.get(i).importDisplayFields

                            if (datasetImportDisplayFields) {
                                importDisplayFields = datasetImportDisplayFields.intersect(toCombineDatasets.get(i + 1).importDisplayFields)
                            }
                        } else {
                            importDisplayFields = importDisplayFields?.intersect(toCombineDatasets.get(i).importDisplayFields)
                        }
                    }
                    Dataset first = toCombineDatasets.find({ it.trainingData })

                    if (!first) {
                        first = toCombineDatasets.get(0)
                    }
                    Dataset copy = datasetService.createCopy(first)

                    if (isResourceDataset(first)) {
                        Double quantity = 0
                        Double area_m2 = 0
                        Double volume_m3 = 0
                        Double mass_kg = 0
                        Double thickness_mm = 0
                        int thickness_mm_amount = 0
                        Double thickness_in = 0
                        int thickness_in_amount = 0
                        List<String> importWarnings = []

                        toCombineDatasets.each { Dataset d ->
                            if (d.quantity) {
                                quantity = quantity + d.quantity
                            }

                            if (d.area_m2) {
                                area_m2 = area_m2 + d.area_m2
                            }

                            if (d.volume_m3) {
                                volume_m3 = volume_m3 + d.volume_m3
                            }

                            if (d.mass_kg) {
                                mass_kg = mass_kg + d.mass_kg
                            }

                            if (d.additionalQuestionAnswers?.get("thickness_mm") && d.additionalQuestionAnswers.get("thickness_mm").toString().isNumber()) {
                                thickness_mm = thickness_mm + d.additionalQuestionAnswers.get("thickness_mm").toString().toDouble()
                                thickness_mm_amount++
                            }

                            if (d.additionalQuestionAnswers?.get("thickness_in") && d.additionalQuestionAnswers.get("thickness_in").toString().isNumber()) {
                                thickness_in = thickness_in + d.additionalQuestionAnswers.get("thickness_in").toString().toDouble()
                                thickness_in_amount++
                            }

                            if (d.importWarning) {
                                importWarnings.add(d.importWarning)
                            }
                        }

                        if (!importWarnings.isEmpty()) {
                            if (importWarnings.unique().size() == 1) {
                                copy.importWarning = importWarnings.first()
                            } else {
                                copy.importWarning = "Multiple warnings from combined rows. See Model checker Excel for detail."
                            }
                        }

                        if (thickness_mm && thickness_mm_amount) {
                            if (copy.additionalQuestionAnswers) {
                                copy.additionalQuestionAnswers.put("thickness_mm", thickness_mm / thickness_mm_amount)
                            } else {
                                copy.additionalQuestionAnswers = ["thickness_mm": thickness_mm / thickness_mm_amount]
                            }
                        } else {
                            copy.additionalQuestionAnswers?.remove("thickness_mm")
                        }

                        if (thickness_in && thickness_in_amount) {
                            if (copy.additionalQuestionAnswers) {
                                copy.additionalQuestionAnswers.put("thickness_in", thickness_in / thickness_in_amount)
                            } else {
                                copy.additionalQuestionAnswers = ["thickness_in": thickness_in / thickness_in_amount]
                            }
                        } else {
                            copy.additionalQuestionAnswers?.remove("thickness_in")
                        }
                        copy.quantity = quantity
                        copy.area_m2 = area_m2 ? area_m2.round(2) : null
                        copy.volume_m3 = volume_m3 ? volume_m3.round(2) : null
                        copy.mass_kg = mass_kg ? mass_kg.round(2) : null
                        copy.manualId = new ObjectId()
                        copy.answerIds = [quantity.toString()]
                        copy.collapseValues = null
                        copy.allImportDisplayFields = allImportDisplayFields
                        copy.originalDatasetsManualIds = toCombineDatasets.collect({ it.manualId })

                        String comment
                        if (materialHeadings.size() >= 1 && materialHeadings.unique().size() == 1) {
                            comment = "${materialHeadings.get(0)}, ${toCombineDatasets.size()} rows"
                        } else {
                            comment = "Combines ${toCombineDatasets.size()} rows"
                        }

                        if (copy.additionalQuestionAnswers) {
                            copy.additionalQuestionAnswers.put("comment", comment)
                        } else {
                            copy.additionalQuestionAnswers = ["comment": comment]
                        }
                    } else {
                        List<String> answers = []
                        List<String> importWarnings = []

                        toCombineDatasets.each { Dataset d ->
                            String answer = d.answerIds?.getAt(0)
                            if (answer) {
                                answers.add(answer)
                            }

                            if (d.importWarning) {
                                importWarnings.add(d.importWarning)
                            }
                        }

                        if (!importWarnings.isEmpty()) {
                            if (importWarnings.unique().size() == 1) {
                                copy.importWarning = importWarnings.first()
                            } else {
                                copy.importWarning = "Multiple warnings from combined rows. See Model checker Excel for detail."
                            }
                        }
                        copy.manualId = new ObjectId()
                        copy.answerIds = [answers?.join(" ")]
                        copy.collapseValues = null
                        copy.allImportDisplayFields = allImportDisplayFields
                        copy.originalDatasetsManualIds = toCombineDatasets.collect({ it.manualId })
                        fillDatasetImportFieldsId(copy)
                    }
                    if (importDisplayFields) {
                        copy.importDisplayFields = importDisplayFields
                        copy.importDisplayFields.put("Combines", "${toCombineDatasets.size()} rows")
                    } else {
                        copy.importDisplayFields = ["Combines": "${toCombineDatasets.size()} rows"]
                    }
                    datasets.removeAll({ collapseGrouperId.equals(it.collapseGrouperId) })
                    datasets.add(copy)
                }
            }
        }
    }

    Workbook xlsxFileParser(File tempFile, Integer maxColumns) {
        // CUSTOM XLSX PARSER, checks the amount of columns and throws index out of bounds if over maxColumns
        OPCPackage pkg = OPCPackage.open(tempFile)
        XSSFReader r = new XSSFReader(pkg)
        SharedStringsTable sst = r.getSharedStringsTable()
        XMLReader parser = fetchSheetParser(sst, maxColumns)
        Iterator<InputStream> sheets = r.getSheetsData()

        while (sheets.hasNext()) {
            InputStream sheet = sheets.next()
            InputSource sheetSource = new InputSource(sheet)
            // Custom parser checks if there are less than maxColumns columns, otherwise throws exception
            parser.parse(sheetSource)
            sheet.close()
        }
        return new XSSFWorkbook(tempFile)
    }

    XMLReader fetchSheetParser(SharedStringsTable sst, Integer maxColumns) {
        XMLReader parser = XMLHelper.newXMLReader()
        org.xml.sax.ContentHandler handler = new SheetHandler(sst, maxColumns)
        parser.setContentHandler(handler)
        return parser
    }

    void changeDatasetClass(Dataset dataset, ImportMapper importMapper, String newClass, String entityClass) {

        boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
        if (useOldImportMapper) {
            if (dataset && importMapper) {
                ImportMapperQuestionRule rule = newClass ? importMapper.questionMappingRules?.find({
                    it.matchingValues?.contains(newClass)
                }) : null

                if (!rule && entityClass) {
                    rule = importMapper.questionMappingRules?.find({ it.defaultTargetForEntityClass?.contains(entityClass) })
                }
                String queryId = rule?.target?.get("queryId")
                String sectionId = rule?.target?.get("sectionId")
                String questionId = rule?.target?.get("questionId")

                if (queryId && sectionId && questionId && dataset) {
                    dataset.queryId = queryId
                    dataset.sectionId = sectionId
                    dataset.questionId = questionId

                    if (newClass) {
                        if (dataset.trainingData) {
                            dataset.trainingData.put("CLASS", newClass)
                        }

                        if (dataset.trainingMatchData && (dataset.trainingMatchData.get("CLASS") || dataset.trainingMatchData.get("class"))) {
                            dataset.trainingMatchData.put("CLASS", newClass)
                        }

                        if (dataset.importDisplayFields) {
                            dataset.importDisplayFields.put("CLASS", newClass)
                        }
                        if (dataset.datasetImportFieldsId) {
                            datasetService.upDatasetImportFieldsByIdAndValueMap([(dataset.datasetImportFieldsId): ["CLASS": newClass]])
                        }
                    }
                }
                return
            }
        }

        if (!dataset || !importMapper) {
            return
        }

        Boolean isResourceDataset = isResourceDataset(dataset)
        List questionMappingRules = isResourceDataset ? importMapper.questionMappingRules : importMapper.descriptiveQuestionMappingRules

        def rule = newClass ? questionMappingRules?.find({
            it.matchingValues?.contains(newClass)
        }) : null

        if (isResourceDataset && !rule && entityClass) {
            rule = questionMappingRules?.find({ it.defaultTargetForEntityClass?.contains(entityClass) })
        }
        Map<String, String> ruleTargetForDataset = rule?.target

        if (validateQuestionMappingRuleTarget(ruleTargetForDataset)) {
            Boolean skipDataForCollapser = isResourceDataset
            changeDatasetTargetQuestionAndClass(dataset, ruleTargetForDataset, newClass, skipDataForCollapser)
        }
    }

    List<Dataset> changeDatasetsClass(List<Dataset> datasets, ImportMapper importMapper, String newClass, String entityClass, String oldClass, String additionalCat = null) {
        boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
        if (useOldImportMapper) {
            if (datasets && importMapper) {
                ImportMapperQuestionRule rule = newClass ? importMapper.questionMappingRules?.find({
                    it.matchingValues?.contains(newClass)
                }) : null

                if (!rule && entityClass) {
                    rule = importMapper.questionMappingRules?.find({ it.defaultTargetForEntityClass?.contains(entityClass) })
                }
                String queryId = rule?.target?.get("queryId")
                String sectionId = rule?.target?.get("sectionId")
                String questionId = rule?.target?.get("questionId")
                if (queryId && sectionId && questionId) {
                    datasets.each { Dataset dataset ->
                        if (dataset) {
                            boolean modifyThis = false
                            if (additionalCat) {
                                modifyThis = (dataset.originalClass?.equalsIgnoreCase(oldClass) || dataset.persistedOriginalClass?.equalsIgnoreCase(oldClass)) && dataset.categoryMaterialImport?.equalsIgnoreCase(additionalCat)
                            } else {
                                modifyThis = oldClass.equalsIgnoreCase(dataset.trainingData.get("CLASS"))
                            }
                            if (modifyThis) {
                                dataset.queryId = queryId
                                dataset.sectionId = sectionId
                                dataset.questionId = questionId

                                if (newClass) {
                                    if (dataset.trainingData) {
                                        dataset.trainingData.put("CLASS", newClass)
                                    }

                                    if (dataset.trainingMatchData && (dataset.trainingMatchData.get("CLASS") || dataset.trainingMatchData.get("class"))) {
                                        dataset.trainingMatchData.put("CLASS", newClass)
                                    }

                                    if (dataset.importDisplayFields) {
                                        dataset.importDisplayFields.put("CLASS", newClass)
                                    }
                                    if (dataset.dataForCollapser) {
                                        dataset.dataForCollapser.put("CLASS", newClass)
                                    }
                                    if (dataset.datasetImportFieldsId) {
                                        datasetService.upDatasetImportFieldsByIdAndValueMap([(dataset.datasetImportFieldsId): ["CLASS": newClass]])
                                    }
                                }
                            }

                        }
                    }
                }
            }
            return datasets
        }

        if (!datasets || !importMapper) {
            return datasets
        }

        ImportMapperQuestionRule rule = newClass ? importMapper.questionMappingRules?.find({
            it.matchingValues?.contains(newClass)
        }) : null

        if (!rule && entityClass) {
            rule = importMapper.questionMappingRules?.find({ it.defaultTargetForEntityClass?.contains(entityClass) })
        }

        ImportMapperDescriptiveQuestionRule descriptiveRule = newClass ? importMapper.descriptiveQuestionMappingRules?.find({
            it.matchingValues?.contains(newClass)
        }) : null

        Map<String, String> resourceRuleTarget = rule?.target
        Map<String, String> descriptiveRuleTarget = descriptiveRule?.target

        if (validateQuestionMappingRuleTarget(resourceRuleTarget) || validateQuestionMappingRuleTarget(descriptiveRuleTarget)) {
            datasets.each { Dataset dataset ->
                if (dataset) {
                    boolean modifyThis = false
                    if (additionalCat) {
                        modifyThis = (dataset.originalClass?.equalsIgnoreCase(oldClass) || dataset.persistedOriginalClass?.equalsIgnoreCase(oldClass)) && dataset.categoryMaterialImport?.equalsIgnoreCase(additionalCat)
                    } else {
                        modifyThis = oldClass.equalsIgnoreCase(dataset.trainingData.get("CLASS"))
                    }
                    if (modifyThis) {
                        Map<String, String> ruleTargetForDataset = isResourceDataset(dataset) ? resourceRuleTarget : descriptiveRuleTarget
                        changeDatasetTargetQuestionAndClass(dataset, ruleTargetForDataset, newClass)
                    }
                }
            }
        }
        return datasets
    }

    private void changeDatasetTargetQuestionAndClass(Dataset dataset, Map<String, String> questionMappingRuleTarget, String newClass, Boolean skipDataForCollapser = false) {

        if (!dataset || !questionMappingRuleTarget) {
            return
        }

        dataset.queryId = questionMappingRuleTarget.queryId
        dataset.sectionId = questionMappingRuleTarget.sectionId
        dataset.questionId = questionMappingRuleTarget.questionId

        if (newClass) {
            if (dataset.trainingData) {
                dataset.trainingData.put("CLASS", newClass)
            }

            if (dataset.trainingMatchData && (dataset.trainingMatchData.get("CLASS") || dataset.trainingMatchData.get("class"))) {
                dataset.trainingMatchData.put("CLASS", newClass)
            }

            if (dataset.importDisplayFields) {
                dataset.importDisplayFields.put("CLASS", newClass)
            }
            if (!skipDataForCollapser && dataset.dataForCollapser) {
                dataset.dataForCollapser.put("CLASS", newClass)
            }
            if (dataset.datasetImportFieldsId) {
                datasetService.upDatasetImportFieldsByIdAndValueMap([(dataset.datasetImportFieldsId): ["CLASS": newClass]])
            }
        }
    }

    /**
     * Validates target field of question rule, it must have fields queryId, sectionId and questionId
     * @param target - target field of {@link ImportMapperQuestionRule} or {@link ImportMapperDescriptiveQuestionRule}
     * @return true if target field is valid
     */
    private Boolean validateQuestionMappingRuleTarget(Map<String, String> target) {

        Boolean isTargetValid = false

        String queryId = target?.get("queryId")
        String sectionId = target?.get("sectionId")
        String questionId = target?.get("questionId")

        if (queryId && sectionId && questionId) {
            isTargetValid = true
        }
        return isTargetValid
    }

    public void handleDatasetTargets(List<Dataset> datasets, Indicator indicator, ImportMapper importMapper, String entityClass) {
        if (datasets && indicator && entityClass) {
            datasets.each { Dataset d ->
                if (!d.queryId || !indicator.indicatorQueries?.find({ it.queryId.equals(d.queryId) })) {
                    changeDatasetClass(d, importMapper, null, entityClass)
                }
            }
        }
    }

    int getRowsAmountFromFile(Workbook workbook, HttpSession session = null) {
        int rowCount = 0
        try {
            Integer numberOfSheets = workbook.getNumberOfSheets()
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i)

                if (sheet && !sheet.sheetName?.contains("@")) {
                    int rowPerSheet = sheet.lastRowNum

                    if (rowPerSheet > 0) {
                        rowCount += rowPerSheet
                    }
                }
            }
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in getRowsAmountFromFile", e)
            flashService.setFadeErrorAlert("Error in getRowsAmountFromFile: ${e.getMessage()}", true)
        }
        if (session) {
            session.removeAttribute("rowCounts")
            session.setAttribute("rowCounts", rowCount)
        }
        return rowCount
    }

    Integer getLimitAccordingToUsedLicenseFeature(String entityId, Indicator indicator, Integer defaultLimit, HttpSession session = null) {
        Integer limit

        if (entityId && indicator) {
            Entity entity = entityService.getEntityById(entityId, session)
            List<String> licensedFeatureIds = entity.getLicensesForIndicator(indicator)?.collect { it.licensedFeatureIds }?.flatten()

            if (licensedFeatureIds) {
                List<String> featureIds = Feature.withCriteria {
                    'in'('id', DomainObjectUtil.stringsToObjectIds(licensedFeatureIds))
                    projections {
                        property "featureId"
                    }
                }

                limit = Const.LimitFeatureId.values().findAll { it.featureId in featureIds }?.collect { it.limit }?.max()
            }
        }

        return limit == null ? defaultLimit : limit
    }

    /**
     * Method to remap non-standard unit quantity type namings to standard
     * (e.g. kpl -> pcs, tn -> ton) according to @mandatoryUnitRemappings specification in
     * One_Click_LCA_application.json config file
     * */
    private String remapUnitQuantityType(ImportMapper importMapper, String quantityTypeValue) {
        if (importMapper && importMapper.mandatoryUnitRemappings?.containsKey(quantityTypeValue?.toLowerCase())) {
            return importMapper.mandatoryUnitRemappings?.get(quantityTypeValue?.toLowerCase())
        } else {
            return quantityTypeValue
        }
    }

    List<ImportMapperPresetFilter> getPresetFilterObjects(List presetFilters) {
        List<ImportMapperPresetFilter> presetFilterList = []

        if (presetFilters) {
            presetFilters.each {
                presetFilterList.add((ImportMapperPresetFilter) it)
            }
        }
        return presetFilterList
    }

    List<String> getRequiredFieldsForRecognition(ImportMapper importMapper) {
        List<String> requiredFields = []
        if (importMapper) {
            List<String> trainingMatch = importMapper.trainingMatch
            List<ImportMapperDataRule> dataMappingRules = importMapper.dataMappingRules
            if (trainingMatch && dataMappingRules) {
                trainingMatch.each { String match ->
                    String target = dataMappingRules.find({
                        it.mappedHeadings && it.mappedHeadings*.toUpperCase().contains(match.toUpperCase())
                    })?.target?.toUpperCase()

                    if (target) {
                        requiredFields.add(target)
                    }
                }
            }
        }
        return requiredFields
    }

    /**
     * Incremental import is a fancy term we use to merge the datasets during import
     * @param newDatasets
     * @param childEntity
     * @param incrementalImport
     */
    void resolveIncrementalImportForDescriptiveDatasets(List<Dataset> newDatasets, Entity childEntity, Boolean incrementalImport) {
        if (!incrementalImport || !childEntity || !childEntity.datasets) {
            return
        }

        List<Dataset> descriptiveDatasets = filterDescriptiveDatasets(newDatasets)
        if (!descriptiveDatasets) {
            return
        }

        Set<Dataset> existingDatasets = childEntity.datasets
        for (Dataset descriptiveDataset in descriptiveDatasets) {
            Dataset matchExisting = datasetService.findMatchedDataset(existingDatasets, descriptiveDataset)

            if (matchExisting) {
                datasetService.mergeAnswerOfTwoDatasets(matchExisting, descriptiveDataset)
            }
        }
    }

    List<Dataset> filterDescriptiveDatasets(List<Dataset> datasets) {
        return datasets?.findAll { !isResourceDataset(it) }
    }

    Boolean isResourceDataset(Dataset dataset) {
        boolean useOldImportMapper = configurationService.getConfigurationValue("useOldImportMapper")?.toBoolean()
        if (useOldImportMapper) {
            return true
        }
        dataset?.resourceId || (isImportingDataset(dataset) && !isDescriptiveImportingDataset(dataset))
    }

    Boolean isImportingDataset(Dataset dataset) {
        dataset?.allImportDisplayFields
    }

    private Boolean isDescriptiveImportingDataset(Dataset importingDataset) {
        importingDataset?.allImportDisplayFields?.getAt(0)?.any { it.key.equalsIgnoreCase(Const.DESCRIPTIVE_DATA_COLUMN_NAME) }
    }

    List<String> getResourceDatasetDisplayHeadersForGroupedBy(List<ImportMapperDataRule> dataMappingRules) {

        List<String> headers = []

        dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->
            if ("RESOURCEID".equalsIgnoreCase(importMapperDataRule.target) || "QUANTITY".equalsIgnoreCase(importMapperDataRule.target) || "COMMENT".equalsIgnoreCase(importMapperDataRule.target)) {
                headers << importMapperDataRule.target.toUpperCase()
            }
        }

        return headers
    }

    List<String> getDescriptiveDatasetDisplayHeaders() {
        ["CLASS", Const.DESCRIPTIVE_DATA_COLUMN_NAME]
    }

    /**
     * Sets preset filters to session
     * @param importMapper
     * @param indicatorId
     * @param session
     * @param presetFilter
     * @param filterId can be null if it's RSEE import
     */
    void setPresetFilters(ImportMapper importMapper, String indicatorId, HttpSession session, ImportMapperPresetFilter presetFilter, String filterId = null) {
        if (filterId) {
            if (importMapper.presetFilters) {
                presetFilter = getPresetFilterObjects(importMapper.presetFilters).find({
                    filterId.equals(it.presetFilterId)
                })

                if (presetFilter) {
                    session?.setAttribute(Const.PRESET_FILTER, presetFilter)
                }
            }
        } else if (indicatorId) {
            if (importMapper.presetFilters) {
                presetFilter = getPresetFilterObjects(importMapper.presetFilters).find({
                    it.defaultForIndicators?.contains(indicatorId)
                })

                if (presetFilter) {
                    session?.setAttribute(Const.PRESET_FILTER, presetFilter)
                }
            }
        } else {
            session?.removeAttribute(Const.PRESET_FILTER)
        }
    }

    /**
     * Updates default user import settings
     * @param entityId from params
     * @param indicatorId from params
     * @param user
     */
    void updateUserDefaults(String entityId, String indicatorId, User user) {
        Map<String, String> userDefaults = new HashMap<String, String>()

        if (entityId) {
            userDefaults.put(Const.ENTITY_ID, entityId)
        }
        if (indicatorId) {
            userDefaults.put(Const.INDICATOR_ID, indicatorId)

        }
        user.importMapperDefaults = userDefaults
        user = userService.updateUser(user)
    }

    Map writeRseeToExcel(String indicatorId, String entityId, String batimentIndex, String batimentName, Integer rowCounts,
                          TemporaryImportData temporaryImportData, User user, Indicator indicator, Boolean fileHandlingOk, String rseeZoneMapping = null) {
        List<RSEnvDatasetDto> rsenvDatasetDtoList
        boolean isFecExport = false

        if (FrenchConstants.FEC_TOOLS.contains(indicatorId)) {
            rsenvDatasetDtoList = xmlService.getFecRseeFormatDatasets(entityId, batimentIndex.toInteger(), batimentName)
            isFecExport = true
        } else if (FrenchConstants.RE2020_TOOLS.contains(indicatorId)) {
            rsenvDatasetDtoList = xmlService.getRe2020RseeFormatDatasets(entityId, batimentIndex.toInteger(), batimentName, rseeZoneMapping)
        }

        Workbook wb = queryService.generateRseeExcel(rsenvDatasetDtoList, indicator, isFecExport)

        if (wb) {
            rowCounts = getRowsAmountFromFile(wb) ?: 0
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            wb.write(byteArrayOutputStream)
            temporaryImportData.importFile = byteArrayOutputStream.toByteArray()
            temporaryImportData.rowCounts = rowCounts
            wb.close()
            fileHandlingOk = true
        } else {
            log.error("User: ${user?.username} imported a file that exceeds the database MaxDocumentSize, unable to save original file.")
            flashService.setErrorAlert("User: ${user?.username} imported a file that exceeds the database MaxDocumentSize, unable to save original file.", true)
        }

        return [fileHandlingOk: fileHandlingOk, rowCounts: rowCounts]
    }

}