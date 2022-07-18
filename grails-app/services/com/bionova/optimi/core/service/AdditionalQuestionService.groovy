package com.bionova.optimi.core.service

import com.bionova.optimi.calculation.cache.EolProcessCache
import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.Account
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.QuestionAnswerChoice
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.core.util.SpringUtil
import com.bionova.optimi.data.ResourceCache
import org.apache.commons.lang.StringUtils
import org.bson.Document

import javax.servlet.http.HttpSession

class AdditionalQuestionService {

    def optimiResourceService
    def eolProcessService
    def loggerUtil
    def flashService
    def nmdElementService
    def stringUtilsService
    def userService
    def resourceService
    QuestionService questionService

    private int oneLetterPixels = 4 // one letter = ~4px

    private String abbr(value, Integer maxLength, Boolean smartCut) {
        String abbrValue = ""

        if (value && maxLength) {
            abbrValue = value.toString()

            if (abbrValue.length() > maxLength) {
                if (smartCut) {
                    def newValue = abbrValue.substring(0, maxLength)
                    int index = StringUtils.lastIndexOf(newValue, " ", newValue.length())
                    if (index == -1) {
                        return StringUtils.stripEnd(newValue, ",")
                    } else {
                        return StringUtils.stripEnd(newValue.substring(0, index), ",")
                    }
                } else {
                    return StringUtils.substring(abbrValue, 0, maxLength)
                }
            } else {
                return abbrValue
            }
        } else {
            return abbrValue
        }
    }

