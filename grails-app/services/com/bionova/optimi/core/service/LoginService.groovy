package com.bionova.optimi.core.service

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache

import javax.annotation.PostConstruct
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class LoginService {
    private static final int LOGIN_ATTEMPTS_LIMIT = 5
    private static final int LOGIN_ATTEMPTS_DEFAULT_VALUE = 0

    UserService userService

    private LoadingCache<String, Integer> loginAttemptsCache

    @PostConstruct
    private void init() {
        loginAttemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            Integer load(String key) throws Exception {
                return LOGIN_ATTEMPTS_DEFAULT_VALUE
            }
        })
    }

    void loginSucceeded(String username) {
        loginAttemptsCache.invalidate(username)
    }

    void loginFailed(String username) {
        if (username) {
            int loginAttempts

            try {
                loginAttempts = loginAttemptsCache.get(username)
            } catch (ExecutionException e) {
                log.error "The following exception happened during retrieving of '${username}' login attempts: ", e
                loginAttempts = LOGIN_ATTEMPTS_DEFAULT_VALUE
            }

            loginAttempts++

            if (loginAttempts < LOGIN_ATTEMPTS_LIMIT) {
                loginAttemptsCache.put(username, loginAttempts)
            } else {
                if (userService.enableTokenAuthForNextLogin(username)) {
                    loginAttemptsCache.invalidate(username)
                } else {
                    loginAttemptsCache.put(username, loginAttempts)
                }
            }
        }
    }
}
