<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="format-detection" content="telephone=no"/>
</head>
<body>
<g:set var="indicatorService" bean="indicatorService"/>
<g:if test="${calculationErrors}">
    <div class="container">
        <div class="alert alert-error">
            <button data-dismiss="alert" class="close" type="button">×</button>
            <strong>${calculationErrors}</strong>
        </div>
    </div>
</g:if>

<div class="container">
    <div class="screenheader">
        <h1>Mass recalculation</h1>
    </div>
</div>
<div class="container section">
    <div class="sectionbody">
        <g:if test="${indicators}">
            <h4>Choose indicator</h4>
            <g:form action="getEntitiesByIndicator">
                <select name="indicatorId" class="autocompletebox select2">
                    <g:each in="${indicators}" var="ind">
                        <option${ind.indicatorId.equals(indicator?.indicatorId) ? ' selected=\"selected\"' : ''} value="${ind.indicatorId}">${indicatorService.getLocalizedShortName(indicator)} (${ind.indicatorId})</option>
                    </g:each>
                </select>

                <h4 style="margin-top: 10px;">By license</h4>
                <g:select id="licenseId" class="autocompletebox select2License" name="licenseId" from="${licenses}"
                          optionKey="id" optionValue="${{ it.name }}" value="${licenseId}"
                          noSelection="['': '']"/>

                <div class="control-group">
                    <div class="controls">
                        <g:message code="admin.massRecalculation.no_locked" />
                        <input type="checkbox" name="noLocked"${noLocked ? ' checked=\"checked\" ' : ''}/>
                    </div>
                </div>

                <g:submitButton name="getEntitites" value="Get entities" class="btn btn-primary" />
            </g:form>
        </g:if>
    </div>
</div>

<div class="container">
    <div class="screenheader">
        <h1>Log dataset migration</h1>
    </div>
</div>
<div class="container section">
    <div class="sectionbody">
        <g:if test="${indicators}">
            <h4>Give queryId, sectionId and questionId</h4>
            <g:form action="logMigration" target="_blank">
                <div class="control-group">
                    <div class="controls">
                        QueryId
                        <input type="text" name="queryId" value="" />
                    </div>
                    <div class="controls">
                        SectionId
                        <input type="text" name="sectionId" value="" />
                    </div>
                    <div class="controls">
                        QuestionId
                        <input type="text" name="questionId" value="" />
                </div>
                </div>

                <g:submitButton name="getResults" value="Get dataset amount" class="btn btn-primary" />
            </g:form>
        </g:if>
    </div>
</div>

<g:if test="${indicator && entities}">
<div class="container section">
    <div class="sectionbody">
        <g:form action="relcalculate" name="recalculateForm">
            <input type="hidden" id="noLocked" value="${noLocked ? 'true' : ''}" name="noLocked">
            <input type="hidden" id="indicatorId" value="${indicator?.indicatorId}" name="indicatorId">
            <input type="hidden" id="licenseId" value="${licenseId}" name="licenseId">
            <div class="btn-group pull-left">
                <g:if test="${!automaticRecalculation}">
                    <a id="toggleAllCheckboxes" class="btn btn-primary" style="margin-bottom: 5px;" >Select first 50</a>
                    <g:if test="${relcalculationNeeded}">
                        <g:submitButton name="recalculateSelected" style="margin-bottom: 5px;" value="Recalculate selected" class="btn btn-primary" />
                    </g:if>
                    <g:if test="${showForceRecalculation}">
                        <g:submitButton name="forceRecalculateSelected" style="margin-bottom: 5px;" value="Force recalculation on selected (eg. after data migration calculates all entities)" class="btn btn-primary" />
                    </g:if>
                </g:if>
            </div>
            <div class="btn-group pull-right">
                <g:if test="${!automaticRecalculation}">
                    <g:if test="${relcalculationNeeded}">
                        <a id="automatedCalculation" class="btn btn-primary" style="margin-bottom: 5px; margin-left: 25px;" >Start automated calculation of listed entities</a>
                    </g:if>
                </g:if>
            </div>
            <table class="massRecalc">
                <tr>
                    <th></th>
                    <th>Entity</th>
                    <th>Design / Period</th>
                    <g:set var="calcRules" value="${indicator.getResolveCalculationRules(null)}" />
                    <g:each in="${calcRules}" var="calcRule">
                        <th>${calcRule.localizedName} ${calcRule.localizedUnit}</th>
                    </g:each>
                </tr>
                <g:each in="${entities}" var="entity">
                    <g:set var="children" value="${'design'.equals(indicator.indicatorUse) ? noLocked ? entity.getDesignsForResultShowing(false, true) : entity.getDesignsForResultShowing(true, true) : noLocked ? entity.getOperatingPeriodsForResultShowing(false, true) : entity.getOperatingPeriodsForResultShowing(true, true)}" />


                    <tr>
                        <th><input style="width: 30px; height: 20px;" type="checkbox" name="entitiesToCalculate" class="entitiesToCalculate" value="${entity.id.toString()}" /></th>
                        <th>${entity.name}</th>
                        <td>
                            <table class="massRecalc">
                                <g:if test="${'design'.equals(indicator.indicatorUse)}">
                                    <g:each in="${children}" var="child">
                                        <tr><td>${child.name}</td></tr>
                                    </g:each>
                                </g:if>
                                <g:else>
                                    <g:each in="${children}" var="child">
                                        <tr><td>${child.operatingPeriod ?: ""} ${child.name ?: ""}</td></tr>
                                    </g:each>
                                </g:else>
                            </table>
                        </td>

                         <g:each in="${calcRules}" var="calcRule">
                            <td>
                                <table class="massRecalc">
                                    <g:each in="${children}" var="child">
                                        <tr>
                                            <td>
                                                <g:formatNumber number="${child.calculationTotalResults?.find({ indicator?.indicatorId?.equals(it.indicatorId) })?.totalByCalculationRule?.get(calcRule.calculationRuleId)}" format="0.##E0" />
                                            </td>
                                        </tr>
                                    </g:each>
                                </table>
                            </td>
                         </g:each>
                    </tr>
                </g:each>
            </table>
        </g:form>
    </div>
