<%@ page import="com.bionova.optimi.core.Constants" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>

<body>
<div class="container">
    <div class="screenheader">
        <h1>License features</h1>
    </div>
    <g:if test="${!isAdmin}">
        <div class="boldText margin-bottom-10">Ask an Admin to add / delete a license feature.</div>
    </g:if>
</div>

<div class="container section" style="border-bottom: 1px solid #E2E2E2; margin-bottom: 20px;">
    <div class="sectionbody">
        <sec:ifAnyGranted roles="${Constants.ROLE_SALES_VIEW}">
            <div class="row-fluid row-bordered">
                <table class="table table-striped table-condensed">
                    <tr>
                        <th>Feature Id</th>
                        <th>Class</th>
                        <th>Name</th>
                        <th>User linked</th>
                        <th colspan="2">Action</th>
                    </tr>
                    <g:if test="${isAdmin}">
                        <tr>
                            <g:form action="updateLicenseFeature" name="featureForm">
                                <td>
                                    <input type="text" name="featureId"/>
                                </td>
                                <td>
                                    <g:select name="type" from="${featureClasses}"
                                              optionValue="${{ it }}"/>
                                </td>
                                <td>
                                    <input type="text" name="name"/>
                                </td>
                                <td>
                                    <g:checkBox name="userLinked" checked="" value="true"/>
                                </td>
                                <td colspan="2">
                                    <opt:submit name="saveFeatureNew" value="Create"
                                                class="btn btn-primary"/>
                                </td>
                            </g:form>
                        </tr>
                    </g:if>
                    <g:each in="${features}" var="feature">
                        <tr>
                            <g:form action="updateLicenseFeature" name="featureForm">
                                <input type="hidden" value="${feature.featureId}" name="featureId">
                                <td>${feature.featureId}</td>
                                <td>
                                    <g:select name="type" class="select${feature.featureId}" disabled="true"
                                              value="${feature?.featureClass}" from="${featureClasses}"
                                              optionValue="${{ it }}"/>
                                </td>
                                <td>
                                    <input readonly class="${feature.featureId}" type="text" value="${feature.name}"
                                           name="name">
                                </td>
                                <td>
                                    <g:checkBox name="userLinked" class="checkbox${feature.featureId}"
                                                value="${feature.userLinked}"
                                                disabled="true"/>
                                </td>
                                <td>
                                    <a href="javascript:" id="editFeature${feature.featureId}"
                                       onclick="unlockFeatureForEdit('${feature.featureId}')"
                                       class="btn btn-primary">
                                        Edit
                                    </a>
                                    <opt:submit name="saveFeature${feature.featureId}" value="Save"
                                                class="btn btn-primary" style="display: none"/>
                                </td>
                            </g:form>
                            <td>
                                <g:if test="${isAdmin}">
                                    <a href="javascript:" id="deleteFeature${feature.featureId}"
                                       onclick="deleteFeature('${feature.id}', '${feature.formattedName}')"
                                       class="btn btn-danger">
                                        Delete
                                    </a>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                </table>
            </div>
        </sec:ifAnyGranted>
    </div>
</div>
<script type="text/javascript">

    function unlockFeatureForEdit(featureId) {
        $('#editFeature' + featureId).hide();
        $('#saveFeature' + featureId).show();
        $('.select' + featureId).removeAttr('disabled');
        $('.checkbox' + featureId).removeAttr('disabled');
        $('.' + featureId).removeAttr('readonly');
    }

</script>
</body>
</html>
