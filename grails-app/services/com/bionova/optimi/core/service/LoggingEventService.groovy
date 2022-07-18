package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.LoggingEvent

/**
 * @author Pasi-Markus Mäkelä
 */
class LoggingEventService {

    def List<LoggingEvent> getExceptionEvents() {
        def results = LoggingEvent.collection().find(["type": "exeption"])

        if (results) {
            return results.collect({it as LoggingEvent})
        }
        return null
    }

    def List<LoggingEvent> getQuotaEvents() {
        def results = LoggingEvent.collection().find(["type": "quota"])

        if (results) {
            return results.collect({it as LoggingEvent})
        }
        return null
    }
}
