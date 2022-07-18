/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FrameStatus
import com.bionova.optimi.core.domain.mongo.HelpConfiguration
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorBenchmark
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.UpdatePublish
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.util.UnitConversionUtil
import grails.converters.JSON
import org.bson.Document

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * @author Pasi-Markus Mäkelä
 *
 * Main Screen Controller, used for "populating" 3 sections
 *      -> up to date news
 *      -> buildings managed by current user
 *      -> current user tasks
 */
class MainController extends AbstractDomainObjectController {

    def entityService
    def taskService
    def configurationService
    def systemService
    def optimiResourceService
    def queryService
    def messageSource
    def channelFeatureService
    def portfolioUtil
    def denominatorUtil
    def licenseService
    def indicatorService
    def loggerUtil
    def accountService
    def helpConfigurationService
    def applicationService
    def datasetService
    def indicatorBenchmarkService
    def workFlowService
    def updatePublishService
    def nmdApiService

    /**
     * Default method of main page controller
     * Returns - the list of buildings
     *         - the list of tasks
     */
    def list() {
        long now = System.currentTimeMillis()
        systemService.logMemoryUsage()
        Boolean skipEmailActivateModal = params.boolean("skipEmailActivateModal")
        if (session["settingsSet"]) {
            skipEmailActivateModal = Boolean.TRUE
        }
        Boolean login = params.boolean("login")

        if (login) {
            userService.logUserBrowserAndOs()
            Boolean validBrowser = userService.isValidBrowser(request)

            if (!validBrowser) {
                flash.warningAlert = message(code: "main.invalid_browser")
            }
        }
        def notification = userService.getUnacknowledgedNotification(session)
        def notificationHeading
        def notificationContent
        def notificationId

        if (notification) {
            notificationHeading = "<h4>${notification.localizedHeading}</h4>"?.replaceAll("\"", "'")
            notificationContent = notification.localizedText?.replaceAll("\"", "'")
            notificationId = notification.id
        }
        Boolean superUserSearchEnabled = new Boolean(params.superuserEnabled)

        if (superUserSearchEnabled) {
            flash.fadeSuccessAlert = message(code: "main.superuser.search.enabled")
        }
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)

        Map portfolioModel

        if (channelFeature?.portfolioIds) {
            portfolioModel = [portfolioHtml: getPortfolioModel(channelFeature), showPortfolio: true, benchmarkName: channelFeature.benchmarkName]
        }
        Map<Boolean, List<Entity>> entitiesWithAllFoundInfo = getFirstEntities(superUserSearchEnabled)
        Boolean allFound = entitiesWithAllFoundInfo?.keySet()?.getAt(0)
        List<Entity> entities
        List<Entity> publicEntities

        if (allFound != null) {
            entities = entitiesWithAllFoundInfo.get(allFound)
        }
        List<Document> users
        def thumbImages
        Query basicQuery = queryService.getBasicQuery()
        def entityWithCertificationList = [:]
        def entityWithCarbonBenchmarkScore = [:]

        if (entities) {
            thumbImages = entityService.getThumbImagesForEntities(entities)
            users = userService.getUsersAsDocumentsByEntities(entities)
            publicEntities = entities.findAll({ Entity e -> e.public })

            String indicatorId = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, "benchmarkingIndicatorId").value
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            List<IndicatorBenchmark> indicatorBenchmarks = indicatorBenchmarkService.getAllIndicatorBenchmarks()

            entities.each { Entity entity ->
                def datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, basicQuery?.queryId, 'basicQuerySection', 'certification')

