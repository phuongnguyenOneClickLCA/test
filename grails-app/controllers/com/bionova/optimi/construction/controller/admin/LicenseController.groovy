/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.configuration.EmailConfiguration
import com.bionova.optimi.construction.controller.ExceptionHandlerController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.FloatingLicenseUser
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseEntityChangeHistory
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.LicenseTier
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.domain.mongo.WorkFlow
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.util.DateUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired

import java.text.SimpleDateFormat
import java.time.Duration

/**
 * @author Pasi-Markus Mäkelä
 */
class LicenseController extends ExceptionHandlerController {

    static defaultAction = "list"
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def licenseService
    def entityService
    def indicatorService
    def featureService
    def userService
    def optimiResourceService
    def accountService
    def channelFeatureService
    def localeResolverUtil
    def maskingService
    def datasetService
    def projectTemplateService
    def licenseTemplateService

    @Autowired
    EmailConfiguration emailConfiguration

    def autorenew() {
        licenseService.autoRenewLicenses()
        render text: "foo"
    }

    def getLicensedEntities() {
        String licenseId = params.id
        String data = ""
        User user = userService.getCurrentUser()

        if (licenseId) {
            License license = licenseService.getById(licenseId)

            if (license?.licensedEntityIds) {
                license.licensedEntityIds.each { String licensedEntityId ->
                    Entity entity = Entity.findById(DomainObjectUtil.stringToObjectId(licensedEntityId))

                    if (entity) {
                        data = "${data}${renderEntityDataForLicense(entity.id.toString(), entity.name, license, user, Boolean.TRUE, entity?.deleted)}"
                    } else {
                        data = "${data}${renderEntityDataForLicense(licensedEntityId, null, license, user, Boolean.TRUE)}"
                    }
                }
            }
        }
        render text: data
    }

