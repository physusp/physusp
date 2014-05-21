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
			data: formData,
			success: function(result){
				var data = [];
				if(typeof(result.consumption.aerobic) != "undefined")
					data.push(["aerobic", parseFloat(result.consumption.aerobic.Kcal)]);
				if(typeof(result.consumption.anaerobicLactic) != "undefined")
					data.push(["anaerobicLactic", parseFloat(result.consumption.anaerobicLactic.Kcal)]);
				if(typeof(result.consumption.anaerobicAlactic) != "undefined")
					data.push(["anaerobicAlactic", parseFloat(result.consumption.anaerobicAlactic.Kcal)]);
				showChart(data);
				showAdvancedResults(result);
			}
		});
	}
	
	function convertTimeStringToSeconds(str)
	{
		var tt = str.split(":");
		return ( tt[0] * 3600 + tt[1] * 60 + tt[2] * 1 ) * 1000;
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
	
	function showAdvancedResults(result) {
		if ($("#parameters\\.calculateAnaerobicAlactic").is(":checked")) {
			if ($("#monoexponential").is(":checked"))
				$("#advancedResults").html(
					"<p>" +
						"<strong>v<sub>0</sub></strong>: " + result.consumption.monoexponentialFitCoefficients[0].toFixed(5) + "<br>" +
						"<strong>t<sub>0</sub></strong>: " + result.consumption.monoexponentialFitCoefficients[1].toFixed(5) + "<br>" +
						"<strong>A</strong>: " + result.consumption.monoexponentialFitCoefficients[2].toFixed(5) + "<br>" +
						"<strong>&tau;</strong>: " + result.consumption.monoexponentialFitCoefficients[3].toFixed(5) + "<br>" +
						"<strong>R<sup>2</sup></strong>: " + result.consumption.rSquared.toFixed(5) + "<br>" +
					"</p>"
				);
			else
				$("#advancedResults").html(
					"<p>" +
						"<strong>v<sub>0</sub></strong>: " + result.consumption.biexponentialFitCoefficients[0].toFixed(5) + "<br>" +
						"<strong>t<sub>0</sub></strong>: " + result.consumption.biexponentialFitCoefficients[1].toFixed(5) + "<br>" +
						"<strong>A<sub>1</sub></strong>: " + result.consumption.biexponentialFitCoefficients[2].toFixed(5) + "<br>" +
						"<strong>A<sub>2</sub></strong>: " + result.consumption.biexponentialFitCoefficients[3].toFixed(5) + "<br>" +
						"<strong>&tau;<sub>1</sub></strong>: " + result.consumption.biexponentialFitCoefficients[4].toFixed(5) + "<br>" +
						"<strong>&tau;<sub>2</sub></strong>: " + result.consumption.biexponentialFitCoefficients[5].toFixed(5) + "<br>" +
						"<strong>R<sup>2</sup></strong>: " + result.consumption.rSquared.toFixed(5) + "<br>" +
					"</p>"
				);
			$("#advancedResults").append('<div id="advancedChart"></div>');
			var input = $('#oxygenConsumptionPostExercise').handsontable('getData');
			var series1 = [];
			for(var i = 0 ; i < input.length ; i++) {
				if (input[i][0] == null) break;
				series1.push([convertTimeStringToSeconds(input[i][0]), input[i][1]]);
			}
			
			var series2 = [];
			for(var i = 0; i < series1.length; i++)
				series2.push([series1[i][0],result.consumption.expectedOxygenConsumption[i]]);
			
			$("#advancedChart").highcharts({
				chart: {
					plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
			    },
			    title: {
			        text: 'Exponential Fit'
			    },
			    plotOptions: {
			    	line: {
			    		marker: {
			    			radius: 0
			    		}
			    	},
			    	scatter: {
			    		marker: {
			    			radius: 2
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
		        xAxis: {
		        	type: "datetime",
		        	title: {
		        		text: "Time (minutes)"
		        	},
		        	dateTimeLabelFormats: {
		        		minute: "%M"
		        	}
		        },
		        yAxis: {
		        	min: 0
		        },
		        series: [{
		            data: series1,
		            type: "scatter"
		        },{
		            data: series2,
		            type: "line"
		        }]
			});
			
		}
	}
	
	function greaterThanZero(value, callback){
		if(value >= 0)
			callback(true);
		else
			callback(false);
	}
	
	function hasSelectedAnaerobicLactic(){
		return $("#parameters\\.calculateAnaerobicLactic").is(":checked");
	}
	
	function hasSelectedAnaerobicAlactic(){
		return $("#parameters\\.calculateAnaerobicAlactic").is(":checked");
	}
	
	function hasSelectedAerobic(){
		return $("#parameters\\.calculateAerobic").is(":checked");
	}
	
	function getRestOxygenCalculateMethod(){
		return $("[name='restOxygenParameters.calculateMethod']:checked").val();
	}
	
	function isRestOxygenFixed(){
		return getRestOxygenCalculateMethod() == "fixed";
	}
	
	$(function(){
		
		$.validator.addMethod("greaterThan", 
				function(value, element, params) {

				    return isNaN(value) && isNaN($(params).val()) 
				        || (Number(value) > Number($(params).val())); 
				},'Must be greater than {0}.');
		
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
			
			if(!$(this).valid())
				return false;
			
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
		
		$("#data").validate({
			errorClass: "error",
			rules: {
				"anaerobicAlacticParameters.timeDelayPost": {
					required: function() { return $("#useTimeDelay").is(":checked"); }
				},
				"anaerobicLacticParameters.weight": {
					required: hasSelectedAnaerobicLactic,
					min: 0.0001
				},
				"anaerobicLacticParameters.restLactateConcentration": {
					required: hasSelectedAnaerobicLactic,
					min: 0
				},
				"anaerobicLacticParameters.maxLactateConcentration": {
					required: hasSelectedAnaerobicLactic,
					greaterThan: "#anaerobicLacticParameters\\.restLactateConcentration"
				},
				"restOxygenParameters.fixedValue": {
					required: function() { return (hasSelectedAerobic() || hasSelectedAnaerobicAlactic()) && isRestOxygenFixed(); },
					min: 0
				}
			},
			messages:{
				"anaerobicLacticParameters.weight": {
					required: hasSelectedAnaerobicLactic,
					min: "Please enter a value greater than or equal to 0."
				},
				"anaerobicLacticParameters.maxLactateConcentration": {
					greaterThan: "Must be greater than rest concentration."
				}
			}
		});
		
		var headers = ["Time <strong>(hh:mm:ss)</strong>", "VO<sub>2</sub> <strong>(ml/min)</strong>"];
		
		$('#oxygenConsumptionRest').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
		    columns: [
        	  {
        		  type: 'text',
        		  validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
        		  allowInvalid: false
        	  },
        	  { 	  	
            	  type: 'numeric',
            	  format: '0,0.00',
            	  validator: greaterThanZero,
            	  allowInvalid: false
        	  },
		    ]
		});
		$('#oxygenConsumptionDuringExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
		    columns: [
        	  {
        		  type: 'text',
        		  validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
        		  allowInvalid: false
        	  },
        	  { 	  	
            	  type: 'numeric',
            	  format: '0,0.00',
            	  validator: greaterThanZero,
            	  allowInvalid: false
        	  },
		    ]
		});
		$('#oxygenConsumptionPostExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
		    columns: [
        	  {
        		  type: 'text',
        		  validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
        		  allowInvalid: false
        	  },
        	  { 	  	
            	  type: 'numeric',
            	  format: '0,0.00',
            	  validator: greaterThanZero,
            	  allowInvalid: false
        	  },
		    ]
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
		
		$("#btnAdvancedResults").click(function(){
			$("#advancedResults").fadeToggle();
			var button = $(this);
			if (button.hasClass("active")) button.removeClass("active");
			else button.addClass("active");
		});
		
		Highcharts.setOptions({
			lang: {
		        numericSymbols: null
		    }
		});
	});
})();
