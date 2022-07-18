/*
 *
 * Copyright (c) 2012 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.User;

/**
 * @author Pasi-Markus Mäkelä
 */
class DummySpringSecurityService {
    User user

    def setCurrentUser(User user) {
        this.user = user
    }

    def getCurrentUser() {
        return user
    }
}
