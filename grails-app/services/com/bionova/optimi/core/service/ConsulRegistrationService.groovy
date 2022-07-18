package com.bionova.optimi.core.service

import com.bionova.optimi.configuration.ConsulConfiguration
import com.bionova.optimi.configuration.ConsulDiscoveryConfiguration
import com.bionova.optimi.monitor.ConsulDiscoveryProperties
import com.bionova.optimi.monitor.WebConnector
import com.bionova.optimi.util.monitor.ConsulTag
import com.bionova.optimi.util.monitor.ServiceId
import com.ecwid.consul.UrlParameters
import com.ecwid.consul.transport.RawResponse
import com.ecwid.consul.transport.TransportException
import com.ecwid.consul.v1.ConsulClient
import com.ecwid.consul.v1.ConsulRawClient
import com.ecwid.consul.v1.OperationException
import com.ecwid.consul.v1.QueryParams
import com.ecwid.consul.v1.Response
import com.ecwid.consul.v1.agent.model.NewService
import com.ecwid.consul.v1.catalog.model.CatalogService
import grails.util.Holders
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StringUtils

import javax.management.MBeanServer
import javax.management.ObjectName
import javax.management.Query
import javax.management.QueryExp
import java.lang.management.ManagementFactory
import java.text.SimpleDateFormat

class ConsulRegistrationService implements InitializingBean, DisposableBean, GroovyObject {

    @Autowired
    ConsulConfiguration consulConfiguration
    @Autowired
    ConsulDiscoveryConfiguration consulDiscoveryConfiguration

    def grailsApplication

    private static final String CONSUL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    private static final String LOCALHOST = 'localhost'
    private static final String PLUGIN_NAME = 'consul'
    private static final String METADATA_APP_NAME = 'info.app.name'
    private static final String METADATA_APP_VERSION = 'info.app.version'

    private static ConsulRawClient consulRawClient
    private static ConsulClient consulClient
    private static TimerTask task = null
    private static Timer timer = null
    private ConsulDiscoveryProperties consulDiscoveryProperties
    private ServiceId applicationServiceId
    private String applicationVersion
    private String contextPath
    private Integer servicePort
    private String serviceId

    @Override
    void afterPropertiesSet() throws Exception {

        if (isEnabled()) {
            log.info("INITIALIZE ConsulRegistrationService")
            try {
                if (grailsApplication == null) {
                    grailsApplication = Holders.grailsApplication
                }

                if (applicationContext == null) {
                    applicationContext = Holders.grailsApplication.mainContext
                }

                String appName = grailsApplication?.metadata[METADATA_APP_NAME]?.toString()
                if (appName.equalsIgnoreCase(PLUGIN_NAME)) {
                    appName = ServiceId.UNDEFINED.getName()
                }
                String appVersion = grailsApplication?.metadata[METADATA_APP_VERSION]?.toString()
                String contextPath = applicationContext?.getServletContext()?.getContextPath()?.toString()

                log.info("appName     = '${appName}'")
                log.info("appVersion  = '${appVersion}'")
                log.info("contextPath = '${contextPath}'")

                WebConnector firstAvailableConnector = discoverWebConnector()
                this.applicationServiceId = ServiceId.fromName(appName)
                this.contextPath = contextPath
                this.applicationVersion = appVersion
                this.servicePort = firstAvailableConnector.getPort()
                this.serviceId = "${applicationServiceId.getName()}-${servicePort}"

                consulDiscoveryProperties = new ConsulDiscoveryProperties()
                consulDiscoveryProperties.setHealthCheckCriticalTimeout(consulDiscoveryConfiguration.healthCheckCriticalTimeout)
                consulDiscoveryProperties.setHealthCheckInterval(consulDiscoveryConfiguration.healthCheckInterval)
                consulDiscoveryProperties.setHealthCheckPath(consulDiscoveryConfiguration.healthCheckPath)
                consulDiscoveryProperties.setPreferIpAddress(consulDiscoveryConfiguration.preferIpAddress)
                consulDiscoveryProperties.setScheme(firstAvailableConnector.getScheme())
                consulDiscoveryProperties.setTags(getTags())

                log.info("Initializing CONSUL with config: ${consulDiscoveryProperties}")

                consulClient()
            } catch (Exception e) {
                consulConfiguration.enabled = Boolean.FALSE
                log.error("ConsulRegistrationService INITIALIZION FAILED", e)
            }

            task = new TimerTask() {
                @Override
                void run() {
                    if (isEnabled()) {
                        if (log.isTraceEnabled()) log.trace("Check CONSUL registration...")
                        selfCheck()
                        if (log.isTraceEnabled()) log.trace("CONSUL registration check complete")
                    }
                }
            };

            timer = new Timer()
            timer.schedule(task, 1000, 5000)

            log.info("ConsulRegistrationService INITIALIZED")
        } else {
            log.info("Consul plugin DISABLED")
        }
    }

