<g:if test="${warnings}">
  <div class="container">
    <div class="alert">
      <button type="button" class="close" data-dismiss="alert">×</button>
      <strong>${warnings.join("<br/>")}</strong>
    </div>
  </div>
</g:if>
