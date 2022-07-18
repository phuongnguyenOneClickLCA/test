%{--The parent select must have class 'selectDesignToImport'--}%
<option></option>
<g:if test="${copyEntitiesMapped}">
    <g:each in="${copyEntitiesMapped}" var="designsAndType" status="i">
        <g:set var="type" value="${designsAndType.key}"/>
        <g:set var="designsByType" value="${designsAndType.value}"/>
        <g:if test="${indicatorCompatible}">
            <option disabled="disabled">${message(code: type)} - ${designsByType?.collect({ it.value })?.flatten()?.flatten()?.size()} ${message(code: 'results')?.toLowerCase()}</option>
            <g:each in="${(Map) designsByType}" var="designsAndClassification">
                <g:set var="classification" value="${designsAndClassification.key}"/>
                <g:set var="designs" value="${(List) designsAndClassification.value}"/>
                <optgroup label="${basicQuery.calculationClassificationList?.get(classification)?.get(language) ?: message(code: 'other_classification')} - ${designs?.size()} ${message(code: 'results')?.toLowerCase()}">
                    <g:each in="${designs.sort({it.parentName + it.name})}" var="design">
                        <g:set var="updateTime" value="${design?.queryLastUpdateInfos?.take(1)?.values()?.updates?.flatten()?.get(0)}"/>
                        <g:set var="updater" value="${design?.queryLastUpdateInfos?.take(1)?.values()?.usernames?.flatten()?.get(0)}"/>
                        <option name="fromEntityId" id="${design?._id}" value="${design?._id}">
                            ${design?.superVerified ? "verified-${design?._id}" : ''}${design?.parentName ? design?.parentName + " - " : ""} ${design?.name ?: design?.operatingPeriod}${updateTime && updater ? " - " + updateTime.format("dd.MM.yyyy") + " / " + updater : ""}
                        </option>
                    </g:each>
                </optgroup>
            </g:each>
        </g:if>
        <g:else>
            <optgroup label="${message(code: type)} - ${designsByType?.collect({ it.value })?.flatten()?.flatten()?.size()} ${message(code: 'results')?.toLowerCase()}">
                <g:each in="${(Map) designsByType}" var="designsAndClassification">
                    <g:set var="classification" value="${designsAndClassification.key}"/>
                    <g:set var="designs" value="${(List) designsAndClassification.value}"/>
                    <g:each in="${designs.sort({it.parentName + it.name})}" var="design">
                        <option name="fromEntityId" id="${design?._id}" value="${design?._id}">
                            ${design?.superVerified ? "verified-${design?._id}" : ''}${design?.parentName ? design.parentName + " - " : ""} ${design?.name ?: design?.operatingPeriod}
                        </option>
                    </g:each>
                </g:each>
            </optgroup>
        </g:else>
    </g:each>
</g:if>
<script>
    $(document).ready(function () {
        disableButtonCopyDesign();

        const allowClear = $(".selectDesignToImport").attr('data-allowClear') === 'true'
        $(".selectDesignToImport").select2({
            allowClear: allowClear,
            matcher: matchStart,
            templateResult: setVerified,
            templateSelection: setVerified
        });
        <g:if test="${!notPrettifyOptGroup}">
            let optgroupState = {};
            prettifySelect2Optgroup('.selectDesignToImport', optgroupState, '#6b9f00')
        </g:if>
    })

    // hacky way to add icons to select2 options, if the design is super verified
    function setVerified(design) {
        if (design.text.includes('verified-' + design.id)) {
            return $('<span>' + design.text.replace('verified-' + design.id, '') + '<i class="fa fa-check oneClickColorScheme" style="font-size: 10px; margin-right: 2px"></i><i class="fas fa-lock" style="font-size: 10px"></i></span>')
        } else {
            return design.text
        }
    }

</script>