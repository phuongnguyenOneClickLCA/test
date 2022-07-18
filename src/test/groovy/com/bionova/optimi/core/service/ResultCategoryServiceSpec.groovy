package com.bionova.optimi.core.service


import com.bionova.optimi.core.domain.mongo.ResultCategory
import grails.testing.services.ServiceUnitTest
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification
import spock.lang.Unroll

class ResultCategoryServiceSpec extends Specification implements ServiceUnitTest<ResultCategoryService> {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    void "test translation"() {
        expect:
        LocaleContextHolder.setLocale(Locale.ENGLISH)
        translation == service.getLocalizedShortName(
                new ResultCategory(name: ["EN": "Bokoblin lives in the forest"], shortName: shortName), trimCounter)
        where:
        translation                    | shortName          | trimCounter
        "Bokoblin"                     | ["EN": "Bokoblin"] | 0
        "Bokoblin lives ..."           | [:]                | 15
        "Bokoblin lives in the forest" | [:]                | 100
    }
}
