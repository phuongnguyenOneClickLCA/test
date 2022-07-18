<%--
  Created by IntelliJ IDEA.
  User: trang
  Date: 8.1.2021
  Time: 15.22
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
    <asset:javascript src="stupidtable.min.js"/>
    <asset:javascript src="parsley.js"/>
</head>

<body>
<g:form action="saveQuantUpdate" controller="importMapper" id="quantUpdateForm" method="post" novalidate="novalidate">
    <g:hiddenField name="entityId" value="${entityId}"/>
    <g:hiddenField name="childEntityId" value="${childEntity?.id}"/>
    <g:hiddenField name="queryId" value="${queryId}"/>
    <g:hiddenField name="indicatorId" value="${indicatorId}"/>
    <g:hiddenField name="ingoreIfcDataset" value=""/>
    <div class="container">
        <g:if test="${warningMessage}">
            <div class="alert alert-warning">
                <button data-dismiss="alert" class="close" type="button">×</button>
                <strong>${warningMessage}</strong>
            </div>
        </g:if>
        <div class="screenheader">
            <opt:breadcrumbsNavBar childEntity="${childEntity}" parentEntity="${entity}" indicator="${indicator}"
                                   specifiedHeading="${g.message(code: 'import_data')}"/>
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
        </div>

        <div class="pull-right hide-on-print">
            <g:link controller="importMapper" action="cancel" class="btn" style="font-weight: normal;" onclick="return
            modalConfirm(this);" data-questionstr="${message(code: 'importMapper.index.cancel')}"
                    data-truestr="${message(code: 'yes')}"
                    data-falsestr="${message(code: 'no')}" data-titlestr="Cancel"><g:message code="cancel"/></g:link>
            <g:link controller="importMapper" action="generateExcelFromDatasets"
                    params="[returnUserTo: 'updateQuant', entityId: entity?.id, childEntityId: childEntity?.id]"
                    class="btn btn-primary" style="font-weight: normal;">
                <g:message code="results.expand.download_excel"/>
            </g:link>
            <g:submitButton name="save" value="${message(code: 'continue')}" class="btn btn-primary" id="ifcContinue"
                            onclick="doubleSubmitPrevention('quantUpdateForm');" style="font-weight: normal;"/>
        </div>

        <h1>${message(code: 'importMapper.updating_header')}</h1>

    </div>

    <div class="container">
        <div class="container">
            <g:if test="${datasets}">
                <g:if test="${multipleFoundData.size() > 0}">
                    <div class="section">
                        <div class="sectionheader" onclick="toggleExpandSection(this)">
                            <button class="pull-left sectionexpander">
                                <i class="icon icon-chevron-down expander"></i>
                                <i class="icon icon-chevron-right collapser"></i>
                            </button>
                            <h2 class="h2expander">
                                ${message(code: "importMapper.updating_updatedData_ambiguious")}
                                <span class="help-block-inline">(${message(code:"importMapper.updating_abiguity_Data")})</span>
                            </h2>
                        </div>
                        <div class="sectionbody">
                            <g:render template="updateQuantTableTemplate"
                                      model="[datasetsForTable     : multipleFoundData, allmatchesCases: allmatchesCases,
                                              additionalQuestions: additionalQuestions, entityDatasets : entityDatasets,
                                              multipleFound: true, identified: false]"/>
                        </div>
                    </div>
                </g:if>
                <g:if test="${changedQuantity.size() > 0}">
                    <div class="section">
                        <div class="sectionheader" onclick="toggleExpandSection(this)">
                            <button class="pull-left sectionexpander">
                                <i class="icon icon-chevron-down expander"></i>
                                <i class="icon icon-chevron-right collapser"></i>
                            </button>
                            <h2 class="h2expander">
                                ${message(code:"importMapper.updating_updatedData")}
                                <span class="help-block-inline">(${message(code:"importMapper.updating_no_abiguity_Data")})</span>
                            </h2>
                        </div>
                        <div class="sectionbody">
                            <g:render template="updateQuantTableTemplate"
                                      model="[datasetsForTable:changedQuantity, allmatchesCases:allmatchesCases,
                                              additionalQuestions: additionalQuestions, entityDatasets:entityDatasets,identified: true]"/>
                        </div>
                    </div>
                </g:if>
                <g:if test="${newData.size() > 0}">
                    <div class="section">
                        <div class="sectionheader" onclick="toggleExpandSection(this)">
                            <button class="pull-left sectionexpander">
                                <i class="icon icon-chevron-down expander"></i>
                                <i class="icon icon-chevron-right collapser"></i>
                            </button>
                            <h2 class="h2expander">
                                ${message(code:"importMapper.updating_newData")}
                            </h2>
                        </div>
                        <div class="sectionbody">
                            <g:render template="updateQuantTableTemplate"
                                      model="[datasetsForTable:newData, identified: false ,
                                              additionalQuestions: additionalQuestions]"/>
                        </div>
                    </div>

                </g:if>
                <g:if test="${unchangedQuantity.size() > 0 || lockedData.size() > 0 }">
                    <div class="section">
                        <div class="sectionheader" onclick="toggleExpandSection(this)">
                            <button class="pull-left sectionexpander">
                                <i class="icon icon-chevron-down expander"></i>
                                <i class="icon icon-chevron-right collapser"></i>
                            </button>
                            <h2 class="h2expander">
                                ${message(code:"importMapper.updating_unchangedData")}
                            </h2>
                        </div>
                        <div class="sectionbody">
                                <g:render template="updateQuantTableTemplate" model="[datasetsForTable:unchangedQuantity, identified: true,
                                                                                      additionalQuestions: additionalQuestions,
                                                                                      unchanged: true,allmatchesCases:allmatchesCases]"/>
                                <g:render template="updateQuantTableTemplate" model="[datasetsForTable:lockedData, locked:true, identified: true,
                                                                                      additionalQuestions: additionalQuestions, allmatchesCases:allmatchesCases]"/>
                        </div>
                    </div>
                </g:if>
            </g:if>
        </div>
    </div>
</g:form>
<script type="text/javascript">
    function ingoreIfcDataset(element,datasetId,elementClass) {
        var row = $(element).closest('tr');
        var ingoreIfcDataset = $("#ingoreIfcDataset")
        var ignoreVaule = $(ingoreIfcDataset).val()
        // add ignore datasetId to form field
        if(ignoreVaule && ignoreVaule.length > 0){
            ignoreVaule += "." + datasetId
        } else {
            ignoreVaule = datasetId
        }
        $(ingoreIfcDataset).val(ignoreVaule)
        // hide rows got deleted
        row.hide()
        row.after('<tr></tr>').hide()
        // remove children row
        $(elementClass).remove()

    }
</script>
</body>
</html>