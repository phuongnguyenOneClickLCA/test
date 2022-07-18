<%@ page import="org.apache.commons.lang.StringUtils" %>
<div class="pieContainer entityshow-pie">
    <div class="pull-right" style="margin-top:20px; margin-right:5px;" id="pie_legend"></div>
    <canvas id="property_types" class="pie" width="200px;"></canvas>

</div>


<script type="text/javascript">
    var options = {
        responsive: false,
        scaleBeginAtZero: true,
        showTooltips: true,
        /*tooltipEvents: [],
         nAnimationComplete: function() {
         this.showTooltip(this.segments, true);
         },*/
        maintainAspectRatio: false,
        tooltipTemplate: "${"<%= value %>%"}",
        //tooltipFillColor: "transparent",
        legendTemplate: "${"<ul class=\\\"<%=name.toLowerCase()%>-legend\\\"><% for (var i=0; i<segments.length; i++){%><li><span style=\\\"background-color:<%=segments[i].fillColor%>\\\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"}"
        // you don't have to define this here, it exists inside the global defaults
    };

    // PIE
    // PROPERTY TYPE DISTRIBUTION
    // context
    var ctx = $("#property_types").get(0).getContext("2d");
    // data
    var datasets = ${datasets};

    // Property Type Distribution
    var propertyTypes = new Chart(ctx).Pie(datasets, options);
    $("#pie_legend").html(propertyTypes.generateLegend());


</script>