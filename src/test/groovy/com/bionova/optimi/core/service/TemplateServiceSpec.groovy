package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.CarbonDesigner3DScenario
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class TemplateServiceSpec extends Specification implements ServiceUnitTest<TemplateService>{

    TemplateService templateService

    def setup() {
        templateService = Mock(TemplateService)
    }

    def cleanup() {
    }

    void "validation for minFloors is more than max floors in params"() {
        given:
        Integer maxFloors = 5
        Integer minFloors = 10
        CarbonDesigner3DScenario template = Mock()

        when:
        CarbonDesigner3DScenario tmp = templateService.setFloorLimits(template, maxFloors, minFloors)

        then:
        tmp == null
    }

    void "maxFloors is less than the minFloors of scenario"(){
        given:
        Integer maxFloors = 5
        CarbonDesigner3DScenario template = Mock()
        template.minFloors = 10

        when:
        CarbonDesigner3DScenario tmp = templateService.setFloorLimits(template, maxFloors, null)

        then:
        tmp == null
    }


}
