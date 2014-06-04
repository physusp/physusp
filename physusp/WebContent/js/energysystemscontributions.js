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
		
		$("#tabResults").addClass("show");
		$("#containerTabs").find("li.show").eq(1).find("a").tab("show");
		
		$("#validateSystems").removeClass("has-error");
		$("#validateSystems").find("label").text("");
		
		$("#btnNext").off("click").click(nextEnergySystem);
	}
	
	function nextEnergySystem() {
		if(!$("#data").valid()) return;
		
		var tab = $("#containerTabs").find("li.active").nextAll(".show:first");
		if (tab.attr("id") == "tabResults")
			calculateResults();
		else
			tab.find("a").tab("show");
	}
	
	function previousEnergySystem() {
		var tab = $("#containerTabs").find("li.active").prevAll(".show:first");
		if (tab.attr("id") == "tabOptions")
			$("#btnNext").off("click").click(setEnergySystems);
		if ($("#advancedResults").css("display") == "block")
			$("#btnAdvancedResults").click();
		$("#exportDropdown").hide();
		$("#btnAdvancedResults").hide();
		tab.find("a").tab("show");
	}
	
	function calculateResults() {
		var form = $("#data");
		if(!form.valid()) return;
		
		var input = form.serialize();
		var url = form.attr("action");
		
		var oxygenConsumptionRest = $('#oxygenConsumptionRest').handsontable('getData');
		input += "&restOxygenParameters.oxygenConsumptionRest=" + prepareTableToSend(oxygenConsumptionRest);
		
		var oxygenConsumptionDuringExercise = $('#oxygenConsumptionDuringExercise').handsontable('getData');
		input += "&aerobicParameters.oxygenConsumptionDuringExercise=" + prepareTableToSend(oxygenConsumptionDuringExercise);
		
		var oxygenConsumptionPostExercise = $('#oxygenConsumptionPostExercise').handsontable('getData');
		input += "&anaerobicAlacticParameters.oxygenConsumptionPostExercise=" + prepareTableToSend(oxygenConsumptionPostExercise);
		
		sendFormData(url, input);
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
				if(typeof(result.consumption.aerobic) != "undefined"){
					data.push(["Aerobic", parseFloat(result.consumption.aerobic.Kcal)]);
					var aerobicRow = $("#aerobicRow");
					aerobicRow.removeClass("hide");
					aerobicRow.find("td:nth-of-type(2)").text(parseFloat(result.consumption.aerobic.Kcal).toFixed(2));
					aerobicRow.find("td:nth-of-type(3)").text(parseFloat(result.consumption.aerobic.KJ).toFixed(2));
					aerobicRow.find("td:nth-of-type(4)").text(parseFloat(result.consumption.aerobic.LO2).toFixed(2));
				}
				if(typeof(result.consumption.anaerobicLactic) != "undefined"){
					data.push(["Anaerobic Lactic", parseFloat(result.consumption.anaerobicLactic.Kcal)]);
					var anaerobicLacticRow = $("#anaerobicLacticRow");
					anaerobicLacticRow.removeClass("hide");
					anaerobicLacticRow.find("td:nth-of-type(2)").text(parseFloat(result.consumption.anaerobicLactic.Kcal).toFixed(2));
					anaerobicLacticRow.find("td:nth-of-type(3)").text(parseFloat(result.consumption.anaerobicLactic.KJ).toFixed(2));
					anaerobicLacticRow.find("td:nth-of-type(4)").text(parseFloat(result.consumption.anaerobicLactic.LO2).toFixed(2));
				}
				if(typeof(result.consumption.anaerobicAlactic) != "undefined"){
					data.push(["Anaerobic Alactic", parseFloat(result.consumption.anaerobicAlactic.Kcal)]);
					var anaerobicAlacticRow = $("#anaerobicAlacticRow");
					anaerobicAlacticRow.removeClass("hide");
					anaerobicAlacticRow.find("td:nth-of-type(2)").text(parseFloat(result.consumption.anaerobicAlactic.Kcal).toFixed(2));
					anaerobicAlacticRow.find("td:nth-of-type(3)").text(parseFloat(result.consumption.anaerobicAlactic.KJ).toFixed(2));
					anaerobicAlacticRow.find("td:nth-of-type(4)").text(parseFloat(result.consumption.anaerobicAlactic.LO2).toFixed(2));
				}
				showChart(data);
				showAdvancedResults(result);
			}
		})
		.error(function (data) {
			$("#modal-error").find("#error-content").text("");
			if(typeof data.responseJSON !== "undefined")
				$("#modal-error").find("#error-content").text(data.responseJSON.exceptionInfo.message);
			$("#modal-error").modal("show");
		});
	}
	
	function convertTimeStringToSeconds(str) {
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
		    tooltip: {
		    	pointFormat: '{series.name}: <b>{point.y:.1f} kcal</b> <br/><b>{point.percentage:.1f}%</b>'
		    },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#fff',
	                    distance: -30,
	                    format: '{point.percentage:.1f} %',
	                    inside: true
	                },
	                showInLegend: true
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
		$('#exportDropdown').show();
		$("#tabResults").show();
		$('#tabResults a').tab('show');
	}
	
	function showAdvancedResults(result) {
		if ($("#parameters\\.calculateAnaerobicAlactic").is(":checked")) {
			if ($("#monoexponential").is(":checked"))
				$("#advancedResults").html(
					"<p>" +
						"<strong>v<sub>0</sub></strong>: " + parseFloat(result.consumption.v0.mlPerMinute).toFixed(5) + "<br>" +
						"<strong>t<sub>0</sub></strong>: " + result.consumption.t0.toFixed(5) + "<br>" +
						"<strong>A</strong>: " + parseFloat(result.consumption.a1.mlPerMinute).toFixed(5) + "<br>" +
						"<strong>&tau;</strong>: " + result.consumption.tau1.toFixed(5) + "<br>" +
						"<strong>R<sup>2</sup></strong>: " + result.consumption.rSquared.toFixed(5) + "<br>" +
					"</p>"
				);
			else
				$("#advancedResults").html(
					"<p>" +
						"<strong>v<sub>0</sub></strong>: " + parseFloat(result.consumption.v0.mlPerMinute).toFixed(5) + "<br>" +
						"<strong>t<sub>0</sub></strong>: " + result.consumption.t0.toFixed(5) + "<br>" +
						"<strong>A<sub>1</sub></strong>: " + parseFloat(result.consumption.a1.mlPerMinute).toFixed(5) + "<br>" +
						"<strong>A<sub>2</sub></strong>: " + parseFloat(result.consumption.a2.mlPerMinute).toFixed(5) + "<br>" +
						"<strong>&tau;<sub>1</sub></strong>: " + result.consumption.tau1.toFixed(5) + "<br>" +
						"<strong>&tau;<sub>2</sub></strong>: " + result.consumption.tau2.toFixed(5) + "<br>" +
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
				series2.push([series1[i][0], parseFloat(result.consumption.expectedOxygenConsumption[i].mlPerMinute)]);
			
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
		            type: "scatter",
		            name: "Input"
		        },{
		            data: series2,
		            type: "line",
		            name: "Exponential Fit"
		        }]
			});
			
			$("#btnAdvancedResults").show();
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
	
	function changeTab(e) {
		var link = $(e.target), tabId = link.parent().attr("id");
		if (tabId == "tabOptions")
			$("#btnPrevious").hide();
		else
			$("#btnPrevious").show();
		if (tabId == "tabResults") {
			$("#btnNext").hide();
			$("#containerChart").highcharts(_chartOptions);
		}
		else
			$("#btnNext").show();
		
		$(link.attr("href")).find('.handsontable').each(function(index, element) {
			for(var i = 0; i< 2; ++i) {
				$(element).handsontable('render');
			}
		});
	}
	
	
	$(function(){
		$.fn.warning = function (isValid, message) {
			var field = this;
			var warningTimeout = null;
			
			$(this).keyup(function () {
				clearTimeout(warningTimeout);
				warningTimeout = setTimeout(function() {
					$(field).next("div.has-warning").remove();
					if (!isValid(field)) {
						$(field).after("<div class=\"has-warning\"><label class=\"control-label\">" + message + "</label></div>");
					}
				}, 500);
			});
		};
		
		$.validator.addMethod("greaterThan", 
			function(value, element, params) {
			    return isNaN(value) && isNaN($(params).val()) 
			        || (Number(value) > Number($(params).val())); 
			},'Must be greater than {0}.');
		
		$.validator.addMethod("empty", function(value, element, params) {
			return !params || value === "";
		}, 'Element must be empty.');
		
		$("#btnNext").click(setEnergySystems);
		$("#btnPrevious").click(previousEnergySystem);
		
		$("#containerTabs").find("a").on("shown.bs.tab", changeTab);
		
		$("#data").validate({
			errorClass: "error",
			ignore: ":hidden:not(#oxygenConsumptionRestError,#oxygenConsumptionDuringExerciseError,#oxygenConsumptionPostExerciseError)",
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
				},
				"oxygenConsumptionRestError": {
					empty: function() { return (hasSelectedAerobic() || hasSelectedAnaerobicAlactic()); }
				},
				"oxygenConsumptionDuringExerciseError": {
					empty: hasSelectedAerobic
				},
				"oxygenConsumptionPostExerciseError": {
					empty: hasSelectedAnaerobicAlactic
				}   
			},
			messages:{
				"anaerobicLacticParameters.weight": {
					min: "Please enter a value greater than or equal to 0."
				},
				"anaerobicLacticParameters.maxLactateConcentration": {
					greaterThan: "Must be greater than rest concentration."
				},
				"oxygenConsumptionRestError": {
					empty: "Table is empty."
				},
				"oxygenConsumptionDuringExerciseError": {
					empty: "Table is empty."
				},
				"oxygenConsumptionPostExerciseError": {
					empty: "Table is empty."
				} 
			}
		});
		
		$("#anaerobicLacticParameters\\.weight").warning(function(field) {
			return ($(field).val() == "" || $(field).val() > 40);
		}, "Weight value is too low?");
		
		var headers = ["Time <strong>(hh:mm:ss)</strong>", "VO<sub>2</sub> <strong>(ml/min)</strong>"];
		
		$('#oxygenConsumptionRest').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
			columns: [{
				type: 'text',
				validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
				allowInvalid: false
			}, { 	  	
				type: 'numeric',
				format: '0,0.00',
				validator: greaterThanZero,
				allowInvalid: false
			}],
			afterChange: function() {
				var errorOutput = $('#oxygenConsumptionRestError');
				var data = this.getData();
				if(data.length <= 10)
					errorOutput.val("Table is empty.");
				else
					errorOutput.val("");
				$('#data').validate().element(errorOutput);
			}
		});
		$('#oxygenConsumptionDuringExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
		    columns: [{
				type: 'text',
				validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
				allowInvalid: false
			}, { 	  	
				type: 'numeric',
				format: '0,0.00',
				validator: greaterThanZero,
				allowInvalid: false
			}],
			afterChange: function() {
				var errorOutput = $('#oxygenConsumptionDuringExerciseError');
				var data = this.getData();
				if(data.length <= 10)
					errorOutput.val("Table is empty.");
				else
					errorOutput.val("");
				$('#data').validate().element(errorOutput);
			}
		});
		$('#oxygenConsumptionPostExercise').handsontable({
		    minSpareRows: 10,
		    colHeaders: headers,
		    contextMenu: true,
		    data: [[null, null]],
		    height: 260,
		    colWidths: [1, 1],
		    stretchH: "all",
			columns: [{
				type: 'text',
				validator: /^(([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])?$/,
				allowInvalid: false
			}, { 	  	
				type: 'numeric',
				format: '0,0.00',
				validator: greaterThanZero,
				allowInvalid: false
			}],
			afterChange: function() {
				var errorOutput = $('#oxygenConsumptionPostExerciseError');
				var data = this.getData();
				if(data.length <= 10)
					errorOutput.val("Table is empty.");
				else
					errorOutput.val("");
				$('#data').validate().element(errorOutput);
			}
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
