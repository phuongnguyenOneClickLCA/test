package com.bionova.optimi.core.domain.mongo

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ResourceSpec extends Specification implements DomainUnitTest<Resource> {
    void "check getting of impact by props hierarchy and stages"() {
        when:
        domain.impactGWP100_kgCO2e_fossil = null
        domain.impactGWP100_kgCO2e = 42.0
        domain.traciGWP_kgCO2e = 36.0
        domain.impactGWP_direct_kgCO2e = 24.0
        domain.impacts = [
                'B4': [
                        impactGWP100_kgCO2e_fossil: 5,
                        impactGWP100_kgCO2e: 10,
                        traciGWP_kgCO2e: 15,
                        impactGWP_direct_kgCO2e: 20
                ],
                'A1-A3': [
                        impactGWP100_kgCO2e_fossil: 50,
                        impactGWP100_kgCO2e: 55,
                        traciGWP_kgCO2e: 60,
                        impactGWP_direct_kgCO2e: 65
                ]
        ]

        then:
        def result = domain.getImpactByPropsHierarchyAndStages(['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList,
                                                        ['A4', 'B4', 'A1-A3'])
        assert result == 10.0, "Result impact should be '10.0' as 'impactGWP100_kgCO2e_fossil' property is null."
    }

    void "check getting of impact by props hierarchy and stages when impacts are not relevant"() {
        when:
        domain.impactGWP100_kgCO2e_fossil = 48.0
        domain.impactGWP100_kgCO2e = 42.0
        domain.traciGWP_kgCO2e = 36.0
        domain.impactGWP_direct_kgCO2e = 24.0
        domain.impacts = [
                'B4': [
                        impactGWP100_kgCO2e_fossil: 5,
                        impactGWP100_kgCO2e: 10,
                        traciGWP_kgCO2e: 15,
                        impactGWP_direct_kgCO2e: 20
                ],
                'A1-A3': [
                        impactGWP100_kgCO2e_fossil: 50,
                        impactGWP100_kgCO2e: 55,
                        traciGWP_kgCO2e: 60,
                        impactGWP_direct_kgCO2e: 65
                ]
        ]

        then:
        def result = domain.getImpactByPropsHierarchyAndStages(['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList,
                ['A4', 'C4'], 0)
        assert result == 48.0, "Result impact should be '48.0' as impacts are not relevant and property 'impactGWP100_kgCO2e_fossil' is taken."
    }

    void "check getting of impact by props hierarchy and stages when stages are null"() {
        when:
        domain.impactGWP100_kgCO2e_fossil = null
        domain.impactGWP100_kgCO2e = null
        domain.traciGWP_kgCO2e = 36.0
        domain.impactGWP_direct_kgCO2e = null
        domain.impacts = [
                'B4': [
                        impactGWP100_kgCO2e_fossil: 5,
                        impactGWP100_kgCO2e: 10,
                        traciGWP_kgCO2e: 15,
                        impactGWP_direct_kgCO2e: 20
                ],
                'A1-A3': [
                        impactGWP100_kgCO2e_fossil: 50,
                        impactGWP100_kgCO2e: 55,
                        traciGWP_kgCO2e: 60,
                        impactGWP_direct_kgCO2e: 65
                ]
        ]

        then:
        def result = domain.getImpactByPropsHierarchyAndStages(['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList)
        assert result == 36.0, "Result impact should be '36.0' as stages are null and properties 'impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e' are null."
    }

    void "check getting of impact by props hierarchy and stages when target property is not in the allowed impact categories list"() {
        when:
        domain.impactGWP100_kgCO2e_fossil = null
        domain.impactGWP100_kgCO2e = null
        domain.traciGWP_kgCO2e = null
        domain.impactGWP_direct_kgCO2e = 24.0
        domain.impacts = [
                'B4': [
                        impactGWP100_kgCO2e_fossil: 5,
                        impactGWP100_kgCO2e: 10,
                        traciGWP_kgCO2e: 15,
                        impactGWP_direct_kgCO2e: 20
                ],
                'A1-A3': [
                        impactGWP100_kgCO2e_fossil: 50,
                        impactGWP100_kgCO2e: 55,
                        traciGWP_kgCO2e: 60,
                        impactGWP_direct_kgCO2e: 65
                ]
        ]

        then:
        def result = domain.getImpactByPropsHierarchyAndStages(['impactGWP100_kgCO2e_fossil', 'impactGWP100_kgCO2e', 'traciGWP_kgCO2e', 'impactGWP_direct_kgCO2e'] as LinkedList,
                                                               ['A4', 'B4', 'A1-A3'])
        assert result == 24.0, "Result impact should be '24.0' as target property 'impactGWP_direct_kgCO2e' is not in the allowed impact categories list."
    }
}
