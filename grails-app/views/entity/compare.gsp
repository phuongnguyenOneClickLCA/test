<%-- Copyright (c) 2012 by Bionova Oy --%>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>

</head>
<body>
    <g:set var="currentUser" value="${userService.getCurrentUser()}"/>
    <div class="container">

        <div class="screenheader">
            <div class="pull-right hide-on-print">
                <opt:link controller="entity" action="show" class="btn"><i class="icon-chevron-left"></i> <g:message code="back" /></opt:link>
                <button class="btn btn-primary" onClick="window.print();"><i class="icon-print icon-white"></i> <g:message code="print" /></button>
            </div>
            <h4>
                <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> >
                <opt:link controller="entity" action="show"> ${entity?.name} </opt:link> > <g:message code="${'operating' == childType ? 'compare.operating' : 'compare.design' }"/> <br/></h4>
            <h2 class="h2heading"><i class="fa fa-home fa-2x"></i> ${entity?.name} - <g:message code="${'operating' == childType ? 'compare.operating' : 'compare.design' }"/></h2>
        </div>
        <g:if test="${nonProdLicenses}">
            <div class="alert">
                <strong>
                    <g:message code="license.non_commercial" />
                    <g:set var="licenseAmount" value="${nonProdLicenses.size()}" />
                    <g:each in="${nonProdLicenses}" var="license" status="index">
                        <g:if test="${index < (licenseAmount - 1)}">
                            ${license.name}, <g:message code="license.type.${license.type}" />, ${currentUser?.name},
                        </g:if>
                        <g:else>
                            ${license.name}, <g:message code="license.type.${license.type}" />, ${currentUser?.name}
                        </g:else>
                    </g:each>
                    ${date}
                </strong>
            </div>
        </g:if>
    </div>

    <g:each in="${indicatorByIsAdmin?.get(false)}" var="indicator">
        <g:render template="/entity/resultstemplate" model="[indicator: indicator, compareView: true]" />
    </g:each>

    <g:if test="${isAdmin}">
        <g:each in="${indicatorByIsAdmin?.get(true)}" var="indicator">
            <g:render template="/entity/resultstemplate" model="[indicator: indicator, compareView: true]" />
        </g:each>
    </g:if>


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

</body>
</html>