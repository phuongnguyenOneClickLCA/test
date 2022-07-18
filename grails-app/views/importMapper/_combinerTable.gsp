<div class="container section">
    <g:if test="${grouperGroupsByClass && groupByForCollapse}">
        <div class="sectionbody">
            <table class="table-striped table table-condensed" style="margin-top: 10px; !important; margin-bottom: 10px; !important;">
                <g:each in="${grouperGroupsByClass}" var="groupersByClass" status="i">
                    <g:set var="classForGroup" value="${groupersByClass.getKey()}"/>
                    <g:set var="randomClass" value="${UUID.randomUUID().toString()}" />
                    <g:each in="${groupByForCollapse}" var="collapseHeading" status="index">
                        <g:if test="${i== 0}">
                            <g:if test="${index == 0}">
                                <tr><th>Count</th>
                            </g:if>
                            <g:if test="${!skipHeadingsForKey?.get(classForGroup)?.contains(collapseHeading)}">
                                <th>${collapseHeading}</th>
                            </g:if>
                            <g:else>
                                <th></th>
                            </g:else>
                            <g:if test="${index == groupByForCollapse.size() - 1}">
                                <th></th>
                                <th></th>
                                </tr>
                            </g:if>
                        </g:if>

                    </g:each>
                    <g:set var="valueForGroup" value="${groupersByClass.getValue()}"/>
                    <g:each in="${valueForGroup}" var="collapseGrouper">
                        <tr>
                            <td class="first">
                                ${collapseGrouper.datasetAmount}
                            </td>
                            <g:each in="${groupByForCollapse}" var="collapseHeading">
                                <g:if test="${!skipHeadingsForKey?.get(classForGroup)?.contains(collapseHeading)}">
                                    <td>
                                        ${collapseGrouper.collapseHeadingAndValue.get(collapseHeading)}
                                    </td>
                                </g:if>
                                <g:else>
                                    <td></td>
                                </g:else>
                            </g:each>
                            <td>
                                <g:set var="infoId" value="${collapseGrouper.grouperId}info" />
                                <a href="javascript:;" class="infoBubble" rel="collapser_data" id="${infoId}">
                                    <i class="far fa-file-alt fa-lg greenInfoBubble" aria-hidden="true" onclick="openCollapserData('${infoId}', '${collapseGrouper.datasetIds}')"></i></a>
                            </td>
                            <td class="last">
                                <input type="checkbox" name="grouperId" class="hidden ${randomClass} grouperCheckbox" checked="checked" value="${collapseGrouper.grouperId}" />
                                <a class="btn btn-danger" onclick="updateDatapointsSpan(this, '${collapseGrouper.datasetAmount}')" class="${randomClass} grouperCheckbox">${message(code:"importMapper.ungroup")}</a>
                            </td>
                        </tr>
                    </g:each>
                </g:each>
            </table>
        </div>
    </g:if>
</div>
<script type="text/javascript">
    $(function () {
        updateDatapointsSpan(null, ${datapointsAfterCombine ?: null})
    })
</script>