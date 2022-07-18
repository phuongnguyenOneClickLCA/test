<%@ page import="com.bionova.optimi.core.Constants; com.bionova.optimi.core.domain.mongo.ScopeToSave; com.bionova.optimi.core.domain.mongo.Question" %>

<g:set var="predefinedScopeQuestion" value="${(Question) scopeQuestions?.find{it.questionId == "predefinedScope"}}" />
<g:set var="projectScopeQuestion" value="${(Question) scopeQuestions?.find{it.questionId == "projectScope"}}" />
<g:set var="projectTypeQuestion" value="${(Question) scopeQuestions?.find{it.questionId == "projectType"}}" />
<g:set var="frameTypeQuestion" value="${(Question) scopeQuestions?.find{it.questionId == "frameType"}}" />
<g:set var="questionService" bean="questionService"/>
<g:set var="valueReferenceService" bean="valueReferenceService"/>

<g:set var="disabled" value="${!(allowedScopes)}" />

<g:set var="lcaCheckerList" value="${"lcaCheckerList"}"/>

<div id="div-scope">
    <g:if test="${allowedScopes}">
        <label class="control-label fiveMarginVertical" id="scopeLabelId">
            ${predefinedScopeQuestion?.localizedQuestion}
            <g:if test="${predefinedScopeQuestion?.help}">
                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover" data-content="${questionService.getLocalizedHelp(predefinedScopeQuestion)}">
                    <i class="icon-question-sign"></i>
                </a>
            </g:if>
        </label>
        <select name="scopeId" id="scopeId" onchange="onChangeScope(this)" ${disabled ? "disabled=\"disabled\"" : ""}>
            <option value=""></option>
            <g:each in="${allowedScopes}" var="allowedScope">
                <option value="${allowedScope.scopeId}" ${scope?.scopeId?.equals(allowedScope.scopeId) ? "selected=\"selected\"" : ""} data-compatibleindicatorlist="${allowedScope.compatibleIndicatorList?.collect({"'"+it+"'"})?:[]}">${allowedScope.localizedName}</option>
            </g:each>
        </select>

    </g:if>
    <g:if test="${entity?.entityClass == Constants.EntityClass.BUILDING.toString()}">

        <tmpl:/entity/selectScopeTemplate question="${projectTypeQuestion}" defaultValue="${scope?.projectType}"
                                          disabled="${false}"/>

        <g:set var="frameTypeDefaultValue"
               value="${scope?.frameType ?: frameTypeQuestion?.defaultValueFromParentEntity ? valueReferenceService.getValueForEntity(frameTypeQuestion.defaultValueFromParentEntity, parentEntity, null, true) ?: null : null}"/>

        <tmpl:/entity/selectScopeTemplate question="${frameTypeQuestion}" defaultValue="${frameTypeDefaultValue}"
                                          disabled="${false}"/>

        <div id="div-scope-details" class="fiveMarginVertical">
        <label class="control-label">
            <b>${projectScopeQuestion?.localizedQuestion}</b>
            <g:if test="${projectScopeQuestion?.help}">
                <a href="#" data-toggle="dropdown" rel="popover" data-trigger="hover"
                   data-content="${questionService.getLocalizedHelp(projectScopeQuestion)}">
                    <i class="icon-question-sign"></i>
                </a>
            </g:if>
        </label>

        <g:if test="${projectScopeQuestion?.choices}">
            <g:each var="choice" in="${projectScopeQuestion.choices}">
                <label class="checkbox">
                    <g:checkBox id="lcaChecker-${choice?.answerId}" name="${lcaCheckerList}" class="${lcaCheckerList}" value="${choice?.answerId}" checked="${scope?.lcaCheckerList?.contains(choice?.answerId)}" disabled="${false}"/>
                    ${choice?.localizedAnswer}
                </label>
            </g:each>
        </g:if>
    </div>

    </g:if>
</div>

<script>

    var jsonScopes = ${jsonScopes ? jsonScopes : 'null'};

    <g:if test="${!scope}">
        $("#scopeId").change()
    </g:if>

    <g:if test="${!allowedScopes?.find{it.scopeId == scope?.scopeId} && !(scope?.lcaCheckerList)}">
        defaultCheckScope()
    </g:if>

    <g:if test="${allowedScopes?.size() == 1}">
        $("#scopeId").val("${allowedScopes.get(0).scopeId ?: ""}")
        $("#scopeId").change()
    </g:if>

    function onChangeScope(scopeSelect) {

        if(jsonScopes){
            let scopeId = $(scopeSelect).val();
            let defaultMap = jsonScopes[scopeId]
            if(defaultMap){
                let selectedFrameType = "${frameTypeDefaultValue}"
                let frameTypeDefault = selectedFrameType ? selectedFrameType : defaultMap["frameTypeDefault"]
                let frameTypeQuestionID = "frameType"
                setValueSelect(frameTypeQuestionID, frameTypeDefault)

                let selectedProjectType = "${scope?.projectType}"
                let projectTypeDefault = selectedProjectType ? selectedProjectType : defaultMap["projectTypeDefault"]
                let projectTypeQuestionID = "projectType"
                setValueSelect(projectTypeQuestionID, projectTypeDefault)

                let lcaCheckerDefaultList = defaultMap["lcaCheckerDefaultList"]
                let lcaCheckerQuestionID = "projectScope"
                setValueCheckBoxes(lcaCheckerDefaultList)
            }
        }

    }

    function setValueSelect (selectId, value){
        if(selectId && value){
            $("." + selectId).val(value).trigger('change');
        }
    }

    function setValueCheckBoxes(lcaCheckerDefaultList) {

        if(lcaCheckerDefaultList && lcaCheckerDefaultList.length > 0){

            let lcaCheckerList = "${lcaCheckerList}"
            let checkboxes = document.querySelectorAll("." + lcaCheckerList)
            checkboxes = Array.from(checkboxes)

            if(checkboxes && checkboxes.length > 0){

                checkboxes.forEach( c => {
                    if(lcaCheckerDefaultList.find(d => d === c.value)){
                        c.checked = true
                    } else {
                        c.checked = false
                    }
                })
            }

        }

    }

    function showHideDivWithLabel(divId, label){

        var div = $('#' + divId)
        var isHidden = div.hasClass("hide")

        var i = $(label).children("i")[0]
        i = $(i)

        //check that div and i are not null
        if(isHidden){
            div.removeClass("hide")
            i.removeClass("fa-plus")
            i.addClass("fa-minus")
        } else {
            div.addClass("hide")
            i.removeClass("fa-minus")
            i.addClass("fa-plus")
        }
    }

    function defaultCheckScope(){

        let checkBoxClass = '${lcaCheckerList}'

        let fon = "foundation"
        let s = "structure"
        let fin = "finishes"

        if(checkBoxClass){
            $("." + checkBoxClass).each(function(i, c){
                if(c){
                    if(c.value === fon || c.value === s || c.value === fin ){
                        c.checked = true
                    } else {
                        c.checked = false
                    }
                }
            })
        }

    }

</script>