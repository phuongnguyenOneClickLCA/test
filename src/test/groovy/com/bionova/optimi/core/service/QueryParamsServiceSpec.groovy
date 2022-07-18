package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import grails.testing.services.ServiceUnitTest
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class QueryParamsServiceSpec extends Specification implements ServiceUnitTest<QueryParamsService> {

    def setup() {
    }

    def cleanup() {
    }

    void "skipTriggeringCalculationFromParams"() {
        expect:
        service.skipTriggeringCalculationFromParams(params) == result
        !params.containsKey('triggerCalculation')

        where:
        params                                                                                  | result
        new GrailsParameterMap([('triggerCalculation'): 'true'], new MockHttpServletRequest())  | false
        new GrailsParameterMap([('triggerCalculation'): 'false'], new MockHttpServletRequest()) | true
        new GrailsParameterMap([:], new MockHttpServletRequest())                               | false
    }
}
