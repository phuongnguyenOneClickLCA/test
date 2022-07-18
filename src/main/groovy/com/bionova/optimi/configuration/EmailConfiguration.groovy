package com.bionova.optimi.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "oneclicklca.email")
class EmailConfiguration {
    List<String> protectedEmailDomains = []
    List<String> dbFixNotificationEmails = []
    String noReplyEmail
    String notificationEmail
    String testsAutomationEmail
    String testingEmail
    String registrationEmail
    String contactEmail
    String supportEmail
    String activationFailedEmail
    String kpiReportEmail
    String userLicenceNotificationEmail
    String salesEmail
    String salesLeadEmail
    String helloEmail
    String accountEmail
    String devEmail

    boolean isProtectedEmail(String email) {
        if (!email) {
            return Boolean.FALSE.booleanValue()
        }
        return protectedEmailDomains.any { emailDomain ->
            email.contains('@') && email.substring(email.lastIndexOf('@') + 1).equalsIgnoreCase(emailDomain)
        }
    }
}
