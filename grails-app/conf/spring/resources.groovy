import com.bionova.optimi.CustomSessionLogoutHandler
import com.bionova.optimi.core.service.MongoUserDetailsService
import com.bionova.optimi.core.util.ImageUtil
import com.bionova.optimi.core.util.LocaleResolverUtil
import com.bionova.optimi.core.util.LoggerUtil
import com.bionova.optimi.frenchTools.fec.DataCompUtilFec
import com.bionova.optimi.frenchTools.fec.EntreeProjetUtilFec
import com.bionova.optimi.frenchTools.fec.SortieProjetUtilFec
import com.bionova.optimi.frenchTools.re2020.DataCompUtilRe2020
import com.bionova.optimi.frenchTools.re2020.EntreeProjetUtilRe2020
import com.bionova.optimi.frenchTools.re2020.RsEnvUtilRe2020
import com.bionova.optimi.frenchTools.re2020.SortieProjetUtilRe2020
import com.bionova.optimi.grails.OptimiMessageSource
import com.bionova.optimi.grails.OptimiParamsAwareLocaleChangeInterceptor
import com.bionova.optimi.grails.TrimWhitespacesFilter
import com.bionova.optimi.monitor.OptimiHealthMonitor
import com.bionova.optimi.security.BearerTokenAuthenticationFilter
import com.bionova.optimi.security.CustomAuthenticationSuccessListener
import com.bionova.optimi.security.CustomAuthenticationFailureListener
import com.bionova.optimi.security.CustomConcurrentSessionControlAuthenticationStrategy
import com.bionova.optimi.security.CustomRequestHolderAuthenticationFilter
import com.bionova.optimi.security.CustomRestAuthenticationSuccessHandler
import com.bionova.optimi.security.HostValidationFilter
import com.bionova.optimi.security.SessionEndedListener
import com.bionova.optimi.security.UserPasswordEncoderListener
import com.bionova.optimi.util.CalculateDifferenceUtil
import com.bionova.optimi.util.DenominatorUtil
import com.bionova.optimi.util.ErrorMessageUtil
import com.bionova.optimi.util.FileUtil
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.util.OptimiMultipartResolver
import com.bionova.optimi.util.OptimiStringUtils
import com.bionova.optimi.util.PortfolioUtil
import com.bionova.optimi.util.ResultFormattingResolver
import com.bionova.optimi.util.UnitConversionUtil
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.aop.scope.ScopedProxyFactoryBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.security.web.session.ConcurrentSessionFilter
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import com.bionova.optimi.util.ResourceFilterCriteriaUtil

