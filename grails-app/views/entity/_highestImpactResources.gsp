<%@ page import="com.bionova.optimi.core.Constants" %>
<g:if test="${resources}">
    <g:set var="entityService" bean="entityService"/>
    <g:if test="${fromPlanetaryReport}">
        <table class="table table-striped table-condensed">
            <thead>
            <tr>
                <th></th>
                <th><g:message code="query.material"/></th>
                <th class="number"><g:message code="cradle_gate_impact"/></th>
                <th class="number"><g:message code="share_in_a1_a3"/></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${resources}" var="resource" status="index">
                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                <tr>
                    <td>${index + 1}. </td>
                    <td>${resource.localizedName}</td>
                    <td class="number">${formattedImpactPerResourceAndDataset?.get(resource)} ${unit}</td>
                    <td class="number">
                        ${percentageScores?.get(resource)} %
                    </td>
                </tr>
            </g:each>

                    </tbody>
                </table>
            </div>
        </div>
    </g:if>
</g:if>