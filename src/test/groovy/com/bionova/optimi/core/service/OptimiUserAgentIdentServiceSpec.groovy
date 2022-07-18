package com.bionova.optimi.core.service

import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.util.WebUtils
import org.geeks.browserdetection.ComparisonType
import org.geeks.browserdetection.VersionHelper

import spock.lang.Specification
import javax.servlet.http.HttpServletRequest

import ua_parser.Parser
import ua_parser.Client
import ua_parser.UserAgent
import ua_parser.OS

class OptimiUserAgentIdentServiceSpec extends Specification implements ServiceUnitTest<OptimiUserAgentIdentService>, AutowiredTest {

    FlashService flashService = Mock()
    Parser parser = Mock()
    GrailsWebRequest webRequest = Mock()
    HttpServletRequest request = Mock()

    def setup() {
        service.flashService = flashService
        service.parser = parser
        GroovySpy(WebUtils, global: true)
    }

    def cleanup() {
    }

    void "check retrieving of browser version"() {
        given:
        String userAgentValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
        UserAgent userAgent = new UserAgent("Chrome", "95", "0", "4638")
        Client client = new Client(userAgent, null, null)

        when:
        String result = service.getBrowserVersion()

        then:
        1 * WebUtils.retrieveGrailsWebRequest() >> webRequest
        1 * webRequest.getNativeRequest(HttpServletRequest.class) >> request
        1 * request.getHeader("user-agent") >> userAgentValue
        1 * parser.parse(userAgentValue) >> client
        0 * _
        result == "95.0.4638"
    }

    void "check retrieving of browser name"() {
        given:
        String userAgentValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
        UserAgent userAgent = new UserAgent("Chrome", "95", "0", "4638")
        Client client = new Client(userAgent, null, null)

        when:
        String result = service.getBrowser()

        then:
        1 * WebUtils.retrieveGrailsWebRequest() >> webRequest
        1 * webRequest.getNativeRequest(HttpServletRequest.class) >> request
        1 * request.getHeader("user-agent") >> userAgentValue
        1 * parser.parse(userAgentValue) >> client
        0 * _
        result == "Chrome"
    }

    void "check retrieving of operating system"() {
        given:
        String userAgentValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
        OS os = new OS("Windows", "10", null, null, null)
        Client client = new Client(null, os, null)

        when:
        String result = service.getOperatingSystem()

        then:
        1 * WebUtils.retrieveGrailsWebRequest() >> webRequest
        1 * webRequest.getNativeRequest(HttpServletRequest.class) >> request
        1 * request.getHeader("user-agent") >> userAgentValue
        1 * parser.parse(userAgentValue) >> client
        0 * _
        result == "Windows 10"
    }

    void "check browser comparison"() {
        given:
        String userAgentValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36"
        UserAgent userAgent = new UserAgent("Chrome", "95", "0", "4638")
        Client client = new Client(userAgent, null, null)
        GroovySpy(VersionHelper, global: true)

        when:
        boolean result = service.isBrowser("Chrome", ComparisonType.EQUAL, "95")

        then:
        1 * WebUtils.retrieveGrailsWebRequest() >> webRequest
        1 * webRequest.getNativeRequest(HttpServletRequest.class) >> request
        1 * request.getHeader("user-agent") >> userAgentValue
        1 * parser.parse(userAgentValue) >> client
        1 * VersionHelper.equals("95.0.4638", "95") >> false
        0 * _
        result == false
    }
}
