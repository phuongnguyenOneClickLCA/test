<%@ page import="com.bionova.optimi.core.domain.mongo.Entity" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container section">
    <h2><g:message code="admin.dataMigration.title" /> (Entities: ${entityCount})</h2>
    <div class="sectionbody">
        <h3><g:message code="admin.dataMigration.migration_search" /></h3>
        <g:form action="search">
            <div class="control-group">
                <label for="queryId" class="control-label">QueryId</label>
                <div class="controls"><opt:textField name="queryId" value="${queryId}" /></div>
            </div>

            <div class="control-group">
                <label for="sectionId" class="control-label">SectionId</label>
                <div class="controls"><opt:textField name="sectionId" value="${sectionId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <h3><g:message code="admin.dataMigration.indicatorId_search" /></h3>
        <g:form action="search">
            <div class="control-group">
                <label for="indicatorId" class="control-label">indicatorId</label>
                <div class="controls"><opt:textField name="indicatorId" value="${indicatorId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <h3><g:message code="admin.dataMigration.resourceId_search" /></h3>
        <g:form action="search">
            <div class="control-group">
                <label for="queryId" class="control-label">ResourceId</label>
                <div class="controls"><opt:textField name="resourceId" value="${resourceId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <h3>Search indicators by resultCategoryId</h3>
        <g:form action="search">
            <div class="control-group">
                <label for="resultCategoryId" class="control-label">resultCategoryId</label>
                <div class="controls"><opt:textField name="resultCategoryId" value="${resultCategoryId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <h3>Search indicators by calculationRuleId</h3>
        <g:form action="search">
            <div class="control-group">
                <label for="calculationRuleId" class="control-label">calculationRuleId</label>
                <div class="controls"><opt:textField name="calculationRuleId" value="${calculationRuleId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <h3>Search indicators by denominatorId</h3>
        <g:form action="search">
            <div class="control-group">
                <label for="denominatorId" class="control-label">denominatorId</label>
                <div class="controls"><opt:textField name="denominatorId" value="${denominatorId}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>
    </div>

    <div class="sectionbody">
        <g:if test="${entities}">
            <g:form action="doMigration">
            <g:if test="${migrationAllowed && !resourceId && !indicatorId}">
                <a href="javascript:;" onclick="showHideDiv('doMigration');">Do migration</a>
                <g:hiddenField name="queryId" value="${queryId}" />
                <g:hiddenField name="sectionId" value="${sectionId}" />
                <div id="doMigration" style="display: none; margin-bottom: 20px;">
                    <div class="control-group">
                        <label for="newQueryId" class="control-label">New queryId</label>
                        <div class="controls"><opt:textField name="newQueryId" value="${newQueryId}" /></div>
                    </div>

                    <div class="control-group">
                        <label for="newSectionId" class="control-label">New sectionId</label>
                        <div class="controls"><opt:textField name="newSectionId" value="${newSectionId}" /></div>
                    </div>

                    <div class="clearfix"></div>

                    <opt:submit name="migrate" value="Migrate" class="btn btn-primary"/>
                    <a href="javascript:;" onclick="showHideDiv('doMigration');" class="btn">Cancel</a>
                </div>
            </g:if>
            <g:elseif test="${migrationAllowed && indicatorId}">
                <g:hiddenField name="indicatorId" value="${indicatorId}" />
                <div id="doIndicatorMigration">
                    <opt:submit name="relCalculate" value="Re-calculate" class="btn btn-primary"/>
                </div>
            </g:elseif>
            <g:else>
                <span style="color: red;">Migration not allowed</span>
            </g:else>
            <table class="table table-striped table-condensed table-data">
               <g:each in="${entities}" var="entityMap">
                   <g:if test="${indicatorId}">
                         <tr>
                         <th style="vertical-align: top !important;">${entityMap.key}</th>
                         <td>
                             <table>
                                 <% try{ %>
                                     <g:each in="${entityMap.value}" var="children">
                                         <g:each in="${children}" var="child">
                                             <tr>
                                                 <td>${child.operatingPeriodAndName}<g:hiddenField name="entityId" value="${child.id}" /></td>
                                                 <td></td>
                                             </tr>
                                         </g:each>
                                     </g:each>
                                 <%} catch(Exception e){%>
                                 <%} %>
                             </table>
                         </td>
                        </tr>
                   </g:if>
                   <g:else>
                         <tr>
                             <th style="vertical-align: top !important;">${entityMap.key}</th>
                             <td>
                                 <table>
                                       <g:each in="${entityMap.value}" var="childrenMap">
                                         <tr>
                                             <td>${childrenMap.key.operatingPeriodAndName}<g:hiddenField name="entityId" value="${childrenMap.key.id}" /></td>
                                             <td>${childrenMap.value}</td>
                                         </tr>
                                     </g:each>
                                 </table>
                             </td>
                         </tr>
                   </g:else>
               </g:each>
            </table>
            </g:form>
        </g:if>
        <g:elseif test="${indicators}">
            <h2>Indicators with ${resultCategoryId ? "applicationCategories: ${resultCategoryId}" : calculationRuleId ? "applicationRules: ${calculationRuleId}" : denominatorId ? "applicationDenominators: ${denominatorId}" : ""}</h2>
            <table class="table table-striped table-condensed table-data">
                <tr><th style="width: 350px;">IndicatorId</th><th>Active</th><th>importFile</th></tr>
            <g:each in="${indicators}" var="indicator">
                <tr><td style="width: 350px;">${indicator.indicatorId}</td><td>${indicator.active}</td><td>${indicator.importFile}</td></tr>
            </g:each>
            </table>
        </g:elseif>
    </div>

    <div class="sectionbody">
        <h3><g:message code="admin.dataMigration.search_deprecated_attributes" default="Deprecated attributes" /></h3>
        <g:form action="searchDeprecatedAttributes">
            <div class="control-group">
                <label for="queryId" class="control-label">Domain class</label>
                <g:select name="domainClass" from="${domainClasses}" noSelection="['' : '']" value="${domainClass}" />
            </div>

            <div class="control-group">
                <label for="attribute" class="control-label">Attribute</label>
                <div class="controls"><opt:textField name="attribute" value="${attribute}" /></div>
            </div>

            <div class="clearfix"></div>

            <opt:submit name="search" value="${message(code:'search')}" class="btn btn-primary"/>
            <opt:link controller="main" action="list" class="btn"><g:message code="cancel" /></opt:link>
        </g:form>

        <g:each in="${results}" var="result">
            <p>${result}</p>
        </g:each>
    </div>

</div>
</body>
</html>



