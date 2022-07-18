/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.taglib

import org.apache.commons.lang.StringUtils


/**
 * @author Pasi-Markus Mäkelä / SoftPM
 */
class StringUtilsTagLib {
    static encodeAsForTags = [
            abbr: 'html',
    ]

    transient stringUtilsService

    def abbr = { attrs ->
        out << stringUtilsService.abbr(attrs.value, attrs.maxLength, attrs.smartCut, attrs.substringAfter)
    }

    def trim = { attrs ->
        def value = attrs.value

        if (value) {
            out << StringUtils.trim(attrs.value)
        } else {
            out << ""
        }
    }

    def replace = { attrs ->
        def value = attrs.value
        def source = attrs.source
        def target = attrs.target

        if (value) {
            out << StringUtils.replace(value, source, target)
        } else {
            out << ""
        }
    }

    def convertUnitToHTML = { attrs ->
        String text = attrs.text
        if (text) {
            out << stringUtilsService.convertUnitToHTML(text)
        } else {
            out << ""
        }
    }
}
