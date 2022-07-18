package com.bionova.optimi.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.cloud.consul.discovery")
class ConsulDiscoveryConfiguration {
    String healthCheckPath
    String healthCheckCriticalTimeout
    String healthCheckInterval
    Boolean preferIpAddress
}
