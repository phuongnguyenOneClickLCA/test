package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.construction.Constants
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class QuestionSpec extends Specification implements DomainUnitTest<Question> {

    void "test GetDefaultValueFromResourceSubtype method with existing entity"() {
        given:
        domain.defaultValueFromResourceSubtype = "defaultTransportBoverketLeg2"
        Entity entity = Mock(Entity.class)
        Resource resource = Mock(Resource.class)
        ResourceType resourceType = Mock(ResourceType.class)

        // Test to get value from resource
        when:
        def result1 = domain.getDefaultValueFromResourceSubtype(entity, resource)

        then:
        2 * entity.getDefaults() >> new HashMap<String, String>() {{
            put("test", "test")
        }}
        1 * resource.getDefaultTransportBoverketLeg2() >> 100.0

        and:
        result1 == 100.0

        // Test to get RICS value which is a type String in ResourceType class
        when:
        def result2 = domain.getDefaultValueFromResourceSubtype(entity, resource)

        then:
        2 * entity.getDefaults() >> new HashMap<String, String>() {{
            put(Constants.DEFAULT_TRANSPORT, Constants.DEFAULT_TRANSPORT_RICS)
        }}
        1 * resource.getSubType() >> resourceType
        1 * resourceType.getTransportResourceIdRICS() >> "50.0"

        and:
        result2 == "50.0"

        // Test to get value from subtype
        when:
        def result3 = domain.getDefaultValueFromResourceSubtype(entity, resource)

        then:
        2 * entity.getDefaults() >> new HashMap<String, String>() {{
            put("test", "test")
        }}
        1 * resource.getSubType() >> resourceType
        1 * resource.getDefaultTransportBoverketLeg2()
        1 * resourceType.getDefaultTransportBoverketLeg2() >> 50.0

        and:
        result3 == 50.0
    }

    void "test GetDefaultValueFromResourceSubtype method without existing entity"() {
        given:
        domain.defaultValueFromResourceSubtype = "defaultTransportBoverketLeg2"
        Resource resource = Mock(Resource.class)
        ResourceType resourceType = Mock(ResourceType.class)

        // Test to get value from resource
        when:
        def result1 = domain.getDefaultValueFromResourceSubtype(resource)

        then:
        1 * resource.getDefaultTransportBoverketLeg2() >> 1.0
        0 * _

        and:
        result1 == 1.0

        // test to get value from subtype
        when:
        def result2 = domain.getDefaultValueFromResourceSubtype(resource)

        then:
        1 * resource.getDefaultTransportBoverketLeg2()
        1 * resource.getSubType() >> resourceType
        1 * resourceType.getDefaultTransportBoverketLeg2() >> 2.0
        0 * _

        and:
        result2 == 2.0
    }
}
