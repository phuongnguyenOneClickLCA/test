package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.NmdElement
import com.bionova.optimi.json.nmd.NmdElementElement
import com.bionova.optimi.json.nmd.NmdElementVersies
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import spock.lang.Specification

class NmdElementServiceSpec extends Specification implements ServiceUnitTest<NmdElementService>, AutowiredTest {

    private NmdResourceService nmdResourceService

    def setup() {
        nmdResourceService = Stub(NmdResourceService)
        service.nmdResourceService = nmdResourceService
    }

    def cleanup() {
    }


    void "getElementTypeFromParents"() {
        expect:
        assert service.getElementTypeFromParents([null, 'b&u']) == null
        assert service.getElementTypeFromParents([null, null]) == null
        assert service.getElementTypeFromParents(['b&u', 'gww']) == null
        assert service.getElementTypeFromParents(['b&u', 'b&u']) == 'b&u'
    }

    void "getElementTypeFromCode"() {
        given: "mock"
        nmdResourceService.getNmdElementTypes() >> ['b&u', 'gww', 'pro']

        expect:
        assert service.getElementTypeFromCode('b&u: 12345') == 'b&u'
        assert service.getElementTypeFromCode('gWw: 12345') == 'gww'
        assert service.getElementTypeFromCode('Pro: 12345') == 'pro'
        assert service.getElementTypeFromCode('pwo: 12345') == null
    }


    void "extractCodeAndElementTypeForExcel"() {
        given: "set of elements with one can be parent of the others"
        Set<NmdElement> allElements = []
        int elementId = 1

        3.times {
            allElements.add(new NmdElement(elementId: elementId, code: 'b&u: 123', elementType: 'b&u'))
            elementId++
        }
        NmdElement test = new NmdElement(parentElementIds: [1, 2, 3], code: '123')

        and: "mock"
        nmdResourceService.getNmdElementTypes() >> ['b&u', 'gww', 'pro']

        when: "all parents have type 'b&u'"
        service.extractCodeAndElementTypeForExcel(test, allElements)

        then: "type must be b&u"
        assert test.code == '123'
        assert test.elementType == 'b&u'

        when: "parents have different types"
        allElements[1].elementType = 'gww'
        // reset for next test
        test.elementType = null
        service.extractCodeAndElementTypeForExcel(test, allElements)

        then: "type is null"
        assert test.code == '123'
        assert test.elementType == null

        when: "parents have different types but the element has type in code"
        // reset for next test
        test.elementType = null
        test.code = 'pro: 345'
        service.extractCodeAndElementTypeForExcel(test, allElements)

        then: "type is pro"
        assert test.code == '345'
        assert test.elementType == 'pro'
    }

    void "extractCodeAndElementTypeForApi"() {
        given: "set of elements with one can be parent of the others"
        int elementId = 1
        int parentElementId = 2
        String parentCode = 'b&u: 12345'
        NmdElementVersies elementJson = new NmdElementVersies(Code: '123', Element_ID: elementId)
        List<NmdElementElement> elementElementVersies = []
        List<NmdElementVersies> elementVersies = []

        3.times {
            elementElementVersies.add(new NmdElementElement(KindElementID: elementId, OuderElementID: parentElementId))
            elementVersies.add(new NmdElementVersies(Element_ID: parentElementId, Code: parentCode))
            parentElementId++
        }

        and: "mock"
        nmdResourceService.getNmdElementTypes() >> ['b&u', 'gww', 'pro']

        when: "all parents have type 'b&u'"
        NmdElement test = new NmdElement()
        service.extractCodeAndElementTypeForApi(elementElementVersies, elementVersies, elementJson, test)

        then: "type must be b&u"
        assert test.code == '123'
        assert test.elementType == 'b&u'

        when: "parents have different types"
        elementVersies[1].Code = 'pro: 12345'
        NmdElement test1 = new NmdElement()
        test1.elementType = null
        service.extractCodeAndElementTypeForApi(elementElementVersies, elementVersies, elementJson, test1)

        then: "type must be null"
        assert test1.code == '123'
        assert test1.elementType == null
    }
}
