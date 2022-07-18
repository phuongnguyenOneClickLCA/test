   <g:if test="${issues}">
    <div class="sectionbody" ${!bimModelCheckerStandAlone ? 'style="display:none"' : ''}>
        <div class="flexContainerBim container">
            <div class="container">
        <table class="query_resource bimtable sortMe">
            <thead><tr><g:each in="${headers}" var="header"><g:if test="${!"Explanation of issue".equalsIgnoreCase(header)}"><th><g:message code ='${header}'/></th></g:if></g:each></tr></thead>
            <tbody>
            <g:each in="${warnings?.sort({it?.priority})?.findAll({!it.dontShowInVisuals})}" var="bimWarning" status="index">
                <tr class="${index % 2 == 0 ? 'grayRow' : 'whiteRow'}"><td class="text-center paddedTd">
                    <g:if test="${bimWarning.status?.equalsIgnoreCase("WARN")}"><asset:image src="img/icon-warning.png" style="max-width:16px"/>
                    </g:if>
                    <g:else>
                    <i class="${bimWarning.status?.equalsIgnoreCase("ERROR") ? 'icon-alert' : bimWarning.status?.equalsIgnoreCase("INFO") ? 'fa fa-check oneClickColorScheme' : bimWarning.status?.equalsIgnoreCase("UNIDENTIFIED") ? ' far fa-question-circle biggerIcon' : ''}"></i>
                     </g:else></td>
                    <td class="paddedTd">${bimWarning.issueClass?.toLowerCase()?.capitalize()} <i class="fa fa-question greenInfoBubble bimHelp" data-content="${bimWarning.helpText}"></i></td>
                    <td class="paddedTd text-right">${bimWarning.amount} </td>
                    <td class="paddedTd text-right">${bimWarning.share} % </td>
                    <td class="paddedTd">${bimWarning.recommendedAction}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="buttons" style="margin-top:20px; margin-bottom:10px;"><g:link controller="importMapper" action="downloadExcelFile" id="downloadButton" class="btn btn-primary bimIssueButton"><g:message code="download_list_issues" /></g:link></div>
       </div>
    </div>
</div>

    <script type="text/javascript">

        $(function () {
            $('#bimHeading').empty().append('${issues} ${message(code: "bimchecker_issues_found")} ${amountToCountAgainst} ${message(code: "importMapper.combiner.info2")})').fadeIn();
            var helpLink = '<span style="margin-left:10px">${message(code: "bimchecker_issues_found.more_info")}  <a href="https://oneclicklca.zendesk.com/hc/en-us/articles/360015040659" target="_blank" >${message(code:"account.here")}</a> </span>';
            $(helpLink).insertAfter('#bimcheckerExpander');


            $('.bimHelp').each(function (){
                $(this).popover({
                    placement: 'top',
                    template: '<div class="popover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                    trigger: 'hover',
                    html: true
                });
            });

            $('.bimIssueButton').popover({
                placement: 'top',
                template: '<div class="popover"><div class="arrow"></div><div class="popover-content dataSourcesToolTip"></div></div>',
                trigger: 'hover',
                html: true,
                content:'The Excel contains two tabs: issues tab to list the rows with reconized issues and DATA that contains all the model data. If you wish to correct the issues directly in Excel the DATA tab can be used to upload corrected data.'
            });
        });

    </script>
   </g:if><g:else>
       <script type="text/javascript">

           $(function () {
               $('#bimHeading').empty().append("${message(code: 'bimchecker_no_issues')}").fadeIn();
               $('#bimModelCheckerContainer').addClass('removeClicks')
           });

       </script>
   </g:else>

