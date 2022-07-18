<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.bionova.optimi.core.domain.mongo.Indicator; com.bionova.optimi.core.domain.mongo.Entity" %>
<%-- Copyright (c) 2012 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2017.2.621/js/jszip.min.js"></script>
    <script src="https://kendo.cdn.telerik.com/2017.2.621/js/kendo.all.min.js"></script>
    <style type="text/css">
    .backgroundImage {
        z-index: -1;
        left: -3px;
        width: 760px;
    }
    @font-face {
        font-family: 'Poppin bold';
        src: url("/app/assets/Poppins-Bold.ttf");
    }
    @font-face {
        font-family: 'Poppin regular';
        src: url("/app/assets/Poppins-Medium.ttf");
    }
    .fontHeader {
        font-family: 'Poppin bold' !important;
    }
    .fontBody, p,label,table,th{
        font-family: 'Poppin regular' !important;
    }
    .greenP {
        color:  rgb(0, 97, 68);
    }
    .darkGrayP {
        color: #696969;
    }
    .whiteP {
        color: white;
    }
    .h2 {
        font-size: 32px;
        line-height: 50px;
    }
    .h3 {
        font-size: 24px;
        line-height: 40px;
    }
    .h4 {
        font-size: 12px;
        line-height: 26px;
    }
    .content-container {
        position: absolute;
        z-index: 2;
        margin-top: 100px;
        width: 650px;
        word-wrap: break-word;
    }
    p {
        line-height: 22px;
    }
    .pageBreak{
        background-repeat: no-repeat;
        padding-top: 0;
        padding-left: 50px;
        height: 1100px;
        width: 105%;
    }
    .th {
        background-color: rgb(0, 97, 68) !important;
        color: #ffffff;
        font-family: 'Poppin regular' !important;
    }
    td {
        background-color: transparent !important;
    }
    .rowBackground {
        background-color: rgba(0, 97, 68,0.5) !important;
    }
    .divider {
        border-top: 1px solid lightgrey;
        margin-bottom: 10px;
    }
    .col3-container{
        width: 28%;
        padding: 5px;
        display: inline-block;
        vertical-align: top;
        max-height: 60px;
        overflow: hidden;
    }
    .col2-container{
        width: 48%;
        padding: 5px;
        display: inline-block;
    }
    .subText {
        font-size: 11px;
    }
    .chart-container {
        height: 200px;
    }
    table {
        font-size: 9px !important;
        width: 650px !important;
        word-break: break-all!important;
        white-space: normal !important;
    }
    td,th {
        padding: 1px 2px 1px 2px !important;
    }
    .icon {
        height: 30px;
    }
    .chart-lg {
        max-height: 280px;
    }
    .benchmarkContainer{
        display:inline-block;
        vertical-align: top;
        padding-top:12px;
        background: white;
    }
    .benchmarkImage {
        width: 180px ;
        padding-bottom: 10px;
    }

    </style>
</head>

<body>
<div class="container">
    <div class="screenheader hide-on-print">
        <div class="pull-right">
            <opt:link controller="design" action="results" class="btn hide-on-print" params="[childEntityId: design?.id, indicatorId: indicator?.indicatorId]"></i>
                <i class="icon-chevron-left"></i> <g:message code="back"/></opt:link>
            <button class="btn btn-primary" onclick="ExportPdf()"><i class="icon-print icon-white"></i> <g:message
                    code="download_pdf"/></button>
        </div>

        <h4> <span id="mainProjectLink">
            <opt:link controller="entity" action="show" id="${parent?.id}">
                ${parent.name}
            </opt:link> </span> > <strong>${design.name} </strong> > <g:message code="planetary_report"/> <br/> </h4>
            <h2><g:message code="planetary_report"/></h2>
    </div>
</div>

<g:render template="/entity/planetaryReportTemplate" model="[indicator: indicator, parent: parent, design: design,
                                                             reportDate: reportDate, user: user, account: userService.getAccount(user),
                                                             grossArea: grossArea, pdfGraphsForSession: pdfGraphsForSession,
                                                             calculationRuleId: calculationRuleId]"/>

<script type="text/javascript">
    $(document).ready(function () {
        Swal.fire({
            text: "Loading...",
            allowOutsideClick: false,
            onOpen: () => {
                Swal.showLoading();
                setTimeout(function () {
                    // timeout here just to make it look like its doing something important :D
                    getHighImpactResources("${design.id}", "${indicator.indicatorId}", "${calculationRuleId}",
                        "${parent.id}", "${userService.getAccount(user)?.id}", true);
                    Swal.close();

                    $("tr:first-child th:visible").each(function () {
                        if ($(this).hasClass("number")) {
                            $(this).replaceWith('<td class="th fontBody number">' + $(this).html() + '</td>');
                        } else {
                            $(this).replaceWith('<td class="th fontBody">' + $(this).html() + '</td>');
                        }
                    });
                    $('tr:visible:even').addClass("rowBackground");
                }, 1000);
            }
        })
    });

    function ExportPdf(){
        kendo.drawing
            .drawDOM("#printing",
                {
                    paperSize: "A4",
                    margin: { top: "0cm", bottom: "0cm" },
                    scale: 0.8,
                    height: 860,
                    forcePageBreak: ".break-page",
                })
            .then(function(group){
                kendo.drawing.pdf.saveAs(group, "Exported.pdf")
            });
    }
</script>
</body>
</html>



