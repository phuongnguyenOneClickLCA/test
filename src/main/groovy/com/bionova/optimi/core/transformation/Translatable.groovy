package com.bionova.optimi.core.transformation


import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * A marker that indicates a translation map
 *
 * @author Valentin Ponochevniy
 */
@Retention (RetentionPolicy.SOURCE)
@Target ([ElementType.FIELD])
@interface Translatable {

    /**
     * An alias that will be generated after the getter part: getLocalized<alias>.
     * May be used to customize a result method name.
     */
    String alias() default "";
}