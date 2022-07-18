package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.ExceptionHandlerController

/**
 * @author Pasi-Markus Mäkelä
 */
class ActiveUsersController extends ExceptionHandlerController {
    def userService

    def list() {
        [activeUsers: userService.getActiveUsers()?.unique(), userIdAndLastRequest: userService.getLastRequestByUserId()]
    }
}
