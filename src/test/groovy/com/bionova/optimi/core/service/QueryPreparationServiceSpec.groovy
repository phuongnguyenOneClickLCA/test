package com.bionova.optimi.core.service

import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification

class QueryPreparationServiceSpec extends Specification implements ServiceUnitTest<QueryPreparationService>, AutowiredTest{

    void "getDefaultTransportAndServiceLife"(){
        given:
        Map<String, String> params = [
                ("calculationDefaults.transportationDefault"): "defaultTransportEurope",
                ("calculationDefaults.serviceLifeDefault"): "serviceLifeTechnical"
        ]


        when:
        Map<String, String> res1 = service.getDefaultTransportAndServiceLife(params, "LCAParametersQuery")
        Map<String, String> res2 = service.getDefaultTransportAndServiceLife(params, "FECParametersQuery")
        Map<String, String> res3 = service.getDefaultTransportAndServiceLife([("calculationDefaults.transportationDefault"): "defaultTransportEurope"], "LCAParametersQuery")
        Map<String, String> res4 = service.getDefaultTransportAndServiceLife(null, "LCAParametersQuery")

        then:
        assert res1.size() == 2
        assert res1.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) == "defaultTransportEurope"
        assert res1.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) == "serviceLifeTechnical"
        assert res2.size() == 0
        assert res2.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) == null
        assert res2.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) == null
        assert res3.size() == 2
        assert res3.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) != null
        assert res3.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) == null
        assert res4.size() == 2
        assert res4.get(com.bionova.optimi.construction.Constants.DEFAULT_TRANSPORT) == null
        assert res4.get(com.bionova.optimi.construction.Constants.DEFAULT_SERVICELIFE) == null

    }
}
