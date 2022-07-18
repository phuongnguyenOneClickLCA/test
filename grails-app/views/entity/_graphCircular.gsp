<%@ page import="org.apache.commons.lang.StringUtils" %>

<asset:javascript src="portfolio_chart.js"/>
<g:if test="${draw}">
    <div class="pieContainer circularChartWrap text-center">
        <div class="text-center">
        <h3 class="bold" >${indicator?.localizedName} <i class="fa fa-question-circle helpCircularChart" rel="popover" data-trigger="hover"></i></h3>
        </div>
        <object id ="svg-object">
        <?xml version="1.0" encoding="UTF-8"?>
            <svg width="${width}px" height="${height}px" viewBox="-50 0 3938 1950" version="1.1" xmlns="http://www.w3.org/2000/svg">
                <!-- Generator: Sketch 53.2 (72643) - https://sketchapp.com -->
                <title>Untitled</title>
                <desc>Created with Sketch.</desc>
                <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                    <g id="graphwithtext" transform="translate(1.000000, 1.000000)">
                        <g id="Group" transform="translate(1075.000000, 138.000000)" fill-rule="nonzero">
                            <rect id="Rectangle" fill="#8DC641" x="0.28033" y="258" width="1962.7196" height="119"></rect>
                            <rect id="rect78" fill="#EF1C39" x="0.28033" y="0" width="1962.7196" height="119"></rect>
                            <rect id="rect80" fill="#00845A" x="0.28033" y="387" width="1962.7195" height="119"></rect>
                            <rect id="rect82" fill="#F78521" x="0.28033" y="129" width="1962.7196" height="119"></rect>
                            <polygon id="polygon84" fill="#F78521" points="1965 1610 315.597981 1610 262 1562.60504 315.597981 1516 1965 1516"></polygon>
                            <polygon id="polygon85" fill="#FFCC00" points="1965 1507 543.2019 1507 497 1460.10924 543.2019 1414 1965 1414"></polygon>
                            <polygon id="polygon86" fill="#EF1C39" points="1965 1711 62.0170065 1711 0 1664.10924 62.0170065 1618 1965 1618"></polygon>
                            <path d="M2860.99998,0.120403198 L2860.99998,501.312054 C2856.02581,501.104705 2851.02512,501 2845.99998,501 C2650.49118,501 2491.99999,659.491199 2491.99999,855 C2491.99999,1050.5088 2650.49118,1209 2845.99998,1209 C2851.02512,1209 2856.02581,1208.89529 2860.99998,1208.68795 L2860.99998,1710.8796 C2856.17627,1710.95976 2851.34282,1711 2846.49998,1711 C2529.74945,1711 2253.20517,1538.85655 2105.3179,1283.0204 C2032.60975,1157.23964 1991,1011.22906 1991,855.5 C1991,383.020397 2374.02039,0 2846.49998,0 C2851.34282,0 2856.17627,0.0402398766 2861,0.120403511 L2860.99998,0.120403198 Z" id="Combined-Shape" stroke="#979797" fill="#D8D8D8" transform="translate(2426.000000, 855.500000) scale(-1, 1) translate(-2426.000000, -855.500000) "></path>
                        </g>
                        <a data-toggle="popover" data-container=".circularChartWrap" data-content="${message(code:'materialRecovered_help')}" data-placement="right"><text x="-120" y ="80" class="questionMarkSolid" font-size="80" fill="#000000" >&#xf059</text></a>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="700">
                           <tspan x="-40" y="80" id="Material-Recovered" f><g:message code="material_recovered"/></tspan><tspan  id="materialRecoveredIndex"x="1050" text-anchor="end"> </tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="223" id="virginMaterialsUsed"></tspan><tspan id="redIn"x="1050" text-anchor="end"> </tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="351" id="renewabled"></tspan><tspan id="orgIn" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="479" id="recycled"></tspan><tspan  id="lgreenIn" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="607" id="reused"></tspan><tspan  id="dgreenIn" x="1050" text-anchor="end"></tspan>
                        </text>
                        <a data-toggle="popover" data-container=".circularChartWrap" data-content="${message(code:'materialReturn_help')}" data-placement="right"><text x="-120" y ="1176" class="questionMarkSolid" font-size="80" fill="#000000" >&#xf059</text></a>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="700">
                            <tspan x="-40" y="1176" id="Material-Returned"><g:message code="material_returned"/></tspan><tspan  id="materialReturnedIndex"x="1050" text-anchor="end"> </tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="1309" id="EOL-reuseMaterial"></tspan><tspan  id="dgreenOut" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="1568" id="EOL-downcycled"></tspan><tspan id="yellowOut" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="1437" id="EOL-recycling"></tspan><tspan  id="lgreenOut" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="1696" id="EOL-reuseEnergy"></tspan><tspan  id="orgOut" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="80" font-weight="normal">
                            <tspan x="-40" y="1824" id="EOL-disposal"></tspan><tspan  id="redOut" x="1050" text-anchor="end"></tspan>
                        </text>
                        <text  x="2920" y="1050" fill="#000000" font-family="Avenir, Arial, sans-serif" font-size="150" font-weight="700">
                            <tspan id="index"  dx="1.2rem"></tspan>
                        </text>
                    </g>
                    <path d="M2781.73344,655 L2868,655 L2868,706.742827 C2762.97631,765.631861 2692,878.027228 2692,1007 C2692,1196.98595 2846.01405,1351 3036,1351 C3037.66947,1351 3039.33616,1350.98811 3041,1350.9644 L3041,1439.96582 C3039.16938,1439.98859 3037.33602,1440 3035.5,1440 C2796.08456,1440 2602,1245.91544 2602,1006.5 C2602,861.850701 2672.84665,733.748525 2781.73344,655 Z" id="Path" fill="#00845A"></path>
                    <polygon id="Path" fill="#00845A" points="2912 644.999998 3041 644.999998 2912 645"></polygon>
                    <path d="M2712,655 L2712,705.452394 C2636.99531,784.734858 2591,891.746039 2591,1009.5 C2591,1253.886 2789.114,1452 3033.5,1452 C3036.00492,1452 3038.50498,1451.97919 3041.00001,1451.93772 L3041,1542.97239 C3039.16885,1542.99078 3037.3355,1543 3035.5,1543 C2739.19923,1543 2499,1302.80077 2499,1006.5 C2499,872.04807 2545.6622,749.147771 2627.18483,655 L2712,655 Z" id="Path" fill="#8DC641"></path>
                </g>
            </svg>

        </object>
    </div>
    <script>
        window.FontAwesomeConfig = {
            searchPseudoElements: true
        }
    </script>
    <script>
        $("#resultReportHeadingsBox").css("width","90%");
        $('.helpCircularChart[rel=popover]').popover({
            content:"${message(code:'circularChart_help')}",
            placement:'bottom',
            template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
            trigger:'hover',
            html:true
        });
        $(document).ready(function(){
            $('[data-toggle="popover"]').popover({
                template: '<div class="popover"><div class="arrow"></div><div class="popover-content ribaHelp" style="font-size: 12px !important; font-weight: normal !important;"></div></div>',
                trigger: 'hover',
                html:'true'
            });
        });
    </script>
    <script><![CDATA[
    var dataset = ${graphDataset};
    $.each(dataset,function(k,v){
        var id = "#" +k;
        var text = $(id).text() + " " + v + " %";
        $(id).text(text);
    })
    var labelset = ${graphLabels};
    $.each(labelset, function(k,v){
        var id = "#" +k;
        $(id).text(v);
    })
    ]]>
    </script>
</g:if><g:else>
    <div class="pieContainer bold entityPieChartWrap" >
        <p style="margin-left: 30px; padding-top: 20px;"><g:message code="no_structure_chart_found"/></p>
        <div style="width: 300px"><i class="fa fa-ban fa-align-center fa-fulldiv" aria-hidden="true"></i></div>
    </div>
</g:else>

