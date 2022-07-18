
    <g:each in="${licenses}" var="license">
     <opt:link controller="license" action="form" params="[id:license.id]">${license.name}</opt:link>
        <br>
    </g:each>