</div>
</g:if>

<div class="container section">

</div>

<script type="text/javascript">

    $(function () {
        var allSelects = $('.select2');
        if (allSelects) {
            $(allSelects).select2({
                width: '400px'
            }).maximizeSelect2Height();
        }
    });

    $(function () {
        var allSelects = $('.select2License');
        if (allSelects) {
            $(allSelects).select2({
                width: '400px',
                allowClear: true,
                placeholder: "Any license"
            }).maximizeSelect2Height();
        }
    });

    $("#toggleAllCheckboxes").on("click", function() {
        var checkBoxes = $('.entitiesToCalculate');
        var i = 0;

        $(checkBoxes).each(function () {
            if (i < 50) {
                $(this).prop('checked', true)
                i++
            } else {
                $(this).prop('checked', false)
            }
        });
    });

    $(document).ready(function(){
        $("#selectAllEntities").on('change', function () {
            $(".entityId").prop('checked', $(this).prop("checked"));
        });

        $("#selectAllIndicators").on('change', function () {
            $(".indicatorId").prop('checked', $(this).prop("checked"));
        });

        $("#selectAllQueries").on('change', function () {
            $(".queryId").prop('checked', $(this).prop("checked"));
        });
    });

    $("#automatedCalculation").on("click", function() {
        var checkBoxes = $('.entitiesToCalculate');
        var i = 0;

        if (checkBoxes) {
            $(checkBoxes).each(function () {
                if (i < 10) {
                    $(this).prop('checked', true);
                    i++
                } else {
                    $(this).prop('checked', false)
                }
            });
            $('#recalculateForm').append('<input type="hidden" name="automaticRecalculation" value="true" />');
            $('#recalculateForm').trigger('submit');
            Swal.fire({
                showCancelButton: false,
                allowOutsideClick: false,
                title: 'Automatic recalculation progress: 0 / ${entities?.size()}'
            });
            swal.showLoading();
        }
    });

    $(document).ready(function() {
        <g:if test="${automaticRecalculation}">
            var checkBoxes = $('.entitiesToCalculate');
            var i = 0;

            if (checkBoxes.length) {
                $(checkBoxes).each(function () {
                    if (i < 10) {
                        $(this).prop('checked', true);
                        i++
                    } else {
                        $(this).prop('checked', false);
                    }
                });
                $('#recalculateForm').append('<input type="hidden" name="automaticRecalculation" value="true" />');
                $('#recalculateForm').trigger('submit');
                Swal.fire({
                    showCancelButton: false,
                    allowOutsideClick: false,
                    title: 'Automatic recalculation progress: ${entitiesCalulated?.size()} / ${entitiesCalulated?.size() + entities?.size()}'
                });
                swal.showLoading();
            } else {
                Swal.fire("Success","Progress: ${entitiesCalulated?.size()} / ${entitiesCalulated?.size()}","success");
            }
        </g:if>
    });
</script>
</body>
</html>