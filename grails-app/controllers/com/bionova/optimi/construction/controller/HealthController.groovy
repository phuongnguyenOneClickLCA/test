package com.bionova.optimi.construction.controller

import grails.converters.JSON

class HealthController {
    def optimiHealthMonitor

    def index() {
        render(optimiHealthMonitor.health() as JSON)
    }
}
