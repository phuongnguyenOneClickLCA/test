/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.CalculationRule
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Configuration
import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.CostStructure
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.ProductDataList
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceCompositePart
import com.bionova.optimi.core.domain.mongo.ResourceFilterCriteria
import com.bionova.optimi.core.domain.mongo.ResourceFilterRule
import com.bionova.optimi.core.domain.mongo.ResourceFilterRuleVerifyValue
import com.bionova.optimi.core.domain.mongo.ResourcePurpose
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.core.util.SpringUtil
import com.bionova.optimi.data.ResourceCache
import com.bionova.optimi.data.ResourceTypeCache
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.client.MongoCursor
import com.mongodb.client.result.UpdateResult
import com.monitorjbl.xlsx.StreamingReader
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.mapping.LinkGenerator
import groovy.transform.CompileStatic
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.StringUtils
import org.apache.poi.hssf.usermodel.HSSFFont
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
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.HtmlUtils

import javax.servlet.http.HttpSession
import java.lang.reflect.Method
import java.text.Normalizer

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 *
 * Named as OptimiResourceService, because Grails has its own ResourceService
 */
class OptimiResourceService {

    def optimiExcelImportService
    def loggerUtil
    def userService
    def applicationService
    def resourceTypeService
    def unitConversionUtil
    def queryService
    def domainClassService
    def configurationService
    def messageSource
    def productDataListService
    def resourceFilterCriteriaUtil
    def accountService
    def importMapperService
    def flashService
    def licenseService
    def stringUtilsService
    def nmdResourceService
    def localizationService
    def entityService
    def datasetService
    LinkGenerator grailsLinkGenerator
    ResourceService resourceService

    private static final Integer BATCH_SIZE = 500

    //TODO: not a thread safe cache
    private static Map<String, Resource> resourceCache = new HashMap<String, Resource>()
    private static Map<String, List<Resource>> resourceProfilesCache = new HashMap<String, List<Resource>>()

    def Resource mapUserAnswersToResource(Indicator indicator, Entity entity, HttpSession session) {
        Resource existingResource
        Resource newResource = new Resource()
        Resource componentResource
        Set<Dataset> foundDatasets = entity?.datasets?.findAll({
            it.mapToResource && it.mapToResourceType && it.answerIds
        })

        if (foundDatasets) {
            indicator?.getResolveCalculationRules(entity?.parentEntityId ? entity.parentById : entity)?.findAll({ it.mapToResource })?.each { CalculationRule calculationRule ->
                String errorInMapping

                foundDatasets.each { Dataset d ->
                    Dataset copy = datasetService.createCopy(d)
                    String methodName = copy.questionId
                    methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
                    Class aClass

                    try {
                        Method method = Resource.class.getMethod(methodName)
                        aClass = method.getReturnType()
                    } catch (Exception e) {
                    }

                    if (aClass && aClass.isAssignableFrom(List.class)) {
                        DomainObjectUtil.callSetterByAttributeName(copy.questionId, newResource, copy.answerIds)
                    } else {
                        def value

                        try {
                            value = copy.answerIds.get(0)

                            if (value && value instanceof String) {
                                if ("boolean".equalsIgnoreCase(copy.mapToResourceType)) {
                                    value = value.toBoolean()
                                } else if ("double".equalsIgnoreCase(copy.mapToResourceType)) {
                                    value = value.toDouble()
                                }
                                DomainObjectUtil.callSetterByAttributeName(copy.questionId, newResource, value)
                            }
                        } catch (Exception e) {
                            errorInMapping = e.toString()
                            loggerUtil.error(log, "Error in mapping to resource with value ${value ?: ''} as type ${d.mapToResourceType}", e)
                            flashService.setErrorAlert("Error in mapping to resource with value ${value ?: ''} as type ${d.mapToResourceType}: ${e.getMessage()}", true)
                        }
                    }
                }

                if (!errorInMapping) {
                    if (newResource.active == null) {
                        newResource.active = Boolean.TRUE
                    }
                    Double calculationRuleValue = entity.getTotalResult(indicator.indicatorId, calculationRule.calculationRuleId)
                    log.info("Setting calculationRule ${calculationRule.calculationRule} as resource attribute with value: ${calculationRuleValue}")
                    DomainObjectUtil.callSetterByAttributeName(calculationRule.calculationRule, newResource, calculationRuleValue)

                    if (newResource.resourceId) {
                        if (newResource.profileId) {
                            existingResource = Resource.findByResourceIdAndProfileId(newResource.resourceId, newResource.profileId)
                        } else {
                            existingResource = Resource.findByResourceId(newResource.resourceId)
                        }
                    }

                    if (existingResource) {
                        List<String> persistentProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class)

                        newResource.properties.each { String propertyName, Object propertyValue ->
                            if (persistentProperties?.contains(propertyName) && propertyValue != null) {
                                DomainObjectUtil.callSetterByAttributeName(propertyName, existingResource, propertyValue)
                            }
                        }
                    }
                } else {
                    session?.setAttribute("componentSaving.error", true)
                }
            }
        }
        componentResource = existingResource ? existingResource : newResource

