package com.bionova.optimi.core.service

import com.bionova.optimi.core.util.DomainObjectUtil
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.codehaus.groovy.runtime.MetaClassHelper

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * A service that tries to deal with translatable properties
 *
 * @author Valentin Ponochevniy
 */
@Slf4j
class LocalizationService {

    def localizedLinkService

    /**
     *
     * Resolves translatable property dynamically using reflection
     * This one is slow, please consider to refactor your translatable properties
     * if you are trying to use it!
     * TODO: grails dev hot reload is broken on gsp pages if changes are detected
     * @param target - a target domain object
     * @param propertyName - a translatable property name
     * @param fallbackPropertyName - a fallback property name for english language
     * @param fallbackMsg - a fallback message or an error message to display
     * @return {@link String} translation
     */
    public <T> String getLocalizedProperty(T target, String propertyName, String fallbackPropertyName = "",
                                                          String fallbackMsg = "") {
        String prop
        String fieldName = "get" + MetaClassHelper.capitalize(propertyName) + DomainObjectUtil.mapKeyLanguage

        prop = invokeTranslationMethod(target, fieldName)
        if (!prop && fallbackPropertyName) {
            prop = invokeTranslationMethod(target, "get" + MetaClassHelper.capitalize(fallbackPropertyName) + "EN")
        }

        return prop ? prop : fallbackMsg
    }

    /**
     * Gets a link with a proper translation
     *
     * @param target - a target domain object
     * @param propertyName - a translatable property name
     * @param fallbackPropertyName - a fallback property name for english language
     * @param fallbackMsg - a fallback message or an error message to display
     * @return {@link String} translated link
     */
    String getLocalizedPropertyLink(Object target, String propertyName, String fallbackPropertyName = "",
                                    String fallbackMsg = "") {
        String translation = getLocalizedProperty(target, propertyName, fallbackPropertyName,
                fallbackMsg)
        return translation ? localizedLinkService.getTransformStringIdsToLinks(translation) : null
    }

    String getLocalizedPropertyLink(String translatedProperty) {
        return translatedProperty ? localizedLinkService.getTransformStringIdsToLinks(translatedProperty) : null
    }

    @CompileStatic
    private static String invokeTranslationMethod(Object target, String fieldName) {
        String result = null
        try {
            Method method = target.class.getDeclaredMethod(fieldName, null)
            result = method.invoke(target)
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            if (log.isTraceEnabled()) {
                log.trace("Error getting translation: ", e)
            }
        }
        return result
    }

}
