<%@ page import="com.bionova.optimi.core.domain.mongo.Benchmark; com.bionova.optimi.core.domain.mongo.Resource; org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Resource data: ${resource?.staticFullName?.encodeAsHTML()}</h1>
        <h2><a href="javascript:" onclick="window.open('${createLink(action: "resourceUsage", params: [resourceId: resource?.resourceId, profileId: resource?.profileId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use</a></h2>
        <h2><a href="javascript:" onclick="window.open('${createLink(action: "resourceUsageInConstructions", params: [resourceId: resource?.resourceId])}', '_blank', 'width=1024, height=768, scrollbars=1');">See use in constructions</a></h2>
    </div>
</div>
<div class="container section">
        <g:if test="${properties}">
            <g:if test="${properties.get("impacts")}">
                <g:set var="impacts" value="${properties.remove("impacts")}" />
                <a href="javascript:" onclick="showElementByClassOrId('noValueStageOrImpact');">Show the entire table</a>
                <table class="resource" style="margin-bottom: 10px;">
                <g:each in="${Resource.allowedStages}" var="stage" status="index">
                    <g:if test="${index == 0}">
                        <tr>
                            <th style="vertical-align: top;">Impacts</th>
                        <g:each in="${Resource.allowedImpactCategories}" var="impactCategory">
                            <td style="white-space: normal; vertical-align: top; ${impactsWithData.contains(impactCategory) ? '' : 'display: none;\" class=\"noValueStageOrImpact'}">${StringUtils.replace(impactCategory, "_", " ")}</td>
                        </g:each>
                        </tr>
                    </g:if>
                    <tr${!impacts.get(stage) ? ' class=\"noValueStageOrImpact\" style=\"display: none;\"' : ''}>
                        <td>${stage}</td>
                    <g:each in="${Resource.allowedImpactCategories}" var="impactCategory">
                        <td ${impactsWithData.contains(impactCategory) ? '' : 'class=\"noValueStageOrImpact\" style=\"display: none;\"'}>
                        <g:set var="impactValue" value="${impacts.get(stage)?.get(impactCategory)}"/>
                        <g:if test="${impactValue != null}">
                            <g:formatNumber number="${impactValue}" format="0.#####E0"/>
                        </g:if>
                        </td>
                    </g:each>
                    </tr>
                </g:each>
                </table>
            </g:if>

            <a href="javascript:" onclick="showElementByClassOrId('noValue');">Show empty rows</a>
            <table class="resource">
                <g:set var="referenceResourceIdFields" value="${showDataReferences?.resourceIdReferences}" />
                <g:set var="referenceResourceTypeFields" value="${showDataReferences?.resourceTypeReferences}" />
                <g:set var="referenceResourceSubTypeFields" value="${showDataReferences?.resourceSubTypeReferences}" />
                <g:each in="${properties}">
                    <g:if test="${it.value != null}">
                        <g:if test="${'benchmark'.equals(it.key)}">
                            <tr>
                                <td>${it.key}</td>
                                <td>
                                    <g:set var="benchmark" value="${(Benchmark) it.value}"/>
                                    <g:if test="${benchmark && benchmark.values}">
                                        <g:each in="${benchmark.values}">
                                            <b>${it.key}:</b>
                                            ${it.value?.encodeAsHTML()}
                                            <br/>
                                        </g:each>
                                    </g:if>
                                </td>
                            </tr>
                        </g:if>
                        <g:elseif test="${'nmdElementId'.equals(it.key) && it.value != null}">
                            <tr>
                                <td>${it.key}</td>
                                <td>
                                    ${it.value?.encodeAsHTML()}
                                    <a href="javascript:"
                                       onclick="window.open('${createLink(action: "showNmdElement", controller: "import", params: [elementId: resource?.nmdElementId])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                        <i class="fas fa-search-plus"></i>
                                    </a>
                                </td>
                            </tr>
                        </g:elseif>
                        <g:elseif test="${'constructionId'.equals(it.key) && it.value != null}">
                            <tr>
                                <td>${it.key}</td>
                                <td>
                                    ${it.value?.encodeAsHTML()}
                                    <a href="javascript:"
                                       onclick="window.open('${createLink(action: "showConstruction", controller: "import", params: [constructionUUID: resource?.constructionId])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                        <i class="fas fa-search-plus"></i>
                                    </a>
                                </td>
                            </tr>
                        </g:elseif>
                        <g:elseif test="${referenceResourceIdFields?.contains(it.key)}">
                            <g:if test="${it.value instanceof List}">
                                <tr>
                                    <td>${it.key}</td>
                                    <td>
                                        <g:each in="${it.value}" var="listValue">
                                            ${listValue?.encodeAsHTML()}
                                            <a href="javascript:"
                                               onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: listValue])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                                <i class="fas fa-search-plus"></i>
                                            </a>
                                            <br/>
                                        </g:each>
                                    </td>
                                </tr>
                            </g:if>
                            <g:else>
                                <tr>
                                    <td>${it.key}</td>
                                    <td>
                                        ${it.value?.encodeAsHTML()}
                                        <a href="javascript:"
                                           onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                            <i class="fas fa-search-plus"></i>
                                        </a>
                                    </td>
                                </tr>
                            </g:else>
                        </g:elseif>
                        <g:elseif test="${referenceResourceTypeFields?.contains(it.key)}">
                            <tr>
                                <td>${it.key}</td>
                                <td>
                                    ${it.value?.encodeAsHTML()}
                                    <a href="javascript:"
                                       onclick="window.open('${createLink(action: "showData", controller: "resourceType", params: [resourceType: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                        <i class="fas fa-search-plus"></i>
                                    </a>
                                </td>
                            </tr>
                        </g:elseif>
                        <g:elseif test="${referenceResourceSubTypeFields?.contains(it.key)}">
                            <tr>
                                <td>${it.key}</td>
                                <td>
                                    ${it.value?.encodeAsHTML()}
                                    <a href="javascript:"
                                       onclick="window.open('${createLink(action: "showData", controller: "resourceType", params: [resourceSubType: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                        <i class="fas fa-search-plus"></i>
                                    </a>
                                </td>
                            </tr>
                        </g:elseif>
                        <g:else>
                            <tr>
                                <td>${it.key}</td>
                                <td>${it.value?.encodeAsHTML()}</td>
                            </tr>
                        </g:else>
                    </g:if>
                    <g:else>
                        <tr class="noValue" style="display: none;">
                            <td>${it.key}</td>
                            <td>${it.value?.encodeAsHTML()}</td>
                        </tr>
                    </g:else>
                </g:each>
            </table>
        </g:if>
</div>
</body>
</html>

