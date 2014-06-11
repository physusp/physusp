function convertTimeStringToSeconds(str) {
	var tt = str.split(":");
	return ( tt[0] * 3600 + tt[1] * 60 + tt[2] * 1 ) * 1000;
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
		for(var i = 0 ; i < input.length; i++) {
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