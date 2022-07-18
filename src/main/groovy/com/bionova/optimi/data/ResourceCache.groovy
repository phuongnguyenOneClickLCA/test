package com.bionova.optimi.data

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.service.OptimiResourceService
import com.bionova.optimi.core.service.ResourceService
import com.bionova.optimi.util.ApplicationContextProvider
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.util.CollectionUtils

/**
 * TODO: please rename to ResourceContext and use it only as local instance!
 * Temporary memorizes resources for working datasets. This is not a global cache in any way.
 */
@CompileStatic
class ResourceCache {

    private final Map<String, List<Resource>> cache = new HashMap<>()
    private final Log log = LogFactory.getLog(this.getClass())
    private final static Set<String> WRONG_RESOURCE_IDS = new HashSet<>(["undefined", null, "null"])
    private Set<String> notFoundResources

    private ResourceTypeCache resourceTypeCache = new ResourceTypeCache()

    ResourceCache() {
        notFoundResources = new HashSet<>(WRONG_RESOURCE_IDS)
    }

    static ResourceCache init(List<String> resourceIds, List<String> profileIds) {
        ResourceCache resourceCache = new ResourceCache()
        resourceCache.cache(resourceIds, profileIds)
        return resourceCache
    }

    /**
     * Init resourceCache object by fetching related resources to datasets
     * @param datasets
     * @param fetchLocalComp
     * @return
     */
    static ResourceCache init(List<Dataset> datasets, Boolean fetchLocalComp = Boolean.FALSE) {
        ResourceCache resourceCache = new ResourceCache()
        Set<String> resourceIds = datasets?.findResults { it.resourceId }?.toSet()

        if (fetchLocalComp) {
            resourceIds?.addAll(getLocalCompsCountryEnergyResourceIds(datasets))
        }

        resourceCache.cache(resourceIds)

        return resourceCache
    }

    /**
     * Add new resources that might be missing in the list of datasets
     * This could happen after the first resource cache initialization and dataset list has been modified after certain code flow
     * @param datasets
     */
    void addExtraResources(List<Dataset> datasets) {
        if (datasets) {
            Set<String> resourceIds = datasets.findResults { it.resourceId }?.toSet()
            
            if (resourceIds) {
                Set<String> existingInCache = cache.keySet()

                if (existingInCache) {
                    resourceIds -= existingInCache
                }

                if (notFoundResources) {
                    resourceIds -= notFoundResources
                }

                if (resourceIds) {
                    putResources(findResources(resourceIds))
                }
            }
        }
    }

    /**
     * Get all resourceIds about country and energy profile resourceIds from datasets
     * These resources are needed for local compensation during calculation
     * @param datasets
     * @return
     */
    static Set<String> getLocalCompsCountryEnergyResourceIds(List<Dataset> datasets) {
        Set<String> localCompsCountryEnergyResourceIds = new HashSet<String>()

        for (Dataset dataset : datasets) {
            // no need to cache because it's get in calculation service before calculation
            String localCompsCountryResourceId = dataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_QUESTIONID)

            if (localCompsCountryResourceId) {
                String compensationEnergyProfile = dataset?.additionalQuestionAnswers?.get(Constants.LOCAL_COMP_ENERGY_PROFILE_QUESTIONID)?.toString()

                if (compensationEnergyProfile && compensationEnergyProfile.tokenize(".")?.size() == 2) {
                    String localCompsCountryEnergyResourceId = compensationEnergyProfile.tokenize(".")[0]
                    localCompsCountryEnergyResourceIds.add(localCompsCountryEnergyResourceId)
                }
            }
        }

