package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

/**
 * @author Pasi-Markus Mäkelä
 */
@GrailsCompileStatic
class PreProvisionedUserRight implements Serializable {
    static mapWith = "mongo"
    String uuid
    String username
    String userRight // readonly, modifider or manager
}
