/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.taglib

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.QuestionSpecifiedSettingDisplayMode
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.service.QuestionService
import com.bionova.optimi.core.util.DomainObjectUtil
import org.apache.commons.lang3.math.NumberUtils
import org.bson.Document

class AdditionalQuestionTagLib {
    static namespace = "aq"
    def costStructureService
    def eolProcessService
    def optimiResourceService
    def additionalQuestionService
    def optimiStringUtils
    def stringUtilsService
    QuestionService questionService
    def unitConversionUtil
    def valueReferenceService
    def resourceService

    def renderAutomatedCostQuestion = { attrs ->
        String costQuestion = ""
        Question additionalQuestion = attrs.additionalQuestion
        Question mainQuestion = attrs.mainQuestion
        Entity entity = attrs.entity
        Indicator indicator = attrs.indicator
        Resource r = attrs.resource
        String resourceId = attrs.resourceId
        String additionalfieldName = attrs.additionalfieldName
        String userSavedUnitForCost = attrs.userSavedUnitForCost
        String unitToShow = attrs.unitToShow
        String mainSectionId = attrs.mainSectionId
        String datasetId = attrs.datasetId
        String additionalfieldId = attrs.additionalfieldId
        String fullTextExpand = attrs.fullTextExpand
        String random = attrs.random
        String uniqueConstructionIdentifier = attrs.uniqueConstructionIdentifier
        Boolean disableEditing = attrs.disableEditing
        Boolean userSetCost = attrs.userSetCost
        Boolean userSetTotalCost = attrs.userSetTotalCost
        Boolean automaticCostLicensed = attrs.automaticCostLicensed
        Boolean showStartupTips = attrs.showStartupTips
        Boolean constructionResource = attrs.constructionResource
        Boolean datasetLocked = attrs.datasetLocked
        String quantityAnswer = attrs.quantityAnswer
        String costCalculationMethod = attrs.costCalculationMethod

        def disabledAttribute = attrs.disabledAttribute
        def answer = attrs.answer ?: "0"
        def showMandatory = attrs.showMandatory

        if (additionalQuestion && ("costPerUnit".equals(additionalQuestion.questionId) || "totalCost".equals(additionalQuestion.questionId) || "totalAndSocialCost".equals(additionalQuestion.questionId))) {
            String formattedAnswer = answer?.toString()?.replace(',', '.')?.isNumber() ? questionService.getFormattedAnswer(answer, "costPerUnit".equals(additionalQuestion.questionId) ? Boolean.TRUE : Boolean.FALSE, ["totalCost","totalAndSocialCost"].contains(additionalQuestion.questionId) ? Boolean.TRUE : Boolean.FALSE) : "0"
            QuestionSpecifiedSettingDisplayMode specifiedSettingDisplayMode = additionalQuestion.specifiedSettingDisplayMode
            String costQuestionId = additionalQuestion.questionId
            String currency = valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE)
            costQuestion = "${costQuestion}<span class=\"hidden\">${answer ? answer : 0}</span>"

            if (constructionResource) {
                if ((("costPerUnit".equals(costQuestionId) && !userSetCost) || ("totalCost".equals(costQuestionId) && !userSetTotalCost) || "totalAndSocialCost".equals(costQuestionId)) && automaticCostLicensed && !"lumpSums".equals(costCalculationMethod)) {
                    if (!disabledAttribute) {
                        if (!attrs.answer && "materialsSpecificCost".equals(costCalculationMethod) && ("costPerUnit".equals(costQuestionId) && !userSetCost)) {
                            answer = costStructureService.getCost(r, entity, indicator, unitToShow, costCalculationMethod)?.toString()

                            if (!answer) {
                                answer = "0"
                            }
                            costQuestion = "${costQuestion}<input type=\"text\" data-materialsSpecificCost=\"${uniqueConstructionIdentifier}\" data-dataSetId=\"${datasetId}\" ${showStartupTips ? 'rel=\"startupTip_resourceRow\"' : ''} ${disableEditing ? 'readonly' : ''} class=\"input-small ${"costPerUnit".equals(costQuestionId) ? "userGivenCustomCostTotalMultiplier" : "totalCostInput"} ${showStartupTips ? 'startupTip' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion?.questionId}\" id=\"${additionalfieldId}\" value=\"${answer}\" />"
                            if (!"totalAndSocialCost".equals(costQuestionId)) {
                                costQuestion = "${costQuestion}<input type=\"hidden\" id=\"userSetCost${costQuestionId}\" value=\"${!automaticCostLicensed ? 'false' : 'true'}\" class=\"userSetCost${costQuestionId}\" name=\"${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}\">"
                            }
                        } else {
                            if ("totalAndSocialCost".equals(costQuestionId)) {
                                costQuestion = "${costQuestion}<a href=\"javascript:\" class=\"removeClicksLowOpacity serviceLifeButton ${automaticCostLicensed ? "${costQuestionId}Total" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${specifiedSettingDisplayMode?.localizedHoverOverText}\">${formattedAnswer}</a>"
                            } else {
                                costQuestion = "${costQuestion}<a href=\"javascript:\" class=\"serviceLifeButton ${automaticCostLicensed ? "${costQuestionId}Total" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\"  onclick=\"changeCostToTextfield(this, '${additionalfieldName}', '${costQuestionId}', 'false', '${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}', '${additionalfieldId}', '${!additionalQuestion?.optional && showMandatory ? 'true' : ''}', '', '${uniqueConstructionIdentifier}');\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${specifiedSettingDisplayMode?.localizedHoverOverText}\">${formattedAnswer}</a>"
                            }
                        }
                        costQuestion = "${costQuestion}<span class=\"add-on ${"costPerUnit".equals(costQuestionId) ? "costStructureUnit" : "totalCostCurrency"}\">"
                    } else {
                        costQuestion = "${costQuestion}${formattedAnswer}"
                    }
                    costQuestion = "${costQuestion}<input type=\"hidden\" id=\"${additionalfieldId}\" name=\"${additionalfieldName}\" value=\"${answer}\" class=\"${costQuestionId}Hidden\">"
                    if (!"totalAndSocialCost".equals(costQuestionId)) {
                        costQuestion = "${costQuestion}<input type=\"hidden\" id=\"userSetCost${costQuestionId}\" value=\"false\" class=\"userSetCost${costQuestionId}\" name=\"${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}\">"
                    }
                } else {
                    costQuestion = "${costQuestion}<input type=\"text\" data-dataSetId=\"${datasetId}\" ${showStartupTips ? 'rel=\"startupTip_resourceRow\"' : ''} ${disableEditing ? 'readonly' : ''} class=\"input-small ${"costPerUnit".equals(costQuestionId) ? "userGivenCustomCostTotalMultiplier" : "totalCostInput"} ${showStartupTips ? 'startupTip' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion?.questionId}\" id=\"${additionalfieldId}\" value=\"${answer}\" />"
                    if (!"totalAndSocialCost".equals(costQuestionId)) {
                        costQuestion = "${costQuestion}<input type=\"hidden\" id=\"userSetCost${costQuestionId}\" value=\"${!automaticCostLicensed ? 'false' : 'true'}\" class=\"userSetCost${costQuestionId}\" name=\"${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}\">"
                    }
                }

                if ("costPerUnit".equals(costQuestionId)) {
                    if (currency && !additionalQuestion?.groupedQuestionIds) {
                        costQuestion = "${costQuestion}&nbsp;${currency}&nbsp;/&nbsp;"
                    } else {
                        costQuestion = "${costQuestion}&nbsp;"
                    }
                } else {
                    if (currency && !additionalQuestion?.groupedQuestionIds) {
                        costQuestion = "${costQuestion}&nbsp;${currency}"
                    }
                }
                costQuestion = "${costQuestion}${"costPerUnit".equals(costQuestionId) ? userSavedUnitForCost ? userSavedUnitForCost : unitToShow ? unitToShow : "" : ""}</span>"
            } else {
                if ((("costPerUnit".equals(costQuestionId) && !userSetCost) || ("totalCost".equals(costQuestionId) && !userSetTotalCost) || "totalAndSocialCost".equals(costQuestionId)) && automaticCostLicensed && !"lumpSums".equals(costCalculationMethod)) {
                    if (!disabledAttribute) {
                        if (!attrs.answer) {
                            if ("costPerUnit".equals(costQuestionId)) {
                                answer = costStructureService.getCost(r, entity, indicator, unitToShow, costCalculationMethod)?.toString()
                                formattedAnswer = questionService.getFormattedAnswer(answer, "costPerUnit".equals(additionalQuestion.questionId) ? Boolean.TRUE : Boolean.FALSE, "totalCost".equals(additionalQuestion.questionId))
                            } else if ("totalCost".equals(costQuestionId)) {
                                answer = costStructureService.getTotalCost(r, entity, indicator, unitToShow, quantityAnswer, costCalculationMethod)?.toString()
                                formattedAnswer = questionService.getFormattedAnswer(answer, "costPerUnit".equals(additionalQuestion.questionId) ? Boolean.TRUE : Boolean.FALSE, "totalCost".equals(additionalQuestion.questionId))
                            } else {
                                answer = "-"
                                formattedAnswer = "-"
                            }
                        }

                        if (answer && answer != "0") {
                            if ("totalAndSocialCost".equals(costQuestionId)) {
                                costQuestion = "${costQuestion}<a href=\"javascript:\" class=\"removeClicksLowOpacity serviceLifeButton ${costQuestionId}Test${uniqueConstructionIdentifier ? " constituenCostLink${uniqueConstructionIdentifier}" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${specifiedSettingDisplayMode?.localizedHoverOverText}\">"
                            } else {
                                costQuestion = "${costQuestion}<a href=\"javascript:\" class=\"serviceLifeButton ${costQuestionId}Test${uniqueConstructionIdentifier ? " constituenCostLink${uniqueConstructionIdentifier}" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeCostToTextfield(this, '${additionalfieldName}', '${costQuestionId}', 'false', '${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}', '${additionalfieldId}'${!additionalQuestion?.optional && showMandatory ? ', \'true\'' : ''}${!specifiedSettingDisplayMode?.setEmptyWhenClicked ? ', \'' + answer?.toString() + '\'' : ''});\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${specifiedSettingDisplayMode?.localizedHoverOverText}\">"
                            }
                            if (answer == "-") {
                                costQuestion = "${costQuestion}-"
                            } else {
                                costQuestion = "${costQuestion}${formattedAnswer}"
                            }
                            costQuestion = "${costQuestion}</a>"
                        } else {
                            if ("costPerUnit".equals(costQuestionId)) {
                                costQuestion = "${costQuestion}<div class=\"lccalcTooltip fiveMarginRight\"><i style=\"color: orange; font-size: 14px;\" class=\"fa fa-exclamation-triangle smoothTip tooltip--right\" data-tooltip=\"${message(code: "query.cost_calcalculation_failed")}\"></i></div>"
                            }
                            costQuestion = "${costQuestion}<a href=\"javascript:\" class=\"serviceLifeButton ${costQuestionId}Test${uniqueConstructionIdentifier ? " constituenCostLink${uniqueConstructionIdentifier}" : ""}\" onclick=\"changeCostToTextfield(this, '${additionalfieldName}', '${costQuestionId}', 'false', '${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}', '${additionalfieldId}'${!additionalQuestion?.optional && showMandatory ? ', \'true\'' : ''}${!specifiedSettingDisplayMode?.setEmptyWhenClicked ? ', \'' + answer?.toString() + '\'' : ''});\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${specifiedSettingDisplayMode?.localizedHoverOverText}\">"
                            costQuestion = "${costQuestion}${formattedAnswer}"
                            costQuestion = "${costQuestion}</a>"
                        }
                    } else {
                        costQuestion = "${costQuestion}${formattedAnswer}"
                    }
                    costQuestion = "${costQuestion}<input type=\"hidden\" id=\"${additionalfieldId}\" name=\"${additionalfieldName}\" ${uniqueConstructionIdentifier ? "data-constituent=\"${costQuestionId}.${uniqueConstructionIdentifier}\"" : ""} value=\"${answer}\" class=\"${costQuestionId}Hidden\">"

                    if (!"totalAndSocialCost".equals(costQuestionId)) {
                        costQuestion = "${costQuestion}<input type=\"hidden\" id=\"userSetCost${costQuestionId}\" value=\"false\" class=\"userSetCost${costQuestionId}\" name=\"${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}\">"
                    }
                } else {
                    costQuestion = "${costQuestion}<input type=\"text\" data-dataSetId=\"${datasetId}\" ${showStartupTips ? 'rel=\"startupTip_resourceRow\"' : ''} ${disableEditing ? 'readonly' : ''} ${uniqueConstructionIdentifier ? "data-constituent=\"${costQuestionId}.${uniqueConstructionIdentifier}\"" : ""} class=\"input-small${"costPerUnit".equals(costQuestionId) ? " userGivenCustomCostTotalMultiplier" : " totalCostInput"}${uniqueConstructionIdentifier ? " constituenCostInput${uniqueConstructionIdentifier}" : ""}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion?.questionId}\" id=\"${additionalfieldId}\" value=\"${answer}\" />"

                    if (!"totalAndSocialCost".equals(costQuestionId)) {
                        costQuestion = "${costQuestion}<input type=\"hidden\" id=\"userSetCost${costQuestionId}\" value=\"${!automaticCostLicensed ? 'false' : 'true'}\" class=\"userSetCost${costQuestionId}\" name=\"${mainSectionId}.${mainQuestion?.questionId}_userSetCost_${additionalQuestion?.questionId}${resourceId ? '.' + resourceId : ''}\">"
                    }
                }
                costQuestion = "${costQuestion}<span class=\"add-on ${"costPerUnit".equals(costQuestionId) ? "costStructureUnit" : "totalCostCurrency"}\">"

