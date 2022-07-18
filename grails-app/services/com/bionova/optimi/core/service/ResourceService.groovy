package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Resource
import com.mongodb.BasicDBObject
import com.bionova.optimi.core.domain.mongo.ResourceType
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.gorm.transactions.Transactional
import org.apache.commons.lang3.StringUtils

import java.lang.reflect.Method

@Transactional
class ResourceService {
    def optimiResourceService
    def datasetService
    def configurationService
    def userService
    def loggerUtil
    def flashService
    def resourceTypeService
    def costStructureService

    //TODO: can be very slow in a loop
    Resource getOriginalResource(CalculationResult calculationResult) {
        return optimiResourceService.getResourceWithParams(calculationResult?.originalResourceId, calculationResult?.originalProfileId, Boolean.TRUE)
    }

    //TODO: can be very slow in a loop
    Resource getResource(CalculationResult calculationResult) {
        if (!calculationResult) {
            return null
        }

        if (calculationResult.calculationResource && calculationResult.calculationResourceId && calculationResult.calculationResourceId.equals(calculationResult.calculationResource.resourceId)) {
            return calculationResult.calculationResource
        } else {
            calculationResult.calculationResource = optimiResourceService.getResourceWithParams(calculationResult.calculationResourceId, calculationResult.calculationProfileId, Boolean.TRUE)
            return calculationResult.calculationResource
        }
    }

    Resource getCalculationResourcePerDataset(CalculationResult calculationResult, String datasetId, Set<Dataset> datasets) {
        Resource resource

        if (datasetId) {
            Map calculationResultDataset = calculationResult?.calculationResultDatasets?.get(datasetId)

            if (calculationResultDataset) {
                resource = optimiResourceService.getResourceWithParams(calculationResultDataset.resourceId,
                        calculationResultDataset.profileId, true)
            } else {
                resource = datasetService.getResource(datasets?.find({ datasetId.equals(it.manualId) }))
            }
        }
        return resource
    }

    @Transactional(readOnly = true)
    List<Resource> findResources(List<String> resourceIds, List<String> profileIds, Boolean alsoInactive = Boolean.FALSE) {
        return optimiResourceService.getResources(resourceIds, profileIds, alsoInactive)
    }

    @Transactional(readOnly = true)
    List<Resource> findResources(List<String> resourceIds) {
        return optimiResourceService.getResources(resourceIds)
    }


    List<String> getResourceSubTypes() {
        return Resource.collection.distinct("resourceSubType", new BasicDBObject([resourceSubType: [$nin: [StringUtils.EMPTY, null]]]), String.class ).toList()
    }

    //TODO: can be very slow in a loop, please use ResourceCache/ResourceTypeCache LOCAL instances till we have a better solution
    @Deprecated
    ResourceType getSubType(Resource resource) {
        ResourceType subResourceType = null
        if (resource) {
            if(log.isTraceEnabled()) {
                log.trace("Fetching resource type for ${resource.resourceId}")
            }
            long now = System.currentTimeMillis()

            if (resource.resourceSubType) {
                subResourceType = resourceTypeService.getResourceTypeByResourceTypeAndSubType(resource.resourceType, resource.resourceSubType, Boolean.TRUE)

                if (!subResourceType && (resource.resourceType||resource.resourceSubType)) {
                    log.error("Resource ${resource.resourceId} / ${resource.profileId} no subResourceType found with params resourceType: ${resource.resourceType} and subType ${resource.resourceSubType}. Elapsed: ${System.currentTimeMillis() - now}")
                    flashService.setErrorAlert("Resource ${resource.resourceId} / ${resource.profileId} no subResourceType found with params resourceType: ${resource.resourceType} and subType ${resource.resourceSubType}", true)
                }
            }
        }
        return subResourceType
    }

