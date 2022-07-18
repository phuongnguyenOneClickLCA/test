<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>

<div class="container">
    <div class="screenheader">
        <h1> Configurations for help </h1>
    </div>
</div>

<div class="container section">

    <div class="wrapperForNewConstructions container">
        <div class="btn-group">
            <a href="javascript:" class="btn btn-primary" id="importConfigurations"><g:message code="import"/> <i id="toggler" class="icon icon-white icon-plus"></i></a>
        </div>
        <br>
        <g:uploadForm action="importConfigurations" name="importingForm" class="hidden">

            <div class="clearfix"></div>
            <div class="column_left">
                <div class="control-group">
                    <label for="file" class="control-label"><g:message code="admin.import.excel_file" /></label>
                    <div class="controls"><input type="file" name="xlsFile" id="file" class="btn" value="" /></div>
                </div>

            </div>
            <div class="clearfix"></div>
            <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
        </g:uploadForm>
    </div>

    <div class="wrapperForExistingConstructions container">
        <table class="table table-striped" id="configTable">
            <thead><tr><g:if test="${configurationTableHeaders}"><th class="no-sort text-left"><div class="btn-group">
                <a href="javascript:"  class="btn btn-info" id="selectAll">check all</a><a href="javascript:" id="deleteSelected" class="btn btn-danger">Delete</a></div></th><g:each in="${configurationTableHeaders}" var="config"><th>${config}</th></g:each></g:if></tr></thead>
            <tbody>
            <g:if test="${allConfigurations}">
                <g:set var="helpConfigurationService" bean="helpConfigurationService"/>
                <g:each in="${allConfigurations}" var="configuration">
                    <tr id="${configuration.id}configuration">
                        <td class="text-center"><g:checkBox name="delete" data-checkbox="true" data-configurationId="${configuration?.id}"/></td>
                        <td>${configuration.targetPage}</td>
                        <td>${configuration.contentType}</td>

                        <td>${helpConfigurationService.getLocalizedContentName(configuration)}</td>
                        <td>${configuration.contentURL}</td>
                        <td>${helpConfigurationService.getLocalizedAdditionalText(configuration)}</td>
                        <td>${configuration.queryId}</td>
                        <td>${configuration.indicatorId}</td>
                    </tr>
                </g:each>
            </g:if>
            </tbody>
        </table>
    </div>
</div>


<script type="text/javascript">
    $('#importConfigurations').on('click', function () {
        var form = $('#importingForm');
        $('#toggler').toggleClass('icon-plus icon-minus');

        if ($(form).is(":visible")) {
            $(form).slideUp().addClass('hidden');
        }else {
            $(form).slideDown().removeClass('hidden');

        }
    });
    $(function () {
        sortableTable('#configTable');
    });

    $('#selectAll').on('click', function () {
        $('[data-checkbox]').each(function () {
            if (!$(this).prop('checked')) {
                $(this).prop('checked', true);
            }
        });
    });

    $('#deleteSelected').on('click', function () {
        var amountSelected = 0;
        $('[data-checkbox]').each(function () {
            if ($(this).prop('checked')) {
                amountSelected++;
            }
        });
        var deleteWarning = '${message(code: 'construction_deleting_selected')} ' + amountSelected + ' configurations ';
        var success = 'Successfully removed configurations: (' + amountSelected + ')';
        deleteConfigurations('${message(code: 'warning')}', deleteWarning ,success,'${message(code: 'yes')}', '${message(code: 'back')}')
    });
</script>


</body>
</html>