<g:set var="language" value="${org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage().toUpperCase()}"/>

<g:if test="${originalEntity}">
    <div class="control-group">
        <label for="name" class="control-label"><g:message code="entity.name" /></label>
        <div class="controls">
            <opt:textField entity="${entity}" design="${originalEntity}" name="name" value="${message(code: 'average_of') +" "+ originalEntity?.name}" class="input-xlarge ${originalEntity?.name ? '' : 'redBorder'}" onkeyup="validateName(this, '${message(code:"invalid_character")}', 'createAverageDesign')" required="true" />
            <div class="hidden"><p class="warningRed"></p></div>

        </div>
    </div>

    <div class="control-group">
        <label for="comment" class="control-label"><g:message code="design.comment" /></label>
        <div class="controls"><opt:textField entity="${entity}" design="${originalEntity}" name="comment" value="${originalEntity?.comment}" class="input-xlarge" /></div>
    </div>

    <div class="control-group">
        ${message(code: 'average.design.a1_a3.avgresults')}
    </div>

</g:if>

<script type="text/javascript">
    $("input, select").on("change", function () {
        validateName($("#name"), '${message(code:"invalid_character")}', 'createAverageDesign')
    })

</script>





