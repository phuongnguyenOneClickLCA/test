/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class EntityFile implements Serializable, Validateable {
    static mapWith = "mongo"
    ObjectId id
    String entityId
    String fileUrl
    byte[] data // Data of file
    String contentType
    String type  // fileType, eg jpg, pdf
    String name // original file name, not sure if needed
    Boolean thumbnail // if image is thumbnail. This is needed when fetching only thumbnail images
    Boolean image
    String queryId
    String sectionId
    String questionId

    static constraints = {
        thumbnail nullable:true
        image nullable: true
        data nullable: true
        contentType nullable: true
        type nullable: true
        name nullable: true
        fileUrl nullable: true
        queryId nullable: true
        sectionId nullable: true
        questionId nullable: true
    }

    static mapping = { entityId index: true }

    static transients = ["isImage"]

    Boolean getIsImage() {
        String typeLowerCase = type?.toLowerCase()

        if (typeLowerCase && (typeLowerCase == "jpg" || typeLowerCase == "jpeg" || typeLowerCase == "gif" || typeLowerCase == "png" || typeLowerCase == "tiff" || typeLowerCase == "tif")) {
            return true
        }
        return false
    }
}
