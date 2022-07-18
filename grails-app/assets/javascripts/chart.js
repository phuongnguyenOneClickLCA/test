function DesignChart(settings){

	var spiderChart = null;
	var barChart = null;

	var settings = settings;
	
	var chartType = (settings.type!='bar' && settings.type!='spider' && settings.type!='pie')? 'bar':settings.type;
	var dataType = settings.dataType ? settings.dataType : 'abs';
	var designs = settings.data_designs;
	var indicators = settings.data_indicators;
	var summary = settings.summary || false;

	var design_controls_id = settings.design_controls_container_id;
	var indicator_controls_id = settings.indicator_controls_container_id;	
	var chart_id = settings.chart_container_id;

	function onCreate() {
		initIndicatorCheckboxes();
		initDesigns();

            $('#' + design_controls_id + ' .rebuildchart').on('click', function (event) {
                rebuildChart();
            });

            $('#' + indicator_controls_id + ' .rebuildchart').on('click', function (event) {
                rebuildChart();
            });

		var btnContainer = $("#" + chart_id).siblings('.btn-toolbar');

		$('.btn.bar', btnContainer).on('click', function() {
			$('.btn.spider', btnContainer).removeClass('active');
			$(this).addClass('active');
			chartType = 'bar';
			rebuildChart();
			return false;
		});

		$('.btn.spider', btnContainer).on('click', function() {
			$('.btn.bar', btnContainer).removeClass('active');
			$(this).addClass('active');
			chartType = 'spider'
			rebuildChart();
			return false;
		});

		$('.btn.abs', btnContainer).on('click', function() {
			$('.btn.norm', btnContainer).removeClass('active');
			$(this).addClass('active');
			dataType = 'abs';
			rebuildChart();
			return false;
		});

		$('.btn.norm', btnContainer).on('click', function() {
			$('.btn.abs', btnContainer).removeClass('active');
			$(this).addClass('active');
			dataType = 'norm';
			rebuildChart();
			return false;
		});
		
		rebuildChart();
	}
	
	function getMaxFromDesigns(indicator_id){
		var max = 0;
		$.each(designs, function(key, design) {
            if(design.visible) {
			$.each(design['indicators'], function(key, indicator) {
				if(indicator['id'] == indicator_id) {
					var score = dataType == 'abs' ? indicator['score'] : indicator['scoreNormalized'];
					if(max < score) {
						max = score;
					}
				}
			});
            }
		});
		return max;
	}
	
	function getMinFromDesigns(indicator_id){
		var min = 0;
		$.each(designs, function(key, design) {
            if(design.visible) {
                $.each(design['indicators'], function(key, indicator) {
				if(indicator['id'] == indicator_id) {
					var score = dataType == 'abs' ? indicator['score'] : indicator['scoreNormalized'];
					if(min > score) {
						min = score;
					}
				}
			});
            }
		});
		return min;
	}

	function getMax(designs) {
		var max = 0;
		for (var j = 0; j < designs.length; j++) {
			var score = dataType == 'abs' ? designs[j].indicators[0].score : designs[j].indicators[0].scoreNormalized;

			if (score > max) {
				max = score;
			}
		}
		return max;
	}

	function getMin(designs) {
		var min = 0;
		for (var j = 0; j < designs.length; j++) {
			var score = dataType == 'abs' ? designs[j].indicators[0].score : designs[j].indicators[0].scoreNormalized;

			if (score < min) {
				min = score;
			}
		}
		return min;
	}
	
	function getIndicatorNameById(id){
		var retval = '';
		$.each(indicators, function(key, value) {
			if(key == id){
				retval = value.substring(0,25);
				return false;
			}
		});
		return retval;
	}

	function getIndicatorScoresFromDesign(design){
		var data = new Array();
		//var selectedIndicators = getSelectedIndicators();
		$.each(design['indicators'], function(key, value) {
			//if(selectedIndicators[value['id']] != undefined) {
				data.push(value['score']);
			//}
		});
		return data;
	}

	function getIndicatorDataFromDesign(design){
		var data = new Array();
		//var selectedIndicators = getSelectedIndicators();
		$.each(design['indicators'], function(key, value) {
			//if(selectedIndicators[value['id']] != undefined) {
				var name = getIndicatorNameById(value['id']);
				data[name] = dataType == 'abs' ? value['score'] : value['scoreNormalized'];
			//}
		});
		return data;
	}

	function getDesignDataByIndicator(indicator){
		var data = new Array();
		var selectedDesigns = getSelectedDesigns();
		$.each(selectedDesigns, function(key, value) {
			var item = new Array();
			for (var i=0;i<value.indicators.length;i++){
				if (value.indicators[i].id == indicator && value.indicators[i]!=0){
					var item = {};
					item.text=value.indicators[i]['score'];
					item.y=value.indicators[i]['score'];
					item.fill=value['color'];
					data.push(item);
				}
				
			}
		});
		
		return data;
	}
	
	function calculatePercentages(data){
		var sum = 0;
		for(var i=0;i<data.length;i++){
			sum+=data[i].y;
		}
		
		var newData = Array();
		
		for(var i=0;i<data.length;i++){
			data[i].text = Math.round(data[i].y*1000/sum)/10;
			data[i].y = data[i].y*100/sum;

			if (data[i].y>0.1){
				newData.push(data[i]);
			}
		}

		return newData;
	}

	
	function getDesignById(id){
		var design = {};
		$.each(designs, function(key, value){
			if(value['id'] == id) {
				design = value;
				return false;
		}
		});
		return design;
	}

	function getSelectedDesigns(){
		var arr = new Array();
		$('#' + design_controls_id + ' .design:checked').each(function(){
			var design_id = $(this).val();
			arr.push(getDesignById(design_id));
		});
		return arr;
	}

	function getSelectedIndicators(){
		var arr = new Array();
		$('#' + indicator_controls_id + ' .indicator:checked').each(function(){
			var indikator_id = $(this).val();
			arr[indikator_id] = indicators[indikator_id];
		});
		return arr;
	}

	function initIndicatorCheckboxes(){
		//var str = '<div class="row-fluid"><div class="span4">';
		var str = '';
		var items_per_row = 9;
		var items_per_cell = 3;
		$.each(indicators, function(key, value) {
			/*if(items_per_row < 1) {
				str += '</div></div><div class="row-fluid"><div class="span4">';
				items_per_row = 9;
				items_per_cell = 3;
			}
			if(items_per_cell < 1) {
				str += '</div><div class="span4">';
				items_per_cell = 3;
			}*/
			str += '<label class="checkbox"><input class="rebuildchart indicator" checked type="checkbox" value="'+ key +'" name="indicator" id="indicator_' + key + '"> ' + value + '</label>';
			items_per_cell--;
			items_per_row--;
		});
		// str += '</div></div>';
		$('#'+indicator_controls_id).html(str);
	}

	function initDesigns(){
		var str = ''
		$.each(designs, function(key, design) {
            if (design.visible) {
			    str += '<label><input class="rebuildchart design" checked type="checkbox" name="design" value="' + design['id'] + '"> ' + design['name']  + ' <div class="entity_chart_color" style="background-color: ' + design['color'] + '">&nbsp;</div></label>';
            } else {
                str += '<label><input class="rebuildchart design" disabled="disabled" type="checkbox" name="design" value="' + design['id'] + '"> ' + design['name']  + ' <div class="entity_chart_color" style="background-color: ' + design['color'] + '">&nbsp;</div></label>';
            }
        });
		$('#'+design_controls_id).html(str);
	}
	
	function getRadiusForSpiderChart() {
		var w = $(window).width(), limits = [], r = 50, min, max, _r;
		var limits = [];
			limits.push([0, 1200, 220]);
			limits.push([ 1200,  980, 120]);
			limits.push([  980,  768,  80]);
		for (var i = 0; i < limits.length; i++) {
			if (w < parseInt(limits[i][0]) && w >= parseInt(limits[i][1])) {
				r = parseInt(limits[i][2]);
			}
		}
		//console.log('W: '+w + ', R: '+r);
		return r;
	}

	function initSpiderChart(){
		require([
			"dojox/charting/Chart",
            "themes/PieTheme",
			"dojox/charting/plot2d/Spider",
			"dojox/charting/axis2d/Base",
			"dojo/domReady!"
		], function(Chart, theme, Spider, Base){
			if(spiderChart) {
				spiderChart = null;
			}
			spiderChart = new Chart(chart_id);

			spiderChart.setTheme(theme);

			spiderChart.addPlot("default", {
				type: Spider, 
				radius: 200,
				fontColor: "black",
				labelOffset: "-20",
				spiderType: "polygon"
			});
			
			var selected_indicators = indicators;
			$.each(selected_indicators, function(key, indicator) {
				if(indicator != undefined) {
					var maxval = getMaxFromDesigns(key);	
					var minval = getMinFromDesigns(key);	
					var name = indicator.substring(0,25);
					spiderChart.addAxis(name, {type:Base, min: minval, max: maxval });
				}
			});
			
			// Add the series of data
			var selected_designs = getSelectedDesigns();
			$.each(selected_designs, function(key, design) {
				var design_scores = getIndicatorDataFromDesign(design);
				spiderChart.addSeries(
					design['id'],
					{
						data: design_scores
					},
					{
						fill: design['color']
					}
				);
			});
			
			spiderChart.render();
			selected_designs = null;
		});
	}
	
	/*function initSpiderChart(){
		require([
			"dojox/charting/Chart",
			"dojox/charting/themes/Claro",
			"dojox/charting/plot2d/Spider",
			"dojox/charting/axis2d/Base",
			"dojo/domReady!"
		], function(Chart, theme, Spider, Base){
			if(spiderChart) {
				spiderChart = null;
			}
			spiderChart = new Chart(chart_id);

			theme.chart.fill = "transparent";
  			theme.plotarea.fill = "transparent";
  			theme.chart.stroke = "none";

			spiderChart.setTheme(theme);
			spiderChart.addPlot("default", {
				type: Spider, 
				radius: 200, // getRadiusForSpiderChart(),
				fontColor: "black",
				labelOffset: "-20",
				spiderType: "polygon",
				fill: "none"
			});
			
			var designs = getSelectedDesigns();
			var labels = [];
			var values = [];

			var min = getMin(designs)
			var max = getMax(designs)
			for (var i = 0; i < designs.length; i++) {
				var name = designs[i].name.substring(0,30);
				spiderChart.addAxis(name, {type: Base, min: min, max: max});
				values[name] = dataType == 'abs' ? designs[i].indicators[0].score : designs[i].indicators[0].scoreNormalized;				
			}
			spiderChart.addSeries(0, { data: values }, {fill: 'green'});
			
			spiderChart.render();

			selected_designs = null;
		});
	}*/
	
	function initBarChart(){
		require([
			"dojox/charting/Chart",
			"dojox/charting/themes/Claro",
			"dojox/charting/plot2d/Columns",
			"dojox/charting/plot2d/Markers",
			"dojox/charting/plot2d/ClusteredColumns",
			"dojox/charting/axis2d/Default",
			"dojo/domReady!"
		], function(Chart, theme) {

			if (barChart) {
				barChart = null;
			}
            barChart = new Chart(chart_id);

			theme.chart.fill = "transparent";
  			theme.plotarea.fill = "transparent";
  			theme.chart.stroke = "none";

			barChart.setTheme(theme);

			// Add the only/default plot
			barChart.addPlot("default", {
				type: "ClusteredColumns",
				markers: true,
				gap: 5,
                labels: true,
                labelStyle: "outside"
			});

			var designs = getSelectedDesigns();
			var labels = [];
			var values = [];


			for (var i = 0; i < designs.length; i++) {
				labels.push({value: i+1, text:designs[i].name.substring(0,30)});
				var score;

                if (summary) {
					var sum = 0;
					for (var j = 0; j < designs[i].indicators.length; j++) {
						sum += designs[i].indicators[j].score; //there is no idea to use scoreNormalized
					}
					score = sum;
				} else {
                    if (designs[i].indicators) {
					    score = dataType == 'abs' ? designs[i].indicators[0].score : designs[i].indicators[0].scoreNormalized;
                    } else {
                        score = 0;
                    }
				}
                values.push({x: i+1, y: score, fill: designs[i].color});
            }

			if (summary && dataType == 'norm') {
				var biggest = 0;
				for (var i = 0; i < values.length; i++) {
					if (values[i].y > biggest) {
						biggest = i;
					}
				}
				for (var i = 0; i < values.length; i++) {
					if (i != biggest) {
						var value = Math.round((values[i].y/values[biggest].y)*100);
						values[i].y = value;
					}
				}
				values[biggest].y = 100;
			}

			barChart.addAxis(
				"x",
				{
					minorLabels: true,
					natural: true, 
					labels: labels,
					rotation: 30
				}
			);
			barChart.addAxis("y", { type: "Invisible", vertical: true, fixLower: "major", fixUpper: "major", min: 0 });

			barChart.addSeries('Serie', values);
			
			barChart.render();

		});
	}
	
	
	function initPieChart(){
		$("#"+chart_id).empty();
		$.each(getSelectedIndicators(), function(indicatorId, indicatorValue) {
			if (indicatorValue)
			{			
				$("#"+chart_id).append("<div id='" + chart_id+indicatorId + "'></div>");
				require([
						"dojox/charting/Chart",
						"dojox/charting/themes/Claro",
						"dojox/charting/plot2d/Pie",
						"dojo/store/Memory",
						"dojo/domReady!"
					], function(Chart, theme) {
						var pieChart = null;
											
						pieChart = new Chart(chart_id+indicatorId);
						pieChart.setTheme(theme);

						pieChart.addPlot("default", {
					            type: "Pie", 
					            radius: 200,
					            fontColor: "black",
					            labelOffset: -20
					        });
					 

						var scores = getDesignDataByIndicator(indicatorId);					
						var percentages = calculatePercentages(scores);
						  					
						pieChart.addSeries("design", percentages	);	
						pieChart.render();

					});
			}
		});

			
		
	}

	function rebuildChart() {
		var timer = null;

		chartInitialization = function(){
			if(chartType == 'spider') {
				initSpiderChart();
            }
			else {
				if(chartType == 'pie') {
                    initPieChart();
				}
				else {
					initBarChart();
                }
            }
		}
		
		clearChart = function() {
            $('#'+chart_id).html('');
            $('#'+chart_id).removeData('');
		}
		clearChart();
		chartInitialization();		
	}
	
	onCreate();
}