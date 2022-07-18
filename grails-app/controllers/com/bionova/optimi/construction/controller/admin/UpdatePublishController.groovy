package com.bionova.optimi.construction.controller.admin

import com.bionova.optimi.construction.controller.AbstractDomainObjectController
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.domain.mongo.UpdatePublish
import com.bionova.optimi.core.domain.mongo.User
import com.mongodb.BasicDBObject
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.bson.Document

import java.text.SimpleDateFormat

@Secured(["ROLE_DEVELOPER"])

class UpdatePublishController extends AbstractDomainObjectController{
    def updatePublishService
    def userService
    def channelFeatureService

    @Override
    def list() {
        BasicDBObject filter = new BasicDBObject("active", true)
        List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({it.nameEN})
        Map<String, String> typeForUpdate = [event: 'Event', update: 'Update']
        List<String> indicatorIds =  Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()?.sort({ it.toLowerCase() })
        def channelFeatures = channelFeatureService.getAllChannelFeatures()



        [updatesPublished: UpdatePublish.getAll(), countries: countries, licenseTypes:Constants.LicenseType.list(), licenceFeatureClass: Constants.LicenseFeatureClass.list(), typeForUpdate: typeForUpdate, indicatorIds: indicatorIds, channelFeatures: channelFeatures]
    }
    @Override
    def saveInternal() {
        UpdatePublish updatePublish
        String id = params.id
        if (id) {
            updatePublish = updatePublishService.getUpdatePublishById(id)
        } else {
            updatePublish = new UpdatePublish()
            updatePublish.type = params.type
            updatePublish.title = params.title
            updatePublish.countries = params.countries? params.countries.toString().tokenize(",[] "): null
            updatePublish.licensesLevel = params.licensesLevel ? params.licensesLevel.toString().tokenize(",[] ") : null
            updatePublish.indicatorId = params.indicatorId ? params.indicatorId.toString().tokenize(",[] ") : null
            updatePublish.licensesType = params.licensesType ? params.licensesType.toString().tokenize(",[] ") : null
            updatePublish.additionalInfo = params.additionalInfo
            updatePublish.link = params.link
            updatePublish.userName = userService.getCurrentUser()?.name ?: ''
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy")
            if(params.dateExpired){
                updatePublish.dateExpired = format.parse(params.dateExpired)
            }
            updatePublish.showAsPopUp = params.showAsPopUp ? Boolean.TRUE : Boolean.FALSE
            updatePublish.stickyNote = params.stickyNote ? Boolean.TRUE : Boolean.FALSE
            updatePublish.disabling = params.disabling ? Boolean.TRUE : Boolean.FALSE
            updatePublish.channelFeatureIds = params.channelFeatureIds ? params.list("channelFeatureIds") : null
        }

        if (updatePublish?.validate() && updatePublish?.title && updatePublish?.type) {
            updatePublishService.saveUpdate(updatePublish)
            flash.fadeSuccessAlert = "Update is saved successfully for publishing"
            redirect action: "list"
        } else {
            flash.errorAlert = renderErrors(bean: updatePublish)
            chain action: "list", model: [updatePublish]
        }
    }
    def modifyPublishModal() {
        UpdatePublish updatePublish
        String updateId = request?.JSON.updateId
        String response = ''

        if(updateId){
            updatePublish = updatePublishService.getUpdatePublishById(updateId)

            if (updatePublish) {
                BasicDBObject filter = new BasicDBObject("active", true)
                List<Document> countries = Resource.collection.find([resourceGroup: [$in: ["world"]], active: true], [resourceId: 1, nameEN: 1,])?.toList()?.sort({it.nameEN})
                Map<String, String> typeForUpdate = [event: 'Event', update: 'Update']
                List<String> indicatorIds =  Indicator.collection.distinct("indicatorId", filter, String.class)?.toList()?.sort({ it.toLowerCase() })
                def channelFeatures = channelFeatureService.getAllChannelFeatures()
                response = g.render(template: "/updatePublish/editModal", model: [update: updatePublish, countries: countries, typeForUpdate: typeForUpdate, channelFeatures:channelFeatures,indicatorIds: indicatorIds, licenseTypes:Constants.LicenseType.list(), licenceFeatureClass: Constants.LicenseFeatureClass.list()]).toString()
            } else {
                response = "Render Update Public modal: No object found with ID: ${updateId}"
            }
        } else {
            response = "Render Update Public modal: no ID given: ${updateId}"
        }
        render([output: response, (flashService.FLASH_OBJ_FOR_AJAX): flash] as JSON)
    }

    def update() {
        boolean saved = false
        String id = params.id
        String type = params.type
        String title = params.title

        if (id && type && title) {
            UpdatePublish updatePublish = updatePublishService.getUpdatePublishById(id)

            if (updatePublish) {
                updatePublish.type = type
                updatePublish.title = title
                updatePublish.countries = params.countries? params.countries.toString().tokenize(",[] "): null
                updatePublish.licensesLevel = params.licensesLevel ? params.licensesLevel.toString().tokenize(",[] ") : null
                updatePublish.indicatorId = params.indicatorId ? params.indicatorId.toString().tokenize(",[] ") : null
                updatePublish.licensesType = params.licensesType ? params.licensesType.toString().tokenize(",[] ") : null
                updatePublish.additionalInfo = params.additionalInfo
                updatePublish.link = params.link
                updatePublish.userName = userService.getCurrentUser()?.name ?: ''
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy")
                if(params.dateExpired.trim().size() > 0){
                    updatePublish.dateExpired = format.parse(params.dateExpired)
                } else {
                    updatePublish.dateExpired = null
                }
                updatePublish.showAsPopUp = params.showAsPopUp ? Boolean.TRUE : Boolean.FALSE
                updatePublish.stickyNote = params.stickyNote ? Boolean.TRUE : Boolean.FALSE
                updatePublish.disabling = params.disabling ? Boolean.TRUE : Boolean.FALSE
                updatePublish.channelFeatureIds = params.channelFeatureIds ? params.list("channelFeatureIds") : null


                updatePublishService.saveUpdate(updatePublish)
                saved = true
            }
        }

        if (saved) {
            flash.fadeSuccessAlert = "Feature saved!"
            redirect action: "list"
        } else {
            flash.fadeErrorAlert = "Something went wrong, contact a developer."
        }
    }
    def remove() {
            User currentUser = userService.getCurrentUser()

           if (userService.isSystemAdmin(currentUser) || userService.getSuperUser(currentUser)) {
               boolean deleted = updatePublishService.deleteUpdate(params.id)
               if(deleted){
                    flash.fadeSuccessAlert = "Update deleted"
                    redirect action: "list"
                } else {
                    flash.fadeErrorAlert = "You are not authorized to delete update. Only admin and system admin can."
                }
            } else {
                flash.fadeErrorAlert = "Cannot find update with that id, please contact dev team"
            }
    }

}
