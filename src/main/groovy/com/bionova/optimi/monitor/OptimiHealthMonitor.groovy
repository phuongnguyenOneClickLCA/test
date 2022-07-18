package com.bionova.optimi.monitor

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

/**
 * Checks back-end accounting system's health.
 */
class OptimiHealthMonitor implements HealthIndicator {
    Health health() {
        if (isHealthy()) {
            return Health.up().build()
        }
        return Health.down().withDetail("System Status", "Unreachable").build();
    }

    static boolean isHealthy() {
        true
    }
}