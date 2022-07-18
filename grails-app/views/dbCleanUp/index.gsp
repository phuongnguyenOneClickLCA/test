<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1><g:message code="admin.dbCleanUp.title" /></h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <h4><g:link controller="dbCleanUp" action="deprecatedResults" elementId="deprecatedResults">Remove deprecated results manually (ran every sunday automcatically)</g:link></h4>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
        <ul class="nav nav-tabs">
            <li class="navInfo"><a href="#deletedEntities" class="load-on-shown" data-toggle="tab">Deleted entities ${countOfDeletedEntities}</a></li>
            <li class="navInfo"><a href="#inactiveIndicators" class="load-on-shown" data-toggle="tab">Inactive indicators ${countOfInactiveIndicators}</a></li>
            <li class="navInfo"><a href="#inactiveQueries" class="load-on-shown" data-toggle="tab">Inactive queries ${countOfInactiveQueries}</a></li>
            <li class="navInfo"><a href="#nonUsedInactiveResources" data-toggle="tab">Non used inactive resources
                <span id="countOfNonUsedInactiveResources">
                    <i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>
                </span>
            </a></li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane" id="deletedEntities">
                <g:if test="${countOfDeletedEntities}">
                    <opt:spinner spinnerId="loadingSpinner-deletedEntities"/>
                </g:if>
                <g:else>
                    <h4>Deleted entities 0</h4>
                </g:else>
            </div>
            <div class="tab-pane" id="inactiveIndicators">
                <g:if test="${countOfInactiveIndicators}">
                    <opt:spinner spinnerId="loadingSpinner-inactiveIndicators"/>
                </g:if>
                <g:else>
                    <h4>Inactive indicators 0</h4>
                </g:else>
            </div>
            <div class="tab-pane" id="inactiveQueries">
                <g:if test="${countOfInactiveQueries}">
                    <opt:spinner spinnerId="loadingSpinner-inactiveQueries"/>
                </g:if>
                <g:else>
                    <h4>Inactive queries 0</h4>
                </g:else>
            </div>
            <div class="tab-pane" id="nonUsedInactiveResources">
                <opt:spinner spinnerId="loadingSpinner-nonUsedInactiveResources"/>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){

        $('a[data-toggle="tab"].load-on-shown').on('shown', function (e) {
            let activatedTab = e.target;
            let tabSelector = $(activatedTab).attr("href");
            let tabElement = $(tabSelector);

            loadData(tabElement);
        })

        function loadData(element) {
            let dataKind = element.attr("id");
            let actionName = dataKind + "ForCleanUp";
            let spinnerElement = $("#loadingSpinner-" + dataKind)

            if (spinnerElement.is(":hidden")) {
                spinnerElement.show();

                $.ajax({
                    type: 'GET',
                    url: '${g.createLink(action: "index")}'.replace(/index$/, actionName),
                    success: function (data) {
                        element.html(data);
                        showHideDiv(dataKind + "Table");
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        element.html("${g.message(code: "error.general")}");
                        console.log("ajax error: " + XMLHttpRequest.status + " " + errorThrown);
                    },
                    complete: function () {
                        spinnerElement.hide();
                    }
                });
            }
        }

        $('a[data-toggle="tab"]:first').tab('show');
        loadData($("#nonUsedInactiveResources"));

        /*
        $("#selectAlldatasetImportFields").on('change', function () {
            $(".datasetImportFieldsId").prop('checked', $(this).prop("checked"));
        });
        */

        $("#deprecatedResults").on('click', function () {
            $("#deprecatedResults").addClass("removeClicks")
        })
    });
</script>
</body>
</html>