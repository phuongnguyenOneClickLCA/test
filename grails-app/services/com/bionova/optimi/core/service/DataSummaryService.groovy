package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.bionova.optimi.domainDTO.IndicatorUsageDTO
import com.bionova.optimi.util.DateUtil
import grails.gorm.transactions.Transactional
import org.bson.Document
import org.bson.types.ObjectId

import java.time.Duration

@Transactional
class DataSummaryService {

    IndicatorService indicatorService
    OptimiResourceService optimiResourceService
    LicenseService licenseService

    IndicatorUsageDTO getIndicatorUsage(String indicatorId, int limit, int offset) {
        IndicatorUsageDTO dto = new IndicatorUsageDTO()
        def currentDate = new Date()
        Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.TRUE)
        if (!indicator) {
            indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, Boolean.FALSE)
        }
        def entityClasses = optimiResourceService.getParentEntityClassIds()

        List<Document> entities
        int totalIndicatorUsage
        if (indicator?.compatibleEntityClasses) {
            entities = Entity.collection.find([indicatorIds: [$in: [indicatorId]], deleted:[$ne:true], $and:[[entityClass:[$in: entityClasses]],
                                                 [entityClass:[$in: indicator.compatibleEntityClasses]]]]).limit(limit).skip(offset)?.toList()

           totalIndicatorUsage = Entity.collection.count([indicatorIds: [$in: [indicatorId]], deleted: [$ne: true], $and: [[entityClass: [$in: entityClasses]],
                                                                                                      [entityClass: [$in: indicator.compatibleEntityClasses]]]]).intValue()
        } else {
            entities = Entity.collection.find([indicatorIds: [$in: [indicatorId]], deleted:[$ne:true], entityClass:[$in: entityClasses]])
                    .limit(limit).skip(offset)?.toList()
            totalIndicatorUsage = Entity.collection.count([indicatorIds: [$in: [indicatorId]], deleted:[$ne:true], entityClass:[$in: entityClasses]]).intValue()
        }

        List<License> allLicenses = licenseService.getProjectionLicenses()

        Map<String, List<Document>> projectIdAndChildren = new HashMap<>()
        Map<String, Map<String, List<License>>> projectIdAndLicenses = new HashMap<>()

        int i = 1
        int size = entities.size()

        List<Document> users
        if (entities) {
            List<ObjectId> objectIds = new ArrayList<>()
            List<ObjectId> lastUpdaterIds = new ArrayList<>()

            entities.each { Document entity ->
                if (entity.childEntities) {
                    entity.childEntities.each {objectIds.add(it.entityId)}
                }
                if (entity.lastUpdaterId) {
                    lastUpdaterIds.add(entity.lastUpdaterId)
                }
            }

            users = User.collection.find([_id: [$in: lastUpdaterIds]] ,[username:1])?.toList()

            List<Document> listChildEntity = Entity.collection.find([_id: [$in: objectIds], deleted: [$ne: true]], [calculationTotalResults: 1, allowAsBenchmark: 1, entityClass: 1])?.toList()
            entities.forEach({ entity ->
                def lastUpdater = entity.lastUpdaterId ? users.find({ entity.lastUpdaterId.equals(it._id) }) : null

                if (lastUpdater) {
                    entity.lastUpdaterUsername = lastUpdater.username
                }

                if (entity.managerIds) {
                    List<ObjectId> managerIds = DomainObjectUtil.stringsToObjectIds(entity.managerIds)
                    def managers = users.findAll({ managerIds.contains(it._id) })

                    if (managers) {
                        entity.managerUsernames = managers.collect({ it.username })
                    }
                }

                if (entity.modifierIds) {
                    List<ObjectId> managerIds = DomainObjectUtil.stringsToObjectIds(entity.modifierIds)
                    def managers = users.findAll({ managerIds.contains(it._id) })

                    if (managers) {
                        entity.modifiersUsernames = managers.collect({ it.username })
                    }
                }

                if (entity.readonlyUserIds) {
                    List<ObjectId> managerIds = DomainObjectUtil.stringsToObjectIds(entity.readonlyUserIds)
                    def managers = users.findAll({ managerIds.contains(it._id) })

                    if (managers) {
                        entity.readonlyUsernames = managers.collect({ it.username })
                    }
                }

                if (entity.childEntities) {
                    def ids = entity.childEntities.collect({ it.entityId })
                    List<Document> childEntities = new ArrayList<>()
                    if (listChildEntity) {
                        listChildEntity.each { Document child ->
                            if (ids.contains(child._id)) {
                                childEntities.add(child)
                            }
                        }
                    }

                    if (childEntities) {
                        childEntities.each { Document child ->
                            def totalsByRule = child.calculationTotalResults?.find({ indicator.indicatorId.equals(it?.indicatorId) })?.totalByCalculationRule

                            if (totalsByRule?.get(indicator.displayResult)) {
                                child.hasResults = true
                            }
                        }
                        projectIdAndChildren.put((entity._id.toString()), childEntities)
                    } else {
                        projectIdAndChildren.put((entity._id.toString()), [])
                    }
                } else {
                    projectIdAndChildren.put((entity._id.toString()), [])
                }
                List<License> entityLicenses = allLicenses.findAll({(!it.compatibleEntityClasses || it.compatibleEntityClasses.contains(entity.entityClass)) && (it.licensedEntityIds?.contains(entity._id) || it.licensedEntityIds?.contains(entity._id.toString())) })

                Map<String, List<License>> licensesMap = [:]

                if (entityLicenses) {
                    entityLicenses.each { License l ->
                        if (l.type.equals(Constants.LicenseType.EDUCATION.toString()) && l.trialLength && Duration.between(DateUtil.dateToLocalDatetime(l.getChangeHistory(null, entity)?.dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() > l.trialLength) {
                            List existing = licensesMap.get("invalid")

                            if (existing) {
                                existing.add(l)
                            } else {
                                existing = [l]
                            }
                            licensesMap.put("invalid", existing)
                        } else if (l.type.equals(Constants.LicenseType.TRIAL.toString()) && l.trialLength && Duration.between(DateUtil.dateToLocalDatetime(l.getChangeHistory(null, entity)?.dateAdded), DateUtil.dateToLocalDatetime(currentDate)).toDays() > l.trialLength) {
                            List existing = licensesMap.get("invalid")

                            if (existing) {
                                existing.add(l)
                            } else {
                                existing = [l]
                            }
                            licensesMap.put("invalid", existing)
                        } else if (l.valid) {
                            List existing = licensesMap.get("valid")

                            if (existing) {
                                existing.add(l)
                            } else {
                                existing = [l]
                            }
                            licensesMap.put("valid", existing)
                        } else {
                            List existing = licensesMap.get("invalid")

                            if (existing) {
                                existing.add(l)
                            } else {
                                existing = [l]
                            }
                            licensesMap.put("invalid", existing)
                        }
                    }
                    projectIdAndLicenses.put((entity._id.toString()), licensesMap)
                } else {
                    projectIdAndLicenses.put((entity._id.toString()), [:])
                }
                log.info("Prog: ${i} / ${size}")
                i++
            })
        }
        dto.setProjectIdAndChildren(projectIdAndChildren)
        dto.setProjectIdAndLicenses(projectIdAndLicenses)
        dto.setEntities(entities)
        dto.setAllLicenses(allLicenses)
        dto.setUsers(users)
        dto.setIndicator(indicator)
        dto.setTotalIndicatorUsage(totalIndicatorUsage)
        return dto
    }

}
