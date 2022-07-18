package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.APICalculationResponse
import com.bionova.optimi.core.util.DomainObjectUtil

class APICalculationResponseService {

    def getAPICalculationResponseByFileToken(String fileToken) {
        APICalculationResponse apiCalculationResponse

        if (fileToken) {
            apiCalculationResponse = APICalculationResponse.findByFileToken(fileToken)
        }
        return apiCalculationResponse
    }

    def getAPICalculationResponsesByIp(String ipAddress) {
        List<APICalculationResponse> apiCalculationResponses

        if (ipAddress) {
            apiCalculationResponses = APICalculationResponse.findAllByIpAddress(ipAddress)
        }
        return apiCalculationResponses
    }

    def getAPICalculationResponseById(String id) {
        APICalculationResponse apiCalculationResponse

        if (id) {
            apiCalculationResponse = APICalculationResponse.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return apiCalculationResponse
    }

    def deleteAPICalculationResponse(id) {
        Boolean deleteOk = Boolean.FALSE

        if (id) {
            APICalculationResponse apiCalculationResponse = APICalculationResponse.get(id)

            if (apiCalculationResponse) {
                apiCalculationResponse.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }
}
