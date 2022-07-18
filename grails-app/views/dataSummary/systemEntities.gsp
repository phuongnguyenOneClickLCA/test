<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Test dataset entities</h1>
    </div>
</div>
<div class="container section">
    Show by indicatorId:
    <select name="indicatorId" id="indicatorId" style="width: 150px;">
        <option></option>
        <g:each in="${indicatorIds}">
            <option value="${it}">${it}</option>
        </g:each>
    </select>

    <table class="resource" id="table1">
        <thead>
        <tr>
            <th>Project</th>
            <th>Class</th>
            <th>Design / Period</th>
            <th>Indicators</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${entities}" var="entity">
                <g:set var="parentEntity" value="${entity.parentById}" />
                <g:set var="indicatorIds" value="${parentEntity?.indicators?.findAll({!entity.disabledIndicators?.contains(it.indicatorId)}).collect({ it.indicatorId })?.unique()}" />
                <tr class="testDatasetEntityRow" data-indicatorIds="${indicatorIds}">
                    <td><opt:link controller="entity" action="show" target="_blank" params="[entityId:entity.parentEntityId]">${entity.parentName}</opt:link></td>
                    <td>${entity.entityClass}</td>
                    <td>${entity.operatingPeriodAndName}</td>
                    <td>${indicatorIds}</td>
                </tr>
            </g:each>
        </tbody>

    </table>
</div>
<script type="text/javascript">

    $("#indicatorId").on('change', function () {
        const value = $(this).val();

        if (value) {
            $(".testDatasetEntityRow").each(function(i, row) {
                var indicatorIds = $(row).attr("data-indicatorIds");

                if (indicatorIds.indexOf(value) > -1) {
                    $(row).show()
                } else {
                    $(row).hide()
                }
            });
        } else {
            $(".testDatasetEntityRow").show()
        }
        console.log(value)
    })

</script>
</body>
</html>