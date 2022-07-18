package com.bionova.optimi.util


import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.FlashService
import com.bionova.optimi.core.service.IndicatorQueryService
import com.bionova.optimi.core.util.DomainObjectUtil
import groovy.transform.CompileStatic
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.bson.Document

/**
 * Created by pmm on 11/6/15.
 */
class ResourceFilterCriteriaUtil {

    private static Log log = LogFactory.getLog(ResourceFilterCriteriaUtil.class)
    FlashService flashService
    IndicatorQueryService indicatorQueryService

    boolean resourceOkAgainstFilterCriteria(Resource resource, String queryId, Indicator indicator, Document resourceAsDocument = null, Entity child = null, Boolean organizationFilterCriteria = null, Question question = null) {
        boolean ok = true
        boolean resourceExistAndActive = (resource && resource.active) || (resourceAsDocument && resourceAsDocument.active)
        if (resourceExistAndActive && queryId && indicator) {
            IndicatorQuery indicatorQueryWithResourceFilter

            if (organizationFilterCriteria) {
                indicatorQueryWithResourceFilter = indicator.indicatorQueries?.find({
                    it.queryId.equals(queryId) && it.organisationResourceFilterCriteria
                })
            } else {
                indicatorQueryWithResourceFilter = indicator.indicatorQueries?.find({
                    it.queryId.equals(queryId) && it.resourceFilterCriteria
                })
            }

            if (indicatorQueryWithResourceFilter) {
                Map<String, List<Object>> criteria = [:]
                Map<String,List<Object>> skipCriteria = [:]

                if (organizationFilterCriteria) {
                    criteria = indicatorQueryService.getFormattedFilterCriteria(child, IndicatorQuery.ORGANISATION_RESOURCE, indicatorQueryWithResourceFilter)
                } else {
                    criteria = indicatorQueryService.getFormattedFilterCriteria(child, IndicatorQuery.RESOURCE, indicatorQueryWithResourceFilter)
                }
                if(question){
                    if(question.skipResourceTypes){
                        skipCriteria.put("resourceType", question.skipResourceTypes)
                        skipCriteria.put("resourceSubType", question.skipResourceTypes)
                    } else if (question.allowResourceTypes) {
                        criteria.put("resourceType",question.allowResourceTypes)
                        criteria.put("resourceSubType", question.allowResourceTypes)
                    }

                    if(question.resourceGroups){
                        criteria.put("resourceGroup",question.resourceGroups)
                    }
                    if (question.requiredEnvironmentDataSourceStandards && !question.requiredEnvironmentDataSourceStandards.isEmpty()) {
                        criteria.put("environmentDataSourceStandard", question.requiredEnvironmentDataSourceStandards)
                    }
                    if (question.filterResources && !question.filterResources.isEmpty()) {
                        question.filterResources.each { String key, String value ->
                            criteria.put(key, [value])
                        }
                    }

                }
                ok = checkCriteriaMapForResource(resource,resourceAsDocument,criteria,ok,Boolean.FALSE)

                ok = checkCriteriaMapForResource(resource,resourceAsDocument,skipCriteria,ok,Boolean.TRUE)
                //TODO: 16537 Moved to separate method to reduce duplicated lines. Will remove once ticket is closed
                /*Iterator<String> iterator = criteria?.keySet()?.iterator()

                while (iterator?.hasNext()) {
                    String resourceAttribute = iterator.next()

                    if (resourceAttribute) {
                        List<Object> requiredValues = criteria.get(resourceAttribute)

                        if (requiredValues?.size()) {
                            def resourceValue

                            if (resourceAsDocument) {
                                resourceValue = resourceAsDocument.get(resourceAttribute)
                            } else {
                                resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource, "${resourceAttribute}: ${requiredValues}")
                            }

                            if (resourceValue != null) {
                                if (resourceValue instanceof List) {
                                    if (!CollectionUtils.containsAny(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }), requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                        ok = false
                                        break
                                    }
                                } else if (!requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                    ok = false
                                    break
                                }
                            } else {
                                if (!requiredValues.contains(null)) {
                                    ok = false
                                    break
                                }
                            }
                        }
                    }
                }*/
            }
        }
        return ok
    }

