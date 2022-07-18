package com.bionova.optimi.security

import com.bionova.optimi.core.domain.mongo.User
import grails.events.annotation.gorm.Listener
import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileStatic
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class UserPasswordEncoderListener {
    @Autowired
    SpringSecurityService springSecurityService

    Log log = LogFactory.getLog(UserPasswordEncoderListener.class)


    @Listener(User)
    void onPreInsertEvent(PreInsertEvent event) {
        encodePasswordForEvent(event)
    }

    @Listener(User)
    void onPreUpdateEvent(PreUpdateEvent event) {
        encodePasswordForEvent(event)
    }

    private void encodePasswordForEvent(AbstractPersistenceEvent event) {
        if (event.entityObject instanceof User) {
            User u = event.entityObject as User

            if (u.password) {
                if (event instanceof PreInsertEvent) {
                    event.getEntityAccess().setProperty('password', encodePassword(u.password))
                    event.getEntityAccess().setProperty('passwordAgain', u.password)
                    event.getEntityAccess().setProperty("persistedPassword", u.password)
                } else if (event instanceof PreUpdateEvent) {
                    if (u.persistedPassword != u.password) {
                        event.getEntityAccess().setProperty('password', encodePassword(u.password))
                        event.getEntityAccess().setProperty('passwordAgain', u.password)
                        event.getEntityAccess().setProperty("persistedPassword", u.password)
                    }
                }
            }
        }
    }

    private String encodePassword(String password) {
        springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

}
