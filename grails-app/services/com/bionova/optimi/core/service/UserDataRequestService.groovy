package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.UserDataRequest
import org.apache.commons.io.FileUtils

class UserDataRequestService {

    def remove(def id) {
        Boolean removeOk = false

        if (id) {
            UserDataRequest userDataRequest = UserDataRequest.get(id)

            if (userDataRequest) {
                if (userDataRequest.filePath) {
                    FileUtils.deleteQuietly(new File(userDataRequest.filePath))
                }
                userDataRequest.delete(flush: true)
                removeOk = true
            }
        }
        return removeOk
    }

    def getDataRequests() {
        return UserDataRequest.listOrderByCreated(order: "desc")
    }
}
