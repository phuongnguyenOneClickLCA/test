<div class="container">
    <div class="screenheader">
        <h2 class="h2heading">
          <opt:link controller="entity" action="show" id="${entity?.id}" class="building">
              <i class="fa fa-home fa-2x"></i>
            ${entity?.name} / ${childEntity?.operatingPeriodAndName ? childEntity?.operatingPeriodAndName : childEntity?.name}
            <g:if test="${indicator}">
              / ${indicator.localizedName}
            </g:if>
          </opt:link>
        </h2>
    </div>
</div>