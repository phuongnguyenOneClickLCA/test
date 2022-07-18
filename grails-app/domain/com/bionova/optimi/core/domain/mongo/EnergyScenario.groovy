package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class EnergyScenario implements Serializable, Validateable {

    String energyScenarioId
    @Translatable
    Map<String, String> name

    static transients = []

    private String localize(Map values) {

        if (values) {
            String lang = DomainObjectUtil.getMapKeyLanguage()
            String localized = values[lang] ?: (values["EN"] ?: "NAME LOCALIZATION MISSING")
            return localized
        } else {
            return ""
        }
    }

}
