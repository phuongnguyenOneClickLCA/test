<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>
<body>
    <div class="container">
        <div class="screenheader"><h1><g:message code="${notification?.id ? 'admin.notification.modify' : 'admin.notification.add' }" /></h1></div>
    </div>

    <g:form action="save" useToken="true">
        <g:hiddenField name="id" value="${notification?.id}" />
        <div class="container section">
            <div class="sectionbody">
                <div class="control-group">
                    <label for="channelFeatureIds" class="control-label">
                        <g:message code="admin.notification.channelFeatures" />
                    </label>
                    <div class="controls">
                        <g:each in="${channelFeatures}" var="channelFeature">
                            <g:checkBox name="channelFeatureIds" value="${channelFeature.id}" checked="${notification?.channelFeatureIds?.contains(channelFeature?.id?.toString())}"/> ${channelFeature.name}<br />
                        </g:each>
                    </div>
                </div>

                <div class="control-group">
                    <label for="showIfAccountCreatedBefore" class="control-label"><g:message code="admin.notification.showIfAccountCreatedBefore" /></label>
                    <div class="controls"><div class="input-append"><label><opt:textField name="showIfAccountCreatedBefore" value="${formatDate(date: notification?.showIfAccountCreatedBefore, format: 'dd.MM.yyyy')}" class="input-xlarge datepicker" /><span class="add-on"><i class="icon-calendar"></i></span></label></div></div>
                </div>
            </div>
        </div>

        <div class="container section">
            <div class="sectionbody">
                <div class="control-group">
                    <label for="channelFeatureIds" class="control-label">
                        <g:message code="admin.notification.heading.choose_language" /> (<g:message code="default_language.info" />)
                    </label>
                    <div class="controls">
                        <g:select from="${systemLocales}" name="language" noSelection="['':'']"  optionKey="resourceId" optionValue="${{it.localizedName}}" />
                        <input type="button" class="btn btn-primary" value="${message(code:'add')}" onclick="addLanguageInput('language', 'heading', 'textField', 'heading');" />
                    </div>
                </div>
                <div class="control-group">
                    <table class="left_alignment" id="heading">
                    <g:each in="${notification?.heading}" var="heading" status="index">
                        <g:if test="${heading.value}">
                            <tr id="heading${index}">
                                <td>${optimiResourceService.getLocalizedName(systemLocales.find({it.resourceId.equals(heading.key)}))}</td>
                                <td><g:textField class="input-xlarge" value="${heading.value}" name="heading[${heading.key}]" /></td>
                                <td><input type="button" class="btn btn-danger" value="${message(code:'delete')}" onclick="$('#heading${index}').remove();" /></td>
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
                    <label for="channelFeatureIds" class="control-label">
                        <g:message code="admin.notification.text.choose_language" /> (<g:message code="default_language.info" />)
                    </label>
                    <div class="controls">
                        <g:select from="${systemLocales}" name="textLanguage" noSelection="['':'']" optionKey="resourceId" optionValue="${{it.localizedName}}" />
                        <input type="button" class="btn btn-primary" value="${message(code:'add')}" onclick="addLanguageInput('textLanguage', 'text', 'textArea', 'text');" />
                    </div>
                </div>

                <div class="control-group">
                    <table class="left_alignment" id="text">
                    <g:each in="${notification?.text}" var="text" status="index">
                        <g:if test="${text.value}">
                            <tr id="text${index}">
                                <td>${optimiResourceService.getLocalizedName(systemLocales.find({it.resourceId.equals(text.key)}))}</td>
                                <td><g:textArea class="input-xlarge" value="${text.value}" name="text[${text.key}]" /></td>
                                <td><input type="button" class="btn btn-danger" value="${message(code:'delete')}" onclick="$('#text${index}').remove();" /></td>
                            </tr>
                        </g:if>
                    </g:each>
                    </table>
                </div>
            </div>
        </div>

        <div class="container section">
            <div class="sectionbody">
                <label class="checkbox">
                    <g:checkBox name="enabled" checked="${notification? notification.enabled : true}" />
                    <g:message code="admin.notification.enabled" />
                </label>
                <label class="checkbox">
                    <g:checkBox name="isPopUp" checked="${notification? notification.isPopUp : false}" />
                    Show as popup
                </label>
                <opt:submit name="save" value="${message(code: 'save')}" class="btn btn-primary"/>
                <opt:link action="list" class="btn"><g:message code="cancel" /></opt:link>
                <g:if test="${notification?.id}">
                    <opt:link action="remove" id="${notification?.id}" class="btn btn-danger" onclick="return modalConfirm(this);"
                          data-questionstr="${message(code:'admin.notification.delete.question')}" data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.notification.delete.header')}"><g:message code="delete" /></opt:link>
                </g:if>
            </div>
        </div>
    </g:form>
</body>
</html>
