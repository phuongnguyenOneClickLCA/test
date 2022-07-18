/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Denominator
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import com.bionova.optimi.core.domain.mongo.LcaCheckerResult
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResultCategory
import com.bionova.optimi.core.domain.mongo.ResultManipulator
import com.bionova.optimi.core.domain.mongo.Task
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.IndicatorReportService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.data.ResourceCache
import grails.converters.JSON
import org.apache.commons.lang.StringUtils
import org.apache.poi.ss.usermodel.Workbook
import org.bson.Document
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * @author Pasi-Markus Mäkelä
 */
class PortfolioController extends ExceptionHandlerController {

    def entityService
    def indicatorService
    def portfolioService
    def userService
    def licenseService
    def optimiResourceService
    def channelFeatureService
    def queryService
    def taskService
    def denominatorService
    def localeResolverUtil
    def denominatorUtil
    def portfolioUtil
    def messageSource
    def calculationRuleService
    IndicatorReportService indicatorReportService

    @Autowired
    EmailConfiguration emailConfiguration

    private static portfolioTypes = ["operating", "design"]
    private static final List alphabets = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                                           'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', 'Ö']

    def results() {
        Portfolio portfolio = portfolioService.getPortfolioById(params.id)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        Entity portfolioEntity = portfolioService.getEntity(portfolio?.entityId)
        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(portfolioEntity?.parentEntityId ? portfolioEntity.parentById : portfolioEntity, [Feature.BETA_FEATURES])
        Boolean betaFeaturesLicensed = featuresAllowed.get(Feature.BETA_FEATURES)
        [portfolio: portfolio, indicator: indicator, operatingPeriod: params.operatingPeriod, portfolioEntity: portfolioEntity, betaFeaturesLicensed: betaFeaturesLicensed]
    }

    def sendReminder() {
        String portfolioId = params.id
        String operatingPeriod = params.period
        Portfolio portfolio
        List<Indicator> indicators

        if (portfolioId && operatingPeriod) {
            portfolio = portfolioService.getPortfolioById(params.id)
            indicators = portfolioService.getIndicators(portfolio.indicatorIds)
            def frequencies = [1: message(code: 'task.frequency.daily'), 2: message(code: 'task.frequency.weekly')]
            def entities = []
            Map indicatorsReady = [:]
            Boolean allReady
            def portfolioEntities = portfolioService.getEntities(portfolio)

            portfolioEntities?.each { Entity e ->
                Entity child = e.operatingPeriods?.find({ operatingPeriod.equals(it.operatingPeriod) })

                if (child) {
                    Boolean ready = child.indicatorsReady(indicators)

                    if (ready) {
                        indicatorsReady.put(e, true)
                    } else {
                        indicatorsReady.put(e, false)
                    }
                }
                entities.add(e)
            }

            if (indicatorsReady.values().contains(false)) {
                allReady = Boolean.FALSE
            } else {
                allReady = Boolean.TRUE
            }

            def confirm

            if (params.confirm) {
                confirm = true
            }
            return [portfolio          : portfolio, operatingPeriod: operatingPeriod, indicators: indicators,
                    reminderFrequencies: frequencies, entities: entities?.sort({ it.name }), confirm: confirm,
                    indicatorsReady    : indicatorsReady, allReady: allReady]
        } else {
            redirect controller: "main"
        }
    }

    def createReminderTasks() {
        if (!params.deadline || !params.notes || !params.id) {
            flash.fadeErrorAlert = message(code: "portfolio.send_reminder.mandatory_data_missing")
            redirect action: "sendReminder", params: params
        } else {
            if (!params.confirmed) {
                params.put("confirm", true)
                def confirmWarning = message(code: "portfolio.send_reminder.warning", args: [params.id ? params.list("entityId").size() : 0])
                chain(action: "sendReminder", params: params, model: [confirmWarning: confirmWarning])
            } else {
                if (params.id && params.id && params.period && params.portfolioEntityId) {
                    List entityIds = params.list("entityId")
                    String operatingPeriod = params.period
                    String portfolioEntityId = params.portfolioEntityId
                    User user = userService.getCurrentUser(Boolean.TRUE) as User
                    def errors

                    entityIds.each { String entityId ->
                        Entity entity = entityService.getEntityById(entityId, session)
                        String email = params[("user." + entityId)]

                        if (entity && email) {
                            Task task = new Task(params)
                            task.deleguer = user
                            task.email = email
                            task.targetEntityId = entityId

                            if (task.validate()) {
                                task = taskService.createTask(entity.id, task)

                                try {
                                    sendReminderEmail(user, email, entity, operatingPeriod, task.notes)
                                } catch (Exception e) {
                                    loggerUtil.error(log, "Error in sending email: ${e}")
                                    errors = message(code: "email.error", args: [emailConfiguration.supportEmail])
                                }
                            } else {
                                errors = errors ? errors + "<br />" + renderErrors(bean: task) : renderErrors(bean: task)
                            }
                        }
                    }

                    if (!errors) {
                        flash.fadeSuccessAlert = message(code: "portfolio.send_reminder.successful")
                        redirect controller: "entity", action: "show", params: [entityId: portfolioEntityId]
                    } else {
                        flash.fadeErrorAlert = errors
                        redirect action: "sendReminder", id: params.id, params: [period: operatingPeriod]
                    }
                } else {
                    redirect controller: "main"
                }
            }
        }
    }

    private String createReminderMessage(User user, Entity entity, String operatingPeriod, String messageFromUser) {
        String reminderMessage

        if (user && entity && operatingPeriod && messageFromUser) {
            String entityLink = generateEntityLink(entity)
            reminderMessage = message(code: "portfolio.send_reminder.message", args: [user.getName(), operatingPeriod, entity.name, messageFromUser, entityLink])
        }
        return reminderMessage
    }

    private void sendReminderEmail(User user, String email, Entity entity, String operatingPeriod, messageFromUser) {
        if (user && email && entity && operatingPeriod && messageFromUser) {
            def emailTo = email
            def emailSubject = message(code: "portfolio.send_reminder.subject", args: [user.getName()])
            def emailFrom = message(code: "email.support")
            def replyToAddress = message(code: "email.support")
            def emailBody = createReminderMessage(user, entity, operatingPeriod, messageFromUser)

            optimiMailService.sendMail({
                to emailTo
                from emailFrom
                replyTo replyToAddress
                subject emailSubject
                body emailBody
            }, emailTo)
        }
    }

    private String generateEntityLink(Entity entity) {
        Locale locale = localeResolverUtil.resolveLocale(session, request, response)
        def lang = locale?.language
        def link = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: "entity", action: "show",
                params: [entityId: entity?.id, lang: lang])

        return link
    }

    def expandResults() {
        if (params.portfolioId && params.indicatorId && params.resultCategory) {
            Portfolio portfolio
            ResultCategory resultCategory
            String resultCategoryParam = params.resultCategory
            String operatingPeriod = params.operatingPeriod
            portfolio = portfolioService.getPortfolioById(params.portfolioId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
            List<CalculationRule> calculationRules

            if (indicator && resultCategoryParam) {
                resultCategory = indicator.getResolveResultCategories(null)?.find({
                    resultCategoryParam.equals(it.resultCategory)
                })
                IndicatorReportItem reportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, indicator.report?.reportItems)?.
                        find({ IndicatorReportItem reportItem ->
                            "default".equals(reportItem.tableSource) ||
                                    "normalTable".equals(reportItem.tableSource)
                        })

                if (reportItem?.rules) {
                    calculationRules = indicator.getResolveCalculationRules(null)?.findAll({
                        reportItem.rules.contains(it.calculationRule)
                    })
                } else {
                    calculationRules = indicator.getResolveCalculationRules(null)
                }

                if (indicator.applicationRules && !calculationRules) {
                    flash.fadeErrorAlert = message(code: 'application_rules.missing', args: [indicator.applicationRules], encodeAs: "Raw")
                }
            }
            render view: "/entity/resultexpand", model: [hideNavi     : true, portfolio: portfolio, indicator: indicator, resultCategory: resultCategory,
                                                         showPoweredBy: true, calculationRules: calculationRules, operatingPeriod: operatingPeriod]
        }
    }

    def categoryDetails() {
        List<Entity> entities = []
        List<String> entityIds = []

        if ( params.entityId) {
            entityIds.addAll(params.entityId)
        }
        String indicatorId = params.indicatorId
        String resultCategoryId = params.resultCategory
        String portfolioId = params.portfolioId
        Indicator indicator
        ResultCategory resultCategory
        List<CalculationRule> calculationRules

        if (entityIds && indicatorId && resultCategoryId && portfolioId) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            resultCategory = indicator?.getResolveResultCategories(null)?.find({ resultCategoryId.equals(it.resultCategory) })

            IndicatorReportItem reportItem = indicatorReportService.getReportItemsAsReportItemObjects(null, indicator.report?.reportItems)?.
                    find({ IndicatorReportItem reportItem ->
                        "default".equals(reportItem.tableSource) ||
                                "normalTable".equals(reportItem.tableSource)
                    })

            if (reportItem?.rules) {
                calculationRules = indicator.getResolveCalculationRules(null)?.findAll({
                    reportItem.rules.contains(it.calculationRule)
                })
                if(!calculationRules || calculationRules?.isEmpty()){
                    calculationRules = indicator.getResolveCalculationRules(null)
                }
            } else {
                calculationRules = indicator.getResolveCalculationRules(null)
            }

            entityIds.each { String entityId ->
                Entity entity = entityService.getEntityById(entityId)

                if (entity) {
                    entities.add(entity)
                }
            }
        }
        [indicator: indicator, resultCategory: resultCategory, entities: entities, calculationRules: calculationRules]
    }

    def denominator() {
        def portfolio = portfolioService.getPortfolioById(params.id)
        def realties = portfolio.entities
        def indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        def chosenPeriods = portfolio.chosenPeriods?.sort({ it.toInteger() })
        def selectedPeriods = []
        def realtyPeriods = [:]
        def operatingPeriodObjects = []

        realties?.each { Entity entity ->
            def operatingPeriods = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())?.findAll({
                !it.name
            })

            if (operatingPeriods) {
                def operatingPeriodByPeriod = [:]

                operatingPeriods.each { operatingPeriod ->
                    operatingPeriodByPeriod.put(operatingPeriod.operatingPeriod, operatingPeriod)

                    if (!operatingPeriodObjects.contains(operatingPeriod)) {
                        operatingPeriodObjects.add(operatingPeriod)
                    }
                }
                realtyPeriods.put(entity.id, operatingPeriodByPeriod)

                if (!chosenPeriods) {
                    operatingPeriods.collect({ it.operatingPeriod })?.each { period ->
                        if (!selectedPeriods?.contains(period)) {
                            selectedPeriods.add(period)
                        }
                    }
                } else {
                    selectedPeriods = chosenPeriods
                }
            }
        }
        def selectedOpObjects = operatingPeriodObjects.findAll({ Entity e -> selectedPeriods?.contains(e.operatingPeriod) })
        [portfolio: portfolio, realties: realties, realtyPeriods: realtyPeriods, operatingPeriods: selectedPeriods?.sort(),
         indicator: indicator, operatingPeriodObjects: operatingPeriodObjects, selectedOpObjects: selectedOpObjects]
    }

    def designDenominator() {
        def portfolio = portfolioService.getPortfolioById(params.id)
        def indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        def entities = portfolio.entities
        def designs = []
        def entityDesigns = [:]

        entities?.each { Entity entity ->
            def childEntities = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())

            childEntities?.each { Entity design ->
                designs.add(design)
            }
            entityDesigns.put(entity, childEntities)
        }
        [portfolio: portfolio, entityDesigns: entityDesigns, designs: designs, indicator: indicator]
    }

    def form() {
        def portfolio = portfolioService.getPortfolioById(params.id)
        def entityId = params.entityId
        List<License> licenses
        def entityClasses = optimiResourceService.getParentEntityClasses(Boolean.TRUE)?.findAll({ Resource r -> !r.resourceId.toLowerCase().equals("portfolio") })
        User user = userService.getCurrentUser() as User
        def allIndicators

        Map<String, String> defaultLicenseType = [:]
        Map<String, String> licenseTypes = [:]
        Constants.LicenseType.values().collect({ it.toString() }).each { String type ->
            String message = message(code: "license.type.${type}")

            if (type.equals(Constants.LicenseType.PRODUCTION.toString())) {
                defaultLicenseType.put(type, "${message}")
            } else {
                licenseTypes.put(type, "${message}")
            }

        }

        String portfolioLicenseType = portfolio?.licenseType ?: Constants.LicenseType.PRODUCTION.toString()

        if (userService.getSuperUser(user)) {
            licenses = licenseService.getUserLicenses()

            if (licenses) {
                licenses = licenses.findAll({ License license -> portfolioLicenseType.equals(license.type) })
            }
            allIndicators = indicatorService.getAllActiveIndicators()
        }

        if ((portfolio && portfolioService.getManageable(portfolio.entityId)) || user.modifiableEntityIds?.contains(entityId)) {
            List<Indicator> indicators = []
            List<Document> entities
            List<Document> choosableEntities = []
            List<String> addedIndicatorIds = portfolio.indicatorIds

            if (portfolio.entityClass) {
                entities = entityService.getModifiableEntitiesForPortfolio([portfolio.entityClass])
            } else {
                entities = entityService.getModifiableEntitiesForPortfolio(optimiResourceService.getParentEntityClassIds())
            }
            Document toremove = entities?.find({ Document d -> d._id.toString().equals(entityId) })

            if (toremove) {
                entities.remove(toremove)
            }
            List<Entity> portfolioEntities = portfolioService.getEntities(portfolio)
            List<String> choosableIndicators = []

            if (entities) {
                List<String> ids = []

                if (portfolioEntities) {
                    portfolioEntities.each { Entity entity ->
                        ids.add(entity.id.toString())
                        List<String> entityIndicatorIds = indicatorService.getIndicatorsByEntityAndIndicatorUse(entity.id, "operating".equals(portfolio?.type) ? com.bionova.optimi.construction.Constants.IndicatorUse.OPERATING.toString() : com.bionova.optimi.construction.Constants.IndicatorUse.DESIGN.toString())?.collect({ it.indicatorId })

                        if (entityIndicatorIds) {
                            choosableIndicators.addAll(entityIndicatorIds)
                        }
                    }
                }

                entities.each {
                    if (!ids?.contains(it._id.toString()) && ((addedIndicatorIds && it.indicatorIds && addedIndicatorIds.intersect(it.indicatorIds.toList())) || !addedIndicatorIds)) {
                        choosableEntities.add(it)
                    }
                }
                choosableIndicators = choosableIndicators.unique()
            }
            List<String> choosablePeriods = []

            if ("operating".equals(portfolio?.type) && portfolioEntities) {
                portfolioEntities?.each { Entity e ->
                    List<Entity> operatingPeriods = entityService.getChildEntities(e, EntityClass.OPERATING_PERIOD.getType())

                    if (operatingPeriods) {
                        operatingPeriods.collect({ it.operatingPeriod })?.each { String period ->
                            if (!choosablePeriods?.contains(period)) {
                                choosablePeriods.add(period)
                            }
                        }
                    }
                }
            }

            if (portfolio.entityClass && portfolio.type && (choosableEntities||portfolioEntities)) {
                indicators = indicatorService.getIndicatorsByEntityClassAndIndicatorUse(portfolio.entityClass, portfolio.type)

                List<Indicator> portfolioIndicator = portfolioService.getIndicators(portfolio.indicatorIds)

                if (portfolioIndicator && indicators) {
                    indicators.removeAll(portfolioIndicator)
                }

                if (choosableIndicators && indicators && !userService.getSuperUser(user)) {
                    List<Indicator> indicatorsToRemove = []

                    indicators.each {
                        if (!choosableIndicators.contains(it.indicatorId)) {
                            indicatorsToRemove.add(it)
                        }
                    }
                    indicators.removeAll(indicatorsToRemove)
                }
            }
            def errorMessage

            if ("operating".equals(portfolio.type) && portfolio.entityIds && !portfolio.chosenPeriods) {
                flash.errorAlert = message(code: "portfolio.no_periods")
            }

            if (!portfolioEntities && portfolio.licenseId) {
                errorMessage = errorMessage ? errorMessage + "<br />" +
                        message(code: "portfolio.license.no_applicable_entities") :
                        message(code: "portfolio.license.no_applicable_entities")
            }

            if (errorMessage) {
                flash.fadeErrorAlert = errorMessage
            }
            if (indicators && !userService.getSuperUser(user)) {
                indicators.removeAll({"benchmark".equalsIgnoreCase(it?.visibilityStatus)})
            }
            [portfolio       : portfolio, licenses: licenses, indicators: indicators, portfolioTypes: portfolioTypes,
             entityId        : entityId, entities: choosableEntities?.sort({ it.name }), entityClasses: entityClasses, allIndicators: allIndicators,
             choosablePeriods: choosablePeriods?.sort({ it.toInteger() }), licenseTypes: licenseTypes, defaultLicenseType: defaultLicenseType]
        } else {
            [portfolioTypes: portfolioTypes, entityId: entityId, licenses: licenses, entityClasses: entityClasses,
             allIndicators : allIndicators, licenseTypes: licenseTypes, defaultLicenseType: defaultLicenseType]
        }
    }

    def save() {
        def portfolio
        def portfolioId = params.id
        def entityId = params.entityId

        if (portfolioId) {
            portfolio = portfolioService.getPortfolioById(portfolioId)
            def licenseTypeChanged = false
            def typeChanged = false
            String newType = params.type
            String newLicenseType = params.licenseType

            if (newType && !newType.equals(portfolio?.type)) {
                typeChanged = true
            }

            if (newLicenseType && portfolio.licenseType && !newLicenseType.equals(portfolio.licenseType)) {
                licenseTypeChanged = true
            }
            portfolio.properties = params

            if (typeChanged) {
                portfolio.indicatorIds = []
            }

            if (licenseTypeChanged) {
                portfolio.licenseId = null
                portfolio.entityIds = []
            }
        } else {
            portfolio = new Portfolio(params)
        }

        if (params.chosenPeriods) {
            portfolio.chosenPeriods = params.list("chosenPeriods")
        }

        if (portfolio.validate()) {
            if (portfolio.id) {
                portfolio = portfolioService.updatePortfolio(portfolio)
            } else {
                portfolio = portfolioService.createPortfolio(portfolio)
            }
            redirect action: "form", id: portfolio?.id, params: [entityId: entityId]
        } else {
            flash.fadeErrorAlert = renderErrors(bean: portfolio)
            chain action: "form", id: portfolioId, model: [portfolio: portfolio], params: [entityId: entityId]
        }
    }

    def addIndicator() {
        def portfolio = portfolioService.addIndicatorToPortfolio(params.id, params.indicatorId)
        redirect action: "form", id: portfolio?.id, params: [entityId: params.entityId]
    }

    def removeIndicator() {
        def portfolio = portfolioService.removeIndicatorFromPortfolio(params.id, params.indicatorId)
        redirect action: "form", id: portfolio?.id, params: [entityId: params.entityId]
    }

    def addEntity() {
        Portfolio portfolio = portfolioService.addEntityToPortfolio(params.id, params.addableEntityId)
        redirect action: "form", id: portfolio?.id, params: [entityId: params.entityId]
    }

    def removeEntity() {
        def portfolio = portfolioService.removeEntityFromPortfolio(params.id, params.removableEntityId)
        redirect action: "form", id: portfolio?.id, params: [entityId: params.entityId]
    }

    def exportReport() {
        def portfolioId = params.id
        def operatingPeriod = params.period

        if (portfolioId && operatingPeriod) {
            Workbook wb = portfolioService.exportPortolio(portfolioId, operatingPeriod)

            if (wb) {
                response.contentType = 'application/vnd.ms-excel'
                response.setHeader("Content-disposition", "attachment; filename=portfolioReport_${new Date().format('dd.MM.yyyy HH:mm:ss')}.xls")
                OutputStream outputStream = response.getOutputStream()
                wb.write(outputStream)
                outputStream.flush()
                outputStream.close()
            }
        }
    }

    def renderDesignPortfolio() {
        Entity entity = entityService.getEntityById(params.entityId)

        if (entity) {
            Portfolio portfolio = entity.portfolio
            def entities = portfolioService.getEntities(portfolio)
            def entityDesigns = [:]
            def indicators = portfolioService.getIndicators(portfolio?.indicatorIds)
            def showAllLink = false
            def allDesigns = []
            Boolean showOnlySpecificallyAllowed = portfolio?.showOnlySpecificallyAllowed
            Boolean showOnlyCarbonHeroes = portfolio?.showOnlyCarbonHeroes
            Boolean showOnlyLatestStatus = portfolio?.showOnlyLatestStatus

            entities?.each { Entity e ->
                if (!showAllLink && !e.hasAnyIndicator(indicators)) {
                    showAllLink = true
                }
                def designs = entityService.getChildEntities(e, EntityClass.DESIGN.getType())

                if (designs) {
                    if (showOnlyLatestStatus) {
                        if (showOnlyCarbonHeroes) {
                            if (showOnlySpecificallyAllowed) {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.allowAsBenchmark && childEntity.carbonHero && childEntity.chosenDesign })

                                if (allowedDesigns) {
                                    entityDesigns.put(e, allowedDesigns)
                                    allDesigns.addAll(allowedDesigns)
                                }
                            } else {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.carbonHero && childEntity.chosenDesign })

                                if (allowedDesigns) {
                                    entityDesigns.put(e, allowedDesigns)
                                    allDesigns.addAll(allowedDesigns)
                                }
                            }
                        } else {
                            if (showOnlySpecificallyAllowed) {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.allowAsBenchmark && childEntity.chosenDesign })
                                entityDesigns.put(e, allowedDesigns)
                                allDesigns.addAll(allowedDesigns)
                            } else {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.chosenDesign })

                                if (allowedDesigns) {
                                    entityDesigns.put(e, allowedDesigns)
                                    allDesigns.addAll(allowedDesigns)
                                }
                            }
                        }
                    } else {
                        if (showOnlyCarbonHeroes) {
                            if (showOnlySpecificallyAllowed) {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.allowAsBenchmark && childEntity.carbonHero })

                                if (allowedDesigns) {
                                    entityDesigns.put(e, allowedDesigns)
                                    allDesigns.addAll(allowedDesigns)
                                }
                            } else {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.carbonHero })

                                if (allowedDesigns) {
                                    entityDesigns.put(e, allowedDesigns)
                                    allDesigns.addAll(allowedDesigns)
                                }
                            }
                        } else {
                            if (showOnlySpecificallyAllowed) {
                                def allowedDesigns = designs.findAll({ Entity childEntity -> childEntity.allowAsBenchmark })
                                entityDesigns.put(e, allowedDesigns)
                                allDesigns.addAll(allowedDesigns)
                            } else {
                                entityDesigns.put(e, designs)
                                allDesigns.addAll(designs)
                            }
                        }
                    }
                }
            }
            Map graphModelByIndicator = [:]
            Map maxScoreByIndicator = [:]

            indicators?.each { Indicator indicator ->
                def graphModel = portfolioUtil.getGraphModelForDesigns(portfolio, entityDesigns, indicator, Boolean.TRUE, Boolean.FALSE)

                if (graphModel && !graphModel.isEmpty()) {
                    graphModelByIndicator.put(indicator.indicatorId, graphModel)
                    Integer maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)

                    if (maxScore) {
                        maxScoreByIndicator.put(indicator.indicatorId, maxScore)
                    }
                }
            }
            User user = userService.getCurrentUser()
            Boolean sysAdmin = userService.isSystemAdmin(user)
            String templateAsString = g.render(template: "/portfolio/designs", model: [entity               : entity, entityDesigns: entityDesigns, indicators: indicators,
                                                                                       showAllLink          : showAllLink, allDesigns: allDesigns, portfolio: portfolio,
                                                                                       graphModelByIndicator: graphModelByIndicator, maxScoreByIndicator: maxScoreByIndicator,
                                                                                       sysAdmin             : sysAdmin, user: user]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            flash.errorAlert = message(code: "entity.illegal_access")
            redirect controller: "main"
        }
    }

    def renderOperatingPortfolio() {
        Entity entity = entityService.getEntityById(params.entityId)

        if (entity) {
            Portfolio portfolio = entity.portfolio
            List<Entity> entities = []
            List<Indicator> indicators = []
            if (portfolio) {
                entities = portfolioService.getEntities(portfolio)
                indicators = portfolioService.getIndicators(portfolio.indicatorIds)
            }
            def chosenPeriods = portfolio?.chosenPeriods?.sort({ it.toInteger() })
            Map entityPeriods = new TreeMap()
            def entitiesByOperatingPeriod = [:]
            def showAllByIndicators = [:]
            def allChildEntities = []
            User user = userService.getCurrentUser(true)

            if (chosenPeriods) {
                entities?.each { Entity e ->
                    indicators.each { Indicator indicator ->
                        if (!showAllByIndicators.get(indicator) && !e.indicators?.contains(indicator)) {
                            showAllByIndicators.put(indicator, true)
                        }
                    }
                    List<Entity> childEntities = entityService.getChildEntities(e, EntityClass.OPERATING_PERIOD.getType())

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
                            entityPeriods.put(e, operatingPeriodByPeriod)
                        }
                    }
                }
            } else if (!chosenPeriods && entities) {
                flash.errorAlert = message(code: "portfolio.no_periods")
            }
            def exportLicensed = licenseService.isFeatureLicensed(Feature.PORTFOLIO_EXCEL_EXPORT, entity?.type, entity?.entityClass)
            Map graphModelByIndicator = new TreeMap()
            Map maxScoreByIndicator = new TreeMap()
            Map topScoreByIndicator = new TreeMap()
            Map minScoreByIndicator = new TreeMap()

            indicators?.each { Indicator indicator ->
                Map graphModel = portfolioUtil.getGraphModelForOperating(portfolio, entityPeriods, chosenPeriods, indicator, Boolean.TRUE, Boolean.FALSE)

                if (graphModel && !graphModel.isEmpty()) {
                    graphModelByIndicator.put(indicator.indicatorId, graphModel)
                    Integer maxScore = portfolioUtil.getMaxScoreFromOperatingGraphModel(graphModel)
                    Map<String, Double> largestByPeriod = portfolioUtil.getLargestScoreFromOperatingGraphModel(graphModel, chosenPeriods)
                    Map<String, Double> minByPeriod = portfolioUtil.getSmallestScoreFromOperatingGraphModel(graphModel, chosenPeriods)
                    
                    if (maxScore) {
                        maxScoreByIndicator.put(indicator.indicatorId, maxScore)
                    }

                    if (largestByPeriod) {
                        topScoreByIndicator.put(indicator.indicatorId, largestByPeriod)
                    }

                    if (minByPeriod) {
                        minScoreByIndicator.put(indicator.indicatorId, minByPeriod)
                    }
                }
            }
            String templateAsString = g.render(template: "/portfolio/operating", model: [entity             : entity, portfolio: portfolio, entities: entities, operatingPeriods: chosenPeriods, entityPeriods: entityPeriods, allChildEntities: allChildEntities,
                                                                                         indicators         : indicators, entitiesByOperatingPeriod: entitiesByOperatingPeriod, showAllByIndicators: showAllByIndicators, exportLicensed: exportLicensed,
                                                                                         portfolio          : portfolio, graphModelByIndicator: graphModelByIndicator, maxScoreByIndicator: maxScoreByIndicator, topScoreByIndicator: topScoreByIndicator,
                                                                                         minScoreByIndicator: minScoreByIndicator, user: user]).toString()
            render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            flash.errorAlert = message(code: "entity.illegal_access")
            redirect controller: "main"
        }
    }

    def renderDesignDenominatorScores() {
        Map dynamicDenominatorScores = [:]
        Map denominatorScores = [:]
        Map formattedForTableScores = [:]
        Map<String, Map<String, String>> formattedForTableScoresAdditionalRules = [:]
        String indicatorId = params.indicatorId
        String entityId = params.entityId
        String denominatorType = params.denominatorType
        List<String> datasetManualIds
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        def showAllLink = false
        User user = userService.getCurrentUser(true)
        if (params.datasetManualIds) {
            datasetManualIds = ((String) params.datasetManualIds).tokenize(',')
        }
        def entityDesigns = [:]
        List<Entity> designs = []
        Portfolio portfolio

        if (indicatorId && entityId) {
            Entity e = entityService.readEntity(entityId)
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
            portfolio = e.portfolio
            Boolean showOnlySpecificallyAllowed = portfolio?.showOnlySpecificallyAllowed
            Boolean showOnlyCarbonHeroes = portfolio?.showOnlyCarbonHeroes
            List<Entity> entities = portfolioService.getEntities(portfolio)
            CalculationRule ruleToDisplay = indicator?.getResolveCalculationRules(e)?.find({it.calculationRuleId.equalsIgnoreCase(indicator.displayResult)})
            String calculationRuleLocalizedUnit = calculationRuleService.getLocalizedUnit(ruleToDisplay, indicator)
            String indicatorDisplayUnit = indicatorService.getDisplayUnit(indicator, false)

            if (entities) {
                for (Entity entity in entities) {
                    if (!showAllLink && !entity.hasAnyIndicator([indicator])) {
                        showAllLink = true
                    }
                    def childEntities = entityService.getChildEntities(entity, EntityClass.DESIGN.getType())

                    if (childEntities) {
                        if (showOnlyCarbonHeroes) {
                            if (showOnlySpecificallyAllowed) {
                                def allowed = childEntities.findAll({ Entity design -> design.allowAsBenchmark && design.carbonHero})

                                if (allowed) {
                                    designs.addAll(allowed)
                                    entityDesigns.put(entity, allowed)
                                }
                            } else {
                                def allowed = childEntities.findAll({ Entity design -> design.carbonHero})

                                if (allowed) {
                                    designs.addAll(allowed)
                                    entityDesigns.put(entity, allowed)
                                }
                            }
                        } else {
                            if (showOnlySpecificallyAllowed) {
                                designs.addAll(childEntities.findAll({ Entity design -> design.allowAsBenchmark}))
                                entityDesigns.put(entity, childEntities.findAll({ Entity design -> design.allowAsBenchmark}))
                            } else {
                                childEntities.each { Entity design ->
                                    designs.add(design)
                                }
                                entityDesigns.put(entity, childEntities)
                            }
                        }
                    }
                }
            }
            Map graphModel
            Integer maxScore
            Map model

            if (datasetManualIds) {
                String displayRuleId = indicator?.displayResult

                if ("dynamic".equals(denominatorType)) {
                    Double score

                    if (designs && !designs.isEmpty()) {
                        for (Entity design in designs) {
                            List<String> intersection = design?.datasets?.collect({
                                it.manualId
                            })?.intersect(datasetManualIds)

                            if (intersection) {
                                score = design?.getResultByDynamicDenominator(indicator.indicatorId, displayRuleId, intersection.get(0))

                                if (score != null) {
                                    dynamicDenominatorScores.put(design.id, df.format(score))
                                    formattedForTableScores.put(design.id, df.format(score))
                                }

                                if (indicator?.additionalPortfolioCalculationRules) {
                                    Map<String, String> denomScoreByAdditionalRule = [:]

                                    indicator.additionalPortfolioCalculationRules.each { String additionalRuleId ->
                                        score = design?.getResultByDynamicDenominator(indicator.indicatorId, additionalRuleId, intersection.get(0))

                                        if (score != null) {
                                            denomScoreByAdditionalRule.put(additionalRuleId, df.format(score))
                                        }
                                    }
                                    formattedForTableScoresAdditionalRules.put(design.id.toString(), denomScoreByAdditionalRule)
                                }
                            }
                        }
                    }
                    graphModel = getGraphModelForDesignDenominator(portfolio, dynamicDenominatorScores, null, entityDesigns, Boolean.FALSE, indicator?.indicatorId)
                    maxScore = getMaxScoreFromDesignGraphModel(graphModel)
                    model = [indicator               : indicator, entityDesigns: entityDesigns, designs: designs,
                             dynamicDenominatorScores: dynamicDenominatorScores, showAllLink : showAllLink,
                             portfolio               : portfolio, graphModel: graphModel, maxScore: maxScore, formattedForTableScores: formattedForTableScores, user:user, ruleToDisplay:ruleToDisplay,
                             calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit,
                             formattedForTableScoresAdditionalRules: formattedForTableScoresAdditionalRules]
                } else {
                    Double score

                    if (designs && !designs.isEmpty()) {
                        for (Entity design in designs) {
                            score = design?.getTotalResultByNonDynamicDenominator(indicator.indicatorId, displayRuleId, datasetManualIds?.get(0))

                            if (score != null) {
                                denominatorScores.put(design.id, df.format(score))
                                formattedForTableScores.put(design.id, df.format(score))

                            }

                            if (indicator?.additionalPortfolioCalculationRules) {
                                Map<String, String> denomScoreByAdditionalRule = [:]

                                indicator.additionalPortfolioCalculationRules.each { String additionalRuleId ->
                                    score = design?.getTotalResultByNonDynamicDenominator(indicator.indicatorId, additionalRuleId, datasetManualIds?.get(0))

                                    if (score != null) {
                                        denomScoreByAdditionalRule.put(additionalRuleId, df.format(score))
                                    }
                                }
                                formattedForTableScoresAdditionalRules.put(design.id.toString(), denomScoreByAdditionalRule)
                            }
                        }
                    }
                    graphModel = getGraphModelForDesignDenominator(portfolio, null, denominatorScores, entityDesigns, Boolean.FALSE, indicator?.indicatorId)
                    maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)
                    model = [indicator        : indicator, entityDesigns: entityDesigns, designs: designs,
                             denominatorScores: denominatorScores, showAllLink : showAllLink,
                             portfolio        : portfolio, graphModel: graphModel, maxScore: maxScore, formattedForTableScores: formattedForTableScores, user:user, ruleToDisplay:ruleToDisplay,
                             calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit,
                             formattedForTableScoresAdditionalRules: formattedForTableScoresAdditionalRules]

                }
                String templateAsString = g.render(template: "/portfolio/designdenominator", model: model).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)

            } else {
                graphModel = portfolioUtil.getGraphModelForDesigns(portfolio, entityDesigns, indicator, Boolean.TRUE, Boolean.FALSE)
                Integer mScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)
                String templateAsString = g.render(template: "/portfolio/designByIndicator", model: [entityDesigns: entityDesigns, indicator: indicator, portfolio: portfolio, designs: designs, allDesigns: designs, noHideDiv: true,
                                                                                                     showAllLink  : showAllLink, graphModel: graphModel, maxScore: mScore, user: user, ruleToDisplay: ruleToDisplay,
                                                                                                     calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit]).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }

        }
    }


    private Map getGraphModelForDesignDenominator(Portfolio portfolio, Map dynamicDenominatorScores, Map denominatorScores, Map<Entity, List<Entity>> entityDesigns,
                                                  Boolean mainPage = Boolean.FALSE, String indicatorId = null) {
        Map model = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int scoresFound = 0
        Double average

        int entityIndex = 1
        int mainPageIndex = 0
        entityDesigns?.each { Entity parent, List<Entity> designs ->
            int designIndex = 1
            boolean hide = portfolio?.anonymous && !parent.nameVisibleInPortfolio

            designs?.each { Entity design ->
                LcaCheckerResult lcaCheckerResult = design.lcaCheckerResult
                String score

                if (dynamicDenominatorScores) {
                    score = dynamicDenominatorScores?.get(design.id)
                } else {
                    score = denominatorScores?.get(design.id)
                }

                if (score != null) {
                    average = average ? average + score.toDouble() : score.toDouble()
                    String label

                    if (mainPage) {
                        if (parent.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                            label = "${abbreviateString(parent.designPFName ? parent.designPFName : parent.shortName, 20)}, ${abbreviateString(design.name, 15)}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                        } else {
                            label = "${alphabets[mainPageIndex]}"
                            mainPageIndex++
                        }
                    } else {
                        if (hide) {
                            label = "${abbreviateString("${messageSource.getMessage("portfolio.design", [entityIndex, designIndex].toArray(), getLocale())} ${parent?.designPFAnon ? parent?.designPFAnon : ""}", 15)}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                        } else {
                            label = "${abbreviateString(parent.designPFName ? parent.designPFName : parent.shortName, 20)}, ${abbreviateString(design.name, 15)}${lcaCheckerResult ? ", ${lcaCheckerResult.letter} (${lcaCheckerResult.percentage} %)" : ""}"
                        }
                    }
                    model.put(label, score)
                    scoresFound++
                }
                designIndex++
            }
            entityIndex++
        }


        if (scoresFound < 2) {
            model = new TreeMap()
        }

        if (mainPage && model) {
            model = model.sort({ it.value.toDouble() })
            model.put(messageSource.getMessage("mean", null, getLocale()), df.format(average / scoresFound))
        }
        return model
    }

    private Integer getMaxScoreFromDesignGraphModel(Map graphModel) {
        Integer maxScore

        if (graphModel && !graphModel.isEmpty()) {
            graphModel.values().each { String score ->
                if (score && score.isNumber() && (!maxScore || score.toDouble() > maxScore)) {
                    maxScore = (int) score.toDouble()
                }
            }
        }
        return maxScore
    }

    private Integer getMaxScoreFromOperatingGraphModel(Map graphModel) {
        Integer maxScore

        if (graphModel && graphModel.size() > 1) {
            graphModel.values().each {
                it.values()?.each { String score ->
                    if (score && (!maxScore || score.toDouble() > maxScore)) {
                        maxScore = (int) score.toDouble()
                    }
                }
            }
        }

        if (maxScore) {
            int i = 1

            while (maxScore / i > 10) {
                i = i * 10
            }
            maxScore = i * 10 / 2 >= maxScore ? i * 10 / 2 : i * 10
        }
        return maxScore
    }

    private Integer getLargestScoreFromOperatingGraphModel(Map graphModel) {
        Integer maxScore

        if (graphModel && graphModel.size() > 1) {
            graphModel.values().each {
                it.values()?.each { String score ->
                    if (score && (!maxScore || score.toDouble() > maxScore)) {
                        maxScore = (int) score.toDouble()
                    }
                }
            }
        }
        return maxScore
    }

    private Integer getSmallestScoreFromOperatingGraphModel(Map graphModel) {
            Integer minScore

            if (graphModel && graphModel.size() > 1) {
                graphModel.values().each {
                    it.values()?.each { String score ->
                        if (score && !score.equals("0") && (!minScore || score.toDouble() < minScore)) {
                            minScore = (int) score.toDouble()
                        }
                    }
                }
            }
            return minScore
        }

    def renderOperatingDenominatorScores() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        Portfolio portfolio = portfolioService.getPortfolioById(params.id)
        List<Entity> entities = portfolioService.getEntities(portfolio)
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(params.indicatorId, true)
        String denominatorType = params.denominatorType
        String denominatorId = params.denominatorId
        def chosenPeriods = portfolio.chosenPeriods?.sort({ it.toInteger() })
        def selectedPeriods = []
        Map realtyPeriods = new TreeMap()
        Map<String, Double> dynamicDenominatorScores = [:]
        def entitiesByOperatingPeriod = [:]
        def allChildEntities = []
        Map<String, String> formattedTableScores = [:]
        Map<String, Double> denominatorScores = [:]
        List<String> datasetManualIds
        User user = userService.getCurrentUser(true)

        if (params.datasetManualIds) {
            datasetManualIds = ((String) params.datasetManualIds).tokenize(',')
        }
        def showAllLink = false
        List<Entity> operatingPeriodObjects = []

        if (entities && indicator) {
            Boolean showPortfolioSourceListings = params.boolean('showPortFolioSourceListing')
            CalculationRule ruleToDisplay = indicator?.getResolveCalculationRules(null, true)?.find({it.calculationRuleId.equalsIgnoreCase(indicator.displayResult)})
            String calculationRuleLocalizedUnit = calculationRuleService.getLocalizedUnit(ruleToDisplay, indicator)
            String indicatorDisplayUnit = indicatorService.getDisplayUnit(indicator, false)

            entities?.each { Entity entity ->
                List<Entity> operatingPeriods = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())?.findAll({
                    !it.name
                })

                if (!entity.indicators?.contains(indicator)) {
                    showAllLink = true
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

                        if (!operatingPeriodObjects.contains(operatingPeriod)) {
                            operatingPeriodObjects.add(operatingPeriod)
                        }
                    }
                    realtyPeriods.put(entity, operatingPeriodByPeriod)

                    if (!chosenPeriods) {
                        operatingPeriods.collect({ it.operatingPeriod })?.each { period ->
                            if (!selectedPeriods?.contains(period)) {
                                selectedPeriods.add(period)
                            }
                        }
                    } else {
                        selectedPeriods = chosenPeriods
                    }
                }
            }

            if (datasetManualIds) {
                String displayRuleId = indicator.displayResult
                def selectedOpObjects = operatingPeriodObjects.findAll({ Entity e -> selectedPeriods?.contains(e.operatingPeriod) })
                def model
                Map graphModel
                Denominator denominator = indicator.resolveDenominators?.find({denominatorId?.equals(it.denominatorId)})

                if ("dynamic".equals(denominatorType)) {
                    if (operatingPeriodObjects && !operatingPeriodObjects.isEmpty() && displayRuleId) {
                        List<ResultManipulator> resultManipulators = denominatorService.getResultManipulatorsAsManipulatorObjects(denominator?.resultManipulator)

                        Set<Dataset> allEntitiesDatasets = operatingPeriodObjects*.datasets?.flatten() + operatingPeriodObjects*.parentEntity?.findResults{ it?.datasets }?.flatten()
                        ResourceCache resourceCache = ResourceCache.init(allEntitiesDatasets as List)

                        operatingPeriodObjects.each { Entity operatingPeriod ->
                            if (indicator.requireMonthly && denominator) {
                                Dataset dataset
                                def denomDataset
                                if ("monthlyValueReference".equals(denominator.denominatorType)) {
                                    denomDataset = denominatorService.getMonthlyValuereferenceDatasets(operatingPeriod, denominator)
                                    if(denomDataset && !denomDataset.isEmpty()){
                                        dataset = denomDataset.first()
                                    }
                                } else {
                                    denomDataset = denominatorService.getDynamicDenominatorDatasets(operatingPeriod, denominator)
                                    if(denomDataset && !denomDataset.isEmpty()){
                                        dataset = denomDataset.first()
                                    }
                                }
                                Double totalForRule = operatingPeriod.getTotalResult(indicator.indicatorId, displayRuleId)
                                Double denominatorQuantity

                                try {
                                    denominatorQuantity = DomainObjectUtil.convertStringToDouble(dataset.answerIds.first().toString())
                                } catch (Exception e) {}

                                if (totalForRule && denominatorQuantity) {
                                    Double sum = totalForRule / denominatorQuantity

                                    if (sum != null) {
                                        if (resultManipulators) {
                                            resultManipulators.each {
                                                sum = denominatorUtil.useResultManipulatorForDoubleValue(it, operatingPeriod, sum, resourceCache)
                                            }
                                        }
                                        dynamicDenominatorScores.put(operatingPeriod.id, sum)
                                        formattedTableScores.put(operatingPeriod.id, df.format(sum))
                                    }
                                }
                            } else {
                                List<String> intersection = operatingPeriod.datasets?.collect({
                                    it.manualId
                                })?.intersect(datasetManualIds)

                                if (intersection && !intersection.isEmpty()) {
                                    Double score = operatingPeriod.getResultByDynamicDenominator(indicator.indicatorId, displayRuleId, intersection.get(0))

                                    if (score != null) {
                                        dynamicDenominatorScores.put(operatingPeriod.id, score)
                                        formattedTableScores.put(operatingPeriod.id, df.format(score))
                                    } else {
                                        Map<String, Double> monthlyResults = operatingPeriod.getResultByDynamicDenominator(indicator.indicatorId, displayRuleId, intersection.get(0), Boolean.TRUE, denominator)

                                        if (monthlyResults && monthlyResults.values()) {
                                            Double monthlyTotal = monthlyResults.values().sum()

                                            if (monthlyTotal != null) {
                                                dynamicDenominatorScores.put(operatingPeriod.id, monthlyTotal)
                                                formattedTableScores.put(operatingPeriod.id, df.format(monthlyTotal))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        graphModel = getGraphModelForOperatingDenominator(portfolio, dynamicDenominatorScores, null, selectedPeriods?.sort(), realtyPeriods, Boolean.FALSE)
                        Integer maxScore = getMaxScoreFromOperatingGraphModel(graphModel)
                        Map<String, Double> minScore = portfolioUtil.getSmallestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                        Map<String, Double> topScore = portfolioUtil.getLargestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                        model = [portfolio: portfolio, realties: entities, realtyPeriods: realtyPeriods, operatingPeriods: selectedPeriods?.sort(), dynamicDenominatorScores: dynamicDenominatorScores, dynamicDenominator: true,
                                 indicator: indicator, operatingPeriodObjects: operatingPeriodObjects, selectedOpObjects: selectedOpObjects, graphModel: graphModel, maxScore: maxScore, formattedTableScores: formattedTableScores,
                                 showAllLink: showAllLink, minScore: minScore, topScore: topScore, user: user, ruleToDisplay: ruleToDisplay,
                                 calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit,
                                 showPortfolioSourceListings: showPortfolioSourceListings]
                        String templateAsString = g.render(template: "/portfolio/operatingdenominator", model: model).toString()
                        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    }
                } else {
                    if (operatingPeriodObjects && !operatingPeriodObjects.isEmpty() && displayRuleId) {
                        Double score
                        operatingPeriodObjects.each { Entity operatingPeriod ->
                            score = operatingPeriod.getTotalResultByNonDynamicDenominator(indicator.indicatorId, displayRuleId, datasetManualIds?.get(0))

                            if (score != null) {
                                denominatorScores.put(operatingPeriod.id, score)
                            }

                        }
                    }


                    graphModel = getGraphModelForOperatingDenominator(portfolio, null, denominatorScores, selectedPeriods?.sort(), realtyPeriods, Boolean.FALSE)
                    Integer maxScore = getMaxScoreFromOperatingGraphModel(graphModel)
                    Map<String, Double> minScore = portfolioUtil.getSmallestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                    Map<String, Double> topScore = portfolioUtil.getLargestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                    model = [portfolio: portfolio, realties: entities, realtyPeriods: realtyPeriods, operatingPeriods: selectedPeriods?.sort(),
                             indicator: indicator, operatingPeriodObjects: operatingPeriodObjects, selectedOpObjects: selectedOpObjects,
                             graphModel: graphModel, maxScore: maxScore, minScore: minScore, topScore: topScore, user:user, ruleToDisplay:ruleToDisplay,
                             calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit,
                             showPortfolioSourceListings:showPortfolioSourceListings]
                    String templateAsString = g.render(template: "/portfolio/operatingdenominator", model: model).toString()
                    render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                }
            } else {
                Map graphModel = portfolioUtil.getGraphModelForOperating(portfolio, realtyPeriods, selectedPeriods?.sort(), indicator, Boolean.TRUE, Boolean.FALSE)
                Integer maxScore
                Map<String, Double> topScore
                Map<String, Double> minScore

                if (graphModel && !graphModel.isEmpty()) {
                    maxScore = getMaxScoreFromOperatingGraphModel(graphModel)
                    topScore = portfolioUtil.getLargestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                    minScore = portfolioUtil.getSmallestScoreFromOperatingGraphModel(graphModel, selectedPeriods)
                }
                String templateAsString = g.render(template: "/portfolio/operatingByIndicator", model: [indicator: indicator, operatingPeriods: selectedPeriods, entitiesByOperatingPeriod: entitiesByOperatingPeriod, noHideDiv: true,
                                                                                                        entities : entities, entityPeriods: realtyPeriods,
                                                                                                        showAll  : showAllLink, graphModel: graphModel, maxScore: maxScore,
                                                                                                        topScore : topScore, minScore: minScore, portfolio: portfolio, user: user, ruleToDisplay: ruleToDisplay,
                                                                                                        calculationRuleLocalizedUnit: calculationRuleLocalizedUnit, indicatorDisplayUnit: indicatorDisplayUnit,
                                                                                                        showPortfolioSourceListings: showPortfolioSourceListings]).toString()
                render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        }
    }


    private Map getGraphModelForOperatingDenominator(Portfolio portfolio,
                                                     Map dynamicDenominatorScores, Map denominatorScores,
                                                     List<String> operatingPeriods, Map entityPeriods, Boolean mainPage = Boolean.FALSE) {
        Map model = new LinkedHashMap()
        Map modelForSum = new LinkedHashMap()
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
        DecimalFormat df = new DecimalFormat("#.##", symbols)
        int entityIndex = 1
        int mainPageIndex = 0
        Map scoreAmountByPeriod = [:]
        Map average = [:]

        entityPeriods?.each { Entity entity, Map<String, Entity> operatingPeriodByPeriod ->
            int scoresFound = 0
            Double sum = 0
            Map scoreByPeriod = new TreeMap()

            operatingPeriods?.each { String operatingPeriod ->
                Entity child = operatingPeriodByPeriod?.get(operatingPeriod)

                if (child) {
                    def score

                    if (dynamicDenominatorScores) {
                        score = dynamicDenominatorScores?.get(child.id)
                    } else {
                        score = denominatorScores?.get(child.id)
                    }

                    if (score) {
                        sum = sum + score
                        scoreByPeriod.put(operatingPeriod, df.format(score.toDouble()))
                        scoresFound++
                        def existingAverage = average.get(operatingPeriod)

                        if (existingAverage) {
                            average.put(operatingPeriod, (existingAverage + score.toDouble()))
                        } else {
                            average.put(operatingPeriod, score.toDouble())
                        }
                    } else {
                        scoreByPeriod.put(operatingPeriod, "0")
                    }
                } else {
                    scoreByPeriod.put(operatingPeriod, "0")
                }
            }

            if (scoresFound) {
                operatingPeriods?.each { String operatingPeriod ->
                    def scoreAmount = scoreAmountByPeriod.get(operatingPeriod)
                    scoreAmountByPeriod.put(operatingPeriod, (scoreAmount ? scoreAmount + 1 : 1))
                }
                String label

                if (mainPage) {
                    if (entity.managerIds?.contains(userService.getCurrentUser()?.id?.toString())) {
                        label = "${abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)}"
                    } else {
                        label = "${alphabets[mainPageIndex]}"
                        mainPageIndex++
                    }
                } else {
                    if (portfolio?.anonymous && !entity.nameVisibleInPortfolio) {
                        label = abbreviateString("${messageSource.getMessage("portfolio.target", [entityIndex].toArray(), getLocale())} ${entity.operatingPFAnon ? entity.operatingPFAnon : ""}", 20)
                    } else {
                        label = abbreviateString(entity.operatingPFName ? entity.operatingPFName : entity.shortName, 20)
                    }
                }
                model.put(label, scoreByPeriod)
                modelForSum.put(label, sum)
            }
            entityIndex++
        }

        if (model && model.keySet().toList().size() < 2) {
            model = null
        }

        if (mainPage && modelForSum && model) {
            Map sortedModel = new LinkedHashMap()
            modelForSum = modelForSum.sort({ it.value })

            modelForSum.each { String key, Double value ->
                sortedModel.put(key, model.get(key))
            }
            Map resolvedAverage = new LinkedHashMap()

            average?.each { String operatingPeriod, Double value ->
                resolvedAverage.put(operatingPeriod, df.format(value / scoreAmountByPeriod.get(operatingPeriod)))
            }
            sortedModel.put(messageSource.getMessage("mean", null, getLocale()), resolvedAverage)
            return sortedModel
        } else {
            if (portfolio?.sortByDesc && model) {
                Map sortedModel = new LinkedHashMap()
                modelForSum = modelForSum.sort({ -it.value })

                modelForSum.each { String key, Double value ->
                    sortedModel.put(key, model.get(key))
                }

                return sortedModel

            } else {
                return model
            }
        }
    }

    private String abbreviateString(String value, Integer maxLength) {
        String abbreviatedString

        if (value && maxLength) {
            if (value.length() > maxLength) {
                abbreviatedString = StringUtils.substring(value,
                        0, maxLength) + "..."
            } else {
                abbreviatedString = value
            }
        } else {
            abbreviatedString = ""
        }
        return abbreviatedString
    }

    private Locale getLocale() {
        GrailsWebRequest webRequest = WebUtils.retrieveGrailsWebRequest()
        HttpServletRequest request = webRequest.getRequest()
        HttpServletResponse response = webRequest.getResponse()
        HttpSession session = webRequest.getSession()
        return localeResolverUtil.resolveLocale(session, request, response)
    }

}