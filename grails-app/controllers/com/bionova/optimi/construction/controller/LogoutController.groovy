package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.ChannelFeature
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author Pasi-Markus Mäkelä
 */
class LogoutController extends ExceptionHandlerController {
    def configurationService
    def logoutHandlers
    def channelFeatureService
    def userService


    def index() {
        /*
        Moved logic to SessionEndedListener
        User user = userService.getCurrentUser()

        if (user) {
            List<License> floatingLicenses = License.collection.find(["floatingLicenseCheckedInUsers.userId": user.id.toString()])?.collect({it as License})

            if (floatingLicenses) {
                floatingLicenses.each { License l ->
                    FloatingLicenseUser floatingLicenseUser = l.floatingLicenseCheckedInUsers.find({ it.userId == user.id.toString() })

                    if (floatingLicenseUser) {
                        l.floatingLicenseCheckedInUsers.remove(floatingLicenseUser)
                        l.merge(flush: true)
                    }
                }
            }
        }*/
        ChannelFeature channelFeature = channelFeatureService.getChannelFeature(session)
        def logoutUrl = channelFeatureService.processLoginUrl(channelFeature?.loginUrl, request)
        Authentication auth = SecurityContextHolder.context.authentication

        if (auth) {
            logoutHandlers.each { handler ->
                handler.logout(request, response, auth)
            }
        }

        try {
            session?.invalidate()
        } catch (Exception e) {

        }
        redirect url: logoutUrl
    }
}
