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
            <g:render template="/entity/basicinfoView"/>
        </div>
    </div>

      <div class="container section">
        <div class="sectionbody">
            <div class="span4">
                <div class="well">
                    <h4><g:message code="entity.graphs_presentation_format" /></h4>
                    <label><select id="selectFormat"><option value="absolute" selected="selected"><g:message code="entity.graphs_absolute" /></option><option value="normalize"><g:message code="entity.graphs_normalize" /></option></select></label>
                    <p>&nbsp;</p>
                    <label><select id="selectChart"><option value="bar" selected="selected">Bar</option><option value="spider">Spider</option><%--<option value="pie">Pie</option>--%></select></label>
                    <hr>
                    <div id="chart_controls">
                        <h4><g:message code="graphs.designs" /></h4>
                        <div id="container_chart_designs"></div>
                        <h4><g:message code="entity.show.designs_tools" /></h4>
                        <div id="container_chart_indicators"></div>
                    </div>
                </div>
            </div>
            <div class="span8">
                <div class="well">
                    <div class="spider_chart" id="the_chart"></div>
                </div>
            </div>
        </div>
      </div>


				<script>
					$(document).ready(function() {
						<g:set var="colors" value="${['red', 'blue', 'fuchsia', 'green', 'olive', 'teal', 'black', 'navy', 'purple']}" />

						<g:set var="indicatorAmount" value="${indicators ? indicators.size() : 0}" />
						<g:set var="entityAmount" value="${designs ? designs.size() : 0}" />
						
						// PRINTTAA INDIKAATTORIEN ARRAY SEURAAVASTI:
						var indicators = {
						  <g:each in="${indicators}" var="indicator" status="ind">
							${ind}: "${indicator.localizedName}"${ind + 1 < indicatorAmount ? ',' : ''}
						  </g:each>
						};

						// PRINTTAA TOTEUTUSSUUNNITELMIEN ARRAY SEURAAVASTI
						<g:set var="colorIndex" value="${0}" />
						
						var designs = [
						  <g:each in="${designs}" var="entity" status="i">
						    {
							  id: ${i},
							  name: '${entity.name}',
							  <g:if test="${(colorIndex + 1) > colors.size()}">
							    <g:set var="colorIndex" value="${0}" />
							  </g:if>
							  color: '${colors[colorIndex]}',
							  visible: true,
							  indicators: [
							    <g:each in="${indicators}" var="indicator" status="j">
								  {
								    id: ${j},
								    score: ${scores.get(indicator.indicatorId)?.get(entity.id)}
								  }${j + 1 < indicatorAmount ? ',' : ''}
							    </g:each>
							  ] 
							}${i + 1 < entityAmount ? ',' : ''}
							<g:set var="colorIndex" value="${colorIndex + 1}" />
						  </g:each>
						];

						$("#selectFormat").on('change', function () {
							var val = $(this).val();

							if(val=='absolute') {
								<g:set var="colorIndex" value="${0}" />
								designs = [
								<g:each in="${designs}" var="entity" status="i">
								  {
								    id: ${i},
									name: '${entity.name}',
									<g:if test="${(colorIndex + 1) > colors.size()}">
									  <g:set var="colorIndex" value="${0}" />
									</g:if>
									color: '${colors[colorIndex]}',
									visible: true,
									indicators: [
									<g:each in="${indicators}" var="indicator" status="j">
									  {
										id: ${j},
										score: ${scores.get(indicator.indicatorId)?.get(entity.id)}
									  }${j + 1 < indicatorAmount ? ',' : ''}
									</g:each>
									] 
							      }${i + 1 < entityAmount ? ',' : ''}
								  <g:set var="colorIndex" value="${colorIndex + 1}" />
								</g:each>
								];				
							} else if(val=='normalize') {
								<g:set var="colorIndex" value="${0}" />
								designs = [
								  <g:each in="${designs}" var="entity" status="i">
								  {
								    id: ${i},
									name: '${entity.name}',
									<g:if test="${(colorIndex + 1) > colors.size()}">
									  <g:set var="colorIndex" value="${0}" />
									</g:if>
									color: '${colors[colorIndex]}',
									visible: true,
									indicators: [
									<g:each in="${indicators}" var="indicator" status="j">
									  {
										id: ${j},
										score: ${normalizedScores?.get(indicator.indicatorId)?.get(entity.id)}
									  }${j + 1 < indicatorAmount ? ',' : ''}
									</g:each>
									] 
								  }${i + 1 < entityAmount ? ',' : ''}
								  <g:set var="colorIndex" value="${colorIndex + 1}" />
								</g:each>
								];			
							}
							val = $("#selectChart").val();
							
							if(val=='spider') {
								chart = new DesignChart({
									type: 'spider',
									data_designs: designs,
									data_indicators: indicators,
									design_controls_container_id: 'container_chart_designs',
									indicator_controls_container_id: 'container_chart_indicators',
									chart_container_id: 'the_chart'
								}); 
								showChart();						
							} else if(val=='bar') {
								chart = new DesignChart({
									type: 'bar',
									data_designs: designs,
									data_indicators: indicators,
									design_controls_container_id: 'container_chart_designs',
									indicator_controls_container_id: 'container_chart_indicators',
									chart_container_id: 'the_chart'
								});
								showChart();
							}  
							<%--
							else if(val=='pie') {
								chart = new DesignChart({
									type: "pie",
									data_designs: designs,
									data_indicators: indicators,
									design_controls_container_id: 'container_chart_designs',
									indicator_controls_container_id: 'container_chart_indicators',
									chart_container_id: 'the_chart'
								});
								showChart();
							}
							--%>
						});						

						var chart;
						
						$("#selectChart").on('change', function () {
							var val = $(this).val();
							
							if(val=='spider') {
								chart = new DesignChart({
									type: 'spider',
									data_designs: designs,
									data_indicators: indicators,
									design_controls_container_id: 'container_chart_designs',
									indicator_controls_container_id: 'container_chart_indicators',
									chart_container_id: 'the_chart'
								}); 
								showChart();						
							} else if(val=='bar') {
								chart = new DesignChart({
									type: 'bar',
									data_designs: designs,
									data_indicators: indicators,
									design_controls_container_id: 'container_chart_designs',
									indicator_controls_container_id: 'container_chart_indicators',
									chart_container_id: 'the_chart'
								});
								showChart();
							}
							<%--
							else if(val=='pie') {
									chart = new DesignChart({
										type: 'pie',
										data_designs: designs,
										data_indicators: indicators,
										design_controls_container_id: 'container_chart_designs',
										indicator_controls_container_id: 'container_chart_indicators',
										chart_container_id: 'the_chart'
									});
									showChart();
							}
							--%>	 
						});
						
						chart = new DesignChart({
							type: 'bar',
							data_designs: designs,
							data_indicators: indicators,
							design_controls_container_id: 'container_chart_designs',
							indicator_controls_container_id: 'container_chart_indicators',
							chart_container_id: 'the_chart'
						});
						showChart();
					});
					
					function showChart(){
						$("#chart_controls").show();
						$("#the_chart").show();
					}
					</script>
    	</div>
  </body>
</html>