    /**
     * render the local comp energy profile additional question
     * @param resource
     * @param parentEntity
     * @param additionalQuestion
     * @param additionalQuestionResources
     * @param additionalfieldName
     * @param additionalfieldId
     * @param newResourceRowAdded
     * @param lcaParametersAdditionalQuestion
     * @param answer
     * @param userGivenAnswer
     * @param mainQuestionAnswer
     * @param datasetLocked
     * @param inlineStyle
     * @param queryId
     * @param sectionId
     * @param questionId
     * @param showAsDisabled
     * @param preventChanges
     * @param localCompensationMethodVersion
     * @param indicatorId
     * @param defaultLocalCompCountry
     * @param overrideLocalResource use this to render the profile select even though the resource is local resource
     * @return
     */
    def getCompensationEnergyProfileAdditionalQuestion(Resource resource, Entity parentEntity, Question additionalQuestion, List<Document> additionalQuestionResources, String additionalfieldName, String additionalfieldId, Boolean newResourceRowAdded, Boolean lcaParametersAdditionalQuestion, answer, userGivenAnswer,
                                                       String mainQuestionAnswer, Boolean datasetLocked, String inlineStyle, String queryId, String sectionId, String questionId, Boolean showAsDisabled, Boolean preventChanges, String localCompensationMethodVersion, String indicatorId,
                                                       String defaultLocalCompCountry, Boolean overrideLocalResource = false) {
        String toRender = ""

        Account account = userService.getAccount(userService.getCurrentUser())
        String accountId = account?.id
        Boolean localCompsDisabled = !localCompensationMethodVersion||"00".equals(localCompensationMethodVersion)
        if (resource) {
            if ("noLocalCompensation".equals(mainQuestionAnswer)||(localCompsDisabled && !resourceService.getIsLocalResource(resource.areas) && !userGivenAnswer)) {
                toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"\" />"
            } else {
                if (newResourceRowAdded||lcaParametersAdditionalQuestion) {
                    // New resource rows show selects from the start!
                    String paramsQueryEnergyProfile

                    if (newResourceRowAdded && !localCompsDisabled) {
                        paramsQueryEnergyProfile = parentEntity?.datasets?.find({ it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID && it.sectionId == com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID && it.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_TARGET_QUESTIONID })?.additionalQuestionAnswers?.get(com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)
                    }
                    String targetDefaultResourceId = optimiResourceService.getResourceWithParams(mainQuestionAnswer, "default")?.countryEnergyResourceId

                    if (showAsDisabled || preventChanges) {
                        List<Document> availableAnswers = additionalQuestionResources.findAll({it.areas && it.areas.contains(mainQuestionAnswer)})?.sort({it.defaultProfile})?.reverse()
                        Document p
                        if (userGivenAnswer && lcaParametersAdditionalQuestion) {
                            p = availableAnswers?.find({userGivenAnswer.equals("${it.resourceId}.${it.profileId}".toString())})
                        }
                        if (!p && paramsQueryEnergyProfile) {
                            p = availableAnswers?.find({paramsQueryEnergyProfile.equals("${it.resourceId}.${it.profileId}".toString())})
                        }
                        if (!p && targetDefaultResourceId) {
                            p = availableAnswers?.find({it.defaultProfile && it.active && targetDefaultResourceId.equals("${it.resourceId}.${it.profileId}".toString())})
                        }
                        if (!p && availableAnswers) {
                            p = availableAnswers.first()
                        }

                        if (p) {
                            String valueString = "${p.resourceId}.${p.profileId}"

                            if (defaultLocalCompCountry && resource.areas && resource.areas.contains(defaultLocalCompCountry)) {
                                toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                            } else {
                                int nameMaxLength = additionalQuestion.inputWidth ? (additionalQuestion.inputWidth / oneLetterPixels) <= 3 ? 3 : (additionalQuestion.inputWidth / oneLetterPixels) : 20

                                if ((p.profileNameEN && p.profileNameEN.toString().length() > nameMaxLength)||(!p.profileNameEN && p.profileId && p.profileId.toString().length() > nameMaxLength)) {
                                    toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${abbr(p.profileNameEN ?: p.profileId, nameMaxLength, true)}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', '${mainQuestionAnswer}', '${indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                                } else {
                                    toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${p.profileNameEN ?: p.profileId}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}', '${mainQuestionAnswer}', '${indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                                }
                            }
                        }
                    } else {
                        if (defaultLocalCompCountry && resource.areas && resource.areas.contains(defaultLocalCompCountry)) {
                            toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${paramsQueryEnergyProfile ?: targetDefaultResourceId ?: ""}\" />"
                        } else {

                            toRender = "${toRender}&nbsp;&nbsp;<select class=\"compensationEnergyProfileSelect ${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" ${inlineStyle} name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"
                            additionalQuestionResources.findAll({it.areas && it.areas.contains(mainQuestionAnswer)})?.sort({it.defaultProfile})?.reverse()?.each { Document p ->
                                String valueString = "${p.resourceId}.${p.profileId}"
                                if (!p.privateDatasetAccountId || p.privateDatasetAccountId == accountId) {
                                    // allow inactive if its selected
                                    if (p.active || ((lcaParametersAdditionalQuestion && userGivenAnswer && valueString.equals(userGivenAnswer)) || (paramsQueryEnergyProfile && paramsQueryEnergyProfile.equals(valueString)))) {
                                        toRender = "${toRender}<option value=\"${valueString}\" ${lcaParametersAdditionalQuestion && userGivenAnswer ? userGivenAnswer.equals(valueString) ? "selected=\"selected\"" : "" : paramsQueryEnergyProfile ? paramsQueryEnergyProfile.equals(valueString) ? "selected=\"selected\"" : "" : p.resourceId?.equals(targetDefaultResourceId) && p.defaultProfile ? "selected=\"selected\"" : ""}>${p.profileNameEN ?: p.profileId} - ${p.staticFullName}</option>"
                                    }
                                }
                            }
                            toRender = "${toRender}</select>"
                        }
                    }
                } else {
                    String selectedProfile = userGivenAnswer
                    if (!selectedProfile) {
                        selectedProfile = findDefaultDocumentForLocalCompProfile(additionalQuestionResources,parentEntity?.datasets,mainQuestionAnswer)
                    }
                    if (selectedProfile) {
                        if (defaultLocalCompCountry && resource.areas && resource.areas.contains(defaultLocalCompCountry) && !overrideLocalResource) {
                            // local resource, take default selectedProfile and hide it
                            toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selectedProfile ?: ""}\" />"
                        } else {
                            String profileResourceId = selectedProfile.tokenize(".")[0]
                            String profileProfileId = selectedProfile.tokenize(".")[1]

                            Document selectedProfileResource = additionalQuestionResources.find({it.areas?.contains(mainQuestionAnswer) && profileResourceId.equals(it.resourceId) && profileProfileId.equals(it.profileId)})
                            if(!selectedProfileResource){
                                selectedProfile = findDefaultDocumentForLocalCompProfile(additionalQuestionResources,parentEntity?.datasets,mainQuestionAnswer)
                                if(selectedProfile){
                                    profileResourceId = selectedProfile.tokenize(".")[0]
                                    profileProfileId = selectedProfile.tokenize(".")[1]
                                    selectedProfileResource = additionalQuestionResources.find({it.areas?.contains(mainQuestionAnswer) && profileResourceId.equals(it.resourceId) && profileProfileId.equals(it.profileId)})
                                }
                            }
                            if (selectedProfileResource) {
                                Integer nameMaxLength = additionalQuestion.inputWidth ? (additionalQuestion.inputWidth / oneLetterPixels) <= 3 ? 3 : (additionalQuestion.inputWidth / oneLetterPixels) : 20
                                // Show just the profile name or profileId in the green link, in selects show also staticFullName
                                String nameString = "${selectedProfileResource.profileNameEN ?: selectedProfileResource.profileId}"
                                if (showAsDisabled || preventChanges) {
                                    String valueString = "${profileResourceId}.${profileProfileId}"

                                    if (nameString.length() > nameMaxLength) {
                                        toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${abbr(nameString, nameMaxLength, true)}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}','${mainQuestionAnswer}', '${indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                                    } else {
                                        toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${nameString}&nbsp;<a href=\"javascript:\" class=\"infoBubble\" rel=\"question_sourceListing\" id=\"${UUID.randomUUID().toString()}info\" onclick=\"openQuestionChoises(this,'${additionalQuestion.questionId}', '${resource?.resourceId}','${mainQuestionAnswer}', '${indicatorId}', '${queryId}')\"><i class=\"fa fa-question greenInfoBubble\" aria-hidden=\"true\"></i></a></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                                    }
                                } else {
                                    if (nameString.length() > nameMaxLength) {
                                        if (additionalQuestion.specifiedSettingDisplayMode?.localizedHoverOverText) {
                                            toRender = "${toRender}&nbsp;&nbsp;<a href=\"javascript:\" class=\"compensationEnergyProfileSelect serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resource.resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selectedProfile}', '${parentEntity?.id?.toString()}', false, null, false, '${mainQuestionAnswer}');\" rel=\"popover\" data-trigger=\"hover\"  data-html=\"true\" data-content=\"${nameString}<br/>${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${abbr(nameString, nameMaxLength, true)}"
                                        } else {
                                            toRender = "${toRender}&nbsp;&nbsp;<a href=\"javascript:\" class=\"compensationEnergyProfileSelect serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resource.resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selectedProfile}', '${parentEntity?.id?.toString()}', false, null, false, '${mainQuestionAnswer}');\" >${abbr(nameString, nameMaxLength, true)}"
                                        }
                                    } else {
                                        if (additionalQuestion.specifiedSettingDisplayMode?.localizedHoverOverText) {
                                            toRender = "${toRender}&nbsp;&nbsp;<a href=\"javascript:\" class=\"compensationEnergyProfileSelect serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resource.resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selectedProfile}', '${parentEntity?.id?.toString()}', false, null, false, '${mainQuestionAnswer}');\" rel=\"popover\" data-trigger=\"hover\"  data-html=\"true\" data-content=\"${additionalQuestion.specifiedSettingDisplayMode.localizedHoverOverText}\">${nameString}"
                                        } else {
                                            toRender = "${toRender}&nbsp;&nbsp;<a href=\"javascript:\" class=\"compensationEnergyProfileSelect serviceLifeButton${datasetLocked ? " lockedDatasetQuestion" : " lockableDatasetQuestion"}\" data-additionalQuestionId=\"${additionalQuestion?.questionId}\" onclick=\"changeAdditionalQuestionLinkToSelect(this, '${additionalQuestion.questionId}', '${resource.resourceId}', '${queryId}', '${sectionId}', '${questionId}', '${selectedProfile}', '${parentEntity?.id?.toString()}', false, null, false, '${mainQuestionAnswer}');\" >${nameString}"
                                        }
                                    }
                                    toRender = "${toRender}</a><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${selectedProfile ?: ""}\" />"
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (mainQuestionAnswer) {
                String targetDefaultResourceId = optimiResourceService.getResourceWithParams(mainQuestionAnswer, "default")?.countryEnergyResourceId

                if (showAsDisabled || preventChanges) {
                    List<Document> allowedAnswers = additionalQuestionResources.findAll({it.active && it.areas && it.areas.contains(mainQuestionAnswer)})?.sort({it.defaultProfile})?.reverse()
                    Document p = allowedAnswers?.find({it.resourceId?.equals(targetDefaultResourceId) && it.defaultProfile})

                    if (!p && allowedAnswers) {
                        p = allowedAnswers.first()
                    }

                    if (p) {
                        Integer nameMaxLength = additionalQuestion.inputWidth ? (additionalQuestion.inputWidth / oneLetterPixels) <= 3 ? 3 : (additionalQuestion.inputWidth / oneLetterPixels) : 20
                        String valueString = "${p.resourceId}.${p.profileId}"

                        if ((p.profileNameEN && p.profileNameEN.toString().length() > nameMaxLength)||(!p.profileNameEN && p.profileId && p.profileId.toString().length() > nameMaxLength)) {
                            toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${abbr(p.profileNameEN ?: p.profileId, nameMaxLength, true)}</span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                        } else {
                            toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\">${p.profileNameEN ?: p.profileId}</span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"${valueString}\" />"
                        }
                    }
                } else {
                    toRender = "${toRender}&nbsp;&nbsp;<select class=\"compensationEnergyProfileSelect ${datasetLocked ? "lockedDatasetQuestion" : "lockableDatasetQuestion"}\" ${inlineStyle} name=\"${additionalfieldName}\" id=\"${additionalfieldId}\">"
                    additionalQuestionResources.findAll({it.active && it.areas && it.areas.contains(mainQuestionAnswer)})?.sort({it.defaultProfile})?.reverse()?.each { Document p ->
                        String valueString = "${p.resourceId}.${p.profileId}"
                        toRender = "${toRender}<option value=\"${valueString}\" ${p.resourceId?.equals(targetDefaultResourceId) && p.defaultProfile ? "selected=\"selected\"" : ""}>${p.profileNameEN ?: p.profileId} - ${p.staticFullName}</option>"
                    }
                    toRender = "${toRender}</select>"
                }
            } else {
                toRender = "${toRender}&nbsp;&nbsp;<span class=\"compensationEnergyProfileSelect\"></span><input type=\"hidden\" name=\"${additionalfieldName}\" id=\"${additionalfieldId}\" value=\"\" />"
            }
        }
        return toRender
    }
    private String findDefaultDocumentForLocalCompProfile(List<Document> additionalQuestionResources,Set<Dataset> datasetSet,String areaToCheck){
        String selectedProfile
        if(additionalQuestionResources && datasetSet && areaToCheck){
            List<Document> allowedProfiles = additionalQuestionResources.findAll({it.active && it.areas && it.areas.contains(areaToCheck)})?.sort({it.defaultProfile})?.reverse()

            if (allowedProfiles) {
                Document first = null
                String paramsQueryEnergyProfile = datasetSet?.find({it.queryId == com.bionova.optimi.core.Constants.LCA_PARAMETERS_QUERYID && it.sectionId == com.bionova.optimi.core.Constants.CALC_DEFAULTS_SECTIONID && it.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_TARGET_QUESTIONID})?.additionalQuestionAnswers?.get(com.bionova.optimi.core.Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)

                if (paramsQueryEnergyProfile) {
                    first = allowedProfiles.find({paramsQueryEnergyProfile.equals("${it.resourceId}.${it.profileId}".toString())})
                }

                if (!first) {
                    String targetDefaultResourceId = optimiResourceService.getResourceWithParams(areaToCheck, "default")?.countryEnergyResourceId

                    if (targetDefaultResourceId) {
                        first = allowedProfiles.find({targetDefaultResourceId.equals(it.resourceId) && it.defaultProfile})

                        if (!first) {
                            first = allowedProfiles.first()
                        }
                    } else {
                        first = allowedProfiles.first()
                    }

                }

                if (first) {
                    selectedProfile = first.resourceId + "." + first.profileId
                }
            }
        }

        return selectedProfile
    }
    def getResolveSelectedResourceParams(Question additionalQuestion, Resource resource, Resource projectCountryResource,
                                         Question mainQuestion, List<Document> additionalQuestionResources, answer, userGivenAnswer,
                                         String defaultLocalCompCountry, String localizedNameGetter, String userUnit, String lcaModel,
                                         Entity parentEntity, String localCompensationMethodVersion, EolProcessCache eolProcessCache) {
        Map<String, Object> resolvedParams = [:]
        String selecedResourceId
        String selectedResourceLocalizedName
        Boolean inactive = Boolean.FALSE
        Boolean failingLocalComps = Boolean.FALSE
        Boolean usingGenericLocal = Boolean.FALSE
        Boolean showAsText = Boolean.FALSE

        if (additionalQuestionResources) {
            if (!Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER.equals(userGivenAnswer)) {
                if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID) {
                    Boolean localResource = resourceService.getIsLocalResource(resource.areas)
                    Boolean localCompsDisabled = !localCompensationMethodVersion||"00".equals(localCompensationMethodVersion)
                    Boolean localCompsIsAvailable = optimiResourceService.isLocalCompApplicable(resource)

                    if (localCompsIsAvailable && (localResource||!localCompsDisabled)) {
                        if (userGivenAnswer) {
                            for (Document d in additionalQuestionResources) {
                                if (d.resourceId == userGivenAnswer.toString()) {
                                    selecedResourceId = d.resourceId
                                    selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                    if (!d.active) {
                                        inactive = Boolean.TRUE
                                    }
                                    if (localResource && projectCountryResource?.resourceId?.equals(selecedResourceId)) {
                                        usingGenericLocal = Boolean.TRUE
                                    }
                                    break
                                }
                            }
                        } else if (defaultLocalCompCountry && !localCompsDisabled) {
                            for (Document d in additionalQuestionResources) {
                                if (d.resourceId == defaultLocalCompCountry.toString()) {
                                    selecedResourceId = d.resourceId
                                    selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                    if (!d.active) {
                                        inactive = Boolean.TRUE
                                    }
                                    if (localResource && projectCountryResource?.resourceId?.equals(selecedResourceId)) {
                                        usingGenericLocal = Boolean.TRUE
                                    }
                                    break
                                }
                            }
                        } else if (localResource && projectCountryResource) {
                            for (Document d in additionalQuestionResources) {
                                if (d.resourceId == projectCountryResource.resourceId.toString()) {
                                    selecedResourceId = d.resourceId
                                    selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                    usingGenericLocal = Boolean.TRUE
                                    if (!d.active) {
                                        inactive = Boolean.TRUE
                                    }
                                    break
                                }
                            }
                        }
                    } else {
                        if (localCompsIsAvailable && userGivenAnswer) {
                            for (Document d in additionalQuestionResources) {
                                if (d.resourceId == userGivenAnswer.toString()) {
                                    selecedResourceId = d.resourceId
                                    selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                    if (!d.active) {
                                        inactive = Boolean.TRUE
                                    }
                                    break
                                }
                            }
                        } else {
                            failingLocalComps = Boolean.TRUE
                        }
                    }
                } else {
                    if (userGivenAnswer) {
                        for (Document d in additionalQuestionResources) {
                            if (d.resourceId == userGivenAnswer.toString()) {
                                selecedResourceId = d.resourceId
                                selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                if (!d.active) {
                                    inactive = Boolean.TRUE
                                }
                                break
                            }
                        }

                    } else if (answer) {
                        for (Document d in additionalQuestionResources) {
                            if (d.resourceId == answer.toString()) {
                                selecedResourceId = d.resourceId
                                selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                                if (!d.active) {
                                    inactive = Boolean.TRUE
                                }
                                break
                            }
                        }
                    }
                }
            }

            if ((!selecedResourceId || !selectedResourceLocalizedName) && !failingLocalComps) {
                if (additionalQuestion.questionId == com.bionova.optimi.core.Constants.LOCAL_COMP_QUESTIONID) {
                    selecedResourceId = "noLocalCompensation"
                    selectedResourceLocalizedName = stringUtilsService.getLocalizedText('notApplied')
                } else if (additionalQuestion.noChoiceDisplayText) {
                    selecedResourceId = Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER
                    selectedResourceLocalizedName = additionalQuestion.localizedNoChoiceDisplayText
                } else {
                    Document d = additionalQuestionResources.first()
                    selecedResourceId = d.resourceId
                    selectedResourceLocalizedName = d.get(localizedNameGetter) ?: d.nameEN
                }
            }
        } else {
            List<QuestionAnswerChoice> filteredChoises = questionService.getFilteredChoices(parentEntity, mainQuestion, resource, userGivenAnswer, null, eolProcessCache, additionalQuestion)

            if (additionalQuestion.questionId == "specialRuleId") {
                if (userUnit && filteredChoises) {
                    filteredChoises = filteredChoises.findAll({ !it.allowedUnits||it.allowedUnits*.toLowerCase().contains(userUnit.toLowerCase()) })
                } else if (filteredChoises) {
                    filteredChoises = filteredChoises.findAll({ !it.allowedUnits })
                } else {
                    filteredChoises = null
                }
            }

            if (filteredChoises) {
                if (additionalQuestion.eolChoicesFromSubType && !answer) {
                    answer = eolProcessService.getEolProcessForDataset(null, resource, resourceService.getSubType(resource), projectCountryResource, null, lcaModel, eolProcessCache)?.eolProcessId
                }

                if (answer && !Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER.equals(answer)) {
                    QuestionAnswerChoice questionAnswerChoice = filteredChoises.find({ answer.toString().equalsIgnoreCase(it.answerId) })

                    if (questionAnswerChoice) {
                        selecedResourceId = questionAnswerChoice.answerId
                        selectedResourceLocalizedName = questionAnswerChoice.localizedAnswer
                    } else {
                        // Failover if answer is not matching any choice.
                        questionAnswerChoice = filteredChoises.first()
                        selecedResourceId = questionAnswerChoice.answerId
                        selectedResourceLocalizedName = questionAnswerChoice.localizedAnswer
                    }
                } else if (additionalQuestion.noChoiceDisplayText) {
                    selecedResourceId = Constants.UNDEFINED_ADDITIONAL_QUESTION_ANSWER
                    selectedResourceLocalizedName = additionalQuestion.localizedNoChoiceDisplayText
                } else {
                    QuestionAnswerChoice questionAnswerChoice = filteredChoises.first()
                    selecedResourceId = questionAnswerChoice.answerId
                    selectedResourceLocalizedName = questionAnswerChoice.localizedAnswer
                }

                if (additionalQuestion.questionId == "specialRuleId" && filteredChoises.size() == 1) {
                    // Only not defined available so show only text
                    showAsText = Boolean.TRUE
                }
            }
        }
        resolvedParams.put("selecedResourceId", selecedResourceId)
        resolvedParams.put("selectedResourceLocalizedName", selectedResourceLocalizedName)
        resolvedParams.put("inactive", inactive)
        resolvedParams.put("failingLocalComps", failingLocalComps)
        resolvedParams.put("usingGenericLocal", usingGenericLocal)
        resolvedParams.put("showAsText", showAsText)
        return resolvedParams

    }

    /**
     * Put list of resources for the additional question (if the add q has resourceGroups) to a map
     * @param additionalQuestion
     * @param mapToPut
     * @param indicator
     * @param queryId
     * @param session
     */
    void putAdditionalQuestionResourcesToMap(Question additionalQuestion, Map<String, List<Document>> mapToPut, Indicator indicator, String queryId, HttpSession session) {
        try {
            Account account = userService.getAccount(userService.getCurrentUser())
            String accountId = account?.id

            if (additionalQuestion.questionId.equalsIgnoreCase(com.bionova.optimi.core.Constants.SFB_CODE_QUESTIONID)) {
                List<Document> nmdElement = session?.getAttribute(Constants.SessionAttribute.NMD_ELEMENT_LIST.attribute) as List<Document>
                if (!nmdElement) {
                    nmdElement = nmdElementService.getNmdElementsAsDocument()
                    session?.setAttribute(Constants.SessionAttribute.NMD_ELEMENT_LIST.attribute, nmdElement)
                }
                if (nmdElement) {
                    mapToPut.put(additionalQuestion.questionId, nmdElement)
                }
            } else {
                List<Document> resources = additionalQuestion.getAdditionalQuestionResources(indicator, queryId)

                if (resources) {
                    // removes private resources when the account Id does not  match
                    resources.removeAll{resource -> (resource.privateDatasetAccountId && !resource.privateDatasetAccountId.equals(accountId))}
                    mapToPut.put(additionalQuestion.questionId, resources)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in putAdditionalQuestionResourcesToMap, questionId ${additionalQuestion?.questionId}", e)
            flashService.setErrorAlert("Error in putAdditionalQuestionResourcesToMap, questionId ${additionalQuestion?.questionId}: ${e.message}", true)
        }
    }

    /**
     * Remove some addQs that we don't need for group editing
     * @param additionalQuestions
     * @param indicator
     */
    void removeAdditionalQuestionsForGroupEditing(Set<Question> additionalQuestions, Indicator indicator) {
        Set<String> toRemoveIds = [com.bionova.optimi.core.Constants.CARBON_IMPACT_ADDITIONAL_QUESTIONID,
                                   com.bionova.optimi.core.Constants.EOL_QUESTIONID]
        if (indicator?.excludeAdditionalQuestionsInGroupEdit?.size() > 0) {
            toRemoveIds.addAll(indicator.excludeAdditionalQuestionsInGroupEdit)
        }
        additionalQuestions?.removeIf({it.isThicknessQuestion || toRemoveIds.contains(it.questionId)})
    }

    /**
     * SW-1525
     * Enable or disable check box in additional question based on enabling condition.
     * @param additionalQuestions
     * @param resource
     * @return String
     * */
    Boolean enableDisableFieldByEnablingCondition(Question additionalQuestion, Resource resource, ResourceCache resourceCache = null) {
        Boolean disabledFlag = false
        if (additionalQuestion && additionalQuestion.enablingCondition) {
            try {
                Map.Entry<String, Boolean> entry = additionalQuestion.enablingCondition.entrySet().iterator().next()
                if (entry) {
                    Object objectValue = optimiResourceService.resourceResourceTypeValueGetterByAttributeName(entry.getKey(), resource, resourceCache)
                    if (objectValue != entry.getValue()) {
                        disabledFlag = true
                    }
                }
            } catch (NoSuchElementException e) {
                LoggerUtil loggerUtil = SpringUtil.getBean("loggerUtil")
                String errorMessage = "Enabling condition does not have valid data"
                loggerUtil.warn(log, errorMessage)
            }
        }
        return disabledFlag
    }
}
