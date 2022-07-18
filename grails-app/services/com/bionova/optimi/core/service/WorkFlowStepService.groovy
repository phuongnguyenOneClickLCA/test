package com.bionova.optimi.core.service

import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional

@Transactional
class WorkFlowStepService {

    LocalizedLinkService localizedLinkService

    String getLocName(Map <String, String> name) {
        if (name) {
            String localName = name?.get(DomainObjectUtil.mapKeyLanguage)
            localName = localName ?: name.get("EN")

            return localName ?: "LOCALIZATION MISSING"
        } else {
            return ""
        }
    }

    String getLocHelp(Map<String, String> help) {
        String language = DomainObjectUtil.mapKeyLanguage
        String localName = help?.get(language) ?: help?.get("EN")
        localName = localizedLinkService.getTransformStringIdsToLinks(localName)
        return localName
    }
}
