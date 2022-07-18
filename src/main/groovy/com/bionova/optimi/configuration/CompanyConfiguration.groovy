package com.bionova.optimi.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oneclicklca.company")
class CompanyConfiguration {
    String url
    String name
    String domainName
}
