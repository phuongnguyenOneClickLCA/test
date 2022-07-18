<%-- Copyright (c) 2012 by Bionova Oy --%>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
      <meta name="format-detection" content="telephone=no"/>
  </head>
  <body>

      <div class="container">
          <div class="screenheader">
              <h4> <opt:link controller="main" removeEntityId="true"><g:message code="main" /></opt:link> >
                  <sec:ifLoggedIn>
                      <g:if test="${parentEntity?.id}">
                          <opt:link controller="entity" action="show" id="${parentEntity?.id}">
                              <g:if test="${entity}">
                                  <a href="#" rel="popover" data-trigger="hover" data-content="${parentEntity.name} - ${entity.operatingPeriodAndName}"><g:abbr value="${parentEntity.name}" maxLength="20" /> - <g:abbr value="${entity.operatingPeriodAndName}" maxLength="15" /></a>
                              </g:if>
                              <g:else>
                                  <g:abbr value="${parentEntity.name}" maxLength="38" />
                              </g:else>
                          </opt:link>
                      </g:if>
                      <g:elseif test="${entity?.id}">
                          <opt:link controller="entity" action="show" id="${entity?.id}" >
                              <g:if test="${childEntity}">
                                  <a href="#" rel="popover" data-trigger="hover" data-content="${entity.name} - ${childEntity.operatingPeriodAndName}"><g:abbr value="${entity.name}" maxLength="20" /> - <g:abbr value="${childEntity.operatingPeriodAndName}" maxLength="15" /></a>
                              </g:if>
                              <g:else>
                                  <g:abbr value="${entity.name}" maxLength="38" />
                              </g:else>
                          </opt:link>
                      </g:elseif>
                  </sec:ifLoggedIn>
                  > <g:message code="${operatingPeriod?.operatingPeriod ? operatingPeriod.operatingPeriod : 'new.period'}" dynamic="true"/> <br/> </h4>
              <g:render template="/entity/basicinfoView"/>
              <h3><g:message code="${operatingPeriod?.operatingPeriod ? operatingPeriod.operatingPeriod : 'new.period'}" dynamic="true"/></h3>

          </div>
      </div>

      <div class="container section">
        <div class="sectionbody">
          <g:uploadForm action="save" useToken="true">
            <g:hiddenField name="parentEntityId" value="${entity?.id}" />
            <g:hiddenField name="id" value="${operatingPeriod?.id}"/>
            <g:hiddenField name="originalOperatingPeriodId" value="${originalOperatingPeriod?.id}" />

            <div class="clearfix"></div>

            <div class="column_left">

              <div class="control-group">
                <label for="operatingPeriod" class="control-label"><g:message code="entity.operatingPeriod" /></label>
                <div class="controls">
                 <opt:select entity="${entity}" name="operatingPeriod" value="${operatingPeriod?.operatingPeriod ? operatingPeriod.operatingPeriod : originalOperatingPeriod?.operatingPeriod}" from="${choosableYears}" noSelection="['':'']" />
                </div>
              </div>

              <div class="control-group">
                <label for="name" class="control-label"><g:message code="entity.operatingPeriod.name" /></label>
                <div class="controls"><opt:textField entity="${entity}" name="name" value="${operatingPeriod?.name}" class="input-xlarge" /></div>
              </div>
            </div>

            <div class="clearfix"></div>

            <opt:submit entity="${entity}" name="save" value="${operatingPeriod?.id ? message(code:'save') : message(code:'add')}" class="btn btn-primary"/>
            <opt:link action="cancel" class="btn"><g:message code="cancel" /></opt:link>
          </g:uploadForm>
        </div>
    </div>
  </body>
</html>



