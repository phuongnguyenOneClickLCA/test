/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.domain.mongo

import com.bionova.optimi.core.transformation.Translatable
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

/**
 * @author Miika
 */
@GrailsCompileStatic
class PluginUpdatePrompt implements Serializable, Validateable {
    String minAddOnVersion
    @Translatable
    Map<String, String> promptText
    String promptLink
    String color // red, yellow, green == corresponding flash messages error, warn and ok

    static mapWith = "mongo"

}
