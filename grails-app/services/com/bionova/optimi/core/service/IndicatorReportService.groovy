package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.IndicatorReportItem
import grails.gorm.transactions.Transactional

@Transactional
class IndicatorReportService {

    LicenseService licenseService

    List<IndicatorReportItem> getReportItemsAsReportItemObjects(Entity parentEntity, Boolean skipLicenseKeyCheck = false, List reportItems) {
        List<IndicatorReportItem> reportItemList = []
        List<String> checkLicensedReportItems = []

        if (reportItems) {
            IndicatorReportItem reportItem

            reportItems.each {
                reportItem = (IndicatorReportItem) it

                if (reportItem && reportItem.licenseKey && !checkLicensedReportItems.contains(reportItem.licenseKey)) {
                    checkLicensedReportItems.add(reportItem.licenseKey)
                }
                reportItemList.add(reportItem)
            }
        }

        if (!checkLicensedReportItems.isEmpty() && !skipLicenseKeyCheck) {
            Map<String, Boolean> featuresAllowed = licenseService.featuresAllowedByCurrentUserOrProject(parentEntity, checkLicensedReportItems, null, true, true)
            reportItemList = reportItemList.findAll({!it.licenseKey||featuresAllowed.get(it.licenseKey)})
        }
        return reportItemList
    }
}
