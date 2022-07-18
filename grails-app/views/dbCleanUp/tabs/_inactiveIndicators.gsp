<g:form action="removeInactiveIndicators">
    <table class="table table-striped table-condensed" style="display: none;" id="inactiveIndicatorsTable">
        <tbody>
        <tr><td colspan="2">&nbsp;</td><td><g:submitButton name="delete" value="Delete selected" class="btn btn-danger"/></td><td><input type="checkbox" id="selectAllIndicators" /> <strong>Select all</strong></td></tr>
        <g:each in="${inactiveIndicators}" var="indicator">
            <tr>
                <td>${indicator.indicatorId}</td><td>${indicator.localizedName}</td><td>${indicator.id}</td><td><g:checkBox name="id" value="${indicator._id.toString()}" checked="${false}" class="indicatorId" /></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:form>

<script type="text/javascript">
    $(document).ready(function() {

        $("#selectAllIndicators").on('change', function () {
            $(".indicatorId").prop('checked', $(this).prop("checked"));
        });
    });
</script>