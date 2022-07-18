/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.UserRegistration

/**
 * @author Pasi-Markus Mäkelä
 */
class OptimiSecurityService {

    def tokenOk(String token) {
        boolean tokenOk = false
        UserRegistration userRegistration = UserRegistration.findByToken(token)

        if (userRegistration) {
            tokenOk = true
        }
        return tokenOk
    }
}
