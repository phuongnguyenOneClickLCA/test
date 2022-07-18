/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.QuerySection
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.DomainObjectUtil
import org.bson.Document

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class QueryTagLib {

    def queryService
    def questionService
    def datasetService
    def userService
    def optimiStringUtils
    def nmdResourceService
    def groovyPageRenderer
    def optimiResourceService

    def resourceNameMaxLength = { attrs ->
        Resource resource = attrs.resource
        int maxLength = attrs.limit ?: 35
        if(resource?.construction) //if resource is a group
            maxLength -= 7
        if(!resource?.active){
            maxLength -= 3
        }
        if(resource?.isExpiredDataPoint){
            maxLength -= 3
        }
        if(resource?.showWarning){
            maxLength -= 4
        }
        out << maxLength;
    }

    def questionInlineStyle = { attrs ->
        Question question = attrs.question
        Boolean showMandatory = attrs.showMandatory
        String answer = attrs.answer
        String style = ""
        def additionalQuestions = attrs.additionalQuestions
        Boolean hide = attrs.hide
        Integer additionalQuestionInputWidth = attrs.additionalQuestionInputWidth

        if (question) {
            if ((question.inputWidth||additionalQuestionInputWidth) || question.inputHeight) {
                style = "style=\"${additionalQuestionInputWidth ? 'width: ' + additionalQuestionInputWidth + 'px;' : question.inputWidth ? 'width: ' + question.inputWidth + 'px;' : ''}${question.inputHeight ? ' height: ' + question.inputHeight + 'px;' : ''}"
            }
            if (additionalQuestions) {
                style = (!style ? "style=\"" : "") + "${style} style: inline-table;"
            }

            if (showMandatory && !answer && !question.optional) {
                style = (!style ? "style=\"" : "") + "${style} border: 1px solid red;"
            }

            if (hide) {
                style = (!style ? "style=\"" : "") + "${style} display: none;"
            }
        }
        style = style ? style + "\"" : ""
        out << style
    }

    def resourceLabel = { attrs ->
        Indicator indicator = attrs.indicator
        Resource resource = attrs.resource
        Query query = attrs.query
        String label
        Integer maxCommercialName = 100
        String commercialName
        def profileLabel = attrs.profileLabel

        if (resource) {
            List<String> extraInformation = indicator?.resolveResourceExtraInformationDropdown

            if (profileLabel) {
                label = optimiResourceService.getLocalizedProfileName(resource)

                if (extraInformation) {
                    String resourceAttribute

                    for (int i = 0; i < extraInformation.size(); i++) {
                        resourceAttribute = extraInformation.get(i)
                        String attributeValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource)

                        if (attributeValue?.trim()) {
                            if (i == 0) {
                                label = label + " - " + attributeValue
                            } else {
                                label = label + " / " + attributeValue
                            }
                        }
                    }
                }
            } else {

                if (resource.commercialName && resource.commercialName?.length() <= maxCommercialName) {
                    commercialName = resource.commercialName
                } else if (resource.commercialName && resource.commercialName?.length() > maxCommercialName) {
                    commercialName = resource.commercialName.take(maxCommercialName) + "..."
                }
                label = "${optimiResourceService.getLocalizedName(resource)}" +
                        "${resource?.hasUnit && query?.showResourceUnit ? '(' + resource.unitForData + ')' : ''}" +
                        "${resource.technicalSpec ? ', ' + resource.technicalSpec : ''}" +
                        "${commercialName ? ', ' + commercialName : ''}" +
                        "${resource.manufacturer ? ' (' + resource.manufacturer + ')' : ''}"

                if (extraInformation) {
                    int i = 0

                    for (String resourceAttribute in extraInformation) {
                        String attributeValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource)

                        if (attributeValue?.trim()) {
                            if (i == 0) {
                                label = label + " - " + attributeValue
                            } else {
                                label = label + " / " + attributeValue
                            }
                        }
                        i++
                    }
                }
                if (resource.multipleProfiles) {
                    label = "${label} / ${message(code: 'resource.multiple_profiles')}"
                }
            }
        }
        out << label
    }

    def resourceLabelForMap = { attrs ->
        List<String> extraInformation = attrs.extraInformation
        Document resourceAsMap = attrs.resource
        Query query = attrs.query
        String label
        String locale = "name" + DomainObjectUtil.getMapKeyLanguage()

        if (resourceAsMap) {
            if (resourceAsMap.customDatasetName) {
                label = resourceAsMap.customDatasetName
            } else {
                String commercialName = abbr(value: resourceAsMap.get("commercialName"), maxLength: 100)
                String name = abbr(value: resourceAsMap.get(locale) ? resourceAsMap.get(locale) : resourceAsMap.get("nameEN"), maxLength: 100)
                String unit = resourceAsMap.get("unitForData")
                String technicalSpec = abbr(value: resourceAsMap.get("technicalSpec"), maxLength: 100)
                String manufacturer = abbr(value: resourceAsMap.get("manufacturer"), maxLength: 100)
                label = "${name ? name : ''}" +
                        "${query?.showResourceUnit && unit ? '(' + unit + ')' : ''}" +
                        "${technicalSpec ? ', ' + technicalSpec : ''}" +
                        "${commercialName ? ', ' + commercialName : ''}" +
                        "${manufacturer ? ' (' + manufacturer + ')' : ''}"

                label = nmdResourceService.appendCategoryAndNmdCodeToName(resourceAsMap, label)

                if (extraInformation) {
                    Integer i = 0

                    for (String resourceAttribute in extraInformation) {
                        String attributeValue = resourceAsMap.get(resourceAttribute)

                        if (attributeValue?.trim() && !"-".equals(attributeValue)) {
                            if (i == 0) {
                                label = label + " - " + attributeValue
                            } else {
                                label = label + " / " + attributeValue
                            }
                        }
                        i++
                    }
                }
                if (resourceAsMap.get("multipleProfiles")) {
                    label = "${label} / ${message(code: 'resource.multiple_profiles')}"
                }
            }
        }
        out << label
    }

    def singleResourceLabel = { attrs ->
        Indicator indicator = attrs.indicator
        List<String> extraInformation = indicator?.resolveResourceExtraInformationDropdown
        Resource resource = attrs.resource
        Query query = attrs.query
        String label

        if (resource) {
            String commercialName = abbr(value: resource.commercialName, maxLength: 100)
            String name = abbr(value: optimiResourceService.getLocalizedName(resource), maxLength: 100)
            String unit = resource.unitForData
            String technicalSpec = abbr(value: resource.technicalSpec, maxLength: 100)
            String manufacturer = abbr(value: resource.manufacturer, maxLength: 100)
            label = "${name ? name : ''}" +
                    "${query?.showResourceUnit && unit ? '(' + unit + ')' : ''}" +
                    "${technicalSpec ? ', ' + technicalSpec : ''}" +
                    "${commercialName ? ', ' + commercialName : ''}" +
                    "${manufacturer ? ' (' + manufacturer + ')' : ''}"

            if (extraInformation) {
                Integer i = 0

                for (String resourceAttribute in extraInformation) {
                    String attributeValue = DomainObjectUtil.callGetterByAttributeName(resourceAttribute, resource)

                    if (attributeValue?.trim() && !"-".equals(attributeValue)) {
                        if (i == 0) {
                            label = label + " - " + attributeValue
                        } else {
                            label = label + " / " + attributeValue
                        }
                    }
                    i++
                }
            }
            if (resource.multipleProfiles) {
                label = "${label} / ${message(code: 'resource.multiple_profiles')}"
            }
        }
        out << label
    }

    def renderProfileOptions = { attrs ->
        List<Resource> resourceProfiles = attrs.resourceProfiles
        Indicator indicator = attrs.indicator
        String profileAnswer = attrs.profileAnswer
        String options = ""

        if (resourceProfiles) {
            for (Resource profile in resourceProfiles) {
                if (profileAnswer) {
                    options = "${options}<option value=\"${profile.profileId}\"${profileAnswer.equals(profile.profileId) ? ' selected=\"selected\"' : ''}>${resourceLabel(indicator: indicator, resource: profile, profileLabel: true)}</option>"
                } else {
                    options = "${options}<option value=\"${profile.profileId}\"${profile.defaultProfile ? ' selected=\"selected\"' : ''}>${resourceLabel(indicator: indicator, resource: profile, profileLabel: true)}</option>"
                }
            }
        }
        out << options
    }
    def renderStateOptions = {attrs ->
        List<Document> stateList = attrs.statesList
        String stateAnswer = attrs.stateAnswer
        String defaultAnswer = attrs.defaultAnswer
        List<String> resourceGroups = attrs.resourceGroups
        String options = ""
        String resourceGroup

        if(stateList) {
            if(stateAnswer){
                resourceGroup = stateList.find({it.resourceId.equals(stateAnswer)})?.resourceGroup
            }else if(defaultAnswer){
                resourceGroup = defaultAnswer
                options="<option value=\"\" selected=\"selected\">${message(code: 'query.choose_resource')}</option>"

            }else {
                options="<option value=\"\" selected=\"selected\">${message(code: 'query.choose_resource')}</option>"
            }
            for (Document state in stateList) {
                String classForOption = state.resourceGroup.find({resourceGroups?.contains(it)})
                options = "${options}<option class=\"stateOption ${classForOption}${resourceGroup && !resourceGroup?.contains(classForOption) ? ' hidden': ''}\" value=\"${state.resourceId}\"${stateAnswer.equals(state.resourceId) ? ' selected=\"selected\"' : ''}>${state.localizedName}</option>"
            }
        }
        out << options

    }

    def renderSfbCodeQuestion = {attrs ->
        List<Document> elementList = attrs.elementList
        String answer = attrs.answer
        Resource resource = attrs.resource
        Boolean newResourceRowAdded = attrs.newResourceRowAdded
        String options = ""
        Integer elementCode

        if (elementList) {
            if (newResourceRowAdded) {
                if (resource && resource.nmdElementId?.isNumber()) {
                    elementCode = resource.nmdElementId.toInteger()
                } else if(answer && answer.isNumber()) {
                    elementCode = answer.toInteger()
                }
            } else {
                if(answer && answer.isNumber()) {
                    elementCode = answer.toInteger()
                } else if (resource && resource.nmdElementId?.isNumber()) {
                    elementCode = resource.nmdElementId.toInteger()
                }
            }
            Integer parentElementId = elementList?.find({it.elementId == elementCode})?.parentElementId
            elementCode = parentElementId ?: elementCode
            for (Document element in elementList) {
                if(element.elementId){
                    if(!element.parentElementId){
                        options = "${options}<option value=\"${element.elementId}\" ${elementCode?.equals(element.elementId) ? ' selected=\"selected\"' : ''}>${element.code} ${element.name}</option>"
                    }
                }
            }
        }
        out << options

    }

    def blockOrNone = { attrs ->
        def entity = attrs.entity
        def queryId = attrs.queryId
        def sectionId = attrs.sectionId
        def questionId = attrs.questionId
        def dataset = datasetService.getUniqueDataset(entity, queryId, sectionId, questionId, null)
        def answerId = attrs.answerId
        def attributes = "none"
        def answerIds = dataset?.answerIds

        if (answerIds?.contains(answerId)) {
            attributes = "block"
        }
        out << attributes
    }

    def disableEnableField = { attrs ->
        Entity entity = attrs.entity
        def modifiable = attrs.modifiable

        if (entity) {
            def var = attrs.var
            def attributes = ""

            if (entity.locked || (modifiable != null && !modifiable)) {
                attributes = "disabled=\"disabled\""
            }

            if (var) {
                pageScope.(var.toString()) = (attributes)
            } else {
                pageScope.enabledAttribute = (attributes)
            }
        }
    }

    def isSectionExpandable = { attrs ->
        Boolean expandable
        Entity entity = attrs.entity
        QuerySection section = attrs.section
        String queryId = attrs.queryId
        Boolean overrideCollapse = attrs.overrideCollapse
        if(!overrideCollapse) {
            if (section && queryId) {
                if (section.expandable) {
                    if (!entity || (entity && !entity.datasets?.find({ queryId.equals(it.queryId) && section.sectionId?.equals(it.sectionId) && it.answerIds }))) {
                        expandable = Boolean.TRUE
                    }
                }
            }
        }
        out << expandable
    }

   /* def questionAnswer = { attrs ->
        def entity = attrs.entity
        def var = attrs.var
        def parentEntity = attrs.parentEntity
        Boolean sendMeData = attrs.sendMeData
        List<Question> linkedQuestions = attrs.linkedQuestions
        Resource entityClassResource = attrs.entityClassResource

        if (entity || parentEntity) {
            def queryId = attrs.queryId
            def sectionId = attrs.sectionId
            def questionId = attrs.questionId
            def resourceId = attrs.resourceId
            Dataset dataset

            if (sendMeData) {
                Question linkedQuestion = linkedQuestions?.find({questionId?.equals(it.mapToResourceParameter)})
                dataset = datasetService.getUniqueDataset(entity ?: parentEntity, linkedQuestion?.queryId, linkedQuestion?.sectionId, linkedQuestion?.questionId, resourceId)
            } else {
                dataset = datasetService.getUniqueDataset(entity ?: parentEntity, queryId, sectionId, questionId, resourceId)
            }

            if (dataset) {
                def inputType = attrs.inputType
                def answerIds = dataset?.answerIds
                def additionalQuestionAnswers = dataset?.additionalQuestionAnswers

                if (additionalQuestionAnswers) {
                    pageScope.additionalQuestionAnswers = additionalQuestionAnswers
                } else {
                    pageScope.additionalQuestionAnswers = null
                }

                if (var) {
                    pageScope.(var.toString()) = answerIds
                } else {
                    if ("text" == inputType || "textarea" == inputType) {
                        if (answerIds) {
                            out << optimiStringUtils.removeSingleAndDoubleQuotes(answerIds[0].toString())
                        }
                    } else if ("select" == inputType) {
                        out << answerIds
                    } else if ("checkboxRadio" == inputType) {
                        out << answerIds
                    } else if (answerIds) {
                        out << true
                    }
                }
            }

        }
        if(entityClassResource){
            def inputType = attrs.inputType
            def questionId = attrs.questionId
            def answerIds
            Map data=  entityClassResource.getProperties()
            if(questionId){
                answerIds = data?.get(questionId)
                if(answerIds){

                    if (var) {
                        pageScope.(var.toString()) = answerIds
                    } else {
                        if ("text" == inputType || "textarea" == inputType) {
                            if (answerIds) {
                                out << answerIds
                            }
                        } else if ("select" == inputType) {
                            out << answerIds
                        } else if ("checkboxRadio" == inputType) {
                            out << answerIds
                        } else if (answerIds) {
                            out << true
                        }
                    }
                    loggerUtil.info(log,"data ==> ${questionId}----q and a-----${data.get(questionId)}")
                }
            }

        }
    }*/
    def questionAnswer = { attrs ->
        def entity = attrs.entity
        def var = attrs.var
        def parentEntity = attrs.parentEntity
        Boolean sendMeData = attrs.sendMeData
        List<Question> linkedQuestions = attrs.linkedQuestions
        Resource entityClassResource = attrs.entityClassResource
        if (entityClassResource) {
            def inputType = attrs.inputType
            String questionId = attrs.questionId
            if(questionId){
                def answerIds = DomainObjectUtil.callGetterByAttributeName(questionId, entityClassResource)
                if(answerIds){
                        if ("text" == inputType || "textarea" == inputType) {
                            if (answerIds) {
                                if (answerIds instanceof List) {
                                    if (var) {
                                        pageScope.(var.toString()) = answerIds[0]
                                    }
                                    else{
                                    out << answerIds[0]
                                    }
                                } else {
                                    if (var) {
                                        pageScope.(var.toString()) = answerIds.toString()
                                    }else {
                                        out << answerIds.toString()
                                    }
                                }
                            }
                        } else if ("select" == inputType) {
                            if (answerIds) {
                                if (answerIds instanceof List) {
                                    if (var) {
                                        pageScope.(var.toString()) = answerIds
                                    }else{
                                        out << answerIds
                                    }

                                } else {
                                    if (var) {
                                        pageScope.(var.toString()) = [answerIds]
                                    }
                                    else{
                                        out << [answerIds]
                                    }
                                }
                            }
                        }
                    //loggerUtil.info(log,"data ==> ${questionId}----q and a-----${data.get(questionId)}")
                }else if (entity || parentEntity) {
                    def queryId = attrs.queryId
                    def sectionId = attrs.sectionId
                    def resourceId = attrs.resourceId
                    Dataset dataset = attrs.dataset ?: datasetService.getQuestionAnswerDataset(questionId, queryId, sectionId, linkedQuestions, sendMeData, entity, parentEntity, resourceId)

                    if (dataset) {
                        answerIds = dataset?.answerIds
                        def additionalQuestionAnswers = dataset?.additionalQuestionAnswers
                        if (additionalQuestionAnswers) {
                            pageScope.additionalQuestionAnswers = additionalQuestionAnswers
                        } else {
                            pageScope.additionalQuestionAnswers = null
                        }
                        if (var) {
                            pageScope.(var.toString()) = answerIds
                        } else {
                            if (com.bionova.optimi.core.Constants.InputType.TEXT.toString() == inputType || com.bionova.optimi.core.Constants.InputType.TEXTAREA.toString() == inputType) {
                                if (answerIds) {
                                    out << optimiStringUtils.removeSingleAndDoubleQuotes(answerIds[0].toString())
                                }
                            } else if (com.bionova.optimi.core.Constants.InputType.SELECT.toString() == inputType) {
                                out << answerIds
                            } else if (com.bionova.optimi.core.Constants.InputType.CHECKBOXRADIO.toString() == inputType) {
                                out << answerIds
                            } else if (answerIds) {
                                out << true
                            }
                        }
                    }
                }
            }
        } else if (entity || parentEntity) {
            def queryId = attrs.queryId
            def sectionId = attrs.sectionId
            def questionId = attrs.questionId
            def resourceId = attrs.resourceId
            Dataset dataset = attrs.dataset ?: datasetService.getQuestionAnswerDataset(questionId, queryId, sectionId, linkedQuestions, sendMeData, entity, parentEntity, resourceId)

            if (dataset) {
                def inputType = attrs.inputType
                def answerIds = dataset?.answerIds
                def additionalQuestionAnswers = dataset?.additionalQuestionAnswers
                if (additionalQuestionAnswers) {
                    pageScope.additionalQuestionAnswers = additionalQuestionAnswers
                } else {
                    pageScope.additionalQuestionAnswers = null
                }
                if (var) {
                    pageScope.(var.toString()) = answerIds
                } else {
                    if ("text" == inputType || "textarea" == inputType) {
                        if (answerIds) {
                            out << optimiStringUtils.removeSingleAndDoubleQuotes(answerIds[0].toString())
                        }
                    } else if ("select" == inputType) {
                        out << answerIds
                    } else if ("checkboxRadio" == inputType) {
                        out << answerIds
                    } else if (answerIds) {
                        out << true
                    }
                }
            }
        }
    }

    def getResourceDataset = { attrs ->
        //loggerUtil.debug(log, "Start of getResourceDataset")
        def entity = attrs.entity
        def queryId = attrs.queryId
        def sectionId = attrs.sectionId
        def questionId = attrs.questionId
        Dataset dataset = datasetService.getUniqueDataset(entity, queryId, sectionId, questionId, null)
        def var = attrs.var

        if (var) {
            pageScope.(var.toString()) = dataset
        } else {
            pageScope.resourceAnswer = dataset
        }
        //loggerUtil.debug(log, "End of getResourceDataset")
    }

    def getResourceDatasets = { attrs ->
        //loggerUtil.debug(log, "Start of getResourceDatasets")
        Entity entity = attrs.entity
        def queryId = attrs.queryId
        def sectionId = attrs.sectionId
        def questionId = attrs.questionId
        def datasets = datasetService.getDatasetsByEntityQueryIdSectionIdAndQuestion(entity, queryId, sectionId, questionId)
        datasets = datasets?.findAll({ Dataset d -> d.resourceId })
        def var = attrs.var

        if (var) {
            pageScope.(var.toString()) = datasets
        } else {
            pageScope.resourceDatasets = datasets
        }
        //loggerUtil.debug(log, "End of getResourceDatasets")
    }

    def resultQuestionAnswer = { attrs ->
        //loggerUtil.debug(log, "Start of resultQuestionAnswer")
        def entity = attrs.entity
        def queryId = attrs.queryId
        def sectionId = attrs.sectionId
        def questionId = attrs.questionId
        def queryQuestions = queryService.getQueryByQueryId(queryId, true)?.getAllQuestions()
        def resourceId = attrs.resourceId
        def dataset = datasetService.getUniqueDataset(entity, queryId, sectionId, questionId, resourceId)
        def answerIds = dataset?.answerIds
        def answers = ""

        answerIds?.each {
            if (it) {
                answers = answers ? answers + ", " + it : it
            }
        }

        if (answers) {
            Question question = queryQuestions.find({ Question q -> q.questionId == questionId })
            answers = question.localizedQuestion + ": " + answers
        }
        out << answers
        //loggerUtil.debug(log, "End of resultQuestionAnswer")
    }

    def sectionScore = { attrs ->
        def entity = attrs.entity
        def queryId = attrs.queryId
        def sectionId = attrs.sectionId
        def datasets = datasetService.getDatasetsByEntityAndQueryIdAndSectionId(entity, queryId, sectionId)
        def points

        datasets?.each { dataset ->
            if (dataset.points) {
                if (points) {
                    points += dataset.points
                } else {
                    points = dataset.points
                }
            }
        }
        def messageCode = attrs.pointsMessageCode
        def message = ""

        if (messageCode) {
            message = g.message(code: messageCode)
        }
        def symbols = new DecimalFormatSymbols(userService.getUserLocale(userService.getCurrentUser()?.localeString))
        char empty = ' '
        symbols.setGroupingSeparator(empty)
        def numberFormat = new DecimalFormat('###,###.##', symbols)
        def answer = points ? numberFormat.format(points) + " " + message : "0"
        out << answer
    }

    def additionalCalculationParams = {
        Question question = it.question
        Entity entity = it.entity
        Map additionalCalculationParams = questionService.getAdditionalCalculationParamsForQuestion(question, entity)

        out << groovyPageRenderer.render(template: "/query/templates/hiddenParamsForAdditionalCalculation",
                model: [additionalCalculationParams: additionalCalculationParams, mainQuestionId: question.questionId])
    }

}
