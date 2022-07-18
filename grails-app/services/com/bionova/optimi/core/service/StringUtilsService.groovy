/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.util.DomainObjectUtil
import org.apache.commons.lang.StringUtils
import org.springframework.context.i18n.LocaleContextHolder

class StringUtilsService {

    def messageSource
    def domainClassService
    def loggerUtil
    def flashService

    private static int oneLetterPixels = 4 // one letter = ~4px

    def abbr(value, maxLength, smartCut = null, substringAfter = null) {
        String abbrValue = ""

        if (value && maxLength && maxLength.toString().isInteger()) {
            value = value.toString()

            if (substringAfter && value.contains(substringAfter)) {
                value = StringUtils.substringAfter(value, substringAfter)
            }

            if (value.length() > maxLength.toString().toInteger()) {
                if (smartCut) {
                    def newValue = value.substring(0, maxLength.toString().toInteger())
                    int index = StringUtils.lastIndexOf(newValue, " ", newValue.length())
                    if (index == -1) {
                        abbrValue = StringUtils.stripEnd(newValue, ",")
                    } else {
                        abbrValue = StringUtils.stripEnd(newValue.substring(0, index), ",")
                    }
                } else {
                    abbrValue = StringUtils.substring(value, 0, maxLength.toString().toInteger())
                }
            } else {
                abbrValue = value
            }
        }
        return abbrValue
    }

    def convertUnitToHTML(String unit) {
        if (!unit) return ''

        Constants.SUBANDSUPERSCRIPTRULE.each { key, value ->
            unit = unit.replace(key, value)
        }
        return unit
    }

    def roundUpNumbersAsString(String number, int decimals) {
        if (number?.isNumber() && number?.toDouble() % 1 != 0 && decimals) {
            number = number?.toDouble()?.round(decimals)?.toString()
        }
        return number
    }

    List<String> getDuplicates(List<String> stringList) {
        List<String> duplicates = []
        if (stringList) {
            Set<String> uniques = new HashSet<>()
            for (String string in stringList) {
                boolean isUnique = uniques.add(string)
                if (!isUnique) {
                    duplicates.add(string)
                }
            }
        }
        return duplicates
    }

    String getLocalizedText(String code, Object[] args = []) {
        if (code) {
            try {
                return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
            } catch (ignored) {
                return code
            }
        } else {
            return ''
        }
    }

    String outputAttributes(Map attrs) {
        String output = ''
        attrs.remove('tagName') // Just in case one is left
        attrs.each { k, v ->
            if (v != null) {
                output += k + '="' + v + '" '
            }
        }
        return output
    }

    /**
     * Calculate max number of character for a name, given the inputWidth of a question
     * @param inputWidth
     * @return
     */
    int calculateNameMaxLength(Integer inputWidth) {
        return inputWidth ? (inputWidth / oneLetterPixels) <= 3 ? 3 : (inputWidth / oneLetterPixels) : 20
    }

    /**
     * Count the number of occurrence of an item in a list
     * @param strings
     * @return
     */
    Map<String, Integer> countItemOccurrenceInList(List<String> strings) {
        Map<String, Integer> countMap = [:]
        if (strings) {
            strings.each { String str ->
                Integer existingCount = countMap?.get(str)
                if (existingCount > 0) {
                    countMap.put(str, existingCount + 1)
                } else {
                    countMap.put(str, 1)
                }
            }
        }
        return countMap
    }

    /**
     * Tokenize a set of strings in format value.commonKey and put to a map
     * @param strings
     * @return map <commonKey, list of values with same key>
     */
    Map<String, Set<String>> tokenizeStringsToMapWithCommonKeyAndSetValue(Set<String> strings) {
        Map<String, Set<String>> map = [:]
        for (String string in strings) {
            Set<String> tokenized = string?.tokenize('.')
            if (tokenized?.size() > 1) {
                String value = tokenized[0]
                String commonKey = tokenized[1]

                Set<String> valueList = map.get(commonKey) ?: []
                valueList.add(value)
                map.put(commonKey, valueList)
            }
        }
        return map
    }

    /**
     * Remove unwanted characters from value of an attribute in object.
     * @param config a Map with key: attribute of object, value: list of Objects (key: the string to be replaced and value: new string)
     * @param object
     */
    void updateStringInObject(Map<String, List<Map<String, String>>> config, Object object) {
        if (!object || !config) {
            return
        }
        config.each { String attribute, List<Map<String, String>> toUpdateConfig ->
            try {
                if (toUpdateConfig && attribute) {
                    if (attribute == 'all') {
                        // run update on all attributes that are type string if attribute from config is 'all'
                        List<String> stringTypeAttributes = domainClassService.getPersistentPropertyNamesForDomainClassForNmd(object.class, String)
                        if (stringTypeAttributes) {
                            for (String attr in stringTypeAttributes) {
                                if (attr) {
                                    updateStringValue(attr, object, toUpdateConfig)
                                }
                            }
                        }
                    } else {
                        updateStringValue(attribute, object, toUpdateConfig)
                    }
                }
            } catch (e) {
                loggerUtil.error(log, "Error in updateStringInObject", e)
                flashService.setErrorAlert("Error in updateStringInObject: ${e.getMessage()}", true)
            }
        }
    }

    private static void updateStringValue(String attribute, Object object, List<Map<String, String>> toUpdateConfig) {
        def value = DomainObjectUtil.callGetterByAttributeName(attribute, object, null, true)
        if (value != null && value instanceof String && toUpdateConfig) {
            toUpdateConfig.each { Map<String, String> config ->
                if (config) {
                    config.each { String sourceString, String targetString ->
                        value = value.replaceAll(sourceString, targetString)
                    }
                }
            }
            DomainObjectUtil.callSetterByAttributeName(attribute, object, value)
        }
    }
}
