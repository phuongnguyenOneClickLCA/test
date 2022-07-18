<g:if test="${verificationPoints}">
    <div class="margin-bottom-10">${message(code: 'select.vPoint')}</div>
    <select id="vPointJumpSelect" class="vPointJumpSelect" onchange="handleVPointChange(this)">
        <option></option>
        <g:each in="${verificationPoints}" var="vPoint">
            <g:if test="${vPoint?.locations}">
                <optgroup label="${vPoint?.pointName} - ${vPoint?.locations?.size()} ${message(code: 'places')}">
                    <g:each in="${vPoint?.locations}" var="location">
                        <option data-queryId="${location?.queryId}" data-url="${location?.url}"
                                value="${location?.id}">${location?.queryName} - ${location?.localizedName} - &lt;${location?.groupName}&gt;</option>
                    </g:each>
                </optgroup>
            </g:if>
        </g:each>
    </select>

    <div class="tenMarginVertical min-height-100">
        <div class="hide">
            <a id="jumpToVpointLink" class="vPointJumpLink" href=""></a>
            <i style="margin-left: 1px !important; font-size: 20px !important;"
               class="far fa-clipboard copyToClipBoard triggerPopover" data-content="${message(code: 'copy.url')}"
               onclick="copyHref('#jumpToVpointLink')"></i>
        </div>
    </div>

    <div>
        <button class="swal2-cancel swal2-styled" onclick="Swal.close()">${message(code: 'cancel')}</button>
        <button class="swal2-confirm swal2-styled oneClickColorSchemeBg"
                onclick="openVpointThisTab('${queryId}', '${message(code: 'vpoint.missing')}')">${message(code: 'open.this.tab')}</button>
        <button class="swal2-confirm swal2-styled oneClickColorSchemeBg"
                onclick="openVpointNewTab('${message(code: 'vpoint.missing')}')">${message(code: 'open.new.tab')}</button>
    </div>
</g:if>
