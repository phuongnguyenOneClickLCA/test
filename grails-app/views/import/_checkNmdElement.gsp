<div class="tenMarginVertical">
    <label for="resourceUuidInput">Check NMD Element:</label>

    <div class="flex">
        <div class="flex">
            <input id="elementUuid" type="text" placeholder="Enter element database Id (UUID)"/>

            <div>
                <a href="javascript:" class="btn btn-primary" onclick="openShowElement(true)">
                    Show
                </a>
            </div>
        </div>

        <div class="flex fiveMarginHorizontal">
            <input id="elementId" type="text" placeholder="Enter elementId"/>

            <div>
                <a href="javascript:" class="btn btn-primary" onclick="openShowElement(false)">
                    Show
                </a>
            </div>
        </div>
    </div>
</div>
<script>
    function openShowElement(useUuid) {
        if (useUuid) {
            const uuid = $('#elementUuid').val()
            if (uuid) {
                window.open('${createLink(controller: "import", action: "showNmdElement", params: [elementUUID: ''])}' + uuid, '_blank', 'width=1024, height=768, scrollbars=1')
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Please input element UUID'
                })
            }
        } else {
            const elementId = $('#elementId').val()
            if (elementId) {
                window.open('${createLink(controller: "import", action: "showNmdElement", params: [elementId: ''])}' + elementId, '_blank', 'width=1024, height=768, scrollbars=1')
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Please input elementId '
                })
            }

        }
    }
</script>