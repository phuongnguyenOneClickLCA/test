<%@ page import="groovy.json.JsonBuilder" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>
            <i class="layout-icon-user"></i>
            ADMIN_Organizations
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <div class="well well-controls">
            <opt:link action="form" class="btn btn-primary"><g:message code="add"/></opt:link>
            <div class="clearfix"></div>
        </div>
        <table id="organizationsTable" class="table table-striped table-condensed">
            <thead>
            <tr>
                <th><g:message code="account.company_name"/></th>
                <th><g:message code="account.address"/></th>
                <th><g:message code="account.country"/></th>
                <th><g:message code="account.branding"/></th>
                <th><g:message code="entity.show.licenses"/></th>
                <th><g:message code="account.users"/></th>
                <th><g:message code="account.private_datasets_header"/></th>
                <th><g:message code="account.private_constructions_header"/></th>
                <th><g:message code="account.favorite_materials"/></th>
                <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                    <th><g:message code="account.loginKey"/></th>
                </sec:ifAnyGranted>
                <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    <th>Delete</th>
                    <th>&nbsp;</th>
                </sec:ifAnyGranted>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <p>&nbsp;</p>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#organizationsTable').dataTable({
                "bPaginate": true,
                "sPaginationType": "full_numbers",
                "bProcessing": true,
                "bDestroy": true,
                "bDeferRender": true,
                "aaData": ${new groovy.json.JsonBuilder(accounts)},
                "aoColumns": [
                    {mDataProp: 'companyName'},
                    {mDataProp: 'address'},
                    {mDataProp: 'country'},
                    {mDataProp: 'brandingImg', 'bSortable': false},
                    {mDataProp: 'licensesCount'},
                    {mDataProp: 'linkedUsersCount'},
                    {mDataProp: 'privateDatasetsCount', 'bSortable': ${privateDatasetColumnSortable}},
                    {mDataProp: 'privateConstructionsCount'},
                    {mDataProp: 'favoriteMaterialsCount'},
                    <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN">
                    {mDataProp: 'loginKey'},
                    </sec:ifAnyGranted>
                    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
                    {mDataProp: 'deleteBtn', 'bSortable': false},
                    </sec:ifAnyGranted>
                ]
            }
        )
    });
</script>
</body>
</html>