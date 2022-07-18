<!doctype html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <g:render template="/entity/basicinfo" />
    </div>
</div>

<%--
Entity name (from SW, non modifiable)
User name (from SW, non modifiable)
User email (from SW, non modifiable)
What would you like to do, or your free comments (textbox, approximately 100 chars wide and 5 chars high)
Explanatory text below heading: "Please describe briefly what you would like to do, it will help us to set up the right kind of trial license for you"

--%>
<div class="container section">
    <h3><g:if test="${isQuote}"><g:message code="quoteRequest.title" /></g:if><g:else><g:message code="licenseRequest.submit" /></g:else></h3>
    <g:form controller="entity" action="${isQuote ? 'sendQuoteRequest' : 'sendTrialLicenseRequest'}" useToken="true">
        <g:hiddenField name="entityId" value="${entity?.id}"/>
        <div class="clearfix"></div>

        <div class="querysection">
            <g:if test="${isQuote}">
                <g:each in="${indicators}" var="indicator">
                    <g:hiddenField name="indicator" value="${indicator}" />
                </g:each>
                <div class="control-group" >
                    <label><g:message code="licenseRequest.excepted_volume" /></label>
                    <div>
                        <div class="input-append">
                            <%--
                            1-3, 4-6, 7-9, 10+ / annualized
                            --%>
                            <select name="expectedVolume">
                                <option value="1-3">1-3</option>
                                <option value="4-6">4-6</option>
                                <option value="7-9">7-9</option>
                                <option value="10+">10+ </option>
                            </select>
                        </div>
                    </div>
                </div>
                <p>&nbsp;</p>
                <div class="control-group" >
                    <div>
                        <div class="input-append">
                           <label class="checkbox"><input type="checkbox" name="integrationNeeds"${integrationNeeds ? ' checked=\"checked\"' : ''} /><g:message code="licenseRequest.integrationNeeds" /></label>
                           <label class="checkbox"><input type="checkbox" name="customisationNeeds"${customisationNeeds ? ' checked=\"checked\"' : ''} /><g:message code="licenseRequest.customisationNeeds" /></label>
                        </div>
                    </div>
                </div>
            </g:if>
            <p>&nbsp;</p>
            <div class="control-group" >
                <g:if test="${isQuote}">
                    <p><g:message code="licenseRequest.comments.quote.explanation" /></p>
                </g:if>
                <g:else><p><g:message code="licenseRequest.comments.explanation" /></p></g:else>
                <div>
                    <div class="input-append">
                        <opt:textArea modifiable="${true}" name="comments" class="input-xlarge span8 mandatory" rows="5" cols="100" />
                    </div>
                </div>
            </div>
            <div class="control-group" >
                <div>
                    <div class="input-append">
                        <table class="left_align">
                        <tr><th><input type="hidden" name="userName" value="${user.name}" /><g:message code="user.name" />:</th><td>${user.name}</td></tr>
                        <tr><th><input type="hidden" name="userEmail" value="${user.username}" /><g:message code="email" />:</th><td>${user.username}</td></tr>
                        <tr><th><g:message code="user.phone" /></th><td><input type="text" name="phone" value="${user.phone}" /></td></tr>
                        <tr><th><g:message code="user.organization" />:</th><td><input type="text" name="organizationName" value="${user.organizationName}" /></td></tr>
                        <tr><th><g:message code="user.country" />:</th><td><g:select name="localeString" from="${locales}" optionKey="${{it.resourceId.toLowerCase()}}" optionValue="${{it.localizedName}}" value="${user?.localeString}" /></td></tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="clearfix"></div>
        <opt:submit entity="${entity}" name="save" value="${isQuote ? message(code:'quoteRequest.submit'): message(code:'licenseRequest.submit')}" class="btn btn-primary" />
        <g:actionSubmit value="${message(code:'cancel')}" action="cancel" class="btn" />
    </g:form>
</div>
</body>
</html>

