/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.Constants.IndicatorUse
import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Benchmark
import com.bionova.optimi.core.domain.mongo.BenchmarkSettings
import com.bionova.optimi.core.domain.mongo.BenchmarkValue
import com.bionova.optimi.core.domain.mongo.CalculationProcess
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.CalculationTotalResult
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorExpandFeature
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.IndicatorReport
import com.bionova.optimi.core.domain.mongo.LcaChecker
import com.bionova.optimi.core.domain.mongo.LcaCheckerResult
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Note
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.PredefinedScope
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ResultFormatting
import com.bionova.optimi.core.domain.mongo.SimulationTool
import com.bionova.optimi.core.domain.mongo.Task
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlowForEntity
import com.bionova.optimi.core.service.CalculationMessageService
import com.bionova.optimi.core.service.NmdElementService
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.taglib.IndicatorTagLib
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.bionova.optimi.domainDTO.LicenseDTO
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonBuilder
import groovy.json.StringEscapeUtils
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.validator.routines.EmailValidator
import org.bson.Document
import org.bson.types.ObjectId
import org.grails.datastore.mapping.core.Session
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpSession
import java.text.DecimalFormat
import java.text.NumberFormat
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.Constants.EntityUserType
import com.bionova.optimi.core.Constants.PortfolioType

/**
 * @author Pasi-Markus Mäkelä
 *
 */
class EntityController extends AbstractDomainObjectController {

    def entityService
    def indicatorService
    def datasetService
    def queryService
    def optimiResourceService
    def configurationService
    def portfolioService
    def licenseService
    def systemService
    def channelFeatureService
    def localeResolverUtil
    def denominatorUtil
    def questionService
    def calculateDifferenceUtil
    def newCalculationServiceProxy
    def accountService
    def resourceTypeService
    def resultFormattingResolver
    def featureService
    def helpConfigurationService
    def lcaCheckerService
    def unitConversionUtil
    def workFlowService
    def activeCampaignService
    def flashService
    def resultCategoryService
    def calculationRuleService
    def valueReferenceService
    def calculationProcessService
    def querySectionService
    def denominatorService
    def indicatorReportService
    def resourceService
    ScopeFactoryService scopeFactoryService
    CalculationMessageService calculationMessageService
    NmdElementService nmdElementService

    @Autowired
    EmailConfiguration emailConfiguration
    /*
     * Empty implementation because of inheritance
     */

    def list() {
        redirect controller: "main", action: "list"
    }

    def licenseRequestForm() {
        Entity entity = entityService.getEntityById(params.entityId)
        Boolean isQuote = params.quote ? Boolean.TRUE : Boolean.FALSE
        List<Indicator> designIndicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.DESIGN.toString(), entity, false)
        List<Indicator> operatingIndicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.OPERATING.toString(), entity, false)
        User user = userService.getCurrentUser()
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
        def systemLocales = optimiResourceService.getSystemLocales(channelFeature)
        Locale selectedLocale = localeResolverUtil.resolveLocale(session, request, response)
        List<String> indicators

        if (params.indicator) {
            indicators = params.list("indicator")
        }
        [entity: entity, isQuote: isQuote, designIndicators: designIndicators, operatingIndicators: operatingIndicators,
         user  : user, locales: systemLocales, locale: selectedLocale, indicators: indicators]
    }

    def sendTrialLicenseRequest() {
        String entityId = params.entityId
        String comments = params.comments

        if (!comments) {
            flash.fadeErrorAlert = message(code: "licenseRequest.comments.nullable")
            redirect action: "licenseRequestForm", params: [entityId: entityId]
        } else {
            if (entityId) {
                Entity entity = entityService.getEntityById(entityId, session)

                if (entity) {
                    User user = userService.getCurrentUser(Boolean.TRUE)
                    String messageSubject = "TRIAL LICENSE REQUEST FOR ${user?.name} (${user?.username}) ENTITY ${entity?.name}"
                    String messageBody = "Entity name: ${entity?.name}<br />" +
                            "User name: ${user?.name}<br />" +
                            "User email: ${user?.username}<br />" +
                            "User phone: ${user?.phone}<br />" +
                            "User organization: ${user?.organizationName}<br />" +
                            "Comments: ${comments}"
                    optimiMailService.sendMail({
                        to emailConfiguration.helloEmail
                        from emailConfiguration.contactEmail
                        replyTo emailConfiguration.supportEmail
                        subject(messageSubject)
                        html(messageBody)
                    }, emailConfiguration.supportEmail)

                    user.phone = params.phone
                    user.organizationName = params.organizationName
                    user.localeString = params.localeString
                    userService.updateUser(user)
                }
            }
            flash.fadeSuccessAlert = message(code: "licenseRequest.sent")
            redirect action: "show", params: [entityId: entityId, trialRequestSent: true]
        }
    }

    def setNotifier() {
        Entity entity = entityService.getEntityById(params.id, session)

        if (entity && !entity.notifiedUsername) {
            entity.notifiedUsername = params.username
            entityService.updateEntity(entity, Boolean.FALSE)
        }
        redirect action: "users", id: params.id
    }

    def unsetNotifier() {
        Entity entity = entityService.getEntityById(params.id, session)

        if (entity && entity.notifiedUsername) {
            entity.notifiedUsername = null
            entityService.updateEntity(entity, Boolean.FALSE)
        }
        redirect action: "users", id: params.id
    }

    def sendQuoteRequest() {
        String entityId = params.entityId
        String comments = params.comments
        String indicator = params.indicator
        String volume = params.expectedVolume

        if (!comments || !volume) {
            flash.fadeErrorAlert = message(code: "quoteRequest.params.missing")
            redirect action: "licenseRequestForm", params: [entityId: entityId, quote: true]
        } else {
            if (entityId) {
                Entity entity = entityService.getEntityById(entityId, session)

                if (entity) {
                    List<String> indicators = params.indicator ? params.list("indicator") : []
                    User user = userService.getCurrentUser(Boolean.TRUE)
                    user.phone = params.phone
                    user.organizationName = params.organizationName
                    user.localeString = params.localeString

                    String messageSubject = "QUOTE REQUEST FOR ${user?.name} (${user?.username}) ENTITY ${entity?.name}"
                    String messageBody = "Entity name: ${entity?.name}<br />" +
                            "User name: ${user?.name}<br />" +
                            "User email: ${user?.username}<br />" +
                            "User phone: ${user?.phone}<br />" +
                            "User organization: ${user?.organizationName}<br />" +
                            "User registration motive: ${user?.registrationMotive}<br />" +
                            "User country: ${params.localeString}<br />" +
                            "Comments: ${comments}<br />" +
                            "Indicators interested in: ${indicators}<br />" +
                            "Expected volume: ${volume}<br />" +
                            "Integration needs: ${params.integrationNeeds ? 'Yes' : 'No'}<br />" +
                            "Customisation needs: ${params.customisationNeeds ? 'Yes' : 'No'}<br />"
                    optimiMailService.sendMail({
                        to emailConfiguration.helloEmail
                        from emailConfiguration.contactEmail
                        replyTo emailConfiguration.supportEmail
                        subject(messageSubject)
                        html(messageBody)
                    }, emailConfiguration.supportEmail)
                    userService.updateUser(user)
                }
            }
            flash.fadeSuccessAlert = message(code: "quoteRequest.sent")
            redirect action: "show", params: [entityId: entityId, trialRequestSent: true]
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def showjson() {
        def entity = Entity.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(params.id)])

        if (entity) {
            File tempFile = File.createTempFile("entity", ".txt")
            tempFile.write(StringEscapeUtils.unescapeJavaScript(new JsonBuilder(entity).toPrettyString()))
            //tempFile.withWriter { it << entity.toString() }
            response.setHeader("Content-disposition", "attachment; filename=${tempFile.name}")
            response.contentType = "text/plain" // or whatever content type your resources are
            response.outputStream << tempFile.bytes
            response.outputStream.flush()
            tempFile.delete()
        }
    }

    @Secured(["ROLE_SUPER_USER"])
    def importJson() {
        String parentId = params.parentEntityId
        String designName = params.designName
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile entityFile = request.getFile("entityFile")

            if (!entityFile || entityFile.empty) {
                flash.errorAlert = message(code: "import.file.required")
            } else {
                entityService.importDesign(entityFile, parentId, designName)
            }
        }
        redirect action: 'show', params: [entityId: parentId]
    }

    def doComplexAssessment() {
        def childEntityId = params.childEntityId
        def indicatorId = params.indicatorId
        Entity parentEntity = entityService.readEntity(childEntityId)?.getParentById()
        newCalculationServiceProxy.calculate(childEntityId, indicatorId, parentEntity)
        redirect action: "show", params: [entityId: params.entityId]
    }

    /**
     * Returns the entity with given id.
     */
    def show() {
        long now = System.currentTimeMillis()
        systemService.logMemoryUsage()
        loggerUtil.debug(log, "Start of EntityController.show")
        Entity entity

        Boolean removePrompt = params.boolean("removePrompt")

        if (removePrompt) {
            session?.removeAttribute("showLoginPrompt")
            session?.removeAttribute("testLoginPrompt")
            session?.setAttribute("noPrompt", "true")
        }

        if (params.entityId) {
            entity = entityService.getEntityById(params.entityId)
        } else {
            entity = entityService.getEntityById(params.id)
        }

        if (entity) {
            User user = userService.getCurrentUser(Boolean.TRUE)
            List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
            List<License> expiredLicensesForEntity = licenseService.getExpiredLicensesForEntity(entity)
            List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

            if (Constants.EntityClass.PORTFOLIO.toString() == entity.entityClass) {
                def portfolio = entity.portfolio
                def tasks
                def completedTasks
                def totalNoteAmount
                def attachments
                def notes
                def chosenIndicatorId = params.indicatorId

                String activeTab = params.activeTab ? params.activeTab : "info"

                if (entity.entityClassResource?.showTasks) {
                    List<Task> allEntityTasks = entity.tasks

                    if (allEntityTasks) {
                        tasks = allEntityTasks.findAll({ it != null && !it.completed })
                        completedTasks = allEntityTasks.findAll({ it != null && it.completed })
                    }
                    List<Note> allNotes = entity.notes

                    if (allNotes) {
                        totalNoteAmount = allNotes.size()
                        attachments = allNotes.findAll({ it.file })?.sort({ Note note -> note.comment?.toLowerCase() })
                        notes = allNotes.findAll({ !it.file })
                    }
                }
                def model = [entity     : entity, portfolio: portfolio, tasks: tasks, completedTasks: completedTasks, totalNoteAmount: totalNoteAmount,
                             attachments: attachments, notes: notes, activeTab: activeTab, chosenIndicatorId: chosenIndicatorId, licensesToShow: validLicenses ?: []]

                if ("design" == portfolio?.type) {
                    model.putAll(getDesignPortfolioModel(portfolio))
                } else if ("operating" == portfolio?.type) {
                    model.putAll(getOperatingPortfolioModel(portfolio))
                    Boolean exportLicensed = licenseService.isFeatureLicensed(Feature.PORTFOLIO_EXCEL_EXPORT, entity?.type, entity?.entityClass)
                    model.put("exportLicensed", exportLicensed)
                }
                return model
            } else {
                List<Entity> designs = entityService.getChildEntitiesForShowing(entity, EntityClass.DESIGN.getType(), null, true)
                boolean enableAverageDesignFeature = entityService.checkIfAverageDesignFeatureEnabled(designs?.findAll({ Entity it -> !it.deleted })?.size(), entity)

                List<Entity> operatingPeriods = entityService.getChildEntitiesForShowing(entity, EntityClass.OPERATING_PERIOD.getType(), null, true)
                String entityType = entity.typeResourceId
                Integer currentHighestRiba = designs?.findAll({ it.ribaStage != null && !it.deleted })?.collect({ it.ribaStage })?.max()?.intValue()
                Map<Integer, String> ribaStages = Constants.DesignRIBAStages.map()
                ribaStages.put(-1, "entity.form.component")

                def noLicenses = true
                loggerUtil.debug(log, "Start of EntityController.show.validDesignLicenses")
                def validDesignLicenses = []
                def validOperatingLicenses = []

                if (validLicenses) {
                    for (License license in validLicenses) {
                        def licensedIndicators = license.licensedIndicators

                        if (licensedIndicators) {
                            def indicators = indicatorService.getIndicatorsByEntityTypeAndClass(licensedIndicators, entity)

                            if (indicators) {
                                for (Indicator indicator in indicators) {
                                    if (IndicatorUse.DESIGN.toString() == indicator.indicatorUse) {
                                        validDesignLicenses.add(license)
                                        break
                                    }
                                }

                                for (Indicator indicator in indicators) {
                                    if (IndicatorUse.OPERATING.toString() == indicator.indicatorUse) {
                                        validOperatingLicenses.add(license)
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                if (validDesignLicenses) {
                    noLicenses = false
                }

                if (noLicenses && validOperatingLicenses) {
                    noLicenses = false
                }
                loggerUtil.debug(log, "End of EntityController.show.validOperatingLicenses")
                List<Indicator> selectedDesignIndicators
                List<Indicator> selectedOperatingIndicators
                boolean benchmarkLicensed = entityService.isBenchmarkLicensed(featuresAvailableForEntity)
                boolean compareLicensed = entityService.isCompareLicensed(featuresAvailableForEntity)
                boolean oldGraphsLicensed = entityService.isOldGraphsLicensed(featuresAvailableForEntity)
                def designPortfolios
                def operatingPortfolios
                def designBenchmarks
                def operatingBenchmarks
                def preventMultipleDesignsLicened = entity.getPreventMultipleDesignsLicensed(validLicenses)
                def carbonDesignerOnlyLicensed = entity.getCarbonDesignerOnlyLicensed(validLicenses)
                boolean carbonDesignerAndResultsOnlyLicensed = entityService.getCarbonDesignerAndResultsOnlyLicensed(validLicenses)

                if (benchmarkLicensed) {
                    def benchmarkPortfolios = portfolioService.getBenchmarkPortfoliosForEntity(entity)

                    if (benchmarkPortfolios) {
                        if (entity.benchmarkIds && entity.benchmarkIds.size() > 0) {
                            def benchmarks = entity.benchmarks
                            designBenchmarks = benchmarks.findAll({ BenchmarkValue b -> b.type == PortfolioType.DESIGN.toString() })
                            operatingBenchmarks = benchmarks.findAll({ BenchmarkValue b -> b.type == PortfolioType.OPERATING.toString() })
                        }
                        designPortfolios = benchmarkPortfolios.findAll({ Portfolio portfolio -> PortfolioType.DESIGN.toString() == portfolio.type })
                        operatingPortfolios = benchmarkPortfolios.findAll({ Portfolio portfolio -> PortfolioType.OPERATING.toString() == portfolio.type })
                    }
                }
                selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.DESIGN.toString())
                selectedOperatingIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.OPERATING.toString())
                def expiredLicenses = []
                Boolean showQuoteBar = Boolean.FALSE
                Boolean showLicenseKeyWindow = Boolean.FALSE
                ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
                Boolean hideEcommerce = channelFeatureService.getHideEcommerceStatus(channelFeature)
                String quoteUrl
                String webinarUrl
                String expertUrl
                boolean disableAverageFeatureInMenu = selectedDesignIndicators.any{Indicator it->!it.allowCreatingAverageDesign}

                if (channelFeature) {
                    quoteUrl = channelFeature.quoteUrl
                    webinarUrl = channelFeature.webinarUrl
                    expertUrl = channelFeature.expertUrl
                }

                List<License> expiredDesignLicenses = licenseService.getExpiredDesignLicenses(entity, expiredLicensesForEntity)

                if (expiredDesignLicenses) {
                    noLicenses = false
                    expiredLicenses.addAll(expiredDesignLicenses)
                }

                List<License> expiredOperatingLicenses = licenseService.getExpiredOperatingLicenses(entity, expiredLicensesForEntity)

                if (expiredOperatingLicenses) {
                    noLicenses = false
                    expiredLicenses.addAll(expiredOperatingLicenses)
                }
                def licensesToShow = []
                loggerUtil.debug(log, "Start of EntityController.show.resolveNonProdDesignLicenses")

                if (validDesignLicenses) {
                    licensesToShow.addAll(validDesignLicenses)
                }
                loggerUtil.debug(log, "End of EntityController.show.resolveNonProdDesignLicenses")
                loggerUtil.debug(log, "Start of EntityController.show.resolveNonProdOperatingLicenses")

                if (validOperatingLicenses) {
                    licensesToShow.addAll(validOperatingLicenses)
                }
                licensesToShow = licensesToShow.unique()
                loggerUtil.debug(log, "End of EntityController.show.resolveNonProdOperatingLicenses")
                boolean reportLicensed = licenseService.getReportIndicatorLicensed(entity, validLicenses)
                def mainLevelButtons = []
                Boolean mainLevelReport

                if (reportLicensed) {
                    List<Indicator> reportIndicators = entityService.getReportIndicators(entity, validLicenses)

                    reportIndicators?.each { Indicator indicator ->
                        if (indicator.report?.mainLevelReport) {
                            mainLevelReport = true
                        } else {
                            mainLevelReport = false
                        }
                        if (mainLevelReport && indicator.report?.mainLevelButtonTitle && !mainLevelButtons.contains(indicator.report.localizedMainLevelButtonTitle)) {
                            mainLevelButtons.add(indicator.report.localizedMainLevelButtonTitle)
                        }
                    }
                }

                List<LicenseDTO> managedLicenses
                List<LicenseDTO> relevantLicenses = licenseService.getValidLicenseDTOs()

                if (noLicenses && user?.managedLicenseIds && user?.usableLicenseIds) {
                    List<LicenseDTO> usableAndManagedLicenses = licenseService.getUsableAndManagedLicenseDTOs(user, relevantLicenses)
                    managedLicenses = usableAndManagedLicenses?.findAll({
                        (!it.compatibleEntityClasses || it.compatibleEntityClasses.contains(entity.entityClass)) && !it.planetary
                    })
                } else if (noLicenses && user?.usableLicenseIds) {
                    managedLicenses = licenseService.getUsableLicenseDTOs(user, relevantLicenses)?.findAll({
                        (!it.compatibleEntityClasses || it.compatibleEntityClasses.contains(entity.entityClass)) && !it.planetary
                    })
                } else if (noLicenses && user?.managedLicenseIds) {
                    managedLicenses = licenseService.getManagedLicenseDTOs(user, relevantLicenses)?.findAll({
                        (!it.compatibleEntityClasses || it.compatibleEntityClasses.contains(entity.entityClass)) && !it.planetary
                    })
                }

                List<Task> completedTasks
                List<Task> tasks
                List<Note> notes
                List<Note> attachments
                Integer totalNoteAmount

                loggerUtil.debug(log, "Start of EntityController.show.resolveTasks")
                if (entity.entityClassShowTasks) {
                    List<Task> parentTasks = entity.targetedTasks
                    List<Task> allEntityTasks = []

                    if (parentTasks) {
                        allEntityTasks.addAll(parentTasks)
                    }

                    designs?.each {
                        List<Task> childTasks = it.targetedTasks

                        if (childTasks) {
                            allEntityTasks.addAll(childTasks)
                        }
                    }

                    operatingPeriods?.each {
                        List<Task> childTasks = it.targetedTasks

                        if (childTasks) {
                            allEntityTasks.addAll(childTasks)
                        }
                    }

                    if (allEntityTasks) {
                        tasks = allEntityTasks.findAll({ it != null && !it.completed })
                        completedTasks = allEntityTasks.findAll({ it != null && it.completed })
                    }
                    List<Note> allNotes = entity.notes

                    if (allNotes) {
                        totalNoteAmount = allNotes.size()
                        attachments = allNotes.findAll({
                            it.file
                        })?.sort({ Note note -> note.comment?.toLowerCase() })
                        notes = allNotes.findAll({ !it.file })
                    }
                }
                loggerUtil.debug(log, "End of EntityController.show.resolveTasks")
                loggerUtil.debug(log, "End of EntityController.show")
                Map<String, List<ImportMapper>> designIndicatorIdsWithImportMappers = [:]
                Map<String, List<ImportMapper>> operatingIndicatorIdsWithImportMappers = [:]

                if (selectedDesignIndicators) {
                    List<ImportMapper> designImportMappers = entity?.designImportMappers

                    if (designImportMappers && !designImportMappers.isEmpty()) {
                        selectedDesignIndicators.collect({ Indicator ind -> ind.indicatorId })?.each { String indicatorId ->
                            List<ImportMapper> foundImportMappers = designImportMappers.findAll({
                                !it.compatibleIndicators || it.compatibleIndicators?.contains(indicatorId)
                            })

                            if (foundImportMappers && !foundImportMappers.isEmpty()) {
                                designIndicatorIdsWithImportMappers.put(indicatorId, foundImportMappers)
                            }
                        }
                    }
                }

                if (selectedOperatingIndicators) {
                    List<ImportMapper> operatingImportMappers = entity?.operatingImportMappers
                    if (operatingImportMappers && !operatingImportMappers.isEmpty()) {
                        selectedOperatingIndicators.collect({ Indicator ind -> ind.indicatorId })?.each { String indicatorId ->
                            List<ImportMapper> foundImportMappers = operatingImportMappers.findAll({
                                !it.compatibleIndicators || it.compatibleIndicators?.contains(indicatorId)
                            })
                            if (foundImportMappers && !foundImportMappers.isEmpty()) {
                                operatingIndicatorIdsWithImportMappers.put(indicatorId, foundImportMappers)
                            }
                        }
                    }
                }
                List<Indicator> operatingDrawableEntityIndicators = []
                List<Indicator> indicatorsWithDeprecationNote = []
                List<Indicator> drawableEntityIndicators = []
                Map<Indicator, List<Map<String, String>>> drawablePiesPerIndicator = [:]
                Map<Indicator, List<Map<String, String>>> drawablePiesPerIndicatorAndPeriod = [:]
                Map<Indicator, List<Map<String, String>>> drawableGraphForCarbonBenchmark = [:]

                def firstReadyDesignName
                def firstReadyDesignId
                def firstReadyOperatingPeriodName
                def firstReadyOperatingPeriodId
                def readyChosenDesignId
                def readyChosenDesignName
                def readyDesignHighestRibaStageName
                def readyDesignHighestRibaStageId

                List<String> userIndicatorIds = entity.userIndicators?.get(user?.id.toString())
                if (userIndicatorIds) {
                    if (selectedDesignIndicators) {
                        selectedDesignIndicators = selectedDesignIndicators.findAll({
                            userIndicatorIds.contains(it.indicatorId)
                        })
                    }

                    if (selectedOperatingIndicators) {
                        selectedOperatingIndicators = selectedOperatingIndicators.findAll({
                            userIndicatorIds.contains(it.indicatorId)
                        })
                    }
                }

                Map<String, List<Query>> queriesForSelectedOperatingIndicators = [:]

                if (selectedOperatingIndicators && operatingPeriods) {
                    selectedOperatingIndicators.each { Indicator indicator ->
                        if(indicator.deprecationNote){
                            indicatorsWithDeprecationNote.add(indicator)
                        }
                        if (!indicator.nonNumericResult) {
                            List<Query> indQueries = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity)
                            queriesForSelectedOperatingIndicators.put(indicator.indicatorId, indQueries)
                            List<String> indicatorQueryIds = indQueries?.collect { it.queryId }
                            Integer ready = 0
                            List<Map<String, String>> drawablePeriodsForThisIndicator = []

                            operatingPeriods.each { Entity period ->
                                def readyQueries = period.queryReadyPerIndicator?.get(indicator.indicatorId)

                                indQueries?.each { Query query ->
                                    if (readyQueries?.get(query?.queryId) == null) {
                                        period.resolveQueryReady(indicator, query)
                                    }
                                }

                                if (period.isIndicatorReady(indicator, indicatorQueryIds)) {
                                    Map<String, String> readyPeriod = [entityId: period.id.toString(), name: period?.nameStringWithoutQuotes, nameForShow: period?.operatingPeriodAndNamed, type: "${message(code: "operating")}"]
                                    drawablePeriodsForThisIndicator.add(readyPeriod)

                                    if (!firstReadyOperatingPeriodId) {
                                        firstReadyOperatingPeriodId = period.id
                                        firstReadyOperatingPeriodName = period.operatingPeriodAndName
                                    }
                                    ready++
                                }
                            }

                            if (indicator.reportVisualisation?.contains("pieChart") && ready >= 1 && !"benchmark".equalsIgnoreCase(indicator.visibilityStatus)) {
                                drawablePiesPerIndicatorAndPeriod.put(indicator, drawablePeriodsForThisIndicator)
                            }

                            if (indicator.reportVisualisation && CollectionUtils.containsAny(["entityPageChart", "detailedEntityCharts"], indicator.reportVisualisation) && (ready >= 1) && !"benchmark".equalsIgnoreCase(indicator.visibilityStatus)) {
                                operatingDrawableEntityIndicators.add(indicator)
                            }
                        }
                    }
                }

                Map<String, List<Query>> queriesForSelectedDesignIndicators = [:]

                if (selectedDesignIndicators && designs) {
                    selectedDesignIndicators.each { Indicator indicator ->
                        if(indicator.deprecationNote){
                            indicatorsWithDeprecationNote.add(indicator)
                        }
                        if (!indicator.nonNumericResult) {
                            List<Query> indQueries = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity)
                            queriesForSelectedDesignIndicators.put(indicator.indicatorId, indQueries)
                            List<String> indicatorQueryIds = indQueries?.collect { it.queryId }
                            Integer ready = 0
                            List<Map<String, String>> drawableDesignsForThisIndicator = []


                            designs.grep().findAll { Entity design -> !(design.isHiddenDesign || design.deleted) }.each { Entity design ->
                                def readyQueries = design.queryReadyPerIndicator?.get(indicator.indicatorId)

                                indQueries?.each { Query query ->
                                    if (readyQueries?.get(query.queryId) == null) {
                                        design.resolveQueryReady(indicator, query)
                                    }
                                }

                                if (design.isIndicatorReady(indicator, indicatorQueryIds, false, true)) {
                                    Map<String, String> readyDesign = [entityId: design.id.toString(), name: design?.nameStringWithoutQuotes, nameForShow: design?.operatingPeriodAndName, type: ""]
                                    drawableDesignsForThisIndicator.add(readyDesign)
                                    ready++
                                    if (!"benchmark".equalsIgnoreCase(indicator.visibilityStatus) && !indicator.indicatorId.equalsIgnoreCase("xBenchmarkembodied")) {
                                        if (!firstReadyDesignId) {
                                            firstReadyDesignId = design.id
                                            firstReadyDesignName = design.operatingPeriodAndName
                                        }

                                        if (design.chosenDesign) {
                                            readyChosenDesignId = design.id
                                            readyChosenDesignName = design.operatingPeriodAndName
                                        }
                                        if (design.ribaStage && design.ribaStage == currentHighestRiba) {
                                            readyDesignHighestRibaStageId = design.id
                                            readyDesignHighestRibaStageName = design.operatingPeriodAndName
                                        }
                                    }
                                }

                            }

                            if (ready >= 1 && !"benchmark".equalsIgnoreCase(indicator.visibilityStatus)) {
                                drawablePiesPerIndicator.put(indicator, drawableDesignsForThisIndicator)
                                if(indicator.benchmarkSettings){
                                    drawableGraphForCarbonBenchmark.put(indicator, drawableDesignsForThisIndicator)
                                }
                            } else if (ready >= 1 && indicator.isBenchmarkable(entityType) && indicator.indicatorId.equalsIgnoreCase("xBenchmarkembodied")) {
                                drawableGraphForCarbonBenchmark.put(indicator, drawableDesignsForThisIndicator)
                            }

                            if (indicator.reportVisualisation && CollectionUtils.containsAny(Constants.PROJECT_CHARTS_VISUALIZATIONS, indicator.reportVisualisation) && ready >= 1 && !"benchmark".equalsIgnoreCase(indicator.visibilityStatus)) {
                                drawableEntityIndicators.add(indicator)
                            }
                        }
                    }
                }
                Boolean sysAdmin = userService.isSystemAdmin(user)
                String channelToken = channelFeatureService.getChannelFeature(session)?.token
                Account account = accountService.getAccountByLoginKey(session?.getAttribute("accountKey")?.toString())

                if (!entity.public && channelFeature?.enableLicenseKeyBox && noLicenses) {
                    showLicenseKeyWindow = Boolean.TRUE
                }

                if ((channelFeature?.enableQuoteBar && noLicenses && !showLicenseKeyWindow) || channelFeature?.enableQuoteBar && entity.public) {
                    showQuoteBar = Boolean.TRUE
                }
                List<License> trialLicenses = []
                List<License> allLicenses = entity?.licenses

                if (allLicenses) {
                    allLicenses.each { License license ->
                        if (license.trialLength) {
                            trialLicenses.add(license)
                        }
                    }
                }

                List<Indicator> licensedDesignIndicators = []
                List<Indicator> licensedOperatignIndicators = []
                List<Indicator> licensedIndicators = indicatorService.getValidLicensedIndicatorsForEntity(entity, validLicenses, true)

                if ((entity?.designIds?.size() < 1) || (entity?.designIds?.size() >= 1 && !selectedDesignIndicators)) {
                    licensedDesignIndicators = indicatorService.getIndicatorsByIndicatorUse(licensedIndicators, entity, IndicatorUse.DESIGN.toString())
                }

                if ((entity?.operatingPeriods?.size() < 1) || (entity?.operatingPeriods?.size() >= 1 && !selectedOperatingIndicators)) {
                    licensedOperatignIndicators = indicatorService.getIndicatorsByIndicatorUse(licensedIndicators, entity, IndicatorUse.OPERATING.toString())
                }

                List<String> designAndOperatingIds = []

                if (entity.designIds) {
                    entity.designIds.each {
                        designAndOperatingIds.add(it?.toString())
                    }
                }
                if (entity.operatingPeriods) {
                    entity.operatingPeriods.each { Entity operatingPeriod ->
                        designAndOperatingIds.add(operatingPeriod.id?.toString())
                    }
                }

                if (!designAndOperatingIds.isEmpty()) {
                    recalculateIfNeeded(entity, designAndOperatingIds)
                }
                if (channelFeature.reversePeriods) {
                    operatingPeriods = operatingPeriods.reverse()
                }

                Map<Query,Boolean> designParameterQueries = [:]
                List<Query> operatingParameterQueries = []

                List<Indicator> indicatorList = []

                if (selectedDesignIndicators && designs) {
                    indicatorList.addAll(selectedDesignIndicators)
                }

                if (selectedOperatingIndicators && operatingPeriods) {
                    indicatorList.addAll(selectedOperatingIndicators)
                }
                Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(entity,
                        [Feature.COMPILED_REPORT, Feature.CHAT_SUPPORT, Feature.INDICATOR_BENCHMARKS,
                         Feature.INDICATOR_BENCHMARKS_LOCKED, Feature.COMPARE_MATERIAL,
                         Feature.LOAD_QUERY_FROM_DEFAULT, Feature.JSON_EXPORT, Feature.GET_TESTABLE_ENTITIES,
                         Feature.CARBONDESIGNER3D], user,
                        featuresAvailableForEntity, validLicenses
                )
                Boolean compiledReportLicensed = featuresAllowed.get(Feature.COMPILED_REPORT)
                Boolean chatSupportLicensed = featuresAllowed.get(Feature.CHAT_SUPPORT)
                Boolean indicatorBenchmarkLicensed = featuresAllowed.get(Feature.INDICATOR_BENCHMARKS) || featuresAllowed.get(Feature.INDICATOR_BENCHMARKS_LOCKED)
                Boolean compareFeatureLicensed = featuresAllowed.get(Feature.COMPARE_MATERIAL) && indicatorService.addToCompareAllowedForIndicators(indicatorList)
                Boolean testableFeatureLicensed = featuresAllowed.get(Feature.GET_TESTABLE_ENTITIES)
                Boolean carbonDesigner3DLicensed = featuresAllowed.get(Feature.CARBONDESIGNER3D)
                Boolean jsonExportLicensed = featuresAllowed.get(Feature.JSON_EXPORT) && entity.modifiable

                Query materialSpecifierQuery

                if (compareFeatureLicensed) {
                    materialSpecifierQuery = queryService.getQueryByQueryId(Constants.COMPARE_QUERYID, true)
                }
                //Moved compare entity creation from renderCompareDataBtn taglib to controller code
                if (compareFeatureLicensed && materialSpecifierQuery){
                    entityService.getMaterialSpecifierEntity(entity)
                }

                if (selectedDesignIndicators) {
                    List<String> designParameterQueryIds = indicatorService.getProjectLevelQueryIdsForIndicators(selectedDesignIndicators)
                    queryService.getQueriesByQueryIds(designParameterQueryIds).each { Query query ->
                        designParameterQueries.put(query, false)
                    }

                    /* SW-1645 keep the commented code until after release 0.6.0
                    Map<String, List<String>> queryAndLinkedIndicatorIds = [:]

                    Map<String, Set<String>> queriesByIndicator = getQueriesByIndicator(selectedDesignIndicators)

                    if (queriesByIndicator) {
                        queryService.getQueriesByQueryIds(queriesByIndicator.values().flatten(), true).each { query ->
                            queriesByIndicator.each { indicatorId, indicatorQueries ->
                                if (indicatorQueries.contains(query.queryId)) {
                                    def existing = queryAndLinkedIndicatorIds.get(query.queryId)

                                    if (existing) {
                                        existing.add(indicatorId)
                                    } else {
                                        existing = [indicatorId]
                                    }
                                    queryAndLinkedIndicatorIds.put(query.queryId, existing)

                                    if (!designParameterQueries.find({ query.queryId.equals(it.key.queryId) })) {
                                        designParameterQueries.put(query, false)
                                    }
                                }
                            }
                        }
                    }
                    */

                    //13994
                    if (compareFeatureLicensed && entityService.findMaterialSpecifierChildEntity(entity)) {
                        Indicator indicatorCompare = indicatorService.getIndicatorByIndicatorId(Constants.COMPARE_INDICATORID, true)
                        IndicatorQuery queryCompareLCC = indicatorCompare?.indicatorQueries?.find {it?.projectLevel}
                        if (queryCompareLCC && !designParameterQueries?.find {it.key.queryId == queryCompareLCC.queryId}) {
                            designParameterQueries?.put(queryService.getQueryByQueryId(queryCompareLCC?.queryId,true), queryCompareLCC?.optional)
                        }
                    }

                    /* SW-1645 keep the commented code until after release 0.6.0
                    //13994
                    if (compareFeatureLicensed &&
                            entity.childEntities?.find({
                                EntityClass.MATERIAL_SPECIFIER.type.toString().equals(it.entityClass)
                            })
                    ) {
                        Indicator indicatorCompare = indicatorService.getIndicatorByIndicatorId(Constants.COMPARE_INDICATORID, true)
                        IndicatorQuery queryCompareLCC = indicatorCompare?.indicatorQueries?.find({it?.projectLevel})
                        if(queryCompareLCC && !designParameterQueries?.find({it.key.queryId == queryCompareLCC.queryId})){
                            designParameterQueries?.put(queryService.getQueryByQueryId(queryCompareLCC?.queryId,true),queryCompareLCC?.optional)
                        }
                        def existing2 =  queryAndLinkedIndicatorIds?.get(queryCompareLCC.queryId) ?: []
                        existing2.add(queryCompareLCC.queryId)
                        queryAndLinkedIndicatorIds.put(queryCompareLCC.queryId, existing2)

                    }
                    // we must save the parentEntity if new params queries are added..
                    if (queryAndLinkedIndicatorIds && entity.projectLevelQueries?.findAll({ queryAndLinkedIndicatorIds.keySet()?.toList()?.contains(it) })?.size() == entity.projectLevelQueries?.size()) {
                        entity = entityService.updateProjectLevelEntity(queryAndLinkedIndicatorIds?.keySet()?.toList(), entity, queryAndLinkedIndicatorIds)
                    }
                    */
                }


                if (selectedOperatingIndicators) {
                    List<String> operatingParameterQueryIds = indicatorService.getProjectLevelQueryIdsForIndicators(selectedOperatingIndicators)
                    queryService.getQueriesByQueryIds(operatingParameterQueryIds).each { Query query ->
                        operatingParameterQueries.add(query)
                    }
                    /* SW-1645 keep the commented code until after release 0.6.0
                    Map<String, List<String>> queryAndLinkedIndicatorIds = [:]

                    Map<String, Set<String>> queriesByIndicator = getQueriesByIndicator(selectedOperatingIndicators)

                    if (queriesByIndicator) {
                        queryService.getQueriesByQueryIds(queriesByIndicator.values().flatten(), true).each { query ->
                            queriesByIndicator.each { indicatorId, indicatorQueries ->
                                if (indicatorQueries.contains(query.queryId)) {
                                    def existing = queryAndLinkedIndicatorIds.get(query.queryId)

                                    if (existing) {
                                        existing.add(indicatorId)
                                    } else {
                                        existing = [indicatorId]
                                    }
                                    queryAndLinkedIndicatorIds.put(query.queryId, existing)

                                    if (!operatingParameterQueries.find({ query.queryId.equals(it.queryId) })) {
                                        operatingParameterQueries.add(query)
                                    }
                                }
                            }
                        }
                    }

                    // we must save the parentEntity if new params queries are added..
                    if (queryAndLinkedIndicatorIds && entity.projectLevelQueries.findAll({ queryAndLinkedIndicatorIds.keySet()?.toList()?.contains(it) })?.size() == entity.projectLevelQueries.size()) {
                        entity = entityService.updateProjectLevelEntity(queryAndLinkedIndicatorIds?.keySet()?.toList(), entity, queryAndLinkedIndicatorIds)
                    }
                    */
                }
                List<License> autoStartTrials
                //0014638
                if ((user?.trialCount < 1 && ("building".equalsIgnoreCase(entity?.entityClass)) || (user?.trialCountForInfra < 1 && ("infrastructure".equalsIgnoreCase(entity?.entityClass)))) && channelFeature?.enableAutomaticTrial) {
                     autoStartTrials = licenseService.getActiveAutoStartTrials()?.findAll({
                        !it.compatibleEntityClasses ||
                                it.compatibleEntityClasses.contains(entity.entityClass)
                    })

                    if (autoStartTrials) {
                        if (user?.country && autoStartTrials.find({ it.countries?.contains(user?.country) })) {
                            autoStartTrials = autoStartTrials.sort({ a, b -> a.countries?.contains(user.country) <=> b.countries?.contains(user.country) ?: b.name <=> a.name }).reverse()
                        } else {
                            autoStartTrials = autoStartTrials.sort({ a, b -> !a.countries <=> !b.countries ?: b.name <=> a.name }).reverse()
                        }
                    }
                }
                String activeTab = params.activeTab ? params.activeTab : "info"
                List<HelpConfiguration> helpConfigurations = helpConfigurationService.getConfigurationsForTargetPage("Entity")
                Boolean showRenderingTimes = userService.getShowDevDebugNotifications(user?.enableDevDebugNotifications)

                //***************************************************************************
                //WorkFlow
                //***************************************************************************
                WorkFlowForEntity workFlowForEntity

                //Add also designs because if there is no design selectedDesignIndicators will have all the indicator
                if (entity) {
                    workFlowForEntity = workFlowService.getWorkFlowForEntity(entity, indicatorList, validLicenses)
                }
                //***************************************************************************

                //***************************************************************************
                //FRAME STATUS
                //***************************************************************************
                FrameStatus frameStatus = workFlowService.loadFrame(user, helpConfigurations, channelFeature, workFlowForEntity)
                //***************************************************************************
                //***************************************************************************
                //SIMULATION TOOL
                //***************************************************************************

                List<SimulationTool> entitySimulationTools = SimulationTool.findAllByEntityId(entity?.id?.toString())
                //Map -> (k,v) k => (designId) If a draft is present for the design (v) =>  will be true
                Map<String, Boolean> draftByDesign = entitySimulationTools?.collectEntries { [(it?.designId ?: ""): true] }

                //***************************************************************************

                List<String> highlightTrialList = []

                if (user?.country) {
                    highlightTrialList = autoStartTrials?.findAll { it.countries?.contains(user.country) }
                }

                if (!highlightTrialList) {
                    highlightTrialList = autoStartTrials?.findAll { !it.countries } ?: []
                }

                Boolean allowQueriesForFloating = Boolean.FALSE
                if (expiredLicenses) {
                    License frozenLicense = expiredLicenses.find({ License l -> l.frozen })

                    if (frozenLicense) {
                        flash.errorAlert = message(code: "license.frozen.info", args: [frozenLicense.name])
                    } else if (!licensesToShow) {
                        License floatingLicense = expiredLicenses.find({ License l -> l.isFloatingLicense })
                        if (floatingLicense) {
                            Feature allowAddingAnyUser = featureService.getFeatureByFeatureId(Feature.ALLOW_ADDING_ANY_USER)
                            Boolean allowAddingAnyUserFeatured = floatingLicense.licensedFeatureIds?.contains(allowAddingAnyUser?.id?.toString())
                            if ((!floatingLicense.floatingLicenseCheckedInUsers?.find({ it.userId == user?.id.toString() }) && floatingLicense.floatingAllowedForUser(user)) || allowAddingAnyUserFeatured)
                                allowQueriesForFloating = Boolean.TRUE
                            // Allow viewing of project with no valid licenses but not checked floating license
                            String floatingLicenseError = "${message(code: "license.floating.error")}."
                            floatingLicenseError = "${floatingLicenseError} <a href=\"javascript:\" onclick=\"openFloatingLicenseModal('${floatingLicense.id.toString()}', null, null, true)\">${message(code: "license.floating.error_info")}.</a>"
                            flash.errorAlert = floatingLicenseError

                        }
                    }
                }

                Boolean showResultReports = Boolean.TRUE

                if (!user.internalUseRoles && (!licensesToShow && expiredLicenses) && !allowQueriesForFloating) {
                    showResultReports = Boolean.FALSE
                }

                if (licensesToShow && licensesToShow.find({ License l -> l.addon })) {
                    if (licensesToShow.size() == licensesToShow.findAll({ License l -> l.addon }).size()) {
                        flash.warningAlert = message(code: "entity.show.only_addon_licenses")
                    }
                }

                List<String> licenseTypes = licensesToShow.collect({ License license -> license.type })

                //***************************************************************************
                //SCOPE DESIGN
                //***************************************************************************
                //Boolean aDesignPresent = designs ?? to think better about this
                //HUOM: at the moment we only use scope for design indicators. If operating indicators use scopes in the future then need to revise this line.
                def scopeValues = scopeFactoryService.getServiceImpl(entity?.entityClass)?.getScopeValues(selectedDesignIndicators)

                List<PredefinedScope> allowedScopes = scopeValues.allowedScopes
                List<Question> scopeQuestions = scopeValues.scopeQuestions
                def jsonScopes = scopeValues.jsonScopes

                Map popupTableDesignMap = scopeFactoryService.getServiceImpl(entity?.entityClass)?.getPopupTableList(designs)
                Map popupTableDesignUpdateMap = entityService.renderPopupTableForTimeUpdate(designs)

                // ***************************************************************************
                String entityClass = entity?.entityClass
                Boolean showRibaStage = entityClass ? ("building".equalsIgnoreCase(entity?.entityClass) || "infrastructure".equalsIgnoreCase(entityClass)) : false

                String newToolsAvailable = entity?.newToolsAvailable
                if (!params.showToolsNote) {
                    newToolsAvailable = null
                }
                if (newToolsAvailable) {
                    if (IndicatorUse.DESIGN.toString().equals(newToolsAvailable) && !designs) {
                        newToolsAvailable = null
                    } else if (IndicatorUse.OPERATING.toString().equals(newToolsAvailable) && !operatingPeriods) {
                        newToolsAvailable = null
                    }
                }
                Query basicQuery = queryService.getBasicQuery()
                String certificationForEntity = ""
                License planetaryLicense

                if (entity && basicQuery) {
                    def datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, basicQuery?.queryId, 'basicQuerySection', 'certification')
                    if (datasets) {
                        String resourceId = datasets?.get(0)?.resourceId
                        if (resourceId) {
                            certificationForEntity = resourceId
                        }
                    }
                    String state = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, basicQuery?.queryId, 'basicQuerySection', 'country')?.getAt(0)?.additionalQuestionAnswers?.state
                    planetaryLicense = licenseService.getPlanetaryLicenseForCountryOrState(entity?.countryResourceResourceId, state, [id: 1, licenseImage:1,conditionalCountries: 1,conditionalStates: 1])
                }

                Boolean loadQueryFromDefault = featuresAllowed.get(Feature.LOAD_QUERY_FROM_DEFAULT)
                Boolean drawOldOperatingChart = operatingDrawableEntityIndicators?.findAll({ it.reportVisualisation.contains("oldOperatingChart") }) ? Boolean.TRUE : Boolean.FALSE
                Boolean allowWorkflowDisableMessage = Boolean.TRUE

                String carbonDesigner3dUrl = channelFeatureService.getServerUrl(request, Boolean.TRUE)
                // Seconds taken at the very bottom
                Double secondsTaken = (System.currentTimeMillis() - now) / 1000
                log.debug("Entity (show) Elapsed: ${secondsTaken}")

                List<CalculationProcess> calculationProcesses = calculationProcessService.getCalculatedProcessesForParentEntity(entity?.id?.toString())
                Boolean isCalculationRunning = Boolean.FALSE

                if (calculationProcesses.size() > 0) {
                    isCalculationRunning = Boolean.TRUE
                    flashService.setPermWarningAlert(g.message(code: "entity.calculation.running"))
                }

                [entity                                : entity, designs: designs, operatingPeriods: operatingPeriods, selectedDesignIndicators: selectedDesignIndicators, workFlowForEntity: workFlowForEntity, allowWorkflowDisableMessage: allowWorkflowDisableMessage,
                 selectedOperatingIndicators           : selectedOperatingIndicators, validDesignLicenses: validDesignLicenses, validLicenses: validLicenses, drawOldOperatingChart: drawOldOperatingChart,
                 validOperatingLicenses                : validOperatingLicenses, designPortfolios: designPortfolios, loadQueryFromDefault: loadQueryFromDefault,
                 operatingPortfolios                   : operatingPortfolios, designBenchmarks: designBenchmarks, operatingBenchmarks: operatingBenchmarks, isCalculationRunning: isCalculationRunning, calculationProcesses: calculationProcesses,
                 benchmarkLicensed                     : benchmarkLicensed, licensesToShow: licensesToShow, userForFloatingLicense: user, reloadOnFloatingStatusChange: Boolean.TRUE,
                 mainLevelButtons                      : mainLevelButtons, mainLevelReport: mainLevelReport, chatSupportLicensed: chatSupportLicensed,
                 expiredLicenses                       : expiredLicenses?.unique(), tasks: tasks, completedTasks: completedTasks, planetaryLicense: planetaryLicense,
                 designStartGuide                      : chainModel?.designStartGuide, indicatorBenchmarkLicensed: indicatorBenchmarkLicensed, entityType: entityType,
                 operatingStartGuide                   : chainModel?.operatingStartGuide, manageable: entity?.manageable, countryForPrioritization: entity.countryForPrioritization,
                 reportLicensed                        : reportLicensed, jsonExportLicensed: jsonExportLicensed, designIndicatorIdsWithImportMappers: designIndicatorIdsWithImportMappers,
                 operatingIndicatorIdsWithImportMappers: operatingIndicatorIdsWithImportMappers, activeTab: activeTab, showQuoteBar: showQuoteBar, quoteUrl: quoteUrl, webinarUrl: webinarUrl, expertUrl: expertUrl,
                 notes                                 : notes, attachments: attachments, totalNoteAmount: totalNoteAmount, drawableEntityIndicators: drawableEntityIndicators, operatingDrawableEntityIndicators: operatingDrawableEntityIndicators,
                 compareLicensed                       : compareLicensed, oldGraphsLicensed: oldGraphsLicensed, secondsTaken: secondsTaken, showRenderingTimes: showRenderingTimes,
                 firstReadyDesignName                  : firstReadyDesignName, firstReadyOperatingPeriodName: firstReadyOperatingPeriodName, readyChosenDesignId: readyChosenDesignId, readyChosenDesignName: readyChosenDesignName, firstReadyDesignId: firstReadyDesignId, firstReadyOperatingPeriodId: firstReadyOperatingPeriodId, readyDesignHighestRibaStageId: readyDesignHighestRibaStageId, readyDesignHighestRibaStageName: readyDesignHighestRibaStageName, ribaStages: ribaStages, currentHighestRiba: currentHighestRiba,
                 sysAdmin                              : sysAdmin, channelToken: channelToken, managedLicenses: managedLicenses, noLicenses: noLicenses, licenseTypes: licenseTypes, showLicenseKeyWindow: showLicenseKeyWindow, account: account, user: user,
                 trialLicenses                         : trialLicenses, licensedDesignIndicators: licensedDesignIndicators?.sort({ !it.certificationImageResourceId?.contains(certificationForEntity) }), licensedOperatignIndicators: licensedOperatignIndicators, hideEcommerce: hideEcommerce,
                 designParameterQueries                : designParameterQueries, operatingParameterQueries: operatingParameterQueries,
                 drawablePiesPerIndicator              : drawablePiesPerIndicator, drawablePiesPerIndicatorAndPeriod: drawablePiesPerIndicatorAndPeriod, drawableGraphForCarbonBenchmark: drawableGraphForCarbonBenchmark, compiledReportLicensed: compiledReportLicensed, autoStartTrials: autoStartTrials, helpConfigurations: helpConfigurations, showResultReports: showResultReports,
                 draftByDesign                         : draftByDesign, highlightTrialList: highlightTrialList, frameStatus: frameStatus, carbonDesignerOnlyLicensed: carbonDesignerOnlyLicensed, carbonDesignerAndResultsOnlyLicensed: carbonDesignerAndResultsOnlyLicensed,
                 allowedScopes                         : allowedScopes, scopeQuestions: scopeQuestions, jsonScopes: jsonScopes, popupTableDesignUpdateMap: popupTableDesignUpdateMap, basicQuery: basicQuery,
                 popupTableDesignMap                   : popupTableDesignMap, showRibaStage: showRibaStage, newToolsAvailable: newToolsAvailable, certificationForEntity: certificationForEntity, compareFeatureLicensed: compareFeatureLicensed, testableFeatureLicensed: testableFeatureLicensed, materialSpecifierQuery: materialSpecifierQuery, preventMultipleDesignsLicened: preventMultipleDesignsLicened,
                 indicatorsWithDeprecationNote         : indicatorsWithDeprecationNote, carbonDesigner3DLicensed: carbonDesigner3DLicensed, carbonDesigner3dUrl: carbonDesigner3dUrl, queriesForSelectedDesignIndicators: queriesForSelectedDesignIndicators, queriesForSelectedOperatingIndicators: queriesForSelectedOperatingIndicators, featuresAvailableForEntity: featuresAvailableForEntity,
                 enableAverageDesignFeature            : enableAverageDesignFeature, disableAverageFeatureInMenu: disableAverageFeatureInMenu]
            }
        } else {
            flash.errorAlert = message(code: "entity.illegal_access")
            redirect controller: "main"
        }
    }

    private static Map getQueriesByIndicator(List<Indicator> indicators) {
        Map<String, Set<String>> queriesByIndicator = [:]
        indicators.each { Indicator indicator ->
            indicator.indicatorQueries?.each {
                if (it.queryId && it.projectLevel) {
                    if (!queriesByIndicator.get(it.queryId)) {
                        queriesByIndicator.put(indicator.indicatorId, [it.queryId] as Set)
                    } else {
                        queriesByIndicator.get(indicator.indicatorId) << it.queryId
                    }
                }
            }
        }
        return queriesByIndicator
    }

    def getTestablePeriodsDropdown() {
        String entityId = params.entityId
        String periodsText = ""

        if (entityId) {
            Entity entity = entityService.readEntity(entityId)

            if (entity) {
                def selectedOperatingIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.OPERATING.toString())

                if (entity.indicatorIds) {
                    def testablePeriods = entityService.getAllTestEntities(EntityClass.OPERATING_PERIOD.getType(), selectedOperatingIndicators?.findAll({!"benchmark".equalsIgnoreCase(it.visibilityStatus)})?.collect({ it.indicatorId }))

                    if (testablePeriods) {
                        testablePeriods.each { Entity child ->
                            periodsText = "${periodsText}<li>${opt.link(controller: "entity", action: "addATestEntity", params: ["childId": child.id, "entityId": entity.id, entityClass: child.entityClass]) {child.operatingPeriodAndName}}</li>"
                        }
                    }
                }
            }
        }
        render([testablePeriods: periodsText, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getTestableDesignsDropdown() {
        String entityId = params.entityId
        String designsText = ""

        if (entityId) {
            Entity entity = entityService.readEntity(entityId)

            if (entity) {
                def selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.DESIGN.toString())

                if (entity.indicatorIds) {
                    def testableDesigns = entityService.getAllTestEntities(EntityClass.DESIGN.getType(), selectedDesignIndicators?.findAll({!"benchmark".equalsIgnoreCase(it.visibilityStatus)})?.collect({ it.indicatorId }))

                    if (testableDesigns) {
                        testableDesigns.each { Entity child ->
                            designsText = "${designsText}<li>${opt.link(controller: "entity", action: "addATestEntity", params: ["childId": child.id, "entityId": entity.id, entityClass: child.entityClass]) {child.operatingPeriodAndName}}</li>"
                        }
                    }
                }
            }
        }
        render([testableDesigns: designsText, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private void recalculateIfNeeded(Entity parenEntity, List<String> childEntityIds) {
        Map<String, String> toCalculate = session?.getAttribute("calculationNeeded")

        if (toCalculate && parenEntity && childEntityIds) {
            String entityId = toCalculate.keySet().first()
            String indicatorId = toCalculate.values().first()
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (entityId && indicator && childEntityIds.contains(entityId)) {
                newCalculationServiceProxy.calculate(entityId, indicator.indicatorId, parenEntity, null)
                session?.removeAttribute("calculationNeeded")
            }
        }
    }

    def getStartedLicenses() {
        String entityId = params.entityId
        Entity entity = entityService.getEntityByIdReadOnly(entityId)
        List<Indicator> validLicenses = []

        if (entity) {
            entity?.validDesignLicenses?.each { License license ->

                license.licensedIndicators?.each { Indicator indicator ->
                    if ("design".equals(indicator?.indicatorUse)) {
                        validLicenses.add(indicator)
                    }
                }
            }
            entity?.validOperatingLicenses?.each { License license ->
                license.licensedIndicators?.each { Indicator indicator ->
                    if ("operating".equals(indicator?.indicatorUse)) {
                        validLicenses.add(indicator)
                    }
                }

            }
        }


        render text: validLicenses
    }

    def dashboard() {
        String entityId = params.entityId
        Entity entity = entityService.getEntityForShowing(entityId)
        if (entity) {
            List<Entity> designs = entity.designs
            List<Indicator> entitysIndicators = entity.indicators
            List<Indicator> drawableEntityIndicators = entitysIndicators?.findAll({ Indicator indicator -> indicator?.reportVisualisation?.contains("entityPageChart") && indicator?.howManyChildEntitiesReady(designs) > 1 })
            List<Indicator> drawablePieIndicators = entitysIndicators?.findAll({ Indicator indicator -> indicator?.reportVisualisation?.contains("pieChart") && indicator?.howManyChildEntitiesReady(designs) >= 1 })
            List<Entity> readyDesigns = []
            List<Entity> allReadyDesigns = []
            List<CalculationRule> calculationRules = []
            List<Resource> topResourcesForCountry
            CalculationRule gwpRule
            def firstDesignIndicatorName
            def firstDesignIndicatorId
            def firstReadyDesignName
            def firstReadyDesignId

            if (drawablePieIndicators) {
                drawablePieIndicators.find { Indicator indicator ->
                    List<Entity> designsWithReadyIndicators = designs?.findAll({ Entity design -> design?.isIndicatorReady(indicator) })

                    designsWithReadyIndicators?.find { Entity design ->

                        if (calc.areResultsValid(entity: design, indicator: indicator)) {
                            firstDesignIndicatorName = indicatorService.getLocalizedShortName(indicator)
                            firstDesignIndicatorId = indicator.indicatorId
                            return true
                        }
                    }
                }
                Indicator readyIndicator = drawablePieIndicators?.find({ Indicator indicator -> indicator })

                drawablePieIndicators.each { Indicator indicator ->
                    List<Entity> designsWithReadyIndicators = designs?.findAll({ Entity design -> design?.isIndicatorReady(indicator) })

                    designsWithReadyIndicators?.each { Entity design ->

                        if (calc.areResultsValid(entity: design, indicator: indicator)) {
                            allReadyDesigns.add(design)
                        }
                    }
                }
                allReadyDesigns = allReadyDesigns.unique()
                readyDesigns = allReadyDesigns
                gwpRule = readyIndicator?.getResolveCalculationRules(entity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(readyIndicator?.displayResult) })

                if (readyDesigns) {
                    firstReadyDesignName = readyDesigns[0]?.name
                    firstReadyDesignId = readyDesigns[0]?.id
                }
            }


            drawableEntityIndicators.each { Indicator indicator ->
                indicator.getResolveCalculationRules(entity)?.findAll({ !it.hideInGraphs })?.each { CalculationRule rule ->
                    if (!calculationRules.contains(rule)) {
                        calculationRules.add(rule)
                    }
                }
            }

            [entity                : entity, drawablePieIndicators: drawablePieIndicators, drawableEntityIndicators: drawableEntityIndicators, readyDesigns: readyDesigns,
             firstDesignIndicatorId: firstDesignIndicatorId, firstReadyDesignId: firstReadyDesignId,
             gwpRule               : gwpRule, firstDesignIndicatorName: firstDesignIndicatorName, firstReadyDesignName: firstReadyDesignName, calculationRules: calculationRules]

        } else {
            flash.errorAlert = message(code: "entity.illegal_access")
            redirect controller: "main"
        }
    }

    private String graphDatasetsForLifeCycle(Map<ResultCategory, Map<String, Double>> totalPerCategoryAndType = [:], List<String> typesForlabels, List<String> strokeAndPointColors = null) {
        String graphDatasets = "["

        if (totalPerCategoryAndType && typesForlabels && strokeAndPointColors) {
            totalPerCategoryAndType.each { ResultCategory category, Map<String, Double> values ->
                String localizedShortName = resultCategoryService.getLocalizedShortName(category, 15)
                graphDatasets = "${graphDatasets} {name:\"${category.resultCategory} ${localizedShortName}\","
                graphDatasets = "${graphDatasets} data:["
                typesForlabels?.each { String type ->
                    graphDatasets = "${graphDatasets}${values?.get(type) ? values.get(type) : 0},"
                }
                graphDatasets = "${graphDatasets}]},"
            }
        }
        graphDatasets = "${graphDatasets}]"

        return graphDatasets
    }


    private String generateGraphLabelsForLifeCycle(List<String> labelsList) {
        String labels = "["
        int index = 0
        if (labelsList) {
            labelsList?.each { String label ->
                labels = "${labels}${index > 0 ? ', ' : ''}\"${label}\""
                index++
            }
        }
        labels = "${labels}]"
        return labels
    }

    def highestImpactResourcesOnly () {
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId
        String parentEntityId = params.parentEntityId
        String calculationRuleId = params.calculationRuleId
        Boolean fromPlanetaryReport = params.boolean("fromPlanetaryReport")
        Entity childEntity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityByIdReadOnly(parentEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parentEntity)
        Map<String, String> emissionCloudsForResources = [:]
        Map<Resource, Double> impactPerResourceAndDataset = [:]
        Map<Resource, String> formattedImpactPerResourceAndDataset = [:]
        Map<Resource, String> unitPerResourceAndDataset = [:]
        CalculationRule calculationRule = indicator?.getResolveCalculationRules(parentEntity)?.find({
            it.calculationRuleId.equals(calculationRuleId)
        })
        Map<Resource, Double> percentageScores = [:]
        Map<Resource, Double> percentageScoresFullLifeCycle = [:]
        User user = userService.getCurrentUser(true)

        String calculationRuleName = ""
        String resultCategoryCategory = ""
        String benchmarkName = ""
        Boolean drawTexts = Boolean.FALSE
        Double totalLifeCycleScore
        String unit = ""
        Account account = null
        List<Account> accountsForEntity = licenseService.getValidLicensesForEntity(parentEntity)
                ?.collect{ it.accounts ?: [] }?.flatten()?.sort{ Account a -> a.companyName }


        if (accountsForEntity?.size()) {
            account = accountsForEntity.first()
        }

        Denominator overallDenominator = indicator?.resolveDenominators?.find({
            "overallDenominator".equalsIgnoreCase(it.denominatorType)
        })
        Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)

        if (childEntity && calculationRule && resultCategories && indicator) {
            totalLifeCycleScore = (Double) childEntity.getTotalResult(indicatorId, calculationRule.calculationRuleId, Boolean.FALSE)
            Boolean showConstructionImpact = indicator.showConstructionImpact
            unit = g.message(code: 'tons').toLowerCase() + " CO<sub>2</sub>e"

            ResultCategory materialsImpact = resultCategories?.get(0)
            resultCategoryCategory = materialsImpact.resultCategory
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(
                    indicator.indicatorId, null, null)
            List<Dataset> datasets = childEntity?.getDatasetsForResultCategoryExpand(
                    indicatorId, materialsImpact?.resultCategoryId, resultsForIndicator)
            calculationRuleName = calculationRule.localizedName

            if (["GWP", "traciGWP_kgCO2e", "traciGWP_tn", "GWP_tn", "GWP_failover"].contains(calculationRuleId)) {
                drawTexts = Boolean.TRUE
            }
            Double total = childEntity.getResult(indicator.indicatorId, calculationRule.calculationRuleId,
                    materialsImpact.resultCategoryId, resultsForIndicator)

            if (overallDenominator) {
                if (overallDenomValue != null && total != null) {
                    total = total * overallDenomValue
                }
            }
            List<Resource> constructionResourceList = []
            ResourceCache resourceCache = ResourceCache.init(datasets)
            for (Dataset dataset: datasets) {
                Resource resource
                if(showConstructionImpact && dataset.parentConstructionId){
                    resource = constructionResourceList?.find({it.constructionId == dataset.parentConstructionId})
                    if(!resource){
                        resource = optimiResourceService.getResourceByConstructionId(dataset.parentConstructionId)
                    }
                } else if(!showConstructionImpact || !dataset.parentConstructionId){
                    resource = resourceCache.getResource(dataset)
                }

                if (resource) {
                    handleResourceImpact(dataset, resource, overallDenominator, overallDenomValue,
                            childEntity, indicator, materialsImpact.resultCategoryId, calculationRuleId,
                            resultsForIndicator, impactPerResourceAndDataset, emissionCloudsForResources)
                }
            }

            if (total && totalLifeCycleScore) {
                impactPerResourceAndDataset.each { Resource r, Double value ->
                    Double score = value / total * 100
                    Double scoreFullLifeCycle = value / totalLifeCycleScore * 100
                    if (score != null && !score.isNaN()) {
                        percentageScores.put(r, score.round(1))
                    }
                    if (scoreFullLifeCycle != null && !scoreFullLifeCycle.isNaN()) {
                        percentageScoresFullLifeCycle.put(r, scoreFullLifeCycle.round(1))
                    }
                    //REL2108-14 - Most contributing materials given in tons in EPD tools
                    String unitBasedOnValue = unit
                    Double valueInTons = value
                    if(valueInTons > 100){
                        valueInTons = valueInTons / 1000
                    }else{
                        unitBasedOnValue = g.message(code: 'kg').toLowerCase() + " CO<sub>2</sub>e"
                    }
                    formattedImpactPerResourceAndDataset.put(r, resultFormattingResolver
                            ?.autoFormatDecimalsAndLocalizedDecimalSeparator(user, valueInTons))
                    unitPerResourceAndDataset.put(r, unitBasedOnValue)
                }
            }

            benchmarkName = indicator.connectedBenchmarks ? Constants.BENCHMARKS.get(indicator.connectedBenchmarks.first()) : ""
        }
        String enterpriseFeatureName = featureService.getFeatureByFeatureId('betaFeatures')?.formattedName
        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity,
                [Feature.BETA_FEATURES, Feature.COMPARE_MATERIAL])

        Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
        Boolean addToCompareLicensed = featuresAllowed.get(Feature.COMPARE_MATERIAL) &&
                indicatorService.addToCompareAllowedForIndicators([indicator])

        def outputMap = [:]
        int resourcesNumber = fromPlanetaryReport ? 5 : 25
        if (indicator?.reportVisualisation?.contains("topContributors")) {
            Entity materialCompareEntity = entityService.getMaterialSpecifierEntity(parentEntity)
            String highestImpactAsString = g.render(template: "/entity/highestImpactResourcesOnly",
                    model: [resources                           : percentageScores?.sort({ -it.value })?.keySet()?.toList()?.take(resourcesNumber),
                            impactPerResourceAndDataset         : impactPerResourceAndDataset,
                            percentageScoresFullLifeCycle       : percentageScoresFullLifeCycle,
                            indicator                           : indicator,
                            emissionCloudsForResources          : emissionCloudsForResources,
                            calculationRuleName                 : calculationRuleName,
                            unit                                : unit,
                            childEntityId                       : childEntityId,
                            materialCompareEntity               : materialCompareEntity,
                            percentageScores                    : percentageScores?.sort({ -it.value })?.take(resourcesNumber),
                            betaFeaturesLicensed                : betaFeaturesLicensed,
                            user                                : user,
                            formattedImpactPerResourceAndDataset: formattedImpactPerResourceAndDataset,
                            resultCategory                      : resultCategoryCategory,
                            parentEntity                        : parentEntity,
                            benchmarkName                       : benchmarkName,
                            enterpriseFeatureName               : enterpriseFeatureName,
                            drawTexts                           : drawTexts,
                            account                             : account,
                            fromPlanetaryReport                 : fromPlanetaryReport,
                            addToCompareLicensed                : addToCompareLicensed,
                            unitPerResourceAndDataset           : unitPerResourceAndDataset])
            outputMap.put("highestMaterial", highestImpactAsString)
        }
        render([output: outputMap] as JSON)
    }

    def highestImpactResources() {
        String indicatorId = params.indicatorId
        String childEntityId = params.childEntityId
        String parentEntityId = params.parentEntityId
        String calculationRuleId = params.calculationRuleId
        Boolean fromPlanetaryReport = params.boolean("fromPlanetaryReport")
        Entity childEntity = entityService.getEntityByIdReadOnly(childEntityId)
        Entity parentEntity = entityService.getEntityByIdReadOnly(parentEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parentEntity)
        CalculationRule calculationRule = indicator?.getResolveCalculationRules(parentEntity)?.find({
            it.calculationRuleId.equals(calculationRuleId)
        })
        User user = userService.getCurrentUser(true)

        String calculationRuleName = ""
        String impactsText = ""
        String resultCategoryCategory = ""
        String benchmarkName = ""
        Boolean drawTexts = Boolean.FALSE
        String unit = ""
        Account account = null
        List<Account> accountsForEntity = licenseService.getValidLicensesForEntity(parentEntity)
                ?.collect({ it.accounts ?: [] })?.flatten()?.sort({ Account a -> a.companyName })

        Map<String, String> impactForPlanetary = [:]

        if (accountsForEntity?.size()) {
            account = accountsForEntity.first()
        }

        if (childEntity && calculationRule && resultCategories && indicator) {

            unit = g.message(code: 'tons').toLowerCase() + " CO<sub>2</sub>e"
            ResultCategory materialsImpact = resultCategories?.get(0)
            resultCategoryCategory = materialsImpact.resultCategory
            calculationRuleName = calculationRule.localizedName

            if (["GWP", "traciGWP_kgCO2e", "traciGWP_tn", "GWP_tn", "GWP_failover"].contains(calculationRuleId)) {
                drawTexts = Boolean.TRUE
            }

            if (fromPlanetaryReport) {
                impactForPlanetary = impactsInTextFormatPlanetary(calculationRuleId, indicatorId, childEntityId, null, account)
            } else {
                impactsText = impactsInTextFormat(calculationRuleId, indicatorId, childEntityId, null, account)
            }
            benchmarkName = indicator.connectedBenchmarks ? Constants.BENCHMARKS.get(indicator.connectedBenchmarks.first()) : ""
        }
        String enterpriseFeatureName = featureService.getFeatureByFeatureId('betaFeatures')?.formattedName

        def outputMap = [:]

        if (indicator?.reportVisualisation?.contains("quickInfoChart") && !fromPlanetaryReport) {
            String quickInfoChartText = g.render(template: "/entity/quickInfoChart",
                    model: [calculationRuleName  : calculationRuleName,
                            unit                 : unit,
                            user                 : user,
                            impactForPlanetary   : impactForPlanetary,
                            impactsText          : impactsText,
                            resultCategory       : resultCategoryCategory,
                            benchmarkName        : benchmarkName,
                            enterpriseFeatureName: enterpriseFeatureName,
                            drawTexts            : drawTexts,
                            account              : account])

            outputMap.put("quickInfoChart", quickInfoChartText)
        }
        if (fromPlanetaryReport) {
            outputMap.put("impactForPlanetary", impactForPlanetary)
        }
        render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private void handleResourceImpact(Dataset dataset, Resource resource, Denominator overallDenominator, Double overallDenomValue, Entity childEntity, Indicator indicator, String resultCategoryId,String calculationRuleId,List<CalculationResult> resultsForIndicator,Map<Resource,Double> impactPerResourceAndDataset,Map<String,String> emissionCloudsForResources){
        Double result = childEntity?.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategoryId, calculationRuleId, resultsForIndicator)
        if(overallDenominator){
            if (overallDenomValue != null && result != null) {
                result = result * overallDenomValue
            }
        }

        if (result != null && !result.isNaN()) {
            Double existingValue = impactPerResourceAndDataset?.get(resource)
            if (existingValue != null && !existingValue.isNaN()) {
                impactPerResourceAndDataset.put(resource, result + existingValue)

            } else {
                impactPerResourceAndDataset.put(resource, result)
            }
        }

        if (resource.benchmark && indicator.connectedBenchmarks) {
            Benchmark benchmark = resource.benchmark
            Integer quintile = benchmark.quintiles?.get(indicator.connectedBenchmarks.first()) ? benchmark.quintiles.get(indicator.connectedBenchmarks.first()) : benchmark.co2Quintile

            String performanceCloud

            if (quintile > 0) {
                performanceCloud = quintile == 5 ? "deepGreenEmissions" : quintile == 4 ? "greenEmissions" : quintile == 3 ? "yellowEmissions" :
                        quintile == 2 ? "orangeEmissions" :
                                quintile == 1 ? "redEmissions" : ""
            }
            if (performanceCloud && !performanceCloud.isEmpty()) {
                emissionCloudsForResources.put(resource.resourceId, performanceCloud)
            }
        }
    }

    private String impactsInTextFormat(String calculationRuleId, String indicatorId, String childEntityId, Boolean showOnlyCarbonTotal = Boolean.FALSE, Account account = null) {
        Entity childEntity = entityService.getEntityByIdReadOnly(childEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Double gwpImpact
        Double gwpAnnual
        Double socialCostOfImpact
        User user = userService.getCurrentUser(true)

        String impactsInTextFormat = ""

        if (childEntity && indicator && calculationRuleId && indicator?.reportVisualisation?.contains("quickInfoChart")) {
            Denominator overallDenominator = indicator?.resolveDenominators?.find({
                "overallDenominator".equalsIgnoreCase(it.denominatorType)
            })
            Double totalGWP = (Double) childEntity?.getTotalResult(indicatorId, calculationRuleId)
            Double assestmentPeriod = (Double) indicator.assessmentPeriodFixed ? indicator.assessmentPeriodFixed : valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, childEntity) ? valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, childEntity) : 0
            List<Dataset> hardCodedDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, "surfaceDefinitionsShared", "surfaceDefinitionsShared", "surfaceDefinitionsShared")
            Double GIFA = hardCodedDatasets?.find({ "surfaceGIFA".equals(it.resourceId) })?.quantity

            if (!GIFA) {
                GIFA = hardCodedDatasets?.find({ "surfaceGFA-sqft".equals(it.resourceId) })?.quantity

                if (GIFA) {
                    GIFA = unitConversionUtil.doConversion(GIFA, null, "sq ft", null, null, null, null, null, null, "m2")
                }
            }

            if (overallDenominator) {
                Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                if (overallDenomValue != null && totalGWP != null) {
                    totalGWP = totalGWP * overallDenomValue
                }
            }
            String unit = ''
            if (totalGWP) {
                if ("traciGWP_tn".equalsIgnoreCase(indicator.displayResult) || "GWP_tn".equalsIgnoreCase(indicator.displayResult)) {
                    gwpImpact = totalGWP
                    unit = message(code: 'tons')
                } else {
                    gwpImpact = totalGWP < 1000 ? totalGWP : totalGWP / 1000
                    unit = totalGWP < 1000 ? 'kg' : message(code: 'tons')
                }
                def carbonCostByOrg = account?.carbonCost ? account?.carbonCost : 50
                socialCostOfImpact = gwpImpact * carbonCostByOrg

                if (GIFA && assestmentPeriod) {
                    gwpAnnual = totalGWP / assestmentPeriod / GIFA
                }
            }

            if (gwpImpact) {
                impactsInTextFormat = "<div class='blowYourMindTextsWrapper'><div class='blowYourMindTextContainer'><div class=\'co2CloudContainer\'><i class='fa fa-cloud fa-2x'></i><p class=\'co2Text\'>co<sub>2</sub></p></div>  ${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, gwpImpact)} ${unit} CO<sub>2</sub>e <i class=\'icon-question-sign\' id=\'tonsCabonHelp\'></i></div>"
            }
            if (!showOnlyCarbonTotal) {
                if (gwpAnnual) {
                    impactsInTextFormat = impactsInTextFormat + "<div class='blowYourMindTextContainer'><i class=\'fas fa-calendar-alt fa-2x\'></i> ${formatNumber(number: gwpAnnual, format: '###.##')} kg CO<sub>2</sub>e / m<sup>2</sup> / ${message(code: 'year')} <i class=\'icon-question-sign\' id=\'annualCarbonHelp\'></i> </div>"
                } else {
                    impactsInTextFormat = impactsInTextFormat + "<div class='blowYourMindTextContainer'>&nbsp;</div>"
                }
                if (socialCostOfImpact) {
                    impactsInTextFormat = impactsInTextFormat + "<div class='blowYourMindTextContainer'><span class='font-2x'><i class='far fa-money-bill-alt' arial-hidden='true'></i>" +
                            "</span>  ${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, socialCostOfImpact)}" +
                            " ${account?.unitCarbonCost ? account?.unitCarbonCost : '€'} ${message(code: 'social_cost_carbon')} <i class=\'icon-question-sign\' id=\'socialCostOfCarbonHelp\'></i></div></div>"

                }
            } else {
                impactsInTextFormat = impactsInTextFormat + "</div>"
            }
        }
        return impactsInTextFormat
    }

    private Map impactsInTextFormatPlanetary(String calculationRuleId, String indicatorId, String childEntityId, Boolean showOnlyCarbonTotal = Boolean.FALSE, Account account = null) {
        Entity childEntity = entityService.getEntityByIdReadOnly(childEntityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Double gwpTonnes
        Double gwpAnnual
        Double socialCostOfImpact
        User user = userService.getCurrentUser(true)

        Map<String, String> impactAsMap = [:]

        if (childEntity && indicator && calculationRuleId && indicator?.reportVisualisation?.contains("quickInfoChart")) {
            Denominator overallDenominator = indicator?.resolveDenominators?.find({
                "overallDenominator".equalsIgnoreCase(it.denominatorType)
            })
            Double totalGWP = (Double) childEntity?.getTotalResult(indicatorId, calculationRuleId)
            Double assestmentPeriod = (Double) indicator.assessmentPeriodFixed ? indicator.assessmentPeriodFixed : valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, childEntity) ? valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, childEntity) : 1
            List<Dataset> hardCodedDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(childEntity, "surfaceDefinitionsShared", "surfaceDefinitionsShared", "surfaceDefinitionsShared")
            Double GIFA = hardCodedDatasets?.find({ ["surfaceGIFA", "surfaceGIFA_US_m2"].contains(it.resourceId) })?.quantity
            if (!GIFA) {
                GIFA = hardCodedDatasets?.find({ "surfaceGFA-sqft".equals(it.resourceId) })?.quantity
                if (GIFA) {
                    GIFA = unitConversionUtil.doConversion(GIFA, null, "sq ft", null, null, null, null, null, null, "m2")
                }
            }

            if (overallDenominator) {
                Double overallDenomValue = denominatorUtil.getFinalValueForDenominator(childEntity, overallDenominator)
                if (overallDenomValue != null && totalGWP != null) {
                    totalGWP = totalGWP * overallDenomValue
                }
            }
            if (totalGWP) {
                if (["traciGWP_tn", "GWP_tn", "traciGWP_kgCO2e_tn"].contains(indicator.displayResult)) {
                    gwpTonnes = totalGWP
                } else {
                    gwpTonnes = totalGWP / 1000
                }
                def carbonCostByOrg = account?.carbonCost ? account?.carbonCost : 50
                socialCostOfImpact = gwpTonnes * carbonCostByOrg

                if (GIFA && assestmentPeriod) {
                    gwpAnnual = totalGWP / assestmentPeriod / GIFA
                }
            }

            if (gwpTonnes) {
                if (gwpTonnes < 1) {
                    impactAsMap.put("gwpTonnes", "${gwpTonnes.round(1)}")
                } else {
                    impactAsMap.put("gwpTonnes", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, gwpTonnes)}")
                }
                Double perCar = 0.15
                Double perTree = 2
                Double perLaundry = 1750
                Double perFlight = 0.25
                Double totalNewCar = gwpTonnes * perCar
                if (totalNewCar < 1) {
                    impactAsMap.put("car", "${totalNewCar.round(1)}")
                } else {
                    impactAsMap.put("car", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, totalNewCar)}")
                }
                Double totalTree = gwpTonnes * perTree
                if (totalTree < 1) {
                    impactAsMap.put("tree", "${totalTree.round(1)}")
                } else {
                    impactAsMap.put("tree", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, totalTree)}")
                }
                Double totalLaundry = gwpTonnes * perLaundry
                if (totalLaundry > 1000000) {
                    impactAsMap.put("laundry", "${((gwpTonnes * perLaundry) / 1000000).round(1)} m")
                } else {
                    impactAsMap.put("laundry", "${((gwpTonnes * perLaundry) / 1000).round(1)} k")
                }
                Double totalFlight = gwpTonnes * perFlight
                if (totalFlight < 1) {
                    impactAsMap.put("flight", "${totalFlight.round(1)}")
                } else {
                    impactAsMap.put("flight", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, totalFlight)}")
                }

            }
            if (gwpAnnual) {
                impactAsMap.put("gwpAnnual", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, gwpAnnual * 1000)}")
            }
            if (socialCostOfImpact) {
                impactAsMap.put("socialCostOfImpact", "${resultFormattingResolver.formatByUserLocaleAndThousandSeparator(user, socialCostOfImpact)}")
            }


        }
        return impactAsMap
    }

    def resourceTypeImpactBars() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String calculationRuleId = params.calculationRuleId
        Entity childEntity = entityService.getEntityById(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule calculationRule = indicator?.getResolveCalculationRules(parentEntity)?.findAll({
            !it.hideInGraphs
        })?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(calculationRuleId) })
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        Map<String, Double> impactPerResourceAndDataset = [:]
        Map<String, Integer> percentageScores = [:]
        String calculationRuleName = ""
        String resultCategoryCategory = ""

        if (childEntity && calculationRule && resultCategories && indicatorId) {
            ResultCategory materialsImpact = resultCategories?.get(0)
            resultCategoryCategory = materialsImpact.resultCategory
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, calculationRule.calculationRuleId, materialsImpact.resultCategoryId)
            List<Dataset> impactDatasets = childEntity?.getDatasetsForResultCategoryExpand(indicatorId, materialsImpact.resultCategoryId, resultsForIndicator)
            calculationRuleName = calculationRule.localizedShortName ? calculationRule.localizedShortName : calculationRule.localizedName

            ResourceCache resourceCache
            ResourceTypeCache resourceTypeCache

            if (impactDatasets) {
                resourceCache = ResourceCache.init(impactDatasets as List)
                resourceTypeCache = resourceCache?.getResourceTypeCache()
                for (Dataset d: impactDatasets) {
                    String type = resourceTypeCache.getResourceType(resourceCache.getResource(d))
                    Double value = childEntity?.getResultForDataset(d.manualId, indicatorId, materialsImpact.resultCategoryId, calculationRuleId, resultsForIndicator)
                    if (value) {
                        if (!impactPerResourceAndDataset.get(type)) {
                            impactPerResourceAndDataset.put(type, value)

                        } else {
                            impactPerResourceAndDataset.put(type, value + impactPerResourceAndDataset.get(type))

                        }
                    }
                }
            }
            Double total = childEntity.getResult(indicator.indicatorId, calculationRule.calculationRuleId,
                    materialsImpact.resultCategoryId, resultsForIndicator)

            if (impactPerResourceAndDataset && !impactPerResourceAndDataset.isEmpty() && total) {
                impactPerResourceAndDataset.each { String resourceType, Double value ->
                    Integer score = Math.round((value / total) * 100).intValue()
                    ResourceType type = resourceTypeCache.getResourceTypeFromCache(resourceType)

                    if (score != null && type) {
                        percentageScores.put(resourceTypeService.getLocalizedName(type), score)
                    }
                }
            }

        }
        percentageScores = percentageScores.sort({ -it.value }).take(5)
        String templateAsString = g.render(template: "/entity/resourceTypeImpactBars", model: [percentageScores: percentageScores, calculationRuleName: calculationRuleName, resultCategory: resultCategoryCategory, childEntity: childEntity]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resourceSubTypeBars() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String calculationRuleId = params.calculationRuleId
        Entity childEntity = entityService.getEntityById(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule calculationRule = indicator?.getGraphCalculationRuleObjects(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(calculationRuleId) })
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        Map<String, Double> impactPerResourceAndDataset = [:]
        Map<String, Integer> percentageScores = [:]
        String calculationRulename = ""
        String resultCategoryCategory = ""

        if (childEntity && calculationRule && resultCategories && indicatorId) {
            ResultCategory materialsImpact = resultCategories?.get(0)
            resultCategoryCategory = materialsImpact.resultCategory
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, calculationRule.calculationRuleId, materialsImpact.resultCategoryId)
            List<Dataset> impactDatasets = childEntity.getDatasetsForResultCategoryExpand(indicatorId, materialsImpact.resultCategoryId, resultsForIndicator)
            calculationRulename = calculationRule.localizedShortName ? calculationRule.localizedShortName : calculationRule.localizedName

            ResourceCache resourceCache
            ResourceTypeCache resourceTypeCache

            if (impactDatasets) {
                resourceCache = ResourceCache.init(impactDatasets as List)
                resourceTypeCache = resourceCache?.getResourceTypeCache()

                for (Dataset d: impactDatasets) {
                    String type = resourceTypeCache.getResourceSubType(resourceCache.getResource(d))
                    Double value = childEntity?.getResultForDataset(d.manualId, indicatorId, materialsImpact.resultCategoryId, calculationRuleId, resultsForIndicator)

                    if (value) {
                        if (!impactPerResourceAndDataset.get(type)) {
                            impactPerResourceAndDataset.put(type, value)
                        } else {
                            impactPerResourceAndDataset.put(type, value + impactPerResourceAndDataset.get(type))

                        }
                    }
                }
            }
            Double total = childEntity.getResult(indicator.indicatorId, calculationRule.calculationRuleId, materialsImpact.resultCategoryId, resultsForIndicator)

            if (impactPerResourceAndDataset && !impactPerResourceAndDataset.isEmpty() && total) {
                impactPerResourceAndDataset.each { String resourceType, Double value ->
                    ResourceType subType = resourceTypeCache.getResourceTypeFromCache(resourceType)
                    Integer score = Math.round((value / total) * 100).toInteger()

                    if (score != null && subType && subType.isSubType) {
                        percentageScores.put(resourceTypeService.getLocalizedName(subType), score)
                    }
                }
            }
        }
        percentageScores = percentageScores.sort({ -it.value }).take(5)
        String templateAsString = g.render(template: "/entity/resourceSubTypeBars", model: [percentageScores: percentageScores, calculationRuleName: calculationRulename, resultCategory: resultCategoryCategory, childEntity: childEntity]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def buildingElementBars() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String calculationRuleId = params.calculationRuleId
        Entity childEntity = entityService.getEntityById(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule calculationRule = indicator?.getGraphCalculationRuleObjects(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(calculationRuleId) })
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        Map<String, Integer> percentageScores = [:]
        Map<String, Double> totalBySection = [:]
        String calculationRulename = ""
        String resultCategoryCategory = ""

        if (resultCategories && indicator && childEntity && calculationRule) {
            ResultCategory materialsImpact = resultCategories?.get(0)
            resultCategoryCategory = materialsImpact.resultCategory
            List<Query> indicatorQueries = indicator.getQueries(childEntity)
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, calculationRule.calculationRuleId, materialsImpact.resultCategoryId)
            calculationRulename = calculationRule.localizedShortName ? calculationRule.localizedShortName : calculationRule.localizedName

            if (indicatorQueries) {
                indicatorQueries.each { Query query ->
                    query.getSections()?.each { QuerySection querySection ->
                        querySectionService.getAllUnderlyingQuestions(childEntity.entityClass, querySection)?.each { Question question ->
                            List<Dataset> foundDatasets = childEntity.
                                    getDatasetsForResultCategoryExpand(indicator.indicatorId, materialsImpact.resultCategoryId, resultsForIndicator)?.
                                    findAll({
                                        query.queryId.equals(it.queryId) && querySection.sectionId.equals(it.sectionId) && question.questionId.equals(it.questionId)
                                    })

                            if (foundDatasets) {
                                foundDatasets.each { Dataset dataset ->
                                    Double result = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                            materialsImpact.resultCategoryId, calculationRule.calculationRuleId,
                                            resultsForIndicator)
                                    if (result) {
                                        Double existingValue = totalBySection.get(querySection.localizedName)

                                        if (!existingValue) {
                                            totalBySection.put(querySection.localizedName, result)
                                        } else {
                                            totalBySection.put(querySection.localizedName, result + existingValue)

                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                Double total = childEntity.getResult(indicator.indicatorId, calculationRule.calculationRuleId,
                        materialsImpact.resultCategoryId, resultsForIndicator)
                if (totalBySection && !totalBySection.isEmpty() && total) {
                    totalBySection.each { String key, Double value ->
                        Integer score = Math.round((value / total) * 100).intValue()

                        if (score != null) {
                            percentageScores.put(key, score)
                        }
                    }
                }
                percentageScores = percentageScores.sort({ -it.value }).take(5)
            }
        }
        String templateAsString = g.render(template: "/entity/buildingElementBars", model: [percentageScores: percentageScores, calculationRuleName: calculationRulename, resultCategory: resultCategoryCategory, childEntity: childEntity]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def graphByIndicator() {
        String strokeAndPointColor
        String colorFill
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        List<String> strokeAndPointColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        Entity entity = entityService.readEntity(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(entity)?.findAll({ !it.hideInGraphs })
        List<Entity> designs = entityService.getChildEntitiesForShowing(entity, EntityClass.DESIGN.getType()).findAll({ Entity design -> design?.isIndicatorReady(indicator, null, false) })

        Denominator denominator = indicator?.getDisplayDenominator()
        String graphDatasets = "["
        String graphLabels = "["

        designs = designs?.findAll { !it?.disabledIndicators?.contains(indicatorId) && !it?.isHiddenDesign } ?: []

        if (calculationRules && designs && indicator) {
            int colorIndex = 0
            Map<Entity, Map> percentsByDesign = [:]
            Map<Entity, Map> scoresByDesigns = [:]
            Map<String, Double> highestScoreByRule = [:]
            Map<String, Double> existingPercentualScores

            designs = designs?.findAll { !it.disabledIndicators?.contains(indicatorId) }

            designs.each { Entity design ->

                Map<String, Double> scoresForDesignByRule = new LinkedHashMap<String, Double>()

                calculationRules.each { CalculationRule calculationRule ->
                    Double score
                    if (denominator) {
                        score = design?.getTotalResultByNonDynamicDenominator(indicatorId, calculationRule.calculationRuleId, denominator.denominatorId)
                    } else {
                        score = design?.getTotalResult(indicatorId, calculationRule.calculationRuleId)
                    }

                    if (score != null) {
                        scoresForDesignByRule.put(calculationRule.calculationRule, score)
                        Double existingHighest = highestScoreByRule.get(calculationRule.calculationRule)

                        if (existingHighest == null || score > existingHighest) {
                            highestScoreByRule.put(calculationRule.calculationRule, score)

                        }

                        if (!scoresForDesignByRule.isEmpty()) {
                            scoresByDesigns.put(design, scoresForDesignByRule)

                        }
                    }
                }
            }

            Map<String, Double> ruleAndSmallestScore = [:]
            scoresByDesigns.each { Entity design, Map<String, Double> scoresByRule ->
                calculationRules.each { CalculationRule calcRule ->
                    Double currentScore = scoresByRule.get(calcRule.calculationRule)

                    if (currentScore != null) {
                        if (!ruleAndSmallestScore.get(calcRule.calculationRule)) {
                            ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                        } else {
                            if (ruleAndSmallestScore.get(calcRule.calculationRule) && ruleAndSmallestScore.get(calcRule.calculationRule) > currentScore) {
                                ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                            }
                        }
                    }
                }
            }
            Boolean dontEugenize = Boolean.TRUE
            Map<String, Double> ruleAndAbsoluteScore = [:]
            if (ruleAndSmallestScore && !ruleAndSmallestScore.isEmpty()) {
                ruleAndSmallestScore.each { String rule, Double smallest ->
                    if (smallest != null) {
                        ruleAndAbsoluteScore.put(rule, Math.abs(smallest))
                        if (smallest < 0) {
                            dontEugenize = Boolean.FALSE
                        }
                    }

                }
            }


            if (!scoresByDesigns.isEmpty() && !highestScoreByRule.isEmpty()) {
                calculationRules.each { CalculationRule calculationRule ->
                    Double highestScore

                    scoresByDesigns.each { Entity design, Map<String, Double> scoreByRule ->
                        Double score
                        if (!dontEugenize) {
                            score = scoreByRule.get(calculationRule.calculationRule) ? scoreByRule.get(calculationRule.calculationRule) : ruleAndAbsoluteScore.get(calculationRule.calculationRule) ? 0 + ruleAndAbsoluteScore.get(calculationRule.calculationRule) : 0
                            highestScore = highestScoreByRule.get(calculationRule.calculationRule) && ruleAndAbsoluteScore.get(calculationRule.calculationRule) ? highestScoreByRule.get(calculationRule.calculationRule) + ruleAndAbsoluteScore.get(calculationRule.calculationRule) : 0
                        } else {
                            score = scoreByRule.get(calculationRule.calculationRule)
                            highestScore = highestScoreByRule.get(calculationRule.calculationRule)
                        }

                        if (score && highestScore) {
                            Double percentToHighestScore
                            percentToHighestScore = (score / highestScore * 100).toInteger()

                            existingPercentualScores = percentsByDesign.get(design)

                            if (existingPercentualScores) {
                                existingPercentualScores.put(calculationRule.calculationRule, percentToHighestScore)

                            } else {
                                existingPercentualScores = [(calculationRule.calculationRule): percentToHighestScore]
                            }
                            percentsByDesign.put(design, existingPercentualScores)
                        }
                    }

                }
            }
            percentsByDesign?.each { Entity design, Map<String, Double> percentsByRule ->
                if (colorIndex + 1 > strokeAndPointColors.size()) {
                    colorIndex = 0
                }
                strokeAndPointColor = strokeAndPointColors.get(colorIndex)
                graphDatasets = "${graphDatasets}  {label: \"${design.operatingPeriodAndName}\","
                graphDatasets = "${graphDatasets}  backgroundColor: \"${strokeAndPointColor}\",\n"
                graphDatasets = "${graphDatasets} data: ["
                int scoreIndex = 0
                calculationRules.each { CalculationRule calcRule ->
                    Double percentualScore = percentsByRule.get(calcRule.calculationRule)
                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${percentualScore ? percentualScore : '0'}"
                    scoreIndex++
                }
                graphDatasets = "${graphDatasets}]},"
                colorIndex++
            }

            int labelIndex = 0

            calculationRules.each { CalculationRule rule ->
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${abbr(value: rule.localizedShortName ?: rule.localizedName, maxLength: 30)}\""
                labelIndex++
            }

        }
        graphDatasets = "${graphDatasets}]"
        graphLabels = "${graphLabels}]"
        String templateAsString = g.render(template: "/entity/graphByIndicator", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, indicator: indicator, entity: entity, drawBars: true]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getGraphDatasetsOnlyDesign() {

        String strokeAndPointColor
        String colorFill
        String entityId = request?.JSON.entityId
        String indicatorId = request?.JSON.indicatorId
        List<String> removeFromCalculation = (request?.JSON.removeFromCalculation as ArrayList<String>) ?: []

        List<String> strokeAndPointColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        List<String> fillColors = ['rgba(127,196,204,0.1)', 'rgba(247,119,0,0.1)', 'rgba(119, 213, 109, 0.1)', 'rgba(115,49,70,0.1)', 'rgba(0,128,0,0.1)', 'rgba(243,153,131,0.1)', 'rgba(144, 127, 204, 0.1)', 'rgba(58, 82, 156, 0.1)', 'rgba(255, 204, 0, 0.1)', 'rgba(67,67,72,0.1)', 'rgba(128, 122,0,0.1)', 'rgba(219,28,17,0.1)']
        Entity entity = entityId ? entityService.readEntity(entityId) : null
        Indicator indicator = indicatorId ? indicatorService.getIndicatorByIndicatorId(indicatorId, true) : null
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(entity)?.findAll({ !it.hideInGraphs })
        List<Entity> designs = entity?.designs?.findAll({
            !removeFromCalculation.contains(it.operatingPeriodAndName) && it.isIndicatorReady(indicator)
        })
        Denominator denominator = indicator?.getDisplayDenominator()
        List<Map<String, Object>> designsAndDatasets = []
        if (calculationRules && designs && indicator && entity) {

            int colorIndex = 0
            Map<Entity, Map> percentsByDesign = [:]
            Map<Entity, Map> scoresByDesigns = [:]
            Map<String, Double> highestScoreByRule = [:]

            designs.each { Entity design ->
                Map<String, Double> scoresForDesignByRule = new LinkedHashMap<String, Double>()

                calculationRules.each { CalculationRule calculationRule ->
                    Double score
                    if (denominator) {
                        score = design?.getTotalResultByNonDynamicDenominator(indicatorId, calculationRule.calculationRuleId, denominator.denominatorId)
                    } else {
                        score = design?.getTotalResult(indicatorId, calculationRule.calculationRuleId)
                    }

                    if (score != null) {
                        scoresForDesignByRule.put(calculationRule.calculationRule, score)
                        Double existingHighest = highestScoreByRule.get(calculationRule.calculationRule)

                        if (existingHighest == null || score > existingHighest) {
                            highestScoreByRule.put(calculationRule.calculationRule, score)

                        }

                        if (!scoresForDesignByRule.isEmpty()) {
                            scoresByDesigns.put(design, scoresForDesignByRule)

                        }
                    }
                }
            }
            Map<String, Double> ruleAndSmallestScore = [:]
            scoresByDesigns.each { Entity design, Map<String, Double> scoresByRule ->
                calculationRules.each { CalculationRule calcRule ->
                    Double currentScore = scoresByRule.get(calcRule.calculationRule)

                    if (currentScore != null) {
                        if (!ruleAndSmallestScore.get(calcRule.calculationRule)) {
                            ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                        } else {
                            if (ruleAndSmallestScore.get(calcRule.calculationRule) && ruleAndSmallestScore.get(calcRule.calculationRule) > currentScore) {
                                ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                            }
                        }
                    }
                }
            }
            Boolean dontEugenize = Boolean.TRUE
            Map<String, Double> ruleAndAbsoluteScore = [:]
            if (ruleAndSmallestScore && !ruleAndSmallestScore.isEmpty()) {
                ruleAndSmallestScore.each { String rule, Double smallest ->
                    if (smallest != null) {
                        ruleAndAbsoluteScore.put(rule, Math.abs(smallest))
                        if (smallest < 0) {
                            dontEugenize = Boolean.FALSE
                        }
                    }

                }
            }

            if (!scoresByDesigns.isEmpty() && !highestScoreByRule.isEmpty()) {
                calculationRules.each { CalculationRule calculationRule ->
                    Double highestScore

                    scoresByDesigns.each { Entity design, Map<String, Double> scoreByRule ->
                        Double score

                        if (!dontEugenize) {
                            score = scoreByRule.get(calculationRule.calculationRule) ? scoreByRule.get(calculationRule.calculationRule) : ruleAndAbsoluteScore.get(calculationRule.calculationRule) ? 0 + ruleAndAbsoluteScore.get(calculationRule.calculationRule) : 0
                            highestScore = highestScoreByRule.get(calculationRule.calculationRule) && ruleAndAbsoluteScore.get(calculationRule.calculationRule) ? highestScoreByRule.get(calculationRule.calculationRule) + ruleAndAbsoluteScore.get(calculationRule.calculationRule) : 0
                        } else {
                            score = scoreByRule.get(calculationRule.calculationRule)
                            highestScore = highestScoreByRule.get(calculationRule.calculationRule)
                        }

                        if (score && highestScore) {
                            Double percentToHighestScore
                            percentToHighestScore = (score / highestScore * 100).toInteger()

                            Map<String, Double> existingPercentualScores = percentsByDesign.get(design)

                            if (existingPercentualScores) {
                                existingPercentualScores.put(calculationRule.calculationRule, percentToHighestScore)
                            } else {
                                existingPercentualScores = [(calculationRule.calculationRule): percentToHighestScore]
                            }
                            percentsByDesign.put(design, existingPercentualScores)
                        }
                    }

                }
            }
            percentsByDesign?.each { Entity design, Map<String, Double> percentsByRule ->
                if (colorIndex + 1 > strokeAndPointColors.size()) {
                    colorIndex = 0
                }
                Map<String, Object> datasetMap = [:]

                strokeAndPointColor = strokeAndPointColors.get(colorIndex)
                datasetMap.put("label", design.operatingPeriodAndName)
                datasetMap.put("backgroundColor", strokeAndPointColor)
                def data = []
                int scoreIndex = 0
                calculationRules.each { CalculationRule calcRule ->
                    Double percentualScore = percentsByRule.get(calcRule.calculationRule)
                    data.add(percentualScore != null ? percentualScore : 0)
                    scoreIndex++
                }
                datasetMap.put("data", data)

                colorIndex++
                designsAndDatasets.add(datasetMap)
            }

        }
        render designsAndDatasets as JSON
    }

    def operatingGraphByIndicator() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String denominatorId = params.denominatorId
        Entity entity = entityService.readEntity(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<Entity> operatingPeriods = entity?.operatingPeriods?.findAll({ Entity operatingPeriod -> operatingPeriod?.isIndicatorReady(indicator) && !operatingPeriod?.isHiddenDesign })

        if (operatingPeriods && indicator && entity) {
            List<CalculationRule> calculationRules = indicator.getResolveCalculationRules(entity)?.findAll({ !it.hideInGraphs })
            Denominator denominator = indicator?.getDisplayDenominator()
            String colorFill
            List<String> fillColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
            String graphLabels = "["
            int labelIndex = 0

            calculationRules.each { CalculationRule rule ->
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${abbr(value: rule.localizedShortName ?: rule.localizedName, maxLength: 30)}\""
                labelIndex++
            }
            graphLabels = "${graphLabels}]"


            String graphDatasets = "["

            int colorIndex = 0
            Map<Entity, Map> percentsByOperatingPeriod = [:]
            Map<Entity, Map> scoresByOperatingPeriods = [:]
            Map<String, Double> highestScoreByRule = [:]

            operatingPeriods.each { Entity operatingPeriod ->
                Map<String, Double> scoresForOperatingPeriodnByRule = new LinkedHashMap<String, Double>()

                calculationRules.each { CalculationRule calculationRule ->
                    Double score

                    if (denominator) {
                        score = operatingPeriod?.getTotalResultByNonDynamicDenominator(indicator.indicatorId, calculationRule.calculationRuleId, denominator.denominatorId)
                    } else {
                        score = operatingPeriod?.getTotalResult(indicator.indicatorId, calculationRule.calculationRuleId)
                    }

                    if (score != null) {
                        scoresForOperatingPeriodnByRule.put(calculationRule.calculationRule, score)
                        Double existingHighest = highestScoreByRule.get(calculationRule.calculationRule)

                        if (existingHighest == null || score > existingHighest) {
                            highestScoreByRule.put(calculationRule.calculationRule, score)
                        }
                    }

                }

                if (!scoresForOperatingPeriodnByRule.isEmpty()) {
                    scoresByOperatingPeriods.put(operatingPeriod, scoresForOperatingPeriodnByRule)
                }
            }


            Map<String, Double> ruleAndSmallestScore = [:]
            scoresByOperatingPeriods.each { Entity design, Map<String, Double> scoresByRule ->
                calculationRules.each { CalculationRule calcRule ->
                    Double currentScore = scoresByRule.get(calcRule.calculationRule)

                    if (!ruleAndSmallestScore.get(calcRule.calculationRule)) {
                        ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                    } else {
                        if (ruleAndSmallestScore.get(calcRule.calculationRule) && ruleAndSmallestScore.get(calcRule.calculationRule) > currentScore) {
                            ruleAndSmallestScore.put(calcRule.calculationRule, currentScore)
                        }
                    }
                }

            }

            Boolean dontEugenize = Boolean.TRUE
            Map<String, Double> ruleAndAbsoluteScore = [:]
            if (ruleAndSmallestScore && !ruleAndSmallestScore.isEmpty()) {
                ruleAndSmallestScore.each { String rule, Double smallest ->
                    if (smallest != null) {
                        ruleAndAbsoluteScore.put(rule, Math.abs(smallest))
                        if (smallest < 0) {
                            dontEugenize = Boolean.FALSE
                        }
                    }

                }


            }


            if (!scoresByOperatingPeriods.isEmpty() && !highestScoreByRule.isEmpty()) {
                calculationRules.each { CalculationRule calculationRule ->
                    Double highestScore = highestScoreByRule.get(calculationRule.calculationRule)

                    if (highestScore) {
                        scoresByOperatingPeriods.each { Entity operatingPeriod, Map<String, Double> scoreByRule ->
                            Double score = scoreByRule.get(calculationRule.calculationRule)

                            if (score) {
                                if (!dontEugenize && ruleAndAbsoluteScore?.get(calculationRule.calculationRule) != null) {
                                    score = score + ruleAndAbsoluteScore.get(calculationRule.calculationRule)
                                    highestScore = highestScore + ruleAndAbsoluteScore.get(calculationRule.calculationRule)
                                }
                                Double percentToHighestScore
                                percentToHighestScore = (score / highestScore * 100).toInteger()

                                Map<String, Double> existingPercentualScores = percentsByOperatingPeriod.get(operatingPeriod)

                                if (existingPercentualScores) {
                                    existingPercentualScores.put(calculationRule.calculationRule, percentToHighestScore)
                                } else {
                                    existingPercentualScores = [(calculationRule.calculationRule): percentToHighestScore]
                                }
                                percentsByOperatingPeriod.put(operatingPeriod, existingPercentualScores)
                            }

                        }
                    }

                }
            }
            percentsByOperatingPeriod?.sort({ it.key })?.each { Entity operatingPeriod, Map percentsByRule ->
                if (colorIndex + 1 > fillColors.size()) {
                    colorIndex = 0
                }
                colorFill = fillColors.get(colorIndex)
                graphDatasets = "${graphDatasets}  {label: \"${operatingPeriod.operatingPeriodAndName}\","
                graphDatasets = "${graphDatasets}  backgroundColor: \"${colorFill}\",\n"
                graphDatasets = "${graphDatasets} data: ["
                int scoreIndex = 0

                calculationRules.each { CalculationRule calcRule ->
                    Double percentualScore = percentsByRule.get(calcRule.calculationRule)
                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${percentualScore ? percentualScore : '0'}"
                    scoreIndex++
                }
                graphDatasets = "${graphDatasets}]},"
                colorIndex++
            }
            graphDatasets = "${graphDatasets}]"
            String templateAsString = g.render(template: "/entity/graphByOperatingIndicator", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, indicator: indicator, entity: entity, drawBars: true]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }


    def drawMonthlyOperating() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String denominatorId = params.denominatorId
        Entity entity = entityService.readEntity(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<Entity> operatingPeriods = entity?.operatingPeriods?.findAll({ Entity operatingPeriod -> operatingPeriod?.isIndicatorReady(indicator) && !operatingPeriod?.isHiddenDesign })
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(entity)?.findAll({ !it.hideInGraphs })
        String colorFill
        List<String> fillColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        def months = []
        for (int i = 1; i < 13; i++) {
            months.add("${message(code: "month." + i)}")
        }
        String graphLabels = "["
        int labelIndex = 0

        if (calculationRules && operatingPeriods && indicator && entity) {
            months.each {
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${it}\""
                labelIndex++
            }
            graphLabels = graphLabels + "]"

            String graphDatasets = "["

            int colorIndex = 0
            Map<Entity, Map> scoresByOperatingPeriods = [:]
            Denominator denominator = indicator?.denominatorListAsDenominatorObjects?.find({
                denominatorId?.equals(it.denominatorId)
            })
            List<Dataset> datasets

            operatingPeriods.each { Entity operatingPeriod ->
                if ("dynamicDenominator".equals(denominator?.denominatorType)) {
                    datasets = denominatorService.getDynamicDenominatorDatasets(operatingPeriod, denominator)
                } else if ("monthlyValueReference".equals(denominator?.denominatorType)) {
                    datasets = denominatorService.getMonthlyValuereferenceDatasets(operatingPeriod, denominator)
                }

                Map<String, Double> scoresForOperatingPeriodnByRule = new LinkedHashMap<String, Double>()
                CalculationTotalResult calculationTotalResult = operatingPeriod.getCalculationTotalResults()?.find({
                    indicator.indicatorId.equals(it.indicatorId)
                })
                int monthIndex = 1

                calculationRules.each { CalculationRule calculationRule ->
                    Double score
                    if (calculationTotalResult) {
                        Map<String, Map<String, Double>> monthlyTotals = calculationTotalResult.monthlyTotalsPerCalculationRule

                        months.each { String month ->
                            if (denominator && datasets) {
                                datasets?.each { Dataset d ->
                                    Map<String, Double> denominatorResultsMonthly = operatingPeriod.getResultByDynamicDenominator(indicator.indicatorId, calculationRule.calculationRuleId, d.manualId, Boolean.TRUE, denominator)
                                    score = denominatorResultsMonthly?.get(monthIndex.toString())
                                }
                            } else {
                                if (monthlyTotals) {
                                    score = monthlyTotals?.get(calculationRule?.calculationRuleId)?.get(monthIndex.toString())

                                }
                            }
                            monthIndex++

                            if (score != null) {
                                scoresForOperatingPeriodnByRule.put(month, score)
                                scoresByOperatingPeriods.put(operatingPeriod, scoresForOperatingPeriodnByRule)

                            }
                        }

                    }
                }
            }
            scoresByOperatingPeriods?.each { Entity operatingPeriod, Map<String, Double> scores ->
                if (colorIndex + 1 > fillColors.size()) {
                    colorIndex = 0
                }
                colorFill = fillColors.get(colorIndex)
                graphDatasets = "${graphDatasets}  {label: \"${operatingPeriod.operatingPeriodAndName}\","
                graphDatasets = "${graphDatasets}  backgroundColor: \"${colorFill}\",\n"
                graphDatasets = "${graphDatasets} data: ["
                int scoreIndex = 0

                months.each { String month ->
                    Double score = scores?.get(month)
                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${score ? score : '0'}"
                    scoreIndex++
                }
                graphDatasets = "${graphDatasets}]},"
                colorIndex++
            }
            graphDatasets = "${graphDatasets}]"
            String templateAsString = g.render(template: "/entity/graphByOperatingIndicator", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, indicator: indicator, entity: entity, drawBars: true, isMonthly: true, denominator: denominator]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def entityPieGraph() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String carbonHeroIdFallback = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, "benchmarkingIndicatorId").value
        def color
        def highColor
        List<String> colors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        List<String> highLightColors = ['rgba(127,196,204,0.1)', 'rgba(247,119,0,0.1)', 'rgba(119, 213, 109, 0.1)', 'rgba(115,49,70,0.1)', 'rgba(0,128,0,0.1)', 'rgba(243,153,131,0.1)', 'rgba(144, 127, 204, 0.1)', 'rgba(58, 82, 156, 0.1)', 'rgba(255, 204, 0, 0.1)', 'rgba(67,67,72,0.1)', 'rgba(128, 122,0,0.1)', 'rgba(219,28,17,0.1)']
        Entity design = entityService.readEntity(entityId)
        Entity parentEntity = design?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        BenchmarkSettings benchmarkSettings = indicator?.benchmarkSettings
        if(!benchmarkSettings){
            indicator = indicatorService.getIndicatorByIndicatorId(carbonHeroIdFallback, true)
        }
        String gwpRuleId = benchmarkSettings?.displayRuleId ?: indicator?.displayResult
        CalculationRule gwpRule = indicator?.getResolveCalculationRules(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(gwpRuleId) })
        String GWP = gwpRule?.calculationRule
        Boolean draw1 = Boolean.FALSE
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        List<CalculationResult> resultsByIndicator = design?.getCalculationResultObjects(indicator?.indicatorId, null, null)
        String datasets = "["
        String calculationRuleName = "["
        int colorIndex = 0
        String labels = "["
        String backgroundColors = "["
        String hoverColors = "["

        if (design && GWP && resultCategories && indicator && resultsByIndicator) {
            calculationRuleName = gwpRule.localizedName
            if (calculationRuleName) {
                calculationRuleName = abbr(value: calculationRuleName, maxLength: 40)
            }
            Double totalScore = design.getTotalResult(indicator.indicatorId, gwpRule.calculationRuleId)
            List<String> categoriesWithScores = []
            int scoreIndex = 0
            resultCategories.each { ResultCategory resultCategory ->
                Double score = resultsByIndicator.find({
                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && gwpRule.calculationRuleId.equals(it.calculationRuleId)
                })?.result
                Integer percentage = 0


                if (score && score > 0 && totalScore) {
                    score = Math.round((score / totalScore * 100))?.toInteger()

                    if (score > 0) {

                        categoriesWithScores.add(resultCategory.resultCategoryId)
                        percentage = score
                        String localizedShortName = resultCategoryService.getLocalizedShortName(resultCategory, 15)
                        labels = "${labels} ${scoreIndex > 0 ? ", " : ""}\"${resultCategory.resultCategory} ${localizedShortName}- ${percentage.toString() + " %"}\""

                        if (colorIndex + 1 > colors.size()) {
                            colorIndex = 0
                        }
                        color = colors.get(colorIndex)
                        highColor = highLightColors.get(colorIndex)
                        hoverColors = "${hoverColors}${scoreIndex > 0 ? ', ' : ''}\"${highColor}\""
                        backgroundColors = "${backgroundColors}${scoreIndex > 0 ? ', ' : ''}\"${color}\""
                        datasets = "${datasets}${scoreIndex > 0 ? ', ' : ''}${percentage}"


                        colorIndex++
                        scoreIndex++
                    }
                }
            }
            if (categoriesWithScores && !categoriesWithScores.isEmpty()) {
                draw1 = Boolean.TRUE
            }

        }
        backgroundColors = "${backgroundColors}]"
        hoverColors = "${hoverColors}]"
        labels = "${labels}]"
        datasets = "${datasets}]"
        String templateAsString = g.render(template: "/entity/entityPieGraph", model: [datasets: datasets, calculationRuleName: calculationRuleName, indicator: indicator, draw1: draw1, entity: design, gwpRule: gwpRule, labels: labels, hoverColors: hoverColors, backgroundColors: backgroundColors]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def monthlyGraph() {
        String colorFill
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        List<String> fillColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        Entity entity = entityService.readEntity(entityId)
        Entity parentEntity = entity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parentEntity)?.findAll({ !it.hideInGraphs })
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parentEntity)?.findAll({ !it.hideInGraphs })
        List<CalculationResult> calculationResults = entity?.getCalculationResultObjects(indicator?.indicatorId, null, null)
        IndicatorReport report = indicator?.report
        def reportItem = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, report?.reportItems)
        String denominatorId = params.denominatorId
        String heading = indicatorService.getLocalizedText(reportItem?.find({ it.type.equals("heading") }))


        int colorIndex = 0
        def months = []


        for (int i = 1; i < 13; i++) {
            months.add("${message(code: "month." + i)}")
        }
        String graphLabels = "["
        String graphDatasets = "["

        if (indicator && entity && resultCategories) {
            int labelIndex = 0


            months.each {
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${it}\""

                labelIndex++
            }

            resultCategories.each { ResultCategory resCat ->

                if (colorIndex + 1 > fillColors.size()) {
                    colorIndex = 0
                }
                int scoreIndex = 0
                colorFill = fillColors.get(colorIndex)
                graphDatasets = "${graphDatasets}  {label: \"${resCat.localizedName}\","
                graphDatasets = "${graphDatasets}  backgroundColor: \"${colorFill}\",\nstack: \'Stack 0\',\n"
                graphDatasets = "${graphDatasets} data: ["
                int monthIndex = 1
                Double resultPerMonth
                calculationRules.each { CalculationRule rule ->

                    months.each {
                        resultPerMonth = calculationResults?.find({
                            resCat.resultCategoryId.equals(it.resultCategoryId) && rule.calculationRuleId.equals(it.calculationRuleId)
                        })?.monthlyResults?.get(monthIndex.toString())
                        monthIndex++
                        graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${resultPerMonth ? resultPerMonth.round(0) : '0'}"
                        scoreIndex++
                    }

                }

                graphDatasets = "${graphDatasets}]},"
                colorIndex++


            }

        }
        graphLabels = "${graphLabels}]"
        graphDatasets = "${graphDatasets}]"
        String templateAsString = g.render(template: "/entity/monthlyGraph", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, entity: entity, indicator: indicator, heading: heading]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resultTableGraph() {
        String strokeAndPointColor
        String colorFill
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        List<String> strokeAndPointColors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        List<String> fillColors = ['rgba(127,196,204,0.1)', 'rgba(247,119,0,0.1)', 'rgba(119, 213, 109, 0.1)', 'rgba(115,49,70,0.1)', 'rgba(0,128,0,0.1)', 'rgba(243,153,131,0.1)', 'rgba(144, 127, 204, 0.1)', 'rgba(58, 82, 156, 0.1)', 'rgba(255, 204, 0, 0.1)', 'rgba(67,67,72,0.1)', 'rgba(128, 122,0,0.1)', 'rgba(219,28,17,0.1)']
        Entity entity = entityService.readEntity(entityId)
        Entity parentEntity = entity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parentEntity)?.findAll({ !it.hideInGraphs })
        List<CalculationRule> calculationRules = indicator?.getResolveCalculationRules(parentEntity)?.findAll({ !it.hideInGraphs })
        List<CalculationResult> calculationResults = entity?.getCalculationResultObjects(indicator?.indicatorId, null, null)
        IndicatorReport report = indicator?.report
        def reportItem = indicatorReportService.getReportItemsAsReportItemObjects(parentEntity, report?.reportItems)
        String heading = indicatorService.getLocalizedText(reportItem?.find({ it.type.equals("heading") }))


        int colorIndex = 0

        String graphLabels = "["
        String graphDatasets = "["

        if (indicator && entity && resultCategories) {
            int labelIndex = 0


            resultCategories.each { ResultCategory category ->
                String localizedShortName = resultCategoryService.getLocalizedShortName(category, 15)
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${abbr(value: localizedShortName, maxLength: 15)}\""

                labelIndex++
            }

            Double score

            calculationRules.each { CalculationRule rule ->

                if (colorIndex + 1 > fillColors.size()) {
                    colorIndex = 0
                }
                int scoreIndex = 0
                colorFill = fillColors.get(colorIndex)
                strokeAndPointColor = strokeAndPointColors.get(colorIndex)
                graphDatasets = "${graphDatasets}  {label: \"${rule.localizedName}\","
                graphDatasets = "${graphDatasets}  fillColor: \"${colorFill}\",\n" +
                        "                strokeColor: \"${strokeAndPointColor}\",\n" +
                        "                pointColor: \"${strokeAndPointColor}\",\n" +
                        "                pointStrokeColor: \"#fff\",\n" +
                        "                pointHighlightFill: \"#fff\",\n" +
                        "                pointHighlightStroke:\"rgba(220,220,220,1)\","
                graphDatasets = "${graphDatasets} data: ["
                resultCategories.each { ResultCategory resultCategory ->
                    score = calculationResults.find({
                        it.calculationRuleId.equals(rule.calculationRuleId) && it.resultCategoryId.equals(resultCategory.resultCategoryId)
                    })?.result
                    graphDatasets = "${graphDatasets}${scoreIndex > 0 ? ', ' : ''}${score ? score : '0'}"
                    scoreIndex++
                }
                graphDatasets = "${graphDatasets}]},"
                colorIndex++

            }
        }
        graphLabels = "${graphLabels}]"
        graphDatasets = "${graphDatasets}]"
        String templateAsString = g.render(template: "/entity/resultTableGraph", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, entity: entity, indicator: indicator, heading: heading]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resultsLcaCheckerDoughnut() {
        String entityId = params.entityId
        String currentIndicatorId = params.indicatorId
        String indicatorId = "xBenchmarkEmbodied"
        Entity childEntity = entityService.readEntity(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Indicator currentIndicator = indicatorService.getIndicatorByIndicatorId(currentIndicatorId, true)
        List<String> queries = indicator.getIndicatorQueries().findAll({ !it.projectLevel }).collect({ it.queryId })

        List<LcaChecker> checksNotApplicable = []
        Map<LcaChecker, List<Object>> suppressedChecks = [:]
        //LcaChecker and points, 0 is points, 1 is target e.g. passed / failed / erosion
        Map<LcaChecker, Double> checksPassed = [:]
        Map<LcaChecker, Double> checksFailed = [:]
        Map<LcaChecker, Double> checksAcceptable = [:]
        Integer percentage = 0
        String color = "#D8D4D4"
        String letter = "G"
        String freeVersion
        Boolean hasAllRequiredResources = Boolean.FALSE
        Query surfaceDefinitionsShared = queryService.getQueryByQueryId("surfaceDefinitionsShared", true)

        if (parentEntity && childEntity && indicator && surfaceDefinitionsShared) {
            hasAllRequiredResources = childEntity.getMissingRequiredResources(currentIndicator, null, surfaceDefinitionsShared).isEmpty() ? Boolean.TRUE : Boolean.FALSE

            if (hasAllRequiredResources) {
                Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.LCA_CHECKER])
                Boolean lcaCheckerLicensed = featuresAllowed.get(Feature.LCA_CHECKER)
                List<String> suppressedCheckIds = childEntity.suppressedLcaCheckers
                List<Dataset> datasets = childEntity.datasets?.toList()?.findAll({ queries.contains(it.queryId) })
                List<LcaChecker> lcaCheckers = lcaCheckerService.getAllLcaCheckers(lcaCheckerLicensed)
                LcaCheckerResult lcaCheckerResult = childEntity.lcaCheckerResult

                if (!lcaCheckerResult?.resultByCheck) {
                    ResourceCache resourceCache = ResourceCache.init(datasets)
                    lcaCheckerResult = newCalculationServiceProxy.getLcaCheckerResultForEntity(childEntity, indicator, lcaCheckers, datasets, suppressedCheckIds, Boolean.FALSE, resourceCache)
                }

                if (lcaCheckerResult && lcaCheckerResult.resultByCheck) {
                    letter = lcaCheckerResult.letter
                    color = lcaCheckerResult.color
                    percentage = lcaCheckerResult.percentage

                    lcaCheckers.each { LcaChecker lcaChecker ->
                        Double result = lcaCheckerResult.resultByCheck.get(lcaChecker.checkId)

                        if (result != null) {
                            if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                if (lcaCheckerResult.failed?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [result, "checksFailed"])
                                } else if (lcaCheckerResult.acceptable?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [result, "checksAcceptable"])
                                } else {
                                    suppressedChecks.put(lcaChecker, [result, "checksPassed"])
                                }
                            } else {
                                if (lcaCheckerResult.failed?.contains(lcaChecker.checkId)) {
                                    checksFailed.put(lcaChecker, result)
                                } else if (lcaCheckerResult.acceptable?.contains(lcaChecker.checkId)) {
                                    checksAcceptable.put(lcaChecker, result)
                                } else {
                                    checksPassed.put(lcaChecker, result)
                                }
                            }
                        } else {
                            checksNotApplicable.add(lcaChecker)
                        }
                    }
                }

                if (!lcaCheckerLicensed) {
                    freeVersion = "${message(code: 'lcaChecker_free_version')}"
                }
            }
        }
        Map outputmap = [:]

        String headingText = "${message(code: 'lcaChecker_heading', args: [parentEntity?.name, childEntity?.name])}"
        String gradingText = "${message(code: 'lcaChecker_grade', args: [letter])}"
        String bodyText = ""
        User user = userService.getCurrentUser(true)
        Boolean internalUser = user.internalUseRoles
        def modifiable = (!parentEntity?.readonly && parentEntity?.getLicenseForIndicator(indicator) && !childEntity?.locked && !childEntity?.superLocked)
        Boolean userReadOnly = Boolean.FALSE
        if (parentEntity?.readonlyUserIds?.contains(user?.id?.toString())
                && !parentEntity?.modifierIds?.contains(user?.id?.toString())
                && !parentEntity?.managerIds?.contains(user?.id?.toString())) {
            userReadOnly = Boolean.TRUE
        }
        String link
        String heading

        if (hasAllRequiredResources) {
            link = "${message(code: 'lcaChecker_grade', args: [letter])}. ${message(code: 'lcaChecker_grade_info')}."


            Query lcaParameters = queryService.getQueryByQueryId(Constants.LCA_PARAMETERS_QUERYID, true)
            Set<Dataset> grossAreaDatasets = childEntity.datasets?.findAll({ surfaceDefinitionsShared.queryId.equals(it.queryId) && "surfaceDefinitionsShared".equals(it.questionId) })
            Dataset areaDataset = grossAreaDatasets?.find({ "surfaceGIFA".equals(it.resourceId) && it.quantity != null }) ?: grossAreaDatasets.find({ "surfaceGFA-sqft".equals(it.resourceId) && it.quantity != null }) ?: grossAreaDatasets.find({ it.quantity != null })

            String area = areaDataset?.quantity?.toString() ?: "[undefined area]"
            String areaUnit = areaDataset?.userGivenUnit ?: "[undefined unit]"

            List<String> frameTypeAnswerIds = parentEntity.datasets?.find({ Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) && "frameType".equals(it.questionId) })?.answerIds
            List<String> scopeAnswerIds = parentEntity.datasets?.find({ Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) && "projectScope".equals(it.questionId) })?.answerIds
            List<String> typeAnswerIds = parentEntity.datasets?.find({ Constants.LCA_PARAMETERS_QUERYID.equals(it.queryId) && "projectType".equals(it.questionId) })?.answerIds

            String type
            String frameType
            String scope

            if (lcaParameters) {
                List<Question> questions = lcaParameters.getAllQuestions()

                if (frameTypeAnswerIds && !frameTypeAnswerIds.isEmpty()) {
                    if (questions) {
                        Question frameTypeQuestion = questions.find({ Question q -> "frameType".equals(q.questionId) })

                        if (frameTypeQuestion) {
                            frameType = frameTypeQuestion.choices?.find({ frameTypeAnswerIds.get(0).equals(it.answerId) })?.localizedAnswer ?: "undefined"
                        }
                    }
                }

                if (typeAnswerIds && !typeAnswerIds.isEmpty()) {
                    if (questions) {
                        Question typeQuestion = questions.find({ Question q -> "projectType".equals(q.questionId) })

                        if (typeQuestion) {
                            type = typeQuestion.choices?.find({ typeAnswerIds.get(0).equals(it.answerId) })?.localizedAnswer ?: "undefined"
                        }
                    }
                }

                if (scopeAnswerIds && !scopeAnswerIds.isEmpty()) {
                    if (questions) {
                        Question scopeQuestion = questions.find({ Question q -> "projectScope".equals(q.questionId) })

                        if (scopeQuestion) {
                            scopeAnswerIds.each { String scopeAnswerId ->
                                String localizedAnswer = scopeQuestion.choices?.find({ scopeAnswerId.equals(it.answerId) })?.localizedAnswer ?: "undefined"

                                if (!"undefined".equals(localizedAnswer)) {
                                    if (scope) {
                                        scope = "${scope}, ${localizedAnswer}"
                                    } else {
                                        scope = "${localizedAnswer}"
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!type) {
                type = "undefined"
            }

            if (!frameType) {
                frameType = "undefined"
            }

            if (!scope) {
                scope = "undefined"
            }
            heading = "${message(code: 'scope.scopeSection', args: [scope, letter])}"

            bodyText = "${g.message(code: 'lcaChecker_body', args: [area, areaUnit, type.toLowerCase(), frameType.toLowerCase(), scope.toLowerCase()])}"
        } else {
            String queryLink = "${g.link(controller: 'query', action: 'form', params: [entityId: parentEntity.id.toString(), childEntityId: childEntity.id.toString(), indicatorId: currentIndicatorId, queryId: surfaceDefinitionsShared.queryId]) { surfaceDefinitionsShared.localizedName }}"
            link = "${message(code: 'lcaChecker_missingData')} ${queryLink}."
            heading = "${message(code: 'scope.scopeSection', args = ['-', '-'])}"


        }

        outputmap.put("link", link)
        outputmap.put("heading", heading)

        String templateAsString = g.render(template: "/entity/resultsLcaCheckerDoughnut", model: [percentage      : percentage, checksFailed: checksFailed?.sort({ -it.key.points }), hasAllRequiredResources: hasAllRequiredResources, queryLink: link,
                                                                                                  checksPassed    : checksPassed?.sort({ -it.key.points }), color: color, entityId: entityId, internalUser: internalUser,
                                                                                                  checksAcceptable: checksAcceptable?.sort({ -it.key.points }), letter: letter, indicatorId: indicatorId,
                                                                                                  headingText     : headingText, gradingText: gradingText, bodyText: bodyText, checksNotApplicable: checksNotApplicable, suppressedChecks: suppressedChecks,
                                                                                                  freeVersion     : freeVersion, modifiable: modifiable, userReadOnly: userReadOnly, user: user]).toString()
        outputmap.put("template", templateAsString)
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }


    def renderLcaCheckerTemplate() {
        String entityId = params.entityId
        String currentIndicatorId = params.indicatorId
        String indicatorId = "xBenchmarkEmbodied"
        Entity childEntity = entityService.readEntity(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        Indicator currentIndicator = indicatorService.getIndicatorByIndicatorId(currentIndicatorId, true)
        List<String> queries = indicator.getIndicatorQueries().findAll({ !it.projectLevel }).collect({ it.queryId })

        List<LcaChecker> checksNotApplicable = []
        Map<LcaChecker, List<Object>> suppressedChecks = [:]
        //LcaChecker and points, 0 is points, 1 is target e.g. passed / failed / erosion
        Map<LcaChecker, Double> checksPassed = [:]
        Map<LcaChecker, List<Double>> checksFailed = [:]
        Map<LcaChecker, Double> checksAcceptable = [:]
        Integer percentage = 0
        String color = "#D8D4D4"
        String letter = "G"
        String letterGrade = "-"
        String freeVersion
        Boolean hasAllRequiredResources = Boolean.FALSE
        Query surfaceDefinitionsShared = queryService.getQueryByQueryId("surfaceDefinitionsShared", true)

        Boolean lcaCheckerAllowed = parentEntity?.lcaCheckerAllowed && currentIndicator?.enableLcaChecker
        if (parentEntity && childEntity && indicator && surfaceDefinitionsShared && lcaCheckerAllowed) {

            hasAllRequiredResources = childEntity.getMissingRequiredResources(currentIndicator, null, surfaceDefinitionsShared).isEmpty() ? Boolean.TRUE : Boolean.FALSE
            if (hasAllRequiredResources) {
                Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, [Feature.LCA_CHECKER])
                Boolean lcaCheckerLicensed = featuresAllowed.get(Feature.LCA_CHECKER)
                List<String> suppressedCheckIds = childEntity.suppressedLcaCheckers
                List<Dataset> datasets = childEntity.datasets?.toList()?.findAll({ queries.contains(it.queryId) })
                List<LcaChecker> lcaCheckers = lcaCheckerService.getAllLcaCheckers(lcaCheckerLicensed)
                LcaCheckerResult lcaCheckerResult = childEntity.lcaCheckerResult
                if (!lcaCheckerResult?.resultByCheck) {
                    ResourceCache resourceCache = ResourceCache.init(datasets)
                    lcaCheckerResult = newCalculationServiceProxy.getLcaCheckerResultForEntity(childEntity, indicator, lcaCheckers, datasets, suppressedCheckIds, Boolean.FALSE, resourceCache)
                }

                if (lcaCheckerResult && lcaCheckerResult.resultByCheck) {
                    letter = lcaCheckerResult.letter
                    color = lcaCheckerResult.color
                    percentage = lcaCheckerResult.percentage

                    lcaCheckers.each { LcaChecker lcaChecker ->
                        Double result = lcaCheckerResult.resultByCheck.get(lcaChecker.checkId)

                        if (result != null) {
                            if (suppressedCheckIds?.contains(lcaChecker.checkId)) {
                                if (lcaCheckerResult.failed?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [result, "checksFailed"])
                                } else if (lcaCheckerResult.acceptable?.contains(lcaChecker.checkId)) {
                                    suppressedChecks.put(lcaChecker, [result, "checksAcceptable"])
                                } else {
                                    suppressedChecks.put(lcaChecker, [result, "checksPassed"])
                                }
                            } else {
                                if (lcaCheckerResult.failed?.contains(lcaChecker.checkId)) {
                                    Double failMagnitude = 0
                                    if ("minimumValue".equalsIgnoreCase(lcaChecker.checkType)) {
                                        failMagnitude = (lcaChecker.checkValue1 - result) / lcaChecker.checkValue1
                                    } else if ("maximumValue".equalsIgnoreCase(lcaChecker.checkType)) {
                                        failMagnitude = (result - lcaChecker.checkValue1) / lcaChecker.checkValue1
                                    } else if ("valueInRange".equalsIgnoreCase(lcaChecker.checkType) || "ratioInRange".equalsIgnoreCase(lcaChecker.checkType)) {
                                        if (result < lcaChecker.checkValue1) {
                                            failMagnitude = (lcaChecker.checkValue1 - result) / lcaChecker.checkValue1
                                        } else {
                                            failMagnitude = (result - lcaChecker.checkValue2) / lcaChecker.checkValue2
                                        }
                                    }
                                    List<Double> resultSet = [result, failMagnitude]
                                    checksFailed.put(lcaChecker, resultSet)
                                } else if (lcaCheckerResult.acceptable?.contains(lcaChecker.checkId)) {
                                    checksAcceptable.put(lcaChecker, result)
                                } else {
                                    checksPassed.put(lcaChecker, result)
                                }
                            }
                        } else {
                            checksNotApplicable.add(lcaChecker)
                        }
                    }
                }

                if (!lcaCheckerLicensed) {
                    freeVersion = "${message(code: 'lcaChecker_free_version')}"
                }
            }
        }
        checksFailed = checksFailed.sort({ -it.value[1] })
        Map outputmap = [:]
        String headingText = "${message(code: 'lcaChecker_heading', args: [parentEntity?.name, childEntity?.name])}"
        String gradingText = "${message(code: 'lcaChecker_grade', args: [letter])}"
        String bodyText = ""
        User user = userService.getCurrentUser(true)
        Boolean internalUser = user.internalUseRoles
        def modifiable = (!parentEntity?.readonly && parentEntity?.getLicenseForIndicator(indicator) && !childEntity?.locked && !childEntity?.superLocked)
        Boolean userReadOnly = Boolean.FALSE
        if (parentEntity?.readonlyUserIds?.contains(user?.id?.toString())
                && !parentEntity?.modifierIds?.contains(user?.id?.toString())
                && !parentEntity?.managerIds?.contains(user?.id?.toString())) {
            userReadOnly = Boolean.TRUE
        }

        String link
        String heading
        if (lcaCheckerAllowed && modifiable && !userReadOnly) {
            if (hasAllRequiredResources) {
                letterGrade = "${message(code: 'grade')}" + " " + letter
                link = "${message(code: 'lcaChecker_grade', args: [letter])}. ${message(code: 'lcaChecker_grade_info')}."
                Set<Dataset> grossAreaDatasets = childEntity.datasets?.findAll({ surfaceDefinitionsShared.queryId.equals(it.queryId) && "surfaceDefinitionsShared".equals(it.questionId) })
                Dataset areaDataset = grossAreaDatasets?.find({ "surfaceGIFA".equals(it.resourceId) && it.quantity != null }) ?: grossAreaDatasets.find({ "surfaceGFA-sqft".equals(it.resourceId)  && it.quantity != null }) ?: grossAreaDatasets.find({ it.quantity != null })
                String area = areaDataset?.quantity?.toString() ?: "[undefined area]"
                String areaUnit = areaDataset?.userGivenUnit ?: "[undefined unit]"

                def locMap = scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getLocalizedMap(childEntity?.scope)

                bodyText = "${g.message(code: 'lcaChecker_body', args: [area, areaUnit, locMap.projectTypeLocalized, locMap.frameTypeLocalized, locMap.projectScopeLocalized])}"

            } else {
                String queryLink = "${g.link(controller: 'query', action: 'form', params: [entityId: parentEntity.id.toString(), childEntityId: childEntity.id.toString(), indicatorId: currentIndicatorId, queryId: surfaceDefinitionsShared.queryId]) { surfaceDefinitionsShared.localizedName }}"
                link = "${message(code: 'lcaChecker_missingData')} ${queryLink}."
                outputmap.put("missingData", true)
            }
        }
        outputmap.put("link", link)


        //***************************************************************************
        //Scope Checker

        //String resultCategoryParam = indicator.resultCategory

        Boolean isCompatible
        String recommendWarning
        String scopeMessageWarning
        String successScopeCompleted
        String requiredWarning
        String scopeId = childEntity?.scope?.scopeId
        String scopePercentage = '-'
        PredefinedScope predefineScope = scopeId ? scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.findByScopeId(scopeId) : null
        List<PredefinedScope> scopesAvailableForIndicator = scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getScopeValues([currentIndicator])?.allowedScopes
        if (!scopeId) {
            scopeMessageWarning = g.message(code: 'scope.noScopeSelected')
        } else if (!scopesAvailableForIndicator && currentIndicator) {
            scopeMessageWarning = g.message(code: 'scope.noScopeAvailable', args: [indicatorService.getLocalizedName(currentIndicator)])
        }

        if (childEntity && childEntity.scope && currentIndicator && predefineScope) {
            try {
                isCompatible = scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.isIndicatorCompatible(currentIndicator, predefineScope)
                if (isCompatible) {
                    def locMap = scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getLocalizedWarningMap(childEntity, predefineScope)
                    recommendWarning = locMap.recommendedWarning
                    requiredWarning = locMap.requiredWarning
                    scopePercentage = scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getScopePercentage(childEntity, predefineScope)
                    if (scopePercentage.equalsIgnoreCase("100%")) {
                        successScopeCompleted = g.message(code: "scope.completed", args: [scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getLocalizedScope(childEntity.scope)])
                    }

                } else {
                    scopeMessageWarning = g.message(code: "scope.incompatibleIndicator", args: [scopeFactoryService.getServiceImpl(childEntity?.entityClass)?.getLocalizedScope(childEntity.scope), indicatorService.getLocalizedName(currentIndicator)])
                }
            } catch (Exception e) {
                loggerUtil.error(log, "SCOPE CHECKER FAIL MISERABLY", e)
                flashService.setErrorAlert("SCOPE CHECKER FAIL MISERABLY ${e.message}", true)
            }

        }
        heading = g.message(code: 'scope.scopeSection', args: [scopePercentage, letterGrade])
        outputmap.put("heading", heading)
        String scopeTemplateAsString = g.render(template: "/entity/scopeCheckerTemplate", model: [isCompatible    : isCompatible, successScopeCompleted: successScopeCompleted,
                                                                                                  scope           : childEntity?.scope,
                                                                                                  indicator       : currentIndicator,
                                                                                                  recommendWarning: recommendWarning,
                                                                                                  requiredWarning : requiredWarning, scopeMessageWarning: scopeMessageWarning]).toString()

        //**********************************************************************************************************************************************

        String templateAsString = ""
        if (lcaCheckerAllowed) {
            templateAsString = g.render(template: "/entity/lcaCheckerTemplate", model: [percentage      : percentage, checksFailed: checksFailed, hasAllRequiredResources: hasAllRequiredResources, queryLink: link,
                                                                                        checksPassed    : checksPassed?.sort({ -it.key.points }), color: color, entityId: entityId, internalUser: internalUser,
                                                                                        checksAcceptable: checksAcceptable?.sort({ -it.key.points }), letter: letter, indicatorId: indicatorId,
                                                                                        headingText     : headingText, gradingText: gradingText, bodyText: bodyText, checksNotApplicable: checksNotApplicable, suppressedChecks: suppressedChecks,
                                                                                        freeVersion     : freeVersion, modifiable: modifiable, userReadOnly: userReadOnly, user: user]).toString()
        }
        outputmap.put("template", templateAsString)
        outputmap.put("scopeTemplate", scopeTemplateAsString)
        render([output: outputmap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def resultsPieGraph() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String calculationRuleId = params.calculationRuleId
        def color
        def highColor
        List<String> colors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        List<String> highLightColors = ['rgba(127,196,204,0.1)', 'rgba(247,119,0,0.1)', 'rgba(119, 213, 109, 0.1)', 'rgba(115,49,70,0.1)', 'rgba(0,128,0,0.1)', 'rgba(243,153,131,0.1)', 'rgba(144, 127, 204, 0.1)', 'rgba(58, 82, 156, 0.1)', 'rgba(255, 204, 0, 0.1)', 'rgba(67,67,72,0.1)', 'rgba(128, 122,0,0.1)', 'rgba(219,28,17,0.1)']
        Entity childEntity = entityService.readEntity(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule calculationRule = indicator?.getGraphCalculationRuleObjects(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(calculationRuleId) })
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        List<CalculationResult> resultsByIndicator = childEntity?.getCalculationResultObjects(indicatorId, null, null)
        String datasets = "["
        Boolean draw = Boolean.FALSE
        int colorIndex = 0
        String calculationRuleName = ""
        String labels = "["
        String backgroundColors = "["
        String hoverColors = "["

        if (childEntity && calculationRule && resultCategories && indicatorId && resultsByIndicator) {
            calculationRuleName = calculationRule.localizedName ?: ""
            calculationRuleName = entityService.putNewLine(calculationRuleName, 30)

            //if (calculationRuleName) {calculationRuleName = abbr(value: calculationRuleName, maxLength: 40)}

            Double totalScore = childEntity.getTotalResult(indicatorId, calculationRule.calculationRuleId)
            List<String> categoriesWithScores = []
            int scoreIndex = 0
            resultCategories.each { ResultCategory resultCategory ->
                Double score = resultsByIndicator.find({
                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRule.calculationRuleId.equals(it.calculationRuleId)
                })?.result

                if (score && score > 0 && totalScore) {
                    score = Math.round((score / totalScore * 100))
                    int roundedScore = score as Integer
                    if (roundedScore) {
                        String localizedShortName = resultCategoryService.getLocalizedShortName(resultCategory, 15)
                        labels = "${labels} ${scoreIndex > 0 ? ", " : ""}\"${resultCategory.resultCategory} ${localizedShortName} - ${roundedScore.toString() + " %"}\""

                        categoriesWithScores.add(resultCategory.resultCategoryId)

                        if (colorIndex + 1 > colors.size()) {
                            colorIndex = 0
                        }
                        color = colors.get(colorIndex)
                        highColor = highLightColors.get(colorIndex)
                        hoverColors = "${hoverColors}${scoreIndex > 0 ? ',' : ''}\"${highColor}\""
                        backgroundColors = "${backgroundColors}${scoreIndex > 0 ? ',' : ''}\"${color}\""
                        datasets = "${datasets}${scoreIndex > 0 ? ',' : ''}${roundedScore}"
                        colorIndex++
                        scoreIndex++
                    }

                }
            }
            if (categoriesWithScores && !categoriesWithScores.isEmpty()) {
                draw = Boolean.TRUE
            }

        }
        backgroundColors = "${backgroundColors}]"
        hoverColors = "${hoverColors}]"
        labels = "${labels}]"
        datasets = "${datasets}]"
        String templateAsString = g.render(template: "/entity/resultsPieGraph", model: [datasets: datasets, calculationRuleName: calculationRuleName, indicator: indicator, draw: draw, childEntity: childEntity, labels: labels, hoverColors: hoverColors, backgroundColors: backgroundColors]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def loadTasks() {
        String entityId = params.entityId

        if (entityId) {
            Entity entity = entityService.getEntityForShowing(entityId)
            List<Task> tasks
            List<Task> completedTasks
            List<Task> allEntityTasks = entity?.tasks

            if (allEntityTasks) {
                tasks = allEntityTasks.findAll({ it != null && !it.completed })
                completedTasks = allEntityTasks.findAll({ it != null && it.completed })
            }
            render(template: "tasks", model: [entity: entity, tasks: tasks, completedTasks: completedTasks])
        }
    }

    def loadNotes() {
        String entityId = params.entityId

        if (entityId) {
            Entity entity = entityService.getEntityForShowing(entityId)
            render(template: "notes", model: [entity: entity])
        }
    }

    private DecimalFormat getDecimalFormatForDifference() {
        DecimalFormat decimalFormat
        User user = userService.getCurrentUser()

        if (user) {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(userService.getUserLocale(user?.localeString))
        } else {
            decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("fi"))
        }
        decimalFormat.applyPattern('###.#')
        return decimalFormat
    }

    def showDifference() {
        def selectedChildId = params.id
        def parentEntityId = params.entityId
        String indicatorId = params.indicatorId
        def childType = params.type
        Entity parent = entityService.getEntityById(parentEntityId, session)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule calculationRule = indicator.getResolveCalculationRules(parent)?.find({ it.calculationRuleId })
        String calculationRuleId = calculationRule?.calculationRuleId
        Map resultsToCompare = [:]
        Double totalResultToCompare
        Map differences = [:]
        Map totalDifferences = [:]
        Entity selectedChild
        Map denominatorDifferences
        DecimalFormat decimalFormat = getDecimalFormatForDifference()

        if (selectedChildId && parent && indicator && calculationRuleId && childType) {
            selectedChild = entityService.getEntityById(selectedChildId, session)
            List<ResultCategory> resultCategories = indicator?.getResolveResultCategories(parent)
            List<CalculationResult> resultsByIndicatorId = selectedChild.getCalculationResultObjects(indicatorId, null, null)

            resultCategories.each { ResultCategory category ->

                Double result = resultsByIndicatorId.find({
                    category.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                })?.result

                if (result) {
                    resultsToCompare.put(category, result)
                }
                totalResultToCompare = selectedChild?.getTotalResult(indicatorId, calculationRuleId)
            }

            if (resultsToCompare) {
                List<Entity> children
                List<Entity> otherChilds

                if ("operating".equals(childType)) {
                    children = parent?.getOperatingPeriods()
                } else {
                    children = parent?.getDesigns()
                }

                if (children) {
                    otherChilds = children?.findAll({ !it.id.equals(selectedChild.id) })
                }

                Map<String, List<CalculationResult>> allResultsForIndicator = entityService.getCalculationResultsObjects(otherChilds, indicatorId, null, null)

                if (otherChilds) {
                    otherChilds.each { Entity child ->
                        Map resultDifferences = [:]
                        List<CalculationResult> resultsByindicatorForOtherChilds = allResultsForIndicator.get(child?.id?.toString())

                        if (resultsByindicatorForOtherChilds) {
                            resultsToCompare.keySet().each { ResultCategory resultCategory ->
                                Double resultOther = resultsByindicatorForOtherChilds.find({
                                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && calculationRuleId.equals(it.calculationRuleId)
                                })?.result
                                if (resultOther) {
                                    Double original = resultsToCompare.get(resultCategory)?.toDouble()
                                    // ( | V1 - V2 | / ((V1 + V2)/2) ) * 100
                                    Double difference = calculateDifferenceUtil.calculateDifference(original, resultOther)
                                    //((original - result) / ((original + result) / 2)) * 100

                                    if (difference != null) {
                                        if (difference <= 0) {
                                            def formattedDifference = decimalFormat.format(difference)
                                            resultDifferences.put(resultCategory, "<span style=\"color: green;\">(${formattedDifference} %)</span>")
                                        } else {
                                            def formattedDifference = decimalFormat.format(difference)
                                            resultDifferences.put(resultCategory, "<span style=\"color: red;\">(+${formattedDifference} %)</span>")
                                        }
                                    }
                                }
                            }
                        }
                        if (!resultDifferences.isEmpty()) {
                            differences.put(child.id, resultDifferences)
                        }
                        def total = child?.getTotalResult(indicatorId, calculationRuleId)

                        if (total && totalResultToCompare) {
                            def difference = calculateDifferenceUtil.calculateDifference(totalResultToCompare, total)
                            def formattedDifference

                            if (difference != null) {
                                formattedDifference = decimalFormat.format(difference)

                                if (difference <= 0) {
                                    totalDifferences.put(child.id, "<span style=\"color: green;\">(${formattedDifference} %)</span>")
                                } else {
                                    totalDifferences.put(child.id, "<span style=\"color: red;\">(+${formattedDifference} %)</span>")
                                }
                            }
                        }
                    }
                    denominatorDifferences = getDenominatorDifferences(indicator, children, selectedChild)
                }
            }
        }
        chain action: "compare", params: [entityId: parentEntityId, type: childType],
                model: [differences        : [(indicatorId): differences], totalDifferences: [(indicatorId): totalDifferences],
                        benchmark          : selectedChild, dynamicDifferences: denominatorDifferences?.get("dynamicDifferences"),
                        compoundDifferences: denominatorDifferences?.get("compoundDifferences")]
    }


    private Map getDenominatorDifferences(Indicator indicator, List<Entity> entities, Entity entityToCompare) {
        CalculationRule calculationRule = indicator?.compareCalculationRuleObject
        Map dynamicDifferences = [:]
        Map compoundDifferences = [:]
        DecimalFormat decimalFormat = getDecimalFormatForDifference()

        if (indicator && entities && calculationRule) {
            def compoundAnswers = [:]
            def dynamicAnswers = [:]
            def datasets

            for (Entity entity in entities) {
                def totalScore = entity.getTotalResult(indicator?.indicatorId, calculationRule?.calculationRuleId)

                if (totalScore) {
                    Double total = totalScore.toDouble()
                    List<Denominator> denominatorList = indicator?.getNonDynamicDenominatorList()

                    if (denominatorList) {
                        denominatorList.each { Denominator denominator ->
                            datasets = denominatorUtil.getDatasetsForDenominator(denominator, entity)
                            def compoundKey
                            def compoundMultipliers
                            def calculatedValue
                            Double compoundAnswer

                            if (datasets) {
                                ResourceCache resourceCache
                                datasets.each { String queryId, List<Dataset> dsets ->
                                    resourceCache = ResourceCache.init(dsets)
                                    for (Dataset dataset: dsets) {
                                        if (!denominator.name) {
                                            Query query = queryService.getQueryByQueryId(queryId, true)
                                            List<Question> questions = query?.getAllQuestions()
                                            Resource resource = resourceCache.getResource(dataset)

                                            if (resource) {
                                                compoundKey = compoundKey ? compoundKey + " * " +
                                                        optimiResourceService.getLocalizedName(resource): optimiResourceService.getLocalizedName(resource)
                                            } else {
                                                Question question = questions?.find({
                                                    it.questionId.equals(dataset.questionId)
                                                })
                                                compoundKey = compoundKey ? compoundKey + " * " + question.localizedQuestion : question.localizedQuestion

                                            }
                                            compoundMultipliers = compoundMultipliers ? compoundMultipliers + " * " + dataset.answerIds[0] : "" + dataset.answerIds[0]
                                        }

                                        if (dataset.quantity) {
                                            compoundAnswer = compoundAnswer ? compoundAnswer * dataset.quantity : dataset.quantity
                                        }
                                    }
                                }
                            }

                            if (((compoundKey && compoundMultipliers) || denominator.name) && compoundAnswer > 0) {
                                calculatedValue = total / compoundAnswer

                                if (denominator.resultManipulator) {
                                    Double multiplier = denominatorUtil.getMultiplierByDenominator(entity, denominator)

                                    if (multiplier != null) {
                                        calculatedValue = calculatedValue * multiplier
                                    } else {
                                        calculatedValue = null
                                    }
                                    Double divider = denominatorUtil.getDividerByDenominator(entity, denominator)

                                    if (divider != null && calculatedValue != null) {
                                        calculatedValue = calculatedValue / divider
                                    } else {
                                        calculatedValue = null
                                    }
                                }

                                if (denominator.name) {
                                    compoundKey = denominator.localizedName
                                    compoundMultipliers = ""
                                }

                                if (compoundKey && compoundMultipliers != null && calculatedValue) {
                                    Map otherEntities = compoundAnswers.get(compoundKey)

                                    if (otherEntities) {
                                        otherEntities.put(entity, calculatedValue)
                                    } else {
                                        otherEntities = [(entity): calculatedValue]
                                    }
                                    compoundAnswers.put(compoundKey, otherEntities)
                                }
                            }
                        }
                    }
                    denominatorList = indicator.getDynamicDenominatorList()

                    if (denominatorList) {
                        denominatorList.each { Denominator denominator ->
                            datasets = denominatorUtil.getDatasetsForDenominator(denominator, entity)

                            if (datasets) {
                                datasets.each { key, value ->
                                    ResourceCache resourceCache = ResourceCache.init(value)
                                    for (Dataset dataset: value) {
                                        if (dataset.quantity) {
                                            Resource resource = resourceCache.getResource(dataset)
                                            Double denominatorAnswer = total / dataset.quantity

                                            if (denominatorAnswer != null) {
                                                Map existing
                                                def denominKey

                                                if (resource) {
                                                    denominKey = optimiResourceService.getLocalizedName(resource)
                                                } else {
                                                    Question question = questionService.getQuestion(dataset.queryId, dataset.questionId)

                                                    if (question) {
                                                        denominKey = question?.localizedQuestion
                                                    }
                                                }

                                                if (denominKey) {
                                                    existing = dynamicAnswers.get(denominKey)

                                                    if (existing) {
                                                        existing.put(entity, denominatorAnswer)
                                                    } else {
                                                        existing = [(entity): denominatorAnswer]
                                                    }
                                                    dynamicAnswers.put(denominKey, existing)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (dynamicAnswers) {
                dynamicAnswers.each { String key, Map<Entity, Double> answers ->
                    Double originalAnswer = answers.get(entityToCompare)

                    if (originalAnswer) {
                        answers.each { Entity entity, Double answer ->
                            if (!entity.id.equals(entityToCompare.id)) {
                                Double difference = calculateDifferenceUtil.calculateDifference(originalAnswer, answer)
                                def formattedDifference

                                if (difference != null) {
                                    formattedDifference = decimalFormat.format(difference)
                                    Map existing = dynamicDifferences.get(key)
                                    String differenceToOriginal

                                    if (difference <= 0) {
                                        differenceToOriginal = "<span style=\"color: green;\">(${formattedDifference} %)</span>"
                                    } else {
                                        differenceToOriginal = "<span style=\"color: red;\">(+${formattedDifference} %)</span>"
                                    }

                                    if (existing) {
                                        existing.put(entity.id, differenceToOriginal)
                                    } else {
                                        existing = [(entity.id): differenceToOriginal]
                                    }
                                    dynamicDifferences.put(key, existing)
                                }
                            }
                        }
                    }
                }
            }

            if (compoundAnswers) {
                compoundAnswers.each { String key, Map<Entity, Double> answers ->
                    Double originalAnswer = answers.get(entityToCompare)

                    if (originalAnswer) {
                        answers.each { Entity entity, Double answer ->
                            if (!entity.id.equals(entityToCompare.id)) {
                                Double difference = calculateDifferenceUtil.calculateDifference(originalAnswer, answer)
                                def formattedDifference

                                if (difference) {
                                    formattedDifference = decimalFormat.format(difference)
                                    Map existing = compoundDifferences.get(key)
                                    String differenceToOriginal

                                    if (difference < 0) {
                                        differenceToOriginal = "<span style=\"color: green;\">(${formattedDifference} %)</span>"
                                    } else {
                                        differenceToOriginal = "<span style=\"color: red;\">(+${formattedDifference} %)</span>"
                                    }

                                    if (existing) {
                                        existing.put(entity.id, differenceToOriginal)
                                    } else {
                                        existing = [(entity.id): differenceToOriginal]
                                    }
                                    compoundDifferences.put(key, existing)
                                }
                            }
                        }
                    }
                }
            }
        }
        return [dynamicDifferences: dynamicDifferences, compoundDifferences: compoundDifferences]
    }

    // Deprecate code, to be removed by the end of feature 0.5.0 release
    /*def compare() {
        Entity entity
        def childType = params.type
        def indicators
        def validLicenses
        Map<String, List<Entity>> childEntities = [:]
        def childEntitiesGeneral
        List<License> nonProdLicenses = []

        if (params.entityId) {
            entity = entityService.getEntityByIdReadOnly(params.entityId)
        } else {
            entity = entityService.getEntityByIdReadOnly(params.id)
        }

        if (entity && childType) {
            if ("operating".equals(childType)) {
                validLicenses = entity.validOperatingLicenses

                if (validLicenses) {
                    indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.OPERATING.toString())
                    childEntitiesGeneral = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())
                    indicators?.each { Indicator indi ->
                        childEntities.put(indi.indicatorId, childEntitiesGeneral.findAll({ Entity design -> !design.disabledIndicators?.contains(indi?.indicatorId) }))
                    }
                }
            } else {
                validLicenses = entity.validDesignLicenses

                if (validLicenses) {
                    indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, IndicatorUse.DESIGN.toString())
                    childEntitiesGeneral = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())
                    indicators?.each { Indicator indi ->
                        childEntities.put(indi.indicatorId, childEntitiesGeneral.findAll({ Entity design -> !design.disabledIndicators?.contains(indi?.indicatorId) }))
                    }
                }
            }
        }

        if (indicators) {
            for (Indicator indicator in indicators) {
                def licenses = entity?.getLicensesForIndicator(indicator)

                String prodType = Constants.LicenseType.PRODUCTION.toString()
                String demoType = Constants.LicenseType.DEMO.toString()
                nonProdLicenses = licenses?.findAll { it.type != prodType && it.type != demoType } ?: []
            }
        }

        Map<Boolean, List<Indicator>> indicatorByIsAdmin = indicators?.groupBy { "benchmark".equalsIgnoreCase(it.visibilityStatus) }

        User user = userService.getCurrentUser(true)
        Boolean isAdmin = userService.isSystemAdmin(user)

        return [entity            : entity, childEntities: childEntities, childType: childType,
                showPoweredBy     : true, nonProdLicenses: nonProdLicenses, compareView: true,
                indicatorByIsAdmin: indicatorByIsAdmin, isAdmin: isAdmin]
    }*/


    def form() {
        Entity entity
        boolean modifyAllowed = true
        String licenseId = params.licenseId
        String licenseName
        def entityId = params.id ? params.id : params.entityId
        String automaticTrial = params.automaticTrial
        boolean isPlanetary = params.planetaryEntity ? true : false

        if (session?.showLoginPrompt) {
            session?.removeAttribute("showLoginPrompt")
            session?.setAttribute("noPrompt", "true")
        }

        if (params."new") {
            entity = null
        } else {
            entity = entityService.getEntityById(entityId, session)

            if (entity && !entity.modifiable) {
                modifyAllowed = false
            }
        }
        if (licenseId && !licenseName) {
            licenseName = licenseService.getLicenseById(licenseId).name
        }

        if (modifyAllowed) {
            def entityClass

            if (entity?.entityClass) {
                entityClass = entity.entityClass
            } else if (params.entityClass) {
                entityClass = params.entityClass
            } else {
                entityClass = EntityClass.BUILDING.toString()
            }
            Resource entityClassResource = optimiResourceService.getResourceWithParams(entityClass)
            Query basicQuery = queryService.getBasicQuery()
            def showMandatory = !basicQuery?.disableHighlight
            def datasets = datasetService.getDatasetsByEntityAndQueryId(entity, basicQuery?.queryId)
            List<License> entityValidLicenses = licenseService.getValidLicensesForEntity(entity)
            List<License> licenses = []

            if (!entityValidLicenses || !entityValidLicenses?.find({ it.planetary })) {
                licenses = licenseService.getUsableAndManagedLicenses2(entityClass, entityValidLicenses ? true : false)
            }
            List<String> statesWithPlanetary = licenseService.getStatesWithPlanetary()
            Map<String, List<Document>> additionalQuestionResources = questionService.getAdditionalQuestionResourceDocumentsByQuestionIdMap(basicQuery?.allQuestions as Set<Question>, basicQuery?.queryId)

            if (entity) {
                [entity          : entity, basicQuery: basicQuery, datasets: datasets, showMandatory: showMandatory, statesWithPlanetary: statesWithPlanetary,
                 licenses        : licenses, entityClassResource: entityClassResource, modifiable: true, entityClass: entityClass, isPlanetary: isPlanetary,
                 autocaseDesignId: entity.autocaseDesignId, autocaseProjectId: entity.autocaseProjectId, entityValidLicenses: entityValidLicenses, additionalQuestionResources: additionalQuestionResources]
            } else {
                [basicQuery         : basicQuery, datasets: datasets, showMandatory: showMandatory, licenses: licenses, isPlanetary: isPlanetary, statesWithPlanetary: statesWithPlanetary,
                 entityClassResource: entityClassResource, modifiable: true, licenseId: licenseId, licenseName: licenseName, automaticTrial: automaticTrial,
                 autocaseDesignId   : params.autocaseDesignId, autocaseProjectId: params.autocaseProjectId, address: params.address, buildingType: params.buildingType, state: params.state, country: params.country,
                 projectName        : params.projectName, user: user, additionalQuestionResources: additionalQuestionResources, entityClass: entityClass]
            }
        } else {
            if (entityId) {
                redirect action: "show", params: [entityId: entityId]
            } else {
                redirect controller: "main"
            }
        }
    }

    def saveInternal() {
        Query basicQuery = queryService.getBasicQuery()
        // moved logic to service. Check commit of this line for original code
        Entity entity = entityService.createOrGetProjectEntity(params, session, request, basicQuery)

        if (entity.validate()) {
            boolean isQueryReady = queryService.resolveBasicQueryReadiness(entity)

            if (params.upload) {
                redirect action: "form", id: entity.id
            } else if (basicQuery && !isQueryReady && !entity?.isPortfolio) {
                flash.fadeWarningAlert = g.message(code: 'query.not_ready')
                redirect action: "form", id: entity.id
            } else {
                redirect action: "show", params: [entityId: entity.id]
            }

        } else {
            String entityClass = params.entityClass ?: EntityClass.BUILDING.getType()
            chain action: "form", model: [entity: entity], params: [entityClass: entityClass]
        }
    }

    def cancel() {
        if (params.entityId) {
            redirect action: "show", id: params.entityId
        } else {
            redirect controller: "main", action: "list"
        }
    }

    def saveIndicators() {
        String entityId = params.entityId
        Boolean newDesign = params.newDesign
        Boolean newOperatingPeriod = params.newOperatingPeriod
        String newDesignName = params.name
        String newDesignComment = params.comment
        Integer ribaStage = params.int("ribaStage")
        List<String> indicatorIds
        List<String> enabledIndicatorIds = params.list('enabledIndicatorIdsForDesign')
        Boolean projectLevelParameters = Boolean.FALSE
        Boolean lcaParamHandling = params.boolean('lcaParamHandling')
        if (params.indicatorIds) {
            indicatorIds = params.list("indicatorIds")
        }

        if (indicatorIds) {
            String indicatorUse = params.indicatorUse
            Entity entity = entityService.addIndicatorIdsForEntity(entityId, indicatorIds, indicatorUse, session)

            String firstQueryId = null

            if (!newDesign && !newOperatingPeriod) {
                List<Indicator> indicatorsChosen = []
                if (indicatorIds && entity?.indicators) {
                    entity.indicators.each { Indicator indicator ->
                        if (indicatorIds.contains(indicator.indicatorId)) {
                            indicatorsChosen.add(indicator)
                        }
                    }
                }
                if (indicatorsChosen && indicatorsChosen.find({ it.indicatorQueries?.find({ it.projectLevel }) })) {
                    projectLevelParameters = Boolean.TRUE
                }

                if (projectLevelParameters && entityId) {
                    firstQueryId = entityService.determineFirstQueryId(entity, indicatorsChosen)
                }
                /* SW-1645 keep the commented code until after release 0.6.0
                List<String> projectLevelQueries = []
                Map<String, List<String>> queryAndLinkedIndicators = [:]

                if (projectLevelParameters && entityId) {
                    indicatorsChosen.each { Indicator indicator ->
                        indicator.getIndicatorQueries()?.each {
                            if (it.projectLevel) {
                                Query q = queryService.getQueryByQueryId(it.queryId)

                                if (q) {
                                    def existing = queryAndLinkedIndicators.get(q.queryId)

                                    if (existing) {
                                        existing.add(indicator.indicatorId)
                                    } else {
                                        existing = [indicator.indicatorId]
                                    }
                                    queryAndLinkedIndicators.put(q.queryId, existing)

                                    if (!projectLevelQueries.contains(q.queryId)) {
                                        projectLevelQueries.add(q.queryId)
                                    }
                                }
                            }
                        }
                    }
                    entity = entityService.updateProjectLevelEntity(projectLevelQueries, entity, queryAndLinkedIndicators)
                } else {
                    entity = entityService.updateProjectLevelEntity(projectLevelQueries, entity, queryAndLinkedIndicators)
                }
                */
            }

            if (newDesign) {
                String scopeId = params.scopeId
                String frameType = params.frameType
                String projectType = params.projectType
                List<String> lcaCheckerList = params.list("lcaCheckerList")

                redirect controller: "design", action: "newDesign", params: [indicatorIds    : indicatorIds, enabledIndicatorIds: enabledIndicatorIds,
                                                                             entityId        : entityId, newDesignName: newDesignName,
                                                                             newDesignComment: newDesignComment, ribaStage: ribaStage,
                                                                             scopeId         : scopeId, frameType: frameType, projectType: projectType,
                                                                             lcaCheckerList  : lcaCheckerList, lcaParamHandling: lcaParamHandling]
            } else if (newOperatingPeriod) {
                redirect controller: "operatingPeriod", action: "newOperatingPeriod", params: [indicatorIds: indicatorIds, entityId: entityId]
            } else {
                /* SW-1645 keep the commented code until after release 0.6.0
                String firstQueryId

                if (entity?.projectLevelQueries) {
                    if (entity.queryReady) {
                        for (String queryId in entity.projectLevelQueries) {
                            if (!entity.queryReady.get(queryId)) {
                                firstQueryId = queryId
                                break
                            }
                        }
                    } else {
                        firstQueryId = entity.projectLevelQueries.first()
                    }
                }
                */

                if (firstQueryId) {
                    redirect controller: "query", action: "form", params: [entityId: entityId, queryId: firstQueryId, projectLevel: true, designLevelParamQuery: true]
                } else {
                    redirect action: "show", id: entityId, params: [calculate: true]
                }
            }
        } else if (!indicatorIds) {
            flash.fadeErrorAlert = message(code: "entity.empty_indicators.not_allowed")
            redirect action: "addIndicators", params: [indicatorUse: params.indicatorUse, entityId: entityId]
        }

    }

    def addIndicators() {
        Entity entity = entityService.getEntityById(params.entityId, session)
        def indicators = []
        def allIndicators = []
        List<Indicator> notLicensedIndicators = []
        def indicatorUse = params.indicatorUse
        Query basicQuery = queryService.getBasicQuery()
        String certificationForEntity = ""


        if ("design".equals(indicatorUse)) {
            indicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.DESIGN.toString(), entity, true)
            allIndicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.DESIGN.toString(), entity, false)
        } else {
            indicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.OPERATING.toString(), entity, true)
            allIndicators = indicatorService.getIndicatorsByIndicatorUse(IndicatorUse.OPERATING.toString(), entity, false)
        }

        allIndicators?.each { Indicator indicator ->
            if (!indicators?.collect({ it.indicatorId })?.contains(indicator.indicatorId)) {
                notLicensedIndicators.add(indicator)
            }
        }
        if (entity && basicQuery) {
            def datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, basicQuery?.queryId, 'basicQuerySection', 'certification')
            if (datasets) {
                String resourceId = datasets.get(0).resourceId
                if (resourceId) {
                    certificationForEntity = resourceId
                }
            }
        }


        if (entity?.newToolsAvailable) {
            entity.newToolsAvailable = null
            entity = entity.merge(flush: true)
        }
        [entity: entity, indicators: indicators?.sort({ !it.certificationImageResourceId?.contains(certificationForEntity) }), notLicensedIndicators: notLicensedIndicators, indicatorUse: indicatorUse]
    }

    def users() {
        if (params.id) {
            Entity entity = entityService.getEntityByIdReadOnly(params.id)

            if (!entity?.manageable) {
                loggerUtil.warn(log, "User tried to manage entity without manager rights.")
                redirect controller: "index"
            } else {
                List<Indicator> indicators = []
                def selectedDesignIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.DESIGN.toString())

                if (selectedDesignIndicators) {
                    indicators.addAll(selectedDesignIndicators)
                }
                def selectedOperatingIndicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity?.id, IndicatorUse.OPERATING.toString())

                if (selectedOperatingIndicators) {
                    indicators.addAll(selectedOperatingIndicators)
                }
                Map locales = new TreeMap<String, String>()
                locales.put("en", "EN - English")
                locales.put("fi", "FI - Suomi")
                [entity: entity, userTypes: EntityUserType.values(), locales: locales, indicators: indicators]
            }
        } else {
            redirect controller: "index"
        }
    }

    def setUserIndicators() {
        if (params.entityId && params.userId) {
            entityService.setUserIndicators(params.entityId, params.userId, request.getParameterValues("indicatorId")?.toList())
        }
        redirect action: "users", id: params.entityId
    }

    def addUser() {
        if (params.id && params.email && params.userType) {
            Entity entity = entityService.getEntityById(params.id, session)
            def userType = params.userType
            String email = params.email
            def showEmail

            if (!entity || !entity.manageable) {
                flash.fadeErrorAlert = message(code: "entity.add_user.no_right")
                loggerUtil.warn(log, "User tried to manage entity without manager rights.")
                redirect controller: "index"
            } else {
                EmailValidator emailValidator = EmailValidator.getInstance()

                if (emailValidator.isValid(email)) {
                    List<License> licenses = licenseService.getValidLicensesForEntity(entity)

                    if (licenses) {
                        User user = entityService.addEntityUser(entity?.id, email, userType)

                        if (user && (licenses.find({ it.allowedForUser(user) }) || licenses.find({ it.userBased && it.licensedFeatures?.collect({ it.featureId })?.contains(Feature.ALLOW_ADDING_ANY_USER) }))) {
                            try {
                                sendUserAddedEmail(user, userType, entity, session)
                            } catch (Exception e) {
                                loggerUtil.warn(log, "Error in sending email: " + e)
                                flash.fadeErrorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                            }
                        } else if (!user && (!licenses.find({ it.userBased }) || (!EntityUserType.MANAGER.toString().equals(userType) && licenses.find({ it.userBased && it.licensedFeatures?.collect({ it.featureId })?.contains(Feature.ALLOW_ADDING_ANY_USER) })))) {
                            flash.errorAlert = message(code: "entity.add_user.no_user", args: [email])
                            session?.setAttribute("email", email)
                            session?.setAttribute("userRights", userType)
                            showEmail = true
                        } else {
                            flash.errorAlert = message(code: "entity.add_user.not_allowed")
                        }
                    } else if (entity.isPortfolio) {
                        User user = entityService.addEntityUser(entity?.id, email, userType)
                        if (user) {
                            try {
                                sendUserAddedEmail(user, userType, entity, session)
                            } catch (Exception e) {
                                loggerUtil.warn(log, "Error in sending email: " + e)
                                flash.fadeErrorAlert = message(code: "email.error", args: [emailConfiguration.supportEmail])
                            }
                        } else {
                            flash.errorAlert = message(code: "entity.add_user.no_user", args: [email])
                            session?.setAttribute("email", email)
                            session?.setAttribute("userRights", userType)
                            showEmail = true
                        }
                    } else {
                        flash.errorAlert = message(code: "licenseRequest.info")
                    }

                    if (showEmail) {
                        redirect action: "users", id: entity.id, params: ["showemail": "true"]
                    } else {
                        redirect action: "users", id: entity.id
                    }
                } else {
                    flash.errorAlert = g.message(code: "passwordReset.email_invalid_format")
                    redirect action: "users", id: entity.id
                }
            }
        } else {
            redirect controller: "index"
        }
    }

    def removePreProvisionedUser() {
        String id = params.id
        String email = params.email
        String uuid = params.uuid

        if (id && (email || uuid)) {
            entityService.removePreProvisionedUser(id, email, uuid)
        }
        redirect action: "users", id: id
    }

    private void sendUserAddedEmail(User user, String userType, Entity entity, HttpSession session) {
        if (user && userType && entity) {
            userType = message(code: userType.toLowerCase())
            String entityLink = generateEntityLink(entity, session, user?.language)
            String adder = userService.getCurrentUser(true)?.username
            Boolean activeCampaignOk = activeCampaignService.updateUserType(user, "commercial", session, null, user.organizationName, adder, entityLink)
            if (!activeCampaignOk) {
                log.info("ActiveCampaign sending failed user adding email, fallback to internal emailservice")
                flashService.setWarningAlert("ActiveCampaign sending failed user adding email, fallback to internal emailservice", true)
                sendAddesUserEmailFallback(user, userType, entity, session)
            }
        }
    }

    private void sendAddesUserEmailFallback(User user, String userType, Entity entity, HttpSession session) {
        if (user && userType && entity) {
            String currentUser = userService.getCurrentUser()?.name
            def emailTo = user.username
            def emailSubject = message(code: "entity.user_added_email_subject", args: [currentUser, entity.name], encodeAs: "Raw")
            def emailFrom = message(code: "email.support")
            def replyToAddress = message(code: "email.support")
            def emailBody = message(code: "entity_user_added.email_body", args: [
                    userType,
                    entity.name,
                    generateEntityLink(entity, session, user?.language)
            ])
            optimiMailService.sendMail({
                to emailTo
                from emailFrom
                replyTo replyToAddress
                subject emailSubject
                html emailBody
            }, emailTo)
        }
    }

    def sendRequestToRegister() {
        String email = session?.getAttribute("email")
        String userRights = session?.getAttribute("userRights")
        session?.removeAttribute("email")
        def entity = entityService.getEntityById(params.id, session)
        def userMessage = params.message
        def language = params.locale ? params.locale : "en"
        String registerLink = generateRegisterLink(language)
        User currentUser = userService.getCurrentUser()
        String adder = currentUser?.name
        String entityLink = generateEntityLink(entity, session, language)

        if (email && entity && registerLink) {
            //Boolean mailchimpOk = sendRequestToRegisterMailChimp(email, entity, registerLink, adder, userMessage, entityLink, userRights)
            Boolean activeCampaignOk = activeCampaignService.updateUserType(null, "commercial", session, email, currentUser?.organizationName, adder, entityLink, registerLink)

            if (!activeCampaignOk) {
                log.info("mailChimp sending failed user request to register email, fallback to internal emailservice")
                flashService.setWarningAlert("mailChimp sending failed user request to register email, fallback to internal emailservice", true)
                sendRequestToRegisterFallBack(entity, email, userMessage, registerLink, adder, language)
            }
        }
        redirect action: "users", id: params.id
    }

    def sendRequestToRegisterFallBack(Entity entity, String email, String userMessage, String registerLink, adder, language) {
        if (email && entity) {
            def emailTo = email.trim()
            def emailSubject = message(code: "entity.user_added_email_subject", args: [adder, entity.name], encodeAs: "Raw")
            def emailFrom = message(code: "email.support")
            def replyToAddress = message(code: "email.support")
            def emailBody = message(code: "entity.request_to_register_body",
                    args: [adder, entity.name, (userMessage ? userMessage : ""), registerLink], locale: new Locale(language))
            try {
                optimiMailService.sendMail({
                    to emailTo
                    from emailFrom
                    replyTo replyToAddress
                    subject emailSubject
                    html emailBody
                }, emailTo)
                flash.fadeSuccessAlert = message(code: "entity.request_to_register_sent")
            }
            catch (Exception e) {
                loggerUtil.warn(log, "Error in sending request to register: " + e)
                flash.fadeErrorAlert = message(code: "entity.request_to_register_failed")
            }
        }
    }

    private String generateEntityLink(Entity entity, HttpSession session, String language = 'en') {
        def channelToken = channelFeatureService.getChannelFeature(session)?.token
        return createLink(base: "$request.scheme://$request.serverName$request.contextPath",
                controller: "entity", action: "show", id: entity?.id, params: [channelToken: channelToken, lang: language])
    }

    def removeUser() {
        if (params.id && params.userId && params.userType) {
            Entity entity = entityService.getEntityById(params.id)

            if (entity) {
                if (!entity.manageable) {
                    loggerUtil.warn(log, "User tried to manage entity without manager rights.")
                    redirect controller: "index"
                } else {
                    Map<String, String> returnable = entityService.removeEntityUser(entity?.id, params.userId, params.userType)

                    if (returnable?.get("errorKey")) {
                        flash.fadeErrorAlert = message(code: returnable.get("errorKey"))
                    }

                    if (entity.manageable) {
                        redirect action: "users", id: entity.id
                    } else {
                        redirect controller: "index"
                    }
                }
            } else {
                flash.fadeErrorAlert = "No rights to perform this action"
                redirect controller: "index"
            }
        } else {
            redirect controller: "index"
        }
    }

    private String generateRegisterLink(String language) {
        Locale locale = localeResolverUtil.resolveLocale(session, request, response)
        String channelToken = channelFeatureService.getChannelFeature(session)?.token
        def lang = language ? language : locale?.language
        return createLink(base: "$request.scheme://$request.serverName$request.contextPath",
                controller: "index", action: "form", params: [lang: lang, channelToken: channelToken])
    }

    def remove() {
        if (params.id) {
            entityService.delete(params.id)
            flash.fadeSuccessAlert = message(code: "entity.deleted")
        }
        redirect controller: "main"
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def restoreDesigns() {
        List<String> childEntityIds = (request?.JSON.childEntityIds as ArrayList<String>) ?: []
        String parentEntityId = request?.JSON.parentEntityId

        try{
            Entity parentEntity = entityService.getEntityFromId(parentEntityId)

            if(parentEntity) {
                List<Entity> childList = entityService.getEntitiesFromIds(childEntityIds)

                parentEntity.childEntities.each{
                    if(it.entityId.toString() in childEntityIds){
                        it.deleted = Boolean.FALSE
                    }
                }

                childList?.each {
                    it.deleted = false
                    it.merge(failOnError: true)
                }

                parentEntity.merge(failOnError: true, flush: true)
            }
        }catch(Exception e){
            loggerUtil.error(log, "Problem saving deletedDesign in the design", e)
            flashService.setErrorAlert("Problem saving deletedDesign in the design: ${e.getMessage()}")
        } finally {
            render(status: 200, text: 'ok', model: [(flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    def archive() {
        if (params.id) {
            Boolean superArchive = params.superArchive ? params.boolean("superArchive") : false
            entityService.archive(params.id, superArchive)
            flash.fadeSuccessAlert = message(code: "entity.archived")
        }
        redirect controller: "main"
    }

    def unarchive() {
        User user = userService.getCurrentUser()
        if (params.id && user && userService.getSuperUser(user)) {
            entityService.unarchive(params.id)
            flash.fadeSuccessAlert = "Project unarchived!"
        } else {
            flash.fadeErrorAlert = "Unable to unarchive project."
        }
        redirect controller: "main"
    }

    private Map getDesignPortfolioModel(Portfolio portfolio) {
        if (params["new"]) {
            session?.removeAttribute(SessionAttribute.CHOSEN_INDICATORS.toString())
        }
        List<Entity> entities = portfolioService.getEntities(portfolio)
        def entityDesigns = [:]
        List<Indicator> indicators = portfolioService.getIndicators(portfolio?.indicatorIds)
        def showAllLink = false
        def allDesigns = []

        entities?.each { Entity entity ->
            if (!showAllLink && !entity.hasAnyIndicator(indicators)) {
                showAllLink = true
            }
            def designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())

            if (designs) {
                entityDesigns.put(entity, designs)
                allDesigns.addAll(designs)
            }
        }
        return [entityDesigns: entityDesigns, indicators: indicators, showAllLink: showAllLink, allDesigns: allDesigns]
    }

    private Map getOperatingPortfolioModel(Portfolio portfolio) {
        def entities = portfolioService.getEntities(portfolio)
        def chosenPeriods = portfolio?.chosenPeriods?.sort({ it.toInteger() })
        def entityPeriods = [:]
        def indicators = portfolioService.getIndicators(portfolio?.indicatorIds)
        def entitiesByOperatingPeriod = [:]
        def showAllByIndicators = [:]
        def allChildEntities = []


        if (chosenPeriods) {
            entities?.each { Entity entity ->
                indicators.each { Indicator indicator ->
                    if (!showAllByIndicators.get(indicator) && !entity.indicators?.contains(indicator)) {
                        showAllByIndicators.put(indicator, true)
                    }
                }
                List<Entity> childEntities = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())

                if (childEntities) {
                    def operatingPeriods = []

                    chosenPeriods.each { String period ->
                        List<Entity> found = childEntities.findAll({ period.equals(it.operatingPeriod) })
                        Entity resolved

                        if (found && found.size() > 0) {
                            if (found.size() > 1) {
                                resolved = found.find({ !it.name })
                            } else {
                                resolved = found.get(0)
                            }
                        }

                        if (resolved) {
                            operatingPeriods.add(resolved)
                        }
                    }

                    if (!operatingPeriods?.isEmpty()) {
                        allChildEntities.addAll(operatingPeriods)
                        def operatingPeriodByPeriod = [:]

                        operatingPeriods.each { Entity operatingPeriod ->
                            operatingPeriodByPeriod.put(operatingPeriod.operatingPeriod, operatingPeriod)
                            List<Entity> existing = entitiesByOperatingPeriod.get(operatingPeriod.operatingPeriod)

                            if (existing) {
                                existing.add(operatingPeriod)
                            } else {
                                existing = [operatingPeriod]
                            }
                            entitiesByOperatingPeriod.put(operatingPeriod.operatingPeriod, existing)
                        }
                        entityPeriods.put(entity.id, operatingPeriodByPeriod)
                    }
                }
            }
        } else if (!chosenPeriods && entities) {
            flash.errorAlert = message(code: "portfolio.no_periods")
        }
        return [portfolio : portfolio, entities: entities, operatingPeriods: chosenPeriods, entityPeriods: entityPeriods, allChildEntities: allChildEntities,
                indicators: indicators, entitiesByOperatingPeriod: entitiesByOperatingPeriod, showAllByIndicators: showAllByIndicators]
    }

    def addBenchmarks() {
        def entityId = params.entityId
        def portfolioIds = params.list("portfolioId")
        def type = params.type
        entityService.addBenchmarks(entityId, portfolioIds, type)
        redirect action: "show", params: [entityId: entityId]
    }

    def removeBenchmark() {
        def entityId = params.entityId
        def benchmarkId = params.id
        entityService.removeBenchmark(entityId, benchmarkId)
        redirect action: "show", params: [entityId: entityId]
    }

    // Deprecate code, to be removed by the end of feature 0.5.0 release
    /*def compoundReport() {
        def entityId = params.entityId
        Entity entity = entityService.getEntityById(entityId, session)

        if (entity) {
            def designs
            def operatingPeriods
            def indicators
            def appliedStylesheets = []
            def reportTitles = []
            indicators = entity.reportIndicators
            designs = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())
            operatingPeriods = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())
            indicators?.each { Indicator indicator ->
                def appliedStylesheet = indicator.report?.appliedStyleSheet

                if (appliedStylesheet) {
                    appliedStylesheets.add(appliedStylesheet)
                }
                String reportTitle = indicator.report?.localizedMainLevelButtonTitle

                if (reportTitle && !reportTitles.contains(reportTitle)) {
                    reportTitles.add(reportTitle)
                }
            }
            flash.warningAlert = message(code: "compoundReport.print_guide")
            [entity            : entity, indicators: indicators, designs: designs, operatingPeriods: operatingPeriods,
             appliedStylesheets: appliedStylesheets, reportTitles: reportTitles]
        } else {
            redirect controller: "main"
        }

    }*/

    def planetaryReport() {
        String designId = params.designId
        String parentId = params.parentId
        String indicatorId = params.indicatorId
        String calculationRuleId = params.calculationRuleId
        Entity design = entityService.getEntityById(designId, session)
        Entity parent = entityService.getEntityById(parentId, session)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        String reportDate = new Date().format("dd.MM.yyyy")
        List<Dataset> hardCodedDatasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(design, "surfaceDefinitionsShared", "surfaceDefinitionsShared", "surfaceDefinitionsShared")
        Dataset gifaDataset = hardCodedDatasets?.find({ ["surfaceGIFA", "surfaceGIFA_US_m2"].contains(it.resourceId) })
        Double GIFA = gifaDataset?.quantity
        String grossAreaWithUnit = gifaDataset?.userGivenUnit ?: gifaDataset?.unitForData ?: "m2"
        User user = userService.getCurrentUser()
        if(user){
            if (!GIFA) {
                GIFA = hardCodedDatasets?.find({ "surfaceGFA-sqft".equals(it.resourceId) })?.quantity

                if (GIFA) {
                    GIFA = unitConversionUtil.doConversion(GIFA, null, "sq ft", null, null, null, null, null, null, "m2")
                }
            }
            if (GIFA) {
                grossAreaWithUnit = "${GIFA} ${grossAreaWithUnit}"
            }

            Map<String,String> pdfGraphsForSession = user.pdfGraphsForSession
            Map<String,String> sessionStoredPdfGraphs = session?.getAttribute("pdfGraphsForSession")
            // add for when refreshing the page
            if(pdfGraphsForSession){
                session?.setAttribute("pdfGraphsForSession",pdfGraphsForSession)
            }else if(!pdfGraphsForSession && sessionStoredPdfGraphs){
                pdfGraphsForSession = sessionStoredPdfGraphs

            }
            user.pdfGraphsForSession = null
            userService.updateUserToSession(user)
            if (design && indicator) {
                [design: design, indicator: indicator, parent: parent, user: user , reportDate: reportDate, calculationRuleId: calculationRuleId, grossArea: grossAreaWithUnit,pdfGraphsForSession:pdfGraphsForSession]
            } else {
                return "error"
            }
        } else {
            return "error"
        }

    }

    def expandResults() {
        Entity entity
        Entity parent
        String resultCategoryParam = params.resultCategory
        if (params.id) {
            entity = entityService.getEntityById(params.id, session)
        } else {
            entity = entityService.getEntityById(params.entityId, session)
        }
        //annie
        boolean downloadSummary = params.downloadSummary
        String parentId = params.parentId
        if(parentId) {
            parent = entityService.getEntityById(parentId,session)
        }
        def indicatorId = params.indicatorId
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        IndicatorExpandFeature expandFeature = indicator?.resolveExpandFeature

        if (entity && indicator && (resultCategoryParam || expandFeature || downloadSummary)) {
            //Disabled for the moment because scope checker moved in result page
            String recommendWarning //= indicator.incompleteWarning?.getRecommendedWarning(entity, indicator)
            String requiredWarning //= indicator.incompleteWarning?.getRequiredWarning(entity, indicator)
            Boolean showScientificWarning = false
            ResultFormatting resultFormatting = resultFormattingResolver.resolveResultFormatting(indicator, null, session, null)

            if (resultFormatting?.useScientificNumbers || indicator.useScientificNumbers) {
                showScientificWarning = true
            }
            render view: "/entity/expandfeature", model: [hideNavi             : true,
                                                          entity               : entity,
                                                          indicator            : indicator,
                                                          downloadSummary      : downloadSummary,
                                                          errorMessageString   : requiredWarning,
                                                          parent               : parent,
                                                          recommendWarning     : recommendWarning,
                                                          showScientificWarning: showScientificWarning]
        } else {
            redirect controller: "main"
        }
    }

    def activateLicenseByKeyOrId() {
        Boolean licenseAddOk = Boolean.FALSE
        Boolean maxEntitiesReached = Boolean.FALSE
        User user = userService.getCurrentUser()

        if ((params.licenseKey || params.licenseId) && params.entityId) {
            License license

            if (params.licenseKey) {
                String licenseKey = params.licenseKey.trim()
                license = licenseService.getLicenseByKey(licenseKey)
                if (license) {
                    if (Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                        if (license.educationLicenseKeyValid) {
                            entityService.sendJoinCodeActivatedMail(license)
                        } else {
                            flash.fadeErrorAlert = message(code: "license.joinCode.invalid")
                            license = null
                        }
                    } else if (user && !license.allowedForUser(user)) {
                        flash.fadeErrorAlert = message(code: "license.joinCode.user_based")
                        license = null
                    }

                    if (license && license.compatibleEntityClasses && !license.compatibleEntityClasses.contains(params.entityClass)) {
                        flash.fadeErrorAlert = message(code: "licenses.activated_failed.wrongEntityClass")
                        license = null
                    }
                }
            } else {
                license = licenseService.getLicenseById(params.licenseId)
            }

            if (license && !license.maxEntitiesReached) {
                licenseService.addEntity(license.id, params.entityId)
                licenseAddOk = Boolean.TRUE
            }

            if (license?.maxEntitiesReached) {
                maxEntitiesReached = Boolean.TRUE
            }

        }

        if (licenseAddOk) {
            flash.fadeSuccessAlert = message(code: "licenses.activated_successful")
        } else if (maxEntitiesReached) {
            flash.fadeErrorAlert = message(code: "license.full")

        } else if (!flash.fadeErrorAlert) {
            flash.fadeErrorAlert = message(code: "licenses.activated_failed")
        }

        if (licenseAddOk && params.activatedTrial) {
            Entity entity = entityService.getEntityById(params.entityId)
            redirect controller: "design", action: "newDesign", params: [indicatorIds: entity?.indicatorIds, enabledIndicatorIds: entity?.indicatorIds, entityId: params.entityId]
        } else {
            redirect action: "show", params: [entityId: params.entityId]
        }

    }

    def removeImage() {
        String entityId = params.entityId
        if (entityId) {
            Entity entity = entityService.getEntityById(entityId)

            if (entity) {
                List<EntityFile> images = entityService.getEntityImages(entity.id, null)
                List<EntityFile> thumbs = entityService.getEntityImages(entity.id, Boolean.TRUE)

                if (images || thumbs) {
                    images?.each { EntityFile entityFile ->
                        entityFile.delete(flush: true)
                    }
                    thumbs?.each { EntityFile entityFile ->
                        entityFile.delete(flush: true)
                    }
                    entity.hasImage = Boolean.FALSE
                    entity.merge(flush: true)
                }
            }
        }
        redirect action: "form", params: [id: entityId]
    }

    def startFreeTrial() {
        Boolean licenseAddOk = Boolean.FALSE
        Boolean maxEntitiesReached = Boolean.FALSE
        User user = userService.getCurrentUser(true)
        String licenseName = ""
        License license = licenseService.getLicenseById(params.licenseId)
        Entity entity = entityService.getEntityById(params.entityId)
        String entityClass = entity?.entityClass
        //0014638
        if(!((user?.trialCount < 1&&("building".equalsIgnoreCase(entity?.entityClass))) || (user?.trialCountForInfra < 1&&("infrastructure".equalsIgnoreCase(entity?.entityClass))))){
            maxEntitiesReached = Boolean.TRUE
        }
        //0014638
        if (license && entity && user && (((user?.trialCount < 1&&("building".equalsIgnoreCase(entity?.entityClass))) || (user?.trialCountForInfra < 1&&("infrastructure".equalsIgnoreCase(entity?.entityClass)))) && license?.compatibleEntityClasses?.contains(entityClass) || license.freeForAllEntities)) {
           if (Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                if (license.educationLicenseKeyValid) {
                    entityService.sendJoinCodeActivatedMail(license)
                } else {
                    flash.fadeErrorAlert = message(code: "license.joinCode.invalid")
                    license = null
                }
            } else if (user && !license.allowedForUser(user)) {
                flash.fadeErrorAlert = message(code: "license.joinCode.user_based")
                license = null
            }


            if (license && !license.maxEntitiesReached) {
                boolean entityAdded = licenseService.addEntity(license.id, params.entityId)

                if (entityAdded) {
                    if (entityClass?.equalsIgnoreCase("building")) {
                        user.trialCount = user.trialCount ? user.trialCount += 1 : 1
                    } else if (entityClass?.equalsIgnoreCase("infrastructure")) {
                        user.trialCountForInfra = user.trialCountForInfra ? user.trialCountForInfra += 1 : 1

                    }
                    userService.updateUser(user)
                    licenseAddOk = Boolean.TRUE
                    licenseName = license.name
                }
            }

            if (license?.maxEntitiesReached) {
                maxEntitiesReached = Boolean.TRUE
            }
        }


        if (licenseAddOk) {
            flash.fadeSuccessAlert = "License activated successfully"
        } else if (maxEntitiesReached) {
            flash.fadeErrorAlert = message(code: "license.full")

        } else if (!flash.fadeErrorAlert) {
            flash.fadeErrorAlert = "Could not active license, check your license key"
        }

        if (licenseAddOk && entity?.indicatorIds) {
            redirect action: "show", params: [entityId: params.entityId]
        } else {
            redirect action: "show", params: [entityId: params.entityId]
        }
    }

    def enableAsUserTestData() {
        def entityId = params.entityId
        def childId = params.childId

        entityService.enableEntityAsTestDataset(childId)
        redirect action: "show", params: [entityId: entityId]
    }

    def disableAsUserData() {
        def entityId = params.entityId
        def childId = params.childId

        entityService.disableEntityAsTestDataset(childId)
        redirect action: "show", params: [entityId: entityId]
    }

    def addATestEntity() {
        def entityId = params.entityId
        def childId = params.entityId
        log.info("Adding a test entity: ${params.entityId} (${params.entityId})")
        String entityClass = params.entityClass
        if (entityId) {
            Entity parent = entityService.getEntityById(entityId, session)
            if (parent) {
                if (childId && entityClass) {
                    if (entityClass.equalsIgnoreCase("design")) {
                        def originalEntity = entityService.getEntityById(childId, session)
                        if (originalEntity) {
                            List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(parent.id, IndicatorUse.DESIGN.toString())
                            Entity design = new Entity()
                            design.entityClass = EntityClass.DESIGN.getType()
                            design.name = originalEntity.name
                            design.parentEntityId = parent.id
                            design.disabledIndicators = originalEntity.disabledIndicators
                            if (entityService.designAlreadyFound(design)) {
                                design.name += " ${formatDate(format: 'dd MMM hh:mm', date: (new Date()))}"
                            }
                            if (design.validate()) {
                                design = entityService.createEntity(design)
                                design = datasetService.copyDatasetsAndFiles(originalEntity, design)
                                indicators?.each { Indicator indicator ->
                                    design = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parent, design)
                                }
                                design = design.save(flush: true, failOnError: true)
                                flash.fadeSuccessAlert = "${message(code: 'successful_test_add')}"
                            } else {
                                flash.fadeErrorAlert = "${message(code: 'error_test_add')}"
                            }
                        }
                    } else if (entityClass.equalsIgnoreCase("period")) {
                        def originalEntity = entityService.getEntityById(childId, session)
                        if (originalEntity) {
                            List<Indicator> indicators = indicatorService.getIndicatorsByEntityAndIndicatorUse(parent.id, IndicatorUse.OPERATING.toString())
                            Entity period = new Entity()
                            period.entityClass = EntityClass.OPERATING_PERIOD.getType()
                            period.name = originalEntity.name
                            period.parentEntityId = DomainObjectUtil.stringToObjectId(entityId)
                            if (entityService.operatingPeriodAlreadyFound(period)) {
                                period.name += " ${formatDate(format: 'dd MMM hh:mm', date: (new Date()))}"
                            }
                            if (period.validate()) {
                                period = entityService.createEntity(period)
                                period = datasetService.copyDatasetsAndFiles(originalEntity, period)
                                indicators?.each { Indicator indicator ->
                                    period = newCalculationServiceProxy.calculate(null, indicator.indicatorId, parent, period)
                                }
                                period = period.save(flush: true, failOnError: true)
                                flash.fadeSuccessAlert = "${message(code: 'successful_test_add')}"
                            } else {
                                flash.fadeErrorAlert = "${message(code: 'error_test_add')}"
                            }
                        }
                    }
                }
            } else {
                redirect controller: "main", action: "list"
            }
            redirect action: "show", params: [entityId: entityId]
        } else {
            redirect controller: "main", action: "list"
        }

    }

    def structurePieChart() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        String carbonHeroIdFallback = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, "benchmarkingIndicatorId").value
        List<String> colors = ['rgba(127,196,204,1)', 'rgba(247,119,0,1)', 'rgba(119, 213, 109, 1)', 'rgba(115,49,70,1)', 'rgba(0,128,0,1)', 'rgba(243,153,131,1)', 'rgba(144, 127, 204, 1)', 'rgba(58, 82, 156, 1)', 'rgba(255, 204, 0, 1)', 'rgba(67,67,72,1)', 'rgba(128, 122,0,1)', 'rgba(219,28,17,1)']
        List<String> highLightColors = ['rgba(127,196,204,0.1)', 'rgba(247,119,0,0.1)', 'rgba(119, 213, 109, 0.1)', 'rgba(115,49,70,0.1)', 'rgba(0,128,0,0.1)', 'rgba(243,153,131,0.1)', 'rgba(144, 127, 204, 0.1)', 'rgba(58, 82, 156, 0.1)', 'rgba(255, 204, 0, 0.1)', 'rgba(67,67,72,0.1)', 'rgba(128, 122,0,0.1)', 'rgba(219,28,17,0.1)']
        Entity childEntity = entityService.readEntity(entityId)
        Entity parentEntity = childEntity?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        BenchmarkSettings benchmarkSettings = indicator?.benchmarkSettings
        if(!benchmarkSettings){
            indicator = indicatorService.getIndicatorByIndicatorId(carbonHeroIdFallback, true)
        }
        String gwpRuleId = benchmarkSettings?.displayRuleId ?: indicator.displayResult
        CalculationRule gwpRule = indicator?.getResolveCalculationRules(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(gwpRuleId) })
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        Map<String, Double> totalBySection = [:]
        String datasets = "["
        String labels = "["
        String hoverColors = "["
        String backgroundColors = "["
        Boolean draw = Boolean.FALSE
        int colorIndex = 0
        int scoreIndex = 0
        def color
        def highColor
        if (indicator && childEntity && resultCategories && gwpRule) {
            ResultCategory materialsImpact = resultCategories?.get(0)
            List<Query> indicatorQueries = indicator.getQueries(childEntity)
            List<CalculationResult> resultsForIndicator = childEntity.getCalculationResultObjects(indicator.indicatorId, gwpRule.calculationRuleId, materialsImpact.resultCategoryId)
            if (indicatorQueries) {
                indicatorQueries.each { Query query ->
                    query.getSections()?.each { QuerySection querySection ->
                        querySectionService.getAllUnderlyingQuestions(childEntity.entityClass, querySection)?.each { Question question ->
                            List<Dataset> foundDatasets = childEntity.
                                    getDatasetsForResultCategoryExpand(indicator.indicatorId, materialsImpact.resultCategoryId, resultsForIndicator)?.
                                    findAll({
                                        query.queryId.equals(it.queryId) && querySection.sectionId.equals(it.sectionId) && question.questionId.equals(it.questionId)
                                    })

                            if (foundDatasets) {
                                foundDatasets.each { Dataset dataset ->
                                    Double result = childEntity.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                            materialsImpact.resultCategoryId, gwpRule.calculationRuleId,
                                            resultsForIndicator)

                                    if (result) {
                                        Double existingValue = totalBySection.get(querySection.localizedName)
                                        if (!existingValue) {
                                            totalBySection.put(querySection.localizedName, result)
                                        } else {
                                            totalBySection.put(querySection.localizedName, result + existingValue)

                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                Double total = childEntity.getResult(indicator.indicatorId, gwpRule.calculationRuleId,
                        materialsImpact.resultCategoryId, resultsForIndicator)
                if (totalBySection && !totalBySection.isEmpty() && total) {
                    totalBySection.each { String key, Double value ->
                        Integer score = Math.round((value / total) * 100).intValue()


                        if (score != null && score > 0) {
                            labels = "${labels} ${scoreIndex > 0 ? ", " : ""}\"${key}\""

                            if (colorIndex + 1 > colors?.size()) {
                                colorIndex = 0
                            }
                            color = colors.get(colorIndex)
                            highColor = highLightColors.get(colorIndex)
                            hoverColors = "${hoverColors}${scoreIndex > 0 ? ',' : ''}\"${highColor}\""
                            backgroundColors = "${backgroundColors}${scoreIndex > 0 ? ',' : ''}\"${color}\""
                            datasets = "${datasets}${scoreIndex > 0 ? ',' : ''}${score}"
                            colorIndex++
                            scoreIndex++
                            draw = Boolean.TRUE

                        }

                    }
                }

            }


        }
        backgroundColors = "${backgroundColors}]"
        hoverColors = "${hoverColors}]"
        labels = "${labels}]"
        datasets = "${datasets}]"
        String templateAsString = g.render(template: "/entity/structuresPieChart", model: [datasets: datasets, indicator: indicator, draw: draw, labels: labels, hoverColors: hoverColors, backgroundColors: backgroundColors, childEntity: childEntity]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def entityPieGraphHotel() {
        String entityId = params.entityId
        String indicatorId = params.indicatorId
        def color
        def highColor
        List<String> colors = Constants.CHART_COLORS
        List<String> highLightColors = Constants.CHART_COLORS
        Entity design = entityService.readEntity(entityId)
        Entity parentEntity = design?.parentById
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        CalculationRule gwpRule = indicator?.getGraphCalculationRuleObjects(parentEntity)?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(indicator?.displayResult) })
        String GWP = gwpRule?.calculationRule
        Boolean draw1 = Boolean.FALSE
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parentEntity)
        List<CalculationResult> resultsByIndicator = design?.getCalculationResultObjects(indicatorId, null, null)
        String datasets = "["
        String calculationRuleName = "["
        int colorIndex = 0
        String labels = "["
        String backgroundColors = "["
        String hoverColors = "["

        if (design && GWP && resultCategories && indicatorId && resultsByIndicator) {
            calculationRuleName = gwpRule.localizedName
            if (calculationRuleName) {
                calculationRuleName = abbr(value: calculationRuleName, maxLength: 40)
            }
            Double totalScore = design.getTotalResult(indicatorId, gwpRule.calculationRuleId)
            List<String> categoriesWithScores = []
            int scoreIndex = 0
            resultCategories.each { ResultCategory resultCategory ->
                Double score = resultsByIndicator.find({
                    resultCategory.resultCategoryId.equals(it.resultCategoryId) && gwpRule.calculationRuleId.equals(it.calculationRuleId)
                })?.result
                Integer percentage = 0


                if (score && score > 0 && totalScore) {
                    score = Math.round((score / totalScore * 100))?.toInteger()

                    if (score > 0) {
                        labels = "${labels} ${scoreIndex > 0 ? ", " : ""}\"${resultCategoryService.getLocalizedShortName(resultCategory, 15)}\""
                        categoriesWithScores.add(resultCategory.resultCategoryId)
                        percentage = score


                        if (colorIndex + 1 > colors.size()) {
                            colorIndex = 0
                        }
                        color = colors.get(colorIndex)
                        highColor = highLightColors.get(colorIndex)
                        hoverColors = "${hoverColors}${scoreIndex > 0 ? ', ' : ''}\"${highColor}\""
                        backgroundColors = "${backgroundColors}${scoreIndex > 0 ? ', ' : ''}\"${color}\""
                        datasets = "${datasets}${scoreIndex > 0 ? ', ' : ''}${percentage}"


                        colorIndex++
                        scoreIndex++
                    }
                }
            }
            if (categoriesWithScores && !categoriesWithScores.isEmpty()) {
                draw1 = Boolean.TRUE
            }

        }
        backgroundColors = "${backgroundColors}]"
        hoverColors = "${hoverColors}]"
        labels = "${labels}]"

        datasets = "${datasets}]"
        String templateAsString = g.render(template: "/entity/entityPieGraphHotel", model: [datasets: datasets, calculationRuleName: calculationRuleName, indicator: indicator, draw1: draw1, entity: design, gwpRule: gwpRule, labels: labels, hoverColors: hoverColors, backgroundColors: backgroundColors]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def requestImportTrial() {
        String userId = params.user
        String returnable = ""

        if (userId) {
            User user = userService.getUserById(DomainObjectUtil.stringToObjectId(userId))
            if (user) {
                Boolean ok = activeCampaignService.requestImportTrial(user)
                if (ok) {
                    returnable = "Email sent!"
                } else {
                    returnable = "Failed to send email, user not found"
                }
            } else {
                returnable = "Failed to send email, user not found"
            }
        } else {
            returnable = "Failed to send email, userId is missing"
        }
        render([returnable: returnable].toString())
    }

    def renderResultGraphs() {
        try {
            String indicatorId = request?.JSON?.indicatorId
            String entityId = request?.JSON?.entityId
            Boolean allCategoryBreakdown = request?.JSON?.allCategoryBreakdown
            Boolean querySideGraph = request?.JSON?.querySideGraph
            List<String> chartsToRender = request?.JSON.chartsToRender?.replaceAll("[\\[\\]\\s]","")?.split(",")?.collect({ it as String }) ?: []
            Entity entity = entityService.getEntityById(entityId)
            Entity parent = entityService.getEntityById(entity?.parentEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            List<CalculationRule> indicatorRules = indicator?.getResolveCalculationRules(parent)
            List<CalculationRule> calculationRules = indicator?.getGraphCalculationRuleObjects(parent, null, indicatorRules)

            List<CalculationRule> calculationRulesForOverView = indicator?.getGraphCalculationRuleObjects(parent, Constants.RULES_IGNORE_INGRAPH, indicatorRules)

            String ruleId = request?.JSON?.calculationRuleId ? request?.JSON?.calculationRuleId : indicator?.displayResult ? indicator?.displayResult : (calculationRules?.size() > 0) ? calculationRules?.first()?.calculationRuleId : null
            CalculationRule calculationRule = ruleId && calculationRules?.find({ it.calculationRuleId?.equalsIgnoreCase(ruleId) }) ? calculationRules?.find({ it.calculationRuleId == ruleId }) : (calculationRules?.size() > 0) ? calculationRules?.first() : null
            List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(parent)

            //sw-1597
            List<Document> nmdElementList
            if(indicatorId.contains(Constants.NMD)){
                nmdElementList = nmdElementService.getNmdElementsAsDocument()
            }

            Map outputMap = [:]
            String unit
            List<String> colorTemplate = Constants.CHART_COLORS
            List<String> colorTemplateOpac = Constants.CHART_COLORS_OPAC
            Query additionalQuestionQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            String selectedClassification = ""

            if (indicator && entity && calculationRule && resultCategories) {
                List<String> classificationListForIndicator = indicator.classificationsMap
                Map<String,Question> additionalQuestions = classificationListForIndicator?.collectEntries {[(it):questionService.getQuestion(additionalQuestionQuery,it)]}
                Map<String,List<String>> indicatorQueryAddQMap = indicator?.indicatorQueries?.collectEntries({[(it.queryId):it.additionalQuestionIds]})
                String defaultClassQuesId = classificationListForIndicator?.size() > 0 ? classificationListForIndicator?.get(0) : ""
                selectedClassification = request?.JSON?.selectedClassification ?: defaultClassQuesId
                Question groupingQuestion = additionalQuestions?.get(selectedClassification)
                String defaultFallbackClassification = request.JSON?.defaultFallbackClassification // this is rendered from DesignController
                if(groupingQuestion){
                    outputMap.put("groupingQuestion",groupingQuestion.localizedQuestion)
                }

                List<String> resolvedResultCategoryIds = resultCategories.collect({it.resultCategoryId})
                List<CalculationResult> calculationResultListForAllRules = entity.getCalculationResultObjects(indicator.indicatorId, null, null)?.findAll({resolvedResultCategoryIds?.contains(it.resultCategoryId)})
                List<CalculationResult> calculationResultList = calculationResultListForAllRules?.findAll({ calculationRule.calculationRuleId?.equals(it.calculationRuleId) })
                Map<String,String> unitsMapToRule = [:]
                Map<String,String> calculationRulesMap = [:]

                calculationRules.each { CalculationRule rule ->
                    String localizedName = rule.localizedName
                    String localizedShortName = rule.localizedShortName ?: localizedName

                    calculationRulesMap << [(localizedShortName): localizedName]
                    unitsMapToRule << [(localizedShortName): calculationRuleService.getLocalizedUnit(rule, indicator)]
                }

                CalculationRule unitAsValueRefRule = indicatorRules?.find({ it.unitAsValueReference })
                // Performance issue: getQueries calls deprecated method queryService.getQueriesByIndicatorAndEntity(this, entity) without entityFeatures
                List<QuerySection> sections = indicator?.getQueries(entity)?.collectMany({ it.sections })?.findAll({ !it.sectionId?.equalsIgnoreCase("coreReference") })
                List datasets = entity.datasets?.toList()
                boolean fallbackToSection = groupingQuestion ? datasets?.collect({it.additionalQuestionAnswers})?.collect({it?.get(groupingQuestion?.questionId)})?.findAll({it})?.unique()?.size() <= 1 : false
                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, parent, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }
                if (querySideGraph) {
                    String querySideGraphAsString = queryResultSideBar(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit, colorTemplate, sections)
                    if (querySideGraphAsString) {
                        outputMap.put("querySideGraph", querySideGraphAsString)
                    }
                } else {
                    //RENDER GRAPH BASED ON REPORT VISUALIZATION OF INDICATOR
                    Boolean bubbleChartRender = chartsToRender?.contains("bubble")
                    Boolean overviewChartRender = chartsToRender?.contains("overview")
                    Boolean sankeyAndOtherChartRender = chartsToRender?.contains("sankey")
                    Boolean annualChartRender = chartsToRender?.contains("annual")
                    Boolean lifeCycleChartBreakdownRender = chartsToRender?.contains("breakdown")
                    Boolean circularChartRender = chartsToRender?.contains("circular")
                    Boolean treemapChartRender = chartsToRender?.contains("treemap")
                    Boolean lifeCycleStageChartRender = chartsToRender?.contains("lifeCycle")
                    Boolean barChartByImpactCategoryStackedByStage = chartsToRender?.contains("stackedStage")
                    Boolean barChartByImpactCategoryStackedByMaterial = chartsToRender?.contains("stackedMat")
                    Boolean radialByElementByDesign = chartsToRender?.contains("radial")
                    if (bubbleChartRender) {
                        String bubbleChartAsString = bubbleChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit, unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (bubbleChartAsString) {
                            outputMap.put("bubbleChart", bubbleChartAsString)
                        }
                    }

                    if (overviewChartRender) {
                        String overviewChartAsString = overviewChart(indicator, entity, parent,calculationRules, calculationRule, resultCategories, calculationResultList, unit, unitsMapToRule,calculationRulesMap, colorTemplate, sections, calculationResultListForAllRules, calculationRulesForOverView, groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection, nmdElementList)
                        if (overviewChartAsString) {
                            outputMap.put("overviewChart", overviewChartAsString)
                        }
                    }
                    if (treemapChartRender) {
                        String treeMapAsString = treeMapChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit, unitsMapToRule,calculationRulesMap,colorTemplateOpac, sections, calculationResultListForAllRules)
                        if (treeMapAsString) {
                            outputMap.put("treeMapChart", treeMapAsString)
                        }
                    }
                    if (sankeyAndOtherChartRender) {
                        String sankeyChartAsString = sankeyChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections,groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection, nmdElementList)
                        if (sankeyChartAsString) {
                            outputMap.put("sankeyChart", sankeyChartAsString)
                        }
                    }
                    if (annualChartRender) {
                        String annualChartAsString = annualChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (annualChartAsString) {
                            outputMap.put("annualChart", annualChartAsString)
                        }
                    }

                    if (lifeCycleChartBreakdownRender) {
                        String lifeCycleBreakdownChartAsString = lifeCycleChartBreakdown(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit, unitsMapToRule,calculationRulesMap, colorTemplate, sections, allCategoryBreakdown, null,  null,groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection, nmdElementList)
                        if (lifeCycleBreakdownChartAsString) {
                            outputMap.put("lifeCycleBreakdownChart", lifeCycleBreakdownChartAsString)
                        }
                    }
                    if (circularChartRender) {
                        String circularChartAsString = circularChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (circularChartAsString) {
                            outputMap.put("circularChart", circularChartAsString)
                        }
                    }
                    if (lifeCycleStageChartRender) {
                        String lifeCycleChartAsString = lifeCycleStageChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultListForAllRules, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (lifeCycleChartAsString) {
                            outputMap.put("lifeCycleChart", lifeCycleChartAsString)
                        }
                    }
                    if (barChartByImpactCategoryStackedByStage) {
                        String chartByImpactCategoryStackedAsString = chartByImpactCategoryStackedByStage(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultListForAllRules, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (chartByImpactCategoryStackedAsString) {
                            outputMap.put("chartByImpactCategoryStacked", chartByImpactCategoryStackedAsString)
                        }
                    }
                    if(radialByElementByDesign){
                        String chartRadialByElementByDesignAsString = chartRadialByementByDesign(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultListForAllRules, unit, unitsMapToRule,calculationRulesMap,colorTemplate, sections,groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection, nmdElementList)
                        if (chartRadialByElementByDesignAsString) {
                            outputMap.put("chartRadialByElementByDesign", chartRadialByElementByDesignAsString)
                        }
                    }
                    if(barChartByImpactCategoryStackedByMaterial){
                        String chartByMaterialByImpactRuleStackedAsString = chartByMaterialByImpactRuleStacked(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultListForAllRules, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections)
                        if (chartByMaterialByImpactRuleStackedAsString) {
                            outputMap.put("chartByMatAndImpactStacked", chartByMaterialByImpactRuleStackedAsString)
                        }
                    }
                    String btnGroupResultClassList = g.render(template: "/entity/btnGroupResultClassList", model: [entity:entity,allClassificationQsMap:additionalQuestions,groupingQuestion:groupingQuestion,graphCalculationRules:calculationRules,defaultFallbackClassification:defaultFallbackClassification,indicator:indicator]).toString()
                    if (btnGroupResultClassList) {
                        outputMap.put("btnGroupResultClassList", btnGroupResultClassList)
                    }
                }
            }

            render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } catch(error){
            loggerUtil.error(log, "ERROR rendering renderResultGraphs", error)
            flashService.setErrorAlert("ERROR rendering renderResultGraphs: ${error.message}", true)
            response.status = 500
            render([output: "error", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }


    }

    def renderSourceListing() {
        try {
            String indicatorId = request?.JSON?.indicatorId
            String parentEntityId = request?.JSON?.parentEntityId
            String entityId = request?.JSON?.entityId
            String countryNameEN = request?.JSON?.countryNameEN
            String projectCountry = request?.JSON?.projectCountry
            Boolean showDiv = request?.JSON?.showDiv

            Entity entity = entityService.getEntityById(entityId)
            Entity parentEntity = entityService.getEntityById(parentEntityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)

            Map params = [
                    indicator: indicator,
                    parentEntity: parentEntity,
                    entity: entity,
                    countryNameEN: countryNameEN,
                    projectCountry: projectCountry,
                    showDiv: showDiv
            ]

            IndicatorTagLib indicatorTagLib = grailsApplication.mainContext.getBean(IndicatorTagLib.class)
            String output = indicatorTagLib.renderSourceListing(params).toString()

            render([output: output, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } catch(error){
            loggerUtil.error(log, "ERROR rendering renderSourceListing", error)
            flashService.setErrorAlert("ERROR rendering renderSourceListing: ${error.message}", true)
            response.status = 500
            render([output: g.message(code: "error.general"), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    //TODO: this function only used for allBreakDowns charts, for now keep it like this, will refactoring it better next release
    def redrawResultCharts() {
        try{
            String indicatorId = request?.JSON.indicatorId
            String entityId = request?.JSON.entityId
            String calculationRuleId = request?.JSON.calculationRuleId
            String chartDivId = request?.JSON.chartDivId
            Boolean allCategoryBreakdown = request?.JSON.allCategoryBreakdown
            String individualCategoryBreakdown = request?.JSON.individualCategoryBreakdown && request?.JSON.individualCategoryBreakdown != "null" ? request?.JSON.individualCategoryBreakdown : null
            String selectedClassification = ""


            Entity entity = entityService.getEntityById(entityId)
            Entity parent = entity?.parentById
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            List<CalculationRule> calculationRules = indicator?.getGraphCalculationRuleObjects(parent)
            List<CalculationRule> calculationRulesForOverView = indicator?.getGraphCalculationRuleObjects(parent, Constants.RULES_IGNORE_INGRAPH)
            CalculationRule calculationRule = calculationRules?.find({ it.calculationRuleId?.equalsIgnoreCase(calculationRuleId) })
            Map outputMap = [:]
            String chartAsString = ""
            String unit
            List<String> colorTemplate = Constants.CHART_COLORS
            List<String> colorTemplateOpac = Constants.CHART_COLORS_OPAC

            if (indicator && entity && calculationRule) {
                List<String> classificationListForIndicator = indicator.classificationsMap
                Query additionalQuestionQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                Map<String,Question> additionalQuestions = classificationListForIndicator?.collectEntries {[(it):questionService.getQuestion(additionalQuestionQuery,it)]}
                String defaultClassQuesId = classificationListForIndicator?.size() > 1 ? classificationListForIndicator?.get(0) : ""
                selectedClassification = request?.JSON?.selectedClassification ?: defaultClassQuesId
                Question groupingQuestion = additionalQuestions?.get(selectedClassification)
                Map<String,List<String>> indicatorQueryAddQMap = indicator?.indicatorQueries?.collectEntries({[(it.queryId):it.additionalQuestionIds]})
                Map<String,String> unitsMapToRule = [:]
                Map<String,String> calculationRulesMap = [:]

                calculationRules.each { CalculationRule rule ->
                    String localizedName = rule.localizedName
                    String localizedShortName = rule.localizedShortName ?: localizedName

                    calculationRulesMap << [(localizedShortName): localizedName]
                    unitsMapToRule << [(localizedShortName): calculationRuleService.getLocalizedUnit(rule, indicator)]
                }

                String defaultFallbackClassification = classificationListForIndicator?.size() == 1 && classificationListForIndicator?.contains(com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID) ? message(code: "default_class") : null
                List datasets = entity.datasets?.toList()
                boolean fallbackToSection = groupingQuestion ? datasets?.collect({it?.additionalQuestionAnswers?.findAll({it?.key == groupingQuestion?.questionId})?.collect({it?.value})})?.flatten()?.unique()?.size() <= 1  : true


                List<ResultCategory> resultCategories = indicator.getGraphResultCategoryObjects(parent)
                List<CalculationResult> allCalculationResults = entity.getCalculationResultObjects(indicator.indicatorId, null, null)
                List<CalculationResult> calculationResultList = allCalculationResults?.findAll({ calculationRule.calculationRuleId?.equals(it.calculationRuleId) })

                CalculationRule unitAsValueRefRule = indicator.getResolveCalculationRules(parent)?.find({ it.unitAsValueReference })
                List<QuerySection> sections = indicator?.getQueries(entity)?.collectMany({ it.sections })?.findAll({ !it.sectionId?.equalsIgnoreCase("coreReference") })


                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, parent, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }
                //RENDER GRAPH BASED ON REPORT VISUALIZATION OF INDICATOR
                if (chartDivId) {

                    switch (chartDivId) {
                        case "overviewPieChart":
                            chartAsString = overviewChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap,colorTemplate, sections, allCalculationResults, calculationRulesForOverView, groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection)
                            break
                        case "bubbleChartTypeSubtype":
                            chartAsString = bubbleChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule, calculationRulesMap,colorTemplate, sections)
                            break
                        case "sankeyWrapper":
                            chartAsString = sankeyChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections, groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection)
                            break
                        case "allBreakDowns":
                            if(allCategoryBreakdown){
                                calculationRules?.each {CalculationRule calculationRule1 ->
                                    if(calculationRule1 != calculationRule){
                                        String currentChartUnit = calculationRuleService.getLocalizedUnit(calculationRule1, indicator)
                                        List<CalculationResult> calculationResultList1 = allCalculationResults?.findAll({ calculationRule1.calculationRuleId?.equals(it.calculationRuleId) })
                                        chartAsString = "${chartAsString}${lifeCycleChartBreakdown(indicator, entity, parent, calculationRules, calculationRule1, resultCategories, calculationResultList1, currentChartUnit,unitsMapToRule,calculationRulesMap, colorTemplate, sections, allCategoryBreakdown, false, individualCategoryBreakdown, groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection)}"
                                    }
                                }
                            } else {
                                chartAsString = lifeCycleChartBreakdown(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule,calculationRulesMap, colorTemplate, sections, allCategoryBreakdown, false, individualCategoryBreakdown, groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection)
                            }
                            break
                        case "treeMapWrapper":
                            chartAsString = treeMapChart(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit,unitsMapToRule, calculationRulesMap,colorTemplateOpac, sections, allCalculationResults)
                            break
                        case "radialByDesignByElementWrapper":
                            chartAsString = chartRadialByementByDesign(indicator, entity, parent, calculationRules, calculationRule, resultCategories, allCalculationResults, unit, unitsMapToRule,calculationRulesMap,colorTemplate, sections,groupingQuestion,additionalQuestions,indicatorQueryAddQMap,defaultFallbackClassification,fallbackToSection )
                            break
                        case "chartByImpactCategoryStackedByStage":
                            chartAsString = chartByImpactCategoryStackedByStage(indicator, entity, parent, calculationRules, calculationRule, resultCategories, calculationResultList, unit, unitsMapToRule,calculationRulesMap,colorTemplate, sections)
                            break
                    }
                }
                outputMap.put("chartDisplay", chartAsString)
                outputMap.put("chartDiv", individualCategoryBreakdown? individualCategoryBreakdown : chartDivId)

            }
            render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } catch (e) {
            loggerUtil.error(log, "Error in rendering redrawResultCharts:", e)
            flashService.setErrorAlert("Error in rendering redrawResultCharts: ${e.message}", true)
        }

    }

    private String overviewChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories,
                                 List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule, Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections, List<CalculationResult> allCalculationResults,
                                 List<CalculationRule> calculationRulesForOverView, Question groupingQuestion, Map<String,Question> allClassificationQsMap, Map<String,List<String>> indicatorAddQs,String defaultFallbackClassification,boolean fallbackToSection, List<Document> nmdElementList = null) {
        try {
            String unitForCarbon = unit
            String unitForMass = "kg"
            String indicatorId = indicator.indicatorId
            String calculationRuleId = calculationRule?.calculationRuleId
            String nameForOtherType = g.message(code: 'other_resource_type')
            String nameForOtherClass = g.message(code: 'other_classification')
            String nameForOtherSubType = g.message(code: 'other_resource_sub_type')
            Boolean fallbackExplanation = Boolean.FALSE
            List<String> colorTemplateOpac = Constants.CHART_COLORS_OPAC
            def resultMapForTypes = [:]
            def groupingQuestionChoices = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []
            def now = System.currentTimeMillis()
            String selectedPrivateClassification = groupingQuestion?.localizedQuestion ?: ""
            Map<String, Map<String, Double>> resultMapForTypesAndSubType = [:]
            def resultMapForClassification = [:]
            def resultMapForResultCategory = [:]
            def massByClassification = [:]
            if (entity && indicator && calculationRule && resultCategories && calculationResults) {
                CalculationRule calculationRuleForMass = calculationRulesForOverView?.sort({it -> Constants.RULES_IGNORE_INGRAPH.indexOf(it.calculationRuleId)})?.find({ Constants.RULES_IGNORE_INGRAPH.contains(it.calculationRuleId) })
                Boolean indicatorHasMass = calculationRuleForMass ? Boolean.TRUE : Boolean.FALSE

                unitForMass = calculationRuleService.getLocalizedUnit(calculationRuleForMass, indicator) ?: "kg"

                ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
                ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()
                Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }

                resultCategories?.each { ResultCategory resultCategory ->
                    List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(resultCategory.resultCategoryId)
                    Double score = entity.getResult(indicator.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsForCategory, false)
                    String resultCategoryIdForGraph = "${resultCategory.resultCategory} ${resultCategoryService.getLocalizedShortName(resultCategory)}"
                    if (score) {
                        resultMapForResultCategory.put(resultCategoryIdForGraph, score)
                    }
                    List<Dataset> impactPerSubTypeDatasets = entity.getDatasetsForResultCategoryExpand(indicatorId, resultCategory.resultCategoryId, resultsForCategory, false)

                    if (impactPerSubTypeDatasets) {
                        for (Dataset d: impactPerSubTypeDatasets) {
                            Resource r = resourceCache.getResource(d)
                            if (r) {
                                //for Resource Pie
                                Double value = entity?.getResultForDataset(d.manualId, indicatorId, resultCategory.resultCategoryId, calculationRuleId, resultsForCategory, false)

                                if (value) {
                                    String shortName = resourceTypeService.getLocalizedShortName(resourceTypeCache.getResourceType(r))
                                    String resourceType = shortName ?: nameForOtherType
                                    String subTypeShortName = resourceTypeService.getLocalizedShortName(resourceTypeCache.getResourceSubType(r))
                                    String subType = subTypeShortName ?: nameForOtherSubType
                                    //for ResourceType
                                    if (resultMapForTypes.get(resourceType)) {
                                        Double newScore = value + resultMapForTypes.get(resourceType)
                                        resultMapForTypes.put(resourceType, newScore)
                                    } else {
                                        resultMapForTypes.put(resourceType, value)
                                    }
                                    //drilldown for subtype
                                    if (resultMapForTypesAndSubType.get(resourceType)) {
                                        Map<String, Double> supTypeSubMap = resultMapForTypesAndSubType.get(resourceType)
                                        if (supTypeSubMap.get(subType)) {
                                            Double newScoreSubMap = value + supTypeSubMap.get(subType)
                                            supTypeSubMap.put(subType, newScoreSubMap)
                                        } else {
                                            supTypeSubMap.put(subType, value)
                                        }
                                        resultMapForTypesAndSubType.put(resourceType, supTypeSubMap)
                                    } else {
                                        Map<String, Double> supTypeSubMap = [(subType): value]
                                        resultMapForTypesAndSubType.put(resourceType, supTypeSubMap)
                                    }

                                    //for Classification or Section Pie
                                    String groupingType = datasetService.getGroupingTypeOfDataset(indicator, sections, d, nameForOtherClass, groupingQuestion,indicatorAddQs,fallbackToSection, nmdElementList)

                                    if (!r.construction) {
                                        if (resultMapForClassification.get(groupingType)) {
                                            Double newScore1 = value + resultMapForClassification.get(groupingType)
                                            resultMapForClassification.put(groupingType, newScore1)
                                        } else {
                                            resultMapForClassification.put(groupingType, value)
                                        }
                                        if (indicatorHasMass) {
                                            Double mass = entity?.getResultForDataset(d.manualId, indicatorId, resultCategory.resultCategoryId, calculationRuleForMass.calculationRuleId, allCalculationResults)
                                            if (mass) {
                                                if (massByClassification.get(groupingType)) {
                                                    Double newMass = mass + massByClassification.get(groupingType)
                                                    massByClassification.put(groupingType, newMass)
                                                } else {
                                                    massByClassification.put(groupingType, mass)

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Adding fallback for indicators that dont have mass
                if (!indicatorHasMass) {
                    Indicator fallbackIndicator = indicatorService.getIndicatorByIndicatorId("xBenchmarkEmbodied")
                    List<CalculationResult> calResListForAllRulesFallback = entity.getCalculationResultObjects(fallbackIndicator.indicatorId, "mass", null)
                    List<ResultCategory> resultCatListFallback = fallbackIndicator?.getGraphResultCategoryObjects(parent)
                    Denominator overallDenominatorFallback = fallbackIndicator?.resolveDenominators?.find({
                        "overallDenominator".equalsIgnoreCase(it.denominatorType)
                    })
                    Double overallDenomValueFallback = denominatorUtil.getFinalValueForDenominator(entity, overallDenominatorFallback)

                    List<QuerySection> fallbackSections = fallbackIndicator?.getQueries(entity)?.collectMany({ it.sections })?.findAll({ !it.sectionId?.equalsIgnoreCase("coreReference") })

                    resultCatListFallback.each { ResultCategory resultCategory1 ->
                        List<Dataset> impactPerSubTypeDatasetsFallBack = entity.getDatasetsForResultCategoryExpand(fallbackIndicator.indicatorId, resultCategory1.resultCategoryId, calResListForAllRulesFallback, false)

                        impactPerSubTypeDatasetsFallBack?.each { Dataset d1 ->
                            Double mass = entity?.getResultForDataset(d1.manualId, fallbackIndicator.indicatorId, resultCategory1.resultCategoryId, "mass", calResListForAllRulesFallback, false)
                            String groupingType1 = datasetService.getGroupingTypeOfDataset(fallbackIndicator, fallbackSections, d1, nameForOtherClass, groupingQuestion,indicatorAddQs,fallbackToSection, nmdElementList)
                            if (mass) {
                                if (overallDenominatorFallback && overallDenomValueFallback) {
                                    mass = mass * overallDenomValueFallback
                                }
                                if (massByClassification.get(groupingType1)) {
                                    Double newMass = mass + massByClassification.get(groupingType1)
                                    massByClassification.put(groupingType1, newMass)
                                } else {
                                    massByClassification.put(groupingType1, mass)
                                }
                            }
                        }
                    }
                }
            }
            resultMapForTypes = resultMapForTypes?.sort({ -it.value })
            resultMapForClassification = resultMapForClassification?.sort({ -it.value })
            massByClassification = massByClassification?.sort({ -it.value })
            resultMapForResultCategory = resultMapForResultCategory?.sort({ it.key })
            if (resultMapForTypes?.size() > 10) {
                Double summed1 = resultMapForTypes?.values()?.takeRight(resultMapForTypes.size() - 9)?.sum()
                resultMapForTypes = resultMapForTypes.take(9)
                if (resultMapForTypes.get(nameForOtherType)) {
                    Double value1 = resultMapForTypes.get(nameForOtherType) + summed1
                    resultMapForTypes.put(nameForOtherType, value1)
                } else {
                    resultMapForTypes.put(nameForOtherType, summed1)
                }
            }
            if (resultMapForClassification?.size() > 10) {
                Double summed2 = resultMapForClassification?.values()?.takeRight(resultMapForClassification.size() - 9)?.sum()
                resultMapForClassification = resultMapForClassification.take(9)
                if (resultMapForClassification.get(nameForOtherClass)) {
                    Double value2 = resultMapForClassification.get(nameForOtherClass) + summed2
                    resultMapForClassification.put(nameForOtherClass, value2)
                } else {
                    resultMapForClassification.put(nameForOtherClass, summed2)
                }
            }
            if (massByClassification?.size() > 10) {
                Double summed3 = massByClassification?.values()?.takeRight(massByClassification.size() - 9)?.sum()
                massByClassification = massByClassification.take(9)
                if (massByClassification.get(nameForOtherClass)) {
                    Double value3 = massByClassification.get(nameForOtherClass) + summed3
                    massByClassification.put(nameForOtherClass, value3)
                } else {
                    massByClassification.put(nameForOtherClass, summed3)
                }
            }
            resultMapForClassification = resultMapForClassification?.sort({ groupingQuestionChoices.indexOf(it.key) != -1 ? groupingQuestionChoices.indexOf(it.key) : groupingQuestionChoices?.size()})
            massByClassification = massByClassification?.sort({ groupingQuestionChoices.indexOf(it.key) != -1 ? groupingQuestionChoices.indexOf(it.key) : groupingQuestionChoices?.size()})

            Map<String, Map<String, Double>> formattedResultTableForTypes = resultMapForTypes?.collectEntries({ [(it.key): ['value': resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(user, it.value), 'percentage': (it.value / resultMapForTypes?.values().sum() * 100).round(2)]] })
            Map<String, Map<String, Double>> formattedResultTableForResultCategory = resultMapForResultCategory?.collectEntries({ [(it.key): ['value': resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(user, it.value), 'percentage': (it.value / resultMapForResultCategory?.values().sum() * 100).round(2)]] })
            Map<String, Map<String, Double>> formattedResultTableForClassification = resultMapForClassification?.collectEntries({ [(it.key): ['value': resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(user, it.value), 'percentage': (it.value / resultMapForClassification?.values().sum() * 100).round(2)]] })
            Map<String, Map<String, Double>> formattedResultTableMassByClassification = massByClassification?.collectEntries({ [(it.key): ['value': resultFormattingResolver.formatByTwoDecimalsAndLocalizedDecimalSeparator(user, it.value), 'percentage': (it.value / massByClassification?.values().sum() * 100).round(2)]] })


            def later = System.currentTimeMillis() - now
            log.info("time to take raw " + later.toString() + "ms")
            List graphDatasetForTypes = resultMapForTypes?.collect { ['name': it.key, 'y': it.value, 'drilldown': it.key] } ?: []
            List pieGraphDatasetForTypes = resultMapForTypes?.findAll({ it.value > 0 })?.collect { ['name': it.key, 'y': it.value, 'drilldown': it.key] } ?: []
            List<String> keysetForDrilldown = resultMapForTypes?.findAll({ it.value > 0 })?.keySet().toList()
            List drillDownDatasetForSubTypes = resultMapForTypesAndSubType?.collect { ['name': it.key, 'id': it.key, 'data': it.value.collect { k, v -> [k, v] }] } ?: []
            List pieDrillDownDatasetForSubTypes = resultMapForTypesAndSubType?.findAll { keysetForDrilldown?.contains(it.key) }?.collect { ['name': it.key, 'id': it.key, 'data': it.value.collect { k, v -> [k, v] }] } ?: []
            String treeChartDatasetsForTypes = renderGraphForTreemapChart(resultMapForTypesAndSubType, colorTemplateOpac, true)
            List graphDatasetForResultCategory = resultMapForResultCategory?.collect { ['name': it.key, 'y': it.value] } ?: []
            String treeChartDatasetForResultCategory = renderGraphForTreemapChart(resultMapForResultCategory, colorTemplateOpac)
            List pieGraphDatasetForResultCategory = resultMapForResultCategory?.findAll { it.value > 0 }?.collect { ['name': it.key, 'y': it.value] } ?: []
            List graphDatasetForClassification = resultMapForClassification?.collect { ['name': it.key, 'y': it.value] } ?: []
            String treeChartDatasetForClassification = renderGraphForTreemapChart(resultMapForClassification, colorTemplateOpac)
            List pieGraphDatasetForClassification = resultMapForClassification?.findAll { it.value > 0 }?.collect { ['name': it.key, 'y': it.value] } ?: []
            List massGraphDatasetForClassification = massByClassification?.collect { ['name': it.key, 'y': it.value] } ?: []
            String massTreeChartDatasetForClassification = renderGraphForTreemapChart(massByClassification, colorTemplateOpac)

            def later2 = System.currentTimeMillis() - now
            log.info("time to process from raw " + later2.toString() + "ms")

            String pageAsString = g.render(template: "overviewPieChart", model: [formattedResultTableForTypes            : formattedResultTableForTypes, formattedResultTableForClassification: formattedResultTableForClassification, formattedResultTableForResultCategory: formattedResultTableForResultCategory, unit: unitForCarbon, unitForMass: unitForMass, fallbackExplanation: fallbackExplanation,
                                                                                 formattedResultTableMassByClassification: formattedResultTableMassByClassification, graphDatasetForTypes: graphDatasetForTypes as JSON, graphDatasetForStages: graphDatasetForResultCategory as JSON, graphDatasetForClassification: graphDatasetForClassification as JSON,
                                                                                 massGraphDatasetForClassification       : massGraphDatasetForClassification as JSON, pieGraphDatasetForTypes: pieGraphDatasetForTypes as JSON, pieDrillDownSubType: pieDrillDownDatasetForSubTypes as JSON, pieGraphDatasetForStages: pieGraphDatasetForResultCategory as JSON,
                                                                                 pieGraphDatasetForClassification        : pieGraphDatasetForClassification as JSON, drillDownSubType: drillDownDatasetForSubTypes as JSON, graphCalculationRules: calculationRules, entity: entity, indicator: indicator, calculationRule: calculationRule, colorTemplate: colorTemplate as JSON,
                                                                                 massTreeChartDatasetForClassification   : massTreeChartDatasetForClassification, treeChartDatasetForClassification: treeChartDatasetForClassification, treeChartDatasetForResultCategory: treeChartDatasetForResultCategory, treeChartDatasetsForTypes: treeChartDatasetsForTypes,
                                                                                 groupingQuestion: groupingQuestion,allClassificationQsMap:allClassificationQsMap,selectedPrivateClassification:selectedPrivateClassification,defaultFallbackClassification:defaultFallbackClassification]).toString()
            return pageAsString
        } catch(e){
            loggerUtil.error(log, "Error in rendering overviewChart:", e)
            flashService.setErrorAlert("Error in rendering overviewChart: ${e.message}", true)
        }
    }

    private String sankeyChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule, Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections, Question groupingQuestion, Map<String,Question> allClassificationQsMap, Map<String,List<String>> indicatorAddQs,String defaultFallbackClassification,boolean fallbackToSection, List<Document> nmdElementList = null) {
       try {
           String indicatorId = indicator.indicatorId
           Integer threshold = 1
           def arrCatVsClass = []
           def arrCatVsType = []
           def arrClassVsType = []
           def arrClassVsSubType = []
           def arrTypeVsSubType = []
           String selectedPrivateClassification = groupingQuestion?.localizedQuestion ?: ""
           def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []
           Map<String, List<String>> groupingElements = [:]
           List<String> calculationRuleIds = calculationRules?.collect { it.calculationRuleId }

           String calculationRuleId = calculationRule.calculationRuleId ?: calculationRuleIds[0]
           def now = System.currentTimeMillis()

           if (entity && indicator && calculationRuleId && parent) {
               //from Indicator get showInGraphs resultCategory

               if (resultCategories && calculationRuleId && calculationResults) {

                   //With each resultCategory retrieve respective datasets as Document - tier 1

                   Map<String, Map<String, Map<String, Map<String, Double>>>> resultPerCategoryAndClassificationAndTypeAndSubType = [:]
                   Double totalResult = entity.getTotalResult(indicatorId, calculationRuleId)
                   Double benchmarkNumber = 0
                   if (totalResult) {
                       benchmarkNumber = totalResult * threshold / 100
                   }

                   ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
                   ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()

                   Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }

                   resultCategories.each { ResultCategory resultCategory ->
                       String localizedShortName = resultCategoryService.getLocalizedShortName(resultCategory)
                       String resultCatKey = resultCategory.resultCategory + " " + localizedShortName

                       List<CalculationResult> resultsForResultCategory = indicatorResultsPerResultCategory?.getAt(resultCategory.resultCategoryId)
                       List<Dataset> impactPerSubTypeDatasets = entity.getDatasetsForResultCategoryExpand(indicatorId, resultCategory.resultCategoryId, resultsForResultCategory, false)


                       if (impactPerSubTypeDatasets) {

                           Map<String, Map<String, Map<String, Double>>> resultPerClassificationAndTypeAndSubType = resultPerCategoryAndClassificationAndTypeAndSubType.get(resultCatKey)

                           for (Dataset d: impactPerSubTypeDatasets) {
                               Resource r = resourceCache.getResource(d)
                               if (r) {
                                   String fallBackString = g.message(code: 'other_classification')
                                   String groupingType = fallBackString
                                   if (!r?.construction) {
                                       // Performance issue: getGroupingTypeOfDataset is slow due to getLocalizedQuestionShort - locale is resolved for each question
                                       groupingType = datasetService.getGroupingTypeOfDataset(indicator, sections, d, fallBackString, groupingQuestion,indicatorAddQs,fallbackToSection, nmdElementList)
                                   }

                                   Double value = entity?.getResultForDataset(d.manualId, indicatorId, resultCategory.resultCategoryId, calculationRuleId, resultsForResultCategory, false) ?: 0
                                   String resourceType = resourceTypeService.getLocalizedName(resourceTypeCache.getResourceType(r)) ?: g.message(code: 'other_resource_type')
                                   ResourceType resourceSubType = resourceTypeCache.getResourceSubType(r)

                                   String subTypeShortName = resourceTypeService.getLocalizedShortName(resourceSubType)
                                   String subTypeName = resourceTypeService.getLocalizedName(resourceSubType)
                                   String subType = subTypeShortName ?: subTypeName ?: g.message(code: 'other_resource_sub_type')
                                   Map<String, Map<String, Double>> resultPerTypeAndSubType = resultPerClassificationAndTypeAndSubType?.get(groupingType)

                                   Map<String, Double> resultPerSubType = resultPerTypeAndSubType?.get(resourceType)
                                   if (resultPerSubType) {
                                       Double subTypeTotal = resultPerSubType.get(subType) ?: 0
                                       subTypeTotal = subTypeTotal + value
                                       resultPerSubType.put(subType, subTypeTotal)
                                   } else {
                                       resultPerSubType = [(subType): value]
                                   }

                                   if (resultPerTypeAndSubType) {
                                       resultPerTypeAndSubType.put(resourceType, resultPerSubType)
                                   } else {
                                       resultPerTypeAndSubType = [(resourceType): resultPerSubType]
                                   }

                                   if (resultPerClassificationAndTypeAndSubType) {
                                       resultPerClassificationAndTypeAndSubType.put(groupingType, resultPerTypeAndSubType)
                                   } else {
                                       resultPerClassificationAndTypeAndSubType = [(groupingType): resultPerTypeAndSubType]
                                   }
                               }
                           }
                           if (resultPerClassificationAndTypeAndSubType) {
                               resultPerClassificationAndTypeAndSubType = resultPerClassificationAndTypeAndSubType?.sort({groupingQuestionAnswer.indexOf(it.key) != 1 ? groupingQuestionAnswer.indexOf(it.key) : groupingQuestionAnswer.size()})
                               if (resultPerCategoryAndClassificationAndTypeAndSubType) {
                                   resultPerCategoryAndClassificationAndTypeAndSubType.put((resultCatKey), resultPerClassificationAndTypeAndSubType)
                               } else {
                                   resultPerCategoryAndClassificationAndTypeAndSubType = [(resultCatKey): resultPerClassificationAndTypeAndSubType]
                               }
                           }

                       }
                   }

                   def resTitles = []
                   def classTitles = []
                   def typeTitles = []
                   def subTypeTitles = []


                   resultPerCategoryAndClassificationAndTypeAndSubType?.each { String resCat, Map<String, Map<String, Map<String, Double>>> classAndTypeAndSubType ->
                       Double totalByRes = resultPerCategoryAndClassificationAndTypeAndSubType.get(resCat)?.values()?.collect({ it.values().collect({ it.values() }) })?.flatten()?.sum()
                       if (totalByRes > 0) {
                           String resCatTitle = resCat
                           resTitles.add(resCatTitle)
                           classAndTypeAndSubType.each { String groupingType, Map<String, Map<String, Double>> typeAndSubType ->
                               Double totalByClass = resultPerCategoryAndClassificationAndTypeAndSubType.get(resCat)?.get(groupingType)?.values()?.collect({ it.values() })?.flatten()?.sum()
                               if (totalByClass > 0) {
                                   String classTitle = groupingType
                                   if (totalByClass < benchmarkNumber) {
                                       classTitle = g.message(code: 'other_classification')
                                   }

                                   def array1 = [resCatTitle, classTitle, totalByClass]
                                   arrCatVsClass.add(array1)

                                   classTitles.add(classTitle)
                                   typeAndSubType.each { String type, Map<String, Double> resSubType ->
                                       Double totalByType = resultPerCategoryAndClassificationAndTypeAndSubType.get(resCat)?.get(groupingType)?.get(type)?.values()?.sum()
                                       if (totalByType > 0) {
                                           String typeTitle = type
                                           if (totalByType < benchmarkNumber) {
                                               typeTitle = g.message(code: 'other_resource_type')
                                           }

                                           typeTitles.add(typeTitle)
                                           def array2 = [resCatTitle, typeTitle, totalByType]
                                           def array3 = [classTitle, typeTitle, totalByType]
                                           arrClassVsType.add(array3)
                                           arrCatVsType.add(array2)
                                           resSubType.each { String subType, Double value ->
                                               Double resultBySubType = resSubType.get(subType)

                                               if (resultBySubType > 0) {
                                                   if (resultBySubType < benchmarkNumber) {
                                                       subType = g.message(code: 'other_resource_sub_type')
                                                   }
                                                   subTypeTitles.add(subType)
                                                   def array4 = [typeTitle, subType, resultBySubType]
                                                   def array5 = [classTitle, subType, resultBySubType]
                                                   arrTypeVsSubType.add(array4)
                                                   arrClassVsSubType.add(array5)
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
                   groupingElements.put("byStage", resTitles.unique().sort())
                   groupingElements.put("byClass", classTitles.unique().sort())
                   groupingElements.put("sankey_material_types", typeTitles.unique().sort())
                   groupingElements.put("sankey_material_subtypes", subTypeTitles.unique().sort())
               }
           }

           def later = System.currentTimeMillis() - now
           log.info("time to process Sankey " + later.toString() + "ms")

           //Depending on settings selected by user that different structure will be retrieved. If no selection then fallback to default
           // Looping through tier 1 to collect tier 2 & tier 3 result
           //add conversion to percentage while looping (result/total)
           String pageAsString = g.render(template: "sankeyDiagram", model: [arrCatVsClass: arrCatVsClass, arrClassVsType: arrClassVsType, arrCatVsType: arrCatVsType, arrTypeVsSubType: arrTypeVsSubType, arrClassVsSubType: arrClassVsSubType,defaultFallbackClassification:defaultFallbackClassification,
                                                                             unit: unit.toString(), graphCalculationRules: calculationRules, entity: entity, indicator: indicator, calculationRule: calculationRule, groupingElements: groupingElements,
                                                                             colorTemplate: colorTemplate,groupingQuestion: groupingQuestion,allClassificationQsMap:allClassificationQsMap,selectedPrivateClassification:selectedPrivateClassification]).toString()
           return pageAsString
       } catch (e) {
           loggerUtil.error(log, "Error in rendering sankeyChart:", e)
           flashService.setErrorAlert("Error in rendering sankeyChart: ${e.message}", true)
       }
    }

    private String annualChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule, Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections) {
        try {
            String calculationRuleId = calculationRule?.calculationRuleId
            String datasets = "["
            String labels = "["

            if (entity && indicator) {
                int colorIndex = 0
                Double assestmentPeriod = (Double) indicator.assessmentPeriodFixed ? indicator.assessmentPeriodFixed + 3 : valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, entity) ? valueReferenceService.getDoubleValueForEntity(indicator.assessmentPeriodValueReference, entity) + 3 : 100
                List<String> annums = []
                for (int i = 0; i < assestmentPeriod; i++) {
                    labels = "${labels}${i > 0 ? ', ' : ''}\"${i}\""
                    annums.add(i?.toString())
                }
                if (resultCategories && calculationResults) {

                    Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }
                    resultCategories.each { ResultCategory category ->

                        List<CalculationResult> resultsForResultCategory = indicatorResultsPerResultCategory?.getAt(category.resultCategoryId)
                        Map<String, Double> scoreForCategoryAnnum = resultsForResultCategory?.find({
                            calculationRuleId?.equalsIgnoreCase(it.calculationRuleId)
                        })?.resultPerOccurencePeriod ?: [:]


                        if (colorIndex + 1 > colorTemplate.size()) {
                            colorIndex = 0
                        }


                        datasets = "${datasets}{name: \"${category.resultCategory + " " + resultCategoryService.getLocalizedShortName(category)}\","

                        datasets = "${datasets}data: ["
                        int scoreIndex = 0
                        annums.each {
                            datasets = "${datasets}${scoreIndex > 0 ? ', ' : ''}${scoreForCategoryAnnum && !scoreForCategoryAnnum.isEmpty() ? scoreForCategoryAnnum.get(it) != null ? scoreForCategoryAnnum.get(it) < 1 ? scoreForCategoryAnnum.get(it).round(5) : scoreForCategoryAnnum.get(it).round(0) : 0 : 0}"
                            scoreIndex++
                        }
                        datasets = "${datasets}]},"
                        colorIndex++

                    }
                }
            }
            labels = "${labels}]"
            datasets = "${datasets}]"
            String pageAsString = g.render(template: "annualAccumulationChart", model: [datasets: datasets, labels: labels, childEntity: entity, indicator: indicator, unit: unit]).toString()
            return pageAsString
        } catch (e) {
            loggerUtil.error(log, "Error in rendering annualChart:", e)
            flashService.setErrorAlert("Error in rendering annualChart: ${e.message}", true)
        }
    }

    private String lifeCycleChartBreakdown(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap, List<String> colorTemplate, List<QuerySection> sections, Boolean allCategoryBreakDowns = Boolean.FALSE, Boolean comingFromCompare = Boolean.FALSE, String individualCategoryBreakdown, Question groupingQuestion, Map<String,Question> allClassificationQsMap, Map<String,List<String>> indicatorAddQs,String defaultFallbackClassification,boolean fallbackToSection, List<Document> nmdElementList = null) {
        try {
            String calculationRuleId = calculationRule.calculationRuleId
            String graphLabels
            List<String> typesForlabels = []
            String graphDatasets = ""
            Boolean showClassificationWarning = Boolean.FALSE
            String upstreamDbWarning = ""
            List<Dataset> nonDefined = []
            def now = System.currentTimeMillis()
            String groupedBy = groupingQuestion?.localizedQuestion ?: "classification"
            def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []
            if (indicator && entity) {
                Map<ResultCategory, Map<String, Double>> totalPerCategoryAndType = [:]
                List<Dataset> datasets = entity?.getDatasetsByIndicator(indicator.indicatorId, calculationResults)

                ResourceCache resourceCache = ResourceCache.init(datasets)

                Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults?.groupBy { it.resultCategoryId }

                resultCategories.each { ResultCategory category ->
                    Map<String, Double> impactsPerType = [:]
                    if (datasets) {
                        List<String> upstreamDbs = datasets?.collect({
                            resourceCache.getResource(it)
                        })?.findAll({ it?.upstreamDBClassified })?.collect({ it?.upstreamDBClassified })

                        //UpstreaDB warning for OPD, task 7082
                        if (calculationRuleId?.equalsIgnoreCase("ODP") && upstreamDbs && upstreamDbs.unique().size() > 1 && indicator?.multiDataWarning) {
                            upstreamDbWarning = "${message(code: "query.multiple_datasources")}: ${upstreamDbs.unique().toString().replace("[", "").replace("]", "")}. <a href='javascript:;' onclick='expandWarningUpstreamDB(this)'>${message(code: 'upstreamDB.warning_explanation')}</a><p class='hidden' style='font-weight: normal !important; color: #000000'>${message(code: 'upstreamDB.warning_explanation.expanded')}</p>"
                        }

                        List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(category.resultCategoryId)

                        String fallbackString = message(code: 'other_classification')
                        for (Dataset dataset: datasets) {
                            Resource resource = resourceCache.getResource(dataset)
                            if (resource && !resource?.construction) {
                                // Performance issue: getGroupingTypeOfDataset is slow due to getLocalizedQuestionShort - locale is resolved for each question
                                String groupingType = datasetService.getGroupingTypeOfDataset(indicator,sections,dataset,fallbackString,groupingQuestion,indicatorAddQs,fallbackToSection, nmdElementList)

                                Double value = entity?.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, calculationRuleId, resultsForCategory, false)

                                if (value != null) {
                                    if (groupingType && !typesForlabels.contains(groupingType)) {
                                        typesForlabels.add(groupingType)
                                    }
                                    if (!impactsPerType.get(groupingType)) {
                                        impactsPerType.put(groupingType, value)

                                    } else {
                                        value = (value + impactsPerType.get(groupingType))
                                        impactsPerType.put(groupingType, value)
                                    }
                                }
                            }

                        }

                        if (impactsPerType && !impactsPerType.isEmpty()) {

                            totalPerCategoryAndType.put(category, impactsPerType)
                        }

                        if (nonDefined && !nonDefined.isEmpty()) {
                            showClassificationWarning = Boolean.TRUE
                        }
                    }
                }

                if (totalPerCategoryAndType && !totalPerCategoryAndType.isEmpty() && typesForlabels && !typesForlabels.isEmpty()) {
                    typesForlabels = typesForlabels.sort({groupingQuestionAnswer.indexOf(it) != -1 ? groupingQuestionAnswer.indexOf(it) : groupingQuestionAnswer.size()})
                    graphDatasets = graphDatasetsForLifeCycle(totalPerCategoryAndType, typesForlabels, colorTemplate)
                    graphLabels = generateGraphLabelsForLifeCycle(typesForlabels?.unique())

                }
            }
            String pageAsString = ""

            pageAsString = g.render(template: "allCategorybreakdowns", model: [graphDatasets            : graphDatasets ?: "[]", graphLabels: graphLabels ?: "[]", indicator: indicator,
                                                                               groupedBy:groupedBy,ruleId: calculationRule?.calculationRuleId,
                                                                               shortName: calculationRule?.localizedShortName ? calculationRule.localizedShortName : calculationRule?.calculationRule,
                                                                               calculationRuleName: calculationRule?.localizedName, childEntity: entity, comingFromCompare: comingFromCompare,
                                                                               showClassificationWarning: showClassificationWarning, countOfUndefined: nonDefined?.size(), upstreamDbWarning: upstreamDbWarning,
                                                                               unit: unit,individualCategoryBreakdown: individualCategoryBreakdown?:"", groupingQuestion: groupingQuestion,
                                                                               allClassificationQsMap:allClassificationQsMap,defaultFallbackClassification:defaultFallbackClassification]).toString()

            return pageAsString
        } catch(e){
            loggerUtil.error(log, "Error in rendering lifeCycleChartBreakdown:", e)
            flashService.setErrorAlert("Error in rendering lifeCycleChartBreakdown: ${e.message}", true)
        }

    }

    private String bubbleChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap, List<String> colorTemplate, List<QuerySection> sections) {
        try {
            List<String> calculationRuleIds = calculationRules?.collect { it.calculationRuleId }
            String calculationRuleId = calculationRule.calculationRuleId ?: calculationRuleIds[0]
            Map<String, Map<String, Double>> resourceTypeSubTypeResult = [:]
            def graphDataset = ""
            Double threshold = 0
            if (entity && indicator && calculationRules) {
                if (calculationRule) {

                    Double totalResult = entity.getTotalResult(indicator.indicatorId, calculationRuleId)
                    if (totalResult) {
                        threshold = totalResult * 0.005
                    }

                    ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
                    ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()

                    List<CalculationResult> resultsForCalculationRule = calculationResults?.findAll{ it.calculationRuleId == calculationRuleId }
                    Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults?.groupBy { it.resultCategoryId }

                    resultCategories?.each { ResultCategory resultCategory ->
                        List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(resultCategory.resultCategoryId)
                        List<Dataset> impactPerSubTypeDatasets = entity.getDatasetsForResultCategoryExpand(indicator.indicatorId, resultCategory.resultCategoryId, resultsForCategory, false)
                        if (impactPerSubTypeDatasets) {
                            for (Dataset d: impactPerSubTypeDatasets) {
                                Resource r = resourceCache.getResource(d)
                                if (r) {
                                    Double value = entity?.getResultForDataset(d.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRuleId, resultsForCalculationRule, false)
                                    if (value && value > 0) {

                                        String resourceLocalizedName = resourceTypeService.getLocalizedName(resourceTypeCache.getResourceType(r))
                                        String resourceType = resourceLocalizedName ?: g.message(code: 'other_resource_type')

                                        ResourceType resourceSubType = resourceTypeCache.getResourceSubType(r)
                                        String subTypeShortName = resourceTypeService.getLocalizedShortName(resourceSubType)
                                        String subTypeName = resourceTypeService.getLocalizedName(resourceSubType)
                                        String subType = subTypeShortName ?: subTypeName ?: g.message(code: 'other_resource_sub_type')
                                        def resultForTypeExit = resourceTypeSubTypeResult?.get(resourceType)
                                        if (resultForTypeExit) {
                                            def resultForSubTypeExit = resourceTypeSubTypeResult?.get(resourceType)?.get(subType)
                                            if (resultForSubTypeExit) {
                                                resultForSubTypeExit += value
                                            } else {
                                                resultForSubTypeExit = value
                                            }
                                            resultForTypeExit.put(subType, resultForSubTypeExit)
                                            resourceTypeSubTypeResult.put(resourceType, resultForTypeExit)
                                        } else {
                                            Map<String, Double> intialMap = [:]
                                            intialMap.put(subType, value)
                                            resourceTypeSubTypeResult.put(resourceType, intialMap)
                                        }
                                    }

                                }

                            }
                        }

                    }
                    int index1 = 0
                    resourceTypeSubTypeResult.each { k, v ->
                        graphDataset = "${graphDataset}${index1 > 0 ? ',{name: ' : '{name: '} \"${k}\" , data: ["
                        int index2 = 0
                        v.each { subtype, result ->
                            graphDataset += "${index2 > 0 ? ',{' : '{'}"
                            graphDataset += "name: \"${subtype}\", value: ${result.round(2) > 0 ? result.round(2) : result.round(4)}}"
                            index2++
                        }
                        graphDataset += "]}"
                        index1++
                    }


                }
            }
            String pageAsString = g.render(template: "bubbleChartTypeSubtype", model: [graphDataset: graphDataset, unit: unit.toString(), graphCalculationRules: calculationRules, entity: entity, indicator: indicator, calculationRule: calculationRule, threshold: threshold, colorTemplate: colorTemplate as JSON]).toString()
            return pageAsString
        } catch(e){
            loggerUtil.error(log, "Error in rendering bubbleChart:", e)
            flashService.setErrorAlert("Error in rendering bubbleChart: ${e.message}", true)
        }
    }

    private String lifeCycleStageChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap, List<String> colorTemplate, List<QuerySection> sections) {
        try {
            String labels = "["
            int index = 0
            List <String> graphLabels = []
            String datasets = "["
            List<ResultCategory> categoriesWithScores = []
            Map<CalculationRule, Map<String, Double>> scoresForCalculationRule = [:]
            Map<String, Double> totalScoresForCalculationRule = [:]
            Map<String,List<Double>> mapAbsoluteValues = [:]
            if (resultCategories && calculationRules && entity && indicator && calculationResults) {

                Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }

                resultCategories?.each { ResultCategory category ->
                    boolean hasResult = false
                    List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(category.resultCategoryId)

                    for (CalculationRule calculationRuleItem in calculationRules) {
                        def result = resultsForCategory?.find({
                            calculationRuleItem.calculationRuleId.equals(it.calculationRuleId)
                        })?.result

                        if (result != null && result != 0) {
                            hasResult = true
                            break

                        }

                    }
                    if (hasResult) {
                        categoriesWithScores.add(category)
                    }
                }
                graphLabels = categoriesWithScores?.collect({it?.resultCategory + " "+ resultCategoryService.getLocalizedShortName(it)})

                Map<String, List<CalculationResult>> indicatorResultsPerCalculationRule = calculationResults.groupBy { it.calculationRuleId }

                calculationRules?.each { CalculationRule rule ->

                    Map<String, Double> scores = new LinkedHashMap<String, Double>()
                    Double totalScore = (Double) entity.getTotalResult(indicator?.indicatorId, rule.calculationRuleId, Boolean.FALSE)
                    if(totalScore){
                        totalScoresForCalculationRule.put(rule.localizedShortName,totalScore)
                    }

                    List<CalculationResult> resultsForCalculationRule = indicatorResultsPerCalculationRule?.getAt(rule.calculationRuleId)
                    categoriesWithScores?.each { ResultCategory resultCategory ->
                        Double score = entity.getResult(indicator.indicatorId, rule.calculationRuleId, resultCategory.resultCategoryId, resultsForCalculationRule, false)
                        if (score) {
                            scores.put(resultCategory.resultCategoryId, score)
                        } else {
                            score = 0
                            scores.put(resultCategory.resultCategoryId, score)
                        }
                    }

                    if (scores && !scores.isEmpty()) {
                        scoresForCalculationRule.put(rule, scores)
                    }

                }
                int colorIndex = 0
                scoresForCalculationRule.each { key, value ->
                    int scoreIndex = 0
                    if (colorIndex + 1 > colorTemplate.size()) {
                        colorIndex = 0
                    }
                    Double totalScoreForThisRule = totalScoresForCalculationRule.get(key.localizedShortName) ?: 0.0
                    if(totalScoreForThisRule){
                        datasets = "${datasets}{name: \"${key.localizedShortName ?: key.localizedName}\","
                        datasets = "${datasets}data: ["
                        categoriesWithScores.each { ResultCategory resultCategory ->
                            datasets = "${datasets}${scoreIndex > 0 ? ', ' : ''}${value && !value.isEmpty() ? value?.get(resultCategory.resultCategoryId) ? (value?.get(resultCategory.resultCategoryId) / totalScoreForThisRule * 100) : 0 : 0}"
                            scoreIndex++
                        }
                        datasets = "${datasets}]},"
                        colorIndex++
                    }

                }
                mapAbsoluteValues = scoresForCalculationRule?.collectEntries {[(it.key.localizedShortName ?: it.key.localizedName): it.value.collect({it.value}) as List<Double>]}
            }
            datasets = "${datasets}]"


            String pageAsString = g.render(template: "/entity/resultgraph", model: [graphDatasets: datasets, graphLabels: graphLabels, indicator: indicator, childEntity: entity,mapAbsoluteValues:mapAbsoluteValues, unit:unit,calculationRulesMap:calculationRulesMap,unitsMapToRule:unitsMapToRule]).toString()
            return pageAsString
        } catch(e){
            loggerUtil.error(log, "Error in rendering lifeCycleStageChart:", e)
            flashService.setErrorAlert("Error in rendering lifeCycleStageChart: ${e.message}", true)
        }
    }

    private String chartByImpactCategoryStackedByStage(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit,  Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections) {
         try {
             Map<String, Map<String, Double>> perRulePerCat = [:]
             List<String> labels = calculationRulesMap.collect { String shortName, String name -> shortName }
             List graphDatasets = []
             List<String> categoriesName = []
             Map<String,List<Double>> mapAbsoluteValues = [:]
             Map<String,Double> totalForRules = [:]

             Map<String, List<CalculationResult>> indicatorResultsPerCalculationRule = calculationResults?.groupBy { it.calculationRuleId }
             calculationRules?.each { CalculationRule calculationRuleItem ->
                 Double totalByRule = 0
                 Map<String, Double> perCat = [:]

                 List<CalculationResult> resultsForCalculationRule = indicatorResultsPerCalculationRule?.getAt(calculationRuleItem.calculationRuleId)
                 resultCategories?.each { ResultCategory resultCategory ->
                     Double resultForCat = resultsForCalculationRule?.find({
                         resultCategory.resultCategoryId.equals(it.resultCategoryId)
                     })?.result
                     if (resultForCat != null) {
                         totalByRule += Math.abs(resultForCat)
                     } else {
                         resultForCat = 0
                     }
                     String localizedShortName = resultCategoryService.getLocalizedShortName(resultCategory)
                     perCat.put("${resultCategory.resultCategory} ${localizedShortName}", resultForCat)
                     categoriesName.add("${resultCategory.resultCategory} ${localizedShortName}")
                 }
                 totalForRules.put(calculationRuleItem.localizedShortName,totalByRule)
                 Map<String, Double> perCatPercent = perCat?.collectEntries({ [(it?.key): it?.value] })
                 perRulePerCat.put(calculationRuleItem.localizedShortName, perCat)
             }
             categoriesName?.unique()?.each { String key ->
                 List<Double> resultForCategory = perRulePerCat?.collect { it.value.findAll({ key == it.key })?.collect { k, v -> (v / totalForRules.get(it.key))*100 } }?.toList()?.flatten()
                 List<Double> absoluteResultForCategory = perRulePerCat?.collect { it.value.findAll({ key == it.key })?.collect { k, v -> v } }?.toList()?.flatten()
                 graphDatasets.add(["name": key, "data": resultForCategory])
                 mapAbsoluteValues.put(key,absoluteResultForCategory)
             }
             String pageAsString = g.render(template: "/entity/chartByImpactCategoryStacked", model: [graphDatasets: graphDatasets, graphLabels: labels as JSON, indicator: indicator, unitsMapToRule:unitsMapToRule,calculationRulesMap:calculationRulesMap,mapAbsoluteValues:mapAbsoluteValues,childEntity: entity, unit: "%", colorTemplate: colorTemplate as JSON]).toString()
             return pageAsString
         } catch (e){
             loggerUtil.error(log, "Error in rendering chartByImpactCategoryStackedByStage:", e)
             flashService.setErrorAlert("Error in rendering chartByImpactCategoryStackedByStage: ${e.message}", true)
         }
    }

    private String chartRadialByementByDesign(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections, groupingQuestion, Map<String,Question> allClassificationQsMap, Map<String,List<String>> indicatorAddQs, String defaultFallbackClassification,boolean fallbackToSection, List<Document> nmdElementList = null) {
        try {
            Map<String, Map<String, Double>> perRulePerCat = [:]
            List<String> labels = calculationRulesMap.collect { String shortName, String name -> shortName }
            List graphDatasets = []
            List<String> categoriesName = []
            String fallbackString = message(code: 'other_classification')
            Map<String,Map<String,Double>> perRulePerClass = [:]
            Map<String,Map<String,Double>> perRulePerClassAbsoluteVal = [:]
            Map<String,Double> highestImpactPerRule = [:]
            Map<String,Double> mapAbsoluteValues = [:]
            String groupedBy = groupingQuestion?.localizedQuestion ?: "classification"
            def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []

            ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
            Map<String, List<CalculationResult>> indicatorResultsPerCalculationRule = calculationResults?.groupBy { it.calculationRuleId }
            calculationRules?.each { CalculationRule calculationRuleItem ->

                List<CalculationResult> calculationResultsForRule = indicatorResultsPerCalculationRule?.getAt(calculationRuleItem.calculationRuleId)?.findAll{ resultCategories*.resultCategoryId?.contains(it?.resultCategoryId) }
                // Double totalForRule = entity?.calculationTotalResults?.find({ indicator.indicatorId.equals(it?.indicatorId) })?.totalByCalculationRule?.get(calculationRuleItem.calculationRuleId) ?: 0
                Double highestPerRule = 0.0
                Map<String, Double> impactsPerType = [:]
                Set<Dataset> datasetsForRule = entity.getDatasetsByIndicator(indicator.indicatorId, calculationResultsForRule, false)
                if (datasetsForRule) {
                    for (Dataset dataset: datasetsForRule) {
                        Resource resource = resourceCache.getResource(dataset)
                        if (resource && !resource.construction) {
                            // Performance issue: getGroupingTypeOfDataset is slow due to getLocalizedQuestionShort - locale is resolved for each question
                            String groupingType = datasetService.getGroupingTypeOfDataset(indicator, sections, dataset, fallbackString, groupingQuestion,indicatorAddQs,fallbackToSection, nmdElementList)
                            Double value = entity?.getResultForDataset(dataset.manualId, indicator.indicatorId, null, calculationRuleItem.calculationRuleId, calculationResultsForRule, false) ?: 0
                            if (value != null) {
                                if (!impactsPerType.get(groupingType)) {
                                    impactsPerType.put(groupingType, value)

                                } else {
                                    value = (value + impactsPerType.get(groupingType))
                                    impactsPerType.put(groupingType, value)
                                }
                                if(highestPerRule < value){
                                    highestPerRule = value
                                    highestImpactPerRule.put(calculationRuleItem.localizedShortName,highestPerRule)
                                }
                            }
                            categoriesName.add(groupingType)

                        }

                    }
                }
                perRulePerClassAbsoluteVal.put(calculationRuleItem.localizedShortName, impactsPerType)
            }
            categoriesName = categoriesName?.sort({groupingQuestionAnswer.indexOf(it) != -1 ? groupingQuestionAnswer.indexOf(it) : groupingQuestionAnswer.size()})
            categoriesName?.unique()?.each { String key ->
                List<Double> resultForClass = []
                List<Double> absolutResultForClass = []
                perRulePerClassAbsoluteVal?.each {k,v->
                    Double highestForThisRule = highestImpactPerRule?.get(k)
                    if(highestForThisRule){
                        Double resForThisRule = v?.find({k1,v1 -> k1?.equalsIgnoreCase(key)})?.value ?: 0.0
                        if(resForThisRule < 0){
                            resForThisRule = 0.0
                        }
                        resultForClass.add(resForThisRule / highestForThisRule * 100)
                        absolutResultForClass.add(resForThisRule)
                    }

                }
                graphDatasets.add(["name": key, "data": resultForClass])
                mapAbsoluteValues.put(key,absolutResultForClass)
            }

            String pageAsString = g.render(template: "/entity/radialByElementByDesign", model: [groupedBy:groupedBy,calculationRule:calculationRule, calculationRulesMap:calculationRulesMap,
                                                                                                graphDatasets: graphDatasets, graphLabels: labels as JSON, indicator: indicator,unitsMapToRule:unitsMapToRule,
                                                                                                entity: entity, unit: "%", colorTemplate: colorTemplate as JSON,allClassificationQsMap:allClassificationQsMap,
                                                                                                mapAbsoluteValues:mapAbsoluteValues,groupingQuestion: groupingQuestion,defaultFallbackClassification:defaultFallbackClassification]).toString()
            return pageAsString
        } catch (e){
            loggerUtil.error(log, "Error in rendering chartRadialByementByDesign:", e)
            flashService.setErrorAlert("Error in rendering chartRadialByementByDesign: ${e.message}", true)
        }
    }

    private String chartByMaterialByImpactRuleStacked(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit,  Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap,List<String> colorTemplate, List<QuerySection> sections){
        try {
            Map<String, Map<String, Double>> perRulePerMat = [:]
            List<String> labels = calculationRulesMap.collect { String shortName, String name -> shortName }
            List graphDatasets = []
            List<String> categoriesName = []
            Map<String,List<Double>> mapAbsoluteValues = [:]
            Map<String,Double> totalForRules = [:]
            List<Dataset> datasets = entity.datasets?.toList()
            ResourceCache resourceCache = ResourceCache.init(datasets)
            List<Resource> allResources = datasets?.findResults { Dataset dataset ->
                Resource resource
                if (dataset?.resourceId) {
                    resource = resourceCache?.getResource(dataset)
                    resource = resource && !resource.construction ? resource : null
                }
                return resource
            }?.unique() ?: [] // Performance issue: unique is slow because resources are compared by optimiResourceService.getLocalizedName

            Map<Resource, List<String>> allResourcesUnfiltered = allResources
                    ?.collectEntries({ key ->
                        [(key): datasets?.findAll({ it?.resourceId == key?.resourceId })*.manualId ?: []]
                    })

            Map<Resource, List<String>> allResourcesWithExistingDatasets = [:]
            if(allResourcesUnfiltered) {
                Set<String> existingDatasetsInCalcResults = findExistingDatasets(calculationResults,
                        allResourcesUnfiltered.values().flatten() as Set<String>)
                allResourcesUnfiltered.each {resource, datasetIds ->
                    List<String> existingPerResource = datasetIds.findAll { existingDatasetsInCalcResults.contains(it) }
                    if(existingPerResource) {
                        allResourcesWithExistingDatasets.put(resource, existingPerResource)
                    }
                }
            }

            categoriesName = allResourcesWithExistingDatasets?.collect{optimiResourceService.getLocalizedName(it?.key)}

            Map<String, List<CalculationResult>> indicatorResultsPerCalculationRule = calculationResults?.groupBy { it.calculationRuleId }

            calculationRules?.each { CalculationRule calculationRuleItem ->
                if (calculationRuleItem) {
                    Double totalByRule = 0
                    Map<String, Double> perMat = [:]

                    List<CalculationResult> resultsForCalculationRule = indicatorResultsPerCalculationRule?.getAt(calculationRuleItem.calculationRuleId)

                    allResourcesWithExistingDatasets?.each { Resource resource, List<String> datasetManualIds ->
                        if (resource) {
                            Double valForMat = 0
                            datasetManualIds?.each { datasetId ->
                                Double resultForThis = entity.getResultForDataset(
                                        datasetId,
                                        indicator.indicatorId,
                                        null,
                                        calculationRuleItem.calculationRuleId,
                                        resultsForCalculationRule, false
                                )
                                if (resultForThis != null) {
                                    valForMat += resultForThis
                                }
                            }
                            totalByRule += Math.abs(valForMat)
                            perMat.put(optimiResourceService.getLocalizedName(resource), valForMat)
                        }
                    }
                    totalForRules.put(calculationRuleItem.localizedShortName,totalByRule)
                    perRulePerMat.put(calculationRuleItem.localizedShortName, perMat)
                }
            }

            categoriesName = categoriesName.unique()
            if(categoriesName?.size() > 15){
                categoriesName = materialListRender(perRulePerMat,totalForRules,5)

                if(categoriesName?.size() > 15){
                    categoriesName = materialListRender(perRulePerMat,totalForRules,10)
                }

            }
            categoriesName?.each { String key ->
                List<Double> resultForCategory
                List<Double> absoluteResultForCategory
                if(key == "other"){
                    resultForCategory = perRulePerMat?.collect {rule,map-> map?.findAll({ !categoriesName.contains(it.key) })?.collect {it.value}?.sum() / totalForRules.get(rule) * 100}
                    absoluteResultForCategory = perRulePerMat?.collect ({rule,map-> map?.findAll({ !categoriesName.contains(it.key) })?.collect {it.value}?.sum()})
                    key = message(code:"other_materials")
                } else {
                    resultForCategory = perRulePerMat?.collect { it.value.findAll({ key == it.key })?.collect { k, v -> v / totalForRules.get(it.key) * 100 } }?.toList()?.flatten()
                    absoluteResultForCategory = perRulePerMat?.collect { it.value.findAll({ key == it.key })?.collect { k, v -> v } }?.toList()?.flatten()
                }
                graphDatasets.add(["name": key, "data": resultForCategory])
                mapAbsoluteValues.put(key,absoluteResultForCategory)
            }
            String pageAsString = g.render(template: "/entity/chartByMatAndImpactStacked", model: [graphDatasets: graphDatasets, graphLabels: labels as JSON, indicator: indicator, unitsMapToRule:unitsMapToRule,calculationRulesMap:calculationRulesMap,mapAbsoluteValues:mapAbsoluteValues,childEntity: entity, unit: "%", colorTemplate: colorTemplate as JSON]).toString()
            return pageAsString
        } catch (e){
            loggerUtil.error(log, "Error in rendering chartByMatAndImpactStacked:", e)
            flashService.setErrorAlert("Error in rendering chartByMatAndImpactStacked: ${e.message}", true)
        }
    }

    /**
     * Finds target datasets within calculation results
     *
     * @param results - a list of {@link com.bionova.optimi.core.domain.mongo.CalculationResult}
     * @param datasetIds - a set of dataset ids
     * @return an intersection of dataset ids
     */
    private Set<String> findExistingDatasets(List<CalculationResult> results, Set<String> datasetIds) {
        Set<String> intersection = []
        if (results && datasetIds) {
            Set<String> calculatedDataSets = results
                    .collect { it.calculationResultDatasets?.keySet() }
                    .flatten() as Set<String>
            intersection = datasetIds.intersect(calculatedDataSets) as Set<String>
        }
        return intersection
    }

    private List<String> materialListRender(Map<String,Map<String,Double>> perRulePerMat,Map<String,Double> totalForRules, Integer percent){
        List<String> x = []
        totalForRules.each { ruleName, ruleVal ->
            Double cut = (ruleVal / 100 * percent)
            if(ruleVal){
                def map = perRulePerMat.get(ruleName)
                map?.each {k,v ->
                    // why Math.abs(v) - cut > 0 is valid but Math.abs(v) -cut >= 0 is not ??!!
                    if(Math.abs(v) - cut > 0){
                        x.add(k)
                    }
                }
            }

        }
        x << "other"
        return x.unique()
    }
    private String circularChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap, List<String> colorTemplate, List<QuerySection> sections) {
        try {
            String width = '750'
            String height = '350'
            List<Indicator> indicatorList = entity.indicatorIdsList
            List<String> chartIndex = ["redIn", "orgIn", "lgreenIn", "dgreenIn", "redOut", "orgOut", "lgreenOut", "dgreenOut", "yellowOut", "index", "materialReturnedIndex", "materialRecoveredIndex"]
            Map<String, Double> graphDataset = [:]
            Map<String, Double> scoreForCalculationRule = [:]
            Map<String, String> labelForGraph = [:]
            boolean draw = false
            boolean hasTool = true
            if (indicatorList && !indicatorList.contains(indicator?.indicatorId)) {
                hasTool = false
            }
            if (indicator && entity && calculationRules && hasTool) {
                Constants.CIRCULAR_GRAPH_RULE_LISTS.each { String rule ->
                    Double score = entity?.getTotalResult(indicator.indicatorId, rule)
                    if (score == null) {
                        score = 0
                    }
                    scoreForCalculationRule.put(rule, score)
                    def ruleObj = calculationRules.find({it?.calculationRuleId?.equalsIgnoreCase(rule)})
                    def label = ruleObj?.localizedGraphName ?: ruleObj?.localizedName
                    labelForGraph.put(rule, label)
                }
                Constants.CIRCULAR_GRAPH_RULE_WEIGHTED_LISTS.each { String key, List<String> rules ->
                    rules?.each { String rule ->
                        Double score = entity?.getTotalResult(indicator.indicatorId, rule)
                        if (score == null) {
                            score = 0
                        }
                        scoreForCalculationRule.put(rule, score)
                    }
                }


                if (scoreForCalculationRule) {
                    draw = true
                    Double total = entity?.getTotalResult(indicator.indicatorId, "mass")
                    if (total != null) {
                        Double renewableIn = (scoreForCalculationRule.get(Constants.RENEWABLE_IN) / total * 100).round(1)
                        Double recycleIn = (scoreForCalculationRule.get(Constants.RECYCLE_IN) / total * 100).round(1)
                        Double reuseIn = (scoreForCalculationRule.get(Constants.REUSE_IN) / total * 100).round(1)
                        Double reuseOut = (scoreForCalculationRule.get(Constants.REUSE_OUT) / total * 100).round(1)
                        Double recycleOut = (scoreForCalculationRule.get(Constants.RECYCLE_OUT) / total * 100).round(1)
                        Double recoveryOut = (scoreForCalculationRule.get(Constants.RECOVERY_OUT) / total * 100).round(1)
                        Double disposalOut = (scoreForCalculationRule.get(Constants.DISPOSAL_OUT) / total * 100).round(1)
                        Double nonRenewable = (scoreForCalculationRule.get(Constants.NON_RENEWABLE) / total * 100).round(1)
                        Double downCycling = (scoreForCalculationRule.get(Constants.DOWN_CYCLING) / total * 100).round(1)
                        Double materialRecovered = scoreForCalculationRule?.findAll {Constants.CIRCULAR_GRAPH_RULE_WEIGHTED_LISTS.get(Constants.MATERIAL_RECOVERED).contains(it.key)}?.values()?.sum()
                        Double materialRecoveredPercent = materialRecovered ? (materialRecovered / total * 100).round(1) : 0
                        Double materialReturned = scoreForCalculationRule?.findAll {Constants.CIRCULAR_GRAPH_RULE_WEIGHTED_LISTS.get(Constants.MATERIAL_RETURNED).contains(it.key)}?.values()?.sum()
                        Double materialReturnedPercent  = materialReturned ? (materialReturned / total * 100).round(1) : 0

                        chartIndex.each { label ->
                            Double result
                            switch (label) {
                                case "redIn":
                                    result = nonRenewable
                                    break
                                case "orgIn":
                                    result = renewableIn
                                    break
                                case "lgreenIn":
                                    result = recycleIn
                                    break
                                case "dgreenIn":
                                    result = reuseIn
                                    break
                                case "redOut":
                                    result = disposalOut
                                    break
                                case "orgOut":
                                    result = recoveryOut
                                    break
                                case "lgreenOut":
                                    result = recycleOut
                                    break
                                case "dgreenOut":
                                    result = reuseOut
                                    break
                                case "index":
                                    result = ((materialReturnedPercent + materialRecoveredPercent) / 2).round(0)
                                    break
                                case "yellowOut":
                                    result = downCycling
                                    break
                                case "materialReturnedIndex":
                                    result = materialReturnedPercent
                                    break
                                case "materialRecoveredIndex":
                                    result = materialRecoveredPercent

                            }
                            graphDataset.put(label, result)
                        }
                    }

                }
            }
            def graphDatasets = graphDataset as grails.converters.JSON
            def graphLabels = labelForGraph as grails.converters.JSON
            String pageAsString
            if (draw && hasTool) {
                pageAsString = g.render(template: "/entity/graphCircular", model: [draw: draw, graphDataset: graphDatasets, width: width, height: height, indicator: indicator, graphLabels: graphLabels]).toString()
            }
            return pageAsString
        } catch (e){
            loggerUtil.error(log, "Error in rendering circularChart:", e)
            flashService.setErrorAlert("Error in rendering circularChart: ${e.message}", true)
        }
    }

    private queryResultSideBar(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, List<String> colors, List<QuerySection> sections) {
        String pageAsString
        Double total
        def resultMapForResultCategory = [:]
        Map<String, Double> highestImpactMaterialScores = [:]
        List pieGraphDatasetForTypes = []
        String impactsText
        Boolean drawTexts
        List<Resource> resourceList = []

        if (entity && indicator && calculationRule && resultCategories && calculationResults) {
            if (["GWP", "traciGWP_kgCO2e", "traciGWP_tn", "GWP_tn", "GWP_failover"].contains(calculationRule.calculationRuleId)) {
                drawTexts = Boolean.TRUE
            }
            Boolean showConstructionImpact = indicator.showConstructionImpact
            total = entity.getTotalResult(indicator?.indicatorId, calculationRule?.calculationRuleId)

            ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
            Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }

            resultCategories?.each { ResultCategory resultCategory ->

                List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(resultCategory.resultCategoryId)
                Double score = entity.getResult(indicator.indicatorId, calculationRule.calculationRuleId, resultCategory.resultCategoryId, resultsForCategory, false)
                String resultCategoryIdForGraph = resultCategory.resultCategory + ' ' + resultCategoryService.getLocalizedShortName(resultCategory)

                if (score) {
                    resultMapForResultCategory.put(resultCategoryIdForGraph, score)
                }
                List<Dataset> datasets = entity?.getDatasetsForResultCategoryExpand(indicator.indicatorId, resultCategory?.resultCategoryId, calculationResults)
                if (datasets) {
                    for (Dataset dataset: datasets) {
                        Resource resource
                        if(showConstructionImpact && dataset.parentConstructionId){
                            resource = resourceList.find({it.constructionId == dataset.parentConstructionId})
                            if(!resource){
                                resource = optimiResourceService.getResourceByConstructionId(dataset.parentConstructionId)
                            }
                        } else if(!showConstructionImpact || !dataset.parentConstructionId){
                            resource = resourceCache.getResource(dataset)
                        }
                        if (resource) {
                            Double scoreByResource = entity.getResultForDataset(dataset.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRule.calculationRuleId, calculationResults)
                            if (scoreByResource != null && !scoreByResource.isNaN()) {
                                Double hasResult = highestImpactMaterialScores.get(resource.resourceId)
                                if (hasResult) {
                                    highestImpactMaterialScores.put(resource.resourceId, hasResult + scoreByResource)
                                } else {
                                    highestImpactMaterialScores.put(resource.resourceId, scoreByResource)
                                }
                                if(!resourceList.contains(resource)){
                                    resourceList.push(resource)
                                }

                            }
                        }
                    }
                }
            }
            impactsText = impactsInTextFormat(calculationRule.calculationRuleId.toString(), indicator.indicatorId.toString(), entity.id.toString(), Boolean.TRUE)
            highestImpactMaterialScores = highestImpactMaterialScores?.sort({ -it.value })?.take(5)
            if (highestImpactMaterialScores && total) {
                Double totalOfOtherMaterial = total - highestImpactMaterialScores.collect({ it.value })?.sum()
                if (totalOfOtherMaterial) {
                    String otherMaterials = message(code: "other_materials")
                    highestImpactMaterialScores.put((otherMaterials), totalOfOtherMaterial)
                }
            }
            if (resultMapForResultCategory) {
                pieGraphDatasetForTypes = resultMapForResultCategory?.findAll({ it.value > 0 })?.collect { ['name': it.key, 'y': it.value] } ?: []

            }
        }
        pageAsString = g.render(template: "/query/resultBarForQueryPage", model: [drawTexts: drawTexts, impactsText: impactsText, resourceList: resourceList, highestImpactMaterialScores: highestImpactMaterialScores, pieGraphDatasetForTypes: pieGraphDatasetForTypes as JSON, entity: entity, indicator: indicator, calculationRule: calculationRule, total: total, calculationRules: calculationRules, unit: unit, colors: colors]).toString()
        return pageAsString
    }

    private String treeMapChart(Indicator indicator, Entity entity, Entity parent, List<CalculationRule> calculationRules, CalculationRule calculationRule, List<ResultCategory> resultCategories, List<CalculationResult> calculationResults, String unit, Map<String,String> unitsMapToRule,Map<String,String>calculationRulesMap, List<String> colorTemplate, List<QuerySection> sections, List<CalculationResult> allCalculationResults) {
        String pageAsString = ""
        Boolean drawTexts = Boolean.TRUE
        List<String> calculationRuleIds = calculationRules?.collect { it.calculationRuleId }
        String calculationRuleId = calculationRule.calculationRuleId ?: calculationRuleIds[0]
        Map<String, Map<String, Double>> resourceTypeSubTypeResult = [:]
        Map<String, Map<String, Double>> resourceStageSubTypeResult = [:]
        def graphDataset = ""
        def graphDatasetForStage = ""
        Double threshold = 0
        if (entity && indicator && calculationRules) {
            if (calculationRule) {

                Double totalResult = entity.getTotalResult(indicator.indicatorId, calculationRuleId)
                if (totalResult) {
                    threshold = totalResult * 0.005
                }

                ResourceCache resourceCache = ResourceCache.init(entity.datasets as List)
                ResourceTypeCache resourceTypeCache = resourceCache?.getResourceTypeCache()
                Map<String, List<CalculationResult>> indicatorResultsPerResultCategory = calculationResults.groupBy { it.resultCategoryId }

                resultCategories?.each { ResultCategory resultCategory ->

                    List<CalculationResult> resultsForCategory = indicatorResultsPerResultCategory?.getAt(resultCategory.resultCategoryId)
                    List<Dataset> impactPerSubTypeDatasets = entity.getDatasetsForResultCategoryExpand(indicator.indicatorId, resultCategory.resultCategoryId, resultsForCategory, false)
                    if (impactPerSubTypeDatasets) {
                        for (Dataset d: impactPerSubTypeDatasets) {
                            Resource r = resourceCache.getResource(d)
                            if (r) {
                                Double value = entity?.getResultForDataset(d.manualId, indicator.indicatorId, resultCategory.resultCategoryId, calculationRuleId, resultsForCategory, false)
                                if (value && value > 0) {
                                    String resourceLocalizedName = resourceTypeService.getLocalizedName(resourceTypeCache.getResourceType(r))
                                    String resourceType = resourceLocalizedName ?: g.message(code: 'other_resource_type')

                                    ResourceType resourceSubType = resourceTypeCache.getResourceSubType(r)
                                    String subTypeShortName = resourceTypeService.getLocalizedShortName(resourceSubType)
                                    String subTypeName = resourceTypeService.getLocalizedName(resourceSubType)
                                    String subType = subTypeShortName ?: subTypeName ?: g.message(code: 'other_resource_sub_type')
                                    def resultForTypeExit = resourceTypeSubTypeResult?.get(resourceType)
                                    if (resultForTypeExit) {
                                        def resultForSubTypeExit = resourceTypeSubTypeResult?.get(resourceType)?.get(subType)
                                        if (resultForSubTypeExit) {
                                            resultForSubTypeExit += value
                                        } else {
                                            resultForSubTypeExit = value
                                        }
                                        resultForTypeExit.put(subType, resultForSubTypeExit)
                                        resourceTypeSubTypeResult.put(resourceType, resultForTypeExit)
                                    } else {
                                        Map<String, Double> intialMap = [:]
                                        intialMap.put(subType, value)
                                        resourceTypeSubTypeResult.put(resourceType, intialMap)
                                    }
                                    String resultCategoryIdForGraph = resultCategory.resultCategory + ' ' + resultCategoryService.getLocalizedShortName(resultCategory)
                                    def resultForStageExit = resourceStageSubTypeResult?.get(resultCategoryIdForGraph)
                                    if (resultForStageExit) {
                                        def resultForSubTypeStageExit = resourceStageSubTypeResult?.get(resultCategoryIdForGraph)?.get(subType)
                                        if (resultForSubTypeStageExit) {
                                            resultForSubTypeStageExit += value
                                        } else {
                                            resultForSubTypeStageExit = value
                                        }
                                        resultForStageExit.put(subType, resultForSubTypeStageExit)
                                        resourceStageSubTypeResult.put(resultCategoryIdForGraph, resultForStageExit)
                                    } else {
                                        Map<String, Double> intialMap1 = [:]
                                        intialMap1.put(subType, value)
                                        resourceStageSubTypeResult.put(resultCategoryIdForGraph, intialMap1)
                                    }
                                }

                            }

                        }
                    }

                }

                graphDataset = renderGraphForTreemapChart(resourceTypeSubTypeResult, colorTemplate, true)
                graphDatasetForStage = renderGraphForTreemapChart(resourceStageSubTypeResult, colorTemplate, true)

            }
        }
        pageAsString = g.render(template: "/entity/treeMap", model: [graphDataset: graphDataset, graphDatasetForStage: graphDatasetForStage, unit: unit.toString(), graphCalculationRules: calculationRules, entity: entity, indicator: indicator, calculationRule: calculationRule]).toString()
        return pageAsString
    }

    private String renderGraphForTreemapChart(Map map, List<String> colorTemplate, Boolean drilldown = Boolean.FALSE){
        String graphForChart = "["
        int index1 = 0
        int indexForColor = 0
        map.each { k, v ->
            if (indexForColor >= colorTemplate.size()) {
                indexForColor = 0
            }
            if (drilldown) {

                graphForChart = "${graphForChart}${index1 > 0 ? ',{id: ' : '{id: '} \"${k}\" ,name:\"${k}\", color:  \'${colorTemplate[indexForColor]}\'}"
                int index2 = 0

                v.each { subtype, result ->
                    graphForChart += "${index2 > 0 ? ',{' : ',{'}"
                    graphForChart += "name: \"${subtype}\", parent: \"${k}\" ,value: ${result.round(2) > 0 ? result.round(2) : result.round(4)}}"
                    index2++
                }
            } else {
                graphForChart = "${graphForChart}${index1 > 0 ? ',{name: ' : '{name: '} \"${k}\" ,value:${v.round(2) > 0 ? v.round(2) : v.round(4)}, color:  \'${colorTemplate[indexForColor]}\'}"
            }

            index1++
            indexForColor++
        }
        graphForChart += "]"
        return graphForChart
    }

    private String mainEntBtnGroupClassList(Entity parent, Indicator indicator, CalculationRule calculationRule, Question selectedGroupingQuestion,Map<String,Question> mapAllGroupingQuestion, List<Indicator> drawableEntityIndicators,String defaultFallbackClassification ){
        List<String> classificationList = indicator?.classificationsMap
        String btnGroupAsList = g.render(template:"/entity/btnGroupMainEntClassList", model:[parent:parent,indicator: indicator,calculationRule:calculationRule,defaultFallbackClassification:defaultFallbackClassification,groupingQuestion:selectedGroupingQuestion,allGroupingQuestion: mapAllGroupingQuestion,indicatorIds:drawableEntityIndicators?.collect({it.indicatorId})]).toString()
        return btnGroupAsList
    }

    def renderMainEntityGraphs() {
        long now = System.currentTimeMillis()
        String indicatorId = request?.JSON?.indicatorId
        String entityId = request?.JSON?.entityId
        String selectedClassification = ""
        List<String> colors = Constants.CHART_COLORS
        Entity entity = entityService.readEntity(entityId)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
        List<CalculationRule> allRules = indicator?.getResolveCalculationRules(entity)
        List<CalculationRule> calculationRuleList = indicator?.graphCalculationRules ? allRules?.findAll({ indicator?.graphCalculationRules?.contains(it.calculationRuleId) }) : allRules?.findAll({ !it.hideInGraphs })
        String calculationRuleId = request?.JSON?.calculationRuleId ? request?.JSON.calculationRuleId : indicator?.displayResult ? indicator?.displayResult : calculationRuleList?.first()?.calculationRuleId
        CalculationRule calculationRule = calculationRuleList?.find({ CalculationRule calculationRule -> calculationRule.calculationRuleId.equals(calculationRuleId) })
        String entityClass = indicator?.indicatorUse?.equalsIgnoreCase("design") ? Constants.EntityClass.DESIGN.toString() : indicator?.indicatorUse?.equalsIgnoreCase("operating") ? Constants.EntityClass.OPERATING_PERIOD.toString() : null

        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
        List<String> indQueryIds = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity).collect{ it.queryId }
        List<Entity> availableDesigns = entityService.getChildEntitiesForShowing(entity, entityClass)?.findAll{ Entity design ->
            design?.isIndicatorReady(indicator, indQueryIds, true, true) && !design?.isHiddenDesign && !design.deleted
        }
        List<Entity> designs = availableDesigns?.findAll { !it.disabledIndicators?.contains(indicatorId) && !it?.isHiddenDesign } ?: []
        List<ResultCategory> resultCategories = indicator?.getGraphResultCategoryObjects(entity)
        List<String> drawableEntityIndicatorIds = request?.JSON?.indicatorIdList
        List<Indicator> drawableEntityIndicators = indicatorService.getIndicatorsByIndicatorIds(drawableEntityIndicatorIds, true)
        String fallbackString = g.message(code: 'other_classification')

        Entity parent = entityService.getEntityById(entity.parentEntityId)
        List<ResultCategory> allResultCategories = indicator?.getResolveResultCategories(parent)

        Map outputMap = [:]
        try {
            if (indicator && resultCategories && calculationRule && designs && entity && drawableEntityIndicators && availableDesigns && calculationRuleList) {

                Map<String, List<CalculationResult>> calculationResultsPerDesign = [:]
                Map<String, List<Dataset>> resolvedDatasetsPerDesign = [:]

                Query additionalQuestionQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
                List<String> classificationListForIndicator = indicator.classificationsMap
                Map<String,Question> additionalQuestions = classificationListForIndicator?.collectEntries {[(it):questionService.getQuestion(additionalQuestionQuery,it)]}
                String defaultClassQuesId = classificationListForIndicator?.size() > 0 ? classificationListForIndicator?.get(0) : ""
                selectedClassification = request?.JSON?.selectedClassification ?: defaultClassQuesId
                Question groupingQuestion = additionalQuestions?.get(selectedClassification)
                Map<String,List<String>> indicatorQueryAddQMap = indicator?.indicatorQueries?.collectEntries({[(it.queryId):it.additionalQuestionIds]})
                List<QuerySection> sections = indicator?.getQueries(entity)?.collectMany({ it.sections })?.findAll({ !it.sectionId?.equalsIgnoreCase("coreReference") })
                String defaultFallbackClassification = classificationListForIndicator?.size() == 1 && classificationListForIndicator?.contains(com.bionova.optimi.core.Constants.ORGANIZATION_CLASSIFICATION_ID) ? message(code: "default_class") : null

                Map<String, List<CalculationResult>> allResultsForIndicator = entityService.getCalculationResultsObjects(designs, indicator.indicatorId, calculationRule.calculationRuleId, null)

                designs.each { Entity design ->
                    List<CalculationResult> resultsForIndicator = allResultsForIndicator.get(design.id.toString())
                    calculationResultsPerDesign.put((design.id.toString()), resultsForIndicator)
                    List<Dataset> datasets = design.getDatasetsByIndicator(indicatorId, resultsForIndicator)

                    if (datasets) {
                        datasets.each {dataset ->
                            String groupingType = datasetService.getGroupingTypeOfDataset(indicator, sections, dataset,
                                    fallbackString, groupingQuestion,indicatorQueryAddQMap)
                            dataset.groupingType = groupingType
                        }
                    }
                    resolvedDatasetsPerDesign.put((design.id.toString()), datasets)
                }

                List<String> reportVisualItems = drawableEntityIndicators?.collect({ it.reportVisualisation })?.flatten()
                Boolean drawTotalChart = reportVisualItems?.contains("entityPageChart")
                Boolean entityByStageChart = reportVisualItems?.contains("entityByStageChart")
                Boolean entityElementChart = reportVisualItems?.contains("entityElementChart")
                Boolean entityCompareElementsChart = reportVisualItems?.contains("entityCompareElementsChart")
                Boolean entityByStageElementChart = reportVisualItems?.contains("entityByStageElementChart")
                if (drawTotalChart) {
                    String totalChartAsString = mainEntTotalResultsChart(entity, indicator, calculationRuleList, designs, colors)
                    if (totalChartAsString) {
                        outputMap.put("totalChart", totalChartAsString)
                    }
                }

                if (entityByStageChart) {
                    String stageChartAsString = mainEntStageChart(entity, resultCategories, indicator, calculationRuleList, designs, colors, calculationRule, allRules, calculationResultsPerDesign)
                    if (stageChartAsString) {
                        outputMap.put("stageChart", stageChartAsString)
                    }
                }

                if (entityElementChart) {
                    String stageClassChartAsString = mainEntClassDesGroupChart(entity, resultCategories, indicator,
                            calculationRuleList, designs, colors, calculationRule, allRules, calculationResultsPerDesign,
                            resolvedDatasetsPerDesign, groupingQuestion, allResultCategories, sections)
                    if (stageClassChartAsString) {
                        outputMap.put("stageClassChart", stageClassChartAsString)
                    }
                }

                if (entityCompareElementsChart) {
                    String classGroupChartAsString = mainEntClassGroupChart(entity, resultCategories, indicator, calculationRuleList, designs, colors, calculationRule, allRules, calculationResultsPerDesign, resolvedDatasetsPerDesign,groupingQuestion)
                    if (classGroupChartAsString) {
                        outputMap.put("classGroupChart", classGroupChartAsString)
                    }
                }

                if (entityByStageElementChart) {
                    String elementChartAsString = elementCompChart(entity, resultCategories, indicator, calculationRuleList,
                            designs, colors, calculationRule, drawableEntityIndicators, allRules, calculationResultsPerDesign,
                            resolvedDatasetsPerDesign, groupingQuestion, allResultCategories, sections)
                    if (elementChartAsString) {
                        outputMap.put("elementChart", elementChartAsString)
                    }
                }

                String btnGroupForClassListAsString = mainEntBtnGroupClassList(entity, indicator,calculationRule,groupingQuestion,additionalQuestions,drawableEntityIndicators,defaultFallbackClassification)
                if(btnGroupForClassListAsString) {
                    outputMap.put("classificationList", btnGroupForClassListAsString)
                }

                String btnGroupAsString = mainEntBtnGroup(entity, indicator, calculationRuleList, designs, colors, calculationRule, drawableEntityIndicators, availableDesigns)
                if (btnGroupAsString) {
                    outputMap.put("btnGroup", btnGroupAsString)
                }
            }

            if (indicator && calculationRule) {
                String fullName = "${indicatorService.getLocalizedName(indicator) + ", " + calculationRule.localizedName}"
                String name = "${abbr(value: fullName, maxLength: 50)}${fullName.length() > 50 ? '...' : ''}"
                outputMap.put("chartMainTitle", fullName)
                outputMap.put("chartShortTitle", name)
                outputMap.put("isOperatingPeriod", indicator?.indicatorUse?.equalsIgnoreCase("operating"))
            }
        } catch (e){
            loggerUtil.error(log, "Error in rendering renderMainEntityGraphs:", e)
            flashService.setErrorAlert("Error in rendering renderMainEntityGraphs: ${e.message}", true)
        }
        render([output: outputMap, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private String mainEntTotalResultsChart(Entity entity, Indicator indicator, List<CalculationRule> calculationRules, List<Entity> designs, List<String> colors) {
        String indicatorId = indicator.indicatorId

        Denominator denominator = indicator?.getDisplayDenominator()
        String graphDatasets = "["
        String graphLabels = "["
        String pageAsString
        Map<String, Map> scoresByDesigns = [:]
        Map unitsMapToRule = [:]
        def mapByRuleByDesign = [:]
        String yAxisTitle = ""

        if (calculationRules && designs && indicator) {
            List<String> units = calculationRules.collect({ calculationRuleService.getLocalizedUnit(it, indicator) })?.toList()?.unique()?.findAll({ it != "" })
            unitsMapToRule = calculationRules.collectEntries({ [(it?.localizedShortName) : (calculationRuleService.getLocalizedUnit(it, indicator) ?: message(code:'equivalent_unit'))]})
            if (units?.size() == 1) {
                yAxisTitle = "% - ${units[0]}"
            } else if (!units) {
                CalculationRule unitAsValueRefRule = calculationRules.find({ it.unitAsValueReference })
                if (entity && unitAsValueRefRule) {
                    yAxisTitle = "% - ${valueReferenceService.getValueForEntity(unitAsValueRefRule.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: indicatorService.getDisplayUnit(indicator)}"
                }
            }

            Map<String, Double> highestScoreByRule = [:]
            Map<String, Double> existingPercentualScores

            designs = designs?.findAll { !it.disabledIndicators?.contains(indicatorId) }

            designs.each { Entity design ->

                Map<String, Double> scoresForDesignByRule = new LinkedHashMap<String, Double>()

                calculationRules.each { CalculationRule calculationRule ->
                    Double score
                    if (denominator) {
                        score = design?.getTotalResultByNonDynamicDenominator(indicatorId, calculationRule.calculationRuleId, denominator.denominatorId) ?: 0
                    } else {
                        score = design?.getTotalResult(indicatorId, calculationRule.calculationRuleId) ?: 0
                    }

                    if (score != null) {
                        scoresForDesignByRule.put(calculationRule.calculationRule, score)
                        Double existingHighest = highestScoreByRule.get(calculationRule.calculationRule)

                        if (existingHighest == null || score > existingHighest) {
                            highestScoreByRule.put(calculationRule.calculationRule, score)

                        }

                        if (!scoresForDesignByRule.isEmpty()) {
                            scoresByDesigns.put(design?.operatingPeriodAndName, scoresForDesignByRule)

                        }
                    }
                }
            }
            calculationRules.each { CalculationRule calculationRule ->
                Map scoreByRule = scoresByDesigns.collectEntries { [(it.key): it.value.find({ k, v -> calculationRule.calculationRule?.equalsIgnoreCase(k) })?.value ?: 0] }
                mapByRuleByDesign.put(calculationRule.calculationRule, scoreByRule.sort({ -it.value }))
            }
            int labelIndex = 0

            calculationRules.each { CalculationRule rule ->
                graphLabels = "${graphLabels}${labelIndex > 0 ? ',' : ''}\"${rule.localizedShortName ?: rule.localizedName}\""
                labelIndex++
            }

        }
        graphLabels = "${graphLabels}]"
        Map maxValueForRule = mapByRuleByDesign.collectEntries { [(it.key): it.value?.values()?.toList()?.first() ?: 0] }
        List mapForRender = scoresByDesigns.collect({ [name: it.key, data: it.value.collect { k, v -> (v / maxValueForRule.get(k) * 100) ?: 0 } as List] })
        def mapAbsoluteValues = scoresByDesigns.collectEntries({ [(it.key): it.value.collect { k, v -> v ?: 0 } as List ]})
        Map calculationRulesMap = calculationRules.collectEntries { CalculationRule rule ->
            String localizedName = rule.localizedName

            [(rule.localizedShortName ?: localizedName): localizedName]
        }

        pageAsString = g.render(template: "/entity/graphByIndicator", model: [graphDatasets: graphDatasets, graphLabels: graphLabels, yAxisTitle: yAxisTitle, indicator: indicator, entity: entity,
                                                                              drawBars: true, mapForRender: mapForRender, maxValueForRule: maxValueForRule, scoresByDesigns: scoresByDesigns, unitsMapToRule:unitsMapToRule,
                                                                              mapByRuleByDesign: mapByRuleByDesign, colors: colors, calculationRulesMap: calculationRulesMap,mapAbsoluteValues:mapAbsoluteValues]).toString()
        return pageAsString
    }

    private String mainEntClassDesGroupChart(Entity entity, List<ResultCategory> resultCategories,
                                             Indicator indicator, List<CalculationRule> calculationRules,
                                             List<Entity> designs, List<String> colors, CalculationRule calculationRule,
                                             List<CalculationRule> allRules, Map<String, List<CalculationResult>> calculationResultsPerDesign,
                                             Map<String, List<Dataset>> resolvedDatasetsPerDesign, Question groupingQuestion,
                                             List<ResultCategory> allResultCategories, List<QuerySection> sections) {

        List<String> typesForlabels = []
        String groupedBy
        String graphDatasets = "["
        String graphLabels = "["
        List<Dataset> nonDefined = []
        int colorIndex = 0
        def color
        Boolean drawable = Boolean.FALSE
        Boolean showWarningChart = Boolean.FALSE
        String unit
        Map mapForRender = [:]
        int classCount = 0
        String fallbackString = g.message(code: 'other_classification')
        String pageAsString
        def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []

        try{
            if (entity && indicator && designs && calculationRule) {
                Map<String, Map<String, Double>> totalPerDesignAndType = [:]

                CalculationRule unitAsValueRefRule = allRules?.find({ it.unitAsValueReference })

                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }
                List<IndicatorQuery> indicatorQueriesWithAdditionalQuestions = indicator.indicatorQueries?.findAll({ it.additionalQuestionIds })
                if (indicatorQueriesWithAdditionalQuestions) {
                    showWarningChart = Boolean.TRUE
                }

                designs.each { Entity childEntity ->
                    List<CalculationResult> resultsForIndicator = calculationResultsPerDesign.get(childEntity.id.toString())
                    List<Dataset> datasets = resolvedDatasetsPerDesign.get(childEntity.id.toString())
                    Map<String, Double> impactsPerType = [:]
                    if (resultCategories) {
                        resultCategories.each { ResultCategory category ->
                            if (datasets) {
                                ResourceCache resourceCache = ResourceCache.init(datasets)
                                for (Dataset dataset: datasets) {
                                    Resource resource = resourceCache.getResource(dataset)
                                    if (resource && !resource.construction) {
                                        String groupingType = dataset.groupingType

                                        if (!groupingType.equals(fallbackString) && !groupingType.equals("Not defined")) {
                                            classCount++
                                        }
                                        Double value = childEntity?.getResultForDataset(dataset.manualId, indicator.indicatorId,
                                                category.resultCategoryId, calculationRule.calculationRuleId, resultsForIndicator)
                                        if (groupingType && !typesForlabels.contains(groupingType)) {
                                            typesForlabels.add(groupingType)
                                        }

                                        if (value != null) {
                                            if (!impactsPerType.get(groupingType)) {
                                                impactsPerType.put(groupingType, value)
                                            } else {
                                                value = (value + impactsPerType.get(groupingType))
                                                impactsPerType.put(groupingType, value)
                                            }
                                            drawable = Boolean.TRUE
                                        }
                                    }
                                }
                            }

                            boolean isUpdated = enrichGraphWithValuableCategories(childEntity.id, category, allResultCategories, sections,
                                    calculationResultsPerDesign, impactsPerType, typesForlabels)
                            if(!drawable) {
                                drawable = isUpdated
                            }
                        }

                        if (impactsPerType && !impactsPerType.isEmpty()) {
                            totalPerDesignAndType.put(childEntity.operatingPeriodAndName, impactsPerType)
                            graphLabels = "${graphLabels} \"${childEntity.operatingPeriodAndName}\","
                        }
                    }
                }
                typesForlabels = typesForlabels?.sort({groupingQuestionAnswer.indexOf(it) != -1 ? groupingQuestionAnswer.indexOf(it) : groupingQuestionAnswer.size()})
                totalPerDesignAndType.each { design, k ->
                    Map<String, Double> mapForDesign = [:]
                    typesForlabels.each { String label ->
                        Double value = k.get(label) ?: 0.0
                        mapForDesign.put(label, value)
                    }
                    mapForRender.put(design, mapForDesign)
                }

                typesForlabels.each { String label ->
                    List<Double> datasets = []
                    if (colorIndex + 1 > colors.size()) {
                        colorIndex = 0
                    }

                    color = colors.get(colorIndex)
                    graphDatasets += "${typesForlabels.indexOf(label) > 0 ? ',' : ''}{name:  \"${label}\","

                    totalPerDesignAndType.each {
                        def d = it.value.get(label)
                        if (d) {
                            datasets.add(d)
                        } else {
                            d = 0.0
                            datasets.add(d)
                        }

                    }
                    graphDatasets = "${graphDatasets} data: ${datasets}}"
                    colorIndex++
                }
            }

            graphLabels = "${graphLabels}]"
            graphDatasets = "${graphDatasets}]"
            pageAsString = g.render(template: "/entity/groupMainCompChart", model: [showWarningChart    : showWarningChart,
                                                                                    classCount          : classCount,
                                                                                    graphLabels         : graphLabels,
                                                                                    graphDatasets       : graphDatasets,
                                                                                    indicator           : indicator,
                                                                                    unit                : unit,
                                                                                    calculationRulesList: calculationRules,
                                                                                    drawable            : drawable,
                                                                                    calculationRule     : calculationRule,
                                                                                    entity              : entity,
                                                                                    mapForRender        : mapForRender, colors: colors]).toString()

        } catch(e){
            loggerUtil.error(log, "Error in rendering groupMainCompChart:", e)
            flashService.setErrorAlert("Error in rendering groupMainCompChart: ${e.message}", true)
        }
           return pageAsString
    }

    private String mainEntStageChart(Entity entity, List<ResultCategory> resultCategories, Indicator indicator, List<CalculationRule> calculationRules,
                                     List<Entity> designs, List<String> colors, CalculationRule calculationRule, List<CalculationRule> allRules,
                                     Map<String, List<CalculationResult>> calculationResultsPerDesign) {

        List<License> validLicenses = licenseService.getValidLicensesForEntity(entity)
        List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)
        List<String> indQueryIds = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity).collect{ it.queryId }
        List<Entity> designList = entityService.getChildEntitiesForShowing(entity, EntityClass.DESIGN.getType()).findAll{ Entity design ->
            design?.isIndicatorReady(indicator, indQueryIds, true, true)
        }
        String unit
        List graphDatasets = []
        Map<String, Map> mapToRender = [:]
        List<String> categoryWithScore = []
        String pageAsString
        try {
            if (entity && calculationRule && designs && indicator && resultCategories) {
                CalculationRule unitAsValueRefRule = allRules?.find({ it.unitAsValueReference })

                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }

                designs.each { Entity design ->
                    Map<String, Double> scoresForDesignByCategory = [:]
                    List<CalculationResult> resultsByIndicator = calculationResultsPerDesign.get(design.id.toString())
                    resultCategories.each { ResultCategory resultCategory ->
                        Double score = design.getResult(indicator.indicatorId, calculationRule?.calculationRuleId, resultCategory.resultCategoryId, resultsByIndicator)
                        String key = resultCategory.resultCategory.toString() + " " + resultCategoryService.getLocalizedShortName(resultCategory)
                        if (score == null) {
                            score = 0
                        }
                        if (score != null) {
                            categoryWithScore.add(key)
                        }
                        scoresForDesignByCategory.put(key, score)
                    }
                    mapToRender.put(design.operatingPeriodAndName, scoresForDesignByCategory)
                }
                categoryWithScore = categoryWithScore?.unique()
                categoryWithScore?.each { String key ->
                    List<Double> resultForCategory = mapToRender?.collect { it.value.findAll({ key == it.key })?.collect { k, v -> v } }?.toList()?.flatten()
                    graphDatasets.add(["name": key, "data": resultForCategory])
                }

            }
            List graphLabels = designs?.collect({ it.operatingPeriodAndName }) as List
            pageAsString = g.render(template: "/entity/entityMainCompChart", model: [graphDatasets : graphDatasets, graphLabels: graphLabels as JSON,
                                                                                     mapForRender  : mapToRender, indicator: indicator,
                                                                                     entity        : entity, calculationRulesList: calculationRules, calculationRule: calculationRule, unit: unit,
                                                                                     designToSelect: designList?.findAll({ it.calculationTotalResults?.find({ it.indicatorId == indicator.indicatorId }) })]).toString()

        } catch(e){
            loggerUtil.error(log, "Error in rendering entityMainCompChart:", e)
            flashService.setErrorAlert("Error in rendering entityMainCompChart: ${e.message}", true)
        }
           return pageAsString
    }

    private String elementCompChart(Entity entity, List<ResultCategory> resultCategories, Indicator indicator,
                                    List<CalculationRule> calculationRules, List<Entity> designs,
                                    List<String> colors, CalculationRule calculationRule, List<Indicator> drawableEntityIndicators,
                                    List<CalculationRule> allRules, Map<String, List<CalculationResult>> calculationResultsPerDesign,
                                    Map<String, List<Dataset>> resolvedDatasetsPerDesign, Question groupingQuestion,
                                    List<ResultCategory> allResultCategories, List<QuerySection> sections) {

        List<String> designNamesForlabel = []
        List<String> classificationList = []
        String unit
        Map<String, Map<String, Map<String, Double>>> mapByCategoryThenType = [:]
        Map<String, Map<String, Map<String, Double>>> mapByClassThenCategory = [:]
        String fallbackString = g.message(code: 'other_classification')
        List<ResultCategory> graphResultCategories = []
        String pageAsString
        def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []
        try {
            if (entity && indicator && designs && calculationRule) {
                resultCategories = indicator?.getGraphResultCategoryObjects(entity)

                CalculationRule unitAsValueRefRule = allRules?.find({ it.unitAsValueReference })

                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }

                resultCategories?.each { ResultCategory category ->
                    Map<String, Map<String, Double>> impactPerCategoryPerDesign = [:]
                    String keyForCategoryMap = "${category.resultCategory} ${resultCategoryService.getLocalizedShortName(category)}"
                    boolean categoryWithResult = false
                    designs?.each { Entity design ->
                        String keyForDesignMap = "${design.operatingPeriodAndName}"
                        List<CalculationResult> resultsForIndicator = calculationResultsPerDesign.get(design.id.toString())
                        List<Dataset> datasets = resolvedDatasetsPerDesign.get(design.id.toString())
                        if (datasets) {
                            Map<String, Double> impactsPerCatPerDesignPerClass = [:]
                            ResourceCache resourceCache = ResourceCache.init(datasets)
                            for (Dataset dataset: datasets) {
                                Resource resource = resourceCache.getResource(dataset)
                                if (resource && !resource.construction) {
                                    String groupingType = dataset.groupingType
                                    classificationList.add(groupingType)
                                    Double value = design?.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, calculationRule.calculationRuleId, resultsForIndicator)
                                    if (value) {
                                        categoryWithResult = true
                                    } else {
                                        value = 0
                                    }
                                    if (groupingType && !impactsPerCatPerDesignPerClass?.get(groupingType)) {
                                        impactsPerCatPerDesignPerClass.put(groupingType, value)
                                    } else {
                                        Double existing = impactsPerCatPerDesignPerClass.get(groupingType) ?: 0
                                        impactsPerCatPerDesignPerClass.put(groupingType, (existing + value))
                                    }
                                }
                            }
                            impactPerCategoryPerDesign.put(keyForDesignMap, impactsPerCatPerDesignPerClass)
                        }

                        if(!impactPerCategoryPerDesign.get(keyForDesignMap)) {
                            impactPerCategoryPerDesign.put(keyForDesignMap, [:])
                        }
                        Map<String, Double> impactsPerCatPerDesignPerClass = impactPerCategoryPerDesign.get(keyForDesignMap)
                        boolean isUpdated = enrichGraphWithValuableCategories(design.id, category, allResultCategories, sections,
                                calculationResultsPerDesign, impactsPerCatPerDesignPerClass, classificationList)
                        if (!categoryWithResult) {
                            categoryWithResult = isUpdated
                        }
                    }

                    if (categoryWithResult) {
                        graphResultCategories.add(category)
                        mapByCategoryThenType.put(keyForCategoryMap, impactPerCategoryPerDesign)
                    }
                }
            }

            pageAsString = g.render(template: "/entity/designComparisonGraph", model: [drawableEntityIndicators  : drawableEntityIndicators,
                                                                                       indicator                 : indicator,
                                                                                       colors                    : colors as JSON,
                                                                                       unit                      : unit,
                                                                                       designs                   : designs,
                                                                                       designNames               : designs.collect({ it.operatingPeriodAndName }),
                                                                                       resultCategories          : graphResultCategories,
                                                                                       designNamesForlabel       : designNamesForlabel,
                                                                                       drawableEntityIndicatorIds: drawableEntityIndicators.collect({ it.indicatorId }) as JSON,
                                                                                       calculationRulesList      : calculationRules,
                                                                                       calculationRule           : calculationRule,
                                                                                       entity                    : entity,
                                                                                       mapByClassThenCategory    : mapByClassThenCategory,
                                                                                       mapByCategoryThenType     : mapByCategoryThenType,
                                                                                       classificationList        : classificationList?.unique()?.sort({groupingQuestionAnswer.indexOf(it) != -1 ? groupingQuestionAnswer.indexOf(it) : groupingQuestionAnswer.size()}),
                                                                                       colors                    : colors]).toString()

        } catch(e) {
            loggerUtil.error(log, "Error in rendering designComparisonGraph:", e)
            flashService.setErrorAlert("Error in rendering designComparisonGraph: ${e.message}", true)
        }
        return pageAsString
    }

    //adds data from categories that exist in calculation results but have no datasets
    private boolean enrichGraphWithValuableCategories(ObjectId entityId, ResultCategory category, List<ResultCategory> allResultCategories,
                                                      List<QuerySection> sections, Map<String, List<CalculationResult>> calculationResultsPerDesign,
                                                      Map<String, Double> impactsPerCat, List<String> graphLabels) {
        boolean isUpdated = false
        if (category?.virtual) {
            List<ResultCategory> subCategoriesWithNoDatasets = allResultCategories.findAll { it ->
                category.virtual.contains(it.resultCategoryId) && !it.data
            }
            if (subCategoriesWithNoDatasets) {
                log.info("Found valuable categories " +
                        "without datasets: ${subCategoriesWithNoDatasets*.resultCategoryId}")
                for (ResultCategory subCategory : subCategoriesWithNoDatasets) {
                    List<CalculationResult> results = calculationResultsPerDesign.get(entityId.toString())?.findAll {
                        it.resultCategoryId == subCategory.resultCategoryId
                    }
                    if (results) {
                        results = results.findAll { it.result }
                    }
                    String sectionId = subCategory.process?.get("replaceResource")?.get("sectionId")
                    if (sectionId && results) {
                        String groupingType = datasetService.resolveSectionLocalizedName(sections, sectionId, sectionId)
                        if (!impactsPerCat.get(groupingType)) {
                            impactsPerCat.put(groupingType, 0D)
                            graphLabels.add(groupingType)
                        }
                        Double existing = impactsPerCat.get(groupingType)
                        impactsPerCat.put(groupingType,
                                (existing + results.collect { it.result }.sum()))
                        isUpdated = true
                    }
                }
            }
        }
        return isUpdated
    }

    private String mainEntBtnGroup(Entity entity, Indicator indicator, List<CalculationRule> calculationRules, List<Entity> designs, List<String> colors, CalculationRule calculationRule, List<Indicator> drawableEntityIndicators, List<Entity> availableDesigns) {
        String pageAsString = g.render(template: "btnGroupMainEntChart", model: [availableDesigns: availableDesigns, drawableEntityIndicators: drawableEntityIndicators, drawableEntityIndicatorIds: drawableEntityIndicators?.collect({ it.indicatorId }) as List<String>, indicator: indicator, designs: designs, entity: entity, graphCalculationRules: calculationRules]).toString()
        return pageAsString
    }

    private String mainEntClassGroupChart(Entity entity, List<ResultCategory> resultCategories, Indicator indicator, List<CalculationRule> calculationRules,
                                          List<Entity> designs, List<String> colors, CalculationRule calculationRule, List<CalculationRule> allRules,
                                          Map<String, List<CalculationResult>> calculationResultsPerDesign, Map<String, List<Dataset>> resolvedDatasetsPerDesign, Question groupingQuestion) {
        Boolean drawable = Boolean.FALSE
        Boolean indicatorHasAddQs = groupingQuestion ? Boolean.TRUE : Boolean.FALSE
        String unit
        Map<String, List<String>> mapForGroupTypeTitles = ["byClass": [], "byQuestion": [], "bySection": []]
        Map<String, Map<String, Double>> mapByAdditionalQs = [:]
        Map<String, Map<String, Double>> mapByQueryQuestions = [:]
        Map<String, Map<String, Double>> mapBySections = [:]
        String defaultStatus = "bySection"
        String fallbackString = g.message(code: 'other_classification')
        String pageAsString
        def groupingQuestionAnswer = groupingQuestion?.choices?.collect({it.localizedAnswer}) ?: []
        try {
            if (entity && indicator && designs && calculationRule) {
                List<QuerySection> sections = indicator?.getQueries(entity)?.collectMany({ it.sections })?.findAll({ !it.sectionId?.equalsIgnoreCase("coreReference") })
                CalculationRule unitAsValueRefRule = allRules?.find({ it.unitAsValueReference })

                unit = valueReferenceService.getValueForEntity(unitAsValueRefRule?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
                if (!unit) {
                    unit = calculationRuleService.getLocalizedUnit(calculationRule, indicator)
                }


                designs.each { Entity childEntity ->
                    List<CalculationResult> resultsForIndicator = calculationResultsPerDesign.get(childEntity.id.toString())
                    List<Dataset> datasets = resolvedDatasetsPerDesign.get(childEntity.id.toString())
                    Map<String, Double> impactByQuestions = [:]
                    Map<String, Double> impactByAdditionalQs = [:]
                    Map<String, Double> impactBySections = [:]
                    if (resultCategories) {
                        resultCategories.each { ResultCategory category ->
                            if (datasets) {
                                ResourceCache resourceCache = ResourceCache.init(datasets)
                                for (Dataset dataset: datasets) {
                                    Resource resource = resourceCache.getResource(dataset)
                                    if (resource && !resource.construction) {
                                        String groupTypeForAdditionalQs = dataset.groupingType
                                        String sectionName
                                        Question question = datasetService.getQuestion(dataset)
                                        String questionName = question?.localizedQuestion

                                        if (dataset.sectionId) {
                                            sectionName = sections.find({
                                                it.sectionId.equals(dataset.sectionId)
                                            })?.localizedName ?: fallbackString

                                        } else {
                                            sectionName = fallbackString
                                        }

                                        Double value = childEntity?.getResultForDataset(dataset.manualId, indicator.indicatorId, category.resultCategoryId, calculationRule.calculationRuleId, resultsForIndicator)
                                        if (value) {
                                            if (sectionName) {
                                                if (!mapForGroupTypeTitles.bySection.contains(sectionName)) {
                                                    mapForGroupTypeTitles.bySection.add(sectionName)
                                                }
                                                if (impactBySections.get(sectionName) != null) {
                                                    Double z = impactBySections.get(sectionName) + value
                                                    impactBySections.put(sectionName, z)
                                                } else {
                                                    impactBySections.put(sectionName, value)
                                                }
                                            }

                                            if (questionName) {
                                                defaultStatus = "byQuestion"
                                                if (!mapForGroupTypeTitles.byQuestion.contains(questionName)) {
                                                    mapForGroupTypeTitles.byQuestion.add(questionName)
                                                }
                                                if (impactByQuestions.get(questionName) != null) {
                                                    Double y = impactByQuestions.get(questionName) + value
                                                    impactByQuestions.put(questionName, y)
                                                } else {
                                                    impactByQuestions.put(questionName, value)
                                                }
                                            }
                                            if (groupTypeForAdditionalQs) {
                                                defaultStatus = "byClass"
                                                if (!mapForGroupTypeTitles.byClass.contains(groupTypeForAdditionalQs)) {
                                                    mapForGroupTypeTitles.byClass.add(groupTypeForAdditionalQs)
                                                }
                                                if (impactByAdditionalQs.get(groupTypeForAdditionalQs) != null) {
                                                    Double x = impactByAdditionalQs.get(groupTypeForAdditionalQs) + value
                                                    impactByAdditionalQs.put(groupTypeForAdditionalQs, x)
                                                } else {
                                                    impactByAdditionalQs.put(groupTypeForAdditionalQs, value)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        mapByAdditionalQs.put(childEntity.operatingPeriodAndName, impactByAdditionalQs)
                        mapByQueryQuestions.put(childEntity.operatingPeriodAndName, impactByQuestions)
                        mapBySections.put(childEntity.operatingPeriodAndName, impactBySections)
                    }
                }
            }
            Map mapForRenderAddQs = [:]
            mapByAdditionalQs.each({ designName, valueMap ->
                def map = [:]
                mapForGroupTypeTitles?.byClass?.sort({groupingQuestionAnswer.indexOf(it) != -1 ? groupingQuestionAnswer.indexOf(it) : groupingQuestionAnswer.size()})?.each({ String name ->
                    Double value = valueMap.get(name) ?: 0
                    map.put(name, value)
                })
                mapForRenderAddQs.put(designName, map)
            })
            Map mapForRenderQs = [:]
            mapByQueryQuestions.each({ designName, valueMap ->
                def map = [:]
                mapForGroupTypeTitles?.byQuestion?.each({ String name ->
                    Double value = valueMap.get(name) ?: 0
                    map.put(name, value)
                })
                mapForRenderQs.put(designName, map)
            })
            Map mapForRenderSection = [:]
            mapBySections.each({ designName, valueMap ->
                def map = [:]
                mapForGroupTypeTitles?.bySection?.each({ String name ->
                    Double value = valueMap.get(name) ?: 0
                    map.put(name, value)
                })
                mapForRenderSection.put(designName, map)
            })
            List<String> labelsForChart = mapForGroupTypeTitles?.get(defaultStatus) ?: []
            pageAsString = g.render(template: "/entity/mainEntClassGroupChart", model: [indicator          : indicator, unit: unit, calculationRulesList: calculationRules, drawable: drawable,
                                                                                        calculationRule    : calculationRule, entity: entity, mapBySections: mapForRenderSection,
                                                                                        mapByQueryQuestions: mapForRenderQs, mapByAdditionalQs: mapForRenderAddQs, mapForGroupTypeTitles: mapForGroupTypeTitles,
                                                                                        defaultStatus      : defaultStatus, labelsForChart: labelsForChart as JSON, colors: colors]).toString()

        } catch (e) {
            loggerUtil.error(log, "Error in rendering mainEntClassGroupChart:", e)
            flashService.setErrorAlert("Error in rendering mainEntClassGroupChart: ${e.message}", true)
        }


        return pageAsString
    }

    def resourcesComparisonEndUser() {
        //TODO: should indicator used for material comparison be configurable? HOW TO DEAL WITH ALL THE HARD_CODE?
        try {
            String indicatorId = params.indicatorId ?: Constants.COMPARE_INDICATORID
            String entityId = params.compareEntityId
            String parentEntityId = params.parentEntityId
            Entity parentEntity = entityService.getEntityById(parentEntityId)
            Entity entity = entityId ? entityService.getEntityById(entityId) : entityService.getMaterialSpecifierEntity(parentEntity)

            Indicator indicatorSpeciFier = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            String materialQueryId = "materialSpecifier"


            //Gather info for all subtype by material specifier indicator


            List<Dataset> datasets = datasetService.getDatasetsByEntityAndQueryIdAsDataset(entity, materialQueryId)
            List<ResultCategory> resultCategories = indicatorSpeciFier?.getGraphResultCategoryObjects(parentEntity)
            List<Resource> resourceList = datasets?.collect({ datasetService.getResource(it) })
            List<ResourceType> subtypes = resourceList?.collect({ resourceService.getSubType(it) })?.unique()

            Map<String, Map<String, Double>> byResourceByCategoryCarbon = [:]
            Map<String, Double> byResourceByCategoryFinanceCost = [:]
            Map<String, Double> byResourceByCategoryFinanceCostAndTotal = [:]
            List<String> categoriesWithScores = []

            Map<String, List<Resource>> resourcesBySubTypes = [:]
            subtypes?.each { ResourceType subtype ->
                List<Resource> resourcesForSubType = resourceList?.findAll({ resourceService.getSubType(it)?.subType == subtype?.subType })
                resourcesBySubTypes.put(subtype?.subType, resourcesForSubType)
            }
            //Gather info for all additional classification question:
            List<Indicator> indicatorList = parentEntity.indicators
            Query additionalQuestionsQuery = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)

            List <String> allClassificationMaps = indicatorList?.collect({it?.classificationsMap})?.flatten()?.findAll({it})?.unique()
            List<Question> allAddQGrouping = allClassificationMaps?.collect({it -> questionService.getQuestion(additionalQuestionsQuery,it)})?.findAll({it != null})
            List<String> allAddQGroupingIds = allAddQGrouping?.collect({ it?.questionId })
            Question totalCostQuestion = questionService.getQuestion(additionalQuestionsQuery, "totalCost")
            String costCurrency = valueReferenceService.getValueForEntity(totalCostQuestion?.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: "€"
            Map<String,List<Resource>> byResourceByClass = [:]
            Map<String,String> resourceAndUnitQuantity = [:]
            CalculationRule calculationRule1 = indicatorSpeciFier.getResolveCalculationRules(parentEntity).find({it.calculationRuleId == indicatorSpeciFier?.displayResult})
            String unitCarbon = calculationRuleService.getLocalizedUnit(calculationRule1, indicatorSpeciFier)

            if(datasets){
                byResourceByCategoryFinanceCost = datasets?.collectEntries({[(it.resourceId) : it?.additionalQuestionAnswers?.get("totalCost")?.toString()?.replace(',', '.')?.replaceAll('-','')?.isNumber() ? it?.additionalQuestionAnswers?.get("totalCost")?.toString()?.replace(',', '.')?.replaceAll('-','0')?.toDouble() : 0.0 ]})
                byResourceByCategoryFinanceCostAndTotal = datasets?.collectEntries({[(it.resourceId) : it?.additionalQuestionAnswers?.get("totalAndSocialCost")?.toString()?.replace(',', '.')?.replaceAll('-','')?.isNumber() ? it?.additionalQuestionAnswers?.get("totalAndSocialCost")?.toString()?.replace(',', '.')?.replaceAll('-','0')?.toDouble() : 0.0]})
                resultCategories?.each { ResultCategory resultCategory ->
                    String categoryFullName = "${resultCategory?.resultCategory} - ${resultCategoryService.getLocalizedShortName(resultCategory)}"
                    List<CalculationResult> calculationResults = entity.getCalculationResultObjects(indicatorSpeciFier.indicatorId, indicatorSpeciFier?.displayResult, resultCategory?.resultCategoryId)
                    if(calculationResults){
                        ResourceCache resourceCache = ResourceCache.init(datasets)
                        for (Dataset dataset: datasets) {
                            Resource resource = resourceCache.getResource(dataset)
                            if (resource && !resource.construction) {
                                resourceAndUnitQuantity.put(resource.resourceId,"${dataset.quantity ?:0} ${dataset.userGivenUnit ?: ''}")
                                String graphNameForResource = "${resource.staticFullName ?: optimiResourceService.getLocalizedName(resource)} - ${dataset.quantity ?:''} ${dataset.userGivenUnit ?: ''} ${dataset.additionalQuestionAnswers?.get("comment") ? ", " + dataset.additionalQuestionAnswers?.get("comment"): ''}"
                                Double result = entity?.getResultForDataset(dataset.manualId, indicatorSpeciFier?.indicatorId, resultCategory?.resultCategoryId, null, calculationResults) ?: 0

                                Map resultListByClass = dataset.additionalQuestionAnswers.findAll {allAddQGroupingIds.contains(it.key)}
                                if (result != 0) {
                                    categoriesWithScores.add(categoryFullName)
                                }
                                //Data group by subtype
                                Map<String, Double> impactPerResource = [:]
                                Boolean resourceExist = byResourceByCategoryCarbon.get(graphNameForResource) ? Boolean.TRUE : Boolean.FALSE
                                if (resourceExist) {
                                    Double resultByMaterial = byResourceByCategoryCarbon.get(graphNameForResource)?.get(categoryFullName)
                                    if (resultByMaterial) {
                                        result =+ resultByMaterial
                                    }
                                    impactPerResource = byResourceByCategoryCarbon.get(graphNameForResource)
                                }
                                impactPerResource.put(categoryFullName, result)


                                byResourceByCategoryCarbon.put(graphNameForResource, impactPerResource)
                                //Data group by classification
                                resultListByClass?.each {k,v ->
                                    Question groupingQ = allAddQGrouping?.find({it?.questionId == k})
                                    if(v && groupingQ){
                                        String key = "${questionService.getLocalizedQuestionShort(groupingQ)}-${groupingQ.choices?.find({it.answerId == v})?.localizedAnswer ?: v}"

                                        List<Resource> existing = byResourceByClass?.get(key)
                                        if(existing){
                                            if(!existing?.contains(resource)){
                                                existing.add(resource)
                                            }
                                        } else {
                                            existing = [resource]
                                        }
                                        byResourceByClass.put(key,existing)
                                    }

                                }
                            }
                        }
                    }
                }
                categoriesWithScores = categoriesWithScores?.unique()
            }
            //Render datasets for subtypes Carbon
            Map resolvedDataForSubtype = renderMapForChart(resourcesBySubTypes,categoriesWithScores,byResourceByCategoryCarbon)
            Map graphDatasetForSubtypeCarbon = resolvedDataForSubtype?.get("graphDataset") ?: [:]
            Map graphLabelsForSubtypeCarbon = resolvedDataForSubtype?.get("resourcesFullNameMap") ?: [:]
            //Render datasets for subtypes Finance cost
            Map resolvedDataForSubtypeFinanceCost = renderMapForChart(resourcesBySubTypes,null,null,byResourceByCategoryFinanceCostAndTotal, true)
            Map graphDatasetForSubtypeFinanceCost = resolvedDataForSubtypeFinanceCost?.get("graphDataset") ?: [:]
            //Render datasets for subtypes Total cost
            Map resolvedDataForSubtypeTotalCost = renderMapForChart(resourcesBySubTypes,null,null,byResourceByCategoryFinanceCost)
            Map graphDatasetForSubtypeTotalCost = resolvedDataForSubtypeTotalCost?.get("graphDataset") ?: [:]
            //render datasets for classification Carbon
            Map resolvedDataForClass = renderMapForChart(byResourceByClass,categoriesWithScores,byResourceByCategoryCarbon)
            Map graphDatasetForClassCarbon = resolvedDataForClass?.get("graphDataset") ?: [:]
            Map graphLabelsForClassCarbon = resolvedDataForClass?.get("resourcesFullNameMap") ?: [:]
            //render datasets for classification Finance cost
            Map resolvedDataForClassFinanceCost = renderMapForChart(byResourceByClass,null,null,byResourceByCategoryFinanceCostAndTotal, true)
            Map graphDatasetForClassCarbonFinanceCost = resolvedDataForClassFinanceCost?.get("graphDataset") ?: [:]
            //render datasets for classification Total cost
            Map resolvedDataForClassTotalCost = renderMapForChart(byResourceByClass,null,null,byResourceByCategoryFinanceCost)
            Map graphDatasetForClassCarbonTotalCost = resolvedDataForClassTotalCost?.get("graphDataset") ?: [:]

            [indicator: indicatorSpeciFier, entity: entity, parentEntity: parentEntity, subtypes: subtypes, categoriesWithScores: categoriesWithScores,calculationRule:calculationRule1,
             graphDatasetForSubtypeCarbon: graphDatasetForSubtypeCarbon, resourcesBySubTypes: resourcesBySubTypes, byResourceByCategory: byResourceByCategoryCarbon,
             graphDatasetForClassCarbonFinanceCost:graphDatasetForClassCarbonFinanceCost, graphDatasetForClassCarbonTotalCost:graphDatasetForClassCarbonTotalCost,resourceAndUnitQuantity:resourceAndUnitQuantity,
             graphDatasetForClassCarbon:graphDatasetForClassCarbon, graphDatasetForSubtypeFinanceCost:graphDatasetForSubtypeFinanceCost, graphDatasetForSubtypeTotalCost:graphDatasetForSubtypeTotalCost,
             allAddQGrouping:allAddQGrouping, byResourceByClass:byResourceByClass,graphLabelsForSubtypeCarbon:graphLabelsForSubtypeCarbon,byResourceByCategoryFinanceCostAndTotal:byResourceByCategoryFinanceCostAndTotal,
             graphLabelsForClassCarbon:graphLabelsForClassCarbon, unitCost:costCurrency,unitCarbon:unitCarbon,byResourceByCategoryFinanceCost:byResourceByCategoryFinanceCost]
        } catch(e){
            loggerUtil.error(log, "Error in rendering resourcesComparisonEndUser:", e)
            flashService.setErrorAlert("Error in rendering resourcesComparisonEndUser: ${e.message}", true)
            redirect controller: "main"
        }

    }

    private Map renderMapForChart(Map<String,List<Resource>> mainMap, List<String> subMap1 = null,Map<String, Map<String, Double>> subMap2 = null, Map<String,Double> subMap3 = null, Boolean isTotalCost = Boolean.FALSE){
        Map graphDataset= [:]
        Map<String,List<String>> resourcesFullNameMap = [:]
        def output = [:]
        // to render map for carbon with categories
        if(mainMap && subMap1 && subMap2){
            mainMap.each { String groupKey, List<Resource> resources ->
                def mapValueAllCat = [:]

                def listResourceNames = []

                subMap1?.each { String category ->
                    def listValueEachCat = []
                    resources?.each { Resource resource ->
                        def map
                        if(resource.staticFullName){
                            map = subMap2?.find({it.key?.contains(resource.staticFullName)})
                        }else {
                            map = subMap2?.find({it.key?.contains(optimiResourceService.getLocalizedName(resource))})
                        }

                        if(map){
                            Double value = map?.value?.get(category) ?: 0
                            listValueEachCat.add(value)
                            listResourceNames.add(map.key)
                        }

                    }
                    mapValueAllCat.put(category, listValueEachCat)
                }
                resourcesFullNameMap.put(groupKey,listResourceNames)
                graphDataset.put(groupKey, mapValueAllCat?.collect({ ["name": "${it.key}", "data": it.value, "type": "column", "yAxis": 0] }))

            }
        } else if (mainMap && subMap3){
            // to render graph for cost  with no categories
            mainMap.each { String groupKey, List<Resource> resources ->
                def listResourceNames = resources?.collect({it?.resourceId})
                resourcesFullNameMap.put(groupKey,listResourceNames)
                graphDataset.put(groupKey, ["name": "${isTotalCost ? message(code:"total_cost") : message(code:"finance_cost")}", color: "${isTotalCost ? 'rgb(153,63,0)' : 'rgb(78, 150, 91)'}", "data": subMap3?.findAll {listResourceNames?.contains(it.key) }?.collect({it.value}), "type": "line", "yAxis": 1])

            }

        }
        output.put("graphDataset",graphDataset)
        output.put("resourcesFullNameMap",resourcesFullNameMap)
        return output
    }

    def isCalculationRunning(){
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        Boolean isResultPage = request.JSON.isResultPage?.toBoolean()
        Boolean isCalculationRunning = calculationProcessService.isCalculationRunning(entityId, indicatorId)
        Map<String, String> messages = [:]
        loggerUtil.debug(log, "isCalculationRunning check")

        if (isCalculationRunning){
            messages.permWarningAlert = g.message(code: isResultPage ? "entity.calculation.running.result" :
                    "entity.calculation.running")
        }

        render([output: isCalculationRunning, (flashService.FLASH_OBJ_FOR_AJAX): messages] as JSON)
    }

    def isCalculationRunningForEntityPage(){
        String entityId = request.JSON.entityId
        List<CalculationProcess> calculationProcesses = calculationProcessService.getCalculatedProcessesForParentEntity(entityId)
        Map<String, String> messages = [:]

        if (calculationProcesses.size() > 0) {
            messages.permWarningAlert = g.message(code: "entity.calculation.running")
        }

        render([output: calculationProcesses, (flashService.FLASH_OBJ_FOR_AJAX): messages] as JSON)
    }

    def getCalculationResultForDesignEntityAndIndicator(){
        String delimiter = ":"
        Map<String, String> result = [:]
        List<String> designAndIndicatorPair = request.JSON.designAndIndicatorForCalculation as List

        if (designAndIndicatorPair) {
            Entity parentEntity = entityService.getEntityById(request.JSON.parentEntityId)


            List<License> validLicenses = licenseService.getValidLicensesForEntity(parentEntity)
            List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

            for (String designAndIndicator in designAndIndicatorPair) {
                List<String> keys = designAndIndicator.split(delimiter)
                Entity entity = entityService.getEntityById(keys[0])
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(keys[1], true)
                List<Query> indQueries = queryService.getQueriesByIndicatorAndEntity(indicator, entity, featuresAvailableForEntity)
                String displayResult = indicatorService.renderDisplayResult(entity, indicator, featuresAvailableForEntity, indQueries.queryId,
                        true, true, true, session)
                result.put(designAndIndicator, displayResult)
            }
        }

        render([output: result, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getCalculationMessageResultAndClearIt() {
        String entityId = request.JSON.entityId
        String indicatorId = request.JSON.indicatorId
        Map<String, String> msg = [:]
        // need to flush session so that it is consistent, because we run a direct db query inside getEntityMessageAndRemoveFromEntity and we need to reflect it on Hibernate cache
        Entity.withSession { Session session ->
            Entity entity = entityService.getEntityById(entityId)

            String link = queryService.createResultsLink(entity, entity.id.toString(), indicatorId)
            CalculationProcess calculationProcess = calculationProcessService.findCalculationProcessByEntityIdAndIndicatorIdAndStatus(
                    entityId, indicatorId, CalculationProcess.CalculationStatus.ERROR.name()
            )

            if (calculationProcess) {
                msg.fadeErrorAlert = g.message(code: "entity.calculation.error")
            }

            if (Boolean.TRUE.equals(entity.resultsOutOfDate)) {
                msg.fadeWarningAlert = g.message(code: 'query.saved', args: [link])
            } else if (calculationMessageService.getCurrentMessageForIndicator(entity, indicatorId)) {
                msg.fadeSaveAlert = calculationMessageService.getEntityMessageAndRemoveFromEntity(entity, indicatorId)
            }
            session.flush()
            session.clear()
        }

        render([(flashService.FLASH_OBJ_FOR_AJAX): msg] as JSON)
    }
}

