    <g:set var="selectedClassKey" value="${allClassificationQsMap?.find({ it?.value?.questionId?.equalsIgnoreCase(groupingQuestion?.questionId) })?.key ?: ''}"/>
    <g:hiddenField name="currentClassifcation" value="${selectedClassKey}" id="currentClassifcation"/>

    <g:if test="${graphCalculationRules?.size() > 1}">
        <div class="btn-group pull-right hide-on-print">
            <a style="display: inline-block" class="btn btn-primary pull-right dropdown-toggle" id="sixPackRuleList"
               data-toggle="dropdown"><i id='resultRuleSpinner'
                                         class='fas fa-circle-notch fa-spin white-font hidden'></i> <g:message
                    code="result.pie_change_category"/> <span class="caret-middle"></span></a>
            <ul class="dropdown-menu pull-right">
                <g:each in="${graphCalculationRules}" var="calculationRule1">
                    <li> <a href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="renderResultGraphs('${entity?.id}', '${indicator?.indicatorId}','${calculationRule1?.calculationRuleId}', '${selectedClassKey}', '${defaultFallbackClassification}');">${calculationRule1?.localizedName}</a></li>
                </g:each>
            </ul>
        </div>
    </g:if>
    <g:if test="${allClassificationQsMap?.values()?.findAll({it})?.size() > 0}">
        <div class="btn-group pull-right hide-on-print" style="margin-right: 5px">
            <a style="display: inline-block" class="btn btn-primary pull-right dropdown-toggle" id="classificationOverviewSwapper" data-toggle="dropdown">
                <i class='fas fa-circle-notch fa-spin white-font hidden'></i> <g:message code="byClass"/>: <span id="groupingQuestionName">${groupingQuestion?.localizedQuestion ?: defaultFallbackClassification}</span> <span class="caret-middle"></span>
            </a>
            <ul class="dropdown-menu pull-right">
                <g:if test="${defaultFallbackClassification}">
                    <li class="${!groupingQuestion ? 'active':''}"><a href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="renderResultGraphs('${entity?.id}', '${indicator?.indicatorId}', '${calculationRule?.calculationRuleId ?: calculationRuleId}', '', '${defaultFallbackClassification}')">${defaultFallbackClassification}</a>
                    </li>
                </g:if>
                <g:each in="${indicator.classificationsMap}" var="classId">
                    <g:set var="classQ" value="${allClassificationQsMap?.get(classId)}"/>
                    <g:if test="${classQ}">
                        <li class="${classQ?.questionId?.equals(groupingQuestion?.questionId) ? 'active':''}"><a href="javascript:" style="font-size: 13px; font-weight: normal;" onclick="renderResultGraphs('${entity?.id}', '${indicator?.indicatorId}', '${calculationRule?.calculationRuleId ?: calculationRuleId}', '${classId}','${defaultFallbackClassification}')">${classQ?.localizedQuestion}</a>
                        </li>
                    </g:if>
                </g:each>
            </ul>
        </div>
    </g:if>
<script type="text/javascript">
    $(function (){
        $(".dropdown-toggle").dropdown()
    })
</script>