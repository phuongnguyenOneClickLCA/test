<g:hiddenField name="queryId" value="${query?.queryId}" />

<g:each in="${query?.sections}" var="section" status="index">
  <g:render template="/query/section" model="[entity:entity, query:query, section:section, isMain:true]" />
</g:each>