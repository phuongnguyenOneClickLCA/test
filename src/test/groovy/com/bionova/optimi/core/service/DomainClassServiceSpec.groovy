package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Account
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.grails.datastore.mapping.model.PersistentEntity
import spock.lang.Specification

class DomainClassServiceSpec extends Specification implements ServiceUnitTest<DomainClassService>, AutowiredTest{

    def setup() {
    }

    def cleanup() {
    }

//    TODO: what does it test? Nothing was set up properly.
//    void "getPersistentEntityForDomainClass returns right persistentEntity"() {
//        expect:
//        System.out.println("name: ${Account.class.name}")
//        PersistentEntity persistentEntity = service.getPersistentEntityForDomainClass(Account.class)
//        System.out.println("PersistentEntity: ${persistentEntity}")
//        persistentEntity.getName() == "Account"
//    }
}
