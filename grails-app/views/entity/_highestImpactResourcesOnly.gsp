<%@ page import="com.bionova.optimi.core.Constants" %>
<script>
    $(function () {
        $('.sustainedWarning').popover({
            container: 'body'
        })
    });

    <g:if test="${!betaFeaturesLicensed}">
    $(function () {
        var nonLicensedLinks = $('.enterpriseCheck');
        $(nonLicensedLinks).each(function () {
            $(this).popover({
                placement: 'top',
                template: '<div class="popover dataSourcesToolTip"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                trigger: 'hover',
                html: true,
                content: '<g:message code="enterprise_feature_warning" args="[enterpriseFeatureName]"/>'
            });
        });

    });
    </g:if>
</script>

<div class="div-scope sectionheader collapsibleImpact"
     onclick='toggleExpandSection(this, false, "${addToCompareLicensed && resources ? "#compareButtonHighestImpact" : ""}");'>
    <div class="inline-block" style="width: 100%">

        <button class="pull-left sectionexpanderspec">
            <i class="icon icon-chevron-down expander"></i>
            <i class="icon icon-chevron-right collapser"></i>
        </button>

        <h2 id="highImpactResourcesHeading" class="h2expanderspec">
            <g:message code="results.contributing_impacts"/> (${calculationRuleName})
        </h2>
        <g:if test="${addToCompareLicensed && resources}">
            <div class="btn-group pull-right hide-on-print">
                <opt:renderCompareDataBtn parentEntity="${parentEntity}"
                                          materialCompareEntity="${materialCompareEntity}"
                                          onclick="stopBubblePropagation(event);"
                                          params="[parentEntityId: parentEntity?.id]"
                                          elementId="compareButtonHighestImpact" controller="entity"
                                          action="resourcesComparisonEndUser"
                                          class="btn btn-primary hidden compareButtonTotal" target="_blank"/>
            </div>
        </g:if>
    </div>
</div>

<div class="sectionbody" style="display: none">
    <g:if test="${resources}">
        <g:set var="entityService" bean="entityService"/>
        <g:set var="optimiResourceService" bean="optimiResourceService"/>
        <table class="table table-striped table-condensed">
            <thead>
            <tr>
                <th>No.</th>
                <th><g:message code="query.material"/></th>
                <th class="number"><g:message code="cradle_gate_impact"/></th>
                <th class="number"><g:message code="share_in_a1_a3"/></th>
                <th class="number hidden"><g:message code="share_in_life_cycle"/></th>
                <th><g:message code="sustainable_alternatives"/></th>

            </tr>
            </thead>
            <tbody>
            <g:set var="resourceIdsList" value="${resources?.collect({ it.resourceId })}"/>
            <g:set var="resourcesAddedToCompareStatus"
                   value="${entityService.areResourcesAlreadyAdded(resourceIdsList, materialCompareEntity,
                           Constants.COMPARE_QUERYID)}"/>
            <g:each in="${resources}" var="resource" status="index">
                <g:set var="random" value="${UUID.randomUUID().toString()}"/>
                <tr>
                    <td>${index + 1}.</td>
                    <td>${optimiResourceService.getLocalizedName(resource)}
                        <g:if test="${emissionCloudsForResources?.get(resource.resourceId)}">
                            <div style="bottom:-0.5px !important;" class="co2CloudContainer"><i
                                    class="fa fa-cloud ${emissionCloudsForResources?.get(resource.resourceId)}"></i>

                                <p class="co2Text">co<sub>2</sub></p></div>
                        </g:if>
                        <opt:renderDataCardBtn indicatorId="${indicator?.indicatorId}"
                                               resourceId="${resource?.resourceId}"
                                               profileId="${resource?.profileId}" childEntityId="${childEntityId}"
                                               showGWP="true" infoId="${random}"/>
                    </td>
                    <td class="number">${formattedImpactPerResourceAndDataset?.get(resource)} ${unitPerResourceAndDataset?.get(resource)}</td>
                    <td class="number">
                        ${percentageScores?.get(resource)} %
                    </td>
                    <td class="number hidden">
                        ${percentageScoresFullLifeCycle?.get(resource)} %
                    </td>
                    <td>
                        <g:if test="${betaFeaturesLicensed && indicator?.connectedBenchmarks /*&& resource.benchmark?.ranking*/}">
                            <span style="bottom: -2px !important;line-height: 20px !important;"><a
                                    href="javascript:" class="sustainableAlternativesLink"
                                    id="sustainableAlternatives_${resource.id}"
                                    onclick="sustainablealternatives('${resource.resourceId}', '${parentEntity?.countryForPrioritization}',
                                        '${resource.resourceSubType}', '${indicator.connectedBenchmarks.first()}', '${parentEntity?.country}', '${parentEntity?.id}', '${indicator?.indicatorId}');"><g:message
                                        code="resource.show_sustainable_alternatives"/></a></span>
                        </g:if>
                        <g:elseif test="${!betaFeaturesLicensed}">
                            <span style="bottom: -2px !important;line-height: 20px !important;"><a
                                    href="javascript:" class="sustainableAlternativesLink enterpriseCheck"
                                    id="sustainableAlternatives_${resource.id}"><g:message
                                        code="resource.show_sustainable_alternatives"/></a></span>
                        </g:elseif>
                        <g:if test="${!resource?.active}">
                            <span><img class="sustainedWarning" rel="popover" data-trigger="hover"
                                       data-placement="top"
                                       data-content="<g:message code="resource.show_sustainable_warning"/>"
                                       src="/app/assets/img/icon-warning.png"
                                       style="max-width:16px; vertical-align: baseline;"/></span>
                        </g:if>
                    </td>
                    <g:if test="${addToCompareLicensed}">
                        <td>
                            <g:if test="${resource?.active}">
                                <g:if test="${resourcesAddedToCompareStatus?.get(resource.resourceId)}">
                                    <a href="javascript:;" class="just_black" rel="popover" data-placement="bottom"
                                       data-trigger="hover" data-html="true"
                                       data-content="${message(code: 'resource_already_added')}">${message(code: "add_to_compare")}</a>
                                </g:if>
                                <g:else>
                                    <a href="javascript:;"
                                       onclick="addSingleResourceToCompare('${resource.resourceId}', '${parentEntity?.id}', '${indicator?.indicatorId}', '${resource.profileId}')">${message(code: "add_to_compare")}</a>
                                </g:else>
                            </g:if>
                        </td>
                    </g:if>
                </tr>

                <div class="modal hide modal sustainableAlternatives"
                     id="sustainableAlternativesModal${resource?.resourceId}">
                    <div class="modal-header text-center"><button type="button" class="close"
                                                                  data-dismiss="modal">&times;</button>

                        <h2>${message(code: 'resource.sustainable_alternatives')} ${parentEntity?.country} ${message(code: 'and_neighbours')}</h2>
                    </div>

                    <div class="modal-body sustainableAlternativesBody"
                         id="sustainableAlternativesBody${resource?.resourceId}">
                    </div>
                </div>
            </g:each>
            </tbody>
        </table>
    </g:if>
</div>