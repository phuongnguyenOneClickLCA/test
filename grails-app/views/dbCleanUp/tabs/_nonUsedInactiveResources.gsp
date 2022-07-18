<g:if test="${nonUsedInactiveResources}">
    <g:form action="removeInactiveResources">
        <table class="table table-striped table-condensed" style="display: none;" id="nonUsedInactiveResourcesTable">
            <tbody>
            <tr><td colspan="2">&nbsp;</td><td><g:submitButton name="delete" value="Delete selected" class="btn btn-danger"/></td><td><input type="checkbox" id="selectAllResources" /> <strong>Select all</strong></td></tr>
            <g:each in="${nonUsedInactiveResources.take(100)}" var="resource">
                <tr>
                    <td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.nameEN}</td><td><g:checkBox name="id" value="${resource._id.toString()}" checked="${false}" class="resourceId" /></td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </g:form>
</g:if>
<g:else>
    <h4>Non used inactive resources 0</h4>
</g:else>

<script type="text/javascript">

    $("#countOfNonUsedInactiveResources").html(${nonUsedInactiveResources?.size()});

    $(document).ready(function() {

        $("#selectAllResources").on('change', function () {
            $(".resourceId").prop('checked', $(this).prop("checked"));
        });
    });
</script>