    def getChoosableEntities() {
        String licenseId = params.id
        List<Map<String, String>> entitiesForSelect2 = []

        if (licenseId) {
            License license = licenseService.getById(licenseId)
            List<String> licensedEntityIds = license.licensedEntityIds

            def entityClasses = optimiResourceService.getParentEntityClassIds()
            def compatibleEntityClasses

            if (license.compatibleEntityClasses) {
                compatibleEntityClasses = entityClasses?.findAll({ license.compatibleEntityClasses.contains(it) })
            } else {
                compatibleEntityClasses = entityClasses
            }
            def entities = entityService.getChoosableEntities(compatibleEntityClasses, licensedEntityIds, Boolean.FALSE)
            int i = 0
            entities?.each { Document entity ->
                if (i == 0) {
                    Map<String, String> d = [:]
                    d["id"] = ""
                    d["text"] = ""
                    entitiesForSelect2.add(d)
                }
                Map<String, String> d = [:]
                d["id"] = entity._id.toString()
                d["text"] = "${entity.name}".toString()
                entitiesForSelect2.add(d)
                i++
            }
        }
        render([entities: entitiesForSelect2, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def getRemovedEntities() {
        String licenseId = params.id
        String data = ""
        User user = userService.getCurrentUser()

        if (licenseId) {
            License license = licenseService.getById(licenseId)

            if (license?.removedEntityIds) {
                List<Entity> entities = license.getRemovedEntities()?.findAll { it != null }
                List<String> removedEntityIds = entities?.collect({ it.id?.toString() })
                List<String> permanentlyDeleteEntities = removedEntityIds ? license.removedEntityIds - removedEntityIds : license.removedEntityIds

                entities?.each { Entity entity ->
                    data = "${data}${renderEntityDataForLicense(entity?.id?.toString(), entity?.name, license, user)}"
                }
                permanentlyDeleteEntities?.each { String idString ->
                    data = "${data}${renderEntityDataForLicense(idString, null, license, user)}"
                }
            }
        }
        render text: data
    }

    private String renderEntityDataForLicense(String entityId, String entityName, License license, User user, Boolean allowRemoveFromLicense = Boolean.FALSE, Boolean entityDeleted = Boolean.FALSE) {
        String data = ""
        if (license && user && entityId) {
            LicenseEntityChangeHistory changeHistory = license.getChangeHistoryByEntityId(entityId)
            Map anniversaryDate = changeHistory?.anniversaryDate
            String anniversary = ""
            String entityNameUI = message(code: 'permanently_deleted_entity')
            String deleteQuestion = ""
            String trueStr = ""
            String falseStr = ""
            String titleStr = ""
            Date expiryDate

            if (anniversaryDate && anniversaryDate.size() > 0) {
                def years = anniversaryDate.keySet().toList()[0]
                def days = anniversaryDate.get(years)
                anniversary = message(code: "admin.license.changeHistory.anniversary", args: [years, days])
            }
            if (entityName) {
                //https://oneclicklca.atlassian.net/browse/REL-344
                entityNameUI = "${user.internalUseRoles ? g.link(controller: 'entity', action: 'show', params: [entityId: entityId, name: entityName]) { entityName } : entityName}"
            }
            if (allowRemoveFromLicense) {
                deleteQuestion = message(code: 'admin.license.delete_building.question', args: [entityNameUI])
                trueStr = message(code: 'delete')
                falseStr = message(code: 'cancel')
                titleStr = message(code: 'admin.license.delete_building.header')
                if (license.trialLength && changeHistory) {
                    expiryDate = changeHistory.dateAdded.plus(license.trialLength)
                }
                data = "<tr><td>${entityNameUI}${entityDeleted ? ' (deleted entity)' : ''}</td><td>${entityId}</td><td><span class=\"hidden\">${formatDate(date: changeHistory?.dateAdded, format: 'yyyyMMdd')}</span>${anniversary ? anniversary : ''}</td><td><span class=\"hidden\">${formatDate(date: changeHistory?.dateAdded, format: 'yyyyMMdd')}</span>${formatDate(date: changeHistory?.dateAdded, format: 'dd.MM.yyyy HH:mm')}</td><td>${changeHistory?.addedBy ? changeHistory.addedBy : ''}</td>" +
                        "${userService.getSalesView(user) && Constants.LicenseType.TRIAL.toString().equals(license.type) && expiryDate ? "<td>${g.textField(value: "${formatDate(date: expiryDate, format: 'dd.MM.yyyy')}", name: "expiryDate", id: "expiryDate${entityId}", class: "input-xlarge datepicker expiryDatePicker", readonly: "true", style: "cursor: pointer; width: 100px;")}<span class=\"add-on\"><i class=\"icon-calendar\"></i></span></label> <span id=\"saveContainer${entityId}\"><a href=\"javascript:\" onclick=\"changeTrialExpiryDate('${entityId}','${license.id.toString()}')\">Save</a></span></td>" : ""}" +
                        "${userService.getSuperUser(user) ? "<td>${link(action: 'removeEntity', id: license.id, params: [entityId: entityId, permanentEntityDeletion: false], class: 'btn btn-danger', onclick: 'return modalConfirm(this);', 'data-questionstr': deleteQuestion, 'data-truestr': trueStr, 'data-falsestr': falseStr, 'data-titlestr': titleStr) { message(code: 'delete') }}</td>" : ""}" +
                        "</tr>"
            } else {
                data = "<tr><td>${entityNameUI}</td><td>${entityId}</td><td><span class=\"hidden\">${formatDate(date: changeHistory?.dateAdded, format: 'yyyyMMdd')}</span>${anniversary}</td><td><span class=\"hidden\">${formatDate(date: changeHistory?.dateAdded, format: 'yyyyMMdd')}</span>${formatDate(date: changeHistory?.dateAdded, format: 'dd.MM.yyyy HH:mm')}</td><td>${changeHistory?.addedBy ? changeHistory.addedBy : ''}</td>" +
                        "<td><span class=\"hidden\">${formatDate(date: changeHistory?.dateRemoved, format: 'yyyyMMdd')}</span>${formatDate(date: changeHistory?.dateRemoved, format: 'dd.MM.yyyy HH:mm')}</td><td>${changeHistory?.removedBy ? changeHistory.removedBy : ''}</td>" +
                        "</tr>"
            }
        }

        return data
    }

    def licenseTemplate() {
        String id = params.id
        User currentUser = userService.getCurrentUser()
        Boolean preventSalesViewEdit = userService.getSuperUser(currentUser) ? Boolean.FALSE : Boolean.TRUE
        List<Feature> features = featureService.getAll()?.sort({ it.formattedName.toUpperCase() })
        List<String> entityClasses = optimiResourceService.getParentEntityClassIds()
        List<Indicator> indicators = indicatorService.getIndicatorsByEntityClasses(entityClasses)?.findAll({
            !it.deprecated
        })
        Query q = Query.collection.findOne([queryId: "workFlowQueryId", active: true]) as Query
        List<WorkFlow> workFlows = q?.workFlowList
        LicenseTemplate licenseTemplate

        if (id) {
            licenseTemplate = licenseService.getLicenseTemplateById(id)
            indicators = indicators.findAll({ !licenseTemplate.licensedIndicatorIds?.contains(it.indicatorId) })
            features = features.findAll({ !licenseTemplate.licensedFeatureIds?.contains(it.id.toString()) })
            workFlows = workFlows.findAll({ !licenseTemplate.licensedWorkFlowIds?.contains(it.workFlowId) })
            List<ProjectTemplate> compatibleProjectTemplates = userService.getSuperUser(currentUser) ? licenseTemplateService.getCompatibleProjectTemplates(projectTemplateService.getAllPublicTemplates(), licenseTemplate) : []

            [licenseTemplate: licenseTemplate, indicators: indicators, features: features, entityClasses: entityClasses, preventSalesViewEdit: preventSalesViewEdit,
             workFlows      : workFlows, compatibleProjectTemplates: compatibleProjectTemplates, user: currentUser]
        } else {
            [licenseTemplate: licenseTemplate, indicators: indicators, features: features, entityClasses: entityClasses, preventSalesViewEdit: preventSalesViewEdit,
             workFlows      : workFlows, compatibleProjectTemplates: null, user: currentUser]
        }
    }

    def licenseFeatures() {
        [features: Feature.list()?.sort({ it.formattedName }), featureClasses: Constants.LicenseFeatureClass.list(), isAdmin: userService.isSystemAdmin(userService.getCurrentUser())]
    }

    def updateLicenseFeature() {
        boolean saved = false
        String featureId = params.featureId
        String type = params.type
        String name = params.name
        Boolean userLinked = params.boolean('userLinked')
        boolean ok = true

        if (!featureId?.trim()) {
            flashService.setErrorAlert('Please enter feature id!')
            ok = false
        }

        if (!type) {
            flashService.setErrorAlert('Please select feature class!')
            ok = false
        }

        if (!name?.trim()) {
            flashService.setErrorAlert('Please enter name!')
            ok = false
        }


        if (ok) {
            saved = featureService.createOrUpdateFeature(featureId?.replaceAll(/\s/, ''), type, name, userLinked)
            if (saved) {
                flashService.setFadeSuccessAlert("Feature saved!")
            } else {
                flashService.setFadeErrorAlert("Something went wrong, contact a developer.")
            }
        }

        chain(action: "licenseFeatures")
    }

    def deleteLicenseFeature() {
        featureService.removeFeature(params.id as String)
        render([output: 'done', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def licenseTemplateLicenses() {
        String id = params.id
        User currentUser = userService.getCurrentUser()
        Boolean preventSalesViewEdit = userService.getSuperUser(currentUser) ? Boolean.FALSE : Boolean.TRUE
        LicenseTemplate licenseTemplate = licenseService.getLicenseTemplateById(id)
        List<License> licenses = licenseTemplateService.getUsedInLicenses(licenseTemplate?.id)
        List<License> activeLicenses = licenses?.findAll({ it.getValid() })

        if (activeLicenses) {
            licenses.removeAll(activeLicenses)
        }
        [licenseTemplate     : licenseTemplate, activeLicenses: activeLicenses, inactiveLicense: licenses,
         preventSalesViewEdit: preventSalesViewEdit]

    }

    def saveTemplate() {
        String id = params.id
        String name = params.name
        String applyDefaultProjectTemplate = params.applyDefaultProjectTemplate
        List<String> applyProjectTemplates = params.list('applyProjectTemplate')
        List<String> indicatorIds = params.list('indicatorId')
        List<String> featureIds = params.list('featureId')
        List<String> workFlowIds = params.list('workFlowId')
        LicenseTemplate licenseTemplate
        List<String> featuresToRemove = []
        List<String> indicatorsToRemove = []
        List<String> workFlowsToRemove = []
        List<String> projectTemplatesToRemove = []

        if (id) {
            licenseTemplate = licenseService.getLicenseTemplateById(id)

            if (licenseTemplate?.licensedFeatureIds) {
                licenseTemplate.licensedFeatureIds.each {
                    if (!featureIds.contains(it)) {
                        featuresToRemove.add(it.toString())
                    }
                }
            }

            if (licenseTemplate?.licensedIndicatorIds) {
                licenseTemplate.licensedIndicatorIds.each {
                    if (!indicatorIds.contains(it)) {
                        indicatorsToRemove.add(it.toString())
                    }
                }
            }

            if (licenseTemplate?.licensedWorkFlowIds) {
                licenseTemplate.licensedWorkFlowIds.each {
                    if (!workFlowIds.contains(it)) {
                        workFlowsToRemove.add(it.toString())
                    }
                }
            }
            if (licenseTemplate?.publicProjectTemplateIds) {
                licenseTemplate.publicProjectTemplateIds.each {
                    if (!applyProjectTemplates.contains(it)) {
                        projectTemplatesToRemove.add(it.toString())
                    }
                }
            }
        } else {
            licenseTemplate = new LicenseTemplate()
        }
        licenseTemplate.name = name
        licenseTemplate.licensedIndicatorIds = indicatorIds
        licenseTemplate.licensedFeatureIds = featureIds
        licenseTemplate.licensedWorkFlowIds = workFlowIds

        if (indicatorsToRemove && applyProjectTemplates) {
            // filter out the incompatible project templates when license template remove some indicators
            applyProjectTemplates = projectTemplateService.doFilterCompatibleTemplatesId(applyProjectTemplates, licenseTemplate)
            // added more template ids that are not compatible
            licenseTemplate.publicProjectTemplateIds.each {
                if (!applyProjectTemplates.contains(it) && !projectTemplatesToRemove.contains(it)) {
                    projectTemplatesToRemove.add(it.toString())
                }
            }
            if (!applyProjectTemplates?.contains(applyDefaultProjectTemplate)) {
                applyDefaultProjectTemplate = null
            }
        }

        licenseTemplate.defaultPublicProjectTemplateId = applyDefaultProjectTemplate
        licenseTemplate.publicProjectTemplateIds = applyProjectTemplates

        if (licenseTemplate.validate()) {
            if (id) {
                licenseTemplate = licenseTemplate.merge(flush: true, failOnError: true)
            } else {
                licenseTemplate = licenseTemplate.save(flush: true, failOnError: true)
            }

            projectTemplateService.updateLinkedLicenseTemplateInProjectTemplates(applyProjectTemplates, projectTemplatesToRemove, licenseTemplate)

            List<License> licenses = licenseService.getLicensesByTemplateId(licenseTemplate.id.toString())

            if (licenses) {
                String errorMessage = ""
                licenses.each { License license ->
                    licenseService.handleAlreadyUsedTemplateFeatures(license, licenseTemplate, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)
                    String errorFromApply = licenseService.applyTemplateToLicense(license, licenseTemplate, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)

                    if (!errorFromApply) {
                        license.merge(flush: true, failOnError: true)
                    } else {
                        errorMessage = "${errorMessage}<br/>${license.name}: ${errorFromApply}"
                    }
                }

                if (errorMessage) {
                    flash.errorAlert = "${errorMessage}"
                } else {
                    flash.fadeSuccessAlert = "Template successfully applied"
                }
            } else {
                flash.fadeSuccessAlert = "Template successfully saved"
            }
        } else {
            flash.fadeErrorAlert = renderErrors(bean: licenseTemplate)
        }
        redirect action: "licenseTemplate", id: licenseTemplate.id?.toString()
    }

    def removeTemplate() {
        String id = params.id

        if (id) {
            LicenseTemplate licenseTemplate = licenseService.getLicenseTemplateById(id)

            if (licenseTemplate) {
                List<License> licenses = licenseService.getLicensesByTemplateId(licenseTemplate.id.toString())

                if (licenses) {
                    List<String> featuresToRemove = licenseTemplate.licensedFeatureIds
                    List<String> indicatorsToRemove = licenseTemplate.licensedIndicatorIds
                    List<String> workFlowsToRemove = licenseTemplate.licensedWorkFlowIds
                    List<String> projectTemplatesToRemove = licenseTemplate.publicProjectTemplateIds
                    String errorMessage = ""

                    licenses.each { License license ->
                        licenseService.handleAlreadyUsedTemplateFeatures(license, licenseTemplate, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)
                        String errorFromApply = licenseService.applyTemplateToLicense(license, null, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)

                        if (!errorFromApply) {
                            license.licenseTemplateIds.remove(licenseTemplate.id.toString())
                            license.merge(flush: true, failOnError: true)
                        } else {
                            errorMessage = "${errorMessage}<br/>${license.name}: ${errorFromApply}"
                        }
                    }

                    if (errorMessage) {
                        flash.errorAlert = "${errorMessage}"
                    } else {
                        licenseTemplate.delete(flush: true)
                        flash.fadeSuccessAlert = "Template successfully removed"
                    }
                } else {
                    licenseTemplate.delete(flush: true)
                    flash.fadeSuccessAlert = "Template successfully removed"
                }
            }
        }
        redirect action: "list"
    }

    def removeTemplateFromLicense() {
        String licenseId = params.id
        String templateId = params.licenseTemplateId

        if (licenseId && templateId) {
            LicenseTemplate licenseTemplate = licenseService.getLicenseTemplateById(templateId)
            License license = licenseService.getById(licenseId)

            if (license && licenseTemplate) {
                List<String> featuresToRemove = licenseTemplate.licensedFeatureIds
                List<String> indicatorsToRemove = licenseTemplate.licensedIndicatorIds
                List<String> workFlowsToRemove = licenseTemplate.licensedWorkFlowIds
                List<String> projectTemplatesToRemove = licenseTemplate.publicProjectTemplateIds
                licenseService.handleAlreadyUsedTemplateFeatures(license, licenseTemplate, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)
                String errorFromApply = licenseService.applyTemplateToLicense(license, null, featuresToRemove, indicatorsToRemove, workFlowsToRemove, projectTemplatesToRemove)

                if (!errorFromApply) {
                    license.licenseTemplateIds.remove(licenseTemplate.id.toString())
                    license.merge(flush: true, failOnError: true)
                }

                if (errorFromApply) {
                    flash.errorAlert = "${errorFromApply}"
                } else {
                    flash.fadeSuccessAlert = "Template successfully removed"
                }
            }
        }
        redirect action: "form", id: licenseId
    }

    def applyTemplate() {
        String licenseId = params.id
        String templateId = params.templateId

        if (licenseId && templateId) {
            License license = licenseService.getById(licenseId)
            LicenseTemplate licenseTemplate = licenseService.getLicenseTemplateById(templateId)

            if (license && licenseTemplate) {
                String errorFromApply = licenseService.applyTemplateToLicense(license, licenseTemplate)

                if (!errorFromApply) {
                    license.merge(flush: true, failOnError: true)
                    flash.fadeSuccessAlert = "Template successfully applied"
                } else {
                    flash.fadeErrorAlert = "${errorFromApply}"
                }
            }
        }
        redirect action: "form", id: licenseId
    }

    def form() {
        long now = System.currentTimeMillis()
        User currentUser = userService.getCurrentUser()
        boolean userManagingAllowed = false
        def disabled
        def features = featureService.getAll()?.sort({ it.formattedName.toUpperCase() })
        Query q = Query.collection.findOne([queryId: "workFlowQueryId", active: true]) as Query
        List<WorkFlow> workFlows = q?.workFlowList
        def entityClasses = optimiResourceService.getParentEntityClassIds()
        def buildingTypes = optimiResourceService.getBuildingTypeIds()
        Map<String, String> licenseTypes = [:]
        Boolean preventSalesViewEdit = Boolean.FALSE
        List<Resource> countries = optimiResourceService.getResourcesByResourceGroupsAndSkipResourceTypes(["world"], null, null, null, null, null)
        List<Resource> states = optimiResourceService.getResourcesByRulesMap(["isState": true])
        Map<String, String> floatingLicenses = Constants.FloatingLicense.map()

        Constants.LicenseType.values().collect({ it.toString() }).each { String type ->
            String message = message(code: "license.type.${type}")
            licenseTypes.put(type, "${message}")
        }

        if (!userService.getSalesView(currentUser)) {
            disabled = "disabled"
        }

        if (params.id || chainModel?.license) {
            License license = params.id ? licenseService.getById(params.id) : chainModel.license
            //User manager = license?.manager
            //12536
            List<String> managerIds = []
            List<User> manager = license?.managers
            if (manager) {
                manager?.each {
                    managerIds.add(it.id)
                }
            }
            if (license && (managerIds?.contains(currentUser.id) || userService.getSuperUser(currentUser) || userService.getSalesView(currentUser) || userService.getConsultant(currentUser))) {
                //if (license && (   manager.stream().anyMatch({ m -> m.id == currentUser.id })|| currentUser.superUser || currentUser.salesView)) {
                // if (license && (currentUser.id.equals(manager?.id) || currentUser.superUser || currentUser.salesView)) {
                List<LicenseTemplate> licenseTemplates = LicenseTemplate.list()?.sort({ it.name })

                if (licenseTemplates && license.licenseTemplateIds) {
                    licenseTemplates = licenseTemplates.findAll({
                        !license.licenseTemplateIds.contains(it.id.toString())
                    })
                }

                if (license.licenseManagerCanAddLicenseUserIds || userService.getSalesView(currentUser)) {
                    userManagingAllowed = true
                }

                if (!userService.getSuperUser(currentUser)) {
                    preventSalesViewEdit = Boolean.TRUE
                }

                List<Account> accounts = accountService.getAccountsForLicense(license.id.toString())
                List<Document> allAccounts = Account.collection.find()?.toList()?.sort({ it.companyName?.toLowerCase() })

                def compatibleEntityClasses

                if (license.compatibleEntityClasses) {
                    compatibleEntityClasses = entityClasses?.findAll({ license.compatibleEntityClasses.contains(it) })
                } else {
                    compatibleEntityClasses = entityClasses
                }
                List<Indicator> indicators = indicatorService.getIndicatorsByEntityClasses(compatibleEntityClasses)?.findAll({
                    !it.deprecated
                })
                def licensedIndicators = license.licensedIndicators

                List<Indicator> allIndicators = new ArrayList<Indicator>(indicators)
                List<Indicator> apiIndicators = new ArrayList<Indicator>(indicators?.findAll({
                    it.indicatorUse?.equalsIgnoreCase("design")
                }))

                if (indicators && licensedIndicators) {
                    indicators.removeAll(licensedIndicators)
                }

                if (license.licensedFeatureIds && features) {
                    features.removeAll({
                        DomainObjectUtil.stringsToObjectIds(license.licensedFeatureIds).contains(it.id)
                    })
                }

                if (license.licensedWorkFlowIds && workFlows) {
                    workFlows.removeAll({ license.licensedWorkFlowIds.contains(it.workFlowId) })
                }
                List<Indicator> designIndicators = indicators?.findAll({ it.indicatorUse?.equalsIgnoreCase("design") })
                List<Indicator> operatingIndicators = indicators?.findAll({
                    it.indicatorUse?.equalsIgnoreCase("operating")
                })

                List<ProjectTemplate> compatibleProjectTemplates = userService.getSuperUser(currentUser) ? license?.getCompatibleProjectTemplates(projectTemplateService.getAllPublicTemplates()) : []

                Set<LicenseTier> licenseTiers = licenseService.getLicenseTiers()

                log.info("PARAMS HANDELED IN ${System.currentTimeMillis() - now}ms")
                [indicators                : indicators, designIndicators: designIndicators?.sort({
                    indicatorService.getLocalizedName(it)?.toLowerCase()
                }),
                 operatingIndicators       : operatingIndicators?.sort({
                     indicatorService.getLocalizedName(it)?.toLowerCase()
                 }), floatingLicenses      : floatingLicenses, floatingUserManagingAllowed: true,
                 features                  : features, userManagingAllowed: userManagingAllowed, workFlows: workFlows,
                 license                   : license, licenseTypes: licenseTypes, userTypes: Constants.EntityUserType.values(),
                 entityClasses             : entityClasses, buildingTypes: buildingTypes, disabled: disabled, preventSalesViewEdit: preventSalesViewEdit,
                 accounts                  : accounts, licenseTemplates: licenseTemplates, licenseInvitations: license.getLicenseInvitations(),
                 apiIndicators             : apiIndicators?.sort({
                     indicatorService.getLocalizedName(it)?.toLowerCase()
                 }), countries             : countries, states: states, allAccounts: allAccounts, allIndicators: allIndicators.sort({
                    indicatorService.getLocalizedName(it)?.toLowerCase()
                }),
                 compatibleProjectTemplates: compatibleProjectTemplates,
                 licenseTiers              : licenseTiers
                ]

            } else {
                redirect controller: "main"
            }
        } else {
            List<Indicator> indicators = indicatorService.getAllActiveIndicators()
            List<Indicator> apiIndicators = new ArrayList<Indicator>(indicators?.findAll({
                it.indicatorUse?.equalsIgnoreCase("design")
            }))
            List<Indicator> designIndicators = indicators?.findAll({ it.indicatorUse?.equalsIgnoreCase("design") })
            List<Indicator> operatingIndicators = indicators?.findAll({
                it.indicatorUse?.equalsIgnoreCase("operating")
            })
            def validFrom = new SimpleDateFormat("dd.MM.yyyy").format(new Date())
            [disabled           : disabled, indicators: indicators, allIndicators: indicators.sort({
                indicatorService.getLocalizedName(it)?.toLowerCase()
            }), features        : features, validFrom: validFrom, floatingLicenses: floatingLicenses, buildingTypes: buildingTypes,
             licenseTypes       : licenseTypes, userTypes: Constants.EntityUserType.values(), entityClasses: entityClasses, workFlows: workFlows,
             designIndicators   : designIndicators?.sort({ indicatorService.getLocalizedName(it)?.toLowerCase() }), countries: countries,
             operatingIndicators: operatingIndicators?.sort({
                 indicatorService.getLocalizedName(it)?.toLowerCase()
             }), apiIndicators  : apiIndicators?.sort({ indicatorService.getLocalizedName(it)?.toLowerCase() })]
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def save() {
        License license
        String based = params.based
        String type = params.type

        if (params.id) {
            license = licenseService.getLicenseForModifying(params.id)

            if (license) {
                license.properties = params

                if (based) {
                    if ("user".equals(based)) {
                        license.userBased = Boolean.TRUE
                        license.projectBased = null
                    } else {
                        license.userBased = null
                        license.projectBased = Boolean.TRUE
                    }
                }

                if (params.freeze) {
                    license.frozen = Boolean.TRUE
                } else if (params.unfreeze) {
                    license.frozen = null
                }
            }
        } else {
            license = new License(params)

            if (based) {
                if ("user".equals(based)) {
                    license.userBased = Boolean.TRUE
                    license.projectBased = null
                } else {
                    license.userBased = null
                    license.projectBased = Boolean.TRUE
                }
            }
        }


        if (license) {
            if (type) {
                if ((license.userBased && (type.equals("Demo") || type.equals("Education") || type.equals("Trial"))) || (license.projectBased && type.equals("Production"))) {
                    flash.warningAlert = "Make sure the license type is correct, you created an untypical license."
                }
            }

            if (!params.conditionalEntityClasses) {
                license.conditionalEntityClasses = null
            }
            if (!params.conditionalCountries) {
                license.conditionalCountries = null
            }
            if (!params.conditionalBuildingTypes) {
                license.conditionalBuildingTypes = null
            }
            if (!params.conditionalStates) {
                license.conditionalStates = null
            }

            if (license.validate()) {
                Date now = new Date()

                if (license.validUntil.before(now)) {
                    flash.warningAlert = message(code: 'admin.license.expiration_in_the_past')
                }
                List<String> accountIds

                if (params.accountId) {
                    accountIds = params.list("accountId")
                }
                licenseService.saveLicense(license, params.newManagerId, accountIds)
                flash.fadeSuccessAlert = message(code: 'admin.license.save_successful')
            } else {
                flash.errorAlert = renderErrors(bean: license)
            }

            if (license.id) {

                String newOrganization = params.newOrganization
                String licenseId = license.id.toString()
                accountService.addLicense(licenseId, newOrganization)

                redirect action: "form", params: [id: license.id]
            } else {
                chain action: "form", model: [license: license]
            }
        } else {
            redirect controller: "main", action: "list"
        }
    }

    def list() {
        List<String> parentEntityClasses = optimiResourceService.getParentEntityClassIds()
        List<License> licenses = licenseService.getLicenses()
        List<License> activeLicenses = licenses?.findAll({ License l -> !l.expired })
        List<License> expiredLicenses = licenses?.findAll({ License l -> l.expired })
        List<License> frozenLicenses = licenses?.findAll({ License l -> l.frozen })
        List<LicenseTemplate> licenseTemplates = LicenseTemplate.list()?.sort({ it.name })
        User user = userService.getCurrentUser()
        Map<String, Integer> activeLicensesPerEntityClass = [:]

        if (activeLicenses) {
            parentEntityClasses.each { String resourceId ->
                def perEntityClass = activeLicenses.findAll({ it.compatibleEntityClasses?.contains(resourceId) })
                activeLicensesPerEntityClass.put(resourceId, perEntityClass ? perEntityClass.size() : 0)
            }
        }
        List<Feature> features = featureService.getAll()?.sort({ it.formattedName.toUpperCase() })
        List<Indicator> indicators = indicatorService.getIndicatorsByEntityClasses(parentEntityClasses)?.findAll({
            !it.deprecated
        })
        [activeLicenses              : activeLicenses, expiredLicenses: expiredLicenses, user: user,
         activeLicensesPerEntityClass: activeLicensesPerEntityClass, licenseTemplates: licenseTemplates,
         features                    : features, indicators: indicators,
         frozenLicenses              : frozenLicenses]
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def remove() {
        licenseService.delete(params.id)
        flash.fadeSuccessAlert = message(code: 'admin.license.delete_successful')
        redirect action: "list"
    }

    def addObjects() {
        if (params.id) {
            def license

            if (params.newManagerId) {
                boolean addOk = licenseService.addManager(params.id, params.newManagerId, params.licenseManagerCanAddLicenseUserIds)

                if (addOk) {
                    flash.fadeSuccessAlert = "New manager added!"
                } else {
                    flash.errorAlert = "Could not find user with email: ${params.newManagerId}"
                }
            } else if (params.addEntity && params.entityId) {
                boolean addOk = licenseService.addEntity(params.id, params.entityId)

                if (addOk) {
                    flash.fadeSuccessAlert = message(code: "admin.license.entity_added")
                } else {
                    flash.errorAlert = message(code: "admin.license.maxEntities.reached")
                }
            } else if (params.addIndicator && params.indicatorId) {
                License addOk = licenseService.addIndicator(params.id, params.indicatorId)

                if (addOk) {
                    flash.fadeSuccessAlert = message(code: "admin.license.indicator_added")
                } else {
                    flash.errorAlert = "Unable to save the license<br/>${renderErrors(bean: License.get(params.id))}"
                }
            } else if (params.addIndicatorOperating && params.indicatorIdOperating) {
                License addOk = licenseService.addIndicator(params.id, params.indicatorIdOperating)

                if (addOk) {
                    flash.fadeSuccessAlert = message(code: "admin.license.indicator_added")
                } else {
                    flash.errorAlert = "Unable to save the license<br/>${renderErrors(bean: License.get(params.id))}"
                }
            } else if (params.addFeature && params.featureId) {
                licenseService.addFeature(params.id, params.featureId)
                flash.fadeSuccessAlert = message(code: "admin.license.feature_added")
            } else if (params.addUser && params.email && params.userType) {
                String errorFromImport = licenseService.addUser(params.id, params.email, params.userType, session)

                if (!errorFromImport) {
                    flash.fadeSuccessAlert = message(code: "admin.license.user_added")
                } else {
                    flash.errorAlert = errorFromImport
                }
            } else if (params.addLicenseUser && params.licenseUserEmail) {
                def addOk = licenseService.addLicenseUser(params.id, params.licenseUserEmail, session)

                if (addOk) {
                    flash.fadeSuccessAlert = message(code: "admin.license.license_user_added")
                } else {
                    inviteUserToLicense(params.licenseUserEmail, params.id)
                }
            } else if (params.addApiIndicatorId) {
                licenseService.addApiIndicator(params.id, params.apiIndicatorId?.toString())
            } else if (params.addWorkFlow) {
                licenseService.addWorkFlow(params.id, params.workFlowId?.toString())
            } else if (params.addIndicatorReadonly && params.readonlyIndicatorId) {
                licenseService.addReadonlyIndicator(params.id, params.readonlyIndicatorId?.toString())
            }
            redirect action: "form", id: params.id
        } else {
            flash.fadeErrorAlert = message(code: "admin.license.give_name_first")
            chain action: "form"
        }
    }

    def removeLicenseUser() {
        if (params.id && params.userId) {
            licenseService.removeLicenseUser(params.id, params.userId)
            flash.fadeSuccessAlert = message(code: "admin.license.license_user_removed")
        }
        redirect action: "form", id: params.id
    }
    //12536
    def removeLicenseManagerId() {
        if (params.id && params.userId) {
            licenseService.removeLicenseManagerId(params.id, params.userId)
            flash.fadeSuccessAlert = message(code: "admin.license.license_manager_removed")
        }
        redirect action: "form", id: params.id
    }

    def removeInvitedUser() {
        if (params.invitationId) {
            licenseService.removeInvitedUser(params.invitationId)
            flash.fadeSuccessAlert = "Invitation removed"
        }
        redirect action: "form", id: params.id
    }

    def checkoutFloatingUser() {
        if (params.id && params.userId) {
            licenseService.checkoutFloatingUser(params.id, params.userId)
            flash.fadeSuccessAlert = "User successfully checked-out from floating license"
        }
        redirect action: "form", id: params.id
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeUser() {
        if (params.id && params.userId) {
            licenseService.removeUser(params.id, params.userId)
            flash.fadeSuccessAlert = message(code: "admin.license.user_removed")
            redirect action: "form", id: params.id
        } else {
            redirect action: "list"
        }
    }

    private String generateRegisterLink(String lang) {
        String channelToken = channelFeatureService.getChannelFeature(session)?.token
        lang = lang ? lang : "en"
        return createLink(base: "$request.scheme://$request.serverName$request.contextPath",
                controller: "index", action: "form", params: [lang: lang, channelToken: channelToken])
    }

    private Boolean inviteUserToLicense(String email, String licenseId) {
        if (email && licenseId) {
            Locale locale = localeResolverUtil.resolveLocale(session, request, response)
            locale = locale ? locale : new Locale("en")
            email = email.trim()
            String currentUser = userService.getCurrentUser()?.name
            License license = licenseService.getLicenseById(licenseId)
            def emailTo = email
            def emailSubject = message(code: "license.request_to_register_subject", args: [currentUser, license.name], encodeAs: "Raw")
            def emailFrom = message(code: "email.support")
            def replyToAddress = message(code: "email.support")
            def emailBody = message(code: "license.request_to_register_body",
                    args: [currentUser, license.name, generateRegisterLink(locale.getLanguage())
                    ], locale: locale)
            try {
                optimiMailService.sendMail({
                    to emailTo
                    from emailFrom
                    replyTo replyToAddress
                    subject emailSubject
                    html emailBody
                }, emailTo)
                flash.fadeSuccessAlert = message(code: "license.request_to_register_sent")
            }
            catch (Exception e) {
                loggerUtil.warn(log, "Error in sending request to register: " + e)
                flash.fadeErrorAlert = message(code: "license.request_to_register_failed")
            }
        }
    }


    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeFeature() {
        if (params.id && params.featureId) {
            licenseService.removeFeature(params.id, params.featureId)
            flash.fadeSuccessAlert = message(code: "admin.license.feature_removed")
            redirect action: "form", id: params.id
        } else {
            redirect action: "list"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeWorkFlow() {
        if (params.id && params.workFlowId) {
            licenseService.removeWorkFlow(params.id, params.workFlowId)
            flash.fadeSuccessAlert = "WorkFlow removed!"
            redirect action: "form", id: params.id
        } else {
            redirect action: "list"
        }
    }

    def removeEntity() {
        Boolean redirectToProject = params.boolean("redirectToProject")
        if (params.id && params.entityId) {
            licenseService.removeEntity(params.id, params.entityId, params.boolean('permanentEntityDeletion'))
            flash.fadeSuccessAlert = message(code: "admin.license.entity_removed")

            if (redirectToProject) {
                redirect controller: "entity", action: "show", params: [entityId: params.entityId]
            } else {
                redirect action: "form", id: params.id
            }
        } else {
            redirect action: "list"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeIndicator() {
        if (params.id && params.indicatorId) {
            licenseService.removeIndicator(params.id, params.indicatorId)
            flash.fadeSuccessAlert = message(code: "admin.license.indicator_removed")
            redirect action: "form", id: params.id
        } else {
            redirect action: "list"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def removeReadonlyIndicator() {
        if (params.id && params.indicatorId) {
            licenseService.removeReadonlyIndicator(params.id, params.indicatorId)
            flash.fadeSuccessAlert = message(code: "admin.license.indicator_removed")
            redirect action: "form", id: params.id
        } else {
            redirect action: "list"
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getLicenseTypeUsers() {
        String entityClass = request.JSON?.entityClass
        String licenseType = request.JSON?.licenseType
        String active = request.JSON?.active
        String featureId = request.JSON?.featureId
        List<String> licenseTemplateIds = request.JSON?.licenseTemplateIds
        List<String> indicatorIds = request.JSON?.indicatorIds
        boolean getManagers = request.JSON?.getManagers ? true : false
        Feature feature = featureService.getFeatureById(featureId)
        List<Document> indicatorsNameOnly
        List<License> licenses

        log.info("License type users, getting licenses...")

        Closure searchRestrictions = { builder ->
            if (entityClass) {
                builder.eq('compatibleEntityClasses', entityClass)
            }

            if (licenseType) {
                builder.eq('type', licenseType)
            }

            if (licenseTemplateIds) {
                licenseTemplateIds.each {
                    builder.eq('licenseTemplateIds', it)
                }
            }

            if (featureId) {
                builder.eq('licensedFeatureIds', featureId)
            }

            if (indicatorIds) {
                indicatorsNameOnly = Indicator.collection.find([indicatorId: [$in: indicatorIds]], [indicatorId: 1, name: 1]).toList()

                builder.or {
                    indicatorIds.each {
                        builder.eq('licensedIndicatorIds', it)
                    }
                }
            }
        }

        if (active == "false") {
            licenses = licenseService.getExpiredLicensesWithCriteria(searchRestrictions)
        } else {
            licenses = licenseService.getValidLicensesWithCriteria(searchRestrictions)
        }

        log.info("License type users, got ${licenses?.size()} valid licenses")

        List<String> users = []
        List<User> userObjects = []
        int count = 0
        String table = ""

        if (licenses) {
            Date currentDate = new Date()
            int i = 1
            int licensesCount = licenses.size()
            licenses.each { License license ->
                if (getManagers) {
                    license.licensedEntities?.each { Entity entity ->
                        if ((license.type.equals(Constants.LicenseType.EDUCATION.toString()) || Constants.LicenseType.TRIAL.toString()) && license.trialLength) {
                            if (Duration.between(DateUtil.dateToLocalDatetime(license.getChangeHistory(entity)?.dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() <= license.trialLength) {
                                def entityUsers = entity.users
                                if (entityUsers) {
                                    userObjects.addAll(entityUsers)
                                }
                            }
                        } else {
                            def entityUsers = entity.users
                            if (entityUsers) {
                                userObjects.addAll(entityUsers)
                            }
                        }
                    }
                } else {
                    if (license.licenseUserIds) {
                        def licenseUsers = license.licenseUsers

                        if (licenseUsers) {
                            userObjects.addAll(licenseUsers)
                        }
                    }
                }
                log.info("License loop progress: ${i} / ${licensesCount}: License: ${license.name}")
                i++
            }

            if (userObjects) {
                userObjects.removeAll({ User u -> u == null || u.accountLocked || u.accountExpired || !u.emailValidated || u.username.contains("bionova") || !u.enabled })
                userObjects = userObjects.unique({ User u -> u.username })
                count = userObjects.size()

                if (count) {
                    session?.setAttribute("dumpLicenseUserIds", userObjects.collect({ it.id.toString() }))
                    String downloadLink = opt.link(controller: "fileExport", action: "dumpLicenseUsers", style: "margin-left: 20px;") {
                        "Download as Excel"
                    }

                    table = "<table class=\"table-striped table\"><thead><tr><td><b>Name</b></td><td><b>Country</b></td><td><b>Organization</b></td><td><b>Email</b></td><td>Licenses name</td><td> Has floating license</td><td>${downloadLink}</td></tr></thead><tbody>"
                    userObjects.each { User u ->
                        table = "${table}<tr><td>${u.name}</td><td>${u.country ?: ""}</td><td>${u.organizationName ?: ""}</td><td>${u.username}</td><td>${userService.getUsableLicenses(u)?.collect({ it.name })}</td><td>${userService.getUsableLicenses(u)?.findAll({ it.isFloatingLicense })?.size() > 0}</td><td></td></tr>"
                    }
                    table = "${table}</tbody></table>"
                }
            }
        }
        render text: "<strong>Amount: ${count.toString()}<br/> ${licenseType ? "LicenseType: ${licenseType}<br/>" : ""}${active ? "Active: ${active}<br/>" : ""}${licenseTemplateIds ? "License templates: ${licenseService.getLicenseTemplatesByIds(licenseTemplateIds)?.collect({ it.name })?.flatten().toString()}<br/>" : ""}${feature ? "Feature: ${feature.name}<br/>" : ""}${indicatorsNameOnly ? "Indicatord: ${indicatorsNameOnly.collect({ it.name.get("EN") })?.flatten().toString()} <br/>" : ""}</strong>${table}"
    }

    def changeHistory() {
        License license = licenseService.getLicenseForChangeHistories(params.id)
        List changeHistories = license?.changeHistories
        [license: license, changeHistories: changeHistories?.sort({ it.date })]
    }

    def trialProjects() {
        List<License> trialLicenses = licenseService.getValidTrials()
        Date currentDate = new Date()
        List<Entity> trialEntities = trialLicenses.collectMany({ it.licensedEntities ?: [] })

        Map<String, List<String>> entitiesWithLicenses = [:]
        trialLicenses.each { license ->
            def licenseName = license.name ?: ""
            license.licensedEntityIds?.each { objectId ->
                String id = objectId?.toString()
                if (id && licenseName) {
                    List<String> list = entitiesWithLicenses.get(id)
                    if (list) {
                        list.add(licenseName)
                    } else {
                        entitiesWithLicenses.put(id, [licenseName])
                    }
                }
            }
        }

        List<User> trialUsers = userService
                .getUsersByEntities(trialEntities)
                .findAll { it ->
                    !emailConfiguration.isProtectedEmail(it.username)
                }
        Map<String, Map<String, String>> usersMapWithEntities = [:]
        Map<String, Map<String, String>> userEntitiesWithIds = [:]

        trialUsers.each { User user ->

            Map<String, String> userDetails = [:]
            Map<String, String> entitiesDetails = [:]
            List<String> entityIdsWithLicenseExpired = []
            userDetails.put("signedUp", user.registrationTime)
            userDetails.put("isAuthenticated", user.emailValidated?.toString())
            String trialCount = user.trialCount ? user.trialCount.toString() : "0"
            String trialCountInfra = user.trialCountForInfra ? user.trialCountForInfra.toString() : "0"
            userDetails.put("trialCount", trialCount)
            userDetails.put("trialCountInfra", trialCountInfra)
            String licenseCount = user.licenseIds ? user.licenseIds.size().toString() : "0"
            userDetails.put("licenseCount", licenseCount)
            userDetails.put("lastLogin", user.getLastLogin())
            List<Entity> allProject = trialEntities.findAll({ (it.managerIds.contains(user?.id?.toString()) || it.modifierIds.contains(user?.id?.toString())) && it && !it.deleted })
            def designsFound = allProject?.collect({ it?.childEntities })?.flatten()?.findAll({ it && !it.deleted })
            def designsListFull = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(designsFound?.collect { it?.entityId }?.unique())], "deleted": false], ["_id": 1, "name": 1, "operatingPeriodAndName": 1, "queryReady": 1])?.toList()
            def designsCompleted = designsListFull?.findAll({ it?.queryReady?.findAll({ it?.value })?.size() > 1 })?.size()

            def licenseNameFound = []
            if (allProject) {
                allProject.each { Entity d ->
                    entitiesDetails.put(d.name, d.id.toString())
                    List<String> licenseNames = entitiesWithLicenses.get(d.id.toString())
                    if (licenseNames) {
                        licenseNameFound.add(licenseNames)
                    }

                }
                entityIdsWithLicenseExpired = allProject.findAll({ it.expiredDesignLicenses?.collect({ Constants.LicenseType.TRIAL.toString().equals(it.type) }) })?.collect({ it.id.toString() })
                int entityCount = allProject.size() - entityIdsWithLicenseExpired?.size()
                userDetails.put("entityActiveCount", entityCount?.toString())
                userDetails.put("entityCount", allProject.size()?.toString())


            }
            String licenseNameFoundString = licenseNameFound ? licenseNameFound?.flatten()?.unique()?.toString() : ""
            userDetails.put("licenseNames", licenseNameFoundString)
            userDetails.put("designNames", designsFound?.collect({ it?.operatingPeriodAndName }).toString())
            userDetails.put("designCount", designsFound?.size()?.toString())
            userDetails.put("designCompletedCount", designsCompleted?.toString())
            userDetails.put("entityIdsWithLicenseExpired", entityIdsWithLicenseExpired)


            usersMapWithEntities.put(user.username, userDetails)
            userEntitiesWithIds.put(user.username, entitiesDetails)
        }

        [trialLicenses: trialLicenses?.findAll({ it.licensedEntityIds }), currentDate: currentDate, trialUsers: trialUsers, usersMapWithEntities: usersMapWithEntities, userEntitiesWithIds: userEntitiesWithIds]
    }

    def getFloatingLicenseForModal() {
        User user = userService.getCurrentUser()
        String licenseId = params.licenseId
        Boolean reloadOnFloatingStatusChange = params.boolean('reloadOnFloatingStatusChange') ? true : false
        String heading = ""
        String body = ""

        if (licenseId) {
            License license = licenseService.getLicenseById(licenseId)

            if (license && user && license.floatingAllowedForUser(user)) {
                List<License> allUsableFloatingLicenses = userService.getUsableValidFloatingLicenses(user)
                if (allUsableFloatingLicenses && allUsableFloatingLicenses.size() > 1) {
                    heading = "${heading}<a href=\"javascript:\" class=\"floatingLicenseDropdown dropdown-toggle\" style=\"border-radius:5px !important;\" data-toggle=\"dropdown\">${license.name}  <i style=\"font-size: 0.9em;\" class=\"fa fa-caret-down\"></i></a>"
                    heading = "${heading}<ul class=\"dropdown-menu\" style=\"text-align: left;\">"
                    allUsableFloatingLicenses.each { License l ->
                        heading = "${heading}<li class=\"sub_menu\" style=\"text-align: left;\"><a href=\"javascript:\" onclick=\"openFloatingLicenseModal('${l.id.toString()}', true, null, ${reloadOnFloatingStatusChange})\">${l.name}</a></li>"
                    }
                    heading = "${heading}</ul>"
                } else {
                    heading = "<h2>${license.name}</h2>"
                }
                body = "${body}<h3>${message(code: "license.floating.currentlyCheckedIn")}: ${license.floatingLicenseCheckedInUsers?.size() ?: 0} / ${license.floatingLicenseMaxUsers} <i class=\"fas fa-sync greenInfoBubble\" style=\"font-size: 1.1em !important;\" onclick=\"openFloatingLicenseModal('${licenseId}', true, null, ${reloadOnFloatingStatusChange})\" aria-hidden=\"true\"></i></h3>"
                body = "${body}<table class=\"table-striped table\"><thead><tr><th>${message(code: "entity.name")}</th><th>${message(code: "email")}</th><th>${message(code: "license.floating.checkedIn")}</th></tr></thead><tbody>"

                license.floatingLicenseCheckedInUsers?.each { FloatingLicenseUser floatingLicenseUser ->

                    body = "${body}<tr><td>${maskingService.maskSurname(floatingLicenseUser.name)}</td><td>${maskingService.maskEmail(floatingLicenseUser.username)}</td><td>${floatingLicenseUser.checkedIn.format('dd.MM.yyyy HH:mm')}</td></tr>"
                }
                body = "${body}</tbody></table>"

                if (license.floatingLicenseCheckedInUsers?.find({ it.userId == user.id.toString() })) {
                    body = "${body}<div style=\"width: 100%; text-align:center;\"><a id=\"openFloatingLicenseModal-anchor\" href=\"javascript:\" onclick=\"openFloatingLicenseModal('${licenseId}', true, false, false, ${reloadOnFloatingStatusChange})\" class=\"btn btn-warning\">${message(code: "license.floating.checkOut")}</a></div>"
                } else {
                    if (license.floatingLicenseCheckedInUsers?.size() >= license.floatingLicenseMaxUsers) {
                        body = "${body}<div style=\"width: 100%; text-align:center;\"><a id=\"openFloatingLicenseModal-anchor\" href=\"#\" class=\"btn btn-primary disabled\" style=\"color: white !important;\">${message(code: "license.floating.full")}</a></div>"
                    } else {
                        body = "${body}<div style=\"width: 100%; text-align:center;\"><a id=\"openFloatingLicenseModal-anchor\" href=\"javascript:\" onclick=\"openFloatingLicenseModal('${licenseId}', true, true, false, ${reloadOnFloatingStatusChange})\" class=\"btn btn-primary\">${message(code: "license.floating.checkIn")}</a></div>"
                    }
                }

            }
        }
        render([heading: heading, body: body, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def toggleCurrentUserInFloatingLicense() {
        User user = userService.getCurrentUser()
        String licenseId = params.licenseId
        Boolean checkIn = params.boolean('checkInCurrentUser')
        String response = "ok"

        if (licenseId && user && checkIn != null) {
            License license = licenseService.getLicenseById(licenseId)

            if (license && license.floatingAllowedForUser(user)) {
                if (checkIn == Boolean.TRUE) {
                    if (license.floatingLicenseCheckedInUsers) {
                        if (license.floatingLicenseMaxUsers && license.floatingLicenseMaxUsers > license.floatingLicenseCheckedInUsers.size()) {
                            FloatingLicenseUser floatingLicenseUser = new FloatingLicenseUser()
                            floatingLicenseUser.userId = user.id.toString()
                            floatingLicenseUser.name = user.name
                            floatingLicenseUser.username = user.username
                            floatingLicenseUser.checkedIn = new Date()
                            license.floatingLicenseCheckedInUsers.add(floatingLicenseUser)
                            license.merge(flush: true)
                        } else {
                            response = "${message(code: "license.floating.full")}"
                        }
                    } else {
                        FloatingLicenseUser floatingLicenseUser = new FloatingLicenseUser()
                        floatingLicenseUser.userId = user.id.toString()
                        floatingLicenseUser.name = user.name
                        floatingLicenseUser.username = user.username
                        floatingLicenseUser.checkedIn = new Date()
                        license.floatingLicenseCheckedInUsers = [floatingLicenseUser]
                        license.merge(flush: true)
                    }
                } else {
                    if (license.floatingLicenseCheckedInUsers) {
                        FloatingLicenseUser floatingLicenseUser = license.floatingLicenseCheckedInUsers.find({
                            it.userId == user.id.toString()
                        })

                        if (floatingLicenseUser) {
                            license.floatingLicenseCheckedInUsers.remove(floatingLicenseUser)
                            license.merge(flush: true)
                        }
                    }
                }
            }
        }
        render text: response
    }

    def changeTrialExpiryDate() {
        User user = userService.getCurrentUser()
        String licenseId = params.licenseId
        String entityId = params.entityId
        String newDate = params.newDate
        String response = "fail"

        if (userService.getSalesView(user) && licenseId && entityId && newDate) {
            License license = licenseService.getLicenseById(licenseId)
            Entity entity = entityService.getEntityById(entityId, null, userService.getSalesView(user))
            Date newDateObject = new SimpleDateFormat("dd.MM.yyyy").parse(newDate)

            if (license && entity && newDateObject && license.trialLength) {
                newDateObject = newDateObject.minus(license.trialLength)
                LicenseEntityChangeHistory licenseEntityChangeHistory = license.getChangeHistory(entity)

                if (licenseEntityChangeHistory) {
                    licenseEntityChangeHistory.dateAdded = newDateObject
                    license.save(flush: true)
                    response = "ok"
                }
            }
        }
        render text: response
    }

    def getResourceUsageByEntities() {
        String id = params.id
        if (id) {
            License license = licenseService.getLicenseById(id)
            if (license) {
                List<Entity> licensedEntities = license.getLicensedEntities()
                List<Document> listOfResources = []
                if (licensedEntities) {
                    licensedEntities?.each { Entity parent ->
                        def children = entityService.getChildEntities(parent, Constants.EntityClass.DESIGN.getType())
                        children?.each { Entity e ->
                            def datasets = datasetService.getDatasetsByEntity(e)
                            if (datasets) {
                                datasets?.each {
                                    Document doc = datasetService.getResourceAsDocument(it)
                                    if (doc && !listOfResources.contains(doc)) {
                                        listOfResources.add(doc)
                                    }
                                }
                            }
                        }
                    }
                    Map<List<String>, List<Document>> resourcesWithDataProperties = [:]
                    listOfResources?.each { Document resource ->
                        List<String> dataProperties = (List<String>) resource.dataProperties*.toUpperCase()
                        dataProperties.each { String dataProperty ->
                            List<Document> existing = resourcesWithDataProperties.get(dataProperty)
                            if (existing) {
                                existing.add(resource)
                            } else {
                                existing = [resource]
                            }
                            resourcesWithDataProperties.put(dataProperty, existing)
                        }
                    }
                    [licensedEntities: licensedEntities.size(), resourcesWithDataProperties: resourcesWithDataProperties]
                }
            }
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def licenseTiers() {
        User user = userService.getCurrentUser()
        Set<LicenseTier> tiers = licenseService.getLicenseTiers()
        [tiers: tiers,
         user : user]
    }

    @Secured([Constants.ROLE_SUPER_USER])
    def saveLicenseTier() {
        licenseService.saveLicenseTier(params)
        chain(action: 'licenseTiers')
    }

    @Secured([Constants.ROLE_SUPER_USER])
    def deleteLicenseTier() {
        licenseService.deleteLicenseTier(params)
        chain(action: 'licenseTiers')
    }

    @Secured([Constants.ROLE_SUPER_USER])
    def applyTier() {
        licenseService.applyLicenseTier(params)
        chain(action: 'form', params: [id: params.licenseId])
    }
}