                if ("costPerUnit".equals(costQuestionId)) {
                    if (currency && !additionalQuestion?.groupedQuestionIds) {
                        costQuestion = "${costQuestion}&nbsp;${currency}&nbsp;/&nbsp;"
                    } else {
                        costQuestion = "${costQuestion}&nbsp;"
                    }
                } else {
                    if (currency && !additionalQuestion?.groupedQuestionIds) {
                        costQuestion = "${costQuestion}&nbsp;${currency}"
                    }
                }
                costQuestion = "${costQuestion}${"costPerUnit".equals(costQuestionId) ? userSavedUnitForCost ? userSavedUnitForCost : unitToShow ? unitToShow : "" : ""}</span>"

                if (additionalQuestion.showFullText) {
                    costQuestion = "${costQuestion}<a href=\"javascript:\" id=\"${additionalQuestion.questionId}expand\" ${fullTextExpand}>&nbsp;<i class=\"fa fa-arrow-up fa-rotate-45 fa-size-medium\"></i></a>"
                }
            }
        }
        out << costQuestion
    }

    def renderSelectQuestion = { attrs ->
        String selectQuestion = ""
        String localizedNameGetter = "name${DomainObjectUtil.mapKeyLanguage}"
        Question mainQuestion = attrs.question
        Question additionalQuestion = attrs.additionalQuestion
        Question mainAdditionalQuestion = attrs.mainAdditionalQuestion
        Indicator indicator = attrs.indicator
        Entity parentEntity = attrs.parentEntity
        Entity entity = attrs.entity
        String childEntityId = entity?.id?.toString()
        String additionalfieldName = attrs.additionalfieldName
        String datasetId = attrs.datasetId
        String additionalfieldId = attrs.additionalfieldId
        Resource resource = attrs.resource
        String resourceId = resource?.resourceId ?: attrs.resourceId
        String random = attrs.random
        Boolean constructionResource = attrs.constructionResource
        String defaultLocalCompCountry = attrs.defaultLocalCompCountry
        Resource projectCountryResource = attrs.projectCountryResource
        String userGivenAnswer = Constants.UNDEFINED.equals(attrs.userGivenAnswer) ? null : attrs.userGivenAnswer
        def answer = attrs.answer
        Boolean preventChanges = attrs.preventChanges
        def showMandatory = attrs.showMandatory
        String userUnit = attrs.userUnit
        String queryId = attrs.queryId
        String sectionId = attrs.sectionId
        String questionId = attrs.questionId
        String inheritToChildrent = attrs.inheritToChildrent
        Boolean newResourceRowAdded = attrs.newResourceRowAdded
        Boolean importMapperAdditionalQuestion = attrs.importMapperAdditionalQuestion
        Boolean datasetLocked = attrs.datasetLocked
        Boolean showAsDisabled = attrs.showAsDisabled
        Resource defaultResource = attrs.defaultResource
        String localCompensationMethodVersion = attrs.localCompensationMethodVersion
        boolean forGroupEditRender = attrs.forGroupEditRender
        EolProcessCache eolProcessCache = attrs.eolProcessCache ?: EolProcessCache.init() //this is checkpoint to understand if cache wasn't come
        // TODO: Split the ifs into nice service method calls for readability
        if (resource) {
            if (constructionResource && additionalQuestion.disableEditingForConstruction) {
                selectQuestion = "${selectQuestion}${message(code: "data_by_constituent")}"
            } else if (additionalQuestion) {
                if (additionalQuestion.showTypeahead && newResourceRowAdded) {
                    // This additionalQuestion needs to be a resource question.
                    selectQuestion = "${selectQuestion}<div class=\"input-append\">"
                    selectQuestion = "${selectQuestion}<input type=\"text\" class=\"additionalQuestionAutocomplete${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" class=\"input-xlarge\" autocomplete=\"off\" data-original-title=\"\""
                    selectQuestion = "${selectQuestion} data-indicatorId=\"${indicator?.indicatorId}\" maxlength=\"10000\""
                    selectQuestion = "${selectQuestion} data-queryId=\"${queryId}\" data-sectionId=\"${sectionId}\" value=\"${answer ? optimiResourceService.getLocalizedName(optimiResourceService.getResourceByResourceAndProfileId(answer, null, true)) ?: "" : ""}\""
                    selectQuestion = "${selectQuestion} data-questionId=\"${questionId}\" data-additionalQuestionId=\"${additionalQuestion.questionId}\" ${preventChanges||showAsDisabled||datasetLocked ? "disabled=\"disabled\"" : ""}><a tabindex=\"-1\" class=\"add-on showAllResources${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" ${preventChanges||showAsDisabled||datasetLocked ? "" : "onclick=\"showAllAdditionalQuestionResources(this);\""} ><i class=\"icon-chevron-down\"></i></a>"
                    selectQuestion = "${selectQuestion}<input class=\"additionalQuestionAutocompleteHiddenInput\" type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ""}\" /></div><span id=\"${random}loadingResource\" style=\"display: none;\"><i class=\"fas fa-circle-notch fa-spin\" aria-hidden=\"true\"></i></span>"
                } else {
                    if (additionalQuestion.questionId == 'profileId') {

                        List<Resource> resourceProfiles = mainQuestion?.getResourceProfiles(resourceId, null, indicator, parentEntity)

                        if (resourceProfiles?.size() == 1 && mainQuestion?.queryId != "LCAParametersQuery") {

                            String profileLabel = optimiResourceService.getLocalizedProfileName(resourceProfiles[0])

                            if (profileLabel?.length() > 10) {
                                profileLabel = "<a href=\"javascript:\" class=\"just_black\" rel=\"popover\" data-trigger=\"hover\" data-content=\"${profileLabel}\">${g.abbr(value: profileLabel, maxLength: 10)}</a>"
                            }
                            selectQuestion = "<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${resourceProfiles[0].profileId}\" />${profileLabel}"
                        } else {

                            selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} onchange=\"updateSourceListing('${indicator?.indicatorId}','${resourceId}','${mainQuestion?.questionId}', \$(this).val(), '${childEntityId}','true','${random}info');\" onselect=\"removeBorder(this);updateHiddenField(this, '${mainQuestion.questionId}');\" id=\"${additionalfieldId}\">"
                            selectQuestion = "${selectQuestion}${g.renderProfileOptions(resourceProfiles: resourceProfiles, indicator: indicator, profileAnswer: answer)}"
                            selectQuestion = "${selectQuestion}</select>"

                            if (preventChanges) {
                                selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : resourceProfiles[0].profileId}\" />"
                            }
                        }
                    } else if (additionalQuestion.questionId == Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                        Boolean localCompsDisabled = !localCompensationMethodVersion||"00".equals(localCompensationMethodVersion)
                        Boolean lcaParametersAdditionalQuestion = mainQuestion?.queryId == "LCAParametersQuery"
                        String mainQuestionAnswer = lcaParametersAdditionalQuestion ? resource.resourceId : attrs.mainAdditionalQuestionAnswer ?: ((defaultLocalCompCountry && !localCompsDisabled) || (resourceService.getIsLocalResource(resource?.areas) && localCompsDisabled)) ? defaultLocalCompCountry : resourceService.getIsLocalResource(resource?.areas) ? projectCountryResource?.resourceId : null
                        Boolean localCompsAvailable = optimiResourceService.isLocalCompApplicable(resource)

                        if (attrs.additionalQuestionResources && (lcaParametersAdditionalQuestion || (localCompsAvailable && (resourceService.getIsLocalResource(resource.areas)||!localCompsDisabled||userGivenAnswer)))) {
                            String inLineStyle = lcaParametersAdditionalQuestion ? "style=\"width: 80px;\"" : g.questionInlineStyle(question: additionalQuestion, answer: answer, showMandatory: showMandatory)
                            selectQuestion = additionalQuestionService.getCompensationEnergyProfileAdditionalQuestion(resource, parentEntity, additionalQuestion, attrs.additionalQuestionResources, additionalfieldName, additionalfieldId, newResourceRowAdded, lcaParametersAdditionalQuestion, answer, userGivenAnswer, mainQuestionAnswer, datasetLocked, inLineStyle, queryId, sectionId, questionId, showAsDisabled, preventChanges, localCompensationMethodVersion, indicator?.indicatorId, defaultLocalCompCountry, forGroupEditRender)
                        } else if (localCompsDisabled) {
                            selectQuestion = "${selectQuestion}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"\" />"
                        }
                    } else if (additionalQuestion.questionId == 'state') {
                        selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"autocompletebox ${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} onselect=\"removeBorder(this);updateHiddenField(this, '${mainQuestion.questionId}');\" onchange=\"showPlanetaryCompatibility(this);\"id=\"${additionalfieldId}\">"
                        selectQuestion = "${selectQuestion}${g.renderStateOptions(statesList: attrs.additionalQuestionResources, stateAnswer: answer, resourceGroups: additionalQuestion.resourceGroups, defaultAnswer: defaultResource?.showState)}"
                        selectQuestion = "${selectQuestion}</select>"

                        if (preventChanges) {
                            selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ''}\" />"
                        }
                    } else if (additionalQuestion.questionId == Constants.SFB_CODE_QUESTIONID) {
                        selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"autocompletebox ${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} id=\"${additionalfieldId}\">"
                        selectQuestion = "${selectQuestion}${g.renderSfbCodeQuestion(elementList: attrs.additionalQuestionResources, answer: answer,resource:resource, newResourceRowAdded: newResourceRowAdded)}"
                        selectQuestion = "${selectQuestion}</select>"

                        if (preventChanges) {
                            selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ''}\" />"
                        }
                    } else {
                        if (newResourceRowAdded) {

                            // New resource rows show selects from the start!
                            if (attrs.additionalQuestionResources) {
                                Boolean disabledNoMass = optimiResourceService.isResourceWithDisabledNoMassTransport(resource, additionalQuestion, mainAdditionalQuestion)
                                if (additionalQuestion.questionId == Constants.LOCAL_COMP_QUESTIONID) {
                                    Boolean localCompsDisabled = !localCompensationMethodVersion||"00".equals(localCompensationMethodVersion)
                                    Boolean localResource = resourceService.getIsLocalResource(resource.areas)
                                    ResourceType subType = resourceService.getSubType(resource)
                                    Boolean canApplyLocalComps = optimiResourceService.isLocalCompApplicable(resource, subType)

                                    if (canApplyLocalComps && (localResource||!localCompsDisabled)) {
                                        if (defaultLocalCompCountry && !localCompsDisabled && resource.areas && resource.areas.contains(defaultLocalCompCountry)) {
                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton lockableDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${defaultLocalCompCountry}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" >${message(code:'applyLocalComps.local')}"
                                            selectQuestion = "${selectQuestion}</a><input  type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${defaultLocalCompCountry}\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\\\"${resourceId}\"/>"
                                        } else {
                                            selectQuestion = "${selectQuestion}<span class=\"hidden\">${defaultLocalCompCountry && !localCompsDisabled ?: ''}</span>"

                                            if (showAsDisabled) {
                                                def temp = localCompsDisabled ? localResource ? projectCountryResource?.resourceId : "" : defaultLocalCompCountry
                                                Document d = attrs.additionalQuestionResources.find({it.resourceId?.equals(temp)})
                                                if (d) {
                                                    int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                                    if (d.localizedName?.toString()?.length() > nameMaxLength) {
                                                        selectQuestion = "${selectQuestion}${d.localizedName ? "${g.abbr(value: d.localizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" />"
                                                    } else {
                                                        selectQuestion = "${selectQuestion}${d.localizedName}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" />"
                                                    }
                                                }
                                            } else {

                                                selectQuestion = "${selectQuestion}<select onchange=\"rerenderEnergyProfiles(this, '${additionalQuestion.groupedQuestionIds ? additionalQuestion.groupedQuestionIds.first() : ''}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', null, '${indicator?.indicatorId}')\" class=\"${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, answer: additionalQuestion.questionId == Constants.LOCAL_COMP_QUESTIONID ? defaultLocalCompCountry : answer, showMandatory: showMandatory)} name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"
                                                selectQuestion = "${selectQuestion}<option value=\"noLocalCompensation\">${message(code: 'notApplied')}</option>"
                                                attrs.additionalQuestionResources.each { Document d ->
                                                    if (d.active) {
                                                        if (localResource) {
                                                            // if the resource is local and we have a defaultLocalCompCountry, we use that, if not we get the project resource
                                                            selectQuestion = "${selectQuestion}<option value=\"${d.resourceId}\" ${defaultLocalCompCountry ? defaultLocalCompCountry.equalsIgnoreCase(d.resourceId) ? "selected='selected'" : "" : projectCountryResource?.resourceId?.equalsIgnoreCase(d.resourceId) ? "selected='selected'" : ""}>${d.localizedName}</option>"
                                                        } else {
                                                            // For all non-local resources, if we have the defaultLocalCompCountry and local compenstation isn't disabled, we use the default, otherwise all other cases are not-applied.
                                                            selectQuestion = "${selectQuestion}<option value=\"${d.resourceId}\" ${defaultLocalCompCountry && !localCompsDisabled ? defaultLocalCompCountry.equalsIgnoreCase(d.resourceId) ? "selected='selected'" : "" : ""}>${d.localizedName}</option>"
                                                        }
                                                    }
                                                }
                                                selectQuestion = "${selectQuestion}</select>"
                                            }
                                        }
                                    } else {
                                        if (canApplyLocalComps) {
                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton lockableDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', 'noLocalCompensation', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" >${message(code:'applyLocalComps.not_applicable')}"
                                            selectQuestion = "${selectQuestion}</a><input  type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"noLocalCompensation\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\\\"${resourceId}\"/>"
                                        } else {
                                            selectQuestion = "${selectQuestion}<span class=\"hidden\"></span>"

                                            String localCompsFail = resource.privateDataset ? "${message(code:'applyLocalComps.not_applicable.private')}" : (subType && (((!subType.requiredElectricityKwh && "10".equals(localCompensationMethodVersion)) || (!subType.requiredElectricityKwhPerKg && "11".equalsIgnoreCase(localCompensationMethodVersion))) || localCompsDisabled)) ? "${message(code:'applyLocalComps.not_applicable.requiredElectricityKwh')}" : "${message(code:'applyLocalComps.not_applicable.info')}"

                                            selectQuestion = "${selectQuestion}${message(code:'applyLocalComps.not_applicable')}<span class=\"fiveMarginLeft hidden-print\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-html=\"true\" data-trigger=\"hover\" data-content=\"<p style='font-weight:normal;'>${localCompsFail}</p>\"><i class=\"far fa-question-circle\"></i></span>"
                                            selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"noLocalCompensation\" />"
                                        }
                                    }
                                } else {

                                    selectQuestion = "${selectQuestion}<span class=\"hidden\">${answer ?: ''}</span>"

                                    if (showAsDisabled) {

                                        Document d = attrs.additionalQuestionResources.find({it.resourceId?.equals(answer?.toString())})
                                        if (d) {
                                            int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                            if (d.localizedName?.toString()?.length() > nameMaxLength) {
                                                selectQuestion = "${selectQuestion}${d.localizedName ? "${g.abbr(value: d.localizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" />"
                                            } else {
                                                selectQuestion = "${selectQuestion}${d.localizedName}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" />"
                                            }
                                        }
                                    } else if (disabledNoMass) {
                                        Document d = attrs.additionalQuestionResources.find({ it.resourceId?.equals(answer?.toString()) })
                                        if (d) {
                                            int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                            if (d.localizedName?.toString()?.length() > nameMaxLength) {
                                                selectQuestion = "${selectQuestion}<div style=\"display: inline-block;\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code:"resourcerow.disabledNoMass")}\"><a href=\"javascript:\" class=\"lockedDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\">${d.localizedName ? "${g.abbr(value: d.localizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}</a>&nbsp;<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" /></div>"
                                            } else {
                                                selectQuestion = "${selectQuestion}<div style=\"display: inline-block;\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code:"resourcerow.disabledNoMass")}\"><a href=\"javascript:\" class=\"lockedDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\">${d.localizedName}</a>&nbsp;<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${d.resourceId}\" /></div>"
                                            }
                                        }
                                    } else {
                                        selectQuestion = "${selectQuestion}<select class=\"${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, answer: additionalQuestion.questionId == Constants.LOCAL_COMP_QUESTIONID ? defaultLocalCompCountry : answer, showMandatory: showMandatory)} name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"

                                        if (additionalQuestion.noChoiceDisplayText) {
                                            selectQuestion = "${selectQuestion}<option value=\"\">${additionalQuestion.localizedNoChoiceDisplayText}</option>"
                                        }
                                        attrs.additionalQuestionResources.each { Document d ->
                                            if (d.active) {
                                                selectQuestion = "${selectQuestion}<option value=\"${d.resourceId}\" ${answer?.toString()?.equalsIgnoreCase(d.resourceId) ? "selected='selected'" : ""}>${d.localizedName}</option>"
                                            }
                                        }
                                        selectQuestion = "${selectQuestion}</select>"
                                    }

                                    if (additionalQuestion.localizedUnit && !additionalQuestion.groupedQuestionIds) {
                                        selectQuestion = "${selectQuestion}&nbsp;${additionalQuestion.localizedUnit}"
                                    } else if (additionalQuestion.unitAsValueReference && !additionalQuestion.groupedQuestionIds) {
                                        selectQuestion = "${selectQuestion}&nbsp;${valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: ''}"
                                    }
                                }
                            } else {
                                List<QuestionAnswerChoice> filteredChoises = questionService.getFilteredChoices(parentEntity, mainQuestion, resource, userGivenAnswer?.toString(), null, eolProcessCache, additionalQuestion)

                                if (filteredChoises) {
                                    String addQValue = DomainObjectUtil.callGetterByAttributeName(additionalQuestion.questionId,resource?.resourceTypeObject)

                                    selectQuestion = "${selectQuestion}<span class=\"hidden\">${addQValue?:answer ?: ''}</span>${additionalQuestion.questionId == "specialRuleId" ? "&nbsp;<span class=\"specialRuleSpan\">" : ""}"
                                    if (additionalQuestion.questionId == "specialRuleId") {
                                        if (userUnit && filteredChoises) {
                                            filteredChoises = filteredChoises.findAll({ !it.allowedUnits||it.allowedUnits*.toLowerCase().contains(userUnit.toLowerCase()) })
                                        } else if (filteredChoises) {
                                            filteredChoises = filteredChoises.findAll({ !it.allowedUnits })
                                        } else {
                                            filteredChoises = null
                                        }

                                        if (filteredChoises) {
                                            if (filteredChoises.size() == 1) {
                                                selectQuestion = "${selectQuestion}${filteredChoises.first().localizedAnswer ?: ''}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${filteredChoises.first().answerId ?: ''}\" />"
                                            } else {
                                                selectQuestion = "${selectQuestion}<select class=\"${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" style=\"max-width: 125px;\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"
                                                filteredChoises.each { QuestionAnswerChoice choise ->
                                                    selectQuestion = "${selectQuestion}<option value=\"${choise.answerId}\" ${answer?.toString()?.equalsIgnoreCase(choise.answerId) ? "selected='selected'" : ""}>${choise.localizedAnswer}</option>"
                                                }
                                                selectQuestion = "${selectQuestion}</select>"
                                            }
                                        }
                                    } else {
                                        if (additionalQuestion.eolChoicesFromSubType && !answer) {
                                            answer = eolProcessService.getEolProcessForDataset(null, resource, resourceService.getSubType(resource), projectCountryResource, null, attrs.lcaModel?.toString(), eolProcessCache)?.eolProcessId
                                        }

                                        if (showAsDisabled) {
                                            QuestionAnswerChoice questionAnswerChoice = filteredChoises.find({answer?.toString()?.equalsIgnoreCase(it.answerId)})
                                            if (questionAnswerChoice) {
                                                int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                                String localizedQuestionAnswerChoise = questionAnswerChoice.localizedAnswer
                                                if (localizedQuestionAnswerChoise?.length() > nameMaxLength) {
                                                    selectQuestion = "${selectQuestion}${localizedQuestionAnswerChoise ? "${g.abbr(value: localizedQuestionAnswerChoise, maxLength: nameMaxLength, smartCut: true)}" : ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${questionAnswerChoice.answerId}\" />"
                                                } else {
                                                    selectQuestion = "${selectQuestion}${localizedQuestionAnswerChoise}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${questionAnswerChoice.answerId}\" />"
                                                }
                                            }
                                        } else {
                                            selectQuestion = "${selectQuestion}<select class=\"${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, answer: answer, showMandatory: showMandatory)} name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"
                                            if (additionalQuestion.noChoiceDisplayText) {
                                                selectQuestion = "${selectQuestion}<option value=\"\">${additionalQuestion.localizedNoChoiceDisplayText}</option>"
                                            }
                                            //annie - 13275
                                            if(addQValue){
                                                answer = addQValue
                                            }
                                            filteredChoises.each { QuestionAnswerChoice choise ->
                                                selectQuestion = "${selectQuestion}<option value=\"${choise.answerId}\" ${answer?.toString()?.equalsIgnoreCase(choise.answerId) ? "selected='selected'" : ""}>${choise.localizedAnswer}</option>"
                                            }
                                            selectQuestion = "${selectQuestion}</select>"
                                        }
                                    }

                                    if (additionalQuestion.localizedUnit && !additionalQuestion.groupedQuestionIds) {
                                        selectQuestion = "${selectQuestion}&nbsp;${additionalQuestion.localizedUnit}"
                                    } else if (additionalQuestion.unitAsValueReference && !additionalQuestion.groupedQuestionIds) {
                                        selectQuestion = "${selectQuestion}&nbsp;${valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: ''}"
                                    }

                                    if (additionalQuestion.questionId == "specialRuleId") {
                                        selectQuestion = "${selectQuestion}</span>"
                                    }
                                }
                            }
                        } else {
                            // Rendering resource rows already saved for the entity
                            Map<String, Object> resolvedSelectedResourceParams = additionalQuestionService.getResolveSelectedResourceParams(
                                    additionalQuestion, resource, projectCountryResource, mainQuestion, attrs.additionalQuestionResources,
                                    answer?.toString(), userGivenAnswer?.toString(), defaultLocalCompCountry, localizedNameGetter, userUnit,
                                    attrs.lcaModel?.toString(), parentEntity, localCompensationMethodVersion, eolProcessCache)

                            String selecedResourceId = resolvedSelectedResourceParams.get("selecedResourceId")
                            String selectedResourceLocalizedName = resolvedSelectedResourceParams.get("selectedResourceLocalizedName")
                            Boolean inactive = resolvedSelectedResourceParams.get("inactive")
                            Boolean failingLocalComps = resolvedSelectedResourceParams.get("failingLocalComps")
                            Boolean usingGenericLocal = resolvedSelectedResourceParams.get("usingGenericLocal")
                            Boolean showAsText = resolvedSelectedResourceParams.get("showAsText")
                            Boolean disabledNoMass = optimiResourceService.isResourceWithDisabledNoMassTransport(resource, additionalQuestion, mainAdditionalQuestion)
                            Boolean localCompsDisabled = !localCompensationMethodVersion||"00".equals(localCompensationMethodVersion)

                            if (failingLocalComps) {
                                selectQuestion = "${selectQuestion}<span class=\"hidden\"></span>"
                                def subType = resourceService.getSubType(resource)
                                String localCompsFail = resource?.privateDataset ? "${message(code:'applyLocalComps.not_applicable.private')}" : (subType && (((!subType.requiredElectricityKwh && "10".equals(localCompensationMethodVersion)) || (!subType.requiredElectricityKwhPerKg && "11".equalsIgnoreCase(localCompensationMethodVersion))) || localCompsDisabled)) ? "${message(code:'applyLocalComps.not_applicable.requiredElectricityKwh')}" : "${message(code:'applyLocalComps.not_applicable.info')}"
                                selectQuestion = "${selectQuestion}${message(code:'applyLocalComps.not_applicable')}<span class=\"fiveMarginLeft hidden-print\" href=\"#\" data-toggle=\"dropdown\" rel=\"popover\" data-html=\"true\" data-trigger=\"hover\" data-content=\"<p style='font-weight:normal;'>${localCompsFail}</p>\"><i class=\"far fa-question-circle\"></i></span>"
                                selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"noLocalCompensation\" />"
                            } else if (additionalQuestion.questionId == Constants.LOCAL_COMP_QUESTIONID && defaultLocalCompCountry && resource.areas && resource.areas.contains(defaultLocalCompCountry) && (!userGivenAnswer||defaultLocalCompCountry.equals(userGivenAnswer))) {
                                selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton lockableDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${defaultLocalCompCountry}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" >${message(code:'applyLocalComps.local')}"
                                selectQuestion = "${selectQuestion}</a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${defaultLocalCompCountry}\"  data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\"/>"
                            } else if (selecedResourceId != null && selectedResourceLocalizedName) {
                                selectQuestion = "${selectQuestion}<span class=\"hidden\">${selecedResourceId.encodeAsHTML()}</span>${additionalQuestion.questionId == "specialRuleId" ? "&nbsp;<span class=\"specialRuleSpan\">" : ""}"

                                if (inactive) {
                                    selectedResourceLocalizedName = "${selectedResourceLocalizedName} (${g.message(code: 'inactive')})"
                                }

                                if (preventChanges) {
                                    int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)

                                    if (selectedResourceLocalizedName.length() > nameMaxLength) {
                                        selectQuestion = "${selectQuestion}${selectedResourceLocalizedName ? "${g.abbr(value: selectedResourceLocalizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${userGivenAnswer ?: ''}\" />"
                                    } else {
                                        selectQuestion = "${selectQuestion}${selectedResourceLocalizedName ?: ''}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${userGivenAnswer ?: ''}\" />"
                                    }
                                } else if (showAsText||showAsDisabled||disabledNoMass) {
                                    if (showAsDisabled || disabledNoMass) {
                                        int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                        if (selectedResourceLocalizedName.length() > nameMaxLength) {
                                            if (disabledNoMass) {
                                                selectQuestion = "${selectQuestion}<div style=\"display: inline-block;\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code:"resourcerow.disabledNoMass")}\"><a href=\"javascript:\" class=\"lockedDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\">${selectedResourceLocalizedName ? "${g.abbr(value: selectedResourceLocalizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}</a>&nbsp;<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" /></div>"
                                            } else {
                                                selectQuestion = "${selectQuestion}${selectedResourceLocalizedName ? "${g.abbr(value: selectedResourceLocalizedName, maxLength: nameMaxLength, smartCut: true)}" : ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" />"
                                            }
                                        } else {
                                            if (disabledNoMass) {
                                                selectQuestion = "${selectQuestion}<div style=\"display: inline-block;\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code:"resourcerow.disabledNoMass")}\"><a href=\"javascript:\" class=\"lockedDatasetQuestion\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\">${selectedResourceLocalizedName ?: ''}</a>&nbsp;<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" /></div>"
                                            } else {
                                                selectQuestion = "${selectQuestion}${selectedResourceLocalizedName ?: ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" />"
                                            }
                                        }
                                    } else {
                                        selectQuestion = "${selectQuestion}${selectedResourceLocalizedName ?: ''}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" />"
                                    }
                                } else {
                                    int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)

                                    if (usingGenericLocal) {
                                        selectQuestion = "${selectQuestion}<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${message(code: 'query.generic_material_profile')}\"><img src=\"/app/assets/img/generic_resource.png\" class=\"genericLocalComp\"></img></a>"
                                    }

                                    if (selectedResourceLocalizedName.length() > nameMaxLength) {

                                        if (additionalQuestion.specifiedSettingDisplayMode?.localizedHoverOverText) {

                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selecedResourceId}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${selectedResourceLocalizedName}<br />${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${g.abbr(value: selectedResourceLocalizedName, maxLength: nameMaxLength, smartCut: true)}"
                                        } else {
                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selecedResourceId}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" rel=\"popover\" data-trigger=\"hover\"  data-html=\"true\" data-content=\"${selectedResourceLocalizedName}\">${g.abbr(value: selectedResourceLocalizedName, maxLength: nameMaxLength, smartCut: true)}"
                                        }
                                    } else {
                                        if (additionalQuestion.specifiedSettingDisplayMode?.localizedHoverOverText) {

                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selecedResourceId}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" rel=\"popover\" data-trigger=\"hover\"  data-html=\"true\" data-content=\"${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${selectedResourceLocalizedName}"
                                        } else {

                                            selectQuestion = "${selectQuestion}<a href=\"javascript:\" class=\"serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selecedResourceId}', '${parentEntity?.id?.toString()}', '${inheritToChildrent ?: ""}', '${importMapperAdditionalQuestion ? datasetId : ""}', ${additionalQuestion.groupedQuestionIds ? true : false}, null, ${additionalQuestion.showTypeahead ? true : false}, '${indicator?.indicatorId}');\" >${selectedResourceLocalizedName}"
                                        }
                                    }
                                    selectQuestion = "${selectQuestion}</a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selecedResourceId}\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\" />"
                                }

                                if (additionalQuestion.localizedUnit && !additionalQuestion.groupedQuestionIds) {
                                    selectQuestion = "${selectQuestion}&nbsp;${additionalQuestion.localizedUnit}"
                                } else if (additionalQuestion.unitAsValueReference && !additionalQuestion.groupedQuestionIds) {
                                    selectQuestion = "${selectQuestion}&nbsp;${valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: ''}"
                                }

                                if (additionalQuestion.questionId == "specialRuleId") {
                                    selectQuestion = "${selectQuestion}</span>"
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (additionalQuestion.questionId == Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID) {
                // Allow rendering compensationEnergyProfile additionalQuestion to parameters query without a resource row
                Boolean lcaParametersAdditionalQuestion = mainQuestion?.queryId == "LCAParametersQuery"
                String inLineStyle = lcaParametersAdditionalQuestion ? "style=\"width: 80px;\"" : g.questionInlineStyle(question: additionalQuestion, answer: answer, showMandatory: showMandatory)
                String mainQuestionAnswer = parentEntity?.datasets?.find({it.queryId == "LCAParametersQuery" && it.sectionId == "calculationDefaults" && it.questionId == "targetCountry"})?.answerIds?.get(0)

                if (!mainQuestionAnswer) {
                    mainQuestionAnswer = mainQuestion?.getCountryResourceDefault(parentEntity)
                }
                selectQuestion = additionalQuestionService.getCompensationEnergyProfileAdditionalQuestion(resource, parentEntity, additionalQuestion, attrs.additionalQuestionResources, additionalfieldName, additionalfieldId, newResourceRowAdded, lcaParametersAdditionalQuestion, answer, userGivenAnswer, mainQuestionAnswer, datasetLocked, inLineStyle, queryId, sectionId, questionId, showAsDisabled, preventChanges, localCompensationMethodVersion, indicator?.indicatorId, defaultLocalCompCountry, forGroupEditRender)
            } else if(additionalQuestion.questionId == 'state'){
                selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"autocompletebox ${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} onselect=\"removeBorder(this);updateHiddenField(this, '${mainQuestion.questionId}');\" onchange=\"showPlanetaryCompatibility(this);\"id=\"${additionalfieldId}\">"
                selectQuestion = "${selectQuestion}${g.renderStateOptions(statesList: attrs.additionalQuestionResources,stateAnswer: answer, resourceGroups: additionalQuestion.resourceGroups, defaultAnswer: defaultResource?.showState)}"
                selectQuestion = "${selectQuestion}</select>"

                if (preventChanges) {
                    selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ''}\"  />"
                }
            } else if (additionalQuestion.questionId == 'profileId') {
                selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: false, answer: answer)} onchange=\"updateSourceListing('${indicator?.indicatorId}','${resourceId}','${mainQuestion?.questionId}', \$(this).val(), '${childEntityId}','true','${random}info');\" onselect=\"removeBorder(this);updateHiddenField(this, '${mainQuestion.questionId}');\" id=\"${additionalfieldId}\">"
                String mainQuestionAnswer = parentEntity?.datasets?.find({it.queryId == "LCAParametersQuery" && it.questionId == mainQuestion?.questionId})?.answerIds?.get(0)

                if (!mainQuestionAnswer) {
                    mainQuestionAnswer = mainQuestion?.getCountryResourceDefault(parentEntity)
                }
                List<Resource> resourceProfiles = mainQuestion?.getResourceProfiles(mainQuestionAnswer, null, indicator, parentEntity)
                selectQuestion = "${selectQuestion}${g.renderProfileOptions(resourceProfiles: resourceProfiles, indicator: indicator, profileAnswer: answer)}"
                selectQuestion = "${selectQuestion}</select>"

                if (preventChanges) {
                    selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ""}\" />"
                }
            } else if (additionalQuestion.questionId == Constants.SFB_CODE_QUESTIONID) {
                selectQuestion = "<select${preventChanges ? ' disabled=\"disabled\"' : ''} class=\"autocompletebox ${additionalQuestion?.hideValueInQuery ? 'hidden' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-dataSetId=\"${datasetId}\" name=\"${additionalfieldName}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} id=\"${additionalfieldId}\">"
                selectQuestion = "${selectQuestion}${g.renderSfbCodeQuestion(elementList: attrs.additionalQuestionResources, answer: answer,resource:resource, resourceGroups: additionalQuestion.resourceGroups, newResourceRowAdded:newResourceRowAdded)}"
                selectQuestion = "${selectQuestion}</select>"

                if (preventChanges) {
                    selectQuestion = "${selectQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${answer ? answer : ''}\" />"
                }
            } else {
                List<QuestionAnswerChoice> filteredChoises = questionService.getFilteredChoices(parentEntity, mainQuestion, resource, userGivenAnswer?.toString(), null, eolProcessCache, additionalQuestion)

                if (filteredChoises) {
                    selectQuestion = "${selectQuestion}<span class=\"hidden\">${answer ?: ''}</span>${additionalQuestion.questionId == "specialRuleId" ? "&nbsp;<span class=\"specialRuleSpan\">" : ""}"
                    if (additionalQuestion.questionId == "specialRuleId") {
                        if (userUnit && filteredChoises) {
                            filteredChoises = filteredChoises.findAll({ !it.allowedUnits||it.allowedUnits*.toLowerCase().contains(userUnit.toLowerCase()) })
                        } else if (filteredChoises) {
                            filteredChoises = filteredChoises.findAll({ !it.allowedUnits })
                        } else {
                            filteredChoises = null
                        }

                        if (filteredChoises) {
                            if (filteredChoises.size() == 1) {
                                selectQuestion = "${selectQuestion}${filteredChoises.first().localizedAnswer ?: ''}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${filteredChoises.first().answerId ?: ''}\" />"
                            } else {
                                selectQuestion = "${selectQuestion}${filteredChoises.find({answer?.toString()?.equalsIgnoreCase(it.answerId)})?.localizedAnswer ?: "Not defined"}"
                            }
                        }
                    } else {
                        if (additionalQuestion.eolChoicesFromSubType && !answer) {
                            answer = eolProcessService.getEolProcessForDataset(null, resource, resourceService.getSubType(resource), projectCountryResource, null, attrs.lcaModel?.toString(), eolProcessCache)?.eolProcessId
                        }
                        QuestionAnswerChoice questionAnswerChoice = filteredChoises.find({answer?.toString()?.equalsIgnoreCase(it.answerId)})
                        if (showAsDisabled) {
                            if (questionAnswerChoice) {
                                int nameMaxLength = stringUtilsService.calculateNameMaxLength(additionalQuestion?.inputWidth)
                                String localizedQuestionAnswerChoise = questionAnswerChoice.localizedAnswer
                                if (localizedQuestionAnswerChoise?.length() > nameMaxLength) {
                                    selectQuestion = "${selectQuestion}${localizedQuestionAnswerChoise ? "${g.abbr(value: localizedQuestionAnswerChoise, maxLength: nameMaxLength, smartCut: true)}" : ''}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${questionAnswerChoice.answerId}\" />"
                                } else {
                                    selectQuestion = "${selectQuestion}${localizedQuestionAnswerChoise}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', null, '${indicator?.indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${questionAnswerChoice.answerId}\" />"
                                }
                            }
                        } else {
                            selectQuestion = "${selectQuestion}${questionAnswerChoice?.localizedAnswer ?: "Not defined"}"
                        }
                    }

                    if (additionalQuestion.localizedUnit && !additionalQuestion.groupedQuestionIds) {
                        selectQuestion = "${selectQuestion}&nbsp;${additionalQuestion.localizedUnit}"
                    } else if (additionalQuestion.unitAsValueReference && !additionalQuestion.groupedQuestionIds) {
                        selectQuestion = "${selectQuestion}&nbsp;${valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: ''}"
                    }

                    if (additionalQuestion.questionId == "specialRuleId") {
                        selectQuestion = "${selectQuestion}</span>"
                    }
                }
            }
        }
        out << selectQuestion
    }

    def renderServiceLifeQuestion = { attrs ->
        String serviceLifeQuestion = ""
        Question additionalQuestion = attrs.additionalQuestion
        Question mainQuestion = attrs.mainQuestion
        Resource r = attrs.resource
        Indicator indicator = attrs.indicator
        String additionalfieldName = attrs.additionalfieldName
        String datasetId = attrs.datasetId
        String additionalfieldId = attrs.additionalfieldId
        String checkValue = attrs.checkValue
        String random = attrs.random
        String defaultServiceLife = attrs.defaultServiceLife
        def userGivenAnswer = attrs.userGivenAnswer
        Boolean disableEditing = attrs.disableEditing
        Boolean showStartupTips = attrs.showStartupTips
        Boolean constructionResource = attrs.constructionResource
        Boolean materialServiceLifeWarning = attrs.materialServiceLifeWarning
        Boolean datasetLocked = attrs.datasetLocked
        Boolean inheritConstructionServiceLife = attrs.inheritConstructionServiceLife

        def disabledAttribute = attrs.disabledAttribute
        def answer = attrs.answer
        def showMandatory = attrs.showMandatory

        Boolean serviceLifeFromSubType
        if (questionService.isWithPermanentServiceLife(mainQuestion, indicator)) {
            if (constructionResource && (!userGivenAnswer || "default".equals(userGivenAnswer))) {
                serviceLifeQuestion = "${serviceLifeQuestion}${message(code: "data_by_constituent")}"
            } else {
                serviceLifeQuestion = "${serviceLifeQuestion}${message(code: "resource.serviceLife.permanent")}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"permanent\" />"
            }
        } else if (constructionResource && (!userGivenAnswer || "default".equals(userGivenAnswer))) {
            serviceLifeQuestion = "${serviceLifeQuestion}${message(code: "data_by_constituent")}"
        } else if (defaultServiceLife && (!userGivenAnswer || "default".equals(userGivenAnswer))) {
            def serviceLife

            if (r && !r.construction) {
                if (r.serviceLifeOverride) {
                    serviceLife = r.serviceLife
                } else if (r.resourceSubType) {
                    if ("resourceServiceLife".equals(defaultServiceLife) || indicator?.useSpecificServiceLifes) {
                        serviceLife = r.serviceLife
                    } else {
                        ResourceType subType = resourceService.getSubType(r)

                        if (subType) {
                            serviceLife = DomainObjectUtil.callGetterByAttributeName(defaultServiceLife, subType)
                        }
                    }
                }

                if(!serviceLife && r.serviceLife == null){
                    ResourceType subType = resourceService.getSubType(r)

                    if (subType) {
                        serviceLife = subType.serviceLifeTechnical
                        serviceLifeFromSubType = true
                    }
                }
            }

            String popOverText = g.message(code: 'popOver.serviceLifeFromSubType') ?: ""
            String popOverContent = serviceLifeFromSubType ? """data-content="$popOverText" """ : ""
            serviceLifeQuestion = "${serviceLifeQuestion}<a style=\"display:inline-block;\" href=\"javascript:\" class=\"${disabledAttribute||disableEditing ? 'removeClicks' : 'usable'}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('numeric', this, '${additionalfieldName}', '${additionalfieldId}', null, null, true);\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" $popOverContent >${serviceLife && serviceLife.toString().isNumber() && serviceLife.intValue() != -1 ? serviceLife.intValue() : additionalQuestion?.specifiedSettingDisplayMode?.localizedDisplayText}</a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"default\" />"

            if (additionalQuestion.localizedUnit && (!serviceLife||(serviceLife && serviceLife.toString().isNumber() && serviceLife.intValue() != -1))) {
                serviceLifeQuestion = "${serviceLifeQuestion} <span class=\"visible\">&nbsp;${additionalQuestion?.localizedUnit}</span>"
            }
        } else {
            Integer formattedServiceLife

            if (answer) {
                if (DomainObjectUtil.isNumericValue(answer.toString())) {
                    formattedServiceLife = DomainObjectUtil.convertStringToDouble(answer.toString()).intValue()
                }
            }

            if (inheritConstructionServiceLife) {
                serviceLifeQuestion = "${serviceLifeQuestion}${message(code: "data_by_construction")}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"${formattedServiceLife ? formattedServiceLife : 0}\" />"
            } else if (formattedServiceLife == additionalQuestion.specifiedSettingDisplayMode?.specifiedSetting?.intValue()) {
                if (disabledAttribute) {
                    serviceLifeQuestion = "${serviceLifeQuestion}${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}"
                } else {
                    serviceLifeQuestion = "${serviceLifeQuestion}<span class=\"hidden\">${formattedServiceLife ? formattedServiceLife : 0}</span><a href=\"javascript:\" class=\"serviceLifeButton ${constructionResource && (additionalQuestion?.disableEditingForConstruction && !r.inheritConstructionServiceLife) ? 'removeClicks disabled' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion?.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', '${!additionalQuestion?.optional && showMandatory ? "true" : ''}', '${!additionalQuestion.specifiedSettingDisplayMode.setEmptyWhenClicked ? formattedServiceLife?.toString() : ''}','','','','','',${constructionResource && r.inheritConstructionServiceLife ? true : false});\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}</a>"
                    if (additionalQuestion.localizedUnit && formattedServiceLife != -1) {
                        serviceLifeQuestion = "${serviceLifeQuestion} <span class=\"visible\">&nbsp;${additionalQuestion?.localizedUnit}</span>"
                    }
                    serviceLifeQuestion = "${serviceLifeQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${additionalQuestion.specifiedSettingDisplayMode.specifiedSetting}\" />"
                }
            } else if (!serviceLifeQuestion && additionalQuestion.specifiedSettingDisplayMode) {
                serviceLifeQuestion = "${serviceLifeQuestion}<span class=\"hidden\">${formattedServiceLife ? formattedServiceLife : 0}</span>"
                serviceLifeQuestion = "${serviceLifeQuestion}<input type=\"text\""

                if (materialServiceLifeWarning && !additionalQuestion?.disableEditingForConstruction) {
                    serviceLifeQuestion = "${serviceLifeQuestion} data-popthis=\"true\" rel=\"popover_materialWarning\" data-content=\"${message(code:'resource.servicelife_warning', args:[""])}\" onclick=\"numericInputCheck();\""
                } else {
                    serviceLifeQuestion = "${serviceLifeQuestion} ${additionalQuestion?.uiNumericCheck}"
                }
                serviceLifeQuestion = "${serviceLifeQuestion} onblur=\"${checkValue}\" data-dataSetId=\"${datasetId}\""

                if (showStartupTips) {
                    serviceLifeQuestion = "${serviceLifeQuestion} data-content=\"${message(code: 'startup-tip.resourcerow_servicelife')}\" rel=\"startupTip_resourceRow\""
                }

                if (disableEditing) {
                    serviceLifeQuestion = "${serviceLifeQuestion} readonly"
                }
                serviceLifeQuestion = "${serviceLifeQuestion} class=\"input-small${showStartupTips ? ' startupTip' : ''}${additionalQuestion?.onlyNumeric ? ' numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\""
                serviceLifeQuestion = "${serviceLifeQuestion} ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: formattedServiceLife)}"
                serviceLifeQuestion = "${serviceLifeQuestion} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion?.questionId}\" id=\"${additionalfieldId}\" value=\"${formattedServiceLife != null ? formattedServiceLife : ''}\" />"
                if (additionalQuestion.localizedUnit && formattedServiceLife != -1) {
                    serviceLifeQuestion = "${serviceLifeQuestion} <span class=\"visible\">&nbsp;${additionalQuestion?.localizedUnit}</span>"
                }
            }
        }

        out << serviceLifeQuestion
    }

    def renderTextQuestion = { attrs ->
        Question additionalQuestion = attrs.additionalQuestion
        Entity entity = attrs.entity
        Resource r = attrs.resource
        String resourceId = attrs.resourceId
        String additionalfieldName = attrs.additionalfieldName
        String userSavedUnitForCost = attrs.userSavedUnitForCost
        String datasetId = attrs.datasetId
        String additionalfieldId = attrs.additionalfieldId
        String fullTextExpand = attrs.fullTextExpand
        String random = attrs.random
        String uniqueConstructionIdentifier = attrs.uniqueConstructionIdentifier
        Boolean disableEditing = attrs.disableEditing
        Boolean disableEditingThickness = attrs.disableEditingThickness
        Boolean constructionResource = attrs.constructionResource
        Boolean datasetLocked = attrs.datasetLocked
        Boolean allowVariableThickness = attrs.allowVariableThickness
        Boolean forceEnableEditingThickness = attrs.forceEnableEditingThickness
        Boolean hideThickness = attrs.hideThickness
        Boolean disabled = attrs.disabled
        Boolean preventChanges = attrs.preventChanges
        Boolean constructionPage = attrs.constructionPage
        Boolean splitPage = attrs.splitPage
        Boolean disableThickness = attrs.disableThickness
        String checkValue = attrs.checkValue
        String trId = attrs.trId
        String constructionUnit = attrs.constructionUnit
        String originalUnit = attrs.originalUnit
        String randomId = attrs.randomId
        Dataset dataset = attrs.dataset
        String unitFieldId = attrs.unitFieldId

        def disabledAttribute = attrs.disabledAttribute
        def disabledNoMass = false
        def answer = attrs.answer
        def showMandatory = attrs.showMandatory
        def additionalQuestionInputWidth = attrs.additionalQuestionInputWidth
        
        Boolean thicknessQuestion = additionalQuestion.isThicknessQuestion
        // This is for some input type text to have the size adjustable. It basically becomes a textarea
        Integer userAdjustableMaxWidth = additionalQuestion.userAdjustableMaxWidth ?: null
        String inputType = userAdjustableMaxWidth != null ? "<textarea style=\"resize: horizontal; max-width: ${userAdjustableMaxWidth}px; ${additionalQuestion.inputWidth ? " min-width: ${additionalQuestion.inputWidth}px; overflow: hidden" : ''}\" rows=\"1\" cols=\"100\"" : '<input type="text" '
        String textInputEndTag = userAdjustableMaxWidth != null ? '</textarea>' : '</input>'
        if(additionalQuestion && additionalQuestion.valueConstraints?.get("type")?.equals("numeric") && answer?.equals("na")){
            answer = null
        }
        String textQuestion = "<span class=\"hidden\">${answer ? answer.encodeAsHTML() : 0}</span>"
        if (additionalQuestion.isTransportQuestion && !r?.combinedUnits?.contains("KG".toLowerCase())) {
            disabledNoMass = true
        }
        if (additionalQuestion.specifiedSettingDisplayMode) {
            if (answer != null && DomainObjectUtil.isNumericValue(answer.toString()) &&
                    DomainObjectUtil.convertStringToDouble(answer.toString()) == additionalQuestion.specifiedSettingDisplayMode.specifiedSetting) {
                if (!disabledAttribute) {
                    textQuestion = "${textQuestion}<a href=\"javascript:\" class=\"serviceLifeButton ${additionalQuestion.disableEditingForConstruction && constructionResource ? 'removeClicks disabled' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion?.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', '${!additionalQuestion.optional && showMandatory ? "true" : ''}', '${!additionalQuestion.specifiedSettingDisplayMode.setEmptyWhenClicked ? "${answer?.toString()}" : ''}', false, '${additionalQuestion.localizedQuestion}', '${additionalQuestion.minAllowed}', '${additionalQuestion.maxAllowed}', '${additionalQuestion.inputWidth}');\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}</a>"
                    textQuestion = "${textQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${additionalQuestion.specifiedSettingDisplayMode.specifiedSetting}\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\"/>"
                } else {
                    textQuestion = "${textQuestion}${additionalQuestion.specifiedSettingDisplayMode.localizedDisplayText}"
                }
            } else {
                String displayValue = answer?.toString() ? optimiStringUtils.removeSingleAndDoubleQuotesAndBreakLineAndBeginningBlankSpaces(answer.toString()) : ''
                textQuestion = "${textQuestion} ${inputType} ${additionalQuestion.uiNumericCheck} onblur=\"${checkValue}\" data-dataSetId=\"${datasetId}\" ${disableEditing ? 'readonly' : ''} class=\"input-small ${additionalQuestion?.onlyNumeric ? 'numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" id=\"${additionalfieldId}\" value=\"${displayValue}\" >${userAdjustableMaxWidth != null ? displayValue : ''}${textInputEndTag}"

                if (additionalQuestion.showFullText) {
                    textQuestion = "${textQuestion}<a href=\"javascript:\" id=\"${additionalQuestion.questionId}expand\" ${fullTextExpand}>&nbsp;<i class=\"fa fa-arrow-up fa-rotate-45 fa-size-medium\"></i>< /a>"
                }

                if (additionalQuestion.localizedUnit && resourceId) {
                    textQuestion = "${textQuestion}<span class=\"${thicknessQuestion ? "unitAfterInput" : ""} visible\" id=\"${trId}unitAfterInput\">&nbsp;${additionalQuestion.localizedUnit}</span>"
                }

                if (["carShare","publicBusShare","publicRailShare","walkBikeShare"].contains(additionalQuestion.questionId)) {
                    String persistingDefaultAnswer = additionalQuestion.getDefaultValueByResourceId(r)

                    if (persistingDefaultAnswer) {
                        textQuestion = "${textQuestion}<input type=\"hidden\" class=\"persistingHiddenDefaultAnswer\" value=\"${persistingDefaultAnswer}\" />"
                    }
                }
            }
        } else {
            if (thicknessQuestion && (allowVariableThickness || forceEnableEditingThickness)) {
                String hiddenClass = ((!Constants.THICKNESS_REQUIRED_UNITS.contains(userSavedUnitForCost) && !forceEnableEditingThickness) && (!Constants.THICKNESS_REQUIRED_UNITS.contains(constructionUnit) && !forceEnableEditingThickness) && !Constants.THICKNESS_REQUIRED_UNITS.contains(originalUnit)) || hideThickness ? 'visibilityHidden' : ''
                textQuestion = "${textQuestion}<span class=\"xBoxSub ${hiddenClass}\" id=\"${trId}xBox\"><sub>x</sub></span>"
            }

            if (!constructionResource && additionalQuestion.isTransportQuestion && (answer == null || answer.isEmpty() || "default" == answer?.toString())) {
                Integer defaultTransportFromSubType = entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) ? questionService.getDefaultTransportFromSubType(entity, r, additionalQuestion.questionId) : null
                if (defaultTransportFromSubType != null) {
                    if (disabledNoMass) {
                        textQuestion = "${textQuestion}<div style=\"display: inline-block;\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code:"resourcerow.disabledNoMass")}\"><a style=\"display:inline-block; width:45px;\" href=\"javascript:\" class=\"removeClicks lockedDatasetQuestion\">${defaultTransportFromSubType}</a></div>"
                    } else {
                        textQuestion = "${textQuestion}<a style=\"display:inline-block; width:45px;\" href=\"javascript:\" class=\"${preventChanges || disabled ? 'removeClicks' : 'usable'}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', null, null, true, '${additionalQuestion.localizedQuestion}', '${additionalQuestion.minAllowed}', '${additionalQuestion.maxAllowed}', '${additionalQuestion.inputWidth}');\">${defaultTransportFromSubType}</a>"
                    }
                    textQuestion = "${textQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"default\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\"/>"
                } else {
                    String displayValue = entity?.defaults?.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) && answer?.toString() ? optimiStringUtils.removeSingleAndDoubleQuotesAndBreakLineAndBeginningBlankSpaces(answer.toString()) : ''

                    if (displayValue.equals("default")) {
                        displayValue = ''
                    }

                    textQuestion = "${textQuestion} ${inputType} data-dataSetId=\"${datasetId}\" ${disableEditing ? "readonly" : ""} onchange=\"\$(this).parent().updateSortVal(this.value);\" name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer, additionalQuestionInputWidth: additionalQuestionInputWidth?.get(additionalQuestion.questionId))} onchange=\"removeBorder(this${additionalQuestion.inputWidth ? ', \'' + additionalQuestion.inputWidth + '\'' : ''});updateHiddenField(this, '${additionalQuestion.questionId}');\" id=\"${additionalfieldId}\" onblur=\"${additionalQuestion.showFullText ? 'changeExpandText(\''+additionalQuestion.questionId+randomId +'expand'+'\',this)' : ''} ${checkValue}\" class=\"${datasetLocked ? 'lockedDatasetQuestion' : 'lockableDatasetQuestion'} input-small ${additionalQuestion.hideValueInQuery && !constructionPage ? 'hidden' : ''} ${additionalQuestion.onlyNumeric ? 'numeric' : ''}\" value=\"${displayValue}\" >${userAdjustableMaxWidth != null ? displayValue : ''}${textInputEndTag}"

                    if (additionalQuestion.showFullText) {
                        textQuestion = "${textQuestion}<a href=\"javascript:\" id=\"${additionalQuestion.questionId}${randomId}expand\" ${fullTextExpand}>&nbsp;<i class=\"fa fa-arrow-up fa-rotate-45 fa-size-medium\"></i></a>"
                    }
                }
            }else if (additionalQuestion.isMassPerUnitQuestion){
                String hiddenClass
                Double calculatedMassToDisplay
                //=== RE2108-59
                if(!r?.combinedUnits?.contains(Constants.UNIT_KG) & !dataset){
                    textQuestion = "${textQuestion} ${inputType} ${additionalQuestion.uiNumericCheck} onblur=\"${checkValue}\" data-dataSetId=\"${datasetId}\" ${disableEditing ? 'readonly' : ''} class=\"isMass input-small ${additionalQuestion?.onlyNumeric ? 'numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" id=\"${additionalfieldId}\" value=\"\" >${userAdjustableMaxWidth != null ? displayValue : ''} ${textInputEndTag}"
                }else if(r && !dataset) {
                    String firstUnit = unitConversionUtil.useUserGivenUnit(r)?.values()?.collect({it?.get(0)})?.get(0)
                    if(r?.combinedUnits?.contains(Constants.UNIT_KG) && Constants.UNIT_KG.equals(firstUnit) ){
                        calculatedMassToDisplay = 1D
                    }else if(r?.combinedUnits?.contains(Constants.UNIT_TON) && Constants.UNIT_TON.equals(firstUnit) ){
                        calculatedMassToDisplay = 1000D
                    } else {
                        calculatedMassToDisplay = 1D
                    }
                    if(calculatedMassToDisplay != null) {
                        textQuestion = "${textQuestion}<a style=\"display:inline-block; width:40px;\" href=\"javascript:\" class=\"isMass ${preventChanges || disabled ? 'removeClicks' : 'usable'}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', null, null, true, '${additionalQuestion.localizedQuestion}', '${additionalQuestion.minAllowed}', '${additionalQuestion.maxAllowed}', '${additionalQuestion.inputWidth}');\">${calculatedMassToDisplay.round(2)}</a>"
                        textQuestion = "${textQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"${calculatedMassToDisplay ? calculatedMassToDisplay.round(2) : 0}\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\"/>"
                    }else{
                       // textQuestion = "${textQuestion} ${inputType} ${additionalQuestion.uiNumericCheck} onblur=\"${checkValue}\" data-dataSetId=\"${datasetId}\" ${disableEditing ? 'readonly' : ''} class=\"isMass input-small ${additionalQuestion?.onlyNumeric ? 'numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" id=\"${additionalfieldId}\" value=\"\" >${userAdjustableMaxWidth != null ? displayValue : ''} ${textInputEndTag}"
                        textQuestion = "${textQuestion}<a style=\"display:inline-block; width:40px; text-decoration:none;\" href=\"javascript:\" class=\"isMass ${preventChanges || disabled ? 'removeClicks' : 'usable'}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', null, null, true, '${additionalQuestion.localizedQuestion}', '${additionalQuestion.minAllowed}', '${additionalQuestion.maxAllowed}', '${additionalQuestion.inputWidth}');\">-</a>"
                    }
                }else if(dataset){
                    String answerString = answer?.toString()?.trim()
                    if (answerString != null && !answerString.isEmpty() && dataset?.quantity > 0) {
                        calculatedMassToDisplay = DomainObjectUtil.convertStringToDouble(answerString)
                    } else if (Constants.UNIT_KG.equals(dataset?.userGivenUnit) || Constants.UNIT_UNIT.equals(dataset?.userGivenUnit)) {
                        calculatedMassToDisplay = 1D
                    } else if (Constants.UNIT_TON.equals(dataset?.userGivenUnit)) {
                        calculatedMassToDisplay = 1000D
                    } else {
                        calculatedMassToDisplay = 1D
                    }

                    //in case of answer was saved but not recalculated
                    if (entity.resultsOutOfDate && NumberUtils.isCreatable(answer?.toString())) {
                        calculatedMassToDisplay = NumberUtils.createDouble(answer.toString())
                    }

                    if(calculatedMassToDisplay && dataset.massPerUnit_kg) {
                        textQuestion = "${textQuestion}<a style=\"display:inline-block; width:40px;\" href=\"javascript:\" class=\"isMass ${preventChanges || disabled ? 'removeClicks' : 'usable'}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeLinkToTextfield('${additionalQuestion.onlyNumeric ? "numeric" : ''}', this, '${additionalfieldName}', '${additionalfieldId}', null, null, true, '${additionalQuestion.localizedQuestion}', '${additionalQuestion.minAllowed}', '${additionalQuestion.maxAllowed}', '${additionalQuestion.inputWidth}');\">${dataset.massPerUnit_kg * calculatedMassToDisplay.round(2)}</a>"
                        textQuestion = "${textQuestion}<input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" class=\"${additionalfieldId}\" value=\"${dataset.massPerUnit_kg ? dataset.massPerUnit_kg : 0}\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\"/>"
                    } else if(!calculatedMassToDisplay || !dataset?.quantity){
                        textQuestion = "${textQuestion} ${inputType} ${additionalQuestion.uiNumericCheck} onblur=\"${checkValue}\" data-dataSetId=\"${datasetId}\" ${disableEditing ? 'readonly' : ''} class=\"isMass input-small ${additionalQuestion?.onlyNumeric ? 'numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"} ${hiddenClass ? hiddenClass : ''}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer)} name=\"${additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" id=\"${additionalfieldId}\" value=\"\" >${userAdjustableMaxWidth != null ? displayValue : ''} ${textInputEndTag}"
                    }
                }
            } else {
                boolean disableDimensieEditing = Boolean.FALSE
                textQuestion = "${textQuestion} ${inputType} data-dataSetId=\"${datasetId}\" ${disableEditing ? "readonly" : ""} onchange=\"\$(this).parent().updateSortVal(this.value);\" data-questionId=\"${additionalQuestion.questionId}\" data-resourceId=\"${resourceId}\" "

                if(additionalQuestion.calculatedOutputField){
                    textQuestion += " data-calculatedOutputField=\"${true}\" readonly "
                }

                if (constructionResource && (thicknessQuestion || Constants.THICKNESS_MM_ADDQ_ID.contains(additionalQuestion.questionId) || Constants.NMD_SCALING_ADDQ_ID.contains(additionalQuestion.questionId))) {
                    textQuestion = "${textQuestion}oninput=\"multiplyConstructionResources('${uniqueConstructionIdentifier}', this, '${unitFieldId}');\""

                } else if (r?.nmdSchalingsFormuleID && Constants.NMD_SCALING_ADDQ_ID.contains(additionalQuestion.questionId)) {
                    if (!answer) {
                        disableDimensieEditing = Boolean.TRUE
                    }
                    textQuestion = "${textQuestion}oninput=\"multiplyConstructionResources('${uniqueConstructionIdentifier}', this, '${unitFieldId}');\""
                }
                if (disableEditingThickness && disableEditing) {
                    textQuestion = "${textQuestion} rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${g.message(code: "resourcerow.noSchalingAvailable")}\" "
                }
                String displayValue = answer?.toString() ? optimiStringUtils.removeSingleAndDoubleQuotesAndBreakLineAndBeginningBlankSpaces(answer.toString()) : ''
                textQuestion = "${textQuestion}name=\"${splitPage && thicknessQuestion ? "splittedOrChangedThickness${r?.resourceId}" : additionalfieldName}\" data-id=\"${random}${additionalQuestion.questionId}\" ${g.questionInlineStyle(question: additionalQuestion, showMandatory: showMandatory, answer: answer, additionalQuestionInputWidth: additionalQuestionInputWidth?.get(additionalQuestion.questionId))} onchange=\"removeBorder(this${additionalQuestion.inputWidth ? ', \'' + additionalQuestion.inputWidth + '\'' : ''});updateHiddenField(this, '${additionalQuestion.questionId}');\" id=\"${additionalfieldId}\" onblur=\"${additionalQuestion.showFullText ? 'changeExpandText(\'' + additionalQuestion.questionId + randomId + 'expand' + '\',this)' : ''} ${checkValue}\" class=\"input-small ${thicknessQuestion ? 'thicknessInput' : ''} ${thicknessQuestion && !forceEnableEditingThickness && ((resourceId && !allowVariableThickness) || hideThickness) ? 'visibilityHidden' : ''} ${additionalQuestion.hideValueInQuery && !constructionPage ? 'hidden' : ''} ${additionalQuestion.onlyNumeric ? 'numeric' : ''}${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" ${(disableThickness && thicknessQuestion && !forceEnableEditingThickness) || disableEditing ? 'readonly' : ''} ${disableDimensieEditing ? 'readOnly' : ''} ${thicknessQuestion && resourceId && !allowVariableThickness && !forceEnableEditingThickness ? 'readonly' : ''} value=\"${displayValue}\" >${userAdjustableMaxWidth != null ? displayValue : ''}${textInputEndTag}"

                if (Constants.NMD_SCALING_ADDQ_ID.contains(additionalQuestion.questionId) && r?.nmdSchalingUnit) {
                    textQuestion = "${textQuestion} ${r.nmdSchalingUnit}"
                }

                if (additionalQuestion.showFullText) {
                    textQuestion = "${textQuestion}<a href=\"javascript:\" id=\"${additionalQuestion.questionId}${randomId}expand\" ${fullTextExpand}>&nbsp;<i class=\"fa fa-arrow-up fa-rotate-45 fa-size-medium\"></i></a>"
                }
            }

            if (thicknessQuestion && additionalQuestion.localizedUnit && !additionalQuestion.groupedQuestionIds && resourceId && allowVariableThickness) {
                textQuestion = "${textQuestion} <span class=\"unitAfterInput${hideThickness && !forceEnableEditingThickness ? ' visibilityHidden' : ' visible'}\" id=\"${trId}unitAfterInput\">&nbsp;${additionalQuestion.localizedUnit}</span>"
            } else if (!thicknessQuestion && additionalQuestion.localizedUnit && resourceId && !additionalQuestion.isMassPerUnitQuestion) {
                textQuestion = "${textQuestion} <span class=\"visible\" id=\"${trId}unitAfterInput\">&nbsp;${additionalQuestion.localizedUnit}</span>"
            } else if (thicknessQuestion && additionalQuestion.unitAsValueReference && !additionalQuestion.groupedQuestionIds && resourceId && allowVariableThickness) {
                textQuestion = "${textQuestion} <span class=\"unitAfterInput${hideThickness && !forceEnableEditingThickness ? ' visibilityHidden' : ' visible'}\" id=\"${trId}unitAfterInput\">&nbsp;${valueReferenceService.getValueForEntity(additionalQuestion.unitAsValueReference, entity, Boolean.FALSE, Boolean.TRUE) ?: ''}</span>"
            } else if (additionalQuestion.addQunitByResource){
                String unitByResource = resourceService.callMethodByName(additionalQuestion?.addQunitByResource, r)
                textQuestion = "${textQuestion} <span class=\"visible\" id=\"${trId}unitAfterInput\">&nbsp;${unitByResource ?: ""}</span>"
            }else if(additionalQuestion.localizedUnit && resourceId && additionalQuestion.isMassPerUnitQuestion ){
                textQuestion = "${textQuestion} <span class=\"visible\" id=\"${trId}unitAfterInput\">&nbsp;${additionalQuestion.localizedUnit}</span>"
            }
        }
        out << textQuestion
    }

    def additionalQuestionAnswer = { attrs ->
        def answer
        Question additionalQuestion = attrs.additionalQuestion
        Resource r = attrs.resource
        String additionalQuestionAnswer = Constants.UNDEFINED.equals(attrs.additionalQuestionAnswer) ? null : attrs.additionalQuestionAnswer
        Entity e = attrs.entity
        if (additionalQuestionAnswer || additionalQuestionAnswer == 0) {
            answer = additionalQuestionAnswer
        } else if (additionalQuestion?.defaultValue) {
            answer = additionalQuestion.defaultValue
        } else if (additionalQuestion?.defaultValueFromResource) {
            if (e) {
                answer = questionService.getDefaultValueFromResourceOrSubType(e, r, additionalQuestion)
            } else {
                answer = additionalQuestion.getDefaultValueByResourceId(r)
            }
        } else if (additionalQuestion?.defaultValueFromResourceSubtype) {
            if (e) {
                answer = additionalQuestion.getDefaultValueFromResourceSubtype(e, r)
            } else {
                answer = additionalQuestion.getDefaultValueFromResourceSubtype(r)
            }

        }

        if (answer == null) {
            answer = ""
        }
        out << answer
    }

    /**
     * Set javascript function to check value for numeric input
     * this function check min max
     */
    def setCheckValue = { attrs ->
        Question additionalQuestion = attrs.additionalQuestion
        Resource resource = attrs.resource
        String toRender = ''
        if (additionalQuestion?.onlyNumeric) {
            if (additionalQuestion.questionId == Constants.DIMENSION1_NMD) {
                // NMD dimensie 1 input
                toRender = "checkValue(this, '${additionalQuestion?.localizedQuestion}', '${resource?.nmdSchalingMinX1}', '${resource?.nmdSchalingMaxX1}', '${additionalQuestion?.inputWidth}', true, '${message(code: 'value.reset')}');"
            } else if (additionalQuestion.questionId == Constants.DIMENSION2_NMD) {
                // NMD dimensie 2 input
                toRender = "checkValue(this, '${additionalQuestion?.localizedQuestion}', '${resource?.nmdSchalingMinX2}', '${resource?.nmdSchalingMaxX2}', '${additionalQuestion?.inputWidth}', true, '${message(code: 'value.reset')}');"
            } else {
                // min max value defined in question (config)
                toRender = "checkValue(this, '${additionalQuestion?.localizedQuestion}', '${additionalQuestion?.minAllowed}', '${additionalQuestion?.maxAllowed}', '${additionalQuestion?.inputWidth}');"
            }
        }
        out << toRender
    }
}
