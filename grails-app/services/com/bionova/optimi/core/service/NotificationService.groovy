package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Notification
import grails.plugin.springsecurity.annotation.Secured

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class NotificationService {

    @Secured(["ROLE_AUTHENTICATED"])
    def getNotificationById(String id) {
        Notification notification

        if(id) {
            notification = Notification.get(id)
        }
        return notification
    }

    @Secured(["ROLE_SUPER_USER"])
    def getAllNotifications() {
        def notifications = Notification.list()?.sort({it.lastUpdated})

        if(notifications) {
            Collections.reverse(notifications)
        }
        return notifications
    }

    @Secured(["ROLE_SUPER_USER"])
    def removeNotification(String id) {
        boolean deleteOk = true

        if(id) {
            Notification notification = Notification.get(id)

            if(notification) {
                notification.delete(flush: true)
                deleteOk = true
            }
        }
        return deleteOk
    }

    @Secured(["ROLE_SUPER_USER"])
    def saveNotification(Notification notification) {
        if(notification && !notification.hasErrors()) {
            if(notification.id) {
                notification = notification.merge(flush: true)
            } else {
                notification = notification.save(flush: true)
            }
        }
        return notification
    }
}
