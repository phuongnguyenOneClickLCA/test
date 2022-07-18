<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">

        <div class="pull-left">  <h1>
            Private datasets
            ${datasetAmount ? '(' + datasetAmount+ ')' : ''}
        </h1>
    </div></div>
</div>

<div class="container section">
<sec:ifAnyGranted roles="ROLE_DATA_MGR">
    <g:if test="${accountAndResource}">
        <table class="resource table table-striped table-condensed" id="resourceTable">
            <thead>
            <tr id="resourceListHeading">
               <th> Account </th> <th>Resource name</th> <th>Resource full name </th> <th>Profile</th> <th>GWP</th> <th>file source</th> <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${accountAndResource}">
                <tr class="resourceRow">
                    <g:each in="${it.value}" var="resource">
                        <td>${it.key}</td><td>${resource.localizedName} <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN"><a href="javascript:;" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></sec:ifAnyGranted></td><td>${resource.staticFullName}</td> <td>${resource.profileId}</td> <td>${resource.impactGWP100_kgCO2e}</td> <td>${resource.importFile}</td>
                    <td>
                        <sec:ifAnyGranted roles="ROLE_SYSTEM_ADMIN"><g:link action="deletePrivateDataset" class="btn btn-danger" id="${resource.id}" params="[resourceId:resource.id, accountId: account?.id]" onclick="return modalConfirm(this);" data-questionstr="${message(code:'admin.import.resource.delete_question')}"
                                                                            data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}" data-titlestr="${message(code:'admin.import.resource.delete_title')}"><g:message code="delete" /></g:link></sec:ifAnyGranted>
                    </td></tr>
                    </g:each>
            </g:each>
            </tbody>
        </table>
    </g:if>
</sec:ifAnyGranted>
</div>
</body>
</html>