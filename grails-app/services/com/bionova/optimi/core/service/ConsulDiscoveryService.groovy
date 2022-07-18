package com.bionova.optimi.core.service

import com.bionova.optimi.util.monitor.ConsulTag
import com.bionova.optimi.util.monitor.ServiceId
import com.ecwid.consul.v1.QueryParams
import com.ecwid.consul.v1.Response
import com.ecwid.consul.v1.catalog.model.CatalogService


class ConsulDiscoveryService {
    def consulRegistrationService

    /**
     * Currently use simple 'Random' for resolving service from Consul.
     * Later need to include 'com.netflix.ribbon:ribbon' balance loader.
     */
    private Random random = new Random()

    String getNextServiceUrl(ServiceId serviceId) {
        if (!consulRegistrationService.isEnabled()) {
            return null
        }

        List<String> instances = getInstances(serviceId)
        int count = instances.size()
        if (count > 1) {
            return instances.get(random.nextInt(count))
        } else if (count == 1) {
            return instances.get(0)
        }
        return null
    }

    List<String> getInstances(ServiceId serviceId) {
        if (!consulRegistrationService.isEnabled()) {
            return []
        }

        Response<List<CatalogService>> services = consulRegistrationService.consulClient()?.getCatalogService(
                serviceId.getName(),
                QueryParams.DEFAULT
        )

        if (log.isTraceEnabled()) log.trace("'${serviceId}' instances: ${services?.value?.size()}")

        List<String> allEndpoints = new ArrayList<>()
        if (services) {
            List<CatalogService> instances = services.getValue()
            for (CatalogService service : instances) {
                String url = serviceToInstanceUrl(service)
                String ctx = getTagValue(ConsulTag.CONTEXT_PATH.getName(), service.getServiceTags())
                String instanceRoot = "${url}${ctx ?: ''}"
                if (log.isTraceEnabled()) log.trace("'${serviceId}' instance: ${instanceRoot}")
                allEndpoints.add(instanceRoot)
            }
        }
        if (log.isTraceEnabled()) log.trace(allEndpoints)
        return allEndpoints
    }

    private static String serviceToInstanceUrl(CatalogService service) {
        if (service) {
            String securedVal = getTagValue(ConsulTag.SECURE.getName(), service.getServiceTags())
            boolean secured = securedVal ? Boolean.parseBoolean(securedVal) : false
            String protocol = secured ? "https://" : "http://"
            if (service.getServicePort() == 0 || service.getServicePort() == 80) {
                return "${protocol}${service.serviceAddress ?: service.address}"
            } else {
                return "${protocol}${service.serviceAddress ?: service.address}:${service.servicePort}"
            }
        }
        return null
    }

    private static String getTagValue(String tagName, List<String> allTags) {
        int ctxTagLen = tagName.length() + 1
        for (String tag : allTags) {
            if (tag.startsWith(tagName)) {
                return tag.substring(ctxTagLen)
            }
        }
        return null
    }
}
