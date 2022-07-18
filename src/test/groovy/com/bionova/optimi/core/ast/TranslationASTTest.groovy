package com.bionova.optimi.core.ast

import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DBuildingShape
import com.bionova.optimi.core.domain.mongo.Notification
import org.springframework.context.i18n.LocaleContextHolder
import spock.lang.Specification

class TranslationASTTest extends Specification {
    void "Test transients"() {
        expect:"localisation methods are not persistable"
        Notification.transients.contains("localizedHeading")
        Notification.transients.contains("localizedText")
    }

    void "Test message default"() {
        when:"The message translation is invoked"
        Notification notification = new Notification()
        notification.heading = ["EN": "english"]

        then:"english is returned"
        "english".equals(notification.getLocalizedHeading())
    }

    void "Test message"() {
        when:"The message translation is invoked"
        Notification notification = new Notification()
        notification.text = ["EN": "english", "FR": "french"]
        LocaleContextHolder.setLocale(Locale.FRANCE)

        then:"french is returned"
        "french".equals(notification.getLocalizedText())
    }

    void "Test empty message"() {
        when:"The message translation is invoked"
        Notification notification = new Notification()

        then:"empty is returned"
        "".equals(notification.getLocalizedHeading())
    }

    void "Test alias"() {
        when:"Localisation property has alias"
        CarbonDesigner3DBuildingShape shape = new CarbonDesigner3DBuildingShape()
        shape.buildingShapeName = ["JA": "onitsuka"]
        LocaleContextHolder.setLocale(Locale.JAPAN)

        then:"onitsuka is returned"
        "onitsuka".equals(shape.getLocalizedName())
    }
}
