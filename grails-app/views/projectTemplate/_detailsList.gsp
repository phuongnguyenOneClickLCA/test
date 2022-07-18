<g:each in="${licenseToTemplateDetailsMap}" var="details">
    <a id="templateDetails-${details.key}" class="templateDetails" style="display: none" href="javascript:" data-content="${details?.value}">
        <i class="fas fa-info-circle greenTick infoCircleSize" style="display: none"></i>
    </a>
</g:each>