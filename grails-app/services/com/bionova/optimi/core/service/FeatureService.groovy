/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.ImportMapper
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.plugin.springsecurity.annotation.Secured

/**
 * @author Pasi-Markus Mäkelä
 */
class FeatureService extends GormCleanerService {

    private static boolean featuresUpdated = false
    private static List<Feature> features

    def applicationService
    def licenseService
    def loggerUtil
    def flashService


    @Secured(["ROLE_SUPER_USER"])
    def getAll() {
        return new ArrayList<Feature>(getFeatures())
    }

    def saveFeature(Feature feature) {
        if (feature && !feature.hasErrors()) {
            if (feature.id) {
                feature.merge(flush: true)
            } else {
                feature.save(flush: true)
            }
            featuresUpdated = true
        }
    }

    List<Feature> getFeaturesByIds(List<String> featureIds) {
        List<Feature> features

        if (featureIds) {
            features = Feature.withCriteria{
                'in' ('id', DomainObjectUtil.stringsToObjectIds(featureIds))
            }
        }
        return features
    }

    def removeFeature(id) {
        if (id) {
            Feature feature = getFeaturesByIds([id])?.get(0)

            if (feature) {
                licenseService.getLicensesByFeatureId(feature.id.toString())?.each { License license ->
                    license.licensedFeatureIds?.remove(feature.id.toString())
                    licenseService.saveLicense(license, null)
                }

                licenseService.getLicenseTemplatesByFeatureId(feature.id.toString())?.each { LicenseTemplate licenseTemplate ->
                    licenseTemplate.licensedFeatureIds?.remove(feature.id.toString())
                    licenseTemplate.merge(flush: true)
                }
                feature.delete(flush: true)
                featuresUpdated = true
            }
        }
    }

    Feature getFeatureByFeatureId(String featureId) {
        Feature feature

        if (featureId) {
            feature = Feature.findByFeatureId(featureId, [cache: true])
        }
        return feature
    }

    Feature getFeatureById(String id) {
        Feature feature

        if (id) {
            feature = Feature.get(DomainObjectUtil.stringToObjectId(id))
        }
        return feature
    }

    def getImportFeatures(ImportMapper importMapper) {
        List<Feature> importFeatures = []

        if (importMapper) {
            List<String> allowedImportFeatures = [Feature.IFC_FROM_SIMPLE_BIM, Feature.API_SAMPLE_EXCEL, Feature.IMPORT_FROM_FILE]
            List<ImportMapper> importMappers = applicationService.getAllApplicationImportMappers()
            if (importMappers) {
                List<String> licensedApiImports = importMappers.collect({ it.securityTokens?.values()?.toList() })?.flatten()

                if (licensedApiImports) {
                    licensedApiImports.unique().each { String licensedSoftware ->
                        if (licensedSoftware && !allowedImportFeatures.contains("import${licensedSoftware}")) {
                            allowedImportFeatures.add("import${licensedSoftware}")
                        }
                    }
                }
            }

            allowedImportFeatures.each { String featureId ->
                Feature feature = getFeatureByFeatureId(featureId)

                if (feature && !importFeatures.contains(feature)) {
                    importFeatures.add(feature)
                }
            }

            Feature feature = getFeatureByFeatureId(importMapper.licenseKey)

            if (feature && !importFeatures.contains(feature)) {
                importFeatures.add(feature)
            }
        }
        return importFeatures

    }

    private List<Feature> getFeatures() {
        if (!features || isFeaturesUpdated()) {
            features = Feature.list()

        }
        return features
    }

    private boolean isFeaturesUpdated() {
        if (featuresUpdated) {
            featuresUpdated = false
            return true
        } else {
            return false
        }
    }

    /**
     * Create or update license feature
     * @param licenseKey Please add a constant in Feature domain and use it to create new licenseKey
     * @param licenseClass Please use LicenseFeatureClass constant
     * @param name
     * @param userLinked
     * @return true if feature is saved successfully
     */
    boolean createOrUpdateFeature(String licenseKey, String licenseClass, String name, Boolean userLinked = false) {
        if (licenseKey && licenseClass && name) {
            try {
                Feature feature = getFeatureByFeatureId(licenseKey)

                if (!feature) {
                    feature = new Feature()
                    feature.featureId = licenseKey
                    flashService.setFadeSuccessAlert("Created a new license feature <br> - Feature Id: $licenseKey <br> - Class: $licenseClass <br> - Name: $name")
                }
                feature.userLinked = userLinked
                feature.featureClass = licenseClass
                feature.name = name
                return saveFeature(feature)
            } catch (e) {
                loggerUtil.error(log, "Error in createOrUpdateFeature", e)
                flashService.setErrorAlert("Error in createOrUpdateFeature ${e.message}", true)
            }
        }
        return false
    }

    /**
     * Check condition for allowing grouping materials feature, from query + license + indicator
     * @param query
     * @param licensedFeatures
     * @param indicator
     * @return
     */
    Boolean isGroupMaterialFeatureAllowedByLicensedFeatures(Query query, List<Feature> licensedFeatures, Indicator indicator) {
        return query?.allowedFeatures?.contains(Feature.GROUP_MATERIALS) && licensedFeatures?.find { it.featureId == Feature.GROUP_MATERIALS } && !indicator?.preventGrouping
    }

    Boolean isGroupMaterialFeatureAllowed(Query query, List<String> licensedFeatureIds, Indicator indicator) {
        return query?.allowedFeatures?.contains(Feature.GROUP_MATERIALS) && licensedFeatureIds?.contains(Feature.GROUP_MATERIALS) && !indicator?.preventGrouping
    }
}
