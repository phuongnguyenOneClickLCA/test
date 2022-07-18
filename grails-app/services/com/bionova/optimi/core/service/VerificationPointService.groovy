package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.ui.VPLocation
import com.bionova.optimi.ui.VerificationPoint
import grails.gorm.transactions.Transactional
import grails.web.mapping.LinkGenerator

@Transactional
class VerificationPointService {

    def queryService
    def loggerUtil
    def flashService
    def questionService
    def entityService
    def querySectionService
    LinkGenerator grailsLinkGenerator

    /**
     * Check in indicator if it has any verification points
     * @param indicator
     * @param parent
     * @param checkHiddenQuestions questions and sections should always be checked if they are hidden in indicator. Set to false if this check has been done before
     * @return
     */
    boolean isIndicatorHavingVerificationPoints(Indicator indicator, Entity parent, Boolean checkHiddenQuestions = true) {
        try {
            if (indicator) {
                boolean isComponentEntity = entityService.isComponentEntity(parent)
                List<Query> queries = queryService.getQueriesByIndicator(indicator, parent?.features, false, isComponentEntity, true)
                if (queries) {
                    for (Query query in queries) {
                        if (isQueryHavingVerificationPoints(query, indicator, checkHiddenQuestions)) {
                            return true
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in isIndicatorHavingVerificationPoints", e)
            flashService.setErrorAlert("Error in isIndicatorHavingVerificationPoints: ${e.message}", true)
        }
        return false
    }

    /**
     * Check in query if it has any verification points
     * @param query
     * @param indicator
     * @param checkHiddenQuestions questions and sections should always be checked if they are hidden in indicator. Set to false if this check has been done before
     * @return
     */
    boolean isQueryHavingVerificationPoints(Query query, Indicator indicator, Boolean checkHiddenQuestions = true) {
        try {
            if (query) {
                for (QuerySection section in query.sections) {
                    if (section?.verificationPoints?.size() > 0) {
                        if (checkHiddenQuestions) {
                            // return true if section is not meant to be hidden
                            if (!indicator?.hideSectionTotally(section)) {
                                return true
                            }
                        } else {
                            return true
                        }
                    }

                    if (section?.questions) {
                        for (Question question in section.questions) {
                            if (question?.verificationPoints?.size() > 0) {
                                if (checkHiddenQuestions) {
                                    // return true if question is not meant to be hidden
                                    if (!questionService.isQuestionHidden(indicator, section, question)) {
                                        return true
                                    }
                                } else {
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in isQueryHavingVerificationPoints", e)
            flashService.setErrorAlert("Error in isQueryHavingVerificationPoints: ${e.message}", true)
        }
        return false
    }

    /**
     * Gets set of verification points objects (defined in indicator) that are allowed to show in entity
     *
     * @param indicator
     * @param parent
     * @param childEntityId
     * @return
     */
    Set<VerificationPoint> getVerificationPointsForIndicatorAndEntity(Indicator indicator, Entity parent, String childEntityId) {
        Set<VerificationPoint> verificationPoints = []
        if (indicator) {
            try {
                List<Feature> licensedFeatures = parent?.features
                boolean isComponentEntity = entityService.isComponentEntity(parent)
                List<Query> queries = queryService.getQueriesByIndicator(indicator, licensedFeatures, false, isComponentEntity, true)
                String entityId = parent?.id?.toString()
                queries?.each { Query query ->
                    if (query) {
                        query.sections?.each { QuerySection section ->
                            if (section) {
                                if (section.verificationPoints?.size() > 0) {
                                    String locationId = getVpLocationId(null, section)
                                    String url = getUrl(locationId, indicator?.indicatorId, query?.queryId, entityId, childEntityId)
                                    addVerificationPoint(section?.verificationPoints, verificationPoints, locationId,
                                            querySectionService.getLocalizedShortName(section), query, url)
                                }

                                section?.questions?.each { Question q ->
                                    if (q?.verificationPoints?.size() > 0) {
                                        String locationId = getVpLocationId(q, null)
                                        String url = getUrl(locationId, indicator?.indicatorId, query?.queryId, entityId, childEntityId)
                                        addVerificationPoint(q?.verificationPoints, verificationPoints, locationId, questionService.getLocalizedQuestionShort(q), query, url)
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error in getVerificationPointsForIndicator", e)
                flashService.setErrorAlert("Error in getVerificationPointsForIndicator: ${e.message}", true)
            }
        }
        return verificationPoints
    }

    /**
     * Get the display text for Verification points from vPointsConfig
     * @param question
     * @param section
     * @return
     */
    String getVPointsUI(Question question, QuerySection section) {
        if (question || section) {
            List<String> verificationPoints = question?.verificationPoints ?: section?.verificationPoints ?: []

            return getGroupedVpoints(verificationPoints, false)
        }
        return ''
    }

    /**
     * Get a unique id on UI for verification point
     * @param question
     * @param section
     * @return
     */
    String getVpLocationId(Question question, QuerySection section) {
        if (question || section) {
            String identifier = question?.questionId ?: section?.sectionId ?: 'none'
            List<String> verificationPoints = question?.verificationPoints ?: section?.verificationPoints ?: []

            return identifier + '-' + getGroupedVpoints(verificationPoints)
        }
        return ''
    }

    /**
     * At one place (a question or section), the config can define multiple verification points.
     * This whole list is considered as a group vPoints at that place {@link VPLocation#groupName}
     * @param vPoints
     * @return
     */
    String getGroupedVpoints(List<String> vPoints, boolean noSpace = true) {
        if (noSpace) {
            return vPoints?.sort()?.join('-') ?: ''
        } else {
            return vPoints?.sort()?.join(', ') ?: ''
        }
    }

    /**
     * Get URL for a verification point with anchor tag in url so user can jump to the point by clicking the link
     * @param locationId
     * @param indicatorId
     * @param queryId
     * @param parentEntityId
     * @param childEntityId
     * @return
     */
    String getUrl(String locationId, String indicatorId, String queryId, String parentEntityId, String childEntityId) {
        if (locationId && indicatorId && queryId && parentEntityId && childEntityId) {
            return grailsLinkGenerator.link(controller: "query", action: "form", params: [indicatorId: indicatorId, queryId: queryId, entityId: parentEntityId, childEntityId: childEntityId, scrollToVP: locationId], absolute: true)
        }
        return ''
    }

    /**
     * Add new VP object to list. If already exists, add a new location to the existing VP object
     * Extract the VPs from vPointsConfig and populate more info into the object, e.g. location, url etc.
     * @param vPointsConfig
     * @param verificationPoints
     * @param locationId
     * @param locationName
     * @param query
     * @param url
     */
    void addVerificationPoint(List<String> vPointsConfig, Set<VerificationPoint> verificationPoints, String locationId, String locationName, Query query, String url) {
        if (vPointsConfig && verificationPoints != null && locationId && locationName && query && url) {
            String groupName = getGroupedVpoints(vPointsConfig, false)
            vPointsConfig?.each { String point ->
                if (point) {
                    boolean addNew = false
                    VerificationPoint VP = verificationPoints?.find { it.pointName == point }

                    if (!VP) {
                        VP = new VerificationPoint()
                        VP.pointName = point
                        addNew = true
                    }

                    VPLocation location = new VPLocation()
                    location.groupName = groupName
                    location.queryId = query.queryId
                    location.queryName = query.localizedName
                    location.localizedName = locationName
                    location.url = url
                    VP.locations.add(location)

                    if (addNew) {
                        verificationPoints.add(VP)
                    }
                }
            }
        }
    }
}
