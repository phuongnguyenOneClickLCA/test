<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery-migrate-3.1.0.min.js"/>
</head>

<body>
<script type="text/javascript">
    if(!!window.performance && window.performance.navigation.type === 2) {
        // This is to detect user pressing back button on browser and reloading && clearing session properly with reload
        window.stop();
        window.location.reload();
    }
</script>
<g:form action="handleConvertData" useToken="true" name="theForm" method="post" novalidate="novalidate">
<div class="container">
    <div class="screenheader">
        <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link>
            <sec:ifLoggedIn>
                <g:if test="${entity}">
                    > <opt:link controller="entity" action="show" id="${entity?.id}">
                    <g:abbr value="${entity?.name}" maxLength="20" />
                </opt:link>
                    <g:if test="${childEntity}">
                        > <span id="childEntityName">${childEntity?.operatingPeriodAndName}</span>
                    </g:if>
                </g:if>
            </sec:ifLoggedIn>
            > <g:message code="import_data" /> <br/> </h4>
            <g:if test="${showSteps}">
                <opt:breadcrumbs current="${currentStep}"/>
            </g:if>
            <div class="btn-group pull-right hide-on-print" id="actions">
                <g:link action="cancel" class="btn" onclick="return modalConfirm(this);"
                        data-questionstr="${message(code: 'importMapper.index.cancel')}"
                        data-truestr="${message(code: 'yes')}" data-falsestr="${message(code: 'no')}"
                        data-titlestr="Cancel"><g:message code="cancel" /></g:link>
                <g:link controller="importMapper" action="generateExcelFromDatasets" params="[returnUserTo: 'resolver', entityId: entityId, childEntityId: childEntityId]" class="btn btn-primary" style="font-weight: normal;"><g:message code="results.expand.download_excel" /></g:link>
                <button name="save" onclick="clearMessages('#loadingSpinner','#importPageContent');" type="submit"
                        class="btn btn-primary btn-block ${noLicense ? ' removeClicks' : ''}" id="save">${message(code: 'continue')}</button>
            </div>
            <h1>${message(code: 'breadcrumbs.converting')}</h1>
    </div>
</div>

<div class="container section">
    <div class="">
        <opt:spinner/>
        <div id="importPageContent">
    <g:set var="queryService" bean="queryService"/>
    <div>
        <table class="table table-condensed table-striped" id="tableConvertData">
            <thead>
            <tr>
                <th><g:message code="importMapper.CLASS_HEADER"/></th>
                <g:if test="${showCategoryCol}">
                    <th><g:message code="importMapper.CLASS_HEADER"/></th>
                </g:if>
                <th><g:message code="importMapper.NEW_CLASS"/></th>
                <th><g:message code="importMapper.TARGET_LOCATION"/></th>
            </tr>
            </thead>
            <g:each in="${(Map) classToRemap}" var="group">
                <g:set var="groupValue" value="${(List) group?.value}"/>
                <g:if test="${groupValue && !groupValue.isEmpty()}">
                    <g:set var="query" value="${queryService.getQueryByQueryId(groupValue[1], true)}"/>
                    <tr>
                        <td>${groupValue[0]}
                            <input class="hidden oldMappingValue" value="${groupValue[0]}"/>
                        </td>
                        <g:if test="${showCategoryCol}">
                            <td>${groupValue[3]}
                                <input class="hidden additionalCat" value="${groupValue[3]}"/>
                            </td>
                        </g:if>
                        <td>
                            <input class="hidden newMappingValue"/>
                            <g:set var="isResourceDataset" value="${groupValue[5]}" />
                            <g:set var="classesForSelect" value="${isResourceDataset ? classesAvailable : descriptiveClassesAvailable}" />
                            <select onchange="appendClassValueToInput(this)">
                                <g:each in="${classesForSelect}" var="choice">
                                    <option value="${choice}" ${choice.equalsIgnoreCase(groupValue[4]?.toString()) ? 'selected' : choice.equalsIgnoreCase(groupValue[0]) ? 'selected' : ''}>${choice.toUpperCase()}</option>
                                </g:each>
                                <g:if test="${!classesForSelect?.contains(groupValue[4]?.toString()) && !classesForSelect?.contains(groupValue[0])}">
                                    <option value="${groupValue[4]?.toString()}"
                                            selected><g:message code="importMapper.OTHER"/></option>
                                </g:if>
                            </select>
                        </td>
                        <td>${query?.localizedName} > ${query?.getSections()?.find({ it.sectionId.equals(groupValue[2]) })?.localizedName}</td>
                    </tr>
                </g:if>

            </g:each>
        </table>

        <script>
            $(function () {
                $(".unitSystemCheckbox").on('click', function () {
                    if (!$(this).is(":checked")) {
                        $(".unitSystemCheckbox").prop("checked", false);
                    } else {
                        $(".unitSystemCheckbox").prop("checked", false);
                        $(this).prop("checked", true);
                    }
                });
            })

        </script>
    </div>
</g:form>
</div>

</div>
</div>
<script type="text/javascript">
    var mapClassToQueryName
    $(function () {
        mapClassToQueryName = ${mapClassToQueryName as grails.converters.JSON}
        $(".autocompletebox").combobox();
        var mapToSend = []
        var hasAdditionalCat = false
        $("#theForm").on("submit",function () {
           var allRows = $("#tableConvertData").find("tr")
            $(allRows).each(function () {
                var newVal = $(this).find("td .newMappingValue").val()
                if(newVal){
                    var oldVal = $(this).find("td .oldMappingValue").val()
                    var additionalCat = $(this).find("td .additionalCat").val()
                    if(additionalCat){
                        mapToSend.push(oldVal+"."+newVal+"."+additionalCat)
                        hasAdditionalCat = true
                    } else {
                        mapToSend.push(oldVal+"."+newVal)
                    }
                }
            })
            if(mapToSend.length > 0){
                $("<input name='mapToChange' class='hidden'/>").val(mapToSend).appendTo("#theForm")
                $("<input name='hasAdditionalCat' class='hidden'/>").val(hasAdditionalCat).appendTo("#theForm")
            }
        })
    });

    function appendClassValueToInput(element){
        var newClass = $(element).find(":selected").val()
        $(element).siblings("input").val(newClass)
        mapClassToQueryName.forEach(function(item){
                if(item[0] == newClass){
                 $(element).closest("tr").find("td:last-child").text(item[1])
                }
            }
        )
    }
</script>
</body>
</html>