                if (datasets) {
                    String resourceId = datasets.get(0).resourceId
                    if (resourceId) {
                        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, 'default')
                        if (resource) {
                            String imgLink = '/static/logoCertificate/' + resourceId + '.png'
                            Map<String, String> resourceDetail = [name: optimiResourceService.getLocalizedName(resource), img: imgLink]
                            entityWithCertificationList.put(entity.id, resourceDetail)
                        }
                    }
                }

                String projectBuildingType = entity.typeResourceId

                if (indicator && indicator.isBenchmarkable(projectBuildingType) && entity.indicatorIds?.contains(indicatorId)) {
                    Document chosenDesign = entityService.getChosenEntityForMainList(entity.id)
                    String parentCountryResourceId = entity.countryResourceResourceId

                    if (chosenDesign && indicatorBenchmarks && projectBuildingType) {
                        IndicatorBenchmark indicatorBenchmark = indicatorBenchmarkService.getBenchmarkForProjectCountryAndType(indicatorBenchmarks, parentCountryResourceId, projectBuildingType)

                        Double score = entityService.getDisplayResultForMainList(chosenDesign, indicator)

                        if (score && indicatorBenchmark) {
                            Map<String, String> chosenBenchmark = [name: indicatorBenchmark.benchmarkName, score: score.round().toString(), index: indicatorBenchmarkService.resultBenchmarkIndex(indicatorBenchmark, score)]
                            entityWithCarbonBenchmarkScore.put(entity.id, chosenBenchmark)
                        }
                    }
                }
            }
            if (publicEntities) {
                entities.removeAll(publicEntities)
            }
        }
        User user = userService.getCurrentUser(true)
        Account account = userService.getAccount(user)
        Account requestToJoinAccount = userService.getRequestToJoinAccount(user.id)
        List<Account> possibleAccounts

        if (!account && !requestToJoinAccount) {
            possibleAccounts = populateUserAccountsOnFirstLogin()
        }
        Boolean hideEcommerceFeatures = Boolean.FALSE

        if (channelFeature?.hideEcommerce) {
            hideEcommerceFeatures = Boolean.TRUE
        }

        if (requestToJoinAccount) {
            flash.fadeSuccessAlert = "${message(code: 'account.request.to_join', args: [requestToJoinAccount.companyName])}"
        }

        def languages = optimiResourceService.getSystemLocales(channelFeature)
        def unitSystems = UnitConversionUtil.UnitSystem.values()

        if (!user?.internalUseRoles) {
            // Dont allow both for non internal users
            unitSystems = unitSystems.findAll({ !UnitConversionUtil.UnitSystem.METRIC_AND_IMPERIAL.value.equals(it.value) })
        }

        def userLocales = [(message(code: 'locale.fi')): new Locale("fi"), (message(code: 'locale.' + Locale.US)): Locale.US, (message(code: 'locale.' + Locale.UK)): Locale.UK]
        Double secondsTaken = (System.currentTimeMillis() - now) / 1000
        Boolean showRenderingTimes = userService.getShowDevDebugNotifications(user?.enableDevDebugNotifications)

        List<HelpConfiguration> helpConfigurations = helpConfigurationService.getConfigurationsForTargetPage("Main") ?: []
        List<License> autoStartTrials = []
        boolean trialsAvailable = false

        if (user && user?.trialCount < 1 && channelFeature?.enableAutomaticTrial) {
            autoStartTrials = licenseService.getActiveAutoStartTrials()

            if (autoStartTrials) {
                if (user.country && autoStartTrials.find({ it.countries?.contains(user.country) })) {
                    autoStartTrials = autoStartTrials.sort({ a, b -> a.countries?.contains(user.country) <=> b.countries?.contains(user.country) ?: b.name <=> a.name }).reverse()
                } else {
                    autoStartTrials = autoStartTrials.sort({ a, b -> !a.countries <=> !b.countries ?: b.name <=> a.name }).reverse()
                }
                trialsAvailable = true
            }
        }

        List<String> highlightTrialList = []

        if (user?.country) {
            highlightTrialList = autoStartTrials?.findAll { it.countries?.contains(user.country) }
        }

        if (!highlightTrialList) {
            highlightTrialList = autoStartTrials?.findAll { !it.countries } ?: []
        }

        //***************************************************************************
        //FRAME STATUS
        //***************************************************************************
        FrameStatus frameStatus = workFlowService.loadFrame(user, helpConfigurations, channelFeature)

        //***************************************************************************

        //**************************************************************************
        //SHOW FIXED UPDATE BAR
        //***************************************************************************
        Boolean showUpdateBar = Boolean.TRUE
        UpdatePublish popUpUpdate
        List<UpdatePublish> sortedUpdateList = []

        if (channelFeature?.disableUpdateSideBar) {
            showUpdateBar = Boolean.FALSE
        }
        def updateTime = System.currentTimeMillis()
        if (showUpdateBar) {
            List<UpdatePublish> updatePublishList = updatePublishService.getAllUpdatePublish(true, channelFeature, true)

            List<License> licenses = []

            if (!user?.internalUseRoles) {
                List<License> userLicenses = userService.getLicenses(user)
                List<License> accountLicenses = accountService.getLicenses(account?.licenseIds)

                if (userLicenses) {
                    licenses.addAll(userLicenses)
                }
                if (accountLicenses) {
                    licenses.addAll(accountLicenses)
                }
            }
            sortedUpdateList = updatePublishService.sortingAlgorithmUpdatePublishList(updatePublishList, user, licenses)
            def foundTempPopUp = sortedUpdateList?.find({ it.showAsPopUp })

            if (sortedUpdateList && sortedUpdateList.find({ it.showAsPopUp }) && (!user?.acknowledgedUpdates || (user?.acknowledgedUpdates && !user?.acknowledgedUpdates.contains(foundTempPopUp?.id.toString())))) {
                popUpUpdate = foundTempPopUp
            }
        }
        def laterTime = System.currentTimeMillis() - updateTime
        log.info("TIME GET UPDATE " + (laterTime).toString())

        //****************************************************************************
        Boolean showFloatingLicense = session?.getAttribute("showFloatingLicense")

        if (showFloatingLicense) {
            session.removeAttribute("showFloatingLicense")
        }
        // List of license names, where training not taken for showing a message for the user
        String licensesWhereTrainingNotTaken = resolveLicensesTrainingNotTaken(user)

        if (licensesWhereTrainingNotTaken) {
            flash.warningAlert = message(code: "main.license.trainingTaken.false", args: licensesWhereTrainingNotTaken)
        }
        Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(null, [Feature.CHAT_SUPPORT])
        Boolean chatSupportLicensed = featuresAllowed.get(Feature.CHAT_SUPPORT)
        Map model = [showSearch           : true, notificationHeading: notificationHeading, notificationContent: notificationContent, notification: notification, notificationId: notificationId, popUpUpdate: popUpUpdate,
                     entities             : entities, publicEntities: publicEntities, users: users, thumbImages: thumbImages, allFound: allFound, basicQuery: basicQuery, entityWithCertificationList: entityWithCertificationList, entityWithCarbonBenchmarkList: entityWithCarbonBenchmarkScore,
                     superuserEnabled     : superUserSearchEnabled, account: account, currentUser: user, trialUrl: channelFeature?.trialUrl, user: user, trialsAvailable: trialsAvailable,
                     hideEcommerceFeatures: hideEcommerceFeatures, possibleAccounts: possibleAccounts, languages: languages, unitSystems: unitSystems, userLocales: userLocales, skipEmailActivateModal: skipEmailActivateModal,
                     secondsTaken         : secondsTaken, showRenderingTimes: showRenderingTimes, helpConfigurations: helpConfigurations, autoStartTrials: autoStartTrials, chatSupportLicensed: chatSupportLicensed,
                     highlightTrialList   : highlightTrialList, frameStatus: frameStatus, userForFloatingLicense: user, showFloatingLicense: showFloatingLicense, showUpdateBar: showUpdateBar, sortedUpdateList: sortedUpdateList]

        if (portfolioModel) {
            model.putAll(portfolioModel)
        }
        return model
    }

    def killPrompt() {
        String returnable = ""
        session?.removeAttribute("showLoginPrompt")
        session?.removeAttribute("testLoginPrompt")
        session?.setAttribute("noPrompt", "true")
        returnable = "removed prompt from session ${session?.getAttribute("noPrompt")}"
        render text: returnable
    }

    def addPromptForTesting() {
        session["testLoginPrompt"] = true
        redirect action: "list"
    }

    private String resolveLicensesTrainingNotTaken(User user) {
        List<License> licenses = userService.getUsableAndLicensedLicenses(user)
        String trainingNotTakenLicenses

        licenses?.each {
            if (!it.trainingTaken) {
                if (trainingNotTakenLicenses) {
                    trainingNotTakenLicenses = "${trainingNotTakenLicenses}, ${it.name}"
                } else {
                    trainingNotTakenLicenses = it.name
                }
            }
        }
        return trainingNotTakenLicenses
    }

    private Map<Boolean, List<Entity>> getFirstEntities(Boolean superUserSearchEnabled) {
        List<String> entityClasses = optimiResourceService.getParentEntityClassIds()
        Map<Boolean, List<Entity>> entities = [:]

        if (entityClasses) {
            entities = entityService.getEntityMainList(entityClasses, null, 30, session, superUserSearchEnabled)
        }
        return entities
    }

    def entities() {
        def entityClasses = optimiResourceService.getParentEntityClassIds()
        List<Entity> entities
        List<Entity> publicEntities

        if (entityClasses) {
            Boolean superUserSearchEnabled = new Boolean(params.superuserEnabled)
            entities = entityService.getEntityMainList(entityClasses, 31, 60, session, superUserSearchEnabled)?.values()?.toList()?.get(0)
        }
        def users
        def tasks
        def thumbImages

        if (entities) {
            thumbImages = entityService.getThumbImagesForEntities(entities)
            users = userService.getUsersAsDocumentsByEntities(entities)
            publicEntities = entities.findAll({ Entity e -> e.public })

            if (publicEntities) {
                entities.removeAll(publicEntities)
            }
        }
        Query basicQuery = queryService.getBasicQuery()
        String templateAsString = g.render(template: "/main/entityList", model: [entities   : entities, publicEntities: publicEntities, users: users, tasks: tasks,
                                                                                 thumbImages: thumbImages, basicQuery: basicQuery]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def entitiesByName() {
        String name = params.name
        List<Entity> entities
        List<Entity> publicEntities
        def users
        def tasks
        def thumbImages

        if (name && name.length() > 2) {
            entities = entityService.searchEntitiesByName(name)
        }
        Query basicQuery = queryService.getBasicQuery()
        def entityWithCertificationList = [:]
        def entityWithCarbonBenchmarkScore = [:]

        if (entities) {
            thumbImages = entityService.getThumbImagesForEntities(entities)
            users = userService.getUsersAsDocumentsByEntities(entities)
            publicEntities = entities.findAll({ Entity e -> e.public })

            String indicatorId = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, "benchmarkingIndicatorId").value
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            List<IndicatorBenchmark> indicatorBenchmarks = indicatorBenchmarkService.getAllIndicatorBenchmarks()

            entities.each { Entity entity ->
                def datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, basicQuery?.queryId, 'basicQuerySection', 'certification')

                if (datasets) {
                    String resourceId = datasets.get(0).resourceId
                    if (resourceId) {
                        Resource resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, 'default')
                        if (resource) {
                            String imgLink = '/static/logoCertificate/' + resourceId + '.png'
                            Map<String, String> resourceDetail = [name: optimiResourceService.getLocalizedName(resource), img: imgLink]
                            entityWithCertificationList.put(entity.id, resourceDetail)
                        }
                    }
                }
                Document chosenDesign = entityService.getChosenEntityForMainList(entity.id)
                String projectBuildingType = entity.typeResourceId
                String parentCountryResourceId = entity.countryResourceResourceId

                if (indicatorId && chosenDesign && indicatorBenchmarks && projectBuildingType) {
                    IndicatorBenchmark indicatorBenchmark

                    if (parentCountryResourceId && projectBuildingType) {
                        indicatorBenchmark = indicatorBenchmarks.find({ it.areas?.contains(parentCountryResourceId) && it.buildingTypes?.contains(projectBuildingType) })
                    }

                    if (!indicatorBenchmark && parentCountryResourceId) {
                        indicatorBenchmark = indicatorBenchmarks.find({ it.areas?.contains(parentCountryResourceId) })
                    }

                    if (!indicatorBenchmark && projectBuildingType) {
                        indicatorBenchmark = indicatorBenchmarks.find({ it.buildingTypes?.contains(projectBuildingType) })
                    }

                    if (!indicatorBenchmark) {
                        indicatorBenchmark = indicatorBenchmarks.first()
                    }

                    Double score = entityService.getDisplayResultForMainList(chosenDesign, indicator)

                    if (score && indicatorBenchmark) {
                        Map<String, String> chosenBenchmark = [name: indicatorBenchmark.benchmarkName, score: score.round().toString(), index: indicatorBenchmarkService.resultBenchmarkIndex(indicatorBenchmark, score)]
                        entityWithCarbonBenchmarkScore.put(entity.id, chosenBenchmark)
                    }
                }
            }

            if (publicEntities) {
                entities.removeAll(publicEntities)
            }
        }

        String templateAsString = g.render(template: "/main/entityList", model: [entities   : entities, publicEntities: publicEntities, users: users, tasks: tasks,
                                                                                 thumbImages: thumbImages, basicQuery: basicQuery, entityWithCarbonBenchmarkList1: entityWithCarbonBenchmarkScore, entityWithCertificationList1: entityWithCertificationList]).toString()
        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    private String getPortfolioModel(ChannelFeature channelFeature) {
        List<Indicator> allIndicators = []
        Map<Indicator, List<Entity>> entitiesByIndicator = new LinkedHashMap<Indicator, List<Entity>>()
        Map graphModelByIndicator = [:]
        Map maxScoreByIndicator = [:]
        Map childEntitiesByIndicator = [:]
        Map chosenPeriodsByIndicators = [:]
        Map entityDesignsByIndicator = [:]
        Map entityPeriodsByIndicator = [:]
        List<Portfolio> portfolios = channelFeatureService.getPortfolios(channelFeature.portfolioIds)

        if (portfolios && !portfolios.isEmpty()) {
            for (int i = 0; i < portfolios.size(); i++) {
                Portfolio portfolio = portfolios.get(i)
                List<Indicator> indicators = portfolio.indicators

                if (indicators && !indicators.isEmpty()) {
                    indicators.each { Indicator indicator ->
                        if (portfolio.chosenPeriods) {
                            chosenPeriodsByIndicators.put(indicator.indicatorId, portfolio.chosenPeriods?.sort({
                                it.toInteger()
                            }))
                        }

                        if (!allIndicators.contains(indicator)) {
                            allIndicators.add(indicator)
                            List<Entity> entities = portfolio.entities
                            List<Entity> userEntities = entityService.getUserEntitiesByClassesAndIndicatorIds([portfolio.entityClass], [indicator.indicatorId])

                            if (userEntities) {
                                if (entities) {
                                    entities.addAll(userEntities)
                                } else {
                                    entities = userEntities
                                }
                            }

                            if (entities) {
                                entitiesByIndicator.put(indicator, entities.unique())
                            }
                        }
                    }
                }
            }

            entitiesByIndicator?.each { Indicator indicator, List<Entity> entities ->
                if ("design".equals(indicator.indicatorUse)) {
                    Map entityDesigns = new LinkedHashMap()
                    List<Entity> childEntities = []

                    entities?.each { Entity e ->
                        def designs = entityService.getChildEntities(e, Constants.EntityClass.DESIGN.getType())

                        if (designs) {
                            childEntities.addAll(designs)
                            entityDesigns.put(e, designs)
                        }
                    }

                    if (childEntities && !childEntities.isEmpty()) {
                        childEntitiesByIndicator.put(indicator.indicatorId, childEntities)
                    }

                    if (entityDesigns) {
                        entityDesignsByIndicator.put(indicator.indicatorId, entityDesigns)
                    }
                    def graphModel = portfolioUtil.getGraphModelForDesigns(null, entityDesigns, indicator, Boolean.TRUE, Boolean.TRUE)

                    if (graphModel && !graphModel.isEmpty()) {
                        graphModelByIndicator.put(indicator.indicatorId, graphModel)
                        Integer maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)

                        if (maxScore) {
                            maxScoreByIndicator.put(indicator.indicatorId, maxScore)
                        }
                    }
                } else {
                    def chosenPeriods = chosenPeriodsByIndicators.get(indicator.indicatorId)?.sort({
                        it.toInteger()
                    })
                    session?.setAttribute("chosenPeriodsByIndicators", chosenPeriodsByIndicators)
                    Map entityPeriods = new LinkedHashMap()
                    def entitiesByOperatingPeriod = [:]
                    def allChildEntities = []

                    if (chosenPeriods) {
                        entities?.each { Entity e ->
                            List<Entity> childEntities = entityService.getChildEntities(e, Constants.EntityClass.OPERATING_PERIOD.getType())

                            if (childEntities) {
                                def operatingPeriods = []

                                chosenPeriods.each { String period ->
                                    List<Entity> found = childEntities.findAll({
                                        period.equals(it.operatingPeriod)
                                    })
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
                    }

                    if (allChildEntities) {
                        childEntitiesByIndicator.put(indicator.indicatorId, allChildEntities)
                    }

                    if (entityPeriods) {
                        entityPeriodsByIndicator.put(indicator.indicatorId, entityPeriods)
                        Map graphModel = portfolioUtil.getGraphModelForOperating(null, entityPeriods, chosenPeriods, indicator, Boolean.TRUE, Boolean.TRUE)

                        if (graphModel && !graphModel.isEmpty()) {
                            graphModelByIndicator.put(indicator.indicatorId, graphModel)
                            Integer maxScore = portfolioUtil.getMaxScoreFromOperatingGraphModel(graphModel)

                            if (maxScore) {
                                maxScoreByIndicator.put(indicator.indicatorId, maxScore)
                            }
                        }
                    }
                }

            }
        }
        session?.setAttribute("entityDesignsByIndicator", entityDesignsByIndicator)
        session?.setAttribute("childEntitiesByIndicator", childEntitiesByIndicator)
        session?.setAttribute("entityPeriodsByIndicator", entityPeriodsByIndicator)
        return g.render(template: "benchmark", model: [indicators               : allIndicators, graphModelByIndicator: graphModelByIndicator,
                                                       maxScoreByIndicator      : maxScoreByIndicator, childEntitiesByIndicator: childEntitiesByIndicator,
                                                       chosenPeriodsByIndicators: chosenPeriodsByIndicators, benchmarkName: channelFeature.benchmarkName])
    }

    def renderIndicatorDenominator() {
        if (params.indicatorId) {
            String indicatorId = params.indicatorId
            String unit = params.unit
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)
            List<Entity> childEntities = session?.getAttribute("childEntitiesByIndicator")?.get(indicatorId)
            String denominatorType = params.denominatorType
            List<String> datasetManualIds
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("en", "US"))
            DecimalFormat df = new DecimalFormat("#.##", symbols)
            User user = userService.getCurrentUser(true)

            if (params.unit) {
                datasetManualIds = ((String) params.unit).tokenize(',')
            }
            Map dynamicDenominatorScores = [:]
            Map<String, Double> denominatorScores = [:]
            Boolean dynamicDenominator = Boolean.FALSE

            if (indicator && childEntities) {
                if (unit) {
                    String displayRuleId = indicator?.displayResult

                    if ("dynamic".equals(denominatorType)) {
                        Double score

                        if (childEntities && !childEntities.isEmpty()) {
                            for (Entity childEntity in childEntities) {
                                List<String> intersection = childEntity?.datasets?.collect({
                                    it.manualId
                                })?.intersect(datasetManualIds)

                                if (intersection) {
                                    score = childEntity?.getResultByDynamicDenominator(indicator.indicatorId, displayRuleId, intersection.get(0))

                                    if (score != null) {
                                        dynamicDenominatorScores.put(childEntity.id, df.format(score))
                                    }
                                }
                            }
                        }
                    } else {
                        Double score

                        if (childEntities && !childEntities.isEmpty()) {
                            for (Entity childEntity in childEntities) {
                                score = childEntity?.getTotalResultByNonDynamicDenominator(indicator.indicatorId, displayRuleId, datasetManualIds?.get(0))

                                if (score != null) {
                                    Double formatScore = df.format(score)?.toDouble()
                                    denominatorScores.put(childEntity.id?.toString(), formatScore)

                                }
                            }
                        }
                    }

                    Map graphModel
                    Integer maxScore

                    if ("design".equals(indicator.indicatorUse)) {
                        Map entityDesigns = session?.getAttribute("entityDesignsByIndicator")?.get(indicatorId)

                        if (dynamicDenominator) {
                            graphModel = portfolioUtil.getGraphModelForDesignDenominator(null, dynamicDenominatorScores, null, entityDesigns, Boolean.TRUE)
                            maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)
                        } else {
                            graphModel = portfolioUtil.getGraphModelForDesignDenominator(null, null, denominatorScores, entityDesigns, Boolean.TRUE)
                            maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)
                        }
                        String templateAsString = g.render(template: "/portfolio/designdenominator", model: [graphModel: graphModel, maxScore: maxScore, indicator: indicator, mainPage: true, user: user]).toString()
                        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    } else {
                        List<String> selectedPeriods = session?.getAttribute("chosenPeriodsByIndicators")?.get(indicatorId)?.sort()
                        Map entityPeriods = session?.getAttribute("entityPeriodsByIndicator")?.get(indicatorId)
                        if (dynamicDenominator) {
                            graphModel = portfolioUtil.getGraphModelForOperatingDenominator(null, dynamicDenominatorScores, null, selectedPeriods?.sort(), entityPeriods, Boolean.TRUE)
                            maxScore = portfolioUtil.getMaxScoreFromOperatingGraphModel(graphModel)
                        } else {
                            graphModel = portfolioUtil.getGraphModelForOperatingDenominator(null, null, denominatorScores, selectedPeriods?.sort(), entityPeriods, Boolean.TRUE)
                            maxScore = portfolioUtil.getMaxScoreFromOperatingGraphModel(graphModel)
                        }
                        String templateAsString = g.render(template: "/portfolio/operatingdenominator", model: [graphModel: graphModel, maxScore: maxScore, indicator: indicator, operatingPeriods: selectedPeriods, mainPage: true, user: user]).toString()
                        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    }
                } else {
                    Map graphModel

                    if ("design".equals(indicator.indicatorUse)) {
                        Map entityDesigns = session?.getAttribute("entityDesignsByIndicator")?.get(indicatorId)
                        graphModel = portfolioUtil.getGraphModelForDesigns(null, entityDesigns, indicator, Boolean.TRUE, Boolean.TRUE)
                        Integer maxScore = portfolioUtil.getMaxScoreFromDesignGraphModel(graphModel)
                        String templateAsString = g.render(template: "/portfolio/designByIndicator", model: [indicator: indicator, graphModel: graphModel, maxScore: maxScore, mainPage: true, user: user]).toString()
                        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    } else {
                        Map entityPeriods = session?.getAttribute("entityPeriodsByIndicator")?.get(indicatorId)
                        List<String> selectedPeriods = session?.getAttribute("chosenPeriodsByIndicators")?.get(indicatorId)?.sort()
                        graphModel = portfolioUtil.getGraphModelForOperating(null, entityPeriods, selectedPeriods, indicator, Boolean.TRUE, Boolean.TRUE)
                        Integer maxScore = portfolioUtil.getMaxScoreFromOperatingGraphModel(graphModel)
                        String templateAsString = g.render(template: "/portfolio/operatingByIndicator", model: [indicator: indicator, graphModel: graphModel, maxScore: maxScore, operatingPeriods: selectedPeriods, mainPage: true, user: user]).toString()
                        render([output: templateAsString, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
                    }
                }
            }
        }
    }

    private List<Account> populateUserAccountsOnFirstLogin() {
        User u = user
        List<Account> accounts

        if (u && (!u.getLoginCount() || u.getLoginCount() == 1) && !userService.getAccount(u) && !userService.getRequestToJoinAccount(u.id)) {
            accounts = accountService.getAccountsByEmailSuffix(u.username)
        }
        return accounts
    }

    def saveInternal() {
        // Do nothing, just needed when extending AbstractDomainObjectController
    }
}
