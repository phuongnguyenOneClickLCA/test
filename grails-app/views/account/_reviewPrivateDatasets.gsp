<%@ page import="com.bionova.optimi.core.domain.mongo.Benchmark; com.bionova.optimi.core.domain.mongo.Resource; org.apache.commons.lang.StringUtils; com.bionova.optimi.core.util.DomainObjectUtil; com.bionova.optimi.core.service.OptimiResourceService" %>
<%
    OptimiResourceService optimiResourceService = grailsApplication.mainContext.getBean("optimiResourceService")
%>
    <section style="height: 40px;" class="revPage">
        <div class="helpSection" style="font-size:14px;">
            ${helpText}
        </div>

        <div class="headName" style="float: left;"><h3><g:if
                test="${optimiResourceService.getLocalizedName(resource)}">Impact data: ${optimiResourceService.getLocalizedName(resource)}</g:if></h3>
        </div>
        <%--<div class="helpSection" style="font-size:14px;">
            ${helpText}
                     <span style="float: right;margin: 10px;" id="helpForm" class="btn btn-primary longcontent" rel="popover" data-trigger="click" data-html="true" data-original-title="" title="" data-content="${helpText}">
                    <i class="fas fa-question"></i></span>
            </div>--%>
    </section>
<% java.util.List impactCategoryQuestions = []%>
<div style="overflow: auto;height: 100%; max-height: 650px; width: 100%">
    <g:if test="${properties}">
        <g:if test="${properties.get("impacts")}">
        <g:set var="impacts" value="${properties.remove("impacts")}" />
            <table class="resource impact" style="margin-bottom: 10px; font-size: 14px; width: 100%">

                <tr>
                <g:each in="${Resource.allowedStages}" var="stage" status="index"><g:if test="${index == 0}">
                    <th style="vertical-align: top; width : 200px;">Impacts</th> </g:if>
                    <th ${!impacts.get(stage) ? ' class=\"noValueStageOrImpact\" style=\"display: none; word-wrap: break-word; width : 100px;\"' : 'style=\"word-wrap: break-word; width : 100px;\"'}>${stage}
                    </th>
                </g:each>
                </tr>
                        <g:each in="${Resource.allowedImpactCategories}" var="impactCategory">
                            <g:set var="question" value="${questionService?.getQuestion("privateResourceCreator", impactCategory)}"/>
                            <tr>
                                <td style="white-space: normal; vertical-align: top; ${impactsWithData.contains(impactCategory) ? '' : 'display: none;\" class=\"noValueStageOrImpact'}">${question?.getLocalizedQuestion()}</td>
                                <%impactCategoryQuestions.add(question?.getLocalizedQuestion())%>
                            <g:each in="${Resource.allowedStages}" var="stage" status="index">
                                <g:if test="${impacts.get(stage)}">
                                <td ${impactsWithData.contains(impactCategory) ? 'style=\"width : 100px;\"' : 'class=\"noValueStageOrImpact\" style=\"display: none; word-wrap: break-word; width : 100px;\"'}>
                                    <g:set var="impactValue" value="${impacts.get(stage)?.get(impactCategory)}"/>
                                    <g:if test="${impactValue != null}">
                                        <g:formatNumber number="${impactValue}" format="0.#####E0"/>
                                    </g:if>
                                </td></g:if>
                            </g:each>
                            </tr>

                </g:each>
            </table>
        </g:if>
        <g:if test="${properties}">

            <h3 style="float: left">Other Details</h3>

        </g:if>
        <table class="resource userData" style="margin-bottom: 10px; font-size: 14px; width:100%;">
            <g:set var="referenceResourceIdFields" value="${showDataReferences?.resourceIdReferences}" />
            <g:set var="referenceResourceTypeFields" value="${showDataReferences?.resourceTypeReferences}" />
            <g:set var="referenceResourceSubTypeFields" value="${showDataReferences?.resourceSubTypeReferences}" />
            <g:each in="${properties}">
                <g:if test="${it.value != null && !impactCategoryQuestions?.contains(it.key)}">
                    <g:if test="${'benchmark'.equals(it.key)}">
                        <tr><td>${it.key}</td><td>
                            <g:set var="benchmark" value="${(Benchmark) it.value}"/>
                            <g:if test="${benchmark && benchmark.values}">
                                <g:each in="${benchmark.values}">
                                    <b>${it.key}:</b> ${it.value}<br/>
                                </g:each>
                            </g:if>
                        </td></tr>
                    </g:if>
                    <g:elseif test="${referenceResourceIdFields?.contains(it.key)}">
                        <g:if test="${it.value instanceof List}">
                            <tr><td>${it.key}</td><td>
                                <g:each in="${it.value}" var="listValue">
                                    ${listValue} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: listValue])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a><br />
                                </g:each>
                            </td></tr>
                        </g:if>
                        <g:else>
                            <tr><td>${it.key}</td><td>${it.value} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "import", params: [resourceId: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td></tr>
                        </g:else>
                    </g:elseif>
                    <g:elseif test="${referenceResourceTypeFields?.contains(it.key)}">
                        <tr><td>${it.key}</td><td>${it.value} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "resourceType", params: [resourceType: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td></tr>
                    </g:elseif>
                    <g:elseif test="${referenceResourceSubTypeFields?.contains(it.key)}">
                        <tr><td>${it.key}</td><td>${it.value} <a href="javascript:" onclick="window.open('${createLink(action: "showData", controller: "resourceType", params: [resourceSubType: it.value])}', '_blank', 'width=1024, height=768, scrollbars=1');"><i class="fas fa-search-plus"></i></a></td></tr>
                    </g:elseif>
                    <g:else>
                        <tr><td>${it.key}</td><td>${it.value}</td></tr>
                    </g:else>
                </g:if>
                <g:else>
                    <tr class="noValue" style="display: none;"><td>${it.key}</td><td>${it.value}</td></tr>
                </g:else>
            </g:each>
        </table>

    </g:if>
</div>
<style>
.impact tr:nth-child(even) {
    background-color: #f0f0f0;
}
</style>