// Place your Spring DSL code here
beans = {
    def conf = SpringSecurityUtils.securityConfig

    userPasswordEncoderListener(UserPasswordEncoderListener)

    userDetailsService(MongoUserDetailsService) {
        springSecurityService = ref('springSecurityService')
    }

    bearerTokenAuthenticationFilter(BearerTokenAuthenticationFilter) {
        springSecurityService = ref('springSecurityService')
    }

    myFilterDeregistrationBean(FilterRegistrationBean) {
        filter = ref('bearerTokenAuthenticationFilter')
        enabled = false
    }

    // custom authentication processing filter for channelFeature
    authenticationProcessingFilter(CustomRequestHolderAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        rememberMeServices = ref('rememberMeServices')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        userService = ref('userService')
        configurationService = ref('configurationService')
        filterProcessesUrl = conf.apf.filterProcessesUrl
        usernameParameter = conf.apf.usernameParameter
        passwordParameter = conf.apf.passwordParameter
        continueChainBeforeSuccessfulAuthentication = conf.apf.continueChainBeforeSuccessfulAuthentication
        allowSessionCreation = conf.apf.allowSessionCreation
        storeLastUsername = Boolean.TRUE
        postOnly = conf.apf.postOnly
    }

    newCalculationServiceProxy(ScopedProxyFactoryBean) {
        targetBeanName = 'newCalculationService'
        proxyTargetClass = true
    }

    detailReportServiceProxy(ScopedProxyFactoryBean) {
        targetBeanName = 'detailReportService'
        proxyTargetClass = true
    }

    localeResolver(SessionLocaleResolver) {
        defaultLocale = new Locale("en", "US")
        Locale.setDefault(defaultLocale)
    }

    loggerUtil(LoggerUtil)

    resultFormattingResolver(ResultFormattingResolver) {
        denominatorService = ref('denominatorService')
        indicatorReportService = ref('indicatorReportService')
        userService = ref('userService')
    }

    denominatorUtil(DenominatorUtil) {
        datasetService = ref('datasetService')
        userService = ref('userService')
        resultFormattingResolver = ref('resultFormattingResolver')
        queryService = ref('queryService')
        unitConversionUtil = ref('unitConversionUtil')
        valueReferenceService = ref('valueReferenceService')
        denominatorService = ref('denominatorService')
        resultManipulatorService = ref('resultManipulatorService')
    }


    localeResolverUtil(LocaleResolverUtil) {
        localeResolver = ref('localeResolver')
        userService = ref('userService')
        channelFeatureService = ref('channelFeatureService')
        flashService = ref('flashService')
        loggerUtil = ref('loggerUtil')
    }

    messageSource(OptimiMessageSource)

    sessionRegistry(SessionRegistryImpl)
    customSessionLogoutHandler(CustomSessionLogoutHandler, ref('sessionRegistry'))

    sessionFixationProtectionStrategy(SessionFixationProtectionStrategy) {
        migrateSessionAttributes = true
        alwaysCreateSession = true
    }

    concurrentSessionControlAuthenticationStrategy(CustomConcurrentSessionControlAuthenticationStrategy, ref('sessionRegistry')) {
        maximumSessions = 1
    }

    registerSessionAuthenticationStrategy(RegisterSessionAuthenticationStrategy, ref('sessionRegistry'))
    sessionAuthenticationStrategy(CompositeSessionAuthenticationStrategy, [ref('concurrentSessionControlAuthenticationStrategy'), ref('sessionFixationProtectionStrategy'), ref('registerSessionAuthenticationStrategy')])
    String invalidSessionUrl = conf.auth.loginFormUrl
    SimpleRedirectSessionInformationExpiredStrategy expiredSessionStrategy = new SimpleRedirectSessionInformationExpiredStrategy(invalidSessionUrl)
    concurrentSessionFilter(ConcurrentSessionFilter, ref('sessionRegistry'), expiredSessionStrategy)

    localeChangeInterceptor(OptimiParamsAwareLocaleChangeInterceptor) {
        paramName = "lang"
        loggerUtil = ref('loggerUtil')
    }

    errorMessageUtil(ErrorMessageUtil)

    unitConversionUtil(UnitConversionUtil) {
        userService = ref('userService')
        errorMessageUtil = ref('errorMessageUtil')
        messageSource = ref('messageSource')
    }

    resourceFilterCriteriaUtil(ResourceFilterCriteriaUtil) {
        flashService = ref('flashService')
        indicatorQueryService = ref('indicatorQueryService')
    }

    portfolioUtil(PortfolioUtil) {
        localeResolverUtil = ref('localeResolverUtil')
        messageSource = ref('messageSource')
        grailsApplication = ref('grailsApplication')
        denominatorUtil = ref('denominatorUtil')
        userService = ref('userService')
    }

    calculateDifferenceUtil(CalculateDifferenceUtil)
    optimiStringUtils(OptimiStringUtils)

    fileUtil(FileUtil) {
        loggerUtil = ref('loggerUtil')
        queryService = ref('queryService')
        errorMessageUtil = ref('errorMessageUtil')
        messageSource = ref('messageSource')
        re2020Service = ref('re2020Service')
        fecService = ref('fecService')
    }

    numberUtil(NumberUtil) {
        loggerUtil = ref('loggerUtil')
        flashService = ref('flashService')
    }

    imageUtil(ImageUtil) {
        flashService = ref('flashService')
        loggerUtil = ref('loggerUtil')
    }

    dataCompUtilFec(DataCompUtilFec) {
        datasetService = ref('datasetService')
        xmlService = ref('xmlService')
        numberUtil = ref('numberUtil')
        optimiResourceService = ref('optimiResourceService')
    }

    entreeProjetUtilFec(EntreeProjetUtilFec) {
        datasetService = ref('datasetService')
        numberUtil = ref('numberUtil')
        xmlService = ref('xmlService')
        newCalculationServiceProxy = ref('newCalculationServiceProxy')
        stringUtilsService = ref('stringUtilsService')
        flashService = ref('flashService')
        loggerUtil = ref('loggerUtil')
        constructionService = ref('constructionService')
        optimiResourceService = ref('optimiResourceService')
        unitConversionUtil = ref('unitConversionUtil')
        valueReferenceService = ref('valueReferenceService')
    }

    sortieProjetUtilFec(SortieProjetUtilFec) {
        calculationResultService = ref('calculationResultService')
        datasetService = ref('datasetService')
        numberUtil = ref('numberUtil')
        flashService = ref('flashService')
        loggerUtil = ref('loggerUtil')
    }

    dataCompUtilRe2020(DataCompUtilRe2020) {
        datasetService = ref('datasetService')
        xmlService = ref('xmlService')
        numberUtil = ref('numberUtil')
        optimiResourceService = ref('optimiResourceService')
        calculationResultService = ref('calculationResultService')
        re2020Service = ref('re2020Service')
    }

    entreeProjetUtilRe2020(EntreeProjetUtilRe2020) {
        datasetService = ref('datasetService')
        numberUtil = ref('numberUtil')
        xmlService = ref('xmlService')
        newCalculationServiceProxy = ref('newCalculationServiceProxy')
        re2020Service = ref('re2020Service')
        stringUtilsService = ref('stringUtilsService')
        optimiResourceService = ref('optimiResourceService')
    }

    sortieProjetUtilRe2020(SortieProjetUtilRe2020) {
        calculationResultService = ref('calculationResultService')
        datasetService = ref('datasetService')
        numberUtil = ref('numberUtil')
    }

    rsEnvUtilRe2020(RsEnvUtilRe2020) {
        datasetService = ref('datasetService')
        numberUtil = ref('numberUtil')
    }

    multipartResolver(OptimiMultipartResolver) {
        errorMessageUtil = ref('errorMessageUtil')
        messageSource = ref('messageSource')
        loggerUtil = ref('loggerUtil')
    }

    restAuthenticationSuccessHandler(CustomRestAuthenticationSuccessHandler) {
        renderer = ref('accessTokenJsonRenderer')
    }
    sessionEndedListener(SessionEndedListener)

    customAuthenticationSuccessListener(CustomAuthenticationSuccessListener) {
        loginService = ref('loginService')
        userService = ref('userService')
    }

    customAuthenticationFailureListener(CustomAuthenticationFailureListener) {
        loginService = ref('loginService')
    }
    // mongoTemplate(MongoTemplate)

    trimWhitespacesFilter(TrimWhitespacesFilter)

    optimiHealthMonitor(OptimiHealthMonitor)

    csrfTokenRepository(HttpSessionCsrfTokenRepository) {
        parameterName = "_csrf"
        headerName = "X-CSRF-TOKEN"
    }

    csrfFilter(CsrfFilter, ref('csrfTokenRepository'))

    csrfFilterDeregistrationBean(FilterRegistrationBean) {
        filter = ref('csrfFilter')
        enabled = false
    }

    hostValidationFilter(HostValidationFilter, ref('domainConfiguration'), ref('userService'))

    hostValidationFilterDeregistrationBean(FilterRegistrationBean) {
        filter = ref('hostValidationFilter')
        enabled = false
    }
}
