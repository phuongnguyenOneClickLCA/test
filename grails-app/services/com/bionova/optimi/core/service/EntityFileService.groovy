package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile


class EntityFileService {
    EntityFile getEntityFile(Entity entity, String queryId, String questionId) {
        if (entity && queryId && questionId) {
            return EntityFile.findByEntityIdAndQueryIdAndQuestionId(entity.id.toString(), queryId, questionId)
        }
        return null
    }
}
