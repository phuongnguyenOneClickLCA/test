<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
    <g:set var="optimiResourceService" bean="optimiResourceService"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <i class="layout-icon-user"></i>
            ADMIN_Ecommerce catalogues
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <table class="table table-striped table-condensed table-data">
            <thead>
            <tr>
                <th><g:message code="admin.accountCatalogue.slug" /></th>
                <th>Countries for which enabled by default</th>
                <th><g:message code="admin.accountCatalogue.name" /></th>
                <th>&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <tr><th colspan="4">Create new account catalogue</th></tr>
            <g:form action="saveCatalogue">
            <tr>
                <td>
                    <g:textField name="slug" value="" placeHolder="Give slug for catalogue"/>
                </td>
                <td>
                    <div class="controls" >
                        <g:if test="${countries}">
                            <g:select multiple="multiple" name="countries" id="countrySelect" from="${countries}" optionKey="${{it.isoCountryCode ? it.isoCountryCode : ''}}" optionValue="${{it.localizedName}}" noSelection="['' : '' ]" />
                        </g:if>
                    </div>
                </td>
                <td>
                    <g:textField name="name" value="" placeHolder="Give name for catalogue"/>
                </td>
                <td><g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" /></td>
            </tr>
            </g:form>
            <tr><th colspan="3">Existing catalogues</th></tr>
            <g:each in="${accountCatalogues}" var="accountCatalogue">
                <g:form action="saveCatalogue">
                <tr>
                    <td>
                        <g:hiddenField name="id" value="${accountCatalogue.id}" />
                        <g:textField name="slug" value="${accountCatalogue.slug}" />
                    </td>
                    <td>
                        <div class="controls" >
                            <g:if test="${countries}">
                                <select name="countries" id="countrySelect" multiple>
                                    <g:each in="${countries}" var="country">
                                        <option value="${country.isoCountryCode}" ${accountCatalogue.countries?.contains(country.isoCountryCode) ? 'selected' : ''}>${optimiResourceService.getLocalizedName(country)}</option>
                                    </g:each>
                                </select>
                            </g:if>
                        </div>
                    </td>
                    <td>
                        <g:textField name="name" value="${accountCatalogue.name}" />
                    </td>
                    <td>
                        <g:submitButton name="save" value="${message(code: 'save')}" class="btn btn-primary" />
                        <g:link action="removeCatalogue" class="btn btn-danger" id="${accountCatalogue.id}" onclick="return modalConfirm(this);"
                                data-questionstr="Are you sure you want to remove catalogue '${accountCatalogue.name}'?"
                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                data-titlestr="Deleting account catalogue"><g:message code="delete" /></g:link>
                    </td>
                </tr>
                </g:form>
            </g:each>
            </tbody>
        </table>
        <p>&nbsp;</p>

    </div>
</div>

<script type="text/javascript">

    $(function () {
        var allSelects = $('select');
        if (allSelects) {
            $(allSelects).select2({
                placeholder:'<g:message code="select"/>'
            }).maximizeSelect2Height();
        }
    });
</script>

</body>
</html>