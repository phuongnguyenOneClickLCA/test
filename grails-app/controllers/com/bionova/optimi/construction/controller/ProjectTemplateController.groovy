package com.bionova.optimi.construction.controller

import com.bionova.optimi.construction.Constants.SessionAttribute
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.AccountService
import com.bionova.optimi.core.service.ChannelFeatureService
import com.bionova.optimi.core.service.EntityService
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.service.IndicatorService
import com.bionova.optimi.core.service.LicenseService
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.ProjectTemplateService
import com.bionova.optimi.core.service.QuerySectionService
import com.bionova.optimi.core.service.QueryService
import com.bionova.optimi.core.service.QuestionService
import com.bionova.optimi.core.service.UserService
import com.bionova.optimi.core.service.serviceFactory.ScopeFactoryService
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.collections4.CollectionUtils
import org.bson.Document

class ProjectTemplateController {

    OptimiResourceService optimiResourceService
    QueryService queryService
    UserService userService
    LicenseService licenseService
    AccountService accountService
    ProjectTemplateService projectTemplateService
    IndicatorService indicatorService
    EntityService entityService
    FlashService flashService
    QuestionService questionService
    QuerySectionService querySectionService
    ChannelFeatureService channelFeatureService
    def groovyPageRenderer
    ScopeFactoryService scopeFactoryService

