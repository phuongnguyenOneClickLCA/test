<li class="parent_menu" style="font-weight: bold; margin: 5px !important;">${message(code: "graph_explain.design")}</li>
<g:each in="${availableDesigns}" var="design">
    <li onclick="event.stopPropagation()"><label><input type="checkbox" id="${design.operatingPeriodAndName}" onclick="toggleDesign(event, '${message(code:"loading.title")}','${message(code:"query.resource.popover")}'); event.stopPropagation()" name="designName" ${designs?.contains(design) ? "checked" : ""} style="margin-right: 5px">${design.operatingPeriodAndName}</label></li>
 </g:each>
<script type="text/javascript">
    $(function(){
        $('.dropdown-toggle').dropdown();
        $("#designNumbers").text("${availableDesigns.size()} / ${availableDesigns.size()}")
    });
</script>