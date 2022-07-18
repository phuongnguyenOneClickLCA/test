package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.util.OptimiCollectionUtils
import com.bionova.optimi.util.OptimiStringUtils
import grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.collections4.CollectionUtils
import org.bson.Document
import org.grails.web.util.WebUtils

import javax.servlet.http.HttpSession

class ProjectTemplateService {

    LoggerUtil loggerUtil
    FlashService flashService
    IndicatorService indicatorService
    LicenseService licenseService
    LicenseTemplateService licenseTemplateService
    UserService userService
    AccountService accountService
    EntityService entityService
    DatasetService datasetService
    QueryService queryService
    OptimiStringUtils optimiStringUtils
    def channelFeatureService
    def optimiResourceService
    def groovyPageRenderer
    def stringUtilsService
    def questionService

    ProjectTemplate getTemplateById(String templateId, Boolean deleted = false) {
        ProjectTemplate template = null

        if (templateId) {
            try {
                template = ProjectTemplate.findByIdAndDeleted(DomainObjectUtil.stringToObjectId(templateId), deleted)
            } catch (e) {
                loggerUtil.error(log, "Error occurred in getProjectTemplateById", e)
                flashService.setErrorAlert("Error occurred in getProjectTemplateById: ${e.getMessage()}", true)
            }
        }
        return template
    }

    List<ProjectTemplate> getAllPublicTemplates(Boolean deleted = false) {
        try {
            return ProjectTemplate.collection.find([deleted: deleted, isPublic: true])?.toList()?.collect({ it as ProjectTemplate })
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getProjectTemplateById", e)
            flashService.setErrorAlert("Error occurred in getProjectTemplateById: ${e.getMessage()}", true)
            return []
        }
    }

