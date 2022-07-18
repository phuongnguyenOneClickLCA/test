package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseEntityChangeHistory
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.mongodb.BasicDBObject
import grails.util.Environment
import org.apache.commons.lang.time.DateUtils
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class KpiReportService {

    def optimiResourceService
    def licenseService
    def optimiMailService
    def configurationService
    def loggerUtil
    def flashService
    def entityService

    @Autowired
    EmailConfiguration emailConfiguration

    void sendWeeklyKPIReportJob(boolean manual = false) {
        // Database objects need to be fetched with gorm or with mongodb driver without collecting objects as their domain class
        // The quartz job fails for unknown reason if e.g. Entity.collection.find().collect({it as Entity}) is called
        // But getting data as documents: Entity.collection.find().toList() works?
        try {
            log.info("Start sending KPI report")
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy")
            log.info("Getting user report")
            String userReport = getUserReport()
            log.info("Getting project report")
            String projectReport = getProjectReport()
            log.info("Getting db report")
            String databaseReport = getDatabaseReport()
            log.info("Getting license report")
            String licenseReport = getLicenseReport()
            log.info("Getting license with no use report")
            //String licenseWithNoUseReport = getLicensesWithNoUseReport()
            log.info("Got all reports. Sending email...")

            String htmlBody = userReport + projectReport + databaseReport + licenseReport
            if(manual){
                optimiMailService.sendMail({
                    to emailConfiguration.kpiReportEmail
                    from emailConfiguration.contactEmail
                    subject optimiMailService.addHostnameToSubject("${Environment.current.name} MANUAL KPI : One Click LCA weekly KPI report - ${dateFormat.format(new Date())}")
                    html htmlBody
                }, emailConfiguration.kpiReportEmail)
                log.info("Weekly KPI HTML: ${htmlBody}")
            } else if (Environment.current == Environment.PRODUCTION) {
                optimiMailService.sendMail({
                    to emailConfiguration.kpiReportEmail
                    from emailConfiguration.contactEmail
                    subject optimiMailService.addHostnameToSubject("PROD: One Click LCA weekly KPI report - ${dateFormat.format(new Date())}")
                    html htmlBody
                }, emailConfiguration.kpiReportEmail)
            } else {
                log.info("Weekly KPI HTML: ${htmlBody}")
            }
            log.info("End sending KPI report")
        } catch (Exception e){
            loggerUtil.error(log, "Cannot send KPI report. Exception", e)
            if (manual) {
                flashService.setErrorAlert("Cannot send KPI report. Exception $e.message", true)
            }
        }
    }
    void sendWeeklyKPIReportForLicenseDetails(boolean manual = false){
        try {
            log.info("Start sending KPI report")
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy")
            log.info("Getting license report")
            String licenseNoLogIn60Days = licenseNotUsed60DaysReport()
            log.info("Getting licenses not used in 30 or 90 days report")
            String licenseNotUsedReport = getLicensesNotUsedIn30to90DaysReport()
            log.info("Getting commercial licenses report")
            String commercialLicensesReport = getCommercialLicensesReport()
            //15004 comment out for now
            /*log.info("Getting commercial users not logged in 60 or 90 days report")
            String commercialUserNotLogIn6090 = commercialLicenseUsernotLoggedin6090days()*/
            log.info("Getting commercial licenses to be renewed in 90 days report")
            String commercialLicenseToBeRenewed = getCommercialLicenseNeedRenewal90Days()
            log.info("Got all reports. Sending email...")
            String htmlBody = licenseNotUsedReport+licenseNoLogIn60Days + commercialLicensesReport + commercialLicenseToBeRenewed
            if(manual){
                optimiMailService.sendMail({
                    to emailConfiguration.kpiReportEmail
                    from emailConfiguration.contactEmail
                    subject optimiMailService.addHostnameToSubject("${Environment.current.name} MANUAL KPI : One Click LCA weekly KPI report - ${dateFormat.format(new Date())}")
                    html htmlBody
                }, emailConfiguration.kpiReportEmail)
                log.info("Weekly KPI churn HTML: ${htmlBody}")
            } else if (Environment.current == Environment.PRODUCTION) {
                optimiMailService.sendMail({
                    to emailConfiguration.kpiReportEmail
                    from emailConfiguration.contactEmail
                    subject optimiMailService.addHostnameToSubject("PROD: One Click LCA weekly anti churn report - ${dateFormat.format(new Date())}")
                    html htmlBody
                }, emailConfiguration.kpiReportEmail)
            } else {
                log.info("Weekly KPI churn HTML: ${htmlBody}")
            }
            log.info("End sending KPI churn report")
        } catch(Exception e) {
            loggerUtil.error(log, "Cannot send KPI churn report. Exception", e)
            if (manual) {
                flashService.setErrorAlert("Cannot send KPI churn report. Exception $e.message", true)
            }
        }
    }
    private String getUserReport() {
        List<Document> activatedUsers = User.collection.find([emailValidated: true],[logins: 1, lastLogin: 1, registrationTime: 1, type: 1, wasInvited: 1, licenseIds: 1, usableLicenseIds: 1,trialStatus:1])?.toList()
        Date hunderEightyDaysAgo = DateUtils.addDays(new Date(), -180)
        Date sixtyDaysAgo = DateUtils.addDays(new Date(), -60)
        Date thirtyDaysAgo = DateUtils.addDays(new Date(), -30)
        Date sevenDaysAgo = DateUtils.addDays(new Date(), -7)

        List<Document> usersActiveInLast180Days = []
        List<Document> planateryUsersActiveInLast180Days = []
        List<Document> usersWithLastLogginIn60Days = []
        List<Document> planateryUsersActiveInLast60Days = []
        List<Document> usersWithLastLogginIn30Days = []
        List<Document> planateryUsersActiveInLast30Days = []
        List<Document> usersWithLastLoggingIn7Days = []
        List<Document> planateryUsersActiveInLast7Days = []
        Integer registeredUsers = 0
        Integer planetaryUsers = 0
        Integer registeredStudents = 0
        Integer registeredClients = 0
        Integer registeredLeads = 0
        Integer commercialLicenseUsers = 0

        activatedUsers.each { Document d ->
            Date userLastLogin = d.logins ? d.logins.max({(Date)it.time})?.time : d.lastLogin
            Date registrationTime = d.registrationTime

            if (userLastLogin != null) {
                Boolean isPlanetaryUser = Boolean.FALSE
                List licenseIds = []
                if (d.licenseIds) {
                    licenseIds.addAll(d.licenseIds)
                }
                if (d.usableLicenseIds) {
                    licenseIds.addAll(d.usableLicenseIds)
                }

                if (licenseIds && License.countByIdInListAndType(DomainObjectUtil.stringsToObjectIds(licenseIds), Constants.LicenseType.PRODUCTION.toString())) {
                    commercialLicenseUsers++
                }
                if(d?.trialStatus?.get("isPlanetaryUser")){
                    planetaryUsers ++
                    isPlanetaryUser = Boolean.TRUE
                }

                if (userLastLogin.after(hunderEightyDaysAgo)) {
                    usersActiveInLast180Days.add(d)
                    if(isPlanetaryUser){
                        planateryUsersActiveInLast180Days.add(d)
                    }
                }

                if (userLastLogin.after(sixtyDaysAgo)) {
                    usersWithLastLogginIn60Days.add(d)
                    if(isPlanetaryUser){
                        planateryUsersActiveInLast60Days.add(d)
                    }
                }


                if (userLastLogin.after(thirtyDaysAgo)) {
                    usersWithLastLogginIn30Days.add(d)
                    if(isPlanetaryUser){
                        planateryUsersActiveInLast30Days.add(d)
                    }
                }

                if (userLastLogin.after(sevenDaysAgo)) {
                    usersWithLastLoggingIn7Days.add(d)
                    if(isPlanetaryUser){
                        planateryUsersActiveInLast7Days.add(d)
                    }
                }
                if (registrationTime && registrationTime.after(sevenDaysAgo)) {
                    registeredUsers++

                    if (d.type == Constants.UserType.STUDENT.toString()) {
                        registeredStudents++
                    }

                    if (d.wasInvited) {
                        registeredClients++
                    } else {
                        registeredLeads++
                    }
                }


            }
        }

        List<Entity> trialEntities = Entity.findAllByDateCreatedGreaterThanEqualsAndParentEntityIdIsNull(sevenDaysAgo)
        Integer newTrialsStarted = 0
        if (trialEntities) {
            trialEntities.each { Entity e ->
                License validTrialLicense = licenseService.getValidLicensesForEntity(e)?.find({ Constants.LicenseType.TRIAL.toString().equals(it.type) })

                if (validTrialLicense && !Constants.UserType.STUDENT.toString().equals(validTrialLicense.getChangeHistory(e)?.addedByUser?.type)) {
                    newTrialsStarted++
                }
            }
        }
        String report = "<p><h1>1. Users and trials</h1>"
        report = report + "Total active user accounts: ${activatedUsers.size()}<br />"
        report = report + "Commercial users: ${commercialLicenseUsers}<br />"
        report = report + "Planetary licence users: ${planetaryUsers}<br />"
        report = report + "Users active in last 180 days: ${usersActiveInLast180Days ? usersActiveInLast180Days.size() : 0}<br />"
        report = report + "Users active in last 60 days: ${usersWithLastLogginIn60Days ? usersWithLastLogginIn60Days.size() : 0}<br />"
        report = report + "Users active in last 30 days: ${usersWithLastLogginIn30Days ? usersWithLastLogginIn30Days.size() : 0}<br />"
        report = report + "Users active in last 7 days: ${usersWithLastLoggingIn7Days ? usersWithLastLoggingIn7Days.size() : 0}<br />"
        report = report + "Planetary users active in last 180 days: ${planateryUsersActiveInLast180Days ? planateryUsersActiveInLast180Days.size() : 0}<br />"
        report = report + "Planetary users active in last 60 days: ${planateryUsersActiveInLast60Days ? planateryUsersActiveInLast60Days.size() : 0}<br />"
        report = report + "Planetary users active in last 30 days: ${planateryUsersActiveInLast30Days ? planateryUsersActiveInLast30Days.size() : 0}<br />"
        report = report + "Planetary users active in last 7 days: ${planateryUsersActiveInLast7Days ? planateryUsersActiveInLast7Days.size() : 0}<br />"
        report = report + "New users: ${registeredUsers}<br />"
        report = report + "Clients: ${registeredClients}<br />"
        report = report + "Leads: ${registeredLeads}<br />"
        report = report + "Students: ${registeredStudents}<br />"
        report = report + "New trials started: ${newTrialsStarted}<br />"
        report = report + "</p>"

        return report
    }

    private String getProjectReport() {
        String report = "<p><h1>2. Projects</h1>"
        List<License> commercialLicenses = licenseService.getValidCommercialLicenses()?.findAll({it.licensedEntityIds})
        List<License> planetaryLicenses = License.findAllByPlanetary(true)
        List<String> entityIds = commercialLicenses?.collect({it.licensedEntityIds as List<String>})?.flatten()?.unique()
        List<String> planetaryEntityIds = planetaryLicenses?.collect({it.licensedEntityIds as List<String>})?.flatten()?.unique()
        Integer commercialProjects = 0
        Integer planetaryProjects = 0
        Integer newPlanetaryProjects = 0
        Double totalAreaOfCommercialProjects = 0D
        Double totalAreaOfPlanetaryProjects = 0D
        Long carbonHeroesDesignAmount = 0
        DecimalFormat df = new DecimalFormat("#,###,###")
        List planetaryCountriesList = []

        df.setMaximumFractionDigits(0)
        String basicQueryId = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.BASIC_QUERY_ID.toString())
        List<String> countriesWithCommercial = []
        if (entityIds) {
            List<Document> entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(entityIds)], deleted: [$nin: [true]]], [datasets: 1, childEntities: 1])?.toList()
            entities?.each { Document entity ->
                commercialProjects++
                List datasets = entity.datasets?.toList()
                def surfaceDataset = datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "grossSurface" })
                String country = datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "country" })?.resourceId
                if(country){
                    countriesWithCommercial.add(country)
                }
                Double surface

                if (surfaceDataset && surfaceDataset.answerIds) {
                    if (surfaceDataset.answerIds[0].isNumber()) {
                        surface = surfaceDataset.answerIds[0].toDouble()
                    } else if (surfaceDataset.answerIds[0].toString().replaceAll("\\s","").replaceAll(",", ".").isNumber()) {
                        surface = surfaceDataset.answerIds[0]?.toString()?.replaceAll("\\s","")?.replaceAll(",", ".")?.toDouble()
                    }
                }

                if (surface) {
                    totalAreaOfCommercialProjects = totalAreaOfCommercialProjects + surface
                }
                def childEntityIds = entity.childEntities?.collect({ it.entityId })

                if (childEntityIds) {
                    long entityCarbonHeroesDesigns = Entity.collection.count([_id: [$in: childEntityIds], carbonHero: true, deleted: [$nin: [true]]])

                    if (entityCarbonHeroesDesigns) {
                        carbonHeroesDesignAmount = carbonHeroesDesignAmount + entityCarbonHeroesDesigns
                    }
                }
            }
        }
        if(planetaryEntityIds){
            Date weekDate = DateUtils.addDays(new Date(), -7)
            List<Document> planetaryEntities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(planetaryEntityIds)],deleted: [$nin: [true]]], [datasets: 1,dateCreated:1])?.toList()
            List<Document> newPlanetaries = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(planetaryEntityIds)], dateCreated: [$gte:weekDate],deleted: [$nin: [true]]], [datasets: 1,dateCreated:1])?.toList()
            newPlanetaryProjects = newPlanetaries?.size() ?: 0
            planetaryEntities?.each {Document entity ->
                planetaryProjects++
                List datasetsP = entity.datasets?.toList()
                def surfaceDatasetPlanetary = datasetsP?.find({ d -> d.queryId == basicQueryId && d.questionId == "grossSurface" })
                String countryPlanetary = datasetsP?.find({ d -> d.queryId == basicQueryId && d.questionId == "country" })?.resourceId
                Double surface
                if(countryPlanetary){
                    planetaryCountriesList.add(countryPlanetary)
                }

                if (surfaceDatasetPlanetary && surfaceDatasetPlanetary.answerIds) {
                    if (surfaceDatasetPlanetary.answerIds[0].isNumber()) {
                        surface = surfaceDatasetPlanetary.answerIds[0].toDouble()
                    } else if (surfaceDatasetPlanetary.answerIds[0].toString().replaceAll("\\s","").replaceAll(",", ".").isNumber()) {
                        surface = surfaceDatasetPlanetary.answerIds[0]?.toString()?.replaceAll("\\s","")?.replaceAll(",", ".")?.toDouble()
                    }
                }

                if (surface) {
                    totalAreaOfPlanetaryProjects = totalAreaOfPlanetaryProjects + surface
                }
            }
        }
        List<License> validTrialLicenses = licenseService.getValidTrials()
        Integer activeTrialProjects = 0

        if (validTrialLicenses) {
            Date now = new Date()
            validTrialLicenses.each { License license ->
                if (license.trialLength) {
                    license.licensedEntityIds?.each { entityId ->
                        LicenseEntityChangeHistory changeHistory = license.getChangeHistoryByEntityId(entityId?.toString())

                        if (changeHistory && changeHistory.dateAdded) {
                            if (addDays(changeHistory.dateAdded, license.trialLength).after(now)) {
                                activeTrialProjects++
                            }
                        }
                    }
                }
            }
        }
        Set<String> planetaryCountriesSet = planetaryCountriesList.toSet()
        report = report + "Total active trial projects: ${activeTrialProjects}<br />"
        report = report + "Project in commercial licenses: ${commercialProjects}<br />"
        report = report + "Project in planetary licenses: ${planetaryProjects}<br />"
        report = report + "New planetary projects: ${newPlanetaryProjects}<br />"
        report = report + "Number of countries with commercial license projects: ${countriesWithCommercial?.toSet()?.size() ?: 0}<br />"
        report = report + "Number of countries with Planetary license projects: ${planetaryCountriesSet?.size() ?: 0}<br />"
        report = report + "Countries that have Planetary license projects: ${planetaryCountriesSet.collectEntries({country -> [(country):planetaryCountriesList.count({ x -> x == country })]})}<br />"
        report = report + "Total area in commercial licenses: ${totalAreaOfCommercialProjects ? df.format(totalAreaOfCommercialProjects) : 0}<br />"
        report = report + "Total area in planetary licenses: ${totalAreaOfPlanetaryProjects ? df.format(totalAreaOfPlanetaryProjects) : 0}<br />"
        report = report + "Carbon Heroes designs: ${carbonHeroesDesignAmount ? carbonHeroesDesignAmount : 0}</p>"
        return report
    }

    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance()
        cal.setTime(date)
        cal.add(Calendar.DATE, days) //minus number would decrement the days
        return cal.getTime()
    }

    private String getDatabaseReport() {
        String report = "<p><h1>3. Database</h1>"
        Date dateLimit = addDays(new Date(), -7)
        Map<String, Integer> databaseKPIs = optimiResourceService.getDatabaseKPIs()
        Map<String, Integer> databaseKPIsThisWeek = optimiResourceService.getDatabaseKPIs(dateLimit)
        def epdKPI = optimiResourceService.getAndSortEpdResources(dateLimit)

        report = report + "<h2>Database overview </h2>"

        databaseKPIs?.each { String key, Integer value ->
            if (key == "totalPartial") {
                report = report + "<b>${getResolveKpiDisplayName(key)}: ${value}</b><br />"
            } else {
                report = report + "${getResolveKpiDisplayName(key)}: ${value}<br />"
            }
        }
        report = report + "<h2>Database growth (Monday-Sunday) </h2>"
        databaseKPIsThisWeek?.each { String key, Integer value ->
            if (key == "totalPartial") {
                report = report + "<b>${getResolveKpiDisplayName(key)}: ${value}</b><br />"
            } else {
                report = report + "${getResolveKpiDisplayName(key)}: ${value}<br />"
            }
        }
        report = report + "<h2>Database growth by EPD Program Operator (Monday-Sunday) </h2>"
        if(epdKPI.size() > 0 && epdKPI.find {it.value > 0}){
            epdKPI?.each { String key, Integer value ->
                if(value){
                    report = report + "${key}: ${value}<br />"
                }
            }
        } else {
            report = report + "<b>There is no new EPD this week</b>"
        }

        //COMMENT OUT TILL NEXT RELEASE 14130 & 14123
     /*   String mappingReportedAsBad = importMapperTrainingDataService.getAllReportedTrainingDataCount()
        report = report + "<br />Mappings reported as bad by user: ${mappingReportedAsBad}"*/

        report = report + "</p>"
        return report
    }

    private String getLicenseReport() {
        Date date = new Date()
        String report = "<p><h1>4. Licenses</h1>"
        Integer commercialLicenses = License.collection.count([type: Constants.LicenseType.PRODUCTION.toString(), validUntil: [$gte: date]])?.intValue()
        report = report + "Commercial licenses: ${commercialLicenses ? commercialLicenses : 0}<br />"
        Integer educationalLicenses = License.collection.count([type: Constants.LicenseType.EDUCATION.toString(), validUntil: [$gte: date]])?.intValue()
        report = report + "Educational licenses: ${educationalLicenses ? educationalLicenses : 0}<br />"
        Integer validLicenseWithNoUse = License.collection.count([validUntil: [$gte: new Date()], $or: [[licensedEntityIds: [$exists: false]], [licensedEntityIds: null], [licensedEntityIds: []]]])?.intValue()
        report = report + "Licenses with no use: ${validLicenseWithNoUse ? validLicenseWithNoUse : 0}</p>"
        return report
    }

    private String getLicensesNotUsedIn30to90DaysReport() {
        String report = "<p><h1>1. Licenses that are not linked to projects</h1><h2>Auto renewal licenses not used in 30 days</h2>"
        List<String> licensesNotUsedIn30Days = licenseService.getLicensesNotUsedBetweenInDays(0, -30)

        if (licensesNotUsedIn30Days) {
            licensesNotUsedIn30Days.each {
                report = report + it + "<br />"
            }
        } else {
            report = report + "No licenses found"
        }
        report = report + "<h2>Auto renewal licenses not used in 30-60 days</h2>"
        List<String> licensesNotUsedIn30to60Days = licenseService.getLicensesNotUsedBetweenInDays(-30, -60)

        if (licensesNotUsedIn30to60Days) {
            licensesNotUsedIn30to60Days.each {
                report = report + it + "<br />"
            }
        } else {
            report = report + "No licenses found"
        }

        report = report + "<h2>Auto renewal licenses not used in 60-90 days</h2>"
        List<String> licensesNotUsedIn60to90Days = licenseService.getLicensesNotUsedBetweenInDays(-60, -90)

        if (licensesNotUsedIn60to90Days) {
            licensesNotUsedIn60to90Days.each {
                report = report + it + "<br />"
            }
        } else {
            report = report + "No licenses found"
        }
        report = report + "<h2>Auto renewal licenses not used in more than 90 days</h2>"
        List<String> licensesNotUsedIn90Days = licenseService.getLicensesNotUsedInDays(-91)

        if (licensesNotUsedIn90Days) {
            licensesNotUsedIn90Days.each {
                report = report + it + "<br />"
            }
        } else {
            report = report + "No licenses found"
        }
        report = report + "<h2>Fixed term licenses not used in any project</h2>"
        List<String> fixedLicenseNotUsed = licenseService.getFixedLicenseWithNoUseReport()

        if (fixedLicenseNotUsed) {
            fixedLicenseNotUsed.each {
                report = report + it + "<br />"
            }
        } else {
            report = report + "No licenses found"
        }
        report = report + "</p>"
        return report
    }
    public String getAddonLicenseUnusedProjectReport(){
        List<Document> allAddOnLicenses = licenseService.getAddOnLicenses()
        String report = "<p><h2>Add-on licenses with unused entities (unused entities / entities allowed)</h2><br/>"
        if(allAddOnLicenses){
            allAddOnLicenses?.each {Document license ->
                Integer entitiesUsed = license.licensedEntityIds ? license.licensedEntityIds.size() : 0
                if(license.maxEntities && license.maxEntities > 0 && license.maxEntities > entitiesUsed){
                    report = report + license.name + ": " + (license.maxEntities - entitiesUsed) +" / "+ license.maxEntities + " (Renewal date : "+license.validUntil+")<br/>"
                }
            }
        }
        report = report + "</p>"
        return report

    }

    public String getResolveKpiDisplayName(String kpi) {
        String resolvedName = ""

        if ("LCA-BMAT".equals(kpi)) {
            resolvedName = "Material datapoints (LCA, BMAT)"
        } else if ("LCA-BTECH".equals(kpi)) {
            resolvedName = "Technology datapoints (LCA, buildingTechnology)"
        } else if ("BMAT+BTECH".equals(kpi)) {
            resolvedName = "Total LCA (BMAT, buildingTechnology)"
        } else if ("LCA-utility".equals(kpi)) {
            resolvedName = "Utility datapoints"
        } else if ("LCC".equals(kpi)) {
            resolvedName = "LCC data"
        } else if ("costStructures".equals(kpi)) {
            resolvedName = "Cost structures"
        } else if ("bionovaConstructions".equals(kpi)) {
            resolvedName = "Bionova Ltd constructions"
        } else if ("otherConstructions".equals(kpi)) {
            resolvedName = "Other private constructions"
        } else if ("generic".equals(kpi)) {
            resolvedName = "Generic datapoints"
        } else if ("total".equals(kpi)) {
            resolvedName = "Total database"
        } else if ("LCA-OTHER".equals(kpi)) {
            resolvedName = "Other datapoints (LCA)"
        } else if ("private".equals(kpi)) {
            resolvedName = "Private datapoints"
        } else if ("inactive".equals(kpi)) {
            resolvedName = "Inactive datapoints"
        } else if ("LCA-Ecoinvent".equals(kpi)) {
            resolvedName = "Ecoinvent datapoints"
        } else if ("totalPartial".equals(kpi)) {
            resolvedName = "BMAT, BTECH, LCC, other LCA, constructions, utilities"
        } else if ("staticDBs".equals(kpi)) {
            resolvedName = "Data from IMPACT, KBOB, OKOBAUDAT, Quartz, ZAG, Earthsure and FPInnovations"
        }
        return resolvedName
    }
    private String getCommercialLicensesReport(){
        String report = "<p><h1>3.Commercial licenses statistic</h1><br/>"
        report = report + getReportCommercialTrainingUnused()
        report = report + getAddonLicenseUnusedProjectReport()
        return report
    }
    private String getReportCommercialTrainingUnused (){
        String report = "<h2>Business and Expert license with training unused after 60 days</h2><br/>"
        List<Document> commercialLicenseWithTraininngUnsued =  licenseService.getCommercialLicenseWithTraininngUnused(-60)
        if(commercialLicenseWithTraininngUnsued){
            commercialLicenseWithTraininngUnsued?.each {Document license ->
                List<Document> usersForLicense = User.collection.find([_id: [$in: license.licenseUserIds.collect({it -> DomainObjectUtil.stringToObjectId(it)}) as List], emailValidated: true],[username: 1])?.toList()
                report = report + license.name + " : " + usersForLicense.collect({it.username}) + "<br/>"
            }
        } else {
            report = report + "Licenses not found with this criteria"
        }
        report = report + "</p>"
    }
    private String licenseNotUsed60DaysReport(){
        List<Document> activatedUsers = User.collection.find([emailValidated: true],[logins: 1, lastLogin: 1, registrationTime: 1, type: 1, wasInvited: 1, licenseIds: 1, usableLicenseIds: 1])?.toList()
        Date sixtyDaysAgo = DateUtils.addDays(new Date(), -60)


        List<Document> usersWithLastLogginIn60Days = []

        List licenseIdsNoLogin60Days = []
        List licenseIdsLogin60Days = []
        List<String> floatingLicenseIdsNoLogin60Days = []
        List<String> floatingLicenseIdsLogin60Days = []
        if(activatedUsers){
            activatedUsers = activatedUsers.sort({d -> d.logins ? d.logins.max({(Date)it.time})?.time : d.lastLogin})
            activatedUsers.each { Document d ->
                Date userLastLogin = d.logins ? d.logins.max({(Date)it.time})?.time : d.lastLogin
                Date registrationTime = d.registrationTime
                if (userLastLogin != null) {
                    if (userLastLogin.before(sixtyDaysAgo)) {
                        usersWithLastLogginIn60Days.add(d)
                        if (d.usableLicenseIds) {
                            licenseIdsNoLogin60Days.addAll(d.usableLicenseIds)
                        }
                    } else {
                        if (d.usableLicenseIds) {
                            licenseIdsLogin60Days.addAll(d.usableLicenseIds)
                        }
                    }

                }
            }
        }

        licenseIdsNoLogin60Days.removeIf({licenseIdsLogin60Days.contains(it)})
        licenseIdsNoLogin60Days = licenseIdsNoLogin60Days.collect({it.toString()}).unique()

        List<Document> nonFloatingLicenseNamesNoLogin60Days = licenseService.getLicenseNamesByLicenseIds(licenseIdsNoLogin60Days, Boolean.FALSE, Boolean.TRUE)
        List<Document> floatingLicenseNamesNoLogin60Days = licenseService.getLicenseNamesByLicenseIds(licenseIdsNoLogin60Days, Boolean.TRUE, Boolean.TRUE)

        String report ="<p><h1>2. License details of users not logged in within last 60 days:</h1>"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy")

        report = report + "<h2>Floating licenses name :</h2>"
        if(floatingLicenseNamesNoLogin60Days && !floatingLicenseNamesNoLogin60Days.isEmpty()){
            floatingLicenseNamesNoLogin60Days.each {license ->
                Date lastLoginForLicense = activatedUsers?.find({u -> u.usableLicenseIds?.contains(license.id.toString())})?.lastLogin
                report = "${report} ${license.name} (Renewal date: ${dateFormat.format((Date) license.validUntil)}) Last login: ${lastLoginForLicense ? dateFormat.format(lastLoginForLicense) : ''}<br/>"
            }
        } else {
            report = report + " No licenses found for this criteria <br />"
        }

        report = report + "<h2>Non floating licenses name:</h2>"
        if(nonFloatingLicenseNamesNoLogin60Days && !nonFloatingLicenseNamesNoLogin60Days.isEmpty()){
            nonFloatingLicenseNamesNoLogin60Days.each {license ->
                Date lastLoginForLicense = activatedUsers?.find({u -> u.usableLicenseIds?.contains(license.id.toString())})?.lastLogin
                report = "${report} ${license.name} (Renewal date: ${dateFormat.format((Date) license.validUntil)}). Last login: ${lastLoginForLicense ? dateFormat.format(lastLoginForLicense) : ''}<br/>"
            }
        } else {
            report = report + " No licenses found for this criteria <br/>"
        }


        report = report + "</p>"

        return report
    }
    private String commercialLicenseUsernotLoggedin6090days(){
        List<Document> activatedUsers = User.collection.find([emailValidated: true],[logins: 1, lastLogin: 1, registrationTime: 1, type: 1, wasInvited: 1, licenseIds: 1, usableLicenseIds: 1, username:1])?.toList()
        Date sixtyDaysAgo = DateUtils.addDays(new Date(), -60)
        Date ninetyDaysAgo = DateUtils.addDays(new Date(), -90)

        List<Document> usersActiveInLast90Days = []
        List<Document> usersWithLastLogginIn60Days = []

        activatedUsers.each { Document d ->
            Date userLastLogin = d.logins ? d.logins.max({(Date)it.time})?.time : d.lastLogin
            Date registrationTime = d.registrationTime

            if (userLastLogin != null) {
                List licenseIds = []
                if (d.licenseIds) {
                    licenseIds.addAll(d.licenseIds)
                }
                if (d.usableLicenseIds) {
                    licenseIds.addAll(d.usableLicenseIds)
                }
                if (licenseIds) {
                    def validLicense = licenseService.findValidLicenseFromUserLicenseIds(licenseIds as List<String>,d.id.toString())
                    if(validLicense){
                        if (userLastLogin.before(ninetyDaysAgo)) {
                            usersActiveInLast90Days.add(d)
                        } else if (userLastLogin.before(sixtyDaysAgo)) {
                            usersWithLastLogginIn60Days.add(d)
                        }
                    }

                }

            }
        }

        String report = "<p><h1>4. Commercial license users that have not logged in within last 60 & 90 days - any user that is part of a PROD license</h1>"
        report = report + "<h2>Users that have not logged in within last 90</h2><br />"

        usersActiveInLast90Days.each {Document user ->
            if(user?.username && !emailConfiguration.isProtectedEmail((String) user.username)){
                report = "${report}${user.username}<br/>"
            }
        }
        report = report + "<h2> Users that have not logged in within last 60</h2><br />"
        usersWithLastLogginIn60Days.each {Document user ->
            if(user?.username && !emailConfiguration.isProtectedEmail((String) user.username)){
                report = "${report}${user.username}<br/>"
            }
        }
        report = report + "</p>"

        return report
    }

    private String getCommercialLicenseNeedRenewal90Days() {
        Date renewalDate = DateUtils.addDays(new Date(), 90)
        List<License> licenses = licenseService.getValidCommercialLicenses(renewalDate)
        String report = "<p><h1>5.Commercial licenses to be renewed in 90 days</h1><br/>"
        licenses.each{License d ->
            if(!d.renewalStatus?.equals("fixedPeriod")){
                report = "${report}${d.name} - Valid until: ${d.validUntil} <br/> "
            }
        }
        report = "${report}</p>"
    }
}
