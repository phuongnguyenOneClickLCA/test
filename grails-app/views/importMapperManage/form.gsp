<!doctype html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>
            Manage ImportMappers
        </h1>
    </div>
</div>
<div class="container section" style="border-bottom: 1px solid #E2E2E2; margin-bottom: 20px;">
    <div class="sectionbody">
        <div class="row-fluid row-bordered">
            <div class="span4">
                <label for="applicationId" class="control-label">Choose application:</label>
                <select name="applicationId" id="applicationId" style="width: 150px;">
                    <option></option>
                    <g:each in="${applicationIds}">
                        <option value="${it}" ${it.equals(appId) ? 'selected' : ''}>${it}</option>
                    </g:each>
                </select>
            </div>
            <div id="importMapperFeatures"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        var appid = '${appId}';
        if (appid) {
            $.ajax({type: 'POST',
                data: 'applicationId=' + appid,
                url: '/app/sec/admin/importMapperManage/importMapperFeatures',
                success: function (data) {
                    if (data.output) {
                        $('#importMapperFeatures').append(data.output);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {

                }
            });
        }

        var appSelect = document.getElementById("applicationId");

        $(appSelect).select2({
            maximumSelectionLength: 4,
            minimumResultsForSearch: Infinity,
            placeholder: 'Select Application'
        }).maximizeSelect2Height();

        $(appSelect).on("select2:select", function(elem) {
            var applicationId = $('#applicationId').val();
            if (document.getElementById("addFeaturesDiv")) {
                document.getElementById("addFeaturesDiv").remove();
            }

            if (applicationId) {
                $.ajax({type: 'POST',
                    data: 'applicationId=' + applicationId,
                    url: '/app/sec/admin/importMapperManage/importMapperFeatures',
                    success: function (data) {
                        if (data.output) {
                            $('#importMapperFeatures').append(data.output);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {

                    }
                });
            }
        });
    });
</script>

</body>
</html>