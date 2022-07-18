<div class="modal modal medium" id="renameResourceModal">

    <div class="modal-header text-center"><button type="button" onclick="closeModal()" class="close" data-dismiss="modal">&times;</button>
        <h2 id="renameResourceModalHeading">${message(code: 'pdl.alias').capitalize()} ${message(code: 'query.material').toLowerCase()}</h2>
        <label><strong>${resourceName}</strong></label>
    </div>

    <div class="modal-body" id="renameResourceModalBody">
        <div id="renameResourceFormContainer">
            <table style="width:100%">
                <tr>
                    <td><label for="newName"><strong>${message(code: 'new').capitalize()} ${message(code: 'query.material').toLowerCase()} ${message(code: 'pdl.alias')}:</strong></label></td>
                    <td><input style="width: 80%;" id="newName" name="newName" type="text" value="" /></td>
                </tr>
            </table>
            <div style="text-align: center">
                <a href="javascript:" id="renameResourceName" class="btn btn-primary" onclick="renameResource('${resourceTableId}')">${message(code: 'add').capitalize()} ${message(code: 'pdl.alias')}</a>
                <a href="javascript:" id="resetResourceName" class="btn btn-danger" onclick="resetResourceName('${resourceTableId}','${sectionId}','${questionId}','${resourceId}','${g.abbr(value: resourceName, maxLength: 40)}')">${message(code: 'undeprecate').capitalize()} ${message(code: 'pdl.alias')}</a>
            </div>
        </div>
    </div>

<script type="text/javascript">
    function closeModal() {
        $('#changeResourceNameOverlay').fadeOut().css("display", "");
        document.body.classList.remove('noScroll');
        closeOverlay("myOverlay");
    }

    function renameResource(resourceTableId) {
        var newName = $('#newName').val();
        var row = $('#'+resourceTableId);

        if ($(row).length) {
            $(row).find('.resourceNameContainer').html(newName);

            var hiddenInput = $(row).find('.customDatasetName');

            if ($(hiddenInput).length) {
                $(hiddenInput).val(newName);
            }
        }
        closeModal()
    }

    function resetResourceName(resourceTableId, sectionId, questionId, resourceId, resourceName) {
        var oldName = resourceName;
        var row = $('#'+resourceTableId);

        console.log(oldName)
        if ($(row).length) {
            $(row).find('.resourceNameContainer').html(oldName);

            var hiddenInput = $(row).find('.customDatasetName');

            if ($(hiddenInput).length) {
                $(hiddenInput).val(null);
            }
        }
        closeModal()
    }
</script>
</div>


