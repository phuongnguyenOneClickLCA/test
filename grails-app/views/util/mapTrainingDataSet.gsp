<%@ page import="org.apache.commons.lang.StringUtils" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
    <asset:javascript src="jquery.autocomplete.js?v=${grailsApplication.metadata.'app.version'}"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Map system training data</h1>
    </div>
</div>
<div class="container section">
    <div class="sectionbody">
        <table border="1" id="notIdentifiedTable">
            <thead>
            <tr class="notIdentified"><th class="mapTrainingDataTable">Resource to be mapped</th><th>Mapped to</th></tr>
            </thead>
            <tbody>
            <g:each in="${systemTrainingDataSet.systemMatches}" var="dataset">
                <tr id="notIdentified">
                    <g:set var="idOfDetails" value="${UUID.randomUUID().toString()}" />
                    <td class="mapTrainingDataTable">${dataset}</td>
                    <td>
                    <div class="input-append">
                        <input type="text" name="resourceId${dataset}" class="autocomplete" onmousedown="hoverDetails('${idOfDetails}');" onmouseout="unhoverDetails('${idOfDetails}');"
                               placeholder="${query?.localizedtypeaheadSearchText ? query.localizedtypeaheadSearchText : message(code: "typeahead.info")}" class="input-xlarge"><a tabindex="-1" title="" href="javascript:;" class="add-on showAllResources" onclick="showAllDataMappingResources(this); hoverDetails('${idOfDetails}');"><i class="icon-chevron-down"></i></a>
                        <input type="hidden" name="resourceId.${dataset}" id="resourceId${dataset}" />
                    </div>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        initSystemMappingAutocompletes()
    });
</script>


</body>
</html>