    File getEpdFile(String downloadLink) {
        File file

        if (downloadLink) {
            String epdFilePathPrefix = configurationService.getConfigurationValue(null, "epdFilePathPrefix")

            if (epdFilePathPrefix) {
                file = new File(epdFilePathPrefix + downloadLink)
            }
        }
        return file
    }

    String getCostStructureUnit(String resourceSubType) {
        String unit
        if (resourceSubType) {
            unit = costStructureService.getCostStructureByResourceSubType(resourceSubType)?.costUnit
        }
        return unit
    }

    def getIsLocalResource(List<String> areas) {
        Boolean localResource = Boolean.FALSE

        if (areas && com.bionova.optimi.core.Constants.RESOURCE_LOCAL_AREA.equals(areas.first())) {
            localResource = Boolean.TRUE
        }
        return localResource
    }

    List<Resource> getTransportResourcesFromSubTypeBasedOnUserUnitSystem(Resource resource) {
        List<Resource> transportResources = []
        User user = userService.getCurrentUser()
        ResourceType rSubType = getSubType(resource)

        if (user?.unitSystem && rSubType) {
            if (user.unitSystem == 'metric' || user.unitSystem == 'both') {
                transportResources += optimiResourceService.getResourceWithGorm(rSubType.transportResourceId)
            }
            if (user.unitSystem == 'imperial' || user.unitSystem == 'both') {
                transportResources += optimiResourceService.getResourceWithGorm(rSubType.transportResourceIdMiles)
            }
        }
        return transportResources
    }

    Integer getServiceLifeDefault(Entity parent, Resource resource) {
        Integer serviceLife = null
        ResourceType rSubType = getSubType(resource)

        if (parent) {
            parent = parent.getParentById() ?: parent
            String serviceLifeDefault = parent.getServiceLifeDefault() ?: ''

            if (serviceLifeDefault) {
                serviceLife = DomainObjectUtil.callGetterByAttributeName(serviceLifeDefault, rSubType) as Integer

                if (serviceLifeDefault == 'resourceServiceLife' && serviceLife == null) {
                    serviceLife = DomainObjectUtil.callGetterByAttributeName('serviceLifeTechnical', rSubType) as Integer
                }
            }
        }

        return serviceLife
    }

    def getProfiles(String resourceId) {
        return optimiResourceService.getResourceProfilesByResourceId(resourceId, null, false)
    }

    String getIsoFlagPath(Map<String, String> isoCodesByAreas) {
        String isoFlag

        if (isoCodesByAreas) {
            String firstIsoCode = isoCodesByAreas.values().first()

            if (firstIsoCode) {
                if (System.getProperty("islocalhost")) {
                    isoFlag = "/app/assets/isoflags/${firstIsoCode}.png"
                } else {
                    String isoFilePath = configurationService.getConfigurationValue(Constants.APPLICATION_ID, "isoFlagUrlPath")

                    if (isoFilePath) {
                        isoFlag = "${isoFilePath}${firstIsoCode}.png"
                    }
                }
            }
        }
        if (!isoFlag) {
            isoFlag = "/app/assets/isoflags/globe.png"
        }
        return isoFlag
    }

    def getUiLabel(Resource resource) {
        "${optimiResourceService.getLocalizedName(resource)}${resource.technicalSpec ? ', ' + resource.technicalSpec : ''}${resource.commercialName ? ', ' + resource.commercialName : ''}" +
                "${resource.manufacturer ? ' (' + resource.manufacturer + ')' : ''}"
    }

    def callMethodByName(String name, Resource resource) {
        def value

        if (resource && name) {
            String firstChar = name?.trim().substring(0, 1).toUpperCase()
            String methodName = "get" + firstChar + name.trim().substring(1)

            try {
                Method method = Resource.class.getDeclaredMethod(methodName, null)
                value = method.invoke(resource, null)
            }
            catch (Exception e) {
                value = ""
                loggerUtil.error(log, "Error in invoking method Resource#" + methodName + ": " + e)
            }
        }
        return value
    }
}
