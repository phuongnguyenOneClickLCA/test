package com.bionova.optimi

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.transform.CompileStatic
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@CompileStatic
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
class Application extends GrailsAutoConfiguration {

    public static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}