    @CompileStatic
    private boolean checkCriteriaMapForResource(Resource resource, Document resourceAsDocument, Map<String,List<Object>> criteria, boolean ok, Boolean toSkip) {
        try {
            Iterator<String> iterator = criteria?.keySet()?.iterator()
            while (iterator?.hasNext()) {
                String resourceAttribute = iterator.next()

                if (resourceAttribute) {
                    List<Object> requiredValues = criteria.get(resourceAttribute)

                    if (requiredValues?.size()) {
                        def resourceValue

                        if (resourceAsDocument) {
                            resourceValue = resourceAsDocument.get(resourceAttribute)
                        } else {
                            resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource, "${resourceAttribute}: ${requiredValues}")
                        }

                        if (resourceValue != null) {
                            if (resourceValue instanceof List) {
                                if (!toSkip && !CollectionUtils.containsAny(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }), requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                    ok = false
                                    break
                                } else if (toSkip && CollectionUtils.containsAny(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }), requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                    ok = false
                                    break
                                }
                            } else {
                                if (!toSkip && !requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                    ok = false
                                    break
                                } else if(toSkip && requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                    ok = false
                                    break
                                }
                            }
                        } else {
                            if (!requiredValues.contains(null) && !toSkip) {
                                ok = false
                                break
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when checking filterCriteria map : ${criteria} for resource ${resource?.resourceId ?: resourceAsDocument?.resourceId}")
            flashService.setErrorAlert("Error when checking filterCriteria map : ${criteria} for resource ${resource?.resourceId ?: resourceAsDocument?.resourceId}: ${e.getMessage()}", true)
        }

        return ok
    }

    @CompileStatic
    public boolean resourceOkAgainstFilterCriteria(Resource resource, Document resourceAsDocument, Map<String, List<Object>> criteria, Boolean checkHasAllIfList = false) {
        boolean ok = true

        if ((resource || resourceAsDocument) && criteria) {
            Iterator<String> iterator = criteria.keySet().iterator()
            while (iterator?.hasNext()) {
                String resourceAttribute = iterator.next()

                if (resourceAttribute) {
                    List<Object> requiredValues = criteria.get(resourceAttribute)

                    if (requiredValues?.size()) {
                        def resourceValue

                        if (resourceAsDocument) {
                            resourceValue = resourceAsDocument.get(resourceAttribute)
                        } else {
                            resourceValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource, "${resourceAttribute}: ${requiredValues}")
                        }

                        if (resourceValue != null) {
                            if (resourceValue instanceof List) {
                                if (checkHasAllIfList) {
                                    if (!requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).containsAll(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                        ok = false
                                        break
                                    }
                                } else {
                                    if (!CollectionUtils.containsAny(resourceValue.findResults({ it != null ? it.toString().toLowerCase() : "null" }), requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }))) {
                                        ok = false
                                        break
                                    }
                                }
                            } else if (!requiredValues.findResults({ it != null ? it.toString().toLowerCase() : "null" }).contains(resourceValue.toString().toLowerCase())) {
                                ok = false
                                break
                            }
                        } else {
                            if (!requiredValues.contains(null)) {
                                ok = false
                                break
                            }
                        }
                    }
                }
            }
        }
        return ok
    }

    @CompileStatic
    boolean resourceOkAgainstAllFilterCriteria(Resource resource, String queryId, Indicator indicator, Document resourceAsDocument = null, Entity child = null, List<String> oganizationDataList = null, boolean isUncapped = false, List<String> units = null, Question question = null) {
        boolean ok = false
        String resourceId = resource ? resource.resourceId : resourceAsDocument ? resourceAsDocument.resourceId : null
        boolean unitPassed = resourceHasUnitMatched(resource, resourceAsDocument, units)

        if (resourceOkAgainstFilterCriteria(resource, queryId, indicator, resourceAsDocument, child, Boolean.FALSE, question) && resourceId && unitPassed) {
            boolean okAgainstOrgFilter = resourceOkAgainstFilterCriteria(resource, queryId, indicator, resourceAsDocument, child, Boolean.TRUE) && indicator?.indicatorQueries?.find({ it.queryId == queryId && it.organisationResourceFilterCriteria })
            if (!okAgainstOrgFilter || isUncapped || (okAgainstOrgFilter && !isUncapped && oganizationDataList && oganizationDataList.contains(resourceId))) {
                ok = true
            }
        }
        return ok
    }

    boolean resourceHasUnitMatched(Resource resource, Document resourceAsDocument, List<String> units) {
        List<String> combinedUnits = resource ? resource.combinedUnits : resourceAsDocument ? resourceAsDocument.get("combinedUnits") : []
        boolean passed = !units || (units && combinedUnits && CollectionUtils.containsAny(units, combinedUnits.collect({ it.toString().toLowerCase() })))
        return passed
    }
}
