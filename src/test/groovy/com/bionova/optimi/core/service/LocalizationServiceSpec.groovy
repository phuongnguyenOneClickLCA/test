package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DBuildingType
import grails.testing.services.ServiceUnitTest
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification
import spock.lang.Unroll

class LocalizationServiceSpec extends Specification implements ServiceUnitTest<LocalizationService> {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    void "test translation"() {
        expect:
        LocaleContextHolder.setLocale(locale)
        translation == service.getLocalizedProperty(
                new CarbonDesigner3DBuildingType(nameEN: "Bokoblin", nameFI: "Kakariko"),
                "name", "name", "errMsg")
        where:
        translation | locale
        "Bokoblin"  | Locale.ENGLISH
        "Bokoblin"  | Locale.JAPAN
        "Kakariko"  | new Locale("FI")
    }

    @Unroll
    void "test fallback message"() {
        expect:
        LocaleContextHolder.setLocale(locale)
        translation == service.getLocalizedProperty(
                new CarbonDesigner3DBuildingType(nameFI: "Kakariko"),
                "name", "name", "errMsg")
        where:
        translation | locale
        "errMsg"    | Locale.JAPAN
        "Kakariko"  | new Locale("FI")
    }
}
