package com.bionova.optimi.construction.interceptor

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class ChartImageRemovalInterceptorSpec extends Specification implements InterceptorUnitTest<ChartImageRemovalInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test chartImageRemoval interceptor matching"() {
        when: "A request matches the interceptor"
        withRequest(controller: "operatingPeriod", action: "results")

        then: "The interceptor does match"
        interceptor.doesMatch()
    }
}
