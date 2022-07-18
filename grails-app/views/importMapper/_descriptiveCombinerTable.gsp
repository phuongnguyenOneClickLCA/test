<div class="container section">
    <g:if test="${grouperGroupsByClass}">
        <div class="sectionbody">
            <table class="table-striped table table-condensed" style="margin-top: 10px; !important; margin-bottom: 10px; !important;">
                <g:each in="${grouperGroupsByClass}" var="groupersByClass" status="i">
                    <g:set var="classForGroup" value="${groupersByClass.getKey()}"/>
                    <g:set var="valueForGroup" value="${groupersByClass.getValue()}"/>
                    <g:set var="randomClass" value="${UUID.randomUUID().toString()}" />
                    <tr>
                        <th>Count</th>
                        <g:each var="heading" in="${valueForGroup?.find()?.collapseHeadingAndValue*.key}">
                            <th>${heading}</th>
                        </g:each>
                        <th></th>
                    </tr>
                    <g:each in="${valueForGroup}" var="collapseGrouper">
                        <tr>
                            <td class="first">
                                ${collapseGrouper.datasetAmount}
                            </td>
                            <g:each var="collapseHeadingAndValue" in="${collapseGrouper.collapseHeadingAndValue}">
                                <td>${collapseHeadingAndValue.value}</td>
                            </g:each>
                            <td>
                                <g:set var="infoId" value="${collapseGrouper.grouperId}info" />
                                <a href="javascript:;" class="infoBubble" rel="collapser_data" id="${infoId}">
                                    <i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true" onclick="openCollapserData('${infoId}', '${collapseGrouper.datasetIds}')"></i></a>
                            </td>
                        </tr>
                    </g:each>
                </g:each>
            </table>
        </div>
    </g:if>
</div>