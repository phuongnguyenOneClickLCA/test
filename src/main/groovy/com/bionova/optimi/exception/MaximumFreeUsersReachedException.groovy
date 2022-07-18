package com.bionova.optimi.exception

import org.springframework.security.core.AuthenticationException

class MaximumFreeUsersReachedException extends AuthenticationException {
    public MaximumFreeUsersReachedException(String message) {
        super(message)
    }
}
