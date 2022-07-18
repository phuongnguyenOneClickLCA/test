package com.bionova.optimi.construction.controller.admin

class UserDataRequestController {

    def userDataRequestService

    // Deprecate code, to be removed by the end of feature 0.5.0 release
//    def index() {
//        [dataRequests: userDataRequestService.getDataRequests()]
//    }

    def remove() {
        if (userDataRequestService.remove(params.id)) {
            flash.fadeSuccessAlert = "User data request removed successfully"
        } else {
            flash.fadeErrorAlert = "Error in removing user data request"
        }
        redirect action: "index"
    }
}
