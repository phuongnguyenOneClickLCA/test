<%@ page import="com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.User; com.bionova.optimi.core.domain.mongo.Indicator; com.bionova.optimi.core.domain.mongo.Entity" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
    def indicatorService = grailsApplication.mainContext.getBean("indicatorService")
%>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
      <meta name="format-detection" content="telephone=no"/>
    <g:if test="${indicator?.report?.appliedStyleSheet}">
        <link rel="stylesheet" type="text/css" href="${indicator?.report?.appliedStyleSheet}" /> %{--static stylesheet url--}%
    </g:if>
      <asset:javascript src="portfolio_chart.js"/>
      <script>
          window.ChartV1 = Chart;
      </script>
      <asset:javascript src="chartJsV2.7.js"/>
      <asset:javascript src="highcharts.js" />
      <asset:javascript src="highcharts-more.js" />
      <asset:javascript src="sankey.js" />
      <asset:javascript src="treemap.js"/>
      <asset:javascript src="no-data-to-display.js"/>
      <asset:javascript src="drilldown.js"/>
      <asset:javascript src="exporting.js" />
      <asset:javascript src="export-data.js" />
      <asset:javascript src="moment.js" />
      <asset:javascript src="xlsx.full.min.js" />
      <asset:javascript src="offline-exporting.js"/>
      <asset:javascript src="no-data-to-display.js"/>
      <asset:javascript src="exportxlsx.js"/>
      <asset:javascript src="query/verificationPoints.js"/>
      <asset:javascript src="result/reportItem.js"/>
      <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>


      <script>
            window.ChartV2 = Chart;
            window.Chart = window.ChartV1;
        </script>
  </head>
  <body>
  <g:if test="${isCalculationRunning}">
      <opt:spinner spinnerId="loadingSpinner"/>
      <script>
          $(window).on('load', function () {
              $("#loadingSpinner").show()
              isCalculationRunningFunction()
          });

          const intervalTime = 2000
          let checkMessagesTimeout

          function isCalculationRunningFunction() {
              $.ajax({
                  url: "/app/sec/entity/isCalculationRunning",
                  contentType: "application/json; charset=utf-8",
                  data: JSON.stringify({
                      entityId: '${design?.id?.toString() ?: childEntity?.id?.toString()}',
                      indicatorId: '${indicator?.indicatorId}',
                      isResultPage: 'true'
                  }),
                  type: "POST",
                  success: function (data, textStatus) {
                      const isCalculationRunning = data.output

                      if (isCalculationRunning) {
                          checkMessagesTimeout = setTimeout(function () {
                              isCalculationRunningFunction();
                          }, intervalTime);
                      } else {
                          location.reload()
                      }
                  },
                  error: function (error, textStatus) {
                      console.log(error + ": " + textStatus)
                      checkMessagesTimeout = setTimeout(function () {
                          isCalculationRunningFunction();
                      }, intervalTime);
                  }
              })
          }
      </script>
  </g:if>
  <g:else>
      <div id="mainContent">
      <g:set var="currentUser" value="${userService.getCurrentUser()}"/>
      <g:set var="pageRenderTime" value="${System.currentTimeMillis()}" />
      <g:render template="/entity/results_info" />

      <div class="container section resultReportSection">
          <div class="container section hide show-on-print" id="resultReportHeadingsContainer">

            <div class="pull-left">
                <div class="tabsForResultReportWithoutSlide">
                    <ul>
                        <li><g:message code="results.report_title"/> <span style="font-weight:normal">${childEntity?.name ? childEntity.name : childEntity?.operatingPeriod}</span></li>
                    </ul>
                </div>
                <div class="info-section resultReportBorders">
                    <div class="entity-info-image text-center">
                    <div id="imageOfEntity" class="entityshow-pieInfoImage resultsImage"></div>
                </div>
                    <div class="entity-info-body" id="resultReportHeadingInfo">
                        <table class="table table-condensed">
                            <tbody>
                            <tr>
                                <th><g:message code="main.list.entity"/></th>
                                <td>${entity?.name} - ${childEntity?.name ? childEntity.name : childEntity?.operatingPeriod}</td>
                            </tr>
                            <g:set var="currentDate" value="${formatDate(date: new Date(), format: Constants.DATE_FORMAT.get(user?.localeString) ?: Constants.DATE_FORMAT.get(User.LocaleString.EUROPEAN.locale))}"/>
                            <tr>
                                <th><g:message code="user.name"/></th>
                                <td>${user?.name} - ${currentDate} </td>
                            </tr>
                            <tr>
                                <th><g:message code="entity.show.indicator"/></th>
                                <td>${indicatorService.getLocalizedName(indicator)}${indicatorLicenseName ?: ""}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}</td>
                            </tr>
                            <tr style="border-bottom: hidden; margin-bottom: -0px !important;">
                                <th style="vertical-align: top;"><g:message code="entity.details.detail.section.header"/></th>
                                <td class="localizedPurposeTd">
                                    <p id="localizedPurpose">${indicatorService.getLocalizedPurpose(indicator)}</p>
                                </td>
                            </tr>
                            <tr><th colspan="2">${message(code:"general_information.heading")}</th></tr>
                            ${basicQueryTable}

                            </tbody>
                        </table>



                    </div>
                </div>
            </div>
          </div>
          <g:if test="${indicator?.reportVisualisation?.contains('circularEconomyGraph')}">
              <div class="container sectionbody">
                  <div class ="circularChartResult" id="circularChartWrapper"></div>
              </div>
          </g:if>

          <g:set var="date" value="${formatDate(date: new Date(), format: 'dd.MM.yyyy HH:mm')}" />
          <div class="hiddenLoader"></div>
          <div class="sectionbody">
              <g:if test="${notInFilterMessage}">
                  <div class="alert">
                      <button type="button" class="close" data-dismiss="alert">×</button>
                      <strong>${notInFilterMessage}</strong>
                  </div>
              </g:if>
              <g:if test="${modifiable && !carbonDesignerAndResultsOnlyLicensed}">
                    <queryRender:renderIncompleteQueries entity="${childEntity}" parentEntity="${entity}" indicator="${indicator}" queries="${designQueries}" />
                </g:if>
              <g:if test="${indicator?.report}">
                  <g:if test="${nonProdLicenses}">
                      <div class="alert">
                          <strong>
                              <g:message code="license.non_commercial" />
                              <g:set var="licenseAmount" value="${nonProdLicenses.size()}" />
                              <g:each in="${nonProdLicenses}" var="license" status="index">
                                  <g:if test="${index < (licenseAmount - 1)}">
                                      ${license?.name}, <g:message code="license.type.${license?.type}" />, ${currentUser?.name},
                                  </g:if>
                                  <g:else>
                                      ${license?.name}, <g:message code="license.type.${license?.type}" />, ${currentUser?.name}
                                  </g:else>
                              </g:each>
                              ${date}
                          </strong>
                      </div>
                  </g:if>
                  <opt:backgroundImage indicator="${indicator}" />
                  <div class="reportContent">

                      <g:if test="${indicator?.report?.showEntityName}">
                          <h3>${entity?.name}: ${design?.name}</h3>
                      </g:if>

                  <%-- TOP CONTRIBUTORS --%>
                      <div class="impactsInTextFormat"></div>
                  <%-- CARBON HEROES BENCHMARK --%>
                      <g:if test="${indicatorBenchmarkLicensed && indicator.isBenchmarkable(entityType)}">
                          <div class="section collapsed">
                              <div class="sectionheader" onclick="toggleExpandSection(this);">
                                  <button class="pull-left sectionexpanderspec" >
                                      <i class="icon icon-chevron-down expander"></i>
                                      <i class="icon icon-chevron-right collapser"></i>
                                  </button>
                                  <h2 class="h2expanderspec" style="margin-left: 15px;"><g:message code="result.carbon_heroes_benchmark_heading"/> </h2>

                              </div>
                              <div class="sectionbody show-on-print" style="display: none">
                                  <div id="resultPageBenchmark" class="col" style="margin-left: 30px; padding-top:10px;padding-bottom:10px; padding-left:10px;display:inline-block;vertical-align: top">
                                      <div id="benchmarkEntityPageWrapper"></div>
                                  </div>
                              </div>
                          </div>
                      </g:if>

                  <%-- RESULT TABLE --%>
                      <div class="section">
                          <div class="sectionheader" onclick="toggleExpandSection(this)">
                              <button class="pull-left sectionexpanderspec" ><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
                              <h2 class="h2expanderspec" style="margin-left: 15px;"><g:message code="entity.details.tab.datasummary"/></h2>

                          </div>
                          <div class="sectionbody show-on-print">
                              <div id="table${indicator.indicatorId}" class="table">

                                  <calc:renderIndicatorReport indicator="${indicator}" report="${indicator.report}" entity="${childEntity}" parentEntity="${entity}" nmdElementList="${nmdElementList}"/>

                              </div>
                          </div>
                      </div>

                  <%-- SCOPE and Lca Cheker SECTION --%>
                      <g:if test="${lcaCheckerAllowed}">
                          <tmpl:/query/scopeLcaCheckerDiv entity="${(Entity)childEntity}" indicator="${(Indicator)indicator}"/>
                      </g:if>

                  <%-- TOP CONTRIBUTORS --%>
                      <g:if test="${!childEntity.operatingPeriod}">
                          <g:if test="${indicator?.reportVisualisation?.contains("topContributors")}">
                              <g:set var="firstRule" value="${indicator.displayResult? graphCalculationRules.find({it.calculationRuleId.equals(indicator.displayResult)}) : graphCalculationRules.get(0)}"/>
                              <div id="highImpactResources" class="section collapsed highHimpacts">
                                  <div class="div-scope sectionheader collapsibleImpact"
                                       onclick="getHighImpactResourcesOnly('${childEntity?.id}', '${indicator?.indicatorId}',
                                           '${firstRule?.calculationRuleId}', '${entity.id}', '${userService.getAccount(user)?.id}', false,
                                           toggleCollapsibleImpact);">

                                      <button class="pull-left sectionexpanderspec" >
                                          <i class="icon icon-chevron-down expander"></i>
                                          <i class="icon icon-chevron-right collapser"></i>
                                      </button>

                                      <h2 style="margin-left: 8px" id="highImpactResourcesHeading">
                                          <g:message code="results.contributing_impacts"/>
                                      </h2>
                                  </div>
                              </div>
                          </g:if>
                      </g:if>

                      <div class="section">
                          <div class="sectionheader" onclick="toggleExpandSection(this)">
                              <div class="inline-block" style="width: 100%">
                                  <button class="pull-left sectionexpanderspec" ><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
                                  <h2 class="h2expanderspec"><g:message code="result_visualisation"/></h2>
                                  <g:if test="${!(indicator?.requireMonthly && indicator?.reportVisualisation?.contains("resultTableGraph"))}">
                                      <div id="btnGroupResultClassList" class="pull-right" onclick="event.stopPropagation()"></div>
                                  </g:if>
                              </div>

                          </div>
                          <div class="sectionbody show-on-print">
                          <%-- OPERATING CHARTS  --%>
                              <g:if test="${indicator?.requireMonthly}">
                                  <div id="graphWrapperMonthly" class="hidden" style="padding-bottom: 50px;">
                                      <div class="btn-group pull-right" >
                                          <a href="javascript:" id="drawTotal"  class="btn btn-small bold" onclick="drawTotal('${childEntity?.id}', '${indicator.indicatorId}')">Total</a>
                                      </div>
                                      <div class="btn-group pull-right" style="display:inline-block;">
                                          <a href="javascript:" class="dropdown-toggle btn btn-small bold" id="operatingDenomGraphDropdown" data-toggle="dropdown">By denominator <span class="caret"></span></a><ul id="denomList"class="dropdown-menu"></ul>
                                      </div>
                                      <div id="graphContentMonthly">
                                      </div>
                                  </div>
                              </g:if>
                              <g:elseif test="${indicator?.reportVisualisation?.contains("resultTableGraph")}">
                                  <div id="graphWrapperRegular">
                                      <div id="graphContentRegular" class="hidden"></div>
                                  </div>
                              </g:elseif>
                              <g:else>
                                  <g:if test="${graphCalculationRules && chartsToRender?.findAll({it.value})}">
                                  <g:render template="/entity/resultCharts" model="[indicator:indicator,allClassificationQsMap:additionalQuestions, defaultFallbackClassification:defaultFallbackClassification,
                                                                                    entity:childEntity,groupingQuestion:groupingQuestion, graphCalculationRules:graphCalculationRules,groupingQuestion:groupingQuestion,chartsToRender:chartsToRender]"/>
                                  </g:if>
                              </g:else>
                               </div>
                      </div>
                      <opt:spinner/>


                  </div>


                 <g:if test="${"figbc".equals(channelToken)}">
                     <div class="pageBreak" style="position: relative;${index > 0 ? ' page-break-before: always;' : ''}"></div>
                 </g:if>


              </g:if>
              <g:else>
                <h2><span class="hide show-on-print">${indicatorService.getLocalizedResultsHeadline(indicator)}</span></h2>

                <g:if test="${nonProdLicenses}">
                    <div class="alert">
                        <strong>
                            <g:message code="license.non_commercial" />
                            <g:set var="licenseAmount" value="${nonProdLicenses.size()}" />
                            <g:each in="${nonProdLicenses}" var="license" status="index">
                                <g:if test="${index < (licenseAmount - 1)}">
                                    ${license?.name}, <g:message code="license.type.${license?.type}" />, ${currentUser?.name},
                                </g:if>
                                <g:else>
                                    ${license?.name}, <g:message code="license.type.${license?.type}" />, ${currentUser?.name}
                                </g:else>
                            </g:each>
                            ${date}
                        </strong>
                    </div>
                </g:if>

                <%-- SCOPE and Lca Cheker SECTION --%>
               <g:if test="${lcaCheckerAllowed}">
                   <tmpl:/query/scopeLcaCheckerDiv entity="${(Entity)childEntity}" indicator="${(Indicator)indicator}"/>
               </g:if>

                <p>${indicatorService.getLocalizedPurpose(indicator)}</p>


                <div class="row-fluid">
                  <div class="span12">
                    <g:if test="${!indicator.assessmentMethod == 'complex'}">
                      <g:render template="/entity/defaultResultTable"/>
                    </g:if>
                    <g:if test="${indicator.assessmentMethod == 'complex'}">
                      <g:render template="/entity/complexresulttable" model="[entity: childEntity, parentEntity: entity, indicator: indicator]" />
                    </g:if>
                   </div>
                 </div>

                <g:if test="${additionalInfos}">
                  <div class="accessibility_lower">
                    <p><strong><g:message code="results.additional_info" /></strong></p>
                      <ul>
                        <g:each in="${additionalInfos}" var="additionalInfo">
                          <li>${additionalInfo}</li>
                        </g:each>
                      </ul>
                  </div>
                </g:if>
              <%-- ANNUAL CHARTS --%>
                  <div id="annualAccumulationContainer"></div>
           <%-- Life-cycle bar chart --%>

                  <div id="lifeCycleStageChart"></div>
           <%-- ALL BREAKDOWN CHARTS --%>

               <g:if test="${indicator?.reportVisualisation?.contains("stackedBarChart")}">

                   <div id="allBreakDowns" class="lifeCycleImpactsChartContainer container">
                       <opt:spinner/>
                   </div>
                   <div class="container text-center">
                       <div class="btn-group" style="padding: 20px !important;">
                           <a href="javascript:" class="btn btn-primary ${graphCalculationRules?.size() > 1 ?'' : 'hidden'}" id="showAllBreakDowns"><i class="fa fa-plus" style="vertical-align:middle;"></i> <g:message code="results.show_all_breakdowns"/></a>
                       </div>
                   </div>
               </g:if>
              </g:else>
              <div class="clearfix"></div>
              <g:if test="${indicator && !indicator.hideSourceListing}">
              <div class="section collapsed clearfix">
                  <div class="sectionheader" onclick="toggleExpandSection(this);" id="dataSourceTable">
                      <button class="pull-left sectionexpanderspec" ><i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></button>
                      <h2 class="h2expanderspec" style="margin-left: 15px;"><g:message code="results.show_sources"/> </h2>

                  </div>
                  <div id="dataSourcesContainer" class="sectionbody show-on-print" style="display: none">
                      <opt:spinner spinnerId="loadingSpinner-dataSource" display="true"/>
                  </div>
              </div>
              </g:if>
          </div>

      </div>
          <div id="png-container"></div>
      <div class="modal hide modal large" id="indicatorBenchmarkModal">
          <div class="modal-header text-center"><button type="button" class="close" data-dismiss="modal">&times;</button>
              <h2 id="indicatorBenchmarkModalHeading"></h2>
          </div>
          <div class="modal-body" id="indicatorBenchmarkModalBody"></div>
      </div>

      <div id="lcaCheckerNoGraphContainer"></div>

      <div class="overlay" id="wordDocGenOverlay">
          <div class="overlay-content" id="wordDocGenOverlayContent">
          </div>
      </div>
      </div>
      <g:set var="lcaCheckerAllowed" value="${entity.lcaCheckerAllowed && indicator?.enableLcaChecker}" />

      <script type="text/javascript">
          $(function () {
              $('#dataSourcesButton').on('click', function () {
                  this.remove();
                  showSources('sourceListingTable', 'showHideSources');
              });
              $('.sectionheader_level2').on('click', function () {
                  const dataReportItemIds = $(this).data("reportItemIds");
                  if (dataReportItemIds) {
                      var reportItemIds = dataReportItemIds.split(".");
                      loadAllReportItems(reportItemIds);
                  }
              });
              <g:if test="${graphCalculationRules}">
                  <g:set var="firstRule" value="${indicator.displayResult? graphCalculationRules.find({it.calculationRuleId.equals(indicator.displayResult)}) : graphCalculationRules.get(0)}"/>

                  loadImage('imageOfEntity', '${entity?.id?.toString()}', '${entity?.entityClass}', false);

                  <g:if test="${indicator?.reportVisualisation?.contains("detailedBarCharts")}">
                      $('#impactCategoryForHeading').append('${firstRule?.localizedName} ${firstRule?.localizedShortName ? "(" + firstRule?.localizedShortName + ")"  : "" }');
                  </g:if>
                  <g:if test="${!childEntity.operatingPeriod}">
                      <g:if test="${indicator?.reportVisualisation?.contains("topContributors")}">
                        getHighImpactResources('${childEntity?.id}', '${indicator?.indicatorId}', '${firstRule?.calculationRuleId}', '${entity.id}', '${userService.getAccount(user)?.id}');
                      </g:if>
                  </g:if>
              </g:if>

              var helpPopSettings = {
                  placement: 'top',
                  template: '<div class="popover popover-fade" style="display: block; max-width: 300px;"><div class="arrow"></div><div style="font-weight: normal !important;" class="popover-content"></div></div>'
              };
              $(".tableHeadingPopover[rel=popover]").popover(helpPopSettings)
              var designInfoSettings = {
                  placement:'bottom',
                  content:"<div class=\"entity-info-body\"><table class=\"table table-condensed\"><tbody><tr><th><g:message code='main.list.entity'/></th><td>${entity?.name?.encodeAsHTML()} - ${childEntity?.name ? childEntity.name?.encodeAsHTML() : childEntity?.operatingPeriod?.encodeAsHTML()}</td> </tr><tr> <th><g:message code='user.name'/></th> <td>${user?.name?.encodeAsHTML()} - ${currentDate} </td> </tr> <tr> <th><g:message code='entity.show.indicator'/></th> <td>${indicatorService.getLocalizedName(indicator)?.encodeAsHTML()}${indicator.deprecated ? ' <span class=\"warningRed\">' + message(code: 'deprecated') + '</span>': ''}</td> </tr><tr style=\"border-bottom: hidden; margin-bottom: -0px !important;\"> <th style=\"vertical-align: top;\"><g:message code='entity.details.detail.section.header'/></th> <td class=\"localizedPurposeTd\"> <p>${indicatorService.getLocalizedPurpose(indicator)?.encodeAsHTML()}</p> </td> </tr><tr><th colspan=\"2\">${message(code:'general_information.heading')}</th></tr>${basicQueryTable} </tbody> </table> </div>",
                  template: '<div class="popover popover-fade" style="display: block; max-width: 500px;"><div class="arrow"></div><div class="popover-content"></div></div>',
                  trigger:'click',
                  html:true,
                  container:'body'
              };
              $('.entityInfoResultReport').popover(designInfoSettings);
          });

      </script>
      <g:if test="${indicator?.requireMonthly && indicator?.reportVisualisation?.contains("resultTableGraph")}">
      <script type="text/javascript">
          $(function () {
              drawMonthlyChart('${childEntity?.id}', '${indicator.indicatorId}')
          });
          function drawTotal (entityId, indicatorId, denominatorId){
              var queryString;
              if (denominatorId !== undefined) {
                  queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&denominatorId=' + denominatorId;
              }else {
                  queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;
              }
                  $.ajax({
                      type: 'POST',
                      data: queryString,
                      url: '/app/sec/entity/monthlyGraph',
                      success: function (data, textStatus) {
                          if (data.output) {
                              $('#denomList').empty().append('<g:each in="${indicator?.denominatorListAsDenominatorObjects?.findAll({it.requireMonthly})}" var="denom">\n' + '<li><a href="javascript:" onclick="drawByDenominator(\'${entity.id}\',\'${indicator.indicatorId}\', \'${indicator.requireMonthly}\', \'${denom.denominatorId}\');"><span>${denom.localizedName ? denom.localizedName : denom.label ?: denom.denominatorId}</span></a></li>\n' +
                                  '</g:each>');
                              $('#graphContentMonthly').empty().append(data.output);
                          }else {
                              $('.nograph').show();

                          }
                      },
                      error: function (XMLHttpRequest, textStatus, errorThrown) {
                      }
                  });
          }
          function drawMonthlyChart (entityId, indicatorId, denominatorId) {
              if ($("#graphWrapperMonthly").hasClass('hidden')){
                  $("#appendGraph").html('Hide Chart')
              } else {
                  $("#appendGraph").html('Show Chart')
              }
              $('#graphWrapperMonthly').toggleClass('hidden');
              drawTotal(entityId, indicatorId, denominatorId)


          }
          function drawByDenominator(entityId, indicatorId, monthly, denominatorId){
              var queryString;

              if (denominatorId && "undefined" !== denominatorId) {
                  queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId + '&denominatorId=' + denominatorId;
              } else {
                  var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;
              }
              $.ajax({
                  type: 'POST',
                  data:queryString,
                  url: '/app/sec/entity/drawMonthlyOperating',
                  success: function (data, textStatus) {
                      if (data.output) {
                          $('#graphContentMonthly').empty().append(data.output);

                      } else {
                          $('.nograph').show();

                      }
                  },
                  error: function (XMLHttpRequest, textStatus, errorThrown) {
                  }
              });
          }
      </script>
      </g:if>
      <g:if test="${indicator?.reportVisualisation?.contains("resultTableGraph") && !indicator?.requireMonthly}">
          <script type="text/javascript">

              $('#firstHeading').append('<a href="javascript:" onclick=\"drawResultTableGraph(\'${childEntity?.id}\', \'${indicator?.indicatorId}\')"> <i class="fas fa-chart-area"></i>${message(code: 'result.montlhy_chart')}</a>');

              function drawResultTableGraph (entityId, indicatorId) {
                  var graphContentRegular = $('#graphContentRegular');
                  $.ajax({
                      type: 'POST',
                      data: 'entityId=' + entityId + '&indicatorId=' + indicatorId,
                      url: '/app/sec/entity/resultTableGraph',
                      beforeSend: function () {
                          $(graphContentRegular).empty();
                          $('#table${indicator.indicatorId}').fadeOut("slow").addClass('hidden');
                      },
                      success: function (data, textStatus) {
                          if (data.output) {
                              $(graphContentRegular).fadeIn(2000).removeClass('hidden');

                              $(graphContentRegular).append(data.output);
                          }else {
                              $('.nograph').show();

                          }
                      },
                      error: function (XMLHttpRequest, textStatus, errorThrown) {
                      }
                  });
              }

          </script>
      </g:if>
      <g:if test="${indicator.report}">
          <script type="text/javascript">
              $(document).ready(function() {
                  $('.sectionexpander').on('click', function (event) {
                      var section = $(this).parent().parent();
                      $(section).toggleClass('collapsed');
                      $(section).find('.sectioncontrols').fadeToggle(1);
                      $(section).find('.sectionbody').fadeToggle(1);
                  });

                  $('.h2expander').on('click', function (event) {
                      var section = $(this).parent().parent();
                      $(section).toggleClass('collapsed');
                      $(section).find('.sectioncontrols').fadeToggle(1);
                      $(section).find('.sectionbody').fadeToggle(1);

                  });
              });
          </script>
          <g:if test="${indicatorBenchmarkLicensed && indicator.isBenchmarkable(entity?.typeResourceId)}">
              <script type="text/javascript">
                  $(document).ready(function() {
                      appendIndicatorBenchmark('${indicator.indicatorId}','${childEntity.id}','${entity?.typeResourceId}','${childEntity.operatingPeriodAndName}','${entity.name}', '${entity.countryResourceResourceId}', '',true);
                  });
              </script>
          </g:if>
      </g:if>
      <g:if test="${indicator && !indicator.hideSourceListing}">
          <script defer>
              $(document).ready(function() {
                  renderSourceListing('${indicator.indicatorId}','${entity?.id}','${childEntity?.id}','${entity.countryForPrioritization}','${entity.country}', true);
              });
          </script>
      </g:if>
      <g:if test="${!user?.disableZohoChat}">
          <!-- Start of oneclicklca Zendesk Widget script -->
          <script id="ze-snippet" src="https://static.zdassets.com/ekr/snippet.js?key=788c0e91-3199-4f3d-8472-17fa41abb688"> </script>
          <script>
              zE(function() {
                  zE.identify({
                      name: '${user?.name}',
                      email: '${user?.username}'
                  });
              });
              window.zESettings = {
                  webWidget: {
                      <g:if test="${!chatSupportLicensed}">
                      chat: {
                          suppress: true
                      },
                      </g:if>
                      <g:if test="${!userService.getContactSupportAvailable(user)}">
                      contactForm: {
                          suppress: true
                      },
                      </g:if>
                      contactOptions: {
                          enabled: true
                      }
                  }
              };
          </script>
          <!-- End of oneclicklca Zendesk Widget script -->
      </g:if>
      <g:set var="pageRenderTime" value="${(System.currentTimeMillis() - pageRenderTime) / 1000}" />
      <script type="text/javascript">
          // THE FOLLOWING NEEDS TO ALWAYS BE LAST IN PAGE RENDER:
          var domReady;
          var windowReady;

          $(document).ready(function() {
              domReady = (Date.now() - timerStart) / 1000;
              keepPopoverOnHover('.stickOnHover', 100)
          });
          $(window).on('load', function() {
              windowReady = ((Date.now() - timerStart) / 1000) - domReady;
              renderPageRenderTimes("${showRenderingTimes ? 'true' : 'false'}", "${secondsTaken}", "${pageRenderTime}", domReady, windowReady)
          });
          // END
      </script>
  </g:else>
  </body>
</html>



