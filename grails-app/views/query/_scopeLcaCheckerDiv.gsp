<div class="section collapsed">
    <div id="scopeLcaChecker-div" class="div-scope sectionheader" onclick="renderScopeLcaChecker('${entity?.id}', '${indicator?.indicatorId}', this);">

        <a class="pull-left sectionexpanderspec" onclick="event.preventDefault()" >
            <i class="icon icon-chevron-down expander"></i><i class="icon icon-chevron-right collapser"></i></a>

        <h2 style="margin-left: 8px" id="lcaCheckerHeading">
            <g:message code="scope.scopeSection" args="${["%", "-"]}"/>
        </h2>
    </div>
    <div id="scopeTotal-div" class="sectionbody show-on-print" style="display: none">
        <div id="scopeChecker-div"></div>

        <div id="lcaChecker-div">
            <div id="lcaCheckerAlertDiv"></div>
            <div id="lcaCheckerNoGraphContainer"></div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function renderScopeLcaChecker(entityId, indicatorId, expansionElement) {
        if(document.getElementById('lcaCheckerNoGraphContainer').innerHTML !== "") {
            toggleExpandSection(expansionElement);
            return;
        }
        appendLoaderToButton('lcaCheckerHeading');
        var queryString = 'entityId=' + entityId + '&indicatorId=' + indicatorId;
        $.ajax({
            type: 'POST',
            async: true,
            data: queryString,
            url: '/app/sec/entity/renderLcaCheckerTemplate',
            success: function (data) {
                if (data.output) {
                    $('#scopeChecker-div').append(data.output.scopeTemplate);
                    $('#lcaCheckerNoGraphContainer').append(data.output.template);

                    if (data.output.link) {
                        if(data.output.missingData){
                            $("<div id='lcaCheckerAlert' class='alert alert-info'>\n" +
                                "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                                "  <i class=\"fas fa-info pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                                "  <strong>" + data.output.link + "</strong>\n" +
                                "</div>").prependTo("#lcaCheckerAlertDiv");
                        } else{
                            $("<div id='lcaCheckerAlert' class='alert alert-success'>\n" +
                                "  <button data-dismiss='alert' class='close' type='button'>×</button>\n" +
                                "  <i class=\"far fa-thumbs-up pull-left\" style=\"font-size: large; margin-right: 8px;\"></i>" +
                                "  <strong>" + data.output.link + "</strong>\n" +
                                "</div>").prependTo("#lcaCheckerAlertDiv");
                        }
                    }
                    if (data.output.heading) {
                        $("#lcaCheckerHeading").text(data.output.heading)
                    }
                }
                toggleExpandSection(expansionElement);
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("Scope and Lca checker fail");
            }
        });
    }

    function showHideDivWithLabel(divId, label){

        var div = $('#' + divId)
        var isHidden = div.hasClass("hide")

        var i = $(label).children("i")[0]
        i = $(i)

        //check that div and i are not null
        if(isHidden){
            div.removeClass("hide")
            i.removeClass("icon icon-chevron-right")
            i.addClass("icon icon-chevron-down")
        } else {
            div.addClass("hide")
            i.addClass("icon icon-chevron-right")
            i.removeClass("icon icon-chevron-down")
        }
    }

    function showHideElementsWithLabel(elementSelector, label){

        var rows = $(elementSelector)
        var isHidden = rows.hasClass("hide")

        var i = $(label).children("i")[0]
        i = $(i)

        //check that div and i are not null
        if(isHidden){
            rows.removeClass("hide")
            i.removeClass("fa-plus")
            i.addClass("fa-minus")
        } else {
            rows.addClass("hide")
            i.removeClass("fa-minus")
            i.addClass("fa-plus")
        }
    }
</script>