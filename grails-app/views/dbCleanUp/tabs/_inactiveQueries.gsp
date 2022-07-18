<g:form action="removeInactiveQueries">
    <table class="table table-striped table-condensed" style="display: none;" id="inactiveQueriesTable">
        <tbody>
        <tr><td colspan="2">&nbsp;</td><td><g:submitButton name="delete" value="Delete selected" class="btn btn-danger"/></td><td><input type="checkbox" id="selectAllQueries" /> <strong>Select all</strong></td></tr>
        <g:each in="${inactiveQueries}" var="query">
            <tr>
                <td>${query.queryId}</td><td>${query.localizedName}</td><td>${query.id}</td><td><g:checkBox name="id" value="${query.id.toString()}" checked="${false}" class="queryId" /></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:form>

<script type="text/javascript">
    $(document).ready(function() {

        $("#selectAllQueries").on('change', function () {
            $(".queryId").prop('checked', $(this).prop("checked"));
        });
    });
</script>