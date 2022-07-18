function hideChildEntity(parentId, designId) {
    if (designId) {
        const url = "/app/sec/util/hideDesign"
        const json = {parentId: parentId, designId: designId}

        fetch(url,
            {method: "POST", body: JSON.stringify(json)}
        ).then(
            function (r) {
                window.location.reload()
            }
        ).catch(
            function (e) {
                console.error(e)
            }
        )
    }
}

function showHiddenDesign(parentId) {
    if (parentId) {
        openOverlay("wordDocGenOverlay");
        appendLoader('wordDocGenOverlayContent');

        const url = "/app/sec/util/showHiddenDesign"
        const json = {parentId: parentId}

        fetch(url,
            {method: "POST", body: JSON.stringify(json)}
        ).then(
            function (r) {
                window.location.reload()
            }
        ).catch(
            function (e) {
                console.error(e)
            }
        )
    }
}

function showHiddenDesignByIds(designSelectWrapper) {
    let childEntityIds = []
    let childEntities = $(designSelectWrapper).find('.designItem:checkbox:checked');

    $.each(childEntities, function (childEntity) {
        childEntityIds.push($(this).val())
    })

    if (childEntityIds.length > 0) {
        const url = "/app/sec/util/showHiddenDesignByIds";
        const json = {childEntityIds: childEntityIds};

        fetch(url,
            {method: "POST", body: JSON.stringify(json)}
        ).then(
            function (r) {
                window.location.reload()
            }
        ).catch(
            function (e) {
                console.error(e)
            }
        );
    }
}

function restoreDesigns(designSelectWrapper, parentEntityId) {
    let childEntityIds = []
    let childEntities = $(designSelectWrapper).find('.designItem:checkbox:checked');

    $.each(childEntities, function (childEntity) {
        childEntityIds.push($(this).val())
    })

    if (childEntityIds.length > 0) {
        const url = "/app/sec/entity/restoreDesigns";
        const json = {childEntityIds: childEntityIds, parentEntityId: parentEntityId};

        fetch(url, {method: "POST", body: JSON.stringify(json)}
        ).then(
            function (r) {
                window.location.reload()
            }
        ).catch(
            function (e) {
                console.error(e)
            }
        );
    }
}