package com.bionova.optimi.core.service


import com.bionova.optimi.core.domain.mongo.ChannelFeature
import com.bionova.optimi.core.domain.mongo.Feature
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.UpdatePublish
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.plugin.springsecurity.annotation.Secured
import org.bson.types.ObjectId

class UpdatePublishService {
    def featureService
    def userService

    @Secured(["ROLE_AUTHENTICATED"])
    UpdatePublish getUpdatePublishById(String id) {
        UpdatePublish updatePublish

        if(id) {
            updatePublish = UpdatePublish.findById(DomainObjectUtil.stringToObjectId(id))
        }
        return updatePublish
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<UpdatePublish> getAllUpdatePublish(boolean checkChannelFeatureIds = false, ChannelFeature channelFeature, boolean checkExpiredDate = false){
        Date now = new Date()
        List<UpdatePublish> updatePublishes = []

        if (checkChannelFeatureIds && checkExpiredDate) {
            if (channelFeature) {
                updatePublishes = UpdatePublish.withCriteria {
                    inList('channelFeatureIds', [channelFeature.id.toString()])
                    or {
                        eq('dateExpired', null)
                        gt('dateExpired', now)
                    }
                    and {
                        ne('disabling',true)
                    }
                }
            }
        } else if (checkChannelFeatureIds) {
            if (channelFeature) {
                updatePublishes = UpdatePublish.withCriteria {
                    inList('channelFeatureIds', [channelFeature.id.toString()])
                    and {
                        ne('disabling',true)
                    }
                }

            }
        } else if (checkExpiredDate) {
            updatePublishes = UpdatePublish.withCriteria {
                or {
                    eq('dateExpired', null)
                    gt('dateExpired', now)
                }
                and {
                    ne('disabling',true)
                }
            }
        } else {
            updatePublishes = UpdatePublish.list().findAll({!it.disabling})
        }
        return updatePublishes

    }

    @Secured(["ROLE_SALES_VIEW"])
    boolean deleteUpdate(id){
        UpdatePublish updatePublish

        if(id) {
            updatePublish = UpdatePublish.get(id)
            updatePublish.delete(flush: true)
            return true
        }
        return false
    }

    @Secured(["ROLE_SALES_VIEW"])
    boolean saveUpdate(UpdatePublish updatePublish){
        if(updatePublish && !updatePublish.hasErrors()){
            if(updatePublish.showAsPopUp){
                List<UpdatePublish> updatePublishes = UpdatePublish.list()

                List<UpdatePublish> toRemovePopUp =  updatePublishes.findAll({it.showAsPopUp && it.channelFeatureIds?.intersect(updatePublish?.channelFeatureIds) && (it.id != updatePublish.id)})
                toRemovePopUp?.each {UpdatePublish publish ->
                    publish.showAsPopUp = null
                    publish = publish.merge(flush: true)
                }
            }
            if(updatePublish.id) {
                updatePublish = updatePublish.merge(flush: true)
            } else {
                updatePublish = updatePublish.save(flush: true)
            }
            return true
        }
        return false
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<UpdatePublish> sortingAlgorithmUpdatePublishList(List<UpdatePublish> updatePublishList, User user, List<License> userLicenses){
        Map<ObjectId, Integer> updateIdsSorted = [:]
        List<UpdatePublish> updatesSorted = []
        boolean isPrivilegeUser = user.internalUseRoles

        if (updatePublishList && !isPrivilegeUser) {
            List<String> userLicenseTypes = []
            List featureIds = []
            List<Feature> userFeatures  = []
            List<String> featureClass  = []
            List<String> relevantIndicatorIds  = []
            if(userLicenses){
                userLicenseTypes = userLicenses.collect({it.type}).unique()
                featureIds = userLicenses.collect({it.licensedFeatureIds}).flatten().unique()
                userFeatures = featureService.getFeaturesByIds(featureIds)
                featureClass = userFeatures.collect({it.featureClass}).unique()
                relevantIndicatorIds = userLicenses.collect({it.licensedIndicatorIds}).flatten().unique()
            }

            updatePublishList.sort({a,b -> b.lastUpdated <=> a.lastUpdated})
            updatePublishList.each {UpdatePublish update ->
                // 15750: new spec, from now only showing relevant updates if indicated for : licensesType,licensesLevel,indicatorId
                Boolean relevantIndicators = (relevantIndicatorIds && update.indicatorId?.intersect(relevantIndicatorIds)?.size() > 0) || !update.indicatorId
                Boolean relevantLicensesType = (userLicenseTypes && update.licensesType?.intersect(userLicenseTypes)?.size() > 0) || !update.licensesType
                Boolean relevantLicensesLevel = (featureClass && update.licensesLevel?.intersect(featureClass)?.size() > 0) || !update.licensesLevel
                if(relevantIndicators && relevantLicensesType && relevantLicensesLevel){
                    Integer score = 0
                    if(user?.country && update.countries?.contains(user.country)){
                        score += 1
                    }

                    if(updatePublishList.indexOf(update) == 0){
                        score += 2
                    }

                    if(update.stickyNote){
                        score += 5
                    }
                    updateIdsSorted.put(update.id, score)
                }
            }
            updateIdsSorted = updateIdsSorted.sort({-it.value})
            updateIdsSorted.each{k,v ->
                updatesSorted.add(updatePublishList.find({it.id.equals(k)}))
            }
        } else {
            updatesSorted = updatePublishList?.sort{a,b -> b?.stickyNote <=> a?.stickyNote ?: b.lastUpdated <=> a.lastUpdated ?: b.dateAdded <=> a.dateAdded}
        }
        return updatesSorted
    }

    List<ChannelFeature> getChannelFeatures(List<String> channelFeatureIds) {
        List<ChannelFeature> channelFeatures

        if (channelFeatureIds) {
            channelFeatures = ChannelFeature.getAll(DomainObjectUtil.stringsToObjectIds(channelFeatureIds))
        }
        return channelFeatures
    }
    List<String> getChannelFeatureNames(List<String> channelFeatureIds) {
        List<String> channelFeatureNames
        List<ChannelFeature> channelFeatures

        if (channelFeatureIds) {
            channelFeatures = ChannelFeature.getAll(DomainObjectUtil.stringsToObjectIds(channelFeatureIds))
            if(channelFeatures){
                channelFeatureNames = channelFeatures.collect({it?.name})
            }
        }


        return channelFeatureNames
    }
}
