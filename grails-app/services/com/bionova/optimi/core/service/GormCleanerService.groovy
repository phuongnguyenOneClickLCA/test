/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

/**
 * @author Pasi-Markus Mäkelä
 */
class GormCleanerService {
    
//    def propertyInstanceMap = DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP
    def mongoDatastore
    
    
    def cleanUpGorm() {
        def mongoSession = mongoDatastore.currentSession
        mongoSession.flush()
        mongoSession.clear()
//        propertyInstanceMap.get().clear()
    }
}
