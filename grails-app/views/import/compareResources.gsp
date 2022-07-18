<!doctype html>
<%@ page import="com.bionova.optimi.core.service.ResourceTypeService" %>
<%
    ResourceTypeService resourceTypeService = grailsApplication.mainContext.getBean("resourceTypeService")
%>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<div class="container">
    <div class="screenheader">
        <h1>Compare resources</h1>
    </div>
</div>
<div class="container section">
<sec:ifAllGranted roles="ROLE_DATA_MGR">
    <g:uploadForm action="uploadResourceCompareFilter">
        <div class="clearfix"></div>
        <div class="column_left">
            <div class="control-group">
                <label for="file" class="control-label">Comparison filter Excel-file</label>
                <div class="controls"><input type="file" name="file" id="file" class="btn" value="" /></div>
            </div>
        </div>
        <div class="clearfix"></div>
        <opt:submit name="import" value="${message(code:'import')}" class="btn btn-primary"/>
        <div style="margin-top: 15px;"><strong>Current filter:</strong> ${currentFilterName ? "${link(action: "downloadResourceCompareFilter") {currentFilterName}} ${link("action": "deleteResourceCompareFilter", "style": "color: red;", onclick: "return modalConfirm(this);", "data-questionstr": "Are you absolutely sure?", "data-truestr": "Im sure!", "data-falsestr": "Im not so sure..", "data-titlestr": "Are you sure?") {"Delete"}}" : "None"}</div>
    </g:uploadForm>

    <div style="float: left; margin-bottom: 600px;">
        <div style="float: left;">
            <label for="baseCategoryId"><strong>Base category:</strong></label>
            <select name="baseCategoryId" id="baseCategoryId" style="width: 150px;">
                <option></option>
                <g:each in="${resultCategories}">
                    <option value="${it.resultCategoryId}">${it.resultCategoryId}</option>
                </g:each>
            </select>
        </div>
        <div style="float: left; text-align: center; margin-left: 3px;">
            <label for="fixedBasedCategory"><strong>Fix B. Cat:</strong></label>
            <input class="numeric" type="text" id="fixedBasedCategory" style="width:50px"/>
        </div>
        <div style="float: left; margin-top: 10px; margin-left: 15px; margin-right: 15px;"><i class="fa fa-angle-double-left fa-4x" aria-hidden="true"></i></div>
        <div style="float: left;">
            <label for="comparisonCategoryId"><strong>Comparison category:</strong></label>
            <select name="comparisonCategoryId" id="comparisonCategoryId" style="width: 150px;">
                <option></option>
                <g:each in="${resultCategories}">
                    <option value="${it.resultCategoryId}">${it.resultCategoryId}</option>
                </g:each>
            </select>
        </div>
        <div style="float: left; text-align: center; margin-left: 3px;">
            <label for="fixedComparisonCategory"><strong>Fix C. Cat:</strong></label>
            <input class="numeric" type="text" id="fixedComparisonCategory" style="width:50px" />
        </div>
        <div style="float: left; margin-left: 20px;">
            <label for="calculationRuleId"><strong>Calculation rule:</strong></label>
            <select name="calculationRuleId" id="calculationRuleId" style="width: 150px;">
                <option></option>
                <g:each in="${calculationRules}">
                    <option value="${it.calculationRuleId}"${it.calculationRuleId == "GWP" ? ' selected' : ''}>${it.calculationRuleId}</option>
                </g:each>
            </select>
        </div>
        <div style="float: left; margin-left: 20px;">
            <label for="resourceTypeId"><strong>Resource type:</strong></label>
            <select name="resourceType" id="resourceTypeId" style="width: 150px;">
                <option></option>
                <g:each in="${resourceTypes}">
                    <option value="${it.resourceType}" ${it.subType ? "data-subtype=\"${it.subType}\"" : ""}>${resourceTypeService.getLocalizedName(it)}</option>
                </g:each>
            </select>
        </div>
        <div style="float: left; margin-left: 20px;">
            <label for="dataProperty"><strong>Data Properties:</strong></label>
            <select name="dataProperty" id="dataProperty" style="width: 150px;">
                <option></option>
                <g:each in="${dataProperties}">
                    <option value="${it}">${it}</option>
                </g:each>
            </select>
        </div>
        <div style="float: left; margin-top: 23px; margin-left: 15px; margin-right: 15px;">
            <a href="javascript:" id="compareResources" class="btn btn-info"> Compare categories </a>
        </div>

        <div style="margin-top: 25px;">
            <table class="resource" id="resulttable" style="float: left;">
                <tbody id="resourceList">
                </tbody>
            </table>
        </div>

        <opt:spinner/>
    </div>


    <div class="clearfix"></div>