        return localCompsCountryEnergyResourceIds
    }

    void cache(List<String> resourceIds, List<String> profileIds) {
        if (CollectionUtils.isEmpty(resourceIds) || CollectionUtils.isEmpty(profileIds)) {
            return
        }
        log.debug("Putting " + resourceIds.size() + " resources into cache")
        if (log.isTraceEnabled()) {
            log.trace("Putting " + resourceIds + " for " + profileIds + " into cache")
        }
        ResourceService resourceService = getResourceService()
        putResources(resourceService.findResources(resourceIds, profileIds, true))
    }

    void cache(Set<String> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return
        }
        log.info("Putting " + resourceIds.size() + " resources into cache")
        if (log.isTraceEnabled()) {
            log.trace("Putting " + resourceIds + " into cache")
        }

        putResources(findResources(resourceIds))
    }

    /**
     * Pre-load necessary resources for calculation both LCA and LCC. This does not pre-load all yet
     */
    void initForCalculation() {
        Set<String> extraResourceIds = new HashSet<String>()
        getResourceTypeCache()
        extraResourceIds.addAll(getCStageResourceIdsFromResourceTypes(resourceTypeCache.getResourceTypes()))
        extraResourceIds.addAll(getCStageResourceIdsFromResources())

        if (extraResourceIds) {
            putResources(findResources(extraResourceIds))
        }
    }

    /**
     * Can be called when we added localComps, defaultEnergy and Calculation resources to ResourceCache
     */
    ResourceTypeCache reInitResourceTypeCache() {
        resourceTypeCache.clearResourceTypeCache()
        resourceTypeCache.cache(getResources())
        return resourceTypeCache
    }

    /**
     * Pre-load all resources by resourceId that are possibly needed for C stage calculation (EOL)
     * @param resourceTypes
     */
    void initCStageResources(List<Resource> resources = null) {
        Set<String> resourceIds = getCStageResourceIdsFromResources(resources)

        if (resourceIds) {
            putResources(findResources(resourceIds))
        }
    }

    /**
     * Get all resourceIds from subtype that are possibly neeeded for C stage calculation (EOL)
     * @param resources
     */
    private Set<String> getCStageResourceIdsFromResources(List<Resource> resources = null) {
        if (!resources) {
            resources = getResources()
        }

        Set<String> resourceIds = new HashSet<String>()

        for (Resource resource : resources) {
            if (!resource) {
                continue
            }

            if (resource.wasteResourceId && !cache.get(resource.wasteResourceId)) {
                resourceIds.add(resource.wasteResourceId.trim())
            }

            if (resource.environmentalBenefitsResourceId && !cache.get(resource.environmentalBenefitsResourceId)) {
                resourceIds.add(resource.environmentalBenefitsResourceId.trim())
            }
        }
        return resourceIds
    }

    /**
     * Get all resourceIds from subtype that are possibly neeeded for C stage calculation (EOL)
     * @param resourceTypes
     */
    Set<String> getCStageResourceIdsFromResourceTypes(List<ResourceType> resourceTypes) {
        Set<String> resourceIds = new HashSet<String>()

        for (ResourceType resourceType : resourceTypes) {
            if (!resourceType) {
                continue
            }

            if (resourceType.wasteResourceId && !cache.get(resourceType.wasteResourceId)) {
                resourceIds.add(resourceType.wasteResourceId)
            }

            if (resourceType.environmentalBenefitsResourceId && !cache.get(resourceType.environmentalBenefitsResourceId)) {
                resourceIds.add(resourceType.environmentalBenefitsResourceId)
            }
        }
        return resourceIds
    }

    private static List<Resource> findResources(Set<String> resourceIds) {
        ResourceService resourceService = getResourceService()
        return resourceService.findResources(resourceIds.toList())
    }

    ResourceTypeCache getResourceTypeCache() {
        if (!resourceTypeCache || resourceTypeCache.size() <= 0) {
            resourceTypeCache = ResourceTypeCache.init(getResources())
        }

        return resourceTypeCache
    }

    Resource getResource(Dataset dataset) {
        if (dataset == null) {
            return null
        }
        return getResource(dataset.getResourceId(), dataset.getProfileId())
    }

    /**
     * Get a resource from the cache object. If not found, fetch from db and log warn in this case
     * @param resourceId
     * @param profileId
     * @param alsoInactive
     * @param fetchIfMissing - fetch from db and put to cache if resource is not found in cache
     * @return
     */
    Resource getResource(String resourceId, String profileId, Boolean alsoInactive = Boolean.TRUE, Boolean fetchIfMissing = Boolean.TRUE) {
        if (resourceId == null) {
            return null
        } else {
            resourceId = resourceId.trim() // can be some cases when it has spaces for environmentalBenefitsResourceId and others
        }

        List<Resource> resourcesByResourceId = cache.get(resourceId)
        Resource foundResource = null

        if (resourcesByResourceId) {
            if (profileId) {
                foundResource = resourcesByResourceId.find { it.resourceId == resourceId && it.profileId == profileId && it.active == true }

                if (!foundResource) {
                    foundResource = resourcesByResourceId.find { it.resourceId == resourceId && it.defaultProfile == true && it.active == true }
                }
            } else {
                foundResource = resourcesByResourceId.find { it.resourceId == resourceId && it.defaultProfile == true && it.active == true }

                // Failover to no defaultProfile if data team fucked up smh..
                if (!foundResource) {
                    foundResource = resourcesByResourceId.find {it.resourceId == resourceId && it.active == true}
                }
            }

            if (foundResource) {
                return foundResource
            } else {
                foundResource = resourcesByResourceId.find {it.resourceId == resourceId && it.active == true}
            }

            //find inactive if no found
            if (!foundResource && alsoInactive) {
                if (profileId) {
                    foundResource = resourcesByResourceId.find { it.resourceId == resourceId && it.profileId == profileId }
                } else {
                    foundResource = resourcesByResourceId.find { it.resourceId == resourceId && it.defaultProfile == true }
                }

                if (!foundResource) {
                    foundResource = resourcesByResourceId.find { it.resourceId == resourceId }
                }
            }
        } else if (fetchIfMissing) {
            if(!(resourceId in notFoundResources)) {
                log.debug("NOTIFY PLATFORM TEAM IF THIS IS IN CALCULATION FLOW: Resources with resourceId ${resourceId} wasn't found in a cache. Stacktrace:\n ${Thread.currentThread()?.getStackTrace()?.toString()?.replaceAll(/,\s/, '\n\t')}")
                Resource resource = optimiResourceService.getResourceWithParams(resourceId, profileId, alsoInactive)

                if (resource) {
                    putResources([resource])
                } else {
                    notFoundResources.add(resourceId)
                }
            }
        }

        return foundResource
    }

    List<Resource> getResources() {
        return cache.values().flatten() as ArrayList<Resource>
    }

    /**
     * Calculation result contains two types of resources. Original resource - this is resource
     * from dataset. Calculation resource - this is another resource (from addQ f.e.), which
     * parameters can be used in calculation
     *
     * @param calculationResult
     * @param isOriginalResource - flag which allow to choose which resource should be taken
     * @param fetchIfMissing - fetch from db and put to cache if resource is not found in cache
     * @return resource - resource from cache
     */
    Resource getResource(CalculationResult calculationResult, Boolean isOriginalResource, Boolean fetchIfMissing = Boolean.TRUE) {
        if (calculationResult == null) {
            return null
        }

        Resource resource

        if (isOriginalResource) {
            resource = getResource(calculationResult.getOriginalResourceId(), calculationResult.getOriginalProfileId(), true, fetchIfMissing)

            if (!resource) {
                resource = getResource(calculationResult.getOriginalResourceId(), null, true, fetchIfMissing)
            }
        } else {
            resource = getResource(calculationResult.getCalculationResourceId(), calculationResult.getCalculationProfileId(), true, fetchIfMissing)

            if (!resource) {
                resource = getResource(calculationResult.getCalculationResourceId(), null, true, fetchIfMissing)
            }
        }

        return resource
    }

    int size() {
        return cache.size()
    }

    /**
     * Put resources to cache, we group by key: resourceId
     * @param resources
     */
    void putResources(List<Resource> resources) {
        if (resources) {
            Map<String, List<Resource>> groupedResources = resources.groupBy { it?.resourceId }

            if (groupedResources?.keySet()?.intersect(cache.keySet())) {
                groupedResources.each {
                    if (cache.get(it.getKey())) {
                        cache.get(it.getKey()).addAll(it.value)
                    } else {
                        cache.put(it.key, it.value)
                    }
                }
            } else {
                cache.putAll(resources.groupBy { it?.resourceId })
            }
        }
    }

    private static ResourceService getResourceService() {
        return ApplicationContextProvider.getApplicationContext().getBean(ResourceService.class)
    }

    private static OptimiResourceService getOptimiResourceService() {
        return ApplicationContextProvider.getApplicationContext().getBean(OptimiResourceService.class)
    }

    /**
     * Get country energy resource of a country, fetch from db if not found and put to cache
     * @param countryResource
     * @param usingNewLocalComps
     * @return
     */
    Resource getCountryEnergyResource(Resource countryResource, Boolean usingNewLocalComps = Boolean.FALSE) {
        if (!countryResource) {
            return null
        }

        Resource resource = null
        String profileId = usingNewLocalComps ? countryResource.countryEnergyResourceProfileId_v2 : countryResource.countryEnergyResourceProfileId

        if (profileId) {
            resource = getResource(countryResource.countryEnergyResourceId, profileId)

            // fetch db and add to cache
            if (!resource) {
                resource = optimiResourceService.getResourceWithParams(countryResource.countryEnergyResourceId, profileId, false)

                if (resource) {
                    putResources([resource])
                }
            }
        }

        return resource
    }

    /**
     * Get incieration resource of a country, fetch from db if not found and put to cache
     * @param countryResource
     * @return
     */
    Resource getCountryIncinerationResource(Resource countryResource) {
        if (!countryResource) {
            return null
        }

        Resource resource = getResource(countryResource.countryIncinerationResourceId, null)

        // fetch db and add to cache
        if (!resource) {
            resource = optimiResourceService.getResourceWithParams(countryResource.countryIncinerationResourceId, null, false)

            if (resource) {
                putResources([resource])
            }
        }
        return resource
    }
}
