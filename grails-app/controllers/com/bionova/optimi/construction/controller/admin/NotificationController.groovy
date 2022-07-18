package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.AbstractDomainObjectController
import com.bionova.optimi.core.domain.mongo.Notification
import grails.plugin.springsecurity.annotation.Secured

/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
@Secured(["ROLE_DEVELOPER"])
class NotificationController extends AbstractDomainObjectController {

    def notificationService
    def channelFeatureService
    def optimiResourceService

    @Override
    def list() {
        // Deprecate code, to be removed by the end of feature 0.5.0 release
        redirect(controller:'main', action:'list')
    }

    @Override
    def saveInternal() {
        Notification notification
        String id = params.id

        if (id) {
            notification = notificationService.getNotificationById(id)
            notification.properties = params

            if (!params.channelFeatureIds) {
                notification.channelFeatureIds = null
            }
        } else {
            notification = new Notification(params)
        }
        notification.channelFeatureIds = params.channelFeatureIds ? params.list("channelFeatureIds") : null

        if (notification?.validate() && notification?.heading && notification?.text) {
            notificationService.saveNotification(notification)
            flash.fadeSuccessAlert = message(code: "admin.notification.save.successful")
            redirect action: "list"
        } else {
            flash.errorAlert = renderErrors(bean: notification)
            chain action: "form", model: [notification]
        }
    }

    def form() {
        def systemLocales = optimiResourceService.getSystemLocales()
        def channelFeatures = channelFeatureService.getAllChannelFeatures()
        Notification notification

        if (params.id) {
            notification = notificationService.getNotificationById(params.id)
        }
        [systemLocales: systemLocales, notification: notification, channelFeatures: channelFeatures]
    }

    def remove() {
        boolean deleteOk = notificationService.removeNotification(params.id)

        if (deleteOk) {
            flash.fadeSuccessAlert = message(code: "admin.notification.delete.successful")
        } else {
            flash.fadeErrorAlert = message(code: "delete.error")
        }
        redirect action: "list"
    }
}
