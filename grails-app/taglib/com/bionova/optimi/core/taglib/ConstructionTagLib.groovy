package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.Resource

class ConstructionTagLib {
    static defaultEncodeAs = "raw"
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    static namespace = "constructionRender"
    def optimiResourceService
    def queryService
    def userService
    def datasetService
    def loggerUtil
    def unitConversionUtil
    def resourceFilterCriteriaUtil
    def indicatorService
    def entityService
    def denominatorUtil
    def configurationService
    def constructionService

    static returnObjectForTags = ['allowMultipleUnitsInConstruction']

    def missmatchDataWarning = { attrs ->
        Construction construction = attrs.construction
        ConstructionGroup group = attrs.group
        String returnable = ""

        if (construction && constructionService.getMissMatchedData(construction.datasets)) {
            returnable = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${message(code: 'query.resource.not_in_filter')}\" id=\"${construction.id}notInFilter\"><i class=\"icon-alert\"></i></a>"
        } else if (group) {
            List <Construction> constructions = constructionService.getConstructionsByGroup(group.groupId)
            if (constructions) {
                if (constructions.find({constructionService.getMissMatchedData(it.datasets)})) {
                    returnable = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${message(code: 'query.resource.not_in_filter')}\" id=\"${group?.id}notInFilter\"><i class=\"icon-alert\"></i></a>"
                }
            }
        }
        out << returnable
    }

    def missMatchDataAlert = { attrs ->
        ConstructionGroup group = attrs.group
        String toRender = ""
        if (group) {
            List <Construction> missMatches = constructionService.getConstructionsByGroup(group.groupId)?.findAll({constructionService.getMissMatchedData(it.datasets)})
            if (missMatches) {
                toRender = "<div class=\"container\"><div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<strong>${message(code: 'incompatible_warning_heading', args: [missMatches.size()])}</strong><ul>"

                missMatches.each { Construction construction ->

                    toRender = "${toRender}<li>Construction ${constructionService.getMirrorResourceObject(construction)?.staticFullName} has datasets with incompatible units</li>"
                }
                toRender = "${toRender}</ul></div></div>"
            }
        }
        out << toRender
    }

    def inactiveDataWarning = { attrs ->
        Construction construction = attrs.construction
        ConstructionGroup group = attrs.group
        String toRender = ""

        if (construction && constructionService.getInactiveData(construction.datasets)) {
            toRender = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"Inactive constituents found\" id=\"${construction.id}notInFilter\">${asset.image(src: "img/icon-warning.png", style: "max-width:14px")}</a>"
        } else if (group) {
            List <Construction> constructions = constructionService.getConstructionsByGroup(group.groupId)
            if (constructions) {
                if (constructions.find({constructionService.getInactiveData(it.datasets)})) {
                    toRender = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"Inactive constituents found\" id=\"${group?.id}notInFilter\">${asset.image(src: "img/icon-warning.png", style: "max-width:14px")}</i></a>"
                }
            }
        }
        out << toRender
    }

    def inactiveDataAlert = { attrs ->
        ConstructionGroup group = attrs.group
        String toRender = ""
        if (group) {
            List <Construction> inactiveData = constructionService.getConstructionsByGroup(group.groupId)?.findAll({constructionService.getInactiveData(it.datasets)})
            if (inactiveData) {
                toRender = "<div class=\"container\"><div class=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>" +
                        "<strong>Constructions with inactive resources, (${inactiveData.size()}): inactive data will not be rendered in queries</strong><ul>"

                inactiveData.each { Construction construction ->
                    def inactiveDataResources =  constructionService.getInactiveData(construction?.datasets)?.collect({datasetService.getResource(it)?.staticFullName})
                    String link = "${createLink(controller: "construction", action:"form", params:[id:construction.id, classificationQuestionId: construction.classificationQuestionId, classificationParamId: construction.classificationParamId, group:group?.id])}"
                    toRender = "${toRender}<li><a href='${link}'>Construction ${constructionService.getMirrorResourceObject(construction)?.staticFullName} </a> <strong> has datasets with inactive resources ${constructionService.getInactiveData(construction?.datasets)?.size()}: ${inactiveDataResources} </strong></li>"
                }
                toRender = "${toRender}</ul></div></div>"
            }
        }
        out << toRender
    }

    def allowMultipleUnitsInConstruction = { attrs ->
        boolean allow = false

        Resource resource = attrs.resource

        // Currently we only allow constructions to have multiple units on UI if it's NMD construction with unit conversion factor available
        if (resource && resource.construction && resource.nmdUnitConversionFactor != null) {
            allow = true
        }

        return allow
    }
}
