package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.TemporaryImportData
import com.bionova.optimi.core.util.DomainObjectUtil

class TemporaryImportDataService {

    def getTemporaryImportDatasByIpAddress(String ipAddress) {
        List<TemporaryImportData> temporaryImportDatas

        if (ipAddress) {
            temporaryImportDatas = TemporaryImportData.findAllByIpAddress(ipAddress)
        }
        return temporaryImportDatas
    }

    def getTemporaryImportDataById(String id) {
        TemporaryImportData temporaryImportData

        if (id) {
            temporaryImportData = TemporaryImportData.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return temporaryImportData
    }
}
