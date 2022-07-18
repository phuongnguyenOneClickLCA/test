/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.util

import grails.util.Holders
import org.springframework.context.ApplicationContext

/**
 * @author Pasi-Markus Mäkelä
 */
class SpringUtil {
    private static ApplicationContext getApplicationContext() {
        return (ApplicationContext) Holders.applicationContext
    }
    
    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName)
    }
}
