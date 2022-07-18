package com.bionova.optimi.core.service

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class WordDocumentServiceSpec extends Specification implements ServiceUnitTest<WordDocumentService> {

    void "unique id is returned"(){

        when: "same indicator name and designId"
        String id1 = service.generateUniqueIDForEPD("5f5f6f914987a74dbd6b1749", "BETiE")
        Thread.sleep(1000);
        String id2 = service.generateUniqueIDForEPD("5f5f6f914987a74dbd6b1749", "BETiE")

        then: "unique ids generated for the same indicator name and entity id"
        id1 != id2

    }
}
