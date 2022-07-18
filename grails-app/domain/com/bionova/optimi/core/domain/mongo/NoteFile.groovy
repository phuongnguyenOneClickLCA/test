package com.bionova.optimi.core.domain.mongo

import grails.validation.Validateable

/**
 *
 * @author Pasi-Markus Mäkelä
 *
 */
class NoteFile implements Serializable, Validateable {
    static mapWith = "mongo"
    byte[] data // Data of file
    String contentType
    String name // original file name, not sure if needed

    static constraints = {
        data maxSize: 1048586// 10MB
    }

    static transients = ['isOfficeFile']

    def getIsOfficeFile() {
        boolean officeFile = false

        if (name && (name.endsWith(".doc") || name.endsWith(".docx") || name.endsWith(".xls") || name.endsWith(".xlsx")
                || name.endsWith(".ptt") || name.endsWith(".pptx"))) {
            officeFile = true
        }
        return officeFile
    }
}
