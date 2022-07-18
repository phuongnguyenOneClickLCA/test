<g:set var="benchmarkScores1" value="${entityWithCarbonBenchmarkList1}"/>
<g:set  var="certificationList1" value="${entityWithCertificationList1}"/>
<g:each in="${entities}" var="entity" status="index"><%--
    --%><tr>
        <td>
            <span class="thumb" id="thumb${index}"><%--
-            --%><g:if test="${entity.hasImage}"><%--
-               --%>${raw(thumbImages?.get(entity.id.toString()))}<%--
-            --%></g:if><%--
-            --%><g:else><%--
-                --%><g:if test="${'ekokompassi'.equals(entity.entityClass)}"><%--
-                    --%><i class="entityclass-small-ekokompassi"></i><%--
-                --%></g:if><%--
-                --%><g:else><%--
-                    --%><i class="entitytype-small <opt:entityIcon entity="${entity}" />-small"></i><%--
-                --%></g:else><%--
-            --%></g:else><%--
-        --%></span>
            </td>
            <td class="maininfo">
                <opt:link class="entityLink" onclick="clickAndDisable('entityLink')" controller="entity" action="show" params="[entityId: entity.id, name: entity.name, showToolsNote: true]">${entity?.archived ? "<i style=\"color: black\" class=\"fas fa-lock\"></i> " : ""} ${entity.name}</opt:link>
                <opt:entityDisplayName basicQuery="${basicQuery}" entity="${entity}" />
            </td>
            <td class="text-center center-block"><g:if test="${certificationList1?.keySet()?.contains(entity.id)}"><img width="60px" src='${certificationList1?.get(entity.id).get("img")}' alt='${certificationList1?.get(entity.id).get("name")}' onerror="this.style.display='none'"/> </g:if></td>
            <td class="text-center"><g:if test="${benchmarkScores1?.keySet()?.contains(entity.id)}"><span class='${benchmarkScores1.get(entity.id).get("index")}index'>${benchmarkScores1.get(entity.id).get("index")}</span> <br /> ${benchmarkScores1.get(entity.id).get("score")} kg CO<sub>2</sub>e/m<sup>2</sup> </g:if></td>
            <td><%--
            --%><g:set var="designCount" value="${entity.getChildCount('design')}" /><%--
            --%><g:if test="${designCount}"><%--
                --%><g:message code="main.list.desigCount" args="[designCount]" /><%--
            --%></g:if><%--
            --%><g:set var="operatingCount" value="${entity.getChildCount('operatingPeriod')}" /><%--
            --%><g:if test="${operatingCount}"><%--
                --%><g:message code="main.list.operatingCount" args="[operatingCount]" /><%--
            --%></g:if><%--
    --%></td>
        </tr><%--
--%></g:each><%--

--%>
<g:each in="${publicEntities}" var="entity" status="index"><%--
--%><tr class="public">
    <td>
        <span class="thumb" id="thumb${index}"><%--
-            --%><g:if test="${entity.hasImage}"><%--
-               --%>${raw(thumbImages?.get(entity.id.toString()))}<%--
-            --%></g:if><%--
-            --%><g:else><%--
-                --%><g:if test="${'ekokompassi'.equals(entity.entityClass)}"><%--
-                    --%><i class="entityclass-small-ekokompassi"></i><%--
-                --%></g:if><%--
-                --%><g:else><%--
-                    --%><i class="entitytype-small <opt:entityIcon entity="${entity}" />-small"></i><%--
-                --%></g:else><%--
-            --%></g:else><%--
-        --%></span>
    </td>
    <td class="maininfo">
        <opt:link class="entityLink" onclick="clickAndDisable('entityLink')" controller="entity" action="show" params="[entityId: entity.id, name: entity.name]">${entity?.archived ? "<i style=\"color: black\" class=\"fas fa-lock\"></i> " : ""} ${entity.name}</opt:link>
        <opt:entityDisplayName basicQuery="${basicQuery}" entity="${entity}" />
    </td>
    <td>${entity.localizedEntityClass}</td>
    <td class="text-center center-block"><g:if test="${certificationList1?.keySet()?.contains(entity.id)}"><img width="60px" src='${certificationList1?.get(entity.id).get("img")}' alt='${certificationList1?.get(entity.id).get("name")}' onerror="this.style.display='none'"/> </g:if></td>
    <td class="text-center"><g:if test="${benchmarkScores1?.keySet()?.contains(entity.id)}"><span class='${benchmarkScores1.get(entity.id).get("index")}index'>${benchmarkScores1.get(entity.id).get("index")}</span> <br /> ${benchmarkScores1.get(entity.id).get("score")} kg CO<sub>2</sub>e/m<sup>2</sup> </g:if></td>
    <td><%--
            --%><g:set var="designCount" value="${entity.getChildCount('design')}" /><%--
            --%><g:if test="${designCount}"><%--
                --%><g:message code="main.list.desigCount" args="[designCount]" /><%--
            --%></g:if><%--
            --%><g:set var="operatingCount" value="${entity.getChildCount('operatingPeriod')}" /><%--
            --%><g:if test="${operatingCount}"><%--
                --%><g:message code="main.list.operatingCount" args="[operatingCount]" /><%--
            --%></g:if><%--
    --%></td>
    <td></td>
</tr><%--
--%></g:each>

