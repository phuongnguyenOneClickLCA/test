<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
    <g:set var="channelFeatureService" bean="channelFeatureService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <g:render template="/entity/basicinfoView"/>
    </div>
</div>
<sec:ifAnyGranted roles="ROLE_SALES_VIEW">
<div class="container section">
    <h3>
        <g:if test="${channelFeature?.id}">
            <g:message code="admin.channelFeature.modify" />
        </g:if>
        <g:else>
            <g:message code="admin.channelFeature.create" />
        </g:else>
    </h3>

    <div class="sectionbody">

        <g:form action="save" useToken="true">
            <g:hiddenField name="id" value="${channelFeature?.id}" />

            <div class="control-group">
                <label for="name" class="control-label"><g:message code="admin.channelFeature.name" /></label>
                <div class="controls"><opt:textField name="name" value="${channelFeature?.name}"  /></div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="defaultChannel" checked="${channelFeature?.defaultChannel}" /> <g:message code="admin.channelFeature.defaultChannel"/></label>
                </div>
            </div>

            <div class="control-group">
                <label for="customer" class="control-label"><g:message code="admin.channelFeature.customer" /></label>
                <div class="controls"><opt:textField name="customer" value="${channelFeature?.customer}" /></div>
            </div>

            <div class="control-group">
                <label for="token" class="control-label"><g:message code="admin.channelFeature.token" /></label>
                <div class="controls"><opt:textField name="token" value="${channelFeature?.token}" /></div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="disableTwoFactorAuth" checked="${channelFeature?.disableTwoFactorAuth}" /> <g:message code="admin.channelFeature.disableTwoFactorAuth"/></label>
                </div>
            </div>

            <div class="control-group">
                <label for="benchmarkName" class="control-label"><g:message code="admin.channelFeature.benchmarkName" /></label>
                <div class="controls"><opt:textField name="benchmarkName" value="${channelFeature?.benchmarkName}" /></div>
            </div>

            <%--
            <div class="control-group">
                <label for="mailChimpSegmentId" class="control-label"><g:message code="admin.channelFeature.mailChimpSegmentId" /></label>
                <div class="controls"><opt:textField name="mailChimpSegmentId" value="${channelFeature?.mailChimpSegmentId}" /></div>
            </div>

            <div class="control-group">
                <label for="mailChimpListId" class="control-label"><g:message code="admin.channelFeature.mailChimpListId" /></label>
                <div class="controls"><opt:textField name="mailChimpListId" value="${channelFeature?.mailChimpListId}" /></div>
            </div>

            <div class="control-group">
                <label for="mailChimpListId" class="control-label">MailChimp Welcome email list ID</label>
                <div class="controls"><opt:textField name="mailChimpCustomWelcomeListId" value="${channelFeature?.mailChimpCustomWelcomeListId}" /></div>
            </div>

            <div class="control-group">
                <label for="mailChimpNotes" class="control-label"><g:message code="admin.channelFeature.mailChimpNotes" /></label>
                <div class="controls"><opt:textArea name="mailChimpNotes" value="${channelFeature?.mailChimpNotes}" /></div>
            </div>--%>

            <div class="control-group">
                <label for="portfolioIds" class="control-label">
                    <g:message code="admin.channelFeature.portfolioIds" />
                </label>
                <div class="controls">
                    <g:select name="addPortfolioId" from="${portfolios}" optionKey="id" optionValue="${{it.entityName}}" noSelection="['':'']" />
                    <opt:submit name="addPortfolio" value="${message(code: 'add')}" class="btn btn-primary" />
                </div>
                <p>&nbsp;</p>
                <table class="table table-condensed" style="width: 500px;">
                    <tbody>
                    <g:each in="${channelFeatureService.getPortfolios(channelFeature?.portfolioIds)}" var="portfolio">
                        <tr>
                            <td>${portfolio.entityName}</td><td>
                            <td>
                                <opt:link action="save" id="${channelFeature?.id}" params="[removePortfolioId: portfolio.id]" class="btn btn-danger"><g:message code="delete" /></opt:link>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="nameAsRegistrationMotive" checked="${channelFeature?.nameAsRegistrationMotive}" /> <g:message code="admin.channelFeature.nameAsRegistrationMotive"/></label>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="showPoweredBy" checked="${channelFeature?.showPoweredBy}" /> <g:message code="admin.channelFeature.showPoweredBy"/></label>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="hidePublic" checked="${channelFeature?.hidePublic}" /> <g:message code="admin.channelFeature.hidePublic"/></label>
                </div>
            </div>

            <%--<div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="enableHotjarTrack" checked="${channelFeature?.enableHotjarTrack}"/> Enable hotjar tracking and feedback</label>
                </div>
            </div>--%>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="disableUpdateSideBar" checked="${channelFeature?.disableUpdateSideBar}"/> Disable permanent update sidebar</label>
                </div>
            </div>

            <div class="control-group">
                <label for="logo" class="control-label"><g:message code="admin.channelFeature.logo" /></label>
                <div class="controls">
                    <opt:textField name="logo" value="${channelFeature?.logo}" />
                    <g:if test="${channelFeature?.logo}">
                        <img src="${channelFeature.logo}" alt="" /> %{--static image url--}%
                    </g:if>
                </div>
            </div>


            <div class="control-group">
                <label for="backgroundImage" class="control-label">Set a background for your channel login page</label>
                <div class="controls">
                    <opt:textField name="backgroundImage" value="${channelFeature?.backgroundImage}"/>
                    <g:if test="${channelFeature?.backgroundImage}">
                        <img src="${channelFeature.backgroundImage}" alt="backgroundImage" width="150px;" /> %{--static image url--}%
                    </g:if>
                </div>
            </div>

            <div class="control-group">
                <label for="loginPageCss" class="control-label">Add custom css for your login page if you want</label>
                <div class="controls">
                    <opt:textArea name="loginPageCss" value="${channelFeature?.loginPageCss}"/>
                </div>
            </div>

            <div class="control-group">
                <label for="shortcutIcon" class="control-label"><g:message code="admin.channelFeature.shortcutIcon" /></label>
                <div class="controls">
                    <opt:textField name="shortcutIcon" value="${channelFeature?.shortcutIcon}" />
                    <g:if test="${channelFeature?.shortcutIcon}">
                        <img src="${channelFeature.shortcutIcon}" alt="" /> %{--static image url--}%
                    </g:if>
                </div>
            </div>

            <div class="control-group">
                <label for="loginUrl" class="control-label"><g:message code="admin.channelFeature.loginUrl" /></label>
                <div class="controls"><opt:textField name="loginUrl" value="${channelFeature?.loginUrl}" /></div>
            </div>

            <div class="container section">
                <div class="sectionbody">
                    <div class="control-group">
                        <label class="control-label">
                            <g:message code="admin.channelFeature.title" /> (<g:message code="default_language.info" />)
                        </label>
                        <div class="controls">
                            <g:select from="${systemLocales}" name="titleLanguage" noSelection="['':'']"  optionKey="resourceId" optionValue="${{optimiResourceService.getLocalizedName(it)}}" />
                            <input type="button" class="btn btn-primary" value="${message(code:'add')}" onclick="addLanguageInput('titleLanguage', 'title', 'textField', 'title');" />
                        </div>
                    </div>
                    <div class="control-group">
                        <table class="left_alignment" id="title">
                            <g:each in="${channelFeature?.title}" var="title" status="index">
                                <g:if test="${title.value}">
                                    <tr id="title${index}">
                                        <td>${optimiResourceService.getLocalizedName(systemLocales.find{it.resourceId.equals(title.key)})}</td>
                                        <td><g:textField class="input-xlarge" value="${title.value}" name="title[${title.key}]" /></td>
                                        <td><g:link action="removeLocaleAttribute" params="[attribute: 'title', locale: title.key, id: channelFeature?.id]" class="btn btn-danger">${message(code:'delete')}</g:link></td>
                                    </tr>
                                </g:if>
                            </g:each>
                        </table>
                    </div>
                </div>
            </div>

            <div class="container section">
                <div class="sectionbody">
                    <div class="control-group">
                        <label class="control-label">
                            <g:message code="admin.channelFeature.supportContact" /> (<g:message code="default_language.info" />)
                        </label>
                        <div class="controls">
                            <g:select from="${systemLocales}" name="language" noSelection="['':'']"  optionKey="resourceId" optionValue="${{optimiResourceService.getLocalizedName(it)}}" />
                            <input type="button" class="btn btn-primary" value="${message(code:'add')}" onclick="addLanguageInput('language', 'supportContact', 'textArea', 'supportContact');" />
                        </div>
                    </div>
                    <div class="control-group">
                        <table class="left_alignment" id="supportContact">
                            <g:each in="${channelFeature?.supportContact}" var="supportContact" status="index">
                                <g:if test="${supportContact.value}">
                                    <tr id="supportContact${index}">
                                        <td>${optimiResourceService.getLocalizedName(systemLocales.find{it.resourceId.equals(supportContact.key)})}</td>
                                        <td><g:textArea class="input-xlarge" value="${supportContact.value}" name="supportContact[${supportContact.key}]" /></td>
                                        <td><g:link action="removeLocaleAttribute" params="[attribute: 'supportContact', locale: supportContact.key, id: channelFeature?.id]" class="btn btn-danger">${message(code:'delete')}</g:link></td>
                                    </tr>
                                </g:if>
                            </g:each>
                        </table>
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label for="navbarColor" class="control-label"><g:message code="admin.channelFeature.navbarColor" /></label>
                <div class="controls"><opt:textField name="navbarColor" value="${channelFeature?.navbarColor}" /></div>
            </div>

            <div class="control-group">
                <label for="addableEntityClasses" class="control-lable"><g:message code="admin.channelFeature.addableEntityClasses" /></label>
                <div class="controls">
                    <g:select name="addableEntityClasses" from="${entityClassResources}" value="${channelFeature?.addableEntityClasses}"
                        optionValue="${{optimiResourceService.getLocalizedName(it)}}" optionKey="resourceId" multiple="multiple" />
                </div>
            </div>
            <div class="control-group">
                <label for="languagesForChannel" class="control-lable"><g:message code="admin.channelFeature.allowLanguagesForChannel" /></label>
                <div class="controls">
                    <g:select name="languagesForChannel" from="${systemLocales}" value="${channelFeature?.languagesForChannel}"
                              optionValue="${{optimiResourceService.getLocalizedName(it)}}" optionKey="resourceId" multiple="multiple" />
                </div>
            </div>

            <div class="container section">
                <div class="sectionbody">
                    <div class="control-group">
                        <label class="control-label">
                            <g:message code="admin.channelFeature.helpDocumentUrl"  />
                        </label>
                        <div class="controls">
                            <g:select from="${systemLocales}" name="helpLanguage" noSelection="['':'']"  optionKey="resourceId" optionValue="${{optimiResourceService.getLocalizedName(it)}}" />
                            <input type="button" class="btn btn-primary" value="${message(code:'add')}" onclick="addLanguageInput('helpLanguage', 'helpDocumentUrls', 'textField', 'helpDocumentUrl');" />
                        </div>
                    </div>
                    <div class="control-group">
                        <table class="left_alignment" id="helpDocumentUrl">
                            <g:each in="${channelFeature?.helpDocumentUrls}" var="helpDocumentUrl" status="index">
                                <g:if test="${helpDocumentUrl.value}">
                                    <tr id="helpDocumentUrl${index}">
                                        <td>${optimiResourceService.getLocalizedName(systemLocales.find{it.resourceId.equals(helpDocumentUrl.key)})}</td>
                                        <td><g:textField class="input-xlarge" value="${helpDocumentUrl.value}" name="helpDocumentUrls[${helpDocumentUrl.key}]" /></td>
                                        <td><g:link action="removeLocaleAttribute" params="[attribute: 'helpDocumentUrls', locale: helpDocumentUrl.key, id: channelFeature?.id]" class="btn btn-danger">${message(code:'delete')}</g:link></td>
                                    </tr>
                                </g:if>
                            </g:each>
                        </table>
                    </div>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="enableLicenseKeyBox" checked="${channelFeature?.enableLicenseKeyBox}" /> Enable licensekey  box in entities </label>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <label class="checkbox"><g:checkBox name="enableQuoteBar" checked="${channelFeature?.enableQuoteBar}" /> <g:message code="admin.channelFeature.enableQuoteBar"/></label>
                </div>
            </div>


            <div class="control-group">
                <label class="checkbox"><g:checkBox name="showInLoginPage" checked="${channelFeature?.showInLoginPage}" /> Show  channelBrand in overall  login page</label>
            </div>

            <div class="control-group">
                <label class="checkbox"><g:checkBox name="reversePeriods" checked="${channelFeature?.reversePeriods}" /> Reverse operating period display order</label>
            </div>

            <div class="control-group">
                <label class="checkbox"><g:checkBox name="hideEcommerce" checked="${channelFeature?.hideEcommerce}" /> Hide eCommerce</label>
            </div>
            <div class="control-group">
                <label class="checkbox"><g:checkBox name="enableBuildingForChannel" checked="${channelFeature?.enableBuildingForChannel}" /> Enable free building for this channel</label>
            </div>

            <div class="control-group">
                <label class="checkbox"><g:checkBox name="allowFloatingHelp" checked="${channelFeature?.allowFloatingHelp}" /> Allow floating help box</label>
            </div>

            <div class="control-group">
                <label class="checkbox"><g:checkBox name="enableAutomaticTrial" checked="${channelFeature?.enableAutomaticTrial}" /> Enable automatic trial</label>
            </div>

            <div class="control-group">
                <label for="quoteUrl" class="control-label"><g:message code="admin.channelFeature.quoteUrl" /></label>
                <div class="controls"><opt:textField name="quoteUrl" value="${channelFeature?.quoteUrl}" /></div>
            </div>

            <div class="control-group">
                <label for="webinarUrl" class="control-label"><g:message code="admin.channelFeature.webinarUrl" /></label>
                <div class="controls"><opt:textField name="webinarUrl" value="${channelFeature?.webinarUrl}" /></div>
            </div>

            <div class="control-group">
                <label for="expertUrl" class="control-label"><g:message code="admin.channelFeature.expertUrl" /></label>
                <div class="controls"><opt:textField name="expertUrl" value="${channelFeature?.expertUrl}" /></div>
            </div>

            <div class="control-group">
                <label for="trialUrl" class="control-label">Trial URL</label>
                <div class="controls"><opt:textField name="trialUrl" value="${channelFeature?.trialUrl}" /></div>
            </div>

            <div class="control-group">
                <label for="helpUrl" class="control-label">Help URL</label>
                <div class="controls"><opt:textField name="helpUrl" value="${channelFeature?.helpUrl}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="save" value="${message(code:'save')}" class="btn btn-primary"/>
            <opt:link action="list" class="btn"><g:message code="cancel" /></opt:link>
            <g:if test="${channelFeature?.id}">
                <opt:link action="remove" id="${channelFeature.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                          data-questionstr="${message(code:'admin.channelFeature.delete.question', args: [channelFeature.name])}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.channelFeature.delete.header')}"><g:message code="delete" /></opt:link>
            </g:if>
        </g:form>
    </div>
</div>
</sec:ifAnyGranted>
</body>
</html>



