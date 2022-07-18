/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */
package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.RecognitionRuleset
import com.bionova.optimi.core.util.DomainObjectUtil

class RecognitionRulesetService extends GormCleanerService {

    static transactional = "mongo"

    List<RecognitionRuleset> getAllRecognitionRulesets(String type = null) {
        List<RecognitionRuleset> recognitionRulesets

        if (type) {
            recognitionRulesets = RecognitionRuleset.findAllByType(type)
        } else {
            recognitionRulesets = RecognitionRuleset.findAll()
        }

        return recognitionRulesets
    }

    def getRecognitionRulesetById(String id) {
        RecognitionRuleset recognitionRuleset

        if (id) {
            recognitionRuleset = RecognitionRuleset.findById(DomainObjectUtil.stringToObjectId(id))
        }

        return recognitionRuleset
    }

    def saveRecognitionRuleset(RecognitionRuleset recognitionRuleset) {
        if (recognitionRuleset && !recognitionRuleset.hasErrors()) {
            if (recognitionRuleset.id) {
                recognitionRuleset = recognitionRuleset.merge(flush: true, failOnError: true)
            } else {
                recognitionRuleset = recognitionRuleset.save(flush: true, failOnError: true)
            }
        }
        return recognitionRuleset
    }
}