        if (componentResource.resourceId && !session?.getAttribute("componentSaving.error")) {
            if (componentResource.id) {
                session?.setAttribute("componentSaving.ok", ["resource.updated", "resourceId ${componentResource.resourceId}, profileId: ${componentResource.profileId}"])
            } else {
                session?.setAttribute("componentSaving.ok", ["resource.saved", "resourceId ${componentResource.resourceId}, profileId: ${componentResource.profileId}"])
            }
            List<Resource> resourceProfiles = getResourceProfilesByResourceId(componentResource.resourceId, null, Boolean.TRUE)

            if (resourceProfiles) {
                if (resourceProfiles.size() >= 1 && !existingResource) {
                    componentResource.multipleProfiles = Boolean.TRUE

                    resourceProfiles.each {
                        it.multipleProfiles = Boolean.TRUE
                    }
                } else if (resourceProfiles.size() > 1 && existingResource) {
                    componentResource.multipleProfiles = Boolean.TRUE

                    resourceProfiles.each {
                        it.multipleProfiles = Boolean.TRUE
                    }
                } else if (resourceProfiles.size() == 1 && existingResource) {
                    componentResource.multipleProfiles = Boolean.FALSE
                } else {
                    componentResource.multipleProfiles = Boolean.FALSE
                    Resource singleProfile = resourceProfiles.get(0)
                    singleProfile.multipleProfiles = Boolean.FALSE
                }
            }
        }
        return componentResource
    }

    def String checkResourceSubTypeStandardUnits(List<String> resourceSubTypes) {
        String error
        if (resourceSubTypes && !resourceSubTypes.isEmpty()) {
            List<String> standardUnits = []
            try {
                resourceSubTypes.each {
                    ResourceType subType = resourceTypeService.getResourceTypeObjectBySubType(it)
                    standardUnits.add(subType.standardUnit.toUpperCase())
                }

                if (standardUnits.unique().size() > 1) {
                    error = "${resourceSubTypes} Standard unit mismatch: ${standardUnits}"
                }
            } catch (Exception e) {
                error = "FilterRule check for subTypes failed: ${e}"
            }
        }
        return error
    }

    def Map runFilterRule(List<Resource> resources, String applicationId, String ruleId, Boolean showOnlyNumbers) {
        Map returnable = [:]

        if (applicationId && ruleId && resources) {
            ResourceFilterRule resourceFilterRule = applicationService.getApplicationByApplicationId(applicationId)?.
                    filterRules?.find({ ruleId.equals(it.ruleId) })

            if (resourceFilterRule) {
                List<Resource> resourcesToCheck
                if (resourceFilterRule.checkConstructionsAlso && resourceFilterRule.checkPrivateDataAlso) {
                    resourcesToCheck = new ArrayList<Resource>(resources)
                } else if (resourceFilterRule.checkConstructionsAlso) {
                    resourcesToCheck = new ArrayList<Resource>(resources.findAll({!it.privateDataset}))
                } else if (resourceFilterRule.checkPrivateDataAlso) {
                    resourcesToCheck = new ArrayList<Resource>(resources.findAll({!it.construction}))
                } else {
                    resourcesToCheck = new ArrayList<Resource>(resources.findAll({!it.construction && !it.privateDataset}))
                }
                returnable.put("ruleId", resourceFilterRule.ruleId)
                returnable.put("ruleName", resourceFilterRule.localizedName)
                returnable.put("showOnlyNumbers", showOnlyNumbers)
                List<Resource> filteredResources
                List<Resource> originalResources = new ArrayList<Resource>(resourcesToCheck)
                String errorFromCheck

                if (resourceFilterRule.resourceFilterCriteria) {
                    if (resourceFilterRule.resourceFilterCriteria.get("resourceSubType")) {
                        errorFromCheck = checkResourceSubTypeStandardUnits(resourceFilterRule.resourceFilterCriteria.get("resourceSubType"))

                        if (!errorFromCheck) {
                            resourcesToCheck = runResourceFilterCriteria(resourcesToCheck, resourceFilterRule.resourceFilterCriteria)

                            if (resourcesToCheck && !resourcesToCheck.isEmpty()) {
                                filteredResources = new ArrayList<Resource>(resourcesToCheck)
                            }
                        }
                    } else {
                        resourcesToCheck = runResourceFilterCriteria(resourcesToCheck, resourceFilterRule.resourceFilterCriteria)

                        if (resourcesToCheck && !resourcesToCheck.isEmpty()) {
                            filteredResources = new ArrayList<Resource>(resourcesToCheck)
                        }
                    }
                } else if (resourceFilterRule.advancedResourceFilterCriteria) {
                    resourcesToCheck = runAdvancedResourceFilterCriteria(resourcesToCheck, resourceFilterRule.advancedResourceFilterCriteria)

                    if (resourcesToCheck && !resourcesToCheck.isEmpty()) {
                        filteredResources = new ArrayList<Resource>(resourcesToCheck)
                    }
                } else {
                    filteredResources = new ArrayList<Resource>(resourcesToCheck)
                }

                if (filteredResources && !filteredResources.isEmpty()) {
                    returnable.put("filteredResources", new ArrayList<Resource>(filteredResources))

                    for (Resource r in filteredResources) {
                        r.missingRequiredFilterFields = null
                        r.failingMaxAgeFilterValues = null
                        r.missingRequiredFilterValues = null
                        r.failingVerifyValues = null
                        r.failingActiveResourceIds = null
                        r.illegalFilterValues = null
                        r.failingRequireIdenticalValues = null
                    }
                    List<Resource> resourcesByRule = getResourcesByFilterRule(applicationId, ruleId, filteredResources)
                    returnable.put("checkedResource", "Checked ${filteredResources ? filteredResources.size() : '0'} resources with filter ${resourceFilterRule.localizedName}")

                    if (filteredResources && resourcesByRule && !resourcesByRule.isEmpty()) {
                        filteredResources.removeAll(resourcesByRule)
                    }

                    if (resourceFilterRule.nonShallPass) {
                        returnable.put("nonShallPass", true)
                        returnable.put("passedResources", filteredResources)
                        returnable.put("notPassedResources", resourcesByRule)
                    } else {
                        returnable.put("nonShallPass", false)
                        returnable.put("passedResources", resourcesByRule)
                        returnable.put("notPassedResources", filteredResources)
                    }
                } else {
                    if (errorFromCheck) {
                        returnable.put("notPassedCriteria", "${errorFromCheck}")
                    } else {
                        returnable.put("notPassedCriteria", "Checked ${originalResources ? originalResources.size() : '0'} resources with rule's ${resourceFilterRule.ruleId} criteria ${resourceFilterRule.resourceFilterCriteria} and none passed that. Could not test filterRules.")
                    }
                }
            } else {
                returnable.put("error", "Error while handling filter rules: No filter rule found with: ApplicationId: ${applicationId}, RuleId: ${ruleId}")
            }
        }
        return returnable
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntityNonUsedInactiveResources() {
        List<Resource> nonUsedInactiveResources = new ArrayList<Resource>()

        getOnlyInactiveResources()?.each { Resource resource ->
            Integer results = Entity.collection.count(["datasets.resourceId": resource.resourceId, "datasets.profileId": resource.profileId])?.intValue()

            if (!results) {
                nonUsedInactiveResources.add(resource)
            }
        }
        return nonUsedInactiveResources
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getEntityNonUsedInactiveResourcesForDBCleanup() {
        List<Document> inactiveResources = Resource.collection.find([active: [$ne: true]], [resourceId: 1, profileId: 1, nameEN: 1])?.toList()
        List<Document> nonUsedInactiveResources = []

        inactiveResources?.each { Document resource ->
            Document result = Entity.collection.findOne(["datasets.resourceId": resource.resourceId, "datasets.profileId": resource.profileId], [_id: 1])

            if (!result) {
                nonUsedInactiveResources.add(resource)
            }
        }
        return nonUsedInactiveResources
    }


    def getEntityNonUsedActiveResources() {
        List<Resource> nonUsedActiveResources = new ArrayList<Resource>()

        getAllResources()?.each { Resource resource ->
            Integer results = Entity.collection.count(["datasets.resourceId": resource.resourceId, "datasets.profileId": resource.profileId])?.intValue()

            if (!results) {
                nonUsedActiveResources.add(resource)
            }
        }
        return nonUsedActiveResources
    }

    def runAdvancedResourceFilterCriteria(List<Resource> appResources, List<ResourceFilterCriteria> resourceFilterCriteria) {
        List<Resource> filteredResources = []

        if (appResources && resourceFilterCriteria) {
            for (Resource r in appResources) {
                boolean valid = filterResourceByResourceFilterCriteriaObjects(r,resourceFilterCriteria)

                if (valid) {
                    filteredResources.add(r)
                }
            }
        } else if (appResources) {
            filteredResources = new ArrayList<Resource>(appResources)
        }
        return filteredResources
    }

    /**
     * Runs advanced ResourceFilterCriteria for Resource Documents
     * @param resourceDocumentList
     * @param resourceFilterCriteriaList
     * @return
     */
    def runAdvancedResourceFilterCriteriaForResourceDocuments(List<Document> resourceDocumentList, List<ResourceFilterCriteria> resourceFilterCriteriaList) {
        List<Document> filteredResources = []

        if (!resourceDocumentList) {
            return filteredResources
        }

        if (resourceFilterCriteriaList) {
            for (Document resourceDocument in resourceDocumentList) {
                boolean valid = filterResourceDocumentsByResourceFilterCriteriaObjects(resourceDocument,resourceFilterCriteriaList)

                if (valid) {
                    filteredResources.add(resourceDocument)
                }
            }
        } else {
            filteredResources = new ArrayList<Document>(resourceDocumentList)
        }
        return filteredResources
    }

    /**
     * Filter resource document against list of resourceFilterCriteria object.
     * @param resourceDocument {@link Document}
     * @param resourceFilterCriteriaList {@link ResourceFilterCriteria}
     */
    boolean filterResourceDocumentsByResourceFilterCriteriaObjects (Document resourceDocument, List<ResourceFilterCriteria> resourceFilterCriteriaList) {
        boolean valid = true

        if (!resourceFilterCriteriaList || !resourceDocument) {
            return valid
        }

        //All criteria that do not have applyCondition are used for resource
        List<ResourceFilterCriteria> criteriaForResource = resourceFilterCriteriaList?.findAll { !it.applyCondition }

        if (!criteriaForResource) {
            return valid
        }

        for (ResourceFilterCriteria criteria in criteriaForResource) {
            String attribute = criteria.attribute
            String resolvingMethod = criteria.resolve ?: "all"
            // if expired or exists equals resolvingMethod then check if resource is valid
            if (Constants.FilteringMethod.EXPIRED.getMethod().equals(resolvingMethod)) {
                if (resourceDocument.isExpiredDataPoint) {
                    valid = false
                    break
                }
            } else if (Constants.FilteringMethod.EXISTS.getMethod().equals(resolvingMethod)) {
                if (attribute) {
                    def resourceValue = getDocumentPropertyByName(attribute, resourceDocument)

                    if (resourceValue == null) {
                        valid = false
                        break
                    }
                }
            } else if (Constants.FilteringMethod.PRESENT.getMethod().equals(resolvingMethod)) {
                String resourceValue = getDocumentPropertyByName(attribute, resourceDocument)
                if (!resourceValue || resourceValue.equals("null")) {
                    valid = false
                }
            } else {
                List<Object> values = criteria.values
                if (attribute && values != null) {
                    def resourceValue = getDocumentPropertyByName(attribute, resourceDocument)
                    List resourceValueList = resourceValue instanceof List ? resourceValue : [resourceValue]
                    valid = isValuePassedFilterListWithResolveMethod(resolvingMethod,values,resourceValueList)

                    if (!valid) {
                        break
                    }
                }
            }
        }

        return valid
    }

    /**
     * Provides value of Document's property by propertyName
     * @param propertyName
     * @param documentObject
     * @return
     */
    @CompileStatic
    private Object getDocumentPropertyByName(String propertyName, Document documentObject) {

        def value = null

        if (!propertyName || !documentObject) {
            return value
        }

        propertyName = propertyName.trim()

        try {
            if (documentObject.containsKey(propertyName)) {
                value = documentObject.get(propertyName)
            }
        } catch (Exception e) {
            // SW-1901 can remove the logs and flash right before release 0.5.0
            LoggerUtil loggerUtil = SpringUtil.getBean("loggerUtil")
            loggerUtil.warn(log, "Exception occurred in getDocumentPropertyByName", e)
            FlashService flashService = SpringUtil.getBean("flashService")
            flashService.setErrorAlert("Error in getDocumentPropertyByName, check log. ${e.getMessage()}", true)
        }

        return value
    }

/**
     * Filter resource AND dataset against list of resourceFilterCriteria object.
     * @param resource {@link Resource}
     * @param resourceFilterCriteria {@link ResourceFilterCriteria}
     * @param dataset {@link Dataset}
     * @param indicatorAddQIds: used only for dataset & when applyCondition == presentInDataset
     */
    boolean filterResourceByResourceFilterCriteriaObjects (Resource resource, List<ResourceFilterCriteria> resourceFilterCriteria, Dataset dataset = null, Set<String> indicatorAddQIds = null) {
        boolean valid = true
        if(resourceFilterCriteria) {
            //All criteria that do not have applyCondition are used for resource, those that have are used for datasets
            List<ResourceFilterCriteria> criteriaForResource = resourceFilterCriteria?.findAll({!it.applyCondition})
            List<ResourceFilterCriteria> criteriaForDataset = criteriaForResource ? resourceFilterCriteria - criteriaForResource : resourceFilterCriteria


            if (resource && criteriaForResource) {
                for (ResourceFilterCriteria criteria in criteriaForResource) {
                    String attribute = criteria.attribute
                    String resolvingMethod = criteria.resolve ?: "all"
                    // if expired or exists equals resolvingMethod then check if resource is valid
                    if (Constants.FilteringMethod.EXPIRED.getMethod().equals(resolvingMethod)) {
                        if (resource.isExpiredDataPoint) {
                            valid = false
                            break
                        }
                    } else if (Constants.FilteringMethod.EXISTS.getMethod().equals(resolvingMethod)) {
                        if (attribute) {
                            def resourceValue = DomainObjectUtil.callGetterByAttributeName(attribute, resource)

                            if (resourceValue == null) {
                                valid = false
                                break
                            }
                        }
                    } else if (Constants.FilteringMethod.PRESENT.getMethod().equals(resolvingMethod)) {
                        String resourceValue = DomainObjectUtil.callGetterByAttributeName(attribute, resource)
                        if (!resourceValue || resourceValue.equals("null")) {
                            valid = false
                        }
                    } else {
                        List<Object> values = criteria.values
                        if (attribute && values != null) {
                            def resourceValue = DomainObjectUtil.callGetterByAttributeName(attribute, resource)
                            if (resourceValue instanceof List) {
                                valid = isValuePassedFilterListWithResolveMethod(resolvingMethod,values,resourceValue)

                                if(!valid) {
                                    break
                                }
                            } else {
                                valid = isValuePassedFilterListWithResolveMethod(resolvingMethod,values,[resourceValue])

                                if(!valid) {
                                    break
                                }
                            }
                        }

                    }

                }
            }
            //if the check from resource passed & dataset available then check if any of the value exist in dataset additionalQuestion, when applyCondition == presentInDataset && indicator has matching addQ Ids
            if (dataset && criteriaForDataset && valid) {

                for (ResourceFilterCriteria criteria in criteriaForDataset) {
                    String attribute = criteria.attribute
                    String applyCondition = criteria.applyCondition ?: ""
                    Boolean applyCheckCondition = dataset?.additionalQuestionAnswers?.containsKey(attribute) && indicatorAddQIds?.contains(attribute)
                    // checkOnlyFromDataset check is actually not needed now but place here in case in future we want to have other values for applyCondition
                    Boolean checkOnlyFromDataset = applyCondition.equalsIgnoreCase(Constants.PRESENT_IN_DATASET)
                    if (applyCondition != null && applyCheckCondition && checkOnlyFromDataset) {
                        List<Object> values = criteria.values
                        valid = isValuePassedFilterListWithResolveMethod(criteria.resolve,values,[dataset.additionalQuestionAnswers.get(attribute)])
                        if (!valid) {
                            break
                        }
                    }  else if (!dataset?.additionalQuestionAnswers?.containsKey(attribute) && checkOnlyFromDataset) {
                        valid = false
                        break
                    }
                }

            }
        }

        return valid
    }
    /**
     * Checking two list based on resolving method string passed in the function.
     * @param resolvingMethod {String }: comparing method from configuration
              // if resolvingMethod == all: check if all values exist in resource
             // if resolvingMethod == any: check if any of the values exist in resource
             // if resolvingMethod == none: check if none of the values exist in resource
     * @param resourceValues ${List<Object>} values of the resource/dataset
     * @param values ${List<Object>} : values from configuration
     * @param valid: {boolean} : boolean from parent function
     */

    boolean isValuePassedFilterListWithResolveMethod(String resolvingMethod, List values, List resourceValues) {
        boolean valid = true
        if (Constants.FilteringMethod.ALL.getMethod().equals(resolvingMethod)) {
            if (!resourceValues?.containsAll(values)) {
                valid = false
            }
        } else if (Constants.FilteringMethod.ANY.getMethod().equals(resolvingMethod)) {
            if (!CollectionUtils.containsAny(values, resourceValues)) {
                valid = false
            }
        } else if (Constants.FilteringMethod.NONE.getMethod().equals(resolvingMethod)) {
            if (CollectionUtils.containsAny(values, resourceValues)) {
                valid = false
            }
        } else if (Constants.FilteringMethod.GREATER_THAN.getMethod().equals(resolvingMethod)) {
            if (resourceValues == null || resourceValues?.getAt(0) == null) {
                valid = false
            } else if (resourceValues && resourceValues.getAt(0) != null && resourceValues.getAt(0) < values.getAt(0)) {
                valid = false
            }
        } else if (Constants.FilteringMethod.LESS_THAN.getMethod().equals(resolvingMethod)) {
            if (resourceValues == null || resourceValues?.getAt(0) == null) {
                valid = false
            } else if (resourceValues && resourceValues.getAt(0) != null && resourceValues.getAt(0) > values.getAt(0)) {
                valid = false
            }
        } else if (!CollectionUtils.containsAny(values, resourceValues)) {
            valid = false
        }
        return valid
    }

    boolean fitlterResourceAndDatasetByResourceFilterParameter (Resource resource, Dataset dataset, Map<String,List<String>> resourceFilterParameters, boolean passed = false) {
        if (resource) {
            //TODO: #runResourceFilterCriteria returns a LIST!!!!!
            passed = runResourceFilterCriteria([resource],resourceFilterParameters)
        }
        if (dataset && !passed) {
            resourceFilterParameters?.each {String key, List<String> values ->
                passed = dataset.additionalQuestionAnswers?.get(key) &&
                        values?.contains(dataset.additionalQuestionAnswers.get(key))
            }
        }
        return passed
    }

    def runResourceFilterCriteria(List<Resource> appResources, Map<String, List<String>> resourceFilterCriteria) {
        List<Resource> filteredResources = []

        if (appResources && resourceFilterCriteria) {
            for (Resource r in appResources) {
                Iterator<String> iterator = resourceFilterCriteria.keySet().iterator()
                boolean valid = true

                while (iterator.hasNext()) {
                    String resourceAttribute = iterator.next()

                    if (resourceAttribute) {
                        def requiredValues = resourceFilterCriteria.get(resourceAttribute)

                        if (requiredValues) {
                            def resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, r)

                            if (!resourceValue && ResourceType.hasProperty(resourceAttribute)) {
                                resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resourceService.getSubType(r))
                            }

                            if (requiredValues instanceof List) {
                                if (resourceValue instanceof List) {
                                    if (!CollectionUtils.containsAny(requiredValues, resourceValue)) {
                                        valid = false
                                    }
                                } else {
                                    if (!requiredValues.contains(resourceValue)) {
                                        valid = false
                                    }
                                }
                            } else {
                                if (resourceValue != requiredValues) {
                                    valid = false
                                }
                            }
                        }
                    }
                }

                if (valid) {
                    filteredResources.add(r)
                }
            }
        } else if (appResources) {
            filteredResources = new ArrayList<Resource>(appResources)
        }
        return filteredResources
    }

    List<Document> getResourcesByResourceGroupsAsDocuments(List<String> resourceGroups, Boolean active = true) {
        List<Document> resources = []
        if (resourceGroups && !resourceGroups.isEmpty()) {
            try {
                resources = Resource.collection.find([resourceGroup : [$in: resourceGroups], active: active])?.toList()

                String locale = "name" + DomainObjectUtil.mapKeyLanguage
                resources?.each { Document r ->
                    r["localizedName"] = r.get(locale) ?: r.get("nameEN")
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in getResourcesByResourceGroups", e)
                flashService.setErrorAlert("Error occurred in getResourcesByResourceGroups: ${e.getMessage()}", true)
            }
        }
        return resources?.sort({ it.get("localizedName") })
    }

    def getResourcesForDataLoadingFeature(String filterValue, List<String> resourceGroups, String sortBy = null) {
        List<Resource> resourcesForDataLoadingFeature

        if (filterValue) {
            DBObject query = new BasicDBObject("filterValue", filterValue)

            if (resourceGroups && !resourceGroups.isEmpty()) {
                query.put("resourceGroup", [$in: resourceGroups])
            }
            query.put("active", true)
            DBObject projection = new BasicDBObject([resourceId: 1, profileId: 1])

            if (sortBy) {
                DBObject sort = new BasicDBObject("${sortBy}", 1)
                projection.put("${sortBy}", 1)
                resourcesForDataLoadingFeature = Resource.collection.find(query, projection)?.sort(sort)?.collect({
                    it as Resource
                })
            } else {
                resourcesForDataLoadingFeature = Resource.collection.find(query, projection)?.collect({
                    it as Resource
                })
            }
        }

        return resourcesForDataLoadingFeature
    }

    def getResourcesByFilterRule(String applicationId, String ruleId, List<Resource> resources) {
        List<Resource> resourcesByFilterRule = []

        if (applicationId && ruleId) {
            ResourceFilterRule resourceFilterRule = applicationService.getApplicationByApplicationId(applicationId)?.
                    filterRules?.find({ ruleId.equals(it.ruleId) })

            if (resourceFilterRule) {
                List<Resource> appResources

                if (resources) {
                    appResources = new ArrayList<>(resources)
                } else {
                    appResources = getResourcesByApplicationId(applicationId)
                }

                if (appResources) {
                    for (Resource r in appResources) {
                        boolean matches = true

                        for (String requiredField in resourceFilterRule.requiredFields) {
                            if (DomainObjectUtil.callGetterByAttributeName(requiredField, r) == null) {
                                matches = false

                                if (r.missingRequiredFilterFields && !r.missingRequiredFilterFields.contains(requiredField)) {
                                    r.missingRequiredFilterFields.add(requiredField)
                                } else {
                                    r.missingRequiredFilterFields = [requiredField]
                                }
                            }
                        }

                        if (resourceFilterRule.requiredValues) {
                            List<String> attributeNames = resourceFilterRule.requiredValues.keySet().toList()

                            for (String attributeName in attributeNames) {
                                boolean matchesResourceFilterRule = true
                                def value = DomainObjectUtil.callGetterByAttributeName(attributeName, r)
                                List<String> resourceGroups = resourceFilterRule.requiredValues.get(attributeName)
                                resourceGroups.find {
                                    def resourceGroup = it

                                    if (value instanceof List && !value.contains(resourceGroup)) {
                                        matchesResourceFilterRule = false
                                    } else if (!(value instanceof List) && !resourceFilterRule.requiredValues.get(attributeName).contains(value)) {
                                        matchesResourceFilterRule = false
                                    } else {
                                        matchesResourceFilterRule = true
                                        return true
                                    }
                                }

                                if (!matchesResourceFilterRule) {
                                    matches = false
                                    def missingValue = "${attributeName}: ${value}"

                                    if (r.missingRequiredFilterValues && !r.missingRequiredFilterValues.contains(missingValue)) {
                                        r.missingRequiredFilterValues.add(missingValue)
                                    } else {
                                        r.missingRequiredFilterValues = [missingValue]
                                    }
                                }
                            }
                        }

                        if (resourceFilterRule.advancedRequiredValues) {
                            for (ResourceFilterCriteria criteria in resourceFilterRule.advancedRequiredValues) {
                                boolean failsCriteria = false
                                String attribute = criteria.attribute
                                String resolvingMethod = criteria.resolve ?: "all"
                                def resourceValue

                                if ("exists".equals(resolvingMethod)) {
                                    if (attribute) {
                                        resourceValue = DomainObjectUtil.callGetterByAttributeName(attribute, r)

                                        if (resourceValue == null) {
                                            matches = false
                                            failsCriteria = true
                                        }
                                    }
                                } else {
                                    List<Object> values = criteria.values

                                    if (attribute && values != null) {
                                        resourceValue = DomainObjectUtil.callGetterByAttributeName(attribute, r)

                                        if (resourceValue instanceof List) {
                                            if ("all".equals(resolvingMethod)) {
                                                if (!resourceValue.containsAll(values)) {
                                                    matches = false
                                                    failsCriteria = true
                                                }
                                            } else if ("any".equals(resolvingMethod)) {
                                                if (!CollectionUtils.containsAny(values, resourceValue)) {
                                                    matches = false
                                                    failsCriteria = true
                                                }
                                            } else if ("none".equals(resolvingMethod)) {
                                                if (CollectionUtils.containsAny(values, resourceValue)) {
                                                    matches = false
                                                    failsCriteria = true
                                                }
                                            }
                                        } else {
                                            if (!values.contains(resourceValue)) {
                                                matches = false
                                                failsCriteria = true
                                            }
                                        }
                                    }
                                }

                                if (failsCriteria) {
                                    def missingValue = "${attribute}: ${resourceValue}, resolving method: ${resolvingMethod}"

                                    if (r.missingRequiredFilterValues) {
                                        r.missingRequiredFilterValues.add(missingValue)
                                    } else {
                                        r.missingRequiredFilterValues = [missingValue]
                                    }
                                }
                            }

                        }

                        if (resourceFilterRule.illegalValues) {
                            List<String> attributeNames = resourceFilterRule.illegalValues.keySet().toList()

                            for (String attributeName in attributeNames) {
                                boolean matchesResourceFilterRule = true
                                def value = DomainObjectUtil.callGetterByAttributeName(attributeName, r)
                                List<String> resourceGroups = resourceFilterRule.illegalValues.get(attributeName)
                                resourceGroups.find {
                                    def resourceGroup = it

                                    if (value instanceof List && value.contains(resourceGroup)) {
                                        matchesResourceFilterRule = false
                                    } else if (!(value instanceof List) && resourceFilterRule.illegalValues.get(attributeName).contains(value)) {
                                        matchesResourceFilterRule = false
                                    } else {
                                        matchesResourceFilterRule = true
                                        return true
                                    }
                                }

                                if (!matchesResourceFilterRule) {
                                    matches = false
                                    def illegalValue = "${attributeName}: ${value}"

                                    if (r.illegalFilterValues && !r.illegalFilterValues.contains(illegalValue)) {
                                        r.illegalFilterValues.add(illegalValue)
                                    } else {
                                        r.illegalFilterValues = [illegalValue]
                                    }
                                }
                            }
                        }

                        if (resourceFilterRule.requireIdenticalValues) {
                            List<String> attributeNames = resourceFilterRule.requireIdenticalValues
                            Map values = [:]

                            for (String attributeName in attributeNames) {
                                def value = DomainObjectUtil.callGetterByAttributeName(attributeName, r)

                                if (value) {
                                    values.put(attributeName, value)
                                }
                            }

                            List<String> foundAttributes = values.keySet()?.toList()
                            List foundAttributeValues = values.values()?.toList()

                            if (foundAttributes && !foundAttributes?.size()?.equals(attributeNames.size())) {
                                matches = false
                                List<String> missingValues = new ArrayList<String>(attributeNames)
                                missingValues.removeAll(foundAttributes)
                                r.failingRequireIdenticalValues = "Missing values from RequireIdenticalValues check: ${missingValues}"
                            } else if (foundAttributeValues.unique().size() > 1) {
                                matches = false
                                r.failingRequireIdenticalValues = "Values do not match: ${values}"
                            }
                        }

                        if (resourceFilterRule.activeResourceId) {
                            for (String attributeName in resourceFilterRule.activeResourceId) {
                                String value = DomainObjectUtil.callGetterByAttributeName(attributeName, r)

                                if (value) {
                                    Resource foundResource = getResourceByResourceAndProfileId(value, null)

                                    if (!foundResource || !foundResource.active) {
                                        matches = false
                                        String failure = "${attributeName}: ${value}"

                                        if (!r.failingActiveResourceIds?.contains(failure)) {
                                            if (r.failingActiveResourceIds) {
                                                r.failingActiveResourceIds.add(failure)
                                            } else {
                                                r.failingActiveResourceIds = [failure]
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (resourceFilterRule.activeResourceType) {
                            if (r.resourceType && !resourceTypeService.getResourceType(r.resourceType, r.resourceSubType, true)) {
                                r.nonActiveResourceType = true
                                matches = false
                            }
                        }


                        if (resourceFilterRule.activeResourceSubType) {
                            if (r.resourceSubType && !resourceTypeService.getResourceTypeBySubType(r.resourceSubType)) {
                                r.nonActiveSubType = true
                                matches = false
                            }
                        }

                        if (resourceFilterRule.verifyValues) {
                            for (ResourceFilterRuleVerifyValue verifyValue in resourceFilterRule.verifyValuesAsObjects) {
                                if (!verifyValue.valueOkForResource(r)) {
                                    matches = false
                                    String failure = "Parameter: ${verifyValue.parameter}, valueConstraints: ${verifyValue.valueConstraints}, resourceValue: ${DomainObjectUtil.callGetterByAttributeName(verifyValue.parameter, r)}"

                                    if (!r.failingVerifyValues?.contains(failure)) {
                                        if (r.failingVerifyValues) {
                                            r.failingVerifyValues.add(failure)
                                        } else {
                                            r.failingVerifyValues = [failure]
                                        }
                                    }

                                }
                            }
                        }

                        if (resourceFilterRule.verifyCalculatedValues) {
                            for (ResourceFilterRuleVerifyValue verifyValue in resourceFilterRule.verifyCalculatedValuesAsObjects) {
                                def value = unitConversionUtil.doConversion((Double) DomainObjectUtil.callGetterByAttributeName(verifyValue.parameter, r), r.defaultThickness_mm, r.resourceTypeObject?.standardUnit, r, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE)

                                if (!verifyValue.calculatedValueOkForResource(r, value)) {
                                    matches = false
                                    String failure = "Parameter: ${verifyValue.parameter}, valueConstraints: ${verifyValue.valueConstraints}, resourceValue: ${value}"

                                    if (!r.failingVerifyValues?.contains(failure)) {
                                        if (r.failingVerifyValues) {
                                            r.failingVerifyValues.add(failure)
                                        } else {
                                            r.failingVerifyValues = [failure]
                                        }
                                    }

                                }
                            }
                        }

                        if (matches && resourceFilterRule.dataMaxAge) {
                            int currentYear = Calendar.getInstance().get(Calendar.YEAR)
                            List<String> attributeNames = resourceFilterRule.dataMaxAge.keySet().toList()

                            for (String attributeName in attributeNames) {
                                def yearForResourceValue = DomainObjectUtil.callGetterByAttributeName(attributeName, r)
                                def requiredMaxAge = resourceFilterRule.dataMaxAge.get(attributeName)

                                if (yearForResourceValue != null && (currentYear - yearForResourceValue) > requiredMaxAge) {
                                    matches = false

                                    if (r.failingMaxAgeFilterValues) {
                                        r.failingMaxAgeFilterValues.add(attributeName + ": " + yearForResourceValue)
                                    } else {
                                        r.failingMaxAgeFilterValues = [attributeName + ": " + yearForResourceValue]
                                    }
                                }
                            }
                        }

                        if (matches) {
                            resourcesByFilterRule.add(r)
                        }
                    }
                }
            }
        }
        return resourcesByFilterRule
    }

    def Resource getResourceByDbId(String id) {
        Resource resource

        if (id) {
            try {
                resource = documentAsResource(Resource.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(id)]))
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting resource by id: ${id}", e)
                flashService.setErrorAlert("Error in getting resource by id: ${id}", true)
            }
        }
        return resource
    }

    Set<Resource> getResourcesByDbIds(Set<String> ids) {
        Set<Resource> resources = []
        if (ids) {
            try {
                resources = Resource.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(ids as List<String>)]])?.toList()?.collect { documentAsResource(it as Document) }
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting resources by ids: ${ids}", e)
                flashService.setErrorAlert("Error in getting resource by ids: ${ids}", true)
            }
        }
        return resources
    }

    def String getResourcePrivateEpdFilePath(String id) {
        String filePath

        if (id) {
            try {
                filePath = Resource.collection.findOne(["_id": DomainObjectUtil.stringToObjectId(id)], [epdcFilePath: 1])?.epdcFilePath
            } catch (Exception e) {
                loggerUtil.error(log, "Error in getting resource by id: ${id}", e)
                flashService.setErrorAlert("Error in getting resource by id: ${id}", true)
            }
        }
        return filePath
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def findResourceUsage(String resourceId, String profileId, Integer batchSize = BATCH_SIZE) {

        Map<String, List<String>> managersAndModifiers = [:]
        Map<String, List<Dataset>> entitiesAndDatasets = [:]

        BasicDBObject query = new BasicDBObject()
        // Closure with condition to filter datasets
        def datasetFilter = { Document dataset -> }

        List<String> additionalQuestionIdsWithResourceIdChoise = getAdditionalQuestionIdsWithResourceIdChoise(resourceId)
        if (!additionalQuestionIdsWithResourceIdChoise.isEmpty()) {
            // checks AdditionalQuestionAnswers for usage of the resource since it is used in additionalQuestions instead as an actual dataset resource
            query.$or = additionalQuestionIdsWithResourceIdChoise.collect { String questionId ->
                [("datasets.additionalQuestionAnswers." + questionId): resourceId]
            }

            datasetFilter = { Document dataset ->
                additionalQuestionIdsWithResourceIdChoise.any { String questionId ->
                    dataset.additionalQuestionAnswers?.get(questionId) == resourceId
                }
            }
        } else if (resourceId) {
            if (profileId) {
                query.datasets = ['$elemMatch': ['resourceId': resourceId, 'profileId': profileId]]

                datasetFilter = { Document dataset ->
                    resourceId.equals(dataset.resourceId) && profileId.equals(dataset.profileId)
                }
            } else {
                query["datasets.resourceId"] = resourceId
                datasetFilter = { Document dataset ->
                    resourceId.equals(dataset.resourceId)
                }
            }
        }

        if (!query.isEmpty()) {

            Integer offset = 0
            Boolean isLastBatch = false

            while (!isLastBatch) {

                List<Document> entities = Entity.collection.find(query, [
                                parentEntityId : 1,
                                name           : 1,
                                operatingPeriod: 1,
                                ribaStage      : 1,
                                datasets       : 1,
                                managerIds     : 1,
                                modifierIds    : 1,
                        ])
                        .skip(offset)
                        .limit(batchSize).toList()

                if (!entities) {
                    isLastBatch = true
                    break
                }

                if (entities.size() < batchSize) {
                    isLastBatch = true
                }
                offset += batchSize

                List<ObjectId> parentEntityIdList = entities.findResults { it.parentEntityId }
                List<Entity> parentEntityList = entityService.getAllEntitiesByIds(parentEntityIdList)

                entities.each { Document entity ->

                    String operatingPeriodAndName = entityService.getOperatingPeriodAndName(entity)

                    if (entity.parentEntityId) {
                        Entity parent = parentEntityList.find { it.id == entity.parentEntityId }
                        String key = "${parent?.name}, ${operatingPeriodAndName}"

                        entitiesAndDatasets.put(key, entity.datasets.
                                findResults({ Document dataset -> datasetFilter(dataset) ? dataset as Dataset : null })?.toList())
                        managersAndModifiers.put(key, parent?.getManagersAndModifiersEmails())
                    } else {
                        entitiesAndDatasets.put(operatingPeriodAndName, entity.datasets.
                                findResults({ Document dataset -> datasetFilter(dataset) ? dataset as Dataset : null })?.toList())
                        managersAndModifiers.put(operatingPeriodAndName, entityService.getManagersAndModifiersEmails(entity))
                    }
                }
            }
        }

        def returnable = [:]
        returnable.put("entitiesAndDatasets", entitiesAndDatasets)
        returnable.put("managersAndModifiers", managersAndModifiers)
        return returnable
    }

    private List<String> getAdditionalQuestionIdsWithResourceIdChoise(String resourceId) {

        List<String> additionalQuestionIdsWithResourceIdChoise = []

        Document resourceDoc = Resource.collection.findOne([resourceId: resourceId, active: true]) ?:
                               Resource.collection.findOne([resourceId: resourceId])
        Resource resource = resourceDoc ? resourceDoc as Resource : null

        if (!resource) {
            return additionalQuestionIdsWithResourceIdChoise
        }

        Configuration additionalQuestionQueryConfig = configurationService.getByConfigurationName(com.bionova.optimi.construction.Constants.APPLICATION_ID, com.bionova.optimi.construction.Constants.ConfigName.ADDITIONAL_QUESTIONS_QUERY_ID.toString())
        def additionalQuestionQueryId = additionalQuestionQueryConfig?.value
        Query additionalQuestionsQuery = queryService.getQueryByQueryId(additionalQuestionQueryId, true)

        if (additionalQuestionsQuery && resource.resourceGroup) {

            additionalQuestionsQuery.sections?.each { QuerySection section ->
                section.questions?.each { Question question ->
                    if (question.choices) {
                        if (question.choices.find({ it.answerId == resourceId })) {
                            additionalQuestionIdsWithResourceIdChoise.add(question.questionId)
                        }
                    } else if (question.resourceGroups && CollectionUtils.containsAny(question.resourceGroups, resource.resourceGroup)) {
                        additionalQuestionIdsWithResourceIdChoise.add(question.questionId)
                    }
                }
            }
        }

        return additionalQuestionIdsWithResourceIdChoise
    }

    void updateStringFieldsInResource(Map<String, List<Map<String, String>>> config, Resource resource) {
        if (!resource || !config) {
            return
        }
        stringUtilsService.updateStringInObject(config, resource)
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def importExcelWorkbook(MultipartFile excelFile, Boolean dryRun = Boolean.FALSE, Account account = null, Boolean isUploadingNmd = false) {
        def returnable = [:]
        def rejectedDefaultProfiles = []
        List<String> deprecatedFields = ["costDataSource", "costDataPeriod", "cost_EUR", "costReplacement_EUR", "costMaintenance_EUR",
                                         "importMapping", "maintenanceFrequency", "maintenanceResourceId", "targetMarket", "unit", "allowedUnits",
                                         "wasteEnergyContent_kWh", "coveredByETS", "enableTrialRequest", "enableQuoteRequest", "wasteEnergyContent_kWh", "amountOfVehicles",
                                         "breDataStandard", "eWCCode", "failingMaxAgeFilterValues", "useThickness", "wasteShareInUsePhase",
                                         "mappedIdentifiers", "operatingperiod", "profileNameFI", "profileNameNO", "profileNameSV", "nameSV",
                                         "cost_INR", "profileNameEN", "traciFossil_MJ", "traciEnabled", "impactADP_kgSbe", "preConsumerWaste_kg", "postConsumerWaste_kg",
                                         "validForLEED", "validForBREEAM", "embodiedEnergy_MJ", "embodiedRenewableEnergy_MJ", "highRadioactiveWaste_kg", "residential",
                                         "cost_GBP", "cost_SEK", "cost_USD", "defaultMaintenanceQuantity", "hasCityDistrict", "compensationFactor", "defaultTransportDistance_km"]

        try {
            def fileName = excelFile.originalFilename
            log.info("PART 2: importing file: ${fileName}")
            //Workbook workbook = WorkbookFactory.create(excelFile.inputStream)
            Workbook workbook = StreamingReader.builder()
                    .rowCacheSize(100)
                    .bufferSize(4096)
                    .open(excelFile.inputStream)
            log.info("PART 3: streaming reader opened")
            def numberOfSheets = workbook?.getNumberOfSheets()
            log.info("PART 4: nr of sheets: ${numberOfSheets}")

            def sheets = []
            def okResources = 0
            def rejectedResources = 0
            String errorMessage = ""
            String deprecatedFieldsError

            Map propertyConfigMap = [:]
            List<PersistentProperty> properties = domainClassService.getPersistentPropertiesForDomainClass(Resource.class)
            List<String> mandatoryProperties = domainClassService.getMandatoryPropertyNamesForDomainClass(Resource.class)
            List<String> listProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, List)
            List<String> doubleProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Double)
            List<String> booleanProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Boolean)
            List<String> integerProperties = domainClassService.getPersistentPropertyNamesForDomainClass(Resource.class, Integer)
            String invalidFieldChars = "\"”'"

            resourceCache = new HashMap<String, Resource>()
            resourceProfilesCache = new HashMap<String, List<Resource>>()

            String importWarnings = ""

            properties?.each { PersistentProperty property ->
                if (property.type.equals(String.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.StringType])
                } else if (property.type.equals(Double.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.DoubleType])
                } else if (property.type.equals(Integer.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.IntType])
                } else if (property.type.equals(Boolean.class)) {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.BooleanType])
                } else {
                    propertyConfigMap.put(property.name, ["expectedType": OptimiExcelImportService.ExpectedPropertyType.StringType])
                }
            }
            def resource_columns_list = []
            List<Resource> okResourceList = []
            List<Resource> errorResourcesList = []
            List<Resource> stageResourceList = []

            Map<String, List<Resource>> stageResourceMap = new HashMap<String, List<Resource>>()

            properties.each {
                if (it.name != "id") {
                    resource_columns_list.add(it.name)
                }
            }

            for (int j = 1; j < 10; j++) {
                resource_columns_list.add("compositeResource" + j)
                resource_columns_list.add("compositeProfile" + j)
                resource_columns_list.add("compositeQuantity" + j)
            }
            long now = System.currentTimeMillis()
            if (numberOfSheets) {
                for (Sheet sheet : workbook){
                    if (!sheet.getSheetName()?.contains('@')) {
                        sheets.add(sheet)
                        Map columnMap = [:]
                        String sheetName = sheet.sheetName

                        for (Row r : sheet) {
                            if (r.rowNum == 0) {
                                //log.info("Handling row num: ${r.rowNum}")
                                Map cellIndexesMap = optimiExcelImportService.createColumnMapFromColumnNames(resource_columns_list, r)

                                if (cellIndexesMap?.keySet()?.toList()?.containsAll(mandatoryProperties)) {
                                    cellIndexesMap.keySet().each { String attributeName ->
                                        if (attributeName.endsWith(" ")) {
                                            errorMessage = errorMessage + " Heading ${attributeName} contains trailing whitespace! Attributes might not have been set.<br />"
                                        }
                                        def cellIndex = cellIndexesMap.get(attributeName)
                                        columnMap.put((cellIndex as int), attributeName)
                                    }
                                } else {
                                    List<String> missingMandatoryFields = new ArrayList<String>(mandatoryProperties)
                                    missingMandatoryFields.removeAll(cellIndexesMap.keySet()?.toList())
                                    errorMessage = errorMessage + " Rejected sheet ${sheet.getSheetName()} totally, missing required fields: ${missingMandatoryFields}<br />"
                                    break
                                }
                            } else {
                                Map resourceParamMap = [:]

                                for (Cell c : r) {
                                    String attribute = columnMap.get(c.columnIndex)
                                    String cellValue = importMapperService.getCellValue(c, true)
                                    boolean isCellValueANumber = DomainObjectUtil.isNumericValue(cellValue)

                                    if (attribute && cellValue && !attribute.startsWith("@")) {
                                        if (cellValue && cellValue.endsWith(" ")) {
                                            if (attribute.startsWith("name")||["productDescription","technicalSpec","commercialName"].contains(attribute)) {
                                                cellValue = cellValue.trim()
                                            } else {
                                                importWarnings = importWarnings + "Value ends with whitespace in Sheet: ${sheetName}, Row: ${r.rowNum + 1}, Column: ${attribute}, Value: ${cellValue}, Attribute might not have been set correctly.<br/>"
                                            }
                                        }

                                        if (listProperties.contains(attribute)) {
                                            if (cellValue.contains(",")) {
                                                def listed = []
                                                cellValue.tokenize(",")?.each {
                                                    listed.add(it.trim())
                                                }
                                                resourceParamMap.put(attribute, listed)
                                            } else {
                                                resourceParamMap.put(attribute, [cellValue])
                                            }
                                        } else if (doubleProperties.contains(attribute)) {
                                            if (isCellValueANumber) {
                                                resourceParamMap.put(attribute, DomainObjectUtil.convertStringToDouble(cellValue))
                                            } else {
                                                errorMessage = "${errorMessage}Invalid Double value in Sheet: ${sheetName}, Row: ${r.rowNum + 1}, Column: ${attribute}, Value: ${cellValue}<br/>"
                                            }
                                        } else if (integerProperties.contains(attribute)) {
                                            if (isCellValueANumber) {
                                                resourceParamMap.put(attribute, DomainObjectUtil.convertStringToDouble(cellValue).intValue())
                                            } else {
                                                errorMessage = "${errorMessage}Invalid Integer value in Sheet: ${sheetName}, Row: ${r.rowNum + 1}, Column: ${attribute}, Value: ${cellValue}<br/>"
                                            }
                                        } else if (booleanProperties.contains(attribute)) {
                                            resourceParamMap.put(attribute, cellValue.toBoolean())
                                        } else {
                                            resourceParamMap.put(attribute, cellValue)
                                        }
                                    }
                                }
                                log.trace("Handling row num: ${r.rowNum}, RESOURCE AS MAP: ${resourceParamMap}")

                                String resourceId = resourceParamMap?.get("resourceId")?.toString()?.trim()

                                if (resourceId && (resourceId.contains("\n") || resourceId.contains("\r"))){
                                    throw new IllegalArgumentException("Resource with id ${resourceId} contains special characters which is prohibited. Please remove these characters and upload file again.")
                                }

                                if (!resourceId?.startsWith("@")) {
                                    String profileId = resourceParamMap.get("profileId")?.toString()?.trim()
                                    String stage = resourceParamMap?.get("stage")?.toString()?.trim()

                                    if (resourceParamMap.keySet()?.containsAll(mandatoryProperties) || (stage && !stage.equals("A1-A3"))) {

                                        if (resourceId) {
                                            // Fix for task 1442
                                            if (profileId?.isDouble()) {
                                                profileId = "" + (int) Double.parseDouble(profileId)
                                            }
                                            def reject
                                            def defaultProfile = resourceParamMap.get("defaultProfile")

                                            if (defaultProfile && !(defaultProfile instanceof Boolean)) {
                                                reject = "Invalid defaultProfile for resource ${resourceId}: " + defaultProfile + ". Class is " + defaultProfile.class
                                                defaultProfile = Boolean.FALSE
                                            } else if (defaultProfile == null) {
                                                reject = "Empty defaultProfile for resource ${resourceId}"
                                            }

                                            def invalidChar = ""

                                            def impactNonLinear = resourceParamMap.get("impactNonLinear")

                                            if (impactNonLinear && !(impactNonLinear instanceof Boolean)) {
                                                log.info("Invalid impactNonLinear: " + impactNonLinear + ". Class is " + impactNonLinear.class)
                                                invalidChar = "${invalidChar}Invalid impactNonLinear for resource ${resourceId}: " + impactNonLinear + ". Class is " + impactNonLinear.class + "<br />"
                                                impactNonLinear = null
                                            }
                                            resourceParamMap.put("impactNonLinear", impactNonLinear)
                                            def active = resourceParamMap.get("active")

                                            if (active == null) {
                                                reject = "Active null for resource ${resourceId}"
                                            }

                                            String verificationStatusInput = resourceParamMap.get("verificationStatus")
                                            String verificationStatusCleaned = verificationStatusInput?.replaceAll('\\s','')?.toLowerCase() ?: ''
                                            if (verificationStatusCleaned && Constants?.ALLOWED_RESOURCE_VERIFICATION_STATUS?.keySet()?.contains(verificationStatusCleaned)) {
                                                resourceParamMap.put("verificationStatus", verificationStatusCleaned)
                                                String verificationValue = Constants?.ALLOWED_RESOURCE_VERIFICATION_STATUS?.get(verificationStatusCleaned) ?: ''

                                                if (verificationValue) {
                                                    List<String> resistanceProperties = (resourceParamMap.get("resistanceProperties") as List<String>) ?: []
                                                    resistanceProperties.add(verificationValue)
                                                    resourceParamMap.put("resistanceProperties", resistanceProperties)
                                                }

                                            }

                                            String unitForData = resourceParamMap.get("unitForData")

                                            if (!unitForData && (!stage||stage.equals("A1-A3"))) {
                                                importWarnings = "${importWarnings}ResourceId ${resourceId} has no unitForData<br />"
                                            }

                                            String thickness_mm = resourceParamMap.get("defaultThickness_mm")
                                            String thickness_in = resourceParamMap.get("defaultThickness_in")

                                            if (thickness_mm && thickness_in) {
                                                if (DomainObjectUtil.isNumericValue(thickness_mm) && DomainObjectUtil.isNumericValue(thickness_in)) {
                                                    Double thickness_mm_double = DomainObjectUtil.convertStringToDouble(thickness_mm)
                                                    Double thickness_in_double = DomainObjectUtil.convertStringToDouble(thickness_in)

                                                    if (thickness_mm_double && thickness_in_double && !thickness_mm_double.round(2).equals((thickness_in_double * 25.4).round(2))) {
                                                        importWarnings = "${importWarnings}Resource ${resourceId} / ${profileId} has defaultThickness_mm (${thickness_mm_double}) and defaultThickness_in (${thickness_in_double}) but they dont match in mm (${thickness_in_double} * 25.4 == ${(thickness_in_double * 25.4)})<br />"
                                                    }
                                                } else {
                                                    importWarnings = "${importWarnings}Resource ${resourceId} / ${profileId} has defaultThickness_mm (${thickness_mm}) and defaultThickness_in (${thickness_in}) but were unable to recognize them as numbers<br />"
                                                }
                                            }

                                            if (StringUtils.contains(resourceId, " ")) {
                                                invalidChar = "${invalidChar}ResourceId ${resourceId} contains whitespace<br />"
                                            }

                                            if (StringUtils.contains(resourceId, "+")) {
                                                invalidChar = "${invalidChar}ResourceId ${resourceId} contains invalid char (+)<br />"
                                            }

                                            if (StringUtils.contains(profileId, " ")) {
                                                invalidChar = "${invalidChar}ProfileId ${profileId} for resource ${resourceId} contains whitespace<br />"
                                            }

                                            Map formattedResourceParamMap = [:]

                                            resourceParamMap?.each { key, value ->
                                                if (value instanceof String) {
                                                    String formattedValue = value.toString().replaceAll("[\\t\\n\\r]+"," ") //To remove linebreaks etc from strings

                                                    if (StringUtils.containsAny(value, invalidFieldChars)) {
                                                        invalidChar = "${invalidChar}Found invalid character from resourceId ${resourceId}, profileId ${profileId} in field ${key}: ${value}<br />"
                                                    }
                                                    formattedResourceParamMap.put(key, formattedValue)
                                                } else {
                                                    formattedResourceParamMap.put(key, value)
                                                }
                                            }

                                            if ((!reject || stage) && !invalidChar) {
                                                formattedResourceParamMap.put("defaultProfile", defaultProfile)
                                                Resource resource

                                                if (!stage || "A1-A3".equals(stage)) {
                                                    List<Resource> resources = Resource.findAllByResourceIdAndProfileId(resourceId, profileId)

                                                    if (resources) {
                                                        if (resources.size() > 1) {
                                                            if (stage) {
                                                                resource = resources.find({stage.equals(it.stage)})
                                                            } else {
                                                                resource = resources.find({!it.stage})
                                                            }
                                                        } else {
                                                            resource = resources.first()
                                                        }
                                                    }
                                                }

                                                if (resource) {
                                                    properties.each { PersistentProperty domainClassProperty ->
                                                        // Skip setting firstUploadTime back to null or automaticallyEnabledPurposes
                                                        if (!["firstUploadTime","automaticallyEnabledPurposes"].contains(domainClassProperty.name)) {
                                                            DomainObjectUtil.callSetterByAttributeName(domainClassProperty.name, resource, formattedResourceParamMap.get(domainClassProperty.name), Boolean.TRUE)
                                                        }
                                                    }
                                                } else {
                                                    resource = new Resource(formattedResourceParamMap)
                                                }
                                                resource.importFile = fileName
                                                resource.resourceId = resourceId
                                                resource.profileId = profileId

                                                if (resource.validate() || resource.stage) {
                                                    resource = generateDynamicValues(resource)

                                                    if (resource.upstreamDB) {
                                                        Map<String, List<String>> upstreamDBClassifiedList = Constants.UPSTREAMDBCLASSIFIED
                                                        upstreamDBClassifiedList.each { String upstreamDBClassified, List<String> upstreamDBs ->
                                                            if (upstreamDBs*.toUpperCase().contains(resource.upstreamDB.toUpperCase())) {
                                                                resource.upstreamDBClassified = upstreamDBClassified
                                                            }
                                                        }
                                                    }

                                                    if (resource.stage) {
                                                        List<Resource> stagedResources = stageResourceMap.get("${resource.resourceId}_${resource.profileId}".toString())
                                                        if (stagedResources) {
                                                            stagedResources.add(resource)
                                                        } else {
                                                            stagedResources = [resource]
                                                        }
                                                        stageResourceMap.put("${resource.resourceId}_${resource.profileId}".toString(), stagedResources)
                                                    } else {
                                                        if (!okResourceList.find({
                                                            resource.resourceId.equals(it.resourceId) && resource.profileId.equals(it.profileId)
                                                        })) {
                                                            okResourceList.add(resource)
                                                        } else {
                                                            resource.errors.reject("Excel contains duplicates for resource ${resource.resourceId} / ${resource.profileId}")
                                                            errorResourcesList.add(resource)
                                                        }
                                                    }
                                                } else {
                                                    errorResourcesList.add(resource)
                                                }
                                            } else {
                                                rejectedResources++

                                                if (reject) {
                                                    rejectedDefaultProfiles.add(reject)
                                                }

                                                if (invalidChar) {
                                                    rejectedDefaultProfiles.add(invalidChar)
                                                }
                                            }
                                        }
                                    } else {
                                        List keySet = resourceParamMap?.keySet()?.toList()

                                        if (keySet && !keySet.isEmpty()) {
                                            rejectedResources++
                                            List missingValues = mandatoryProperties?.findAll({!keySet?.contains(it)})

                                            if (resourceId) {
                                                errorMessage = errorMessage + "Resource ${resourceId} in sheet ${sheet?.sheetName} / row ${r.rowNum} is missing required values ${missingValues}<br />"
                                            } else {
                                                errorMessage = errorMessage + "Resource in sheet ${sheet?.sheetName} / row ${r.rowNum} is missing required values ${missingValues}<br />"
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            workbook.close()

            log.info("Sheets handled in ${System.currentTimeMillis() - now} ms")
            now = System.currentTimeMillis()
            int size = stageResourceMap.keySet().size()
            int j = 1;
            log.info("Handling ${size} stage resources...")

            if (stageResourceMap) {
                stageResourceMap.each { String key, List<Resource> resources ->
                    //log.info("STAGE MAP PROGRESS: ${j} / ${size}")
                    Resource mainStage = resources?.find({"A1-A3".equals(it.stage)})
                    Map<String, Map<String, Objects>> impactsByStage = [:]
                    int i = 0

                    if (mainStage) {
                        resources.each { Resource r ->
                            if (!mainStage.stage.equals(r.stage)) {
                                Map<String, Object> impacts = [:]

                                Resource.allowedImpactCategories.each { String impactCategory ->
                                    Object value = DomainObjectUtil.callGetterByAttributeName(impactCategory, r)

                                    if (value != null) {
                                        impacts.put(impactCategory, value)
                                    }
                                }

                                if (!impacts.isEmpty()) {
                                    impactsByStage.put((r.stage), impacts)
                                }
                            } else {
                                if (i) {
                                    r.errors.reject("Stage error: Excel contains duplicate main stage A1-A3 for resource ${r.resourceId} / ${r.profileId}")
                                    errorResourcesList.add(r)
                                } else {
                                    i++
                                }
                            }
                        }

                        Map<String, Object> impacts = [:]

                        Resource.allowedImpactCategories.each { String impactCategory ->
                            Object value = DomainObjectUtil.callGetterByAttributeName(impactCategory, mainStage)

                            if (value != null) {
                                impacts.put((impactCategory), value)
                            }
                        }

                        if (!impacts.isEmpty()) {
                            impactsByStage.put((mainStage.stage), impacts)
                        }

                        if (!impactsByStage.isEmpty()) {
                            mainStage.impacts = impactsByStage
                        }

                        if (mainStage.validate()) {
                            okResourceList.add(mainStage)
                        } else {
                            errorResourcesList.add(mainStage)
                        }
                    } else {
                        resources.each { Resource resource ->
                            resource.errors.reject("Stage error: Resource ${resource.resourceId} / ${resource.profileId} missing main stage A1-A3<br />")
                            errorResourcesList.add(resource)
                        }
                    }
                    j++
                }
            }

            if (!okResourceList.isEmpty()) {
                // get config to remove chars in nmd resource
                Map<String, List<Map<String, String>>> updateStringForNmdResourceConfig = nmdResourceService.getUpdateStringOnImportNmd()
                Resource.withNewTransaction {
                    okResourceList.each { Resource resource ->
                        if (!dryRun) {
                            if (account) {
                                resource.privateDatasetAccountId = account.id
                                resource.privateDataset = Boolean.TRUE
                                resource.environmentDataSourceType = Resource.CONSTANTS.PRIVATE.value()
                                resource.privateDatasetAccountName = account.companyName
                            }
                            setMagicButtonValues(resource)
                            if (isUploadingNmd) {
                                updateStringFieldsInResource(updateStringForNmdResourceConfig, resource)
                            }
                            if (resource.id) {
                                resource.merge(flush: true)
                            } else {
                                loggerUtil.info(log, "Adding new resource, not found in database " + resource.resourceId + ", " + resource.profileId + ", " + resource.defaultProfile + " for the imported row.")
                                resource.save(flush: true)
                            }
                        }
                        List duplicateDefaults = Resource.findAllByResourceIdAndDefaultProfile(resource.resourceId, true)
                        List duplicateResourceAndProfiles = Resource.findAllByResourceIdAndProfileId(resource.resourceId, resource.profileId)

                        if (duplicateDefaults && duplicateDefaults.size() > 1) {
                            List existing = returnable.get("duplicateDefaults")

                            if (existing && existing instanceof List) {
                                List temp = new ArrayList(existing)
                                temp.addAll(new ArrayList(duplicateDefaults))
                                returnable.put("duplicateDefaults", temp)
                            } else {
                                returnable.put("duplicateDefaults", new ArrayList(duplicateDefaults))
                            }
                        }

                        if (duplicateResourceAndProfiles && duplicateResourceAndProfiles.size() > 1) {
                            List existing = returnable.get("duplicateResourceAndProfiles")

                            if (existing && existing instanceof List) {
                                List temp = new ArrayList(existing)
                                temp.addAll(new ArrayList(duplicateResourceAndProfiles))
                                returnable.put("duplicateResourceAndProfiles", temp)
                            } else {
                                returnable.put("duplicateResourceAndProfiles", new ArrayList(duplicateResourceAndProfiles))
                            }
                        }
                    }
                }
                okResources = okResourceList.size()

                String notActiveResMessage = okResourceList?.findAll {it.active == false}?.resourceId ?: ""
                notActiveResMessage = notActiveResMessage ? "Excel contains the following Active FALSE resources: $notActiveResMessage" : ""
                returnable.put("notActiveResMessage", notActiveResMessage)
            }
            if (rejectedDefaultProfiles) {
                returnable.put("rejectedDefaultProfiles", rejectedDefaultProfiles)
            }
            returnable.put("file", fileName)
            returnable.put("sheets", sheets)
            returnable.put("okResources", okResources)
            returnable.put("importWarnings", importWarnings)

            if (dryRun) {
                returnable.put("resources", okResourceList)
            }
            returnable.put("rejectedResources", rejectedResources)
            returnable.put("errorResources", errorResourcesList)
            returnable.put("errorMessage", errorMessage)
            returnable.put("deprecatedFieldsError", deprecatedFieldsError)
        } catch (Exception e) {
            loggerUtil.warn(log, "Error in importing resources", e)
            flashService.setErrorAlert("Error in importing resources: ${e.getMessage()}", true)
            returnable.put("errorMessage", e)
        }
        return returnable
    }

    public void setMagicButtonValues(Resource resource) {
        if (resource) {
            if (resource.areas) {
                if (!resource.resourceGroup || !CollectionUtils.containsAny(resource.resourceGroup, ["world", "US-states", "CA-provinces", "otherAreas", "constructionAreas"])) {
                    Map<String, String> isoCodesByAreas = getresourceIsoCodes(resource.areas, resource.additionalArea)

                    if (isoCodesByAreas) {
                        resource.isoCodesByAreas = isoCodesByAreas
                    } else if (resource.areas) {
                        log.error("MagicButton on resource upload: ${resource.resourceId} / ${resource.profileId}, had an areas: ${resource.areas}, but did not find a countryCodes for it.")
                        flashService.setErrorAlert("MagicButton on resource upload: ${resource.resourceId} / ${resource.profileId}, had an areas: ${resource.areas}, but did not find a countryCodes for it.", true)
                    }
                }
            }
            Integer profiles = Resource.collection.count(["resourceId": resource.resourceId, "active": true])?.intValue()

            if (resource.id) {
                if (profiles > 1) {
                    resource.multipleProfiles = Boolean.TRUE
                } else {
                    resource.multipleProfiles = Boolean.FALSE
                }
            } else {
                if (profiles >= 1) {
                    resource.multipleProfiles = Boolean.TRUE
                } else {
                    resource.multipleProfiles = Boolean.FALSE
                }
            }

            if (resource.resourceSubType) {
                String resourceType = resourceTypeService.getResourceTypeBySubType(resource.resourceSubType)

                if (resourceType) {
                    resource.resourceType = resourceType
                } else {
                    log.error("MagicButton on resource upload: ${resource.resourceId} / ${resource.profileId}, had a subType: ${resource.resourceSubType}, but did not find a resourceType for it.")
                    flashService.setErrorAlert("MagicButton on resource upload: ${resource.resourceId} / ${resource.profileId}, had a subType: ${resource.resourceSubType}, but did not find a resourceType for it.", true)
                }
            }
            if (!resource.firstUploadTime) {
                resource.firstUploadTime = new Date()
            }
            List<String> purposes = []
            if (resource.manuallyEnabledPurposes) {
                purposes.addAll(resource.manuallyEnabledPurposes)
            }
            if (resource.disableAutomaticPurposes) {
                resource.automaticallyEnabledPurposes = null
            } else if (resource.automaticallyEnabledPurposes) {
                purposes.addAll(resource.automaticallyEnabledPurposes)
            }
            resource.enabledPurposes = purposes.unique()
        }
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def saveResource(Resource resource) {
        if (!resource.hasErrors()) {
            resource = generateDynamicValues(resource)

            if (resource.upstreamDB) {
                Map<String, List<String>> upstreamDBClassifiedList = Constants.UPSTREAMDBCLASSIFIED
                upstreamDBClassifiedList.each { String upstreamDBClassified, List<String> upstreamDBs ->
                    if (upstreamDBs*.toUpperCase().contains(resource.upstreamDB.toUpperCase())) {
                        resource.upstreamDBClassified = upstreamDBClassified
                    }
                }
            }
            setMagicButtonValues(resource)

            if (resource.id) {
                resource = resource.merge(flush: true, failOnError: true)
            } else {
                resource = resource.save(flush: true, failOnError: true)
            }
            String key = "${resource.resourceId}${resource.profileId}"
            resourceCache.remove(key)
            resourceProfilesCache.remove(resource.resourceId)
        } else {
            log.error("Could not save the resource: ${resource.getErrors()}")
            flashService.setErrorAlert("Could not save the resource: ${resource.getErrors()}", true)
        }
        return resource
    }

    def getResourcesByResourceType(String resourceType) {
        List<Resource> resources = []

        if (resourceType) {
            resources = Resource.collection.find([resourceType: resourceType, active: true])?.toList()?.sort({it.nameEN})?.collect({it as Resource})
        }
        return resources
    }

    def getResourcesByResourceTypeObject(ResourceType resourceType) {
        List<Resource> resources = []

        if (resourceType) {
            if (resourceType.isSubType) {
                resources = getResourcesByResourceSubType(resourceType.resourceType, resourceType.subType)
            } else {
                resources = getResourcesByResourceType(resourceType.resourceType)
            }
        }
        return resources
    }

    def getResourcesByResourceSubType(String resourceType, String resourceSubType, Boolean excludeFromBenchmark = null, Map<String, String> ne = null) {
        List<Resource> resources = []

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            if (excludeFromBenchmark != null) {
                if (excludeFromBenchmark) {
                    query.put("excludeFromBenchmark", true)
                } else {
                    query.put("\$or", [["excludeFromBenchmark": null], ["excludeFromBenchmark": false]])
                }
            }

            if (ne) {
                ne.each { String key, String value ->
                    query.put((key), [$ne: value])
                }
                resources = Resource.collection.find(query)?.collect({ it as Resource })
            } else {
                resources = Resource.collection.find(query)?.collect({ it as Resource })
            }
        }
        return resources?.sort({it.nameEN})
    }

    def getResourcesAsDocumentsByResourceSubType(String resourceType, String resourceSubType, Boolean excludeFromBenchmark = null, Map<String, String> ne = null) {
        List<Document> resources = []

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            if (excludeFromBenchmark != null) {
                if (excludeFromBenchmark) {
                    query.put("excludeFromBenchmark", true)
                } else {
                    query.put("\$or", [["excludeFromBenchmark": null], ["excludeFromBenchmark": false]])
                }
            }

            if (ne) {
                ne.each { String key, String value ->
                    query.put((key), [$ne: value])
                }
                resources = Resource.collection.find(query)?.toList()
            } else {
                resources = Resource.collection.find(query)?.toList()
            }
        }
        return resources
    }

    def getResourcesByResourceSubTypeAndResourceIds(String resourceType, String resourceSubType, Boolean excludeFromBenchmark = null, Map<String, String> ne = null, List<ObjectId> resourceIds) {
        List<Resource> resources = []

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            if (excludeFromBenchmark != null) {
                if (excludeFromBenchmark) {
                    query.put("excludeFromBenchmark", true)
                } else {
                    query.put("\$or", [["excludeFromBenchmark": null], ["excludeFromBenchmark": false]])
                }
            }
            if(resourceIds){
                query.put("_id",[$in: DomainObjectUtil.stringsToObjectIds(resourceIds)])
            }

            if (ne) {
                ne.each { String key, String value ->
                    query.put((key), [$ne: value])
                }
                resources = Resource.collection.find(query)?.collect({ it as Resource })
            } else {
                resources = Resource.collection.find(query)?.collect({ it as Resource })
            }
        }
        return resources?.sort({it.nameEN})
    }

    def findDuplicateDefaultProfiles() {
        BasicDBObject matchByResourceId = new BasicDBObject()
        matchByResourceId.append("\$match", [$and: [[defaultProfile: true, active: true]]])
        BasicDBObject group = new BasicDBObject()
        group.append("\$group", [_id: "\$resourceId", count: [$sum: 1]])
        BasicDBObject matchByCount = new BasicDBObject()
        matchByCount.append("\$match", [count: ["\$gt": 1]])
        List<Document> results = Resource.collection.aggregate(Arrays.asList(matchByResourceId, group, matchByCount))?.toList()

        if (results) {
            return Resource.collection.find([resourceId: [$in: results.collect({it.getString("_id")})], defaultProfile: true]).collect({ it as Resource })
        } else {
            return null
        }
    }

    def findFailingMassConversionFactor(Boolean active) {
        List<Resource> failing = []
        def resources = Resource.collection.find([unitForData: "m2", density: [$exists: true], "\$or": [[defaultThickness_mm: [$exists: true]], [defaultThickness_in: [$exists: true]]], active: active])?.collect({ it as Resource })
        resources?.each { Resource resource ->
            Double thickness = resource.defaultThickness_mm ?: resource.defaultThickness_in * 25.4
            Double massCheck = resource.density * (thickness / 1000)
            Double massConversionFactor = resource.massConversionFactor

            if (massConversionFactor) {
                Double min = massConversionFactor * 0.95
                Double max = massConversionFactor * 1.05

                if (massCheck < min || massCheck > max) {
                    failing.add(resource)
                }
            } else {
                failing.add(resource)
            }
        }
        log.info("Failing: ${failing.size()} from ${resources.size()}")
        return failing
    }

    def findFailingThicknessMatch() {
        List<Resource> failing = []
        List<Document> resources = Resource.collection.find([defaultThickness_mm: [$exists: true], defaultThickness_in: [$exists: true], active: true])?.toList()
        resources?.each { Document resource ->
            Double thickness_mm = (Double) resource.defaultThickness_mm
            Double thickness_in = (Double) resource.defaultThickness_in

            if (!thickness_mm.round(2).equals((thickness_in * 25.4).round(2))) {
                failing.add(resource as Resource)
            }
        }
        log.info("Failing: ${failing.size()} from ${resources.size()}")
        return failing
    }

    def findFailingDefaultProfile() {
        List<Resource> failing = []
        List<Document> resources = Resource.collection.find([defaultProfile: [$ne: true], active: true])?.toList()
        resources?.each { Document resource ->
            long count = Resource.collection.count([resourceId: resource.resourceId, defaultProfile: true, active: true])

            if (!count) {
                failing.add(resource as Resource)
            }
        }
        log.info("Failing: ${failing.size()} from ${resources.size()}")
        return failing
    }

    def getResourcesForCalculationFailCheck(String resourceType, String resourceSubType, Map projection) {
        List<Document> resources = []

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            if (projection) {
                resources = Resource.collection.find(query, projection)?.toList()
            } else {
                resources = Resource.collection.find(query)?.toList()
            }
        }
        return resources
    }

    def getResourceCountByResourceSubTypeAndDeviationThreshold(String resourceType, String resourceSubType, Boolean excludeFromBenchmark = null, Double threshold) {
        Integer amount = 0
        List<String> benchmarks = Constants.BENCHMARKS.keySet().toList()
        List<Map<String, Double>> deviationsMap = []

        benchmarks.each { String benchmark ->
            Map perse = [:]
            perse["benchmark.deviations." + benchmark] = [$gt: threshold]
            deviationsMap.add(perse)
        }

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            query.put("\$or", deviationsMap)

            amount = Resource.collection.countDocuments(query)?.intValue()
        }
        return amount
    }

    def getResourcesByResourceSubTypeAndDeviationThreshold(String resourceType, String resourceSubType, Double threshold) {
        List<Resource> resources
        List<String> benchmarks = Constants.BENCHMARKS.keySet().toList()
        List<Map<String, Double>> deviationsMap = []

        benchmarks.each { String benchmark ->
            Map perse = [:]
            perse["benchmark.deviations." + benchmark] = [$gt: threshold]
            deviationsMap.add(perse)
        }

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            query.put("\$or", deviationsMap)

            resources = Resource.collection.find(query)?.collect({ it as Resource })
        }
        return resources
    }


    def getResourcesByResourceSubTypeAndFactorThreshold(String resourceType, String resourceSubType, Double threshold) {
        List<Resource> resources
        List<String> benchmarks = Constants.BENCHMARKS.keySet().toList()
        List<Map<String, Double>> deviationsMap = []

        benchmarks.each { String benchmark ->
            Map perse = [:]
            perse["benchmark.factorDeviations." + benchmark] = [$gt: threshold]
            deviationsMap.add(perse)
        }

        if (resourceType && resourceSubType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)
            query.put("resourceSubType", resourceSubType)
            query.put("active", true)

            query.put("\$or", deviationsMap)

            resources = Resource.collection.find(query)?.collect({ it as Resource })
        }
        return resources
    }

    def getResourcesByPrivateDatasetAccountId(String privateDatasetAccountId) {
        List<Resource> resources = []
        if (privateDatasetAccountId) {
            MongoCursor mongoCursor = Resource.collection.find([privateDatasetAccountId: privateDatasetAccountId])?.sort([nameEN: 1])?.iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.TRUE)
                }))
            }
            return resources
        }
    }

    def getResourcesCountByResourceType(String resourceType, String resourceSubType = null) {
        Integer resources

        if (resourceType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)

            if (resourceSubType) {
                query.put("resourceSubType", resourceSubType)
            }
            query.put("active", true)

            resources = Resource.collection.count(query)?.intValue()
        }
        return resources
    }

    def getResourceCountForNestedFilters(String resourceType, String resourceSubType = null, Map<String, List<Object>> resourceFilterCriteria = null,
                                         Map<String, String> userGivenFilters = null, Boolean alsoNonDefaults = Boolean.FALSE) {
        Integer resources

        if (resourceType) {
            BasicDBObject query = new BasicDBObject()
            query.put("resourceType", resourceType)

            if (resourceSubType) {
                query.put("resourceSubType", resourceSubType)
            }
            query.put("active", true)

            if (resourceFilterCriteria) {
                resourceFilterCriteria.each { String attribute, List<Object> requiredValues ->
                    query.put(attribute, [$in: requiredValues])
                }
            }
            if (userGivenFilters && !userGivenFilters.isEmpty()) {

                userGivenFilters.each { String key, String value ->
                    if (key.startsWith("quintiles")) {
                        String connectedBenchmark = StringUtils.substringAfter(key, "quintiles")

                        if (connectedBenchmark && value.isNumber()) {
                            query["benchmark.quintiles." + connectedBenchmark] = Integer.valueOf(value)
                        }
                    } else {
                        query[key] = value
                    }
                }
            }

            if (!alsoNonDefaults) {
                query.put("defaultProfile", true)
            }
            resources = Resource.collection.countDocuments(query)?.intValue()
        }
        return resources ? resources : 0
    }

    def getAdditionalQuestionResources(List<String> resourceGroups, List<String> skipResourceTypes, List<String> requiredEnvironmentDataSourceStandards,
                                       String queryString = null, Indicator indicator = null, String queryId = null, Boolean applyIndicatorResourceFilterCriteria = false,
                                       List<String> allowResourceTypes = null) {
        Map projection = ["resourceId": 1, "profileId": 1, "areas": 1, "nameEN": 1, "nameFI": 1, "nameFR": 1, "nameDE": 1, "nameNO": 1, "nameNL": 1, "nameES": 1, "nameSE": 1, "nameIT": 1,  "nameHU": 1,  "nameJP": 1,
                          "active": 1, "staticFullName": 1, "defaultProfile": 1, "resourceGroup": 1, "isoCodesByAreas": 1, "privateDatasetAccountId": 1]
        String localizedName = "name" + DomainObjectUtil.mapKeyLanguage
        List<Document> resources

        if (resourceGroups || skipResourceTypes || allowResourceTypes) {
            BasicDBObject query = new BasicDBObject()

            List<String> groups = []
            List<String> types = []
            List<String> allowedTypes = []
            List<String> standards = []

            if (resourceGroups) {
                groups.addAll(resourceGroups)
            }

            if (allowResourceTypes) {
                allowedTypes.addAll(allowResourceTypes)
            }

            if (skipResourceTypes) {
                types.addAll(skipResourceTypes)
            }

            if (requiredEnvironmentDataSourceStandards) {
                standards.addAll(requiredEnvironmentDataSourceStandards)
            }

            if (indicator && applyIndicatorResourceFilterCriteria && queryId) {
                def resourceFilterCriteria = indicator.indicatorQueries?.find({queryId == it.queryId})?.resourceFilterCriteria

                if (resourceFilterCriteria) {
                    resourceFilterCriteria.each { String filter, List<Object> values ->
                        if (filter == IndicatorQuery.RESOURCE_GROUP_FILTER) {
                            groups.addAll(values)
                        } else if (filter == IndicatorQuery.RESOURCE_TYPE_FILTER) {
                            allowedTypes.addAll(values)
                        } else if (filter == IndicatorQuery.ENVIRONMENT_DATA_SOURCE_STANDARD_FILTER) {
                            standards.addAll(values)
                        } else {
                            query.put((filter), [$in: values])
                        }
                    }
                }
            }

            if (groups) {
                query.put("resourceGroup", [$in: groups])
            }

            if (types && allowedTypes) {
                query.put("\$and", [[resourceType: [$nin: types]], ["\$or": [[resourceType: [$in: allowedTypes]],[resourceSubType: [$in: allowedTypes]]]]])
            } else if (types) {
                query.put("resourceType", [$nin: types])
            } else if (allowedTypes) {
                query.put("\$and", [["\$or": [[resourceType: [$in: allowedTypes]],[resourceSubType: [$in: allowedTypes]]]]])
            }

            if (standards) {
                query.put("environmentDataSourceStandard", [$in: standards])
            }

            if (queryString) {
                String staticFullName = queryString
                queryString = getNormalizedQueryString(queryString)
                List<String> userQuery = queryString.tokenize(" ")

                if (userQuery.size() > 1) {
                    BasicDBObject searchString = new BasicDBObject()

                    userQuery.each { String s ->
                        List<Map> existing = searchString.get("\$and")
                        if (existing) {
                            existing.add(["searchString": [$regex: ".*\\Q${s}\\E.*"]])
                        } else {
                            existing = [["searchString": [$regex: ".*\\Q${s}\\E.*"]]]
                        }
                        searchString.put("\$and", existing)
                    }
                    query.put("\$or", [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]])
                } else {
                    query.put("\$or", [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]])
                }
            }
            List<Document> allResources = Resource.collection.find(query, projection)?.toList()

            List<Document> actives = []
            List<Document> inactives = []

            allResources?.each { Document r ->
                r["localizedName"] = r.get(localizedName) ?: r.get("nameEN")

                if (r.active) {
                    actives.add(r)
                } else {
                    inactives.add(r)
                }
            }

            List<String> activeResourceIds = actives.collect({ it.resourceId })

            inactives.each { Document inactive ->
                if (!activeResourceIds.contains(inactive.resourceId)) {
                    actives.add(inactive)
                }
            }
            resources = actives
        }
        return resources?.sort({ it.get("localizedName") })
    }

    def getResourcesByResourceGroupsAndSkipResourceTypes(List<String> resourceGroups, List<String> skipResourceTypes,
                                                         Boolean alsoNonDefaults = Boolean.FALSE, Integer limit = null,
                                                         Integer skip = null, String queryString = null, Boolean alsoInactive = Boolean.FALSE,
                                                         List<String> allowResourceTypes = null) {
        List<Resource> resources = []
        MongoCursor mongocursor

        if (queryString) {
            queryString = getNormalizedQueryString(queryString)
        }

        if (resourceGroups || skipResourceTypes || allowResourceTypes) {
            BasicDBObject query = new BasicDBObject()

            if (resourceGroups) {
                query.put("resourceGroup", [$in: resourceGroups])
            }

            if (skipResourceTypes) {
                query.put("resourceType", [$nin: skipResourceTypes])
            } else if (allowResourceTypes) {
                query.put("\$or", [[resourceType: [$in: allowResourceTypes]], [resourceSubType: [$in: allowResourceTypes]]])
            }

            if (!alsoInactive) {
                query.put("active", true)
            }

            if (!alsoNonDefaults) {
                query.put("defaultProfile", true)
            }

            if (queryString) {
                query.put("searchString", ["\$regex": ".*${queryString}.*"])
            }

            if (limit != null && skip != null) {
                mongocursor = Resource.collection.find(query)?.limit(limit)?.skip(skip)?.iterator()
            } else {
                mongocursor = Resource.collection.find(query)?.iterator()
            }

            if (mongocursor?.hasNext()) {
                resources.addAll(mongocursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources?.sort({ it.nameEN })
    }

    def getResourceAsDocument(String resourceId, String profileId, Map projection = null) {
        Document resource

        if (resourceId) {
            if (profileId) {
                if (projection) {
                    resource = Resource.collection.findOne([resourceId: resourceId, profileId: profileId, active: true], projection)
                } else {
                    resource = Resource.collection.findOne([resourceId: resourceId, profileId: profileId, active: true])
                }
            } else {
                if (projection) {
                    resource = Resource.collection.findOne([resourceId: resourceId, active: true], projection)
                } else {
                    resource = Resource.collection.findOne([resourceId: resourceId, active: true])
                }
            }
        }
        return resource
    }

    /**
     * This method didn't seem to be used and didn't match the scope of its method name, so I have expanded it for us
     * within carbon designer, provide it a list of ids and a projection map and it will return them as documents
     * @param resourceIds
     * @param alsoInactive
     * @param projection
     * @return
     */
    def getResourcesAsDocuments(List<String> resourceIds, Boolean alsoInactive = false, Map projection = null) {
        List<Document> resource

        if (resourceIds) {
            if (alsoInactive) {
                if (projection) {
                    resource = Resource.collection.find([resourceId: [$in: resourceIds]], projection)?.toList()
                } else {
                    resource = Resource.collection.findOne([resourceId: [$in: resourceIds]])?.toList()
                }
            } else {
                if (projection) {
                    resource = Resource.collection.find([resourceId: [$in: resourceIds], active: true], projection)?.toList()
                } else {
                    resource = Resource.collection.find([resourceId: [$in: resourceIds], active: true])?.toList()
                }
            }
        }
        return resource
    }

    def getResourcesAsMapsByResourceGroupsAndSkipResourceTypes(Entity entity, List<String> resourceGroups, List<String> skipResourceTypes,
                                                               Boolean alsoNonDefaults = Boolean.FALSE, Integer limit = null,
                                                               Integer skip = null, String queryString = null, Map<String, List<Object>> resourceFilterCriteria = null,
                                                               List<String> requiredEnvironmentDataSourceStandards = null, Map<String, String> filterResources = null,
                                                               Map<String, List<Object>> userGivenFilter = null, List<String> resourceType = null, List<String> subType = null,
                                                               List<String> privateDatasetAccountId = null, Boolean constructionPage = Boolean.FALSE, Boolean getSubTypesList = Boolean.FALSE,
                                                               String unit = null, Map<String, List<Object>> organisationResourceFilterCriteria = null, Boolean preventPrivateData = null,
                                                               Map<String, Map> blockAndWarningResourceFilterCriteria = null, Account userAccount = null, List<String> allowResourceTypes = null) {

        User user = userService.getCurrentUser(true)
        Map<String, String> favoriteMaterialIds = userAccount?.favoriteMaterialIdAndUserId
        Boolean failQuery = false

        List<Document> resourcesAsMaps = []
        String basicQueryId = queryService.getBasicQuery()?.queryId
        String entityCountry = entity?.datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "country" })?.answerIds?.get(0)
        String entityState = entity?.datasets?.find({ d -> d.queryId == basicQueryId && d.questionId == "state" })?.answerIds?.get(0)
        List<String> neighbouringCountries
        Map<String, List<Object>> filterForUser

        if (userGivenFilter) {
            filterForUser = new HashMap<String, List<Object>>(userGivenFilter)
        }

        if (entityCountry) {
            neighbouringCountries = (List<String>) Resource.collection.findOne([resourceId: entityCountry, active: true], [neighbouringCountries: 1])?.neighbouringCountries
        }
        // REL-304 deprecate nmd3ElementId (was removed from the projection map) (check commit)
        Map projection = ["unitForData"              : 1, "resourceId": 1, "profileId": 1, "commercialName": 1, "nameEN": 1, "nameFI": 1, "nameFR": 1, "nameDE": 1, "nameHU": 1, "nameJP": 1,
                          "nameNO"                   : 1, "nameNL": 1, "nameES": 1, "nameSE": 1, "nameIT": 1, "technicalSpec": 1, "manufacturer": 1, "multipleProfiles": 1, "epdProgram": 1, "epdNumber": 1,
                          "isoCodesByAreas"          : 1, "benchmark": 1, "privateDatasetAccountId": 1, "constructionId": 1, "brandImageId": 1, "areas": 1, "additionalArea": 1, "representativeArea": 1,
                          "environmentDataSourceType": 1, "constructionType": 1, "resourceType": 1, "resourceSubType": 1, "combinedUnits": 1, "environmentDataPeriod": 1, "isMultiPart": 1,
                          "applicationId"            : 1, "resourceGroup": 1, "dataProperties": 1, "enabledPurposes": 1, "resourceQualityWarning": 1, "multipart": 1, "nmdCategoryId": 1, "nmdElementId": 1,
                          "datasetType"              : 1, "upstreamDB": 1, "environmentDataSource": 1]
        BasicDBObject query = new BasicDBObject()

        if (constructionPage) {
            query.put("construction", [$exists: false])
        }

        if (preventPrivateData) {
            query.put("privateDataset", [$exists: false])
        } else {
            /*if (user?.superUser) {
                query.put("\$or", [[privateDatasetAccountId: [$exists: true]], [privateDataset: null]])
            } else */
            if (privateDatasetAccountId) {
                query.put("\$or", [[privateDatasetAccountId: [$in: privateDatasetAccountId]], [privateDataset: null]])
            } else {
                query.put("privateDataset", [$exists: false])
            }
        }

        if (resourceGroups) {
            query.put("resourceGroup", [$in: resourceGroups])
        }

        if (filterForUser && filterForUser.get("resourceType")) {
            if (resourceType) {
                resourceType.addAll(filterForUser.get("resourceType"))
            } else {
                resourceType = filterForUser.get("resourceType")
            }
            filterForUser.remove("resourceType")
        }

        if (filterForUser && filterForUser.get("resourceSubType")) {
            if (subType) {
                subType.addAll(filterForUser.get("resourceSubType"))
            } else {
                subType = filterForUser.get("resourceSubType")
            }
            filterForUser.remove("resourceSubType")
        }

        if (skipResourceTypes) {
            if (resourceType && subType) {
                query.put("\$and", [[resourceType: [$nin: skipResourceTypes]], [resourceSubType: [$nin: skipResourceTypes]], [resourceType: [$in: resourceType]], [resourceSubType: [$in: subType]]])
            } else if (resourceType) {
                query.put("\$and", [[resourceType: [$nin: skipResourceTypes]], [resourceType: [$in: resourceType]]])
                query.put("resourceSubType", [$nin: skipResourceTypes])
            } else if (subType) {
                query.put("\$and", [[resourceSubType: [$nin: skipResourceTypes]], [resourceSubType: [$in: subType]]])
                query.put("resourceType", [$nin: skipResourceTypes])
            } else {
                query.put("resourceType", [$nin: skipResourceTypes])
                query.put("resourceSubType", [$nin: skipResourceTypes])
            }
        } else {
            if (resourceType||subType) {
                if (!allowResourceTypes||(allowResourceTypes && ((resourceType && allowResourceTypes.containsAll(resourceType))||(subType && allowResourceTypes.containsAll(subType))))) {
                    if (resourceType) {
                        query.put("resourceType", [$in: resourceType])
                    }

                    if (subType) {
                        query.put("resourceSubType", [$in: subType])
                    }
                } else {
                    failQuery = true
                }
            } else if (allowResourceTypes) {
                query.put("\$and", [["\$or": [[resourceType: [$in: allowResourceTypes]],[resourceSubType: [$in: allowResourceTypes]]]]])
            }
        }
        query.put("active", true)

        if (failQuery) {
            // ResourceTypes / SubTypes missmatch with what is allowed for this question
            return resourcesAsMaps
        } else {
            if (!alsoNonDefaults) {
                query.put("defaultProfile", true)
            }

            if (resourceFilterCriteria && !resourceFilterCriteria.isEmpty()) {
                resourceFilterCriteria.each { String resourceAttribute, List<Object> requiredValues ->
                    query[resourceAttribute] = [$in: requiredValues]
                }
            }

            if (requiredEnvironmentDataSourceStandards && !requiredEnvironmentDataSourceStandards.isEmpty()) {
                query.put("environmentDataSourceStandard", [$in: requiredEnvironmentDataSourceStandards])
            }

            if (filterResources && !filterResources.isEmpty()) {
                filterResources.each { String key, String value ->
                    query[key] = value
                }
            }

            BasicDBObject areasQuery

            if (filterForUser && !filterForUser.isEmpty()) {
                filterForUser.each { String key, List<Object> value ->
                    if (key.startsWith("quintiles")) {
                        String connectedBenchmark = StringUtils.substringAfter(key, "quintiles")

                        if (connectedBenchmark && value?.first()?.toString()?.isNumber()) {
                            String quintileGetter = "benchmark.quintiles." + connectedBenchmark
                            query[quintileGetter] = Integer.valueOf(value.first().toString())
                        }
                    } else if ("areas".equals(key)) {
                        def areaFromFilterCriteria = query.get("areas")?.get("\$in")
                        List<Object> userAreasFilter

                        if (filterForUser.get("state")) {
                            if (value?.contains(entityCountry)) {
                                List states = filterForUser.get("state")
                                states.add(Constants.RESOURCE_LOCAL_AREA)
                                userAreasFilter = states
                            } else {
                                userAreasFilter = filterForUser.get("state")
                            }
                        } else if (entityState) {
                            userAreasFilter = [entityState, Constants.RESOURCE_LOCAL_AREA]
                        } else {
                            if (value?.contains(entityCountry)) {
                                List states = value
                                states.add(Constants.RESOURCE_LOCAL_AREA)
                                userAreasFilter = states
                            } else {
                                userAreasFilter = value
                            }
                        }

                        if (areaFromFilterCriteria && value && !value.isEmpty()) {
                            areasQuery = new BasicDBObject()
                            List mapperinos = []
                            areaFromFilterCriteria.each {
                                mapperinos.add([areas: [$all: [it, value.first()]]])
                            }
                            areasQuery.put("\$or", mapperinos)
                        } else {
                            query.put("areas", [$in: userAreasFilter])
                        }
                    } else if(!"state".equals(key)){
                        query[key] = [$in: value]
                    }
                }
            }

            boolean isQueryString = false
            if (queryString) {
                isQueryString = true
                String staticFullName = queryString
                queryString = getNormalizedQueryString(queryString)

                List<String> userQuery = queryString.tokenize(" ")

                // TODO: Write an actual query builder that makes sense and nuke this mess
                if (userQuery.size() > 1) {
                    BasicDBObject searchString = new BasicDBObject()

                    userQuery.each { String s ->
                        List<Map> existing = searchString.get("\$and")
                        if (existing) {
                            existing.add(["searchString": [$regex: ".*\\Q${s}\\E.*"]])
                        } else {
                            existing = [["searchString": [$regex: ".*\\Q${s}\\E.*"]]]
                        }
                        searchString.put("\$and", existing)
                    }

                    if (query.keySet()?.contains("\$or")) {
                        if (query.keySet()?.contains("\$and")) {
                            if (areasQuery) {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], [$and: query.get("\$and")], areasQuery])
                                query.remove("areas")
                            } else {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], [$and: query.get("\$and")]])
                            }
                        } else {
                            if (areasQuery) {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], [$or: query.get("\$or")], areasQuery])
                                query.remove("areas")
                            } else {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], [$or: query.get("\$or")]])
                            }
                            query.remove("\$or")
                        }
                    } else {
                        if (areasQuery) {
                            if (query.keySet()?.contains("\$and")) {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], [$and: query.get("\$and")], areasQuery])
                            } else {
                                query.put("\$and", [[$or: [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]]], areasQuery])
                            }
                            query.remove("areas")
                        } else {
                            query.put("\$or", [[$and: searchString.get("\$and")], ["staticFullName": staticFullName]])
                        }
                    }
                } else {
                    if (query.keySet()?.contains("\$or")) {
                        if (query.keySet()?.contains("\$and")) {
                            if (areasQuery) {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], [$and: query.get("\$and")], areasQuery])
                                query.remove("areas")
                            } else {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], [$and: query.get("\$and")]])
                            }
                        } else {
                            if (areasQuery) {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], [$or: query.get("\$or")], areasQuery])
                                query.remove("areas")
                            } else {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], [$or: query.get("\$or")]])
                            }
                            query.remove("\$or")
                        }
                    } else {
                        if (areasQuery) {
                            if (query.keySet()?.contains("\$and")) {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], [$and: query.get("\$and")], areasQuery])
                            } else {
                                query.put("\$and", [[$or: [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]]], areasQuery])
                            }
                            query.remove("areas")
                        } else {
                            query.put("\$or", [["searchString": [$regex: ".*\\Q${queryString}\\E.*"]], ["staticFullName": staticFullName]])
                        }
                    }
                }
            } else if (areasQuery) {
                if (query.keySet()?.contains("\$or")) {
                    if (query.keySet()?.contains("\$and")) {
                        query.put("\$and", [[$and: query.get("\$and")], areasQuery])
                    } else {
                        query.put("\$and", [[$or: query.get("\$or")], areasQuery])
                        query.remove("\$or")
                    }
                } else {
                    query.put("\$or", areasQuery.get("\$or"))
                }
                query.remove("areas")
            }

            Map<String, Integer> subTypeAndCount = [:]
            if (query && projection) {
                List<String> units = unitConversionUtil.getUnitAndItsEquivalent(unit)

                log.debug("Resource query: ${query}")
                resourcesAsMaps = Resource.collection.find(query, projection)?.toList()

                List<ProductDataList> userProductDataLists = []
                Boolean isLimitedLicense = false

                Map<String, Map<String, String>> customEPDNameAndListId = [:]
                if (organisationResourceFilterCriteria) {
                    List<String> licensedFeatureIds = []
                    accountService.getAccountsByIds(privateDatasetAccountId, ["licenseIds": 1])?.each { Account account ->
                        List<String> licIds = licenseService.getLicensedFeatureIdsFromAccount(account)
                        if (licIds) {
                            licensedFeatureIds.addAll(licIds)
                        }
                    }
                    boolean isUncapped = licenseService.hasEcoinventUncappedLicensed(licensedFeatureIds)

                    if (!isUncapped) {
                        isLimitedLicense = true
                        if (licensedFeatureIds?.find({ Feature.ECOINVENT_WITH_CAP.contains(it) }) || userService.isSystemAdmin(user)) {
                            List<ProductDataList> productDataLists = productDataListService.getProductDataListByAccountIds(privateDatasetAccountId)
                            List<ProductDataList> linkedDataLists = productDataListService.getLinkedDataListsFromAccountIds(privateDatasetAccountId)
                            if (productDataLists) {
                                userProductDataLists.addAll(productDataLists)
                            }
                            if (linkedDataLists) {
                                userProductDataLists.addAll(linkedDataLists)
                            }

                            Closure getResourceKey = { return "${it.resourceId}${it.profileId}".toString()}

                            List<String> allResourceIds = []
                            List<String> allProfileIds = []

                            Map<String, String> resourceByAlias = [:]
                            userProductDataLists.each { ProductDataList productDataList ->
                                productDataList?.datasets?.each { Dataset dataset ->
                                    allResourceIds.add(dataset.resourceId)
                                    allProfileIds.add(dataset.profileId)
                                    if(dataset.groupingDatasetName) {
                                        resourceByAlias.put(getResourceKey(dataset),
                                                getNormalizedQueryString(dataset?.groupingDatasetName))
                                    }
                                }
                            }

                            Map<String, Resource> matchedResources = [:]
                            getResources(allResourceIds, allProfileIds)?.each { Resource r ->
                                String normalizedLocalName = getNormalizedQueryString(getLocalizedName(r))
                                String key = getResourceKey(r)
                                String alias = resourceByAlias?.get(key)
                                if (null != queryString && (queryString?.contains(normalizedLocalName) ||
                                        normalizedLocalName?.contains(queryString) || alias?.contains(queryString))) {
                                    matchedResources.put(key, r)
                                }
                            }

                            userProductDataLists.each { ProductDataList productDataList ->
                                productDataList?.datasets?.each { Dataset dataset ->
                                    Map<String, String> customDatasetNameAndListId = [:]
                                    Resource matchedResource = matchedResources.get(getResourceKey(dataset))
                                    if (matchedResource && !resourcesAsMaps?.find({ it.resourceId.equals(dataset.resourceId) })) {
                                        Document r = Resource.collection.findOne(["_id":matchedResource.id])
                                        if(r) {
                                            resourcesAsMaps.add(r)
                                        }
                                    }
                                    if (dataset.groupingDatasetName) {
                                        if (isQueryString && dataset.groupingDatasetName.toLowerCase().contains(queryString)) {
                                            if(!resourcesAsMaps?.find({it.resourceId.equals(dataset.resourceId)})) {
                                                Document r = datasetService.getResourceAsDocument(dataset)
                                                resourcesAsMaps.add(r)
                                            }
                                        }
                                        customDatasetNameAndListId.put("customDatasetName", dataset.groupingDatasetName)
                                    }
                                    customDatasetNameAndListId.put("productDataListId", productDataList.id.toString())
                                    customEPDNameAndListId.put((dataset.resourceId), customDatasetNameAndListId)
                                }
                            }
                        }
                    }
                }

                if (resourcesAsMaps) {
                    String id
                    Iterator iterator = resourcesAsMaps.iterator()

                    while (iterator.hasNext()) {
                        Document it = iterator.next()
                        String filterResourceQualityWarning
                        //(REL2108-27) Added logic with displaying warn and block resources via indicator parameters
                        if (blockAndWarningResourceFilterCriteria?."${IndicatorQuery.WARNING_RESOURCE}" || blockAndWarningResourceFilterCriteria?."${IndicatorQuery.BLOCK_RESOURCE}") {
                            blockAndWarningResourceFilterCriteria.each{key, value ->
                                Map<String, List<Object>> propertiesWithoutWarningString = value.findAll{
                                    !(it.key in [IndicatorQuery.WARNING_MESSAGE_INDICATOR, IndicatorQuery.BLOCK_MESSAGE_INDICATOR])
                                }

                                if (resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(null, it,
                                        propertiesWithoutWarningString, true)) {
                                    it.showWarning = true
                                    //If resource has both warn and block, then block will be performed first in formatAutocompleteRows JS action
                                    if (key == IndicatorQuery.BLOCK_RESOURCE){
                                        filterResourceQualityWarning = Resource.QUALITY_WARNING_BLOCK_TYPE
                                    } else {
                                        filterResourceQualityWarning = Resource.QUALITY_WARNING_WARN_TYPE
                                    }
                                }
                            }
                        }

                        if (filterResourceQualityWarning &&
                                (filterResourceQualityWarning == Resource.QUALITY_WARNING_BLOCK_TYPE || !it.resourceQualityWarning)){
                            it.resourceQualityWarning = filterResourceQualityWarning
                        }

                        Map<String, String> customListEPD = customEPDNameAndListId?.get(it.resourceId)

                        if (customListEPD) {
                            it.productDataListId = customListEPD.get("productDataListId")

                            if (customListEPD.get("customDatasetName")) {
                                it.customDatasetName = customListEPD.get("customDatasetName")
                            }
                        }

                        if (id?.equals(it.resourceId)) {
                            iterator.remove()
                        } else if (isLimitedLicense && !it.productDataListId && resourceFilterCriteriaUtil.resourceOkAgainstFilterCriteria(null, it, organisationResourceFilterCriteria)) {
                            iterator.remove()
                        } else if (!units || (units.contains(it.unitForData?.toString()?.toLowerCase()) || (it.combinedUnits && CollectionUtils.containsAny(units, it.combinedUnits.collect({ it.toString().toLowerCase() }))))) {
                            id = it.resourceId

                            if (getSubTypesList) {
                                Integer existing = subTypeAndCount.get(it.resourceSubType)
                                if (existing) {
                                    existing = existing + 1
                                } else {
                                    existing = 1
                                }
                                subTypeAndCount.put((String) it.resourceSubType, existing)
                            } else {
                                List<String> areas = (List<String>) it.areas
                                if (areas) {
                                    if (areas.contains(entityCountry) || areas.contains(Constants.RESOURCE_LOCAL_AREA) || areas.contains(entityState)) {
                                        if (Constants.GENERIC_EDST.equals(it.environmentDataSourceType)) {
                                            it["weight"] = 5
                                        } else {
                                            it["weight"] = 4
                                        }
                                    } else if (neighbouringCountries && CollectionUtils.containsAny(neighbouringCountries, areas)) {
                                        if (Constants.GENERIC_EDST.equals(it.environmentDataSourceType)) {
                                            it["weight"] = 3
                                        } else {
                                            it["weight"] = 2
                                        }
                                    } else if (Constants.GENERIC_EDST.equals(it.environmentDataSourceType)) {
                                        it["weight"] = 1
                                    } else {
                                        it["weight"] = 0
                                    }
                                } else if (Constants.GENERIC_EDST.equals(it.environmentDataSourceType)) {
                                    it["weight"] = 1
                                } else {
                                    it["weight"] = 0
                                }
                                if(favoriteMaterialIds && favoriteMaterialIds?.containsKey(it.resourceId + '.'+ it.profileId)){
                                    it["weight"] = 8
                                }
                            }
                        } else {
                            iterator.remove()
                        }
                    }
                }
            }

            if (getSubTypesList) {
                resourcesAsMaps = []
                if (subTypeAndCount) {
                    subTypeAndCount = subTypeAndCount.sort({ a, b -> b.value <=> a.value })
                    List<Document> subTypes = resourceTypeService.getSubTypeDocumentsBySubTypes(subTypeAndCount.keySet().toList())

                    if (subTypes) {
                        subTypeAndCount.each { String key, Integer value ->
                            if (key) {
                                Document subTypeDoc = subTypes.find({key.equals(it.subType)})

                                if (subTypeDoc) {
                                    subTypeDoc["count"] = value
                                    resourcesAsMaps.add(subTypeDoc)
                                }
                            }
                        }
                    }
                }
                return resourcesAsMaps
            } else {
                return resourcesAsMaps?.sort({a,b -> -a?.weight <=> -b?.weight ?: a?.areas?.contains(entityState) <=> b?.areas?.contains(entityState) ?: a?.nameEN <=> b?.nameEN})
            }
        }
    }

    private static String getNormalizedQueryString(String queryString) {
        if (queryString) {
            return Normalizer.normalize(
                    queryString
                            .replaceAll(",", "")?.toLowerCase(), Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .replaceAll("[^a-zA-Z0-9 ]+", "")
        }
        return queryString
    }

    def getResourcesTypesResourceGroupsAndSkipResourceTypesAsDocuments(Map<String, List<Object>> resourceFilterCriteria, Boolean constructionPage, List<String> questionsResourceGroups,
                                                                       List<String> questionsSkipResourceTypes, Boolean productDataListPage, List<String> questionsAllowResourcetypes) {
        List<Document> resourceTypes

        if (resourceFilterCriteria||questionsResourceGroups||constructionPage||productDataListPage) {
            List<String> resourceTypeStrings = getUniqueResourceAttributeValues(questionsResourceGroups, questionsSkipResourceTypes, "resourceType", resourceFilterCriteria, false, questionsAllowResourcetypes)

            if (resourceTypeStrings) {
                resourceTypes = ResourceType.collection.find([resourceType: [$in: resourceTypeStrings], active: true], [resourceType: 1, subType: 1, nameEN: 1, nameFI: 1,
                                                                                                          nameNO: 1, nameDE: 1, nameFR: 1, nameNL: 1, nameES: 1, nameSE: 1, nameIT:1 , "nameHU": 1, "nameJP": 1, linkedSubTypes:1, multipart:1])?.toList()
            }
        }
        return resourceTypes
    }

    def getUniqueResourceAttributeValues(List<String> resourceGroups, List<String> skipResourceTypes,
                                         String resourceAttribute, Map<String, List<Object>> resourceFilterCriteria, boolean findState = false,
                                         List<String> allowResourcetypes = null) {
        List<String> resourceAttributeValues
        List<String> resourceAttributeValuesForLinkedStypes
        List<String> resAttr
        if (resourceAttribute) {
            BasicDBObject filter = new BasicDBObject()
            BasicDBObject filterForResType = new BasicDBObject()
            filter.put(resourceAttribute, [$ne: null])
            filterForResType.put(resourceAttribute, [$ne: null])
            if(!findState){
                filter.put("isState", [$ne: true])
            }

            if (resourceFilterCriteria) {
                resourceFilterCriteria.each { String attribute, List<Object> requiredValues ->
                    filter.put(attribute, [$in: requiredValues])
                }
            }
            filter.put("active", true)
            filterForResType.put("active",true)
            if (resourceGroups) {
                filter.put("resourceGroup", [$in: resourceGroups])
            }

            if (skipResourceTypes) {
                filter.put("resourceType", [$nin: skipResourceTypes])
                filterForResType.put("resourceType", [$nin: skipResourceTypes])
            } else if (allowResourcetypes) {
                filter.put("\$or", [[resourceType: [$in: allowResourcetypes]],[resourceSubType: [$in: allowResourcetypes]]])
                filterForResType.put("\$or", [[resourceType: [$in: allowResourcetypes]],[resourceSubType: [$in: allowResourcetypes]]])
            }
            resourceAttributeValues = Resource.collection.distinct(resourceAttribute, filter, String.class).toList()
            //0014969
            //some resourceTypes might not have any resources linked to it. But allow it if there is any linkedResourceTypes and those (subtypes) have resources
            if("resourceType".equals(resourceAttribute)) {
                filterForResType.put("linkedSubTypes", [$exists: true])
                resAttr = ResourceType.collection.distinct("linkedSubTypes", filterForResType, String.class).toList()
                filterForResType.clear()
                filterForResType.put("resourceSubType", [$in: resAttr])
                resourceAttributeValuesForLinkedStypes = Resource.collection.distinct("resourceSubType", filterForResType, String.class).toList()
                filterForResType.clear()
                filterForResType.put("linkedSubTypes", [$in: resourceAttributeValuesForLinkedStypes])
                resAttr = ResourceType.collection.distinct(resourceAttribute,filterForResType, String.class).toList()
                resAttr?.each { String r ->
                       if(!resourceAttributeValues?.contains())  {
                               resourceAttributeValues.add(r)
                           }
                        }
                }
        }
        return resourceAttributeValues?.sort({ it.toLowerCase() })
    }

    def getResourceCountryAsList() {
        List<String> country = []
        List<Document> countriesAsDoc = Resource.collection.find([resourceGroup: [$in: ["world", "localCompensation"]], active: true, isState: [$exists: false], areas: [$exists: true], isoCountryCode: [$exists: true]], [resourceId: 1])?.toList()
        country = countriesAsDoc?.collect({it.resourceId})

        return country
    }

    def getStateResourceCountryAsMap() {
        Map<String, List<Document>> stateCountryMap = [:]
        List<Document> countryWithStateAsDoc = Resource.collection.find([showState: [$exists: true], active: true], [resourceId: 1, resourceGroup: 1, showState:1])?.toList()
        Map<String, String> countryAndResourceGroup = countryWithStateAsDoc?.collectEntries({
            [(it.resourceId): it.showState]
        })
        countryAndResourceGroup?.each { String k,String v ->
            List<Document> stateByCountry = Resource.collection.find([resourceGroup: v, active: true, isState: true], [resourceId: 1, nameEN: 1, nameFI: 1,
                                                                                                                              nameNO    : 1, nameDE: 1, nameFR: 1, nameNL: 1, nameES: 1, nameSE: 1, nameIT: 1, "nameHU": 1, "nameJP": 1])?.toList()
            if (stateByCountry) {
                stateByCountry?.sort({it.nameEN})
                stateCountryMap.put(k, stateByCountry)
            }
        }
        return stateCountryMap
    }
    def getCountryAsResourceByResrouceGroup(List<String> resourceGroup){
        Resource country
        if(resourceGroup){
            country = Resource.collection.findOne([showState: [$in: resourceGroup], active: true]) as Resource
        }
        return country
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourcesByResourceGroup(List<String> resourceGroup, String applicationId = null, Boolean noConstructions = null) {
        List<Resource> resources = []

        if (resourceGroup) {
            BasicDBObject filter = new BasicDBObject()
            filter.put("active", true)
            filter.put("resourceGroup", [$in: resourceGroup])

            if (applicationId) {
                filter.put("applicationId", applicationId)
            }

            if (noConstructions) {
                filter.put("constructionId", [$exists:false])
            }
            resources = Resource.collection.find(filter)?.toList()?.collect({it as Resource})
        }
        return resources
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourcesBySkipResourceGroup(List<String> resourceGroup, String applicationId = null, Boolean noConstructions = null) {
        List<Resource> resources = []

        if (resourceGroup) {
            BasicDBObject filter = new BasicDBObject()
            filter.put("active", true)
            filter.put("resourceGroup", [$nin: resourceGroup])

            if (applicationId) {
                filter.put("applicationId", applicationId)
            }

            if (noConstructions) {
                filter.put("constructionId", [$exists:false])
            }
            resources = Resource.collection.find(filter)?.toList()?.collect({it as Resource})
        }
        return resources
    }

    def getResourcesByApplicationId(String applicationId) {
        List<Resource> resources = []

        if (applicationId) {
            MongoCursor mongoCursor = Resource.collection.find(["applicationId": applicationId]).iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources?.sort({ it.resourceId })
    }

    def getResourcesAsDocumentsByApplicationId(String applicationId) {
        List<Document> resources = []

        if (applicationId) {
            Resource.collection.find(["applicationId": applicationId, active: true])?.toList()?.each { Document d ->
                d.localizedName = getLocalizedNameFromDocument(d)
                resources.add(d)
            }
        }
        return resources?.sort({ it.resourceId })
    }

    def getResourcesAsDocumentsByQuery(BasicDBObject query) {
        List<Document> resources = []

        if (query) {
            Resource.collection.find(query)?.toList()?.each { Document d ->
                d.localizedName = getLocalizedNameFromDocument(d)
                resources.add(d)
            }
        }
        return resources?.sort({ it.resourceId })
    }

    def String getLocalizedNameFromDocument(Document d) {
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

    def getResourcesWithAttributeExists(String attributeName) {
        List<Resource> resources = []

        if (attributeName) {
            MongoCursor mongoCursor = Resource.collection.find([(attributeName): ["\$exists": true],
                                                                (attributeName): ["\$ne": null], active: true]).iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources?.sort({ it.resourceId })
    }

    def getResourcesWithAttributeNotExists(String attributeName) {
        List<Resource> resources = []

        if (attributeName) {
            MongoCursor mongoCursor = Resource.collection.find([active: true, "\$or": [[(attributeName): ["\$exists": false]],
                                                                                       [(attributeName): null]]]).iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources?.sort({ it.resourceId })
    }

    def getResourcesByAttributeNameAndValue(String attributeName, Object value) {
        List<Resource> resources = []

        if (attributeName && value) {
            MongoCursor mongoCursor = Resource.collection.find([(attributeName): value, active: true]).iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources
    }

    def getResourcesByAttributeNameAndListValue(String attributeName, List values) {
        List<Resource> resources = []

        if (attributeName && values) {
            BasicDBObject query = new BasicDBObject()
            query.put(attributeName, [$all: values])
            query.put("active", true)
            def results = Resource.collection.find(query)

            if (results) {
                resources = results.collect({ it as Resource })
            }
        }
        return resources
    }

    def getResourcesByResourceGroupAndApplicationId() {
        Map<String, Map> resourcesByApplicationId = new TreeMap<String, Map>()
        List<String> applicationIds = applicationService.getAllApplicationApplicationIds()
        List<String> allResourceGroups = getAllResourceGroups()


        if (applicationIds && allResourceGroups) {
            def allResources = getAllResources()

            applicationIds?.each { String applicationId ->
                List<Resource> resourcesByAppId = getResourcesByApplicationId(applicationId)

                if (resourcesByAppId) {
                    allResourceGroups?.each { String resourceGroup ->
                        List<Resource> resourcesByGroup = resourcesByAppId?.findAll({
                            it.resourceGroup?.contains(resourceGroup)
                        })

                        if (resourcesByGroup) {
                            Map existing = resourcesByApplicationId.get(applicationId)

                            if (existing) {
                                existing.put(resourceGroup, resourcesByGroup.size())
                            } else {
                                existing = [(resourceGroup): resourcesByGroup.size()]
                            }
                            resourcesByApplicationId.put(applicationId, existing)
                        }

                    }
                }
            }
            applicationIds.each { String applicationId ->
                List<Resource> unlinkedResources = allResources?.findAll({ !it.applicationId })

                allResourceGroups?.each { String resourceGroup ->
                    List<Resource> resourcesByGroup = unlinkedResources?.findAll({
                        it.resourceGroup?.contains(resourceGroup)
                    })

                    if (resourcesByGroup) {
                        TreeMap<String, Integer> unlinked = new TreeMap<String, Integer>()

                        unlinked.put(resourceGroup, resourcesByGroup.size())

                        resourcesByApplicationId.put("unlinked", unlinked)
                    }
                }
            }
        }
        return resourcesByApplicationId
    }

    def getResourcesByDataProperties() {
        Map<List<String>, List<Resource>> resourcesWithDataProperties = new LinkedHashMap<List<String>, List<Resource>>()
        List<Resource> resources = []
        MongoCursor mongocursor = Resource.collection.find(["dataProperties": ["\$exists": true, "\$ne": null], "active": true]).iterator()

        if (mongocursor?.hasNext()) {
            resources.addAll(mongocursor.toList().collect({ Document document ->
                documentAsResource(document, Boolean.FALSE)
            })
            )
            def dataProperties = resources.findAll({ it.dataProperties })?.collect({ it.dataProperties })?.unique()

            dataProperties.each { List<String> dataProperty ->
                resourcesWithDataProperties.put(dataProperty, resources.findAll({
                    dataProperty.equals(it.dataProperties)
                }))
            }
            mongocursor = Resource.collection.find(["\$or": [["dataProperties": ["\$exists": false]], ["dataProperties": null]], "active": true]).iterator()

            if (mongocursor?.hasNext()) {
                resourcesWithDataProperties.put("unlinked", mongocursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resourcesWithDataProperties
    }

    def getResourceDocumentsByDataProperties() {
        Map<List<String>, List<Document>> resourcesWithDataProperties = [:]
        List<Document> resources = Resource.collection.find(["dataProperties": ["\$exists": true, "\$ne": null], "active": true], [dataProperties: 1])?.toList()

        resources?.each { Document resource ->
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
        return resourcesWithDataProperties
    }

    def getResourceDocumentsByEnabledPurposes() {
        Map<List<String>, List<Document>> resourcesWithEnabledPurposes = [:]
        List<Document> resources = Resource.collection.find(["enabledPurposes": ["\$exists": true, "\$ne": null], "active": true], [enabledPurposes: 1])?.toList()

        resources?.each { Document resource ->
            List<String> enabledPurposes = (List<String>) resource.enabledPurposes
            enabledPurposes.each { String enabledPurpose ->
                List<Document> existing = resourcesWithEnabledPurposes.get(enabledPurpose)

                if (existing) {
                    existing.add(resource)
                } else {
                    existing = [resource]
                }
                resourcesWithEnabledPurposes.put(enabledPurpose, existing)
            }
        }
        return resourcesWithEnabledPurposes
    }

    def getResourceCountsByArea() {
        Map<String, Integer> countOfResourcesByCountry = [:]
        List<String> countries = getAllAreas()
        countries?.each { String country ->
            if (country) {
                Integer size = Resource.collection.count([applicationId: "LCA", areas: country, active: true, privateDataset: [$ne: true]])?.intValue()

                if (size != null) {
                    countOfResourcesByCountry.put(country, size)
                }
            }
        }
        Integer unlinked = Resource.collection.count([applicationId: "LCA", active: true, $or: [[areas: [$exists: false]], [areas: null]], privateDataset: [$ne: true]])?.intValue() ?: 0
        countOfResourcesByCountry.put("unlinked", unlinked)
        return countOfResourcesByCountry
    }

    def getResourceCountsByUpstreamDBs() {
        Map<String, Integer> countOfResourcesByUpstreamDB = [:]
        List<String> upstreamDBs = getAllUpstreamDBs()?.sort({ it.toLowerCase() })

        upstreamDBs?.each { String upstreamDB ->
            countOfResourcesByUpstreamDB.put(upstreamDB, Resource.collection.count([applicationId: "LCA", upstreamDB: upstreamDB, active: true])?.intValue() ?: 0)
        }
        Integer unlinked = Resource.collection.count([applicationId: "LCA", active: true, $or: [[upstreamDB: [$exists: false]], [upstreamDB: null]]])?.intValue() ?: 0
        countOfResourcesByUpstreamDB.put("unlinked", unlinked)
        return countOfResourcesByUpstreamDB
    }

    def getDatabaseKPIs(Date dateLimit = null) {
        Map<String, Integer> databaseKPIs = [:]
        Integer bmatBtech = getMaterialAndBuildingTechnologyCount(dateLimit)?.intValue() ?: 0
        Integer utility = getUtilityResourcesCount(dateLimit)?.intValue() ?: 0
        Integer other = getOtherCount(dateLimit)?.intValue() ?: 0
        Integer bionovaConstructions = getBionovaConstruction(dateLimit)?.intValue() ?: 0

        Integer lcc = getLccResourcesCount(dateLimit)?.intValue() ?: 0

        databaseKPIs.put("LCA-BMAT", getMaterialResourcesCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("LCA-BTECH", getBuildingTechnologyCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("LCA-OTHER", other)
        databaseKPIs.put("BMAT+BTECH", bmatBtech)
        databaseKPIs.put("LCA-utility", utility)
        databaseKPIs.put("LCC", lcc)
        databaseKPIs.put("costStructures", CostStructure.collection.count()?.intValue() ?: 0)
        databaseKPIs.put("bionovaConstructions", bionovaConstructions)
        databaseKPIs.put("otherConstructions", getOtherConstruction(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("generic", getGenericDatapointsCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("private", getPrivateDatapointsCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("inactive", getInactiveDatapointsCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("LCA-Ecoinvent", getEcoinventDatapointsCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("staticDBs", getStaticDatabasesCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("total", getAllResourcesCount(dateLimit)?.intValue() ?: 0)
        databaseKPIs.put("totalPartial", bmatBtech + utility + bionovaConstructions + other + lcc)
        return databaseKPIs.sort({-it.value})
    }
    def getBionovaConstruction(Date dateLimit = null){
        if(dateLimit){
            def constructionList = Construction.collection.find([$or: [[privateConstructionCompanyName: [$in: ["Bionova Ltd", ""]]], [privateConstructionCompanyName: null]]],["mirrorResourceId":1])
            List resourceIds = constructionList.collect({it.mirrorResourceId})
            if(resourceIds){
                Integer newNumber = Resource.collection.count([resourceId: [$in:resourceIds],firstUploadTime: [$gte: dateLimit], active: true])?.intValue() ?: 0
                Integer deactNumber = Resource.collection.count([resourceId: [$in:resourceIds],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
                return newNumber - deactNumber
            } else {
                return 0
            }
        } else {
            return Construction.collection.count([locked:true, $or: [[privateConstructionCompanyName: [$in: ["Bionova Ltd", ""]]], [privateConstructionCompanyName: null]]])
        }
    }
    def getLccResourcesCount(Date dateLimit = null){
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCC","firstUploadTime":[$gte: dateLimit], privateDataset: [$ne:true], active: true, constructionId: [$exists:false]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCC","deactivateTime":[$gte: dateLimit], privateDataset: [$ne:true], active: false, constructionId: [$exists:false]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCC", active: true, constructionId: [$exists:false]])
        }
    }

    def getOtherConstruction(Date dateLimit = null){
        if(dateLimit){
            def constructionList = Construction.collection.find([$and: [[privateConstructionCompanyName: [$nin: ["Bionova Ltd", ""]]], [privateConstructionCompanyName: [$exists: true]]] ],["mirrorResourceId":1])
            List resourceIds = constructionList.collect({it.mirrorResourceId})
            if(resourceIds){
                Integer newNumber = Resource.collection.count([resourceId: [$in:resourceIds],firstUploadTime: [$gte: dateLimit], active: true])?.intValue() ?: 0
                Integer deactNumber = Resource.collection.count([resourceId: [$in:resourceIds],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
                return newNumber - deactNumber
            } else {
                return 0
            }
        } else {
            return Construction.collection.count([locked:true, $and: [[privateConstructionCompanyName: [$nin: ["Bionova Ltd", ""]]], [privateConstructionCompanyName: [$exists: true]]] ])
        }
    }
    def getAllResourcesCount(Date dateLimit = null){
        if(dateLimit){
            Integer newNumber = Resource.collection.count([active: true,firstUploadTime: [$gte: dateLimit]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([active: false,deactivateTime: [$gte: dateLimit]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([active: true])
        }
    }

    def getMaterialResourcesCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", active: true, resourceGroup: [$in: ["BMAT"]], constructionId: [$exists:false],"firstUploadTime":[$gte: dateLimit], privateDataset: [$ne:true]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA", active: false, resourceGroup: [$in: ["BMAT"]], constructionId: [$exists:false],"deactivateTime":[$gte: dateLimit], privateDataset: [$ne:true]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA", active: true, resourceGroup: [$in: ["BMAT"]], constructionId: [$exists:false]])
        }
    }

    def getStaticDatabasesCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([epdProgram: [$in: ["IMPACT","OKOBAUDAT","Quartz","KBOB","ZAG","Earthsure","FPInnovations"]], active: true, constructionId: [$exists:false],firstUploadTime: [$gte: dateLimit]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([epdProgram: [$in: ["IMPACT","OKOBAUDAT","Quartz","KBOB","ZAG","Earthsure","FPInnovations"]], active: false, constructionId: [$exists:false],deactivateTime: [$gte: dateLimit]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([epdProgram: [$in: ["IMPACT","OKOBAUDAT","Quartz","KBOB","ZAG","Earthsure","FPInnovations"]], active: true, constructionId: [$exists:false]])
        }
    }

    def getGenericDatapointsCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", areas: "LOCAL", active: true,firstUploadTime: [$gte: dateLimit]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA", areas: "LOCAL", active: false,deactivateTime: [$gte: dateLimit]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA", areas: "LOCAL", active: true])
        }
    }

    def getEcoinventDatapointsCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", areas: "LOCAL", active: true,firstUploadTime: [$gte: dateLimit]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA", areas: "LOCAL", active: false,deactivateTime: [$gte: dateLimit]])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA-Ecoinvent", active: true, constructionId: [$exists:false]])
        }
    }

    def getInactiveDatapointsCount(Date dateLimit = null) {
        if(dateLimit){
            return Resource.collection.count([active: false,deactivateTime: [$gte: dateLimit]])?.intValue() ?: 0
        } else {
            return Resource.collection.count([active: false])
        }
    }

    def getPrivateDatapointsCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([environmentDataSourceType: "private",firstUploadTime: [$gte: dateLimit], active: true])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([environmentDataSourceType: "private",deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([environmentDataSourceType: "private", active: true])
        }
    }

    def getUtilityResourcesCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA-utility", active: true,firstUploadTime: [$gte: dateLimit],privateDataset: [$ne: true], constructionId: [$exists:false]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA-utility",privateDataset: [$ne: true], constructionId: [$exists:false],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA-utility", active: true, constructionId: [$exists:false]])
        }
    }

    def getBuildingTechnologyCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", active: true,firstUploadTime: [$gte: dateLimit],privateDataset: [$ne: true], resourceGroup: [$in: ["buildingTechnology"]], constructionId: [$exists:false]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA", privateDataset: [$ne: true], resourceGroup: [$in: ["buildingTechnology"]], constructionId: [$exists:false],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA", active: true, resourceGroup: [$in: ["buildingTechnology"]], constructionId: [$exists:false]])
        }
    }

    def getMaterialAndBuildingTechnologyCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", active: true,firstUploadTime: [$gte: dateLimit],privateDataset: [$ne: true], resourceGroup: [$in: ["BMAT","buildingTechnology"]], constructionId: [$exists:false]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA", privateDataset: [$ne: true], resourceGroup: [$in: ["BMAT","buildingTechnology"]], constructionId: [$exists:false],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA", active: true, resourceGroup: [$in: ["BMAT","buildingTechnology"]], constructionId: [$exists:false]])
        }
    }

    def getOtherCount(Date dateLimit = null) {
        if(dateLimit){
            Integer newNumber = Resource.collection.count([applicationId: "LCA", active: true,firstUploadTime: [$gte: dateLimit],privateDataset: [$ne: true], resourceGroup: [$nin: ["BMAT","buildingTechnology"]], constructionId: [$exists:false]])?.intValue() ?: 0
            Integer deactNumber = Resource.collection.count([applicationId: "LCA",privateDataset: [$ne: true], resourceGroup: [$nin: ["BMAT","buildingTechnology"]], constructionId: [$exists:false],deactivateTime: [$gte: dateLimit], active: false])?.intValue() ?: 0
            return newNumber - deactNumber
        } else {
            return Resource.collection.count([applicationId: "LCA", active: true, resourceGroup: [$nin: ["BMAT","buildingTechnology"]], constructionId: [$exists:false]])
        }
    }

    def getAndSortEpdResources(Date dateLimit){
        Map<String, Integer> uniqueEPDForLCA = [:]
        Integer resources = 0
        Integer deactResources = 0
        BasicDBObject queryForEpdProg = new BasicDBObject()
        queryForEpdProg.put("applicationId", "LCA")
        List<String> epdPrograms = Resource.collection.distinct("epdProgram",queryForEpdProg, String.class)?.toList()
        if(dateLimit){
            epdPrograms?.each { String p ->
                resources = Resource.collection.count([applicationId: "LCA", active: true, epdProgram: p, firstUploadTime: [$gte: dateLimit], construction: [$ne: true], privateDataset: [$ne: true]])?.intValue() ?: 0
                deactResources = Resource.collection.count([applicationId: "LCA", active: false, deactivateTime: [$gte: dateLimit], epdProgram: p, construction: [$ne: true], privateDataset: [$ne: true]])?.intValue() ?: 0
                Integer total = resources - deactResources
                uniqueEPDForLCA.put(p,total)
            }
        }

        return uniqueEPDForLCA
    }

    def getDatabasePCRs() {
        Map<String, Integer> databasePCRs = [:]
        List<Document> resources = Resource.collection.find([applicationId: "LCA", pcr:[$exists: true], active: true],[pcr: 1])?.toList()
        resources?.each { Document resource ->
            Integer count = databasePCRs.get(resource.pcr.toString())

            if (count) {
                count = count + 1
            } else {
                count = 1
            }
            databasePCRs.put(resource.pcr.toString(), count)
        }
        return databasePCRs
    }

    def getImportFilesResourceAmounts() {
        Map<String, Integer> importFilesResourceAmounts = [:]

        BasicDBObject filter = new BasicDBObject("active", true)
        List<String> activeImportFiles = Resource.collection.distinct("importFile", filter, String.class)?.toList()?.findAll({!it.toLowerCase().endsWith(".xml")})?.sort({it.toLowerCase()})

        activeImportFiles?.each { String importFile ->
            Integer count = Resource.collection.count([importFile: importFile, active: true])?.intValue()
            importFilesResourceAmounts.put((importFile), count)
        }
        return importFilesResourceAmounts
    }

    def activateResource(id) {
        boolean deleteOk = false

        if (id) {
            Resource resource = Resource.get(id)

            if (resource) {
                String resourceId = resource.resourceId
                String key = "${resourceId}${resource.profileId}"
                resourceCache.remove(key)
                resourceProfilesCache.remove(resource.resourceId)
                resource.active = true
                resource.merge(flush: true, failOnError: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    boolean deactivateResourceById(id) {
        if (id) {
            Resource resource = Resource.get(id)
            return deactivateResource(resource)
        }
        return false
    }

    boolean deactivateResource(Resource resource) {
        if (resource) {
            String resourceId = resource.resourceId
            String key = "${resourceId}${resource.profileId}"
            resourceCache.remove(key)
            resourceProfilesCache.remove(resource.resourceId)
            resource.active = false
            resource.merge(flush: true, failOnError: true)
            return true
        }
        return false
    }

    def deleteResource(id) {
        boolean deleteOk = false

        if (id) {
            Resource resource = Resource.get(id)

            if (resource) {
                String resourceId = resource.resourceId
                String key = "${resourceId}${resource.profileId}"
                resourceCache.remove(key)
                resourceProfilesCache.remove(resource.resourceId)
                resource.delete(flush: true)
                List<Resource> resourceProfiles = getResourceProfilesByResourceId(resourceId, null, Boolean.TRUE)

                if (resourceProfiles) {
                    if (resourceProfiles.size() > 1) {
                        resourceProfiles.each {
                            it.multipleProfiles = Boolean.TRUE
                            saveResource(it)
                        }
                    } else {
                        Resource singleProfile = resourceProfiles.get(0)
                        singleProfile.multipleProfiles = Boolean.FALSE
                        saveResource(singleProfile)
                    }
                }
                deleteOk = true
            }
        }
        return deleteOk
    }


    def deleteResources(List<String> ids) {
        boolean deleteOk = false

        if (ids) {
            List<Resource> resources = Resource.getAll(DomainObjectUtil.stringsToObjectIds(ids))

            if (resources) {
                resources.each { Resource resource ->
                    String resourceId = resource.resourceId
                    String key = "${resourceId}${resource.profileId}"
                    resourceCache.remove(key)
                    resourceProfilesCache.remove(resource.resourceId)
                    resource.delete(flush: true)
                    List<Resource> resourceProfiles = getResourceProfilesByResourceId(resourceId, null, Boolean.TRUE)

                    if (resourceProfiles) {
                        if (resourceProfiles.size() > 1) {
                            resourceProfiles.each {
                                it.multipleProfiles = Boolean.TRUE
                                saveResource(it)
                            }
                        } else {
                            Resource singleProfile = resourceProfiles.get(0)
                            singleProfile.multipleProfiles = Boolean.FALSE
                            saveResource(singleProfile)
                        }
                    }
                }
                deleteOk = true
            }
        }
        return deleteOk
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourcesByResourceGroupAndResourceIds(resourceGroup, Collection<String> resourceIds) {
        List<Resource> resources = []

        if (resourceGroup && resourceIds) {
            MongoCursor mongocursor = Resource.collection.find(["active": true, "resourceGroup": resourceGroup, "resourceId": ["\$in": resourceIds]]).iterator()

            if (mongocursor?.hasNext()) {
                resources.addAll(mongocursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }
        }
        return resources
    }

    /**
     * Find resources by a combination of parameters
     *
     * @param resourceIds
     * @param profileIds
     * @param alsoInactive
     *
     * @return a list of matched resources
     */
    @Transactional(readOnly = true)
    List<Resource> getResources(List<String> resourceIds, List<String> profileIds = null, Boolean alsoInactive = Boolean.FALSE) {
        long start = System.currentTimeMillis()
        List<Resource> result
        if (!resourceIds) {
            return []
        }

        if(profileIds && !alsoInactive) {
            result = Resource.findAllByResourceIdInListAndProfileIdInListAndActive(resourceIds, profileIds, true)
        } else if(profileIds && alsoInactive) {
            result = Resource.findAllByResourceIdInListAndProfileIdInList(resourceIds, profileIds)
        } else {
            result = Resource.findAllByResourceIdInList(resourceIds)
        }

        log.info("Fetched resources: ${result.size()} in ${System.currentTimeMillis() - start}")
        return new ArrayList<Resource>(result)
    }

    Resource getResourceWithParams(String resourceId, String profileId = null, Boolean alsoInactive = null, Boolean fetchResourceId = Boolean.FALSE) {
        Resource resource = null

        if (resourceId) {
            resourceId = resourceId.trim()
            profileId = profileId?.trim()
            resource = getResourceFromCache(resourceId, profileId)
            //log.error("epdNumberALT 1 ${resource?.epdNumberAlt}")
            if (!resource) {
                try {
                    if (profileId) {
                        resource = Resource.collection.findOne(["resourceId": resourceId, "profileId": profileId, "active": true]) as Resource

                        // ProfileId might come invalid from calculation
                        if (!resource) {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true, "active": true]) as Resource

                            // Failover to no defaultProfile if data team fucked up smh..
                            if (!resource) {
                                resource = Resource.collection.findOne(["resourceId": resourceId, "active": true]) as Resource
                            }
                        }
                    } else {
                        resource = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true, "active": true]) as Resource

                        if (!resource) {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "active": true]) as Resource
                        }
                    }

                    if (!resource && alsoInactive) {
                        if (profileId) {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "profileId": profileId]) as Resource

                            if (!resource) {
                                resource = Resource.collection.findOne(["resourceId": resourceId]) as Resource
                            }
                        } else {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true]) as Resource

                            if (!resource) {
                                resource = Resource.collection.findOne(["resourceId": resourceId]) as Resource
                            }
                        }
                    }

                    if (resource && !resource?.resourceId) {
                        resource = getResourceWithGorm(resourceId, profileId, alsoInactive)
                        //log.error("epdNumberALT 2 ${resource?.epdNumberAlt}")
                    }

                    if (resource && resource.active) {
                        setResourceToCache(resource)
                        //log.error("epdNumberALT 3 ${resource?.epdNumberAlt}")
                    }
                } catch (Exception e) {
                }
            }
        }
        return resource
    }

    def getResourceNameEN(String resourceId, String profileId = null) {
        String nameEN

        if (resourceId) {
            if (profileId) {
                nameEN = Resource.collection.findOne([resourceId: resourceId, profileId: profileId, active: true], [nameEN: 1])?.nameEN?.toString()
            } else {
                nameEN = Resource.collection.findOne([resourceId: resourceId, active: true], [nameEN: 1])?.nameEN?.toString()
            }
        }

        if (!nameEN) {
            nameEN = ""
        }
        return nameEN

    }

    def getResourceWithGorm(String resourceId, String profileId = null, Boolean alsoInactive = null) {
        Resource resource

        if (resourceId) {
            if (profileId) {
                resource = Resource.findByResourceIdAndProfileIdAndActive(resourceId, profileId, true)

                if (!resource && alsoInactive) {
                    resource = Resource.findByResourceIdAndProfileId(resourceId, profileId)
                }
            } else {
                resource = Resource.findByResourceIdAndActive(resourceId, true)

                if (!resource && alsoInactive) {
                    resource = Resource.findByResourceId(resourceId)
                }
            }
        }

        return resource
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Resource> getResourceProfilesByResourceId(String resourceId, String profileId = null, Boolean fetchId = Boolean.FALSE) {
        List<Resource> profiles
        MongoCursor mongoCursor

        if (resourceId) {
            profiles = getResourceProfilesFromCache(resourceId)

            if (!profiles) {
                resourceId = resourceId.trim()

                if (profileId) {
                    profileId = profileId.trim()
                    mongoCursor = Resource.collection.find(["resourceId": resourceId, "profileId": profileId, "active": true]).iterator()

                    if (!mongoCursor || !mongoCursor.hasNext()) {
                        mongoCursor = Resource.collection.find(["resourceId": resourceId, "profileId": profileId]).iterator()
                    }
                } else {
                    mongoCursor = Resource.collection.find(["resourceId": resourceId, "active": true, "profileId": ["\$exists": true]]).iterator()

                    if (!mongoCursor || !mongoCursor.hasNext()) {
                        mongoCursor = Resource.collection.find(["resourceId": resourceId, "profileId": ["\$exists": true]]).iterator()
                    }
                }

                if (mongoCursor?.hasNext()) {
                    profiles = mongoCursor.toList().collect({ Document document ->
                        documentAsResource(document, fetchId)
                    })
                    setResourceProfilesToCache(profiles)
                }
            }
        }
        return profiles
    }


    @Secured(["ROLE_AUTHENTICATED"])
    def getResourceByResourceAndProfileId(resourceId, profileId, Boolean alsoInactive = Boolean.FALSE, Boolean includeId = Boolean.FALSE) {
        Resource resource = null

        if (resourceId) {
            resourceId = resourceId.trim()
            profileId = profileId?.trim()
            resource = getResourceFromCache(resourceId, profileId)

            if (!resource || (!resource.active && !alsoInactive)) {
                try {
                    if (alsoInactive) {
                        if (profileId) {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "profileId": profileId]) as Resource
                        } else {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true]) as Resource

                            if (!resource) {
                                resource = Resource.collection.findOne(["resourceId": resourceId]) as Resource
                            }
                        }
                    } else {
                        if (profileId) {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "profileId": profileId, "active": true]) as Resource
                        } else {
                            resource = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true, "active": true]) as Resource

                            if (!resource) {
                                resource = Resource.collection.findOne(["resourceId": resourceId, "active": true]) as Resource
                            }
                        }
                    }

                    if (resource && !resource?.resourceId) {
                        resource = getResourceWithGorm(resourceId, profileId, alsoInactive)
                    }

                    if (resource && resource.active) {
                        setResourceToCache(resource)
                    }
                } catch (Exception e) {
                    log.trace("Failed to get a resource", e)
                }
            }
        }
        return resource
    }


    @Secured(["ROLE_AUTHENTICATED"])
    def getresourceIsoCode(String area) {
        String isoCountryCode
        Resource country = getResourceByResourceAndProfileId(area, null)
        isoCountryCode = country?.isoCountryCode?.toLowerCase()

        if (isoCountryCode) {
            return isoCountryCode
        } else {
            return "world"
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getresourceIsoCodes(List<String> areas, String additionalArea = null) {
        Map<String, String> isoCountryCodes = [:]

        if (areas) {
            areas.each { String area ->
                String isoCountryCode = Resource.collection.findOne([resourceId: area, active: true], [isoCountryCode: 1])?.isoCountryCode?.toString()?.toLowerCase()

                if (isoCountryCode && !isoCountryCodes.get(area)) {
                    isoCountryCodes.put(area, isoCountryCode)
                }
            }
        }

        if (additionalArea) {
            String additionalIsoCountryCode = Resource.collection.findOne([resourceId: additionalArea, active: true], [isoCountryCode: 1])?.isoCountryCode?.toString()?.toLowerCase()

            if (additionalIsoCountryCode) {
                isoCountryCodes.put("additionalArea", additionalIsoCountryCode)
            }
        }

        if (!isoCountryCodes.isEmpty()) {
            return isoCountryCodes
        } else {
            return [world: "world"]
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def uncapitalizeArea(CharSequence self) {
        if (self.length() == 0) return "";
        return "" + Character.toLowerCase(self.charAt(0)) + self.subSequence(1, self.length());
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def trimBadResourceIds(String resourceId) {

        if (resourceId) {
            if (resourceId.capitalize()?.equals(resourceId)) {
                resourceId = uncapitalizeArea(resourceId)
            }

            if (!resourceId.contains(" ")) {
                resourceId = resourceId.toLowerCase()
            }

            if (resourceId) {
                resourceId = resourceId.replaceAll("\\s", "")
            }

            if (resourceId.equalsIgnoreCase("unitedstates")) {
                resourceId = "USA"
            }
            if (resourceId.equalsIgnoreCase("unitedkingdom")) {
                resourceId = "unitedKingdom"
            }
        }
        return resourceId
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getBestResourceInCountryBySubTypeAndDataType(String area, String subType, String connectedBenchmark, String resourceId,
                                                     Map<String, List<Object>> resourceFilterCriteria = null, List<String> neighbouringCountries = null) {
        Map<String, Resource> best3OrBest1 = [:]
        List<Resource> resources = []
        List<String> areaAndNeighBours = []
        // have to strip area of whitespaces since resources stored in mongo cant be e.q United kingdom, but unitedKingdom
        area = trimBadResourceIds(area)

        if (area && neighbouringCountries) {
            areaAndNeighBours.add(area)
            areaAndNeighBours.addAll(neighbouringCountries)
        }

        if (resourceFilterCriteria) {
            MongoCursor mongoCursor
            BasicDBObject query = new BasicDBObject()
            query.put("active", true)
            query.put("benchmark", [$exists: true])

            resourceFilterCriteria?.each { String attribute, List<Object> requiredValues ->
                query.put(attribute, [$in: requiredValues])
            }
            if (areaAndNeighBours && !areaAndNeighBours.isEmpty()) {
                query.put("areas", [$in: areaAndNeighBours])

            } else if (area) {
                query.put("areas", [$in: [area]])
            }

            if (subType) {
                query.put("resourceSubType", subType)
            }
            mongoCursor = Resource.collection.find(query).iterator()

            if (mongoCursor?.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, Boolean.FALSE)
                }))
            }

        } else {
            if (areaAndNeighBours && !areaAndNeighBours.isEmpty()) {
                resources = Resource.findAllByAreasIlikeAndResourceSubTypeAndBenchmarkIsNotNull(areaAndNeighBours, subType)
            } else {
                resources = Resource.findAllByAreasIlikeAndResourceSubTypeAndBenchmarkIsNotNull([area], subType)
            }
        }
        if (resources) {
            List<Resource> sortedBybest
            Resource originalResource = Resource.findByResourceIdAndActiveAndResourceSubType(resourceId, true, subType)
            Double originalRank = originalResource?.benchmark?.ranking?.get(connectedBenchmark)
            sortedBybest = resources.findAll({
                it.benchmark?.ranking?.get(connectedBenchmark) && it.benchmark?.ranking?.get(connectedBenchmark) < originalRank
            })?.sort({ it.benchmark.ranking?.get(connectedBenchmark) })

            if (sortedBybest && !sortedBybest.isEmpty()) {
                Resource bestResource = sortedBybest.get(0)
                if (resourceId.equals(bestResource?.resourceId)) {
                    best3OrBest1.put(bestResource.resourceId, bestResource)
                } else {
                    int i = 0
                    sortedBybest?.findAll({ !(resourceId).equalsIgnoreCase(it.resourceId) })?.each { Resource r ->
                        if (i <= 24) {
                            best3OrBest1.put(r.resourceId, r)
                        }
                        i++
                    }

                }
            }

        }
        return best3OrBest1
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourcesThatAreConstructions() {
        List<Resource> resources = []
        resources = Resource.findAllByConstructionIsNotNull()
        return resources
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getResourceByConstructionId(String constructionId) {
        Resource resource
        resource = Resource.findByConstructionId(constructionId)
        return resource
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getCountryLocalizedName(String area) {
        String localizedName
        Resource country = getResourceByResourceAndProfileId(area, null)
        localizedName = getLocalizedName(country)

        if (localizedName) {
            if("local".equalsIgnoreCase(localizedName)){
                localizedName =  messageSource.getMessage("area_for_generic", null, LocaleContextHolder.getLocale()) ?: localizedName
            }
            return localizedName
        } else {
            if("local".equalsIgnoreCase(area)){
                area =  messageSource.getMessage("area_for_generic", null, LocaleContextHolder.getLocale()) ?: area
            }
            return area
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getAllResources(Boolean alsoInactive = Boolean.FALSE, Boolean includeIds = Boolean.FALSE, String applicationId = null) {
        List<Resource> resources = []

        if (alsoInactive) {
            MongoCursor mongoCursor

            if (applicationId) {
                mongoCursor = Resource.collection.find(["applicationId": applicationId]).iterator()
            } else {
                mongoCursor = Resource.collection.find().iterator()
            }

            if (mongoCursor.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, includeIds)
                }))
            }

        } else {
            MongoCursor mongoCursor

            if (applicationId) {
                mongoCursor = Resource.collection.find(["applicationId": applicationId, "active": true]).iterator()
            } else {
                mongoCursor = Resource.collection.find(["active": true]).iterator()
            }

            if (mongoCursor.hasNext()) {
                resources.addAll(mongoCursor.toList().collect({ Document document ->
                    documentAsResource(document, includeIds)
                }))
            }
        }
        return resources?.sort({ it.resourceId })
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Document> getAllResourcesAsDocuments(Boolean active = Boolean.TRUE, String applicationId = null) {
        if (applicationId) {
            return Resource.collection.find([applicationId: applicationId, active: active])?.toList()
        } else {
            return Resource.collection.find([active: active])?.toList()
        }
    }

    def getResourcesWithoutApplicationId(Boolean active = Boolean.TRUE) {
        return Resource.findAllByApplicationIdIsNullAndActive(active)
    }

    def getOnlyInactiveResources() {
        List<Resource> resources = Resource.collection.find(["active": ["\$ne": true]])?.collect({ it as Resource })
        return resources?.sort({ it.resourceId })
    }

    def getResourcesWithMultipleProfiles() {
        Map<Resource, List<Resource>> resourceWithMultipleProfiles = new LinkedHashMap<Resource, List<Resource>>()
        BasicDBObject filter = new BasicDBObject()
        filter.put("applicationId", "LCA")
        filter.put("active", true)
        List<String> resourceIds = Resource.collection.distinct("resourceId", filter, String.class)?.toList()

        resourceIds?.each { String resourceId ->
            if (Resource.collection.count(["resourceId": resourceId , "applicationId": "LCA", active: true]) > 1) {
                List<Resource> resources = Resource.findAllByResourceIdAndApplicationIdAndActive(resourceId, "LCA", true)
                Resource resource = resources.find({it.defaultProfile})
                List<Resource> profiles = resources.findAll({!it.defaultProfile})

                if (resource && profiles) {
                    resourceWithMultipleProfiles.put(resource, profiles)
                }
            }
        }
        return resourceWithMultipleProfiles
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getDuplicateResourcesByStaticFullName() {
        BasicDBObject matchByResourceId = new BasicDBObject()
        matchByResourceId.append("\$match", [active: true, applicationId: "LCA"])
        BasicDBObject group = new BasicDBObject()
        group.append("\$group", ["_id": [staticFullName: "\$staticFullName"], uniqueIds: ["\$addToSet":"\$_id"], count: ["\$sum":1]])
        BasicDBObject matchByCount = new BasicDBObject()
        matchByCount.append("\$match", [count: ["\$gt": 1]])
        List<Document> results = Resource.collection.aggregate(Arrays.asList(matchByResourceId, group, matchByCount))?.toList()
        List<Resource> duplicates = []

        if (results) {
            duplicates = Resource.collection.find([_id: [$in: results.collect({it.get("uniqueIds")}).flatten()]]).collect({ it as Resource })?.sort({it.resourceId?.toLowerCase()})
        }
        return duplicates
    }

    @Secured(["ROLE_SYSTEM_ADMIN"])
    def getDuplicateResources() {
        BasicDBObject matchByResourceId = new BasicDBObject()
        matchByResourceId.append("\$match", [active: true])
        BasicDBObject group = new BasicDBObject()
        group.append("\$group", ["_id": [resourceId: "\$resourceId", profileId: "\$profileId"], uniqueIds: ["\$addToSet":"\$_id"], count: ["\$sum":1]])
        BasicDBObject matchByCount = new BasicDBObject()
        matchByCount.append("\$match", [count: ["\$gt": 1]])
        List<Document> results = Resource.collection.aggregate(Arrays.asList(matchByResourceId, group, matchByCount))?.toList()
        List<Resource> duplicates = []

        if (results) {
            duplicates = Resource.collection.find([_id: [$in: results.collect({it.get("uniqueIds")}).flatten()]]).collect({ it as Resource })?.sort({it.resourceId?.toLowerCase()})
        }
        return duplicates
    }

    def getDefaultProfileForResource(String resourceId) {
        String defaultProfileId

        if (resourceId) {
            String profileId = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true, "active": true], [profileId: 1])?.profileId

            if (!profileId) {
                profileId = Resource.collection.findOne(["resourceId": resourceId, "defaultProfile": true], [profileId: 1])?.profileId
            }

            if (profileId) {
                defaultProfileId = profileId
            }
        }
        return defaultProfileId
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getEntityClasses() {
        User user = userService.getCurrentUser()
        def entityClassResources

        if (user) {
            entityClassResources = getResourcesByResourceGroup([Constants.ResourceGroup.ENTITY_CLASS.toString()])

            if (entityClassResources) {
                if (!userService.getSuperUser(user)) {
                    entityClassResources = entityClassResources.findAll({ Resource r -> !r.superUserOnly })
                }
            }
        }
        return entityClassResources
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getParentEntityClasses(Boolean showAll = null) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<Resource> entityClassResources

        if (user) {
            if (userService.getSuperUser(user) || showAll) {
                entityClassResources = Resource.collection.find(["resourceGroup": Constants.ResourceGroup.ENTITY_CLASS.toString(), "active": true, "canBeChildEntity": [$ne: true]])?.collect({
                    it as Resource
                })
            } else {
                entityClassResources = Resource.collection.find(["resourceGroup": Constants.ResourceGroup.ENTITY_CLASS.toString(), "active": true, "canBeChildEntity": [$ne: true], "superUserOnly": [$ne: true], "\$or": [["channelTokens": [$exists: false]], ["channelTokens": [$in: ["default"]]]]])?.collect({
                    it as Resource
                })
            }
        }
        return entityClassResources
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getParentEntityClassesAsJSON(Boolean showAll = null) {
        String localizedName = "name" + DomainObjectUtil.mapKeyLanguage
        Map projection = ["resourceId": 1, "nameEN": 1, "nameFI": 1, "nameFR": 1, "nameDE": 1, "nameNO": 1, "nameNL": 1, "nameES": 1, "nameSE":1, "nameIT": 1, "nameHU": 1, "nameJP": 1, "active": 1]
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<Document> entityClassResources

        if (user) {
            if (userService.getSuperUser(user) || showAll) {
                entityClassResources = Resource.collection.find(["resourceGroup": Constants.ResourceGroup.ENTITY_CLASS.toString(), "active": true, "canBeChildEntity": [$ne: true]], projection)?.toList()
            } else {
                entityClassResources = Resource.collection.find(["resourceGroup": Constants.ResourceGroup.ENTITY_CLASS.toString(), "active": true, "canBeChildEntity": [$ne: true], "superUserOnly": [$ne: true], "\$or": [["channelTokens": [$exists: false]], ["channelTokens": [$in: ["default"]]]]], projection)?.toList()
            }

            if (entityClassResources) {
                entityClassResources.each { Document r ->
                    r["localizedName"] = r.get(localizedName) ?: r.get("nameEN")
                }
            }
        }
        return entityClassResources
    }

    // List<Document> resources = Resource.collection.find([applicationId: "system", active: true, resourceGroup: "buildingTypes", privateDataset: [$exists: false]], projection)?.toList()

    @Secured(["ROLE_AUTHENTICATED"])
    def getBuildingTypeIds() {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<String> buildingTypeResourceIds

        if (user) {
            if (userService.getSuperUser(user)) {
                BasicDBObject filter = new BasicDBObject("resourceGroup", Constants.ResourceGroup.BUILDING_TYPES.toString()).append("active", true).append("applicationId", "system")
                buildingTypeResourceIds = Resource.collection.distinct("resourceId", filter, String.class)?.toList()
            } else {
                BasicDBObject filter = new BasicDBObject("resourceGroup", Constants.ResourceGroup.BUILDING_TYPES.toString()).append("active", true).append("applicationId", "system").append("superUserOnly", [$ne: true]).append("\$or", [["channelTokens": [$exists: false]], ["channelTokens": [$in: ["default"]]]])
                buildingTypeResourceIds = Resource.collection.distinct("resourceId", filter, String.class)?.toList()
            }
        }
        return buildingTypeResourceIds
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<String> getParentEntityClassIds(Boolean showAll = null) {
        User user = userService.getCurrentUser(Boolean.TRUE)
        List<String> entityClassResourceIds

        if (user) {
            if (userService.getSuperUser(user) || showAll) {
                BasicDBObject filter = new BasicDBObject("resourceGroup", Constants.ResourceGroup.ENTITY_CLASS.toString()).append("active", true).append("canBeChildEntity", [$ne: true])
                entityClassResourceIds = Resource.collection.distinct("resourceId", filter, String.class)?.toList()
            } else {
                BasicDBObject filter = new BasicDBObject("resourceGroup", Constants.ResourceGroup.ENTITY_CLASS.toString()).append("active", true).append("canBeChildEntity", [$ne: true]).append("superUserOnly", [$ne: true]).append("\$or", [["channelTokens": [$exists: false]], ["channelTokens": [$in: ["default"]]]])
                entityClassResourceIds = Resource.collection.distinct("resourceId", filter, String.class)?.toList()
            }
        }
        return entityClassResourceIds
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getChildEntityClasses() {
        User user = userService.getCurrentUser()
        def entityClassResources

        if (user) {
            entityClassResources = getResourcesByResourceGroup([Constants.ResourceGroup.ENTITY_CLASS.toString()])

            if (entityClassResources) {
                if (userService.getSuperUser(user)) {
                    entityClassResources = entityClassResources.findAll({ Resource r -> r.canBeChildEntity })
                } else {
                    entityClassResources = entityClassResources.findAll({ Resource r ->
                        !r.superUserOnly && r.canBeChildEntity &&
                                (!r.channelTokens || r.channelTokens?.contains("default"))
                    })
                }
            }
        }
        return entityClassResources
    }

    def generateDefaultThickness_mm(Resource resource) {
        Double thickness
        if (resource) {
            if (resource.defaultThickness_mm != null) {
                thickness = resource.defaultThickness_mm
            } else if (resource.defaultThickness_in != null) {
                thickness = resource.defaultThickness_in * 25.4
            }
        }
        return thickness
    }

    def generateDefaultThickness_in(Resource resource) {
        Double thickness
        if (resource) {
            if (resource.defaultThickness_in != null) {
                thickness = resource.defaultThickness_in
            } else if (resource.defaultThickness_mm) {
                thickness = resource.defaultThickness_mm / 25.4
            }
        }
        return thickness
    }

    def generateTechnicalSpec(Resource resource) {
        String technicalSpec
        if (resource && resource.technicalSpec) {
            technicalSpec = resource.technicalSpec.trim()

            if (resource.thermalLambda) {
                if (resource.thermalLambda % 1 == 0) {
                    technicalSpec = "${technicalSpec}, Lambda=${resource.thermalLambda.intValue()} W/(m.K)"
                } else {
                    technicalSpec = "${technicalSpec}, Lambda=${resource.thermalLambda} W/(m.K)"
                }
            }
        }
        return technicalSpec
    }

    def generateCombinedUnits(Resource resource) {
        List<String> combinedUnits = []
        if (resource.unitForData) {
            if ("kg".equalsIgnoreCase(resource.unitForData)) {
                combinedUnits.add("kg")
                combinedUnits.add("ton")

                if (resource.density != null) {
                    combinedUnits.add("m3")

                    if (resource.defaultThickness_mm != null) {
                        combinedUnits.add("m2")
                    }
                }
            } else if ("m2".equalsIgnoreCase(resource.unitForData)) {
                combinedUnits.add("m2")

                if (resource.massConversionFactor != null) {
                    combinedUnits.add("kg")
                    combinedUnits.add("ton")
                }

                if (resource.defaultThickness_mm != null) {
                    combinedUnits.add("m3")
                }
            } else if ("m3".equalsIgnoreCase(resource.unitForData)) {
                combinedUnits.add("m3")

                if (resource.density != null) {
                    combinedUnits.add("kg")
                    combinedUnits.add("ton")
                }

                if (resource.defaultThickness_mm != null) {
                    combinedUnits.add("m2")
                }
            } else if ("kwh".equalsIgnoreCase(resource.unitForData)||"mj".equalsIgnoreCase(resource.unitForData)||"mwh".equalsIgnoreCase(resource.unitForData)) {
                combinedUnits.add("kWh")
                combinedUnits.add("MJ")
                combinedUnits.add("MWh")
            } else if (resource.unitForData) {
                combinedUnits.add(resource.unitForData)
                if (resource.massConversionFactor != null) {
                    combinedUnits.add("kg")
                    combinedUnits.add("ton")
                }
            }

            if (resource.mirrorConstructionCombinedUnits) {
                // this comes from the mirror construction. Only add if resource has a unitForData
                combinedUnits.addAll(resource.mirrorConstructionCombinedUnits)
            }
        }
        return combinedUnits.unique()
    }

    def generateStaticFullName(Resource resource) {
        String staticFullNameString
        String nameEN = resource?.nameEN
        String technicalSpec = resource?.technicalSpec
        String commercialName = resource?.commercialName
        String manufacturer = resource?.manufacturer

        if (nameEN && (technicalSpec || commercialName || manufacturer)) {
            staticFullNameString = "${nameEN}${technicalSpec ? ", " + technicalSpec : ""}" +
                    "${commercialName ? ", " + commercialName : ""}${manufacturer ? " (" + manufacturer + ")" : ""}"
        } else {
            staticFullNameString = "${nameEN}"
        }
        // Remove line breaks to prevent data teams errors smh
        staticFullNameString = staticFullNameString.replace("\r\n", "").replace("\n", "")
        return staticFullNameString
    }

    def generateSearchString(Resource resource) {
        String searchString
        if (resource) {
            searchString = "${resource.nameEN ? resource.nameEN.toLowerCase() : ''}${resource.nameFI ? resource.nameFI.toLowerCase() : ''}" +
                    "${resource.nameDE ? resource.nameDE.toLowerCase() : ''}${resource.nameFR ? resource.nameFR.toLowerCase() : ''}" +
                    "${resource.nameNL ? resource.nameNL.toLowerCase() : ''}${resource.nameNO ? resource.nameNO.toLowerCase() : ''}" +
                    "${resource.nameES ? resource.nameES.toLowerCase() : ''}${resource.nameSE ? resource.nameSE.toLowerCase() : ''}" +
                    "${resource.nameIT ? resource.nameIT.toLowerCase() : ''}${resource.nameHU ? resource.nameHU.toLowerCase() : ''}" +
                    "${resource.nameJP ? resource.nameJP.toLowerCase() : ''}${resource.technicalSpec ? resource.technicalSpec.toLowerCase() : ''}" +
                    "${resource.commercialName ? resource.commercialName.toLowerCase() : ''}${resource.manufacturer ? resource.manufacturer.toLowerCase() : ''}" +
                    "${resource.epdNumber ? resource.epdNumber.toLowerCase() : ''}${resource.epdNumberAlt ? resource.epdNumberAlt.toLowerCase() : ''}${resource.casNumber ? resource.casNumber.toLowerCase() : ''}"

            if (searchString) {
                searchString = Normalizer.normalize(searchString.replaceAll(",", "")?.toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("[^a-zA-Z0-9 ]+","")
            }
        }
        return searchString
    }

    def generateDynamicValues(Resource resource) {
        if (resource) {
            resource.technicalSpec = generateTechnicalSpec(resource)
            resource.searchString = generateSearchString(resource)
            resource.staticFullName = generateStaticFullName(resource)
            resource.combinedUnits = generateCombinedUnits(resource)
            resource.defaultThickness_mm = generateDefaultThickness_mm(resource)
            resource.defaultThickness_in = generateDefaultThickness_in(resource)

            if (resource.biogenicCarbonStorage_kgCO2e) {
                if (resource.resistanceProperties) {
                    resource.resistanceProperties.add("bio-co2 stored")
                } else {
                    resource.resistanceProperties = ["bio-co2 stored"]
                }
            }
            if (resource.resourceQualityWarning) {
                if (resource.resistanceProperties) {
                    resource.resistanceProperties.add("hasWarning")
                } else {
                    resource.resistanceProperties = ["hasWarning"]
                }
            }

            if (!resource.impactPERRT_MJ && (resource.renewablesUsedAsEnergy_MJ||resource.renewablesUsedAsMaterial_MJ)) {
                resource.impactPERRT_MJ = ((resource.renewablesUsedAsEnergy_MJ?:0) + (resource.renewablesUsedAsMaterial_MJ?:0))
            }

            if (!resource.impactPERNRT_MJ && (resource.nonRenewablesUsedAsEnergy_MJ||resource.nonRenewablesUsedAsMaterial_MJ)) {
                resource.impactPERNRT_MJ = ((resource.nonRenewablesUsedAsEnergy_MJ?:0) + (resource.nonRenewablesUsedAsMaterial_MJ?:0))
            }
        }
        return resource
    }

    def findResourceByStaticFullName(String staticFullName) {
        Resource resource

        if (staticFullName) {
            resource = Resource.findByStaticFullNameAndActive(staticFullName, true)
        }
        return resource
    }

    def getAllResourcesAsDocumentsForRecognitionInOrder(String databaseId, String fullName, String staticFullName,
                                                        String ifcMaterialValue, String epdNumber,
                                                        String accountId, BasicDBObject filter = null, Boolean isRseeImport) {
        Map returnable = [:]
        Integer weight
        List<Document> resources

        if (databaseId || fullName || staticFullName || ifcMaterialValue || epdNumber) {
            BasicDBObject query = new BasicDBObject()
            if(filter) {
                query = filter
            }

            if (databaseId) {
                query.put("_id", DomainObjectUtil.stringToObjectId(databaseId))
                weight = 6
            } else if (staticFullName) {
                query.put("staticFullName", staticFullName)
                weight = 5
            } else if (epdNumber && !"-".equals(epdNumber)) {
                query.put("epdNumber", epdNumber)
                weight = 4
            } else {
                if (fullName && ifcMaterialValue) {
                    query.put("staticFullName", [$in: [fullName, ifcMaterialValue]])
                    weight = 5
                } else if (fullName) {
                    query.put("staticFullName", fullName)
                    weight = 5
                } else if (ifcMaterialValue) {
                    query.put(isRseeImport ? "nameFR" : "staticFullName", ifcMaterialValue)
                    weight = 5
                }
            }
            query.put("active", true)

            if (accountId) {
                query.put("\$or", [[privateDatasetAccountId: accountId], [privateDataset: null]])
            } else {
                query.put("privateDataset", [$exists: false])
            }
            resources = Resource.collection.find(query)?.toList()
            // 16250: since cannot search staticFullName AND regex search string at the same time so need to break it down
            //to fallback if cannot search by staticFullName first then try again with regex String
        }
        returnable.put("resources", resources)
        returnable.put("weight", weight)
        return returnable
    }

    def findAllResourceAsDocumentByStaticFullName(String staticFullName, Map projection = null) {
        List<Document> resources

        if (staticFullName) {
            if (projection) {
                resources = Resource.collection.find([staticFullName: staticFullName, active: true], projection)?.toList()
            } else {
                resources = Resource.collection.find([staticFullName: staticFullName, active: true])?.toList()
            }
        }
        return resources
    }

    def getSystemLocales(ChannelFeature channelFeature = null) {
        List<Resource> systemLocales
        try {
            systemLocales = getResourcesByResourceGroup([Constants.ResourceGroup.SYSTEM_LOCALE.toString()])
            if(channelFeature){
                List<String> allowedLanguageForChannel = channelFeature.getLanguagesForChannel()
                if(allowedLanguageForChannel){
                    systemLocales = systemLocales?.findAll({allowedLanguageForChannel.contains(it.resourceId )})
                }
            }
        } catch(e) {
            loggerUtil.error(log,"Cannot get locale from system", e)
            flashService.setErrorAlert("Cannot get locale from system. Exception ${e.getMessage()}",Boolean.TRUE)
        }

        return systemLocales
    }

    def getSystemLocaleByAreas(List<String> areas) {
        Resource resource

        if (areas) {
            Document localeDoc = Resource.collection.findOne(["resourceGroup": Constants.ResourceGroup.SYSTEM_LOCALE.toString(),
                                                              "active": true, areas: [$in: areas]])

            if (localeDoc) {
                resource = documentAsResource(localeDoc, Boolean.FALSE)
            }
        }
        return resource
    }

    def getAllResourceGroups() {
        BasicDBObject filter = new BasicDBObject("active", true)
        return Resource.collection.distinct("resourceGroup", filter, String.class)?.toList()
    }

    def getAllAreas() {
        BasicDBObject filter = new BasicDBObject("active", true)
        return Resource.collection.distinct("areas", filter, String.class)?.toList()
    }

    def getAllUpstreamDBs() {
        BasicDBObject filter = new BasicDBObject("active", true)
        return Resource.collection.distinct("upstreamDB", filter, String.class)?.toList()
    }

    def getResourcesForResourceDump(String upstreamDBClassified, List<String> impactCategories) {
        List<Document> resources
        if (upstreamDBClassified && impactCategories) {
            BasicDBObject query = new BasicDBObject()
            query.put("upstreamDBClassified", upstreamDBClassified)
            impactCategories.each { String c ->
                query.put("${c}", [$exists: true])
            }

            query.put("active", true)
            resources = Resource.collection.find(query)?.toList()
        }
        return resources
    }

    def combineResourceUnits(List<String> allowedUnits, Boolean alsoImperials = Boolean.FALSE) {
        List<String> combinedUnits = []

        if (allowedUnits) {
            combinedUnits = allowedUnits*.toLowerCase()
        }

        if (alsoImperials && combinedUnits) {
            List<String> imperials = []
            combinedUnits.each { String u ->
                String imperial = unitConversionUtil.europeanUnitToImperialUnit(u)

                if (imperial && !combinedUnits.contains(imperial)) {
                    imperials.add(imperial)

                    if ("cu ft".equals(imperial) && !combinedUnits.contains("cu yd")) {
                        imperials.add("cu yd")
                    }
                }
            }
            combinedUnits.addAll(imperials)
        }
        return combinedUnits
    }

    def deleteResourceFromCache(Resource resource){

        try{
            if(resource){
                String key = resource.resourceId + resource.profileId
                if(key) {
                    resourceCache.remove(key)
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Exception trying to delete resource from cache", e)
            flashService.setErrorAlert("Exception trying to delete resource from cache: ${e.getMessage()}", true)
        }

    }
    /**
     * @see #deactivateResource
     * @param resource
     * @return
     */
    @Deprecated
    def deactivateResourceFromCache(Resource resource) {
        try {
            if (resource) {
                String key = resource.resourceId + resource.profileId
                if (key) {
                    def resourceToModify = resourceCache.get(key)
                    if (resourceToModify) {
                        resourceToModify.active = false
                        resourceCache.put(key, resourceToModify)
                    }
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        } catch (Exception e) {
            loggerUtil.error(log, "Exception trying to delete resource from cache", e)
            flashService.setErrorAlert("Exception trying to delete resource from cache: ${e.getMessage()}", true)
        }
    }

    def getResourceByStateCode(String stateCode) {
        Resource resource

        if (stateCode) {
            resource = Resource.findByStateCodeAndActive(stateCode, true)
        }
        return resource
    }

    def getConstructionIcon(def asset, String constructionType) {
        String constructionTypeImage = ""
        if (constructionType) {
            if (System.getProperty("islocalhost")) {
                constructionTypeImage = "<img src='/app/assets/constructionTypes/${constructionType}.png' class='constructionType'></img>"
                if (!asset.assetPathExists(src: "/constructionTypes/${constructionType}.png")) {
                    log.error("missing image for construction type ${constructionType}")
                    flashService.setFadeErrorAlert("missing image for construction type ${constructionType}", true)
                    constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
                }
            } else {
                String filePath = configurationService.getConfigurationValue(com.bionova.optimi.construction.Constants.APPLICATION_ID, "constructionTypesPath")
                def testPath = new File("/var/www/${filePath}${constructionType}.png")
                if (testPath.exists()) {
                    constructionTypeImage = "<img src='${filePath}${constructionType}.png' class='constructionType'></img>"

                } else {
                    log.error("missing image for construction type ${constructionType} with filepath ${testPath?.absolutePath} from server")
                    flashService.setFadeErrorAlert("missing image for construction type ${constructionType} with filepath ${testPath?.absolutePath} from server", true)
                    constructionTypeImage = "<img src='/app/assets/constructionTypes/${constructionType}.png' class='constructionType'></img>"
                    if (!asset.assetPathExists(src: "/constructionTypes/${constructionType}.png")) {
                        log.error("missing image for construction type ${constructionType} from assets")
                        flashService.setFadeErrorAlert("missing image for construction type ${constructionType} from assets", true)
                        constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
                    }
                }
            }
        } else {
            constructionTypeImage = "<i class='fa fa-cog constructionType'></i>"
        }
        return constructionTypeImage
    }

    private Resource documentAsResource(Document document, Boolean includeId = Boolean.TRUE) {
        Resource resource

        if (document) {
            try {
                ObjectId id = document.remove("_id")
                resource = document as Resource

                if (includeId && id) {
                    resource.id = id
                }
            } catch (e) {
                loggerUtil.error(log, 'Error in documentAsResource while trying to cast resource document to resource', e)
                flashService.setErrorAlert("Error in documentAsResource while trying to cast resource document to resource ${e.message}", true)
                return null
            }
        }
        return resource
    }

    private Resource getResourceFromCache(String resourceId, String profileId) {
        Resource resource

        if (resourceId && profileId) {
            String key = "${resourceId}${profileId}"
            resource = resourceCache.get(key)
        }
        return resource
    }

    private void setResourceToCache(Resource resource) {
        String resourceId = resource?.resourceId
        String profileId = resource?.profileId

        if (resourceId && profileId) {
            String key = "${resourceId}${profileId}"
            resourceCache.put(key, resource)
        }
    }

    private List<Resource> getResourceProfilesFromCache(String resourceId) {
        List<Resource> resources

        if (resourceId) {
            resources = resourceProfilesCache.get(resourceId)
        }
        return resources
    }

    private void setResourceProfilesToCache(List<Resource> resources) {
        String resourceId = resources?.get(0)?.resourceId

        if (resourceId) {
            resourceProfilesCache.put(resourceId, resources)
        }
    }

    def getDummyGroupingResource() {
        String resourceId = Constants.DUMMY_RESOURCE_ID
        Resource resource = getResourceWithParams(resourceId)

        if (!resource) {
            resource = new Resource()
            resource.resourceId = resourceId
            resource.profileId = resourceId
            resource.nameEN = "dummy"
            resource.construction = true
            resource.isMultiPart = Boolean.TRUE
            resource.areas = ["LOCAL"]
            resource.defaultProfile = true
            resource.active = true
            resource.isoCodesByAreas = ["LOCAL": "ll"]
            resource.staticFullName = "dummy"
            resource.applicationId = "LCA"
            resource.multipleProfiles = false
            resource.firstUploadTime = new Date()
            resource.importFile = "Dummy for query level grouping"
            resource.isDummy = true
            resource.epdProgram = "One Click LCA"
            resource.environmentDataSource = "One Click LCA generic construction definitions"
            resource.upstreamDB = "Automatically assign Ecoinvent or other alternative based on inputs"
            resource.constructionId = resourceId
            resource = resource.save(flush: true)
        }
        return resource
    }

    /**
     * Process ResourcePurposes of LCA Application
     * @param resourcePurposeAttributes
     * @param removePurposes
     * @return
     */
    @Secured(["ROLE_SYSTEM_ADMIN"])
    Map<String, List<Document>> processApplicationResourcePurpose(List<String> resourcePurposeAttributes, Boolean removePurposes) {

        Map<String, List<Document>> filteredResourcesPerResourcePurposeAttribute = [:]

        if (resourcePurposeAttributes) {
            List<ResourcePurpose> resourcePurposesList = applicationService.getApplicationByApplicationId(Constants.LCA_APPID)?.resourcePurposes

            resourcePurposeAttributes.each { String resourcePurposeAttribute ->
                ResourcePurpose resourcePurpose = resourcePurposesList?.find { it.attribute == resourcePurposeAttribute }

                if (resourcePurpose) {
                    List<Document> filteredResources = processResourcePurpose(resourcePurpose, removePurposes)
                    filteredResourcesPerResourcePurposeAttribute.put(resourcePurposeAttribute, filteredResources)
                }
            }
        }

        return filteredResourcesPerResourcePurposeAttribute
    }

    /**
     * Process ResourcePurpose
     * @param resourcePurpose
     * @param removePurposes
     * @return
     */
    @Secured(["ROLE_SYSTEM_ADMIN"])
    List<Document> processResourcePurpose(ResourcePurpose resourcePurpose, Boolean removePurposes) {

        List<Document> filteredResources = []
        if (!resourcePurpose) {
            return filteredResources
        }

        if (removePurposes) {
            filteredResources = getResourceDocumentsWithResourcePurpose(resourcePurpose)
        } else {
            List<Document> resourcePurposeResources = getResourceDocumentsByResourcePurposeFilterConditions(resourcePurpose)

            if (resourcePurposeResources) {
                filteredResources = runAdvancedResourceFilterCriteriaForResourceDocuments(resourcePurposeResources, resourcePurpose.validityCondititions)
            }
        }

        filteredResources?.each { Document resourceDocument ->
            updateResourceDocumentFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)
        }

        return filteredResources
    }

    /**
     * Prepares Resource Fields from ResourcePurpose to update and if there is some changes updates the resource
     * @param resourceDocument
     * @param resourcePurpose
     * @param removePurposes
     * @return
     */
    UpdateResult updateResourceDocumentFromResourcePurpose(Document resourceDocument, ResourcePurpose resourcePurpose, Boolean removePurposes) {

        UpdateResult result = null

        if (!resourceDocument || !resourcePurpose) {
            return result
        }

        Map<String, Object> updatedFields = prepareResourceFieldsToUpdateFromResourcePurpose(resourceDocument, resourcePurpose, removePurposes)?.findAll { String key, def value ->
            resourceDocument.get(key) != value
        }

        if (updatedFields) {
            result = Resource.collection.updateOne(["_id": resourceDocument._id], [$set: updatedFields])
        }

        return result
    }

    /**
     * Prepares Resource Fields from ResourcePurpose to update
     * @param resourceDocument
     * @param resourcePurpose
     * @param removePurposes
     * @return
     */
    Map<String, Object> prepareResourceFieldsToUpdateFromResourcePurpose(Document resourceDocument, ResourcePurpose resourcePurpose, Boolean removePurposes) {

        Map<String, Object> updatedFields = [:]

        if (!resourceDocument || !resourcePurpose) {
            return updatedFields
        }

        updatedFields.enabledPurposes = resourceDocument.enabledPurposes?.clone() ?: []
        updatedFields.automaticallyEnabledPurposes = resourceDocument.automaticallyEnabledPurposes?.clone() ?: []

        if (resourceDocument.disableAutomaticPurposes) {
            updatedFields.enabledPurposes = []
            updatedFields.automaticallyEnabledPurposes = []
        } else {
            String resourcePurposeAttribute = resourcePurpose.attribute
            if (removePurposes) {
                updatedFields.automaticallyEnabledPurposes.removeIf { it.equals(resourcePurposeAttribute) }
                updatedFields.enabledPurposes.removeIf { it.equals(resourcePurposeAttribute) }
            } else {
                if (!updatedFields.automaticallyEnabledPurposes.contains(resourcePurposeAttribute)) {
                    updatedFields.automaticallyEnabledPurposes.add(resourcePurposeAttribute)
                }
                updatedFields.enabledPurposes.addAll(updatedFields.automaticallyEnabledPurposes)
            }
        }

        if (resourceDocument.manuallyEnabledPurposes) {
            updatedFields.enabledPurposes.addAll(resourceDocument.manuallyEnabledPurposes)
        }

        updatedFields.enabledPurposes = updatedFields.enabledPurposes.unique()

        return updatedFields
    }

    def getResourcesByResourcePurpose(ResourcePurpose resourcePurpose) {
        List<Resource> resources

        if (resourcePurpose) {
            BasicDBObject query = new BasicDBObject()
            resourcePurpose.filterConditions?.each {
                query.put((it.key), [$in: it.value])
            }
            resources = Resource.collection.find(query)?.toList()?.collect({it as Resource})
        }
        return resources
    }

    /**
     * Provides list of all resource documents filtered by resourcePurpose.filterConditions
     * @param resourcePurpose
     * @return
     */
    List<Document> getResourceDocumentsByResourcePurposeFilterConditions(ResourcePurpose resourcePurpose) {
        List<Document> resources

        if (resourcePurpose) {
            BasicDBObject query = new BasicDBObject()
            resourcePurpose.filterConditions?.each {
                query.put((it.key), [$in: it.value])
            }
            resources = Resource.collection.find(query)?.toList()
        }
        return resources
    }

    def getResourcesWithResourcePurpose(ResourcePurpose resourcePurpose) {
        List<Resource> resources

        if (resourcePurpose) {
            resources = Resource.collection.find([enabledPurposes: resourcePurpose.attribute])?.toList()?.collect({it as Resource})
        }
        return resources
    }

    /**
     * Provides list of all resource documents filtered with ResourcePurpose (resource.enabledPurposes = resourcePurpose.attribute)
     * @param resourcePurpose
     * @return
     */
    List<Document> getResourceDocumentsWithResourcePurpose(ResourcePurpose resourcePurpose) {
        List<Document> resources

        if (resourcePurpose) {
            resources = Resource.collection.find([enabledPurposes: resourcePurpose.attribute])?.toList()
        }
        return resources
    }

    def getResourcesByRulesMap(Map<String,Object> ruleMap, Boolean findPlantData = Boolean.FALSE, Boolean findPrivateData = Boolean.FALSE){
        BasicDBObject query = new BasicDBObject()
        DBObject projection = new BasicDBObject([resourceId: 1, profileId: 1, staticFullName: 1,_id:1,enabledPurposes:1, resourceSubType: 1, resourceType: 1])
        ruleMap.each {k,v->
            query.put((k), [$eq: v])
        }
        query.put("active", true)
        if(!findPlantData){
            query.put("environmentDataSource",[$ne: "Climate Earth"])
        }
        if(!findPrivateData){
            query.put("privateDataset",[$ne: true] )
        }

        List<Resource> resourcesFound = Resource.collection.find(query,projection).toList()?.collect({it as Resource})
        return resourcesFound
    }

    def getPassesBenchmarkFilter(Document resource, String benchmark) {
        Boolean passes = false
        if (resource && benchmark) {
            if ("co2_cml".equals(benchmark)) {
                if (resource.impactGWP100_kgCO2e != null && resource.dataProperties?.contains("CML") && "LCA".equals(resource.applicationId)) {
                    passes = true
                }
            } else if ("co2_traci".equals(benchmark)) {
                if (resource.traciGWP_kgCO2e != null && resource.dataProperties?.contains("TRACI")) {
                    passes = true
                }
            } else if ("co2_inies_a1-c4".equals(benchmark)) {
                if ("INIES".equalsIgnoreCase(resource.epdProgram?.toString()) && resource.impacts?.get("A1-C4") && resource.dataProperties && org.apache.commons.collections4.CollectionUtils.containsAny(["CML", "INIES"], resource.dataProperties)) {
                    passes = true
                }
            } else if ("leed_epd_cml".equals(benchmark)) {
                if (resource.dataProperties?.contains("CML") && "Verified".equalsIgnoreCase(resource.verificationStatus?.toString())) {
                    passes = true
                }
            } else if ("leed_epd_traci".equals(benchmark)) {
                if (resource.dataProperties?.contains("TRACI") && "Verified".equalsIgnoreCase(resource.verificationStatus?.toString())) {
                    passes = true
                }
            } else if ("ecopoints_direct".equals(benchmark)) {
                if (resource.dataProperties?.contains("IMPACT") && resource.impact_ecopoints != null) {
                    passes = true
                }
            } else if ("shadowprice_direct".equals(benchmark)) {
                if (resource.shadowPrice != null) {
                    passes = true
                }
            } else if ("utilities".equals(benchmark)) {
                if (resource.impactGWP100_kgCO2e != null && resource.dataProperties?.contains("LCA") && "LCA-utility".equals(resource.applicationId)) {
                    passes = true
                }
            }
        }
        return passes
    }

    /**
     * check if local compensation (localisation) can apply to resource
     * @return null if no resource available
     */
    Boolean isLocalCompApplicable(Resource resource, ResourceType subType = null) {
        if (resource) {
            subType = subType ?: resourceService.getSubType(resource)
            return !resource.privateDataset && subType && (subType.requiredElectricityKwh != null || subType.requiredElectricityKwhPerKg != null) && (subType.standardUnit && resource.combinedUnits && resource.combinedUnits*.toLowerCase().contains(subType.standardUnit.toLowerCase()))
        }
        return null
    }

    def generateExcelForResources(List<Resource> resources, List<String> headers) {
        // let it crash if something goes wrong. No try catch

        Workbook wb

        if (resources && headers) {
            wb = new HSSFWorkbook()
            CellStyle headerCellStyle = wb.createCellStyle()
            Font f = wb.createFont()
            f.setBold(Boolean.TRUE)
            headerCellStyle.setFont(f)
            headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.index)
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

            CellStyle hyperlinkCS = wb.createCellStyle()
            Font hyperLinkFont = wb.createFont()
            hyperLinkFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.index)
            hyperLinkFont.setUnderline(HSSFFont.U_SINGLE)
            hyperlinkCS.setFont(hyperLinkFont)

            CellStyle wrapCS = wb.createCellStyle();
            wrapCS.setWrapText(true);

            Sheet sheet = wb.createSheet()
            Row headerRow = sheet.createRow(0)
            Integer i = 0
            Integer numberOfHeadings = headers.size()

            headers.each { String header ->
                Cell headerCell = headerRow.createCell(i)
                headerCell.setCellStyle(headerCellStyle)
                headerCell.setCellType(CellType.STRING)
                headerCell.setCellValue(header)
                i++
            }
            i = 1

            resources.each { Resource resource ->
                Row resourceRow = sheet.createRow(i)
                int j = 0

                headers.each { String header ->
                    Cell cell = resourceRow.createCell(j)
                    String value = ''
                    Boolean isHyperLink = false
                    String hyperLinkText = ''

                    if ("ResourceId".equalsIgnoreCase(header)) {
                        value = resource.resourceId?.toString()
                    } else if ("ProfileId".equalsIgnoreCase(header)) {
                        value = resource.profileId?.toString()
                    } else if ("Active".equalsIgnoreCase(header)) {
                        value = resource.active?.toString()
                    } else if ("Virtual".equalsIgnoreCase(header)) {
                        value = resource.virtual ? 'true' : 'false'
                    } else if ("Virtual parts".equalsIgnoreCase(header)) {
                        if (resource.compositeParts) {
                            cell.setCellStyle(wrapCS)
                            resource.compositeParts.each { ResourceCompositePart compositePart ->
                                if (compositePart) {
                                    value += 'ResourceId: ' + compositePart.resourceId + '\n'
                                    value += 'ProfileId: ' + compositePart.profileId + '\n'
                                    value += 'Quantity: ' + compositePart.quantity + '\n'
                                }
                            }
                        }
                    } else if ("Show data".equalsIgnoreCase(header)) {
                        value = grailsLinkGenerator.link(action: "showData", params: [resourceUUID: resource.id], absolute: true)?.toString()
                        hyperLinkText = 'Data ' + resource.resourceId
                        isHyperLink = true
                    } else if ("Stage count".equalsIgnoreCase(header)) {
                        value = resource.impacts?.keySet()?.toList()?.size()?.toString()
                    } else if ("Stages".equalsIgnoreCase(header)) {
                        value = resource.impacts?.keySet()?.join(', ')
                    } else if ("See instances".equalsIgnoreCase(header)) {
                        value = grailsLinkGenerator.link(action: "resourceUsage", params: [resourceId: resource.resourceId, profileId: resource.profileId], absolute: true)
                        hyperLinkText = 'See use ' + resource.resourceId
                        isHyperLink = true
                    } else if ("Default Profile".equalsIgnoreCase(header)) {
                        value = resource.defaultProfile?.toString()
                    } else if ("Density".equalsIgnoreCase(header)) {
                        value = resource.density?.toString()
                    } else if ("ApplicationId".equalsIgnoreCase(header)) {
                        value = resource.applicationId?.toString()
                    } else if ("Groups".equalsIgnoreCase(header)) {
                        value = resource.resourceGroup?.toString()
                    } else if ("Name".equalsIgnoreCase(header)) {
                        value = getLocalizedName(resource)
                    } else if ("StaticFullname".equalsIgnoreCase(header)) {
                        value = resource.staticFullName?.toString()
                    } else if ("Unit".equalsIgnoreCase(header)) {
                        value = resource.unitForData?.toString()
                        value += resource.combinedUnits ? ' (' + resource.combinedUnits.toString() + ')' : ''
                    } else if ("GWP100".equalsIgnoreCase(header)) {
                        value = resource.impactGWP100_kgCO2e?.toString()
                    } else if ("ServiceLife".equalsIgnoreCase(header)) {
                        value = resource.serviceLife?.toString()
                    } else if ("Mass".equalsIgnoreCase(header)) {
                        value = resource.massConversionFactor?.toString()
                    } else if ("Energy".equalsIgnoreCase(header)) {
                        value = resource.energyConversionFactor?.toString()
                    } else if ("Import file".equalsIgnoreCase(header)) {
                        value = resource.importFile?.toString()
                    } else if ("Delete link".equalsIgnoreCase(header)) {
                        value = grailsLinkGenerator.link(action: "deleteResource", params: [id: resource.id.toString()], absolute: true)
                        isHyperLink = true
                        hyperLinkText = 'Delete resource ' + resource.resourceId
                    }

                    if (isHyperLink && value) {
                        cell.setCellFormula("HYPERLINK(\"${value}\",\"${hyperLinkText}\")");
                        cell.setCellStyle(hyperlinkCS)
                    } else if (value?.isNumber()) {
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

    /**
     * Populate the groupingDatasetName to transient groupingName of resource
     * @param r
     * @param dummyDataset the grouping dataset
     */
    void putGroupingNameToResource(Resource r, Dataset dummyDataset) {
        if (r && dummyDataset) {
            r.groupingName = dummyDataset.groupingDatasetName
        }
    }

    /**
     * Populate groupingDatasetName of grouping datasets (dummy datasets) to transient groupingName of resources
     * @param resources
     * @param datasets of design
     * @param manualIdsPerResourceUuid map <resourceUuid, set of manualIds>
     */
    void populateGroupingNameToResources(Set<Resource> resources, Set<Dataset> datasets, Map<String, Set<String>> manualIdsPerResourceUuid) {
        if (resources && datasets && manualIdsPerResourceUuid) {
            try {
                for (Resource r in resources) {
                    if (r?.isDummy) {
                        Set<String> manualIdsLinkedToResource = manualIdsPerResourceUuid.get(r?.id?.toString())
                        if (manualIdsLinkedToResource) {
                            putGroupingNameToResource(r, datasets?.find({ manualIdsLinkedToResource.contains(it.manualId) }))
                        }
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error in populateGroupingNameToResource", e)
                flashService.setErrorAlert("Error in populateGroupingNameToResource: ${e.getMessage()}", true)
            }
        }
    }

    /**
     * Resolve the display full name (or grouping name) of resource on UI. It can be staticFullName or uiLabel (depends on where)
     * <li> Resource row and group edit modal: uiLabel </li>
     * <li> Data card: static full name </li>
     * @param resource
     * @param displayStaticFullName set to true if need to display static full name, will display uiLabel if false
     * @param dummyDataset use if needed
     * @return
     */
    String getResourceDisplayName(Resource resource, Boolean displayStaticFullName, Dataset dummyDataset = null) {
        if (resource) {
            String displayName = displayStaticFullName ? resource.staticFullName : getUiLabel(resource)
            if (resource.isDummy) {
                return resource?.groupingName ?: dummyDataset?.groupingDatasetName ?: displayName
            } else {
                nmdResourceService.appendCategoryAndNmdCodeToName(resource, displayName)
                return displayName
            }
        }
        return ''
    }

    /**
     * Returns a parent construction resource name for a constituent resource dataset.
     * If the parent construction resource is a dummy resource the name is extracted from
     * groupingDatasetName of the parent construction dataset. Otherwise, the localizedName of
     * the parent construction resource.
     *
     * @param resourceDataset a constituent resource dataset
     * @return a resource name (string) of a parent construction
     */
    private getParentConstructionResourceName(Dataset resourceDataset) {
        String constructionResourceName

        Dataset parentConstructionDataset = resourceDataset?.parentConstructionDataset
        Resource parentConstructionResource = datasetService.getResource(parentConstructionDataset)

        if (parentConstructionResource?.isDummy) {
            constructionResourceName = parentConstructionDataset?.groupingDatasetName ?: ''
        } else {
            constructionResourceName = getLocalizedName(parentConstructionResource) ?: ''
        }

        return constructionResourceName
    }

    /**
     * Checks if resource is disabledNoMass (it doesn't have defined mass or density) for transport question
     * @param resource
     * @param additionalQuestion
     * @param mainAdditionalQuestion
     * @return
     */
    boolean isResourceWithDisabledNoMassTransport(Resource resource, Question additionalQuestion = null, Question mainAdditionalQuestion = null) {
        return (additionalQuestion?.isTransportQuestion || mainAdditionalQuestion?.isTransportQuestion) && !resource?.combinedUnits?.contains("kg")
    }

    /**
     * Find resource that is not disabledNoMass for transport question
     * @param resources
     * @param transportAddQ
     * @return
     */
    Resource getResourceWithoutDisabledNoMassTransport(Set<Resource> resources, Question transportAddQ) {
        if (resources && transportAddQ) {
            return resources.find { !isResourceWithDisabledNoMassTransport(it, transportAddQ) }
        }
        return null
    }

    /**
     * Loads resource object to dataset
     * This method is similar to {@link com.bionova.optimi.core.service.NewCalculationService#preloadCalculationResources}
     * Resource can be null if not found from database >>> won't be set to dataset
     * @param datasets
     */
    void loadResourceToDatasets(Set<Dataset> datasets) {
        if (datasets) {
            try {
                Map<String, Resource> cache = [:]
                for (Dataset dataset in datasets) {
                    if (dataset?.resourceId) {
                        String key = dataset.resourceId + '.' + dataset.profileId
                        Resource resource
                        if (cache.containsKey(key)) {
                            resource = cache.get(key)
                        } else {
                            resource = getResourceWithParams(dataset.resourceId, dataset.profileId, true)
                            cache.put(key, resource)
                        }
                        dataset.setCalculationResource(resource)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error in loadResourceToDatasets", e)
                flashService.setErrorAlert("Error in loadResourceToDatasets: ${e.getMessage()}", true)
            }
        }
    }

    boolean isEcoinventResource(Resource resource) {
        return isEcoinventResource(resource.enabledPurposes)
    }

    boolean isEcoinventResource(List<String> enabledPurposes) {
        return enabledPurposes?.find { Resource.ECOINVENT36.equalsIgnoreCase(it) } ? true : false
    }

    /**
     * Check if user has rights to use this Ecoinvent resource
     *
     * @param resource an Ecoinvent resource, should run a check with {@link #isEcoinventResource} before using this method
     * @param hasUncappedLicense true if user has the license {@link com.bionova.optimi.core.domain.mongo.Feature#ECOINVENT_UNCAPPED} which allows user to freely access Ecoinvent resources
     * @param resourceIdsInDataList list of resourceIds in company data list, if resource is in this list >>> user is allowed to access this resource
     * @return
     */
    boolean isAuthorizedEcoinventResourceForUser(Resource resource, Boolean hasUncappedLicense, List<String> resourceIdsInDataList) {
        return hasUncappedLicense || resourceIdsInDataList?.contains(resource?.resourceId)
    }

    //TODO: to remove and not to use for calculations, use ResourceCache instead
    void initResources(List<Dataset> datasets) {
        if (!datasets) {
            return
        }
        Long start = new Date().time
        Map<String, Resource> cache = preloadCalculationResources(datasets, [:])
        datasets.each { dataset ->
            Resource resource = cache.get(getResourceKey(dataset))
            if (resource) {
                //sets resource explicitly to avoid extra db calls
                dataset.setCalculationResource(resource)
            }
        }
        log.info("Preloaded ${cache.size()} resources for ${datasets.size()} datasets in ${new Date().time - start}.")
    }

    static String getResourceKey(Dataset ds) {
        if (!ds) {
            return null
        }
        return "${ds.resourceId}${ds.profileId}"
    }

    static String getResourceKey(Resource rs) {
        if (!rs) {
            return null
        }
        return "${rs.resourceId}${rs.profileId}"
    }

    private Map<String, Resource> preloadCalculationResources(List<Dataset> datasets, Map cache = [:]) {
        Map<String, Resource> result = [:]
        if (!datasets) {
            log.trace("No resources to fetch.")
            return cache
        }

        List<String> resourceIds = []
        List<String> profileIds = []
        for (Dataset ds : datasets) {
            if (ds) {
                if (ds.resourceId) {
                    resourceIds.add(ds.resourceId)
                }
                if (ds.profileId) {
                    profileIds.add(ds.profileId)
                }
            }
        }

        List<Resource> resources = []
        if (resourceIds && profileIds) {
            resources = getResources(resourceIds,
                    profileIds, Boolean.TRUE)
        }
        if (resources) {
            result.putAll(addResourcesToCache(resources))
        }

        if (cache) {
            result.putAll(cache)
        }
        return result
    }

    Map<String, Resource> addResourcesToCache(List<String> resources) {
        Map<String, Resource> result = [:]
        resources?.each { Resource resource ->
            if (resource != null) {
                String compositeKey = getResourceKey(resource)
                Resource existingByCompositeKey = result.get(compositeKey)
                if (resource.active || !existingByCompositeKey) {
                    result.put(compositeKey, resource)
                }
                Resource existingByResourceId = result.get(resource.resourceId)
                if (resource.active || !existingByResourceId ||
                        (resource.defaultProfile && !existingByResourceId.defaultProfile && !existingByResourceId.active)) {
                    result.put(resource.resourceId, resource)
                }
            }
        }
        return result
    }

    String getLocalizedName(Resource resource) {
        if (!resource) {
            return null
        }

        String fallback = "Localized name missing for resource ${resource.resourceId}"
        return localizationService.getLocalizedProperty(resource, "name", "name", fallback)
    }

    String getUiLabel(Resource resource) {
        if (!resource) {
            return null
        }

        "${getLocalizedName(resource)}${resource.technicalSpec ? ', ' + resource.technicalSpec : ''}${resource.commercialName ? ', ' + resource.commercialName : ''}" +
                "${resource.manufacturer ? ' (' + resource.manufacturer + ')' : ''}"
    }

    String getUiLabel(String resourceId, String profileId,
                      Boolean alsoInactive = Boolean.FALSE, Boolean includeId = Boolean.FALSE) {
        if (!resourceId && !profileId) {
            return null
        }

        return getUiLabel(getResourceByResourceAndProfileId(resourceId, profileId, alsoInactive, includeId))
    }

    String getLocalizedProfileName(Resource resource) {
        if (!resource) {
            return null
        }
        return resource.profileNameEN ?: resource.profileId
    }

/**
 * SW-1525
 * Get value based on attribute name for resource.
 * if null then check for value in resource subtype
 * @param attribute
 * @param object
 * @param resourceCache
 * @return value
 * */
    Object resourceResourceTypeValueGetterByAttributeName(String attribute, Resource object, ResourceCache resourceCache = null) {

        def value
        if (attribute && object) {
            value = DomainObjectUtil.callGetterByAttributeName(attribute, object)
            if (!value && resourceCache) {
                ResourceTypeCache resourceTypeCache = resourceCache.getResourceTypeCache()
                ResourceType resourceSubType = resourceTypeCache?.getResourceSubType(object)
                if (resourceSubType) {
                    value = DomainObjectUtil.callGetterByAttributeName(attribute, resourceSubType)
                }
            } else if (!value && !resourceCache) {
                ResourceType resourceSubType = object.getSubType()
                if (resourceSubType) {
                    value = DomainObjectUtil.callGetterByAttributeName(attribute, resourceSubType)
                }
            }
        }
        return value
    }
}