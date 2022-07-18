package com.bionova.optimi.construction.controller


import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EpdRequest
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.service.Re2020ApiService
import com.bionova.optimi.core.service.StringUtilsService
import com.bionova.optimi.core.service.UserService
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.frenchTools.FrenchConstants
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.bson.Document
import org.grails.web.json.JSONObject

class EpdRequestController {
    def activeCampaignService
    def resourceTypeService
    def optimiResourceService
    def sendMeDataRequestService
    def flashService
    Re2020ApiService re2020ApiService
    UserService userService
    StringUtilsService stringUtilsService

    // Deprecate code, to be removed by the end of feature 0.5.0 release
    /*@Secured(["ROLE_SALES_VIEW"])
    def list() {
        List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({ it.nameEN })

        [allEpdRequest: EpdRequest.getAll(), countries: countries, sendMeDataRequests: sendMeDataRequestService.getAllSendMeDataRequests()]

    }*/

    def yourEpdRequestList() {
        String manualId = params.manualId
        Boolean fromLink = params.externalLink ? Boolean.TRUE : Boolean.FALSE
        List<EpdRequest> epdRequests = []
        Entity entity
        List<Document> countries = []
        String basicQueryTable = ""
        List<Document> subTypeObjs = []
        User user
        if (manualId) {
            epdRequests = EpdRequest.findAllByManualId(manualId)
            if (epdRequests) {
                List<String> subtypes = epdRequests.collect({ it.subtypeForSendData })
                subTypeObjs = resourceTypeService.getSubTypeDocumentsBySubTypes(subtypes, null, true)
                countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({ it.nameEN })
            }
        }

        [epdRequests: epdRequests, countries: countries, basicQueryTable: basicQueryTable, subTypeObjs: subTypeObjs, fromLink: fromLink]

    }

    def staticRequestDetails() {
        String epdRequestId = params.epdReqObjectId
        Boolean fromLink = params.externalLink ? Boolean.TRUE : Boolean.FALSE
        EpdRequest epdRequest
        Entity entity
        List<Document> countries
        String basicQueryTable = ""
        ResourceType subTypeObj
        User user
        if (epdRequestId) {
            epdRequest = EpdRequest.findById(DomainObjectUtil.stringToObjectId(epdRequestId))
            if (epdRequest) {
                user = User.findByUsername(epdRequest.senderEmail)
                entity = Entity.findById(DomainObjectUtil.stringToObjectId(epdRequest?.entityId))
                subTypeObj = resourceTypeService.getResourceTypeObjectBySubType(epdRequest.subtypeForSendData)

                Query basicQuery = Query.findByQueryIdAndActive("basicQuery", true)
                if (basicQuery && entity) {
                    List<Dataset> basicQueryDatasets = entity.datasets?.toList()?.findAll({ basicQuery.queryId.equals(it.queryId) })
                    List<Question> allQuestionsBasicQuery = basicQuery.allQuestions

                    basicQueryDatasets?.each { Dataset d ->

                        String questionName = allQuestionsBasicQuery?.find({ it.questionId?.equalsIgnoreCase(d.questionId) })?.localizedQuestion
                        String userAnswer = ''
                        String name = optimiResourceService.getLocalizedName(optimiResourceService.getResourceWithParams(d.answerIds[0], null, null))
                        if (!name) {
                            userAnswer = d.answerIds[0].toString()
                        } else {
                            userAnswer = name
                        }
                        basicQueryTable += '<strong>' + questionName + '</strong>: ' + userAnswer + '<br/>'
                    }
                }
                countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({ it.nameEN })
                if (fromLink) {
                    epdRequest.lastViewed = new Date()
                    epdRequest.save(flush: true)
                }

            }
        }

        [epdRequest: epdRequest, countries: countries, basicQueryTable: basicQueryTable, subTypeObj: subTypeObj, user: user, fromLink: fromLink]
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def saveAndSend() {
        EpdRequest epdRequest = new EpdRequest(params)

        if (params.senderEmail) {
            epdRequest.save(flush: true)
            String requestLink = createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                    controller: "epdRequest", action: "yourEpdRequestList",
                    params: [externalLink: true, manualId: epdRequest.manualId])
            String senderInfo = "${params.senderName} (${params.senderEmail}) from ${epdRequest.sendingOrganisation}"
            activeCampaignService.createContactAndSendEmailForEpdRequest(epdRequest.recipientEmail, requestLink, senderInfo, params.entityName)
        } else {
            flashService.setErrorAlert("Params $params missing senderEmail", true)
        }
        render([output: "saved", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def deleteRequest() {
        String requestId = params.id
        boolean deleted = false
        if (requestId) {
            EpdRequest epdRequest = EpdRequest.findById(DomainObjectUtil.stringToObjectId(requestId))
            if (epdRequest) {
                epdRequest.delete(flush: true)
                deleted = true
            }
        }
        if (deleted) {
            flash.fadeSuccessAlert = "Request deleted successfully"
        } else {
            flash.fadeErrorAlert = "Error while delete request"
        }
        redirect action: "list", controller: "epdRequest"
    }

    /**
     * Receives the request from front-end to communicate with
     * French RE2020 endpoint in order to obtain id_demandeur for the user
     * (which is required to send an EPD request to Re2020 in the next step)
     * Saves idDemandeur in the user object
     * @return sends success or error response to the front-end
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def sendDemandeurRequest() {
        User user = userService.getCurrentUser()
        JSONObject response = re2020ApiService.sendDemandeurRequest(params, user)
        if (response) {
            Integer id_demandeur = response.get(FrenchConstants.ID_DEMANDEUR)
            String message = response.get("msg")
            if (id_demandeur) {
                return render([output: message, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                return render([output: "error", msg: message, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            return render([output: "error", msg: "Unable to obtain API response", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

    }

    /**
     * Receives the request from front-end to communicate with
     * French RE2020 endpoint in order to send an EPD request to Re2020
     * Should save a request number @idDemande somewhere
     * @return sends success or error response to the front-end
     */
    @Secured(["ROLE_AUTHENTICATED"])
    def sendRe2020DataRequest() {
        User user = userService.getCurrentUser()
        JSONObject response = re2020ApiService.sendDemandesRequest(params, user?.idDemandeur)

        if (response) {
            Integer idDemande = response.get(FrenchConstants.ID_DEMANDE)
            String message = response.get("msg")
            if (idDemande) {
                String reqNumberMessage = stringUtilsService.getLocalizedText("re2020.request_number")
                flash.successAlert = "${message}. ${reqNumberMessage} ${idDemande}."
                render([output: message, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            } else {
                render([output: "error", msg: message, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
            }
        } else {
            return render([output: "error", msg: "Unable to obtain API response", (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
        }

    }

}
