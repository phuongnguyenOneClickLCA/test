<%@ page trimDirectiveWhitespaces="true" %>
<div class="container section">
    <div class="sectionheader">
        <div class="sectioncontrols pull-right">
            <%--
            <g:if test="${'operating' == portfolio?.type}">
                <g:set var="chosenOperatingPeriods" value="${session.chosenOperatingPeriods}" />
                <g:if test="${allOperatingPeriods}">
                    <g:set var="operatingPeriodCount" value="${allOperatingPeriods?.size()}" />
                    <g:set var="chosenPeriodCount" value="${chosenOperatingPeriods ? chosenOperatingPeriods.size() : operatingPeriodCount}" />
                    <a href="javascript: ;" onclick="$('#chooseOperatingPeriods').modal();"  class="btn btn-primary hide-on-print"><g:message code="portfolio.periods" args="[chosenPeriodCount, operatingPeriodCount]" /></a>
                </g:if>
            </g:if>
            --%>
        </div>

        <button class="pull-left sectionexpander"><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
         <h2><g:message code="entity.portfolio" /></h2>
    </div>

    <div class="sectionbody">
        <g:if test="${'design' == portfolio?.type}">
            <g:render template="/portfolio/designs"/>
        </g:if>
        <g:else>
            <g:render template="/portfolio/operating"/>
        </g:else>
    </div>
</div>