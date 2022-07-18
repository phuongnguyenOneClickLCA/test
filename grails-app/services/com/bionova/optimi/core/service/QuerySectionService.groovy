package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.core.util.LoggerUtil
import org.springframework.context.i18n.LocaleContextHolder

class QuerySectionService {

    LoggerUtil loggerUtil
    FlashService flashService
    def queryService
    def datasetService
    def localizedLinkService
    def licenseService

    List<QuerySection> compileVirtualSections(Query query) {
        List<QuerySection> virSections = []
        try {
            if (query) {
                // compile the query sections from virtual sections
                query.virtualSections?.each { virSections.add(it.compileVirtualSection(query?.sections)) }
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in compileVirtualSections", e)
            flashService.setErrorAlert("Error occurred in compileVirtualSections: ${e.getMessage()}", true)
        }
        return virSections
    }

    List<QuerySection> compileCompatibleVirtualSectionsForNewProjectModal(Query query, String entityClass) {
        List<QuerySection> compatibleVirSections = []
        try {
            if (query && entityClass) {
                boolean takeVirtualSection = !Constants.EntityClass.notSupportedForTemplate.contains(entityClass)
                // Take the virtual sections that have compatible EntityClasses and match, don't have any compatibleEntityClasses (hardcoded sections, if entity class is supported by this feature), and min_info section for portfolio
                compatibleVirSections = compileVirtualSections(query)?.findAll({
                    getCompatibleEntityClasses(it.questions, it.sections)?.contains(entityClass) || (!getCompatibleEntityClasses(it.questions, it.sections) && takeVirtualSection) || (entityClass == Constants.EntityClass.PORTFOLIO.type && it.virtualSectionId == Constants.BasicQueryVirtualSectionIds.MIN_INFO.toString())
                })
            }
        } catch (e) {
            loggerUtil.error(log, "Error occurred in compileCompatibleVirtualSectionsForNewProjectModal", e)
            flashService.setErrorAlert("Error occurred in compileCompatibleVirtualSectionsForNewProjectModal: ${e.getMessage()}", true)
        }
        return compatibleVirSections
    }

    /**
     * Check in entity's datasets and set the hasVerifiedDatasets in query sections
     * @param query
     * @param entity design with datasets
     */
    void setHasVerifiedDatasetsInSections(Query query, Entity entity) {
        try {
            if (entity?.datasets?.size() > 0 && query?.sections?.size() > 0) {
                List<String> sectionIds = queryService.getAllSectionIdsInQuery(query)
                Set<Dataset> datasets = datasetService.getDatasetsByEntityAndQueryIdAndSectionIds(entity, query.queryId, sectionIds)

                if (datasets) {
                    query.sections?.each { QuerySection section ->
                        section.hasVerifiedDatasets = hasVerifiedDatasetInSection(query.queryId, section, datasets)
                    }
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in setHasVerifiedDatasetsInSection", e)
            flashService.setErrorAlert("Error in setHasVerifiedDatasetsInSection: ${e.getMessage()}", true)
        }
    }

    /**
     * Check if datasets has a verified dataset in this section. Return false if not or an exception occurs
     * @param queryId
     * @param section
     * @param datasets
     * @return
     */
    boolean hasVerifiedDatasetInSection(String queryId, QuerySection section, Set<Dataset> datasets) {
        try {
            if (queryId && section && datasets) {
                return datasets?.find({ it.verified && it.sectionId == section.sectionId && it.queryId == queryId }) ? true : false
            }
        } catch (e) {
            loggerUtil.error(log, "Error in hasVerifiedDatasetInSection", e)
            flashService.setErrorAlert("Error in hasVerifiedDatasetInSection: ${e.getMessage()}", true)
        }
        return false
    }

    String getLocalizedExtraName(QuerySection querySection,String indicatorId) {
        if(!querySection) {
            return null
        }
        String nameExtra = ""
        if (indicatorId && querySection.appendToSectionNameForIndicatorId) {
            Map<String, String> extraNameString = querySection.appendToSectionNameForIndicatorId.get(indicatorId)

            if (extraNameString) {
                def language = LocaleContextHolder.getLocale().getLanguage().toUpperCase()
                nameExtra = extraNameString.get(language) ?: extraNameString.get("EN")
            }
        }
        return nameExtra
    }

    String getLocalizedShortName(QuerySection querySection) {
        if(!querySection) {
            return null
        }
        if (querySection.shortName) {
            def localizedShortName = querySection.getLocalizedShortName()

            if (localizedShortName) {
                return localizedShortName
            } else {
                return querySection.getLocalizedName()
            }
        } else {
            return querySection.getLocalizedName()
        }
    }

    String getLocalizedHelp(QuerySection querySection) {
        if (!querySection) {
            return null
        }
        String localizedHelp = querySection.getLocalizedHelp()
        return localizedLinkService.getTransformStringIdsToLinks(localizedHelp)
    }

    List<QuerySection> getIncludedSections(Map<String, List<String>> includeSectionIds) {
        return getSectionsByQueryIds(includeSectionIds)
    }

    private List<QuerySection> getSectionsByQueryIds(Map<String, List<String>> querySections) {
        List<QuerySection> sections = []

        if (querySections) {
            List<String> queryIds = querySections.keySet().asList()
            List<Query> queries = queryService.getQueriesByQueryIds(queryIds, true)
            List<String> sectionIds = []
            List<QuerySection> foundSections = []

            queries.each {
                sectionIds = querySections.get(it.queryId)
                foundSections = it.sections?.findAll { s -> sectionIds.contains(s.sectionId) }

                if (!foundSections) {
                    loggerUtil.error(log, "No includable / additional sections found for query " + it.queryId + " with sectionIds: " + sectionIds)
                } else {
                    sections.addAll(foundSections)
                }
            }
        }
        return sections
    }

    List<Question> getQuestionsByEntityClass(String entityClass, String queryId = null, List<Question> questions) {
        List<Question> questionsByEntityClass = new ArrayList<Question>()

        questions?.each { Question question ->
            if (entityClass && question.entityClasses) {
                if (entityClass && question.entityClasses.contains(entityClass)) {
                    question.queryId = queryId
                    questionsByEntityClass.add(question)
                }
            } else {
                question.queryId = queryId
                questionsByEntityClass.add(question)
            }
        }
        return questionsByEntityClass
    }

    boolean isSectionLicensed(Entity entity, QuerySection querySection) {
        return licenseService.isQueryOrSectionOrQuestionLicensed(querySection, null, entity)
    }

    List<Question> getAllUnderlyingQuestions(String entityClass, QuerySection querySection) {
        List<Question> questionsByEntityClass = new ArrayList<Question>()

        if (querySection) {
            List<Question> questions = querySection.questions
            questions?.each { Question question ->
                if (entityClass && question.entityClasses) {
                    if (entityClass && question.entityClasses.contains(entityClass)) {
                        questionsByEntityClass.add(question)
                    }
                } else {
                    questionsByEntityClass.add(question)
                }
            }

            getIncludedSections(querySection.includeSectionIds)?.each { QuerySection section ->
                section.questions?.each { Question question ->
                    if (entityClass && question.entityClasses) {
                        if (entityClass && question.entityClasses.contains(entityClass)) {
                            questionsByEntityClass.add(question)
                        }
                    } else {
                        questionsByEntityClass.add(question)
                    }
                }
            }

            List<QuerySection> sections = querySection.sections
            sections?.each { QuerySection section ->
                section.questions?.each { Question question ->
                    if (entityClass && question.entityClasses) {
                        if (entityClass && question.entityClasses.contains(entityClass)) {
                            questionsByEntityClass.add(question)
                        }
                    } else {
                        questionsByEntityClass.add(question)
                    }
                }
            }
        }
        return questionsByEntityClass
    }
    /*
        This is used for collecting the compatible entity classes in the virtualSection
     */
    List<String> getCompatibleEntityClasses(List<Question> questions, List<QuerySection> sections){
        List<String> compatibleClasses = []
        try {
            questions?.each {question ->
                if (question.entityClasses && !compatibleClasses?.contains(question.entityClasses)){
                    compatibleClasses += question.entityClasses
                }
            }
            sections?.each { section ->
                section?.questions?.each {question ->
                    if (question.entityClasses && !compatibleClasses?.contains(question.entityClasses)){
                        compatibleClasses += question.entityClasses
                    }
                }
            }
        } catch (e) {
            loggerUtil.warn(log,"Error occurred in getCompatibleEntityClasses", e)
            flashService.setErrorAlert("Error occurred in getCompatibleEntityClasses: ${e.getMessage()}", true)
        }

        return compatibleClasses
    }
}
