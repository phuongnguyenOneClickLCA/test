package com.bionova.optimi.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Represents the configuration for domain names of all servers the application can run on.
 */
@Configuration
@ConfigurationProperties(prefix = "oneclicklca.domain")
class DomainConfiguration {
    /**
     * For legacy support we have to keep former domains of the production server.
     * The first one in the list is the actual (current) one.
     */
    List<String> prodDomains = []

    /**
     * The domain names of other supported servers the application can run on (TEST, DEV, CONFIG, etc).
     */
    List<String> otherDomains = []

    /**
     * Main domain name (without prefix) for the non-production installations (config.1clicklca.com, test.1clicklca.com, features.1clicklca.com, etc.).
     */
    String nonProdMainDomain

    /**
     * @return the domain of the actual (current) production server
     */
    String getCurrentProdDomain()  {
        return prodDomains[0] ?: ""
    }

    /**
     * @return all possible domains the application can run on (DEV, CONFIG, TEST, PROD, etc.).
     */
    List<String> getDomains() {
        return (prodDomains + otherDomains).unique()
    }
}
