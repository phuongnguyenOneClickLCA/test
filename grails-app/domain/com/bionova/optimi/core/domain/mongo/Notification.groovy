package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import com.bionova.optimi.core.util.DomainObjectUtil
import grails.validation.Validateable
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.MapUtils
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
class Notification implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    @Translatable
    Map<String, String> heading = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    @Translatable
    Map<String, String> text = MapUtils.lazyMap([:], FactoryUtils.constantFactory(''))
    Boolean enabled
    Boolean isPopUp
    Date dateCreated
    Date lastUpdated
    Date showIfAccountCreatedBefore
    List channelFeatureIds

    static constraints = {
        heading nullable: false
        text nullable: false
        enabled nullable: true
        isPopUp nullable: true
        channelFeatureIds nullable: true
        showIfAccountCreatedBefore nullable: true
    }

    static transients = ['channelFeatures']

    def getChannelFeatures() {
        def channelFeatures = null

        if (channelFeatureIds) {
            channelFeatures = ChannelFeature.getAll(DomainObjectUtil.stringsToObjectIds(channelFeatureIds))
        }
        return channelFeatures
    }

    def beforeInsert() {
        dateCreated = new Date()
        lastUpdated = dateCreated
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }
}
