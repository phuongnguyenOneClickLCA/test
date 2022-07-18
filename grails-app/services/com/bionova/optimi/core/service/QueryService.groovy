/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as Const
import com.bionova.optimi.core.domain.mongo.Application
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.CarbonDesignerRegion
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QueryFilter
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.ui.rset.RSEnvDatasetDto
import com.bionova.optimi.util.FileUtil
import com.bionova.optimi.util.UnitConversionUtil
import com.bionova.optimi.core.Constants.EntityClass
import com.gmongo.GMongo
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCursor
import com.mongodb.DBObject
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.plugin.springsecurity.annotation.Secured
import groovy.transform.CompileStatic
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat

import static com.bionova.optimi.core.Constants.ADDITIONAL_QUESTIONS_QUERY_ID

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class QueryService {

    def shellCmdService
    def loggerUtil
    def configurationService
    def optimiSecurityService
    def userService
    def optimiResourceService
    def applicationService
    def licenseService
    def messageSource
    def resourceFilterCriteriaUtil
    def newCalculationServiceProxy
    def featureService
    def constructionService
    def privateClassificationListService
    def unitConversionUtil
    def entityService
    def grailsLinkGenerator
    def localizedLinkService
    FlashService flashService

    //TODO: this is not thread safe
    private static boolean additionalQuestionsQueryUpdated = false
    //TODO: this is not thread safe
    private static Query additionalQuestionQuery

    @Secured(["ROLE_AUTHENTICATED"])
    def getBasicQuery() {
        Query basicQuery = null
        String basicQueryId = configurationService.getConfigurationValue(Constants.APPLICATION_ID, Constants.ConfigName.BASIC_QUERY_ID.toString())

        if (basicQueryId) {
            List<Query> basicQueries = Query.findAllByQueryIdAndActive(basicQueryId, true)

            if (basicQueries) {
                if (basicQueries.size() > 1) {
                    basicQuery = basicQueries.max({ Query q -> q.importTimeAsDate })
                } else {
                    basicQuery = basicQueries.get(0)
                }
            }

        }
        return basicQuery
    }

    /**
     * Get all queries of indicator. The sections, questions in queries have been filtered by license check.
     * @param indicator
     * @param licensedFeatures
     * @param onlyDesignLevel
     * @param isComponentEntity
     * @param checkHideQuestions hide the questions / sections defined in indicator {@link Indicator#hideQuestions}
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    List<Query> getQueriesByIndicator(Indicator indicator, List<Feature> licensedFeatures, Boolean onlyDesignLevel = false, Boolean isComponentEntity = false, Boolean checkHideQuestions = false) {
        List<IndicatorQuery> indicatorQueries = onlyDesignLevel ? indicator?.indicatorQueries?.findAll({ !it.projectLevel }) : indicator?.indicatorQueries
        List<String> licensedFeatureIds = licensedFeatures?.collect { it?.featureId }
        indicatorQueries = licenseService.doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys(indicatorQueries, licensedFeatureIds)
        List<String> queryIds = indicatorQueries?.collect { it.queryId }
        List<Query> queries = []

        if (queryIds) {
            if (isComponentEntity) {
                queries = Query.findAllByQueryIdInListAndActive(queryIds, true)
            } else {
                queries = Query.findAllByQueryIdInListAndActiveAndComponentQueryIsNull(queryIds, true)
            }

            if (queries?.size() > 0) {
                queries.each { Query query ->
                    doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery(query, licensedFeatureIds)
                    if (checkHideQuestions) {
                        removeHiddenQuestions(query, indicator)
                    }
                }
                queries.sort({ query -> queryIds.indexOf(query.queryId) })
            }
        } else {
            loggerUtil.warn(log, "No active queries found for Indicator: " + indicator?.indicatorId)
            flashService.setWarningAlert("No active queries found for Indicator: " + indicator?.indicatorId, true)
        }
        return queries
    }

    /**
     * Remove questions / sections to be hidden defined in {@link Indicator#hideQuestions}
     * Similar function {@link QuestionService#isQuestionHidden}
     * @param query
     * @param indicator
     */
    void removeHiddenQuestions(Query query, Indicator indicator) {
        if (indicator?.hideQuestions && query) {
            Set<String> hideSections = []
            query?.sections?.each { QuerySection section ->
                if (indicator.hideSectionTotally(section)) {
                    hideSections.add(section?.sectionId)
                } else if (indicator.hideQuestions.containsKey(section?.sectionId)) {
                    List<String> hideQuestions = indicator.hideQuestions.get(section.sectionId)
                    section.questions?.removeIf { it.questionId in hideQuestions }
                }
            }
            if (hideSections?.size() > 0) {
                query.sections.removeIf { it.sectionId in hideSections }
            }
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Map getDataQualityWarning(Entity entity, Indicator indicator, String queryId, QueryFilter upstreamDBClassifiedFilter, User user) {
        List<String> dataQualityWarnings = []
        List<String> filteredOutConstructionManualIds = []

        if (entity && queryId) {
            Boolean decideLaterResourceInQuery = Boolean.FALSE
            Boolean notInFilter = Boolean.FALSE
            List<String> upstreamDBClassifiedList = []
            ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)

            entity.datasets.each { Dataset dataset ->
                if (dataset.queryId == queryId && !dataset.noQueryRender) {
                    Resource resource = resourceCache.getResource(dataset)

                    if (resource) {
                        if (resource.upstreamDBClassified) {
                            upstreamDBClassifiedList.add(resource.upstreamDBClassified)
                        }

                        if (resource.decideLaterResource) {
                            decideLaterResourceInQuery = Boolean.TRUE
                        }

                        // Validate constituents of the dummy (locally made) constructions.
                        if (dataset.isConstituent() && (dataset.parentConstructionId == Const.DUMMY_RESOURCE_ID)) {
                            if (!resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, queryId, indicator, null, entity)) {
                                notInFilter = true
                                // Collect 'manualId' of the dummy construction the invalid resource belongs to.
                                // It will be used to display an alert icon for the dummy construction.
                                filteredOutConstructionManualIds << entity.datasets.find {
                                    it.isConstruction() && (it.uniqueConstructionIdentifier == dataset.uniqueConstructionIdentifier)
                                } ?.manualId
                            }
                        // Do not validate constituents and dummy resources/constructions.
                        } else if (!notInFilter && !dataset.parentConstructionId && !resource.isDummy) {
                            notInFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(resource, queryId, indicator, null, entity) ? Boolean.FALSE : Boolean.TRUE
                        }
                    }
                }
            }

            if (indicator?.multiDataWarning && upstreamDBClassifiedList.unique().size() > 1 && upstreamDBClassifiedFilter && ((userService.getAuthenticated(user) && com.bionova.optimi.core.Constants.ROLE_AUTHENTICATED.equals(upstreamDBClassifiedFilter.userClass)) || !userService.getAuthenticated(user))) {
                dataQualityWarnings << "${messageSource.getMessage("query.multiple_datasources", null, LocaleContextHolder.getLocale())}: ${upstreamDBClassifiedList.unique().toString().replace("[", "").replace("]", "")}. <a href='javascript:;' onclick='expandWarningUpstreamDB(this)'>${messageSource.getMessage('upstreamDB.warning_explanation', null, LocaleContextHolder.getLocale())}</a><p style='font-weight: normal !important; color: #000000' class='hidden'>${messageSource.getMessage('upstreamDB.warning_explanation.expanded', null, LocaleContextHolder.getLocale())}</p>"
            }

            if (decideLaterResourceInQuery) {
                dataQualityWarnings << messageSource.getMessage("query.resource.undefined_materials_warning", null, LocaleContextHolder.getLocale())
            } else if (notInFilter) {
                dataQualityWarnings << messageSource.getMessage("query.resource.warning.general.filterCriteriaFailure", null, "Some of the used data are incompatible with the calculation tool. Please ensure that the data quality requirements are met.", LocaleContextHolder.getLocale())
            }
        }

        return [qualityWarnings: dataQualityWarnings, filteredOutConstructionManualIds: filteredOutConstructionManualIds.unique().grep()]
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getDatasetsOkForEntity(Entity entity, Indicator indicator, List<Dataset> datasets) {
        User user = userService.getCurrentUser()
        String dataQualityWarning = ""

        if (entity && datasets && indicator) {
            List<String> queryIds = indicator.indicatorQueries?.collect({ it.queryId })
            Boolean decideLaterResourceInQuery = Boolean.FALSE
            Boolean notInFilter = Boolean.FALSE

            String userClass = ""
            Boolean upstreamDBClassifiedFilter = Boolean.FALSE
            List<String> queryIdsAlreadyChecked = []
            List<String> upstreamDBClassifiedList = []

            datasets.each { Dataset dataset ->
                if (queryIds && queryIds.contains(dataset.queryId)) {

                    if (!queryIdsAlreadyChecked.contains(dataset.queryId)) {
                        queryIdsAlreadyChecked.add(dataset.queryId)
                        Query query = getQueryByQueryId(dataset.queryId, true)

                        if (query) {
                            List<QueryFilter> supportedFilters = query.supportedFilters
                            QueryFilter filter = supportedFilters?.find({ "upstreamDBClassified".equals(it.resourceAttribute) })

                            if (filter) {
                                userClass = filter.userClass
                                upstreamDBClassifiedFilter = Boolean.TRUE
                            }
                        }
                    }

                    String resourceId = dataset.resourceId
                    String profileId = dataset.profileId ?: dataset.additionalQuestionAnswers?.get("profileId")
                    Document d

                    if (resourceId) {
                        if (profileId) {
                            d = Resource.collection.findOne([resourceId: resourceId, profileId: profileId])
                        } else {
                            d = Resource.collection.findOne([resourceId: resourceId])
                        }
                    }

                    if (d) {
                        String upstreamDb = d.get("upstreamDBClassified")

                        if (upstreamDb) {
                            upstreamDBClassifiedList.add(upstreamDb)
                        }

                        if (d.get("decideLaterResource")) {
                            decideLaterResourceInQuery = Boolean.TRUE
                        }

                        // Do not validate constituents and dummy resources/constructions; validate constituents of the dummy (locally made) constructions.
                        if ((!dataset.parentConstructionId && !d.get("isDummy")) || (dataset.isConstituent() && (dataset.parentConstructionId == Const.DUMMY_RESOURCE_ID))) {
                            notInFilter = resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(null, dataset.queryId, indicator, d, entity) ? Boolean.FALSE : Boolean.TRUE
                        }
                    }
                }
            }

            if (upstreamDBClassifiedFilter && ((userService.getAuthenticated(user) && com.bionova.optimi.core.Constants.ROLE_AUTHENTICATED.equals(userClass)) || !userService.getAuthenticated(user))) {
                if (upstreamDBClassifiedList.unique().size() > 1 && indicator.multiDataWarning) {
                    dataQualityWarning = "${dataQualityWarning}${messageSource.getMessage("query.multiple_datasources", null, LocaleContextHolder.getLocale())}: ${upstreamDBClassifiedList.unique().toString().replace("[", "").replace("]", "")}<br/>"
                }
            }

            if (decideLaterResourceInQuery) {
                dataQualityWarning = "${dataQualityWarning}${messageSource.getMessage("query.resource.undefined_materials_warning", null, LocaleContextHolder.getLocale())}<br/>"
            } else if (notInFilter) {
                dataQualityWarning = "${dataQualityWarning}${messageSource.getMessage("query.resource.not_in_filter_warning", null, LocaleContextHolder.getLocale())}<br/>"
            }
        }
        return dataQualityWarning
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def generateExcel(Set<Dataset> datasets, Query query, Entity entity, Indicator indicator, Entity parent) {
        Workbook wb

        if (datasets && query && indicator) {
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parent, [Feature.SHOW_DATABASE_ID])
            Boolean showDatabaseIdLicensed = featuresAllowed.get(Feature.SHOW_DATABASE_ID)
            List<String> additionalQuestions = ["comment"]
            List<Indicator> projectIndicators = parent.indicators?.findAll({ indicator.indicatorUse?.equals(it.indicatorUse) })

            projectIndicators?.each { Indicator ind ->
                ind.indicatorQueries?.find({ query.queryId.equals(it.queryId) })?.additionalQuestionIds?.each { String additionalQuestionId ->
                    if (!additionalQuestions.contains(additionalQuestionId) && !"carbonDataImpact".equalsIgnoreCase(additionalQuestionId)) {
                        additionalQuestions.add(additionalQuestionId)
                    }
                }
            }
            wb = new HSSFWorkbook()

            Map<String, Boolean> constructionPreventExpand = [:]
            Map<String, String> constructionName = [:]

            ResourceCache resourceCache = ResourceCache.init(datasets.toList())
            datasets.findAll({ it.uniqueConstructionIdentifier && !it.parentConstructionId })?.each {
                def construction = constructionService.getConstruction(resourceCache.getResource(it)?.constructionId)
                if (construction) {
                    Boolean preventExpand = construction?.preventExpand
                    constructionPreventExpand.put((it.uniqueConstructionIdentifier), preventExpand)
                    constructionName.put((it.uniqueConstructionIdentifier), construction.nameEN)
                }
            }

            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            CellStyle constituentStyle = wb.createCellStyle()
            constituentStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index)
            constituentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            Sheet sheet = wb.createSheet()

            wb.setSheetName(wb.getSheetIndex(sheet), "DATA")
            Row headerRow = sheet.createRow(0)
            Integer i = 0

            Cell headerCellclass = headerRow.createCell(i)
            headerCellclass.setCellStyle(cs)
            headerCellclass.setCellType(CellType.STRING)
            headerCellclass.setCellValue("CLASS")
            i++

            Cell headerCellmaterial = headerRow.createCell(i)
            headerCellmaterial.setCellStyle(cs)
            headerCellmaterial.setCellType(CellType.STRING)
            headerCellmaterial.setCellValue("MATERIAL")
            i++

            Cell headerCellquantity = headerRow.createCell(i)
            headerCellquantity.setCellStyle(cs)
            headerCellquantity.setCellType(CellType.STRING)
            headerCellquantity.setCellValue("QUANTITY")
            i++

            Cell headerCellunit = headerRow.createCell(i)
            headerCellunit.setCellStyle(cs)
            headerCellunit.setCellType(CellType.STRING)
            headerCellunit.setCellValue("QTY_TYPE")
            i++

            List<String> skipValues = query.exportSkipParameters
            if (skipValues && !skipValues.isEmpty()) {
                additionalQuestions.removeAll(skipValues)
            }

            additionalQuestions.each { String additionalQuestion ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(cs)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(additionalQuestion.toUpperCase())
                i++
            }

            Cell headerCellConstruction = headerRow.createCell(i)
            headerCellConstruction.setCellStyle(cs)
            headerCellConstruction.setCellType(CellType.STRING)
            headerCellConstruction.setCellValue("PART OF CONSTRUCTION")
            i++

            Cell headerCellIgnore = headerRow.createCell(i)
            headerCellIgnore.setCellStyle(cs)
            headerCellIgnore.setCellType(CellType.STRING)
            headerCellIgnore.setCellValue("IGNORE")
            i++

            if (showDatabaseIdLicensed) {
                Cell headerCellOclid = headerRow.createCell(i)
                headerCellOclid.setCellStyle(cs)
                headerCellOclid.setCellType(CellType.STRING)
                headerCellOclid.setCellValue("OCLID")
                i++
            }

            Integer numberOfHeadings = i
            i = 1
            resourceCache = ResourceCache.init(datasets.toList())
            for (Dataset dataset: datasets.sort({ it.seqNr }))
            {
                if (!dataset.uniqueConstructionIdentifier || !dataset.parentConstructionId || (dataset.uniqueConstructionIdentifier && dataset.parentConstructionId && !constructionPreventExpand.get(dataset.uniqueConstructionIdentifier))) {
                    boolean constituent = dataset.parentConstructionId
                    Row datasetRow = sheet.createRow(i)
                    Resource resource = resourceCache.getResource(dataset)


                    Integer j = 0
                    Cell datasetCellclass = datasetRow.createCell(j)
                    datasetCellclass.setCellType(CellType.STRING)

                    if (dataset.persistedOriginalClass) {
                        datasetCellclass.setCellValue(dataset.persistedOriginalClass)
                    } else {
                        query.exportClasses?.find { Map<String, Map<String, String>> exportClass ->
                            exportClass.values().find { Map<String, String> sectionAndQuestion ->
                                if (sectionAndQuestion.get(dataset.sectionId)?.equalsIgnoreCase(dataset.questionId)) {
                                    datasetCellclass.setCellValue(exportClass.keySet().toList().get(0))
                                    return true
                                }
                            }
                        }
                    }

                    if (constituent) {
                        datasetCellclass.setCellStyle(constituentStyle)
                    }
                    j++

                    Cell datasetCellmaterial = datasetRow.createCell(j)
                    datasetCellmaterial.setCellType(CellType.STRING)
                    datasetCellmaterial.setCellValue(resource?.isDummy ? dataset?.groupingDatasetName : resource?.staticFullName)

                    if (constituent) {
                        datasetCellmaterial.setCellStyle(constituentStyle)
                    }
                    j++

                    Cell datasetCellquantity = datasetRow.createCell(j)
                    datasetCellquantity.setCellType(CellType.NUMERIC)
                    datasetCellquantity.setCellValue(dataset.quantity)
                    if (constituent) {
                        datasetCellquantity.setCellStyle(constituentStyle)
                    }
                    j++

                    Cell datasetCellunit = datasetRow.createCell(j)
                    datasetCellunit.setCellType(CellType.STRING)
                    datasetCellunit.setCellValue(dataset.userGivenUnit)
                    if (constituent) {
                        datasetCellunit.setCellStyle(constituentStyle)
                    }
                    j++

                    additionalQuestions.each { String question ->
                        Cell datasetCell = datasetRow.createCell(j)
                        if (question.equals(com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID)) {
                            question = privateClassificationListService.getPrivateClassificationQuestion()?.questionId
                        }
                        if (dataset.additionalQuestionAnswers?.get(question)) {
                            if (question.equals("serviceLife")) {
                                datasetCell.setCellType(CellType.STRING)
                                datasetCell.setCellValue(newCalculationServiceProxy.getServiceLife(dataset, resource, entity, indicator)?.toString())
                            } else if (com.bionova.optimi.core.Constants.TransportDistanceQuestionId.list().contains(question)) {
                                if ("default".equals(dataset.additionalQuestionAnswers.get(question)?.toString())) {
                                    datasetCellquantity.setCellType(CellType.NUMERIC)
                                    datasetCell.setCellValue(entity?.getDefaultTransportFromSubType(resource))
                                } else {
                                    datasetCell.setCellType(CellType.STRING)
                                    datasetCell.setCellValue(dataset.additionalQuestionAnswers.get(question)?.toString())
                                }
                            } else {
                                String answer = dataset.additionalQuestionAnswers.get(question)?.toString()?.trim()
                                if (answer?.isNumber()) {
                                    datasetCell.setCellType(CellType.NUMERIC)

                                    if (answer.isInteger()) {
                                        datasetCell.setCellValue(Integer.valueOf(answer))
                                    } else if (answer.isDouble()) {
                                        datasetCell.setCellValue(Double.valueOf(answer))
                                    }
                                } else {
                                    datasetCell.setCellType(CellType.STRING)
                                    datasetCell.setCellValue(answer ?: "")
                                }
                            }
                        }
                        if (constituent) {
                            datasetCell.setCellStyle(constituentStyle)
                        }
                        j++
                    }

                    if (constituent) {
                        Cell datasetCellConstruction = datasetRow.createCell(j)
                        datasetCellConstruction.setCellType(CellType.STRING)
                        datasetCellConstruction.setCellValue(constructionName.get(dataset.uniqueConstructionIdentifier) ?: "")
                        datasetCellConstruction.setCellStyle(constituentStyle)
                        j++

                        Cell datasetCellIgnore = datasetRow.createCell(j)
                        datasetCellIgnore.setCellType(CellType.BOOLEAN)
                        datasetCellIgnore.setCellValue(true)
                        datasetCellIgnore.setCellStyle(constituentStyle)
                        j++
                    } else {
                        Cell datasetCellConstruction = datasetRow.createCell(j)
                        datasetCellConstruction.setCellType(CellType.STRING)
                        datasetCellConstruction.setCellValue("")
                        j++

                        Cell datasetCellIgnore = datasetRow.createCell(j)
                        datasetCellIgnore.setCellType(CellType.BOOLEAN)
                        datasetCellIgnore.setCellValue(false)
                        j++
                    }

                    if (showDatabaseIdLicensed) {
                        Cell datasetCellOclid = datasetRow.createCell(j)
                        datasetCellOclid.setCellType(CellType.STRING)
                        datasetCellOclid.setCellValue(resource?.id?.toString() ?: "")

                        if (constituent) {
                            datasetCellOclid.setCellStyle(constituentStyle)
                        }
                        j++
                    }

                    i++
                }
            }

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }

            Sheet help = wb.createSheet()
            wb.setSheetName(wb.getSheetIndex(help), "@HELP")
            // TODO: Help sheet info
        }
        return wb
    }

    def generateRseeExcel(List<RSEnvDatasetDto> datasets, Indicator indicator, boolean isFecExport) {
        Workbook wb

        if (datasets && indicator) {

            wb = new HSSFWorkbook()

            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            CellStyle constituentStyle = wb.createCellStyle()
            constituentStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index)
            constituentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            Sheet sheet = wb.createSheet()

            wb.setSheetName(wb.getSheetIndex(sheet), "DATA")
            Row headerRow = sheet.createRow(0)
            Integer i = 0

            Cell headerCellclass = headerRow.createCell(i)
            headerCellclass.setCellStyle(cs)
            headerCellclass.setCellType(CellType.STRING)
            headerCellclass.setCellValue("CLASS")
            i++

            Cell headerCellmaterial = headerRow.createCell(i)
            headerCellmaterial.setCellStyle(cs)
            headerCellmaterial.setCellType(CellType.STRING)
            headerCellmaterial.setCellValue("MATERIAL")
            i++

            Cell headerCellquantity = headerRow.createCell(i)
            headerCellquantity.setCellStyle(cs)
            headerCellquantity.setCellType(CellType.STRING)
            headerCellquantity.setCellValue("QUANTITY")
            i++

            Cell headerCellunit = headerRow.createCell(i)
            headerCellunit.setCellStyle(cs)
            headerCellunit.setCellType(CellType.STRING)
            headerCellunit.setCellValue("QTY_TYPE")
            i++

            Cell headerCellSousLot = headerRow.createCell(i)
            headerCellSousLot.setCellStyle(cs)
            headerCellSousLot.setCellType(CellType.STRING)
            headerCellSousLot.setCellValue(isFecExport ? "LOT" : "SOUS-LOT")
            i++

            Cell headerCellZone = headerRow.createCell(i)
            headerCellZone.setCellStyle(cs)
            headerCellZone.setCellType(CellType.STRING)
            headerCellZone.setCellValue("ZONE")
            i++

            Cell headerCellComment = headerRow.createCell(i)
            headerCellComment.setCellStyle(cs)
            headerCellComment.setCellType(CellType.STRING)
            headerCellComment.setCellValue("COMMENT")
            i++

            if (!isFecExport) {
                Cell headerCellEnergyUse = headerRow.createCell(i)
                headerCellEnergyUse.setCellStyle(cs)
                headerCellEnergyUse.setCellType(CellType.STRING)
                headerCellEnergyUse.setCellValue("ENERGY_USE")
                i++
            }

            Integer numberOfHeadings = i
            i = 1
            datasets.each { RSEnvDatasetDto dataset ->
                Row datasetRow = sheet.createRow(i)
                Integer j = 0

                Cell datasetCellclass = datasetRow.createCell(j)
                datasetCellclass.setCellType(CellType.STRING)
                String materialClass
                if (isFecExport) {
                    materialClass = FrenchConstants.MAP_LOTS_TO_IFC.get(dataset.ref) ?: dataset.ref
                } else {
                    if (dataset.impactClass) {
                        materialClass = dataset.impactClass
                    } else {
                        materialClass = FrenchConstants.MAP_SOUS_LOTS_TO_IFC.get(dataset.ref) ?: dataset.ref
                    }
                }
                datasetCellclass.setCellValue(materialClass)
                j++

                Cell datasetCellmaterial = datasetRow.createCell(j)
                datasetCellmaterial.setCellType(CellType.STRING)
                datasetCellmaterial.setCellValue(dataset?.name)
                j++

                Cell datasetCellquantity = datasetRow.createCell(j)
                datasetCellquantity.setCellType(CellType.NUMERIC)
                datasetCellquantity.setCellValue(dataset.quantity)
                j++

                Cell datasetCellunit = datasetRow.createCell(j)
                datasetCellunit.setCellType(CellType.STRING)
                datasetCellunit.setCellValue(dataset.unit)
                j++

                Cell datasetCellSousLot = datasetRow.createCell(j)
                datasetCellSousLot.setCellType(CellType.STRING)
                datasetCellSousLot.setCellValue(dataset.ref)
                j++

                Cell datasetCellZone = datasetRow.createCell(j)
                datasetCellZone.setCellType(CellType.NUMERIC)
                datasetCellZone.setCellValue(dataset.zone)
                j++

                Cell datasetCellComment = datasetRow.createCell(j)
                datasetCellComment.setCellType(CellType.STRING)
                datasetCellComment.setCellValue(dataset.comment)
                j++

                if (!isFecExport && dataset.energyUseCategory) {
                    Cell datasetCellEnergyUse = datasetRow.createCell(j)
                    datasetCellEnergyUse.setCellType(CellType.STRING)
                    datasetCellEnergyUse.setCellValue(dataset.energyUseCategory)
                    j++
                }

                i++
            }

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }

            Sheet help = wb.createSheet()
            wb.setSheetName(wb.getSheetIndex(help), "@HELP")
            // TODO: Help sheet info
        }
        return wb
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def generateAPIExcel(Set<Dataset> datasets, Query query, Indicator indicator, Entity child) {
        Workbook wb

        if (datasets && query && indicator) {
            ResultCategory resultCategory = indicator.getResolveResultCategories(null)?.find({ it.resultCategoryId.contains("A1-A3") })
            CalculationRule calculationRule = indicator.getResolveCalculationRules(null)?.find({ it.calculationRuleId.contains("GWP") })

            wb = new HSSFWorkbook()

            CellStyle cs = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            cs.setFont(f)
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND)
            CellStyle ignored = wb.createCellStyle()
            Font ignoredFont = wb.createFont()
            ignoredFont.setColor(HSSFColor.HSSFColorPredefined.RED.index);
            ignored.setFont(ignoredFont)
            Sheet sheet = wb.createSheet()
            wb.setSheetName(wb.getSheetIndex(sheet), "response")
            Row headerRow = sheet.createRow(0)
            Integer i = 0

            List<String> headings = ["CLASS", "IFCMATERIAL", "THICKNESS", "RESULT_STATUS", "RESULT_ABSOLUTE", "RESULT_BY_VOLUME", "DATA_LABEL", "DATA_ID"]

            headings.each { String heading ->
                Cell headerCellclass = headerRow.createCell(i)
                headerCellclass.setCellStyle(cs)
                headerCellclass.setCellType(CellType.STRING)
                headerCellclass.setCellValue(heading)
                i++
            }

            Integer numberOfHeadings = headings.size()
            i = 1
            ResourceCache resourceCache = ResourceCache.init(datasets.toList())
            for (Dataset dataset: datasets)
            {
                Double resultForDataset = child.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRule.calculationRuleId)
                Row datasetRow = sheet.createRow(i)
                Integer j = 0

                Cell datasetCellclass = datasetRow.createCell(j)
                datasetCellclass.setCellType(CellType.STRING)
                if (dataset.trainingData?.get("CLASS")) {
                    datasetCellclass.setCellValue(dataset.trainingData.get("CLASS"))
                } else {
                    query.exportClasses.find { Map<String, Map<String, String>> exportClass ->
                        exportClass.values().find { Map<String, String> sectionAndQuestion ->
                            if (sectionAndQuestion.get(dataset.sectionId)?.equalsIgnoreCase(dataset.questionId)) {
                                datasetCellclass.setCellValue(exportClass.keySet().toList().get(0))
                                return true
                            }
                        }
                    }
                }
                j++

                Cell datasetCellmaterial = datasetRow.createCell(j)
                datasetCellmaterial.setCellType(CellType.STRING)
                datasetCellmaterial.setCellValue(dataset.trainingData?.get("RESOURCEID"))
                j++

                Cell datasetCellthickness = datasetRow.createCell(j)

                if (dataset.additionalQuestionAnswers?.get("thickness_mm")?.toString()?.isNumber()) {
                    datasetCellthickness.setCellType(CellType.NUMERIC)

                    if (dataset.additionalQuestionAnswers.get("thickness_mm").toString().isInteger()) {
                        datasetCellthickness.setCellValue(Integer.valueOf(dataset.additionalQuestionAnswers.get("thickness_mm").toString()))
                    } else if (dataset.additionalQuestionAnswers.get("thickness_mm").toString().isDouble()) {
                        datasetCellthickness.setCellValue(Double.valueOf(dataset.additionalQuestionAnswers.get("thickness_mm").toString()))
                    }
                } else {
                    datasetCellthickness.setCellType(CellType.STRING)
                    datasetCellthickness.setCellValue((String) dataset.additionalQuestionAnswers?.get("thickness_mm"))
                }
                j++

                Cell datasetCellstatus = datasetRow.createCell(j)
                datasetCellstatus.setCellType(CellType.STRING)
                if (resultForDataset != null) {
                    datasetCellstatus.setCellValue("SUCCESS")
                } else {
                    datasetCellstatus.setCellValue("IGNORED")
                    datasetCellstatus.setCellStyle(ignored)
                }
                j++

                Cell datasetCellabsolute = datasetRow.createCell(j)
                datasetCellabsolute.setCellType(CellType.NUMERIC)
                datasetCellabsolute.setCellValue(resultForDataset)
                j++

                Cell datasetCellByVolume = datasetRow.createCell(j)
                datasetCellByVolume.setCellType(CellType.NUMERIC)
                datasetCellByVolume.setCellValue(dataset.quantity != null && resultForDataset != null ? resultForDataset / dataset.quantity : null)
                j++

                Cell datasetCellLabel = datasetRow.createCell(j)
                datasetCellLabel.setCellType(CellType.STRING)
                datasetCellLabel.setCellValue(resourceCache.getResource(dataset)?.staticFullName)
                j++

                Cell datasetCellId = datasetRow.createCell(j)
                datasetCellId.setCellType(CellType.STRING)
                datasetCellId.setCellValue(optimiResourceService.getResourceByResourceAndProfileId(dataset.resourceId, dataset.profileId, null, Boolean.TRUE)?.id?.toString())
                i++
            }

            numberOfHeadings.times {
                sheet.autoSizeColumn(it)
            }
        }
        return wb
    }

    List<Document> getQueriesForNavBar(Entity entity, Indicator indicator, List<Feature> licensedFeatures, Boolean sortByIndicatorQuery) {
        String entityClass = entity?.entityClass
        List<IndicatorQuery> indicatorQueries = indicator?.indicatorQueries
        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
        List<Document> queries = []

        if (indicatorQueries) {
            List<IndicatorQuery> licensedQueries = licenseService.doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys(indicatorQueries, licensedFeatures?.collect({ it?.featureId }))
            List<String> queryIds = licensedQueries?.collect { it.queryId }

            if (queryIds) {
                if (entity && ("component".equals(entity.entityClass) || licensedFeatures?.find({ it.featureId == Feature.COMPONENT_ABILITY }))) {
                    Query.collection.find([queryId: [$in: queryIds], active: true], [queryId: 1, name: 1, appendNameByEntityType: 1])?.each { Document d ->
                        if (entityClass && d.appendNameByEntityType) {
                            d["localizedAppendNameByEntityType"] = d.appendNameByEntityType?.get(entityClass)?.get(language) ?: d.appendNameByEntityType?.get(entityClass)?.get("EN")
                        }

                        if (d.name) {
                            d["localizedName"] = d.name.get(language) ?: d.name.get("EN")
                        }
                        queries.add(d)
                    }
                } else {
                    Query.collection.find([queryId: [$in: queryIds], active: true, componentQuery: null], [queryId: 1, name: 1, appendNameByEntityType: 1])?.each { Document d ->
                        if (entityClass && d.appendNameByEntityType) {
                            d["localizedAppendNameByEntityType"] = d.appendNameByEntityType?.get(entityClass)?.get(language) ?: d.appendNameByEntityType?.get(entityClass)?.get("EN")
                        }

                        if (d.name) {
                            d["localizedName"] = d.name.get(language) ?: d.name.get("EN")
                        }
                        queries.add(d)
                    }
                }
            } else {
                loggerUtil.warn(log, "No active queries found for Indicator: " + indicator?.indicatorId)
                flashService.setErrorAlert("No active queries found for Indicator: " + indicator?.indicatorId, true)
            }
        }

        List<Document> sortedQueries = []

        if (sortByIndicatorQuery) {
            if (queries) {
                indicatorQueries?.each { IndicatorQuery ind ->
                    def query = queries.find({ it.queryId.toString() == ind.queryId })

                    if (query) {
                        sortedQueries.add(query)
                    }
                }
            }
            return sortedQueries
        } else {
            return queries
        }
    }

    /**
     * Get list of queries, that are licensed in entity
     * @param indicator
     * @param entity
     * @param onlyDesignLevel
     * @param checkHideQuestions hide the questions / sections defined in indicator {@link Indicator#hideQuestions}
     * @return
     */
    /*
        this method should be deprecated because entity?.features make additional request to DB and often it using in a loops
        For Entity page it was called from indicator.getQueryIds
     */

    @Deprecated
    @Secured(["ROLE_AUTHENTICATED"])
    List<Query> getQueriesByIndicatorAndEntity(Indicator indicator, Entity entity, Boolean onlyDesignLevel = false, Boolean checkHideQuestions = false) {
        Boolean isComponentEntity = entityService.isComponentEntity(entity)
        return getQueriesByIndicator(indicator, entity?.features, onlyDesignLevel, isComponentEntity, checkHideQuestions)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<String> getQueryIdsByIndicatorAndEntity(Indicator indicator, Entity entity) {
        return getQueriesByIndicatorAndEntity(indicator, entity)?.collect { it.queryId }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<String> getQueryIdsByIndicatorAndEntity(Indicator indicator, Entity entity, List<Feature> entityFeatures) {
        Boolean isComponentEntity = entityService.isComponentEntity(entity, entityFeatures)
        return getQueriesByIndicator(indicator, entityFeatures, false, isComponentEntity, false)?.collect { it.queryId }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Query> getQueriesByIndicatorAndEntity(Indicator indicator, Entity entity, List<Feature> entityFeatures) {
        Boolean isComponentEntity = entityService.isComponentEntity(entity, entityFeatures)
        return getQueriesByIndicator(indicator, entityFeatures, false, isComponentEntity, false)
    }

    /**
     * This method does not run license check for queries, sections, questions
     * @see #getQueriesByIndicator
     * @see #doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery
     * @param queryId
     * @param active
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    @Cacheable('query')
    Query getQueryByQueryId(String queryId, Boolean active = true) {
        if (queryId) {
            if (queryId == ADDITIONAL_QUESTIONS_QUERY_ID) {
                return resolveAdditionalQuestionsQuery()
            } else {
                return Query.findByQueryIdAndActive(queryId, active, [cache: true])
            }
        } else {
            return null
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Query getQueryByQueryIdWithoutCache(String queryId, Boolean active) {
        if (queryId) {
            if (queryId == ADDITIONAL_QUESTIONS_QUERY_ID) {
                return resolveAdditionalQuestionsQuery()
            } else {
                return Query.findByQueryIdAndActive(queryId, active)
            }
        } else {
            return null
        }
    }

    /**
     * This method does not run license check for queries, sections, questions
     * @see #getQueriesByIndicator
     * @see #doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery
     * @param queryIds
     * @param active
     * @return
     */
    @Secured(["ROLE_AUTHENTICATED"])
    @Cacheable('query')
    List<Query> getQueriesByQueryIds(List<String> queryIds, Boolean active = true) {
        long start = System.currentTimeMillis()
        List<Query> result = []
        if (!queryIds) {
            return Collections.emptyList()
        }
        if (queryIds.contains(ADDITIONAL_QUESTIONS_QUERY_ID)) {
            queryIds.removeAll(ADDITIONAL_QUESTIONS_QUERY_ID)
            result.add(resolveAdditionalQuestionsQuery())
        }
        if (queryIds) {
            result.addAll(Query.findAllByQueryIdInListAndActive(queryIds, active, [cache: true]))
        }
        if (log.isTraceEnabled()) {
            log.trace("resolved All Additional Questions Query  ${System.currentTimeMillis() - start}")
        }
        return result
    }

    @Cacheable('query')
    Query resolveAdditionalQuestionsQuery(Boolean active = true) {
        long start = System.currentTimeMillis()
        Query query = Query.findByQueryIdAndActive(
                ADDITIONAL_QUESTIONS_QUERY_ID, active, [cache: true]
        )

        if (query) {
            query = enhanceQueryQuestionsWithQueryAndSection(query)
        }
        log.trace("resolved Additional Questions Query  ${System.currentTimeMillis() - start}")
        return query
    }

    @Deprecated
    Query getAdditionalQuestionsQuery() {
        if (!additionalQuestionQuery || isAdditionalQuestionsQueryUpdated()) {
            try {
                Document q = Query.collection.findOne([queryId: ADDITIONAL_QUESTIONS_QUERY_ID, active: true])
                q.sections?.each { section ->
                    section.questions?.each { question ->
                        question.queryId = q.queryId
                        question.sectionId = section.sectionId
                    }
                }
                additionalQuestionQuery = new Query(q)
            } catch (Exception e) {

            }
        }
        return additionalQuestionQuery
    }

    private boolean isAdditionalQuestionsQueryUpdated() {
        if (additionalQuestionsQueryUpdated) {
            additionalQuestionsQueryUpdated = false
            return true
        } else {
            return false
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @Cacheable('query')
    def getQueryById(String id) {
        Query query = null
        if (id) {
            query = Query.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return query
    }

    @Secured(["ROLE_AUTHENTICATED"])
    @Cacheable('query')
    Query getQueryByIndicatorAndQueryId(Indicator indicator, String queryId) {
        List<String> queryIds = indicator?.indicatorQueries?.collect({ it.queryId })
        Query query

        if (queryIds) {
            query = Query.findByQueryIdInListAndQueryIdAndActive(queryIds, queryId, true)
        } else {
            loggerUtil.warn(log, "No active queries found for Indicator: " + indicator?.indicatorId)
            flashService.setErrorAlert("No active queries found for Indicator: " + indicator?.indicatorId, true)
        }
        return query
    }

    def Query getQueryByIndicatorAndQueryIdAndToken(Indicator indicator, queryId, token, Entity entity) {
        if (optimiSecurityService.tokenOk(token)) {
            def queries = getQueriesByIndicatorAndEntity(indicator, entity)
            Query query = queries?.find { q ->
                q.queryId == queryId
            }
            return query
        } else {
            throw new SecurityException("Trying to access without invalid token: " + token)
        }
    }

    def getQueryForEditing(ObjectId id) {
        if (id) {
            def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
            GMongo mongo = new GMongo()
            DB db = mongo.getDB(dbName)
            DBObject removeIdProjection = new BasicDBObject(["_id": 0, "importFile": 0, "active": 0, "importTime": 0, "lastUpdated": 0, "owner": 0])
            DBCursor query = db.query.find(["_id": id], removeIdProjection)
            return query
        } else {
            return null
        }

    }

    @Secured(["ROLE_AUTHENTICATED"])
    @Cacheable('query')
    def getQueriesByApplicationId(String applicationId) {
        return Query.findAllByApplicationId(applicationId)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllQueries() {
        return Query.list()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllQueriesAsDocuments() {
        return Query.collection.find()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getAllInactiveQueries() {
        return Query.collection.find(getAllInactiveQueriesQuery())?.toList()
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    Long getCountOfAllInactiveQueries() {
        return Query.collection.count(getAllInactiveQueriesQuery())
    }

    private Map getAllInactiveQueriesQuery() {
        [active: false]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'query', allEntries = true)
    def saveQuery(Query query) {
        if (query) {
            def activeQueries = Query.findAllByQueryIdAndActiveAndIdNotEqual(query.queryId, true, query.id)

            if (activeQueries && query.active) {
                query.errors.reject("query.too_many_active", "There can be only one active query with same queryId")
            } else {
                query.save(flush: true, failOnError: true)
            }
        }
        return query
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'query', allEntries = true)
    def importQuery(MultipartFile jsonFile) {
        File tempFile = File.createTempFile("query", ".json")
        jsonFile.transferTo(tempFile)
        tempFile.text = FileUtil.getJSONArrayStringFromJSONFile(tempFile)
        Map response = importQueryFromFile(tempFile, jsonFile.getOriginalFilename(), null, Boolean.TRUE)
        response.put("fileName", "File name: ${jsonFile?.originalFilename}.")
        return response
    }

    @CacheEvict(value = 'query', allEntries = true)
    def activateQuery(String id) {
        Query query = getQueryById(id)

        if (query) {
            def activeQueriesWithSameQueryId = Query.findAllByQueryIdAndActiveAndIdNotEqual(query.queryId, true, query.id)

            if (activeQueriesWithSameQueryId) {
                activeQueriesWithSameQueryId.each { Query q ->
                    q.active = Boolean.FALSE
                    q.save(flush: true)
                }
            }
            query.active = Boolean.TRUE
            query = query.save(flush: true)
        }
        return query
    }

    @CacheEvict(value = 'query', allEntries = true)
    def inactivateQuery(String id) {
        Query query = getQueryById(id)

        if (query && query.active) {
            query.active = Boolean.FALSE
            query = query.save(flush: true)
        }
        return query
    }

    private void loadApplicationFiltersForQuery(Query query) {
        if (query && query.applicationFilters) {
            String applicationId = query.applicationFilters.keySet().toList().get(0)

            if (applicationId) {
                Application application = applicationService.getApplicationByApplicationId(applicationId)

                if (application && application.defaultFilters) {
                    query.supportedFilters = application.defaultFilters
                }
            }
        }
    }

    @CacheEvict(value = 'query', allEntries = true)
    def importQueryFromFile(File file, String originalFileName = null, String id = null, Boolean activateNewQuery = Boolean.FALSE) {
        def cmdResponse
        Map returnable = [:]
        String duplicateError = ""
        if (file) {
            List<Query> existingQueries = new ArrayList<Query>(Query.findAll())
            def dbName = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbName")?.value
            def dbUsername = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbUsername")?.value
            def dbPassword = configurationService.getByConfigurationName(Constants.APPLICATION_ID, "dbPassword")?.value
            String usernamePasswordStr = (dbUsername && dbPassword) ? " --username $dbUsername --password $dbPassword" : ""
            def cmdToExecute = "mongoimport --db $dbName $usernamePasswordStr --collection query --jsonArray --file $file.path"
            cmdToExecute = id ? (cmdToExecute + " --upsert") : cmdToExecute
            def importFile

            try {
                if (file.text?.contains("–")) {
                    throw new Exception("Illegal character found: '–', please replace U+2013 dashes with '-' U+002D hyphen / minus.")
                }

                cmdResponse = shellCmdService.executeShellCmd(cmdToExecute)
                List<Query> newQueries = new ArrayList<Query>(Query.findAll())

                if (existingQueries) {
                    newQueries.removeAll(existingQueries)
                }
                def dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:sss")
                def createDate = new Date()
                def importTime = dateFormat.format(createDate)
                importFile = originalFileName ? originalFileName : file.getName()
                String queryId

                newQueries.each { Query query ->
                    queryId = query.queryId
                    String sectionId

                    Boolean duplicateSectionsInQuery = Boolean.FALSE
                    List<QuerySection> sections = query.sections
                    List<String> sectionIds = []
                    List<String> duplicateIds = []
                    sections?.each { QuerySection section ->
                        if (sectionIds.contains(section.sectionId)) {
                            duplicateSectionsInQuery = Boolean.TRUE
                            duplicateIds.add(section.sectionId)
                        } else {
                            sectionIds.add(section.sectionId)
                        }
                    }

                    if (!duplicateSectionsInQuery) {
                        Boolean duplicateQuestionsInSections = Boolean.FALSE
                        sections?.each { QuerySection section ->
                            List<Question> questions = section.questions
                            List<String> questionIds = []
                            questions?.each { Question question ->
                                if (questionIds.contains(question.questionId)) {
                                    duplicateQuestionsInSections = Boolean.TRUE
                                    duplicateIds.add(question.questionId)
                                    sectionId = section.sectionId
                                } else {
                                    questionIds.add(question.questionId)
                                }
                            }
                        }

                        if (!duplicateQuestionsInSections) {
                            query.importTime = importTime
                            query.importFile = importFile

                            if (activateNewQuery) {
                                query.active = Boolean.TRUE
                            }
                            cmdResponse = "${cmdResponse}, QueryId: ${queryId}"

                            if (query.validate()) {
                                if (query.applicationFilters) {
                                    loadApplicationFiltersForQuery(query)
                                }
                                query = query.save(flush: true, failOnError: true)
                                cmdResponse = "${cmdResponse}, ObjectId: ${query.id}"

                                if (queryId == ADDITIONAL_QUESTIONS_QUERY_ID) {
                                    additionalQuestionsQueryUpdated = true
                                }
                                if (activateNewQuery) {
                                    def sameQueries = existingQueries?.findAll({ eq -> eq.queryId.equals(query.queryId) && !eq.id?.equals(query.id) })

                                    sameQueries?.each { sq ->
                                        sq.active = Boolean.FALSE
                                        sq.save(flush: true, failOnError: true)
                                    }
                                }

                                if (query.carbonDesignerRegions?.find({ it.licenseKeyToExclusivelyUseThisCDRegion })) {
                                    query.carbonDesignerRegions.findAll({ it.licenseKeyToExclusivelyUseThisCDRegion })?.each { CarbonDesignerRegion carbonDesignerRegion ->
                                        Feature dynamicRegionLicenseFeature = featureService.getFeatureByFeatureId(carbonDesignerRegion.licenseKeyToExclusivelyUseThisCDRegion)

                                        if (!dynamicRegionLicenseFeature) {
                                            dynamicRegionLicenseFeature = new Feature()
                                            dynamicRegionLicenseFeature.featureId = carbonDesignerRegion.licenseKeyToExclusivelyUseThisCDRegion
                                            dynamicRegionLicenseFeature.featureClass = com.bionova.optimi.core.Constants.LicenseFeatureClass.SPECIAL.toString()
                                            dynamicRegionLicenseFeature.name = "Carbon Designer allows only ${carbonDesignerRegion.nameRegion?.get("EN")}"
                                            dynamicRegionLicenseFeature.regionReferenceId = carbonDesignerRegion.regionReferenceId
                                            featureService.saveFeature(dynamicRegionLicenseFeature)
                                        }
                                    }
                                }
                            } else {
                                cmdResponse = "Error in saving query: ${query.getErrors()}"
                                query.delete(flush: true)
                            }
                        } else {
                            duplicateError = "${duplicateError}Duplicate question ${duplicateIds} found in Query: ${queryId}, Section: ${sectionId}, Query removed<br/>"
                            query.delete(flush: true)
                        }
                    } else {
                        duplicateError = "${duplicateError}Duplicate section ${duplicateIds} found in Query: ${queryId}, Query removed<br/>"
                        query.delete(flush: true)
                    }
                }
            } catch (Exception e) {
                def mongo = new GMongo()
                def db = mongo.getDB(dbName)
                db.query.remove([importTime: null])
                def errorMessage = e.message
                loggerUtil.error(log, "Error in importing queries: " + errorMessage)
                flashService.setErrorAlert("Error in importing queries: $errorMessage", true)
                cmdResponse = "Probably you imported wrong type of json file. Errors is: " + errorMessage
            }
            file.delete()
        }
        returnable.put("cmdResponse", cmdResponse)
        returnable.put("duplicateError", duplicateError)
        return returnable
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    @CacheEvict(value = 'query', allEntries = true)
    def deleteQuery(Query query) {
        query.delete(flush: true, failOnError: true)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def deleteQueryById(String id) {
        if (id) {
            Query query = Query.findById(DomainObjectUtil.stringToObjectId(id))

            if (query) {
                deleteQuery(query)
            }
        }
    }

    def isDuplicateIfChosenQuestionIdsFound(queryId) {
        def query = getQueryByQueryId(queryId, true)
        def mainSections = query.sections
        def questionIds = []

        mainSections?.each { section ->
            section.questions?.each { question ->
                question.choices?.each { choice ->
                    choice.ifChosen?.each { ifChosenQuestion ->
                        questionIds.add(ifChosenQuestion.questionId)
                    }
                }
            }
        }
        def duplicates = false

        if (questionIds?.size() != questionIds.unique()?.size()) {
            duplicates = true
        }
        return duplicates
    }

    private List<Query> getQueries() {
        return Query.findAllByActive(true)
    }

    private List<Query> getInactiveQueries() {
        return Query.findAllByActive(false)
    }

    def resolveBasicQueryReadiness(Entity entity) {

        Query bQuery = null
        if (entity) {
            bQuery = getBasicQuery()
        }

        if (bQuery) {
            boolean ready = entity.resolveQueryReady(null, bQuery)

            if (ready) {
                if (entity.queryReady) {
                    entity.queryReady.put((bQuery.queryId), true)
                } else {
                    entity.queryReady = [(bQuery.queryId): true]
                }

            }
        }
    }

    List<String> getAllowedUnitsByQueryAndUserSettings(Query query, User user) {
        String unitSystem = user?.unitSystem
        List<String> units = []
        List<String> allowedUnit = query.allowedUnits
        if (allowedUnit) {
            if (UnitConversionUtil.UnitSystem.IMPERIAL.value.equals(unitSystem)) {
                units = allowedUnit.collect({ unitConversionUtil.europeanUnitToImperialUnit(it) })?.flatten()
            } else if (UnitConversionUtil.UnitSystem.METRIC.value.equals(unitSystem)) {
                units = allowedUnit
            } else {
                units = allowedUnit.collect({ unitConversionUtil.getUnitAndItsEquivalent(it) })?.flatten()
            }
        }
        return units?.unique()
    }

    /**
     *  Remove sections / questions in query that do not pass the licenseKeys and/or requiredLicenseKeys and/or disableForLicenseKeys requirements
     *
     * @param query
     * @param licensedFeatureIds
     */
    void doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery(Query query, List<String> licensedFeatureIds) {
        try {
            if (query) {
                if (query.sections) {
                    query.sections = doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForSections(query.sections, licensedFeatureIds)
                }

                if (query.includedSections) {
                    query.includedSections = doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForSections(query.includedSections, licensedFeatureIds)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery", e)
            flashService.setErrorAlert("Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForQuery: ${e.getMessage()}", true)
        }
    }

    /**
     *  Remove sections and questions in section that do not pass the licenseKeys and/or requiredLicenseKeys and/or disableForLicenseKeys requirements
     *
     * @param sections
     * @param licensedFeatureIds
     * @return sections after filter
     */
    List<QuerySection> doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForSections(List<QuerySection> sections, List<String> licensedFeatureIds) {
        try {
            if (sections) {
                sections = licenseService.doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys(sections, licensedFeatureIds)
                List<String> sectionToRemove = []
                sections?.each { QuerySection section ->
                    if (section?.questions) {
                        section.questions = licenseService.doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeys(section.questions, licensedFeatureIds)

                        if (!section.questions) {
                            // remove section if all questions have been filtered out
                            sectionToRemove.add(section?.sectionId)
                        } else if (section.useTableFormatting?.questionIds) {
                            List<String> questionIdsAfterFilter = section.questions?.collect({ it.questionId })
                            section.useTableFormatting?.questionIds = section.useTableFormatting?.questionIds?.findAll({ questionIdsAfterFilter?.contains(it) })
                        }
                    }
                }
                if (sectionToRemove) {
                    sections.removeIf({ sectionToRemove.contains(it.sectionId) })
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForSections", e)
            flashService.setErrorAlert("Error in doFilterByLicenseKeysAndRequiredLicenseKeysAndDisableForLicenseKeysForSections: ${e.getMessage()}", true)
        }
        return sections
    }

    void expandAllSectionsInQuery(Query query) {
        try {
            query?.sections?.each({ it ->
                it.expandable = false
                it.questions?.each { ques -> ques.expandable = false }
                it.sections?.each { subSection ->
                    subSection.expandable = false
                    subSection.questions?.each { subQues -> subQues.expandable = false }
                }
            })
        } catch (e) {
            loggerUtil.error(log, "Error in expandAllSectionsInQuery", e)
            flashService.setErrorAlert("Error in expandAllSectionsInQuery: ${e.getMessage()}", true)
        }
    }

    List<String> getAllSectionIdsInQuery(Query query) {
        return query?.allSections?.collect({ it.sectionId }) ?: []
    }

    Set<String> getAllQueryIdsWithZoneDataset(Indicator indicator) {
        return indicator?.indicatorQueries?.findAll { it.additionalQuestionIds?.contains(FrenchConstants.BUILDING_ZONES_FEC_QUESTIONID) }?.collect { it.queryId }?.toSet()
    }

    Set<String> getAllQueryIdsWithLotDataset(Indicator indicator) {
        return indicator?.indicatorQueries?.findAll { it.additionalQuestionIds?.contains(FrenchConstants.LOTS_FRANCE_EC_QUESTIONID) }?.collect { it.queryId }?.toSet()
    }

    String createResultsLink(Entity entity, String childEntityId, String indicatorId) {
        String link = "#"
        if (!entity || !childEntityId || !indicatorId) {
            return link
        }
        if (EntityClass.OPERATING_PERIOD.getType() == entity.entityClass) {
            link = grailsLinkGenerator.link(controller: 'operatingPeriod', action: 'results',
                    params: [entityId: entity.id, childEntityId: childEntityId, indicatorId: indicatorId])
        } else {
            link = grailsLinkGenerator.link(controller: 'design', action: 'results',
                    params: [entityId: entity.id, childEntityId: childEntityId, indicatorId: indicatorId])
        }
        return link
    }

    Map groupAdditionalCalculationScriptsByQuestions(Query query) {
        Map<String, String> questionsWithAdditionalCalculationScripts = null
        //will be grouped by questions for ease use in all pages
        if (query.linkedJavascriptRules) {
            List<String> uniqueQuestionIds = query.linkedJavascriptRules.collect { it.value }?.flatten()?.unique()

            questionsWithAdditionalCalculationScripts = uniqueQuestionIds.collectEntries { String questionId ->
                [(questionId), query.linkedJavascriptRules.find { it.value.contains(questionId) }.key]
            }
        }
        return questionsWithAdditionalCalculationScripts
    }

    String getLocalizedNoResourceFoundText(Query query) {
        if (!query) {
            return null
        }
        String localizedText = query.getLocalizedNoResourceFoundText()
        localizedText = localizedLinkService.getTransformStringIdsToLinks(localizedText)
        return localizedText
    }

    String getLocalizedPurpose(Query query) {
        if (!query) {
            return null
        }
        String localizedPurpose = query.getLocalizedPurpose()
        localizedPurpose = localizedLinkService.getTransformStringIdsToLinks(localizedPurpose)
        return localizedPurpose
    }

    String getLocalizedHelp(Query query) {
        if (!query) {
            return null
        }
        String localizedHelp = query.getLocalizedHelp()
        localizedHelp = localizedLinkService.getTransformStringIdsToLinks(localizedHelp)
        return localizedHelp
    }

    String getLocalizedhelpByEntityType(Query query, String entityType) {
        if (!query) {
            return null
        }
        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
        String localizedhelpByEntityType = query.helpByEntityType?.get(entityType)?.get(language) ?:
                query.helpByEntityType?.get(entityType)?.get("EN")
        localizedhelpByEntityType = localizedLinkService.getTransformStringIdsToLinks(localizedhelpByEntityType)
        return localizedhelpByEntityType
    }

    String getLocalizedPurposeByEntityType(Query query, String entityType) {
        if (!query) {
            return null
        }
        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
        String localizedPurposeByEntityType = query.purposeByEntityType?.get(entityType)?.get(language) ?:
                query.purposeByEntityType?.get(entityType)?.get("EN")
        localizedPurposeByEntityType = localizedLinkService.getTransformStringIdsToLinks(localizedPurposeByEntityType)
        return localizedPurposeByEntityType
    }

    String getLocalizedAppendNameByEntityType(Query query, String entityType) {
        if (!query) {
            return null
        }
        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
        String LocalizedAppendNameByEntityType = query.appendNameByEntityType?.get(entityType)?.get(language)
        if (LocalizedAppendNameByEntityType) {
            return LocalizedAppendNameByEntityType
        } else {
            return query.appendNameByEntityType?.get(entityType)?.get("EN")
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Query> getAllQueriesForDatasets(Set<Dataset> datasets, Boolean active = true) {
        Set<String> queryIds = datasets*.queryId as Set
        return getAllQueriesByIds(queryIds, active)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Query> getAllQueriesByIds(Set<String> queryIds, Boolean active = true) {

        List<Query> queryList = []

        if (!queryIds) {
            return queryList
        }

        queryList = Query.findAllByQueryIdInListAndActive(queryIds, active, [cache: true])
        Query additionalQuestionsQuery = queryList?.find { it.queryId == ADDITIONAL_QUESTIONS_QUERY_ID}
        enhanceQueryQuestionsWithQueryAndSection(additionalQuestionsQuery)

        return queryList
    }

    private Query enhanceQueryQuestionsWithQueryAndSection(Query query) {

        query?.sections?.each { section ->
            section.questions?.each { question ->
                question.queryId = query.queryId
                question.sectionId = section.sectionId
            }
        }
        return query
    }
    
    /**
     * Prepare unique Map of all additional calc params from all questions
     * @param query
     * @return all additional calc params from Query
     */
    @CompileStatic
    Map<String, Map<String, List<String>>> getAdditionalCalcParamsFromQuery(Query query){
        List<Question> questions = query.getAllQuestions()
        Map<String, Map<String, List<String>>> additionalCalcParamsForQuery = [:]

        for (Question question in questions) {
            Map<String, Map<String, List<String>>> paramsFromQuestion = question?.javascriptAdditionalCalcParams

            if(paramsFromQuestion){
                for (Map.Entry<String, Map<String, List<String>>> queryEntry : paramsFromQuestion.entrySet()){
                    Map<String, List<String>> valuesForQuery = additionalCalcParamsForQuery.get(queryEntry.getKey())

                    if (valuesForQuery){
                        for (Map.Entry<String, List<String>> sectionEntry : queryEntry.value.entrySet()){
                            List<String> valuesForSection = valuesForQuery.get(sectionEntry.getKey())

                            if(valuesForSection){
                                valuesForSection.addAll(sectionEntry.value)
                                valuesForSection.unique()
                            } else {
                                valuesForQuery.put(sectionEntry.getKey(), sectionEntry.getValue())
                            }
                        }
                    } else {
                        additionalCalcParamsForQuery.put(queryEntry.getKey(), queryEntry.getValue())
                    }
                }
            }
        }

        return additionalCalcParamsForQuery
    }

    /**
     *
     * @param query - source query where we complete iteration by all questions
     * @param targetQueryId - another query, referenced by questions from the current one
     * @param additionalCalcParamsFromQuery - can be parameters from single question (or for multiple), if it's not set
     * then will be prepared unique map for whole source query
     * @return
     */
    @CompileStatic
    Map<String, List<String>> getAdditionalCalcFromQueryForParticularQuery(Query query, String targetQueryId = null,
                                                                           Map<String, Map<String, List<String>>> additionalCalcParamsFromQuery = null){
        if (!additionalCalcParamsFromQuery) {
            additionalCalcParamsFromQuery = getAdditionalCalcParamsFromQuery(query)
        }

        return additionalCalcParamsFromQuery?.get(targetQueryId ?: query.queryId)
    }

    @CompileStatic
    List<Query> getQueriesForEntity(Entity entity){
        List<Query> queriesForEntity = []
        List<Indicator> indicators

        if(entity.parentEntityId){
            indicators = entity.getParentById()?.getIndicators()
        } else {
            indicators = entity.getIndicators()
        }

        if (indicators) {
            Map<String, Query> queries = [:]

            for (Indicator indicator in indicators) {
                if ("complex" == indicator.assessmentMethod) {
                    indicator.getQueries(entity)?.each { Query query ->
                        queries.put(query.queryId, query)
                    }
                }
            }

            queriesForEntity.addAll(queries.values())
        }

        return queriesForEntity
    }
}
