package com.bionova.optimi.security

import com.bionova.optimi.configuration.DomainConfiguration
import com.bionova.optimi.core.service.UserService
import grails.config.Config
import grails.util.Holders
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

final class HostValidationFilter extends OncePerRequestFilter {

    private final static Log log = LogFactory.getLog(HostValidationFilter.class)

    private final List<String> validDomains
    private final List<String> validIPs
    private final String rootPath
    private final String nonProdMainDomain
    private final UserService userService

    HostValidationFilter(DomainConfiguration domainConfiguration, UserService userService) {
        validDomains = domainConfiguration.getDomains()
        nonProdMainDomain = domainConfiguration.nonProdMainDomain
        this.userService = userService

        Config configuration = Holders.getConfig()
        validIPs = configuration.getProperty("oneclicklca.validIPs", List.class, [])
        rootPath = configuration.getProperty("server.servlet.context-path", String.class, "/app") + "/"
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String host = request.getHeader("Host")

        if (host && !((host in validDomains) || host.endsWith(nonProdMainDomain) ||
                // This condition was added to enable inner requests to the application by the load balancer.
                // If a new virtual private cloud is deployed, then the valid IPs need to be reviewed.
                (request.requestURL.toString().endsWith(rootPath) && validIPs.any { String ip -> host.startsWith(ip) } ))) {
            log.warn "An attempt to use invalid 'Host' header parameter was detected: " +
                    "Host = ${host}, URL = ${request.requestURL}, user = ${userService.getCurrentUser()?.username}. " +
                    "The request was rejected."
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
        } else {
            filterChain.doFilter(request, response)
        }
    }
}
