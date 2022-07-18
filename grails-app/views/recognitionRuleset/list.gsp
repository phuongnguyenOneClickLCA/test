<%@ page import="com.bionova.optimi.core.Constants" %>
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
            <i class="fa fa-magic" aria-hidden="true"></i>
            Recognition Rulesets
        </h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <div class="well well-controls">
            <g:if test="${!recognitionRulesets?.find({ it.type.equalsIgnoreCase(Constants.RulesetType.USER.toString()) })}">
                <opt:link action="createUserRuleset" class="btn btn-primary" style="float: left;" onclick="return modalConfirm(this);"
                          data-questionstr="Are you sure you want to create/recreate the user ruleset?"
                          data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                          data-titlestr="Create user ruleset">Create the user ruleset</opt:link>
            </g:if>
            <opt:link action="form" class="btn btn-primary"><g:message code="add" /></opt:link>
            <div class="clearfix"></div>
        </div>

            <table class="table table-striped table-condensed table-data">
                <thead>
                <tr>
                    <th>Type</th>
                    <th>Application</th>
                    <th>Name</th>
                    <th>Organization</th>
                    <th>Count</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${recognitionRulesets}" var="recognitionRuleset">
                    <tr>
                        <td>
                            <g:if test="${recognitionRuleset.type.equalsIgnoreCase(Constants.RulesetType.USER.toString())}">
                                <opt:link controller="adaptiveRecognitionData" action="list">${recognitionRuleset.type}</opt:link>
                            </g:if>
                            <g:else>
                                <opt:link action="map" params="[id: recognitionRuleset.id]">${recognitionRuleset.type}</opt:link>
                            </g:else>
                        </td>
                        <td>${recognitionRuleset.applicationId}</td>
                        <td>${recognitionRuleset.name ? recognitionRuleset.name : ''}</td>
                        <td>${recognitionRuleset.accountId && allAccounts ? allAccounts.find({it.id.toString() == recognitionRuleset.accountId})?.companyName : ''}</td>
                        <td>${recognitionRuleset.ruleCount}</td>
                        <td>
                            <g:if test="${!recognitionRuleset.type.equalsIgnoreCase(Constants.RulesetType.USER.toString())}">
                                <opt:link action="form" params="[id: recognitionRuleset.id]" class="btn btn-primary">Edit</opt:link>
                                <g:link action="removeRecognitionRuleset" params="[id: recognitionRuleset.id]" class="btn btn-danger" onclick="return modalConfirm(this);"
                                        data-questionstr="Are you sure you want to remove discard '${recognitionRuleset.name}'?"
                                        data-truestr="${message(code:'delete')}" data-falsestr="${message(code:'cancel')}"
                                        data-titlestr="Deleting recognitionRuleset"><g:message code="delete" /></g:link>
                            </g:if>
                        </td>
                    </tr>
                    <g:if test="${recognitionRuleset.type.equalsIgnoreCase(Constants.RulesetType.USER.toString())}">
                        <tr>
                            <td><opt:link controller="adaptiveRecognitionData" action="list" params="[reportedAsBad: true]">${recognitionRuleset.type}</opt:link></td>
                            <td>${recognitionRuleset.applicationId}</td>
                            <td>${recognitionRuleset.name ? recognitionRuleset.name : ''} (Reported by users as bad)</td>
                            <td>${recognitionRuleset.accountId && allAccounts ? allAccounts.find({it.id.toString() == recognitionRuleset.accountId})?.companyName : ''}</td>
                            <td>${recognitionRuleset.reportedTrainingDataCount}</td>
                            <td></td>
                        </tr>
                    </g:if>
                </g:each>
                </tbody>
            </table>
            <p>&nbsp;</p>
    </div>
</div>
</body>
</html>