package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.LicenseTemplate
import com.bionova.optimi.core.domain.mongo.ProjectTemplate
import com.bionova.optimi.core.domain.mongo.WorkFlow
import com.bionova.optimi.core.util.LoggerUtil
import grails.gorm.transactions.Transactional
import org.bson.types.ObjectId

@Transactional
class LicenseTemplateService {

    IndicatorService indicatorService
    FeatureService featureService
    QueryService queryService
    ProjectTemplateService projectTemplateService
    LoggerUtil loggerUtil
    FlashService flashService

    List<Indicator> getLicensedIndicators(List licensedIndicatorIds) {
        List<Indicator> indicators

        if (licensedIndicatorIds) {
            indicators = indicatorService.getIndicatorsByIndicatorIds(licensedIndicatorIds, true)
        }
        return indicators
    }

    List<WorkFlow> getLicensedWorkFlows(List<String> licensedWorkFlowIds) {
        List<WorkFlow> workFlows

        if (licensedWorkFlowIds) {
            workFlows = queryService.getQueryByQueryId("workFlowQueryId")?.workFlowList?.findAll({ licensedWorkFlowIds.contains(it.workFlowId) })
        }
        return workFlows
    }

    List<Feature> getLicensedFeatures(List licensedFeatureIds) {
        List<Feature> features

        if (licensedFeatureIds) {
            features = featureService.getFeaturesByIds(licensedFeatureIds)
        }
        return features
    }

    List<License> getUsedInLicenses(ObjectId id) {
        List<License> licenses

        if (id) {
            licenses = License.collection.find([licenseTemplateIds: [$in: [id.toString()]]])?.collect({ it as License })
        }
        return licenses
    }

    Integer getUsedInLicensesCount(ObjectId id) {
        Integer licenses

        if (id) {
            licenses = License.collection.count([licenseTemplateIds: [$in: [id.toString()]]])?.intValue()
        }
        return licenses
    }

    List<ProjectTemplate> getCompatibleProjectTemplates(List<ProjectTemplate> templates, LicenseTemplate licenseTemplate) {
        return projectTemplateService.getCompatibleProjectTemplates(templates, null, licenseTemplate)
    }

    void removeLinkedProjectTemplate(ProjectTemplate template, LicenseTemplate licenseTemplate) {
        if (template && licenseTemplate) {
            try {
                Boolean okToSave = false

                if (template.isPublic) {
                    if (licenseTemplate.defaultPublicProjectTemplateId == template.id?.toString()) {
                        licenseTemplate.defaultPublicProjectTemplateId = null
                    }
                    // this is more important
                    okToSave = licenseTemplate.publicProjectTemplateIds?.remove(template.id?.toString())
                }

                if (okToSave) {
                    licenseTemplate.merge(flush: true, failOnError: true)
                }
            } catch (e) {
                loggerUtil.error(log, "Error occurred in removeLinkedProjectTemplate in license template", e)
                flashService.setErrorAlert("Error occurred in removeLinkedProjectTemplate in license template: ${e.getMessage()}", true)
            }
        }
    }
}
