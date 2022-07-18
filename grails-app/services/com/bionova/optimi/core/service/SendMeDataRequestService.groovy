package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.SendMeDataRequest
import com.bionova.optimi.core.domain.mongo.User
import grails.plugin.springsecurity.annotation.Secured

import javax.servlet.http.HttpSession

/**
 * @author Pasi-Markus Mäkelä
 */
class SendMeDataRequestService {

    def getAllSendMeDataRequests() {
        return SendMeDataRequest.list()
    }

    def createSendMeDataRequest(String sentByUserId, String receivedByUserId, String type) {
        if (sentByUserId && receivedByUserId && type) {
            SendMeDataRequest sendMeDataRequest = new SendMeDataRequest()
            sendMeDataRequest.sentByUserId = sentByUserId
            sendMeDataRequest.receivedByUserIds = [receivedByUserId]
            sendMeDataRequest.type = type

            if (sendMeDataRequest.validate()) {
                sendMeDataRequest = sendMeDataRequest.save(flush: true)
            }
            return sendMeDataRequest
        } else {
            return null
        }
    }
}
