package com.bionova.optimi.construction.interceptor

import com.bionova.optimi.core.domain.mongo.User


class ChartImageRemovalInterceptor {

    ChartImageRemovalInterceptor() {
        match(controller:'design', action:'results')
        match(controller:'operatingPeriod', action:'results')
    }

    def userService

    boolean before() {
        true
    }

    boolean after() {
        User user = userService.getCurrentUser()

        if (user) {
            user.wordDocGraphsForSession = null
            userService.updateUserToSession(user)
        }
        true
    }

    void afterView() {
        // no-op
    }
}
