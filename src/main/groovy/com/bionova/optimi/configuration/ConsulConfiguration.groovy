package com.bionova.optimi.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.cloud.consul")
class ConsulConfiguration {
    Boolean enabled
    String name
    String host
    Integer port
}
