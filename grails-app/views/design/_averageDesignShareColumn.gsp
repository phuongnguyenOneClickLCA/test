<div class="span6 splitViewBox" id="splitOrChangeView">
    <div class="splitOrChangeView">

        <g:hiddenField name="entityId" value="${parentEntityId}"/>
        <g:hiddenField name="queryId" value="${query?.queryId}"/>
        <g:hiddenField name="indicatorId" value="${indicatorId}"/>
        <g:hiddenField name="sectionId" value="${section?.sectionId}"/>
        <g:hiddenField name="questionId" value="${question?.questionId}"/>

        <div id="appendSplitsHere">
            <div id="originalDataContainer">
                <br>
                <table id="originResource" class="query_resource">
                    <thead>
                    <tr>
                        <th><g:message code="design.name"/></th>
                        <th><g:message code="design.sharepercentage"/></th>
                    </tr>
                    <g:each in="${designsList}" var="des">

                        <tr>
                            <td>${des?.name}
                            </td>
                            <td><input type="text" id="input${des?.id}"
                                       value="0" class="input input-small numeric" data-share="true" style="width:25px;"
                                       name="${des?.id}" onclick="numericInputCheck();" onblur="designTotalUpdate();">
                            </td>
                        </tr>
                    </g:each>
                    </thead>
                </table>
            </div>
        </div>
        <div>
            <a href="javascript:" class="hidden hightLightOnHover" id="moreSplits"><i
                    class="fa fa-plus fa-2x oneClickColorScheme"></i></a>
        </div>
    </div>
</div>
