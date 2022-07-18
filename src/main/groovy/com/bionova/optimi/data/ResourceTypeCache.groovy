package com.bionova.optimi.data

import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.core.service.ResourceTypeService
import com.bionova.optimi.util.ApplicationContextProvider
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.util.CollectionUtils

@CompileStatic
class ResourceTypeCache {
    private final Map<String, ResourceType> cache = new HashMap<>()
    private final Log log = LogFactory.getLog(this.getClass())
    private final Set<String> notFoundTypes = new HashSet<>()

    ResourceTypeCache() {
    }

    List<ResourceType> getResourceTypes() {
        return cache.values().flatten() as ArrayList<ResourceType>
    }

    void clearResourceTypeCache() {
        cache.clear()
    }

    static ResourceTypeCache init(List<Resource> resources) {
        ResourceTypeCache resourceTypeCache = new ResourceTypeCache()
        resourceTypeCache.cache(resources)
        return resourceTypeCache
    }

    void cache(List<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return
        }
        log.debug("Putting " + resources.size() + " resources into cache")

        ResourceTypeService resourceTypeService = ApplicationContextProvider.getApplicationContext().getBean(ResourceTypeService.class)
        resourceTypeService.findResourceTypes(resources).each { it ->
            cache.put(getKey(it.getResourceType(), it.getSubType()), it)
        }
    }

    ResourceType getResourceSubType(Resource resource) {
        if (resource == null || !resource?.getResourceSubType()) {
            return null
        }
        ResourceType result = getResourceTypeFromCache(resource.getResourceType(), resource.getResourceSubType())

        if (result == null){
            String keyForSearch = getKey(resource.getResourceType(), resource.getResourceSubType())

            if (!(keyForSearch in notFoundTypes)){
                if (log.debugEnabled) {
                    log.debug("NOTIFY PLATFORM TEAM IF THIS IS IN CALCULATION FLOW: ResourceSubType with resourceType ${resource.getResourceType()} and ResourceSubType ${resource.getResourceSubType()} wasn't found in a cache. Stacktrace:\n ${Thread.currentThread()?.getStackTrace()?.toString()?.replaceAll(/,\s/, '\n\t')}")
                }
                result = resourceService.getSubType(resource)

                if (result) {
                    cache.put(keyForSearch, result)
                } else {
                    notFoundTypes.add(keyForSearch)
                }
            }
        }

        return result
    }

    ResourceType getResourceType(Resource resource) {
        if (resource == null || (!resource.getResourceType() && !resource.getResourceSubType())) {
            return null
        }
        ResourceType result = getResourceTypeFromCache(resource.getResourceType(), resource.getResourceSubType())

        if (result == null) {
            String keyForSearch = getKey(resource.getResourceType(), resource.getResourceSubType())

            if (!(keyForSearch in notFoundTypes)){
                if (log.debugEnabled) {
                    log.debug("NOTIFY PLATFORM TEAM IF THIS IS IN CALCULATION FLOW: ResourceType with resourceType ${resource.getResourceType()} and ResourceSubType ${resource.getResourceSubType()} wasn't found in a cache. Stacktrace:\n ${Thread.currentThread()?.getStackTrace()?.toString()?.replaceAll(/,\s/, '\n\t')}")
                }
                result = resource.getResourceTypeObject()

                if (result) {
                    cache.put(keyForSearch, result)
                } else {
                    notFoundTypes.add(keyForSearch)
                }
            }
        }
        return result
    }

    ResourceType getResourceTypeFromCache(String type, String subType) {
        if (subType == null && type == null) {
            return null
        }
        return cache.get(getKey(type, subType))
    }

    int size() {
        return cache.size()
    }

    private static String getKey(String type, String subType) {
        return "${type}${subType}"
    }

    private static ResourceService getResourceService() {
        return ApplicationContextProvider.getApplicationContext().getBean(ResourceService.class)
    }
}
