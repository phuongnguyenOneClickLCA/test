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
        <h1>List of Entities with data saved in incompatible units</h1>
    </div>
</div>

<div class="container section">
    <div class="sectionbody">
    <sec:ifAnyGranted roles="ROLE_SUPER_USER">
        <div class="sectionbody">
            <a class="btn btn-primary" href="javascript:" onclick="renderCorruptedDataTables()">Get corrupted data</a>
        </div>
        <ul class="nav nav-tabs">
            <li class="navInfo active" name="datasetTable" onclick="showHideChildDiv('corruptedDataMainDiv', this);"><a href="#datasetTable"> Dataset</a></li>
            <li class="navInfo" name="thicknessTable" onclick="showHideChildDiv('corruptedDataMainDiv', this);"><a href="#thicknessTable"> Thickness</a></li>
        </ul>
        <div id="corruptedDataMainDiv">
            <g:render template="/dbCleanUp/corruptedDataTables" model="[projectsEntitiesWithIncompatibleUnits:projectsEntitiesWithIncompatibleUnits]"/>
        </div>
        <div class="loading-spinner span8" style="display:none; margin: 0 auto; width: 100%;" id="loadingSpinner">
            <div class="image">
                <svg class="loadingSVG" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"  x="0px" y="0px"
                     width="270px" height="270px" viewBox="0 0 32 32" style="enable-background:new 0 0 32 32;" xml:space="preserve">
                    <g>
                        <path class="arrowSpinningSvg mm-o-syncing-queue" d="M24.5,10.4L22.2,11l5,5L29,9.2l-2.4,0.7C24.4,6.1,20.4,3.8,16,3.8c-5.4,0-10.1,3.4-11.7,8.6
	                                c-0.2,0.5,0.1,1.1,0.7,1.3c0.1,0,0.2,0,0.3,0c0.4,0,0.8-0.3,1-0.7c1.3-4.3,5.2-7.2,9.7-7.2C19.5,5.8,22.7,7.5,24.5,10.4z"/>
                    </g>
                </svg>
                <p class="working"><g:message code="loading.working"/>.</p>
            </div>
        </div>
    </sec:ifAnyGranted>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        showActiveTab();
    });

    function renderCorruptedDataTables() {

        Swal.fire({
            title: "Find corrupted data?",
            text: "Finding corrupted data is a heavy task, it will check all projects, designs, datasets and resources for corrupted data and could take up to 15 minutes to complete" ,
            icon: "warning",
            confirmButtonText: "${message(code: 'yes')}",
            cancelButtonText: "${message(code: 'back')}",
            confirmButtonColor: "red",
            showCancelButton: true,
            reverseButtons: true,
            allowOutsideClick: false
        }).then((result) => {
            if (result.value) {
                var url = '/app/sec/admin/dbCleanUp/getCorruptedData'
                var loadingSpinner = $("#loadingSpinner")
                var dataTable = $("#corruptedDataMainDiv");

                $(loadingSpinner).show()
                $(dataTable).empty().hide()
                $.ajax({
                    type: "POST",
                    url: url,
                    success: function(data){
                        $(loadingSpinner).hide()
                        if (data) {
                            $(dataTable).append(data.htmlContent).show()
                            showActiveTab();
                        }
                    },
                    error: function(error){
                        console.log(error)
                    }
                })
            }
            if (result.dismiss === swal.DismissReason.cancel) {
                console.log("Cancelled corrupted data search")
            }
        });

    }
</script>
</body>
</html>