    @Override
    void destroy() throws Exception {
        try {
            if (task != null) {
                task.cancel()
                task = null
            }
            if (timer != null) {
                timer.cancel()
                timer = null
            }
        } catch (Exception e) {
            log.warn("Unable to stop self-check timer: ${e.getMessage()}", e)
        }
    }

    boolean isEnabled() {
        return Boolean.TRUE.equals(consulConfiguration.enabled)
    }

    ConsulRawClient consulRawClient() {
        if (consulRawClient == null) {
            String consulHost = consulConfiguration.host
            Integer consulPort = consulConfiguration.port
            log.info("Connect to CONSUL [${consulHost}:${consulPort}]")
            consulRawClient = new ConsulRawClient(consulHost, consulPort)
        }
        return consulRawClient
    }

    ConsulClient consulClient() {
        if (consulClient == null && consulRawClient() != null) {
            consulClient = new ConsulClient(consulRawClient)
        }
        return consulClient
    }

    boolean isRegistered() {
        if (!isEnabled()) {
            return false
        }

        try {
            return findCatalogService(
                    applicationServiceId,
                    getApplicationHostAddress(),
                    servicePort) != null
        } catch (Exception e) {
            log.error("Couldn't query catalog service for ${applicationServiceId.getName()}", e)
            // mark actuator down
            return false
        }
    }

    synchronized void selfCheck() {
        if (!isEnabled()) {
            return
        }

        if (log.isTraceEnabled()) log.trace("CONSUL selfCheck...")
        String address = getApplicationHostAddress()

        try {
            List<String> currentTags = consulDiscoveryProperties.getTags()
            CatalogService catalogService = findCatalogService(applicationServiceId, address, servicePort)

            if (catalogService == null) {
                registeringService(address)
            } else if (consulHasDeadInstance(address, currentTags, catalogService)) {
                deregisteringService(catalogService.getServiceId())
                registeringService(address)
            }
        } catch (TransportException te) {
            log.error("Unable to connect to CONSUL: ${te.getMessage()}")
        } catch (Exception e) {
            log.error("Couldn't query catalog service for ${applicationServiceId.getName()}", e)
        }
    }

    /**
     * If in Consul we have already registered service for same 'address' (usually 'localhost'),
     * but with different set of 'tags' ('startedAt') - need to re-register it.
     */
    private boolean consulHasDeadInstance(String address, List<String> currentTags, CatalogService remoteService) {
        return (getAllHostAddresses(address).contains(remoteService.getAddress())
                && !currentTags.equals(remoteService.getServiceTags()))
    }

    private synchronized void registeringService(String address) {
        log.info("Registering '" + serviceId + "' at '${address}' in CONSUL...")
        // register new service
        NewService newService = new NewService()
        newService.setId(serviceId)
        newService.setName(applicationServiceId.getName())
        newService.setAddress(address)
        newService.setPort(servicePort)

        newService.setTags(consulDiscoveryProperties.getTags())

        // Add the health check
        NewService.Check serviceCheck = new NewService.Check()
        serviceCheck.setHttp(getHealthCheckUrl(address, servicePort))
        serviceCheck.setInterval(consulDiscoveryProperties.getHealthCheckInterval())
        if (StringUtils.hasText(consulDiscoveryProperties.getHealthCheckCriticalTimeout())) {
            serviceCheck.setDeregisterCriticalServiceAfter(consulDiscoveryProperties.getHealthCheckCriticalTimeout())
        }

        // register the health check
        newService.setCheck(serviceCheck)

        // register the service
        consulClient()?.agentServiceRegister(newService)
        log.info("'" + serviceId + "' completely registered in CONSUL.")
    }