    @Secured([Constants.ROLE_AUTHENTICATED])
    def startNewProjectModal() {
        String entityClass = request.JSON?.entityClass ?: Constants.EntityClass.BUILDING.type
        Resource entityClassResource = optimiResourceService.getResourceWithParams(entityClass)
        Query basicQuery = queryService.getBasicQuery()
        User user = userService.getCurrentUser()
        Boolean hasLicense = user?.managedLicenseIds || user?.usableLicenseIds
        List<License> licenses = licenseService.getUsableAndPlanetaryAndTrialLicensesByEntityClass(entityClass, session, user, hasLicense)
        List<String> floatingLicenseIdsNeedCheckIn = licenseService.filterInactivatedFloatingLicenseForUser(licenses, user)?.collect({ it.id.toString() }) ?: []
        List<String> statesWithPlanetary = licenseService.getStatesWithPlanetary()
        Map<String, List<Document>> additionalQuestionResources = questionService.getAdditionalQuestionResourceDocumentsByQuestionIdMap(basicQuery?.allQuestions as Set<Question>, basicQuery?.queryId)
        List<QuerySection> compatibleVirSections = querySectionService.compileCompatibleVirtualSectionsForNewProjectModal(basicQuery, entityClass)
        Map<String, List<Document>> licenseToTemplates = projectTemplateService.getLicenseToTemplatesMap(licenses, user, entityClass)
        Map<String, String> licenseToTemplateDetailsMap = projectTemplateService.getlicenseToTemplateDetailsMap(licenseToTemplates, licenses)

        String toRender = groovyPageRenderer.render(template: "/projectTemplate/quickStartProject", model: [basicQuery                   : basicQuery,
                                                                                                            entityClass                  : entityClass,
                                                                                                            user                         : user,
                                                                                                            modifiable                   : true,
                                                                                                            additionalQuestionResources  : additionalQuestionResources,
                                                                                                            licenseToTemplates           : licenseToTemplates,
                                                                                                            licenseToTemplateDetailsMap  : licenseToTemplateDetailsMap,
                                                                                                            licenses                     : licenses,
                                                                                                            compatibleVirSections        : compatibleVirSections,
                                                                                                            entityClassResource          : entityClassResource,
                                                                                                            statesWithPlanetary          : statesWithPlanetary,
                                                                                                            selectMultiLicenses          : hasLicense,
                                                                                                            floatingLicenseIdsNeedCheckIn: floatingLicenseIdsNeedCheckIn])
        render([output: toRender, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getLicenseAndTemplateByLicenseKey() {
        String entityClass = params.entityClass
        String licenseKey = params.licenseKey
        Map<String, List<Document>> licenseToTemplates = [:]
        String templateDetailsList = ''
        License license = null
        String error = ''

        if (licenseKey && entityClass) {
            license = licenseService.getLicenseByKey(licenseKey.trim())

            if (license) {
                User user = userService.getCurrentUser()

                if (Constants.LicenseType.EDUCATION.toString().equals(license.type)) {
                    if (!license.educationLicenseKeyValid) {
                        error = message(code: "license.joinCode.invalid") + ' '
                        license = null
                    }
                } else if (user && !license.allowedForUser(user)) {
                    error = message(code: "license.joinCode.user_based") + ' '
                    license = null
                }

                if (license && license.compatibleEntityClasses && !license.compatibleEntityClasses.contains(entityClass)) {
                    error += message(code: "licenses.activated_failed.wrongEntityClass")
                    license = null
                }

                if (license?.maxEntitiesReached) {
                    error += message(code: "license.full")
                    license = null
                }

                if (!error) {
                    List<License> licenses = licenseService.getUsableAndPlanetaryAndTrialLicensesByEntityClass(entityClass, session, user, null, [license])
                    licenseToTemplates = projectTemplateService.getLicenseToTemplatesMap(licenses, user, entityClass)
                    templateDetailsList = projectTemplateService.renderTemplateDetailsListAsString(licenseToTemplates, licenses)
                }
            } else {
                error = message(code: "licenses.activated_failed") as String
            }
        } else {
            error = message(code: "licenses.activated_failed") as String
        }

        if (license) {
            Document licenseDoc = projectTemplateService.convertLicenseToDoc(license)
            render([output: 'ok', status: message(code: "license.found", args: [license.name]) as String, licenseToTemplates: licenseToTemplates ?: null, templateDetailsList: templateDetailsList ?: null, license: licenseDoc, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: 'error', error: error, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    // This is a middleware to handle save from quick start modal.
    // The request is redirected back to this middleware every step
    @Secured([Constants.ROLE_AUTHENTICATED])
    def startNewProject() {
        String fwOrigin = session?.getAttribute(SessionAttribute.FORWARD_ORIGIN.attribute)

        Integer numberOfCreatedProjects = session?.getAttribute("numberOfCreatedProjects")
        session?.setAttribute("numberOfCreatedProjects", numberOfCreatedProjects ? ++numberOfCreatedProjects : 1)

        if (!fwOrigin) {
            // first step: request from modal
            // check if there are any licenses that were retrieved by licenseKey and if yes, are they still okay to be added.
            Boolean okToContinue = projectTemplateService.verifyLicensesRetrievedByLicenseKeyInParams(params)

            if (!okToContinue) {
                flashService.setErrorAlert("${message(code: "licenses.activated_failed")}")
                redirect controller: "main", action: "list"
            } else {
                if (params.boolean('newDesign')) {
                    // a template is applied
                    handleFirstStepWithTemplate()
                } else {
                    // finish request here, no template applied.
                    // cannot chain or redirect to "saveInternal" in EntityController as it loses the uploaded file
                    handleFirstStepNoTemplate()
                }
            }
        } else if (fwOrigin?.equalsIgnoreCase(Constants.ForwardOrigin.NEWDESIGN.origin)) {
            handleSecondStepWithTemplate()
        }
    }

    /**
     * Not to be used as an endpoint
     * First step of creating a normal project (without applying a template)
     * Finish request here, no template applied.
     * Cannot chain or redirect to "saveInternal" in EntityController as it loses the uploaded file
     */
    private void handleFirstStepNoTemplate() {
        Query basicQuery = queryService.getBasicQuery()
        Entity entity = entityService.createOrGetProjectEntity(params, session, request, basicQuery)

        if (entity.validate()) {
            boolean isQueryReady = queryService.resolveBasicQueryReadiness(entity)

            if (params.upload) {
                redirect controller: "entity", action: "form", id: entity.id
            } else if (basicQuery && !isQueryReady && !entity?.isPortfolio) {
                flashService.setFadeWarningAlert(g.message(code: 'query.not_ready') as String)
                redirect controller: "entity", action: "form", id: entity.id
            } else {
                redirect controller: "entity", action: "show", params: [entityId: entity.id]
            }
        } else {
            String entityClass = params.entityClass ?: Constants.EntityClass.BUILDING.getType()
            chain controller: "entity", action: "form", model: [entity: entity], params: [entityClass: entityClass]
        }
    }

    /**
     * Not to be used as an endpoint
     * First step of creating a project when a template is applied
     */
    private void handleFirstStepWithTemplate() {
        params.remove('parentEntityId')
        String projectName = ''
        String designName = ''
        List<String> names = params.list('name')
        if (names?.size() > 1) {
            projectName = names[0]
            designName = names[1]
        }
        params.name = projectName // this is for creating project in the following method
        Entity entity = entityService.createOrGetProjectEntity(params, session, request)

        if (entity) {
            if (params.boolean('openCarbonDesigner') || params.openQuery) {
                // guide for forwarding in newDesign()
                session?.setAttribute(SessionAttribute.FORWARD_ORIGIN.attribute, Constants.ForwardOrigin.STARTNEWPROJECT.origin)

                if (params.openIndicator) {
                    session?.setAttribute(SessionAttribute.OPEN_INDICATORID.attribute, params.openIndicator)

                    if (params.boolean('openCarbonDesigner')) {
                        Boolean isCarbonDesignerLicensed = licenseService.getLicensesByIds(params.list("licenseId"), true)?.collect({ it.licensedFeatures })?.flatten()?.unique({ it?.featureId })?.find({ Feature.CARBON_DESIGNER.equals(it?.featureId) }) ? true : false

                        if (isCarbonDesignerLicensed) {
                            session?.setAttribute(SessionAttribute.OPEN_CD.attribute, true)
                        } else {
                            // CD not licensed => remove the forwarding guide, let newDesign() handle as usual
                            session?.removeAttribute(SessionAttribute.FORWARD_ORIGIN.attribute)
                            session?.removeAttribute(SessionAttribute.OPEN_INDICATORID.attribute)
                            params.remove('openCarbonDesigner')
                        }
                    } else if (params.openQuery) {
                        session?.setAttribute(SessionAttribute.OPEN_QUERY.attribute, params.openQuery)
                        session?.setAttribute(SessionAttribute.EXPAND_INPUTS.attribute, params.get(Constants.PARAM_EXPAND_ALL_SECTIONS_IN_QUERY))
                    }
                }
            }

            List<String> indicatorIds = params.list("indicatorIdList")

            if (!params.fromEntityId && params.selectedTemplateId) {
                // check if a deleted / unauthorized entity template is set in the project template >> set warning
                // need to run a check because the quick start project modal won't send the fromEntityId params if entity template is deleted or user isn't authorized to access
                projectTemplateService.checkEntityTemplateValidity(params)
            } else if (params.fromEntityId && indicatorIds) {
                // set instruction for 2nd step to copy datasets from a design template
                // guide for forwarding in newDesign()
                session?.setAttribute(SessionAttribute.FORWARD_ORIGIN.attribute, Constants.ForwardOrigin.STARTNEWPROJECT.origin)
                session?.setAttribute(SessionAttribute.COPY_DATASETS_FROM_TEMPLATE.attribute, params.fromEntityId)
                session?.setAttribute(SessionAttribute.ENABLED_INDICATORIDS.attribute, indicatorIds)
                params.remove('fromEntityId')
            }

            // proceed to add indicators and create design
            redirect controller: "entity", action: "saveIndicators", params: [indicatorIds  : indicatorIds, enabledIndicatorIdsForDesign: indicatorIds,
                                                                              entityId      : entity?.id?.toString(), name: designName,
                                                                              comment       : params.comment, ribaStage: params.int("ribaStage"),
                                                                              scopeId       : params.scopeId, frameType: params.frameType, projectType: params.projectType,
                                                                              lcaCheckerList: params.list("lcaCheckerList"), lcaParamHandling: params.applyLcaDefaults,
                                                                              newDesign     : params.newDesign, indicatorUse: 'design']
        } else {
            flashService.setErrorAlert(message(code: 'cannot.create.project') as String)
            redirect controller: "main", action: "list"
        }
    }

    /**
     * Not to be used as an endpoint
     * Second step of creating a project when a template is applied
     * We handle copying datasets, opening CD / query
     */
    private void handleSecondStepWithTemplate() {
        session?.removeAttribute(SessionAttribute.FORWARD_ORIGIN.attribute)
        // 2nd step: design created, open query / carbon designer also handle copy datasets
        if (session?.getAttribute(SessionAttribute.COPY_DATASETS_FROM_TEMPLATE.attribute) && session?.getAttribute(SessionAttribute.ENABLED_INDICATORIDS.attribute)) {
            handleApplyEntityTemplate()
        }

        if (session?.getAttribute(SessionAttribute.OPEN_INDICATORID.attribute)) {
            params.put('indicatorId', session?.getAttribute(SessionAttribute.OPEN_INDICATORID.attribute)?.toString())
            session?.removeAttribute(SessionAttribute.OPEN_INDICATORID.attribute)

            if (session?.getAttribute(SessionAttribute.OPEN_CD.attribute)) {
                session?.removeAttribute(SessionAttribute.OPEN_CD.attribute)
                redirect controller: "design", action: "simulationTool", params: params
            } else if (session?.getAttribute(SessionAttribute.OPEN_QUERY.attribute)) {
                params.put('queryId', session?.getAttribute(SessionAttribute.OPEN_QUERY.attribute)?.toString())
                params.put('childEntityId', params.designId)
                params.put(Constants.PARAM_EXPAND_ALL_SECTIONS_IN_QUERY, session?.getAttribute(SessionAttribute.EXPAND_INPUTS.attribute)?.toString())
                session?.removeAttribute(SessionAttribute.OPEN_QUERY.attribute)
                session?.removeAttribute(SessionAttribute.EXPAND_INPUTS.attribute)
                redirect controller: "query", action: "form", params: params
            } else {
                redirect controller: "entity", action: "show", params: params
            }
        } else {
            redirect controller: "entity", action: "show", params: params
        }
    }

    /**
     * Not to be used as an endpoint
     * Second step of creating a project when a template is applied
     * Handles copying datasets from a design template
     */
    private void handleApplyEntityTemplate() {
        // copy datasets from a design template to the newly created design
        if (params.designId) {
            String newlyCreatedDesignId = params.designId
            String newlyCreatedProjectId = params.entityId
            String fromEntityId = session?.getAttribute(SessionAttribute.COPY_DATASETS_FROM_TEMPLATE.attribute)
            List<String> indicatorIds = session?.getAttribute(SessionAttribute.ENABLED_INDICATORIDS.attribute) as List<String>
            session?.removeAttribute(SessionAttribute.COPY_DATASETS_FROM_TEMPLATE.attribute)
            session?.removeAttribute(SessionAttribute.ENABLED_INDICATORIDS.attribute)

            // run direct query to get the object from db without any validation
            Entity entityTemplate = Entity.findById(DomainObjectUtil.stringToObjectId(fromEntityId))
            boolean okToCopy = projectTemplateService.isEntityTemplateOkToCopy(entityTemplate)

            if (okToCopy) {
                Entity targetChild = entityService.getEntityById(newlyCreatedDesignId)
                Entity parent = entityService.getEntityById(newlyCreatedProjectId)
                projectTemplateService.handleCopyDatasetsFromEntityTemplate(targetChild, newlyCreatedDesignId, entityTemplate, fromEntityId, parent, indicatorIds)
            }
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def saveProjectTemplate() {
        String accountId = params.accountId
        Boolean edit = params.boolean('edit')
        Boolean isPublic = params.boolean('isPublic')
        Boolean fetchLicenseTable = params.boolean('fetchLicenseTable') || !isPublic
        // always fetch when it's not public
        boolean saveOk = false

        ProjectTemplate template = projectTemplateService.saveTemplate(params, edit)
        if (template) {
            if (edit) {
                // edit ok
                saveOk = true
            } else {
                if (isPublic) {
                    // save ok
                    saveOk = true
                } else {
                    // add new template to account
                    saveOk = accountService.addProjectTemplateIdToAccount(accountId, template.id.toString())
                }
            }
        }

        if (saveOk) {
            User user = userService.getCurrentUser()
            Account account = accountService.getAccount(accountId)
            String licenseToTemplateTable = fetchLicenseTable ? projectTemplateService.renderProjectTemplateToLicenseTableAsString(isPublic, accountId, user, account) : ''
            Boolean isMainUser = account?.mainUserIds?.contains(user?.id?.toString()) || userService.getSuperUser(user)
            Question productTypeQuestion = questionService.getProductTypeQuestion()
            List<Resource> entityClassResources = projectTemplateService.getEntityClassesForTemplate(session)
            String templateRow = groovyPageRenderer.render(template: "/projectTemplate/projectTemplateRow", model: [template: template, isPublic: isPublic, accountId: accountId, isMainUser: isMainUser, productTypeQuestion: productTypeQuestion, entityClassResources: entityClassResources])

            render([output: templateRow, secondaryOutput: licenseToTemplateTable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def removeProjectTemplate() {
        String accountId = params.accountId
        String templateId = params.templateId
        Boolean isPublic = params.boolean('isPublic')
        Boolean fetchLicenseTable = params.boolean('fetchLicenseTable') || !isPublic
        // always fetch when it's not public
        boolean removeOk = false

        if (templateId) {
            ProjectTemplate template = projectTemplateService.removeTemplate(templateId)
            if (template) {
                if (isPublic) {
                    removeOk = true
                } else {
                    removeOk = accountService.removeProjectTemplateIdFromAccount(accountId, template.id.toString())
                }
            }
        }

        if (removeOk) {
            String licenseToTemplateTable = fetchLicenseTable ? projectTemplateService.renderProjectTemplateToLicenseTableAsString(isPublic, accountId) : ''
            render([output: 'ok', secondaryOutput: licenseToTemplateTable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def linkProjectTemplatesToLicense() {
        String licenseId = params.licenseId
        Boolean isPublic = params.boolean('isPublic')
        String accountId = params.accountId
        String redirectTo = params.redirectTo
        String applyDefaultTemplate = params.applyDefaultTemplate
        List<String> applyTemplates = params.list('applyTemplate') ?: []

        boolean ok = false

        if (isPublic) {
            ok = projectTemplateService.linkPublicTemplateToLicense(licenseId, applyTemplates, applyDefaultTemplate)
        } else {
            ok = projectTemplateService.linkTemplatesToLicense(licenseId, accountId, applyTemplates, applyDefaultTemplate)
        }

        if (redirectTo == 'license.form') {
            redirect controller: 'license', action: 'form', params: [id: licenseId]
        } else {
            if (ok) {
                render([output: 'ok', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def editProjectTemplateModal() {
        String templateId = params.templateId
        String accountId = params.accountId
        Boolean isPublic = params.boolean('isPublic')

        if (templateId) {
            ProjectTemplate template = projectTemplateService.getTemplateById(templateId)

            if (template) {

                List<License> licenses = []
                if (isPublic) {
                    licenses = licenseService.getLicenses()?.findAll({ License l -> !l.expired }) // active licenses
                } else if (accountId) {
                     licenses = accountService.getLicenses(accountService.getAccount(accountId)?.getLicenseIds())
                }
                Map model = [template                      : template,
                             appendRowTo                   : templateId,
                             accountId                     : accountId,
                             isPublic                      : isPublic,
                             createNew                     : false,
                             edit                          : true,
                             modalContainerId              : Constants.EDIT_TEMPLATE_OPTION_CONTAINER_ID,
                             licenses                      : licenses]

                projectTemplateService.populateModelForTemplateModal(model, session, licenses, template?.useIndicatorIds, template?.compatibleEntityClass)
                render([output: groovyPageRenderer.render(template: "/projectTemplate/projectTemplateModal", model: model), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getProjectTemplateExtendedOptions() {
        String indicatorId = params.openIndicator
        List<String> licenseIds = params.list('licenseIds')

        if (indicatorId) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId)
            List<License> licenses = licenseIds ? licenseService.getLicensesByIds(licenseIds, true) : []
            List<Query> designQueries = queryService.getQueriesByIndicator(indicator, licenses?.collect({ it.licensedFeatures }) as List<Feature>, true)
            if (indicator) {
                String extendedOptions = groovyPageRenderer.render(template: "/projectTemplate/projectTemplateExtendedOptions", model: [indicatorFromFetch    : indicator,
                                                                                                                                        designQueriesFromFetch: designQueries,
                                                                                                                                        licenses              : licenses])
                render([output: extendedOptions, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        }
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getProjectTemplateDetails() {
        String formId = params.formId
        String entityType = params.entityType
        String templateId = params.templateId
        List<String> licenseIds = params.list('licenseIds')
        List<String> useIndicatorIds = params.list('useIndicatorIds')
        boolean ok = false
        Map outputMap = [:]

        if (templateId) {
            ProjectTemplate template = projectTemplateService.getTemplateById(templateId)

            if (template) {
                List<License> licenses = licenseIds ? licenseService.getLicensesByIds(licenseIds, true) : []
                Boolean indicatorCompatible = false
                Map<String, Map<String, List<Document>>> copyEntitiesMapped = [:]
                List<String> indicatorIds = template?.useIndicatorIds
                Query basicQuery = queryService.getBasicQuery()
                if (indicatorIds?.size() > 0 && template?.childEntityId && template?.compatibleEntityClass) {
                    Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorIds[0])
                    Set<String>calculationClassificationList = basicQuery?.calculationClassificationList?.keySet()
                    indicatorCompatible = CollectionUtils.containsAny(basicQuery?.applicableIndicatorForCalculationClass, indicatorIds) ?: false
                    copyEntitiesMapped = entityService.getChildrenByEntityClassAndQueryReadyWithCustomClass(template?.compatibleEntityClass, indicator?.indicatorUse, null, indicatorIds, null, calculationClassificationList)
                }
                String options = groovyPageRenderer.render(template: "/projectTemplate/projectTemplateOptions", model: [template           : template,
                                                                                                                        templateModalForm  : Constants.QSPM_OPTIONS_TAB_ID,
                                                                                                                        copyEntitiesMapped : copyEntitiesMapped,
                                                                                                                        basicQuery         : basicQuery,
                                                                                                                        indicatorCompatible: indicatorCompatible,
                                                                                                                        onNewProjectModal  : true,
                                                                                                                        licenses           : licenses])
                outputMap.put('templateOptions', options)

                Map<Integer, String> ribaStages = Constants.DesignRIBAStages.map()
                ribaStages.put(-1, "entity.form.component")
                String entityClass = template?.compatibleEntityClass
                Boolean showRibaStage = Constants.EntityClass.BUILDING.type.equalsIgnoreCase(entityClass) || Constants.EntityClass.INFRASTRUCTURE.type.equalsIgnoreCase(entityClass)
                Boolean isBuilding = Constants.EntityClass.BUILDING.type.equalsIgnoreCase(entityClass)
                List<Indicator> selectedDesignIndicators = indicatorService.getIndicatorsByIndicatorIdsAndEntityTypeAndClass(useIndicatorIds, entityType, entityClass)
                boolean showCalculationClassification = basicQuery?.applicableIndicatorForCalculationClass?.intersect(selectedDesignIndicators?.collect({ it.indicatorId })) && basicQuery?.calculationClassificationList
                Map model = [ribaStages              : ribaStages, basicQuery: basicQuery, isBuilding: isBuilding, showRibaStage: showRibaStage, onlyModalBody: true,
                             selectedDesignIndicators: selectedDesignIndicators, formId: formId, showCalculationClassification: showCalculationClassification]
                model = scopeFactoryService.getServiceImpl(entityClass).addValueScopeToModel(model, selectedDesignIndicators)
                outputMap.put('designModal', groovyPageRenderer.render(template: "/design/designModal", model: model))
                ok = true
            }
        }

        if (ok) {
            if (flash) {
                outputMap.put(flashService.FLASH_OBJ_FOR_AJAX, flash)
            }
            render(outputMap as JSON)
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    @Secured([Constants.ROLE_DEVELOPER])
    def managePublicTemplate() {
        List<ProjectTemplate> publicTemplates = projectTemplateService.getAllPublicTemplates()
        List<License> activeLicenses = licenseService.getLicenses()?.findAll({ License l -> !l.expired })

        Map model = [publicTemplates: publicTemplates,
                     licenses       : activeLicenses, user: userService.getCurrentUser()]
        projectTemplateService.populateModelForTemplateModal(model, session, activeLicenses)

        model
    }

    @Secured([Constants.ROLE_AUTHENTICATED])
    def getEntityTemplateSelect() {
        String templateId = params.templateId
        String entityClass = params.entityClass
        List<String> licenseIds = params.list('licenseIds')
        List<String> indicatorIds = params.list('indicatorId') ?: []
        if (templateId && indicatorIds?.size() > 0 && entityClass) {
            Map model = [templateId : templateId]
            boolean allowCopyingPublicEntities = licenseService.isFeatureLicensed(Feature.ALLOW_IMPORT_PUBLIC_ENTITIES, licenseIds)
            projectTemplateService.populateModelForEntityTemplateSelect(model, indicatorIds, entityClass, allowCopyingPublicEntities)
            render([output: groovyPageRenderer.render(template: "/projectTemplate/entityTemplateSelect", model: model), (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }

    @Secured([Constants.ROLE_SUPER_USER])
    def publicTemplateToLicenseTable() {
        String licenseToTemplateTable = projectTemplateService.renderProjectTemplateToLicenseTableAsString(true)
        if (licenseToTemplateTable) {
            render([output: licenseToTemplateTable, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        } else {
            render([output: 'error', (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }
    }
}
