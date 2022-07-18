import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.core.spi.CyclicBufferTracker
import ch.qos.logback.core.util.FileSize
import com.bionova.optimi.log.OptimiSMTPAppender
import grails.util.BuildSettings
import grails.util.Environment

import ch.qos.logback.classic.PatternLayout
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
import static ch.qos.logback.core.spi.FilterReply.DENY
import ch.qos.logback.classic.filter.ThresholdFilter

//config is not initialized yet
Map emailConfiguration = [:]
emailConfiguration.put("notificationEmail", '360optimi-notification@oneclicklca.com')
emailConfiguration.put("contactEmail", 'contact@oneclicklca.com')

appender("STDOUT", ConsoleAppender) {
    encoder(LayoutWrappingEncoder) {
        layout(PatternLayout) {
            pattern = '%c %X{hostname} %d{dd.MM.yyyy HH:mm:ss,SSS} %-5p - %m %n'
        }
    }
}

appender("INFO_FILE", RollingFileAppender) {
    /*filter(LevelFilter) {
    level = ERROR
    onMatch = ACCEPT
    onMismatch = DENY
    }*/

    filter(LevelFilter) {
        level = DEBUG
        onMatch = DENY
        onMismatch = ACCEPT
    }
    append = true

    encoder(PatternLayoutEncoder) {
        pattern = "%level %X{hostname} %logger - %d{dd.MM.yyyy HH:mm:ss,SSS} %msg%n"
    }

    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "/var/www/static/logs/360optimi-%d{dd.MM.yyyy}.log"
        maxHistory = 7
        // this size cap will cause the log to disappear at midnight if it passes the threshold.
//        totalSizeCap = FileSize.valueOf("2GB")
    }
}
logger('com.bionova.optimi', INFO, ['INFO_FILE']) //grails
logger('grails.app', DEBUG, ['INFO_FILE', 'STDOUT'])
logger('grails.web.pages', INFO, ['INFO_FILE'])

logger('com.bionova.optimi', ERROR, ['INFO_FILE']) //grails
logger('grails.app', ERROR, ['INFO_FILE'])
logger('grails.web.pages', ERROR, ['INFO_FILE'])


appender("DEBUG_FILE", RollingFileAppender) {
    filter(LevelFilter) {
        level = DEBUG
        onMatch = ACCEPT
        onMismatch = DENY
    }
    append = true

    encoder(PatternLayoutEncoder) {
        pattern = "%level %X{hostname} %logger - %d{dd.MM.yyyy HH:mm:ss,SSS} %msg%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "/var/www/static/logs/360optimi-%d{dd.MM.yyyy}.debug.log"
        maxHistory = 7
        // this size cap will cause the log to disappear at midnight if it passes the threshold.
//        totalSizeCap = FileSize.valueOf("2GB")
    }
}

logger('com.bionova.optimi', DEBUG, ['DEBUG_FILE']) //grails
logger('grails.app', DEBUG, ['DEBUG_FILE'])
logger('grails.web.pages', DEBUG, ['DEBUG_FILE'])
logger("org.springframework.security", DEBUG, ['DEBUG_FILE'], false)
logger("grails.plugin.springsecurity", DEBUG, ['DEBUG_FILE'], false)
logger("org.pac4j", DEBUG, ['DEBUG_FILE'], false)

logger('org.apache.catalina.session.StandardManager', OFF)

logger 'org.springframework.security', DEBUG
logger 'grails.plugin.springsecurity', TRACE

if (Environment.current == Environment.PRODUCTION) {
    appender("mailAppender", SMTPAppender) {
        evaluator(OptimiSMTPAppender) {
            marker = "ERROR"
        }
        layout(PatternLayout) {
            pattern = '%c %X{hostname} %d{dd.MM.yyyy HH:mm:ss,SSS} %-5p - %m %n'
        }
        from = "${emailConfiguration?.contactEmail ?: 'contact@oneclicklca.com'}"
        to = "${emailConfiguration?.notificationEmail ?: '360optimi-notification@oneclicklca.com'}"
        subject = "PROD: Exception in 360optimi.com"
        SMTPHost = "localhost"
        filter(ThresholdFilter) {
            level = ERROR
        }
        cyclicBufferTracker(CyclicBufferTracker) {
            bufferSize = 50
        }
    }
    root(ERROR, ["mailAppender"])
    logger('com.bionova.optimi.grails', DEBUG, ['mailAppender']) //grails
    logger('com.bionova.optimi.util', DEBUG, ['mailAppender']) //util
    logger('com.bionova.optimi.excelimport', DEBUG, ['mailAppender']) //excelimport
} else if (Environment.current == Environment.DEVELOPMENT) {
    appender("devMailAppender", SMTPAppender) {
        evaluator(OptimiSMTPAppender) {
            marker = "ERROR"
        }
        layout(PatternLayout) {
            pattern = '%c %X{hostname} %d{dd.MM.yyyy HH:mm:ss,SSS} %-5p - %m %n'
        }
        from = "dev@oneclicklca.com"
        to = "${emailConfiguration?.notificationEmail ?: '360optimi-notification@oneclicklca.com'}"
        subject = "DEV: Exception in dev.360optimi.com"
        SMTPHost = "localhost"
        filter(ThresholdFilter) {
            level = ERROR
        }
        cyclicBufferTracker(CyclicBufferTracker) {
            bufferSize = 50
        }
    }
    root(ERROR, ["devMailAppender"])
    logger('com.bionova.optimi.grails', DEBUG, ['devMailAppender']) //grails
    logger('com.bionova.optimi.util', DEBUG, ['devMailAppender']) //util
    logger('com.bionova.optimi.excelimport', DEBUG, ['devMailAppender']) //excelimport
} else {
    appender('STDOUT', ConsoleAppender) {
        println("This doesn' t appear.")
        encoder(PatternLayoutEncoder) {
            pattern = "%level %X{hostname} %logger - %msg%n"
        }
    }

    root(DEBUG, ['STDOUT'])

    def targetDir = BuildSettings.TARGET_DIR

    if (targetDir) {
        appender("FULL_STACKTRACE", FileAppender) {

            file = "${targetDir}/stacktrace.log"
            append = true
            encoder(PatternLayoutEncoder) {
                pattern = "%level %X{hostname} %logger - %msg%n"
            }
        }
        logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
    }
}

// See http://logback.qos.ch/manual/groovy.html for details on configuration