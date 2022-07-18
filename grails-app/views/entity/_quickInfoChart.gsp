<div id="quickInfoChartContainer"></div>
<script type="text/javascript">
    <g:if test="${drawTexts}">
    var accountId = '${account?.id}'
    var link = '${createLink(action: "form", params: [accountId:account?.id ], controller: "account")}'
    $('#quickInfoChartContainer').append("<h1>${impactsText}</h1>").hide().fadeIn(2500);
    $('#socialCostOfCarbonHelp').popover({
        placement: 'top',
        template: '<div class="popover" style="font-size:12px !important; max-width:300px;"><div class="arrow"></div><div class="popover-content"></div></div>',
        html: true,
        trigger: 'click',
        content:"${message(code: 'social_cost_help',args: [account?.carbonCost ?: '50', account?.unitCarbonCost ?: '€']) } <a href='${createLink(action: "form", params: [id:account?.id ], controller: "account")}'>${message (code:'account.here')}</a>"
    });
    $('#tonsCabonHelp').popover({
        placement: 'top',
        template: '<div class="popover" style="font-size:12px !important; max-width:300px;"><div class="arrow"></div><div class="popover-content"></div></div>',
        html: true,
        trigger: 'hover',
        content:"${message(code: 'tonnes_carbon_help')}"
    });
    $('#annualCarbonHelp').popover({
        placement: 'top',
        template: '<div class="popover" style="font-size:12px !important; max-width:300px;"><div class="arrow"></div><div class="popover-content"></div></div>',
        html: true,
        trigger: 'hover',
        content:"${message(code: 'annual_carbon_help')}"
    });
    </g:if>
</script>