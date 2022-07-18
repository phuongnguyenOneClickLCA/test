<g:set var="manualId" value="${dataset.manualId}" />
<g:set var="warningThicknessId" value="${"warningThickness" + dataset.manualId}" />
<i id="${warningThicknessId}" rel="popover" data-trigger="hover" data-html="true" data-content="${content}" class="thicknessWarningPopover icon-alert" data-sectionId='${dataset.sectionId}' data-resourceId='${dataset.resourceId}'
   data-questionId='${dataset.questionId}' data-rowId='${rowId}'>
</i>
