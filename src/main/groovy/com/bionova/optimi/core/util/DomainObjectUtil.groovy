/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.util

import ch.qos.logback.classic.Logger
import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.Resource
import com.bionova.optimi.core.service.FlashService
import groovy.transform.CompileStatic
import org.apache.commons.beanutils.PropertyUtils
import org.bson.Document
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.context.i18n.LocaleContextHolder

import java.lang.reflect.Method

/**
 * @author Pasi-Markus Mäkelä
 */

class DomainObjectUtil {

    private static final Logger log = LoggerFactory.getLogger(DomainObjectUtil.class)

    @CompileStatic
    static List<ObjectId> stringsToObjectIds(List<String> strings) {
        def objectIds = []

        if (strings) {
            strings.each {
                if (it instanceof String && it != null && it != "null") {
                    objectIds.add(new ObjectId(it))
                } else if (it instanceof ObjectId && it != null) {
                    objectIds.add(it)
                }
            }
        }
        return objectIds
    }

    @CompileStatic
    static ObjectId stringToObjectId(def domainObjectId) {
        try {
            if (domainObjectId) {
                if (domainObjectId instanceof String) {
                    return new ObjectId(domainObjectId)
                } else if (domainObjectId instanceof ObjectId) {
                    return domainObjectId
                } else {
                    return null
                }
            } else {
                return null
            }
        } catch (Exception e) {
            return null
        }

    }

    @CompileStatic
    static String getMapKeyLanguage() {
        return LocaleContextHolder.getLocale().getLanguage().toUpperCase()
    }

    @CompileStatic
    static Object callGetterByAttributeName(String attribute, Object object, String filterName = null, Boolean suppressWarning = false) {
        def value

        if (attribute && object) {
            String firstChar = attribute.trim().substring(0, 1).toUpperCase()
            String methodName = "get" + firstChar + attribute.trim().substring(1)

            if (methodName in Constants.FORBIDDEN_METHODS) {
                LoggerUtil loggerUtil = SpringUtil.getBean("loggerUtil")
                loggerUtil.warn(log, "Attempt to call forbidden method '${methodName}' on instance of '${object.class.name}' class.")
            } else {
                try {
                    Method method = object.class.getMethod(methodName, null)
                    // Check https://oneclicklca.atlassian.net/browse/SW-1752?focusedCommentId=30676 explanation [] as Object[]
                    // Another possible fix is removing the second parameter as suggested and explained here https://github.com/One-Click-LCA/360Optimi/pull/1826
                    value = method.invoke(object, [] as Object[])
                } catch (NoSuchMethodException e) {
                    if (suppressWarning) {
                        return null
                    }

                    LoggerUtil loggerUtil = SpringUtil.getBean("loggerUtil")

                    if (object instanceof Resource) {
                        String errorMessage = "Resource does not have attribute ${attribute}"

                        if (filterName) {
                            errorMessage = "${errorMessage}. Failure in definition of filter ${filterName}"
                        }
                        // SW-1864 Minor cleanup: remove "Resource does not have attribute" WARNS from logging, just logging these takes time
//                        loggerUtil.debug(log, errorMessage)
                    } else {
                        // SW-1864 Minor cleanup: remove "Resource does not have attribute" WARNS from logging, just logging these takes time
//                        loggerUtil.debug(log, "Class ${object.class.name} doesn't have attribute ${attribute}")
                    }
                    value = null
                } catch (Exception e) {
                    // SW-1752 can remove the logs and flash right before release 0.5.0
                    LoggerUtil loggerUtil = SpringUtil.getBean("loggerUtil")
                    loggerUtil.warn(log, "Exception occured in callGetterByAttributeName", e)
                    FlashService flashService = SpringUtil.getBean("flashService")
                    flashService.setErrorAlert("Error in callGetterByAttributeName, check log. ${e.getMessage()}", true)
                    value = null
                }
            }
        }

        return value
    }

    @CompileStatic
    static void callSetterByAttributeName(String attribute, Object object, Object value, Boolean nullAllowed = Boolean.FALSE) {
        if (attribute && object && (value != null || nullAllowed)) {
            try {
                PropertyUtils.setProperty(object, attribute, value)
            } catch (Exception e) {
                PropertyUtils.setProperty(object, attribute, null)
            }
        }
    }

    @CompileStatic
    static List<Document> addStringIdToDocuments(List<Document> documents, Boolean removeDatabaseId = false) {
        // only run if all documents have _id
        if (documents && documents.findAll({ it._id }).size() == documents.size()) {
            documents.each {
                it.id = it._id.toString()
                if (removeDatabaseId) {
                    it.remove('_id')
                }
            }
        }

        return documents
    }

    @CompileStatic
    static Double convertStringToDouble(String value, Boolean returnNull = Boolean.FALSE) {
        value = convertToDotDecimalSeparator(value)

        if (value?.isDouble()) {
            return value.toDouble()
        }

        return returnNull ? null : 0D
    }

    @CompileStatic
    static boolean isNumericValue(String value) {
        return convertToDotDecimalSeparator(value)?.isNumber()
    }

    @CompileStatic
    static String convertToDotDecimalSeparator(String value) {
        return value?.trim()?.replace(",", ".")
    }
}
