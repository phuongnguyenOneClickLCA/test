package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.DomainConfiguration
import com.bionova.optimi.core.domain.mongo.ChannelFeature
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.springframework.mock.web.MockHttpServletRequest
import spock.lang.Specification

class ChannelFeatureServiceSpec extends Specification implements ServiceUnitTest<ChannelFeatureService>, AutowiredTest, DataTest {

    // Manual injection of the needed beans
    Closure doWithSpring() {{ ->
        domainConfiguration DomainConfiguration
    }}

    Class<?>[] getDomainClassesToMock(){
        // Enabling GORM operations (save) for ChannelFeature domain object in the test context
        return [ChannelFeature] as Class[]
    }

    def setup() {
        // Mocking the relevant configuration
        DomainConfiguration domainConfiguration = Stub(DomainConfiguration)
        domainConfiguration.prodDomains >> ['oneclicklcaapp.com', 'www.360optimi.com']
        domainConfiguration.otherDomains >> ['test.1clicklca.com', '1clicklca.com', 'config.1clicklca.com']
        domainConfiguration.currentProdDomain >> domainConfiguration.prodDomains[0]
        service.domainConfiguration = domainConfiguration

        ConfigurationService configurationService = Stub(ConfigurationService)
        service.configurationService = configurationService

        service.channelFeatureLoginUrlsUpdated = false
    }


    def cleanup() {
    }

    void "update of channel feature login URLs happens only once"() {
        given:
        ChannelFeature defaultChannelFeature = new ChannelFeature()
        defaultChannelFeature.defaultChannel = true
        defaultChannelFeature.loginUrl = "https://www.360optimi.com/app/?channelToken=360optimi"

        List<ChannelFeature> channelFeatures = [defaultChannelFeature]
        ConfigurationService.serverName = "oneclicklcaapp.com"

        MockHttpServletRequest request = new MockHttpServletRequest()
        request.serverPort = -1

        when:
        service.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        then:
        ChannelFeatureService.channelFeatureLoginUrlsUpdated == true

        when:
        ConfigurationService.serverName = "test.1clicklca.com"
        service.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        then:
        ChannelFeatureService.channelFeatureLoginUrlsUpdated == true
        defaultChannelFeature.loginUrl == "https://www.360optimi.com/app/?channelToken=360optimi"
    }

    void "no update of channel feature login URLs for PROD system"() {
        given:
        ChannelFeature defaultChannelFeature = new ChannelFeature()
        defaultChannelFeature.defaultChannel = true
        defaultChannelFeature.loginUrl = "https://www.360optimi.com/app/?channelToken=360optimi"

        ChannelFeature anotherChannelFeature = new ChannelFeature()
        anotherChannelFeature.loginUrl = "https://oneclicklcaapp.com/app/?lang=en&channelToken=IsSusCon"

        List<ChannelFeature> channelFeatures = [defaultChannelFeature, anotherChannelFeature]
        ConfigurationService.serverName = "oneclicklcaapp.com"

        MockHttpServletRequest request = new MockHttpServletRequest()
        request.serverPort = -1

        when:
        service.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        then:
        defaultChannelFeature.loginUrl == "https://www.360optimi.com/app/?channelToken=360optimi"
        anotherChannelFeature.loginUrl == "https://oneclicklcaapp.com/app/?lang=en&channelToken=IsSusCon"
    }

    void "update all channel feature login URLs for TEST system"() {
        given:
        ChannelFeature defaultChannelFeature = new ChannelFeature()
        defaultChannelFeature.defaultChannel = true
        defaultChannelFeature.loginUrl = "https://www.360optimi.com/app/?channelToken=360optimi"

        ChannelFeature anotherChannelFeature = new ChannelFeature()
        anotherChannelFeature.loginUrl = "https://www.360optimi.com/login/ekokompassi/"

        List<ChannelFeature> channelFeatures = [defaultChannelFeature, anotherChannelFeature]
        ConfigurationService.serverName = "test.1clicklca.com"

        MockHttpServletRequest request = new MockHttpServletRequest()
        request.serverPort = -1

        when:
        service.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        then:
        defaultChannelFeature.loginUrl == "https://test.1clicklca.com/app?channelToken=oneclicklca"
        anotherChannelFeature.loginUrl == "https://test.1clicklca.com/login/ekokompassi/"
    }

    void "update of only default channel feature login URL for localhost"() {
        given:
        System.setProperty("islocalhost", "true")
        ChannelFeature defaultChannelFeature = new ChannelFeature()
        defaultChannelFeature.defaultChannel = true
        defaultChannelFeature.loginUrl = "https://www.360optimi.com/app/?channelToken=360optimi"

        ChannelFeature anotherChannelFeature = new ChannelFeature()
        anotherChannelFeature.loginUrl = "https://oneclicklcaapp.com/app/?lang=en&channelToken=IsSusCon"

        List<ChannelFeature> channelFeatures = [defaultChannelFeature, anotherChannelFeature]
        ConfigurationService.serverName = "localhost"

        MockHttpServletRequest request = new MockHttpServletRequest()
        request.serverPort = 8080

        when:
        service.updateAllChannelFeatureLoginUrls(channelFeatures, request)

        then:
        defaultChannelFeature.loginUrl == "http://localhost:8080/app?channelToken=oneclicklca"
        anotherChannelFeature.loginUrl == "https://oneclicklcaapp.com/app/?lang=en&channelToken=IsSusCon"
    }
}
