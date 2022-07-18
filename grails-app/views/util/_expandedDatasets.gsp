<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.roundToString as roundToString" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnit as conversionUnit" %>
<%@ page import="static com.bionova.optimi.core.service.SimulationToolService.conversionUnitFactor as conversionUnitFactor" %>
<g:set var="simulationToolService" bean="simulationToolService"/>
<g:set var="datasetService" bean="datasetService"/>

<g:set var="constructionId" value="${simConstruction?.constructionId}"/>

<div style="text-align: center; margin-bottom: 15px;" >
    <g:set var="appendPrivateEye" value="${simConstruction?.name?.startsWith("(PRIVATE)")}" />
    <h4><g:if test="${appendPrivateEye}"><i class="far fa-eye-slash" aria-hidden="true"></i> </g:if>${abbr(value: simConstruction.name, maxLength: '300', substringAfter: "(PRIVATE)")} <br> <g:message code="total"/> Co<sub>2</sub>e [<i id="modalDatasets${constructionId}">${roundToString(simConstruction?.co2e)}</i> tn]</h4>
</div>

<table id="datasetTable${simConstruction?.constructionId}" class="table">
    <thead>
        <tr>
            <g:if test="${!privateConstruction&&!dynamicDefault}">
                <th><g:message code="component"/></th>
            </g:if>
            <th style="white-space: nowrap;">CO<sub>2</sub>e</th>
            <th><g:message code="changeMaterial"/></th>
            <th><g:message code="amount"/></th>
            <th><g:message code="thickness"/> ${conversionUnit(unitSystem, "mm")}</th>
            <th>${commentHeading}</th>

        </tr>
    </thead>

    <g:if test="${privateConstruction||dynamicDefault}">
        <g:each var="group" in="${simConstruction?.constituents}">
            <g:set var="groupId" value="${group?.manualId}" />
            <tr>
                <td><span id="impactModal${groupId}">${roundToString(group?.co2e)} tn</span></td>
                <td>
                    ${group.name}
                    <a href="javascript:" class="infoBubble" rel="resource_sourceListing" id="info${group?.groupId}" data-profileId="${group?.resourceId}" onclick="openSourceListing('${indicatorId}','${group?.resourceId}', '${group?.questionId}', '${group?.profileId}', '${entityId}','${Boolean.TRUE}', 'info${group?.groupId}', '${message(code: 'resource.sourcelisting_no_information_available')}', null, '${group?.queryId}', '${group?.sectionId}', null, 'right')">
                        <i class="fa fa-question greenInfoBubble" aria-hidden="true"></i>
                    </a>
                    <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${group?.resourceId}" questionId="${group?.questionId}" profileId="${group?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${group?.groupId}" queryId="${group?.queryId}" sectionId="${group?.sectionId}"/>
                </td>
                <g:set var="constituentUnit" value="${group?.userGivenUnit}"/>
                <g:set var="constituentAmount" value="${roundToString((group?.amount?:0) * conversionUnitFactor(unitSystem, constituentUnit))}"/>
                <g:set var="thickness" value ="${(group?.modThickness) ? ( simulationToolService.roundToNumber( (group?.thickness?:0) * conversionUnitFactor(unitSystem, "mm"), true  ) ) : null}"/>
                <td id="unit${groupId}">${constituentAmount + " " + conversionUnit(unitSystem, constituentUnit)}</td>
                <td><input id="thickness${groupId}" class="numeric expandedInput" type="text" value="${thickness}" onblur="simulateImpactConstruction($(this).val(), '${group.manualId}')"/></td>
                <td><input id="groupComment${groupId}" class="input-cd-comment" type="text" value="${group?.comment ?: ""}"/></td>
            </tr>
        </g:each>
    </g:if>
    <g:else>
        <g:each var="group" in="${simConstruction?.constituents?.groupBy{it.groupId}}">
            <g:set var="groupId" value="${group?.key}" />
            <g:set var="groupDatasets" value="${group?.value}" />
            <g:if test="${groupDatasets}">
                <g:set var="selectedDataset" value="${groupDatasets.find({it.defaultConstituent })}" />
                <tr>
                    <td>${g.message(code: groupId)}</td>
                    <td><span id="impactModal${groupId}">${roundToString(selectedDataset?.co2e)} tn</span></td>
                    <td>
                        <g:select class="expandedSelect" impact-spanId="impactModal${groupId}" thickness-spanId="thickness${groupId}" name="manualId${groupId}" from="${groupDatasets}" value="${selectedDataset?.manualId}" optionKey="${{it.manualId}}" optionValue="${{it.name}}" />
                        <opt:renderDataCardBtn indicatorId="${indicatorId}" resourceId="${selectedDataset?.resourceId}" questionId="${selectedDataset?.questionId}" profileId="${selectedDataset?.profileId}" childEntityId="${entityId}" showGWP="true" infoId="${selectedDataset?.groupId}" queryId="${selectedDataset?.queryId}" sectionId="${selectedDataset?.sectionId}"/>
                    </td>

                    <g:set var="constituentUnit" value="${selectedDataset?.userGivenUnit}"/>
                    <g:set var="constituentAmount" value="${roundToString((selectedDataset?.amount?:0) * conversionUnitFactor(unitSystem, constituentUnit))}"/>
                    <g:set var="thickness" value ="${(selectedDataset?.modThickness) ? ( simulationToolService.roundToNumber( (datasetService.getThickness(selectedDataset?.additionalQuestionAnswers)?:0) * conversionUnitFactor(unitSystem, "mm"), true  ) ) : null}"/>
                    <td id="unit${groupId}">${constituentAmount + " " + conversionUnit(unitSystem, constituentUnit)}</td>
                    <td><input id="thickness${groupId}" class="numeric expandedInput" type="text" value="${thickness}" onblur="simulateImpactConstruction($(this).val(), $('#manualId${groupId}').val())"/></td>
                    <td><input id="groupComment${groupId}" class="input-cd-comment" type="text" value="${selectedDataset?.comment ?: ""}"/></td>

                </tr>
            </g:if>
        </g:each>
    </g:else>
</table>

<g:if test="${simConstruction?.share}">
    <div style="text-align: right">
        <button class="btn expandedSelect" data-dismiss="modal"><g:message code="cancel"/></button>
            <button id="updateModal" class="btn btn-primary" onclick="updateImpactConstruction()"><g:message code="update"/></button>
            <i id="onUpdate__spinner" class='fas fa-circle-notch fa-spin oneClickColorScheme hidden cd-spinner-expand'></i>
    </div>
</g:if>
<g:else>
    <div style="text-align: right">
        <button class="btn expandedSelect" data-dismiss="modal"><g:message code="close"/></button>
    </div>
</g:else>

<script>

    var localConstruction = findConstruction(model, '${simConstruction?.constructionId}')
    localConstruction = JSON.parse(JSON.stringify(localConstruction))

    var oldCo2e = localConstruction.co2e

    hideThickness();

    checkIfDisableUpdate(localConstruction);

    // Input Field Numeric Class Validation
    $('.numeric').on('input propertychange', numericInputValidation);

    $(".expandedInput").on('focus', function(){
        disableInputsByClass("expandedSelect")
    })

    <g:if test="${!simConstruction?.share}">
        disableInputsByClass("expandedInput")
    </g:if>

    //FUNCTIONS

    function checkIfDisableUpdate(construction){

        let constituents = construction.constituents
        let size = constituents.filter(it => it.defaultConstituent && !it.modThickness).length

        if(construction.amount == 0 || constituents.length == size){
            $("#updateModal").prop("disabled",true);
        }
    }

    function updateImpactConstruction() {

        onUpdateGlobalValue()

        loadComment(localConstruction, true)

        let json = {sessionId : ${sessionId}, jsonConstruction : localConstruction}

        let url = '/app/sec/util/updateConstructionImpact'

        let successCallback = function (data) {
            //check if the update was ok adding return to function
            if (!updateConstructionView(data.simConstruction)) {
                console.log("Replace Wrong -- add return updateConstructionView ")
            }
        }

        ajaxForJson (url, json, successCallback, errorServerReply)

        $("#modal").modal("toggle")
    }

    function simulateImpactConstruction(thickness, constituentId) {


        let inputs = $("#datasetTable${simConstruction?.constructionId}").find(":input");

        let json = {sessionId : ${sessionId}, jsonConstruction : localConstruction}
        let url = '/app/sec/util/simulateConstructionImpact';

        let successCallback = function (data) {
            console.log(localConstruction)
            localConstruction = JSON.parse(JSON.stringify(data.simConstruction))
            updateModalConstruction();
            console.log(data.simConstruction)

            $("#updateModal").prop("disabled",false);
            $("#updateModal").show();
            $("#onUpdate__spinner").addClass("hidden");
            inputs.prop("readonly", false )
            enableInputsByClass("expandedSelect")

        };

        if(localConstruction.amount){

            if(!($.isNumeric(thickness))){return null;}

            let conversionFactor = conversionUnitFactorJS('${unitSystem}', "mm");

            thickness = new Number(thickness / conversionFactor)

            var index = localConstruction.constituents.findIndex(c => c.manualId === constituentId);
            if(index != -1){
                let constituent = localConstruction.constituents[index]
                if(constituent.thickness != thickness){
                    onUpdateConstructionModal("${simConstruction?.constructionId}")
                    constituent.thickness = thickness
                } else{
                    enableInputsByClass("expandedSelect")
                    return null
                }
            }

            $("#updateModal").prop("disabled",true);
            $("#updateModal").hide();
            $("#onUpdate__spinner").removeClass("hidden");
            inputs.prop("readonly", true );
            disableInputsByClass("expandedSelect")

            ajaxForJson (url, json, successCallback, errorServerReply);
        } else {
            updateModalConstruction();
        }

    }

    (function () {
        var previous;

        $("select[name^=manualId]").on('focus', function () {
            // Store the current value on focus, before it changes
            previous = this.value;

        }).on('change', function () {
            var manualId = $(this).val();

            var j = localConstruction.constituents.findIndex(c => c.manualId === previous);
            if(j!=-1){localConstruction.constituents[j].defaultConstituent = false}

            var i = localConstruction.constituents.findIndex(c => c.manualId === manualId);
            if(i!=-1){localConstruction.constituents[i].defaultConstituent = true}

            localConstruction.co2e = localConstruction.constituents.filter(c => c.defaultConstituent)
                                                                   .reduce((sum, c) => sum + c.co2e, 0);

            updateModalConstruction();

            previous = manualId;

        });
    })();

    function updateModalConstruction(){

        let conversionFactor = conversionUnitFactorJS('${unitSystem}', "mm");

        var newCo2e = localConstruction.co2e
        var color

        if(newCo2e > oldCo2e){color = "red"}
        else if(newCo2e < oldCo2e) {color = "green"}
             else {color = "black"}

        let co2e = roundingJS(localConstruction.co2e)
        $("#modalDatasets"+ localConstruction.constructionId).text(co2e).css("color", color);

        localConstruction.constituents.forEach(c => {
            if(c.defaultConstituent === true){
                let impact = roundingJS(c.co2e)
                $("#impactModal"+ c.groupId).text(impact + " tn");
                $("#manualId"+ c.groupId).val(c.manualId);
                //$("#unit"+ c.groupId).text(c.amount + " " + c.userGivenUnit); // funtile in a group always the same
                if(c.modThickness){
                    let t = roundingToNumberJS(c.thickness * conversionFactor)
                    $("#thickness"+ c.groupId).val(t);
                }
                else{
                    $("#thickness"+ c.groupId).val(null);
                }

                let idQMark = 'qMark' + c.groupId;
                let a = document.getElementById(idQMark);
                if ($(a).length) {
                    a.setAttribute("data-profileId", c.resourceId);
                    a.onclick = () =>{
                        renderSourceListingToAnyElement(c.resourceId, c.groupId, '${indicatorId}', c.questionId, c.profileId, '${entityId}', true, c.queryId, c.sectionId)
                    };
                }
            }
        });

        hideThickness();
    }

    function hideThickness(){

        $('#modal').find("input").each((i,el) => {
            if(!$(el).val() && !$(el).hasClass("input-cd-comment")){   $(el).addClass("hidden");}
            else{               $(el).removeClass("hidden");}
        });

    }

    function updateQuestionMark(manualId){

        let i = localConstruction.constituents.findIndex(c => c.manualId === manualId);
        if(i!=-1){
            let constituent = localConstruction.constituents[i];

            let idQMark = 'qMark' + constituent.groupId;

            let a = document.getElementById(idQMark);
            a.setAttribute("data-profileId", constituent.resourceId);
            a.onclick = () =>{
                renderSourceListingToAnyElement(constituent.resourceId, constituent.groupId, '${indicatorId}', constituent.questionId, constituent.profileId, '${entityId}', true, constituent.queryId, constituent.sectionId)
            };
        }
    }

    function onUpdateConstructionModal(constructionId){

        if(constructionId){
            let spinner = "<i class='fas fa-circle-notch fa-spin oneClickColorScheme'></i>"
            $("#modalDatasets" + constructionId).empty().append(spinner)
        }

    }

</script>


