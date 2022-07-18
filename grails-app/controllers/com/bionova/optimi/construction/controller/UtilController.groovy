/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.service.IndicatorReportService
import com.bionova.optimi.core.service.LocalizationService
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.core.service.IndicatorQueryService
import grails.async.Promise

import static com.bionova.optimi.core.Constants.EntityClass
import static com.bionova.optimi.core.Constants.DesignStatus
import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.Benchmark
import com.bionova.optimi.core.domain.mongo.BenchmarkSettings
import com.bionova.optimi.core.domain.mongo.CDEarthquakeSetting
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.CalculationTotalResult
import com.bionova.optimi.core.domain.mongo.CarbonDesignerRegion
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.Constants as Const
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.DataCardRow
import com.bionova.optimi.core.domain.mongo.DataCardSection
import com.bionova.optimi.core.domain.mongo.DataLoadingFeature
import com.bionova.optimi.core.domain.mongo.DataLoadingFromFile
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.DatasetImportFields
import com.bionova.optimi.core.domain.mongo.DimensioningVariable
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.EntityTypeConstructionGroupAllocation
import com.bionova.optimi.core.domain.mongo.EolProcess
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.ImportMapperDataRule
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorBenchmark
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.PrivateClassificationList
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryApplyFilterOnCriteria
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.SimulationTool
import com.bionova.optimi.core.domain.mongo.SimulationToolConstruction
import com.bionova.optimi.core.domain.mongo.SimulationToolConstructionGroup
import com.bionova.optimi.core.domain.mongo.SimulationToolEnergyType
import com.bionova.optimi.core.domain.mongo.SimulationToolSession
import com.bionova.optimi.core.domain.mongo.SystemTrainingDataSet
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.util.UnitConversionUtil
import com.bionova.optimi.xml.fecEPD.EPDC
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.userdetails.GrailsUser
import groovy.json.JsonSlurper
import org.apache.catalina.connector.ClientAbortException
import org.apache.commons.codec.binary.Base64
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringEscapeUtils
import org.apache.commons.lang3.StringUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.tools.zip.ZipEntry
import org.apache.tools.zip.ZipOutputStream
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.OptimisticLockingException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.session.SessionInformation
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.util.HtmlUtils

import javax.servlet.http.HttpServletResponse
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.NumberFormat
import java.time.Year
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes

import static grails.async.Promises.task;

/**
 * @author Pasi-Markus Mäkelä
 */
class UtilController extends ExceptionHandlerController {

    def utilService
    def optimiResourceService
    def queryService
    def entityService
    def datasetService
    def indicatorService
    def userService
    def unitConversionUtil
    def calculateDifferenceUtil
    def resultFormattingResolver
    def applicationService
    def resourceTypeService
    def xmlService
    def importMapperTrainingDataService
    def configurationService
    def costStructureService
    def questionService
    def constructionService
    def featureService
    def optimiExcelImportService
    def indicatorBenchmarkService
    def newCalculationServiceProxy
    def optimiUserAgentIdentService
    def simulationToolService
    def zohoService
    def domainClassService
    def licenseService
    def eolProcessService
    def accountService
    def resourceFilterCriteriaUtil
    def importMapperService
    def channelFeatureService
    def productDataListService
    def stringUtilsService
    def maskingService
    def sessionRegistry
    def privateClassificationListService
    def accountImagesService
    def additionalQuestionService
    def groovyPageRenderer
    def dataCardService
    def fecService
    def calculationRuleService
    def valueReferenceService
    def calculationProcessService
    def queryParamsService
    ScopeFactoryService scopeFactoryService
    def newCalculationService
    ResourceService resourceService
    IndicatorQueryService indicatorQueryService
    IndicatorReportService indicatorReportService
    LocalizationService localizationService

    @Autowired
    EmailConfiguration emailConfiguration

    static allowedMethods = [create: "POST", update: "POST", delete: "POST", read: "GET"]

    private static final Integer resourceShowLimit = 250
    private static final String ECOINVENT_CLASSIFICATION_PARAMETER_ID = '1'
    private static final List<String> ATTR_IDS_FOR_DOUBLE_CHECKING = [
            Constants.DEFAULT_TRANSPORT_BOVERKET_ATTR_ID, Constants.DEFAULT_TRANSPORT_BOVERKET_LEG2_ATTR_ID
    ]
    private static final List<String> TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_IDS = [
            Constants.TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_ID, Constants.TRANSPORT_RESOURCE_ID_BOVERKET_LEG2_ATTR_ID
    ]

    def downloadEntityFile() {
        String fileId = params.id
        if (fileId) {
            EntityFile file = EntityFile.read(fileId)

            if (file) {
                response.setContentType(file.contentType)
                response.setHeader("Content-disposition", "filename=${file.name}")
                response.outputStream << file.data
            } else {
                render text: "File not found!"
            }
        } else {
            render text: "File not found!"
        }
    }

    def removeEntityFile() {
        String fileId = params.fileId
        entityService.removeEntityFile(fileId)
        render([output: 'ok', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def saveUserInput() {
        String datasetId = params.datasetId
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String userInputParamName = params.keySet()?.find({
            !"controller".equals(it) && !"action".equals(it) &&
                    !"datasetId".equals(it) && !"entityId".equals(it) && !"indicatorId".equals(it)
        })
        String returnable = ""

        if (datasetId && entityId && indicatorId && userInputParamName) {
            Entity entity = entityService.getEntityById(entityId)
            if (entity) {
                Dataset dataset = entity.datasets?.find({ datasetId.equals(it.manualId) })
                if (dataset) {
                    if (userInputParamName.contains("_additional_")) {
                        String additionalQuestionId = userInputParamName.split("_additional_")[1]

                        if (additionalQuestionId.contains('.')) {
                            additionalQuestionId = additionalQuestionId.tokenize('.')[0]

                            if (dataset.additionalQuestionAnswers) {
                                dataset.additionalQuestionAnswers.put(additionalQuestionId, params.get(userInputParamName))
                            } else {
                                dataset.additionalQuestionAnswers = [(additionalQuestionId): params.get(userInputParamName)]
                            }

                            if ("profileId".equals(additionalQuestionId)) {
                                dataset.profileId = params.get(userInputParamName)
                            }
                        }
                    } else {
                        dataset.answerIds = [params.get(userInputParamName)]
                    }
                    entity.merge(flush: true)
                    returnable = "ok"
                    session?.setAttribute("calculationNeeded", [(entityId): indicatorId])
                }
            }
        }
        render text: returnable
    }

    def getDefaultQuantityAnswer(DataLoadingFeature dataLoadingFeature, Resource resource, String quantityResolutionValue) {
        String quantityAnswer
        if (dataLoadingFeature && resource) {
            if (dataLoadingFeature.loadResourceDefaultQuantityFromValue) {
                quantityAnswer = DomainObjectUtil.callGetterByAttributeName(dataLoadingFeature.loadResourceDefaultQuantityFromValue, resource)?.toString()

                if (quantityAnswer && !quantityAnswer.replace(",", ".").isNumber()) {
                    quantityAnswer = ""
                }
            }

            List<Map<String, Object>> quantityResolutionValues = dataLoadingFeature.loadQuantityResolutionValues
            String resourceVariable = dataLoadingFeature.loadQuantityResolutionMatchingResourceVariable

            if (quantityResolutionValues && resourceVariable) {
                String valueToMatch = DomainObjectUtil.callGetterByAttributeName(resourceVariable, resource)?.toString()

                if (valueToMatch) {
                    if (quantityResolutionValue && !"undefined".equals(quantityResolutionValue)) {
                        quantityAnswer = quantityResolutionValues.find({
                            quantityResolutionValue?.equals(it.get("variable"))
                        })?.get(valueToMatch)
                    } else {
                        quantityAnswer = quantityResolutionValues.find({ it.get("defaultValue") })?.get(valueToMatch)
                    }
                } else {
                    log.error("DataLoadingFeature ${dataLoadingFeature.buttonName?.get("EN")}: No loadQuantityResolutionMatchingResourceVariable value (${resourceVariable}) found for resource: ${resource.resourceId} / ${resource.profileId}")
                    flashService.setErrorAlert("DataLoadingFeature ${dataLoadingFeature.buttonName?.get("EN")}: No loadQuantityResolutionMatchingResourceVariable value (${resourceVariable}) found for resource: ${resource.resourceId} / ${resource.profileId}", true)
                }

                if (quantityAnswer && !quantityAnswer.replace(",", ".").isNumber()) {
                    quantityAnswer = ""
                }
            }

            if (dataLoadingFeature.loadResourceParameterDecimals) {
                Integer loadResourceParameterDecimals = dataLoadingFeature.loadResourceParameterDecimals

                if (quantityAnswer && quantityAnswer.replace(",", ".").isNumber()) {
                    quantityAnswer = formatNumber(number: DomainObjectUtil.convertStringToDouble(quantityAnswer), type: "number", minFractionDigits: loadResourceParameterDecimals, roundingMode: "HALF_UP")
                }
            }
        }
        return quantityAnswer
    }


    def addResourceRow() {
        long now = System.currentTimeMillis()
        String resourceId = params.resourceId
        String entityId = params.entityId
        String constructionId = "undefined".equals(params.constructionId) || !params.constructionId ? null : params.constructionId
        String uniqueConstructionIdentifier = "undefined".equals(params.uniqueConstructionIdentifier) || !params.uniqueConstructionIdentifier ? null : params.uniqueConstructionIdentifier
        String previousSeqNo = params.previousSeqNro
        String datasetIdFromConstruction = params.datasetManualId
        Boolean constructionPage = params.boolean("constructionPage")
        Boolean productDataListPage = params.boolean("productDataListPage")
        Entity entity = entityService.getEntityById(entityId)
        Boolean splitPage = params.boolean("splitPage")
        Boolean modifiable = params.boolean("modifiable")
        String splitRowId = params.splitRowId
        Double originalQuantity = params.double("originalQuantity")
        Double originalThickness = params.double("originalThickness")
        String originalUnit = params.originalUnit
        String originalId = params.originalId
        String originalDatasetAnswers = "undefined".equals(params.originalDatasetAnswers) || !params.originalDatasetAnswers ? null : params.originalDatasetAnswers
        String persistChangedManualId = "undefined".equals(params.persistChangedManualId) || !params.persistChangedManualId ? null : params.persistChangedManualId
        String copiedDesignId= params.copiedDesignId
        String productDataListId = params.productDataListId
        Double groupParentConstructionQty = params.double("groupParentConstructionQty")
        ProductDataList productDataList = productDataListService.getProductDataListById(productDataListId)
        String customDatasetName

        if (resourceId) {

            if (resourceId.contains(" ")) {
                resourceId = resourceId.tokenize(" ")[0]
            }

            if (productDataList) {
                customDatasetName = productDataList.datasets?.find({it.resourceId == resourceId})?.groupingDatasetName
            }

            String profileId = params.profileId

            // ProfileId might be "null" if a construction has a inactive nondefault resource
            // Workaround for task 9225 for getDefaultProfileForResource not setting profileId correctly for dataset, discussed w Arturs.
            if ("undefined".equalsIgnoreCase(profileId)||"null".equalsIgnoreCase(profileId)) {
                profileId = null
            }
            Resource resource = optimiResourceService.getResourceWithParams(resourceId, profileId, uniqueConstructionIdentifier ? Boolean.TRUE : null)

            if (resource) {
                User user = userService.getCurrentUser(true)
                String quarterlyInputEnabledParam = params.quarterlyInputEnabled
                def quarterlyInputEnabled = new Boolean(quarterlyInputEnabledParam)
                String monthlyEnabledParam = params.monthlyInputEnabled
                def monthlyInputEnabled = new Boolean(monthlyEnabledParam)
                def indicatorId = params.indicatorId
                def indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                String queryId = params.queryId
                String sectionId = params.sectionId
                String questionId = params.questionId
                def resourceTableId = params.resourceTableId
                def fieldName = params.fieldName
                def trId = resourceTableId + UUID.randomUUID().toString().replaceAll('-', '')
                Query query = queryService.getQueryByQueryIdWithoutCache(queryId, true)
                Question question = query?.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
                Double defaultQuantity = question?.defaultQuantity
                Boolean preventDoubleEntries = Boolean.FALSE
                QuerySection section = query?.getAllSections()?.find({ it.sectionId.equals(sectionId) })
                String firstSectionId = query?.getAllSections()?.get(0)?.sectionId
                def multipleChoicesAllowed = question?.defineMultipleChoicesAllowed(section)
                def quantity = question?.quantity
                Entity parentEntity = entity?.parentById
                List<Question> additionalQuestions = utilService.getAdditionalQuestions(question, indicator, parentEntity, entity, query)
                def additionalQuestionAnswers = [:]
                DecimalFormat decimalFormat = userService.getDefaultDecimalFormat()
                String quantityMissingStyle = ""
                Boolean showGWP = new Boolean(params.showGWP)
                String eolProcessingType = resource.eolProcessingType ?: resourceService.getSubType(resource)?.eolProcessingType
                String quantityAnswer
                String quantityForDataLoadFromFile
                String parentConstructionId = "undefined".equals(params.parentConstructionId) || !params.parentConstructionId ? null : params.parentConstructionId // FROM NMD3 CURRENTLY FOR CONSTITUENTS
                String constructionUnit
                String constructionValue
                Map<String, List<Document>> additionalQuestionResources = [:]
                Dataset copiedDataset

                Boolean showWarning = Boolean.FALSE
                String indicatorWarningMessage = null
                Map<String, List<Object>> warningResourceFilterCriteria = question?.getWarningResourceFilterCriteria(indicator, entity)
                if (warningResourceFilterCriteria) {
                    showWarning = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null,
                            warningResourceFilterCriteria.findAll{!(it.key in [IndicatorQuery.WARNING_MESSAGE_INDICATOR, IndicatorQuery.BLOCK_MESSAGE_INDICATOR])}, true) ? Boolean.TRUE : Boolean.FALSE

                    if(showWarning){
                        indicatorWarningMessage = warningResourceFilterCriteria.get(IndicatorQuery.WARNING_MESSAGE_INDICATOR)?.get(0)
                    }
                }


                Entity copiedDesign
                if (copiedDesignId) {
                    String copiedUniqueConstructionIdentifier = params.copiedUniqueConstructionIdentifier
                    copiedDesign = entityService.getEntityById(copiedDesignId)
                    if(originalId){
                        copiedDataset = copiedDesign.datasets.find({originalId.equals(it.manualId)})
                    }
                    if(!copiedDataset){
                        copiedDataset = datasetService.getUniqueDataset(copiedDesign, queryId, sectionId, questionId, resourceId, copiedUniqueConstructionIdentifier)
                    }
                }

                DataLoadingFeature dataLoadingFeature = query?.dataLoadingFeature
                DataLoadingFromFile dataLoadingFromFile = query?.dataLoadingFromFile
                Integer loadResourceParameterDecimals

                if (dataLoadingFeature) {
                    loadResourceParameterDecimals = dataLoadingFeature.loadResourceParameterDecimals
                    quantityAnswer = getDefaultQuantityAnswer(dataLoadingFeature, resource, params.quantityResolutionValue)
                }
                if(dataLoadingFromFile){
                    if(params.resQuantity){
                        quantityForDataLoadFromFile = params.resQuantity
                    }
                }
                def additionalQuestionAnswerFromConstruction
                Boolean inheritConstructionServiceLife = Boolean.FALSE
                String nmdProfileSetString = "undefined".equals(params.nmdProfileSetString) || !params.nmdProfileSetString ? null : params.nmdProfileSetString
                Map scalingParameters

                if (constructionId) {
                    Construction construction = constructionService.getConstruction(constructionId)
                    Dataset d

                    if (copiedDesign && "dummyGroupResource".equals(constructionId)) {
                        d = copiedDataset
                    } else {
                        d = construction?.datasets?.find({ datasetIdFromConstruction?.equals(it.manualId) })
                    }

                    if (d) {
                        parentConstructionId = constructionId
                        inheritConstructionServiceLife = construction?.inheritConstructionServiceLife

                        scalingParameters = constructionService.getNMD3ScalingParameters(resource)
                        additionalQuestionAnswerFromConstruction = utilService.getAdditionalQuestionAnswerFromConstruction(d, scalingParameters, additionalQuestions, construction)

                        if ("imperial".equals(user?.unitSystem)) {
                            constructionUnit = d.imperialUnit ?: unitConversionUtil.europeanUnitToImperialUnit(d.userGivenUnit)
                            if (d.constructionValue) {
                                constructionValue = d.constructionValue
                            } else if (d.quantity) {
                                Double convertedValue = unitConversionUtil.doConversion(d.quantity, datasetService.getThickness(d.additionalQuestionAnswers), constructionUnit, resource, null, null, null, Boolean.TRUE, null, d.userGivenUnit)

                                if (convertedValue) {
                                    constructionValue = groupParentConstructionQty ? new BigDecimal((convertedValue / groupParentConstructionQty).toString()).toPlainString() : new BigDecimal(convertedValue.toString()).toPlainString()
                                }
                            }
                        } else {
                            constructionUnit = d.userGivenUnit
                            if (d.constructionValue) {
                                constructionValue = d.constructionValue
                            } else if (d.quantity) {
                                constructionValue = groupParentConstructionQty ? new BigDecimal((d.quantity / groupParentConstructionQty).toString()).toPlainString() : new BigDecimal(d.quantity.toString()).toPlainString()
                            }
                        }
                    } else if (parentConstructionId) {
                        constructionUnit = resource?.unitForData
                        constructionValue = "1"
                    }
                }

                if (!question?.optional && question?.quantity && !quantityForDataLoadFromFile) {
                    quantityMissingStyle = "border: 1px solid red;"
                }

                if (splitPage && originalQuantity && !originalId) {
                    quantityAnswer = originalQuantity
                }

                if (!quantityAnswer && defaultQuantity != null) {
                    quantityAnswer = defaultQuantity
                }
                if(copiedDataset && copiedDataset?.quantity){
                    quantityAnswer = copiedDataset.quantity
                }
                //Copy value from parent entity if copyFromParent is true
                def copyQuantity = question?.copyQuantity
                if(copyQuantity?.copyFromParent && parentEntity){
                    quantityAnswer = parentEntity.datasets?.find{it.questionId == copyQuantity.questionId && it.sectionId == copyQuantity.sectionId && it.queryId == copyQuantity.queryId}?.quantity
                }
                //********************************************************
                String defaultLocalCompCountry = datasetService.getDefaultLocalCompensationCountry(parentEntity)
                Resource projectCountryResource = parentEntity?.countryResource
                Map<String, Boolean> additionalQuestionLicensed = [:]
                Map<String, Boolean> additionalQuestionShowAsDisabled = [:]
                List<String> licensedFeatureIds = licenseService.getLicensedFeatureIdsOfEntity(parentEntity)

                if (additionalQuestions) {

                    for (Question additionalQuestion in additionalQuestions) {
                        additionalQuestionService.putAdditionalQuestionResourcesToMap(additionalQuestion, additionalQuestionResources, indicator, queryId, session)
                        licenseService.putLicensedStatusOfQuestionToMap(additionalQuestion, additionalQuestionLicensed, licensedFeatureIds, indicator, additionalQuestionShowAsDisabled)
                    }

                    additionalQuestionAnswers = copiedDataset ? copiedDataset.additionalQuestionAnswers : utilService.getAdditionalQuestionAnswers(additionalQuestions, question, resource,
                            resource?.resourceTypeObject, additionalQuestionAnswerFromConstruction, decimalFormat, originalThickness)

                }
                Boolean preventChanges = params.boolean("preventChanges") ? params.boolean("preventChanges") : indicator?.preventChanges(query)
                Boolean newResourceRowAdded = Boolean.FALSE

                if (question?.preventDoubleEntries) {
                    preventDoubleEntries = Boolean.TRUE
                }

                if (params.newResourceOnSelect) {
                    newResourceRowAdded = params.newResourceOnSelect
                }
                def datasets = entity?.datasets?.findAll({
                    queryId.equals(it.queryId) &&
                            sectionId.equals(it.sectionId) && questionId.equals(it.questionId)
                })
                boolean duplicateFound = false

                if (datasets) {
                    if (preventDoubleEntries && datasets.find({
                        resource.resourceId.equals(it.resourceId) &&
                                resource.profileId.equals(it.profileId)
                    })) {
                        duplicateFound = true
                    }
                }

                if (!duplicateFound) {
                    Question additionalQuestion = additionalQuestions?.find({ it.isThicknessQuestion })
                    Map<Boolean, List<String>> useUserGivenUnit = unitConversionUtil.useUserGivenUnit(resource)

                    Question useResourceUnitCost = additionalQuestions?.find({
                        "costPerUnit".equals(it.questionId) && it.defaultValueFromResource
                    })
                    Boolean materialServiceLifeWarning = Boolean.FALSE
                    Double assestmentPeriod = (Double) indicator?.assessmentPeriodFixed ? indicator.assessmentPeriodFixed : valueReferenceService.getDoubleValueForEntity(indicator?.assessmentPeriodValueReference, entity) ? valueReferenceService.getDoubleValueForEntity(indicator?.assessmentPeriodValueReference, entity) : 0

                    if (question?.materialServiceLifeWarning && resource.serviceLife && resource.serviceLife > 0 && resource.serviceLife < assestmentPeriod) {
                        materialServiceLifeWarning = Boolean.TRUE
                    }
                    Double defaultResourceUnitCostFromResource
                    Double resourceTypeCostMultiplier = resource.resourceTypeCostMultiplier

                    if (useResourceUnitCost) {
                        try {
                            defaultResourceUnitCostFromResource = (Double) DomainObjectUtil.callGetterByAttributeName(useResourceUnitCost.defaultValueFromResource, resource)
                            Double currencyMultiplier = valueReferenceService.getDoubleValueForEntity(indicator?.currencyExchangeValueReference, entity)

                            if (currencyMultiplier && defaultResourceUnitCostFromResource) {
                                defaultResourceUnitCostFromResource = defaultResourceUnitCostFromResource * currencyMultiplier
                            }

                            if (resourceTypeCostMultiplier && defaultResourceUnitCostFromResource) {
                                defaultResourceUnitCostFromResource = defaultResourceUnitCostFromResource * resourceTypeCostMultiplier
                            }
                        } catch (Exception e) {
                            loggerUtil.error(log, "Could not use ${useResourceUnitCost.defaultValueFromResource} defaultValueFromResource ${resource?.resourceId} / ${resource?.profileId}", e)
                            flashService.setErrorAlert("Could not use ${useResourceUnitCost.defaultValueFromResource} defaultValueFromResource ${resource?.resourceId} / ${resource?.profileId}: ${e.message}", true)
                        }
                    }
                    Boolean hideCopyRows = query?.hideCopyRows
                    Boolean constructionResource = Boolean.FALSE
                    Boolean unbundable = Boolean.FALSE
                    Boolean preventExpand = Boolean.FALSE
                    String constructionTypeImage

                    if (resource.construction) {
                        constructionResource = Boolean.TRUE
                        Construction construction = constructionService.getConstruction(resource.constructionId)

                        if (construction) {
                            unbundable = construction.unbundable
                            preventExpand = construction.preventExpand

                            if ("imperial".equals(user?.unitSystem)) {
                                constructionUnit = construction.imperialUnit ?: unitConversionUtil.europeanUnitToImperialUnit(construction.unit)
                            } else {
                                constructionUnit = construction.unit
                            }

                            scalingParameters = constructionService.getScalingParameters(construction)

                            if (additionalQuestionAnswers) {
                                construction.additionalQuestionAnswers?.each {
                                    additionalQuestionAnswers.put(it.key, it.value)
                                }
                            } else {
                                additionalQuestionAnswers = construction.additionalQuestionAnswers ?: [:]
                            }
                        }

                        if (!constructionUnit && copiedDataset?.userGivenUnit) {
                            constructionUnit = copiedDataset?.userGivenUnit
                        }

                        String constructionType = resource.constructionType
                        constructionTypeImage = optimiResourceService.getConstructionIcon(asset, constructionType)
                    }
                    if(resource.isMultiPart || resource.multipart){
                        constructionTypeImage = optimiResourceService.getConstructionIcon(asset, com.bionova.optimi.core.Constants.STRUCTURE_CT)
                    }
                    Integer seqNo = previousSeqNo ? previousSeqNo.toInteger() + 1 : 0
                    Boolean hideThickness = Boolean.FALSE
                    def key = useUserGivenUnit?.keySet()?.toList()

                    if(copiedDataset && copiedDataset?.userGivenUnit){
                        if (key && !key.isEmpty() && key.get(0)) {
                            def unitForDataset = copiedDataset?.userGivenUnit
                            useUserGivenUnit?.get(key.get(0))?.removeAll{ it == unitForDataset}
                            useUserGivenUnit?.get(key.get(0))?.add(0, unitForDataset)
                        }

                        if (resource.isDummy) {
                            customDatasetName = copiedDataset?.groupingDatasetName
                        }
                    }

                    if (!parentConstructionId && !constructionResource) {
                        if (key && !key.isEmpty() && key.get(0)) {
                            def values = useUserGivenUnit.get(key.get(0))

                            if (values && !values.isEmpty()) {
                                def value = values.get(0)

                                if (value && !value.equalsIgnoreCase("m2") && !value.equalsIgnoreCase("sq ft")) {
                                    if (splitPage) {
                                        if ((originalUnit && !originalUnit.equalsIgnoreCase("m2") && !originalUnit.equalsIgnoreCase("sq ft")) || !CollectionUtils.containsAny(values, ["m2","sq ft"])) {
                                            hideThickness = Boolean.TRUE
                                        }
                                    } else {
                                        hideThickness = Boolean.TRUE
                                    }
                                } else if (splitPage && originalUnit && !originalUnit.equalsIgnoreCase("m2") && !originalUnit.equalsIgnoreCase("sq ft")) {
                                    hideThickness = Boolean.TRUE
                                }
                            }
                        } else if ((!resource.unitForData?.equalsIgnoreCase("m2") && !resource.unitForData?.equalsIgnoreCase("sq ft")) || (originalUnit && !originalUnit.equalsIgnoreCase("m2") && !originalUnit.equalsIgnoreCase("sq ft"))) {
                            hideThickness = Boolean.TRUE
                        }
                    } else if ((parentConstructionId||constructionResource) && constructionUnit && !["m2", "sq ft"].contains(constructionUnit.toLowerCase())) {
                        hideThickness = Boolean.TRUE
                    }
                    Boolean automaticCostLicensed
                    if (indicator?.isCostCalculationAllowed) {
                        automaticCostLicensed = parentEntity?.automaticCostCalculationLicensed
                    }
                    String manualId = persistChangedManualId ?: new ObjectId()?.toString()

                    additionalQuestions = questionService.handleGroupedQuestions(additionalQuestions)

                    String sourceListingDirection = "top"

                    if (firstSectionId && section && firstSectionId.equalsIgnoreCase(section.sectionId)) {
                        sourceListingDirection = "right"
                    }

                    String datasetImportFieldsId

                    if (originalDatasetAnswers) {
                        Dataset original = entity?.datasets?.find({ originalDatasetAnswers.equals(it.manualId) })

                        if (original) {
                            //Only used when replacing a resource to keep additionalQuestionAnsers
                            datasetImportFieldsId = original.datasetImportFieldsId

                            if (additionalQuestionAnswers) {
                                original.additionalQuestionAnswers?.each { mapKey, mapValue ->
                                    if (mapValue) {
                                        additionalQuestionAnswers.put(mapKey, mapValue)
                                    }
                                }
                            } else {
                                additionalQuestionAnswers = original.additionalQuestionAnswers
                            }

                            if (additionalQuestionAnswers) {
                                additionalQuestionAnswers.remove("profileId")
                                additionalQuestionAnswers["profileId"] = resource?.profileId
                            }
                         }
                    }
                    Boolean isManager = parentEntity?.managerIds?.contains(user?.id?.toString()) || userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)
                    def disabledForTaskUser = false


                    if (!parentEntity) {
                        if ((modifiable != null && !modifiable) || (entity && !entity.modifiable)) {
                            disabledForTaskUser = true
                        }
                    } else {
                        if ((modifiable != null && !modifiable) || (parentEntity && !parentEntity.modifiable) || entity?.locked) {
                            disabledForTaskUser = true
                        }
                    }
                    String lcaModel = parentEntity?.lcaModel
                    String costCalculationMethod = parentEntity?.costCalculationMethod
                    String localCompensationMethodVersion = datasetService.getDefaultLocalCompensationMethodVersion(parentEntity)
                    Map<String, Integer> additionalQuestionInputWidth = indicator?.indicatorQueries?.find({ it.queryId == queryId })?.additionalQuestionInputWidth
                    Boolean groupMaterialsAllowed = featureService.isGroupMaterialFeatureAllowed(query, licensedFeatureIds, indicator)
                    Boolean createConstructionsAllowed = (groupMaterialsAllowed && licensedFeatureIds.any({ it == Feature.CREATE_CONSTRUCTIONS })) ? true : false
                    Boolean publicGroupAllowed = (createConstructionsAllowed && licensedFeatureIds.any({ it == Feature.SUBMIT_PUBLIC_CONSTRUCTIONS })) ? true : false
                    Map <String, String> questionsWithAdditionalCalculationScripts = queryService.groupAdditionalCalculationScriptsByQuestions(query)

                    //SW-1525: check for enabling or disabling nmdReuseMaterial checkbox
                    Boolean disabledFlag
                    Question nmdAddQuestion = additionalQuestions?.find { it.questionId == com.bionova.optimi.core.Constants.NMD_REUSED_MATERIAL_QUESTIONID }
                    if (nmdAddQuestion) {
                        disabledFlag = additionalQuestionService.enableDisableFieldByEnablingCondition(nmdAddQuestion, resource)
                    }

                    //SW-1882
                    //verifiedProductFlag is added to check verified building product resources.
                    Boolean verifiedProductFlag = false
                    if (parentEntity.entityClass == EntityClass.PRODUCT.getType()) {
                        verifiedProductFlag = true
                    }

                    log.info("Resource row backend handled in: ${System.currentTimeMillis() - now} ms")
                    String templateAsString = groovyPageRenderer.render(template: "/query/resourcerow", model: [indicator                                : indicator,
                                                                                                                quarterlyInputEnabled                    : quarterlyInputEnabled,
                                                                                                                monthlyInputEnabled                      : monthlyInputEnabled,
                                                                                                                resource                                 : resource,
                                                                                                                resourceTableId                          : resourceTableId,
                                                                                                                trId                                     : trId,
                                                                                                                quantity                                 : quantity,
                                                                                                                fieldName                                : fieldName,
                                                                                                                disabledForTaskUser                      : disabledForTaskUser,
                                                                                                                customDatasetName                        : customDatasetName,
                                                                                                                groupMaterialsAllowed                    : groupMaterialsAllowed,
                                                                                                                additionalQuestions                      : additionalQuestions,
                                                                                                                additionalQuestionAnswers                : additionalQuestionAnswers,
                                                                                                                mainSection                              : section,
                                                                                                                query                                    : query,
                                                                                                                question                                 : question,
                                                                                                                parentEntity                             : parentEntity,
                                                                                                                additionalQuestionShowAsDisabled         : additionalQuestionShowAsDisabled,
                                                                                                                additionalQuestionInputWidth             : additionalQuestionInputWidth,
                                                                                                                showSaveWarning                          : true,
                                                                                                                quantityMissing                          : quantityMissingStyle,
                                                                                                                multipleChoicesAllowed                   : multipleChoicesAllowed,
                                                                                                                useUserGivenUnit                         : useUserGivenUnit,
                                                                                                                showGWP                                  : showGWP,
                                                                                                                preventChanges                           : preventChanges,
                                                                                                                defaultResourceUnitCostFromResource      : defaultResourceUnitCostFromResource,
                                                                                                                inWarningFilter                          : showWarning,
                                                                                                                warningMessage                           : indicatorWarningMessage,
                                                                                                                questionsWithAdditionalCalculationScripts: questionsWithAdditionalCalculationScripts,
                                                                                                                additionalQuestionId                     : additionalQuestion?.questionId,
                                                                                                                entity                                   : entity,
                                                                                                                profileId                                : profileId,
                                                                                                                newResourceRowAdded                      : newResourceRowAdded,
                                                                                                                seqNo                                    : seqNo,
                                                                                                                useResourceUnitCost                      : useResourceUnitCost,
                                                                                                                thicknessQuestion                        : additionalQuestion,
                                                                                                                additionalQuestionLicensed               : additionalQuestionLicensed,
                                                                                                                costCalculationMethod                    : costCalculationMethod,
                                                                                                                hideCopyRows                             : hideCopyRows,
                                                                                                                materialServiceLifeWarning               : materialServiceLifeWarning,
                                                                                                                quantityAnswer                           : quantityAnswer,
                                                                                                                constructionResource                     : constructionResource,
                                                                                                                parentConstructionId                     : parentConstructionId,
                                                                                                                defaultLocalCompCountry                  : defaultLocalCompCountry,
                                                                                                                projectCountryResource                   : projectCountryResource,
                                                                                                                productDataListPage                      : productDataListPage,
                                                                                                                constructionUnit                         : constructionUnit,
                                                                                                                uniqueConstructionIdentifier             : uniqueConstructionIdentifier,
                                                                                                                constructionValue                        : constructionValue,
                                                                                                                loadResourceParameterDecimals            : loadResourceParameterDecimals,
                                                                                                                resourceTypeCostMultiplier               : resourceTypeCostMultiplier,
                                                                                                                user                                     : user,
                                                                                                                constructionTypeImage                    : constructionTypeImage,
                                                                                                                localCompensationMethodVersion           : localCompensationMethodVersion,
                                                                                                                datasetIdFromConstruction                : datasetIdFromConstruction,
                                                                                                                unbundable                               : unbundable,
                                                                                                                preventExpand                            : preventExpand,
                                                                                                                assestmentPeriod                         : assestmentPeriod,
                                                                                                                scalingParameters                        : scalingParameters,
                                                                                                                eolProcessingType                        : eolProcessingType,
                                                                                                                constructionPage                         : constructionPage,
                                                                                                                splitPage                                : splitPage,
                                                                                                                modifiable                               : modifiable,
                                                                                                                splitRowId                               : splitRowId,
                                                                                                                originalUnit                             : originalUnit,
                                                                                                                originalId                               : originalId,
                                                                                                                originalQuantity                         : originalQuantity,
                                                                                                                hideThickness                            : hideThickness,
                                                                                                                automaticCostLicensed                    : automaticCostLicensed,
                                                                                                                manualId                                 : manualId,
                                                                                                                additionalQuestionResources              : additionalQuestionResources,
                                                                                                                sourceListingDirection                   : sourceListingDirection,
                                                                                                                datasetImportFieldsId                    : datasetImportFieldsId,
                                                                                                                isManager                                : isManager,
                                                                                                                inheritConstructionServiceLife           : inheritConstructionServiceLife,
                                                                                                                nmdProfileSetString                      : nmdProfileSetString,
                                                                                                                lcaModel                                 : lcaModel,
                                                                                                                qtyForDataLoadFromFile                   : quantityForDataLoadFromFile,
                                                                                                                publicGroupAllowed                       : publicGroupAllowed,
                                                                                                                createConstructionsAllowed               : createConstructionsAllowed,
                                                                                                                disabledFlag                             : disabledFlag,
                                                                                                                verifiedProductFlag                      : verifiedProductFlag])
                    render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                } else {
                    render([output: "duplicate", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                }
            } else {
                log.error("RESOURCE ROW ADD: Error: Unable to find resource with params: ${resourceId} / ${profileId}")
                render status: HttpServletResponse.SC_BAD_REQUEST
            }
        } else {
            log.error("RESOURCE ROW ADD: Error: Unable to render resource, no resourceId")
            render status: HttpServletResponse.SC_BAD_REQUEST
        }
    }

    /**
     * This method was added for updating recently added rows of materials on a Query page when results was saved
     */
    def getResourceRowsRepresentation() {
        String childEntityId = request.JSON.entityId
        String queryId = request.JSON.queryId
        String indicatorId = request.JSON.indicatorId
        List<String> datasetIds = request.JSON.datasetIds as List

        Entity entity = entityService.getEntityById(childEntityId)
        Indicator indicator = indicatorService.getIndicator(indicatorId)
        Query query = queryService.getQueryByQueryId(queryId)
        Map<String, String> resourcesRepresentation

        if (!entity || !query || !indicator){
            return response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to get new rows representations")
        } else {
            resourcesRepresentation = utilService.getResourceRowsRepresentation(entity, indicator, query, datasetIds)
        }

        render ([output: resourcesRepresentation, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    /**
     * This method was added for updating style of mandatory fields on a Query page when results was saved
     */
    def getQuestionsStatus() {
        String entityId = request.JSON.entityId
        String queryId = request.JSON.queryId
        String indicatorId = request.JSON.indicatorId

        Entity entity = entityService.getEntityById(entityId)
        Indicator indicator = indicatorService.getIndicator(indicatorId)
        Query query = queryService.getQueryByQueryId(queryId)
        Map<String, Map> questionsStatuses

        if (!entity || !query || !indicator){
            return response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to get questions status")
        } else {
            questionsStatuses = utilService.getQuestionsStatuses(entity, indicator, query)
        }

        render ([questionsStatuses: questionsStatuses, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def renderSourceListing() {
        String resourceId = params.resourceId
        String profileId = params.profileId
        Resource resource = resourceId ? optimiResourceService.getResourceWithParams(resourceId, profileId ?: null, true) : null
        if (!resource) {
            log.error("No resource found for sourcelisting with params ${params}")
            render text: "${message(code: 'resource.sourcelisting_no_information_available')}"
        } else {
            String entityId = params.entityId
            String indicatorId = params.indicatorId
            String datasetId = params.datasetId
            String questionId = params.questionId
            String sectionId = params.sectionId
            String queryId = params.queryId
            String showGWP = params.showGWP
            String infoId = params.infoId ?: "${resource.id.toString()}info"
            String queryPage = params.queryPage
            String manualId = params.manualId
            String disabledParam = params.disabled
            String customDatasetName = params.customName
            String cardPosition = params.cardPosition
            String topCoordinate = params.topCoordinate
            String leftCoordinate = params.leftCoordinate
            String initWidth = params.initWidth
            String initHeight = params.initHeight
            String zIndex = params.zIndex
            boolean hideImage = params.boolean('hideImage')
            boolean productDataListPage = params.productDataListPage
            boolean constructionPage = params.boolean('constructionPage')

            // Convert to Double the position and size of data card (if called from renderSourceListingToAnyElement or anywhere else that wants to set the size and position of data card)
            if (topCoordinate) {
                topCoordinate = topCoordinate.trim().replace('px', '').toDouble() ?: null
            }
            if (leftCoordinate) {
                leftCoordinate = leftCoordinate.trim().replace('px', '').toDouble() ?: null
            }
            if (initWidth) {
                initWidth = initWidth.trim().replace('px', '').toDouble() ?: null
            }
            if (initHeight) {
                initHeight = initHeight.trim().replace('px', '').toDouble() ?: null
            }
            if (zIndex) {
                zIndex = zIndex.trim().toDouble() ?: null
            }

            // Fetch necessary objects for later use
            ResourceType subType = resourceService.getSubType(resource)
            User user = userService.getCurrentUser()
            Account account = userService.getAccount(user)
            boolean isFavorite = user ? userService.getFavoriteMaterials(user)?.containsKey(resourceId + '.' + profileId) : false
            String userLanguage = DomainObjectUtil.mapKeyLanguage ?: "EN"
            Entity entity = entityId ? entityService.getEntityById(entityId, null) : null
            Entity parent = entity?.parentById
            Resource countryResource = parent?.getCountryResource()
            Dataset dummyDataset = resource.isDummy && manualId ? entity?.datasets?.find { it.manualId == manualId } : null
            EolProcess eolProcess = subType ? eolProcessService.getEolProcessForSubType(subType, countryResource) : null
            Question ques = questionService.getQuestion(queryId, questionId)
            if (!sectionId && ques) {
                sectionId = ques.sectionId
            }

            // To understand this block of codes, check ticket 15399 && 15409
            String eolCalculationMethod = parent?.getEOLCalculationMethod() ?: ''
            Map<String, Double> eolImpactsByStages = resource.getResolvedGWPImpactsByStages(com.bionova.optimi.core.Constants.EOL_STAGES)
            boolean useEpdScenario = eolCalculationMethod == '20EPD' && eolImpactsByStages
            boolean useEpdScenarioButNotAvailable = eolCalculationMethod == '20EPD' && !eolImpactsByStages

            // Fetch configuration for render sections and required licenses
            Indicator indicator = indicatorId ? indicatorService.getIndicatorByIndicatorId(indicatorId, true) : null
            List<DataCardSection> sourceListing = indicator?.getResolveResourceExtraInformationInfoBubble() ?: utilService.getDefaultConfigurationForDataCard()
            List<String> requiredLicenseKeys = utilService.getResolvedLicenseKeysForSourceListing(sourceListing)
            requiredLicenseKeys.addAll(Feature.EPD_DOWNLOAD, Feature.COMPARE_MATERIAL)
            Map<String, Map> blockAndWarningResourceFilterCriteria = [
                    (IndicatorQuery.WARNING_RESOURCE): ques?.getWarningResourceFilterCriteria(indicator, parent),
                    (IndicatorQuery.BLOCK_RESOURCE): ques?.getBlockResourceFilterCriteria(indicator, parent)
            ]
            blockAndWarningResourceFilterCriteria.removeAll{!it.value}

            // Fetch licenses
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parent, requiredLicenseKeys)
            Query query = queryService.getQueryByQueryId(queryId, true)
            boolean compareFeatureLicensed = featuresAllowed?.get(Feature.COMPARE_MATERIAL)
            boolean epdDownloadLicensed = featuresAllowed?.get(Feature.EPD_DOWNLOAD)
            boolean showPerformanceRanking = resource.benchmark && "OK" == resource.benchmark.status && subType && subType.isSubType && subType.standardUnit && subType.benchmarkResult && indicator?.connectedBenchmarks
            String displayDownloadText = ''
            String epdDownloadLink = ''
            String b64ImageEPD = ''

            // Fetch EPD and first page of EPD as image
            if (resource.downloadLink && "-" != resource.downloadLink.trim() && resource.downloadLink.startsWith("http")) {
                displayDownloadText = message(code: 'resource.externalLink')
                epdDownloadLink = resource.downloadLink
            } else if (resourceService.getEpdFile(resource.downloadLink)?.exists()) {
                String epdFilePathPrefix = configurationService.getConfigurationValue(null, "epdFilePathPrefix")
                displayDownloadText = message(code: 'resource.pdfFile_download')
                epdDownloadLink = createLink(controller: 'util', action: 'getEpdFile', params: [resourceId: resource.resourceId, profileId: resource.profileId])
                b64ImageEPD = epdDownloadLicensed && !hideImage ? dataCardService.getEpdFirstPageB64(epdFilePathPrefix, resource) : ''
            }

            // Get image from resource.imgLink OR from construction if resource doesn't have one
            Construction construction = resource.construction && resource.constructionId && !hideImage && (!b64ImageEPD || !resource.imgLink) ? constructionService.getConstruction(resource.constructionId) : null
            String imgLink = !b64ImageEPD && !hideImage ? (resource.imgLink ?: construction?.imgLink ?: '') : ''
            String imgCopyRightText = imgLink == resource.imgLink ? resource.copyrightText : imgLink == construction?.imgLink ? construction?.copyrightText : ''

            // Get Brand
            String b64BrandImage = accountImagesService.getAccountImageAsB64Data(resource?.brandImageId)

            // Check full static name
            String resourceFullName = optimiResourceService.getResourceDisplayName(resource, true, dummyDataset)
            boolean isLongName = resourceFullName?.length() > 500
            String resourceFullNameHTML = stringUtilsService.convertUnitToHTML(resourceFullName?.encodeAsHTML())
            String constructionGroupDescription = dummyDataset?.productDescription ?: ''

            // Check if resource has been added to compare
            Entity materialCompareEnt = parent ? entityService.getMaterialSpecifierEntity(parent) : null
            List<String> resourceIdsList = materialCompareEnt?.datasets?.toList()?.collect({ it.resourceId }) ?: []
            boolean resourceAlreadyAdded = resourceIdsList?.contains(resourceId)

            boolean monthlyInputIndicator = indicator?.allowMonthlyData || indicator?.requireMonthly //TODO: temp fix for operating indicator that allow monthly data input. Better fix next time @Trang, ticket 11115
            boolean multipleChoicesAllowed = ques ? ques.multipleChoicesAllowed : true
            boolean disabled = !(("false").equalsIgnoreCase(disabledParam) || ("undefined").equalsIgnoreCase(disabledParam) || ("").equalsIgnoreCase(disabledParam))
            boolean showAddToInputBtn = false
            boolean showAddToCompareBtn = false
            boolean maxRowsReached = false
            boolean showResourceFilterWarning = false
            String resourceFilterQualityWarning = Resource.QUALITY_WARNING_WARN_TYPE
            String uniqueConstructionIdentifier = ''
            String resConstructionId = ''
            // Resolve whether to show the buttons
            boolean showFavoriteBtn = resource.active
            if (queryPage && !disabled && resource.active && !monthlyInputIndicator && multipleChoicesAllowed) {
                uniqueConstructionIdentifier = resource.constructionId + UUID.randomUUID().toString()
                resConstructionId = resource?.constructionId
                maxRowsReached = indicator?.getDesignMaxRowsReached(entity?.id?.toString())
                showAddToInputBtn = !resource.isDummy && !productDataListPage && !constructionPage // don't add !maxRowsReached, leave it to gsp to show warning
                showAddToCompareBtn = !resource.construction && !maxRowsReached && indicatorService.compareDataLicensedAndCompatible(featuresAllowed, query, indicator)
            }

            String transportationDefault = parent?.defaults?.get(Constants.DEFAULT_TRANSPORT)
            List additionalQuestionIds = indicator?.indicatorQueries?.findResults { IndicatorQuery indicatorQuery ->
                (indicatorQuery.queryId == queryId) ? indicatorQuery.additionalQuestionIds : null
            }?.flatten()?.unique()

            // Start rendering sections defined in configuration
            List<DataCardSection> sections = sourceListing?.each { DataCardSection section ->
                section.authorizedToDisplay = section.licenseKey ? featuresAllowed?.get(section.licenseKey) : true
                // Do not skip looping if not authorized, there might be some logic in processing the customAttributes to show license warning text or reassign row.authorizedToDisplay
                section.heading = section.name?.get(userLanguage) ?: section.name?.get("EN") ?: 'Missing name'
                section.help = section.helpLocalized ? stringUtilsService.convertUnitToHTML(message(code: section.helpLocalized) as String) : ""

                if (section.customRendering) {

                    if (!section.sectionId) {
                        section.customContent = "Empty sectionId with 'customRendering': true is not allowed. Double check configuration or contact a developer"
                    } else if ("description" == section.sectionId) {
                        section.customContent = resource.productDescription ? "<div style=\"padding: 0 10px 5px 10px\">${stringUtilsService.convertUnitToHTML(resource.productDescription?.encodeAsHTML())}</div>" : ''
                    } else {
                        section.customContent = "Unrecognized custom sectionId (${section.sectionId}) with 'customRendering': true. Double check configuration or contact a developer"
                    }
                    section.isEmpty = !section.customContent

                } else if (!section.resourceAttributes) {

                    section.customRendering = true
                    section.customContent = "The object with sectionId (${section.sectionId}) has incorrect format. It must have either 'resourceAttributes' or 'customRendering' : true. Double check configuration or contact a developer"

                } else {
                    section.resourceAttributes.each { DataCardRow row ->
                        if (!utilService.checkIfDataCardRowIsForDisplaying(row, additionalQuestionIds)) {
                            return
                        }

                        row.authorizedToDisplay = row.licenseKey ? featuresAllowed?.get(row.licenseKey) : true
                        // Do not skip looping if not authorized, there might be some logic in processing the customAttributes to show license warning text or reassign row.authorizedToDisplay
                        row.heading  = row.name?.get(userLanguage) ?: row.name?.get("EN") ?: 'Missing name'
                        row.help = row.helpLocalized ? stringUtilsService.convertUnitToHTML(message(code: row.helpLocalized) as String) : ''
                        utilService.handleConditionalHideOnDataCardRow(row, resource)

                        if (!row.resourceAttribute && !row.resourceSubTypeAttribute && !row.customAttribute) {

                            row.content = "Configuration must have either resourceAttribute or resourceSubTypeAttribute or customAttribute for this row."

                        } else if (row.resourceAttribute || row.resourceSubTypeAttribute) {

                            def sourceResource = row.resourceAttribute ? resource : subType
                            String attributeKey = row.resourceAttribute ?: row.resourceSubTypeAttribute
                            def value = sourceResource ? DomainObjectUtil.callGetterByAttributeName(attributeKey, sourceResource) : ''
                            if (value) {
                                value = (value instanceof List<String>) ? value.join(', ') : value
                                value = value != "-" ? stringUtilsService.roundUpNumbersAsString(value.toString(), 3) : ''
                            }
                            row.content = value ? stringUtilsService.convertUnitToHTML(value.toString().encodeAsHTML()) : ''

                        } else if (row.customAttribute) {
                            String customAttribute = row.customAttribute
                            String customContent = ''

                            // NOTE: this is in a loop for each row, hence the customContent will take values only from one of the IF statements
                            // To add a qMark with popover, either define row.help (recommended) OR add class triggerPopover to your custom qMark for consistency. The keepPopoverOnHover javascript method is used on the data card to create popover
                            if ('localizedName' == customAttribute) {

                                if (userLanguage && userLanguage == 'EN') {
                                    row.authorizedToDisplay = false
                                } else if (userLanguage) {
                                    String localName = DomainObjectUtil.callGetterByAttributeName("name${userLanguage}", resource)
                                    if (localName) {
                                        customContent = "${localName} <i class=\"far fa-clipboard copyToClipBoard triggerPopover\" data-content=\"${message(code: 'resource.sourcelisting.copy')}\" onclick=\"copyFullNameToClipBoard('name${userLanguage}${resource?.resourceId}');\"></i><br/>"
                                        customContent += "<input type=\"hidden\" id=\"name${userLanguage}${resource?.resourceId}\" value=\"${localName}\">"
                                    }
                                }

                            } else if ('subTypeLocalizedName' == customAttribute) {

                                 if (userLanguage && subType) {
                                    String localNameSubType = DomainObjectUtil.callGetterByAttributeName("name${userLanguage}", subType)
                                    customContent = localNameSubType ?: ''
                                }

                            } else if ('areas' == customAttribute) {

                                if (resource.areas) {
                                    String globe = "/app/assets/isoflags/globe.png"
                                    Map<String, String> isoFlagPaths = [:]
                                    List<String> isoCodes = []

                                    if (resourceService.getIsLocalResource(resource.areas)) {
                                        if (countryResource && countryResource.isoCountryCode) {
                                            isoCodes.add(countryResource.isoCountryCode)
                                        } else if (resource.representativeArea) {
                                            isoCodes = optimiResourceService.getResourceWithParams(resource.representativeArea, null, null)?.isoCodesByAreas?.values()?.toList()
                                        }
                                    } else {
                                        if (!resource?.isoCodesByAreas) {
                                            isoCodes = optimiResourceService.getresourceIsoCodes(resource.areas, resource.additionalArea)?.values()?.toList()
                                        } else {
                                            isoCodes = resource?.isoCodesByAreas?.values()?.toList()
                                        }
                                    }

                                    if (isoCodes) {
                                        isoCodes.collect({ it.toLowerCase() }).each { String isoCountryCode ->
                                            String isoFlag

                                            if (System.getProperty("islocalhost")) {
                                                isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                                                if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                                    log.error("missing flag for countrycode ${isoCountryCode}")
                                                    isoFlag = globe
                                                }
                                            } else {
                                                String isoFilePath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "isoFlagUrlPath")
                                                def testIsoFilePath = new File("/var/www/${isoFilePath}${isoCountryCode}.png")
                                                if (testIsoFilePath.exists()) {
                                                    isoFlag = "${isoFilePath}${isoCountryCode}.png"
                                                } else {
                                                    log.error("missing flag for countrycode ${isoCountryCode} with filepath ${testIsoFilePath?.absolutePath} from server")
                                                    isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                                                    if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                                        log.error("missing flag for countrycode ${isoCountryCode} from assets")
                                                        isoFlag = globe
                                                    }
                                                }
                                            }
                                            if (isoFlag) {
                                                isoFlagPaths.put(resource?.isoCodesByAreas?.find({
                                                    it.value == isoCountryCode
                                                })?.key?.toString()?.toLowerCase(), "<img src=\'${isoFlag}\'  class=\'miniFlag\'/>")
                                            }
                                        }
                                    }

                                    String areas = resourceService.getIsLocalResource(resource.areas) && countryResource ? countryResource.nameEN : resource.areas.join(", ")
                                    areas.split(", ")?.each { String area ->
                                        customContent += optimiResourceService.getCountryLocalizedName(area)?.capitalize() + " ${isoFlagPaths?.get(area?.toLowerCase() ?: '') ?: ''} "
                                    }
                                }

                            } else if ('warning' == customAttribute) {
                                if (blockAndWarningResourceFilterCriteria) {
                                    showResourceFilterWarning = blockAndWarningResourceFilterCriteria.any{key, value ->
                                        Map<String, List<Object>> propertiesWithoutWarningString = value.findAll{
                                            !(it.key in [IndicatorQuery.WARNING_MESSAGE_INDICATOR, IndicatorQuery.BLOCK_MESSAGE_INDICATOR])
                                        }

                                        if(resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, propertiesWithoutWarningString, true)){
                                            if (key == IndicatorQuery.BLOCK_RESOURCE){
                                                resourceFilterQualityWarning = Resource.QUALITY_WARNING_BLOCK_TYPE
                                            }

                                            return true
                                        } else {
                                            return false
                                        }
                                    }
                                }
                                if (showResourceFilterWarning) {
                                    customContent += "<img src='/app/assets/img/icon-warning.png' style='max-width: 15px' /><b>${message(code: 'resource.query_filter_warning')}</b>"
                                }

                                if (!resource.active) {
                                    customContent += "<img src = '/app/assets/img/icon-warning.png' style='max-width: 15px' /><b>${message(code: 'query.resource.inactive')}</b>"
                                }

                                if (resource.resourceQualityWarning && resource.resourceQualityWarningText) {
                                    customContent += "<img src = '/app/assets/img/icon-warning.png' style='max-width: 15px' /><b>${resource.resourceQualityWarningText}</b>"
                                } else if (resource.showWarning && resource.warningText) {
                                    // warningText is to be deprecated, resourceQualityWarningText should be used instead
                                    customContent += "<img src = '/app/assets/img/icon-warning.png' style='max-width: 15px' /><b>${resource.warningText}</b>"
                                }

                                if (resource.isExpiredDataPoint) {
                                    customContent += "<div rel=\"modal\" data-placement=\"top\" data-trigger=\"hover\" data-content=\"${message(code: 'expired_icon_detail')}\"><img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/><b>${message(code: 'data_point.expired')}</b></div>"
                                }

                            } else if ('gwpBefore' == customAttribute) {

                                Map<String, Double> gwpImpacts = resource.getResolvedGWPImpacts()
                                if (gwpImpacts) {
                                    String localCompensationCountry = datasetService.getDefaultLocalCompensationCountry(parent)
                                    row.heading += localCompensationCountry && !resource.areas?.contains(localCompensationCountry) ? " ${message(code: 'before_local_compensation')}" : ''
                                    gwpImpacts.each { unit, value ->
                                        if (value) {
                                            int roundingNumber = dataCardService.getRoundingNumber(value)
                                            customContent += "${value.round(roundingNumber)} kg CO2e / ${unit ?: ''}<br/>"
                                        }
                                    }
                                }

                            } else if ('performanceGroup' == customAttribute) {

                                if (subType && showPerformanceRanking) {
                                    customContent = resourceTypeService.getLocalizedName(subType)
                                }

                            } else if ('performanceRanking' == customAttribute) {

                                if (!row.authorizedToDisplay) {
                                    String enterpriseFeatureName = featureService.getFeatureByFeatureId('betaFeatures')?.formattedName ?: ''
                                    customContent = message(code: 'enterprise_feature_warning', args: [enterpriseFeatureName])
                                } else if (showPerformanceRanking) {
                                    Benchmark benchmark = resource.benchmark
                                    Map<String, Integer> validValues = subType?.benchmarkResult?.validValues
                                    row.help = "${message(code: 'resource.ranking_help_1')} <b>${resourceTypeService.getLocalizedName(subType)?.toLowerCase()}</b>. ${message(code: 'resource.ranking_help_2')}"
                                    indicator?.connectedBenchmarks?.each { String connectedBenchmark ->
                                        if (connectedBenchmark) {
                                            Integer quintile = benchmark?.quintiles?.get(connectedBenchmark) ?: benchmark?.co2Quintile
                                            String performanceCloud = ''

                                            if (quintile > 0) {
                                                switch (quintile) {
                                                    case 5:
                                                        performanceCloud = "deepGreenEmissions"
                                                        break
                                                    case 4:
                                                        performanceCloud = "greenEmissions"
                                                        break
                                                    case 3:
                                                        performanceCloud = "yellowEmissions"
                                                        break
                                                    case 2:
                                                        performanceCloud = "orangeEmissions"
                                                        break
                                                    case 1:
                                                        performanceCloud = "redEmissions"
                                                        break
                                                }
                                            }
                                            String performanceRanking

                                            if (benchmark?.values?.get(connectedBenchmark) && "NOT_CALCULABLE" != benchmark?.values?.get(connectedBenchmark)) {
                                                Integer ranking = benchmark?.ranking?.get(connectedBenchmark)
                                                String validValuePerDataProperty = validValues?.get(connectedBenchmark)

                                                if (ranking && validValuePerDataProperty) {
                                                    performanceRanking = "${ranking} / ${validValuePerDataProperty}"
                                                }
                                            }

                                            if (performanceRanking) {
                                                String ranking = "${com.bionova.optimi.core.Constants.BENCHMARKS?.get(connectedBenchmark)}: "

                                                if (!resource.dataProperties?.contains(Const.INIES)) {
                                                    ranking += "<b>${performanceRanking ?: ''}</b> ${performanceCloud ? "<div class=\"co2CloudContainer\"><i class=\"fa fa-cloud ${performanceCloud}\"></i><p class=\"co2Text\">CO<sub>2</sub></p></div>" : ""}"
                                                }

                                                String link = "<a style=\"cursor: pointer; margin-left: 5px;\" onclick=\"fullScreenPopup('${createLink(controller: "resourceType", action: "benchmarkGraph", params: [resourceSubTypeId: subType?.id, resourceId: resource.resourceId, profileId: resource.profileId, benchmarkToShow: connectedBenchmark, entityId: entityId, indicatorId: indicatorId, stateIdOfProject: parent?.getStateResource()?.resourceId])}'); stopBubblePropagation(event);\">${message(code: 'resource.link.emissions.seeFullPerformance', default: 'See full performance')}</a> ${userService.getSuperUser(user) && resource.dataManuallyVerified ? " <i class=\"fa fa-asterisk smoothTip tooltip--right\" data-tooltip=\"Manually verified\"></i>" : ""}" ?: ''
                                                customContent += ranking + link + '<br/>'
                                            }
                                        }
                                    }

                                    if (resource.dataManuallyVerified) {
                                        customContent += "${asset.image(src: "img/icon-warning.png", style: "max-width:16px")} ${message(code: 'datapoint_impact_unusual')}.<br />"
                                    }
                                } else if (resource.excludeFromBenchmark) {
                                    customContent += "${asset.image(src: "img/icon-warning.png", style: "max-width:16px")} ${message(code: 'datapoint_excluded')}.<br />"
                                } else if (resource.dataManuallyVerified) {
                                    customContent += "${asset.image(src: "img/icon-warning.png", style: "max-width:16px")} ${message(code: 'datapoint_impact_unusual')}.<br />"
                                }

                            } else if ('availableUnits' == customAttribute) {

                                if (resource.combinedUnits) {
                                    customContent = resource.combinedUnits.join(', ')
                                } else if (resource.isDummy && dummyDataset?.userGivenUnit) {
                                    customContent = dummyDataset.userGivenUnit
                                }

                            } else if ('transportationDistance' == customAttribute) {

                                customContent = parent?.getDefaultTransportFromSubType(resource)?.toString() ?: ''

                            } else if ('transportationMethod' == customAttribute) {

                                List<Resource> transportResources = resourceService.getTransportResourcesFromSubTypeBasedOnUserUnitSystem(resource) as List<Resource>
                                transportResources?.each { Resource transportResource ->
                                    customContent += optimiResourceService.getLocalizedName(transportResource) ? optimiResourceService.getLocalizedName(transportResource) + ':' : ''
                                    Map<String, Double> gwpImpacts = transportResource?.getResolvedGWPImpacts()
                                    gwpImpacts?.each { unit, value ->
                                        if (value) {
                                            int roundingNumber = dataCardService.getRoundingNumber(value)
                                            customContent += " ${value.round(roundingNumber)} kg CO2e / ${unit ?: ''}<br/>"
                                        }
                                    }
                                }

                            } else if ('serviceLifeProjectBased' == customAttribute) {

                                Integer serviceLife = parent ? resourceService.getServiceLifeDefault(parent, resource) : null
                                if (serviceLife == -1) {
                                    customContent = message('code': 'resource.serviceLife.as_building')
                                } else if (serviceLife != null) {
                                    customContent = serviceLife.toString()
                                }

                            } else if ('serviceLifeResource' == customAttribute) {

                                Double serviceLife = resource.serviceLife
                                if (serviceLife == -1) {
                                    customContent = message('code': 'resource.serviceLife.as_building')
                                } else if (serviceLife != null) {
                                    customContent = serviceLife.toString()
                                }

                            } else if ('wasteTreatmentScenario' == customAttribute) {

                                // To understand this, check ticket 15399
                                if (eolCalculationMethod == '20' || useEpdScenarioButNotAvailable) {
                                    customContent = eolProcess ? eolProcessService.getLocalizedName(eolProcess) : ''
                                } else if (useEpdScenario) {
                                    customContent = message('code': 'epdScenario')
                                } else {
                                    row.authorizedToDisplay = false
                                }

                            } else if ('wasteTreatmentMethod' == customAttribute) {

                                // To understand this, check ticket 15399 && 15409
                                if (eolProcess && (eolCalculationMethod == '20' || useEpdScenarioButNotAvailable)) {
                                    String c2 = eolProcess.c2ResourceId ?: ''
                                    String c3 = eolProcess.c3ResourceId ?: ''
                                    String c4 = eolProcess.c4ResourceId ?: ''
                                    String d = eolProcess.dResourceId ?: ''
                                    String cellContent = ''

                                    ['C2': c2, 'C3': c3, 'C4': c4, 'D': d].each { String name, String stageResourceId ->
                                        if (stageResourceId) {
                                            Resource stageResource = optimiResourceService.getResourceWithGorm(stageResourceId)
                                            Map<String, Double> gwpImpacts = stageResource?.getResolvedGWPImpacts() ?: [:]
                                            if (gwpImpacts && stageResource) {
                                                String localName = optimiResourceService.getLocalizedName(stageResource)
                                                // each stage is a mini row with 2 columns
                                                cellContent += "<tr class='noBorder'>"
                                                cellContent += "<td class='noBorder twoPadding alignBaseline'><b>${name}:</b></td>"
                                                cellContent += "<td class='noBorder twoPadding'>"
                                                cellContent += "${localName}: "
                                                gwpImpacts.each { unit, value ->
                                                    if (value) {
                                                        int roundingNumber = dataCardService.getRoundingNumber(value)
                                                        cellContent += "${(value as Double).round(roundingNumber)} kg CO2e / ${unit ?: ''}"
                                                    }
                                                }
                                                cellContent += '</td>'
                                                cellContent += '</tr>'
                                            }
                                        }
                                    }
                                    customContent += cellContent ? '<table><tbody>' + cellContent + '</tbody></table>' : ''
                                } else {
                                    row.authorizedToDisplay = false
                                }

                            } else if ('privateDataset' == customAttribute) {

                                if (resource.privateDataset && resource.privateDatasetAccountId) {
                                    customContent = "${resource.privateDatasetAccountName ? resource.privateDatasetAccountName.encodeAsHTML() : accountService.getAccount(resource.privateDatasetAccountId)?.companyName?.encodeAsHTML()}"

                                    if (resource.firstUploadTime) {
                                        customContent += " - ${resource.firstUploadTime.format("dd.MM.yyyy")}"

                                        if (resource.privateDatasetImportedByUserId) {
                                            User uploader = userService.getUserById(DomainObjectUtil.stringToObjectId(resource.privateDatasetImportedByUserId))

                                            if (uploader) {
                                                customContent += " ${maskingService.maskEmail(uploader.username)}"
                                            }
                                        }
                                    }
                                }

                            } else if ('specificCharacteristics' == customAttribute) {

                                resource.specificCharacteristics?.each {
                                    customContent += "${message(code: it)}<br />"
                                }

                            } else if ('otherImpactCategories' == customAttribute) {

                                List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parent)
                                List<CalculationRule> impactCatzToShowInDataCard = calculationRules?.findAll { it.mapToImpactCategory }

                                impactCatzToShowInDataCard?.forEach({
                                    if (it) {
                                        Double impactValues = (Double) DomainObjectUtil.callGetterByAttributeName(it.mapToImpactCategory, resource)
                                        if (impactValues) {
                                            int impactRoundingNumber = 2
                                            if (impactValues < 0.0001) {
                                                DecimalFormat format = new DecimalFormat("0.00E0");
                                                impactValues = Double.parseDouble(format.format(impactValues))
                                            } else if (impactValues < 0.1 && impactValues >= 0.0001) {
                                                impactRoundingNumber = 4
                                                impactValues = impactValues.round(impactRoundingNumber)
                                            } else {
                                                impactValues = impactValues.round(impactRoundingNumber)
                                            }
                                            String impactHelp = it.getLocalizedHelp() ?: ''
                                            String qMark = impactHelp ? "<span class=\"triggerPopover qMarkInRowDataCard\" data-content=\"${impactHelp}\"><i class=\"icon-question-sign\"></i></span>" : ''
                                            customContent += "<div class='hasQMarkInRowDataCard'><b>${qMark} ${it.getLocalizedName() ?: ''}:</b> ${impactValues} ${it.getLocalizedUnit() ?: ''} / ${resource.unitForData ?: ''}</div>"
                                        }
                                    }
                                })
                                if (customContent) {
                                    customContent = "<a  href='javascript:' onclick='toggleElement(\".hideotherimpacts${infoId}\"); \$(this).remove(); '>${message('code': 'show')}</a><span class='hideotherimpacts${infoId}' style='display: none'>${customContent}</span>"
                                }

                            } else if ('resistanceProperties' == customAttribute) {

                                resource.resistanceProperties?.eachWithIndex { it, index ->
                                    if (it != null && it != 'null') {
                                        String propertyPath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "resistancePropertiesPath")
                                        File testPath = propertyPath ? new File("/var/www/${propertyPath}${it}.png") : null
                                        if (testPath?.exists()) {
                                            customContent += "${index > 0 ? ', ' : ''} <img src=\"${propertyPath}${it}.png\" class=\"miniFlag\"></img> ${it}"
                                        } else {
                                            String assetPath = "/resistanceProperties/${it}.png"
                                            if (asset?.assetPathExists(src: assetPath)) {
                                                customContent += "${index > 0 ? ', ' : ''} <img src=\"/app/assets/${assetPath}\" class=\"miniFlag\"></img> ${it}"
                                            } else {
                                                customContent += "${index > 0 ? ', ' : ''} ${it.toString()}"
                                            }
                                        }
                                    }
                                }

                            } else if ('leedEpdImpactReduction' == customAttribute) {

                                Benchmark benchmark = resource.benchmark
                                indicator?.connectedBenchmarks?.each { String connectedBenchmark ->
                                    if (connectedBenchmark == "leed_epd_cml" && benchmark.values && benchmark.values.get("leed_epd_cml") && benchmark.values.get("leed_epd_cml") != "NOT_CALCULABLE") {
                                        customContent += "${benchmark.values.get("leed_epd_cml")} ${Integer.valueOf(benchmark.values.get("leed_epd_cml")) >= 3 ? " - ${message(code: 'resource.qualifies')}" : "(${message(code: 'resource.not_qualified')})"}"
                                    } else if (connectedBenchmark == "leed_epd_traci" && benchmark.values && benchmark.values.get("leed_epd_traci") && benchmark.values.get("leed_epd_cml") != "NOT_CALCULABLE") {
                                        customContent += "${benchmark.values.get("leed_epd_traci")} ${Integer.valueOf(benchmark.values.get("leed_epd_traci")) >= 3 ? " - ${message(code: 'resource.qualifies')}" : "(${message(code: 'resource.not_qualified')})"}"
                                    }
                                }

                            } else if ('biogenicCarbonStorage' == customAttribute) {

                                if (resource.biogenicCarbonStorage_kgCO2e) {
                                    int roundingNumber = dataCardService.getRoundingNumber(resource.biogenicCarbonStorage_kgCO2e)
                                    if (("buildingOperatingEnergyAndWater").equalsIgnoreCase(queryId)) {
                                        row.heading = message(code: 'resource.biogenicCarbonContent_kgCO2e') as String
                                        customContent += "${resource.biogenicCarbonStorage_kgCO2e.round(roundingNumber)} kg CO2e / ${resource.unitForData ?: ''}<br />"
                                    } else {
                                        customContent += "${resource.biogenicCarbonStorage_kgCO2e.round(roundingNumber)} kg CO2e / ${resource.unitForData ?: ''} <i class=\"fa fa-tree darkGreenIcon\"></i><br />"
                                    }
                                }
                                if (resource.biogenicCarbonStorage_kgCO2e) {
                                    row.help += "${message(code: 'resource.biogenicCarbonStorage_kgCO2e.value_default')}<br/> "
                                }
                                if (resource.biogenicCarbonSeparated) {
                                    customContent += "${message(code: 'resource.biogenicCarbonStorage_kgCO2e.info')}<br/> "
                                }
                                if (resource.biogenicCarbonEstimated) {
                                    customContent += "${asset.image(src: "img/icon-warning.png", style: "max-width:16px")} ${message(code: 'resource.biogenicCarbonStorage_kgCO2e.estimated')} "
                                }

                            } else if ('datasetType' == customAttribute) {

                                customContent = resource.datasetType ? message(code: resource.datasetType) : ''

                            } else if ('marketDataset' == customAttribute) {

                                customContent = resource.marketDataset ? message(code: 'yes') : message(code: 'no')

                            } else if ('totalWasteAndMaterialOutputs_kg' == customAttribute) {

                                customContent = resource.totalWasteAndMaterialOutputs_kg ? stringUtilsService.roundUpNumbersAsString(resource.totalWasteAndMaterialOutputs_kg.toString(), 3) + ' kg' : ''

                            } else if ('allEnergyUsedTotal_kWh_SUM' == customAttribute) {

                                customContent = resource.allEnergyUsedTotal_kWh_SUM ? stringUtilsService.roundUpNumbersAsString(resource.allEnergyUsedTotal_kWh_SUM.toString(), 3) + ' kWh' : ''

                            } else if ('qMetadata' == customAttribute) {

                                def q = resource.getQMetadata()
                                if (q) {
                                    q = (q * 100).round(2).toString()
                                    customContent = message(code: "qMetadata", args: [q])
                                }

                            } else if ('customDatasetName' == customAttribute) {

                                if (customDatasetName) {
                                    customContent = customDatasetName?.encodeAsHTML()
                                } else {
                                    row.authorizedToDisplay = false
                                }

                            } else if ('density' == customAttribute) {

                                customContent = resource.density ? stringUtilsService.roundUpNumbersAsString(resource.density.toString(), 2) + ' kg/m3' : ''

                            } else if ('verificationStatus' == customAttribute) {

                                if (resource.verificationStatus && com.bionova.optimi.core.Constants?.ALLOWED_RESOURCE_VERIFICATION_STATUS?.keySet()?.contains(resource.verificationStatus)) {
                                    customContent = message(code: "resource.verificationStatus.${resource.verificationStatus}") as String
                                    row.help = message(code: "resource.verificationStatus.${resource.verificationStatus}.info") as String
                                }

                            } else if (customAttribute == Constants.SITE_WASTAGE_ATTR_ID) {
                                customContent = subType && (subType.siteWastage != null) ? stringUtilsService.roundUpNumbersAsString(subType.siteWastage.toString(), 1) + ' %' : ''
                            } else if (customAttribute == Constants.SITE_WASTAGE_BOVERKET_ATTR_ID) {
                                Double siteWastageBoverket = resource.siteWastageBoverket != null ? resource.siteWastageBoverket : subType?.siteWastageBoverket

                                if (siteWastageBoverket != null) {
                                    customContent = stringUtilsService.roundUpNumbersAsString(siteWastageBoverket.toString(), 1) + ' %'
                                }
                            } else if ('massConversionFactor' == customAttribute) {

                                if (resource.massConversionFactor && resource.unitForData) {
                                    if ( resource.unitForData == 'kg' && resource.massConversionFactor == 1) {
                                        row.authorizedToDisplay = false
                                    } else {
                                        customContent = stringUtilsService.roundUpNumbersAsString(resource.massConversionFactor.toString(), 3) + ' kg/' + resource.unitForData
                                    }
                                }

                            } else if ('thermalLambda' == customAttribute) {

                                customContent = resource.thermalLambda ? stringUtilsService.roundUpNumbersAsString(resource.thermalLambda.toString(), 3) + ' W/m.K' : ''

                            } else if ('defaultThickness_mm' == customAttribute) {

                                customContent = resource.defaultThickness_mm ? stringUtilsService.roundUpNumbersAsString(resource.defaultThickness_mm.toString(), 3) + ' mm' : ''

                            } else if (customAttribute == Constants.SITE_WASTAGE_REFERENCE_ATTR_ID) {
                                customContent = resource.siteWastage == null ? subType?.siteWastageReference :
                                        message(code: "dataCard.attribute.siteWastageReference.message", default: "Site wastage comes from the datapoint")
                            } else if (customAttribute == Constants.SITE_WASTAGE_REFERENCE_BOVERKET_ATTR_ID) {
                                customContent = resource.siteWastageBoverket == null ? subType?.siteWastageBoverketReference :
                                        message(code: "dataCard.attribute.siteWastageReference.message", default: "Site wastage comes from the datapoint")
                            } else if (customAttribute == Constants.DEFAULT_TRANSPORT_BOVERKET_REFERENCE_ATTR_ID) {
                                customContent = resource.defaultTransportBoverket == null ? subType?.defaultTransportBoverketReference :
                                        message(code: "dataCard.attribute.defaultTransportBoverketReference.message", default: "Transportation scenario comes from the datapoint")
                            } else if (customAttribute == Constants.TRANSPORTATION_DISTANCE_REFERENCE_ATTR_ID) {
                                customContent = transportationDefault ?
                                        DomainObjectUtil.callGetterByAttributeName(transportationDefault + Constants.REFERENCE, subType) : StringUtils.EMPTY
                            } else if (customAttribute == Constants.TRANSPORTATION_DISTANCELEG2_ATTR_ID) {
                                customContent = (transportationDefault == Constants.DEFAULT_TRANSPORT_RICS) ? subType?.defaultTransportRICSleg2?.toString() : StringUtils.EMPTY
                            } else if (customAttribute == Constants.TRANSPORTATION_METHOD_LEG2_ATTR_ID) {
                                if (transportationDefault == Constants.DEFAULT_TRANSPORT_RICS) {
                                    String transportResourceId = subType?.transportResourceIdRICSleg2

                                    if (transportResourceId) {
                                        Resource transportResource = optimiResourceService.getResourceByResourceAndProfileId(transportResourceId, null)
                                        customContent = transportResource ? localizationService.getLocalizedProperty(transportResource, "name", "resourceId", transportResourceId) : transportResourceId
                                    } else {
                                        customContent = StringUtils.EMPTY
                                    }
                                } else {
                                    customContent = StringUtils.EMPTY
                                }
                            } else if (customAttribute == Constants.SERVICE_LIFE_REFERENCE_ATTR_ID) {
                                String serviceLifeDefault = parent?.defaults?.get(Constants.DEFAULT_SERVICELIFE)

                                if (serviceLifeDefault in [Constants.SERVICE_LIFE_COMMERCIAL, Constants.SERVICE_LIFE_TECHNICAL, Constants.SERVICE_LIFE_RICS]) {
                                    customContent = DomainObjectUtil.callGetterByAttributeName(serviceLifeDefault + Constants.REFERENCE, subType) ?: StringUtils.EMPTY
                                }
                            } else if (customAttribute in ATTR_IDS_FOR_DOUBLE_CHECKING) {
                                customContent = DomainObjectUtil.callGetterByAttributeName(customAttribute, resource)?.toString() ?:
                                        DomainObjectUtil.callGetterByAttributeName(customAttribute, subType)?.toString()
                            } else if (customAttribute in TRANSPORT_RESOURCE_ID_BOVERKET_ATTR_IDS) {
                                String transportResourceId = DomainObjectUtil.callGetterByAttributeName(customAttribute, resource)?.toString() ?:
                                        DomainObjectUtil.callGetterByAttributeName(customAttribute, subType)?.toString()

                                if (transportResourceId) {
                                    Resource transportResource = optimiResourceService.getResourceByResourceAndProfileId(transportResourceId, null)
                                    customContent = transportResource ? localizationService.getLocalizedProperty(transportResource, "name", "resourceId", transportResourceId) : transportResourceId
                                } else {
                                    customContent = StringUtils.EMPTY
                                }
                            } else {
                                customContent = "Unrecognized custom attribute (${customAttribute}). Contact developer"
                            }
                            customContent = stringUtilsService.roundUpNumbersAsString(customContent, 3)
                            row.content = stringUtilsService.convertUnitToHTML(customContent)
                        }
                    }
                    section.isEmpty = !section.resourceAttributes.find { DataCardRow row -> row.content }
                }
            }
            String templateAsString = groovyPageRenderer.render(template: "/util/dataCard", model: [resourceId         : resourceId, profileId: profileId, queryId: queryId, sectionId: sectionId, questionId: questionId, entityId: entityId, indicatorId: indicatorId, infoId: infoId, datasetId: datasetId, showGWP: showGWP, account: account, resource: resource, construction: construction,
                                                                                   user               : user, parent: parent, indicator: indicator, ques: ques, displayDownloadText: displayDownloadText, epdDownloadLink: epdDownloadLink, b64ImageEPD: b64ImageEPD, imgLink: imgLink, imgCopyRightText: imgCopyRightText, maxRowsReached: maxRowsReached, resourceAlreadyAdded: resourceAlreadyAdded,
                                                                                   isFavorite         : isFavorite, showAddToInputBtn: showAddToInputBtn, showAddToCompareBtn: showAddToCompareBtn, showFavoriteBtn: showFavoriteBtn, resourceFullName: resourceFullName, resourceFullNameHTML: resourceFullNameHTML, isLongName: isLongName, uniqueConstructionIdentifier: uniqueConstructionIdentifier,
                                                                                   epdDownloadLicensed: epdDownloadLicensed, compareFeatureLicensed: compareFeatureLicensed, sections: sections, topCoordinate: topCoordinate, leftCoordinate: leftCoordinate, initWidth: initWidth, initHeight: initHeight, zIndex: zIndex, cardPosition: cardPosition, b64BrandImage: b64BrandImage, resConstructionId: resConstructionId,
                                                                                   showResourceFilterWarning: showResourceFilterWarning, resourceFilterQualityWarning: resourceFilterQualityWarning,constructionGroupDescription: constructionGroupDescription]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def compiledReportDesigns() {
        Map<String, String> outputmap = [:]
        String indicatorId = params.indicatorId
        String entityId = params.parentEntityId
        String output

        if (indicatorId && entityId) {
            Entity entity = entityService.getEntityById(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (entity && indicator) {
                String heading = "${message(code: 'compiledReport.heading')}: ${indicatorService.getLocalizedName(indicator)}"

                if (indicator.breeamTypeExcelReport) {
                    heading = "${heading}<br/>${message(code: 'compiledReport.heading.help')} <a href=\"${indicator?.indicatorHelpUrlTranslation}\" target=\"_blank\" class=\"helpLink\">${message(code: 'help.instructions')}</a>"
                }
                outputmap.put("heading", heading)
                List<Entity> designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType()).findAll({Entity design -> !design.disabledIndicators?.contains(indicatorId)})

                if (designs) {
                    output = "<table class='table-striped table table-condensed'><tbody>"

                    designs.sort({ it.name }).each { Entity design ->
                        output = output + "<tr><td>${design.name}</td><td style=\"width: 50px;\"><input style=\"width: 30px; height: 20px;\" type=\"checkbox\" class=\"designCheckbox\" data-indicatorId=\"${indicatorId}\" data-parentId=\"${entityId}\" data-designId=\"${design.id}\" onclick=\"createDownloadLinkFromChecked()\" /></td></tr>"
                    }
                    output = output + "</tbody></table>"
                }
            }
        }

        if (output) {
            output = output + "<div id=\"downloadButtonContainer\" style=\"text-align: center;\"></div>"
            outputmap.put("htmltable", output)
        }
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def compiledReportDownloadLink() {
        def chosenDesignIds = request.JSON.designIds
        String indicatorId = request.JSON.indicatorId
        String parentEntityid = request.JSON.parentId
        Integer batimentIndex = request.JSON.batimentIndex?.toString()?.toInteger()
        String entityId = request.JSON.entityId
        String button = ""
        Boolean frenchTool = request.JSON.frenchTool
        String questionIdForFrenchRset = request.JSON.questionId

        if (frenchTool) {
            String zonesSelect = xmlService.getZonesForSelectedBatimentRenderString(parentEntityid, questionIdForFrenchRset, entityId, batimentIndex)
            button = "<a href=\"javascript:\" onclick=\"rsetFormvalidateAndSubmit('exportFecRSETForm')\" class=\"btn btn-primary\" >${message(code: "results.export_xml")}</a>"
            render([zonesSelect: zonesSelect, button: button, error: "", triggerChange: false, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else if (chosenDesignIds && !chosenDesignIds.isEmpty()) {
            String link = createLink(controller: "util", action: "downloadCompiledReport", params: [chosenDesignIds: chosenDesignIds, indicatorId: indicatorId, parentId: parentEntityid])

            if (link) {
                button = "<a href=\"${link}\" onclick=\"\$('#chooseDesignsModal').modal('toggle')\" class=\"btn btn-primary\" >Download Excel</a>"
            }
            render([text: button, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([text: "", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def downloadCompiledReport() {
        List<String> designIds = params.list('chosenDesignIds')
        String indicatorId = params.indicatorId
        String parentEntityId = params.parentId
        List<Workbook> workbooksToZip = []
        Workbook wb = new XSSFWorkbook()

        if (indicatorId && designIds && parentEntityId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Entity parent = entityService.readEntity(parentEntityId)

            if (indicator && indicator.excelExportCategoryIds && parent) {
                Boolean lcaForIMPACTcompliant = "lcaForIMPACTcompliant".equals(indicator.indicatorId) || "lcaForIMPACTcompliant2".equals(indicator.indicatorId)
                List<String> excelExportCategoryIds = indicator.excelExportCategoryIds
                List<String> excelExportRuleIds = indicator.excelExportRuleIds
                Boolean breeam = indicator.breeamTypeExcelReport
                List<ResultCategory> allResultCategories = indicator.getResolveResultCategories(parent)
                List<ResultCategory> resultCategories = allResultCategories?.findAll({
                    excelExportCategoryIds.contains(it.resultCategoryId)
                })
                List<CalculationRule> allCalculationRules = indicator.getResolveCalculationRules(parent)
                List<CalculationRule> calculationRules = excelExportRuleIds && !excelExportRuleIds?.isEmpty() ? allCalculationRules?.findAll({ excelExportRuleIds.contains(it.calculationRuleId) }) : allCalculationRules

                if (resultCategories && calculationRules) {
                    Map<String, List<String>> allowedQueriesAndSections = indicatorService.getQueriesAndSectionsFromResultCategories(indicator, resultCategories, allResultCategories)
                    List<String> validQueryIds = allowedQueriesAndSections?.keySet()?.toList()

                    if (validQueryIds) {
                        Date now = new Date()
                        CellStyle bold = optimiExcelImportService.getBoldCellStyle(wb)
                        CellStyle date = optimiExcelImportService.getDateCellStyle(wb)
                        Question elementCategory = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)?.sections[0]?.questions?.find({
                            "elementCategory".equals(it.questionId)
                        })

                        designIds.each { String entityId ->
                            if (breeam) {
                                wb = new XSSFWorkbook()
                                bold = optimiExcelImportService.getBoldCellStyle(wb)
                                date = optimiExcelImportService.getDateCellStyle(wb)
                            }
                            Entity entity = entityService.readEntity(entityId)

                            if (entity) {
                                String transportDefault = entity.defaults ? entity.defaults.get(Constants.DEFAULT_TRANSPORT) : null
                                ResourceCache cache = ResourceCache.init(entity.datasets?.toList())
                                // SW-1538 for some reason the services inside getAssessmentPeriod() do not get injected when we use newCalculationServiceProxy, hence change to newCalculationService
                                Integer assessmentPeriod = newCalculationService.getAssessmentPeriod(entity, indicator, cache)
                                List<CalculationResult> resultsByIndicatorId = entity.getCalculationResultObjectsByResultCategoryIds(indicator.indicatorId, excelExportCategoryIds)
                                Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(entity.name))
                                List<String> infoHeadings = ["Design option ID:", "Option name:", "Date created:", "Created by:", "Building ref."]
                                Row empty = sheet.createRow(0)
                                empty.setHeight(250.shortValue())

                                Integer rowIterator = 1
                                infoHeadings.each { String heading ->
                                    Row infoRowHeading = sheet.createRow(rowIterator)
                                    Cell infoCellTitle = infoRowHeading.createCell(1)
                                    infoCellTitle.setCellStyle(bold)
                                    infoCellTitle.setCellType(CellType.STRING)
                                    infoCellTitle.setCellValue(heading)

                                    Cell infoCellValue = infoRowHeading.createCell(2)

                                    if (1 == rowIterator || 2 == rowIterator) {
                                        infoCellValue.setCellType(CellType.STRING)
                                        infoCellValue.setCellValue(entity.name)
                                    } else if (3 == rowIterator) {
                                        infoCellValue.setCellValue(now)
                                        infoCellValue.setCellStyle(date)
                                    } else if (4 == rowIterator) {
                                        infoCellValue.setCellType(CellType.STRING)
                                        infoCellValue.setCellValue("One Click LCA (${now.format('dd.MM.yyyy')}), ${indicator.excelExportName ?: indicatorService.getLocalizedName(indicator)}, ${userService.getCurrentUser(Boolean.TRUE)?.name}")
                                    } else if (5 == rowIterator) {
                                        infoCellValue.setCellType(CellType.STRING)
                                        infoCellValue.setCellValue(parent.address ?: "")
                                    }
                                    rowIterator++
                                }
                                sheet.createRow(rowIterator)
                                rowIterator++

                                Row mainHeadingsRow = sheet.createRow(rowIterator)
                                List<String> mainHeadings = ["Unique incidence ID", "Elemental construction ID", "Elemental construction name", "Elemental construction classification", "Elemental construction quantity", "Elemental construction unit", "", "Product ID", "Product name / description", "Product type (generic)", "Product classification", "Product mass per element construction unit (kg)", "Product mass per element construction unit (kg) - All life stages", "Product unit", "Product service life (years)", "Product site waste (% of first installation quantity)", "Product transport 1 - Distance (km)", "Product transport 1 - Mode", "Product transport 2 - Distance (Km)", "Product transport 2 - Mode", ""]

                                if (lcaForIMPACTcompliant) {
                                    mainHeadings.add("All life stages: BRE EN 15804 ecopoints (Ep)")
                                } else {
                                    mainHeadings.add("All life stages: Global warming potential (kg CO2 equiv)")
                                }

                                Integer mainHeadingsCellIterator = 1
                                mainHeadings.each { String heading ->
                                    Cell columnCell = mainHeadingsRow.createCell(mainHeadingsCellIterator)
                                    columnCell.setCellStyle(bold)
                                    columnCell.setCellType(CellType.STRING)
                                    columnCell.setCellValue(heading)
                                    mainHeadingsCellIterator++
                                }
                                mainHeadingsCellIterator = mainHeadingsCellIterator + 2

                                resultCategories.each { ResultCategory resultCategory ->
                                    calculationRules.each { CalculationRule calculationRule ->
                                        if (!resultCategory.ignoreFromTotals?.contains(calculationRule.calculationRuleId) || !resultCategory.ignoreFromTotals?.isEmpty()) {
                                            Cell columnCell = mainHeadingsRow.createCell(mainHeadingsCellIterator)
                                            columnCell.setCellStyle(bold)
                                            columnCell.setCellType(CellType.STRING)
                                            if (breeam) {
                                                columnCell.setCellValue("${resultCategory.localizedBreeamReportName ?: resultCategory.resultCategory}: ${calculationRule.localizedBreeamReportName}")
                                            } else {
                                                columnCell.setCellValue("${resultCategory.resultCategory}: ${calculationRule.localizedName}, ${calculationRuleService.getLocalizedUnit(calculationRule, indicator)}")
                                            }
                                            mainHeadingsCellIterator++
                                        }
                                    }
                                }
                                rowIterator++

                                Integer incrementingNumber = 1
                                Set<Dataset> datasets = entity.datasets?.findAll({ validQueryIds.contains(it.queryId) })

                                ResourceCache resourceCache = ResourceCache.init(datasets.toList())
                                for (Dataset dataset: datasets) {
                                    List<String> allowedSections = allowedQueriesAndSections.get(dataset.queryId)

                                    if ((!allowedSections || allowedSections.contains(dataset.sectionId)) && (!breeam || (breeam && !(dataset.uniqueConstructionIdentifier && !dataset.parentConstructionId)))) {
                                        Resource resource = resourceCache.getResource(dataset)
                                        Boolean kilograms = "kg".equalsIgnoreCase(dataset.userGivenUnit)
                                        Double mass = dataset.calculatedMass
                                        Double quantity = dataset.quantity
                                        Dataset parentConstructionDataset
                                        Boolean parentConstructionKilograms

                                        if (dataset.parentConstructionId) {
                                            parentConstructionDataset = datasets.find({
                                                it.uniqueConstructionIdentifier.equals(dataset.uniqueConstructionIdentifier) && !it.parentConstructionId
                                            })
                                            parentConstructionKilograms = "kg".equalsIgnoreCase(parentConstructionDataset?.userGivenUnit)
                                        }

                                        if (resource) {
                                            ResourceType resourceType
                                            ResourceType subType

                                            if (resource.resourceType) {
                                                resourceType = ResourceType.findByResourceTypeAndSubTypeIsNullAndActive(resource.resourceType, true)
                                            }

                                            if (resource.resourceSubType) {
                                                subType = ResourceType.findBySubTypeAndActive(resource.resourceSubType, true)
                                            }

                                            Double serviceLife = newCalculationServiceProxy.getServiceLife(dataset, resource, entity, indicator)
                                            Row datasetRow = sheet.createRow(rowIterator)
                                            Cell valueCell
                                            mainHeadings.size().times {
                                                valueCell = datasetRow.createCell(it + 1)
                                                if (it == 0) { //B Unique incidence ID
                                                    valueCell.setCellType(CellType.NUMERIC)
                                                    valueCell.setCellValue(incrementingNumber)
                                                } else if (it == 1) { //C Elemental construction ID
                                                    if (dataset.parentConstructionId) {
                                                        valueCell.setCellType(CellType.STRING)
                                                        valueCell.setCellValue(dataset.parentConstructionId)
                                                    } else {
                                                        valueCell.setCellType(CellType.NUMERIC)
                                                        valueCell.setCellValue(incrementingNumber)
                                                    }
                                                } else if (it == 2) { //D Elemental construction name (COMMENT)
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (breeam && parentConstructionDataset) {
                                                        valueCell.setCellValue((String) parentConstructionDataset.additionalQuestionAnswers?.get("comment") ?: "")
                                                    } else {
                                                        valueCell.setCellValue((String) dataset.additionalQuestionAnswers?.get("comment") ?: "")
                                                    }
                                                } else if (it == 3) {
                                                    //E Elemental construction classification (elementCategory additionalquestion)
                                                    String selectedCat = dataset.additionalQuestionAnswers?.get("elementCategory")
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (breeam) {
                                                        valueCell.setCellValue(questionService.translateElementCategoryToBreeamCategory(selectedCat, dataset.questionId))
                                                    } else {
                                                        valueCell.setCellValue((String) elementCategory?.choices?.find({
                                                            it.answerId?.equals(selectedCat)
                                                        })?.answer?.get("EN") ?: "")
                                                    }

                                                } else if (it == 4) { //F Elemental construction quantity
                                                    valueCell.setCellType(CellType.NUMERIC)

                                                    if (parentConstructionDataset && breeam) {
                                                        if (parentConstructionKilograms) {
                                                            valueCell.setCellValue(1)
                                                        } else {
                                                            valueCell.setCellValue(parentConstructionDataset.quantity)
                                                        }
                                                    } else {
                                                        if (kilograms) {
                                                            valueCell.setCellValue(1)
                                                        } else {
                                                            valueCell.setCellValue(dataset.quantity)
                                                        }
                                                    }
                                                } else if (it == 5) { //G Elemental construction unit
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (parentConstructionDataset && breeam) {
                                                        if (parentConstructionKilograms) {
                                                            valueCell.setCellValue("#")
                                                        } else {
                                                            valueCell.setCellValue(parentConstructionDataset.userGivenUnit)
                                                        }
                                                    } else {
                                                        if (kilograms) {
                                                            valueCell.setCellValue("#")
                                                        } else {
                                                            valueCell.setCellValue(dataset.userGivenUnit)
                                                        }
                                                    }
                                                } else if (it == 7) { //I Product ID
                                                    valueCell.setCellType(CellType.STRING)
                                                    valueCell.setCellValue(resource.id.toString())
                                                } else if (it == 8) { //J Product name / description
                                                    valueCell.setCellType(CellType.STRING)
                                                    valueCell.setCellValue(resource.nameEN)
                                                } else if (it == 9) { //K Product type (generic)
                                                    if (resourceType || subType) {
                                                        String resourceTypeName = resourceTypeService.getLocalizedName(resourceType)
                                                        String resourceSubTypeName = resourceTypeService.getLocalizedName(subType)

                                                        String productType

                                                        if (resourceTypeName && resourceSubTypeName) {
                                                            productType = "${resourceTypeName}, ${resourceSubTypeName}"
                                                        } else {
                                                            productType = resourceTypeName ?: resourceSubTypeName ?: ""
                                                        }
                                                        valueCell.setCellType(CellType.STRING)
                                                        valueCell.setCellValue(productType)
                                                    }
                                                } else if (it == 10) { //L Product classification
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (breeam) {
                                                        valueCell.setCellValue(subType?.uniClassBRE ?: "")
                                                    } else {
                                                        valueCell.setCellValue(subType?.uniClass ?: "")
                                                    }
                                                } else if (it == 11) {
                                                    //M Product mass per element construction unit (kg)
                                                    valueCell.setCellType(CellType.NUMERIC)

                                                    if (mass) {
                                                        if (kilograms) {
                                                            valueCell.setCellValue(mass)
                                                        } else if (quantity) {
                                                            valueCell.setCellValue(mass / quantity)
                                                        }
                                                    }
                                                } else if (it == 12) {
                                                    //N Product mass per element construction unit (kg) - All life stages
                                                    if (mass && serviceLife && assessmentPeriod) {
                                                        valueCell.setCellType(CellType.NUMERIC)
                                                        Integer assessments = Math.ceil(assessmentPeriod / serviceLife).intValue()

                                                        if (assessments < 1) {
                                                            assessments = 1
                                                        }

                                                        if (kilograms) {
                                                            valueCell.setCellValue(mass * assessments)
                                                        } else if (quantity) {
                                                            valueCell.setCellValue((mass / quantity) * assessments)
                                                        }
                                                    }
                                                } else if (it == 13) { //O Product unit
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (parentConstructionDataset && breeam) {
                                                        if (parentConstructionKilograms) {
                                                            valueCell.setCellValue("#")
                                                        } else {
                                                            valueCell.setCellValue(parentConstructionDataset.userGivenUnit)
                                                        }
                                                    } else {
                                                        if (kilograms) {
                                                            valueCell.setCellValue("#")
                                                        } else {
                                                            valueCell.setCellValue(dataset.userGivenUnit)
                                                        }
                                                    }
                                                } else if (it == 14) { //P Product service life
                                                    valueCell.setCellType(CellType.STRING)
                                                    valueCell.setCellValue(serviceLife ? serviceLife.toString() : "")
                                                } else if (it == 15) {
                                                    //Q Product site waste (% of first installation quantity)
                                                    valueCell.setCellType(CellType.STRING)
                                                    valueCell.setCellValue((String) dataset.additionalQuestionAnswers?.get("wasteShareInUsePhase") ?: "")
                                                } else if (it == 16) { //R Product transport 1 - Distance (km)
                                                    valueCell.setCellType(CellType.STRING)
                                                    String transport

                                                    if (lcaForIMPACTcompliant) {
                                                        transport = dataset.additionalQuestionAnswers?.get("transportDistance_kmIMPACT")
                                                    } else {
                                                        transport = dataset.additionalQuestionAnswers?.get("transportDistance_km")
                                                    }

                                                    if (transport) {
                                                        if ("default".equals(transport) && transportDefault && subType) {
                                                            String quantityAnswer = DomainObjectUtil.callGetterByAttributeName(transportDefault, subType)?.toString()

                                                            if (quantityAnswer != null && quantityAnswer.isNumber()) {
                                                                valueCell.setCellValue(Math.round(quantityAnswer.toDouble()).intValue().toString())
                                                            }
                                                        } else {
                                                            valueCell.setCellValue(transport)
                                                        }
                                                    }
                                                } else if (it == 17) { //S Product transport 1 - Mode
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (lcaForIMPACTcompliant) {
                                                        valueCell.setCellValue(dataset.additionalQuestionAnswers?.get("transportResourceIdIMPACT1") ? optimiResourceService.getResourceNameEN(dataset.additionalQuestionAnswers.get("transportResourceIdIMPACT1").toString()) : "")
                                                    } else {
                                                        valueCell.setCellValue(dataset.additionalQuestionAnswers?.get("transportResourceId") ? optimiResourceService.getResourceNameEN(dataset.additionalQuestionAnswers.get("transportResourceId").toString()) : "")
                                                    }
                                                } else if (it == 19) { //U Product transport 2 - Mode
                                                    valueCell.setCellType(CellType.STRING)

                                                    if (lcaForIMPACTcompliant) {
                                                        valueCell.setCellValue(dataset.additionalQuestionAnswers?.get("transportResourceIdIMPACT2") ? dataset.additionalQuestionAnswers.get("transportResourceIdIMPACT2").toString() : "")
                                                    }
                                                } else if (it == 21) { //W All life stages
                                                    valueCell.setCellType(CellType.NUMERIC)
                                                    Double total = 0

                                                    excelExportCategoryIds.each { String resultCategoryId ->
                                                        Double result = entity.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategoryId, lcaForIMPACTcompliant ? "ecopointsImpact" : "GWP", resultsByIndicatorId)

                                                        if (result) {
                                                            total = total + result
                                                        }
                                                    }
                                                    valueCell.setCellValue(total)
                                                }
                                            }

                                            Integer resultCategoriesindex = mainHeadings.size() + 3
                                            resultCategories.each { ResultCategory resultCategory ->
                                                calculationRules.each { CalculationRule calculationRule ->
                                                    if (!resultCategory.ignoreFromTotals?.contains(calculationRule.calculationRuleId) || !resultCategory.ignoreFromTotals?.isEmpty()) {
                                                        valueCell = datasetRow.createCell(resultCategoriesindex)
                                                        valueCell.setCellType(CellType.NUMERIC)
                                                        valueCell.setCellValue((Double) entity.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRule.calculationRuleId, resultsByIndicatorId) ?: 0)
                                                        resultCategoriesindex++
                                                    }
                                                }
                                            }
                                            rowIterator++
                                            incrementingNumber++
                                        }
                                    }
                                }
                                Integer columnsToResize = (mainHeadings.size() + 3 + (resultCategories.size() * calculationRules.size()))

                                columnsToResize.times {
                                    if ([0, 7, 21, 23, 24].contains(it)) {
                                        sheet.setColumnWidth(it, 600)
                                    } else if ([19, 20].contains(it)) {
                                        sheet.setColumnHidden(it, true)
                                    } else {
                                        sheet.autoSizeColumn(it)
                                    }
                                }
                            }
                            if (breeam) {
                                workbooksToZip.add(wb)
                            }
                        }

                        try {
                            if (breeam) {
                                response.setContentType('APPLICATION/OCTET-STREAM')
                                response.setHeader('Content-Disposition', "Attachment;Filename=${indicatorId}_CompiledReport_${new Date().format('dd.MM.yyyy HH:mm:ss')}.zip")
                                ZipOutputStream zip = new ZipOutputStream(response.outputStream)
                                workbooksToZip.each { Workbook workbook ->
                                    String fileName = workbook.getSheetName(0) ?: "unnamed"
                                    workbook.setSheetName(0, "option")
                                    def fileEntry = new ZipEntry("${fileName}.xlsx")
                                    zip.putNextEntry(fileEntry)
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream()
                                    try {
                                        workbook.write(bos)
                                    } finally {
                                        bos.close()
                                    }
                                    zip.write(bos.toByteArray())
                                }
                                zip.flush()
                                zip.close()
                            } else {
                                response.contentType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
                                response.setHeader("Content-disposition", "attachment; filename=${indicatorId}_CompiledReport_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xlsx")
                                OutputStream outputStream = response.getOutputStream()
                                wb.write(outputStream)
                                outputStream.flush()
                                outputStream.close()
                            }
                        } catch (ClientAbortException e) {
                            // client disconnected before bytes were able to be written.
                        }
                    } else {
                        log.error("CompiledReport: unable to generate report for indicator: ${indicator?.indicatorId}, Valid resultCategories: ${resultCategories?.size()}, calculationRules: ${calculationRules?.size()}, queryIds ${validQueryIds}")
                        flash.warningAlert = "Compiled report generation unavailable for this indicator."
                        redirect controller: "entity", action: "show", id: parentEntityId
                    }
                } else {
                    log.error("CompiledReport: unable to generate report for indicator: ${indicator?.indicatorId}, Valid resultCategories: ${resultCategories?.size()}, calculationRules: ${calculationRules?.size()}")
                    flash.warningAlert = "Compiled report generation unavailable for this indicator."
                    redirect controller: "entity", action: "show", id: parentEntityId
                }
            } else {
                log.error("CompiledReport: unable to generate report with params: ${params}")
                flash.warningAlert = "Compiled report generation unavailable."
                redirect controller: "entity", action: "show", id: parentEntityId
            }
        } else {
            log.error("CompiledReport: unable to generate report with params: ${params}")
            flash.warningAlert = "Compiled report generation unavailable."
            redirect controller: "entity", action: "show", id: parentEntityId
        }
    }

    def sustainableAlternatives() {
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String resourceId = params.resourceId
        String area = params.area
        String subType = params.subType
        String connectedBenchmark = params.connectedBenchmark
        String projectCountry = params.projectCountry
        String output = "<table class='table-striped table '><tbody>"
        Boolean splitView = params.boolean('splitView')

        if (resourceId && area && subType && connectedBenchmark && projectCountry) {
            Map<String, List<Object>> resourceFilterCriteria = [:]
            Entity parentEntity = entityService.getEntityById(entityId)

            if (indicatorId) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(parentEntity, IndicatorQuery.RESOURCE, indicator?.indicatorQueries?.find({it.resourceFilterCriteria}))
            }
            Map<String, Resource> sustainableAlternatives = optimiResourceService.getBestResourceInCountryBySubTypeAndDataType(area, subType, connectedBenchmark, resourceId, resourceFilterCriteria, parentEntity?.countryResource?.neighbouringCountries)
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, null, true)
            if (!sustainableAlternatives.isEmpty() && resource && resource.resourceId?.equals(sustainableAlternatives.values()?.toList()?.get(0)?.resourceId)) {
                output = output + "<tr><td>${message(code: 'resource.most_sustainable')}   ${projectCountry}</td></tr>"
            } else if (!sustainableAlternatives.isEmpty() && resource && !resource.resourceId?.equals(sustainableAlternatives.values()?.toList()?.get(0)?.resourceId)) {
                output = output + "<thead><tr><th class='bold'>No.</th><th style='width:250px;' class='bold'>${message(code: 'resource.full_name')}</th><th style='width:100px;'>&nbsp;</th><th>${message(code: 'resource.performance_ranking')}</th><th>GWP CO<sub>2</sub> / ${resourceService.getSubType(resource)?.standardUnit}</th></tr></thead>"
                int i = 1
                String randomOriginal = UUID.randomUUID().toString()

                output = output + "<tr class=\"chosenResourceRow\"><td>Current</td><td>${abbr(maxLength: 125, value: resource.staticFullName)}</td><td>${opt.renderDataCardBtn(indicatorId: indicatorId, resourceId: resource?.resourceId, profileId: resource?.profileId, childEntityId: entityId, showGWP: true, infoId: randomOriginal + 'info', zIndex: 1050)}</td>"


                String performanceCloudOriginal
                Integer rankingOriginal
                String performanceRankingOriginal
                String validValuePerDataPropertyOriginal
                Double benchmarkValueOriginal

                if (resource.benchmark) {
                    Benchmark benchmark = resource.benchmark
                    Integer quintile = benchmark.quintiles?.get(connectedBenchmark) ? benchmark.quintiles.get(connectedBenchmark) : benchmark.co2Quintile



                    if (quintile > 0) {
                        performanceCloudOriginal = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" :
                                quintile == 2 ? "orangeEmissions" :
                                        quintile == 1 ? "redEmissions" : ""
                    }


                    if (benchmark.values?.get(connectedBenchmark) && !"NOT_CALCULABLE".equals(benchmark.values?.get(connectedBenchmark))) {
                        ResourceType subTypeForAlt = resourceTypeService.getResourceType(resource.resourceType, resource.resourceSubType, true)
                        Map<String, Integer> validValues = subTypeForAlt.benchmarkResult?.validValues
                        benchmarkValueOriginal = benchmark.values?.get(connectedBenchmark)?.toDouble()

                        rankingOriginal = benchmark?.ranking?.get(connectedBenchmark)
                        validValuePerDataPropertyOriginal = validValues?.get(connectedBenchmark)

                        if (rankingOriginal && validValuePerDataPropertyOriginal) {
                            performanceRankingOriginal = "${rankingOriginal} / ${validValuePerDataPropertyOriginal}"
                        }
                    }


                    if (performanceRankingOriginal) {
                        String link = "<i class=\"fas fa-chart-line benchmarkGraph\" onclick=\"fullScreenPopup('${createLink(controller: "resourceType", action: "benchmarkGraph", params: [resourceSubTypeId: resource.resourceTypeObject?.id, resourceId: resource.resourceId, profileId: resource.profileId, indicatorId: indicatorId, entityId: entityId, benchmarkToShow: connectedBenchmark])}');\"></i><br/>"
                        output = output + "<td> ${performanceRankingOriginal ? performanceRankingOriginal : ''} ${performanceCloudOriginal ? "<div class=\"co2CloudContainer\"><i class=\"fa fa-cloud ${performanceCloudOriginal}\"></i><p class=\"co2Text\">co<sub>2</sub></p></div>" : ""}" +
                                "${benchmark.values.get(connectedBenchmark) && !"NOT_CALCULABLE".equals(benchmark.values.get(connectedBenchmark)) ? " ${message(code: 'resource.see_full_ranking')}: ${link}" : ''}</td>"
                    } else {
                        output = output + "<td>none available</td>"
                    }

                    if (benchmarkValueOriginal) {
                        output = output + "<td>${benchmarkValueOriginal.round(3)}</td>"
                    } else {
                        output = output + "<td>none available</td>"
                    }
                }

                output = output + "</tr>"

                sustainableAlternatives?.values()?.each { Resource r ->
                    Double benchmarkValue
                    String random = UUID.randomUUID().toString()
                    output = output + "<tr><td>${i}.</td><td><span> ${splitView ? "<a href=\"javascript:\" onclick=\"appendSustainableResourceToSelect(\'${r.staticFullName}\', \'${r.resourceId}\')\">${abbr(maxLength: 125, value: r.staticFullName)}</a> " : "${abbr(maxLength: 125, value: r.staticFullName)}"} </span></td><td>${opt.renderDataCardBtn(indicatorId: indicatorId, resourceId: r?.resourceId, profileId: r?.profileId, childEntityId: entityId, showGWP: true, infoId: random)}</td>"
                    output = output + "<input type=\"hidden\" id=\"copyTarget\" value=\"${r.staticFullName}\">"

                    String performanceCloud
                    Integer ranking
                    String performanceRanking
                    String validValuePerDataProperty

                    if (r.benchmark) {
                        Benchmark benchmark = r.benchmark
                        Integer quintile = benchmark.quintiles?.get(connectedBenchmark) ? benchmark.quintiles.get(connectedBenchmark) : benchmark.co2Quintile



                        if (quintile > 0) {
                            performanceCloud = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" :
                                    quintile == 2 ? "orangeEmissions" :
                                            quintile == 1 ? "redEmissions" : ""
                        }


                        if (benchmark.values?.get(connectedBenchmark) && !"NOT_CALCULABLE".equals(benchmark.values?.get(connectedBenchmark))) {
                            ResourceType subTypeForAlt = resourceTypeService.getResourceType(r.resourceType, r.resourceSubType, true)
                            Map<String, Integer> validValues = subTypeForAlt.benchmarkResult?.validValues
                            benchmarkValue = benchmark.values?.get(connectedBenchmark)?.toDouble()
                            ranking = benchmark?.ranking?.get(connectedBenchmark)
                            validValuePerDataProperty = validValues?.get(connectedBenchmark)

                            if (ranking && validValuePerDataProperty) {
                                performanceRanking = "${ranking} / ${validValuePerDataProperty}"
                            }
                        }


                        if (performanceRanking) {
                            String link = "<i class=\"fas fa-chart-line benchmarkGraph\" onclick=\"fullScreenPopup('${createLink(controller: "resourceType", action: "benchmarkGraph", params: [resourceSubTypeId: r.resourceTypeObject?.id, resourceId: r.resourceId, profileId: r.profileId, indicatorId: indicatorId, entityId: entityId, benchmarkToShow: connectedBenchmark])}');\"></i><br/>"
                            output = output + "<td> ${performanceRanking ? performanceRanking : ''} ${performanceCloud ? "<div class=\"co2CloudContainer\"><i class=\"fa fa-cloud ${performanceCloud}\"></i><p class=\"co2Text\">co<sub>2</sub></p></div>" : ""}" +
                                    "${benchmark.values.get(connectedBenchmark) && !"NOT_CALCULABLE".equals(benchmark.values.get(connectedBenchmark)) ? " ${message(code: 'resource.see_full_ranking')}: ${link}" : ''}</td>"
                        } else {
                            output = output + "<td>none available</td>"
                        }
                    }

                    if (benchmarkValue) {
                        output = output + "<td>${benchmarkValue?.round(3)}</td>"
                    } else {
                        output = output + "<td>none available</td>"
                    }
                    i++
                    output = output + "</tr>"
                }
                output = output + "</tbody></table>"
            } else {
                String link = "<a style=\"cursor: pointer;\" onclick=\"fullScreenPopup('${createLink(controller: "resourceType", action: "benchmarkGraph", params: [resourceSubTypeId: resource?.resourceTypeObject?.id, resourceId: resource?.resourceId, profileId: resource?.profileId, benchmarkToShow: connectedBenchmark, entityId: entityId])}');\">${message(code: 'resource.country')}</a><br/>"
                output =  "<span class='text-center'>${message(code: 'resource.no.benchmarked_alternatives', args: [optimiResourceService.getLocalizedName(resource), projectCountry])}. </span> " +
                        "${message(code: 'resource.look_for_alternatives')} ${link}"
            }
        } else {
            log.error("No alternatives found for ${resourceId}")
            flashService.setErrorAlert("No alternatives found for ${resourceId}", true)
            output = "${message(code: 'resource.sourcelisting_no_information_available')}"
        }
        render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def sourceListingExternal() {
        String resourceDbId = params.resourceInternalId
        if (resourceDbId) {
            Resource resource = optimiResourceService.getResourceByDbId(resourceDbId)

            if (resource) {

                String content = ""
                if (resource.staticFullName) {
                    content = content + "<strong>Full name:</strong> ${resource.staticFullName}"
                    content = content + "<input type=\"hidden\" id=\"copyTarget\" value=\"${resource.staticFullName}\"> <br />"
                }
                if (resource.areas) {
                    List<String> isoCodes = []
                    Map<String, String> isoFlagPaths = [:]

                    String globe = "/app/assets/isoflags/globe.png"

                    if (!resource?.isoCodesByAreas) {
                        isoCodes = optimiResourceService.getresourceIsoCodes(resource.areas, resource.additionalArea)?.values()?.toList()
                    } else {
                        isoCodes = resource?.isoCodesByAreas?.values()?.toList()
                    }

                    if (isoCodes) {
                        isoCodes.collect({ it.toLowerCase() }).each { String isoCountryCode ->
                            String isoFlag

                            if (System.getProperty("islocalhost")) {
                                isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                                if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                    log.error("missing flag for countrycode ${isoCountryCode}")
                                    isoFlag = globe
                                }
                            } else {
                                String isoFilePath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "isoFlagUrlPath")
                                def testIsoFilePath = new File("/var/www/${isoFilePath}${isoCountryCode}.png")
                                if (testIsoFilePath.exists()) {
                                    isoFlag = "${isoFilePath}${isoCountryCode}.png"

                                } else {
                                    log.error("missing flag for countrycode ${isoCountryCode} with filepath ${testIsoFilePath?.absolutePath} from server")
                                    isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                                    if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                                        log.error("missing flag for countrycode ${isoCountryCode} from assets")
                                        isoFlag = globe
                                    }
                                }
                            }
                            if (isoFlag) {
                                isoFlagPaths.put(resource?.isoCodesByAreas?.find({
                                    it.value == isoCountryCode
                                })?.key?.toString()?.toLowerCase(), "<img src=\'${isoFlag}\'  class=\'miniFlag\'/>")
                            }
                        }
                    }

                    content = content + "<strong>${message(code: 'resource.country')}:</strong> "
                    resource.areas.each { String area ->
                        content = content + optimiResourceService.getCountryLocalizedName(area)?.capitalize() + " ${isoFlagPaths?.get(area?.toLowerCase() ?: '')} "
                    }
                    content = content + "<br />"

                }
                if (resource.commercialName) {
                    content = content + "<strong>${message(code: 'resource.product')}:</strong> ${resource.commercialName} <br />"

                }
                if (resource.density) {
                    content = content + "<strong>${message(code: 'resource.density')}</strong> ${resource.density} <br />"

                }
                if (resource.environmentDataPeriod) {
                    content = content + "<strong>${message(code: 'resource.date')}:</strong> ${resource.environmentDataPeriod} <br />"
                }

                if (resource.environmentDataSource) {
                    content = content + "<strong>${message(code: 'resource.environmentDataSource')}:</strong> ${resource.environmentDataSource}<br />"

                }
                if (resource.environmentDataSourceStandard) {
                    content = content + "<strong>${message(code: 'resource.standard')}:</strong> ${resource.environmentDataSourceStandard}<br />"

                }
                if (resource.epdNumber) {
                    content = content + "<strong>${message(code: 'resource.epd_number')}:</strong> ${resource.epdNumber} <br />"
                }
                if (resource.epdProgram) {
                    content = content + "<strong>${message(code: 'resource.epd_program')}:</strong> ${resource.epdProgram} <br />"
                }
                if (resource.technicalSpec) {
                    content = content + "<strong>${message(code: 'resource.techSpec')}</strong> ${resource.technicalSpec} <br />"
                }
                if (resource.manufacturer) {
                    content = content + "<strong>${message(code: 'resource.manufacturer')}:</strong> ${resource.manufacturer} <br />"

                }
                if (resource.upstreamDB) {
                    content = content + "<strong>${message(code: 'resource.upstreamDB')}:</strong> ${resource.upstreamDB} <br />"
                }
                if (resource.version) {
                    content = content + "<strong>${message(code: 'resource.verification')}:</strong> ${resource.version}<br /> "
                }




                if (resource.impactGWP100_kgCO2e != null) {
                    content = content + "<strong>${message(code: 'resource.impactGWP100_kgCO2e')}:</strong> ${String.format("%.2f", resource.impactGWP100_kgCO2e)} kg CO<sub>2</sub>e / ${resource.unitForData ? resource.unitForData : resource.unitForData}<br />"
                } else if (resource.traciGWP_kgCO2e != null) {
                    content = content + "<strong>${message(code: 'resource.impactGWP100_kgCO2e')}:</strong> ${String.format("%.2f", resource.traciGWP_kgCO2e)} kg CO<sub>2</sub>e / ${resource.unitForData ? resource.unitForData : resource.unitForData}<br />"
                } else if (resource.impactGWP_direct_kgCO2e != null) {
                    content = content + "<strong>${message(code: 'resource.impactGWP100_kgCO2e')}:</strong> ${String.format("%.2f", resource.impactGWP_direct_kgCO2e)} kg CO<sub>2</sub>e / ${resource.unitForData ? resource.unitForData : resource.unitForData}<br />"
                }

                if (resource?.benchmark && "OK".equals(resource.benchmark.status)) {
                    Benchmark benchmark = resource.benchmark
                    ResourceType subType = resourceTypeService.getResourceType(resource?.resourceType, resource?.resourceSubType, Boolean.TRUE)

                    if (subType && subType.isSubType && subType.standardUnit && subType.benchmarkResult) {
                        Map<String, Integer> validValues = subType.benchmarkResult.validValues

                        if (validValues) {
                            Integer ranking
                            String performanceRanking
                            String validValuePerDataProperty
                            Integer quintile = benchmark.co2Quintile
                            String performanceCloud

                            if (quintile > 0) {
                                performanceCloud = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" :
                                        quintile == 2 ? "orangeEmissions" :
                                                quintile == 1 ? "redEmissions" : ""
                            }

                            if (resource.dataProperties?.contains("CML") || resource.dataProperties?.contains("IMPACT")) {
                                ranking = benchmark.ranking.get("co2_cml")
                                validValuePerDataProperty = validValues.get("co2_cml")
                                performanceRanking = "${ranking} / ${validValuePerDataProperty}"

                            } else if (resource.dataProperties?.contains("TRACI")) {
                                ranking = benchmark.ranking.get("co2_traci")
                                validValuePerDataProperty = validValues.get("co2_traci")
                                performanceRanking = "${ranking} / ${validValuePerDataProperty}"
                            }

                            if (performanceRanking) {
                                content = content + "<strong>${message(code: 'resource.performance_group')}</strong> ${resourceTypeService.getLocalizedName(subType)}<br />"
                                content = content + "<strong>${message(code: 'resource.performance_ranking')}</strong> ${performanceRanking ? performanceRanking : ''}${quintile && quintile > 0 && quintile <= 5 ? " <div class=\"co2CloudContainer\"><i class=\"fa fa-cloud ${performanceCloud}\"></i><p class=\"co2Text\">co<sub>2</sub></p></div>" : ""} <br />"
                                content = content + "${message(code: 'resource.ranking_help_1')} ${resourceTypeService.getLocalizedName(subType)?.toLowerCase()}.  ${message(code: 'resource.ranking_help_2')}"
                            }
                        }
                    }

                }
                render template: "sourceListingExternal", model: [renderContent: content]
            } else {
                render text: "${message(code: 'resource.sourcelisting_no_information_available')}"
            }
        } else {
            render text: "${message(code: 'resource.sourcelisting_no_information_available')}"

        }
    }

    def renderCollapserData() {
        def datasetManualIds = params.datasetIds
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        ImportMapper importMapper = session?.getAttribute("importMapper")
        String content = ""

        if (datasetManualIds && ifcDatasets && importMapper) {
            List<Dataset> groupDatasets = ifcDatasets.findAll({ datasetManualIds.contains(it.manualId) })

            if (groupDatasets) {
                List<String> headings = groupDatasets.collect({
                    it.dataForCollapser?.keySet()?.toList()
                })?.flatten()?.unique()


                if (headings) {
                    content = content + "<table class=\"collapser_datasets\"><tr>"

                    headings.each { String heading ->
                        content = content + "<th>${heading}</th>"
                    }
                    content = content + "</tr>"

                    groupDatasets.each { Dataset dataset ->
                        content = content + "<tr>"

                        headings.each { String heading ->
                            content = content + "<td>${dataset.dataForCollapser?.get(heading) ?: ""}</td>"
                        }
                        content = content + "</tr>"
                    }
                }
                content = content + "</table>"
            }
            render([output: content, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: "No information available", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def renderGroupedByData() {
        String datasetClass = params.datasetClass
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        ImportMapper importMapper = session?.getAttribute("importMapper")

        if (datasetClass && ifcDatasets && importMapper) {

            List<Dataset> groupedByClassDatasets = ifcDatasets.findAll { Dataset dataset ->
                datasetClass.equalsIgnoreCase(dataset.trainingData?.get("CLASS"))
            }

            Boolean areResourceDatasets = importMapperService.isResourceDataset(groupedByClassDatasets?.find())
            List<String> headers = areResourceDatasets ? importMapperService.getResourceDatasetDisplayHeadersForGroupedBy(importMapper.dataMappingRules) : importMapperService.getDescriptiveDatasetDisplayHeaders() - "CLASS"

            String content = "<table class=\"collapser_datasets table-striped\"><tr>"
            headers?.each {
                content = content + "<th>${it}</th>"
            }
            content = content + "<th>&nbsp;</th></tr>"

            groupedByClassDatasets?.each { Dataset dataset ->
                Map<String, String> displayData = areResourceDatasets ? dataset.trainingData : dataset.descriptiveDisplayData
                content = content + "<tr>"
                headers?.each { String header ->
                    content = content + "<td>${displayData?.get(header) ?: "-"}</td>"
                }
                content = content + "<td><a href=\"javascript:\" class=\"btn btn-danger deleteDataset\" id=\"deleteDataset\" onclick=\"deleteIfcDataset(this,'${dataset.manualId}','${datasetClass.replaceAll("\\s", "")}AmountContainer','${datasetClass}')\">Delete</a></td></tr>"
            }
            content = content + "</table>"

            render([output: content, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: "No information available", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def deleteIfcDataset() {
        String manualId = params.datasetId
        String datasetClass = params.datasetClass
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        Map<String, Integer> amountOfGroupedByData = session?.getAttribute("amountOfGroupedByData")

        if (manualId && ifcDatasets && amountOfGroupedByData && datasetClass) {
            boolean removed = ifcDatasets.removeIf{ it.manualId.equals(manualId) }
            if (removed) {
                session?.setAttribute("ifcDatasets", ifcDatasets)
                Integer amount = amountOfGroupedByData.get(datasetClass)

                if (amount != null) {
                    amountOfGroupedByData.put(datasetClass, amount - 1)
                    session?.setAttribute("amountOfGroupedByData", amountOfGroupedByData)
                } else {
                    flashService.setErrorAlert("No amount can be retrieved from amountOfGroupedByData: $amountOfGroupedByData with datasetClass $datasetClass", true)
                }
            } else {
                flashService.setErrorAlert("No dataset can be found with manualId $manualId", true)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params for deleteIfcDataset", true)
        }
        render([output: "true", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def renderDeniedDatasets() {
        List<Dataset> datasets = session?.getAttribute("tooGenericDatasets")
        ImportMapper importMapper = session?.getAttribute("importMapper")
        String content = ""

        if (datasets) {
            content = content + "<table class=\"collapser_datasets\"><tr>"
            importMapper.dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->
                if ("RESOURCEID".equalsIgnoreCase(importMapperDataRule.target)) {
                    content = content + "<th>${importMapperDataRule.target.toUpperCase()}</th>"
                }
            }
            content = content + "</tr>"

            datasets.each { Dataset dataset ->
                content = content + "<tr>"
                importMapper.dataMappingRules?.each { ImportMapperDataRule importMapperDataRule ->
                    if ("RESOURCEID".equalsIgnoreCase(importMapperDataRule.target)) {
                        content = content + "<td>${dataset.trainingData?.get(importMapperDataRule.target.toUpperCase()) ? dataset.trainingData?.get(importMapperDataRule.target.toUpperCase()) : "-"}</td>"
                    }
                }
                content = content + "</tr>"
            }
            content = content + "</table>"

            render([output: content, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: "No information available", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def renderSystemTrainingData() {
        String fileName = params.fileName
        String applicationId = params.applicationId
        String content = ""

        if (fileName && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)
            SystemTrainingDataSet systemTrainingDataSet = application.systemTrainingDatas.find({
                it.fileName.equals(fileName)
            })
            content = content + "<table class=\"trainingData\"><tr><th>Data</th></tr>"

            systemTrainingDataSet?.systemMatches?.each { String data ->
                content = content + "<tr><td>${data}</td></tr>"
            }
            content = content + "</table>"
            render([output: content, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: "No information available", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }


    def addLanguageTextField() {

        if (params.language && params.name) {
            String language = params.language
            String name = params.name

            def deleteText = message(code: "delete") ?: ""
            def resource = optimiResourceService.getSystemLocales().find({
                it.resourceId.equals(language)
            })
            def label = optimiResourceService.getLocalizedName(resource) ?: ""

            def nameLabel = "${params.name}[${params.language}]"
            String templateAsString = g.render(template: "/notification/textFieldTemplate", model: [name: name, language: language,
                                                                                                    deleteText : deleteText, label: label,
                                                                                                    nameLabel: nameLabel ]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            flashService.setErrorAlert("Incorrect params in addLanguageTextField $params", true)
            render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def addLanguageTextArea() {
        if (params.language && params.name) {
            def delete = message(code: "delete")
            def resource = optimiResourceService.getSystemLocales().find({
                it.resourceId.equals(params.language)
            })
            def label = optimiResourceService.getLocalizedName(resource)
            def name = "${params.name}[${params.language}]"
            def textArea = "            <tr id=\"${params.name}_${params.language}\">\n" +
                    "                        <td>${label}</td>\n" +
                    "                        <td>${textArea(name: name, class: "input-xlarge")}</td>\n" +
                    "                        <td><input type=\"button\" class=\"btn btn-danger\" value=\"${delete}\" onclick=\"\$('#${params.name}_${params.language}').remove();\" /></td>" +
                    "                    </tr>"
            render([output: textArea, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            flashService.setErrorAlert("Incorrect params in addLanguageTextField $params", true)
            render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def getResourceProfiles() {
        String resourceId = params.resourceId
        String options = ""

        if (resourceId) {
            if (resourceId.contains(" ")) {
                resourceId = resourceId.split(" ")[0]
            }
            Resource resource = optimiResourceService.getResourceWithParams(resourceId, null, null)

            if (resource) {
                def profiles = resourceService.getProfiles(resource.resourceId)

                if (profiles) {

                    profiles?.each { profile ->
                        options = options +
                                "<option value='${profile.profileId}'${profile.defaultProfile ? " selected='selected'" : ""}>" +
                                "${optimiResourceService.getLocalizedProfileName(profile)}</option>"
                    }
                }
            }
        }
        render([output: options, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def renderPreviousOperatingPeriods() {
        String parentEntityId = params.parentEntityId
        def previousOperatingPeriods = entityService.getPreviousOperatingPeriods(parentEntityId, params.entityId)
        String tableData

        if (previousOperatingPeriods) {
            Entity parentEntity = entityService.getEntityById(parentEntityId)
            def indicatorId = params.indicatorId
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            def queryId = params.queryId
            def sectionId = params.sectionId
            def questionId = params.questionId
            Query query = queryService.getQueryByQueryId(queryId, true)
            QuerySection section = query.sections?.find({ it.sectionId.equals(sectionId) })
            Question question = query.getQuestionsBySection(sectionId)?.find({ it.questionId.equals(questionId) })
            def additionalQuestions = question.defineAdditionalQuestions(indicator, section, null, parentEntity)
            def resourceIds = question.getAdditionalQuestionResources(indicator, queryId)?.collect({ it.resourceId.toString() })
            def datasets
            String targetTableId = params.targetTableId

            if (resourceIds) {
                tableData = "<table class=\"left_alignment\" id=\"innerTable${targetTableId}\"><tr><th>&nbsp;</th><th>${message(code: 'query.material')}</th>"

                if (question.quantity) {
                    tableData = tableData + "<th>${indicator?.requireMonthly ? message(code: 'totalScore') : message(code: 'query.quantity')}</th>"
                }

                additionalQuestions?.each { Question additionalQuestion ->
                    tableData = tableData + "<th>${additionalQuestion.localizedQuestion}</th>"
                }

                if (indicator?.requireMonthly) {
                    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].each {
                        tableData = tableData + "<th>${message(code: "month.${it}")}</th>"
                    }
                }

                tableData = tableData + "</tr>"
                previousOperatingPeriods.each { Entity e ->
                    datasets = datasetService.getDatasetsForResources(e, queryId, sectionId, questionId, resourceIds)?.sort()
                    tableData = tableData + "<tr><th>${e.operatingPeriod}</th>"
                    def index = 0

                    if (datasets) {
                        ResourceCache resourceCache = ResourceCache.init(datasets)
                        for (Dataset d: datasets) {
                            if (index > 0) {
                                tableData = tableData + "<tr><th>&nbsp;</th>"
                            }
                            tableData = tableData + "<td>${optimiResourceService.getLocalizedName(resourceCache.getResource(d))}</td>"

                            if (question.quantity) {
                                tableData = tableData + "<td>${d.answerIds[0]}</td>"
                            }

                            additionalQuestions?.each { Question additionalQuestion ->
                                tableData = tableData + "<td>${d.additionalQuestionAnswers?.get(additionalQuestion.questionId) ? d.additionalQuestionAnswers?.get(additionalQuestion.questionId) : ''}</td>"
                            }

                            if (indicator?.requireMonthly) {
                                [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12].each {
                                    tableData = tableData + "<td>${d.monthlyAnswers?.get(it.toString()) ?: ''}</td>"
                                }
                            }

                            tableData = tableData + "</tr>"
                            index++
                        }
                    }
                }
            } else {
                tableData = "<table class=\"left_alignment\">"
                previousOperatingPeriods.each { Entity e ->
                    def answers = ""
                    datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(e, queryId, sectionId, questionId)
                    tableData = tableData + "<tr><th>${e.operatingPeriod}</th>"
                    def index = 0

                    datasets?.each { Dataset d ->
                        if (index > 0) {
                            tableData = tableData + "<tr><th>&nbsp;</th>"
                        }
                        d.answerIds?.each {
                            answers = answers + it + "<br />"
                        }
                        tableData = tableData + "<td>${answers}</td>"
                        tableData = tableData + "</tr>"
                        index++
                    }
                }
            }
            tableData = tableData + "</table>"
        }

        if (!tableData) {
            tableData = message(code: "no_previous.operating_period")
        }
        render([output: tableData, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }


    def renderDesignResults() {
        String renderString = ''
        String designId = params.designId

        if (designId) {
            Entity design = entityService.getEntityById(designId, session)

            if (design) {
                String indicatorId = params.indicatorId
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                String queryId = params.queryId
                String sectionId = params.sectionId
                String questionId = params.questionId
                String currentDesignId = params.currentDesignId
                Boolean modifiable = params.boolean('modifiabled')

                Query query = queryService.getQueryByQueryId(queryId, true)
                QuerySection section = query.sections?.find({ (it.sectionId == sectionId) })
                Question question = (query.getQuestionsBySection(sectionId) as List<Question>)?.find { (it.questionId == questionId) }
                List<Question> additionalQuestions = question?.defineAdditionalQuestions(indicator, section, null, design.parentById)

                // the headline
                renderString = '<h4 style="min-width: 450px">'
                renderString += "${message(code: 'compare')}: ${design.name} - ${question?.localizedQuestion}"
                renderString += '</h4>'

                List<Dataset> datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(design, queryId, sectionId, questionId)

                if (!datasets) {
                    renderString += '<table><tr><td>' + message(code: 'resource.sourcelisting_no_information_available') + '</td></tr></table>'
                } else {
                    boolean nonResourceBasedQuestion = datasets.findAll { !it.resourceId }?.size() == datasets.size()

                    // the columns headers
                    renderString += '<table class="left_alignment">'
                    renderString += '<tr>'
                    renderString += '<th>'
                    renderString += nonResourceBasedQuestion ? message(code: 'answer') : message(code: 'query.material')
                    renderString += '</th>'
                    renderString += question?.quantity ? "<th class=\"right-align\">${message(code: 'query.quantity')}</th><th>${message(code: 'entity.show.unit')}</th>" : ''
                    additionalQuestions?.each { Question additionalQuestion ->
                        renderString += "<th class=\"right-align\">${additionalQuestion.localizedQuestion}</th>"
                    }
                    renderString += '</tr>'

                    if (nonResourceBasedQuestion) {
                        datasets.each { Dataset d ->
                            if (d) {
                                renderString += '<tr class="highlightHover">'
                                String answer = questionService.getAnswerForNonResourceBasedQuestion(question, d)
                                renderString += "<td>${answer}</td>"
                                additionalQuestions?.each { Question additionalQuestion ->
                                    String additionalAnswer = d?.additionalQuestionAnswers?.get(additionalQuestion.questionId)
                                    renderString += additionalAnswer ? "<td class=\"right-align\">${additionalAnswer}</td>" : '<td></td>'
                                }
                                renderString += '</tr>'
                            }
                        }
                    } else {
                        ResourceCache resourceCache = ResourceCache.init(datasets)
                        for (Dataset d: datasets) {
                            Resource resource = resourceCache.getResource(d)
                            if (resource && resource.construction || !d.parentConstructionId) {
                                renderString += '<tr class="highlightHover">'
                                String profileId = d?.profileId
                                String resourceId = resource.resourceId

                                renderString += d.groupingDatasetName ? "<td>${d.groupingDatasetName} </td>" : optimiResourceService.getLocalizedName(resource) ? "<td>${optimiResourceService.getLocalizedName(resource)}</td>" : ''

                                if (question?.quantity) {
                                    renderString += "<td class=\"right-align\">${d?.answerIds?.size() > 0 ? d?.answerIds[0] : ''}</td><td>${d?.userGivenUnit ?: ''}</td>"
                                }

                                additionalQuestions?.each { Question additionalQuestion ->
                                    int nameMaxLength = additionalQuestion.inputWidth ? (additionalQuestion.inputWidth / 4) <= 3 ? 3 : (additionalQuestion.inputWidth / 4) : 20 // Magic

                                    String answer = d?.additionalQuestionAnswers?.get(additionalQuestion.questionId)

                                    if (answer) {
                                        if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID && answer == "noLocalCompensation") {
                                            renderString += "<td class=\"right-align\">${message(code: 'notApplied')}</td>"
                                        } else if (additionalQuestion.resourceGroups) {
                                            renderString += "<td class=\"right-align\">${optimiResourceService.getLocalizedName(Resource.findByResourceIdAndActive(answer, true)) ?: ''}</td>"
                                        } else if (additionalQuestion.choices) {
                                            renderString += "<td class=\"right-align\">${additionalQuestion.choices.find({ it.answerId?.equals(answer) })?.localizedAnswer}</td>"
                                        } else if (additionalQuestion.isTransportQuestion) {
                                            if ("default" == answer) {
                                                String transportString = design?.getDefaultTransportFromSubType(resource)
                                                renderString += "<td class=\"right-align\">${transportString}</td>"
                                            } else {
                                                renderString += "<td class=\"right-align\">${answer}</td>"
                                            }
                                        } else if (additionalQuestion.questionId == Constants.DEFAULT_SERVICELIFE) {
                                            String serviceLife = "${newCalculationServiceProxy.getServiceLife(d, resource, design, indicator)?.toInteger() ?: ""}"
                                            if ("-1" == serviceLife) {
                                                renderString += "<td class=\"right-align\">${message(code: "resource.serviceLife.as_building")}</td>"
                                            } else {
                                                renderString += "<td class=\"right-align\">${serviceLife}</td>"
                                            }
                                        } else if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.EOL_QUESTIONID) {
                                            renderString += "<td class=\"right-align\">" +
                                                    "${stringUtilsService.abbr(eolProcessService.getLocalizedName(eolProcessService.getEolProcessByEolProcessId(answer)), nameMaxLength, true)}</td>"
                                        } else if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                                            String energyProfileString = ''
                                            if (answer && answer.tokenize(".")?.size() == 2) {
                                                energyProfileString = "${answer.tokenize(".")[1]}"
                                            }
                                            renderString += "<td class=\"right-align\">${energyProfileString}</td>"
                                        } else {
                                            renderString += "<td class=\"right-align\">${answer}</td>"
                                        }
                                    } else {
                                        renderString += '<td class=\"right-align\"></td>'
                                    }
                                }
                                if (modifiable && resource.active && !indicator?.maxRowLimitPerDesign) {
                                    if (resource.construction) {
                                        String uniqueConstructionIdentifier = resource.constructionId
                                        renderString += "<td style=\"white-space: nowrap\" ><a class=\"dropdown bold\" onclick=\'expandAnswerSection(\"#toggleAnswerSectionIcon-${sectionId}-${questionId}\"); executeCopyConstructionFromOtherDesign(\"${currentDesignId}\", null, null , \"${sectionId + questionId + 'ResourcesSelect'}\",\"${indicatorId}\", \"${queryId}\", \"${sectionId}\", \"${questionId}\", \"${sectionId + questionId + 'Resources'}\", \"${sectionId + '.' + questionId}\", \"${question?.preventDoubleEntries}\", true, \"${message(code: 'query.question.prevent_double_entries')}\",\"${uniqueConstructionIdentifier}\" , null, \"${resourceId + '.' + profileId}\",null,null, \"${resourceId + '.' + profileId}\", \"${designId}\", \"${d.manualId}\", \"${d.uniqueConstructionIdentifier}\", ${resource.isDummy}, \"${d.quantity}\");\'><u>${message(code: 'query_form.add_to_input')}<u/></a></td>"
                                    } else {
                                        renderString += "<td style=\"white-space: nowrap\" ><a class=\"dropdown bold\" onclick=\'expandAnswerSection(\"#toggleAnswerSectionIcon-${sectionId}-${questionId}\"); addResourceFromSelect(\"${currentDesignId}\", null, null , \"${sectionId + questionId + 'ResourcesSelect'}\",\"${indicatorId}\", \"${queryId}\", \"${sectionId}\", \"${questionId}\", \"${sectionId + questionId + 'Resources'}\", \"${sectionId + '.' + questionId}\", \"${question?.preventDoubleEntries}\", true, \"${message(code: 'query.question.prevent_double_entries')}\", null, null, \"${resourceId + '.' + profileId}\",null,null, \"${resourceId + '.' + profileId}\", \"${designId}\", \"${d.manualId}\")\'><u>${message(code: 'query_form.add_to_input')}</u></a></td>"
                                    }
                                }
                                renderString += '</tr>'
                            }
                        }
                    }
                    renderString += '</table>'
                }
            }
        }
        render([output: renderString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addCompositeRow() {
        String datasetId = params.datasetId
        String resourceGroups = params.resourceGroups
        String detailsKey = params.detailsKey
        String indicatorId = params.indicatorId

        if (datasetId) {
            List<Dataset> datasets = session?.getAttribute("ifcDatasets")
            Dataset original = datasets?.find({ datasetId.equals(it.manualId) })

            if (original) {
                Dataset dataset = datasetService.createCopy(original)
                dataset.manualId = new ObjectId().toString()
                datasets.add(dataset)
                dataset.importDisplayFields = null
                session.setAttribute("ifcDatasets", datasets)
                String templateAsString = g.render(template: "/importMapper/compositeRow", model: [dataset          : dataset, resourceGroups: resourceGroups,
                                                                                                   originalDatasetId: datasetId, detailsKey: detailsKey, indicatorId: indicatorId]).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        }
    }

    /* Hung: comment out since it's not in use anywhere 19.02.2021. Remove after few months. If decide to put in use, fix the indicatorXmlMapper.source
    def copyAnswerFromRset() {
        String entityId = params.entityId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String indicatorId = params.indicatorId
        String answer = ""

        if (indicatorId && entityId && queryId && sectionId && questionId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            List<IndicatorXmlMapper> indicatorXmlMappers = indicator?.readValuesFromXML
            IndicatorXmlMapper indicatorXmlMapper = indicatorXmlMappers?.find({
                queryId.equals(it.target.queryId) &&
                        sectionId.equals(it.target.sectionId) && questionId.equals(it.target.questionId)
            })
            Entity entity = entityService.getEntityWithProjection(entityId, [datasets: 1])

            if (entity && indicatorXmlMapper) {
                EntityFile entityFile = indicator?.rsetSource?.getFileForEntity(entity)

                if (entityFile) {
                    RSET rset = XMLService.getRsetFromDataset(entityFile)

                    if (rset) {
                        def xmlObject
                        int index = 0

                        indicatorXmlMapper.source?.tokenize('.')?.each { String xmlElementOrAttribute ->
                            if (index == 0) {
                                xmlObject = DomainObjectUtil.callGetterByAttributeName(xmlElementOrAttribute, rset)
                            } else {
                                xmlObject = DomainObjectUtil.callGetterByAttributeName(xmlElementOrAttribute, xmlObject)
                            }
                            index++
                        }

                        if (xmlObject) {
                            if (xmlObject instanceof String) {
                                answer = xmlObject
                            } else {
                                answer = "" + xmlObject
                            }
                        }
                    }
                }
            }
        }
        render text: answer
    }
    */

    def readFecEpd() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String answer = ""

        if (indicatorId && entityId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Entity entity = entityService.getEntityById(entityId)
            if (entity) {
                EntityFile entityFile = valueReferenceService.getFileForEntity(indicator?.fecEpdSource, entity)
                if (entityFile) {
                    EPDC epdc = fecService.getFecEpdFromFile(entityFile)
                    log.info("Success: ${epdc.getEPDCId()} == ${epdc.name}")
                }
            }
        }

        render text: message(code: "xml.RSET_data_injected")
    }

    def copyEntityAnswersModal() {
        if (params.targetEntityId && params.entityId && params.queryId && params.indicatorId) {
            Entity parentEntity = entityService.readEntity(params.entityId)
            Entity entity = entityService.readEntity(params.targetEntityId)
            List<Entity> toCopyEntities = entityService.
                    getModifiableEntitiesByEntityClassAndQueryReady(parentEntity?.entityClass, entity?.entityClass,
                            params.queryId, parentEntity, params.indicatorId)
            if (toCopyEntities) {
                toCopyEntities.remove(entity)
                String templateAsString = g.render(template: "/query/copyentities", model: [targetEntityId: params.targetEntityId,
                                                                                            entityId      : params.entityId, queryId: params.queryId,
                                                                                            indicatorId   : params.indicatorId, toCopyEntities: toCopyEntities]).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                flashService.setErrorAlert("Error in copying entities. Operation unsucessful")
                render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            flashService.setErrorAlert("Incorrect params in copyEntityAnswersModal. Params: $params")
            render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def showOtherDesignsDropDown() {
        String id = params.id
        String parentEntityId = params.parentEntityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String resourceTableId = params.resourceTableId
        String showComparisonOnNewRow = params.showComparisonOnNewRow
        String toRender

        if (id && parentEntityId && indicatorId && queryId && sectionId && resourceTableId) {
            Entity parentEntity = entityService.readEntity(parentEntityId)

            String modifiable = params.modifiabled

            List<Entity> otherDesigns = entityService.getChildEntities(parentEntity, EntityClass.DESIGN.toString(),
                    ["name": 1, "disabledIndicators": 1])?.findAll({ Entity design -> !design.id.equals(DomainObjectUtil.stringToObjectId(id)) && !design.disabledIndicators?.contains(indicatorId) })
            if (!resourceTableId.toLowerCase().endsWith("other")) {
                resourceTableId = "${resourceTableId}Other"
            }


            if (otherDesigns) {
                toRender = "<ul class=\"dropdown-menu\" style=\"margin-left: 30px;\">"
                otherDesigns.each { Entity design ->
                    toRender = "${toRender}<li><a href=\"javascript:;\" onclick=\"renderDesignResults('${design.id}', " +
                            "'${indicatorId}', '${queryId}', '${sectionId}', '${questionId}', " +
                            "'${resourceTableId}', '${id}', '${modifiable}', ${showComparisonOnNewRow});\">${design.name}</a></li>"
                }
                toRender = "${toRender}</ul>"
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private Map<String, List<Object>> handleFilters(Map<String, String> userSelectedQuickFilters, List<QueryFilter> supportedFilters) {
        Map<String, List<Object>> filters = [:]

        if (userSelectedQuickFilters) {
            List<String> persistingBooleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)
            if (supportedFilters) {
                userSelectedQuickFilters.each { String resourceAttr, String resourceValue ->
                    List<String> values = resourceValue.tokenize(",")

                    if (resourceAttr.startsWith("quintiles")) {
                        if (supportedFilters.find({ "co2Quintile".equals(it.resourceAttribute) })) {
                            filters.put(resourceAttr, values)
                        }
                    } else if ("resourceSubType".equals(resourceAttr)) {
                        if (supportedFilters.find({ "resourceType".equals(it.resourceAttribute) })) {
                            filters.put(resourceAttr, values)
                        }
                    } else if ("state".equals(resourceAttr)) {
                        if (supportedFilters.find({ "areas".equals(it.resourceAttribute) })) {
                            filters.put(resourceAttr, values)
                        }
                    } else if (supportedFilters.find({ resourceAttr.equals(it.resourceAttribute) })) {
                        if (persistingBooleanProperties?.contains(resourceAttr)) {
                            if (values.contains("true")) {
                                filters.put(resourceAttr, [Boolean.TRUE])
                            } else if (values.contains("false")) {
                                filters.put(resourceAttr, [Boolean.FALSE])
                            } else if (values.contains("null")) {
                                filters.put(resourceAttr, [null])
                            }
                        } else {
                            filters.put(resourceAttr, values)
                        }
                    }
                }
            } else {
                userSelectedQuickFilters.each { String resourceAttr, String resourceValue ->
                    List<String> values = resourceValue.tokenize(",")

                    if (resourceAttr.startsWith("quintiles")) {
                        filters.put(resourceAttr, values)
                    } else if ("resourceSubType".equals(resourceAttr)) {
                        filters.put(resourceAttr, values)
                    }  else if ("state".equals(resourceAttr)) {
                        if (supportedFilters.find({ "areas".equals(it.resourceAttribute) })) {
                            filters.put(resourceAttr, values)
                        }
                    } else {
                        if (persistingBooleanProperties?.contains(resourceAttr)) {
                            if (values.contains("true")) {
                                filters.put(resourceAttr, [Boolean.TRUE])
                            } else if (values.contains("false")) {
                                filters.put(resourceAttr, [Boolean.FALSE])
                            } else if (values.contains("null")) {
                                filters.put(resourceAttr, [null])
                            }
                        } else {
                            filters.put(resourceAttr, values)
                        }
                    }
                }
            }
        }
        return filters
    }

    def getAdditionalQuestionResourcesAsJson() {
        User user = userService.getCurrentUser()
        List<Document> resourcesAsMaps
        String resourcesAsJSON
        String q = params.query ? params.query.toString().trim() : ""
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String indicatorId = params.indicatorId
        String additionalQuestionId = params.additionalQuestionId
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
        boolean ok = true
        if (additionalQuestionsQuery && additionalQuestionId) {
            Question additionalQuestion = additionalQuestionsQuery.allQuestions?.find({it.questionId == additionalQuestionId})

            if (additionalQuestion) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                resourcesAsMaps = additionalQuestion.getAdditionalQuestionResources(indicator, queryId, q)

                if (resourcesAsMaps) {
                    Account account = userService.getAccount(userService.getCurrentUser())
                    String accountId = account?.id
                    // removes private resources when the account Id does not  match
                    resourcesAsMaps.removeAll{resource -> (resource.privateDatasetAccountId && !resource.privateDatasetAccountId.equals(accountId))}
                }

                Query query = queryService.getQueryByQueryId(queryId, true)

                if (resourcesAsMaps) {
                    Integer yearNow = Year.now().getValue()
                    String label
                    List<String> extraInformation = indicator?.resolveResourceExtraInformationDropdown
                    String isoFilePath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "isoFlagUrlPath")
                    String globe = "/app/assets/isoflags/globe.png"
                    String deepGreenEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud deepGreenEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                    String greenEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud greenEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                    String redEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud redEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                    String orangeEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud orangeEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                    String yellowEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud yellowEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"

                    resourcesAsMaps.each { Document resourceAsMap ->
                        if (resourceAsMap.active) {
                            label = resourceLabelForMap(extraInformation: extraInformation, resource: resourceAsMap, query: query)?.toString()
                            label = label.replaceAll("'", "")
                            label = StringEscapeUtils.escapeJavaScript(label)
                            Benchmark benchmark = (Benchmark) resourceAsMap.get("benchmark")
                            String isoCountryCode
                            List<String> resourceAreas = (List<String>) resourceAsMap.get("areas")
                            Map<String, String> isoCodesByAreas = (Map<String, String>) resourceAsMap?.get("isoCodesByAreas")

                            if (resourceAreas) {
                                if (isoCodesByAreas) {
                                    isoCountryCode = isoCodesByAreas.values().first().toLowerCase()
                                }
                            }
                            String additionalIsoCountryCode = isoCodesByAreas?.get("additionalArea")?.toLowerCase()
                            String additionalisoFlag
                            String isoFlag
                            String emissionIcon
                            String privateDatasetIcon

                            if (isoCountryCode) {
                                isoFlag = getIsoFlagPath(isoCountryCode, isoFilePath)
                            }

                            if (additionalIsoCountryCode) {
                                additionalisoFlag = getIsoFlagPath(additionalIsoCountryCode, isoFilePath)
                            }

                            if (benchmark) {
                                Integer quintile = indicator?.connectedBenchmarks ? benchmark.quintiles?.get(indicator.connectedBenchmarks.first()) : benchmark.co2Quintile

                                if (quintile > 0 && quintile <= 5) {
                                    switch (quintile) {
                                        case 5:
                                            emissionIcon = deepGreenEmissions
                                            break
                                        case 4:
                                            emissionIcon = greenEmissions
                                            break
                                        case 3:
                                            emissionIcon = yellowEmissions
                                            break
                                        case 2:
                                            emissionIcon = orangeEmissions
                                            break
                                        case 1:
                                            emissionIcon = redEmissions
                                            break
                                        default:
                                            break
                                    }
                                }
                            }
                            String genericResourceImg

                            if (resourceAsMap.get("environmentDataSourceType")) {
                                if (com.bionova.optimi.core.Constants.GENERIC_EDST.equalsIgnoreCase((String) resourceAsMap.get("environmentDataSourceType"))) {
                                    genericResourceImg = "<img src='/app/assets/img/generic_resource.png' class='constructionType'></img>"
                                }
                            }
                            String groupByCategory = ""
                            String info = ""
                            String units = unitConversionUtil.getUnitsAndTheirEquivalents((List<String>) resourceAsMap.get("combinedUnits"))?.toString()?.replace("[", "")?.replace("]", "")
                            Boolean expiredDataPoint = false

                            if (resourceAsMap.get("environmentDataPeriod") && "LCA".equals(resourceAsMap.get("applicationId")) && resourceAsMap.get("resourceGroup") && CollectionUtils.containsAny(["buildingTechnology","BMAT"], (List<String>) resourceAsMap.get("resourceGroup"))){
                                Integer environmentalDataPeriod = Integer.valueOf((String) resourceAsMap.get("environmentDataPeriod"))
                                if(genericResourceImg){
                                    if(yearNow - environmentalDataPeriod > 10){
                                        expiredDataPoint = true
                                    }
                                }else{
                                    if(yearNow - environmentalDataPeriod > 5){
                                        expiredDataPoint = true
                                    }
                                }
                            }
                            Boolean showWarning = resourceAsMap.get("showWarning")

                            if (resourceAsMap.privateDatasetAccountId != null) {
                                privateDatasetIcon = "private"
                            }

                            if (resourcesAsJSON) {
                                resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${label}\", \"value\": \"\", \"questionId\": \"${questionId}\", \"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"count\": \"0\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"dataSource\": \"${privateDatasetIcon}\", \"battery\": \"${emissionIcon && user ? emissionIcon : ''}\",  \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"units\" : \"${units}\"${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"":""}${user ? ", \"isUser\": true": ""}${showWarning ? ", \"warning\": \"${showWarning}\"":""}}"
                            } else {
                                String showInfoText = "${message(code: "select_resource_guide")}."
                                resourcesAsJSON = "{\"suggestions\": [{\"vvalue\": \"${label}\", \"value\": \"\", \"questionId\": \"${questionId}\", \"showInfoText\": \"${showInfoText}\", \"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"count\": \"0\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"dataSource\": \"${privateDatasetIcon}\", \"battery\": \"${emissionIcon && user ? emissionIcon : ''}\",  \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"units\" : \"${units}\"${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"":""}${user ? ", \"isUser\": true": ""}${showWarning ? ", \"warning\": \"${showWarning}\"":""}}"
                            }
                        }
                    }
                    resourcesAsJSON = "${resourcesAsJSON}], \"${flashService.FLASH_OBJ_FOR_AJAX}\": ${flash as JSON}}"
                } else {
                    Boolean isImportMapper = params.boolean('isImportMapper')
                    String notFoundMessage = isImportMapper ? message(code: 'resource.not_found') : queryService.getLocalizedNoResourceFoundText(query) ?: message(code: 'resource.not_found')
                    resourcesAsJSON = "{\"suggestions\": [{\"data\": \"\", \"notFound\": \"true\", \"value\": \"${notFoundMessage}\", \"vvalue\":\"<div>${notFoundMessage} </div>\"}], \"${flashService.FLASH_OBJ_FOR_AJAX}\": ${flash as JSON}}"
                }
            } else {
                ok = false
                flashService.setWarningAlert("getAdditionalQuestionResourcesAsJson did not have additionalQuestion", true)
                log.warn("getAdditionalQuestionResourcesAsJson did not have additionalQuestion")
            }
        } else {
            ok = false
            log.warn("getAdditionalQuestionResourcesAsJson did not have additionalQuestionsQuery && additionalQuestionId, params: ${params}")
            flashService.setWarningAlert("getAdditionalQuestionResourcesAsJson did not have additionalQuestionsQuery && additionalQuestionId, params: ${params}", true)
        }

        if (!ok) {
            String error = message(code: 'unknownError')
            resourcesAsJSON = "{\"suggestions\": [{\"data\": \"\", \"notFound\": \"true\", \"value\": \"${error}\", \"vvalue\":\"<div>${error} </div>\"}], \"${flashService.FLASH_OBJ_FOR_AJAX}\": ${flash as JSON}}"
        }
        render text: resourcesAsJSON, contentType: "text/plain"
    }

    def getResourcesAsJson() {
        User user = userService.getCurrentUser()
        List<Document> resourcesAsMaps
        String resourcesAsJSON
        String q = params.query ? params.query.toString().trim() : ""
        String originalQuery = q
        String resourceGroupParams = params.resourceGroups
        String skipResourceTypeParams = params.skipResourceTypes
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String applicationId = params.applicationId
        String importMapperId = params.importMapperId
        String datasetId = params.datasetId
        String classificationParamId = params.classificationParamId
        String unit = params.unit
        Boolean useSplitFilters = params.boolean('useSplitFilters')
        Boolean alwaysShowFullDB = params.boolean('alwaysShowFullDB')
        Boolean isImportMapper = params.boolean('isImportMapper')
        String showDataForUnitText = params.showDataForUnitText

        Integer skip = 0
        Boolean showAll = Boolean.FALSE

        if (q?.startsWith("+skipShow") && q?.contains("__")) {
            List<String> tokenized = q.split("__")

            if (tokenized) {
                String toSkip = tokenized[1]

                if ("showall".equals(toSkip)) {
                    showAll = Boolean.TRUE
                } else if (toSkip?.isNumber()) {
                    skip = toSkip.toDouble().intValue()
                }
            }

            if (tokenized && tokenized.size() > 2) {
                q = tokenized[2]
                originalQuery = tokenized[2]
            } else {
                q = ""
                originalQuery = ""
            }
        }


        List<String> privateDatasetAccountId = params.privateDatasetAccountId?.toString()?.tokenize(",")?.toList()

        Boolean resourcesForMapping = params.resourcesForMapping
        Boolean remap = params.boolean('remap')
        List<String> resourceType = params.resourceType?.toString()?.tokenize(",")?.toList()
        List<String> subType = params.subType?.toString()?.tokenize(",")?.toList()
        Map<String, List<Object>> userGivenFilter = [:]
        Boolean constructionPage = params.constructionPage
        Account account = userService.getAccount(user)
        String basicQueryId = queryService.getBasicQuery()?.queryId

        Query query = queryService.getQueryByQueryId(queryId, true)
        Boolean subTypeSearch = query?.subtypeSearch && !optimiUserAgentIdentService.isMsie()
        Question question = (Question) query?.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
        Entity child = entityService.readEntity(entityId)
        Entity entity
        String organization = account?.companyName

        if (child) {
            if (child.parentEntityId) {
                entity = child.parentById
            } else {
                entity = child
            }
        }
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)

        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.BETA_FEATURES, Feature.COMPARE_MATERIAL])
        Map<String, Boolean> featuresByLicense = licenseService.featuresAllowedByCurrentUserOrProject(entity, [Feature.SEND_ME_EPD_REQUEST],user,false,true)

        Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
        Boolean compareMaterialFeatureLicensed = indicatorService.compareDataLicensedAndCompatible(featuresAllowed, query, indicator)
        Boolean sendMeEpdRequestLicensed = featuresByLicense.get(Feature.SEND_ME_EPD_REQUEST)

        Map<String, List<Object>> resourceFilterCriteria = question?.getResourceFilterCriteria(indicator, child)
        Map<String, List<Object>> organisationResourceFilterCriteria = question?.getOrganisationResourceFilterCriteria(indicator, child)
        Map<String, Map> blockAndWarningResourceFilterCriteria = [
                (IndicatorQuery.WARNING_RESOURCE): question?.getWarningResourceFilterCriteria(indicator, child),
                (IndicatorQuery.BLOCK_RESOURCE): question?.getBlockResourceFilterCriteria(indicator, child)
        ]
        blockAndWarningResourceFilterCriteria.removeAll{!it.value}

        if (user) {
            if (useSplitFilters||constructionPage) {
                if (user.userSplitFilterChoices) {
                    userGivenFilter = handleFilters(user.userSplitFilterChoices, null)
                }
            } else if (user.userFilterChoices) {
                if (query && query.supportedFilters) {
                    userGivenFilter = handleFilters(user.userFilterChoices, query.supportedFilters)
                } else if (resourcesForMapping) {
                    userGivenFilter = handleFilters(user.userFilterChoices, null)
                }
            }
        }

        Boolean renderImportMapperShowAll = Boolean.FALSE
        Boolean subTypeShow = Boolean.FALSE
        Boolean showWeight = Boolean.TRUE
        if (applicationId && importMapperId && datasetId && (!q || (q && !q.startsWith("+importMapperShowAll"))) && (!q || (q && !q.startsWith("+subtypesearch"))) && !alwaysShowFullDB) {
            renderImportMapperShowAll = Boolean.TRUE
            Dataset dataset = session?.getAttribute("ifcDatasets")?.find({ Dataset d -> datasetId.equals(d.manualId) })

            if (dataset) {
                List<Document> trainingDataAsMap = importMapperTrainingDataService.getTrainingDataForDataset(dataset, applicationId, importMapperId, entity?.countryForPrioritization, organization, indicator, entity, child)?.unique({
                    it.get("resourceId")
                })

                if (trainingDataAsMap) {
                    resourcesAsMaps = new ArrayList<Document>()
                    trainingDataAsMap.each {
                        Document d = optimiResourceService.getResourceAsDocument(it.get("resourceId")?.toString(), it.get("profileId")?.toString())
                        if (d && (!unit||(d.combinedUnits && d.combinedUnits.contains(unitConversionUtil.transformImperialUnitToEuropeanUnit(unit))))) {
                            d.put("weight", it.get("weight"))
                            d.put("trainingDataId", it.get("_id")?.toString())
                            d.put("reportedAsBad", it.getBoolean("reportedAsBad"))
                            resourcesAsMaps.add(d)
                        }
                    }
                }
            }
        } else {
            if (applicationId && importMapperId && datasetId && q && q.startsWith("+importMapperShowAll")) {
                List<String> searchedWith = q.split("__")?.toList()

                if (searchedWith.size() == 2) {
                    q = searchedWith.get(1)
                } else {
                    q = ""
                }
            }

            if (queryId && questionId && sectionId && indicatorId) {
                if (applicationId && importMapperId) {
                    showWeight = Boolean.FALSE
                }

                if (subTypeSearch && (!q || (q && !q.startsWith("+subtypesearch")))) {
                    resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(entity, question?.resourceGroups, question?.skipResourceTypes, null, null,
                            null, q, resourceFilterCriteria, question?.requiredEnvironmentDataSourceStandards, question?.filterResources, userGivenFilter, resourceType, subType, privateDatasetAccountId,
                            null, Boolean.TRUE, unit, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account, question?.allowResourceTypes)

                    if (resourcesAsMaps && resourcesAsMaps.size() > 1) {
                        subTypeShow = Boolean.TRUE
                    } else {
                        resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(entity, question?.resourceGroups, question?.skipResourceTypes, null,
                                null, null, q, resourceFilterCriteria, question?.requiredEnvironmentDataSourceStandards, question?.filterResources, userGivenFilter, resourceType, subType, privateDatasetAccountId,
                                null, Boolean.FALSE, unit, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account, question?.allowResourceTypes)
                    }
                } else {
                    if (subTypeSearch && q && q.startsWith("+subtypesearch")) {
                        List<String> subTypeQuery = q.split("__")?.toList()

                        if (subTypeQuery) {
                            if (subTypeQuery.size() > 2) {
                                q = subTypeQuery.get(2) != "null" ? subTypeQuery.get(2) : null
                            } else {
                                q = null
                            }

                            if (subTypeQuery.size() >= 2 && !"showall".equals(subTypeQuery.get(1))) {
                                subType = [subTypeQuery.get(1)]
                            }

                            if (subTypeQuery.size() == 4) {
                                if ("showall".equals(subTypeQuery.get(3))) {
                                    showAll = Boolean.TRUE
                                } else {
                                    skip = subTypeQuery.get(3).toInteger()
                                }
                            }
                        }
                    }
                    resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(entity, question?.resourceGroups, question?.skipResourceTypes, null, null,
                            null, q, resourceFilterCriteria, question?.requiredEnvironmentDataSourceStandards, question?.filterResources, userGivenFilter, resourceType, subType, privateDatasetAccountId,
                            null, Boolean.FALSE, unit, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account, question?.allowResourceTypes)

                    if (q && q.length() <= 0 && resourcesAsMaps.isEmpty() && userGivenFilter.isEmpty()) {
                        log.error("No resources found for question: ${question?.questionId} indicator: ${indicatorService.getLocalizedName(indicator)} query ${query?.localizedName}")
                        flashService.setErrorAlert("No resources found for question: ${question?.questionId} indicator: ${indicatorService.getLocalizedName(indicator)} query ${query?.localizedName}", true)
                    }
                }
            } else if (queryId && questionId && sectionId && ("basicQuery".equals(queryId)||Constants.PARAMETER_QUERYIDS.contains(queryId))) {
                resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(null, question?.resourceGroups, null, null, null, null, q)
            } else if (skipResourceTypeParams || resourceGroupParams) {
                List<String> skipResourceTypes = skipResourceTypeParams?.tokenize(",")?.toList()
                List<String> resourceGroups = resourceGroupParams?.tokenize(",")?.toList()
                resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(entity, resourceGroups, skipResourceTypes, null, null, null, q)
                indicator = session?.getAttribute("indicator")
            } else if (resourcesForMapping||remap) {
                Map<String, String> applicationFilter = ["applicationId": applicationId ? applicationId : "LCA"]

                if (!q || (q && !q.startsWith("+subtypesearch"))) {
                    resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(null, null, null, null, null, null, q, null, null, applicationFilter, userGivenFilter, resourceType, subType, privateDatasetAccountId, null, Boolean.TRUE, null, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account)

                    if (resourcesAsMaps && resourcesAsMaps.size() > 1) {
                        subTypeShow = Boolean.TRUE
                    } else {
                        resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(null, null, null, null, null, null, q, null, null, applicationFilter, userGivenFilter, resourceType, subType, privateDatasetAccountId, null, Boolean.FALSE, null, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account)
                    }
                } else {
                    if (q && q.startsWith("+subtypesearch")) {
                        List<String> subTypeQuery = q.split("__")?.toList()

                        if (subTypeQuery) {
                            if (subTypeQuery.size() > 2) {
                                q = subTypeQuery.get(2) != "null" ? subTypeQuery.get(2) : null
                            } else {
                                q = null
                            }

                            if (subTypeQuery.size() >= 2 && !"showall".equals(subTypeQuery.get(1))) {
                                subType = [subTypeQuery.get(1)]
                            }

                            if (subTypeQuery.size() == 4) {
                                if ("showall".equals(subTypeQuery.get(3))) {
                                    showAll = Boolean.TRUE
                                } else {
                                    skip = subTypeQuery.get(3).toInteger()
                                }
                            }
                        }
                    }
                    resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(null, null, null, null, null, null, q, null, null, applicationFilter, userGivenFilter, resourceType, subType, privateDatasetAccountId, null, Boolean.FALSE, null, organisationResourceFilterCriteria, indicator?.preventPrivateData, blockAndWarningResourceFilterCriteria, account)

                    if (q && q.length() <= 0 && resourcesAsMaps.isEmpty() && userGivenFilter.isEmpty()) {
                        log.error("No resources found for question: ${question?.questionId} indicator: ${indicatorService.getLocalizedName(indicator)} query ${query?.localizedName}")
                        flashService.setErrorAlert("No resources found for question: ${question?.questionId} indicator: ${indicatorService.getLocalizedName(indicator)} query ${query?.localizedName}", true)
                    }
                }
            } else if (constructionPage) {
                log.info("construction page: ${userGivenFilter}, resourcetype: ${resourceType}, subtype: ${subType}")
                resourcesAsMaps = optimiResourceService.getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(null, question?.resourceGroups, null, null, null, null, q, null, null, null, userGivenFilter, resourceType, subType, privateDatasetAccountId, Boolean.TRUE)
            }
        }
        if (resourcesAsMaps) {
            Integer yearNow = Year.now().getValue()

            if (subTypeShow) {
                String label
                String locale = "name" + DomainObjectUtil.getMapKeyLanguage()
                String subtypesInfo = g.message(code: 'typeahead.subtypesInfo.showAll') ?: ""
                String showInfoText = "${message(code: "select_resourceType_guide")}"
                resourcesAsJSON = "{\"suggestions\": [{\"vvalue\": \"<i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> ${subtypesInfo}\", \"value\": \"\", \"showInfoText\": \"${showInfoText}\", \"data\": \"\", \"subType\": \"showall\", \"searchedWith\": \"${q}\"}"

                resourcesAsMaps.each { Document subTypeAsMap ->
                    label = "<i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> ${subTypeAsMap.get(locale) ?: subTypeAsMap.nameEN} ${subTypeAsMap.technicalSpec ?: ""} - ${subTypeAsMap.count} ${message(code:'matches')}"
                    if (resourcesAsJSON) {
                        resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${label}\", \"value\": \"\", \"data\": \"\", \"subType\": \"${subTypeAsMap.subType}\", \"searchedWith\": \"${q}\"}"
                    }
                }
                //Show Data Unit for Import Mapper / Resolver
                if (resourcesAsJSON && showDataForUnitText) {
                    if (unit) {
                        showDataForUnitText = message(code: "importMapper.showDataForUnit", args: [unit])
                    }
                    resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${showDataForUnitText}\", \"value\": \"\", \"data\": \"\"}"
                }

                resourcesAsJSON += resourcesAsJSON ? "]"  : ""
            } else {
                String label
                List<String> extraInformation = indicator?.resolveResourceExtraInformationDropdown
                Dataset defaultCountryDataset = entity?.datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "country" })
                Resource entityHomeCountry
                List<String> neighbouringCountries

                if (defaultCountryDataset?.answerIds) {
                    entityHomeCountry = optimiResourceService.getResourceWithParams(defaultCountryDataset.answerIds[0].toString(), null, null)

                    if (entityHomeCountry) {
                        neighbouringCountries = entityHomeCountry.neighbouringCountries
                    }
                }
                String isoFilePath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "isoFlagUrlPath")
                String globe = "/app/assets/isoflags/globe.png"
                String deepGreenEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud deepGreenEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                String greenEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud greenEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                String redEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud redEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                String orangeEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud orangeEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"
                String yellowEmissions = "<div class='co2CloudContainer'><i class='fa fa-cloud yellowEmissions'></i><p class='co2Text'>co<sub>2</sub></p></div>"

                Boolean showMoreButton = Boolean.FALSE
                Integer totalAmountOfResources = resourcesAsMaps.size()


                if (totalAmountOfResources > resourceShowLimit && !showAll) {
                    if (skip) {
                        resourcesAsMaps = resourcesAsMaps.drop(skip).take(resourceShowLimit)

                        if (skip < totalAmountOfResources && resourcesAsMaps.size() == resourceShowLimit) {
                            showMoreButton = Boolean.TRUE
                        }
                    } else {
                        resourcesAsMaps = resourcesAsMaps.take(resourceShowLimit)
                        showMoreButton = Boolean.TRUE
                    }
                }

                Map<String, Integer> countsByWeight = [:]

                if (subTypeSearch) {
                    resourcesAsMaps.each { Document resourceAsMap ->
                        Integer existing = countsByWeight.get(resourceAsMap.weight.toString())

                        if (existing) {
                            existing = existing + 1
                        } else {
                            existing = 1
                        }
                        countsByWeight.put(resourceAsMap.weight.toString(), existing)
                    }
                }

                for (Document resourceAsMap : resourcesAsMaps) {
                    if (ECOINVENT_CLASSIFICATION_PARAMETER_ID.equalsIgnoreCase(classificationParamId) &&
                            (resourceAsMap.get("enabledPurposes") == null ||
                                    !optimiResourceService.isEcoinventResource((List<String>) resourceAsMap.get("enabledPurposes")))) {
                        continue
                    }
                    label = resourceLabelForMap(extraInformation: extraInformation, resource: resourceAsMap, query: query)?.toString()
                    label = label.replaceAll("'", "")
                    label = StringEscapeUtils.escapeJavaScript(label)
                    Benchmark benchmark = (Benchmark) resourceAsMap.get("benchmark")
                    Boolean privateDataset = Boolean.FALSE

                    if(resourceAsMap.get("privateDatasetAccountId")) {
                        privateDataset = Boolean.TRUE
                    }
                    String isoCountryCode
                    List<String> resourceAreas = (List<String>) resourceAsMap.get("areas")
                    String additionalArea = resourceAsMap.get("additionalArea")
                    Map<String, String> isoCodesByAreas = (Map<String, String>) resourceAsMap?.get("isoCodesByAreas")

                    if (!isoCodesByAreas && resourceAreas) {
                        isoCodesByAreas = optimiResourceService.getresourceIsoCodes(resourceAreas, additionalArea)
                    }

                    if (resourceAreas) {
                        if (com.bionova.optimi.core.Constants.RESOURCE_LOCAL_AREA.equalsIgnoreCase(resourceAreas.first())) {
                            if (entityHomeCountry?.isoCountryCode) {
                                isoCountryCode = entityHomeCountry.isoCountryCode?.toLowerCase()
                            } else if (entityHomeCountry?.isoCodesByAreas) {
                                isoCountryCode = entityHomeCountry?.isoCodesByAreas.values()?.first()?.toLowerCase()
                            } else if (resourceAsMap.get("representativeArea")) {
                                isoCountryCode = optimiResourceService.getResourceWithParams(resourceAsMap.get("representativeArea").toString(), null, null)?.isoCodesByAreas?.values()?.first()?.toLowerCase()
                            }
                        } else if (isoCodesByAreas) {
                            if (isoCodesByAreas.get(entityHomeCountry?.resourceId)) {
                                isoCountryCode = isoCodesByAreas.get(entityHomeCountry.resourceId).toLowerCase()
                            } else if (neighbouringCountries && neighbouringCountries.find({
                                isoCodesByAreas.get(it)
                            })) {
                                isoCountryCode = isoCodesByAreas.get(neighbouringCountries.find({
                                    isoCodesByAreas.get(it)
                                }))
                            } else {
                                isoCountryCode = isoCodesByAreas.values().first().toLowerCase()
                            }
                        }
                    }
                    String additionalIsoCountryCode = isoCodesByAreas?.get("additionalArea")?.toLowerCase()
                    String additionalisoFlag
                    String isoFlag
                    String emissionIcon

                    if (isoCountryCode) {
                        isoFlag = getIsoFlagPath(isoCountryCode, isoFilePath)
                    }

                    if (additionalIsoCountryCode) {
                        additionalisoFlag = getIsoFlagPath(additionalIsoCountryCode, isoFilePath)
                    }

                    if ((betaFeaturesLicensed || resourcesForMapping) && benchmark) {
                        Integer quintile = indicator?.connectedBenchmarks ? benchmark.quintiles?.get(indicator.connectedBenchmarks.first()) : benchmark.co2Quintile

                        if ((quintile > 0) && (quintile <= 5) && !resourceAsMap.get("dataProperties")?.contains(Const.INIES)) {
                            switch (quintile) {
                                case 5:
                                    emissionIcon = deepGreenEmissions
                                    break
                                case 4:
                                    emissionIcon = greenEmissions
                                    break
                                case 3:
                                    emissionIcon = yellowEmissions
                                    break
                                case 2:
                                    emissionIcon = orangeEmissions
                                    break
                                case 1:
                                    emissionIcon = redEmissions
                                    break
                                default:
                                    break
                            }
                        }
                    }

                    String constructionId = resourceAsMap.get("constructionId")
                    String constructionTypeImage
                    String multipartImage

                    if (constructionId) {
                        String constructionType = resourceAsMap.get("constructionType")
                        constructionTypeImage = optimiResourceService.getConstructionIcon(asset, constructionType)
                    } else if (resourceAsMap.get("isMultiPart")) {
                        constructionTypeImage = optimiResourceService.getConstructionIcon(asset, com.bionova.optimi.core.Constants.STRUCTURE_CT)
                    } else if (resourceAsMap.get("multipart")) {
                        multipartImage = "<img src='/app/assets/constructionTypes/structure.png' class='constructionType'></img>"
                    }

                    String brandImageId = resourceAsMap.get("brandImageId")
                    String brandImage
                    if (brandImageId) {
                        String b64BrandImage = accountImagesService.getAccountImageAsB64Data(brandImageId)
                        if (b64BrandImage) {
                            brandImage = "<img class='brandImage' src='data:image/png;base64," + b64BrandImage + "\' id='${resourceAsMap.get("manufacturer")?:''}'/>"
                        }
                    }

                    String genericResourceImg
                    Boolean isPlant

                    if (resourceAsMap.get("environmentDataSourceType")) {
                        if (com.bionova.optimi.core.Constants.GENERIC_EDST.equalsIgnoreCase((String) resourceAsMap.get("environmentDataSourceType"))) {
                            genericResourceImg = "<img src='/app/assets/img/generic_resource.png' class='constructionType'></img>"
                        } else if ("plant".equalsIgnoreCase((String) resourceAsMap.get("environmentDataSourceType"))) {
                            isPlant = Boolean.TRUE
                        }
                    }
                    Integer intweight = Integer.valueOf((String) resourceAsMap.get("weight"))
                    String mappingRemoveOrReport = intweight == 3 ? "${message(code: "importMapper.trainingData.removeMapping")}" : "${message(code: "importMapper.trainingData.reportMapping")}"
                    String weight = importMapperTrainingDataService.getWeightFlavor(intweight) ?: ""
                    String weightShort = importMapperTrainingDataService.getWeightFlavor(intweight, Boolean.FALSE,Boolean.TRUE,[entity?.countryForPrioritization]) ?: ""
                    String groupByCategory = ""
                    String info = ""

                    if (subTypeSearch) {
                        groupByCategory = importMapperTrainingDataService.getGroupByText(intweight, Boolean.FALSE) ?: ""
                        info = importMapperTrainingDataService.getGroupByText(intweight, Boolean.TRUE, account?.companyName) ?: ""
                    }
                    String units = unitConversionUtil.getUnitsAndTheirEquivalents((List<String>) resourceAsMap.get("combinedUnits"))?.toString()?.replace("[", "")?.replace("]", "")
                    Boolean expiredDataPoint = false

                    if(resourceAsMap.get("environmentDataPeriod") && "LCA".equals(resourceAsMap.get("applicationId")) && resourceAsMap.get("resourceGroup") && CollectionUtils.containsAny(["buildingTechnology","BMAT"], (List<String>) resourceAsMap.get("resourceGroup"))){
                        Integer environmentalDataPeriod = Integer.valueOf((String) resourceAsMap.get("environmentDataPeriod"))
                        if(genericResourceImg){
                            if(yearNow - environmentalDataPeriod > 10){
                                //expiredDataPointIcon ="<img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/>"
                                expiredDataPoint = true
                            }
                        }else{
                            if(yearNow - environmentalDataPeriod > 5){
                                //expiredDataPointIcon ="<img src='/app/assets/img/iconExpiredBig.png' class='expiredIcon'/>"
                                expiredDataPoint = true
                            }
                        }
                    }
                    Boolean showWarning = resourceAsMap.get("showWarning")
                    String resourceQualityWarning = resourceAsMap.get("resourceQualityWarning")

                    if (resourcesAsJSON) {
                        // REL-304 deprecate nmd3ElementId
                        //resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${label}\"${resourceAsMap.get("productDataListId") ? ", \"productDataListId\": \"${resourceAsMap.get("productDataListId")}\"":""}, \"value\": \"\", \"nmd3ElementId\": \"${resourceAsMap.get("nmd3ElementId") ?: ''}\",\"dataSource\": \"${privateDataset ? "private" : isPlant ? 'plant' : ''}\",\"addToCompare\":\"${compareMaterialFeatureLicensed}\" ,\"questionId\": \"${questionId}\",\"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"weight\": \"${intweight}\", \"count\": \"${countsByWeight.get(resourceAsMap.weight.toString())}\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"battery\": \"${emissionIcon && user ? emissionIcon : ''}\", \"constructionId\": \"${constructionId ?: ''}\" , \"multipartImage\": \"${multipartImage ?: ''}\", \"constructionImage\" : \"${constructionTypeImage ?: ''}\", \"brandImage\" : \"${brandImage ?: ''}\",  \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"weight\" : \"${showWeight ? weight : ""}\", \"weightShort\" : \"${showWeight ? weightShort : ""}\", \"units\" : \"${units}\", \"trainingDataId\" : \"${resourceAsMap.get("trainingDataId") ?: ''}\", \"trainingDataRemoveOrReport\" : \"${mappingRemoveOrReport ?: ''}\"${datasetId ? ", \"datasetId\" : \"${datasetId}\"" : ""}${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"":""}${user ? ", \"isUser\": true": ""}${resourceAsMap.get("reportedAsBad") ? ", \"reportedAsBad\": \"${message(code:'reportedAsBad')}\"": ""}${showWarning ? ", \"warning\": \"${showWarning}\"":""}${resourceQualityWarning ? ", \"resourceQualityWarning\": \"${resourceQualityWarning}\"":""}}"
                        resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${label}\"${resourceAsMap.get("productDataListId") ? ", \"productDataListId\": \"${resourceAsMap.get("productDataListId")}\"" : ""}, \"value\": \"\",\"dataSource\": \"${privateDataset ? "private" : isPlant ? 'plant' : ''}\",\"addToCompare\":\"${compareMaterialFeatureLicensed}\" ,\"questionId\": \"${questionId}\",\"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"weight\": \"${intweight}\", \"count\": \"${countsByWeight.get(resourceAsMap.weight.toString())}\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"battery\": \"${emissionIcon && user ? emissionIcon : ''}\", \"constructionId\": \"${constructionId ?: ''}\" , \"multipartImage\": \"${multipartImage ?: ''}\", \"constructionImage\" : \"${constructionTypeImage ?: ''}\", \"brandImage\" : \"${brandImage ?: ''}\",  \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"weight\" : \"${showWeight ? weight : ""}\", \"weightShort\" : \"${showWeight ? weightShort : ""}\", \"units\" : \"${units}\", \"trainingDataId\" : \"${resourceAsMap.get("trainingDataId") ?: ''}\", \"trainingDataRemoveOrReport\" : \"${mappingRemoveOrReport ?: ''}\"${datasetId ? ", \"datasetId\" : \"${datasetId}\"" : ""}${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"" : ""}${user ? ", \"isUser\": true" : ""}${resourceAsMap.get("reportedAsBad") ? ", \"reportedAsBad\": \"${message(code: 'reportedAsBad')}\"" : ""}${showWarning ? ", \"warning\": \"${showWarning}\"" : ""}${resourceQualityWarning ? ", \"resourceQualityWarning\": \"${resourceQualityWarning}\"" : ""}}"
                    } else {
                        String showInfoText = "${message(code: "select_resource_guide")}."

                        if (showMoreButton) {
                            showInfoText = "${showInfoText} ${message(code: "resources.showing", args: [(skip + 1), (skip + resourceShowLimit), totalAmountOfResources])}"
                        }
                        // REL-304 deprecate nmd3ElementId
                        //resourcesAsJSON = "{\"suggestions\": [{\"vvalue\": \"${label}\"${resourceAsMap.get("productDataListId") ? ", \"productDataListId\": \"${resourceAsMap.get("productDataListId")}\"":""}, \"value\": \"\", \"nmd3ElementId\": \"${resourceAsMap.get("nmd3ElementId") ?: ''}\",\"dataSource\": \"${privateDataset ? "private" : isPlant ? 'plant' : ''}\",\"addToCompare\":\"${compareMaterialFeatureLicensed}\" ,\"questionId\": \"${questionId}\", \"showInfoText\": \"${showInfoText}\", \"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"weight\": \"${intweight}\", \"count\": \"${countsByWeight.get(resourceAsMap.weight.toString())}\", \"company\": \"${user?.account?.companyName}\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"multipartImage\": \"${multipartImage ?: ''}\",\"battery\": \"${emissionIcon && user ? emissionIcon : ''}\", \"constructionId\": \"${constructionId ?: ''}\", \"constructionImage\" : \"${constructionTypeImage ?: ''}\", \"brandImage\" : \"${brandImage ?: ''}\", \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"weight\" : \"${showWeight ? weight : ""}\",\"weightShort\" : \"${showWeight ? weightShort : ""}\", \"units\" : \"${units}\", \"trainingDataId\" : \"${resourceAsMap.get("trainingDataId") ?: ''}\", \"trainingDataRemoveOrReport\" : \"${mappingRemoveOrReport ?: ''}\"${datasetId ? ", \"datasetId\" : \"${datasetId}\"" : ""}${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"":""}, \"totalAmountOfResources\": ${totalAmountOfResources}${user ? ", \"isUser\": true": ""}${resourceAsMap.get("reportedAsBad") ? ", \"reportedAsBad\": \"${message(code:'reportedAsBad')}\"": ""}${showWarning ? ", \"warning\": \"${showWarning}\"":""}${resourceQualityWarning ? ", \"resourceQualityWarning\": \"${resourceQualityWarning}\"":""}}"
                        resourcesAsJSON = "{\"suggestions\": [{\"vvalue\": \"${label}\"${resourceAsMap.get("productDataListId") ? ", \"productDataListId\": \"${resourceAsMap.get("productDataListId")}\"" : ""}, \"value\": \"\",\"dataSource\": \"${privateDataset ? "private" : isPlant ? 'plant' : ''}\",\"addToCompare\":\"${compareMaterialFeatureLicensed}\" ,\"questionId\": \"${questionId}\", \"showInfoText\": \"${showInfoText}\", \"data\": {\"category\": \"${groupByCategory}\", \"info\": \"${info}\", \"weight\": \"${intweight}\", \"count\": \"${countsByWeight.get(resourceAsMap.weight.toString())}\", \"company\": \"${userService.getAccount(user)?.companyName}\", \"resource\": \"${resourceAsMap.get("resourceId") + '.' + resourceAsMap.get("profileId")}\"}, \"img\": \"${isoFlag ?: globe}\", \"img2\": \"${additionalisoFlag ?: ''}\", \"multipartImage\": \"${multipartImage ?: ''}\",\"battery\": \"${emissionIcon && user ? emissionIcon : ''}\", \"constructionId\": \"${constructionId ?: ''}\", \"constructionImage\" : \"${constructionTypeImage ?: ''}\", \"brandImage\" : \"${brandImage ?: ''}\", \"genericResourceImg\" : \"${genericResourceImg ?: ''}\", \"weight\" : \"${showWeight ? weight : ""}\",\"weightShort\" : \"${showWeight ? weightShort : ""}\", \"units\" : \"${units}\", \"trainingDataId\" : \"${resourceAsMap.get("trainingDataId") ?: ''}\", \"trainingDataRemoveOrReport\" : \"${mappingRemoveOrReport ?: ''}\"${datasetId ? ", \"datasetId\" : \"${datasetId}\"" : ""}${expiredDataPoint ? ", \"expiredDataPoint\": \"${expiredDataPoint}\"" : ""}, \"totalAmountOfResources\": ${totalAmountOfResources}${user ? ", \"isUser\": true" : ""}${resourceAsMap.get("reportedAsBad") ? ", \"reportedAsBad\": \"${message(code: 'reportedAsBad')}\"" : ""}${showWarning ? ", \"warning\": \"${showWarning}\"" : ""}${resourceQualityWarning ? ", \"resourceQualityWarning\": \"${resourceQualityWarning}\"" : ""}}"
                    }
                }
                if (renderImportMapperShowAll) {
                    String showWholeDb = "<i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> <strong>${message(code: "importmapper.resolver.show_from_entire_db")}</strong>"
                    resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${showWholeDb}\", \"importMapperShowAll\": \"true\", \"value\": \"\", \"data\": \"\", \"subType\": \"\", \"searchedWith\": \"${q}\"}"
                } else {
                    if (showMoreButton) {
                        Integer nextAmountOfResources = (totalAmountOfResources - (skip + resourceShowLimit)) > resourceShowLimit ? resourceShowLimit : (totalAmountOfResources - (skip + resourceShowLimit))
                        String showAllLabel = "<b><i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> ${message(code: "resources.showAll", args: [totalAmountOfResources])}</b>"
                        label = "<b><i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> ${message(code: "resources.showNext", args: [nextAmountOfResources, (skip + resourceShowLimit + 1), (skip + nextAmountOfResources + resourceShowLimit < totalAmountOfResources ? skip + nextAmountOfResources + resourceShowLimit : totalAmountOfResources), totalAmountOfResources])}</b>"
                        if (resourcesAsJSON) {
                            resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${label}\", \"value\": \"\", \"data\": {\"category\": \"${message(code: "resources.showing", args: [(skip + 1), (skip + resourceShowLimit), totalAmountOfResources])}\"}, \"nextSkip\": \"${skip + resourceShowLimit}\", \"originalQuery\": \"${originalQuery ?: ""}\", \"searchedWith\": \"${q ?: ""}\"}"
                            resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${showAllLabel}\", \"value\": \"\", \"data\": {\"category\": \"${message(code: "resources.showing", args: [(skip + 1), (skip + resourceShowLimit), totalAmountOfResources])}\"}, \"nextSkip\": \"showall\", \"originalQuery\": \"${originalQuery ?: ""}\", \"searchedWith\": \"${q ?: ""}\"}"
                        }
                    }

                    //Show Data Unit for Import Mapper / Resolver
                    if (resourcesAsJSON && showDataForUnitText) {
                        if (unit) {
                            showDataForUnitText = message(code: "importMapper.showDataForUnit", args: [unit])
                        }
                        resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${showDataForUnitText}\", \"value\": \"\", \"data\": \"\"}"
                    }
                }
                resourcesAsJSON += resourcesAsJSON ? "]"  : ""
            }
        } else {
            if (renderImportMapperShowAll) {
                String showWholeDb = "<i style='margin-left: 5px; margin-right: 5px;' class='fa fa-plus fa-lg oneClickColorScheme'></i> <strong>${message(code: "importmapper.resolver.show_from_entire_db")}</strong>"
                resourcesAsJSON = "{\"suggestions\": [{\"vvalue\": \"${showWholeDb}\", \"importMapperShowAll\": \"true\", \"value\": \"\", \"data\": \"\", \"subType\": \"\", \"searchedWith\": \"${q}\"}"
            } else {
                String notFoundMessage = isImportMapper ? message(code: 'resource.not_found') : queryService.getLocalizedNoResourceFoundText(query) ?: message(code: 'resource.not_found')

                // RE2020 INIES data request dropdown option
                String re2020SendMeDataTxt

                if (indicator?.iniesDataRequestAPI) {
                    re2020SendMeDataTxt = g.message(code :"re2020_request_data.message")
                }

                String sendMeDataTxt = g.message(code :"send_me_data")

                if (!basicQueryId.equalsIgnoreCase(queryId)){
                    String openTicket = g.message(code :"zoho.openDataTicket")
                    if(sendMeEpdRequestLicensed && account){
                        resourcesAsJSON = "{\"suggestions\": [{\"data\": \"\", \"notFound\": \"true\", \"value\": \"${notFoundMessage}\", \"vvalue\":\"<div class='notFoundMessage'>${notFoundMessage}:<br/> ${indicator?.iniesDataRequestAPI ? "<br/><a onclick='openRe2020RequestData()')><i class='fas fa-file-contract'></i> ${re2020SendMeDataTxt}</a>" : ""} </div>\"}"
                    } else{
                        resourcesAsJSON = "{\"suggestions\": [{\"data\": \"\", \"notFound\": \"true\", \"value\": \"${notFoundMessage}\", \"vvalue\":\"<div class='notFoundMessage'>${notFoundMessage}:<br/></div>\"}"
                    }
                }else{
                    resourcesAsJSON = "{\"suggestions\": [{\"data\": \"\", \"notFound\": \"true\", \"value\": \"${notFoundMessage}\", \"vvalue\":\"<div class='notFoundMessage'>${notFoundMessage} </div>\"}"
                }

                //Show Data Unit for Import Mapper / Resolver
                if (resourcesAsJSON && showDataForUnitText) {
                    if (unit) {
                        showDataForUnitText = message(code: "importMapper.showDataForUnit", args: [unit])
                    }
                    resourcesAsJSON = "${resourcesAsJSON}, {\"vvalue\": \"${showDataForUnitText}\", \"value\": \"\", \"data\": \"\"}"
                }
            }
            resourcesAsJSON += resourcesAsJSON ? "]"  : ""
        }
        resourcesAsJSON += resourcesAsJSON ? ",\"${flashService.FLASH_OBJ_FOR_AJAX}\": ${flash as JSON}}" : ''
        render text: resourcesAsJSON, contentType: "text/plain"
    }

    def getIsoFlagPath(String isoCountryCode, String isoFilePath) {
        String isoFlag
        if (isoCountryCode) {
            if (System.getProperty("islocalhost")) {
                isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                    isoFlag = "/app/assets/isoflags/globe.png"
                }
            } else {
                def testIsoFilePath = new File("/var/www/${isoFilePath}${isoCountryCode}.png")
                if (testIsoFilePath.exists()) {
                    isoFlag = "${isoFilePath}${isoCountryCode}.png"
                } else {
                    log.error("missing flag for countrycode ${isoCountryCode} with filepath ${testIsoFilePath?.absolutePath} from server")
                    isoFlag = "/app/assets/isoflags/${isoCountryCode}.png"
                    if (!asset.assetPathExists(src: "/isoflags/${isoCountryCode}.png")) {
                        log.error("missing flag for countrycode ${isoCountryCode} from assets")
                        isoFlag = "/app/assets/isoflags/globe.png"
                    }
                }
            }
        }

        if (!isoFlag) {
            isoFlag = "/app/assets/isoflags/globe.png"
        }
        return isoFlag
    }

    private String getResourceSearchString(Resource resource) {
        String searchString = ""

        if (resource) {
            searchString = "${searchString}${optimiResourceService.getLocalizedName(resource)?.replaceAll(",", "")?.toUpperCase()}"

            if (resource.commercialName) {
                searchString = "${searchString}${resource.commercialName.replaceAll(",", "").toUpperCase()}"
            }

            if (resource.manufacturer) {
                searchString = "${searchString}${resource.manufacturer.replaceAll(",", "").toUpperCase()}"
            }

            if (resource.technicalSpec) {
                searchString = "${searchString}${resource.technicalSpec.replaceAll(",", "").toUpperCase()}"
            }
        }
        searchString = Normalizer.normalize(searchString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("[^a-zA-Z0-9 ]+","")
        return searchString
    }

    String getQuickFilters() {
        String filterName = params.filterName
        String queryId = params.queryId
        String indicatorId = params.indicatorId
        String resourceAttribute = params.resourceAttribute
        String entityId = params.entityId
        String parentEntityId = params.parentEntityId
        String classificationParamId = params.classificationParamId
        Boolean constructionPage = params.constructionPage
        Boolean productDataListPage = params.productDataListPage
        Boolean splitFilters = params.boolean("splitFilters")
        String filters = "<option></option>"
        User user = userService?.getCurrentUser(true)
        Map<String, String> splitFilterChoices = [:]
        Map<String, String> userFilterChoices = [:]
        Resource stateResource
        String basicQueryId = queryService.getBasicQuery()?.queryId

        if (filterName && queryId && resourceAttribute) {
            List<String> persistingBooleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)
            Query query = queryService.getQueryByQueryId(queryId, true)
            List<Question> questions = query?.getAllQuestions()?.findAll({ Question question -> question?.showTypeahead })
            List<Document> resourceTypeFilters = new ArrayList<>()
            List<String> otherTypeFilters = new LinkedList<>()
            List<String> areaFilters = new LinkedList<>()
            Map<String, List<String>> stateFilters= [:]
            List<String> epdFilters = new LinkedList<>()
            Map<String, Map<String, String>> matchFilterValues
            List<String> matchFilterKeys = query.supportedFilters?.collect({it.resourceAttribute})
            Indicator indicator
            Boolean useSplitFilter = Boolean.FALSE

            if (indicatorId) {
                indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            }

            if (splitFilters || constructionPage || productDataListPage) {
                splitFilterChoices = user?.userSplitFilterChoices
                useSplitFilter = Boolean.TRUE
            } else {
                userFilterChoices = user?.userFilterChoices
            }
            if (query && questions) {
                Entity entity = entityService.getEntityByIdReadOnly(entityId)
                Entity parent = entityService.getEntityByIdReadOnly(parentEntityId)
                Map<String, List<Object>> resourceFilterCriteria
                IndicatorQuery indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                    it.queryId.equals(queryId) && it.resourceFilterCriteria
                })
                if(parent){
                    Dataset defaultStateDatasetForLocalResources = parent?.datasets?.find({ d -> d.queryId == basicQueryId && d?.additionalQuestionAnswers?.state})

                    if(defaultStateDatasetForLocalResources){
                        stateResource = optimiResourceService.getResourceWithParams(defaultStateDatasetForLocalResources?.additionalQuestionAnswers?.state, null, null)
                    }
                }

                if (indicatorQueryWithResourceFilter) {
                    resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(entity, IndicatorQuery.RESOURCE, indicatorQueryWithResourceFilter)
                } else if (constructionPage && classificationParamId) {
                    Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                    Question classificationQuestion = questionService.getQuestion(constructionCreationQuery, com.bionova.optimi.core.Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS)

                    if (classificationQuestion) {
                        String dataProperty = classificationQuestion.choices?.find({it.answerId.equals(classificationParamId)})?.lockMandatoryFilters?.get("dataProperties")

                        if (dataProperty) {
                            if (dataProperty.contains(",")) {
                                resourceFilterCriteria = [dataProperties: dataProperty.tokenize(",")]
                            } else {
                                resourceFilterCriteria = [dataProperties: [dataProperty]]
                            }
                        }
                    }
                } else if (productDataListPage && classificationParamId) {
                    Query productDataListQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
                    Question classificationQuestion = questionService.getQuestion(productDataListQuery, "productDataListsType")

                    if (classificationQuestion) {
                        String dataProperty = classificationQuestion.choices?.find({it.answerId.equals(classificationParamId)})?.lockMandatoryFilters?.get("dataProperties")

                        if (dataProperty) {
                            if (dataProperty.contains(",")) {
                                resourceFilterCriteria = [dataProperties: dataProperty.tokenize(",")]
                            } else {
                                resourceFilterCriteria = [dataProperties: [dataProperty]]
                            }
                        }
                    }
                }
                if(matchFilterKeys && matchFilterKeys.contains(resourceAttribute)){
                    matchFilterValues = query.supportedFilters?.find({
                        resourceAttribute.equals(it.resourceAttribute)
                    })?.matchFilterValues
                }

                if (persistingBooleanProperties?.contains(resourceAttribute)) {
                    otherTypeFilters = ["true", "false", "null"]
                } else {
                    String projectCountry
                    String isoCodeForProjectCountry
                    Map<String, String> countryCodesForEpd

                    if (parent) {
                        projectCountry = parent.countryForPrioritization?.toLowerCase()

                        if (projectCountry) {
                            projectCountry = optimiResourceService.trimBadResourceIds(projectCountry)
                            isoCodeForProjectCountry = optimiResourceService.getresourceIsoCode(projectCountry)
                        }
                        countryCodesForEpd = query?.supportedFilters?.find({ it.countryCode })?.countryCode
                    }

                    List<String> questionsResourceGroups = questions?.findResults({it.resourceGroups ?: null})?.flatten()?.unique()
                    List<String> questionsAllowResourcetypes = questions?.findResults({it.allowResourceTypes ?: null})?.flatten()?.unique()

                    //List<String> questionsSkipResourceTypes = questions?.findResults({it.skipResourceTypes ?: null}).flatten().unique()

                    if ("resourceType".equals(resourceAttribute)) {

                        optimiResourceService.getResourcesTypesResourceGroupsAndSkipResourceTypesAsDocuments(resourceFilterCriteria, constructionPage, questionsResourceGroups, null, productDataListPage, questionsAllowResourcetypes)?.unique()?.each { Document resourceType ->

                            resourceTypeFilters.add(resourceType)

                        }
                    } else if ("areas".equals(resourceAttribute)) {
                        List<String> areaWithData = optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, null, resourceAttribute, resourceFilterCriteria, false, questionsAllowResourcetypes)
                        optimiResourceService.getResourceCountryAsList()?.each { String attribute ->
                            if (!areaFilters.contains(attribute) && !attribute.equalsIgnoreCase("local") && areaWithData?.contains(attribute)) {
                                areaFilters.add(attribute)
                            }
                        }
                    }else {
                        optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, null, resourceAttribute, resourceFilterCriteria, false, questionsAllowResourcetypes)?.each { String attribute ->
                            if ("epdProgram".equals(resourceAttribute) && !epdFilters.contains(attribute)) {
                                epdFilters.add(attribute)
                            } else {
                                otherTypeFilters.add(attribute)
                            }
                        }
                    }

                    areaFilters = areaFilters?.sort({ it.toLowerCase() })
                    epdFilters = epdFilters?.sort({ it.toLowerCase() })

                    if (projectCountry) {
                        String originalFilter

                        if(projectCountry) {
                            areaFilters?.each { String filter ->
                                String cleanedFilter = filter.replaceAll("\\s", "")
                                if (projectCountry.equalsIgnoreCase(cleanedFilter)) {
                                    originalFilter = filter
                                }
                            }
                        }


                        if (originalFilter) {
                            areaFilters.remove(originalFilter)
                            areaFilters.add(0, originalFilter)
                        }

                    }
                    if (isoCodeForProjectCountry && countryCodesForEpd) {
                        List<String> epdProgramForPriority = countryCodesForEpd.findAll({
                            it.value.equals(isoCodeForProjectCountry.toUpperCase())
                        })?.keySet()?.toList()

                        if (epdProgramForPriority) {
                            List<String> foundEpdFilters = []
                            epdFilters.each { String filter ->
                                if (epdProgramForPriority.contains(filter)) {
                                    foundEpdFilters.add(filter)
                                }
                            }
                            if (foundEpdFilters) {
                                int i = 0
                                foundEpdFilters?.each { String epdFilterToPrio ->
                                    epdFilters.remove(epdFilterToPrio)
                                    epdFilters.add(i, epdFilterToPrio)
                                    i++;
                                }
                            }
                        }
                    }
                }

                if ("resourceType".equals(resourceAttribute)) {
                    //0014969
                    //some resourceTypes might not have any resources linked to it. But allow it if there is any linkedResourceTypes and those (subtypes) have resources
                    resourceTypeFilters?.unique()?.sort({ getLocalizedNameFromDocument(it)?.toLowerCase() })?.each { Document resourceType ->
                        String hasChildren = ""
                        if (resourceType.linkedSubTypes) {
                            hasChildren = "true"
                        }
                        if (useSplitFilter) {
                            if (!resourceType.subType) {
                                filters = "${filters}<option class=\"resourceType\" data-multiPartValue=\"${resourceType.multipart}\"  data-resourceAttributeValue=\"${resourceType.resourceType}\" data-haschildren=\"${resourceTypeService.getResourceTypeHasSubTypes(resourceType.resourceType) ? 'true' : hasChildren}\" data-resourceTypeId=\"${resourceType?._id}\" data-resourceType=\"${resourceType.resourceType}\"  data-attribute=\"${resourceAttribute}\" ${splitFilterChoices?.get(resourceAttribute)?.equals(resourceType.resourceType) ? "selected" : ""} value=\"${resourceType.resourceType}\">${getLocalizedNameFromDocument(resourceType)}</option>"
                            }
                        } else {
                            if (!resourceType.subType) {
                                if (userFilterChoices?.get(resourceAttribute)?.equals(resourceType.resourceType) && userFilterChoices?.get("resourceSubType")) {
                                    ResourceType subType = resourceTypeService.getResourceTypeObjectBySubType(userFilterChoices.get("resourceSubType"))
                                    filters = "${filters}<option class=\"resourceType\" data-multiPartValue=\"${resourceType.multipart}\" data-resourceAttributeValue=\"${resourceType.resourceType}\" data-haschildren=\"${resourceTypeService.getResourceTypeHasSubTypes(resourceType.resourceType) ? 'true' : hasChildren}\" data-resourceTypeId=\"${resourceType?._id}\" data-resourceType=\"${resourceType.resourceType}\"  data-attribute=\"${resourceAttribute}\" value=\"${resourceType.resourceType}\">${getLocalizedNameFromDocument(resourceType)}</option>"
                                    filters = "${filters}<option selected class=\"subTypeResource\" data-attribute=\"resourceType\" data-multiPartValue=\"${resourceType.multipart}\"  data-resourceAttributeValue=\"${subType.resourceType}\" value=\"${resourceType.subType}\" data-subType=\"${subType.subType}\" data-resourceType=\"${subType.resourceType}\" data-hidden=\"true\">${resourceTypeService.getLocalizedName(subType)}</option>"

                                } else {
                                    filters = "${filters}<option class=\"resourceType\" data-multiPartValue=\"${resourceType.multipart}\"  data-resourceAttributeValue=\"${resourceType.resourceType}\" data-haschildren=\"${resourceTypeService.getResourceTypeHasSubTypes(resourceType.resourceType) ? 'true' : hasChildren}\" data-resourceTypeId=\"${resourceType?._id}\" data-resourceType=\"${resourceType.resourceType}\"  data-attribute=\"${resourceAttribute}\" ${userFilterChoices?.get(resourceAttribute)?.equals(resourceType.resourceType) ? "selected" : ""} value=\"${resourceType.resourceType}\">${getLocalizedNameFromDocument(resourceType)}</option>"
                                }
                            } else if (resourceType) {

                                filters = "${filters}<option class=\"subTypeResource forSelect2 hidden\" data-attribute=\"resourceType\" data-multiPartValue=\"${resourceType.multipart}\"  data-resourceAttributeValue=\"${resourceType.resourceType}\" value=\"${resourceType.subType}\" data-subType=\"${resourceType.subType}\" data-resourceType=\"${resourceType.resourceType}\" data-hidden=\"true\">${resourceType.localizedName ? resourceType.localizedName : resourceType.nameEN}</option>"
                            }
                        }
                    }
                } else if ("areas".equals(resourceAttribute)) {
                    stateFilters = optimiResourceService.getStateResourceCountryAsMap()

                    areaFilters?.each { String area ->
                        String option = optimiResourceService.getCountryLocalizedName(area)?.capitalize()?.replace(" ", "")
                        String isoCode = optimiResourceService.getresourceIsoCode(area)
                        if (isoCode) {
                            isoCode = "data-isoCode='${isoCode}'"
                        }
                        option = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(option), ' ')

                        if (userFilterChoices?.get(resourceAttribute)?.equals(area) && userFilterChoices?.get("state")) {
                            Document state = stateFilters?.get(area)?.find({it.resourceId.equalsIgnoreCase(userFilterChoices.get("state"))})
                            if((stateResource && !stateResource.resourceId.equalsIgnoreCase(state.resourceId)) || !stateResource){
                                filters = "${filters}<option  selected class=\"subTypeResource\" data-attribute=\"state\" data-resourceAttributeValue=\"${state.resourceId}\" value=\"${state.resourceId}\">${getLocalizedNameFromDocument(state)}</option>"
                            }
                            filters = "${filters}<option ${isoCode ? isoCode : ''} data-resourceAttributeValue=\"${area}\" data-haschildren=\"${stateFilters?.get(area) ? 'true' : '' }\" data-attribute=\"${resourceAttribute}\" value=\"${area}\">${option}</option>"
                        } else {
                            filters = "${filters}<option ${isoCode ? isoCode : ''} ${splitFilterChoices?.get(resourceAttribute)?.equals(area) || userFilterChoices?.get(resourceAttribute)?.equals(area) ? "selected" : ""} data-resourceAttributeValue=\"${area}\" data-haschildren=\"${stateFilters?.get(area) ? 'true' : '' }\" data-attribute=\"${resourceAttribute}\" value=\"${area}\">${option}</option>"

                        }

                    }
                } else if ("epdProgram".equals(resourceAttribute)) {
                    epdFilters.each { String epdProgram ->
                        String isoCode
                        Map<String, String> countryCodesForEpd = query?.supportedFilters?.find({
                            it.countryCode
                        })?.countryCode
                        String codeAsText = countryCodesForEpd?.get(epdProgram)?.toLowerCase()
                        isoCode = "data-isoCode='${codeAsText ? codeAsText : 'globe'}'"
                        filters = "${filters}<option ${isoCode ? isoCode : ''} data-resourceAttributeValue=\"${epdProgram}\"  data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(epdProgram) || userFilterChoices?.get(resourceAttribute)?.equals(epdProgram) ? "selected" : ""} value=\"${epdProgram}\">${epdProgram}</option>"
                    }
                } else if ("environmentDataSourceType".equals(resourceAttribute)) {
                    otherTypeFilters?.unique()?.sort({ it.toLowerCase() })?.each {
                        if (com.bionova.optimi.core.Constants.MANUFACTURER_EDST.equals(it)) {
                            filters = "${filters}<option data-resourceAttributeValue=\"${it}\" data-attribute=\"${resourceAttribute}\"  ${userFilterChoices?.get(resourceAttribute)?.equals(it) ? "selected" : ""} value=\"${it}\">${message(code: 'resource.manufacturer')}</option>"
                        } else if (com.bionova.optimi.core.Constants.GENERIC_EDST.equals(it)) {
                            filters = "${filters}<option data-environmentDataSourceType=\"${it}\" data-resourceAttributeValue=\"${it}\" data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(it) || userFilterChoices?.get(resourceAttribute)?.equals(it) ? "selected" : ""} value=\"${it}\">${message(code: 'resource.generic')}</option>"
                        } else if ("private".equals(it)) {
                            filters = "${filters}<option data-environmentDataSourceTypePrivate=\"${it}\" data-resourceAttributeValue=\"${it}\" data-attribute=\"${resourceAttribute}\"  ${userFilterChoices?.get(resourceAttribute)?.equals(it) ? "selected" : ""} value=\"${it}\">${message(code: 'resource.private')}</option>"
                        } else if ("plant".equals(it)) {
                            filters = "${filters}<option data-environmentDataSourceTypePlant=\"${it}\" data-resourceAttributeValue=\"${it}\" data-attribute=\"${resourceAttribute}\"  ${userFilterChoices?.get(resourceAttribute)?.equals(it) ? "selected" : ""} value=\"${it}\">${message(code: 'resource.plant')}</option>"
                        } else {
                            log.error("Unsupported environmentDataSourceType filter attribute: ${it}")
                            flashService.setErrorAlert("Unsupported environmentDataSourceType filter attribute: ${it}", true)
                        }
                    }
                } else if ("combinedUnits".equals(resourceAttribute)) {
                    if ("imperial".equals(user?.unitSystem)) {
                        otherTypeFilters?.unique()?.sort({ it.toLowerCase() })?.each {
                            String imperialUnit = unitConversionUtil.europeanUnitToImperialUnit(it)
                            filters = "${filters}<option data-resourceAttributeValue=\"${it?.replace(",", "")}\"  data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) || userFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) ? "selected" : ""} value=\"${it}\">${imperialUnit}</option>"
                        }
                    } else {
                        otherTypeFilters?.unique()?.sort({ it.toLowerCase() })?.each {
                            filters = "${filters}<option data-resourceAttributeValue=\"${it?.replace(",", "")}\"  data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) || userFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) ? "selected" : ""} value=\"${it}\">${it}</option>"
                        }
                    }
                } else {

                    if (persistingBooleanProperties?.contains(resourceAttribute)) {
                        otherTypeFilters?.each {
                            filters = "${filters}<option data-resourceAttributeValue=\"${it}\"  data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(it) || userFilterChoices?.get(resourceAttribute)?.equals(it) ? "selected" : ""} value=\"${it}\">${it}</option>"
                        }
                    } else if(matchFilterKeys && matchFilterKeys.contains(resourceAttribute) && matchFilterValues){
                        def language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
                        if ("co2Quintile".equals(resourceAttribute)) {
                            if (indicator?.connectedBenchmarks) {
                                matchFilterValues.each { String key, Map<String, String> value ->
                                    String emissionLevel = "5".equals(key) ? "deepGreenEmissions" : "4".equals(key) ? "greenEmissions" : "3".equals(key) ? "yellowEmissions" : "2".equals(key) ? "orangeEmissions" : "1".equals(key) ? "redEmissions" : ""
                                    filters = "${filters}<option data-quintileEmission=\"${emissionLevel}\" data-resourceAttributeValue=\"${key}\" data-attribute=\"quintiles${indicator.connectedBenchmarks.first()}\" ${splitFilterChoices?.get("quintiles" + indicator.connectedBenchmarks.first())?.equals(key) || userFilterChoices?.get("quintiles" + indicator.connectedBenchmarks.first())?.equals(key) ? "selected" : ""} value=\"${key}\">${value?.get(language) ?: "EN"}</option>"
                                }
                            }
                        } else {
                            matchFilterValues.each { String key, Map<String, String> value ->
                                filters = "${filters}<option data-resourceAttributeValue=\"${key}\" data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(key?.replace(",", "")) || userFilterChoices?.get(resourceAttribute)?.equals(key?.replace(",", "")) ? "selected" : ""} value=\"${key}\">${value?.get(language) ?: "EN"}</option>"
                            }
                        }
                    } else {
                        otherTypeFilters?.unique()?.sort({ it.toLowerCase() })?.each {
                            filters = "${filters}<option data-resourceAttributeValue=\"${it?.replace(",", "")}\"  data-attribute=\"${resourceAttribute}\"  ${splitFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) || userFilterChoices?.get(resourceAttribute)?.equals(it?.replace(",", "")) ? "selected" : ""} value=\"${it}\">${it}</option>"
                        }
                    }
                }
            } else {
                log.info("No filters found ${query} with indicator ${indicatorService.getLocalizedName(indicator)} ")
                flashService.setErrorAlert("No filters found ${query} with indicator ${indicatorService.getLocalizedName(indicator)}", true)
                filters = "no filters found"
            }
        } else {
            flashService.setErrorAlert("no filters found", true)
            filters = "no filters found"
        }
        render([output: filters, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getNestedFilters() {
        String childEntityId = params.childEntityId
        String resourceAttribute = params.resourceAttribute
        String resourceTypeId = params.resourceTypeId
        String queryId = params.queryId
        String indicatorId = params.indicatorId
        String chosenAttributes = params.chosenAttributes
        String chosenAttributeValues = params.chosenAttributeValues
        Boolean splitPage = params.boolean('splitPage')
        ResourceType resourceType
        List<ResourceType> subTypes
        String countryId = params.countryId
        if (resourceTypeId) {
            resourceType = resourceTypeService?.getResourceTypeById(resourceTypeId)
        }
        if (resourceType && resourceTypeService.getResourceCount(resourceType.resourceType)) {
            subTypes = resourceTypeService.getSubTypes(resourceType)
        }
        String filters = "<li></li>"
        if(resourceType?.linkedSubTypes) {
            List<ResourceType> linkedSubResList = resourceTypeService.getLinkedSubTypes(resourceType.linkedSubTypes)
            //0014969
            //some resourceTypes might not have any resources linked to it. But allow it if there is any linkedResourceTypes and those (subtypes) have resources
            if (linkedSubResList) {
                linkedSubResList?.each {
                    it.linkedSubTypeCheck = Boolean.TRUE
                }
                if(subTypes){
                    subTypes?.addAll(linkedSubResList)
                }else{
                    subTypes = linkedSubResList
                }
            }
        }


        Entity child = entityService.readEntity(childEntityId)
        User user = userService.getCurrentUser()

        Map<String, List<Object>> resourceFilterCriteria
        Map<String, String> userChosenFilters = [:]

        if (indicatorId && queryId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator) {
                IndicatorQuery indicatorQueryWithResourceFilter = indicator?.indicatorQueries?.find({
                    it.queryId.equals(queryId) && it.resourceFilterCriteria
                })
                if (indicatorQueryWithResourceFilter) {
                    resourceFilterCriteria = indicatorQueryService.getFormattedFilterCriteria(child, IndicatorQuery.RESOURCE, indicatorQueryWithResourceFilter)
                }
            }
        }

        if (chosenAttributes && chosenAttributeValues) {

            Integer chosenAttributesSize = chosenAttributes.tokenize(",")?.toList()?.size()
            List<String> chosenAttributesList = chosenAttributes.tokenize(",")?.toList()
            List<String> chosenAttributeValuesList = chosenAttributeValues.tokenize(",")?.toList()
            //dirty fix for 6066
            if (chosenAttributesList?.contains("Ecoinvent Gabi")) {
                chosenAttributesList.set(chosenAttributeValuesList.indexOf("Ecoinvent Gabi"), "Ecoinvent, Gabi")
            }
            chosenAttributesSize.times {
                userChosenFilters.put(chosenAttributesList.get(it), chosenAttributeValuesList.get(it))
            }
        }
        if (subTypes) {
            for (ResourceType sub: subTypes) {
                if (!sub) {
                    continue
                }
                Integer subCount = resourceTypeService.getNestedFiltersCount(resourceFilterCriteria ? resourceFilterCriteria : null,
                        userChosenFilters && !userChosenFilters.isEmpty() ? userChosenFilters : null, sub)
                if (subCount && subCount > 0) {
                    if (splitPage) {
                        if(sub.linkedSubTypeCheck){
                            filters = "${filters}<li class=\"nestedSubAttributes\" id=\"nestedSubAttribute${sub.subType}\" onclick=\"setSelectValWithNestedOption('quickFilterSplit${resourceAttribute}','${resourceTypeService.getLocalizedName(sub)}', '${sub.subType}','${sub.resourceType}')\" data-attribute=\"${resourceAttribute}\" value=\"${sub.resourceType}\">${sub.multipart||sub.resourceType.equals("construction")?"<img src=\"/app/assets/constructionTypes/structure.png\" class=\"constructionType\">":''}${sub.technicalSpec ? "${resourceTypeService.getLocalizedName(sub)}, ${sub.technicalSpec}" : resourceTypeService.getLocalizedName(sub)} (${subCount}) </li>"
                        }
                        else{
                            filters = "${filters}<li class=\"nestedSubAttributes\" id=\"nestedSubAttribute${sub.subType}\" onclick=\"setSelectValWithNestedOption('quickFilterSplit${resourceAttribute}','${resourceTypeService.getLocalizedName(sub)}', '${sub.subType}','${resourceType.resourceType}')\" data-attribute=\"${resourceAttribute}\" value=\"${sub.resourceType}\">${sub.multipart||sub.resourceType.equals("construction")?"<img src=\"/app/assets/constructionTypes/structure.png\" class=\"constructionType\">":''}${sub.technicalSpec ? "${resourceTypeService.getLocalizedName(sub)}, ${sub.technicalSpec}" : resourceTypeService.getLocalizedName(sub)} (${subCount}) </li>"
                        }
                    } else {
                        if(sub.linkedSubTypeCheck) {
                            filters = "${filters}<li class=\"nestedSubAttributes\" id=\"nestedSubAttribute${sub.subType}\" onclick=\"setSelectValWithNestedOption('quickFilter${resourceAttribute}','${resourceTypeService.getLocalizedName(sub)}', '${sub.subType}','${sub.resourceType}')\" data-attribute=\"${resourceAttribute}\" value=\"${sub.resourceType}\">${sub.multipart||sub.resourceType.equals("construction")?"<img src=\"/app/assets/constructionTypes/structure.png\" class=\"constructionType\">":''}${sub.technicalSpec ? "${resourceTypeService.getLocalizedName(sub)}, ${sub.technicalSpec}" : resourceTypeService.getLocalizedName(sub)} (${subCount}) </li>"
                        }else{
                            filters = "${filters}<li class=\"nestedSubAttributes\" id=\"nestedSubAttribute${sub.subType}\" onclick=\"setSelectValWithNestedOption('quickFilter${resourceAttribute}','${resourceTypeService.getLocalizedName(sub)}', '${sub.subType}','${resourceType.resourceType}')\" data-attribute=\"${resourceAttribute}\" value=\"${sub.resourceType}\">${sub.multipart||sub.resourceType.equals("construction")?"<img src=\"/app/assets/constructionTypes/structure.png\" class=\"constructionType\">":''}${sub.technicalSpec ? "${resourceTypeService.getLocalizedName(sub)}, ${sub.technicalSpec}" : resourceTypeService.getLocalizedName(sub)} (${subCount}) </li>"
                        }
                    }
                }
            }
        } else if(countryId){
            String stateChosen = (user?.userFilterChoices && !user?.userFilterChoices.isEmpty()) ? user?.userFilterChoices.get("state") : ''
            Map<String, List<Document>> stateFilters = optimiResourceService.getStateResourceCountryAsMap()
            List<Document> statesForCountry = stateFilters[countryId]
            //TODO: change  quickfilter hardcode id to dynamic id
            if(statesForCountry && statesForCountry.size() > 0) {
                statesForCountry.each {Document state ->
                    if (splitPage) {
                        filters = "${filters}<li class=\"nestedSubAttributes\" id=\"nestedSubAttribute${state.resourceId}\" onclick=\"setSelectValWithNestedOption('quickFilterSplitareas','${getLocalizedNameFromDocument(state)}','${state.resourceId}', '${countryId}', true)\" data-attribute=\"${resourceAttribute}\" value=\"${state.resourceId}\">${getLocalizedNameFromDocument(state)}</li>"
                    } else {
                        filters = "${filters}<li class=\"nestedSubAttributes ${stateChosen?.equalsIgnoreCase(state.resourceId) ? 'dark-gray-background' : ''}\"  id=\"nestedSubAttribute${state.resourceId}\" onclick=\"setSelectValWithNestedOption('quickFilterareas','${getLocalizedNameFromDocument(state)}','${state.resourceId}','${countryId}', true)\" data-attribute=\"${resourceAttribute}\" value=\"${state.resourceId}\">${getLocalizedNameFromDocument(state)}</li>"
                    }
                }
            }

        }
        render([output: filters, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def userFilterChoices() {
        String resourceAttribute = params.resourceAttribute
        String resourceAttributeValue = params.resourceAttributeValue
        String resourceType = params.resourceType
        String subType = params.subType
        String classificationQuestionId = params.classificationQuestionId
        String classificationParamId = params.classificationParamId
        String stateForCountry = params.stateForCountry
        Boolean splitOrChangeFilter = params.boolean('splitOrChangeFilter')
        Boolean constructionFilter = params.boolean('constructionFilter')
        Boolean productDataListFilter = params.boolean('productDataListFilter')
        User user = userService.getCurrentUser(true)
        Map<String, String> usersFilters = [:]
        Map emptyMap = [:]
        if (user) {
            if (resourceAttribute && resourceAttributeValue && user) {
                Integer resourceAttributesSize = resourceAttribute.tokenize(",").toList().size()
                List<String> resourceAttributesList = resourceAttribute.tokenize(",").toList()
                List<String> resourceAttributeValuesList = resourceAttributeValue.tokenize(",").toList()
                resourceAttributesSize.times {
                    String filter = resourceAttributesList.get(it)
                    String choice = resourceAttributeValuesList.get(it)

                    if (filter == com.bionova.optimi.core.Constants.UserFilterChoice.MARKET_DATASET.filter) {
                        choice = Boolean.valueOf(choice).toString()
                    }

                    usersFilters?.put(filter, choice)
                }
            } else {
                usersFilters = emptyMap
            }

            if (splitOrChangeFilter||constructionFilter||productDataListFilter) {

                user.userSplitFilterChoices = usersFilters

                if (subType && resourceType) {
                    user.userSplitFilterChoices.put("resourceType", resourceType)
                    user.userSplitFilterChoices.put("resourceSubType", subType)
                }
                if(stateForCountry){
                    user.userSplitFilterChoices.put("state", stateForCountry)
                }

                if (constructionFilter || productDataListFilter) {
                    Query constructionCreationQuery

                    if (constructionFilter) {
                        constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                    } else if (productDataListFilter) {
                        constructionCreationQuery = queryService.getQueryByQueryId("productDataListsQuery", true)
                    }

                    List<QueryFilter> supportedFilters = constructionCreationQuery?.supportedFilters

                    if (constructionCreationQuery && classificationQuestionId && classificationParamId) {
                        Question classificationQuestion = questionService.getQuestion(constructionCreationQuery, classificationQuestionId)
                        Map <String, String> lockMandatoryFilters = classificationQuestion?.choices?.find({
                            classificationParamId.equals(it.answerId)
                        })?.lockMandatoryFilters

                        if (lockMandatoryFilters && supportedFilters) {
                            lockMandatoryFilters.each { String key, String value ->
                                QueryFilter lockedFilter = supportedFilters.find({ it.resourceAttribute.equals(key) })
                                if (lockedFilter) {
                                    if (user.userSplitFilterChoices) {
                                        user.userSplitFilterChoices.put(key, value)
                                    } else {
                                        user.userSplitFilterChoices = [(key): value]
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                user.userFilterChoices = usersFilters

                if (subType && resourceType) {
                    user.userFilterChoices.put("resourceType", resourceType)
                    user.userFilterChoices.put("resourceSubType", subType)
                }
                if(stateForCountry){
                    user.userFilterChoices.put("state", stateForCountry)
                }

            }
            userService.updateUser(user)
        }
        render([output: 'userFilters updated', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getResources() {
        String options = "<option></option>"
        List<Resource> resources

        if (params.resourceGroup) {
            List<String> resourceGroups = params.list("resourceGroup")
            String unit = params.unit
            resources = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(resourceGroups, null)

            if (unit) {
                resources = resources?.findAll({ Resource r ->
                    r.combinedUnits && r.combinedUnits.collect({ it.toUpperCase() }).contains(unit.toUpperCase())
                })
            }

            if (resources) {
                Indicator indicator
                Query query

                if (params.indicatorId) {
                    indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
                }

                if (params.queryId) {
                    query = queryService.getQueryByQueryId(params.queryId, true)
                }
                for (Resource resource in resources) {
                    options = "${options}<option value=\"${resource.resourceId}\">${resourceLabel(indicator: indicator, query: query, resource: resource)}</option>"
                }
            }
        } else {
            String queryId = params.queryId
            String sectionId = params.sectionId
            String questionId = params.questionId
            String indicatorId = params.indicatorId
            String entityId = params.entityId

            if (queryId && sectionId && questionId && indicatorId && entityId) {
                Query query = queryService.getQueryByQueryId(queryId, true)
                Entity entity = entityService.readEntity(entityId)
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                Question question = query?.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
                resources = questionService.getFilteredResources(entity, indicator, queryId, null, Boolean.TRUE, question)

                if (resources) {
                    QueryApplyFilterOnCriteria applyFilterOnCriteria = indicator?.indicatorQueries?.find({
                        query.queryId.equals(it.queryId) && it.applyFilterOnCriteria
                    })?.applyFilterOnCriteria

                    if (applyFilterOnCriteria) {
                        String filterOnCriteria = session?.getAttribute("filterOnCriteria")

                        if (filterOnCriteria) {
                            resources = resources?.findAll({ Resource r -> filterOnCriteria.equals(DomainObjectUtil.callGetterByAttributeName(applyFilterOnCriteria.name, r)) })
                        }
                    }

                    for (Resource resource in resources) {
                        options = "${options}<option value=\"${resource.resourceId}\">${resourceLabel(indicator: indicator, query: query, resource: resource)}</option>"
                    }
                }
            }
        }
        render([output: options, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def setResultFormatting() {
        if (params.resultFormatting && params.indicatorId) {
            if (params.resultFormatting) {
                def resultFormatting = [(params.indicatorId): params.resultFormatting]
                session?.setAttribute(Constants.SessionAttribute.RESULT_FORMATTING.toString(), resultFormatting)
            }
        }

        if ("design".equals(params.type)) {
            redirect controller: "design", action: "results", params: [entityId     : params.entityId,
                                                                       childEntityId: params.childEntityId, indicatorId: params.indicatorId, type: "design"]
        } else {
            redirect controller: "operatingPeriod", action: "results", params: [entityId     : params.entityId,
                                                                                childEntityId: params.childEntityId, indicatorId: params.indicatorId, type: "operating"]
        }
    }


    def loadEntityImage() {
        String entityId = params.entityId
        String entityClass = params.entityClass
        String toRender = ''

        if (entityId && entityClass) {
            Boolean thumbImage = params.thumbImage ? Boolean.TRUE : Boolean.FALSE

            if (thumbImage) {
                List<EntityFile> entityFiles = entityService.getEntityImages(entityId, thumbImage)

                if (entityFiles && !entityFiles.isEmpty()) {
                    EntityFile entityFile = entityFiles.get(0)
                    Base64 encoder = new Base64()
                    toRender = "<img src=\"data:" + entityFile.contentType + ";base64," + encoder.encodeBase64String(entityFile.data) + "\" />"
                } else {
                    if ("infrastructure".equalsIgnoreCase(entityClass)) {
                        toRender = "<img src='/app/assets/img/infraroad.png' class='entityImage' >"
                    } else if ("ekokompassi".toLowerCase().equals(entityClass.toLowerCase())) {
                        toRender = "<i class=\"entityclass-small-ekokompassi\"></i>"
                    } else {
                        Entity entity = entityService.readEntity(entityId)
                        if ("infrastructure".equalsIgnoreCase(entity?.entityClass)) {
                            toRender = "<img src='/app/assets/img/infraroad.png' class='entityImage' >"
                        } else {
                            toRender = "<i class=\"entitytype-small ${opt.entityIcon(entity: entity)?.toString()}-small\">"
                        }
                    }
                }
            } else {
                Entity entity = entityService.readEntity(entityId)

                if (entity) {
                    if (entity.hasImage) {
                        toRender = opt.showImage(entity: entity)?.toString()
                    } else {
                        if ("infrastructure".equalsIgnoreCase(entity?.entityClass)) {
                            toRender = "<img src='/app/assets/img/infraroad.png' class='entityImage' >"
                        } else if ("ekokompassi".toLowerCase().equals(entity.entityClass?.toLowerCase())) {
                            toRender = "<i class=\"entityclass-ekokompassi\"></i>"
                        } else {
                            toRender = "<i class=\"entitytype${entity.smallImage ? '-smaller' : ''} ${opt.entityIcon(entity: entity)?.toString()}${entity.smallImage ? '-smaller' : ''}\"></i>"
                        }
                    }
                }
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def setUnitChangeWarning() {
        String warningSet

        if (!session?.getAttribute("unitChangeWarningGiven")) {
            warningSet = "true"
            session?.setAttribute("unitChangeWarningGiven", Boolean.TRUE)
        }
        render text: warningSet ? warningSet : ""
    }

    def existingResourceData() {
        String resourceId = params.resourceId
        def resources

        if (resourceId) {
            resources = optimiResourceService.getResourceProfilesByResourceId(resourceId, null, true)
        }
        [resources: resources]
    }

    def setDefaultProfileFalse() {
        Resource resource

        if (params.id) {
            resource = optimiResourceService.getResourceByDbId(params.id)

            if (resource.defaultProfile) {
                resource.defaultProfile = false
                optimiResourceService.saveResource(resource)
                loggerUtil.info(log, "ResourceId ${resource.resourceId}, profileId ${resource.profileId} was set as defaultprofile = false.")
                User currentUser = userService.getCurrentUser()

                if (currentUser) {
                    optimiMailService.sendMail({
                        to currentUser.username
                        from message(code: "email.support")
                        subject "ResourceId ${resource.resourceId}, profileId ${resource.profileId} was set as defaultprofile = false."
                        body "ResourceId ${resource.resourceId}, profileId ${resource.profileId} was set as defaultprofile = false by user ${currentUser.name}."
                    }, currentUser.username)
                }
            }
        }
        redirect action: "existingResourceData", params: [resourceId: resource?.resourceId]
    }

    def renderResultComparePerCategory() {
        String toCompareEntityId = params.entityId
        String otherEntityId = params.otherEntityId

        String indicatorId = params.indicatorId
        String reportItemId = params.reportItemId

        if (params.calculationRule && toCompareEntityId && otherEntityId && indicatorId) {
            List<String> calculationRulesIds = params.list("calculationRule")
            DecimalFormat decimalFormat = calculateDifferenceUtil.getDecimalFormatForDifference()
            Entity toCompareEntity = entityService.readEntity(toCompareEntityId)
            Entity parent = toCompareEntity?.parentById
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity otherEntity = entityService.readEntity(otherEntityId)

            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(null, true)
            Boolean trialResults = toCompareEntity?.isFreeTrial


            if (toCompareEntity && otherEntity && indicator) {
                IndicatorReportItem indicatorReportItem = indicatorReportService.getReportItemsAsReportItemObjects(parent, indicator.report?.reportItems)?.find({
                    it.reportItemId?.equals(reportItemId)
                })
                List<String> indicatorReportCategories = indicatorReportItem?.categories
                List<String> indicatorReportRules = indicatorReportItem?.rules

                List<ResultCategory> resultCategories = []
                List<ResultCategory> resolvedResultCategories = indicator.getResolveResultCategories(null) //TODO: can compare different projects can we give parent?

                if (indicatorReportCategories) {
                    indicatorReportCategories.each { String category ->
                        ResultCategory resultCategory = resolvedResultCategories?.find({
                            category.equals(it.resultCategoryId)
                        })

                        if (!resultCategory) {
                            resultCategory = resolvedResultCategories?.find({
                                category.equals(it.resultCategory)
                            })
                        }

                        if (resultCategory) {
                            resultCategories.add(resultCategory)
                        }
                    }
                } else {
                    resultCategories = resolvedResultCategories
                }

                if (indicatorReportRules) {
                    calculationRulesIds = indicatorReportRules
                }

                List<CalculationResult> toCompareCalculationResults = toCompareEntity.getCalculationResultObjects(indicatorId, null, null)
                List<CalculationResult> otherEntityCalculationResults = otherEntity.getCalculationResultObjects(indicatorId, null, null)

                String comparisonTable = ""

                Double originalEntityScore
                Double comparedEntityScore

                if (toCompareCalculationResults && otherEntityCalculationResults) {
                    resultCategories.each { ResultCategory resultCategory ->


                        boolean hasResult = false
                        boolean hasApplyConditionData = false

                        if (calculationRulesIds) {
                            for (String calcRule : calculationRulesIds) {
                                if ((otherEntityCalculationResults?.find({
                                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && calcRule.equals(it.calculationRuleId)
                                })?.result) || (toCompareCalculationResults.find({
                                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && calcRule.equals(it.calculationRuleId)
                                })?.result)) {
                                    hasResult = true
                                    break
                                }
                            }
                        }

                        if (resultCategory.applyConditions?.dataPresent && hasResult) {
                            hasApplyConditionData = true
                        }

                        comparisonTable = "${comparisonTable}<tr  id=\"resultRow${resultCategory.resultCategoryId}\" class=\"${resultCategory?.applyConditions && !hasApplyConditionData ? 'hidden' : ''}\">"

                        List<String> resultCategoryQueryIds = resultCategory?.data?.keySet()?.toList()

                        String queryId = resultCategoryQueryIds?.find({ String queryId -> queryId })
                        if (resultCategoryQueryIds?.size() == 1 && resultCategory?.linkToQueryFromReport) {

                            if (!indicator?.hideResultCategoryLabels) {
                                comparisonTable = comparisonTable + "<td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: toCompareEntity?.id, queryId: queryId, entityId: toCompareEntity?.parentEntityId])}\" class=\"link\">${resultCategory?.resultCategory}</a></td>"
                            } else {
                                comparisonTable = comparisonTable + "<td>&nbsp;</td>"
                            }
                            comparisonTable = comparisonTable + "<td><a href=\"${createLink(controller: 'query', action: 'form', params: [indicatorId: indicator?.indicatorId, childEntityId: toCompareEntity?.id, queryId: queryId, entityId: toCompareEntity?.parentEntityId])}\" class=\"link\">${resultCategory?.localizedName}</a></td>"
                        } else {

                            if (!indicator?.hideResultCategoryLabels) {
                                comparisonTable = comparisonTable + "<td>${resultCategory?.resultCategory}</td><td>${resultCategory?.localizedName}</td>"
                            } else {
                                comparisonTable = comparisonTable + "<td>${resultCategory?.localizedName}</td><td>&nbsp;</td>"
                            }
                        }

                        if (indicator?.requireMonthly) {
                            def months = []
                            for (int i = 1; i < 13; i++) {
                                months.add("${message(code: "month." + i)}")
                            }
                            int monthIndex = 1
                            def originalEntityMonthlyScore
                            def comparedEntityMonthlyScore
                            calculationRulesIds?.each { String calculationRuleId ->
                                CalculationRule calculationRule = calculationRules?.find({it.calculationRuleId == calculationRuleId})

                                months?.each {
                                    if (trialResults && calculationRule?.hideInTrial) {
                                        comparisonTable = "${comparisonTable}<td class=\"monthlyCompare text-right to-bold-result\">TRIAL</td>"
                                    } else {
                                        originalEntityMonthlyScore = toCompareCalculationResults?.find({
                                            resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                                        })?.monthlyResults?.get(monthIndex.toString())

                                        comparedEntityMonthlyScore = otherEntityCalculationResults?.find({
                                            resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                                        })?.monthlyResults?.get(monthIndex.toString())

                                        if (originalEntityMonthlyScore) {
                                            String formattedOriginalMonthlyScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, originalEntityMonthlyScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                            String formattedCompareMonthlyScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, comparedEntityMonthlyScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                            Double difference = calculateDifferenceUtil.calculateDifference(comparedEntityMonthlyScore, originalEntityMonthlyScore)

                                            if (!difference) {
                                                difference = 0
                                            }

                                            if (difference != null) {
                                                def range = 1..5
                                                if (difference >= 10 || difference <= -10) {
                                                    difference = difference.round()
                                                    difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                                    difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                                                }
                                                def formattedDifference = decimalFormat.format(difference)

                                                if (difference <= -10) {
                                                    comparisonTable = "${comparisonTable}<td  class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\">${formattedOriginalMonthlyScore}<span style=\"color: green;\" class=\"compareDifferenceSpan\" > | ${formattedDifference} %</span></td>"
                                                } else if (difference >= 5) {
                                                    comparisonTable = "${comparisonTable}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\">${formattedOriginalMonthlyScore}<span style=\"color: red;\" class=\"compareDifferenceSpan\" > | +${formattedDifference} %</span></td>"
                                                } else if (range.contains(difference.toInteger())) {
                                                    comparisonTable = "${comparisonTable}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\">${formattedOriginalMonthlyScore}<span style=\"color: black;\" class=\"compareDifferenceSpan\"> | +${formattedDifference} % </span></td>"
                                                } else {
                                                    comparisonTable = "${comparisonTable}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\">${formattedOriginalMonthlyScore}<span style=\"color: black;\" class=\"compareDifferenceSpan\"> | ${formattedDifference} %</span></td>"
                                                }
                                            }

                                        } else {
                                            comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${it}${otherEntityId}\">&nbsp;</td>"

                                        }
                                        monthIndex++
                                    }

                                }

                                if (trialResults && calculationRule?.hideInTrial) {
                                    comparisonTable = "${comparisonTable}<td class=\"text-right to-bold-result\">TRIAL</td>"
                                } else {
                                    originalEntityScore = toCompareCalculationResults.find({
                                        resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                                    })?.result
                                    comparedEntityScore = otherEntityCalculationResults?.find({
                                        resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                                    })?.result

                                    if (originalEntityScore) {
                                        String formattedOriginalEntityScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, originalEntityScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                        String formattedComparedEntityScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, comparedEntityScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                        Double difference = calculateDifferenceUtil.calculateDifference(comparedEntityScore, originalEntityScore)

                                        if (!difference) {
                                            difference = 0
                                        }

                                        if (difference != null) {
                                            def range = 1..5
                                            def formattedDifference = decimalFormat.format(difference)
                                            if (difference >= 10 || difference <= -10) {
                                                difference = difference.round()
                                                difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                                difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                                            }
                                            if (difference <= -10) {
                                                comparisonTable = "${comparisonTable}<td  class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\">${formattedOriginalEntityScore}<span style=\"color: green;\" class=\"compareDifferenceSpan\"> | ${formattedDifference} %</span></td>"
                                            } else if (difference >= 5) {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\">${formattedOriginalEntityScore}<span style=\"color: red;\" class=\"compareDifferenceSpan\"> | +${formattedDifference} %</span></td>"
                                            } else if (range.contains(difference.toInteger())) {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\">${formattedOriginalEntityScore}<span style=\"color: black;\" class=\"compareDifferenceSpan\"> | +${formattedDifference} % </span></td>"
                                            } else {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\">${formattedOriginalEntityScore}<span style=\"color: black;\" class=\"compareDifferenceSpan\"> | ${formattedDifference} %</span></td>"
                                            }
                                        }

                                    } else {
                                        comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\">&nbsp;</td>"

                                    }
                                }
                            }
                        } else {
                            calculationRulesIds.each { String ruleId ->
                                CalculationRule calculationRule = calculationRules?.find({it.calculationRuleId == ruleId})
                                if (trialResults && calculationRule?.hideInTrial) {
                                    comparisonTable = "${comparisonTable}<td class=\"text-right to-bold-result\">TRIAL</td>"
                                } else {
                                    originalEntityScore = toCompareCalculationResults.find({
                                        resultCategory.resultCategoryId.equals(it.resultCategoryId) && ruleId.equals(it.calculationRuleId)
                                    })?.result
                                    comparedEntityScore = otherEntityCalculationResults?.find({
                                        resultCategory.resultCategoryId.equals(it.resultCategoryId) && ruleId.equals(it.calculationRuleId)
                                    })?.result

                                    if (originalEntityScore && comparedEntityScore) {
                                        String formattedOriginalEntityScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, originalEntityScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                        String formattedComparedEntityScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, comparedEntityScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                        Double difference = calculateDifferenceUtil.calculateDifference(comparedEntityScore, originalEntityScore)

                                        if (!difference) {
                                            difference = 0
                                        }

                                        if (difference != null) {
                                            if (difference >= 10 || difference <= -10) {
                                                difference = difference.round()
                                                difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                                difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                                            }
                                            def range = 1..5
                                            def formattedDifference = decimalFormat.format(difference)

                                            if (difference <= -10) {
                                                comparisonTable = "${comparisonTable}<td  class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">${formattedOriginalEntityScore} <span style=\"color: black\"> | </span><span style=\"color: green;\" class=\"compareDifferenceSpan\">  ${formattedDifference} %</span></td>"
                                            } else if (difference >= 5) {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">${formattedOriginalEntityScore} <span style=\"color: black\"> | </span><span style=\"color: red;\"  class=\"compareDifferenceSpan\"> +${formattedDifference} %</span></td>"
                                            } else if (range.contains(difference.toInteger())) {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">${formattedOriginalEntityScore} <span style=\"color: black\"> | </span><span style=\"color: black;\" class=\"compareDifferenceSpan\"> +${formattedDifference} % </span></td>"
                                            } else {
                                                comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">${formattedOriginalEntityScore} <span style=\"color: black\"> | </span><span style=\"color: black;\" class=\"compareDifferenceSpan\">  ${formattedDifference} %</span></td>"
                                            }
                                        }

                                    } else {
                                        String formattedOriginalEntityScore = resultFormattingResolver.formatByResultFormatting(userService.getCurrentUser(), indicator, null, originalEntityScore, session, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, "")
                                        if (!originalEntityScore && comparedEntityScore) {
                                            comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\"><span style=\"color: green;\"  class=\"compareDifferenceSpan\"> -100%</span></td>"

                                        } else if (originalEntityScore && !comparedEntityScore) {
                                            comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">${formattedOriginalEntityScore}  <span style=\"color: black\"> | </span>  <span style=\"color: red;\"  class=\"compareDifferenceSpan\"> +100%</span></td>"

                                        } else {
                                            comparisonTable = "${comparisonTable}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\">&nbsp;</td>"

                                        }

                                    }

                                }

                            }
                        }

                        if ((!resultCategory.preventExpand || userService.getSuperUser(userService.getCurrentUser())) && !indicator?.hideExpandAndCollapse) {
                            comparisonTable = comparisonTable + "<td colspan=\"3\"><a href=\"javascript:;\" style=\"color: #6b9f00;\" onclick=\"fullScreenPopup('" +
                                    "${createLink(controller: 'result', action: 'categoryDetails', params: [entityId: toCompareEntity.id, indicatorId: indicator?.indicatorId, resultCategoryId: resultCategory.resultCategoryId, reportItemId: reportItemId])}');\">${message(code: 'results.expand')}</a></td>"
                        } else {
                            comparisonTable = comparisonTable + "<td colspan=\"4\"></td>"
                        }
                        comparisonTable = "${comparisonTable}</tr>"

                    }
                    comparisonTable = "${comparisonTable}"
                }
                render([output: comparisonTable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            render([output: "no result", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

    }

    def renderResultCompare() {
        String toCompareEntityId = params.entityId
        String otherEntityId = params.otherEntityId
        String parentEntityId = params.parentEntityId
        String indicatorId = params.indicatorId
        String reportItemId = params.reportItemId

        if (params.calculationRule && toCompareEntityId && otherEntityId && indicatorId) {
            User user = userService.getCurrentUser()
            DecimalFormat decimalFormat = calculateDifferenceUtil.getDecimalFormatForDifference()
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            List<String> calculationRulesIds = params.list("calculationRule")
            Entity toCompareEntity = entityService.readEntity(toCompareEntityId)
            Entity otherEntity = entityService.readEntity(otherEntityId)
            CalculationTotalResult calculationTotalResultsToCompareEntity = toCompareEntity?.getCalculationTotalResults()?.find({
                indicator.indicatorId.equals(it.indicatorId)
            })
            CalculationTotalResult calculationTotalResultsOtherEntity = otherEntity?.getCalculationTotalResults()?.find({
                indicator.indicatorId.equals(it.indicatorId)
            })

            def otherEntityName = otherEntity.operatingPeriodAndName
            def entityName = toCompareEntity.operatingPeriodAndName
            def compareWith = message(code: "entity.compare_with")
            def total = message(code: "totalScore")
            int colspan = 1

            IndicatorReportItem indicatorReportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, indicator?.report?.reportItems)?.find({
                it.reportItemId?.equals(reportItemId)
            })
            List<String> indicatorReportRules = indicatorReportItem?.rules
            List<String> indicatorReportCategories = indicatorReportItem?.categories
            List<ResultCategory> resolvedCategories = indicator?.getResolveResultCategories(null)
            List<ResultCategory> resultCategories = resolvedCategories?.findAll({
                indicatorReportCategories?.contains(it.resultCategory)
            }) ? resolvedCategories?.findAll({
                indicatorReportCategories?.contains(it.resultCategory)
            }) : resolvedCategories
            Boolean showTotals = indicatorReportItem?.tableSettings?.get("showTotals")
            if (indicatorReportRules) {
                calculationRulesIds = indicatorReportRules
            }
            String renderRows = ''
            if (showTotals || (!reportItemId && !resultCategories?.find({ it.ignoreFromTotals }))) {
                if (indicator?.requireMonthly && calculationTotalResultsToCompareEntity && calculationTotalResultsOtherEntity) {
                    renderRows = "<tbody id=\"compareTotalTbody${reportItemId ? reportItemId : ''}\"><tr class=\"compareWith\"><td>&nbsp;</td><td> <span class=\"bold\">${message(code: 'entity.compare_total')}</span>  <a href='/app/sec/design/results?childEntityId=${otherEntity?.id}&indicatorId=${indicator?.indicatorId}&entityId=${parentEntityId}'>${otherEntityName}</a></td>"

                    def months = []
                    for (int i = 1; i < 13; i++) {
                        months.add("${message(code: "month." + i)}")
                    }
                    int monthIndex = 1
                    Double originalEntityMonthlyScore
                    Double otherEntityOriginalMonthlyScore

                    calculationRulesIds?.each { String ruleId ->
                        months.each {
                            Map monthlyTotalsOtherEntity = calculationTotalResultsOtherEntity.monthlyTotalsPerCalculationRule

                            if (monthlyTotalsOtherEntity) {
                                otherEntityOriginalMonthlyScore = monthlyTotalsOtherEntity?.get(ruleId)?.get(monthIndex.toString())
                            }
                            def formattedOtherMonthlyScore = resultFormattingResolver.formatByResultFormatting(user, indicator, null,
                                    otherEntityOriginalMonthlyScore, session, false, false, true, "")

                            renderRows = "${renderRows}<td class=\"monthlyCompare\">${formattedOtherMonthlyScore}</td>"

                            monthIndex++
                        }
                        Double otherTotalScore = otherEntity.getTotalResult(indicatorId, ruleId)
                        Double toCompareTotalScore = toCompareEntity.getTotalResult(indicatorId, ruleId)

                        def formattedOtherTotalScore = resultFormattingResolver.formatByResultFormatting(user, indicator, null, otherTotalScore, session, false, false, true, "")
                        renderRows = "${renderRows}<td class=\"text-right\">${formattedOtherTotalScore}</td><td>&nbsp;</td></tr>"

                        renderRows = "${renderRows}<tr><td colspan=\"${colspan}\"></td> <td${!indicator.hideZeroResults ? ' ' : ''}colspan=\"${colspan}\"><strong>${entityName}&nbsp;${compareWith}&nbsp;${otherEntityName}&nbsp;</strong></td>"
                        int monthIndex2 = 1

                        months.each {
                            Map monthlyTotalsToCompareEntity = calculationTotalResultsToCompareEntity.monthlyTotalsPerCalculationRule
                            Map monthlyTotalsOtherEntity = calculationTotalResultsOtherEntity.monthlyTotalsPerCalculationRule
                            if (monthlyTotalsToCompareEntity) {
                                otherEntityOriginalMonthlyScore = monthlyTotalsOtherEntity?.get(ruleId)?.get(monthIndex2.toString())
                                originalEntityMonthlyScore = monthlyTotalsToCompareEntity?.get(ruleId)?.get(monthIndex2.toString())
                            }
                            Double difference = calculateDifferenceUtil.calculateDifference(otherEntityOriginalMonthlyScore, originalEntityMonthlyScore)

                            if (!difference) {
                                difference = 0
                            }

                            if (difference != null) {
                                if (difference >= 10 || difference <= -10) {
                                    difference = difference.round()
                                    difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                    difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                                }
                                def range = 1..5
                                def formattedDifference = decimalFormat.format(difference)
                                if (difference <= -10) {
                                    renderRows = "${renderRows}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\" style=\"color: green;\">${formattedDifference} %</td>"
                                } else if (difference >= 5) {
                                    renderRows = "${renderRows}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\" style=\"color: red;\">+${formattedDifference} %</td>"
                                } else if (range.contains(difference.toInteger())) {
                                    renderRows = "${renderRows}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\" style=\"color: black;\">+${formattedDifference} %</td>"
                                } else {
                                    renderRows = "${renderRows}<td class=\"monthlyCompare\" id=\"compareSpan${it}${otherEntityId}\" style=\"color: black;\">${formattedDifference} %</td>"
                                }
                            }
                            monthIndex2++
                        }

                        Double difference = calculateDifferenceUtil.calculateDifference(otherTotalScore, toCompareTotalScore)

                        if (!difference) {
                            difference = 0
                        }

                        if (difference != null) {
                            if (difference >= 10 || difference <= -10) {
                                difference = difference.round()
                                difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                            }
                            def range = 1..5
                            def formattedDifference = decimalFormat.format(difference)

                            if (difference <= -10) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\" style=\"color: green;\">${formattedDifference} %</td>"
                            } else if (difference >= 5) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\" style=\"color: red;\">+${formattedDifference} %</td>"
                            } else if (range.contains(difference.toInteger())) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\" style=\"color: black;\">+${formattedDifference} %</td>"
                            } else {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${ruleId}${otherEntityId}\" style=\"color: black;\">${formattedDifference} %</td>"
                            }
                        }
                    }
                    renderRows = "${renderRows}<td>&nbsp;</td></tr></tbody>"

                } else {
                    renderRows = "<tbody id=\"compareTotalTbody${reportItemId ? reportItemId : ''}\"><tr><td>&nbsp;</td><td> <span class=\"bold\">${message(code: 'entity.compare_total')}</span>  <a href='/app/sec/design/results?childEntityId=${otherEntity?.id}&indicatorId=${indicator?.indicatorId}&entityId=${parentEntityId}'>${otherEntityName}</a></td><td colspan=\"12\"></td></tr><tr class=\"compareWith\"><td colspan=\"${colspan}\"><td colspan=\"${colspan}\"><strong>${otherEntityName}&nbsp;&nbsp;${total}</strong></td>"

                    Double otherTotalScore
                    Double toCompareTotalScore
                    calculationRulesIds?.each { String calculationRuleId ->

                        resultCategories.each { ResultCategory category ->
                            otherTotalScore = otherEntity.getTotalResult(indicatorId, calculationRuleId)
                        }

                        def formattedOtherTotalScore = resultFormattingResolver.formatByResultFormatting(user, indicator, null,
                                otherTotalScore, session, false, false, true, "")
                        renderRows = "${renderRows}<td class=\"text-right\">${formattedOtherTotalScore}</td>"
                    }

                    renderRows = "${renderRows}<th style=\"background-color:#DCDCDC;\"colspan=\"3\"></tr><tr><td colspan=\"${colspan}\"></td> <td${!indicator.hideZeroResults ? ' ' : ''}colspan=\"${colspan}\"><strong>${entityName}&nbsp;${compareWith}&nbsp;${otherEntityName}&nbsp;</strong></td>"

                    calculationRulesIds?.each { String calculationRuleId ->

                        resultCategories.each { ResultCategory category ->
                            toCompareTotalScore = toCompareEntity.getTotalResult(indicatorId, calculationRuleId)
                            otherTotalScore = otherEntity.getTotalResult(indicatorId, calculationRuleId)
                        }

                        Double difference = calculateDifferenceUtil.calculateDifference(otherTotalScore, toCompareTotalScore)

                        if (!difference) {
                            difference = 0
                        }

                        if (difference != null) {
                            if (difference >= 10 || difference <= -10) {
                                difference = difference.round()
                                difference > 100 ? difference = Math.round(difference / 10) * 10 : difference
                                difference > 1000 ? difference = Math.round(difference / 100) * 100 : difference
                            }
                            def range = 1..5
                            def formattedDifference = decimalFormat.format(difference)
                            if (difference <= -10) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\" style=\"color: green;\">${formattedDifference} %</td>"
                            } else if (difference >= 5) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\" style=\"color: red;\">+${formattedDifference} %</td>"
                            } else if (range.contains(difference.toInteger())) {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\" style=\"color: black;\">+${formattedDifference} %</td>"
                            } else {
                                renderRows = "${renderRows}<td class=\"text-right\" id=\"compareSpan${calculationRuleId}${otherEntityId}\" style=\"color: black;\">${formattedDifference} %</td>"
                            }
                        }
                    }
                    renderRows = "${renderRows}<td>&nbsp;</td></tr></tbody>"
                }
            }
            render([output: renderRows, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def logJSError() {
        String errorMessage = params.errorMessage
        if (errorMessage) {
            log.error("${errorMessage}")
        }
        return true
    }

    def multiDataAjaxWarningForCompare() {
        String toCompareEntityId = params.entityId
        String otherEntityId = params.otherEntityId
        String indicatorId = params.indicatorId
        String reportItemId = params.reportItemId
        String renderer = ""

        if (params.calculationRule && toCompareEntityId && otherEntityId && indicatorId) {
            List<String> calculationRulesIds = params.list("calculationRule")
            DecimalFormat decimalFormat = calculateDifferenceUtil.getDecimalFormatForDifference()
            Entity toCompareEntity = entityService.readEntity(toCompareEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity otherEntity = entityService.readEntity(otherEntityId)

            if (toCompareEntity && otherEntity && indicator) {
                IndicatorReportItem indicatorReportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, indicator.report?.reportItems)?.find({
                    it.reportItemId?.equals(reportItemId)
                })
                List<String> indicatorReportCategories = indicatorReportItem?.categories
                List<ResultCategory> resultCategories = []
                List<ResultCategory> resolvedCategories = indicator.getResolveResultCategories(null)

                if (indicatorReportCategories) {
                    indicatorReportCategories.each { String category ->
                        ResultCategory resultCategory = resolvedCategories?.find({
                            category.equals(it.resultCategoryId)
                        })

                        if (!resultCategory) {
                            resultCategory = resolvedCategories?.find({
                                category.equals(it.resultCategory)
                            })
                        }

                        if (resultCategory) {
                            resultCategories.add(resultCategory)
                        }
                    }
                } else {
                    resultCategories = resolvedCategories
                }


                List<CalculationResult> toCompareCalculationResults = toCompareEntity.getCalculationResultObjects(indicatorId, null, null)
                List<CalculationResult> otherEntityCalculationResults = otherEntity.getCalculationResultObjects(indicatorId, null, null)

                if (toCompareCalculationResults && otherEntityCalculationResults && resultCategories) {
                    resultCategories.each { ResultCategory resultCategory ->
                        List<String> upstreamDBClassifiedList = []
                        List<String> toCompareUpstreamDbs = toCompareEntity.getDatasetsForResultCategoryExpand(indicatorId, resultCategory.resultCategoryId, toCompareCalculationResults)?.collect({
                            datasetService.getResource(it)
                        })?.findAll({ it?.upstreamDBClassified })?.collect({ it?.upstreamDBClassified })
                        List<String> otherUpstreamDbs = otherEntity.getDatasetsForResultCategoryExpand(indicatorId, resultCategory.resultCategoryId, otherEntityCalculationResults)?.collect({
                            datasetService.getResource(it)
                        })?.findAll({ it?.upstreamDBClassified })?.collect({ it?.upstreamDBClassified })

                        if (toCompareUpstreamDbs) {
                            toCompareUpstreamDbs.each { String upstreamDb ->
                                upstreamDBClassifiedList.add(upstreamDb)

                            }
                        }
                        if (otherUpstreamDbs) {
                            otherUpstreamDbs.each { String upstreamDb ->
                                upstreamDBClassifiedList.push(upstreamDb)
                            }
                        }
                        if (upstreamDBClassifiedList && upstreamDBClassifiedList.unique().size() > 1 && indicator?.multiDataWarning) {
                            renderer = "${message(code: "query.multiple_datasources")}: ${upstreamDBClassifiedList.unique().toString().replace("[", "").replace("]", "")}"
                        }
                    }
                }
            }
        }
        render([output: renderer, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getEpdFile() {
        String resourceId = params.resourceId
        String profileId = params.profileId
        File epdFile

        if (resourceId && profileId) {
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
            epdFile = resourceService.getEpdFile(resource?.downloadLink)
        }

        if (epdFile && epdFile.exists()) {
            render(file: epdFile, contentType: "application/pdf")
        } else {
            loggerUtil.error(log, "Failed to load PDF document: " + epdFile + " does not exist")
            render(text: "${message(code: 'resource.pdfFile_notFound')}")
        }
    }

    def mapTrainingDataSet() {
        String fileName = params.fileName
        String applicationId = params.applicationId

        if (fileName && applicationId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)

            if (application && application.systemTrainingDatas) {
                SystemTrainingDataSet systemTrainingDataSet = application.systemTrainingDatas.find({
                    it.fileName?.equalsIgnoreCase(fileName)
                })

                if (systemTrainingDataSet) {
                    [systemTrainingDataSet: systemTrainingDataSet]
                }
            }
        }
    }

    def calculateCost() {
        Boolean allowVariableThickness = params.boolean("allowVariableThickness")
        User user = userService.getCurrentUser()
        DecimalFormat dfCost = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
        DecimalFormat dfTotal = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString) ?: new Locale("fi"))
        dfCost.applyPattern('#,###,###.##')
        dfTotal.applyPattern('#,###,###')
        DecimalFormat dfCostDefault = new DecimalFormat("#.##")
        DecimalFormat dfTotalDefault = new DecimalFormat("#")

        Double labourCostCraftsman = params.double("labourCostCraftsman")
        Double labourCostWorker = params.double("labourCostWorker")
        Double finalMultiplier = params.double("finalMultiplier")
        Double currencyMultiplier = params.double("currencyMultiplier")
        Double costConstruction = "undefined".equals(params.costConstruction) || !params.costConstruction ? null : params.costConstruction.toDouble()
        Double defaultDensity = "undefined".equals(params.defaultDensity) || !params.defaultDensity ? null : params.defaultDensity.toDouble()
        Double defaultThickness = "undefined".equals(params.defaultThickness) || !params.defaultThickness ? null : params.defaultThickness.toDouble()
        Double defaultThickness_in = "undefined".equals(params.defaultThickness_in) || !params.defaultThickness_in ? null : params.defaultThickness_in.toDouble()
        Double userGivenThickness = "undefined".equals(params.userGivenThickness) || !params.userGivenThickness ? null : params.userGivenThickness.replaceAll(",", ".")?.toDouble()
        Double resourceTypeCostMultiplier = "undefined".equals(params.resourceTypeCostMultiplier) || !params.resourceTypeCostMultiplier ? null : params.resourceTypeCostMultiplier.toDouble()
        Double massConversionFactor = "undefined".equals(params.massConversionFactor) || !params.massConversionFactor ? null : params.massConversionFactor.toDouble()
        String userGivenCustomCostTotalMultiplier = "undefined".equals(params.userGivenCustomCostTotalMultiplier) || !params.userGivenCustomCostTotalMultiplier ? null : params.userGivenCustomCostTotalMultiplier
        String defaultTargetCostPerUnit = "undefined".equals(params.defaultTargetCostPerUnit) || !params.defaultTargetCostPerUnit ? null : params.defaultTargetCostPerUnit
        String userGivenUnit = params.userGivenUnit
        String userGivenQuantityString = params.userGivenQuantity
        String resourceSubType = params.subType
        String unitForData = params.unitForData
        String costCalculationMethod = params.costCalculationMethod

        Map<String, String> calculatedCost = [:]
        Double userGivenQuantity
        Double costPerUnit
        Double totalCost

        try {
            userGivenQuantity = DomainObjectUtil.isNumericValue(userGivenQuantityString) ? DomainObjectUtil.convertStringToDouble(userGivenQuantityString) : null
        } catch (Exception e) {
            loggerUtil.warn(log, "Cost calc failed", e)
            flashService.setErrorAlert("Cost calc failed: ${e.message}", true)
        }

        if (userGivenQuantity != null && resourceSubType) {
            CostStructure costStructure = costStructureService.getCostStructureByResourceSubType(resourceSubType)
            Double costEur = DomainObjectUtil.isNumericValue(defaultTargetCostPerUnit) ? DomainObjectUtil.convertStringToDouble(defaultTargetCostPerUnit) : null
            Double cost = datasetService.resolveCostFromParams(costStructure, userGivenUnit, unitForData, userGivenCustomCostTotalMultiplier, allowVariableThickness, currencyMultiplier, finalMultiplier,
                    userGivenQuantity, labourCostWorker, labourCostCraftsman, userGivenThickness, defaultThickness, defaultThickness_in, defaultDensity, massConversionFactor, resourceTypeCostMultiplier,
                    costCalculationMethod, costConstruction, null)

            if(!cost && costEur) {
                cost = costEur
            }

            if (costStructure && cost) {
                costPerUnit = cost

                if (userGivenQuantity != null) {
                    totalCost = cost * userGivenQuantity
                }
            } else if (userGivenCustomCostTotalMultiplier && userGivenQuantity != null && cost) {
                totalCost = cost * userGivenQuantity
            }
        } else if (userGivenQuantity != null && (userGivenCustomCostTotalMultiplier || defaultTargetCostPerUnit)) {
            Double cost
            try {
                if (userGivenCustomCostTotalMultiplier) {
                    cost = DomainObjectUtil.convertStringToDouble(userGivenCustomCostTotalMultiplier)
                } else {
                    cost = DomainObjectUtil.convertStringToDouble(defaultTargetCostPerUnit)
                }

                if (cost != null) {
                    totalCost = cost * userGivenQuantity
                }
            } catch (Exception e) {
                loggerUtil.warn(log, "Could not calculate cost for user:", e)
                flashService.setErrorAlert("Could not calculate cost for user: ${e.message}", true)
            }
        }

        if (costPerUnit == null && totalCost == null && !userGivenCustomCostTotalMultiplier) {
            calculatedCost.put("uncalculable", "true")
        }

        if (costPerUnit != null) {
            calculatedCost.put("costPerUnit", stringUtilsService.abbr(dfCost.format(costPerUnit), 10))
            calculatedCost.put("costPerUnitHidden", dfCostDefault.format(costPerUnit.round(2)))
        }

        if (totalCost != null) {
            calculatedCost.put("costTotal", stringUtilsService.abbr(dfTotal.format(totalCost), 10))
            calculatedCost.put("costTotalHidden", dfTotalDefault.format(totalCost.round(0)))
        }

        if (userGivenUnit) {
            calculatedCost.put("convertedUnit", userGivenUnit)
        }

        render([calculatedCost: calculatedCost, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getResourcesForDataLoadingFeature() {
        String queryId = params.queryId
        String filterValue = params.filterValue
        List<Resource> resources

        if (queryId && filterValue) {
            DataLoadingFeature dataLoadingFeature = queryService.getQueryByQueryId(queryId, true)?.dataLoadingFeature

            if (dataLoadingFeature) {
                resources = optimiResourceService.getResourcesForDataLoadingFeature(filterValue, dataLoadingFeature.loadedResourcesRequiredResourceGroups, dataLoadingFeature.loadResourcesOrderedByValue)
            }
        }
        render([resources: resources, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getDataLoadingFeatureOtherQuestionValue() {
        List<Map<String, String>> answers = []
        String resourceId = params.resourceId
        String profileId = params.profileId
        Integer loadResourceParameterDecimals

        if (params.loadResourceParameterDecimals) {
            loadResourceParameterDecimals = params.int("loadResourceParameterDecimals")
        }

        def datasetsMap = com.mongodb.util.JSON.parse(params.datasets)

        if (datasetsMap) {
            if (resourceId) {
                Resource resource = optimiResourceService.getResourceWithGorm(resourceId, profileId)

                if (resource) {
                    datasetsMap.each { Map<String, String> paramPerSectionAndQuestion ->
                        String resourceParameter = paramPerSectionAndQuestion.get("resourceParameter")
                        String sectionId = paramPerSectionAndQuestion.get("sectionId")
                        String questionId = paramPerSectionAndQuestion.get("questionId")

                        if (resourceParameter) {
                            String otherQuestionAnswer = DomainObjectUtil.callGetterByAttributeName(resourceParameter, resource)?.toString()

                            if (!otherQuestionAnswer) {
                                otherQuestionAnswer = ""
                            } else if (otherQuestionAnswer && DomainObjectUtil.isNumericValue(otherQuestionAnswer)) {
                                if (loadResourceParameterDecimals != null) {
                                    otherQuestionAnswer = formatNumber(number: DomainObjectUtil.convertStringToDouble(otherQuestionAnswer), type: "number", minFractionDigits: loadResourceParameterDecimals, maxFractionDigits: loadResourceParameterDecimals, roundingMode: "HALF_UP")
                                } else {
                                    otherQuestionAnswer = formatNumber(number: DomainObjectUtil.convertStringToDouble(otherQuestionAnswer), type: "number", roundingMode: "HALF_UP")
                                }
                            }
                            answers.add([sectionId: sectionId, questionId: questionId, otherQuestionAnswer: otherQuestionAnswer])
                        }
                    }
                }

            }
        }
        render([answers: answers, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getResourcesForConstruction() {
        String constructionId = params.constructionId
        String copiedDesignId = params.copiedDesignId
        String copiedUniqueConstructionIdentifier = params.copiedUniqueConstructionIdentifier
        List<Document> datasets = []

        if (constructionId) {
            List<Document> ds

            // Need to get the datasets from entity if its a dummy construction
            if ("dummyGroupResource".equals(constructionId)) {
                if (copiedDesignId && copiedUniqueConstructionIdentifier) {
                    ds = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(copiedDesignId)], [datasets: 1])?.datasets?.findAll({it.uniqueConstructionIdentifier == copiedUniqueConstructionIdentifier && it.parentConstructionId})
                }
            } else {
                ds = constructionService.getConstructionAsDocument(constructionId, [datasets: 1])?.datasets
            }

            if (ds) {
                if (ds.find({ it.defaultConstituent })) {
                    datasets = ds.findAll({ it.defaultConstituent })
                } else {
                    datasets = ds
                }
            }
        }
        render([datasetsAsJson: datasets, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def changeImportMapperDatasetClass() {
        ImportMapper importMapper = session?.getAttribute("importMapper")
        List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
        String chosenClass = params.chosenClass
        String datasetManualId = params.datasetManualId
        String parentEntityId = params.parentEntityId

        if (importMapper && ifcDatasets && chosenClass && datasetManualId) {
            Entity parentEntity = entityService.getEntityByIdReadOnly(parentEntityId)
            Dataset dataset = ifcDatasets.find({ datasetManualId.equals(it.manualId) })

            if (dataset) {
                importMapperService.changeDatasetClass(dataset, importMapper, chosenClass, parentEntity?.entityClass)
            }
        }
        return true
    }

    def renderCombinedDisplayFields() {
        String datasetId = params.datasetId
        Boolean dontShowRecognizedFrom = params.boolean("dontShowRecognizedFrom")
        String content = ''

        if (datasetId) {
            Dataset dataset

            if (params.importMapper) {
                List<Dataset> ifcDatasets = session?.getAttribute("ifcDatasets")
                dataset = ifcDatasets?.find({ it.manualId?.equals(datasetId) })
            } else {
                String entityId = params.entityId
                String queryId = params.queryId
                String sectionId = params.sectionId
                String questionId = params.questionId

                if (entityId && queryId && sectionId && questionId) {
                    Entity entity = entityService.getEntityById(entityId)

                    if (entity) {
                        List<Dataset> datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId,
                                sectionId, questionId)
                        if (datasets) {
                            dataset = datasets.find{ it.manualId.equals(datasetId) }
                        }
                    }
                }
            }

            if (dataset && dataset.datasetImportFieldsId) {
                DatasetImportFields datasetImportFields = datasetService.getDatasetImportFieldsById(dataset.datasetImportFieldsId)

                if (datasetImportFields) {
                    List<String> headers = []
                    datasetImportFields.importFields?.each { Map<String, String> displayFields ->
                        headers.addAll(displayFields.keySet()?.toList())
                    }
                    headers = headers.unique()

                    if (dontShowRecognizedFrom && headers) {
                        headers.remove(0)
                    }

                    content = ""
                    content = content + "<table><thead>"
                    if (datasetImportFields.limitExceeded) {
                        content = content + "<tr>" +
                                "${message(code: "import_field.limit_exceeded", args: [datasetImportFields.IMPORT_FIELD_LIMIT])}" +
                                "</tr>"
                    }
                    content = content + "<tr>"
                    headers.each { String header ->
                        content = content + "<th>${header}</th>"
                    }
                    content = content + "</tr></thead><tbody>"

                    datasetImportFields.importFields?.each { Map<String, String> displayFields ->
                        content = content + "<tr>"
                        headers.each { String header ->
                            String value = displayFields.get(header) ? HtmlUtils.htmlEscape(displayFields.get(header)) : ""
                            content = content + "<td>${value}</td>"
                        }
                        content = content + "</tr>"
                    }
                    content = content + "</tbody></table>"
                }
            }
        }
        render([
                output                           : content ?: "${message(code: 'resource.sourcelisting_no_information_available')}",
                (flashService.FLASH_OBJ_FOR_AJAX): flash
        ] as JSON)
    }

    def renderDatasetInformation() {
        String entityId = params.childEntityId
        String datasetManualId = params.datasetManualId
        String constructionId = params.constructionId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String renderContent = "<table class=\'resource\'><tbody>"

        if ((entityId || constructionId) && datasetManualId) {
            List<Dataset> datasets = []

            if (entityId && queryId && sectionId && questionId) {
                Entity entity = entityService.getEntityById(entityId)

                if (entity) {
                    datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId, sectionId, questionId)
                }

            }
            if (constructionId) {
                Construction construction = constructionService.getConstruction(constructionId)
                datasets = construction?.datasets
            }

            if (datasets) {

                Dataset dataset = datasets.find({ it.manualId.equals(datasetManualId) })

                if (dataset) {
                    dataset?.properties?.each {
                        renderContent = "${renderContent} <tr ${it.value == null ? 'data-nullvalue=\"true\"' : ''}><td class=\'bold\'>${it.key}</td> <td>${it.value}</td></tr>"
                    }
                }
            }

        }
        renderContent = "${renderContent} </tbody></table>"
        if (params.boolean('ajaxCall')) {
            String templateAsString = g.render(template: "/util/datasetInformation", model: [renderContent: renderContent]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render template: "datasetInformation", model: [renderContent: renderContent]
        }
    }

    def splitOrChangeDataset() {
        String childEntityId = params.childEntityId
        String parentEntityId = params.parentEntityId
        String datasetManualId = params.datasetManualId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String mainSectionId = params.mainSectionId
        String originalResourceTableId = params.originalResourceTableId
        Boolean showGWP = params.boolean("showGWP")
        Boolean preventChanges = params.boolean("preventChanges")
        Boolean splitView = params.boolean("splitView")
        Boolean changeView = params.boolean("changeView")
        Map model = [:]

        if (childEntityId && datasetManualId && queryId && indicatorId && sectionId) {
            Query query = queryService.getQueryByQueryId(queryId)
            Entity parentEntity = entityService.getEntityById(parentEntityId)
            Entity childEntity = entityService.getEntityById(childEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Question question = questionService.getQuestion(queryId, questionId)
            Dataset dataset

            if (query && childEntity && indicator && question) {
                QuerySection section = query.getSections().find({ it.sectionId.equals(sectionId) })
                String resourceTableId = ""
                List<QueryFilter> supportedFilters = query.supportedFilters
                QueryFilter resourceTypeFilter = supportedFilters?.find({ it.resourceAttribute.equals("resourceType") })
                User user = userService.getCurrentUser()
                List<String> deletedDatasetsToSession = session?.getAttribute("deletedDatasets") ?: []
                List<Dataset> datasets = datasetService.getDatasetsByEntityAndQueryIdAsDataset(childEntity, queryId)

                if (deletedDatasetsToSession) {
                    datasets.removeAll({ deletedDatasetsToSession.contains(it.manualId) })
                }
                ResourceCache resourceCache = null
                if (datasets) {
                    resourceCache = ResourceCache.init(datasets)
                    dataset = datasets.find({ datasetManualId.equalsIgnoreCase(it.manualId) })
                    if (dataset) {
                        resourceTableId = dataset.manualId + dataset.resourceId
                        datasets.removeAll({ it.manualId.equalsIgnoreCase(datasetManualId) })
                    }

                }
                String fieldName

                if (sectionId && questionId) {
                    fieldName = sectionId + "." + questionId;
                }
                List<Question> additionalQuestions = question?.getAdditionalQuestions(indicator, null, null, parentEntity)?.sort()
                String datasetFilterForSplit

                if (resourceTypeFilter) {
                    datasetFilterForSplit = resourceCache?.getResource(dataset)?.resourceType

                    if (datasetFilterForSplit) {
                        user.userSplitFilterChoices = ["resourceType": datasetFilterForSplit]
                    } else {
                        user.userSplitFilterChoices = [:]
                    }
                } else {
                    user.userSplitFilterChoices = [:]
                }
                user = userService.updateUser(user)
                Map<String, List<Document>> additionalQuestionResources = [:]

                if (additionalQuestions) {
                    for (Question additionalQuestion in additionalQuestions) {
                        List<Document> additionalQuestionResourcesList = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)

                        if (additionalQuestionResourcesList) {
                            additionalQuestionResources.put(additionalQuestion.questionId, additionalQuestionResourcesList)
                        }
                    }
                }

                List<Dataset> changeableSimilarDatasets = []
                if (changeView && dataset && datasets) {
                    datasets.each { Dataset d ->
                        if (!d.locked && !d.uniqueConstructionIdentifier && !d.parentConstructionId && d.userGivenUnit == dataset.userGivenUnit && d.resourceId == dataset.resourceId) {
                            changeableSimilarDatasets.add(d)
                        }
                    }
                }
                Account account = userService.getAccount(user)

                if (dataset) {
                    model = [splitView                  : splitView,
                             changeView                 : changeView,
                             originalResourceTableId    : originalResourceTableId,
                             mainSectionId              : mainSectionId,
                             section                    : section,
                             quantity                   : dataset?.quantity,
                             question                   : question,
                             additionalQuestions        : additionalQuestions,
                             showGWP                    : showGWP,
                             preventChanges             : preventChanges,
                             parentEntity               : parentEntity,
                             entity                     : childEntity,
                             resourceTableId            : resourceTableId,
                             indicator                  : indicator,
                             fieldName                  : fieldName,
                             parentEntityId             : parentEntityId,
                             childEntityId              : childEntityId,
                             indicatorId                : indicatorId,
                             supportedFilters           : supportedFilters,
                             resourceTypeFilter         : resourceTypeFilter,
                             query                      : query,
                             user                       : user,
                             dataset                    : dataset,
                             resource                   : resourceCache?.getResource(dataset),
                             datasets                   : datasets,
                             additionalQuestionResources: additionalQuestionResources,
                             changeableSimilarDatasets  : changeableSimilarDatasets,
                             account                    : account
                    ]
                } else {
                    log.error("SPLIT OR CHANGE ERROR: No dataset found with manualId: ${datasetManualId}, Project: ${parentEntity?.name}, Design: ${childEntity?.operatingPeriodAndName}")
                    flashService.setErrorAlert("SPLIT OR CHANGE ERROR: No dataset found with manualId: ${datasetManualId}, Project: ${parentEntity?.name}, Design: ${childEntity?.operatingPeriodAndName}", true)
                }
            } else {
                log.error("SPLIT OR CHANGE ERROR: Unable to get query or question with: ${queryId}, ${questionId}. Or unable to get childEntity with: ${childEntityId}. Or unable to get indicator with: ${indicatorId}")
                flashService.setErrorAlert("SPLIT OR CHANGE ERROR: Unable to get query or question with: ${queryId}, ${questionId}. Or unable to get childEntity with: ${childEntityId}. Or unable to get indicator with: ${indicatorId}", true)
            }

        } else {
            log.error("SPLIT OR CHANGE ERROR: Unable to render split or changes due missing parameters, given parameters are: ${params}")
            flashService.setErrorAlert("SPLIT OR CHANGE ERROR: Unable to render split or changes due missing parameters, given parameters are: ${params}", true)
        }
        render([output: g.render(template: "/util/splitOrChangeDataset", model: model).toString(), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def splitOrChangeDatasetForm() {
        String originalDatasetId = params.originalDatasetId
        String entityId = params.entityId
        String childEntityId = params.childEntityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        Boolean changeView = params.boolean("changeView")
        Boolean splitView = params.boolean("splitView")
        Boolean preserverQuestionAnswers = params.boolean("preserveConstructionInfo")
        List<String> changeSimilarDatasets = params.list('changeSimilarDataset')

        if (originalDatasetId && entityId && childEntityId && indicatorId && queryId && sectionId && questionId) {
            Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())
            def additionalQuestionQueryId = additionalQuestionQueryConfig?.value
            Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryId, true)
            Question specialRuleIdQuestion = questionService.getQuestion(additionalQuestionsQuery, "specialRuleId")
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity childEntity = entityService.getEntityById(childEntityId)
            Entity parentEntity = childEntity?.parentById

            if (childEntity && parentEntity && indicator) {
                Boolean useInches = indicator.useThicknessInInches
                def splittedOrChangedResourceIds = params.findAll({ it.key.toString().startsWith("splittedOrChangedResourceId" )})
                def splittedOrChangedQuantitys = params.findAll({ it.key.toString().startsWith("splittedOrChangedQuantity" )})
                def splittedOrChangedUnits = params.findAll({ it.key.toString().startsWith("splittedOrChangedUnit" )})
                def splittedOrChangedThicknesses = params.findAll({ it.key.toString().startsWith("splittedOrChangedThickness" )})
                List<Dataset> datasets = childEntity.datasets?.toList()
                Dataset splittedOrChangedDataset = datasets.find({ it.manualId == originalDatasetId })
                Resource originalResource = datasetService.getResource(splittedOrChangedDataset)

                if (originalResource?.construction && splittedOrChangedDataset.uniqueConstructionIdentifier) {
                    // Remove constituents if user is changed a construction
                    datasets.removeIf({it.uniqueConstructionIdentifier == splittedOrChangedDataset.uniqueConstructionIdentifier && it.manualId != splittedOrChangedDataset.manualId})
                }

                if (changeView) { // CHANGE VIEW HANDLING
                    String newResourceId = splittedOrChangedResourceIds?.values()?.isEmpty() ? null : splittedOrChangedResourceIds.values().first()
                    String newQuantity = splittedOrChangedQuantitys?.values()?.isEmpty() ? null : splittedOrChangedQuantitys.values().first()
                    String newUnit = splittedOrChangedUnits?.values()?.isEmpty() ? null : splittedOrChangedUnits.values().first()
                    String newThickness = splittedOrChangedThicknesses?.values()?.isEmpty() ? null : splittedOrChangedThicknesses.values().first()

                    if (newResourceId && newQuantity && newUnit) {
                        Document resource = optimiResourceService.getResourceAsDocument(newResourceId, null, [construction: 1, constructionId: 1, resourceSubType: 1, areas: 1])

                        if (resource) {
                            List<String> areas = (List<String>) resource.areas
                            Double newQuantityDouble = DomainObjectUtil.convertStringToDouble(newQuantity)
                            splittedOrChangedDataset.resourceId = newResourceId
                            splittedOrChangedDataset.profileId = null

                            if (splittedOrChangedDataset.additionalQuestionAnswers) {
                                splittedOrChangedDataset.additionalQuestionAnswers.remove("profileId")

                                if (newThickness) {
                                    if (useInches) {
                                        splittedOrChangedDataset.additionalQuestionAnswers.put("thickness_in", newThickness)
                                        splittedOrChangedDataset.additionalQuestionAnswers.remove("thickness_mm")
                                    } else {
                                        splittedOrChangedDataset.additionalQuestionAnswers.put("thickness_mm", newThickness)
                                        splittedOrChangedDataset.additionalQuestionAnswers.remove("thickness_in")
                                    }
                                }

                                if (originalResource && resourceService.getIsLocalResource(originalResource.areas) && (!areas||(areas && !com.bionova.optimi.core.Constants.RESOURCE_LOCAL_AREA.equals(areas.first())))) {
                                    splittedOrChangedDataset.additionalQuestionAnswers.remove(com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID)
                                }
                            }
                            splittedOrChangedDataset.quantity = newQuantityDouble
                            splittedOrChangedDataset.answerIds = ["${newQuantityDouble}"]
                            splittedOrChangedDataset.userGivenUnit = newUnit
                            splittedOrChangedDataset.originalAnswer = null

                            Construction c

                            if (resource.construction && resource.constructionId) {
                                c = constructionService.getConstruction(resource.constructionId)

                                if (c) {
                                    splittedOrChangedDataset.uniqueConstructionIdentifier = UUID.randomUUID().toString() + c.id.toString()
                                    List<Dataset> datasetsForConstruction = constructionService.getFormattedConstructionDatasets(c, splittedOrChangedDataset, indicator)

                                    if (datasetsForConstruction) {
                                        datasetsForConstruction.each { Dataset constituent ->
                                            if (constituent.additionalQuestionAnswers && splittedOrChangedDataset.additionalQuestionAnswers) {
                                                splittedOrChangedDataset.additionalQuestionAnswers.remove("specialRuleId")
                                                constituent.additionalQuestionAnswers.putAll(splittedOrChangedDataset.additionalQuestionAnswers)
                                                datasets.add(constituent)
                                            }
                                        }
                                    }
                                    if(!preserverQuestionAnswers) {
                                        log.info("new construction answers used")
                                        splittedOrChangedDataset.additionalQuestionAnswers = c.additionalQuestionAnswers
                                    }
                                }
                            }

                            if (splittedOrChangedDataset.additionalQuestionAnswers?.get("specialRuleId")) {
                                if (specialRuleIdQuestion) {
                                    def choise = specialRuleIdQuestion.choices.find({ it.answerId == splittedOrChangedDataset.additionalQuestionAnswers.get("specialRuleId").toString()})

                                    if (!choise?.allowedResourceSubTypes?.contains(resource.resourceSubType)) {
                                        splittedOrChangedDataset.additionalQuestionAnswers["specialRuleId"] = "0"

                                        if (splittedOrChangedDataset.connectedDatasetManualId) {
                                            datasets.removeIf({it.manualId == splittedOrChangedDataset.connectedDatasetManualId})
                                        }
                                    }
                                }
                            }

                            if (changeSimilarDatasets) {
                                changeSimilarDatasets.each { String manualId ->
                                    Dataset d = datasets.find({ it.manualId == manualId })

                                    if (d) {
                                        Resource similarResource = datasetService.getResource(d)

                                        if (similarResource?.construction && d.uniqueConstructionIdentifier) {
                                            // Remove constituents if user is changed a construction
                                            datasets.removeIf({it.uniqueConstructionIdentifier == d.uniqueConstructionIdentifier && it.manualId != d.manualId})
                                        }

                                        d.resourceId = newResourceId
                                        d.profileId = null

                                        if (d.additionalQuestionAnswers) {
                                            d.additionalQuestionAnswers.remove("profileId")
                                        }

                                        if (c) {
                                            d.uniqueConstructionIdentifier = UUID.randomUUID().toString() + c.id.toString()

                                            List<Dataset> datasetsForConstruction = constructionService.getFormattedConstructionDatasets(c, d, indicator)

                                            if (datasetsForConstruction) {
                                                datasetsForConstruction.each { Dataset constituent ->
                                                    if (constituent.additionalQuestionAnswers && d.additionalQuestionAnswers) {
                                                        d.additionalQuestionAnswers.remove("specialRuleId")
                                                        constituent.additionalQuestionAnswers.putAll(d.additionalQuestionAnswers)
                                                        datasets.add(constituent)
                                                    }
                                                }
                                            }
                                            d.additionalQuestionAnswers = c.additionalQuestionAnswers
                                        }

                                        if (d.additionalQuestionAnswers?.get("specialRuleId")) {
                                            if (specialRuleIdQuestion) {
                                                def choise = specialRuleIdQuestion.choices.find({ it.answerId == d.additionalQuestionAnswers.get("specialRuleId").toString()})

                                                if (!choise?.allowedResourceSubTypes?.contains(resource.resourceSubType)) {
                                                    d.additionalQuestionAnswers["specialRuleId"] = "0"

                                                    if (d.connectedDatasetManualId) {
                                                        datasets.removeIf({it.manualId == d.connectedDatasetManualId})
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            log.error("SPLIT OR CHANGE ERROR: No resource found with resourceId: ${newResourceId}")
                            flashService.setErrorAlert("SPLIT OR CHANGE ERROR: No resource found with resourceId: ${newResourceId}", true)
                        }
                    }
                } else if (splitView) { // SPLIT VIEW HANDLING
                    List<Dataset> splittedDatasets = []

                    if (splittedOrChangedResourceIds) {
                        splittedOrChangedResourceIds.each { key, resourceId ->
                            String resource = org.apache.commons.lang.StringUtils.substringAfter(key.toString(), "splittedOrChangedResourceId")
                            String quantityGetter = "splittedOrChangedQuantity${resource}"
                            String unitGetter = "splittedOrChangedUnit${resource}"
                            String thicknessGetter = "splittedOrChangedThickness${resource}"
                            Boolean isArray = resourceId.getClass().isArray()

                            List<String> resourceIds

                            // resourceId can be an String[] if user has splitted to multiple of the same resourceId
                            if (isArray) {
                                resourceIds = resourceId.toList()
                            } else {
                                resourceIds = [resourceId.toString()]
                            }

                            int i = 0

                            resourceIds.each { String id ->
                                Document r = optimiResourceService.getResourceAsDocument(id, null, [construction: 1, constructionId: 1])
                                String quantity
                                String unit
                                String thickness

                                if (isArray) {
                                    quantity = splittedOrChangedQuantitys.get(quantityGetter)?.getAt(i)
                                    unit = splittedOrChangedUnits.get(unitGetter)?.getAt(i)
                                    thickness = splittedOrChangedThicknesses.get(thicknessGetter)?.getAt(i)
                                } else {
                                    quantity = splittedOrChangedQuantitys.get(quantityGetter)
                                    unit = splittedOrChangedUnits.get(unitGetter)
                                    thickness = splittedOrChangedThicknesses.get(thicknessGetter)
                                }

                                Double quantityDouble = DomainObjectUtil.convertStringToDouble(quantity)

                                Dataset d = new Dataset()
                                d.manualId = new ObjectId().toString()
                                d.resourceId = id
                                d.quantity = quantityDouble
                                d.answerIds = ["${quantityDouble}"]
                                d.userGivenUnit = unit
                                d.queryId = splittedOrChangedDataset.queryId
                                d.sectionId = splittedOrChangedDataset.sectionId
                                d.questionId = splittedOrChangedDataset.questionId

                                if (id.equals(splittedOrChangedDataset.resourceId)) {
                                    // Persist user set cost values in the origian dataset and resources with same resourceId
                                    d.userSetCost = splittedOrChangedDataset.userSetCost
                                    d.userSetTotalCost = splittedOrChangedDataset.userSetTotalCost
                                }
                                d.additionalQuestionAnswers = [:]

                                Construction c

                                if (r?.construction && r?.constructionId) {
                                    c = constructionService.getConstruction(r?.constructionId)

                                    if (c) {
                                        d.uniqueConstructionIdentifier = UUID.randomUUID().toString() + c.id.toString()
                                        List<Dataset> datasetsForConstruction = constructionService.getFormattedConstructionDatasets(c, d, indicator)

                                        if (datasetsForConstruction) {
                                            splittedDatasets.addAll(datasetsForConstruction)
                                        }
                                    }
                                } else {
                                    d.additionalQuestionAnswers.putAll(splittedOrChangedDataset.additionalQuestionAnswers)
                                }

                                if (thickness) {
                                    if (useInches) {
                                        if (d.additionalQuestionAnswers) {
                                            d.additionalQuestionAnswers.put("thickness_in", thickness)
                                            d.additionalQuestionAnswers.remove("thickness_mm")
                                        } else {
                                            d.additionalQuestionAnswers = [thickness_in: thickness]
                                        }
                                    } else {
                                        if (d.additionalQuestionAnswers) {
                                            d.additionalQuestionAnswers.put("thickness_mm", thickness)
                                            d.additionalQuestionAnswers.remove("thickness_in")
                                        } else {
                                            d.additionalQuestionAnswers = [thickness_mm: thickness]
                                        }
                                    }
                                }
                                if (d.additionalQuestionAnswers?.get("specialRuleId")) {
                                    if (specialRuleIdQuestion) {
                                        def choise = specialRuleIdQuestion.choices.find({ it.answerId == d.additionalQuestionAnswers.get("specialRuleId").toString()})

                                        if (!choise?.allowedResourceSubTypes?.contains(r.resourceSubType)) {
                                            d.additionalQuestionAnswers["specialRuleId"] = "0"
                                        }
                                    }
                                }
                                splittedDatasets.add(d)
                                i++
                            }
                        }
                    }

                    if (splittedOrChangedDataset.connectedDatasetManualId) {
                        datasets.removeIf({it.manualId == splittedOrChangedDataset.connectedDatasetManualId})
                    }
                    // Normal remove did not work since dataset compareTo is fucked and checks for seqNo? todo: fix seqNo of copied dataset or compareTo
                    datasets.removeIf({it.manualId == splittedOrChangedDataset.manualId})
                    datasets.addAll(splittedDatasets)
                }
                List<Indicator> indicatorsToSave = indicatorService.getIndicatorsWithSameQuery(parentEntity, queryId)

                childEntity = datasetService.preHandleDatasets(childEntity, datasets, indicatorsToSave,
                        Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)
                childEntity = childEntity.merge(flush: true)
                calculationProcessService.createCalculationProcessAndSave(childEntity.id?.toString(),
                        childEntity?.parentEntityId?.toString(), indicator.indicatorId)
                entityService.calculateEntity(childEntity, indicator.indicatorId, queryId, false, false)
            }
            session?.removeAttribute("deletedDatasets")
        }
        redirect controller: "query", action: "form", params: [indicatorId: indicatorId, queryId: queryId, entityId: entityId, childEntityId: childEntityId]
    }

    def splitDataset() {
        String originalDatasetId = params.originalDatasetId
        Boolean drawOriginalDataset = params.boolean('drawOriginalDataset')
        String entityId = params.parentEntityId
        String childEntityId = params.childEntityId
        String indicatorId = params.indicatorId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        Boolean dynamicAdd = params.boolean('dynamicAdd')

        if (entityId && childEntityId && indicatorId && queryId && sectionId && questionId) {
            Dataset originalDataset
            Query query = queryService.getQueryByQueryId(queryId)
            Entity parentEntity = entityService.getEntityById(entityId)
            Entity childEntity = entityService.getEntityById(childEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            Question question = questionService.getQuestion(queryId, questionId)
            List<Question> additionalQuestions = []

            if (query && childEntity && indicator && question) {
                List<Dataset> datasets = []
                datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, queryId, sectionId, questionId)
                ResourceCache resourceCache = ResourceCache.init(datasets)

                if (datasets && originalDatasetId) {
                    originalDataset = datasets.find({ it.manualId.equals(originalDatasetId) })
                }
                QuerySection section = query.getSections().find({ it.sectionId.equals(sectionId) })


                String fieldName
                if (sectionId && questionId) {
                    fieldName = sectionId + "." + questionId;
                }
                additionalQuestions = question.defineAdditionalQuestions(indicator, section, Boolean.TRUE, parentEntity)?.sort()
                String templateAsString = g.render(template: "/util/splitTable", model: [drawOriginalDataset: drawOriginalDataset,
                                                                                         originalDataset    : originalDataset,
                                                                                         resource           : resourceCache.getResource(originalDataset),
                                                                                         entity             : childEntity,
                                                                                         fieldName          : fieldName,
                                                                                         quantity           : originalDataset?.quantity,
                                                                                         additionalQuestions: additionalQuestions,
                                                                                         indicator          : indicator,
                                                                                         showGWP            : Boolean.TRUE,
                                                                                         parentEntity       : parentEntity,
                                                                                         query              : query,
                                                                                         sectionId          : sectionId, question: question,
                                                                                         dynamicAdd         : dynamicAdd,
                                                                                         accountId          : params.accountId])
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }

        }
    }

    def indicatorBenchmarks() {
        String childEntityId = params.childEntityId
        String indicatorId = params.indicatorId
        String carbonHeroIdFallback = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "benchmarkingIndicatorId").value
        String parentEntityType = "undefined".equals(params.parentEntityType) || !params.parentEntityType ? null : params.parentEntityType
        String parentCountryResourceId = params.countryForPrioritization
        String benchmarkId = params.benchmarkId
        boolean forMainEntity = params.mainEntity ? true : false
        List<IndicatorBenchmark> allBenchmarks
        IndicatorBenchmark indicatorBenchmark
        Double score
        String indicatorName
        String denominatorName
        String designName
        String parentName
        String missingDataWarning
        String templateToRender = "indicatorBenchmark"
        Double planetaryBenchmarkMargin = 1.0
        Indicator indicator
        Boolean isIndicatorBenchmarkLocked
        String heading = message(code:"heading_benchmark.table")
        if (childEntityId && indicatorId) {
            Entity design = entityService.getEntityByIdReadOnly(childEntityId)
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            BenchmarkSettings benchmarkSettings = indicator.benchmarkSettings
            if(!benchmarkSettings){
                indicator = indicatorService.getIndicatorByIndicatorId(carbonHeroIdFallback)
            }
            if(benchmarkSettings?.tableHeading){
                heading = message(code:benchmarkSettings.tableHeading)
            }
            if (design && indicator) {
                planetaryBenchmarkMargin = indicator?.benchmarkSettings?.planetaryBenchmarkMargin ? (planetaryBenchmarkMargin - indicator?.benchmarkSettings?.planetaryBenchmarkMargin) : planetaryBenchmarkMargin
                indicatorName = indicatorService.getLocalizedName(indicator)
                designName = design.operatingPeriodAndName
                parentName = design.parentById?.name
                Entity parent = design.parentById
                isIndicatorBenchmarkLocked = parent.isFeatureLicensed(Feature.INDICATOR_BENCHMARKS_LOCKED)
                String projectBuildingType = parent?.typeResourceId?.toString()
                allBenchmarks = indicatorBenchmarkService.getIndicatorBenchmarkEmbodiedAndParentIds(projectBuildingType)

                if (allBenchmarks && indicator.isBenchmarkable(projectBuildingType)) {
                    if (benchmarkId && !"undefined".equals(benchmarkId)) {
                        indicatorBenchmark = allBenchmarks.find({ benchmarkId.equals(it.benchmarkId) })
                    } else {
                        indicatorBenchmark = indicatorBenchmarkService.getBenchmarkForProjectCountryAndType(allBenchmarks, parentCountryResourceId, projectBuildingType)
                    }
                    score = design.getDisplayResult(indicator.indicatorId, benchmarkSettings?.displayRuleId)
                }

            }
        }
        if (score != null) {
            score = Math.round(score)
        } else if (!missingDataWarning) {
            missingDataWarning = "${message(code: 'benchmark_no_result')}"
        }

        if(forMainEntity){
            templateToRender = "benchmarkGraph2"
        }
        String templateAsString = g.render(template: "/util/$templateToRender", model: [indicatorBenchmark        : indicatorBenchmark, allBenchmarks: allBenchmarks, benchmarkId: indicatorBenchmark?.benchmarkId,
                                                                                        score                     : score, indicatorName: indicatorName, denominatorName: denominatorName,
                                                                                        childEntityId             : childEntityId, indicatorId: indicatorId, parentEntityType: parentEntityType,
                                                                                        countryForPrioritization  : parentCountryResourceId, designName: designName, parentName: parentName, heading: heading,
                                                                                        missingDataWarning        : missingDataWarning, planetaryBenchmarkMargin: planetaryBenchmarkMargin, indicator: indicator,
                                                                                        isIndicatorBenchmarkLocked: isIndicatorBenchmarkLocked]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def clearSplitFilter() {
        User user = userService.getCurrentUser()
        if (user) {
            user.userSplitFilterChoices = [:]
            userService.updateUser(user)
        }
        render([output: "cleared split filter", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    // Ajax call from Edit Button CarbonDashboard.gsp
    def earlyPhaseToolDatasets() {
        //???ADD CHECK SESSION EXPIRED???

        String jsonConstruction = request.JSON?.construction


        Map outputmap = [:]
        String templateAsString

        if (!jsonConstruction) {
            templateAsString = g.render(text: "Error Json")
            outputmap.put("template", templateAsString ?: "")
            return render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

        SimulationToolConstruction simConstruction = new JsonSlurper().parseText(jsonConstruction)

        if (!simConstruction) {
            render(text: jsonConstruction)
        }

        String sessionId = request.JSON?.sessionId
        SimulationToolSession simSession = session?.getAttribute(sessionId)

        User user = userService.getCurrentUser(true)
        String unitSystem = user?.unitSystem
        //If unitSystem null throw some exception

        String commentHeading = session?.getAttribute("commentHeading") ?: ""

        try {
            templateAsString = g.render(template: "expandedDatasets", model: [simConstruction     : simConstruction,
                                                                              entityId            : simSession?.entityId,
                                                                              indicatorId         : simSession?.indicatorId,
                                                                              sessionId           : sessionId,
                                                                              unitSystem          : unitSystem,
                                                                              commentHeading: commentHeading,
                                                                              privateConstruction: simConstruction.isCreatedFromPrivateConstruction,
                                                                              dynamicDefault: simConstruction.dynamicDefault]).toString()
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw)
            log.info("TEMPLATE EXCEPTION: ${sw.toString()}")
        }

        outputmap.put("template", templateAsString ?: "")
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

    }

    //This method invoked from ajax call when update button is pushed or the share of construction is modified
    def updateConstructionImpact() {

        //???ADD CHECK SESSION EXPIRED???
        String sessionId = request.JSON?.sessionId
        String jsonConstruction = request.JSON?.jsonConstruction

        SimulationToolSession simulationSession = session?.getAttribute(sessionId)
        String unitSystem = simulationSession?.unitSystem

        Entity tempEntity = simulationSession?.tempEntity
        Entity parentEntity = entityService.getEntityById(simulationSession?.entityId)
        Entity design = entityService.getEntityById(simulationSession?.designId)
        Indicator indicator = simulationSession?.indicator

        if (jsonConstruction && tempEntity && parentEntity && indicator) {
            SimulationToolConstruction simConstruction = JSON.parse(jsonConstruction) as SimulationToolConstruction

            if(simConstruction){
                def resultCalc = simulationToolService.calculationConstructionImpact(parentEntity, tempEntity, indicator, simConstruction, unitSystem, design, simulationSession.assessmentPeriod)
                if (resultCalc) {
                    simConstruction = resultCalc.simConstruction
                    simulationSession?.tempEntity = resultCalc.temEntity
                }
            }
            session?.setAttribute(sessionId, simulationSession)
            render([simConstruction: simConstruction, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            log.error("CD updateConstructionImpact --> STATUS  503: JSON: ${request.JSON}")
            render(status: 503, text: 'fail')
        }

    }

    //This method invoked from ajax call when thickness is modified
    def simulateConstructionImpact() {

        //???ADD CHECK SESSION EXPIRED???
        String sessionId = request.JSON?.sessionId
        String jsonConstruction = request.JSON?.jsonConstruction

        SimulationToolSession simulationSession = session?.getAttribute(sessionId)
        String unitSystem = simulationSession?.unitSystem

        Entity tempEntity = simulationSession?.tempEntity
        Entity parentEntity = entityService.getEntityById(simulationSession?.entityId)
        Indicator indicator = simulationSession?.indicator

        if (jsonConstruction && tempEntity && parentEntity && indicator) {
            SimulationToolConstruction simConstruction = JSON.parse(jsonConstruction) as SimulationToolConstruction

            def resultCalc = simulationToolService.simulateConstructionImpact(parentEntity, tempEntity, indicator, simConstruction, unitSystem, simulationSession.assessmentPeriod)
            if (resultCalc) {
                simConstruction = resultCalc.simConstruction
            }

            render([simConstruction: simConstruction, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

        }

    }

    //Calculate Energy Impact
    def simulationToolEnergyImpact() {
        //???ADD CHECK SESSION EXPIRED???
        try{
            String sessionId = request.JSON?.sessionId
            SimulationToolSession simulationSession = session?.getAttribute(sessionId)
            Entity design = entityService.getEntityById(simulationSession?.designId)
            Double co2e
            String manualId

            if (simulationSession) {
                Entity tempEntity = simulationSession?.tempEntity
                Indicator indicator = simulationSession?.indicator

                String jsonEnergyType = request.JSON?.jsonEnergyType

                if (jsonEnergyType) {
                    SimulationToolEnergyType energyType = JSON.parse(jsonEnergyType) as SimulationToolEnergyType

                    Query query = queryService.getQueryByQueryId(simulationToolService.QUERY_ID, true)
                    Map target = simulationToolService.getTargetByResourceId(query, energyType?.defaultOption)
                    if (target) {
                        def result = simulationToolService.energyResourceImpact(energyType, indicator, tempEntity, target, design, null, null, simulationSession.assessmentPeriod)
                        if (result) {
                            co2e = result.co2e
                            manualId = result.manualId
                            if (result.tempEntity) {
                                simulationSession?.tempEntity = result.tempEntity
                                session?.setAttribute(sessionId, simulationSession)
                            }
                        }
                    }
                }
            }

            render([manualId: manualId, co2e: co2e, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

        }catch(Exception e){
            log.error("CD Energy update: $e")
            render([error: "Exception", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

        }
    }

    def earlyPhaseToolSaveDraft() {
        //Check if the simulationtool obtained by the id has the same designid and entityid otherwise send a error message

        String sessionId = request.JSON?.sessionId
        String jsonSimModel = request.JSON?.jsonSimModel
        String jsonEnergyTypeList = request.JSON?.jsonEnergyTypeList
        Boolean saveToQuery = request.JSON?.saveToQuery
        String lccIndicatorId = request.JSON?.lccIndicatorId

        if (lccIndicatorId && lccIndicatorId == "null") {lccIndicatorId = null}

        //???ADD CHECK SESSION EXPIRED???

        SimulationToolSession simulationSession = session?.getAttribute(sessionId)

        String simulationToolId = simulationSession?.simulationToolId
        String entityId = simulationSession?.entityId
        String designId = simulationSession?.designId

        User user = userService.getCurrentUser(true)

        def output = [:]

        //????CHECK HERE THE VALIDITY OF ALL THE ELEMeT OF THE SIMULATION
        if (jsonSimModel && user && simulationSession) {

            List<SimulationToolConstructionGroup> simConstructionGroups = []

            def groups = JSON.parse(jsonSimModel)
            groups?.each {
                simConstructionGroups.add(it as SimulationToolConstructionGroup)
            }

            List<SimulationToolEnergyType> energyTypeList = []
            if (jsonEnergyTypeList) {

                def list = JSON.parse(jsonEnergyTypeList)
                list?.each {
                    energyTypeList.add(it as SimulationToolEnergyType)
                }
            }

            if (simConstructionGroups) {

                SimulationTool simulationTool = simulationToolService.findSimulationToolDraft(entityId, designId)

                if (simulationTool) {

                    //?????Check consistence of session attribute and simulation tool draft from db??????
                    simulationTool.simulationToolConstructionGroups = simConstructionGroups
                    simulationTool.energyTypeList = energyTypeList
                    simulationTool.lccIndicatorId = lccIndicatorId
                    simulationTool.assessmentPeriod = simulationSession.assessmentPeriod
                } else {

                    simulationTool = simulationSession?.getSimulationTool()
                    simulationTool?.simulationToolConstructionGroups = simConstructionGroups
                    simulationTool?.energyTypeList = energyTypeList
                    simulationTool?.userId = user.id.toString()
                    simulationTool?.lccIndicatorId = lccIndicatorId
                    simulationTool?.assessmentPeriod = simulationSession.assessmentPeriod
                }

                if (saveSimulationTool(simulationTool)) {
                    output = [output: g.message(code: "simulationTool.messageSaveDesign")]
                } else {
                    output = [output: g.message(code: "simulationTool.messageNoSaveDesign")]
                }
            } else {
                output = [output: g.message(code: "simulationTool.messageNoSaveDesign")]
            }
        } else {
            output = [output: g.message(code: "simulationTool.messageNoSaveDesign")]
        }
        if (flash) {
            output.put(flashService.FLASH_OBJ_FOR_AJAX, flash) //hung check here
        }
        render(output as JSON)
    }

    Boolean saveSimulationTool(SimulationTool simulationTool){

        if (simulationTool.validate()) {
            if (simulationTool.id) {
                simulationTool.merge(flush: true, failOnError: true)
            } else {
                simulationTool = simulationTool.save(flush: true, failOnError: true)
            }
            return true

        } else {
            return false
        }
    }

    def earlyPhaseToolDeleteDraft() {

        String sessionId = params.sessionId

        SimulationToolSession simulationSession = session?.getAttribute(sessionId)
        //???ADD CHECK SESSION EXPIRED

        String simulationToolId = simulationSession?.simulationToolId
        String entityId = simulationSession?.entityId
        String designId = simulationSession?.designId
        String indicatorId = simulationSession?.indicatorId

        //SimulationTool simulationTool = SimulationTool.collection.findOne([_id: DomainObjectUtil.stringToObjectId(simulationToolId)]) as SimulationTool
        User user = userService.getCurrentUser(true)
        SimulationTool simulationTool = simulationToolService.findSimulationToolDraft(entityId, designId)

        if (simulationTool) {
            simulationTool.delete()
            flash.fadeSuccessAlert = g.message(code :"simulationTool.designDeleted")
        }

        if (entityId && designId && indicatorId) {
            redirect controller: "design", action: "simulationTool", params: [entityId: entityId, designId: designId, indicatorId: indicatorId]
        } else {
            flash.warningAlert = "Session values problems!!!"
            log.error("Session values problems!!! sessionId: ${sessionId}, entityId: ${entityId}, designId: ${designId}, indicatorId: ${indicatorId}")
            redirect controller: "entity", action: "show", id: entityId
        }
    }

    def getOptionsForEnergyType(){

        String jsonEnergyType = request.JSON?.energyType
        String indicatorId = request.JSON?.indicatorId
        String sessionId = request.JSON?.sessionId
        SimulationToolSession simSession = sessionId ? session?.getAttribute(sessionId) : null

        if (jsonEnergyType) {
            SimulationToolEnergyType energyType = JSON.parse(jsonEnergyType) as SimulationToolEnergyType

            if(energyType){
                def mapResult = simulationToolService.getEnergyTypeOptions(energyType, indicatorId, simSession?.designId)
                render([options: mapResult?.options, defaultOptionName : mapResult?.defaultOptionName, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        }
    }


    def validateSession(){

        String sessionId = request?.JSON?.sessionId
        String entityId = request?.JSON?.entityId

        SimulationToolSession simulationSession = (SimulationToolSession) session?.getAttribute(sessionId)

        if(simulationSession) {
            render([isValid: true] as JSON)
        } else {
            //flash.warningAlert = "Session Expired"
            //redirect controller: "entity", action: "show", id: entityId
            render([isValid: false] as JSON)
        }
    }

    //This method validate the values from the view
    def earlyPhaseToolValidCalculation() {

        Resource res = Resource.findByResourceIdAndActive(params.buildingType, true)

        Double grossFloorArea = params.double("grossFloorArea") ?: 0
        Integer aboveGroundFloors = params.double("aboveGroundFloors")?.toInteger() ?: 0
        Integer undergroundHeatedFloors = params.double("undergroundHeatedFloors")?.toInteger() ?: 0
        Integer undergroundUnheatedFloors = params.double("undergroundUnheatedFloors")?.toInteger() ?: 0

        //At least one checkbox has to be enabled
        Boolean foundationsCheck = params.boolean("foundationsCheck") ?: false
        Boolean groundSlabCheck = params.boolean("groundSlabCheck") ?: false
        Boolean enclosureCheck = params.boolean("enclosureCheck") ?: false
        Boolean structureCheck = params.boolean("structureCheck") ?: false
        Boolean finishesCheck = params.boolean("finishesCheck") ?: false
        Boolean servicesCheck = params.boolean("servicesCheck") ?: false
        Boolean externalAreasCheck = params.boolean("externalAreasCheck") ?: false
        Boolean defaultsCheck = params.boolean("defaultsCheck") ?: false

        String sessionId = params.sessionId ?: ""

        if (sessionId && res && grossFloorArea > 0 && aboveGroundFloors >= 1 && undergroundHeatedFloors >= 0 && undergroundUnheatedFloors >= 0
                && (foundationsCheck || groundSlabCheck || enclosureCheck || structureCheck || finishesCheck || servicesCheck || externalAreasCheck || defaultsCheck)
        ) {

            redirect action: "earlyPhaseToolCalculation", params: params

        } else {

            render text: "error"

        }
    }

    //After the validation [earlyPhaseToolValidCalculation()], the values will be calculated
    def earlyPhaseToolCalculation() {

        log.info("Early Phase Calculation -- Start")

        String indicatorId = params.indicatorId
        String regionReferenceId = params.regionReferenceId
        String buildingType = params.buildingType
        Query query = queryService.getQueryByQueryId(simulationToolService.QUERY_ID, true)
        CarbonDesignerRegion carbonDesignerRegion = query?.carbonDesignerRegions?.find({it.regionReferenceId.equals(regionReferenceId)})
        Resource res = Resource.findByResourceIdAndActive(buildingType, true)

        if(!res) {
            res = new Resource()
            log.error("CD -> The buildingType resource: $buildingType is not present in the DB")
            flashService.setErrorAlert("CD -> The buildingType resource: $buildingType is not present in the DB", true)
        }

        Double grossFloorArea = params.double("grossFloorArea") ?: 0
        Integer aboveGroundFloors = params.double("aboveGroundFloors")?.toInteger() ?: 0
        Integer undergroundHeatedFloors = params.double("undergroundHeatedFloors")?.toInteger() ?: 0
        Integer undergroundUnheatedFloors = params.double("undergroundUnheatedFloors")?.toInteger() ?: 0

        //--------------------------------
        //Unit System Conversion
        //--------------------------------
        //Create a method that return metric if there is a error and throw an exception
        User user = userService.getCurrentUser(true)
        String unitSystem = user.unitSystem
        grossFloorArea = grossFloorArea / simulationToolService.conversionUnitFactor(unitSystem, "m2")

        //--------------------------------

        //--------------------------------
        //CALCULATIONS
        //--------------------------------
        //Calculated parameters


        Double aboveGroundHeight = params.double("heightBuilding")
        Double shorterExternalWallLenght = params.double("depthBuilding")
        Double longer_external_wall_lenght = params.double("widthBuinding")
        Double totalNetArea = params.double("totalNetArea")
        Double maxSpanWithoutColumns_m = params.double("maxSpanWithoutColumns_m")
        Double internalFloorHeight = params.double("internalFloorHeight")
        Double floorThickness_m = params.double("floorThickness_m")
        Double envelopeThickness_m = params.double("envelopeThickness_m")
        Double roofShapeEfficiencyFactor = params.double("roofShapeEfficiencyFactor")
        Double shapeEfficiencyFactor = params.double("shapeEfficiencyFactor")
        Double totalFloors = params.double("totalFloors")
        Double numberStaircases = params.double("numberStaircases")
        Double loadBearingShare = params.double("loadBearingShare")

        Map<String, Double> isUserGiven = [heightBuilding: aboveGroundHeight ?: null, depthBuilding: shorterExternalWallLenght ?: null, widthBuinding: longer_external_wall_lenght ?: null,
                                           totalNetArea: totalNetArea ?: null, maxSpanWithoutColumns_m: maxSpanWithoutColumns_m ?: null, internalFloorHeight: internalFloorHeight ?: null,
                                           floorThickness_m: floorThickness_m ?: null, envelopeThickness_m: envelopeThickness_m ?: null, roofShapeEfficiencyFactor: roofShapeEfficiencyFactor ?: null,
                                           shapeEfficiencyFactor: shapeEfficiencyFactor ?: null, numberStaircases: numberStaircases ?: null, loadBearingShare: loadBearingShare ?: null]

        if(unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value){
            if (aboveGroundHeight) {
                aboveGroundHeight = unitConversionUtil.doConversion(aboveGroundHeight, null, "ft", null, null, null, null, null, null, "m")
            }
            if (shorterExternalWallLenght) {
                shorterExternalWallLenght = unitConversionUtil.doConversion(shorterExternalWallLenght, null, "ft", null, null, null, null, null, null, "m")
            }
            if (longer_external_wall_lenght) {
                longer_external_wall_lenght = unitConversionUtil.doConversion(longer_external_wall_lenght, null, "ft", null, null, null, null, null, null, "m")
            }
            if (maxSpanWithoutColumns_m) {
                maxSpanWithoutColumns_m = unitConversionUtil.doConversion(maxSpanWithoutColumns_m, null, "ft", null, null, null, null, null, null, "m")
            }
            if (internalFloorHeight) {
                internalFloorHeight = unitConversionUtil.doConversion(internalFloorHeight, null, "ft", null, null, null, null, null, null, "m")
            }
            if (totalNetArea) {
                totalNetArea = unitConversionUtil.doConversion(totalNetArea, null, "sq ft", null, null, null, null, null, null, "m2")
            }
            if (floorThickness_m) {
                floorThickness_m = unitConversionUtil.doConversion(floorThickness_m, null, "ft", null, null, null, null, null, null, "m")
            }
            if (envelopeThickness_m) {
                envelopeThickness_m = unitConversionUtil.doConversion(envelopeThickness_m, null, "ft", null, null, null, null, null, null, "m")
            }
        }

        if (!floorThickness_m) {
            floorThickness_m = res.floorThickness_m ?: 0
        }

        if (!envelopeThickness_m) {
            envelopeThickness_m = res.envelopeThickness_m ?: 0
        }

        if (!roofShapeEfficiencyFactor) {
            roofShapeEfficiencyFactor = res.roofShapeEfficiencyFactor ?: 0
        }
        Integer undergroundTotalFloors = undergroundHeatedFloors + undergroundUnheatedFloors

        if(totalFloors == null) totalFloors = aboveGroundFloors + undergroundTotalFloors

        internalFloorHeight = internalFloorHeight != null ? internalFloorHeight : aboveGroundHeight && totalFloors ? (aboveGroundHeight / totalFloors - floorThickness_m) : (res.internalFloorHeight_m ?: 0)


        Double singleFloorGrossArea = (grossFloorArea) / (totalFloors?:1)

        //Building envelope
        //??? IF a resource value is null what should i do?
        Double singleFloorHeight = internalFloorHeight + floorThickness_m

        aboveGroundHeight = aboveGroundHeight != null ? aboveGroundHeight : ((aboveGroundFloors) * (singleFloorHeight))

        Double belowGroundHeight = (undergroundTotalFloors) * (singleFloorHeight)

        // LOWER OF A) Math.sqrt(singleFloorGrossArea /res.shapeRatio ) OR B) buildingMaxDepth_m
        if(shorterExternalWallLenght == null){
            shorterExternalWallLenght = Math.sqrt(singleFloorGrossArea / (res.lenghtDepthRatio ?: 1))
            shorterExternalWallLenght = (shorterExternalWallLenght > (res.maxBuildingDepth_m ?: 0)) ?
                    (res.maxBuildingDepth_m ?: 0) : shorterExternalWallLenght
        }

        shapeEfficiencyFactor = shapeEfficiencyFactor != null ? shapeEfficiencyFactor : (res.buildingShapeEfficiencyFactor ?: 0)

        if(longer_external_wall_lenght == null){
            longer_external_wall_lenght = (singleFloorGrossArea) / (shorterExternalWallLenght?:1) * (shapeEfficiencyFactor ?: 1)
        }

        Double building_external_wall_lenght = 2 * (shorterExternalWallLenght + longer_external_wall_lenght)

        Double singleFloorNetArea = singleFloorGrossArea - (building_external_wall_lenght * envelopeThickness_m)

        if (totalNetArea == null) {
            totalNetArea = params.double('designNetFloorArea')

            if (!totalNetArea) {
                totalNetArea = singleFloorNetArea * totalFloors

                if (unitSystem == UnitConversionUtil.UnitSystem.IMPERIAL.value){
                    totalNetArea = unitConversionUtil.doConversion(totalNetArea, null, "m2", null, null, null, null, null, null, "sq ft")
                }
            }
        }

        Double underground_walls_area_m2 = building_external_wall_lenght * singleFloorHeight * undergroundTotalFloors

        Double above_ground_facade_area_m2 = building_external_wall_lenght * singleFloorHeight * aboveGroundFloors

        Double external_doors_area_m2 = (res.doorsToGroundfFloorAreaRatio ?: 0) * singleFloorGrossArea

        //LOWER OF A) windowsShare x (above ground floors x singleFloorGrossArea)  OR b) maximum share of windows of ext wall x above ground facade area
        Double a = aboveGroundFloors * singleFloorGrossArea * (res.windowsToAboveGroundAreaRatio ?: 0)
        Double b = above_ground_facade_area_m2 * (res.windowsMaximumOfExternalWalls ?: 0)
        Double external_windows_area_m2 = (a > b) ? b : a

        //above ground facade area minus doors minus windows BUT never negative
        Double external_wall_area_m2 = above_ground_facade_area_m2 - external_doors_area_m2 - external_windows_area_m2
        external_wall_area_m2 = (external_wall_area_m2 > 0) ? external_wall_area_m2 : 0 //???

        //Building height and structure

        //Columns and Beams

        maxSpanWithoutColumns_m = maxSpanWithoutColumns_m != null ? maxSpanWithoutColumns_m : res.maxSpanWithoutColumns_m
        // generate error log message here if maxSpanWithoutColumns_m < 5 or null
        if(!maxSpanWithoutColumns_m || maxSpanWithoutColumns_m < 5){
            log.error("Carbon Designer --> For the buidingType: ${buildingType} maxSpanWithoutColumns_m is ${maxSpanWithoutColumns_m}")
            flashService.setErrorAlert("Carbon Designer --> For the buidingType: ${buildingType} maxSpanWithoutColumns_m is ${maxSpanWithoutColumns_m}", true)
            maxSpanWithoutColumns_m = 5
        }

        Integer nrOfLoadBearingAxes = Math.round ( longer_external_wall_lenght / (maxSpanWithoutColumns_m ?:1) ) + 1
        nrOfLoadBearingAxes = (nrOfLoadBearingAxes > 2) ? nrOfLoadBearingAxes : 2 // it has to have at minimum one axis on both sides
        Integer nrOfColumnsPerAxis = Math.round ( shorterExternalWallLenght /(maxSpanWithoutColumns_m ?:1) ) + 1
        nrOfColumnsPerAxis = (nrOfColumnsPerAxis > 2) ? nrOfColumnsPerAxis : 2 /// it has to have at minimum one column on both sides

        Double nrOfRequiredColumnsPerFloor = nrOfColumnsPerAxis * nrOfLoadBearingAxes

        Double lenghtOfRequiredColumns = (nrOfRequiredColumnsPerFloor) * (totalFloors) * (singleFloorHeight)

        Double lenghtOfRequiredBeams = nrOfLoadBearingAxes * shorterExternalWallLenght * totalFloors

        Boolean isEarthquakeZoneMedium = params.boolean("isEarthquakeZoneMedium")
        Boolean isEarthquakeZoneHigh = params.boolean("isEarthquakeZoneHigh")

        if (isEarthquakeZoneMedium||isEarthquakeZoneHigh) {
            String earthquakeMethod = isEarthquakeZoneMedium ? "medium" : "high"
            lenghtOfRequiredBeams = lenghtOfRequiredBeams + ( nrOfColumnsPerAxis * longer_external_wall_lenght * totalFloors )

            List<CDEarthquakeSetting> earthquakeSettings = carbonDesignerRegion?.earthquakeSettings

            if (earthquakeSettings) {
                lenghtOfRequiredBeams = lenghtOfRequiredBeams * (earthquakeSettings.find({earthquakeMethod.equals(it.earthquakeZone) && it.compatibleBuildingTypeList != null && (it.compatibleBuildingTypeList.isEmpty() || it.compatibleBuildingTypeList.contains(buildingType))})?.quantityMultiplier?.get("beam") ?: 1)
                lenghtOfRequiredColumns = lenghtOfRequiredColumns * (earthquakeSettings.find({earthquakeMethod.equals(it.earthquakeZone) && it.compatibleBuildingTypeList != null && (it.compatibleBuildingTypeList.isEmpty() || it.compatibleBuildingTypeList.contains(buildingType))})?.quantityMultiplier?.get("column") ?: 1)
            } else {
                log.warn("CARBON DESIGNER: No earthquakeSettings found with regionReferenceId: ${regionReferenceId}")
                flashService.setWarningAlert("CARBON DESIGNER: No earthquakeSettings found with regionReferenceId: ${regionReferenceId}", true)
            }
        }

        //--------------------------------
        //CALCULATIONS
        //--------------------------------

        //--------------------------------
        //-----LOG Parameters------------
        log.info("singleFloorGrossArea: " + singleFloorGrossArea)
        log.info("singleFloorHeight: " + singleFloorHeight)
        log.info("aboveGroundHeight: " + aboveGroundHeight)
        log.info("belowGroundHeight: " + belowGroundHeight)
        log.info("shortwall: " + Math.sqrt(singleFloorGrossArea / (res.lenghtDepthRatio ?: 1)))
        log.info("shorterExternalWallLenght: " + shorterExternalWallLenght)
        log.info("longer_external_wall_lenght: " + longer_external_wall_lenght)
        log.info("building_external_wall_lenght: " + building_external_wall_lenght)
        log.info("singleFloorNetArea: " + singleFloorNetArea)
        log.info("totalNetArea: " + totalNetArea)
        log.info("underground_walls_area_m2: " + underground_walls_area_m2)
        log.info("above_ground_facade_area_m2: " + above_ground_facade_area_m2)
        log.info("external_doors_area_m2: " + external_doors_area_m2)
        log.info("external_windows_area_m2: " + external_windows_area_m2)
        log.info("external_wall_area_m2: " + external_wall_area_m2)
        log.info("nrOfRequiredColumnsPerFloor: " + nrOfRequiredColumnsPerFloor)
        log.info("lenghtOfRequiredColumns: " + nrOfRequiredColumnsPerFloor)
        log.info("lenghtOfRequiredBeams: " + nrOfRequiredColumnsPerFloor)

        //-----LOG Parameters------------
        //--------------------------------

        //--------------------------------
        //Parameters showed
        //--------------------------------

        //Foundations
        Double foundations = grossFloorArea

        //Ground Slab
        Double groundSlab = singleFloorGrossArea //singleFloorArea
        Double foundationCleanliness = singleFloorGrossArea

        //Enclosure
        Double undergroundWalls = underground_walls_area_m2
        Double externalWalls = external_wall_area_m2
        Double cladding = external_wall_area_m2
        Double externalWindows = external_windows_area_m2
        Double externalDoors = external_doors_area_m2
        Double roofSlab = singleFloorGrossArea //singleFloorArea
        Double roof = singleFloorGrossArea * roofShapeEfficiencyFactor

        //Structure
        Double floorSlabs = singleFloorGrossArea * (totalFloors - 1)
        Double columns_m = lenghtOfRequiredColumns
        Double beams_m = lenghtOfRequiredBeams

        //IF (above ground floors >1) then above ground floors x singleFloorArea x balconyRatio
        //To understand better iIF (above ground floors < 1) what it will happen
        Double balcony = (aboveGroundFloors > 1) ? aboveGroundFloors * singleFloorGrossArea * (res.balconiesToAboveGroundAreaRatio ?: 0) : 0

        //IF (floors > 1) THEN (aboveGroundheight + belowGroundHeight) x  ( (round up (longer external wall lenght/max stair case distance - 1) or minimum 1) )
        //To understand better IF (floors < 1) what it will happen

        if(numberStaircases == null) {
            numberStaircases = Math.ceil((longer_external_wall_lenght / (res.maxStairCaseDistance_m ?: 1)) - 1)
            numberStaircases = (numberStaircases > 1) ? numberStaircases : 1
            numberStaircases = totalFloors > 1 ? numberStaircases : 0
        }
        Double staircases = (aboveGroundHeight + belowGroundHeight) * numberStaircases

        //Finishes
        Double floorFinish = (aboveGroundFloors + undergroundHeatedFloors) * singleFloorNetArea
        Double ceilingFinish = floorFinish

        //--------------------------------
        //Parameters showed
        //--------------------------------


        log.info("Early Phase Calculation -- End")

        Boolean foundationsCheck = params.boolean("foundationsCheck") ?: false
        Boolean groundSlabCheck = params.boolean("groundSlabCheck") ?: false
        Boolean enclosureCheck = params.boolean("enclosureCheck") ?: false
        Boolean structureCheck = params.boolean("structureCheck") ?: false
        Boolean finishesCheck = params.boolean("finishesCheck") ?: false
        Boolean servicesCheck = params.boolean("servicesCheck") ?: false
        Boolean externalAreasCheck = params.boolean("externalAreasCheck") ?: false
        Boolean defaultsCheck = params.boolean("defaultsCheck") ?: false

        Double heightBuilding = aboveGroundHeight
        Double widthBuinding = longer_external_wall_lenght?:0
        Double depthBuilding = shorterExternalWallLenght?:0
        Double heatedArea = params.double("designHeatedArea") ?: (singleFloorNetArea ?: 0) * ((aboveGroundFloors ?:0) + (undergroundHeatedFloors?:0))

        //Frost insulation
        Double frostInsulation = (depthBuilding + widthBuinding) * 2



        //New Parameter
        String entityId = params.entityId
        Entity entity = entityId ? entityService.getEntityById(entityId) : null
        if(!entity) {
            log.error("Entity in CD for calculation group is null [entityId: $entityId]")
            flashService.setErrorAlert("Entity in CD for calculation group is null [entityId: $entityId]", true)
        }
        String countryId = entity?.datasets?.find{it?.questionId == "country"}?.answerIds?.find{it} ?: ""
        def dimensioningVariables = query?.dimensioningVariables
        if(!dimensioningVariables) {
            log.error("DimensioningVariables in CD query are not defined or not correct")
            flashService.setErrorAlert("DimensioningVariables in CD query are not defined or not correct", true)
        }

        List<EntityTypeConstructionGroupAllocation> orderedConstructionGroupList = query?.orderedConstructionGroupList

        DimensioningVariable defaultDimensional = dimensioningVariables?.find{ it.countryIds?.contains(countryId)}
        DimensioningVariable globalDefaultDimensional = dimensioningVariables?.find{it.applyAsDefault}

        if(!defaultDimensional) defaultDimensional = globalDefaultDimensional

        if(!defaultDimensional) {
            log.error("DimensioningVariables in CD query are not defined in the correct way ")
            flashService.setErrorAlert("DimensioningVariables in CD query are not defined in the correct way ", true)
        }

        Double coolingkWperm2GFA = defaultDimensional?.coolingkWtom2GFARatio?.get(buildingType)
        coolingkWperm2GFA = coolingkWperm2GFA != null ? coolingkWperm2GFA : (globalDefaultDimensional?.coolingkWtom2GFARatio?.get(buildingType) ?: 0)

        Double heatingkWperm2GFA = defaultDimensional?.heatingkWtom2GFARatio?.get(buildingType)
        heatingkWperm2GFA = heatingkWperm2GFA != null ? heatingkWperm2GFA : (globalDefaultDimensional?.heatingkWtom2GFARatio?.get(buildingType) ?: 0)

        Double loadBearingInternalWallShare =  defaultDimensional?.loadBearingInternalWallShare?.get(buildingType)
        loadBearingInternalWallShare = loadBearingInternalWallShare != null ? loadBearingInternalWallShare : (globalDefaultDimensional?.loadBearingInternalWallShare?.get(buildingType) ?: 0)
        log.info("DimensioningVariable in CD coolingkWperm2GFA: $coolingkWperm2GFA, heatingkWperm2GFA: $heatingkWperm2GFA")

        Double externalPavedAreasPerGFA = res.externalPavedAreasPerGFA ?: 0

        Double ventilation = grossFloorArea ?: 0
        Double heating = grossFloorArea ?: 0
        Double cooling = (grossFloorArea ?: 0) * (coolingkWperm2GFA)
        Double electrification	= grossFloorArea ?: 0
        Double heatSource	= (grossFloorArea ?: 0) * (heatingkWperm2GFA)
        Double water = grossFloorArea ?: 0
        Double safety = grossFloorArea ?: 0
        Double freshWater = grossFloorArea ?: 0
        Double wasteWater = grossFloorArea ?: 0
        Double shelter = simulationToolService.resolveShelter(grossFloorArea, buildingType)

        Double numberElevators = numberStaircases ?: 0
        Double sanitarySpaces = grossFloorArea ?: 0
        Double telecommunications = grossFloorArea ?: 0
        Double heatingUnit = grossFloorArea ?: 0
        Double externalPavedAreas = externalPavedAreasPerGFA * grossFloorArea ?: 30
        Double energyNetwork = grossFloorArea ?: 0

                //InternalWalls
        loadBearingShare = loadBearingShare != null ? (loadBearingShare / 100) :  (loadBearingInternalWallShare ?: 0)

        Double internalWall = above_ground_facade_area_m2 * (res.internalWallsToExternalWallsRatio ?: 0)
        Double loadBearingInternalWall = internalWall * loadBearingShare
        internalWall = internalWall * (1 - loadBearingShare)

        Double internalWallFinishes = (internalWall * 2) + (loadBearingInternalWall * 2) + external_wall_area_m2

        //ContructionGroup amount If less then 0 zeturn 0
        foundations = simulationToolService.filterNegative(foundations)
        frostInsulation = simulationToolService.filterNegative(frostInsulation)
        groundSlab = simulationToolService.filterNegative(groundSlab)

        floorSlabs = simulationToolService.filterNegative(floorSlabs)
        columns_m = simulationToolService.filterNegative(columns_m)
        beams_m = simulationToolService.filterNegative(beams_m)
        balcony = simulationToolService.filterNegative(balcony)
        staircases = simulationToolService.filterNegative(staircases)
        loadBearingInternalWall = simulationToolService.filterNegative(loadBearingInternalWall)

        undergroundWalls = simulationToolService.filterNegative(undergroundWalls)
        externalWalls = simulationToolService.filterNegative(externalWalls)
        cladding = simulationToolService.filterNegative(cladding)
        externalWindows = simulationToolService.filterNegative(externalWindows)
        externalDoors = simulationToolService.filterNegative(externalDoors)
        externalPavedAreas = simulationToolService.filterNegative(externalPavedAreas)
        roofSlab = simulationToolService.filterNegative(roofSlab)
        roof = simulationToolService.filterNegative(roof)

        internalWall = simulationToolService.filterNegative(internalWall)
        internalWallFinishes = simulationToolService.filterNegative(internalWallFinishes)
        floorFinish = simulationToolService.filterNegative(floorFinish)
        ceilingFinish = simulationToolService.filterNegative(ceilingFinish)

        ventilation = simulationToolService.filterNegative(ventilation)
        heating = simulationToolService.filterNegative(heating)
        cooling = simulationToolService.filterNegative(cooling)
        electrification = simulationToolService.filterNegative(electrification)
        heatSource = simulationToolService.filterNegative(heatSource)
        water = simulationToolService.filterNegative(water)
        safety = simulationToolService.filterNegative(safety)
        freshWater = simulationToolService.filterNegative(freshWater)
        wasteWater = simulationToolService.filterNegative(wasteWater)
        numberElevators = simulationToolService.filterNegative(numberElevators)
        sanitarySpaces = simulationToolService.filterNegative(sanitarySpaces)
        telecommunications = simulationToolService.filterNegative(telecommunications)
        heatingUnit = simulationToolService.filterNegative(heatingUnit)
        energyNetwork = simulationToolService.filterNegative(energyNetwork)

        totalNetArea = simulationToolService.filterNegative(totalNetArea)
        heatedArea = simulationToolService.filterNegative(heatedArea)
        foundationCleanliness = simulationToolService.filterNegative(foundationCleanliness)


        Map<String, Double> calculatedAreas = [foundations: foundations, groundSlab: groundSlab, undergroundWalls: undergroundWalls, externalWalls: externalWalls, cladding: cladding,
                                               externalWindows: externalWindows, externalDoors: externalDoors, roofSlab: roofSlab, roof: roof, floorSlabs: floorSlabs, columns: columns_m,
                                               beams: beams_m, balconies: balcony, staircases: staircases, floorFinish: floorFinish, ceilingFinish: ceilingFinish, frostInsulation: frostInsulation,
                                               shelter: shelter, internalWallFinishes: internalWallFinishes, ventilation: ventilation, heating: heating, cooling: cooling, electrification: electrification,
                                               heatSource: heatSource, water: water, safety: safety, internalWall: internalWall, loadBearingInternalWall: loadBearingInternalWall, freshWater: freshWater,
                                               wasteWater: wasteWater, foundationCleanliness: foundationCleanliness, externalPavedAreas: externalPavedAreas, numberElevators: numberElevators,
                                               sanitarySpaces: sanitarySpaces, telecommunications: telecommunications, heatingUnit: heatingUnit, energyNetwork: energyNetwork]

        query?.orderedConstructionGroupList?.findAll({"defaultsGroup".equals(it.buildingElement)})?.each { EntityTypeConstructionGroupAllocation defaultGroups ->
            if ("grossInternalFloorArea".equals(defaultGroups.quantitySource)) {
                calculatedAreas.put((defaultGroups.groupId), totalNetArea?:0)
            } else if ("grossFloorArea".equals(defaultGroups.quantitySource)) {
                calculatedAreas.put((defaultGroups.groupId), grossFloorArea?:0)
            } else if ("heatedArea".equals(defaultGroups.quantitySource)) {
                calculatedAreas.put((defaultGroups.groupId), heatedArea?:0)
            }
        }

        List<String> allowedGroupsForRegion = simulationToolService.getAllowedConstructionGroupsForRegionReferenceId(regionReferenceId, indicatorId)

        Map<String, String> output = [:]

        def modelBuildingDimension = [loadBearingShare: loadBearingShare, maxSpanWithoutColumns_m: maxSpanWithoutColumns_m,
                                      heightBuilding: heightBuilding, widthBuinding: widthBuinding, depthBuilding: depthBuilding,
                                      totalFloors: totalFloors, numberStaircases: numberStaircases, internalFloorHeight: internalFloorHeight,
                                      heatedArea: heatedArea, totalNetArea: totalNetArea, unitSystem: unitSystem,
                                      floorThickness_m: floorThickness_m, envelopeThickness_m: envelopeThickness_m, roofShapeEfficiencyFactor: roofShapeEfficiencyFactor,
                                      lenghtDepthRatio: res.lenghtDepthRatio ?: 0, maxBuildingDepth_m: res.maxBuildingDepth_m ?: 0, doorsToGroundfFloorAreaRatio: res.doorsToGroundfFloorAreaRatio ?: 0,
                                      windowsToAboveGroundAreaRatio: res.windowsToAboveGroundAreaRatio ?: 0, windowsMaximumOfExternalWalls: res.windowsMaximumOfExternalWalls ?: 0, balconiesToAboveGroundAreaRatio: res.balconiesToAboveGroundAreaRatio ?: 0,
                                      internalWallsToExternalWallsRatio: res.internalWallsToExternalWallsRatio ?: 0, maxStairCaseDistance_m: res.maxStairCaseDistance_m ?: 0, isUserGiven: isUserGiven,
                                      externalPavedAreas: externalPavedAreas, externalPavedAreasRatio: res.externalPavedAreasPerGFA ?: 0]

        def model =        [foundationsCheck: foundationsCheck, groundSlabCheck: groundSlabCheck,
                            enclosureCheck: enclosureCheck, structureCheck: structureCheck,
                            finishesCheck : finishesCheck, servicesCheck: servicesCheck,
                            externalAreasCheck: externalAreasCheck, defaultsCheck: defaultsCheck,
                            shapeEfficiencyFactor : shapeEfficiencyFactor, allowedGroupsForRegion: allowedGroupsForRegion,
                            orderedConstructionGroupList: orderedConstructionGroupList, calculatedAreas: calculatedAreas]

        model << modelBuildingDimension

        String building_dimensions_div = g.render(template:  "/util/calculationEarlyPhase", model: model).toString()
        String column_areas_div = g.render(template:  "/util/columnConstrunctionGroups", model: model).toString()

        output.put("building_dimensions_div", building_dimensions_div)
        output.put("column_areas_div", column_areas_div)

        String sessionId = params.sessionId ?: ""
        SimulationToolSession simulationSession = sessionId ? session?.getAttribute(sessionId) : null

        if(simulationSession){
            simulationSession.heightBuilding = heightBuilding
            simulationSession.widthBuinding = widthBuinding
            simulationSession.depthBuilding = depthBuilding
            simulationSession.maxSpanWithoutColumns_m = maxSpanWithoutColumns_m
            simulationSession.internalFloorHeight = internalFloorHeight
            simulationSession.loadBearingShare = loadBearingShare
            simulationSession.numberStaircases = numberStaircases
            simulationSession.totalNetArea = totalNetArea
            simulationSession.shapeEfficiencyFactor = shapeEfficiencyFactor
            session?.setAttribute(sessionId, simulationSession)
        }

        render([data: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getBuildingTypeImageClass(){

        String buildingType = request.JSON.buildingType
        String buildingTypeImageClass = buildingType ? simulationToolService.iconBuildingType(buildingType) : ""

        if(buildingTypeImageClass) buildingTypeImageClass = "entitytype $buildingTypeImageClass"

        render([className : buildingTypeImageClass ?: "", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    //WORKFLOW
    def saveWorkFlow() {

        String jsonWorkFlow = request?.JSON?.jsonWorkFlow

        if (jsonWorkFlow) {

            WorkFlowForEntity workFlow = JSON.parse(jsonWorkFlow)

            if (workFlow) {
                try{
                    WorkFlowForEntity old = WorkFlowForEntity.findByEntityId(workFlow.entityId)
                    if(old){
                        old.properties = workFlow.properties
                        workFlow = old
                    }

                    // Kikkare, needs to persist in query workFlow object but not for every entity
                    workFlow?.workFlowList?.each { workFlowList ->
                        workFlowList?.stepList?.each {
                            it.name = null
                            it.help = null
                            it.localizedName = null
                            it.localizedHelp = null
                        }
                    }

                    if (workFlow.validate()) {
                        workFlow.save(flush: true, failOnError: true)
                        render([workFLow: workFlow, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    } else {
                        render([workFlow: workFlow, error: "workFlow is not valid", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    }

                }catch (OptimisticLockingException e) {
                    log.info("WorkFLow: Optimistic locking error!")
                    render([workFLow: workFlow, error: "Error Opt. Lock WorkFLow", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                }

            } else {
                render([error: "WorkFlow not found", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            render([error: "Error JSON workflow", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

        }

    }

    def saveFrameStatus() {

        String frameStatusJson = request?.JSON?.frameStatus
        log.info(frameStatusJson)
        if (frameStatusJson) {
            FrameStatus frameStatus = JSON.parse(frameStatusJson)

            if (frameStatus) {
                try {
                    FrameStatus old = FrameStatus.findByUserId(frameStatus?.userId)
                    if (old) {
                        old.properties = frameStatus.properties

                        if (old.validate()) {
                            old.save(flush: true, failOnError: true)
                            log.info("FrameStatus: saved!")
                            render([frameStatus: old, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                        } else {
                            log.info("FrameStatus: invalid!")
                            render([frameStatus: old, error: "frameStatus is not valid", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                        }
                    } else {
                        if (frameStatus.validate()) {
                            frameStatus.save(flush: true, failOnError: true)
                            log.info("FrameStatus: saved!")
                            render([frameStatus: frameStatus, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                        } else {
                            log.info("FrameStatus: invalid!")
                            render([frameStatus: frameStatus, error: "frameStatus is not valid", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                        }
                    }
                } catch (OptimisticLockingException e) {
                    log.info("FrameStatus: Optimistic locking error!")
                    render([frameStatus: frameStatus, error: "Error Opt. Lock frameStatus", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                }

            } else {
                render([error: "frameStatus not found", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }

        }else {
            render([error: "Error JSON frameStatus", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

    }

    private String getLocalizedNameFromDocument(Document d) {
        String localizedName

        if (d) {
            String lang = DomainObjectUtil.mapKeyLanguage
            localizedName = d.get("name" + lang)

            if (!localizedName) {
                localizedName = d.nameEN
            }
        }
        return localizedName
    }

    def getEnergyProfileSelect() {
        String toRender = ""
        String countryResourceId = params.countryResourceId
        String resourceId = params.resourceId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String additionalQuestionId = params.additionalQuestionId
        String queryId = params.queryId
        String indicatorId = params.indicatorId
        Boolean changeInputResourceId = params.boolean("changeInputResourceId")
        String nameToRemove = ""
        Account account = userService.getAccount(userService.getCurrentUser())
        String accountId = account?.id

        if (countryResourceId && additionalQuestionId && questionId && sectionId) {
            String additionalFieldName
            String random = UUID.randomUUID().toString()

            if (changeInputResourceId) {
                additionalFieldName = "${sectionId}.${questionId}_additional_${additionalQuestionId}${countryResourceId ? '.' + countryResourceId : ''}"
            } else {
                additionalFieldName = "${sectionId}.${questionId}_additional_${additionalQuestionId}${resourceId ? '.' + resourceId : ''}"
            }
            String additionalFieldId = "${random}${sectionId}.${questionId}_additional_${additionalQuestionId}"

            Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())

            if (additionalQuestionQueryConfig) {
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryConfig.value, true)

                if (additionalQuestionsQuery) {
                    Question additionalQuestion = additionalQuestionsQuery.getAllQuestions()?.find({ Question q -> q.questionId == additionalQuestionId })

                    if (additionalQuestion) {
                        if ("noLocalCompensation".equals(countryResourceId)) {
                            toRender = "${toRender}<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" value=\"\" />"
                        } else {
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                            List<Document> profiles = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)?.findAll({it.active && it.areas && it.areas.contains(countryResourceId)})?.sort({it.defaultProfile})?.reverse()
                            String targetDefaultResourceId = optimiResourceService.getResourceWithParams(countryResourceId, "default")?.countryEnergyResourceId
                            toRender = "${toRender}<select class=\"compensationEnergyProfileSelect lockableDatasetQuestion\" ${changeInputResourceId ? "style=\"width: 80px;\"" : g.questionInlineStyle(question: additionalQuestion)} name=\"${additionalFieldName}\" id=\"${additionalFieldId}\">"
                            profiles?.each { Document p ->
                                String valueString = "${p.resourceId}.${p.profileId}"
                                if(!p.privateDatasetAccountId || p.privateDatasetAccountId == accountId ){
                                    toRender = "${toRender}<option value=\"${valueString}\" ${p.resourceId?.equals(targetDefaultResourceId) && p.defaultProfile ? "selected=\"selected\"" : ""}>${p.profileNameEN ?: p.profileId} - ${p.staticFullName}</option>"
                                }
                            }
                            toRender = "${toRender}</select>"
                        }
                        nameToRemove = "${sectionId}.${questionId}_additional_${additionalQuestionId}${resourceId ? '.' + resourceId : ''}"
                    }
                }
            }
        }
        render([toRender: toRender, nameToRemove: nameToRemove, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getAdditionalQuestionSelect() {
        String toRender = ""
        String additionalQuestionId = params.additionalQuestionId
        String resourceId = params.resourceId
        String queryId = params.queryId
        String sectionId = params.sectionId
        String questionId = params.questionId
        String answer = params.answer
        String importMapperDatasetId = params.importMapperDatasetId
        String parentEntityId = params.parentEntityId
        String indicatorId = params.indicatorId
        String nameToRemove = ""

        if (additionalQuestionId && queryId && sectionId && questionId) {
            String random = UUID.randomUUID().toString()
            Boolean undefinedSelected = Boolean.FALSE
            String additionalFieldName
            String additionalFieldId

            if (importMapperDatasetId) {
                additionalFieldName = "additional_${additionalQuestionId}.${importMapperDatasetId}"
                additionalFieldId = "additional_${additionalQuestionId}.${importMapperDatasetId}"
            } else {
                additionalFieldName = "${sectionId}.${questionId}_additional_${additionalQuestionId}${resourceId ? '.' + resourceId : ''}"
                additionalFieldId = "${random}${sectionId}.${questionId}_additional_${additionalQuestionId}"
            }

            if (answer && Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER.equals(answer)) {
                answer = null
                undefinedSelected = Boolean.TRUE
            }

            Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())

            if (additionalQuestionQueryConfig) {
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryConfig.value, true)

                if (additionalQuestionsQuery) {
                    Question additionalQuestion = additionalQuestionsQuery.getAllQuestions()?.find({ Question q -> q.questionId == additionalQuestionId })

                    if (additionalQuestion) {
                        if (additionalQuestion.showTypeahead) {
                            String widthClass = params.overrideAutocompleteWidthClass != null ? params.overrideAutocompleteWidthClass: 'additionalQuestionAutocompleteWidth'
                            toRender = "${toRender}<div class=\"input-append\">"
                            toRender = "${toRender}<input type=\"text\" class=\"additionalQuestionAutocomplete ${widthClass} lockableDatasetQuestion\" class=\"input-xlarge\" autocomplete=\"off\" data-original-title=\"\""
                            toRender = "${toRender} data-indicatorId=\"${indicatorId}\" maxlength=\"10000\""
                            toRender = "${toRender} data-queryId=\"${queryId}\" data-sectionId=\"${sectionId}\" value=\"${answer ? optimiResourceService.getLocalizedName(optimiResourceService.getResourceByResourceAndProfileId(answer, null, true)) ?: "" : ""}\""
                            toRender = "${toRender} data-questionId=\"${questionId}\" data-additionalQuestionId=\"${additionalQuestion.questionId}\"><a tabindex=\"-1\" class=\"add-on showAllResources lockableDatasetQuestion\" onclick=\"showAllAdditionalQuestionResources(this);\"><i class=\"icon-chevron-down\"></i></a>"
                            toRender = "${toRender}<input class=\"additionalQuestionAutocompleteHiddenInput\" type=\"hidden\" name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" value=\"${answer ? answer : ""}\" /></div><span id=\"${random}loadingResource\" style=\"display: none;\"><i class=\"fas fa-circle-notch fa-spin\" aria-hidden=\"true\"></i></span>"
                            nameToRemove = additionalFieldName
                        } else if (com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID.equals(additionalQuestion.questionId)) {
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                            String mainQuestionAnswer = params.mainQuestionAnswer
                            List<Document> profiles = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)?.findAll({it.active && it.areas && it.areas.contains(mainQuestionAnswer)})?.sort({it.defaultProfile})?.reverse()

                            toRender = "${toRender}<select class=\"compensationEnergyProfileSelect lockableDatasetQuestion\" ${g.questionInlineStyle(question: additionalQuestion, answer: answer)} name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" data-questionId=\"${additionalQuestionId}\" data-resourceId=\"${resourceId}\">"
                            profiles?.each { Document p ->
                                String valueString = "${p.resourceId}.${p.profileId}"
                                toRender = "${toRender}<option value=\"${valueString}\" ${valueString?.equals(answer?.toString()) ? "selected=\"selected\"" : ""}>${p.profileNameEN ?: p.profileId} - ${p.staticFullName}</option>"
                            }
                            toRender = "${toRender}</select>"
                            nameToRemove = additionalFieldName
                        } else {
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                            List<Document> resources = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)

                            if (resources) {
                                Boolean activeAnswer = answer ? resources.find({ answer.toString().equalsIgnoreCase(it.resourceId) && it.active }) ? true : false : true
                                Document inactiveResource = answer && !activeAnswer ? resources.find({ answer?.toString()?.equalsIgnoreCase(it.resourceId) && !it.active }) : null

                                toRender = "${toRender}<select class='lockableDatasetQuestion '${additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID ? " onchange=\"rerenderEnergyProfiles(this, '${additionalQuestion.groupedQuestionIds ? additionalQuestion.groupedQuestionIds.first() : ''}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', null, null, null, '${indicator?.indicatorId}')\"" : ""} ${g.questionInlineStyle(question: additionalQuestion, answer: answer)} name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" data-questionId=\"${additionalQuestionId}\" data-resourceId=\"${resourceId}\">"

                                if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID) {
                                    toRender = "${toRender}<option value=\"noLocalCompensation\" ${answer?.toString()?.equalsIgnoreCase("noLocalCompensation") ? "selected='selected'" : ""}>${message(code: 'notApplied')}</option>"
                                } else if (additionalQuestion.noChoiceDisplayText) {
                                    toRender = "${toRender}<option value=\"${Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER}\" ${undefinedSelected ? "selected='selected'" : ""}>${additionalQuestion.localizedNoChoiceDisplayText}</option>"
                                }

                                resources.each { Document resource ->
                                    if (resource.active) {
                                        toRender = "${toRender}<option value=\"${resource.resourceId}\" ${answer?.toString()?.equalsIgnoreCase(resource.resourceId) ? "selected='selected'" : ""}>${resource.localizedName}</option>"
                                    }
                                }

                                if (inactiveResource) {
                                    toRender = "${toRender}<option value=\"${inactiveResource.resourceId}\" selected=\"selected\">${inactiveResource.localizedName} (${message(code: "inactive")})</option>"
                                }
                                toRender = "${toRender}</select>"
                                nameToRemove = additionalFieldName
                            } else {
                                Query query = queryService.getQueryByQueryId(queryId, true)

                                if (query) {
                                    Question mainQuestion = questionService.getQuestion(query, questionId)
                                    Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, null, Boolean.TRUE)

                                    if (mainQuestion) {
                                        Entity parentEntity = entityService.readEntity(parentEntityId)
                                        List<QuestionAnswerChoice> filteredChoises = questionService.getFilteredChoices(parentEntity, mainQuestion, resource, answer?.toString(), additionalQuestion)

                                        if (filteredChoises) {
                                            toRender = "${toRender}<select ${g.questionInlineStyle(question: additionalQuestion, answer: answer)} name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" data-questionId=\"${additionalQuestionId}\" data-resourceId=\"${resourceId}\">"
                                            if (additionalQuestion.noChoiceDisplayText) {
                                                toRender = "${toRender}<option value=\"${Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER}\" ${undefinedSelected ? "selected='selected'" : ""}>${additionalQuestion.localizedNoChoiceDisplayText}</option>"
                                            }

                                            filteredChoises.each { QuestionAnswerChoice choise ->
                                                toRender = "${toRender}<option value=\"${choise.answerId}\" ${answer?.toString()?.equalsIgnoreCase(choise.answerId) ? "selected='selected'" : ""}>${choise.localizedAnswer}</option>"
                                            }
                                            toRender = "${toRender}</select>"
                                            nameToRemove = additionalFieldName
                                        }
                                    }
                                }
                            }
                        }
                    } else { // add check for PCL as else if //Get PCL by questionId? each list should be unique? can avoid the account //Might be easier to make a new question
                        // and feed it into the tag above - questionInStyle
                        PrivateClassificationList pcl = privateClassificationListService.getPrivateClassificationListFromQuestionId(additionalQuestionId)
                        if (pcl) {
                            toRender = "${toRender}<select class='lockableDatasetQuestion' style=\"width: 100px\" name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" data-questionId=\"${additionalQuestionId}\" data-resourceId=\"${resourceId}\">"
                            pcl.answers.each ({
                                toRender = "${toRender}<option value=\"${it.getKey()}\" ${answer?.toString()?.equalsIgnoreCase(it.key) ? "selected='selected'" : ""}>${it.getValue()}</option>"
                            })
                            toRender = "${toRender}</select>"
                            nameToRemove = additionalFieldName
                        }
                    }
                }
            }
        }
        render([toRender: toRender, nameToRemove: nameToRemove, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def rerenderVolumeFractionLink() {
        String toRender = ""
        String resourceId = params.resourceId
        String selectedUnit = params.selectedUnit
        String sectionId = params.sectionId
        String questionId = params.questionId
        String queryId = params.queryId

        if (resourceId && selectedUnit && sectionId && questionId) {
            String additionalFieldName = "${sectionId}.${questionId}_additional_specialRuleId${resourceId ? '.' + resourceId : ''}"
            String additionalFieldId = "${sectionId}.${questionId}_additional_specialRuleId"
            Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, null, Boolean.TRUE)

            if (resource) {
                Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(Constants.APPLICATION_ID, Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())
                Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryConfig?.value, true)
                Question additionalQuestion = additionalQuestionsQuery?.getAllQuestions()?.find({ Question q -> q.questionId == "specialRuleId" })

                if (additionalQuestion) {
                    List<QuestionAnswerChoice> filteredChoises = questionService.getFilteredChoices(null, null, resource, additionalQuestion)?.findAll({ !it.allowedUnits||it.allowedUnits*.toLowerCase().contains(selectedUnit.toLowerCase()) })

                    if (filteredChoises) {
                        toRender = "${toRender}<span class=\"specialRuleSpan\">"
                        if (filteredChoises.size() > 1) {
                            if (additionalQuestion.specifiedSettingDisplayMode?.localizedHoverOverText) {
                                toRender = "${toRender}<a href=\"javascript:\" class=\"serviceLifeButton\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '', '');\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${filteredChoises.first().localizedAnswer}</a>"
                            } else {
                                toRender = "${toRender}<a href=\"javascript:\" class=\"serviceLifeButton\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '', '');\" >${filteredChoises.first().localizedAnswer}</a>"
                            }
                            toRender = "${toRender}<input type=\"hidden\" name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" value=\"${filteredChoises.first().answerId ?: ''}\" />"
                        } else {
                            toRender = "${toRender}${filteredChoises.first().localizedAnswer ?: ''}<input type=\"hidden\" name=\"${additionalFieldName}\" id=\"${additionalFieldId}\" value=\"${filteredChoises.first().answerId ?: ''}\" />"
                        }
                        toRender = "${toRender}</span>"
                    }
                }
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    // Ajax call from average Design
    def averageDesignModal() {
        Map outputmap = [:]
        String originalEntityId = request.JSON?.originalEntityId
        String formId = request.JSON?.formId
        Entity originalEntity = originalEntityId ? entityService.getEntityById(originalEntityId) : null
        if(originalEntity) {
            String patentEntityId = originalEntity?.parentEntityId
            Entity entity = patentEntityId ? entityService.getEntityById(patentEntityId) : null
            if(entity) {
                Map<String, String> designStatuses = DesignStatus.map()
                List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, Constants.IndicatorUse.DESIGN.toString())
                List<Entity> designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())

                LinkedHashMap<String, Object> model = [originalEntity: originalEntity, originalEntityId: originalEntityId, entity: entity,
                                                       designStatuses: designStatuses, designsList: designs,
                                                       formId        : formId]

                model = scopeFactoryService.getServiceImpl(entity?.entityClass)?.addValueScopeToModel(model, indicators ?: [])
                String templateAsString = g.render(template: "/design/averageDesignModal", model: model).toString()
                outputmap = ["template": (templateAsString ?: "")]
            }
        }
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }


    // Ajax call from Modify Design (_designs.gsp)
    def modifyDesignModal() {
        Map outputmap = [:]

        String designId = request.JSON?.designId
        String originalEntityId = request.JSON?.originalEntityId
        def isCopy = request.JSON?.copy

        String formId = request.JSON?.formId

        Entity design = designId ? entityService.getEntityById(designId) : null
        Entity originalEntity = originalEntityId ? entityService.getEntityById(originalEntityId) : null

        String patentEntityId = design ? design.parentEntityId : originalEntity?.parentEntityId
        Entity entity = patentEntityId ? entityService.getEntityById(patentEntityId) : null

        List<String> disabledIndicatorsForOriginal = originalEntity?.disabledIndicators

        Integer currentHighestRiba = entity?.highestChildEntityRibaStage
        Map<Integer, String> ribaStages = com.bionova.optimi.core.Constants.DesignRIBAStages.map()
        ribaStages.put(-1, "entity.form.component")

        String entityClass = entity?.entityClass
        Boolean showRibaStage = EntityClass.BUILDING.toString().equalsIgnoreCase(entityClass) || EntityClass.INFRASTRUCTURE.toString().equalsIgnoreCase(entityClass)

        Map<String, String> designStatuses = com.bionova.optimi.core.Constants.DesignStatus.map()
        List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, Constants.IndicatorUse.DESIGN.toString())

        Boolean hideIndicatorSelect = Boolean.FALSE
        if (indicators?.findAll({!"benchmark".equals(it?.visibilityStatus) && !"visibleBenchmark".equals(it?.visibilityStatus)})?.size() == 0) {
            hideIndicatorSelect = Boolean.TRUE
        }

        def ifcImport = params?.ifcImport
        Query basicQuery = queryService.getBasicQuery()
        Boolean showCalculationClassification = basicQuery?.applicableIndicatorForCalculationClass?.intersect(indicators?.collect({it.indicatorId})) && basicQuery?.calculationClassificationList

        def model = [design: design, originalEntity: originalEntity, originalEntityId: originalEntityId, entity: entity, ifcImport: ifcImport, ribaStages: ribaStages,basicQuery: basicQuery,
                     showRibaStage: showRibaStage, currentHighestRiba:currentHighestRiba, manageable: entity?.manageable, designStatuses: designStatuses,
                     indicators: indicators, hideIndicatorSelect: hideIndicatorSelect, disabledIndicatorsForOriginal: disabledIndicatorsForOriginal,showCalculationClassification:showCalculationClassification,
                     formId : formId, entityClass: entityClass, isCopy: isCopy]

        model = scopeFactoryService.getServiceImpl(entity?.entityClass)?.addValueScopeToModel(model, indicators?:[])

        String templateAsString = g.render(template:  "/design/designModal", model: model).toString()

        outputmap = ["template" : (templateAsString ?: "")]

        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    //ajax call from modifyPeriodModal _periods.jsp
    def modifyPeriodModal() {
        Map outputmap = [:]

        def choosableYears = []
        def calendar = Calendar.instance
        def nextYear = calendar[Calendar.YEAR] + 4
        for (int i = 2005; i <= nextYear; i++) {
            choosableYears.add("${i}")
        }

        String operatingPeriodId = request.JSON?.operatingPeriodId
        def isCopyPeriod = request.JSON?.copyPeriod
        def isModifyPeriod = request.JSON?.modifyPeriod

        Entity operatingPeriod = operatingPeriodId ? entityService.getEntityById(operatingPeriodId) : null
        String parentEntityId = operatingPeriod ? operatingPeriod.parentEntityId : null
        Entity parentEntity = parentEntityId ? entityService.getEntityById(parentEntityId) : null

        def model = [operatingPeriod: operatingPeriod, parentEntity: parentEntity,
                     manageable: parentEntity?.manageable, isCopyPeriod: isCopyPeriod ,isModifyPeriod : isModifyPeriod, childEntity: operatingPeriod , choosableYears: choosableYears, ]


        String templateAsString = g.render(template:  "/operatingPeriod/periodModal", model: model).toString()

        outputmap = ["template" : (templateAsString ?: "")]

        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addNewDesignModal() {
        long now = System.currentTimeMillis()
        Map outputmap = [:]

        String entityId = request.JSON?.entityId
        String formId = request.JSON?.formId
        Entity entity = entityService.getEntityById(entityId)
        String indicatorId = request.JSON?.indicatorId
        Integer currentHighestRiba = entity?.highestChildEntityRibaStage

        log.info("HIGHEST RIBA: ${currentHighestRiba}")

        Map<Integer, String> ribaStages = com.bionova.optimi.core.Constants.DesignRIBAStages.map()
        ribaStages.put(-1, "entity.form.component")
        List<Indicator> selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, Constants.IndicatorUse.DESIGN.toString()) ?: []
/*
        User user = userService.getCurrentUser(true)

        List<String> userIndicatorIds = entity?.userIndicators?.get(user?.id.toString())

        if (userIndicatorIds) {
            if (selectedDesignIndicators) {
                selectedDesignIndicators = selectedDesignIndicators.findAll({
                    userIndicatorIds.contains(it.indicatorId)
                })
            }
        }
*/
        String entityClass = entity?.entityClass
        Boolean showRibaStage = EntityClass.BUILDING.toString().equalsIgnoreCase(entityClass) || EntityClass.INFRASTRUCTURE.toString().equalsIgnoreCase(entityClass)
        Query basicQuery = queryService.getBasicQuery()
        Boolean showCalculationClassification = basicQuery?.applicableIndicatorForCalculationClass?.intersect(selectedDesignIndicators?.collect({it.indicatorId})) && basicQuery?.calculationClassificationList

        Map model = [entity: entity, ribaStages: ribaStages, currentHighestRiba:currentHighestRiba,basicQuery: basicQuery,
                     selectedDesignIndicators:selectedDesignIndicators, formId : formId, indicatorId: indicatorId,showCalculationClassification:showCalculationClassification,
                     entityClass: entityClass, showRibaStage: showRibaStage]

        model = scopeFactoryService.getServiceImpl(entity?.entityClass)?.addValueScopeToModel(model, selectedDesignIndicators?:[])
        String templateAsString = g.render(template:  "/design/designModal", model: model).toString()
        outputmap = ["template" : (templateAsString ?: "")]
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def addNewPeriodModal() {
        long now = System.currentTimeMillis()
        def choosableYears = []
        def calendar = Calendar.instance
        def nextYear = calendar[Calendar.YEAR] + 4

        for (int i = 2005; i <= nextYear; i++) {
            choosableYears.add("${i}")
        }

        Map outputmap = [:]

        String parentEntityId = request.JSON?.entityId
        Entity entity = entityService.getEntityById(parentEntityId)

        Map model = [entity: entity , parentEntity: entity, choosableYears: choosableYears]
        String templateAsString = g.render(template:  "/operatingPeriod/periodModal", model: model).toString()
        outputmap = ["template" : (templateAsString ?: "")]
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def updateProjectScope() {

        try {
            String entityId = request.JSON?.entityId
            String sectionId = request.JSON?.sectionId

            Entity entity = entityId ? entityService.getEntityById(entityId) : null

            if(entity?.scope && sectionId){
                def map = scopeFactoryService.getServiceImpl(entity?.entityClass)?.getMapSectionToProjectScope()
                sectionId = map.find{it.value?.contains(sectionId)}?.key

                if(sectionId){ //????IF sectionId it's not a valid section????
                    if(entity.scope.lcaCheckerList){
                        entity.scope.lcaCheckerList.add(sectionId)
                    }else {
                        entity.scope.lcaCheckerList = [sectionId]
                    }
                    entity.disabledSections = entityService.resolveDisabledSections(entity)
                    entity.save(flush: true, failOnError: true)
                }else {
                    log.error("SectionId is not valid updating ProjectScope from buldingMaterialsQuery page")
                    flashService.setErrorAlert("SectionId is not valid updating ProjectScope from buldingMaterialsQuery page", true)
                }
            }

            render([output: true, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

        } catch(Exception e){
            loggerUtil.error(log, "Exception updateProjectScope", e)
        }

    }

    def downloadAreasTemplate() {

        String pathFile = "/var/www/static/CarbonDesigner_structures.xlsx"

        File f = new File(pathFile)
        String fileName = "CarbonDesigner_structures.xlsx"

        if (f && f.exists()) {
            render(file: f, fileName: fileName, contentType: "application/xls")
        } else {
            loggerUtil.error(log, "Failed to load Excel Areas Template document ")
            render(text: "${message(code: 'simulationTool.template_notFound')}")
        }
    }

    def uploadSimulationToolAreasTemplate() {
        if (request instanceof MultipartHttpServletRequest) {
            def maxSize = 30 * 1024
            def excelFile = request.getFile("file")

            if (!excelFile || excelFile?.empty || excelFile?.getSize() > maxSize) {
                String messageError = g.message(code: "import.file.required")
                if(excelFile?.getSize() > maxSize) messageError = g.message(code: "simulationTool.fileToBig")

                render([error: messageError] as JSON)
            } else {
                try {
                    String regionId = request.getParameter("regionId") ?: ""
                    String indicatorId = request.getParameter("indicatorId") ?: ""
                    Map mapWithInfo = simulationToolService.importSimulationToolAreasTemplate(excelFile)
                    def model = simulationToolService.getModelSimulationUploadTemplate(mapWithInfo, regionId, indicatorId)

                    if(model){
                        String column_areas_div = g.render(template:  "/util/columnConstrunctionGroups", model: model).toString()
                        String building_dimensions_div = g.render(template:  "/util/calculationEarlyPhase", model: model).toString()

                        Map output = [column_areas_div: column_areas_div, building_dimensions_div: building_dimensions_div,
                                      gfa: model.gfa, error: mapWithInfo?.error]
                        render( [data: output] as JSON)
                    } else {
                        String messageError = g.message(code : "simulationTool.error_upload_file")
                        render( [error: messageError] as JSON)
                    }

                } catch (Exception e) {
                    String messageError = g.message(code : "simulationTool.error_upload_file")
                    loggerUtil.error(log, "$messageError", e)
                    render([error: messageError] as JSON)

                }
            }
        } else {
            String messageError = "Cannot import excel file, because invalid request: ${request?.class} (should be StandardMultipartHttpServletRequest)."
            log.info("Upload CD: $messageError")
            messageError =  g.message(code : "simulationTool.error_upload_file")
            render([error: messageError] as JSON)
        }
    }

    def importDatasetsFromEntities() {
        if (params.targetEntityId && params.entityId && params.indicatorId) {
            Entity parentEntity = entityService.readEntity(params.entityId)
            Entity entity = entityService.readEntity(params.targetEntityId)
            List<String> queryName = params.queryName ? [params.queryName] : null
            Boolean mainPageCheck = params.boolean("mainPageCheck")
            Query basicQuery = queryService.getBasicQuery()
            Set<String> calculationClassificationList = basicQuery?.calculationClassificationList?.keySet()
            Boolean indicatorCompatible = basicQuery?.applicableIndicatorForCalculationClass?.contains(params.indicatorId)
            Map<String, Map<String, List<Document>>> copyEntitiesMapped = entityService.getChildrenByEntityClassAndQueryReadyWithCustomClass(parentEntity?.entityClass, entity?.entityClass, parentEntity, [params.indicatorId], params.targetEntityId, calculationClassificationList)
            if (copyEntitiesMapped) {
                String templateAsString = groovyPageRenderer.render(template: "/query/importDatasetsFromDesigns", model: [targetEntityId: params.targetEntityId, basicQuery: basicQuery, mainPageCheck: mainPageCheck,
                                                                                                                          entityId      : params.entityId, queryId: params.queryId, entityClass: entity?.entityClass, indicatorCompatible: indicatorCompatible,
                                                                                                                          indicatorId   : params.indicatorId, originalDesign: entity, queryName: queryName, copyEntitiesMapped: copyEntitiesMapped])
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                flashService.setErrorAlert('No copyEntitiesMapped in importDatasetsFromEntities', true)
                render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            flashService.setErrorAlert("Incorrect params $params", true)
            render([output: '', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def hideDesign() {

        String designId = request.JSON.designId

        try{
            Entity entity = designId ? entityService.getEntityById(designId) : null

            if(entity){
                entity.isHiddenDesign = true
                entity.merge(flush: true, failOnError: true)
                flash.fadeSuccessAlert = g.message(code: 'hide_design_sucessfully', args: [entity.operatingPeriodAndName])
            } else {
                flash.fadeErrorAlert = "Unable to hide design."
            }
        }catch(Exception e){
            loggerUtil.error(log, "Problem saving isHiddenDesign in the design $designId", e)
        } finally {
            render(status: 200, text: 'ok')
        }
    }

    def showHiddenDesign() {

        String parentId = request.JSON.parentId

        try{
            Entity entity = parentId ? entityService.getEntityById(parentId) : null

            if(entity){
                List<Entity> childList = entity.getChildrenByChildEntities()

                childList?.findAll{!it.deleted && it.isHiddenDesign}?.each {
                    it.isHiddenDesign = false
                    it.merge(failOnError: true, flush: true)
                }
            }
        }catch(Exception e){
            loggerUtil.error(log, "Problem saving isHiddenDesign in the design $parentId", e)
        } finally {
            render(status: 200, text: 'ok')
        }
    }

    def showHiddenDesignByIds() {
        List<String> childEntityIds = (request?.JSON.childEntityIds as ArrayList<String>) ?: []

        try{
            List<Entity> childList = entityService.getEntitiesFromIds(childEntityIds)

            childList?.each {
                it.isHiddenDesign = false
                it.merge(failOnError: true, flush: true)
            }
        }catch(Exception e){
            loggerUtil.error(log, "Problem saving isHiddenDesign in the design $parentId", e)
        } finally {
            render(status: 200, text: 'ok')
        }
    }

    def dumpAllLcaResources() {

        List<Resource> resourceList = Resource.findAllByApplicationId("LCA")

        if(resourceList){

            StringBuilder s = new StringBuilder()
            s.append("resourceId,resourceGroup,epdProgram,epdNumber,isPep\n")
            resourceList.each {
                String tmp = "${it.resourceId},${it.resourceGroup?.toString()?.replaceAll(",", " - ")},${it.epdProgram?.replaceAll(",", " - ")},${it.epdNumber?.replaceAll(",", " - ")},${it.filterValue?.contains("isPEP") ? true : ""}\n"
                s.append(tmp)
            }

            String file = s.toString()
            def stream = new ByteArrayInputStream(file.getBytes())

            render file: stream, fileName: "dumpAllLcaResources.csv", contentType: "application/csv"

        }

    }

    def downloadFile() {
        String fileName = params.fileName
        String filePath = params.filePath

        if (!filePath && fileName) {
            filePath = zohoService.getFilePathToZohoDataRequest(fileName)
        }

        if(filePath){
            File file = new File(filePath)

            if (file && file.exists()) {
                InputStream contentStream

                try {
                    response.setHeader "Content-disposition", "attachment; filename=${file.name}"
                    response.setHeader("Content-Length", "${file.size()}")
                    response.setContentType("text/plain")
                    contentStream = file.newInputStream()
                    response.outputStream << contentStream
                    webRequest.renderView = false
                } finally {
                    IOUtils.closeQuietly(contentStream)
                }
            } else {
                loggerUtil.error(log, "Failed to load Zoho data request file")
                render(text: "File not Found")
            }
        }
    }

    def getCalculationFormula() {
        String toRender = ""
        User user = userService.getCurrentUser(true)

        if (user) {
            String entityId = params.entityId
            String datasetId = params.datasetId
            String indicatorId = params.indicatorId
            String resultCategoryId = params.resultCategoryId
            String calculationRuleId = params.calculationRuleId

            if (entityId && datasetId && indicatorId && resultCategoryId && calculationRuleId) {
                Entity entity = entityService.getEntityById(entityId)

                if (entity) {
                    Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                    Entity parentEntity = entity.parentById
                    List<CalculationRule> resolvedCalculationRules = indicator?.getResolveCalculationRules(parentEntity)

                    CalculationRule calculationRule = resolvedCalculationRules?.find({it.calculationRuleId?.equals(calculationRuleId)})
                    List<CalculationRule> rulesForDynamicResult = []

                    if (calculationRule?.virtual) {
                        List<CalculationRule> neededVirtualRules = resolvedCalculationRules.findAll({calculationRule.virtual.contains(it.calculationRuleId)})

                        if (neededVirtualRules) {
                            neededVirtualRules.each { CalculationRule nestedVirtualRule ->
                                if (nestedVirtualRule.virtual) {
                                    List<CalculationRule> neededVirtualRules2 = resolvedCalculationRules.findAll({nestedVirtualRule.virtual.contains(it.calculationRuleId)})

                                    if (neededVirtualRules2) {
                                        rulesForDynamicResult.add(nestedVirtualRule)
                                        rulesForDynamicResult.addAll(neededVirtualRules2)
                                    }
                                } else {
                                    rulesForDynamicResult.add(nestedVirtualRule)
                                }
                            }
                            rulesForDynamicResult.add(calculationRule)
                        }
                    } else {
                        rulesForDynamicResult.add(calculationRule)
                    }

                    List<ResultCategory> resolvedResultCategories = indicator?.getResolveResultCategories(parentEntity)

                    ResultCategory resultCategory = resolvedResultCategories?.find({it.resultCategoryId?.equals(resultCategoryId)})
                    List<ResultCategory> categoriesForDynamicResult = []

                    if (resultCategory?.virtual) {
                        List<ResultCategory> neededVirtualCategories = resolvedResultCategories.findAll({resultCategory.virtual.contains(it.resultCategoryId)})

                        if (neededVirtualCategories) {
                            neededVirtualCategories.each { ResultCategory nestedVirtualCategory ->
                                if (nestedVirtualCategory.virtual) {
                                    List<CalculationRule> neededVirtualCategories2 = resolvedResultCategories.findAll({nestedVirtualCategory.virtual.contains(it.resultCategoryId)})

                                    if (neededVirtualCategories2) {
                                        categoriesForDynamicResult.add(nestedVirtualCategory)
                                        categoriesForDynamicResult.addAll(neededVirtualCategories2)
                                    }
                                } else {
                                    categoriesForDynamicResult.add(nestedVirtualCategory)
                                }
                            }
                            categoriesForDynamicResult.add(resultCategory)
                        }
                    } else {
                        categoriesForDynamicResult.add(resultCategory)
                    }

                    Dataset dataset = entity.datasets?.find({datasetId.equals(it.manualId)})

                    CalculationResult calculationResult = newCalculationServiceProxy.dynamicCalculationFormulaResult(entity, parentEntity, indicator, rulesForDynamicResult, categoriesForDynamicResult, dataset, resultCategoryId, calculationRuleId)

                    if (calculationResult && dataset) {
                        String calculationFormula = calculationResult.calculationResultDatasets?.get(datasetId)?.calculationFormula

                        if (calculationFormula) {
                            toRender = "${toRender}<strong>${message(code:"see_calculation.heading")}</strong><br />"
                            toRender = "${toRender}${message(code:"see_calculation.body")}<br /><br />"

                            Resource originalResource = datasetService.getResource(dataset)
                            Resource calculationResource
                            String calculationResourceId = calculationResult.calculationResultDatasets?.get(datasetId)?.resourceId
                            String calculationProfileId = calculationResult.calculationResultDatasets?.get(datasetId)?.profileId

                            if (originalResource) {
                                if (originalResource.resourceId && calculationResourceId && !originalResource.resourceId.equals(calculationResourceId)) {
                                    calculationResource = optimiResourceService.getResourceWithParams(calculationResourceId, calculationProfileId)
                                }
                            }

                            if (originalResource) {
                                toRender = "${toRender}<strong>Resource(s):</strong><br />"
                                toRender = "${toRender}${originalResource.staticFullName}<br />"

                                if (calculationResource) {
                                    String random = UUID.randomUUID().toString()
                                    toRender = "${toRender}${calculationResource.staticFullName} ${opt.renderDataCardBtn(indicatorId: indicatorId, resourceId: calculationResource?.resourceId, profileId: calculationResource?.profileId, childEntityId: entity?.id?.toString(), showGWP: true, infoId: random)}<br />"
                                }
                                toRender = "${toRender}<br />"
                            }

                            calculationFormula.tokenize(",").each { String calculationStep ->
                                toRender = "${toRender}${calculationStep}<br />"
                            }

                        }
                    }
                }
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getLicenseMenu() {
        User user = userService.getCurrentUser()
        String toRender = ""
        Boolean reloadOnFloatingStatusChange = params.boolean("reloadOnFloatingStatusChange")
        Boolean allowChangeFloatingStatus = params.boolean("allowChangeFloatingStatus")

        if (user) {
            Account account = userService.getAccountForMenu(user)
            ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

            if (allowChangeFloatingStatus) {
                toRender = opt.renderLicenseMenu(account: account, channelFeature: channelFeature, userForFloatingLicense: user, loggedInUserObject: user, user: user, reloadOnFloatingStatusChange: reloadOnFloatingStatusChange)
            } else {
                toRender = opt.renderLicenseMenu(account: account, channelFeature: channelFeature, loggedInUserObject: user, user: user, reloadOnFloatingStatusChange: reloadOnFloatingStatusChange)
            }
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getAdditionalQuestionChoices() {
        String toRender = ""
        String additionalQuestionId = params.additionalQuestionId
        String resourceId = params.resourceId
        String mainQuestionAnswer = params.mainQuestionAnswer
        String indicatorId = params.indicatorId
        String queryId = params.queryId

        if (additionalQuestionId) {
            Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())
            Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryConfig?.value, true)

            if (additionalQuestionsQuery) {
                Question additionalQuestion = additionalQuestionsQuery.allQuestions?.find({ Question q -> q.questionId == additionalQuestionId})

                if (additionalQuestion) {
                    Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                    List<Document> resources = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)?.findAll({it.active})

                    if (resources && additionalQuestionId == com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                        resources = resources.findAll({it.areas?.contains(mainQuestionAnswer)})
                    }

                    if (resources) {
                        toRender = "${toRender}<table><tr><td><strong>${additionalQuestion.localizedQuestion}</strong></td></tr>"
                        if (additionalQuestionId == com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                            resources.each {
                                toRender = "${toRender}<tr><td>${it.profileNameEN ?: it.profileId} - ${it.staticFullName}</td></tr>"
                            }
                        } else {
                            resources.each {
                                toRender = "${toRender}<tr><td>${it.localizedName}</td></tr>"
                            }
                        }
                        toRender = "${toRender}</table>"
                    } else {
                        Resource resource = optimiResourceService.getResourceWithParams(resourceId, null)
                        List<QuestionAnswerChoice> choises = questionService.getFilteredChoices(null, null, resource, null, true, additionalQuestion)

                        if (choises) {
                            toRender = "${toRender}<table><tr><td><strong>${additionalQuestion.localizedQuestion}</strong></td></tr>"
                            choises.each {
                                toRender = "${toRender}<tr><td>${it.localizedAnswer}</td></tr>"
                            }
                            toRender = "${toRender}</table>"
                        } else {
                            toRender = "${message(code: "resource.sourcelisting_no_information_available")}"
                        }
                    }
                } else {
                    toRender = "${message(code: "resource.sourcelisting_no_information_available")}"
                }
            } else {
                toRender = "${message(code: "resource.sourcelisting_no_information_available")}"
            }
        } else {
            toRender = "${message(code: "resource.sourcelisting_no_information_available")}"
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def convertResourceRowQuantity() {
        String toRender = "0"
        String resourceId = params.resourceId
        String currentUnit = params.currentUnit
        String targetUnit = params.targetUnit
        String quantityString = params.quantity
        String thickness_mmString = params.thickness_mm
        String thickness_inString = params.thickness_in

        if (resourceId && currentUnit && targetUnit && quantityString) {
            Double quantity
            try {
                quantity = quantityString.replace(',','.').toDouble()
            } catch (Exception e) {
                // Unable to parse quantity
            }

            if (quantity) {
                Resource resource = optimiResourceService.getResourceWithParams(resourceId, null, true)
                if (resource) {
                    Double thickness_mm = thickness_mmString && DomainObjectUtil.isNumericValue(thickness_mmString) ? DomainObjectUtil.convertStringToDouble(thickness_mmString) :
                            thickness_inString && DomainObjectUtil.isNumericValue(thickness_inString) ? DomainObjectUtil.convertStringToDouble(thickness_inString) * 25.4 : null
                    Double convertedQuantity = unitConversionUtil.convertQuantity(resource, quantity, thickness_mm, currentUnit, targetUnit)

                    if (convertedQuantity) {
                        //Not sure what is the logic of % 1 == 0 here but guess the purpose is to find
                        // if the number has significant figure after comma?
                        if (convertedQuantity % 1 == 0) {
                            toRender = "${convertedQuantity.round()}"
                        } else {
                            toRender = resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(userService.getCurrentUser(), convertedQuantity, true)
                        }
                    } else {
                        toRender = "error"
                    }
                } else {
                    toRender = "error"
                }
            } else {
                toRender = "error"
            }
        } else {
            toRender = "error"
        }
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def renderRowGroupingView() {
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        String queryId = request.JSON.queryId
        String sectionId = request.JSON.sectionId
        String questionId = request.JSON.questionId
        List<String> manualIds = request.JSON.manualIds
        String existingParentConstructionDatasetId = request.JSON.existingParentConstructionDatasetId

        Boolean createConstructionsAllowed = request.JSON.createConstructionsAllowed?.asBoolean()
        Boolean publicGroupAllowed = request.JSON.publicGroupAllowed?.asBoolean()
        String tableHeading = ""
        String tableConstructionRow = ""
        String tableData = ""
        if (entityId) {
            Entity design = entityService.getEntityById(entityId, session)

            if (design) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                Query query = queryService.getQueryByQueryId(queryId, true)
                QuerySection section = query.sections?.find({ it.sectionId.equals(sectionId) })
                Question question = query.getQuestionsBySection(sectionId)?.find({ it.questionId.equals(questionId) })
                Entity parentEntity = design.parentById
                List<Question> additionalQuestions = question.defineAdditionalQuestions(indicator, section, null, parentEntity)
                def datasets = design.datasets?.findAll({manualIds?.contains(it.manualId)})
                EolProcessCache eolProcessCache = EolProcessCache.init()
                tableHeading = "<tr><th class=\"text_at_top\">${message(code: 'query.material')}</th>"
                if(existingParentConstructionDatasetId) {
                    tableConstructionRow = datasetService.getRenderGroupingDatasetHeader(question,query, section, additionalQuestions,
                            parentEntity, design,indicator,existingParentConstructionDatasetId,createConstructionsAllowed,publicGroupAllowed)
                }
                if (question.quantity) {
                    tableHeading = tableHeading + "<th class=\"right-align text_at_top\">${message(code: 'query.quantity')}</th><th class=\"text_at_top\">${message(code:'entity.show.unit')}</th>"
                }

                additionalQuestions?.each { Question additionalQuestion ->
                    tableHeading = tableHeading + "<th class=\"right-align text_at_top\">${additionalQuestion.localizedQuestion}</th>"
                }
                tableHeading = tableHeading + "</tr>"
                if(datasets){
                    datasets.each { Dataset d ->
                        tableData = tableData + datasetService.getRenderGroupingDatasetRow(d, question, additionalQuestions, parentEntity?.countryResource, parentEntity, indicator, sectionId, eolProcessCache)
                    }
                }
                tableData = tableData + "</table>"
            }
        }
        render([tableHeading: tableHeading, tableData: tableData, tableConstructionRow:tableConstructionRow,(flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def renderRowGroupingRow() {
        String toRender = ""
        String error
        String manualId = request.JSON.manualId
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        String queryId = request.JSON.queryId
        String sectionId = request.JSON.sectionId
        String questionId = request.JSON.questionId
        List<String> alreadyRenderedDatasets = request.JSON.alreadyRenderedDatasets

        if (entityId && manualId) {
            if (alreadyRenderedDatasets?.contains(manualId)) {
                error = "Group already contains this row."
            } else if (alreadyRenderedDatasets?.size() > 20) {
                error = "Maximum size of a group reached (20/20)"
            } else {
                Entity entity = entityService.getEntityById(entityId, session)

                if (entity) {
                    Dataset dataset = entity.datasets?.find({manualId.equals(it.manualId)})

                    if (dataset) {
                        if (!dataset.uniqueConstructionIdentifier && !dataset.parentConstructionId) {
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                            Query query = queryService.getQueryByQueryId(queryId, true)
                            QuerySection section = query.sections?.find({ it.sectionId.equals(sectionId) })
                            Question question = query.getQuestionsBySection(sectionId)?.find({ it.questionId.equals(questionId) })
                            Entity parentEntity = entity.parentById
                            def additionalQuestions = question.defineAdditionalQuestions(indicator, section, null, parentEntity)

                            toRender = toRender + datasetService.getRenderGroupingDatasetRow(dataset, question, additionalQuestions, parentEntity?.countryResource, parentEntity, indicator, sectionId, null)
                        } else {
                            error = "Row is already part of a construction."
                        }
                    } else {
                        error = "Please save the material first before trying to add it to a group."
                    }
                }
            }
        }
        render([tableData: toRender, error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def createConstructionFromParams() {
        User user = userService.getCurrentUser()
        boolean created = false
        String entityId = request.JSON.entityId
        List<String> manualIds = request.JSON.manualIds
        String constructionName = request.JSON.constructionName
        String constructionQuantityString = request.JSON.constructionQuantity
        String constructionUnit = request.JSON.constructionUnit
        String constructionComment = request.JSON.constructionComment
        String queryId = request.JSON.queryId
        String sectionId = request.JSON.sectionId
        String questionId = request.JSON.questionId
        String accountId = request.JSON.accountId
        String indicatorId = request.JSON.indicatorId
        String subType = request.JSON.subType
        Boolean publicConstruction = request.JSON.publicConstruction
        String showInMappingQuestionId = request.JSON.showInMappingQuestionId
        String showInMappingQuestionAnswer = request.JSON.showInMappingQuestionAnswer
        String privateClassificationQuestionId = request.JSON.privateClassificationQuestionId
        String privateClassificationQuestionAnswer = request.JSON.privateClassificationQuestionAnswer
        String brandImageId = request.JSON.brandImageId
        String existingParentConstructionDatasetId = request.JSON.existingParentConstructionDatasetId
        String productDescription = request.JSON.productDescription

        if (user && entityId && manualIds && constructionName && constructionQuantityString && constructionUnit && queryId && sectionId && questionId) {
            Double constructionQuantity = DomainObjectUtil.isNumericValue(constructionQuantityString) ? DomainObjectUtil.convertStringToDouble(constructionQuantityString) : null
            Entity entity = entityService.getEntityById(entityId, session)

            if (entity && constructionQuantity) {
                Entity parentEntity = entity?.parentById
                String projectCountry = parentEntity?.countryResourceResourceId
                List<Dataset> datasets = entity.datasets?.findAll({manualIds.contains(it.manualId)})?.toList()
                Dataset dataset = null
                if (existingParentConstructionDatasetId) {
                    dataset = entity.datasets?.find({it.manualId == existingParentConstructionDatasetId})
                }
                if (datasets) {
                    Boolean isImperial = unitConversionUtil.isImperialUnit(constructionUnit)
                    String unit
                    Double quantity

                    if (isImperial) {
                        unit = unitConversionUtil.transformImperialUnitToEuropeanUnit(constructionUnit)
                        quantity = unitConversionUtil.doConversion(constructionQuantity, null, constructionUnit, null, null, null, null, null, null, unit)
                    } else {
                        unit = constructionUnit
                        quantity = constructionQuantity
                    }

                    if (accountId) {
                        Account account = accountService.getAccount(accountId)
                        Boolean isMainUser = account.mainUserIds?.contains(user.id.toString())
                        List<User> mainUsers = accountService.getMainUsers(account.mainUserIds)

                        if (account && mainUsers) {
                            String createdDatasetManualId = null
                            Query constructionCreationQuery = queryService.getQueryByQueryId(Query.QUERYID_CONSTRUCTION_CREATOR, true)
                            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
                            String classificationParamId = indicator.groupingClassificationParamId
                            Question classificationParameterQuestion = (Question) constructionCreationQuery?.getAllQuestions()?.find({ com.bionova.optimi.core.Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS.equals(it.questionId) })
                            QuestionAnswerChoice classificationParams = classificationParameterQuestion?.choices?.find({it.answerId == classificationParamId})
                            if (classificationParams) {
                                Map<String, String> resourceParameters = classificationParams.resourceParameters


                                List<Dataset> datasetsForConstruction = []

                                datasets.each { Dataset d ->
                                    Dataset copy = datasetService.createCopy(d)
                                    datasetService.convertDatasetToMetric(copy, null, quantity)
                                    copy.questionId = classificationParams.componentQuestionId
                                    copy.sectionId = "constructionComponents"
                                    copy.queryId = Query.QUERYID_CONSTRUCTION_CREATOR

                                    if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                        if (copy.additionalQuestionAnswers) {
                                            copy.additionalQuestionAnswers.put((showInMappingQuestionId), showInMappingQuestionAnswer)
                                        } else {
                                            copy.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                        }
                                    }
                                    if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                        if (copy.additionalQuestionAnswers) {
                                            copy.additionalQuestionAnswers.put((privateClassificationQuestionId), privateClassificationQuestionAnswer)
                                        } else {
                                            copy.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                        }
                                    }
                                    if (copy?.constructionValue) {
                                        copy.constructionValue = null
                                    }
                                    datasetsForConstruction.add(copy)
                                }
                                ConstructionGroup group = constructionService.getConstructionGroupByAccountId(account.id.toString())

                                if (!group) {
                                    group = new ConstructionGroup()
                                    group.groupId = UUID.randomUUID().toString()
                                    group.name = account?.companyName
                                    group.privateAccountId = account.id.toString()
                                    group = group.save(flush: true, failOnError: true)
                                }

                                if (group) {
                                    Construction construction = new Construction()
                                    construction.importFile = "Manually added"
                                    construction.constructionId = "constructionId${new ObjectId().toString()}".toString()
                                    if (isMainUser) {
                                        construction.locked = true
                                    }
                                    construction.constructionGroup = group.groupId
                                    construction.nameEN = constructionName
                                    construction.unit = unit
                                    construction.privateConstructionAccountId = account.id.toString()
                                    construction.classificationParamId = classificationParams.answerId
                                    construction.classificationQuestionId = com.bionova.optimi.core.Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS
                                    construction.resourceIds = datasets.collect({it.resourceId})
                                    construction.constructionType = com.bionova.optimi.core.Constants.STRUCTURE_CT
                                    construction.productDescription = productDescription ?: constructionComment

                                    construction.country = projectCountry

                                    if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                        construction.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                    }

                                    if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                        construction.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                    }

                                    if (brandImageId) {
                                        construction.brandName = accountImagesService.getAccountImage(brandImageId)?.name
                                        construction.brandImageId = brandImageId
                                    }

                                    if (isImperial) {
                                        construction.imperialUnit = constructionUnit
                                    }
                                    if(subType){
                                        construction.resourceSubType = subType
                                    }
                                    construction = construction.save(flush: true, failOnError: true)

                                    constructionService.createConstructionResource(construction, resourceParameters)

                                    String constructionIdentifier = construction.id.toString() + UUID.randomUUID().toString()

                                    construction.datasets = datasetsForConstruction
                                    construction = construction.save(flush: true, failOnError: true)

                                    Resource mirrorResource = constructionService.getMirrorResourceObject(construction)
                                    log.info("mirrorResource ${mirrorResource?.resourceId}")

                                    if (mirrorResource) {
                                        if (!dataset) {
                                            dataset = new Dataset() // The group dataset
                                        }
                                        dataset.manualId = existingParentConstructionDatasetId ?: new ObjectId().toString()
                                        createdDatasetManualId = dataset.manualId // To flash green for user after creation
                                        dataset.resourceId = mirrorResource.resourceId
                                        dataset.answerIds = ["${constructionQuantity}"]
                                        dataset.quantity = constructionQuantity
                                        dataset.uniqueConstructionIdentifier = constructionIdentifier
                                        dataset.queryId = queryId
                                        dataset.sectionId = sectionId
                                        dataset.questionId = questionId
                                        dataset.userGivenUnit = constructionUnit
                                        dataset.groupingDatasetName = constructionName

                                        if (constructionComment) {
                                            dataset.additionalQuestionAnswers = ["comment": constructionComment]
                                        }
                                        if (productDescription) {
                                            dataset.additionalQuestionAnswers = ["productDescription": productDescription]
                                        }

                                        if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                            if (dataset.additionalQuestionAnswers) {
                                                dataset.additionalQuestionAnswers.put(showInMappingQuestionId, showInMappingQuestionAnswer)
                                            } else {
                                                dataset.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                            }
                                        }
                                        if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                            if (dataset.additionalQuestionAnswers) {
                                                dataset.additionalQuestionAnswers.put(privateClassificationQuestionId, privateClassificationQuestionAnswer)
                                            } else {
                                                dataset.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                            }
                                        }
                                        entity.datasets.removeIf({manualIds.contains(it.manualId) || it.manualId == existingParentConstructionDatasetId})
                                        entity.datasets.add(dataset)

                                        datasets.each { Dataset d ->
                                            d.parentConstructionId = construction.id.toString()
                                            d.uniqueConstructionIdentifier = constructionIdentifier
                                            if (d.quantity) {
                                                d.constructionValue = "${d.quantity / quantity}".toString()
                                            }
                                            if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                                if (d.additionalQuestionAnswers) {
                                                    d.additionalQuestionAnswers.put((showInMappingQuestionId), showInMappingQuestionAnswer)
                                                } else {
                                                    d.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                                }
                                            }
                                            if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                                if (d.additionalQuestionAnswers) {
                                                    d.additionalQuestionAnswers.put((privateClassificationQuestionId), privateClassificationQuestionAnswer)
                                                } else {
                                                    d.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                                }
                                            }
                                            // new (or edited) construction should have all linked constituents
                                            constructionService.relinkConstituentDatasetToConstruction(d)
                                        }
                                        entity.datasets.addAll(datasets)
                                        entity.save(flush: true)

                                        if (!isMainUser) {
                                            List<String> recipients = mainUsers.collect { it.username }
                                            String link = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                                                    controller: "construction", action: "index",
                                                    params: [accountId: account?.id])

                                            String htmlBody = "" +
                                                    "Organization ${account?.companyName} user: ${user.name} has created a new private construction group and it's pending approval.<br/>" +
                                                    "Construction name: ${mirrorResource.nameEN}<br/><br/>" +
                                                    "You can manage the organization private construction through this link:<br/>" +
                                                    "<a href=\"${link}\" target=\"_blank\">Private constructions</a><br/>"

                                            optimiMailService.sendMail({
                                                to recipients
                                                from emailConfiguration.contactEmail
                                                subject("One Click LCA: New private construction is pending approval")
                                                html htmlBody
                                            }, "${recipients.join(", ")}")
                                        }
                                        created = true
                                    }
                                }

                                if (publicConstruction) {
                                    ConstructionGroup publicGroup = constructionService.getConstructionGroupByName(Constants.PUBLIC_CONSTRUCTION_GROUP)

                                    if (!publicGroup) {
                                        publicGroup = new ConstructionGroup()
                                        publicGroup.groupId = UUID.randomUUID().toString()
                                        publicGroup.uneditable = true
                                        publicGroup.name = Constants.PUBLIC_CONSTRUCTION_GROUP
                                        publicGroup = publicGroup.save(flush: true, failOnError: true)
                                    }

                                    if (publicGroup) {
                                        Construction construction = new Construction()
                                        construction.importFile = "Manually added"
                                        construction.constructionId = "constructionId${new ObjectId().toString()}".toString()
                                        //construction.locked = true
                                        construction.constructionGroup = publicGroup.groupId
                                        construction.nameEN = constructionName
                                        construction.unit = unit
                                        construction.classificationParamId = classificationParams.answerId
                                        construction.classificationQuestionId = com.bionova.optimi.core.Constants.CONSTRUCTION_CLASSIFICATION_PARAMETERS
                                        construction.resourceIds = datasets.collect({it.resourceId})
                                        construction.constructionType = com.bionova.optimi.core.Constants.STRUCTURE_CT
                                        construction.productDescription = productDescription ?: constructionComment

                                        construction.country = projectCountry

                                        if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                            construction.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                        }

                                        if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                            construction.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                        }

                                        if (brandImageId) {
                                            construction.brandName = accountImagesService.getAccountImage(brandImageId)?.name
                                            construction.brandImageId = brandImageId
                                        }

                                        if (isImperial) {
                                            construction.imperialUnit = constructionUnit
                                        }
                                        construction = construction.save(flush: true, failOnError: true)

                                        constructionService.createConstructionResource(construction, resourceParameters)

                                        construction.datasets = datasetsForConstruction
                                        construction.save(flush: true, failOnError: true)
                                    }
                                }
                                if (created) {
                                    if (createdDatasetManualId) {
                                        session?.setAttribute("newlyAddedDatasets", [createdDatasetManualId])
                                    }
                                    session?.setAttribute("privateConstructionCreated", "Private construction ${constructionName} successfully created for ${account?.companyName}")
                                }
                            } else {
                                flash.errorAlert = "No groupingClassificationParamId found from indicator"
                            }
                        } else {
                            flash.errorAlert = "Organization doesnt have a main user."
                        }
                    } else {
                        Resource dummyGroupResource = optimiResourceService.getDummyGroupingResource()

                        if (dummyGroupResource) {
                            String constructionIdentifier = dummyGroupResource.resourceId + UUID.randomUUID().toString()
                            if (!dataset) {
                                dataset = new Dataset() // The group dataset
                            }
                            dataset.manualId = existingParentConstructionDatasetId ?: new ObjectId().toString()
                            dataset.resourceId = dummyGroupResource.resourceId
                            dataset.answerIds = ["${constructionQuantity}"]
                            dataset.quantity = constructionQuantity
                            dataset.uniqueConstructionIdentifier = constructionIdentifier
                            dataset.queryId = queryId
                            dataset.sectionId = sectionId
                            dataset.questionId = questionId
                            dataset.userGivenUnit = constructionUnit
                            dataset.groupingDatasetName = constructionName
                            dataset.productDescription = productDescription ?: constructionComment

                            if (constructionComment) {
                                dataset.additionalQuestionAnswers = ["comment": constructionComment]
                            }
                            if (productDescription) {
                                dataset.additionalQuestionAnswers = ["productDescription": productDescription]
                            }

                            if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put(showInMappingQuestionId, showInMappingQuestionAnswer)
                                } else {
                                    dataset.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                }
                            }

                            if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                if (dataset.additionalQuestionAnswers) {
                                    dataset.additionalQuestionAnswers.put(privateClassificationQuestionId, privateClassificationQuestionAnswer)
                                } else {
                                    dataset.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                }
                            }

                            datasets.each { Dataset d ->
                                d.parentConstructionId = dummyGroupResource.resourceId
                                d.uniqueConstructionIdentifier = constructionIdentifier
                                if (d.quantity) {
                                    d.constructionValue = "${d.quantity / quantity}".toString()
                                }
                                if (showInMappingQuestionAnswer && showInMappingQuestionId) {
                                    if (d.additionalQuestionAnswers) {
                                        d.additionalQuestionAnswers.put((showInMappingQuestionId), showInMappingQuestionAnswer)
                                    } else {
                                        d.additionalQuestionAnswers = [(showInMappingQuestionId): showInMappingQuestionAnswer]
                                    }
                                }
                                if (privateClassificationQuestionAnswer && privateClassificationQuestionId) {
                                    if (d.additionalQuestionAnswers) {
                                        d.additionalQuestionAnswers.put((privateClassificationQuestionId), privateClassificationQuestionAnswer)
                                    } else {
                                        d.additionalQuestionAnswers = [(privateClassificationQuestionId): privateClassificationQuestionAnswer]
                                    }
                                }
                                // new (or edited) construction should have all linked constituents
                                constructionService.relinkConstituentDatasetToConstruction(d)
                            }
                            entity.datasets.removeIf({manualIds.contains(it.manualId) || it.manualId == existingParentConstructionDatasetId})
                            entity.datasets.add(dataset)
                            entity.datasets.addAll(datasets)
                            entity.save(flush: true)
                            session?.setAttribute("newlyAddedDatasets", [dataset.manualId])
                            created = true
                        }
                    }
                }
            }
        }
        render text: created
    }

    def renderSendMeDataModal() {
        String queryId = request.JSON.queryId
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        Boolean isPlanetaryOnly = request.JSON.isPlanetaryOnly == "true" ? true : false

        List<Resource> countriesAsDoc
        List<ResourceType> subTypes = []
        List<String> manufacturers = []
        Entity entity
        String basicQueryTable =""

        if(queryId && entityId){
            entity = entityService.getEntityById(entityId, session)
            Query query = queryService.getQueryById(queryId)
            List<String> questionsResourceGroups = query?.getAllQuestions()?.findAll({ Question question -> question?.showTypeahead })?.findResults({it.resourceGroups ?: null})?.flatten()?.unique()
            List<String> resourceSubTypesStrings = optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, null, "resourceSubType", null)
            resourceSubTypesStrings.each{String resouceSubType ->
                ResourceType subTypeObj = resourceTypeService.getResourceTypeObjectBySubType(resouceSubType)
                if(subTypeObj && subTypeObj.subTypeGroup == "material"){
                    subTypes.add(subTypeObj)
                }
            }
            manufacturers = optimiResourceService.getUniqueResourceAttributeValues(questionsResourceGroups, null, "manufacturer", ['resourceType':subTypes?.collect({it.resourceType}) as List])

            countriesAsDoc = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)
            Query basicQuery = queryService.getQueryByQueryId("basicQuery", true)
            if(basicQuery){
                List<Dataset> basicQueryDatasets= entity.datasets?.toList()?.findAll({ basicQuery.queryId.equals(it.queryId) })
                List<Question> allQuestionsBasicQuery = basicQuery.allQuestions

                basicQueryDatasets?.each {Dataset d ->

                    String questionName = allQuestionsBasicQuery?.find({it.questionId?.equalsIgnoreCase(d.questionId)})?.localizedQuestion
                    String userAnswer = ''
                    String name = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(d.answerIds[0], null, null))
                    if(!name){
                        userAnswer = d.answerIds[0].toString()
                    }else {
                        userAnswer = name
                    }
                        basicQueryTable += '<strong>'+ questionName +'</strong>: ' + userAnswer + '<br/>'
                }
            }
        }
        String templateAsString = g.render(template: "/entity/sendMeData", model: [countries: countriesAsDoc, isPlanetaryOnly:isPlanetaryOnly,subTypes:subTypes?.sort{resourceTypeService.getLocalizedName(it)}, entity: entity, indicatorId: indicatorId, basicQueryTable:basicQueryTable, manufacturers:manufacturers,user:userService.getCurrentUser()]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    /**
     * Renders re2020RequestDataModal to request data from INIES
     */
    def renderRe2020RequestDataModal() {
        String templateAsString = g.render(template: "/entity/modal/re2020RequestData", model: [user: userService.getCurrentUser()]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def simpleResourceSearch(){
        String countryId = request.JSON?.countryId
        String subtypeId = request.JSON?.subtypeId
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        Boolean isPlanetaryOnly = request.JSON.isPlanetaryOnly == "true" ? true : false
        List<Resource> resourcesFound
        User user = userService.getCurrentUser()
        if(countryId && subtypeId){
            resourcesFound = optimiResourceService.getResourcesByRulesMap(['areas': countryId, 'resourceSubType': subtypeId],Boolean.FALSE,user.internalUseRoles)
        }

        String renderOption = "<tr><th>No.</th><th>${message(code: 'resource.name')}</th><th></th></tr>"
        if(resourcesFound){
            int i = 1
            resourcesFound.each {Resource r ->
                renderOption = "${renderOption}<tr><td>${i}</td><td id='${r.id}'>${r.staticFullName}</td><td>"
                if((r.enabledPurposes == "planetary" && isPlanetaryOnly) || !isPlanetaryOnly){
                    String randomOriginal = UUID.randomUUID().toString()
                    renderOption = "${renderOption}${opt.renderDataCardBtn(indicatorId: indicatorId, resourceId: r?.resourceId, profileId: r?.profileId, childEntityId: entityId, showGWP: true, infoId: randomOriginal)}</td><td><a href='#' style='white-space:nowrap' name='${r.id}' onclick ='copyResourceName(this)'>${message(code: 'send_me_data.btn_find_data')}</a></td></tr>"
                } else {
                    renderOption = "${renderOption}${message(code: 'send_me_data.planetary_not_applicable')}</td></tr>"
                }
                i++
            }
        } else {
            renderOption = "${renderOption}<tr><td></td><td>${message(code: 'resource.not_found')}</td></tr>"

        }
        Map outputMap = [table:renderOption, size:resourcesFound ? resourcesFound.size() : 0]
        render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def maxRowLimitExceeded() {
        Boolean exceeded = false
        String errorMessage = ""
        String designId = params.designId
        String indicatorId = params.indicatorId
        String currentQueryId = params.currentQueryId
        Integer currentRows = params.int("currentRows")

        if (designId && currentQueryId && currentRows && indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity design = entityService.getEntityById(designId, session)

            if (design && design.datasets && indicator && indicator.maxRowLimitPerDesign) {
                List<String> validQueryIds = indicator.indicatorQueries?.collect({it.queryId})
                Integer datasetsAmount = design.datasets.findAll({!it.projectLevelDataset && !currentQueryId.equals(it.queryId) && validQueryIds?.contains(it.queryId)})?.size() ?: 0
                datasetsAmount = datasetsAmount + currentRows

                if (datasetsAmount > indicator.maxRowLimitPerDesign) {
                    exceeded = true
                    errorMessage = "${message(code: "query_row_limit_reached")} (${indicator.maxRowLimitPerDesign} ${message(code: "importMapper.combiner.info2")})"
                }
            }
        }
        render([exceeded: exceeded, errorMessage: errorMessage, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resourceQualityWarning() {
        String warning = ""
        String warningType = ""
        String resourceId = params.resourceId
        String profileId = params.profileId
        String sectionId = params.sectionId
        String indicatorId = params.indicatorId
        String questionId = params.questionId
        String entityId = params.entityId
        String queryId = params.queryId

        if (resourceId) {
            def resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
            Query query = queryService.getQueryByQueryId(queryId, true)
            Question question = query?.getQuestionsBySection(sectionId)?.find({ it.questionId == questionId })
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            Entity entity = entityService.getEntityById(entityId)
            Map filterResourceQualityWarning =[:]

            if(resource) {
                Map<String, Map> blockAndWarningResourceFilterCriteria = [
                        (IndicatorQuery.WARNING_RESOURCE): question?.getWarningResourceFilterCriteria(indicator, entity),
                        (IndicatorQuery.BLOCK_RESOURCE)  : question?.getBlockResourceFilterCriteria(indicator, entity)
                ]
                blockAndWarningResourceFilterCriteria.removeAll { !it.value }

                blockAndWarningResourceFilterCriteria.each { key, value ->
                    Map<String, List<Object>> propertiesWithoutWarningString = value.findAll {
                        !(it.key in [IndicatorQuery.WARNING_MESSAGE_INDICATOR, IndicatorQuery.BLOCK_MESSAGE_INDICATOR])
                    }

                    if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, null, propertiesWithoutWarningString, true)) {
                        if (key == IndicatorQuery.BLOCK_RESOURCE) {
                            filterResourceQualityWarning.put("resourceQualityWarning", Resource.QUALITY_WARNING_BLOCK_TYPE)
                            filterResourceQualityWarning.put("resourceQualityWarningText", value.get(IndicatorQuery.BLOCK_MESSAGE_INDICATOR)?.get(0))
                        } else {
                            filterResourceQualityWarning.put("resourceQualityWarning", Resource.QUALITY_WARNING_WARN_TYPE)
                            filterResourceQualityWarning.put("resourceQualityWarningText", value.get(IndicatorQuery.WARNING_MESSAGE_INDICATOR)?.get(0))
                        }
                    }
                }

                if (!filterResourceQualityWarning ||
                        (resource.resourceQualityWarning == Resource.QUALITY_WARNING_BLOCK_TYPE &&
                                filterResourceQualityWarning.resourceQualityWarning != Resource.QUALITY_WARNING_BLOCK_TYPE)) {
                    warning = getMessageForResourcePopup(resource)
                    warningType = resource.resourceQualityWarning
                } else if (filterResourceQualityWarning) {
                    warning = getMessageForResourcePopup(filterResourceQualityWarning, true)
                    warningType = filterResourceQualityWarning.resourceQualityWarning
                }
            }
        }
        render([warningType: warningType, warning: warning, localizedYes: message(code:"yes"), localizedCancel: message(code:"cancel"), localizedHeading: message(code:"resource_quality_warning"), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private String getMessageForResourcePopup(def resource, boolean isFilterMessage = false){
        String warning = ""

        if (resource && (resource.resourceQualityWarning)) {
            String qualityWarningText = resource.resourceQualityWarningText ?: "Reason unspecified"

            if (isFilterMessage){
                warning = message(code: resource.resourceQualityWarningText ?: "resource_quality_warning.info1", args: [qualityWarningText])
            } else {
                warning = message(code: "resource_quality_warning.info1", args: [qualityWarningText])
            }

            if (Resource.QUALITY_WARNING_WARN_TYPE.equals(resource.resourceQualityWarning) && !isFilterMessage) {
                warning = warning + " " + message(code: "resource_quality_warning.info2")
            }
        }

        return warning
    }

    def sessionInformation() {
        User user = userService.getUserById(params.userId)
        def lastRequest
        Boolean expired = false

        if (user) {
            List<GrailsUser> grailsUsers = sessionRegistry.getAllPrincipals()
            GrailsUser sessionUser = grailsUsers.find({it.id.equals(user.id)})

            if (sessionUser) {
                List<SessionInformation> sessionInformations = []

                sessionRegistry.getAllSessions(sessionUser, false).each {
                    if (!sessionInformations.contains(it)) {
                        sessionInformations.add(it)
                    }
                }

                Date currentDate = new Date()
                sessionInformations.each { SessionInformation sessionInfo ->
                    /* checks to see if current session is not expired and last request was made less than 8 hours ago
                    * Our sessions expire at 8 (2.88e+7 ms) hours, however springSecurity does not realise this properly and the sessionRegistry valid sessions
                    * contain "expired" sessions, hence why we need to check for lastRequest time aswell to get realistic users.
                    * */
                    lastRequest = sessionInfo.lastRequest.time
                    if (user.getIsCommercialUser() && (currentDate.time - sessionInfo.lastRequest.time) < 2.88e+7 && !sessionInfo.expired) {
                        /*log.info("Commerical user session isn't expired")
                        log.info("Total: ${currentDate.time - sessionInfo.lastRequest.time}")
                        log.info("CurrentTime: ${currentDate.time}")
                        log.info("TimeLeft: ${sessionInfo.lastRequest.time}")*/
                        expired = false
                    } else if (!user.getIsCommercialUser() && (currentDate.time - sessionInfo.lastRequest.time) < 3600000 && !sessionInfo.expired) {
                        log.info("Non-commerical user session isn't expired")
                        flashService.setInfoAlert("Non-commerical user session isn't expired", true)
                        expired = false
                    } else {
                        log.info("Current session is expired or has alternative issue")
                        flashService.setWarningAlert("Current session is expired or has alternative issue", true)
                        expired = true
                    }
                }
            }
        }
        render([lastRequest: "${lastRequest}", expired: "${expired}", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

    }

    def maintainActivity() {
        User user = userService.getUserById(params.userId)
        Boolean sessionRefreshed = false

        if (user) {
            GrailsUser sessionUser = sessionRegistry.getAllPrincipals().find({it.id.equals(user.id)})
            if (sessionUser) {
                List<SessionInformation> sessionInformations = []

                sessionRegistry.getAllSessions(sessionUser, false).each {
                    if (!sessionInformations.contains(it)) {
                        sessionInformations.add(it)
                    }
                }

                sessionInformations.each { SessionInformation sessionInfo ->
                    sessionInfo.refreshLastRequest()
                    Date currentDate = new Date()
                    if (currentDate.time == sessionInfo.lastRequest.time) {
                        sessionRefreshed = true
                    }
                }
            }
        }
        render([activitySuccess: "${sessionRefreshed}", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def checkHasData() {
        Boolean hasData = false
        String designId = params.designId
        String indicatorId = params.indicatorId
        String entityName = ""
        log.info("HAS DATA!!!!!!!!")
        if (designId && indicatorId) {
            Entity design = entityService.readEntity(designId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (design && indicator) {
                entityName = design.name

                List<String> indicatorQueryIds = indicator.indicatorQueries?.findAll({!Constants.PARAMETER_QUERYIDS.contains(it.queryId)})?.collect({it.queryId})

                if (indicatorQueryIds && design.datasets?.find({indicatorQueryIds.contains(it.queryId) && !it.projectLevelDataset})) {
                    hasData = true
                }
            }
        }
        log.info("Entity: ${entityName} hasData: ${hasData}")
        render([hasData: hasData, entityName: entityName, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }
    @Secured(["ROLE_AUTHENTICATED"])
    def addResourceToCompare(){
        String resourceId = params.resourceId
        String parentId = params.parentEntityId
        String profileId = params.profileId
        String originIndicatorId = params.originIndicatorId
        List<String> resourceIdAndProfileIdList = params.resourceList?.tokenize(",")
        Entity parentEntity = entityService.getEntityById(parentId)
        List<Indicator> allIndicators = parentEntity?.indicators
        List <String> allClassificationMaps = allIndicators?.collect({it.classificationsMap})?.flatten()?.unique()
        Query additionalQuery = queryService.getQueryByQueryId(com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID,true)
        List<Question> allClassQuestion = allClassificationMaps?.collect({it -> questionService.getQuestion(additionalQuery,it)})

        String indicatorId = com.bionova.optimi.core.Constants.COMPARE_INDICATORID
        String materialQueryId = com.bionova.optimi.core.Constants.COMPARE_QUERYID
        Indicator indicatorSpeciFier = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Indicator originIndicator = indicatorService.getIndicatorByIndicatorId(originIndicatorId, true)
        Entity entity = entityService.getMaterialSpecifierEntity(parentEntity)
        List<Resource> resources = []
        Map<Question,Question> addQsMapToMainQs
        Map<String,String> resourceIdAndProfileIdMap
        Map<String,List<String>> resourceIdAndQuestionIdMap
        String status = ""
        List<Resource> notAddedResources = []
        Map outputMap = [:]
        List<Question> mainQs
        try {
            if(resourceIdAndProfileIdList){
                resourceIdAndProfileIdMap = resourceIdAndProfileIdList?.collectEntries({it ->
                    def itList = it.tokenize(".")
                    [(itList[0]) : itList[1]]
                })
                resourceIdAndQuestionIdMap = resourceIdAndProfileIdList?.collectEntries({it ->
                    def itList = it.tokenize(".")
                    [(itList[0]) : [itList[2],itList[3]]]
                })
                mainQs = resourceIdAndQuestionIdMap?.collect{k,v-> questionService.getQuestion(v[1], v[0])}?.unique()

                resourceIdAndProfileIdMap?.each {resId,profId ->
                    Resource r = optimiResourceService.getResourceByResourceAndProfileId(resId,profId)
                    if(r){
                        resources.add(r)
                    }
                }
            } else if(resourceId){
                resources = [optimiResourceService.getResourceByResourceAndProfileId(resourceId,profileId)]
            }
            if(resources.size() > 0 && entity && parentEntity){
                List<ResultCategory> resolvedResultCategories = indicatorSpeciFier?.getResolveResultCategories(null)
                List<Dataset> entityDatsets = entity.datasets ? entity.datasets.toList() : []
                List<String> resourceIdsExisted = entityDatsets?.collect({it?.resourceId})?.unique() ?: []
                resources.each {resource ->
                        if(resource){
                            if(!resourceIdsExisted.contains(resource.resourceId)){
                                String mainQId = resourceIdAndQuestionIdMap?.get(resource.resourceId)?.get(0)
                                Map additionalQuestionDefault
                                String defaultClass
                                List<String> allowedUnit = resource.allowedUnits
                                String unit = allowedUnit?.contains(resourceService.getSubType(resource)?.standardUnit) ? resourceService.getSubType(resource).standardUnit : resource.unitForData
                                Dataset d = new Dataset()
                                d.resourceId = resource.resourceId
                                d.profileId = resource.profileId
                                d.additionalQuestionAnswers = [profileId: resource.profileId]
                                if(allClassQuestion && resourceIdAndQuestionIdMap && mainQId){
                                    allClassQuestion.each {Question question ->
                                        additionalQuestionDefault = mainQs?.find({it?.questionId == mainQId})?.additionalQuestionDefaults
                                        if(question){
                                            defaultClass = additionalQuestionDefault?.get(question?.questionId) ?: 0

                                            if(defaultClass){
                                                d.additionalQuestionAnswers.put(question?.questionId, defaultClass)
                                            }
                                        }
                                    }

                                }
                                d.userGivenUnit = unit
                                d.questionId = materialQueryId
                                d.queryId = materialQueryId
                                d.sectionId = materialQueryId
                                d.quantity = 1
                                d.answerIds = ["1"]
                                d.manualId = new ObjectId().toString()
                                d.resultCategoryIds = resolvedResultCategories?.collect({it.resultCategoryId})
                                entityDatsets.add(d)
                            } else {
                                notAddedResources.add(resource)
                            }

                        }
                    }
                Integer addedResource = resources?.size() - notAddedResources?.size() ?: 0
                entity = datasetService.saveDatasets(entity, entityDatsets, materialQueryId, indicatorId, true, null,
                        true, false, false, null)
                entityService.calculateEntity(entity, indicatorId, materialQueryId, false)
                Integer datasetSize = datasetService.getDatasetsByEntityAndQueryId(entity,materialQueryId)?.size()
                status=  datasetSize ? "(${datasetSize})" : ''
                outputMap.put("datasize",status)
                outputMap.put("notAddedResources",notAddedResources?.size())
                outputMap.put("addedResources",addedResource)
            }
        } catch (Exception e){
            flashService.setErrorAlert("ERROR while adding resource(s) to compare: ${e.message}", true)
            loggerUtil.error(log, "ERROR while adding resource(s) to compare:", e)
        }
        render ([output:outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def saveResourceCompareFromAjax(){
        String resourcesList = request.JSON?.resourceList
        String parentEntityId = request.JSON?.parentEntityId

        if(parentEntityId){
            if(resourcesList?.length() > 0){
                session?.setAttribute("${parentEntityId}CompareResourceBasket", resourcesList)
            } else {
                session?.removeAttribute("${parentEntityId}CompareResourceBasket")
            }
        }
        render text: "saved!"
    }
    def getRecipesForDataLoadingFromFile() {
        String excelFilePath = params.filePath
        FileInputStream inputStream
        File file
        Path path
        BasicFileAttributes attr
        Workbook workbook
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
            workbook = getWorkbook(inputStream, excelFilePath, file);
            FormulaEvaluator formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            Sheet firstSheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            int lastRowNum = firstSheet.getLastRowNum();
            int lastColumnNum = firstSheet.getRow(0).getLastCellNum();
            Row firstRowAsKeys = firstSheet.getRow(0); // first row as a json keys
            List<Map<String, String>> finalSheetObject = []
            Set<String> mandatoryDataInput = []

            for (int j = com.bionova.optimi.core.Constants.BETIE_EXCEL_FIRST_FORMULATION_COLUMN; j < lastColumnNum; j++) {
                Map<String, Object> attrMap = [:]
                Map<String, Map<String, Object>> listOfResIdsWithQuantity = [:] // value of map is an object containing quantity and multiplierId
                Map<String, Map<String, Object>> listOfQuestionIdsWithQuantity = [:] // value of map is an object containing quantity and multiplierId
                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = firstSheet.getRow(i);
                    attrMap.put(com.bionova.optimi.core.Constants.BETIE_RECIPENAME, firstRowAsKeys?.getCell(j)?.getStringCellValue())
                    //is it ok to have it in loop ? String

                    if (row) {
                        String cellData = dataFormatter.formatCellValue(row.getCell(j), formulaEvaluator)
                        if (cellData) {
                            if (com.bionova.optimi.core.Constants.BETIE_FILTER.equalsIgnoreCase(row.getCell(0)?.getStringCellValue())) {
                                attrMap.put(row.getCell(1)?.getStringCellValue(), cellData)
                            } else if (com.bionova.optimi.core.Constants.BETIE_DATA.equalsIgnoreCase(row.getCell(0)?.getStringCellValue())) {
                                Map<String, Double> quantityValuesObject = createQuantityValuesObjectForLoadingRecipeFeature(Double.parseDouble(cellData), row)
                                listOfResIdsWithQuantity.put(dataFormatter.formatCellValue(row.getCell(1), formulaEvaluator), quantityValuesObject)
                            } else if (com.bionova.optimi.core.Constants.BETIE_VAR_DATA.equalsIgnoreCase(row.getCell(0)?.getStringCellValue())) {
                                Map<String, Double> quantityValuesObject = createQuantityValuesObjectForLoadingRecipeFeature(Double.parseDouble(cellData), row)
                                listOfQuestionIdsWithQuantity.put(dataFormatter.formatCellValue(row.getCell(1), formulaEvaluator), quantityValuesObject)
                                mandatoryDataInput.add(dataFormatter.formatCellValue(row.getCell(1), formulaEvaluator));
                            }
                        }
                    }
                    if (listOfResIdsWithQuantity) {
                        attrMap.put(com.bionova.optimi.core.Constants.BETIE_RESOURCEIDS, listOfResIdsWithQuantity)
                    }
                    if (listOfQuestionIdsWithQuantity) {
                        attrMap.put(com.bionova.optimi.core.Constants.BETIE_QUESTIDS_QTY, listOfQuestionIdsWithQuantity)
                    }
                }
                finalSheetObject.add(attrMap)
            }
            render([workbookJson: finalSheetObject, mandatoryDataInput: mandatoryDataInput, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } catch (FileNotFoundException notFoundException) {
            log.warn("OOPS ! File not found ! : ${notFoundException}")
            flashService.setErrorAlert("File not found when getting recipes for DataLoadingFromFile : ${notFoundException.message}", true)
        }catch (IOException io) {
            log.warn("IOException ! : ${io}")
            flashService.setErrorAlert("IOException occured while getting recipes for DataLoadingFromFile : ${io.message}", true)
        }catch (Exception e) {
            log.warn("Exception ! :${e}")
            flashService.setErrorAlert("Exception occured while getting recipes for DataLoadingFromFile : ${e.message}", true)
        }
        finally {
            if (workbook) {
                workbook.close()
            }
            if (inputStream) {
                inputStream.close()
            }
        }

    }
    /*
     * This function relates to loading recipe feature for Betie.
     * It creates a quantity values object for the JSON to be transferred to front-end.
     * It contains:
     * - quantity of a resource to be added
     * - multiplierId which is optional in the Excel and defines a multiplier factor for quantity
     *
     * More info: https://oneclicklca.atlassian.net/browse/REL-629
     */
    private Map<String, Object> createQuantityValuesObjectForLoadingRecipeFeature(Double quantity, Row row) {
        Map<String, Object> quantityValues = [:]
        quantityValues.put(com.bionova.optimi.core.Constants.BETIE_JSON_QUANTITY_VALUES_OBJECT_QUANTITY_PROPERTY_NAME, quantity)
        String multiplierId = row.getCell(com.bionova.optimi.core.Constants.BETIE_EXCEL_MULTIPLIER_ID_COLUMN)?.getStringCellValue()

        if (multiplierId) {
            quantityValues.put(com.bionova.optimi.core.Constants.BETIE_JSON_QUANTITY_VALUES_OBJECT_MULTIPLIER_ID_PROPERTY_NAME, multiplierId)
        }

        return quantityValues
    }
    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath ,File file)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(new ByteArrayInputStream(file.getBytes()))
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }
}