    /**
     * Consul API was updated since '1.2.5' and currently 'deregisteringService' processed with
     * 'PUT' request (instead of 'GET')
     */
    private synchronized void deregisteringService(String serviceId) {
        log.info("Deregistering '" + serviceId + "' from CONSUL...")
        RawResponse rawResponse = consulRawClient.makePutRequest(
                "/v1/agent/service/deregister/" + serviceId,
                "",
                new UrlParameters[0])
        if (rawResponse.getStatusCode() == 200) {
            log.info("'" + serviceId + "' deregistered from CONSUL...")
        } else {
            throw new OperationException(rawResponse)
        }
    }

    private List<String> getTags() {
        List<String> tags = new ArrayList<>()

        tags.add(ConsulTag.VERSION.asTag(applicationVersion))

        Date startedAt = new Date(ManagementFactory.getRuntimeMXBean().getStartTime())
        SimpleDateFormat dateFormat = new SimpleDateFormat(CONSUL_DATE_TIME_PATTERN)

        tags.add(ConsulTag.STARTED_AT.asTag(dateFormat.format(startedAt)))

        final boolean[] contextPathAndSecureTagsAdded = [false, false] as boolean[]

        for (String tag : consulDiscoveryProperties.getTags()) {
            if (tag.startsWith(ConsulTag.CONTEXT_PATH.getName())) {
                tags.add(tag)
                contextPathAndSecureTagsAdded[0] = true
            } else if (tag.startsWith(ConsulTag.SECURE.getName())) {
                tags.add(tag)
                contextPathAndSecureTagsAdded[1] = true
            } else if (tag.startsWith(ConsulTag.VERSION.getName()) || tag.startsWith(STARTED_AT.getName())) {
                // impossible to override 'version' and 'startedAt' tags
            } else {
                tags.add(tag)
            }
        }
        if (!contextPathAndSecureTagsAdded[0]) {
            tags.add(ConsulTag.CONTEXT_PATH.asTag(contextPath))
        }
        if (!contextPathAndSecureTagsAdded[1]) {
            tags.add(ConsulTag.SECURE.asTag(Boolean.FALSE.toString()))
        }
        return tags
    }

    private String getHealthCheckUrl(String address, Integer servicePort) {
        StringBuilder sb = new StringBuilder()
        sb.append(consulDiscoveryProperties.getScheme())
                .append("://")
                .append(address)
                .append(':')
                .append(servicePort)
                .append(consulDiscoveryProperties.getHealthCheckPath())
        return sb.toString()
    }

    private String getApplicationHostAddress() {
        String applicationHostAddress = LOCALHOST
        try {
            InetAddress inetAddress = InetAddress.getLocalHost()
            applicationHostAddress = consulDiscoveryProperties.isPreferIpAddress() ?
                    inetAddress.getHostAddress() : inetAddress.getHostName()
        } catch (RuntimeException | UnknownHostException e) {
            log.warn("${e.getMessage()}", e)
        }
        return applicationHostAddress
    }

    private Set<String> getAllHostAddresses(String hostName) {
        Set<String> allHostAddresses = new HashSet<>()
        try {
            for (InetAddress address : InetAddress.getAllByName(hostName)) {
                allHostAddresses.add(address.getHostAddress())
            }
        } catch (RuntimeException | UnknownHostException e) {
            log.warn("${e.getMessage()}", e)
            allHostAddresses = new HashSet<>()
        }
        return allHostAddresses
    }

    private CatalogService findCatalogService(ServiceId applicationServiceId,
                                              String serviceAddress,
                                              Integer servicePort) {
        Response<List<CatalogService>> services = consulClient()?.getCatalogService(
                applicationServiceId.getName(),
                QueryParams.DEFAULT
        )

        if (services) {
            for (CatalogService service : services.getValue()) {
                if (service.getServiceAddress().equals(serviceAddress) && service.getServicePort().equals(servicePort)) {
                    return service
                }
            }
        }
        return null
    }

    /**
     * Resolve SCHEMA and PORT properties from web connector (located in <tomcat>/conf/server.xml)
     */
    private WebConnector discoverWebConnector() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer()
        QueryExp subQuery1 = Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"))
        QueryExp subQuery2 = Query.anySubString(Query.attr("protocol"), Query.value("Http11"))
        QueryExp query = Query.or(subQuery1, subQuery2)
        Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"), query)
        if (objs.size() > 0) {
            for (ObjectName obj : objs) {
                String scheme = mbs.getAttribute(obj, "scheme").toString()
                try {
                    return new WebConnector(scheme, Integer.parseInt(obj.getKeyProperty("port")))
                } catch (Exception e) {
                    log.warn("${e.getMessage()}", e)
                }
            }
        }
        return null
    }
}
