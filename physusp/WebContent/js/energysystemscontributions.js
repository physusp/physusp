(function() {
	var _chartOptions = null;
	
	function setEnergySystems() {
		var selectedCalculations = 0;
		$("#options").find("input").each(function(){
			var input = $(this);
			if (input.is(":checked")) {
				$("#tab_" + input.data("tab")).addClass("show");
				selectedCalculations++;
			}
			else
				$("#tab_" + input.data("tab")).removeClass("show");
		});
		
		if (selectedCalculations == 0) {
			$("#validateSystems").addClass("has-error");
			$("#validateSystems").find("label").text("Please select at least one energy system.");
			return;
		}
		
		if ($("#tab_aerobic").hasClass("show")) {
			var restData = $("#restData").detach();
			$("#aerobic").prepend(restData);
		}
		else if ($("#tab_anaerobicAlactic").hasClass("show")) {
			var restData = $("#restData").detach();
			$("#anaerobicAlactic").prepend(restData);
		}
		
		$("#containerTabs").find("li.show").first().find("a").tab("show");
		$("#btnCalculate").show();
		
		$("#validateSystems").removeClass("has-error");
		$("#validateSystems").find("label").text("");
	}
	
	function prepareTableToSend(tableRows){
		var toSend = "";
		for(var i = 0; i < tableRows.length - 1; i++)
			if(tableRows[i][0] != null) {
				toSend += tableRows[i][0] + "," + tableRows[i][1] + "\n";
			}
		return toSend;
	}
	
	function sendFormData(url, formData) {
		$.ajax({
			url: url,
			type: "POST",
			data: formData
		})
		.done(function(result){
			var data = [];
			data.push(["aerobic", result.consumption.aerobic]);
			data.push(["anaerobicLactic", result.consumption.anaerobicLactic]);
			data.push(["anaerobicAlactic", result.consumption.anaerobicAlactic]);
			showChart(data);
		});
	}
	
	function showChart(data) {
		
		_chartOptions = {
			chart: {
				plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
		    },
		    title: {
		        text: 'Energy system contributions'
		    },
		    tooltip: {
		    	pointFormat: '{series.name}: <b>{point.y:.1f} kcal</b> <br/><b>{point.percentage:.1f}%</b>'
		    },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} % ({point.y:.1f} kcal)'
	                }
	            }
	        },
	        exporting: {
	        	buttons: {
	        		contextButton: {
	        			enabled: false
	        		}
	        	}
	        },
	        series: [{
	            type: 'pie',
	            name: 'Contribution',
	            data: data
	        }]
		};
		$('#exp-svg').show();
		$('#exp-png').show();
		$('#exp-jpg').show();
		$("#tabResults").show();
		$('#tabResults a').tab('show');
		$("#btnCalculate").hide();
	}
	
	$(function(){
		
		$("#btnContinue").click(setEnergySystems);
		
		$("#containerTabs a").click(function (e) {
			e.preventDefault();
			var link = $(this), tabId = link.parent().attr("id");
			if (tabId == "tabOptions" || tabId == "tabResults")
				$("#btnCalculate").hide();
			else
				$("#btnCalculate").show();
			link.tab("show");
		});
		
		$('#containerTabs a').on('shown.bs.tab', function (e) {
			var $tab = $($(this).attr('href'));
			$tab.find('.handsontable').each(function(index, element) {
				for(var i = 0; i< 2; ++i) {
					$(element).handsontable('render');
				}
			});
			if ($tab.attr("id") == "results") {
				$("#containerChart").highcharts(_chartOptions);
			}
		});
		
		$("#data").submit(function(){
			var input = $(this).serialize();
			var url = $(this).attr("action");
			
			var oxygenConsumptionRest = $('#oxygenConsumptionRest').handsontable('getData');
			input += "&restOxygenParameters.oxygenConsumptionRest=" + prepareTableToSend(oxygenConsumptionRest);
			
			var oxygenConsumptionDuringExercise = $('#oxygenConsumptionDuringExercise').handsontable('getData');
			input += "&aerobicParameters.oxygenConsumptionDuringExercise=" + prepareTableToSend(oxygenConsumptionDuringExercise);
			
			var oxygenConsumptionPostExercise = $('#oxygenConsumptionPostExercise').handsontable('getData');
			input += "&anaerobicAlacticParameters.oxygenConsumptionPostExercise=" + prepareTableToSend(oxygenConsumptionPostExercise);
			
			sendFormData(url, input);
			
			return false;
		});
		
		var headers = ["Time <strong>(hh:mm:ss)</strong>", "VO<sub>2</sub> <strong>(ml/min)</strong>"];
		
		$('#oxygenConsumptionRest').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all"
		});
		$('#oxygenConsumptionDuringExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all"
		});
		$('#oxygenConsumptionPostExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all"
		});
		
		$('#aerobic input:radio').change(function() {
			$("#aerobicRestTable").toggle($(this).val() == "series");
			$("#aerobicRestAvg").toggle($(this).val() == "fixed");
		});
		
		$('#useTimeDelay').change(function() {
			$("#TimeDelayDiv").toggle();
		});
		
		$(".exportButton").click(function() {
			var format = $(this).data("format");
			var chart = $("#containerChart").highcharts();
			chart.exportChart({
				type: format,
				filename: 'energy systems contributions ' + new Date()
			});
		});
		
	});
})();