    List<ProjectTemplate> getTemplatesByIds(List<String> templateIds) {
        List<ProjectTemplate> templates = []
        try {
            if (templateIds) {
                templates = getTemplatesByIdsAsDocuments(templateIds)?.collect({ it as ProjectTemplate })
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getTemplatesByIds", e)
            flashService.setErrorAlert("Error occurred in getTemplatesByIds: ${e.getMessage()}", true)
        }
        return templates
    }

    List<Document> getTemplatesByIdsAsDocuments(List<String> templateIds) {
        List<Document> templates = []
        try {
            if (templateIds) {
                templates = ProjectTemplate.collection.find([_id: [$in: DomainObjectUtil.stringsToObjectIds(templateIds)], deleted: false])?.toList()
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getTemplatesByIdsAsDocuments", e)
            flashService.setErrorAlert("Error occurred in getTemplatesByIdsAsDocuments: ${e.getMessage()}", true)
        }
        return templates
    }

    // used for saving new template and editing an existing template
    ProjectTemplate saveTemplate(GrailsParameterMap params, Boolean editMode) {
        ProjectTemplate template = null

        try {
            String templateId = params.templateId
            List<String> indicatorIds = indicatorService.doFilterBenchmarkToolInIndicatorList(params.list('indicatorIds')) ?: []
            String openQuery = params.openQuery ?: ''
            String openIndicator = params.openIndicator ?: ''
            Boolean openCarbonDesigner = params.boolean('openCarbonDesigner') ?: false
            Boolean expandAllInputs = params.boolean('expandAllInputs') ?: false
            Boolean isPublic = params.boolean('isPublic') ?: false

            if (templateId) {
                // edit mode
                template = getTemplateById(templateId)
            } else {
                template = new ProjectTemplate()
            }

            if (template != null) {
                if (editMode) {
                    removeIncompatibleLicenses(indicatorIds, openIndicator, isPublic, params.accountId as String, template)
                    if (isPublic) {
                        removeIncompatibleLicenseTemplates(indicatorIds, openIndicator, template)
                    }
                }

                template.name = optimiStringUtils.removeIllegalCharacters(params.templateName) ?: 'Unnamed template'
                template.projectName = optimiStringUtils.removeIllegalCharacters(params.projectName)
                template.designName = optimiStringUtils.removeIllegalCharacters(params.designName)
                template.buildingTypeResourceId = params.defaultBuildingType ?: ''
                template.countryResourceId = params.defaultCountry ?: ''
                template.useIndicatorIds = indicatorIds
                template.applyLcaDefaults = params.boolean('applyLcaDefaults') ?: false
                template.isPublic = isPublic
                template.isMandatory = params.boolean('isMandatory') ?: false
                template.allowChange = params.boolean('allowChange') ?: false
                template.compatibleEntityClass = params.entityClass
                template.childEntityId = params.fromEntityId
                template.productType = params.productType

                boolean optionsOk = validateTemplateOptions(indicatorIds, openIndicator, openQuery, openCarbonDesigner)
                // do not save the template options to db if they are not logical
                if (optionsOk) {
                    template.openQuery = openQuery
                    template.openIndicator = openIndicator
                    template.openCarbonDesigner = openCarbonDesigner
                    template.expandAllInputs = expandAllInputs
                }

                template = template.merge()
            }
        } catch (e) {
            flashService.setErrorAlert("Error in saving project template: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in saving project template", e)
        }

        return template
    }

    ProjectTemplate removeTemplate(String templateId = null) {
        ProjectTemplate template = null
        try {
            if (templateId) {
                template = getTemplateById(templateId)
                if (template) {
                    template.deleted = true
                    template = template.merge(flush: true, failOnError: true)
                }
            }
        } catch (e) {
            flashService.setErrorAlert("Error in removing project template: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in removing project template", e)
        }

        return template
    }

    Boolean validateTemplateOptions(List<String> indicatorIds, String openIndicator, String openQuery, Boolean openCarbonDesigner) {
        boolean optionsOk = true

        try {
            if (!indicatorIds) {
                optionsOk = false
            } else {
                if (openCarbonDesigner) {
                    if (!openIndicator) {
                        // can't open CD without selecting indicator
                        optionsOk = false
                    } else {
                        // check if the selected indicator allows CD
                        optionsOk = indicatorService.getIndicatorByIndicatorId(openIndicator, true)?.allowedCarbonDesignerRegions
                    }

                    if (openQuery) {
                        // cannot open CD and Query at the same time
                        optionsOk = false
                    }
                } else if (openQuery) {
                    if (!openIndicator) {
                        // can't open query without selecting indicator
                        optionsOk = false
                    } else {
                        // check if the selected indicator has selected query, and it's not a project level by any chance.
                        IndicatorQuery indQuery = indicatorService.getIndicatorByIndicatorId(openIndicator, true)?.indicatorQueries?.find({ it.queryId == openQuery })
                        optionsOk = indQuery && !indQuery.projectLevel
                    }
                }
            }
        } catch (e) {
            optionsOk = false
            flashService.setErrorAlert("Error in validating project template options: ${e.getMessage()}", true)
            loggerUtil.error(log, "Error in validateProjectTemplateOptions", e)
        }

        return optionsOk
    }

    Boolean linkPublicTemplateToLicense(String licenseId, List<String> templateIds = [], String applyDefaultTemplateId = '') {
        Boolean saveOk = false
        try {
            if (licenseId && userService.getSuperUser(userService.getCurrentUser(true))) {
                License license = licenseService.getLicenseById(licenseId)

                if (license) {
                    Boolean okToSave = verifyTemplatesAreCompatible(templateIds, license)

                    if (okToSave) {
                        updateLinkedLicenseInTemplates(license.publicProjectTemplateIds, templateIds, license)
                        license.publicProjectTemplateIds = templateIds
                        // applyDefaultTemplateId can be empty. It means that Super user wants to unlink default template from license
                        license.defaultPublicProjectTemplateId = applyDefaultTemplateId && templateIds?.contains(applyDefaultTemplateId) ? applyDefaultTemplateId : null
                        saveOk = license.save(flush: true, failOnError: true) ? true : false
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in linkPublicTemplateToLicense", e)
            flashService.setErrorAlert("Error occurred in linkPublicTemplateToLicense: ${e.getMessage()}", true)
        }
        return saveOk
    }

    Boolean linkTemplatesToLicense(String licenseId, String accountId, List<String> templateIds = [], String applyDefaultTemplateId = '') {
        Boolean saveOk = false
        if (licenseId && accountId) {
            try {
                License license = licenseService.getLicenseById(licenseId)

                if (license) {
                    Boolean okToSave = verifyTemplatesAreCompatible(templateIds, license)

                    if (okToSave) {
                        updateLinkedLicenseInTemplates(license.projectTemplateIdsByAccount?.get(accountId), templateIds, license, accountId)
                        if (!license.projectTemplateIdsByAccount) {
                            license.projectTemplateIdsByAccount = new LinkedHashMap<String, List<String>>()
                        }

                        license.projectTemplateIdsByAccount.put(accountId, templateIds)

                        if (!license.defaultProjectTemplateIdByAccount) {
                            license.defaultProjectTemplateIdByAccount = new LinkedHashMap<String, String>()
                        }
                        // applyDefaultTemplateId can be empty. It means user wants to unlink a default template from license
                        if (applyDefaultTemplateId && templateIds?.contains(applyDefaultTemplateId)) {
                            license.defaultProjectTemplateIdByAccount.put(accountId, applyDefaultTemplateId)
                        } else {
                            license.defaultProjectTemplateIdByAccount.put(accountId, null)
                        }
                        saveOk = license.save(flush: true, failOnError: true) ? true : false
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in linkLicenseToTemplates", e)
                flashService.setErrorAlert("Error occurred in linkLicenseToTemplates: ${e.getMessage()}", true)
            }
        }
        return saveOk
    }

    List<String> doFilterCompatibleTemplatesId(List<String> templateIds, LicenseTemplate licenseTemplate = null, License license = null, List<ProjectTemplate> templates = []) {
        if (!templates && templateIds) {
            templates = getTemplatesByIds(templateIds)
        }

        if (templates) {
            try {
                if (licenseTemplate) {
                    templateIds = licenseTemplateService.getCompatibleProjectTemplates(templates, licenseTemplate)?.collect({ it.id?.toString() })
                } else if (license) {
                    templateIds = license.getCompatibleProjectTemplates(templates)?.collect({ it.id?.toString() })
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in doFilterCompatibleTemplatesId", e)
                flashService.setErrorAlert("Error occurred in doFilterCompatibleTemplatesId: ${e.getMessage()}", true)
            }
        }
        return templateIds
    }

    Boolean verifyTemplatesAreCompatible(List<String> templateIds, License license) {
        Boolean ok = false
        if (license) {
            try {
                if (templateIds) {
                    // check again the templates to be linked to make sure whole list is compatible
                    List<ProjectTemplate> compatibles = license.getCompatibleProjectTemplates(getTemplatesByIds(templateIds))
                    if (compatibles && compatibles.size() == templateIds.size()) {
                        ok = true
                    }
                } else {
                    // templateIds can be empty. It means that user wants to unlink template from license
                    ok = true
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in verifyTemplatesAreCompatibleToLinkToLicense", e)
                flashService.setErrorAlert("Error occurred in verifyTemplatesAreCompatibleToLinkToLicense: ${e.getMessage()}", true)
            }
        }
        return ok
    }

    String renderProjectTemplateToLicenseTableAsString(Boolean isPublicTable, String accountId = '', User user = null, Account account = null) {
        String tableAsString = ''
        try {
            List<ProjectTemplate> templates = []
            List<License> licenses = []

            if (!user) {
                user = userService.getCurrentUser()
            }
            Boolean isMainUser = false
            if (isPublicTable) {
                if (userService.getSuperUser(user)) {
                    licenses = licenseService.getLicenses()?.findAll({ License l -> !l.expired })
                    // all active licenses in sw
                    templates = getAllPublicTemplates()
                    isMainUser = true
                }
            } else {
                if (accountId) {
                    if (!account) {
                        account = accountService.getAccount(accountId)
                    }
                    licenses = accountService.getLicenses(account.licenseIds)
                    templates = accountService.getProjectTemplates(account.projectTemplateIds)
                    isMainUser = account?.mainUserIds?.contains(user?.id?.toString()) || userService.getSuperUser(user)
                }
            }

            tableAsString = groovyPageRenderer.render(template: "/projectTemplate/projectTemplateToLicenseTable", model: [licenses     : licenses,
                                                                                                                          accountId    : accountId,
                                                                                                                          templates    : templates,
                                                                                                                          isPublicTable: isPublicTable,
                                                                                                                          isMainUser   : isMainUser,
                                                                                                                          user         : user])
        } catch (e) {
            loggerUtil.error(log, "Error occurred in renderProjectTemplateToLicenseTableAsString", e)
            flashService.setErrorAlert("Error occurred in renderProjectTemplateToLicenseTableAsString: ${e.getMessage()}", true)
        }
        return tableAsString
    }

    /**
     * Gets all project templates for each license and put to a map
     * This method does a lot of cleaning to make sure the templates are correct for user (license check, entity class check, benchmark tool, mark default template, etc.)
     *
     * @param licenses
     * @param user
     * @param entityClass to filter the templates compatible to entity class
     * @return a map <licenseId, list of templates Documents for the license>
     */
    Map<String, List<Document>> getLicenseToTemplatesMap(List<License> licenses, User user, String entityClass) {
        Map<String, List<Document>> licenseToTemplates = [:]
        try {
            if (licenses?.size() > 0 && entityClass && !Constants.EntityClass.notSupportedForTemplate.contains(entityClass)) {
                if (!user) {
                    user = userService.getCurrentUser()
                }

                Account account = userService.getAccount(user)
                licenses.each { License license ->
                    List<String> templateIds = []
                    if (license.projectTemplateIdsByAccount?.get(account?.id?.toString())) {
                        templateIds += license.projectTemplateIdsByAccount?.get(account?.id?.toString())
                    }
                    if (license.publicProjectTemplateIds) {
                        templateIds += license.publicProjectTemplateIds
                    }

                    if (templateIds) {
                        List<Document> templateDocs = getTemplatesByIdsAsDocuments(templateIds)
                        // run filter again to ensure compatibility with license before sending to frontend
                        removeIncompatibleTemplateDocs(templateDocs, license)
                        if (templateDocs) {
                            filterTemplateDocsByEntityClass(templateDocs, entityClass)
                            DomainObjectUtil.addStringIdToDocuments(templateDocs, true)
                            convertNameMarkersInTemplatesDocuments(templateDocs, user)
                            removeUnlicensedIndicators(templateDocs, license)
                            removeUnlicensedOpenCarbonDesignerOption(templateDocs, license)
                            removeIncompatibleBenchmarkToolInTemplates(templateDocs)
                            markDefaultTemplate(templateDocs, license, account)
                            licenseToTemplates.put(license.id?.toString(), templateDocs)
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getLicenseToTemplatesMap", e)
            flashService.setErrorAlert("Error occurred in getLicenseToTemplatesMap: ${e.getMessage()}", true)
        }
        return licenseToTemplates
    }

    List<Document> convertNameMarkersInTemplatesDocuments(List<Document> templates, User user) {
        if (templates) {
            try {
                templates.each { Document template ->
                    if (template.projectName) {
                        template.projectName = convertMarkers(template.projectName as String, user)
                    }

                    if (template.designName) {
                        template.designName = convertMarkers(template.designName as String, user)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in convertNameMarkersInTemplatesDocuments", e)
                flashService.setErrorAlert("Error occurred in convertNameMarkersInTemplatesDocuments: ${e.getMessage()}", true)
            }
        }
        return templates
    }

    String convertMarkers(String nameWithMarkers, User user) {

        if (nameWithMarkers) {
            try {
                if (!user) {
                    user = userService.getCurrentUser()
                }
                if (nameWithMarkers.contains('*USER*')) {
                    nameWithMarkers = nameWithMarkers.replace('*USER*', user?.name ?: '')
                }
                if (nameWithMarkers.contains('*ORG*')) {
                    nameWithMarkers = nameWithMarkers.replace('*ORG*', userService.getAccount(user)?.companyName ?: '')
                }
                if (nameWithMarkers.contains('*DATE*')) {
                    String dateFormat = userService.getUserDateFormat(user).replaceAll(/\./, '/')
                    nameWithMarkers = nameWithMarkers.replace('*DATE*', new Date().format(dateFormat))
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in convertMarkers", e)
                flashService.setErrorAlert("Error occurred in convertMarkers: ${e.getMessage()}", true)
            }
        }
        return nameWithMarkers
    }

    void filterTemplateDocsByEntityClass(List<Document> templates, String entityClass) {
        if (templates?.size() > 0 && entityClass) {
            templates.removeIf { it.compatibleEntityClass != entityClass }
        }
    }

    // logic similar to getCompatibleProjectTemplates, need to update both if business logic changes
    List<Document> removeIncompatibleTemplateDocs(List<Document> templates, License license) {
        if (templates && license) {
            try {
                Set<String> toRemove = []
                templates.each { Document template ->
                    boolean ok = false

                    if (template.useIndicatorIds) {
                        if (license.licensedIndicatorIds) {
                            // license must have at least one licensed tool that is set in template
                            if (template.useIndicatorIds.intersect(license.licensedIndicatorIds)?.size()) {
                                ok = true
                            }
                            // if template is set with opening a tool, this tool must be licensed
                            if (template.openIndicator) {
                                ok = license.licensedIndicatorIds.contains(template.openIndicator)
                            }
                        }
                    } else {
                        // if template doesn't set any tool, then it's compatible.
                        ok = true
                    }

                    if (!ok) {
                        toRemove.add(template._id.toString())
                    }
                }
                if (toRemove) {
                    templates.removeAll({ toRemove.contains(it._id.toString()) })
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeIncompatibleTemplateDocs", e)
                flashService.setErrorAlert("Error occurred in removeIncompatibleTemplateDocs: ${e.getMessage()}", true)
            }
        }
        return templates
    }

    List<Document> removeUnlicensedIndicators(List<Document> templates, License license) {
        if (templates && license) {
            try {
                templates.each { Document template ->
                    if (template?.useIndicatorIds && license.licensedIndicatorIds) {
                        template?.useIndicatorIds = license.licensedIndicatorIds.intersect(template?.useIndicatorIds)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeUnlicensedIndicators", e)
                flashService.setErrorAlert("Error occurred in removeUnlicensedIndicators: ${e.getMessage()}", true)
            }
        }
        return templates
    }

    List<Document> removeUnlicensedOpenCarbonDesignerOption(List<Document> templates, License license) {
        if (templates && license) {
            try {
                templates.each { Document template ->
                    if (template?.openCarbonDesigner && !license.licensedFeatures?.collect({ it.featureId })?.flatten()?.contains(Feature.CARBON_DESIGNER)) {
                        template?.remove('openCarbonDesigner')
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeUnlicensedOpenCarbonDesignerOption", e)
                flashService.setErrorAlert("Error occurred in removeUnlicensedOpenCarbonDesignerOption: ${e.getMessage()}", true)
            }
        }
        return templates
    }

    List<Document> markDefaultTemplate(List<Document> templates, License license, Account account) {
        if (templates && license) {
            try {
                String defaultTemplateId = license.defaultProjectTemplateIdByAccount?.get(account?.id?.toString()) ?: license.defaultPublicProjectTemplateId ?: ''

                if (defaultTemplateId) {
                    templates.each { Document template ->
                        if (template.id == defaultTemplateId) {
                            template.isDefault = true
                        }
                    }
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in markDefaultTemplate", e)
                flashService.setErrorAlert("Error occurred in markDefaultTemplate: ${e.getMessage()}", true)
            }
        }
        return templates
    }

    /**
     * Remove the benchmark tool from useIndicatorIds of each template if no other tool in list is compatible with benchmark
     * @param templates
     */
    void removeIncompatibleBenchmarkToolInTemplates(List<Document> templates) {
        if (templates) {
            try {
                templates?.each { Document template ->
                    if (template?.useIndicatorIds) {
                        template.useIndicatorIds = indicatorService.doFilterBenchmarkToolInIndicatorList(template.useIndicatorIds as List<String>)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeIncompatibleBenchmarkToolInTemplates", e)
                flashService.setErrorAlert("Error occurred in removeIncompatibleBenchmarkToolInTemplates: ${e.getMessage()}", true)
            }
        }
    }

    /**
     * Gets the templates details of each template in the licenseToTemplates map
     * @param licenseToTemplates
     * @param licenses
     * @return a map <licenseId-templateId, list of template details popup HTML>
     */
    Map<String, String> getlicenseToTemplateDetailsMap(Map<String, List<Document>> licenseToTemplates, List<License> licenses) {
        Map<String, String> licenseToTemplateDetails = [:]
        if (licenseToTemplates) {
            try {
                Question productTypeQuestion = null
                licenseToTemplates.each { String licenseId, List<Document> templates ->
                    templates?.each { Document template ->
                        if (template) {
                            if (template.productType && !productTypeQuestion) {
                                productTypeQuestion = questionService.getProductTypeQuestion()
                            }
                            String templateId = new String(template.id as String)
                            License linkedLicense = licenses?.find({ it.id?.toString() == licenseId })
                            String details = renderProjectTemplateDetailsAsString(template as ProjectTemplate, linkedLicense, productTypeQuestion)
                            template.id = templateId
                            if (details) {
                                String key = licenseId + '-' + template.id
                                licenseToTemplateDetails.put(key, details)
                            }
                        }
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in renderProjectTemplateDetailsAsString", e)
                flashService.setErrorAlert("Error occurred in renderProjectTemplateDetailsAsString: ${e.getMessage()}", true)
            }
        }
        return licenseToTemplateDetails
    }

    String renderProjectTemplateDetailsAsString(ProjectTemplate template, License license, Question productTypeQuestion) {
        String detailsAsString = ''
        if (template) {
            try {
                detailsAsString = groovyPageRenderer.render(template: "/projectTemplate/projectTemplateDetails", model: [template: template, license: license, productTypeQuestion: productTypeQuestion])
            } catch (e) {
                loggerUtil.error(log, "Error occurred in renderProjectTemplateDetailsAsString", e)
                flashService.setErrorAlert("Error occurred in renderProjectTemplateDetailsAsString: ${e.getMessage()}", true)
            }
        }
        return detailsAsString.replaceAll("\"", "'")
    }

    String renderTemplateDetailsListAsString(Map<String, List<Document>> licenseToTemplates, List<License> licenses) {
        String render = ''
        if (licenseToTemplates && licenses) {
            try {
                render = groovyPageRenderer.render(template: "/projectTemplate/detailsList", model: [licenseToTemplateDetailsMap: getlicenseToTemplateDetailsMap(licenseToTemplates, licenses)])
            } catch (e) {
                loggerUtil.error(log, "Error occurred in renderTemplateDetailsListAsString", e)
                flashService.setErrorAlert("Error occurred in renderTemplateDetailsListAsString: ${e.getMessage()}", true)
            }
        }
        return render
    }

    Document convertLicenseToDoc(License license) {
        Document doc = null
        if (license) {
            try {
                doc = new Document()
                doc.id = license.id?.toString()
                doc.name = license.name ?: ''
                doc.freeForAllEntities = license.freeForAllEntities ?: false
                doc.planetary = license.planetary ?: false
                doc.conditionalCountries = license?.conditionalCountries ?: []
                doc.conditionalBuildingTypes = license?.conditionalBuildingTypes ?: []
            } catch (e) {
                loggerUtil.error(log, "Error occurred in convertLicenseToDocForRender", e)
                flashService.setErrorAlert("Error occurred in convertLicenseToDocForRender: ${e.getMessage()}", true)
            }
        }
        return doc
    }

    Boolean verifyLicensesRetrievedByLicenseKeyInParams(GrailsParameterMap params = null, User user = null) {
        Boolean ok = true
        if (!params) {
            params = WebUtils.retrieveGrailsWebRequest()?.getParams()
        }
        if (!user) {
            user = userService.getCurrentUser()
        }

        if (params) {
            try {
                // licenseIdRetrievedByLicenseKey set in frontend
                if (params.list('licenseId') && params.list('licenseIdRetrievedByLicenseKey')) {
                    List<String> activateLicenseIds = params.list('licenseId').intersect(params.list('licenseIdRetrievedByLicenseKey')) as List<String>
                    if (activateLicenseIds) {
                        List<License> licensesToBeActivated = licenseService.getLicensesByIds(activateLicenseIds)
                        if (licensesToBeActivated) {
                            licensesToBeActivated.each { License license ->
                                // continue only if it's still ok
                                if (ok) {
                                    if (license.maxEntitiesReached) {
                                        ok = false
                                    } else {
                                        if (Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                                            if (license.educationLicenseKeyValid) {
                                                entityService.sendJoinCodeActivatedMail(license)
                                            } else {
                                                ok = false
                                            }
                                        } else if (user && !license.allowedForUser(user)) {
                                            // floating license, user not allowed
                                            ok = false
                                        }
                                    }
                                }
                            }
                        } else {
                            // licenses to be activated are somehow not found
                            ok = false
                        }
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in verifyLicensesActivatedByLicenseKey", e)
                flashService.setErrorAlert("Error occurred in verifyLicensesActivatedByLicenseKey: ${e.getMessage()}", true)
            }
        }
        return ok
    }

    void updateLinkedLicenseInTemplates(List<String> currentTemplateIds, List<String> templateIdsFromParams, License license, String accountId = null) {
        try {
            if (!templateIdsFromParams) {
                // user want to unlink all templates
                getTemplatesByIds(currentTemplateIds)?.each { template ->
                    removeLinkedLicense(license, accountId, template)
                }
            } else {
                List<String> linkNewTemplateIds = OptimiCollectionUtils.getNewItemsBetweenTwoLists(currentTemplateIds, templateIdsFromParams)
                if (linkNewTemplateIds) {
                    getTemplatesByIds(linkNewTemplateIds)?.each { template ->
                        addLinkedLicense(license, accountId, template)
                    }
                }

                List<String> unlinkTemplateIds = OptimiCollectionUtils.getRemovedItemsBetweenTwoLists(currentTemplateIds, templateIdsFromParams)
                if (unlinkTemplateIds) {
                    getTemplatesByIds(unlinkTemplateIds)?.each { template ->
                        removeLinkedLicense(license, accountId, template)
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in updateLinkedLicenseInTemplates", e)
            flashService.setErrorAlert("Error occurred in updateLinkedLicenseInTemplates: ${e.getMessage()}", true)
        }
    }

    void updateLinkedLicenseTemplateInProjectTemplates(List<String> linkNewTemplateIds, List<String> unlinkTemplateIds, LicenseTemplate licenseTemplate) {
        try {
            if (linkNewTemplateIds) {
                getTemplatesByIds(linkNewTemplateIds)?.each { template ->
                    addLinkedLicenseTemplate(licenseTemplate, template)
                }
            }

            if (unlinkTemplateIds) {
                getTemplatesByIds(unlinkTemplateIds)?.each { template ->
                    removeLinkedLicenseTemplate(licenseTemplate, template)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in updateLinkedLicenseTemplateInTemplates", e)
            flashService.setErrorAlert("Error occurred in updateLinkedLicenseTemplateInTemplates: ${e.getMessage()}", true)
        }
    }

    // logic similar to removeIncompatibleTemplateDocs, need to update both if business logic changes
    List<ProjectTemplate> getCompatibleProjectTemplates(List<ProjectTemplate> templates, License license = null, LicenseTemplate licenseTemplate = null) {
        List<ProjectTemplate> compatibles = []
        if (templates) {
            try {
                List<String> licensedIndicatorIds = license?.licensedIndicatorIds ?: licenseTemplate?.licensedIndicatorIds ?: []
                templates.each { ProjectTemplate template ->
                    boolean ok = false

                    if (template.useIndicatorIds) {
                        if (licensedIndicatorIds) {
                            // license / license template must have at least one licensed tool that is set in template
                            if (template.useIndicatorIds.intersect(licensedIndicatorIds)?.size()) {
                                ok = true
                            }
                            // if template is set with opening a tool, this tool must be licensed
                            if (template.openIndicator) {
                                ok = licensedIndicatorIds.contains(template.openIndicator)
                            }
                        }
                    } else {
                        // if template doesn't set any tool, then it's compatible.
                        ok = true
                    }

                    if (ok) {
                        compatibles.add(template)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in getCompatibleProjectTemplate", e)
                flashService.setErrorAlert("Error occurred in getCompatibleProjectTemplate: ${e.getMessage()}", true)
            }
        }
        return compatibles
    }

    /**
     * Get the name of the entity template (child entity), in format: project name - child name
     * @param template
     * @return
     */
    String getEntityTemplateName(ProjectTemplate template, Entity entityTemplate = null) {
        String name = ''
        if (!entityTemplate && template?.childEntityId) {
            entityTemplate = entityService.getEntityForShowing(template?.childEntityId, [name: 1, parentEntityId: 1, parentName: 1])
        }

        if (entityTemplate) {
            name = entityTemplate?.parentName + ' - ' + entityTemplate?.name
        }
        return name
    }

    /**
     * Run copy datasets of the selected tools from the entity template to the newly created design / operating period
     * @param targetEntity
     * @param targetEntityId
     * @param fromEntityId
     * @param parent
     * @param indicatorIds
     */
    void handleCopyDatasetsFromEntityTemplate(Entity targetEntity, String targetEntityId, Entity fromEntity, String fromEntityId, Entity parent, List<String> indicatorIds) {
        if (targetEntity && targetEntityId && fromEntityId && indicatorIds?.size() > 0) {
            try {
                List<Indicator> indicators = indicatorService.getIndicatorsByIndicatorIds(indicatorIds)
                List<License> validLicenses = licenseService.getValidLicensesForEntity(parent)
                List<Feature> featuresAvailableForEntity = entityService.getFeatures(validLicenses)

                if (indicators?.size() > 0) {
                    boolean runEcoinventCheck = false
                    Set<String> queryIdsToCopy = []

                    for (Indicator indicator in indicators) {
                        if (indicator) {
                            if (!runEcoinventCheck) {
                                if (indicatorService.isUsingEcoinventData(indicator)) {
                                    runEcoinventCheck = true
                                }
                            }

                            queryIdsToCopy.addAll(queryService.getQueryIdsByIndicatorAndEntity(indicator, parent, featuresAvailableForEntity))
                        }
                    }
                    Indicator indicatorWithRowLimit = indicators?.find { it.maxRowLimitPerDesign != null } ?: indicators[0]


                    if (queryIdsToCopy?.size() > 0) {
                        datasetService.copyDatasetsByQueryIdsList(fromEntityId, targetEntityId, indicatorWithRowLimit?.indicatorId, queryIdsToCopy?.toList(), true, false, true, targetEntity, fromEntity, indicatorWithRowLimit, runEcoinventCheck)
                    }
                }
            } catch(e) {
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('copy.datasets.from.template.error'))
                loggerUtil.error(log, "Error occurred in handleCopyDatasetsFromEntityTemplate", e)
                flashService.setErrorAlert("Error occurred in handleCopyDatasetsFromEntityTemplate: ${e.getMessage()}", true)
            }
        }
    }

    /**
     * Populate few parameters for _projectTemplateModal.gsp
     * Not all needed for the modal are populated in this method
     * @param model
     * @param session
     * @param licenses
     * @param templateIndicatorIds
     * @param entityClass
     */
    void populateModelForTemplateModal(Map model, HttpSession session, List<License> licenses, List<String> templateIndicatorIds = [], String entityClass = null) {
        if (model) {
            Question productTypeQuestion = questionService.getProductTypeQuestion()
            List<Resource> entityClassResources = getEntityClassesForTemplate(session)
            List<Indicator> availableIndicators = indicatorService.getLicensedIndicatorsOfLicenses(licenses)?.findAll({ it.indicatorUse == com.bionova.optimi.construction.Constants.IndicatorUse.DESIGN.indicatorUse })
            Map<String, List<Indicator>> indicatorsByEntityClass = indicatorService.getIndicatorListByEntityClassMap(availableIndicators, entityClassResources?.collect({ it.resourceId }))
            List<Document> buildingTypeResourceGroup = optimiResourceService.getResourcesByResourceGroupsAsDocuments([Constants.ResourceGroup.BUILDING_TYPES.toString()])
            List<Document> countriesResourceGroup = optimiResourceService.getResourcesByResourceGroupsAsDocuments([Constants.ResourceGroup.WORLD.toString(), Constants.ResourceGroup.EUROPE.toString()])

            model << [entityClassResources     : entityClassResources,
                      availableIndicators      : availableIndicators,
                      indicatorsByEntityClass  : indicatorsByEntityClass,
                      buildingTypeResourceGroup: buildingTypeResourceGroup,
                      countriesResourceGroup   : countriesResourceGroup,
                      productTypeQuestion      : productTypeQuestion]

            if (templateIndicatorIds && entityClass) {
                boolean allowCopyingPublicEntities = licenseService.featuresAllowedByLicenses(licenses, [Feature.ALLOW_IMPORT_PUBLIC_ENTITIES])?.get(Feature.ALLOW_IMPORT_PUBLIC_ENTITIES) ?: false
                populateModelForEntityTemplateSelect(model, templateIndicatorIds, entityClass, allowCopyingPublicEntities)
            }
        }
    }

    /**
     * Populate model for _entityTemplateSelect.gsp
     * @param model
     * @param indicatorIds
     * @param entityClass
     */
    void populateModelForEntityTemplateSelect(Map model, List<String> indicatorIds, String entityClass, Boolean allowCopyingPublicEntities) {
        if (model && indicatorIds?.size() > 0 && entityClass) {
            // HUOM: front end must make sure all indicators have same indicatorUse
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorIds[0] as String)
            Query basicQuery = queryService.getBasicQuery()
            Set<String> calculationClassificationList = basicQuery?.calculationClassificationList?.keySet()
            Boolean indicatorCompatible = CollectionUtils.containsAny(basicQuery?.applicableIndicatorForCalculationClass, indicatorIds) ?: false
            Map<String, Map<String, List<Document>>> copyEntitiesMapped = entityService.getChildrenByEntityClassAndQueryReadyWithCustomClass(entityClass, indicator?.indicatorUse, null, indicatorIds, null, calculationClassificationList, allowCopyingPublicEntities)
            model << [basicQuery         : basicQuery,
                      indicatorCompatible: indicatorCompatible,
                      copyEntitiesMapped : copyEntitiesMapped]
        }
    }

    /**
     * Template is not supported for portfolio and org
     * @param session
     * @return
     */
    List<Resource> getEntityClassesForTemplate(HttpSession session) {
        return (channelFeatureService.getEntityClassesForChannel(session, true) as List<Resource>)?.findAll({ !Constants.EntityClass.notSupportedForTemplate.contains(it.resourceId) })
    }

    /**
     * check if a deleted / unauthorized entity template is set in the project template >> set warning
     * @param params
     */
    void checkEntityTemplateValidity(GrailsParameterMap params) {
        ProjectTemplate template = getTemplateById(params.selectedTemplateId as String)
        if (template?.childEntityId) {
            // run direct query to get the object from db without any validation
            Entity entityTemplate = Entity.findById(DomainObjectUtil.stringToObjectId(template.childEntityId))
            isEntityTemplateOkToCopy(entityTemplate)
        }
    }

    boolean isEntityTemplateOkToCopy(Entity entityTemplate) {
        if (entityTemplate) {
            if (entityTemplate.deleted) {
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('deleted.design.template', [getEntityTemplateName(null, entityTemplate)]))
            } else if (!entityService.isUserAllowedToAccessEntity(entityTemplate)) {
                flashService.setErrorAlert(stringUtilsService.getLocalizedText('unauthorized.design.template', [getEntityTemplateName(null, entityTemplate)]))
            } else {
                return true
            }
        }
        return false
    }

    // get the selected indicators in template option
    List<Indicator> getIndicators(List<String> useIndicatorIds) {
        List<Indicator> indicators = []
        try {
            if (useIndicatorIds) {
                indicators = indicatorService.getIndicatorsByIndicatorIds(useIndicatorIds, true)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getIndicatorNames", e)
            flashService.setErrorAlert("Error occurred in getting the indicator names: ${e.getMessage()}", true)
        }

        return indicators
    }

    Indicator getSelectedIndicator(String openIndicator) {
        Indicator ind = null
        try {
            if (openIndicator) {
                ind = indicatorService.getIndicatorByIndicatorId(openIndicator, true)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getSelectedIndicatorName", e)
            flashService.setErrorAlert("Error occurred in getting the selected indicator name: ${e.getMessage()}", true)
        }

        return ind
    }

    List<Query> getDesignQueries(List<License> licenses, String openIndicator) {
        List<Query> designQueries = []
        try {
            if (openIndicator) {
                Indicator indicator = indicatorService.getIndicatorByIndicatorId(openIndicator, true)
                designQueries = queryService.getQueriesByIndicator(indicator, licenses?.collect({it.licensedFeatures}) as List<Feature>,  true)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getDesignQueries for projectTemplate uuid ${id?.toString()}", e)
            flashService.setErrorAlert("Error occurred in getting the design queries for project template: ${e.getMessage()}", true)
        }

        return designQueries
    }

    Query getSelectedQuery(String openQuery) {
        Query query = null
        try {
            if (openQuery) {
                query = queryService.getQueryByQueryId(openQuery)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getSelectedQuery", e)
            flashService.setErrorAlert("Error occurred in getting the selected query: ${e.getMessage()}", true)
        }

        return query
    }

    Resource getBuildingTypeResource(String buildingTypeResourceId) {
        Resource resource = null
        try {
            if (buildingTypeResourceId) {
                resource = optimiResourceService.getResourceWithParams(buildingTypeResourceId)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getBuildingTypeResource", e)
            flashService.setErrorAlert("Error occurred in getting the selected building type: ${e.getMessage()}", true)
        }

        return resource
    }

    Resource getCountryResource(String countryResourceId) {
        Resource resource = null
        try {
            if (countryResourceId) {
                resource = optimiResourceService.getResourceWithParams(countryResourceId)
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in getCountryResource", e)
            flashService.setErrorAlert("Error occurred in getting the country: ${e.getMessage()}", true)
        }

        return resource
    }

    Boolean isCompatibleWithLicense(License license, ProjectTemplate projectTemplate) {
        Boolean isCompatible = false
        try {
            if (license) {
                isCompatible = license.getCompatibleProjectTemplates([projectTemplate])?.size() > 0
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in isCompatibleWithLicense", e)
            flashService.setErrorAlert("Error occurred in isCompatibleWithLicense: ${e.getMessage()}", true)
        }
        return isCompatible
    }

    void removeLinkedLicense(License license, String accountId = null, ProjectTemplate projectTemplate) {
        if (license && projectTemplate) {
            try {
                Boolean okToSave = false

                if (projectTemplate.isPublic) {
                    okToSave = projectTemplate.publicLinkedLicenseIds?.remove(license.id?.toString())
                } else {
                    if (accountId) {
                        okToSave = projectTemplate.linkedLicenseIdsByAccount?.get(accountId)?.remove(license.id?.toString())
                    }
                }

                if (okToSave) {
                    projectTemplate.merge(flush: true, failOnError: true)
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeLinkedLicense", e)
                flashService.setErrorAlert("Error occurred in removeLinkedLicense: ${e.getMessage()}", true)
            }
        }
    }

    void addLinkedLicense(License license, String accountId = null, ProjectTemplate projectTemplate) {
        if (license) {
            try {
                Boolean okToSave = false

                if (projectTemplate.isPublic) {
                    if (!projectTemplate.publicLinkedLicenseIds) {
                        projectTemplate.publicLinkedLicenseIds = new ArrayList<String>()
                    }
                    if (!projectTemplate.publicLinkedLicenseIds?.contains(license.id?.toString())) {
                        projectTemplate.publicLinkedLicenseIds?.add(license.id?.toString())
                        okToSave = true
                    }
                } else {
                    if (accountId) {
                        if (!projectTemplate.linkedLicenseIdsByAccount) {
                            projectTemplate.linkedLicenseIdsByAccount = new LinkedHashMap<String, List<String>>()
                        }

                        List<String> ids = projectTemplate.linkedLicenseIdsByAccount?.get(accountId) ?: []
                        if (!ids?.contains(license.id?.toString())) {
                            ids.add(license.id?.toString())
                            projectTemplate.linkedLicenseIdsByAccount.put(accountId, ids)
                            okToSave = true
                        }
                    }
                }

                if (okToSave) {
                    projectTemplate.merge(flush: true, failOnError: true)
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in addLinkedLicense", e)
                flashService.setErrorAlert("Error occurred in addLinkedLicense: ${e.getMessage()}", true)
            }
        }
    }

    void removeIncompatibleLicenses(List<String> indicatorIdsParam, String openIndicatorParam, Boolean publicMode, String accountId, ProjectTemplate projectTemplate) {
        try {
            Boolean sameIndicatorIds = indicatorIdsParam?.size() == projectTemplate.useIndicatorIds?.size() && projectTemplate.useIndicatorIds?.containsAll(indicatorIdsParam)
            Boolean sameOpenIndicator = projectTemplate.openIndicator == openIndicatorParam
            Boolean runCheck = !(sameIndicatorIds && sameOpenIndicator)
            if (runCheck) {
                List<String> removeIds = []

                if (publicMode && projectTemplate.isPublic && projectTemplate.publicLinkedLicenseIds) {
                    projectTemplate.publicLinkedLicenseIds.each { String licenseId ->
                        License license = licenseService.getLicenseById(licenseId)
                        if (license && !isCompatibleWithLicense(license, projectTemplate)) {
                            removeIds.add(licenseId)
                            license.removeLinkedProjectTemplate(projectTemplate)
                        }
                    }

                    if (removeIds) {
                        projectTemplate.publicLinkedLicenseIds.removeAll(removeIds)
                    }
                } else if (!publicMode && !projectTemplate.isPublic && projectTemplate.linkedLicenseIdsByAccount && accountId) {
                    List<String> linkedLicenseIds = projectTemplate.linkedLicenseIdsByAccount.get(accountId)

                    if (linkedLicenseIds) {
                        linkedLicenseIds?.each { String licenseId ->
                            License license = licenseService.getLicenseById(licenseId)
                            if (license && !isCompatibleWithLicense(license, projectTemplate)) {
                                removeIds.add(licenseId)
                                license.removeLinkedProjectTemplate(projectTemplate, accountId)
                            }
                        }

                        if (removeIds) {
                            if (linkedLicenseIds.containsAll(removeIds)) {
                                projectTemplate.linkedLicenseIdsByAccount.remove(accountId)
                            } else {
                                linkedLicenseIds.removeAll(removeIds)
                                projectTemplate.linkedLicenseIdsByAccount.put(accountId, linkedLicenseIds)
                            }
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in removeIncompatibleLicenses", e)
            flashService.setErrorAlert("Error occurred in removeIncompatibleLicenses: ${e.getMessage()}", true)
        }
    }

    Boolean isCompatibleWithLicenseTemplate(LicenseTemplate licenseTemplate, ProjectTemplate projectTemplate) {
        Boolean isCompatible = false
        try {
            if (licenseTemplate) {
                isCompatible = licenseTemplate.getCompatibleProjectTemplates([projectTemplate])?.size() > 0
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in isCompatibleWithLicenseTemplate", e)
            flashService.setErrorAlert("Error occurred in isCompatibleWithLicenseTemplate: ${e.getMessage()}", true)
        }
        return isCompatible
    }

    void removeLinkedLicenseTemplate(LicenseTemplate licenseTemplate, ProjectTemplate projectTemplate) {
        if (licenseTemplate && projectTemplate) {
            try {
                Boolean okToSave = false

                if (projectTemplate.isPublic) {
                    okToSave = projectTemplate.publicLinkedLicenseTemplateIds?.remove(licenseTemplate.id?.toString())
                }

                if (okToSave) {
                    projectTemplate.merge(flush: true, failOnError: true)
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeLinkedLicenseTemplate", e)
                flashService.setErrorAlert("Error occurred in removeLinkedLicenseTemplate: ${e.getMessage()}", true)
            }
        }
    }

    void addLinkedLicenseTemplate(LicenseTemplate licenseTemplate, ProjectTemplate projectTemplate) {
        if (licenseTemplate && projectTemplate) {
            try {
                Boolean okToSave = false

                if (projectTemplate.isPublic) {
                    if (!projectTemplate.publicLinkedLicenseTemplateIds) {
                        projectTemplate.publicLinkedLicenseTemplateIds = new ArrayList<String>()
                    }
                    if (!projectTemplate.publicLinkedLicenseTemplateIds?.contains(licenseTemplate.id?.toString())) {
                        projectTemplate.publicLinkedLicenseTemplateIds?.add(licenseTemplate.id?.toString())
                        okToSave = true
                    }
                }

                if (okToSave) {
                    projectTemplate.merge(flush: true, failOnError: true)
                }

            } catch (e) {
                loggerUtil.error(log, "Error occurred in addLinkedLicenseTemplate", e)
                flashService.setErrorAlert("Error occurred in addLinkedLicenseTemplate: ${e.getMessage()}", true)
            }
        }
    }

    void removeIncompatibleLicenseTemplates(List<String> indicatorIdsParam, String openIndicatorParam, ProjectTemplate projectTemplate) {
        try {
            Boolean sameIndicatorIds = indicatorIdsParam?.size() == projectTemplate.useIndicatorIds?.size() && projectTemplate.useIndicatorIds?.containsAll(indicatorIdsParam)
            Boolean sameOpenIndicator = projectTemplate.openIndicator == openIndicatorParam
            Boolean runCheck = !(sameIndicatorIds && sameOpenIndicator)
            if (runCheck) {
                List<String> removeIds = []

                if (projectTemplate.isPublic && projectTemplate.publicLinkedLicenseTemplateIds) {
                    projectTemplate.publicLinkedLicenseTemplateIds.each { String licenseTemplateId ->
                        LicenseTemplate licenseTemplate = licenseService.getLicenseTemplateById(licenseTemplateId)
                        if (licenseTemplate && !isCompatibleWithLicenseTemplate(licenseTemplate, projectTemplate)) {
                            removeIds.add(licenseTemplateId)
                            licenseTemplate.removeLinkedProjectTemplate(projectTemplate)
                        }
                    }

                    if (removeIds) {
                        projectTemplate.publicLinkedLicenseTemplateIds.removeAll(removeIds)
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in removeIncompatibleLicenseTemplates", e)
            flashService.setErrorAlert("Error occurred in removeIncompatibleLicenseTemplates: ${e.getMessage()}", true)
        }
    }
}