</sec:ifAllGranted>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $('#baseCategoryId').select2().maximizeSelect2Height();
        $('#comparisonCategoryId').select2().maximizeSelect2Height();
        $('#calculationRuleId').select2().maximizeSelect2Height();
        $('#resourceTypeId').select2().maximizeSelect2Height();
        $('#dataProperty').select2().maximizeSelect2Height();


        $('#compareResources').on('click', function (event) {
            if (!$(this).attr('disabled')) {
                var baseCategoryId = $('#baseCategoryId').val();
                var comparisonCategoryId = $('#comparisonCategoryId').val();
                var calculationRuleId = $('#calculationRuleId').val();
                var useNewCalculation = $('#useNewCalculation').is(':checked');

                var resourceTypeOption = $('#resourceTypeId').find('option:selected');
                var resourceType, resourceSubType = "";

                if ($(resourceTypeOption).length) {
                    resourceType = $(resourceTypeOption).val();

                    if ($(resourceTypeOption).attr("data-subtype")) {
                        resourceSubType = $(resourceTypeOption).attr("data-subtype")
                    }
                }
                var dataProperty = $('#dataProperty').val();
                var fixedBasedCategory = $('#fixedBasedCategory').val();
                var fixedComparisonCategory = $('#fixedComparisonCategory').val();

                fixedBasedCategory = parseFloat(fixedBasedCategory)
                fixedComparisonCategory = parseFloat(fixedComparisonCategory);

                if(!isNaN(fixedBasedCategory) && !isNaN(fixedComparisonCategory)){
                    alert("You can have only one fixed value")
                    return
                }

                //var isFixedValue = !isNaN(fixedBasedCategory) || !isNaN(fixedComparisonCategory)

                if ((baseCategoryId || !isNaN(fixedBasedCategory)) && (comparisonCategoryId || !isNaN(fixedComparisonCategory)) && calculationRuleId) {
                    var queryString = 'baseCategoryId=' + baseCategoryId + '&comparisonCategoryId=' + comparisonCategoryId +
                        '&calculationRuleId=' + calculationRuleId + '&useNewCalculation=' + useNewCalculation +
                        '&resourceType=' + resourceType + '&dataProperty=' + dataProperty + '&fixedBasedCategory=' + fixedBasedCategory +
                        '&fixedComparisonCategory=' + fixedComparisonCategory + '&resourceSubType=' + resourceSubType;

                    document.getElementById('resourceList').innerHTML = '';
                    $.ajax({async: true, type:'POST',
                        data: queryString,
                        url:'/app/sec/admin/import/runCompareResources',
                        beforeSend: function() {
                            $("#loadingSpinner").show();
                            $("#baseCategoryId").attr("disabled", true);
                            $("#comparisonCategoryId").attr("disabled", true);
                            $("#calculationRuleId").attr("disabled", true);
                            $("#compareResources").attr("disabled", true);
                            $('#useNewCalculation').attr("disabled", true);
                            $('#resourceTypeId').attr("disabled", true);
                            $('#dataProperty').attr("disabled", true);
                            $('#fixedBasedCategory').attr("disabled", true);
                            $('#fixedComparisonCategory').attr("disabled", true);
                        },
                        success: function(data, textStatus) {
                            $("#loadingSpinner").hide();
                            $("#baseCategoryId").attr("disabled", false);
                            $("#comparisonCategoryId").attr("disabled", false);
                            $("#calculationRuleId").attr("disabled", false);
                            $("#compareResources").attr("disabled", false);
                            $('#useNewCalculation').attr("disabled", false);
                            $('#resourceTypeId').attr("disabled", false);
                            $('#dataProperty').attr("disabled", false);
                            $('#fixedBasedCategory').attr("disabled", false);
                            $('#fixedComparisonCategory').attr("disabled", false);

                            if (data.output) {
                                $('#resourceList').append(data.output);
                            }
                        },
                        error:function(XMLHttpRequest,textStatus,errorThrown){
                        }
                    });
                }
            }
        });

    });

/*    // Input Field Numeric Class Validation
    $('.numeric').on('input propertychange', numericInputValidation);


   // Input Field Numeric Class Validation
    function numericInputValidation(){

        var start = this.selectionStart;
        end = this.selectionEnd;
        var val = $(this).val();

        if (val) {
            $(this).popover('hide');
        }
        if (isNaN(val)) {
            val = val.replace(",",".")
            val = val.replace(/[^0-9\.]/g, '');

            if (val.split('.').length > 2) {
                val = val.replace(/\.+$/, '');
            }
        }

        $(this).val(val);
        this.setSelectionRange(start, end);
    }*/

  </script>
</body>
</html>
