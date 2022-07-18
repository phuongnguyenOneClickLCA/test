package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Account
import com.mongodb.client.MongoCollection
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import grails.web.servlet.mvc.GrailsParameterMap
import org.bson.types.ObjectId
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

/**
 * Test class for  AccountService.groovy
 **/
class AccountServiceSpec extends Specification implements ServiceUnitTest<AccountService>, AutowiredTest {


    def setup() {
        GroovySpy(Account, global: true)
    }

    def cleanup() {
    }

    /**
     * parsing number to double test
     */
    void "parseNumberInParamsForAccount"() {
        given:
        MockHttpServletRequest request = new MockHttpServletRequest()
        GrailsParameterMap params = new GrailsParameterMap(request)
        Double num = 124.0
        params.put("carbonCost", 124)

        expect:
        //test case1: parsing valid number
        service.parseNumberInParamsForAccount(params)
        assert params.get("carbonCost") == num

        //test case2: parsing invalid number
        when:
        params.put("carbonCost", "number")
        service.parseNumberInParamsForAccount(params)
        then:
        noExceptionThrown()

        //test case3: parsing null
        when:
        params.put("carbonCost", null)
        service.parseNumberInParamsForAccount(params)
        then:
        noExceptionThrown()
    }

    /**
     * get account list test
     */
    void "getAccounts"() {
        given:
        List list = Mock(List)

        when:
        service.getAccounts()
        then:
        1 * Account.list() >> list

    }

    /**
     * get account based on login key
     */
    void "get account with login key"() {
        given:
        Account account = Mock(Account)

        when:
        service.getAccounts("test")
        then:
        1 * Account.findByLoginKey(_ as String) >> account

        when: "passing null as login key"
        service.getAccounts(null)
        then:
        0 * Account.findByLoginKey(_ as String) >> account

    }

    /**
     * get account list based on Object Ids
     */
    void "get accounts by Ids"() {
        given:
        MongoCollection mongoCollection = Mock(MongoCollection)
        List<String> idList=Arrays.asList("58de3add5d96ff2a7b28a2b9","58de3add5d96ff2a7b28a2b7","58de3add5d96ff2a7b28a2b8")
        List<String> idListInvalid=Arrays.asList("test1","test2","test3")

        when:
        service.getAccountsByIds(idList)
        then:
        1 * Account.collection >> mongoCollection

        when: "passing null as id list"
        service.getAccountsByIds(null)
        then:
        0 * Account.collection >> mongoCollection

        when: "passing invalid id list"
        service.getAccountsByIds(idListInvalid)
        then:
        1 * Account.collection >> mongoCollection
        thrown(IllegalArgumentException)

    }

    /**
     * get account based on id
     */
    void "get account with id"() {
        given:
        Account account = Mock(Account)

        when:
        service.getAccount("58de3add5d96ff2a7b28a2b9")
        then:
        1 * Account.get(_ as ObjectId) >> account

        when: "passing null as id"
        service.getAccount(null)
        then:
        0 * Account.get(_ as ObjectId) >> account

        when: "passing invalid id"
        service.getAccount("testId")
        then:
        1 * Account.get(null) >> account

    }

}
