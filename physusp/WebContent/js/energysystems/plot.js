function showAerobicPlot() {
	showPlot($("#oxygenConsumptionDuringExercise"));
}

function showAnaerobicPlot() {
	showPlot($("#oxygenConsumptionPostExercise"));
}

function showRestPlot() {
	showPlot($("#oxygenConsumptionRest"));
}

function showPlot(element, plotElement) {
	plotElement.html("");
	plotElement.append('<div></div>');
	var series1 = [];
	var input = element.handsontable('getData');

	for (var i = 0; i < input.length; i++) {
		if (input[i][0] == null || input[i][0] === "")
			break;
		series1.push([ convertTimeStringToMilliseconds(input[i][0]), input[i][1] ]);
	}

	plotElement.find("div").highcharts({
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		title : {
			text : 'Data plot'
		},
		plotOptions : {
			scatter : {
				marker : {
					radius : 2
				}
			},
	    	series: {
	    		showInLegend: false
	    	}
		},
	    tooltip: {
	    	formatter: function() {
	    		var time = convertMillisecondsToTimeString(this.x);
	    		return 'time: <b>' + time + '</b><br/>VO<sub>2</sub>: <b>' + this.y + '</b>';
	    	}
	    },
		exporting : {
			buttons : {
				contextButton : {
					enabled : false
				}
			}
		},
		xAxis : {
			type : "datetime",
			title : {
				text : "Time (minutes)"
			},
			labels : {
				format : '{value:%M}'
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : "Oxygen uptake (ml/min)"
			}
		},
		series : [ {
			data : series1,
			type : "scatter",
			name : "Input"
		} ]
	});
}

$(function(){
	$("#btnOxygenConsumptionRestPlot, #btnOxygenConsumptionPostExercisePlot, #btnOxygenConsumptionDuringExercisePlot").click(function() {
		var plotId = $(this).data("plot");
		var tableId = $(this).data("table");
		var $plot = $("#" + plotId);
		var exportPlotBtn = $(this).siblings(".dropdown");
		
		$plot.fadeToggle();
		var button = $(this);
		if (button.hasClass("active")) {
			button.removeClass("active");
			exportPlotBtn.addClass("hide");
		}
		else {
			button.addClass("active");
			exportPlotBtn.removeClass("hide");
		}
		showPlot($("#" + tableId), $plot);
	});
});

