package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import groovy.transform.TypeCheckingMode

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class DefaultValue {
    static mapWith = "mongo"
    String defaultValueId
    @Translatable
    Map<String, String> name
    String type // possible values: resourceType, resource or query
    List<DefaultValueSet> defaultValueSets
    String applicationId
    String linkedQuestionId
    String linkedQueryId

    static transients = [
            'resourceTypes',
            'linkedQuestion',
            'applicationId',
            'linkedQuestion'
    ]

    static embedded = ['defaultValueSets']

    transient queryService
    transient resourceTypeService

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Question getLinkedQuestion() {
        Question linkedQuestion

        if (linkedQuestionId) {
            Query query

            if (linkedQueryId) {
                query = queryService.getQueryByQueryId(linkedQueryId, true)
            } else {
                query = queryService.getQueryByQueryId(Constants.ADDITIONAL_QUESTIONS_QUERY_ID, true)
            }

            if (query) {
                linkedQuestion = query.getAllQuestions()?.find({ Question q -> linkedQuestionId.equals(q.questionId) })
            }

        }
        return linkedQuestion
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    List<ResourceType> getResourceTypes() {
        List<ResourceType> resourceTypes

        if ("resourceType".equals(type)) {
            resourceTypes = resourceTypeService.getActiveResourceTypes()?.findAll({ !it.subType })?.sort { resourceTypeService.getLocalizedName(it) }
        }
        return resourceTypes
    }
}
