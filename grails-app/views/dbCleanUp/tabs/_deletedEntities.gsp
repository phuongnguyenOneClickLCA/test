<g:form action="removeDeletedEntities">
    <table class="table table-striped table-condensed" style="display: none;" id="deletedEntitiesTable">
        <tbody>
        <tr><td colspan="2">&nbsp;</td><td><g:submitButton name="delete" value="Delete selected" class="btn btn-danger"/></td><td><input type="checkbox" id="selectAllEntities" /> <strong>Select all</strong></td></tr>
        <g:each in="${deletedEntities}" var="entity">
            <tr>
                <td>${entity.name}</td><td>${entity.entityClass}</td><td>${entity.id}</td><td><g:checkBox name="id" value="${entity._id.toString()}" checked="${false}" class="entityId" /></td>
            </tr>
        </g:each>
        </tbody>
    </table>
</g:form>

<script type="text/javascript">
    $(document).ready(function() {

        $("#selectAllEntities").on('change', function () {
            $(".entityId").prop('checked', $(this).prop("checked"));
        });
    });
</script>