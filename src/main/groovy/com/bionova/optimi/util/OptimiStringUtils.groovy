package com.bionova.optimi.util

import com.bionova.optimi.core.Constants
import groovy.transform.CompileStatic
import org.apache.commons.lang.StringUtils

/**
 * @author Pasi-Markus Mäkelä
 */
@CompileStatic
class OptimiStringUtils {

    public String truncate(String str, int maxLength) {
        if(str && maxLength) {
            if (str.length() > maxLength) {
                return StringUtils.substring(str, 0, maxLength) + "..."
            } else {
                return str
            }
        } else {
            return ""
        }
    }

    public String escapeSingleAndDoubleQuotes(String str) {
        if (str) {
            str = str.replace("\'", "\\'").replace("\"", "\\\"").trim()
            return str
        } else {
            return ""
        }
    }
    public String removeEscapedSingleAndDoubleQuotes(String str) {
        if (str) {
            str = str.replaceAll("\\'", "").replaceAll("\\\"", "").trim()
            return str
        } else {
            return ""
        }
    }

    public String removeSingleAndDoubleQuotes(String str) {
        if (str) {
            str = str.replace("\'", "").replace("\"", "")
            return str
        }else {
            return ""
        }
    }

    public String removeIllegalCharacters(String str){
        if (str) {
            str = str.replaceAll(Constants.USER_INPUT_REGEX, "")
            return str
        } else {
            return ""
        }
    }

    public String removeDotsAndCommas(String str) {
        if (str) {
            str = str.replaceAll("\\.", "")?.replaceAll(",", "")?.replaceAll(";", "")
            return str
        } else {
            return ""
        }
    }

    public String removeBreakLine(String str) {
        if (str) {
            str = str.replace('\n', '')
            return str
        } else {
            return ''
        }
    }

    public String removeBeginningBlankSpaces(String str) {
        if (str) {
            str = str.replaceFirst(/^\s*/, '')
            return str
        } else {
            return ''
        }
    }

    public String removeSingleAndDoubleQuotesAndBreakLineAndBeginningBlankSpaces(String str) {
        if (str) {
            str = removeSingleAndDoubleQuotes(str)
            str = removeBreakLine(str)
            str = removeBeginningBlankSpaces(str)
            return str
        } else {
            return ''
        }
    }

    public String removeInvalidCharacters(String str) {
        if (str) {
            str = str.replaceAll(Constants.USER_INPUT_REGEX_2, "")
            return str
        } else {
            return ""
        }
    }

}
