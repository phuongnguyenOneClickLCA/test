package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import grails.gorm.transactions.Transactional
import org.bson.types.ObjectId

@Transactional
class ChildEntityService {

    Entity getEntity(ObjectId entityId) {
        Entity entity = null
        if (entityId) {
            entity = Entity.collection.findOne(["_id": entityId]) as Entity
        }
        return entity
    }
}
