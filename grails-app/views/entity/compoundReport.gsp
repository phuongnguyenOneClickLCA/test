<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <g:if test="${appliedStylesheets && !appliedStylesheets.isEmpty()}">
        <g:each in="${appliedStylesheets}" var="appliedStylesheet">
            <link rel="stylesheet" type="text/css" href="${appliedStylesheet}"/> %{--static stylesheet url--}%
        </g:each>
    </g:if>
    <g:else>
        <asset:stylesheet src="building-passport.css"/>
    </g:else>
</head>

<body>
<div class="container">
    <div class="screenheader hide-on-print">

        <div class="pull-right">
            <opt:link controller="entity" action="show" class="btn hide-on-print"><i
                    class="icon-chevron-left"></i> <g:message code="back"/></opt:link>
            <button class="btn btn-primary" onClick="window.print();"><i class="icon-print icon-white"></i> <g:message
                    code="print"/></button>
        </div>
        <opt:breadcrumbsNavBar parentEntity="${entity}" specifiedHeading="${message(code: 'entity.property_passport')}" />
        <g:if test="${reportTitles}">
            <g:each in="${reportTitles}" var="title">
                <h1>${title}</h1>
            </g:each>
        </g:if>
        <g:else>
            <h2><g:message code="entity.property_passport"/></h2>
        </g:else>
    </div>
</div>

<g:each in="${indicators}" var="indicator">
    <g:render template="/entity/compoundReportTemplate"
              model="[indicator: indicator, entity: entity, designs: designs, operatingPeriods: operatingPeriods]"/>
</g:each>

<script type="text/javascript">
    $(document).ready(function () {
        $('.sectionexpander').on('click', function (event) {
            var section = $(this).parent().parent();
            $(section).find('.sectioncontrols').fadeToggle(200);
            $(section).find('.sectionbody').fadeToggle(200);
            $(section).toggleClass('collapsed');

        });
    });
</script>
</body>
</html>



