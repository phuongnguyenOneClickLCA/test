grails.views.enable.jsessionid = false // This it to allow session id in url, if no cookies
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = true
grails.mime.types = [html         : [
        'text/html',
        'application/xhtml+xml'
],
                     xml          : [
                             'text/xml',
                             'application/xml'
                     ],
                     text         : 'text/plain',
                     js           : 'text/javascript',
                     rss          : 'application/rss+xml',
                     atom         : 'application/atom+xml',
                     css          : 'text/css',
                     csv          : 'text/csv',
                     all          : '*/*',
                     json         : [
                             'application/json',
                             'text/json'
                     ],
                     form         : 'application/x-www-form-urlencoded',
                     multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = [
        '/images/*',
        '/css/*',
        '/js/*',
        '/plugins/*',
        '/img/*',
        '/fonts/*'
]

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
grails.converters.json.default.deep = true

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
// grails.hibernate.cache.queries = true

environments {
    development {
        grails.logging.jul.usebridge = true
        grails.plugin.console.enabled = true
    }
    production {
        grails.logging.jul.usebridge = false
    }
    local {
        grails.logging.jul.usebridge = true
        disable.auto.recompile = false
        grails.gsp.enable.reload = true
    }
}

// Spring Security Configuration
grails.plugin.springsecurity.filterChain.chainMap = [
        //Stateless chain
        [
                pattern: '/api/login',
                filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter,-csrfFilter'
        ],
        [
                pattern: '/api/validate',
                filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter,-csrfFilter'
        ],
        [
                pattern: '/importMapperFile',
                /*
                   The following filters are needed here: bearerTokenAuthenticationFilter, anonymousAuthenticationFilter, restTokenValidationFilter, restExceptionTranslationFilter, filterInvocationInterceptor.
                   Unfortunately it isn't possible to write something like:
                   filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'
                   as custom 'csrfFilter' will be also added to the filter chain.
                */
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/api/startCalculationOnWeb',
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/api/runCalc',
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/api/startCalculationRequest',
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/importResults',
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/api/getCalculationResults',
                filters: 'JOINED_FILTERS,-securityRequestHolderFilter,-securityContextPersistenceFilter,-csrfFilter,-logoutFilter,-authenticationProcessingFilter,-restAuthenticationFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-httpPutFormContentFilter,-exceptionTranslationFilter'
        ],
        [
                pattern: '/sec/user/form/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/user/save',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/account/form/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/account/save',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/account/addAccountImage',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/projectTemplate/linkProjectTemplatesToLicense',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/projectTemplate/saveProjectTemplate',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/admin/projectTemplate/managePublicTemplate',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/projectTemplate/removeProjectTemplate',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/entity/show',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/entity/show/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/entity/form',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/entity/save',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/task/form/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/task/save',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/note/form/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/note/save',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/register',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sendReset',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/login/authenticate',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        [
                pattern: '/sec/license/form/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ],
        //Traditional chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter,-csrfFilter'
        ]
]

grails.plugin.springsecurity.rest.token.validation.enableAnonymousAccess = true
grails.plugin.springsecurity.rest.login.active = true
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/login'
grails.plugin.springsecurity.rest.token.validation.endpointUrl = '/api/validate'
grails.plugin.springsecurity.rest.login.failureStatusCode = 401
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 28800
grails.plugin.springsecurity.rest.token.storage.jwt.secret = '5v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)'
grails.plugin.springsecurity.sch.strategyName = org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
grails.plugin.springsecurity.adh.errorPage = '/index/deniedError'
grails.plugin.springsecurity.adh.ajaxErrorPage = '/index/deniedError'

grails.plugin.springsecurity.rememberMe.cookieName = '360optimi_remember_me'
grails.plugin.springsecurity.rememberMe.alwaysRemember = false //if true uses remember me even if no chkbox is in the form
grails.plugin.springsecurity.rememberMe.useSecureCookie = true
grails.plugin.springsecurity.useSessionFixationPrevention = true
grails.plugin.springsecurity.rememberMe.key = '360optimiApp' // should be unique per application
grails.plugin.springsecurity.auth.loginFormUrl = '/login'
grails.plugin.springsecurity.password.algorithm = 'SHA-256'
grails.plugin.springsecurity.password.hash.iterations = 1
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'
grails.plugin.springsecurity.rejectPublicInvocations = false
grails.plugin.springsecurity.successHandler.alwaysUseDefault = false
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/authfail'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.bionova.optimi.core.domain.mongo.User'
grails.plugin.springsecurity.authority.className = 'com.bionova.optimi.core.domain.Role'
grails.plugin.springsecurity.useHttpSessionEventPublisher = true
grails.plugin.springsecurity.useSecurityEventListener = true
grails.plugin.springsecurity.logout.handlerNames = ['customSessionLogoutHandler','securityContextLogoutHandler', 'rememberMeServices']
grails.plugin.springsecurity.roleHierarchy = '''
   ROLE_SYSTEM_ADMIN > ROLE_SUPER_USER
   ROLE_SUPER_USER > ROLE_DEVELOPER
   ROLE_DEVELOPER > ROLE_DATA_MGR
   ROLE_DATA_MGR > ROLE_CONSULTANT
   ROLE_CONSULTANT > ROLE_SALES_VIEW
   ROLE_SALES_VIEW > ROLE_AUTHENTICATED
'''
grails.plugin.springsecurity.apf.storeLastUsername = true
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.interceptUrlMap = [
        [pattern: '/sec/epdRequest/staticRequestDetails', access: ['permitAll']],
        [pattern: '/sec/epdRequest/yourEpdRequestList', access: ['permitAll']],
        [pattern: '/sec/admin/activeUsers/list', access: ['ROLE_SALES_VIEW']],
        [pattern: '/sec/user/list', access: ['ROLE_SALES_VIEW']],
        [pattern: '/sec/account/accountCatalogues/**', access: ['ROLE_SALES_VIEW']],
        [pattern: '/sec/admin/userDataRequest/**', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/import/**', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/dbCleanUp/corruptedData', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/resourceType/upstreamDBAverages', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/recognitionRuleset/**', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/adaptiveRecognitionData/**', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/construction/constructionsManagementPage', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/carbonDesigner/constructionUpload', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/carbonDesigner3D/buildingTypesUpload', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/carbonDesigner3D/manageCarbonDesigner3DScenarios', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/oneClickLcaApi/manualMappingJSON', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/resourceType/benchmark', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/resourceType/index', access: ['ROLE_DATA_MGR']],
        [pattern: '/sec/admin/**', access: ['ROLE_DEVELOPER']],
        [pattern: '/sec/test/**', access: ['permitAll']],
        [pattern: '/sec/**', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/importResults', access: ['permitAll']],
        [pattern: '/storeSplitFiltersInSession', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/login', access: ['permitAll']],
        [pattern: '/login.', access: ['permitAll']],
        [pattern: '/login*', access: ['permitAll']],
        [pattern: '/actuator/**', access: ['permitAll']],
        [pattern: '/actuator', access: ['permitAll']],
        [pattern: '/health/**', access: ['permitAll']],
        [pattern: '/health', access: ['permitAll']],
        [pattern: '/steptwoauth/**', access: ['ROLE_PRE_AUTH']],
        [pattern: '/authenticatebytoken', access: ['ROLE_PRE_AUTH']],
        [pattern: '/nonValidatedAccountHandler', access: ['permitAll']],
        [pattern: '/logout/**', access: ['permitAll']],
        [pattern: '/register', access: ['permitAll']],
        [pattern: '/message', access: ['permitAll']],
        [pattern: '/reactivate', access: ['permitAll']],
        [pattern: '/reactivateinside', access: ['permitAll']],
        [pattern: '/querytask', access: ['permitAll']],
        [pattern: '/querytaskForm', access: ['permitAll']],
        [pattern: '/taskform', access: ['permitAll']],
        [pattern: '/querytasksave', access: ['permitAll']],
        [pattern: '/tasksave', access: ['permitAll']],
        [pattern: '/enable', access: ['permitAll']],
        [pattern: '/monitoring', access: ['ROLE_DEVELOPER']],
        [pattern: '/addresource', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/previousperiods', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/resources', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/addcompositerow', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/copyentityanswers', access: ['ROLE_AUTHENTICATED']],
//        [pattern: '/getrsetvalue', access: ['ROLE_AUTHENTICATED']], // Hung: comment out since it's not in use anywhere 19.02.2021. Remove after few months
//        [pattern: '/injectrsetvalue', access: ['ROLE_AUTHENTICATED']], // Hung: to be deprecated. Can be removed after few months 12.02.2021
        [pattern: '/readfecepd', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/otherdesignsdropdown', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/jsonresources', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/entityimage', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/removefile', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/designresults', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/profiles', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/rendersourcelisting', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/rendercollapserdata', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/rendergroupedbydata', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/renderdenieddatasets', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/rendersystemtrainingdata', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/languagetextfield', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/languagetextarea', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/sendreset', access: ['permitAll']],
        [pattern: '/resetform', access: ['permitAll']],
        [pattern: '/reset', access: ['permitAll']],
        [pattern: '/authfail', access: ['permitAll']],
        [pattern: '/importMapperFile', access: ['permitAll']],
        [pattern: '/', access: ['permitAll']],
        [pattern: '/form.gsp', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/img/**', access: ['permitAll']],
        [pattern: '/jawr/**', access: ['permitAll']],
        [pattern: '/jsoneditor/**', access: ['permitAll']],
        [pattern: '/fonts/**', access: ['permitAll']],
        [pattern: '/unitchangewarning', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/filterchoices', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/getquickfilters', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/saveuserfilters', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/getnestedfilters', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/renderresultcomparepercategory', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/saveuserinput', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/calculatecost', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/dataloadingfeatureresources', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/dataloadingfeatureotherquestionvalue', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/changeclass', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/saveimportmappermappings', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/sustainablealternatives', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/compiledreportdesigns', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/compiledreportdownloadlink', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/constructionresources', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/multidataajaxwarningforcompare', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/renderRSEnv', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/logjserror', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/renderCombinedDisplayFields', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/deleteifcdataset', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/renderDatasetInformation', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/splitOrChangeDataset', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/splitDataset', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/importMappingFile', access: ['permitAll']],
        [pattern: '/importSpecialRules', access: ['permitAll']],
        [pattern: '/dumpIndicatorBenchmarks', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/storeSplitFiltersInSession', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/changeResourceName', access: ['ROLE_SUPER_USER']],
        [pattern: '/sessionInformation', access: ['permitAll']],
        [pattern: '/maintainActivity', access: ['permitAll']],
        [pattern: '/api/getCalculationResults', access: ['permitAll']],
        [pattern: '/api/login', access: ['permitAll']],
        [pattern: '/api/getResourceLibrary', access: ['ROLE_AUTHENTICATED']],

        [pattern: '/api/autocase', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/api/getBenchmarks', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/api/startCalculationOnWeb', access: ['permitAll']],
        [pattern: '/api/startCalculationRequest', access: ['permitAll']],
        [pattern: '/api/runCalc', access: ['ROLE_DEVELOPER']],
        [pattern: '/console/**', access: ['ROLE_DEVELOPER']],
        [pattern: '/static/console/**', access: ['ROLE_SUPER_USER']],
        [pattern: '/cd/**', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/tdl/**', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/datacard/**', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/getRecipesForDataLoadingFromFile', access: ['ROLE_AUTHENTICATED']],
        [pattern: '/exportEPDJson', access: ['ROLE_AUTHENTICATED']]

]

grails.plugins.remotepagination.enableBootstrap=true

grails.controllers.defaultScope = "singleton"

grails.mail.poolSize = 1
spring.task.scheduling.pool.size=2
grails {
    mail {
        host = "localhost"
        port = 25
    }
}

grails.gorm.default.mapping = {
    autowire true
}