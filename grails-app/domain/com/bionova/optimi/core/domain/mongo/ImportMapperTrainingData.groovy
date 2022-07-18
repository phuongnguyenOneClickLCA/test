package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBObject
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import groovy.transform.TypeCheckingMode
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class ImportMapperTrainingData implements Validateable {
    static mapWith = "mongo"
    ObjectId id
    String applicationId
    String importMapperId
    Map<String, String> trainingData
    String resourceId
    String profileId
    String user
    String userLocale
    String originatingSystem
    String originalFileName
    String trainingComment
    String organization
    Date time
    Date lastUpdated
    Boolean compositeMaterial
    Boolean isDefault
    Boolean reportedAsBad
    String reportedAsBadByUser

    String equalIdForUI

    static constraints = {
        resourceId nullable: true
        profileId nullable: true
        originatingSystem nullable: true
        originalFileName nullable: true
        trainingComment nullable: true
        compositeMaterial nullable: true
        isDefault nullable: true
        equalIdForUI nullable: true
        user nullable: true
        userLocale nullable: true
        lastUpdated nullable: true
        organization nullable: true
        reportedAsBad nullable: true
        reportedAsBadByUser nullable: true
    }

    static transients = ['resource',
                         'nonActive',
                         'equalIdForUI',
                         'incomingKey',
                         'userObject',
                         'username']

    transient optimiResourceService
    transient userService
    transient applicationService

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    Resource getResource() {
        Resource resource

        if (resourceId) {
            resource = optimiResourceService.getResourceByResourceAndProfileId(resourceId, profileId)
        }
        return resource
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    boolean getNonActive() {
        boolean nonActive = false


        if (resourceId) {
            Resource resource = optimiResourceService.getResourceWithParams(resourceId, profileId, Boolean.TRUE)

            if (resource && !resource.active) {
                nonActive = true
            }
        }
        return nonActive
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getIncomingKey() {
        String incomingKey

        if (applicationId && importMapperId) {
            Application application = applicationService.getApplicationByApplicationId(applicationId)
            ImportMapper importMapper = application?.importMappers?.find({ importMapperId.equals(it.importMapperId) })

            if (importMapper?.trainingMatch) {
                List<String> trainingMatches = new ArrayList<String>(importMapper.trainingMatch)
                String userOwnResourceMapping = userService.getCurrentUser()?.importMapperResourceMappings?.get(importMapperId)

                if (userOwnResourceMapping) {
                    trainingMatches.add(userOwnResourceMapping)
                }

                trainingMatches.each { String key ->
                    String value = trainingData.get(key)

                    if (value) {
                        incomingKey = incomingKey ? "${incomingKey}, ${key}: ${value}" : "${key}: ${value}"
                    }
                }
            }
        }
        return incomingKey
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    User getUserObject() {
        User userObject

        if (user) {
            userObject = userService.getUserById(DomainObjectUtil.stringToObjectId(user))
        }
        return userObject
    }

    @GrailsCompileStatic(TypeCheckingMode.SKIP)
    String getUsername() {
        String username

        if (user) {
            username = userService.getUserAsDocumentById(user, new BasicDBObject([username: 1]))?.username
        }
        return username
    }
}
