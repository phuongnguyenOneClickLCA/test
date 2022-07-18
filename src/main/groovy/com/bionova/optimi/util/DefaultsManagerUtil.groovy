package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.DefaultValue
import com.bionova.optimi.core.domain.mongo.DefaultValueSet
import com.bionova.optimi.core.domain.mongo.Entity

/**
 * Created by pmm on 16/05/16.
 */
class DefaultsManagerUtil {

    public static String isDefaultValueUsed(Entity entity) {
        String usedDefaultValueAndSet

        if (entity && entity.defaults) {
            entity.defaults.each { String defaultSet, String defaultValue ->
                if (defaultValue) {
                    if (usedDefaultValueAndSet) {
                        usedDefaultValueAndSet = "${usedDefaultValueAndSet}, ${defaultSet}"
                    } else {
                        usedDefaultValueAndSet = "${defaultSet}"
                    }
                }
            }
        }
        return usedDefaultValueAndSet
    }

    public static String getUiStringForDefaultValueUsed(DefaultValue defaultValue, DefaultValueSet defaultValueSet) {
        String usedDefaultValueAndSet

        if (defaultValue && defaultValueSet) {
            usedDefaultValueAndSet = defaultValue?.localizedName + ", " + defaultValueSet?.localizedName
        }
        return usedDefaultValueAndSet
    }
}
