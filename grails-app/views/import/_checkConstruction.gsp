<div class="tenMarginVertical">
    <label for="constructionUUID">Check Construction:</label>

    <div class="flex">
        <div class="flex">
            <input id="constructionUUID" type="text" placeholder="Enter construction database Id (UUID)"/>

            <div>
                <a href="javascript:" class="btn btn-primary" onclick="openShowConstruction(true)">
                    Show
                </a>
            </div>
        </div>

        <div class="flex fiveMarginHorizontal">
            <input id="constructionId" type="text" placeholder="Enter constructionId"/>

            <div>
                <a href="javascript:" class="btn btn-primary" onclick="openShowConstruction(false)">
                    Show
                </a>
            </div>
        </div>
    </div>

</div>
<script>
    function openShowConstruction(getFromUuid) {
        if (getFromUuid) {
            const uuid = $('#constructionUUID').val()
            if (uuid) {
                window.open('${createLink(controller: "import", action: "showConstruction", params: [constructionUUID: ''])}' + uuid, '_blank', 'width=1024, height=768, scrollbars=1')
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Please input a construction UUID'
                })
            }
        } else {
            const id = $('#constructionId').val()
            if (id) {
                window.open('${createLink(controller: "import", action: "showConstruction", params: [constructionId: ''])}' + id, '_blank', 'width=1024, height=768, scrollbars=1')
            } else {
                Swal.fire({
                    icon: 'error',
                    text: 'Please input a constructionId'
                })
            }
        }
    }
</script>