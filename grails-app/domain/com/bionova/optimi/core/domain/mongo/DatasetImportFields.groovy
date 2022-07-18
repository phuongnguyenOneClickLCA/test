package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic
import org.bson.types.ObjectId

@GrailsCompileStatic
class DatasetImportFields {

    static mapWith = "mongo"

    ObjectId id
    List<Map<String, String>> importFields
    Boolean limitExceeded

    static constraints = {
        importFields nullable: true
        limitExceeded nullable: true
    }
    public static final int IMPORT_FIELD_LIMIT = 500

    def beforeInsert() {
        if (importFields?.size() > IMPORT_FIELD_LIMIT) {
            importFields = importFields.take(IMPORT_FIELD_LIMIT)
            limitExceeded = Boolean.TRUE
        }
    }

    def beforeUpdate() {
        if (importFields?.size() > IMPORT_FIELD_LIMIT) {
            importFields = importFields.take(IMPORT_FIELD_LIMIT)
            limitExceeded = Boolean.TRUE
        }
    }
}
